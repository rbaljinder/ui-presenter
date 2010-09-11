/**
 * 
 */
package org.baljinder.presenter.dataacess.internal;

import net.sf.hibernate.FlushMode;
import net.sf.hibernate.HibernateException;
import net.sf.hibernate.Session;
import net.sf.hibernate.SessionFactory;

import org.springframework.dao.CleanupFailureDataAccessException;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.orm.hibernate.SessionFactoryUtils;
import org.springframework.orm.hibernate.support.OpenSessionInViewFilter;

public class HibernateFilter extends OpenSessionInViewFilter {

	 @Override
	 protected Session getSession(SessionFactory sessionFactory) throws DataAccessResourceFailureException {
	  Session session = SessionFactoryUtils.getSession(sessionFactory, true);
	  //set the FlushMode to auto in order to save objects.
	  session.setFlushMode(FlushMode.AUTO);
	  return session;
	 }
	 
	 
	 @Override
	 protected void closeSession(Session session, SessionFactory sessionFactory) {
	  try{
	   if (session != null && session.isOpen() && session.isConnected()) {
	    try {
	     session.flush();
	    } catch (HibernateException e) {
	     throw new CleanupFailureDataAccessException("Failed to flush session before close: " + e.getMessage(), e);
	    } catch(Exception e){}
	    }
	  } finally{
	   super.closeSession(session, sessionFactory);
	  }
	 }
	}