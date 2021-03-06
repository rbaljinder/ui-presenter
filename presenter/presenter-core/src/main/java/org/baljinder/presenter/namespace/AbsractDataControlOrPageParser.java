/**
 * 
 */
package org.baljinder.presenter.namespace;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.baljinder.presenter.dataacess.IDataController;
import org.baljinder.presenter.dataacess.IDataController.Operation;
import org.baljinder.presenter.dataacess.internal.GenericPresentationDao;
import org.baljinder.presenter.dataacess.internal.extension.ValidValueDataControl;
import org.baljinder.presenter.dataacess.internal.extension.ValidValueGenericDaoImpl;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.config.RuntimeBeanReference;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionReaderUtils;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.ManagedList;
import org.springframework.beans.factory.support.ManagedMap;
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
		Element actionOutcomeElement = DomUtils.getChildElementByTagName(dataControlElement, ACTION_OUTCOME_XSD);
		if(actionOutcomeElement != null){
			ManagedMap<Operation, String> actionOutomes = new ManagedMap<Operation, String>();
			if(StringUtils.isNotBlank(actionOutcomeElement.getAttribute(ACTION_OUTCOME_CREATE_XSD)))
				actionOutomes.put(Operation.CREATE, actionOutcomeElement.getAttribute(ACTION_OUTCOME_CREATE_XSD));
			if(StringUtils.isNotBlank(actionOutcomeElement.getAttribute(ACTION_OUTCOME_DELETE_XSD)))
				actionOutomes.put(Operation.DELETE, actionOutcomeElement.getAttribute(ACTION_OUTCOME_DELETE_XSD));
			if(StringUtils.isNotBlank(actionOutcomeElement.getAttribute(ACTION_OUTCOME_UPDATE_XSD)))
				actionOutomes.put(Operation.UPDATE, actionOutcomeElement.getAttribute(ACTION_OUTCOME_UPDATE_XSD));
			if(StringUtils.isNotBlank(actionOutcomeElement.getAttribute(ACTION_OUTCOME_SELECT_XSD)))
				actionOutomes.put(Operation.SELECT, actionOutcomeElement.getAttribute(ACTION_OUTCOME_SELECT_XSD));
			if(StringUtils.isNotBlank(actionOutcomeElement.getAttribute(ACTION_OUTCOME_SORT_XSD)))
				actionOutomes.put(Operation.SORT, actionOutcomeElement.getAttribute(ACTION_OUTCOME_SORT_XSD));
			if(StringUtils.isNotBlank(actionOutcomeElement.getAttribute(ACTION_OUTCOME_SAVE_XSD)))
				actionOutomes.put(Operation.SAVE, actionOutcomeElement.getAttribute(ACTION_OUTCOME_SAVE_XSD));
			mpvs.addPropertyValue(ACTION_OUTCOME,actionOutomes);
		}
		addPropertyToBeanDefinition(getChildElementCollection(dataControlElement, PROPERTY),mpvs);
		List<Node> dataControlTransitions = getChildElementCollection(dataControlElement, TRANSITION_XSD);
		for (Node aDataControlTransition : dataControlTransitions) {
			Element dataControlTransition = (Element) aDataControlTransition ; 
			String transitionBeanName = dataControlTransition.getAttributes().getNamedItem(NAME).getNodeValue();
			AbstractBeanDefinition transitionDef = createTransitionBeanDefintionForOtherElements(dataControlTransition, parserContext);
			 dataControlTransition.getAttribute(NAME);
			BeanDefinitionRegistry registry = parserContext.getRegistry();
			MutablePropertyValues mpvsOfTransition = transitionDef.getPropertyValues();
			mpvsOfTransition.add(SOURCEDATACONTROL, new RuntimeBeanReference(dataControlElement.getAttribute(NAME)));
			transitionDef.setPropertyValues(mpvsOfTransition);
			registry.registerBeanDefinition(transitionBeanName, transitionDef);
		}
		dataControlDefinition.setPropertyValues(mpvs);
		return dataControlDefinition;
	}

	protected AbstractBeanDefinition createTransitionBeanDefintionForOtherElements(Element dataControlTransition,ParserContext parserContext){
		AbstractBeanDefinition transitionDef = createTransitionBeanDefinition(dataControlTransition, parserContext);
		BeanDefinitionRegistry registry = parserContext.getRegistry();
		String transitionBeanName = dataControlTransition.getAttribute(NAME);
		if (StringUtils.isEmpty(transitionBeanName)) {
			transitionBeanName = BeanDefinitionReaderUtils.generateBeanName(transitionDef, registry);
		}
		String transitionActionBeanName = getTransitionActionBeanName(dataControlTransition);
		if(transitionActionBeanName != null)
			registry.registerBeanDefinition(transitionActionBeanName, createTransitionActionBeanDefinition(dataControlTransition));
		return transitionDef ;
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
