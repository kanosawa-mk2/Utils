package my.bat.base.common.db;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import my.bat.base.common.util.PropertiesReader;

/**
 * DB設定
 *
 */
public enum DBProperties {
	/** ドライバ名 */
	DRIVER_NAME,
	/** DBコネクション */
	DB_CONNECTION,
	/** DBユーザ */
	DB_USER,
	/** DBユーザパスワード */
	DB_USER_PASSWORD, ;

	/** 値. */
	public String value;

	/** メッセージプロパティファイルパス. */
	private static final String SYSTEM_PROPERTIES_FILE_PATH = "/db.properties";

	/** スタティックイニシャライザ. */
	static {
		Properties prop = PropertiesReader.readPropertiesFileByClassPath(SYSTEM_PROPERTIES_FILE_PATH);

		for (DBProperties key : DBProperties.values()) {
			key.value = prop.getProperty(key.name());
		}
	}

	/**
	 * キーと値の文字列取得(デバッグ用).
	 *
	 * @return デバッグ文字列
	 */
	public static Object[] getValueStrings() {
		List<String> bu = new ArrayList<String>();
		for (DBProperties key : DBProperties.values()) {
			bu.add(key.name() + "=" + key.value);
		}
		return bu.toArray();
	}

	/**
	 * テスト用メインメソッド.
	 *
	 * @param args
	 *            引数
	 */
	public static void main(String[] args) {
		for (Object string : DBProperties.getValueStrings()) {
			System.out.println(string);
		}
	}
}
