
package org.baljinder.presenter.testing;

import java.sql.Connection;
import java.sql.DriverManager;

import org.dbunit.DatabaseTestCase;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSet;
import org.dbunit.operation.DatabaseOperation;

public abstract class BasicDatabaseTestCase extends DatabaseTestCase{
	
	public static String DEFAULT_DATABASE_CONNECTION_URL = "jdbc:hsqldb:file:";
	
	public static String DEFAULT_DATABASE_NAME = "src/test/resources/test";
	
	public static String DEFAULT_USER = "sa";
	
	public static String DEFAULT_PASSWORD ="";


	public static String DEFAULT_SEED_DATA_FILE ="/database/seed-data.xml" ;
	
	@Override
	protected DatabaseOperation getSetUpOperation() throws Exception {
		return DatabaseOperation.CLEAN_INSERT ;
	}
	/*
	@Override
	protected DatabaseOperation getTearDownOperation() throws Exception {
		return DatabaseOperation.DELETE_ALL;
	}*/
	@Override
	protected IDatabaseConnection getConnection() throws Exception {
		Class.forName("org.hsqldb.jdbcDriver");
		Connection connection =  DriverManager.getConnection(getConnnectionUrl()+getDatabaseName(),getDatabaseUser(),getDatabasePassword());
		return new DatabaseConnection(connection);
	}
	
	protected String getDatabasePassword() {
		return DEFAULT_PASSWORD;
	}
	
	protected String getDatabaseUser() {
		return DEFAULT_USER;
	}
	
	protected String getDatabaseName() {
		return DEFAULT_DATABASE_NAME;
	}
	
	protected String getConnnectionUrl() {
		return DEFAULT_DATABASE_CONNECTION_URL;
	}
	
	@Override
	protected IDataSet getDataSet() throws Exception {
		return new FlatXmlDataSet(getClass().getResourceAsStream(getDataSeedFileName()));
	}

	public String getDataSeedFileName() {
		return DEFAULT_SEED_DATA_FILE ;
	}
	
	public void xxtestMe() throws Exception{
	  assertNotNull(getConnection());	
	}
}
 