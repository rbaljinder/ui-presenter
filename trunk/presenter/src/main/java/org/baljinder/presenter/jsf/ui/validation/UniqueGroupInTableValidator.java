/**
 * 
 */
package org.baljinder.presenter.jsf.ui.validation;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;

import org.apache.commons.lang.StringUtils;
import org.baljinder.presenter.jsf.util.FacesContextUtil;
import org.baljinder.presenter.jsf.util.TokenizedMessageBean;

import com.google.common.collect.Maps;

/**
 * TOTAL HACK
 * 
 * @author Baljinder Randhawa
 * 
 */

public class UniqueGroupInTableValidator extends UniqueGroupValidator {

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.faces.validator.Validator#validate(javax.faces.context.FacesContext,
	 * javax.faces.component.UIComponent, java.lang.Object)
	 */
	@SuppressWarnings("unchecked")
	public void validate(FacesContext facesContext, UIComponent uiComponent, Object value) throws ValidatorException {
		if (facesContext == null)
			throw new NullPointerException("facesContext");
		if (uiComponent == null)
			throw new NullPointerException("uiComponent");
		if (value == null) {
			return;
		}
		Map<String, String> valueMap = (Map<String, String>) FacesContextUtil.getInstance().getScopeUtil().getFromRequest(
				"UniqueGroupInTableValidatorInfo" + facesContext.getViewRoot().getViewId());
		if (valueMap == null) {
			valueMap = Maps.newHashMap();
			FacesContextUtil.getInstance().getScopeUtil().storeOnRequest("UniqueGroupInTableValidatorInfo" + facesContext.getViewRoot().getViewId(), valueMap);
		}
		if (uiComponent.getAttributes().get("submittedValue") != null)
			valueMap.put(uiComponent.getId(), uiComponent.getAttributes().get("submittedValue").toString());
		if (isTheLastFieldToValidateInRow(uiComponent.getId())) {
			FacesContextUtil.getInstance().getScopeUtil().storeOnRequest("UniqueGroupInTableValidatorInfo" + facesContext.getViewRoot().getViewId(), null);
			Boolean validationFaliedAlready = (Boolean) FacesContextUtil.getInstance().getScopeUtil().getFromRequest("validationFaliedAlready" + facesContext.getViewRoot().getViewId());
			if(validationFaliedAlready == null)
				validateInternal(valueMap,facesContext);
		}
	}

	/**
	 * @param valueMap
	 */
	private void validateInternal(Map<String, String> valueMap,FacesContext facesContext) {
		Collection<String> values = valueMap.values();
		for (String value : values) {
			if (Collections.frequency(values, value) > 1) {
				FacesMessage message = new FacesMessage();
				String messageToDisplay = getMessageFromPresentationProvider(); 
				message.setDetail(messageToDisplay);
				message.setSummary(messageToDisplay);
				message.setSeverity(FacesMessage.SEVERITY_ERROR);
				FacesContextUtil.getInstance().getScopeUtil().storeOnRequest("validationFaliedAlready" + facesContext.getViewRoot().getViewId(), true);
				TokenizedMessageBean.getInstance().addErrorMessage(getMessage()); 
				throw new ValidatorException(message);
			}
		}
	}

	public boolean isTheLastFieldToValidateInRow(String currentComponentId) {
		List<String> components = getComponentsToCompare();
		if (components != null && components.size() > 0) {
			if (StringUtils.equals(currentComponentId, components.get(components.size() - 1)))
				return true;
		}
		return false;
	}
}
