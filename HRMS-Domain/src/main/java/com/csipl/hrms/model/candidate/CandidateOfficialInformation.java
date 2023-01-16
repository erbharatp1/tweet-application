package com.csipl.hrms.model.candidate;
import java.io.Serializable;
import javax.persistence.*;

import com.csipl.hrms.model.organisation.Grade;

import java.util.Date;


import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the CandidateOfficialInformation database table.
 * 
 */
@Entity
@NamedQuery(name="CandidateOfficialInformation.findAll", query="SELECT c FROM CandidateOfficialInformation c")
public class CandidateOfficialInformation implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long candidateOfficialId;

	private String accidentalInsurance;

	@Temporal(TemporalType.TIMESTAMP)
	private Date aiFromDate;

	@Temporal(TemporalType.TIMESTAMP)
	private Date aiToDate;

	private String biometricId;

	private Long candidateId;

	private Long companyId;

	@Temporal(TemporalType.TIMESTAMP)
	private Date dateCreated;

	@Temporal(TemporalType.TIMESTAMP)
	private Date dateUpdate;

	private String employeeCode;

	private String employeeCodeStatus;

	@Temporal(TemporalType.TIMESTAMP)
	private Date esiEnrollDate;

	@Temporal(TemporalType.DATE)
	private Date esiExitDate;

	private String esiNumber;

	private String isAiApplicable;

	private String isEsicApplicable;

	private String isMiApplicable;

	private String isPfApplicable;

	private String officialEmail;
	
	private String medicalInsurance;

	@Temporal(TemporalType.TIMESTAMP)
	private Date miFromDate;

	@Temporal(TemporalType.TIMESTAMP)
	private Date miToDate;

	private Long noticePeriod;

	@Temporal(TemporalType.TIMESTAMP)
	private Date pfEnrollDate;

	@Temporal(TemporalType.DATE)
	private Date pfExitDate;

	private String pfNumber;

	private Long probationDays;

	private String uanNumber;

	private Long userId;

	private Long userIdUpdate;

	@Transient
	private Long employeeId;
	
	@Transient
	private long role;
	
	public Long getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(Long employeeId) {
		this.employeeId = employeeId;
	}

	public long getRole() {
		return role;
	}

	public void setRole(long role) {
		this.role = role;
	}

	//bi-directional many-to-one association to Grade
	@ManyToOne(cascade=CascadeType.MERGE)
	@JoinColumn(name="gradeId")
	private Grade grade;

	public CandidateOfficialInformation() {
	}

	public Long getCandidateOfficialId() {
		return this.candidateOfficialId;
	}

	public void setCandidateOfficialId(Long candidateOfficialId) {
		this.candidateOfficialId = candidateOfficialId;
	}

	public String getAccidentalInsurance() {
		return this.accidentalInsurance;
	}

	public void setAccidentalInsurance(String accidentalInsurance) {
		this.accidentalInsurance = accidentalInsurance;
	}

	public Date getAiFromDate() {
		return this.aiFromDate;
	}

	public void setAiFromDate(Date aiFromDate) {
		this.aiFromDate = aiFromDate;
	}

	public Date getAiToDate() {
		return this.aiToDate;
	}

	public void setAiToDate(Date aiToDate) {
		this.aiToDate = aiToDate;
	}

	public String getBiometricId() {
		return this.biometricId;
	}

	public void setBiometricId(String biometricId) {
		this.biometricId = biometricId;
	}

	public Long getCandidateId() {
		return this.candidateId;
	}

	public void setCandidateId(Long candidateId) {
		this.candidateId = candidateId;
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

	public Date getDateUpdate() {
		return this.dateUpdate;
	}

	public void setDateUpdate(Date dateUpdate) {
		this.dateUpdate = dateUpdate;
	}

	public String getEmployeeCode() {
		return this.employeeCode;
	}

	public void setEmployeeCode(String employeeCode) {
		this.employeeCode = employeeCode;
	}

	public String getEmployeeCodeStatus() {
		return this.employeeCodeStatus;
	}

	public void setEmployeeCodeStatus(String employeeCodeStatus) {
		this.employeeCodeStatus = employeeCodeStatus;
	}

	public Date getEsiEnrollDate() {
		return this.esiEnrollDate;
	}

	public void setEsiEnrollDate(Date esiEnrollDate) {
		this.esiEnrollDate = esiEnrollDate;
	}

	public Date getEsiExitDate() {
		return this.esiExitDate;
	}

	public void setEsiExitDate(Date esiExitDate) {
		this.esiExitDate = esiExitDate;
	}

	public String getEsiNumber() {
		return this.esiNumber;
	}

	public void setEsiNumber(String esiNumber) {
		this.esiNumber = esiNumber;
	}

	public String getIsAiApplicable() {
		return this.isAiApplicable;
	}

	public void setIsAiApplicable(String isAiApplicable) {
		this.isAiApplicable = isAiApplicable;
	}

	public String getIsEsicApplicable() {
		return this.isEsicApplicable;
	}

	public void setIsEsicApplicable(String isEsicApplicable) {
		this.isEsicApplicable = isEsicApplicable;
	}

	public String getIsMiApplicable() {
		return this.isMiApplicable;
	}

	public void setIsMiApplicable(String isMiApplicable) {
		this.isMiApplicable = isMiApplicable;
	}

	public String getIsPfApplicable() {
		return this.isPfApplicable;
	}

	public void setIsPfApplicable(String isPfApplicable) {
		this.isPfApplicable = isPfApplicable;
	}

	public String getMedicalInsurance() {
		return this.medicalInsurance;
	}

	public void setMedicalInsurance(String medicalInsurance) {
		this.medicalInsurance = medicalInsurance;
	}

	public Date getMiFromDate() {
		return this.miFromDate;
	}

	public void setMiFromDate(Date miFromDate) {
		this.miFromDate = miFromDate;
	}

	public Date getMiToDate() {
		return this.miToDate;
	}

	public void setMiToDate(Date miToDate) {
		this.miToDate = miToDate;
	}

	public Long getNoticePeriod() {
		return this.noticePeriod;
	}

	public void setNoticePeriod(Long noticePeriod) {
		this.noticePeriod = noticePeriod;
	}

	public Date getPfEnrollDate() {
		return this.pfEnrollDate;
	}

	public void setPfEnrollDate(Date pfEnrollDate) {
		this.pfEnrollDate = pfEnrollDate;
	}

	public Date getPfExitDate() {
		return this.pfExitDate;
	}

	public void setPfExitDate(Date pfExitDate) {
		this.pfExitDate = pfExitDate;
	}

	public String getPfNumber() {
		return this.pfNumber;
	}

	public void setPfNumber(String pfNumber) {
		this.pfNumber = pfNumber;
	}

	public Long getProbationDays() {
		return this.probationDays;
	}

	public void setProbationDays(Long probationDays) {
		this.probationDays = probationDays;
	}

	public String getUanNumber() {
		return this.uanNumber;
	}

	public void setUanNumber(String uanNumber) {
		this.uanNumber = uanNumber;
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

	public Grade getGrade() {
		return this.grade;
	}

	public void setGrade(Grade grade) {
		this.grade = grade;
	}

	public String getOfficialEmail() {
		return officialEmail;
	}

	public void setOfficialEmail(String officialEmail) {
		this.officialEmail = officialEmail;
	}

	
}