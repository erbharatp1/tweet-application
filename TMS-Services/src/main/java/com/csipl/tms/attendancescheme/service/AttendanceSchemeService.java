package com.csipl.tms.attendancescheme.service;

import java.util.List;

import com.csipl.tms.model.attendancescheme.AttendanceScheme;

public interface AttendanceSchemeService {

	public void saveAttendanceScheme(AttendanceScheme attendanceScheme);
	public List<AttendanceScheme> allAttendanceSchemes();
	public AttendanceScheme attendanceSchemeById(Long attendanceSchemeId);
	public List<AttendanceScheme> findSchemesByName(String schemeName);
	public List<AttendanceScheme> activeAttendanceSchemes();
	
}
