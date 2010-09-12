/**
 * 
 */
package org.baljinder.presenter.namespace;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.baljinder.presenter.dataacess.internal.GenericPresentationDao;
import org.baljinder.presenter.dataacess.internal.extension.ValidValueDataControl;
import org.baljinder.presenter.dataacess.internal.extension.ValidValueGenericDaoImpl;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.config.RuntimeBeanReference;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionReaderUtils;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.ManagedList;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.util.xml.DomUtils;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.google.common.collect.Lists;

/**
 * @author Baljinder Randhawa
 * 
 */
//TODO: add event-handler-ref property to data control definition
public abstract class AbsractDataControlOrPageParser extends AbstractBeanDefinitionParser {

	protected final static String DATAACCESSSTRATEGY = "dataAccessStrategy";

	protected final static String ACCESSSTRATEGY = "access-strategy";

	protected final static String JSFBASED = "jsf-based"; //TODO: remove me

	protected final static String DIRECT = "direct";

	protected final static String CODEDVALUE = "coded-value";

	protected final static String DATACONTROLCLASSFORCODEDVALUE = "org.baljinder.presenter.dataacess.internal.extension.ValidValueDataControl";

	protected final static String DAOKEYNAMEFORCODEDVALUE = "org.baljinder.presenter.dataacess.internal.extension.ValidValueGenericDaoImpl";

	public AbsractDataControlOrPageParser() {
		defaults.put(JSFBASED, "jsfBasedDataAccessStrategy");//TODO: remove me
		defaults.put(DIRECT, "directDataAccessStrategy");
		defaultClasses.put(DATACONTROLCLASSFORCODEDVALUE, ValidValueDataControl.class);
		defaultClasses.put(DAOKEYNAMEFORCODEDVALUE, ValidValueGenericDaoImpl.class);
		defaultClasses.put(DAOKEYNAME, GenericPresentationDao.class);
		defaults.put(DATACONTROLCLASS, "org.baljinder.presenter.dataacess.internal.DataController");
		defaults.put(PAGECLASS, "org.baljinder.presenter.dataacess.internal.PageController");
		defaults.put(QUERYBUILDER, "defaultQueryBuilder");
		defaults.put(PERSISTANCEMANAGER, "presentationPersistence");
		defaults.put(DAOKEYNAME, "org.baljinder.presenter.dataacess.internal.GenericPresentationDao");
	}

	/**
	 * @param dataControlElement
	 * @param parserContext
	 * @param childBeanDefinitions
	 */
	protected AbstractBeanDefinition parseDataControl(Element dataControlElement, ParserContext parserContext, ManagedList childBeanDefinitions) {
		AbstractBeanDefinition dataControlDef = createDataControlBeanDefinition(dataControlElement, parserContext);
		BeanDefinitionRegistry registry = parserContext.getRegistry();
		String dataControlBeanName = dataControlElement.getAttribute(NAME);
		if (StringUtils.isEmpty(dataControlBeanName))
			dataControlBeanName = BeanDefinitionReaderUtils.generateBeanName(dataControlDef, registry);
		childBeanDefinitions.add(new RuntimeBeanReference(dataControlBeanName));
		registry.registerBeanDefinition(dataControlBeanName, dataControlDef);
		List<Element> childDataControlsElements = getChildDataControlElements(dataControlElement);
		for (Element aDataControlElement : childDataControlsElements) {
			parseDataControl(aDataControlElement, parserContext, childBeanDefinitions);
		}
		return dataControlDef;
	}

	/**
	 * @param dataControlElement
	 * @return
	 */
	protected List<Element> getChildDataControlElements(Element dataControlElement) {
		List<Element> childElementList = Lists.newArrayList();
		NodeList childNodes = dataControlElement.getChildNodes();
		for (int i = 0; i < childNodes.getLength(); i++) {
			Node node = childNodes.item(i);
			if (node.getNodeType() == Node.ELEMENT_NODE) {
				String localName = node.getLocalName();
				if (DATACONTROL_XSD.equals(localName)) {
					childElementList.add((Element) node);
				}
			}
		}
		return childElementList;
	}

	/**
	 * Create a {@link RootBeanDefinition} for the advisor described in the supplied. Does <strong>not</strong> parse any associated '<code>pointcut</code>' or '<code>pointcut-ref</code>' attributes.
	 */
	protected AbstractBeanDefinition createDataControlBeanDefinition(Element dataControlElement, ParserContext parserContext) {
		Class<? extends Object> clazz = getBeanClassFromChildOrDefault(dataControlElement, TYPE, CLASS, DATACONTROLCLASS);
		Boolean codedValue = Boolean.valueOf(dataControlElement.getAttribute(CODEDVALUE));
		if(codedValue)
				clazz = getBeanClass(DATACONTROLCLASSFORCODEDVALUE);
		RootBeanDefinition dataControlDefinition = new RootBeanDefinition(clazz);
		dataControlDefinition.setScope(dataControlElement.getAttribute(SCOPE));
		// dataControlDefinition.setInitMethodName(dataControlElement.getAttribute(INITMETHOD));
		dataControlDefinition.setSource(parserContext.extractSource(dataControlElement));
		MutablePropertyValues mpvs = dataControlDefinition.getPropertyValues();
		addEventHandlerDefinitionIfAny(dataControlElement,parserContext,mpvs,dataControlElement.getAttribute(SCOPE));
		mpvs.addPropertyValue(DATACONTROLNAME, dataControlElement.getAttribute(NAME));
		mpvs.addPropertyValue(PAGESIZE, dataControlElement.getAttribute(SIZE));
		addProprtyFromChildOrDefault(mpvs, dataControlElement, PERSISTANCEMANAGER_XSD, MANAGER, PERSISTANCEMANAGER, true);
		addProprtyFromChildOrDefault(mpvs, dataControlElement, QUERYBUILDER_XSD, BUILDER, QUERYBUILDER, true);
		if(codedValue)
			mpvs.addPropertyValue(DAOKEYNAME,DAOKEYNAMEFORCODEDVALUE);
		else
			addProprtyFromChildOrDefault(mpvs, dataControlElement, DAOKEYNAME_XSD, KEY, DAOKEYNAME);
		String accessStrategyClass = defaults.get(dataControlElement.getAttribute(ACCESSSTRATEGY));
		mpvs.addPropertyValue(DATAACCESSSTRATEGY, new RuntimeBeanReference(accessStrategyClass));
		addProprtyOfChild(mpvs, dataControlElement, DEFAULTWHERECLAUSE_XSD, CLAUSE, DEFAULTWHERECLAUSE);
		addProprtyOfChild(mpvs, dataControlElement, JOINCRITERIA_XSD, CRITERIA, JOINCRITERIA);
		mpvs.addPropertyValue(MODELLIST, getAttributeCollectionFromChilds(dataControlElement, MODEL, CLASS));
		String parentDataControl = getParentDataContorlIfAny(dataControlElement);
		if (parentDataControl != null) {
			mpvs.addPropertyValue(PARENTDATACONTROL, new RuntimeBeanReference(parentDataControl));
			Element relations = DomUtils.getChildElementByTagName(dataControlElement, PARENTRELATIONS_XSD);
			mpvs.addPropertyValue(PARENTCHILDRELATION, getAttributeCollectionFromChilds(relations, PARENTRELATION_XSD, RELATION));
		}
		addPropertyToBeanDefinition(getChildElementCollection(dataControlElement, PROPERTY),mpvs);
		dataControlDefinition.setPropertyValues(mpvs);
		return dataControlDefinition;
	}

	/**
	 * @param dataControlElement
	 * @return
	 */
	protected String getParentDataContorlIfAny(Element dataControlElement) {
		if (DATACONTROL_XSD.equals(dataControlElement.getParentNode().getLocalName()))
			return ((Element) dataControlElement.getParentNode()).getAttribute(NAME);
		return null;
	}
}
