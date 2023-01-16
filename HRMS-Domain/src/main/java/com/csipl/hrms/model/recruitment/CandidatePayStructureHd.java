package com.csipl.hrms.model.recruitment;

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

/**
 * The persistent class for the PayStructureHd database table.
 * 
 */
@Entity
@NamedQuery(name = "CandidatePayStructureHd.findAll", query = "SELECT p FROM CandidatePayStructureHd p")
public class CandidatePayStructureHd implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long candidatePaystructureHdId;

	private String activeStatus;

	@Temporal(TemporalType.TIMESTAMP)
	private Date dateCreated;

	@Temporal(TemporalType.DATE)
	private Date effectiveDate;

	private BigDecimal grossPay;
	private Long userId;
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

	// bi-directional many-to-one association to PayStructure
	@OneToMany(mappedBy = "candidatePayStructureHd", cascade = CascadeType.ALL)
	private List<CandidatePayStructure> candidatePayStructure;

	// bi-directional many-to-one association to Employee
	@ManyToOne
	@JoinColumn(name = "interviewScheduleId")
	private InterviewScheduler interviewScheduler;

	public Long getCandidatePaystructureHdId() {
		return candidatePaystructureHdId;
	}

	public void setCandidatePaystructureHdId(Long candidatePaystructureHdId) {
		this.candidatePaystructureHdId = candidatePaystructureHdId;
	}

	public String getActiveStatus() {
		return activeStatus;
	}

	public void setActiveStatus(String activeStatus) {
		this.activeStatus = activeStatus;
	}

	public Date getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	public Date getEffectiveDate() {
		return effectiveDate;
	}

	public void setEffectiveDate(Date effectiveDate) {
		this.effectiveDate = effectiveDate;
	}

	public BigDecimal getGrossPay() {
		return grossPay;
	}

	public void setGrossPay(BigDecimal grossPay) {
		this.grossPay = grossPay;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getProcessMonth() {
		return processMonth;
	}

	public void setProcessMonth(String processMonth) {
		this.processMonth = processMonth;
	}

	public BigDecimal getCtc() {
		return ctc;
	}

	public void setCtc(BigDecimal ctc) {
		this.ctc = ctc;
	}

	public BigDecimal getNetPay() {
		return netPay;
	}

	public void setNetPay(BigDecimal netPay) {
		this.netPay = netPay;
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

	public List<CandidatePayStructure> getCandidatePayStructure() {
		return candidatePayStructure;
	}

	public void setCandidatePayStructure(List<CandidatePayStructure> candidatePayStructure) {
		this.candidatePayStructure = candidatePayStructure;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public CandidatePayStructureHd() {
	}

	public InterviewScheduler getInterviewScheduler() {
		return interviewScheduler;
	}

	public void setInterviewScheduler(InterviewScheduler interviewScheduler) {
		this.interviewScheduler = interviewScheduler;
	}
}
