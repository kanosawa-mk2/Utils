package my.bat.base.service;

import my.bat.base.common.log.LogService;
import my.bat.base.common.util.MessageConstants;

public abstract class ServiceBase {

	/** ログ出力クラス. */
	private static final LogService LOG_SERVICE = new LogService(ServiceBase.class);

	public boolean startService(String[] args) {
		// スレッドローカルの削除
		LogService.removePreMessage();
		// スレッドローカルの設定
		long start = System.currentTimeMillis();
		boolean result = false;
		try {
			// 開始ログ
			LOG_SERVICE.info(MessageConstants.I001.getMessage());

			result = execute(args);
		} catch (Exception e) {
			// システムエラー
			LOG_SERVICE.error(MessageConstants.E999.getMessage(), e);
		} catch (Error e) {
			// 他エラー
			LOG_SERVICE.error(MessageConstants.E999.getMessage(), e);
			throw e;
		}

		long end = System.currentTimeMillis();
		LOG_SERVICE.info(MessageConstants.I002.getMessage((end - start)));

		// スレッドローカルの削除
		LogService.removePreMessage();

		return result;

	}

	abstract protected boolean execute(String[] args) throws Exception;
}
