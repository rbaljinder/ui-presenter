package org.baljinder.presenter.dataacess;

import org.baljinder.presenter.testing.support.BasicIntegrationTestCase;
import org.baljinder.presenter.testing.support.model.DomainModel;
import org.springframework.context.ApplicationContext;

//TODO: read some good book man seriously .. this cant be how you write test cases
public class DataControlCRUDOperationTest extends BasicIntegrationTestCase {

	public void testCreate() {
		ApplicationContext applicationContext = getApplicationContext();
		IDataController dataController = (IDataController) applicationContext.getBean("dataControl_dataAccess_test");
		assertTrue(dataController.getData().size() == 1);
		DataControllerBuilder.createDataControlElement(dataController, 3, "TestName", 10);
		dataController.save();
		assertTrue(dataController.getData().size() == 4);
	}

	public void testDelete() {
		ApplicationContext applicationContext = getApplicationContext();
		IDataController dataControl = (IDataController) applicationContext.getBean("dataControl_dataAccess_test");
		int sizeBeforeCreation = dataControl.getData().size();
		DataControllerBuilder.createDataControlElement(dataControl, 2, "deleteMe", 100);
		dataControl.save();
		int sizeAfterCreation = dataControl.getData().size();
		dataControl.setDataFetched(false);
		assertTrue(sizeAfterCreation - sizeBeforeCreation == 2);
		dataControl.getFilterObjectMap().get("domainModel.name").setValue("deleteMe*");
		assertTrue(dataControl.getData().size() == 2); // should fetch only two records
		dataControl.delete();
		dataControl.clearFilterValues();
		dataControl.setDataFetched(false);
		assertTrue(dataControl.getData().size() == sizeBeforeCreation);
	}

	public void testUpdate() {
		ApplicationContext ac = getApplicationContext();
		String seededName = "test";
		String newName = "newTest";
		IDataController dataController = (IDataController) ac.getBean("dataControl_dataAccess_test");
		dataController.getFilterObjectMap().get("domainModel.testId").setValue(1);
		assertTrue(((DomainModel) dataController.getData().get(0).get("domainModel")).getName().equals(seededName));
		((DomainModel) dataController.getData().get(0).get("domainModel")).setName(newName);
		dataController.update();
		dataController.setDataFetched(false);
		assertTrue(((DomainModel) dataController.getData().get(0).get("domainModel")).getName().equals(newName));
		// revert back
		((DomainModel) dataController.getData().get(0).get("domainModel")).setName(seededName);
		dataController.save();
	}

	public void testOperationOutcome(){
		ApplicationContext ac = getApplicationContext();
		IDataController  allPropertiesConfigurableDataControl = (IDataController) ac.getBean("All_Properties_Configurable_DataControl");
		allPropertiesConfigurableDataControl.create().equals("All_Properties_Configurable_DataControl_Create");
	}
	// FIXME : this dont work.. looks like some problem with HSQLDB .. or the dialect
	// I have varified it with Oracle though
	public void xxtestCreateForMultipleModelDataControl() {
		ApplicationContext applicationContext = getApplicationContext();
		IDataController dataControl = (IDataController) applicationContext.getBean("multiple_model_dataControl_dataAccess_test");
		int sizeBeforeCreation = dataControl.getData().size();
		assertTrue(sizeBeforeCreation == 0);

	}

}
