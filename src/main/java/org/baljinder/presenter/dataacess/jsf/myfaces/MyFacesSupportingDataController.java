package org.baljinder.presenter.dataacess.jsf.myfaces;

import java.util.Map;

import javax.faces.context.FacesContext;

import org.apache.myfaces.trinidad.component.UIXTable;
import org.apache.myfaces.trinidad.event.SortEvent;
import org.apache.myfaces.trinidad.model.RowKeySet;
import org.apache.myfaces.trinidad.model.SortCriterion;
import org.baljinder.presenter.dataacess.internal.DataController;
import org.baljinder.presenter.dataacess.jsf.SupportsJsfComponent;
import org.baljinder.presenter.jsf.util.FacesContextUtil;
import org.baljinder.presenter.jsf.util.FacesUtils;

import com.google.common.collect.Maps;

public class MyFacesSupportingDataController extends DataController implements SupportsJsfComponent {

	private UIXTable table;

	private Map<Operation,String> actionMethodBinding = Maps.newHashMap();
	
	public UIXTable getTable() {
		return table;
	}

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

	public void clearSelectedIndexesOfUITable() {
		if (getTable() != null) {
			if (FacesUtils.isSingleSelect(getTable()) && data.size() > 0) {
				RowKeySet rowKeySet = getTable().getSelectedRowKeys();
				rowKeySet.clear();
				rowKeySet.add(0);
				getTable().setSelectedRowKeys(rowKeySet);
			}
		}
	}

	public String selectDataFromTable() {
		UIXTable table = getTable();
		if (table != null)
			setCurrentElementIndex(table.getRowIndex());
		return null;
	}

	public String sortData(SortEvent event) {
		if (event.getSortCriteria().size() > 0) {
			String sortCriterionSelected = ((SortCriterion) event.getSortCriteria().get(0)).getProperty();
			return sort(sortCriterionSelected);
		}
		return null;
	}
	
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
