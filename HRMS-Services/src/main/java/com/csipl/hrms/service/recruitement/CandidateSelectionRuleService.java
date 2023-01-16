package com.csipl.hrms.service.recruitement;

import java.util.List;

import com.csipl.hrms.model.recruitment.SelectionRuleDescription;

public interface CandidateSelectionRuleService {

	public void saveCandidateSelectionRule(SelectionRuleDescription candidateSelectionRule);

	public void updateCandidateSelectionRule(List<SelectionRuleDescription> candidateSelectionRule);

	public List<SelectionRuleDescription> getAllCandidateSelectionRule();

	public void deleteCandidateSelectionRule(Long selectionRuleId);

}
