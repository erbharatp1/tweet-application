package com.csipl.hrms.dto.recruitment;

import java.util.Date;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;

public class CandidateLetterDTO {

	private Long candidateLetterId;

	private String activeStatus;

	private String candidateStatus;

	@Temporal(TemporalType.TIMESTAMP)
	private Date dateCreated;

	@Temporal(TemporalType.DATE)
	private Date declerationDate;

	private String declerationStatus;

	private String hrStatus;

	private String letterDescription;

	private Long letterId;

	private Long interviewScheduleId;

	private String releaseStatus;

	private Long userId;

	private String hideAnnexure;
	
	private String annexureStatus;

	public String getActiveStatus() {
		return activeStatus;
	}

	public void setActiveStatus(String activeStatus) {
		this.activeStatus = activeStatus;
	}

	public String getCandidateStatus() {
		return candidateStatus;
	}

	public void setCandidateStatus(String candidateStatus) {
		this.candidateStatus = candidateStatus;
	}

	public Date getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	public Date getDeclerationDate() {
		return declerationDate;
	}

	public void setDeclerationDate(Date declerationDate) {
		this.declerationDate = declerationDate;
	}

	public String getDeclerationStatus() {
		return declerationStatus;
	}

	public void setDeclerationStatus(String declerationStatus) {
		this.declerationStatus = declerationStatus;
	}

	public String getHrStatus() {
		return hrStatus;
	}

	public void setHrStatus(String hrStatus) {
		this.hrStatus = hrStatus;
	}

	public String getLetterDescription() {
		return letterDescription;
	}

	public void setLetterDescription(String letterDescription) {
		this.letterDescription = letterDescription;
	}

	public Long getCandidateLetterId() {
		return candidateLetterId;
	}

	public void setCandidateLetterId(Long candidateLetterId) {
		this.candidateLetterId = candidateLetterId;
	}

	public Long getLetterId() {
		return letterId;
	}

	public void setLetterId(Long letterId) {
		this.letterId = letterId;
	}

	public String getReleaseStatus() {
		return releaseStatus;
	}

	public void setReleaseStatus(String releaseStatus) {
		this.releaseStatus = releaseStatus;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getInterviewScheduleId() {
		return interviewScheduleId;
	}

	public void setInterviewScheduleId(Long interviewScheduleId) {
		this.interviewScheduleId = interviewScheduleId;
	}

	public String getHideAnnexure() {
		return hideAnnexure;
	}

	public void setHideAnnexure(String hideAnnexure) {
		this.hideAnnexure = hideAnnexure;
	}

	public String getAnnexureStatus() {
		return annexureStatus;
	}

	public void setAnnexureStatus(String annexureStatus) {
		this.annexureStatus = annexureStatus;
	}

}
