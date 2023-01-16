package com.csipl.hrms.service.employee;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.csipl.common.services.dropdown.DropDownCache;
import com.csipl.hrms.common.enums.DropDownEnum;
import com.csipl.hrms.common.util.ErrorHandling;
import com.csipl.hrms.model.common.MandatoryInfoCheck;
import com.csipl.hrms.model.employee.EmployeeStatuary;
import com.csipl.hrms.service.employee.repository.EmployeeStatuaryRepository;
import com.csipl.hrms.service.organization.repository.MandatoryInfoCheckRepository;

@Transactional
@Service("employeeStatuaryService")
public class EmployeeStatuaryServiceImpl implements EmployeeStatuaryService {
	/**
	 * Logger declaration for knowing the flow of execution for debugging
	 */
	private static final Logger logger = LoggerFactory.getLogger(EmployeeStatuaryServiceImpl.class);

	@Autowired
	private EmployeeStatuaryRepository employeeStatuaryRepository;

	@Autowired
	MandatoryInfoCheckRepository mandatoryInfoCheckRepository;

	/**
	 * Method performed save OR update operation
	 * @throws ErrorHandling 
	 */
	@Override
	public List<EmployeeStatuary> save(List<EmployeeStatuary> employeeStatuaryList, Long employeeId) throws ErrorHandling {
		logger.info("EmployeeStatuaryList is ===== " + employeeId);
		List<EmployeeStatuary> employeeStatuaryInfos = (List<EmployeeStatuary>) employeeStatuaryRepository
				.save(employeeStatuaryList);
		
		/*
		 * first check statuary related data already exist or not
		 */
		if(employeeStatuaryList!=null) {
			for(EmployeeStatuary es : employeeStatuaryList) {
				if(es.getStatuaryNumber()!=null && es.getStatuaryNumber()!="") {
				int count=employeeStatuaryRepository.checkStatuaryExist(employeeId, es.getStatuaryType(), es.getStatuaryNumber());
				if(count > 0 ) {
					throw new ErrorHandling( DropDownCache.getInstance().getDropDownValue(
							DropDownEnum.Statuary.getDropDownName(), es.getStatuaryType())+" No. "+ es.getStatuaryNumber()+  " already exist " );
				}
				}
			}
		}
		for (EmployeeStatuary employeeStatuary : employeeStatuaryInfos) {
			System.out.println(employeeStatuary.getStatuaryId());
		}
		MandatoryInfoCheck mandatoryInfoCheck = new MandatoryInfoCheck();
		mandatoryInfoCheck = mandatoryInfoCheckRepository.getMandatoryInfoCheck(employeeId);

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

			if ((("ES").equals(employeeStatuary.getStatuaryType()))&& (employeeStatuary.getStatuaryNumber()!=null && employeeStatuary.getStatuaryNumber()!="") && employeeStatuary.getIsApplicable()!="Y" ) {
				mandatoryInfoCheck.setEs("YES");
			}
			else if((("ES").equals(employeeStatuary.getStatuaryType())) && ((employeeStatuary.getIsApplicable()).equals("Y")))
			{
				mandatoryInfoCheck.setEs("YES");
			}
			else if(("ES").equals(employeeStatuary.getStatuaryType()))
			{
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
		 logger.info("mandatoryInfoCheck.getEs()" + mandatoryInfoCheck.getEs());
		return employeeStatuaryInfos;
	}

	/**
	 * to get List of EmployeeStatuary from database based on employeeId
	 */
	@Override
	public List<EmployeeStatuary> findAllEmployeeStatuary(Long employeeId) {
		logger.info("findAllEmployeeStatuary is ===== " + employeeId);
		List<EmployeeStatuary> empStaturyList = employeeStatuaryRepository.findAllEmployeeStatuarys(employeeId);
		System.out.println("empStaturyList....");
		for (EmployeeStatuary employeeStatuary : empStaturyList) {
			System.out.println(employeeStatuary.getDateFrom());
		}
		return empStaturyList;
	}
	@Override
	public List<EmployeeStatuary> findAllEmployeeStatuaryWithoutUAN(Long employeeId) {
		logger.info("findAllEmployeeStatuary is ===== " + employeeId);
		List<EmployeeStatuary> empStaturyList = employeeStatuaryRepository.findAllEmployeeStatuarysWithoutUAN(employeeId);
		System.out.println("empStaturyList....");
		for (EmployeeStatuary employeeStatuary : empStaturyList) {
			System.out.println(employeeStatuary.getDateFrom());
		}
		return empStaturyList;
	}
	/**
	 * delete EmployeeStatuary object from database based on staturyId (Primary Key)
	 */
	@Override
	public void delete(Long statuaryId) {
		employeeStatuaryRepository.delete(statuaryId);
	}

	@Override
	public List<EmployeeStatuary> findEmployeeStatuaryNominee(Long empId) {
		// TODO Auto-generated method stub
		return employeeStatuaryRepository.findAllEmployeeNominee(empId);
	}

	/*@Override
	public EmployeeStatuary EmployeeStatuary(Long empId) {
		// TODO Auto-generated method stub
		return employeeStatuaryRepository.findOne(arg0);
	}*/
}
