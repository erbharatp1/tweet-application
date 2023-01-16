package com.csipl.hrms.service.recruitement;

import java.util.List;

import com.csipl.hrms.model.recruitment.JobDescription;

public interface JobDescriptionService {

	public JobDescription save(JobDescription jobDescription);

	public List<JobDescription> findAllJobDescription();

	public void deleteJobDescriptionById(Long jdId);

	public JobDescription findJobDescriptionById(Long jdId);

}
