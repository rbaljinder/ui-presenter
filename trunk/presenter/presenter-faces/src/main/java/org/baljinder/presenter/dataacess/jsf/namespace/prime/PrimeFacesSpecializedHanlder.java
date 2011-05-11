package org.baljinder.presenter.dataacess.jsf.namespace.prime;

import org.baljinder.presenter.dataacess.jsf.namespace.FacesSpecializedHanlder;
import org.baljinder.presenter.dataacess.jsf.prime.PrimeFacesSupportingDataController;

public class PrimeFacesSpecializedHanlder extends FacesSpecializedHanlder {
	
	@Override
	public void init() {
		super.init();
		registerBeanDefinitionParser("data-control", new PrimeDataControlBeanDefinitionParser());
		registerBeanDefinitionParser("page", new PrimePageBeanDefinitionParser());
	}

	public class PrimePageBeanDefinitionParser extends FacesPageBeanDefinitionParser{
		
		public PrimePageBeanDefinitionParser() {
			defaultClasses.put(DATACONTROLCLASS, PrimeFacesSupportingDataController.class);
		}
	}	
	public class PrimeDataControlBeanDefinitionParser extends FacesDataControlBeanDefinitionParser{
		
		public PrimeDataControlBeanDefinitionParser() {
			defaultClasses.put(DATACONTROLCLASS, PrimeFacesSupportingDataController.class);
		}
	}
}
