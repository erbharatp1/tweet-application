package com.csipl.hrms.model.employee;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.csipl.hrms.model.organisation.Department;

/**
 * The persistent class for the CompanyPolicy database table.
 * 
 */
@Entity
@NamedQuery(name = "EmployeeCompanyPolicy.findAll", query = "SELECT c FROM EmployeeCompanyPolicy c")
public class EmployeeCompanyPolicy implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long employeeCompanyPolicyId;

	private String status;

	@Temporal(TemporalType.DATE)
	private Date dateCreated;

	@Temporal(TemporalType.DATE)
	private Date dateUpdated;

	private Long userId;
	private Long userIdUpdate;

	@ManyToOne
	@JoinColumn(name = "companyPolicyId")
	private CompanyPolicy companyPolicy;

	@ManyToOne
	@JoinColumn(name = "employeeId")
	private Employee employee;

	@ManyToOne
	@JoinColumn(name = "departmentId")
	private Department department;

	public EmployeeCompanyPolicy() {
	}

	public Long getEmployeeCompanyPolicyId() {
		return employeeCompanyPolicyId;
	}

	public void setEmployeeCompanyPolicyId(Long employeeCompanyPolicyId) {
		this.employeeCompanyPolicyId = employeeCompanyPolicyId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	public Date getDateUpdated() {
		return dateUpdated;
	}

	public void setDateUpdated(Date dateUpdated) {
		this.dateUpdated = dateUpdated;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getUserIdUpdate() {
		return userIdUpdate;
	}

	public void setUserIdUpdate(Long userIdUpdate) {
		this.userIdUpdate = userIdUpdate;
	}

	public CompanyPolicy getCompanyPolicy() {
		return companyPolicy;
	}

	public void setCompanyPolicy(CompanyPolicy companyPolicy) {
		this.companyPolicy = companyPolicy;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

}