package com.csipl.hrms.model.payrollprocess;

import java.io.Serializable;
import javax.persistence.*;

import com.csipl.hrms.model.employee.Employee;
import com.csipl.hrms.model.organisation.Department;

import java.util.Date;


/**
 * The persistent class for the PayRegister database table.
 * 
 */
@Entity
@NamedQuery(name="PayRegister.findAll", query="SELECT p FROM PayRegister p")
public class PayRegister implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long payRegisterId;

	@Temporal(TemporalType.TIMESTAMP)
	private Date dateCreated;

	@Temporal(TemporalType.TIMESTAMP)
	private Date dateUpdate;

	private String employeeCode;

	private Long userId;

	private Long userIdUpdate;

	//bi-directional many-to-one association to Employee
	@ManyToOne
	@JoinColumn(name="employeeId")
	private Employee employee;

	//bi-directional many-to-one association to Department
	@ManyToOne
	@JoinColumn(name="departmentId")
	private Department department;

	//bi-directional many-to-one association to PayRegisterHd
	@ManyToOne
	@JoinColumn(name="payRegisterHdId")
	private PayRegisterHd payRegisterHd;
	
	private String activeStatus;

	private Boolean payrollLockFlag;

	public PayRegister() {
	}

	public Long getPayRegisterId() {
		return this.payRegisterId;
	}

	public void setPayRegisterId(Long payRegisterId) {
		this.payRegisterId = payRegisterId;
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

	public String getEmployeeCode() {
		return this.employeeCode;
	}

	public void setEmployeeCode(String employeeCode) {
		this.employeeCode = employeeCode;
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

	public Department getDepartment() {
		return this.department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	public PayRegisterHd getPayRegisterHd() {
		return this.payRegisterHd;
	}

	public void setPayRegisterHd(PayRegisterHd payRegisterHd) {
		this.payRegisterHd = payRegisterHd;
	}

	public String getActiveStatus() {
		return activeStatus;
	}

	public void setActiveStatus(String activeStatus) {
		this.activeStatus = activeStatus;
	}

	public Boolean getPayrollLockFlag() {
		return payrollLockFlag;
	}

	public void setPayrollLockFlag(Boolean payrollLockFlag) {
		this.payrollLockFlag = payrollLockFlag;
	}

}