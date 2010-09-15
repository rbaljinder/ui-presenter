package org.baljinder.presenter.dataacess.internal;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang.StringUtils;
import org.baljinder.presenter.dataacess.IDataController;
import org.baljinder.presenter.dataacess.IPresentationDao;
import org.baljinder.presenter.util.ClassUtils;
import org.baljinder.presenter.util.Utils;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class DataController extends AbstractDataController {

	protected List<Integer> selectedElementsIndex = Lists.newArrayList();

	protected List<Map<String, Object>> newlyCreatedElements = Lists.newArrayList();

	public boolean initialize() {
		eventHandler.beforeInitialize(this);
		newlyCreatedElements.clear();
		data.clear();
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
		data = getDao().getAllEntities(getModelList(), getPageSize(), calculateFirstResultToFetch(), getQuery());
		resetDataControllerAfterDataFetch();
		if (data.size() > 0)
			setCurrentElementIndex(0);
		else
			data = Lists.newArrayList();
		eventHandler.afterDataFetch(this);
	}

	private void resetDataControllerAfterDataFetch(){
		setCountForLastPageFetch(-1);
		clearSelectedElementsIndexes();
	}
	/**
	 * Also determines if we even need to fetch data for this data control or not
	 */
	protected boolean fetchParentIfAny() {
		IDataController parentDataControl = getParentDataControl();
		if (parentDataControl != null && getParentChildRelation() != null) {
			List<Map<String, Object>> parentData = parentDataControl.getData();
			if (parentData.size() == 0) {
				if (logger.isDebugEnabled())
					logger.debug("Data Fetch not required for[" + getDataControlName() + "] as the parent["
							+ parentDataControl.getDataControlName() + "] has no data row");
				data.clear();
				return false; // if parent has no data element then child has nothing to relate to (You cant have child without parent :-)
			}
		}
		return true;
	}

	/** 
	 * 
	 */
	//TODO: get it out to view specific framework ;
	
	public void clearSelectedElementsIndexes() {
		selectedElementsIndex.clear();
		performAfterEvent(Event.CLEAR_SELECTED_UI_ELEMENTS);
	}

	protected Integer calculateFirstResultToFetch() {
		if (getCountForLastPageFetch() == -1)
			return getPageSize() * (getPageCursor());
		else {
			int lastChunkOfRecordsAsPerPageSize = getCountForLastPageFetch() % getPageSize() == 0 ? getPageSize()
					: getCountForLastPageFetch() % getPageSize();
			return getCountForLastPageFetch() - lastChunkOfRecordsAsPerPageSize;
		}
	}

	public String insert() {
		eventHandler.beforeInsert(this);
		for (Class<?> clazz : getModelList()) {
			Object newInstance = ClassUtils.getNewInstance(clazz, "Verify the bean Declartion for classs model name for DataControl["
					+ getDataControlName() + "]");
			final int noOfModelsInvolved = getModelList().size();
			Map<String, Object> newDataElement = Maps.newHashMap();
			for (int index = 0; index < noOfModelsInvolved; index++) {
				String modelName = Utils.getModelName(getModelList(), newInstance);
				newDataElement.put(modelName, newInstance);
				data.add(0, newDataElement);
				newlyCreatedElements.add(newDataElement);
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
		for(Map<String, Object> anElement : data)
			 saveInternal(anElement,flushChanges);
		markDataFetched();
		return null;
	}

	//FIXME: WHAT IS THIS
	protected void saveInternal(Map<String, Object> elementToSave, Boolean flushChanges) {
		if (elementToSave.entrySet().isEmpty())
			return;
		eventHandler.beforeSave(this);
		IPresentationDao dao = getDao();
		for (Entry<String, Object> anEntry : elementToSave.entrySet()) {
			if(anEntry.getValue() == null )
				continue ;
			Map<String, Object> newlyCreatedElement = isNewlyCreatedElement(anEntry);
			if (newlyCreatedElement != null ) {
				dao.create(elementToSave);
				newlyCreatedElements.remove(newlyCreatedElement);
			} else {
				if (flushChanges)
					dao.save(getCurrentElementInternal(), true);
				else
					dao.save(getCurrentElementInternal());
			}
		}
		eventHandler.afterSave(this);
	}
	private Map<String, Object> isNewlyCreatedElement(Entry<String, Object> anEntry){
		for (Map<String, Object> newElementMap : newlyCreatedElements) {
			if(newElementMap.values().contains(anEntry.getValue())){
				return newElementMap;
			}	
		}
		return null ;
	}
	public String saveSelectedElements() {
		eventHandler.beforeSave(this);
		saveSelectedElements(false);
		markDataFetched();
		eventHandler.afterSave(this);
		return null;
	}

	public String saveSelectedElements(Boolean flushChanges) {
		List<Map<String, Object>> selectedElements = getSelectedElements();
		for (Map<String, Object> element : selectedElements)
				saveInternal(element, flushChanges);
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
		List<Map<String, Object>> elementsToRemove =Lists.newArrayList();
		for (Map<String, Object> objectToDelete : data){
				deleteInternal(objectToDelete);
				elementsToRemove.add(objectToDelete);
		}
		for(Map<String, Object> removeMe : elementsToRemove)
			data.remove(removeMe);
		markDataFetched();
		clearSelectedElementsIndexes();
		return null;
	}
//TODO:Merge methods insert/delete with their selected parts
	public String deleteSelectedElements() {
		List<Map<String, Object>> elementsToRemove =Lists.newArrayList();
		List<Map<String, Object>> selectedElements = getSelectedElements();
		for (Map<String, Object> objectToDelete : selectedElements){
				deleteInternal(objectToDelete);
				elementsToRemove.add(objectToDelete);
		}		
		for(Map<String, Object> removeMe : elementsToRemove)
			data.remove(removeMe);
		markDataFetched();
		clearSelectedElementsIndexes();
		return null;
	}
	
	private void deleteInternal(Map<String, Object> objectToDelete) {
		eventHandler.beforeDelete(this);
		for (Entry<String, Object> anEntry : objectToDelete.entrySet()) {
			if(anEntry.getValue() == null )
				continue ;
			Map<String, Object> newlyCreatedElement = isNewlyCreatedElement(anEntry);
			if (newlyCreatedElement != null ) 
				newlyCreatedElements.remove(newlyCreatedElement);
			else
				getDao().delete(objectToDelete);
		}
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

	//TODO: move this to jsf specific part 
	public List<Map<String, Object>> getSelectedElements() {
		List<Map<String, Object>> selectedElements = Lists.newArrayList();
		List<Integer> selectedElementsIndex = getSelectedElementsIndex();
		//TODO: what to with this
		/*RowKeySet selectedIndexs = getTable().getSelectedRowKeys();
		Iterator<Object> iter = selectedIndexs.iterator();
		while (iter.hasNext()) {
			Integer index = (Integer) iter.next();
			selectedElementsIndex.add(index);
		}*/
		if(selectedElementsIndex.size() == 0){
			if (data.size() > 0) {
				setCurrentElementIndex(0);
				selectedElements.add(getCurrentElementInternal());
			}
			return selectedElements;
		}
		for (Integer selectedIndex : selectedElementsIndex)
			selectedElements.add(data.get(selectedIndex));
		//selectedElementsIndex.clear(); //TODO: why clearing
		return selectedElements;
	}

	public void getSelectedElementsIndex(List<Integer> indices) {
		this.selectedElementsIndex= indices;
	}
	
	public List<Integer> getSelectedElementsIndex() {
		return selectedElementsIndex;
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
	
	//If somebody calls this method, then the datacontroller has no information about how to figure out which elements are selected
	//so just raise calling performAfterEvent(), so someone know how to do it, then have a hook to do that
	public String selectData() {
		//at least select something
		if (getData().size() > 0)
			setCurrentElementIndex(0);
		performAfterEvent(Event.SELECT_DATA);
		return null;
	}

	public String sort(String propertyName){
		boolean requiresOrdering = true;
		List<OrderByAttribute> orderByAttributes = getOrderByList();
		OrderByAttribute orderByAttributeToRemove = null;
		String[] sortModelAttribute = StringUtils.split(propertyName, ".");
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
		return null;
	}
	
	public String sort(String []properties){
		throw new UnsupportedOperationException("Sortnig on mulitple properties is not implemented yet");
	}
	
	
	public String update(Boolean flushChanges) {
		eventHandler.beforeUpdate(this);
		getDao().update(getCurrentElementInternal(), true);
		markDataFetched();
		eventHandler.afterUpdate(this);
		return null;
	}

	public List<Map<String, Object>> refresh() {
		setDataFetched(false);
		IDataController parentDataControl = getParentDataControl();
		if (parentDataControl != null && getParentChildRelation() != null) {
			parentDataControl.refresh();
			if (parentDataControl.getData().size() == 0) {
				if (logger.isDebugEnabled())
					logger.debug("Data refresh not required for[" + getDataControlName() + "] as the parent["
							+ parentDataControl.getDataControlName() + "] has no data row");
				data.clear();
				return data;
			}
		}
		eventHandler.beforeRefresh(this);
		logger.info("Refreshing data for Data Control[" + getDataControlName() + "]");
		data = getDao().getAllEntities(getModelList(), getPageSize(), calculateFirstResultToFetch(), getQuery());
		setCountForLastPageFetch(-1);
		clearSelectedElementsIndexes();
		if (data.size() > 0)
			setCurrentElementIndex(0);
		else
			data = Lists.newArrayList();
		eventHandler.afterRefresh(this);
		return data;
	}

	public String create() {
		return insert();
	}

	public List<Map<String, Object>> getNewlyCreatedElement() {
		return newlyCreatedElements;

	}
	
	public void performAfterEvent(Event event) {
	}
}
