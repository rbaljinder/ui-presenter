package org.baljinder.presenter.testing.support.model;

import org.baljinder.presenter.datamodel.config.Config.*;
import org.baljinder.presenter.datamodel.config.DataModel;
import org.baljinder.presenter.datamodel.config.DataModel.Rule;
import org.baljinder.presenter.datamodel.config.DataModel.RuleExpression;
import org.baljinder.presenter.datamodel.config.Field;
import org.baljinder.presenter.datamodel.config.Field.FieldRule;
import org.baljinder.presenter.datamodel.config.Field.FieldRuleExpression;

/**
 * 
 */

/**
 * @author Baljinder Randhawa
 * 
 */
@DataModel( rules = { 
				@Rule(action = "none", events= {Event.INSERTING,Event.DELETING})},
			ruleExpressions={
				@RuleExpression(event=Event.DELETING,expression="")
			}	
)
public class ToAnnotate {

	@Field( Case = Case.UPPER, value = "nothing")
	private String test;

	@Field( validation = ValidTransactionState.class,
			ruleExpressions = {
				@FieldRuleExpression(event= Event.INSERTING, expression = ""),
				@FieldRuleExpression(event= Event.DELETING, expression = "")},
			rules = {
				@FieldRule(action="",event=Event.DELETING)
			},
			Case =Case.UPPER
	)
	private String transactionState;

	/**
	 * @return the test
	 */
	public String getTest() {
		return test;
	}

	/**
	 * @param test
	 *            the test to set
	 */
	public void setTest(String test) {
		this.test = test;
	}

	public String getTransactionState() {
		return transactionState;
	}

	public void setTransactionState(String transactionState) {
		this.transactionState = transactionState;
	}
}
