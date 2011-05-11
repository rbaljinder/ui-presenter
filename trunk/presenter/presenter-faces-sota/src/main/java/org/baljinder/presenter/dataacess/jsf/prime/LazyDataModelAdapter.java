package org.baljinder.presenter.dataacess.jsf.prime;

import java.util.List;
import java.util.Map;

import org.primefaces.model.LazyDataModel;

public class LazyDataModelAdapter extends LazyDataModel<Map<String, Object>>{

	private static final long serialVersionUID = 1L;
	
	private PrimeFacesSupportingDataController dataController;

	
	public LazyDataModelAdapter(
			PrimeFacesSupportingDataController primeFacesSupportingDataController) {
		dataController = primeFacesSupportingDataController;
	}


	@Override
	public List<Map<String, Object>> load(int first, int pageSize, String sortField, boolean sortOrder, Map<String, String> filters) {
		if (dataController.getDao() == null)
			dataController.setDao(dataController.getPersistenceManager().getDAO(dataController.getDaoKeyName()));
		return dataController.getData(first, pageSize, sortField, sortOrder, filters);
	}
	
	//FIXME: query is being fired twice: may be set it in the above method and keep an attribute on datacontroller so that we dont have to calculate it again and again 
	@Override
	public int getRowCount() {
		if (dataController.getDao() == null)
			dataController.setDao(dataController.getPersistenceManager().getDAO(dataController.getDaoKeyName()));
		return dataController.getDao().getCountOfRecords(dataController.getCountQuery());
	}

}
