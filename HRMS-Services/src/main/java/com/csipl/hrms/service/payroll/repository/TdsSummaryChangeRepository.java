package com.csipl.hrms.service.payroll.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;


import com.csipl.hrms.model.payroll.TdsSummary;
import com.csipl.hrms.model.payroll.TdsSummaryChange;

public interface TdsSummaryChangeRepository extends CrudRepository<TdsSummaryChange, Long> {
	
	@Query("from TdsSummaryChange ts  where ts.employee.employeeId=?1 and ts.financialYearId=?2 and ts.active='AC' ")  //
	public TdsSummaryChange findTdsSummary(Long empId,Long financialYearId);	


}
