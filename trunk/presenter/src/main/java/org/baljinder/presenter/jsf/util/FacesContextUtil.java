package org.baljinder.presenter.jsf.util;

import java.util.List;

import javax.faces.component.EditableValueHolder;
import javax.faces.component.UIComponent;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;

//inspired from somewhere
public class FacesContextUtil {
	private FacesContext _context;

	private FacesScopeUtil _scopeUtil;

	private static ThreadLocal<FacesContextUtil> _instance = new ThreadLocal<FacesContextUtil>();

	private FacesContextUtil() {
	}

	private void setContext(FacesContext context) {
		_context = context;
	}

	public static FacesContextUtil getInstance() {
		return getInstance(FacesContext.getCurrentInstance());
	}

	public static FacesContextUtil getInstance(FacesContext context) {
		FacesContextUtil result = _instance.get();
		if (result == null) {
			result = new FacesContextUtil();
			_instance.set(result);
		}
		result.setContext(context);
		return result;
	}

	public FacesContext getFacesContext() {
		return _context;
	}

	public ValueBinding createValueBinding(final String expression) {
		return _context.getApplication().createValueBinding(expression);
	}

	public Object resolveExpression(final String expression) {
		return  createValueBinding(expression).getValue(_context);
	}

	public Object getManagedBean(final String beanName) {
		return _context.getApplication().getVariableResolver().resolveVariable(_context, beanName);
	}

	public FacesScopeUtil getScopeUtil() {
		if (_scopeUtil == null) {
			_scopeUtil = new FacesScopeUtil(this);
		}
		return _scopeUtil;
	}

	@SuppressWarnings("unchecked")
	public void clearSubmittedValuesInComponentTree(String componentName) {
		FacesContext fc = FacesContext.getCurrentInstance();
		UIViewRoot view = fc.getViewRoot();
		UIComponent refreshComponent = view.findComponent(componentName);
		if (refreshComponent != null)
			clearChildren(refreshComponent.getChildren());
	}

	@SuppressWarnings("unchecked")
	private void clearChildren(List<UIComponent> componentList) {
		for (UIComponent component : componentList) {
			if (component.getChildCount() > 0)
				clearChildren(component.getChildren());
			if (component instanceof EditableValueHolder)
				((EditableValueHolder) component).setSubmittedValue(null);
		}
	}

}
