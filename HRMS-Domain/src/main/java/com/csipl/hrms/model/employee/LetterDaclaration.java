package com.csipl.hrms.model.employee;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;

/**
 * The persistent class for the LetterDaclaration database table.
 * 
 */
@Entity
@NamedQuery(name = "LetterDaclaration.findAll", query = "SELECT l FROM LetterDaclaration l")
public class LetterDaclaration implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long declarationId;

	private Long companyId;

	@Temporal(TemporalType.DATE)
	private Date dateCreated;

	@Temporal(TemporalType.DATE)
	private Date dateUpdate;

	private String declarationContant;

	private String declarationStatus;

	private String heading;

	private Long letterId;

	private Long userId;

	private Long userIdUpdate;

	public LetterDaclaration() {
	}

	public Long getDeclarationId() {
		return declarationId;
	}

	public void setDeclarationId(Long declarationId) {
		this.declarationId = declarationId;
	}

	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
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

	public String getDeclarationContant() {
		return declarationContant;
	}

	public void setDeclarationContant(String declarationContant) {
		this.declarationContant = declarationContant;
	}

	public String getDeclarationStatus() {
		return declarationStatus;
	}

	public void setDeclarationStatus(String declarationStatus) {
		this.declarationStatus = declarationStatus;
	}

	public String getHeading() {
		return heading;
	}

	public void setHeading(String heading) {
		this.heading = heading;
	}

	public Long getLetterId() {
		return letterId;
	}

	public void setLetterId(Long letterId) {
		this.letterId = letterId;
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

}