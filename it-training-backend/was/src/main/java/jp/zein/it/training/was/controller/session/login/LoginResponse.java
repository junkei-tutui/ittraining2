package jp.zein.it.training.was.controller.session.login;

import lombok.Data;

/**
 * B011：ログイン用返却情報（セッション情報）
 */
@Data
public class LoginResponse {

	/**
	 * シリアルバージョン
	 */
	@SuppressWarnings("unused")
	private static final long serialVersionUID = 1L;

	/**
	 * ユーザーID
	 */
	private String userId;

	/**
	 * ユーザー名
	 */
	private String userName;

}
