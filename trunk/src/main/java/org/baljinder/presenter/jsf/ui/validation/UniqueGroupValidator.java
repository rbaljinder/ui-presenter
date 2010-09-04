/**
 * 
 */
package org.baljinder.presenter.jsf.ui.validation;

import java.util.Arrays;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;

import org.apache.commons.lang.StringUtils;
import org.apache.myfaces.validator.ValidatorBase;

/**
 * @author Baljinder Randhawa
 * 
 */

//TODO: get messages the way myfaces validator are doing
public class UniqueGroupValidator extends ValidatorBase {

	private String groupComponentIds;

	private List<String> componentsToCompare;

	private String message = "Can_Not_Assign_Same_Value";

	private static final String SPLITTER_IDENTIFIER = ",";

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.faces.validator.Validator#validate(javax.faces.context.FacesContext,
	 * javax.faces.component.UIComponent, java.lang.Object)
	 */
	public void validate(FacesContext facesContext, UIComponent uiComponent, Object value) throws ValidatorException {
		if (facesContext == null)
			throw new NullPointerException("facesContext");
		if (uiComponent == null)
			throw new NullPointerException("uiComponent");
		if (value == null) {
			return;
		}
		for (String componentId : componentsToCompare) {
			UIComponent component = facesContext.getViewRoot().findComponent(componentId);
			if (component != null) {
				Object otherValue = component.getAttributes().get("submittedValue");
				if (otherValue != null)
					if (StringUtils.equals(uiComponent.getAttributes().get("submittedValue").toString(), otherValue.toString())) {
						FacesMessage message = new FacesMessage();
						String messageToDisplay = getMessageFromPresentationProvider();
						message.setDetail(messageToDisplay);
						message.setSummary(messageToDisplay);
						message.setSeverity(FacesMessage.SEVERITY_ERROR);
						throw new ValidatorException(message);
					}
			}
		}
	}

	/**
	 * @return
	 */
	//TODO: fix me
	protected String getMessageFromPresentationProvider() {
		// TODO Auto-generated method stub
		/*PresentationMetadataProvider presentationMetadataProvider = (PresentationMetadataProvider) FacesContextUtil.getInstance().getManagedBean(
				"presentationMetadataProvider");
		return presentationMetadataProvider.getMessage().get(getMessage());*/
		return null;
	}

	/**
	 * @return the groupComponentIds
	 */
	public String getGroupComponentIds() {
		return groupComponentIds;
	}

	/**
	 * @param groupComponentIds
	 *            the groupComponentIds to set
	 */
	public void setGroupComponentIds(String groupComponentIds) {
		this.groupComponentIds = groupComponentIds;
		if (groupComponentIds != null)
			componentsToCompare = Arrays.asList(StringUtils.split(groupComponentIds, SPLITTER_IDENTIFIER));
	}

	/**
	 * @return the componentsToCompare
	 */
	public List<String> getComponentsToCompare() {
		return componentsToCompare;
	}

	/**
	 * @param componentsToCompare
	 *            the componentsToCompare to set
	 */
	public void setComponentsToCompare(List<String> componentsToCompare) {
		this.componentsToCompare = componentsToCompare;
	}

	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param message
	 *            the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}

}
