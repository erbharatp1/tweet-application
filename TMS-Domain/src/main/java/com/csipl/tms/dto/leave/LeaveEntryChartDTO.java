package com.csipl.tms.dto.leave;

import java.util.ArrayList;

import com.csipl.hrms.dto.report.Data;
import com.csipl.hrms.dto.report.LeaveChart;

public class LeaveEntryChartDTO {
	LeaveChart chart;
	ArrayList<Data> data= new ArrayList<>();
	
	
	public LeaveEntryChartDTO(LeaveChart chart, ArrayList<Data> data) {
		super();
		this.chart = chart;
		this.data = data;
	}
	public LeaveChart getChart() {
		return chart;
	}
	public void setChart(LeaveChart chart) {
		this.chart = chart;
	}
	public ArrayList<Data> getData() {
		return data;
	}
	public void setData(ArrayList<Data> data) {
		this.data = data;
	}
	
}
