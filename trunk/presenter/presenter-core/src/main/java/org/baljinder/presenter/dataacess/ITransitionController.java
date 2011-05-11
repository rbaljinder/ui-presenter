/**
 * 
 */
package org.baljinder.presenter.dataacess;

import java.util.List;

/**
 * @author Baljinder Randhawa
 * 
 */
public interface ITransitionController extends SupportsEventHandler {

	public enum TransitionMode {
		QUERY, LOAD, CREATE
	};

	public static final String NO_TRANSITION = "refresh";

	public static final TransitionMode defaultTransitionMode = TransitionMode.LOAD;

	public void setName(String name);

	public String getName();

	public void setTransitionMode(TransitionMode transitionMode);

	public TransitionMode getTransitionMode();

	public void setSourceDataControl(IDataController sourceDataController);

	public IDataController getSourceDataControl();

	public void setTargetDataControl(IDataController targetDataController);

	public IDataController getTargetDataControl();

	public IPageController getTargetPageController();

	public void setTargetPageController(IPageController targetPageController);

	public String performTransition();

	public String selectAndPerformTransition();

	public List<String> getTransitionCriteria();

	public void setTransitionCriteria(List<String> transitionCriterion);

	public void setTransitionAction(ITransitionAction transitionAction);

	public ITransitionAction getTransitionAction();
}
