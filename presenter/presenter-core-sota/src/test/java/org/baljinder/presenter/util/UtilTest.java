package org.baljinder.presenter.util;

import org.baljinder.presenter.dataacess.util.Messages;

import junit.framework.TestCase;

public class UtilTest extends TestCase {

	public void testMessage(){
		String [] param = {"PARAM","YEAH"};
		assertTrue("Test PARAM replacement, YEAH it works".equals(Messages.resloveMessage("Test {0} replacement, {1} it works", param)));
	}
}
