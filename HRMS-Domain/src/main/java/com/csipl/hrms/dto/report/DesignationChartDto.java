package com.csipl.hrms.dto.report;

import java.util.ArrayList;

import com.csipl.hrms.dto.payroll.dashboard.ChartNew;
 

public class DesignationChartDto {
	
	ChartNew chart;
	ArrayList<DesignationData> data =new ArrayList<DesignationData>();
	public DesignationChartDto(ChartNew chart, ArrayList<DesignationData> data) {
		super();
		this.chart = chart;
		this.data = data;
	}
	public ChartNew getChart() {
		return chart;
	}
	public void setChart(ChartNew chart) {
		this.chart = chart;
	}
	public ArrayList<DesignationData> getData() {
		return data;
	}
	public void setData(ArrayList<DesignationData> data) {
		this.data = data;
	}
	
	 
	
	

}
