package my.bat.base.business;

public abstract class BusinessControlBase {


	public boolean startBusiness(String[] args) throws Exception {
		return execute(args);
	}


	abstract protected boolean execute(String[] args) throws Exception;
}
