package com.csipl.tms.org;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
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
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.ui.velocity.VelocityEngineUtils;
import org.springframework.web.client.RestTemplate;

import com.csipl.common.model.EmailConfiguration;
import com.csipl.common.model.EmailNotificationMaster;
import com.csipl.common.model.Mail;
import com.csipl.hrms.common.constant.StatusMessage;
import com.csipl.hrms.common.enums.MailEnum;
import com.csipl.hrms.common.util.DateUtils;
import com.csipl.hrms.common.util.HrmsGlobalConstantUtil;
import com.csipl.hrms.model.common.Company;
import com.csipl.hrms.model.employee.Employee;
import com.csipl.hrms.service.common.EmailNotificationService;
import com.csipl.hrms.service.common.MailService;
import com.csipl.hrms.service.common.repository.EmailConfigurationRepository;
import com.csipl.hrms.service.employee.EmployeePersonalInformationService;
import com.csipl.hrms.service.organization.CompanyService;
import com.csipl.tms.deviceinfo.repository.DeviceLogsRepository;
import com.csipl.hrms.dto.employee.EmployeeDTO;

@Component
public class AttendanceProcessCountScheduler {

	private static final Logger logger = LoggerFactory.getLogger(AttendanceProcessCountScheduler.class);
	private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
	public static final String ENCODING = "UTF-8";
	@Autowired
	EmployeePersonalInformationService employeePersonalInformationService;

	@Autowired
	private VelocityEngine velocityEngine;

	@Autowired
	CompanyService companyService;

	@Autowired
	DeviceLogsRepository deviceLogsRepository;
	@Autowired
	EmailConfigurationRepository emailConfugrationRepository;

	@Autowired
	EmailNotificationService emailNotificationService;

	public void scheduleTaskWithFixedDelay() {
	}

	public void scheduleTaskWithInitialDelay() {
	}

	@Scheduled(cron = "0 35 12 * * ?")
	public void scheduleTaskWithCronExpression() {
		Long companyId = 1L;

		try {

			EmailConfiguration confugration = null;
			confugration = emailConfugrationRepository.findEMail();
			EmailNotificationMaster listEmail = emailNotificationService.findEMailListByStatus("LC");
			JavaMailSenderImpl mailSender = null;

			mailSender = new JavaMailSenderImpl();
			Properties props = mailSender.getJavaMailProperties();
			if (confugration.getHost().equals("smtp.yandex.com")) {
				System.out.println("===========Attendance==.getJavaMailSender()===============" + listEmail.toString());
				mailSender.setHost(confugration.getHost());
				mailSender.setPort(confugration.getPort());
				mailSender.setUsername(listEmail.getUserName());
				mailSender.setPassword(listEmail.getPassword());
				props.put("mail.transport.protocol", confugration.getProtocol());
				props.put("mail.smtp.auth", confugration.getAuth());
				props.put("mail.smtp.ssl.trust", confugration.getSslName());
				props.put("mail.smtp.starttls.enable", confugration.getStarttlsName());
				props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
				props.put("mail.mime.charset", ENCODING);

			} else {
				System.out
						.println("===========Attendance==.getJavaMailSender()================" + listEmail.toString());
				mailSender.setHost(confugration.getHost());
				mailSender.setPort(confugration.getPort());
				mailSender.setUsername(listEmail.getUserName());
				mailSender.setPassword(listEmail.getPassword());
				props.put("mail.transport.protocol", confugration.getProtocol());
				props.put("mail.smtp.auth", confugration.getAuth());
				props.put("mail.smtp.ssl.trust", confugration.getSslName());
				props.put("mail.smtp.starttls.enable", confugration.getStarttlsName());
				props.put("mail.mime.charset", ENCODING);

				companyId = listEmail.getCompanyId();
				SimpleDateFormat sm = new SimpleDateFormat("dd-MMM-yyyy");
				String date = sm.format(new Date());
				Company company = companyService.getCompany(companyId);
				List<Object[]> lateComesCount = deviceLogsRepository.getAllLateComersList(companyId);
				List<Object[]> absentCount = deviceLogsRepository.getAllAbsentList(companyId);
				List<Object[]> presentCount = deviceLogsRepository.getAllPresentList(companyId);
				MimeMessage mimeMessage = mailSender.createMimeMessage();
				MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);

				Map<String, Object> model = new HashMap<String, Object>();
				model.put("companyName", company.getCompanyName());
				model.put("companyLogo", company.getCompanyLogoPath());
				model.put("lateComes", lateComesCount.size());
				model.put("absentCount", absentCount.size());
				model.put("presentCount", presentCount.size());
				model.put("date", date);
				String text = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine,
						"templates/attendenceTemplate.vm", "UTF-8", model);
				mimeMessageHelper.setSubject(listEmail.getSubject());
				mimeMessageHelper.setFrom(listEmail.getUserName());

				if (listEmail.getToMail() != null && !listEmail.getToMail().equals("")) {
					String toList = listEmail.getToMail();
					String[] to = toList.split(",");
					mimeMessageHelper.setTo(to);
				} else {
					String toList = "noreply@gmail.com";
					mimeMessageHelper.setTo(toList);
				}
				if (listEmail.getCc() != null && !listEmail.getCc().equals("")) {
					String ccList = listEmail.getCc();
					String[] cc = ccList.split(",");
					mimeMessageHelper.setCc(cc);
				}
				if (listEmail.getBcc() != null && !listEmail.getBcc().equals("")) {
					String bccList = listEmail.getBcc();
					String[] bcc = bccList.split(",");
					mimeMessageHelper.setBcc(bcc);
				}
				mimeMessageHelper.setText(text, true);
				if (listEmail.getActiveStatus().equals(StatusMessage.ACTIVE_CODE) && presentCount.size() > 0) {
					mailSender.send(mimeMessageHelper.getMimeMessage());
					logger.info("mail send Successfully");
				}

			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

	public void scheduleTaskWithFixedRate() {
		logger.info("Fixed Rate Task :: Execution Time - {}", dateTimeFormatter.format(LocalDateTime.now()));
	}

}
