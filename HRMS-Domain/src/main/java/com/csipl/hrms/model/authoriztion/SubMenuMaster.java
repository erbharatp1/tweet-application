
package com.csipl.hrms.model.authoriztion;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


/**
 * The persistent class for the SubMenuMaster database table.
 * 
 */
@Entity
@NamedQuery(name="SubMenuMaster.findAll", query="SELECT s FROM SubMenuMaster s")
public class SubMenuMaster implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long submenuId;

	@Temporal(TemporalType.DATE)
	private Date dateCreated;

	@Temporal(TemporalType.DATE)
	private Date dateUpdate;

	private String status;

	private String submenuName;

	private Long userId;

	private Long userIdUpdate;

	//bi-directional many-to-one association to MenuMaster
	@ManyToOne
	@JoinColumn(name="menuId")
	private MenuMaster menuMaster;

	//bi-directional many-to-one association to SubmenuActionMaster
	@OneToMany(mappedBy="subMenuMaster")
	private List<SubmenuActionMaster> submenuActionMasters;

	public SubMenuMaster() {
	}



	public Long getSubmenuId() {
		return submenuId;
	}



	public void setSubmenuId(Long submenuId) {
		this.submenuId = submenuId;
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

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getSubmenuName() {
		return this.submenuName;
	}

	public void setSubmenuName(String submenuName) {
		this.submenuName = submenuName;
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

	public MenuMaster getMenuMaster() {
		return this.menuMaster;
	}

	public void setMenuMaster(MenuMaster menuMaster) {
		this.menuMaster = menuMaster;
	}

	public List<SubmenuActionMaster> getSubmenuActionMasters() {
		return this.submenuActionMasters;
	}

	public void setSubmenuActionMasters(List<SubmenuActionMaster> submenuActionMasters) {
		this.submenuActionMasters = submenuActionMasters;
	}

	public SubmenuActionMaster addSubmenuActionMaster(SubmenuActionMaster submenuActionMaster) {
		getSubmenuActionMasters().add(submenuActionMaster);
		submenuActionMaster.setSubMenuMaster(this);

		return submenuActionMaster;
	}

	public SubmenuActionMaster removeSubmenuActionMaster(SubmenuActionMaster submenuActionMaster) {
		getSubmenuActionMasters().remove(submenuActionMaster);
		submenuActionMaster.setSubMenuMaster(null);

		return submenuActionMaster;
	}

}