package com.csipl.hrms.model.payroll;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;


/**
 * The persistent class for the FinalSettlementReport database table.
 * 
 */
@Entity
@NamedQuery(name="FinalSettlementReport.findAll", query="SELECT f FROM FinalSettlementReport f")
public class FinalSettlementReport implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long finalReportId;

	private Long employeeId;

	private String lastPaidMonth;

	private BigDecimal lastPaidSalary;

	private BigDecimal salaryPayable;

	private String salaryPayableMonth;

	public FinalSettlementReport() {
	}

	public Long getFinalReportId() {
		return this.finalReportId;
	}

	public void setFinalReportId(Long finalReportId) {
		this.finalReportId = finalReportId;
	}

	public Long getEmployeeId() {
		return this.employeeId;
	}

	public void setEmployeeId(Long employeeId) {
		this.employeeId = employeeId;
	}

	public String getLastPaidMonth() {
		return this.lastPaidMonth;
	}

	public void setLastPaidMonth(String lastPaidMonth) {
		this.lastPaidMonth = lastPaidMonth;
	}

	public BigDecimal getLastPaidSalary() {
		return this.lastPaidSalary;
	}

	public void setLastPaidSalary(BigDecimal lastPaidSalary) {
		this.lastPaidSalary = lastPaidSalary;
	}

	public BigDecimal getSalaryPayable() {
		return this.salaryPayable;
	}

	public void setSalaryPayable(BigDecimal salaryPayable) {
		this.salaryPayable = salaryPayable;
	}

	public String getSalaryPayableMonth() {
		return this.salaryPayableMonth;
	}

	public void setSalaryPayableMonth(String salaryPayableMonth) {
		this.salaryPayableMonth = salaryPayableMonth;
	}

}
