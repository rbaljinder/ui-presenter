package org.baljinder.presenter.dataacess;

import java.util.List;
import java.util.Map;

import junit.framework.TestCase;

import org.baljinder.presenter.dataacess.internal.OrderByAttribute;
import org.baljinder.presenter.testing.support.model.AnotherTestTable;
import org.baljinder.presenter.testing.support.model.TestTable;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

//TODO: you need to learn how to write test cases.. go get a book(note to self)
public class QueryBuilderTest extends TestCase {

	public void testBasicDataControl() {
		IDataControl basicDataControl = DataControlBuilder.getBasicDataControlWithMockedData("basicDataControl",
				DataControlBuilder.getModelListForClasses(TestTable.class));
		assertTrue(basicDataControl.getQuery().equals(" from TestTable testTable "));
		assertTrue(basicDataControl.getCountQuery().equals(" Select count(*)  from TestTable testTable "));
	}

	public void testDataControlWithFilter() {
		String expectedQuery = " from TestTable testTable  where  (  (  ( testTable.name = 'name' )  and  ( testTable.testId = '10' )  )  ) ";
		String expectedCountQuery = " Select count(*)  from TestTable testTable  where  (  (  ( testTable.name = 'name' )  and  ( testTable.testId = '10' )  )  ) ";
		IDataControl dataControl = DataControlBuilder.getBasicDataControlWithMockedData("basicDataControl",
				DataControlBuilder.getModelListForClasses(TestTable.class));
		Map<String, ModelFieldMapping> filterObjectMap = dataControl.getFilterObjectMap();
		filterObjectMap.get("testTable.testId").setValue("10");
		filterObjectMap.get("testTable.name").setValue("name");
		dataControl.setFilterObjectMap(filterObjectMap);
		assertTrue(dataControl.getQuery().equals(expectedQuery));
		assertTrue(dataControl.getCountQuery().equals(expectedCountQuery));
	}

	public void testDataControlWithFilterAndOrderBy() {
		String expectedQuery = " from TestTable testTable  where  (  (  ( testTable.name = 'name' )  and  ( testTable.testId = '10' )  )  )  order by testTable.testId ASC";
		String expectedCountQuery = " Select count(*)  from TestTable testTable  where  (  (  ( testTable.name = 'name' )  and  ( testTable.testId = '10' )  )  ) ";
		IDataControl dataControl = DataControlBuilder.getBasicDataControlWithMockedData("basicDataControl",
				DataControlBuilder.getModelListForClasses(TestTable.class));
		Map<String, ModelFieldMapping> filterObjectMap = dataControl.getFilterObjectMap();
		filterObjectMap.get("testTable.testId").setValue("10");
		filterObjectMap.get("testTable.name").setValue("name");
		dataControl.setFilterObjectMap(filterObjectMap);
		List<OrderByAttribute> orderBy = Lists.newArrayList(new OrderByAttribute("testTable", "testId", OrderByAttribute.ASC));
		dataControl.setOrderByList(orderBy);
		assertTrue(dataControl.getQuery().equals(expectedQuery));
		assertTrue(dataControl.getCountQuery().equals(expectedCountQuery));
	}

	public void testDataControlWithJoin() {
		String expectedQuery = " from TestTable testTable , AnotherTestTable anotherTestTable  where  (  ( testTable.testId = anotherTestTable.testId AND testTable.name = anotherTestTable.name )  and  (  ( testTable.name = 'name' )  and  ( testTable.testId = '10' )  )  ) ";
		String expectedCountQuery=" Select count(*)  from TestTable testTable , AnotherTestTable anotherTestTable  where  (  ( testTable.testId = anotherTestTable.testId AND testTable.name = anotherTestTable.name )  and  (  ( testTable.name = 'name' )  and  ( testTable.testId = '10' )  )  ) ";
		IDataControl dataControl = DataControlBuilder.getBasicDataControlWithMockedData("basicDataControl",
				DataControlBuilder.getModelListForClasses(TestTable.class, AnotherTestTable.class));
		String joiningCriteria = "testTable.testId = anotherTestTable.testId AND testTable.name = anotherTestTable.name";
		dataControl.setJoinCriteria(joiningCriteria);
		Map<String, ModelFieldMapping> filterObjectMap = dataControl.getFilterObjectMap();
		filterObjectMap.get("testTable.testId").setValue("10");
		filterObjectMap.get("testTable.name").setValue("name");
		dataControl.setFilterObjectMap(filterObjectMap);
		assertTrue(dataControl.getQuery().equals(expectedQuery));
		assertTrue(dataControl.getCountQuery().equals(expectedCountQuery));
	}

	public void testChildDataControl(){
		String expectedQuery = " from AnotherTestTable anotherTestTable  where  (  (  ( anotherTestTable.testId  = '10' )  )  ) ";
		String expectedCountQury= " Select count(*)  from AnotherTestTable anotherTestTable  where  (  (  ( anotherTestTable.testId  = '10' )  )  ) ";
		IDataControl dataControl = DataControlBuilder.getBasicDataControlWithMockedData("basicDataControl",
				DataControlBuilder.getModelListForClasses(AnotherTestTable.class));
		TestTable parentDataElement = new TestTable();
		parentDataElement.setTestId(10);
		parentDataElement.setName("name");
		List<Map<String, Object>> parentdataList = Lists.newArrayList();
		Map<String,Object> dataMap = Maps.newHashMap();
		dataMap.put("testTable", parentDataElement);
		parentdataList.add(dataMap);
		IDataControl parentdataControl = DataControlBuilder.getBasicDataControlWithMockedData("basicDataControl",
				DataControlBuilder.getModelListForClasses(TestTable.class),parentdataList);
		dataControl.setParentDataControl(parentdataControl);
		dataControl.setParentChildRelation(Lists.newArrayList("anotherTestTable.testId = testTable.testId"));
		assertTrue(dataControl.getQuery().equals(expectedQuery));
		assertTrue(dataControl.getCountQuery().equals(expectedCountQury));
	}

}
