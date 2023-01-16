package com.csipl.hrms.model.employee;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the EmpLetter database table.
 * 
 */
@Entity
@NamedQuery(name="EmployeeLetter.findAll", query="SELECT e FROM EmployeeLetter e")
public class EmployeeLetter implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long empLetterId;

	private String activeStatus;

	@Temporal(TemporalType.DATE)
	private Date dateCreated;

	@Temporal(TemporalType.DATE)
	private Date dateUpdate;

	private Long empId;

	private String empStatus;

	private String HRStatus;
	private String	realeseStatus;
	@Lob
	private String letterDecription;

	private Long userId;

	 
	private Long userIdUpdate;
	
	private String declarationStatus;
	
	@Temporal(TemporalType.DATE)
	private Date declarationDate;
	
	private Long letterId;
	public Long getLetterId() {
		return letterId;
	}

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

	public void setLetterId(Long letterId) {
		this.letterId = letterId;
	}

	public EmployeeLetter() {
	}

	public Long getEmpLetterId() {
		return empLetterId;
	}

	public void setEmpLetterId(Long empLetterId) {
		this.empLetterId = empLetterId;
	}

	public String getActiveStatus() {
		return activeStatus;
	}

	public void setActiveStatus(String activeStatus) {
		this.activeStatus = activeStatus;
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

	public Long getEmpId() {
		return empId;
	}

	public void setEmpId(Long empId) {
		this.empId = empId;
	}

	public String getEmpStatus() {
		return empStatus;
	}

	public void setEmpStatus(String empStatus) {
		this.empStatus = empStatus;
	}

	public String getHRStatus() {
		return HRStatus;
	}

	public void setHRStatus(String hRStatus) {
		HRStatus = hRStatus;
	}

	public String getRealeseStatus() {
		return realeseStatus;
	}

	public void setRealeseStatus(String realeseStatus) {
		this.realeseStatus = realeseStatus;
	}

	public String getLetterDecription() {
		return letterDecription;
	}

	public void setLetterDecription(String letterDecription) {
		this.letterDecription = letterDecription;
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

//	public Letter getLetter() {
//		return letter;
//	}
//
//	public void setLetter(Letter letter) {
//		this.letter = letter;
//	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

 

}