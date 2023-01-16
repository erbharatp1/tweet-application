package com.csipl.tms.dto.common;

public class FrequentLeaveTakerChart {
private String
	   theme,
	   showvalues,
	   numVisiblePlot,
	   scrollheight,
	   flatScrollBars,
	   scrollShowButtons,
	   scrollColor,
	   showHoverEffect,
	   showYAxisValue,
	   paletteColors,
	   labelFontColor;

public FrequentLeaveTakerChart(String theme, String showvalues, String numVisiblePlot, String scrollheight,
		String flatScrollBars, String scrollShowButtons, String scrollColor, String showHoverEffect,
		String showYAxisValue, String paletteColors, String labelFontColor) {
	super();
	this.theme = theme;
	this.showvalues = showvalues;
	this.numVisiblePlot = numVisiblePlot;
	this.scrollheight = scrollheight;
	this.flatScrollBars = flatScrollBars;
	this.scrollShowButtons = scrollShowButtons;
	this.scrollColor = scrollColor;
	this.showHoverEffect = showHoverEffect;
	this.showYAxisValue = showYAxisValue;
	this.paletteColors = paletteColors;
	this.labelFontColor = labelFontColor;
}

public String getTheme() {
	return theme;
}

public void setTheme(String theme) {
	this.theme = theme;
}

public String getShowvalues() {
	return showvalues;
}

public void setShowvalues(String showvalues) {
	this.showvalues = showvalues;
}

public String getNumVisiblePlot() {
	return numVisiblePlot;
}

public void setNumVisiblePlot(String numVisiblePlot) {
	this.numVisiblePlot = numVisiblePlot;
}

public String getScrollheight() {
	return scrollheight;
}

public void setScrollheight(String scrollheight) {
	this.scrollheight = scrollheight;
}

public String getFlatScrollBars() {
	return flatScrollBars;
}

public void setFlatScrollBars(String flatScrollBars) {
	this.flatScrollBars = flatScrollBars;
}

public String getScrollShowButtons() {
	return scrollShowButtons;
}

public void setScrollShowButtons(String scrollShowButtons) {
	this.scrollShowButtons = scrollShowButtons;
}

public String getScrollColor() {
	return scrollColor;
}

public void setScrollColor(String scrollColor) {
	this.scrollColor = scrollColor;
}

public String getShowHoverEffect() {
	return showHoverEffect;
}

public void setShowHoverEffect(String showHoverEffect) {
	this.showHoverEffect = showHoverEffect;
}

public String getShowYAxisValue() {
	return showYAxisValue;
}

public void setShowYAxisValue(String showYAxisValue) {
	this.showYAxisValue = showYAxisValue;
}

public String getPaletteColors() {
	return paletteColors;
}

public void setPaletteColors(String paletteColors) {
	this.paletteColors = paletteColors;
}

public String getLabelFontColor() {
	return labelFontColor;
}

public void setLabelFontColor(String labelFontColor) {
	this.labelFontColor = labelFontColor;
}



}
