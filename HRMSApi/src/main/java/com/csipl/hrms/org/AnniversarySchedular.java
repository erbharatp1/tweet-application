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
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.ui.velocity.VelocityEngineUtils;

import com.csipl.common.model.Mail;
import com.csipl.hrms.common.enums.MailEnum;
import com.csipl.hrms.common.util.HrmsGlobalConstantUtil;
import com.csipl.hrms.model.common.Company;
import com.csipl.hrms.model.employee.Employee;
import com.csipl.hrms.service.common.MailService;
import com.csipl.hrms.service.employee.EmployeePersonalInformationService;
import com.csipl.hrms.service.organization.CompanyService;

@Component
public class AnniversarySchedular {

	private static final Logger logger = LoggerFactory.getLogger(AnniversarySchedular.class);
	private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
	@Autowired
	CompanyService companyService;
	@Autowired
	EmployeePersonalInformationService employeePersonalInformationService;

	@Autowired
	private VelocityEngine velocityEngine;

	@Autowired
	MailService mailService;

	@Autowired
	JavaMailSender mailSender;

	@Autowired
	private Environment environment;

	public void scheduleTaskWithFixedDelay() {
	}

	public void scheduleTaskWithInitialDelay() {
	}

	@Scheduled(cron = "0 0 10 * * ?")
	public void scheduleTaskWithCronExpression() {

		logger.info("Cron Task :: Execution Time - {}", dateTimeFormatter.format(LocalDateTime.now()));

		String path = "";
		Date now = new Date();

		SimpleDateFormat simpleDateformat = new SimpleDateFormat("EEEE");
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(now);

		List<Mail> mails = mailService.getEmailDetailForSchedular(MailEnum.Anniversery.name());

		try {
			for (Mail mail : mails) {

				String from = mail.getFromMail();
				String subject = mail.getSubject();

				logger.info(" mail.getFromMail() " + from);
				logger.info(" mail.getSubject() " + subject);

				List<Employee> employees = employeePersonalInformationService
						.findBirthDayEmployees(mail.getCompanyId());

				Properties velocityProperties = new Properties();
				velocityProperties.setProperty("file.resource.loader.path", "/templates");

				for (Employee emp : employees) {
					logger.info("emp.getAddress1().getEmailId() " + emp.getOfficialEmail());
					Company company = companyService.getCompany(emp.getCompany().getCompanyId());
					String path1 = environment.getProperty("application.companyLogoPath");
					String companyLogoPath = path1 + company.getCompanyLogoPath();
					MimeMessage mimeMessage = mailSender.createMimeMessage();
					MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
					if (mail.getCc() != null)
						mimeMessageHelper.setCc(mail.getCc());
					Map<String, Object> model = new HashMap<String, Object>();
					model.put("companyName", company.getCompanyName());
					model.put("companyLogo", companyLogoPath);
					// Template template =
					// velocityEngine.getTemplate("/templates/birthday/birthday_monday.vm");
					logger.info("companyLogoPath -->"+companyLogoPath);
					path = "/templates/marriage-anniversary.vm";
					String text = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, path, "UTF-8", model);
					if (from != null && !from.trim().equals("")) {
						mimeMessageHelper.setFrom(from);
					} else {
						mimeMessageHelper.setFrom(HrmsGlobalConstantUtil.GREETING_MAIL_ID);
					}
					if (subject != null && !subject.trim().equals("")) {
						mimeMessageHelper.setSubject(subject);
					} else {
						mimeMessageHelper.setSubject("Happy Birth Day");
					}
					logger.info("emp.getAddress1().getEmailId() " + emp.getOfficialEmail());
					mimeMessageHelper.setTo(emp.getOfficialEmail());
					mimeMessageHelper.setText(text, true);
					mailSender.send(mimeMessageHelper.getMimeMessage());
					logger.info("mimeMessageHelper.getMimeMessage()" + mimeMessageHelper.getMimeMessage());
				}
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

	// @Scheduled(fixedRate = 2000)
	public void scheduleTaskWithFixedRate() {
		logger.info("Fixed Rate Task :: Execution Time - {}", dateTimeFormatter.format(LocalDateTime.now()));
	}
}
