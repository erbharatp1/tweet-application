package com.csipl.tms.dto.calendar;

import java.util.List;

import com.csipl.tms.dto.daysattendancelog.DaysAttendanceLogDTO;
import com.csipl.tms.dto.leave.SandwitchIssueResolver;

public class CalendarDTO implements Comparable<CalendarDTO>{

	private List<DaysAttendanceLogDTO> events;

	public List<DaysAttendanceLogDTO> getEvents() {
		return events;
	}

	public void setEvents(List<DaysAttendanceLogDTO> events) {
		this.events = events;
	}
	
	
	
	

	@Override
	public int compareTo(CalendarDTO calendarDTO) {
		// TODO Auto-generated method stub
		return 0;
	}

	

}
