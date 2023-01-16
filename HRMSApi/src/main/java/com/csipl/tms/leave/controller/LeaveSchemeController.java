package com.csipl.tms.leave.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.csipl.hrms.common.util.ErrorHandling;
import com.csipl.tms.dto.leave.LeaveSchemeMasterDTO;
import com.csipl.tms.leave.adaptor.LeaveSchemeAdaptor;
import com.csipl.tms.leave.service.LeaveSchemeService;
import com.csipl.tms.model.leave.LeaveSchemeMaster;



@RestController
@RequestMapping("/leaveScheme")
public class LeaveSchemeController {
	private static final Logger logger = LoggerFactory.getLogger(LeaveSchemeController.class);
	@Autowired
	LeaveSchemeService leaveSchemeService;

	LeaveSchemeAdaptor leaveSchemeAdaptor=new LeaveSchemeAdaptor();
	
	
	@RequestMapping(method = RequestMethod.POST)
	public LeaveSchemeMasterDTO saveLeaveScheme(@RequestBody LeaveSchemeMasterDTO leaveSchemeMasterDTO) {
	logger.info("saveLeaveScheme is calling : " + " : leaveSchemeMasterDTO " + leaveSchemeMasterDTO);
	LeaveSchemeMaster leaveScheme = leaveSchemeAdaptor.uiDtoToDatabaseModel(leaveSchemeMasterDTO);
	logger.info("LeaveScheme is end  :" + "LeaveScheme" + leaveScheme);
	LeaveSchemeMasterDTO leaveSchemeDTO = leaveSchemeAdaptor.databaseModelToUiDto(leaveSchemeService.save(leaveScheme));
	return leaveSchemeDTO;
	}
	
	
	
	
	@RequestMapping(value="/findAll/{leavePeriodId}",method = RequestMethod.GET)
	public @ResponseBody List<LeaveSchemeMasterDTO> leaveSchemeList(@PathVariable("leavePeriodId") Long leavePeriodId,HttpServletRequest req) {

	List<LeaveSchemeMaster> leaveSchemeList = leaveSchemeService.findLeaveScheme(leavePeriodId);
	List<LeaveSchemeMasterDTO> leaveSchemeDTOList = leaveSchemeAdaptor.databaseModelToUiDtoList(leaveSchemeList);
	
	return leaveSchemeDTOList;
	}
	
	@RequestMapping(value="/findLeave",method = RequestMethod.GET)
	public @ResponseBody List<LeaveSchemeMasterDTO> leaveScheme(HttpServletRequest req) {

	List<LeaveSchemeMaster> leaveSchemeList = leaveSchemeService.findAllLeaveScheme();
	List<LeaveSchemeMasterDTO> leaveSchemeDTOList = leaveSchemeAdaptor.databaseModelToUiDtoList(leaveSchemeList);
	
	return leaveSchemeDTOList;
	}
	
	
	@RequestMapping(value = "/companyLeaveScheme/{companyId}", method = RequestMethod.GET)
	public @ResponseBody LeaveSchemeMasterDTO getLeaveSchemeDto(@PathVariable("companyId") Long companyId)throws ErrorHandling {
	logger.info("getAllLeaveScheme is calling : ");
	LeaveSchemeMasterDTO leaveSchemeMasterDto = new LeaveSchemeMasterDTO();
	
	List<LeaveSchemeMaster> leaveSchemeList = leaveSchemeService.findLeaveScheme(companyId);
	List<LeaveSchemeMasterDTO> leaveSchemeDTOList = leaveSchemeAdaptor.databaseModelToUiDtoList(leaveSchemeList);
	leaveSchemeMasterDto.setLeaveSchemeMasterDto(leaveSchemeDTOList);
	return leaveSchemeMasterDto;

	}
}
