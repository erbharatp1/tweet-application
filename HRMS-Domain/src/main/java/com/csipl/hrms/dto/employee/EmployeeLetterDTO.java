package com.csipl.hrms.dto.employee;

import java.util.Date;
import java.util.List;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;

public class EmployeeLetterDTO {
	private Long empLetterId;
	private Long employeeId;
	private Long approvalId;
	private String  approvalStatus;
	private String approvedBy;

	private String approvalRemarks;
	private String activeStatus;
	private Date dateCreated;
	private Date dateUpdate;
	private Long empId;
	private String empStatus;
	private String HRStatus;
	private String letterDecription;
	private Long userId;
	private Long userIdUpdate;
	private Long letterId;
	private String  status;
	private String levelStatus;
	// private Letter letter;

	private String firstName;
	private String lastName;
	private String charFirstName;
	private String charLastName;
	private String designationName;
	private String employeeCode;
	private String HrCode;
	private String departmentName;
	private Date dateOfBirth;
	private String letterType;
	private String employeeName;
	private String letterName;
	private String HrName;
	private String empEmailId;
	private String officialEmailId;
	private Date dateOfJoining;
	private Long employeeLetterTransactionId;
	private Long designationId;
	private String levels;
	private Long levelDesignationId;
	private String designationNameApprovedBy;
	private String approvalHierarchyStatus;
	private String employeeLogoPath;
	
	private Integer count;
	private Integer cal;
    private String declarationStatus;
	
	private Date declarationDate;
	
	public String getDeclarationStatus() {
		return declarationStatus;
	}

	public void setDeclarationStatus(String declarationStatus) {
		this.declarationStatus = declarationStatus;
	}

	public Date getDeclarationDate() {
		return declarationDate;
	}

	public void setDeclarationDate(Date declarationDate) {
		this.declarationDate = declarationDate;
	}

	public Integer getCal() {
		return cal;
	}

	public void setCal(Integer cal) {
		this.cal = cal;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	private List<EmployeeLettersTransactionDTO> empTransactionList ;

	private String	realeseStatus;
	
	
	
	public String getDesignationNameApprovedBy() {
		return designationNameApprovedBy;
	}

	public void setDesignationNameApprovedBy(String designationNameApprovedBy) {
		this.designationNameApprovedBy = designationNameApprovedBy;
	}

	public String getApprovedBy() {
		return approvedBy;
	}

	public void setApprovedBy(String approvedBy) {
		this.approvedBy = approvedBy;
	}

	public String getApprovalStatus() {
		return approvalStatus;
	}

	public void setApprovalStatus(String approvalStatus) {
		this.approvalStatus = approvalStatus;
	}

	public String getLevelStatus() {
		return levelStatus;
	}

	public void setLevelStatus(String levelStatus) {
		this.levelStatus = levelStatus;
	}

	public Long getLevelDesignationId() {
		return levelDesignationId;
	}

	public void setLevelDesignationId(Long levelDesignationId) {
		this.levelDesignationId = levelDesignationId;
	}

	public Long getDesignationId() {
		return designationId;
	}

	public void setDesignationId(Long designationId) {
		this.designationId = designationId;
	}

	public String getLevels() {
		return levels;
	}

	public void setLevels(String levels) {
		this.levels = levels;
	}

	public Long getApprovalId() {
		return approvalId;
	}

	public void setApprovalId(Long approvalId) {
		this.approvalId = approvalId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Long getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(Long employeeId) {
		this.employeeId = employeeId;
	}

	public String getApprovalRemarks() {
		return approvalRemarks;
	}

	public void setApprovalRemarks(String approvalRemarks) {
		this.approvalRemarks = approvalRemarks;
	}

	public Long getEmployeeLetterTransactionId() {
		return employeeLetterTransactionId;
	}

	public void setEmployeeLetterTransactionId(Long employeeLetterTransactionId) {
		this.employeeLetterTransactionId = employeeLetterTransactionId;
	}

	public Date getDateOfJoining() {
		return dateOfJoining;
	}

	public void setDateOfJoining(Date dateOfJoining) {
		this.dateOfJoining = dateOfJoining;
	}

	public String getHrCode() {
		return HrCode;
	}

	public void setHrCode(String hrCode) {
		HrCode = hrCode;
	}

	public String getHrName() {
		return HrName;
	}

	public void setHrName(String hrName) {
		HrName = hrName;
	}

	public String getRealeseStatus() {
		return realeseStatus;
	}

	public void setRealeseStatus(String realeseStatus) {
		this.realeseStatus = realeseStatus;
	}

	public String getEmployeeName() {
		return employeeName;
	}

	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

	public Long getEmpLetterId() {
		return this.empLetterId;
	}

	public void setEmpLetterId(Long empLetterId) {
		this.empLetterId = empLetterId;
	}

	public String getLetterName() {
		return letterName;
	}

	public void setLetterName(String letterName) {
		this.letterName = letterName;
	}

	public String getActiveStatus() {
		return this.activeStatus;
	}

	public void setActiveStatus(String activeStatus) {
		this.activeStatus = activeStatus;
	}

	public Date getDateCreated() {
		return this.dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	public Date getDateUpdate() {
		return this.dateUpdate;
	}

	public void setDateUpdate(Date dateUpdate) {
		this.dateUpdate = dateUpdate;
	}

	public Long getEmpId() {
		return this.empId;
	}

	public void setEmpId(Long empId) {
		this.empId = empId;
	}

	public String getEmpStatus() {
		return this.empStatus;
	}

	public void setEmpStatus(String empStatus) {
		this.empStatus = empStatus;
	}

	public String getHRStatus() {
		return this.HRStatus;
	}

	public void setHRStatus(String HRStatus) {
		this.HRStatus = HRStatus;
	}

	public String getLetterDecription() {
		return this.letterDecription;
	}

	public void setLetterDecription(String letterDecription) {
		this.letterDecription = letterDecription;
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

	public Long getLetterId() {
		return letterId;
	}

	public void setLetterId(Long letterId) {
		this.letterId = letterId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getCharFirstName() {
		return charFirstName;
	}

	public void setCharFirstName(String charFirstName) {
		this.charFirstName = charFirstName;
	}

	public String getCharLastName() {
		return charLastName;
	}

	public void setCharLastName(String charLastName) {
		this.charLastName = charLastName;
	}

	public String getDesignationName() {
		return designationName;
	}

	public void setDesignationName(String designationName) {
		this.designationName = designationName;
	}

	public String getEmployeeCode() {
		return employeeCode;
	}

	public void setEmployeeCode(String employeeCode) {
		this.employeeCode = employeeCode;
	}

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getLetterType() {
		return letterType;
	}

	public void setLetterType(String letterType) {
		this.letterType = letterType;
	}

	public String getEmpEmailId() {
		return empEmailId;
	}

	public void setEmpEmailId(String empEmailId) {
		this.empEmailId = empEmailId;
	}

	public String getOfficialEmailId() {
		return officialEmailId;
	}

	public void setOfficialEmailId(String officialEmailId) {
		this.officialEmailId = officialEmailId;
	}

	public String getApprovalHierarchyStatus() {
		return approvalHierarchyStatus;
	}

	public void setApprovalHierarchyStatus(String approvalHierarchyStatus) {
		this.approvalHierarchyStatus = approvalHierarchyStatus;
	}

	public List<EmployeeLettersTransactionDTO> getEmpTransactionList() {
		return empTransactionList;
	}

	public void setEmpTransactionList(List<EmployeeLettersTransactionDTO> empTransactionList) {
		this.empTransactionList = empTransactionList;
	}

	public String getEmployeeLogoPath() {
		return employeeLogoPath;
	}

	public void setEmployeeLogoPath(String employeeLogoPath) {
		this.employeeLogoPath = employeeLogoPath;
	}

}