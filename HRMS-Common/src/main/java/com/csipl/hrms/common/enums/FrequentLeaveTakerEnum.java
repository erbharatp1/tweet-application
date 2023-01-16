package com.csipl.hrms.common.enums;

public enum FrequentLeaveTakerEnum {

	theme("fusion"),
	showValues("1"),
	numVisiblePlot("6"),
	scrollheight("5"),
	flatScrollBars("1"),
	scrollShowButtons("0"),
	scrollColor("#cccccc"),
	showHoverEffect("1"),
	showYAxisValue("0"),
	paletteColors("#575bde"),
	labelFontColor("#000000");
	public String pieChartValue ;
	
	FrequentLeaveTakerEnum (String pieChartValue  ) {
		
		this.pieChartValue = pieChartValue;
	}



	public String getPieChartValue() {
		return pieChartValue;
	}



	public void setPieChartValue(String pieChartValue) {
		this.pieChartValue = pieChartValue;
	}
}
