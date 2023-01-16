package com.csipl.hrms.dto.organisation;

import java.math.BigDecimal;
import java.util.Date;

  


public class CompanyDTO {
 
	
	private String companyName;
	private Long companyId;
	private GroupDTO groupg;
	private Long groupId;
	private String groupName;
	private AddressDTO address1;
	private AddressDTO address2;
	private Long retirementAge;
	private String panNo;
	private String registrationNo;
	private String epfNo;
	private String esicNo;
	private String gstNo;
	private Long userId;
	private Date dateCreated;
	private Long userIdUpdate;
	private String domainName;
 	private Date dateOfBirth;
 	private String companyLogoPath;
 	
 	private String activeStatus;
 	private String allowModi;
 	private String authorizedPerson;
 	private String companyAbbreviation;
  	private Date dateUpdate;
 	private Date effectiveEndDate;
  	private Date effectiveStartDate;
 	private String emailId;
  	private String gumastaNo;
 	private String importExportCode;
 	private String mobile;
 	private String nagarnigamNo;
   	private String tanNo;
 	private String typeOfIndustry;
   	private String website;
   	private String prefix;
   	private BigDecimal series;
   	
	public String getPrefix() {
		return prefix;
	}
	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}
	public BigDecimal getSeries() {
		return series;
	}
	public void setSeries(BigDecimal series) {
		this.series = series;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public Long getCompanyId() {
		return companyId;
	}
	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}
	public GroupDTO getGroupg() {
		return groupg;
	}
	public void setGroupg(GroupDTO groupg) {
		this.groupg = groupg;
	}
	public Long getGroupId() {
		return groupId;
	}
	public void setGroupId(Long groupId) {
		this.groupId = groupId;
	}
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	public AddressDTO getAddress1() {
		return address1;
	}
	public void setAddress1(AddressDTO address1) {
		this.address1 = address1;
	}
	public AddressDTO getAddress2() {
		return address2;
	}
	public void setAddress2(AddressDTO address2) {
		this.address2 = address2;
	}
	public Long getRetirementAge() {
		return retirementAge;
	}
	public void setRetirementAge(Long retirementAge) {
		this.retirementAge = retirementAge;
	}
	public String getPanNo() {
		return panNo;
	}
	public void setPanNo(String panNo) {
		this.panNo = panNo;
	}
	public String getRegistrationNo() {
		return registrationNo;
	}
	public void setRegistrationNo(String registrationNo) {
		this.registrationNo = registrationNo;
	}
	public String getEpfNo() {
		return epfNo;
	}
	public String getActiveStatus() {
		return activeStatus;
	}
	public void setActiveStatus(String activeStatus) {
		this.activeStatus = activeStatus;
	}
	public String getAllowModi() {
		return allowModi;
	}
	public void setAllowModi(String allowModi) {
		this.allowModi = allowModi;
	}
	public String getAuthorizedPerson() {
		return authorizedPerson;
	}
	public void setAuthorizedPerson(String authorizedPerson) {
		this.authorizedPerson = authorizedPerson;
	}
	public String getCompanyAbbreviation() {
		return companyAbbreviation;
	}
	public void setCompanyAbbreviation(String companyAbbreviation) {
		this.companyAbbreviation = companyAbbreviation;
	}
	public Date getDateUpdate() {
		return dateUpdate;
	}
	public void setDateUpdate(Date dateUpdate) {
		this.dateUpdate = dateUpdate;
	}
	public Date getEffectiveEndDate() {
		return effectiveEndDate;
	}
	public void setEffectiveEndDate(Date effectiveEndDate) {
		this.effectiveEndDate = effectiveEndDate;
	}
	public Date getEffectiveStartDate() {
		return effectiveStartDate;
	}
	public void setEffectiveStartDate(Date effectiveStartDate) {
		this.effectiveStartDate = effectiveStartDate;
	}
	public String getEmailId() {
		return emailId;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	public String getGumastaNo() {
		return gumastaNo;
	}
	public void setGumastaNo(String gumastaNo) {
		this.gumastaNo = gumastaNo;
	}
	public String getImportExportCode() {
		return importExportCode;
	}
	public void setImportExportCode(String importExportCode) {
		this.importExportCode = importExportCode;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getNagarnigamNo() {
		return nagarnigamNo;
	}
	public void setNagarnigamNo(String nagarnigamNo) {
		this.nagarnigamNo = nagarnigamNo;
	}
	public String getTanNo() {
		return tanNo;
	}
	public void setTanNo(String tanNo) {
		this.tanNo = tanNo;
	}
	public String getTypeOfIndustry() {
		return typeOfIndustry;
	}
	public void setTypeOfIndustry(String typeOfIndustry) {
		this.typeOfIndustry = typeOfIndustry;
	}
	public String getWebsite() {
		return website;
	}
	public void setWebsite(String website) {
		this.website = website;
	}
	public void setEpfNo(String epfNo) {
		this.epfNo = epfNo;
	}
	public String getEsicNo() {
		return esicNo;
	}
	public void setEsicNo(String esicNo) {
		this.esicNo = esicNo;
	}
	public String getGstNo() {
		return gstNo;
	}
	public void setGstNo(String gstNo) {
		this.gstNo = gstNo;
	}
	/*public String getTypeOfIndustry() {
		return typeOfIndustry;
	}
	public void setTypeOfIndustry(String typeOfIndustry) {
		this.typeOfIndustry = typeOfIndustry;
	}*/
	public Date getDateOfBirth() {
		return dateOfBirth;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public Date getDateCreated() {
		return dateCreated;
	}
	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}
	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
	public String getCompanyLogoPath() {
		return companyLogoPath;
	}
	public void setCompanyLogoPath(String companyLogoPath) {
		this.companyLogoPath = companyLogoPath;
	}
	/*public byte[] getCompanyLogo() {
		return companyLogo;
	}
	public void setCompanyLogo(byte[] companyLogo) {
		this.companyLogo = companyLogo;
	}*/
	public Long getUserIdUpdate() {
		return userIdUpdate;
	}
	public void setUserIdUpdate(Long userIdUpdate) {
		this.userIdUpdate = userIdUpdate;
	}
	public String getDomainName() {
		return domainName;
	}
	public void setDomainName(String domainName) {
		this.domainName = domainName;
	}
	
}
