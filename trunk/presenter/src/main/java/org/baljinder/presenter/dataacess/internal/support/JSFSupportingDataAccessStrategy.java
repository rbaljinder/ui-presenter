/**
 * 
 */
package org.baljinder.presenter.dataacess.internal.support;

import org.baljinder.presenter.dataacess.IDataAccessStrategy;
import org.baljinder.presenter.dataacess.IDataControl;
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
	 * @see org.baljinder.presenter.jsf.ui.dataacess.IDataAccessStrategy#shouldFetch(org.baljinder.presenter.jsf.ui.dataacess.IDataControl)
	 */
	public boolean shouldFetch(IDataControl dataControl) {
		Boolean initialized = (Boolean) FacesContextUtil.getInstance().getScopeUtil().getFromRequest(dataControl.getDataControlName() + "Intilized");
		if (FacesContextUtil.getInstance().getFacesContext().getRenderResponse() && !dataControl.getDataFetched()
				&& (initialized == null || !initialized))
			return true;
		return false;
	}

	public void markFetched(IDataControl dataControl) {
		FacesContextUtil.getInstance().getScopeUtil().storeOnRequest(dataControl.getDataControlName() + "Intilized", true);
	}
}
