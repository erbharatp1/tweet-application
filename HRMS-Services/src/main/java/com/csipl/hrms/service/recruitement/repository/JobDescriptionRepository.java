package com.csipl.hrms.service.recruitement.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.csipl.hrms.model.recruitment.JobDescription;

@Repository("jobDescriptionRepository")
public interface JobDescriptionRepository extends CrudRepository<JobDescription, Long> {

	@Query(" from JobDescription ORDER BY  jdId  DESC")
	public List<JobDescription> findAllJobDescription();

	public static final String DeleteJobDescriptionById = "DELETE FROM JobDescription WHERE jdId =?1";

	@Modifying
	@Query(value = DeleteJobDescriptionById, nativeQuery = true)
	public void deleteJobDescriptionById(Long jdId);

	@Query(" from JobDescription where jdId=?1 ORDER BY  jdId  DESC")
	public JobDescription findJobDescriptionById(Long jdId);

}
