package com.csipl.hrms.employee.controller;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.csipl.hrms.dto.candidate.CandidateFamilyDTO;
import com.csipl.hrms.dto.employee.EmployeeDTO;
import com.csipl.hrms.dto.employee.EmployeeNode;
import com.csipl.hrms.model.employee.EmployeeFamily;
import com.csipl.hrms.service.adaptor.EmpFamilyAdaptor;
import com.csipl.hrms.service.adaptor.MyTeamAdaptor;
import com.csipl.hrms.service.employee.EmployeePersonalInformationService;
import com.csipl.hrms.service.employee.FamilyService;
import com.csipl.hrms.service.employee.ReportToHierarchyService;
import com.csipl.hrms.service.employee.ReportToHierarchyServiceImpl;

@RestController
@RequestMapping("/myTeam")
public class MyTeamController {
	/**
	 * Logger declaration for knowing the flow of execution for debugging
	 */
	private static final Logger logger = LoggerFactory.getLogger(MyTeamController.class);

	/**
	 * to get List of EmployeeFamily from database based on employeeId
	 */
	MyTeamAdaptor myTeamAdaptor = new MyTeamAdaptor();

	@Autowired
	ReportToHierarchyService reportToHierarchyService;

	@RequestMapping(path = "/teamHirarchy/{companyId}", method = RequestMethod.GET)
	public @ResponseBody EmployeeNode getTeamHirarchy(@PathVariable("companyId") Long companyId,  HttpServletRequest req) {
		List<Object[]> objList = reportToHierarchyService.getTeamHirarchy(companyId);
		return ReportToHierarchyServiceImpl.hirarchy(objList);
	}
}
