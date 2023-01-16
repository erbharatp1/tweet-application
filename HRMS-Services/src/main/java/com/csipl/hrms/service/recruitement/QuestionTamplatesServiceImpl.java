package com.csipl.hrms.service.recruitement;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.csipl.hrms.model.recruitment.QuestionTamplates;
import com.csipl.hrms.service.recruitement.repository.QuestionTamplatesRepository;

/**
 * @author Bharat
 *
 */
@Service("questionTamplatesService")
@Transactional
public class QuestionTamplatesServiceImpl implements QuestionTamplatesService {

	@Autowired
	private QuestionTamplatesRepository questionTamplatesRepository;

	@Override
	public QuestionTamplates save(QuestionTamplates questionTamplates) {
		if (questionTamplates.getQuestionTamplateId() != null && questionTamplates.getQuestionTamplateId() != 0) {
			questionTamplatesRepository.deleteQuestionTamplateById(questionTamplates.getQuestionTamplateId());
		}
		return questionTamplatesRepository.save(questionTamplates);
	}

	@Override
	public List<QuestionTamplates> fetchQuestionList(Long companyId) {
		// TODO Auto-generated method stub
		return questionTamplatesRepository.fetchQuestionList(companyId);
	}

	@Override
	public void deleteByfetchQuestionTampId(Long questionTamplatesId) {
		// TODO Auto-generated method stub
		questionTamplatesRepository.delete(questionTamplatesId);
	}

	@Override
	public QuestionTamplates findQuestionTampById(Long questionTamplateId) {
		// TODO Auto-generated method stub
		return questionTamplatesRepository.findOne(questionTamplateId);
	} 
	


}
