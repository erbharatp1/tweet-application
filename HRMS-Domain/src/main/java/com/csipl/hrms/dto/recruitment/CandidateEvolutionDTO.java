package com.csipl.hrms.dto.recruitment;


import java.util.Date;

public class CandidateEvolutionDTO {

	private Long evalutionId;

	private Long interviewScheduleId;

	private Long levelId;

	private String interviewMode;

	private String interviewTime;

	private Date interviewDate;
	private Long userId;

	private Date dateCreated;
	
	private String status;
	
	
	private String remark;
	

	public Long getEvalutionId() {
		return evalutionId;
	}

	public void setEvalutionId(Long evalutionId) {
		this.evalutionId = evalutionId;
	}

	public Long getInterviewScheduleId() {
		return interviewScheduleId;
	}

	public void setInterviewScheduleId(Long interviewScheduleId) {
		this.interviewScheduleId = interviewScheduleId;
	}

	public Long getLevelId() {
		return levelId;
	}

	public void setLevelId(Long levelId) {
		this.levelId = levelId;
	}

	public String getInterviewMode() {
		return interviewMode;
	}

	public void setInterviewMode(String interviewMode) {
		this.interviewMode = interviewMode;
	}

	

	public Date getInterviewDate() {
		return interviewDate;
	}

	public void setInterviewDate(Date interviewDate) {
		this.interviewDate = interviewDate;
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

	public String getInterviewTime() {
		return interviewTime;
	}

	public void setInterviewTime(String interviewTime) {
		this.interviewTime = interviewTime;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	

}
