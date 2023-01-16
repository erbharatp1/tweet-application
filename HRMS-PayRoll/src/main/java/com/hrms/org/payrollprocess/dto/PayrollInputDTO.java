package com.hrms.org.payrollprocess.dto;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class PayrollInputDTO {
	
	private String processMonth;
	private int payrollDays;
	private List<Long> departmentIds;
	private List<Long> employeeIds;
	private Long departmentId;
	private Long employeeId;
		private String deptEmpFlag;

	private String activeStatus;
	private Long financialYearId;
	private Long userId;
	private Date dateCreated;
	private Long userIdUpdate;
	private Long companyId;
	
	private HashMap<Long, String> map = new HashMap<>();
	
	
	
	public String getProcessMonth() {
		return processMonth;
	}
	public void setProcessMonth(String processMonth) {
		this.processMonth = processMonth;
	}
	public int getPayrollDays() {
		return payrollDays;
	}
	public void setPayrollDays(int payrollDays) {
		this.payrollDays = payrollDays;
	}
	
	
	
	
	public Long getDepartmentId() {
		return departmentId;
	}
	public void setDepartmentId(Long departmentId) {
		this.departmentId = departmentId;
	}
	public Long getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(Long employeeId) {
		this.employeeId = employeeId;
	}
	public List<Long> getDepartmentIds() {
		return departmentIds;
	}
	public void setDepartmentIds(List<Long> departmentIds) {
		this.departmentIds = departmentIds;
	}
	public List<Long> getEmployeeIds() {
		return employeeIds;
	}
	public void setEmployeeIds(List<Long> employeeIds) {
		this.employeeIds = employeeIds;
	}

	public String getDeptEmpFlag() {
		return deptEmpFlag;
	}
	public void setDeptEmpFlag(String deptEmpFlag) {
		this.deptEmpFlag = deptEmpFlag;
	}
	public String getActiveStatus() {
		return activeStatus;
	}
	public void setActiveStatus(String activeStatus) {
		this.activeStatus = activeStatus;
	}
	public Long getFinancialYearId() {
		return financialYearId;
	}
	public void setFinancialYearId(Long financialYearId) {
		this.financialYearId = financialYearId;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public Date getDateCreated() {
		return dateCreated;
	}
	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}
	public Long getUserIdUpdate() {
		return userIdUpdate;
	}
	public void setUserIdUpdate(Long userIdUpdate) {
		this.userIdUpdate = userIdUpdate;
	}
	public Long getCompanyId() {
		return companyId;
	}
	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}
	public HashMap<Long, String> getMap() {
		return map;
	}
	public void setMap(HashMap<Long, String> map) {
		this.map = map;
	}
	
	
	
	

}
