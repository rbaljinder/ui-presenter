/**
 * 
 */
package org.baljinder.presenter.namespace;

import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.BeanDefinitionStoreException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.RuntimeBeanReference;
import org.springframework.beans.factory.support.ManagedList;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.beans.factory.xml.BeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.util.ClassUtils;
import org.springframework.util.xml.DomUtils;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import org.baljinder.presenter.dataacess.IPresentationDao;
import org.baljinder.presenter.dataacess.internal.DataControl;
import org.baljinder.presenter.dataacess.internal.PageController;
import org.baljinder.presenter.dataacess.internal.TransitionController;
import com.google.common.collect.Maps;

/**
 * @author Baljinder Randhawa
 * 
 */
public abstract class AbstractBeanDefinitionParser implements BeanDefinitionParser {

	protected static Map<String, String> defaults = Maps.newHashMap();
	
	protected static Map<String, Class<? extends Object>> defaultClasses = Maps.newHashMap();

	protected static final String TYPE = "type";

	protected static final String CLASS = "class";

	protected static final String NAME = "name";

	protected static final String CACHED = "cached";

	protected static final String SCOPE = "scope";

	protected static final String DATACONTROL_XSD = "data-control";
	
	protected static final String DATACONTROL = "dataControl";

	protected static final String DEFAULTWHERECLAUSE_XSD = "default-where-clause";
	
	protected static final String DEFAULTWHERECLAUSE = "defaultWhereClause";

	protected static final String CLAUSE = "clause";

	protected static final String DAOKEYNAME_XSD = "dao-key-name";
	
	protected static final String DAOKEYNAME = "daoKeyName";

	protected static final String KEY = "key";

	protected static final String PERSISTANCEMANAGER_XSD = "persistence-manager";
	
	protected static final String PERSISTANCEMANAGER = "persistenceManager";

	protected static final String MANAGER = "manager";

	protected static final String QUERYBUILDER_XSD = "query-builder";
	
	protected static final String QUERYBUILDER = "queryBuilder";

	protected static final String BUILDER = "builder";

	protected static final String MODEL = "model";

	protected static final String MODELLIST = "modelList";

	protected static final String JOINCRITERIA_XSD = "join-criteria";
	
	protected static final String JOINCRITERIA = "joinCriteria";

	protected static final String CRITERIA = "criteria";

	protected static final String PAGESIZE = "pageSize";

	protected static final String SIZE = "size";

	protected static final String INITMETHOD = "init-method";

	protected static final String DATACONTROLNAME = "dataControlName";

	protected static final String DATACONTROLCLASS = "dataControlClass";

	protected static final String DATACONTROLLIST = "dataControlList";

	protected static final String PARENTDATACONTROL = "parentDataControl";

	protected static final String PARENTRELATIONS_XSD = "parent-relations";
	
	protected static final String PARENTRELATIONS = "parentRelations";

	protected static final String PARENTCHILDRELATION = "parentChildRelation";

	protected static final String PARENTRELATION = "parentRelation";
	
	protected static final String PARENTRELATION_XSD = "parent-relation";

	protected static final String RELATION = "relation";

	protected static final String TRANSITIONCLASS = "transitionClass";

	protected static final String MODE = "mode";

	protected static final String TRANSITIONMODE = "transitionMode";

	protected static final String SOURCEDATACONTROL_XSD = "source-data-control";

	protected static final String SOURCEDATACONTROL = "sourceDataControl";

	protected static final String TARGETDATACONTROL_XSD = "target-data-control";
	
	protected static final String TARGETDATACONTROL = "targetDataControl";

	protected static final String TRANSITIONCRITERIA_XSD = "transition-criteria";
	
	protected static final String TRANSITIONCRITERIA = "transitionCriteria";

	protected static final String TRANSITIONCRITERION_XSD = "transition-criterion";
	
	protected static final String TRANSITIONCRITERION = "transitionCriterion";

	protected static final String TRANSITIONACTION_XSD = "transition-action";
	
	protected static final String TRANSITIONACTION = "transitionAction";

	protected static final String CRITERION = "criterion";

	protected static final String PAGE = "page";

	protected static final String TARGETPAGE_XSD = "target-page";
	
	protected static final String TARGETPAGE = "targetPage";

	protected static final String TARGETPAGECONTROLLER = "targetPageController";

	protected static final String PAGECLASS = "pageClass";

	protected final static String EVENTHANDLER = "eventHandler";

	protected final static String EVENT_HANDLER = "event-handler";

	protected final static String EVENT_HANDLER_REF = "event-handler-ref";

	static {
		defaultClasses.put(DATACONTROLCLASS, DataControl.class);
		defaultClasses.put(PAGECLASS, PageController.class);
		defaultClasses.put(DAOKEYNAME, IPresentationDao.class);
		defaultClasses.put(TRANSITIONCLASS, TransitionController.class);

		defaults.put(QUERYBUILDER, "defaultQueryBuilder");
		defaults.put(PERSISTANCEMANAGER, "presentationPersistence");
		
	}

	/**
	 * @param element
	 * @return
	 */
	protected static ManagedList getAttributeCollectionFromChilds(Element element, String childElement, String attribute) {
		ManagedList list = new ManagedList();
		NodeList childNodes = element.getChildNodes();
		if (childNodes == null)
			return list;
		for (int i = 0; i < childNodes.getLength(); i++) {
			Node node = childNodes.item(i);
			if (node.getNodeType() == Node.ELEMENT_NODE) {
				String localName = node.getLocalName();
				if (childElement.equals(localName)) {
					list.add(node.getAttributes().getNamedItem(attribute).getNodeValue());
				}
			}
		}
		return list;
	}

	protected static Class<? extends Object> getBeanClass(String beanClass) {
		Class<? extends Object> clazz = null;
		try {
			clazz = ClassUtils.forName(beanClass);
		} catch (Throwable th) {
			throw new BeanDefinitionStoreException("Bean Class Name incorrect" + th);
		}
		return clazz;
	}

	protected static Class<? extends Object> getBeanClassFromChildOrDefault(Element element, String childElementName, String attribute,
			String defaultKey) {
		Class<? extends Object> clazz = null;
		try {
			Element childElement = DomUtils.getChildElementByTagName(element, childElementName);
			if (childElement != null)
				clazz = ClassUtils.forName(childElement.getAttribute(attribute));
			else
				clazz = defaultClasses.get(defaultKey);
		} catch (Throwable th) {
			throw new BeanDefinitionStoreException("Bean Class Name incorrect" + th);
		}
		return clazz;
	}

	/**
	 * @param mpvs
	 * @param propertyNameToSet
	 * @param dataControlElement
	 * @param defaultwhereclause2
	 * @param clause2
	 */
	protected static void addProprtyOfChild(MutablePropertyValues mpvs, Element element, String childElementName, String attribute,
			String propertyNameToSet) {
		addProprtyOfChild(mpvs, element, childElementName, attribute, propertyNameToSet, false);
	}

	/**
	 * @param mpvs
	 * @param propertyNameToSet
	 * @param dataControlElement
	 * @param defaultwhereclause2
	 * @param clause2
	 */
	protected static void addProprtyOfChild(MutablePropertyValues mpvs, Element element, String childElementName, String attribute,
			String propertyNameToSet, boolean runtimeReference) {
		if (element == null || DomUtils.getChildElementByTagName(element, childElementName) == null)
			return;
		if (runtimeReference)
			mpvs.addPropertyValue(propertyNameToSet, new RuntimeBeanReference(DomUtils.getChildElementByTagName(element, childElementName)
					.getAttribute(attribute)));
		else
			mpvs.addPropertyValue(propertyNameToSet, DomUtils.getChildElementByTagName(element, childElementName).getAttribute(attribute));
	}

	protected static void addProprtyFromChildOrDefault(MutablePropertyValues mpvs, Element element, String childElementName, String attribute,
			String defaultKey) {
		addProprtyFromChildOrDefault(mpvs, element, childElementName, attribute, defaultKey, false);
	}

	/**
	 * @param mpvs
	 * @param runtimeReference
	 * @param dataControlElement
	 * @param defaultwhereclause2
	 * @param clause2
	 */
	protected static void addProprtyFromChildOrDefault(MutablePropertyValues mpvs, Element element, String childElementName, String attribute,
			String defaultKey, boolean runtimeReference) {
		if (element == null || DomUtils.getChildElementByTagName(element, childElementName) == null) {
			if (runtimeReference)
				mpvs.addPropertyValue(defaultKey, new RuntimeBeanReference(defaults.get(defaultKey)));
			else
				mpvs.addPropertyValue(defaultKey, defaults.get(defaultKey));
			return;
		}
		if (runtimeReference)
			mpvs.addPropertyValue(defaultKey, new RuntimeBeanReference(DomUtils.getChildElementByTagName(element, childElementName).getAttribute(
					attribute)));
		else
			mpvs.addPropertyValue(defaultKey, DomUtils.getChildElementByTagName(element, childElementName).getAttribute(attribute));
	}

	protected static String getAttributeOrDefault(Element element, String attribute, String defaultKey) {
		if (element == null)
			return defaults.get(defaultKey);
		String value = element.getAttribute(attribute);
		return value != null ? value : defaults.get(defaultKey);
	}

	public void addEventHandlerDefinitionIfAny(Element element, ParserContext parserContext, MutablePropertyValues mpvs, String scope) {
		if (!StringUtils.isEmpty(element.getAttribute(EVENT_HANDLER_REF)))
			mpvs.addPropertyValue(EVENTHANDLER, new RuntimeBeanReference(element.getAttribute(EVENT_HANDLER_REF)));
		if (!StringUtils.isEmpty(element.getAttribute(EVENT_HANDLER))) {
			String eventhandlerBeanName = getEventHandlerBeanName(element, "EVENT_HANDLER");
			parserContext.getRegistry().registerBeanDefinition(eventhandlerBeanName, createEventHandlerBeanDefinition(element,scope));
			mpvs.addPropertyValue(EVENTHANDLER, new RuntimeBeanReference(eventhandlerBeanName));
		}
	}

	protected String getEventHandlerBeanName(Element element, String postFix) {
		String eventHandlerClassName = element.getAttribute(EVENT_HANDLER);
		if (eventHandlerClassName == null)
			return null;
		return element.getAttribute(NAME) + postFix;
	}

	protected BeanDefinition createEventHandlerBeanDefinition(Element element, String scope) {
		RootBeanDefinition eventHandlerDefinition = new RootBeanDefinition(getBeanClass(element.getAttribute(EVENT_HANDLER)));
		eventHandlerDefinition.setScope(scope);
		return eventHandlerDefinition;
	}
}
