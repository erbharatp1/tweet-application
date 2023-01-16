
package com.csipl.hrms.dto.esipf.dashboard;

/**
 * @author admin
 *
 */
public class ChartNew {
	private String caption;
	private String subCaption;
	private String numberPrefix;
	private String bgColor;
	private String startingAngle;
	private String showLegend;
	private String defaultCenterLabel;
	private String centerLabel;
	private String centerLabelBold;
	private String showTooltip;
	private String decimals;
	private String theme;
	private String pieRadius;
	private String labelDistance;

	/**
	 * @param caption
	 * @param subCaption
	 * @param numberPrefix
	 * @param bgColor
	 * @param startingAngle
	 * @param showLegend
	 * @param defaultCenterLabel
	 * @param centerLabel
	 * @param centerLabelBold
	 * @param showTooltip
	 * @param decimals
	 * @param theme
	 * @param pieRadius
	 * @param labelDistance
	 * 
	 * 
	 * 
	 * "caption": "",
        "subCaption": "",
        "numberPrefix": "$",
        "bgColor": "#ffffff",
        "startingAngle": "310",
        "showLegend": "0",
        "defaultCenterLabel": "",
        "centerLabel": "Revenue from $label: $value",
        "centerLabelBold": "1",
        "showTooltip": "0",
        "decimals": "0",
        "theme": "fusion",
        "pieRadius": "80",
        "labelDistance": "50"
	 */
	public ChartNew(String caption, String subCaption, String numberPrefix, String bgColor, String startingAngle,
			String showLegend, String defaultCenterLabel, String centerLabel, String centerLabelBold,
			String showTooltip, String decimals, String theme, String pieRadius, String labelDistance) {
		super();
		this.caption = caption;
		this.subCaption = subCaption;
		this.numberPrefix = numberPrefix;
		this.bgColor = bgColor;
		this.startingAngle = startingAngle;
		this.showLegend = showLegend;
		this.defaultCenterLabel = defaultCenterLabel;
		this.centerLabel = centerLabel;
		this.centerLabelBold = centerLabelBold;
		this.showTooltip = showTooltip;
		this.decimals = decimals;
		this.theme = theme;
		this.pieRadius = pieRadius;
		this.labelDistance = labelDistance;
	}

	public String getCaption() {
		return caption;
	}

	public void setCaption(String caption) {
		this.caption = caption;
	}

	public String getSubCaption() {
		return subCaption;
	}

	public void setSubCaption(String subCaption) {
		this.subCaption = subCaption;
	}

	public String getNumberPrefix() {
		return numberPrefix;
	}

	public void setNumberPrefix(String numberPrefix) {
		this.numberPrefix = numberPrefix;
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

	public String getDefaultCenterLabel() {
		return defaultCenterLabel;
	}

	public void setDefaultCenterLabel(String defaultCenterLabel) {
		this.defaultCenterLabel = defaultCenterLabel;
	}

	public String getCenterLabel() {
		return centerLabel;
	}

	public void setCenterLabel(String centerLabel) {
		this.centerLabel = centerLabel;
	}

	public String getCenterLabelBold() {
		return centerLabelBold;
	}

	public void setCenterLabelBold(String centerLabelBold) {
		this.centerLabelBold = centerLabelBold;
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

}
