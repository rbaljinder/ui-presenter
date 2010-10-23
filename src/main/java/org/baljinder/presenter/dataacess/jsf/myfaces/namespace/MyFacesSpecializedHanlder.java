package org.baljinder.presenter.dataacess.jsf.myfaces.namespace;

import org.baljinder.presenter.dataacess.jsf.myfaces.MyFacesSupportingDataController;
import org.baljinder.presenter.namespace.DataControlBeanDefinitionParser;
import org.baljinder.presenter.namespace.PresenterNameSpaceHandler;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.xml.ParserContext;
import org.w3c.dom.Element;

public class MyFacesSpecializedHanlder extends PresenterNameSpaceHandler {
	
	
	@Override
	public void init() {
		super.init();
		registerBeanDefinitionParser("data-control", new MFDataControlBeanDefinitionParser());
	}

	public class MFDataControlBeanDefinitionParser extends DataControlBeanDefinitionParser{
		
		public MFDataControlBeanDefinitionParser() {
			defaultClasses.put(DATACONTROLCLASS, MyFacesSupportingDataController.class);
		}
		
		@Override
		public BeanDefinition parse(Element dataControlElement, ParserContext parserContext) {
			// TODO Auto-generated method stub
			return super.parse(dataControlElement, parserContext);
		}
	}
}
