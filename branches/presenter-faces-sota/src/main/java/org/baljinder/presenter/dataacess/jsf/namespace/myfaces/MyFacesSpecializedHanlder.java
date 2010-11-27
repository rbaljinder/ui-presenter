package org.baljinder.presenter.dataacess.jsf.namespace.myfaces;

import org.baljinder.presenter.dataacess.jsf.myfaces.MyFacesSupportingDataController;
import org.baljinder.presenter.dataacess.jsf.namespace.FacesSpecializedHanlder;

public class MyFacesSpecializedHanlder extends FacesSpecializedHanlder {
	
	@Override
	public void init() {
		super.init();
		registerBeanDefinitionParser("data-control", new MyFacesDataControlBeanDefinitionParser());
		registerBeanDefinitionParser("page", new MyFacesPageBeanDefinitionParser());
	}

	public class MyFacesPageBeanDefinitionParser extends FacesPageBeanDefinitionParser{
		
		public MyFacesPageBeanDefinitionParser() {
			defaultClasses.put(DATACONTROLCLASS, MyFacesSupportingDataController.class);
		}
	}	
	public class MyFacesDataControlBeanDefinitionParser extends FacesDataControlBeanDefinitionParser{
		
		public MyFacesDataControlBeanDefinitionParser() {
			defaultClasses.put(DATACONTROLCLASS, MyFacesSupportingDataController.class);
		}
	}
}
