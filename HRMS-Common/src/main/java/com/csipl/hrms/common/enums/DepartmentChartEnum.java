package com.csipl.hrms.common.enums;

public enum DepartmentChartEnum {
	caption(""), subCaption(""), xAxisName(""), yAxisName(""), theme("fusion"), paletteColors("#575bde"),
	labelDisplay("auto"), baseFontColor("888888"), baseFont("Helvetica Neue,Arial"), captionFontSize("14"),
	subcaptionFontSize("14"), subcaptionFontBold("0"), showBorder("0"), bgColor("#ffffff"), showShadow("0"),
	canvasBgColor("#ffffff"), showCanvasBorder("0"), showHoverEffect("1"), yAxisValueDecimals("2"), baseFontSize("14"),
	width("500"), height("500"), showValues("1"), setAdaptiveYMin("1"), decimalSeparator(","), thousandSeparator("."),
	forceDecimals("1"), forceYAxisValueDecimals("1"), forceXAxisValueDecimals("1"), thousandSeparatorPosition("2,3"),
	labelFontColor("888888"), formatNumber("0"), yFormatNumbe("0"), xFormatNumber("0"), formatNumberScale("0");

	public String pieChartValue;

	DepartmentChartEnum(String pieChartValue) {

		this.pieChartValue = pieChartValue;
	}

	public String getPieChartValue() {
		return pieChartValue;
	}

	public void setPieChartValue(String pieChartValue) {
		this.pieChartValue = pieChartValue;
	}

}
