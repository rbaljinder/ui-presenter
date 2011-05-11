package org.baljinder.presenter.dataacess.extension;

import org.springframework.jdbc.core.JdbcTemplate;

public interface SupportsJDBCTemplating {

	public void setJdbcTemplate(JdbcTemplate template) ;
	
	public JdbcTemplate getJdbcTemplate();
	
}
