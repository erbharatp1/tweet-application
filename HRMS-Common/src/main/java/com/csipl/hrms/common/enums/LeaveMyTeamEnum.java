package com.csipl.hrms.common.enums;

public enum LeaveMyTeamEnum {

	Today("myteam_today"), Month("myteam_month");

	public String leaveFlagEnum;

	private LeaveMyTeamEnum(String leaveFlagEnum) {
		this.leaveFlagEnum = leaveFlagEnum;
	}

	public String getLeaveFlagEnum() {
		return leaveFlagEnum;
	}

	public void setLeaveFlagEnum(String leaveFlagEnum) {
		this.leaveFlagEnum = leaveFlagEnum;
	}

}
