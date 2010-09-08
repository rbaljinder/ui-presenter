/**
 * 
 */
package org.baljinder.presenter.dataacess.internal;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.myfaces.trinidad.component.UIXTable;

import org.baljinder.presenter.dataacess.IPresentationDao;
import org.baljinder.presenter.dataacess.IDataAccessStrategy;
import org.baljinder.presenter.dataacess.IDataControl;
import org.baljinder.presenter.dataacess.IEventHandler;
import org.baljinder.presenter.dataacess.ModelFieldMapping;
import org.baljinder.presenter.dataacess.IPersistenceManager;
import org.baljinder.presenter.dataacess.internal.support.EmptyEventHandler;
import org.baljinder.presenter.dataacess.util.IQueryBuilder;
import org.baljinder.presenter.dataacess.util.PresentationConstants;
import org.baljinder.presenter.util.ReflectionUtils;
import org.baljinder.presenter.util.Utils;
import com.google.common.collect.Lists;

/**
 * @author Baljinder Randhawa
 * 
 */
public abstract class AbstractDataControl implements IDataControl {

	protected static final Log logger = LogFactory.getLog(AbstractDataControl.class);

	private IPersistenceManager persistenceManager;

	private UIXTable table = null;

	private Integer pageSize = PresentationConstants.PAGESIZE;

	private Integer pageCursor = 0;

	private String dataControlName;

	private List<Class<? extends Object>> modelList;

	private String joinCriteria;

	private IPresentationDao dao;

	private String daoKeyName;

	private Integer currentElementIndex = 0;

	protected List<Map<String, Object>> data = Lists.newArrayList();

	protected Boolean dataFetched = false;

	private Integer countForLastPageFetch = -1;

	private String defaultWhereClaue;

	protected IDataAccessStrategy dataAccessStrategy;

	protected IEventHandler eventHandler = EmptyEventHandler.doNothingHandler;

	private IDataControl parentDataControl;

	private List<String> parentChildRelation;

	private String propagetedClause;

	private IQueryBuilder queryBuilder;

	private List<OrderByAttribute> orderByList = Lists.newArrayList();

	private Map<String, ModelFieldMapping> filterObjectMap = new HashMap<String, ModelFieldMapping>() {

		private static final long serialVersionUID = 1L;

		@Override
		public ModelFieldMapping get(Object key) {
			if (key == null)
				return null;
			ModelFieldMapping modelFieldMapping = null;
			if (super.containsKey(key))
				modelFieldMapping = super.get(key);
			else {
				String[] defintion = StringUtils.split((String) key, ".");
				String modelName = defintion[0];
				String fieldName = defintion[1];
				modelFieldMapping = new ModelFieldMapping(modelName, fieldName);
				Class<? extends Object> modelClass = Utils.getModelClassOfDataControl(getDataControl(), modelFieldMapping.getModelName());
				Class<? extends Object> fieldType = ReflectionUtils.getFieldTypeOfClass(modelClass, modelFieldMapping.getFieldName());
				modelFieldMapping.setModelClass(modelClass);
				modelFieldMapping.setFieldClass(fieldType);
				put((String) key, modelFieldMapping);
				super.get(key);
			}
			return modelFieldMapping;
		}

	};

	public boolean initialize() {
		if (dao == null)
			dao = persistenceManager.getDAO(getDaoKeyName());
		orderByList.clear();
		return true;
	}

	private IDataControl getDataControl() {
		return this;
	}

	public IPresentationDao getDao() {
		return dao;
	}

	public void setDao(IPresentationDao dao) {
		this.dao = dao;
	}

	public List<Class<? extends Object>> getModelList() {
		return modelList;
	}

	public void setModelList(List<Class<? extends Object>> modelClassList) {
		this.modelList = modelClassList;
	}

	public String getJoinCriteria() {
		return joinCriteria;
	}

	public void setJoinCriteria(String joinCriteria) {
		this.joinCriteria = joinCriteria;
	}

	public Integer getCurrentElementIndex() {
		return currentElementIndex;
	}

	public void setCurrentElementIndex(Integer currentElementIndex) {
		this.currentElementIndex = currentElementIndex;
	}

	public void setData(List<Map<String, Object>> data) {
		this.data = data;
	}

	public void setDataControlName(String name) {
		this.dataControlName = name;
	}

	public String getDataControlName() {
		return dataControlName;
	}

	public String getDaoKeyName() {
		return daoKeyName;
	}

	public void setDaoKeyName(String daoKeyName) {
		this.daoKeyName = daoKeyName;
	}

	public UIXTable getTable() {
		return table;
	}

	public void setTable(UIXTable table) {
		this.table = table;
	}

	public IPersistenceManager getPersistenceManager() {
		return persistenceManager;
	}

	public void setPersistenceManager(IPersistenceManager persistenceManager) {
		this.persistenceManager = persistenceManager;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public Integer getPageCursor() {
		return pageCursor;
	}

	public void setPageCursor(Integer pageCursor) {
		this.pageCursor = pageCursor;
	}

	/**
	 * @return the dataFetched
	 */
	public Boolean getDataFetched() {
		return dataFetched;
	}

	/**
	 * @param dataFetched the dataFetched to set
	 */
	public void setDataFetched(Boolean dataFetched) {
		this.dataFetched = dataFetched;
	}

	/**
	 * @return the defaultWhereClaue
	 */
	public String getDefaultWhereClause() {
		return defaultWhereClaue;
	}

	/**
	 * @param defaultWhereClaue the defaultWhereClaue to set
	 */
	public void setDefaultWhereClause(String defaultWhereClaue) {
		this.defaultWhereClaue = defaultWhereClaue;
	}

	public void setPropagetedClause(String propagetedClause) {
		this.propagetedClause = propagetedClause;
	}

	public String getPropagetedClasue() {
		return propagetedClause;
	}

	/**
	 * @return the queryBuilder
	 */
	public IQueryBuilder getQueryBuilder() {
		return queryBuilder;
	}

	/**
	 * @param queryBuilder the queryBuilder to set
	 */
	public void setQueryBuilder(IQueryBuilder queryBuilder) {
		this.queryBuilder = queryBuilder;
	}

	/**
	 * @return the countForLastPageFetch
	 */
	protected Integer getCountForLastPageFetch() {
		return countForLastPageFetch;
	}

	/**
	 * @param countForLastPageFetch the countForLastPageFetch to set
	 */
	protected void setCountForLastPageFetch(Integer countForLastPageFetch) {
		this.countForLastPageFetch = countForLastPageFetch;
	}

	public String getQuery() {
		return getQueryBuilder().buildQuery(this);
	}

	public String getCountQuery() {
		return getQueryBuilder().getCountQuery(this);
	}

	public int getCurrentPageSize() {
		if (getPageCursor() > 0)
			return getPageCursor();
		return data.size() - getPageSize();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.baljinder.presenter.jsf.ui.dataacess.IDataControl#isNextPossible()
	 */
	public boolean getNextPossible() {
		return data.size() > getPageSize();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.baljinder.presenter.jsf.ui.dataacess.IDataControl#isPrevPossible()
	 */
	public boolean getPrevPossible() {
		return getPageCursor() > 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.baljinder.presenter.jsf.ui.dataacess.IDataControl#first()
	 */
	public String first() {
		setPageCursor(0);
		dataFetched = false;
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.baljinder.presenter.jsf.ui.dataacess.IDataControl#last()
	 */
	public String last() {
		setCountForLastPageFetch(calculateLastPageCursor());
		int pageCursor = getCountForLastPageFetch() / getPageSize();
		setPageCursor(pageCursor);
		dataFetched = false;
		return null;
	}

	/**
	 * 
	 */
	protected Integer calculateLastPageCursor() {
		return getDao().getCountOfRecords(getCountQuery());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.baljinder.presenter.jsf.ui.dataacess.IDataControl#next()
	 */
	public String next() {
		setPageCursor(getPageCursor() + 1);
		dataFetched = false;
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.baljinder.presenter.jsf.ui.dataacess.IDataControl#prev()
	 */
	public String prev() {
		setPageCursor(getPageCursor() - 1);
		dataFetched = false;
		return null;
	}

	/**
	 * @return the filterObjectMap
	 */
	public Map<String, ModelFieldMapping> getFilterObjectMap() {
		return filterObjectMap;
	}

	/**
	 * @param filterObjectMap the filterObjectMap to set
	 */
	public void setFilterObjectMap(Map<String, ModelFieldMapping> filterObjectMap) {
		this.filterObjectMap = filterObjectMap;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.baljinder.presenter.jsf.ui.dataacess.Filterable#applySearch()
	 */
	public void applySearch() {
		setDataFetched(false);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.baljinder.presenter.jsf.ui.dataacess.Filterable#clear()
	 */
	public void clear() {
		clearFilterValues();
		setDataFetched(false);
	}

	public void clearFilterValues() {
		for (ModelFieldMapping modelFieldMapping : filterObjectMap.values()) {
			modelFieldMapping.setValue(null);
		}
	}

	/**
	 * @return the orderByList
	 */
	public List<OrderByAttribute> getOrderByList() {
		return orderByList;
	}

	/**
	 * @param orderByList the orderByList to set
	 */
	public void setOrderByList(List<OrderByAttribute> orderByList) {
		this.orderByList = orderByList;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.baljinder.presenter.jsf.ui.dataacess.extension.IEmbeddableDataControl#getParentProprty()
	 */
	public List<String> getParentChildRelation() {
		return parentChildRelation;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.baljinder.presenter.jsf.ui.dataacess.extension.IEmbeddableDataControl#setParentProprty(java.lang.String)
	 */
	public void setParentChildRelation(List<String> parentChildRelation) {
		this.parentChildRelation = parentChildRelation;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.baljinder.presenter.jsf.ui.dataacess.extension.IEmbeddableDataControl#getParentDataControl()
	 */
	public IDataControl getParentDataControl() {
		return parentDataControl;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.baljinder.presenter.jsf.ui.dataacess.extension.IEmbeddableDataControl#setParentDataControl(org.baljinder.presenter.jsf.ui.dataacess
	 * .IDataControl)
	 */
	public void setParentDataControl(IDataControl parentDataControl) {
		this.parentDataControl = parentDataControl;
	}

	/**
	 * 
	 */
	abstract protected List<Map<String, Object>> fetchData();

	/**
	 * @return the dataAccessStrategy
	 */
	public IDataAccessStrategy getDataAccessStrategy() {
		return dataAccessStrategy;
	}

	/**
	 * @param dataAccessStrategy the dataAccessStrategy to set
	 */
	public void setDataAccessStrategy(IDataAccessStrategy dataAccessStrategy) {
		this.dataAccessStrategy = dataAccessStrategy;
	}

	public boolean shouldFetchData() {
		return getDataAccessStrategy().shouldFetch(this);
	}

	public void markDataFetched() {
		getDataAccessStrategy().markFetched(this);
	}

	/**
	 * @return the eventHandler
	 */
	public IEventHandler getEventHandler() {
		return eventHandler;
	}

	/**
	 * @param eventHandler the eventHandler to set
	 */
	public void setEventHandler(IEventHandler eventHandler) {
		this.eventHandler = eventHandler;
	}
}
