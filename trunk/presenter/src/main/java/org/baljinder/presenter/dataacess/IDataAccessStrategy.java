/**
 * 
 */
package org.baljinder.presenter.dataacess;

/**
 * @author Baljinder Randhawa
 *
 */
public interface IDataAccessStrategy {
	
	public boolean shouldFetch(IDataController dataControl);
	
	public void markFetched(IDataController dataControl) ;

}
