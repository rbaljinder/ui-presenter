/**
 * 
 */
package org.baljinder.presenter.dataacess.internal.support;

import org.baljinder.presenter.dataacess.IDataAccessStrategy;
import org.baljinder.presenter.dataacess.IDataController;
import org.baljinder.presenter.jsf.util.FacesContextUtil;

/**
 * @author Baljinder Randhawa
 * 
 */
//FIXME: does it still need dependency on request scoped variable
public class JSFSupportingDataAccessStrategy implements IDataAccessStrategy {

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.baljinder.presenter.jsf.ui.dataacess.IDataAccessStrategy#shouldFetch(org.baljinder.presenter.jsf.ui.dataacess.IDataController)
	 */
	public boolean shouldFetch(IDataController dataController) {
		Boolean initialized = (Boolean) FacesContextUtil.getInstance().getScopeUtil().getFromRequest(dataController.getDataControlName() + "Intilized");
		if (FacesContextUtil.getInstance().getFacesContext().getRenderResponse() && !dataController.getDataFetched()
				&& (initialized == null || !initialized))
			return true;
		return false;
	}

	public void markFetched(IDataController dataController) {
		FacesContextUtil.getInstance().getScopeUtil().storeOnRequest(dataController.getDataControlName() + "Intilized", true);
	}
}
