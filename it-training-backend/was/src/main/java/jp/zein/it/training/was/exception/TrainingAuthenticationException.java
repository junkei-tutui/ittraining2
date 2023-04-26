package jp.zein.it.training.was.exception;

import org.springframework.security.core.AuthenticationException;

import lombok.Getter;

/**
 * 認証失敗例外クラス
 *
 */
@Getter
public class TrainingAuthenticationException extends AuthenticationException {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * エラーコード
	 */
	private final String errorCode;

	/**
	 * エラーメッセージ
	 */
	private final String errorMessage;

	public TrainingAuthenticationException(String errorCode, String errorMessage) {
		super(String.format("認証エラーが発生。エラーコード=[%s], エラーメッセージ=[%s]", errorCode, errorMessage));

		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
	}

}
