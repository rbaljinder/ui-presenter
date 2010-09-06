package org.baljinder.presenter.dataacess;

import org.baljinder.presenter.testing.BasicIntegrationTestCase;
import org.springframework.context.ApplicationContext;

//TODO: test insertion/deletion/search and related stuff
public class DataAccessIntergationTest extends BasicIntegrationTestCase {

	public void testDataAccess(){
		ApplicationContext ac = getApplicationContext();
		IDataControl dataAccess = (IDataControl)ac.getBean("dataControl_dataAccess_test") ;
		assertTrue(dataAccess.getData().size() > 0);
	}
}
