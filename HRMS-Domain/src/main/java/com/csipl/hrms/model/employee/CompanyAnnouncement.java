package com.csipl.hrms.model.employee;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.csipl.hrms.model.organisation.Department;


/**
 * The persistent class for the CompanyAnnouncement database table.
 * 
 */
@Entity
@Table(name = "CompanyAnnouncement")
@NamedQuery(name="CompanyAnnouncement.findAll", query="SELECT c FROM CompanyAnnouncement c")
public class CompanyAnnouncement implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long companyAnnouncementId;

	private String activeStatus;

	@Temporal(TemporalType.DATE)
	private Date dateCreated;

	@Temporal(TemporalType.DATE)
	private Date dateUpdate;

	@ManyToOne
	@JoinColumn(name="departmentId")
	private Department department;

	private Long userId;

	private Long userIdUpdate;

	@ManyToOne
	@JoinColumn(name = "massCommunicationId")
	private MassCommunication massCommunication;
	//bi-directional many-to-one association to MassCommunication
//	@ManyToOne
	//@JoinColumn(name="massCommunicationId")
//	private Long massCommunicationId;

	public CompanyAnnouncement() {
	}

	public MassCommunication getMassCommunication() {
		return massCommunication;
	}

	public void setMassCommunication(MassCommunication massCommunication) {
		this.massCommunication = massCommunication;
	}

	public Long getCompanyAnnouncementId() {
		return this.companyAnnouncementId;
	}

	public void setCompanyAnnouncementId(Long companyAnnouncementId) {
		this.companyAnnouncementId = companyAnnouncementId;
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

	 

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
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

//	public Long getMassCommunicationId() {
//		return massCommunicationId;
//	}
//
//	public void setMassCommunicationId(Long massCommunicationId) {
//		this.massCommunicationId = massCommunicationId;
//	}

//	public MassCommunication getMassCommunication() {
//		return this.massCommunication;
//	}
//
//	public void setMassCommunication(MassCommunication massCommunication) {
//		this.massCommunication = massCommunication;
//	}

}