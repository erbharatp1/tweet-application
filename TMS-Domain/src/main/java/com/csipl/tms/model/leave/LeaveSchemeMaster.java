package com.csipl.tms.model.leave;


import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the LeaveSchemeMaster database table.
 * 
 */
@Entity
@NamedQuery(name="LeaveSchemeMaster.findAll", query="SELECT l FROM LeaveSchemeMaster l")
public class LeaveSchemeMaster implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long leaveSchemeId;

	@Temporal(TemporalType.TIMESTAMP)
	private Date dateCreated;

	@Temporal(TemporalType.TIMESTAMP)
	private Date dateUpdates;

	private String leaveSchemeName;

	private Long userId;

	private Long userIdUpdate;
	private String status;
  private Long leavePeriodId;
	public LeaveSchemeMaster() {
	}

	public Long getLeaveSchemeId() {
		return this.leaveSchemeId;
	}

	public void setLeaveSchemeId(Long leaveSchemeId) {
		this.leaveSchemeId = leaveSchemeId;
	}

	public Date getDateCreated() {
		return this.dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	public Date getDateUpdates() {
		return this.dateUpdates;
	}

	public void setDateUpdates(Date dateUpdates) {
		this.dateUpdates = dateUpdates;
	}

	public String getLeaveSchemeName() {
		return this.leaveSchemeName;
	}

	public void setLeaveSchemeName(String leaveSchemeName) {
		this.leaveSchemeName = leaveSchemeName;
	}

	public Long getUserId() {
		return this.userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getUserIdUpdate() {
		return this.userIdUpdate;
	}

	public void setUserIdUpdate(Long userIdUpdate) {
		this.userIdUpdate = userIdUpdate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Long getLeavePeriodId() {
		return leavePeriodId;
	}

	public void setLeavePeriodId(Long leavePeriodId) {
		this.leavePeriodId = leavePeriodId;
	}

}