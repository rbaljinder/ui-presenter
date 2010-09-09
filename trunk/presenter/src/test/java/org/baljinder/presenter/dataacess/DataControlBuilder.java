package org.baljinder.presenter.dataacess;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.baljinder.presenter.dataacess.filter.Filter;
import org.baljinder.presenter.dataacess.internal.DataControl;
import org.baljinder.presenter.dataacess.util.BasicQueryBuilder;
import org.baljinder.presenter.dataacess.util.IQueryBuilder;
import org.baljinder.presenter.testing.support.model.TestTable;

import com.google.common.collect.Lists;

public class DataControlBuilder {

	private static IDataControl getMockedDataControl(final List<Map<String, Object>> dataList){
		IDataControl dataControl = new DataControl(){
			public List<java.util.Map<String,Object>> getData() {
				return dataList ;
			};
		};
		return dataControl ; 
	}
	
	public static IDataControl getBasicDataControlWithMockedData(String name,List<Class<?>> modelList,final List<Map<String, Object>> dataList) {
		IDataControl dataControl = getMockedDataControl(dataList);
		dataControl.setDataControlName(name);
		dataControl.setModelList(modelList);
		dataControl.setQueryBuilder(getQueryBuilder());
		return dataControl;
	}

	
	public static IDataControl getBasicDataControlWithMockedData(String name, List<Class<?>> modelList) {
		return getBasicDataControlWithMockedData(name, modelList,new ArrayList<Map<String,Object>>());
	}

	public static IQueryBuilder getQueryBuilder() {
		IQueryBuilder queryBuilder = new BasicQueryBuilder();
		return queryBuilder;
	}

	public static IQueryBuilder getQueryBuilder(Map<String, Map<String, Filter>> filters) {
		IQueryBuilder queryBuilder = getQueryBuilder();
		queryBuilder.setFilters(filters);
		return queryBuilder;
	}
	
	public static List<Class<?>> getModelListForClasses(Class<?>... classes) {
		List<Class<?>> modelList = Lists.newArrayList();
		for (Class<?> clazz : classes)
			modelList.add(clazz);
		return modelList;
	}
	
	public static void createDataControlElement(IDataControl dataControl, int howMany, String name, int seedFrom) {
		for (int i = 0; i < howMany; i++) {
			dataControl.create();
		}
		List<Map<String, Object>> newlyCreatedElements = dataControl.getNewlyCreatedElement();
		for (Map<String, Object> element : newlyCreatedElements) {
			for (Map.Entry<String, Object> anEntry : element.entrySet()) {
				TestTable testTable = (TestTable) anEntry.getValue();
				testTable.setName("deleteMe" + seedFrom);
				testTable.setTestId(seedFrom);
				seedFrom++;
			}
		}
	}
}
