package com.csipl.tms.attendanceepush.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.csipl.hrms.dto.search.EmployeeSearchDTO;
import com.csipl.tms.attendanceCalculation.controller.AttendanceLogController;
import com.csipl.tms.attendancelog.service.AttendanceLogService;
import com.csipl.tms.dto.attendancelog.AttendanceLogDTO;

@RestController
@RequestMapping("/attendanceEpush")
public class AttendanceEPushController {

	private static final Logger logger = LoggerFactory.getLogger(AttendanceEPushController.class);
	@Autowired
	AttendanceLogService attendanceLogService;
	
	//@RequestMapping(//value = "/attendanceLogs/{attendanceDate}", 
	//		method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@PostMapping(value = "/ePush")
	public String getAttendanceLogPaginationList(@RequestBody EmployeeSearchDTO employeeSearcDto,
			@PathVariable("attendanceDate") Date attendanceDate) {
		
		return "";
	}
	
	
}
