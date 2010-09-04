package org.baljinder.presenter.dataacess;

import java.util.List;

public interface IPageController extends SupportsEventHandler {

	public void initialize();
	
	public String getName() ;
	
	public void setName(String name) ;
	
	public void setCached(boolean chached);

	public boolean getCached();

	public void setDataControlList(List<IDataControl> dataControlList);

	public List<IDataControl> getDataConrolList();

	public IDataControl getDataControl(String dataControlName);
	
	public String transition() ;
	
	public String save();
	
	public String saveAll();
	
}
