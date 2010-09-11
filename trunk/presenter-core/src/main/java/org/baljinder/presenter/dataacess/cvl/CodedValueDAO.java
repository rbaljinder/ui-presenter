package org.baljinder.presenter.dataacess.cvl;

import java.util.List;

import org.baljinder.presenter.dataacess.internal.GenericPresentationDao;
import org.springframework.orm.hibernate.HibernateTemplate;

public class CodedValueDAO extends GenericPresentationDao {

	public CodedValueDAO() {
	}
	
	public CodedValueDAO(HibernateTemplate hibernateTemplate) {
		super(hibernateTemplate);
	}
	
	public List<?> getCodedValueObject(Class<?> className) {
		return getHibernateTemplate().loadAll(className);
	}

}
