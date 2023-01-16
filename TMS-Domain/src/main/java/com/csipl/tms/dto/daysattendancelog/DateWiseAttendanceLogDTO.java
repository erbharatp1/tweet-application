package com.csipl.tms.dto.daysattendancelog;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateWiseAttendanceLogDTO  {

	
	private String title;
	
	private Date start;
	private String inTime;
	private String outTime;
	private String mode;
	private String shift;
	private String eventColor;
	private String titleValue;
	private String leaveDate;
	private String starts;
	private String employeeCode;
	private String status;
	private Long employeeId;
	private Long companyId;
	private String dayName;
	public String getEmployeeCode() {
		return employeeCode;
	}

	public void setEmployeeCode(String employeeCode) {
		this.employeeCode = employeeCode;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Long getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(Long employeeId) {
		this.employeeId = employeeId;
	}

	 

	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	public DateWiseAttendanceLogDTO() {
		
	}

	public DateWiseAttendanceLogDTO(String title, Date start,String inTime,String outTime) {
		super();
		this.title = title;
		this.start = start;
		this.inTime = inTime;
		this.outTime = outTime;
	}

	public String getLeaveDate() {
		return leaveDate;
	}

	public void setLeaveDate(String leaveDate) {
		this.leaveDate = leaveDate;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Date getStart() {
		return start;
	}

	public void setStart(Date start) {
		this.start = start;
	}

	public String getInTime() {
		return inTime;
	}

	public void setInTime(String inTime) {
		this.inTime = inTime;
	}

	public String getOutTime() {
		return outTime;
	}

	public void setOutTime(String outTime) {
		this.outTime = outTime;
	}

	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}

	public String getShift() {
		return shift;
	}

	public void setShift(String shift) {
		this.shift = shift;
	}

	public String getEventColor() {
		return eventColor;
	}

	public void setEventColor(String eventColor) {
		this.eventColor = eventColor;
	}



	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((eventColor == null) ? 0 : eventColor.hashCode());
		result = prime * result + ((inTime == null) ? 0 : inTime.hashCode());
		result = prime * result + ((mode == null) ? 0 : mode.hashCode());
		result = prime * result + ((outTime == null) ? 0 : outTime.hashCode());
		result = prime * result + ((shift == null) ? 0 : shift.hashCode());
		result = prime * result + ((start == null) ? 0 : start.hashCode());
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DateWiseAttendanceLogDTO other = (DateWiseAttendanceLogDTO) obj;
		if (eventColor == null) {
			if (other.eventColor != null)
				return false;
		} else if (!eventColor.equals(other.eventColor))
			return false;
		if (inTime == null) {
			if (other.inTime != null)
				return false;
		} else if (!inTime.equals(other.inTime))
			return false;
		if (mode == null) {
			if (other.mode != null)
				return false;
		} else if (!mode.equals(other.mode))
			return false;
		if (outTime == null) {
			if (other.outTime != null)
				return false;
		} else if (!outTime.equals(other.outTime))
			return false;
		if (shift == null) {
			if (other.shift != null)
				return false;
		} else if (!shift.equals(other.shift))
			return false;
		if (start == null) {
			if (other.start != null)
				return false;
		} else if (!start.equals(other.start))
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		return true;
	}

	public String getTitleValue() {
		return titleValue;
	}

	public void setTitleValue(String titleValue) {
		this.titleValue = titleValue;
	}

	public String getStarts() {
		return starts;
	}

	public void setStarts(String starts) {
		this.starts = starts;
	}

	public String getDayName() {
		return dayName;
	}

	public void setDayName(String dayName) {
		this.dayName = dayName;
	}

	

}
