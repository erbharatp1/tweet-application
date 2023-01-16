package com.csipl.hrms.service.recruitement;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.csipl.hrms.model.recruitment.JobDescription;
import com.csipl.hrms.service.recruitement.repository.JobDescriptionRepository;

@Service("jobDescriptionService")

public class JobDescriptionServiceImpl  implements JobDescriptionService{

	@Autowired
	private JobDescriptionRepository jobDescriptionRepository;
	
	@Override
	public JobDescription save(JobDescription jobDescription) {
		// TODO Auto-generated method stub
		return jobDescriptionRepository.save(jobDescription);
	}

	@Override
	public List<JobDescription> findAllJobDescription() {
		// TODO Auto-generated method stub
		return jobDescriptionRepository.findAllJobDescription();
	}

	@Override
	@Transactional
	public void deleteJobDescriptionById(Long jdId) {
		// TODO Auto-generated method stub
		jobDescriptionRepository.deleteJobDescriptionById(jdId);
	}

	@Override
	public JobDescription findJobDescriptionById(Long jdId) {
		// TODO Auto-generated method stub
		return jobDescriptionRepository.findJobDescriptionById(jdId);
	}

}
