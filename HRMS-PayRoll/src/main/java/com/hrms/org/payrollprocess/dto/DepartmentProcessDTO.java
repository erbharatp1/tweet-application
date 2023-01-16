package com.hrms.org.payrollprocess.dto;

import java.math.BigDecimal;

public class DepartmentProcessDTO {
	private Long depaertmentId;
	private String departmentName;
	private String processMonth;
	private String calanderDate;
	private int monthCount;
	private Long totalEmployee;
	private Long processCount;
	private Long remaining;

	private BigDecimal totalGross;
	private BigDecimal netPayableAmount;
	private BigDecimal totalCTC;
	private BigDecimal totalDeduction;
	public BigDecimal getTotalGross() {
		return totalGross;
	}

	public void setTotalGross(BigDecimal totalGross) {
		this.totalGross = totalGross;
	}

	public BigDecimal getNetPayableAmount() {
		return netPayableAmount;
	}

	public void setNetPayableAmount(BigDecimal netPayableAmount) {
		this.netPayableAmount = netPayableAmount;
	}

	public BigDecimal getTotalCTC() {
		return totalCTC;
	}

	public void setTotalCTC(BigDecimal totalCTC) {
		this.totalCTC = totalCTC;
	}

	public BigDecimal getTotalDeduction() {
		return totalDeduction;
	}

	public void setTotalDeduction(BigDecimal totalDeduction) {
		this.totalDeduction = totalDeduction;
	}

	public Long getDepaertmentId() {
		return depaertmentId;
	}

	public void setDepaertmentId(Long depaertmentId) {
		this.depaertmentId = depaertmentId;
	}

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	public String getProcessMonth() {
		return processMonth;
	}

	public void setProcessMonth(String processMonth) {
		this.processMonth = processMonth;
	}

	 

	public String getCalanderDate() {
		return calanderDate;
	}

	public void setCalanderDate(String calanderDate) {
		this.calanderDate = calanderDate;
	}

	public int getMonthCount() {
		return monthCount;
	}

	public void setMonthCount(int monthCount) {
		this.monthCount = monthCount;
	}

	public Long getTotalEmployee() {
		return totalEmployee;
	}

	public void setTotalEmployee(Long totalEmployee) {
		this.totalEmployee = totalEmployee;
	}

	public Long getProcessCount() {
		return processCount;
	}

	public void setProcessCount(Long processCount) {
		this.processCount = processCount;
	}

	public Long getRemaining() {
		return remaining;
	}

	public void setRemaining(Long remaining) {
		this.remaining = remaining;
	}

	@Override
	public String toString() {
		return "DepartmentProcessDTO [depaertmentId=" + depaertmentId + ", departmentName=" + departmentName
				+ ", processMonth=" + processMonth + ", calanderDate=" + calanderDate + ", monthCount=" + monthCount
				+ ", totalEmployee=" + totalEmployee + ", processCount=" + processCount + ", remaining=" + remaining
				+ "]";
	}

}
