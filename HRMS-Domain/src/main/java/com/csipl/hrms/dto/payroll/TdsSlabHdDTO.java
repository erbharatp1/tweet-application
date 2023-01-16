package com.csipl.hrms.dto.payroll;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.csipl.hrms.model.payroll.TdsSlabMaster;
import com.csipl.hrms.model.payrollprocess.FinancialYear;

public class TdsSlabHdDTO {
	private Long tdsSLabHdId;
	private Date dateEffective;

	private Long userId;
	private String tdsCategoryValue;
	private Date dateCreated;
	private Long companyId;
	private Long userIdUpdate;
	private Date dateUpdate;

	private String activeStatus;

	private String allowModi;

	private Date effectiveEndDate;

	private Date effectiveStartDate;

	private Long groupId;

	private Long finencialYearId;

	private Long tdsSlabMasterId;

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



	public Long getGroupId() {
		return groupId;
	}

	public void setGroupId(Long groupId) {
		this.groupId = groupId;
	}

	public Long getFinencialYearId() {
		return finencialYearId;
	}

	public void setFinencialYearId(Long finencialYearId) {
		this.finencialYearId = finencialYearId;
	}

	public Date getDateUpdate() {
		return dateUpdate;
	}

	public void setDateUpdate(Date dateUpdate) {
		this.dateUpdate = dateUpdate;
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

	private List<TdsSlabDTO> tdsSlabs = new ArrayList<TdsSlabDTO>();

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

	public Date getDateEffective() {
		return dateEffective;
	}

	public void setDateEffective(Date dateEffective) {
		this.dateEffective = dateEffective;
	}


	public List<TdsSlabDTO> getTdsSlabs() {
		return tdsSlabs;
	}

	public void setTdsSlabs(List<TdsSlabDTO> tdsSlabs) {
		this.tdsSlabs = tdsSlabs;
	}

	public Long getTdsSLabHdId() {
		return tdsSLabHdId;
	}

	public void setTdsSLabHdId(Long tdsSLabHdId) {
		this.tdsSLabHdId = tdsSLabHdId;
	}

	public String getTdsCategoryValue() {
		return tdsCategoryValue;
	}

	public void setTdsCategoryValue(String tdsCategoryValue) {
		this.tdsCategoryValue = tdsCategoryValue;
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

}
