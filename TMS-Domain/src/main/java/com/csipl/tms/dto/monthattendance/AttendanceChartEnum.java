package com.csipl.tms.dto.monthattendance;
public enum AttendanceChartEnum {

	bgColor("#ffffff"), startingAngle("310"), showLegend("1"), showTooltip("1"), decimals("1"), theme("fusion"),
	pieRadius("80"), labelDistance("50"), showPercentValues("0"), showPercentInToolTip("0"), formatNumber("1"),
	formatNumberScale("1"), forceNumberScale("0"), numberScaleUnit("0"), smartLineColor("#000000"),
	labelFontColor("#000000"), showLabels("0"), legendItemFontColor("#000000");

	public String pieChartValue;

	AttendanceChartEnum(String pieChartValue) {

		this.pieChartValue = pieChartValue;
	}

	public String getPieChartValue() {
		return pieChartValue;
	}

	public void setPieChartValue(String pieChartValue) {
		this.pieChartValue = pieChartValue;
	}

}

