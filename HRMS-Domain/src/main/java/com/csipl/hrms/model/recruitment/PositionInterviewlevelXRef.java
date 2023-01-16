package com.csipl.hrms.model.recruitment;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the PositionInterviewlevelXRef database table.
 * 
 */
@Entity
@NamedQuery(name="PositionInterviewlevelXRef.findAll", query="SELECT p FROM PositionInterviewlevelXRef p")
public class PositionInterviewlevelXRef implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long levelId;

	private String contactNo;

	@Temporal(TemporalType.DATE)
	private Date datecreated;

	@Temporal(TemporalType.DATE)
	private Date dateUpdated;

	private String emailId;

	private String externalInterviewerName;

	private String interviewLevelType;

	private String levelName;

	private Long userId;

	private Long userIdUpdated;
	
	private Long internalInterviewerId;
	
	private String levelIndex;

	//bi-directional many-to-one association to Position
	@ManyToOne
	@JoinColumn(name="positionId")
	private Position position;

	public PositionInterviewlevelXRef() {
	}

	 

	public Long getInternalInterviewerId() {
		return internalInterviewerId;
	}



	public void setInternalInterviewerId(Long internalInterviewerId) {
		this.internalInterviewerId = internalInterviewerId;
	}



	public String getLevelIndex() {
		return levelIndex;
	}



	public void setLevelIndex(String levelIndex) {
		this.levelIndex = levelIndex;
	}



	public String getContactNo() {
		return this.contactNo;
	}

	public void setContactNo(String contactNo) {
		this.contactNo = contactNo;
	}

	public Date getDatecreated() {
		return this.datecreated;
	}

	public void setDatecreated(Date datecreated) {
		this.datecreated = datecreated;
	}

	public Date getDateUpdated() {
		return this.dateUpdated;
	}

	public void setDateUpdated(Date dateUpdated) {
		this.dateUpdated = dateUpdated;
	}

	public String getEmailId() {
		return this.emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getExternalInterviewerName() {
		return this.externalInterviewerName;
	}

	public void setExternalInterviewerName(String externalInterviewerName) {
		this.externalInterviewerName = externalInterviewerName;
	}

	public String getInterviewLevelType() {
		return this.interviewLevelType;
	}

	public void setInterviewLevelType(String interviewLevelType) {
		this.interviewLevelType = interviewLevelType;
	}

	public String getLevelName() {
		return this.levelName;
	}

	public void setLevelName(String levelName) {
		this.levelName = levelName;
	}

 

	public Long getLevelId() {
		return levelId;
	}



	public void setLevelId(Long levelId) {
		this.levelId = levelId;
	}



	public Long getUserId() {
		return userId;
	}



	public void setUserId(Long userId) {
		this.userId = userId;
	}



	public Long getUserIdUpdated() {
		return userIdUpdated;
	}



	public void setUserIdUpdated(Long userIdUpdated) {
		this.userIdUpdated = userIdUpdated;
	}



	public Position getPosition() {
		return this.position;
	}

	public void setPosition(Position position) {
		this.position = position;
	}

}