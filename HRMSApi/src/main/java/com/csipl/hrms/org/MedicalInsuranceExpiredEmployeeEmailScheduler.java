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
import com.csipl.hrms.dto.employee.EmployeeDTO;
import com.csipl.hrms.service.common.EmailNotificationService;
import com.csipl.hrms.service.common.repository.EmailConfigurationRepository;
import com.csipl.hrms.service.employee.EmployeePersonalInformationService;
import com.csipl.hrms.service.organization.CompanyService;
import com.csipl.tms.deviceinfo.repository.DeviceLogsRepository;

@Component
public class MedicalInsuranceExpiredEmployeeEmailScheduler {

	private static final Logger logger = LoggerFactory.getLogger(MedicalInsuranceExpiredEmployeeEmailScheduler.class);
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
	
	@Autowired
	Environment env;

	public void scheduleTaskWithFixedDelay() {
	}

	public void scheduleTaskWithInitialDelay() {
	}

	Object tenant = "computronics.in";

	@Scheduled(cron = "0 50 12 * * ?")
	public void scheduleTaskWithCronExpression() {

		Map<Object, Object> mapObj =  MasterService.getDataSourceHashMap(env);
		mapObj.forEach((k, v) -> {
			this.tenant = k;

			System.out.println("Item : " + k + " Count : " + v);
			TenantContext.setTenantId(this.tenant != null ? this.tenant.toString() : "computronics.in");
			Long companyId = 1L;

			try {

				EmailConfiguration confugration = null;
				confugration = emailConfugrationRepository.findEMail();
				EmailNotificationMaster listEmail = emailNotificationService.findEMailListByStatus(StatusMessage.MEDICAL_INSURANCE_EXPIRED_CODE);
				JavaMailSenderImpl mailSender = null;

				mailSender = new JavaMailSenderImpl();
				Properties props = mailSender.getJavaMailProperties();
				if (confugration.getHost().equals(StatusMessage.HOST_NAME_SMTP)) {
					logger.info(
							"======MedicalInsuranceExpiredEmployeeEmailScheduler==.getJavaMailSender()==============="+ listEmail.toString());
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
					logger.info("======MedicalInsuranceExpiredEmployeeEmailScheduler==.getJavaMailSender()===="	+ listEmail.toString());
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
				companyId = listEmail.getCompanyId();
			 
				List<EmployeeDTO> employeeListMedicalInsurance = employeePersonalInformationService
						.getEmployeeListMedicalInsurance(companyId);

				MimeMessage mimeMessage = mailSender.createMimeMessage();
				MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);

				Map<String, Object> model = new HashMap<String, Object>();
				 
				model.put("employeeListMedicalInsurance", employeeListMedicalInsurance);
				String text = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine,
						"templates/medical_insurance_expired.vm", "UTF-8", model);
				mimeMessageHelper.setSubject(listEmail.getSubject());
				mimeMessageHelper.setFrom(listEmail.getUserName());

				if (listEmail.getToMail() != null && !listEmail.getToMail().equals("")) {
					String toList = listEmail.getToMail();
					String[] to = toList.split(",");
					mimeMessageHelper.setTo(to);
				} 
//				else {
//					String toList = "noreply@gmail.com";
//					mimeMessageHelper.setTo(toList);
//				}
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
				if (listEmail.getActiveStatus().equals(StatusMessage.ACTIVE_CODE) && employeeListMedicalInsurance.size()>0) {
					try {
						mailSender.send(mimeMessageHelper.getMimeMessage());
						logger.info(" MedicalInsuranceExpiredEmployeeEmailScheduler mail send Successfully");
					} catch (Exception e) {
						e.printStackTrace();
						logger.info(" MedicalInsuranceExpiredEmployeeEmailScheduler mail send fail");
					}
				} else
					logger.info(" MedicalInsuranceExpiredEmployeeEmailScheduler mail send failed Size--"+employeeListMedicalInsurance.size()+"listEmail Status"+listEmail.getActiveStatus());


			} catch (Exception ex) {
				ex.printStackTrace();
			}
		});
	}

	public void scheduleTaskWithFixedRate() {
		logger.info("Fixed Rate Task :: Execution Time - {}", dateTimeFormatter.format(LocalDateTime.now()));
	}

}
