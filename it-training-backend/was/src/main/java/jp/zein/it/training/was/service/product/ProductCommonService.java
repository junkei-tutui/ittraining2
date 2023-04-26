package jp.zein.it.training.was.service.product;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import jp.zein.it.training.common.generate.entity.MstItemEntity;
import jp.zein.it.training.common.generate.entity.MstProductEntity;
import jp.zein.it.training.was.dao.WasMstProductEntityDao;
import jp.zein.it.training.was.dao.WasTblOrderDetailEntityDao;
import jp.zein.it.training.was.exception.TrainingApplicationException;
import jp.zein.it.training.was.service.product.dto.ProductRegistryDto;
import lombok.RequiredArgsConstructor;

/**
 * 商品（SKU）コントローラー
 *
 */
@Component
@RequiredArgsConstructor
public class ProductCommonService {

	private final ModelMapper modelMapper;

	private final WasMstProductEntityDao mstProductEntityDao;

	private final WasTblOrderDetailEntityDao tblOrderDetailEntityDao;

	/**
	 * 消費税率
	 */
	@Value("${training.tax-rate-percentage:10}")
	private int taxRatePercentage;

	/**
	 * 商品共通チェック処理
	 * 
	 * @param request
	 */
	public void commonProductCheck(ProductRegistryDto request) {
		// 商品コードを分解して、分類コード、シーズンコードとの整合性チェックを行う。
		// 尚、分類コードおよびシーズンコードのマスタチェックは行わない。
		// また、商品コードは8桁、分類コードは2桁、シーズンコードは3桁のコードで取得することを前提（バリデーションチェックにて実施済み）

		// 分類コードの一致チェック（商品コードの上位2桁）
		if (!request.getItemCode().startsWith(request.getClassificationCode())) {
			// 分類コードが一致しない場合はエラー
			throw new TrainingApplicationException("商品コードの頭2桁は分類コードと一致している必要があります。");
		}

		// シーズンコードの一致チェック（商品コードの下位3桁）
		if (!request.getItemCode().endsWith(request.getSeasonCode())) {
			// シーズンコードが一致しない場合はエラー
			throw new TrainingApplicationException("商品コードの下3桁はシーズンコードと一致している必要があります。");
		}

		// 商品（SKU）が1件も設定されていない場合
		if (Optional.ofNullable(request.getProductDetailList()).isEmpty()
				|| request.getProductDetailList().size() == 0) {
			// 商品（SKU）が１件も設定されていない場合はエラー
			throw new TrainingApplicationException("商品（SKU）が設定されていません。");
		}
	}

	/**
	 * 商品共通設定処理
	 * 
	 * @param request
	 */
	public void commonProductSetting(ProductRegistryDto request, MstItemEntity itemInfo) {

		// 消費税率を決定する
		var taxRate = (new BigDecimal(taxRatePercentage)).divide(new BigDecimal(100), 2, RoundingMode.HALF_UP)
				.add(BigDecimal.ONE);

		// 仕入価格（税込）を設定する。
		itemInfo.setPurchasePriceIntax(request.getPurchasePrice().multiply(taxRate).setScale(0, RoundingMode.FLOOR));

		// 販売価格（税込）を設定する。
		itemInfo.setSellingPriceIntax(request.getSellingPrice().multiply(taxRate).setScale(0, RoundingMode.FLOOR));

	}

	/**
	 * SKU一括更新処理
	 * 
	 * @param itemInfo 商品マスタ情報
	 * @param request  リクエスト情報（商品情報）
	 */
	public void replacementSKU(MstItemEntity itemInfo, ProductRegistryDto request) {

		// 対象商品の商品（SKU）を一旦、論理削除する
		var delitem = mstProductEntityDao.getListByItemCode(itemInfo.getItemCode());
		if (!Optional.ofNullable(delitem).isEmpty()) {
			delitem.forEach(v -> {
				// 商品（SKU）マスタを更新する。
				v.getItem1().setIsDelete(true);
				mstProductEntityDao.update(v.getItem1());
			});
		}

		// 登録されたSKUのマスタチェックを行う。
		request.getProductDetailList().forEach(v -> {

			// 商品SKUコードを生成する。（商品コード＋カラーコード＋サイズコード）
			var skuCode = String.format("%s%s%s", itemInfo.getItemCode(), v.getColorCode(), v.getSizeCode());

			// 生成した商品SKUコードで商品（SKU）マスタを取得する。
			var product = mstProductEntityDao.selectByIdIncLD(skuCode);

			// レコードの存在有無で、処理を変更する。
			if (Objects.isNull(product)) {
				// 新規レコードを作成する。
				var entryRecode = new MstProductEntity();
				// 商品マスタの情報を登録用エンティティにマッピングする。
				modelMapper.map(itemInfo, entryRecode);
				// 入力されたSKU固有情報を登録用エンティティにマッピングする。
				modelMapper.map(v, entryRecode);
				// 固定情報の情報のみ再設定
				// 商品（SKU）コードを設定する
				entryRecode.setProductCode(skuCode);
				// 削除フラグ（OFF）
				entryRecode.setIsDelete(false);

				// 商品（SKU）マスタに登録する。
				mstProductEntityDao.insert(entryRecode);
			} else {
				// 商品マスタの情報をマッピングする。
				modelMapper.map(itemInfo, product);
				// 入力された情報でSKU情報を更新する。
				modelMapper.map(v, product);
				// 削除フラグは有効に変更する。
				product.setIsDelete(false);

				// 商品（SKU）マスタを更新する。
				mstProductEntityDao.update(product);
			}
		});

		// 削除された商品（SKU）の受注データによる利用をチェックする。
		var isOrder = tblOrderDetailEntityDao.isExistsOrderDetailByDLProduct(itemInfo.getItemCode());

		// 削除されたSKUが受注データ使用されていないかをチェックする。
		if (isOrder) {
			// 商品マスタが存在かつ、削除フラグがOFFの場合はエラー
			throw new TrainingApplicationException("削除された商品（SKU）の中に使用されている商品がございます。\n一度、照会画面に戻り、再度、修正を行ってください。");
		}

	}

}