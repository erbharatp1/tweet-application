package com.csipl.hrms.model.payroll;

import java.io.Serializable;
import java.math.BigDecimal;
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

import com.csipl.hrms.model.employee.Employee;
import com.csipl.hrms.model.payrollprocess.FinancialYear;

/**
 * The persistent class for the TdsTransaction database table.
 * 
 */
@Entity
@NamedQuery(name = "TdsTransaction.findAll", query = "SELECT t FROM TdsTransaction t")
public class TdsTransaction implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long tdsTransactionId;

	private String activeStatus;

	private String allowModi;

	private BigDecimal approvedAmount;

	private String approveStatus;

	private BigDecimal basicDA;

	private String city;

	@Temporal(TemporalType.TIMESTAMP)
	private Date dateCreated;

	@Temporal(TemporalType.TIMESTAMP)
	private Date dateUpdate;

	private String fileLocation;

	private BigDecimal investmentAmount;

	private String investmentDetail;

	private BigDecimal maxLimit;

	private Long noOfDocuments;

	private String proof;

	private String remarks;

	private String status;

	private Long userId;

	private Long userIdUpdate;
	
	private String tdsTransactionUpdateStatus;
	
	@ManyToOne
	@JoinColumn(name = "financialYearId")
	private FinancialYear financialYear;

	// bi-directional many-to-one association to TdsHouseRentFileInfo
	@OneToMany(mappedBy = "tdsTransaction", cascade = CascadeType.ALL)
	private List<TdsTransactionFileInfo> tdsTransactionFileInfo;

	// bi-directional many-to-one association to Employee
	@ManyToOne
	@JoinColumn(name = "employeeId")
	private Employee employee;

	// bi-directional many-to-one association to TdsGroup
	/*
	 * @ManyToOne
	 * 
	 * @JoinColumn(name="tdsGroupId") private TdsGroup tdsGroup;
	 */

	// bi-directional many-to-one association to TdsGroupSetup
	@ManyToOne
	@JoinColumn(name = "tdsGroupId")
	private TdsGroupSetup tdsGroupSetup;

	// bi-directional many-to-one association to TdsSectionSetup
	@ManyToOne
	@JoinColumn(name = "tdsSectionId")
	private TdsSectionSetup tdsSectionSetup;

	// bi-directional many-to-one association to TdsHouseRentInfo
	@OneToMany(mappedBy = "tdsTransaction")
	private List<TdsHouseRentInfo> tdsHouseRentInfos;

	public TdsTransaction() {
	}

	public List<TdsTransactionFileInfo> getTdsTransactionFileInfo() {
		return tdsTransactionFileInfo;
	}

	public void setTdsTransactionFileInfo(List<TdsTransactionFileInfo> tdsTransactionFileInfo) {
		this.tdsTransactionFileInfo = tdsTransactionFileInfo;
	}

	public Long getTdsTransactionId() {
		return this.tdsTransactionId;
	}

	public void setTdsTransactionId(Long tdsTransactionId) {
		this.tdsTransactionId = tdsTransactionId;
	}

	public FinancialYear getFinancialYear() {
		return financialYear;
	}

	public void setFinancialYear(FinancialYear financialYear) {
		this.financialYear = financialYear;
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

	public BigDecimal getApprovedAmount() {
		return this.approvedAmount;
	}

	public void setApprovedAmount(BigDecimal approvedAmount) {
		this.approvedAmount = approvedAmount;
	}

	public String getApproveStatus() {
		return this.approveStatus;
	}

	public void setApproveStatus(String approveStatus) {
		this.approveStatus = approveStatus;
	}

	public BigDecimal getBasicDA() {
		return this.basicDA;
	}

	public void setBasicDA(BigDecimal basicDA) {
		this.basicDA = basicDA;
	}

	public String getCity() {
		return this.city;
	}

	public void setCity(String city) {
		this.city = city;
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

	public String getFileLocation() {
		return this.fileLocation;
	}

	public void setFileLocation(String fileLocation) {
		this.fileLocation = fileLocation;
	}

	public BigDecimal getInvestmentAmount() {
		return this.investmentAmount;
	}

	public void setInvestmentAmount(BigDecimal investmentAmount) {
		this.investmentAmount = investmentAmount;
	}

	public String getInvestmentDetail() {
		return this.investmentDetail;
	}

	public void setInvestmentDetail(String investmentDetail) {
		this.investmentDetail = investmentDetail;
	}

	public BigDecimal getMaxLimit() {
		return this.maxLimit;
	}

	public void setMaxLimit(BigDecimal maxLimit) {
		this.maxLimit = maxLimit;
	}

	public Long getNoOfDocuments() {
		return this.noOfDocuments;
	}

	public void setNoOfDocuments(Long noOfDocuments) {
		this.noOfDocuments = noOfDocuments;
	}

	public String getProof() {
		return this.proof;
	}

	public void setProof(String proof) {
		this.proof = proof;
	}

	public String getRemarks() {
		return this.remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
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

	public Employee getEmployee() {
		return this.employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	/*
	 * public TdsGroup getTdsGroup() { return this.tdsGroup; }
	 * 
	 * public void setTdsGroup(TdsGroup tdsGroup) { this.tdsGroup = tdsGroup; }
	 */

	public TdsGroupSetup getTdsGroupSetup() {
		return this.tdsGroupSetup;
	}

	public void setTdsGroupSetup(TdsGroupSetup tdsGroupSetup) {
		this.tdsGroupSetup = tdsGroupSetup;
	}

	public TdsSectionSetup getTdsSectionSetup() {
		return this.tdsSectionSetup;
	}

	public void setTdsSectionSetup(TdsSectionSetup tdsSectionSetup) {
		this.tdsSectionSetup = tdsSectionSetup;
	}

	public List<TdsHouseRentInfo> getTdsHouseRentInfos() {
		return this.tdsHouseRentInfos;
	}

	public void setTdsHouseRentInfos(List<TdsHouseRentInfo> tdsHouseRentInfos) {
		this.tdsHouseRentInfos = tdsHouseRentInfos;
	}

	public TdsHouseRentInfo addTdsHouseRentInfo(TdsHouseRentInfo tdsHouseRentInfo) {
		getTdsHouseRentInfos().add(tdsHouseRentInfo);
		tdsHouseRentInfo.setTdsTransaction(this);

		return tdsHouseRentInfo;
	}

	public TdsHouseRentInfo removeTdsHouseRentInfo(TdsHouseRentInfo tdsHouseRentInfo) {
		getTdsHouseRentInfos().remove(tdsHouseRentInfo);
		tdsHouseRentInfo.setTdsTransaction(null);

		return tdsHouseRentInfo;
	}

	public String getTdsTransactionUpdateStatus() {
		return tdsTransactionUpdateStatus;
	}

	public void setTdsTransactionUpdateStatus(String tdsTransactionUpdateStatus) {
		this.tdsTransactionUpdateStatus = tdsTransactionUpdateStatus;
	}

}