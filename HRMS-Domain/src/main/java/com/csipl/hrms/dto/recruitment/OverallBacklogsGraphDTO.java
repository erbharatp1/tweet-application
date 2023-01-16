package com.csipl.hrms.dto.recruitment;

public class OverallBacklogsGraphDTO {

	private Integer totalPositionCount;
	private Integer closedPositionCount;
	private Integer openPositionCount;
	private String recruiterName;

	public Integer getTotalPositionCount() {
		return totalPositionCount;
	}

	public void setTotalPositionCount(Integer totalPositionCount) {
		this.totalPositionCount = totalPositionCount;
	}

	public Integer getClosedPositionCount() {
		return closedPositionCount;
	}

	public void setClosedPositionCount(Integer closedPositionCount) {
		this.closedPositionCount = closedPositionCount;
	}

	public Integer getOpenPositionCount() {
		return openPositionCount;
	}

	public void setOpenPositionCount(Integer openPositionCount) {
		this.openPositionCount = openPositionCount;
	}

	public String getRecruiterName() {
		return recruiterName;
	}

	public void setRecruiterName(String recruiterName) {
		this.recruiterName = recruiterName;
	}

}
