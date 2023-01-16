package com.csipl.hrms.model.authoriztion;


import java.io.Serializable;
import javax.persistence.*;

import java.util.Date;
import java.util.List;


/**
 * The persistent class for the SubmenuActionMaster database table.
 * 
 */
@Entity
@NamedQuery(name="SubmenuActionMaster.findAll", query="SELECT s FROM SubmenuActionMaster s")
public class SubmenuActionMaster implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long submenuActionId;

	private Date dateCreated;

	private String description;

	private String status;

	private String title;

	private String urlPath;

	private Long userId;
    private String uniqueCode;
	//bi-directional many-to-one association to RoleSubmenuActionMaster
	@OneToMany(mappedBy="submenuActionMaster")
	private List<RoleSubmenuActionMaster> roleSubmenuActionMasters;

	//bi-directional many-to-one association to SubMenuMaster
	@ManyToOne
	@JoinColumn(name="submenuId")
	private SubMenuMaster subMenuMaster;

	public SubmenuActionMaster() {
	}

	public Long getSubmenuActionId() {
		return this.submenuActionId;
	}

	public void setSubmenuActionId(Long submenuActionId) {
		this.submenuActionId = submenuActionId;
	}


	public Date getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getUrlPath() {
		return this.urlPath;
	}

	public void setUrlPath(String urlPath) {
		this.urlPath = urlPath;
	}

	public Long getUserId() {
		return this.userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public List<RoleSubmenuActionMaster> getRoleSubmenuActionMasters() {
		return this.roleSubmenuActionMasters;
	}

	public void setRoleSubmenuActionMasters(List<RoleSubmenuActionMaster> roleSubmenuActionMasters) {
		this.roleSubmenuActionMasters = roleSubmenuActionMasters;
	}

	public RoleSubmenuActionMaster addRoleSubmenuActionMaster(RoleSubmenuActionMaster roleSubmenuActionMaster) {
		getRoleSubmenuActionMasters().add(roleSubmenuActionMaster);
		roleSubmenuActionMaster.setSubmenuActionMaster(this);

		return roleSubmenuActionMaster;
	}

	public RoleSubmenuActionMaster removeRoleSubmenuActionMaster(RoleSubmenuActionMaster roleSubmenuActionMaster) {
		getRoleSubmenuActionMasters().remove(roleSubmenuActionMaster);
		roleSubmenuActionMaster.setSubmenuActionMaster(null);

		return roleSubmenuActionMaster;
	}

	public SubMenuMaster getSubMenuMaster() {
		return this.subMenuMaster;
	}

	public void setSubMenuMaster(SubMenuMaster subMenuMaster) {
		this.subMenuMaster = subMenuMaster;
	}

	public String getUniqueCode() {
		return uniqueCode;
	}

	public void setUniqueCode(String uniqueCode) {
		this.uniqueCode = uniqueCode;
	}

}