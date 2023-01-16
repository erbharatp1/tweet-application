package com.csipl.hrms.common.enums;

public enum LeaveTakenByLeaveTypeEnum {

	theme("fusion"),
	showValues("1"),
	numVisiblePlot("6"),
	scrollheight("5"),
	flatScrollBars("1"),
	scrollShowButtons("0"),
	showHoverEffect("1"),
	showYAxisValue("0"),
	labelFontColor("#000000");
	public String pieChartValue ;
	
	LeaveTakenByLeaveTypeEnum (String pieChartValue  ) {
		
		this.pieChartValue = pieChartValue;
	}



	public String getPieChartValue() {
		return pieChartValue;
	}



	public void setPieChartValue(String pieChartValue) {
		this.pieChartValue = pieChartValue;
	}
	
}
