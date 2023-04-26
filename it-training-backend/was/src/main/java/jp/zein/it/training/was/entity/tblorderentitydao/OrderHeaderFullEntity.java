package jp.zein.it.training.was.entity.tblorderentitydao;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import org.seasar.doma.Column;
import org.seasar.doma.Entity;

import lombok.Getter;
import lombok.Setter;

/**
 * 受注ヘッダー取得（全項目取得）用エンティティ
 *
 */
@Entity
@Getter
@Setter
public class OrderHeaderFullEntity {

	/** 受注番号 */
	@Column(name = "order_no")
	Integer orderNo;

	/** 受注件名 */
	@Column(name = "order_subject")
	String orderSubject;

	/** 取引先ID（クライアントID） */
	@Column(name = "partner_id")
	String partnerId;

	/** 取引先名（クライアント名） */
	@Column(name = "partner_name")
	String partnerName;

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

	/** 担当者 */
	@Column(name = "responsible_party")
	String responsibleParty;

	/** 受注受付ステータス */
	@Column(name = "order_status")
	String orderStatus;

	/** 合計受注金額（税抜） */
	@Column(name = "total_order_amount")
	BigDecimal totalOrderAmount;

	/** 合計受注金額（税込） */
	@Column(name = "total_order_amount_intax")
	BigDecimal totalOrderAmountIntax;

	/** 客先注文番号 */
	@Column(name = "client_order_no")
	String clientOrderNo;

	/** 備考 */
	@Column(name = "note")
	String note;

	/** 削除フラグ */
	@Column(name = "is_delete")
	Boolean isDelete;

	/** 登録日時 */
	@Column(name = "created_at")
	LocalDateTime createdAt;

	/** 登録者 */
	@Column(name = "created_by")
	String createdBy;

	/** 更新日時 */
	@Column(name = "modify_at")
	LocalDateTime modifyAt;

	/** 更新者 */
	@Column(name = "modify_by")
	String modifyBy;

}
