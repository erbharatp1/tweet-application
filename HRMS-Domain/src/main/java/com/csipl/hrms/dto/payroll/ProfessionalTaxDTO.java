package com.csipl.hrms.dto.payroll;

import java.util.Date;
import java.util.List;

import com.csipl.hrms.model.payroll.ProfessionalTaxInfo;

  public class ProfessionalTaxDTO {
	 private Long professionalHeadId;
	
	private String  stateName;
	private Long stateId;
	
	private Long userId;
	private Date dateCreated;
	private String activeStatus;
	private Date effectiveStartDate;
	private Date effectiveEndDate;
	private Long companyId;
	private Long userIdUpdate;
	private Long groupId;
	private Long financialYearId;
	private List<ProfessionalTaxInfoDTO> professionalTaxInfos;
	private List<ProfessionalTaxInfo> professionalTaxInfos2;
	
	
	public ProfessionalTaxDTO(Long professionalHeadId, String stateName, Long stateId, Long userId, Date dateCreated,
			String activeStatus, Date effectiveStartDate, Date effectiveEndDate, Long companyId, Long userIdUpdate,
			 List<ProfessionalTaxInfo> professionalTaxInfos2) {
		this.professionalHeadId = professionalHeadId;
		this.stateName = stateName;
		this.stateId = stateId;
		this.userId = userId;
		this.dateCreated = dateCreated;
		this.activeStatus = activeStatus;
		this.effectiveStartDate = effectiveStartDate;
		this.effectiveEndDate = effectiveEndDate;
		this.companyId = companyId;
		this.userIdUpdate = userIdUpdate;
		this.professionalTaxInfos2 = professionalTaxInfos2;
	}
	
	public ProfessionalTaxDTO() {

	}

	public Long getGroupId() {
		return groupId;
	}
	public void setGroupId(Long groupId) {
		this.groupId = groupId;
	}
	public Long getCompanyId() {
		return companyId;
	}
	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}
	public Long getUserIdUpdate() {
		return userIdUpdate;
	}
	public void setUserIdUpdate(Long userIdUpdate) {
		this.userIdUpdate = userIdUpdate;
	}
	
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public Date getDateCreated() {
		return dateCreated;
	}
	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}
	public String getActiveStatus() {
		return activeStatus;
	}
	public void setActiveStatus(String activeStatus) {
		this.activeStatus = activeStatus;
	}
	public Long getProfessionalHeadId() {
		return professionalHeadId;
	}
	public void setProfessionalHeadId(Long professionalHeadId) {
		this.professionalHeadId = professionalHeadId;
	}
	
	public String getStateName() {
		return stateName;
	}
	public void setStateName(String stateName) {
		this.stateName = stateName;
	}
	public Long getStateId() {
		return stateId;
	}
	public void setStateId(Long stateId) {
		this.stateId = stateId;
	}
	
	
	public Date getEffectiveEndDate() {
		return effectiveEndDate;
	}
	public void setEffectiveEndDate(Date effectiveEndDate) {
		this.effectiveEndDate = effectiveEndDate;
	}
	public List<ProfessionalTaxInfoDTO> getProfessionalTaxInfos() {
		return professionalTaxInfos;
	}
	public void setProfessionalTaxInfos(List<ProfessionalTaxInfoDTO> professionalTaxInfos) {
		this.professionalTaxInfos = professionalTaxInfos;
	}
	public Date getEffectiveStartDate() {
		return effectiveStartDate;
	}
	public void setEffectiveStartDate(Date effectiveStartDate) {
		this.effectiveStartDate = effectiveStartDate;
	}
	public Long getFinancialYearId() {
		return financialYearId;
	}
	public void setFinancialYearId(Long financialYearId) {
		this.financialYearId = financialYearId;
	}

	public List<ProfessionalTaxInfo> getProfessionalTaxInfos2() {
		return professionalTaxInfos2;
	}

	public void setProfessionalTaxInfos2(List<ProfessionalTaxInfo> professionalTaxInfos2) {
		this.professionalTaxInfos2 = professionalTaxInfos2;
	}
	 
	  
}
