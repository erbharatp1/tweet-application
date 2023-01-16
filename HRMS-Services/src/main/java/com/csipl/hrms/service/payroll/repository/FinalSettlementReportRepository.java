package com.csipl.hrms.service.payroll.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.csipl.hrms.model.payroll.FinalSettlementReport;

public interface FinalSettlementReportRepository extends CrudRepository<FinalSettlementReport, Long> {

	@Query(value="SELECT * FROM FinalSettlementReport WHERE employeeId=?1",nativeQuery=true)
	public List<FinalSettlementReport> getFinalSettlementReport(Long employeeId);
}
