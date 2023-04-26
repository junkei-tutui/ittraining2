package jp.zein.it.training.was.controller.common.exceptionhandler;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import jp.zein.it.training.was.controller.common.error.ErrorResponse;
import jp.zein.it.training.was.exception.TrainingApplicationException;
import jp.zein.it.training.was.exception.TrainingSystemException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 例外ハンドラークラス
 * <p>
 * 例外処理をクラス単位でハンドリングしてHTTPエラーレスポンスを返す。
 * <p>
 */
@RestControllerAdvice
@RequiredArgsConstructor
@Slf4j
public class TrainingExceptionHandler {

	/**
	 * 研修用アプリケーション例外ハンドラー
	 * <p>
	 * 当例外クラスでキャッチした場合は「400 Bad Request」でレスポンスする。
	 * <p>
	 * 
	 * @param e   発生した例外
	 * @param req 例外が発生した処理のリクエスト情報
	 * @return 例外により設定されたHTTPレスポンス情報
	 */
	@ExceptionHandler({ TrainingApplicationException.class })
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ErrorResponse handleApplicationException(TrainingApplicationException e, WebRequest req) {

		log.info("アプリケーション例外が発生しました。 エラーコード={}, エラーメッセージ{}", e.getErrorCode(), e.getErrorMessage());

		return new ErrorResponse(e.getErrorCode(), e.getErrorMessage());

	}

	/**
	 * 研修用システム例外ハンドラー
	 * <p>
	 * 当例外クラスでキャッチした場合は「500 Internal Server Error」でレスポンスする。
	 * <p>
	 * 
	 * @param e   発生した例外
	 * @param req 例外が発生した処理のリクエスト情報
	 * @return 例外により設定されたHTTPレスポンス情報
	 */
	@ExceptionHandler({ TrainingSystemException.class })
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public ErrorResponse handleSystemException(TrainingSystemException e, WebRequest req) {

		log.error("システム例外が発生しました。 ", e);

		return new ErrorResponse(e.getErrorCode(), e.getErrorMessage());

	}

	/**
	 * 認可エラーハンドラー
	 * <p>
	 * 当例外クラスでキャッチした場合は「403 Forbidden」でレスポンスする。
	 * <p>
	 * 
	 * @param e   発生した例外
	 * @param req 例外が発生した処理のリクエスト情報
	 * @return 例外により設定されたHTTPレスポンス情報
	 */
	@ExceptionHandler({ AccessDeniedException.class })
	@ResponseStatus(HttpStatus.FORBIDDEN)
	public ErrorResponse handleAccessDeniedException(AccessDeniedException e, WebRequest req) {

		log.error("認可エラーが発生しました。", e);

		return new ErrorResponse(HttpStatus.FORBIDDEN.name(), "認可エラーが発生しました。");

	}

	/**
	 * その他システム例外ハンドラー
	 * <p>
	 * 当例外クラスでキャッチした場合は「500 Internal Server Error」でレスポンスする。
	 * <p>
	 * 
	 * @param e   発生した例外
	 * @param req 例外が発生した処理のリクエスト情報
	 * @return 例外により設定されたHTTPレスポンス情報
	 */
	@ExceptionHandler({ Exception.class })
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public ErrorResponse handleSystemException(Exception e, WebRequest req) {

		log.error("システム例外が発生しました。", e);

		return new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.name(), "システム例外が発生しました。");

	}
}
