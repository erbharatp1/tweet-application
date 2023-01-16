package com.csipl.hrms.employee.controller;

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
import com.csipl.hrms.dto.employee.EmployeeNomineeDTO;
import com.csipl.hrms.model.employee.EmployeeNominee;
import com.csipl.hrms.model.employee.EmployeeStatuary;
import com.csipl.hrms.service.adaptor.EmployeeNomineeAdaptor;
import com.csipl.hrms.service.adaptor.EmployeeStatuaryAdaptor;
import com.csipl.hrms.service.employee.EmployeeNomineeService;
import com.csipl.hrms.service.employee.EmployeeStatuaryService;

@RestController
@RequestMapping("/employee")
public class EmployeeNomineeController {
	private static final Logger logger = LoggerFactory.getLogger(EmployeeNomineeController.class);
	
	EmployeeNomineeAdaptor employeeNomineeAdaptor=new EmployeeNomineeAdaptor();
	EmployeeStatuaryAdaptor employeeStatuaryAdaptor = new EmployeeStatuaryAdaptor();
	
	@Autowired
	EmployeeNomineeService employeeNomineeService;
	
	@Autowired
	EmployeeStatuaryService employeeStatuaryService;
	
	@RequestMapping(value="/employeeNominee/{employeeId}",method = RequestMethod.POST)
	public List<EmployeeNomineeDTO> saveEmployeeNominee(@PathVariable("employeeId") Long employeeId,@RequestBody List<EmployeeNomineeDTO> employeeNomineeDTOList, HttpServletRequest req)throws ErrorHandling {
		logger.info("saveCandidateFamily is calling : employeeNomineeDTOList "+ employeeNomineeDTOList);
		List<EmployeeStatuary> employeeNomineeList = employeeStatuaryService.findAllEmployeeStatuary(employeeId);
		List<EmployeeStatuary> employeeNomineeObjList = employeeNomineeAdaptor.uiDtoToDatabaseModelList1(employeeNomineeDTOList,employeeNomineeList);
	List<EmployeeStatuary> employeeStatuaryListObj =	employeeStatuaryService.save(employeeNomineeObjList,employeeId);
	//System.out.println(candidateFamilyAdaptor.databaseModelToUiDtoList(candidateFamilyListObj).toString());
	employeeNomineeAdaptor.statuaryModelToUiDtoList(employeeStatuaryListObj);
	return employeeNomineeAdaptor.statuaryModelToUiDtoList(employeeStatuaryListObj);
	}
	
	
	@RequestMapping(value="/getNominee/{employeeId}", method = RequestMethod.GET)
	public @ResponseBody List<EmployeeNomineeDTO> getEmployeeFamilyDetails(@PathVariable("employeeId") Long employeeId,
			HttpServletRequest req) {
		
		logger.info("FamilyDetailsController getAllEmployeeFamilyDetails  empId is :" + employeeId);
		List<EmployeeNominee> employeeNomineeList=employeeNomineeService.findAllEmployeeNominee(employeeId);
		
		List<EmployeeNomineeDTO> employeeNomineeDTOList=employeeNomineeAdaptor.databaseModelToUiDtoList(employeeNomineeList);
		logger.info("getting  FamilyDetails :==========================="  + employeeNomineeList);
		return employeeNomineeDTOList;
}
}
