package my.bat.base.common.util;

import java.util.Properties;

/**
 * メッセージ定数管理
 *
 */
public enum MessageConstants {
	/** 開始 */
	I001,
	/** 終了 所要時間: %s ms */
	I002,
	/** システムエラーが発生しました。 */
	E999,

	;

	/** 値. */
	private String value;

	/** メッセージプロパティファイルパス. */
	private static final String FILE_PATH = "/message.properties";

	/**
	 * 指定のパスからプロパティファイルを読み込む
	 *
	 * @param className
	 *            例 /application.properties
	 * @return Propertiesオブジェクト
	 * @throws IOException
	 */
	static {
		Properties result = PropertiesReader.readPropertiesFileByClassPath(FILE_PATH);

		for (MessageConstants key : MessageConstants.values()) {
			MessageConstants.valueOf(key.name()).value = result.getProperty(key.name());
		}
	}

	public String getMessage(Object... args) {
		return String.format(value, args);
	}

	private static String dump() {
		java.util.List<String> bu = new java.util.ArrayList<String>();
		for (MessageConstants key : MessageConstants.values()) {
			bu.add(key.name() + "=" + key.value);
		}

		return String.join("\r\n", bu);
	}

	/**
	 * テスト用メインメソッド.
	 *
	 * @param args
	 *            引数
	 */
	public static void main(String[] args) {
		System.out.println(MessageConstants.dump());
	}
}
