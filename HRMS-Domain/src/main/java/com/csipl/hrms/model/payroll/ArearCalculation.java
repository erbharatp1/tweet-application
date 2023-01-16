package com.csipl.hrms.model.payroll;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the ArearCalculation database table.
 * 
 */
@Entity
@NamedQuery(name="ArearCalculation.findAll", query="SELECT a FROM ArearCalculation a")
public class ArearCalculation implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long arearCalculationId;

	private BigDecimal actualAmount;

	private Long companyId;

	@Temporal(TemporalType.TIMESTAMP)
	private Date dateCreated;

	@Temporal(TemporalType.TIMESTAMP)
	private Date dateUpdated;

	private BigDecimal esiDeduction;

	private BigDecimal netPayableAmount;

	private String payrollMonth;

	private BigDecimal pfDeduction;
	private BigDecimal ptDeduction;

	private Long userId;

	private Long userIdUpdate;

	//bi-directional many-to-one association to ArearMaster
	@ManyToOne
	@JoinColumn(name="arearId")
	private ArearMaster arearMaster;

	public ArearCalculation() {
	}

	public Long getArearCalculationId() {
		return this.arearCalculationId;
	}

	public void setArearCalculationId(Long arearCalculationId) {
		this.arearCalculationId = arearCalculationId;
	}

	public BigDecimal getActualAmount() {
		return this.actualAmount;
	}

	public void setActualAmount(BigDecimal actualAmount) {
		this.actualAmount = actualAmount;
	}

	public Long getCompanyId() {
		return this.companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	public Date getDateCreated() {
		return this.dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	public Date getDateUpdated() {
		return this.dateUpdated;
	}

	public void setDateUpdated(Date dateUpdated) {
		this.dateUpdated = dateUpdated;
	}

	public BigDecimal getEsiDeduction() {
		return this.esiDeduction;
	}

	public void setEsiDeduction(BigDecimal esiDeduction) {
		this.esiDeduction = esiDeduction;
	}

	public BigDecimal getNetPayableAmount() {
		return this.netPayableAmount;
	}

	public void setNetPayableAmount(BigDecimal netPayableAmount) {
		this.netPayableAmount = netPayableAmount;
	}

	public String getPayrollMonth() {
		return this.payrollMonth;
	}

	public void setPayrollMonth(String payrollMonth) {
		this.payrollMonth = payrollMonth;
	}

	public BigDecimal getPfDeduction() {
		return this.pfDeduction;
	}

	public void setPfDeduction(BigDecimal pfDeduction) {
		this.pfDeduction = pfDeduction;
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

	public ArearMaster getArearMaster() {
		return this.arearMaster;
	}

	public void setArearMaster(ArearMaster arearMaster) {
		this.arearMaster = arearMaster;
	}

	public BigDecimal getPtDeduction() {
		return ptDeduction;
	}

	public void setPtDeduction(BigDecimal ptDeduction) {
		this.ptDeduction = ptDeduction;
	}

}