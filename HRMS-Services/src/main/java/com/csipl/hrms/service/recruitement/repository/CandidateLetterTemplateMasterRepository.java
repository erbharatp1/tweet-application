package com.csipl.hrms.service.recruitement.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.csipl.hrms.model.recruitment.CandidateLetterTemplateMaster;

@Repository("candidateLetterTemplateMasterRepository")
public interface CandidateLetterTemplateMasterRepository extends CrudRepository<CandidateLetterTemplateMaster, Long> {

	
	@Query(" from CandidateLetterTemplateMaster  where templateFlag=?1 ")
	CandidateLetterTemplateMaster findCandidateLetterByTemplateType(String templateFlag);

}
