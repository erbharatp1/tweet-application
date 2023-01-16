package com.csipl.hrms.employee.controller;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import com.csipl.hrms.common.exception.PayRollProcessException;
import com.csipl.hrms.common.util.ErrorHandling;
import com.csipl.hrms.dto.candidate.CandidateDTO;
import com.csipl.hrms.dto.candidate.ProgressBarDTO;
import com.csipl.hrms.dto.common.WeekOffPatternDTO;
import com.csipl.hrms.model.common.User;
import com.csipl.hrms.model.employee.Employee;
import com.csipl.hrms.model.employee.EmployeeLetter;
import com.csipl.hrms.model.employee.Letter;
import com.csipl.hrms.service.adaptor.EmpPersonalAdaptor;
import com.csipl.hrms.service.authorization.LoginService;
import com.csipl.hrms.service.employee.EmployeeLetterService;
import com.csipl.hrms.service.employee.EmployeePersonalInformationService;
import com.csipl.hrms.service.employee.LetterService;
import com.csipl.tms.dto.shift.ShiftDTO;
import com.csipl.tms.dto.weekofpattern.TMSWeekOffMasterPatternDto;
import com.csipl.tms.model.shift.Shift;
import com.csipl.tms.shift.adaptor.ShiftAdaptor;
import com.csipl.tms.shift.service.ShiftService;
import com.csipl.tms.weekoffpattern.adaptor.TMSWeekOffMasterPatternAdaptor;
import com.csipl.tms.weekoffpattern.service.WeekOffPatternService;

@RestController
@RequestMapping("/employeePersonal")

public class EmpPersonalController {
	/**
	 * Logger declaration for knowing the flow of execution for debugging
	 */
	private static final Logger logger = LoggerFactory.getLogger(EmpPersonalController.class);
	@Autowired
	private EmployeeLetterService empLetterService;
	@Autowired
	private LetterService letterService;
	@Autowired
	EmployeePersonalInformationService employeePersonalInformationService;

	@Autowired
	private org.springframework.core.env.Environment environment;
	@Autowired
	ShiftService shiftService;

	@Autowired
	LoginService loginService;
	
	EmpPersonalAdaptor empPersonalAdaptor = new EmpPersonalAdaptor();

	ShiftAdaptor shiftAdaptor = new ShiftAdaptor();

	TMSWeekOffMasterPatternAdaptor tmsWeekOffMasterPatternAdaptor = new TMSWeekOffMasterPatternAdaptor();

	@Autowired
	WeekOffPatternService week_Off_PatternService;

	/**
	 * @param employeeDto This is the first parameter for getting Employee Object
	 *                    from UI
	 * @param req         This is the second parameter to maintain user session
	 * @throws PayRollProcessException
	 */
	@RequestMapping(method = RequestMethod.POST)
	public @ResponseBody CandidateDTO save(@RequestBody CandidateDTO candidateDto, HttpServletRequest req)
			throws ErrorHandling, PayRollProcessException {
		logger.info("save is calling : " + " : CandidateDTO " + candidateDto);
		Employee employee = employeePersonalInformationService.getEmployeeInfo(candidateDto.getCandidateId());
		employeePersonalInformationService.save(empPersonalAdaptor.candidateDtoToDatabaseModel(candidateDto, employee),
				null, false);
		CandidateDTO candidate = empPersonalAdaptor.databaseModelToUiDto(employee);
		return candidate;
	}

	@RequestMapping(value = "/{employeeId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody CandidateDTO getEmployee(@PathVariable("employeeId") Long employeeId) {
		logger.info("getEmployee is calling : " + employeeId);
		Employee employee = employeePersonalInformationService.getEmployeeInfo(employeeId);
		// System.out.println("employee..."+employee);
		ShiftDTO shiftDto = null;
		String tenantId = null;
		ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes(); // get
		User user = loginService.getUser(employee.getEmployeeCode());																					// request
																												// object
		if (attr != null) {
			// String tenantId = attr.getRequest().getParameter("tenantId"); // find
			// parameter from request
			tenantId = attr.getRequest().getHeader("tenantId");
		}
		WeekOffPatternDTO weekOffDto = null;
		TMSWeekOffMasterPatternDto tmsWeekOffMasterPatternDto = null;
		CandidateDTO candidateDto = empPersonalAdaptor.databaseModelToCandidateDto(employee, shiftDto);
		candidateDto.getUserId();
		
		if(user.getActiveStatus()!=null)
		candidateDto.setLoginActiveDeactiveStatus(user.getActiveStatus());
		
		
		if (employee.getReportingToEmployee() != null) {
			Employee reportingEmployee = employeePersonalInformationService
					.findEmployeesById(employee.getReportingToEmployee());
			if (reportingEmployee != null) {
				candidateDto.setReportingToemployeeName(
						reportingEmployee.getFirstName() + " " + reportingEmployee.getLastName());
				candidateDto.setReportingToemployeeDesignation(reportingEmployee.getDesignation().getDesignationName());

			}
		}
		if (employee.getShiftId() != null) {
			// shiftDto = getshiftByRestTamplate(employee.getShiftId().toString(),tenantId);
			Shift shift = shiftService.findShift(employee.getShiftId());
			shiftDto = shiftAdaptor.databaseModelToUiDto(shift);
			candidateDto.setShiftName(shiftDto.getShiftFName());
			candidateDto.setShiftId(shiftDto.getShiftId());
		}
		if (employee.getPatternId() != null) {
			// tmsWeekOffMasterPatternDto=getPatternByRestTemplate(employee.getPatternId().toString(),tenantId);
			tmsWeekOffMasterPatternDto = tmsWeekOffMasterPatternAdaptor
					.databaseModelToUiDto(week_Off_PatternService.findTMSWeekOffMasterPattern(employee.getPatternId()));
			candidateDto.setPatternName(tmsWeekOffMasterPatternDto.getPatternName());
			candidateDto.setPatternId(tmsWeekOffMasterPatternDto.getPatternId());
		}

		return candidateDto;

	}

	/**
	 * @param file        This is the first parameter for taking file Input
	 * @param employeeDto This is the second parameter for getting Employee Object
	 *                    from UI
	 * @param req         This is the third parameter to maintain user session
	 * @throws ErrorHandling
	 */
	@RequestMapping(value = "/employeefile", method = RequestMethod.POST, consumes = "multipart/form-data")
	public void saveCandidateImage(@RequestPart("uploadFile") MultipartFile file,
			@RequestPart("info") CandidateDTO candidateDto, HttpServletRequest req) throws ErrorHandling {
		logger.info("saveEmp is calling : " + " : candidateId " + candidateDto + " : uploadFile" + file);
		employeePersonalInformationService.saveCandidateImage(candidateDto.getCandidateId(), file);
	}

	@RequestMapping(value = "/progressBar/{employeeId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ProgressBarDTO progressBar(@PathVariable("employeeId") Long employeeId) {
		System.out.println("Employee service is calling : " + employeeId);
		List<Object[]> progressBarObj = employeePersonalInformationService.getOnBoardProgessBar(employeeId);
		return empPersonalAdaptor.progressBarConversion(progressBarObj);

	}

	private ShiftDTO getshiftByRestTamplate(String shiftId, String tenantId) {
		logger.info("Shift Id----->" + shiftId);
		logger.info("getshiftByRestTamplate is calling : ");
		String url = environment.getProperty("application.shiftTemp");

		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders header = new HttpHeaders();
		header.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		header.set("tenantId", tenantId);
		Map<String, String> params = new HashMap<>();
		HttpEntity<String> request = new HttpEntity<String>(header);
		params.put("shiftId", shiftId);
		return restTemplate.exchange(url, HttpMethod.GET, request, ShiftDTO.class, params).getBody();
	}

	private WeekOffPatternDTO getWeekoffByRestTamplate(String patternId, String tenantId) {
		logger.info("patternId Id----->" + patternId);
		logger.info("getWeekoffByRestTamplate is calling : ");
		String url = environment.getProperty("application.weekoffpatternTemp");
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders header = new HttpHeaders();
		header.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		header.set("tenantId", tenantId);
		HttpEntity<String> request = new HttpEntity<String>(header);
		Map<String, String> params = new HashMap<>();
		params.put("patternId", patternId);
		return restTemplate.exchange(url, HttpMethod.GET, request, WeekOffPatternDTO.class, params).getBody();
	}

	// As per new requirement -new table TMSWeekOffMasterPattern
	private TMSWeekOffMasterPatternDto getPatternByRestTemplate(String patternId, String tenantId) {

		logger.info("patternId Id----->" + patternId);
		logger.info("getWeekoffByRestTamplate is calling : ");
		String url = environment.getProperty("application.weekoffpatternTemp");
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders header = new HttpHeaders();
		header.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		header.set("tenantId", tenantId);
		Map<String, String> params = new HashMap<>();
		params.put("patternId", patternId);
		HttpEntity<String> request = new HttpEntity<String>(header);
		// System.out.println("==========="+restTemplate.getForObject(url,
		// ShiftDTO.class, params));
		return (restTemplate.exchange(url, HttpMethod.GET, request, TMSWeekOffMasterPatternDto.class, params))
				.getBody();
	}

	/**
	 * @param candidateId This is to send onboard credentials Mail to the candidate
	 *                    based on employeeId
	 */
	@RequestMapping(value = "/onboardMail/{employeeId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody void onboardMail(@PathVariable("employeeId") Long employeeId) {
		System.out.println("onboardMail service is calling : " + employeeId);
		Employee employee = employeePersonalInformationService.findEmployeesById(employeeId);

		
		employeePersonalInformationService.onboardMail(employee);
//		logger.info("-----------------=====EmployeeLetter====-----------");
//		EmployeeLetter employeeLetter = new EmployeeLetter();
//		employeeLetter.setEmpId(employeeId);
//		Letter ltr = letterService.findLetterByType("Appointment Letter");
//		employeeLetter.setLetterDecription(ltr.getLetterDecription());
//		SimpleDateFormat sm = new SimpleDateFormat("dd-MMM-yyyy");
//		String doj = sm.format(employee.getDateOfJoining());
//		String currentDT = sm.format(new Date());
//		employeeLetter.setLetterDecription(ltr.getLetterDecription()
//				.replace("EMP_NAME", employee.getFirstName() + " " + employee.getLastName())
//				.replace("Date_Of_Joining", doj) 
//				.replace("City", employee.getCity().getCityName()
//						.replace("Design_Name", employee.getDesignation().getDesignationName()).replace("Company_Name",
//								employee.getCompany().getCompanyName().replace("Letter_Name", ltr.getLetterName())
//									 	.replace("Current_Date",currentDT).replace("CURRENT_SALARY", "10000.00")
//										)).replace("YEARLY_SALARY", "120000.00").replace("CURRENT_AD_BONUS", "10000.00"));
//		employeeLetter.setActiveStatus("AC");
//		employeeLetter.setLetterId(ltr.getLetterId());
//		employeeLetter.setEmpStatus("PEN");
//		employeeLetter.setHRStatus("PEN");
//		employeeLetter.setActiveStatus("AC");
//		empLetterService.saveLtr(employeeLetter); 

	}

}
