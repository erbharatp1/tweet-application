package com.csipl.hrms.service.employee;

import java.io.File;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.transaction.Transactional;

import org.apache.commons.io.FilenameUtils;
import org.apache.velocity.app.VelocityEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.velocity.VelocityEngineUtils;
import org.springframework.web.multipart.MultipartFile;

import com.csipl.common.model.EmailConfiguration;
import com.csipl.common.model.EmailNotificationMaster;
import com.csipl.hrms.common.constant.StatusMessage;
import com.csipl.hrms.common.enums.ActiveStatusEnum;
import com.csipl.hrms.common.exception.PayRollProcessException;
import com.csipl.hrms.common.util.AppUtils;
import com.csipl.hrms.common.util.ErrorHandling;
import com.csipl.hrms.common.util.HrmsGlobalConstantUtil;
import com.csipl.hrms.dto.employee.EmpHierarchyDTO;
import com.csipl.hrms.dto.employee.EmployeeDTO;
import com.csipl.hrms.model.authoriztion.RoleMaster;
import com.csipl.hrms.model.authoriztion.UserRole;
import com.csipl.hrms.model.common.Company;
import com.csipl.hrms.model.common.MandatoryInfoCheck;
import com.csipl.hrms.model.common.User;
import com.csipl.hrms.model.employee.Employee;
import com.csipl.hrms.model.employee.EmployeeBank;
import com.csipl.hrms.model.employee.MasterBook;
import com.csipl.hrms.service.authorization.repository.RoleMasterRepository;
import com.csipl.hrms.service.authorization.repository.UserRoleRepository;
import com.csipl.hrms.service.common.EmailNotificationService;
import com.csipl.hrms.service.common.repository.EmailConfigurationRepository;
import com.csipl.hrms.service.employee.repository.BankDetailsRepository;
import com.csipl.hrms.service.employee.repository.EmployeePersonalInformationRepository;
import com.csipl.hrms.service.employee.repository.EmployeeSkillRepository;
import com.csipl.hrms.service.employee.repository.MasterBookRepository;
import com.csipl.hrms.service.employee.repository.UserRepository;
import com.csipl.hrms.service.organization.CompanyService;
import com.csipl.hrms.service.organization.StorageService;
import com.csipl.hrms.service.organization.repository.MandatoryInfoCheckRepository;
import com.csipl.hrms.service.payroll.repository.ReportPayOutRepository;
import com.csipl.hrms.service.util.ConverterUtil;

@Transactional
@Service("employeeService")
public class EmployeePersonalInformationServiceImpl implements EmployeePersonalInformationService {

	/**
	 * Logger declaration for knowing the flow of execution for debugging
	 */
	private final Logger logger = LoggerFactory.getLogger(EmployeePersonalInformationServiceImpl.class);
	public static final String ENCODING = "UTF-8";
	@Autowired
	StorageService storageService;

	@Autowired
	CompanyService companyService;

	@Autowired
	EmployeePersonalInformationService employeePersonalInformationService;

	@Autowired
	private EmployeePersonalInformationRepository employeePersonalInformationRepository;

	@Autowired
	private EmployeeSkillRepository employeeSkillRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RoleMasterRepository roleMasterRepository;

	@Autowired
	private UserRoleRepository userRoleRepository;

	@Autowired
	private MasterBookRepository masterBookRepository;

	@Autowired
	private MandatoryInfoCheckRepository mandatoryInfoCheckRepository;

	@Autowired
	private BankDetailsRepository bankDetailsRepository;

	@Autowired
	private ReportPayOutRepository reportPayOutRepository;

	@Autowired
	private VelocityEngine velocityEngine;

//	@Autowired
//	MailService mailService;
//
//	@Autowired
//	JavaMailSender mailSender;
	@Autowired
	EmailConfigurationRepository emailConfugrationRepository;

	@Autowired
	EmailNotificationService emailNotificationService;

	@Autowired
	private org.springframework.core.env.Environment env;

	/**
	 * Save OR update employee object with file into Database
	 * 
	 * @throws ErrorHandling
	 */
	@Override
	public Employee save(Employee employee, MultipartFile file, boolean fileFlag) throws ErrorHandling {

		logger.info("Old Employee Updation case employeeId=>>>" + employee.getEmployeeId() + "Employee CODE=>> "
				+ employee.getEmployeeCode());
		User newUser = new User();
		Employee newEmployee = new Employee();
		long adhar = 0l;

		if (employee.getAdharNumber() != null)
			adhar = aadharCheck(employee.getAdharNumber(), employee.getEmployeeId());

		if (adhar < 1l) {

			if (employee.getEmployeeId() != null && employee.isNewSkillValues()) {
				employeeSkillRepository.deleteEmployeeSkill(employee.getEmployeeId());
			}

			List<EmployeeBank> employeeBanks = bankDetailsRepository.findAllBankDetails(employee.getEmployeeId());
			for (EmployeeBank employeeBank : employeeBanks) {
				employeeBank.setActiveStatus(employee.getActiveStatus());
				employeeBank.setEmployee(employee);
			}
			employee.setEmployeeBanks(employeeBanks);

			if (employee.getAddress1() != null) {
				logger.info("User Table Updation Case emailId=>>" + employee.getOfficialEmail());
				newUser = userRepository.findUser(employee.getEmployeeCode());
				newUser.setAddress(employee.getAddress1());
				newUser.setEmailOfUser(employee.getOfficialEmail());
				newUser = userRepository.save(newUser);

			}

			newEmployee = employeePersonalInformationRepository.save(employee);

			String fileName = "";
			if (fileFlag) {
				fileName = "Employee_" + newEmployee.getEmployeeId().toString();
				String extension = FilenameUtils.getExtension(file.getOriginalFilename());
				fileName = fileName + "." + extension;
				logger.info(" File with extension " + fileName);
//				String path = File.separator + "images" + File.separator + "employeeImages";
				String path = storageService.createFilePath(HrmsGlobalConstantUtil.EMPLOYEE_IMAGES);
				String dbPath = path + File.separator + fileName;
				storageService.store(file, path, fileName);
				newEmployee.setEmployeeLogoPath(dbPath);
				employeePersonalInformationRepository.save(newEmployee);
			}

			return newEmployee;

		}

		else {
			throw new ErrorHandling(" Aadhar number already exists ");
		}
	}

	/**
	 * To save first time employees to Database based on company
	 */

	@Override
	public Employee saveEmployee(Employee employee) throws ErrorHandling {
		// TODO Auto-generated method stub
		long adhar = 0l;

		if (employee.getAdharNumber() != null)
			adhar = aadharCheck(employee.getAdharNumber(), employee.getEmployeeId());

		User newUser = new User();
		Employee newEmployee = null;
		String bookCode = "EMPNO";

		if (adhar < 1l) {
			logger.info("employee to save $$$$$" + employee);

			Company companyDomain = new Company();
			companyDomain = companyService.getCompany(employee.getCompany().getCompanyId());

			MasterBook masterBook = masterBookRepository.findMasterBook(employee.getCompany().getCompanyId(), bookCode);

			if (employee.getEmployeeCodeStatus().equals("AU")) {

				List<Employee> employeeList = employeePersonalInformationService
						.getAllActiveEmployee(employee.getCompany().getCompanyId());
				List<String> empCodes = new ArrayList<>();

				for (Employee emp : employeeList) {
					empCodes.add(emp.getEmployeeCode());
				}

				checkAutometicEmpCode(employee.getCompany().getCompanyId(), empCodes, employee);
//				BigDecimal lastNumberValue;
//				lastNumberValue = masterBook.getLastNo();
//				long longValue;
//				longValue = lastNumberValue.longValue() + 1;
//				BigDecimal newDecimalValue = new BigDecimal(longValue);
//				Boolean ans = empCodes.contains(masterBook.getPrefixBook() + newDecimalValue);

			}
			newEmployee = employeePersonalInformationRepository.save(employee);

			MandatoryInfoCheck mandatoryInfoCheck = new MandatoryInfoCheck();
			mandatoryInfoCheck.setUserId(employee.getUserId());
			mandatoryInfoCheck.setUserIdUpdate(employee.getUserIdUpdate());
			mandatoryInfoCheck.setDateCreated(employee.getDateCreated());
			mandatoryInfoCheck.setDateUpdate(employee.getDateUpdate());
			mandatoryInfoCheck.setEmployee(newEmployee);
			if (employee.getAdharNumber() != null)
				mandatoryInfoCheck.setUi("YES");
			mandatoryInfoCheckRepository.save(mandatoryInfoCheck);

			/*
			 * String fileName = ""; if (fileFlag) { fileName = "Employee_" +
			 * newEmployee.getEmployeeId().toString(); String extension =
			 * FilenameUtils.getExtension(file.getOriginalFilename()); fileName = fileName +
			 * "." + extension; logger.info(" File with extension " + fileName);
			 * 
			 * String path = File.separator + "images" + File.separator + "employeeImages";
			 * String dbPath = path + File.separator + fileName; storageService.store(file,
			 * path, fileName); newEmployee.setEmployeeLogoPath(dbPath); }
			 */

			newUser.setCompany(newEmployee.getCompany());
			newUser.setGroupg(newEmployee.getGroupg());
			newUser.setUserPassword(
					AppUtils.SHA1(newEmployee.getEmployeeCode() + HrmsGlobalConstantUtil.USER_PASSWORD));
			if (employee.getEmployeeCodeStatus() == "AU") {
				BigDecimal lastNumberValue;
				lastNumberValue = masterBook.getLastNo();
				long longValue;
				longValue = lastNumberValue.longValue() + 1;
				BigDecimal newDecimalValue = new BigDecimal(longValue);
				newUser.setNameOfUser(masterBook.getPrefixBook() + newDecimalValue);
			} else {
				newUser.setNameOfUser(employee.getEmployeeCode());
			}

			newUser.setLoginName(newUser.getNameOfUser() + "-" + companyDomain.getDomainName());
			newUser.setSuserId(newEmployee.getUserId());
			newUser.setEmailOfUser(employee.getPersonalEmail());
			newUser.setUserAttempts(0l);
			newUser.setActiveStatus("AC");
			User newUser1 = userRepository.save(newUser);
			UserRole userRole = new UserRole();
			logger.info("RoleMaster------------------------------------" + employee.getRole());
			RoleMaster roleMaster = roleMasterRepository.getRoleMasterId(employee.getRole());
			logger.info("RoleMaster rollid------------------------------------" + roleMaster.getRoleId());
			userRole.setUser(newUser1);
			userRole.setRoleMaster(roleMaster);
			userRole.setSUserId(HrmsGlobalConstantUtil.SUPER_USER_ID);
			// userRole.setrol
			logger.info("user role object=>  User Id" + userRole.getUser().getUserId());

			userRoleRepository.save(userRole);

		}

		else {
			throw new ErrorHandling(" Aadhar number already exists ");
		}
		return newEmployee;
	}

	Boolean checkAutometicEmpCode(Long companyId, List<String> empCodes, Employee employee) {
		String bookCode = "EMPNO";
		MasterBook masterBook = masterBookRepository.findMasterBook(companyId, bookCode);
		BigDecimal lastNumberValue;
		lastNumberValue = masterBook.getLastNo();
		long longValue;
		longValue = lastNumberValue.longValue() + 1;
		BigDecimal newDecimalValue = new BigDecimal(longValue);
		Boolean ans = empCodes.contains(masterBook.getPrefixBook() + newDecimalValue);

		if (ans == true) {
			masterBook.setLastNo(newDecimalValue);
			masterBook.setCompany(employee.getCompany());
			masterBookRepository.save(masterBook);
			checkAutometicEmpCode(companyId, empCodes, employee);

		} else {
			employee.setEmployeeCode(masterBook.getPrefixBook() + newDecimalValue);

			masterBook.setLastNo(newDecimalValue);
			masterBook.setCompany(employee.getCompany());
			masterBookRepository.save(masterBook);
		}

		return ans;
	}

	/**
	 * To get List of employees from Database based on company
	 */
	@Override
	public List<Object[]> findAllPersonalInformationDetails(Long companyId) {
		String status = "AC";
		return employeePersonalInformationRepository.findAllEmployees(companyId, status);
	}

	/**
	 * To get employee object from Database based on employeeCode
	 */
	@Override
	public Employee findEmployees(String employeeCode, Long companyId) {
		return employeePersonalInformationRepository.findEmployees(employeeCode, companyId);
	}

	/**
	 * To get employee object from Database based on employeeId (primary key)
	 */
	@Override
	public Employee findEmployeesById(long employeeId) {
		return employeePersonalInformationRepository.findOne(employeeId);
	}

	/**
	 * Save OR update employees List into Database
	 */
	@Override
	public void saveAll(List<Employee> employeeList) {
		logger.info("saveAll method is calling ");
		employeePersonalInformationRepository.save(employeeList);
	}

	/**
	 * To get List of employees from Database based on companyId and departmentId
	 */
	@Override
	public List<Employee> findAllEmpByDeptId(Long companyId, Long departmentId) {
		return employeePersonalInformationRepository.findAllEmpByDeptId(companyId, departmentId);
	}

	/**
	 * To get List of Deactivated Or Currently not working employees from Database
	 * based on companyId
	 */
	@Override
	public List<Employee> getAllDeactivateEmployees(Long companyId) {
		return employeePersonalInformationRepository.findAllDeactivateEmployees(companyId);
	}

	/**
	 * To get List of employees from Database based on companyId
	 */
	@Override
	public List<Employee> fetchEmployee(Long companyId) {

		List<Employee> employees = new ArrayList<Employee>();
		List<Object[]> employeeList = employeePersonalInformationRepository.fetchEmployee(companyId);

		for (Object[] empObj : employeeList) {
			Employee emp = new Employee();

			long empId = ConverterUtil.getLong(empObj[0]);
			emp.setEmployeeId(empId);
			emp.setEmployeeCode(empObj[1].toString());
			emp.setFirstName(empObj[2].toString());
			emp.setLastName(empObj[3].toString());
			emp.setBiometricId(empObj[4] != null ? (empObj[4].toString()).trim() : null);
			employees.add(emp);
		}
		return employees;
	}

	/**
	 * To get List of employees from Database based on companyId for Employee
	 * Searching
	 */
	public List<Object[]> searchEmployee(Long companyId) {
		List<Object[]> employeeList = employeePersonalInformationRepository.searchEmployee(companyId);
		return employeeList;
	}

	/**
	 * To get employee from Database based on employeeId
	 */
	@Override
	public Employee getEmployeeInfo(Long employeeId) {
		return employeePersonalInformationRepository.findOne(employeeId);
	}

	/**
	 * To get count value from Database for Checking Aadhar number duplicate or not
	 */
	@Override
	public Long aadharCheck(String adharNumber, Long employeeId) {
		if (employeeId == null)

			return employeePersonalInformationRepository.aadharCheck(adharNumber,
					ActiveStatusEnum.ActiveStatus.getActiveStatus());
		else
			return employeePersonalInformationRepository.aadharCheck(adharNumber,
					ActiveStatusEnum.ActiveStatus.getActiveStatus(), employeeId);
	}

	@Override
	public List<Employee> findBirthDayEmployees(Long companyId) {
		return employeePersonalInformationRepository.findBirthDayEmployees(companyId);
	}

	@Override
	public List<Employee> findAnniversaryEmployees(Long companyId) {
		return employeePersonalInformationRepository.findAnniversaryEmployees(companyId);
	}

	@Override
	public List<Employee> findJoiningAnniversaryEmployees(Long companyId) {
		return employeePersonalInformationRepository.findJoiningAnniversaryEmployees(companyId);
	}

	@Override
	public Long checkPayRollForEmployeeJoining(Long companyId, String processMonth, Long departmentId) {
		logger.info("processmonth>>" + processMonth + "departmentId" + departmentId);
		return reportPayOutRepository.checkPayRollForEmployeeJoining(companyId, processMonth, departmentId);
	}

	@Override
	public List<EmpHierarchyDTO> orgHierarchyList(Long employeeId) {
		Employee employee = employeePersonalInformationRepository.findOne(employeeId);
		List<Object[]> employeeListObj = employeePersonalInformationRepository.orgHierarchyList(employeeId);
		List<EmpHierarchyDTO> empHierarchyDtoList = new ArrayList<EmpHierarchyDTO>();
		List<EmpHierarchyDTO> empHierarchyDtoListParent = new ArrayList<EmpHierarchyDTO>();
		for (Object[] empObj : employeeListObj) {
			EmpHierarchyDTO empHierarchyDtoObj = new EmpHierarchyDTO();
			Long empId = empObj[0] != null ? Long.parseLong(empObj[0].toString()) : null;
			String firstName = empObj[1] != null ? (String) empObj[1] : null;
			String lastName = empObj[2] != null ? (String) empObj[2] : null;
			String employeeCode = empObj[3] != null ? (String) empObj[3] : null;
			String employeeLogoPath = empObj[4] != null ? (String) empObj[4] : null;
			Long managerId = empObj[5] != null ? Long.parseLong(empObj[5].toString()) : null;
			String departmentName = empObj[8] != null ? (String) empObj[8] : null;
			empHierarchyDtoObj.setEmployeeId(empId);
			empHierarchyDtoObj.setText(firstName + " " + lastName + " (" + departmentName + ")");
			empHierarchyDtoObj.setUrl(employeeLogoPath);
			empHierarchyDtoList.add(empHierarchyDtoObj);
		}
		EmpHierarchyDTO empHierarchyDto = new EmpHierarchyDTO();

		empHierarchyDto.setEmployeeId(employeeId);
		empHierarchyDto.setItems(empHierarchyDtoList);
		empHierarchyDto.setEmployeeId(employeeId);
		empHierarchyDto.setText(employee.getFirstName() + " " + employee.getLastName() + " ("
				+ employee.getDepartment().getDepartmentName() + ")");
		empHierarchyDtoListParent.add(empHierarchyDto);

		return empHierarchyDtoListParent;
	}

	@Override
	public void saveCandidateImage(Long employeeId, MultipartFile file) {

		String fileName = "";
		fileName = "Employee_" + employeeId.toString();
		String extension = FilenameUtils.getExtension(file.getOriginalFilename());
		fileName = fileName + "." + extension;
		logger.info(" File with extension " + fileName);

//		String path = File.separator + "images" + File.separator + "employeeImages";
		String path = storageService.createFilePath(HrmsGlobalConstantUtil.EMPLOYEE_IMAGES);
		String dbPath = path + File.separator + fileName;
		storageService.store(file, path, fileName);
		employeePersonalInformationRepository.saveCandidateImage(dbPath, employeeId);

	}

	public List<Object[]> getOnBoardProgessBar(Long progressBar) {
		return employeePersonalInformationRepository.getOnBoardProgessBar(progressBar);

	}

	@Override
	public void onboardMail(Employee employee) {
		String path1 = env.getProperty("application.companyLogoPath");
		EmailConfiguration confugration = null;
		confugration = emailConfugrationRepository.findEMail();
		EmailNotificationMaster listEmail = emailNotificationService
				.findEMailListByStatus(StatusMessage.WELCOME_FOR_ONBOARDING_CODE);
		JavaMailSenderImpl mailSender = null;
		mailSender = new JavaMailSenderImpl();
		Properties props = mailSender.getJavaMailProperties();
		if (confugration.getHost().equals(StatusMessage.HOST_NAME_SMTP)) {
			logger.info("===========Welcome==.getJavaMailSender()===============" + listEmail.toString());
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
			logger.info("===========Welcome==.getJavaMailSender()================" + listEmail.toString());
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
		Company company = companyService.getCompany(employee.getCompany().getCompanyId());
		String companyLogoPath = path1 + company.getCompanyLogoPath();
		
		String mobileAppLink = "https://play.google.com/store/apps/details?id=fabhr.computronics.com.hrms";
		
		logger.info("---------------in Onboard Mail Service");
		MimeMessage mimeMessage = mailSender.createMimeMessage();
		MimeMessageHelper mimeMessageHelper;
		try {
			mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
			String emailLink = AppUtils.url + employee.getEmployeeId();
			logger.info("UserId=======" + employee.getUserId());
			User user = userRepository.findUser(employee.getEmployeeCode());
			String path = env.getProperty("application.url");
			Map<String, Object> model = new HashMap<String, Object>();
			model.put("firstName", employee.getFirstName());
			model.put("lastName", employee.getLastName());
			model.put("loginName", user.getLoginName());
			model.put("companyName", company.getCompanyName());
			model.put("companyLogoPath", companyLogoPath);
			model.put("mobileAppLink", mobileAppLink);
			model.put("passward", employee.getEmployeeCode() + "@1234");
			model.put("url", path);
			 
			String text = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, "templates/onboard_user.vm",
					"UTF-8", model);
			mimeMessageHelper.setFrom(listEmail.getUserName());
			mimeMessageHelper.setSubject(listEmail.getSubject());

			if (listEmail.getToMail() != null && !listEmail.getToMail().equals("")) {
				String toList = listEmail.getToMail() + "," + employee.getAddress1().getEmailId();
				String[] to = toList.split(",");
				mimeMessageHelper.setTo(to);
			} else {
				String toList = employee.getAddress1().getEmailId();
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
			if (listEmail.getActiveStatus().equals("AC")) {
				try {
					mailSender.send(mimeMessageHelper.getMimeMessage());
					logger.info("mail send Successfully");
				} catch (Exception ex) {
					ex.getMessage();
				}
			} else
				logger.info("mail send fail");
		} catch (MessagingException e) {

			e.printStackTrace();
		}
	}

	@Override
	public List<Object[]> getEmpReportingToEmail(Long employeeId) {
		return employeePersonalInformationRepository.getEmpReportingToEmail(employeeId);

	}

	@Override
	public List<Employee> getEmployeeInPayroll(Long companyID, String payrollMonth) {
		// TODO Auto-generated method stub
		return employeePersonalInformationRepository.getEmployeeInPayroll(companyID, payrollMonth);
	}

	@Override
	public List<Object[]> getEmpShiftName(Long employeeId) {
		// TODO Auto-generated method stub
		return employeePersonalInformationRepository.getEmpShiftName(employeeId);
	}

	@Override
	public List<Object[]> findAllPersonalInformationDetailsForSeparationLOV(Long companyId) {
		String status = "AC";
		return employeePersonalInformationRepository.findAllPersonalInformationDetailsForSeparationLOV(companyId,
				status);
	}

	@Override
	public List<Employee> getAllActiveEmployee(Long companyId) {
		// TODO Auto-generated method stub
		return employeePersonalInformationRepository.getAllActiveEmployee(companyId);
	}

	@Override
	public Long adharCheck(String adharNumber) throws PayRollProcessException {
		String status = "AC";
		Long count = employeePersonalInformationRepository.aadharCheck(adharNumber, status);
		logger.info("count is ---->" + count);
		if (count > 0l) {
			throw new PayRollProcessException("Aadhar Number is already exist");
		} else {
			return count;
		}

	}

	@Override
	public List<Object[]> findAllEmployeeNotInReportpayOut(Long companyId, String processMonth) {
		// TODO Auto-generated method stub
		return employeePersonalInformationRepository.findAllEmployeesNotInReport(companyId, processMonth);
	}

	@Override
	public int changeEmployeeStatus(Long employeeId) {
		// TODO Auto-generated method stub
		return employeePersonalInformationRepository.changeEmployeeStatus(employeeId);
	}

	@Override
	public List<Object[]> getTeamHirarchy(Long companyId, Long employeeId) {
		// TODO Auto-generated method stub
		return employeePersonalInformationRepository.getTeamHirarchy(companyId, employeeId);
	}

	@Override

	public int employeeCodeCheck(String employeeCode) throws PayRollProcessException {
		int count = employeePersonalInformationRepository.employeeCodeCheck(employeeCode);
		logger.info("count is ---->" + count);
		if (count > 0) {
			throw new PayRollProcessException("Employee code is already exist");
		} else {
			return count;
		}
	}

	public List<Object[]> getEmployeeLifeCycleData(Long companyId, Long employeeId, String status) {

		return employeePersonalInformationRepository.getEmployeeLifeCycleData(companyId, employeeId, status);

	}

	@Override
	public List<Employee> fetchEmployeeWithDepartment(Long companyId, Long deptId) {

		List<Employee> employees = new ArrayList<Employee>();
		List<Object[]> employeeList = employeePersonalInformationRepository.fetchEmployeeDepartmentWise(companyId,
				deptId);

		for (Object[] empObj : employeeList) {
			Employee emp = new Employee();

			long empId = ConverterUtil.getLong(empObj[0]);
			emp.setEmployeeId(empId);
			emp.setEmployeeCode(empObj[1].toString());
			emp.setFirstName(empObj[2].toString());
			emp.setLastName(empObj[3].toString());
			emp.setBiometricId(empObj[4] != null ? (empObj[4].toString()).trim() : null);
			Date joiningDate = empObj[5] != null ? ConverterUtil.getDate(empObj[05].toString()) : null;
			emp.setDateOfJoining(joiningDate);
			employees.add(emp);
		}
		return employees;
	}

	@Override
	public List<Object[]> findAllEmployeeNotInReport(Long companyId, String processMonth) {
		// TODO Auto-generated method stub
		return employeePersonalInformationRepository.findAllEmployeeNotInReport(companyId, processMonth);
	}

	@Override
	public List<Employee> fetchAllEmployee(Long companyId) {
		List<Employee> employees = new ArrayList<Employee>();
		List<Object[]> employeeList = employeePersonalInformationRepository.findAllEmployeeByStatus(companyId);

		for (Object[] empObj : employeeList) {
			Employee emp = new Employee();
			long empId = ConverterUtil.getLong(empObj[0]);
			emp.setEmployeeId(empId);
			emp.setEmployeeCode(empObj[1].toString());
			emp.setFirstName(empObj[2].toString());
			emp.setLastName(empObj[3].toString());
			employees.add(emp);
		}
		return employees;
	}

	@Override
	public List<EmployeeDTO> getEmployeeListPrabationPeriod(Long companyId) {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String currentDate = dateFormat.format(new Date());
		List<Object[]> deviceLogsInfoDTO = employeePersonalInformationRepository
				.getEmployeeListPrabationPeriod(companyId);
		List<EmployeeDTO> employeeDTOs = new ArrayList<EmployeeDTO>();

		for (Object[] objects : deviceLogsInfoDTO) {
			EmployeeDTO dto = new EmployeeDTO();

			String employeeName = objects[0] != null ? (String) objects[0] : null;
			String employeeCode = objects[1] != null ? (String) objects[1] : null;
			String departmentName = objects[2] != null ? (String) objects[2] : null;
			Date dateOfJoining = objects[3] != null ? (Date) objects[3] : null;
			Date probationDate = objects[4] != null ? (Date) objects[4] : null;

			SimpleDateFormat sm = new SimpleDateFormat(StatusMessage.DATE_FORMAT);
			String dateOfJoiningNew = sm.format(dateOfJoining);
			String probationDateNew = sm.format(probationDate);

			dto.setFirstName(employeeName);
			dto.setEmployeeCode(employeeCode);
			dto.setDepartmentName(departmentName);
			dto.setDateOfJoiningNew(dateOfJoiningNew);
			dto.setProbationDateNew(probationDateNew);
			employeeDTOs.add(dto);
		}

		return employeeDTOs;
	}

	@Override
	public List<EmployeeDTO> getEmployeeListAccidentalInsurance(Long companyId) {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String currentDate = dateFormat.format(new Date());
		List<Object[]> deviceLogsInfoDTO = employeePersonalInformationRepository
				.getEmployeeListAccidentalInsurance(companyId, currentDate);
		List<EmployeeDTO> employeeDTOs = new ArrayList<EmployeeDTO>();

		for (Object[] objects : deviceLogsInfoDTO) {
			EmployeeDTO dto = new EmployeeDTO();

			String employeeName = objects[0] != null ? (String) objects[0] : null;
			String employeeCode = objects[1] != null ? (String) objects[1] : null;
			String departmentName = objects[2] != null ? (String) objects[2] : null;
			Date datefrom = objects[3] != null ? (Date) objects[3] : null;
			Date dateTo = objects[4] != null ? (Date) objects[4] : null;
			String policyNo = objects[5] != null ? (String) objects[5] : null;
			SimpleDateFormat sm = new SimpleDateFormat(StatusMessage.DATE_FORMAT);
			String dateFromNew = sm.format(datefrom);
			String dateToNew = sm.format(dateTo);

			dto.setFirstName(employeeName);
			dto.setEmployeeCode(employeeCode);
			dto.setDepartmentName(departmentName);
			dto.setDuration(dateFromNew + "  to <br> " + dateToNew);
			dto.setPolicyNo(policyNo);
			employeeDTOs.add(dto);
		}

		return employeeDTOs;
	}

	@Override
	public List<EmployeeDTO> getEmployeeListMedicalInsurance(Long companyId) {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String currentDate = dateFormat.format(new Date());
		List<Object[]> deviceLogsInfoDTO = employeePersonalInformationRepository
				.getEmployeeListMedicalInsurance(companyId, currentDate);
		List<EmployeeDTO> employeeDTOs = new ArrayList<EmployeeDTO>();

		for (Object[] objects : deviceLogsInfoDTO) {
			EmployeeDTO dto = new EmployeeDTO();

			String employeeName = objects[0] != null ? (String) objects[0] : null;
			String employeeCode = objects[1] != null ? (String) objects[1] : null;
			String departmentName = objects[2] != null ? (String) objects[2] : null;
			Date datefrom = objects[3] != null ? (Date) objects[3] : null;
			Date dateTo = objects[4] != null ? (Date) objects[4] : null;
			String policyNo = objects[5] != null ? (String) objects[5] : null;
			SimpleDateFormat sm = new SimpleDateFormat(StatusMessage.DATE_FORMAT);
			String dateFromNew = sm.format(datefrom);
			String dateToNew = sm.format(dateTo);

			dto.setFirstName(employeeName);
			dto.setEmployeeCode(employeeCode);
			dto.setDepartmentName(departmentName);
			dto.setDuration(dateFromNew + " to <br> " + dateToNew);
			dto.setPolicyNo(policyNo);
			employeeDTOs.add(dto);
		}

		return employeeDTOs;
	}

	@Override
	public Employee fetchEmployeeByEmployeeId(Long employeeId) {
		Employee employeeDetails = employeePersonalInformationRepository.fetchEmployeeByEmployeeId(employeeId);
//		Employee emp = new Employee();
//		String biometricId= employeeDetails.getBiometricId();
//		emp.setBiometricId(biometricId);

		return employeeDetails;
	}

	@Override
	public List<EmployeeDTO> getEmployeeListPrabationAboutPeriod(Long companyId) {

		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String currentDate = dateFormat.format(new Date());
		List<Object[]> deviceLogsInfoDTO = employeePersonalInformationRepository
				.getEmployeeListPrabationAboutPeriod(companyId);
		List<EmployeeDTO> employeeDTOs = new ArrayList<EmployeeDTO>();

		for (Object[] objects : deviceLogsInfoDTO) {
			EmployeeDTO dto = new EmployeeDTO();

			String employeeName = objects[0] != null ? (String) objects[0] : null;
			String employeeCode = objects[1] != null ? (String) objects[1] : null;
			String departmentName = objects[2] != null ? (String) objects[2] : null;
			Date dateOfJoining = objects[3] != null ? (Date) objects[3] : null;
			Date probationDate = objects[4] != null ? (Date) objects[4] : null;

			SimpleDateFormat sm = new SimpleDateFormat(StatusMessage.DATE_FORMAT);
			String dateOfJoiningNew = sm.format(dateOfJoining);
			String probationDateNew = sm.format(probationDate);

			dto.setFirstName(employeeName);
			dto.setEmployeeCode(employeeCode);
			dto.setDepartmentName(departmentName);
			dto.setDateOfJoiningNew(dateOfJoiningNew);
			dto.setProbationDateNew(probationDateNew);
			employeeDTOs.add(dto);
		}

		return employeeDTOs;
	}

	@Override
	public List<EmployeeDTO> getEmployeeListContractExpired(Long companyId) {
		List<Object[]> deviceLogsInfoDTO = employeePersonalInformationRepository
				.getEmployeeListContractExpired(companyId);
		List<EmployeeDTO> employeeDTOs = new ArrayList<EmployeeDTO>();

		for (Object[] objects : deviceLogsInfoDTO) {
			EmployeeDTO dto = new EmployeeDTO();

			String employeeName = objects[0] != null ? (String) objects[0] : null;
			String employeeCode = objects[1] != null ? (String) objects[1] : null;
			String departmentName = objects[2] != null ? (String) objects[2] : null;
			Date dateOfJoining = objects[3] != null ? (Date) objects[3] : null;
			Date contractStartDate = objects[4] != null ? (Date) objects[4] : null;
			Date contractOverDate = objects[5] != null ? (Date) objects[5] : null;

			SimpleDateFormat sm = new SimpleDateFormat(StatusMessage.DATE_FORMAT);
			String contractStartDateNew = sm.format(contractStartDate);
			String contractOverDateNew = sm.format(contractOverDate);
			String dateOfJoiningNew = sm.format(dateOfJoining);
			dto.setFirstName(employeeName);
			dto.setEmployeeCode(employeeCode);
			dto.setDepartmentName(departmentName);
			dto.setDateOfJoiningNew(dateOfJoiningNew);
			dto.setDuration(contractStartDateNew + " to <br> " + contractOverDateNew);

			employeeDTOs.add(dto);
		}

		return employeeDTOs;
	}

	@Override
	public List<EmployeeDTO> getEmployeeListContractAboutExpired(Long companyId) {
		List<Object[]> deviceLogsInfoDTO = employeePersonalInformationRepository
				.getEmployeeListContractAboutExpired(companyId);
		List<EmployeeDTO> employeeDTOs = new ArrayList<EmployeeDTO>();

		for (Object[] objects : deviceLogsInfoDTO) {
			EmployeeDTO dto = new EmployeeDTO();

			String employeeName = objects[0] != null ? (String) objects[0] : null;
			String employeeCode = objects[1] != null ? (String) objects[1] : null;
			String departmentName = objects[2] != null ? (String) objects[2] : null;
			Date dateOfJoining = objects[3] != null ? (Date) objects[3] : null;
			Date contractStartDate = objects[4] != null ? (Date) objects[4] : null;
			Date contractOverDate = objects[5] != null ? (Date) objects[5] : null;

			SimpleDateFormat sm = new SimpleDateFormat(StatusMessage.DATE_FORMAT);
			String contractStartDateNew = sm.format(contractStartDate);
			String contractOverDateNew = sm.format(contractOverDate);
			String dateOfJoiningNew = sm.format(dateOfJoining);
			dto.setFirstName(employeeName);
			dto.setEmployeeCode(employeeCode);
			dto.setDepartmentName(departmentName);
			dto.setDateOfJoiningNew(dateOfJoiningNew);
			dto.setDuration(contractStartDateNew + " to <br> " + contractOverDateNew);

			employeeDTOs.add(dto);
		}

		return employeeDTOs;
	}

	@Override
	public List<EmployeeDTO> getEmployeeListAccidentalInsuranceAbout(Long companyId) {
		List<Object[]> deviceLogsInfoDTO = employeePersonalInformationRepository
				.getEmployeeListAccidentalInsuranceAbout(companyId);
		List<EmployeeDTO> employeeDTOs = new ArrayList<EmployeeDTO>();

		for (Object[] objects : deviceLogsInfoDTO) {
			EmployeeDTO dto = new EmployeeDTO();

			String employeeName = objects[0] != null ? (String) objects[0] : null;
			String employeeCode = objects[1] != null ? (String) objects[1] : null;
			String departmentName = objects[2] != null ? (String) objects[2] : null;
			Date datefrom = objects[3] != null ? (Date) objects[3] : null;
			Date dateTo = objects[4] != null ? (Date) objects[4] : null;
			String policyNo = objects[5] != null ? (String) objects[5] : null;
			SimpleDateFormat sm = new SimpleDateFormat(StatusMessage.DATE_FORMAT);
			String dateFromNew = sm.format(datefrom);
			String dateToNew = sm.format(dateTo);

			dto.setFirstName(employeeName);
			dto.setEmployeeCode(employeeCode);
			dto.setDepartmentName(departmentName);
			dto.setDuration(dateFromNew + "  to <br> " + dateToNew);
			dto.setPolicyNo(policyNo);
			employeeDTOs.add(dto);
		}

		return employeeDTOs;
	}

	@Override
	public List<EmployeeDTO> getEmployeeListMedicalInsuranceAbout(Long companyId) {
		List<Object[]> deviceLogsInfoDTO = employeePersonalInformationRepository
				.getEmployeeListMedicalInsuranceAbout(companyId);
		List<EmployeeDTO> employeeDTOs = new ArrayList<EmployeeDTO>();

		for (Object[] objects : deviceLogsInfoDTO) {
			EmployeeDTO dto = new EmployeeDTO();

			String employeeName = objects[0] != null ? (String) objects[0] : null;
			String employeeCode = objects[1] != null ? (String) objects[1] : null;
			String departmentName = objects[2] != null ? (String) objects[2] : null;
			Date datefrom = objects[3] != null ? (Date) objects[3] : null;
			Date dateTo = objects[4] != null ? (Date) objects[4] : null;
			String policyNo = objects[5] != null ? (String) objects[5] : null;
			SimpleDateFormat sm = new SimpleDateFormat(StatusMessage.DATE_FORMAT);
			String dateFromNew = sm.format(datefrom);
			String dateToNew = sm.format(dateTo);

			dto.setFirstName(employeeName);
			dto.setEmployeeCode(employeeCode);
			dto.setDepartmentName(departmentName);
			dto.setDuration(dateFromNew + " to <br> " + dateToNew);
			dto.setPolicyNo(policyNo);
			employeeDTOs.add(dto);
		}

		return employeeDTOs;
	}

	@Override
	public List<Object[]> fetchEmployeeList(Long companyId) {
		// TODO Auto-generated method stub
		return employeePersonalInformationRepository.findAllEmployeeByStatus(companyId);
	}

	@Override
	public List<Employee> fetchEmployeeFromDepartmentAndSeparation(Long companyId, Long deptId) {
		List<Employee> employees = new ArrayList<Employee>();
		List<Object[]> employeeList = employeePersonalInformationRepository.fetchEmployeeFromDepartmentAndSeparation(companyId,
				deptId);

		for (Object[] empObj : employeeList) {
			Employee emp = new Employee();

			long empId = ConverterUtil.getLong(empObj[0]);
			emp.setEmployeeId(empId);
			emp.setEmployeeCode(empObj[1].toString());
			emp.setFirstName(empObj[2].toString());
			emp.setLastName(empObj[3].toString());
			emp.setBiometricId(empObj[4] != null ? (empObj[4].toString()).trim() : null);
			Date joiningDate = empObj[5] != null ? ConverterUtil.getDate(empObj[05].toString()) : null;
			emp.setDateOfJoining(joiningDate);
			employees.add(emp);
		}
		return employees;
	}

	@Override
	public Employee fetchEmployeeByIdCode(Long employeeId) {
		// TODO Auto-generated method stub
		System.out.println("EmployeePersonalInformationServiceImpl.fetchEmployeeByIdCode()"+employeeId);
		return employeePersonalInformationRepository.fetchEmployeeByIdCode(employeeId);
	}
	
	@Override
	public void updateTdsPlanTypeStatus(String status, Long employeeId) {
		employeePersonalInformationRepository.updateTdsPlanTypeStatus(status, employeeId);
	}

	@Override
	public String getPlanTypeStatus(Long employeeId) {
		// TODO Auto-generated method stub
		return employeePersonalInformationRepository.getPlanTypeStatus(employeeId);
	}

	@Override
	public List<Employee> findAllEmpByDeptIdList(Long companyId, List<Long> departmentIdList) {
		return employeePersonalInformationRepository.findAllEmpByDeptIdList(companyId, departmentIdList);
	}

	@Override
	public List<Employee> getEmployeeOnDesignationId(Long companyId, Long designationId, Long departmentId) {
		// TODO Auto-generated method stub
		return employeePersonalInformationRepository.getEmployeeOnDesignationId(companyId, designationId,departmentId);
	}

}
