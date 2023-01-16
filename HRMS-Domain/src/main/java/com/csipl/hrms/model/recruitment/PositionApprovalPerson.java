package com.csipl.hrms.model.recruitment;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

/**
 * The persistent class for the PositionApprovalPerson database table.
 * 
 */
@Entity
@NamedQuery(name = "PositionApprovalPerson.findAll", query = "SELECT p FROM PositionApprovalPerson p")
public class PositionApprovalPerson implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long positionApprovalPersonId;

	private Long employeeId;

	@Temporal(TemporalType.DATE)
	private Date dateCreated;

	@Temporal(TemporalType.DATE)
	private Date upatedDate;

	@Transient
	private String firstName;

	@Transient
	private String lastName;

	@Transient
	private String employeeCode;

	private String employeeName;

	private Long userId;

	private Long userIdUpdate;

	private String status;

	public Long getPositionApprovalPersonId() {
		return positionApprovalPersonId;
	}

	public void setPositionApprovalPersonId(Long positionApprovalPersonId) {
		this.positionApprovalPersonId = positionApprovalPersonId;
	}

	public Long getEmployeeId() {
		return employeeId;
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

	public Date getUpatedDate() {
		return upatedDate;
	}

	public void setUpatedDate(Date upatedDate) {
		this.upatedDate = upatedDate;
	}

	public String getEmployeeName() {
		return employeeName;
	}

	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
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

	public String getEmployeeCode() {
		return employeeCode;
	}

	public void setEmployeeCode(String employeeCode) {
		this.employeeCode = employeeCode;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}