package jp.zein.it.training.common.generate.entity;

import java.time.LocalDateTime;
import org.seasar.doma.Column;
import org.seasar.doma.Entity;
import org.seasar.doma.Id;
import org.seasar.doma.Metamodel;
import org.seasar.doma.Table;

/**
 * 
 */
@Entity(listener = MstPartnerEntityListener.class, metamodel = @Metamodel)
@Table(name = "mst_partner")
public class MstPartnerEntity {

    /** 取引先ID */
    @Id
    @Column(name = "partner_id")
    String partnerId;

    /** 取引先名 */
    @Column(name = "partner_name")
    String partnerName;

    /** 取引区分 */
    @Column(name = "partner_type")
    Integer partnerType;

    /** 郵便番号 */
    @Column(name = "zip")
    String zip;

    /** 住所 */
    @Column(name = "address")
    String address;

    /** 電話番号 */
    @Column(name = "tel_no")
    String telNo;

    /** FAX番号 */
    @Column(name = "fax_no")
    String faxNo;

    /** 担当者 */
    @Column(name = "responsible_party")
    String responsibleParty;

    /** メールアドレス */
    @Column(name = "mail_address")
    String mailAddress;

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
     * Returns the partnerName.
     *
     * @return the partnerName
     */
    public String getPartnerName() {
        return partnerName;
    }

    /**
     * Sets the partnerName.
     *
     * @param partnerName the partnerName
     */
    public void setPartnerName(String partnerName) {
        this.partnerName = partnerName;
    }

    /**
     * Returns the partnerType.
     *
     * @return the partnerType
     */
    public Integer getPartnerType() {
        return partnerType;
    }

    /**
     * Sets the partnerType.
     *
     * @param partnerType the partnerType
     */
    public void setPartnerType(Integer partnerType) {
        this.partnerType = partnerType;
    }

    /**
     * Returns the zip.
     *
     * @return the zip
     */
    public String getZip() {
        return zip;
    }

    /**
     * Sets the zip.
     *
     * @param zip the zip
     */
    public void setZip(String zip) {
        this.zip = zip;
    }

    /**
     * Returns the address.
     *
     * @return the address
     */
    public String getAddress() {
        return address;
    }

    /**
     * Sets the address.
     *
     * @param address the address
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Returns the telNo.
     *
     * @return the telNo
     */
    public String getTelNo() {
        return telNo;
    }

    /**
     * Sets the telNo.
     *
     * @param telNo the telNo
     */
    public void setTelNo(String telNo) {
        this.telNo = telNo;
    }

    /**
     * Returns the faxNo.
     *
     * @return the faxNo
     */
    public String getFaxNo() {
        return faxNo;
    }

    /**
     * Sets the faxNo.
     *
     * @param faxNo the faxNo
     */
    public void setFaxNo(String faxNo) {
        this.faxNo = faxNo;
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
     * Returns the mailAddress.
     *
     * @return the mailAddress
     */
    public String getMailAddress() {
        return mailAddress;
    }

    /**
     * Sets the mailAddress.
     *
     * @param mailAddress the mailAddress
     */
    public void setMailAddress(String mailAddress) {
        this.mailAddress = mailAddress;
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