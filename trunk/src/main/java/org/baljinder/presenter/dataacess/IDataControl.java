package org.baljinder.presenter.dataacess;

import java.util.List;
import java.util.Map;

import org.apache.myfaces.trinidad.component.UIXTable;
import org.apache.myfaces.trinidad.event.SortEvent;

import org.baljinder.presenter.dataacess.internal.OrderByAttribute;
import org.baljinder.presenter.dataacess.util.IQueryBuilder;

//TODO: add support for default order by for data control; as of now its just run time
public interface IDataControl extends SupportsEventHandler {

	public void setDataControlName(String name);

	public String getDataControlName();

	public void setModelList(List<Class<? extends Object>> modelClassList);

	public List<Class<? extends Object>> getModelList();

	public void setJoinCriteria(String joinCriteria);

	public String getJoinCriteria();

	/**
	 * @return the filterObjectMap
	 */
	public Map<String, ModelFieldMapping> getFilterObjectMap() ;
	/**
	 * @param filterObjectMap the filterObjectMap to set
	 */
	public void setFilterObjectMap(Map<String, ModelFieldMapping> filterObjectMap) ;

	public List<Map<String, Object>> getData();

	public void applySearch();

	public void clear();

	public void clearFilterValues();

	public boolean initialize();

	public String sortData(SortEvent event);

	public String insert();

	public String save();
	
	public String save(Boolean flushChanges);

	public String saveAll();
	
	public String saveAll(Boolean flushChanges);

	public String update();

	public String update(Boolean flushChanges);
	
	public String delete();
	
	public List<Map<String, Object>> refresh();

	public Map<String, Object> getCurrentElement();
	
	public List<Map<String, Object>> getSelectedElements() ;
	
	public boolean getPrevPossible();

	public boolean getNextPossible();

	public String first();

	public String last();

	public String prev();

	public String next();

	public Boolean getDataFetched();

	public void setDataFetched(Boolean dataFetched);

	public Integer getPageSize();

	public void setPageSize(Integer pageSize);

	public Integer getPageCursor();

	public void setPageCursor(Integer pageCursor);

	public String getDefaultWhereClause();

	public void setDefaultWhereClause(String whereClause);

	/**
	 * @return the orderByList
	 */
	public List<OrderByAttribute> getOrderByList() ;

	/**
	 * @param orderByList the orderByList to set
	 */
	public void setOrderByList(List<OrderByAttribute> orderByList) ;

	/**
	 * @return the queryBuilder
	 */
	public IQueryBuilder getQueryBuilder() ;

	/**
	 * @param queryBuilder
	 *            the queryBuilder to set
	 */
	public void setQueryBuilder(IQueryBuilder queryBuilder) ;
	
	public PresentationLookupPersistenceManager getPersistenceManager() ;

	public void setPersistenceManager(PresentationLookupPersistenceManager persistenceManager) ;
		
	public void setDataAccessStrategy(IDataAccessStrategy accessStrategy);
	
	public IDataAccessStrategy  getDataAccessStrategy();
	
	public  String getQueryString() ;

	public void setParentDataControl(IDataControl parentDataControl);

	public IDataControl getParentDataControl();

	public void setParentChildRelation(List<String> parentChildRelation);

	public List<String> getParentChildRelation();

	/**
	 * @param join
	 */
	public void setPropagetedClause(String join);
	
	public String getPropagetedClasue();
	
	public UIXTable getTable() ;
	
	public String selectData() ;
}
