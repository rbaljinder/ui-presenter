package org.baljinder.presenter.dataacess;

import junit.framework.TestCase;

import org.baljinder.presenter.testing.support.model.TestTable;

public class QueryBuilderTest extends TestCase {

	public void testBasicDataControl() {
		IDataControl basicDataControl = DataControlBuilder.getBasicDataControl("basicDataControl",
				DataControlBuilder.getModelListForClasses(TestTable.class));
		assertTrue(basicDataControl.getQuery().equals(" from TestTable testTable "));
		assertTrue(basicDataControl.getCountQuery().equals(" Select count(*)  from TestTable testTable "));
	}

}
