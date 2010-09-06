/**
 * 
 */
package org.baljinder.presenter.dataacess.util;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.baljinder.presenter.dataacess.IDataControl;
import org.baljinder.presenter.dataacess.filter.Filter;
import org.baljinder.presenter.dataacess.internal.OrderByAttribute;
import org.baljinder.presenter.datamodel.DataModelMetadataProvider;
import org.baljinder.presenter.util.ReflectionUtils;
import org.baljinder.presenter.util.Utils;

import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

//TODO: Everything is in line with ORACLE
public class BasicQueryBuilder implements IQueryBuilder, SQLConstants {

	Map<String, Map<String, Filter>> filters = Maps.newHashMap();

	private DataModelMetadataProvider metaDataProvider = DataModelMetadataProvider.emptyInstance;

	private FilterBuilder filterBuilder = new FilterBuilder(this);

	// TODO: Check thread safety
	public static Function<String, String> buildClause = new Function<String, String>() {
		public String apply(String clause) {
			if (StringUtils.isEmpty(clause))
				return null;
			return STARTEXP + clause + ENDEXP;
		}
	};

	private String getFilter(String dataControlName, Class<?> entity) {
		if (filters.get(dataControlName) == null)
			return null;
		Filter filter = filters.get(dataControlName).get(entity.getName());
		if (filter != null)
			return Utils.getAlias(entity) + "." + filter.getFilterProprty();
		return null;
	}

	public String buildQuery(IDataControl dataControl) {
		List<String> resolvedFromList = Lists.transform(dataControl.getModelList(), new Function<Class<?>, String>() {
			public String apply(Class<?> modelClass) {
				return Utils.getShortName(modelClass) + SPACE + Utils.getAlias(modelClass) + SPACE;
			}
		});
		String queryString = FROM + Joiner.on(COMMA + SPACE).join(resolvedFromList);
		String whereClause = buildWhereClause(dataControl);
		String orderBy = buildOrderBy(dataControl);
		if (StringUtils.isEmpty(whereClause))
			return queryString + orderBy;
		else
			return queryString + WHERE + whereClause + orderBy;
	}

	public String buildWhereClause(final IDataControl dataControl) {
		String filterClause = Joiner.on(AND).skipNulls().join(Lists.transform(dataControl.getModelList(), new Function<Class<?>, String>() {
			public String apply(Class<?> modelClassName) {
				return getFilter(dataControl.getDataControlName(), modelClassName);
			}
		}));
		String filteringCriteria = filterBuilder.buildFilteringCriteria(dataControl);
		List<String> clauseList = Lists.transform(Lists.newArrayList(dataControl.getJoinCriteria(), filterClause, filteringCriteria, dataControl
				.getDefaultWhereClause(), dataControl.getPropagetedClasue(), getClauseFromParentIfApplicable(dataControl)), buildClause);
		String finalWhereClause = Joiner.on(AND).skipNulls().join(clauseList);
		if (StringUtils.isEmpty(finalWhereClause))
			return null;
		return STARTEXP + finalWhereClause + ENDEXP;
	}

	/**
	 * @param dataControl
	 * @return
	 */
	private String buildOrderBy(IDataControl dataControl) {
		List<OrderByAttribute> orderByList = dataControl.getOrderByList();
		if (orderByList.isEmpty())
			return EMPTY_STRING;
		return ORDER_BY + Joiner.on(COMMA + SPACE).join(orderByList);
	}

	public String getCountQuery(final IDataControl dataControl) {
		List<String> resolvedFromList = Lists.transform(dataControl.getModelList(), new Function<Class<?>, String>() {
			public String apply(Class<?> modelClass) {
				return Utils.getShortName(modelClass) + SPACE + Utils.getAlias(modelClass) + SPACE;
			}
		});
		String queryString = SELECT_COUNT + FROM + Joiner.on(COMMA + SPACE).join(resolvedFromList);
		String filterClause = Joiner.on(AND).skipNulls().join(Lists.transform(dataControl.getModelList(), new Function<Class<?>, String>() {
			public String apply(Class<?> modelClassName) {
				return getFilter(dataControl.getDataControlName(), modelClassName);
			}
		}));
		String filteringCriteria = filterBuilder.buildFilteringCriteria(dataControl);
		List<String> clauseList = Lists.transform(Lists.newArrayList(dataControl.getJoinCriteria(), filterClause, filteringCriteria, dataControl
				.getDefaultWhereClause(), dataControl.getPropagetedClasue(), getClauseFromParentIfApplicable(dataControl)), buildClause);
		String finalWhereClause = Joiner.on(AND).skipNulls().join(clauseList);
		String whereClause = EMPTY_STRING;
		if (StringUtils.isNotEmpty(finalWhereClause))
			whereClause = STARTEXP + finalWhereClause + ENDEXP;
		if (StringUtils.isEmpty(whereClause))
			return queryString;
		else
			return queryString + WHERE + whereClause;
	}

	/**
	 * TODO: move this method to may be some specialized class
	 * 
	 * @param dataControl
	 * @return
	 */
	public String getClauseFromParentIfApplicable(IDataControl dataControl) {
		String whereClauseWithParentProperty = "";
		final IDataControl parentDataControl = dataControl.getParentDataControl();
		List<String> parentChildRelation = dataControl.getParentChildRelation();
		if (dataControl.getParentDataControl() != null && parentChildRelation != null) {
			// Consider Only first row. Ideally in case of parent- child,the parent should have only one row selected
			// or the other possible thing is to consider multiple parent records .. how that can be implemented
			List<Map<String, Object>> data = parentDataControl.getData();
			if (data.size() > 0) {
				final Map<String, Object> parentRow = data.get(0);
				List<String> clause = Lists.transform(parentChildRelation, new Function<String, String>() {
					public String apply(String attribute) {
						// verify and throw exception if invalid stuff.. or maybe just verify at the construction level only once
						String[] participatingAttribtue = StringUtils.split(attribute, "=");
						String parentAttribute = participatingAttribtue[1].trim();
						String[] splittedParentAttribute = StringUtils.split(parentAttribute, ".");
						Object parentObject = parentRow.get(splittedParentAttribute[0]);
						String parentFieldName = splittedParentAttribute[1];
						Object parentAttibuteValue = ReflectionUtils.getFieldValue(parentObject, parentFieldName);
						if (parentAttibuteValue == null)
							throw new RuntimeException("Parent Property can not be null"); // create a Embeddable Data Control specific exception
						return participatingAttribtue[0] + EQUALS + QT + parentAttibuteValue + QT;
					}
				});
				clause = Lists.transform(clause, buildClause);
				whereClauseWithParentProperty = Joiner.on(AND).skipNulls().join(clause);
			}
		}
		return whereClauseWithParentProperty;
	}

	/**
	 * @return the filters
	 */
	public Map<String, Map<String, Filter>> getFilters() {
		return filters;
	}

	/**
	 * @param filters
	 *            the filters to set
	 */
	public void setFilters(Map<String, Map<String, Filter>> filters) {
		this.filters = filters;
	}

	/**
	 * @return the metaDataProvider
	 */
	public DataModelMetadataProvider getMetaDataProvider() {
		return metaDataProvider;
	}

	/**
	 * @param metaDataProvider
	 *            the metaDataProvider to set
	 */
	public void setMetaDataProvider(DataModelMetadataProvider metaDataProvider) {
		this.metaDataProvider = metaDataProvider;
	}

}
