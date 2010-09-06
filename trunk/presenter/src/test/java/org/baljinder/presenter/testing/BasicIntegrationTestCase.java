package org.baljinder.presenter.testing;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public abstract class BasicIntegrationTestCase extends BasicDatabaseTestCase{

	public static String DEFAULT_SPRING_CONFIG_FILE = "configuration.xml";
	
	private ApplicationContext ac;
	
	@Override
	protected void setUp() throws Exception {
		ac = new ClassPathXmlApplicationContext(getConfigFiles());
		super.setUp();
	}
	
	protected ApplicationContext getApplicationContext() {
		return ac;
	} 
	
	public String[] getConfigFiles() {
		return new String[]{DEFAULT_SPRING_CONFIG_FILE};
	}
	
}
