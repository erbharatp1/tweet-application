package com.csipl.hrms.model.recruitment;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * The persistent class for the Position database table.
 * 
 */
@Entity
@NamedQuery(name = "Position.findAll", query = "SELECT p FROM Position p")
public class Position implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long positionId;

	private Long approvePersonId;

	@Temporal(TemporalType.DATE)
	private Date dateCreated;

	@Temporal(TemporalType.DATE)
	private Date dateUpdated;

	private String employeementType;

	@Temporal(TemporalType.DATE)
	private Date expectedate;

	private BigDecimal extraBudget;

	private String extraBudgetRemark;
	private String interviewType;

	private Long gradeId;

	private Long hiringManagerId;

	@Column(name = "JdId")
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

	private String positionStatus;
	private String positionType;
	private String extraBudgetStatus;
	private String extraBudgetApprovalRemark;
	private String approvalRemark;
	private Long noOfInterviewLevel;
	// bi-directional many-to-one association to PositionInterviewlevelXRef
	@OneToMany(mappedBy = "position", cascade = CascadeType.ALL)
	private List<PositionInterviewlevelXRef> positionInterviewlevelXrefs;

	public Position() {
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

	public String getPositionStatus() {
		return positionStatus;
	}

	public void setPositionStatus(String positionStatus) {
		this.positionStatus = positionStatus;
	}

	public String getInterviewType() {
		return interviewType;
	}

	public void setInterviewType(String interviewType) {
		this.interviewType = interviewType;
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

	public List<PositionInterviewlevelXRef> getPositionInterviewlevelXrefs() {
		return positionInterviewlevelXrefs;
	}

	public void setPositionInterviewlevelXrefs(List<PositionInterviewlevelXRef> positionInterviewlevelXrefs) {
		this.positionInterviewlevelXrefs = positionInterviewlevelXrefs;
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

}