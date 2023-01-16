package com.csipl.hrms.model.recruitment;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;

@Entity
@NamedQuery(name = "CandidateFinalEvalution.findAll", query = "SELECT f FROM CandidateFinalEvalution f")
public class CandidateFinalEvalution implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private Long interviewScheduledId;

	private String candidateName;

	private String contactNo;

	private String finalStatus;

	private String emailId;

	private Long positionId;

	private String recruiterName;

	private Date positionClosedBy;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getInterviewScheduledId() {
		return interviewScheduledId;
	}

	public void setInterviewScheduledId(Long interviewScheduledId) {
		this.interviewScheduledId = interviewScheduledId;
	}

	public String getCandidateName() {
		return candidateName;
	}

	public void setCandidateName(String candidateName) {
		this.candidateName = candidateName;
	}

	public String getContactNo() {
		return contactNo;
	}

	public void setContactNo(String contactNo) {
		this.contactNo = contactNo;
	}

	public String getFinalStatus() {
		return finalStatus;
	}

	public void setFinalStatus(String finalStatus) {
		this.finalStatus = finalStatus;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public Long getPositionId() {
		return positionId;
	}

	public void setPositionId(Long positionId) {
		this.positionId = positionId;
	}

	public String getRecruiterName() {
		return recruiterName;
	}

	public void setRecruiterName(String recruiterName) {
		this.recruiterName = recruiterName;
	}

	public Date getPositionClosedBy() {
		return positionClosedBy;
	}

	public void setPositionClosedBy(Date positionClosedBy) {
		this.positionClosedBy = positionClosedBy;
	}

}
