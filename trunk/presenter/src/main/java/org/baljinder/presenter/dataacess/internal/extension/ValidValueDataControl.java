/**
 * 
 */
package org.baljinder.presenter.dataacess.internal.extension;

import java.util.List;
import java.util.Map;

import org.baljinder.presenter.dataacess.extension.ValidValueGenricDao;
import org.baljinder.presenter.dataacess.internal.DataControl;
import org.baljinder.presenter.util.Utils;
import com.google.common.collect.Lists;

/**
 * @author Baljinder Randhawa
 * 
 */
//TODO: FIX ME 
public class ValidValueDataControl extends DataControl {

	private List<Object> objectsIDFromDB = Lists.newArrayList();

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.baljinder.presenter.jsf.ui.dataacess.internal.DataControl#initialize()
	 */
	@Override
	public boolean initialize() {
		super.initialize();
		objectsIDFromDB.clear();
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.baljinder.presenter.jsf.ui.dataacess.internal.DataControl#fetchData()
	 */
	@Override
	protected List<Map<String, Object>> fetchData() {
		List<Map<String, Object>> data = super.fetchData();
		for (Map<String, Object> dataObject : data) {
			for (Object value : dataObject.values())
				objectsIDFromDB.add(getValidValueDao().getAssignedIdOfObject(value, Utils.getClassNameForModel(getModelList(), value)));
		}
		return data;
	}

	public String save() {
		List<Map<String, Object>> selectedElements = getSelectedElements();
		if (selectedElements.isEmpty())
			saveInternal(getCurrentElementInternal());
		else {
			for (Map<String, Object> elementToSave : selectedElements)
				saveInternal(elementToSave);
		}
		markDataFetched();
		return null;
	}

	private void saveInternal(Map<String, Object> elementToSave) {
		ValidValueGenricDao dao = getValidValueDao();
		for (Object element : elementToSave.values()) {
			if (newlyCreatedElements.contains(element)) {
				if (dao.createNewIfDoesNotExist(element, Utils.getClassNameForModel(getModelList(), element)))
					newlyCreatedElements.remove(element);
				objectsIDFromDB.add(dao.getAssignedIdOfObject(elementToSave, Utils.getClassNameForModel(getModelList(), element)));
			} else {
				dao.deleteExistingIfIdChangeOrUpdate(getCurrentElementInternal(), Utils.getClassNameForModel(getModelList(), element), objectsIDFromDB);
			}
		}
	}

	public String saveSelectedElements() {
		List<Map<String, Object>> data = getData();
		for (Map<String, Object> elementToSave : data) {
			for (Object element : elementToSave.values()) {
				getValidValueDao().checkExistanceThenSaveOrUpdate(data, Utils.getClassNameForModel(getModelList(), element));
			}
		}
		markDataFetched();
		return null;
	}

	private ValidValueGenricDao getValidValueDao() {
		return (ValidValueGenricDao) getDao();
	}
}
