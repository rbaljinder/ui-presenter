package org.baljinder.presenter.dataacess;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.baljinder.presenter.dataacess.filter.Filter;
import org.baljinder.presenter.dataacess.internal.DataController;
import org.baljinder.presenter.dataacess.util.BasicQueryBuilder;
import org.baljinder.presenter.dataacess.util.IQueryBuilder;
import org.baljinder.presenter.testing.support.model.DomainModel;

import com.google.common.collect.Lists;

public class DataControllerBuilder {

	private static IDataController getMockedDataControl(final List<Map<String, Object>> dataList){
		IDataController dataController = new DataController(){
			public List<java.util.Map<String,Object>> getData() {
				return dataList ;
			};
		};
		return dataController ; 
	}
	
	public static IDataController getBasicDataControlWithMockedData(String name,List<Class<?>> modelList,final List<Map<String, Object>> dataList) {
		IDataController dataController = getMockedDataControl(dataList);
		dataController.setDataControlName(name);
		dataController.setModelList(modelList);
		dataController.setQueryBuilder(getQueryBuilder());
		return dataController;
	}

	
	public static IDataController getBasicDataControlWithMockedData(String name, List<Class<?>> modelList) {
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
	
	public static void createDataControlElement(IDataController dataController, int howMany, String name, int seedFrom) {
		for (int i = 0; i < howMany; i++) {
			dataController.create();
		}
		List<Map<String, Object>> newlyCreatedElements = dataController.getNewlyCreatedElement();
		for (Map<String, Object> element : newlyCreatedElements) {
			for (Map.Entry<String, Object> anEntry : element.entrySet()) {
				DomainModel testTable = (DomainModel) anEntry.getValue();
				testTable.setName("deleteMe" + seedFrom);
				testTable.setTestId(seedFrom);
				seedFrom++;
			}
		}
	}
}
