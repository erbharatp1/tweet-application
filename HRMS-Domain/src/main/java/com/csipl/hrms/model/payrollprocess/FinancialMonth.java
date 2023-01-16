package com.csipl.hrms.model.payrollprocess;
import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the FinancialMonth database table.
 * 
 */
@Entity
@NamedQuery(name="FinancialMonth.findAll", query="SELECT f FROM FinancialMonth f")
public class FinancialMonth implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long financialMonthId;

	private Long companyId;

	@Temporal(TemporalType.TIMESTAMP)
	private Date dateCreated;

	private String month;

	@Temporal(TemporalType.TIMESTAMP)
	private Date updatedDate;

	private Long userId;

	private Long userIdUpdate;

	public FinancialMonth() {
	}

	public Long getFinancialMonthId() {
		return this.financialMonthId;
	}

	public void setFinancialMonthId(Long financialMonthId) {
		this.financialMonthId = financialMonthId;
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

	public String getMonth() {
		return this.month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public Date getUpdatedDate() {
		return this.updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
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

}