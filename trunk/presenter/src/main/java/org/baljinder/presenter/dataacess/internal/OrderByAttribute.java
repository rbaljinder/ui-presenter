/**
 * 
 */
package org.baljinder.presenter.dataacess.internal;

/**
 * @author Baljinder Randhawa
 *
 */
public class OrderByAttribute {

	public static final String DESC = "DESC" ;
	
	public static final String ASC ="ASC" ;
	
	private String model ;
	
	private String attribute ;
	
	private String order ;
	
	
	public OrderByAttribute(String model, String attribute) {
		this(model,attribute,DESC);
	}
	
	/**
	 * 
	 */
	public OrderByAttribute(String model, String attribute, String order) {
		this.model = model ;
		this.attribute = attribute;
		this.order = order ;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append(model+"."+attribute+" "+order);;
		return buffer.toString();
	}

	/**
	 * @return the model
	 */
	public String getModel() {
		return model;
	}

	/**
	 * @param model the model to set
	 */
	public void setModel(String model) {
		this.model = model;
	}

	/**
	 * @return the attribute
	 */
	public String getAttribute() {
		return attribute;
	}

	/**
	 * @param attribute the attribute to set
	 */
	public void setAttribute(String attribute) {
		this.attribute = attribute;
	}

	/**
	 * @return the order
	 */
	public String getOrder() {
		return order;
	}

	/**
	 * @param order the order to set
	 */
	public void setOrder(String order) {
		this.order = order;
	}

	/**
	 * 
	 */
	public void flipOrder() {
		if(ASC.equals(getOrder()))
			setOrder(DESC);
		else
			setOrder(ASC);
	}
}
