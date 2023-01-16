package com.csipl.hrms.common.enums;

public enum DesignationChartEnum {

	/**
	 * theme, baseFontColor, showBorder, bgColor, canvasBgColor, showCanvasBorder,
	 * showHoverEffect, baseFontSize, showValues, setAdaptiveYMin, labelFontColor,
	 * formatNumber, yFormatNumber, xFormatNumber, formatNumberScale,
	 * showYAxisvalue, placeValuesInside;
	 * 
	 */
	theme("fusion"), baseFontColor("#888888"), showBorder("0"), bgColor("#ffffff"), canvasBgColor("#ffffff"),
	showCanvasBorder("0"), showHoverEffect("1"), baseFontSize("11"), showValues("1"), setAdaptiveYMin("1"),
	labelFontColor("#888888"), formatNumber("0"), yFormatNumber("0"), xFormatNumber("0"), formatNumberScale("0"),
	showYAxisvalue("0"), placeValuesInside("0");
	public String pieChartValue;

	DesignationChartEnum(String pieChartValue) {

		this.pieChartValue = pieChartValue;
	}

	public String getPieChartValue() {
		return pieChartValue;
	}

	public void setPieChartValue(String pieChartValue) {
		this.pieChartValue = pieChartValue;
	}

}
