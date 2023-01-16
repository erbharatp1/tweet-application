package com.csipl.hrms.dto.common;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;



public class LeaveSchemeMasterDTO {

	private Long leaveSchemeId;

	private Date dateCreated;

	private Date dateUpdates;

	private String leaveSchemeName;

	private Long userId;

	private Long userIdUpdate;
	
	
	// added to call rest template
	@JsonProperty( "leaveSchemeMasterDto" )
	private  List<LeaveSchemeMasterDTO> leaveSchemeMasterDto = new ArrayList<LeaveSchemeMasterDTO>();

	public Long getLeaveSchemeId() {
		return leaveSchemeId;
	}

	public void setLeaveSchemeId(Long leaveSchemeId) {
		this.leaveSchemeId = leaveSchemeId;
	}

	public Date getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	public Date getDateUpdates() {
		return dateUpdates;
	}

	public void setDateUpdates(Date dateUpdates) {
		this.dateUpdates = dateUpdates;
	}

	public String getLeaveSchemeName() {
		return leaveSchemeName;
	}

	public void setLeaveSchemeName(String leaveSchemeName) {
		this.leaveSchemeName = leaveSchemeName;
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

	public List<LeaveSchemeMasterDTO> getLeaveSchemeMasterDto() {
		return leaveSchemeMasterDto;
	}

	public void setLeaveSchemeMasterDto(List<LeaveSchemeMasterDTO> leaveSchemeMasterDto) {
		this.leaveSchemeMasterDto = leaveSchemeMasterDto;
	}
}
