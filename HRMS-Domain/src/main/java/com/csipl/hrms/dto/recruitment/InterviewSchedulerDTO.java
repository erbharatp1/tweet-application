package com.csipl.hrms.dto.recruitment;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class InterviewSchedulerDTO {
	private Long interviewScheduleId;

	private String candidateName;

	private String candidateContactNo;

	private String candidateEmailId;

	private Long positionId;

	private Long recuiterId;

	private String sourceOfProfile;

	private Long userId;
	private Date dateCreated;

	private Long userIdUpdate;

	private Date dateUpdated;

	private Date interviewDate;
	private String interviewTime;
	private String interviewMode;
	private String positionTitle;
	private String positionCode;
	private String requiredExperience;
	private String noOfLevel;
	private String recuiterName;
	private String level;
	private String jobLocation;
	private String gradeName;
	private String  positionType;
	private String employeeName;

	private String finalStatus;
	private Long levelId;
	private String activeStatus;
	private String declineRemark;
	private Long evalutionId;
	private String levelIndex;
	private BigDecimal levelPercentage;
	private Long noOfInterviewLevel;
	private Long gradeId;
	private Long finalEvolutionId;

	private List<CandidateEvolutionDTO> candidateEvolutionList;
	private List<InterviewLevelDTO> InterviewLevelDTOList;

	public List<InterviewLevelDTO> getInterviewLevelDTOList() {
		return InterviewLevelDTOList;
	}

	public void setInterviewLevelDTOList(List<InterviewLevelDTO> interviewLevelDTOList) {
		InterviewLevelDTOList = interviewLevelDTOList;
	}

	public Long getInterviewScheduleId() {
		return interviewScheduleId;
	}

	public void setInterviewScheduleId(Long interviewScheduleId) {
		this.interviewScheduleId = interviewScheduleId;
	}

	public String getCandidateName() {
		return candidateName;
	}

	public void setCandidateName(String candidateName) {
		this.candidateName = candidateName;
	}

	public String getCandidateContactNo() {
		return candidateContactNo;
	}

	public void setCandidateContactNo(String candidateContactNo) {
		this.candidateContactNo = candidateContactNo;
	}

	public String getCandidateEmailId() {
		return candidateEmailId;
	}

	public void setCandidateEmailId(String candidateEmailId) {
		this.candidateEmailId = candidateEmailId;
	}

	public Long getPositionId() {
		return positionId;
	}

	public void setPositionId(Long positionId) {
		this.positionId = positionId;
	}

	public Long getRecuiterId() {
		return recuiterId;
	}

	public void setRecuiterId(Long recuiterId) {
		this.recuiterId = recuiterId;
	}

	
	public String getSourceOfProfile() {
		return sourceOfProfile;
	}

	public void setSourceOfProfile(String sourceOfProfile) {
		this.sourceOfProfile = sourceOfProfile;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Date getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	public Long getUserIdUpdate() {
		return userIdUpdate;
	}

	public void setUserIdUpdate(Long userIdUpdate) {
		this.userIdUpdate = userIdUpdate;
	}

	public Date getDateUpdated() {
		return dateUpdated;
	}

	public void setDateUpdated(Date dateUpdated) {
		this.dateUpdated = dateUpdated;
	}

	public List<CandidateEvolutionDTO> getCandidateEvolutionList() {
		return candidateEvolutionList;
	}

	public void setCandidateEvolutionList(List<CandidateEvolutionDTO> candidateEvolutionList) {
		this.candidateEvolutionList = candidateEvolutionList;
	}

	public Date getInterviewDate() {
		return interviewDate;
	}

	public void setInterviewDate(Date interviewDate) {
		this.interviewDate = interviewDate;
	}

	public String getInterviewTime() {
		return interviewTime;
	}

	public void setInterviewTime(String interviewTime) {
		this.interviewTime = interviewTime;
	}

	public String getInterviewMode() {
		return interviewMode;
	}

	public void setInterviewMode(String interviewMode) {
		this.interviewMode = interviewMode;
	}

	public String getPositionTitle() {
		return positionTitle;
	}

	public void setPositionTitle(String positionTitle) {
		this.positionTitle = positionTitle;
	}

	public String getPositionCode() {
		return positionCode;
	}

	public void setPositionCode(String positionCode) {
		this.positionCode = positionCode;
	}

	 

	

	

	public String getRequiredExperience() {
		return requiredExperience;
	}

	public void setRequiredExperience(String requiredExperience) {
		this.requiredExperience = requiredExperience;
	}

	public String getEmployeeName() {
		return employeeName;
	}

	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}


	public Long getLevelId() {
		return levelId;
	}

	public void setLevelId(Long levelId) {
		this.levelId = levelId;
	}

	
	
	
	
	
	
	


	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public String getJobLocation() {
		return jobLocation;
	}

	public void setJobLocation(String jobLocation) {
		this.jobLocation = jobLocation;
	}

	public String getGradeName() {
		return gradeName;
	}

	public void setGradeName(String gradeName) {
		this.gradeName = gradeName;
	}

	public String getRecuiterName() {
		return recuiterName;
	}

	public void setRecuiterName(String recuiterName) {
		this.recuiterName = recuiterName;
	}


	public String getPositionType() {
		return positionType;
	}

	public void setPositionType(String positionType) {
		this.positionType = positionType;
	}

	public String getFinalStatus() {
		return finalStatus;
	}

	public void setFinalStatus(String finalStatus) {
		this.finalStatus = finalStatus;
	}


	public String getActiveStatus() {
		return activeStatus;
	}

	public void setActiveStatus(String activeStatus) {
		this.activeStatus = activeStatus;
	}

	public String getDeclineRemark() {
		return declineRemark;
	}

	public void setDeclineRemark(String declineRemark) {
		this.declineRemark = declineRemark;
	}

	public String getNoOfLevel() {
		return noOfLevel;
	}

	public void setNoOfLevel(String noOfLevel) {
		this.noOfLevel = noOfLevel;
	}

	public Long getEvalutionId() {
		return evalutionId;
	}

	public void setEvalutionId(Long evalutionId) {
		this.evalutionId = evalutionId;
	}

	public String getLevelIndex() {
		return levelIndex;
	}

	public void setLevelIndex(String levelIndex) {
		this.levelIndex = levelIndex;
	}

	public BigDecimal getLevelPercentage() {
		return levelPercentage;
	}

	public void setLevelPercentage(BigDecimal levelPercentage) {
		this.levelPercentage = levelPercentage;
	}

	public Long getNoOfInterviewLevel() {
		return noOfInterviewLevel;
	}

	public void setNoOfInterviewLevel(Long noOfInterviewLevel) {
		this.noOfInterviewLevel = noOfInterviewLevel;
	}

	public Long getGradeId() {
		return gradeId;
	}

	public void setGradeId(Long gradeId) {
		this.gradeId = gradeId;
	}

	public Long getFinalEvolutionId() {
		return finalEvolutionId;
	}

	public void setFinalEvolutionId(Long finalEvolutionId) {
		this.finalEvolutionId = finalEvolutionId;
	}

	

	 

}
