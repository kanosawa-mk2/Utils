package utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * システムプロパティファイル
 *
 */
public enum ApplicationProperties {
	/** ユーザ名 */
	user_name,
	/** パスワード */
	user_password,
	;

	/** 値. */
	public String value;

	/** メッセージプロパティファイルパス. */
	private static final String FILE_PATH = "/application.properties";

	/**
	 * 指定のパスからプロパティファイルを読み込む
	 *
	 */
	static {
		try (InputStream is = ApplicationProperties.class.getResourceAsStream(FILE_PATH);
				InputStreamReader isr = new InputStreamReader(is, StandardCharsets.UTF_8);
				BufferedReader reader = new BufferedReader(isr)) {
			Properties result = new Properties();
			// Properties#load() で渡す Reader オブジェクトを UTF-8 エンコーディング指定して生成した
			// InputStreamReader オブジェクトにする
			result.load(reader);

			for (ApplicationProperties key : ApplicationProperties.values()) {
				ApplicationProperties.valueOf(key.name()).value = result.getProperty(key.name());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public static String dump() {
		List<String> bu = new ArrayList<String>();
		for (ApplicationProperties key : ApplicationProperties.values()) {
			bu.add(key.name() + "=" + key.value);
		}

		return String.join(",", bu);
	}

	/**
	 * テスト用メインメソッド.
	 *
	 * @param args
	 *            引数
	 */
	public static void main(String[] args) {
		System.out.println(ApplicationProperties.dump());
	}
}
