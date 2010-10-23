/**
 * 
 */
package org.baljinder.presenter.dataacess;

/**
 * @author Baljinder Randhawa
 *
 */
public interface SupportsEventHandler {

	public void setEventHandler(IEventHandler eventHandler);
	
	public IEventHandler getEventHandler();

}
