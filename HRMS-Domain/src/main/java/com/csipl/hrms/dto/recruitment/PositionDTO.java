package com.csipl.hrms.dto.recruitment;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class PositionDTO {

	private Long positionId;

	private Long approvePersonId;

	private Date dateCreated;

	private Date dateUpdated;

	private String employeementType;

	private Date expectedate;

	private BigDecimal extraBudget;

	private String extraBudgetRemark;

	private Long gradeId;

	private Long hiringManagerId;

	private Long jdId;

	private String jobLocation;

	private BigDecimal maxBudget;

	private String noOfLevel;

	private Long noOfPosition;

	private String positionCode;

	private String positionTitle;

	private String remark;

	private String requiredExperience;

	private String sourceOfPosion;

	private Long userId;

	private Long userIdUpdate;

	private Long employeeId;

	private String employeeName;

	private String interviewType;
	private Date endDate;
	private String positionStatus;
	private String employeeCode;
	private String gradeName;

	private Long levelId;
	private String departmentName;
	private String designationName;
	private Long noOfInterviewLevel;

	private String levelName;
	private List<PositionInterviewlevelXRefDTO> positionInterviewlevelXrefs;

	private String userName;

	private String userNameCode;

	private String hiringMangerName;
	private String hiringMangerCode;
	private String approvalPersonName;
	private String approvalPersonCode;
	private String extraBudgetStatus;
	private String positionType;
	private String empType;
	private String extraBudgetApprovalRemark;
	private String approvalRemark;
	
	
	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	public String getDesignationName() {
		return designationName;
	}

	public void setDesignationName(String designationName) {
		this.designationName = designationName;
	}

	public Long getNoOfInterviewLevel() {
		return noOfInterviewLevel;
	}

	public void setNoOfInterviewLevel(Long noOfInterviewLevel) {
		this.noOfInterviewLevel = noOfInterviewLevel;
	}

	public String getApprovalRemark() {
		return approvalRemark;
	}

	public void setApprovalRemark(String approvalRemark) {
		this.approvalRemark = approvalRemark;
	}

	public String getEmpType() {
		return empType;
	}

	public void setEmpType(String empType) {
		this.empType = empType;
	}

	public String getApprovalPersonName() {
		return approvalPersonName;
	}

	public void setApprovalPersonName(String approvalPersonName) {
		this.approvalPersonName = approvalPersonName;
	}

	public String getApprovalPersonCode() {
		return approvalPersonCode;
	}

	public void setApprovalPersonCode(String approvalPersonCode) {
		this.approvalPersonCode = approvalPersonCode;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserNameCode() {
		return userNameCode;
	}

	public void setUserNameCode(String userNameCode) {
		this.userNameCode = userNameCode;
	}

	public String getHiringMangerName() {
		return hiringMangerName;
	}

	public void setHiringMangerName(String hiringMangerName) {
		this.hiringMangerName = hiringMangerName;
	}

	public String getHiringMangerCode() {
		return hiringMangerCode;
	}

	public void setHiringMangerCode(String hiringMangerCode) {
		this.hiringMangerCode = hiringMangerCode;
	}

	public String getPositionStatus() {
		return positionStatus;
	}

	public void setPositionStatus(String positionStatus) {
		this.positionStatus = positionStatus;
	}

	public Long getPositionId() {
		return positionId;
	}

	public void setPositionId(Long positionId) {
		this.positionId = positionId;
	}

	public Long getApprovePersonId() {
		return approvePersonId;
	}

	public void setApprovePersonId(Long approvePersonId) {
		this.approvePersonId = approvePersonId;
	}

	public Date getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	public Date getDateUpdated() {
		return dateUpdated;
	}

	public void setDateUpdated(Date dateUpdated) {
		this.dateUpdated = dateUpdated;
	}

 
	public Date getExpectedate() {
		return expectedate;
	}

	public void setExpectedate(Date expectedate) {
		this.expectedate = expectedate;
	}

	public BigDecimal getExtraBudget() {
		return extraBudget;
	}

	public void setExtraBudget(BigDecimal extraBudget) {
		this.extraBudget = extraBudget;
	}

	public String getExtraBudgetRemark() {
		return extraBudgetRemark;
	}

	public void setExtraBudgetRemark(String extraBudgetRemark) {
		this.extraBudgetRemark = extraBudgetRemark;
	}

	public Long getGradeId() {
		return gradeId;
	}

	public void setGradeId(Long gradeId) {
		this.gradeId = gradeId;
	}

	public Long getHiringManagerId() {
		return hiringManagerId;
	}

	public void setHiringManagerId(Long hiringManagerId) {
		this.hiringManagerId = hiringManagerId;
	}

	public String getInterviewType() {
		return interviewType;
	}

	public void setInterviewType(String interviewType) {
		this.interviewType = interviewType;
	}

	public Long getJdId() {
		return jdId;
	}

	public void setJdId(Long jdId) {
		this.jdId = jdId;
	}

	public String getJobLocation() {
		return jobLocation;
	}

	public void setJobLocation(String jobLocation) {
		this.jobLocation = jobLocation;
	}

	public BigDecimal getMaxBudget() {
		return maxBudget;
	}

	public void setMaxBudget(BigDecimal maxBudget) {
		this.maxBudget = maxBudget;
	}

	 

	public Long getNoOfPosition() {
		return noOfPosition;
	}

	public void setNoOfPosition(Long noOfPosition) {
		this.noOfPosition = noOfPosition;
	}

	public String getPositionCode() {
		return positionCode;
	}

	public void setPositionCode(String positionCode) {
		this.positionCode = positionCode;
	}

	public String getPositionTitle() {
		return positionTitle;
	}

	public void setPositionTitle(String positionTitle) {
		this.positionTitle = positionTitle;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	 


	public String getRequiredExperience() {
		return requiredExperience;
	}

	public void setRequiredExperience(String requiredExperience) {
		this.requiredExperience = requiredExperience;
	}

	public String getEmployeementType() {
		return employeementType;
	}

	public void setEmployeementType(String employeementType) {
		this.employeementType = employeementType;
	}

	public String getNoOfLevel() {
		return noOfLevel;
	}

	public void setNoOfLevel(String noOfLevel) {
		this.noOfLevel = noOfLevel;
	}

	public String getSourceOfPosion() {
		return sourceOfPosion;
	}

	public void setSourceOfPosion(String sourceOfPosion) {
		this.sourceOfPosion = sourceOfPosion;
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

	public List<PositionInterviewlevelXRefDTO> getPositionInterviewlevelXrefs() {
		return positionInterviewlevelXrefs;
	}

	public void setPositionInterviewlevelXrefs(List<PositionInterviewlevelXRefDTO> positionInterviewlevelXrefs) {
		this.positionInterviewlevelXrefs = positionInterviewlevelXrefs;
	}

	public Long getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(Long employeeId) {
		this.employeeId = employeeId;
	}

	public String getEmployeeName() {
		return employeeName;
	}

	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

	public String getGradeName() {
		return gradeName;
	}

	public void setGradeName(String gradeName) {
		this.gradeName = gradeName;
	}

	public Long getLevelId() {
		return levelId;
	}

	public void setLevelId(Long levelId) {
		this.levelId = levelId;
	}

	public String getLevelName() {
		return levelName;
	}

	public void setLevelName(String levelName) {
		this.levelName = levelName;
	}

	public String getPositionType() {
		return positionType;
	}

	public void setPositionType(String positionType) {
		this.positionType = positionType;
	}

	public String getExtraBudgetStatus() {
		return extraBudgetStatus;
	}

	public void setExtraBudgetStatus(String extraBudgetStatus) {
		this.extraBudgetStatus = extraBudgetStatus;
	}

	public String getExtraBudgetApprovalRemark() {
		return extraBudgetApprovalRemark;
	}

	public void setExtraBudgetApprovalRemark(String extraBudgetApprovalRemark) {
		this.extraBudgetApprovalRemark = extraBudgetApprovalRemark;
	}

	public String getEmployeeCode() {
		return employeeCode;
	}

	public void setEmployeeCode(String employeeCode) {
		this.employeeCode = employeeCode;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

}