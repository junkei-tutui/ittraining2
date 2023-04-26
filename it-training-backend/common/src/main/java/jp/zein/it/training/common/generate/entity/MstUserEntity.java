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
@Entity(listener = MstUserEntityListener.class, metamodel = @Metamodel)
@Table(name = "mst_user")
public class MstUserEntity {

    /** ユーザーID */
    @Id
    @Column(name = "user_id")
    String userId;

    /** ユーザーパスワード */
    @Column(name = "user_password")
    String userPassword;

    /** ユーザー名 */
    @Column(name = "user_name")
    String userName;

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
     * Returns the userId.
     *
     * @return the userId
     */
    public String getUserId() {
        return userId;
    }

    /**
     * Sets the userId.
     *
     * @param userId the userId
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * Returns the userPassword.
     *
     * @return the userPassword
     */
    public String getUserPassword() {
        return userPassword;
    }

    /**
     * Sets the userPassword.
     *
     * @param userPassword the userPassword
     */
    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    /**
     * Returns the userName.
     *
     * @return the userName
     */
    public String getUserName() {
        return userName;
    }

    /**
     * Sets the userName.
     *
     * @param userName the userName
     */
    public void setUserName(String userName) {
        this.userName = userName;
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