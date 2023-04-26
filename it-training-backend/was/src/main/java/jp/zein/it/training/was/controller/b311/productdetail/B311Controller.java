package jp.zein.it.training.was.controller.b311.productdetail;

import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jp.zein.it.training.was.dao.WasMstItemEntityDao;
import jp.zein.it.training.was.dao.WasMstPartnerEntityDao;
import jp.zein.it.training.was.dao.WasMstProductEntityDao;
import jp.zein.it.training.was.dao.WasTblOrderDetailEntityDao;
import jp.zein.it.training.was.exception.TrainingApplicationException;
import lombok.RequiredArgsConstructor;

/**
 * B311:商品（SKU）情報取得コントローラー
 *
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/product")
public class B311Controller {

	private final ModelMapper modelMapper;

	private final WasMstItemEntityDao mstItemEntityDao;

	private final WasMstPartnerEntityDao mstPartnerEntityDao;

	private final WasMstProductEntityDao mstProductEntityDao;

	private final WasTblOrderDetailEntityDao tblOrderDetailEntityDao;

	/**
	 * B311:商品（SKU）情報取得
	 * 
	 * @param itemCode SKUを含む商品の詳細を取得する対象の商品コード
	 * @return 商品コードより取得した商品（SKU）詳細データ（{@link B311Response}）
	 */
	@GetMapping()
	@Transactional
	public B311Response get(@RequestParam(name = "itemCode", required = true) String itemCode) {

		// 商品マスタを取得する
		var itemDetail = mstItemEntityDao.selectById(itemCode);

		// 商品マスタの取得チェック
		if (itemDetail == null) {
			// 商品マスタが存在しない場合はエラー
			throw new TrainingApplicationException("対象の商品データは削除された可能性があります。管理者にお問い合わせください。");
		}

		// 仕入れ先の情報を取得する。
		var supplierDetail = mstPartnerEntityDao.selectById(itemDetail.getPartnerId());
		if (supplierDetail == null) {
			// 取引先マスタが存在しない場合はエラー
			throw new TrainingApplicationException("取引先情報が存在しません。管理者にお問い合わせください。");
		}

		// 商品（SKU）情報を取得する
		var productDtlList = mstProductEntityDao.getListByItemCode(itemCode).stream().map(group -> {
			// 商品（SKU）マスタを一括でマッピング
			var result = modelMapper.map(group.getItem1(), B311Response.ProductDetail.class);
			// カラー名を追加設定
			result.setColorName(group.getItem2().getColorName());
			// サイズ名を追加設定
			result.setSizeName(group.getItem3().getSizeName());
			// 削除可能フラグを設定する。
			result.setIsCanDeleted(
					!tblOrderDetailEntityDao.isExistsOrderDetailByProductSku(group.getItem1().getProductCode()));
			return result;
		}).collect(Collectors.toList());
		// レスポンス情報にマッピングする
		var response = modelMapper.map(itemDetail, B311Response.class);
		// 仕入先情報を追加設定する。
		response.setSupplierId(supplierDetail.getPartnerId());
		response.setSupplierName(supplierDetail.getPartnerName());
		// 商品（SKU）の情報を追加設定する
		response.setProductDetailList(productDtlList);

		return response;
	}

}