package org.baljinder.presenter.dataacess;

import java.util.List;
import java.util.Map;

import org.baljinder.presenter.testing.BasicIntegrationTestCase;
import org.baljinder.presenter.testing.support.model.TestTable;
import org.springframework.context.ApplicationContext;

//TODO: read some good book man seriously .. this cant be how you write test cases
public class DataControlCRUDOperationTest extends BasicIntegrationTestCase {

	
	public void testCreate(){
		ApplicationContext applicationContext = getApplicationContext();
		IDataControl dataControl = (IDataControl) applicationContext.getBean("dataControl_dataAccess_test");
		assertTrue(dataControl.getData().size() == 1);
		createDataControlElement(dataControl,3,"TestName",10);
		dataControl.save();
		assertTrue(dataControl.getData().size() == 4);
	}
	
	public void createDataControlElement(IDataControl dataControl, int howMany,String name, int seedFrom){
		for(int i = 0 ; i< howMany;i++){
			dataControl.create();
		}
		List<Map<String,Object>> newlyCreatedElements  = dataControl.getNewlyCreatedElement();
		for(Map<String,Object> element :newlyCreatedElements){
			for(Map.Entry<String, Object> anEntry :element.entrySet()){
				TestTable testTable = (TestTable) anEntry.getValue();
				testTable.setName("deleteMe"+seedFrom);
				testTable.setTestId(seedFrom);
				seedFrom++;
			}
		} 
	}
	public void testDelete(){
		ApplicationContext applicationContext = getApplicationContext();
		IDataControl dataControl = (IDataControl) applicationContext.getBean("dataControl_dataAccess_test");
		int sizeBeforeCreation = dataControl.getData().size();
		createDataControlElement(dataControl,2,"deleteMe",100);
		dataControl.save();
		int sizeAfterCreation = dataControl.getData().size();
		assertTrue(sizeAfterCreation - sizeBeforeCreation == 2);
		dataControl.getFilterObjectMap().get("testTable.name").setValue("deleteMe*");
		assertTrue(dataControl.getData().size() == 2);		//should fetch only two records
		dataControl.delete();
		dataControl.clearFilterValues();
		assertTrue(dataControl.getData().size() == sizeBeforeCreation );
	}
}
