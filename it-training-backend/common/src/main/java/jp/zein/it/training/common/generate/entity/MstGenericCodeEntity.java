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
@Entity(listener = MstGenericCodeEntityListener.class, metamodel = @Metamodel)
@Table(name = "mst_generic_code")
public class MstGenericCodeEntity {

    /** 汎用コード種類ID */
    @Id
    @Column(name = "generic_code_type_id")
    String genericCodeTypeId;

    /** 汎用コード種類名 */
    @Column(name = "generic_code_type_name")
    String genericCodeTypeName;

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
     * Returns the genericCodeTypeId.
     *
     * @return the genericCodeTypeId
     */
    public String getGenericCodeTypeId() {
        return genericCodeTypeId;
    }

    /**
     * Sets the genericCodeTypeId.
     *
     * @param genericCodeTypeId the genericCodeTypeId
     */
    public void setGenericCodeTypeId(String genericCodeTypeId) {
        this.genericCodeTypeId = genericCodeTypeId;
    }

    /**
     * Returns the genericCodeTypeName.
     *
     * @return the genericCodeTypeName
     */
    public String getGenericCodeTypeName() {
        return genericCodeTypeName;
    }

    /**
     * Sets the genericCodeTypeName.
     *
     * @param genericCodeTypeName the genericCodeTypeName
     */
    public void setGenericCodeTypeName(String genericCodeTypeName) {
        this.genericCodeTypeName = genericCodeTypeName;
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