package com.csipl.hrms.dto.employee;

import java.util.Date;

public class LetterDaclarationDTO  {
 
	private Long declarationId;

	private Long companyId;
	 
	private Date dateCreated;
	 
	private Date dateUpdate;

	private String declarationContant;

	private String declarationStatus;

	private String heading;

	private Long letterId;

	private Long userId;

	private Long userIdUpdate;

	public LetterDaclarationDTO() {
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