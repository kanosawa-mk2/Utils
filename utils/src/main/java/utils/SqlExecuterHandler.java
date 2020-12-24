package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SqlExecuterHandler {
	private static final String URL = "jdbc:oracle:thin:@localhost:1521:xe";
	private static final String USER = "MYUSER";
	private static final String PASS = "MYUSER";

	@FunctionalInterface
	public static interface ThrowingFunction<T, R> {
		R applay(T arg) throws Exception;
	}

	public static <R> R execute(ThrowingFunction<Connection, R> f) {
		try (Connection conn = DriverManager.getConnection(URL, USER, PASS)) {
			return f.applay(conn);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	public static <R> R executeTrun(ThrowingFunction<Connection, R> f) {
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(URL, USER, PASS);
			conn.setAutoCommit(false);
			R r = f.applay(conn);
			conn.commit();
			return r;
		} catch (Exception e) {
			if (conn != null) {
				try {
					conn.rollback();
				} catch (SQLException e1) {
				}
			}
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
				}
			}
		}
	}
}
