package com.csipl.hrms.dto.payroll.dashboard;

import java.math.BigDecimal;

public class EmployeeLifeCycleData {
	private BigDecimal value;

	String color;

	public EmployeeLifeCycleData() {
		super();
	}

	public BigDecimal getValue() {
		return value;
	}

	public void setValue(BigDecimal value) {
		this.value = value;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

}
