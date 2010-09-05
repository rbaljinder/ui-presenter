import junit.framework.TestCase;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import org.baljinder.presenter.dataacess.IDataControl;
import org.baljinder.presenter.dataacess.IPageController;

/**
 * @author Baljinder Randhawa
 * 
 */
public class NameSpaceTestCase extends TestCase {

	public void test() {
		ApplicationContext ac = new ClassPathXmlApplicationContext("test.xml");
		assertNotNull(ac.getBean("Workbench_DataWarehousePreference_System_DataControlxx"));
		assertNotNull(ac.getBean("Workbench_DataWarehousePreference_DIInteractionConfig_DataControlX"));
		assertNotNull(ac.getBean("Workbench_SalesAgentChannelMapping_SalesChannelMapping_DataControl"));
		assertNotNull(ac.getBean("testTransition"));
		assertNotNull(ac.getBean("testTransition1"));
		assertNotNull(ac.getBean("testEventHandler"));
		IPageController page = (IPageController)ac.getBean("testPage") ;
		assertNotNull(page.getEventHandler()) ;
		IDataControl dataControl = (IDataControl)ac.getBean("Workbench_SalesAgentChannelMapping_SalesChannelMapping_DataControl") ;
		assertNotNull(dataControl) ;
		IDataControl dataAccess = (IDataControl)ac.getBean("dataControl_dataAccess_test") ;
		assertTrue(dataAccess.getData().size() > 1 ) ;
	}
}
