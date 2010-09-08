package org.baljinder.presenter.dataacess.internal;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import net.sf.hibernate.HibernateException;
import net.sf.hibernate.Query;
import net.sf.hibernate.Session;
import net.sf.hibernate.SessionFactory;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.baljinder.presenter.dataacess.IPresentationDao;
import org.baljinder.presenter.dataacess.extension.SupportsHibernate;
import org.baljinder.presenter.dataacess.util.PresentationConstants;
import org.baljinder.presenter.util.Utils;
import org.springframework.orm.hibernate.HibernateCallback;
import org.springframework.orm.hibernate.HibernateTemplate;
import org.springframework.orm.hibernate.SessionFactoryUtils;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class GenericPresentationDao implements IPresentationDao,SupportsHibernate {

	private int prefetchCount = PresentationConstants.NO_OF_RECORDS_TO_PREFETCH;

	private static Log logger = LogFactory.getLog(GenericPresentationDao.class);

	private HibernateTemplate hibernateTemplate ;

	public GenericPresentationDao() {
	}
	
	public GenericPresentationDao(HibernateTemplate hibernateTemplate){
		this.hibernateTemplate = hibernateTemplate;
	}
	public HibernateTemplate getHibernateTemplate() {
		return hibernateTemplate;
	}
	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}

	public void create(Object object) 
    {	   
        getHibernateTemplate().save(object);		
	}
	public void create(Map<String, Object> dataObject) 
    {	   
		for (Object data : dataObject.values()) {
			getHibernateTemplate().save(data);
		}
	}
	
	public void update(Object object) 
    {
	   getHibernateTemplate().update(object);	
	}
	
	public void update(Map<String, Object> dataObject) 
    {
		for (Object data : dataObject.values()) {
			getHibernateTemplate().update(data);
		}	
	}
	
	public void delete(Object object)
	{
	    getHibernateTemplate().delete(object);
	}
	
	public Object getObject(Class modelClass,Serializable  primaryKey)
	{
	    return  getHibernateTemplate().get(modelClass,primaryKey);
	}
	
	public Session getSession() {
		SessionFactory sessionFactory = getHibernateTemplate().getSessionFactory();
		return !getHibernateTemplate().isAllowCreate() ?SessionFactoryUtils.getSession(sessionFactory, false):
			SessionFactoryUtils.getSession(sessionFactory, getHibernateTemplate().getEntityInterceptor(), getHibernateTemplate().getJdbcExceptionTranslator());
	}
	
	public void executeSql(final String sql)
	{
		System.out.println("Executing sql " + sql);
		getHibernateTemplate().execute(new HibernateCallback()
		{

			public Object doInHibernate(Session session) throws HibernateException, SQLException 
			{
				return session.connection().createStatement().execute(sql);
			}
		}
		);
	}
	
	@SuppressWarnings("unchecked")
	public List<Object> getAllEntities(Class<? extends Object> enttityName) {
		return getHibernateTemplate().loadAll(enttityName);

	}
	
	public void save(List<Map<String,Object>> dataList,Boolean flushChanges){
		save(dataList);
		try {
			getSession().flush();
		} catch (Throwable th) {
			throw new RuntimeException(th);
		}
	}
	
	public void save(List<Map<String, Object>> dataList) {
		List<Object> objectListToSave = Lists.newArrayList();
		for (Map<String, Object> objectToSave : dataList) {
			for (Object object : objectToSave.values())
				objectListToSave.add(object);
		}
		getHibernateTemplate().saveOrUpdateAll(objectListToSave);
	}

	public void save(Map<String,Object> dataObject,Boolean flushChanges){
		save(dataObject);
		try {
			getSession().flush();
		} catch (Throwable th) {
			throw new RuntimeException(th);
		}
	}
	
	public void save(Map<String, Object> dataObject) {
		for (Object data : dataObject.values()) {
			getHibernateTemplate().saveOrUpdate(data);
		}
	};

	public void update(Map<String, Object> dataObject,Boolean flushChanges) {
		for (Object data : dataObject.values()) {
			getHibernateTemplate().update(data);
		}
		try {
			getSession().flush();
		} catch (Throwable th) {
			throw new RuntimeException(th);
		}
		
	}

	@SuppressWarnings("unchecked")
	public Integer getCountOfRecords(String countQuery) {
		int count = 0;
		logger.info("Count Query generated: ["+countQuery+"]");
		List<Integer> countList = getHibernateTemplate().find(countQuery);
		if (countList != null && !countList.isEmpty()) {
			count = ((Integer) countList.get(0)).intValue();
		}
		return count;
	}

	public void delete(Map<String, Object> objectToDelete) {
		for (Object object : objectToDelete.values())
			delete(object);
	}

	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> getAllEntities(final List<Class<? extends Object>> modelList, final Integer maxResult, final Integer firstResult, final String queryString) {
		return (List<Map<String, Object>>) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				logger.info("Query generated: ["+queryString+"]"+", No of records to fetch["+ (maxResult)+"]");
				List<Map<String, Object>> result = Lists.newArrayList();
				Query query = session.createQuery(queryString);
				query = query.setFirstResult(firstResult);
				query.setMaxResults(maxResult + prefetchCount);
				List<Object> response = query.list();
				for (Object object : response) {
					if (object instanceof Object[])
						result.add(handlePair((Object[]) object, modelList));
					else
						result.add(handleSingle(object, modelList));
				}
				return result != null ? result : Lists.newArrayList();
			}
		});
	}

	private Map<String, Object> handlePair(Object[] pairs, List<Class<? extends Object>> modelList) {
		
		final int noOfModelsInvolved = modelList.size();
		Map<String, Object> modelNameObjectMap = Maps.newHashMap();
		for(Class<? extends Object> modelClazz: modelList)
			modelNameObjectMap.put(Utils.getUncappedAlias(modelClazz),null);
		for (int index = 0; index < noOfModelsInvolved; index++) {
			Object model = pairs[index];
			if(model != null)
				modelNameObjectMap.put(Utils.getModelName(modelList, model), model);
		}
		return modelNameObjectMap;
	}

	private Map<String, Object> handleSingle(Object object, List<Class<? extends Object>> modelList) {
		final int noOfModelsInvolved = modelList.size();
		Map<String, Object> modelNameObjectMap = Maps.newHashMap();
		for (int index = 0; index < noOfModelsInvolved; index++)
			modelNameObjectMap.put(Utils.getModelName(modelList, object), object);
		return modelNameObjectMap;
	}
}
