package org.baljinder.presenter.dataacess;


public interface IPersistenceManager  {
	
	public IPresentationDao getDAO(Object keyName);

}
