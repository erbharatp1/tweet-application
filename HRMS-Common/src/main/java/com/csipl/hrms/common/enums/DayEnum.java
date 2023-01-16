package com.csipl.hrms.common.enums;

public enum DayEnum {

	SUN(1), MON(2), TUE(3), WED(4), THU(5), FRI(6), SAT(7);

	Integer dayId;

	private DayEnum(Integer dayId) {
		this.dayId = dayId;
	}

	public Integer getDayId() {
		return dayId;
	}

	public void setDayId(Integer dayId) {
		this.dayId = dayId;
	}
	 
}
