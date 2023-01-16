package com.csipl.hrms.dto.candidate;

import java.util.Date;



public class CandidateOfficialInformationDTO {
	
	private Long candidateOfficialId;

	private String accidentalInsurance;

	private Date aiFromDate;

	private Date aiToDate;

	private Long companyId;
	
	private Date dateCreated;

	private Date dateUpdate;
	
	private Date esiEnrollDate;

	private String esiNumber;

	private Long grade;
	
	private Long aiFamilyId;
	
	private Long miFamilyId;
	
	private Long pfFamilyId;
	
	private Long esicFamilyId;

	private String isAiApplicable;

	private String isEsicApplicable;

	private String isMiApplicable;

	private String isPfApplicable;

	private String medicalInsurance;
	
	private Date miFromDate;

	private Date miToDate;

	private Long noticePeriod;

	private Date pfEnrollDate;

	private String pfNumber;

	private Long probationDays;

	private String uanNumber;

	private Long userId;

	private Long userIdUpdate;
	
	private Long candidateId;
	
	private String gradeName;
	
	private Long UanId;
	
	private Long EsicId;
	
	private Long MiId;

	private Long AiId;
	
	private Long pfId;
	private Long employeeId;
	
	private long role;
	
	private String status;

	private String employeeCode;
	private String employeeCodeStatus;
	
	private String biometricId;
	private String officialEmail;
	private Date pfExitDate;
	private Date esiExitDate;
	
	public Date getPfExitDate() {
		return pfExitDate;
	}

	public void setPfExitDate(Date pfExitDate) {
		this.pfExitDate = pfExitDate;
	}

	public Date getEsiExitDate() {
		return esiExitDate;
	}

	public void setEsiExitDate(Date esiExitDate) {
		this.esiExitDate = esiExitDate;
	}

	public String getEmployeeCode() {
		return employeeCode;
	}

	public void setEmployeeCode(String employeeCode) {
		this.employeeCode = employeeCode;
	}

	public String getEmployeeCodeStatus() {
		return employeeCodeStatus;
	}

	public void setEmployeeCodeStatus(String employeeCodeStatus) {
		this.employeeCodeStatus = employeeCodeStatus;
	}

	public Long getCandidateId() {
		return candidateId;
	}

	public Long getUanId() {
		return UanId;
	}

	public void setUanId(Long uanId) {
		UanId = uanId;
	}

	public Long getEsicId() {
		return EsicId;
	}

	public void setEsicId(Long esicId) {
		EsicId = esicId;
	}

	public Long getMiId() {
		return MiId;
	}

	public void setMiId(Long miId) {
		MiId = miId;
	}

	public Long getAiId() {
		return AiId;
	}

	public void setAiId(Long aiId) {
		AiId = aiId;
	}

	public Long getPfId() {
		return pfId;
	}

	public void setPfId(Long pfId) {
		this.pfId = pfId;
	}

	public void setCandidateId(Long candidateId) {
		this.candidateId = candidateId;
	}

	public Date getMiFromDate() {
		return miFromDate;
	}

	public void setMiFromDate(Date miFromDate) {
		this.miFromDate = miFromDate;
	}

	public Date getMiToDate() {
		return miToDate;
	}

	public void setMiToDate(Date miToDate) {
		this.miToDate = miToDate;
	}

	public Long getNoticePeriod() {
		return noticePeriod;
	}

	public void setNoticePeriod(Long noticePeriod) {
		this.noticePeriod = noticePeriod;
	}

	public Date getPfEnrollDate() {
		return pfEnrollDate;
	}

	public void setPfEnrollDate(Date pfEnrollDate) {
		this.pfEnrollDate = pfEnrollDate;
	}

	public String getPfNumber() {
		return pfNumber;
	}

	public void setPfNumber(String pfNumber) {
		this.pfNumber = pfNumber;
	}

	public Long getProbationDays() {
		return probationDays;
	}

	public void setProbationDays(Long probationDays) {
		this.probationDays = probationDays;
	}

	public String getUanNumber() {
		return uanNumber;
	}

	public void setUanNumber(String uanNumber) {
		this.uanNumber = uanNumber;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getUserIdUpdate() {
		return userIdUpdate;
	}

	public void setUserIdUpdate(Long userIdUpdate) {
		this.userIdUpdate = userIdUpdate;
	}

	

	public Long getCandidateOfficialId() {
		return candidateOfficialId;
	}

	public void setCandidateOfficialId(Long candidateOfficialId) {
		this.candidateOfficialId = candidateOfficialId;
	}

	public String getAccidentalInsurance() {
		return accidentalInsurance;
	}

	public void setAccidentalInsurance(String accidentalInsurance) {
		this.accidentalInsurance = accidentalInsurance;
	}

	public Date getAiFromDate() {
		return aiFromDate;
	}

	public void setAiFromDate(Date aiFromDate) {
		this.aiFromDate = aiFromDate;
	}

	public Date getAiToDate() {
		return aiToDate;
	}

	public void setAiToDate(Date aiToDate) {
		this.aiToDate = aiToDate;
	}

	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	public Date getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	public Date getDateUpdate() {
		return dateUpdate;
	}

	public void setDateUpdate(Date dateUpdate) {
		this.dateUpdate = dateUpdate;
	}

	public Date getEsiEnrollDate() {
		return esiEnrollDate;
	}

	public void setEsiEnrollDate(Date esiEnrollDate) {
		this.esiEnrollDate = esiEnrollDate;
	}

	public String getEsiNumber() {
		return esiNumber;
	}

	public void setEsiNumber(String esiNumber) {
		this.esiNumber = esiNumber;
	}

	public Long getGrade() {
		return grade;
	}

	public void setGrade(Long grade) {
		this.grade = grade;
	}

	public String getIsAiApplicable() {
		return isAiApplicable;
	}

	public void setIsAiApplicable(String isAiApplicable) {
		this.isAiApplicable = isAiApplicable;
	}

	public String getIsEsicApplicable() {
		return isEsicApplicable;
	}

	public void setIsEsicApplicable(String isEsicApplicable) {
		this.isEsicApplicable = isEsicApplicable;
	}

	public String getIsMiApplicable() {
		return isMiApplicable;
	}

	public void setIsMiApplicable(String isMiApplicable) {
		this.isMiApplicable = isMiApplicable;
	}

	public String getIsPfApplicable() {
		return isPfApplicable;
	}

	public void setIsPfApplicable(String isPfApplicable) {
		this.isPfApplicable = isPfApplicable;
	}

	public String getMedicalInsurance() {
		return medicalInsurance;
	}

	public void setMedicalInsurance(String medicalInsurance) {
		this.medicalInsurance = medicalInsurance;
	}
	public String getGradeName() {
		return gradeName;
	}

	public void setGradeName(String gradeName) {
		this.gradeName = gradeName;
	}

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

	public Long getAiFamilyId() {
		return aiFamilyId;
	}

	public void setAiFamilyId(Long aiFamilyId) {
		this.aiFamilyId = aiFamilyId;
	}

	public Long getMiFamilyId() {
		return miFamilyId;
	}

	public void setMiFamilyId(Long miFamilyId) {
		this.miFamilyId = miFamilyId;
	}

	public Long getPfFamilyId() {
		return pfFamilyId;
	}

	public void setPfFamilyId(Long pfFamilyId) {
		this.pfFamilyId = pfFamilyId;
	}

	public Long getEsicFamilyId() {
		return esicFamilyId;
	}

	public void setEsicFamilyId(Long esicFamilyId) {
		this.esicFamilyId = esicFamilyId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getBiometricId() {
		return biometricId;
	}

	public void setBiometricId(String biometricId) {
		this.biometricId = biometricId;
	}

	public String getOfficialEmail() {
		return officialEmail;
	}

	public void setOfficialEmail(String officialEmail) {
		this.officialEmail = officialEmail;
	}

}
