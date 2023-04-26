package jp.zein.it.training.common.generate.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import org.seasar.doma.Column;
import org.seasar.doma.Entity;
import org.seasar.doma.GeneratedValue;
import org.seasar.doma.GenerationType;
import org.seasar.doma.Id;
import org.seasar.doma.Metamodel;
import org.seasar.doma.Table;

/**
 * 
 */
@Entity(listener = TblOrderEntityListener.class, metamodel = @Metamodel)
@Table(name = "tbl_order")
public class TblOrderEntity {

    /** 受注番号 */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_no")
    Integer orderNo;

    /** 受注件名 */
    @Column(name = "order_subject")
    String orderSubject;

    /** 取引先ID */
    @Column(name = "partner_id")
    String partnerId;

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

    /**
     * Returns the orderNo.
     *
     * @return the orderNo
     */
    public Integer getOrderNo() {
        return orderNo;
    }

    /**
     * Sets the orderNo.
     *
     * @param orderNo the orderNo
     */
    public void setOrderNo(Integer orderNo) {
        this.orderNo = orderNo;
    }

    /**
     * Returns the orderSubject.
     *
     * @return the orderSubject
     */
    public String getOrderSubject() {
        return orderSubject;
    }

    /**
     * Sets the orderSubject.
     *
     * @param orderSubject the orderSubject
     */
    public void setOrderSubject(String orderSubject) {
        this.orderSubject = orderSubject;
    }

    /**
     * Returns the partnerId.
     *
     * @return the partnerId
     */
    public String getPartnerId() {
        return partnerId;
    }

    /**
     * Sets the partnerId.
     *
     * @param partnerId the partnerId
     */
    public void setPartnerId(String partnerId) {
        this.partnerId = partnerId;
    }

    /**
     * Returns the orderDate.
     *
     * @return the orderDate
     */
    public LocalDate getOrderDate() {
        return orderDate;
    }

    /**
     * Sets the orderDate.
     *
     * @param orderDate the orderDate
     */
    public void setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
    }

    /**
     * Returns the deliveryDate.
     *
     * @return the deliveryDate
     */
    public LocalDate getDeliveryDate() {
        return deliveryDate;
    }

    /**
     * Sets the deliveryDate.
     *
     * @param deliveryDate the deliveryDate
     */
    public void setDeliveryDate(LocalDate deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    /**
     * Returns the deliveryZip.
     *
     * @return the deliveryZip
     */
    public String getDeliveryZip() {
        return deliveryZip;
    }

    /**
     * Sets the deliveryZip.
     *
     * @param deliveryZip the deliveryZip
     */
    public void setDeliveryZip(String deliveryZip) {
        this.deliveryZip = deliveryZip;
    }

    /**
     * Returns the deliveryAddress.
     *
     * @return the deliveryAddress
     */
    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    /**
     * Sets the deliveryAddress.
     *
     * @param deliveryAddress the deliveryAddress
     */
    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    /**
     * Returns the responsibleParty.
     *
     * @return the responsibleParty
     */
    public String getResponsibleParty() {
        return responsibleParty;
    }

    /**
     * Sets the responsibleParty.
     *
     * @param responsibleParty the responsibleParty
     */
    public void setResponsibleParty(String responsibleParty) {
        this.responsibleParty = responsibleParty;
    }

    /**
     * Returns the orderStatus.
     *
     * @return the orderStatus
     */
    public String getOrderStatus() {
        return orderStatus;
    }

    /**
     * Sets the orderStatus.
     *
     * @param orderStatus the orderStatus
     */
    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    /**
     * Returns the totalOrderAmount.
     *
     * @return the totalOrderAmount
     */
    public BigDecimal getTotalOrderAmount() {
        return totalOrderAmount;
    }

    /**
     * Sets the totalOrderAmount.
     *
     * @param totalOrderAmount the totalOrderAmount
     */
    public void setTotalOrderAmount(BigDecimal totalOrderAmount) {
        this.totalOrderAmount = totalOrderAmount;
    }

    /**
     * Returns the totalOrderAmountIntax.
     *
     * @return the totalOrderAmountIntax
     */
    public BigDecimal getTotalOrderAmountIntax() {
        return totalOrderAmountIntax;
    }

    /**
     * Sets the totalOrderAmountIntax.
     *
     * @param totalOrderAmountIntax the totalOrderAmountIntax
     */
    public void setTotalOrderAmountIntax(BigDecimal totalOrderAmountIntax) {
        this.totalOrderAmountIntax = totalOrderAmountIntax;
    }

    /**
     * Returns the clientOrderNo.
     *
     * @return the clientOrderNo
     */
    public String getClientOrderNo() {
        return clientOrderNo;
    }

    /**
     * Sets the clientOrderNo.
     *
     * @param clientOrderNo the clientOrderNo
     */
    public void setClientOrderNo(String clientOrderNo) {
        this.clientOrderNo = clientOrderNo;
    }

    /**
     * Returns the note.
     *
     * @return the note
     */
    public String getNote() {
        return note;
    }

    /**
     * Sets the note.
     *
     * @param note the note
     */
    public void setNote(String note) {
        this.note = note;
    }

    /**
     * Returns the isDelete.
     *
     * @return the isDelete
     */
    public Boolean getIsDelete() {
        return isDelete;
    }

    /**
     * Sets the isDelete.
     *
     * @param isDelete the isDelete
     */
    public void setIsDelete(Boolean isDelete) {
        this.isDelete = isDelete;
    }

    /**
     * Returns the createdAt.
     *
     * @return the createdAt
     */
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    /**
     * Sets the createdAt.
     *
     * @param createdAt the createdAt
     */
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * Returns the createdBy.
     *
     * @return the createdBy
     */
    public String getCreatedBy() {
        return createdBy;
    }

    /**
     * Sets the createdBy.
     *
     * @param createdBy the createdBy
     */
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    /**
     * Returns the modifyAt.
     *
     * @return the modifyAt
     */
    public LocalDateTime getModifyAt() {
        return modifyAt;
    }

    /**
     * Sets the modifyAt.
     *
     * @param modifyAt the modifyAt
     */
    public void setModifyAt(LocalDateTime modifyAt) {
        this.modifyAt = modifyAt;
    }

    /**
     * Returns the modifyBy.
     *
     * @return the modifyBy
     */
    public String getModifyBy() {
        return modifyBy;
    }

    /**
     * Sets the modifyBy.
     *
     * @param modifyBy the modifyBy
     */
    public void setModifyBy(String modifyBy) {
        this.modifyBy = modifyBy;
    }
}