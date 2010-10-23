package org.baljinder.presenter.dataacess.jsf.myfaces.namespace;

import org.baljinder.presenter.dataacess.IDataController;
import org.baljinder.presenter.dataacess.jsf.myfaces.MyFacesSupportingDataController;
import org.baljinder.presenter.testing.support.SpringContextTestCase;
import org.springframework.context.ApplicationContext;

/**
 * @author Baljinder Randhawa
 * 
 */
// TODO: Add all possible type of test cases
// most of the things are prototype scoped so comparing names of the references
// TODO: change scope to singleton for session scoped beans
public class NameSpaceDefinitionAndBeanConstructionTest extends SpringContextTestCase {

	@Override
	public String[] getConfigFiles() {
		return new String[] { "configuration-ex.xml" };
	}

	public void testDataControlExtension() {
		ApplicationContext ac = getApplicationContext();
		IDataController allPropertiesConfigurableDataControl = (IDataController) ac.getBean("EventHandler_Supporting_DataControl");
		assertTrue(allPropertiesConfigurableDataControl instanceof MyFacesSupportingDataController);
	}
}
