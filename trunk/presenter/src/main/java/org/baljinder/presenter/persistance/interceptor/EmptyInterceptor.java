/**
 * 
 */
package org.baljinder.presenter.persistance.interceptor;

import java.io.Serializable;
import java.util.Iterator;

import net.sf.hibernate.CallbackException;
import net.sf.hibernate.Interceptor;
import net.sf.hibernate.type.Type;
/**
 * @author Baljinder Randhawa
 *
 */
public class EmptyInterceptor implements Interceptor {

	/* (non-Javadoc)
	 * @see net.sf.hibernate.Interceptor#findDirty(java.lang.Object, java.io.Serializable, java.lang.Object[], java.lang.Object[], java.lang.String[], net.sf.hibernate.type.Type[])
	 */
	public int[] findDirty(Object obj, Serializable serializable, Object[] aobj, Object[] aobj1, String[] as, Type[] atype) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see net.sf.hibernate.Interceptor#instantiate(java.lang.Class, java.io.Serializable)
	 */
	public Object instantiate(Class class1, Serializable serializable) throws CallbackException {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see net.sf.hibernate.Interceptor#isUnsaved(java.lang.Object)
	 */
	public Boolean isUnsaved(Object obj) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see net.sf.hibernate.Interceptor#onDelete(java.lang.Object, java.io.Serializable, java.lang.Object[], java.lang.String[], net.sf.hibernate.type.Type[])
	 */
	public void onDelete(Object obj, Serializable serializable, Object[] aobj, String[] as, Type[] atype) throws CallbackException {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see net.sf.hibernate.Interceptor#onFlushDirty(java.lang.Object, java.io.Serializable, java.lang.Object[], java.lang.Object[], java.lang.String[], net.sf.hibernate.type.Type[])
	 */
	public boolean onFlushDirty(Object obj, Serializable serializable, Object[] aobj, Object[] aobj1, String[] as, Type[] atype)
			throws CallbackException {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see net.sf.hibernate.Interceptor#onLoad(java.lang.Object, java.io.Serializable, java.lang.Object[], java.lang.String[], net.sf.hibernate.type.Type[])
	 */
	public boolean onLoad(Object obj, Serializable serializable, Object[] aobj, String[] as, Type[] atype) throws CallbackException {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see net.sf.hibernate.Interceptor#onSave(java.lang.Object, java.io.Serializable, java.lang.Object[], java.lang.String[], net.sf.hibernate.type.Type[])
	 */
	public boolean onSave(Object entity, Serializable serializable, Object[] aobj, String[] as, Type[] atype) throws CallbackException {
		return false;
	}

	/* (non-Javadoc)
	 * @see net.sf.hibernate.Interceptor#postFlush(java.util.Iterator)
	 */
	public void postFlush(Iterator iterator) throws CallbackException {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see net.sf.hibernate.Interceptor#preFlush(java.util.Iterator)
	 */
	public void preFlush(Iterator iterator) throws CallbackException {
		// TODO Auto-generated method stub
		
	}

}
