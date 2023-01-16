package com.csipl.hrms.common.enums;

public enum TimeAndAttendanceEnum {
	theme("fusion"),
	showvalues("1"),
	numVisiblePlot("6"),
	scrollheight("5"),
	flatScrollBars("1"),
	scrollShowButtons("0"),
	scrollColor("#cccccc"),
	showHoverEffect("1"), 
	numbersuffix("%"),
	showYAxisvalue("0"),
	paletteColors("#575bde"),
	labelFontColor("#000000");
	
	

	
	
//	  "theme": "fusion",
//  "showvalues": "1",
//  "numVisiblePlot": "6",
//  "scrollheight": "5",
//  "flatScrollBars": "1",
//  "scrollShowButtons": "0",
//  "scrollColor": "#cccccc",
//  "showHoverEffect": "1",
//  "numbersuffix": "%",
//  "showYAxisValue":'0',
//  "paletteColors": '#575bde',
//  "labelFontColor":"#000000"
	public String pieChartValue;

	TimeAndAttendanceEnum(String pieChartValue) {

		this.pieChartValue = pieChartValue;
	}

	public String getPieChartValue() {
		return pieChartValue;
	}

	public void setPieChartValue(String pieChartValue) {
		this.pieChartValue = pieChartValue;
	}

}
