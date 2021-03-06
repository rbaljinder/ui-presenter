# ui-presenter
CRUD/ data acces layer for presentation frameworks. JSF module provides support for jsf to utilize this, It has been generalized so that it can be used with any other presentation technology.

Check out the demo project presenter-demo

JavaDoc? from the main interface IDataConroller:

This interface is the central point to interact with this CRUD layer for various operation. Following is the list of main operations:

Fetch the data from database based on the model class list set on the data control
Search based on the dynamic search criteria(but has support for default clauses, both will be merged at runtime)
Sorting of the data list. As of now it fetches data from database on every sorting event, may not be suggested for large data set
Pagination(first/previous/next/last), page size can be set while defining the data control
Save/Update/Delete either the currently selected data elements or the entire data list loaded for data control
Joining of multiple models based on a join criteria(which is required to be set while defining the data control)
Event handling mechanism for various useful events(load/save/delete/update)
Spring Configuration(using the namespace provided with this framework): A fully configured data controller can look like this <ps:data-control name="All_Properties_Configurable_DataControl?"
﻿ ﻿ init-method="initialize" scope="singleton" size="20" access-strategy="direct" ﻿ ﻿ event-handler="org.baljinder.presenter.testing.support.DoNothingEventHandler?"> ﻿ ﻿
<type class="org.baljinder.presenter.dataacess.internal.DataController">
</type>
﻿ ﻿
<default-where-clause clause="system.systemId = 1">
</default-where-clause>
﻿ ﻿ <ps:dao-key-name ﻿ ﻿ ﻿ key="org.baljinder.presenter.dataacess.internal.GenericPresentationDao?" /> ﻿ ﻿
<persistence-manager manager="presentationPersistence">
</persistence-manager>
﻿ ﻿
<query-builder builder="defaultQueryBuilder">
</query-builder>
﻿ ﻿
<model class="org.baljinder.presenter.testing.support.model.System">
</model>
﻿ ﻿
<model class="org.baljinder.presenter.testing.support.model.AnotherSystem">
</model>
﻿ ﻿
<join-criteria criteria="system.systemId = anotherSystem.systemId">
</join-criteria>
﻿ ﻿ <ps:data-control name="Basic_Child_DataControl?" ﻿ ﻿ ﻿ access-strategy="direct" scope="singleton"> ﻿ ﻿ ﻿
<model class="org.baljinder.presenter.testing.support.model.Client">
</model>
﻿ ﻿ ﻿
<parent-relations>
﻿ ﻿ ﻿ ﻿
<parent-relation relation="client.clientId = system.clientId">
</parent-relation>
﻿ ﻿ ﻿
</parent-relations>
﻿ ﻿
Unknown end tag for </data-control>

﻿
Unknown end tag for </data-control>

But only following configuration is enough with some sensible defaults( set by the Namespace handler while building bean definition)
<data-control name="another_dataControl_dataAccess_test">
﻿ ﻿
<model class="org.baljinder.presenter.testing.support.model.TestTable">
</model>
</data-control>
public interface IDataController extends SupportsEventHandler? {
﻿ public enum Event { ﻿ ﻿ CLEAR_SELECTED_UI_ELEMENTS, SELECT_DATA ﻿ };

﻿ public enum Operation { ﻿ ﻿ CREATE,DELETE,UPDATE,SAVE,SELECT,SORT ﻿ }; ﻿ / ﻿ Set the name of the data control. This will be the bean-name/bean-id/managed-bean-name(for jsf) for the data control. ﻿ ﻿ @param name ﻿ / ﻿ public void setDataControlName(String name);

﻿ / ﻿ Get the data control name. This will be the bean-name/bean-id/managed-bean-name(for jsf) for the data control. ﻿ ﻿ @return name ﻿ / ﻿ public String getDataControlName();

﻿ / ﻿ The data elements loaded/created by this data controller. It returns a list of Maps. where each map represents an element from the ﻿ data controller perspective. They key in that map element will be the class name(alias) of a model(which is determined by the model ﻿ list set for this controller) and the value is the object instance of the same model. ﻿

﻿ A Data Controller supports data from multiple domain object(based on some join criteria usually). And that is the reason for having ﻿ map for each data element, so that each row(database) can be loaded in one data element (Data Controller), where row can have data ﻿ from multiple table. ﻿

﻿ ﻿ Usage: ﻿ ﻿
 ﻿   * <code> ﻿   * ﻿  ﻿  List<Map<String, Object>> data = dataController.getData() ;
 ﻿   * ﻿  ﻿  Map<String, Object> firstRow = data.get(0); 
 ﻿   * ﻿  ﻿  System someSystem = (System)firstRow.get("system"); 
 ﻿   * ﻿  ﻿  Client someClient = (Client)firstRow.get("client");
 ﻿   * 
Unknown end tag for </code>

 ﻿    
﻿ ﻿ No type safety here. ﻿ ﻿
 ﻿   * Whenever this method is called, controller will try to figure out whether it needs to fetch data from database or not. {@link DataAccessStartegy} is used to determine this.
 ﻿   * The default strategy {@link org.baljinder.presenter.dataacess.internal.support.DirectDataAccessStrategy} will only check for dataFetch variable. if its false then data will be loaded 
 ﻿   * through the {@link IPresentationDao}.And the variable will be marked false until some other operations needs to fetch data from database
 ﻿   * 
﻿ ﻿ @return ﻿ / ﻿ public List<Map<String, Object>> getData(); ﻿ ﻿ / ﻿ As of now this is just a convenience method. But it may be used for state-less data controller, a possibility. ﻿ @param first ﻿ @param pageSize ﻿ @param sortField ﻿ @param sortOrder ﻿ @param filters ﻿ @return ﻿ / ﻿ //TODO: add test cases ﻿ public List<Map<String, Object>> getData(int first, int pageSize, String sortField, boolean sortOrder, Map<String, String> filters) ;
﻿ / ﻿ Set the list of domain model classes(Entity). The whole processing of query generation/data fetch/populating the data list to return, ﻿ all depends on the model class being set. ﻿ ﻿ Configuration(As per ps: namespace) ﻿ ﻿

 ﻿   * {@code
 ﻿   *  <ps:model class="org.baljinder.presenter.testing.support.model.System" /> 
 ﻿   *  <ps:model class="org.baljinder.presenter.testing.support.model.AnotherSystem" /> ﻿   * }
 ﻿   * 
﻿ ﻿ @param modelClassList ﻿ / ﻿ public void setModelList(List<Class<? extends Object>> modelClassList);
﻿ / ﻿ Get the list of domain model classes ﻿ ﻿ @return ﻿ / ﻿ public List<Class<? extends Object>> getModelList();

﻿ / ﻿ Set the join criteria, which will be utilized during query building process. Be careful while defining this in bean configuration. ﻿ There is a restriction on the naming convention. ﻿ ﻿ Configuration(As per ps: namespace) ﻿ ﻿

 ﻿   * {@code
 ﻿   * <ps:join-criteria criteria="system.systemId = anotherSystem.systemId"/> ﻿   * }
 ﻿   * 
﻿ ﻿ @param joinCriteria ﻿ / ﻿ public void setJoinCriteria(String joinCriteria);
﻿ / ﻿ Get the join criteria for this data control ﻿ ﻿ @return ﻿ / ﻿ public String getJoinCriteria();

﻿ / ﻿ This is used for providing the runtime search filtering support. ﻿ ﻿ @return the filterObjectMap ﻿ / ﻿ public Map<String, ModelFieldMapping?> getFilterObjectMap();

﻿ / ﻿ @param filterObjectMap the filterObjectMap to set ﻿ / ﻿ public void setFilterObjectMap(Map<String, ModelFieldMapping?> filterObjectMap);

﻿ public void applySearch();

﻿ public void clear();

﻿ public void clearFilterValues();

﻿ public boolean initialize();

﻿ public String create();

﻿ public String insert();

﻿ public List<Map<String, Object>> getNewlyCreatedElement();

﻿ public String save();

﻿ public String save(Boolean flushChanges);

﻿ public String saveSelectedElements();

﻿ public String saveSelectedElements(Boolean flushChanges);

﻿ public String update();

﻿ public String update(Boolean flushChanges);

﻿ public String delete();

﻿ public String deleteSelectedElements();

﻿ public List<Map<String, Object>> refresh();

﻿ public Map<String, Object> getCurrentElement();

﻿ public List<Map<String, Object>> getSelectedElements();

﻿ // List of standard pagination operations ﻿ / ﻿ Are there elements present in the data controller loaded earlier. Simply put from UI we usually want to get to the previous data ﻿ loaded by the controller. Depends on the page size. ﻿ ﻿ @return ﻿ / ﻿ public boolean getPrevPossible();

﻿ / ﻿ Are there more elements present then what we have currently fetched. ﻿ ﻿ @return ﻿ / ﻿ public boolean getNextPossible();

﻿ / ﻿ Move to the beginning data list (based on the no of elements to be loaded by controller) ﻿ ﻿ @return ﻿ / ﻿ public String first();

﻿ / ﻿ Move to the end of the data list (based on the no of elements to be loaded by controller) ﻿ ﻿ @return ﻿ / ﻿ public String last();

﻿ / ﻿ Move to the previous data set loaded by controller. ﻿ ﻿ @return ﻿ / ﻿ public String prev();

﻿ / ﻿ Move to next data set to be loaded by controller. ﻿ ﻿ @return ﻿ / ﻿ public String next();

﻿ / ﻿ This is sort of marker for figuring out whether data has been fetched or not. This is used in various operations of data controller. ﻿ ﻿ @return ﻿ / ﻿ public void setDataFetched(Boolean dataFetched);

﻿ / ﻿ Is the data fetched form database. ﻿ ﻿ @return ﻿ / ﻿ public Boolean getDataFetched();

﻿ / ﻿ The no of elements to be loaded (fetched) at a time by the controller. More appropriately its the size. ﻿ ﻿ @return ﻿ / ﻿ public Integer getPageSize();

﻿ / ﻿ Set the no of elements to be fetched by the controller in one go. ﻿ ﻿ @param pageSize ﻿ / ﻿ public void setPageSize(Integer pageSize);

﻿ / ﻿ Controller keeps track of a page cursor which is utilized for pagination. ﻿ ﻿ @return ﻿ / ﻿ public Integer getPageCursor();

﻿ / ﻿ Set the page cursor. ﻿ ﻿ @param pageCursor ﻿ / ﻿ public void setPageCursor(Integer pageCursor);

﻿ / ﻿ Get the default where clause. This field is set during the data controller configuration and it gets appended to the query(as it as). ﻿ ﻿ @return ﻿ / ﻿ public String getDefaultWhereClause();

﻿ / ﻿ Set the default where clause. ﻿ ﻿ @param whereClause ﻿ / ﻿ public void setDefaultWhereClause(String whereClause);

﻿ / ﻿ Sort the data elements of the data controller based on this property. As each element is actually a map of model name and ﻿ corresponding objects, we need to pass in fully qualified property name. e.g ﻿ ﻿

 ﻿   * dataController.sort(&quot;system.systemName&quot;); // where system is the model alias and system name is the property on which sorting will be done.
 ﻿   * 
﻿ ﻿ @param propertyName ﻿ @return ﻿ / ﻿ public String sort(String propertyName);
﻿ / ﻿ Sort the data elements based on the list of properties. ﻿ ﻿ @param proprties ﻿ @return ﻿ / ﻿ public String sort(String proprties);

﻿ / ﻿ @return the orderByList ﻿ / ﻿ public List

<orderbyattribute>
getOrderByList();
﻿ / ﻿ @param orderByList the orderByList to set ﻿ / ﻿ public void setOrderByList(List

<orderbyattribute>
orderByList);
﻿ / ﻿ @return the queryBuilder ﻿ / ﻿ public IQueryBuilder getQueryBuilder();

﻿ / ﻿ @param queryBuilder the queryBuilder to set ﻿ / ﻿ public void setQueryBuilder(IQueryBuilder queryBuilder);

﻿ public IPersistenceManager getPersistenceManager();

﻿ public void setPersistenceManager(IPersistenceManager persistenceManager);

﻿ public void setDataAccessStrategy(IDataAccessStrategy accessStrategy);

﻿ public IDataAccessStrategy getDataAccessStrategy();

﻿ public String getQuery();

﻿ public String getCountQuery();

﻿ public void setParentDataControl(IDataController parentDataControl);

﻿ public IDataController getParentDataControl();

﻿ public void setParentChildRelation(List

<string>
parentChildRelation);
﻿ public List

<string>
getParentChildRelation();
﻿ / ﻿ @param join ﻿ / ﻿ public void setPropagetedClause(String join);

﻿ public String getPropagetedClasue();

﻿ public String selectData();

﻿ public void setSelectedElementsIndex(List

<integer>
indices);
﻿ public List

<integer>
getSelectedElementsIndex();
﻿ //TODO: more convinence methods ﻿ public void setSelectedDataElement(Map<String, Object> element) ;

﻿ public void setSelectedDataElements(Map<String, Object> elements) ;

﻿ public Map<String, Object> getSelectedDataElement() ; ﻿ ﻿ public Map<String, Object> getSelectedDataElements() ;

﻿ public void performAfterEvent(Event event); ﻿ ﻿ / ﻿ Set default outcome(a string value that can be used for navigation or other purpose) for following operations : ﻿ ﻿

﻿
Create
﻿
Save
﻿
Update
﻿
Delete
﻿
Sort
﻿
Select
﻿
﻿ ﻿ @param actionOutcomes ﻿ / ﻿ public void setActionOutcome(Map<Operation,String> actionOutcomes) ; ﻿ ﻿ / ﻿ Get default outcome(a string value that can be used for navigation or other purpose) for operations. ﻿ ﻿ @return ﻿ / ﻿ public Map<Operation,String> getActionOutcome() ;
}
