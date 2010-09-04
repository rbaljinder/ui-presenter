package model;

import org.baljinder.presenter.datamodel.config.ValidValueModel;

@ValidValueModel(Key="transactionState",Desc="description")
public class ValidTransactionState {

	private String transactionState ;

	private String description ;
	
	public String getTransactionState() {
		return transactionState;
	}

	public void setTransactionState(String transactionState) {
		this.transactionState = transactionState;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
