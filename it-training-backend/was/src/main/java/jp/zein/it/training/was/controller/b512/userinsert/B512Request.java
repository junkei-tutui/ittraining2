package jp.zein.it.training.was.controller.b512.userinsert;

import lombok.Data;

/**
 * B512:ユーザー登録用データ（ユーザー情報）
 *
 */
@Data
public class B512Request {

	/**
	 * ユーザーID
	 */
	private String userId;

	/**
	 * ユーザー名
	 */
	private String userName;

	/**
	 * 入力パスワード
	 */
	private String inputPassword;

}
