package org.baljinder.presenter.dataacess.extension;

import net.sf.hibernate.Session;

import org.springframework.orm.hibernate.HibernateTemplate;

public interface SupportsHibernate {

	public void setHibernateTemplate(HibernateTemplate template);
	
	public HibernateTemplate getHibernateTemplate();
	
	public Session getSession() ;
	
}
