package com.csipl.hrms.dto.esipf.dashboard;

import java.util.ArrayList;

import com.csipl.hrms.dto.report.EsicChart;
import com.csipl.hrms.dto.report.PfChart;

public class GraphDtoNew {
	EsicChart chart;
	ArrayList<Data> data = new ArrayList<>();
	/**
	 * @param chart
	 * @param data
	 */
	public GraphDtoNew(EsicChart chart, ArrayList<Data> data) {
		super();
		this.chart = chart;
		this.data = data;
	}
	public EsicChart getChart() {
		return chart;
	}
	public void setChart(EsicChart chart) {
		this.chart = chart;
	}
	public ArrayList<Data> getData() {
		return data;
	}
	public void setData(ArrayList<Data> data) {
		this.data = data;
	}

}
