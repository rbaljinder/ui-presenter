/**
 * 
 */
package org.baljinder.presenter.dataacess;



/**
 * One possibility is handle these event through aspects if that works 
 * @author Baljinder Randhawa
 *
 */
public interface IEventHandler {

	public void beforeTransition(ITransitionController transitionController);
	
	// what could be this used for. because according to this framework data is not fetched 
	//just after transition action, but only when its render phase
	public void afterTransition(ITransitionController transitionController);
	
	public void beforeInitialize(IDataController dataControl);
	
	public void afterInitialize(IDataController dataControl);
	
	public void beforeDataFetch(IDataController dataControl);
	
	public void afterDataFetch(IDataController dataControl);
	
	public void beforeSave(IDataController dataControl);
	
	public void afterSave(IDataController dataControl);
	
	public void beforeDelete(IDataController dataControl);
	
	public void afterDelete(IDataController dataControl);
	
	public void beforeUpdate(IDataController dataControl);
	
	public void afterUpdate(IDataController dataControl);

	/**
	 * @param dataControl
	 */
	public void beforeInsert(IDataController dataControl);

	/**
	 * @param dataControl
	 */
	public void afterInsert(IDataController dataControl);

	/**
	 * @param pageController
	 */
	public void beforeInitialize(IPageController pageController);

	/**
	 * @param pageController
	 */
	public void afterInitialize(IPageController pageController);
	
	public void beforeTransition(IPageController pageController);

	public void afterTransition(IPageController pageController);

	/**
	 * @param pageController
	 */
	public void beforeSave(IPageController pageController);

	/**
	 * @param pageController
	 */
	public void afterSave(IPageController pageController);

	/**
	 * @param dataControl
	 */
	public void beforeRefresh(IDataController dataControl);

	/**
	 * @param dataControl
	 */
	public void afterRefresh(IDataController dataControl);

}
