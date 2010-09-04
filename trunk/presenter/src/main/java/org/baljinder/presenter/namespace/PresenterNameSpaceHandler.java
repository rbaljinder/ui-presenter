package org.baljinder.presenter.namespace;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.beans.factory.xml.NamespaceHandlerSupport;
import org.springframework.beans.factory.xml.ParserContext;
import org.w3c.dom.Element;

/**
 *
 */

/**
 * @author Baljinder Randhawa
 * 
 */
public class PresenterNameSpaceHandler extends NamespaceHandlerSupport {

	public void init() {
		registerBeanDefinitionParser("page", new PageBeanDefinitionParser());
		registerBeanDefinitionParser("data-control", new DataControlBeanDefinitionParser());
		registerBeanDefinitionParser("transition", new TransitionBeanDefinitionParser());
		registerBeanDefinitionParser("event-handler", new EventHandlerBeanDefinitionParser());
	}

	public class EventHandlerBeanDefinitionParser extends AbstractBeanDefinitionParser {
		/*
		 * (non-Javadoc)
		 * 
		 * @see org.springframework.beans.factory.xml.BeanDefinitionParser#parse(org.w3c.dom.Element, org.springframework.beans.factory.xml.ParserContext)
		 */
		public BeanDefinition parse(Element eventHandlerElement, ParserContext parserContext) {
			AbstractBeanDefinition eventHandlerDef = createEventHandlerBeanDefinition(eventHandlerElement, parserContext);
			BeanDefinitionRegistry registry = parserContext.getRegistry();
			String eventHandlerBeanName = eventHandlerElement.getAttribute(NAME);
			registry.registerBeanDefinition(eventHandlerBeanName, eventHandlerDef);
			return eventHandlerDef;
		}

		private AbstractBeanDefinition createEventHandlerBeanDefinition(Element eventHandlerElement, ParserContext parserContext) {
			Class<? extends Object> clazz = getBeanClass(eventHandlerElement.getAttribute(CLASS));
			RootBeanDefinition pageDefinition = new RootBeanDefinition(clazz);
			pageDefinition.setScope(eventHandlerElement.getAttribute(SCOPE));
			pageDefinition.setSource(parserContext.extractSource(eventHandlerElement));
			return pageDefinition;
		}
	}
}
