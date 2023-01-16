package com.csipl.hrms.dto.recruitment;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.csipl.hrms.dto.employee.PayStructureDTO;
import com.csipl.hrms.dto.payrollprocess.PayOutDTO;

public class CandidatePayStructureHdDTO {
	private Long candidatePaystructureHdId;

	private String activeStatus;

	
	private Date dateCreated;

	
	private Date effectiveDate;

	private BigDecimal grossPay;
	private Long userId;
	private Long interviewScheduleId;
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
	
	List<PayOutDTO> payOutDtoList;
	private List<CandidatePayStructureDTO> payStructureDtoList;
	private Map<String,String> payHeadsMap;
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
	public Long getInterviewScheduleId() {
		return interviewScheduleId;
	}
	public void setInterviewScheduleId(Long interviewScheduleId) {
		this.interviewScheduleId = interviewScheduleId;
	}
	public List<PayOutDTO> getPayOutDtoList() {
		return payOutDtoList;
	}
	public void setPayOutDtoList(List<PayOutDTO> payOutDtoList) {
		this.payOutDtoList = payOutDtoList;
	}
	public List<CandidatePayStructureDTO> getPayStructureDtoList() {
		return payStructureDtoList;
	}
	public void setPayStructureDtoList(List<CandidatePayStructureDTO> payStructureDtoList) {
		this.payStructureDtoList = payStructureDtoList;
	}
	public Map<String, String> getPayHeadsMap() {
		return payHeadsMap;
	}
	public void setPayHeadsMap(Map<String, String> payHeadsMap) {
		this.payHeadsMap = payHeadsMap;
	}

}
