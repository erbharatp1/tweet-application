package com.csipl.hrms.dto.payroll;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.csipl.hrms.model.payroll.LabourWelfareFundInfo;



public class LabourWelfareFundDTO   {
	private Long labourWelfareFundHeadId;
	private String activeStatus;
	private String allowModi;
	private Long companyId;	 
	private Date dateCreated;
	private Date dateUpdate;
	private Date effectiveEndDate;
	private Date effectiveStartDate;
	private Long groupId;
	private String   stateName;;
	private BigDecimal limitAmount;
	private BigDecimal perMonthAmount;
	private Long stateId;
	private Long userId;
	private Long userIdUpdate;
	private List<LabourWelfareFundInfoDTO> LabourWelfareFundInfos;
	private List<LabourWelfareFundInfo> labourWelfareFundInfos2;
	private Long financialYearId;

	public LabourWelfareFundDTO(Long labourWelfareFundHeadId, String activeStatus, String allowModi, Long companyId,
			Date dateCreated, Date dateUpdate, Date effectiveEndDate, Date effectiveStartDate, Long groupId,
			String stateName, BigDecimal limitAmount, BigDecimal perMonthAmount, Long stateId, Long userId,
			Long userIdUpdate, List<LabourWelfareFundInfo> labourWelfareFundInfos2) {
		
		this.labourWelfareFundHeadId = labourWelfareFundHeadId;
		this.activeStatus = activeStatus;
		this.allowModi = allowModi;
		this.companyId = companyId;
		this.dateCreated = dateCreated;
		this.dateUpdate = dateUpdate;
		this.effectiveEndDate = effectiveEndDate;
		this.effectiveStartDate = effectiveStartDate;
		this.groupId = groupId;
		this.stateName = stateName;
		this.limitAmount = limitAmount;
		this.perMonthAmount = perMonthAmount;
		this.stateId = stateId;
		this.userId = userId;
		this.userIdUpdate = userIdUpdate;
		this.labourWelfareFundInfos2 = labourWelfareFundInfos2;
	}

	public String getStateName() {
		return stateName;
	}

	public void setStateName(String stateName) {
		this.stateName = stateName;
	}

	public List<LabourWelfareFundInfoDTO> getLabourWelfareFundInfos() {
		return LabourWelfareFundInfos;
	}

	public void setLabourWelfareFundInfos(List<LabourWelfareFundInfoDTO> labourWelfareFundInfos) {
		LabourWelfareFundInfos = labourWelfareFundInfos;
	}

	public LabourWelfareFundDTO() {
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

	public Long getStateId() {
		return this.stateId;
	}

	public void setStateId(Long stateId) {
		this.stateId = stateId;
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

	public List<LabourWelfareFundInfo> getLabourWelfareFundInfos2() {
		return labourWelfareFundInfos2;
	}

	public void setLabourWelfareFundInfos2(List<LabourWelfareFundInfo> labourWelfareFundInfos2) {
		this.labourWelfareFundInfos2 = labourWelfareFundInfos2;
	}

	public Long getFinancialYearId() {
		return financialYearId;
	}

	public void setFinancialYearId(Long financialYearId) {
		this.financialYearId = financialYearId;
	}

}