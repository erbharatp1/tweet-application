package com.csipl.hrms.dto.common;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TMSWeekOffMasterPatternDto {

	private Long patternId;

	private String activeStatus;

	private Long companyId;

	private Date createdDate;

	private String patternName;

	private Date updatedDate;

	private Long updateUserId;

	private Long userId;
	
	@JsonProperty( "weekOffDto" )
	private  List<TMSWeekOffMasterPatternDto> weekOffDto = new ArrayList<TMSWeekOffMasterPatternDto>();
	

//	public List<TMSWeekOffChildPatternDTO> getTmsweekOffChildPatternsDTO() {
//		return tmsweekOffChildPatternsDTO;
//	}
//
//	public void setTmsweekOffChildPatternsDTO(List<TMSWeekOffChildPatternDTO> tmsweekOffChildPatternsDTO) {
//		this.tmsweekOffChildPatternsDTO = tmsweekOffChildPatternsDTO;
//	}
//
//	private List<TMSWeekOffChildPatternDTO> tmsweekOffChildPatternsDTO;

	public Long getPatternId() {
		return this.patternId;
	}

	public void setPatternId(Long patternId) {
		this.patternId = patternId;
	}

	public String getActiveStatus() {
		return this.activeStatus;
	}

	public void setActiveStatus(String activeStatus) {
		this.activeStatus = activeStatus;
	}

	public Long getCompanyId() {
		return this.companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	public Date getCreatedDate() {
		return this.createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public String getPatternName() {
		return this.patternName;
	}

	public void setPatternName(String patternName) {
		this.patternName = patternName;
	}

	public Date getUpdatedDate() {
		return this.updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	public Long getUpdateUserId() {
		return this.updateUserId;
	}

	public void setUpdateUserId(Long updateUserId) {
		this.updateUserId = updateUserId;
	}

	public Long getUserId() {
		return this.userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public List<TMSWeekOffMasterPatternDto> getWeekOffDto() {
		return weekOffDto;
	}

	public void setWeekOffDto(List<TMSWeekOffMasterPatternDto> weekOffDto) {
		this.weekOffDto = weekOffDto;
	}
	
}
