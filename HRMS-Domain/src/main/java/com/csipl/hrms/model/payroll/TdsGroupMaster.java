package com.csipl.hrms.model.payroll;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

@Entity
@NamedQuery(name="TdsGroupMaster.findAll", query="SELECT t FROM TdsGroupMaster t")
public class TdsGroupMaster implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long tdsGroupMasterId;

	private Long companyId;

	private String isSubGroupLimit;

	private String tdsGroupName;

	private Long userId;

	//bi-directional many-to-one association to TdsGroupSetup
	@OneToMany(mappedBy="tdsGroupMaster",cascade = CascadeType.ALL)
	private List<TdsGroupSetup> tdsGroupSetups;

	public TdsGroupMaster() {
	}

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

	public List<TdsGroupSetup> getTdsGroupSetups() {
		return this.tdsGroupSetups;
	}

	public void setTdsGroupSetups(List<TdsGroupSetup> tdsGroupSetups) {
		this.tdsGroupSetups = tdsGroupSetups;
	}

	public TdsGroupSetup addTdsGroupSetup(TdsGroupSetup tdsGroupSetup) {
		getTdsGroupSetups().add(tdsGroupSetup);
		tdsGroupSetup.setTdsGroupMaster(this);

		return tdsGroupSetup;
	}

	public TdsGroupSetup removeTdsGroupSetup(TdsGroupSetup tdsGroupSetup) {
		getTdsGroupSetups().remove(tdsGroupSetup);
		tdsGroupSetup.setTdsGroupMaster(null);

		return tdsGroupSetup;
	}

}