package com.csipl.hrms.dto.report;

public class AttendanceChart {
	private String  bgColor, startingAngle, showLegend, showTooltip,decimals,theme,pieRadius,labelDistance,showPercentValues,showPercentInToolTip,formatNumber,formatNumberScale,
	forceNumberScale, numberScaleUnit,smartLineColor ,labelFontColor,showLabels,legendItemFontColor;

	/**
	 * @param bgColor
	 * @param startingAngle
	 * @param showLegend
	 * @param showTooltip
	 * @param decimals
	 * @param theme
	 * @param pieRadius
	 * @param labelDistance
	 * @param showPercentValues
	 * @param showPercentInToolTip
	 * @param formatNumber
	 * @param formatNumberScale
	 * @param forceNumberScale
	 * @param numberScaleUnit
	 * @param smartLineColor
	 * @param labelFontColor
	 * @param showLabels
	 * @param legendItemFontColor
	 */
	public AttendanceChart(String bgColor, String startingAngle, String showLegend, String showTooltip, String decimals,
			String theme, String pieRadius, String labelDistance, String showPercentValues, String showPercentInToolTip,
			String formatNumber, String formatNumberScale, String forceNumberScale, String numberScaleUnit,
			String smartLineColor, String labelFontColor, String showLabels, String legendItemFontColor) {
		super();
		this.bgColor = bgColor;
		this.startingAngle = startingAngle;
		this.showLegend = showLegend;
		this.showTooltip = showTooltip;
		this.decimals = decimals;
		this.theme = theme;
		this.pieRadius = pieRadius;
		this.labelDistance = labelDistance;
		this.showPercentValues = showPercentValues;
		this.showPercentInToolTip = showPercentInToolTip;
		this.formatNumber = formatNumber;
		this.formatNumberScale = formatNumberScale;
		this.forceNumberScale = forceNumberScale;
		this.numberScaleUnit = numberScaleUnit;
		this.smartLineColor = smartLineColor;
		this.labelFontColor = labelFontColor;
		this.showLabels = showLabels;
		this.legendItemFontColor = legendItemFontColor;
	}

	public String getBgColor() {
		return bgColor;
	}

	public void setBgColor(String bgColor) {
		this.bgColor = bgColor;
	}

	public String getStartingAngle() {
		return startingAngle;
	}

	public void setStartingAngle(String startingAngle) {
		this.startingAngle = startingAngle;
	}

	public String getShowLegend() {
		return showLegend;
	}

	public void setShowLegend(String showLegend) {
		this.showLegend = showLegend;
	}

	public String getShowTooltip() {
		return showTooltip;
	}

	public void setShowTooltip(String showTooltip) {
		this.showTooltip = showTooltip;
	}

	public String getDecimals() {
		return decimals;
	}

	public void setDecimals(String decimals) {
		this.decimals = decimals;
	}

	public String getTheme() {
		return theme;
	}

	public void setTheme(String theme) {
		this.theme = theme;
	}

	public String getPieRadius() {
		return pieRadius;
	}

	public void setPieRadius(String pieRadius) {
		this.pieRadius = pieRadius;
	}

	public String getLabelDistance() {
		return labelDistance;
	}

	public void setLabelDistance(String labelDistance) {
		this.labelDistance = labelDistance;
	}

	public String getShowPercentValues() {
		return showPercentValues;
	}

	public void setShowPercentValues(String showPercentValues) {
		this.showPercentValues = showPercentValues;
	}

	public String getShowPercentInToolTip() {
		return showPercentInToolTip;
	}

	public void setShowPercentInToolTip(String showPercentInToolTip) {
		this.showPercentInToolTip = showPercentInToolTip;
	}

	public String getFormatNumber() {
		return formatNumber;
	}

	public void setFormatNumber(String formatNumber) {
		this.formatNumber = formatNumber;
	}

	public String getFormatNumberScale() {
		return formatNumberScale;
	}

	public void setFormatNumberScale(String formatNumberScale) {
		this.formatNumberScale = formatNumberScale;
	}

	public String getForceNumberScale() {
		return forceNumberScale;
	}

	public void setForceNumberScale(String forceNumberScale) {
		this.forceNumberScale = forceNumberScale;
	}

	public String getNumberScaleUnit() {
		return numberScaleUnit;
	}

	public void setNumberScaleUnit(String numberScaleUnit) {
		this.numberScaleUnit = numberScaleUnit;
	}

	public String getSmartLineColor() {
		return smartLineColor;
	}

	public void setSmartLineColor(String smartLineColor) {
		this.smartLineColor = smartLineColor;
	}

	public String getLabelFontColor() {
		return labelFontColor;
	}

	public void setLabelFontColor(String labelFontColor) {
		this.labelFontColor = labelFontColor;
	}

	public String getShowLabels() {
		return showLabels;
	}

	public void setShowLabels(String showLabels) {
		this.showLabels = showLabels;
	}

	public String getLegendItemFontColor() {
		return legendItemFontColor;
	}

	public void setLegendItemFontColor(String legendItemFontColor) {
		this.legendItemFontColor = legendItemFontColor;
	}
			 

}
