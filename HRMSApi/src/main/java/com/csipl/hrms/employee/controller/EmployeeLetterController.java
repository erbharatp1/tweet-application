package com.csipl.hrms.employee.controller;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import com.csipl.hrms.common.exception.PayRollProcessException;
import com.csipl.hrms.common.util.ErrorHandling;
import com.csipl.hrms.config.JwtTokenUtil;
import com.csipl.hrms.config.TokenProvider;
import com.csipl.hrms.dto.employee.EmployeeLetterDTO;
import com.csipl.hrms.model.common.Company;
import com.csipl.hrms.model.common.User;
import com.csipl.hrms.model.employee.ApprovalHierarchyMaster;
import com.csipl.hrms.model.employee.AuthorizedSignatory;
import com.csipl.hrms.model.employee.Employee;
import com.csipl.hrms.model.employee.EmployeeLetter;
import com.csipl.hrms.model.employee.EmployeeLettersTransaction;
import com.csipl.hrms.model.employee.Letter;
import com.csipl.hrms.model.employee.LetterDaclaration;
import com.csipl.hrms.service.adaptor.EmployeeLetterAdaptor;
import com.csipl.hrms.service.authorization.LoginService;
import com.csipl.hrms.service.employee.ApprovalHierarchyMasterService;
import com.csipl.hrms.service.employee.AuthorizedSignatoryService;
import com.csipl.hrms.service.employee.EmployeeLetterService;
import com.csipl.hrms.service.employee.EmployeePersonalInformationService;
import com.csipl.hrms.service.employee.LetterDaclarationService;
import com.csipl.hrms.service.employee.LetterService;
import com.csipl.hrms.service.organization.CompanyService;
import com.csipl.hrms.service.util.SalaryPdfReport;
import com.csipl.tms.dto.common.EntityCountDTO;
import com.csipl.tms.dto.common.PageIndex;
import com.csipl.tms.dto.common.SearchDTO;

@RestController
@RequestMapping("/empLetter")
public class EmployeeLetterController {
	/**
	 * Logger declaration for knowing the flow of execution for debugging
	 */
	private static final Logger logger = LoggerFactory.getLogger(EmployeeLetterController.class);
	@Autowired
	AuthorizedSignatoryService authorizedSignatoryService;
	@Autowired
	private EmployeeLetterService empLetterService;
	@Autowired
	private EmployeeLetterAdaptor empLetterAdaptor;

	@Autowired
	private CompanyService companyService;

	@Autowired
	private LetterService letterService;

	@Autowired
	private ApprovalHierarchyMasterService approvalHierarchyMasterService;
	
	@Autowired
	private LoginService loginService;
	@Autowired
    private JwtTokenUtil jwtTokenUtil;
	
	@Autowired
	private LetterDaclarationService letterDaclarationService;
	
	@Autowired
	private EmployeePersonalInformationService employeePersonalInformationService;
	@PostMapping(path = "/save")
	public @ResponseBody EmployeeLetter saveLetter(@RequestBody EmployeeLetterDTO ltrDTO, HttpServletRequest req) {
		logger.info("saveLetter is calling : EmpLetterDTO " + ltrDTO);
		EmployeeLetter empLtr = empLetterAdaptor.uiDtoToDatabaseModel(ltrDTO);
		// empLetterService.saveLtr(empLtr);
		logger.info("saveLetter is end  :" + empLtr);
		return empLetterService.saveLtr(empLtr);
	}

	@GetMapping(path = "/fetchList/{companyId}/{empStatus}/{HRStatus}")
	public @ResponseBody List<EmployeeLetterDTO> getAllEmpLetters(@PathVariable("companyId") Long companyId,
			@PathVariable("empStatus") String empStatus, @PathVariable("HRStatus") String HRStatus)
			throws ErrorHandling, PayRollProcessException {
		logger.info("getAllLetters is calling :  ");

		List<EmployeeLetter> empLetterList = empLetterService.findAllEmpLetter(companyId, empStatus, HRStatus);
		logger.info("getAllLetters is end : Letter List " + empLetterList);
		if (empLetterList.size() > 0)
			return empLetterAdaptor.databaseModelToUiDtoList(empLetterList);
		else
			throw new ErrorHandling("Letters are not available in company");
	}

	@GetMapping(value = "/employeeView/{companyId}")
	public @ResponseBody List<EmployeeLetterDTO> getAllPendingEmployeeDocumentView(@PathVariable("companyId") Long companyId,HttpServletRequest req) {
		logger.info("getAllPendingEmployeeDocumentView is calling : start " );
		List<Object[]> pendingEmployeeDocumentViewList = empLetterService.findAllPendingEmployeeDocumentView();
		List<ApprovalHierarchyMaster> approvalHierarchyMasters = approvalHierarchyMasterService
				.getLetterApprovals(companyId);
		List<EmployeeLetterDTO> getAllPendingEmployeeDocumentView=empLetterAdaptor.databaseModelToUiDtoEmployeeDocumentViewListTx(pendingEmployeeDocumentViewList,
				approvalHierarchyMasters);
		logger.info("getAllPendingEmployeeDocumentView is calling :  end " );
		return getAllPendingEmployeeDocumentView;
	}

	@GetMapping(value = "/employeeDocument/{empId}/{letterId}")
	public @ResponseBody EmployeeLetterDTO getEmployeeLetterById(@PathVariable("empId") Long empId,
			@PathVariable("letterId") Long letterId, HttpServletRequest req) {
		return empLetterAdaptor.databaseModelToUiDto(empLetterService.findEmployeeLetterById(empId, letterId));
	}

	@GetMapping(value = "/fetchLetterList/{companyId}")
	public @ResponseBody List<EmployeeLetterDTO> fetchLetterList(@PathVariable("companyId") Long companyId,
			HttpServletRequest req) {
		List<Object[]> empLetterList = empLetterService.fetchLetterList(companyId);
		return empLetterAdaptor.databaseModelToUiDtoEmployeeLetterMonthwiseList(empLetterList);
	}

	@GetMapping(value = "/getMasterTemplateList/{companyId}/{empId}/{letterId}")
	public EmployeeLetterDTO getMasterTemplateList(@PathVariable("companyId") Long companyId,
			@PathVariable("empId") Long empId, @PathVariable("letterId") Long letterId, HttpServletRequest req)
			throws ErrorHandling {

		logger.info("generateMasterTemplate Start...");

		EmployeeLetter letterList = empLetterService.findEmployeeLetterById(empId, letterId);
		EmployeeLetterDTO letterDTO = empLetterAdaptor.databaseModelToUiDto(letterList);

		if (letterList.getLetterDecription() == null) {
			throw new ErrorHandling("Departments Are Not Available In Company");
		}
		logger.info("masterTemplateList ..." + letterDTO);

		return letterDTO;
	}

	@GetMapping(value = "/generateMasterTemplatePDF/{companyId}/{empId}/{letterId}")
	public StreamingResponseBody generateMasterTemplatePDF(@PathVariable("companyId") Long companyId,
			@PathVariable("empId") Long empId, @PathVariable("letterId") Long letterId, HttpServletRequest req,
			HttpServletResponse response) {

		logger.info("generateMasterTemplate Start...");

		Company company = companyService.getCompany(companyId);
		EmployeeLetter letterList = empLetterService.findEmployeeLetterById(empId, letterId);
		EmployeeLetterDTO letterDTO = empLetterAdaptor.databaseModelToUiDto(letterList);
		Letter ltrList = letterService.findLetter(letterId);
		AuthorizedSignatory authorizedSignatory = authorizedSignatoryService
				.findAuthorizedSignatoryById(ltrList.getLetterId());
		LetterDaclaration letterDaclaration = letterDaclarationService.findLetterDaclarationById(ltrList.getLetterId());
		logger.info("EmployeeLetterController.generateMasterTemplatePDF()");
		logger.info("masterTemplateList ...");
		response.setContentType("application/pdf");
		response.setHeader("Content-Disposition", "attachment; filename=\"" + ltrList.getLetterType() + ".pdf\"");
		response.setHeader(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, HttpHeaders.CONTENT_DISPOSITION);

		ByteArrayInputStream bis = new SalaryPdfReport().masterTemplatePdf(company, letterList, letterDTO, ltrList,
				authorizedSignatory,letterDaclaration);
		return outputStream -> {
			int nRead;
			byte[] data = new byte[1024];
			while ((nRead = bis.read(data, 0, data.length)) != -1) {
				outputStream.write(data, 0, nRead);
			}
		};

	}

	@GetMapping(value = "/previewPDF/{companyId}/{empId}/{letterId}")
	public StreamingResponseBody previewPDF(@PathVariable("companyId") Long companyId,
														   @PathVariable("empId") Long empId, @PathVariable("letterId") Long letterId, HttpServletRequest req,
														   HttpServletResponse response) {

		logger.info("generateMasterTemplate Start...");

		Company company = companyService.getCompany(companyId);
		EmployeeLetter letterList = empLetterService.findEmployeeLetterById(empId, letterId);
		EmployeeLetterDTO letterDTO = empLetterAdaptor.databaseModelToUiDto(letterList);
		Letter ltrList = letterService.findLetter(letterId);
		AuthorizedSignatory authorizedSignatory = authorizedSignatoryService
				.findAuthorizedSignatoryById(ltrList.getLetterId());
		LetterDaclaration letterDaclaration = letterDaclarationService.findLetterDaclarationById(ltrList.getLetterId());
		logger.info("EmployeeLetterController.generateMasterTemplatePDF()");
		logger.info("masterTemplateList ...");
		response.setContentType("application/pdf");
		response.setHeader("Content-Disposition", "inline; filename=\"" + ltrList.getLetterType() + ".pdf\"");
		response.setHeader(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, HttpHeaders.CONTENT_DISPOSITION);

		ByteArrayInputStream bis = new SalaryPdfReport().masterTemplatePdf(company, letterList, letterDTO, ltrList,
				authorizedSignatory,letterDaclaration);
		return outputStream -> {
			int nRead;
			byte[] data = new byte[1024];
			while ((nRead = bis.read(data, 0, data.length)) != -1) {
				outputStream.write(data, 0, nRead);
			}
		};

	}


	@GetMapping(value = "/generateMasterTemplatePDF/{companyId}/{empId}/{letterId}/{empLetterId}")
	public StreamingResponseBody generateLetterMasterTemplatePDF(@PathVariable("companyId") Long companyId,
			@PathVariable("empId") Long empId, @PathVariable("letterId") Long letterId,
			@PathVariable("empLetterId") Long empLetterId, HttpServletRequest req, HttpServletResponse response) {

		logger.info("generateMasterTemplate Start...");

		Company company = companyService.getCompany(companyId);
		EmployeeLetter letterList = empLetterService.findEmployeeLetterByEmpLetterId(empId, letterId, empLetterId);
		EmployeeLetterDTO letterDTO = empLetterAdaptor.databaseModelToUiDto(letterList);
		Letter ltrList = letterService.findLetter(letterId);
		AuthorizedSignatory authorizedSignatory = authorizedSignatoryService
				.findAuthorizedSignatoryById(ltrList.getLetterId());
		LetterDaclaration letterDaclaration = letterDaclarationService.findLetterDaclarationById(ltrList.getLetterId());
		Employee emp = employeePersonalInformationService.getEmployeeInfo(letterList.getEmpId());
		if(emp!=null) {
			letterDTO.setEmployeeName(emp.getFirstName()+" "+emp.getLastName());
		}
		logger.info("EmployeeLetterController.generateMasterTemplatePDF()");
		logger.info("masterTemplateList ...");
		response.setContentType("application/pdf");
		response.setHeader("Content-Disposition", "attachment; filename=\"" + ltrList.getLetterType() + ".pdf\"");
		response.setHeader(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, HttpHeaders.CONTENT_DISPOSITION);

		ByteArrayInputStream bis = new SalaryPdfReport().masterTemplatePdf(company, letterList, letterDTO, ltrList,
				authorizedSignatory,letterDaclaration);
		return outputStream -> {
			int nRead;
			byte[] data = new byte[1024];
			while ((nRead = bis.read(data, 0, data.length)) != -1) {
				outputStream.write(data, 0, nRead);
			}
		};

	}

	@GetMapping(value = "/pendingLetters/{companyId}/{employeeId}")
	public @ResponseBody List<EmployeeLetterDTO> getPendingLetters(@PathVariable("companyId") Long companyId,
			@PathVariable("employeeId") Long employeeId) {

		List<Object[]> empLetterList = empLetterService.findPendingLetterList(companyId, employeeId);

		List<EmployeeLetterDTO> employeeLetterDTOList = empLetterAdaptor
				.databaseModelToUiDtoEmployeeLetterList(empLetterList);

		return employeeLetterDTOList;
	}

	// @GetMapping(value = "/pendingLetterList/{companyId}/{employeeId}")
	@RequestMapping(value = "/pendingLetterList/{companyId}/{employeeId}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody List<EmployeeLetterDTO> getPendingLetterList(@PathVariable("companyId") Long companyId,
			@PathVariable("employeeId") Long employeeId, @RequestBody SearchDTO searcDto) {

		List<Object[]> empLetterList = empLetterService.findPendingLetterList(companyId, employeeId, searcDto);

		List<EmployeeLetterDTO> employeeLetterDTOList = empLetterAdaptor
				.databaseModelToUiDtoEmployeeLetterList(empLetterList);

		logger.info("pendingLetterList calling...");
		return employeeLetterDTOList;
	}

	// @GetMapping(value = "/nonPendingLetterList/{companyId}/{employeeId}")
	@RequestMapping(value = "/nonPendingLetterList/{companyId}/{employeeId}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody List<EmployeeLetterDTO> getNonPendingLetterList(@PathVariable("companyId") Long companyId,
			@PathVariable("employeeId") Long employeeId, @RequestBody SearchDTO searcDto) {
		List<Object[]> empLetterList = empLetterService.findNonPendingLetterList(companyId, employeeId, searcDto);

		logger.info("NonPendingLetterList Method ...");
		return empLetterAdaptor.databaseModelToUiDtoEmployeeLetterListForNonPending(empLetterList);
	}

	// @GetMapping(value =
	// "/letterApprovalPendingCount/{companyId}/{employeeId}/{pageSize}/{status}")
	@RequestMapping(value = "/letterApprovalPendingCount/{companyId}/{employeeId}/{pageSize}/{status}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody EntityCountDTO getLetterApprovalPendingCount(@PathVariable("companyId") Long companyId,
			@PathVariable("pageSize") String pageSize, @PathVariable("employeeId") Long employeeId,
			@PathVariable("status") String status, @RequestBody SearchDTO searcDto) throws PayRollProcessException {
		int count;
		List<PageIndex> pageIndexList = new ArrayList<PageIndex>();
//		List<Object[]> empLetterList = empLetterService.findPendingLetterList(companyId, employeeId);
//
//		List<EmployeeLetterDTO> employeeLetterDTOList = empLetterAdaptor
//				.databaseModelToUiDtoEmployeeLetterList(empLetterList);
		List<Object[]> empLetterList = empLetterService.findPendingLetterList(companyId, employeeId, searcDto);
		// Long longCompanyId = Long.parseLong(companyId);
		int parPageRecord = Integer.parseInt(pageSize);
		EntityCountDTO entityCountDto = new EntityCountDTO();
		System.out.println(" getLetterApprovalPendingCount calling--------employeeLetterDTOList.size()-----"
				+ empLetterList.size());
		count = empLetterList.size();
		System.out.println(" letter approval Pending count" + count);
		int pages = (count + parPageRecord - 1) / parPageRecord;

		for (int i = 1; i <= pages; i++) {
			PageIndex pageIndex = new PageIndex();
			pageIndex.setPageIndex(i);
			pageIndexList.add(pageIndex);
		}
		entityCountDto.setPageIndexs(pageIndexList);
		entityCountDto.setCount(count);
		return entityCountDto;
	}

	// @GetMapping(value =
	// "/letterApprovalNonPendingCount/{companyId}/{employeeId}/{pageSize}/{status}")
	@RequestMapping(value = "/letterApprovalNonPendingCount/{companyId}/{employeeId}/{pageSize}/{status}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody EntityCountDTO getLetterApprovalNonPendingCount(@PathVariable("companyId") Long companyId,
			@PathVariable("pageSize") String pageSize, @PathVariable("employeeId") Long employeeId,
			@PathVariable("status") String status, @RequestBody SearchDTO searcDto) throws PayRollProcessException {
		int count;
		List<PageIndex> pageIndexList = new ArrayList<PageIndex>();

		// Long longCompanyId = Long.parseLong(companyId);
		int parPageRecord = Integer.parseInt(pageSize);
		EntityCountDTO entityCountDto = new EntityCountDTO();
		List<Object[]> empLetterList = empLetterService.findNonPendingLetterList(companyId, employeeId, searcDto);
		// empLetterService.getLetterApprovalNonPendingCount(longCompanyId, employeeId,
		// entityCountDto);
		// count = entityCountDto.getCount();
		count = empLetterList.size();
		System.out.println("Count :" + count);
		int pages = (count + parPageRecord - 1) / parPageRecord;
		System.out.println("letter approval Pages : -" + pages + " letter approval nonPending count" + count);
		for (int i = 1; i <= pages; i++) {
			PageIndex pageIndex = new PageIndex();
			pageIndex.setPageIndex(i);
			pageIndexList.add(pageIndex);
		}
		entityCountDto.setPageIndexs(pageIndexList);
		entityCountDto.setCount(count);
		return entityCountDto;
	}

	@PutMapping(value = "/letterStatusUpdate")
	public void letterStatusUpdate(@RequestBody EmployeeLetterDTO employeeLetterDTO, HttpServletRequest req) {
		logger.info("save EployeeLettersTransaction is calling :  " + employeeLetterDTO);
		EmployeeLettersTransaction employeeLettersTransaction = new EmployeeLettersTransaction();

		employeeLettersTransaction.setEmployeeLetterTransactionId(employeeLetterDTO.getEmployeeLetterTransactionId());
		employeeLettersTransaction.setStatus(employeeLetterDTO.getStatus());
		employeeLettersTransaction.setApprovalRemarks(employeeLetterDTO.getApprovalRemarks());
		employeeLettersTransaction.setApprovalId(employeeLetterDTO.getApprovalId());
		employeeLettersTransaction.setDateUpdate(new Date());
		empLetterService.updateById(employeeLettersTransaction);

	}

	@GetMapping(value = "/findemployeeLetterByEmployeeId/{employeeId}")
	public @ResponseBody List<EmployeeLetterDTO> findemployeeLetterByEmployeeId(
			@PathVariable("employeeId") Long employeeId, HttpServletRequest req) {
		List<Object[]> empLetterList = empLetterService.employeeLetterByEmployeeId(employeeId);
		return empLetterAdaptor.databaseModelToUiDtoEmployeeLtrList(empLetterList);
	}

	@PutMapping(value = "/updateAfterRealeseLetter")
	public void updateAfterRealeseLetter(@RequestBody EmployeeLetterDTO employeeLetterDTO, HttpServletRequest req) {
		logger.info("save EployeeLettersTransaction is calling :  " + employeeLetterDTO);
		EmployeeLetter employeeLetter = new EmployeeLetter();

		employeeLetter.setEmpLetterId(employeeLetterDTO.getEmpLetterId());
		employeeLetter.setActiveStatus(employeeLetterDTO.getActiveStatus());

		empLetterService.updateAfterRealeseLetter(employeeLetter);

	}

	@GetMapping(value = "/fetchLetterListByStatus/{companyId}/{activeStatus}")
	public @ResponseBody List<EmployeeLetterDTO> fetchLetterListByStatus(@PathVariable("companyId") Long companyId,
			@PathVariable("activeStatus") String activeStatus, HttpServletRequest req) {
		List<Object[]> empLetterList = empLetterService.findAllEmpLetterActiveStatus(companyId, activeStatus);
		return empLetterAdaptor.databaseModelToUiDtoEmployeeLetterMonthwiseList(empLetterList);
	}

	@GetMapping(value = "/fetchLetterListByStatus/{companyId}/{letterId}/{employeeId}")
	public @ResponseBody EmployeeLetterDTO generateLetter(@PathVariable("companyId") Long companyId,
			@PathVariable("letterId") Long letterId, @PathVariable("employeeId") Long employeeId) {

		return empLetterAdaptor.databaseModelToUiDto(empLetterService.generateLetter(companyId, letterId, employeeId));
	}

	@GetMapping(value = "/findAllEmployeeLetterView/{employeeId}")
	public @ResponseBody List<EmployeeLetterDTO> findAllEmployeeLetterView(@PathVariable("employeeId") Long employeeId,
			HttpServletRequest req) {
		List<Object[]> empLetterList = empLetterService.findAllEmployeeLetterView(employeeId);
		return empLetterAdaptor.databaseModelToUiDtoEmployeeLetterMonthwiseList(empLetterList);
	}

//	@PutMapping(value = "/updateAfterRealeseLetter/{empLetterId}/{declarationStatus}/{empId}")
//	public void updateDeclarationStatus(@PathVariable("empLetterId") Long empLetterId,@PathVariable("declarationStatus") Long declarationStatus,@PathVariable("empId") Long empId, HttpServletRequest req) {
//		logger.info("save updateDeclarationStatus is calling :  ");
//		//empLetterService.updateDeclarationStatus(empLetterId,declarationStatus,empId);
//
//	}
	@PutMapping(value = "/updateDeclarationStatus")
	public void updateDeclarationStatus(@RequestBody EmployeeLetterDTO employeeLetterDTO, HttpServletRequest req) {
		logger.info("save EployeeLettersTransaction is calling :  " + employeeLetterDTO); 
		empLetterService.updateDeclarationStatus(employeeLetterDTO.getEmpLetterId(),employeeLetterDTO.getDeclarationStatus(),employeeLetterDTO.getEmpId());
	}

	@GetMapping(value = "/findOneEmpLetter/{empLetterId}")
	public @ResponseBody EmployeeLetterDTO findOneEmpLetter(@PathVariable("empLetterId") Long empLetterId
		, HttpServletRequest req) {
		logger.info("EmployeeLetterController.findOneEmpLetter()");
		return empLetterAdaptor.databaseModelToUiDto(empLetterService.findOneEmpLetter(empLetterId));
	}
	
	@GetMapping(value = "/fetchLetterListNew/{empLetterId}")
	public @ResponseBody List<EmployeeLetterDTO> fetchLetterListNew(@PathVariable("empLetterId") Long empLetterId,
			HttpServletRequest req) {
		List<Object[]> empLetterList = empLetterService.fetchLetterList(1L);
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

           	EmployeeLetter letterList = empLetterService.findEmployeeLetterByEmpLetterId(2633L, 13L, empLetterId);
		empLetterService.triggerDeclarationMail(letterList, token);
		
		return empLetterAdaptor.databaseModelToUiDtoEmployeeLetterMonthwiseList(empLetterList);
	}
}
