package com.csipl.hrms.model.employee;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;

/**
 * The persistent class for the EmployeeLettersTransaction database table.
 * 
 */
@Entity
@NamedQuery(name = "EmployeeLettersTransaction.findAll", query = "SELECT e FROM EmployeeLettersTransaction e")
public class EmployeeLettersTransaction implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long employeeLetterTransactionId;

	private Long companyId;

	@Temporal(TemporalType.DATE)
	private Date dateCreated;

	@Temporal(TemporalType.DATE)
	private Date dateUpdate;

	private Long designationId;

	private String levels;

	private String status;

	private Long userId;

	private Long userIdUpdate;
	
	private Long approvalId;

	private String approvalRemarks;
	@Transient
	private String approvalLevel;
	@Transient
	private String designationName;
	// bi-directional many-to-one association to EmployeeLetter
	@ManyToOne
	@JoinColumn(name = "employeeLetterId")
	private EmployeeLetter employeeLetter;

	public String getDesignationName() {
		return designationName;
	}

	public void setDesignationName(String designationName) {
		this.designationName = designationName;
	}

	public String getApprovalLevel() {
		return approvalLevel;
	}

	public void setApprovalLevel(String approvalLevel) {
		this.approvalLevel = approvalLevel;
	}

	public EmployeeLettersTransaction() {
	}

	public Long getEmployeeLetterTransactionId() {
		return this.employeeLetterTransactionId;
	}

	
	
	public Long getApprovalId() {
		return approvalId;
	}

	public void setApprovalId(Long approvalId) {
		this.approvalId = approvalId;
	}

	public String getApprovalRemarks() {
		return approvalRemarks;
	}

	public void setApprovalRemarks(String approvalRemarks) {
		this.approvalRemarks = approvalRemarks;
	}

	public void setEmployeeLetterTransactionId(Long employeeLetterTransactionId) {
		this.employeeLetterTransactionId = employeeLetterTransactionId;
	}

	public Long getCompanyId() {
		return this.companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
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

	public Long getDesignationId() {
		return this.designationId;
	}

	public void setDesignationId(Long designationId) {
		this.designationId = designationId;
	}

	public String getLevels() {
		return this.levels;
	}

	public void setLevels(String levels) {
		this.levels = levels;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
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

	public EmployeeLetter getEmployeeLetter() {
		return this.employeeLetter;
	}

	public void setEmployeeLetter(EmployeeLetter employeeLetter) {
		this.employeeLetter = employeeLetter;
	}

}