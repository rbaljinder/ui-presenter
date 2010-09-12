package org.baljinder.presenter.dataacess;

import java.util.List;
import java.util.Map;

import org.baljinder.presenter.dataacess.internal.OrderByAttribute;
import org.baljinder.presenter.dataacess.util.IQueryBuilder;

/**
 * 
 * This interface is the central point to interact with this CRUD layer for various operation. Following is the list of main operations:
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
 * 
 *Spring Configuration(using the namespace provided with this framework):
 * 
 * <code>
 * A fully configured data controller can look like this
 * <ps:data-control name="All_Properties_Configurable_DataControl"
			init-method="initialize" scope="singleton" size="20" access-strategy="direct"
			event-handler="org.baljinder.presenter.testing.support.DoNothingEventHandler">
			<ps:type class="org.baljinder.presenter.dataacess.internal.DataController" />
			<ps:default-where-clause clause="system.systemId = 1" />
			<ps:dao-key-name
				key="org.baljinder.presenter.dataacess.internal.GenericPresentationDao" />
			<ps:persistence-manager manager="presentationPersistence" />
			<ps:query-builder builder="defaultQueryBuilder" />
			<ps:model class="org.baljinder.presenter.testing.support.model.System" />
			<ps:model class="org.baljinder.presenter.testing.support.model.AnotherSystem" />
			<ps:join-criteria criteria="system.systemId = anotherSystem.systemId"/>
			<ps:data-control name="Basic_Child_DataControl"
				access-strategy="direct" scope="singleton">
				<ps:model class="org.baljinder.presenter.testing.support.model.Client" />
				<ps:parent-relations>
					<ps:parent-relation relation="client.clientId = system.clientId" />
				</ps:parent-relations>
			</ps:data-control>
		</ps:data-control>
	</code>
 * But only following configuration is enough with some sensible defaults( set by the Namespace hanlder while building bean definition)
 * 	<ps:data-control name="another_dataControl_dataAccess_test">
		<ps:model class="org.baljinder.presenter.testing.support.model.TestTable" />
	</ps:data-control> 
 * @author baljinder
 * 
 */
// TODO: include support for sorting only the loaded data list, instead of only database fetch based sorting
// TODO:Elaborate the use cases and how to of teh above lis of operations
// TODO: add support for default order by for data control; as of now its just run time
// TODO: verify insert new/create works or not
public interface IDataController extends SupportsEventHandler {

	public enum Event {
		CLEAR_SELECTED_UI_ELEMENTS, SELECT_DATA
	};

	/**
	 * Set the name of the data control. This will be the bean-name/bean-id/managed-bean-name(for jsf) for the data control.
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
	 * The data elements loaded/created by this data controller. It returns a list of Maps. where each map represents an element from the
	 * data controller perspective. They key in that map element will be the class name(alias) of a model(which is determined by the model
	 * list set for this controller) and the value is the object instance of the same model.
	 * <p>
	 * A Data Controller supports data from multiple domain object(based on some join criteria usually). And that is the reason for having
	 * map for each data element, so that each row(database) can be loaded in one data element (Data Controller), where row can have data from multiple
	 * table.
	 * </p>
	 * Usage: 
	 * <code>
	 * 		List<Map<String, Object>> data = dataController.getData() ;
	 * 		Map<String, Object> firstRow = data.get(0); 
	 * 		System someSystem = (System)firstRow.get("system"); 
	 * 		Client someClient = (Client)firstRow.get("client");
	 * </code> No type safety here.
	 * 
	 * @return
	 */
	public List<Map<String, Object>> getData();

	/**
	 * Set the list of domain model classes(Entity). The whole processing of query generation/data fetch/populating the data list to return,
	 * all depends on the model class being set.
	 * 
	 * Configuration(As per ps: namespace)
	 * 		<ps:model class="org.baljinder.presenter.testing.support.model.System" />
			<ps:model class="org.baljinder.presenter.testing.support.model.AnotherSystem" />
	 * @param modelClassList
	 */
	public void setModelList(List<Class<? extends Object>> modelClassList);

	/**
	 * Get the list of domain model classes
	 * 
	 * @return
	 */
	public List<Class<? extends Object>> getModelList();

	/**
	 * Set the join criteria, which will be utilized during query building process. Be careful while defining this in bean configuration.
	 * There is a restriction on the naming convention.
	 * 
	 * Configuration(As per ps: namespace)
	 *  <ps:join-criteria criteria="system.systemId = anotherSystem.systemId"/>
	 * @param joinCriteria
	 */
	// TODO: elaborate this with example
	public void setJoinCriteria(String joinCriteria);

	/**
	 * Get the join criteria for this data control
	 * @return
	 */
	public String getJoinCriteria();

	/**
	 * This is used for providing the runtime search filtering support.
	 * 
	 * @return the filterObjectMap
	 */
	public Map<String, ModelFieldMapping> getFilterObjectMap();

	/**
	 * @param filterObjectMap the filterObjectMap to set
	 */
	public void setFilterObjectMap(Map<String, ModelFieldMapping> filterObjectMap);

	public void applySearch();

	public void clear();

	public void clearFilterValues();

	public boolean initialize();

	public String create();

	public String insert();

	public List<Map<String, Object>> getNewlyCreatedElement();

	public String save();

	public String save(Boolean flushChanges);

	public String saveSelectedElements();

	public String saveSelectedElements(Boolean flushChanges);

	public String update();

	public String update(Boolean flushChanges);

	public String delete();

	public String deleteSelectedElements();

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

	public String sort(String propertyName);

	public String sort(String[] proprties);

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

	public void setParentDataControl(IDataController parentDataControl);

	public IDataController getParentDataControl();

	public void setParentChildRelation(List<String> parentChildRelation);

	public List<String> getParentChildRelation();

	/**
	 * @param join
	 */
	public void setPropagetedClause(String join);

	public String getPropagetedClasue();

	public String selectData();

	public void getSelectedElementsIndex(List<Integer> indices);

	public List<Integer> getSelectedElementsIndex();

	public void performAfterEvent(Event event);

}
