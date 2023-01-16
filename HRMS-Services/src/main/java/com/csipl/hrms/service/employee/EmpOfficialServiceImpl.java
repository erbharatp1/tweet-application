package com.csipl.hrms.service.employee;

import java.util.Date;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.csipl.hrms.common.exception.PayRollProcessException;
import com.csipl.hrms.common.util.ErrorHandling;
import com.csipl.hrms.model.authoriztion.RoleMaster;
import com.csipl.hrms.model.authoriztion.UserRole;
import com.csipl.hrms.model.common.MandatoryInfoCheck;
import com.csipl.hrms.model.common.User;
import com.csipl.hrms.model.employee.Employee;
import com.csipl.hrms.model.employee.EmployeeStatuary;
import com.csipl.hrms.service.authorization.repository.RoleMasterRepository;
import com.csipl.hrms.service.authorization.repository.UserRoleRepository;
import com.csipl.hrms.service.employee.repository.EmployeePersonalInformationRepository;
import com.csipl.hrms.service.employee.repository.EmployeeStatuaryRepository;
import com.csipl.hrms.service.employee.repository.UserRepository;
import com.csipl.hrms.service.organization.repository.MandatoryInfoCheckRepository;
@Transactional
@Service("empOfficialService")
public class EmpOfficialServiceImpl implements EmpOfficialService{
	/**
	 * Logger declaration for knowing the flow of execution for debugging
	 */
	private static final Logger logger = LoggerFactory.getLogger(FamilyServiceImpl.class);

	@Autowired
	EmployeePersonalInformationRepository empRepository;
	
	@Autowired
	EmployeeStatuaryRepository empStatutoryRepository;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	UserRoleRepository userRoleRepository;
	
	@Autowired
	RoleMasterRepository roleMasterRepository;
	
	@Autowired
	MandatoryInfoCheckRepository mandatoryInfoCheckRepository;

	/*
	 * 15-APR-2020
     */
	@Override
	public void saveOfficialInfo(Employee employee, List<EmployeeStatuary> statutoryList) throws ErrorHandling {
		//System.out.println("employee inside emloyeeServImpl..."+employee.toString());
		Employee employeeNew=empRepository.save(employee);
		User user=userRepository.findUser(employeeNew.getEmployeeCode());
		UserRole userRole=userRoleRepository.getUserRoles(user.getUserId());
		RoleMaster roleMaster = roleMasterRepository.getRoleMasterId(employee.getRole());
		userRole.setRoleMaster(roleMaster);
		userRoleRepository.save(userRole);
	//	System.out.println("employee aftr save emloyeeServImpl..."+employeeNew.toString());
		
		/*
		 * first check statuary related data already exist or not
		 */
		if(statutoryList!=null) {
			for(EmployeeStatuary es : statutoryList) {
				if(es.getStatuaryNumber()!=null && es.getStatuaryNumber()!="") {
				int count=empStatutoryRepository.checkStatuaryExist(employee.getEmployeeId(), es.getStatuaryType(), es.getStatuaryNumber());
				if(count > 0 ) {
					throw new ErrorHandling(es.getStatuaryNumber() + " ( "+es.getStatuaryType() +" ) "+" already exist " );
				}
				}
			}
		}
		List<EmployeeStatuary> employeeStatuaryInfos = (List<EmployeeStatuary>) empStatutoryRepository
				.save(statutoryList);
		
		MandatoryInfoCheck mandatoryInfoCheck = new MandatoryInfoCheck();
		mandatoryInfoCheck = mandatoryInfoCheckRepository.getMandatoryInfoCheck(employee.getEmployeeId());
		logger.info("mandatoryInfoCheck : " + mandatoryInfoCheck.getUserId());

		if (mandatoryInfoCheck.getUserId() == null && mandatoryInfoCheck.getDateCreated() == null) {

			mandatoryInfoCheck.setUserId(mandatoryInfoCheck.getUserId());
			mandatoryInfoCheck.setDateCreated(mandatoryInfoCheck.getDateCreated());
		}
		mandatoryInfoCheck.setDateUpdate(new Date());
		mandatoryInfoCheck.setUserIdUpdate(mandatoryInfoCheck.getUserId());
		for (EmployeeStatuary employeeStatuary : employeeStatuaryInfos) {
			// employeeStatuaryList.forEach(employeeStatuary -> {
			logger.info("employeeStatuary.getStatuaryType()" + employeeStatuary.getStatuaryType());

			if ((("ES").equals(employeeStatuary.getStatuaryType()))&&  (employeeStatuary.getStatuaryNumber()!=null && employeeStatuary.getStatuaryNumber()!="") && employeeStatuary.getIsApplicable()!="Y" )
			{
				mandatoryInfoCheck.setEs("YES");
			}
			else if ((("ES").equals(employeeStatuary.getStatuaryType())) && ((employeeStatuary.getIsApplicable()).equals("Y"))) {
				mandatoryInfoCheck.setEs("YES");
			} 
			else if (("ES").equals(employeeStatuary.getStatuaryType())) {
				mandatoryInfoCheck.setEs(null);
			}
			
			if ((("ME").equals(employeeStatuary.getStatuaryType()))&&  (employeeStatuary.getStatuaryNumber()!=null && employeeStatuary.getStatuaryNumber()!="") && employeeStatuary.getIsApplicable()!="Y" ) {
				mandatoryInfoCheck.setMi("YES");
			    mandatoryInfoCheck.setNm("YES");
			}
			else if((("ME").equals(employeeStatuary.getStatuaryType())) && ((employeeStatuary.getIsApplicable()).equals("Y")))
			{
				mandatoryInfoCheck.setMi("YES");
			    mandatoryInfoCheck.setNm("YES");
			}
			else if(("ME").equals(employeeStatuary.getStatuaryType()))
			{
				mandatoryInfoCheck.setMi(null);
			    mandatoryInfoCheck.setNm(null);
			}
			

			if ((("AC").equals(employeeStatuary.getStatuaryType()))&&  (employeeStatuary.getStatuaryNumber()!=null && employeeStatuary.getStatuaryNumber()!="") && employeeStatuary.getIsApplicable()!="Y" ) {
				mandatoryInfoCheck.setAi("YES");
				mandatoryInfoCheck.setNa("YES");
			}
			else if((("AC").equals(employeeStatuary.getStatuaryType())) && ((employeeStatuary.getIsApplicable()).equals("Y")))
			{
				mandatoryInfoCheck.setAi("YES");
				mandatoryInfoCheck.setNa("YES");
			}
			else if(("AC").equals(employeeStatuary.getStatuaryType()))
			{
				mandatoryInfoCheck.setAi(null);
				mandatoryInfoCheck.setNa(null);
			}

			if ((("UA").equals(employeeStatuary.getStatuaryType()))&&  (employeeStatuary.getStatuaryNumber()!=null && employeeStatuary.getStatuaryNumber()!="") && employeeStatuary.getIsApplicable()!="Y" ) {
				mandatoryInfoCheck.setUa("YES");
				mandatoryInfoCheck.setNu("YES");
			}
			else if((("UA").equals(employeeStatuary.getStatuaryType())) && ((employeeStatuary.getIsApplicable()).equals("Y")))
			{
				mandatoryInfoCheck.setUa("YES");
				mandatoryInfoCheck.setNu("YES");
			}
			else if(("UA").equals(employeeStatuary.getStatuaryType()))
			{
				mandatoryInfoCheck.setUa(null);
				mandatoryInfoCheck.setNu(null);
			}

			if ((("PF").equals(employeeStatuary.getStatuaryType()))&&  (employeeStatuary.getStatuaryNumber()!=null && employeeStatuary.getStatuaryNumber()!="") && employeeStatuary.getIsApplicable()!="Y" )
			{
				mandatoryInfoCheck.setPF("YES");
			}
			else if((("PF").equals(employeeStatuary.getStatuaryType()))  && ((employeeStatuary.getIsApplicable()).equals("Y")))
			{
				mandatoryInfoCheck.setPF("YES");
			}
			else if(("PF").equals(employeeStatuary.getStatuaryType()))
			{
				mandatoryInfoCheck.setPF(null);
			}
				

		}
		logger.info("mandatoryInfoCheck.getPF()" + mandatoryInfoCheck.getPF());
		mandatoryInfoCheckRepository.save(mandatoryInfoCheck);
	}
 
}
