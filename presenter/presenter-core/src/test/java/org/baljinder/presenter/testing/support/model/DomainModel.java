package org.baljinder.presenter.testing.support.model;


public class DomainModel {

	private Integer testId;
	
	private String name ;

	public Integer getTestId() {
		return testId;
	}

	public void setTestId(Integer testId) {
		this.testId = testId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "DomainModel [testId=" + testId + ", name=" + name + "]";
	}
}
