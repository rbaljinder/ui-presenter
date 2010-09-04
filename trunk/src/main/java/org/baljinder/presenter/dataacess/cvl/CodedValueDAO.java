package org.baljinder.presenter.dataacess.cvl;

import java.util.List;

import org.baljinder.presenter.dataacess.internal.GenericPresentationDAOImpl;

public class CodedValueDAO extends GenericPresentationDAOImpl {

	public List<?> getCodedValueObject(Class<?> className) {
		List<Object> dataList = getHibernateTemplate().loadAll(className);
		return dataList;
	}

}
