package com.csipl.hrms.service.candidate;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.io.FilenameUtils;
import org.apache.velocity.app.VelocityEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.velocity.VelocityEngineUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import com.csipl.common.model.EmailConfiguration;
import com.csipl.common.model.EmailNotificationMaster;
import com.csipl.common.services.notification.NotificationServices;
import com.csipl.hrms.common.constant.StatusMessage;
import com.csipl.hrms.common.util.AppUtils;
import com.csipl.hrms.common.util.CandidateTokenService;
import com.csipl.hrms.common.util.HrmsGlobalConstantUtil;
//import com.csipl.hrms.config.JwtTokenUtil;
import com.csipl.hrms.dto.candidate.CandidateDTO;
import com.csipl.hrms.model.candidate.Candidate;
import com.csipl.hrms.model.candidate.CandidateIdProof;
import com.csipl.hrms.model.common.Company;
import com.csipl.hrms.model.common.User;
import com.csipl.hrms.model.employee.MasterBook;
import com.csipl.hrms.service.authorization.LoginService;
import com.csipl.hrms.service.candidate.repository.CandidateIdAddressRepository;
import com.csipl.hrms.service.candidate.repository.CandidateRepository;
import com.csipl.hrms.service.common.EmailNotificationService;
import com.csipl.hrms.service.common.repository.EmailConfigurationRepository;
import com.csipl.hrms.service.employee.repository.MasterBookRepository;
import com.csipl.hrms.service.organization.CompanyServiceImpl;
import com.csipl.hrms.service.organization.StorageService;

@Transactional
@Service("candidateService")
public class CandidateServiceImpl implements CandidateService {
	private static final Logger logger = LoggerFactory.getLogger(CandidateServiceImpl.class);

	@Autowired
	public CandidateRepository candidateRepository;

	@Autowired
	public NotificationServices notificationServices;

	@Autowired
	private MasterBookRepository masterBookRepository;

	@PersistenceContext(unitName = "mySQL")
	@Autowired
	private EntityManager em;
	@Autowired
	StorageService storageService;

	@Autowired
	private VelocityEngine velocityEngine;

//	@Autowired
//	MailService mailService;
//
//	@Autowired
//	JavaMailSender mailSender;
	
	@Autowired
	LoginService loginService;

	@Autowired
	CompanyServiceImpl companyServiceImpl;

	@Autowired
	private org.springframework.core.env.Environment environment;

	@Autowired
	CandidateTokenService candidateTokenService;
	@Autowired
	EmailConfigurationRepository emailConfugrationRepository;

	@Autowired
	EmailNotificationService emailNotificationService;

	@Autowired
	private CandidateIdAddressRepository candidateIdAddressRepository;

	@Autowired
	CandidateIdAddressService candidateIdAddressService;
	
	 

	@Autowired
	private org.springframework.core.env.Environment env;
	private static final Random random = new Random();
	public static final String ENCODING = "UTF-8";
	private static final String CHARS = "abcdefghijkmnopqrstuvwxyzABCDEFGHJKLMNOPQRSTUVWXYZ234567890!@#$";

	@Transactional
	@SuppressWarnings("unchecked")
	public Candidate save(Candidate candidate,String tokenObj) throws Exception {

		String path = environment.getProperty("application.companyLogoPath");
		Company company = companyServiceImpl.getCompany(candidate.getCompanyId());
		String companyLogoPath = path + company.getCompanyLogoPath();

		Candidate candidate1 = candidateRepository.save(candidate);

		CandidateIdProof candidateIdProof = candidateIdAddressService.getCandidateIdproof(candidate1.getCandidateId());
		Long candidateProofsId = null;

		if (candidateIdProof != null && candidateIdProof.getIdTypeId() != null
				&& candidateIdProof.getIdTypeId().equalsIgnoreCase("AC")) {
			candidateProofsId = candidateIdProof.getCandidateIdProofsId();
		}

		if (candidate1 != null && candidate1.getAdharNumber() != null && candidate1.getAdharNumber() != "null") {
			List<CandidateIdProof> candidateIdProofList = new ArrayList();
			CandidateIdProof candidateIdProofs = new CandidateIdProof();
			candidateIdProofs.setCandidate(candidate1);
			candidateIdProofs.setIdNumber(candidate1.getAdharNumber());
			candidateIdProofs.setIdTypeId("AC");
			if (candidateProofsId != null) {
				candidateIdProofs.setCandidateIdProofsId(candidateProofsId);
			}
			candidateIdProofList.add(candidateIdProofs);

			candidateIdAddressRepository.save(candidateIdProofList);

		}

		String bookCode = "EMPNO";
		MasterBook masterBook = masterBookRepository.findMasterBook(candidate1.getCompanyId(), bookCode);
		String prefix = masterBook.getPrefixBook();
		String add = "0";
		String strCandidateId = String.valueOf(candidate1.getCandidateId());
		String candidateCode = prefix + "C"
				+ Integer.valueOf(String.valueOf(candidate1.getCompanyId()) + strCandidateId);
		if (strCandidateId.length() == 1) {
			strCandidateId = add.concat(strCandidateId);
		}
		/**
		 * add mail config
		 */
		EmailConfiguration confugration = null;
		confugration = emailConfugrationRepository.findEMail();
		EmailNotificationMaster listEmail = emailNotificationService
				.findEMailListByStatus(StatusMessage.INVITE_FOR_ONBOARDING_CODE);
		JavaMailSenderImpl mailSender = null;

		mailSender = new JavaMailSenderImpl();
		Properties props = mailSender.getJavaMailProperties();
		if (confugration.getHost().equals(StatusMessage.HOST_NAME_SMTP)) {
			logger.info("===========invite==.getJavaMailSender()===============" + listEmail.toString());
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
			logger.info("===========invite==.getJavaMailSender()================" + listEmail.toString());
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
		candidate1.setCandidateCode(candidateCode);
		logger.info(" checkapproach in service impl===========================" + candidate.getCheckApproach());
		ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		String tenantId = null;
		if (attr != null) {
			tenantId = attr.getRequest().getParameter("tenantId"); // find parameter from request
			logger.info("CandidateServiceImpl.save()" + tenantId);
			if (tenantId == null)
				tenantId = attr.getRequest().getHeader("tenantId");
			logger.info("CandidateServiceImpl.save()==" + tenantId);
		}
		if (candidate1.getCandidateId() != null && candidate.getCheckApproach() == 1) {
			logger.info("in sede if");
			MimeMessage mimeMessage = mailSender.createMimeMessage();
			MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
			String token = getToken(6);
			logger.info("TOKEN..." + token);
			int candidateToken = candidateTokenService.getCandidateToken(candidateCode);
			logger.info("otp-------------------------" + candidateToken);
			// String password = AppUtils.SHA1(candidateCode + candidateToken);
			int rand_int1 = ThreadLocalRandom.current().nextInt();
			String password = "" + rand_int1;
			String newPass = password.substring(1, 7);
			logger.info("candidate login pass word..........." + newPass);
			// tenantId= "computronics.in";
			candidate.setPassword(newPass);
			User user =loginService.findUserByUserId(candidate.getUserId());
		//	String tokenObj = jwtTokenUtil.generateToken(user);
			String emailLink = AppUtils.url + candidate1.getCandidateId() + "&companyId=" + candidate1.getCompanyId()
					+ "&tenantId=" + tenantId +"&token="+tokenObj+"&username="+user.getLoginName();
			String s1 = candidate.getFirstName().substring(0, 1).toUpperCase();
			String first = s1 + candidate.getFirstName().substring(1);
			String s2 = candidate.getLastName().substring(0, 1).toUpperCase();
			String last = s2 + candidate.getLastName().substring(1);
			Map<String, Object> model = new HashMap<String, Object>();
			model.put("firstName", first);
			model.put("lastName", last);
			model.put("companyName", company.getCompanyName());
			model.put("companyLogoPath", companyLogoPath);
			logger.info(" checkapproach in service impl" + emailLink);
			model.put("link", emailLink);
			model.put("password", newPass);
			String text = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, "templates/invite_onboarding.vm",
					"UTF-8", model);
			mimeMessageHelper.setFrom(listEmail.getUserName());
			mimeMessageHelper.setSubject(listEmail.getSubject());

			if (listEmail.getCc() != null && !listEmail.getCc().equals("")) {
				String ccList = listEmail.getCc();
				String[] cc = ccList.split(",");
				mimeMessageHelper.setCc(cc);
			}
			if (listEmail.getToMail() != null && !listEmail.getToMail().equals("")) {
				String toList = candidate1.getEmailId() + "," + listEmail.getToMail();
				String[] to = toList.split(",");
				mimeMessageHelper.setTo(to);
			} else {
				String to1 = candidate1.getEmailId();
				mimeMessageHelper.setTo(to1);
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
					e.printStackTrace();
					logger.info("mail send failed");
				}
			} else
				logger.info("mail send fail");
			candidate1.setCheckApproach(candidate.getCheckApproach());
		}

		Candidate newCandidate = candidateRepository.save(candidate1);
		newCandidate.setCheckApproach(candidate.getCheckApproach());
		logger.info("newCandidate.getCheckApproach()>>>> " + newCandidate.getCheckApproach());
		return newCandidate;
	}

	public static String getToken(int length) {
		StringBuilder token = new StringBuilder(length);
		for (int i = 0; i < length; i++) {
			token.append(CHARS.charAt(random.nextInt(CHARS.length())));
		}
		return token.toString();
	}

	@Override
	public Candidate findCandidate(Long candidateId) {
		Candidate candidate = candidateRepository.findOne(candidateId);
		CandidateDTO candidateDto = new CandidateDTO();
		return candidate;
	}

	@Override
	public CandidateDTO findCandidatebyId(Long candidateId) {
		Candidate candidate = candidateRepository.findOne(candidateId);
		CandidateDTO candidateDto = new CandidateDTO();
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT shiftFName FROM `TMSShift` WHERE shiftId= :shiftId");

		String query = sb.toString();
		logger.info("Query=----" + query);
		Query nativeQuery = em.createNativeQuery(query);
		nativeQuery.setParameter("shiftId", candidate.getShiftId());
		final List<Object[]> resultList = nativeQuery.getResultList();
		Object[] result = resultList.get(0);

		return candidateDto;
	}

	@Override
	public Candidate findCandidateByCode(String candidatecode) {

		return candidateRepository.findCandidateByCode(candidatecode);
	}

	@Override
	public void saveCandidateImage(Long candidateId, MultipartFile file) {
		String fileName = "";

		fileName = "Candidate" + candidateId.toString();
		String extension = FilenameUtils.getExtension(file.getOriginalFilename());
		fileName = fileName + "." + extension;
		logger.info(" File with extension " + fileName);

//		String path = File.separator + "images" + File.separator + "employeeImages";
		String path = storageService.createFilePath(HrmsGlobalConstantUtil.EMPLOYEE_IMAGES);
		String dbPath = path + File.separator + fileName;
		storageService.store(file, path, fileName);
		candidateRepository.saveCandidateImage(dbPath, candidateId);

	}

	@Override

	public void changeCandidateStatus(String candidateStatus, Long candidateId, String reason) {
		candidateRepository.changeCandidateStatus(candidateStatus, candidateId, reason);
	}

	public List<Object[]> progressBar(Long progressBar) {
		return candidateRepository.getProcessBar(progressBar);

	}

	@Override
	public List<Object[]> tabSubmitValidation(Long candidateId) {
		return candidateRepository.tabSubmitValidation(candidateId);
	}

	@Override
	public void resendMail(Candidate candidate, String comment,String tokenObj) {
		// Mail mail = new Mail();
		// Notification notification = new Notification();
		logger.info("candidateId===============================" + candidate.getCandidateId());
		logger.info("CheckApproach===============================" + candidate.getCheckApproach());
		ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		String tenantId = null;
		if (attr != null) {
			tenantId = attr.getRequest().getParameter("tenantId"); // find parameter from request
			if (tenantId == null)
				tenantId = attr.getRequest().getHeader("tenantId");
		}
		if (candidate.getCandidateId() != null && !candidate.getCandidateStatus().equals("RE")) {
			logger.info("in sede if");

			String path = environment.getProperty("application.companyLogoPath");
			Company company = companyServiceImpl.getCompany(candidate.getCompanyId());
			String companyLogoPath = path + company.getCompanyLogoPath();
			EmailConfiguration confugration = null;
			confugration = emailConfugrationRepository.findEMail();
			EmailNotificationMaster listEmail = emailNotificationService
					.findEMailListByStatus(StatusMessage.INVITE_FOR_ONBOARDING_CODE);
			JavaMailSenderImpl mailSender = null;

			mailSender = new JavaMailSenderImpl();
			Properties props = mailSender.getJavaMailProperties();
			if (confugration.getHost().equals(StatusMessage.HOST_NAME_SMTP)) {
				logger.info("===========Resend==.getJavaMailSender()===============" + listEmail.toString());
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
				logger.info("===========Resend==.getJavaMailSender()================" + listEmail.toString());
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
			logger.info("COmpanyLogopath==========================>" + companyLogoPath);
			MimeMessage mimeMessage = mailSender.createMimeMessage();
			MimeMessageHelper mimeMessageHelper;
			try {
				String bookCode = "EMPNO";
				MasterBook masterBook = masterBookRepository.findMasterBook(candidate.getCompanyId(), bookCode);
				String prefix = masterBook.getPrefixBook();
				String add = "0";
				String strCandidateId = String.valueOf(candidate.getCandidateId());
				String candidateCode = prefix + "C"
						+ Integer.valueOf(String.valueOf(candidate.getCompanyId()) + strCandidateId);
				if (strCandidateId.length() == 1) {
					strCandidateId = add.concat(strCandidateId);
				}
				candidate.setCandidateCode(candidateCode);
				mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
				String token = getToken(6);
				logger.info("TOKEN..." + token);
				int candidateToken = candidateTokenService.getCandidateToken(candidateCode);
				logger.info("otp-------------------------" + candidateToken);
				// String password =AppUtils.SHA1(candidateCode +candidateToken);
				int rand_int1 = ThreadLocalRandom.current().nextInt();
				String password = "" + rand_int1;
				String newPass = password.substring(1, 7);
				logger.info("candidate login pass word..........." + newPass);
				candidate.setPassword(newPass);
				User user =loginService.findUserByUserId(candidate.getUserId());
				//	String tokenObj = jwtTokenUtil.generateToken(user);
					String emailLink = AppUtils.url + candidate.getCandidateId() + "&companyId=" + candidate.getCompanyId()
							+ "&tenantId=" + tenantId +"&token="+tokenObj+"&username="+user.getLoginName();
				
				String s1 = candidate.getFirstName().substring(0, 1).toUpperCase();
				String first = s1 + candidate.getFirstName().substring(1);

				String s2 = candidate.getLastName().substring(0, 1).toUpperCase();
				String last = s2 + candidate.getLastName().substring(1);
				Map<String, Object> model = new HashMap<String, Object>();
				model.put("firstName", first);
				model.put("lastName", last);
				model.put("companyName", company.getCompanyName());
				model.put("companyLogoPath", companyLogoPath);
				model.put("link", emailLink);
				model.put("comment", comment);
				model.put("password", newPass);
				model.put("companyLogoPath", companyLogoPath);

				String text = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine,
						"templates/onboarding_details.vm", "UTF-8", model);
				mimeMessageHelper.setFrom(listEmail.getUserName());
				mimeMessageHelper.setSubject(listEmail.getSubject());
				if (listEmail.getToMail() != null && !listEmail.getToMail().equals("")) {
					String toList = candidate.getEmailId() + "," + listEmail.getToMail();
					String[] to = toList.split(",");
					mimeMessageHelper.setTo(to);
				} else {
					String to = candidate.getEmailId();
					mimeMessageHelper.setTo(to);
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
				if (listEmail.getActiveStatus().equals(StatusMessage.ACTIVE_CODE)) {
					try {
						mailSender.send(mimeMessageHelper.getMimeMessage());
						logger.info("mail send Successfully");
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			} catch (MessagingException e) {

				e.printStackTrace();
			}

		}
	}

	@Override
	public String findShiftName(Long shiftId) {
		List<Object[]> objList = candidateRepository.findShiftName(shiftId);
		String shiftName = "";
		for (Object[] objects : objList) {
			shiftName = objects[0] != null ? (String) objects[0] : "";
		}
		return shiftName;
	}

	@Override
	public String findWeekOffPatternName(Long patternId) {
		List<Object[]> objList = candidateRepository.findPatternName(patternId);
		String patternName = "";
		for (Object[] objects : objList) {
			patternName = objects[0] != null ? (String) objects[0] : "";
		}
		return patternName;
	}

	@Override
	public void changeStatus(String candidateStatus, Long candidateId) {
		candidateRepository.changeStatus(candidateStatus, candidateId);

	}

}
