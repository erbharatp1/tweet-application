package com.csipl.hrms.model.employee;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * The persistent class for the ApprovalHierarchyMaster database table.
 * 
 */
@Entity
@NamedQuery(name = "ApprovalHierarchyMaster.findAll", query = "SELECT a FROM ApprovalHierarchyMaster a")
public class ApprovalHierarchyMaster implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long approvalHierarchyMasterId;

	private String activeStatus;

	private Long companyId;

	@Temporal(TemporalType.DATE)
	private Date dateCreated;

	@Temporal(TemporalType.DATE)
	private Date dateUpdate;

	private Long userId;

	private Long userIdUpdate;

	// bi-directional many-to-one association to ApprovalHierarchy
	@OneToMany(mappedBy = "approvalHierarchyMaster", cascade = CascadeType.ALL)
	private List<ApprovalHierarchy> approvalHierarchies;

	// bi-directional many-to-one association to Letter
	@ManyToOne
	@JoinColumn(name = "letterId")
	private Letter letter;

	public ApprovalHierarchyMaster() {
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

	public List<ApprovalHierarchy> getApprovalHierarchies() {
		return this.approvalHierarchies;
	}

	public void setApprovalHierarchies(List<ApprovalHierarchy> approvalHierarchies) {
		this.approvalHierarchies = approvalHierarchies;
	}

	public ApprovalHierarchy addApprovalHierarchy(ApprovalHierarchy approvalHierarchy) {
		getApprovalHierarchies().add(approvalHierarchy);
		approvalHierarchy.setApprovalHierarchyMaster(this);

		return approvalHierarchy;
	}

	public ApprovalHierarchy removeApprovalHierarchy(ApprovalHierarchy approvalHierarchy) {
		getApprovalHierarchies().remove(approvalHierarchy);
		approvalHierarchy.setApprovalHierarchyMaster(null);

		return approvalHierarchy;
	}

	public Letter getLetter() {
		return this.letter;
	}

	public void setLetter(Letter letter) {
		this.letter = letter;
	}

}