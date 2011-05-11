package org.baljinder.presenter.dataacess.jsf;

import java.util.Map;

import org.baljinder.presenter.dataacess.IDataController;


public interface SupportsJsfComponent {
	
	public void setActionMethodBinding(Map<IDataController.Operation,String> actionMethodBinding) ;
	
	public Map<IDataController.Operation,String> getActionMethodBinding() ;
	
}
