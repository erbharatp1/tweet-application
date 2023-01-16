package com.csipl.hrms.dto.report;

public class DepartmentData {

	String label;
	String value;
	String color;

	public DepartmentData(String label, String value) {

		this.label = label;
		this.value = value;

	}
	

	/**
	 * @param label
	 * @param value
	 * @param color
	 */
	public DepartmentData(String label, String value, String color) {
		this.label = label;
		this.value = value;
		this.color = color;
	}


	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

}
