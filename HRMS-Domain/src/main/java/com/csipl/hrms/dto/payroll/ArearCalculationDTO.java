package com.csipl.hrms.dto.payroll;

import java.math.BigDecimal;
import java.util.Date;

public class ArearCalculationDTO {

	private Long arearCalculationId;

	private BigDecimal actualAmount;

	private Long companyId;

	private Date dateCreated;

	private Date dateUpdated;
	private BigDecimal netPayableAmount;

	private String payrollMonth;

	private BigDecimal pfDeduction;
	private Long employeeId;
	private Long userId;
	private boolean isBooked;
	private Long userIdUpdate;
	private Long arearId;
	private Date arearFrom;
	private BigDecimal esiDeduction;
	private String employeeCode;
	private String empName;
	private String departmentName;
	private String deductionAmt;
	private Date arearTo;
	private Date dateOfJoining;
	private String designationName;
	private String gradesName;
	  private BigDecimal basicSalary;
	  private BigDecimal specialAllowance;
	private String  employeeLogoPath;
	private BigDecimal ptDeduction;
	public Long getArearCalculationId() {
		return arearCalculationId;
	}

	public void setArearCalculationId(Long arearCalculationId) {
		this.arearCalculationId = arearCalculationId;
	}

	public BigDecimal getActualAmount() {
		return actualAmount;
	}

	public void setActualAmount(BigDecimal actualAmount) {
		this.actualAmount = actualAmount;
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

	public Date getDateUpdated() {
		return dateUpdated;
	}

	public void setDateUpdated(Date dateUpdated) {
		this.dateUpdated = dateUpdated;
	}

	public BigDecimal getEsiDeduction() {
		return esiDeduction;
	}

	public void setEsiDeduction(BigDecimal esiDeduction) {
		this.esiDeduction = esiDeduction;
	}

	public BigDecimal getNetPayableAmount() {
		return netPayableAmount;
	}

	public void setNetPayableAmount(BigDecimal netPayableAmount) {
		this.netPayableAmount = netPayableAmount;
	}

	public String getPayrollMonth() {
		return payrollMonth;
	}

	public void setPayrollMonth(String payrollMonth) {
		this.payrollMonth = payrollMonth;
	}

	public BigDecimal getPfDeduction() {
		return pfDeduction;
	}

	public void setPfDeduction(BigDecimal pfDeduction) {
		this.pfDeduction = pfDeduction;
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

	public Long getArearId() {
		return arearId;
	}

	public void setArearId(Long arearId) {
		this.arearId = arearId;
	}

	public String getEmployeeCode() {
		return employeeCode;
	}

	public void setEmployeeCode(String employeeCode) {
		this.employeeCode = employeeCode;
	}

	public String getEmpName() {
		return empName;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
	}

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	public String getDeductionAmt() {
		return deductionAmt;
	}

	public void setDeductionAmt(String deductionAmt) {
		this.deductionAmt = deductionAmt;
	}

	public Date getArearFrom() {
		return arearFrom;
	}

	public void setArearFrom(Date arearFrom) {
		this.arearFrom = arearFrom;
	}

	public Date getArearTo() {
		return arearTo;
	}

	public void setArearTo(Date arearTo) {
		this.arearTo = arearTo;
	}

	public boolean getIsBooked() {
		return isBooked;
	}

	public void setIsBooked(boolean isBooked) {
		this.isBooked = isBooked;
	}

	public Long getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(Long employeeId) {
		this.employeeId = employeeId;
	}

	public Date getDateOfJoining() {
		return dateOfJoining;
	}

	public void setDateOfJoining(Date dateOfJoining) {
		this.dateOfJoining = dateOfJoining;
	}

	public String getDesignationName() {
		return designationName;
	}

	public void setDesignationName(String designationName) {
		this.designationName = designationName;
	}

	public String getGradesName() {
		return gradesName;
	}

	public void setGradesName(String gradesName) {
		this.gradesName = gradesName;
	}

	public BigDecimal getBasicSalary() {
		return basicSalary;
	}

	public void setBasicSalary(BigDecimal basicSalary) {
		this.basicSalary = basicSalary;
	}

	public BigDecimal getSpecialAllowance() {
		return specialAllowance;
	}

	public void setSpecialAllowance(BigDecimal specialAllowance) {
		this.specialAllowance = specialAllowance;
	}

	public String getEmployeeLogoPath() {
		return employeeLogoPath;
	}

	public void setEmployeeLogoPath(String employeeLogoPath) {
		this.employeeLogoPath = employeeLogoPath;
	}

	public BigDecimal getPtDeduction() {
		return ptDeduction;
	}

	public void setPtDeduction(BigDecimal ptDeduction) {
		this.ptDeduction = ptDeduction;
	}

	public void setBooked(boolean isBooked) {
		this.isBooked = isBooked;
	}

}
