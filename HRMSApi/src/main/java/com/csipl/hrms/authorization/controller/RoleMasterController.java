package com.csipl.hrms.authorization.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.csipl.hrms.dto.authorization.EmployeeRoleMasterDTO;
import com.csipl.hrms.dto.authorization.RoleMasterDTO;
import com.csipl.hrms.model.authoriztion.RoleMaster;
import com.csipl.hrms.service.adaptor.RoleMasterAdaptor;
import com.csipl.hrms.service.authorization.RoleMasterService;

@RequestMapping("/roleMaster")
@RestController
public class RoleMasterController {
	/**
	 * Logger declaration for knowing the flow of execution for debugging
	 */
	private static final Logger log = LoggerFactory.getLogger(RoleMasterController.class);

	RoleMasterAdaptor roleMasterAdaptor = new RoleMasterAdaptor();

	@Autowired
	RoleMasterService roleMasterService;

	@RequestMapping(method = RequestMethod.POST)
	public void rolemaster(@RequestBody RoleMasterDTO roleMasterDto, HttpServletRequest req) {
		log.info("rolemaster is calling : roleMasterDto " + roleMasterDto.toString());
		System.out.println("rolemaster is calling : roleMasterDto " + roleMasterDto.toString());

		RoleMaster roleMaster = roleMasterAdaptor.uiDtoToDatabaseModel(roleMasterDto);
		roleMasterService.save(roleMaster);
	}

	@RequestMapping(method = RequestMethod.GET)
	public @ResponseBody List<RoleMasterDTO> findAllRoleMasters(HttpServletRequest req) {
		return roleMasterAdaptor.databaseModelToUiDtoList(roleMasterService.getAllRoleMasters());
	}

	@GetMapping(path = "/getAllRoleMasters")
	public @ResponseBody List<RoleMasterDTO> getAllRoleMasters(HttpServletRequest req) {
		log.info("RoleMasterController.getAllRoleMasters()");
		return roleMasterAdaptor.databaseModelToUiDtoList(roleMasterService.getRoleMasters());
	}

//	@GetMapping(path = "/getEmpRoleMastersList/{companyId}")
//	public @ResponseBody List<EmployeeRoleMasterDTO> getEmpRoleMastersList(@PathVariable Long companyId,
//			HttpServletRequest req) {
//		log.info("RoleMasterController.getEmpRoleMastersList()");
//		return roleMasterAdaptor.databaseModelToUiDtoEmpList(roleMasterService.getEmpRoleMastersList(companyId));
//	}

	@GetMapping(path = "/getDeptRoleMastersList/{companyId}/{departmentId}")
	public @ResponseBody List<EmployeeRoleMasterDTO> getDeptRoleMastersList(@PathVariable Long companyId,
			@PathVariable Long departmentId, HttpServletRequest req) {
		log.info("RoleMasterController.getDeptRoleMastersList()");
		return roleMasterAdaptor
				.databaseModelToUiDtoEmpList(roleMasterService.getDeptRoleMastersList(companyId, departmentId));
	}

	@GetMapping(path = "/getEmpRoleMaster/{companyId}/{employeeId}")
	public @ResponseBody List<EmployeeRoleMasterDTO> getEmpRoleMastersList(@PathVariable Long companyId,
			@PathVariable Long employeeId, HttpServletRequest req) {
		log.info("RoleMasterController.getEmpRoleMastersList()");
		return roleMasterAdaptor
				.databaseModelToUiDtoEmp(roleMasterService.getEmpRoleMasters(companyId, employeeId));
	}
	
	@GetMapping(path = "/getRolePermission/{roleId}")
	public @ResponseBody List<EmployeeRoleMasterDTO> getRolePermission(@PathVariable Long roleId,
		 HttpServletRequest req) {
		log.info("RoleMasterController.getRolePermission()");
		return roleMasterAdaptor
				.databaseModelToUiDtoRolePermission(roleMasterService.getRolePermission(roleId));
	}

}
