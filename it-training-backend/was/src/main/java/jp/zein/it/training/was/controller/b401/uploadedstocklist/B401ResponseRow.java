package jp.zein.it.training.was.controller.b401.uploadedstocklist;

import java.time.LocalDateTime;

import lombok.Data;

/**
 * B401:在庫取込結果一覧取得用行データ（在庫取込管理テーブル）
 *
 */
@Data
public class B401ResponseRow {

	/** 在庫取込管理ID */
	private Long stockCaptureId;

	/** アップロード日時 */
	private LocalDateTime uploadAt;

	/** 担当者名 */
	private String uploadedUserName;

	/** 在庫ファイル名 */
	private String filenameStock;

	/** 引当後在庫ファイル名 */
	private String filenameStockResult;

	/** 引当不可リスト名 */
	private String filenameCannotreserve;

}
