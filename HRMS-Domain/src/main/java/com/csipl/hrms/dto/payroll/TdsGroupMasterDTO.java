package com.csipl.hrms.dto.payroll;

import java.util.List;

import com.csipl.hrms.model.payroll.TdsGroupSetup;


public class TdsGroupMasterDTO{
	private Long tdsGroupMasterId;
	private Long companyId;
	private String isSubGroupLimit;
	private String tdsGroupName;
	private Long userId;
	private List<TdsGroupSetupDTO> tdsGroupSetups;


	public Long getTdsGroupMasterId() {
		return this.tdsGroupMasterId;
	}

	public void setTdsGroupMasterId(Long tdsGroupMasterId) {
		this.tdsGroupMasterId = tdsGroupMasterId;
	}

	public Long getCompanyId() {
		return this.companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	public String getIsSubGroupLimit() {
		return this.isSubGroupLimit;
	}

	public void setIsSubGroupLimit(String isSubGroupLimit) {
		this.isSubGroupLimit = isSubGroupLimit;
	}

	public String getTdsGroupName() {
		return this.tdsGroupName;
	}

	public void setTdsGroupName(String tdsGroupName) {
		this.tdsGroupName = tdsGroupName;
	}

	public Long getUserId() {
		return this.userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public List<TdsGroupSetupDTO> getTdsGroupSetups() {
		return tdsGroupSetups;
	}

	public void setTdsGroupSetups(List<TdsGroupSetupDTO> tdsGroupSetups) {
		this.tdsGroupSetups = tdsGroupSetups;
	}

	


}