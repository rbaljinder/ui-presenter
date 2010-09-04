package org.baljinder.presenter.dataacess.internal;

import java.util.List;

import org.baljinder.presenter.dataacess.IDataControl;
import org.baljinder.presenter.dataacess.IEventHandler;
import org.baljinder.presenter.dataacess.IPageController;
import org.baljinder.presenter.dataacess.internal.support.EmptyEventHandler;
import com.google.common.collect.Lists;

public class PageController implements IPageController {

	private String name;
	
	private boolean cached;
	
	private IEventHandler eventHandler = EmptyEventHandler.doNothingHandler;
	
	
	private List<IDataControl> dataControlList = Lists.newArrayList();

	public void initialize() {
		eventHandler.beforeInitialize(this);
		for (IDataControl dataControl : dataControlList)
			dataControl.initialize();
		eventHandler.afterInitialize(this);
	}

	public boolean getInitialize() {
		initialize();
		return true;
	}
	

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.baljinder.presenter.jsf.ui.dataacess.ITransitionController#performTransition()
	 */
	public String transition() {
		eventHandler.beforeTransition(this);
		initialize();
		if (!getCached()) {
			for (IDataControl dataControl : getDataConrolList()) {
				dataControl.setDataFetched(false);
				dataControl.clearFilterValues();
				dataControl.setPageCursor(0);
			}
		}
		eventHandler.afterTransition(this);
		return name;
	}
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setDataControlList(List<IDataControl> dataControlList) {
		this.dataControlList = dataControlList;
	}

	public List<IDataControl> getDataConrolList() {
		return dataControlList;
	}

	public IDataControl getDataControl(String dataControlName) {
		for (IDataControl dataControl : dataControlList) {
			if (dataControlName.equals(dataControl.getDataControlName()))
				return dataControl;
		}
		return null;
	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.baljinder.presenter.jsf.ui.dataacess.ITransitionController#getCached()
	 */
	public boolean getCached() {
		return cached;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.baljinder.presenter.jsf.ui.dataacess.ITransitionController#setCached(boolean)
	 */
	public void setCached(boolean cached) {
		this.cached = cached;

	}

	/**
	 * @return the eventHandler
	 */
	public IEventHandler getEventHandler() {
		return eventHandler;
	}

	/**
	 * @param eventHandler the eventHandler to set
	 */
	public void setEventHandler(IEventHandler eventHandler) {
		this.eventHandler = eventHandler;
	}

	/* (non-Javadoc)
	 * @see org.baljinder.presenter.jsf.ui.dataacess.IPageController#saveAll()
	 */
	public String save() {
		for(IDataControl dataControl :getDataConrolList()){
			dataControl.save();
		}
		return null ; // refresh it 
	}
	public String saveAll() {
		eventHandler.beforeSave(this);
		for(IDataControl dataControl :getDataConrolList()){
			dataControl.saveAll();
		}
		eventHandler.afterSave(this);
		return null ; // refresh it 
	}
}
