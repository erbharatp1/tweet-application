package com.csipl.hrms.dto.recruitment;

import java.util.List;

public class Dataset {

	List<PositionCreatedSourceData> data;
	private String seriesname;

	public List<PositionCreatedSourceData> getData() {
		return data;
	}

	public void setData(List<PositionCreatedSourceData> data) {
		this.data = data;
	}

	public String getSeriesname() {
		return seriesname;
	}

	public void setSeriesname(String seriesname) {
		this.seriesname = seriesname;
	}

}
