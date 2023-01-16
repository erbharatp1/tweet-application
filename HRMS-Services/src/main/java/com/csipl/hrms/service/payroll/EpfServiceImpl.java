package com.csipl.hrms.service.payroll;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.csipl.hrms.common.exception.PayRollProcessException;
import com.csipl.hrms.common.util.DateUtils;
import com.csipl.hrms.dto.payroll.EpfDTO;
import com.csipl.hrms.model.payroll.Epf;
import com.csipl.hrms.service.payroll.repository.EpfRepository;


@Service("epfService")
public class EpfServiceImpl implements EpfService {
	
	@Autowired
	private EpfRepository epfRepository;
	
	/**
	 * Save OR update epf object  into Database 
	 */
	@Transactional
	@Override
	public Epf save(Epf epf) {
		/*Epf epfnew = new Epf ();
		epfnew= epf;
		epf = epfRepository.getEPF( epf.getCompany().getCompanyId() );
		epf.setActiveStatus(StatusMessage.DEACTIVE_CODE);
		System.out.println("epf.setActiveStatus(StatusMessage.DEACTIVE_CODE);..."+epf.getActiveStatus());
		epf = epfRepository.save(epf);
		epfnew.setEpfId(null);
		epfnew.setActiveStatus(StatusMessage.ACTIVE_CODE);*/
    	//epfnew = epfRepository.save(epf);
   		return epfRepository.save(epf);
	}
	/**
	 * to get EPF Object from database based on companyId
	 */
 	@Override
	public Epf getEPF( Long companyId ) {
 	//	DateUtils dateUtils = new DateUtils();
	//	Date currentDate = dateUtils.getCurrentDate();
 		return epfRepository.getEPF( companyId );
	}
 	/**
	 * to get Any Esi Object based on payroll process month last Date(YYYY-MM-DD) and company Id
	 */
	@Override
	public Epf getEPFByPayrollPsMonth(String payPsMonth, Long companyId) throws PayRollProcessException 
			{
		// TODO Auto-generated method stub
		
		 Date psMonthDate = DateUtils.getDateForProcessMonth(payPsMonth);
		 Calendar cal = Calendar.getInstance();
	     cal.setTime(psMonthDate);
	     cal.set(Calendar.MINUTE, 0);
	     cal.set(Calendar.SECOND, 0);   
	     cal.set(Calendar.HOUR_OF_DAY, 0);
	     cal.set(Calendar.DATE, cal.getActualMaximum(Calendar.DATE));
		
		List<Epf> epfobj =  epfRepository.getEPFByPayrollPsMonth(cal.getTime(), companyId);
		
		if(epfobj!= null && epfobj.size() >1) {
			throw new PayRollProcessException("Multiple Record found in EPF");
		}else if(epfobj!= null && epfobj.size() == 1) {
			return epfobj.get(0);
		}
		
		return null;
	}
	
	@Transactional
	@Override
	public Epf saveEpfs(Epf existingEpfDE, Epf newEpf) {
		       epfRepository.save(existingEpfDE);
		return epfRepository.save(newEpf);
	}
	
	public List<EpfDTO> findAllEpfsDescByDate(Long companyId){
		
		List<Epf> epfList = epfRepository.findAllEpfsDescByDate(companyId);
		if(epfList!=null) {
			return	epfList.stream().map((Epf epf) ->
			new EpfDTO(epf.getEpfId(), epf.getEmployeePer() , epf.getEmployerPer(), epf.getEmployerPensionPer(), epf.getAdminPer(), epf.getEdliPer(), epf.getEdliExpPer(), epf.getMaxBasicLimit() , epf.getEffectiveDate(), epf.getActiveStatus(), epf.getUserId(), epf.getDateCreated() , epf.getMaxPensionLimit(), epf.getIsActual() , epf.getCompany().getCompanyId(), epf.getUserIdUpdate())
			).collect(Collectors.toList());                                                                                                                                                                                                                                                                
		}
		
		return null;
		
	}

}
