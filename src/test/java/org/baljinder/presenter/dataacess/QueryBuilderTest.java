package org.baljinder.presenter.dataacess;

import java.util.List;
import java.util.Map;

import junit.framework.TestCase;

import org.baljinder.presenter.dataacess.internal.OrderByAttribute;
import org.baljinder.presenter.testing.support.model.AnotherTestTable;
import org.baljinder.presenter.testing.support.model.DomainModel;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

//TODO: you need to learn how to write test cases.. go get a book(note to self)
public class QueryBuilderTest extends TestCase {

	public void testBasicDataControl() {
		IDataController basicDataControl = DataControllerBuilder.getBasicDataControlWithMockedData("basicDataControl",
				DataControllerBuilder.getModelListForClasses(DomainModel.class));
		assertTrue(basicDataControl.getQuery().equals(" from DomainModel domainModel "));
		assertTrue(basicDataControl.getCountQuery().equals(" Select count(*)  from DomainModel domainModel "));
	}

	public void testDataControlWithFilter() {
		//					   " from DomainModel domainModel  where  (  (  ( domainModel.testId = '10' )  and  ( domainModel.name = 'name' )  )  ) " 
		String expectedQuery = " from DomainModel domainModel  where  (  (  ( domainModel.name = 'name' )  and  ( domainModel.testId = '10' )  )  ) ";
		String expectedCountQuery = " Select count(*)  from DomainModel domainModel  where  (  (  ( domainModel.name = 'name' )  and  ( domainModel.testId = '10' )  )  ) ";
		IDataController dataControl = DataControllerBuilder.getBasicDataControlWithMockedData("basicDataControl",
				DataControllerBuilder.getModelListForClasses(DomainModel.class));
		Map<String, ModelFieldMapping> filterObjectMap = dataControl.getFilterObjectMap();
		filterObjectMap.get("domainModel.name").setValue("name");
		filterObjectMap.get("domainModel.testId").setValue("10");
		dataControl.setFilterObjectMap(filterObjectMap);
		assertTrue(dataControl.getQuery().equals(expectedQuery));
		assertTrue(dataControl.getCountQuery().equals(expectedCountQuery));
	}

	public void testDataControlWithFilterAndOrderBy() {
		String expectedQuery = " from DomainModel domainModel  where  (  (  ( domainModel.name = 'name' )  and  ( domainModel.testId = '10' )  )  )  order by domainModel.testId ASC";
		String expectedCountQuery = " Select count(*)  from DomainModel domainModel  where  (  (  ( domainModel.name = 'name' )  and  ( domainModel.testId = '10' )  )  ) ";
		IDataController dataControl = DataControllerBuilder.getBasicDataControlWithMockedData("basicDataControl",
				DataControllerBuilder.getModelListForClasses(DomainModel.class));
		Map<String, ModelFieldMapping> filterObjectMap = dataControl.getFilterObjectMap();
		filterObjectMap.get("domainModel.name").setValue("name");
		filterObjectMap.get("domainModel.testId").setValue("10");
		dataControl.setFilterObjectMap(filterObjectMap);
		List<OrderByAttribute> orderBy = Lists.newArrayList(new OrderByAttribute("domainModel", "testId", OrderByAttribute.ASC));
		dataControl.setOrderByList(orderBy);
		assertTrue(dataControl.getQuery().equals(expectedQuery));
		assertTrue(dataControl.getCountQuery().equals(expectedCountQuery));
	}

	public void testDataControlWithJoin() {
		String expectedQuery = " from DomainModel domainModel , AnotherTestTable anotherTestTable  where  (  ( domainModel.testId = anotherTestTable.testId AND domainModel.name = anotherTestTable.name )  and  (  ( domainModel.name = 'name' )  and  ( domainModel.testId = '10' )  )  ) ";
		String expectedCountQuery=" Select count(*)  from DomainModel domainModel , AnotherTestTable anotherTestTable  where  (  ( domainModel.testId = anotherTestTable.testId AND domainModel.name = anotherTestTable.name )  and  (  ( domainModel.name = 'name' )  and  ( domainModel.testId = '10' )  )  ) ";
		IDataController dataControl = DataControllerBuilder.getBasicDataControlWithMockedData("basicDataControl",
				DataControllerBuilder.getModelListForClasses(DomainModel.class, AnotherTestTable.class));
		String joiningCriteria = "domainModel.testId = anotherTestTable.testId AND domainModel.name = anotherTestTable.name";
		dataControl.setJoinCriteria(joiningCriteria);
		Map<String, ModelFieldMapping> filterObjectMap = dataControl.getFilterObjectMap();
		filterObjectMap.get("domainModel.name").setValue("name");
		filterObjectMap.get("domainModel.testId").setValue("10");
		dataControl.setFilterObjectMap(filterObjectMap);
		assertTrue(dataControl.getQuery().equals(expectedQuery));
		assertTrue(dataControl.getCountQuery().equals(expectedCountQuery));
	}

	public void testChildDataControl(){
		String expectedQuery = " from AnotherTestTable anotherTestTable  where  (  (  ( anotherTestTable.testId  = '10' )  )  ) ";
		String expectedCountQury= " Select count(*)  from AnotherTestTable anotherTestTable  where  (  (  ( anotherTestTable.testId  = '10' )  )  ) ";
		IDataController dataControl = DataControllerBuilder.getBasicDataControlWithMockedData("basicDataControl",
				DataControllerBuilder.getModelListForClasses(AnotherTestTable.class));
		DomainModel parentDataElement = new DomainModel();
		parentDataElement.setTestId(10);
		parentDataElement.setName("name");
		List<Map<String, Object>> parentdataList = Lists.newArrayList();
		Map<String,Object> dataMap = Maps.newHashMap();
		dataMap.put("domainModel", parentDataElement);
		parentdataList.add(dataMap);
		IDataController parentdataControl = DataControllerBuilder.getBasicDataControlWithMockedData("basicDataControl",
				DataControllerBuilder.getModelListForClasses(DomainModel.class),parentdataList);
		dataControl.setParentDataControl(parentdataControl);
		dataControl.setParentChildRelation(Lists.newArrayList("anotherTestTable.testId = domainModel.testId"));
		assertTrue(dataControl.getQuery().equals(expectedQuery));
		assertTrue(dataControl.getCountQuery().equals(expectedCountQury));
	}

}
