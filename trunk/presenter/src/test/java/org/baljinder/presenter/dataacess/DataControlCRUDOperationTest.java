package org.baljinder.presenter.dataacess;

import org.baljinder.presenter.testing.BasicIntegrationTestCase;
import org.springframework.context.ApplicationContext;

//TODO: read some good book man seriously .. this cant be how you write test cases
public class DataControlCRUDOperationTest extends BasicIntegrationTestCase {

	public void testCreate() {
		ApplicationContext applicationContext = getApplicationContext();
		IDataController dataControl = (IDataController) applicationContext.getBean("dataControl_dataAccess_test");
		assertTrue(dataControl.getData().size() == 1);
		DataControlBuilder.createDataControlElement(dataControl, 3, "TestName", 10);
		dataControl.save();
		assertTrue(dataControl.getData().size() == 4);
	}

	public void testDelete() {
		ApplicationContext applicationContext = getApplicationContext();
		IDataController dataControl = (IDataController) applicationContext.getBean("dataControl_dataAccess_test");
		int sizeBeforeCreation = dataControl.getData().size();
		DataControlBuilder.createDataControlElement(dataControl, 2, "deleteMe", 100);
		dataControl.save();
		int sizeAfterCreation = dataControl.getData().size();
		dataControl.setDataFetched(false);
		assertTrue(sizeAfterCreation - sizeBeforeCreation == 2);
		dataControl.getFilterObjectMap().get("testTable.name").setValue("deleteMe*");
		assertTrue(dataControl.getData().size() == 2); // should fetch only two records
		dataControl.delete();
		dataControl.clearFilterValues();
		dataControl.setDataFetched(false);
		assertTrue(dataControl.getData().size() == sizeBeforeCreation);
	}

		//FIXME : this dont work.. looks like some problem with HSQLDB .. or the dialect
	//I have varified it with Oracle though
	public void xxtestCreateForMultipleModelDataControl() {
		ApplicationContext applicationContext = getApplicationContext();
		IDataController dataControl = (IDataController) applicationContext.getBean("multiple_model_dataControl_dataAccess_test");
		int sizeBeforeCreation = dataControl.getData().size();
		assertTrue(sizeBeforeCreation == 0);
		
	}

}
