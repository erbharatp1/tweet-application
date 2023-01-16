package com.csipl.hrms.model.authoriztion;



import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the RoleSubmenuActionMaster database table.
 * 
 */
@Entity
@NamedQuery(name="RoleSubmenuActionMaster.findAll", query="SELECT r FROM RoleSubmenuActionMaster r")
public class RoleSubmenuActionMaster implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long roleSubmenuActionId;
	 private String status;
	//bi-directional many-to-one association to SubmenuActionMaster
	@ManyToOne
	@JoinColumn(name="submenuActionId")
	private SubmenuActionMaster submenuActionMaster;

	//bi-directional many-to-one association to RoleMaster
	@ManyToOne
	@JoinColumn(name="roleId")
	private RoleMaster roleMaster;

	public RoleSubmenuActionMaster() {
	}

	public Long getRoleSubmenuActionId() {
		return this.roleSubmenuActionId;
	}

	public void setRoleSubmenuActionId(Long roleSubmenuActionId) {
		this.roleSubmenuActionId = roleSubmenuActionId;
	}

	public SubmenuActionMaster getSubmenuActionMaster() {
		return this.submenuActionMaster;
	}

	public void setSubmenuActionMaster(SubmenuActionMaster submenuActionMaster) {
		this.submenuActionMaster = submenuActionMaster;
	}

	public RoleMaster getRoleMaster() {
		return this.roleMaster;
	}

	public void setRoleMaster(RoleMaster roleMaster) {
		this.roleMaster = roleMaster;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}