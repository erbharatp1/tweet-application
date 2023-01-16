package com.csipl.hrms.dto.payroll.dashboard;

import java.util.ArrayList;

public class GraphDtoNew {
	ChartNew chart;
	ArrayList<Data> data= new ArrayList<>();
	
	public GraphDtoNew(ChartNew chart, ArrayList<Data> data) {
		super();
		this.chart = chart;
		this.data = data;
	}
	public ChartNew getChart() {
		return chart;
	}
	public ArrayList<Data> getData() {
		return data;
	}
	public void setChart(ChartNew chart) {
		this.chart = chart;
	}
	public void setData(ArrayList<Data> data) {
		this.data = data;
	}
	
	
}
