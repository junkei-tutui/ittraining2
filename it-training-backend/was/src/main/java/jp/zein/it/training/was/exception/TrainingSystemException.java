package jp.zein.it.training.was.exception;

import lombok.Getter;

/**
 * システム例外
 *
 * <p>
 * 当例外をRESTコントローラーでthrowすると、エラー応答電文がレスポンスされる。
 * </p>
 *
 */
@Getter
public class TrainingSystemException extends RuntimeException {
	/**
	 *
	 */
	private static final long serialVersionUID = -8945073945874790081L;

	/**
	 * エラーコード
	 */
	private final String errorCode;

	/**
	 * エラーメッセージ
	 */
	private final String errorMessage;

	/**
	 * コンストラクタ
	 *
	 * @param errorCode    エラーコード
	 * @param errorMessage エラーメッセージ
	 * @param cause        根本例外
	 */
	public TrainingSystemException(String errorCode, String errorMessage, Throwable cause) {
		super(String.format("システムエラーが発生。エラーコード=[%s], エラーメッセージ=[%s]", errorCode, errorMessage), cause);

		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
	}

	/**
	 * コンストラクタ
	 *
	 * @param errorCode    エラーコード
	 * @param errorMessage エラーメッセージ
	 */
	public TrainingSystemException(String errorCode, String errorMessage) {
		super(String.format("システムエラーが発生。エラーコード=[%s], エラーメッセージ=[%s]", errorCode, errorMessage));

		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
	}
}
