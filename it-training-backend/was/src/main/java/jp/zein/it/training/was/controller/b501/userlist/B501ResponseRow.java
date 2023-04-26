package jp.zein.it.training.was.controller.b501.userlist;

import lombok.Data;

/**
 * B501:ユーザー検索一覧取得用行データ（ユーザーマスタ）
 *
 */
@Data
public class B501ResponseRow {

	/**
	 * ユーザーID
	 */
	private String userId;

	/**
	 * ユーザー名
	 */
	private String userName;

}
