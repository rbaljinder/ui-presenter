package org.baljinder.presenter.namespace;

import org.baljinder.presenter.dataacess.IDataControl;
import org.baljinder.presenter.dataacess.IPageController;
import org.baljinder.presenter.dataacess.ITransitionController;
import org.baljinder.presenter.dataacess.internal.support.JSFSupportingDataAccessStrategy;
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
		IDataControl  eventHandlerSupportingDataControl = (IDataControl) ac.getBean("EventHandler_Supporting_DataControl");
		assertNotNull(eventHandlerSupportingDataControl);
		assertTrue(eventHandlerSupportingDataControl.getEventHandler() instanceof DoNothingEventHandler);
		
		//TODO: test all configurable options
		IDataControl  allPropertiesConfigurableDataControl = (IDataControl) ac.getBean("All_Properties_Configurable_DataControl");
		assertNotNull(allPropertiesConfigurableDataControl);
		assertTrue(allPropertiesConfigurableDataControl.getEventHandler() instanceof DoNothingEventHandler);
		assertTrue(allPropertiesConfigurableDataControl.getDataAccessStrategy() instanceof JSFSupportingDataAccessStrategy);
		assertTrue(allPropertiesConfigurableDataControl.getPageSize() == 20);
		
		//TODO:add more options
		IDataControl basicChildDataControl = (IDataControl)ac.getBean("Basic_Child_DataControl");
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