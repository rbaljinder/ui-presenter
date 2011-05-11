/**
 * 
 */
package org.baljinder.presenter.dataacess.util;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import org.baljinder.presenter.datamodel.DataModelMetadataProvider;
import org.baljinder.presenter.datamodel.config.Config.Case;
import org.baljinder.presenter.dataacess.IDataController;
import org.baljinder.presenter.dataacess.ModelFieldMapping;
import com.google.common.base.Joiner;
import com.google.common.collect.Lists;

/**
 * @author Baljinder Randhawa
 * 
 */
public class FilterBuilder implements SQLConstants {

	private BasicQueryBuilder queryBuilder; // why ????

	/**
	 * 
	 */
	public FilterBuilder(BasicQueryBuilder queryBuilder) {
		this.queryBuilder = queryBuilder;
	}

	/**
	 * @param dataControl
	 */
	public String buildFilteringCriteria(IDataController dataControl) {
		Map<String, ModelFieldMapping> filterMap = dataControl.getFilterObjectMap();
		String criteria = "";
		List<String> critriaList = Lists.newArrayList();
		List<ModelFieldMapping> filterRanges = Lists.newArrayList();
		for (ModelFieldMapping modelFieldMapping : filterMap.values()) {
			if (!StringUtils.isEmpty(modelFieldMapping.getValueAsString())) {
				if (modelFieldMapping.isPartOfRange())
					filterRanges.add(modelFieldMapping);
				else
					critriaList.add(STARTEXP + getClause(dataControl, modelFieldMapping) + ENDEXP);
			}
		}
		critriaList.addAll(handleRangeFilters(dataControl, filterRanges));
		criteria = Joiner.on(AND).skipNulls().join(critriaList);
		return StringUtils.isEmpty(criteria) ? null : criteria;
	}

	private List<String> handleRangeFilters(IDataController dataControl, List<ModelFieldMapping> filterRanges) {
		Map<String, ModelFieldMapping> filterMap = dataControl.getFilterObjectMap();
		List<String> critriaList = Lists.newArrayList();
		List<ModelFieldMapping> alreadyMapped = Lists.newArrayList();
		for (ModelFieldMapping modelFieldMapping : filterRanges) {
			if (alreadyMapped.contains(modelFieldMapping))
				continue;
			if (modelFieldMapping.getValueAsString() == null) {
				alreadyMapped.add(modelFieldMapping);
				continue;
			}
			ModelFieldMapping startPoint = null;
			ModelFieldMapping endPoint = null;
			if (ModelFieldMapping.TO.equals(modelFieldMapping.whichPartOfRange())) {
				startPoint = filterMap.get(modelFieldMapping.getModelName() + DOT + modelFieldMapping.getFieldName() + ModelFieldMapping.FROM);
				if (startPoint != null && startPoint.getValueAsString() == null) {
					alreadyMapped.add(startPoint);
					startPoint = null; // dont care abt that
				}
				endPoint = modelFieldMapping;
			}
			if (ModelFieldMapping.FROM.equals(modelFieldMapping.whichPartOfRange())) {
				startPoint = modelFieldMapping;
				endPoint = filterMap.get(modelFieldMapping.getModelName() + DOT + modelFieldMapping.getFieldName() + ModelFieldMapping.TO);
				if (endPoint != null && endPoint.getValueAsString() == null) {
					alreadyMapped.add(endPoint);
					endPoint = null; // dont care abt that
				}
			}
			if ((startPoint == null || endPoint == null)) { // could not find the other part
				critriaList.add(STARTEXP + getClause(dataControl, modelFieldMapping) + ENDEXP);
				alreadyMapped.add(modelFieldMapping);
			} else {
				alreadyMapped.add(startPoint);
				alreadyMapped.add(endPoint);
				if (modelFieldMapping.getFieldClass().isAssignableFrom(Date.class)) {
					critriaList.add(handleDate(startPoint, endPoint));
				} else {
					String leftPartOperator = getOperator(startPoint.getValueAsString()).equals(LIKE) ? LIKE : GTEQUALS;
					String rightPartOperator = getOperator(endPoint.getValueAsString()).equals(LIKE) ? LIKE : LTEQUALS;
					String leftValue = modelFieldMapping.getModelName() + DOT + modelFieldMapping.getFieldName();
					String startPointValue = doCoversionsIfRequired(startPoint);
					String endPointValue = doCoversionsIfRequired(endPoint);
					critriaList.add(STARTEXP + leftValue + leftPartOperator + QT + startPointValue + QT + AND + leftValue + rightPartOperator + QT
							+ endPointValue + QT + ENDEXP);
				}

			}
		}
		return critriaList;
	}

	/**
	 * @param dataControl
	 * @param modelFieldMapping
	 * @return
	 */
	private String getClause(IDataController dataControl, ModelFieldMapping modelFieldMapping) {
		if (modelFieldMapping.getFieldClass().isAssignableFrom(Date.class))
			return handleDate(modelFieldMapping, null);
		String leftValue = modelFieldMapping.getModelName() + DOT + modelFieldMapping.getFieldName();
		String fieldValue = doCoversionsIfRequired(modelFieldMapping);
		return leftValue + getOperator(modelFieldMapping.getValue().toString()) + QT + fieldValue + QT;
	}

	/**
	 * @param value
	 * @return
	 */
	// TODO: may be use reg expression to verify that the value has any of the wild cards
	private String getOperator(String value) {
		for (String wildCard : WILD_CARDS) {
			if (StringUtils.contains(value, wildCard))
				return LIKE;
		}
		return EQUALS;
	}

	// TODO: how to handle if a class is inherited from another
	private String doCoversionsIfRequired(ModelFieldMapping modelFieldMapping) {
		Class<?> modelClass = modelFieldMapping.getModelClass();
		String fieldName = modelFieldMapping.getFieldName();
		String fieldValue = modelFieldMapping.getValueAsString();
		DataModelMetadataProvider metaDataProvider = queryBuilder.getMetaDataProvider();
		if (metaDataProvider.doesFieldMetaDataExists(modelClass, fieldName)) {
			Case fieldCase = metaDataProvider.getFieldMetaData(modelClass, fieldName).getFieldCase();
			if (fieldCase != null) {
				switch (fieldCase) {
				case LOWER:
					fieldValue = StringUtils.lowerCase(fieldValue);
					break;
				case UPPER:
					fieldValue = StringUtils.upperCase(fieldValue);
					break;
				}
			}
		}
		fieldValue = StringUtils.replace(fieldValue, ANY_MATCH, ORACLE_ANY_MATCH);
		return fieldValue.trim();
	}

	/**
	 * @param dataControl
	 * @param modelFieldMapping
	 */
	// TODO: what if the date format changes
	private String handleDate(ModelFieldMapping fromModelFieldMapping, ModelFieldMapping toModelFieldMapping) {
		String fromLeftValue = fromModelFieldMapping.getModelName() + DOT + fromModelFieldMapping.getFieldName();
		String fromValue = fromModelFieldMapping.getValueAsString();
		StringBuffer dateClause = new StringBuffer();
		dateClause.append(fromLeftValue + GTEQUALS);
		dateClause.append(TO_DATE_FUNCTION + STARTEXP + QT + fromValue + SPACE + START_TIME_OF_DAY + QT + COMMA + QT + DATE_FORMAT + QT + ENDEXP);
		if (toModelFieldMapping == null) {
			dateClause.append(AND);
			dateClause.append(fromLeftValue + LTEQUALS);
			dateClause.append(TO_DATE_FUNCTION + STARTEXP + QT + fromValue + SPACE + END_TIME_OF_DAY + QT + COMMA + QT + DATE_FORMAT + QT + ENDEXP);
		} else {
			String toLeftValue = fromModelFieldMapping.getModelName() + DOT + fromModelFieldMapping.getFieldName();
			String toValue = fromModelFieldMapping.getValueAsString();
			dateClause.append(AND);
			dateClause.append(toLeftValue + LTEQUALS);
			dateClause.append(TO_DATE_FUNCTION + STARTEXP + QT + toValue + SPACE + END_TIME_OF_DAY + QT + COMMA + QT + DATE_FORMAT + QT + ENDEXP);
		}
		return dateClause.toString();
	}
}
