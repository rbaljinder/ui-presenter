package org.baljinder.presenter.dataacess.cvl;

public class CodedValueDefinition {

	private Class<? extends Object> className;

	private String keyColumn;

	private String descColumn;

	public Class<? extends Object> getClassName() {
		return className;
	}

	public void setClassName(Class<? extends Object> name) {
		this.className = name;
	}

	public String getKeyColumn() {
		return keyColumn;
	}

	public void setKeyColumn(String keyColumn) {
		this.keyColumn = keyColumn;
	}

	public String getDescColumn() {
		return descColumn;
	}

	public void setDescColumn(String descColumn) {
		this.descColumn = descColumn;
	}

}
