package utils;

import java.util.List;

public class RetryException extends Exception {

	List<Throwable> throwables;

	public RetryException(Throwable t) {
		super(t);
	}

	public RetryException(List<Throwable> throwables) {
		this.throwables = throwables;
	}

	public List<Throwable> getThrowables(){
		return throwables;
	}
}
