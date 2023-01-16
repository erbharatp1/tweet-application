package com.csipl.hrms.dto.payroll.dashboard;

public class EmployeeLifeCycleChart {

	private String 
	 theme,
	 showvalues,
	 numVisiblePlot,
	 scrollheight,
	 flatScrollBars,
	 scrollShowButtons,
	 showHoverEffect,
     showYAxisvalue,
     labelFontColor 
     ;

	
	public EmployeeLifeCycleChart(String theme, String showvalues, String numVisiblePlot, String scrollheight,
			String flatScrollBars, String scrollShowButtons, String showHoverEffect, String showYAxisvalue,
			String labelFontColor) {
		super();
		this.theme = theme;
		this.showvalues = showvalues;
		this.numVisiblePlot = numVisiblePlot;
		this.scrollheight = scrollheight;
		this.flatScrollBars = flatScrollBars;
		this.scrollShowButtons = scrollShowButtons;
		this.showHoverEffect = showHoverEffect;
		this.showYAxisvalue = showYAxisvalue;
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

	public String getShowHoverEffect() {
		return showHoverEffect;
	}

	public void setShowHoverEffect(String showHoverEffect) {
		this.showHoverEffect = showHoverEffect;
	}

	public String getShowYAxisvalue() {
		return showYAxisvalue;
	}

	public void setShowYAxisvalue(String showYAxisvalue) {
		this.showYAxisvalue = showYAxisvalue;
	}

	public String getLabelFontColor() {
		return labelFontColor;
	}

	public void setLabelFontColor(String labelFontColor) {
		this.labelFontColor = labelFontColor;
	}

 
	 
}
