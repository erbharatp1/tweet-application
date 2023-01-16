package com.csipl.hrms.dto.employee;

 
import java.util.Date;
  public class EmployeeFamilyDTO {
 	private String relation;
 	private Long familyId;
	private String captions;
 	private String name;
	private String qualificationId;
 	private String occupations;
 	private String dateOfBirth;
  	private String contactPhone;
 	private String contactMobile;
 	private Long userId;
	private Date dateCreated;
	private Long userIdUpdate;
	
	/**
	 * @author ${Nisha Parveen}
	 *
	 */
	private String empCode;
	private String empName;
	private String deptName;
	private Date dob;
	
 	public Long getUserIdUpdate() {
		return userIdUpdate;
	}
	public void setUserIdUpdate(Long userIdUpdate) {
		this.userIdUpdate = userIdUpdate;
	}
	public String getRelation() {
		return relation;
	}
	public Long getFamilyId() {
		return familyId;
	}
	public String getCaptions() {
		return captions;
	}
	public String getName() {
		return name;
	}
	public String getQualificationId() {
		return qualificationId;
	}
	public String getOccupations() {
		return occupations;
	}
	
	public String getDateOfBirth() {
		return dateOfBirth;
	}
	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
	public String getContactPhone() {
		return contactPhone;
	}
	public String getContactMobile() {
		return contactMobile;
	}
	public void setRelation(String relation) {
		this.relation = relation;
	}
	public void setFamilyId(Long familyId) {
		this.familyId = familyId;
	}
	public void setCaptions(String captions) {
		this.captions = captions;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setQualificationId(String qualificationId) {
		this.qualificationId = qualificationId;
	}
	public void setOccupations(String occupations) {
		this.occupations = occupations;
	}
	
	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}
	public void setContactMobile(String contactMobile) {
		this.contactMobile = contactMobile;
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
	public String getEmpCode() {
		return empCode;
	}
	public void setEmpCode(String empCode) {
		this.empCode = empCode;
	}
	public String getEmpName() {
		return empName;
	}
	public void setEmpName(String empName) {
		this.empName = empName;
	}
	public String getDeptName() {
		return deptName;
	}
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	public Date getDob() {
		return dob;
	}
	public void setDob(Date dob) {
		this.dob = dob;
	}
 
	 
 }
