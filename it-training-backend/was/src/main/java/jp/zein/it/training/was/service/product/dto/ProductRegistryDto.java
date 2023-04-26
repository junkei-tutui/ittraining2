package jp.zein.it.training.was.service.product.dto;

import java.math.BigDecimal;

import lombok.Data;

/**
 * 共通商品登録データモデル
 * <p>
 * 商品情報および商品の全パターン（SKU）をリストで保持する。
 * <p>
 */
@Data
public class ProductRegistryDto {

	/** 商品コード */
	private String itemCode;

	/** 商品名 */
	private String itemName;

	/** カテゴリコード */
	private String categoryCode;

	/** 分類コード */
	private String classificationCode;

	/** シーズンコード */
	private String seasonCode;

	/** 仕入先 */
	private String supplierId;

	/** 仕入価格（税抜） */
	private BigDecimal purchasePrice;

	/** 販売価格（税抜） */
	private BigDecimal sellingPrice;

	/**
	 * 商品（SKU）リスト
	 */
	private java.util.List<ProductDetail> productDetailList;

	/**
	 * 商品パターンデータモデル
	 *
	 */
	@Data
	public static class ProductDetail {

		/** カラーコード */
		private String colorCode;

		/** サイズコード */
		private String sizeCode;
	}

}
