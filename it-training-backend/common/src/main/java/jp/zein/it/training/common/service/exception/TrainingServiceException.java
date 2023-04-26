package jp.zein.it.training.common.service.exception;

/**
 * サービスで発生した例外
 *
 */
public class TrainingServiceException extends RuntimeException {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	public TrainingServiceException(String message) {
		super(message);
	}

	public TrainingServiceException(String format, Object[] parameters) {
		super(String.format(format, parameters));
	}

	public TrainingServiceException(String message, Throwable cause) {
		super(message, cause);
	}

	public TrainingServiceException(String format, Object[] parameters, Throwable cause) {
		super(String.format(format, parameters), cause);
	}

}
