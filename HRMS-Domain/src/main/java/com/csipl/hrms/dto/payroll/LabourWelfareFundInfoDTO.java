package com.csipl.hrms.dto.payroll;

import java.math.BigDecimal;
import java.util.Date;

 
public class LabourWelfareFundInfoDTO {
private Long labourWelfareFundInfoId;

	private String activeStatus;

	private String allowModi;
 
	private Date dateCreated;

	private Date dateUpdate;

	private Long gradeId;
	private String gradeName;
	private Long labourWelfareFundId;

	private BigDecimal limitFrom;

	private BigDecimal limitTo;

	private Long userId;

	private Long userIdUpdate;

	private BigDecimal welFareAmount;
	private BigDecimal	employerWelFareAmount;
	public String getGradeName() {
		return gradeName;
	}

	public void setGradeName(String gradeName) {
		this.gradeName = gradeName;
	}

	public LabourWelfareFundInfoDTO() {
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

	public Long getLabourWelfareFundId() {
		return this.labourWelfareFundId;
	}

	public void setLabourWelfareFundId(Long labourWelfareFundId) {
		this.labourWelfareFundId = labourWelfareFundId;
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

	public BigDecimal getEmployerWelFareAmount() {
		return employerWelFareAmount;
	}

	public void setEmployerWelFareAmount(BigDecimal employerWelFareAmount) {
		this.employerWelFareAmount = employerWelFareAmount;
	}

}