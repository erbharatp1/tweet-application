package com.csipl.hrms.model.recruitment;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * shubham yaduwanshi
 *
 */
@Entity
@NamedQuery(name = "SelectionRuleDescription.findAll", query = "SELECT p FROM SelectionRuleDescription p")
public class SelectionRuleDescription {
    
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long selectionRuleId;
	
	private String selectionRuleDescription;
	
	private String status;
	
	private Long userId;
	
	private Long userIdUpdate;
	
	@Temporal(TemporalType.DATE)
	private Date dateCreated;
	
	@Temporal(TemporalType.DATE)
	private Date updatedDate;

	public Long getSelectionRuleId() {
		return selectionRuleId;
	}
	public void setSelectionRuleId(Long selectionRuleId) {
		this.selectionRuleId = selectionRuleId;
	}
	public String getSelectionRuleDescription() {
		return selectionRuleDescription;
	}
	public void setSelectionRuleDescription(String selectionRuleDescription) {
		this.selectionRuleDescription = selectionRuleDescription;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
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
	public Date getDateCreated() {
		return dateCreated;
	}
	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}
	public Date getUpdatedDate() {
		return updatedDate;
	}
	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}
	
}
