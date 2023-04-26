package jp.zein.it.training.was.service.order.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.Data;

/**
 * 共通受注ヘッダーデータモデル
 *
 */
@Data
public class OrderHeaderDto {

	/** 受注番号 */
	private int orderNo;

	/** 受注件名 */
	private String orderSubject;

	/** クライアントID */
	private String clientId;

	/** クライアント名 */
	private String clientName;

	/** 受注日 */
	private LocalDate orderDate;

	/** 納期 */
	private LocalDate deliveryDate;

	/** 納品先郵便番号 */
	private String deliveryZip;

	/** 納品先住所 */
	private String deliveryAddress;

	/** 受注受付ステータス */
	private String orderStatus;

	/** 合計受注金額（税抜） */
	private BigDecimal totalOrderAmount;

	/** 合計受注金額（税込） */
	private BigDecimal totalOrderAmountIntax;

}
