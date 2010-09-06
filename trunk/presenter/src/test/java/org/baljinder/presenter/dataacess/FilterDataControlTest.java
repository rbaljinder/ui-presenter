package org.baljinder.presenter.dataacess;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import junit.framework.TestCase;

import org.baljinder.presenter.dataacess.IDataControl;
import org.baljinder.presenter.dataacess.ModelFieldMapping;
import org.baljinder.presenter.dataacess.filter.Filter;
import org.baljinder.presenter.dataacess.internal.DataControl;
import org.baljinder.presenter.dataacess.internal.OrderByAttribute;
import org.baljinder.presenter.dataacess.util.BasicQueryBuilder;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

//TODO: Make me self testable.. this aint a test case
/**
 * @author Baljinder Randhawa
 *
 */
//FIXME: not working
public class FilterDataControlTest extends TestCase {
	
	public void testDataControl(){
		IDataControl dataControl = buildDataControl();
		java.lang.System.out.println("DataControl Query" +dataControl.getQueryString());
		java.lang.System.out.println("DataControl Coutn Query" +getQueryBuilder().getCountQuery(dataControl));
		
	}
	
	public void testDataControlWithFilter(){
		IDataControl dataControl = buildDataControl();
		Map<String,ModelFieldMapping> filterObjectMap = dataControl.getFilterObjectMap() ;
		filterObjectMap.get("transaction.configurationId").setValue("10") ;
		filterObjectMap.get("unitedstatesconsumersubject.firstName").setValue("name") ;
		dataControl.setFilterObjectMap(filterObjectMap);
		java.lang.System.out.println("DataControl With Filter Query" +dataControl.getQueryString());
		java.lang.System.out.println("DataControl With Filter Coutn Query" +getQueryBuilder().getCountQuery(dataControl));
	}
	
	public void testDataControlWithFilterAndOrderBy(){
		IDataControl dataControl = buildDataControl();
		Map<String,ModelFieldMapping> filterObjectMap = dataControl.getFilterObjectMap() ;
		filterObjectMap.get("transaction.transactionId").setValue("10") ;
		filterObjectMap.get("unitedstatesconsumersubject.firstName").setValue("name") ;
		dataControl.setFilterObjectMap(filterObjectMap);
		List<OrderByAttribute> orderBy =  Lists.newArrayList(new OrderByAttribute("transaction", "transactionId", OrderByAttribute.ASC));
		dataControl.setOrderByList(orderBy ); 
		java.lang.System.out.println("DataControl With Filter And Order bY Query" +dataControl.getQueryString());
		java.lang.System.out.println("DataControl With Filter And Order bY Coutn Query" +getQueryBuilder().getCountQuery(dataControl));
	}
	
	
	public void testDataControlWithJoin(){
		IDataControl dataControl = buildDataControl();
		String joiningCriteria = "transaction.transactionId = subject.transactionId AND subject.subjectId = unitedstatesconsumersubject.subjectId (+)";
		dataControl.setJoinCriteria(joiningCriteria);
		Map<String,ModelFieldMapping> filterObjectMap = dataControl.getFilterObjectMap() ;
		filterObjectMap.get("transaction.configurationId").setValue("10") ;
		filterObjectMap.get("unitedstatesconsumersubject.firstName").setValue("name") ;
		dataControl.setFilterObjectMap(filterObjectMap);
		java.lang.System.out.println("DataControl with join Query" +dataControl.getQueryString());
		java.lang.System.out.println("DataControl with join Coutn Query" +getQueryBuilder().getCountQuery(dataControl));
	}
	
	/**
	 * 
	 */
	private IDataControl buildDataControl() {
		IDataControl dataControl = new DataControl(){
			/* (non-Javadoc)
			 * @see org.baljinder.presenter.jsf.ui.dataacess.internal.DataControl#getData()
			 */
			@Override
			public List<Map<String, Object>> getData() {
				Map<String, Object> testMap = Maps.newHashMap();
				testMap.put("system.systemId", 1);
				return Lists.newArrayList(testMap);
			}
		};
		dataControl.setDataControlName("DataControl");
		dataControl.setQueryBuilder(getQueryBuilder());
		List<Class<? extends Object>>modelList = Lists.newArrayList();
	/*	modelList.add(org.baljinder.presenter.ui.model.Transaction.class);
		modelList.add(org.baljinder.presenter.ui.model.Subject.class);
		modelList.add(org.baljinder.presenter.ui.model.UnitedStatesConsumerSubject.class);*/
		modelList.add(System.class);
		dataControl.setModelList(modelList);
		return dataControl;
	}
	
	public void testEmbeddableDataControl(){
		IDataControl dataControl = buildEmbeddableDataControl();
		java.lang.System.out.println("DataControl with embedding Query" +dataControl.getQueryString());
		java.lang.System.out.println("DataControl with embedding Coutn Query" +getQueryBuilder().getCountQuery(dataControl));
	}
	
	
	
	/**
	 * 
	 */
	private IDataControl buildEmbeddableDataControl() {
		IDataControl dataControl = new DataControl(){
			/* (non-Javadoc)
			 * @see org.baljinder.presenter.jsf.ui.dataacess.internal.DataControl#getData()
			 */
			@Override
			public List<Map<String, Object>> getData() {
				return Lists.newArrayList();
			}
		};
		dataControl.setDataControlName("DataControl");
		dataControl.setQueryBuilder(getQueryBuilder());
		List<Class<? extends Object>>modelList = Lists.newArrayList();
	/*	modelList.add(org.baljinder.presenter.ui.model.Transaction.class);
		modelList.add(org.baljinder.presenter.ui.model.Subject.class);
		modelList.add(org.baljinder.presenter.ui.model.UnitedStatesConsumerSubject.class);*/
		modelList.add(System.class);
		dataControl.setModelList(modelList);
		dataControl.setParentDataControl(buildDataControl());
		List<String> attributes = Lists.newArrayList();
		attributes.add("system.systemId = system.systemId"); 
		attributes.add("system.systemId = system.systemId");
		dataControl.setParentChildRelation(attributes);
		return dataControl;
	}
	
	private BasicQueryBuilder  getQueryBuilder(){
		BasicQueryBuilder  queryBuilder = new BasicQueryBuilder();
		Map<String, Map<String, Filter>> filters = Maps.newHashMap();
		Map<String, Filter> internalFilter = new HashMap<String,Filter>();
		internalFilter.put("org.baljinder.presenter.ui.model.System", getMockedSystemIdFiler());
		filters.put("DataControl",internalFilter) ;
		queryBuilder.setFilters(filters);
		return queryBuilder; 
	}
	/**
	 * @return
	 */
	private Filter getMockedSystemIdFiler() {
		return new Filter(){
			public String getFilterProprty() {
				return "systemId = 1";
			}};
		
	}
}
