package com.csipl.hrms.service.payroll.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.csipl.hrms.common.constant.StatusMessage;
import com.csipl.hrms.model.payroll.OtherIncome;

public interface OtherIncomeRepository extends CrudRepository<OtherIncome, Long> {

	String queryForStatusUPDATE = "UPDATE OtherIncome SET status='Declared' WHERE employeeId=?1 and financialYear=?2";

	@Query(" from OtherIncome where employeeId=?1 and financialYear.financialYearId=?2 and activeStatus='"+StatusMessage.ACTIVE_CODE+"' ")
	public List<OtherIncome> findAllOtherIncome(Long employeeId, Long financialYearId);

	@Query("SELECT SUM(amount) from OtherIncome where employeeId=?1 and financialYear.financialYearId=?2   and activeStatus='"+StatusMessage.ACTIVE_CODE+"'")
	public BigDecimal findOtherIncomeSum(Long employeeId, Long financialYearId);

	@Modifying
	@Query(value = queryForStatusUPDATE, nativeQuery = true)
	public void updateStatus(Long employeeId, String financialYear);

	@Modifying
	@Query("Update OtherIncome e SET e.activeStatus=:status WHERE e.otherIncomeId=:otherIncomeId")
	public int deleteOtherIncome(@Param("otherIncomeId") Long otherIncomeId, @Param("status") String status);

	@Modifying
	@Query("Update OtherIncome  SET tdsTransactionUpdateStatus='DE' where employeeId=?1 and financialYear.financialYearId=?2")
	public int updateTdsStatus(@Param("employeeId") Long employeeId, @Param("financialYearId") Long financialYearId);
	
	@Query("SELECT COUNT(tdsTransactionUpdateStatus) from OtherIncome where employeeId=?1 and financialYear.financialYearId=?2 and tdsTransactionUpdateStatus='AC' ")
	public Long getOtherIncomeUpdateStatusCount(Long employeeId, Long financialYearId);
}
