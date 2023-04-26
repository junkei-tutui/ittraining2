package jp.zein.it.training.was.controller.session.login;

import lombok.Data;

/**
 * B011：ログイン用リクエスト
 */
@Data
public class LoginRequest {

	/**
	 * ユーザーID
	 */
	private String userId;

	/**
	 * パスワード
	 */
	private String password;

}
