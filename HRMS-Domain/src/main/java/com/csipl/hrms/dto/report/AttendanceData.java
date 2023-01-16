package com.csipl.hrms.dto.report;

public class AttendanceData {
	 String label;
     String value;
    String color;
 
 


	/**
	 * @param label
	 * @param value
	 * @param color
	 */
	public AttendanceData(String label, String value, String color) {
		super();
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

