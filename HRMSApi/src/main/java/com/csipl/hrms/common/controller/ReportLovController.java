package com.csipl.hrms.common.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.csipl.hrms.dto.common.ReportLovDTO;
import com.csipl.hrms.model.common.ReportLov;
import com.csipl.hrms.service.common.ReportLovService;
import com.csipl.hrms.service.common.adaptor.ReportLovAdaptor;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/reportLov")
public class ReportLovController {

	@Autowired
	ReportLovService reportLovService;

	ReportLovAdaptor reportLovAdaptor = new ReportLovAdaptor();

	@ApiOperation(value = "View List of shift based on company ID")
	@RequestMapping(method = RequestMethod.GET)
	public List<ReportLovDTO> reportLovList() {

		List<ReportLov> reportLovList = reportLovService.findAllReport();
		List<ReportLovDTO> reportLovDTOList = reportLovAdaptor.databaseModelToUiDtoList(reportLovList);

		return reportLovDTOList;
	}
	
	
	@ApiOperation(value = "View List of shift based on company ID")
	@RequestMapping(value="/{moduleName}",method = RequestMethod.GET)
	public List<ReportLovDTO> moduleNameList(@PathVariable("moduleName") String moduleName) {

		List<ReportLov> reportLovList = reportLovService.findReportByModuleName(moduleName);
		List<ReportLovDTO> reportLovDTOList = reportLovAdaptor.databaseModelToUiDtoList(reportLovList);

		return reportLovDTOList;
	}
}
