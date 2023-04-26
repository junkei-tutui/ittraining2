package jp.zein.it.training.was.service.order.dto;

import java.math.BigDecimal;

import lombok.Data;

/**
 * 共通受注明細データモデル
 *
 */
@Data
public class OrderDetailDto {

	/** 明細番号 */
	private int lineNo;

	/** 商品（SKU）コード */
	private String productCode;

	/** 品目コード */
	private String itemCode;

	/** 品目名 */
	private String itemName;

	/** カラーコード */
	private String colorCode;

	/** カラー名 */
	private String colorName;

	/** サイズコード */
	private String sizeCode;

	/** サイズ名 */
	private String sizeName;

	/** 数量 */
	private BigDecimal quantity;

	/** 販売価格（税抜） */
	private BigDecimal sellingPrice;

	/** 販売価格（税込） */
	private BigDecimal sellingPriceIntax;

	/** 受注明細ステータス */
	private String orderLineStatus;

	/** 小計販売価格（税抜） */
	private BigDecimal subtotalSellingPrice;

	/** 小計販売価格（税込） */
	private BigDecimal subtotalSellingPriceIntax;

}
