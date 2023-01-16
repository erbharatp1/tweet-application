package com.csipl.hrms.dto.employee;

import java.util.Date;
import java.util.List;

import com.csipl.hrms.model.employee.EmployeeLetter;

public class LetterDTO {

	private Long letterId;
	private String activeStatus;
	private Date dateCreated;
	private Date dateUpdate;
	private String letterDecription;
	private String letterName;
	private String letterType;
	private Long userId;
	private Long userIdUpdate;
	private Long companyId;
	private String fileLocation;
	private Long employeeId;
	private Long gradeId;
	private EmployeeLetter empLetters;
	private String enableGrade;
	public Long getLetterId() {
		return this.letterId;
	}

	public Long getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(Long employeeId) {
		this.employeeId = employeeId;
	}

	public void setLetterId(Long letterId) {
		this.letterId = letterId;
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

	public String getLetterDecription() {
		return this.letterDecription;
	}

	public void setLetterDecription(String letterDecription) {
		this.letterDecription = letterDecription;
	}

	public String getLetterName() {
		return this.letterName;
	}

	public void setLetterName(String letterName) {
		this.letterName = letterName;
	}

	public String getLetterType() {
		return this.letterType;
	}

	public void setLetterType(String letterType) {
		this.letterType = letterType;
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

	public EmployeeLetter getEmpLetters() {
		return empLetters;
	}

	public void setEmpLetters(EmployeeLetter empLetters) {
		this.empLetters = empLetters;
	}

	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	public String getFileLocation() {
		return fileLocation;
	}

	public void setFileLocation(String fileLocation) {
		this.fileLocation = fileLocation;
	}

	public Long getGradeId() {
		return gradeId;
	}

	public void setGradeId(Long gradeId) {
		this.gradeId = gradeId;
	}

	public String getEnableGrade() {
		return enableGrade;
	}

	public void setEnableGrade(String enableGrade) {
		this.enableGrade = enableGrade;
	}
}