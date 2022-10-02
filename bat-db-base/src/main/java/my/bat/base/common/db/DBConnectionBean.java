package my.bat.base.common.db;

import java.sql.Connection;
import java.sql.SQLException;

import my.bat.base.common.log.LogService;

/**
 * DBコネクションBeanクラス.
 */
public class DBConnectionBean implements AutoCloseable {

	/** ログ出力クラス. */
	private static final LogService LOG_SERVICE = new LogService(DBConnectionBean.class);

	/** コネクション. */
	private Connection conn;
	/** トランザクションフラグ. */
	private boolean tranFlag = false;

	/**
	 * コンストラクタ
	 *
	 * @param conn コネクション
	 * @param sqlControl
	 */
	public DBConnectionBean(Connection conn) {
		this.conn = conn;
	}
	
	public DbutilsControl getDbutilsControl() {
		return new DbutilsControl(conn);
	}
	
	public StatementControl getStatementControl() {
		return new StatementControl(conn);
	}
	
//	/**
//	 * コネクションを取得する
//	 * @return コネクション
//	 */
//	public Connection getConnection() {
//		return this.conn;
//	}

	/**
	 * トランザクション設定.
	 *
	 * @throws SQLException
	 */
	public void beginTransaction() throws SQLException {
		if (conn != null) {
			conn.setAutoCommit(false);
			tranFlag = true;
		}
	}

	/**
	 * コミット
	 *
	 * @throws SQLException
	 */
	public void commit() throws SQLException {
		if (conn != null && tranFlag) {
			if(LOG_SERVICE.isDebugEnabled()) {
				LOG_SERVICE.debug("connection commit!!");
			}
			conn.commit();
			tranFlag = false;
		}
	}

	/**
	 * AutoCloseable実装のclose処理<br>
	 * コミットされていなかった場合、ロールバックを実行
	 */
	@Override
	public void close() throws Exception {
		if (conn != null) {
			if (tranFlag) {
				try {
					if (LOG_SERVICE.isDebugEnabled()) {
						LOG_SERVICE.debug("db connection rollback!!");
					}
					conn.rollback();
				} catch (Exception e) {
				}

			}

			try {
				if (LOG_SERVICE.isDebugEnabled()) {
					LOG_SERVICE.debug("db connection close!!");
				}
				conn.close();
			} catch (Exception e) {
			}
		}
	}
}
