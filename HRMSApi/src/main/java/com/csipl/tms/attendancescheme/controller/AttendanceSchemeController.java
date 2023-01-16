package com.csipl.tms.attendancescheme.controller;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.csipl.hrms.common.util.ErrorHandling;
import com.csipl.tms.attendancescheme.adaptor.AttendanceSchemeAdaptor;
import com.csipl.tms.attendancescheme.service.AttendanceSchemeService;
import com.csipl.tms.dto.attendancescheme.AttendanceSchemeDTO;
import com.csipl.tms.locationmaster.adaptor.LocationMasterAdaptor;
import com.csipl.tms.locationmaster.service.LocationMasterService;
import com.csipl.tms.model.attendancescheme.AttendanceScheme;
import com.csipl.tms.model.latlonglocation.LocationMaster;


@RequestMapping("/attendancescheme")
@RestController
public class AttendanceSchemeController {

	private static final Logger logger = LoggerFactory.getLogger(AttendanceSchemeController.class);
	
	@Autowired
	private AttendanceSchemeService attendanceSchemeService;
	@Autowired
	private AttendanceSchemeAdaptor attendanceSchemeAdaptor;
	@Autowired
	LocationMasterAdaptor locationMasterAdaptor;
	@Autowired
	LocationMasterService locationMasterService;
	
	@PostMapping(value = "/save")
	public void saveAttendanceScheme(@RequestBody  AttendanceSchemeDTO attendanceSchemeDTO  , HttpServletRequest request) throws ErrorHandling {
		  logger.info("attendancescheme");
		  
		  List<AttendanceScheme> schemesByName = attendanceSchemeService.findSchemesByName(attendanceSchemeDTO.getSchemeName());
		  if(attendanceSchemeDTO.getAttendanceSchemeId() != null )
			  schemesByName= null;
		  if(schemesByName == null || schemesByName.size()==0) {
		  List<LocationMaster> locationMaster =locationMasterAdaptor.uiMappingDtoToModelList(attendanceSchemeDTO.getAttendanceLocationMappingsDtoList());
		  List<LocationMaster> savedlocationMaster =locationMasterService.save(locationMaster);
		  AttendanceScheme attendanceScheme =  attendanceSchemeAdaptor.uiDtoToModel(attendanceSchemeDTO , savedlocationMaster);
		  attendanceSchemeService.saveAttendanceScheme(attendanceScheme);
		  }else {
			  throw new ErrorHandling("Name you are using in the scheme is already exist, Please try it with Different name");
		  }
	}
	
	@GetMapping(value = "/viewattendancescheme")
	public List<AttendanceSchemeDTO> getAttendanceScheme( ) {
		  logger.info("viewattendancescheme");
		   List<AttendanceScheme> schememodelList = attendanceSchemeService.allAttendanceSchemes();
		  return attendanceSchemeAdaptor.modelToUiDtoList(schememodelList);
	}
	
	@GetMapping(value = "/activeattendancescheme")
	public List<AttendanceSchemeDTO> getActiveAttendanceScheme( ) {
		  logger.info("viewattendancescheme");
		   List<AttendanceScheme> schememodelList = attendanceSchemeService.activeAttendanceSchemes();
		  return attendanceSchemeAdaptor.modelToUiDtoList(schememodelList);
	}

	@GetMapping(value = "/viewattendancescheme/{schemeId}")
	public AttendanceSchemeDTO attendanceSchemeById(@PathVariable("schemeId") Long attendanceSchemeId ) {
		  logger.info("viewattendancescheme");
		   AttendanceScheme schememodel = attendanceSchemeService.attendanceSchemeById(attendanceSchemeId);
		  return attendanceSchemeAdaptor.modelToUiDto(schememodel);
	}
	
	
	 
}
