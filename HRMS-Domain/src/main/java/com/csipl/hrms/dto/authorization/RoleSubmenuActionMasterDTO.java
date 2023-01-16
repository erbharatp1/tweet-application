package com.csipl.hrms.dto.authorization;



public class RoleSubmenuActionMasterDTO {
	private Long roleSubmenuActionId;

	private Long submenuActionId;
  private String roleDescription;
  private String uniqueCode;
	private Long roleId;
    private String status;
	public Long getRoleSubmenuActionId() {
		return roleSubmenuActionId;
	}

	public void setRoleSubmenuActionId(Long roleSubmenuActionId) {
		this.roleSubmenuActionId = roleSubmenuActionId;
	}

	public Long getSubmenuActionId() {
		return submenuActionId;
	}

	public void setSubmenuActionId(Long submenuActionId) {
		this.submenuActionId = submenuActionId;
	}

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getUniqueCode() {
		return uniqueCode;
	}

	public void setUniqueCode(String uniqueCode) {
		this.uniqueCode = uniqueCode;
	}

	public String getRoleDescription() {
		return roleDescription;
	}

	public void setRoleDescription(String roleDescription) {
		this.roleDescription = roleDescription;
	}
}
