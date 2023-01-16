package com.csipl.tms.dto.common;

import java.util.List;

public class FrequentLeaveTakerGraphDto {

	FrequentLeaveTakerChart chart;
	List<Dataset> dataset ;	
	List<Categories> categories;
	
	
	
	public FrequentLeaveTakerGraphDto(FrequentLeaveTakerChart chart,List<Categories> categories, List<Dataset> dataset
			) {
		super();
		this.chart = chart;
		this.dataset = dataset;
		this.categories = categories;
	}
	public FrequentLeaveTakerChart getChart() {
		return chart;
	}
	public void setChart(FrequentLeaveTakerChart chart) {
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
