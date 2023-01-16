package com.csipl.hrms.service.candidate;

import java.util.List;
import com.csipl.hrms.model.candidate.CandidateNominee;



public interface CandidateNomineeService {

	public List<CandidateNominee> save(List<CandidateNominee> candidateNominee);
	public List<CandidateNominee> findAllNominee(Long candidateId);
}
