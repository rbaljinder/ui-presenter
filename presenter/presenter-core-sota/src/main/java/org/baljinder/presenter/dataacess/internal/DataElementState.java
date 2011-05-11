package org.baljinder.presenter.dataacess.internal;

public class DataElementState {

	public enum DataState{ SAVED,UNSAVED};
	
	private DataState state ;

	public DataState getState() {
		return state;
	}

	public void setState(DataState state) {
		this.state = state;
	}
}
