package com.csipl.hrms.service.report.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.csipl.hrms.model.employee.Employee;

public interface EmployeeReportRepository extends CrudRepository<Employee, Long> {

	String countEMPIMPTODAYDATE = "CALL  pro_emp_count_todaydate( :p_comp_id,:p_flag)";

	String fetchAnniversayEmpList = "CALL  pro_emp_count_todaydate( :p_comp_id,:p_flag)";

	String fetchBirthDayEmpList = "CALL  pro_emp_count_todaydate( :p_comp_id,:p_flag)";

	String fetchWorkAnniversayEmpList = "CALL  pro_emp_count_todaydate( :p_comp_id,:p_flag)";

	String fetchHolidayListByMonth = "CALL  pro_emp_count_todaydate( :p_comp_id,:p_flag)";

	String fetchDesignationWiseCTC = "CALL  pro_designation_wise_ctc( :p_comp_id,:p_process_month)";

	String fetchHeadCountByBankPay = "CALL  pro_headcount_by_bankpay( :p_comp_id)";

	String empPayrollstatusbyMonth = "CALL  pro_emp_payrollmonth_status( :p_comp_id)";

	String empTicketStatus = "CALL  pro_emp_ticket_status(:p_comp_id,:p_user_id, :roleName)";

	String empTicketStatusWithMonth = "CALL  pro_emp_ticket_status(:p_comp_id, :lastMonth)";

	String fetchEmpPfContribution = "CALL  	pro_pf_contribution( :p_comp_id,:p_process_month)";

	String fetchEmpESIContribution = "CALL  	pro_esi_contribution( :p_comp_id,:p_process_month)";

	String fetchEmployeeDocumentConfirmation = "CALL  	pro_emp_doc_confirmation( :p_comp_id)";

	String countNotification = "CALL  	pro_emp_notification( :p_comp_id ,:p_flag)";

	String empNotification = "CALL  	pro_emp_notification( :p_comp_id ,:p_flag)";

	String fetchEmployeeSeprationInfo = "CALL  	pro_emp_sepration_status( :p_comp_id) ";

	String empGenderWiseRatio = "CALL  	pro_emp_genderwise_ratio( :p_comp_id) ";

	String departmentWiseRatio = "CALL  pro_department_wise_ration( :p_comp_id) ";

	String empAttritionofResigned = "CALL  pro_emp_attrition_resigned( :p_comp_id) ";

	String empAttritionofJoined = "CALL  pro_emp_attrition_joined( :p_comp_id) ";

	String empAgeWiseRatio = "CALL  pro_emp_count_agewisedistribution( :p_comp_id) ";

	String empCountByDesignationWise = "CALL 	pro_emp_count_designationwise( :p_comp_id) ";

	String empCountByDepartmentWise = "CALL 	pro_emp_count_departmentwise( :p_comp_id) ";

	String empCompanyAnnouncement = "CALL 	pro_company_announcement( :p_comp_id) ";

	String empAttendanceRatio = "CALL 	pro_emp_attendance_bymonth( :p_comp_id,:p_emp_id) ";

	@Query(value = countEMPIMPTODAYDATE, nativeQuery = true)
	public List<Object[]> countEMPIMPTODAYDATE(@Param(value = "p_comp_id") Long p_comp_id,
			@Param(value = "p_flag") String p_flag);

	@Query(value = fetchBirthDayEmpList, nativeQuery = true)
	public List<Object[]> fetchBirthDayEmpList(@Param(value = "p_comp_id") Long p_comp_id,
			@Param(value = "p_flag") String p_flag);

	@Query(value = fetchAnniversayEmpList, nativeQuery = true)
	public List<Object[]> fetchAnniversaryDayEmpList(@Param(value = "p_comp_id") Long p_comp_id,
			@Param(value = "p_flag") String p_flag);

	@Query(value = fetchWorkAnniversayEmpList, nativeQuery = true)
	public List<Object[]> fetchWorkAnniversaryDayEmpList(@Param(value = "p_comp_id") Long p_comp_id,
			@Param(value = "p_flag") String p_flag);

	@Query(value = fetchHolidayListByMonth, nativeQuery = true)
	public List<Object[]> fetchHolidayListByMonth(@Param(value = "p_comp_id") Long p_comp_id,
			@Param(value = "p_flag") String p_flag);

	@Query(value = fetchDesignationWiseCTC, nativeQuery = true)
	public List<Object[]> fetchDesignationWiseCTCList(@Param(value = "p_comp_id") Long p_comp_id,
			@Param(value = "p_process_month") Long p_process_month);

	@Query(value = fetchHeadCountByBankPay, nativeQuery = true)
	public List<Object[]> fetchHeadCountByBankPay(@Param(value = "p_comp_id") Long p_comp_id);

	@Query(value = empPayrollstatusbyMonth, nativeQuery = true)
	public List<Object[]> empPayrollstatusbyMonth(@Param(value = "p_comp_id") Long p_comp_id);

	@Query(value = empTicketStatus, nativeQuery = true)
	public List<Object[]> empTicketStatus(@Param(value = "p_comp_id") Long p_comp_id,
			@Param(value = "p_user_id") Long p_user_id, @Param(value = "roleName") String roleName);

	@Query(value = empTicketStatusWithMonth, nativeQuery = true)
	public List<Object[]> empTicketStatuswithMonth(@Param(value = "p_comp_id") Long p_comp_id,
			@Param(value = "lastMonth") Long lastMonth);

	@Query(value = fetchEmpPfContribution, nativeQuery = true)
	public List<Object[]> fetchEmpPfContribution(@Param(value = "p_comp_id") Long p_comp_id,
			@Param(value = "p_process_month") Long p_process_month);

	@Query(value = fetchEmpESIContribution, nativeQuery = true)
	public List<Object[]> fetchEmpESIContribution(@Param(value = "p_comp_id") Long p_comp_id,
			@Param(value = "p_process_month") Long p_process_month);

	@Query(value = fetchEmployeeDocumentConfirmation, nativeQuery = true)
	public List<Object[]> fetchEmployeeDocumentConfirmation(@Param(value = "p_comp_id") Long p_comp_id);

	@Query(value = fetchEmployeeSeprationInfo, nativeQuery = true)
	public List<Object[]> fetchEmployeeSeprationInfo(@Param(value = "p_comp_id") Long p_comp_id);

	@Query(value = countNotification, nativeQuery = true)
	public List<Object> countNotification(@Param(value = "p_comp_id") Long p_comp_id,
			@Param(value = "p_flag") String p_flag);

	@Query(value = empNotification, nativeQuery = true)
	public List<Object[]> employeeNotification(@Param(value = "p_comp_id") Long p_comp_id,
			@Param(value = "p_flag") String p_flag);

	@Query(value = empGenderWiseRatio, nativeQuery = true)
	public List<Object[]> empGenderWiseRatio(@Param(value = "p_comp_id") Long p_comp_id);

	@Query(value = departmentWiseRatio, nativeQuery = true)
	public List<Object[]> departmentWiseRatio(@Param(value = "p_comp_id") Long p_comp_id);

	@Query(value = empAttritionofResigned, nativeQuery = true)
	public List<Object[]> EmpAttritionofResigned(@Param(value = "p_comp_id") Long p_comp_id);

	@Query(value = empAttritionofJoined, nativeQuery = true)
	public List<Object[]> EmpAttritionofJoined(@Param(value = "p_comp_id") Long p_comp_id);

	@Query(value = empAgeWiseRatio, nativeQuery = true)
	public List<Object[]> empAgeWiseRatio(@Param(value = "p_comp_id") Long p_comp_id);

	@Query(value = empCountByDesignationWise, nativeQuery = true)
	public List<Object[]> empCountByDesignationWise(@Param(value = "p_comp_id") Long p_comp_id);

	@Query(value = empCountByDepartmentWise, nativeQuery = true)
	public List<Object[]> empCountByDepartmentWise(@Param(value = "p_comp_id") Long p_comp_id);

	@Query(value = empCompanyAnnouncement, nativeQuery = true)
	public List<Object[]> empCompanyAnnouncement(@Param(value = "p_comp_id") Long p_comp_id);

	@Query(value = findEmployeesReportStatusBased, nativeQuery = true)
	public List<Object[]> findEmployeesReportStatusBased(Long companyId, String status);

	String findEmployeesReportStatusBased = "\r\n" + "SELECT\r\n" + "    emp.employeeCode,\r\n"
			+ "    emp.firstName,\r\n" + "    emp.middleName,\r\n" + "    emp.lastName,\r\n"
			+ "    emp.dateOfBirth,\r\n" + "    emp.gender,\r\n" + "    emp.maritalStatus,\r\n"
			+ "    addrs.addressText,\r\n" + "    addrs.landmark,\r\n" + "    addrsCountry.countryName,\r\n"
			+ "    addrsState.stateName,\r\n" + "    addrsCity.cityName,\r\n" + "    addrs.pincode,\r\n"
			+ "    addrs.mobile,\r\n" + "    addrs.telephone,\r\n" + "    addrs.emailId,\r\n"
			+ "    state.stateName AS jobLocationState,\r\n" + "    city.cityName AS jobLocationCity,\r\n"
			+ "    emp.bloodGroup,\r\n" + "    emp.probationDays,\r\n" + "    emp.empType,\r\n"
			+ "    dept.departmentName,\r\n" + "    designation.designationName,\r\n" + "    emp.contractOverDate,\r\n"
			+ "    emp.referenceName,\r\n"
			+ "    emp.dateOfJoining,eBank.accountType,eBank.bankId,eBank.accountNumber,eBank.ifscCode,\r\n"
			+ "     GROUP_CONCAT(DISTINCT eIdProofs.idTypeId SEPARATOR ',') as IdTypeeIdProofs, \r\n"
			+ "    GROUP_CONCAT(DISTINCT eIdProofs.idNumber SEPARATOR ',') as IdNumbereIdProofs, \r\n"
			+ "     GROUP_CONCAT(DISTINCT eStatuary.statuaryType SEPARATOR ',') as statuaryType, \r\n"
			+ "    GROUP_CONCAT(DISTINCT eStatuary.statuaryNumber SEPARATOR ',') as statuaryNumber,\r\n"
			+ "                    GROUP_CONCAT(  ePayStructure.payHeadId SEPARATOR ',') as payHeadId,\r\n"
			+ "                           GROUP_CONCAT(  ePayStructure.amount SEPARATOR ',') as amount\r\n" + "\r\n"
			+ "\r\n" + "    \r\n" + " \r\n" + "FROM\r\n" + "    Employee emp\r\n" + "LEFT JOIN PayStructureHd ph ON\r\n"
			+ "    ph.employeeId = emp.employeeId AND(\r\n"
			+ "        ph.dateEnd IS NULL OR ph.dateEnd > CURRENT_DATE\r\n" + "    ) AND(\r\n"
			+ "        ph.effectiveDate IS NOT NULL AND ph.effectiveDate <= CURRENT_DATE\r\n"
			+ "    ) AND ph.activeStatus = 'AC'\r\n" + "    LEFT JOIN PayStructure ePayStructure ON\r\n"
			+ "    ePayStructure.payStructureHdId = ph.payStructureHdId\r\n" + "    \r\n"
			+ "    LEFT  JOIN EmployeeIdProofs eIdProofs ON\r\n" + "    eIdProofs.employeeId =emp.employeeId\r\n"
			+ " LEFT  JOIN EmployeeStatuary eStatuary ON\r\n" + "     eStatuary.employeeId=  emp.employeeId\r\n"
			+ "LEFT   JOIN EmployeeBank eBank ON\r\n"
			+ "    eBank.employeeId = emp.employeeId AND (eBank.activeStatus='AC' AND eBank.accountType='SA')\r\n"
			+ "  \r\n" + "LEFT JOIN Address addrs ON\r\n" + "    addrs.addressId = emp.presentAddressId\r\n"
			+ "LEFT JOIN State state ON\r\n" + "    state.stateId = emp.stateId\r\n" + "LEFT JOIN City city ON\r\n"
			+ "    city.cityId = emp.cityId\r\n" + "LEFT JOIN Country addrsCountry ON\r\n"
			+ "    addrsCountry.countryId = addrs.countryId\r\n" + "LEFT JOIN State addrsState ON\r\n"
			+ "    addrsState.stateId = addrs.stateId\r\n" + "LEFT JOIN City addrsCity ON\r\n"
			+ "    addrsCity.cityId = addrs.cityId\r\n" + "LEFT JOIN Department dept ON\r\n"
			+ "    dept.departmentId = emp.departmentId\r\n" + "LEFT JOIN Designation designation ON\r\n"
			+ "    designation.designationId = emp.designationId\r\n"
			+ "  WHERE  emp.companyId=?1 and emp.activeStatus=?2 \r\n" + "GROUP BY\r\n"
			+ "emp.employeeId ,eBank.bankId,eBank.accountType,eBank.ifscCode,eBank.accountNumber";

	/*
	 * String findEmployeesReportDeptAndStatusBased ="\r\n" + "SELECT\r\n" +
	 * "    emp.employeeCode,\r\n" + "    emp.firstName,\r\n" +
	 * "    emp.middleName,\r\n" + "    emp.lastName,\r\n" +
	 * "    emp.dateOfBirth,\r\n" + "    emp.gender,\r\n" +
	 * "    emp.maritalStatus,\r\n" + "    addrs.addressText,\r\n" +
	 * "    addrs.landmark,\r\n" + "    addrsCountry.countryName,\r\n" +
	 * "    addrsState.stateName,\r\n" + "    addrsCity.cityName,\r\n" +
	 * "    addrs.pincode,\r\n" + "    addrs.mobile,\r\n" +
	 * "    addrs.telephone,\r\n" + "    addrs.emailId,\r\n" +
	 * "    state.stateName AS jobLocationState,\r\n" +
	 * "    city.cityName AS jobLocationCity,\r\n" + "    emp.bloodGroup,\r\n" +
	 * "    emp.probationDays,\r\n" + "    emp.empType,\r\n" +
	 * "    dept.departmentName,\r\n" + "    designation.designationName,\r\n" +
	 * "    emp.contractOverDate,\r\n" + "    emp.referenceName,\r\n" +
	 * "    emp.dateOfJoining,eBank.accountType,eBank.bankId,eBank.accountNumber,eBank.ifscCode,\r\n"
	 * +
	 * "     GROUP_CONCAT(DISTINCT eIdProofs.idTypeId SEPARATOR ',') as IdTypeeIdProofs, \r\n"
	 * +
	 * "    GROUP_CONCAT(DISTINCT eIdProofs.idNumber SEPARATOR ',') as IdNumbereIdProofs, \r\n"
	 * +
	 * "     GROUP_CONCAT(DISTINCT eStatuary.statuaryType SEPARATOR ',') as statuaryType, \r\n"
	 * +
	 * "    GROUP_CONCAT(DISTINCT eStatuary.statuaryNumber SEPARATOR ',') as statuaryNumber,\r\n"
	 * +
	 * "                    GROUP_CONCAT(  ePayStructure.payHeadId SEPARATOR ',') as payHeadId,\r\n"
	 * +
	 * "                           GROUP_CONCAT(  ePayStructure.amount SEPARATOR ',') as amount\r\n"
	 * + "\r\n" + "\r\n" + "    \r\n" + " \r\n" + "FROM\r\n" +
	 * "    Employee emp\r\n" + "LEFT JOIN PayStructureHd ph ON\r\n" +
	 * "    ph.employeeId = emp.employeeId AND(\r\n" +
	 * "        ph.dateEnd IS NULL OR ph.dateEnd > CURRENT_DATE\r\n" +
	 * "    ) AND(\r\n" +
	 * "        ph.effectiveDate IS NOT NULL AND ph.effectiveDate <= CURRENT_DATE\r\n"
	 * + "    ) AND ph.activeStatus = 'AC'\r\n" +
	 * "    LEFT JOIN PayStructure ePayStructure ON\r\n" +
	 * "    ePayStructure.payStructureHdId = ph.payStructureHdId\r\n" + "    \r\n" +
	 * "    LEFT  JOIN EmployeeIdProofs eIdProofs ON\r\n" +
	 * "    eIdProofs.employeeId =emp.employeeId\r\n" +
	 * " LEFT  JOIN EmployeeStatuary eStatuary ON\r\n" +
	 * "     eStatuary.employeeId=  emp.employeeId\r\n" +
	 * "LEFT   JOIN EmployeeBank eBank ON\r\n" +
	 * "    eBank.employeeId = emp.employeeId AND (eBank.activeStatus='AC' AND eBank.accountType='SA')\r\n"
	 * + "  \r\n" + "LEFT JOIN Address addrs ON\r\n" +
	 * "    addrs.addressId = emp.presentAddressId\r\n" +
	 * "LEFT JOIN State state ON\r\n" + "    state.stateId = emp.stateId\r\n" +
	 * "LEFT JOIN City city ON\r\n" + "    city.cityId = emp.cityId\r\n" +
	 * "LEFT JOIN Country addrsCountry ON\r\n" +
	 * "    addrsCountry.countryId = addrs.countryId\r\n" +
	 * "LEFT JOIN State addrsState ON\r\n" +
	 * "    addrsState.stateId = addrs.stateId\r\n" +
	 * "LEFT JOIN City addrsCity ON\r\n" + "    addrsCity.cityId = addrs.cityId\r\n"
	 * + "LEFT JOIN Department dept ON\r\n" +
	 * "    dept.departmentId = emp.departmentId\r\n" +
	 * "LEFT JOIN Designation designation ON\r\n" +
	 * "    designation.designationId = emp.designationId\r\n" +
	 * "  WHERE  emp.companyId=?1 and emp.activeStatus=?2 emp.departmentId=?3 and (emp.dateOfJoining>=?4 and emp.dateOfJoining<=?5) \r\n"
	 * + "GROUP BY\r\n" +
	 * "emp.employeeId ,eBank.bankId,eBank.accountType,eBank.ifscCode,eBank.accountNumber"
	 * ;
	 */

	String findEmployeesReportDeptAndStatusBased = "SELECT\r\n" + "    emp.employeeCode,\r\n" + "    emp.firstName,\r\n"
			+ "    emp.middleName,\r\n" + "    emp.lastName,\r\n" + "    emp.dateOfBirth,\r\n" + "    emp.gender,\r\n"
			+ "    emp.maritalStatus,\r\n" + "    addrs.addressText,\r\n" + "    addrs.landmark,\r\n"
			+ "    addrsCountry.countryName,\r\n" + "    addrsState.stateName,\r\n" + "    addrsCity.cityName,\r\n"
			+ "    addrs.pincode,\r\n" + "    addrs.mobile,\r\n" + "    addrs.telephone,\r\n" + "    addrs.emailId,\r\n"
			+ "    state.stateName AS jobLocationState,\r\n" + "    city.cityName AS jobLocationCity,\r\n"
			+ "    emp.bloodGroup,\r\n" + "    emp.probationDays,\r\n" + "    emp.empType,\r\n"
			+ "    dept.departmentName,\r\n" + "    designation.designationName,\r\n" + "    emp.contractOverDate,\r\n"
			+ "    emp.referenceName,\r\n"
			+ "    emp.dateOfJoining,eBank.accountType,eBank.bankId,eBank.accountNumber,eBank.ifscCode,\r\n"
			+ "     GROUP_CONCAT(DISTINCT eIdProofs.idTypeId SEPARATOR ',') as IdTypeeIdProofs, \r\n"
			+ "    GROUP_CONCAT(DISTINCT eIdProofs.idNumber SEPARATOR ',') as IdNumbereIdProofs, \r\n"
			+ "     GROUP_CONCAT(DISTINCT eStatuary.statuaryType SEPARATOR ',') as statuaryType, \r\n"
			+ "    GROUP_CONCAT(DISTINCT eStatuary.statuaryNumber SEPARATOR ',') as statuaryNumber,\r\n"
			+ "                    GROUP_CONCAT(  ePayStructure.payHeadId SEPARATOR ',') as payHeadId,\r\n"
			+ "                           GROUP_CONCAT(  ePayStructure.amount SEPARATOR ',') as amount\r\n" + "\r\n"
			+ "FROM\r\n" + "    Employee emp\r\n" + "LEFT JOIN PayStructureHd ph ON\r\n"
			+ "    ph.employeeId = emp.employeeId AND(\r\n"
			+ "        ph.dateEnd IS NULL OR ph.dateEnd > CURRENT_DATE\r\n" + "    ) AND(\r\n"
			+ "        ph.effectiveDate IS NOT NULL AND ph.effectiveDate <= CURRENT_DATE\r\n"
			+ "    ) AND ph.activeStatus = 'AC'\r\n" + "    LEFT JOIN PayStructure ePayStructure ON\r\n"
			+ "    ePayStructure.payStructureHdId = ph.payStructureHdId\r\n" + "    \r\n"
			+ "    LEFT  JOIN EmployeeIdProofs eIdProofs ON\r\n" + "    eIdProofs.employeeId =emp.employeeId\r\n"
			+ " LEFT  JOIN EmployeeStatuary eStatuary ON\r\n" + "     eStatuary.employeeId=  emp.employeeId\r\n"
			+ "LEFT   JOIN EmployeeBank eBank ON\r\n"
			+ "    eBank.employeeId = emp.employeeId AND (eBank.activeStatus='AC' AND eBank.accountType='SA')\r\n"
			+ "  \r\n" + "LEFT JOIN Address addrs ON\r\n" + "    addrs.addressId = emp.presentAddressId\r\n"
			+ "LEFT JOIN State state ON\r\n" + "    state.stateId = emp.stateId\r\n" + "LEFT JOIN City city ON\r\n"
			+ "    city.cityId = emp.cityId\r\n" + "LEFT JOIN Country addrsCountry ON\r\n"
			+ "    addrsCountry.countryId = addrs.countryId\r\n" + "LEFT JOIN State addrsState ON\r\n"
			+ "    addrsState.stateId = addrs.stateId\r\n" + "LEFT JOIN City addrsCity ON\r\n"
			+ "    addrsCity.cityId = addrs.cityId\r\n" + "LEFT JOIN Department dept ON\r\n"
			+ "    dept.departmentId = emp.departmentId\r\n" + "LEFT JOIN Designation designation ON\r\n"
			+ "    designation.designationId = emp.designationId\r\n"
			+ "  WHERE emp.companyId =?1 AND emp.departmentId =?2\r\n" + "    and \r\n"
			+ "          emp.activeStatus =?3 AND(\r\n"
			+ "            emp.dateOfJoining >= ?4 AND emp.dateOfJoining <=?5)\r\n" + "    \r\n" + "    \r\n"
			+ "        GROUP BY\r\n" + "            emp.employeeId,\r\n" + "            eBank.bankId,\r\n"
			+ "            eBank.accountType,\r\n" + "            eBank.ifscCode,\r\n"
			+ "            eBank.accountNumber";

	String findEmployeesReportStatusAndDurationBased = "SELECT\r\n" + "    emp.employeeCode,\r\n"
			+ "    emp.firstName,\r\n" + "    emp.middleName,\r\n" + "    emp.lastName,\r\n"
			+ "    emp.dateOfBirth,\r\n" + "    emp.gender,\r\n" + "    emp.maritalStatus,\r\n"
			+ "    addrs.addressText,\r\n" + "    addrs.landmark,\r\n" + "    addrsCountry.countryName,\r\n"
			+ "    addrsState.stateName,\r\n" + "    addrsCity.cityName,\r\n" + "    addrs.pincode,\r\n"
			+ "    addrs.mobile,\r\n" + "    addrs.telephone,\r\n" + "    addrs.emailId,\r\n"
			+ "    state.stateName AS jobLocationState,\r\n" + "    city.cityName AS jobLocationCity,\r\n"
			+ "    emp.bloodGroup,\r\n" + "    emp.probationDays,\r\n" + "    emp.empType,\r\n"
			+ "    dept.departmentName,\r\n" + "    designation.designationName,\r\n" + "    emp.contractOverDate,\r\n"
			+ "    emp.referenceName,\r\n"
			+ "    emp.dateOfJoining,eBank.accountType,eBank.bankId,eBank.accountNumber,eBank.ifscCode,\r\n"
			+ "     GROUP_CONCAT(DISTINCT eIdProofs.idTypeId SEPARATOR ',') as IdTypeeIdProofs, \r\n"
			+ "    GROUP_CONCAT(DISTINCT eIdProofs.idNumber SEPARATOR ',') as IdNumbereIdProofs, \r\n"
			+ "     GROUP_CONCAT(DISTINCT eStatuary.statuaryType SEPARATOR ',') as statuaryType, \r\n"
			+ "    GROUP_CONCAT(DISTINCT eStatuary.statuaryNumber SEPARATOR ',') as statuaryNumber,\r\n"
			+ "                    GROUP_CONCAT(  ePayStructure.payHeadId SEPARATOR ',') as payHeadId,\r\n"
			+ "                           GROUP_CONCAT(  ePayStructure.amount SEPARATOR ',') as amount\r\n" + "\r\n"
			+ "FROM\r\n" + "    Employee emp\r\n" + "LEFT JOIN PayStructureHd ph ON\r\n"
			+ "    ph.employeeId = emp.employeeId AND(\r\n"
			+ "        ph.dateEnd IS NULL OR ph.dateEnd > CURRENT_DATE\r\n" + "    ) AND(\r\n"
			+ "        ph.effectiveDate IS NOT NULL AND ph.effectiveDate <= CURRENT_DATE\r\n"
			+ "    ) AND ph.activeStatus = 'AC'\r\n" + "    LEFT JOIN PayStructure ePayStructure ON\r\n"
			+ "    ePayStructure.payStructureHdId = ph.payStructureHdId\r\n" + "    \r\n"
			+ "    LEFT  JOIN EmployeeIdProofs eIdProofs ON\r\n" + "    eIdProofs.employeeId =emp.employeeId\r\n"
			+ " LEFT  JOIN EmployeeStatuary eStatuary ON\r\n" + "     eStatuary.employeeId=  emp.employeeId\r\n"
			+ "LEFT   JOIN EmployeeBank eBank ON\r\n"
			+ "    eBank.employeeId = emp.employeeId AND (eBank.activeStatus='AC' AND eBank.accountType='SA')\r\n"
			+ "  \r\n" + "LEFT JOIN Address addrs ON\r\n" + "    addrs.addressId = emp.presentAddressId\r\n"
			+ "LEFT JOIN State state ON\r\n" + "    state.stateId = emp.stateId\r\n" + "LEFT JOIN City city ON\r\n"
			+ "    city.cityId = emp.cityId\r\n" + "LEFT JOIN Country addrsCountry ON\r\n"
			+ "    addrsCountry.countryId = addrs.countryId\r\n" + "LEFT JOIN State addrsState ON\r\n"
			+ "    addrsState.stateId = addrs.stateId\r\n" + "LEFT JOIN City addrsCity ON\r\n"
			+ "    addrsCity.cityId = addrs.cityId\r\n" + "LEFT JOIN Department dept ON\r\n"
			+ "    dept.departmentId = emp.departmentId\r\n" + "LEFT JOIN Designation designation ON\r\n"
			+ "    designation.designationId = emp.designationId\r\n" + "  WHERE emp.companyId =?1 " + "    and \r\n"
			+ "          emp.activeStatus =?2 AND(\r\n"
			+ "            emp.dateOfJoining >= ?3 AND emp.dateOfJoining <=?4)\r\n" + "    \r\n" + "    \r\n"
			+ "        GROUP BY\r\n" + "            emp.employeeId,\r\n" + "            eBank.bankId,\r\n"
			+ "            eBank.accountType,\r\n" + "            eBank.ifscCode,\r\n"
			+ "            eBank.accountNumber";

	// String ActiveOnboadedEmployeesDetails1 ="select e.firstName, emp.firstName as
	// emp from Employee e LEFT JOIN Employee emp
	// on emp.ReportingToEmployee=e.employeeId LEFT JOIN Employee emp on
	// emp.ReportingToEmployee=e.ReportingToEmployee emp.firstName as
	// reportingToFirtName ,
	// emp.lastName as reportingToLastName,";

//	String ActiveOnboardEmployeesDetails = "SELECT e.employeeCode, e.firstName as empFirstName, e.middleName as empMiddleName, e.lastName as empLastName,\r\n"
//			+ "                         e.adharNumber, e.contactNo, e.officialEmail,\r\n"
//			+ "                         c.cityName as jobLocation, ts.shiftFName, womp.patternName, ls.leaveSchemeName, dep.departmentName, des.designationName, g.gradesName,\r\n"
//			+ "                        e.probationDays, e.noticePeriodDays, e.dateOfJoining,e.contractStartDate, e.contractOverDate, IF(e.timeContract='FT', 'Full Time ' , 'Part Time') as timeContract, e.dateOfBirth, e.gender,\r\n"
//			+ "                     e.bloodGroup, e.alternateNumber, e.maritalStatus, e.anniversaryDate, par.addressText, par.landmark, par.pincode, co.countryName, st.stateName, ci.cityName,\r\n"
//			+ "             pre.addressText as presentaddressText, pre.landmark as presentlandmark, pre.pincode as presentpincode , pco.countryName as presentCountry,\r\n"
//			+ "   pst.stateName as presentState, pci.cityName as presentCity,\r\n"
//			+ "          e.referenceName, refAd.mobile as refMobile, CONCAT(refAd.addressText,' ',refAd.landmark) as refAddressTextLandmark,\r\n"
//			+ "           refAd.emailId as refEmailId, refAd.pincode as refPincode,\r\n"
//			+ "             refCo.countryName as refcountryName, refS.stateName as refstateName , refC.cityName as refcityName,\r\n"
//			+ "           b.bankId, b.accountNumber, b.BankBranch, b.IFSCCode, IF(e.ReportingToEmployee=0, ' ' , CONCAT(emp.firstName,' ', emp.lastName)),  IF(e.empType='PE', 'Permanent ' , 'Contract') , rm.roleDescription \r\n"
//			+ "      FROM Employee e LEFT JOIN EmployeeBank b on e.employeeId=b.employeeId LEFT JOIN City c on  c.cityId=e.cityId \r\n"
//			+ "       LEFT JOIN TMSShift ts on e.shiftId=ts.shiftId RIGHT JOIN TMSWeekOffMasterPattern womp on womp.patternId=e.patternId  \r\n"
//			+ "     LEFT JOIN Department dep on dep.departmentId= e.departmentId LEFT JOIN Designation des on des.designationId=e.designationId \r\n"
//			+ "       LEFT JOIN Grades g on g.gradesId=e.gradesId \r\n"
//			+ "        LEFT JOIN Address par on par.addressId=e.permanentAddressId LEFT JOIN City ci on ci.cityId=par.cityId  LEFT JOIN State st on st.stateId=par.stateId \r\n"
//			+ "     LEFT JOIN Country co on co.countryId=par.countryId\r\n"
//			+ "       LEFT JOIN Address pre on  pre.addressId=e.presentAddressId LEFT JOIN City pci on pci.cityId=pre.cityId  LEFT JOIN State pst on pst.stateId=pre.stateId \r\n"
//			+ "       LEFT JOIN Country pco on pco.countryId=pre.countryId\r\n"
//			+ "          LEFT JOIN Address refAd on  refAd.addressId=e.referenceAddressId LEFT JOIN City refC on refC.cityId=refAd.cityId LEFT JOIN State refS on refS.stateId=refAd.stateId LEFT JOIN Country refCo on refCo.countryId=refAd.countryId\r\n"
//			+ "                  LEFT JOIN Employee emp on (emp.employeeId=e.ReportingToEmployee) OR e.ReportingToEmployee=0  \r\n"
//			+ "                  LEFT JOIN Users u on u.nameOfUser=e.employeeCode RIGHT JOIN UserRoles ur on ur.userId=u.userId RIGHT JOIN RoleMaster rm on ur.roleId=rm.roleId LEFT JOIN LeaveSchemeMaster ls on ls.leaveSchemeId=e.leaveSchemeId \r\n"
//			+ "                      where e.companyId=?1 and e.activeStatus=?2 and dep.departmentId in ?3 group by e.employeeId";

	String ActiveOnboardEmployeesDetails = "SELECT e.employeeCode, e.firstName as empFirstName, e.middleName as empMiddleName, e.lastName as empLastName,\r\n"
			+ "		  e.adharNumber, e.contactNo, e.personalEmail,\r\n"
			+ "	  c.cityName as jobLocation, ts.shiftFName, womp.patternName, ash.schemeName, ls.leaveSchemeName, dep.departmentName, des.designationName, bc.branchName, IF(e.ReportingToEmployee=0, ' ' , CONCAT(emp.firstName,' ', emp.lastName)), g.gradesName,\r\n"
			+ "		   e.probationDays, e.noticePeriodDays,  IF(e.empType='PE', 'Permanent ' , 'Contract'), e.dateOfJoining,e.contractStartDate, e.contractOverDate, IF(e.timeContract='FT', 'Full Time ' , 'Part Time') as timeContract, e.officialEmail, rm.roleDescription, e.dateOfBirth, e.gender,\r\n"
			+ "		 e.bloodGroup, e.alternateNumber, e.maritalStatus, e.anniversaryDate, par.addressText, par.landmark, par.pincode, co.countryName, st.stateName, ci.cityName,\r\n"
			+ "		   pre.addressText as presentaddressText, pre.landmark as presentlandmark, pre.pincode as presentpincode , pco.countryName as presentCountry,\r\n"
			+ "		  pst.stateName as presentState, pci.cityName as presentCity,\r\n"
			+ "			 e.referenceName, refAd.mobile as refMobile, CONCAT(refAd.addressText,' ',refAd.landmark) as refAddressTextLandmark,\r\n"
			+ "		  refAd.emailId as refEmailId, refAd.pincode as refPincode,\r\n"
			+ "		   refCo.countryName as refcountryName, refS.stateName as refstateName , refC.cityName as refcityName,\r\n"
			+ "		 b.bankId, b.accountNumber, b.BankBranch, b.IFSCCode \r\n"
			+ "	  FROM Employee e LEFT JOIN EmployeeBank b on e.employeeId=b.employeeId LEFT JOIN City c on  c.cityId=e.cityId \r\n"
			+ "	  LEFT JOIN TMSShift ts on e.shiftId=ts.shiftId RIGHT JOIN TMSWeekOffMasterPattern womp on womp.patternId=e.patternId \r\n"
			+ "	 LEFT JOIN Department dep on dep.departmentId= e.departmentId LEFT JOIN Designation des on des.designationId=e.designationId \r\n"
			+ "	  LEFT JOIN Grades g on g.gradesId=e.gradesId \r\n"
			+ "	  LEFT JOIN Address par on par.addressId=e.permanentAddressId LEFT JOIN City ci on ci.cityId=par.cityId  LEFT JOIN State st on st.stateId=par.stateId\r\n"
			+ "	 LEFT JOIN Country co on co.countryId=par.countryId\r\n"
			+ "	 LEFT JOIN Address pre on  pre.addressId=e.presentAddressId LEFT JOIN City pci on pci.cityId=pre.cityId  LEFT JOIN State pst on pst.stateId=pre.stateId \r\n"
			+ "		 LEFT JOIN Country pco on pco.countryId=pre.countryId\r\n"
			+ "	  LEFT JOIN Address refAd on  refAd.addressId=e.referenceAddressId LEFT JOIN City refC on refC.cityId=refAd.cityId LEFT JOIN State refS on refS.stateId=refAd.stateId LEFT JOIN Country refCo on refCo.countryId=refAd.countryId\r\n"
			+ "		    LEFT JOIN Employee emp on (emp.employeeId=e.ReportingToEmployee) OR e.ReportingToEmployee=0  \r\n"
			+ "		  LEFT JOIN Users u on u.nameOfUser=e.employeeCode RIGHT JOIN UserRoles ur on ur.userId=u.userId RIGHT JOIN RoleMaster rm on ur.roleId=rm.roleId LEFT JOIN LeaveSchemeMaster ls on ls.leaveSchemeId=e.leaveSchemeId \r\n"
			+ "          LEFT JOIN AttendanceScheme ash ON ash.attendanceSchemeId=e.attendanceSchemeId\r\n"
			+ "          LEFT JOIN Branch bc ON bc.branchId=e.branchId where e.companyId=?1 and e.activeStatus=?2 and dep.departmentId in ?3 group by e.employeeId";

	@Query(value = ActiveOnboardEmployeesDetails, nativeQuery = true)
	public List<Object[]> findEmployeesReportStatusBased11(Long companyId, String status, List<Long> departmentList);

	// @Query(value=ActiveOnboadedEmployeesDetails,nativeQuery = true)
	// public List<Object[]> findEmployeesReportStatusBased11(Long companyId,String
	// status);

	// public List<Object[]> empAttendanceRatio(@Param(value = "p_comp_id") Long
	// p_comp_id,@Param(value = "p_emp_id") Long p_emp_id);

	// @Query("from Employee where companyId=?1 and activeStatus=?2 ORDER BY
	// employeeId DESC ")
	// public List<Employee> findEmployeesReportStatusBased11(Long companyId,String
	// status);
	// @Query(value=findEmployeesReportDeptAndStatusBased,nativeQuery = true)

	@Query("from Employee where companyId=?1 and departmentId=?2 and activeStatus=?3  AND(dateOfJoining >=?4 AND dateOfJoining <=?5)  ORDER BY  employeeId  DESC")
	public List<Employee> findEmployeesReportDeptAndStatusBased(Long companyId, Long departmentId, String status,
			Date fromDate1, Date toDate1);

	// @Query(value=findEmployeesReportStatusAndDurationBased,nativeQuery = true)
	@Query("from Employee where companyId=?1   and activeStatus=?2  AND(dateOfJoining >=?3 AND dateOfJoining <=?4)  ORDER BY  employeeId  DESC")
	public List<Employee> findEmployeesReportStatusAndDurationBased(Long companyId, String status, Date fromDate1,
			Date toDate1);

	@Query("from Employee where companyId=?1   and activeStatus=?2  AND(dateUpdate >=?3 AND dateUpdate <=?4)  ORDER BY  employeeId  DESC")
	public List<Employee> findDeactivateEmployeesReportDurationBased(Long companyId, String status, Date fromDate1,
			Date toDate1);

	@Query("from Employee where companyId=?1 and departmentId=?2 and activeStatus=?3  AND(dateUpdate >=?4 AND dateUpdate <=?5)  ORDER BY  employeeId  DESC")
	public List<Employee> findDeactivateEmployeesReportDurationAndDeptBased(Long companyId, Long departmentId,
			String status, Date fromDate1, Date toDate1);

	@Query(value = empAttendanceRatio, nativeQuery = true)
	public List<Object[]> empAttendanceRatio(@Param(value = "p_comp_id") Long p_comp_id,
			@Param(value = "p_emp_id") Long p_emp_id);

	String GET_UPCOMING_BDAY_LIST = "select concat(em.firstName ,' ',em.lastName),em.employeeCode ,dp.departmentName,dg.designationName,em.dateOfBirth ,em.employeeLogoPath\r\n"
			+ "		  from \r\n" + "      Employee em \r\n"
			+ "     left join PayStructureHd ph on ph.employeeId=em.employeeId and (ph.dateEnd is null or ph.dateEnd>CURRENT_DATE) and (ph.effectiveDate is NOT null and ph.effectiveDate<=CURRENT_DATE ) and ph.activeStatus='AC'\r\n"
			+ " \r\n" + "     left join Designation dg on dg.designationId=em.designationId\r\n"
			+ "     left join Department dp on dp.departmentId=em.departmentId\r\n" + "      where \r\n"
			+ "      DAY(em.dateOfBirth) = DAY(NOW())  and MONTH(em.dateOfBirth) = MONTH(NOW()) AND em.companyId=?1 AND em.activeStatus ='AC' ";

	@Query(value = GET_UPCOMING_BDAY_LIST, nativeQuery = true)
	public List<Object[]> getUpcomingBDayList(Long companyId);

	String GET_UPCOMING_ANNIVERSARYDATE_LIST = " select concat(em.firstName,' ',em.lastName),em.employeeCode ,dp.departmentName,dg.designationName,em.anniversaryDate,em.employeeLogoPath\r\n"
			+ "		  from \r\n" + "      Employee em \r\n"
			+ "     left join PayStructureHd ph on ph.employeeId=em.employeeId and (ph.dateEnd is null or ph.dateEnd>CURRENT_DATE) and (ph.effectiveDate is NOT null and ph.effectiveDate<=CURRENT_DATE ) and ph.activeStatus='AC'\r\n"
			+ "  left join Department dp on dp.departmentId=em.departmentId\r\n"
			+ "     left join Designation dg on dg.designationId=em.designationId\r\n" + "      where \r\n"
			+ "      DAY(em.anniversaryDate) = DAY(NOW()) and MONTH(em.anniversaryDate) = MONTH(NOW())  AND em.companyId=?1 AND   em.activeStatus ='AC'";

	@Query(value = GET_UPCOMING_ANNIVERSARYDATE_LIST, nativeQuery = true)
	public List<Object[]> getAnniversaryList(Long companyId);

	String GET_TODAY_WORK_ANNIVERSARYDATE_LIST = " select concat(em.firstName,' ',em.lastName),em.employeeCode ,dp.departmentName,dg.designationName,em.dateOfJoining,em.employeeLogoPath\r\n"
			+ " 	 				  from \r\n" + " 	 		     Employee em \r\n"
			+ " 	 			   left join PayStructureHd ph on ph.employeeId=em.employeeId and (ph.dateEnd is null or ph.dateEnd>CURRENT_DATE) and (ph.effectiveDate is NOT null and ph.effectiveDate<=CURRENT_DATE ) and ph.activeStatus='AC'\r\n"
			+ " 	 			  left join Department dp on dp.departmentId=em.departmentId\r\n"
			+ " 	 			  left join Designation dg on dg.designationId=em.designationId\r\n"
			+ " 	 			     where \r\n"
			+ " 	 			       DAY(em.dateOfJoining) = DAY(NOW())  and MONTH(em.dateOfJoining) = MONTH(NOW()) AND em.companyId=?1 AND   em.activeStatus ='AC'";

	@Query(value = GET_TODAY_WORK_ANNIVERSARYDATE_LIST, nativeQuery = true)
	public List<Object[]> getTodayWorkAnniversaryList(Long companyId);

	String FormerEmployeesDetails = "SELECT e.employeeCode, e.firstName as empFirstName, e.middleName as empMiddleName, e.lastName as empLastName,"
			+ " e.adharNumber, e.contactNo, e.officialEmail,"
			+ " c.cityName as jobLocation, ts.shiftFName, womp.patternName, dep.departmentName, des.designationName, "
			+ " g.gradesName,"
			+ " e.probationDays, e.noticePeriodDays, e.dateOfJoining,e.contractStartDate, e.contractOverDate, e.timeContract, e.dateOfBirth, e.gender,"
			+ " e.bloodGroup, e.alternateNumber, e.maritalStatus, e.anniversaryDate, par.addressText, par.landmark, par.pincode, co.countryName, st.stateName, ci.cityName,"
			+ " pre.addressText as presentaddressText, pre.landmark as presentlandmark, pre.pincode as presentpincode , pco.countryName as presentCountry,"
			+ " pst.stateName as presentState, pci.cityName as presentCity,"
			+ " e.referenceName, refAd.mobile as refMobile, CONCAT(refAd.addressText,' ',refAd.landmark) as refAddressTextLandmark,"
			+ " refAd.emailId as refEmailId, refAd.pincode as refPincode,"
			+ " refCo.countryName as refcountryName, refS.stateName as refstateName , refC.cityName as refcityName,"
			+ " b.bankId, b.accountNumber, b.BankBranch, b.IFSCCode, CONCAT(emp.firstName,' ', emp.lastName) as reportingTo, e.empType, rm.roleDescription,e.endDate "

			+ " FROM Employee e LEFT JOIN EmployeeBank b on e.employeeId=b.employeeId LEFT JOIN City c on  c.cityId=e.cityId "
			+ " LEFT JOIN TMSShift ts on e.shiftId=ts.shiftId RIGHT JOIN TMSWeekOffMasterPattern womp on womp.patternId=e.patternId "
			+ " LEFT JOIN Department dep on dep.departmentId= e.departmentId LEFT JOIN Designation des on des.designationId=e.designationId "
			+ " LEFT JOIN Grades g on g.gradesId=e.gradesId "
			+ " LEFT JOIN Address par on par.addressId=e.permanentAddressId LEFT JOIN City ci on ci.cityId=par.cityId  LEFT JOIN State st on st.stateId=par.stateId "
			+ " LEFT JOIN Country co on co.countryId=par.countryId"
			+ " LEFT JOIN Address pre on  pre.addressId=e.presentAddressId LEFT JOIN City pci on pci.cityId=pre.cityId  LEFT JOIN State pst on pst.stateId=pre.stateId "
			+ " LEFT JOIN Country pco on pco.countryId=pre.countryId"
			+ " LEFT JOIN Address refAd on  refAd.addressId=e.referenceAddressId LEFT JOIN City refC on refC.cityId=refAd.cityId "
			+ " LEFT JOIN State refS on refS.stateId=refAd.stateId LEFT JOIN Country refCo on refCo.countryId=refAd.countryId"
			+ " INNER JOIN Employee emp on emp.ReportingToEmployee=e.ReportingToEmployee"
			+ " LEFT JOIN Users u on u.nameOfUser=e.employeeCode RIGHT JOIN UserRoles ur on ur.userId=u.userId RIGHT JOIN RoleMaster rm on ur.roleId=rm.roleId "
			+ "  where e.companyId=?1  AND e.endDate >=?2 and e.endDate <=?3  and   dep.departmentId in ?4 AND e.activeStatus='DE'  group by e.employeeId";

	@Query(value = FormerEmployeesDetails, nativeQuery = true)

	public List<Object[]> findFormerEmployeeReport(Long longcompanyId, Date fromDate1, Date toDate1,
			List<Long> departmentList);

	String fetchLastTwelveMonthSalary = "CALL  	pro_lastsixmonth_ctc( :p_comp_id,:p_process_month)";

	@Query(value = fetchLastTwelveMonthSalary, nativeQuery = true)
	public List<Object[]> fetchLastTwelveMonthSalary(@Param(value = "p_comp_id") Long p_comp_id,
			@Param(value = "p_process_month") Long p_process_month);

	String GET_MY_TEAM_TODAY_LEAVE = "CALL  	pro_myteam_on_leave( :p_flag,:p_comp_id,:p_emp_id)";

	@Query(value = GET_MY_TEAM_TODAY_LEAVE, nativeQuery = true)
	public List<Object[]> getMyTeamToday(@Param(value = "p_flag") String p_flag,
			@Param(value = "p_comp_id") Long p_comp_id, @Param(value = "p_emp_id") Long p_emp_id);

	String GET_COUNT_NOTIFICATION_LEAVE = "CALL  	pro_count_notification( :p_comp_id,:p_emp_id)";

	@Query(value = GET_COUNT_NOTIFICATION_LEAVE, nativeQuery = true)
	public List<Object[]> getCountNotificationLeave(@Param(value = "p_comp_id") Long p_comp_id,
			@Param(value = "p_emp_id") Long p_emp_id);

	/**
	 * getCountNotificationLeaveBy}
	 */

	@Query(value = " SELECT   e.firstName ,e.lastName,thd.employeeRemark,thd.dateCreated FROM TMSLeaveEntries thd INNER join Employee e on thd.employeeId= e.employeeId where thd.status='PEN'	 and DATE(thd.dateCreated) = CURRENT_DATE and e.ReportingToEmployee=?2  and thd.companyId=?1", nativeQuery = true)
	public List<Object[]> getCountNotificationLeaveBy(Long companyId, Long employeeId);

	/**
	 * getCountNotificationArRequest}
	 */

	@Query(value = "SELECT   e.firstName,e.lastName,thd.employeeRemark,thd.dateCreated FROM TMSARRequest thd INNER join Employee e on thd.employeeId= e.employeeId where thd.status='PEN' and DATE(thd.dateCreated) = CURRENT_DATE and e.ReportingToEmployee=?2  and thd.companyId=?1	", nativeQuery = true)
	public List<Object[]> getCountNotificationArRequest(Long companyId, Long employeeId);

	/**
	 * getCountNotificationCompOff}
	 */
	@Query(value = "SELECT e.firstName,e.lastName ,thd.remark AS employeeRemark ,thd.dateCreated FROM TMSCompensantoryOff thd INNER join Employee e on thd.employeeId= e.employeeId where thd.status='PEN' and DATE(thd.dateCreated) = CURRENT_DATE and e.ReportingToEmployee=?2 and thd.companyId=?1", nativeQuery = true)
	public List<Object[]> getCountNotificationCompOff(Long companyId, Long employeeId);

	/**
	 * getCountNotificationSepration}
	 */
	@Query(value = "SELECT e.firstName ,e.lastName,thd.remark AS employeeRemark ,thd.dateCreated FROM Separation thd INNER join Employee e on thd.employeeId= e.employeeId where thd.status='PEN' and DATE(thd.dateCreated) = CURRENT_DATE and e.ReportingToEmployee=?2 and thd.companyId=?1", nativeQuery = true)
	public List<Object[]> getCountNotificationSepration(Long companyId, Long employeeId);

	/**
	 * getCountNotificationHelp}
	 */
	@Query(value = "SELECT e.firstName,e.lastName,thd.title AS employeeRemark ,thd.dateCreated FROM TicketRaisingHD thd INNER join Employee e on thd.employeeId= e.employeeId where thd.status='Open' and DATE(thd.dateCreated) = CURRENT_DATE and e.ReportingToEmployee=?2 and thd.companyID=?1", nativeQuery = true)
	public List<Object[]> getCountNotificationHelp(Long companyId, Long employeeId);

	@Query(nativeQuery = true, value = "select COUNT(e.employeeId) ,e.firstName \r\n" + "         \r\n"
			+ "        from TMSLeaveEntries tl INNER join Employee e on tl.employeeId= e.employeeId where \r\n"
			+ "DAY(tl.fromDate)= DAY(now()) AND tl.status='APR'\r\n" + "and e.companyId=?1")
	public List<Object[]> getCountLeave(Long companyId);

	String ADRESS_ID_PROOFS_DATA_EMPLOYEE_WISE = "SELECT e.employeeCode, concat(e.firstName, ' ', e.lastName) as employee, \r\n"
			+ "(select idNumber from EmployeeIdProofs WHERE idTypeId = 'AC' and activeStatus = 'AC' and employeeId=?2) as AdharCard, \r\n"
			+ "(select idNumber from EmployeeIdProofs WHERE idTypeId = 'PA' and activeStatus = 'AC' and employeeId=?2) as PanCard, \r\n"
			+ "(select idNumber from EmployeeIdProofs WHERE idTypeId = 'VO' and activeStatus = 'AC' and employeeId=?2) as VoterIdNo, \r\n"
			+ "(select idNumber from EmployeeIdProofs WHERE idTypeId = 'DL' and activeStatus = 'AC' and employeeId=?2) as DrivingLicenseNo, \r\n"
			+ "(select dateFrom FROM EmployeeIdProofs WHERE idTypeId = 'DL' and activeStatus = 'AC' and employeeId=?2) as DLDateFrom, \r\n"
			+ "(select dateTo FROM EmployeeIdProofs WHERE idTypeId = 'DL' and activeStatus = 'AC' and employeeId=?2) as DLDateTo,\r\n"
			+ "(select idNumber from EmployeeIdProofs WHERE idTypeId = 'PS' and activeStatus = 'AC' and employeeId=?2) as PassportNo,\r\n"
			+ "(select dateFrom FROM EmployeeIdProofs WHERE idTypeId = 'PS' and activeStatus = 'AC' and employeeId=?2) as PSDateFrom, \r\n"
			+ "(select dateTo FROM EmployeeIdProofs WHERE idTypeId = 'PS' and activeStatus = 'AC' and employeeId=?2) as PSDateTo\r\n"
			+ "	 FROM Employee e LEFT JOIN EmployeeIdProofs eip ON e.employeeId=eip.employeeId where e.companyId= ?1 and e.employeeId= ?2 GROUP by e.employeeId ";

	@Query(value = ADRESS_ID_PROOFS_DATA_EMPLOYEE_WISE, nativeQuery = true)
	public List<Object[]> getEmployeeIdProofsDataEmployeeWise(Long companyId, Long employeeId);

	String ADRESS_ID_PROOFS_DATA_DEPARTMENT_WISE = "SELECT e.employeeCode, concat(e.firstName, ' ', e.lastName) as employee, \r\n"
			+ " eip.idTypeId,eip.idNumber, eip.dateFrom, eip.dateTo  FROM \r\n"
			+ " Employee e LEFT JOIN EmployeeIdProofs eip \r\n"
			+ " ON e.employeeId=eip.employeeId JOIN Department dep ON dep.departmentId=e.departmentId \r\n"
			+ " where e.companyId=?1 and e.activeStatus=?3 and dep.departmentId in ?2  ORDER BY e.employeeCode ";

	@Query(value = ADRESS_ID_PROOFS_DATA_DEPARTMENT_WISE, nativeQuery = true)
	public List<Object[]> getEmployeeIdProofsDataDepartmentWise(Long companyId, List<Long> departmentIds,
			String activeStatus);

	String ACCIDENTAL_INSURANCE_DATA_EMP_WISE = "SELECT e.employeeCode, concat(e.firstName, ' ', e.lastName) as employee,\r\n"
			+ "			es.statuaryNumber,es.dateFrom, es.dateTo FROM Employee e LEFT JOIN EmployeeStatuary es\r\n"
			+ "			ON e.employeeId=es.employeeId WHERE es.statuaryType='AC' AND es.status='AC' AND es.isApplicable='N' \r\n"
			+ "			AND e.activeStatus='AC' and e.companyId = ?1 and e.employeeId = ?2 ";

	@Query(value = ACCIDENTAL_INSURANCE_DATA_EMP_WISE, nativeQuery = true)
	public List<Object[]> getEmployeeAccidentalInsDataEmployeeWise(Long companyId, Long employeeId);

	String ACCIDENTAL_INSURANCE_DATA_DEPT_WISE = "SELECT e.employeeCode, concat(e.firstName, ' ', e.lastName) as employee,\r\n"
			+ "		 es.statuaryNumber,  es.dateFrom, es.dateTo, dep.departmentName FROM Employee e LEFT JOIN EmployeeStatuary es \r\n"
			+ "		  ON e.employeeId=es.employeeId JOIN Department dep ON dep.departmentId=e.departmentId\r\n"
			+ "			     where es.statuaryType='AC' AND es.status='AC' AND es.isApplicable='N' \r\n"
			+ "			       AND e.companyId = ?1 AND  e.activeStatus = ?3 and dep.departmentId in ?2 GROUP by e.employeeId ";

	@Query(value = ACCIDENTAL_INSURANCE_DATA_DEPT_WISE, nativeQuery = true)
	public List<Object[]> getEmployeeAccidentalInsDataDepartmentWise(Long companyId, List<Long> departmentIds,
			String activeStatus);

	String PF_UAN_DATA_EMPLOYEE_WISE = "SELECT e.employeeCode, concat(e.firstName, ' ', e.lastName) as employee,\r\n"
			+ "			(select statuaryNumber from EmployeeStatuary WHERE statuaryType = 'UA' and status = 'AC' and employeeId=?2)as UAN,\r\n"
			+ "            (select statuaryNumber from EmployeeStatuary WHERE statuaryType = 'PF' and status = 'AC' and employeeId=?2) as PF,\r\n"
			+ "            (select dateFrom FROM EmployeeStatuary WHERE statuaryType = 'PF' and status = 'AC' and employeeId=?2) as dateFrom, \r\n"
			+ "            (select dateTo FROM EmployeeStatuary  WHERE statuaryType = 'PF' and status = 'AC' and employeeId=?2) as dateTo \r\n"
			+ "            FROM Employee e LEFT JOIN EmployeeStatuary es ON e.employeeId=es.employeeId \r\n"
			+ "			where e.companyId= ?1 and es.employeeId=?2 and es.status = 'AC' AND es.isApplicable='N' GROUP by e.employeeId";

	@Query(value = PF_UAN_DATA_EMPLOYEE_WISE, nativeQuery = true)
	public List<Object[]> getEmployeePfDataEmployeeWise(Long companyId, Long employeeId);

	String PF_UAN_DATA_DEPARTMENT_WISE = "SELECT e.employeeCode, concat(e.firstName, ' ', e.lastName) as employee, es.statuaryType,\r\n"
			+ "			es.statuaryNumber, es.dateFrom, es.dateTo, dep.departmentName FROM Employee e LEFT JOIN EmployeeStatuary es\r\n"
			+ "			ON e.employeeId=es.employeeId JOIN Department dep ON\r\n"
			+ "			dep.departmentId=e.departmentId where e.companyId=?1 AND dep.departmentId in ?2 and e.activeStatus=?3 and es.status='AC' AND es.isApplicable='N' and ( es.statuaryType='PF' OR es.statuaryType='UA' ) ORDER BY dep.departmentName ";

	@Query(value = PF_UAN_DATA_DEPARTMENT_WISE, nativeQuery = true)
	public List<Object[]> getEmployeePfDataDepartmentWise(Long companyId, List<Long> departmentIds,
			String activeStatus);

	String FAMILY_DETAILS_EMP_WISE = "SELECT emp.employeeCode, concat(emp.firstName, ' ', emp.lastName) as Employee, \r\n"
			+ "	ef.name, ef.relation, ef.qualificationId, ef.occupations, ef.dateOfBirth, ef.contactMobile FROM Employee emp  \r\n"
			+ "	LEFT JOIN EmployeeFamily ef on ef.employeeId=emp.employeeId where emp.companyId =?1 and emp.employeeId=?2 ORDER BY emp.employeeCode";

	@Query(value = FAMILY_DETAILS_EMP_WISE, nativeQuery = true)
	public List<Object[]> getFamilyDetailsEmpWise(Long companyId, Long employeeId);

	String FAMILY_DETAILS_DEPT_WISE = "SELECT emp.employeeCode, concat(emp.firstName, ' ', emp.lastName) as Employee, \r\n"
			+ "			ef.name, ef.relation, ef.qualificationId, ef.occupations, ef.dateOfBirth, ef.contactMobile, dept.departmentName FROM Employee emp LEFT JOIN EmployeeFamily ef on ef.employeeId=emp.employeeId LEFT JOIN Department dept on emp.departmentId=dept.departmentId \r\n"
			+ "			where emp.companyId = ?1 and dept.departmentId in ?2 and emp.activeStatus= ?3 ORDER BY emp.employeeCode";

	@Query(value = FAMILY_DETAILS_DEPT_WISE, nativeQuery = true)
	public List<Object[]> getFamilyDetailsDeptWise(Long companyId, List<Long> departmentIds, String activeStatus);

	/**
	 * @author ${Nisha Parveen}
	 *
	 */
	String EDUCATIONAL_DETAILS_EMP_WISE = "SELECT emp.employeeCode, concat(emp.firstName, ' ', emp.lastName) as Employee, "
			+ "ee.qualificationId, ee.degreeName, ee.nameOfInstitution, ee.nameOfBoard, ee.marksPer, ee.passingYear, "
			+ "ee.regularCorrespondance FROM EmployeeEducation ee LEFT JOIN Employee emp on ee.employeeId=emp.employeeId "
			+ "where emp.companyId = ?1 and emp.employeeId =?2 ORDER BY emp.employeeCode";

	@Query(value = EDUCATIONAL_DETAILS_EMP_WISE, nativeQuery = true)
	public List<Object[]> getEducationalDetailsEmpWise(Long companyId, Long employeeId);

	String EDUCATIONAL_DETAILS_DEPT_WISE = "SELECT emp.employeeCode, concat(emp.firstName, ' ', emp.lastName) as Employee, "
			+ "ee.qualificationId, ee.degreeName, ee.nameOfInstitution, ee.nameOfBoard, ee.marksPer, ee.passingYear, "
			+ "ee.regularCorrespondance FROM EmployeeEducation ee LEFT JOIN Employee emp on ee.employeeId=emp.employeeId LEFT JOIN Department dept on emp.departmentId=dept.departmentId "
			+ "where emp.companyId = ?1 and dept.departmentId in ?2 and emp.activeStatus = ?3 ORDER BY emp.employeeCode";

	@Query(value = EDUCATIONAL_DETAILS_DEPT_WISE, nativeQuery = true)
	public List<Object[]> getEducationalDetailsDeptWise(Long companyId, List<Long> departmentIds, String activeStatus);

	String PROFESSIONAL_DETAILS_EMPLOYEE_WISE = "SELECT e.employeeCode,concat(e.firstName,' ',e.lastName),"
			+ "pi.organizationName,pi.dateFrom,pi.dateTo ,pi.designation,pi.reportingTo,"
			+ "pi.reportingContact,pi.annualSalary,pi.reasonForChange"
			+ " from Employee e JOIN ProfessionalInformation pi ON e.employeeId=pi.employeeId"
			+ " WHERE  e.companyId=?1 AND pi.employeeId=?2";

	@Query(value = PROFESSIONAL_DETAILS_EMPLOYEE_WISE, nativeQuery = true)
	public List<Object[]> getProfessionalDetailsReportEmloyeeWise(Long companyId, Long employeeId);

	String PROFESSIONAL_DETAILS_DEPARTMENT_WISE = "SELECT e.employeeCode,concat(e.firstName,' ',e.lastName),"
			+ "pi.organizationName,pi.dateFrom,pi.dateTo ,pi.designation,pi.reportingTo,pi.reportingContact,"
			+ "pi.annualSalary,pi.reasonForChange from Employee e  JOIN ProfessionalInformation pi ON e.employeeId=pi.employeeId "
			+ " JOIN Department dep ON dep.departmentId=e.departmentId WHERE "
			+ " e.companyId=?1 AND e.activeStatus=?2 AND dep.departmentId IN (?3) ORDER BY e.firstName ";

	@Query(value = PROFESSIONAL_DETAILS_DEPARTMENT_WISE, nativeQuery = true)
	public List<Object[]> getProfessionalDetailsReportDepartmentWise(Long companyId, String status,
			List<Long> departmentList);

	// Nominee Details Report

	String NOMINEE_DETAILS_EMPLOYEE_WISE = "SELECT e.employeeCode,concat(e.firstName,' ' ,e.lastName ) as Emp_Name ,\r\n"
			+ "(CASE\r\n" + " 	WHEN( es.statuaryType IS NULL) THEN NULL \r\n" + " ELSE\r\n" + " 	ef.name\r\n"
			+ "END) AS NomName\r\n" + ",\r\n" + "(CASE\r\n" + " 	WHEN( es.statuaryType IS NULL) THEN NULL\r\n"
			+ " ELSE\r\n" + " 	ef.relation\r\n" + "END) AS Relation\r\n" + ",\r\n" + "(CASE\r\n"
			+ " 	WHEN( es.statuaryType IS NULL) THEN NULL\r\n" + " ELSE\r\n" + " 	es.statuaryType\r\n"
			+ "END) AS NomFor\r\n" + ",\r\n" + "(CASE\r\n" + " 	WHEN( es.statuaryType IS NULL) THEN NULL\r\n"
			+ " ELSE\r\n" + " 	ef.contactMobile\r\n" + "END) AS Contact_Number\r\n" + "\r\n"
			+ "FROM Employee e LEFT JOIN EmployeeFamily ef ON e.employeeId=ef.employeeId LEFT JOIN EmployeeStatuary es ON es.familyId=ef.familyId  AND es.statuaryType!='UA'  AND es.isApplicable='N' AND es.status='AC' LEFT JOIN Department dep ON e.departmentId=dep.departmentId   WHERE    e.companyId=?1  AND e.employeeId=?2";

	@Query(value = NOMINEE_DETAILS_EMPLOYEE_WISE, nativeQuery = true)
	public List<Object[]> getNomineeDetailsReportEmloyeeWise(Long companyId, Long employeeId);

	String NOMINEE_DETAILS_DEPARTMENT_WISE = "SELECT e.employeeCode,concat(e.firstName,' ' ,e.lastName ) as Emp_Name ,\r\n"
			+ "(CASE\r\n" + " 	WHEN( es.statuaryType IS NULL) THEN NULL\r\n" + " ELSE\r\n" + " 	ef.name\r\n"
			+ "END) AS NomName\r\n" + ",\r\n" + "(CASE\r\n" + " 	WHEN( es.statuaryType IS NULL) THEN NULL\r\n"
			+ " ELSE\r\n" + " 	ef.relation\r\n" + "END) AS Relation\r\n" + ",\r\n" + "(CASE\r\n"
			+ " 	WHEN( es.statuaryType IS NULL) THEN NULL\r\n" + " ELSE\r\n" + " 	es.statuaryType\r\n"
			+ "END) AS NomFor\r\n" + ",\r\n" + "(CASE\r\n" + " 	WHEN( es.statuaryType IS NULL) THEN NULL\r\n"
			+ " ELSE\r\n" + " 	ef.contactMobile\r\n" + "END) AS Contact_Number\r\n"
			+ "FROM Employee e LEFT JOIN EmployeeFamily ef ON e.employeeId=ef.employeeId LEFT JOIN EmployeeStatuary es ON es.familyId=ef.familyId  AND es.statuaryType!='UA'  AND es.isApplicable='N' AND es.status='AC' LEFT JOIN Department dep ON e.departmentId=dep.departmentId   WHERE    e.companyId=?1 AND   e.activeStatus=?2 AND dep.departmentId IN (?3)    ORDER BY e.employeeId";

	@Query(value = NOMINEE_DETAILS_DEPARTMENT_WISE, nativeQuery = true)
	public List<Object[]> getNomineeDetailsReportDepartmentWise(Long companyId, String status,
			List<Long> departmentList);

	String ESIC_NUMBER_EMPLOYEE_WISE = "SELECT e.employeeCode, concat(e.firstName, ' ', e.lastName) as employee,es.statuaryNumber,es.dateFrom, es.dateTo FROM Employee e LEFT JOIN EmployeeStatuary es ON e.employeeId=es.employeeId WHERE   es.statuaryType='ES' AND es.status='AC' AND es.isApplicable='N' AND e.companyId=?1 AND es.employeeId=?2 GROUP BY e.employeeCode";

	@Query(value = ESIC_NUMBER_EMPLOYEE_WISE, nativeQuery = true)
	public List<Object[]> getEsicReportEmloyeeWise(Long companyId, Long employeeId);

	String ESIC_NUMBER_DEPARTMENT_WISE = "SELECT e.employeeCode, concat(e.firstName, ' ', e.lastName) as employee,\r\n"
			+ "es.statuaryNumber,  es.dateFrom, es.dateTo FROM Employee e LEFT JOIN EmployeeStatuary es\r\n"
			+ "ON e.employeeId=es.employeeId  JOIN  Department dep ON dep.departmentId=e.departmentId\r\n"
			+ "where   es.statuaryType='ES' AND es.status='AC' AND es.isApplicable='N'\r\n"
			+ "AND e.companyId=?1  AND e.activeStatus=?2 AND dep.departmentId IN (?3) GROUP BY e.employeeCode";

	@Query(value = ESIC_NUMBER_DEPARTMENT_WISE, nativeQuery = true)
	public List<Object[]> getEsicReportDepartmentWise(Long companyId, String status, List<Long> departmentIds);

	String MED_INS_NUMBER_EMPLOYEE_WISE = "SELECT e.employeeCode, concat(e.firstName, ' ', e.lastName) as employee,es.statuaryNumber,es.dateFrom, es.dateTo FROM Employee e LEFT JOIN EmployeeStatuary es ON e.employeeId=es.employeeId WHERE   es.statuaryType='ME' AND es.status='AC' AND es.isApplicable='N' AND e.companyId=?1 AND es.employeeId=?2 GROUP BY e.employeeCode";

	@Query(value = MED_INS_NUMBER_EMPLOYEE_WISE, nativeQuery = true)
	public List<Object[]> getMedicalInsuranceReportEmloyeeWise(Long companyId, Long employeeId);

	String MED_INS_DEPARTMENT_WISE = "SELECT e.employeeCode, concat(e.firstName, ' ', e.lastName) as employee,\r\n"
			+ "es.statuaryNumber,  es.dateFrom, es.dateTo  FROM Employee e LEFT JOIN EmployeeStatuary es \r\n"
			+ "ON e.employeeId=es.employeeId  JOIN  Department dep ON dep.departmentId=e.departmentId\r\n"
			+ "where   es.statuaryType='ME' AND es.status='AC' AND es.isApplicable='N'\r\n"
			+ "AND e.companyId=?1  AND e.activeStatus=?2 AND dep.departmentId IN (?3) GROUP BY e.employeeCode";

	@Query(value = MED_INS_DEPARTMENT_WISE, nativeQuery = true)
	public List<Object[]> getMedicalInsuranceReportDepartmentWise(Long companyId, String status,
			List<Long> departmentIds);

	String EMPLOYEES_ON_NOTICE_PERIOD = " SELECT e.employeeCode, e.firstName as empFirstName, e.middleName as empMiddleName, e.lastName as empLastName,\r\n"
			+ "   e.contactNo, e.officialEmail,e.dateOfJoining,c.cityName as jobLocation, ts.shiftFName,"
			+ "   womp.patternName, dep.departmentName, des.designationName, CONCAT(emp.firstName,' ', emp.lastName) as reportingTo,\r\n"
			+ "   g.gradesName, e.probationDays, e.noticePeriodDays ,e.empType,e.timeContract, e.endDate as resignationOn,s.endDate \r\n"
			+ "   FROM Employee e  LEFT JOIN City c on  c.cityId=e.cityId\r\n"
			+ "   LEFT JOIN TMSShift ts on e.shiftId=ts.shiftId\r\n"
			+ "   LEFT JOIN TMSWeekOffMasterPattern womp on womp.patternId=e.patternId \r\n"
			+ "   LEFT JOIN Department dep on dep.departmentId= e.departmentId\r\n"
			+ "    LEFT JOIN Designation des on des.designationId=e.designationId \r\n"
			+ "   LEFT JOIN Grades g on g.gradesId=e.gradesId \r\n"
			+ "   LEFT JOIN Employee emp on emp.ReportingToEmployee=e.ReportingToEmployee\r\n"
			+ "   JOIN Separation s on s.employeeId=e.employeeId and s.status='APR'\r\n"
			+ "  where e.companyId= ?1  AND e.endDate >=?2 and e.endDate <=?3  and e.activeStatus='AC' Group by e.employeeId ";

	@Query(value = EMPLOYEES_ON_NOTICE_PERIOD, nativeQuery = true)
	public List<Object[]> getEmployeesOnNoticePeriod(Long companyId, Date fDate, Date tDate);

	String LANGUAGE_STATUS_EMP_WISE = "SELECT e.employeeCode, e.firstName, e.middleName, e.lastName, dept.departmentName, pi.designationName, "
			+ "(SELECT langRead from EmployeeLanguage WHERE languageId=5 AND employeeId=?2) as hr, "
			+ "(SELECT langWrite from EmployeeLanguage WHERE languageId=5 AND employeeId=?2) as hw, "
			+ "(SELECT langSpeak from EmployeeLanguage WHERE languageId=5 AND employeeId=?2) as hs, "
			+ "(SELECT langRead from EmployeeLanguage WHERE languageId=6 AND employeeId=?2) as er, "
			+ "(SELECT langWrite from EmployeeLanguage WHERE languageId=6 AND employeeId=?2) as ew, "
			+ "(SELECT langSpeak from EmployeeLanguage WHERE languageId=6 AND employeeId=?2) as es FROM Employee e LEFT JOIN Designation pi ON e.designationId=pi.designationId LEFT JOIN EmployeeLanguage el ON e.employeeId=el.employeeId LEFT JOIN Language l on el.languageId=l.languageId LEFT JOIN  Department dept ON dept.departmentId=e.departmentId \r\n"
			+ "		where e.companyId = ?1 and e.employeeId=?2 GROUP BY e.employeeCode";

	@Query(value = LANGUAGE_STATUS_EMP_WISE, nativeQuery = true)
	public List<Object[]> getLanguageStatusEmpWise(Long companyId, Long employeeId);

	String LANGUAGE_STATUS_DEPT_WISE = "SELECT e.employeeCode, e.firstName, e.middleName, e.lastName, dept.departmentName, pi.designationName, el.languageId, el.langRead, el.langWrite, el.langSpeak FROM Employee e LEFT JOIN Designation pi ON e.designationId=pi.designationId LEFT JOIN EmployeeLanguage el ON e.employeeId=el.employeeId LEFT JOIN Language l on el.languageId=l.languageId LEFT JOIN Department dept ON dept.departmentId=e.departmentId \r\n"
			+ "where e.companyId = ?1 AND e.activeStatus= ?2 and dept.departmentId IN ?3 ORDER BY e.employeeCode";

	@Query(value = LANGUAGE_STATUS_DEPT_WISE, nativeQuery = true)
	public List<Object[]> getLanguageStatusDeptWise(Long companyId, String activeStatus, List<Long> departmentIds);

	String GET_ALL_EMPLOYEE_TODAY_LEAVE = "CALL pro_all_emp_on_leave( :p_flag,:p_comp_id)";

	@Query(value = GET_ALL_EMPLOYEE_TODAY_LEAVE, nativeQuery = true)
	public List<Object[]> allEmployeeLeaveToday(@Param(value = "p_flag") String p_flag,
			@Param(value = "p_comp_id") Long p_comp_id);

	String SEPARATION_REQUEST_SUMMARY_EMP_WISE = "SELECT e.employeeCode, concat(e.firstName, ' ', e.lastName) as employeeName, dep.departmentName, des.designationName,c.cityName as jobLocation, IF(e.ReportingToEmployee=0, ' ' , CONCAT(emp.firstName,' ', emp.lastName)) as reportingManager, e.dateOfJoining, sep.dateCreated as requestedON, sep.description, (CASE WHEN (sep.exitDate IS NULL) THEN sep.endDate ELSE sep.exitDate END) as exitDate, (CASE WHEN (e.noticePeriodDays IS NULL) THEN 0 ELSE e.noticePeriodDays END) as noticePeriod, sep.remark\r\n"
			+ "	FROM Employee e JOIN Separation sep ON sep.employeeId=e.employeeId LEFT JOIN City c on c.cityId=e.cityId LEFT JOIN Department dep on dep.departmentId= e.departmentId LEFT JOIN Designation des on des.designationId=e.designationId LEFT JOIN Employee emp on (emp.employeeId=e.ReportingToEmployee) OR e.ReportingToEmployee=0 "
			+ "WHERE e.companyId=?1 and sep.dateCreated>=?2 AND sep.dateCreated<=?3 AND e.employeeId=?4 ORDER BY sep.dateCreated DESC";

	@Query(value = SEPARATION_REQUEST_SUMMARY_EMP_WISE, nativeQuery = true)
	public List<Object[]> findSeparationReqSumEmployeeWise(Long companyId, Date fDate, Date tDate, Long employeeId);

	String SEPARATION_REQUEST_SUMMARY_DEPT_WISE = "SELECT e.employeeCode, concat(e.firstName, ' ', e.lastName) as employeeName, dep.departmentName, des.designationName,c.cityName as jobLocation, IF(e.ReportingToEmployee=0, ' ' , CONCAT(emp.firstName,' ', emp.lastName)) as reportingManager, e.dateOfJoining, sep.dateCreated as requestedON, sep.description, (CASE WHEN (sep.exitDate IS NULL) THEN sep.endDate ELSE sep.exitDate END) as exitDate, (CASE WHEN (e.noticePeriodDays IS NULL) THEN 0 ELSE e.noticePeriodDays END) as noticePeriod, sep.remark\r\n"
			+ " FROM Employee e JOIN Separation sep ON sep.employeeId=e.employeeId LEFT JOIN City c on c.cityId=e.cityId LEFT JOIN Department dep on dep.departmentId= e.departmentId LEFT JOIN Designation des on des.designationId=e.designationId LEFT JOIN Employee emp on (emp.employeeId=e.ReportingToEmployee) OR e.ReportingToEmployee=0 "
			+ " WHERE e.companyId=?1 and sep.dateCreated>=?2 AND sep.dateCreated<=?3 AND dep.departmentId IN (?4) ORDER BY sep.dateCreated DESC";

	@Query(value = SEPARATION_REQUEST_SUMMARY_DEPT_WISE, nativeQuery = true)
	public List<Object[]> findSeparationReqSumDepartmentWise(Long companyId, Date fDate, Date tDate,
			List<Long> departmentIds);
}