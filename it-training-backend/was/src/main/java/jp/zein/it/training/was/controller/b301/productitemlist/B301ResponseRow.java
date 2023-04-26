package jp.zein.it.training.was.controller.b301.productitemlist;

import java.math.BigDecimal;

import jp.zein.it.training.was.service.order.dto.OrderHeaderDto;
import lombok.Data;

/**
 * B301:商品一覧取得用行データ（商品マスタ）
 * <p>
 * 共通受注データモデル（{@link OrderHeaderDto}）の派生
 * <p>
 */
@Data
public class B301ResponseRow {

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

	/** 仕入先ID */
	private String supplierId;

	/** 仕入先名 */
	private String supplierName;

	/** 仕入価格（税抜） */
	private BigDecimal purchasePrice;

	/** 仕入価格（税込） */
	private BigDecimal purchasePriceIntax;

	/** 販売価格（税抜） */
	private BigDecimal sellingPrice;

	/** 販売価格（税込） */
	private BigDecimal sellingPriceIntax;

	/** 削除可能フラグ */
	private Boolean isCanDeleted;

}
