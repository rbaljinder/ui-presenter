/**
 * 
 */
package org.baljinder.presenter.dataacess.internal.support;

import org.baljinder.presenter.dataacess.IDataAccessStrategy;
import org.baljinder.presenter.dataacess.IDataControl;

/**
 *  This is more of do thing strategy. Just allows to fetch data without caring about anything.
 * @author Baljinder Randhawa
 *
 */

public class DirectDataAccessStrategy implements IDataAccessStrategy {

	/* (non-Javadoc)
	 * @see org.baljinder.presenter.jsf.ui.dataacess.IDataAccessStrategy#markFetched(org.baljinder.presenter.jsf.ui.dataacess.IDataControl)
	 */
	public void markFetched(IDataControl dataControl) {
		//OKAY 
	}

	/* (non-Javadoc)
	 * @see org.baljinder.presenter.jsf.ui.dataacess.IDataAccessStrategy#shouldFetch(org.baljinder.presenter.jsf.ui.dataacess.IDataControl)
	 */
	public boolean shouldFetch(IDataControl dataControl) {
		if(dataControl.getDataFetched())
			return false ;
		return true;
	}

}
