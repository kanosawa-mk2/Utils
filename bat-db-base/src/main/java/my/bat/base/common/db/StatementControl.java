package my.bat.base.common.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Objects;

import my.bat.base.common.log.LogService;



/**
 * SQLコントローラ。
 * ステートメントとパラメータの設定を簡略化する。
 *
 */
public class StatementControl {

	private static LogService log = new LogService(StatementControl.class);

	/** コネクション. */
	private Connection conn;
	
	/**
	 * コンストラクタ
	 * @param conn コネクション
	 */
	public StatementControl(Connection conn) {
		this.conn = conn;
	}
	
	
	/**
	 * SQLログを出力
	 *
	 * @param sql    SQL
	 * @param params パラメータ
	 */
	private static void outLog(SqlConstants sql, Object... params) {
		if (log.isDebugEnabled()) {
			log.debug(String.format("SQL:%s", sql.getValue()));
			if (params.length > 0) {

				log.debug(String.format("params:%s",
						String.join(",", Arrays.stream(params).map(x -> Objects.toString(x)).toArray(String[]::new))));

			}
		}
	}

	/**
	 * ステートメントを取得する
	 * @param conn DBコネクション
	 * @param sql SQL
	 * @param params パラメータ
	 * @return ステートメント
	 * @throws SQLException SQL例外
	 */
	public PreparedStatement createStatement(SqlConstants sql, Object... params) throws SQLException {
		outLog(sql, params);
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement(sql.getValue());
			setParams(pstmt, params);
			return pstmt;
		} catch (Exception e) {
			if (pstmt != null) {
				pstmt.close();
			}
			throw e;
		}
	}

	/**
	 * ステートメントにパラメータを設定する。
	 * @param pstmt ステートメント
	 * @param params パラメータ
	 * @throws SQLException SQL例外 基本的に型例外
	 */
	private static void setParams(PreparedStatement pstmt, Object... params) throws SQLException {
		if (params == null) {
			return;
		}

		for (int i = 0; i < params.length; i++) {
			Object object = params[i];
			int index = i + 1;

			if (object == null) {
				pstmt.setNull(index, java.sql.Types.NULL);
			} else if (object.getClass() == java.lang.String.class) {
				pstmt.setString(index, (String) object);
			} else if (object.getClass() == java.sql.Timestamp.class) {
				pstmt.setTimestamp(index, (java.sql.Timestamp)object);
			} else if (object.getClass() == java.util.Date.class) {
				Timestamp sqlDate = new Timestamp(((java.util.Date) object).getTime());
				pstmt.setTimestamp(index, sqlDate);
			} else if (object.getClass() == java.sql.Date.class) {
				pstmt.setDate(index, (java.sql.Date) object);
			} else if (object.getClass() == java.math.BigDecimal.class) {
				pstmt.setBigDecimal(index, (java.math.BigDecimal) object);
			} else if (object.getClass() == java.lang.Boolean.class) {
				pstmt.setBoolean(index, (java.lang.Boolean) object);
			} else {
				throw new RuntimeException("unknown type:" + object.getClass());
			}
		}
	}

}
