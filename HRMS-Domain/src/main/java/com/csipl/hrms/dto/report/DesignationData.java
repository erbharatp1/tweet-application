package com.csipl.hrms.dto.report;

public class DesignationData {
	
	 String label;
     String value;
   String color;
     
   
	public DesignationData(String label, String value) {
	
		this.label = label;
		this.value = value;
		
	}
	

	/**
	 * @param label
	 * @param value
	 * @param color
	 */
	public DesignationData(String label, String value, String color) {
		super();
		this.label = label;
		this.value = value;
		this.color = color;
	}


	public String getColor() {
		return color;
	}


	public void setColor(String color) {
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
	
     
     

}
