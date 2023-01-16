package com.csipl.hrms.payroll.controller;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.MediaType;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import com.csipl.hrms.common.exception.PayRollProcessException;
import com.csipl.hrms.common.util.ErrorHandling;
import com.csipl.hrms.common.util.HrmsGlobalConstantUtil;
import com.csipl.hrms.dto.payroll.LoanEMIDTO;
import com.csipl.hrms.dto.payroll.LoanIssueDTO;
import com.csipl.hrms.dto.recruitment.PositionAllocationXrefDTO;
import com.csipl.hrms.model.common.Company;
import com.csipl.hrms.model.employee.EmployeeBank;
import com.csipl.hrms.model.organisation.Grade;
import com.csipl.hrms.model.payroll.LoanEMI;
import com.csipl.hrms.model.payroll.LoanIssue;
import com.csipl.hrms.org.BaseController;
import com.csipl.hrms.service.adaptor.LoanEmiAdaptor;
import com.csipl.hrms.service.adaptor.LoanIssueAdaptor;
import com.csipl.hrms.service.adaptor.ReportPayOutAdaptor;
import com.csipl.hrms.service.organization.CompanyService;
import com.csipl.hrms.service.organization.GradeService;
import com.csipl.hrms.service.payroll.LoanIssueService;
import com.csipl.hrms.service.util.LoanEmiPdf;
import com.csipl.tms.dto.common.EntityCountDTO;
import com.csipl.tms.dto.common.PageIndex;
import com.csipl.tms.dto.common.SearchDTO;

@RestController
public class LoanIssueController {

	/**
	 * Logger declaration for knowing the flow of execution for debugging
	 */
	private static final Logger logger = LoggerFactory.getLogger(LoanIssueController.class);
	LoanIssueAdaptor loanIssueAdaptor = new LoanIssueAdaptor();
	LoanEmiAdaptor loanEmiAdaptor = new LoanEmiAdaptor();

	@Autowired
	LoanIssueService loanIssueService;
	@Autowired
	CompanyService companyService;

	@Autowired
	GradeService gradeService;

	@Autowired
	JavaMailSender mailSender;

	/**
	 * @param loanIssueDto This is the first parameter for getting loanIssue Object
	 *                     from UI
	 * @param req          This is the second parameter to maintain user session
	 */
	@RequestMapping(path = "/loanIssue", method = RequestMethod.POST)
	public void loanIssue(@RequestBody LoanIssueDTO loanIssueDTO, HttpServletRequest req) {
//		logger.info("loanIssueDto :" + loanIssueDTO.toString());
	
		LoanIssue loanIssue = loanIssueAdaptor.uiDtoToDatabaseModel(loanIssueDTO);
		// boolean newFlag = loanIssue != null && loanIssue.getTransactionNo() != null ?
		// false : true;
		// editLogInfoWithoutGroup(loanIssue, newFlag, req);
		loanIssueService.save(loanIssue);
	}
	
	
	
	@RequestMapping(path = "/loanEmi", method = RequestMethod.POST)
	public void loanEmi(@RequestBody LoanEMIDTO loanEMIDTO, HttpServletRequest req) {
		LoanIssue loanIssue=new LoanIssue();
		LoanEMI loanEMI = loanIssueAdaptor.uiDtoToDatabaseModelListSettlement(loanEMIDTO , loanIssue);
		loanIssueService.saveLoanEmi(loanEMI);
		
	}

	/**
	 * to get List of loanIssue objects from database based on companyId
	 * 
	 * @throws PayRollProcessException
	 */
	@RequestMapping(path = "/loanIssue", method = RequestMethod.GET)
	public @ResponseBody List<LoanIssueDTO> findAllLoanIssue(@RequestParam("companyId") String companyId,
			HttpServletRequest req) throws PayRollProcessException {
		logger.info("findAllLoanIssue controller companyId is :" + companyId);
		Long companyID = Long.parseLong(companyId);
		// return
		// loanIssueAdaptor.databaseObjModelToUiDtoList(loanIssueService.getAllLoanIssue(companyID));
		List<LoanIssue> loanIssueList = loanIssueService.findAllLoanIssue(companyID);
	
		return loanIssueAdaptor.databaseModelToUiDtoList(loanIssueList);
	}

	/**
	 * to get LoanIssue from database based on loanIssueId(Primary Key)
	 */
	@RequestMapping(path = "/loanIssueId", method = RequestMethod.GET)
	public @ResponseBody LoanIssueDTO findLoanIssue(@RequestParam("loanIssueId") String loanIssueId,
			HttpServletRequest req) {

		logger.info("findLoanIssue controller loanIssueId is :" + loanIssueId);
		Long loanId = Long.parseLong(loanIssueId);
		LoanIssue loanIssue = loanIssueService.getLoanEmiDetailsList(loanId);
		Grade grade = gradeService.findGradeDetails(loanIssue.getEmployee().getGradesId());

		LoanIssueDTO loanIssueDto = loanIssueAdaptor.databaseModelToUiDto(loanIssue);
		if(grade != null) {
			loanIssueDto.setGradeName(grade.getGradesName());
		}
//		List<Object[]> loanIssueObj= loanIssueService.getLoanIssue(loanId);
//		
//		LoanIssueDTO loanIssueDto = loanIssueAdaptor.databaseObjModelToUiDto(loanIssueObj.get(0));

// 		if (loanIssue.getLoanEmis() != null && loanIssue.getLoanEmis().size() > 0) {
//			loanIssueDto.setFlag(true);
//			return loanIssueDto;
//		} else
//			loanIssueDto.setFlag(false);
		return loanIssueDto;
	}

	/**
	 * to get List of LoanEMI from database based on transactionNo(Primary Key)
	 */
	@RequestMapping(path = "/myloanEmiDetails", method = RequestMethod.GET)
	public @ResponseBody List<LoanEMIDTO> findLoanEmiDetails(@RequestParam("transactionNo") Long transactionNo,
			HttpServletRequest req) {

		logger.info("findLoanEmiDetails is transactionNo :" + transactionNo);
		return loanIssueAdaptor.databaseModelToLoanEmiDtoList(loanIssueService.getLoanEmiDetailsList(transactionNo));
	}

	/**
	 * to get List of LoanIssue from database based on empolyeeId
	 */
	@RequestMapping(path = "/myLoanIssue", method = RequestMethod.GET)
	public @ResponseBody List<LoanIssueDTO> getMyLoanInfo(@RequestParam("empolyeeId") Long empolyeeId,
			HttpServletRequest req) {

		logger.info("--------------------------------------getMyLoanInfo is :----------------------------------"
				+ empolyeeId);
		// Long empId = Long.parseLong(empolyeeId);

		List<LoanIssue> loanIssueList = loanIssueService.getMyLoanInfo(empolyeeId);

		return loanIssueAdaptor.databaseModelToUiDtoList(loanIssueList);
	}

	/**
	 * to get loanEmi statement as a PDF file based on transactionNo
	 */
//	@RequestMapping(path = "/loanIssuePdf", method = RequestMethod.GET)
//	public StreamingResponseBody reportPayout(@RequestParam("transactionNo") String transactionNo,
//			HttpServletRequest req, HttpServletResponse response) throws Exception {
//		logger.info("reportPayout is :" + transactionNo);
//		Company company = null;
//		Long longTransactionNo = Long.parseLong(transactionNo);
// 		List<LoanEMIDTO> LoanEMIDTOList = loanIssueAdaptor
//				.databaseModelToLoanEmiDtoList(loanIssueService.getLoanEmiDetailsList(longTransactionNo));
//  		//LoanIssue loanIssue = loanIssueService.getLoanIssue(longTransactionNo);
// 		//LoanIssueDTO loanIssueDto = loanIssueAdaptor.databaseModelToUiDto(loanIssue);
//// 		company = companyService.getCompany(loanIssue.getCompany().getCompanyId());
//		response.setContentType("application/pdf");
//		response.setHeader("Content-Disposition", "attachment; filename=\"loanEmi.pdf\"");
//		ByteArrayInputStream bis = new LoanEmiPdf().loanPdfReport(loanIssueDto, company, LoanEMIDTOList);
//		return outputStream -> {
//			int nRead;
//			byte[] data = new byte[1024];
//			while ((nRead = bis.read(data, 0, data.length)) != -1) {
//				outputStream.write(data, 0, nRead);
//			}
//		};
//	}

	/**
	 * Method Performed email sending logic
	 */
	public void triggerEmail(ByteArrayInputStream bis, String email, ReportPayOutAdaptor company) {
		logger.info("triggerEmail");
		MimeMessage mimeMessage = mailSender.createMimeMessage();
		try {
			MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
			mimeMessageHelper.setSubject("Employee Loan Details");
			mimeMessageHelper.setTo(email);
			mimeMessageHelper.setFrom(HrmsGlobalConstantUtil.FROM_MAIL);
			mimeMessageHelper.setCc(HrmsGlobalConstantUtil.MAIL_CC);
			String quationName = "loan" + ".pdf";
			mimeMessageHelper.addAttachment(quationName, new ByteArrayResource(IOUtils.toByteArray(bis)));
			mailSender.send(mimeMessageHelper.getMimeMessage());

		} catch (MessagingException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @param loanIssueDto This is the first parameter for getting loanIssue Object
	 *                     from UI
	 * @param req          This is the second parameter to maintain user session
	 */
	@RequestMapping(path = "/loanSettled", method = RequestMethod.POST)
	public void loanSettled(@RequestBody LoanIssueDTO loanIssueDto, HttpServletRequest req) {
		logger.info("loanSettled method loanIssueDto :" + loanIssueDto.toString());
		LoanIssue loanIssue = loanIssueAdaptor.uiDtoToDatabaseModelSettlement(loanIssueDto);

		loanIssueService.save(loanIssue);
	}

	/**
	 * to get LoanIssue from database based on loanIssueId(Primary Key)
	 * 
	 * @throws ErrorHandling
	 */
//	@RequestMapping(path = "/loanSettled", method = RequestMethod.GET)
//	public @ResponseBody LoanIssueDTO findloanSettled(@RequestParam("loanIssueId") String loanIssueId,
//			HttpServletRequest req) {
//
//		logger.info("findloanSettled controller loanIssueId is :" + loanIssueId);
// 		Long loanId = Long.parseLong(loanIssueId);
//		//LoanIssue loanIssue = loanIssueService.getLoanIssue(loanId);
//		//LoanIssueDTO loanIssueDto = loanIssueAdaptor.databaseModelToUiDtoSettement(loanIssue);
//	 
//			if (professionalTaxs != null && professionalTaxs.size() > 0)
//	return professionalTaxAdaptor.databaseModelToUiDtoList(professionalTaxs);
//else
//	throw new ErrorHandling("ProfessionalTax data not present");
//}

// 		return loanIssueDto;
//	}

	@RequestMapping(path = "/loanIssueEmployee", method = RequestMethod.GET)
	public @ResponseBody List<LoanIssueDTO> findAllLoanIssueEmployee(@RequestParam("companyId") String companyId,
			@RequestParam("employeeId") String employeeId, HttpServletRequest req)
			throws PayRollProcessException, ErrorHandling {
		logger.info("findAllLoanIssue controller companyId is :" + companyId);
		Long companyID = Long.parseLong(companyId);
		Long employeeID = Long.parseLong(employeeId);
		
		List<LoanIssue> loanIssueList = loanIssueService.findAllLoanIssueEmployee(companyID, employeeID);
		if (loanIssueList != null && loanIssueList.size() > 0)
			return loanIssueAdaptor.databaseModelToUiDtoList(loanIssueList);
		else
			throw new ErrorHandling("Loan Issue data not present");
	}


	@RequestMapping(value = "/getLoanIssueSearch/{companyId}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<LoanIssueDTO> getLoanIssueSearch(@PathVariable("companyId") Long companyId,
			@RequestBody SearchDTO searcDto) throws PayRollProcessException {
		logger.info("getLoanIssueSearch is calling ");
		List<Object[]> positionAllocationList = loanIssueService.getLoanIssueSearch(companyId, searcDto);
		
		return loanIssueAdaptor.databaseModelToUiDtoListLoanIssueSearch(positionAllocationList);

	}
	
	@RequestMapping(value = "/getLoanIssueSearchCount/{companyId}/{pageSize}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody EntityCountDTO getLoanIssueSearchCount(@PathVariable("companyId") Long companyId,
			@PathVariable("pageSize") String pageSize, @RequestBody SearchDTO searcDto) throws PayRollProcessException {
		logger.info("LoanIssueController.getLoanIssueSearchCount() pageSize  "+pageSize);
		int count;
		List<PageIndex> pageIndexList = new ArrayList<PageIndex>();

		int parPageRecord = Integer.parseInt(pageSize);
		EntityCountDTO entityCountDto = new EntityCountDTO();

		List<Object[]> getLoanIssueSearchList = loanIssueService.getLoanIssueSearchCount(companyId, searcDto);

		count = getLoanIssueSearchList.size();

		logger.info("LoanIssueController  count :" + count);

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

}
