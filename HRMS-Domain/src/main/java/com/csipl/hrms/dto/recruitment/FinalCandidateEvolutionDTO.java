package com.csipl.hrms.dto.recruitment;

import java.util.Date;

public class FinalCandidateEvolutionDTO {

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
