package my.bat.base.common.exception;

/**
 * アプリケーションエラー.
 */
public class AppException extends RuntimeException {

	/**
	 * コンストラクタ.<br>
	 *
	 * @param message エラーメッセージ
	 */
	public AppException(String message) {
		super(message);
	}

	/**
	 * コンストラクタ.<br>
	 *
	 * @param message エラーメッセージ
	 * @param cause 例外オブジェクト
	 */
	public AppException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * コンストラクタ.<br>
	 *
	 * @param cause 例外オブジェクト
	 */
	public AppException(Throwable cause) {
		super(cause);
	}

}
