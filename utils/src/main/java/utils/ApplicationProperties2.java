package utils;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;

public class ApplicationProperties2 {
	/** 初期値 */
	private static final String DEFAULT_VALUE = "";

	/** 初期値:ファイル移動リトライ回数 */
	private static final int DEFAULT_FILE_MOVE_LOOP_COUNT = 5;

	/** 初期値:ファイル移動リトライ間隔 */
	private static final long DEFAULT_FILE_MOVE_WAIT_INTERVAL = 100L;

	/** コンストラクタ. */
	private ApplicationProperties2() {
	}

	/**
	 * DB接続リトライ回数
	 * @return リトライ回数
	 */
	public static int getDbConnLoopCount() {
		return Integer.parseInt(ApplicationPropertiesValue.DB_CONN_LOOP_COUNT.value);
	}

	/**
	 * DB接続リトライ間隔
	 * @return リトライ間隔
	 */
	public static long getDbConnWaitInterval() {
		return Long.parseLong(ApplicationPropertiesValue.DB_CONN_WAIT_INTERVAL.value);
	}

	/**
	 * ファイル移動リトライ回数
	 * @return リトライ回数
	 */
	public static int getFileMoveLoopCount() {
		if (DEFAULT_VALUE.equals(ApplicationPropertiesValue.FILE_MOVE_LOOP_COUNT.value)) {
			return DEFAULT_FILE_MOVE_LOOP_COUNT;
		} else {
			return Integer.parseInt(ApplicationPropertiesValue.FILE_MOVE_LOOP_COUNT.value);
		}
	}

	/**
	 * ファイル移動リトライ間隔
	 * @return リトライ間隔
	 */
	public static long getFileMoveWaitInterval() {
		if (DEFAULT_VALUE.equals(ApplicationPropertiesValue.FILE_MOVE_WAIT_INTERVAL.value)) {
			return DEFAULT_FILE_MOVE_WAIT_INTERVAL;
		} else {
			return Integer.parseInt(ApplicationPropertiesValue.FILE_MOVE_WAIT_INTERVAL.value);
		}
	}

	/**
	 * 初期化処理
	 */
	public static void initializ() {
		ApplicationPropertiesValue.initializ();
	}

	/**
	 * システムプロパティ値
	 *
	 */
	public enum ApplicationPropertiesValue {
		/** PDF生成フォルダパス */
		PDF_GENERAT_IN_FILE_PATH {
			@Override
			protected void check() throws Exception {
				required();
				existFileOrFolder();
			}
		},
		/** PDFマージフォルダパス */
		PDF_MERGE_IN_FILE_PATH {
			@Override
			protected void check() throws Exception {
				required();
				existFileOrFolder();
			}
		},
		/** オーバレイフォルダパス */
		IMAGE_INSERT_IN_FILE_PATH {
			@Override
			protected void check() throws Exception {
				required();
				existFileOrFolder();
			}
		},
		/** 分割フォルダパス */
		PDF_DIVISION_IN_FILE_PATH {
			@Override
			protected void check() throws Exception {
				required();
				existFileOrFolder();
			}
		},
		/** ページ数取得フォルダパス */
		GET_PDF_PAGE_IN_FILE_PATH {
			@Override
			protected void check() throws Exception {
				required();
				existFileOrFolder();
			}
		},
		/** 画像/テキスト判定フォルダパス */
		PDF_TEXT_COUNT_IN_FILE_PATH {
			@Override
			protected void check() throws Exception {
				required();
				existFileOrFolder();
			}
		},
		/** PDF変換元ファイルを配置する共有フォルダパス */
		PDF_IN_FILE_PATH {
			@Override
			protected void check() throws Exception {
				required();
				existFileOrFolder();
			}
		},
		/** 投入ファイルエラーを配置する共有フォルダパス */
		PDF_ERROR_FILE_PATH {
			@Override
			protected void check() throws Exception {
				required();
				existFileOrFolder();
			}
		},
		/** EOFファイル名 */
		EOF_FILE_NAME {
			@Override
			protected void check() throws Exception {
				required();
			}
		},
		/** ロックファイルパス */
		LOCK_FILE_PATH {
			@Override
			protected void check() throws Exception {
				required();
				existFileOrFolder();
			}
		},
		/** DB接続失敗時にリトライする回数 */
		DB_CONN_LOOP_COUNT {
			@Override
			protected void check() throws Exception {
				required();
				checkNum();
			}
		},
		/** DB接続失敗時にリトライするまで待機する時間 */
		DB_CONN_WAIT_INTERVAL {
			@Override
			protected void check() throws Exception {
				required();
				checkNum();
			}
		},
		/** ファイル移動失敗時にリトライする回数 */
		FILE_MOVE_LOOP_COUNT {
			@Override
			protected void check() throws Exception {
				checkNum();
			}
		},
		/** ファイル移動失敗時にリトライするまで待機する時間 */
		FILE_MOVE_WAIT_INTERVAL {
			@Override
			protected void check() throws Exception {
				checkNum();
			}
		},

		;

		/** 値. */
		public String value;

		/**
		 * プロパティのチェック.
		 *
		 * @throws Exception チェック例外
		 */
		protected abstract void check() throws Exception;

		/** メッセージプロパティファイルパス. */
		private static final String SYSTEM_PROPERTIES_FILE_PATH = "/application.properties";

		/** スタティックイニシャライザ. */
		static {
			//initializ();
		}

		/**
		 * 初期化
		 */
		public static void initializ() {
			Properties prop = new Properties();
			try (InputStream is = ApplicationProperties.class.getResourceAsStream(SYSTEM_PROPERTIES_FILE_PATH);
					InputStreamReader isr = new InputStreamReader(is, "UTF-8");
					BufferedReader reader = new BufferedReader(isr)) {
				prop.load(reader);
			} catch (IOException e) {
				throw new RuntimeException(e);
			}

			for (ApplicationPropertiesValue key : ApplicationPropertiesValue.values()) {

				if (prop.getProperty(key.name()) != null) {
					key.value = prop.getProperty(key.name()).trim();
				} else {
					key.value = DEFAULT_VALUE;
				}
			}
		}

		/**
		 * 必須チェック
		 *
		 * @throws Exception チェック例外
		 */
		protected void required() throws Exception {
			if (StringUtils.isEmpty(value)) {
				throw new Exception(String.format("value is empty. key=%s",this.name()));
			}
		}

		/**
		 * ファイル存在チェック
		 *
		 * @throws Exception チェック例外
		 */
		protected void existFileOrFolder() throws Exception {
			if (StringUtils.isNotEmpty(value) && Files.notExists(Paths.get(value))) {
				throw new FileNotFoundException(String.format("file not found. key=%s file=%s",this.name(), value));
			}
		}

		/**
		 * 数値チェック
		 *
		 * @throws Exception チェック例外
		 */
		protected void checkNum() throws Exception {
			if (StringUtils.isNotEmpty(value) && !StringUtils.isNumeric(value)) {
				throw new Exception(String.format("value is not numeric. key=%s file=%s",this.name(), value));
			}
		}

		/**
		 * キーと値の文字列取得(デバッグ用).
		 *
		 * @return デバッグ文字列
		 */
		public static Object[] getValueStrings() {
			List<String> bu = new ArrayList<String>();
			for (ApplicationPropertiesValue key : ApplicationPropertiesValue.values()) {
				bu.add(key.name() + "=" + key.value);
			}
			return bu.toArray();
		}

		/**
		 * プロパティをチェックする.
		 *
		 * @return true OK/false NG
		 */
		public static boolean checkProprties() {
			boolean r = true;
			for (ApplicationPropertiesValue key : ApplicationPropertiesValue.values()) {
				try {
					key.check();
				} catch (Exception e) {
					r = false;
				}
			}
			return r;
		}
	}

	/**
	 * テスト用メインメソッド.
	 *
	 * @param args
	 *            引数
	 */
	public static void main(String[] args) {
		for (Object string : ApplicationPropertiesValue.getValueStrings()) {
			System.out.println(string);
		}
		System.out.println();
		for (ApplicationPropertiesValue key : ApplicationPropertiesValue.values()) {
			try {
				key.check();
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}
	}

}
