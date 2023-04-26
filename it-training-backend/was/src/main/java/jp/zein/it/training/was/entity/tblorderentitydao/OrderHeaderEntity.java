package jp.zein.it.training.was.entity.tblorderentitydao;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.seasar.doma.Column;
import org.seasar.doma.Entity;

import lombok.Getter;
import lombok.Setter;

/**
 * 受注ヘッダー取得用エンティティ
 *
 */
@Entity
@Getter
@Setter
public class OrderHeaderEntity {

	/** 受注番号 */
	@Column(name = "order_no")
	Integer orderNo;

	/** 受注件名 */
	@Column(name = "order_subject")
	String orderSubject;

	/** クライアントID */
	@Column(name = "client_id")
	String clientId;

	/** クライアント名 */
	@Column(name = "client_name")
	String clientName;

	/** 受注日 */
	@Column(name = "order_date")
	LocalDate orderDate;

	/** 納期 */
	@Column(name = "delivery_date")
	LocalDate deliveryDate;

	/** 納品先郵便番号 */
	@Column(name = "delivery_zip")
	String deliveryZip;

	/** 納品先住所 */
	@Column(name = "delivery_address")
	String deliveryAddress;

	/** 受注受付ステータス */
	@Column(name = "order_status")
	String orderStatus;

	/** 合計受注金額（税抜） */
	@Column(name = "total_order_amount")
	BigDecimal totalOrderAmount;

	/** 合計受注金額（税込） */
	@Column(name = "total_order_amount_intax")
	BigDecimal totalOrderAmountIntax;

}
