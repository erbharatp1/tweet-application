package com.csipl.hrms.service.payroll;

import java.util.List;

import com.csipl.hrms.common.exception.PayRollProcessException;
import com.csipl.hrms.dto.employee.EmployeeDTO;

import com.csipl.hrms.model.payrollprocess.Attendance;
import com.csipl.hrms.model.payrollprocess.ReportPayOut;

public interface AttendanceService {

	public List<Attendance> fetchEmployeeForSalary( long companyId, long departmentId );

	public List<ReportPayOut> fetchEmployeeForSalary( long companyId, StringBuilder employeeIds, String payMonth );
	

//	public List<String> validateAttendanceBeforeUpload( long companyId, long departmentId, String processMonth );
	
	public List<EmployeeDTO> fetchEmployeeForValidation(long companyId, long departmentId, String payMonth);
	
	public List<EmployeeDTO> fetchEmployeeForValidation(long companyId, String payMonth);
	
	public void  upload( List<Attendance> attendances) throws PayRollProcessException;
	
	//public List<String> findDepartmentForProcessing(  long companyId,  String processMonth  );
	
	public void checkFormerEmployees(List<String> empCodeListCsv ,  Long companyId)  throws PayRollProcessException;
	
	
}
