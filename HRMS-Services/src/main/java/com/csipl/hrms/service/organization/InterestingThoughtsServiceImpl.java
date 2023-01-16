package com.csipl.hrms.service.organization;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.csipl.hrms.model.employee.InterestingThought;
import com.csipl.hrms.service.organization.repository.InterestingThoughtsRepository;

@Service
@Transactional
public class InterestingThoughtsServiceImpl implements InterestingThoughtsService {
	@Autowired
	InterestingThoughtsRepository interestingThoughtsRepository;

	/**
	 * save
	 */
	@Override
	public InterestingThought save(InterestingThought interestingThought) {
		// TODO Auto-generated method stub
		return interestingThoughtsRepository.save(interestingThought);
	}

	/**
	 * findAllInterestingThought
	 */
	@Override
	public List<InterestingThought> findAllInterestingThought(Long companyId) {
		// TODO Auto-generated method stub
		return interestingThoughtsRepository.findInterestingThought(companyId);
	}

	/**
	 * deleteByinterestingThoughtsId
	 */
	@Override
	public void deleteByinterestingThoughtsId(Long interestingThoughtsId) {
		// TODO Auto-generated method stub
		interestingThoughtsRepository.delete(interestingThoughtsId);
	}

	@Override
	public InterestingThought findInterestingThought(Long interestingthoughtsId,Long companyId) {
		// TODO Auto-generated method stub
		return interestingThoughtsRepository.findOne(interestingthoughtsId,companyId);
	}

}
