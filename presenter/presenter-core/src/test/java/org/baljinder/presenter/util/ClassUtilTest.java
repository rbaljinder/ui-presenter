package org.baljinder.presenter.util;
import junit.framework.TestCase;

import org.baljinder.presenter.testing.support.model.ToAnnotate;

/**
 * 
 */

/**
 * @author Baljinder Randhawa
 *
 */
public class ClassUtilTest extends TestCase {

	public void test(){
		ToAnnotate system = new ToAnnotate();
		system.setTest("ME");
		assertEquals(ReflectionUtils.getFieldValue(system,"test"),"ME");
		ReflectionUtils.setFieldValue(system,"test", "YOU");
		assertEquals(system.getTest(),"YOU");
		System.out.println(system.getTest());
		//System.out.println(ClassUtils.getClassesForPackage("net.sf.hibernate"));
	}
}
