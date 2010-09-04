/**
 * 
 */
package org.baljinder.presenter.namespace;

import java.util.List;

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
public class DataControlBeanDefinitionParser extends AbsractDataControlOrPageParser {
	static {
		defaults.put(DATACONTROLCLASS, "org.baljinder.presenter.dataacess.internal.DataControl");
		defaults.put(PAGECLASS, "org.baljinder.presenter.dataacess.internal.PageController");
		defaults.put(QUERYBUILDER, "defaultQueryBuilder");
		defaults.put(PERSISTANCEMANAGER, "presentationPersistence");
		defaults.put(DAOKEYNAME, "org.baljinder.presenter.dataacess.GenericPresentationDAO");
	}

	public BeanDefinition parse(Element dataControlElement, ParserContext parserContext) {
		AbstractBeanDefinition dataControlDef = createDataControlBeanDefinition(dataControlElement, parserContext);
		BeanDefinitionRegistry registry = parserContext.getRegistry();
		String dataControlBeanName = dataControlElement.getAttribute(NAME);
		if (StringUtils.isEmpty(dataControlBeanName))
			dataControlBeanName = BeanDefinitionReaderUtils.generateBeanName(dataControlDef, registry);
		
		registry.registerBeanDefinition(dataControlBeanName, dataControlDef);
		List<Element> childDataControlsElements = getChildDataControlElements(dataControlElement);
		for (Element aDataControlElement : childDataControlsElements) {
			parse(aDataControlElement, parserContext);
		}
		return dataControlDef;
	}
}
