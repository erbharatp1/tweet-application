package com.csipl.hrms.model.payroll;

import java.io.Serializable;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the LabourWelfareFundInfo database table.
 * 
 */
@Entity
@NamedQuery(name="LabourWelfareFundInfo.findAll", query="SELECT l FROM LabourWelfareFundInfo l")
public class LabourWelfareFundInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long labourWelfareFundInfoId;

	private String activeStatus;

	private String allowModi;

	@Temporal(TemporalType.DATE)
	private Date dateCreated;

	@Temporal(TemporalType.DATE)
	private Date dateUpdate;

	private Long gradeId;

	private BigDecimal limitFrom;

	private BigDecimal limitTo;

	private Long userId;

	private Long userIdUpdate;

	private BigDecimal welFareAmount;
	private BigDecimal	employerWelFareAmount;
	
	//bi-directional many-to-one association to ProfessionalTax
		@ManyToOne
		@JoinColumn(name="labourWelfareFundId")
		private LabourWelfareFund labourWelfareFund;

	public LabourWelfareFundInfo() {
	}

	public Long getLabourWelfareFundInfoId() {
		return this.labourWelfareFundInfoId;
	}

	public void setLabourWelfareFundInfoId(Long labourWelfareFundInfoId) {
		this.labourWelfareFundInfoId = labourWelfareFundInfoId;
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

	public Long getGradeId() {
		return this.gradeId;
	}

	public void setGradeId(Long gradeId) {
		this.gradeId = gradeId;
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

	public BigDecimal getWelFareAmount() {
		return this.welFareAmount;
	}

	public void setWelFareAmount(BigDecimal welFareAmount) {
		this.welFareAmount = welFareAmount;
	}

	@JsonIgnore
	public LabourWelfareFund getLabourWelfareFund() {
		return labourWelfareFund;
	}

	public void setLabourWelfareFund(LabourWelfareFund labourWelfareFund) {
		this.labourWelfareFund = labourWelfareFund;
	}

	public BigDecimal getEmployerWelFareAmount() {
		return employerWelFareAmount;
	}

	public void setEmployerWelFareAmount(BigDecimal employerWelFareAmount) {
		this.employerWelFareAmount = employerWelFareAmount;
	}

}