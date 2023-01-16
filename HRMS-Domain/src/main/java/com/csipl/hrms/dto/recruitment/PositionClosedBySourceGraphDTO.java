package com.csipl.hrms.dto.recruitment;

import java.util.List;

public class PositionClosedBySourceGraphDTO {

	PositionClosedBySourceChart chart;
	List<Dataset> dataset;
	List<Categories> categories;

	public PositionClosedBySourceGraphDTO(PositionClosedBySourceChart chart, List<Categories> categories,
			List<Dataset> dataset) {
		super();
		this.chart = chart;
		this.dataset = dataset;
		this.categories = categories;
	}

	public PositionClosedBySourceChart getChart() {
		return chart;
	}

	public void setChart(PositionClosedBySourceChart chart) {
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
