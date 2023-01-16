package com.csipl.tms.attendancelog.service;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public interface AttendanceCalculationService {
	//public List<Object[]> getAllPunchDetails(Object[] object);
	public List<Object[]> getAttendanceFromEpush(List<String> sreialNoList);
	
	public List<Object[]> getAttendanceFromEpushViaDate(List<String> sreialNoList , Date date);
	
	public List<Object[]> getAttendanceFromEpushViaDate(List<String> sreialNoList , LocalDate date);
	
	public List<Object[]> getFirstAttendanceLogFromEpush(List<String> sreialNoList);
	
	public List<Object[]> getCheckInAttendanceFromEpush(List<String> sreialNoList, String boimetricId);
}
