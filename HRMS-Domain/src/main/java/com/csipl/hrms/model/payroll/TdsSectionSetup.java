package com.csipl.hrms.model.payroll;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

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


/**
 * The persistent class for the TdsSectionSetup database table.
 * 
 */
@Entity
@NamedQuery(name="TdsSectionSetup.findAll", query="SELECT t FROM TdsSectionSetup t")
public class TdsSectionSetup implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long tdsSectionId;

	private String activeStatus;

	@Temporal(TemporalType.TIMESTAMP)
	private Date dateCreated;

	@Temporal(TemporalType.TIMESTAMP)
	private Date dateUpdate;

	private BigDecimal maxLimit;;

	private String tdsSectionName;

	private Long userId;

	private Long userIdUpdate;

	//bi-directional many-to-one association to TdsGroupSetup
	@ManyToOne
	@JoinColumn(name="tdsGroupId")
	private TdsGroupSetup tdsGroupSetup;

	//bi-directional many-to-one association to TdsTransaction
	@OneToMany(mappedBy="tdsSectionSetup")
	private List<TdsTransaction> tdsTransactions;

	public TdsSectionSetup() {
	}

	public Long getTdsSectionId() {
		return this.tdsSectionId;
	}

	public void setTdsSectionId(Long tdsSectionId) {
		this.tdsSectionId = tdsSectionId;
	}

	public String getActiveStatus() {
		return this.activeStatus;
	}

	public void setActiveStatus(String activeStatus) {
		this.activeStatus = activeStatus;
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



	public String getTdsSectionName() {
		return this.tdsSectionName;
	}

	public void setTdsSectionName(String tdsSectionName) {
		this.tdsSectionName = tdsSectionName;
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

	public TdsGroupSetup getTdsGroupSetup() {
		return this.tdsGroupSetup;
	}

	public void setTdsGroupSetup(TdsGroupSetup tdsGroupSetup) {
		this.tdsGroupSetup = tdsGroupSetup;
	}

	public List<TdsTransaction> getTdsTransactions() {
		return this.tdsTransactions;
	}

	public void setTdsTransactions(List<TdsTransaction> tdsTransactions) {
		this.tdsTransactions = tdsTransactions;
	}

	public TdsTransaction addTdsTransaction(TdsTransaction tdsTransaction) {
		getTdsTransactions().add(tdsTransaction);
		tdsTransaction.setTdsSectionSetup(this);

		return tdsTransaction;
	}

	public TdsTransaction removeTdsTransaction(TdsTransaction tdsTransaction) {
		getTdsTransactions().remove(tdsTransaction);
		tdsTransaction.setTdsSectionSetup(null);

		return tdsTransaction;
	}

	public BigDecimal getMaxLimit() {
		return maxLimit;
	}

	public void setMaxLimit(BigDecimal maxLimit) {
		this.maxLimit = maxLimit;
	}

}