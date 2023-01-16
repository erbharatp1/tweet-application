package com.csipl.hrms.service.payroll.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.csipl.hrms.common.constant.StatusMessage;
import com.csipl.hrms.model.payrollprocess.ReportPayOut;

public interface ReportPayOutRepository extends CrudRepository<ReportPayOut, Long> {

	String summaryReportQueryForDepartment = "select ReportPayOut.departmentId, Department.departmentName ,sum(ReportPayOut.BasicEarning),sum(ReportPayOut.GrossSalary),sum(ReportPayOut.NetPayableAmount),sum(ReportPayOut.ProvidentFundEmployee),sum(ReportPayOut.ProvidentFundEmployer),sum(ReportPayOut.ESI_Employee),sum(ReportPayOut.ESI_Employer),sum(ReportPayOut.PT),sum(ReportPayOut.TDS),sum(ReportPayOut.Loan) from ReportPayOut , Department where ReportPayOut.departmentId = Department.departmentId and ReportPayOut.processMonth=?2\r\n"
			+ " and ReportPayOut.departmentId =?1 group by ReportPayOut.departmentId , Department.departmentName";

	String sumaryReportForCompany = "select ReportPayOut.departmentId, Department.departmentName ,sum(ReportPayOut.BasicEarning),sum(ReportPayOut.GrossSalary),sum(ReportPayOut.NetPayableAmount),sum(ReportPayOut.ProvidentFundEmployee),sum(ReportPayOut.ProvidentFundEmployer),sum(ReportPayOut.ESI_Employee),sum(ReportPayOut.ESI_Employer),sum(ReportPayOut.PT),sum(ReportPayOut.TDS),sum(ReportPayOut.Loan) from ReportPayOut , Department where ReportPayOut.departmentId = Department.departmentId and ReportPayOut.processMonth=?2\r\n"
			+ " and ReportPayOut.companyId =?1 group by ReportPayOut.departmentId , Department.departmentName";

//	String salarySlipProcessForDepartment = "SELECT ReportPayOut.departmentId, ReportPayOut.employeeCode, ReportPayOut.Name, ReportPayOut.dateOfJoining, Department.departmentName, Designation.designationName,ReportPayOut.email,ReportPayOut.employeeId,ReportPayOut.processMonth FROM ReportPayOut, Department, Employee, Designation WHERE ReportPayOut.departmentId = Department.departmentId AND ReportPayOut.employeeId=Employee.employeeId AND Employee.designationId=Designation.designationId AND ReportPayOut.departmentId=?1 AND ReportPayOut.processMonth =?2";
//	String salarySlipProcessForAll = "SELECT ReportPayOut.departmentId, ReportPayOut.employeeCode, ReportPayOut.Name, ReportPayOut.dateOfJoining, Department.departmentName, Designation.designationName,ReportPayOut.email,ReportPayOut.employeeId,ReportPayOut.processMonth FROM ReportPayOut, Department, Employee, Designation WHERE ReportPayOut.departmentId = Department.departmentId AND ReportPayOut.employeeId=Employee.employeeId AND Employee.designationId=Designation.designationId AND  ReportPayOut.processMonth =?2 AND ReportPayOut.companyId =?1";

	String salarySlipProcessForDepartment = "SELECT ReportPayOut.departmentId, ReportPayOut.employeeCode, ReportPayOut.Name, ReportPayOut.dateOfJoining, Department.departmentName,Designation.designationName, Employee.personalEmail, ReportPayOut.employeeId, ReportPayOut.processMonth, Grades.gradesName FROM ReportPayOut, Department, Employee, Designation, Grades WHERE ReportPayOut.departmentId = Department.departmentId AND ReportPayOut.employeeId = Employee.employeeId AND Employee.designationId = Designation.designationId AND ReportPayOut.departmentId =?1 AND ReportPayOut.processMonth =?2 AND Grades.gradesId=Employee.gradesId";
	String salarySlipProcessForAll = "SELECT ReportPayOut.departmentId, ReportPayOut.employeeCode, ReportPayOut.Name, ReportPayOut.dateOfJoining, Department.departmentName,Designation.designationName, Employee.personalEmail, ReportPayOut.employeeId, ReportPayOut.processMonth, Grades.gradesName FROM ReportPayOut, Department, Employee, Designation, Grades WHERE ReportPayOut.departmentId = Department.departmentId AND ReportPayOut.employeeId = Employee.employeeId AND Employee.designationId = Designation.designationId AND ReportPayOut.processMonth =?2 AND ReportPayOut.companyId =?1 AND Grades.gradesId=Employee.gradesId";

	String tdsSummary = "SELECT SUM(GrossSalary), SUM(ProvidentFundEmployee), SUM(ESI_Employee), SUM(PT), processMonth \r\n"
			+ "FROM ReportPayOut where employeeId=?1 and processMonth in (?2)";

	String processMonthForTdsSummary = "select processMonth FROM ReportPayOut WHERE employeeId=?1 and processMonth IN (?2)";
	/*
	 * String summaryQueryList= "select ReportPayOut.departmentId," +
	 * " Department.departmentName ,sum(ReportPayOut.BasicEarning)," +
	 * "sum(ReportPayOut.GrossSalary),sum(ReportPayOut.NetPayableAmount)," +
	 * "sum(ReportPayOut.ProvidentFundEmployee),sum(ReportPayOut.ProvidentFundEmployer),"
	 * +
	 * "sum(ReportPayOut.ESI_Employee),sum(ReportPayOut.ESI_Employer),sum(ReportPayOut.PT),"
	 * + "sum(ReportPayOut.TDS),sum(ReportPayOut.Loan) from ReportPayOut ," +
	 * " Department where ReportPayOut.departmentId = Department.departmentId and ReportPayOut.companyId=?1 and  ReportPayOut.processMonth=?2  group by ReportPayOut.departmentId , Department.departmentName"
	 * ;
	 */

	@Query(value = summaryReportQueryForDepartment, nativeQuery = true)
	List<Object[]> findPayOutReportOfDepartment(Long departmentId, String processMonth);

	@Query(value = sumaryReportForCompany, nativeQuery = true)
	List<Object[]> findPayOutReportOfCompany(Long companyId, String processMonth);

	/*
	 * @Query(value=summaryQueryList,nativeQuery = true) List<Object[]>
	 * findPayOutSumaryReportOfDepartment(Long companyId, String processMonth);
	 */

	// @Query(" from ReportPayOut where departmentId=?1 AND processMonth=?2 ORDER BY
	// departmentId DESC")
	@Query(" from ReportPayOut rp where rp.departmentId=?1  AND  rp.id.processMonth=?2 AND rp.employee.employeeId not in (select p.employee.employeeId from Provision p where p.processMonth=?2 ) ORDER BY  rp.departmentId  DESC")
	List<ReportPayOut> findEmployeesPayOutReport(Long departmentId, String processMonth);

	@Query(" from ReportPayOut rp where employeeCode=?1  AND  rp.id.processMonth=?2 and rp.companyId =?3 ")
	ReportPayOut findEmployeePayOutPdf(String employeeCode, String processMonth, Long companyId);

	@Query(" from ReportPayOut rp where    rp.ESICNumber =?1 ")
	List<ReportPayOut> findEsiReport(String esicNO);

	@Query(" from ReportPayOut rp where rp.uanno=?1")
	List<ReportPayOut> findPfReport(String uanno);

	// @Query(" from ReportPayOut where companyId=?1 AND processMonth=?2 ORDER BY
	// departmentId DESC")

	@Query(" from ReportPayOut rp where rp.companyId=?1  AND  rp.id.processMonth=?2 AND rp.employee.employeeId not in (select p.employee.employeeId from Provision p where p.processMonth=?2 )")
	List<ReportPayOut> findAllEmployeesPayOutReport(Long companyId, String processMonth);

	@Query(" from ReportPayOut rp where rp.companyId=?1  AND  rp.id.processMonth=?2 ORDER BY  rp.dateOfJoining  ASC")
	List<ReportPayOut> findAllEmployeesPaysheet(Long companyId, String processMonth);

//	String findESICReport = "SELECT   rp.employeeCode,rp.ESICNumber ,rp .Name ,rp.fatherName,\r\n"
//			+ "family.name,family.relation,rp.DOB,rp.gender,rp.esicjoining,\r\n"
//			+ "rp.presense,rp.GrossSalary,rp.TotalEarning ,rp.ESI_Employee\r\n"
//			+ "   FROM ReportPayOut  rp   LEFT JOIN EmployeeStatuary st ON rp.employeeId= st.employeeId \r\n"
//			+ "   LEFT JOIN EmployeeFamily family ON family.familyId= st.familyId \r\n"
//			+ "   JOIN Esi esi on esi.companyId=rp.companyId  where  rp.GrossSalary<esi.maxGrossLimit AND rp.processMonth=?2"
//			+ "  and rp.companyId =?1 AND rp.isNoPFDeduction='N' and st.statuaryType='ES' or st.statuaryType=' ' or st.statuaryType is null group by rp.employeeId"
//			+ "  ORDER BY  dateOfJoining  ASC";

//	String findESICReport= "SELECT   rp.employeeCode,rp.ESICNumber ,rp .Name ,rp.fatherName,\r\n" + 
//			" 	rp.esicNominee,rp.esicNomineeRelation, rp.DOB,rp.gender,rp.esicjoining,\r\n" + 
//			" 	rp.payableDays,rp.GrossSalary,rp.TotalEarning ,rp.ESI_Employee\r\n" + 
//			"   FROM ReportPayOut  rp   \r\n" + 
//			"              JOIN EmployeeStatuary st ON  st.employeeId =rp.employeeId  and st.statuaryType='ES' or st.statuaryType=' '\r\n" + 
//			 
//			" 	JOIN Esi esi on esi.companyId=rp.companyId  where  (rp.GrossSalary<esi.maxGrossLimit OR rp.isNoESIDeduction='N') AND rp.processMonth=?2 and rp.companyId =?1 "
//			+ " AND rp.isNoESIDeduction='N'  or st.statuaryType is null   \r\n" + 
//			"             group by rp.employeeId\r\n" + 
//			"             ORDER BY  dateOfJoining  ASC";

	String findESICReport = "SELECT   rp.employeeCode,rp.ESICNumber ,rp .Name ,rp.fatherName,  "
			+ "			  	rp.esicNominee,rp.esicNomineeRelation, rp.DOB,rp.gender,rp.esicjoining,  "
			+ "			  	rp.payableDays,rp.GrossSalary,rp.TotalEarning ,rp.ESI_Employee  "
			+ "			    FROM ReportPayOut  rp     \r\n"
			+ "			               JOIN EmployeeStatuary st ON  st.employeeId =rp.employeeId  and st.statuaryType='ES' or st.statuaryType=' ' "
			+ "			  	JOIN Esi esi on esi.companyId=rp.companyId  where  (rp.GrossSalary<esi.maxGrossLimit OR rp.isNoESIDeduction='N') AND "
			+ "  rp.processMonth=?2 and rp.companyId =?1  \r\n"
			+ "			    and rp.ESI_Employee !=0 and rp.payableDays>0 AND rp.isNoESIDeduction='N'  or st.statuaryType is null     "
			+ "			              group by rp.employeeId  "
			+ "			              ORDER BY  dateOfJoining  ASC ";

	// @Query(" from ReportPayOut where companyId=?1 AND processMonth=?2 and
	// ESI_Employee > 0 ORDER BY dateOfJoining ASC")
	@Query(value = findESICReport, nativeQuery = true)
	List<Object[]> findEmployeesESIPayOutReport(Long companyId, String processMonth);

	String findEPFReport = "SELECT   rp.employeeCode,rp.UANNO ,rp .Name ,rp.fatherName,rp.epfNominee,rp.epfNomineeRelation,rp.DOB,rp.gender,"
			+ " rp.epfJoining,rp.maritalStatus,rp.accountNumber,rp.IFSCCode, rp.aadharNo,rp.PANNO,rp.mobileNo,rp.email, rp.GrossSalary+rp.otherEarning, "
			+ " rp.dearnessAllowance,rp.stdEpfWagesAmount, rp.payableDays,rp.absense, rp.TotalEarning+rp.otherEarning, rp.EarnedStdEpfWagesAmount, rp.PensionEarningSalary ,rp.ProvidentFundEmployee "
			+ " FROM ReportPayOut  rp where   rp.processMonth=?1 and rp.companyId =?2 AND ( rp.isNoPFDeduction= 'N' OR rp.isNoPFDeduction IS Null) "
			+ " ORDER BY  dateOfJoining  ASC";

	@Query(value = findEPFReport, nativeQuery = true)
	// @Query(" from ReportPayOut rp where rp.id.processMonth=?1 and rp.companyId
	// =?2 and rp.providentFundEmployee > 0 ORDER BY dateOfJoining ASC")
	List<Object[]> findEsiEpfReport(String processMonth, Long companyId);

	@Query(value = salarySlipProcessForDepartment, nativeQuery = true)
	List<Object[]> findEmployeesPayOutReportForSalaryProcess(Long departmentId, String processMonth);

	@Query(value = salarySlipProcessForAll, nativeQuery = true)
	List<Object[]> findAllEmployeesPayOutReportForSalaryProcess(Long companyId, String processMonth);

	@Query(" select COUNT(1) from ReportPayOut WHERE employeeId=?1 ")
	Long checkEmployeePayrollProcess(Long employeeId);

	@Query("select COUNT(1) from ReportPayOut WHERE employeeCode=?1 And processMonth=?2 And companyId=?3")
	Long checkEmployeePayrollForSalarySlip(String employeeCode, String processMonth, Long companyId);

	String companypayoutReportCount = "Select COUNT(*) from ReportPayOut rp where rp.companyId=?1 AND rp.processMonth=?2 AND rp.employeeId not in (select p.employeeId from Provision p where p.processMonth=?2)";

	@Query(value = companypayoutReportCount, nativeQuery = true)
	public int companyPayoutReportCount(Long companyId, String processMonth);

	String departmentpayoutReportCount = "select COUNT( * ) from ReportPayOut , Department where ReportPayOut.departmentId = Department.departmentId and ReportPayOut.companyId=?1 and ReportPayOut.processMonth=?2 and ReportPayOut.departmentId =?3 group by ReportPayOut.departmentId , Department.departmentName";

	@Query(value = departmentpayoutReportCount, nativeQuery = true)
	public int departmentPayoutReportCount(Long companyId, String processMonth, String departmentId);

	@Query(value = processMonthForTdsSummary, nativeQuery = true)
	List<Object[]> findProcessMonthForTdsSummary(Long employeeId, String month);

	@Query(value = tdsSummary, nativeQuery = true)
	List<Object[]> findTdsSummary(Long employeeId, String month);

	@Query("select COUNT(1) from ReportPayOut WHERE companyId=?1 And processMonth=?2 And departmentId=?3")
	Long checkPayRollForEmployeeJoining(Long companyId, String processMonth, Long departmentId);

	@Query("SELECT COUNT(1) FROM ReportPayOut where employeeId=?1 AND processMonth=?2")
	Long checkPayrollOfEmployee(Long employeeId, String processMonth);

	@Query(" from ReportPayOut rp where employeeId=?1  AND  rp.id.processMonth=?2 and rp.companyId =?3 ")
	ReportPayOut findReportPayout(Long employeeId, String processMonth, Long companyId);

	@Query(" from ReportPayOut rp where rp.companyId=?1  AND  rp.id.processMonth=?3 AND  rp.departmentId=?2 ORDER BY  rp.dateOfJoining  ASC")
	List<ReportPayOut> findEmployeesPaysheetBydept(Long companyId, Long departmentId, String processMonth);

	String ReportPayoutListForRecordPayment = "SELECT rp.Name, rp.employeeCode,rp.bankName ,rp.accountNumber,rp.IFSCCode,rp.NetPayableAmount ,dept.departmentName , rp.employeeId\r\n"
			+ "	 FROM ReportPayOut  rp JOIN Department dept ON dept.departmentId = rp.departmentId  where"
			+ "   rp.processMonth=?2 and rp.companyId =?1 and rp.transactionNo is null and rp.transactionMode is null";

	@Query(value = ReportPayoutListForRecordPayment, nativeQuery = true)
	List<Object[]> findAllReportPayoutListForRecordPayment(Long companyId, String processMonth);

	String processMonthList = "select processMonth from ReportPayOut where employeeId=?1";

	@Query(value = processMonthList, nativeQuery = true)
	List<String> processMonthList(Long employeeId);

	// String UpdateReportPayoutforRecordPayment = "UPDATE `ReportPayOut` rp SET
	// rp.transactionNo =?2 ,processDate =?4 where employeeId =?1 and processMonth
	// =?3 AND companyId=?5";
	@Modifying
	@Query("UPDATE ReportPayOut rp SET transactionNo =?2 ,processDate=?5,transactionMode=?6,dateUpdate=?7 where employeeId =?1 and processMonth =?3 AND companyId=?4")
	int updateReportPayoutForRecordPayment(Long employeeId, String transctionNo, String processMonth, Long companyId,
			Date processDate, String transctionMode, Date date);

	@Query(" from ReportPayOut where employeeId=?1 AND transactionNo IS NULL order by month(str_to_date(SUBSTRING_INDEX(processMonth,'-',1),'%b')) ASC")
	public List<ReportPayOut> getSalary(Long employeeId);

//	String pendingSalary="SELECT processMonth,NetPayableAmount,payDays from ReportPayOut where employeeId=?1 AND transactionNo IS NULL";
//	@Query(value=pendingSalary,nativeQuery = true)
//	List<ReportPayOut> getSalary(Long employeeId);

	@Query("from ReportPayOut  where employeeId=?1 ")
	List<ReportPayOut> createSalary(Long employeeId);

	String employeeArrearCalculation = "SELECT  companyId,employeeId,processMonth,GrossSalary,TotalEarning,ProvidentFundEmployee,ProvidentFundEmployer,ProvidentFundEmployerPension ,isNoPFDeduction,isNoESIDeduction,ESI_Employee,pt FROM ReportPayOut where employeeId=?1 and processMonth=?2";

	@Query(value = employeeArrearCalculation, nativeQuery = true)
	List<Object[]> employeeArrearCalculationOnReportPayOut(Long employeeId, String processMonth);

//	String processMonthFromReportPayout = "select DISTINCT processMonth FROM ReportPayOut WHERE  companyId=?1";
//	@Query(value = processMonthFromReportPayout, nativeQuery = true )
//	List<String>  findPayrollMonthFromReportPayout(Long companyId);
//	

	// To get process month order wise
	String processMonthFromReportPayout = "CALL  	Pro_processMonth_by_reportPayout( :p_comp_id)";

	@Query(value = processMonthFromReportPayout, nativeQuery = true)
	public List<String> findPayrollMonthFromReportPayout(@Param(value = "p_comp_id") Long p_comp_id);

	String ReportPayOutProcessMonth = "select processMonth from PayrollControl where  "
			+ " financialYearId=?2 and processMonth IN (select DISTINCT processMonth FROM ReportPayOut WHERE  companyId=?1)";

	@Query(value = ReportPayOutProcessMonth, nativeQuery = true)
	List<String> getReportPayOutProcessMonthList(Long companyId, Long financialYearId);

	@Query(nativeQuery = true, value = "SELECT COUNT(1) FROM ReportPayOut rp JOIN Employee emp on rp.employeeId =emp.employeeId  WHERE  rp.processMonth=?1 AND rp.departmentId=?2 AND emp.activeStatus='"
			+ StatusMessage.ACTIVE_CODE + "'  ")
	Long checkPayRollForEmpMonth(String processMonth, Long departmentId);

//	@Query(nativeQuery = true,value = "SELECT COUNT(1) FROM Employee emp join PayStructureHd hd on hd.employeeId=emp.employeeId  WHERE hd.effectiveDate<=CURRENT_DATE() and  emp.departmentId =?1 AND emp.activeStatus='"+StatusMessage.ACTIVE_CODE+"'")
	@Query(nativeQuery = true, value = "SELECT COUNT(DISTINCT emp.employeeId) FROM Employee emp join PayStructureHd"
			+ " hd on hd.employeeId=emp.employeeId "
			+ " join Attendance atd on atd.employeeCode=emp.employeeCode WHERE hd.effectiveDate<=CURRENT_DATE()"
			+ " and atd.presense>0 and emp.departmentId =?1 AND emp.activeStatus='AC' ")
	Long checkPayRollForEmployeeTotal(Long departmentId);

//	@Query(nativeQuery = true,value = "SELECT emp.employeeId ,emp.employeeCode FROM Employee emp  WHERE  emp.departmentId =?1 AND emp.activeStatus='"+StatusMessage.ACTIVE_CODE+"' and emp.employeeId not in (SELECT rp.employeeId FROM ReportPayOut rp JOIN Employee emp on rp.employeeId =emp.employeeId  WHERE rp.departmentId=?1 AND rp.processMonth=?2   AND emp.activeStatus='"+StatusMessage.ACTIVE_CODE+"' ) ")

	@Query(nativeQuery = true, value = " SELECT emp.employeeId ,emp.employeeCode  " + "  	FROM Employee emp "
			+ "  	join PayStructureHd hd on hd.employeeId=emp.employeeId  "
			+ "    join Attendance atd on atd.employeeCode=emp.employeeCode "
			+ "     	WHERE  hd.effectiveDate<=CURRENT_DATE() and   emp.departmentId =?1 AND emp.activeStatus='"
			+ StatusMessage.ACTIVE_CODE
			+ "' and emp.employeeId not in (SELECT rp.employeeId FROM ReportPayOut rp JOIN Employee emp on rp.employeeId =emp.employeeId  WHERE rp.departmentId=?1 AND rp.processMonth=?2   AND emp.activeStatus='"
			+ StatusMessage.ACTIVE_CODE + "' ) " + "    	group by emp.employeeId")

	public List<Object[]> checkPayRollForEmployeeList(Long departmentId, String processMonth);

//	 @Modifying
//	 @Query( nativeQuery = true,value="UPDATE PayRegister pr SET pr.payrollLockFlag =1  where pr.employeeId =?1 and pr.payRegisterHd.processMonth =?2")
//	 public int updatePayrollLockFlagInPayRegister(Long employeeId, String processMonth);

	String month = "SELECT processMonth  FROM ReportPayOut WHERE employeeId =?1 AND  transactionNo is null";

	@Query(value = month, nativeQuery = true)
	public List<String> getReportPayOutMonth(Long employeeId);

	String recordPaymentUpdatedCount = "SELECT COUNT(rp.employeeId) FROM ReportPayOut rp  WHERE  rp.processMonth=?2 and rp.companyId=?1 and rp.transactionNo is not null and rp.transactionMode is not null";

	@Query(value = recordPaymentUpdatedCount, nativeQuery = true)
	public Long recordPaymentUpdatedCount(String companyId, String payrollMonth);

	String recordPaymentPendingCount = "SELECT COUNT(rp.employeeId) FROM ReportPayOut rp  WHERE  rp.processMonth=?2 and rp.companyId=?1 and rp.transactionNo is null and rp.transactionMode is null";

	@Query(value = recordPaymentPendingCount, nativeQuery = true)
	public Long recordPaymentPendingCount(String companyId, String payrollMonth);

//	 @Query(nativeQuery = true,value = "SELECT emp.employeeId ,emp.employeeCode FROM Employee emp  WHERE  emp.employeeId =?1 AND emp.activeStatus='AC' AND emp.employeeId NOT IN (SELECT rp.employeeId FROM ReportPayOut rp JOIN Employee emp on rp.employeeId =emp.employeeId  WHERE emp.activeStatus='AC'AND rp.processMonth=?2 AND rp.employeeId=?1 )")
//	 public List<Object[]>  notInReportEmployeeList(Long employeeId,String payMonth);

	String payOutInfo = "SELECT rp.processMonth,rp.NetPayableAmount from ReportPayOut rp JOIN PayRegister pr ON rp.employeeId=pr.employeeId WHERE pr.payrollLockFlag=0 and pr.employeeId=?1";

	@Query(value = payOutInfo, nativeQuery = true)
	public List<Object[]> payOutInfo(Long employeeId);

//	SELECT processMonth,NetPayableAmount  FROM ReportPayOut WHERE employeeId=1785 and processDate=( SELECT MAX(processDate) FROM ReportPayOut WHERE employeeId=1785)
	// String lastPaid = "SELECT processMonth,NetPayableAmount FROM ReportPayOut
	// WHERE employeeId = ?1 AND processDate = (SELECT MAX(processDate) FROM
	// ReportPayOut WHERE employeeId = ?1) AND transactionNo is NOT null";
	String lastPaid = "SELECT rp.processMonth,rp.NetPayableAmount  FROM ReportPayOut rp JOIN PayrollControl pc ON rp.processMonth=pc.processMonth WHERE employeeId = ?1 AND rp.dateUpdate = (SELECT MAX(dateUpdate)  FROM ReportPayOut WHERE employeeId = ?1) AND rp.transactionNo is NOT null  ORDER BY pc.controlId DESC LIMIT 1";

	@Query(value = lastPaid, nativeQuery = true)
	public List<Object[]> lastPaid(Long employeeId);

	// String LAST_6_MONTH_REPORTS=" SELECT em.processMonth ,em.dateCreated FROM
	// ReportPayOut em WHERE em.dateCreated > DATE_SUB(now(), INTERVAL 6 MONTH) AND
	// em.companyId=?1 AND em.employeeId=?2 order by
	// month(str_to_date(SUBSTRING_INDEX(em.processMonth,'-',1),'%b')) ASC LIMIT 6
	// ";

	String LAST_SIX_MONTH_PAYROLL_PROCESS = "CALL  pro_new_payroll_leave_ar( :p_company_id,:p_employeeId) ";

	@Query(value = LAST_SIX_MONTH_PAYROLL_PROCESS, nativeQuery = true)
	public List<Object[]> lastSixReports(@Param(value = "p_company_id") Long p_company_id,
			@Param(value = "p_employeeId") Long p_employeeId);

	String pfWorkingConsultantCount = " SELECT COUNT(rp.employeeId)as totalEmployee , COUNT(r.employeeId)as excludedemployee ,SUM(r.TotalEarning+r.otherEarning)as grossEmployeeSalary from ReportPayOut rp  LEFT JOIN ReportPayOut r ON rp.employeeId=r.employeeId  and  r.isNoPFDeduction='Y'  and r.processMonth=?1 \r\n"
			+ " Where rp.companyId=?2 and rp.processMonth=?1 ";

	@Query(value = pfWorkingConsultantCount, nativeQuery = true)
	List<Object[]> findPfWorkingConsultantReport(String processMonth, Long longcompanyId);

	@Query(" from ReportPayOut where employeeId=?1 AND processMonth=?2 ")
	ReportPayOut isPayrollProcessed(Long employeeId, String processMonth);

//	String EsicWorkingConsultantCount = " SELECT COUNT(rp.employeeId)as totalEmployee , COUNT(r.employeeId)as excludedemployee ,SUM(r.TotalEarning)as grossEmployeeSalary from ReportPayOut rp  LEFT JOIN ReportPayOut r ON rp.employeeId=r.employeeId  and  r.isNoESIDeduction='Y'  and r.processMonth=?1 \r\n"
//			+ " Where rp.companyId=?2 and rp.processMonth=?1 ";

	String EsicWorkingConsultantCount = "SELECT COUNT(rp.employeeId)as totalEmployee , COUNT(r.employeeId)as excludedemployee ,"
			+ " SUM(r.TotalEarning)as grossEmployeeSalary from ReportPayOut rp  LEFT JOIN ReportPayOut r ON rp.employeeId=r.employeeId  and "
			+ " ((r.isNoESIDeduction='Y' OR rp.ESI_Employee =0) )  and r.processMonth=?1 "
			+ "			 Where rp.companyId=?2 and rp.processMonth=?1 ";

	@Query(value = EsicWorkingConsultantCount, nativeQuery = true)
	List<Object[]> getEsicConsutantEmployeeCount(String processMonth, Long longcompanyId);

	@Query(value = "select pc.processMonth from PayrollControl pc  join FinancialYear fy on pc.financialYearId=fy.financialYearId WHERE fy.activeStatus='AC' and  pc.financialYearId=?1", nativeQuery = true)
	public List<String> getProcessMonthListByfinancialYear(Long financialYearId);

	// check for payroll already created or not for given month
	@Query(value = "SELECT rp.processMonth FROM ReportPayOut rp WHERE rp.companyId=:companyId AND rp.processMonth IN (:processMonth) ", nativeQuery = true)
	public List<String> payrollCreationCheck(@Param("companyId") Long companyId,
			@Param("processMonth") List<String> processMonth);

	@Query(" from ReportPayOut rp where rp.id.processMonth=?1 and rp.companyId =?2 ")
	List<ReportPayOut> findReportPayoutForMonth(String processMonth, Long companyId);

	String EsicWorkingConsultantCountMonthly = "SELECT COUNT(rp.employeeId)as totalEmployee , COUNT(r.employeeId)as excludedemployee ,SUM(r.TotalEarning+r.otherEarning)as grossEmployeeSalary, rp.processMonth from ReportPayOut rp  LEFT JOIN ReportPayOut r ON rp.employeeId=r.employeeId  and  r.isNoPFDeduction='Y' LEFT JOIN PayrollControl pc ON pc.processMonth=rp.processMonth "
			+ "Where rp.companyId=?2 AND pc.financialYearId=?1 GROUP BY rp.processMonth ";

	@Query(value = EsicWorkingConsultantCountMonthly, nativeQuery = true)
	List<Object[]> findPfWorkingConsultantReportMonthly(Long financialYearId, Long companyId);
    String EMPLOYEE_PF_INFO = " SELECT rp.Name, e.dateOfJoining, rp.epfJoining, rp.UANNO, rp.PFNumber FROM ReportPayOut rp JOIN Employee e ON e.employeeId=rp.employeeId WHERE e.companyId=?1 AND e.employeeId=?2 AND e.activeStatus=?3 GROUP BY e.employeeId ";

	@Query(value = EMPLOYEE_PF_INFO, nativeQuery = true)
	List<Object[]> findEmployeePFInfo(Long companyId, Long employeeId, String activeStatus);
	
	
	String EsicStatementAnnualSummery = "  SELECT   rp.processMonth, SUM(rp.GrossSalary),SUM(rp.ESI_Employee), SUM(rp.ESI_Employer),  ( SUM(rp.ESI_Employee) + SUM(rp.ESI_Employer)) as totalShare,  SUM(rp.absense) \r\n"
			+ "					 FROM ReportPayOut  rp    \r\n"
			+ "					  LEFT JOIN PayrollControl pc on pc.processMonth=rp.processMonth\r\n"
			+ "			         where  (rp.GrossSalary<21000 OR rp.isNoESIDeduction='N') AND  \r\n"
			+ "		             rp.companyId =?1  AND pc.financialYearId=?2\r\n"
			+ "					 and rp.ESI_Employee !=0 and rp.payableDays>0 AND rp.isNoESIDeduction='N' \r\n"
			+ "					     GROUP by rp.processMonth  order BY pc.controlId";

	@Query(value = EsicStatementAnnualSummery, nativeQuery = true)
	List<Object[]> findEsicStatementAnnualSummery(Long companyId, Long financialYearId);

	String EsicStatementAnnualMonthly = "SELECT  rp.ESICNumber , rp .Name, rp.GrossSalary,rp.ESI_Employee ,rp.ESI_Employer, rp.absense, rp.processMonth\r\n"
			+ "				FROM ReportPayOut  rp   \r\n"
			+ "				 LEFT JOIN PayrollControl pc on pc.processMonth=rp.processMonth\r\n"
			+ "	    where  (rp.GrossSalary<21000 OR rp.isNoESIDeduction='N') AND \r\n"
			+ "		rp.companyId =?1  AND pc.financialYearId=?2\r\n"
			+ "		and rp.ESI_Employee !=0 and rp.payableDays>0 AND rp.isNoESIDeduction='N'\r\n"
			+ "		 order by pc.controlId";

	@Query(value = EsicStatementAnnualMonthly, nativeQuery = true)
	List<Object[]> findEsicStatementAnnualMonthly(Long companyId, Long financialYearId);

	
	
	String EsicStatementEmployeeWise = "	SELECT  rp.processMonth,  rp.GrossSalary, rp.ESI_Employee ,rp.ESI_Employer,   (rp.ESI_Employee + rp.ESI_Employer), rp.absense\r\n"
			+ "		FROM ReportPayOut  rp  \r\n"
			+ "		 LEFT JOIN PayrollControl pc on pc.processMonth=rp.processMonth\r\n"
			+ "	    where  (rp.GrossSalary<21000 OR rp.isNoESIDeduction='N') AND \r\n"
			+ "		 rp.companyId =?1  and rp.employeeId=?2 \r\n"
			+ "		and rp.ESI_Employee !=0 and rp.payableDays>0 AND rp.isNoESIDeduction='N'\r\n"
			+ "		  order by pc.controlId";

	@Query(value = EsicStatementEmployeeWise, nativeQuery = true)
	List<Object[]> findEsicStatementEmployeeWise(Long companyId, Long employeeId);

	String EmployeeEsic = "SELECT  rp.Name, rp.dateOfJoining , rp.dateCreated,rp.ESICNumber from ReportPayOut rp "
			+ "  LEFT JOIN ReportPayOut r ON rp.employeeId=r.employeeId  \r\n" + 
			"  AND  r.isNoESIDeduction='Y' LEFT JOIN PayrollControl pc ON pc.processMonth=rp.processMonth \r\n" + 
			"			Where rp.companyId=?1 AND rp.employeeId=?2 GROUP BY rp.employeeId";

	@Query(value = EmployeeEsic, nativeQuery = true)
	List<Object[]> findEmployeeEsic(Long companyId, Long employeeId);

	
	
	String payRollOverview = "SELECT count(DISTINCT e.employeeId)+(SELECT COUNT( DISTINCT emp.employeeId) FROM Employee emp  WHERE emp.endDate BETWEEN ?3 and ?4 and emp.activeStatus='DE') empCount, (SELECT count(rp.employeeId) FROM  ReportPayOut rp  WHERE rp.processMonth=?2) as payrollProcessed "
			+ "    FROM Employee e  \r\n" + 
			"		   WHERE e.dateOfJoining<=?4 and e.activeStatus='AC' and  e.companyId=?1 ";
	@Query(value = payRollOverview, nativeQuery = true)
	List<Object[]> getPayRollOverview(Long companyId, String processMonth, Date fromDate, Date toDate);

	String payRollOverviewCTC = "SELECT SUM(rp.TotalEarning)+sum(rp.otherEarning) as earnedGross, SUM(rp.TotalDeduction),SUM(rp.NetPayableAmount),SUM(IFNULL(rp.employerWelFareAmount,0)+ IFNULL(rp.otherEarning,0) + IFNULL(rp.BasicEarning, 0)+ IFNULL(rp.dearnessAllowanceEarning, 0)+ \r\n" + 
			"			  IFNULL(rp.ConveyanceAllowanceEarning, 0)+IFNULL(rp.HRAEarning, 0)+IFNULL(rp.MedicalAllowanceEarning, 0)+IFNULL(rp.AdvanceBonusEarning, 0)+ \r\n" + 
			"			 IFNULL(rp.SpecialAllowanceEarning,0)+IFNULL(rp.leaveTravelAllowanceEarning,0)+IFNULL(rp.performanceLinkedIncomeEarning,0)+ \r\n" + 
			"			  IFNULL(rp.uniformAllowanceEarning,0)+IFNULL(rp.CompanyBenefitsEarning,0)+IFNULL(rp.ProvidentFundEmployer,0)+IFNULL(rp.ProvidentFundEmployerPension,0)+ \r\n" + 
			"			  IFNULL(rp.ESI_Employer,0)) as CTC   from ReportPayOut rp WHERE rp.processMonth=?2 and rp.companyId=?1 ";
	@Query(value = payRollOverviewCTC, nativeQuery = true)
	List<Object[]> getPayRollOverviewCTC(Long companyId, String processMonth);
	
	String payHeadList = "SELECT Concat(ph.payHeadName, '-', ph.payHeadId) FROM PayHeads ph WHERE ph.payHeadFlag='N' and ph.earningDeduction=?2 and ph.companyId=?1";
	@Query(value = payHeadList, nativeQuery = true)
	List<String> getPayHeadList(Long companyId, String status);

	// check for payroll already created or not for given month
		@Query(value = "SELECT * FROM ReportPayOut rp WHERE rp.companyId=:companyId AND rp.employeeId=:employeeId AND rp.processMonth IN (:processMonth) ", nativeQuery = true)
		public List<ReportPayOut> payrollCreationCheckObj(@Param("companyId") Long companyId,@Param("employeeId") Long employeeId,
				@Param("processMonth") List<String> processMonth);
}
