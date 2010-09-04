/**
 * 
 */
package org.baljinder.presenter.dataacess;

/**
 * @author Baljinder Randhawa
 * 
 */
public interface ITransitionAction {

	public ActionResult performTransitionAction(TransitionContext transitionContext);

	public enum ActionResult {

		DONOTHING(0),CONTINUE(1),CANCEL(2);

		private int result;

		ActionResult(int result) {
			this.result = result;
		}

		public int result() {
			return result;
		}
	};

	public static class TransitionContext {

		private ITransitionController transitionController;

		public IDataControl getSourceDataControl() {
			return getTransitionController().getSourceDataControl();
		}

		public IDataControl getTargetDataControl() {
			return getTransitionController().getTargetDataControl();
		}

		public IDataControl getDataControlOfTargetPage(String dataControlName) {
			return getTransitionController().getTargetPageController().getDataControl(dataControlName);
		}

		public IPageController getTargetPageController() {
			return getTransitionController().getTargetPageController();
		}

		/**
		 * @return the transitionController
		 */
		public ITransitionController getTransitionController() {
			return transitionController;
		}

		/**
		 * @param transitionController
		 *            the transitionController to set
		 */
		public void setTransitionController(ITransitionController transitionController) {
			this.transitionController = transitionController;
		}

	}
}
