package com.csipl.hrms.model.employee;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the Letter database table.
 * 
 */
@Entity
@NamedQuery(name="Letter.findAll", query="SELECT l FROM Letter l")
public class Letter implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long letterId;

	private String activeStatus;

	@Temporal(TemporalType.DATE)
	private Date dateCreated;

	@Temporal(TemporalType.DATE)
	private Date dateUpdate;

	@Lob
	private String letterDecription;

	private String letterName;

	private String letterType;

	private Long userId;

	private Long gradeId;

	private Long userIdUpdate;

	private Long companyId;
	private String enableGrade;
	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	public Letter() {
	}

	public Long getLetterId() {
		return this.letterId;
	}

	public void setLetterId(Long letterId) {
		this.letterId = letterId;
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

	public String getLetterDecription() {
		return this.letterDecription;
	}

	public void setLetterDecription(String letterDecription) {
		this.letterDecription = letterDecription;
	}

	public String getLetterName() {
		return this.letterName;
	}

	public void setLetterName(String letterName) {
		this.letterName = letterName;
	}

	public String getLetterType() {
		return this.letterType;
	}

	public void setLetterType(String letterType) {
		this.letterType = letterType;
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

	public Long getGradeId() {
		return gradeId;
	}

	public void setGradeId(Long gradeId) {
		this.gradeId = gradeId;
	}


	public String getEnableGrade() {
		return enableGrade;
	}

	public void setEnableGrade(String enableGrade) {
		this.enableGrade = enableGrade;
	}
}