package com.csipl.hrms.service.recruitement.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.csipl.hrms.model.recruitment.QuestionTamplates;

@Repository("questionTamplatesRepository")
public interface QuestionTamplatesRepository extends CrudRepository<QuestionTamplates, Long> {
	public static final String DELETE_QUESTION_TAMPLATE_BY_ID = "DELETE FROM QuestionTamplatesOptions WHERE questionTamplateId = ?1";
	
	@Query(" from QuestionTamplates where companyId=?1  ORDER BY  questionTamplateId  DESC")
	List<QuestionTamplates> fetchQuestionList(Long companyId);

	@Modifying
	@Query(value = DELETE_QUESTION_TAMPLATE_BY_ID, nativeQuery = true)
	void deleteQuestionTamplateById(Long questionTamplateId);

}
