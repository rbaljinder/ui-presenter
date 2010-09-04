package org.baljinder.presenter.jsf.util;

import java.util.List;

import javax.faces.component.EditableValueHolder;
import javax.faces.component.UIComponent;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;

public class FacesContextUtil
{
	private FacesContext _context;

	private ScopeUtil _scopeUtil;
	
	private static ThreadLocal<FacesContextUtil> _instance = new ThreadLocal<FacesContextUtil>();

	private FacesContextUtil()
	{
	}
	
	private void setContext(FacesContext context)
	{
		_context = context;
	}

	public static FacesContextUtil getInstance()
	{
		return getInstance(FacesContext.getCurrentInstance());
	}

	public static FacesContextUtil getInstance(FacesContext context)
	{
		FacesContextUtil result = _instance.get();
		if (result == null)
		{
			result = new FacesContextUtil();
			_instance.set(result);
		}
		result.setContext(context);
		return result;
	}

	public FacesContext getFacesContext()
	{
		return _context;
	}

	public ValueBinding createValueBinding(final String expression)
	{
		return _context.getApplication().createValueBinding(expression);
	}

	public Object resolveExpression(final String expression)
	{
		Object result = createValueBinding(expression).getValue(_context);
		return result;
	}

	public Object getManagedBean(final String beanName)
	{
		Object requestedObject = _context.getApplication().getVariableResolver().resolveVariable(_context, beanName);
		return requestedObject;
	}
	
	public ScopeUtil getScopeUtil()
	{
		if (_scopeUtil == null)
		{
			_scopeUtil = new ScopeUtil(this);
		}
		return _scopeUtil; 
	}
	
	
	public void clearSubmittedValuesInComponentTree(String componentName) 
	 {
		FacesContext fc = FacesContext.getCurrentInstance();
		UIViewRoot view = fc.getViewRoot();
		UIComponent refreshComponent =  view.findComponent(componentName) ;
		if(refreshComponent!=null)
			clearChildren(refreshComponent.getChildren()) ;
	 }
	 
	 private void clearChildren(List<UIComponent> componentList)
		{
			for(UIComponent component: componentList)
			{
				if(component.getChildCount()>0)
					clearChildren(component.getChildren());//recursion
				if(component instanceof EditableValueHolder)
				{
					//this will force the values of these components to be read from the model.
					//this is required for components in subforms which need to 
					//be refreshed when other subforms are submitted on a page
					((EditableValueHolder)component).setSubmittedValue(null);
					
				}
			}	
		}

}
