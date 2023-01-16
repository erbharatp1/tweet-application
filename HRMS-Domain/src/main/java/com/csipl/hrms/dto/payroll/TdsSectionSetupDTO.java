package com.csipl.hrms.dto.payroll;

import java.math.BigDecimal;
import java.util.Date;

import com.csipl.hrms.model.payroll.TdsGroupSetup;



public class TdsSectionSetupDTO {
	
	private Long tdsSectionId;

	private Date dateCreated;

	private Date dateUpdate;

	

	private String tdsSectionName;
private Long tdsGroupMasterId;
	private Long userId;
	private String activeStatus;
	private Long userIdUpdate;

	private TdsGroupSetup tdsGroupSetup;
	private BigDecimal maxLimit;
	private Long companyId;
	private String tdsGroupName;
	private Long tdsGroupId;
	public Long getTdsSectionId() {
		return this.tdsSectionId;
	}

	public void setTdsSectionId(Long tdsSectionId) {
		this.tdsSectionId = tdsSectionId;
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

	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	public String getTdsGroupName() {
		return tdsGroupName;
	}

	public void setTdsGroupName(String tdsGroupName) {
		this.tdsGroupName = tdsGroupName;
	}

	public Long getTdsGroupId() {
		return tdsGroupId;
	}

	public void setTdsGroupId(Long tdsGroupId) {
		this.tdsGroupId = tdsGroupId;
	}

	/**
	 * @return the activeStatus
	 */
	public String getActiveStatus() {
		return activeStatus;
	}

	/**
	 * @param activeStatus the activeStatus to set
	 */
	public void setActiveStatus(String activeStatus) {
		this.activeStatus = activeStatus;
	}

	

	public Long getTdsGroupMasterId() {
		return tdsGroupMasterId;
	}

	public void setTdsGroupMasterId(Long tdsGroupMasterId) {
		this.tdsGroupMasterId = tdsGroupMasterId;
	}

	public BigDecimal getMaxLimit() {
		return maxLimit;
	}

	public void setMaxLimit(BigDecimal maxLimit) {
		this.maxLimit = maxLimit;
	}

}