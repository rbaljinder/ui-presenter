package org.baljinder.presenter.dataacess.internal;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.myfaces.trinidad.component.UIXTable;
import org.apache.myfaces.trinidad.event.SortEvent;
import org.apache.myfaces.trinidad.model.RowKeySet;
import org.apache.myfaces.trinidad.model.SortCriterion;

import org.baljinder.presenter.dataacess.GenericPresentationDAO;
import org.baljinder.presenter.dataacess.IDataControl;
import org.baljinder.presenter.dataacess.util.FacesUtils;
import org.baljinder.presenter.util.ClassUtils;
import org.baljinder.presenter.util.Utils;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

//TODO: get rid of JSF components and reliance of JSF life-cycle to determine when to fetch data(if possible)
public class DataControl extends AbstractDataControl {

	protected List<Integer> selectedElementsIndex = Lists.newArrayList();

	protected List<Object> newlyInsertedElements = Lists.newArrayList();

	public boolean initialize() {
		eventHandler.beforeInitialize(this);
		newlyInsertedElements.clear();
		boolean toReturn = super.initialize();
		eventHandler.afterInitialize(this);
		return toReturn;
	}

	protected List<Map<String, Object>> fetchData() {
		if (getDao() == null)
			setDao(getPersistenceManager().getDAO(getDaoKeyName()));
		fetchInternal();
		markDataFetched();
		dataFetched = true;
		return data;
	}

	protected void fetchInternal() {
		if (!fetchParentIfAny())
			return;
		eventHandler.beforeDataFetch(this);
		logger.info("Fetching data for Data Control[" + getDataControlName() + "]");
		data = getDao().getAllEntities(getModelList(), getPageSize(), calculateFirstResultToFetch(), getQueryString());
		setCountForLastPageFetch(-1);
		clearSelectedIndexesOfUITable();
		if (data.size() > 0)
			setCurrentElementIndex(0);
		else
			data = Lists.newArrayList();
		eventHandler.afterDataFetch(this);
	}

	/**
	 * Also determines if we even need to fetch data for this data control or not
	 */
	protected boolean fetchParentIfAny() {
		IDataControl parentDataControl = getParentDataControl();
		if (parentDataControl != null && getParentChildRelation() != null) {
			List<Map<String, Object>> parentData = parentDataControl.getData();
			if (parentData.size() == 0) {
				if (logger.isDebugEnabled())
					logger.debug("Data Fetch not required for[" + getDataControlName() + "] as the parent[" + parentDataControl.getDataControlName()
							+ "] has no data row");
				data.clear();
				return false; // if parent has no data element then child has nothing to relate to (You cant have child without parent :-)
			}
		}
		return true;
	}

	/** 
	 * 
	 */
	public void clearSelectedIndexesOfUITable() {
		selectedElementsIndex.clear();
		if (getTable() != null) {
			if (FacesUtils.isSingleSelect(getTable()) && data.size() > 0) {
				RowKeySet rowKeySet = getTable().getSelectedRowKeys();
				rowKeySet.clear();
				rowKeySet.add(0);
				getTable().setSelectedRowKeys(rowKeySet);
			}
		}
	}

	protected Integer calculateFirstResultToFetch() {
		if (getCountForLastPageFetch() == -1)
			return getPageSize() * (getPageCursor());
		else {
			int lastChunkOfRecordsAsPerPageSize = getCountForLastPageFetch() % getPageSize() == 0 ? getPageSize() : getCountForLastPageFetch()
					% getPageSize();
			return getCountForLastPageFetch() - lastChunkOfRecordsAsPerPageSize;
		}
	}

	@SuppressWarnings("unchecked")
	public String insert() {
		eventHandler.beforeInsert(this);
		for (Class clazz : getModelList()) {
			Object newInstance = ClassUtils.getNewInstance(clazz, "Verify the bean Declartion for classs model name for DataControl[" + getDataControlName() + "]");
			Map<String, Object> modelNameObjectMap = Maps.newHashMap();
			final int noOfModelsInvolved = getModelList().size();
			for (int index = 0; index < noOfModelsInvolved; index++) {
				modelNameObjectMap.put(Utils.getModelName(getModelList(), newInstance), newInstance);
				data.add(0, modelNameObjectMap);
				newlyInsertedElements.add(newInstance);
			}
		}
		eventHandler.afterInsert(this);
		return null;
	}

	public String save() {
		save(false);
		return null;
	}

	public String save(Boolean flushChanges) {
		List<Map<String, Object>> selectedElements = getSelectedElements();
		if (selectedElements.isEmpty())
			saveInternal(getCurrentElementInternal(), flushChanges);
		else {
			for (Map<String, Object> element : selectedElements)
				saveInternal(element, flushChanges);
		}
		markDataFetched();
		return null;
	}

	private void saveInternal(Map<String, Object> elementToSave, Boolean flushChanges) {
		if(elementToSave.entrySet().isEmpty())
			return ;
		eventHandler.beforeSave(this);
		GenericPresentationDAO dao = getDao();
		if (newlyInsertedElements.contains(elementToSave)) {
			dao.create(elementToSave);
			newlyInsertedElements.remove(elementToSave);
		} else {
			if (flushChanges)
				dao.save(getCurrentElementInternal(), true);
			else
				dao.save(getCurrentElementInternal());
		}
		eventHandler.afterSave(this);
	}

	public String saveAll() {
		eventHandler.beforeSave(this);
		getDao().save(getData());
		markDataFetched();
		eventHandler.afterSave(this);
		return null;
	}

	public String update() {
		eventHandler.beforeUpdate(this);
		getDao().update(getCurrentElementInternal());
		markDataFetched();
		eventHandler.afterUpdate(this);
		return null;
	}

	public String delete() {
		List<Map<String, Object>> selectedElements = getSelectedElements();
		if (selectedElements.isEmpty())
			deleteInternal(getCurrentElementInternal());
		else {
			for (Map<String, Object> objectToDelete : selectedElements)
				deleteInternal(objectToDelete);
		}
		markDataFetched();
		clearSelectedIndexesOfUITable();
		return null;
	}

	private void deleteInternal(Map<String, Object> objectToDelete) {
		eventHandler.beforeDelete(this);
		if (newlyInsertedElements.contains(objectToDelete))
			newlyInsertedElements.remove(objectToDelete);
		else
			getDao().delete(objectToDelete);
		data.remove(objectToDelete);
		eventHandler.afterDelete(this);
	}

	public List<Map<String, Object>> getData() {
		if (shouldFetchData())
			fetchData();
		if (data.size() > getPageSize())
			return data.subList(0, getPageSize());
		return data;
	}

	public Map<String, Object> getCurrentElement() {
		if (shouldFetchData())
			fetchData();
		return getCurrentElementInternal();
	}

	public List<Map<String, Object>> getSelectedElements() {
		List<Map<String, Object>> selectedElements = Lists.newArrayList();
		if (getTable() == null) {
			if (data.size() > 0) {
				setCurrentElementIndex(0);
				selectedElements.add(getCurrentElementInternal());
			}
			return selectedElements;
		}
		RowKeySet selectedIndexs = getTable().getSelectedRowKeys();
		Iterator<Object> iter = selectedIndexs.iterator();
		while (iter.hasNext()) {
			Integer index = (Integer) iter.next();
			selectedElementsIndex.add(index);
		}
		for (Integer selectedIndex : selectedElementsIndex)
			selectedElements.add(data.get(selectedIndex));
		selectedElementsIndex.clear();
		return selectedElements;
	}

	protected Map<String, Object> getCurrentElementInternal() {
		if (data.size() > getCurrentElementIndex())
			return getData().get(getCurrentElementIndex());
		else {
			if (data.size() > 0)
				return data.get(0);
		}
		return Maps.newHashMap();
	}

	public String selectData() {
		UIXTable table = getTable();
		if (table == null) {
			if (getData().size() > 0)
				setCurrentElementIndex(0);
		} else
			setCurrentElementIndex(table.getRowIndex());
		return null;
	}

	public String sortData(SortEvent event) {
		if (event.getSortCriteria().size() > 0) {
			String sortCriterionSelected = ((SortCriterion) event.getSortCriteria().get(0)).getProperty();
			boolean requiresOrdering = true;
			List<OrderByAttribute> orderByAttributes = getOrderByList();
			OrderByAttribute orderByAttributeToRemove = null;
			String[] sortModelAttribute = StringUtils.split(sortCriterionSelected, ".");
			for (OrderByAttribute attribute : orderByAttributes) {
				if (attribute.getModel().equals(sortModelAttribute[0]) && attribute.getAttribute().equals(sortModelAttribute[1])) {
					requiresOrdering = false;
					orderByAttributeToRemove = attribute;
					orderByAttributeToRemove.flipOrder();
					break;
				}
			}
			if (requiresOrdering)
				orderByAttributes.add(new OrderByAttribute(sortModelAttribute[0], sortModelAttribute[1], OrderByAttribute.ASC));
			dataFetched = false;
			fetchData();
		}
		return null;
	}

	public String saveAll(Boolean flushChanges) {
		eventHandler.beforeSave(this);
		getDao().save(getData(), true);
		markDataFetched();
		eventHandler.afterSave(this);
		return null;
	}

	public String update(Boolean flushChanges) {
		eventHandler.beforeUpdate(this);
		getDao().update(getCurrentElementInternal(), true);
		markDataFetched();
		eventHandler.afterUpdate(this);
		return null;
	}

	public List<Map<String, Object>> refresh() {
		IDataControl parentDataControl = getParentDataControl();
		if (parentDataControl != null && getParentChildRelation() != null) {
			parentDataControl.refresh();
			if(parentDataControl.getData().size() == 0){
				if (logger.isDebugEnabled())
					logger.debug("Data refresh not required for[" + getDataControlName() + "] as the parent[" + parentDataControl.getDataControlName()
							+ "] has no data row");
				data.clear();
				return data;
			}	
		}
		eventHandler.beforeRefresh(this);
		logger.info("Refreshing data for Data Control[" + getDataControlName() + "]");
		data = getDao().getAllEntities(getModelList(), getPageSize(), calculateFirstResultToFetch(), getQueryString());
		setCountForLastPageFetch(-1);
		clearSelectedIndexesOfUITable();
		if (data.size() > 0)
			setCurrentElementIndex(0);
		else
			data = Lists.newArrayList();
		eventHandler.afterRefresh(this);
		return data ;
	}
}