/**
 * 
 */
package org.baljinder.presenter.dataacess.util;

import java.util.Map;

import org.baljinder.presenter.dataacess.IDataController;
import org.baljinder.presenter.dataacess.filter.Filter;
/**
 * @author Baljinder Randhawa
 *
 */
public interface IQueryBuilder {
	
	public Map<String, Map<String,Filter>> getFilters() ; 

	public void setFilters(Map<String, Map<String,Filter>> filters) ;
	
	public String buildWhereClause(IDataController dataControl);
	
	public String buildQuery(IDataController dataControl);
	
	public String getCountQuery(IDataController dataControl) ;
	
}
