package com.csipl.hrms.service.report;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.csipl.hrms.dto.employee.EmployeeEducationDTO;
import com.csipl.hrms.dto.employee.EmployeeFamilyDTO;
import com.csipl.hrms.dto.employee.EmployeeStatuaryDTO;
import com.csipl.hrms.dto.report.EmployeeReportDTO;
import com.csipl.hrms.model.employee.Employee;
import com.csipl.hrms.model.employee.ProfessionalInformation;

public interface EmployeeReportService {

	public EmployeeReportDTO countEMPIMPTODAYDATE(Long companyId, String value);

	public EmployeeReportDTO countNotification(Long companyId);

	public EmployeeReportDTO empTicketStatuswithMonth(Long companyId, Long lastMonth);

	public EmployeeReportDTO empTicketStatus(Long companyId, Long userId, String roleName);

	public List<EmployeeReportDTO> empGenderWiseRatio(Long companyId);

	public List<EmployeeReportDTO> empAgeWiseRatio(Long companyId);

	public List<EmployeeReportDTO> employeeNotification(Long companyId, String value);

	public List<EmployeeReportDTO> fetchBirthDayEmpList(Long companyId, String value);

	public List<EmployeeReportDTO> fetchAnniversaryDayEmpList(Long companyId, String value);

	public List<EmployeeReportDTO> fetchWorkAnniversaryDayEmpList(Long companyId, String value);

	public List<EmployeeReportDTO> fetchHolidayListByMonth(Long companyId, String value) throws ParseException;

	public List<EmployeeReportDTO> fetchEmployeeDocumentConfirmation(Long companyId);

	public List<EmployeeReportDTO> fetchEmployeeSeprationInfo(Long companyId);

	public List<EmployeeReportDTO> fetchDesignationWiseCTC(Long companyId, Long p_process_month);

	public List<EmployeeReportDTO> fetchHeadCountByBankPay(Long companyId);

	public List<EmployeeReportDTO> empPayrollstatusbyMonth(Long companyId);

	public List<EmployeeReportDTO> fetchEmpPfContribution(Long companyId, Long p_process_month);

	public List<EmployeeReportDTO> fetchEmpESIContribution(Long companyId, Long p_process_month);

	public List<EmployeeReportDTO> departmentWiseRatio(Long companyId);

	public List<EmployeeReportDTO> empCountByDesignationWise(Long companyId);

	public List<EmployeeReportDTO> empCountByDepartmentWise(Long companyId);

	public List<EmployeeReportDTO> EmpAttritionofResigned(Long companyId);

	public List<EmployeeReportDTO> EmpAttritionofJoined(Long companyId);

	public List<Object[]> findEmployeesReportStatusBased(String status, Long companyId, List<Long> departmentList);

	// public List<Employee> findEmployeesReportStatusBased(String status, Long
	// companyId);

	public List<EmployeeReportDTO> empCompanyAnnouncement(Long companyId);

	public List<Employee> findEmployeesReportDeptAndStatusBased(Long companyId, Date fromDate1, Date toDate1,
			Long departmentId, String status);

	public List<EmployeeReportDTO> empAttendanceRatio(Long companyId, Long employeeId);

	/**
	 * 
	 * getUpcomingBDayList
	 */
	public List<Object[]> getUpcomingBDayList(Long companyId);

	/**
	 * 
	 * getAnniversaryList
	 */
	public List<Object[]> getAnniversaryList(Long companyId);

	public List<Object[]> getWorkAnniversaryList(Long companyId);

	public List<Object[]> findFormerEmployeeReport(Long longcompanyId, Date fromDate1, Date toDate1,
			List<Long> departmentList);

	public List<EmployeeReportDTO> fetchLastTwelveMonthSalary(Long companyId, Long p_process_month);

	public List<EmployeeReportDTO> getMyTeamToday(String leaveFlagEnum, Long companyId, Long employeeId);

	public List<EmployeeReportDTO> getMyTeamMonthCount(String string, Long companyId, Long employeeId);

	/**
	 * getCountNotificationLeave}
	 */
	public List<EmployeeReportDTO> getCountNotificationLeave(Long companyId, Long employeeId);

	/**
	 * getCountNotificationLeaveBy}
	 */
	public List<EmployeeReportDTO> getCountNotificationLeaveBy(Long companyId, Long employeeId);

	/**
	 * getCountNotificationArRequest}
	 */
	public List<EmployeeReportDTO> getCountNotificationArRequest(Long companyId, Long employeeId);

	/**
	 * getCountNotificationCompOff}
	 */
	public List<EmployeeReportDTO> getCountNotificationCompOff(Long companyId, Long employeeId);

	/**
	 * getCountNotificationSepration}
	 */
	public List<EmployeeReportDTO> getCountNotificationSepration(Long companyId, Long employeeId);

	/**
	 * getCountNotificationHelp}
	 */
	public List<EmployeeReportDTO> getCountNotificationHelp(Long companyId, Long employeeId);

	public List<EmployeeReportDTO> getCountLeave(Long companyId);

	/**
	 * @author ${Nisha Parveen}
	 *
	 */
	public List<Object[]> getAccidentalInsuranceReport(Long companyId, Long employeeId, List<Long> departmentIds,
			String activeStatus);

	/**
	 * @author ${Nisha Parveen}
	 *
	 */

	public List<Object[]> getIdAddressProofsReport(Long companyId, Long employeeId, List<Long> departmentIds,
			String activeStatus);

	/**
	 * @author ${Nisha Parveen}
	 *
	 */
	public List<Object[]> getPfUANNumbersReport(Long companyId, Long employeeId, List<Long> departmentIds,
			String activeStatus);

	/**
	 * @author ${Nisha Parveen}
	 *
	 */
	public Map<String, List<EmployeeFamilyDTO>> getFamilyDetailsReport(Long companyId, Long employeeId,
			List<Long> departmentIds, String activeStatus);

	/**
	 * @author ${Nisha Parveen}
	 *
	 */
	public Map<String, List<EmployeeEducationDTO>> getEducationalDetailsReport(Long companyId, Long employeeId,
			List<Long> departmentIds, String activeStatus);

	public List<Object[]> getMedicalInsuranceReport(Long companyId, Long employeeId, List<Long> departmentList,
			String status);

	public List<Object[]> getEsicReport(Long companyId, Long employeeId, List<Long> departmentList, String status);

	public Map<String, List<ProfessionalInformation>> getProfessionalDetailsReport(Long companyId, Long employeeId,
			String status, List<Long> departmentList);

	public Map<String, List<EmployeeStatuaryDTO>> getNomineeDetailsReport(Long companyId, Long employeeId,
			String status, List<Long> departmentList);

	public List<Object[]> getEmployeesOnNoticePeriod(Long companyId, Date fDate, Date tDate);

	public List<Object[]> getLanguageKnownStatusReport(Long companyId, Long employeeId, String activeStatus,
			List<Long> departmentIds);

	public List<EmployeeReportDTO> allEmployeeLeaveToday(String leaveFlagEnum, Long companyId);

	public List<Object[]> getSeparationReqSummary(Long companyId, Long employeeId, List<Long> departmentIds, Date fDate,
			Date tDate);
}
