package com.csipl.hrms.model.payroll;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.*;

import com.csipl.hrms.model.BaseModelWithoutGroup;
import com.csipl.hrms.model.employee.Employee;
import com.csipl.hrms.model.payrollprocess.FinancialYear;

/**
 * The persistent class for the OtherIncome database table.
 * 
 */
@Entity
@NamedQuery(name="OtherIncome.findAll", query="SELECT o FROM OtherIncome o")
public class OtherIncome extends BaseModelWithoutGroup implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long otherIncomeId;

	private BigDecimal amount;

	private String description;
	
	private String status;
	
	private String approveStatus;
	
	private String tdsTransactionUpdateStatus;
	
	private String otherIncomeDoc;
	
	private String documentName;
	
	@ManyToOne
	@JoinColumn(name="financialYearId")
	private FinancialYear financialYear;
	
	/*private String financialYear;*/

	public String getStatus() {
		return status;
	}

	public void setFinancialYear(FinancialYear financialYear) {
		this.financialYear = financialYear;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}

	public String getApproveStatus() {
		return approveStatus;
	}

	public void setApproveStatus(String approveStatus) {
		this.approveStatus = approveStatus;
	}

	/*public String getFinancialYear() {
		return financialYear;
	}

	public void setFinancialYear(String financialYear) {
		this.financialYear = financialYear;
	}*/

	//bi-directional many-to-one association to Employee
	@ManyToOne
	@JoinColumn(name="employeeId")
	private Employee employee;

	public OtherIncome() {
	}

	public Long getOtherIncomeId() {
		return otherIncomeId;
	}

	public void setOtherIncomeId(Long otherIncomeId) {
		this.otherIncomeId = otherIncomeId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Employee getEmployee() {
		return this.employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public String getTdsTransactionUpdateStatus() {
		return tdsTransactionUpdateStatus;
	}

	public void setTdsTransactionUpdateStatus(String tdsTransactionUpdateStatus) {
		this.tdsTransactionUpdateStatus = tdsTransactionUpdateStatus;
	}

	public String getDocumentName() {
		return documentName;
	}

	public void setDocumentName(String documentName) {
		this.documentName = documentName;
	}

	public String getOtherIncomeDoc() {
		return otherIncomeDoc;
	}

	public void setOtherIncomeDoc(String otherIncomeDoc) {
		this.otherIncomeDoc = otherIncomeDoc;
	}

}
