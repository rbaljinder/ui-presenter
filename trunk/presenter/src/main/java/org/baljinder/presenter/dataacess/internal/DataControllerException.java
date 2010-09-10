/**
 * 
 */
package org.baljinder.presenter.dataacess.internal;

/**
 * @author Baljinder Randhawa
 *
 */
public class DataControllerException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	public DataControllerException() {

	}
	
	public DataControllerException(String message, Throwable throwable) {
		super(message,throwable);
	}
}
