package jp.zein.it.training.was.controller.b311.productdetail;

import java.math.BigDecimal;

import lombok.Data;

/**
 * B311:商品（SKU）情報取得用データ（商品詳細データ）
 * 
 */
@Data
public class B311Response {

	/** 品目コード */
	private String itemCode;

	/** 品目名 */
	private String itemName;

	/** カテゴリコード */
	private String categoryCode;

	/** 分類コード */
	private String classificationCode;

	/** シーズンコード */
	private String seasonCode;

	/** 仕入先 */
	private String supplierId;

	/** 仕入先 */
	private String supplierName;

	/** 仕入価格（税抜） */
	private BigDecimal purchasePrice;

	/** 仕入価格（税込） */
	private BigDecimal purchasePriceIntax;

	/** 販売価格（税抜） */
	private BigDecimal sellingPrice;

	/** 販売価格（税込） */
	private BigDecimal sellingPriceIntax;

	/**
	 * 商品（SKU）リスト
	 */
	private java.util.List<ProductDetail> productDetailList;

	/**
	 * 商品のSKU情報データモデル
	 *
	 */
	@Data
	public static class ProductDetail {

		/** 商品（SKU）コード */
		private String productCode;

		/** 品目コード */
		private String itemCode;

		/** カラーコード */
		private String colorCode;

		/** カラー名 */
		private String colorName;

		/** サイズコード */
		private String sizeCode;

		/** サイズ名 */
		private String sizeName;

		/** 削除可能フラグ */
		private Boolean isCanDeleted;

	}

}
