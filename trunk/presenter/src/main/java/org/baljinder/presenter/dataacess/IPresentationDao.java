package org.baljinder.presenter.dataacess;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

//FIXME : create is nothing but save. so why keeping two methods
public interface IPresentationDao {

	public void create(Object object);

	public void create(Map<String, Object> dataObject);

	public void update(Object object);

	public void update(Map<String, Object> dataObject);

	public void delete(Object object);
	
	public void delete(Map<String, Object> dataObject);

	@SuppressWarnings("unchecked")
	public Object getObject(Class modelClass, Serializable primaryKey);

	public void executeSql(String sql);

	public List<Map<String, Object>> getAllEntities(List<Class<? extends Object>> modelList, Integer maxResult, Integer firstResult, String whereClause);

	public Integer getCountOfRecords(String countQuery);

	public void save(List<Map<String, Object>> dataList);

	public void save(List<Map<String, Object>> dataList, Boolean flushChanges);

	public void save(Map<String, Object> dataObject);

	public void save(Map<String, Object> dataObject, Boolean flushChanges);
	
	public void update(Map<String, Object> currentElementInternal, Boolean flushChanges);

}
