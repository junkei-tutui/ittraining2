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
@Entity(listener = MstItemEntityListener.class, metamodel = @Metamodel)
@Table(name = "mst_item")
public class MstItemEntity {

    /** 商品コード */
    @Id
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

    /** 取引先ID */
    @Column(name = "partner_id")
    String partnerId;

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
     * Returns the itemName.
     *
     * @return the itemName
     */
    public String getItemName() {
        return itemName;
    }

    /**
     * Sets the itemName.
     *
     * @param itemName the itemName
     */
    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    /**
     * Returns the categoryCode.
     *
     * @return the categoryCode
     */
    public String getCategoryCode() {
        return categoryCode;
    }

    /**
     * Sets the categoryCode.
     *
     * @param categoryCode the categoryCode
     */
    public void setCategoryCode(String categoryCode) {
        this.categoryCode = categoryCode;
    }

    /**
     * Returns the classificationCode.
     *
     * @return the classificationCode
     */
    public String getClassificationCode() {
        return classificationCode;
    }

    /**
     * Sets the classificationCode.
     *
     * @param classificationCode the classificationCode
     */
    public void setClassificationCode(String classificationCode) {
        this.classificationCode = classificationCode;
    }

    /**
     * Returns the seasonCode.
     *
     * @return the seasonCode
     */
    public String getSeasonCode() {
        return seasonCode;
    }

    /**
     * Sets the seasonCode.
     *
     * @param seasonCode the seasonCode
     */
    public void setSeasonCode(String seasonCode) {
        this.seasonCode = seasonCode;
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