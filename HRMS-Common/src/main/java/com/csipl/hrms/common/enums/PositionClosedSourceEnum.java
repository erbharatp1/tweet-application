package com.csipl.hrms.common.enums;

public enum PositionClosedSourceEnum {

	theme("fusion"),
	xAxisName("Source of profile"),
    yAxisName("No. of source"),
    lineThickness("2"),
    divlineAlpha("100"),
    divlineColor("#999999"),
    divlineThickness("1"),
    divLineIsDashed("1"),
    divLineDashLen("1"),
    divLineGapLen("1"),
    showXAxisLine("1"),
    xAxisLineThickness("1"),
  //Anchor Cosmatics
    anchorRadius("6"),
    anchorBorderThickness("2"),
    anchorBorderColor("#cc3333"),
    anchorBgColor("#ff9900");
	public String pieChartValue ;
	
	PositionClosedSourceEnum (String pieChartValue  ) {
		
		this.pieChartValue = pieChartValue;
	}



	public String getPieChartValue() {
		return pieChartValue;
	}



	public void setPieChartValue(String pieChartValue) {
		this.pieChartValue = pieChartValue;
	}
	
}
