package com.csipl.hrms.service.payroll.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.csipl.hrms.model.payroll.FinalSettlement;


public interface FinalSettleRepository extends CrudRepository<FinalSettlement, Long>{
	@Query("from FinalSettlement where employeeId=?1")
	public FinalSettlement getFinalSettlementById(Long employeeId);

	
	String findFinalSettlementEmployee = "SELECT  DISTINCT(e.employeeId),e.firstName ,e.lastName,e.employeeCode,ad.cityId,e.activeStatus,   \r\n"
			+ "e.employeeLogoPath From Employee e LEFT JOIN\r\n" + "Address ad On ad.cityId=e.cityId WHERE e.companyId=?1\r\n"
			+ " AND e.activeStatus=?2 ORDER BY e.employeeId DESC";

	@Query(value = findFinalSettlementEmployee, nativeQuery = true)
	public List<Object[]> getFinalSettlementEmployee(Long companyId,String status);
}
