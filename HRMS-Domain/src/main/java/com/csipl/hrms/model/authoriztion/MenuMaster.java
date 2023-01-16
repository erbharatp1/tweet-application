package com.csipl.hrms.model.authoriztion;


import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

import com.csipl.hrms.model.common.Company;


/**
 * The persistent class for the MenuMaster database table.
 * 
 */
@Entity
@NamedQuery(name="MenuMaster.findAll", query="SELECT m FROM MenuMaster m")
public class MenuMaster implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long menuId;

	private String menuName;

	//bi-directional many-to-one association to Company
	@ManyToOne
	@JoinColumn(name="companyId")
	private Company company;

	//bi-directional many-to-one association to SubMenuMaster
	@OneToMany(mappedBy="menuMaster")
	private List<SubMenuMaster> subMenuMasters;

	public MenuMaster() {
	}

	public Long getMenuId() {
		return this.menuId;
	}

	public void setMenuId(Long menuId) {
		this.menuId = menuId;
	}

	public String getMenuName() {
		return this.menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	public Company getCompany() {
		return this.company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public List<SubMenuMaster> getSubMenuMasters() {
		return this.subMenuMasters;
	}

	public void setSubMenuMasters(List<SubMenuMaster> subMenuMasters) {
		this.subMenuMasters = subMenuMasters;
	}

	public SubMenuMaster addSubMenuMaster(SubMenuMaster subMenuMaster) {
		getSubMenuMasters().add(subMenuMaster);
		subMenuMaster.setMenuMaster(this);

		return subMenuMaster;
	}

	public SubMenuMaster removeSubMenuMaster(SubMenuMaster subMenuMaster) {
		getSubMenuMasters().remove(subMenuMaster);
		subMenuMaster.setMenuMaster(null);

		return subMenuMaster;
	}

}