package com.csipl.hrms.dto.authorization;

import java.util.Date;




public class SubMenuMasterDTO {
	private Long submenuId;

	
	private Date dateCreated;

	
	private Date dateUpdate;

	private String status;

	private String submenuName;

	private Long userId;

	private Long userIdUpdate;

	//bi-directional many-to-one association to MenuMaster
	
	private Long menuId;



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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getSubmenuName() {
		return submenuName;
	}

	public void setSubmenuName(String submenuName) {
		this.submenuName = submenuName;
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

	public Long getMenuId() {
		return menuId;
	}

	public void setMenuId(Long menuId) {
		this.menuId = menuId;
	}

	public Long getSubmenuId() {
		return submenuId;
	}

	public void setSubmenuId(Long submenuId) {
		this.submenuId = submenuId;
	}


}
