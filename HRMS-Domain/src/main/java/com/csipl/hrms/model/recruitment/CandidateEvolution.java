package com.csipl.hrms.model.recruitment;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


@Entity
@NamedQuery(name="CandidateEvolution.findAll", query="SELECT c FROM CandidateEvolution c")
public class CandidateEvolution implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long evalutionId;
	
	@ManyToOne
	@JoinColumn(name="interviewScheduleId")
	private InterviewScheduler interviewScheduler;
	
	@ManyToOne
	@JoinColumn(name="levelId")
	private PositionInterviewlevelXRef positionInterviewlevelXRef;
	
	
	private String interviewMode;
	
	private String interviewTime;
	
	@Temporal(TemporalType.DATE)
	private Date interviewDate;
	
	private String status;
	
	private String remark;
	
	private Long userId;
	
	private String filePath;
	private String fileName;
	private String isInterviewScheduled;
	
	@Temporal(TemporalType.DATE)
	private Date dateUpdated;
	
	public Date getDateUpdated() {
		return dateUpdated;
	}

	public void setDateUpdated(Date dateUpdated) {
		this.dateUpdated = dateUpdated;
	}

	@Temporal(TemporalType.TIMESTAMP)
	private Date dateCreated;

	public Long getEvalutionId() {
		return evalutionId;
	}

	public void setEvalutionId(Long evalutionId) {
		this.evalutionId = evalutionId;
	}

	public InterviewScheduler getInterviewScheduler() {
		return interviewScheduler;
	}

	public void setInterviewScheduler(InterviewScheduler interviewScheduler) {
		this.interviewScheduler = interviewScheduler;
	}

	public PositionInterviewlevelXRef getPositionInterviewlevelXRef() {
		return positionInterviewlevelXRef;
	}

	public void setPositionInterviewlevelXRef(PositionInterviewlevelXRef positionInterviewlevelXRef) {
		this.positionInterviewlevelXRef = positionInterviewlevelXRef;
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

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getIsInterviewScheduled() {
		return isInterviewScheduled;
	}

	public void setIsInterviewScheduled(String isInterviewScheduled) {
		this.isInterviewScheduled = isInterviewScheduled;
	}
	
	
	
	
	

}
