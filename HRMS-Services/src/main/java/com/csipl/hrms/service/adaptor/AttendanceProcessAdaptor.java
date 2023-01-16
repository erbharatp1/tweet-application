package com.csipl.hrms.service.adaptor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

//import com.csipl.hrms.dto.common.AttendanceLogDTO;
import com.csipl.hrms.model.common.Company;
import com.csipl.hrms.model.organisation.Department;
import com.csipl.hrms.model.payrollprocess.Attendance;
import com.csipl.hrms.model.payrollprocess.AttendancePK;
import com.csipl.tms.dto.attendancelog.AttendanceLogDTO;

public class AttendanceProcessAdaptor {
public List<Attendance> AttendanceDtoToAttendance(List<AttendanceLogDTO> attendanceLogDtoList){
	List<Attendance> attendances = new ArrayList<Attendance>();
	for (AttendanceLogDTO attendanceDto : attendanceLogDtoList) {
		Attendance attendance = new Attendance();
		attendance.setAbsense(attendanceDto.getAbsense());
		attendance.setLeaves(attendanceDto.getLeaves());
		attendance.setPresense(attendanceDto.getPresense());
		attendance.setWeekoff(attendanceDto.getWeekoff());
		attendance.setPublicholidays(attendanceDto.getPublicholidays());
		attendance.setEmployeeName(attendanceDto.getEmployeeName());
		  System.out.println("companyId ------"+attendanceDto.getCompanyId()+"attendanceDto.getEmployeeCode().."+attendanceDto.getEmployeeCode());
		AttendancePK id = new AttendancePK();
		id.setEmployeeCode(attendanceDto.getEmployeeCode());
		id.setProcessMonth(attendanceDto.getProcessMonth());
		attendance.setId(id);
		
        Company company = new Company();
      //  System.out.println("companyId ------"+attendanceDto.getCompanyId());
        company.setCompanyId(1l);
        attendance.setCompany(company);
        if(attendanceDto.getPayDays()!=null)
        attendance.setPayDays(attendanceDto.getPayDays());
        Department department = new Department();
        department.setDepartmentId(attendanceDto.getEmployeeId());
        attendance.setDateCreated(new Date());
        attendance.setUserId(1l);
        attendances.add(attendance);

	}
	return attendances;
}
}

/*attendance.setEmployeeId(employeeId);
attendance.setAbsense(absense);

attendance.setWeekoff(weeklyoffDays);
attendance.setPublicholidays(holidayDays);
attendance.setPresense(presentDays);
attendance.setProcessMonth(processMonth);
attendance.setEvents(list);*/
