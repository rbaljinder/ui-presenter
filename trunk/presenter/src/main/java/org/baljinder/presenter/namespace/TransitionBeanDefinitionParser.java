/**
 * 
 */
package org.baljinder.presenter.namespace;

import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.baljinder.presenter.dataacess.ITransitionController.TransitionMode;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.RuntimeBeanReference;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionReaderUtils;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.util.xml.DomUtils;
import org.w3c.dom.Element;

import com.google.common.collect.Maps;

/**
 * @author Baljinder Randhawa
 * 
 */
//TODO: get rid of trickery used for transition action
public class TransitionBeanDefinitionParser extends AbstractBeanDefinitionParser {
	
	private static Map<String,TransitionMode> transitionModeMapping = Maps.newHashMap() ;
	
	static{
		transitionModeMapping.put("load", TransitionMode.LOAD);
		transitionModeMapping.put("query", TransitionMode.QUERY);
		transitionModeMapping.put("insert", TransitionMode.CREATE);
	}
	
	public BeanDefinition parse(Element transitionElement, ParserContext parserContext) {
		AbstractBeanDefinition transitionDef = createTransitionBeanDefinition(transitionElement, parserContext);
		BeanDefinitionRegistry registry = parserContext.getRegistry();
		String transitionBeanName = transitionElement.getAttribute(NAME);
		if (StringUtils.isEmpty(transitionBeanName)) {
			transitionBeanName = BeanDefinitionReaderUtils.generateBeanName(transitionDef, registry);
		}
		String transitionActionBeanName = getTransitionActionBeanName(transitionElement);
		if(transitionActionBeanName != null)
			registry.registerBeanDefinition(transitionActionBeanName, createTransitionActionBeanDefinition(transitionElement));
		registry.registerBeanDefinition(transitionBeanName, transitionDef);
		return null;
	}

	/**
	 * @param transitionElement
	 * @param parserContext
	 * @return
	 */
	private AbstractBeanDefinition createTransitionBeanDefinition(Element transitionElement, ParserContext parserContext) {
		Class<? extends Object> clazz = getBeanClassFromChildOrDefault(transitionElement, TYPE, CLASS, TRANSITIONCLASS);
		RootBeanDefinition transitionDefinition = new RootBeanDefinition(clazz);
		transitionDefinition.setScope(transitionElement.getAttribute(SCOPE));
		transitionDefinition.setSource(parserContext.extractSource(transitionElement));
		MutablePropertyValues mpvs = transitionDefinition.getPropertyValues();
		mpvs.addPropertyValue(NAME, transitionElement.getAttribute(NAME));
		addProprtyOfChild(mpvs, transitionElement, TARGETPAGE_XSD, PAGE, TARGETPAGECONTROLLER, true);
		addProprtyOfChild(mpvs, transitionElement, SOURCEDATACONTROL_XSD, DATACONTROL_XSD, SOURCEDATACONTROL, true);
		addProprtyOfChild(mpvs, transitionElement, TARGETDATACONTROL_XSD, DATACONTROL_XSD, TARGETDATACONTROL, true);
		addEventHandlerDefinitionIfAny(transitionElement,parserContext,mpvs,transitionElement.getAttribute(SCOPE));
		String transitionActionBeanName = getTransitionActionBeanName(transitionElement);
		if (transitionActionBeanName != null) 
			mpvs.addPropertyValue(TRANSITIONACTION, new RuntimeBeanReference(transitionActionBeanName));
		mpvs.addPropertyValue(TRANSITIONMODE, transitionModeMapping.get(transitionElement.getAttribute(MODE)));
		Element transitionCriteria = DomUtils.getChildElementByTagName(transitionElement, TRANSITIONCRITERIA_XSD);
		if (transitionCriteria != null) {
			mpvs.addPropertyValue(TRANSITIONCRITERIA, getAttributeCollectionFromChilds(transitionCriteria, TRANSITIONCRITERION_XSD, CRITERION));
		}
		transitionDefinition.setPropertyValues(mpvs);
		return transitionDefinition;
	}
	
	private String getTransitionActionBeanName(Element transitionElement){
		Element transitionActionElement = DomUtils.getChildElementByTagName(transitionElement, TRANSITIONACTION_XSD);
		if(transitionActionElement == null)
			return null;
		return transitionElement.getAttribute(NAME)+"TransitionAction";
	}
	// TODO : there must be some other way of doing it .. may be FactoryBean
	private BeanDefinition createTransitionActionBeanDefinition(Element transitionElement) {
		Element transitionActionElement = DomUtils.getChildElementByTagName(transitionElement, TRANSITIONACTION_XSD);
		String clazz = transitionActionElement.getAttribute(CLASS);
		RootBeanDefinition transitionActionDefinition = new RootBeanDefinition(getBeanClass(clazz));
		transitionActionDefinition.setScope(transitionActionElement.getAttribute(SCOPE));
		return transitionActionDefinition;
	}

}
