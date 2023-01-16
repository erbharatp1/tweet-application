package com.csipl.hrms.model.payroll;

import java.io.Serializable;
import javax.persistence.*;

import com.csipl.hrms.model.employee.Employee;

import java.util.Date;


/**
 * The persistent class for the HoldSalary database table.
 * 
 */
@Entity
@NamedQuery(name="HoldSalary.findAll", query="SELECT h FROM HoldSalary h")
public class HoldSalary implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long holdSalaryId;

	private Long companyId;

	@Temporal(TemporalType.DATE)
	private Date dateCreated;

	@Temporal(TemporalType.DATE)
	private Date dateUpdate;

	private String payrollMonth;

	private String remark;

	private String status;

	private Long userId;

	private Long userIdUpdate;

	//bi-directional many-to-one association to Employee
	@ManyToOne
	@JoinColumn(name="employeeId")
	private Employee employee;

	public HoldSalary() {
	}

	public Long getHoldSalaryId() {
		return this.holdSalaryId;
	}

	public void setHoldSalaryId(Long holdSalaryId) {
		this.holdSalaryId = holdSalaryId;
	}

	public Long getCompanyId() {
		return this.companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
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

	public String getPayrollMonth() {
		return this.payrollMonth;
	}

	public void setPayrollMonth(String payrollMonth) {
		this.payrollMonth = payrollMonth;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
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

}