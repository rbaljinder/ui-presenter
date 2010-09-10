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

		public IDataController getSourceDataControl() {
			return getTransitionController().getSourceDataControl();
		}

		public IDataController getTargetDataControl() {
			return getTransitionController().getTargetDataControl();
		}

		public IDataController getDataControlOfTargetPage(String dataControllerName) {
			return getTransitionController().getTargetPageController().getDataControl(dataControllerName);
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
