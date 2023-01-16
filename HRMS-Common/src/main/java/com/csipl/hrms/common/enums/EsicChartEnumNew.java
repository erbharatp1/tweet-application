package com.csipl.hrms.common.enums;

public enum EsicChartEnumNew {
	caption(""), subCaption(""), numberPrefix(""), bgColor("#ffffff"), startingAngle("310"), showLegend("1"),
	defaultCenterLabel(""), centerLabel(""), centerLabelBold("1"), showTooltip("1"), decimals("0"), theme("fusion"),
	pieRadius("80"), labelDistance("50"), smartLineThickness("1"), smartLineAlpha("100"), isSmartLineSlanted("1"),
	showPercentValues("0"), showPercentInToolTip("0"), formatNumber("1"), formatNumberScale("1"), forceNumberScale("0"),
	numberScaleUnit("0"), smartLineColor("#000000"), labelFontColor("#000000"),employeeColor("#575bde"),employeerColor("#2095f2");

	/**
	 * bgColor": "#ffffff", "startingAngle": "310", "showLegend": "0", //
	 * "defaultCenterLabel": "Total revenue: $64.08K", // "centerLabel": "Revenue
	 * from $label: $value", "centerLabelBold": "1", "showTooltip": "0", "decimals":
	 * "0", "theme": "fusion", "pieRadius": "80", "labelDistance": "50",
	 * "showPercentValues":"0", "showPercentInToolTip":"0", "formatNumber":"1",
	 * "formatNumberScale":"1", "forceNumberScale":"0", "numberScaleUnit":"0",
	 * "smartLineColor":"#000000", "labelFontColor":"#000000"
	 */

	public String pieChartValue;

	EsicChartEnumNew(String pieChartValue) {

		this.pieChartValue = pieChartValue;
	}

	public String getPieChartValue() {
		return pieChartValue;
	}

	public void setPieChartValue(String pieChartValue) {
		this.pieChartValue = pieChartValue;
	}

}
