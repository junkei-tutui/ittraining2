package jp.zein.it.training.was.controller.common.error;

import lombok.Data;

/**
 * アプリケーションエラーおよびシステムエラー時の応答電文
 *
 */
@Data
public class ErrorResponse {
	/**
	 * エラーコード
	 */
	private final String errorCode;

	/**
	 * エラーメッセージ
	 */
	private final String errorMessage;
}
