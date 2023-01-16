package com.csipl.hrms.dto.payroll;

import java.math.BigDecimal;

public class ArrearReportPayOutDTO {
	
	private BigDecimal payableDays;
	private BigDecimal payDays;
	private String ESICNumber;
	private BigDecimal netPayableAmount;
	private String name;
	private BigDecimal grossSalary;
	private  int totalEmployee;
	
	public BigDecimal getPayableDays() {
		return payableDays;
	}
	public void setPayableDays(BigDecimal payableDays) {
		this.payableDays = payableDays;
	}
	public BigDecimal getPayDays() {
		return payDays;
	}
	public void setPayDays(BigDecimal payDays) {
		this.payDays = payDays;
	}
	public String getESICNumber() {
		return ESICNumber;
	}
	public void setESICNumber(String eSICNumber) {
		ESICNumber = eSICNumber;
	}
	public BigDecimal getGrossSalary() {
		return grossSalary;
	}
	public void setGrossSalary(BigDecimal grossSalary) {
		this.grossSalary = grossSalary;
	}
	public int getTotalEmployee() {
		return totalEmployee;
	}
	public void setTotalEmployee(int totalEmployee) {
		this.totalEmployee = totalEmployee;
	}
	public BigDecimal getNetPayableAmount() {
		return netPayableAmount;
	}
	public void setNetPayableAmount(BigDecimal netPayableAmount) {
		this.netPayableAmount = netPayableAmount;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

}
