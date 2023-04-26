package jp.zein.it.training.common.generate.entity;

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
@Entity(listener = TblStockCaptureEntityListener.class, metamodel = @Metamodel)
@Table(name = "tbl_stock_capture")
public class TblStockCaptureEntity {

    /** 在庫取込管理ID */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "stock_capture_id")
    Long stockCaptureId;

    /** アップロード日時 */
    @Column(name = "upload_at")
    LocalDateTime uploadAt;

    /** 在庫ファイル名 */
    @Column(name = "filename_stock")
    String filenameStock;

    /** 引当後在庫ファイル名 */
    @Column(name = "filename_stock_result")
    String filenameStockResult;

    /** 引当不可リスト名 */
    @Column(name = "filename_cannotreserve")
    String filenameCannotreserve;

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
     * Returns the stockCaptureId.
     *
     * @return the stockCaptureId
     */
    public Long getStockCaptureId() {
        return stockCaptureId;
    }

    /**
     * Sets the stockCaptureId.
     *
     * @param stockCaptureId the stockCaptureId
     */
    public void setStockCaptureId(Long stockCaptureId) {
        this.stockCaptureId = stockCaptureId;
    }

    /**
     * Returns the uploadAt.
     *
     * @return the uploadAt
     */
    public LocalDateTime getUploadAt() {
        return uploadAt;
    }

    /**
     * Sets the uploadAt.
     *
     * @param uploadAt the uploadAt
     */
    public void setUploadAt(LocalDateTime uploadAt) {
        this.uploadAt = uploadAt;
    }

    /**
     * Returns the filenameStock.
     *
     * @return the filenameStock
     */
    public String getFilenameStock() {
        return filenameStock;
    }

    /**
     * Sets the filenameStock.
     *
     * @param filenameStock the filenameStock
     */
    public void setFilenameStock(String filenameStock) {
        this.filenameStock = filenameStock;
    }

    /**
     * Returns the filenameStockResult.
     *
     * @return the filenameStockResult
     */
    public String getFilenameStockResult() {
        return filenameStockResult;
    }

    /**
     * Sets the filenameStockResult.
     *
     * @param filenameStockResult the filenameStockResult
     */
    public void setFilenameStockResult(String filenameStockResult) {
        this.filenameStockResult = filenameStockResult;
    }

    /**
     * Returns the filenameCannotreserve.
     *
     * @return the filenameCannotreserve
     */
    public String getFilenameCannotreserve() {
        return filenameCannotreserve;
    }

    /**
     * Sets the filenameCannotreserve.
     *
     * @param filenameCannotreserve the filenameCannotreserve
     */
    public void setFilenameCannotreserve(String filenameCannotreserve) {
        this.filenameCannotreserve = filenameCannotreserve;
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