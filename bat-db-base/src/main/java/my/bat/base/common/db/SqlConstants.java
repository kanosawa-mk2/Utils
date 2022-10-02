package my.bat.base.common.db;


import java.io.IOException;
import java.util.Properties;

import my.bat.base.common.util.PropertiesReader;

/**
 * SQL定数管理
 *
 */
public enum SqlConstants {
	/** サンプルマスタ 名前 検索 */
	SELECT_NAME,
	/** サンプルマスタ ID,名前 検索 */
	SELECT_ID_NAME,
	/** サンプルマスタ　全文検索 */
	SELECT_ALL,
	/** サンプルマスタ　登録 */
	INSERT,

	;

	/** 値. */
	private String value;

	/** メッセージプロパティファイルパス. */
	private static final String FILE_PATH = "/sql.properties";

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

		for (SqlConstants key : SqlConstants.values()) {
			SqlConstants.valueOf(key.name()).value = result.getProperty(key.name());
		}
	}

	public String getValue() {
		return value;
	}

	private static String dump() {
		java.util.List<String> bu = new java.util.ArrayList<String>();
		for (SqlConstants key : SqlConstants.values()) {
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
		System.out.println(SqlConstants.dump());
	}
}
