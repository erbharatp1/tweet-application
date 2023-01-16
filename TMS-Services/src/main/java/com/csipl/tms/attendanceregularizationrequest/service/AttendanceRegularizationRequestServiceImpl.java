package com.csipl.tms.attendanceregularizationrequest.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.apache.velocity.app.VelocityEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.velocity.VelocityEngineUtils;

import com.csipl.common.model.EmailConfiguration;
import com.csipl.common.model.EmailNotificationMaster;
import com.csipl.hrms.common.constant.StatusMessage;
import com.csipl.hrms.common.util.DateUtils;
import com.csipl.hrms.common.util.ErrorHandling;
import com.csipl.hrms.model.common.Company;
import com.csipl.hrms.model.employee.Employee;
import com.csipl.hrms.model.payrollprocess.ReportPayOut;
import com.csipl.hrms.service.common.EmailNotificationService;
import com.csipl.hrms.service.common.PushNotificationsServiceImpl;
import com.csipl.hrms.service.common.PushNotificationsServices;
import com.csipl.hrms.service.common.repository.EmailConfigurationRepository;
import com.csipl.hrms.service.common.repository.FcmNotificationRepository;
import com.csipl.hrms.service.employee.EmployeePersonalInformationService;
import com.csipl.hrms.service.organization.CompanyServiceImpl;
import com.csipl.hrms.service.payroll.repository.ReportPayOutRepository;
import com.csipl.tms.attendanceregularizationrequest.repository.ARRequestPagingAndFilterRepository;
import com.csipl.tms.attendanceregularizationrequest.repository.AttendanceRegularizationRequestRepository;
import com.csipl.tms.attendancescheme.repository.AttendanceSchemeRepository;
import com.csipl.tms.dto.attendanceregularizationrequest.AttendanceRegularizationRequestDTO;
import com.csipl.tms.dto.attendancescheme.AttendanceSchemeDTO;
import com.csipl.tms.dto.common.EntityCountDTO;
import com.csipl.tms.dto.common.SearchDTO;
import com.csipl.tms.model.attendanceregularizationrequest.AttendanceRegularizationRequest;

@Transactional
@Service("attendanceRegularizationRequestService")
public class AttendanceRegularizationRequestServiceImpl implements AttendanceRegularizationRequestService {
	public static final String ENCODING = "UTF-8";
	@PersistenceContext(unitName = "mySQL")
	@Autowired
	private EntityManager entityManager;

	@Autowired
	private VelocityEngine velocityEngine;

	@Autowired
	EmployeePersonalInformationService employeePersonalInformationService;

	@Autowired
	private AttendanceRegularizationRequestRepository attendanceRegularizationRequestRepository;

	private static final Logger logger = LoggerFactory.getLogger(AttendanceRegularizationRequestServiceImpl.class);

	@Autowired
	private ReportPayOutRepository reportPayOutRepository;

	@Autowired
	PushNotificationsServices pushNotificationsServices;

	@Autowired
	PushNotificationsServiceImpl fcm;

	@Autowired
	FcmNotificationRepository fcmNotificationRepository;
	@Autowired
	CompanyServiceImpl companyServiceImpl;

	@Autowired
	private Environment environment;
	@Autowired
	EmailConfigurationRepository emailConfugrationRepository;

	@Autowired
	EmailNotificationService emailNotificationService;

	@Autowired
	AttendanceSchemeRepository attendanceSchemeRepository;

	@Autowired
	ARRequestPagingAndFilterRepository aRRequestPagingAndFilterRepository;

	@Override
	public AttendanceRegularizationRequest save(AttendanceRegularizationRequest attendanceRegularizationRequest)
			throws ErrorHandling {

		logger.info("approval id===" + attendanceRegularizationRequest.getApprovalId());

		Date fromDates = attendanceRegularizationRequest.getFromDate();
		String processMonth = DateUtils.getMonthOfProcess(fromDates).toUpperCase() + "-"
				+ DateUtils.getYearOfProcess(fromDates);

		ReportPayOut reportPayOut = reportPayOutRepository
				.isPayrollProcessed(attendanceRegularizationRequest.getEmployeeId(), processMonth);

		if (reportPayOut == null) {
			// email config start
			EmailConfiguration confugration = null;
			EmailNotificationMaster listEmail = null;
			confugration = emailConfugrationRepository.findEMail();
			if (attendanceRegularizationRequest.getApprovalId() != null) {
				listEmail = emailNotificationService
						.findEMailListByStatus(StatusMessage.ATTENDANCE_REGULARIZATION_APR_OR_REJ_CODE);
			} else {
				listEmail = emailNotificationService
						.findEMailListByStatus(StatusMessage.ATTENDANCE_REGULARIZATION_CODE);
			}

			JavaMailSenderImpl mailSender = null;
			mailSender = new JavaMailSenderImpl();
			Properties props = mailSender.getJavaMailProperties();
			if (confugration.getHost().equals(StatusMessage.HOST_NAME_SMTP)) {
				logger.info("===========AR==.getJavaMailSender()===============" + listEmail.toString());
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
				logger.info("===========Ar==.getJavaMailSender()================" + listEmail.toString());
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
			} // end email config
			if (attendanceRegularizationRequest.getApprovalId() != null) {
				AttendanceRegularizationRequest arRequest = attendanceRegularizationRequestRepository
						.save(attendanceRegularizationRequest);
				// Employee employee = employeePersonalInformationService
				// .getEmployeeInfo(attendanceRegularizationRequest.getApprovalId());
				Employee employeeAR = employeePersonalInformationService
						.getEmployeeInfo(attendanceRegularizationRequest.getEmployeeId());

				String reason = null;
				if (attendanceRegularizationRequest.getArCategory().equals(StatusMessage.AR_MISS_PUNCH_CODE)) {
					reason = StatusMessage.AR_MISS_PUNCH_VALUE;
				} else if (attendanceRegularizationRequest.getArCategory()
						.equals(StatusMessage.AR_CORD_NOT_WORKING_CODE)) {
					reason = StatusMessage.AR_CORD_NOT_WORKING_VALUE;
				} else if (attendanceRegularizationRequest.getArCategory().equals(StatusMessage.AR_POWER_CUT_CODE)) {
					reason = StatusMessage.AR_POWER_CUT_VALUE;
				} else {
					reason = StatusMessage.AR_MECHINE_NOT_WORKING_VALUE;
				}
				SimpleDateFormat sm = new SimpleDateFormat("dd-MMM-yyyy");
				String fromDate = sm.format(attendanceRegularizationRequest.getFromDate());
				String toDate = sm.format(attendanceRegularizationRequest.getToDate());
				String path = environment.getProperty("application.companyLogoPath");
				Company company = companyServiceImpl.getCompany(attendanceRegularizationRequest.getCompanyId());
				String companyLogoPath = path + company.getCompanyLogoPath();
				// List<Object[]> reportingTo = employeePersonalInformationService
				// .getEmpReportingToEmail(attendanceRegularizationRequest.getEmployeeId());

				String status;
				if (arRequest.getStatus().equals(StatusMessage.APPROVED_CODE)) {
					status = StatusMessage.APPROVED_VALUE;

					Long useId = fcmNotificationRepository.getUserId(employeeAR.getEmployeeId());

					ArrayList<String> tokenList = fcmNotificationRepository.getTokenList(useId);
					try {
						fcm.fcmRequest(tokenList, "AR Apply", "AR has been Approved", "myAR", arRequest.getArID(),
								StatusMessage.DESCRIPTION);
					} catch (Exception e) {
						e.printStackTrace();
					}

				} else {
					status = StatusMessage.REJECTED_VALUE;
					Long useId = fcmNotificationRepository.getUserId(employeeAR.getEmployeeId());
					ArrayList<String> tokenList = fcmNotificationRepository.getTokenList(useId);
					try {
						fcm.fcmRequest(tokenList, "AR Apply", "AR has been Rejected", "myAR", arRequest.getArID(),
								StatusMessage.DESCRIPTION);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				logger.info("AttendanceRegularizationRequestServiceImpl.save()");

				String toReporting = "", ccReportingTo = "";
				Long toReportingId = null;

				List<Object[]> reportingToApp = employeePersonalInformationService
						.getEmpReportingToEmail(attendanceRegularizationRequest.getEmployeeId());

				// String empCode = null;
				if (reportingToApp != null) {
					for (Object obj[] : reportingToApp) {
						toReportingId = obj[0] != null ? Long.parseLong(obj[0].toString()) : null;
						toReporting = obj[2] != null ? (String) obj[2] : null;

					}
				}
				// ReportingToManager
				List<Object[]> reportingToManager = employeePersonalInformationService
						.getEmpReportingToEmail(toReportingId);
				if (reportingToManager.size() > 0) {
					for (Object obj[] : reportingToManager) {
						// if (obj[2].toString() != null && obj[2].toString().equals("")) {
						ccReportingTo = obj[2] != null ? (String) obj[2] : null;
						// }

					}
				}

				try {
					MimeMessage mimeMessage = mailSender.createMimeMessage();
					MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);

					Map<String, Object> model = new HashMap<String, Object>();
					model.put("firstName", employeeAR.getFirstName());
					model.put("lastName", employeeAR.getLastName());
					model.put("fromDate", fromDate);
					model.put("toDate", toDate);
					model.put("category", reason);
					model.put("status", status);
					model.put("companyLogoPath", companyLogoPath);

					String text = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine,
							"templates/AR_approved.vm", "UTF-8", model);
					if (listEmail.getFromMail() != null && !listEmail.getFromMail().trim().equals("")) {
						mimeMessageHelper.setFrom(listEmail.getFromMail());
					} else {
						mimeMessageHelper.setFrom(listEmail.getFromMail());
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

						String toList = "";
						if (employeeAR.getOfficialEmail() != null && employeeAR.getOfficialEmail().equals("")) {
							toList = listEmail.getToMail();
						} else {
							toList = employeeAR.getOfficialEmail() + "," + listEmail.getToMail();
						}
						if (listEmail.getIsApplicableOnReportingTo().equals("Y")) {
							// toList = toList + "," + toReporting;
							if (toReporting != null && !toReporting.equals("")) {
								toList = toReporting + "," + toList;
							}
						}
						String[] to1 = toList.split(",");
						mimeMessageHelper.setTo(to1);

					} else if (listEmail.getIsApplicableOnReportingTo().equals("Y")) {
						String toList = "";
						if (toReporting != null && !toReporting.equals("")) {
							toList = toReporting;
						}
						if (employeeAR.getOfficialEmail() != null && !employeeAR.getOfficialEmail().equals("")) {
							toList = toList + "," + employeeAR.getOfficialEmail();
						}

						String[] to1 = toList.split(",");
						mimeMessageHelper.setTo(to1);
					} else {
						if (toReporting != null && toReporting.equalsIgnoreCase("null")
								&& toReporting.equalsIgnoreCase("")) {
							String toListNew = employeeAR.getOfficialEmail();
							mimeMessageHelper.setTo(toListNew);
						} else {
							String toListNew = StatusMessage.EMAIL;
							mimeMessageHelper.setTo(toListNew);
						}
					}

					if (listEmail.getCc() != null && !listEmail.getCc().equals("")) {
						String ccList = listEmail.getCc();

						if (listEmail.getIsApplicableOnReportingToManager().equals("Y")) {

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
							logger.info("mail send failed");
							e.printStackTrace();
						}
					} else {
						logger.info("mail send failed");
					}

				} catch (MessagingException e) {
					logger.info("mail send failed");
					e.printStackTrace();
				}
			} else {

				int count = 0;
				if (attendanceRegularizationRequest.getStatus().equals(StatusMessage.PENDING_CODE)
						&& attendanceRegularizationRequest.getArID() != null) {

					count = attendanceRegularizationRequestRepository.checkDateValidationOfAR(
							attendanceRegularizationRequest.getEmployeeId(),
							attendanceRegularizationRequest.getFromDate(), attendanceRegularizationRequest.getToDate(),
							attendanceRegularizationRequest.getArID());
					logger.info("AR  Count for update on same date--- " + count);
				}

				if (attendanceRegularizationRequest.getStatus().equals(StatusMessage.PENDING_CODE)
						&& attendanceRegularizationRequest.getArID() == null) {

					count = attendanceRegularizationRequestRepository.checkDateValidationOfAROnSameDate(
							attendanceRegularizationRequest.getEmployeeId(),
							attendanceRegularizationRequest.getFromDate(), attendanceRegularizationRequest.getToDate());
					logger.info("AR  Count for new on same date and diff date--- " + count);
				}

				// <<<<<<<<<<<<<<< APPLYING >>>>>>>>>>>>>>>>
				if (count < 1) {

					Long arAppliedCount = getArcountMonth(attendanceRegularizationRequest.getEmployeeId(),
							attendanceRegularizationRequest.getFromDate());
					Long appliedDays = attendanceRegularizationRequest.getDays();
					AttendanceSchemeDTO attendanceSchemeDTO = arDaysSettingOfEmployee(
							attendanceRegularizationRequest.getEmployeeId());
					if (arAppliedCount == null) {
						arAppliedCount = 0L;
					}
					if (appliedDays == null) {
						appliedDays = 0L;
					}
					Long totalArDays = arAppliedCount + appliedDays;

					if (attendanceSchemeDTO != null) {
						Long scheemArDays = attendanceSchemeDTO.getArDays();
						if (scheemArDays != null) {
							if (totalArDays > scheemArDays) {
								throw new ErrorHandling(
										"You can not apply AR Request more than " + scheemArDays + " in a Month");
							}
						}

					}

					attendanceRegularizationRequestRepository.save(attendanceRegularizationRequest);
					Employee employee = employeePersonalInformationService
							.getEmployeeInfo(attendanceRegularizationRequest.getEmployeeId());
					String toReporting = null, ccReportingTo = null;
					Long toReportingId = null;

					List<Object[]> reportingTo = employeePersonalInformationService
							.getEmpReportingToEmail(attendanceRegularizationRequest.getEmployeeId());

					if (reportingTo != null) {
						for (Object obj[] : reportingTo) {
							toReportingId = Long.valueOf(obj[0].toString());
							toReporting = obj[2] != null ? (String) obj[2] : null;
						}
					}
					// ReportingToManager
					List<Object[]> reportingToManager = employeePersonalInformationService
							.getEmpReportingToEmail(toReportingId);
					if (reportingToManager != null) {
						for (Object obj[] : reportingToManager) {
							ccReportingTo = obj[2] != null ? (String) obj[2] : null;
						}
					}

					if (attendanceRegularizationRequest.getStatus().equals(StatusMessage.CANCEL_CODE)) {
						ArrayList<String> reportinsgTo = pushNotificationsServices
								.getReportingEmployeeCode(employee.getEmployeeId());
						try {
							fcm.fcmRequest(reportinsgTo, "AR Apply",
									employee.getFirstName() + " " + employee.getLastName() + " cancelled AR", "teamAR",
									attendanceRegularizationRequest.getArID(), StatusMessage.DESCRIPTION);
						} catch (Exception e) {
							e.printStackTrace();
						}
					} else {

						ArrayList<String> reportinsgTo = pushNotificationsServices
								.getReportingEmployeeCode(employee.getEmployeeId());
						try {
							fcm.fcmRequest(reportinsgTo, "AR Apply",
									employee.getFirstName() + " " + employee.getLastName() + " applied AR", "teamAR",
									attendanceRegularizationRequest.getArID(), StatusMessage.RAF);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}

					String reason = null;
					if (attendanceRegularizationRequest.getArCategory().equals(StatusMessage.AR_MISS_PUNCH_CODE)) {
						reason = StatusMessage.AR_MISS_PUNCH_VALUE;
					} else if (attendanceRegularizationRequest.getArCategory()
							.equals(StatusMessage.AR_CORD_NOT_WORKING_CODE)) {
						reason = StatusMessage.AR_CORD_NOT_WORKING_VALUE;
					} else if (attendanceRegularizationRequest.getArCategory()
							.equals(StatusMessage.AR_POWER_CUT_CODE)) {
						reason = StatusMessage.AR_POWER_CUT_VALUE;
					} else {
						reason = StatusMessage.AR_MECHINE_NOT_WORKING_VALUE;
					}

					String status;
					if (attendanceRegularizationRequest.getStatus().equals(StatusMessage.PENDING_CODE)) {
						status = StatusMessage.PENDING_VALUE;

					} else {
						status = StatusMessage.CANCEL_CODE;
					}
					SimpleDateFormat sm = new SimpleDateFormat("dd-MMM-yyyy");
					String fromDate = sm.format(attendanceRegularizationRequest.getFromDate());
					String toDate = sm.format(attendanceRegularizationRequest.getToDate());
					String path = environment.getProperty("application.companyLogoPath");
					Company company = companyServiceImpl.getCompany(attendanceRegularizationRequest.getCompanyId());
					String companyLogoPath = path + company.getCompanyLogoPath();

					MimeMessage mimeMessage = mailSender.createMimeMessage();
					MimeMessageHelper mimeMessageHelper;
					try {
						mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);

						Map<String, Object> model = new HashMap<String, Object>();
						model.put("firstName", employee.getFirstName());
						model.put("lastName", employee.getLastName());
						model.put("fromDate", fromDate);
						model.put("toDate", toDate);
						model.put("category", reason);
						model.put("day", attendanceRegularizationRequest.getDays());
						model.put("status", status);
						model.put("employeeCode", employee.getEmployeeCode());
						model.put("companyLogoPath", companyLogoPath);
						String text = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine,
								"templates/AR_Request.vm", "UTF-8", model);
						mimeMessageHelper.setText(text, true);
						if (listEmail.getFromMail() != null && !listEmail.getFromMail().trim().equals("")) {
							mimeMessageHelper.setFrom(listEmail.getFromMail());
						} else {
							mimeMessageHelper.setFrom(listEmail.getFromMail());
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
								// toList = toList + "," + toReporting;
								if (toReporting != null && !toReporting.equals("")) {
									toList = toReporting + "," + toList;
								}
							}
							String[] to1 = toList.split(",");
							mimeMessageHelper.setTo(to1);
						} else if (listEmail.getIsApplicableOnReportingTo().equals("Y")) {
							String toList = "";
							if (toReporting != null && !toReporting.equals("")) {
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
							if (ccReportingTo != null && ccReportingTo.equalsIgnoreCase("null")
									&& ccReportingTo.equalsIgnoreCase("")) {
								String toListNew = ccReportingTo;
								mimeMessageHelper.setCc(toListNew);
							}

						}

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
							if (ccReportingTo != null) {
								ccList = ccReportingTo;
								mimeMessageHelper.setCc(ccList);
							}
						}

						if (listEmail.getActiveStatus().equals(StatusMessage.ACTIVE_CODE)) {
							try {
								mailSender.send(mimeMessageHelper.getMimeMessage());
								logger.info("mail send Successfully");
							} catch (Exception ex) {
								logger.info("mail send Failed");
								ex.printStackTrace();
							}

						} else {
							logger.info("mail send Failed");
						}
					} catch (Exception e) {
						logger.info("mail send Failed");
						e.printStackTrace();
					}
				} else {

					throw new ErrorHandling("You have already applied AR in the given duration.");
				}

			}
		} else {
			throw new ErrorHandling("Payroll already processed you can not applied AR");
		}
		return attendanceRegularizationRequest;
	}

	@Override
	public List<Object[]> getAllARRequest(Long companyId) {

		// String query = "SELECT
		// ecd.employeeCode,ecd.firstName,ecd.lastName,ecd.departmentName,ar.arID,ar.dateCreated,ar.arCategory,ar.actionableDate,ar.fromDate,ar.toDate,ar.days,ar.status,ar.employeeId,ar.employeeRemark,ar.userId
		// ,ecd.designationName FROM TMSARRequest ar JOIN EmpCommonDetails ecd on
		// ecd.employeeId=ar.employeeId where ar.companyId=?1 and status !='P'";

		/**
		 * query new for AR request
		 */
		String query = " SELECT ecd.employeeCode,ecd.firstName,ecd.lastName,dept.departmentName,ar.arID,ar.dateCreated,ar.arCategory,ar.actionableDate,ar.fromDate,ar.toDate,ar.days,ar.status,ar.employeeId,ar.employeeRemark,ar.userId  ,deg.designationName ,ecd.employeeLogoPath,ar.approvalRemark FROM TMSARRequest ar JOIN Employee ecd JOIN Department dept JOIN Designation deg on ecd.employeeId=ar.employeeId and ecd.departmentId =dept.departmentId AND deg.designationId=ecd.designationId where ar.companyId=?1 and  status  !='PEN'";
		Query nativeQuery = entityManager.createNativeQuery(query);
		nativeQuery.setParameter(1, companyId);
		@SuppressWarnings("unchecked")
		final List<Object[]> arResultList = nativeQuery.getResultList();
		return arResultList;
	}

	@Override
	public AttendanceRegularizationRequest getARRequest(Long arId) {
		return attendanceRegularizationRequestRepository.getARRequest(arId);
	}

	@Override
	public List<AttendanceRegularizationRequest> getEmployeeARRequest(Long employeeId) {
		return attendanceRegularizationRequestRepository.getEmployeeARRequest(employeeId);
	}

	@Override
	public List<Object[]> getARRequestforMonth(Long companyId, Long employeeId, String fromDate, String toDate) {

		String query = "SELECT * FROM TMSARRequest where companyId=?1 and employeeId=?2 and fromDate >=?3  and toDate <=?4 ORDER by fromDate ASC";
		Query nativeQuery = entityManager.createNativeQuery(query);
		nativeQuery.setParameter(1, companyId).setParameter(2, employeeId).setParameter(3, fromDate).setParameter(4,
				toDate);
		@SuppressWarnings("unchecked")
		final List<Object[]> resultList = nativeQuery.getResultList();
		logger.info("AttendanceRegularizationRequest resultList size------>" + resultList.size());
		return resultList;
		/*
		 * return
		 * attendanceRegularizationRequestRepository.getAttendanceRegularizationRequest(
		 * companyId,employeeId,fromDate,toDate);
		 */
	}

	@Override
	public AttendanceRegularizationRequestDTO arCount(Long companyId, Long employeeId) {
		int arCount = attendanceRegularizationRequestRepository.arCount(companyId, employeeId);
		AttendanceRegularizationRequestDTO attendanceRegularizationRequestDTO = new AttendanceRegularizationRequestDTO();
		attendanceRegularizationRequestDTO.setArCount(arCount);
		return attendanceRegularizationRequestDTO;
	}

	@Override
	public List<AttendanceRegularizationRequest> getEmployeePendingARRequest(Long employeeId) {
		return attendanceRegularizationRequestRepository.getEmployeePendingARRequest(employeeId);
	}

	@Override
	public List<AttendanceRegularizationRequest> getEmployeeApprovedARRequest(Long employeeId, Long companyId,
			String processMonth) {
		return attendanceRegularizationRequestRepository.getEmployeeApprovedARRequest(employeeId, companyId,
				DateUtils.getMonthForProcessMonth(processMonth));

	}

	@Override
	public List<Object[]> getAllPendingARRequest(Long companyId) {
		// String query = "SELECT
		// ecd.employeeCode,ecd.firstName,ecd.lastName,ecd.departmentName,ar.arID,ar.dateCreated,ar.arCategory,ar.actionableDate,ar.fromDate,ar.toDate,ar.days,ar.status,ar.employeeId,ar.employeeRemark,ar.userId
		// ,ecd.designationName FROM TMSARRequest ar JOIN EmpCommonDetails ecd on
		// ecd.employeeId=ar.employeeId where ar.companyId=?1 and status ='PEN'";

		/**
		 * query new for AR request in PEN status
		 */

		String query = " SELECT ecd.employeeCode,ecd.firstName,ecd.lastName,dept.departmentName,ar.arID,ar.dateCreated,ar.arCategory,ar.actionableDate,ar.fromDate,ar.toDate,ar.days,ar.status,ar.employeeId,ar.employeeRemark,ar.userId ,deg.designationName,ecd.employeeLogoPath,ar.approvalRemark  FROM TMSARRequest ar JOIN Employee ecd JOIN Department dept JOIN Designation deg on ecd.employeeId=ar.employeeId and ecd.departmentId =dept.departmentId AND deg.designationId=ecd.designationId where ar.companyId=?1 and status ='PEN'";
		Query nativeQuery = entityManager.createNativeQuery(query);
		nativeQuery.setParameter(1, companyId);
		@SuppressWarnings("unchecked")
		final List<Object[]> arResultList = nativeQuery.getResultList();
		return arResultList;
	}

	@Override
	public void getPendingARCount(Long longCompanyId, EntityCountDTO searchDto) {
		searchDto.setCount(attendanceRegularizationRequestRepository.getPendingARCount(longCompanyId));
	}

	@Override
	public void getNonPendingARCount(Long longCompanyId, EntityCountDTO searchDto) {
		searchDto.setCount(attendanceRegularizationRequestRepository.getNonPendingARCount(longCompanyId));

	}

	@Override
	public AttendanceRegularizationRequestDTO countMyTeamPendingARCount(Long employeeId, Long companyId) {
		int count = attendanceRegularizationRequestRepository.countMyTeamPendingARCount(employeeId, companyId);
		AttendanceRegularizationRequestDTO attendanceRegularizationRequestDTO = new AttendanceRegularizationRequestDTO();
		attendanceRegularizationRequestDTO.setMyTeamARCount(count);
		return attendanceRegularizationRequestDTO;

	}

	@Override
	public AttendanceRegularizationRequestDTO countAllTimeMyTeamPendingARCount(Long employeeId, Long companyId) {
		int count = attendanceRegularizationRequestRepository.countAllTimeMyTeamPendingARCount(employeeId, companyId);
		AttendanceRegularizationRequestDTO attendanceRegularizationRequestDTO = new AttendanceRegularizationRequestDTO();
		attendanceRegularizationRequestDTO.setMyTeamARCount(count);
		return attendanceRegularizationRequestDTO;

	}

	@Override
	public List<Object[]> getAllApprovalsPending(Long companyId, Long employeeId) {
		// TODO Auto-generated method stub
		return attendanceRegularizationRequestRepository.getAllApprovalsPending(companyId, employeeId);
	}

	@Override
	public List<Object[]> getAllEmpApprovalsPending(Long companyId, SearchDTO searcDto) {
		// TODO Auto-generated method stub
		return aRRequestPagingAndFilterRepository.getAllEmpApprovalsPending(companyId, searcDto);
	}

	@Override
	public List<Object[]> getAllApprovalsNonPending(Long companyId, Long employeeId, SearchDTO searcDto) {
		// TODO Auto-generated method stub
		// return
		// attendanceRegularizationRequestRepository.getAllApprovalsNonPending(companyId,
		// employeeId);

		return aRRequestPagingAndFilterRepository.getAllApprovalsNonPending(companyId, employeeId, searcDto);
	}

	@Override
	public void updateById(AttendanceRegularizationRequest attendanceRegularizationRequest) {
		attendanceRegularizationRequestRepository.updateById(attendanceRegularizationRequest.getArID(),
				attendanceRegularizationRequest.getStatus(), attendanceRegularizationRequest.getApprovalId(),
				attendanceRegularizationRequest.getActionableDate());

	}

	@Override
	public List<AttendanceRegularizationRequest> getEmployeeARRequestList(Long employeeId) {
		// TODO Auto-generated method stub
		return attendanceRegularizationRequestRepository.getEmployeeARRequestList(employeeId);
	}

	private Long getArcountMonth(Long employeeId, Date fromDateMonth) {
		return attendanceRegularizationRequestRepository.arCountsOfEmployee(employeeId, fromDateMonth);
	}

	private AttendanceSchemeDTO arDaysSettingOfEmployee(Long employeeId) {
		List<Object[]> objs = attendanceSchemeRepository.arDaysSettingOfEmployee(employeeId);

		if (objs == null || objs.size() == 0) {
			return null;
		}
		for (Object[] obj : objs) {
			AttendanceSchemeDTO attendanceSchemeDTO = new AttendanceSchemeDTO();
			Long attendanceSchemeId = obj[0] != null ? Long.parseLong(obj[0].toString()) : null;
			attendanceSchemeDTO.setAttendanceSchemeId(attendanceSchemeId);
			Long employeeIds = obj[2] != null ? Long.parseLong(obj[2].toString()) : null;
			attendanceSchemeDTO.setEmployeeId(employeeIds);
			Long userId = obj[3] != null ? Long.parseLong(obj[3].toString()) : null;
			attendanceSchemeDTO.setUserId(userId);
			String aschemeStatus = obj[4] != null ? obj[4].toString() : null;
			attendanceSchemeDTO.setAschemeStatus(aschemeStatus);
			Long arDays = obj[5] != null ? Long.parseLong(obj[5].toString()) : null;
			attendanceSchemeDTO.setArDays(arDays);
			Long attendanceTypeId = obj[6] != null ? Long.parseLong(obj[6].toString()) : null;
			attendanceSchemeDTO.setAttendanceTypeId(attendanceTypeId);
			String typeCode = obj[7] != null ? obj[7].toString() : null;
			attendanceSchemeDTO.setTypeCode(typeCode);
			String typeTransactionstatus = obj[8] != null ? obj[8].toString() : null;
			attendanceSchemeDTO.setaTypeTransactionstatus(typeTransactionstatus);
			return attendanceSchemeDTO;
		}
		return null;
	}

	@Override
	public AttendanceRegularizationRequestDTO allEmployeePendingARCount(Long companyId) {
		int count = attendanceRegularizationRequestRepository.allEmployeePendingARCount(companyId);
		AttendanceRegularizationRequestDTO attendanceRegularizationRequestDTO = new AttendanceRegularizationRequestDTO();
		attendanceRegularizationRequestDTO.setMyTeamARCount(count);
		return attendanceRegularizationRequestDTO;
	}

	@Override
	public AttendanceRegularizationRequestDTO allTimeAllEmployeePendingARCount(Long companyId) {
		int count = attendanceRegularizationRequestRepository.allTimeAllEmployeePendingARCount(companyId);
		AttendanceRegularizationRequestDTO attendanceRegularizationRequestDTO = new AttendanceRegularizationRequestDTO();
		attendanceRegularizationRequestDTO.setMyTeamARCount(count);
		return attendanceRegularizationRequestDTO;
	}

	public List<AttendanceRegularizationRequest> getPenAprARRequestList(Long employeeId) {
		return attendanceRegularizationRequestRepository.getPenAprARRequestList(employeeId);
	}

	@Override
	public void getARCountPending(Long longCompanyId, EntityCountDTO searchDto) {

		searchDto.setCount(attendanceRegularizationRequestRepository.getARCountPending(longCompanyId));
	}

	@Override
	public void getARCountNonPending(Long longCompanyId, EntityCountDTO searchDto) {

		searchDto.setCount(attendanceRegularizationRequestRepository.getARCountNonPending(longCompanyId));
	}
	
	@Override
	public List<Object[]> getARRequestDetailsbyPagination(Long empId, SearchDTO searchDTO) {
		return aRRequestPagingAndFilterRepository.getARRequestDetailsbyPagination(empId,searchDTO);
	}

	@Override
	public List<Object[]> getARPendingRequestDetailsbyPagination(Long empId, SearchDTO searcDto) {
		return aRRequestPagingAndFilterRepository.getARPendingRequestDetailsbyPagination(empId,searcDto);
	}
}
