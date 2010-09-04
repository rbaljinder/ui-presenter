/**
 * 
 */
package org.baljinder.presenter.dataacess.util;

import java.util.Map;

import org.baljinder.presenter.dataacess.IDataControl;
import org.baljinder.presenter.dataacess.filter.Filter;
/**
 * @author Baljinder Randhawa
 *
 */
public interface IQueryBuilder {
	
	public Map<String, Map<String,Filter>> getFilters() ; 

	public void setFilters(Map<String, Map<String,Filter>> filters) ;
	
	public String buildWhereClause(IDataControl dataControl);
	
	public String buildQuery(IDataControl dataControl);
	
	public String getCountQuery(IDataControl dataControl) ;
	
}
