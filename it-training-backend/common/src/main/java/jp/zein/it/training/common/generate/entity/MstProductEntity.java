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
@Entity(listener = MstProductEntityListener.class, metamodel = @Metamodel)
@Table(name = "mst_product")
public class MstProductEntity {

    /** SKUコード */
    @Id
    @Column(name = "product_code")
    String productCode;

    /** 商品コード */
    @Column(name = "item_code")
    String itemCode;

    /** カラーコード */
    @Column(name = "color_code")
    String colorCode;

    /** サイズコード */
    @Column(name = "size_code")
    String sizeCode;

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
     * Returns the itemCode.
     *
     * @return the itemCode
     */
    public String getItemCode() {
        return itemCode;
    }

    /**
     * Sets the itemCode.
     *
     * @param itemCode the itemCode
     */
    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    /**
     * Returns the colorCode.
     *
     * @return the colorCode
     */
    public String getColorCode() {
        return colorCode;
    }

    /**
     * Sets the colorCode.
     *
     * @param colorCode the colorCode
     */
    public void setColorCode(String colorCode) {
        this.colorCode = colorCode;
    }

    /**
     * Returns the sizeCode.
     *
     * @return the sizeCode
     */
    public String getSizeCode() {
        return sizeCode;
    }

    /**
     * Sets the sizeCode.
     *
     * @param sizeCode the sizeCode
     */
    public void setSizeCode(String sizeCode) {
        this.sizeCode = sizeCode;
    }

    /**
     * Returns the purchasePrice.
     *
     * @return the purchasePrice
     */
    public BigDecimal getPurchasePrice() {
        return purchasePrice;
    }

    /**
     * Sets the purchasePrice.
     *
     * @param purchasePrice the purchasePrice
     */
    public void setPurchasePrice(BigDecimal purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

    /**
     * Returns the purchasePriceIntax.
     *
     * @return the purchasePriceIntax
     */
    public BigDecimal getPurchasePriceIntax() {
        return purchasePriceIntax;
    }

    /**
     * Sets the purchasePriceIntax.
     *
     * @param purchasePriceIntax the purchasePriceIntax
     */
    public void setPurchasePriceIntax(BigDecimal purchasePriceIntax) {
        this.purchasePriceIntax = purchasePriceIntax;
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