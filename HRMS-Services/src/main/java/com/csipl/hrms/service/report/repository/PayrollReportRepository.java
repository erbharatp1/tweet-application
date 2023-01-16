package com.csipl.hrms.service.report.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.csipl.hrms.model.payrollprocess.ReportPayOut;

public interface PayrollReportRepository extends CrudRepository<ReportPayOut, Long> {

	String findPTReport = "CALL  pro_emp_reportpt_bymonth( :p_comp_id,:p_from_process_month,:p_to_process_month,:p_emp_id,:p_state_id,:p_dept_id)";

	@Query(value = findPTReport, nativeQuery = true)
	public List<Object[]> findPTReport(@Param(value = "p_comp_id") Long p_comp_id,
			@Param(value = "p_from_process_month") String p_from_process_month,
			@Param(value = "p_to_process_month") String p_to_process_month, @Param(value = "p_emp_id") Long p_emp_id,
			@Param(value = "p_state_id") Long p_state_id, @Param(value = "p_dept_id") Long p_dept_id); // empCode,Long
																										// stateId

	String findProvisionReport = "SELECT rp.Name ,rp.employeeCode,rp.bankName,rp.accountNumber,rp.NetPayableAmount,pr.dateCreated,dept.departmentName \r\n"
			+ "    FROM ReportPayOut rp join Provision pr ON  rp.employeeId = pr.employeeId\r\n"
			+ "   join Department dept ON pr.departmentId = dept.departmentId\r\n"
			+ "    WHERE  pr.companyId =?1 AND rp.processMonth IN (select p.processMonth from Provision p where  pr.dateCreated BETWEEN ?2 and ?3 ) ";

	@Query(value = findProvisionReport, nativeQuery = true)
	List<Object[]> findProvisionReport(Long companyId, Date fromDate, Date toDate);// Date fromDate, Date toDate,

	String findProvisionReportByDept = "SELECT rp.Name ,rp.employeeCode,rp.bankName,rp.accountNumber,rp.NetPayableAmount,pr.dateCreated,dept.departmentName \r\n"
			+ "    FROM ReportPayOut rp join Provision pr ON  rp.employeeId = pr.employeeId\r\n"
			+ "   join Department dept ON pr.departmentId = dept.departmentId\r\n"
			+ "    WHERE  pr.departmentId =?4 AND pr.companyId =?1 AND rp.processMonth IN (select p.processMonth from Provision p where  pr.dateCreated BETWEEN ?2 and ?3 ) ";

	@Query(value = findProvisionReportByDept, nativeQuery = true)
	List<Object[]> findProvisionReportbydeptId(Long companyId, Date fromDate, Date toDate, Long departmentId); // empCode,Long
																												// stateId

	String findPayrollReportByMonth = "SELECT   rp .Name ,rp.employeeCode,rp.bankName,rp.accountNumber, rp.dateOfJoining,\r\n"
			+ "			rp.Basic ,rp.dearnessAllowance,rp.ConveyanceAllowance,rp.HRA,rp.MedicalAllowance,\r\n"
			+ "			rp.AdvanceBonus,rp.SpecialAllowance ,rp.CompanyBenefits ,rp.otherAllowance \r\n"
			+ "			,rp.GrossSalary ,rp.absense,rp.casualleave,rp.seekleave,rp.paidleave\r\n"
			+ "			,rp.presense ,rp.publicholidays ,rp.weekoff,rp.overtime ,rp.payDays ,rp.BasicEarning \r\n"
			+ "			,rp.dearnessAllowanceEarning,rp.ConveyanceAllowanceEarning ,rp.HRAEarning,rp.MedicalAllowanceEarning\r\n"
			+ "			,rp.AdvanceBonusEarning, rp.SpecialAllowanceEarning,rp.CompanyBenefitsEarning,rp.otherAllowanceEarning\r\n"
			+ "			,rp.TotalEarning,rp.EmployeeLoansAdvnaceEarning,rp.ProvidentFundEmployee,rp.ESI_Employee,\r\n"
			+ "			rp.PT,rp.TDS,rp.TotalDeduction,rp.NetPayableAmount\r\n" + "			,dept.departmentName\r\n"
			+ "				 FROM ReportPayOut  rp JOIN Department dept ON rp.departmentId= dept.departmentId \r\n"
			+ "			 WHERE rp.processMonth=?2 \r\n" + "			 AND rp.companyId=?1\r\n"
			+ "			ORDER BY  rp.dateOfJoining  ASC";

	@Query(value = findPayrollReportByMonth, nativeQuery = true)

	List<Object[]> findPayrollReportByMonth(Long companyId, String processMonth);

	String findPayrollReportByMonthBydept = "SELECT   rp .Name ,rp.employeeCode,rp.bankName,rp.accountNumber, rp.dateOfJoining,\r\n"
			+ "			rp.Basic ,rp.dearnessAllowance,rp.ConveyanceAllowance,rp.HRA,rp.MedicalAllowance,\r\n"
			+ "			rp.AdvanceBonus,rp.SpecialAllowance ,rp.CompanyBenefits ,rp.otherAllowance \r\n"
			+ "			,rp.GrossSalary ,rp.absense,rp.casualleave,rp.seekleave,rp.paidleave\r\n"
			+ "			,rp.presense ,rp.publicholidays ,rp.weekoff,rp.overtime ,rp.payDays ,rp.BasicEarning \r\n"
			+ "			,rp.dearnessAllowanceEarning,rp.ConveyanceAllowanceEarning ,rp.HRAEarning,rp.MedicalAllowanceEarning\r\n"
			+ "			,rp.AdvanceBonusEarning, rp.SpecialAllowanceEarning,rp.CompanyBenefitsEarning,rp.otherAllowanceEarning\r\n"
			+ "			,rp.TotalEarning,rp.EmployeeLoansAdvnaceEarning,rp.ProvidentFundEmployee,rp.ESI_Employee,\r\n"
			+ "			rp.PT,rp.TDS,rp.TotalDeduction,rp.NetPayableAmount\r\n" + "			,dept.departmentName\r\n"
			+ "				 FROM ReportPayOut  rp JOIN Department dept ON rp.departmentId= dept.departmentId  WHERE rp.processMonth=?3 AND rp.departmentId=?2 \r\n"
			+ "			 AND rp.companyId=?1\r\n" + "			ORDER BY  rp.dateOfJoining  ASC";

	@Query(value = findPayrollReportByMonthBydept, nativeQuery = true)

	List<Object[]> findPayrollReportByMonthBydept(Long companyId, Long departmentId, String processMonth);

	/*
	 * String findPayrollReportByEmpId =
	 * "SELECT  rp.processMonth , rp .Name ,rp.employeeCode,rp.bankName,rp.accountNumber,rp.dateOfJoining, \r\n"
	 * +
	 * "						rp.Basic ,rp.dearnessAllowance,rp.ConveyanceAllowance,rp.HRA,rp.MedicalAllowance, \r\n"
	 * +
	 * "				rp.AdvanceBonus,rp.SpecialAllowance ,rp.CompanyBenefits ,rp.otherAllowance \r\n"
	 * +
	 * "						,rp.GrossSalary ,rp.absense,rp.casualleave,rp.seekleave,rp.paidleave\r\n"
	 * +
	 * "					,rp.presense ,rp.publicholidays ,rp.weekoff,rp.overtime ,rp.payDays ,rp.BasicEarning \r\n"
	 * +
	 * "					,rp.dearnessAllowanceEarning,rp.ConveyanceAllowanceEarning ,rp.HRAEarning,rp.MedicalAllowanceEarning\r\n"
	 * +
	 * "				,rp.AdvanceBonusEarning, rp.SpecialAllowanceEarning,rp.CompanyBenefitsEarning,rp.otherAllowanceEarning\r\n"
	 * +
	 * "						,rp.TotalEarning,rp.EmployeeLoansAdvnaceEarning,rp.ProvidentFundEmployee,rp.ESI_Employee, \r\n"
	 * + "			rp.PT,rp.TDS,rp.TotalDeduction,rp.NetPayableAmount\r\n" +
	 * "					 FROM ReportPayOut  rp WHERE ( month(str_to_date(SUBSTRING_INDEX(rp.processMonth,'-',1),'%b')) BETWEEN month(str_to_date(SUBSTRING_INDEX(?3,'-',1),'%b'))  and month(str_to_date(SUBSTRING_INDEX(?4,'-',1),'%b'))) AND rp.employeeId=?2 \r\n"
	 * + "					 AND rp.companyId=?1\r\n" +
	 * "						ORDER BY  rp.dateOfJoining  ASC    pro_emp_reportpt_bymonth"
	 * ;
	 */
	/*
	 * @Query(value = findPayrollReportByEmpId, nativeQuery = true)
	 * 
	 * List<Object[]> findPayrollReportByEmpId(Long companyId, Long employeeId,
	 * String processMonth, String toProcessMonth);
	 */
	@Query(value = findPayRollBankTRF, nativeQuery = true)
	List<Object[]> findPayRollBankTRF(Long companyId, String processMonth, String bankName);

	@Query("SELECT COUNT(1) FROM PayRollLock WHERE isPayRollLocked=?1 and departmentId=?2 and processMonth=?3")
	public Long checkForRecoReprotAvailability(String checkReco, Long longDeptId, String processMonth);

	String findPayRollBankTRF = "SELECT rp.employeeCode,rp.name,rp.bankName,rp.accountNumber,rp.NetPayableAmount,rp.processMonth,dept.departmentName FROM ReportPayOut rp JOIN Department dept ON dept.departmentId = rp.departmentId WHERE rp.companyId=?1 and rp.processMonth =?2 AND rp.bankName =?3";

	String findReconciliationReportWithDept = "SELECT DISTINCT rp.employeeCode,rp.name,rp.bankName,rp.accountNumber,rp.NetPayableAmount,rp.processMonth,dept.departmentName,rp.transactionNo,rp.dateUpdate \r\n"
			+ "FROM ReportPayOut rp LEFT JOIN Department dept ON dept.departmentId = rp.departmentId LEFT JOIN PayRollLock pl ON pl.processMonth = rp.processMonth\r\n"
			+ "WHERE rp.companyId =?1 AND rp.departmentId =?2 AND rp.processMonth =?3 and pl.isPayRollLocked=?4 AND rp.employeeId not in (select pr.employeeId from Provision pr where pr.processMonth=?3 AND pr.activeStatus=?5 )";

	String findReconciliationReportWithoutDept = "SELECT DISTINCT rp.employeeCode,rp.name,rp.bankName,rp.accountNumber,rp.NetPayableAmount,rp.processMonth,dept.departmentName,rp.transactionNo,rp.dateUpdate \r\n"
			+ "FROM ReportPayOut rp LEFT JOIN Department dept ON dept.departmentId = rp.departmentId LEFT JOIN PayRollLock pl ON pl.processMonth = rp.processMonth\r\n"
			+ "WHERE rp.companyId =?1 AND  rp.processMonth =?2 and pl.isPayRollLocked=?3 AND rp.employeeId not in (select pr.employeeId from Provision pr where pr.processMonth=?2 AND pr.activeStatus=?4 )";

	String findNonReconciliationReportWithDept = "SELECT DISTINCT rp.employeeCode,rp.name,rp.bankName,rp.accountNumber,rp.NetPayableAmount,rp.processMonth,dept.departmentName,rp.transactionNo,rp.dateUpdate \r\n"
			+ "FROM ReportPayOut rp LEFT JOIN Department dept ON dept.departmentId = rp.departmentId LEFT JOIN PayRollLock pl ON pl.processMonth = rp.processMonth\r\n"
			+ "WHERE rp.companyId =?1 AND rp.departmentId =?2 AND rp.processMonth =?3 and pl.isPayRollLocked=?4 AND rp.employeeId  in (select pr.employeeId from Provision pr where pr.processMonth=?3 AND pr.activeStatus=?5 )";

	String findNonReconciliationReportWithoutDept = "SELECT DISTINCT rp.employeeCode,rp.name,rp.bankName,rp.accountNumber,rp.NetPayableAmount,rp.processMonth,dept.departmentName,rp.transactionNo,rp.dateUpdate \r\n"
			+ "FROM ReportPayOut rp LEFT JOIN Department dept ON dept.departmentId = rp.departmentId LEFT JOIN PayRollLock pl ON pl.processMonth = rp.processMonth\r\n"
			+ "WHERE rp.companyId =?1 AND  rp.processMonth =?2 and pl.isPayRollLocked=?3 AND rp.employeeId  in (select pr.employeeId from Provision pr where pr.processMonth=?2 AND pr.activeStatus=?4 )";

	String findPayrollReportByEmpId = "CALL  pro_payrollreport_byempid( :p_comp_id,:p_emp_id,:p_from_process_month,:p_to_process_month)";

	@Query(value = findPayrollReportByEmpId, nativeQuery = true)
	public List<Object[]> findPayrollReportByEmpId(@Param(value = "p_comp_id") Long p_comp_id,
			@Param(value = "p_emp_id") Long p_emp_id,
			@Param(value = "p_from_process_month") String p_from_process_month,
			@Param(value = "p_to_process_month") String p_to_process_month);

	@Query(value = findReconciliationReportWithDept, nativeQuery = true)
	List<Object[]> findReconciliationReportWithDept(Long companyId, Long longDeptId, String processMonth,
			String checkReco, String status);

	@Query(value = findReconciliationReportWithoutDept, nativeQuery = true)
	public List<Object[]> findReconciliationReportWithoutDept(Long companyId, String processMonth, String checkReco,
			String status);

	@Query(value = findNonReconciliationReportWithDept, nativeQuery = true)
	List<Object[]> findNonReconciliationReportWithDept(Long companyId, Long longDeptId, String processMonth,
			String checkReco, String status);

	@Query(value = findNonReconciliationReportWithoutDept, nativeQuery = true)
	public List<Object[]> findNonReconciliationReportWithoutDept(Long companyId, String processMonth, String checkReco,
			String status);

	String findEpfEcrReport = "SELECT rp.UANNO ,rp.Name, rp.TotalEarning+rp.otherEarning, rp.EarnedStdEpfWagesAmount,rp.PensionEarningSalary ,rp.absense,"
			+ "rp.ProvidentFundEmployee,rp.ProvidentFundEmployer ,rp.ProvidentFundEmployerPension, rp.dearnessAllowanceEarning "
			+ " FROM ReportPayOut rp WHERE rp.processMonth=?2  AND rp.companyId=?1 AND ( rp.isNoPFDeduction='N' OR rp.isNoPFDeduction IS Null)";

	@Query(value = findEpfEcrReport, nativeQuery = true)
	List<Object[]> findEpfEcrReport(Long companyId, String processMonth);

	// String findEpfReport ="SELECT COUNT(rp.Name),SUM(rp.TotalEarning) FROM
	// ReportPayOut rp WHERE rp.processMonth=?2 AND rp.isNoPFDeduction='Y' AND
	// rp.companyId=?1";

	String findEpfReport = "SELECT COUNT(rp.employeeId)as totalEmployee , COUNT(r.employeeId)as excludedemployee ,"
			+ " SUM(r.TotalEarning)as grossEmployeeSalary from ReportPayOut rp  LEFT JOIN ReportPayOut r ON rp.employeeId=r.employeeId "
			+ " and  r.isNoPFDeduction='Y'  and r.processMonth=?1 "
			+ "			  Where rp.companyId=?2 and rp.processMonth=?1";

	@Query(value = findEpfReport, nativeQuery = true)

//
//	List<Object[]> findEpfReport(Long companyId, String processMonth);
//
//	String findEsicEcrReport = "SELECT rp.ESICNumber, rp.Name,rp.payDays,rp.TotalEarning FROM ReportPayOut rp  JOIN Esi esi on esi.companyId=rp.companyId WHERE (rp.GrossSalary<esi.maxGrossLimit OR rp.isNoESIDeduction='N') AND rp.companyId=?1 AND rp.processMonth=?2 GROUP by rp.employeeId";
//
//	@Query(value = findEsicEcrReport, nativeQuery = true)

	List<Object[]> findEpfReport(Long companyId, String processMonth);

// 	String findEsicEcrReport ="SELECT rp.ESICNumber, rp.Name,rp.payableDays,rp.TotalEarning FROM ReportPayOut rp  JOIN Esi esi on esi.companyId=rp.companyId WHERE (rp.GrossSalary<esi.maxGrossLimit OR rp.isNoESIDeduction='N') AND rp.companyId=?1 AND rp.processMonth=?2 GROUP by rp.employeeId";

	String findEsicEcrReport = "SELECT rp.ESICNumber, rp.Name,rp.payableDays,rp.TotalEarning FROM ReportPayOut rp  JOIN Esi esi on esi.companyId=rp.companyId WHERE (rp.GrossSalary<esi.maxGrossLimit OR rp.isNoESIDeduction='N') AND rp.ESI_Employee !=0 and rp.payableDays>0 AND rp.companyId=?1 AND rp.processMonth=?2 GROUP by rp.employeeId";

	@Query(value = findEsicEcrReport, nativeQuery = true)
	List<Object[]> findEsicEcrReport(Long companyId, String processMonth);

	// String findEsicReport ="SELECT COUNT(rp.employeeCode),sum(rp.TotalEarning)
	// FROM ReportPayOut rp JOIN Esi esi on esi.companyId=rp.companyId WHERE
	// rp.GrossSalary>esi.maxGrossLimit AND rp.companyId=?1 AND rp.processMonth=?2
	// AND rp.isNoESIDeduction='Y' ";
//	String findEsicReport = "SELECT COUNT(rp.employeeId)as totalEmployee , SUM(r.TotalEarning)as grossEmployeeSalary "
//			+ " from ReportPayOut rp  LEFT JOIN ReportPayOut r ON rp.employeeId=r.employeeId  and  r.isNoESIDeduction='Y'  and r.processMonth=?2 "
//			+ "		  Where rp.companyId=?1 and rp.processMonth=?2 ";

	String findEsicReport = "SELECT COUNT(rp.employeeId)as totalEmployee , SUM(r.TotalEarning)as grossEmployeeSalary  \r\n"
			+ "			 from ReportPayOut rp  LEFT JOIN ReportPayOut r ON rp.employeeId=r.employeeId  and  ((r.isNoESIDeduction='Y' OR rp.ESI_Employee =0) )  "
			+ " and r.processMonth=?2  \r\n" + "		 	  Where rp.companyId=?1 and rp.processMonth=?2";

	@Query(value = findEsicReport, nativeQuery = true)

	List<Object[]> findEsicReport(Long companyId, String processMonth);

	String total = "SELECT SUM(rp.TotalEarning+rp.otherEarning), SUM(rp.EarnedStdEpfWagesAmount), SUM(rp.PensionEarningSalary), SUM(rp.ProvidentFundEmployee), SUM(rp.ProvidentFundEmployer),"
			+ " SUM(rp.ProvidentFundEmployerPension)  FROM ReportPayOut rp WHERE rp.processMonth=?2  AND rp.companyId=?1 AND "
			+ " ( rp.isNoPFDeduction='N' OR rp.isNoPFDeduction IS Null)";

	@Query(value = total, nativeQuery = true)
	List<Object[]> getTotal(Long companyId, String processMonth);

	String EarningDeduction = "SELECT e.employeeCode, CONCAT(e.firstName, ' ', e.lastName) as employeeName, des.designationName, dp.departmentName, ph.payHeadName,"
			+ " ot.amount, ot.remarks FROM OneTimeEarningDeduction ot LEFT JOIN Employee e  ON ot.employeeId=e.employeeId "
			+ "LEFT JOIN Department dp ON dp.departmentId=e.departmentId  LEFT JOIN Designation des ON des.designationId= e.designationId "
			+ "LEFT JOIN PayHeads ph ON ph.payHeadId=ot.payHeadId WHERE ot.companyId=:companyId and e.departmentId in:departmentId "
			+ " and ot.earningDeductionMonth=:processMonth and ot.type=:type";

	@Query(value = EarningDeduction, nativeQuery = true)
	List<Object[]> findEarningDeductionForEmployee(@Param("companyId") Long companyId,
			@Param("departmentId") List<Long> departmentId, @Param("processMonth") String processMonth,
			@Param("type") String type);

	// String salarySheet = "SELECT rp.employeeCode, rp.Name, des.designationName,
	// dept.departmentName, rp.dateOfJoining, rp.gender, "
	// + "c.cityName as jobLocation, rp.payableDays, rp.absense, rp.payDays,
	// rp.GrossSalary, GROUP_CONCAT(DISTINCT p.payHeadId), GROUP_CONCAT(p.amount), "
	// + "rp.NetPayableAmount, rp.bankName, rp.accountNumber, rp.IFSCCode,
	// b.bankBranch "
	//
	// + "FROM ReportPayOut rp "
	// + " LEFT JOIN Employee e ON e.employeeId = rp.employeeId "
	// + " Left join PayOut p on p.employeeId =e.employeeId and p.processMonth
	// =:processMonth"
	//// + " -- Left join LoanEMI le on le.emiNo=p. "
	// + " LEFT JOIN City c ON c.cityId = e.cityId LEFT JOIN Designation des ON
	// des.designationId = e.designationId "
	// + " LEFT JOIN EmployeeBank b on e.employeeId=b.employeeId "
	// + " LEFT JOIN Department dept ON dept.departmentId = e.departmentId WHERE
	// e.companyId=:companyId "
	// + "and e.departmentId in:departmentId and rp.processMonth=:processMonth ";

	String salarySheet = "SELECT rp.employeeCode, rp.Name, des.designationName, dept.departmentName, rp.dateOfJoining, rp.gender, "
			+ "c.cityName as jobLocation, rp.payDays, rp.absense, rp.payableDays, rp.GrossSalary, GROUP_CONCAT(DISTINCT p.payHeadId), GROUP_CONCAT(p.amount ORDER BY p.payHeadId ASC), "
			+ " rp.NetPayableAmount, rp.bankName, rp.accountNumber, rp.IFSCCode, b.bankBranch "
			+ " FROM ReportPayOut rp join PayOut p on p.employeeId =rp.employeeId and p.processMonth =:processMonth "
			+ " LEFT JOIN Employee e ON e.employeeId = rp.employeeId LEFT JOIN City c ON c.cityId = e.cityId "
			+ " LEFT JOIN Designation des ON des.designationId = e.designationId LEFT JOIN EmployeeBank b on e.employeeId=b.employeeId "
			+ " LEFT JOIN PayHeads ph ON ph.payHeadId=p.payHeadId and ph.activeStatus='AC'"
			+ " LEFT JOIN Department dept ON dept.departmentId = rp.departmentId WHERE e.companyId=:companyId and rp.departmentId in:departmentId and "
			+ " rp.processMonth =:processMonth group by rp.employeeId ORDER by ph.sequenceId ASC ";

	@Query(value = salarySheet, nativeQuery = true)
	public List<Object[]> getSalarySheetData(@Param("companyId") Long companyId,
			@Param("departmentId") List<Long> departmentId, @Param("processMonth") String processMonth);

	// String payHeads ="SELECT concat( ph.payHeadName,'/',ph.payHeadId) FROM
	// PayHeads ph WHERE ph.companyId=?1 and
	// ph.activeStatus='"+StatusMessage.ACTIVE_CODE+"' ";
	// String earningPayHeads ="SELECT concat( ph.payHeadName,'/',ph.payHeadId) FROM
	// PayHeads ph WHERE ph.companyId=?1 and ph.payHeadName not like "
	// + "'%Employer%' and ph.payHeadName not like '%LWF%' and
	// ph.earningDeduction='EA' order by ph.sequenceId ASC";

	String earningPayHeads = "SELECT concat( ph.payHeadName,'/',ph.payHeadId) FROM PayHeads ph WHERE ph.companyId=?1 and ph.payHeadName not like "
			+ "'%Employer%'  and ph.earningDeduction='EA' order by ph.sequenceId ASC";

	@Query(value = earningPayHeads, nativeQuery = true)
	public String[] getEarnngPayHeads(Long longcompanyId);

	// String deductionPayHeads ="SELECT concat( ph.payHeadName,'/',ph.payHeadId)
	// FROM PayHeads ph WHERE ph.companyId=?1 and ph.payHeadName not like "
	// + " '%Employer%' and ph.payHeadName not like '%LWF%' and
	// ph.earningDeduction='DE' order by ph.sequenceId ASC";

	String deductionPayHeads = "SELECT concat( ph.payHeadName,'/',ph.payHeadId) FROM PayHeads ph WHERE ph.companyId=?1 and ph.payHeadName not like "
			+ " '%Employer%' and ph.payHeadId!=103   and ph.earningDeduction='DE' order by ph.sequenceId ASC";

	@Query(value = deductionPayHeads, nativeQuery = true)
	public String[] getDeductionPayHeads(Long longcompanyId);

	String ProfessionalTaxStatementMonthlyEmployeeWise = " SELECT rp.employeeCode, rp.Name, c.cityName AS jobLocation, s.stateName, rp.TotalEarning, "
			+ " rp.PT FROM ReportPayOut rp LEFT JOIN Employee e ON e.employeeId = rp.employeeId LEFT JOIN City c ON c.cityId = e.cityId LEFT JOIN State s "
			+ " ON c.stateId = s.stateId WHERE e.companyId=?1 and  rp.processMonth =?2 and rp.PT is not null and rp.PT != 0  GROUP BY rp.employeeId ORDER BY s.stateId ASC ";

	@Query(value = ProfessionalTaxStatementMonthlyEmployeeWise, nativeQuery = true)
	public List<Object[]> findProfessionalTaxStatementMonthlyEmployeeWise(Long longcompanyId, String processMonth);

	String ProfessionalTaxStatementMonthlyStateWise = " SELECT s.stateName, Count( rp.employeeCode) as totalEmployee, Sum(rp.TotalEarning), Sum( rp.PT) FROM ReportPayOut rp "
			+ " LEFT JOIN Employee e ON e.employeeId = rp.employeeId LEFT JOIN City c ON c.cityId = e.cityId LEFT JOIN State s "
			+ " ON c.stateId = s.stateId WHERE rp.processMonth =?2 and e.companyId=?1 and rp.PT is not null and rp.PT != 0  GROUP BY s.stateId ORDER BY s.stateId ASC";

	@Query(value = ProfessionalTaxStatementMonthlyStateWise, nativeQuery = true)
	public List<Object[]> findProfessionalTaxStatementMonthlyStateWise(Long longcompanyId, String processMonth);

	String ArrearMonthlyReport = "SELECT e.employeeCode, e.firstName, des.designationName, dept.departmentName,"
			+ " e.dateOfJoining, e.gender, c.cityName as jobLocation,  SUM(ap.amount) FROM ArrearPayOut ap "
			+ "      JOIN Employee e ON e.employeeId = ap.employeeId LEFT JOIN City c ON c.cityId = e.cityId "
			+ "     LEFT JOIN Designation des ON des.designationId = e.designationId "
			+ "     LEFT JOIN Department dept ON dept.departmentId = e.departmentId WHERE e.companyId=?1 AND "
			+ "  dept.departmentId in ?2 AND ap.processMonth=?3 Group BY ap.processMonth ";

	@Query(value = ArrearMonthlyReport, nativeQuery = true)
	public List<Object[]> getArrearMonthlyReport(Long longcompanyId, List<Long> departmentDTOList, String processMonth);

	String ArrearAnnualyReport = "SELECT e.employeeCode, e.firstName, des.designationName, dept.departmentName, e.dateOfJoining,"
			+ "  e.gender, c.cityName as jobLocation, SUM(ap.amount), ap.processMonth "
			+ "FROM ArrearPayOut ap Left join PayrollControl pc on pc.financialYearId =?2 AND ap.processMonth=pc.processMonth \r\n"
			+ "      JOIN Employee e ON e.employeeId = ap.employeeId LEFT JOIN City c ON c.cityId = e.cityId \r\n"
			+ "     LEFT JOIN Designation des ON des.designationId = e.designationId \r\n"
			+ " LEFT JOIN Department dept ON dept.departmentId = e.departmentId WHERE e.companyId=?1 And pc.financialYearId=?2 Group BY ap.processMonth";

	@Query(value = ArrearAnnualyReport, nativeQuery = true)
	public List<Object[]> getArrearAnnualyReport(Long longcompanyId, Long financialYrId);

	// String PfExitReport="SELECT rp.employeeCode,rp.UANNO,
	// rp.Name,rp.epfJoining,sp.exitDate FROM ReportPayOut rp "
	// + " JOIN Separation sp On sp.employeeId=rp.employeeId WHERE rp.companyId=?1
	// AND rp.processMonth=?2 AND rp.isNoPFDeduction='N' "
	// + " AND sp.exitDate IS NOT NULL AND sp.status='APR' ";

	// String PfExitReport = "SELECT rp.employeeCode,es.statuaryNumber,
	// rp.Name,es.dateFrom, es.dateTo FROM ReportPayOut rp \r\n" +
	// " JOIN EmployeeStatuary es On es.employeeId=rp.employeeId WHERE
	// rp.companyId=?1 and es.statuaryType='UA' AND rp.isNoPFDeduction='N' \r\n" +
	// " AND es.dateTo IS NOT NULL AND rp.processMonth=?2 AND es.status='AC' ";

	String PfExitReport = " SELECT e.employeeCode, es.statuaryNumber, Concat(e.firstName, ' ', e.lastName), es.dateFrom, es.dateTo FROM Employee e \r\n"
			+ "  JOIN EmployeeStatuary es On es.employeeId=e.employeeId   WHERE e.companyId=?1 and  es.statuaryType='UA' AND es.isApplicable='N'   \r\n"
			+ "	AND es.dateTo IS NOT NULL  AND es.dateTo>=?2 AND es.dateTo<=?3 AND es.status='AC' GROUP BY es.employeeId ";

	@Query(value = PfExitReport, nativeQuery = true)
	public List<Object[]> getPFExitStatementReport(Long longcompanyId, Date fromDate, Date toDate);

	// String ESIExitReport = "SELECT rp.employeeCode, rp.ESICNumber, rp.Name,
	// rp.esicjoining, sp.exitDate FROM ReportPayOut rp "
	// + " JOIN Separation sp On sp.employeeId=rp.employeeId WHERE rp.companyId=?1
	// AND rp.processMonth=?2 AND rp.isNoESIDeduction='N' "
	// + " AND sp.exitDate IS NOT NULL AND sp.status='APR' ";

	String ESIExitReport = "SELECT e.employeeCode, es.statuaryNumber, Concat(e.firstName, ' ', e.lastName), es.dateFrom, es.dateTo FROM Employee e \r\n"
			+ "	JOIN EmployeeStatuary es On es.employeeId=e.employeeId WHERE e.companyId=?1 and  es.statuaryType='ES' AND es.isApplicable='N'   \r\n"
			+ "	AND es.dateTo IS NOT NULL AND es.dateTo>=?2 AND es.dateTo<=?3 AND es.status='AC' GROUP BY es.employeeId ";

	@Query(value = ESIExitReport, nativeQuery = true)
	public List<Object[]> getESIExitStatementReport(Long longcompanyId, Date fromDate, Date toDate);

	String PaymentRecordReport = "SELECT  rp.employeeCode, rp.Name, rp.bankName, rp.accountNumber, rp.IFSCCode, rp.NetPayableAmount,  \r\n"
			+ "  rp.transactionNo, rp.transactionMode, rp.processDate  FROM ReportPayOut rp LEFT JOIN Employee e ON e.employeeId=rp.employeeId \r\n"
			+ "  WHERE rp.processMonth=?2 AND rp.companyId=?1  AND e.departmentId in ?3	 ";

	@Query(value = PaymentRecordReport, nativeQuery = true)
	public List<Object[]> getPaymentRecordStatementReport(Long longcompanyId, String processMonth,
			List<Long> departmentDTOList);

	String EsiArrearEcrReport = "SELECT ar.ESICNumber, ar.Name, ar.payDays, ar.NetPayableAmount "
			+ " FROM ArrearReportPayOut ar WHERE ar.companyId=?1 AND ar.processMonth=?2 ";

	@Query(value = EsiArrearEcrReport, nativeQuery = true)
	public List<Object[]> getESIArrearECRReport(Long longcompanyId, String processMonth);

	String EsicExcludedEmployeeReport = "SELECT COUNT(ap.employeeId)as totalEmployee, COUNT(r.employeeId)as excludedemployee, "
			+ " SUM(r.GrossSalary)as grossEmployeeSalary\r\n"
			+ " FROM ArrearReportPayOut ap  LEFT JOIN ArrearReportPayOut r ON ap.employeeId=r.employeeId And r.isNoESIDeduction='Y' \r\n"
			+ " WHERE ap.companyId=?1 AND ap.processMonth=?2 ";

	@Query(value = EsicExcludedEmployeeReport, nativeQuery = true)
	public List<Object[]> getEsicExcludedEmployee(Long longcompanyId, String processMonth);

	String PayHeads = "SELECT concat(ph.payHeadName,'/',ph.payHeadId)  from PayStructure ps JOIN PayHeads ph ON ph.payHeadId=ps.payHeadId  WHERE ph.companyId=?1 GROUP BY ph.payHeadId";

	@Query(value = PayHeads, nativeQuery = true)
	public String[] getcurrentPayHeads(Long longcompanyId);

	String CurrentSalarySheetReport = "SELECT e.employeeCode, e.firstName, des.designationName, dp.departmentName, g.gradesName, \r\n"
			+ " e.empType, e.timeContract, ps.effectiveDate, ps.dateUpdate, ps.grossPay, GROUP_CONCAT(DISTINCT pd.payHeadId)as payHeadId, "
			+ " GROUP_CONCAT(pd.amount), ps.epfEmployee, ps.epfEmployer, ps.esiEmployee, ps.esiEmployer, ps.professionalTax, \r\n"
			+ " ps.netPay, pd.amount From PayStructureHd ps LEFT JOIN Employee e On ps.employeeId=e.employeeId \r\n"
			+ " LEFT JOIN Department dp ON dp.departmentId=e.departmentId  LEFT JOIN Designation des \r\n"
			+ " ON des.designationId= e.designationId  LEFT JOIN Grades g On g.gradesId=ps.gradesId  LEFT JOIN PayStructure pd On pd.payHeadId\r\n"
			+ " WHERE e.companyId=?1 and e.employeeId=2030 ";

	@Query(value = CurrentSalarySheetReport, nativeQuery = true)
	public List<Object[]> getCurrentSalarySheetReport(Long longcompanyId, Long longemployeeId, List<Long> ids);

	String PayStructureEmployeeWise = "SELECT e.employeeCode,  CONCAT(e.firstName, ' ', e.lastName) as employeeName, "
			+ " des.designationName, dp.departmentName, g.gradesName, \r\n"
			+ " e.empType, e.activeStatus, ph.effectiveDate,  ph.dateUpdate, ph.grossPay, GROUP_CONCAT(DISTINCT ps.payHeadId)as payHeadId, "
			+ " GROUP_CONCAT(ps.amount  ORDER by ps.payHeadId),\r\n"
			+ " ph.epfEmployee, ph.epfEmployer, ph.esiEmployee, ph.esiEmployer, ph.lwfEmployeeAmount, ph.lwfEmployerAmount,    ph.professionalTax, \r\n"
			+ " ph.netPay,  ph.epfEmployeePension   From PayStructureHd ph LEFT JOIN Employee e On ph.employeeId=e.employeeId \r\n"
			+ " LEFT JOIN Department dp ON dp.departmentId=e.departmentId  LEFT JOIN Designation des \r\n"
			+ " ON des.designationId= e.designationId  LEFT JOIN Grades g On g.gradesId=ph.gradesId  \r\n"
			+ " LEFT JOIN PayStructure ps On ps.payStructureHdId=ph.payStructureHdId\r\n"
			+ " WHERE e.companyId=?1 and ph.employeeId=?2 and (ph.dateEnd is null or ph.dateEnd>=?3) \r\n"
			+ " and (ph.effectiveDate is NOT null and ph.effectiveDate<=?3) ";

	@Query(value = PayStructureEmployeeWise, nativeQuery = true)
	public List<Object[]> findPayStructureEmployeeWise(Long longcompanyId, Long longemployeeId, Date currentDate);

	String PayStructureDepartmentWise = "SELECT e.employeeCode, CONCAT(e.firstName, ' ', e.lastName) as employeeName,"
			+ " des.designationName, dp.departmentName, g.gradesName, \r\n"
			+ " e.empType, e.activeStatus, ph.effectiveDate,  ph.dateUpdate, ph.grossPay, GROUP_CONCAT(DISTINCT ps.payHeadId)as payHeadId, "
			+ " GROUP_CONCAT(ps.amount  ORDER by ps.payHeadId), \r\n"
			+ " ph.epfEmployee, ph.epfEmployer, ph.esiEmployee, ph.esiEmployer,ph.lwfEmployeeAmount, ph.lwfEmployerAmount,  ph.professionalTax, \r\n"
			+ " ph.netPay,  ph.epfEmployeePension From PayStructureHd ph LEFT JOIN Employee e On ph.employeeId=e.employeeId \r\n"
			+ " LEFT JOIN Department dp ON dp.departmentId=e.departmentId  LEFT JOIN Designation des \r\n"
			+ " ON des.designationId= e.designationId  LEFT JOIN Grades g On g.gradesId=ph.gradesId  \r\n"
			+ " LEFT JOIN PayStructure ps On ps.payStructureHdId=ph.payStructureHdId \r\n"
			+ " WHERE e.companyId=?1  and dp.departmentId in ?2 and (ph.dateEnd is null or ph.dateEnd>=?3)  \r\n"
			+ " and (ph.effectiveDate is NOT null and ph.effectiveDate<=?3) and e.activeStatus=?4  group by ph.employeeId  ";

	@Query(value = PayStructureDepartmentWise, nativeQuery = true)
	public List<Object[]> findPayStructureDepartmentWise(Long longcompanyId, List<Long> ids, Date currentDate,
			String status);

	String annualSummary = "SELECT rp.processMonth,SUM(rp.TotalEarning), sum(rp.BasicEarning), SUM(rp.TotalEarning)- sum(rp.BasicEarning) as other,"
			+ " sum(rp.NetPayableAmount), sum(rp.otherEarning), (SELECT SUM(p.amount)  FROM PayOut p WHERE p.processMonth=rp.processMonth AND p.payHeadId = ?3) as PF,"
			+ " SUM(rp.ProvidentFundEmployer), "
			+ " (SELECT   SUM(p.amount)  FROM PayOut p WHERE p.processMonth=rp.processMonth AND p.payHeadId = ?4 ) as ESIC ,"
			+ " SUM(rp.ESI_Employer), (SELECT   SUM(p.amount)  FROM PayOut p WHERE p.processMonth=rp.processMonth AND p.payHeadId = ?5) as PT,"
			+ " (SELECT   SUM(p.amount)  FROM PayOut p WHERE p.processMonth=rp.processMonth AND p.payHeadId = ?6) as TDS,"
			+ "  (SELECT   SUM(p.amount)  FROM PayOut p WHERE p.processMonth=rp.processMonth AND p.payHeadId = ?7) as loan ,"
			+ " SUM(rp.TotalDeduction), SUM(rp.ProvidentFundEmployerPension), SUM(rp.LWF), SUM(rp.employerWelFareAmount), "
			+ " SUM(IFNULL(rp.employerWelFareAmount,0)+ IFNULL(rp.otherEarning,0) + IFNULL(rp.BasicEarning, 0)+ IFNULL(rp.dearnessAllowanceEarning, 0)+"
			+ " IFNULL(rp.ConveyanceAllowanceEarning, 0)+IFNULL(rp.HRAEarning, 0)+IFNULL(rp.MedicalAllowanceEarning, 0)+IFNULL(rp.AdvanceBonusEarning, 0)+"
			+ " IFNULL(rp.SpecialAllowanceEarning,0)+IFNULL(rp.leaveTravelAllowanceEarning,0)+IFNULL(rp.performanceLinkedIncomeEarning,0)+"
			+ " IFNULL(rp.uniformAllowanceEarning,0)+IFNULL(rp.CompanyBenefitsEarning,0)+IFNULL(rp.ProvidentFundEmployer,0)+IFNULL(rp.ProvidentFundEmployerPension,0)+"
			+ " IFNULL(rp.ESI_Employer,0)) as CTC " + " from ReportPayOut rp LEFT JOIN PayrollControl pc ON "
			+ " pc.processMonth=rp.processMonth WHERE pc.financialYearId=?2  and rp.companyId=?1  Group by rp.processMonth  ORDER BY pc.controlId ";

	@Query(value = annualSummary, nativeQuery = true)
	public List<Object[]> getAnnualSummary(Long companyId, Long financialYearId, Long pfPayHeadId, Long esicPayHeadId,
			Long ptPayHeadId, Long tdsPayHeadId, Long loanPayHeadId);

	String salarySheetAnnual = "SELECT rp.employeeCode, rp.Name, des.designationName, dept.departmentName, rp.dateOfJoining, rp.gender,  \r\n"
			+ " c.cityName as jobLocation, rp.payDays, rp.absense, rp.payableDays, rp.GrossSalary, GROUP_CONCAT(DISTINCT p.payHeadId), GROUP_CONCAT(p.amount  ORDER BY p.payHeadId ASC), \r\n"
			+ " rp.NetPayableAmount, rp.bankName, rp.accountNumber, rp.IFSCCode, b.bankBranch,  \r\n"
			+ " rp.processMonth FROM ReportPayOut rp join PayOut p on p.employeeId =rp.employeeId and p.processMonth=rp.processMonth  \r\n"
			+ " LEFT JOIN PayrollControl pc ON    pc.processMonth=rp.processMonth  LEFT JOIN Employee e ON e.employeeId = rp.employeeId LEFT JOIN City c ON c.cityId = e.cityId  \r\n"
			+ " LEFT JOIN Designation des ON des.designationId = e.designationId LEFT JOIN EmployeeBank b on e.employeeId=b.employeeId  \r\n"
			+ " LEFT JOIN PayHeads ph ON ph.payHeadId=p.payHeadId and ph.activeStatus='AC'  \r\n"
			+ " LEFT JOIN Department dept ON dept.departmentId = rp.departmentId WHERE e.companyId=?1   and pc.financialYearId=?2 GROUP BY e.employeeId , rp.processMonth ORDER BY pc.controlId ";

	@Query(value = salarySheetAnnual, nativeQuery = true)
	public List<Object[]> getSalarySheetAnnual(Long companyId, Long financialYearId);

//	String currentCostToCompanyEmployeeWise = "SELECT  rp.employeeCode, rp.Name, des.designationName, dept.departmentName ,g.gradesName ,\r\n"
//			+ "		 IF(e.empType='PE',  \"Permanant\", \"Contract\"), IF(e.activeStatus='AC', \"Working\", \"Former\" ),ps.effectiveDate, ps.dateUpdate,ps.ctc, \r\n"
//			+ "				rp.GrossSalary, GROUP_CONCAT(DISTINCT p.payHeadId), GROUP_CONCAT(DISTINCT p.amount ORDER by  p.payHeadId ASC), rp.NetPayableAmount \r\n"
//			+ "                \r\n" + "   FROM  PayOut p \r\n"
//			+ "           LEFT JOIN PayHeads ph ON ph.payHeadId=p.payHeadId and ph.activeStatus='AC' \r\n"
//			+ "           LEFT JOIN Employee e ON e.employeeId = p.employeeId  \r\n"
//			+ "           LEFT JOIN ReportPayOut rp on rp.employeeId=p.employeeId\r\n"
//			+ "           LEFT JOIN Department dept ON dept.departmentId = rp.departmentId\r\n"
//			+ "           LEFT JOIN Designation des ON des.designationId = e.designationId  \r\n"
//			+ "           LEFT JOIN Grades g ON g.gradesId=e.gradesId \r\n"
//			+ "           LEFT  JOIN PayStructureHd ps ON ps.employeeId=rp.employeeId \r\n"
//			+ "            WHERE  e.companyId= ?1  AND  p.employeeId=?2 and ( ps.dateEnd is null or ps.dateEnd>=?3) and (ps.effectiveDate is NOT null and ps.effectiveDate<=?3) \r\n"
//			+ "             group by p.employeeId";

	String currentCostToCompanyEmployeeWise = "SELECT e.employeeCode,  CONCAT(e.firstName, ' ', e.lastName) as employeeName, \r\n"
			+ "			 des.designationName, dp.departmentName, g.gradesName,IF(e.empType='PE',  \"Permanant\", \"Contract\"), \r\n"
			+ "			 IF(e.activeStatus='AC', \"Working\", \"Former\" ), ph.effectiveDate,  ph.dateUpdate, ph.ctc,ph.grossPay, GROUP_CONCAT(DISTINCT ps.payHeadId)as payHeadId, \r\n"
			+ "			 GROUP_CONCAT(ps.amount  ORDER by ps.payHeadId),\r\n"
			+ "			 ph.epfEmployee, ph.epfEmployer, ph.esiEmployee, ph.esiEmployer, ph.lwfEmployeeAmount, ph.lwfEmployerAmount, ph.professionalTax, \r\n"
			+ "			 ph.netPay, ph.epfEmployeePension\r\n"
			+ "             From PayStructureHd ph LEFT JOIN Employee e On ph.employeeId=e.employeeId \r\n"
			+ "			 LEFT JOIN Department dp ON dp.departmentId=e.departmentId  LEFT JOIN Designation des \r\n"
			+ "			 ON des.designationId= e.designationId  LEFT JOIN Grades g On g.gradesId=ph.gradesId  \r\n"
			+ "			 LEFT JOIN PayStructure ps On ps.payStructureHdId=ph.payStructureHdId\r\n"
			+ "			 WHERE e.companyId=?1 and ph.employeeId=?2 and (ph.dateEnd is null or ph.dateEnd>=?3)\r\n"
			+ "			 and (ph.effectiveDate is NOT null and ph.effectiveDate<=?3)";

	@Query(value = currentCostToCompanyEmployeeWise, nativeQuery = true)
	public List<Object[]> getcurrentCostToCompanyReport(Long companyId, Long employeeId, Date currentDate);

//	String currentCostToCompanyEmployeeDeptWise = "SELECT rp.employeeCode, rp.Name, des.designationName, dept.departmentName ,g.gradesName ,"
//			+ "  IF(e.empType='PE', \"Permanant\", \"Contract\"), IF(e.activeStatus='AC', \"Working\", \"Former\" ),ps.effectiveDate, ps.dateUpdate,ps.ctc, \r\n"
//			+ "		rp.GrossSalary, GROUP_CONCAT(DISTINCT p.payHeadId), GROUP_CONCAT(DISTINCT(p.amount) ORDER by p.payHeadId),  \r\n"
//			+ "		rp.NetPayableAmount \r\n"
//			+ "		  FROM ReportPayOut rp  join PayOut p on p.employeeId =rp.employeeId "
//			+ "			  LEFT JOIN Employee e ON e.employeeId = rp.employeeId  \r\n"
//			+ "		  LEFT JOIN Designation des ON des.designationId = e.designationId  \r\n"
//			+ "		 LEFT JOIN PayHeads ph ON ph.payHeadId=p.payHeadId and ph.activeStatus='AC'  \r\n"
//			+ "		  LEFT JOIN Department dept ON dept.departmentId = rp.departmentId\r\n"
//			+ "          LEFT JOIN Grades g ON g.gradesId=e.gradesId \r\n"
//			+ "          LEFT  JOIN PayStructureHd ps ON ps.employeeId=rp.employeeId \r\n"
//			+ "          WHERE e.companyId= ?1 and rp.departmentId in  ?2 "
//			+ "		 and ( ps.dateEnd is null or ps.dateEnd>=?3) and (ps.effectiveDate is NOT null and ps.effectiveDate<=?3) and e.activeStatus=?4  group by rp.employeeId ";

	String currentCostToCompanyEmployeeDeptWise = "	SELECT e.employeeCode, CONCAT(e.firstName, ' ', e.lastName) as employeeName,\r\n"
			+ "			 des.designationName, dp.departmentName, g.gradesName, IF(e.empType='PE',  \"Permanant\", \"Contract\"),\r\n"
			+ "			  IF(e.activeStatus='AC', \"Working\", \"Former\" ), ph.effectiveDate,  ph.dateUpdate, ph.ctc, ph.grossPay, GROUP_CONCAT(DISTINCT ps.payHeadId)as payHeadId, \r\n"
			+ "			 GROUP_CONCAT(ps.amount  ORDER by ps.payHeadId), \r\n"
			+ "			 ph.epfEmployee, ph.epfEmployer, ph.esiEmployee, ph.esiEmployer,  ph.lwfEmployeeAmount, ph.lwfEmployerAmount, ph.professionalTax, \r\n"
			+ "			 ph.netPay,  ph.epfEmployeePension\r\n"
			+ "             From PayStructureHd ph LEFT JOIN Employee e On ph.employeeId=e.employeeId \r\n"
			+ "			LEFT JOIN Department dp ON dp.departmentId=e.departmentId  LEFT JOIN Designation des \r\n"
			+ "			 ON des.designationId= e.designationId  LEFT JOIN Grades g On g.gradesId=ph.gradesId  \r\n"
			+ "			 LEFT JOIN PayStructure ps On ps.payStructureHdId=ph.payStructureHdId \r\n"
			+ "            WHERE e.companyId=?1  and dp.departmentId in ?2 and (ph.dateEnd is null or ph.dateEnd>=?3)  \r\n"
			+ "			 and (ph.effectiveDate is NOT null and ph.effectiveDate<=?3) and e.activeStatus=?4  group by ph.employeeId";

	@Query(value = currentCostToCompanyEmployeeDeptWise, nativeQuery = true)
	public List<Object[]> getcurrentCostToCompanyDepartmentWiseReport(Long companyId, List<Long> departmentList,
			Date currentDate, String status);

	@Query("from ReportPayOut rp Where rp.companyId=?1 and rp.id.processMonth=?2 ")
	public List<ReportPayOut> findAllEmployee(Long companyId, String processMonth);

	String grossSum = "SELECT COUNT(rp.processMonth), SUM(rp.tdsAmount) as tdsAmount, SUM(rp.TotalEarning)+ SUM(rp.arearAmount) as totalEarnigGrossSalary , SUM(rp.otherEarning) otherEarning, sum(rp.ProvidentFundEmployee),  SUM(rp.PT), SUM(rp.HRA)  FROM  ReportPayOut rp JOIN PayrollControl pc on  pc.processMonth=rp.processMonth WHERE rp.companyId=?2 and rp.employeeId=?1 and pc.financialYearId=?3";

	@Query(value = grossSum, nativeQuery = true)
	public List<Object[]> getGrossSumEmployee(Long empId, Long companyId, Long financialYearId);

	String allEarningPayHeads = "SELECT concat( ph.payHeadName,'/',ph.payHeadId) FROM PayHeads ph WHERE ph.companyId=?1 and ph.earningDeduction='EA' and ph.payHeadId in ?2 order by ph.sequenceId ASC";

	@Query(value = allEarningPayHeads, nativeQuery = true)
	public String[] getAllEarnngPayHeads(Long companyId, List<Long> earnngPayHeadsId);

	String allDeductionPayHeads = "SELECT concat( ph.payHeadName,'/',ph.payHeadId) FROM PayHeads ph WHERE ph.companyId=?1 and ph.earningDeduction='DE' and ph.payHeadId in ?2 order by ph.sequenceId ASC";

	@Query(value = allDeductionPayHeads, nativeQuery = true)
	public String[] getAllDeductionPayHeads(Long companyId, List<Long> deductionPayHeadsId);

	String incomeTaxMonthlyReport = "SELECT rp.employeeCode,rp.Name,rp.dateOfJoining,eip.idNumber,rp.TotalEarning,rp.tdsAmount FROM ReportPayOut rp LEFT JOIN EmployeeIdProofs eip ON eip.employeeId=rp.employeeId AND eip.idTypeId='PA' WHERE rp.companyId=?1 AND rp.departmentId IN (?2) AND rp.processMonth=?3 AND rp.tdsAmount > 0 ORDER BY rp.employeeCode";

	@Query(value = incomeTaxMonthlyReport, nativeQuery = true)
	public List<Object[]> getIncomeTaxMonthlyReport(Long companyId, List<Long> departmentIds, String processMonth);

	String incomeTaxYearlyReport = " SELECT rp.employeeCode,rp.Name,rp.dateOfJoining,eip.idNumber,rp.tdsAmount,pc.processMonth FROM ReportPayOut rp LEFT JOIN  PayrollControl pc on pc.financialYearId=?1 AND rp.processMonth=pc.processMonth LEFT JOIN EmployeeIdProofs eip ON eip.employeeId=rp.employeeId AND eip.idTypeId='PA' WHERE rp.companyId=?1  AND pc.financialYearId=?2 AND rp.tdsAmount > 0   ORDER BY rp.employeeCode ";

	@Query(value = incomeTaxYearlyReport, nativeQuery = true)
	public List<Object[]> getIncomeTaxAnnualyReport(Long companyId, Long financialYearId);

	String getMonthByFin = "SELECT pc.processMonth FROM PayrollControl pc  WHERE  pc.financialYearId=?1";

	@Query(value = getMonthByFin, nativeQuery = true)
	public String[] getMonthByFinId(Long financialYearId);

	String RENT_PAID_LANDLORD_DETAILS = "SELECT emp.employeeCode, concat(emp.firstName, ' ', emp.lastName), th.fromDate, th.toDate, adr.addressText, c.cityName, th.totalRental, th.landlordName, th.addressOfLandlord, th.landlordPan FROM Employee emp JOIN TdsTransaction tt on emp.employeeId=tt.employeeId JOIN TdsHouseRentInfo th on th.tdsTransactionId=tt.tdsTransactionId JOIN City c on c.cityId=emp.cityId JOIN Address adr on adr.cityId=emp.cityId LEFT JOIN FinancialYear fy on fy.financialYearId=tt.financialYearId "
			+ "WHERE emp.companyId = ?1 and fy.financialYearId =?2 GROUP BY th.tdsHouseRentInfoId";

	@Query(value = RENT_PAID_LANDLORD_DETAILS, nativeQuery = true)
	public List<Object[]> getRentPaidLandlordDetails(Long companyId, Long financialYearId);

	String LabourWelfareFundStatementEmployeeWise = "Select   rp.employeeCode, rp.Name,cty.cityName  ,st.stateName,  rp.GrossSalary, rp.LWF, rp.employerWelFareAmount\r\n"
			+ "from ReportPayOut rp  LEFT JOIN Employee e on e.employeeId=rp.employeeId\r\n"
			+ "LEFT JOIN City cty on cty.cityId=e.cityId\r\n" + "LEFT JOIN State st on st.stateId=e.stateId\r\n"
			+ "WHERE e.companyId=?1 AND rp.processMonth=?2 AND rp.LWF!=0.00  GROUP BY rp.employeeId  ORDER BY st.stateId ASC";

	@Query(value = LabourWelfareFundStatementEmployeeWise, nativeQuery = true)
	public List<Object[]> findLabourWelfareFundSummaryMonthlyEmployeeWise(Long companyId, String processMonth);

	String LabourWelfareFundStatementStateWise = "Select   st.stateName, COUNT(rp.employeeCode),  SUM(rp.GrossSalary), SUM(rp.LWF),  SUM(rp.employerWelFareAmount) as LWFEmployerContri from ReportPayOut rp\r\n"
			+ "LEFT JOIN Employee e on e.employeeId=rp.employeeId\r\n"
			+ "LEFT JOIN State st on st.stateId=e.stateId\r\n"
			+ "WHERE e.companyId=?1 AND rp.processMonth=?2 AND rp.LWF!=0.00  \r\n"
			+ "GROUP BY st.stateId ORDER BY st.stateId";

	@Query(value = LabourWelfareFundStatementStateWise, nativeQuery = true)
	public List<Object[]> findLabourWelfareFundSummaryMonthlyStateWise(Long companyId, String processMonth);

	String ProfessionalTaxStatementEmployeeAnnualSummary = "SELECT rp.employeeCode, rp.Name, c.cityName AS jobLocation, s.stateName, sum(rp.TotalEarning), \r\n"
			+ "			rp.PT FROM ReportPayOut rp LEFT JOIN Employee e ON e.employeeId = rp.employeeId LEFT JOIN City c ON c.cityId = e.cityId LEFT JOIN State s \r\n"
			+ "			 ON c.stateId = s.stateId  LEFT JOIN PayrollControl pc ON \r\n"
			+ "			 pc.processMonth=rp.processMonth WHERE  e.companyId=?1 and  pc.financialYearId=?2     \r\n"
			+ "             and rp.PT is not null and rp.PT != 0  Group by rp.employeeId\r\n"
			+ "            ORDER BY pc.controlId ASC";

	@Query(value = ProfessionalTaxStatementEmployeeAnnualSummary, nativeQuery = true)
	public List<Object[]> findProfessionalTaxStatementEmployeeAnnualSummary(Long companyId, Long financialYearId);

	String PtStatementEmployeeWiseMonthly = "SELECT rp.employeeCode, rp.Name, c.cityName AS jobLocation, s.stateName, rp.TotalEarning, \r\n"
			+ "			 rp.PT,rp.processMonth FROM ReportPayOut rp LEFT JOIN Employee e ON e.employeeId = rp.employeeId LEFT JOIN City c ON c.cityId = e.cityId LEFT JOIN State s \r\n"
			+ "			 ON c.stateId = s.stateId LEFT JOIN PayrollControl pc ON \r\n"
			+ "			 pc.processMonth=rp.processMonth\r\n"
			+ "             WHERE e.companyId=?1 and pc.financialYearId=?2  and rp.PT is not null and rp.PT != 0     ORDER BY pc.controlId ASC";

	@Query(value = PtStatementEmployeeWiseMonthly, nativeQuery = true)
	public List<Object[]> findPtStatementEmployeeWiseMonthly(Long companyId, Long financialYearId);

	String ProcessMonth = "SELECT rp.processMonth, rp.employeeCode FROM ReportPayOut rp LEFT JOIN Employee e ON e.employeeId = rp.employeeId LEFT JOIN City c ON c.cityId = e.cityId LEFT JOIN State s \r\n"
			+ "			 ON c.stateId = s.stateId LEFT JOIN PayrollControl pc ON \r\n"
			+ "			 pc.processMonth=rp.processMonth\r\n"
			+ "             WHERE e.companyId=?1 and pc.financialYearId=?2 and rp.PT is not null and rp.PT != 0    GROUP BY rp.processMonth ORDER BY pc.controlId ASC";

	@Query(value = ProcessMonth, nativeQuery = true)
	public List<Object[]> findProcessMonth(Long companyId, Long financialYearId);

	String ProfessionalTaxStatementStateWiseAnnualSummery = "SELECT s.stateName, Count( rp.employeeCode) as totalEmployee, Sum(rp.TotalEarning), Sum( rp.PT) \r\n"
			+ "			FROM ReportPayOut rp \r\n"
			+ "			 LEFT JOIN Employee e ON e.employeeId = rp.employeeId LEFT JOIN City c ON c.cityId = e.cityId LEFT JOIN State s \r\n"
			+ "			 ON c.stateId = s.stateId\r\n" + "             LEFT JOIN PayrollControl pc ON \r\n"
			+ "			 pc.processMonth=rp.processMonth\r\n"
			+ "             WHERE   e.companyId=?1  and pc.financialYearId=?2   and rp.PT is not null and rp.PT != 0   \r\n"
			+ "			 Group by s.stateId ORDER BY pc.controlId ASC";

	@Query(value = ProfessionalTaxStatementStateWiseAnnualSummery, nativeQuery = true)
	public List<Object[]> findProfessionalTaxStatementStateWiseAnnualSummery(Long companyId, Long financialYearId);

	String PtStatementStateWiseMonthly = "SELECT s.stateName, Count( rp.employeeCode) as totalEmployee, Sum(rp.TotalEarning), Sum( rp.PT) ,rp.processMonth\r\n"
			+ "           FROM ReportPayOut rp \r\n"
			+ "			 LEFT JOIN Employee e ON e.employeeId = rp.employeeId LEFT JOIN City c ON c.cityId = e.cityId LEFT JOIN State s \r\n"
			+ "			 ON c.stateId = s.stateId    LEFT JOIN PayrollControl pc ON \r\n"
			+ "			 pc.processMonth=rp.processMonth\r\n"
			+ "             WHERE e.companyId=?1  AND  pc.financialYearId=?2  AND rp.PT is not null and rp.PT != 0  GROUP by rp.employeeId  ORDER BY pc.controlId ASC";

	@Query(value = PtStatementStateWiseMonthly, nativeQuery = true)
	public List<Object[]> findPtStatementStateWiseMonthly(Long companyId, Long financialYearId);

	String ANNUAL_PF_CONTRIBUTION = "SELECT rp.processMonth, SUM(rp.TotalEarning+rp.otherEarning), SUM(rp.EarnedStdEpfWagesAmount), SUM(rp.PensionEarningSalary) , SUM(rp.absense),\r\n"
			+ "	SUM(rp.ProvidentFundEmployee), SUM(rp.ProvidentFundEmployer) ,SUM(rp.ProvidentFundEmployerPension), SUM(rp.dearnessAllowanceEarning) from ReportPayOut rp LEFT JOIN PayrollControl pc ON pc.processMonth=rp.processMonth \r\n"
			+ "   WHERE pc.financialYearId=?2 and rp.companyId=?1 AND ( rp.isNoPFDeduction='N' OR rp.isNoPFDeduction IS Null) Group by rp.processMonth ORDER BY pc.controlId";

	@Query(value = ANNUAL_PF_CONTRIBUTION, nativeQuery = true)
	public List<Object[]> findPFControAnnual(Long companyId, Long financialYearId);

	String MONTHLY_PF_CONTRIBUTION = "SELECT rp.UANNO ,rp.Name, rp.TotalEarning+rp.otherEarning, rp.EarnedStdEpfWagesAmount,rp.PensionEarningSalary ,rp.absense,\r\n"
			+ "	rp.ProvidentFundEmployee,rp.ProvidentFundEmployer ,rp.ProvidentFundEmployerPension, rp.dearnessAllowanceEarning, rp.processMonth \r\n"
			+ "	FROM ReportPayOut rp LEFT JOIN PayrollControl pc ON pc.processMonth=rp.processMonth WHERE pc.financialYearId=?2 AND rp.companyId=?1 AND ( rp.isNoPFDeduction='N' OR rp.isNoPFDeduction IS Null) Order BY rp.processMonth";

	@Query(value = MONTHLY_PF_CONTRIBUTION, nativeQuery = true)
	public List<Object[]> findPFControMonthly(Long companyId, Long financialYearId);

	String TOTAL_PF_CONTRIBUTION = " SELECT SUM(rp.TotalEarning+rp.otherEarning), SUM(rp.EarnedStdEpfWagesAmount), SUM(rp.PensionEarningSalary), SUM(rp.ProvidentFundEmployee), SUM(rp.ProvidentFundEmployer), SUM(rp.ProvidentFundEmployerPension), SUM(rp.absense) FROM ReportPayOut rp LEFT JOIN PayrollControl pc ON pc.processMonth=rp.processMonth "
			+ "WHERE pc.financialYearId=?2 AND rp.companyId=?1 AND ( rp.isNoPFDeduction='N' OR rp.isNoPFDeduction IS Null) ";

	@Query(value = TOTAL_PF_CONTRIBUTION, nativeQuery = true)
	public List<Object[]> findTotalPFControbution(Long companyId, Long financialYearId);

	String TOTAL_PF_CONTRIBUTION_MONTHLY = " SELECT SUM(rp.TotalEarning+rp.otherEarning), SUM(rp.EarnedStdEpfWagesAmount), SUM(rp.PensionEarningSalary), SUM(rp.ProvidentFundEmployee), SUM(rp.ProvidentFundEmployer), SUM(rp.ProvidentFundEmployerPension), SUM(rp.absense), rp.processMonth FROM ReportPayOut rp LEFT JOIN PayrollControl pc ON pc.processMonth=rp.processMonth\r\n"
			+ "			WHERE pc.financialYearId=?2 AND rp.companyId=?1 AND ( rp.isNoPFDeduction='N' OR rp.isNoPFDeduction IS Null) GROUP BY rp.processMonth ";

	@Query(value = TOTAL_PF_CONTRIBUTION_MONTHLY, nativeQuery = true)
	public List<Object[]> getTotalMonthly(Long companyId, Long financialYearId);

    String PF_CONTRIBUTION_EMPLOYEE_WISE = " SELECT rp.processMonth, SUM(rp.TotalEarning+rp.otherEarning), SUM(rp.EarnedStdEpfWagesAmount), SUM(rp.PensionEarningSalary),\r\n"
			+ "	SUM(rp.ProvidentFundEmployee), SUM(rp.ProvidentFundEmployer), SUM(rp.ProvidentFundEmployerPension), SUM(rp.ProvidentFundEmployee+rp.ProvidentFundEmployer+rp.ProvidentFundEmployerPension) as TotalCOntribution,SUM(rp.absense)\r\n"
			+ "    FROM ReportPayOut rp JOIN Employee e ON e.employeeId=rp.employeeId WHERE e.companyId=?1 AND e.employeeId=?2 AND e.activeStatus=?3 AND ( rp.isNoPFDeduction='N' OR rp.isNoPFDeduction IS Null) GROUP BY rp.processMonth ORDER BY rp.processMonth ";

	@Query(value = PF_CONTRIBUTION_EMPLOYEE_WISE, nativeQuery = true)
	public List<Object[]> findPFContributionEmpWise(Long companyId, Long employeeId, String activeStatus);

	String TOTAL_PF_CONTRIBUTION_OF_EMPLOYEE = " SELECT SUM(rp.TotalEarning+rp.otherEarning), SUM(rp.EarnedStdEpfWagesAmount), SUM(rp.PensionEarningSalary),\r\n"
			+ "			SUM(rp.ProvidentFundEmployee), SUM(rp.ProvidentFundEmployer), SUM(rp.ProvidentFundEmployerPension), SUM(rp.ProvidentFundEmployee+rp.ProvidentFundEmployer+rp.ProvidentFundEmployerPension) as TotalCOntribution,SUM(rp.absense)\r\n"
			+ "			FROM ReportPayOut rp JOIN Employee e ON e.employeeId=rp.employeeId WHERE e.companyId=?1 AND e.employeeId=?2 AND e.activeStatus=?3 AND ( rp.isNoPFDeduction='N' OR rp.isNoPFDeduction IS Null) ";

	@Query(value = TOTAL_PF_CONTRIBUTION_OF_EMPLOYEE, nativeQuery = true)
	public List<Object[]> findTotalOfEmployee(Long companyId, Long employeeId, String activeStatus);
    String PAYMENT_TRANSFER_SHEET = " SELECT rp.Name,rp.employeeCode,rp.companyId ,comp.companyName,dept.departmentName,rp.bankName,rp.accountNumber,rp.IFSCCode,rp.dateOfJoining,rp.Basic,rp.dearnessAllowance,rp.ConveyanceAllowance,rp.HRA,rp.MedicalAllowance,rp.AdvanceBonus,rp.SpecialAllowance,rp.CompanyBenefits,rp.GrossSalary,rp.absense, rp.payDays,rp.BasicEarning,rp.ConveyanceAllowanceEarning,rp.HRAEarning,rp.MedicalAllowanceEarning,rp.AdvanceBonusEarning,rp.SpecialAllowanceEarning,rp.CompanyBenefitsEarning,rp.otherAllowanceEarning,rp.TotalEarning ,rp.Loan,rp.otherDeduction,rp.ProvidentFundEmployee,rp.ESI_Employee, rp.PT,rp.TDS,rp.TotalDeduction,rp.NetPayableAmount,rp.otherEarning,rp.transactionNo,hd.status FROM PayRegisterHd payregister\r\n"
			+ "		Left JOIN PayRegister pay ON pay.payRegisterHdId =payregister.payRegisterHdId\r\n"
			+ "		LEFT JOIN ReportPayOut rp ON rp.employeeId = pay.employeeId \r\n"
			+ "		LEFT JOIN Department dept ON dept.departmentId = pay.departmentId\r\n"
			+ "		LEFT JOIN HoldSalary hd on rp.employeeId=hd.employeeId and rp.processMonth=rp.processMonth\r\n"
			+ "        LEFT JOIN PayrollControl pc ON pc.processMonth=rp.processMonth\r\n"
			+ "		 JOIN Company comp ON rp.companyId= comp.companyId\r\n"
			+ "		  where pc.financialYearId=?1 AND rp.processMonth=?2 AND  dept.departmentId IN (?3) group by pay.employeeId ORDER BY dept.departmentId";

	@Query(value = PAYMENT_TRANSFER_SHEET, nativeQuery = true)
	public List<Object[]> findPaymentTransferSheet(Long financialYearId, String processMonth, Long departmentId);

}
