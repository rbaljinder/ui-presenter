package org.baljinder.presenter.dataacess.internal;

import java.util.Map;

import org.baljinder.presenter.dataacess.IPersistenceManager;
import org.baljinder.presenter.dataacess.IPresentationDao;
import org.baljinder.presenter.dataacess.extension.SupportsHibernate;
import org.baljinder.presenter.dataacess.extension.SupportsJDBCTemplating;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.hibernate3.HibernateTemplate;

/**
 * This is out of my learning of a framework i use at work. Nothing special
 * 
 * @author baljinder
 * 
 */
public class PersistenceManager implements IPersistenceManager {

	private Map<Object, Object> daoAssociations;

	// TODO: is the template share-able
	private HibernateTemplate hibernateTemplate;

	private JdbcTemplate jdbcTemplate;

	public IPresentationDao getDAO(Object keyName) {
		IPresentationDao dao = (IPresentationDao)daoAssociations.get(keyName);
		if (dao == null)
			throw new RuntimeException("No Dao association registered with the key[" + keyName + "]");
		if (dao instanceof SupportsHibernate){
			((SupportsHibernate) dao).setHibernateTemplate(hibernateTemplate);
		}	
		if (dao instanceof SupportsJDBCTemplating)
			((SupportsJDBCTemplating) dao).setJdbcTemplate(jdbcTemplate);
		
		return dao;
	}

	public Map<Object, Object> getDaoAssociations() {
		return daoAssociations;
	}

	public void setDaoAssociations(Map<Object, Object> daoAssociations) {
		this.daoAssociations = daoAssociations;
	}

	public HibernateTemplate getHibernateTemplate() {
		return hibernateTemplate;
	}

	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}

	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

}
