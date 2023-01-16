package com.csipl.hrms.org;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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

@Component
public class WorkAnniversarySchedular {
	public static final String ENCODING = "UTF-8";
	private static final Logger logger = LoggerFactory.getLogger(WorkAnniversarySchedular.class);
	private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");

	@Autowired
	EmployeePersonalInformationService employeePersonalInformationService;

	@Autowired
	private VelocityEngine velocityEngine;

	@Autowired
	CompanyService companyService;

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
		Map<Object, Object> mapObj = MasterService.getDataSourceHashMap(env);
		mapObj.forEach((k, v) -> {
			this.tenant = k;

			logger.info("Item : " + k + " Count : " + v);
			TenantContext.setTenantId(this.tenant != null ? this.tenant.toString() : "computronics.in");
			Long companyId = 1L;
			try {

				EmailConfiguration confugration = null;
				confugration = emailConfugrationRepository.findEMail();
				EmailNotificationMaster listEmail = emailNotificationService
						.findEMailListByStatus(StatusMessage.HAPPY_WORK_ANNIVERSARY_CODE);
				JavaMailSenderImpl mailSender = null;

				mailSender = new JavaMailSenderImpl();
				Properties props = mailSender.getJavaMailProperties();
				if (confugration.getHost().equals(StatusMessage.HOST_NAME_SMTP)) {
					logger.info("===========WorkAnniversarySchedular==.getJavaMailSender()==============="
							+ listEmail.toString());
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
					logger.info("===========WorkAnniversarySchedular==.getJavaMailSender()================"
							+ listEmail.toString());
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
				List<Employee> employees = employeePersonalInformationService
						.findJoiningAnniversaryEmployees(listEmail.getCompanyId());
				for (Employee emp : employees) {
					Company company = companyService.getCompany(emp.getCompany().getCompanyId());
					MimeMessage mimeMessage = mailSender.createMimeMessage();
					MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
					Map<String, Object> model = new HashMap<String, Object>();
					model.put("companyName", company.getCompanyName());
					model.put("companyLogo", company.getCompanyLogoPath());
					String text = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine,
							"templates/work_anniversary_new.vm", "UTF-8", model);

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
				 
					mimeMessageHelper.setTo(emp.getOfficialEmail());
					if (listEmail.getToMail() != null && !listEmail.getToMail().equals("")) {
						String toList1 = emp.getOfficialEmail() + "," + listEmail.getToMail();
						String[] to1 = toList1.split(",");
						mimeMessageHelper.setTo(to1);
					} else {
						String ccList1 = emp.getOfficialEmail();
						mimeMessageHelper.setTo(ccList1);
					}

					if (listEmail.getCc() != null && !listEmail.getCc().equals("")) {
						String ccList1 = listEmail.getCc();
						String[] cc1 = ccList1.split(",");
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
							logger.info(" WorkAnniversarySchedular mail send Successfully");
						} catch (Exception e) {
							e.printStackTrace();
							logger.info(" WorkAnniversarySchedular mail send fail");
						}
					} else
						logger.info(" WorkAnniversarySchedular mail send failed getActiveStatus()"
								+ listEmail.getActiveStatus());

				}

			} catch (Exception ex) {
				ex.printStackTrace();
			}
		});

	}

	public void scheduleTaskWithFixedRate() {
		logger.info("Fixed Rate Task :: Execution Time - {}", dateTimeFormatter.format(LocalDateTime.now()));
	}

}
