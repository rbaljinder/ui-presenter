package org.baljinder.presenter.dataacess.jsf.prime;

import java.util.Map;

import org.baljinder.presenter.dataacess.jsf.FacesDataController;
import org.baljinder.presenter.dataacess.jsf.SupportsJsfComponent;
import org.primefaces.model.LazyDataModel;

//TODO : implement methods
public class PrimeFacesSupportingDataController extends FacesDataController
		implements SupportsJsfComponent {

	private LazyDataModelAdapter dataModelAdapter = new LazyDataModelAdapter(
			this);
	
	public static enum DisplayMode {LIST,UPDATE,SHOW,CREATE} ;
	
	private DisplayMode displayMode = DisplayMode.LIST;
	
	public String getDisplayMode() {
		return displayMode.toString();
	}

	//Helper method to fool jsf.
	public void updateDisplayMode(){
		
	}
	public void setDisplayMode(DisplayMode displayMode) {
		this.displayMode = displayMode;
	}
	
	public void setDisplayMode(String displayMode) {
		this.displayMode = DisplayMode.valueOf(displayMode);
	}
	

	public LazyDataModel<Map<String, Object>> getDataModel() {
		return dataModelAdapter;
	}

	@Override
	protected String selectDataFromTable() {
		// throw new UnsupportedOperationException("Not implemented yet");
		return "";
	}

	@Override
	protected void clearSelectedIndexesOfUITable() {
		// throw new UnsupportedOperationException("Not implemented yet");
	}
//
//	@Override
//	public String create() {
//		setDisplayMode(DisplayMode.CREATE); //is there any way else just on client side
//		return super.create();
//	}
//	
//	@Override
//	public String delete() {
//		setDisplayMode(DisplayMode.LIST); 
//		return super.delete();
//	}
//	
//	@Override
//	public String deleteSelectedElements() {
//		setDisplayMode(DisplayMode.LIST); //is there any way else just on client side
//		return super.deleteSelectedElements();
//	}
//	
//	@Override
//	public String update() {
//		setDisplayMode(DisplayMode.LIST); //is there any way else just on client side
//		return super.update();
//	}
	
}