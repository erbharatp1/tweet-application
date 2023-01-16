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

import com.csipl.hrms.model.employee.Letter;

/**
 * The persistent class for the CandidateLetter database table.
 * 
 */
@Entity
@NamedQuery(name = "CandidateLetter.findAll", query = "SELECT c FROM CandidateLetter c")
public class CandidateLetter implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
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

	private String releaseStatus;

	private Long userId;

	private String hideAnnexure;

	private String annexureStatus;

	// bi-directional many-to-one association to Letter
	@ManyToOne
	@JoinColumn(name = "letterId")
	private Letter letter;

	// bi-directional many-to-one association to Letter
	@ManyToOne
	@JoinColumn(name = "interviewScheduleId")
	private InterviewScheduler interviewScheduler;

	public CandidateLetter() {
	}

	public String getActiveStatus() {
		return this.activeStatus;
	}

	public void setActiveStatus(String activeStatus) {
		this.activeStatus = activeStatus;
	}

	public String getCandidateStatus() {
		return this.candidateStatus;
	}

	public void setCandidateStatus(String candidateStatus) {
		this.candidateStatus = candidateStatus;
	}

	public Date getDateCreated() {
		return this.dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	public Date getDeclerationDate() {
		return this.declerationDate;
	}

	public void setDeclerationDate(Date declerationDate) {
		this.declerationDate = declerationDate;
	}

	public String getDeclerationStatus() {
		return this.declerationStatus;
	}

	public void setDeclerationStatus(String declerationStatus) {
		this.declerationStatus = declerationStatus;
	}

	public String getHrStatus() {
		return this.hrStatus;
	}

	public void setHrStatus(String hrStatus) {
		this.hrStatus = hrStatus;
	}

	public String getLetterDescription() {
		return this.letterDescription;
	}

	public void setLetterDescription(String letterDescription) {
		this.letterDescription = letterDescription;
	}

	public String getReleaseStatus() {
		return this.releaseStatus;
	}

	public void setReleaseStatus(String releaseStatus) {
		this.releaseStatus = releaseStatus;
	}

	public Long getCandidateLetterId() {
		return candidateLetterId;
	}

	public void setCandidateLetterId(Long candidateLetterId) {
		this.candidateLetterId = candidateLetterId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public InterviewScheduler getInterviewScheduler() {
		return interviewScheduler;
	}

	public void setInterviewScheduler(InterviewScheduler interviewScheduler) {
		this.interviewScheduler = interviewScheduler;
	}

	public Letter getLetter() {
		return this.letter;
	}

	public void setLetter(Letter letter) {
		this.letter = letter;
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