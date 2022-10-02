package my.bat.base.common.log;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * ログサービス.
 */
public class LogService {
	/** スレッドローカルインスタンス管理. */
	protected static final ThreadLocal<String> threadLocal = new ThreadLocal<String>();

	/** ログオブジェクト. */
	protected final Logger log;

	/**
	 * コンストラクタ.
	 *
	 * @param clazz クラス
	 */
	public LogService(Class<?> clazz) {
		log = LoggerFactory.getLogger(clazz);
	}

	/**
	 * 固定メッセージ文字列生成.<br>
	 * 固定の文字列メッセージを生成します。 [メッセージ]
	 *
	 * @param message メッセージ
	 * @return String 固定メッセージ文字列
	 */
	public String createMessage(String message) {
		StringBuffer strBuf = new StringBuffer();
		String preMessage = threadLocal.get();
		if (!StringUtils.isEmpty(preMessage)) {
			strBuf.append(preMessage);
			strBuf.append(" ");
		}
		strBuf.append(message);
		return strBuf.toString();
	}

	/**
	 * ログ出力(ERROR).<br>
	 * ERRORレベルのメッセージをログに出力する。
	 *
	 * @param message メッセージ
	 * @since 1.0
	 */
	public void error(String message) {
		// ログ出力
		log.error(createMessage(message));
	}
//
//	/**
//	 * ログ出力(ERROR).<br>
//	 * ERRORレベルのメッセージをログに出力する。
//	 *
//	 * @param message メッセージ
//	 * @param arguments プレースホルダを置き換える文字
//	 */
//	public void error(String message, Object... arguments) {
//		// ログ出力
//		log.error(createMessage(message),arguments);
//	}

	/**
	 * ログ出力(ERROR).<br>
	 * ERRORレベルのメッセージをログに出力する。
	 *
	 * @param message メッセージ
	 * @param e エラーオブジェクト
	 * @since 1.0
	 */
	public void error(String message, Throwable e) {
		// ログ出力
		log.error(createMessage(message), e);
	}

	/**
	 * ログ出力(WARN).<br>
	 * WARNレベルのメッセージをログに出力する。
	 *
	 * @param message メッセージ
	 * @since 1.0
	 */
	public void warn(String message) {
		// ログ出力
		log.warn(createMessage(message));
	}

//	/**
//	 * ログ出力(WARN).<br>
//	 * WARNレベルのメッセージをログに出力する。
//	 *
//	 * @param message メッセージ
//	 * @param arguments プレースホルダを置き換える文字
//	 */
//	public void warn(String message, Object... arguments) {
//		// ログ出力
//		log.warn(createMessage(message),arguments);
//	}

	/**
	 * ログ出力(WARN).<br>
	 * WARNレベルのメッセージをログに出力する。
	 *
	 * @param message メッセージ
	 * @param e エラーオブジェクト
	 */
	public void warn(String message, Throwable e) {
		// ログ出力
		log.warn(createMessage(message), e);
	}

	/**
	 * ログ出力(INFO).<br>
	 * INFOレベルのメッセージをログに出力する。
	 *
	 * @param message メッセージ
	 * @since 1.0
	 */
	public void info(String message) {
		// ログ出力
		if (isInfoEnabled()) {
			log.info(createMessage(message));
		}
	}

//	/**
//	 * ログ出力(INFO).<br>
//	 * INFOレベルのメッセージをログに出力する。
//	 *
//	 * @param message メッセージ
//	 * @param arguments プレースホルダを置き換える文字
//	 */
//	public void info(String message, Object... arguments) {
//		// ログ出力
//		if (isInfoEnabled()) {
//			log.info(createMessage(message), arguments);
//		}
//	}

	/**
	 * ログ出力(DEBUG).<br>
	 * DEBUGレベルのメッセージをログに出力する。
	 *
	 * @param message メッセージ
	 * @since 1.0
	 */
	public void debug(String message) {
		// ログ出力
		if (isDebugEnabled()) {
			log.debug(createMessage(message));
		}
	}

	/**
	 * ログ出力(DEBUG).<br>
	 * DEBUGレベルのメッセージをログに出力する。
	 *
	 * @param message メッセージ
	 * @param arguments プレースホルダを置き換える文字
	 */
	public void debug(String message, Object... arguments) {
		// ログ出力
		if (isDebugEnabled()) {
			log.debug(createMessage(message), arguments);
		}
	}

	/**
	 * DEBUGレベルのログを出力するかどうか判定します.
	 *
	 * @return true:DEBUGレベルのログを出力する false:DEBUGレベルのログを出力しない
	 */
	public boolean isDebugEnabled() {
		return log.isDebugEnabled();
	}

	/**
	 * INFOレベルのログを出力するかどうか判定します.
	 *
	 * @return true INFO:レベルのログを出力する false:INFOレベルのログを出力しない
	 */
	public boolean isInfoEnabled() {
		return log.isInfoEnabled();
	}

	/**
	 * ログ固定文字列を設定する.
	 *
	 * @param preMessage ログ固定文字列
	 */
	public static void setPreMessage(String preMessage) {
		threadLocal.set(preMessage);
	}

	/**
	 * ログ固定文字列をクリアする.
	 */
	public static void removePreMessage() {
		threadLocal.remove();
	}
}
