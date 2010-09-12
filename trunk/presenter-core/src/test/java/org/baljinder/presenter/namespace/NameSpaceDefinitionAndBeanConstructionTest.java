package org.baljinder.presenter.namespace;

import org.baljinder.presenter.dataacess.IDataController;
import org.baljinder.presenter.dataacess.IPageController;
import org.baljinder.presenter.dataacess.ITransitionController;
import org.baljinder.presenter.dataacess.internal.support.DirectDataAccessStrategy;
import org.baljinder.presenter.testing.SpringContextTestCase;
import org.baljinder.presenter.testing.support.DoNothingEventHandler;
import org.springframework.context.ApplicationContext;

/**
 * @author Baljinder Randhawa
 * 
 */
//TODO: Add all possible type of test cases
//most of the things are prototype scoped so comparing names of the references
//TODO: change scope to singleton for session scoped beans 
public class NameSpaceDefinitionAndBeanConstructionTest extends SpringContextTestCase {

	public void testDataControlDefinitionConstruction() {
		ApplicationContext ac = getApplicationContext();
		IDataController  eventHandlerSupportingDataControl = (IDataController) ac.getBean("EventHandler_Supporting_DataControl");
		assertNotNull(eventHandlerSupportingDataControl);
		assertTrue(eventHandlerSupportingDataControl.getEventHandler() instanceof DoNothingEventHandler);
		
		//TODO: test all configurable options
		IDataController  allPropertiesConfigurableDataControl = (IDataController) ac.getBean("All_Properties_Configurable_DataControl");
		assertNotNull(allPropertiesConfigurableDataControl);
		assertTrue(allPropertiesConfigurableDataControl.getEventHandler() instanceof DoNothingEventHandler);
		assertTrue(allPropertiesConfigurableDataControl.getDataAccessStrategy() instanceof DirectDataAccessStrategy);
		assertTrue(allPropertiesConfigurableDataControl.getPageSize() == 20);
		
		//TODO:add more options
		IDataController basicChildDataControl = (IDataController)ac.getBean("Basic_Child_DataControl");
		assertNotNull(basicChildDataControl);
		assertTrue(basicChildDataControl.getParentDataControl().getDataControlName().equals("All_Properties_Configurable_DataControl"));
	}
	
	public void testPageDefinitionConstruction(){
		ApplicationContext ac = getApplicationContext();
		IPageController page = (IPageController)ac.getBean("All_Properties_Configurable_Supporting_Page") ;
		assertNotNull(page.getEventHandler().equals(ac.getBean("doNothingEventHandler"))) ;
	}
	public void testTransitionDefinitionConstruction(){
		ApplicationContext ac = getApplicationContext();
		ITransitionController eventHandlerSupportingTransition =  (ITransitionController) ac.getBean("EventHandler_TransitionAction_Supporting_Transition");
		assertNotNull(eventHandlerSupportingTransition);
		assertTrue(eventHandlerSupportingTransition.getEventHandler() instanceof DoNothingEventHandler);
		
		ITransitionController transitionActionSupportingTransition =  (ITransitionController) ac.getBean("TransitionAction_Supporting_Transition");
		assertNotNull(transitionActionSupportingTransition);
		assertNotNull(transitionActionSupportingTransition.getTransitionAction());
		assertTrue(transitionActionSupportingTransition.getTargetPageController().getName().equals("All_Properties_Configurable_Supporting_Page"));
	}
}
