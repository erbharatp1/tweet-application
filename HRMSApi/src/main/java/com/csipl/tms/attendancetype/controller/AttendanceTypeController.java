package com.csipl.tms.attendancetype.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.csipl.tms.attendancetype.adaptor.AttendanceTypeAdaptor;
import com.csipl.tms.attendancetype.service.AttendanceTypeService;
import com.csipl.tms.dto.attendancetype.AttendanceTypeDTO;
import com.csipl.tms.model.attendancetype.AttendanceType;




@RequestMapping("/attendancetype")
@RestController
public class AttendanceTypeController {

	private static final Logger logger = LoggerFactory.getLogger(AttendanceTypeController.class);
	
	@Autowired
	AttendanceTypeAdaptor attendanceTypeAdaptor;
	
	@Autowired
	AttendanceTypeService attendanceTypeService;
	
	@GetMapping(value = "/typelist")
	public List<AttendanceTypeDTO> getAttendanceType() {
		  logger.info("attendancetype");
		  List<AttendanceType> attendanceTypeList = attendanceTypeService.getAllAttendanceType();
		return  attendanceTypeAdaptor.modelToUiDtoList(attendanceTypeList);
	}
	
}




