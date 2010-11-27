package org.baljinder.presenter.dataacess.jsf.prime;

import java.util.Map;

import org.baljinder.presenter.dataacess.jsf.FacesDataController;
import org.baljinder.presenter.dataacess.jsf.SupportsJsfComponent;
import org.primefaces.model.LazyDataModel;

//TODO : implement methods
public class PrimeFacesSupportingDataController extends FacesDataController implements SupportsJsfComponent {

	private LazyDataModelAdapter dataModelAdapter = new LazyDataModelAdapter(this);
	
	public LazyDataModel<Map<String, Object>> getDataModel(){
		return dataModelAdapter;
	}

	@Override
	protected String selectDataFromTable() {
		throw new UnsupportedOperationException("Not implemented yet");
	}

	@Override
	protected void clearSelectedIndexesOfUITable() {
		throw new UnsupportedOperationException("Not implemented yet");
	}
	
}
