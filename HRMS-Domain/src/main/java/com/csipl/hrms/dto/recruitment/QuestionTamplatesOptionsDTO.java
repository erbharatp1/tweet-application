package com.csipl.hrms.dto.recruitment;

import java.util.Date;

public class QuestionTamplatesOptionsDTO {

	private Long optionId;

	private Long questionTamplateId;

	private Date dateCreated;

	private Date dateUpdate;

	private String status;

	private Long userId;

	private Long companyId;

	private Long userIdUpdate;
	private QuestionTamplatesDTO questionTamplatesDTO;

	public QuestionTamplatesOptionsDTO() {
		super();
	}

	public QuestionTamplatesDTO getQuestionTamplatesDTO() {
		return questionTamplatesDTO;
	}

	public void setQuestionTamplatesDTO(QuestionTamplatesDTO questionTamplatesDTO) {
		this.questionTamplatesDTO = questionTamplatesDTO;
	}

	 

	public Long getOptionId() {
		return optionId;
	}

	public void setOptionId(Long optionId) {
		this.optionId = optionId;
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

}