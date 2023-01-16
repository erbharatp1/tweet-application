package com.csipl.hrms.employee.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.velocity.app.VelocityEngine;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.csipl.hrms.common.exception.PayRollProcessException;
import com.csipl.hrms.common.util.ErrorHandling;
import com.csipl.hrms.dto.employee.EmpHierarchyDTO;
import com.csipl.hrms.dto.employee.EmployeeCountDTO;
import com.csipl.hrms.dto.employee.EmployeeDTO;
import com.csipl.hrms.dto.employee.EmployeeDeptDTO;
import com.csipl.hrms.dto.employee.PageIndex;
import com.csipl.hrms.dto.search.EmployeeSearchDTO;
import com.csipl.hrms.model.common.User;
import com.csipl.hrms.model.employee.Employee;
import com.csipl.hrms.model.organisation.Grade;
import com.csipl.hrms.service.adaptor.EmployeePersonalInformationAdaptor;
import com.csipl.hrms.service.common.EmailNotificationService;
import com.csipl.hrms.service.common.repository.EmailConfigurationRepository;
import com.csipl.hrms.service.employee.EmployeePagingAndFilterService;
import com.csipl.hrms.service.employee.EmployeePersonalInformationService;
import com.csipl.hrms.service.organization.CompanyService;
import com.csipl.hrms.service.organization.GradeService;
import com.csipl.hrms.service.organization.StorageService;
import com.csipl.hrms.service.users.UserService;
import com.csipl.tms.deviceinfo.repository.DeviceLogsRepository;

@RestController
@RequestMapping("/employee")
public class EmployeePersonalInformationController {
	public static final String ENCODING = "UTF-8";
	/**
	 * Logger declaration for knowing the flow of execution for debugging
	 */
	private static final Logger logger = LoggerFactory.getLogger(EmployeePersonalInformationController.class);
	@Autowired
	private VelocityEngine velocityEngine;

	@Autowired
	CompanyService companyService;

	@Autowired
	DeviceLogsRepository deviceLogsRepository;
	@Autowired
	EmailConfigurationRepository emailConfugrationRepository;

	@Autowired
	EmailNotificationService emailNotificationService;

	@Autowired
	EmployeePersonalInformationService employeePersonalInformationService;

	@Autowired
	StorageService storageService;

	@Autowired
	UserService userService;

	@Autowired
	private EmployeePagingAndFilterService employeePagingAndFilterService;

	@Autowired
	GradeService gradeService;

	int count;

	EmployeePersonalInformationAdaptor employeePersonalInformationAdaptor = new EmployeePersonalInformationAdaptor();

	/**
	 * @param file        This is the first parameter for taking file Input
	 * @param employeeDto This is the second parameter for getting Employee Object
	 *                    from UI
	 * @param req         This is the third parameter to maintain user session
	 * @throws ErrorHandling
	 */
	@RequestMapping(value = "/employeefile", method = RequestMethod.POST, consumes = "multipart/form-data")
	public void saveEmp(@RequestPart("uploadFile") MultipartFile file, @RequestPart("info") EmployeeDTO employeeDto,
			HttpServletRequest req) throws ErrorHandling {
		logger.info("saveEmp is calling : " + " : EmployeeDTO " + employeeDto + " : uploadFile" + file);
		employeePersonalInformationService.save(employeePersonalInformationAdaptor.uiDtoToDatabaseModel(employeeDto),
				file, true);
	}

	/**
	 * @param employeeDto This is the first parameter for getting Employee Object
	 *                    from UI
	 * @param req         This is the second parameter to maintain user session
	 * @throws PayRollProcessException
	 */
	@RequestMapping(method = RequestMethod.POST)
	public void save(@RequestBody EmployeeDTO employeeDto, HttpServletRequest req)
			throws ErrorHandling, PayRollProcessException {
		logger.info("save is calling : " + " : EmployeeDTO " + employeeDto);
		employeePersonalInformationService.save(employeePersonalInformationAdaptor.uiDtoToDatabaseModel(employeeDto),
				null, false);
	}

	/**
	 * to get all employees List from data base based of company
	 * 
	 * @throws PayRollProcessException
	 */

	@RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<EmployeeDTO> getAllEmployeePersonInformationDetails(@RequestBody EmployeeSearchDTO employeeSearcDto)
			throws PayRollProcessException {
		logger.info(" active employees is calling :" + employeeSearcDto.getActiveStaus());

		List<Object[]> employeeList = employeePagingAndFilterService
				.getEmployeeByPagingAndFilter(employeeSearcDto.getCompanyId(), employeeSearcDto);

		return employeePersonalInformationAdaptor.modeltoDTOList(employeeList, employeeSearcDto);
	}

	@RequestMapping(value = "/{employeeId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody EmployeeDTO get(@PathVariable("employeeId") Long employeeId) {
		// System.out.println("Employee service is calling : " + employeeId);
		Employee employee = employeePersonalInformationService.getEmployeeInfo(employeeId);
		Grade grade = gradeService.findGradeDetails(employee.getGradesId());
		EmployeeDTO employeeDto = employeePersonalInformationAdaptor.databaseModelToUiDto(employee);
		employeeDto.setGradeName(grade.getGradesName());
		return employeeDto;

	}

	@RequestMapping(value = "/employees/{companyId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody EmployeeDTO fetchEmployees(@PathVariable("companyId") Long companyId) {
		// System.out.println("Employee service is calling : " + companyId);
		List<Employee> employeeList = employeePersonalInformationService.fetchEmployee(companyId);
		List<EmployeeDTO> employeeDtoList = employeePersonalInformationAdaptor
				.databaseModelToUiDtoListNativeSearch(employeeList);
		EmployeeDTO employeeDto = new EmployeeDTO();
		employeeDto.setEmployessList(employeeDtoList);

		return employeeDto;
	}

	@RequestMapping(value = "/employeeWithDept/{companyId}/{deptId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody EmployeeDTO fetchEmployeesWithDepartment(@PathVariable("companyId") Long companyId,
			@PathVariable("deptId") Long deptId) {
		// System.out.println("Employee service is calling : " + companyId);

		List<Employee> employeeList = employeePersonalInformationService.fetchEmployeeWithDepartment(companyId, deptId);
		List<EmployeeDTO> employeeDtoList = employeePersonalInformationAdaptor
				.databaseModelToUiDtoListNativeSearch(employeeList);
		EmployeeDTO employeeDto = new EmployeeDTO();
		employeeDto.setEmployessList(employeeDtoList);

		return employeeDto;
	}

	@GetMapping(value = "/employeecount/{companyId}/{pageSize}")
	public @ResponseBody EmployeeCountDTO getAllEmployeeCount(@PathVariable("companyId") String companyId,
			@PathVariable("pageSize") String pageSize, HttpServletRequest req) throws PayRollProcessException {

		List<PageIndex> pageIndexList = new ArrayList<PageIndex>();

		Long longCompanyId = Long.parseLong(companyId);
		int parPageRecord = Integer.parseInt(pageSize);
		EmployeeCountDTO employeeCountDto = new EmployeeCountDTO();
		employeePagingAndFilterService.getEmployeeCount(longCompanyId, employeeCountDto);
		count = employeeCountDto.getCount();
		System.out.println("Count :" + count);
		int pages = (count + parPageRecord - 1) / parPageRecord;
		System.out.println("Pages : -" + pages);
		for (int i = 1; i <= pages; i++) {
			PageIndex pageIndex = new PageIndex();
			pageIndex.setPageIndex(i);
			pageIndexList.add(pageIndex);
		}
		employeeCountDto.setPageIndexs(pageIndexList);
		employeeCountDto.setCount(count);
		return employeeCountDto;
	}

	@GetMapping(value = "/employeecountDE/{companyId}/{pageSize}")
	public @ResponseBody EmployeeCountDTO getAllEmployeeCountDE(@PathVariable("companyId") String companyId,
			@PathVariable("pageSize") String pageSize, HttpServletRequest req) throws PayRollProcessException {

		List<PageIndex> pageIndexList = new ArrayList<PageIndex>();
		int parPageRecord = Integer.parseInt(pageSize);
		EmployeeCountDTO employeeCountDto = new EmployeeCountDTO();
		Long longCompanyId = Long.parseLong(companyId);
		employeePagingAndFilterService.getEmployeeCountDE(longCompanyId, employeeCountDto);
		count = employeeCountDto.getCount();
		System.out.println("Count " + count);
		int pages = (count + parPageRecord - 1) / parPageRecord;
		System.out.println("Pages : -" + pages);
		for (int i = 1; i <= pages; i++) {
			PageIndex pageIndex = new PageIndex();
			pageIndex.setPageIndex(i);
			pageIndexList.add(pageIndex);
		}
		employeeCountDto.setPageIndexs(pageIndexList);
		employeeCountDto.setCount(count);

		return employeeCountDto;
	}

	@GetMapping(value = "/employeeseparatingcount/{companyId}/{pageSize}")
	public @ResponseBody EmployeeCountDTO getAllEmployeeSeparatingCount(@PathVariable("companyId") String companyId,
			@PathVariable("pageSize") String pageSize, HttpServletRequest req) throws PayRollProcessException {

		List<PageIndex> pageIndexList = new ArrayList<PageIndex>();
		int parPageRecord = Integer.parseInt(pageSize);
		EmployeeCountDTO employeeCountDto = new EmployeeCountDTO();
		Long longCompanyId = Long.parseLong(companyId);
		employeePagingAndFilterService.getEmployeeSeparatingCount(longCompanyId, employeeCountDto);
		count = employeeCountDto.getCount();
		System.out.println("Count " + count);
		int pages = (count + parPageRecord - 1) / parPageRecord;
		System.out.println("Pages : -" + pages);
		for (int i = 1; i <= pages; i++) {
			PageIndex pageIndex = new PageIndex();
			pageIndex.setPageIndex(i);
			pageIndexList.add(pageIndex);
		}
		employeeCountDto.setPageIndexs(pageIndexList);
		employeeCountDto.setCount(count);

		return employeeCountDto;
	}

	/**
	 * to get Employee from database based on userName OR employee Code
	 */
	@RequestMapping(value = "/myProfileInfo/{userName}", method = RequestMethod.GET)
	public @ResponseBody EmployeeDTO getEmployeePersonInformationDetails(@PathVariable("userName") String userName,
			HttpServletRequest req) {
		logger.info("EmployeePersonalInformationController getEmployeePersonInformationDetails is calling  userName :"
				+ userName);

		User userBean = userService.findUser(userName);

		Employee employee = employeePersonalInformationService.findEmployees(userName,
				userBean.getCompany().getCompanyId());
		logger.info(" Employee is :");
		return employeePersonalInformationAdaptor.databaseModelToUiDto(employee);
	}

	/**
	 * to get Employee from database based on Department
	 * 
	 * @throws PayRollProcessException
	 */
	@RequestMapping(value = "/empOnDept/{departmentId}/{companyId}", method = RequestMethod.GET)
	public @ResponseBody List<EmployeeDeptDTO> getEmployeeOnDeptId(@PathVariable("departmentId") Long departmentId,
			@PathVariable("companyId") Long companyId, HttpServletRequest req) throws PayRollProcessException {
		logger.info(
				"EmployeePersonalInformationController getEmployeeOnDeptId is calling  departmentId :" + departmentId);
		List<Employee> employee = employeePersonalInformationService.findAllEmpByDeptId(companyId, departmentId);
		// System.out.println("findAllEmpByDeptId" );
		logger.info(" Employee is :");
		return employeePersonalInformationAdaptor.employeeListToEmployeeDeptDto(employee);
	}
	
	
	@RequestMapping(value = "/empOnDesignation/{companyId}/{designationId}/{departmentId}", method = RequestMethod.GET)
	public @ResponseBody List<EmployeeDeptDTO> getEmployeeOnDesignationId(@PathVariable("companyId") Long companyId,@PathVariable("designationId") Long designationId,@PathVariable("departmentId") Long departmentId
			, HttpServletRequest req) throws PayRollProcessException {
		logger.info(
				"EmployeePersonalInformationController getEmployeeOnDeptId is calling  departmentId :" + designationId);
		List<Employee> employee = employeePersonalInformationService.getEmployeeOnDesignationId(companyId, designationId, departmentId);
		// System.out.println("findAllEmpByDeptId" );
		logger.info(" Employee is :");
		return employeePersonalInformationAdaptor.employeeListToEmployeeDeptDto(employee);
	}

	/**
	 * to get all employees List from database based on company
	 * 
	 * @throws PayRollProcessException
	 */
	// @RequestMapping(value = "/employeeLOV/{companyId}", method =
	// RequestMethod.GET)
	// public @ResponseBody List<EmployeeDTO> employeeLOV(@PathVariable("companyId")
	// String companyId,
	// HttpServletRequest req) throws PayRollProcessException {
	// Long longCompanyId = Long.parseLong(companyId);
	// logger.info("employeeLOV is calling :" + longCompanyId);
	// return employeePersonalInformationAdaptor
	// .databaseModelToUiDtoListNativeSearch(employeePersonalInformationService.fetchEmployee(longCompanyId));
	// }

	/**
	 * @param employeeId get the data from database based on employeeId
	 */
	@RequestMapping(value = "/employeeInfo/{employeeId}", method = RequestMethod.GET)
	public @ResponseBody EmployeeDTO employeeInfo(@PathVariable("employeeId") Long employeeId, HttpServletRequest req) {
		EmployeeDTO employeeDTO = new EmployeeDTO();
		logger.info("employeeInfo is calling :" + " : employeeId " + employeeId);
		Employee employee = employeePersonalInformationService.getEmployeeInfo(employeeId);
		Grade grade = gradeService.findGradeDetails(employee.getGradesId());
		logger.info(" employee is : ");
		logger.info(" grade is : " + grade.getGradesName());
		employeeDTO = employeePersonalInformationAdaptor.databaseModelToUiDto(employee);
		employeeDTO.setGradeName(grade.getGradesName());
		return employeeDTO;
	}

	/**
	 * @param employeeId get the data from database based on employeeId
	 */
	@RequestMapping(path = "/orgHierarchy", method = RequestMethod.GET)
	public @ResponseBody List<EmpHierarchyDTO> orgHierarchy(@RequestParam("employeeId") Long employeeId,
			HttpServletRequest req) {
		logger.info("employeeInfo is calling :" + " : employeeId " + employeeId);
		List<EmpHierarchyDTO> employeeListDtoList = employeePersonalInformationService.orgHierarchyList(employeeId);
		return employeeListDtoList;
	}

	/**
	 * @param employeeId get the data from database based on employeeId
	 */
	@RequestMapping(path = "/employeeList", method = RequestMethod.GET)
	public @ResponseBody List<EmployeeDTO> allEmployeesList(@RequestParam("companyId") Long companyId,
			HttpServletRequest req) {
		logger.info("allEmployeesList is calling :" + " : companyId " + companyId);
		List<Object[]> employeeObjList = employeePersonalInformationService
				.findAllPersonalInformationDetails(companyId);
		List<EmployeeDTO> employeeDtoList = employeePersonalInformationAdaptor
				.databaseObjModelToUiDtoList(employeeObjList);
		return employeeDtoList;
	}

	@RequestMapping(path = "/allEmployeeList", method = RequestMethod.GET)
	public @ResponseBody List<EmployeeDTO> getEmployeesListWoEmpCode(@RequestParam("companyId") Long companyId,
			HttpServletRequest req) {
		logger.info("getEmployeesListWoEmpCode is calling :");
		List<Object[]> employeeObjList = employeePersonalInformationService
				.findAllPersonalInformationDetails(companyId);
		List<EmployeeDTO> employeeDtoList = employeePersonalInformationAdaptor
				.databaseObjectModelToUiDto(employeeObjList);
		return employeeDtoList;
	}

	@RequestMapping(path = "/reportPayOut/{companyId}/{processMonth}", method = RequestMethod.GET)
	public @ResponseBody List<EmployeeDTO> allEmployeesListNotOnReport(@PathVariable("companyId") Long companyId,
			@PathVariable("processMonth") String processMonth, HttpServletRequest req) {
		logger.info("allEmployeesList is calling :" + " : companyId " + companyId);
		List<Object[]> employeeObjList = employeePersonalInformationService.findAllEmployeeNotInReport(companyId,
				processMonth);
		List<EmployeeDTO> employeeDtoList = employeePersonalInformationAdaptor
				.databaseObjModelToUiDtoListEmployee(employeeObjList);
		return employeeDtoList;
	}

	/**
	 * @param employeeId get the data from database based on employeeId
	 */
	@RequestMapping(path = "/activeEmployeeList", method = RequestMethod.GET)
	public @ResponseBody List<EmployeeDTO> allActiveEmployeesList(@RequestParam("companyId") Long companyId,
			HttpServletRequest req) {
		logger.info("allActiveEmployeesList is calling :" + " : companyId " + companyId);
		List<Employee> emp = employeePersonalInformationService.getAllActiveEmployee(companyId);
		List<EmployeeDTO> employeeDTOList = employeePersonalInformationAdaptor.databaseModelToUiDtoList(emp);
		return employeeDTOList;

	}

	@RequestMapping(path = "/allEmployees", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<EmployeeDTO> getAllEmployeeList(@RequestBody EmployeeSearchDTO employeeSearcDto)
			throws PayRollProcessException {
		logger.info(" new active employees is calling :" + employeeSearcDto.getActiveStaus());

		List<Object[]> employeeList = employeePagingAndFilterService
				.getAllEmployeeByPagingAndFilter(employeeSearcDto.getCompanyId(), employeeSearcDto);

		return employeePersonalInformationAdaptor.modeltoDTOList(employeeList, employeeSearcDto);
	}

	@RequestMapping(value = "adharCheck/{adharNumber}", method = RequestMethod.GET)
	public @ResponseBody Long adharNoCheck(@PathVariable("adharNumber") String adharNumber, HttpServletRequest req)
			throws PayRollProcessException {
		logger.info("EmpAssetsStatusUpdate is calling :" + "employeeAssetsId  ==" + adharNumber);
		return employeePersonalInformationService.adharCheck(adharNumber);
	}

	@RequestMapping(value = "employeeCodeCheck/{employeeCode}", method = RequestMethod.GET)
	public @ResponseBody int employeeCodeCheck(@PathVariable("employeeCode") String employeeCode,
			HttpServletRequest req) throws PayRollProcessException {
		return employeePersonalInformationService.employeeCodeCheck(employeeCode);
	}

	@RequestMapping(path = "/employeeLifeCycleData/{companyId}/{employeeId}/{status}", method = RequestMethod.GET)
	public @ResponseBody List<EmployeeDTO> getEmployeeLifeCycleData(@PathVariable("companyId") Long companyId,
			@PathVariable("employeeId") Long employeeId, @PathVariable("status") String status,
			HttpServletRequest req) {
		logger.info("allEmployeesList is calling :" + " : companyId " + companyId);
		List<Object[]> employeeObjList = employeePersonalInformationService.getEmployeeLifeCycleData(companyId,
				employeeId, status);
		List<EmployeeDTO> employeeDtoList = employeePersonalInformationAdaptor
				.databaseObjModelToUiEmployeeLifeCycleDto(employeeObjList);
		return employeeDtoList;
	}

//	@GetMapping(path = "/fetchEmployeeList/{companyId}")
//	public @ResponseBody List<EmployeeDTO> fetchEmployeeList(@PathVariable("companyId") Long companyId,
//			HttpServletRequest req) {
//		logger.info("allActiveEmployeesList is calling :" + " : companyId " + companyId);
//		List<Employee> emp = employeePersonalInformationService.fetchAllEmployee(companyId);
//		List<EmployeeDTO> employeeDTOList = employeePersonalInformationAdaptor.databaseModelToUiDtoListEmployee(emp);
//		return employeeDTOList;
//
//	}

	@GetMapping(path = "/fetchEmployeeList/{companyId}")
	public @ResponseBody List<EmployeeDTO> fetchEmployeeList(@PathVariable("companyId") Long companyId,
			HttpServletRequest req) {
		logger.info("allEmployeesList is calling :" + " : companyId " + companyId);
		List<Object[]> employeeObjList = employeePersonalInformationService.fetchEmployeeList(companyId);
		List<EmployeeDTO> employeeDtoList = employeePersonalInformationAdaptor
				.databaseObjModelToUiDtoList(employeeObjList);
		return employeeDtoList;
	}

	
	@RequestMapping(value = "/updateTdsPlanTypeStatus/{status}/{employeeId}", method = RequestMethod.GET)
	public void updateTdsPlanTypeStatus(@PathVariable("status") String status,@PathVariable("employeeId") Long employeeId, HttpServletRequest req)
			throws PayRollProcessException {
		  employeePersonalInformationService.updateTdsPlanTypeStatus(status, employeeId);
	}	
	
	@RequestMapping(value = "/tdsPlanTypeStatus/{employeeId}", method = RequestMethod.GET)
	public EmployeeDTO getPlanTypeStatus(@PathVariable("employeeId") Long employeeId, HttpServletRequest req)
			throws PayRollProcessException {
		EmployeeDTO employeeDTO = new EmployeeDTO();
		String status = employeePersonalInformationService.getPlanTypeStatus(employeeId);
		employeeDTO.setTdsPlanType(status);
		return employeeDTO;
	}
	
}
