package com.csipl.hrms.service.recruitement;

import java.util.List;

import com.csipl.hrms.model.recruitment.QuestionTamplates;

public interface QuestionTamplatesService {

	public QuestionTamplates save(QuestionTamplates questionTamplates);

	public List<QuestionTamplates> fetchQuestionList(Long companyId);

	public void deleteByfetchQuestionTampId(Long questionTamplatesId);

	public QuestionTamplates findQuestionTampById(Long questionTamplateId);

}
