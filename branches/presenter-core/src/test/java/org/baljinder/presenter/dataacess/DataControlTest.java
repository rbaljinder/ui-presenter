package org.baljinder.presenter.dataacess;

import org.baljinder.presenter.testing.support.BasicIntegrationTestCase;
import org.baljinder.presenter.testing.support.model.TestTable;
import org.springframework.context.ApplicationContext;

//TODO: test insertion/deletion/search and related stuff
public class DataControlTest extends BasicIntegrationTestCase {

	public void testDataAccess(){
		ApplicationContext ac = getApplicationContext();
		IDataController dataController = (IDataController)ac.getBean("dataControl_dataAccess_test") ;
		assertTrue(dataController.getData().size() > 0);
	}
	
	public void testChildDataControl(){
		ApplicationContext ac = getApplicationContext();
		IDataController dataController = (IDataController)ac.getBean("Basic_Child_DataControl") ;
		assertTrue(dataController.getData().size() ==1) ;
		assertTrue(dataController.getParentDataControl().getData().size() ==1) ;
	}
	
	public void testRefresh(){
		ApplicationContext ac = getApplicationContext();
		String seededName = "test";
		String newName = "newTest";
		IDataController dataControlToRefresh = (IDataController)ac.getBean("dataControl_dataAccess_test") ;
		dataControlToRefresh.getFilterObjectMap().get("testTable.testId").setValue(1);
		assertTrue(((TestTable)dataControlToRefresh.getData().get(0).get("testTable")).getName().equals(seededName));
		IDataController dataController = (IDataController)ac.getBean("dataControl_dataAccess_test") ;
		dataController.getFilterObjectMap().get("testTable.testId").setValue(1);
		((TestTable)dataController.getData().get(0).get("testTable")).setName(newName);
		dataController.save();
		dataControlToRefresh.refresh();
		assertTrue(((TestTable)dataControlToRefresh.getData().get(0).get("testTable")).getName().equals(newName));
		//revert back
		((TestTable)dataController.getData().get(0).get("testTable")).setName(seededName);
		dataController.save();
	}
}
