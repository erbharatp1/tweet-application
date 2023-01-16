package com.csipl.hrms.service.recruitement.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.csipl.hrms.model.recruitment.SelectionRuleDescription;

@Repository
public interface CandidateSelectionRuleRepository extends CrudRepository<SelectionRuleDescription, Long>{

}
