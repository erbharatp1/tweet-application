package com.csipl.hrms.service.employee;


import java.util.Date;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.csipl.hrms.common.exception.PayRollProcessException;
import com.csipl.hrms.common.util.ErrorHandling;
import com.csipl.hrms.dto.employee.EmpHierarchyDTO;
import com.csipl.hrms.dto.employee.EmployeeDTO;
import com.csipl.hrms.model.employee.Employee;
 
public interface EmployeePersonalInformationService {
	public Employee save(Employee employee , MultipartFile file , boolean fileFlag) throws ErrorHandling;
	public Employee saveEmployee(Employee employee ) throws ErrorHandling;

	
	
	
	public List<Object[]> findAllPersonalInformationDetails(Long companyId);
	
	//
	
	
	public List<Object[]> findAllEmployeeNotInReportpayOut(Long companyId,String processMonth);
	
	public List<Object[]> findAllEmployeeNotInReport(Long companyId,String processMonth);
	
 	public Employee findEmployees(String  employeeCode, Long companyId);
 	public Employee findEmployeesById( long employeeId );
 	public void saveAll(List<Employee> employeeList);
 	public List<Employee> findAllEmpByDeptId(Long companyId,Long departmentId);

 	
 	
	public List<Employee> getAllDeactivateEmployees(Long companyId);
 	public List<Employee> fetchEmployee(Long companyId);
 	
 	public Employee fetchEmployeeByEmployeeId(Long employeeId);
 	
 	public List<Employee> fetchEmployeeWithDepartment(Long companyId,Long deptId);
 	
	public List<Employee> fetchEmployeeFromDepartmentAndSeparation(Long companyId,Long deptId);
 	
 	//public Long findEmployeeNoticePeriod(Long  employeeId);
 	public Employee getEmployeeInfo(Long employeeId);
 	public Long aadharCheck(String  adharNumber,Long employeeId);
 	public List<Object[]> searchEmployee( Long companyId );
 	public List<Employee> findBirthDayEmployees( Long companyId );
 	public List<Employee> findAnniversaryEmployees( Long companyId  );
 	public List<Employee> findJoiningAnniversaryEmployees(Long companyId);
	public Long checkPayRollForEmployeeJoining(Long companyId, String dateStringWithYYYYMMDD, Long departmentId);
	public  List<EmpHierarchyDTO>  orgHierarchyList(Long employeeId);
	public void saveCandidateImage(Long candidateId, MultipartFile file);
	public List<Object[]> getOnBoardProgessBar(Long progressBar);
	public void onboardMail(Employee employee);
	public  List<Object[]> getEmpShiftName(Long employeeId);
	public List<Object[]> getEmpReportingToEmail(Long employeeId);
    public List<Employee> getEmployeeInPayroll (Long companyID,String payrollMonth);
	public List<Object[]> findAllPersonalInformationDetailsForSeparationLOV(Long companyId);
	public List<Employee> getAllActiveEmployee(Long companyId);


	 public Long adharCheck(String adharNumber) throws PayRollProcessException;
		public int employeeCodeCheck(String employeeCode)throws PayRollProcessException;
	 public int changeEmployeeStatus(Long employeeId);

	 


	public List<Object[]> getTeamHirarchy(Long companyId, Long employeeId);

	public List<Object[]> getEmployeeLifeCycleData(Long companyId, Long employeeId, String status);
	
	public List<Employee> fetchAllEmployee(Long companyId);
	
	public List<EmployeeDTO> getEmployeeListPrabationPeriod(Long companyId);
	
	public List<EmployeeDTO> getEmployeeListAccidentalInsurance(Long companyId);
	public List<EmployeeDTO> getEmployeeListMedicalInsurance(Long companyId);
	public List<EmployeeDTO> getEmployeeListPrabationAboutPeriod(Long companyId);
	public List<EmployeeDTO> getEmployeeListContractExpired(Long companyId);
	public List<EmployeeDTO> getEmployeeListContractAboutExpired(Long companyId);
	public List<EmployeeDTO> getEmployeeListAccidentalInsuranceAbout(Long companyId);
	public List<EmployeeDTO> getEmployeeListMedicalInsuranceAbout(Long companyId);
	
	public List<Object[]> fetchEmployeeList(Long companyId);
	
	public Employee fetchEmployeeByIdCode(Long employeeId);
	public void updateTdsPlanTypeStatus(String status, Long employeeId);
	public String getPlanTypeStatus(Long employeeId);
	public List<Employee> findAllEmpByDeptIdList(Long companyId, List<Long> departmentIdList);
	public List<Employee> getEmployeeOnDesignationId(Long companyId, Long designationId, Long departmentId);
}
 