package com.csipl.hrms.org;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.mail.internet.MimeMessage;

import org.apache.velocity.app.VelocityEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.ui.velocity.VelocityEngineUtils;

import com.csipl.common.model.EmailConfiguration;
import com.csipl.common.model.EmailNotificationMaster;
import com.csipl.hrms.common.constant.StatusMessage;
import com.csipl.hrms.model.common.Company;
import com.csipl.hrms.model.employee.Employee;
import com.csipl.hrms.service.common.EmailNotificationService;
import com.csipl.hrms.service.common.repository.EmailConfigurationRepository;
import com.csipl.hrms.service.employee.EmployeePersonalInformationService;
import com.csipl.hrms.service.organization.CompanyService;

/**
 * 
 * @author Bharat
 *
 */
@Component
public class BirthDaySchedular {
	private static final Logger logger = LoggerFactory.getLogger(BirthDaySchedular.class);
	public static final String ENCODING = "UTF-8";
	private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");

	@Autowired
	CompanyService companyService;

	@Autowired
	EmployeePersonalInformationService employeePersonalInformationService;

	@Autowired
	private VelocityEngine velocityEngine;

	@Autowired
	EmailNotificationService service;
	@Autowired
	private Environment environment;
	@Autowired
	EmailConfigurationRepository emailConfugrationRepository;

	@Autowired
	EmailNotificationService emailNotificationService;
	
	@Autowired
	Environment env;

	public void scheduleTaskWithFixedDelay() {
	}

	public void scheduleTaskWithInitialDelay() {
	}

	Object tenant = "computronics.in";

	@Scheduled(cron = "0 0 10 * * ?")
	public void scheduleTaskWithCronExpression() {

		logger.info("Cron Task :: Execution Time - {}", dateTimeFormatter.format(LocalDateTime.now()));

		Date now = new Date();
		SimpleDateFormat simpleDateformat = new SimpleDateFormat("EEEE");
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(now);
		Map<Object, Object> mapObj =  MasterService.getDataSourceHashMap(env);
		mapObj.forEach((k, v) -> {
			this.tenant = k;

			System.out.println("Item : " + k + " Count : " + v);
			TenantContext.setTenantId(this.tenant != null ? this.tenant.toString() : "computronics.in");
			Long companyId = 1L;

			try {
				String toReporting = null, ccReportingTo = null;
				Long toReportingId = null;
				List<Employee> employees = employeePersonalInformationService.findBirthDayEmployees(1L);
				Properties velocityProperties = new Properties();
				velocityProperties.setProperty("file.resource.loader.path", "/templates");
				EmailConfiguration confugration = null;
				confugration = emailConfugrationRepository.findEMail();
				EmailNotificationMaster listEmail = emailNotificationService
						.findEMailListByStatus(StatusMessage.HAPPY_BIRTHDAY_CODE);
				JavaMailSenderImpl mailSender = null;

				mailSender = new JavaMailSenderImpl();
				Properties props = mailSender.getJavaMailProperties();
				if (confugration.getHost().equals(StatusMessage.HOST_NAME_SMTP)) {
					logger.info("===========Birthday==.getJavaMailSender()===============" + listEmail.toString());
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
					logger.info("===========Birthday==.getJavaMailSender()================" + listEmail.toString());
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
				for (Employee emp : employees) {

					List<Object[]> reportingTo = employeePersonalInformationService
							.getEmpReportingToEmail(emp.getEmployeeId());
					if (reportingTo.size() > 0) {
						for (Object obj[] : reportingTo) {
							if (obj[2] != null)
								toReportingId = Long.valueOf(obj[0].toString());
							toReporting = obj[2].toString();
						}
					}

					List<Object[]> reportingToManager = employeePersonalInformationService
							.getEmpReportingToEmail(toReportingId);
					if (reportingToManager.size() > 0) {
						for (Object obj[] : reportingToManager) {
							ccReportingTo = obj[2].toString();
						}
					}
					Company company = companyService.getCompany(emp.getCompany().getCompanyId());
					String path1 = environment.getProperty("application.companyLogoPath");
					String companyLogoPath = path1 + company.getCompanyLogoPath();
					MimeMessage mimeMessage = mailSender.createMimeMessage();
					MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
					Map<String, Object> model = new HashMap<String, Object>();
					model.put("firstName", emp.getFirstName());
					model.put("lastName", emp.getLastName());
					model.put("companyName", company.getCompanyName());
					model.put("companyLogo", companyLogoPath);
					String text = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine,
							"/templates/birthday_new.vm", "UTF-8", model);
					mimeMessageHelper.setFrom(listEmail.getUserName());
					mimeMessageHelper.setSubject(listEmail.getTitle());
					logger.info("getEmailId() " + emp.getOfficialEmail());

					/**
					 * getIsApplicableOnReportingTo
					 */
					if (listEmail.getToMail() != null && !listEmail.getToMail().equals("")) {
						String toList = emp.getOfficialEmail() + "," + listEmail.getToMail();
						String[] to = toList.split(",");
						mimeMessageHelper.setTo(to);
					} else {
						String to = emp.getOfficialEmail();
						mimeMessageHelper.setTo(to);
					}

					if (listEmail.getCc() != null && !listEmail.getCc().equals("")) {
						String ccList = listEmail.getCc();

						if (listEmail.getIsApplicableOnReportingToManager().equals("Y")) {
							if (ccReportingTo.equals(null)) {
								ccList = "" + ccList;
							} else {
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

					if (listEmail.getCc() != null && !listEmail.getCc().equals("")) {
						String ccList = listEmail.getCc();
						String[] cc1 = ccList.split(",");
						mimeMessageHelper.setCc(cc1);
					}

					if (listEmail.getBcc() != null && !listEmail.getBcc().equals("")) {
						String bccList1 = listEmail.getBcc();
						String[] bcc1 = bccList1.split(",");
						mimeMessageHelper.setBcc(bcc1);
					}
					mimeMessageHelper.setText(text, true);

					if (listEmail.getActiveStatus().equals(StatusMessage.ACTIVE_CODE)) {
						try {
							mailSender.send(mimeMessageHelper.getMimeMessage());
							logger.info(" BirthDaySchedular mail send Successfully");
						} catch (Exception e) {
							e.printStackTrace();
							logger.info(" BirthDaySchedular mail send failed");
						}
					} else
						logger.info(" BirthDaySchedular mail send failed --" + listEmail.getActiveStatus());

				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		});

	}

	// @Scheduled(fixedRate = 2000)
	public void scheduleTaskWithFixedRate() {
		logger.info("Fixed Rate Task :: Execution Time - {}", dateTimeFormatter.format(LocalDateTime.now()));
	}

}