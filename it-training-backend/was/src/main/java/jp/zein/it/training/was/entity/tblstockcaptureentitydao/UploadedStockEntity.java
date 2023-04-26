package jp.zein.it.training.was.entity.tblstockcaptureentitydao;

import java.time.LocalDateTime;

import org.seasar.doma.Column;
import org.seasar.doma.Entity;
import org.seasar.doma.GeneratedValue;
import org.seasar.doma.GenerationType;
import org.seasar.doma.Id;

import lombok.Getter;
import lombok.Setter;

/**
 * 在庫取込結果取得用エンティティ
 *
 */
@Entity
@Getter
@Setter
public class UploadedStockEntity {

	/** 在庫取込管理ID */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "stock_capture_id")
	Long stockCaptureId;

	/** アップロード日時 */
	@Column(name = "upload_at")
	LocalDateTime uploadAt;

	/** アップロード担当者ID */
	@Column(name = "uploaded_user_id")
	String uploadedUserId;

	/** アップロード担当者名 */
	@Column(name = "uploaded_user_name")
	String uploadedUserName;

	/** 在庫ファイル名 */
	@Column(name = "filename_stock")
	String filenameStock;

	/** 引当後在庫ファイル名 */
	@Column(name = "filename_stock_result")
	String filenameStockResult;

	/** 引当不可リスト名 */
	@Column(name = "filename_cannotreserve")
	String filenameCannotreserve;

}
