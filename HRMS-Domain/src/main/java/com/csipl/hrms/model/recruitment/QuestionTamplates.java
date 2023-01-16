package com.csipl.hrms.model.recruitment;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
 

/**
 * The persistent class for the QuestionTamplates database table.
 * 
 */
@Entity
@NamedQuery(name = "QuestionTamplates.findAll", query = "SELECT q FROM QuestionTamplates q")
public class QuestionTamplates implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long questionTamplateId;
	@Temporal(TemporalType.DATE)
	private Date dateCreated;
	@Temporal(TemporalType.DATE)
	private Date dateUpdate;
	private String questionDescription;
	private String status;
	private String desirableAnswer;
	private Long userId;
	private Long companyId;
	private Long userIdUpdate;

	@OneToMany(mappedBy = "questionTamplates", cascade = CascadeType.ALL)
	private List<QuestionTamplatesOptions> questionTamplatesOptions;
	
	public List<QuestionTamplatesOptions> getQuestionTamplatesOptions() {
		return questionTamplatesOptions;
	}

	public void setQuestionTamplatesOptions(List<QuestionTamplatesOptions> questionTamplatesOptions) {
		this.questionTamplatesOptions = questionTamplatesOptions;
	}

	public QuestionTamplates() {
		super();
	}

	public Long getQuestionTamplateId() {
		return questionTamplateId;
	}

	public void setQuestionTamplateId(Long questionTamplateId) {
		this.questionTamplateId = questionTamplateId;
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

	public String getQuestionDescription() {
		return questionDescription;
	}

	public void setQuestionDescription(String questionDescription) {
		this.questionDescription = questionDescription;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getDesirableAnswer() {
		return desirableAnswer;
	}

	public void setDesirableAnswer(String desirableAnswer) {
		this.desirableAnswer = desirableAnswer;
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

}