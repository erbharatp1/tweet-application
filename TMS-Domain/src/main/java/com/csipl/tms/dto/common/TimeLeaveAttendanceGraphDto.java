package com.csipl.tms.dto.common;

import java.util.List;



public class TimeLeaveAttendanceGraphDto {
	
	TimeLeaveAttendanceChart chart;
	List<Dataset> dataset ;	
	List<Categories> categories;

	
	public TimeLeaveAttendanceGraphDto(TimeLeaveAttendanceChart chart, List<Categories> categories, List<Dataset> dataset) {
		super();
		this.chart = chart;
		this.categories = categories;
		this.dataset = dataset;
		
	}
	
	public TimeLeaveAttendanceChart getChart() {
		return chart;
	}
	public void setChart(TimeLeaveAttendanceChart chart) {
		this.chart = chart;
	}
	public List<Dataset> getDataset() {
		return dataset;
	}
	public void setDataset(List<Dataset> dataset) {
		this.dataset = dataset;
	}
	public List<Categories> getCategories() {
		return categories;
	}
	public void setCategories(List<Categories> categories) {
		this.categories = categories;
	}
	
	
	 
}
