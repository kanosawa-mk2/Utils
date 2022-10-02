package my.bat.base.common.util;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

import my.bat.base.common.exception.AppException;



/**
 * プロパティを読込むクラス.<br>
 */
public final class PropertiesReader {

	/** インスタンス生成禁止. */
	private PropertiesReader() {
	}

	/**
	 * プロパティファイルの読込（ファイルパス指定）.<br>
	 *
	 * @param filePath ファイルパス
	 * @return Properties プロパティ
	 */
	public static Properties readPropertiesFile(String filePath) {
		Properties prop = new Properties();
		try (InputStream is = new FileInputStream(new File(filePath));
				InputStreamReader isr = new InputStreamReader(is, StandardCharsets.UTF_8);
				BufferedReader reader = new BufferedReader(isr)) {

			prop.load(reader);
		} catch (IOException e) {
			throw new AppException(e);
		}

		return prop;
	}

	/**
	 * プロパティファイルの読込（クラスパス指定）.<br>
	 *
	 * @param filePath クラスパス
	 * @return Properties プロパティ
	 */
	public static Properties readPropertiesFileByClassPath(String filePath) {
		Properties prop = new Properties();
		try (InputStream is = PropertiesReader.class.getResourceAsStream(filePath);
				InputStreamReader isr = new InputStreamReader(is, StandardCharsets.UTF_8);
				BufferedReader reader = new BufferedReader(isr)) {

			prop.load(reader);

		} catch (IOException e) {
			throw new AppException(e);
		}

		return prop;
	}
}
