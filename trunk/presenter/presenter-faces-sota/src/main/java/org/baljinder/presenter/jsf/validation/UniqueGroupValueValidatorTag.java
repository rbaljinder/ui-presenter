package org.baljinder.presenter.jsf.validation;

import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;
import javax.faces.validator.Validator;
import javax.faces.webapp.UIComponentTag;
import javax.servlet.jsp.JspException;

import org.apache.myfaces.validator.ValidatorBaseTag;

/**
 * 
 * @author Baljinder Randhawa
 * 
 */
public class UniqueGroupValueValidatorTag extends ValidatorBaseTag {

	private static final long serialVersionUID = -1L;

	private String _groupComponentIds = null;

	private String message  = "Can_Not_Assign_Same_Value";
	
	public void setGroupComponentIds(String string) {
		this._groupComponentIds = string;
	}

	public String getGroupComponentIds() {
		return _groupComponentIds ;
	}

	protected Validator createValidator() throws JspException {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		setValidatorId("org.baljinder.presenter.jsf.ui.validation.UniqueGroupValidator");
		UniqueGroupValidator validator = (UniqueGroupValidator) super.createValidator();
		if (this._groupComponentIds != null) {
			if (UIComponentTag.isValueReference(this._groupComponentIds)) {
				ValueBinding vb = facesContext.getApplication().createValueBinding(this._groupComponentIds);
				validator.setGroupComponentIds(vb.getValue(facesContext).toString());
			} else {
				validator.setGroupComponentIds(this._groupComponentIds);
				validator.setMessage(getMessage());
			}
		}

		return validator;
	}

	public void release() {
		super.release();
		this._groupComponentIds = null;
	}
	

	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}

}
