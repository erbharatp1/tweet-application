package com.csipl.hrms.dto.recruitment;

public class PositionSourceGraphDTO {

	private String sourceOfPosition;
	private String positionCount;
	private String sourceOfProfile;
	private String sourceOfProfileCount;

	private String month;
	private String monthName;
	private String year;
	private String positionClosedByMonthCount;
	private String recruiter;
	private Long totalCount;
	private Long closeCount;
	private String openCount;
	private String closePositionCount;
	private String totalPositionCount;

	public String getSourceOfPosition() {
		return sourceOfPosition;
	}

	public void setSourceOfPosition(String sourceOfPosition) {
		this.sourceOfPosition = sourceOfPosition;
	}

	public String getPositionCount() {
		return positionCount;
	}

	public void setPositionCount(String positionCount) {
		this.positionCount = positionCount;
	}

	public String getSourceOfProfile() {
		return sourceOfProfile;
	}

	public void setSourceOfProfile(String sourceOfProfile) {
		this.sourceOfProfile = sourceOfProfile;
	}

	public String getSourceOfProfileCount() {
		return sourceOfProfileCount;
	}

	public void setSourceOfProfileCount(String sourceOfProfileCount) {
		this.sourceOfProfileCount = sourceOfProfileCount;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public String getMonthName() {
		return monthName;
	}

	public void setMonthName(String monthName) {
		this.monthName = monthName;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getPositionClosedByMonthCount() {
		return positionClosedByMonthCount;
	}

	public void setPositionClosedByMonthCount(String positionClosedByMonthCount) {
		this.positionClosedByMonthCount = positionClosedByMonthCount;
	}

	public String getRecruiter() {
		return recruiter;
	}

	public void setRecruiter(String recruiter) {
		this.recruiter = recruiter;
	}

	public Long getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(Long totalCount) {
		this.totalCount = totalCount;
	}

	public Long getCloseCount() {
		return closeCount;
	}

	public void setCloseCount(Long closeCount) {
		this.closeCount = closeCount;
	}

	public String getOpenCount() {
		return openCount;
	}

	public void setOpenCount(String openCount) {
		this.openCount = openCount;
	}

	public String getTotalPositionCount() {
		return totalPositionCount;
	}

	public void setTotalPositionCount(String totalPositionCount) {
		this.totalPositionCount = totalPositionCount;
	}

	public String getClosePositionCount() {
		return closePositionCount;
	}

	public void setClosePositionCount(String closePositionCount) {
		this.closePositionCount = closePositionCount;
	}

}
