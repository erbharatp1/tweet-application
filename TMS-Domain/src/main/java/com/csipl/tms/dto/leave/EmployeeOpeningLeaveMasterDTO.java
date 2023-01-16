package com.csipl.tms.dto.leave;

 
import java.util.Date;

import com.csipl.hrms.model.common.Company;
import com.csipl.hrms.model.employee.Employee;
import com.csipl.tms.model.leave.TMSLeavePeriod;
import com.csipl.tms.model.leave.TMSLeaveRuleMaster;


/**
 * The persistent class for the EmployeeOpeningLeaveMaster database table.
 * 
 */
 
public class EmployeeOpeningLeaveMasterDTO   {
	 
	private Long empOpeningId;

	 
	private Date dateCreated;

	 
	private Date dateUpdate;

	private Long noOfOpening;

	private Long userId;

	private Long userIdUpdate;

 
	private Employee employee;

	 
	private TMSLeavePeriod tmsleavePeriod;

 
	private TMSLeaveRuleMaster tmsleaveRuleMaster;

	 
	private Company company;

	public EmployeeOpeningLeaveMasterDTO() {
	}

	public Long getEmpOpeningId() {
		return this.empOpeningId;
	}

	public void setEmpOpeningId(Long empOpeningId) {
		this.empOpeningId = empOpeningId;
	}

	public Date getDateCreated() {
		return this.dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	public Date getDateUpdate() {
		return this.dateUpdate;
	}

	public void setDateUpdate(Date dateUpdate) {
		this.dateUpdate = dateUpdate;
	}

	public Long getNoOfOpening() {
		return this.noOfOpening;
	}

	public void setNoOfOpening(Long noOfOpening) {
		this.noOfOpening = noOfOpening;
	}

	public Long getUserId() {
		return this.userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getUserIdUpdate() {
		return this.userIdUpdate;
	}

	public void setUserIdUpdate(Long userIdUpdate) {
		this.userIdUpdate = userIdUpdate;
	}

	public Employee getEmployee() {
		return this.employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public TMSLeavePeriod getTmsleavePeriod() {
		return this.tmsleavePeriod;
	}

	public void setTmsleavePeriod(TMSLeavePeriod tmsleavePeriod) {
		this.tmsleavePeriod = tmsleavePeriod;
	}

	public TMSLeaveRuleMaster getTmsleaveRuleMaster() {
		return this.tmsleaveRuleMaster;
	}

	public void setTmsleaveRuleMaster(TMSLeaveRuleMaster tmsleaveRuleMaster) {
		this.tmsleaveRuleMaster = tmsleaveRuleMaster;
	}

	public Company getCompany() {
		return this.company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

}