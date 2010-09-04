/**
 * 
 */
package org.baljinder.presenter.datamodel.config;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.baljinder.presenter.datamodel.config.Config.Case;
import org.baljinder.presenter.datamodel.config.Config.Event;

/**
 * @author Baljinder Randhawa
 * 
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Field {

	Case Case() default Case.USER;

	Class<? extends Object> validation() default Object.class;

	String validatingMethod() default "";

	String value() default "";

	FieldRule[] rules() default @FieldRule(action = "", event = Event.FILLER);

	FieldRuleExpression[] ruleExpressions() default @FieldRuleExpression(expression = "", event = Event.FILLER);

	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.FIELD)
	public static @interface FieldRule {

		Event event();

		String action();

		String message() default "";
	}

	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.FIELD)
	public static @interface FieldRuleExpression {

		Event event();

		String expression();

		String message() default "";
	}

}
