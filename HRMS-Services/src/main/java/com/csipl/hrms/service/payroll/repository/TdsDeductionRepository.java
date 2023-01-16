package com.csipl.hrms.service.payroll.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.csipl.hrms.common.constant.StatusMessage;
import com.csipl.hrms.model.payroll.TdsDeduction;
import com.csipl.hrms.model.payrollprocess.FinancialYear;

public interface TdsDeductionRepository extends CrudRepository<TdsDeduction,Long> {

	
//	@Query(value = "SELECT td.tdsDeductionId,  td.companyId, td.financialYearId,td.taxTobeDeductedMonthly,td.taxDeductedMonthly,td.userId,td.totalTax,td.remark, emp.firstName, emp.lastName,emp.employeeId,desig.designationName,td.activeStatus FROM TdsDeduction td join Employee emp JOIN Designation desig on td.employeeId= emp.employeeId AND emp.designationId =desig.designationId  WHERE td.companyId=?1 and td.financialYearId=?2 AND td.activeStatus='AC'", nativeQuery = true)
	
	@Query(value = "SELECT td.tdsDeductionId,  td.companyId, td.financialYearId, tsc.netTaxMonthly as taxToBeDedductedMonthaly, td.taxDeductedMonthly,"
			+ "  td.userId, tsc.netTaxYearly, td.remark, "
			+ "  	emp.firstName, emp.lastName,emp.employeeId,desig.designationName,td.activeStatus"
			+ "  	FROM TdsDeduction td join Employee emp JOIN Designation desig on td.employeeId= emp.employeeId AND "
			+ "  	emp.designationId =desig.designationId LEFT JOIN TdsSummaryChange tsc on tsc.employeeId=td.employeeId and tsc.financialYearId=td.financialYearId "
			+ " 	and tsc.active='AC' " + 
			"   	WHERE td.companyId=?1 and td.financialYearId=?2 AND td.activeStatus='AC' " ,nativeQuery = true)
	public List<Object[]> findAlltdsDeduction(Long companyId,Long financialYearId);
	
	
	@Modifying
	@Query(value = "UPDATE TdsDeduction SET activeStatus='"+StatusMessage.DEACTIVE_CODE+"' WHERE employeeId=?1  and  financialYearId=?2 ", nativeQuery = true)
	public int deleteTdsDeduction(Long employeeId, Long financialYearId);
	
	
	@Query("from TdsDeduction where companyId=?1  and financialYearId=?2 and employeeId=?3 and activeStatus='"+StatusMessage.ACTIVE_CODE+"'")
	public TdsDeduction getTdsDeduction(Long companyId, Long financialYearId, Long employeeId);
	
	
	String employee = "SELECT emp.employeeId,emp.firstName,emp.lastName,emp.employeeCode,emp.employeeLogoPath ,dept.departmentId, dept.departmentName, desg.designationId,desg.designationName, emp.dateOfJoining, emp.tdsLockUnlockStatus  FROM Employee emp JOIN Department dept on dept.departmentId = emp.departmentId JOIN Designation desg ON desg.designationId= emp.designationId where emp.companyId=?1 and emp.activeStatus=?2 AND emp.endDate is null ORDER BY emp.employeeId DESC";
	@Query(value = employee, nativeQuery = true)
	public List<Object[]> findAllEmployees(Long companyId, String status);

	
	@Modifying
	@Query(value = "UPDATE Employee SET tdsLockUnlockStatus=:tdsLockUnlockStatus WHERE employeeId in :ids ", nativeQuery = true)
	public int updateTdsLockUnlockStatus(@Param("ids") List<Long> ids, @Param("tdsLockUnlockStatus") String tdsLockUnlockStatus);
	
	
	@Modifying
	@Query(value = "UPDATE Employee SET tdsLockUnlockStatus=:tdsLockUnlockStatus , tdsStatus=:tdsStatus WHERE employeeId in :ids ", nativeQuery = true)
	public int updateTdsUnlockStatus(@Param("ids") List<Long> ids, @Param("tdsLockUnlockStatus") String tdsLockUnlockStatus, @Param("tdsStatus") String tdsStatus);

	@Modifying
	@Query(value = "UPDATE Employee SET tdsLockUnlockStatus=:tdsLockedStatus , tdsStatus=:tdsStatus WHERE employeeId =:employeeId ", nativeQuery = true)
	public void updateTdsStatusById(@Param("employeeId") Long employeeId,@Param("tdsStatus") String tdsStatus,@Param("tdsLockedStatus") String tdsLockedStatus);

	@Query(value = "SELECT tdsLockUnlockStatus from Employee WHERE companyId=?1 and employeeId =?2 ", nativeQuery = true)
	public String getTdsLockUnlockStatus(Long companyId, Long employeeId);


	String summary = "SELECT emp.employeeId, emp.firstName, emp.lastName,desig.designationName, pc.processMonth,td.taxDeductedMonthly,"
			+ " 		tsc.netTaxYearly, tsc.netTaxMonthly , rp.tdsAmount ,td.remark "
			+ "			FROM TdsDeduction td join Employee emp JOIN Designation desig on td.employeeId= emp.employeeId AND   "
			+ "		  	emp.designationId =desig.designationId LEFT JOIN TdsSummaryChange tsc on tsc.employeeId=td.employeeId and tsc.financialYearId=td.financialYearId  "
			+ "		  	and tsc.active='AC' left JOIN PayrollControl pc on pc.financialYearId=td.financialYearId  left join ReportPayOut rp on rp.employeeId=emp.employeeId and rp.processMonth=pc.processMonth "
			+ "		  	WHERE td.companyId=?1 and td.financialYearId=?2 AND td.activeStatus='AC' and emp.employeeId=?3 ";
	
//	String summary= "SELECT emp.employeeId, emp.firstName, emp.lastName,desig.designationName, pc.processMonth,td.taxDeductedMonthly,    \r\n" + 
//			"			     		tsc.netTaxYearly, tsc.netTaxMonthly , rp.tdsAmount ,td.remark     \r\n" + 
//			"			    			FROM TdsDeduction td join Employee emp JOIN Designation desig on td.employeeId= emp.employeeId AND       \r\n" + 
//			"			    		  	emp.designationId =desig.designationId LEFT JOIN TdsSummaryChange tsc on tsc.employeeId=td.employeeId and tsc.financialYearId=td.financialYearId      \r\n" + 
//			"			    		  	and tsc.active='AC' left JOIN PayrollControl pc on pc.financialYearId=td.financialYearId   join ReportPayOut rp on rp.employeeId=emp.employeeId and rp.processMonth=pc.processMonth     \r\n" + 
//			"			    		  	WHERE td.companyId=?1 and td.financialYearId=?2 AND td.activeStatus='AC' and emp.employeeId=?3 ORDER BY pc.controlId";
	@Query(value = summary, nativeQuery = true)
	public List<Object[]> getTdsSummary(Long companyId, Long financialYearId, Long employeeId);
	
 
	
}
