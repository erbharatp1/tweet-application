package com.csipl.hrms.candidate.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import com.csipl.hrms.common.exception.PayRollProcessException;
import com.csipl.hrms.common.util.AppUtils;
import com.csipl.hrms.common.util.DateUtils;
import com.csipl.hrms.common.util.ErrorHandling;
import com.csipl.hrms.config.JwtTokenUtil;
import com.csipl.hrms.config.TokenProvider;
import com.csipl.hrms.dto.candidate.CandidateDTO;
import com.csipl.hrms.dto.candidate.CandidateSearchDTO;
import com.csipl.hrms.dto.candidate.ProgressBarDTO;
import com.csipl.hrms.dto.employee.EmployeeCountDTO;
import com.csipl.hrms.dto.employee.PageIndex;
import com.csipl.hrms.model.candidate.Candidate;
import com.csipl.hrms.model.candidate.CandidateIdProof;
import com.csipl.hrms.model.common.User;
import com.csipl.hrms.model.employee.Employee;
import com.csipl.hrms.model.payrollprocess.PayrollControl;
import com.csipl.hrms.service.adaptor.CandidateAdaptor;
import com.csipl.hrms.service.authorization.LoginService;
import com.csipl.hrms.service.candidate.CandidateIdAddressService;
import com.csipl.hrms.service.candidate.CandidatePagingAndFilterService;
import com.csipl.hrms.service.candidate.CandidateService;
import com.csipl.hrms.service.employee.EmployeePersonalInformationService;
import com.csipl.hrms.service.payroll.PayrollControlService;

import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/candidate")
public class CandidateController {
	/**
	 * Logger declaration for knowing the flow of execution for debugging
	 */
	private static final Logger logger = LoggerFactory.getLogger(CandidateController.class);
	@Autowired
	CandidateService candidateService;

	@Autowired
	CandidatePagingAndFilterService candidatePagingAndFilterService;
	@Autowired
	PayrollControlService payrollControlService;
	@Autowired
	LoginService loginService;
	@Autowired
    private JwtTokenUtil jwtTokenUtil;
	@Autowired
	EmployeePersonalInformationService employeePersonalInformationService;
	int count;
	
	@Autowired
	CandidateIdAddressService candidateIdAddressService;

	CandidateAdaptor candidateAdaptor = new CandidateAdaptor();

	/**
	 * @param file
	 *            This is the first parameter for taking file Input
	 * @param employeeDto
	 *            This is the second parameter for getting Employee Object from UI
	 * @param req
	 *            This is the third parameter to maintain user session
	 * @throws Exception
	 */

	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully saved or update"),
			@ApiResponse(code = 401, message = "You are not authorized to save or update the resource"),
			@ApiResponse(code = 403, message = "You were trying to save resource is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })
	/**
	 * @param candidateDto
	 *            This is the first parameter for getting candidateDto Object from
	 *            UI
	 */
	@RequestMapping(method = RequestMethod.POST)
	public CandidateDTO saveCandidate(@RequestBody CandidateDTO candidateDto) throws Exception {

		logger.info("saveCandidate is calling : " + " : CandidateDTO " + candidateDto);
		String psMonth = DateUtils.getDateStringWithYYYYMMDD(candidateDto.getDateOfJoining());
		String processMonth = psMonth.toUpperCase();
		logger.info("processMonth  " + processMonth);
		//here we will check process month is locked or not if locked throw exception
		List<PayrollControl> unlockedPsMonthList = payrollControlService.findPCBypIsLockn(processMonth);
		if(unlockedPsMonthList == null || unlockedPsMonthList.size()==0) {
			throw new ErrorHandling(processMonth +"  is locked, Candidate can not join in this duration");
		}
		
		Candidate candidate = null;
		if (candidateDto.getCandidateId() == null) {
			logger.info("saveCandidate if block when candidate Id null.."+candidateDto.getDateOfJoining());
			candidate = candidateAdaptor.uiDtoToDatabaseModel(candidateDto);
		}

		else {
			logger.info("saveCandidate else  block  CandidateId " + candidateDto.getCandidateId());
		
			candidate = candidateAdaptor.uiDtoToDatabaseModel(candidateDto);
			
		}
	//	User user =loginService.findUserByUserName("COM-145-computronics.in");
		//String token = jwtTokenUtil.generateToken(user);
		User user = null;
		ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		String username = null;
		if (attr != null) {
			username = attr.getRequest().getParameter("username"); // find parameter from request
			if (username == null)
				username = attr.getRequest().getHeader("username");
		}
	    String name[] = username.split("-");
		if (name[0].equals("Administrator")) {
			user = loginService.findUserByUserName(name[0].trim());
		} else {
			user = loginService.findUserByUserName(username.trim());
		}   	
             Map<String, Object> claims= new HashMap<>();
             TokenProvider tokenProvider = TokenProvider.getTokenProvider();
              claims.put("user", TokenProvider.getTokenProvider());
              final String token=  jwtTokenUtil.doGenerateTokenByClaims( claims,user.getLoginName(),user.getUserPassword());
               System.out.println("============token==========="+token);
		
		
		return candidateAdaptor.databaseModelToUiDto(candidateService.save(candidate,token));

	}

	@RequestMapping(value = "/candidates", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<CandidateDTO> getCandidateInformationDetails(@RequestBody CandidateSearchDTO candidateSearcDto)
			throws PayRollProcessException { //
		logger.info(" active employees is calling :");

		List<Object[]> candidateList = candidatePagingAndFilterService
				.getCandidateByPagingAndFilter(candidateSearcDto.getCompanyId(), candidateSearcDto);

		return candidateAdaptor.modeltoDTOList(candidateList, candidateSearcDto);
	}

	@RequestMapping(value = "/statusupdate/{candidateId}/{status}/{reason}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public void changeCandidateStatus(@PathVariable("candidateId") Long candidateId,
			@PathVariable("status") String candidateStatus, @PathVariable("reason") String reason)
			throws PayRollProcessException { //
		logger.info(" changeCandidateStatus is calling :");
		candidateService.changeCandidateStatus(candidateStatus, candidateId, reason);
	}

	@RequestMapping(value = "/tabs_validation/{candidateId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Long> tabSubmitValidation(@PathVariable("candidateId") Long candidateId)
			throws PayRollProcessException {
		logger.info(" changeCandidateStatus is calling :");
		List<Object[]> tabValidtionList = candidateService.tabSubmitValidation(candidateId);
		List<Long> tabValidation = candidateAdaptor.validationConvert(tabValidtionList);
		return tabValidation;
	}

	@GetMapping(value = "/candidateCount/{companyId}/{pageSize}/{candidateStatus}")
	public @ResponseBody EmployeeCountDTO getCandidateCount(@PathVariable("companyId") String companyId,
			@PathVariable("pageSize") String pageSize, @PathVariable("candidateStatus") String candidateStatus,
			HttpServletRequest req) throws PayRollProcessException {

		List<PageIndex> pageIndexList = new ArrayList<PageIndex>();
		System.out.println("candidateStatus" + candidateStatus);
		Long longCompanyId = Long.parseLong(companyId);
		int parPageRecord = Integer.parseInt(pageSize);
		EmployeeCountDTO employeeCountDto = new EmployeeCountDTO();
		candidatePagingAndFilterService.getCandidateCount(longCompanyId, candidateStatus, employeeCountDto);
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

	@RequestMapping(value = "/{candidateId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody CandidateDTO get(@PathVariable("candidateId") Long candidateId) {
		System.out.println("candidate service is calling : " + candidateId);
		Candidate newJoinee = candidateService.findCandidate(candidateId);
		Employee employee = null;
		if(newJoinee.getReportingToEmployee() != null) {
			employee = employeePersonalInformationService
					.findEmployeesById(Long.parseLong(newJoinee.getReportingToEmployee()));
		}
			 
			 String ShiftName = candidateService.findShiftName(newJoinee.getShiftId());
			 
			 // new requirement -table is changed in parent child  and getting weeklyoff data from TMSWeekOffMasterPattern
				String patternName = candidateService.findWeekOffPatternName(newJoinee.getPatternId());
				return candidateAdaptor.databaseModelToCandidateDto(newJoinee, employee, ShiftName, patternName);
		
	}

	@RequestMapping(value = "/code/{candidateCode}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody CandidateDTO getCandidate(@PathVariable("candidateCode") String candidateCode) {
		System.out.println("Employee service is calling : " + candidateCode);
		Candidate newJoinee = candidateService.findCandidateByCode(candidateCode);
		return candidateAdaptor.databaseModelToUiDto(newJoinee);

	}

	/**
	 * @param file
	 *            This is the first parameter for taking file Input
	 * @param employeeDto
	 *            This is the second parameter for getting Employee Object from UI
	 * @param req
	 *            This is the third parameter to maintain user session
	 * @throws ErrorHandling
	 */
	@RequestMapping(value = "/candidatefile", method = RequestMethod.POST, consumes = "multipart/form-data")
	public void saveCandidateImage(@RequestPart("uploadFile") MultipartFile file,
			@RequestPart("info") CandidateDTO candidateDto, HttpServletRequest req) throws ErrorHandling {
		logger.info("saveEmp is calling : " + " : candidateId " + candidateDto + " : uploadFile" + file);
		candidateService.saveCandidateImage(candidateDto.getCandidateId(), file);
	}

	/**
	 * @param candidateId
	 *            This is the parameter for taking candidateId Input to get Progress
	 *            Bar Information
	 */
	@RequestMapping(value = "/progressBar/{candidateId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ProgressBarDTO progressBar(@PathVariable("candidateId") Long candidateId) {
		logger.info("progressBar method  is calling   candidateId>> " + candidateId);
		return candidateAdaptor.databaseModelToUiDtoProgressBar(candidateService.progressBar(candidateId));

	}

	/**
	 * @param candidateId
	 *            This is to resend Mail to the candidate based on candidateId
	 * @throws ErrorHandling 
	 */
	@RequestMapping(value = "/resendMail/{candidateId}/{comment}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody void resendLink(@PathVariable("candidateId") Long candidateId,
			@PathVariable("comment") String comment) throws ErrorHandling {
		System.out.println("comment is ------> : " + comment);
		Candidate candidate = candidateService.findCandidate(candidateId);
		String psMonth = DateUtils.getDateStringWithYYYYMMDD(candidate.getDateOfJoining());
		String processMonth = psMonth.toUpperCase();
		logger.info("processMonth  " + processMonth);
		//here we will check process month is locked or not if locked throw exception
		List<PayrollControl> unlockedPsMonthList = payrollControlService.findPCBypIsLockn(processMonth);
		if(unlockedPsMonthList == null || unlockedPsMonthList.size()==0) {
			throw new ErrorHandling(processMonth +"  is locked, Candidate can not join in this duration");
		}
		System.out.println("checkapproach============" + candidate.getCheckApproach());
		User user = null;
		ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		String username = null;
		if (attr != null) {
			username = attr.getRequest().getParameter("username"); // find parameter from request
			if (username == null)
				username = attr.getRequest().getHeader("username");
		}
	    String name[] = username.split("-");
		if (name[0].equals("Administrator")) {
			user = loginService.findUserByUserName(name[0].trim());
		} else {
			user = loginService.findUserByUserName(username.trim());
		}   	
             Map<String, Object> claims= new HashMap<>();
             TokenProvider tokenProvider = TokenProvider.getTokenProvider();
              claims.put("user", TokenProvider.getTokenProvider());
              final String token=  jwtTokenUtil.doGenerateTokenByClaims( claims,user.getLoginName(),user.getUserPassword());
               System.out.println("============token==========="+token);
		candidateService.resendMail(candidate, comment,token);

	}

	@RequestMapping(value = "/changeStatus/{candidateId}/{status}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public void changeStatus(@PathVariable("candidateId") Long candidateId,@PathVariable("status") String status)
			throws PayRollProcessException { //
		logger.info(" changeCandidateStatus is calling :");
		candidateService.changeStatus(status, candidateId);
	}
	
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully saved or update"),
			@ApiResponse(code = 401, message = "You are not authorized to save or update the resource"),
			@ApiResponse(code = 403, message = "You were trying to save resource is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })
	/**
	 * @param candidateDto
	 *            This is the first parameter for getting candidateDto Object from
	 *            UI
	 */
	@RequestMapping(value = "/login/{candidateId}/{candidateLoginCode}" ,method = RequestMethod.GET)
	public CandidateDTO  candidateLogin(@PathVariable("candidateId") Long candidateId,@PathVariable("candidateLoginCode") String candidateLoginCode)  {

		logger.info("saveCandidate is calling : " + " : CandidateDTO " + candidateId +"candidateLoginCode.."+candidateLoginCode);
		Candidate candidate = candidateService.findCandidate(candidateId);
		
		logger.info("Candidate status"+candidate.getCandidateStatus());
		CandidateDTO candidateDto = new CandidateDTO();
		candidateDto.setCandidateId(candidateId);
		candidateDto.setCompanyId(candidate.getCompanyId());
		if(candidate.getCandidateStatus().equals("IN"))
		{
		if(candidateId!=null && (candidateLoginCode.trim()!=null)) {
			System.out.println(" in side if candidateLoginCode,,,"+candidateLoginCode);
			
			if(candidate.getPassword().equals(candidateLoginCode.trim())) {
				System.out.println("successfully loged in");
				 candidateDto=candidateAdaptor.databaseModelToUiDto(candidate);
			}
			else {
				
				logger.info("Error! Invalid credentials");
				candidateDto.setMessage("Error! Invalid credentials");
			}
		}
		else {
			
			logger.info("Please Check your UserName and Password ");
			candidateDto.setMessage("Please Check your UserName and Password ");
		}
		}
		else
		{
			logger.info("Already Submitted SucessFully");
			candidateDto.setMessage("Profile Already Submitted");
		}
				
	return candidateDto;
	}
	

}
