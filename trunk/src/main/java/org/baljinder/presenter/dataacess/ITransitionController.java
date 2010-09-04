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

	public static final String QUERY = "query";

	public static final String LOAD = "load";

	public static final String INSERT = "insert";

	public static final String NO_TRANSITION = "refresh";
	
	public static final String defaultTransitionMode = LOAD;

	public void setName(String name);
	
	public String getName();
	
	public void setTransitionMode(String transitionMode);

	public String getTransitionMode();

	public void setSourceDataControl(IDataControl sourceDataControl);

	public IDataControl getSourceDataControl();

	public void setTargetDataControl(IDataControl targetDataControl);

	public IDataControl getTargetDataControl();

	/**
	 * @return the targetPageController
	 */
	public IPageController getTargetPageController() ;

	/**
	 * @param targetPageController the targetPageController to set
	 */
	public void setTargetPageController(IPageController targetPageController) ;
	
	public String performTransition();
	
	public String selectAndPerformTransition();

	public List<String> getTransitionCriteria() ;

	public void setTransitionCriteria(List<String> transitionCriterion) ;

	public void setTransitionAction(ITransitionAction transitionAction) ;
	
	public ITransitionAction getTransitionAction();
}
