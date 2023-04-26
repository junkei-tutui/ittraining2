package jp.zein.it.training.was.service.stock;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.dataformat.csv.CsvGenerator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;

import jp.zein.it.training.common.generate.entity.TblOrderDetailEntity;
import jp.zein.it.training.common.generate.entity.TblOrderEntity;
import jp.zein.it.training.common.service.TrainingDateTimeService;
import jp.zein.it.training.common.util.TrainingConsts;
import jp.zein.it.training.common.util.TrainingConsts.OrderLineStatus;
import jp.zein.it.training.common.util.TrainingConsts.OrderStatus;
import jp.zein.it.training.was.dao.WasTblOrderDetailEntityDao;
import jp.zein.it.training.was.dao.WasTblOrderEntityDao;
import jp.zein.it.training.was.exception.TrainingSystemException;
import jp.zein.it.training.was.service.order.csv.OrderDataBean;
import jp.zein.it.training.was.service.stock.csv.NoStockOrderDataBean;
import jp.zein.it.training.was.service.stock.csv.StockDataBean;
import jp.zein.it.training.was.util.csv.CsvLoader;
import jp.zein.it.training.was.util.csv.CsvLoader.DataLine;
import jp.zein.it.training.was.util.csv.TextFileUtils;
import lombok.RequiredArgsConstructor;

/**
 * 在庫管理サービスクラス
 * <p>
 * 在庫データを管理するサービスクラス
 * <p>
 */
@Component
@RequiredArgsConstructor
public class StockManagementService {

	private final ModelMapper modelMapper;

	private final CsvLoader<StockDataBean> csvLoader;

	private final WasTblOrderEntityDao tblOrderEntityDao;

	private final WasTblOrderDetailEntityDao tblOrderDetailEntityDao;

	private final TrainingDateTimeService dateTimeService;

	/**
	 * 引当結果在庫データの出力先パス
	 */
	@Value("${training.file-storage.path-prefix.output-stock-result}")
	private String stockResultListPath;

	/**
	 * 引当結果在庫データの基本ファイル名
	 */
	@Value("${training.file-storage.base-filename.output-stock-result}")
	private String stockResultListFileName;

	/**
	 * 引当不可リストの出力先パス
	 */
	@Value("${training.file-storage.path-prefix.output-cannot-reserve-order}")
	private String noStockOrderListPath;

	/**
	 * 引当不可リストの基本ファイル名
	 */
	@Value("${training.file-storage.base-filename.output-cannot-reserve-order}")
	private String noStockOrderListFileName;

	/**
	 * 引当処理済み在庫リスト
	 */
	private List<StockDataBean> stockList;

	/**
	 * 在庫引当有無フラグ（true: 引当あり）
	 */
	private boolean isAllocationStock = false;
	/**
	 * 引当不可受注リスト
	 */
	private List<NoStockOrderDataBean> noStockOrderList;

	/**
	 * CSVファイル取り込み処理
	 * 
	 * @param csvStream アップロードされた在庫ファイルのストリーム
	 * @return エラー有無フラグを返す。1行でも取込エラーが発生している場合に{@code true}が設定される
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws IOException
	 */
	public boolean importStockCsv(InputStream csvStream)
			throws IllegalArgumentException, IllegalAccessException, IOException {

		// 在庫引き当て済みリストを初期化
		this.stockList = null;
		// 在庫引当有無フラグ初期化
		this.isAllocationStock = false;
		// 引当できなっかったオーダーリストを初期化
		this.noStockOrderList = null;

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
	 * @param csvStream アップロードされた在庫ファイルのストリーム
	 * @throws IOException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	private void loadAndValidate(InputStream csvStream)
			throws IOException, IllegalArgumentException, IllegalAccessException {

		csvLoader.load(StockDataBean.class, csvStream);
		csvLoader.validate();
	}

	/**
	 * 在庫ファイル固有バリデート
	 * <p>
	 * CSVパーサーにてチェックできないCSV固有の取り込みチェックを行う
	 * <p>
	 * 
	 * @param dataLineList アップロードされた在庫ファイル（在庫データ（{@link StockDataBean}）のリスト）
	 */
	private void correlationCsvValidate(List<DataLine<StockDataBean>> dataLineList) {
		// FIXME: 特に処理なし サンプルとしてロジックを残す。
		// レコード毎に個別のチェックを行う
//		for (var dataLine : dataLineList) {
//			
//		}
	}

	/**
	 * 在庫ファイル個別バリデート
	 * <p>
	 * 在庫ファイル（在庫データ）に対する個別のバリデートを行う。
	 * <p>
	 * 
	 * @param dataLineList アップロードされた在庫ファイル（在庫データ（{@link StockDataBean}）のリスト）
	 */
	private void correlationValidate(List<DataLine<StockDataBean>> dataLineList) {
		// FIXME: 特に処理なし サンプルとしてロジックを残す。
//		// レコード毎に個別のチェックを行う
//		for (var dataLine : dataLineList) {
//
//		}
	}

	/**
	 * 
	 * 在庫引き当て処理
	 * <p>
	 * バリデートチェックにてOKとなった在庫データを使用し、在庫引き当て処理をする。
	 * <p>
	 * 
	 * @param orderList アップロードされた注文書ファイル（受注データ（{@link OrderDataBean}）のリスト）
	 */
	public void allocationStock() {

		// 取り込んだ在庫ファイルで在庫引き当て処理をする。
		this.stockList = csvLoader.getDataLineList().stream().map(DataLine<StockDataBean>::getCsvBean) //
				.collect(Collectors.toList());

		// 引当できなっかったオーダーリストを初期化
		this.noStockOrderList = new ArrayList<NoStockOrderDataBean>();

		// 1)引当対象の受注を検索する
		var orders = tblOrderEntityDao.selectByConfirmOrder();

		// 【ループ１】受注明細を取得する
		for (var orderData : orders) {

			boolean bolreserveOK = false;
			boolean bolreserveNG = false;

			// 2)引当対象の受注詳細を検索する
			var orderdetails = tblOrderDetailEntityDao.selectIncludeMasterByOrderNo(orderData.getOrderNo());

			// 3)在庫引当
			// 【ループ２】受注データから在庫を引き当てる
			for (var orderdetailData : orderdetails) {

				// a)取得した受注明細から判定を行う
				// a-1)受注明細.受注明細ステータスが、01引当前 以外の場合、次の受注明細に移る
				if (!orderdetailData.getOrderLineStatus().equals(OrderLineStatus.BEFORERESERVED.getValue())) {
					continue;
				}

				// a-2)在庫リソース.SKU = 受注明細.SKUコード の在庫リソースが存在した場合
				int indexnum = 0;
				boolean existproduct = false;
				for (indexnum = 0; indexnum < stockList.size(); indexnum++) {
					if (stockList.get(indexnum).getProduct_code().equals(orderdetailData.getProductCode())) {
						existproduct = true;
						bolreserveOK = true;
						break;
					}
				}

				// 受注詳細データより受注明細データのみ取り出す
				var detailData = modelMapper.map(orderdetailData, TblOrderDetailEntity.class);

				if (existproduct) {
					if (stockList.get(indexnum).getAllocable_qty().compareTo(orderdetailData.getQuantity()) >= 0) {
						// 在庫リソース.引当可能在庫 >= 受注明細.数量 の場合
						// a-2-1)在庫リソース.引当可能在庫 に、在庫リソース.引当可能在庫 - 受注明細.数量 をセットする
						// a-2-1)在庫リソース.引当済在庫 に、受注明細.数量 を加算する
						stockList.get(indexnum).setAllocable_qty(
								stockList.get(indexnum).getAllocable_qty().subtract(orderdetailData.getQuantity()));
						stockList.get(indexnum).setReserved_qty(
								stockList.get(indexnum).getReserved_qty().add(orderdetailData.getQuantity()));
						detailData.setOrderLineStatus(OrderLineStatus.RESERVED.getValue());

						// 在庫引当有フラグを引当ありに変更
						this.isAllocationStock = true;
					} else {
						// 在庫リソース.引当可能在庫 ＜ 受注明細.数量 の場合
						indexnum = -1;
						existproduct = false;
					}
				}
				// 在庫リソース.引当可能在庫 ＜ 受注明細.数量 の場合
				// a-3)在庫リソース.SKU = 受注明細.SKUコード の在庫リソースが存在しなかった場合
				if (!existproduct) {
					bolreserveNG = true;
					detailData.setOrderLineStatus(OrderLineStatus.NOTRESERVED.getValue());

					// a-4)受注明細データを引当不可に更新した場合、引当不可情報を記録する
					// 引当不可受注ファイル（CSVデータ）に受注明細情報をマッピング
					var noStockOrderData = modelMapper.map(orderdetailData, NoStockOrderDataBean.class);

					// 受注情報を個別に引当不可受注ファイル（CSVデータ）へマッピング
					// 受注件名
					noStockOrderData.setOrderSubject(orderData.getOrderSubject());
					// 納品先住所
					noStockOrderData.setDeliveryAddress(orderData.getDeliveryAddress());
					// 注文受付ステータス
					noStockOrderData.setOrderStatus(orderData.getOrderStatus());
					// 取引先ID
					noStockOrderData.setPartnerId(orderData.getPartnerId());
					// 取引先名
					noStockOrderData.setPartnerName(orderData.getPartnerName());
					// 受注日
					noStockOrderData.setOrderDate(orderData.getOrderDate().format(TrainingConsts.CSV_DATE_FORMETTER));
					// 納期
					noStockOrderData
							.setDeliveryDate(orderData.getDeliveryDate().format(TrainingConsts.CSV_DATE_FORMETTER));

					// 区分を追加で設定
					if (indexnum < 0) {
						noStockOrderData.setResultType("在庫不足");
					} else {
						noStockOrderData.setResultType("存在しない商品");
					}

					this.noStockOrderList.add(noStockOrderData);
				}
				// a-2-2)受注明細を更新する
				// a-2-3)受注明細を更新する
				// a-3-1)受注明細を更新する
				tblOrderDetailEntityDao.update(detailData);
			}

			// 4)受注データの更新を行う
			String reserveresult;
			if (bolreserveOK) {
				if (!bolreserveNG) {
					// 4-1)受注明細ステータスが全て、02引当済 の場合、受注受付ステータスを更新する
					reserveresult = OrderStatus.RESERVED.getValue();
				} else {
					// 4-2)受注明細ステータスをひとつでも、03引当不可 にした場合、受注受付ステータスを更新する
					reserveresult = OrderStatus.SOMENOTRESERVED.getValue();
				}
			} else {
				// 4-3)受注明細ステータスが全て、03引当不可 の場合、受注受付ステータスを更新する
				reserveresult = OrderStatus.NOTRESERVED.getValue();
			}
			TblOrderEntity orderUpdate = modelMapper.map(orderData, TblOrderEntity.class);
			// 受注受付ステータスを追加設定する
			orderUpdate.setOrderStatus(reserveresult);
			tblOrderEntityDao.update(orderUpdate);
		}
	}

	/**
	 * 引当処理結果在庫CSVファイル出力処理
	 * 
	 * @return
	 * @throws IOException
	 */
	public String outputStockResultCsv() throws IOException {
		var mapper = new CsvMapper();

		String stockFileName = "";

		if (this.isAllocationStock && !Optional.ofNullable(this.stockList).isEmpty()) {
			mapper.configure(CsvGenerator.Feature.ALWAYS_QUOTE_STRINGS, true);
			var schema = mapper.schemaFor(StockDataBean.class).withHeader();
			var stockText = mapper.writer(schema).writeValueAsString(this.stockList);

			// アプリケーションパラメータに設定された基本ファイル名より出力ファイル名を生成する。 ※ 必ず拡張子があることを前提とする。
			String[] fileNameSplit = this.stockResultListFileName.split("\\.(?=[^\\.]+$)");
//			stockFileName = String.format("%s%s_%s.%s", TrainingConsts.RETURN_FILE_PRIFIX, fileNameSplit[0],
//					dateTimeService.now().format(TrainingConsts.FILE_DATETIME_FORMETTER), fileNameSplit[1]);
			stockFileName = String.format("%s%s_%s.%s", "", fileNameSplit[0],
					dateTimeService.now().format(TrainingConsts.FILE_DATETIME_FORMETTER), fileNameSplit[1]);

			// BOM付きのバイナリデータに変換する。
			var stockBuf = TextFileUtils.toBinaryWithBom(stockText);

			// 出力ファイルをオープンする。
			try (FileOutputStream fos = new FileOutputStream(this.stockResultListPath + stockFileName)) {
				// 引当済み在庫ファイルを出力する。
				fos.write(stockBuf);
			} catch (Exception e) {
				// 例外はシステムエラーとする。
				throw new TrainingSystemException("system error", "システムエラーが発生しました。", e);
			}
		}
		return stockFileName;
	}

	/**
	 * 引当不可受注CSVファイル出力処理
	 * 
	 * @return
	 * @throws IOException
	 */
	public String outputNoStockOrderCsv() throws IOException {
		var mapper = new CsvMapper();

		String noStockOrderFileName = "";
		if (!Optional.ofNullable(this.noStockOrderList).isEmpty() && this.noStockOrderList.size() > 0) {
			mapper.configure(CsvGenerator.Feature.ALWAYS_QUOTE_STRINGS, true);
			var schema = mapper.schemaFor(NoStockOrderDataBean.class).withHeader();
			var noStockOrderText = mapper.writer(schema).writeValueAsString(this.noStockOrderList);

			// アプリケーションパラメータに設定された基本ファイル名より出力ファイル名を生成する。 ※ 必ず拡張子があることを前提とする。
			String[] fileNameSplit = this.noStockOrderListFileName.split("\\.(?=[^\\.]+$)");
//			noStockOrderFileName = String.format("%s%s_%s.%s", TrainingConsts.RETURN_FILE_PRIFIX, fileNameSplit[0],
//					dateTimeService.now().format(TrainingConsts.FILE_DATETIME_FORMETTER), fileNameSplit[1]);
			noStockOrderFileName = String.format("%s%s_%s.%s", "", fileNameSplit[0],
					dateTimeService.now().format(TrainingConsts.FILE_DATETIME_FORMETTER), fileNameSplit[1]);

			// BOM付きのバイナリデータに変換する。
			var noStockOrderBuf = TextFileUtils.toBinaryWithBom(noStockOrderText);

			try (FileOutputStream fos = new FileOutputStream(noStockOrderListPath + noStockOrderFileName)) {
				// エラーファイルを出力する。
				fos.write(noStockOrderBuf);
			} catch (Exception e) {
				// 例外はシステムエラーとする。
				throw new TrainingSystemException("system error", "システムエラーが発生しました。", e);
			}
		}
		return noStockOrderFileName;
	}
}
