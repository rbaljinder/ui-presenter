
package org.baljinder.presenter.jsf.util;
import java.text.MessageFormat;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.baljinder.presenter.dataacess.IPresentationMetaDataProvider;


public class MessageBean 
{

	private IPresentationMetaDataProvider  presentationMetaDataProvider ;
	
	private  static MessageBean messageBean = getInstance();
	
	public static MessageBean getInstance(){
		if(messageBean == null){
			messageBean = new MessageBean();
		}
		return messageBean ;
	}
	
	public void addErrorMessage(String code)
	{
		addMessage(FacesMessage.SEVERITY_ERROR, code, null);
	}

	public void addInfoMessage(String code)
	{
		addMessage(FacesMessage.SEVERITY_INFO, code, null);
	}

	public void addWarningMessage(String code)
	{
		addMessage(FacesMessage.SEVERITY_WARN, code, null);
	}

	public void addErrorMessage(String code, Object[] params)
	{
		addMessage(FacesMessage.SEVERITY_ERROR, code, params);
	}

	public void addInfoMessage(String code, Object[] params)
	{
		addMessage(FacesMessage.SEVERITY_INFO, code, params);
	}

	public void addWarningMessage(String code, Object[] params)
	{
		addMessage(FacesMessage.SEVERITY_WARN, code, params);
	}

	public void addMessage(FacesMessage.Severity severity, String code, Object[] params)
	{
		String localizedMessage = getLocalizedMessage(code, params);
		FacesMessage facesMessage = new FacesMessage(severity, "", localizedMessage);
		FacesContext.getCurrentInstance().addMessage(null, facesMessage);
	}

	public String getLocalizedMessage(String code, Object[] params)
	{
		String message = presentationMetaDataProvider.getMessage(code,params);  
		return message != null ? MessageFormat.format(message, params) : message;

	}

	public IPresentationMetaDataProvider getPresentationMetaDataProvider() {
		return presentationMetaDataProvider;
	}

	public void setPresentationMetaDataProvider(IPresentationMetaDataProvider presentationMetaDataProvider) {
		this.presentationMetaDataProvider = presentationMetaDataProvider;
	}
}
