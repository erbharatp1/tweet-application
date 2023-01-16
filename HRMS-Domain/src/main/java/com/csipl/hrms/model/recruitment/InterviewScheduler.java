package com.csipl.hrms.model.recruitment;

import java.io.Serializable;
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

@Entity
@NamedQuery(name = "InterviewScheduler.findAll", query = "SELECT i FROM InterviewScheduler i")
public class InterviewScheduler implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long interviewScheduleId;

	private String candidateName;

	private String candidateContactNo;

	private String candidateEmailId;

	
	// bi-directional many-to-one association to Position
	@ManyToOne
	@JoinColumn(name = "positionId")
	private Position position;

	private Long recuiterId;

	private String sourceOfProfile;

	private Long userId;

	@Temporal(TemporalType.DATE)
	private Date dateCreated;

	private Long userIdUpdate;

	@Temporal(TemporalType.DATE)
	private Date dateUpdated;
	
	
	private String activeStatus;
	private String declineRemark;

	@OneToMany(mappedBy = "interviewScheduler", cascade = CascadeType.ALL)
	private List<CandidateEvolution> candidateEvolution;

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


	public Position getPosition() {
		return position;
	}

	public void setPosition(Position position) {
		this.position = position;
	}

	public Long getRecuiterId() {
		return recuiterId;
	}

	public void setRecuiterId(Long recuiterId) {
		this.recuiterId = recuiterId;
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

	public List<CandidateEvolution> getCandidateEvolution() {
		return candidateEvolution;
	}

	public void setCandidateEvolution(List<CandidateEvolution> candidateEvolution) {
		this.candidateEvolution = candidateEvolution;
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

	public String getSourceOfProfile() {
		return sourceOfProfile;
	}

	public void setSourceOfProfile(String sourceOfProfile) {
		this.sourceOfProfile = sourceOfProfile;
	}

	
	
	
	
	
	
	

}
