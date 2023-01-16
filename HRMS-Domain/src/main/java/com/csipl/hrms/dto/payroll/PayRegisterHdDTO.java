package com.csipl.hrms.dto.payroll;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class PayRegisterHdDTO {
	private Long payRegisterHdId;
	
	private Date dateUpdate;
	
	private String processMonth;
	
	private String registerName;
	
    private Long payRegisterId;
	
	private Date DOJ;
	
	private String employeeName;
	
	private String employeeCode;
	
	private Long employeeId;
	
	private List<Long> employeeIds;
	
    private String  departmentName ;
    
    private BigDecimal netPayableAmount;
    
    private BigDecimal grossSalary;
    
    private BigDecimal payableGross;
    
    private BigDecimal deduction;
	//private String employeeCode;
    private String days;

	public Long getPayRegisterHdId() {
		return payRegisterHdId;
	}

	public void setPayRegisterHdId(Long payRegisterHdId) {
		this.payRegisterHdId = payRegisterHdId;
	}

	public Date getDateUpdate() {
		return dateUpdate;
	}

	public void setDateUpdate(Date dateUpdate) {
		this.dateUpdate = dateUpdate;
	}

	public String getProcessMonth() {
		return processMonth;
	}

	public void setProcessMonth(String processMonth) {
		this.processMonth = processMonth;
	}

	public String getRegisterName() {
		return registerName;
	}

	public void setRegisterName(String registerName) {
		this.registerName = registerName;
	}

	public Long getPayRegisterId() {
		return payRegisterId;
	}

	public void setPayRegisterId(Long payRegisterId) {
		this.payRegisterId = payRegisterId;
	}

	public Date getDOJ() {
		return DOJ;
	}

	public void setDOJ(Date dOJ) {
		DOJ = dOJ;
	}

	public String getEmployeeName() {
		return employeeName;
	}

	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

	public String getEmployeeCode() {
		return employeeCode;
	}

	public void setEmployeeCode(String employeeCode) {
		this.employeeCode = employeeCode;
	}

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	public BigDecimal getNetPayableAmount() {
		return netPayableAmount;
	}

	public void setNetPayableAmount(BigDecimal netPayableAmount) {
		this.netPayableAmount = netPayableAmount;
	}

	public BigDecimal getGrossSalary() {
		return grossSalary;
	}

	public void setGrossSalary(BigDecimal grossSalary) {
		this.grossSalary = grossSalary;
	}

	public BigDecimal getPayableGross() {
		return payableGross;
	}

	public void setPayableGross(BigDecimal payableGross) {
		this.payableGross = payableGross;
	}

	public BigDecimal getDeduction() {
		return deduction;
	}

	public void setDeduction(BigDecimal deduction) {
		this.deduction = deduction;
	}

	public String getDays() {
		return days;
	}

	public void setDays(String days) {
		this.days = days;
	}

	public Long getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(Long employeeId) {
		this.employeeId = employeeId;
	}

	public List<Long> getEmployeeIds() {
		return employeeIds;
	}

	public void setEmployeeIds(List<Long> employeeIds) {
		this.employeeIds = employeeIds;
	}
	
}
