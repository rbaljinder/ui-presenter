package org.baljinder.presenter.dataacess;

import java.util.List;
import java.util.Map;

import org.apache.myfaces.trinidad.component.UIXTable;
import org.apache.myfaces.trinidad.event.SortEvent;

import org.baljinder.presenter.dataacess.internal.OrderByAttribute;
import org.baljinder.presenter.dataacess.util.IQueryBuilder;

/**
 * 
 * This interface is the central point to interact with this CRUD layer for various operation. Following is the list of
 * main operations:
 * <ul>
 * <li>Fetch the data from database based on the model class list set on the data control</li>
 * <li>Search based on the dynamic search criteria(but has support for default clauses, both will be merged at runtime)</li>
 * <li>Sorting of the data list. As of now it fetches data from database on every sorting event, may not be suggested for large data set</li>
 * <li>Pagination(first/previous/next/last), page size can be set while defining the data control</li>
 * <li>Save/Update/Delete either the currently selected data elements or the entire data list loaded for data control</li>
 * <li>Joining of multiple models based on a join criteria(which is required to be set while defining the data control)</li>
 * <li>Event handling mechanism for various useful events(load/save/delete/update)</li>
 * </ul>
 * 
 * @author baljinder
 * 
 */
//TODO: include support for sorting only the loaded data list, instead of only database fetch based sorting
//TODO:Elaborate the use cases and how to of teh above lis of operations 
//TODO: add support for default order by for data control; as of now its just run time
//TODO: verify insert new/create works or not
public interface IDataControl extends SupportsEventHandler {

	/**
	 * Set the name of the data control. This will be the bean-name/bean-id/managed-bean-name(for jsf) for the data
	 * control.
	 * 
	 * @param name
	 */
	public void setDataControlName(String name);

	/**
	 * Get the data control name. This will be the bean-name/bean-id/managed-bean-name(for jsf) for the data control.
	 * 
	 * @return name
	 */
	public String getDataControlName();

	/**
	 * Set the list of domain model classes(Entity). The whole processing of query generation/data fetch/populating the
	 * data list to return, all depends on the model class being set.
	 * 
	 * @param modelClassList
	 */
	public void setModelList(List<Class<? extends Object>> modelClassList);

	/**
	 * Get the list of domain model classes
	 * @return
	 */
	public List<Class<? extends Object>> getModelList();

	/**
	 * Set the join criteria, which will be utilized during query building process. Be careful while defining this in bean configuration.
	 * There is a restriction on the naming convention. 
	 *  
	 * @param joinCriteria
	 */
	//TODO: elaborate this with example
	public void setJoinCriteria(String joinCriteria);

	/**
	 * Get the join criteria for this data control
	 * @return
	 */
	public String getJoinCriteria();

	/**
	 * This is used for providing the runtime search filtering support. 
	 * @return the filterObjectMap
	 */
	public Map<String, ModelFieldMapping> getFilterObjectMap();

	/**
	 * @param filterObjectMap the filterObjectMap to set
	 */
	public void setFilterObjectMap(Map<String, ModelFieldMapping> filterObjectMap);

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

	public List<Map<String, Object>> getSelectedElements();

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
	public List<OrderByAttribute> getOrderByList();

	/**
	 * @param orderByList the orderByList to set
	 */
	public void setOrderByList(List<OrderByAttribute> orderByList);

	/**
	 * @return the queryBuilder
	 */
	public IQueryBuilder getQueryBuilder();

	/**
	 * @param queryBuilder the queryBuilder to set
	 */
	public void setQueryBuilder(IQueryBuilder queryBuilder);

	public IPersistenceManager getPersistenceManager();

	public void setPersistenceManager(IPersistenceManager persistenceManager);

	public void setDataAccessStrategy(IDataAccessStrategy accessStrategy);

	public IDataAccessStrategy getDataAccessStrategy();

	public String getQuery();

	public String getCountQuery();

	public void setParentDataControl(IDataControl parentDataControl);

	public IDataControl getParentDataControl();

	public void setParentChildRelation(List<String> parentChildRelation);

	public List<String> getParentChildRelation();

	/**
	 * @param join
	 */
	public void setPropagetedClause(String join);

	public String getPropagetedClasue();

	public UIXTable getTable();

	public String selectData();
}
