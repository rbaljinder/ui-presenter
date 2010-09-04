/**
 * 
 */
package org.baljinder.presenter.dataacess.util;

import java.util.List;

import com.google.common.collect.Lists;

/**
 * @author Baljinder Randhawa
 *
 */
public interface SQLConstants {
	
	public static final String AND = " and ";

	public static final String OR = " or ";

	public static final String WHERE = " where ";

	public static final String EQUALS = " = ";

	public static final String GTEQUALS = " >= ";

	public static final String LTEQUALS = " <= ";

	public static final String LIKE = " LIKE ";

	public static final String STARTEXP = " ( ";

	public static final String ENDEXP = " ) ";

	public static final String SPACE = " ";

	public static final String FROM = " from ";

	public static final String ORDER_BY = " order by ";

	public static final String LIKE_IDENTIFIER = "%";

	// TODO: change the names
	public static final String ANY_MATCH = "*";

	public static final String ORACLE_ANY_MATCH = "%";

	public static final List<String> WILD_CARDS = Lists.newArrayList("*", "?", "_");

	public static final String DOT = ".";

	public static final String QT = "'";

	public static final String COMMA = ",";

	public static final String SELECT_COUNT = " Select count(*) ";

	public static final String EMPTY_STRING = "";

	public static final String GET = "get";

	public static final String DATE_FORMAT = "mm/dd/yyyy HH24:MI:SS";

	public static final String START_TIME_OF_DAY = "00:00:00";

	public static final String END_TIME_OF_DAY = "23:59:59";

	public static final String TO_DATE_FUNCTION = "to_date";
}
