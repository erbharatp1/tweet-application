package com.csipl.hrms.common.enums;

public enum GenderChartEnumNew {
//	caption(""), subCaption(""), numberPrefix(""), bgColor("#ffffff"), startingAngle("310"), showLegend("1"),
//	defaultCenterLabel("Total Employee: 100"), centerLabel("Total Employee: 100"), centerLabelBold("1"),
//	showTooltip("1"), decimals("0"), theme("fusion"), pieRadius("80"), labelDistance("50"),labelFontColor("#000000"),legendItemFontColor("#000000"),
//	smartLineColor("#282d32"), smartLineThickness("1"), smartLineAlpha("100"), isSmartLineSlanted("1"),feMaleColor("#0390ea"),maleColor("#0378be")
//	;
	bgColor("#ffffff"), startingAngle("310"), showLegend("1"), showTooltip("1"), decimals("0"), theme("fusion"),
	pieRadius("80"), labelDistance("50"), showPercentValues("0"), showPercentInToolTip("0"), formatNumber("1"),
	formatNumberScale("1"), forceNumberScale("0"), numberScaleUnit("0"), smartLineColor("#000000"),
	labelFontColor("#000000"), showLabels("0"), legendItemFontColor("#000000"),feMaleColor("#0390ea"),maleColor("#0378be");

	
	
	  
	public String pieChartValue;

	GenderChartEnumNew(String pieChartValue) {

		this.pieChartValue = pieChartValue;
	}

	public String getPieChartValue() {
		return pieChartValue;
	}

	public void setPieChartValue(String pieChartValue) {
		this.pieChartValue = pieChartValue;
	}

}
