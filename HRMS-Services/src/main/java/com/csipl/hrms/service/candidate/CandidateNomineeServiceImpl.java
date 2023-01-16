package com.csipl.hrms.service.candidate;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.csipl.hrms.model.candidate.CandidateNominee;
import com.csipl.hrms.service.candidate.repository.CandidateNomineeRepository;
@Service("candidateNominee")
public class CandidateNomineeServiceImpl implements CandidateNomineeService{

	@Autowired
	private CandidateNomineeRepository candidateNomineeRepository;
	
	@Override
	public List<CandidateNominee> save(List<CandidateNominee> candidateNominee) {
	
		return (List<CandidateNominee>) candidateNomineeRepository.save(candidateNominee);
	}

	@Override
	public List<CandidateNominee> findAllNominee(Long candidateId) {
		
		return candidateNomineeRepository.findAllNominee(candidateId);
	}

}
