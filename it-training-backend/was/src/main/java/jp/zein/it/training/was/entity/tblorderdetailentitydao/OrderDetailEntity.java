package jp.zein.it.training.was.entity.tblorderdetailentitydao;

import java.math.BigDecimal;

import org.seasar.doma.Column;
import org.seasar.doma.Entity;

import lombok.Getter;
import lombok.Setter;

/**
 * 受注明細取得用エンティティ
 *
 */
@Entity
@Getter
@Setter
public class OrderDetailEntity {

	/** 受注番号 */
	@Column(name = "order_no")
	Integer orderNo;

	/** 明細番号 */
	@Column(name = "line_no")
	Integer lineNo;

	/** 商品コード */
	@Column(name = "product_code")
	String productCode;

	/** 品目コード */
	@Column(name = "item_code")
	String itemCode;

	/** 品目名 */
	@Column(name = "item_name")
	String itemName;

	/** カラーコード */
	@Column(name = "color_code")
	String colorCode;

	/** カラー名 */
	@Column(name = "color_name")
	String colorName;

	/** サイズコード */
	@Column(name = "size_code")
	String sizeCode;

	/** サイズ名 */
	@Column(name = "size_name")
	String sizeName;

	/** 数量 */
	@Column(name = "quantity")
	BigDecimal quantity;

	/** 販売価格（税抜） */
	@Column(name = "selling_price")
	BigDecimal sellingPrice;

	/** 販売価格（税込） */
	@Column(name = "selling_price_intax")
	BigDecimal sellingPriceIntax;

	/** 受注明細ステータス */
	@Column(name = "order_line_status")
	String orderLineStatus;

	/** 小計販売価格（税抜） */
	@Column(name = "subtotal_selling_price")
	BigDecimal subtotalSellingPrice;

	/** 小計販売価格（税込） */
	@Column(name = "subtotal_selling_price_intax")
	BigDecimal subtotalSellingPriceIntax;

}
