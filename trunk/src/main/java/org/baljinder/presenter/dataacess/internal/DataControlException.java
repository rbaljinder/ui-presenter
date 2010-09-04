/**
 * 
 */
package org.baljinder.presenter.dataacess.internal;

/**
 * @author Baljinder Randhawa
 *
 */
public class DataControlException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	public DataControlException() {

	}
	
	public DataControlException(String message, Throwable throwable) {
		super(message,throwable);
	}
}
