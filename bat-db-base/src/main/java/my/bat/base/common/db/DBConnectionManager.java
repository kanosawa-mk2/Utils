package my.bat.base.common.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnectionManager {

	/**
	 * DB接続の初期化
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	private static Connection getDbConnection() throws ClassNotFoundException, SQLException {

		// Oracle JDBC Driverのロード
		Class.forName(DBProperties.DRIVER_NAME.value);
		String conUrl = DBProperties.DB_CONNECTION.value;
		String conUser = DBProperties.DB_USER.value;
		String conPass = DBProperties.DB_USER_PASSWORD.value;
		Connection conn = DriverManager.getConnection(conUrl, conUser, conPass);

//		conn.setAutoCommit(false);

		return conn;

	}


	/**
	 * コネクションを取得する.
	 *
	 * @return コネクション.
	 */
	public static DBConnectionBean getConnection() throws SQLException, ClassNotFoundException {
		Connection conn = getDbConnection();
		DBConnectionBean connectionBean = new DBConnectionBean(conn);
		return connectionBean;
	}

	/**
	 * トランザクションコネクションを取得する.
	 *
	 * @return コネクション.
	 */
	public static DBConnectionBean getTranConnection() throws SQLException, ClassNotFoundException {
		Connection conn = getDbConnection();
		DBConnectionBean connectionBean = new DBConnectionBean(conn);
		connectionBean.beginTransaction();
		return connectionBean;
	}

}
