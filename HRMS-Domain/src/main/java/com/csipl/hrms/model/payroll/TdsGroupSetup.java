package com.csipl.hrms.model.payroll;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.csipl.hrms.model.payrollprocess.FinancialYear;


/**
 * The persistent class for the TdsGroupSetup database table.
 * 
 */
@Entity
@NamedQuery(name="TdsGroupSetup.findAll", query="SELECT t FROM TdsGroupSetup t")
public class TdsGroupSetup implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long tdsGroupId;

	private String activeStatus;

	private Long companyId;

	@Temporal(TemporalType.TIMESTAMP)
	private Date dateCreated;

	@Temporal(TemporalType.TIMESTAMP)
	private Date dateUpdate;

	private BigDecimal maxLimit;

	private Long userId;

	private Long userIdUpdate;

	//bi-directional many-to-one association to FinancialYear
	@ManyToOne
	@JoinColumn(name="financialYearId")
	private FinancialYear financialYear;

	//bi-directional many-to-one association to TdsGroupMaster
	@ManyToOne
	@JoinColumn(name="tdsGroupMasterId")
	private TdsGroupMaster tdsGroupMaster;

	//bi-directional many-to-one association to TdsSectionSetup
	@OneToMany(mappedBy="tdsGroupSetup",cascade = CascadeType.ALL)
	private List<TdsSectionSetup> tdsSectionSetups;

	//bi-directional many-to-one association to TdsTransaction
	@OneToMany(mappedBy="tdsGroupSetup")
	private List<TdsTransaction> tdsTransactions;

	public TdsGroupSetup() {
	}

	public Long getTdsGroupId() {
		return this.tdsGroupId;
	}

	public void setTdsGroupId(Long tdsGroupId) {
		this.tdsGroupId = tdsGroupId;
	}

	public String getActiveStatus() {
		return this.activeStatus;
	}

	public void setActiveStatus(String activeStatus) {
		this.activeStatus = activeStatus;
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

	public Date getDateUpdate() {
		return this.dateUpdate;
	}

	public void setDateUpdate(Date dateUpdate) {
		this.dateUpdate = dateUpdate;
	}

	public BigDecimal getMaxLimit() {
		return this.maxLimit;
	}

	public void setMaxLimit(BigDecimal maxLimit) {
		this.maxLimit = maxLimit;
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

	public FinancialYear getFinancialYear() {
		return this.financialYear;
	}

	public void setFinancialYear(FinancialYear financialYear) {
		this.financialYear = financialYear;
	}

	public TdsGroupMaster getTdsGroupMaster() {
		return this.tdsGroupMaster;
	}

	public void setTdsGroupMaster(TdsGroupMaster tdsGroupMaster) {
		this.tdsGroupMaster = tdsGroupMaster;
	}

	public List<TdsSectionSetup> getTdsSectionSetups() {
		return this.tdsSectionSetups;
	}

	public void setTdsSectionSetups(List<TdsSectionSetup> tdsSectionSetups) {
		this.tdsSectionSetups = tdsSectionSetups;
	}

	public TdsSectionSetup addTdsSectionSetup(TdsSectionSetup tdsSectionSetup) {
		getTdsSectionSetups().add(tdsSectionSetup);
		tdsSectionSetup.setTdsGroupSetup(this);

		return tdsSectionSetup;
	}

	public TdsSectionSetup removeTdsSectionSetup(TdsSectionSetup tdsSectionSetup) {
		getTdsSectionSetups().remove(tdsSectionSetup);
		tdsSectionSetup.setTdsGroupSetup(null);

		return tdsSectionSetup;
	}


	public List<TdsTransaction> getTdsTransactions() {
		return this.tdsTransactions;
	}

	public void setTdsTransactions(List<TdsTransaction> tdsTransactions) {
		this.tdsTransactions = tdsTransactions;
	}

	public TdsTransaction addTdsTransaction(TdsTransaction tdsTransaction) {
		getTdsTransactions().add(tdsTransaction);
		tdsTransaction.setTdsGroupSetup(this);

		return tdsTransaction;
	}

	public TdsTransaction removeTdsTransaction(TdsTransaction tdsTransaction) {
		getTdsTransactions().remove(tdsTransaction);
		tdsTransaction.setTdsGroupSetup(null);

		return tdsTransaction;

	}

}