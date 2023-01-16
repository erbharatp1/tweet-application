package com.csipl.hrms.dto.payroll;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.csipl.hrms.model.payroll.TdsGroupSetup;
import com.csipl.hrms.model.payroll.TdsSectionSetup;

public class TdsGroupSetupDTO {

	private Long tdsGroupId;
	private String activeStatus;
	private Long companyId;
	private Date dateCreated;
	private Date dateUpdate;

	private BigDecimal maxLimit;
	
	private Long userId;
	private Long userIdUpdate;
	private String financialYearName;
	private Long financialYearId;

	private Long tdsGroupMasterId;
	private TdsGroupSetup tdsGroupSetup;

	private List<TdsSectionSetupDTO> tdsSectionSetupDTO;
	private String tdsGroupName;

	

	public Long getTdsGroupId() {
		return tdsGroupId;
	}

	public void setTdsGroupId(Long tdsGroupId) {
		this.tdsGroupId = tdsGroupId;
	}

	public String getActiveStatus() {
		return activeStatus;
	}

	public void setActiveStatus(String activeStatus) {
		this.activeStatus = activeStatus;
	}

	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
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

	

	public BigDecimal getMaxLimit() {
		return maxLimit;
	}

	public void setMaxLimit(BigDecimal maxLimit) {
		this.maxLimit = maxLimit;
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



	/**
	 * @return the financialYearId
	 */
	public Long getFinancialYearId() {
		return financialYearId;
	}

	/**
	 * @param financialYearId the financialYearId to set
	 */
	public void setFinancialYearId(Long financialYearId) {
		this.financialYearId = financialYearId;
	}

	public Long getTdsGroupMasterId() {
		return tdsGroupMasterId;
	}

	public void setTdsGroupMasterId(Long tdsGroupMasterId) {
		this.tdsGroupMasterId = tdsGroupMasterId;
	}

	/**
	 * @return the financialYearName
	 */
	public String getFinancialYearName() {
		return financialYearName;
	}

	/**
	 * @param financialYearName the financialYearName to set
	 */
	public void setFinancialYearName(String financialYearName) {
		this.financialYearName = financialYearName;
	}

	/**
	 * @return the tdsGroupName
	 */
	public String getTdsGroupName() {
		return tdsGroupName;
	}

	/**
	 * @param tdsGroupName the tdsGroupName to set
	 */
	public void setTdsGroupName(String tdsGroupName) {
		this.tdsGroupName = tdsGroupName;
	}

	/**
	 * @return the tdsGroupSetup
	 */
	public TdsGroupSetup getTdsGroupSetup() {
		return tdsGroupSetup;
	}

	/**
	 * @param tdsGroupSetup the tdsGroupSetup to set
	 */
	public void setTdsGroupSetup(TdsGroupSetup tdsGroupSetup) {
		this.tdsGroupSetup = tdsGroupSetup;
	}

	@Override
	public String toString() {
		return "TdsGroupSetupDTO [tdsGroupId=" + tdsGroupId + ", activeStatus=" + activeStatus + ", companyId="
				+ companyId + ", dateCreated=" + dateCreated + ", dateUpdate=" + dateUpdate + ", maxLimit=" + maxLimit
				+ ", userId=" + userId + ", userIdUpdate=" + userIdUpdate + ", financialYearName=" + financialYearName
				+ ", financialYearId=" + financialYearId + ", tdsGroupMasterId=" + tdsGroupMasterId + ", tdsGroupSetup="
				+ tdsGroupSetup + ", tdsGroupName=" + tdsGroupName + "]";
	}

	public List<TdsSectionSetupDTO> getTdsSectionSetupDTO() {
		return tdsSectionSetupDTO;
	}

	public void setTdsSectionSetupDTO(List<TdsSectionSetupDTO> tdsSectionSetupDTO) {
		this.tdsSectionSetupDTO = tdsSectionSetupDTO;
	}

	







}