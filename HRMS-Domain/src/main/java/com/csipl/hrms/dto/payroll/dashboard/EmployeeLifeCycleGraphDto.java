package com.csipl.hrms.dto.payroll.dashboard;

import java.util.ArrayList;
import java.util.List;

public class EmployeeLifeCycleGraphDto {
	EmployeeLifeCycleChart chart;
	List<Dataset> dataset ;	
	List<Categories> categories;
	 
	public EmployeeLifeCycleGraphDto(EmployeeLifeCycleChart chart, List<Categories> categories, List<Dataset> dataset) {
		super();
		this.chart = chart;
		this.categories = categories;
		this.dataset = dataset;
		
	}
	
	public EmployeeLifeCycleChart getChart() {
		return chart;
	}
	public void setChart(EmployeeLifeCycleChart chart) {
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
