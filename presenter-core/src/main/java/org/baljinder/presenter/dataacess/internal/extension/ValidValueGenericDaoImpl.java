/**
 * 
 */
package org.baljinder.presenter.dataacess.internal.extension;

import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.baljinder.presenter.dataacess.extension.ValidValueGenricDao;
import org.baljinder.presenter.dataacess.internal.GenericPresentationDao;
import org.baljinder.presenter.util.ReflectionUtils;
import org.baljinder.presenter.util.Utils;
import org.springframework.orm.hibernate.HibernateTemplate;

/**
 * @author Baljinder Randhawa
 * 
 */

// TODO: requires refactoring use generics //  mes
// FIXME:


public class ValidValueGenericDaoImpl extends GenericPresentationDao implements ValidValueGenricDao {

	private static Log logger = LogFactory.getLog(ValidValueGenericDaoImpl.class);

	public ValidValueGenericDaoImpl() {
	}
	
	public ValidValueGenericDaoImpl(HibernateTemplate hibernateTemplate){
		super(hibernateTemplate);
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.baljinder.presenter.jsf.ui.dataacess.GenericPresentationDAO#
	 * checkExistanceThenSaveOrUpdate(java.util.List)
	 */
	public void checkExistanceThenSaveOrUpdate(List<Map<String, Object>> elementList, Class<? extends Object> entitiyName) {
		if (elementList == null)
			return;
		for (Map<String, Object> elementToSave : elementList) {
			for (Object element : elementToSave.values()) {
				checkExistanceThenSaveOrUpdate(element, entitiyName);
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.baljinder.presenter.jsf.ui.dataacess.GenericPresentationDAO#
	 * checkExistanceThenSaveOrUpdate(java.lang.Object)
	 */
	public void checkExistanceThenSaveOrUpdate(Object currentElementInternal, Class<? extends Object> entitiyName) {
		if (currentElementInternal == null)
			return;
		String idetifierPropertyName;
		try {
			idetifierPropertyName = getHibernateTemplate().getSessionFactory().getClassMetadata(entitiyName).getIdentifierPropertyName();
			Object peroprtyValue = ReflectionUtils.getFieldValue(currentElementInternal, idetifierPropertyName);
			List countList = getHibernateTemplate().find(
					"Select count(*) from " + entitiyName.getName() + " " + Utils.getAlias(entitiyName) + " where " + Utils.getAlias(entitiyName) + "."
							+ idetifierPropertyName + "=" + "'" + peroprtyValue + "'");
			if (Integer.valueOf(countList.get(0).toString()) > 0)
				update(currentElementInternal);
			else
				create(currentElementInternal);

		} catch (Throwable th) {
			logger.error(th);
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.baljinder.presenter.jsf.ui.dataacess.GenericPresentationDAO#
	 * checkExistanceThenSaveOrUpdate(java.lang.Object)
	 */
	public boolean createNewIfDoesNotExist(Object currentElementInternal, Class entitiyName) {
		if (currentElementInternal == null)
			return false;
		String idetifierPropertyName;
		try {
			idetifierPropertyName = getHibernateTemplate().getSessionFactory().getClassMetadata(entitiyName).getIdentifierPropertyName();
			Object peroprtyValue = ReflectionUtils.getFieldValue(currentElementInternal, idetifierPropertyName);
			List countList = getHibernateTemplate().find(
					"Select count(*) from " + entitiyName.getName() + " " + Utils.getAlias(entitiyName) + " where " + Utils.getAlias(entitiyName) + "."
							+ idetifierPropertyName + "=" + "'" + peroprtyValue + "'");
			if (Integer.valueOf(countList.get(0).toString()) > 0) {
				//TODO: raise exception
				//FacesUtils.addErrorMessage("PK_Violation");
				return false;
			} else {
				create(currentElementInternal);
				return true;
			}

		} catch (Throwable th) {
			//TODO: raise exception
			//FacesUtils.addErrorMessage("PK_Violation");
			logger.error(th);
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 */
	public Object getAssignedIdOfObject(Object currentElementInternal, Class entitiyName) {
		String idetifierPropertyName;
		Object peroprtyValue = null;
		try {
			idetifierPropertyName = getHibernateTemplate().getSessionFactory().getClassMetadata(entitiyName).getIdentifierPropertyName();
			peroprtyValue = ReflectionUtils.getFieldValue(currentElementInternal, idetifierPropertyName);
		} catch (Throwable th) {
			logger.error(th);
		}
		return peroprtyValue;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.baljinder.presenter.jsf.ui.dataacess.GenericPresentationDAO#
	 * deleteExistingIfIdChangeOrUpdate(java.lang.Object, java.lang.Class, java.util.List)
	 */
	public void deleteExistingIfIdChangeOrUpdate(Object currentElementInternal, Class<? extends Object> modelClassName, List<Object> specialHandlingForObjectsWithAssignedId) {
		if (specialHandlingForObjectsWithAssignedId.contains(getAssignedIdOfObject(currentElementInternal, modelClassName)))
			update(currentElementInternal);
		else {
			try {
				String idetifierPropertyName = getHibernateTemplate().getSessionFactory().getClassMetadata(modelClassName).getIdentifierPropertyName();
				Object peroprtyValue = ReflectionUtils.getFieldValue(currentElementInternal, idetifierPropertyName);
				getHibernateTemplate().delete(
						"from " + modelClassName.getName() + " " + Utils.getAlias(modelClassName) + " where " + Utils.getAlias(modelClassName) + "."
								+ idetifierPropertyName + "=" + "'" + peroprtyValue + "'");
				create(currentElementInternal);
			} catch (Throwable th) {
				logger.error(th);
			}
		}
	}

}
