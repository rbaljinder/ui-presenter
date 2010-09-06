package org.baljinder.presenter.testing;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import junit.framework.TestCase;

public class SpringContextTestCase extends TestCase {

	public static String DEFAULT_SPRING_CONFIG_FILE = "configuration.xml";
	
	private ApplicationContext ac;
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		ac = new ClassPathXmlApplicationContext(getConfigFiles());
	}
	
	protected ApplicationContext getApplicationContext() {
		return ac;
	} 
	
	public String[] getConfigFiles() {
		return new String[]{DEFAULT_SPRING_CONFIG_FILE};
	}
}
