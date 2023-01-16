package com.csipl.hrms.model.payroll;


import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the OneTimeEarningDeduction database table.
 * 
 */
@Entity
@NamedQuery(name="OneTimeEarningDeduction.findAll", query="SELECT o FROM OneTimeEarningDeduction o")
public class OneTimeEarningDeduction implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="Id")
	private Long id;

	private BigDecimal amount;

	private Long companyId;

	@Temporal(TemporalType.DATE)
	private Date dateCreated;

	private String earningDeductionMonth;

	private Long employeeId;

	private String isEarningDeduction;

	private Long payHeadId;

	private String remarks;

	private String type;

	@Temporal(TemporalType.DATE)
	private Date updateDate;

	private Long updateId;

	private Long userId;

	public OneTimeEarningDeduction() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public BigDecimal getAmount() {
		return this.amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
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

	public String getEarningDeductionMonth() {
		return this.earningDeductionMonth;
	}

	public void setEarningDeductionMonth(String earningDeductionMonth) {
		this.earningDeductionMonth = earningDeductionMonth;
	}

	public Long getEmployeeId() {
		return this.employeeId;
	}

	public void setEmployeeId(Long employeeId) {
		this.employeeId = employeeId;
	}

	public String getIsEarningDeduction() {
		return this.isEarningDeduction;
	}

	public void setIsEarningDeduction(String isEarningDeduction) {
		this.isEarningDeduction = isEarningDeduction;
	}

	public Long getPayHeadId() {
		return this.payHeadId;
	}

	public void setPayHeadId(Long payHeadId) {
		this.payHeadId = payHeadId;
	}

	public String getRemarks() {
		return this.remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Date getUpdateDate() {
		return this.updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public Long getUpdateId() {
		return this.updateId;
	}

	public void setUpdateId(Long updateId) {
		this.updateId = updateId;
	}

	public Long getUserId() {
		return this.userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

}
