package org.baljinder.presenter.dataacess;

import org.baljinder.presenter.testing.support.BasicIntegrationTestCase;
import org.baljinder.presenter.testing.support.model.DomainModel;
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
		dataControlToRefresh.getFilterObjectMap().get("domainModel.testId").setValue(1);
		assertTrue(((DomainModel)dataControlToRefresh.getData().get(0).get("domainModel")).getName().equals(seededName));
		IDataController dataController = (IDataController)ac.getBean("dataControl_dataAccess_test") ;
		dataController.getFilterObjectMap().get("domainModel.testId").setValue(1);
		((DomainModel)dataController.getData().get(0).get("domainModel")).setName(newName);
		dataController.save();
		dataControlToRefresh.refresh();
		assertTrue(((DomainModel)dataControlToRefresh.getData().get(0).get("domainModel")).getName().equals(newName));
		//revert back
		((DomainModel)dataController.getData().get(0).get("domainModel")).setName(seededName);
		dataController.save();
	}
}
