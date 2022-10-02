package my.bat.base.common.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import my.bat.base.common.log.LogService;

/**
 * Oracle接続クラス.
 */
public class DbConnection implements AutoCloseable {

	/** ロガー. */
	protected LogService log = new LogService(DbConnection.class);
	/** コネクション. */
	private Connection conn;
	/** トランザクション */
	private boolean istran = false;

	private static String conUrl;
	private static String conUser;
	private static String conPass;

	/**
	 * スタティックイニシャライザ
	 */
	static {
		conUrl = DBProperties.DB_CONNECTION.value;
		conUser = DBProperties.DB_USER.value;
		conPass = DBProperties.DB_USER_PASSWORD.value;
		try {
			Class.forName(DBProperties.DRIVER_NAME.value);
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e);
		}
	}

	/** コンストラクタ */
	public DbConnection() throws SQLException {
		conn = openDbConnection();
	}

	/**
	 * DB接続の初期化
	 *
	 * @throws MhiAppException
	 */
	private Connection openDbConnection() throws SQLException {
		try {
			if (log.isDebugEnabled()) {
				log.debug("pdf db connection open");
			}
			Connection conn = DriverManager.getConnection(conUrl, conUser, conPass);
			return conn;

		} catch (SQLException e) {
			throw new SQLException(e);
		}
	}

	/**
	 * DBコネクション取得
	 */
	public Connection getDbConnection() {
		return conn;
	}

	/**
	 * トランザクション設定.
	 *
	 * @throws SQLException SQL例外
	 */
	public void beginTransaction() throws SQLException {
		if (conn != null) {
			conn.setAutoCommit(false);
			istran = true;
		}
	}

	/**
	 * コミット
	 *
	 * @throws SQLException SQL例外
	 */
	public void commit() throws SQLException {
		if (conn != null && istran) {
			conn.commit();
			istran = false;
			if (log.isDebugEnabled()) {
				log.debug("pdf db connection commit!!");
			}
		}
	}

	/**
	 * AutoCloseable実装のclose処理<br>
	 * コミットされていなかった場合、ロールバックを実行
	 */
	@Override
	public void close() {
		if (conn != null) {
			if (istran) {
				try {
					if (log.isDebugEnabled()) {
						log.debug("pdf db connection rollback!!");
					}
					conn.rollback();
				} catch (Exception e) {
				}

			}

			try {
				if (log.isDebugEnabled()) {
					log.debug("pdf db connection close!!");
				}
				conn.close();
			} catch (Exception e) {
			}
		}
	}
}
