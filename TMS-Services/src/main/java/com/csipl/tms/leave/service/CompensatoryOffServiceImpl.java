package com.csipl.tms.leave.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

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
import com.csipl.hrms.common.util.ErrorHandling;
import com.csipl.hrms.model.common.Company;
import com.csipl.hrms.model.employee.Employee;
import com.csipl.hrms.service.common.EmailNotificationService;
import com.csipl.hrms.service.common.PushNotificationsServiceImpl;
import com.csipl.hrms.service.common.PushNotificationsServices;
import com.csipl.hrms.service.common.repository.EmailConfigurationRepository;
import com.csipl.hrms.service.common.repository.FcmNotificationRepository;
import com.csipl.hrms.service.employee.EmployeePersonalInformationService;
import com.csipl.hrms.service.organization.CompanyServiceImpl;
import com.csipl.tms.dto.common.SearchDTO;
import com.csipl.tms.dto.leave.CompensatoryOffDTO;
import com.csipl.tms.leave.repository.CompensatoryOffLeaveRepository;
import com.csipl.tms.leave.repository.CompensatoryOffRepository;
import com.csipl.tms.model.leave.CompensatoryOff;

@Service("compensatoryOffService")
public class CompensatoryOffServiceImpl implements CompensatoryOffService {

	private static final Logger log = LoggerFactory.getLogger(CompensatoryOffServiceImpl.class);
	public static final String ENCODING = "UTF-8";
	@Autowired
	CompensatoryOffRepository compensatoryOffRepository;

 
	@Autowired
	private EmployeePersonalInformationService employeePersonalInformationService;
 
	@Autowired
	PushNotificationsServices pushNotificationsServices;

	@Autowired
	PushNotificationsServiceImpl fcm;

	@Autowired
	FcmNotificationRepository fcmNotificationRepository;

	@Autowired
	private CompanyServiceImpl companyServiceImpl;
	@Autowired
	private VelocityEngine velocityEngine;
	@Autowired
	private Environment environment;
	
	@Autowired
	private CompensatoryOffLeaveRepository compensatoryOffLeaveRepository;
	
	@Autowired
	EmailConfigurationRepository emailConfugrationRepository;

	@Autowired
	EmailNotificationService emailNotificationService;

	@SuppressWarnings("null")
	@Override
	public void saveAll(CompensatoryOff compensatoryOff) throws ErrorHandling {
		// email configuration start
		EmailConfiguration configuration = null;
		EmailNotificationMaster listEmail =null;
		configuration = emailConfugrationRepository.findEMail();
		if (compensatoryOff.getApprovalId() != null) {
			  listEmail = emailNotificationService
					.findEMailListByStatus(StatusMessage.COMP_OFF_APPROVED_CODE);
		}else {
			  listEmail = emailNotificationService
					.findEMailListByStatus(StatusMessage.COMPENSATORY_OFF_CODE);
		}
		
		JavaMailSenderImpl mailSender = null;
		mailSender = new JavaMailSenderImpl();
		Properties props = mailSender.getJavaMailProperties();
		if (configuration.getHost().equals(StatusMessage.HOST_NAME_SMTP)) {
			log.info("===========Comp Off==.getJavaMailSender()===============" + listEmail.toString());
			mailSender.setHost(configuration.getHost());
			mailSender.setPort(configuration.getPort());
			mailSender.setUsername(listEmail.getUserName());
			mailSender.setPassword(listEmail.getPassword());
			props.put("mail.transport.protocol", configuration.getProtocol());
			props.put("mail.smtp.auth", configuration.getAuth());
			props.put("mail.smtp.ssl.trust", configuration.getSslName());
			props.put("mail.smtp.starttls.enable", configuration.getStarttlsName());
			props.put("mail.mime.charset", ENCODING);

		} else {
			log.info("===========Comp Off ==.getJavaMailSender()================" + listEmail.toString());
			mailSender.setHost(configuration.getHost());
			mailSender.setPort(configuration.getPort());
			mailSender.setUsername(listEmail.getUserName());
			mailSender.setPassword(listEmail.getPassword());
			props.put("mail.transport.protocol", configuration.getProtocol());
			props.put("mail.smtp.auth", configuration.getAuth());
			props.put("mail.smtp.ssl.trust", configuration.getSslName());
			props.put("mail.smtp.starttls.enable", configuration.getStarttlsName());
			props.put("mail.mime.charset", ENCODING);
			props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
			props.put("mail.smtp.socketFactory.fallback", "true");
		} // end email config

		if (compensatoryOff.getApprovalId() != null) {
			String toReporting = "", ccReportingTo = null;
			Long toReportingId = null;
			log.info("model" + compensatoryOff.getEmployeeId());
			CompensatoryOff compensatoryOffNew = compensatoryOffRepository.save(compensatoryOff);
			 
			List<Object[]> reportingTo = employeePersonalInformationService
					.getEmpReportingToEmail(compensatoryOffNew.getEmployeeId());
			if (reportingTo.size() > 0) {
				for (Object obj[] : reportingTo) {
					toReportingId = Long.valueOf(obj[0].toString());
					toReporting = obj[2] != null ? (String) obj[2] : null;  
				}
			}
			// ReportingToManager
			List<Object[]> reportingToManager = employeePersonalInformationService
					.getEmpReportingToEmail(toReportingId);
			if (reportingToManager.size() > 0) {
				for (Object obj[] : reportingToManager) {
					ccReportingTo="";
					ccReportingTo = obj[2] != null ? (String) obj[2] : null;  
				}
			}

			Employee emp = employeePersonalInformationService.findEmployeesById(compensatoryOffNew.getEmployeeId());
		 
			String status = null;
			if (compensatoryOffNew.getStatus().equals(StatusMessage.APPROVED_CODE)) {
				status = StatusMessage.APPROVED_VALUE;
				Long useId = fcmNotificationRepository.getUserId(emp.getEmployeeId());

				ArrayList<String> tokenList = fcmNotificationRepository.getTokenList(useId);
				try {
					fcm.fcmRequest(tokenList, "CompOff Apply", "CompOff has been Approved", "mycompoff",
							compensatoryOffNew.getTmsCompensantoryOffId(), StatusMessage.DESCRIPTION);
				} catch (Exception e) {
					e.printStackTrace();
				}

			} else {
				status = StatusMessage.REJECTED_VALUE;
				Long useId = fcmNotificationRepository.getUserId(emp.getEmployeeId());
				ArrayList<String> tokenList = fcmNotificationRepository.getTokenList(useId);
				try {
					fcm.fcmRequest(tokenList, "CompOff Apply", "CompOff has been Rejected", "mycompoff",
							compensatoryOffNew.getTmsCompensantoryOffId(), StatusMessage.DESCRIPTION);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			SimpleDateFormat sm = new SimpleDateFormat("dd-MMM-yyyy");
			String fromDate = sm.format(compensatoryOffNew.getFromDate());
			String toDate = sm.format(compensatoryOffNew.getToDate());
			String path = environment.getProperty("application.companyLogoPath");
			Company company = companyServiceImpl.getCompany(compensatoryOffNew.getCompanyId());
			String companyLogoPath = path + company.getCompanyLogoPath();
			 
			MimeMessage mimeMessage = mailSender.createMimeMessage();
			MimeMessageHelper mimeMessageHelper;
			try {
				mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);

				Map<String, Object> model = new HashMap<String, Object>();
				model.put("firstName", emp.getFirstName());
				model.put("lastName", emp.getLastName());
				model.put("fromDate", fromDate);
				model.put("toDate", toDate);
				model.put("status", status);
				model.put("companyLogoPath", companyLogoPath);
				String text = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine,
						"templates/comp_off_approved.vm", "UTF-8", model);
				if (listEmail.getUserName() != null && !listEmail.getUserName().trim().equals("")) {
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
					 
					String toList = "";
					if (emp.getOfficialEmail() == null || emp.getOfficialEmail().equals("")) {
						toList = listEmail.getToMail();
					} else {
						toList = emp.getOfficialEmail() + "," + listEmail.getToMail();
					}
					if (listEmail.getIsApplicableOnReportingTo().equals("Y")) {

						 
						if (toReporting != null && !toReporting.equals("")) {
							toList = toReporting + "," + toList;
						}
					}
					String[] to1 = toList.split(",");
					mimeMessageHelper.setTo(to1);

				} else if (listEmail.getIsApplicableOnReportingTo().equals("Y")) {
					String toList = emp.getOfficialEmail() + "," + toReporting;
					String[] to1 = toList.split(",");
					mimeMessageHelper.setTo(to1);
				} else {
					if (toReporting != null || toReporting.equalsIgnoreCase("null")
							|| toReporting.equalsIgnoreCase("")) {
						String toListNew = emp.getOfficialEmail();
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
 
						if (ccReportingTo != null && !ccReportingTo.equals("")) {
							ccList = ccReportingTo + "," + ccList;
						}

					}
					String[] cc1 = ccList.split(",");
					mimeMessageHelper.setCc(cc1);

				} else if (listEmail.getIsApplicableOnReportingToManager().equals("Y")) {
					String ccList = "";
					if (ccReportingTo != null && !ccReportingTo.equals("")) {
						ccList = ccReportingTo;
						mimeMessageHelper.setCc(ccList);
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
						log.info("mail send Successfully");
					} catch (Exception e) {
						log.info("mail send failed");
						e.printStackTrace();
					}
				} else
					log.info("mail send failed");
			} catch (MessagingException e) {
				e.printStackTrace();
			}
		} else {
			int count = 0;
			String toReporting = "", ccReportingTo = null;
			Long toReportingId = null;
			log.info("approval id===" + compensatoryOff.getApprovalId());
			if (compensatoryOff.getStatus().equals(StatusMessage.PENDING_CODE)
					&& compensatoryOff.getTmsCompensantoryOffId() != null) {

				count = compensatoryOffRepository.checkDateValidation1(compensatoryOff.getEmployeeId(),
						compensatoryOff.getFromDate(), compensatoryOff.getToDate(),
						compensatoryOff.getTmsCompensantoryOffId());
				log.info("AR  Count for update on same date--- " + count);
			}

			if ((compensatoryOff.getStatus().equals(StatusMessage.PENDING_CODE))
					&& (compensatoryOff.getTmsCompensantoryOffId()) == null) {

				count = compensatoryOffRepository.checkDateValidation(compensatoryOff.getEmployeeId(),
						compensatoryOff.getFromDate(), compensatoryOff.getToDate());
				log.info("AR  Count for new on same date and diff date--- " + count);
			}
			if (count < 1) {
				log.info("model" + compensatoryOff.getEmployeeId());
				CompensatoryOff compensatoryOffNew = compensatoryOffRepository.save(compensatoryOff);

				List<Object[]> reportingTo = employeePersonalInformationService
						.getEmpReportingToEmail(compensatoryOffNew.getEmployeeId());
				//List<String> to = new ArrayList<String>();
				if (reportingTo.size() > 0) {
					for (Object obj[] : reportingTo) {
						toReportingId = Long.valueOf(obj[0].toString());
						toReporting="";
						toReporting = obj[2] != null ? (String) obj[2] : null; 
					//	to.add(obj[2].toString());
					}
				}

				// ReportingToManager
				List<Object[]> reportingToManager = employeePersonalInformationService
						.getEmpReportingToEmail(toReportingId);
				if (reportingToManager.size() > 0) {
					for (Object obj[] : reportingToManager) {
						ccReportingTo="";
						ccReportingTo = obj[2] != null ? (String) obj[2] : null; 
					}
				}

				Employee employee = employeePersonalInformationService
						.findEmployeesById(compensatoryOffNew.getEmployeeId());

				String status1;
				if (compensatoryOffNew.getStatus().equals(StatusMessage.CANCEL_CODE)) {
					ArrayList<String> reportinsgTo = pushNotificationsServices
							.getReportingEmployeeCode(employee.getEmployeeId());
					try {
						fcm.fcmRequest(reportinsgTo, "CompOff Apply",
								employee.getFirstName() + " " + employee.getLastName() + " cancelled CompOff",
								"teamcompoff", compensatoryOff.getTmsCompensantoryOffId(), StatusMessage.DESCRIPTION);
					} catch (Exception e) {
						e.printStackTrace();
					}
				} else {

					ArrayList<String> reportinsgTo = pushNotificationsServices
							.getReportingEmployeeCode(employee.getEmployeeId());
					try {
						fcm.fcmRequest(reportinsgTo, "CompOff Apply",
								employee.getFirstName() + " " + employee.getLastName() + " applied CompOff",
								"teamcompoff", compensatoryOff.getTmsCompensantoryOffId(), StatusMessage.RAF);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}

				SimpleDateFormat sm = new SimpleDateFormat("dd-MMM-yyyy");
				String fromDate = sm.format(compensatoryOffNew.getFromDate());
				String toDate = sm.format(compensatoryOffNew.getToDate());
				String path = environment.getProperty("application.companyLogoPath");
				Company company = companyServiceImpl.getCompany(compensatoryOffNew.getCompanyId());
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
					model.put("day", compensatoryOffNew.getDays());
					model.put("companyLogoPath", companyLogoPath);
					model.put("employeeCode", employee.getEmployeeCode());

					String text = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine,
							"templates/comp_off_request.vm", "UTF-8", model);
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
					// mimeMessageHelper.setTo(toMail);
					/**
					 * getIsApplicableOnReportingTo
					 */
					if (listEmail.getToMail() != null && !listEmail.getToMail().equals("")) {
						String toList = listEmail.getToMail();

						if (listEmail.getIsApplicableOnReportingTo().equals("Y")) {
							if (toReporting != null && !toReporting.equals("")) {
								toList = toReporting + "," + toList;
							}
							// toList = toReporting + "," + toList;
						}
						String[] to1 = toList.split(",");
						mimeMessageHelper.setTo(to1);

					} else if (listEmail.getIsApplicableOnReportingTo().equals("Y")) {
						String toList = "";
						if (toReporting != null && !toReporting.equals("")) {
							toList = toReporting;
						} else {
							toList = StatusMessage.EMAIL;
						}

						mimeMessageHelper.setTo(toList);
					} else {
						if (toReporting != null || toReporting.equalsIgnoreCase("null")
								|| toReporting.equalsIgnoreCase("")) {
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

					if (listEmail.getBcc() != null && !listEmail.getBcc().equals("")) {
						String bccList = listEmail.getBcc();
						String[] bcc = bccList.split(",");
						mimeMessageHelper.setBcc(bcc);
					}
					mimeMessageHelper.setText(text, true);

					String status;
					if (compensatoryOffNew.getStatus().equals(StatusMessage.PENDING_CODE)) {
						status = StatusMessage.PENDING_CODE;
						if (listEmail.getActiveStatus().equals(StatusMessage.ACTIVE_CODE)) {
							try {
								mailSender.send(mimeMessageHelper.getMimeMessage());
								log.info("mail send Successfully");
							} catch (Exception e) {
								log.info("mail send failed");
								e.printStackTrace();
							}

						} else
							log.info("mail send fail");
					} else {
						status = StatusMessage.CANCEL_CODE;
					}
				} // close try block
				catch (MessagingException e) {
					e.printStackTrace();
				}
			} // close if block
			else {
				log.info("You have already applied AR in the given duration.");
				throw new ErrorHandling("You have already applied CompOff in the given duration.");
			}
		}
	}

	@Override
	public List<CompensatoryOff> findAllCompensatoryOff(Long companyId) {

		return compensatoryOffRepository.findAllCompensatoryOff(companyId);
	}

	@Override
	public List<CompensatoryOff> findMyCompOffPendingReqList(Long employeeId) {
		String status = StatusMessage.PENDING_CODE;
		return compensatoryOffRepository.findMyCompOffPendingReqList(employeeId, status);
	}

	@Override
	public List<CompensatoryOff> findMyCompOffExcludedPendingReqList(Long employeeId) {
		String status = StatusMessage.PENDING_CODE;
		return compensatoryOffRepository.findMyCompOffExcludedPendingReqList(employeeId, status);
	}

	@Override
	public CompensatoryOff getCompensatoryOff(Long tmsCompensantoryOffId) {

		return compensatoryOffRepository.findOne(tmsCompensantoryOffId);
	}

	@Override
	public List<CompensatoryOff> findAllCompOffPendingReqList(Long companyId) {
		String status = StatusMessage.PENDING_CODE;
		return compensatoryOffRepository.findAllCompOffPendingReqList(companyId, status);
	}

	@Override
	public List<CompensatoryOff> findAllCompOffExcludedPendingReqList(Long companyId) {
		String status = StatusMessage.PENDING_CODE;
		return compensatoryOffRepository.findAllCompOffExcludedPendingReqList(companyId, status);
	}

	@Override
	public List<Object[]> getApprovalsPendingCompOff(Long companyId, Long employeeId) {
		// TODO Auto-generated method stub
		return compensatoryOffRepository.getApprovalsPendingCompOff(companyId, employeeId);
	}

	@Override
	public List<Object[]> getAllEmpApprovalsPendingCompOff(Long companyId, SearchDTO searcDto) {
		// TODO Auto-generated method stub
		return compensatoryOffLeaveRepository.getAllEmpApprovalsPendingCompOff(companyId, searcDto);
	}

	@Override
	public List<Object[]> getApprovalsNonPendingCompOff(Long companyId, Long employeeId, SearchDTO searcDto) {
		// TODO Auto-generated method stub
		return compensatoryOffLeaveRepository.getApprovalsNonPendingCompOff(companyId, employeeId, searcDto);
	}

	@Override
	public CompensatoryOffDTO compOffCountMyTeam(Long employeeId, Long companyId) {
		int count = compensatoryOffRepository.compOffCountPandingMyTeam(employeeId, companyId);
		CompensatoryOffDTO compensatoryOffDTO = new CompensatoryOffDTO();
		compensatoryOffDTO.setCompOffCountMyTeam(count);
		log.info("count compOff--->" + compensatoryOffDTO.getCompOffCountMyTeam());
		return compensatoryOffDTO;
	}

	@Override
	public CompensatoryOffDTO compOffAllTimeCountMyTeam(Long employeeId, Long companyId) {
		int count = compensatoryOffRepository.compOffAllTimeCountPandingMyTeam(employeeId, companyId);
		CompensatoryOffDTO compensatoryOffDTO = new CompensatoryOffDTO();
		compensatoryOffDTO.setCompOffCountMyTeam(count);
		log.info("count compOff--->" + compensatoryOffDTO.getCompOffCountMyTeam());
		return compensatoryOffDTO;
	}
	
	@Override
	public CompensatoryOffDTO compOffCountMy(Long employeeId, Long companyId) {
		int count = compensatoryOffRepository.compOffCountPandingMy(employeeId, companyId);
		CompensatoryOffDTO compensatoryOffDTO = new CompensatoryOffDTO();
		compensatoryOffDTO.setCompOffCountMy(count);
		log.info("count compOff--->" + compensatoryOffDTO.getCompOffCountMy());
		return compensatoryOffDTO;
	}

	@Override
	public List<CompensatoryOff> getPendingCompffOfEntitybyPagination(Long employeeId, SearchDTO searcDto) {
		int offset = searcDto.getOffset();
		int limit = searcDto.getLimit();
		int page = (offset + limit) / limit;
		String status = StatusMessage.PENDING_CODE;
		
		log.info("myOffset.....>" + offset);
		log.info("limit.....>" + limit);
		log.info("page.....>" + page);
		log.info("dataStatus.....>" + searcDto.getDataStatus());
		
		List<CompensatoryOff> pendingCompffOfEntitybyPagination =new ArrayList<CompensatoryOff>();
		
		if(searcDto.getDataStatus().equalsIgnoreCase("pen")) {
			/*log.info("select * from TMSCompensantoryOff where employeeId="+employeeId+" AND status="+status
					+ " and  dateCreated > DATE_SUB(now(), INTERVAL 6 MONTH) ORDER BY  tmsCompensantoryOffId "
					+ " DESC limit"+ limit+" offset "+offset);*/
	      pendingCompffOfEntitybyPagination = compensatoryOffRepository
				.getPendingCompffOfEntitybyPagination(employeeId,status, limit, offset);
		}else {
			/*log.info("select * from TMSCompensantoryOff where employeeId="+employeeId+" AND status!="+status
					+ " and  dateCreated > DATE_SUB(now(), INTERVAL 6 MONTH) ORDER BY  tmsCompensantoryOffId "
					+ " DESC limit"+ limit+" offset "+offset);*/
			 pendingCompffOfEntitybyPagination = compensatoryOffRepository
						.getNonPendingCompffOfEntitybyPagination(employeeId,status, limit, offset);
		}
		return pendingCompffOfEntitybyPagination;
	}

}
