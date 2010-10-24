/**
 * 
 */
package org.baljinder.presenter.namespace;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionReaderUtils;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.xml.ParserContext;
import org.w3c.dom.Element;

/**
 * @author Baljinder Randhawa
 * 
 */
//TODO: get rid of trickery used for transition action
public class TransitionBeanDefinitionParser extends AbstractBeanDefinitionParser {
	
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

}
