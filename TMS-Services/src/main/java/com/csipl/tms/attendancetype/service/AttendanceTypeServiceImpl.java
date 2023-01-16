package com.csipl.tms.attendancetype.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.csipl.tms.attendancetype.repository.AttendanceTypeRepository;
import com.csipl.tms.model.attendancetype.AttendanceType;

@Service("attendanceTypeService")
public class AttendanceTypeServiceImpl implements AttendanceTypeService{

	@Autowired
	AttendanceTypeRepository attendanceTypeRepository;
	
	@Override
	public List<AttendanceType> getAllAttendanceType() {
		return attendanceTypeRepository.getAllAttendanceType();
	}
	
}
