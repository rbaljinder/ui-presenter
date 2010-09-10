package org.baljinder.presenter.dataacess;

import org.baljinder.presenter.testing.BasicIntegrationTestCase;
import org.springframework.context.ApplicationContext;

//TODO: test insertion/deletion/search and related stuff
public class DataControlTest extends BasicIntegrationTestCase {

	public void testDataAccess(){
		ApplicationContext ac = getApplicationContext();
		IDataController dataControl = (IDataController)ac.getBean("dataControl_dataAccess_test") ;
		assertTrue(dataControl.getData().size() > 0);
	}
	
	public void testChildDataControl(){
		ApplicationContext ac = getApplicationContext();
		IDataController dataControl = (IDataController)ac.getBean("Basic_Child_DataControl") ;
		assertTrue(dataControl.getData().size() ==1) ;
		assertTrue(dataControl.getParentDataControl().getData().size() ==1) ;
	}
}
