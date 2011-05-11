/**
 * 
 */
package org.baljinder.presenter.namespace;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionReaderUtils;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.ManagedList;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.beans.factory.xml.ParserContext;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * @author Baljinder Randhawa
 * 
 */
public class PageBeanDefinitionParser extends AbsractDataControlOrPageParser {

	public BeanDefinition parse(Element pageElement, ParserContext parserContext) {
		AbstractBeanDefinition pageDef = createPageBeanDefinition(pageElement, parserContext);
		BeanDefinitionRegistry registry = parserContext.getRegistry();
		String pageBeanName = pageElement.getAttribute(NAME);
		if (StringUtils.isEmpty(pageBeanName)) {
			pageBeanName = BeanDefinitionReaderUtils.generateBeanName(pageDef, registry);
		}
		ManagedList childBeanDefinitions = new ManagedList();
		NodeList childNodes = pageElement.getChildNodes();
		for (int i = 0; i < childNodes.getLength(); i++) {
			Node node = childNodes.item(i);
			if (node.getNodeType() == Node.ELEMENT_NODE) {
				String localName = node.getLocalName();
				if (DATACONTROL_XSD.equals(localName)) {
					parseDataControl((Element) node, parserContext, childBeanDefinitions);
				}
			}
		}
		pageDef.getPropertyValues().addPropertyValue(DATACONTROLLIST, childBeanDefinitions);
		registry.registerBeanDefinition(pageBeanName, pageDef);
		return pageDef;
	}

	/**
	 * Create a {@link RootBeanDefinition} for the advisor described in the supplied. Does <strong>not</strong> parse any associated '<code>pointcut</code>' or '<code>pointcut-ref</code>' attributes.
	 */
	private AbstractBeanDefinition createPageBeanDefinition(Element pageElement, ParserContext parserContext) {
		Class<? extends Object> clazz = getBeanClassFromChildOrDefault(pageElement, TYPE, CLASS, PAGECLASS);
		RootBeanDefinition pageDefinition = new RootBeanDefinition(clazz);
		pageDefinition.setScope(pageElement.getAttribute(SCOPE));
		// TODO: do we need it
		// pageDefinition.setInitMethodName(pageElement.getAttribute(INITMETHOD));
		pageDefinition.setSource(parserContext.extractSource(pageElement));
		MutablePropertyValues mpvs = pageDefinition.getPropertyValues();
		addEventHandlerDefinitionIfAny(pageElement,parserContext,mpvs,pageElement.getAttribute(SCOPE));
		mpvs.addPropertyValue(NAME, pageElement.getAttribute(NAME));
		mpvs.addPropertyValue(CACHED, pageElement.getAttribute(CACHED));
		addPropertyToBeanDefinition(getChildElementCollection(pageElement, PROPERTY),mpvs);
		List<Node> dataControlTransitions = getChildElementCollection(pageElement, TRANSITION_XSD);
		for (Node aDataControlTransition : dataControlTransitions) {
			Element pageTransition = (Element) aDataControlTransition ; 
			AbstractBeanDefinition transitionDef = createTransitionBeanDefintionForOtherElements(pageTransition, parserContext);
			String transitionBeanName = pageTransition.getAttribute(NAME);
			BeanDefinitionRegistry registry = parserContext.getRegistry();
			registry.registerBeanDefinition(transitionBeanName, transitionDef);
		}	
		pageDefinition.setPropertyValues(mpvs);
		return pageDefinition;
	}
}
