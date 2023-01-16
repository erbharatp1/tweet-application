package com.csipl.hrms.dto.payroll;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.csipl.hrms.model.payroll.EsiCycle;


public class EsiDTO {
	private Long esiId;
	private BigDecimal employeePer;

	private BigDecimal employerPer;
	private BigDecimal maxGrossLimit;
	private Date effectiveDate;
	private Date effectiveEndDate;
	private String activeStatus;
	private Long userId;
	private Date dateCreated;
	private Long companyId;
	private Long userIdUpdate;
	private List<EsiCycleDTO> esiCycleDto;
	private List<EsiCycle> esiCycleDto2;
	private Long financialYearId;
	public EsiDTO() {
	
	}

	public EsiDTO(Long esiId, BigDecimal employeePer, BigDecimal employerPer, BigDecimal maxGrossLimit,
			Date effectiveDate, Date effectiveEndDate, String activeStatus, Long userId, Date dateCreated,
			Long companyId, Long userIdUpdate,  List<EsiCycle> esiCycleDto2) {
		
		this.esiId = esiId;
		this.employeePer = employeePer;
		this.employerPer = employerPer;
		this.maxGrossLimit = maxGrossLimit;
		this.effectiveDate = effectiveDate;
		this.effectiveEndDate = effectiveEndDate;
		this.activeStatus = activeStatus;
		this.userId = userId;
		this.dateCreated = dateCreated;
		this.companyId = companyId;
		this.userIdUpdate = userIdUpdate;
		//modal
		this.esiCycleDto2 = esiCycleDto2;
	}
	
	public Long getUserIdUpdate() {
		return userIdUpdate;
	}
	public void setUserIdUpdate(Long userIdUpdate) {
		this.userIdUpdate = userIdUpdate;
	}
	public Long getCompanyId() {
		return companyId;
	}
	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}
	public Long getEsiId() {
		return esiId;
	}
	public void setEsiId(Long esiId) {
		this.esiId = esiId;
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
	public BigDecimal getMaxGrossLimit() {
		return maxGrossLimit;
	}
	public void setMaxGrossLimit(BigDecimal maxGrossLimit) {
		this.maxGrossLimit = maxGrossLimit;
	}
	public Date getEffectiveDate() {
		return effectiveDate;
	}
	public void setEffectiveDate(Date effectiveDate) {
		this.effectiveDate = effectiveDate;
	}
	public String getActiveStatus() {
		return activeStatus;
	}
	public void setActiveStatus(String activeStatus) {
		this.activeStatus = activeStatus;
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
	public List<EsiCycleDTO> getEsiCycleDto() {
		return esiCycleDto;
	}
	public void setEsiCycleDto(List<EsiCycleDTO> esiCycleDto) {
		this.esiCycleDto = esiCycleDto;
	}
	public Date getEffectiveEndDate() {
		return effectiveEndDate;
	}
	public void setEffectiveEndDate(Date effectiveEndDate) {
		this.effectiveEndDate = effectiveEndDate;
	}

	public List<EsiCycle> getEsiCycleDto2() {
		return esiCycleDto2;
	}

	public void setEsiCycleDto2(List<EsiCycle> esiCycleDto2) {
		this.esiCycleDto2 = esiCycleDto2;
	}

	public Long getFinancialYearId() {
		return financialYearId;
	}

	public void setFinancialYearId(Long financialYearId) {
		this.financialYearId = financialYearId;
	}
		

}
