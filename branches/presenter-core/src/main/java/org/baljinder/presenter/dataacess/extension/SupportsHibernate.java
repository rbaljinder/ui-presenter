package org.baljinder.presenter.dataacess.extension;

import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateTemplate;

public interface SupportsHibernate {

	public void setHibernateTemplate(HibernateTemplate template);
	
	public HibernateTemplate getHibernateTemplate();
	
	public Session getSession() ;
	
}
