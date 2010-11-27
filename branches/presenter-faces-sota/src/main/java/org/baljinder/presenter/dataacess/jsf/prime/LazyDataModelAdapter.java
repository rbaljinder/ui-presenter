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
		return dataController.getData(first, pageSize, sortField, sortOrder, filters);
	}
	
	@Override
	public int getRowCount() {
		return dataController.getDao().getCountOfRecords(dataController.getCountQuery());
	}

}
