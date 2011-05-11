package org.baljinder.presenter.dataacess.jsf.namespace;

import org.apache.commons.lang.StringUtils;
import org.baljinder.presenter.dataacess.IDataController.Operation;
import org.baljinder.presenter.namespace.DataControlBeanDefinitionParser;
import org.baljinder.presenter.namespace.PageBeanDefinitionParser;
import org.baljinder.presenter.namespace.PresenterNameSpaceHandler;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.ManagedMap;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.util.xml.DomUtils;
import org.w3c.dom.Element;

public abstract class FacesSpecializedHanlder extends PresenterNameSpaceHandler {
	
	public String ACTION_OUTCOME_METHOD_BINDING_XSD = "action-outcome-binding";
	
	public String ACTION_METHOD_BINDING = "actionMethodBinding" ;
	
	protected static final String ACTION_OUTCOME_CREATE_XSD = "create";
	
	protected static final String ACTION_OUTCOME_SAVE_XSD = "save";
	
	protected static final String ACTION_OUTCOME_UPDATE_XSD = "update";
	
	protected static final String ACTION_OUTCOME_DELETE_XSD = "delete";
	
	protected static final String ACTION_OUTCOME_SELECT_XSD = "select";
	
	protected static final String ACTION_OUTCOME_SORT_XSD = "sort";

	
	public class FacesPageBeanDefinitionParser extends PageBeanDefinitionParser{
		
		@Override
		protected AbstractBeanDefinition createDataControlBeanDefinition(
				Element dataControlElement, ParserContext parserContext) {
			AbstractBeanDefinition dataControlDefinition =  super.createDataControlBeanDefinition(dataControlElement, parserContext);
			updateDataControlBeanDefinition(dataControlElement, parserContext, dataControlDefinition);
			return dataControlDefinition ;
		}
		
	}	
	public class FacesDataControlBeanDefinitionParser extends DataControlBeanDefinitionParser{
		
		@Override
		protected AbstractBeanDefinition createDataControlBeanDefinition(
				Element dataControlElement, ParserContext parserContext) {
			AbstractBeanDefinition dataControlDefinition =  super.createDataControlBeanDefinition(dataControlElement, parserContext);
			updateDataControlBeanDefinition(dataControlElement, parserContext, dataControlDefinition);
			return dataControlDefinition ;
		}
	}
	
	protected AbstractBeanDefinition updateDataControlBeanDefinition(
			Element dataControlElement, ParserContext parserContext,AbstractBeanDefinition dataControlDefinition) {
		MutablePropertyValues mpvs =dataControlDefinition.getPropertyValues();
		Element actionMethodBindingElement = DomUtils.getChildElementByTagName(dataControlElement, ACTION_OUTCOME_METHOD_BINDING_XSD);
		if(actionMethodBindingElement != null){
			ManagedMap<Operation, String> actionOutcomeBinding = new ManagedMap<Operation, String>();
			if(StringUtils.isNotBlank(actionMethodBindingElement.getAttribute(ACTION_OUTCOME_CREATE_XSD)))
				actionOutcomeBinding.put(Operation.CREATE, actionMethodBindingElement.getAttribute(ACTION_OUTCOME_CREATE_XSD));
			if(StringUtils.isNotBlank(actionMethodBindingElement.getAttribute(ACTION_OUTCOME_DELETE_XSD)))
				actionOutcomeBinding.put(Operation.DELETE, actionMethodBindingElement.getAttribute(ACTION_OUTCOME_DELETE_XSD));
			if(StringUtils.isNotBlank(actionMethodBindingElement.getAttribute(ACTION_OUTCOME_UPDATE_XSD)))
				actionOutcomeBinding.put(Operation.UPDATE, actionMethodBindingElement.getAttribute(ACTION_OUTCOME_UPDATE_XSD));
			if(StringUtils.isNotBlank(actionMethodBindingElement.getAttribute(ACTION_OUTCOME_SELECT_XSD)))
				actionOutcomeBinding.put(Operation.SELECT, actionMethodBindingElement.getAttribute(ACTION_OUTCOME_SELECT_XSD));
			if(StringUtils.isNotBlank(actionMethodBindingElement.getAttribute(ACTION_OUTCOME_SORT_XSD)))
				actionOutcomeBinding.put(Operation.SORT, actionMethodBindingElement.getAttribute(ACTION_OUTCOME_SORT_XSD));
			if(StringUtils.isNotBlank(actionMethodBindingElement.getAttribute(ACTION_OUTCOME_SAVE_XSD)))
				actionOutcomeBinding.put(Operation.SAVE, actionMethodBindingElement.getAttribute(ACTION_OUTCOME_SAVE_XSD));
			mpvs.addPropertyValue(ACTION_METHOD_BINDING,actionOutcomeBinding);
			dataControlDefinition.setPropertyValues(mpvs);
		}
		return dataControlDefinition ;
	}
}
