package com.csipl.tms.dto.monthattendance;

import java.util.ArrayList;

import com.csipl.tms.model.attendancelog.AttendanceChart;
import com.csipl.tms.model.attendancelog.AttendanceData;

public class AttendanceDto {

	AttendanceChart chart;
	ArrayList<AttendanceData> data = new ArrayList<>();

	public AttendanceDto(AttendanceChart chart, ArrayList<AttendanceData> data) {
		super();
		this.chart = chart;
		this.data = data;
	}

	public AttendanceChart getChart() {
		return chart;
	}

	public void setChart(AttendanceChart chart) {
		this.chart = chart;
	}

	public ArrayList<AttendanceData> getData() {
		return data;
	}

	public void setData(ArrayList<AttendanceData> data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return "AttendanceDto [chart=" + chart + ", data=" + data + "]";
	}

}