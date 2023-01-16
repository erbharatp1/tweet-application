package com.csipl.hrms.org;

import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.web.context.WebApplicationContext;

import com.csipl.common.model.EmailConfiguration;
import com.csipl.hrms.service.common.repository.EmailConfigurationRepository;

@Configuration
public class MailHrmsConfig {
	private static final Logger logger = LoggerFactory.getLogger(MailHrmsConfig.class);
	static final String ENCODING = "UTF-8";

	@Autowired
	EmailConfigurationRepository emailConfugrationRepository;
	
	@Bean
	@Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
	public JavaMailSender getJavaMailSender() {
		EmailConfiguration confugration = null;
		confugration = emailConfugrationRepository.findEMail();
		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
		Properties props = mailSender.getJavaMailProperties();
 
		
		if (confugration.getHost().equals("smtp.gmail.com")) {
			logger.info("=MailHrmsConfig==.getJavaMailSender()==" );
			mailSender.setHost(confugration.getHost());
			mailSender.setPort(confugration.getPort());
			mailSender.setUsername(confugration.getUserName());
			mailSender.setPassword(confugration.getPassword());
			props.put("mail.transport.protocol", confugration.getProtocol());
			props.put("mail.smtp.auth", confugration.getAuth());
			props.put("mail.smtp.ssl.trust", confugration.getSslName());
			props.put("mail.smtp.starttls.enable", confugration.getStarttlsName());
			props.put("mail.mime.charset", ENCODING);

		} else {
			logger.info("==== =MailHrmsConfig==.getJavaMailSender() ===" + confugration.toString());
			mailSender.setHost(confugration.getHost());
			mailSender.setPort(confugration.getPort());
			mailSender.setUsername(confugration.getUserName());
			mailSender.setPassword(confugration.getPassword());
			props.put("mail.transport.protocol", confugration.getProtocol());
			props.put("mail.smtp.auth", confugration.getAuth());
			props.put("mail.smtp.ssl.trust", confugration.getSslName());
			props.put("mail.smtp.starttls.enable", confugration.getStarttlsName());
			props.put("mail.mime.charset", ENCODING);
			props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
			props.put("mail.smtp.socketFactory.fallback", "true");

		} // close email config

		
		
		
		
		
		
		
		
		
		return mailSender;
	} 

}
