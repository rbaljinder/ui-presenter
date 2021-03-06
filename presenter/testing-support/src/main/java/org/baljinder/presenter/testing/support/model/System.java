/**
 * 
 */
package org.baljinder.presenter.testing.support.model;


/**
 * @author Baljinder Randhawa
 * 
 */
public class System {

	private Long systemId;

	private String systemName;

	private Long clientId;

	private String name;

	public Long getSystemId() {
		return systemId;
	}

	public void setSystemId(Long systemId) {
		this.systemId = systemId;
	}

	public Long getClientId() {
		return clientId;
	}

	public void setClientId(Long clientId) {
		this.clientId = clientId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "System [systemId=" + systemId + ", clientId=" + clientId + ", name=" + name + "]";
	}

	public String getSystemName() {
		return systemName;
	}

	public void setSystemName(String systemName) {
		this.systemName = systemName;
	}

}
