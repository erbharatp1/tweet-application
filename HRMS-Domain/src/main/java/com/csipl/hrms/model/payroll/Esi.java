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

import com.csipl.hrms.model.common.Company;


/**
 * The persistent class for the Esi database table.
 * 
 */
@Entity
@NamedQuery(name="Esi.findAll", query="SELECT e FROM Esi e")
public class Esi implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long esiId;

	private String activeStatus;

	private String allowModi;

	@Temporal(TemporalType.TIMESTAMP)
	private Date dateCreated;

	@Temporal(TemporalType.TIMESTAMP)
	private Date dateUpdate;

	@Temporal(TemporalType.DATE)
	private Date effectiveDate;

	@Temporal(TemporalType.DATE)
	private Date effectiveEndDate;

	@Temporal(TemporalType.DATE)
	private Date effectiveStartDate;

	private BigDecimal employeePer;

	private BigDecimal employerPer;

	private String esiName;

	private Long groupId;

	private BigDecimal maxGrossLimit;

	private Long userId;

	private Long userIdUpdate;

	//bi-directional many-to-one association to Company
	@ManyToOne
	@JoinColumn(name="companyId")
	private Company company;

	//bi-directional many-to-one association to EsiCycle
	@OneToMany(mappedBy="esi",cascade = CascadeType.ALL)
	private List<EsiCycle> esiCycles;

	public Esi() {
	}

	public Long getEsiId() {
		return esiId;
	}

	public void setEsiId(Long esiId) {
		this.esiId = esiId;
	}

	public String getActiveStatus() {
		return activeStatus;
	}

	public void setActiveStatus(String activeStatus) {
		this.activeStatus = activeStatus;
	}

	public String getAllowModi() {
		return allowModi;
	}

	public void setAllowModi(String allowModi) {
		this.allowModi = allowModi;
	}

	public Date getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	public Date getDateUpdate() {
		return dateUpdate;
	}

	public void setDateUpdate(Date dateUpdate) {
		this.dateUpdate = dateUpdate;
	}

	public Date getEffectiveDate() {
		return effectiveDate;
	}

	public void setEffectiveDate(Date effectiveDate) {
		this.effectiveDate = effectiveDate;
	}

	public Date getEffectiveEndDate() {
		return effectiveEndDate;
	}

	public void setEffectiveEndDate(Date effectiveEndDate) {
		this.effectiveEndDate = effectiveEndDate;
	}

	public Date getEffectiveStartDate() {
		return effectiveStartDate;
	}

	public void setEffectiveStartDate(Date effectiveStartDate) {
		this.effectiveStartDate = effectiveStartDate;
	}

	public BigDecimal getEmployeePer() {
		return employeePer;
	}

	public void setEmployeePer(BigDecimal employeePer) {
		this.employeePer = employeePer;
	}

	public BigDecimal getEmployerPer() {
		return employerPer;
	}

	public void setEmployerPer(BigDecimal employerPer) {
		this.employerPer = employerPer;
	}

	public String getEsiName() {
		return esiName;
	}

	public void setEsiName(String esiName) {
		this.esiName = esiName;
	}

	public Long getGroupId() {
		return groupId;
	}

	public void setGroupId(Long groupId) {
		this.groupId = groupId;
	}

	public BigDecimal getMaxGrossLimit() {
		return maxGrossLimit;
	}

	public void setMaxGrossLimit(BigDecimal maxGrossLimit) {
		this.maxGrossLimit = maxGrossLimit;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getUserIdUpdate() {
		return userIdUpdate;
	}

	public void setUserIdUpdate(Long userIdUpdate) {
		this.userIdUpdate = userIdUpdate;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public List<EsiCycle> getEsiCycles() {
		return esiCycles;
	}

	public void setEsiCycles(List<EsiCycle> esiCycles) {
		this.esiCycles = esiCycles;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	

//	public Long getEsiId() {
//		return this.esiId;
//	}
//
//	public void setEsiId(Long esiId) {
//		this.esiId = esiId;
//	}
//
//	public String getActiveStatus() {
//		return this.activeStatus;
//	}
//
//	public void setActiveStatus(String activeStatus) {
//		this.activeStatus = activeStatus;
//	}
//
//	public String getAllowModi() {
//		return this.allowModi;
//	}
//
//	public void setAllowModi(String allowModi) {
//		this.allowModi = allowModi;
//	}
//
//	public Date getDateCreated() {
//		return this.dateCreated;
//	}
//
//	public void setDateCreated(Date dateCreated) {
//		this.dateCreated = dateCreated;
//	}
//
//	public Date getDateUpdate() {
//		return this.dateUpdate;
//	}
//
//	public void setDateUpdate(Date dateUpdate) {
//		this.dateUpdate = dateUpdate;
//	}
//
//	public Date getEffectiveDate() {
//		return this.effectiveDate;
//	}
//
//	public void setEffectiveDate(Date effectiveDate) {
//		this.effectiveDate = effectiveDate;
//	}
//
//	public Date getEffectiveEndDate() {
//		return this.effectiveEndDate;
//	}
//
//	public void setEffectiveEndDate(Date effectiveEndDate) {
//		this.effectiveEndDate = effectiveEndDate;
//	}
//
//	public Date getEffectiveStartDate() {
//		return this.effectiveStartDate;
//	}
//
//	public void setEffectiveStartDate(Date effectiveStartDate) {
//		this.effectiveStartDate = effectiveStartDate;
//	}
//
//	public BigDecimal getEmployeePer() {
//		return this.employeePer;
//	}
//
//	public void setEmployeePer(BigDecimal employeePer) {
//		this.employeePer = employeePer;
//	}
//
//	public BigDecimal getEmployerPer() {
//		return this.employerPer;
//	}
//
//	public void setEmployerPer(BigDecimal employerPer) {
//		this.employerPer = employerPer;
//	}
//
//	public String getEsiName() {
//		return this.esiName;
//	}
//
//	public void setEsiName(String esiName) {
//		this.esiName = esiName;
//	}
//
//	public Long getGroupId() {
//		return this.groupId;
//	}
//
//	public void setGroupId(Long groupId) {
//		this.groupId = groupId;
//	}
//
//	public BigDecimal getMaxGrossLimit() {
//		return this.maxGrossLimit;
//	}
//
//	public void setMaxGrossLimit(BigDecimal maxGrossLimit) {
//		this.maxGrossLimit = maxGrossLimit;
//	}
//
//	public Long getUserId() {
//		return this.userId;
//	}
//
//	public void setUserId(Long userId) {
//		this.userId = userId;
//	}
//
//	public Long getUserIdUpdate() {
//		return this.userIdUpdate;
//	}
//
//	public void setUserIdUpdate(Long userIdUpdate) {
//		this.userIdUpdate = userIdUpdate;
//	}
//
//	public Company getCompany() {
//		return this.company;
//	}
//
//	public void setCompany(Company company) {
//		this.company = company;
//	}
//
//	public List<EsiCycle> getEsiCycles() {
//		return this.esiCycles;
//	}
//
//	public void setEsiCycles(List<EsiCycle> esiCycles) {
//		this.esiCycles = esiCycles;
//	}
//
//	public EsiCycle addEsiCycle(EsiCycle esiCycle) {
//		getEsiCycles().add(esiCycle);
//		esiCycle.setEsi(this);
//
//		return esiCycle;
//	}
//
//	public EsiCycle removeEsiCycle(EsiCycle esiCycle) {
//		getEsiCycles().remove(esiCycle);
//		esiCycle.setEsi(null);
//
//		return esiCycle;
//	}

}