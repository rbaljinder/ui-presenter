package org.baljinder.presenter.dataacess;

import java.util.List;
import java.util.Map;

import org.baljinder.presenter.dataacess.filter.Filter;
import org.baljinder.presenter.dataacess.internal.DataControl;
import org.baljinder.presenter.dataacess.util.BasicQueryBuilder;
import org.baljinder.presenter.dataacess.util.IQueryBuilder;

import com.google.common.collect.Lists;

public class DataControlBuilder {

	public static IDataControl getBasicDataControlWithMockedData(String name,List<Class<?>> modelList,final List<Map<String, Object>> dataList) {
		IDataControl dataControl = new DataControl(){
			public List<java.util.Map<String,Object>> getData() {
				return dataList ;
			};
		};
		dataControl.setDataControlName(name);
		dataControl.setModelList(modelList);
		dataControl.setQueryBuilder(getQueryBuilder());
		return dataControl;
	}

	
	public static IDataControl getBasicDataControl(String name, List<Class<?>> modelList) {
		IDataControl dataControl = new DataControl(){
			@Override
			public List<Map<String, Object>> getData() {
				return Lists.newArrayList();
			}
		};
		dataControl.setDataControlName(name);
		dataControl.setQueryBuilder(getQueryBuilder());
		dataControl.setModelList(modelList);
		return dataControl;
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
}
