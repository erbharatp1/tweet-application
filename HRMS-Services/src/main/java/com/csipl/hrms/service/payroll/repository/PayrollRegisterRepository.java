package com.csipl.hrms.service.payroll.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.csipl.hrms.model.payrollprocess.PayRegisterHd;



public interface PayrollRegisterRepository extends CrudRepository<PayRegisterHd, Long>{
	@Query(" from PayRegisterHd  where companyId=?1  AND  processMonth=?2 ")
	List<PayRegisterHd> findPayrollRegisterList(Long companyID, String payrollMonth); 
	
//	String fetchPayrollRegisterProcessMonth="SELECT DISTINCT ph.payRegisterHdId, ph.processMonth FROM  PayRegisterHd"
//			+ " ph WHERE ph.companyId=?1 group by ph.processMonth";
//	@Query(value=fetchPayrollRegisterProcessMonth, nativeQuery=true)
//	List<Object[]> findPayrollRegisterProcessMonth(Long companyId); 
//	
	
	String fetchPayrollRegisterProcessMonth = "CALL  Pro_fetch_process_month(:p_comp_id)";
	@Query(value = fetchPayrollRegisterProcessMonth, nativeQuery = true)
	public List<Object> findPayrollRegisterProcessMonth(@Param(value = "p_comp_id") Long p_comp_id);
	
	
	
	String fetchPayrollRegisterList ="SELECT pHd.payRegisterHdId,pHd.processMonth,pHd.dateUpdate,replace(concat(\"/\",GROUP_CONCAT( DISTINCT dept.departmentCode),\"/\"),\",\",\"/\") as departmentCode FROM PayRegisterHd pHd JOIN PayRegister pr ON pHd.payRegisterHdId = pr.payRegisterHdId \r\n" + 
			"JOIN Department dept ON dept.departmentId = pr.departmentId WHERE pHd.companyId =?1 and pHd.processMonth =?2 and pr.activeStatus='AC'\r\n" + 
			"group by pHd.payRegisterHdId";
	@Query(value=fetchPayrollRegisterList ,nativeQuery = true)
	public List<Object[]> fetchPayrollRegisterList(Long companyID, String payrollMonth);
	
	String fetchPayrollRegisterListById ="select  rp.Name,rp.employeeCode,d.departmentName,\r\n" + 
			"rp.dateOfJoining,rp.payDays,rp.GrossSalary,rp.TotalDeduction ,rp.NetPayableAmount ,rp.TotalEarning, rp.processmonth,rp.bankName,rp.accountNumber,rp.IFSCCode \r\n" + 
			"from PayRegister p join ReportPayOut rp on p.employeeId=rp.employeeId \r\n" + 
			"JOIN Department d on p.departmentId= d.departmentId JOIN PayOut po ON rp.employeeId = po.employeeId \r\n" + 
			"WHERE p.payRegisterHdId =?1 and po.processMonth=?2 group by p.employeeId";
	
	@Query(value=fetchPayrollRegisterListById ,nativeQuery = true)
	public List<Object[]> fetchPayrollRegisterListById(Long payRegisterHdId,String  payrollMonth);
	
	String fetchPayrollRegisterListPaymentTransferSheet ="SELECT rp.Name,rp.employeeCode,rp.companyId ,comp.companyName,dept.departmentName,rp.bankName,rp.accountNumber,rp.IFSCCode,rp.dateOfJoining,rp.Basic,rp.dearnessAllowance,rp.ConveyanceAllowance,rp.HRA,rp.MedicalAllowance,rp.AdvanceBonus,rp.SpecialAllowance,rp.CompanyBenefits,rp.GrossSalary,rp.absense,  rp.payDays,rp.BasicEarning,rp.ConveyanceAllowanceEarning,rp.HRAEarning,rp.MedicalAllowanceEarning,rp.AdvanceBonusEarning,rp.SpecialAllowanceEarning,rp.CompanyBenefitsEarning,rp.otherAllowanceEarning,rp.TotalEarning ,rp.Loan,rp.otherDeduction,rp.ProvidentFundEmployee,rp.ESI_Employee,"
			+ "rp.PT,rp.TDS,rp.TotalDeduction,rp.NetPayableAmount,rp.otherEarning,rp.transactionNo,hd.status FROM PayRegisterHd payregister \r\n" + 
			"			Left JOIN PayRegister pay ON pay.payRegisterHdId =payregister.payRegisterHdId\r\n" + 
			"			LEFT JOIN ReportPayOut rp ON rp.employeeId = pay.employeeId \r\n" + 
			"			LEFT JOIN Department dept ON dept.departmentId = pay.departmentId"+
			"  			LEFT JOIN HoldSalary hd on rp.employeeId=hd.employeeId and rp.processMonth=rp.processMonth"+
			" 			 JOIN Company comp ON rp.companyId= comp.companyId\r\n" + 
			"            where rp.processMonth=?2 AND  payregister.payRegisterHdId=?1\r\n" + 
			"			group by pay.employeeId";
	
	@Query(value=fetchPayrollRegisterListPaymentTransferSheet ,nativeQuery = true)
	public List<Object[]> fetchPayrollRegisterListPaymentTransfer(Long payRegisterHdId,String  payrollMonth);

//	String flag="UPDATE PayRegister pr INNER JOIN PayRegisterHd hd ON "
//			+ " hd.payRegisterHdId=pr.payRegisterHdId SET pr.payrollLockFlag =:payrollLockFlag WHERE pr.employeeId in:employeeId and hd.processMonth =:processMonth";
	 @Modifying
	 @Query(value="UPDATE PayRegister pr INNER JOIN PayRegisterHd hd ON  hd.payRegisterHdId=pr.payRegisterHdId SET pr.payrollLockFlag =:payrollLockFlag WHERE pr.employeeId in:employeeId and hd.processMonth =:processMonth", nativeQuery = true)
	 public int updatePayrollLockFlagInPayRegister(@Param("employeeId") List<Long> employeeId, @Param("processMonth") String processMonth, @Param("payrollLockFlag") boolean  payrollLockFlag );


	 String salarySheetByHD ="SELECT rp.employeeCode, rp.Name, des.designationName, dept.departmentName, rp.dateOfJoining, rp.gender,\r\n" + 
	 		"c.cityName as jobLocation,  rp.payDays, rp.absense, rp.payableDays, rp.GrossSalary, GROUP_CONCAT(DISTINCT p.payHeadId), GROUP_CONCAT(p.amount ORDER by p.payHeadId ASC),  \r\n" + 
	 		" rp.NetPayableAmount, rp.bankName, rp.accountNumber, rp.IFSCCode, b.bankBranch\r\n" + 
	 		" FROM ReportPayOut rp Left join PayOut p on p.employeeId =rp.employeeId and p.processMonth =:processMonth  \r\n" + 
	 		" LEFT JOIN Employee e ON e.employeeId = rp.employeeId LEFT JOIN City c ON c.cityId = e.cityId \r\n" + 
	 		" LEFT JOIN Designation des ON des.designationId = e.designationId LEFT JOIN EmployeeBank b on e.employeeId=b.employeeId \r\n" + 
	 		"LEFT JOIN PayHeads ph ON ph.payHeadId=p.payHeadId and ph.activeStatus='AC' \r\n" + 
	 		" LEFT JOIN Department dept ON dept.departmentId = rp.departmentId \r\n" + 
	 		" LEFT JOIN PayRegister pay on pay.employeeId= rp.employeeId\r\n" + 
	 		" LEFT JOIN PayRegisterHd pr on pr.payRegisterHdId= pay.payRegisterHdId\r\n" + 
	 		" where pay.payRegisterHdId=:hdId   and e.companyId=:companyId and rp.processMonth =:processMonth group by rp.employeeId";
		@Query(value = salarySheetByHD, nativeQuery = true)
		public List<Object[]> getSalarySheetByHd(@Param("companyId") Long companyId, @Param("hdId") Long hdId, @Param("processMonth") String processMonth);
		

}
                                                                                   