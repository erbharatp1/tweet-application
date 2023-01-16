package com.csipl.hrms.service.payroll.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.csipl.hrms.model.payroll.PreviousEmployerIncomeTds;

public interface PreviousEmployerIncomeRepository extends CrudRepository<PreviousEmployerIncomeTds, Long> {

	String findPreviousEmployerIncome = "SELECT pi.previousEmployerIncomeId,pi.particular, pit.previousEmployerIncomeTdsId, pit.amount,pit.financialYearId, \r\n"
			+ "pit.employeeId, pit.userId, pit.dateCreated FROM PreviousEmployerIncome pi LEFT JOIN PreviousEmployerIncomeTds pit ON \r\n"
			+ "pi.previousEmployerIncomeId=pit.previousEmployerIncomeId and employeeId=?1 and financialYearId=?2";
	
	@Query(value = findPreviousEmployerIncome ,nativeQuery = true)
	public List<Object[]> findAllPreviousEmployerIncome(Long employeeId, Long financialYearId);
	
	@Query("from PreviousEmployerIncomeTds where employeeId=?1 and financialYearId=?2")
	public List<PreviousEmployerIncomeTds> getAllPreviousEmployerIncome(Long employeeId, Long financialYearId);
	
	@Modifying
	@Query("Update PreviousEmployerIncomeTds  SET tdsTransactionUpdateStatus='DE' where employeeId=?1 and financialYearId=?2")
	public int updateTdsStatus(@Param("employeeId") Long employeeId, @Param("financialYearId") Long financialYearId);
	
	
	@Query("SELECT COUNT(tdsTransactionUpdateStatus) from PreviousEmployerIncomeTds  where employeeId=?1 and financialYearId=?2  and tdsTransactionUpdateStatus='AC' ")
	public Long getTDSTransactionUpdateStatusCount(Long employeeId,  Long financialYearId);
}
