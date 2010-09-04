/**
 * 
 */
package org.baljinder.presenter.dataacess;

/**
 * @author Baljinder Randhawa
 *
 */
public interface IDataAccessStrategy {
	
	public boolean shouldFetch(IDataControl dataControl);
	
	public void markFetched(IDataControl dataControl) ;

}
