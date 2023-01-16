package com.csipl.hrms.dto.employee;

import java.util.List;

public class EmployeeNode {
	private List<EmployeeNode> children;
	private Long employeeId;
	private String name;
	private String imageUrl;
	private String positionName;
	private String profileUrl;
	private Long reportToId;
	private DepartmentNode unit;

	public Long getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(Long employeeId) {
		this.employeeId = employeeId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getPositionName() {
		return positionName;
	}

	public void setPositionName(String positionName) {
		this.positionName = positionName;
	}

	public String getProfileUrl() {
		return profileUrl;
	}

	public void setProfileUrl(String profileUrl) {
		this.profileUrl = profileUrl;
	}

	public List<EmployeeNode> getChildren() {
		return children;
	}

	public void setChildren(List<EmployeeNode> children) {
		this.children = children;
	}

	public Long getReportToId() {
		return reportToId;
	}

	public void setReportToId(Long reportToId) {
		this.reportToId = reportToId;
	}

	public DepartmentNode getUnit() {
		return unit;
	}

	public void setUnit(DepartmentNode unit) {
		this.unit = unit;
	}

}
