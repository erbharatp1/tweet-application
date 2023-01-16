package com.csipl.tms.dto.common;

import java.util.List;

public class LeaveTakenByLeaveTypeGraphDto {

	LeaveTakenByLeaveTypeChart chart;
	List<Dataset> dataset ;	
	List<Categories> categories;
	
	public LeaveTakenByLeaveTypeGraphDto(LeaveTakenByLeaveTypeChart chart,List<Categories> categories, List<Dataset> dataset
			) {
		super();
		this.chart = chart;
		this.dataset = dataset;
		this.categories = categories;
	}
	public LeaveTakenByLeaveTypeChart getChart() {
		return chart;
	}
	public void setChart(LeaveTakenByLeaveTypeChart chart) {
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
