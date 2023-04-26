package jp.zein.it.training.was.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;

/**
 * アプリケーション例外
 *
 * <p>
 * 当例外をRESTコントローラーでthrowすると、エラー応答電文がレスポンスされる。
 * </p>
 *
 */
@Getter
public class TrainingApplicationException extends RuntimeException {
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
	 */
	public TrainingApplicationException(String errorCode, String errorMessage) {
		super(String.format("アプリケーションエラーが発生。エラーコード=[%s], エラーメッセージ=[%s]", errorCode, errorMessage));

		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
	}

	/**
	 * コンストラクタ
	 *
	 * @param errorMessage エラーメッセージ
	 */
	public TrainingApplicationException(String errorMessage) {
		super(String.format("アプリケーションエラーが発生。 エラーメッセージ=[%s]", errorMessage));

		this.errorCode = HttpStatus.BAD_REQUEST.name();
		this.errorMessage = errorMessage;
	}

}
