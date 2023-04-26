package jp.zein.it.training.was.entity.mstitementitydao;

import java.math.BigDecimal;

import org.seasar.doma.Column;
import org.seasar.doma.Entity;

import lombok.Getter;
import lombok.Setter;

/**
 * 商品一覧取得用エンティティ
 *
 */
@Entity
@Getter
@Setter
public class ItemEntity {

	/** 商品コード */
	@Column(name = "item_code")
	String itemCode;

	/** 商品名 */
	@Column(name = "item_name")
	String itemName;

	/** カテゴリコード */
	@Column(name = "category_code")
	String categoryCode;

	/** 分類コード */
	@Column(name = "classification_code")
	String classificationCode;

	/** シーズンコード */
	@Column(name = "season_code")
	String seasonCode;

	/** 仕入れ先ID（取引先ID） */
	@Column(name = "supplier_id")
	String supplierId;

	/** 仕入れ先名（取引先名） */
	@Column(name = "supplier_name")
	String supplierName;

	/** 仕入価格（税抜） */
	@Column(name = "purchase_price")
	BigDecimal purchasePrice;

	/** 仕入価格（税込） */
	@Column(name = "purchase_price_intax")
	BigDecimal purchasePriceIntax;

	/** 販売価格（税抜） */
	@Column(name = "selling_price")
	BigDecimal sellingPrice;

	/** 販売価格（税込） */
	@Column(name = "selling_price_intax")
	BigDecimal sellingPriceIntax;

}
