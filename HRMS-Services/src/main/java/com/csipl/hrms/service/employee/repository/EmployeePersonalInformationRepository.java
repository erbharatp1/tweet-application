package com.csipl.hrms.service.employee.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.csipl.hrms.model.employee.Employee;
import com.csipl.hrms.model.employee.EmployeeBulkUploadMaster;
import com.csipl.hrms.model.employee.EmployeeLanguage;

public interface EmployeePersonalInformationRepository extends CrudRepository<Employee, Long> {
//	String employeeLovSearch="SELECT emp.employeeId,emp.firstName,emp.lastName,emp.employeeCode,emp.employeeLogoPath ,dept.departmentId, dept.departmentName, desg.designationId,desg.designationName FROM Employee emp JOIN Department dept on dept.departmentId = emp.departmentId JOIN Designation desg ON desg.designationId= emp.designationId where emp.companyId=?1 and emp.activeStatus=?2 ORDER BY emp.employeeId DESC";
     
String employeeLovSearch=	"SELECT emp.employeeId,emp.firstName,emp.lastName,emp.employeeCode,emp.employeeLogoPath ,dept.departmentId, dept.departmentName, desg.designationId,desg.designationName FROM Employee emp JOIN Department dept on dept.departmentId = emp.departmentId JOIN Designation desg ON desg.designationId= emp.designationId where emp.companyId=?1 and emp.activeStatus=?2 AND emp.endDate is null ORDER BY emp.employeeId DESC";
	
	//String employeeLovSearch="SELECT emp.employeeId,emp.firstName,emp.lastName,emp.employeeCode,emp.employeeLogoPath ,dept.departmentId, dept.departmentName, desg.designationId,desg.designationName FROM Employee emp JOIN Department dept on dept.departmentId = emp.departmentId JOIN Designation desg ON desg.designationId= emp.designationId where emp.companyId=1 and emp.activeStatus='AC' AND emp.endDate is null OR emp.endDate <= CURRENT_DATE ORDER BY emp.employeeId DESC";	
	@Query(value = employeeLovSearch, nativeQuery = true)
	public List<Object[]> findAllEmployees(long companyId,String status);

	String employeeSearchNotInReport="SELECT emp.employeeId,emp.firstName,emp.lastName ,emp.employeeCode,dept.departmentName FROM Employee emp JOIN Department dept on dept.departmentId=emp.departmentId WHERE  emp.companyId=?1 and emp.activeStatus='AC' and emp.employeeId not in (SELECT rp.employeeId FROM ReportPayOut rp   WHERE rp.processMonth=?2 AND emp.activeStatus='AC' )";
	
	@Query(value = employeeSearchNotInReport, nativeQuery = true)
	public List<Object[]> findAllEmployeesNotInReport(long companyId,String processMonth);
	
	
String employeeNotInReport="SELECT emp.employeeId,emp.firstName,emp.lastName ,emp.employeeCode,dept.departmentName FROM Employee emp JOIN Department dept on dept.departmentId=emp.departmentId WHERE  emp.companyId=?1 and emp.activeStatus='AC' and emp.employeeId not in (SELECT rp.employeeId FROM ReportPayOut rp  WHERE rp.processMonth=?2 AND emp.activeStatus='AC') and (MONTH(emp.dateOfJoining) != MONTH(CURRENT_DATE) OR YEAR(emp.dateOfJoining) != YEAR(CURRENT_DATE))";
	
	@Query(value = employeeNotInReport, nativeQuery = true)
	public List<Object[]> findAllEmployeeNotInReport(long companyId,String processMonth);
	
	@Query(" from Employee where employeeCode=?1 and activeStatus='AC' and companyId=?2")
	public Employee findEmployees(String employeeCode, Long companyId);

	@Query("from Employee where companyId=?1 and activeStatus='AC' and departmentId=?2 ORDER BY  employeeId  DESC ")
	public List<Employee> findAllEmpByDeptId(Long companyId, Long departmentId);

	@Query("from Employee where companyId=?1 and activeStatus='AC' and departmentId IN ?2 group by employeeId ORDER BY  employeeId  DESC ")
	public List<Employee> findAllEmpByDeptIdList(Long companyId, List<Long> departmentIds);
	
	@Query("from Employee where companyId=?1 and activeStatus='DE' ORDER BY  employeeId  DESC ")
	public List<Employee> findAllDeactivateEmployees(Long companyId);

//	String employeeLOV = "SELECT emp.employeeId,emp.employeeCode, emp.firstName,emp.lastName  "
//			+ " from Employee emp where" + " emp.companyId=?1 and emp.activeStatus ='AC'";

	String employeeLOV = "SELECT emp.employeeId,emp.employeeCode, emp.firstName,emp.lastName ,emp.biometricId " 
 			+ " from Employee emp where" + " emp.companyId=?1 and emp.activeStatus ='AC' AND emp.endDate is null OR emp.endDate <= CURRENT_DATE";
	@Query(value = employeeLOV, nativeQuery = true)
	public List<Object[]> fetchEmployee(long companyId);

	String employeeByIdLOV = "SELECT * from Employee emp where emp.employeeId=?1 and emp.activeStatus ='AC' ";

	@Query(value = employeeByIdLOV, nativeQuery = true)
	public Employee fetchEmployeeByEmployeeId(long employeeId);

	String employeeDepartmentLOV = "SELECT emp.employeeId,emp.employeeCode, emp.firstName,emp.lastName,emp.biometricId,emp.dateOfJoining \r\n"
			+ "  from Employee emp where emp.companyId=?1 and emp.activeStatus ='AC'  AND emp.departmentId=?2  AND (emp.endDate is null OR emp.endDate <= CURRENT_DATE)";

	@Query(value = employeeDepartmentLOV, nativeQuery = true)
	public List<Object[]> fetchEmployeeDepartmentWise(long companyId, long deptId);

	String employeeSearch = "SELECT emp.employeeId, emp.employeeCode, emp.firstName, emp.lastName, "
			+ "gd.gradesName, dept.departmentName,  design.designationName, emp.dateOfJoining  emp.endDate"
			+ " FROM  Employee emp LEFT JOIN PayStructureHd ph ON  ph.employeeId = emp.employeeId AND ( "
			+ " ph.dateEnd IS NULL OR ph.dateEnd >= CURRENT_DATE ) AND ( ph.effectiveDate IS NOT NULL AND ph.effectiveDate <= CURRENT_DATE "
			+ ") AND ph.activeStatus = 'AC' LEFT JOIN Grades gd ON gd.gradesId = ph.gradesId LEFT JOIN Department dept ON   emp.departmentId = dept.departmentId "
			+ " LEFT JOIN Designation design ON   emp.designationId = design.designationId   WHERE emp.companyId =?1 AND emp.activeStatus = 'AC' order by emp.employeeId desc ";

	@Query(value = employeeSearch, nativeQuery = true)
	public List<Object[]> searchEmployee(long companyId);

	@Query("SELECT COUNT(1) from Employee where adharNumber=?1 AND activeStatus=?2 AND employeeId NOT IN (?3)")
	public Long aadharCheck(String adharNumber, String activeStatus, Long employeeId);

	@Query("SELECT COUNT(1) from Employee where adharNumber=?1 AND activeStatus=?2")
	public Long aadharCheck(String adharNumber, String activeStatus);

	@Query(" SELECT count(*) from Employee  where companyId=?1 AND endDate IS NULL AND activeStatus = 'AC'")
	public int employeeSearch(Long companyId);

	@Query(" SELECT count(*) from Employee  where companyId=?1 AND endDate IS NULL AND tdsStatus IS NOT NULL AND activeStatus = 'AC'")
	public int employeeSearchWithTdsStatus(Long companyId);

	@Query(" SELECT count(*) from Employee where companyId=?1 AND activeStatus = 'DE'")
	public int employeeSearchDE(Long companyId);

	@Query(" SELECT count(*) from Employee where companyId=?1 AND endDate!='' AND activeStatus = 'AC'")
	public int getEmployeeSeparatingCount(Long companyId);

	String filterEmployee = "SELECT emp.employeeId, emp.employeeCode, emp.firstName, emp.lastName, "
			+ "gd.gradesName, dept.departmentName,  design.designationName, emp.dateOfJoining  emp.endDate"
			+ " FROM  Employee emp LEFT JOIN PayStructureHd ph ON  ph.employeeId = emp.employeeId AND ( "
			+ " ph.dateEnd IS NULL OR ph.dateEnd > CURRENT_DATE ) AND ( ph.effectiveDate IS NOT NULL AND ph.effectiveDate <= CURRENT_DATE "
			+ ") AND ph.activeStatus = 'AC' LEFT JOIN Grades gd ON gd.gradesId = ph.gradesId LEFT JOIN Department dept ON   emp.departmentId = dept.departmentId "
			+ " LEFT JOIN Designation design ON   emp.designationId = design.designationId   WHERE emp.firstName=?1 AND emp.companyId =?2 AND emp.activeStatus = 'AC' order by emp.employeeId desc ";

	@Query(value = filterEmployee, nativeQuery = true)
	public List<Object[]> findPagedResultFilterEmployee(String firstName, long companyId);

	@Query("FROM Employee emp where companyId=?1 and MONTH( emp.dateOfBirth ) = MONTH(NOW()) and DAY(emp.dateOfBirth) = DAY(NOW()) and activeStatus='AC'")
	public List<Employee> findBirthDayEmployees(Long companyId);

	@Query("FROM Employee emp where companyId=?1 and MONTH( emp.anniversaryDate ) = MONTH(NOW()) and DAY(emp.anniversaryDate) = DAY(NOW()) and activeStatus='AC'")
	public List<Employee> findAnniversaryEmployees(Long companyId);

	@Query("FROM Employee emp where companyId=?1 and MONTH(emp.dateOfJoining) = MONTH(NOW()) and DAY(emp.dateOfJoining) = DAY(NOW()) and activeStatus='AC'")
	public List<Employee> findJoiningAnniversaryEmployees(Long companyId);

	@Query(value = orgTree, nativeQuery = true)
	public List<Object[]> orgHierarchyList(Long employeeId);

	String orgTree = "SELECT  employee.employeeId, employee.firstName,employee.lastName,employee.employeeCode,employee.employeeLogoPath,manager.employeeId as managerId,manager.firstName as managerFirstName,manager.lastName as managerLastName ,dept.departmentName FROM  Employee employee LEFT JOIN Employee manager ON  employee.ReportingToEmployee = manager.employeeId JOIN Department dept ON employee.departmentId=dept.departmentId WHERE manager.employeeId =?1";

	@Modifying
	@Query("UPDATE Employee SET employeeLogoPath=?1 WHERE employeeId=?2")
	void saveCandidateImage(String dbPath, Long candidateId);

	String onBoardProgessBar = "SELECT  emp.employeeId AS empId, eIdProofs.employeeId AS eIdProofs, pInformation.employeeId pInformation, eEducation.employeeId AS eEducation, eFamily.employeeId AS eFamily, eStatuary.employeeId AS eStatutory,emp.employeeId AS employeeId,eBank.employeeId AS eBank FROM Employee emp LEFT JOIN EmployeeStatuary eStatuary ON eStatuary.employeeId = emp.employeeId LEFT JOIN EmployeeIdProofs eIdProofs ON eIdProofs.employeeId = emp.employeeId LEFT JOIN ProfessionalInformation pInformation ON  pInformation.employeeId = emp.employeeId LEFT JOIN EmployeeEducation eEducation ON  eEducation.employeeId = emp.employeeId  LEFT JOIN EmployeeFamily eFamily ON  eFamily.employeeId = emp.employeeId LEFT JOIN EmployeeBank eBank ON  eBank.employeeId = emp.employeeId WHERE emp.employeeId =?1   GROUP BY emp.employeeId";

	@Query(value = onBoardProgessBar, nativeQuery = true)
	List<Object[]> getOnBoardProgessBar(Long candidateId);

	String shiftName = "SELECT ts.shiftId, ts.shiftFName FROM Employee e JOIN TMSShift ts ON e.shiftId=ts.shiftId WHERE e.employeeId=?1";

	@Query(value = shiftName, nativeQuery = true)
	public List<Object[]> getEmpShiftName(Long employeeId);

	String reportingTo = "SELECT b.employeeId,b.employeeCode,b.officialEmail,b.contactNo, UPPER(concat(concat(b.firstName,' '),b.lastName)) as empname FROM Employee a join Employee b on b.employeeId=a.ReportingToEmployee WHERE a.employeeId=?1";

	@Query(value = reportingTo, nativeQuery = true)
	public List<Object[]> getEmpReportingToEmail(Long employeeId);

	@Query(" from Employee emp  where emp.company.companyId=?1 and emp.activeStatus='AC'  and emp.employeeId  in (select rp.employee.employeeId from ReportPayOut rp where rp.id.processMonth=?2 ) ")
	public List<Employee> getEmployeeInPayroll(Long companyId, String payrollMonth);

	/**
	 * 
	 */
	String employeeLovSearchForSeparationLOV = "SELECT emp.employeeId,emp.firstName,emp.lastName,emp.employeeCode,emp.employeeLogoPath ,dept.departmentId, dept.departmentName, desg.designationId,desg.designationName FROM Employee emp JOIN Department dept on dept.departmentId = emp.departmentId JOIN Designation desg ON desg.designationId= emp.designationId where emp.companyId=?1 and emp.activeStatus=?2 AND emp.endDate is null ORDER BY emp.employeeId DESC";

	@Query(value = employeeLovSearchForSeparationLOV, nativeQuery = true)
	public List<Object[]> findAllPersonalInformationDetailsForSeparationLOV(Long companyId, String status);

	@Query("FROM Employee emp where companyId=?1 AND emp.endDate IS NULL")
	public List<Employee> getAllActiveEmployee(Long companyId);

	@Query("SELECT COUNT(adharNumber) FROM Employee where adharNumber=?1")
	public int adharCheck(String adharNumber);

	@Query("SELECT COUNT(employeeCode) FROM Employee where employeeCode=?1")
	public int employeeCodeCheck(String employeeCode);

	@Query("SELECT employeeCode FROM Employee WHERE employeeId=?1")
	public String getEmployeeCode(Long employeeId);

	@Query("SELECT COUNT(accountNumber) FROM EmployeeBank where accountNumber=?1")
	public int bankAccountNumber(String bankAccountNumber);

	@Query(" from EmployeeLanguage where employeeId=?1 and languageId=?2  ")
	public EmployeeLanguage findEmployeeLanguage(Long employeeId, Long langType);

	@Query(" from EmployeeBulkUploadMaster where fileCode=?1 ORDER BY  indexNumber  ASC")
	public List<EmployeeBulkUploadMaster> findHeaderName(String fileCode);

	@Query("  from Employee where companyId =?1 ORDER BY employeeId ASC")
	public List<Employee> findAllEmployee(Long companyID);

//		@Query(value="UPDATE Employee SET activeStatus = 'DE' WHERE employeeId = ?1", nativeQuery = true)
//		public int changeEmployeeStatus(Long employeeId);

	@Modifying
	@Query(value = "UPDATE Employee SET activeStatus = 'DE' WHERE employeeId =:employeeId", nativeQuery = true)
	public int changeEmployeeStatus(@Param("employeeId") Long employeeId);

//		String teamHirarchy="SELECT e1.employeeId,CONCAT (e1.firstName,' ', e1.lastName), des.designationName,e1.employeeLogoPath, "
//				+ " GROUP_CONCAT(e2.employeeId,',',CONCAT (e2.firstName,' ', e2.lastName),',',des.designationName,',', IFNULL( e2.employeeLogoPath, 'null'), '-') as employeeName, "
//				+ " e2.ReportingToEmployee, COUNT(*) FROM Employee e1 INNER JOIN Employee e2 on e1.employeeId=e2.ReportingToEmployee " + 
//				" LEFT JOIN Designation des ON des.designationId = e1.designationId where e1.companyId=?1 and e1.activeStatus='AC' " + 
//				" Group by e1.employeeId  " + 
//				" ORDER BY e2.ReportingToEmployee ASC";
//		@Query(value=teamHirarchy, nativeQuery = true)
//		public List<Object[]> getTeamHirarchy(Long companyId);

	String teamHirarchy = "SELECT e1.employeeId, CONCAT (e1.firstName,' ', e1.lastName), des.designationName, e1.employeeLogoPath,"
			+ " e1.ReportingToEmployee, dept.departmentName from Employee e1 "
			+ " LEFT JOIN Designation des ON des.designationId = e1.designationId  left JOIN Department dept ON dept.departmentId=e1.departmentId"
			+ "   where e1.companyId=?1  and (e1.employeeId=?2 or e1.ReportingToEmployee=?2)  and e1.activeStatus='AC'";

	@Query(value = teamHirarchy, nativeQuery = true)
	public List<Object[]> getTeamHirarchy(Long companyId, Long employeeId);

	String teamHirarchys="SELECT e1.employeeId, CONCAT (e1.firstName,' ', e1.lastName), des.designationName, e1.employeeLogoPath,"
			+ " e1.ReportingToEmployee, dept.departmentName from Employee e1 " 
	+" LEFT JOIN Designation des ON des.designationId = e1.designationId  left JOIN Department dept ON dept.departmentId=e1.departmentId"
	+ "   where e1.companyId=?1 and e1.activeStatus='AC' ";
	@Query(value=teamHirarchys, nativeQuery = true)
	public List<Object[]> getTeamHirarchy(Long companyId);
	
	@Query(value = "Select emp.employeeId from Employee emp where emp.activeStatus ='AC' and emp.companyId =?1", nativeQuery = true)
	public List<Long> getEmployeeIdList(Long companyId);

	String employeeLifeCycle = "SELECT e1.employeeId, CONCAT (e1.firstName,' ', e1.lastName), des.designationName,  dept.departmentName , e1.dateUpdate"
			+ " from EmployeeLifeCycleHistory e1 "
			+ "	 LEFT JOIN Designation des ON des.designationId = e1.designationId  left JOIN Department dept ON dept.departmentId=e1.departmentId "
			+ "   where e1.companyId=?1 and e1.employeeId=?2 and e1.activeStatus=?3 ";


		
		@Query(value=employeeLifeCycle, nativeQuery = true)
		public List<Object[]> getEmployeeLifeCycleData(Long companyId, Long employeeId, String status);
		
		public static final String PROBATION_PERIOD_EMP_LIST="SELECT concat( e.firstName,' ',e.lastName) as 'empName ',e.employeeCode,dept.departmentName,e.dateOfJoining , DATE_ADD(e.dateOfJoining, INTERVAL e.probationDays DAY) as 'probationDate'FROM Employee e JOIN Department dept ON e.departmentId=dept.departmentId WHERE  e.companyId=?1 AND DATE_ADD(e.dateOfJoining, INTERVAL e.probationDays DAY) =CURRENT_DATE AND e.activeStatus='AC' GROUP by e.employeeCode";
	 
		@Query(value=PROBATION_PERIOD_EMP_LIST, nativeQuery = true)
		public List<Object[]> getEmployeeListPrabationPeriod(Long companyId );

		
		public static final String ACCIDENTAL_INSURANCE_EXPIRED_EMP_LIST="SELECT concat( e.firstName,' ',e.lastName) as 'empName ',e.employeeCode,dept.departmentName,  es.dateFrom  ,es.dateTo ,es.statuaryNumber as 'policyNo' FROM Employee e   JOIN EmployeeStatuary es on es.employeeId=e.employeeId JOIN Department dept ON e.departmentId=dept.departmentId WHERE  e.companyId=?1 AND es.dateTo=?2 AND es.statuaryType='AC' AND es.status='AC' AND e.activeStatus='AC' GROUP by e.employeeCode";
		 
		@Query(value=ACCIDENTAL_INSURANCE_EXPIRED_EMP_LIST, nativeQuery = true)
		public List<Object[]> getEmployeeListAccidentalInsurance(Long companyId ,String currentDate);

		
		public static final String MEDICAL_INSURANCE_EXPIRED_EMP_LIST="SELECT concat( e.firstName,' ',e.lastName) as 'empName ',e.employeeCode,dept.departmentName,  es.dateFrom  ,es.dateTo ,es.statuaryNumber as 'policyNo' FROM Employee e   JOIN EmployeeStatuary es on es.employeeId=e.employeeId JOIN Department dept ON e.departmentId=dept.departmentId WHERE  e.companyId=?1 AND es.dateTo=?2 AND es.statuaryType='ME' AND es.status='AC' AND e.activeStatus='AC' GROUP by e.employeeCode";
		 
		@Query(value=MEDICAL_INSURANCE_EXPIRED_EMP_LIST, nativeQuery = true)
		public List<Object[]> getEmployeeListMedicalInsurance(Long companyId ,String currentDate);
	
		//public static final String PROBATION_PERIOD_ABOUT_EMP_LIST="SELECT concat( e.firstName,' ',e.lastName) as 'empName ',e.employeeCode,dept.departmentName,e.dateOfJoining ,DATE_ADD(e.dateOfJoining, INTERVAL e.probationDays DAY) as 'probationDate' , e.probationDays, DATE_ADD(CURDATE(), INTERVAL  -2 DAY) as 'privousDadte'FROM Employee e   JOIN Department dept ON e.departmentId=dept.departmentId WHERE  e.companyId=?1 AND e.activeStatus='AC' \r\n" + 
		//		"HAVING DATE_ADD(e.dateOfJoining, INTERVAL e.probationDays DAY) BETWEEN CURDATE() AND DATE_ADD(CURDATE(), INTERVAL 2 DAY)";
		public static final String PROBATION_PERIOD_ABOUT_EMP_LIST="SELECT concat( e.firstName,' ',e.lastName) as 'empName ',e.employeeCode,dept.departmentName,e.dateOfJoining ,DATE_ADD(e.dateOfJoining, INTERVAL e.probationDays DAY) as 'probationDate' , e.probationDays, DATE_ADD(e.dateOfJoining, INTERVAL  (e.probationDays-2) DAY) as 'privousDate'FROM Employee e   JOIN Department dept ON e.departmentId=dept.departmentId WHERE  e.companyId=?1 AND e.activeStatus='AC' HAVING DATE_ADD(e.dateOfJoining, INTERVAL e.probationDays DAY) BETWEEN CURDATE() AND DATE_ADD(CURDATE(), INTERVAL 2 DAY)";
		@Query(value=PROBATION_PERIOD_ABOUT_EMP_LIST, nativeQuery = true)
		public List<Object[]> getEmployeeListPrabationAboutPeriod(Long companyId);

		
		public static final String CONTRACT_EXPIRED_EMP_LIST="SELECT concat( e.firstName,' ',e.lastName) as 'empName ',e.employeeCode,dept.departmentName,e.dateOfJoining,e.contractStartDate,e.contractOverDate FROM Employee e   JOIN Department dept ON e.departmentId=dept.departmentId WHERE  e.companyId=?1 AND e.activeStatus='AC' AND e.contractOverDate=CURDATE() GROUP by e.employeeCode";
		 
		@Query(value=CONTRACT_EXPIRED_EMP_LIST, nativeQuery = true)
		public List<Object[]> getEmployeeListContractExpired(Long companyId);

		public static final String CONTRACT_EXPIRED_ABOUT_EMP_LIST="SELECT concat( e.firstName,' ',e.lastName) as 'empName ',e.employeeCode,dept.departmentName,e.dateOfJoining,e.contractStartDate,e.contractOverDate, DATE_SUB(e.contractOverDate, INTERVAL 2 DAY) as newDate FROM Employee e JOIN Department dept ON e.departmentId=dept.departmentId WHERE e.companyId=?1 AND e.activeStatus='AC' AND DATE_SUB(e.contractOverDate, INTERVAL 2 DAY)=CURRENT_DATE GROUP by e.employeeCode";
		 
		@Query(value=CONTRACT_EXPIRED_ABOUT_EMP_LIST, nativeQuery = true)
		public List<Object[]> getEmployeeListContractAboutExpired(Long companyId);

		public static final String ACCIDENTAL_INSURANCE_ABOUT_EXPIRED_EMP_LIST="SELECT concat( e.firstName,' ',e.lastName) as 'empName ',e.employeeCode ,dept.departmentName,es.dateFrom,es.dateTo,es.statuaryNumber, DATE_SUB(es.dateTo, INTERVAL 2 DAY) as newDate FROM Employee e join EmployeeStatuary es on e.employeeId=es.employeeId JOIN Department dept ON e.departmentId=dept.departmentId WHERE e.companyId=?1 AND e.activeStatus='AC' AND es.statuaryType='AC' AND DATE_SUB(es.dateTo, INTERVAL 2 DAY)=CURRENT_DATE GROUP by e.employeeCode";
		 
		@Query(value=ACCIDENTAL_INSURANCE_ABOUT_EXPIRED_EMP_LIST, nativeQuery = true)
		public List<Object[]> getEmployeeListAccidentalInsuranceAbout(Long companyId);

		public static final String MEDICAL_INSURANCE_ABOUT_EXPIRED_EMP_LIST="SELECT concat( e.firstName,' ',e.lastName) as 'empName ',e.employeeCode ,dept.departmentName,es.dateFrom,es.dateTo,es.statuaryNumber, DATE_SUB(es.dateTo, INTERVAL 2 DAY) as newDate FROM Employee e join EmployeeStatuary es on e.employeeId=es.employeeId JOIN Department dept ON e.departmentId=dept.departmentId WHERE e.companyId=?1 AND e.activeStatus='AC' AND es.statuaryType='ME' AND DATE_SUB(es.dateTo, INTERVAL 2 DAY)=CURRENT_DATE GROUP by e.employeeCode";
		 
		@Query(value=MEDICAL_INSURANCE_ABOUT_EXPIRED_EMP_LIST, nativeQuery = true)
		public List<Object[]> getEmployeeListMedicalInsuranceAbout(Long companyId);
		
		String employeeStatus=	"SELECT emp.employeeId,emp.firstName,emp.lastName,emp.employeeCode,emp.employeeLogoPath ,dept.departmentId, dept.departmentName, desg.designationId,desg.designationName FROM Employee emp JOIN Department dept on dept.departmentId = emp.departmentId JOIN Designation desg ON desg.designationId= emp.designationId where emp.companyId=?1 ORDER BY emp.employeeId DESC";
		@Query(value = employeeStatus, nativeQuery = true)
		public List<Object[]> findAllEmployeeByStatus(Long companyId);


		
		String employeeDepartment = "SELECT emp.employeeId,emp.employeeCode, emp.firstName,emp.lastName,emp.biometricId,emp.dateOfJoining \r\n"
				+ "  from Employee emp  LEFT JOIN Separation s on s.employeeId=emp.employeeId and s.status='PEN'  "
				+ " where emp.companyId=?1 and emp.activeStatus ='AC'  AND emp.departmentId=?2  AND (emp.endDate is null OR emp.endDate <= CURRENT_DATE)";

		@Query(value = employeeDepartment, nativeQuery = true)
		public List<Object[]> fetchEmployeeFromDepartmentAndSeparation(Long companyId, Long deptId);

	

		@Query("from Employee where companyId=?1 and activeStatus='AC' ORDER BY  employeeId  DESC ")
		public List<Employee> findAllActiveEmployees(Long companyId);

		

	 
		@Query("from Employee where employeeId=?1 and activeStatus='AC' ORDER BY  employeeId  DESC")
		public Employee fetchEmployeeByIdCode(Long employeeId);
		
		@Modifying
		@Query("UPDATE Employee SET tdsPlanType=?1 WHERE employeeId=?2")
		void updateTdsPlanTypeStatus(String status, Long employeeId);

		@Modifying
		@Query("UPDATE Employee SET tdsPlanType=?1 WHERE employeeId IN ?2")
		void updateTdsPlanTypeStatus(String status, List<Long> employeeId);
		
		@Query("SELECT tdsPlanType from Employee where employeeId=?1 ")
		public String getPlanTypeStatus(Long employeeId);

		@Query("from Employee where companyId=?1 and activeStatus='AC' and designationId=?2 and departmentId=?3 ORDER BY  employeeId  DESC ")
		public List<Employee> getEmployeeOnDesignationId(Long companyId, Long designationId, Long departmentId);
}

