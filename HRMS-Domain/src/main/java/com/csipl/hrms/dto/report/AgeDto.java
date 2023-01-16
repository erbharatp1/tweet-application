package com.csipl.hrms.dto.report;

import java.util.ArrayList;

public class AgeDto {
	
	AgeWiseChart chart;
	
	ArrayList<AgeData> data= new ArrayList<>();

	public AgeDto( 	AgeWiseChart chart, ArrayList<AgeData> data) {
		super();
		this.chart = chart;
		this.data = data;
	}

 

	public 	AgeWiseChart getChart() {
		return chart;
	}



	public void setChart(	AgeWiseChart chart) {
		this.chart = chart;
	}



	public ArrayList<AgeData> getData() {
		return data;
	}

	public void setData(ArrayList<AgeData> data) {
		this.data = data;
	}
	
	

}
