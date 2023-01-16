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

@Entity
@NamedQuery(name="TdsSlab.findAll", query="SELECT t FROM TdsSlab t")
public class TdsSlab implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long tdsSLabId;

	private String activeStatus;

	private String allowModi;

	@Temporal(TemporalType.TIMESTAMP)
	private Date dateCreated;

	@Temporal(TemporalType.TIMESTAMP)
	private Date dateUpdate;

	private BigDecimal limitFrom;

	private BigDecimal limitTo;

	private BigDecimal tdsPercentage;

	private Long userId;

	private Long userIdUpdate;

	private String tdsSlabPlanType;
	
	//bi-directional many-to-one association to TdsPayroll
	@OneToMany(mappedBy="tdsSlab")
	private List<TdsPayroll> tdsPayrolls;

	//bi-directional many-to-one association to TdsSlabHd
	@ManyToOne
	@JoinColumn(name="tdsSLabHdId" )
	private TdsSlabHd tdsSlabHd;

	public TdsSlab() {
	}

	public Long getTdsSLabId() {
		return this.tdsSLabId;
	}

	public void setTdsSLabId(Long tdsSLabId) {
		this.tdsSLabId = tdsSLabId;
	}

	public String getActiveStatus() {
		return this.activeStatus;
	}

	public void setActiveStatus(String activeStatus) {
		this.activeStatus = activeStatus;
	}

	public String getAllowModi() {
		return this.allowModi;
	}

	public void setAllowModi(String allowModi) {
		this.allowModi = allowModi;
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

	public BigDecimal getLimitFrom() {
		return this.limitFrom;
	}

	public void setLimitFrom(BigDecimal limitFrom) {
		this.limitFrom = limitFrom;
	}

	public BigDecimal getLimitTo() {
		return this.limitTo;
	}

	public void setLimitTo(BigDecimal limitTo) {
		this.limitTo = limitTo;
	}

	public BigDecimal getTdsPercentage() {
		return this.tdsPercentage;
	}

	public void setTdsPercentage(BigDecimal tdsPercentage) {
		this.tdsPercentage = tdsPercentage;
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

	public List<TdsPayroll> getTdsPayrolls() {
		return this.tdsPayrolls;
	}

	public void setTdsPayrolls(List<TdsPayroll> tdsPayrolls) {
		this.tdsPayrolls = tdsPayrolls;
	}

	public TdsPayroll addTdsPayroll(TdsPayroll tdsPayroll) {
		getTdsPayrolls().add(tdsPayroll);
		tdsPayroll.setTdsSlab(this);

		return tdsPayroll;
	}

	public TdsPayroll removeTdsPayroll(TdsPayroll tdsPayroll) {
		getTdsPayrolls().remove(tdsPayroll);
		tdsPayroll.setTdsSlab(null);

		return tdsPayroll;
	}

	public TdsSlabHd getTdsSlabHd() {
		return this.tdsSlabHd;
	}

	public void setTdsSlabHd(TdsSlabHd tdsSlabHd) {
		this.tdsSlabHd = tdsSlabHd;
	}

	public String getTdsSlabPlanType() {
		return tdsSlabPlanType;
	}

	public void setTdsSlabPlanType(String tdsSlabPlanType) {
		this.tdsSlabPlanType = tdsSlabPlanType;
	}

}