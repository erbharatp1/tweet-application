package com.csipl.hrms.dto.report;

public class DepartmentChartEmpMaster {

	 
		private String caption, subCaption, xAxisName, yAxisName, theme, paletteColors, labelDisplay, baseFontColor, baseFont,
		captionFontSize, subcaptionFontSize, subcaptionFontBold, showBorder, bgColor, showShadow, canvasBgColor,
		showCanvasBorder, showHoverEffect, yAxisValueDecimals, baseFontSize, width, height, showValues,
		setAdaptiveYMin, decimalSeparator, thousandSeparator, forceDecimals, forceYAxisValueDecimals,
		forceXAxisValueDecimals, thousandSeparatorPosition, labelFontColor,formatNumber,yFormatNumber,xFormatNumber,formatNumberScale;

	/**
	 *  formatNumber: "0",
	        yFormatNumber:"0",
	        xFormatNumber:"0",
	        formatNumberScale:"0",
	 
	 */
		
	/**
	 * 
	 * @param caption
	 * @param subCaption
	 * @param xAxisName
	 * @param yAxisName
	 * @param theme
	 * @param paletteColors
	 * @param labelDisplay
	 * @param baseFontColor
	 * @param baseFont
	 * @param captionFontSize
	 * @param subcaptionFontSize
	 * @param subcaptionFontBold
	 * @param showBorder
	 * @param bgColor
	 * @param showShadow
	 * @param canvasBgColor
	 * @param showCanvasBorder
	 * @param showHoverEffect
	 * @param yAxisValueDecimals
	 * @param baseFontSize
	 * @param width
	 * @param height
	 * @param showValues
	 * @param setAdaptiveYMin
	 * @param decimalSeparator
	 * @param thousandSeparator
	 * @param forceDecimals
	 * @param forceYAxisValueDecimals
	 * @param forceXAxisValueDecimals
	 * @param thousandSeparatorPosition
	 * @param labelFontColor
	 */
		public DepartmentChartEmpMaster(String caption, String subCaption, String xAxisName, String yAxisName,
				String theme, String paletteColors, String labelDisplay, String baseFontColor, String baseFont,
				String captionFontSize, String subcaptionFontSize, String subcaptionFontBold, String showBorder,
				String bgColor, String showShadow, String canvasBgColor, String showCanvasBorder, String showHoverEffect,
				String yAxisValueDecimals, String baseFontSize, String width, String height, String showValues,
				String setAdaptiveYMin, String decimalSeparator, String thousandSeparator, String forceDecimals,
				String forceYAxisValueDecimals, String forceXAxisValueDecimals, String thousandSeparatorPosition,
				String labelFontColor) {
			super();
			this.caption = caption;
			this.subCaption = subCaption;
			this.xAxisName = xAxisName;
			this.yAxisName = yAxisName;
			this.theme = theme;
			this.paletteColors = paletteColors;
			this.labelDisplay = labelDisplay;
			this.baseFontColor = baseFontColor;
			this.baseFont = baseFont;
			this.captionFontSize = captionFontSize;
			this.subcaptionFontSize = subcaptionFontSize;
			this.subcaptionFontBold = subcaptionFontBold;
			this.showBorder = showBorder;
			this.bgColor = bgColor;
			this.showShadow = showShadow;
			this.canvasBgColor = canvasBgColor;
			this.showCanvasBorder = showCanvasBorder;
			this.showHoverEffect = showHoverEffect;
			this.yAxisValueDecimals = yAxisValueDecimals;
			this.baseFontSize = baseFontSize;
			this.width = width;
			this.height = height;
			this.showValues = showValues;
			this.setAdaptiveYMin = setAdaptiveYMin;
			this.decimalSeparator = decimalSeparator;
			this.thousandSeparator = thousandSeparator;
			this.forceDecimals = forceDecimals;
			this.forceYAxisValueDecimals = forceYAxisValueDecimals;
			this.forceXAxisValueDecimals = forceXAxisValueDecimals;
			this.thousandSeparatorPosition = thousandSeparatorPosition;
			this.labelFontColor = labelFontColor;
		}
	/**
	 * 
	 * @param theme
	 * @param paletteColors
	 * @param labelDisplay
	 * @param baseFontColor
	 * @param showBorder
	 * @param bgColor
	 * @param showShadow
	 * @param canvasBgColor
	 * @param showCanvasBorder
	 * @param showHoverEffect
	 * @param baseFontSize
	 * @param showValues
	 * @param setAdaptiveYMin
	 * @param labelFontColor
	 * @param formatNumber
	 * @param yFormatNumber
	 * @param xFormatNumber
	 * @param formatNumberScale
	 */
		public DepartmentChartEmpMaster(String theme, String paletteColors, String labelDisplay, String baseFontColor,
			String showBorder, String bgColor, String showShadow, String canvasBgColor, String showCanvasBorder,
			String showHoverEffect, String baseFontSize, String showValues, String setAdaptiveYMin, String labelFontColor,
			String formatNumber, String yFormatNumber, String xFormatNumber, String formatNumberScale) {
		super();
		this.theme = theme;
		this.paletteColors = paletteColors;
		this.labelDisplay = labelDisplay;
		this.baseFontColor = baseFontColor;
		this.showBorder = showBorder;
		this.bgColor = bgColor;
		this.showShadow = showShadow;
		this.canvasBgColor = canvasBgColor;
		this.showCanvasBorder = showCanvasBorder;
		this.showHoverEffect = showHoverEffect;
		this.baseFontSize = baseFontSize;
		this.showValues = showValues;
		this.setAdaptiveYMin = setAdaptiveYMin;
		this.labelFontColor = labelFontColor;
		this.formatNumber = formatNumber;
		this.yFormatNumber = yFormatNumber;
		this.xFormatNumber = xFormatNumber;
		this.formatNumberScale = formatNumberScale;
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

		public String getxAxisName() {
			return xAxisName;
		}

		public void setxAxisName(String xAxisName) {
			this.xAxisName = xAxisName;
		}

		public String getyAxisName() {
			return yAxisName;
		}

		public void setyAxisName(String yAxisName) {
			this.yAxisName = yAxisName;
		}

		public String getTheme() {
			return theme;
		}

		public void setTheme(String theme) {
			this.theme = theme;
		}

		public String getPaletteColors() {
			return paletteColors;
		}

		public void setPaletteColors(String paletteColors) {
			this.paletteColors = paletteColors;
		}

		public String getLabelDisplay() {
			return labelDisplay;
		}

		public void setLabelDisplay(String labelDisplay) {
			this.labelDisplay = labelDisplay;
		}

		public String getBaseFontColor() {
			return baseFontColor;
		}

		public void setBaseFontColor(String baseFontColor) {
			this.baseFontColor = baseFontColor;
		}

		public String getBaseFont() {
			return baseFont;
		}

		public void setBaseFont(String baseFont) {
			this.baseFont = baseFont;
		}

		public String getCaptionFontSize() {
			return captionFontSize;
		}

		public void setCaptionFontSize(String captionFontSize) {
			this.captionFontSize = captionFontSize;
		}

		public String getSubcaptionFontSize() {
			return subcaptionFontSize;
		}

		public void setSubcaptionFontSize(String subcaptionFontSize) {
			this.subcaptionFontSize = subcaptionFontSize;
		}

		public String getSubcaptionFontBold() {
			return subcaptionFontBold;
		}

		public void setSubcaptionFontBold(String subcaptionFontBold) {
			this.subcaptionFontBold = subcaptionFontBold;
		}

		public String getShowBorder() {
			return showBorder;
		}

		public void setShowBorder(String showBorder) {
			this.showBorder = showBorder;
		}

		public String getBgColor() {
			return bgColor;
		}

		public void setBgColor(String bgColor) {
			this.bgColor = bgColor;
		}

		public String getShowShadow() {
			return showShadow;
		}

		public void setShowShadow(String showShadow) {
			this.showShadow = showShadow;
		}

		public String getCanvasBgColor() {
			return canvasBgColor;
		}

		public void setCanvasBgColor(String canvasBgColor) {
			this.canvasBgColor = canvasBgColor;
		}

		public String getShowCanvasBorder() {
			return showCanvasBorder;
		}

		public void setShowCanvasBorder(String showCanvasBorder) {
			this.showCanvasBorder = showCanvasBorder;
		}

		public String getShowHoverEffect() {
			return showHoverEffect;
		}

		public void setShowHoverEffect(String showHoverEffect) {
			this.showHoverEffect = showHoverEffect;
		}

		public String getyAxisValueDecimals() {
			return yAxisValueDecimals;
		}

		public void setyAxisValueDecimals(String yAxisValueDecimals) {
			this.yAxisValueDecimals = yAxisValueDecimals;
		}

		public String getBaseFontSize() {
			return baseFontSize;
		}

		public void setBaseFontSize(String baseFontSize) {
			this.baseFontSize = baseFontSize;
		}

		public String getWidth() {
			return width;
		}

		public void setWidth(String width) {
			this.width = width;
		}

		public String getHeight() {
			return height;
		}

		public void setHeight(String height) {
			this.height = height;
		}

		public String getShowValues() {
			return showValues;
		}

		public void setShowValues(String showValues) {
			this.showValues = showValues;
		}

		public String getSetAdaptiveYMin() {
			return setAdaptiveYMin;
		}

		public void setSetAdaptiveYMin(String setAdaptiveYMin) {
			this.setAdaptiveYMin = setAdaptiveYMin;
		}

		public String getDecimalSeparator() {
			return decimalSeparator;
		}

		public void setDecimalSeparator(String decimalSeparator) {
			this.decimalSeparator = decimalSeparator;
		}

		public String getThousandSeparator() {
			return thousandSeparator;
		}

		public void setThousandSeparator(String thousandSeparator) {
			this.thousandSeparator = thousandSeparator;
		}

		public String getForceDecimals() {
			return forceDecimals;
		}

		public void setForceDecimals(String forceDecimals) {
			this.forceDecimals = forceDecimals;
		}

		public String getForceYAxisValueDecimals() {
			return forceYAxisValueDecimals;
		}

		public void setForceYAxisValueDecimals(String forceYAxisValueDecimals) {
			this.forceYAxisValueDecimals = forceYAxisValueDecimals;
		}

		public String getForceXAxisValueDecimals() {
			return forceXAxisValueDecimals;
		}

		public void setForceXAxisValueDecimals(String forceXAxisValueDecimals) {
			this.forceXAxisValueDecimals = forceXAxisValueDecimals;
		}

		public String getThousandSeparatorPosition() {
			return thousandSeparatorPosition;
		}

		public void setThousandSeparatorPosition(String thousandSeparatorPosition) {
			this.thousandSeparatorPosition = thousandSeparatorPosition;
		}

		public String getLabelFontColor() {
			return labelFontColor;
		}

		public void setLabelFontColor(String labelFontColor) {
			this.labelFontColor = labelFontColor;
		}
	      
	    
	      }
