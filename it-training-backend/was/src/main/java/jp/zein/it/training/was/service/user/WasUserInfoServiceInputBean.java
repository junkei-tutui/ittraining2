package jp.zein.it.training.was.service.user;

import lombok.Data;

@Data
public class WasUserInfoServiceInputBean {
	/**
	 * ユーザーID
	 */
	private String userId;

	/**
	 * ユーザー名
	 */
	private String userName;

	/**
	 * ユーザー名（ふりがな）
	 */
	private String userNameKana;

	/**
	 * 言語コード
	 */
	private String languageCode;

	/**
	 * メールアドレス
	 */
	private String mailAddress;

}
