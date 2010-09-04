package org.baljinder.presenter.util;

import java.util.Map;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

public class ScopeUtil
{
	private FacesContextUtil _contextUtil;

	ScopeUtil(FacesContextUtil contextUtil)
	{
		_contextUtil = contextUtil;
	}

	@SuppressWarnings("unchecked")
	public Object storeOnSession(final String key, final Object object)
	{
		Map sessionMap = getContext().getExternalContext().getSessionMap();
		return sessionMap.put(key, object);
	}

	@SuppressWarnings("unchecked")
	public Object getFromSession(final String key)
	{
		Map sessionMap = getContext().getExternalContext().getSessionMap();
		return sessionMap.get(key);
	}

	@SuppressWarnings("unchecked")
	public Object storeOnRequest(final String key, final Object object)
	{
		Map requestMap = getContext().getExternalContext().getRequestMap();
		return requestMap.put(key, object);
	}

	@SuppressWarnings("unchecked")
	public Object getFromRequest(final String key)
	{
		Map requestMap = getContext().getExternalContext().getRequestMap();
		return requestMap.get(key);
	}

	@SuppressWarnings("unchecked")
	public Object removeFromSession(final String key)
	{
		Map sessionMap = getContext().getExternalContext().getSessionMap();
		return sessionMap.remove(key);
	}

	public void invalidateSession()
	{
		if(getContext() != null && getContext().getExternalContext() != null)
		{
			if(getContext().getExternalContext().getSession(false) != null)
			{
				HttpSession session = (HttpSession) getContext().getExternalContext().getSession(false);
				if (session != null)
				{
					session.invalidate();
				}
			}
		}
	}

	private FacesContext getContext()
	{
		return _contextUtil.getFacesContext();
	}
}
