package demo;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.baljinder.presenter.testing.support.BasicDatabaseTestCase;

public class LoadDataBase extends BasicDatabaseTestCase implements ServletContextListener {

	public void contextInitialized(ServletContextEvent arg0) {
		try {
			System.out.println("loading data base");
			setUp();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void contextDestroyed(ServletContextEvent arg0) {
		try {
			tearDown();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
