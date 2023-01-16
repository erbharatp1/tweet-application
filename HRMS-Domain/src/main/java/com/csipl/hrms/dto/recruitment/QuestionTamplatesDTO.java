package com.csipl.hrms.dto.recruitment;

import java.util.Date;
import java.util.List;

import com.csipl.hrms.model.recruitment.QuestionTamplatesOptions;

public class QuestionTamplatesDTO {

	private Long questionTamplateId;

	private Date dateCreated;

	private Date dateUpdate;

	private String questionDescription;

	private String status;

	private String desirableAnswer;

	private Long userId;

	private Long companyId;

	private Long userIdUpdate;
	
	private List<QuestionTamplatesOptionsDTO> questionTamplatesOptions;

	public QuestionTamplatesDTO() {
		super();
	}

 

	public List<QuestionTamplatesOptionsDTO> getQuestionTamplatesOptions() {
		return questionTamplatesOptions;
	}



	public void setQuestionTamplatesOptions(List<QuestionTamplatesOptionsDTO> questionTamplatesOptions) {
		this.questionTamplatesOptions = questionTamplatesOptions;
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