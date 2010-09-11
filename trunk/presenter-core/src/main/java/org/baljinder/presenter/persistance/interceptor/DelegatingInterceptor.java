/**
 * 
 */
package org.baljinder.presenter.persistance.interceptor;

import java.io.Serializable;
import java.util.List;

import net.sf.hibernate.CallbackException;
import net.sf.hibernate.Interceptor;
import net.sf.hibernate.type.Type;

import com.google.common.collect.Lists;

/**
 * @author Baljinder Randhawa
 *
 */
public class DelegatingInterceptor extends EmptyInterceptor {

	//as of now just adding to the interesting intersections
	private List<Interceptor> interceptors = Lists.newArrayList(); 
	
	public boolean onSave(Object entity, Serializable serializable, Object[] aobj, String[] as, Type[] atype) throws CallbackException {
		for(Interceptor interceptor: interceptors){
			interceptor.onSave(entity, serializable, aobj, as, atype);
		}
		return false;
	}
	
	public boolean onFlushDirty(Object entity, Serializable serializable, Object[] aobj, Object[] aobj1, String[] as, Type[] atype) throws CallbackException {
		for(Interceptor interceptor: interceptors){
			interceptor.onFlushDirty(entity, serializable, aobj, aobj1, as, atype);
		}
		return false;
	};
	/**
	 * @return the interceptors
	 */
	public List<Interceptor> getInterceptors() {
		return interceptors;
	}

	/**
	 * @param interceptors the interceptors to set
	 */
	public void setInterceptors(List<Interceptor> interceptors) {
		this.interceptors = interceptors;
	}

}
