package com.csipl.hrms.service.employee;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
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
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.csipl.common.model.EmailConfiguration;
import com.csipl.common.model.EmailNotificationMaster;
import com.csipl.hrms.common.constant.StatusMessage;
import com.csipl.hrms.common.util.AppUtils;
import com.csipl.hrms.model.common.User;
import com.csipl.hrms.model.employee.Employee;
import com.csipl.hrms.model.employee.EmployeeLetter;
import com.csipl.hrms.model.employee.EmployeeLettersTransaction;
import com.csipl.hrms.model.employee.Letter;
import com.csipl.hrms.service.authorization.LoginService;
import com.csipl.hrms.service.common.EmailNotificationService;
import com.csipl.hrms.service.common.repository.EmailConfigurationRepository;
import com.csipl.hrms.service.employee.repository.EmployeeLetterApprovalsRepository;
import com.csipl.hrms.service.employee.repository.EmployeeLetterRepository;
import com.csipl.hrms.service.employee.repository.EmployeeLettersTransactionRepository;
import com.csipl.hrms.service.organization.CompanyServiceImpl;
import com.csipl.tms.dto.common.EntityCountDTO;
import com.csipl.tms.dto.common.SearchDTO;

@Service
@Transactional
public class EmployeeLetterServiceImpl implements EmployeeLetterService {
	private static final Logger LOG = LoggerFactory.getLogger(EmployeeLetterServiceImpl.class);
	public static final String ENCODING = "UTF-8";
	@Autowired
	private EmployeeLetterRepository empLetterRepository;

	@Autowired
	private EmployeeLetterApprovalsRepository employeeLetterApprovalsRepository;

	@Autowired
	private EmployeeLettersTransactionRepository employeeLettersTransactionRepository;
	@Autowired
	private EmployeeLetterService empLetterService;

	@Autowired
	private LetterService letterService;

	@Autowired
	private EmployeePersonalInformationService employeePersonalInformationService;

	@Autowired
	private VelocityEngine velocityEngine;
	
	@Autowired
	LoginService loginService;

	@Autowired
	CompanyServiceImpl companyServiceImpl;

	@Autowired
	private Environment environment;
	@Autowired
	EmailConfigurationRepository emailConfugrationRepository;

	@Autowired
	EmailNotificationService emailNotificationService;
	@Override
	public List<EmployeeLetter> findAllEmpLetter(Long companyId, String empStatus, String HRStatus) {
		// TODO Auto-generated method stub
		return empLetterRepository.findAllEmpLetter(companyId, empStatus, HRStatus);
	}

	@Override
	public EmployeeLetter saveLtr(EmployeeLetter ltr) {
		// TODO Auto-generated method stub
		return empLetterRepository.save(ltr);
	}

	@Override
	public List<Object[]> findAllPendingEmployeeDocumentView() {
		// TODO Auto-generated method stub
		return empLetterRepository.findAllPendingEmployeeDocumentView();
	}

	@Override
	public EmployeeLetter findEmployeeLetterById(Long empId, Long letterId) {
		// TODO Auto-generated method stub
		return empLetterRepository.findEmployeeLetterById(empId, letterId);
	}

	@Override
	public List<Object[]> fetchLetterList(Long companyId) {
		// TODO Auto-generated method stub
		return empLetterRepository.findAllEmpLetter(companyId);
	}

	@Override
	public List<Object[]> findPendingLetterList(Long companyId, Long employeeId, SearchDTO searcDto) {
		// TODO Auto-generated method stub
		// return empLetterRepository.findPendingLetterList(companyId, employeeId);

		return employeeLetterApprovalsRepository.findPendingLetterList(companyId, employeeId, searcDto);
	}

	@Override
	public List<Object[]> findNonPendingLetterList(Long companyId, Long employeeId, SearchDTO searcDto) {

		// return empLetterRepository.findNonPendingLetterList(companyId, employeeId);
		return employeeLetterApprovalsRepository.findNonPendingLetterList(companyId, employeeId, searcDto);
	}

	@Override
	@Transactional
	public void updateById(EmployeeLettersTransaction employeeLettersTransaction) {

		employeeLettersTransactionRepository.updateById(employeeLettersTransaction.getEmployeeLetterTransactionId(),
				employeeLettersTransaction.getStatus(), employeeLettersTransaction.getApprovalRemarks(),
				employeeLettersTransaction.getApprovalId(), employeeLettersTransaction.getDateUpdate());

	}

	@Override
	public EmployeeLetter getEmployeeLetterById(Long employeeLetterId) {
		// TODO Auto-generated method stub
		return empLetterRepository.findOne(employeeLetterId);
	}

	@Override
	public EmployeeLetter findEmployeeLetter(Long employeeLetterId, Long employeeId, String status) {
		// TODO Auto-generated method stub
		return empLetterRepository.findEmployeeLetter(employeeLetterId, employeeId);
	}

	@Override
	public EmployeeLetter findEmpLetterByStatus(Long employeeLetterId, Long employeeId) {

		return empLetterRepository.findEmpLetterByStatus(employeeLetterId, employeeId);
	}

	@Override
	public List<Object[]> employeeLetterByEmployeeId(Long employeeId) {
		// TODO Auto-generated method stub
		return empLetterRepository.findEmployeeLetterByEmployeeId(employeeId);
	}

	@Override
	public void updateAfterRealeseLetter(EmployeeLetter employeeLetter) {
		empLetterRepository.updateById(employeeLetter.getEmpLetterId(), employeeLetter.getActiveStatus());

	}

	@Override
	public List<Object[]> findAllEmpLetterActiveStatus(Long companyId, String activeStatus) {
		return empLetterRepository.findAllEmpLetterActiveStatus(companyId, activeStatus);
	}

	@Override
	@Transactional
	public EmployeeLetter generateLetter(Long companyId, Long letterId, Long employeeId) {
		EmployeeLetter oldEmpLetter = empLetterRepository.findEmployeeLetterByStatus(employeeId, letterId);

		LOG.info("EmployeeLetterServiceImpl.generateLetter() start");
		Employee employee = employeePersonalInformationService.findEmployeesById(employeeId);
		EmployeeLetter employeeLetter = new EmployeeLetter();

		employeeLetter.setEmpId(employeeId);

		Letter ltr = letterService.findLetter(letterId);
		StringBuilder sb = new StringBuilder();
		SimpleDateFormat sm = new SimpleDateFormat("dd-MMM-yyyy");
		String currentDT = sm.format(new Date());
		if (ltr != null) {
			sb.append(ltr.getLetterDecription().replace("@Letter_Name@", ltr.getLetterName())
					.replace("@EMPLOYEE_NAME@", employee.getFirstName() + " " + employee.getLastName())
					.replace("@EMPLOYEE_CODE@", employee.getEmployeeCode()).replace("@CURRENT_DATE@", currentDT)
					.replace("@DESIGNATION_NAME@", employee.getDesignation().getDesignationName())
					.replace("@COMPANY_NAME@", employee.getCompany().getCompanyName())
					.replace("@City@",
							employee.getCity().getCityName() + " " + employee.getState().getStateName() + "- "
									+ employee.getAddress2().getPincode())
					.replace("@Address@", employee.getAddress2().getAddressText()));

		}
		if(ltr.getLetterId()!=null){
		employeeLetter.setLetterDecription(sb.toString());
		employeeLetter.setActiveStatus(StatusMessage.ACTIVE_CODE);

		employeeLetter.setLetterId(ltr.getLetterId());
		employeeLetter.setEmpStatus(StatusMessage.PENDING_CODE);
		employeeLetter.setHRStatus(StatusMessage.PENDING_CODE);
		employeeLetter.setActiveStatus(StatusMessage.ACTIVE_CODE);
		employeeLetter.setRealeseStatus(StatusMessage.PENDING_CODE);
		employeeLetter.setDateCreated(new Date());
		employeeLetter.setUserId(employee.getUserId());
		}
		if (oldEmpLetter != null) {
			employeeLetter.setEmpLetterId(oldEmpLetter.getEmpLetterId());
		}
		EmployeeLetter empLetter = empLetterService.saveLtr(employeeLetter);

		return empLetter;

	}

	@Override
	public List<Object[]> findAllEmployeeLetterView(Long employeeId) {

		return empLetterRepository.findAllEmployeeLetterView(employeeId);
	}

	@Override
	public EmployeeLetter findEmployeeLetterByStatus(Long empId, Long letterId) {

		return empLetterRepository.findEmployeeLetterByStatus(empId, letterId);
	}

	@Override
	public EmployeeLetter findEmployeeLetterByEmpLetterId(Long empId, Long letterId, Long empLetterId) {
		// TODO Auto-generated method stub
		return empLetterRepository.findEmployeeLetterByEmpLetterId(empId, letterId, empLetterId);
	}

	@Override
	public void getLetterApprovalNonPendingCount(Long longCompanyId, Long employeeId, EntityCountDTO searchDto) {

		searchDto.setCount(empLetterRepository.getLetterApprovalNonPendingCount(longCompanyId, employeeId));

	}

	@Override
	public List<Object[]> findPendingLetterList(Long companyId, Long employeeId) {

		return empLetterRepository.findPendingLetterList(companyId, employeeId);

	}

	@Override
	public void updateDeclarationStatus(Long empLetterId, String declarationStatus, Long empId) {
		empLetterRepository.updateDeclarationStatus(empLetterId,declarationStatus,empId, new Date());
		
	}

	 
	
	private void resendMail(Long empLetterId, Long letterId, Long employeeId, String tokenObj) {
		 System.out.println("EmployeeLetterServiceImpl.resendMail()");
		ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		String tenantId = null;
		if (attr != null) {
			tenantId = attr.getRequest().getParameter("tenantId"); // find parameter from request
			if (tenantId == null)
				tenantId = attr.getRequest().getHeader("tenantId");
		}
			EmailConfiguration confugration = null;
			confugration = emailConfugrationRepository.findEMail();
			EmailNotificationMaster listEmail = emailNotificationService
					.findEMailListByStatus(StatusMessage.DECLARATION_FOR_LETTER);
			JavaMailSenderImpl mailSender = null;
			mailSender = new JavaMailSenderImpl();
			Properties props = mailSender.getJavaMailProperties();
			if (confugration.getHost().equals(StatusMessage.HOST_NAME_SMTP)) {
				LOG.info("===========Declaration==.getJavaMailSender()===============" + listEmail.toString());
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
				LOG.info("===========Declaration==.getJavaMailSender()================" + listEmail.toString());
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
			MimeMessage mimeMessage = mailSender.createMimeMessage();
			MimeMessageHelper mimeMessageHelper;
			try {
				mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
				Employee emp = employeePersonalInformationService.getEmployeeInfo(employeeId);
				System.out.println("EmployeeLetterServiceImpl.resendMail()"+emp.toString());
				User user =loginService.findUserByUserId(emp.getUserId());
				System.out.println("EmployeeLetterServiceImpl.resendMail()"+user.toString());
//				String emailLink = AppUtils.DECLARATION_URL + emp.getCompanyId() + "&companyId=" + emp.getCompanyId()
//				+ "&tenantId=" + tenantId +"&token="+tokenObj+"&username="+user.getLoginName()+"&empLetterId="+empLetterId+"&letterId="+letterId;
				String emailLink = AppUtils.DECLARATION_URL + empLetterId+ "&companyId=" + 1
				+ "&tenantId=" + tenantId +"&token="+tokenObj+"&username="+user.getLoginName();
				
				String s1 = emp.getFirstName().substring(0, 1).toUpperCase();
				String first = s1 + emp.getFirstName().substring(1);
				String s2 = emp.getLastName().substring(0, 1).toUpperCase();
				String last = s2 + emp.getLastName().substring(1);
				Map<String, Object> model = new HashMap<String, Object>();
				model.put("firstName", first);
				model.put("lastName", last);
				model.put("link", emailLink);
			 

				String text = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine,
						"templates/declaration.vm", "UTF-8", model);
				mimeMessageHelper.setFrom(listEmail.getUserName());
				mimeMessageHelper.setSubject(listEmail.getSubject());
				if (listEmail.getToMail() != null && !listEmail.getToMail().equals("")) {
					String toList = emp.getPersonalEmail()+ "," + listEmail.getToMail();
					String[] to = toList.split(",");
					mimeMessageHelper.setTo(to);
				} else {
					String to = emp.getPersonalEmail();
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
						LOG.info("mail send Successfully");
					} catch (Exception e) {
						LOG.error("error in processing email for declaration");
						e.printStackTrace();
					}
				}
			} catch (MessagingException e) {

				e.printStackTrace();
			}

		}

	@Override
	public EmployeeLetter triggerDeclarationMail(EmployeeLetter letterList, String token) {
		System.out.println("EmployeeLetterServiceImpl.triggerDeclarationMail()");
		resendMail(letterList.getEmpLetterId(),letterList.getLetterId(), letterList.getEmpId(), token) ;
		System.out.println("EmployeeLetterServiceImpl.triggerDeclarationMail() end ");
		return null;
	}

	@Override
	public EmployeeLetter findOneEmpLetter(Long empLetterId) {
		// TODO Auto-generated method stub
		return empLetterRepository.findOne(empLetterId);
	}
	 

}
