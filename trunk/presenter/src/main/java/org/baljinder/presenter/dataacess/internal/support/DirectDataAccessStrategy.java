/**
 * 
 */
package org.baljinder.presenter.dataacess.internal.support;

import org.baljinder.presenter.dataacess.IDataAccessStrategy;
import org.baljinder.presenter.dataacess.IDataController;

/**
 *  This is more of do thing strategy. Just allows to fetch data without caring about anything.
 * @author Baljinder Randhawa
 *
 */

public class DirectDataAccessStrategy implements IDataAccessStrategy {

	/* (non-Javadoc)
	 * @see org.baljinder.presenter.jsf.ui.dataacess.IDataAccessStrategy#markFetched(org.baljinder.presenter.jsf.ui.dataacess.IDataController)
	 */
	public void markFetched(IDataController dataController) {
		//OKAY 
	}

	/* (non-Javadoc)
	 * @see org.baljinder.presenter.jsf.ui.dataacess.IDataAccessStrategy#shouldFetch(org.baljinder.presenter.jsf.ui.dataacess.IDataController)
	 */
	public boolean shouldFetch(IDataController dataController) {
		if(dataController.getDataFetched())
			return false ;
		return true;
	}

}
