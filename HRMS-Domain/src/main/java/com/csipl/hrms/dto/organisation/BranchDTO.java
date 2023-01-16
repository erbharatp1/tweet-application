package com.csipl.hrms.dto.organisation;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BranchDTO {
	private Long branchId;
	private String branchName;
	private AddressDTO address;


	private Long userId;
	private Date dateCreated; 
	private Long companyId;
	private Long userIdUpdate;
	private String activeStatus;
	
	
	@JsonProperty( "branchDto" )
	private List<BranchDTO> branchDto= new ArrayList<BranchDTO>();
	
	public Long getBranchId() {
		return branchId;
	}
	public void setBranchId(Long branchId) {
		this.branchId = branchId;
	}
	public List<BranchDTO> getBranchDto() {
		return branchDto;
	}
	public void setBranchDto(List<BranchDTO> branchDto) {
		this.branchDto = branchDto;
	}
	public String getBranchName() {
		return branchName;
	}
	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}
	public AddressDTO getAddress() {
		return address;
	}
	public void setAddress(AddressDTO address) {
		this.address = address;
	}

	public Long getUserId() {
		return userId;
	}
	public Date getDateCreated() {
		return dateCreated;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}
	 
	public Long getCompanyId() {
		return companyId;
	}
	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}
	public Long getUserIdUpdate() {
		return userIdUpdate;
	}
	public void setUserIdUpdate(Long userIdUpdate) {
		this.userIdUpdate = userIdUpdate;
	}
	@Override
	public String toString() {
		return "BranchDTO [address=" + address + "]";
	}
	public String getActiveStatus() {
		return activeStatus;
	}
	public void setActiveStatus(String activeStatus) {
		this.activeStatus = activeStatus;
	}
	 

}
