package com.csipl.hrms.model.employee;

import java.io.Serializable;
import javax.persistence.*;


import com.csipl.hrms.model.BaseModelWithoutCG;
import com.csipl.hrms.model.organisation.Grade;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * The persistent class for the PayStructureHd database table.
 * 
 */
@Entity
@NamedQuery(name = "PayStructureHd.findAll", query = "SELECT p FROM PayStructureHd p")
public class PayStructureHd extends BaseModelWithoutCG implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long payStructureHdId;

	private String activeStatus;

	private String allowModi;

	@Temporal(TemporalType.TIMESTAMP)
	private Date dateCreated;

	@Temporal(TemporalType.DATE)
	private Date dateEnd;

	@Temporal(TemporalType.TIMESTAMP)
	private Date dateUpdate;

	@Temporal(TemporalType.DATE)
	private Date effectiveDate;
	
	@Temporal(TemporalType.DATE)
	private Date esicEndDate;

	private BigDecimal grossPay;
	
	private BigDecimal costToCompany;

	private Long userId;

	private Long userIdUpdate;

	private String isNoPFDeduction;
	private String processMonth;
	
	private BigDecimal ctc;
	private BigDecimal netPay;
 	
	private BigDecimal esiEmployee;
	private BigDecimal esiEmployer;
	private BigDecimal epfEmployee;
	private BigDecimal epfEmployer;
 	private BigDecimal professionalTax;
 	private BigDecimal lwfEmployeeAmount;
 	private BigDecimal lwfEmployerAmount;
 	private BigDecimal epfEmployeePension;

 	@Transient
	private String payHeadExcelId;
	
	@Transient
	private String amountExcel;
 	
 	
	@Transient
	 private  boolean updateFlage;
	
	private String through_excel_flag ;
	
	@Transient
	private String is_pf_applicable;
	
	@Transient
	private String is_esi_applicable;
	@Transient
	private String staturyType;
	
	@Transient
	private String is_lwf_applicable;
	

	// bi-directional many-to-one association to PayStructure
	@OneToMany(mappedBy = "payStructureHd", cascade = CascadeType.ALL)
 	private List<PayStructure> payStructures;

	// bi-directional many-to-one association to Employee
	@ManyToOne
	@JoinColumn(name = "employeeId")
	private Employee employee;

	// bi-directional many-to-one association to Grade
	@ManyToOne
	@JoinColumn(name = "gradesId")
	private Grade grade;

	public PayStructureHd() {
	}

	public Long getPayStructureHdId() {
		return this.payStructureHdId;
	}

	public void setPayStructureHdId(Long payStructureHdId) {
		this.payStructureHdId = payStructureHdId;
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

	public Date getDateEnd() {
		return this.dateEnd;
	}

	public void setDateEnd(Date dateEnd) {
		this.dateEnd = dateEnd;
	}

	public Date getDateUpdate() {
		return this.dateUpdate;
	}

	public void setDateUpdate(Date dateUpdate) {
		this.dateUpdate = dateUpdate;
	}

	public Date getEffectiveDate() {
		return this.effectiveDate;
	}

	public void setEffectiveDate(Date effectiveDate) {
		this.effectiveDate = effectiveDate;
	}
	
 

	public BigDecimal getGrossPay() {
		return this.grossPay;
	}

	public void setGrossPay(BigDecimal grossPay) {
		this.grossPay = grossPay;
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

	public List<PayStructure> getPayStructures() {
		return this.payStructures;
	}

	public void setPayStructures(List<PayStructure> payStructures) {
		this.payStructures = payStructures;
	}

	public PayStructure addPayStructure(PayStructure payStructure) {
		getPayStructures().add(payStructure);
		payStructure.setPayStructureHd(this);

		return payStructure;
	}

	public PayStructure removePayStructure(PayStructure payStructure) {
		getPayStructures().remove(payStructure);
		payStructure.setPayStructureHd(null);

		return payStructure;
	}

	public Employee getEmployee() {
		return this.employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public Grade getGrade() {
		return this.grade;
	}

	public void setGrade(Grade grade) {
		this.grade = grade;
	}

	public boolean isUpdateFlage() {
		return updateFlage;
	}

	public void setUpdateFlage(boolean updateFlage) {
		this.updateFlage = updateFlage;
	}

	public BigDecimal getCostToCompany() {
		return costToCompany;
	}

	public void setCostToCompany(BigDecimal costToCompany) {
		this.costToCompany = costToCompany;
	}

	public String getIsNoPFDeduction() {
		return isNoPFDeduction;
	}

	public void setIsNoPFDeduction(String isNoPFDeduction) {
		this.isNoPFDeduction = isNoPFDeduction;
	}

	public String getProcessMonth() {
		return processMonth;
	}

	public void setProcessMonth(String processMonth) {
		this.processMonth = processMonth;
	}
	public Date getEsicEndDate() {
		return this.esicEndDate;
	}

	public void setEsicEndDate(Date esicEndDate) {
		this.esicEndDate = esicEndDate;
	}

	public BigDecimal getCtc() {
		return ctc;
	}

	public void setCtc(BigDecimal ctc) {
		this.ctc = ctc;
	}

	public BigDecimal getEsiEmployee() {
		return esiEmployee;
	}

	public void setEsiEmployee(BigDecimal esiEmployee) {
		this.esiEmployee = esiEmployee;
	}

	public BigDecimal getEsiEmployer() {
		return esiEmployer;
	}

	public void setEsiEmployer(BigDecimal esiEmployer) {
		this.esiEmployer = esiEmployer;
	}

	public BigDecimal getEpfEmployee() {
		return epfEmployee;
	}

	public void setEpfEmployee(BigDecimal epfEmployee) {
		this.epfEmployee = epfEmployee;
	}

	public BigDecimal getEpfEmployer() {
		return epfEmployer;
	}

	public void setEpfEmployer(BigDecimal epfEmployer) {
		this.epfEmployer = epfEmployer;
	}

	
	public BigDecimal getProfessionalTax() {
		return professionalTax;
	}

	public void setProfessionalTax(BigDecimal professionalTax) {
		this.professionalTax = professionalTax;
	}

	public BigDecimal getNetPay() {
		return netPay;
	}

	public void setNetPay(BigDecimal netPay) {
		this.netPay = netPay;
	}

	public String getPayHeadExcelId() {
		return payHeadExcelId;
	}

	public void setPayHeadExcelId(String payHeadExcelId) {
		this.payHeadExcelId = payHeadExcelId;
	}

	public String getAmountExcel() {
		return amountExcel;
	}

	public void setAmountExcel(String amountExcel) {
		this.amountExcel = amountExcel;
	}

	public String getThrough_excel_flag() {
		return through_excel_flag;
	}

	public void setThrough_excel_flag(String through_excel_flag) {
		this.through_excel_flag = through_excel_flag;
	}

	public String getIs_pf_applicable() {
		return is_pf_applicable;
	}

	public void setIs_pf_applicable(String is_pf_applicable) {
		this.is_pf_applicable = is_pf_applicable;
	}

	public String getIs_esi_applicable() {
		return is_esi_applicable;
	}

	public void setIs_esi_applicable(String is_esi_applicable) {
		this.is_esi_applicable = is_esi_applicable;
	}

	public String getStaturyType() {
		return staturyType;
	}

	public void setStaturyType(String staturyType) {
		this.staturyType = staturyType;
	}

	public BigDecimal getLwfEmployeeAmount() {
		return lwfEmployeeAmount;
	}

	public void setLwfEmployeeAmount(BigDecimal lwfEmployeeAmount) {
		this.lwfEmployeeAmount = lwfEmployeeAmount;
	}

	public BigDecimal getLwfEmployerAmount() {
		return lwfEmployerAmount;
	}

	public void setLwfEmployerAmount(BigDecimal lwfEmployerAmount) {
		this.lwfEmployerAmount = lwfEmployerAmount;
	}

	public BigDecimal getEpfEmployeePension() {
		return epfEmployeePension;
	}

	public void setEpfEmployeePension(BigDecimal epfEmployeePension) {
		this.epfEmployeePension = epfEmployeePension;
	}

	public String getIs_lwf_applicable() {
		return is_lwf_applicable;
	}

	public void setIs_lwf_applicable(String is_lwf_applicable) {
		this.is_lwf_applicable = is_lwf_applicable;
	}
	
	
	
}