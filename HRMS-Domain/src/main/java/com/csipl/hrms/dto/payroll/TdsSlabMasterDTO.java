package com.csipl.hrms.dto.payroll;

import java.io.Serializable;
import java.util.List;

public class TdsSlabMasterDTO implements Serializable {

	private Long tdsSlabMasterId;
	private Long companyId;
	private String tdsCategory;
	private Long userId;

	/**
	 * 
	 */
	public TdsSlabMasterDTO() {
		super();
	}

	private List<TdsSlabHdDTO> tdsSlabHds;

	public Long getTdsSlabMasterId() {
		return tdsSlabMasterId;
	}

	public void setTdsSlabMasterId(Long tdsSlabMasterId) {
		this.tdsSlabMasterId = tdsSlabMasterId;
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

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public List<TdsSlabHdDTO> getTdsSlabHds() {
		return tdsSlabHds;
	}

	public void setTdsSlabHds(List<TdsSlabHdDTO> tdsSlabHds) {
		this.tdsSlabHds = tdsSlabHds;
	}

}