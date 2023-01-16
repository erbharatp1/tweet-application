package com.csipl.hrms.dto.employee;

import java.util.Date;
import java.util.List;

import com.csipl.hrms.model.employee.Letter;

public class ApprovalHierarchyMasterDTO {

	private Long approvalHierarchyMasterId;
	private String activeStatus;
	private Long companyId;
	private Date dateCreated;
	private Date dateUpdate;
	private Long userId;
	private Long userIdUpdate;
	private Long letterId;
	private Long approvalHierarchyId;
	private Long designationId;
	private String levels;
	private String letterName;

	private List<ApprovalHierarchyDTO> letterApprovalList;

//	//bi-directional many-to-one association to Letter
//	@ManyToOne
//	@JoinColumn(name="letterId")
	private Letter letter;

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

	public List<ApprovalHierarchyDTO> getLetterApprovalList() {
		return letterApprovalList;
	}

	public void setLetterApprovalList(List<ApprovalHierarchyDTO> letterApprovalList) {
		this.letterApprovalList = letterApprovalList;
	}

	public Letter getLetter() {
		return letter;
	}

	public void setLetter(Letter letter) {
		this.letter = letter;
	}

	public Long getLetterId() {
		return letterId;
	}

	public void setLetterId(Long letterId) {
		this.letterId = letterId;
	}

	public Long getApprovalHierarchyMasterId() {
		return approvalHierarchyMasterId;
	}

	public void setApprovalHierarchyMasterId(Long approvalHierarchyMasterId) {
		this.approvalHierarchyMasterId = approvalHierarchyMasterId;
	}

	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
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

	public Long getApprovalHierarchyId() {
		return approvalHierarchyId;
	}

	public void setApprovalHierarchyId(Long approvalHierarchyId) {
		this.approvalHierarchyId = approvalHierarchyId;
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

	public String getLetterName() {
		return letterName;
	}

	public void setLetterName(String letterName) {
		this.letterName = letterName;
	}

}