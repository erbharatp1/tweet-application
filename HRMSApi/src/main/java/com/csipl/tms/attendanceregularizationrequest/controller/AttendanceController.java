package com.csipl.tms.attendanceregularizationrequest.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.csipl.hrms.common.util.ErrorHandling;
import com.csipl.tms.attendanceregularizationrequest.adaptor.AttendanceAdaptor;
import com.csipl.tms.attendanceregularizationrequest.service.PunchTimeDetailsService;
import com.csipl.tms.dto.attendanceregularizationrequest.PunchTimeDetailDTO;
import com.csipl.tms.dto.attendanceregularizationrequest.SystemAttendanceDTO;
import com.csipl.tms.dto.latlonglocation.RestrictedLocationPremiseDTO;
import com.csipl.tms.dto.leave.LeaveTypeHdDTO;
import com.csipl.tms.leave.controller.LeaveTypeController;
import com.csipl.tms.model.attendanceregularizationrequest.PunchTimeDetail;
//import com.csipl.tms.model.leave.TMSLeaveTypeHd;
import com.csipl.tms.model.shift.Shift;

import io.swagger.annotations.*;


@RestController
public class AttendanceController {
	/**
	 * Logger declaration for knowing the flow of execution for debugging
	 */
	private static final Logger logger = LoggerFactory.getLogger(AttendanceController.class);
	@Autowired
	PunchTimeDetailsService punchTimeDetailsService;
	
	AttendanceAdaptor attendanceAdaptor = new AttendanceAdaptor();
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully saved or update"),
			@ApiResponse(code = 401, message = "You are not authorized to save or update the resource"),
			@ApiResponse(code = 403, message = "You were trying to save resource is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })
	
	@ApiOperation(value = "Save or Update Attendance")
	@RequestMapping(path = "/webAttendance", method = RequestMethod.POST)
	public void savePunchTime(@RequestBody PunchTimeDetailDTO punchTimeDetailsDto) throws ErrorHandling{
		logger.info("savePunchTime is calling : punchTimeDetailsDto " + punchTimeDetailsDto);
		Long count =0l;
		List<PunchTimeDetail> punchTimeDetailList = punchTimeDetailsService.getAllPunchtime(punchTimeDetailsDto.getTktNo(),punchTimeDetailsDto.getCompanyId(),punchTimeDetailsDto.getIn_out());
		if( punchTimeDetailList.size()!=0) {
		int size = punchTimeDetailList.size();
		System.out.println("size.."+size);
		PunchTimeDetail punchTimeDetail = punchTimeDetailList.get(size-1);
		 count = punchTimeDetail.getSNo();}
		PunchTimeDetail punchTimeDetail1 = attendanceAdaptor.uiDtoTopunchTimeDetailModel(punchTimeDetailsDto,count);
		System.out.println("punchTimeDetail..."+punchTimeDetail1);
	     punchTimeDetailsService.save(punchTimeDetail1,punchTimeDetailsDto);
			
	}

	
	@ApiOperation(value = "calculate  Attendance")
	@RequestMapping(path = "/calculateAttendance", method = RequestMethod.GET)
	public List<SystemAttendanceDTO> calculateAttendance(@RequestParam("companyId") String companyId) {
		logger.info("calculateAttendance is calling : companyId " + companyId);
		Long companyID = Long.parseLong(companyId);
		List<Object[]> empAttendanceList = punchTimeDetailsService.getEmpAttendaceList(companyID);
		System.out.println(empAttendanceList);
		List<SystemAttendanceDTO> punchTimeDtoList =	attendanceAdaptor.objectListToUImodel(empAttendanceList);
		System.out.println(punchTimeDtoList);
		for (SystemAttendanceDTO systemAttendanceDTO : punchTimeDtoList) {
			System.out.println(systemAttendanceDTO.getIntime());
			System.out.println(systemAttendanceDTO.getOuttime());
			System.out.println(systemAttendanceDTO.getTktNo());
		}
		return punchTimeDtoList;
	}
	
	@ApiOperation(value = "View a leave Based on shift ID")
	@RequestMapping(path = "/punchTime/{employeeCode}/{companyId}", method = RequestMethod.GET)
	public PunchTimeDetailDTO findMaxTime(@PathVariable(value = "employeeCode") String employeeCode,@PathVariable(value = "companyId") Long companyId) {
		//logger.info("shiftList is calling : " + " :" + "....shiftId.." + shiftId);
    System.out.println("!!!!!!!!!!!!!"+employeeCode+"companyId"+companyId);
		//Long shiftID = Long.parseLong(shiftId);
    List<Object[]> punchTimeDetail = punchTimeDetailsService.findMaxTime(employeeCode, companyId);
		System.out.println("@@@@@@@@@@@@@@@"+punchTimeDetail);
		PunchTimeDetailDTO punchTimeDetailDTO = attendanceAdaptor.punchdatabaseModelToUiDto(punchTimeDetail);
		logger.info("shiftList is end  :" + "punchTimeDetailDTO.." + punchTimeDetailDTO);
		return punchTimeDetailDTO;
	}
	
	//service for posting biometric lane data 	
		@RequestMapping(value = "/biometricLane", method = RequestMethod.POST)
		public void biometricLen(@RequestBody  List<PunchTimeDetailDTO> punchTimeDetailDTO) {
			  logger.info("ePush");
			List<PunchTimeDetail> punchTimeDetailList = attendanceAdaptor.uiDtoToDatabaseModelList(punchTimeDetailDTO);
			punchTimeDetailsService.saveAll(punchTimeDetailList);
			
		}
		
		//service for restricting mobile check in particular location premise
		@GetMapping(value = "/restrictLocations/{employeeId}")
		public List<RestrictedLocationPremiseDTO> restrictedLocations(@PathVariable(value = "employeeId")  Long employeeId) {
			  logger.info("restrictedLocations");
			  List<Object[]> restrictedLocationObj = punchTimeDetailsService.restrictLocationPremise(employeeId);
			  return attendanceAdaptor.objRestrictedLocationPremiseToDtoList(restrictedLocationObj);
			   
		}
		/*
		 * check in for mobile application	with map snapshot file
		 */
		@RequestMapping(value = "/mobAttendance", method = RequestMethod.POST, consumes = "multipart/form-data")
		public void saveCheckInForMobileDetails(@RequestPart("uploadFile") MultipartFile file,
				@RequestPart("info") PunchTimeDetailDTO punchTimeDetailsDto, HttpServletRequest req) throws ErrorHandling {
	        logger.info("saveCheckInForMobileDetails is calling : punchTimeDetailsDto " + punchTimeDetailsDto);
	        Long count =0l;
			List<PunchTimeDetail> punchTimeDetailList = punchTimeDetailsService.getAllPunchtime(punchTimeDetailsDto.getTktNo(),punchTimeDetailsDto.getCompanyId(),punchTimeDetailsDto.getIn_out());
			if( punchTimeDetailList.size()!=0) {
			int size = punchTimeDetailList.size();
			System.out.println("size.."+size);
			PunchTimeDetail punchTimeDetail = punchTimeDetailList.get(size-1);
			count = punchTimeDetail.getSNo();}
			PunchTimeDetail punchTimeDetail1 = attendanceAdaptor.uiDtoTopunchTimeDetailModel(punchTimeDetailsDto,count);
			PunchTimeDetail punchTimeDetail2=attendanceAdaptor.addFilNameToPunchTimeDetailObj(punchTimeDetail1,file,true);
		    punchTimeDetailsService.save(punchTimeDetail2,punchTimeDetailsDto);
	        logger.info("saveCheckInForMobileDetails is ending:file path:" + punchTimeDetail2.getFileLocation());
	
		}
}











