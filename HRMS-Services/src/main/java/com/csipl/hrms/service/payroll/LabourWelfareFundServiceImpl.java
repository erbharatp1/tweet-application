package com.csipl.hrms.service.payroll;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.csipl.hrms.common.util.DateUtils;
import com.csipl.hrms.dto.payroll.LabourWelfareFundDTO;
import com.csipl.hrms.model.common.State;
import com.csipl.hrms.model.payroll.LabourWelfareFund;
import com.csipl.hrms.model.payroll.LabourWelfareFundInfo;
import com.csipl.hrms.service.organization.repository.StateRepository;
import com.csipl.hrms.service.payroll.repository.LabourWalfareFundRepository;

@Service
public class LabourWelfareFundServiceImpl implements LabourWelfareFundService {

	@Autowired
	private LabourWalfareFundRepository labourWalfareFundRepository;
	
	@Autowired
	private	StateRepository  stateRepository;

	/**
	 * Save OR update ProfessionalTax
	 */
	@Transactional
	@Override
	public  LabourWelfareFund save( LabourWelfareFund labourWelfareFund) {
		return labourWalfareFundRepository.save(labourWelfareFund);
	}

	/**
	 * to get List of ProfessionalTax objects from database based on currentDate
	 */
	@Override
	public List< LabourWelfareFund> getAllProfessionalTax(Long companyId) {
		DateUtils dateUtils = new DateUtils();
		Date currentDate = dateUtils.getCurrentDate();
		System.out.println("current date ....."+currentDate);
		//return labourWalfareFundRepository.findAllProfessionalTax( companyId);
		return null;
	}

	

	/**
	 * to get LabourWelfareFund object from database based on stateId and companyId
	 */
	 
	@Override
	public  LabourWelfareFund findLabourWelfareFundEmployee( Long stateId, Long companyId ,String payrollMonth) {
		 Date psMonthDate = DateUtils.getDateForProcessMonth(payrollMonth);
		 Calendar cal = Calendar.getInstance();
	     cal.setTime(psMonthDate);
	     cal.set(Calendar.MINUTE, 0);
	     cal.set(Calendar.SECOND, 0);   
	     cal.set(Calendar.HOUR_OF_DAY, 0);
	     cal.set(Calendar.DATE, cal.getActualMaximum(Calendar.DATE));			
	
		List<LabourWelfareFund> lwfList = labourWalfareFundRepository.findLabourWelfareFundEmployee(stateId, companyId,cal.getTime());
		 LabourWelfareFund labourWelfareFund = null;
		if (lwfList != null && lwfList.size() > 0) {
			labourWelfareFund = lwfList.get(0);
		}
		return labourWelfareFund;
	}
	
	
	  
	

	 
//	@Override
//	public  LabourWelfareFund findProfessionalTax(long professionalHeadId ,Long companyId) {
//		return null;
//	//	return labourWalfareFundRepository.findProfessionalTax(professionalHeadId ,companyId);
//	}

	@Override
	public List<State> findAllState(Long companyId) {
		return   stateRepository.findStateByIdLabour(companyId);
		 
	}
   @Transactional
	@Override
	public void updateByActiveStatus( LabourWelfareFundInfo info) {
	   labourWalfareFundRepository.updateByStatus(info.getLabourWelfareFundInfoId(), info.getActiveStatus());
	}

@Override
public List<LabourWelfareFund> getAllLabourWelfareFund(Long companyId) {
 
	return labourWalfareFundRepository.findAllLabourWelfareFund( companyId);
}

@Override
public LabourWelfareFund findLabourWelfareFund(Long labourWelfareFundHeadId, Long companyId) {
	// TODO Auto-generated method stub
	return labourWalfareFundRepository.findLabourWelfareFund(labourWelfareFundHeadId ,companyId);
}

@Override
@Transactional
public void updateApplicableStatus(String applicableStatus) {
	labourWalfareFundRepository.updateApplicableStatus(applicableStatus);	
}

@Override
public int getActiveCount(Long companyId) {
	return labourWalfareFundRepository.getActiveCount(companyId);
}

@Override
public List<LabourWelfareFundDTO> findAllLWFDescByDate(Long companyId) {
	List<LabourWelfareFund> lwfList = labourWalfareFundRepository.findAllLWFDescByDate(companyId);
	if(lwfList!=null) {
		return	lwfList.stream().map((LabourWelfareFund lwf) ->
		new LabourWelfareFundDTO(lwf.getLabourWelfareFundHeadId(), lwf.getActiveStatus(), lwf.getAllowModi(), lwf.getCompanyId(), lwf.getDateCreated(), lwf.getDateUpdate(), lwf.getEffectiveEndDate(), lwf.getEffectiveStartDate(), lwf.getGroupId(), lwf.getState().getStateName(), lwf.getLimitAmount(), lwf.getPerMonthAmount(), lwf.getState().getStateId(), lwf.getUserId(), lwf.getUserIdUpdate(), lwf.getLabourWelfareFundInfos())
		).collect(Collectors.toList());   
	}
	return null;
}

@Override
public List<LabourWelfareFundDTO> findAllLWFStateIdDescByDate(Long companyId, Long stateId) {
	 List<LabourWelfareFund> lwfListByStateId = labourWalfareFundRepository.findAllLWFStateIdDescByDate(companyId ,stateId);
		if(lwfListByStateId!=null) {
			return	lwfListByStateId.stream().map((LabourWelfareFund lwf) ->
			new LabourWelfareFundDTO(lwf.getLabourWelfareFundHeadId(), lwf.getActiveStatus(), lwf.getAllowModi(), lwf.getCompanyId(), lwf.getDateCreated(), lwf.getDateUpdate(), lwf.getEffectiveEndDate(), lwf.getEffectiveStartDate(), lwf.getGroupId(), lwf.getState().getStateName(), lwf.getLimitAmount(), lwf.getPerMonthAmount(), lwf.getState().getStateId(), lwf.getUserId(), lwf.getUserIdUpdate(), lwf.getLabourWelfareFundInfos())
			).collect(Collectors.toList());   
		}
		return null;
	}

public LabourWelfareFund findAllLWFById(Long companyId , Long labourWelfareFundHeadId){
	return  labourWalfareFundRepository.lwfByHeadId( labourWelfareFundHeadId, companyId);
}

}
