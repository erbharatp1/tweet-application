package com.csipl.hrms.dto.payroll;

import java.math.BigDecimal;
import java.util.Date;

public class HoldSalaryDTO {

	private Long holdSalaryId;
	private Long companyId;
	private Date dateCreated;
	private Date dateUpdate;
	private Long employeeId;
	private String payrollMonth;
	private String remark;
	private String status;
	private Long userId;
	private Long userIdUpdate;

	private String employeeCode;
	private String firstName;
	private String lastName;
	private BigDecimal grossPay;

	private String departmentName;
	private String gradesName;

	private Long departmentId;
	private Long  designationId;
	private Long   gradesId;

	public String getEmployeeCode() {
		return employeeCode;
	}

	public void setEmployeeCode(String employeeCode) {
		this.employeeCode = employeeCode;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public BigDecimal getGrossPay() {
		return grossPay;
	}

	public void setGrossPay(BigDecimal grossPay) {
		this.grossPay = grossPay;
	}

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	public String getGradesName() {
		return gradesName;
	}

	public void setGradesName(String gradesName) {
		this.gradesName = gradesName;
	}

	public Long getHoldSalaryId() {
		return holdSalaryId;
	}

	public void setHoldSalaryId(Long holdSalaryId) {
		this.holdSalaryId = holdSalaryId;
	}

	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	public Date getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	public Date getDateUpdate() {
		return dateUpdate;
	}

	public void setDateUpdate(Date dateUpdate) {
		this.dateUpdate = dateUpdate;
	}

	public Long getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(Long employeeId) {
		this.employeeId = employeeId;
	}

	public String getPayrollMonth() {
		return payrollMonth;
	}

	public void setPayrollMonth(String payrollMonth) {
		this.payrollMonth = payrollMonth;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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

	public Long getGradesId() {
		return gradesId;
	}

	public void setGradesId(Long gradesId) {
		this.gradesId = gradesId;
	}

	public Long getDesignationId() {
		return designationId;
	}

	public void setDesignationId(Long designationId) {
		this.designationId = designationId;
	}

	public Long getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(Long departmentId) {
		this.departmentId = departmentId;
	}

}
