package org.baljinder.presenter.dataacess.jsf.myfaces;

import org.apache.myfaces.trinidad.component.UIXTable;
import org.apache.myfaces.trinidad.event.SortEvent;
import org.apache.myfaces.trinidad.model.RowKeySet;
import org.apache.myfaces.trinidad.model.SortCriterion;
import org.baljinder.presenter.dataacess.internal.DataController;
import org.baljinder.presenter.dataacess.jsf.SupportsJsfComponent;
import org.baljinder.presenter.jsf.util.FacesUtils;

public class MyFacesSupportingDataController extends DataController implements SupportsJsfComponent {

	private UIXTable table;

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
			sort(sortCriterionSelected);
		}
		return null;
	}
}
