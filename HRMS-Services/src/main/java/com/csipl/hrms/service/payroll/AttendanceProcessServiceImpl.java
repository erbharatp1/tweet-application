package com.csipl.hrms.service.payroll;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.csipl.hrms.dto.employee.EmployeeDTO;
import com.csipl.hrms.model.payrollprocess.Attendance;
import com.csipl.hrms.service.payroll.repository.AttendanceProcessRepository;
import com.csipl.hrms.service.payroll.repository.AttendanceRepository;

@Transactional
@Service("attendanceProcessService")
public class AttendanceProcessServiceImpl implements   AttendanceProcessService{
	
	@Autowired
	private AttendanceRepository attendanceRepository;
	
	 
	@Override
	public List<Object[]> findAllEmployeeForAttendanceProcess(Long departmentId, String processMonth) {
		
		return attendanceRepository.findEmployeeForAttendanceProcess(processMonth, departmentId);
	}

	@Override
	public void save(List<Attendance> attendances) {
		 attendanceRepository.save(attendances);
		
	}

	@Override
	public List<Object[]> getEmployeeforAttendanceRollback(Long companyId, String processMonth) {
		// TODO Auto-generated method stub
		return attendanceRepository.getEmployeeforAttendanceRollback(companyId, processMonth);
	}

	@Override
	@Transactional
	public int attendanceRollback(List<EmployeeDTO> employeeDTO, String processMonth) {
		List<String> employeeCode =employeeDTO.stream()  .map(EmployeeDTO::getEmployeeCode)  .collect(Collectors.toList());
		return attendanceRepository.attendanceRollback(employeeCode,  processMonth);
	}

}
