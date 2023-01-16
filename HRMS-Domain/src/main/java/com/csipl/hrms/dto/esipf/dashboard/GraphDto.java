package com.csipl.hrms.dto.esipf.dashboard;

import java.util.ArrayList;

import com.csipl.hrms.dto.report.PfChart;



public class GraphDto {
	PfChart chart;
	
	ArrayList<Data> data= new ArrayList<>();

	public PfChart getChart() {
		return chart;
	}

	public void setChart(PfChart chart) {
		this.chart = chart;
	}

	public ArrayList<Data> getData() {
		return data;
	}

	public void setData(ArrayList<Data> data) {
		this.data = data;
	}

	/**
	 * @param chart
	 * @param data
	 */
	public GraphDto(PfChart chart, ArrayList<Data> data) {
		super();
		this.chart = chart;
		this.data = data;
	}
	
	
               
 

	

	

}
