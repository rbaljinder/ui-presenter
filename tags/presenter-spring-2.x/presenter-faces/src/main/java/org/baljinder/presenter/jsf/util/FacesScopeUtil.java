package org.baljinder.presenter.jsf.util;


import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

public class FacesScopeUtil
{
	private FacesContextUtil _contextUtil;

	FacesScopeUtil(FacesContextUtil contextUtil)
	{
		_contextUtil = contextUtil;
	}

	@SuppressWarnings("unchecked")
	public Object storeOnSession(final String key, final Object object)
	{
		return getContext().getExternalContext().getSessionMap().put(key, object);
	}

	public Object getFromSession(final String key)
	{
		return getContext().getExternalContext().getSessionMap().get(key);
	}

	@SuppressWarnings("unchecked")
	public Object storeOnRequest(final String key, final Object object)
	{
		return getContext().getExternalContext().getRequestMap().put(key, object);
	}

	public Object getFromRequest(final String key)
	{
		return getContext().getExternalContext().getRequestMap().get(key);
	}

	public Object removeFromSession(final String key)
	{
		return getContext().getExternalContext().getSessionMap().remove(key);
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
