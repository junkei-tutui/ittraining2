package jp.zein.it.training.was.controller.b513.userupdate;

import lombok.Data;

/**
 * B513:ユーザー更新用データ（ユーザー情報）
 * 
 */
@Data
public class B513Request {

	/**
	 * ユーザーID
	 */
	private String userId;

	/**
	 * ユーザー名
	 */
	private String userName;

	/**
	 * パスワード変更フラグ
	 */
	private boolean isChangePassword;

	/**
	 * 入力パスワード
	 */
	private String inputPassword;

}
