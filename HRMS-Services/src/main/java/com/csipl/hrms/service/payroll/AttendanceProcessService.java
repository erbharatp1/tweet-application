package com.csipl.hrms.service.payroll;

import java.util.List;

import com.csipl.hrms.dto.employee.EmployeeDTO;
import com.csipl.hrms.model.payrollprocess.Attendance;

public interface AttendanceProcessService {
	public List<Object[]> findAllEmployeeForAttendanceProcess(Long departmentId,String processMonth);
	public void save (List<Attendance> attendances);
	public List<Object[]> getEmployeeforAttendanceRollback(Long companyId, String processMonth);
	public int attendanceRollback(List<EmployeeDTO> employeeDTO, String processMonth);
	 
}
