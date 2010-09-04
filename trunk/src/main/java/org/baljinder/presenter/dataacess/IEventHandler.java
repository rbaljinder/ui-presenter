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
	
	public void beforeInitialize(IDataControl dataControl);
	
	public void afterInitialize(IDataControl dataControl);
	
	public void beforeDataFetch(IDataControl dataControl);
	
	public void afterDataFetch(IDataControl dataControl);
	
	public void beforeSave(IDataControl dataControl);
	
	public void afterSave(IDataControl dataControl);
	
	public void beforeDelete(IDataControl dataControl);
	
	public void afterDelete(IDataControl dataControl);
	
	public void beforeUpdate(IDataControl dataControl);
	
	public void afterUpdate(IDataControl dataControl);

	/**
	 * @param dataControl
	 */
	public void beforeInsert(IDataControl dataControl);

	/**
	 * @param dataControl
	 */
	public void afterInsert(IDataControl dataControl);

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
	public void beforeRefresh(IDataControl dataControl);

	/**
	 * @param dataControl
	 */
	public void afterRefresh(IDataControl dataControl);

}
