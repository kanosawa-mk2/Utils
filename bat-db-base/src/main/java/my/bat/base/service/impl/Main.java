package my.bat.base.service.impl;

import my.bat.base.business.BusinessControlBase;
import my.bat.base.business.impl.BusinessControl;
import my.bat.base.service.ServiceBase;

/**
 * メインクラス.
 */
public final class Main extends ServiceBase {
	/**
	 * エントリポイント
	 *
	 * @param args 引数
	 * @throws Exception 例外
	 */
	public static void main(String[] args) throws Exception {
		Main main = new Main();
		main.startService(args);
	}

	@Override
	protected boolean execute(String[] args) throws Exception {

		BusinessControlBase main = new BusinessControl();
		try {
			main.startBusiness(args);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}

}
