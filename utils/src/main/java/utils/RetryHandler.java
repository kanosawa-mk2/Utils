package utils;

import java.util.ArrayList;
import java.util.List;

/**
 * リトライハンドラ.
 *
 */
public class RetryHandler {

	/** デフォルトリトライ対象例外 */
	static final Class<? extends Throwable> DEFAULT_CATCH_CLASS = Throwable.class;

	/** デフォルトリトライ間隔 */
	static final long DEFAULT_SLEEP_TIME = 0L;

	/** インスタンス生成禁止 */
	private RetryHandler() {
	}

	/**
	 * リトライ実行.
	 *
	 * @param retryCount リトライ回数
	 * @param runnable タスク
	 * @throws RetryException リトライ超過エラー
	 */
	public static void retry(int retryCount, RetryConsumer runnable) throws RetryException {
		retry(retryCount, runnable, DEFAULT_CATCH_CLASS);
	}

	/**
	 * リトライ実行.
	 *
	 * @param retryCount リトライ回数
	 * @param runnable タスク
	 * @param catchClass リトライ対象エラー
	 * @throws RetryException リトライ超過エラー
	 */
	public static void retry(int retryCount, RetryConsumer runnable, Class<? extends Throwable> catchClass) throws RetryException {
		retry(retryCount, runnable, DEFAULT_SLEEP_TIME, catchClass);
	}

	/**
	 * リトライ実行.
	 *
	 * @param retryCount リトライ回数
	 * @param runnable タスク
	 * @param sleeptime リトライ間隔
	 * @throws RetryException リトライ超過エラー
	 */
	public static void retry(int retryCount, RetryConsumer runnable, long sleeptime) throws RetryException {
		retry(retryCount, runnable, sleeptime, DEFAULT_CATCH_CLASS);
	}

	/**
	 * リトライ実行.
	 *
	 * @param retryCount リトライ回数
	 * @param runnable タスク
	 * @param sleeptime リトライ間隔
	 * @param catchClass リトライ対象エラー
	 * @throws RetryException リトライ超過エラー
	 */
	public static void retry(int retryCount, RetryConsumer runnable, long sleeptime,
			Class<? extends Throwable> catchClass) throws RetryException {
		if (catchClass == null) {
			throw new IllegalArgumentException("catchClass is null");
		}
		if (sleeptime < 0) {
			throw new IllegalArgumentException("sleeptime value is negative");
		}
		List<Throwable> throwables = new ArrayList<Throwable>();
		for (int i = 0; i < retryCount; i++) {
			try {
				runnable.run();
				return;
			} catch (Throwable e) {
				throwables.add(e);
				if (catchClass.isAssignableFrom(e.getClass())) {
					try {
						Thread.sleep(sleeptime);
					} catch (InterruptedException e1) {
						throw new RetryException(e1);
					}
					continue;
				}
				throw new RetryException(e);
			}
		}
		throw new RetryException(throwables);
	}

	/**
	 * リトライ実行.
	 *
	 * @param retryCount リトライ回数
	 * @param runnable タスク
	 * @throws RetryException リトライ超過エラー
	 */
	public static <R> R retry(int retryCount, RetrySupplier<R> runnable) throws RetryException {
		return retry(retryCount, runnable, DEFAULT_CATCH_CLASS);
	}

	/**
	 * リトライ実行.
	 *
	 * @param retryCount リトライ回数
	 * @param runnable タスク
	 * @param catchClass リトライ対象エラー
	 * @throws RetryException リトライ超過エラー
	 */
	public static <R> R retry(int retryCount, RetrySupplier<R> runnable, Class<? extends Throwable> catchClass) throws RetryException {
		return retry(retryCount, runnable, DEFAULT_SLEEP_TIME, catchClass);
	}

	/**
	 * リトライ実行.
	 *
	 * @param retryCount リトライ回数
	 * @param runnable タスク
	 * @param sleeptime リトライ間隔
	 * @throws RetryException リトライ超過エラー
	 */
	public static <R> R retry(int retryCount, RetrySupplier<R> runnable, long sleeptime) throws RetryException {
		return retry(retryCount, runnable, sleeptime, DEFAULT_CATCH_CLASS);
	}

	/**
	 * リトライ実行.
	 *
	 * @param retryCount リトライ回数
	 * @param runnable タスク
	 * @param sleeptime リトライ間隔
	 * @param catchClass リトライ対象エラー
	 * @throws RetryException リトライ超過エラー
	 */
	public static <R> R retry(int retryCount, RetrySupplier<R> runnable, long sleeptime,
			Class<? extends Throwable> catchClass) throws RetryException {
		if (catchClass == null) {
			throw new IllegalArgumentException("catchClass is null");
		}
		if (sleeptime < 0) {
			throw new IllegalArgumentException("sleeptime value is negative");
		}
		List<Throwable> throwables = new ArrayList<Throwable>();
		for (int i = 0; i < retryCount; i++) {
			try {
				return runnable.run();
			} catch (Throwable e) {
				throwables.add(e);
				if (catchClass.isAssignableFrom(e.getClass())) {
					try {
						Thread.sleep(sleeptime);
					} catch (InterruptedException e1) {
						throw new RetryException(e1);
					}
					continue;
				}
				throw new RetryException(e);
			}
		}
		throw new RetryException(throwables);
	}

	/**
	 * リトライ対象タスク
	 *
	 */
	@FunctionalInterface
	public interface RetryConsumer {

		/** 処理
		 * @throws Throwable 例外
		*/
		void run() throws Throwable;
	}

	/**
	 * リトライ対象タスク
	 *
	 */
	@FunctionalInterface
	public interface RetrySupplier<R> {

		/** 処理
		 * @throws Throwable 例外
		*/
		R run() throws Throwable;
	}
}
