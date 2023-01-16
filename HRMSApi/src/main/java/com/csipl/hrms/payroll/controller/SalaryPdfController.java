package com.csipl.hrms.payroll.controller;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import com.csipl.common.model.EmailConfiguration;
import com.csipl.common.model.EmailNotificationMaster;
import com.csipl.common.model.Mail;
import com.csipl.common.util.EmailUtils;
import com.csipl.hrms.common.exception.PayRollProcessException;
import com.csipl.hrms.common.util.ErrorHandling;
import com.csipl.hrms.common.util.HrmsGlobalConstantUtil;
import com.csipl.hrms.dto.organisation.DepartmentDTO;
import com.csipl.hrms.dto.payroll.InvestmentFinancialYearDTO;
import com.csipl.hrms.dto.payrollprocess.PayOutDTO;
import com.csipl.hrms.dto.payrollprocess.ReportPayOutSalaryDTO;
import com.csipl.hrms.model.common.City;
import com.csipl.hrms.model.common.Company;
import com.csipl.hrms.model.employee.Employee;
import com.csipl.hrms.model.organisation.Department;
import com.csipl.hrms.model.payrollprocess.ReportPayOut;
import com.csipl.hrms.service.adaptor.DepartmentAdaptor;
import com.csipl.hrms.service.adaptor.ReportPayOutAdaptor;
import com.csipl.hrms.service.common.CityService;
import com.csipl.hrms.service.common.EmailNotificationService;
import com.csipl.hrms.service.common.MailService;
import com.csipl.hrms.service.common.repository.EmailConfigurationRepository;
import com.csipl.hrms.service.employee.EmployeePersonalInformationService;
import com.csipl.hrms.service.organization.CompanyService;
import com.csipl.hrms.service.payroll.PayOutService;
import com.csipl.hrms.service.payroll.PayRollLockService;
import com.csipl.hrms.service.payroll.PayrollDateCalculationService;
import com.csipl.hrms.service.payroll.ReportPayOutService;
import com.csipl.hrms.service.util.SalaryPdfReport;

@RequestMapping("/salaryReport")
@RestController
public class SalaryPdfController {
	/**
	 * Logger declaration for knowing the flow of execution for debugging
	 */
	private static final Logger logger = LoggerFactory.getLogger(SalaryPdfController.class);
	@Autowired
	ReportPayOutService reportPayOutService;

	@Autowired
	CompanyService companyService;

	@Autowired
	CityService cityService;

	@Autowired
	EmployeePersonalInformationService employeePersonalInformationService;

	@Autowired
	JavaMailSender mailSender;

	@Autowired
	PayRollLockService payRollLockService;

	@Autowired
	PayOutService payOutService;
	@Autowired
	PayrollDateCalculationService payrollDateCalculation;

	@Autowired
	MailService mailService;

	@Autowired
	EmailConfigurationRepository emailConfugrationRepository;
	@Autowired
	EmailNotificationService emailNotificationService;
	public static final String ENCODING = "UTF-8";
	ReportPayOutAdaptor reportPayOutAdaptor = new ReportPayOutAdaptor();
	DepartmentAdaptor departmentAdaptor = new DepartmentAdaptor();

	/**
	 * Method performed save operation with attendance attachment file
	 * 
	 * @param reportPayOutSalaryDtoList
	 *            This is the first parameter for getting List if reportPayOutSalary
	 *            Objects from UI
	 * @param req
	 *            This is the second parameter to maintain user session
	 */
	@RequestMapping(value = "/pdf", method = RequestMethod.POST)
	public void salaryPdfReport(@RequestBody List<ReportPayOutSalaryDTO> reportPayOutSalaryDtoList,
			@RequestParam("companyId") Long companyId, HttpServletRequest req) throws Exception {
		ByteArrayInputStream bis = null;
		EmailUtils emailUtils = new EmailUtils();
		List<PayOutDTO> payOutDTOList = new ArrayList<PayOutDTO>();

		System.out.println("salaryPdfReport controller");
		Mail mail = mailService.getEmailDetail("salarySlip", companyId);
		logger.info(mail.toString());

		logger.info(mail.getFunctionName());
		logger.info((mail.getFromMail()));

		if (reportPayOutSalaryDtoList != null && reportPayOutSalaryDtoList.size() > 0) {

			for (ReportPayOutSalaryDTO reportPayOutSalaryDto : reportPayOutSalaryDtoList) {

				ReportPayOut reportPayOut = reportPayOutService.findEmployeePayOutPdf(
						reportPayOutSalaryDto.getEmployeeCode(), reportPayOutSalaryDto.getProcessMonth(), companyId);
				Company company = companyService.getCompany(reportPayOut.getCompanyId());
				Employee employee = employeePersonalInformationService
						.findEmployeesById(reportPayOut.getEmployee().getEmployeeId());
				if (reportPayOut != null) {
					payOutDTOList = payOutService.getPayOutsBasedOnProcessMonthAndEmployeeId(
							reportPayOut.getEmployee().getEmployeeId(), reportPayOut.getId().getProcessMonth());

				}
				City city = cityService.getCity(reportPayOut.getCityId());
				if (company != null && city != null && employee != null) {
					logger.info("generating pdf for enployee" + employee.getEmployeeCode() + "name is :"
							+ employee.getFirstName());
					bis = new SalaryPdfReport().salaryPdfReport(reportPayOut, company, city, employee, payOutDTOList);

					logger.info("sending selary  slip pdf " + bis);
					triggerEmail(bis, reportPayOutSalaryDto.getEmail(), mail);
					// triggerEmail1(bis, reportPayOutSalaryDto.getEmail(),null);
				} else {
					logger.info("Some data are not avalable to generate pdf  " + company + "city" + city + "employee"
							+ employee);
					throw new ErrorHandling("Some data are not avalable to generate pdf ");
				}
			}
		} else {
			logger.info("No Employee available to generate salary slip  pdf  report");
			throw new ErrorHandling("No Employee available to generate salary slip  pdf  report");
		}
	}

	public void triggerEmail(ByteArrayInputStream bis, String email, Mail mail) {
		EmailConfiguration confugration = null;

		confugration = emailConfugrationRepository.findEMail();
		  EmailNotificationMaster listEmail = emailNotificationService.findEMailListByStatus("HELP");

		JavaMailSenderImpl mailSender = null;

		mailSender = new JavaMailSenderImpl();
		Properties props = mailSender.getJavaMailProperties();
		if (confugration.getHost().equals("smtp.gmail.com")) {
			logger.info("===========salary slip==.getJavaMailSender()===============" + confugration.toString());
			mailSender.setHost(confugration.getHost());
			mailSender.setPort(confugration.getPort());
			mailSender.setUsername(listEmail.getUserName());
			mailSender.setPassword(listEmail.getPassword());
			props.put("mail.transport.protocol", confugration.getProtocol());
			props.put("mail.smtp.auth", confugration.getAuth());
			props.put("mail.smtp.ssl.trust", confugration.getSslName());
			props.put("mail.smtp.starttls.enable", confugration.getStarttlsName());

			props.put("mail.mime.charset", ENCODING);

		} else {
			logger.info("===========salary slip==.getJavaMailSender()================" + confugration.toString());
			mailSender.setHost(confugration.getHost());
			mailSender.setPort(confugration.getPort());
			mailSender.setUsername(listEmail.getUserName());
			mailSender.setPassword(listEmail.getPassword());
			props.put("mail.transport.protocol", confugration.getProtocol());
			props.put("mail.smtp.auth", confugration.getAuth());
			props.put("mail.smtp.ssl.trust", confugration.getSslName());
			props.put("mail.smtp.starttls.enable", confugration.getStarttlsName());
			props.put("mail.mime.charset", ENCODING);
			props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
			props.put("mail.smtp.socketFactory.fallback", "true");
		}
		
			System.out.println("To email is :" + email);
			System.out.println("mail.getCc() " + mail.getCc() + "mail.getFromMail() : " + mail.getFromMail());
			try {

//				MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
//				mimeMessageHelper.setSubject(mail.getSubject());
//				mimeMessageHelper.setTo(email);
//
//				mimeMessageHelper.setFrom(mail.getFromMail());
//				mimeMessageHelper.setCc(mail.getCc());mimeMessageHelper.setBcc(mail.getCc());

				// mimeMessageHelper.addAttachment(HrmsGlobalConstantUtil.QUATION_NAME,new
				// ByteArrayResource(IOUtils.toByteArray(bis)));
				
				logger.info("Mail is sending...");

				MimeMessage mimeMessage = mailSender.createMimeMessage();
				MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
				mimeMessageHelper.setSubject(mail.getSubject());
				mimeMessageHelper.setFrom(listEmail.getUserName());

				if (listEmail.getToMail() != null && !listEmail.getToMail().equals("")) {
					String toList = email + "," + listEmail.getToMail();
					String[] to = toList.split(",");
					mimeMessageHelper.setTo(to);
				} else {
					String to = email;
					mimeMessageHelper.setTo(to);
				}
				if (listEmail.getCc() != null && !listEmail.getCc().equals("")) {
					String ccList = listEmail.getCc();
					String[] cc1 = ccList.split(",");
					mimeMessageHelper.setCc(cc1);
				}

//				if (mail.getBcc() != null && !mail.getBcc().equals("")) {
//					String bccList1 = mail.getBcc();
//					String[] bcc1 = bccList1.split(",");
//					mimeMessageHelper.setBcc(bcc1);
//				}

				// mimeMessageHelper.setTo(employee.getOfficialEmail());
				// mimeMessageHelper.setCc(listEmail.getCc());
				// mimeMessageHelper.setBcc(listEmail.getBcc());

				mimeMessageHelper.setText("Hi " + email + "," + "\r\n" + "\r\n"
						+ "We would like to share Pdf of the following Salary Slip , which is attatched with mail : ");

				mimeMessageHelper.addAttachment(HrmsGlobalConstantUtil.QUATION_NAME,new
				 ByteArrayResource(IOUtils.toByteArray(bis)));
				
				try {
					mailSender.send(mimeMessageHelper.getMimeMessage());
					logger.info(" Salary-Slip mail send Successfully");
				} catch (Exception e) {
					e.printStackTrace();
					logger.info(" Salary-Slip mail send failed");
				}
			}   catch (Exception e) {
				e.printStackTrace();
			}
		}
	 

	/**
	 * Method Performed email sending logic
	 */
	public void triggerEmail1(ByteArrayInputStream bis, String email, ReportPayOutAdaptor company) {
		MimeMessage mimeMessage = mailSender.createMimeMessage();
		logger.info("To email1 is :" + email);
		try {

			MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
			mimeMessageHelper.setSubject(HrmsGlobalConstantUtil.MAIL_SUBJECT);
			mimeMessageHelper.setTo(email);
			mimeMessageHelper.setFrom(HrmsGlobalConstantUtil.FROM_MAIL);
			mimeMessageHelper.setCc(HrmsGlobalConstantUtil.MAIL_CC);
			mimeMessageHelper.addAttachment(HrmsGlobalConstantUtil.QUATION_NAME,
					new ByteArrayResource(IOUtils.toByteArray(bis)));
			mailSender.send(mimeMessageHelper.getMimeMessage());
			logger.info("mail send succesfully :" + email);
		} catch (MessagingException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Method Performed employee salary slip generation logic based on employeeCode
	 * and processMonth
	 */
	@RequestMapping(path = "/payoutPdf/{empCode}/{processMonth}/{companyId}", method = RequestMethod.GET)
	public StreamingResponseBody reportPayout(@PathVariable("empCode") String empCode,
			@PathVariable("processMonth") String processMonth, @PathVariable("companyId") Long companyId,
			HttpServletRequest req, HttpServletResponse response) throws Exception {
		Company company = null;
		ReportPayOut reportPayOut = null;
		Employee employee = null;
		City city = null;
		List<PayOutDTO> payOutDTOList = new ArrayList<PayOutDTO>();
		response.setContentType("application/pdf");
		response.setHeader("Content-Disposition", "attachment; filename=\"employeeSalarySlip.pdf\"");
		response.setHeader(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, HttpHeaders.CONTENT_DISPOSITION);
		
		if (empCode != null && !empCode.equals("") && processMonth != null && !processMonth.equals(""))
			reportPayOut = reportPayOutService.findEmployeePayOutPdf(empCode, processMonth, companyId);
		if (reportPayOut != null) {
			payOutDTOList = payOutService.getPayOutsBasedOnProcessMonthAndEmployeeId(
					reportPayOut.getEmployee().getEmployeeId(), reportPayOut.getId().getProcessMonth());
			company = companyService.getCompany(reportPayOut.getCompanyId());
			employee = employeePersonalInformationService.findEmployeesById(reportPayOut.getEmployee().getEmployeeId());
			city = cityService.getCity(reportPayOut.getCityId());
		}
		// logger.info("Generating Pdf for Employee code
		// :"+employee.getEmployeeCode()+"name :"+employee.getFirstName());
		ByteArrayInputStream bis = new SalaryPdfReport().salaryPdfReport(reportPayOut, company, city, employee,
				payOutDTOList);
		return outputStream -> {
			int nRead;
			byte[] data = new byte[1024];
			while ((nRead = bis.read(data, 0, data.length)) != -1) {
				outputStream.write(data, 0, nRead);
			}
		};
	}

	/**
	 * to get last 6 process months for employee salary slip
	 */
	@RequestMapping(path = "/employeeSalarySlipMonth/{companyId}/{employeeId}", method = RequestMethod.GET)
	public @ResponseBody List<InvestmentFinancialYearDTO> getEmployeeSalarySlipMonth(
			@PathVariable("companyId") Long companyId, @PathVariable("employeeId") Long employeeId,
			HttpServletRequest req) {
		System.out.println(
				"SalaryPdfController.getEmployeeSalarySlipMonth() Start" + employeeId + " companyID" + companyId);
		List<Object[]> investmentFinancialYearDtoList = payrollDateCalculation
				.employeeSalarySlipProcessMonthLastSix(employeeId, companyId);
		System.out.println("SalaryPdfController.getEmployeeSalarySlipMonth() End" + investmentFinancialYearDtoList);
		return reportPayOutAdaptor.investmentDatabaseModelToUiDtoLists(investmentFinancialYearDtoList);
	}

	/**
	 * to get List of departments based on processMonth
	 * 
	 * @throws PayRollProcessException
	 */
	@RequestMapping(path = "/departmentForSalary", method = RequestMethod.GET)
	public @ResponseBody List<DepartmentDTO> getAllDepartmentsForSalaryReconcilition(
			@RequestParam("processMonth") String processMonth, @RequestParam("companyId") String companyId,
			HttpServletRequest req) throws ErrorHandling, PayRollProcessException {
		Long companyID = Long.parseLong(companyId);
		List<Department> departmentList = payRollLockService.findDepartmentForSalaryGenerate(companyID, processMonth);
		if (departmentList != null)
			return departmentAdaptor.databaseModelToUiDtoList(departmentList);
		else
			throw new ErrorHandling("Departments Are Not Available In Company");
	}

	@RequestMapping(path = "/reportPayoutPdfApp", method = RequestMethod.GET)
	public ResponseEntity<ByteArrayResource> reportPayoutApp(@RequestParam("empCode") String empCode,
			@RequestParam("processMonth") String processMonth, @RequestParam("companyId") String companyId,
			HttpServletResponse response) throws Exception {
		Company company = null;
		ReportPayOut reportPayOut = null;
		Employee employee = null;
		City city = null;
		List<PayOutDTO> payOutDTOList = new ArrayList<PayOutDTO>();

		Long companyID = Long.parseLong(companyId);
		response.setContentType("application/pdf");
		response.setHeader("Content-Disposition", "attachment; filename=\"employeeSalarySlip.pdf\"");
		response.setHeader(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, HttpHeaders.CONTENT_DISPOSITION);
		
		if (empCode != null && !empCode.equals("") && processMonth != null && !processMonth.equals(""))
			reportPayOut = reportPayOutService.findEmployeePayOutPdf(empCode, processMonth, companyID);
		if (reportPayOut != null) {
			company = companyService.getCompany(reportPayOut.getCompanyId());
			employee = employeePersonalInformationService.findEmployeesById(reportPayOut.getEmployee().getEmployeeId());
			payOutDTOList = payOutService.getPayOutsBasedOnProcessMonthAndEmployeeId(
					reportPayOut.getEmployee().getEmployeeId(), reportPayOut.getId().getProcessMonth());
			city = cityService.getCity(reportPayOut.getCityId());
		}
		logger.info(
				"Generating Pdf for Employee code :" + employee.getEmployeeCode() + "name :" + employee.getFirstName());
		ByteArrayInputStream bis = new SalaryPdfReport().salaryPdfReport(reportPayOut, company, city, employee,
				payOutDTOList);
		byte[] array = new byte[bis.available()];
		bis.read(array);

		return ResponseEntity.ok().contentLength(array.length).body(new ByteArrayResource(array));
	}

}
