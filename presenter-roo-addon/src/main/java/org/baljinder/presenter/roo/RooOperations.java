package org.baljinder.presenter.roo;

import org.springframework.roo.model.JavaType;

/**
 * Interface of commands that are available via the Roo shell.
 *
 * @since 1.1.0-M1
 */
public interface RooOperations {

	boolean isCommandAvailable();

	void annotateType(JavaType type);
	
	void setup();
}