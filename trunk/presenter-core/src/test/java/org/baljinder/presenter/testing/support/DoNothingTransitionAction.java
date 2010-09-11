package org.baljinder.presenter.testing.support;
import org.baljinder.presenter.dataacess.ITransitionAction;

/**
 * 
 */

/**
 * @author Baljinder Randhawa
 *
 */
public class DoNothingTransitionAction implements ITransitionAction {

	/* (non-Javadoc)
	 * @see org.baljinder.presenter.jsf.ui.dataacess.ITransitionAction#performTransitionAction(org.baljinder.presenter.jsf.ui.dataacess.ITransitionAction.TransitionContext)
	 */
	public ActionResult performTransitionAction(TransitionContext transitionContext) {
		return ActionResult.DONOTHING;
	}

}
