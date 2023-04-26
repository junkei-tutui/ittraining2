package jp.zein.it.training.was.service.order;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.MessageFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import jp.zein.it.training.common.generate.entity.TblOrderDetailEntity;
import jp.zein.it.training.common.generate.entity.TblOrderEntity;
import jp.zein.it.training.common.util.TrainingConsts;
import jp.zein.it.training.common.util.TrainingConsts.OrderStatus;
import jp.zein.it.training.common.util.TrainingConsts.PartnerType;
import jp.zein.it.training.was.dao.WasMstColorEntityDao;
import jp.zein.it.training.was.dao.WasMstItemEntityDao;
import jp.zein.it.training.was.dao.WasMstPartnerEntityDao;
import jp.zein.it.training.was.dao.WasMstProductEntityDao;
import jp.zein.it.training.was.dao.WasMstSizeEntityDao;
import jp.zein.it.training.was.dao.WasTblOrderDetailEntityDao;
import jp.zein.it.training.was.dao.WasTblOrderEntityDao;
import jp.zein.it.training.was.service.order.csv.OrderDataBean;
import jp.zein.it.training.was.service.order.csv.OrderDataBean.OrderDataCsv;
import jp.zein.it.training.was.util.csv.CsvLoader;
import jp.zein.it.training.was.util.csv.CsvLoader.DataLine;
import lombok.RequiredArgsConstructor;

/**
 * 受注管理サービスクラス
 * <p>
 * 受注データを管理するサービスクラス
 * <p>
 */
@Component
@RequiredArgsConstructor
public class OrderManagementService {

	private final CsvLoader<OrderDataBean> csvLoader;

	private final WasMstPartnerEntityDao mstPartnerEntityDao;

	private final WasMstItemEntityDao mstItemEntityDao;

	private final WasMstColorEntityDao mstColorEntityDao;

	private final WasMstSizeEntityDao mstSizeEntityDao;

	private final WasMstProductEntityDao mstProductEntityDao;

	private final WasTblOrderEntityDao tblOrderEntityDao;

	private final WasTblOrderDetailEntityDao tblOrderDetailEntityDao;

	/**
	 * 消費税率
	 */
	@Value("${training.tax-rate-percentage:10}")
	private int taxRatePercentage;

	/**
	 * CSVファイル取り込み処理
	 * 
	 * @param csvStream アップロードされた注文書ファイルのストリーム
	 * @return エラー有無フラグを返す。1行でも取込エラーが発生している場合に{@code true}が設定される
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws IOException
	 */
	public boolean importOrderCsv(InputStream csvStream)
			throws IllegalArgumentException, IllegalAccessException, IOException {

		// CSVアップロード標準バリデートを行う。
		loadAndValidate(csvStream);

		// CSVファイル個別のバリデートを行う。
		correlationCsvValidate(csvLoader.getDataLineList());

		// 個別のバリデートを行う。
		correlationValidate(csvLoader.getDataLineList());

		// エラーハンドリング
		if (csvLoader.hasError()) { // 1件以上エラーがあった場合はエラーとする。（処理終了）
			// 取込エラー有として呼び出し元に処理を戻す。
			return true;
		}

		// オーダー情報を一括で登録する。
		insertOrder(csvLoader.getDataLineList().stream().map(DataLine<OrderDataBean>::getCsvBean) //
				.collect(Collectors.toList()));

		// 取込エラー無しとして処理終了
		return false;
	}

	public String getTextWithValidationErrorMessage() {
		// 取り込み結果をテキストで取得する。
		return csvLoader.getTextWithValidationErrorMessage();
	}

	/**
	 * アップロードされたCSVの読み込みと検証
	 * 
	 * @param csvStream アップロードされた注文書ファイルのストリーム
	 * @throws IOException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	private void loadAndValidate(InputStream csvStream)
			throws IOException, IllegalArgumentException, IllegalAccessException {

		csvLoader.load(OrderDataBean.class, csvStream);
		csvLoader.validate();
	}

	/**
	 * 注文書ファイル固有バリデート
	 * <p>
	 * CSVパーサーにてチェックできないCSV固有の取り込みチェックを行う
	 * <p>
	 * 
	 * @param dataLineList アップロードされた注文書ファイル（受注データ（{@link OrderDataBean}）のリスト）
	 */
	private void correlationCsvValidate(List<DataLine<OrderDataBean>> dataLineList) {

		/**
		 * CSVより連携される日付のフォーマット
		 */
		DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy/MM/dd");

		// レコード毎に個別のチェックを行う
		for (var dataLine : dataLineList) {

			// 注文日の日付妥当チェック
			try {
				// 文字列の注文日をLocalDateに変換可能かをチェックする
				LocalDate.parse(dataLine.getCsvBean().getOrderDate(), df);
			} catch (DateTimeParseException e) {
				// 日付変換でエラーとなった場合はエラーメッセージを追加する。
				dataLine.appendValidationErrorMessage(MessageFormat.format("{0}の形式が不正です。", OrderDataCsv.ORDER_DATE));
			}

			// 納期の日付妥当チェック
			try {
				// 文字列の納期をLocalDateに変換可能かをチェックする
				LocalDate.parse(dataLine.getCsvBean().getDeliveryDate(), df);
			} catch (DateTimeParseException e) {
				// 日付変換でエラーとなった場合はエラーメッセージを追加する。
				dataLine.appendValidationErrorMessage(MessageFormat.format("{0}の形式が不正です。", OrderDataCsv.DELIVERY_DATE));
			}
		}
	}

	/**
	 * 注文書ファイル個別バリデート
	 * <p>
	 * 注文書ファイル（受注データ）に対する個別のバリデートを行う。
	 * <p>
	 * 
	 * @param dataLineList アップロードされた注文書ファイル（受注データ（{@link OrderDataBean}）のリスト）
	 */
	private void correlationValidate(List<DataLine<OrderDataBean>> dataLineList) {

		// レコード毎に個別のチェックを行う
		for (var dataLine : dataLineList) {

			// 取引先（クライアント）の存在チェックを行う。
			validateExistClient(dataLine);

			// 商品（SKU）の存在チェックを行う。
			validateExistItemAndProduct(dataLine);
		}
	}

	/**
	 * 
	 * 受注データ登録処理
	 * <p>
	 * バリデートチェックにてOKとなった受注データをDBに一括登録する
	 * <p>
	 * 
	 * @param orderList アップロードされた注文書ファイル（受注データ（{@link OrderDataBean}）のリスト）
	 */
	private void insertOrder(List<OrderDataBean> orderList) {
		// 取り込みデータを行単位で処理する
		// ヘッダ情報を保持
		String groupOrderNo = null;

		// 最後に採番された受注番号を取得する。
		int autoNumOrderNo = tblOrderEntityDao.lastOrderNo();

		// 消費税率を決定する
		var taxRate = (new BigDecimal(taxRatePercentage)).divide(new BigDecimal(100), 2, RoundingMode.HALF_UP)
				.add(BigDecimal.ONE);

		for (var orderData : orderList) {
			// 受注番号を生成する。

			// 日付として連携される以下の項目をキャスト
			// 注文日 （文字列で取得、日付変換可否はチェック済みとする。）
			LocalDate orderDate = LocalDate.parse(orderData.getOrderDate(),
					DateTimeFormatter.ofPattern(TrainingConsts.CSV_DATE_FORMAT));
			// 納期 （文字列で取得、日付変換可否はチェック済みとする。）
			LocalDate deliveryDate = LocalDate.parse(orderData.getDeliveryDate(),
					DateTimeFormatter.ofPattern(TrainingConsts.CSV_DATE_FORMAT));

			// グループ（受注単位）チェック
			if (!StringUtils.equals(orderData.getClientOrderNo(), groupOrderNo)) {
				// 受注日と受注番号が異なる場合はヘッダが変更されたと判断する。
				// 受注番号をインクリメントする
				autoNumOrderNo++;

				// 受注テーブルのデータを作成する
				var orderHeader = new TblOrderEntity();
				// カラムが異なる事を前提で各項目毎に設定する。
				// 受注番号（自動インクリメントによりnullを設定）
				orderHeader.setOrderNo(autoNumOrderNo);
				// 受注件名
				orderHeader.setOrderSubject(orderData.getOrderSubject());
				// 取引先ID
				orderHeader.setPartnerId(orderData.getClientId());
				// 受注日
				orderHeader.setOrderDate(orderDate);
				// 納期
				orderHeader.setDeliveryDate(deliveryDate);
				// 納品先郵便番号
				orderHeader.setDeliveryZip(orderData.getDeliveryZip());
				// 納品先住所
				orderHeader.setDeliveryAddress(orderData.getDeliveryAddress());
				// 受注担当者
				orderHeader.setResponsibleParty(orderData.getResponsibleParty());
				// 受注受付ステータス
				orderHeader.setOrderStatus(OrderStatus.UNCONFIRMED.getValue());
				// 合計受注金額（税抜）
				orderHeader.setTotalOrderAmount(orderData.getTotalOrderAmount());
				// 合計受注金額（税込）
				orderHeader.setTotalOrderAmountIntax(
						orderData.getTotalOrderAmount().multiply(taxRate).setScale(0, RoundingMode.FLOOR));
				// 備考（メモ）
				orderHeader.setNote(orderData.getMemo());
				// クライアント側注文番号
				orderHeader.setClientOrderNo(orderData.getClientOrderNo());
				// 削除フラグ
				orderHeader.setIsDelete(false);

				// 受注データを登録する。
				tblOrderEntityDao.insert(orderHeader);
				// グループ条件を設定する。
				groupOrderNo = orderData.getClientOrderNo();
			}

			// 受注明細テーブルのデータを作成する
			var orderDetail = new TblOrderDetailEntity();
			// カラムが異なる事を前提で各項目毎に設定する。
			// 受注番号
			orderDetail.setOrderNo(autoNumOrderNo);
			// 明細番号
			orderDetail.setLineNo(orderData.getLineNo());
			// SKUコード（商品コード＋カラーコード＋サイズコード）
			orderDetail.setProductCode(
					orderData.getItemCode().concat(orderData.getColorCode()).concat(orderData.getSizeCode()));
			// 数量
			orderDetail.setQuantity(orderData.getQuantity());
			// 販売価格（税抜）
			orderDetail.setSellingPrice(orderData.getSellingPrice());
			// 販売価格（税込）
			orderDetail.setSellingPriceIntax(
					orderData.getSellingPrice().multiply(taxRate).setScale(0, RoundingMode.FLOOR));
			// 小計販売価格（税抜）
			orderDetail.setSubtotalSellingPrice(orderData.getSubtotalSellingPrice());
			// 小計販売価格（税込）
			orderDetail.setSubtotalSellingPriceIntax(
					orderData.getSubtotalSellingPrice().multiply(taxRate).setScale(0, RoundingMode.FLOOR));
			// 削除フラグ
			orderDetail.setIsDelete(false);
			// 受注明細登録
			tblOrderDetailEntityDao.insert(orderDetail);
		}
	}

	/**
	 * クライアント（取引先）の存在チェック
	 *
	 * @param dataLineアップロードされたCSVの１行データ（受注データ（{@link OrderDataBean}））
	 */
	private void validateExistClient(DataLine<OrderDataBean> dataLine) {

		// 取引先マスタに販売先（クライアント）として存在するかチェックする
		var partnerEntity = mstPartnerEntityDao.selectById(dataLine.getCsvBean().getClientId());
		// 取引先マスタより取得データが0件の場合はエラーとする。
		if (Objects.isNull(partnerEntity)) {
			// マスタチェックエラーとしてエラーメッセージを追加する。
			dataLine.appendValidationErrorMessage("取引先マスタに存在しません。");
		} else {
			// マスタが存在する場合は取引区分をチェックする。
			var partnaerType = PartnerType.valueOf(partnerEntity.getPartnerType());
			if (!partnaerType.isClient()) {
				// 販売先の区分じゃない場合はエラーとしてエラーメッセージを追加する。
				dataLine.appendValidationErrorMessage("販売先ではありません。");
			}
		}
	}

	/**
	 * 商品（SKU）の存在チェック
	 *
	 * @param dataLineアップロードされたCSVの１行データ（受注データ（{@link OrderDataBean}））
	 */
	private void validateExistItemAndProduct(DataLine<OrderDataBean> dataLine) {
		// 商品、カラー、サイズ毎にマスタチェックし、何れかがエラーの場合にフラグを立てる
		boolean errorFlag = false;
		var csvBean = dataLine.getCsvBean();

		// 商品マスタに存在するかチェックする
		var itemEntity = mstItemEntityDao.selectById(csvBean.getItemCode());

		// 商品マスタより取得データが0件の場合はエラーとする。
		if (Objects.isNull(itemEntity)) {
			// マスタチェックエラーとしてエラーメッセージを追加する。
			dataLine.appendValidationErrorMessage("商品マスタに存在しません。");
			// エラーフラグを立てる
			errorFlag = true;
		}

		// カラーマスタに存在するかチェックする
		var colorEntity = mstColorEntityDao.selectById(csvBean.getColorCode());
		// カラーマスタより取得データが0件の場合はエラーとする。
		if (Objects.isNull(colorEntity)) {
			// マスタチェックエラーとしてエラーメッセージを追加する。
			dataLine.appendValidationErrorMessage("カラーマスタに存在しません。");
			// エラーフラグを立てる
			errorFlag = true;
		}

		// サイズマスタに存在するかチェックする
		var sizeEntity = mstSizeEntityDao.selectById(csvBean.getSizeCode());
		// サイズマスタより取得データが0件の場合はエラーとする。
		if (Objects.isNull(sizeEntity)) {
			// マスタチェックエラーとしてエラーメッセージを追加する。
			dataLine.appendValidationErrorMessage("サイズマスタに存在しません。");
			// エラーフラグを立てる
			errorFlag = true;
		}

		// SKUチェック
		if (!errorFlag) {
			// 商品、カラー、サイズ全てのマスタチェックがOKだった場合のみSKU（組み合わせ）のチェックを行う。
			// SKUコードを生成する。
			var skuEntity = mstProductEntityDao
					.selectById(csvBean.getItemCode().concat(csvBean.getColorCode()).concat(csvBean.getSizeCode()));
			// SKUマスタより取得データが0件の場合はエラーとする。
			if (Objects.isNull(skuEntity)) {
				// マスタチェックエラーとしてエラーメッセージを追加する。
				dataLine.appendValidationErrorMessage("SKUマスタに商品、カラー、サイズの組み合わせが存在しません。");
				// エラーフラグを立てる
			}
		}
	}

}
