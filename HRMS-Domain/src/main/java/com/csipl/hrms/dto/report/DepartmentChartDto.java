package com.csipl.hrms.dto.report;

import java.util.ArrayList;

import com.csipl.hrms.dto.payroll.dashboard.ChartNew;

public class DepartmentChartDto {

	
	public ChartNew  chart;
	ArrayList<DepartmentData> data =new ArrayList<DepartmentData>();
	public ChartNew getChart() {
		return chart;
	}
	public void setChart(ChartNew chart) {
		this.chart = chart;
	}
	public ArrayList<DepartmentData> getData() {
		return data;
	}
	public void setData(ArrayList<DepartmentData> data) {
		this.data = data;
	}
	public DepartmentChartDto(ChartNew chart, ArrayList<DepartmentData> data) {
		super();
		this.chart = chart;
		this.data = data;
	}
	
	
	
	 	
}
