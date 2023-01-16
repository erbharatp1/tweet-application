package com.csipl.hrms.service.recruitement;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.csipl.hrms.model.recruitment.SelectionRuleDescription;
import com.csipl.hrms.service.recruitement.repository.CandidateSelectionRuleRepository;

@Service
public class CandidateSelectionRuleServiceImpl implements CandidateSelectionRuleService {

	@Autowired
	private CandidateSelectionRuleRepository candidateSelectionRuleRepository;

	@Override
	public void saveCandidateSelectionRule(SelectionRuleDescription candidateSelectionRule) {
		candidateSelectionRule.setDateCreated(new Date());
		candidateSelectionRule.setUpdatedDate(new Date());
		candidateSelectionRuleRepository.save(candidateSelectionRule);
	}

	@Override
	public List<SelectionRuleDescription> getAllCandidateSelectionRule() {
		return (List<SelectionRuleDescription>) candidateSelectionRuleRepository.findAll();
	}

	@Override
	public void deleteCandidateSelectionRule(Long selectionRuleId) {
		candidateSelectionRuleRepository.delete(selectionRuleId);
	}

	@Override
	public void updateCandidateSelectionRule(List<SelectionRuleDescription> candidateSelectionRule) {
		candidateSelectionRuleRepository.save(candidateSelectionRule);	
	}

}
