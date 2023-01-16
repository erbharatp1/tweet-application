package com.csipl.hrms.model.payroll;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
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

import com.csipl.hrms.model.common.Company;
import com.csipl.hrms.model.payrollprocess.FinancialYear;

@Entity
@NamedQuery(name = "TdsSlabHd.findAll", query = "SELECT t FROM TdsSlabHd t")
public class TdsSlabHd implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long tdsSLabHdId;

	private String activeStatus;

	private String allowModi;

	@Temporal(TemporalType.TIMESTAMP)
	private Date dateCreated;

	@Temporal(TemporalType.DATE)
	private Date dateEffective;

	@Temporal(TemporalType.TIMESTAMP)
	private Date dateUpdate;

	@Temporal(TemporalType.TIMESTAMP)
	private Date effectiveEndDate;

	@Temporal(TemporalType.TIMESTAMP)
	private Date effectiveStartDate;

	private Long groupId;

	private Long userId;

	private Long userIdUpdate;

	// bi-directional many-to-one association to TdsSlab
	@OneToMany(mappedBy = "tdsSlabHd",cascade = CascadeType.ALL)
	private List<TdsSlab> tdsSlabs;

	// bi-directional many-to-one association to Company
	@ManyToOne
	@JoinColumn(name = "companyId")
	private Company company;

	// bi-directional many-to-one association to FinancialYear
	@ManyToOne
	@JoinColumn(name = "finencialYearId")
	private FinancialYear financialYear;

	// bi-directional many-to-one association to TdsSlabMaster
	@ManyToOne
	@JoinColumn(name = "tdsSlabMasterId")
	private TdsSlabMaster tdsSlabMaster;

	public TdsSlabHd() {
	}

	public Long getTdsSLabHdId() {
		return this.tdsSLabHdId;
	}

	public void setTdsSLabHdId(Long tdsSLabHdId) {
		this.tdsSLabHdId = tdsSLabHdId;
	}

	public String getActiveStatus() {
		return this.activeStatus;
	}

	public void setActiveStatus(String activeStatus) {
		this.activeStatus = activeStatus;
	}

	public String getAllowModi() {
		return this.allowModi;
	}

	public void setAllowModi(String allowModi) {
		this.allowModi = allowModi;
	}

	public Date getDateCreated() {
		return this.dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	public Date getDateEffective() {
		return this.dateEffective;
	}

	public void setDateEffective(Date dateEffective) {
		this.dateEffective = dateEffective;
	}

	public Date getDateUpdate() {
		return this.dateUpdate;
	}

	public void setDateUpdate(Date dateUpdate) {
		this.dateUpdate = dateUpdate;
	}

	public Date getEffectiveEndDate() {
		return this.effectiveEndDate;
	}

	public void setEffectiveEndDate(Date effectiveEndDate) {
		this.effectiveEndDate = effectiveEndDate;
	}

	public Date getEffectiveStartDate() {
		return this.effectiveStartDate;
	}

	public void setEffectiveStartDate(Date effectiveStartDate) {
		this.effectiveStartDate = effectiveStartDate;
	}

	public Long getGroupId() {
		return this.groupId;
	}

	public void setGroupId(Long groupId) {
		this.groupId = groupId;
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

	public List<TdsSlab> getTdsSlabs() {
		return this.tdsSlabs;
	}

	public void setTdsSlabs(List<TdsSlab> tdsSlabs) {
		this.tdsSlabs = tdsSlabs;
	}

	public TdsSlab addTdsSlab(TdsSlab tdsSlab) {
		getTdsSlabs().add(tdsSlab);
		tdsSlab.setTdsSlabHd(this);

		return tdsSlab;
	}

	public TdsSlab removeTdsSlab(TdsSlab tdsSlab) {
		getTdsSlabs().remove(tdsSlab);
		tdsSlab.setTdsSlabHd(null);

		return tdsSlab;
	}

	public Company getCompany() {
		return this.company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public FinancialYear getFinancialYear() {
		return this.financialYear;
	}

	public void setFinancialYear(FinancialYear financialYear) {
		this.financialYear = financialYear;
	}

	public TdsSlabMaster getTdsSlabMaster() {
		return this.tdsSlabMaster;
	}

	public void setTdsSlabMaster(TdsSlabMaster tdsSlabMaster) {
		this.tdsSlabMaster = tdsSlabMaster;
	}

	@Override
	public String toString() {
		return "TdsSlabHd [tdsSLabHdId=" + tdsSLabHdId + ", activeStatus=" + activeStatus + ", allowModi=" + allowModi
				+ ", dateCreated=" + dateCreated + ", dateEffective=" + dateEffective + ", dateUpdate=" + dateUpdate
				+ ", effectiveEndDate=" + effectiveEndDate + ", effectiveStartDate=" + effectiveStartDate + ", groupId="
				+ groupId + ", userId=" + userId + ", userIdUpdate=" + userIdUpdate + ", tdsSlabs=" + tdsSlabs
				+ ", company=" + company + ", financialYear=" + financialYear + ", tdsSlabMaster=" + tdsSlabMaster
				+ "]";
	}

}