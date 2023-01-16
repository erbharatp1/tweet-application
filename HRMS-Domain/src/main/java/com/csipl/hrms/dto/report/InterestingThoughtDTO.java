package com.csipl.hrms.dto.report;

import java.util.Date;



public class InterestingThoughtDTO{

	private Long interestingThoughtsId;
	private Date dateCreated;
	private Date dateUpdate;
	private String thoughts; 
	private Long userId;
	private Long userIdUpdate;
	private Long companyId;
	private Long employeeId;

	private String empName;
	 private String firstLetter;
	 private String lastLetter;
	private String employeeLogoPath;

	
	

	
	public String getFirstLetter() {
		return firstLetter;
	}

	public void setFirstLetter(String firstLetter) {
		this.firstLetter = firstLetter;
	}

	public String getEmployeeLogoPath() {
		return employeeLogoPath;
	}

	public void setEmployeeLogoPath(String employeeLogoPath) {
		this.employeeLogoPath = employeeLogoPath;
	}



	public Long getInterestingThoughtsId() {
		return interestingThoughtsId;
	}

	public void setInterestingThoughtsId(Long interestingThoughtsId) {
		this.interestingThoughtsId = interestingThoughtsId;
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

	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	public void setEmployeeId(Long employeeId) {
		this.employeeId = employeeId;
	}

	public Date getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	public Date getDateUpdate() {
		return dateUpdate;
	}

	public void setDateUpdate(Date dateUpdate) {
		this.dateUpdate = dateUpdate;
	}

	public String getThoughts() {
		return this.thoughts;
	}

	public void setThoughts(String thoughts) {
		this.thoughts = thoughts;
	}

	public Long getEmployeeId() {
		return employeeId;
	}



	public String getEmpName() {
		return empName;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
	}

	/**
	 * @return the lastLetter
	 */
	public String getLastLetter() {
		return lastLetter;
	}

	/**
	 * @param lastLetter the lastLetter to set
	 */
	public void setLastLetter(String lastLetter) {
		this.lastLetter = lastLetter;
	}



 

}