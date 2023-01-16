package com.csipl.hrms.model.recruitment;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * The persistent class for the QuestionTamplatesOptions database table.
 * 
 */
/**
 * @author Bharat
 *
 */
@Entity
@NamedQuery(name = "QuestionTamplatesOptions.findAll", query = "SELECT q FROM QuestionTamplatesOptions q")
public class QuestionTamplatesOptions implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long optionId;
 
	@Temporal(TemporalType.DATE)
	private Date dateCreated;
	@Temporal(TemporalType.DATE)
	private Date dateUpdate;
	private String status;
	private Long userId;
	private Long companyId;
	private Long userIdUpdate;
	@ManyToOne
	@JoinColumn(name = "questionTamplateId")
	private QuestionTamplates questionTamplates;

	public QuestionTamplatesOptions() {
		super();
	}
	 
	 
	public Long getOptionId() {
		return optionId;
	}





	public void setOptionId(Long optionId) {
		this.optionId = optionId;
	}





	public QuestionTamplates getQuestionTamplates() {
		return questionTamplates;
	}

	public void setQuestionTamplates(QuestionTamplates questionTamplates) {
		this.questionTamplates = questionTamplates;
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	public Long getUserIdUpdate() {
		return userIdUpdate;
	}

	public void setUserIdUpdate(Long userIdUpdate) {
		this.userIdUpdate = userIdUpdate;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}