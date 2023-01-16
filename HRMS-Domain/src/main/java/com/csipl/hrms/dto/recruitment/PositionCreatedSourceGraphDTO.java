package com.csipl.hrms.dto.recruitment;

import java.util.List;

public class PositionCreatedSourceGraphDTO {

	PositionCreatedSourceChart chart;
	List<Dataset> dataset;
	List<Categories> categories;

	public PositionCreatedSourceGraphDTO(PositionCreatedSourceChart chart, List<Categories> categories,
			List<Dataset> dataset) {
		super();
		this.chart = chart;
		this.dataset = dataset;
		this.categories = categories;
	}

	public PositionCreatedSourceChart getChart() {
		return chart;
	}

	public void setChart(PositionCreatedSourceChart chart) {
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
