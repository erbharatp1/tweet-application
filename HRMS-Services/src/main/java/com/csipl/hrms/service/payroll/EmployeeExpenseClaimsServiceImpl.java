package com.csipl.hrms.service.payroll;

import java.io.File;
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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.velocity.VelocityEngineUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import com.csipl.common.model.EmailConfiguration;
import com.csipl.common.model.EmailNotificationMaster;
import com.csipl.hrms.common.constant.StatusMessage;
import com.csipl.hrms.common.util.HrmsGlobalConstantUtil;
import com.csipl.hrms.model.common.User;
import com.csipl.hrms.model.employee.Employee;
import com.csipl.hrms.model.employee.EmployeeExpenseClaim;
import com.csipl.hrms.service.authorization.LoginService;
import com.csipl.hrms.service.common.EmailNotificationService;
import com.csipl.hrms.service.common.repository.EmailConfigurationRepository;
import com.csipl.hrms.service.employee.EmployeePersonalInformationService;
import com.csipl.hrms.service.organization.CompanyServiceImpl;
import com.csipl.hrms.service.organization.StorageService;
import com.csipl.hrms.service.payroll.repository.EmployeeExpenseClaimPaginationRepository;
import com.csipl.hrms.service.payroll.repository.EmployeeExpenseClaimRepository;
import com.csipl.tms.dto.common.SearchDTO;

@Service("employeeExpenseClaimsService")
@Transactional
public class EmployeeExpenseClaimsServiceImpl implements EmployeeExpenseClaimsService {
	private static final Logger LOG = LoggerFactory.getLogger(EmployeeExpenseClaimsServiceImpl.class);
	public static final String ENCODING = "UTF-8";
	@Autowired
	private StorageService storageService;
	@Autowired
	private EmployeeExpenseClaimRepository employeeExpenseClaimRepository;
	
	@Autowired
	private EmployeeExpenseClaimPaginationRepository employeeExpenseClaimPaginationRepository;

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
	public EmployeeExpenseClaim save(EmployeeExpenseClaim company, MultipartFile file, boolean fileFlag) {
		if(fileFlag == false && company.getCompanyId() !=null) {
			EmployeeExpenseClaim company2 = employeeExpenseClaimRepository.findOne(company.getCompanyId());
			company.setFilePath(company2.getFilePath());
			company = employeeExpenseClaimRepository.save(company);
		}
		EmployeeExpenseClaim company1 = employeeExpenseClaimRepository.save(company);
		String fileName = "";
		if (fileFlag) {
			fileName = file.getOriginalFilename();
		 
			String path = storageService.createFilePath(HrmsGlobalConstantUtil.COMPANY_IMAGES);
			String dbPath = path + File.separator + fileName;
			storageService.store(file, path, fileName);
			company1.setFilePath(dbPath);
		}
		return employeeExpenseClaimRepository.save(company1);
		
	}

	@Override
	public List<EmployeeExpenseClaim> findExpenseList(Long companyId) {
		 
		return employeeExpenseClaimRepository.findExpenseList(companyId);
	}

	@Override
	public EmployeeExpenseClaim findById(Long employeeExpeneseClaimId) {
	 
		return employeeExpenseClaimRepository.findOne(employeeExpeneseClaimId);
	}

	//np
	@Override
	public List<Object[]> getExpensesClaimPendingApprovals(Long companyId, SearchDTO searcDto) {
		
		return employeeExpenseClaimPaginationRepository.findExpensesClaimPendingApprovals(companyId, searcDto);
	}
	
	@Override
	public List<Object[]> getExpensesClaimNonPendingApprovals(Long companyId, SearchDTO searcDto) {
		
		return employeeExpenseClaimPaginationRepository.findExpensesClaimNonPendingApprovals(companyId, searcDto);
	}

	@Override
	public List<Object[]> getExpenseClaimSummary(Long employeeId) {
		
		return employeeExpenseClaimRepository.findExpenseClaimSummary(employeeId);
	}

	
	@Override
	public void updateByStatus(Long[] employeeExpeneseClaimIds) {
		Long count = 0l;
		count = employeeExpenseClaimRepository.checkMaxBatchId();
		count++;
		for (Long empExpeneseClaimId : employeeExpeneseClaimIds) {
			try {
				if (empExpeneseClaimId != null) {
					String status = StatusMessage.PENDING_CODE;
					employeeExpenseClaimRepository.updateByStatus(empExpeneseClaimId, status, count);
					LOG.info(" .updateByStatus() secussfully" + empExpeneseClaimId);
					String toReporting = "";
					Long toReportingId = 0L;
					EmployeeExpenseClaim findById = employeeExpenseClaimRepository.findOne(empExpeneseClaimId);
					List<Object[]> reportingTo = employeePersonalInformationService
							.getEmpReportingToEmail(findById.getEmployeId());
				//	if (reportingTo.size() > 0) {
						for (Object obj[] : reportingTo) {
							if (obj[2] != null) {
								toReportingId = obj[0] != null ? Long.parseLong(obj[0].toString()) : null;
								toReporting = obj[2] != null ? (String) obj[2] : null;
								resendMail(findById.getEmployeId(), toReporting);
							}
						}
				//	}
				}
			} catch (Exception e) {
				LOG.error(".updateByStatus()" + e.getMessage());
			}
		}

	}
	

	private void resendMail(Long employeeId, String toReporting) {
		  System.out.println("EmployeeExpenseClaimsServiceImpl.resendMail()");
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
					.findEMailListByStatus(StatusMessage.LETTER_CODE);
			JavaMailSenderImpl mailSender = null;
			mailSender = new JavaMailSenderImpl();
			Properties props = mailSender.getJavaMailProperties();
			if (confugration.getHost().equals(StatusMessage.HOST_NAME_SMTP)) {
				LOG.info("EmployeeExpenseClaimsServiceImpl.resendMail()");
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
				LOG.info("EmployeeExpenseClaimsServiceImpl.resendMail()");
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
				String s1 = emp.getFirstName().substring(0, 1).toUpperCase();
				String first = s1 + emp.getFirstName().substring(1);
				String s2 = emp.getLastName().substring(0, 1).toUpperCase();
				String last = s2 + emp.getLastName().substring(1);
				Map<String, Object> model = new HashMap<String, Object>();
				model.put("firstName", first);
				model.put("lastName", last);
			 
				String text = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine,
						"templates/expense_claim.vm", "UTF-8", model);
				mimeMessageHelper.setFrom(listEmail.getUserName());
				mimeMessageHelper.setSubject("Regarding Expense Claim ");
				if (listEmail.getToMail() != null && !listEmail.getToMail().equals("")) {
					String toList = toReporting+ "," + listEmail.getToMail();
					String[] to = toList.split(",");
					mimeMessageHelper.setTo(to);
				} else {
					String to = toReporting;
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
						LOG.error("error in processing email for Expense Claims");
						e.printStackTrace();
					}
				}
			} catch (MessagingException e) {
				LOG.error("error in processing email for Expense Claims");
				e.printStackTrace();
			}

		}


}
