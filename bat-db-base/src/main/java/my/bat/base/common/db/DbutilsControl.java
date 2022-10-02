package my.bat.base.common.db;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

import org.apache.commons.dbutils.BasicRowProcessor;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.RowProcessor;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import my.bat.base.common.log.LogService;

/**
 * DbUtilsによるSQL実行クラス.
 *
 */
public class DbutilsControl {

	/** ログ出力クラス. */
	private static final LogService LOG_SERVICE = new LogService(DbutilsControl.class);
	/** Beanマッピング時に、アンダーバーを除いてマッピングを行うマッピングプロセッサ */
	private static final RowProcessor rp = new BasicRowProcessor(new RemoveUnderScoreNameMappingProcessor());

	/** コネクション. */
	private Connection conn;
	
	/**
	 * コンストラクタ.
	 * 
	 * @param conn コネクション
	 */
	public DbutilsControl(Connection conn) {
		this.conn = conn;
	}

	/** ParameterMetaDataをドライバがサポートしている場合false,していない場合true **/
	private static final boolean IS_PARAMETER_METADATA_SUPPORTED = true;
	/** クエリランナ */
	private QueryRunner RUNNER = new QueryRunner(IS_PARAMETER_METADATA_SUPPORTED);
	
	private static void outLog(String sql, Object... params) {
		if (LOG_SERVICE.isDebugEnabled()) {
			LOG_SERVICE.debug("SQL:{}", sql);
			if (params.length > 0) {
				LOG_SERVICE.debug("params:[{}]", String.join(",", Stream.of(params).map(x -> Objects.toString(x)).toArray(String[]::new)));
			}
		}
		
	}

	/**
	 * 更新、削除を実行します.
	 * 
	 * @param sql    SQL
	 * @param params パラメータ
	 * @return 結果件数
	 * @exception SQLException SQL例外
	 */
	public int update(String sql, Object... params) throws SQLException {
		outLog(sql,params);
		return RUNNER.update(conn, sql, params);
	}

	/**
	 * 検索(単一項目)を実行します.
	 *
	 * @param sql    SQL
	 * @param params パラメータ
	 * @return 単一項目
	 * @exception SQLException SQL例外
	 */
	public <T> T selectSingle(String sql, Object... params) throws SQLException {
		outLog(sql,params);
		ResultSetHandler<T> rs = new ScalarHandler<T>();
		return RUNNER.query(conn, sql, rs, params);
	}

	/**
	 * 検索(単一JavaBean項目)を実行します.
	 *
	 * @param type   タイプ
	 * @param sql    SQL
	 * @param params パラメータ
	 * @return 単一JavaBean項目
	 * @exception SQLException SQL例外
	 */
	public <T> T selectBean(Class<T> type, String sql, Object... params) throws SQLException {
		outLog(sql,params);
		ResultSetHandler<T> rs = new BeanHandler<T>(type, rp);
		return RUNNER.query(conn, sql, rs, params);
	}

	/**
	 * 検索(複数JavaBean項目)を実行します.
	 *
	 * @param type   タイプ
	 * @param sql    SQL
	 * @param params パラメータ
	 * @return 複数JavaBean項目
	 * @exception SQLException SQL例外
	 */
	public <T> List<T> selectBeanList(Class<T> type, String sql, Object... params)
			throws SQLException {
		outLog(sql,params);
		ResultSetHandler<List<T>> rs = new BeanListHandler<T>(type, rp);
		return RUNNER.query(conn, sql, rs, params);
	}
	
//	/**
//	 * 更新、削除を実行します.
//	 * 
//	 * @param sql    SQL
//	 * @param params パラメータ
//	 * @return 結果件数
//	 * @exception SQLException SQL例外
//	 */
//	public int update(String sql, Object... params) throws SQLException {
//		outLog(sql,params);
//		return RUNNER.update(conn, sql, params);
//	}
//
//	/**
//	 * 検索(単一項目)を実行します.
//	 *
//	 * @param sql    SQL
//	 * @param params パラメータ
//	 * @return 単一項目
//	 * @exception SQLException SQL例外
//	 */
//	public <T> T selectSingle(String sql, Object... params) throws SQLException {
//		outLog(sql,params);
//		ResultSetHandler<T> rs = new ScalarHandler<T>();
//		return RUNNER.query(conn, sql, rs, params);
//	}
//
//	/**
//	 * 検索(単一JavaBean項目)を実行します.
//	 *
//	 * @param type   タイプ
//	 * @param sql    SQL
//	 * @param params パラメータ
//	 * @return 単一JavaBean項目
//	 * @exception SQLException SQL例外
//	 */
//	public <T> T selectBean(Class<T> type, String sql, Object... params) throws SQLException {
//		outLog(sql,params);
//		ResultSetHandler<T> rs = new BeanHandler<T>(type);
//		return RUNNER.query(conn, sql, rs, params);
//	}
//
//	/**
//	 * 検索(複数JavaBean項目)を実行します.
//	 *
//	 * @param type   タイプ
//	 * @param sql    SQL
//	 * @param params パラメータ
//	 * @return 複数JavaBean項目
//	 * @exception SQLException SQL例外
//	 */
//	public <T> List<T> selectBeanList(Class<T> type, String sql, Object... params)
//			throws SQLException {
//		outLog(sql,params);
//		ResultSetHandler<List<T>> rs = new BeanListHandler<T>(type);
//		return RUNNER.query(conn, sql, rs, params);
//	}

}
