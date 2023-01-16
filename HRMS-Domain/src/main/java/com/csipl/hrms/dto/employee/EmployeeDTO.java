package com.csipl.hrms.dto.employee;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.csipl.hrms.dto.organisation.AddressDTO;
import com.csipl.hrms.model.common.User;

public class EmployeeDTO implements Comparable<EmployeeDTO> {
	private List<EmployeeDTO> employessList;
	private boolean newSkillValues;
	private Integer index;
	private List<Long> employeeSkills = new ArrayList<Long>();
	private List<EmployeeSkillDTO> employeeSkillArray;
	private String gradeName;
	private Long cityId;
	private String cityName;
	private Long stateId;
	private String stateName;
	private String employeeCode;
	private Long employeeId;
	private String firstName;
	private String middleName;
	private String lastName;
	private Date dateOfBirth;
	private String gender;
	private String maritalStatus;
	private AddressDTO address1;
	private AddressDTO address2;
	private Date anniversaryDate;
	private String bloodGroup;
	private Long probationDays;
	private String empType;
	private Long departmentId;
	private String departmentName;
	private Long designationId;
	private String designationName;
	private Long branchId;
	private String branchName;
	private Long projectId;
	private String projectName;
	private Long clientId;
	private String clientName;
	private Long reportingToEmployee;
	private Date contractOverDate;
	private String referenceName;
	private String referenceMobile;
	private AddressDTO address3;
	private Date dateOfJoining;
	private String activeStatus;
	private String employeeLogoPath;
	private Long noticePeriodDays;
	private Long userId;
	private Long payStructureHdId;
	private String voluntaryPfContribution;
	private String accountNumber;
	private String bankId;
	private String aadharNumber;
	private String uanNumber;
	private Long companyId;
	private Long groupId;
	private List<PayStructureHdDTO> payStructureHds;
	private List<BankDetailsDTO> employeeBanks;
	private List<EmployeeIdProofDTO> employeeIdProofs;
	private List<EmployeeStatuaryDTO> employeeStatuaries;
	private String totalExperience;
	private String noticeDate;
	private Date dateCreated;
	private Date contractStartDate;
	private String pan;
	private String empPFNumber;
	private String empESINumber;
	private String accountType;
	private String ifscCode;
	private int workingMonths;
	private String basicSalary;
	private String dearnessAllowance;
	private String houseRentAllowance;
	private String conveyanceAllowance;
	private String specialAllowance;
	private String medicalAllowance;
	private String advanceBonus;
	private String performanceLinkedIncome;
	private String companyBenefits;
	private String leaveTravelAllowance;
	private String uniformAllowance;
	private Date endDate;
	private Date exitDate;
	private Long userIdUpdate;
	private String reportingEmployeeFirstName;
	private String reportingEmployeeLastName;
	private String charFirstName;
	private String charLastName;
	private String fullNameCodeVaues;
	private BigDecimal gratuatyAmount;
	private String noticeReason;
	private Long gradesId;
	private Date resignationDate;
	private Long shortFallDays;
	private String employmentWithUs;

	private String employeeEmail;
	private Long shiftId;
	private Long weekOfPattern;
	private Long systemRole;
	private String timeContract;// full time or part time
	private String alternateNo;
	private String refContNo;
	private String refEmailId;
	private String contactNo;
	private User user;
	private String dropdownId;// for bulk upload

	private String biometricId;
	private int year;

	private String tdsLockUnlockStatus;
	private String tdsStatus;

	private String adharNumber;
	private String jobLocation;
	private String shiftName;
	private String weeklyOffPattern;
	private String gradesName;

	private String alternateNumber;
	private Long referenceEmpAddrsStateId;
	private Long referenceEmpAddrsCityId;
	private String bankBranch;

	private String emailId;
	private String officialEmailId;
	private String referenceEmailId;

	private BigDecimal totalIncome;
	private BigDecimal totalRebate;
	private BigDecimal taxableIncome;
	private String processMonth;
	private Date dateUpdated;

	private String leaveSchemeName;
	private String attendanceSchemeName;
	private Date probationDate;
	private String probationDateNew;
	private String dateOfJoiningNew;

	private String duration;
	private String policyNo;
	private String roleDescription;
	private String tdsPlanType;
	public String getRoleDescription() {
		return roleDescription;
	}

	public void setRoleDescription(String roleDescription) {
		this.roleDescription = roleDescription;
	}

	public BigDecimal getTotalIncome() {
		return totalIncome;
	}

	public void setTotalIncome(BigDecimal totalIncome) {
		this.totalIncome = totalIncome;
	}

	public BigDecimal getTotalRebate() {
		return totalRebate;
	}

	public void setTotalRebate(BigDecimal totalRebate) {
		this.totalRebate = totalRebate;
	}

	public BigDecimal getTaxableIncome() {
		return taxableIncome;
	}

	public void setTaxableIncome(BigDecimal taxableIncome) {
		this.taxableIncome = taxableIncome;
	}

	public String getEmploymentWithUs() {
		return employmentWithUs;
	}

	public void setEmploymentWithUs(String employmentWithUs) {
		this.employmentWithUs = employmentWithUs;
	}

	public String getNoticeReason() {
		return noticeReason;
	}

	public void setNoticeReason(String noticeReason) {
		this.noticeReason = noticeReason;
	}

	public BigDecimal getGratuatyAmount() {
		return gratuatyAmount;
	}

	public void setGratuatyAmount(BigDecimal gratuatyAmount) {
		this.gratuatyAmount = gratuatyAmount;
	}

	public int getWorkingMonths() {
		return workingMonths;
	}

	public void setWorkingMonths(int workingMonths) {
		this.workingMonths = workingMonths;
	}

	public String getCharFirstName() {
		return charFirstName;
	}

	public void setCharFirstName(String charFirstName) {
		this.charFirstName = charFirstName;
	}

	public Date getContractStartDate() {
		return contractStartDate;
	}

	public void setContractStartDate(Date contractStartDate) {
		this.contractStartDate = contractStartDate;
	}

	public String getNoticeDate() {
		return noticeDate;
	}

	public void setNoticeDate(String noticeDate) {
		this.noticeDate = noticeDate;
	}

	public Long getStateId() {
		return stateId;
	}

	public void setStateId(Long stateId) {
		this.stateId = stateId;
	}

	public String getStateName() {
		return stateName;
	}

	public void setStateName(String stateName) {
		this.stateName = stateName;
	}

	public String getEmployeeCode() {

		return employeeCode;
	}

	public Long getEmployeeId() {
		return employeeId;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getMiddleName() {
		return middleName;
	}

	public String getLastName() {
		return lastName;
	}

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public String getGender() {
		return gender;
	}

	public String getMaritalStatus() {
		return maritalStatus;
	}

	public String getBloodGroup() {
		return bloodGroup;
	}

	public String getEmpType() {
		return empType;
	}

	public Long getDepartmentId() {
		return departmentId;
	}

	public String getDepartmentName() {
		return departmentName;
	}

	public Long getDesignationId() {
		return designationId;
	}

	public String getDesignationName() {
		return designationName;
	}

	public Long getProjectId() {
		return projectId;
	}

	public String getProjectName() {
		return projectName;
	}

	public Long getClientId() {
		return clientId;
	}

	public String getClientName() {
		return clientName;
	}

	public Long getReportingToEmployee() {
		return reportingToEmployee;
	}

	public Date getContractOverDate() {
		return contractOverDate;
	}

	public String getReferenceName() {
		return referenceName;
	}

	public Date getDateOfJoining() {
		return dateOfJoining;
	}

	public String getActiveStatus() {
		return activeStatus;
	}

	public String getPolicyNo() {
		return policyNo;
	}

	public void setPolicyNo(String policyNo) {
		this.policyNo = policyNo;
	}

	public String getEmployeeLogoPath() {
		return employeeLogoPath;
	}

	public void setEmployeeLogoPath(String employeeLogoPath) {
		this.employeeLogoPath = employeeLogoPath;
	}

	public void setEmployeeCode(String employeeCode) {
		this.employeeCode = employeeCode;
	}

	public void setEmployeeId(Long employeeId) {
		this.employeeId = employeeId;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public void setMaritalStatus(String maritalStatus) {
		this.maritalStatus = maritalStatus;
	}

	public void setBloodGroup(String bloodGroup) {
		this.bloodGroup = bloodGroup;
	}

	public void setEmpType(String empType) {
		this.empType = empType;
	}

	public void setDepartmentId(Long departmentId) {
		this.departmentId = departmentId;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	public void setDesignationId(Long designationId) {
		this.designationId = designationId;
	}

	public void setDesignationName(String designationName) {
		this.designationName = designationName;
	}

	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public void setClientId(Long clientId) {
		this.clientId = clientId;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	public void setReportingToEmployee(Long reportingToEmployee) {
		this.reportingToEmployee = reportingToEmployee;
	}

	public void setContractOverDate(Date contractOverDate) {
		this.contractOverDate = contractOverDate;
	}

	public void setReferenceName(String referenceName) {
		this.referenceName = referenceName;
	}

	public void setDateOfJoining(Date dateOfJoining) {
		this.dateOfJoining = dateOfJoining;
	}

	public void setActiveStatus(String activeStatus) {
		this.activeStatus = activeStatus;
	}

	public Date getAnniversaryDate() {
		return anniversaryDate;
	}

	public Long getProbationDays() {
		return probationDays;
	}

	public void setAnniversaryDate(Date anniversaryDate) {
		this.anniversaryDate = anniversaryDate;
	}

	public void setProbationDays(Long probationDays) {
		this.probationDays = probationDays;
	}

	public AddressDTO getAddress1() {
		return address1;
	}

	public AddressDTO getAddress2() {
		return address2;
	}

	public AddressDTO getAddress3() {
		return address3;
	}

	public void setAddress1(AddressDTO address1) {
		this.address1 = address1;
	}

	public void setAddress2(AddressDTO address2) {
		this.address2 = address2;
	}

	public void setAddress3(AddressDTO address3) {
		this.address3 = address3;
	}

	public Long getCityId() {
		return cityId;
	}

	public void setCityId(Long cityId) {
		this.cityId = cityId;
	}

	public String getCityName() {
		return cityName;
	}

	public Long getNoticePeriodDays() {
		return noticePeriodDays;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public void setNoticePeriodDays(Long noticePeriodDays) {
		this.noticePeriodDays = noticePeriodDays;
	}

	public List<EmployeeSkillDTO> getEmployeeSkillArray() {
		return employeeSkillArray;
	}

	public void setEmployeeSkillArray(List<EmployeeSkillDTO> employeeSkillArray) {
		this.employeeSkillArray = employeeSkillArray;
	}

	public boolean isNewSkillValues() {
		return newSkillValues;
	}

	public void setNewSkillValues(boolean newSkillValues) {
		this.newSkillValues = newSkillValues;
	}

	public List<Long> getEmployeeSkills() {
		return employeeSkills;
	}

	public void setEmployeeSkills(List<Long> employeeSkills) {
		this.employeeSkills = employeeSkills;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getVoluntaryPfContribution() {
		return voluntaryPfContribution;
	}

	public void setVoluntaryPfContribution(String voluntaryPfContribution) {
		this.voluntaryPfContribution = voluntaryPfContribution;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public Long getPayStructureHdId() {
		return payStructureHdId;
	}

	public void setPayStructureHdId(Long payStructureHdId) {
		this.payStructureHdId = payStructureHdId;
	}

	public String getBankId() {
		return bankId;
	}

	public void setBankId(String bankId) {
		this.bankId = bankId;
	}

	public String getGradeName() {
		return gradeName;
	}

	public void setGradeName(String gradeName) {
		this.gradeName = gradeName;
	}

	public String getUanNumber() {
		return uanNumber;
	}

	public void setUanNumber(String uanNumber) {
		this.uanNumber = uanNumber;
	}

	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	public Long getGroupId() {
		return groupId;
	}

	public void setGroupId(Long groupId) {
		this.groupId = groupId;
	}

	public List<PayStructureHdDTO> getPayStructureHds() {
		return payStructureHds;
	}

	public void setPayStructureHds(List<PayStructureHdDTO> payStructureHds) {
		this.payStructureHds = payStructureHds;
	}

	public List<BankDetailsDTO> getEmployeeBanks() {
		return employeeBanks;
	}

	public void setEmployeeBanks(List<BankDetailsDTO> employeeBanks) {
		this.employeeBanks = employeeBanks;
	}

	public List<EmployeeIdProofDTO> getEmployeeIdProofs() {
		return employeeIdProofs;
	}

	public void setEmployeeIdProofs(List<EmployeeIdProofDTO> employeeIdProofs) {
		this.employeeIdProofs = employeeIdProofs;
	}

	public List<EmployeeStatuaryDTO> getEmployeeStatuaries() {
		return employeeStatuaries;
	}

	public void setEmployeeStatuaries(List<EmployeeStatuaryDTO> employeeStatuaries) {
		this.employeeStatuaries = employeeStatuaries;
	}

	public String getTotalExperience() {
		return totalExperience;
	}

	public void setTotalExperience(String totalExperience) {
		this.totalExperience = totalExperience;
	}

	public String getAadharNumber() {
		return aadharNumber;
	}

	public void setAadharNumber(String aadharNumber) {
		this.aadharNumber = aadharNumber;
	}

	public Date getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	public String getPan() {
		return pan;
	}

	public void setPan(String pan) {
		this.pan = pan;
	}

	public String getEmpPFNumber() {
		return empPFNumber;
	}

	public String getEmpESINumber() {
		return empESINumber;
	}

	public void setEmpPFNumber(String empPFNumber) {
		this.empPFNumber = empPFNumber;
	}

	public void setEmpESINumber(String empESINumber) {
		this.empESINumber = empESINumber;
	}

	public String getAccountType() {
		return accountType;
	}

	public String getIfscCode() {
		return ifscCode;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

	public void setIfscCode(String ifscCode) {
		this.ifscCode = ifscCode;
	}

	public String getBasicSalary() {
		return basicSalary;
	}

	public String getDearnessAllowance() {
		return dearnessAllowance;
	}

	public String getHouseRentAllowance() {
		return houseRentAllowance;
	}

	public String getConveyanceAllowance() {
		return conveyanceAllowance;
	}

	public String getSpecialAllowance() {
		return specialAllowance;
	}

	public String getMedicalAllowance() {
		return medicalAllowance;
	}

	public String getAdvanceBonus() {
		return advanceBonus;
	}

	public String getPerformanceLinkedIncome() {
		return performanceLinkedIncome;
	}

	public String getCompanyBenefits() {
		return companyBenefits;
	}

	public String getLeaveTravelAllowance() {
		return leaveTravelAllowance;
	}

	public String getUniformAllowance() {
		return uniformAllowance;
	}

	public void setBasicSalary(String basicSalary) {
		this.basicSalary = basicSalary;
	}

	public void setDearnessAllowance(String dearnessAllowance) {
		this.dearnessAllowance = dearnessAllowance;
	}

	public void setHouseRentAllowance(String houseRentAllowance) {
		this.houseRentAllowance = houseRentAllowance;
	}

	public void setConveyanceAllowance(String conveyanceAllowance) {
		this.conveyanceAllowance = conveyanceAllowance;
	}

	public void setSpecialAllowance(String specialAllowance) {
		this.specialAllowance = specialAllowance;
	}

	public void setMedicalAllowance(String medicalAllowance) {
		this.medicalAllowance = medicalAllowance;
	}

	public void setAdvanceBonus(String advanceBonus) {
		this.advanceBonus = advanceBonus;
	}

	public void setPerformanceLinkedIncome(String performanceLinkedIncome) {
		this.performanceLinkedIncome = performanceLinkedIncome;
	}

	public void setCompanyBenefits(String companyBenefits) {
		this.companyBenefits = companyBenefits;
	}

	public void setLeaveTravelAllowance(String leaveTravelAllowance) {
		this.leaveTravelAllowance = leaveTravelAllowance;
	}

	public void setUniformAllowance(String uniformAllowance) {
		this.uniformAllowance = uniformAllowance;
	}

	public Integer getIndex() {
		return index;
	}

	public void setIndex(Integer index) {
		this.index = index;
	}

	@Override
	public int compareTo(EmployeeDTO dto) {
		if (dto.getIndex() < this.getIndex()) {
			return 1;
		} else {
			return -1;
		}
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Long getUserIdUpdate() {
		return userIdUpdate;
	}

	public void setUserIdUpdate(Long userIdUpdate) {
		this.userIdUpdate = userIdUpdate;
	}

	@Override
	public String toString() {
		return "EmployeeDTO [newSkillValues=" + newSkillValues + ", index=" + index + ", gradeName=" + gradeName
				+ ", cityId=" + cityId + ", cityName=" + cityName + ", stateId=" + stateId + ", stateName=" + stateName
				+ ", employeeCode=" + employeeCode + ", employeeId=" + employeeId + ", firstName=" + firstName
				+ ", middleName=" + middleName + ", lastName=" + lastName + ", dateOfBirth=" + dateOfBirth + ", gender="
				+ gender + ", maritalStatus=" + maritalStatus + ", anniversaryDate=" + anniversaryDate + ", bloodGroup="
				+ bloodGroup + ", probationDays=" + probationDays + ", empType=" + empType + ", departmentId="
				+ departmentId + ", departmentName=" + departmentName + ", designationId=" + designationId
				+ ", designationName=" + designationName + ", projectId=" + projectId + ", projectName=" + projectName
				+ ", clientId=" + clientId + ", clientName=" + clientName + ", reportingToEmployee="
				+ reportingToEmployee + ", contractOverDate=" + contractOverDate + ", referenceName=" + referenceName
				+ ", dateOfJoining=" + dateOfJoining + ", activeStatus=" + activeStatus + ", employeeLogoPath="
				+ employeeLogoPath + ", noticePeriodDays=" + noticePeriodDays + ", userId=" + userId
				+ ", payStructureHdId=" + payStructureHdId + ", voluntaryPfContribution=" + voluntaryPfContribution
				+ ", accountNumber=" + accountNumber + ", bankId=" + bankId + ", aadharNumber=" + aadharNumber
				+ ", uanNumber=" + uanNumber + ", companyId=" + companyId + ", groupId=" + groupId + ", noticeDate="
				+ noticeDate + ", dateCreated=" + dateCreated + ", contractStartDate=" + contractStartDate + ", pan="
				+ pan + ", empPFNumber=" + empPFNumber + ", empESINumber=" + empESINumber + ", accountType="
				+ accountType + ", ifscCode=" + ifscCode + ", basicSalary=" + basicSalary + ", dearnessAllowance="
				+ dearnessAllowance + ", houseRentAllowance=" + houseRentAllowance + ", conveyanceAllowance="
				+ conveyanceAllowance + ", specialAllowance=" + specialAllowance + ", medicalAllowance="
				+ medicalAllowance + ", advanceBonus=" + advanceBonus + ", performanceLinkedIncome="
				+ performanceLinkedIncome + ", companyBenefits=" + companyBenefits + ", leaveTravelAllowance="
				+ leaveTravelAllowance + ", uniformAllowance=" + uniformAllowance + ", endDate=" + endDate
				+ ", userIdUpdate=" + userIdUpdate + "]";
	}

	public List<EmployeeDTO> getEmployessList() {
		return employessList;
	}

	public void setEmployessList(List<EmployeeDTO> employessList) {
		this.employessList = employessList;
	}

	public String getReportingEmployeeFirstName() {
		return reportingEmployeeFirstName;
	}

	public void setReportingEmployeeFirstName(String reportingEmployeeFirstName) {
		this.reportingEmployeeFirstName = reportingEmployeeFirstName;
	}

	public String getReportingEmployeeLastName() {
		return reportingEmployeeLastName;
	}

	public void setReportingEmployeeLastName(String reportingEmployeeLastName) {
		this.reportingEmployeeLastName = reportingEmployeeLastName;
	}

	public String getCharLastName() {
		return charLastName;
	}

	public void setCharLastName(String charLastName) {
		this.charLastName = charLastName;
	}

	public String getFullNameCodeVaues() {
		return fullNameCodeVaues;
	}

	public void setFullNameCodeVaues(String fullNameCodeVaues) {
		this.fullNameCodeVaues = fullNameCodeVaues;
	}

	public Long getGradesId() {
		return gradesId;
	}

	public void setGradesId(Long gradesId) {
		this.gradesId = gradesId;
	}

	public Long getShortFallDays() {
		return shortFallDays;
	}

	public void setShortFallDays(Long shortFallDays) {
		this.shortFallDays = shortFallDays;
	}

	public Date getResignationDate() {
		return resignationDate;
	}

	public void setResignationDate(Date resignationDate) {
		this.resignationDate = resignationDate;
	}

	public String getEmployeeEmail() {
		return employeeEmail;
	}

	public void setEmployeeEmail(String employeeEmail) {
		this.employeeEmail = employeeEmail;
	}

	public Long getShiftId() {
		return shiftId;
	}

	public void setShiftId(Long shiftId) {
		this.shiftId = shiftId;
	}

	public Long getWeekOfPattern() {
		return weekOfPattern;
	}

	public void setWeekOfPattern(Long weekOfPattern) {
		this.weekOfPattern = weekOfPattern;
	}

	public Long getSystemRole() {
		return systemRole;
	}

	public void setSystemRole(Long systemRole) {
		this.systemRole = systemRole;
	}

	public String getTimeContract() {
		return timeContract;
	}

	public void setTimeContract(String timeContract) {
		this.timeContract = timeContract;
	}

	public String getAlternateNo() {
		return alternateNo;
	}

	public void setAlternateNo(String alternateNo) {
		this.alternateNo = alternateNo;
	}

	public String getRefContNo() {
		return refContNo;
	}

	public void setRefContNo(String refContNo) {
		this.refContNo = refContNo;
	}

	public String getRefEmailId() {
		return refEmailId;
	}

	public void setRefEmailId(String refEmailId) {
		this.refEmailId = refEmailId;
	}

	public String getContactNo() {
		return contactNo;
	}

	public void setContactNo(String contactNo) {
		this.contactNo = contactNo;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getDropdownId() {
		return dropdownId;
	}

	public void setDropdownId(String dropdownId) {
		this.dropdownId = dropdownId;
	}

	public String getTdsLockUnlockStatus() {
		return tdsLockUnlockStatus;
	}

	public void setTdsLockUnlockStatus(String tdsLockUnlockStatus) {
		this.tdsLockUnlockStatus = tdsLockUnlockStatus;
	}

	public String getTdsStatus() {
		return tdsStatus;
	}

	public void setTdsStatus(String tdsStatus) {
		this.tdsStatus = tdsStatus;
	}

	public String getReferenceMobile() {
		return referenceMobile;
	}

	public void setReferenceMobile(String referenceMobile) {
		this.referenceMobile = referenceMobile;
	}

	public String getAdharNumber() {
		return adharNumber;
	}

	public void setAdharNumber(String adharNumber) {
		this.adharNumber = adharNumber;
	}

	public String getJobLocation() {
		return jobLocation;
	}

	public void setJobLocation(String jobLocation) {
		this.jobLocation = jobLocation;
	}

	public String getShiftName() {
		return shiftName;
	}

	public void setShiftName(String shiftName) {
		this.shiftName = shiftName;
	}

	public String getWeeklyOffPattern() {
		return weeklyOffPattern;
	}

	public void setWeeklyOffPattern(String weeklyOffPattern) {
		this.weeklyOffPattern = weeklyOffPattern;
	}

	public String getGradesName() {
		return gradesName;
	}

	public void setGradesName(String gradesName) {
		this.gradesName = gradesName;
	}

	public String getAlternateNumber() {
		return alternateNumber;
	}

	public void setAlternateNumber(String alternateNumber) {
		this.alternateNumber = alternateNumber;
	}

	public Long getReferenceEmpAddrsStateId() {
		return referenceEmpAddrsStateId;
	}

	public void setReferenceEmpAddrsStateId(Long referenceEmpAddrsStateId) {
		this.referenceEmpAddrsStateId = referenceEmpAddrsStateId;
	}

	public Long getReferenceEmpAddrsCityId() {
		return referenceEmpAddrsCityId;
	}

	public void setReferenceEmpAddrsCityId(Long referenceEmpAddrsCityId) {
		this.referenceEmpAddrsCityId = referenceEmpAddrsCityId;
	}

	public String getBankBranch() {
		return bankBranch;
	}

	public void setBankBranch(String bankBranch) {
		this.bankBranch = bankBranch;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getReferenceEmailId() {
		return referenceEmailId;
	}

	public void setReferenceEmailId(String referenceEmailId) {
		this.referenceEmailId = referenceEmailId;
	}

	public Date getExitDate() {
		return exitDate;
	}

	public void setExitDate(Date exitDate) {
		this.exitDate = exitDate;
	}

	public Date getDateUpdated() {
		return dateUpdated;
	}

	public void setDateUpdated(Date dateUpdated) {
		this.dateUpdated = dateUpdated;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public String getBiometricId() {
		return biometricId;
	}

	public void setBiometricId(String biometricId) {
		this.biometricId = biometricId;
	}

	public String getProcessMonth() {
		return processMonth;
	}

	public void setProcessMonth(String processMonth) {
		this.processMonth = processMonth;
	}

	public String getLeaveSchemeName() {
		return leaveSchemeName;
	}

	public void setLeaveSchemeName(String leaveSchemeName) {
		this.leaveSchemeName = leaveSchemeName;
	}

	public String getAttendanceSchemeName() {
		return attendanceSchemeName;
	}

	public void setAttendanceSchemeName(String attendanceSchemeName) {
		this.attendanceSchemeName = attendanceSchemeName;
	}

	public Date getProbationDate() {
		return probationDate;
	}

	public void setProbationDate(Date probationDate) {
		this.probationDate = probationDate;
	}

	public String getProbationDateNew() {
		return probationDateNew;
	}

	public void setProbationDateNew(String probationDateNew) {
		this.probationDateNew = probationDateNew;
	}

	public String getDateOfJoiningNew() {
		return dateOfJoiningNew;
	}

	public void setDateOfJoiningNew(String dateOfJoiningNew) {
		this.dateOfJoiningNew = dateOfJoiningNew;
	}

	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

	public Long getBranchId() {
		return branchId;
	}

	public void setBranchId(Long branchId) {
		this.branchId = branchId;
	}

	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	public String getOfficialEmailId() {
		return officialEmailId;
	}

	public void setOfficialEmailId(String officialEmailId) {
		this.officialEmailId = officialEmailId;
	}

	public String getTdsPlanType() {
		return tdsPlanType;
	}

	public void setTdsPlanType(String tdsPlanType) {
		this.tdsPlanType = tdsPlanType;
	}

}
