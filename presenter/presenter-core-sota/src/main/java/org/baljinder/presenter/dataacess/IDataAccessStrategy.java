/**
 * 
 */
package org.baljinder.presenter.dataacess;

/**
 * @author Baljinder Randhawa
 *
 */
public interface IDataAccessStrategy {
	
	public boolean shouldFetch(IDataController dataController);
	
	public void markFetched(IDataController dataController) ;

}
