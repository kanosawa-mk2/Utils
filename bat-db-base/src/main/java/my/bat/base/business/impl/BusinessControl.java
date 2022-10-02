package my.bat.base.business.impl;

import my.bat.base.business.BusinessControlBase;
import my.bat.base.common.dao.Tbl2Dao;
import my.bat.base.common.db.DBConnectionBean;
import my.bat.base.common.db.DBConnectionManager;
import my.bat.base.common.log.LogService;

/**
 * ビジネスロジック
 *
 */
public class BusinessControl extends BusinessControlBase {

	/** ログ出力クラス. */
	private static final LogService LOG_SERVICE = new LogService(BusinessControl.class);

	/**
	 * 実行する.
	 * @return 全て正常終了した場合、true
	 * @throws Exception 例外
	 */
	@Override
	public boolean execute(String[] args) throws Exception {

		try(DBConnectionBean conn = DBConnectionManager.getConnection()){
			
			var dao = new Tbl2Dao();
			
			
			System.out.println(dao.selectName(conn, 1));
			
			
			dao.selectAll(conn).forEach(x->System.out.println( x.toString()));
		}
		return true;
	}
}
