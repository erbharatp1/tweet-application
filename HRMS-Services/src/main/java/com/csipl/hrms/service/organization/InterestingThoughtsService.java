package com.csipl.hrms.service.organization;

import java.util.List;

import com.csipl.hrms.model.employee.InterestingThought;

public interface InterestingThoughtsService {
	/**
	 * 
	 * save}
	 */
	public InterestingThought save(InterestingThought interestingThought);

	/**
	 * 
	 * findAllInterestingThought}
	 */
	public List<InterestingThought> findAllInterestingThought(Long companyId);

	/**
	 * 
	 * deleteByinterestingThoughtsId}
	 */
	public void deleteByinterestingThoughtsId(Long interestingThoughtsId);
	
	

	public InterestingThought findInterestingThought(Long interestingthoughtsId,Long companyId);

}
