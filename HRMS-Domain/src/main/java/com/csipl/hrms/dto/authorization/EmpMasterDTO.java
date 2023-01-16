package com.csipl.hrms.dto.authorization;

import java.util.List;

public class EmpMasterDTO {
	private Long roleId;
	private List<Long> userRolesSrNo;

	public EmpMasterDTO() {
		super();
	}

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	public List<Long> getUserRolesSrNo() {
		return userRolesSrNo;
	}

	public void setUserRolesSrNo(List<Long> userRolesSrNo) {
		this.userRolesSrNo = userRolesSrNo;
	}

	 
}
