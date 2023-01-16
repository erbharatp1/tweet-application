package com.csipl.hrms.org.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;

import org.apache.velocity.app.VelocityEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.ui.velocity.VelocityEngineUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.csipl.common.model.EmailConfiguration;
import com.csipl.common.model.EmailNotificationMaster;
import com.csipl.hrms.common.constant.StatusMessage;
import com.csipl.hrms.common.util.AppUtils;
import com.csipl.hrms.common.util.ErrorHandling;
import com.csipl.hrms.common.util.OTPService;
import com.csipl.hrms.dto.organisation.UserDTO;
import com.csipl.hrms.model.common.Address;
import com.csipl.hrms.model.common.Company;
import com.csipl.hrms.model.common.User;
import com.csipl.hrms.model.employee.Employee;
import com.csipl.hrms.service.adaptor.UserAdaptor;
import com.csipl.hrms.service.authorization.LoginService;
import com.csipl.hrms.service.common.EmailNotificationService;
import com.csipl.hrms.service.common.repository.EmailConfigurationRepository;
import com.csipl.hrms.service.employee.EmployeePersonalInformationService;
import com.csipl.hrms.service.organization.CompanyService;
import com.csipl.hrms.service.users.UserService;

@RequestMapping("/forgetPassword")
@RestController
public class GenerateOtpController {
	private static final Logger logger = LoggerFactory.getLogger(GenerateOtpController.class);
	public static final String ENCODING = "UTF-8";
	boolean status = false;
	UserAdaptor usersAdaptor = new UserAdaptor();
	Address address = null;
	Employee employee = null;// test
	int otp = 1234;
	@Autowired
	EmailConfigurationRepository emailConfugrationRepository;

	@Autowired
	EmailNotificationService emailNotificationService;

	@Autowired
	EmployeePersonalInformationService employeeService;

	@Autowired
	UserService userService;

	@Autowired
	LoginService loginService;

	@Autowired
	private VelocityEngine velocityEngine;

	@Autowired
	JavaMailSender mailSender;

	@Autowired
	OTPService otpService;

	@Autowired
	private Environment environment;

	@Autowired
	CompanyService companyService;

	@RequestMapping(path = "/generateOtp", method = RequestMethod.POST)
	public @ResponseBody UserDTO generateOtp(@RequestBody UserDTO userDto, HttpServletRequest req) throws ErrorHandling
		 {
		logger.info("generateOtp is calling : UserDTO " + userDto + "" + userDto.getLoginName());

		UserDTO userDtoDb = new UserDTO();

		User userBean = loginService.findUserByUserName(userDto.getLoginName());
		logger.info("userBean is : " + userBean);
		if (userBean != null) {
		 
			// logger.info("employee" + employee);
			// address = employee.getAddress2();
			char[] buff = null;
			char[] buffEmail = null;
			String mobile = null;
			if (userBean.getLoginName().equals("Administrator")) {
				buff = userBean.getUserMobileNo().toCharArray();
				mobile = userBean.getUserMobileNo();
				buffEmail = userBean.getEmailOfUser().toCharArray();
			} else {
				employee = employeeService.findEmployees(userBean.getNameOfUser(),
						userBean.getCompany().getCompanyId());
				buff = employee.getContactNo().toCharArray();
				mobile = employee.getContactNo();
				buffEmail = employee.getPersonalEmail().toCharArray();
			}
			StringBuilder sbMobile = new StringBuilder();
			// buff = userBean.getUserMobileNo().toCharArray();
			userBean.setUserMobileNo(mobile);
			sbMobile.append(buff, 0, 2).append("*****");
			sbMobile.append(buff, 7, buff.length - 7);

			StringBuilder sbEmail = new StringBuilder();
			// char[] buffEmail = userBean.getEmailOfUser().toCharArray();
			sbEmail.append(buffEmail, 0, 3).append("******");
			String strEmail = new String(buffEmail);
			String str1[] = strEmail.split("\\@");
			sbEmail.append(str1[0].substring(str1[0].length() - 2));
			sbEmail.append("@" + str1[1]);

			int otp = otpService.getOtp(userDto.getLoginName());

			if (otp > 0) {
				Integer value = otpService.getOtp(userDto.getLoginName());
			 
				if (value > 0) {
					userDtoDb = sendOtp(value, sbEmail, sbMobile, userBean);
					userDtoDb.setLoginName(userBean.getLoginName());
					userDtoDb.setNameOfUser(userBean.getNameOfUser());
				}
			} else {
				if (mobile != null) {
					Integer value = otpService.generateOtp(userDto.getLoginName());
					logger.info("new OTP :================ " + value);
					if (value > 0) {
						userDtoDb = sendOtp(value, sbEmail, sbMobile, userBean);
						userDtoDb.setNameOfUser(userBean.getNameOfUser());
						userDtoDb.setLoginName(userBean.getLoginName());

					}

				}
			}

		} else {
			logger.info("Please enter correct employee code");
			throw new ErrorHandling("Error! Please enter correct employee code");

		}
		return userDtoDb;
	}

	private UserDTO sendOtp(Integer value, StringBuilder sbEmail, StringBuilder sbMobile, User userBean) {

		UserDTO u = new UserDTO();
		new AppUtils().sendOtpBySms("Hi Your Otp Number is :- " + value, userBean.getUserMobileNo());

		// u.setNameOfUser(employee.getEmployeeCode());
		u.setEmailOfUser(sbEmail.toString());
		u.setMobile(sbMobile.toString());
		if (userBean.getLoginName().equals("Administrator") || userBean.getLoginName().equals("Super Administrator")) {
			triggerEmailAdmin(userBean.getEmailOfUser(), userBean, value);
		} else {
			triggerEmail(userBean.getEmailOfUser(), userBean, employee, value);
			u.setEmployeeId(employee.getEmployeeId());
			u.setCompanyId(employee.getCompany().getCompanyId());
			System.out.println("u.getCompanyId()>>> " + u.getCompanyId());
		}
		return u;
	}

	/**
	 * 
	 * @param emailOfUser
	 * @param userBean
	 * @param value
	 */
	private void triggerEmailAdmin(String emailOfUser, User userBean, Integer value) {
		// mail config
		try {
			EmailConfiguration confugration = null;
			confugration = emailConfugrationRepository.findEMail();
			EmailNotificationMaster listEmail = emailNotificationService.findEMailListByStatus(StatusMessage.OTP_CODE);
			JavaMailSenderImpl mailSender = null;
			String toList = null;
			// String[] to = toList.split(",");
			mailSender = new JavaMailSenderImpl();
			Properties props = mailSender.getJavaMailProperties();
			if (confugration.getHost().equals(StatusMessage.HOST_NAME_SMTP)) {
				logger.info("===========Otp==.getJavaMailSender()===============" + listEmail.toString());
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
				logger.info("===========Otp==.getJavaMailSender()================" + listEmail.toString());
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
			logger.info("To email is :" + userBean.getUserEmail());
			Company company = companyService.getCompany(userBean.getCompany().getCompanyId());
			String path = environment.getProperty("application.companyLogoPath");
			String companyLogoPath = path + company.getCompanyLogoPath();

			logger.info("To companyLogoPath is :" + companyLogoPath);
			MimeMessage mimeMessage = mailSender.createMimeMessage();
			MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);

			mimeMessageHelper.setSubject(listEmail.getSubject());

			Map<String, Object> model = new HashMap<String, Object>();
			model.put("firstName", userBean.getLoginName());
			model.put("companyName", company.getCompanyName());
			model.put("otpCode", value);
			model.put("CompanyEmail", company.getEmailId());
			model.put("CompanyMobile", company.getMobile());
			 			model.put("companyLogoPath", companyLogoPath);
			String text = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, "templates/forget_otp.vm",
					"UTF-8", model);
			mimeMessageHelper.setText(text, true);

			mimeMessageHelper.setFrom(listEmail.getUserName());
			if (listEmail.getToMail() != null && !listEmail.getToMail().equals("")) {
				toList = listEmail.getToMail() + "," + userBean.getUserEmail();
				String[] to1 = toList.split(",");
				mimeMessageHelper.setTo(to1);
			} else {
				toList = userBean.getUserEmail();
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

			if (listEmail.getActiveStatus().equals(StatusMessage.ACTIVE_CODE)) {
				try {
					mailSender.send(mimeMessageHelper.getMimeMessage());
					logger.info("mail send Successfully");
				} catch (Exception e) {
					logger.info("mail send failed");
					e.printStackTrace();
				}
			} else

				logger.info("mail send disable");
		}

		catch (Exception e) {
			e.printStackTrace();
		}

	}

	@RequestMapping(path = "/verifyOtp", method = RequestMethod.POST)
	public @ResponseBody UserDTO varifyOtp(@RequestBody UserDTO userDto, HttpServletRequest req) throws ErrorHandling {
	 

		String username = userDto.getLoginName();
		logger.info("session otp value: " + username + "" + otpService.getOtp(username));
		String mobile = null;
		if (otpService.getOtp(username) == (Integer.parseInt(userDto.getOtp()))) {
			otpService.clearOTP(username);
			// User user = loginRepository.findUserByUserName(username);
			User user = loginService.findUserByUserName(username);
			if (user.getLoginName().equals("Administrator")) {
				mobile = user.getUserMobileNo();
			} else {
				employee = employeeService.findEmployees(user.getNameOfUser(), user.getCompany().getCompanyId());
				mobile = employee.getAddress2().getMobile();
			}
			UserDTO u = new UserDTO();
			u.setEmailOfUser(user.getEmailOfUser());
			u.setNameOfUser(user.getNameOfUser());
			u.setLoginName(user.getLoginName());
			u.setMobile(mobile);
			return u;
		}
		else {
			throw new ErrorHandling("Please enter correct OTP");
		}
	}

	@RequestMapping(method = RequestMethod.POST)
	public @ResponseBody Boolean forgetPassword(@RequestBody UserDTO userDto, HttpServletRequest req) {
		logger.info("forgetPassword is calling : UserDTO " + userDto);
		String userName = userDto.getLoginName();
		String userPassword = userDto.getUserPassword();
	 
		User userBean = loginService.findUserByUserName(userName);
		userBean.setUserPassword(AppUtils.SHA1(userPassword));
	 
		userBean = userService.save(userBean);
		if (userBean.getUserId() > 0) {
			return true;
		}
		return false;
	}

	/**
	 * Method Performed email sending logic
	 */
	private void triggerEmail(String email, User userBean, Employee employee, Integer otp) {

		// mail config
		try {
			EmailConfiguration confugration = null;
			confugration = emailConfugrationRepository.findEMail();
			EmailNotificationMaster listEmail = emailNotificationService.findEMailListByStatus(StatusMessage.OTP_CODE);
			JavaMailSenderImpl mailSender = null;
			String toList = null;
			// String[] to = toList.split(",");
			mailSender = new JavaMailSenderImpl();
			Properties props = mailSender.getJavaMailProperties();
			if (confugration.getHost().equals(StatusMessage.HOST_NAME_SMTP)) {
				logger.info("===========Otp==.getJavaMailSender()===============" + listEmail.toString());
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
				logger.info("===========Otp==.getJavaMailSender()================" + listEmail.toString());
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
			logger.info("To email is :" + email);
			Company company = companyService.getCompany(userBean.getCompany().getCompanyId());
			String path = environment.getProperty("application.companyLogoPath");
			String companyLogoPath = path + company.getCompanyLogoPath();
			String role = userService.getUserRole(userBean.getUserId());
			logger.info("To companyLogoPath is :" + companyLogoPath);
			MimeMessage mimeMessage = mailSender.createMimeMessage();
			MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
			Map<String, Object> model = new HashMap<String, Object>();
 
			model.put("firstName", employee.getFirstName());
			model.put("companyName", company.getCompanyName());
			model.put("otpCode", otp);
			model.put("CompanyEmail", company.getEmailId());
			model.put("CompanyMobile", company.getMobile());
			model.put("EmployeeCode", employee.getEmployeeCode());
			model.put("companyLogoPath", companyLogoPath);
			String text = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, "templates/forget_otp.vm",
					"UTF-8", model);
			mimeMessageHelper.setText(text, true);
			// }
			mimeMessageHelper.setSubject(listEmail.getSubject());
			mimeMessageHelper.setFrom(listEmail.getUserName());
			if (listEmail.getToMail() != null && !listEmail.getToMail().equals("")) {
				toList = listEmail.getToMail() ;  
				
				if(email != null && !email.equals("")) {
					toList= toList+"," + email;
				}
				if(employee.getPersonalEmail()  != null && !employee.getPersonalEmail().equals("")) {
					toList= toList+"," + employee.getPersonalEmail();
				}
				String[] to1 = toList.split(",");
				mimeMessageHelper.setTo(to1);
			} else {
				//toList = email;
				if(email != null && !email.equals("")) {
					toList= toList+"," + email;
				}
				if(employee.getPersonalEmail()  != null && !employee.getPersonalEmail().equals("")) {
					toList= toList+"," + employee.getPersonalEmail();
				}
				String[] to1 = toList.split(",");
				mimeMessageHelper.setTo(to1);
			//	mimeMessageHelper.setTo(toList);
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

			if (listEmail.getActiveStatus().equals(StatusMessage.ACTIVE_CODE)) {
				try {
					mailSender.send(mimeMessageHelper.getMimeMessage());
					logger.info("mail send Successfully");
				} catch (Exception e) {
					logger.info("mail send failed");
					e.printStackTrace();
				}
			} else

				logger.info("mail send disable");
		}
		 
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**private String createOPTMailMessage(Integer otp, Company company) {
		StringBuilder sb = new StringBuilder();

		sb.append(" Dear  Administrator" + " \n ");
		sb.append(
				" 	This is a system generated mail that is being sent out to you with regard to your account at Fabhr.in/hrms. \n ");
		sb.append(" 	Please use  " + otp + "  as the one time password for secure login in your account.  \n ");
		sb.append(" 	This password is valid for 30 minutes.   \n \n ");
		sb.append(" 	For any further queries, please feel free to write us at " + company.getEmailId()
				+ " or call us" + company.getMobile() + ". \n  \n ");
		sb.append(" Regards, \n" + "	Team " + company.getCompanyName() + "\r\n" + " \n  ");

		return sb.toString();
	}
	*/
}
