package com.csipl.tms.model.leave;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.*;

import com.csipl.hrms.model.common.Company;
import com.csipl.hrms.model.employee.Employee;

import java.util.Date;


/**
 * The persistent class for the EmployeeOpeningLeaveMaster database table.
 * 
 */
@Entity
@NamedQuery(name="EmployeeOpeningLeaveMaster.findAll", query="SELECT e FROM EmployeeOpeningLeaveMaster e")
public class EmployeeOpeningLeaveMaster implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long empOpeningId;

	private String activeStatus;
	
	private String status;

	@Temporal(TemporalType.DATE)
	private Date dateCreated;

	@Temporal(TemporalType.DATE)
	private Date dateUpdate;

	private BigDecimal noOfOpening;

	private Long userId;

	private Long userIdUpdate;

	//bi-directional many-to-one association to Company
	@ManyToOne
	@JoinColumn(name="companyId")
	private Company company;

	//bi-directional many-to-one association to Employee
	@ManyToOne
	@JoinColumn(name="employeeId")
	private Employee employee;

	//bi-directional many-to-one association to TMSLeavePeriod
	@ManyToOne
	@JoinColumn(name="leavePeriodId")
	private TMSLeavePeriod tmsleavePeriod;

	
	//bi-directional many-to-one association to TMSLeaveTypeMaster
	@ManyToOne
	@JoinColumn(name="leaveTypeMasterId")
	private TMSLeaveTypeMaster tmsleaveTypeMaster;

	public EmployeeOpeningLeaveMaster() {
	}

	public Long getEmpOpeningId() {
		return this.empOpeningId;
	}

	public void setEmpOpeningId(Long empOpeningId) {
		this.empOpeningId = empOpeningId;
	}

	public String getActiveStatus() {
		return this.activeStatus;
	}

	public void setActiveStatus(String activeStatus) {
		this.activeStatus = activeStatus;
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

	public BigDecimal getNoOfOpening() {
		return this.noOfOpening;
	}

	public void setNoOfOpening(BigDecimal noOfOpening) {
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

	public Company getCompany() {
		return this.company;
	}

	public void setCompany(Company company) {
		this.company = company;
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

	

	public TMSLeaveTypeMaster getTmsleaveTypeMaster() {
		return this.tmsleaveTypeMaster;
	}

	public void setTmsleaveTypeMaster(TMSLeaveTypeMaster tmsleaveTypeMaster) {
		this.tmsleaveTypeMaster = tmsleaveTypeMaster;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}