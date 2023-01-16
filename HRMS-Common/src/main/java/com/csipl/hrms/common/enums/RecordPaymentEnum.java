package com.csipl.hrms.common.enums;

public enum RecordPaymentEnum {

	Cheque(1, "Cheque"), Cash(2, "Cash"), Banking(3, "Banking"), Other(4, "Other");

	public int id;
	public String transactionMode;

	private RecordPaymentEnum(int id, String transactionMode) {
		this.id = id;
		this.transactionMode = transactionMode;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTransactionMode() {
		return transactionMode;
	}

	public void setTransactionMode(String transactionMode) {
		this.transactionMode = transactionMode;
	}

}
