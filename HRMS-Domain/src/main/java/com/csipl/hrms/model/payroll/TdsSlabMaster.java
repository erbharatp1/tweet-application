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


/**
 * The persistent class for the TdsSlabMaster database table.
 * 
 */
@Entity
@NamedQuery(name="TdsSlabMaster.findAll", query="SELECT t FROM TdsSlabMaster t")
public class TdsSlabMaster implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long tdsSlabMasterId;

	private Long companyId;

	private String tdsCategory;

	private Long userId;

	//bi-directional many-to-one association to TdsSlabHd
	@OneToMany(mappedBy="tdsSlabMaster" ,cascade = CascadeType.ALL)
	private List<TdsSlabHd> tdsSlabHds;

	public TdsSlabMaster() {
	}

	public Long getTdsSlabMasterId() {
		return this.tdsSlabMasterId;
	}

	public void setTdsSlabMasterId(Long tdsSlabMasterId) {
		this.tdsSlabMasterId = tdsSlabMasterId;
	}

	public Long getCompanyId() {
		return this.companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	public String getTdsCategory() {
		return this.tdsCategory;
	}

	public void setTdsCategory(String tdsCategory) {
		this.tdsCategory = tdsCategory;
	}

	public Long getUserId() {
		return this.userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public List<TdsSlabHd> getTdsSlabHds() {
		return this.tdsSlabHds;
	}

	public void setTdsSlabHds(List<TdsSlabHd> tdsSlabHds) {
		this.tdsSlabHds = tdsSlabHds;
	}

	public TdsSlabHd addTdsSlabHd(TdsSlabHd tdsSlabHd) {
		getTdsSlabHds().add(tdsSlabHd);
		tdsSlabHd.setTdsSlabMaster(this);

		return tdsSlabHd;
	}

	public TdsSlabHd removeTdsSlabHd(TdsSlabHd tdsSlabHd) {
		getTdsSlabHds().remove(tdsSlabHd);
		tdsSlabHd.setTdsSlabMaster(null);

		return tdsSlabHd;
	}

}