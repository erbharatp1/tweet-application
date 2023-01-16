package com.csipl.hrms.service.employee.bulkupload;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.springframework.transaction.annotation.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.csipl.common.model.DrowpdownHd;
import com.csipl.common.services.dropdown.DropDownHdService;
import com.csipl.common.util.EnumUtil;
import com.csipl.hrms.common.enums.DropDownEnum;
import com.csipl.hrms.common.exception.PayRollProcessException;
import com.csipl.hrms.common.util.AppUtils;
import com.csipl.hrms.common.util.DateUtils;
import com.csipl.hrms.common.util.ErrorHandling;
import com.csipl.hrms.common.util.HrmsGlobalConstantUtil;
import com.csipl.hrms.dto.employee.EmployeeDTO;
import com.csipl.hrms.dto.employee.PayStructureHdDTO;
import com.csipl.hrms.model.authoriztion.RoleMaster;
import com.csipl.hrms.model.authoriztion.UserRole;
import com.csipl.hrms.model.common.Company;
import com.csipl.hrms.model.common.MandatoryInfoCheck;
import com.csipl.hrms.model.common.User;
import com.csipl.hrms.model.employee.Employee;
import com.csipl.hrms.model.employee.EmployeeBulkUploadMaster;
import com.csipl.hrms.model.employee.EmployeeEducation;
import com.csipl.hrms.model.employee.EmployeeFamily;
import com.csipl.hrms.model.employee.EmployeeIdProof;
import com.csipl.hrms.model.employee.EmployeeLanguage;
import com.csipl.hrms.model.employee.EmployeeSkill;
import com.csipl.hrms.model.employee.EmployeeStatuary;
import com.csipl.hrms.model.employee.MasterBook;
import com.csipl.hrms.model.employee.PayStructure;
import com.csipl.hrms.model.employee.PayStructureHd;
import com.csipl.hrms.model.employee.ProfessionalInformation;
import com.csipl.hrms.model.employee.Skill;
import com.csipl.hrms.model.organisation.Grade;
import com.csipl.hrms.model.payroll.PayHead;
import com.csipl.hrms.service.authorization.repository.UserRoleRepository;
import com.csipl.hrms.service.employee.repository.BankDetailsRepository;
import com.csipl.hrms.service.employee.repository.EmployeeEducationRepository;
import com.csipl.hrms.service.employee.repository.EmployeeIdProofRepository;
import com.csipl.hrms.service.employee.repository.EmployeePersonalInformationRepository;
import com.csipl.hrms.service.employee.repository.EmployeeSkillRepository;
import com.csipl.hrms.service.employee.repository.EmployeeStatuaryRepository;
import com.csipl.hrms.service.employee.repository.MasterBookRepository;
import com.csipl.hrms.service.employee.repository.PayStructureRepository;
import com.csipl.hrms.service.employee.repository.ProfessionalInformationRepository;
import com.csipl.hrms.service.employee.repository.UserRepository;
import com.csipl.hrms.service.organization.CompanyService;
import com.csipl.hrms.service.organization.repository.MandatoryInfoCheckRepository;
import com.csipl.hrms.service.payroll.repository.FamilyRepository;

@Transactional
@Service("bulkUploadService")
public class BulkUploadServiceImpl implements BulkUploadService {
	/**
	 * Logger declaration for knowing the flow of execution for debugging
	 */
	private final Logger logger = LoggerFactory.getLogger(BulkUploadServiceImpl.class);
	@Autowired
	private EmployeePersonalInformationRepository employeePersonalInformationRepository;
	
	@Autowired
	private BankDetailsRepository bankDetailsRepository;
	
	@Autowired
	private MasterBookRepository masterBookRepository;

	@Autowired
	private CompanyService companyService;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private UserRoleRepository userRoleRepository;
	
	@Autowired
	private EmployeeIdProofRepository employeeIdProofRepository;
	
	@Autowired
	MandatoryInfoCheckRepository mandatoryInfoCheckRepository;

	@Autowired
	FamilyRepository familyRepository;

	@Autowired
	private EmployeeStatuaryRepository employeeStatuaryRepository;
	
	@Autowired
	private 	ProfessionalInformationRepository professionalInformationRepository;
	@Autowired
	  private EmployeeEducationRepository eduInformationRepository;
	@Autowired
	EmployeeSkillRepository employeeSkillRepository;
	
	@Autowired
	DropDownHdService dropDownHdService;
	
	@Autowired
	PayStructureRepository payStructureRepository;
 	@Override
	public MasterBook findMasterBook(String bookCode, Long companyId, String bookType) {
		return masterBookRepository.getMasterBook(companyId, bookCode, bookType);
	}
	@Override
	public void saveAll(List<Employee> employeeList, EmployeeDTO employeeDto, Map<Integer, StringBuilder> errorMap) throws PayRollProcessException  {
		logger.info("employeeList is ===== " + employeeList);
		String userPassword = "@1234";
		logger.info("============9");
		int errorIndex = 0;
		
		
		//fetching employee code number list to update in master book
		//code is commented because now we can upload any type of employee code in db- 28/08/2019
	/*
	 * 	
	 
		List<Integer> employeeCodes = new ArrayList<Integer>();
		for(Employee emp: employeeList) {
			String empCode = emp.getEmployeeCode();
			String code= empCode.substring(empCode.lastIndexOf("-") +1, empCode.length());
			employeeCodes.add(Integer.parseInt(code));
		  }
		Collections.sort(employeeCodes);
		int maxEmpCode = employeeCodes.get(employeeCodes.size() - 1);
		BigDecimal deciLastNo = new BigDecimal(maxEmpCode);
		
	*/
		
		//to get aadhar list and check  aadhar number should'nt repeat in excel
		List<String> aadharList= new ArrayList<String>();
		for(Employee emp: employeeList) {
			String aadhar= emp.getAdharNumber();
			aadharList.add(aadhar);
		}
		String[] aadharArray =aadharList.toArray(new String[aadharList.size()]);
			HashMap<String, Integer> map = new HashMap<>();
			for( String a: aadharArray) {
			if (map.containsKey(a)) {
				int val = map.get(a);
				map.put(a, val + 1);
				throw new PayRollProcessException("Aadhar  can't duplicate in excel");
			} else {
				map.put(a, 1);
			}
		}
		
			//to get Bank Account Number list and check  Bank Account number should'nt repeat in excel
			List<String> BankAccountList= new ArrayList<String>();
			for(Employee emp: employeeList) {
				String account= emp.getEmployeeBanks().get(0).getAccountNumber();
				BankAccountList.add(account);
			}
			String[] bankArray = (String[]) BankAccountList.toArray(new String[BankAccountList.size()]);
			HashMap<String, Integer> Accountmap = new HashMap<>();
			for( String a: bankArray) {
			if (Accountmap.containsKey(a)) {
				int val = Accountmap.get(a);
				Accountmap.put(a, val + 1);
				throw new PayRollProcessException("Account number  can't duplicate in excel");
			} else {
				Accountmap.put(a, 1);
			}
		}
			
		///MasterBook masterBook = masterBookRepository.findMasterBook(employeeDto.getCompanyId(), HrmsGlobalConstantUtil.bookCode);
		
		Company company = new Company();
		company.setCompanyId(employeeDto.getCompanyId());
		company = companyService.getCompany(employeeDto.getCompanyId());
		String domainName= company.getDomainName();
		
		//masterBook.setCompany(company);
		//masterBook.setLastNo(deciLastNo);
		StringBuilder sb=new StringBuilder("");
	
		
		// To check whether employee is already present or not , check aadhar card , account number from database
		for(Employee employee : employeeList) {
			Employee employee2= null;
			 employee2= employeePersonalInformationRepository.findEmployees(employee.getEmployeeCode(), employeeDto.getCompanyId());
			if(employee2 != null) {
				sb.append(employee2.getEmployeeCode()+",This Employee code is already present");
			}
			if(sb.length()>0) {
				throw new PayRollProcessException(sb.toString());
			}
			else {
				int count = employeePersonalInformationRepository.adharCheck(employee.getAdharNumber());
				System.out.println("count is ---->"+count);
				if(count > 0 ) {
					throw new PayRollProcessException(employee.getEmployeeCode()+", Aadhar Number is already exist" );
				}
				int checkBankAccountNumber = bankDetailsRepository.bankAccountNumber(employee.getEmployeeBanks().get(0).getAccountNumber());
				System.out.println("count is ---->"+checkBankAccountNumber);
				if(checkBankAccountNumber > 0 ) {
					throw new PayRollProcessException(employee.getEmployeeCode()+" , Bank Account number is already exist" );
				}
			}
		}
				logger.info("inserting data into db ===== 10");
			//saving employee tables:- employee, employee bank,user,userRoles, mandatory check info
			for(Employee employeeToInsert : employeeList) {
				
			/*Groupg g= new Groupg();
			g.setGroupId(1L);*/
				//TdsLockUnlockStatus change by singya bhalse Unlock To Locked
			employeeToInsert.setTdsLockUnlockStatus("Locked");
			employeeToInsert.setDateCreated(new Date());
			employeeToInsert.setActiveStatus("AC");
			employeeToInsert.setDateUpdate(new Date());
			employeeToInsert.setUserIdUpdate(employeeDto.getUserId());
			//employeeToInsert.setGroupg(g);
			Employee	employee1= employeePersonalInformationRepository.save(employeeToInsert);
			User user = new User();
			user.setCompany(employeeToInsert.getCompany());
			//user.setGroupg(g);
			user.setUserPassword(AppUtils.SHA1(employeeToInsert.getEmployeeCode() + userPassword));
			user.setLoginName(employee1.getEmployeeCode()+"-"+domainName);
			user.setNameOfUser(employee1.getEmployeeCode());
			user.setSuserId(employee1.getUserId());
			user.setAddress(employee1.getAddress1());
			user.setEmailOfUser(employee1.getPersonalEmail());
			user.setUserAttempts(0l);
			User newUser =userRepository.save(user);
			RoleMaster roleMaster= new RoleMaster();
			roleMaster.setRoleId(employeeToInsert.getRoleId());
			UserRole userRole = new UserRole();
			userRole.setUser(newUser);
			userRole.setRoleMaster(roleMaster);
			userRole.setSUserId(HrmsGlobalConstantUtil.SUPER_USER_ID);
			userRoleRepository.save(userRole);
			
			//insert data into mandatory info table
			MandatoryInfoCheck mandatoryInfoCheck = new MandatoryInfoCheck();
			    mandatoryInfoCheck.setEmployee(employee1);
				mandatoryInfoCheck.setUserId(employeeToInsert.getUserId());
				if(!employeeToInsert.getAdharNumber().equals(""))
				mandatoryInfoCheck.setUi("YES");
				
				mandatoryInfoCheck.setBa("YES");
				mandatoryInfoCheck.setDateCreated(new Date());
				mandatoryInfoCheck.setDateUpdate(new Date());
			    mandatoryInfoCheckRepository.save(mandatoryInfoCheck);
			    
			  //insert data into statuary table
			    List<EmployeeStatuary> employeeStatuaryList= new ArrayList<>();
			    for(int i=0 ;i <=4 ;i++) {
			    	EmployeeStatuary empStatuary= new EmployeeStatuary();
			    	empStatuary.setEmployee(employee1);
			    	empStatuary.setUserId(employeeToInsert.getUserId());
			    	empStatuary.setStatus("AC");
			    	empStatuary.setEffectiveStartDate(new Date());
			    	empStatuary.setDateCreated(new Date());
			    	if(i==0) {
			    		empStatuary.setStatuaryType("UA");
			    		empStatuary.setIsApplicable("N");
			    		employeeStatuaryList.add(empStatuary);
			    	}
			    	else if(i==1) {
			    		empStatuary.setStatuaryType("PF");
			    		empStatuary.setIsApplicable("N");
			    		employeeStatuaryList.add(empStatuary);
			    	}
			    	else if(i==2) {
			    		empStatuary.setStatuaryType("ES");
			    		empStatuary.setIsApplicable("N");
			    		employeeStatuaryList.add(empStatuary);
			    	}
			    	else if(i==3) {
			    		empStatuary.setStatuaryType("ME");
			    		empStatuary.setIsApplicable("N");
			    		employeeStatuaryList.add(empStatuary);
			    	}
			    	else if(i==4) {
			    		empStatuary.setStatuaryType("AC");
			    		empStatuary.setIsApplicable("N");
			    		employeeStatuaryList.add(empStatuary);
			    	}
				}
			    employeeStatuaryRepository.save(employeeStatuaryList);
			}
			//now masterbook will not update - 28/08/2019
		//	if (masterBook != null)
		//	masterBookRepository.save(masterBook);
	}
	
	@Override
	public void saveIdProof(List<EmployeeIdProof> employeeIdProofList, EmployeeDTO employeeDto,Map<Integer, StringBuilder> errorMap) throws PayRollProcessException {
		logger.info("EmployeeIdProofList is ===== " + employeeIdProofList);
		MandatoryInfoCheck mandatoryInfoCheck = new MandatoryInfoCheck();
		logger.info(" mandatoryInfoCheck is : " + mandatoryInfoCheck);
		List<MandatoryInfoCheck> mandatoryInfoCheckList= new ArrayList<MandatoryInfoCheck>();
		Long empId = 0l;
		int errorIndex = 0;
		Employee employee= null;
		String idName= null;
		DrowpdownHd ids = dropDownHdService.findDropDownById(DropDownEnum.SelectIdType.drowpdownId);
		idName = EnumUtil.getEnumValue(ids, employeeIdProofList.get(0).getIdTypeId());
		if(idName == null) {
			throw new PayRollProcessException(" ID Proof type is not present in database" );
		}
				List<String> idList= new ArrayList<String>();
				for(EmployeeIdProof empProof: employeeIdProofList) {
					
					String idType = empProof.getIdTypeId();
					String idNumber= empProof.getIdNumber();
					idList.add(idNumber);
				}
				
				
				String[] aadharArray =idList.toArray(new String[idList.size()]);
					HashMap<String, Integer> map = new HashMap<>();
					for( String a: aadharArray) {
					if (map.containsKey(a)) {
						int val = map.get(a);
						map.put(a, val + 1);
						throw new PayRollProcessException("ID proof  can't duplicate in excel");
					} else {
						map.put(a, 1);
					}
				}
	   StringBuilder sb=new StringBuilder("");
		for (EmployeeIdProof employeeIdProof : employeeIdProofList) {
			 employee= employeePersonalInformationRepository.findEmployees(employeeIdProof.getEmployee().getEmployeeCode(), employeeDto.getCompanyId());
			if(employee==null)
				 throw new PayRollProcessException(employeeIdProof.getEmployee().getEmployeeCode() + " No employee found for this Code, Please onboard the employee first");
			else {
				int count = employeeIdProofRepository.checkIdProof(employeeIdProof.getIdTypeId(),employeeIdProof.getIdNumber());
				System.out.println("count is ---->"+count);
				if(count > 0 ) {
					throw new PayRollProcessException(employee.getEmployeeCode()+", ID proof is already exist" );
				}
				else {
				employeeIdProof.setEmployee(employee);
			    employeeIdProof.setDateCreated(new Date());
			    employeeIdProof.setDateUpdate(new Date());
			    employeeIdProof.setUserIdUpdate(employeeDto.getUserId());
				}
			}
		}
		if(sb.length()>0) {
			errorMap.put(errorIndex, sb);
			errorIndex++;
			//throw new CustomException(sb.toString());
		}
		else {
			employeeIdProofRepository.save(employeeIdProofList);
          for (EmployeeIdProof employeeIdProof : employeeIdProofList) {
        	  mandatoryInfoCheck = mandatoryInfoCheckRepository.getMandatoryInfoCheck(employeeIdProof.getEmployee().getEmployeeId());
			
			if (employeeIdProof.getIdTypeId().equals("PA")) {
				mandatoryInfoCheck.setPA("YES");
		     	mandatoryInfoCheck.setUserIdUpdate(employeeDto.getUserId());
		     	mandatoryInfoCheck.setDateUpdate(new Date());
		     }
          else if (employeeIdProof.getIdTypeId().equals("AA")) {
  	            mandatoryInfoCheck.setUi("YES");
		     	mandatoryInfoCheck.setUserIdUpdate(employeeDto.getUserId());
		     	mandatoryInfoCheck.setDateUpdate(new Date());
		     }
		}
		mandatoryInfoCheckList.add(mandatoryInfoCheck);
		mandatoryInfoCheckRepository.save(mandatoryInfoCheckList);
	}
	}
	@Override
	public void saveFamily(List<EmployeeFamily> employeeFamilyList, EmployeeDTO employeeDto,Map<Integer, StringBuilder> errorMap) throws PayRollProcessException  {
		logger.info("employeeFamilyList is ===== " + employeeFamilyList);
		
		Employee employee= null;
		List<EmployeeFamily> empFamily= new ArrayList<>();
	
		int errorIndex = 0;
		StringBuilder sb=new StringBuilder("");
		for (EmployeeFamily employeeFamily : employeeFamilyList) {
			 employee= employeePersonalInformationRepository.findEmployees(employeeFamily.getEmployee().getEmployeeCode(), employeeDto.getCompanyId());
			 if(employee == null)
				 throw new PayRollProcessException(employeeFamily.getEmployee().getEmployeeCode()+ " No employee found for this Code, Please onboard the employee first");
			 Long empId= employee.getEmployeeId();
			 empFamily=	 familyRepository.findEmployeeFamily(empId);
			 if(empFamily.size() > 0) {
				sb.append(employeeFamily.getEmployee().getEmployeeCode()+", Family details for this employee code is already present");
				throw new PayRollProcessException(sb.toString());
			}
			else {
				employeeFamily.setEmployee(employee);
			    employeeFamily.setDateCreated(new Date());
			}
		}
		if(sb.length()>0) {
			errorMap.put(errorIndex, sb);
			errorIndex++;
		}
		else {
		 familyRepository.save(employeeFamilyList);
		 logger.info("Employee Family saved SuccessFully ........");
		}
	}
	@Override
	public void saveUanAndPf(List<EmployeeStatuary> employeeStatuaryList, EmployeeDTO employeeDto,
			Map<Integer, StringBuilder> errorMap) throws PayRollProcessException {
		logger.info("employeeUan and PF List is ===== " + employeeStatuaryList);
		
		Employee employee= null;
		EmployeeStatuary empStatuaryObj= null;
		int errorIndex = 0;
	    StringBuilder sb=new StringBuilder("");
		MandatoryInfoCheck mandatoryInfoCheck = new MandatoryInfoCheck();
		
		logger.info(" mandatoryInfoCheck is : " + mandatoryInfoCheck);
		
		//to get UAN list and check  UAN number should'nt repeat in excel
				List<String> uanList= new ArrayList<String>();
				for(EmployeeStatuary employeeUanAndPf : employeeStatuaryList) {
					String empUan = employeeUanAndPf.getStatuaryNumber();
					uanList.add(empUan);
				}
				String[] uanArray =uanList.toArray(new String[uanList.size()]);
					HashMap<String, Integer> map = new HashMap<>();
					for( String a: uanArray) {
					if (map.containsKey(a)) {
						int val = map.get(a);
						map.put(a, val + 1);
						throw new PayRollProcessException("UAN/PF Number  can't duplicate in excel");
					} else {
						map.put(a, 1);
					}
				}
					
				/*	//to get emp list and check  employee code should'nt repeat in excel
					List<String> uanList= new ArrayList<String>();
					for(EmployeeStatuary employeeUanAndPf : employeeStatuaryList) {
						String empUan = employeeUanAndPf.getStatuaryNumber();
						uanList.add(empUan);
					}
					String[] uanArray =uanList.toArray(new String[uanList.size()]);
						HashMap<String, Integer> map = new HashMap<>();
						for( String a: uanArray) {
						if (map.containsKey(a)) {
							int val = map.get(a);
							map.put(a, val + 1);
							throw new PayRollProcessException("UAN/PF Number  can't duplicate in excel");
						} else {
							map.put(a, 1);
						}
					}*/
		
		List<MandatoryInfoCheck> mandatoryInfoCheckList= new ArrayList<MandatoryInfoCheck>();
		List<EmployeeStatuary> employeeStatuaryPFList = new ArrayList<EmployeeStatuary>();
		for (EmployeeStatuary employeeUanAndPf : employeeStatuaryList) {
			employeeUanAndPf.setUserId(employeeDto.getUserId());
			 employee= employeePersonalInformationRepository.findEmployees(employeeUanAndPf.getEmployee().getEmployeeCode(), employeeDto.getCompanyId());
			if(employee==null)
				throw new PayRollProcessException(employeeUanAndPf.getEmployee().getEmployeeCode()+ " No employee found for this Code, Please onboard the employee first");
			else {
				int count = employeeStatuaryRepository.pfCheck(employeeUanAndPf.getStatuaryNumber(),employeeUanAndPf.getStatuaryType());
			System.out.println("count is ---->"+count);
			if(count > 0 ) {
				throw new PayRollProcessException(employee.getEmployeeCode()+", PF/UAN Number is already exists" );
				}
			
				empStatuaryObj=	employeeStatuaryRepository.findEmployeeStatuary(employee.getEmployeeId(),employeeUanAndPf.getStatuaryType());
				employeeUanAndPf.setEmployee(empStatuaryObj.getEmployee());
				//employeeUanAndPf.setStatuaryId(empStatuaryObj.getStatuaryId());
				employeeUanAndPf.setUserIdUpdate(employeeDto.getUserId());
				employeeUanAndPf.setDateUpdate(new Date());
				employeeUanAndPf.setIsApplicable(empStatuaryObj.getIsApplicable());
				employeeUanAndPf.setStatuaryNumber(employeeUanAndPf.getStatuaryNumber());
				employeeUanAndPf.setStatuaryType(employeeUanAndPf.getStatuaryType());
				employeeUanAndPf.setStatus("AC");
				employeeUanAndPf.setEffectiveStartDate(new Date());
				empStatuaryObj.setStatus("DE");
				empStatuaryObj.setEffectiveEndDate(new Date());
				employeeStatuaryPFList.add(empStatuaryObj);
			}
		}
		if(sb.length()>0) {
			errorMap.put(errorIndex, sb);
			errorIndex++;
			//throw new CustomException(sb.toString());
		}
		else {
			employeeStatuaryRepository.save(employeeStatuaryList);
			employeeStatuaryRepository.save(employeeStatuaryPFList);
			for (EmployeeStatuary employeeUanAndPf : employeeStatuaryList) {
	        	  mandatoryInfoCheck = mandatoryInfoCheckRepository.getMandatoryInfoCheck(employeeUanAndPf.getEmployee().getEmployeeId());
				if (("UA").equals(employeeUanAndPf.getStatuaryType())) {
					mandatoryInfoCheck.setUa("YES");
					mandatoryInfoCheck.setNu("YES");
				    mandatoryInfoCheck.setUserIdUpdate(employeeDto.getUserId());
				    mandatoryInfoCheck.setDateUpdate(new Date());
				}

				if (("PF").equals(employeeUanAndPf.getStatuaryType())) {
					mandatoryInfoCheck.setPF("YES");
				    mandatoryInfoCheck.setUserIdUpdate(employeeDto.getUserId());
			     	mandatoryInfoCheck.setDateUpdate(new Date());
				}
			}
			mandatoryInfoCheckList.add(mandatoryInfoCheck);
			logger.info("mandatoryInfoCheck.getPF()" + mandatoryInfoCheck.getPF());
			mandatoryInfoCheckRepository.save(mandatoryInfoCheckList);
	
		}
		
	}
	//to save Esi
	@Override
	public void saveEsi(List<EmployeeStatuary> employeeStatuaryList, EmployeeDTO employeeDto,
			Map<Integer, StringBuilder> errorMap) throws PayRollProcessException {
	logger.info("employee ESI List is ===== " + employeeStatuaryList);
		
		Employee employee= null;
		EmployeeStatuary empStatuaryObj= null;
		int errorIndex = 0;
	   StringBuilder sb=new StringBuilder("");
		MandatoryInfoCheck mandatoryInfoCheck = new MandatoryInfoCheck();
		logger.info(" mandatoryInfoCheck is : " + mandatoryInfoCheck);
		
		//to get UAN list and check  UAN number should'nt repeat in excel
		List<String> esiList= new ArrayList<String>();
		for(EmployeeStatuary employeeEsi : employeeStatuaryList) {
			String empEsi = employeeEsi.getStatuaryNumber();
			esiList.add(empEsi);
		}
		String[] esiArray =esiList.toArray(new String[esiList.size()]);
			HashMap<String, Integer> map = new HashMap<>();
			for( String a: esiArray) {
			if (map.containsKey(a)) {
				int val = map.get(a);
				map.put(a, val + 1);
				throw new PayRollProcessException("ESI Number  can't duplicate in excel");
			} else {
				map.put(a, 1);
			}
		}
			
		List<MandatoryInfoCheck> mandatoryInfoCheckList= new ArrayList<MandatoryInfoCheck>();
		List<EmployeeStatuary> employeeStatuaryEsiList = new ArrayList<EmployeeStatuary>();
		for (EmployeeStatuary employeeEsi : employeeStatuaryList) {
			employeeEsi.setUserId(employeeDto.getUserId());
			 employee= employeePersonalInformationRepository.findEmployees(employeeEsi.getEmployee().getEmployeeCode(), employeeDto.getCompanyId());
			if(employee==null)
					throw new PayRollProcessException(employeeEsi.getEmployee().getEmployeeCode() + " No employee found for this Code, Please onboard the employee first");
			int count = employeeStatuaryRepository.pfCheck(employeeEsi.getStatuaryNumber(),employeeEsi.getStatuaryType());
			System.out.println("count is ---->"+count);
			if(count > 0 ) {
				throw new PayRollProcessException(employee.getEmployeeCode()+", ESI Number is already exists" );
			}
			else {
				empStatuaryObj=	employeeStatuaryRepository.findEmployeeStatuary(employee.getEmployeeId(),employeeEsi.getStatuaryType());
				
				employeeEsi.setEmployee(empStatuaryObj.getEmployee());
				//employeeEsi.setStatuaryId(empStatuaryObj.getStatuaryId());
				employeeEsi.setUserIdUpdate(employeeDto.getUserId());
				employeeEsi.setDateUpdate(new Date());
				employeeEsi.setIsApplicable(empStatuaryObj.getIsApplicable());
				employeeEsi.setStatuaryNumber(employeeEsi.getStatuaryNumber());
				employeeEsi.setStatuaryType(employeeEsi.getStatuaryType());
				employeeEsi.setStatus("AC");
				employeeEsi.setEffectiveStartDate(new Date());
				empStatuaryObj.setStatus("DE");
				empStatuaryObj.setEffectiveEndDate(new Date());
				employeeStatuaryEsiList.add(empStatuaryObj);
				
				
		}
		if(sb.length()>0) {
			errorMap.put(errorIndex, sb);
			errorIndex++;
			//throw new CustomException(sb.toString());
		}
	}
			employeeStatuaryRepository.save(employeeStatuaryList);
			employeeStatuaryRepository.save(employeeStatuaryEsiList);
			for (EmployeeStatuary employeeEsi : employeeStatuaryList) {
	        	  mandatoryInfoCheck = mandatoryInfoCheckRepository.getMandatoryInfoCheck(employeeEsi.getEmployee().getEmployeeId());
				if (("ES").equals(employeeEsi.getStatuaryType())) {
					mandatoryInfoCheck.setEs("YES");
				    mandatoryInfoCheck.setUserIdUpdate(employeeDto.getUserId());
				    mandatoryInfoCheck.setDateUpdate(new Date());
				}
			}
			mandatoryInfoCheckList.add(mandatoryInfoCheck);
			logger.info("mandatoryInfoCheck.getEs()" + mandatoryInfoCheck.getEs());
			mandatoryInfoCheckRepository.save(mandatoryInfoCheckList);
	
	}
	
	// to save Medical insurance And Accidental insurance
	@Override
	public void saveMi(List<EmployeeStatuary> employeeStatuaryList, EmployeeDTO employeeDto,
			Map<Integer, StringBuilder> errorMap) throws PayRollProcessException {
		 logger.info("employee MI List is ===== " + employeeStatuaryList);
		Employee employee= null;
		EmployeeStatuary empMedical= null;
		int errorIndex = 0;
	   StringBuilder sb=new StringBuilder("");
		MandatoryInfoCheck mandatoryInfoCheck = new MandatoryInfoCheck();
		logger.info(" mandatoryInfoCheck is : " + mandatoryInfoCheck);
		List<MandatoryInfoCheck> mandatoryInfoCheckList= new ArrayList<MandatoryInfoCheck>();
		List<EmployeeStatuary> employeeStatuaryMIList = new ArrayList<EmployeeStatuary>();
		for (EmployeeStatuary employeeMedical : employeeStatuaryList) {
			employeeMedical.setUserId(employeeDto.getUserId());
			 employee= employeePersonalInformationRepository.findEmployees(employeeMedical.getEmployee().getEmployeeCode(), employeeDto.getCompanyId());
			if(employee==null)
				throw new PayRollProcessException(employeeMedical.getEmployee().getEmployeeCode()+ " No employee found for this Code, Please onboard the employee first");
			
			else {
				empMedical=	employeeStatuaryRepository.findEmployeeStatuary(employee.getEmployeeId(),employeeMedical.getStatuaryType());
				
				employeeMedical.setEmployee(empMedical.getEmployee());
				//employeeMedical.setStatuaryId(empMedical.getStatuaryId());
				employeeMedical.setUserIdUpdate(employeeDto.getUserId());
				employeeMedical.setDateUpdate(new Date());
				employeeMedical.setIsApplicable(employeeMedical.getIsApplicable());
				employeeMedical.setStatuaryNumber(employeeMedical.getStatuaryNumber());
				employeeMedical.setStatuaryType(employeeMedical.getStatuaryType());
				employeeMedical.setStatus("AC");
				employeeMedical.setEffectiveStartDate(new Date());
				empMedical.setStatus("DE");
				empMedical.setEffectiveEndDate(new Date());
				employeeStatuaryMIList.add(empMedical);
				
		}
	}
		if(sb.length()>0) {
			errorMap.put(errorIndex, sb);
			errorIndex++;
			//throw new CustomException(sb.toString());
		}
		else {
			employeeStatuaryRepository.save(employeeStatuaryList);
			employeeStatuaryRepository.save(employeeStatuaryMIList);
			for (EmployeeStatuary employeeMi : employeeStatuaryList) {
	        	  mandatoryInfoCheck = mandatoryInfoCheckRepository.getMandatoryInfoCheck(employeeMi.getEmployee().getEmployeeId());
				if (("ME").equals(employeeMi.getStatuaryType())) {
					mandatoryInfoCheck.setMi("YES");
				    mandatoryInfoCheck.setNm("YES");
				    mandatoryInfoCheck.setUserIdUpdate(employeeDto.getUserId());
				    mandatoryInfoCheck.setDateUpdate(new Date());
				}
			}
			mandatoryInfoCheckList.add(mandatoryInfoCheck);
			logger.info("mandatoryInfoCheck.getMi()" + mandatoryInfoCheck.getMi());
			mandatoryInfoCheckRepository.save(mandatoryInfoCheckList);
		}
	}
	@Override
	public void saveAi(List<EmployeeStatuary> employeeStatuaryList, EmployeeDTO employeeDto,
			Map<Integer, StringBuilder> errorMap) throws PayRollProcessException {
		 logger.info("employee AI List is ===== " + employeeStatuaryList);
		Employee employee= null;
		EmployeeStatuary empMedical= null;
		int errorIndex = 0;
	   StringBuilder sb=new StringBuilder("");
		MandatoryInfoCheck mandatoryInfoCheck = new MandatoryInfoCheck();
		logger.info(" mandatoryInfoCheck is : " + mandatoryInfoCheck);
		List<MandatoryInfoCheck> mandatoryInfoCheckList= new ArrayList<MandatoryInfoCheck>();
		List<EmployeeStatuary> employeeStatuaryAIList = new ArrayList<EmployeeStatuary>();
		for (EmployeeStatuary employeeMedical : employeeStatuaryList) {
			employeeMedical.setUserId(employeeDto.getUserId());
			 employee= employeePersonalInformationRepository.findEmployees(employeeMedical.getEmployee().getEmployeeCode(), employeeDto.getCompanyId());
			if(employee==null)
				throw new PayRollProcessException(employeeMedical.getEmployee().getEmployeeCode()+ " No employee found for this Code, Please onboard the employee first");
			else {
				empMedical=	employeeStatuaryRepository.findEmployeeStatuary(employee.getEmployeeId(),employeeMedical.getStatuaryType());
				employeeMedical.setEmployee(empMedical.getEmployee());
				//employeeMedical.setStatuaryId(empMedical.getStatuaryId());
				employeeMedical.setUserIdUpdate(employeeDto.getUserId());
				employeeMedical.setDateUpdate(new Date());
				employeeMedical.setIsApplicable(employeeMedical.getIsApplicable());
				employeeMedical.setStatuaryNumber(employeeMedical.getStatuaryNumber());
				employeeMedical.setStatuaryType(employeeMedical.getStatuaryType());
				employeeMedical.setStatus("AC");
				employeeMedical.setEffectiveStartDate(new Date());
				empMedical.setStatus("DE");
				empMedical.setEffectiveEndDate(new Date());
				employeeStatuaryAIList.add(empMedical);
		}
	}
		if(sb.length()>0) {
			errorMap.put(errorIndex, sb);
			errorIndex++;
		}
		else {
			employeeStatuaryRepository.save(employeeStatuaryList);
			employeeStatuaryRepository.save(employeeStatuaryAIList);
			for (EmployeeStatuary employeeMi : employeeStatuaryList) {
	        	  mandatoryInfoCheck = mandatoryInfoCheckRepository.getMandatoryInfoCheck(employeeMi.getEmployee().getEmployeeId());
				if (("AC").equals(employeeMi.getStatuaryType())) {
					mandatoryInfoCheck.setAi("YES");
					mandatoryInfoCheck.setNa("YES");
				    mandatoryInfoCheck.setUserIdUpdate(employeeDto.getUserId());
				    mandatoryInfoCheck.setDateUpdate(new Date());
				}
			}
			mandatoryInfoCheckList.add(mandatoryInfoCheck);
			logger.info("mandatoryInfoCheck.getMi()" + mandatoryInfoCheck.getMi());
			mandatoryInfoCheckRepository.save(mandatoryInfoCheckList);
	
		}
	}
	// save Employee Professional Information
	@Override
	public void saveProfessionalInfo(List<ProfessionalInformation> employeeProfessionalList, EmployeeDTO employeeDto,
			Map<Integer, StringBuilder> errorMap) throws PayRollProcessException {
		 logger.info("employee Professional list  ===== " + employeeProfessionalList);
			Employee employee= null;
			int errorIndex = 0;
		   StringBuilder sb=new StringBuilder("");
		   List<ProfessionalInformation> empProfessional= new ArrayList<>();
		   
			
			for (ProfessionalInformation employeeProfession : employeeProfessionalList) {
				 employee= employeePersonalInformationRepository.findEmployees(employeeProfession.getEmpCode(), employeeDto.getCompanyId());
				 if(employee == null)
					 throw new PayRollProcessException(employeeProfession.getEmpCode()+ " No employee found for this Code, Please onboard the employee first");
				 Long empId= employee.getEmployeeId();
				 empProfessional=professionalInformationRepository.findEmployeeProfessopnal(empId);
				if(empProfessional.size() >0 )
					throw new PayRollProcessException(employeeProfession.getEmpCode() +" Professional information for this employee code is already present");
				else
					employeeProfession.setUserId(employeeDto.getUserId());
					employeeProfession.setDateCreated(new Date());
					employeeProfession.setDateUpdate(new Date());
					employeeProfession.setUserIdUpdate(employeeDto.getUserId());
					employeeProfession.setEmployeeId(employee.getEmployeeId());
			}
			if(sb.length()>0) {
				errorMap.put(errorIndex, sb);
				errorIndex++;
				//throw new CustomException(sb.toString());
			}
			else {
				professionalInformationRepository.save(employeeProfessionalList);
				
			}
	}
	// save educational information
	@Override
	public void saveEducationInfo(List<EmployeeEducation> employeeEducationList, EmployeeDTO employeeDto,
			Map<Integer, StringBuilder> errorMap) throws PayRollProcessException  {
		logger.info("employee Education list  ===== " + employeeEducationList);
		Employee employee= null;
		int errorIndex = 0;
	   StringBuilder sb=new StringBuilder("");
	   List<EmployeeEducation> empEducation= new ArrayList<>();
		for (EmployeeEducation employeeEducation : employeeEducationList) {
			
			 employee= employeePersonalInformationRepository.findEmployees(employeeEducation.getEmpCode(), employeeDto.getCompanyId());
			 if(employee == null)
				throw new PayRollProcessException(employeeEducation.getEmpCode()+ " No employee found for this Code, Please onboard the employee first");
			 Long empId= employee.getEmployeeId();
			 empEducation=eduInformationRepository.findAllEmployeeEducations(empId);
			if(empEducation.size() >0 )
				throw new PayRollProcessException(employeeEducation.getEmpCode() + " Education information for this employee code is already present");
			else
				employeeEducation.setUserId(employeeDto.getUserId());
				employeeEducation.setDateCreated(new Date());
				employeeEducation.setDateUpdate(new Date());
				employeeEducation.setUserIdUpdate(employeeDto.getUserId());
				employeeEducation.setEmployeeId(employee.getEmployeeId());
				Company company= new Company();
				company.setCompanyId(employeeDto.getCompanyId());
			/*	Groupg g= new Groupg();
				g.setGroupId(1L);*/
				employeeEducation.setCompany(company);
				//employeeEducation.setGroupg(g);
		}
		if(sb.length()>0) {
			errorMap.put(errorIndex, sb);
			errorIndex++;
			//throw new CustomException(sb.toString());
		}
		else {
			eduInformationRepository.save(employeeEducationList);
			
		}
	}
	//To save Employee Language
	@Override
	public void saveLanguage(List<EmployeeLanguage> employeeLanguageList, EmployeeDTO employeeDto,
			Map<Integer, StringBuilder> errorMap) throws PayRollProcessException  {
		Employee employee= null;
		EmployeeLanguage empLang = null;
		
		int errorIndex = 0;
	   StringBuilder sb=new StringBuilder("");
	 
	   
       
		
		for (EmployeeLanguage employeeLanguage : employeeLanguageList) {
			
			 employee= employeePersonalInformationRepository.findEmployees(employeeLanguage.getEmployee().getEmployeeCode(), employeeDto.getCompanyId());
			if(employee==null)
				throw new PayRollProcessException(employeeLanguage.getEmployee().getEmployeeCode()+ " No employee found for this Code, Please onboard the employee first");
			else
				empLang=employeePersonalInformationRepository.findEmployeeLanguage(employee.getEmployeeId(),employeeLanguage.getLanguage().getLanguageId());
			if(empLang != null) {
				employeeLanguage.setEmployee(empLang.getEmployee());
				employeeLanguage.setEmployeeLanguageId(empLang.getEmployeeLanguageId());
			}
			else
				employeeLanguage.setEmployee(employee);
			
				
		}
		if(sb.length()>0) {
			errorMap.put(errorIndex, sb);
			errorIndex++;
		}
		else {
			employee.setEmployeeLanguages(employeeLanguageList);
			employeePersonalInformationRepository.save(employee);
			
		}
	
	}
	
	// saving Pay structure
	//To save Pay structure of the employee - table affected - payStructureHD and PayStruture
	@Override
	public void saveEmployeePaystructure(List<PayStructureHd> employeePayStrcutureList, EmployeeDTO employeeDto,
			Map<Integer, StringBuilder> errorMap) throws PayRollProcessException ,ErrorHandling,org.springframework.dao.DataIntegrityViolationException {
		
		Employee employee= null;
		List<EmployeeStatuary> employeeStatuaryList= new ArrayList<>();
		//to get employee list and check  employee should'nt repeat in excel
				List<String> empList= new ArrayList<String>();
				for(PayStructureHd emp: employeePayStrcutureList) {
					String empl= emp.getEmployee().getEmployeeCode();
					empList.add(empl);
				}
				String[] empArray =empList.toArray(new String[empList.size()]);
					HashMap<String, Integer> map = new HashMap<>();
					for( String a: empArray) {
					if (map.containsKey(a)) {
						int val = map.get(a);
						map.put(a, val + 1);
						throw new PayRollProcessException("Employee Code can not repeat in excel sheet");
					} else {
						map.put(a, 1);
					}
				}
		
		//check if pay is already created through bulk upload		
		getExistingPayStructures(employeePayStrcutureList, employeeDto.getCompanyId());
		
		for (PayStructureHd employeePayStructure : employeePayStrcutureList) {
			employee= employeePersonalInformationRepository.findEmployees(employeePayStructure.getEmployee().getEmployeeCode(), employeeDto.getCompanyId());
			if(employee==null)
				throw new PayRollProcessException(employeePayStructure.getEmployee().getEmployeeCode()+ " No employee found for this Code, Please onboard the employee first");
			else {
				
			//	PayStructureHd pay = new PayStructureHd();
				employeePayStructure.setEmployee(employee);
				Grade g= new Grade();
				g.setGradesId(employee.getGradesId());
				employeePayStructure.setGrade(g);
				employeePayStructure.setActiveStatus("AC");
				employeePayStructure.setUserId(employeeDto.getUserId());
				employeePayStructure.setDateCreated(new Date());
				employeePayStructure.setThrough_excel_flag("Y");
				employeePayStructure.setEffectiveDate(DateUtils.getDateForProcessMonth(employeePayStructure.getProcessMonth()));
				
			    for(int i=0 ;i <=2 ;i++) {
			    	EmployeeStatuary empStatuary= new EmployeeStatuary();
			    	if(i==0) {
			    	if(findEmployeeStatuaryIsList(employee.getEmployeeId(), "UA"))
			    	throw new PayRollProcessException("Multiple Records Found For Statuary Type UA For Employee Code "+ employeePayStructure.getEmployee().getEmployeeCode());	
			    	empStatuary= employeeStatuaryRepository.findEmployeeStatuary(employee.getEmployeeId(), "UA");
			    	if(empStatuary != null)
			    	empStatuary.setStatuaryId(empStatuary.getStatuaryId());
			    	empStatuary.setEmployee(empStatuary.getEmployee());
			    	empStatuary.setStatuaryId(empStatuary.getStatuaryId());
			    	empStatuary.setIsApplicable(employeePayStructure.getIs_pf_applicable());
			    	empStatuary.setDateCreated(empStatuary.getDateCreated());
			    	empStatuary.setUserId(empStatuary.getUserId());
			    	empStatuary.setDateUpdate(new Date());
			    	empStatuary.setUserIdUpdate(employeePayStructure.getUserId());
					employeeStatuaryList.add(empStatuary);
			    	}
			    	if(i==1) {
			    		if(findEmployeeStatuaryIsList(employee.getEmployeeId(), "PF"))
					    throw new PayRollProcessException("Multiple Records Found For Statuary Type PF For Employee Code "+ employeePayStructure.getEmployee().getEmployeeCode());
			    		empStatuary= employeeStatuaryRepository.findEmployeeStatuary(employee.getEmployeeId(), "PF");
			    		if(empStatuary != null)
			    		empStatuary.setStatuaryId(empStatuary.getStatuaryId());
			    		empStatuary.setEmployee(empStatuary.getEmployee());
			    		empStatuary.setStatuaryId(empStatuary.getStatuaryId());
						empStatuary.setIsApplicable(employeePayStructure.getIs_pf_applicable());
						empStatuary.setDateCreated(empStatuary.getDateCreated());
				    	empStatuary.setUserId(empStatuary.getUserId());
				    	empStatuary.setDateUpdate(new Date());
				    	empStatuary.setUserIdUpdate(employeePayStructure.getUserId());
						employeeStatuaryList.add(empStatuary);
			    	}
			    	if(i==2) {
			    		if(findEmployeeStatuaryIsList(employee.getEmployeeId(), "ES"))
					    throw new PayRollProcessException("Multiple Records Found For Statuary Type ES For Employee Code "+ employeePayStructure.getEmployee().getEmployeeCode());
			    		empStatuary= employeeStatuaryRepository.findEmployeeStatuary(employee.getEmployeeId(), "ES");
			    		if(empStatuary != null)
			    		empStatuary.setStatuaryId(empStatuary.getStatuaryId());
			    		empStatuary.setEmployee(empStatuary.getEmployee());
			    		empStatuary.setStatuaryId(empStatuary.getStatuaryId());
			    		empStatuary.setIsApplicable(employeePayStructure.getIs_esi_applicable());
			    		empStatuary.setDateCreated(empStatuary.getDateCreated());
				    	empStatuary.setUserId(empStatuary.getUserId());
				    	empStatuary.setDateUpdate(new Date());
				    	empStatuary.setUserIdUpdate(employeePayStructure.getUserId());
						employeeStatuaryList.add(empStatuary);
						
			    	}
			    }
			    
			    employeeStatuaryRepository.save(employeeStatuaryList);
			    
				if(employeePayStructure.getPayHeadExcelId() != null && employeePayStructure.getAmountExcel() != null ) {
				String payHeads = employeePayStructure.getPayHeadExcelId();
				String pAmount = employeePayStructure.getAmountExcel();
				String[] headIDs= payHeads.split(",");
				
				
				List<String> list= new ArrayList<String>();
				list =  Arrays.asList(headIDs); 
				String[] pAmountId= pAmount.split(",");
				List<String> amountList= new ArrayList<String>();
				amountList =  Arrays.asList(pAmountId);
				List<PayStructure> pList = new ArrayList<>();
				for(int i=0;i<list.size();i++) {
				
						PayHead pHead = new PayHead();
						PayStructure payStruct= new PayStructure();
						Long headId = Long.parseLong(list.get(i).trim());
						payStruct.setAmount(new BigDecimal(amountList.get(i).trim()));
						pHead.setPayHeadId(headId);
						payStruct.setPayHead(pHead);
						payStruct.setUserId(employeeDto.getUserId());
						payStruct.setDateCreated(new Date());
						payStruct.setPayStructureHd(employeePayStructure);
						pList.add(payStruct);
						
						}
				
				employeePayStructure.setPayStructures(pList);
				
						
				}
				
			      payStructureRepository.save(employeePayStructure);
				
			 }
			 
			}
		}
	
		
	
	@Override
	public void saveSkills(List<EmployeeSkill> employeeSkillsList, EmployeeDTO employeeDto,
			Map<Integer, StringBuilder> errorMap)  {
		Employee employee= null;
		int errorIndex = 0;
	   StringBuilder sb=new StringBuilder("");
		
		for (EmployeeSkill employeeSkill : employeeSkillsList) {
			
			 employee= employeePersonalInformationRepository.findEmployees(employeeSkill.getEmployee().getEmployeeCode(), employeeDto.getCompanyId());
			if(employee==null)
			sb.append(employeeSkill.getEmployee().getEmployeeCode()+",");
			else
			employeeSkill.setEmployee(employee);
			employeeSkill.setUserId(employeeDto.getUserId());
			employeeSkill.setDateCreated(new Date());
			employeeSkill.setDateUpdate(new Date());
			employeeSkill.setUserIdUpdate(employeeDto.getUserId());
			employeeSkill.setActiveStatus("AC");
			// employeeSkillRepository.save(employeeSkill);
			if(sb.length()>0) {
				errorMap.put(errorIndex, sb);
				errorIndex++;
				//throw new CustomException(sb.toString());
			}
			else {
				if(employeeSkill.getSkillSet().size() >0 ) {
					for(Skill employeeSkill1 : employeeSkill.getSkillSet()) {
						Skill s= new Skill();
						s.setSkillId(employeeSkill1.getSkillId());
						employeeSkill.setSkill(s);
						 employeeSkillRepository.save(employeeSkill);
				}
					
			}	
		}
	}
	}
	
	@Override
	public List<EmployeeBulkUploadMaster> findHeaderName(String fileCode) {
		logger.info("BulkUploadServiceImpl.findHeaderName()" + fileCode);
		return employeePersonalInformationRepository.findHeaderName(fileCode);
	}
	@Override
	public List<Employee> findAllEmployee(Long companyId) {
		logger.info("BulkUploadServiceImpl.findAllEmployee()" + companyId);
		return employeePersonalInformationRepository.findAllEmployee(companyId);
	}

	//check multiple row data for statuaryType type
	public boolean findEmployeeStatuaryIsList(Long employeeId , String statuaryType) {
		List<EmployeeStatuary> empStatuaryList = employeeStatuaryRepository.findEmployeeStatuaryIsList(employeeId , statuaryType);
		// here checking null and empStatuaryList records greater than one ( checking multiple records) 
		if(empStatuaryList !=null && empStatuaryList.size() > 1) {
			logger.info("BulkUploadServiceImpl.findEmployeeStatuaryIsList() true for employeeId "+employeeId+"  and statuaryType "+ statuaryType);
			return true;
		}
		return false;
	}
	
	//filter already created pay structure
	public void getExistingPayStructures(List<PayStructureHd> employeePayStrcutureList ,Long companyId) throws PayRollProcessException {
		
		List<Object[]> allEmployeePays = payStructureRepository.getAllEmployeePays(companyId);
		
		List<String> allPaysemployeeCodeList = new ArrayList<>();
		for(Object[] paysObj : allEmployeePays ) {
		Long employeeId = paysObj[0] != null ? Long.parseLong(paysObj[0].toString()) : null;
		String employeeCode = paysObj[1] != null ? paysObj[1].toString() : null;
		allPaysemployeeCodeList.add(employeeCode);
		}
	
		List<String> payStrcutureEmployeeCodeList = new ArrayList<>();
		for(PayStructureHd payStructureHd : employeePayStrcutureList) {
		payStrcutureEmployeeCodeList.add(payStructureHd.getEmployee().getEmployeeCode());
		}
		
		if(payStrcutureEmployeeCodeList.retainAll(allPaysemployeeCodeList)) {
		logger.info("retain list in if "+payStrcutureEmployeeCodeList);
		}
		
		if(payStrcutureEmployeeCodeList.size() > 0) {
			throw new PayRollProcessException("Pay Structure Already created for Employee Code "+payStrcutureEmployeeCodeList);	
		}
	}
	
	
}
	
