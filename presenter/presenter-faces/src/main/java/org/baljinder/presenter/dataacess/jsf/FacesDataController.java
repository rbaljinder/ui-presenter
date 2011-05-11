package org.baljinder.presenter.dataacess.jsf;

import java.util.Map;

import javax.faces.context.FacesContext;

import org.baljinder.presenter.dataacess.internal.DataController;
import org.baljinder.presenter.jsf.util.FacesContextUtil;

import com.google.common.collect.Maps;

public abstract class FacesDataController extends DataController {

	private Map<Operation,String> actionMethodBinding = Maps.newHashMap();
	
	@Override
	public void performAfterEvent(Event event) {
		switch (event) {
		case CLEAR_SELECTED_UI_ELEMENTS:
			clearSelectedIndexesOfUITable();
			break;
		case SELECT_DATA:
			selectDataFromTable();
			break;
		default:
			break;
		}
		super.performAfterEvent(event);
	}

	protected abstract String selectDataFromTable() ;

	protected abstract void clearSelectedIndexesOfUITable() ;

	@Override
	public String save() {
		String defaultReturn = super.save();
		if(actionMethodBinding.get(Operation.SAVE) != null)
			return executeMethodBinding(Operation.SAVE);
		return defaultReturn ;
	}
	
	@Override
	public String saveSelectedElements() {
		String defaultReturn =  super.saveSelectedElements();
		if(actionMethodBinding.get(Operation.SAVE) != null)
			return executeMethodBinding(Operation.SAVE);
		return defaultReturn ;
	}
	
	@Override
	public String delete() {
		String defaultReturn =  super.delete();
		if(actionMethodBinding.get(Operation.DELETE) != null)
			return executeMethodBinding(Operation.DELETE);
		return defaultReturn ;
	}
	
	@Override
	public String deleteSelectedElements() {
		String defaultReturn =  super.deleteSelectedElements();
		if(actionMethodBinding.get(Operation.DELETE) != null)
			return executeMethodBinding(Operation.DELETE);
		return defaultReturn ;
	}
	
	@Override
	public String update() {
		String defaultReturn =  super.update();
		if(actionMethodBinding.get(Operation.UPDATE) != null)
			return executeMethodBinding(Operation.UPDATE);
		return defaultReturn ;
	}

	public void setActionMethodBinding(
			Map<Operation, String> actionMethodBinding) {
		this.actionMethodBinding = actionMethodBinding ; 
		
	}

	public Map<Operation, String> getActionMethodBinding() {
		// TODO Auto-generated method stub
		return actionMethodBinding ;
	}
	
	private String executeMethodBinding(Operation operation){
		FacesContext facesContext = FacesContextUtil.getInstance().getFacesContext() ;
		return facesContext.getApplication().createMethodBinding("#{"+actionMethodBinding.get(operation)+"}", null).invoke(facesContext, null).toString();
	}
}
