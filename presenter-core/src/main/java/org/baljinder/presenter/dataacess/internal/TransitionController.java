/**
 * 
 */
package org.baljinder.presenter.dataacess.internal;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import org.baljinder.presenter.dataacess.IDataController;
import org.baljinder.presenter.dataacess.IEventHandler;
import org.baljinder.presenter.dataacess.IPageController;
import org.baljinder.presenter.dataacess.ITransitionAction;
import org.baljinder.presenter.dataacess.ITransitionController;
import org.baljinder.presenter.dataacess.ITransitionAction.ActionResult;
import org.baljinder.presenter.dataacess.ITransitionAction.TransitionContext;
import org.baljinder.presenter.dataacess.internal.support.EmptyEventHandler;
import org.baljinder.presenter.dataacess.util.BasicQueryBuilder;
import org.baljinder.presenter.util.ReflectionUtils;
import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.collect.Lists;

/**
 * @author Baljinder Randhawa
 * 
 */
public class TransitionController implements ITransitionController {

	private String name;

	private TransitionMode transitionMode = defaultTransitionMode;

	private String outcome;

	private IPageController targetPageController;

	private IDataController sourceDataControl;

	private IDataController targetDataControl;

	private ITransitionAction transitionAction;

	private List<String> transitionCriteria = Lists.newArrayList();

	private IEventHandler eventHandler = EmptyEventHandler.doNothingHandler;

	public static final String GET = "get";

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.baljinder.presenter.jsf.ui.dataacess.ITransitionController#performTransition()
	 */
	public String performTransition() {
		eventHandler.beforeTransition(this);
		initlizeTarget();
		ActionResult actionResult = performTransitionAction();
		if (ActionResult.CANCEL.equals(actionResult))
			return NO_TRANSITION;
		if (getTransitionCriteria() != null && !getTransitionCriteria().isEmpty())
			setCriteriaOnTargetDataControl();
		eventHandler.afterTransition(this);
		return getOutcome();
	}

	private void initlizeTarget() {
		getTargetPageController().initialize();
		if (!targetPageController.getCached()) {
			for (IDataController dataController : getTargetPageController().getDataConrolList()) {
				switch (getTransitionMode()) {
				case QUERY:
					dataController.setDataFetched(true);
					break;
				case CREATE:
					dataController.setDataFetched(true);
					dataController.create();
					break;
				case LOAD:
					dataController.setDataFetched(false);
					break;
				default:
					break;
				}
				dataController.clearFilterValues();
				dataController.setPageCursor(0);
				dataController.setPropagetedClause(null);
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.baljinder.presenter.jsf.ui.dataacess.ITransitionController#selectAndPerformTransition()
	 */
	public String selectAndPerformTransition() {
		eventHandler.beforeTransition(this);
		initlizeTarget();
		getSourceDataControl().selectData();
		ActionResult actionResult = performTransitionAction();
		if (ActionResult.CANCEL.equals(actionResult))
			return NO_TRANSITION;
		setCriteriaOnTargetDataControl();
		eventHandler.afterTransition(this);
		return getOutcome();
	}

	/**
	 * @return
	 * 
	 */
	private ActionResult performTransitionAction() {
		ITransitionAction transitionAction = getTransitionAction();
		if (transitionAction == null)
			return ActionResult.CONTINUE;
		TransitionContext transitionContext = new TransitionContext();
		transitionContext.setTransitionController(this);
		return transitionAction.performTransitionAction(transitionContext);
	}

	/**
	 * 
	 */
	private void setCriteriaOnTargetDataControl() {
		final Map<String, Object> currentlySelectedElement = getSourceDataControl().getCurrentElement();
		List<String> transitionCriterion = getTransitionCriteria();
		List<String> clauses = Lists.transform(transitionCriterion, new Function<String, String>() {
			public String apply(String attribute) {
				String[] participatingAttribtue = StringUtils.split(attribute, "=");
				String sourceAttribute = participatingAttribtue[1].trim();
				// verify and throw exception if invalid stuff.. or maybe just verify at the construction level only once
				String[] splittedParentAttribute = StringUtils.split(sourceAttribute, ".");
				Object parentObject = currentlySelectedElement.get(splittedParentAttribute[0]);
				String parentFieldName = splittedParentAttribute[1];
				Object sourceAttibuteValue = ReflectionUtils.getFieldValue(parentObject, parentFieldName);
				if (sourceAttibuteValue == null)
					throw new RuntimeException("Source Property can not be null");
				return participatingAttribtue[0] + BasicQueryBuilder.EQUALS + BasicQueryBuilder.QT + sourceAttibuteValue
						+ BasicQueryBuilder.QT;
			}
		});
		clauses = Lists.transform(clauses, BasicQueryBuilder.buildClause);
		getTargetDataControl().setPropagetedClause(Joiner.on(BasicQueryBuilder.AND).skipNulls().join(clauses));

	}

	/**
	 * @return the outcome
	 */
	public String getOutcome() {
		return !StringUtils.isEmpty(outcome) ? outcome : getTargetPageController().getName();
	}

	/**
	 * @param outcome the outcome to set
	 */
	public void setOutcome(String outcome) {
		this.outcome = outcome;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.baljinder.presenter.jsf.ui.dataacess.ITransitionController#getTransitionMode()
	 */
	public TransitionMode getTransitionMode() {
		return transitionMode;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.baljinder.presenter.jsf.ui.dataacess.ITransitionController#setTransitionMode(java.lang.String)
	 */
	public void setTransitionMode(TransitionMode transitionMode) {
		this.transitionMode = transitionMode;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.baljinder.presenter.jsf.ui.dataacess.ITransitionController#getSourceDataControl()
	 */
	public IDataController getSourceDataControl() {
		return sourceDataControl;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.baljinder.presenter.jsf.ui.dataacess.ITransitionController#getTargetDataControl()
	 */
	public IDataController getTargetDataControl() {
		return targetDataControl;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.baljinder.presenter.jsf.ui.dataacess.ITransitionController#setSourceDataControl(org.baljinder.presenter.jsf.ui.dataacess.IDataController
	 * )
	 */
	public void setSourceDataControl(IDataController sourceDataControl) {
		this.sourceDataControl = sourceDataControl;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.baljinder.presenter.jsf.ui.dataacess.ITransitionController#setTargetDataControl(org.baljinder.presenter.jsf.ui.dataacess.IDataController
	 * )
	 */
	public void setTargetDataControl(IDataController targetDataControl) {
		this.targetDataControl = targetDataControl;
	}

	/**
	 * @return the targetPageController
	 */
	public IPageController getTargetPageController() {
		return targetPageController;
	}

	/**
	 * @param targetPageController the targetPageController to set
	 */
	public void setTargetPageController(IPageController targetPageController) {
		this.targetPageController = targetPageController;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.baljinder.presenter.jsf.ui.dataacess.ITransitionController#getName()
	 */
	public String getName() {
		return name;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.baljinder.presenter.jsf.ui.dataacess.ITransitionController#setName(java.lang.String)
	 */
	public void setName(String name) {
		this.name = name;

	}

	/**
	 * @return the transitionCriterion
	 */
	public List<String> getTransitionCriteria() {
		return transitionCriteria;
	}

	/**
	 * @param transitionCriterion the transitionCriterion to set
	 */
	public void setTransitionCriteria(List<String> transitionCriterion) {
		this.transitionCriteria = transitionCriterion;
	}

	/**
	 * @return the transitionAction
	 */
	public ITransitionAction getTransitionAction() {
		return transitionAction;
	}

	/**
	 * @param transitionAction the transitionAction to set
	 */
	public void setTransitionAction(ITransitionAction transitionAction) {
		this.transitionAction = transitionAction;
	}

	/**
	 * @return the eventHandler
	 */
	public IEventHandler getEventHandler() {
		return eventHandler;
	}

	/**
	 * @param eventHandler the eventHandler to set
	 */
	public void setEventHandler(IEventHandler eventHandler) {
		this.eventHandler = eventHandler;
	}

}
