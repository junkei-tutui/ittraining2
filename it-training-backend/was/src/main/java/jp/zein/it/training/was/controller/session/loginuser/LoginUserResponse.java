package jp.zein.it.training.was.controller.session.loginuser;

import lombok.Data;

/**
 * B012:セッションユーザー情報取得用返却情報（セッション情報）
 *
 */
@Data
public class LoginUserResponse {

	/**
	 * ユーザーID
	 */
	private String userId;

	/**
	 * ユーザー名
	 */
	private String userName;

}
