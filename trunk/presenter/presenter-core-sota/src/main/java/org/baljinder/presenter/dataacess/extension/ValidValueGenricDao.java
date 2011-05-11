/**
 * 
 */
package org.baljinder.presenter.dataacess.extension;

import java.util.List;
import java.util.Map;

import org.baljinder.presenter.dataacess.IPresentationDao;

/**
 * @author Baljinder Randhawa
 * 
 */
public interface ValidValueGenricDao extends IPresentationDao {

	public void checkExistanceThenSaveOrUpdate(List<Map<String, Object>> elementList, Class<? extends Object> entitiyName);

	public void checkExistanceThenSaveOrUpdate(Object currentElementInternal, Class<? extends Object> entitiyName);

	public boolean createNewIfDoesNotExist(Object currentElementInternal, Class<? extends Object> entitiyName);

	public Object getAssignedIdOfObject(Object currentElementInternal, Class<? extends Object> entitiyName);

	public void deleteExistingIfIdChangeOrUpdate(Object currentElementInternal, Class<? extends Object> modelClassName, List<Object> specialHandlingForObjectsWithAssignedId);
}
