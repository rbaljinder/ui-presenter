/**
 * 
 */
package org.baljinder.presenter.datamodel.config;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.baljinder.presenter.datamodel.config.Config.Event;



/**
 * @author Baljinder Randhawa
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface DataModel {
	
	String[] upperCaseFields() default "";
	
	String[] lowerCaseFields() default "";
	
	Class<? extends Object> replacement() default Object.class;
	
	Rule[] rules()  default @Rule(action="",events=Event.INSERTING);
	
	RuleExpression[] ruleExpressions() default @RuleExpression(expression = "", event = Event.FILLER);

	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.TYPE)
	public static @interface Rule {

		Event[] events();

		String action();
		
		String message() default "";
	}

	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.FIELD)
	public static @interface RuleExpression {

		Event event();

		String expression();

		String message() default "";
	}
}

