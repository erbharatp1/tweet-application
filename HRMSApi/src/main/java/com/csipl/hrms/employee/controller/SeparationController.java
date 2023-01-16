package com.csipl.hrms.employee.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;

import org.apache.velocity.app.VelocityEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.ui.velocity.VelocityEngineUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.csipl.common.model.EmailConfiguration;
import com.csipl.common.model.EmailNotificationMaster;
import com.csipl.hrms.common.constant.StatusMessage;
import com.csipl.hrms.common.exception.PayRollProcessException;
import com.csipl.hrms.common.util.ErrorHandling;
import com.csipl.hrms.common.util.HrmsGlobalConstantUtil;
import com.csipl.hrms.dto.employee.EmployeeCountDTO;
import com.csipl.hrms.dto.employee.EmployeeDTO;
import com.csipl.hrms.dto.employee.PageIndex;
import com.csipl.hrms.dto.employee.SeparationDTO;
import com.csipl.hrms.dto.recruitment.PositionDTO;
import com.csipl.hrms.dto.search.EmployeeSearchDTO;
import com.csipl.hrms.model.common.Company;
import com.csipl.hrms.model.employee.Employee;
import com.csipl.hrms.model.employee.Separation;
import com.csipl.hrms.service.adaptor.EmployeePersonalInformationAdaptor;
import com.csipl.hrms.service.adaptor.SeparationAdaptor;
import com.csipl.hrms.service.common.EmailNotificationService;
import com.csipl.hrms.service.common.PushNotificationsServiceImpl;
import com.csipl.hrms.service.common.PushNotificationsServices;
import com.csipl.hrms.service.common.repository.EmailConfigurationRepository;
import com.csipl.hrms.service.common.repository.FcmNotificationRepository;
import com.csipl.hrms.service.employee.EmployeePersonalInformationService;
import com.csipl.hrms.service.employee.SeparationPaginationService;
import com.csipl.hrms.service.employee.SeparationService;
import com.csipl.hrms.service.organization.CompanyServiceImpl;

@RestController
@RequestMapping("/separation")
public class SeparationController {
	/**
	 * Logger declaration for knowing the flow of execution for debugging
	 */
	private static final Logger logger = LoggerFactory.getLogger(SeparationController.class);
	public static final String ENCODING = "UTF-8";
	@Autowired
	SeparationService separationService;

	@Autowired
	JavaMailSender mailSender;

	@Autowired
	EmployeePersonalInformationService employeePersonalInformationService;

	@Autowired
	private SeparationPaginationService separationPaginationService;

	SeparationAdaptor separationAdaptor = new SeparationAdaptor();

	@Autowired
	PushNotificationsServices pushNotificationsServices;

	@Autowired
	PushNotificationsServiceImpl fcm;

	@Autowired
	FcmNotificationRepository fcmNotificationRepository;

	@Autowired
	private CompanyServiceImpl companyServiceImpl;

	@Autowired
	private Environment environment;

	@Autowired
	EmailConfigurationRepository emailConfugrationRepository;

	@Autowired
	EmailNotificationService emailNotificationService;
	@Autowired
	private VelocityEngine velocityEngine;

	/**
	 * @param separationDto This is the first parameter for getting Separation
	 *                      Object from UI
	 * @param req           This is the second parameter to maintain user session
	 * @throws PayRollProcessException
	 */

	@RequestMapping(value = "/resignation", method = RequestMethod.POST)
	public void saveSeparation(@RequestBody SeparationDTO separationDto, HttpServletRequest req)
			throws ErrorHandling, PayRollProcessException {
		logger.info("saveSeparation is calling : " + " SeparationDTO  " + separationDto.toString());
		Long separationCount = 0l;

		Separation separation = separationAdaptor.uiDtoToDatabaseModel(separationDto);

		if (separation.getStatus().equals(StatusMessage.PENDING_CODE)
				|| (separation.getStatus().equals(StatusMessage.APPROVED_CODE) && separation.getSeparationId() == null))
			separationCount = separationService.checkSeparationForRequest(separation.getEmployee1().getEmployeeId());

		/**
		 * email config start
		 */
		String toReporting = "", ccReportingTo = "";
		Long toReportingId = null;

		logger.info("separation  Count >> " + separationCount);
		if (separationCount == 0l || separationDto.getApprovalId() == null) {

			logger.info("saveSeparation is start  :" + separation.toString());

			Separation separation1 = separationService.save(separation);
			EmailConfiguration confugration = null;
			EmailNotificationMaster listEmail = null;
			confugration = emailConfugrationRepository.findEMail();
			if (separation1.getEmployee2() == null) {
				listEmail = emailNotificationService.findEMailListByStatus(StatusMessage.RESIGNATION_REQUEST_CODE);
			} else {
				listEmail = emailNotificationService.findEMailListByStatus(StatusMessage.SEPARATION_CODE);
			}
			JavaMailSenderImpl mailSender = null;
			mailSender = new JavaMailSenderImpl();
			Properties props = mailSender.getJavaMailProperties();
			if (confugration.getHost().equals("smtp.gmail.com")) {
				logger.info("===========separation==.getJavaMailSender()==" + listEmail.toString());
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
				logger.info("==== =separation==.getJavaMailSender() ===" + listEmail.toString());
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

			} // close email config

			if (separation1.getEmployee2() == null) {
				List<Object[]> reportingTo = employeePersonalInformationService
						.getEmpReportingToEmail(separation1.getEmployee1().getEmployeeId());

				Employee employee = employeePersonalInformationService
						.getEmployeeInfo(separation.getEmployee1().getEmployeeId());
				String reason = null;
				if (separation.getResoan().equals(StatusMessage.SEPARATION_RESOAN_CAREER_GROWTH_CODE)) {
					reason = StatusMessage.SEPARATION_RESOAN_CAREER_GROWTH_VALUE;
				} else if (separation.getResoan().equals(StatusMessage.SEPARATION_RESOAN_ISSUE_WITH_COM_CODE)) {
					reason = StatusMessage.SEPARATION_RESOAN_ISSUE_WITH_COM_VALUE;
				} else if (separation.getResoan().equals(StatusMessage.SEPARATION_RESOAN_MEDICAL_ISSUE_CODE)) {
					reason = StatusMessage.SEPARATION_RESOAN_MEDICAL_ISSUE_VALUE;
				} else if (separation.getResoan().equals(StatusMessage.SEPARATION_RESOAN_OTHER_CODE)) {
					reason = StatusMessage.SEPARATION_RESOAN_OTHER_VALUE;
				} else {
					reason = StatusMessage.SEPARATION_RESOAN_PERSONAL_ISSUE_VALUE;
				}

				if (reportingTo.size() > 0) {
					for (Object obj[] : reportingTo) {

						if (obj[2] != null)
							toReportingId = Long.valueOf(obj[0].toString());
						toReporting = obj[2].toString();

					}
				}
				List<Object[]> ccReportingToManager = employeePersonalInformationService
						.getEmpReportingToEmail(toReportingId);
				if (ccReportingToManager != null && ccReportingToManager.size() > 0) {
					for (Object obj[] : ccReportingToManager) {
						if (obj[2] != null)
							ccReportingTo = obj[2].toString();

					}
				}

				if (separation.getStatus().equals(StatusMessage.CANCEL_CODE)) {
					ArrayList<String> reportinsgTo = pushNotificationsServices
							.getReportingEmployeeCode(employee.getEmployeeId());
					try {
						fcm.fcmRequest(reportinsgTo, "Separation",
								employee.getFirstName() + " " + employee.getLastName() + " cancelled Separation ",
								"teamseparation", separation1.getSeparationId(), StatusMessage.DESCRIPTION);
					} catch (Exception e) {
						e.printStackTrace();
					}
				} else {

					ArrayList<String> reportinsgTo = pushNotificationsServices
							.getReportingEmployeeCode(employee.getEmployeeId());
					try {
						fcm.fcmRequest(reportinsgTo, "Separation",
								employee.getFirstName() + " " + employee.getLastName() + " applied Separation ",
								"teamseparation", separation1.getSeparationId(), StatusMessage.RAF);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				SimpleDateFormat sm = new SimpleDateFormat("dd-MMM-yyyy");
				String exitDate = sm.format(separation.getEndDate());
				String path = environment.getProperty("application.companyLogoPath");
				Company company = companyServiceImpl.getCompany(separation.getCompany().getCompanyId());
				String companyLogoPath = path + company.getCompanyLogoPath();
				MimeMessage mimeMessage = mailSender.createMimeMessage();
				MimeMessageHelper mimeMessageHelper;
				try {
					mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
					Map<String, Object> model = new HashMap<String, Object>();
					model.put("firstName", employee.getFirstName());
					model.put("lastName", employee.getLastName());
					model.put("exitDate", exitDate);
					model.put("reason", reason);
					model.put("employeeCode", employee.getEmployeeCode());
					model.put("companyLogoPath", companyLogoPath);
					String text = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine,
							"templates/resignation_request.vm", "UTF-8", model);
					if (listEmail.getFromMail() != null && !listEmail.getFromMail().trim().equals("")) {
						mimeMessageHelper.setFrom(listEmail.getUserName());
					} else {
						mimeMessageHelper.setFrom(listEmail.getUserName());
					}
					if (listEmail.getSubject() != null && !listEmail.getSubject().trim().equals("")) {
						mimeMessageHelper.setSubject(listEmail.getSubject());
					} else {
						mimeMessageHelper.setSubject(listEmail.getSubject());
					}

					if (listEmail.getBcc() != null && !listEmail.getBcc().equals("")) {
						String bccList = listEmail.getBcc();
						String[] bcc = bccList.split(",");
						mimeMessageHelper.setBcc(bcc);
					}

					/**
					 * getIsApplicableOnReportingTo
					 */
					if (listEmail.getToMail() != null && !listEmail.getToMail().equals("")) {
						String toList = listEmail.getToMail();

						if (listEmail.getIsApplicableOnReportingTo().equals("Y")) {
							// toList = toReporting + "," + toList;
							if (toReporting != null && !toReporting.equals("")) {
								toList = toReporting + "," + toList;
							}
						}
						String[] to1 = toList.split(",");
						mimeMessageHelper.setTo(to1);

					} else if (listEmail.getIsApplicableOnReportingTo().equals("Y")) {
						String toList = toReporting;

						if (toList != null && !toList.equals("")) {
							toList = toReporting;
							mimeMessageHelper.setTo(toList);
						}
					} else {
						if (toReporting != null && toReporting.equalsIgnoreCase("null")
								&& toReporting.equalsIgnoreCase("")) {
							String toListNew = toReporting;
							mimeMessageHelper.setTo(toListNew);
						} else {
							String toListNew = StatusMessage.EMAIL;
							mimeMessageHelper.setTo(toListNew);
						}
					}

					/**
					 * getIsApplicableOnReportingToManager
					 */

//					if (listEmail.getCc() != null && !listEmail.getCc().equals("")) {
//						String ccList = listEmail.getCc();
//
//						if (listEmail.getIsApplicableOnReportingToManager().equals("Y")) {
//							ccList = ccReportingTo + "," + ccList;
//						}
//						String[] cc1 = ccList.split(",");
//						mimeMessageHelper.setCc(cc1);
//
//					} else if (listEmail.getIsApplicableOnReportingToManager().equals("Y")) {
//						String ccList = ccReportingTo;
//						mimeMessageHelper.setCc(ccList);
//					}

					if (listEmail.getCc() != null && !listEmail.getCc().equals("")) {
						String ccList = listEmail.getCc();

						if (listEmail.getIsApplicableOnReportingToManager().equals("Y")) {

							if (ccReportingTo != null && !ccReportingTo.equals("")) {
								ccList = ccReportingTo + "," + ccList;
							}

						}
						String[] cc1 = ccList.split(",");
						mimeMessageHelper.setCc(cc1);

					} else if (listEmail.getIsApplicableOnReportingToManager().equals("Y")) {
						String ccList = null;
						if (ccReportingTo != null && !ccReportingTo.equals("")) {
							ccList = ccReportingTo;
							mimeMessageHelper.setCc(ccList);
						}
					}

					mimeMessageHelper.setText(text, true);
					if (listEmail.getActiveStatus().equals(StatusMessage.ACTIVE_CODE)) {
						try {
							mailSender.send(mimeMessageHelper.getMimeMessage());
							logger.info("mail send Successfully");
						} catch (Exception e) {
							logger.info("mail send Failed");
							e.printStackTrace();
						}
					} else {
						logger.info("mail send Failed");
					}
				} catch (Exception e) {

					e.printStackTrace();
				}

			} else {
				String toApproveReprting = "";
				List<Object[]> reportingTo = employeePersonalInformationService
						.getEmpReportingToEmail(separation1.getEmployee1().getEmployeeId());
				Employee employee = employeePersonalInformationService
						.getEmployeeInfo(separation.getEmployee1().getEmployeeId());
				// List<String> to = new ArrayList<String>();
				if (reportingTo != null && reportingTo.size() > 0) {
					for (Object obj[] : reportingTo) {

						if (obj[2] != null) {
							toReportingId = Long.valueOf(obj[0].toString());
							toApproveReprting = obj[2].toString();
						}
					}
				}
				SimpleDateFormat sm = new SimpleDateFormat("dd-MMM-yyyy");
				String exitDate = sm.format(separation.getExitDate());
				String path = environment.getProperty("application.companyLogoPath");
				Company company = companyServiceImpl.getCompany(separation.getCompany().getCompanyId());
				String companyLogoPath = path + company.getCompanyLogoPath();

				String status;
				if (separation.getStatus().equals(StatusMessage.APPROVED_CODE)) {
					status = StatusMessage.APPROVED_VALUE;
					Long useId = fcmNotificationRepository.getUserId(employee.getEmployeeId());
					ArrayList<String> tokenList = fcmNotificationRepository.getTokenList(useId);
					try {
						fcm.fcmRequest(tokenList, "Separation ", "Separation  has been Approved", "myseparation",
								separation.getSeparationId(), StatusMessage.DESCRIPTION);
					} catch (Exception e) {
						e.printStackTrace();
					}

				} else {
					status = StatusMessage.CANCEL_VALUE;
					Long useId = fcmNotificationRepository.getUserId(employee.getEmployeeId());
					ArrayList<String> tokenList = fcmNotificationRepository.getTokenList(useId);
					try {
						fcm.fcmRequest(tokenList, "Separation ", "Separation  has been Rejected", "myseparation",
								separation.getSeparationId(), StatusMessage.DESCRIPTION);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}

				MimeMessage mimeMessage = mailSender.createMimeMessage();
				MimeMessageHelper mimeMessageHelper;
				try {
					mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);

					Map<String, Object> model = new HashMap<String, Object>();
					model.put("firstName", employee.getFirstName());
					model.put("lastName", employee.getLastName());
					model.put("exitDate", exitDate);
					model.put("status", status);
					model.put("companyLogoPath", companyLogoPath);

					String text = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine,
							"templates/resignation_approved.vm", "UTF-8", model);
					if (listEmail.getFromMail() != null && !listEmail.getFromMail().trim().equals("")) {
						mimeMessageHelper.setFrom(listEmail.getUserName());
					} else {
						mimeMessageHelper.setFrom(listEmail.getUserName());
					}
					if (listEmail.getSubject() != null && !listEmail.getSubject().trim().equals("")) {
						mimeMessageHelper.setSubject(listEmail.getSubject());
					} else {
						mimeMessageHelper.setSubject(listEmail.getSubject());
					}
					/**
					 * getIsApplicableOnReportingTo
					 */
					if (listEmail.getToMail() != null && !listEmail.getToMail().equals("")) {
						String toList = listEmail.getToMail();
						// employee.getOfficialEmail() + "," + listEmail.getToMail();
						if (employee.getOfficialEmail() == null || employee.getOfficialEmail().equals("")) {
							toList = listEmail.getToMail();
						} else {
							toList = employee.getOfficialEmail() + "," + listEmail.getToMail();
						}
						if (listEmail.getIsApplicableOnReportingTo().equals("Y")) {
							// toList = toList + "," + toApproveReprting;
							if (toApproveReprting != null && !toApproveReprting.equals("")) {
								toList = toApproveReprting + "," + toList;
							}
						}
						String[] to1 = toList.split(",");
						mimeMessageHelper.setTo(to1);

					} else if (listEmail.getIsApplicableOnReportingTo().equals("Y")) {
						String toList = "";
						// employee.getOfficialEmail() + "," + toApproveReprting;
						if (toApproveReprting != null && !toApproveReprting.equals("")) {
							toList = toApproveReprting;
						}
						if (employee.getOfficialEmail() != null && !employee.getOfficialEmail().equals("")) {
							if (!toList.equals("")) {
								toList = employee.getOfficialEmail() + "," + toList;
							}
							toList = employee.getOfficialEmail();
						} else {
							toList = StatusMessage.EMAIL;
						}

						// String toList = employee.getOfficialEmail() + "," + toApproveReprting;
						String[] to1 = toList.split(",");
						mimeMessageHelper.setTo(to1);
					} else {
						if (toApproveReprting != null || toApproveReprting.equalsIgnoreCase("Null")
								|| toApproveReprting.equalsIgnoreCase("")) {
							String toListNew = employee.getOfficialEmail();
							mimeMessageHelper.setTo(toListNew);
						} else {
							String toListNew = StatusMessage.EMAIL;
							mimeMessageHelper.setTo(toListNew);
						}
					}
					/**
					 * getIsApplicableOnReportingToManager
					 */
					if (listEmail.getCc() != null && !listEmail.getCc().equals("")) {
						String ccList = listEmail.getCc();

						if (listEmail.getIsApplicableOnReportingToManager().equals("Y")) {
//							if (ccReportingTo.equals(null)) {
//								ccList = "" + ccList;
//							} else {
//								ccList = ccReportingTo + "," + ccList;
//							}
							if (ccReportingTo != null && !ccReportingTo.equals("")) {
								ccList = ccReportingTo + "," + ccList;
							}
						}
						String[] cc1 = ccList.split(",");
						mimeMessageHelper.setCc(cc1);

					} else if (listEmail.getIsApplicableOnReportingToManager().equals("Y")) {

						if (!ccReportingTo.equals("")) {
							mimeMessageHelper.setCc(ccReportingTo);
						}

					}

					if (listEmail.getBcc() != null && !listEmail.getBcc().equals("")) {
						String bccList = listEmail.getBcc();
						String[] bcc = bccList.split(",");
						mimeMessageHelper.setBcc(bcc);
					}
					mimeMessageHelper.setText(text, true);
					if (listEmail.getActiveStatus().equals(StatusMessage.ACTIVE_CODE)) {
						try {
							mailSender.send(mimeMessageHelper.getMimeMessage());
							logger.info("mail send Successfully");
						} catch (Exception e) {
							logger.info("mail send Failed");
							e.printStackTrace();
						}
					} // close if block
					else
						logger.info("mail send Failed");
				} // close try block
				catch (Exception e) {
					e.printStackTrace();
				}
			}
		} else {
			logger.info("Separation action already in progress :" + separation.toString());
			throw new ErrorHandling("Separation action already in progress");
		}
	}

	/**
	 * to get List of Separation from database based on employeeId
	 * 
	 * @throws PayRollProcessException
	 */
	@RequestMapping(value = "/{employeeId}/{companyId}", method = RequestMethod.GET)
	public @ResponseBody List<SeparationDTO> getSeparationListByEmpId(@PathVariable("employeeId") String employeeId,
			@PathVariable("companyId") String companyId, HttpServletRequest req) throws PayRollProcessException {
		logger.info("getSeparationListByEmpId is calling : " + " employeeId  " + employeeId);
		Long empId = Long.parseLong(employeeId);
		Long comId = Long.parseLong(companyId);
		return separationAdaptor.databaseModelToUiDtoList(separationService.getSeparationList(empId, comId));// 333
	}

	/**
	 * to get List of Separation from database based on companyId
	 * 
	 * @throws PayRollProcessException
	 */
	@RequestMapping(value = "/separationList/{companyId}", method = RequestMethod.GET)
	public @ResponseBody List<SeparationDTO> getAllseparationList(@PathVariable("companyId") String companyId,
			HttpServletRequest req) throws PayRollProcessException {
		logger.info("getAllseparationList is calling : ");
		Long companyID = Long.parseLong(companyId);
		return separationAdaptor.databaseModelToUiDtoList(separationService.getAllseparationList(companyID));
	}

	/**
	 * to get Separation Object from database based on separtionId(Primary Key)
	 */
	@RequestMapping(value = "/separationData/{separtionId}", method = RequestMethod.GET)
	public @ResponseBody SeparationDTO separation(@PathVariable("separtionId") String separtionId) {
		logger.info("separation is calling : " + "separtionId :" + separtionId);
		Long longSepartionId = Long.parseLong(separtionId);
		return separationAdaptor.databaseModelToUiDto(separationService.getSeparation(longSepartionId));// 111
	}

	/**
	 * Method Performed email sending logic
	 */
	public void triggerEmail(Separation separation, String emailTo) {
		MimeMessage mimeMessage = mailSender.createMimeMessage();
		logger.info("emailTo is : " + emailTo);
		try {
			MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
			mimeMessageHelper.setSubject("Resignation Letter");

			mimeMessageHelper.setTo(emailTo);
			mimeMessageHelper.setFrom(HrmsGlobalConstantUtil.FROM_MAIL);
			mimeMessageHelper.setText("Hi Team, " + "\r\n" + separation.getDescription() + "\r\n"
					+ String.format(HrmsGlobalConstantUtil.STRING_FORMAT) + "\r\n"
					+ separation.getEmployee1().getFirstName() + " " + separation.getEmployee1().getLastName());
			mailSender.send(mimeMessageHelper.getMimeMessage());

		} catch (MessagingException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * to get List of Separation from database based on companyId and status=pending
	 * and current date
	 * 
	 * @throws PayRollProcessException
	 */
	@RequestMapping(value = "/separationPending/{companyId}", method = RequestMethod.GET)
	public @ResponseBody List<SeparationDTO> separationPending(@PathVariable("companyId") String companyId,
			HttpServletRequest req) throws ErrorHandling, PayRollProcessException {
		logger.info("separationPending is calling : " + companyId);
		Long companyID = Long.parseLong(companyId);
		List<Separation> separationList = separationService.getAllseparationPendingList(companyID);
		if (separationList != null)
			return separationAdaptor.databaseModelToUiDtoList(separationList);
		else
			throw new ErrorHandling("No one separation pending notification today");

	}

	@RequestMapping(value = "/separationAprooved/{companyId}", method = RequestMethod.GET)
	public @ResponseBody List<PositionDTO> findAllSeparationAproveList(@PathVariable("companyId") String companyId,
			HttpServletRequest req) throws ErrorHandling, PayRollProcessException {
		logger.info("separationPending is calling : " + companyId);
		Long companyID = Long.parseLong(companyId);
		List<Object[]> separationList = separationService.findAllSeparationAproveList(companyID);
		if (separationList != null)
			return separationAdaptor.modelListToDtoList(separationList);
		else
			throw new ErrorHandling("No one separation pending notification today");
	}
	
	
	@RequestMapping(value = "/isPositionClosed/{companyId}/{employeeId}", method = RequestMethod.GET)
	public void updateisPositionClosedFlag(@PathVariable("companyId") String companyId,@PathVariable("employeeId") Long employeeId,
			HttpServletRequest req) {
		logger.info("separationPending is calling : " + companyId);
		Long companyID = Long.parseLong(companyId);
		separationService.updateisPositionClosedFlag(companyID, employeeId);
	}
	
	/**
	 * 
	 * to get List of Separation from database based on companyId and status=pending
	 * and current date
	 * 
	 * @throws PayRollProcessException
	 */
	@RequestMapping(value = "/noticePeriodCount/{companyId}", method = RequestMethod.GET)
	public @ResponseBody SeparationDTO noticePeriodCount(@PathVariable("companyId") Long companyId,
			HttpServletRequest req) throws ErrorHandling, PayRollProcessException {
		logger.info("separationPending is calling companyId: " + companyId);
		SeparationDTO SeparationDTO = separationService.getNoticePeriodCount(companyId);
		return SeparationDTO;

	}

	/**
	 * to get List of Separation from database based on employeeId
	 * 
	 * @throws PayRollProcessException
	 */
	@RequestMapping(value = "/count/{companyId}", method = RequestMethod.GET)
	public @ResponseBody SeparationDTO getSeparationCount(@PathVariable("companyId") Long companyId,
			HttpServletRequest req) throws PayRollProcessException {

		return separationService.seperationCount(companyId);
	}

	/**
	 * to get List of Separation from database based on employeeId
	 * 
	 */
	@RequestMapping(value = "/{employeeId}", method = RequestMethod.GET)
	public @ResponseBody List<SeparationDTO> employeeCancelledResignReqList(@PathVariable("employeeId") Long employeeId)
			throws PayRollProcessException {
		logger.info("separationListOfEmployee is calling : " + " employeeId  " + employeeId);
		return separationAdaptor.databaseModelToUiDtoList(separationService.employeeCancelledResignReqList(employeeId));
	}

	/**
	 * to get List of Separation from database based on employeeId
	 * 
	 */
	@RequestMapping(value = "/pendingApproved/{employeeId}", method = RequestMethod.GET)
	public @ResponseBody SeparationDTO employeePendingResignReq(@PathVariable("employeeId") Long employeeId)
			throws PayRollProcessException {
		SeparationDTO SeparationDto = new SeparationDTO();
		logger.info("separationListOfEmployee is calling : " + " employeeId  " + employeeId);
		Separation separation = separationService.employeePendingResignReq(employeeId);

		if (separation != null) {
			return separationAdaptor.databaseModelToUiDto(separation);
		} else {
			return SeparationDto;

		}
	}

	/**
	 * @param separationDto This is the first parameter for getting Separation
	 *                      Object from UI
	 * @param req           This is the second parameter to maintain user session
	 * @throws PayRollProcessException
	 */
	@RequestMapping(value = "/upadateStatus", method = RequestMethod.POST)
	public void updateRequestStatus(@RequestBody SeparationDTO separationDto) {
		logger.info("updateRequestStatus is calling : " + " SeparationDTO  " + separationDto.toString());
		separationService.updateRequestStatus(separationDto.getSeparationId(), separationDto.getStatus(),
				separationDto.getDescription());

	}

	/**
	 * to get List of Separation from database based on companyId and status=pending
	 * and current date
	 * 
	 * @throws PayRollProcessException
	 */
	@RequestMapping(value = "/pending/{companyId}", method = RequestMethod.GET)
	public @ResponseBody List<SeparationDTO> findAllSeparationPendingReqList(@PathVariable("companyId") Long companyId)
			throws ErrorHandling, PayRollProcessException {
		logger.info("findAllSeparationPendingReqList is calling : " + companyId);
		List<Separation> separationPendingList = separationService.findAllSeparationPendingReqList(companyId);
		if (separationPendingList != null)
			return separationAdaptor.databaseModelToUiDtoList(separationPendingList);
		else
			throw new ErrorHandling("No one separation pending request");

	}

	/**
	 * to get List of Separation from database based on companyId and status=pending
	 * and current date
	 * 
	 * @throws PayRollProcessException
	 */
	@RequestMapping(value = "/excludedPending/{companyId}", method = RequestMethod.GET)
	public @ResponseBody List<SeparationDTO> findAllSeparationExcludedPendingReqList(
			@PathVariable("companyId") Long companyId) throws ErrorHandling, PayRollProcessException {
		logger.info("findAllSeparationPendingReqList is calling : " + companyId);
		List<Separation> separationExcludedPendingList = separationService
				.findAllSeparationExcludedPendingReqList(companyId);
		if (separationExcludedPendingList != null)
			return separationAdaptor.databaseModelToUiDtoList(separationExcludedPendingList);
		else
			throw new ErrorHandling("No one separation completed request");

	}

	@RequestMapping(value = "/{status}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<SeparationDTO> getSeparationPaginationList(@RequestBody EmployeeSearchDTO employeeSearcDto,
			@PathVariable("status") boolean status) {
		logger.info(" active employees is calling :" + status);
		List<Object[]> separationList = separationPaginationService
				.getSeparationPaginationList(employeeSearcDto.getCompanyId(), employeeSearcDto, status);
		List<SeparationDTO> separationDtoList = separationAdaptor.modelListToDtoList(separationList, employeeSearcDto);
		return separationDtoList;
	}

	@GetMapping(value = "/separationCount/{companyId}/{pageSize}/{status}")
	public @ResponseBody EmployeeCountDTO getSeparationCount(@PathVariable("companyId") Long companyId,
			@PathVariable("pageSize") String pageSize, @PathVariable("status") boolean status, HttpServletRequest req) {

		List<PageIndex> pageIndexList = new ArrayList<PageIndex>();

		int parPageRecord = Integer.parseInt(pageSize);
		EmployeeCountDTO employeeCountDto = new EmployeeCountDTO();
		separationPaginationService.getSeparationCount(companyId, status, employeeCountDto);
		int count = employeeCountDto.getCount();
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

	/**
	 * @param employeeId get the data from database based on employeeId
	 */
	@RequestMapping(path = "/employees", method = RequestMethod.GET)
	public @ResponseBody List<EmployeeDTO> allEmployeesListForSeparationLOV(@RequestParam("companyId") Long companyId,
			HttpServletRequest req) {
		EmployeePersonalInformationAdaptor employeePersonalInformationAdaptor = new EmployeePersonalInformationAdaptor();
		logger.info("this.employeeList is calling :" + " : companyId " + companyId);
		List<Object[]> employeeObjList = employeePersonalInformationService
				.findAllPersonalInformationDetailsForSeparationLOV(companyId);
		List<EmployeeDTO> employeeDtoList = employeePersonalInformationAdaptor
				.databaseObjModelToUiDtoList(employeeObjList);
		return employeeDtoList;
	}

	/**
	 * 
	 * seprationPandingTeamCount
	 */
	@GetMapping(value = "/seprationPandingTeamCount/{employeeId}/{companyId}")
	public @ResponseBody SeparationDTO seprationPandingTeamCount(@PathVariable("employeeId") Long employeeId,
			@PathVariable("companyId") Long companyId) throws ErrorHandling {
		return separationService.seprationPandingTeamCount(employeeId, companyId);
	}

	@GetMapping(value = "/seprationAllTimePandingTeamCount/{employeeId}/{companyId}")
	public @ResponseBody SeparationDTO seprationAllTimePandingTeamCount(@PathVariable("employeeId") Long employeeId,
			@PathVariable("companyId") Long companyId) throws ErrorHandling {
		return separationService.seprationAllTimePandingTeamCount(employeeId, companyId);
	}
	
	/*
	 * All employee separation
	 */
	@GetMapping(value = "/allEmployeeSeprationPanding/{companyId}")
	public @ResponseBody SeparationDTO allEmployeeSeprationPanding(@PathVariable("companyId") Long companyId)
			throws ErrorHandling {
		return separationService.allEmployeeSeprationPanding(companyId);
	}

	@GetMapping(value = "/allTimeAllEmployeeSeprationPanding/{companyId}")
	public @ResponseBody SeparationDTO allTimeAllEmployeeSeprationPanding(@PathVariable("companyId") Long companyId)
			throws ErrorHandling {
		return separationService.allTimeAllEmployeeSeprationPanding(companyId);
	}
	
	/**
	 * 
	 * seprationPandingMyCount
	 */
	@GetMapping(value = "/seprationPandingMyCount/{employeeId}/{companyId}")
	public @ResponseBody SeparationDTO seprationPandingMyCount(@PathVariable("employeeId") Long employeeId,
			@PathVariable("companyId") Long companyId) throws ErrorHandling {
		return separationService.seprationPandingMyCount(employeeId, companyId);
	}

}
