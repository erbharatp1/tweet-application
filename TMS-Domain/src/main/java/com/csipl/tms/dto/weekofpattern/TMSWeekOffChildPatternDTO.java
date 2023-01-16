package com.csipl.tms.dto.weekofpattern;

public class TMSWeekOffChildPatternDTO {
	
	private Long id;

	private Long patternId;
	 
	private String activeStatus;

	private String dayName;

	private String natureOfDay;

	private Long positionOfDay;
 
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getActiveStatus() {
		return activeStatus;
	}

	public void setActiveStatus(String activeStatus) {
		this.activeStatus = activeStatus;
	}

	public String getDayName() {
		return dayName;
	}

	public void setDayName(String dayName) {
		this.dayName = dayName;
	}

	public String getNatureOfDay() {
		return natureOfDay;
	}

	public void setNatureOfDay(String natureOfDay) {
		this.natureOfDay = natureOfDay;
	}

	public Long getPositionOfDay() {
		return positionOfDay;
	}

	public void setPositionOfDay(Long positionOfDay) {
		this.positionOfDay = positionOfDay;
	}
	public Long getPatternId() {
		return patternId;
	}

	public void setPatternId(Long patternId) {
		this.patternId = patternId;
	}

}
