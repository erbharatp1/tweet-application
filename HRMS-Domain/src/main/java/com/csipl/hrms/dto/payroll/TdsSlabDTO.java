package com.csipl.hrms.dto.payroll;

import java.math.BigDecimal;
import java.util.Date;
 
public class TdsSlabDTO {

	private Long tdsSLabId;
	private BigDecimal limitFrom;
	private BigDecimal limitTo;
	private BigDecimal tdsPercentage;
	private Long userId;
	private Date dateCreated;
	private Long userIdUpdate;
	private Long companyId;
	
	private String tdsCategory;
	private Long tdsSLabHdId;
	private String activeStatus;
	private Long	tdsSlabMasterId;
	private String tdsSlabPlanType;
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

	public Long getTdsSLabId() {
		return tdsSLabId;
	}

	public void setTdsSLabId(Long tdsSLabId) {
		this.tdsSLabId = tdsSLabId;
	}

	public BigDecimal getLimitFrom() {
		return limitFrom;
	}

	public void setLimitFrom(BigDecimal limitFrom) {
		this.limitFrom = limitFrom;
	}

	public BigDecimal getLimitTo() {
		return limitTo;
	}

	public void setLimitTo(BigDecimal limitTo) {
		this.limitTo = limitTo;
	}

	public BigDecimal getTdsPercentage() {
		return tdsPercentage;
	}

	public void setTdsPercentage(BigDecimal tdsPercentage) {
		this.tdsPercentage = tdsPercentage;
	}

	

	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	public String getTdsCategory() {
		return tdsCategory;
	}

	public void setTdsCategory(String tdsCategory) {
		this.tdsCategory = tdsCategory;
	}

	public Long getTdsSLabHdId() {
		return tdsSLabHdId;
	}

	public void setTdsSLabHdId(Long tdsSLabHdId) {
		this.tdsSLabHdId = tdsSLabHdId;
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

	/**
	 * @return the tdsSlabMasterId
	 */
	public Long getTdsSlabMasterId() {
		return tdsSlabMasterId;
	}

	/**
	 * @param tdsSlabMasterId the tdsSlabMasterId to set
	 */
	public void setTdsSlabMasterId(Long tdsSlabMasterId) {
		this.tdsSlabMasterId = tdsSlabMasterId;
	}

	public String getTdsSlabPlanType() {
		return tdsSlabPlanType;
	}

	public void setTdsSlabPlanType(String tdsSlabPlanType) {
		this.tdsSlabPlanType = tdsSlabPlanType;
	}



	

}
