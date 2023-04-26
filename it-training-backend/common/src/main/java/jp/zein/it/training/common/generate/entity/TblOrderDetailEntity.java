package jp.zein.it.training.common.generate.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import org.seasar.doma.Column;
import org.seasar.doma.Entity;
import org.seasar.doma.Id;
import org.seasar.doma.Metamodel;
import org.seasar.doma.Table;

/**
 * 
 */
@Entity(listener = TblOrderDetailEntityListener.class, metamodel = @Metamodel)
@Table(name = "tbl_order_detail")
public class TblOrderDetailEntity {

    /** 受注番号 */
    @Id
    @Column(name = "order_no")
    Integer orderNo;

    /** 明細番号 */
    @Id
    @Column(name = "line_no")
    Integer lineNo;

    /** SKUコード */
    @Column(name = "product_code")
    String productCode;

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
     * Returns the lineNo.
     *
     * @return the lineNo
     */
    public Integer getLineNo() {
        return lineNo;
    }

    /**
     * Sets the lineNo.
     *
     * @param lineNo the lineNo
     */
    public void setLineNo(Integer lineNo) {
        this.lineNo = lineNo;
    }

    /**
     * Returns the productCode.
     *
     * @return the productCode
     */
    public String getProductCode() {
        return productCode;
    }

    /**
     * Sets the productCode.
     *
     * @param productCode the productCode
     */
    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    /**
     * Returns the quantity.
     *
     * @return the quantity
     */
    public BigDecimal getQuantity() {
        return quantity;
    }

    /**
     * Sets the quantity.
     *
     * @param quantity the quantity
     */
    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    /**
     * Returns the sellingPrice.
     *
     * @return the sellingPrice
     */
    public BigDecimal getSellingPrice() {
        return sellingPrice;
    }

    /**
     * Sets the sellingPrice.
     *
     * @param sellingPrice the sellingPrice
     */
    public void setSellingPrice(BigDecimal sellingPrice) {
        this.sellingPrice = sellingPrice;
    }

    /**
     * Returns the sellingPriceIntax.
     *
     * @return the sellingPriceIntax
     */
    public BigDecimal getSellingPriceIntax() {
        return sellingPriceIntax;
    }

    /**
     * Sets the sellingPriceIntax.
     *
     * @param sellingPriceIntax the sellingPriceIntax
     */
    public void setSellingPriceIntax(BigDecimal sellingPriceIntax) {
        this.sellingPriceIntax = sellingPriceIntax;
    }

    /**
     * Returns the orderLineStatus.
     *
     * @return the orderLineStatus
     */
    public String getOrderLineStatus() {
        return orderLineStatus;
    }

    /**
     * Sets the orderLineStatus.
     *
     * @param orderLineStatus the orderLineStatus
     */
    public void setOrderLineStatus(String orderLineStatus) {
        this.orderLineStatus = orderLineStatus;
    }

    /**
     * Returns the subtotalSellingPrice.
     *
     * @return the subtotalSellingPrice
     */
    public BigDecimal getSubtotalSellingPrice() {
        return subtotalSellingPrice;
    }

    /**
     * Sets the subtotalSellingPrice.
     *
     * @param subtotalSellingPrice the subtotalSellingPrice
     */
    public void setSubtotalSellingPrice(BigDecimal subtotalSellingPrice) {
        this.subtotalSellingPrice = subtotalSellingPrice;
    }

    /**
     * Returns the subtotalSellingPriceIntax.
     *
     * @return the subtotalSellingPriceIntax
     */
    public BigDecimal getSubtotalSellingPriceIntax() {
        return subtotalSellingPriceIntax;
    }

    /**
     * Sets the subtotalSellingPriceIntax.
     *
     * @param subtotalSellingPriceIntax the subtotalSellingPriceIntax
     */
    public void setSubtotalSellingPriceIntax(BigDecimal subtotalSellingPriceIntax) {
        this.subtotalSellingPriceIntax = subtotalSellingPriceIntax;
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