package com.csipl.hrms.employee.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.csipl.hrms.common.util.ErrorHandling;
import com.csipl.hrms.dto.candidate.CandidateOfficialInformationDTO;
import com.csipl.hrms.model.authoriztion.UserRole;
import com.csipl.hrms.model.employee.Employee;
import com.csipl.hrms.model.employee.EmployeeStatuary;
import com.csipl.hrms.model.organisation.Grade;
import com.csipl.hrms.service.employee.BankDetailsService;
import com.csipl.hrms.service.employee.EmpOfficialService;
import com.csipl.hrms.service.employee.EmployeePersonalInformationService;
import com.csipl.hrms.service.employee.EmployeeStatuaryService;
import com.csipl.hrms.service.employee.repository.EmployeeStatuaryRepository;
import com.csipl.hrms.service.organization.GradeService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;


import com.csipl.hrms.service.adaptor.EmpOfficialAdaptor;
import com.csipl.hrms.service.authorization.UserRoleService;
@RestController
@RequestMapping("/employeeOfficial")
public class EmpOfficialController {
	/**
	 * Logger declaration for knowing the flow of execution for debugging
	 */
	private static final Logger logger = LoggerFactory.getLogger(EmpOfficialController.class);

	@Autowired
	EmployeePersonalInformationService employeePersonalInformationService;
	
	@Autowired
	BankDetailsService bankDetailsService;
	
	@Autowired
	EmployeeStatuaryService employeeStatuaryService;
	
	@Autowired
	EmpOfficialService empOfficialService;
	
	@Autowired
	GradeService gradeService;
	
	@Autowired
	EmployeeStatuaryRepository empStatutoryRepository;
	
	@Autowired
	UserRoleService  userRoleService;
	
	EmpOfficialAdaptor EmpOfficialAdaptor = new EmpOfficialAdaptor(); 
	

	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully saved CandidateIdAddress"),
			@ApiResponse(code = 401, message = "You are not authorized to save or update CandidateIdAddress"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })
	/**
	 * @param candidateIdProofDto
	 *            This is the first parameter for getting shift Object from UI
	 */
	@ApiOperation(value = "Save or Update CandidateIdAddress")
	@RequestMapping(value = "/file/{employeeId}", method = RequestMethod.POST)
	public CandidateOfficialInformationDTO saveOfficial(@PathVariable("employeeId") String employeeId,
			@RequestBody CandidateOfficialInformationDTO candidateOfficialInformationDTO,HttpServletRequest req)throws ErrorHandling {
		
		logger.info("saveOfficial is calling : " + "employeeId : " + employeeId + " : candidateOfficialInformationDTO  "
				+ candidateOfficialInformationDTO.getPfExitDate());
		
		Long empId = Long.parseLong(employeeId);

		Employee employee = employeePersonalInformationService.getEmployeeInfo(empId);
		Employee employeeNew =EmpOfficialAdaptor.UIDtoToEmployeeModel(candidateOfficialInformationDTO, employee);
		List<EmployeeStatuary>  empStaturyList = EmpOfficialAdaptor.UIDtoToStatutoryModelList(candidateOfficialInformationDTO,empId);
	
		for (EmployeeStatuary employeeStatuary : empStaturyList) {
			System.out.println(employeeStatuary.getStatuaryId() +"---------"+employeeStatuary.getStatuaryType());
			employeeStatuary.setStatus("DE");
			employeeStatuary.setEffectiveEndDate(new Date());
		}
	     empOfficialService.saveOfficialInfo(employeeNew, empStaturyList);
		
	    List<EmployeeStatuary>  empStaturyNewList =  EmpOfficialAdaptor.databaseModelTodbModelList(candidateOfficialInformationDTO,empId);


//	     
	 	 empOfficialService.saveOfficialInfo(employeeNew, empStaturyNewList);
	 	 
		return EmpOfficialAdaptor.databaseModelToUiDto(employeeNew,empStaturyNewList);
	}
	@RequestMapping(value = "/{employeeId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody CandidateOfficialInformationDTO get(@PathVariable("employeeId") Long employeeId) {
		System.out.println("Employee service is calling : " + employeeId);
		Employee employee = employeePersonalInformationService.getEmployeeInfo(employeeId);
		Grade grade = gradeService.findGradeDetails(employee.getGradesId());
		List<EmployeeStatuary> employeeStatuaryList  = employeeStatuaryService.findAllEmployeeStatuary(employeeId);
		CandidateOfficialInformationDTO candidateOfficialInformationDto = EmpOfficialAdaptor.databaseModelToUiDto(employee,employeeStatuaryList);
		candidateOfficialInformationDto.setGradeName(grade.getGradesName());
		System.out.println("Employee id in official------------------------- : " + employeeId);
		UserRole userRole=userRoleService.getUserRole(employeeId);
		System.out.println("--------------------------------------"+userRole);
		candidateOfficialInformationDto.setRole(userRole.getRoleMaster().getRoleId());
		return candidateOfficialInformationDto;

	}
}
