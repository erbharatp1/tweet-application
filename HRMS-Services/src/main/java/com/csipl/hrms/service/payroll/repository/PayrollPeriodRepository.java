package com.csipl.hrms.service.payroll.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.csipl.hrms.model.payrollprocess.FinancialYear;

public interface PayrollPeriodRepository extends CrudRepository<FinancialYear, Long> {
	@Query(" from FinancialYear where financialYear=?1 and companyId=?2")
	public FinancialYear findFinancialYear(String financialYear, Long companyId);

	@Query(" from FinancialYear where  companyId=?1 order by financialYearId desc ")
	public FinancialYear findLatestFinancialYear(Long companyId);

	@Query(" from FinancialYear where  companyId=?1 order by financialYearId desc ")
	public List<FinancialYear> getFinancialYearList(Long companyId);

//	@Modifying
//	@Query("Update FinancialYear f SET f.activeStatus=:status WHERE f.financialYearId=:financialYearId")
//	// @Query("Update activeStatus=?2 Designation d where designationId=?1 ")
//	public void updateById(@Param("financialYearId") Long financialYearId, @Param("status") String status);

	
	@Modifying
	@Query(value = "Update FinancialYear f,PayrollControl p SET f.activeStatus=?2 ,p.activeStatus=?3  WHERE f.financialYearId=?1 and p.financialYearId=?1 ", nativeQuery = true)
	public void updateById(Long financialYearId, String status, String PayrollControlStatus);
	
//	@Modifying
//	@Query(value = "Update FinancialYear f SET f.activeStatus=?2 WHERE f.companyId=?1", nativeQuery = true)
//	public void updateStatusByCompanyId(Long companyId, String status);

	@Modifying
	@Query(value = "Update FinancialYear f,PayrollControl p SET f.activeStatus=?2 ,p.activeStatus=?3  WHERE f.companyId=?1", nativeQuery = true)
	public void updateStatusByCompanyId(Long companyId, String status, String PayrollControlStatus);
	
	public static final String DELETE_PAYROLLCONTROL = "Delete  From PayrollControl Where financialYearId=?1";
	
	@Modifying
    @Query(value = DELETE_PAYROLLCONTROL, nativeQuery = true)
    public void deletePayrollControlByfinancialYearId(Long  financialYearId);
	
	@Query("FROM FinancialYear where financialYearId  NOT IN (?1) and financialYear=?2 and companyId=?3")
	public FinancialYear checkFinancialYear(Long financialYearId,String financialYear, Long companyId);

	
}
