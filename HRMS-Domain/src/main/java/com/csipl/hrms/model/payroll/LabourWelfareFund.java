package com.csipl.hrms.model.payroll;

import java.io.Serializable;
import javax.persistence.*;

import com.csipl.hrms.model.common.State;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the LabourWelfareFund database table.
 * 
 */
@Entity
@NamedQuery(name="LabourWelfareFund.findAll", query="SELECT l FROM LabourWelfareFund l")
public class LabourWelfareFund implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long labourWelfareFundHeadId;

	private String activeStatus;

	private String allowModi;

	private Long companyId;

	@Temporal(TemporalType.TIMESTAMP)
	private Date dateCreated;

	@Temporal(TemporalType.TIMESTAMP)
	private Date dateUpdate;

	@Temporal(TemporalType.DATE)
	private Date effectiveEndDate;

	@Temporal(TemporalType.DATE)
	private Date effectiveStartDate;

	private Long groupId;

	private BigDecimal limitAmount;

	private BigDecimal perMonthAmount;

	private Long userId;
	private String isApplicable;
	
	public String getIsApplicable() {
		return isApplicable;
	}

	public void setIsApplicable(String isApplicable) {
		this.isApplicable = isApplicable;
	}

	private Long userIdUpdate;
	@ManyToOne
	@JoinColumn(name="stateId")
	private State state;

	//bi-directional many-to-one association to LabourWelfareFundInfo
	@OneToMany(mappedBy="labourWelfareFund", cascade = CascadeType.ALL )
	private List<LabourWelfareFundInfo> labourWelfareFundInfos;

	public LabourWelfareFund() {
	}

	public Long getLabourWelfareFundHeadId() {
		return this.labourWelfareFundHeadId;
	}

	public void setLabourWelfareFundHeadId(Long labourWelfareFundHeadId) {
		this.labourWelfareFundHeadId = labourWelfareFundHeadId;
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

	public Date getEffectiveEndDate() {
		return this.effectiveEndDate;
	}

	public void setEffectiveEndDate(Date effectiveEndDate) {
		this.effectiveEndDate = effectiveEndDate;
	}

	public Date getEffectiveStartDate() {
		return this.effectiveStartDate;
	}

	public void setEffectiveStartDate(Date effectiveStartDate) {
		this.effectiveStartDate = effectiveStartDate;
	}

	public Long getGroupId() {
		return this.groupId;
	}

	public void setGroupId(Long groupId) {
		this.groupId = groupId;
	}

	public BigDecimal getLimitAmount() {
		return this.limitAmount;
	}

	public void setLimitAmount(BigDecimal limitAmount) {
		this.limitAmount = limitAmount;
	}

	public BigDecimal getPerMonthAmount() {
		return this.perMonthAmount;
	}

	public void setPerMonthAmount(BigDecimal perMonthAmount) {
		this.perMonthAmount = perMonthAmount;
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

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}

	public List<LabourWelfareFundInfo> getLabourWelfareFundInfos() {
		return labourWelfareFundInfos;
	}

	public void setLabourWelfareFundInfos(List<LabourWelfareFundInfo> labourWelfareFundInfos) {
		this.labourWelfareFundInfos = labourWelfareFundInfos;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}