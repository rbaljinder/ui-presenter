/**
 * 
 */
package org.baljinder.presenter.domain;

/**
 * @author Baljinder Randhawa
 *
 */
public interface IAuditable {
	
	public boolean isInsertOnlyDefaults() ;
	
	public boolean isInsertOrUpdateDefaults();
	
}
