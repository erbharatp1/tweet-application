package com.csipl.hrms.service.common.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.csipl.hrms.model.common.ReportLov;

public interface ReportLovRepository extends CrudRepository<ReportLov, Long> {

	@Query(" from ReportLov where status = 'AC'  ")
	public List<ReportLov> findAllReport();

	@Query(" from ReportLov where moduleName =?1  ")
	public List<ReportLov> findReportByModuleName(String moduleName);

}
