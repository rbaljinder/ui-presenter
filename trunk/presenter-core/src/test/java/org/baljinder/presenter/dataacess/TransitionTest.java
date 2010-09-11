package org.baljinder.presenter.dataacess;

import java.util.Map;

import org.baljinder.presenter.dataacess.ITransitionController.TransitionMode;
import org.baljinder.presenter.testing.BasicIntegrationTestCase;
import org.springframework.context.ApplicationContext;

public class TransitionTest extends BasicIntegrationTestCase  {

	public void testTransitionToTarget(){
		ApplicationContext applicationContext = getApplicationContext();
		ITransitionController transitionController = (ITransitionController) applicationContext.getBean("TransitionAction_Supporting_Transition");
		assertTrue(transitionController.performTransition().equals(transitionController.getTargetPageController().getName()));
	}

	public void testMode(){
		ApplicationContext applicationContext = getApplicationContext();
		ITransitionController transitionController = (ITransitionController) applicationContext.getBean("TransitionAction_Supporting_Transition");
		IDataController targetDataControl = transitionController.getTargetPageController().getDataControl("All_Properties_Configurable_DataControl");
		transitionController.setTransitionMode(TransitionMode.LOAD);
		transitionController.performTransition();
		assertTrue(targetDataControl.getData().size()==1);
		transitionController.setTransitionMode(TransitionMode.QUERY);
		transitionController.performTransition();
		assertTrue(targetDataControl.getData().size()==0);
		transitionController.setTransitionMode(TransitionMode.CREATE);
		transitionController.performTransition();
		assertTrue(targetDataControl.getNewlyCreatedElement().size()==1);
		for(Map<String, Object> newElement :targetDataControl.getNewlyCreatedElement()){
			for(Map.Entry<String, Object> element:newElement.entrySet()){
				org.baljinder.presenter.testing.support.model.System system = (org.baljinder.presenter.testing.support.model.System)element.getValue();
				system.setClientId((long) 100);
				system.setSystemId((long) 100);
				system.setSystemName("TESTNAME");
			}
		}
		targetDataControl.save();
		transitionController.setTransitionMode(TransitionMode.LOAD);
		transitionController.performTransition();
		assertTrue(targetDataControl.getData().size()==1);
	}

}
