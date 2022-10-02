package my.bat.base.common.dao;

import java.sql.SQLException;
import java.util.List;

import my.bat.base.common.db.DBConnectionBean;
import my.bat.base.common.db.SqlConstants;
import my.bat.base.common.dto.Tbl2Dto;

public class Tbl2Dao {

	/**
	 * オブジェクトIDを指定してコンテンツの存在チェックをする.
	 *
	 * @param dbConnection DBコネクション
	 * @param objectId 図書オブジェクトID
	 * @retur 結果セット
	 * @throws SQLException
	 */
	public String selectName(DBConnectionBean dbConnection, int id) throws SQLException {
		return dbConnection.getDbutilsControl().selectSingle(SqlConstants.SELECT_NAME.getValue(), id);
	}

	/**
	 * オブジェクトIDを指定してコンテンツの存在チェックをする.
	 *
	 * @param dbConnection DBコネクション
	 * @param objectId 図書オブジェクトID
	 * @retur 結果セット
	 * @throws SQLException
	 */
	public Tbl2Dto selectByPk(DBConnectionBean dbConnection, String id) throws SQLException {
		return dbConnection.getDbutilsControl().selectBean(Tbl2Dto.class, SqlConstants.SELECT_ID_NAME.getValue(), id);
	}

	/**
	 * オブジェクトIDを指定してコンテンツの存在チェックをする.
	 *
	 * @param dbConnection DBコネクション
	 * @param objectId 図書オブジェクトID
	 * @retur 結果セット
	 * @throws SQLException
	 */
	public List<Tbl2Dto> selectAll(DBConnectionBean dbConnection) throws SQLException {
		return dbConnection.getDbutilsControl().selectBeanList(Tbl2Dto.class, SqlConstants.SELECT_ALL.getValue());
	}

	/**
	 * オブジェクトIDを指定してコンテンツの存在チェックをする.
	 *
	 * @param dbConnection DBコネクション
	 * @param objectId 図書オブジェクトID
	 * @retur 結果セット
	 * @throws SQLException
	 */
	public int insert(DBConnectionBean dbConnection, Tbl2Dto dto) throws SQLException {
		return dbConnection.getDbutilsControl().update(SqlConstants.INSERT.getValue(),
				dto.getId(), dto.getName());
	}

}
