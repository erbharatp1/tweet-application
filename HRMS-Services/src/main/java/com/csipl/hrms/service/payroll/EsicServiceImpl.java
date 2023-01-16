package com.csipl.hrms.service.payroll;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.csipl.hrms.common.util.DateUtils;
import com.csipl.hrms.dto.payroll.EsiDTO;
import com.csipl.hrms.model.common.Company;
import com.csipl.hrms.model.payroll.Esi;
import com.csipl.hrms.service.adaptor.EsicAdaptor;
import com.csipl.hrms.service.payroll.repository.EsicRepository;


@Service("esicService")
public class EsicServiceImpl implements EsicService {

	@Autowired
	  private EsicRepository esicRepository;
	EsicAdaptor esicAdaptor=new EsicAdaptor();
 
	/**
	 * Save OR update esi object  into Database 
	 */
	@Override
	public Esi save(Esi esic) {
		Long esiId = esic.getEsiId();
		if(esiId!=null)
		esicRepository.deleteEsiCycleById(esiId);
		
		esic= esicRepository.save(esic);
		return esic;
	}
 
	/**
	 * to get Esi Object from database based on companyId
	 */
	@Override
	public Esi getESI(Long companyId) {	
		DateUtils dateUtils = new DateUtils();
		Date currentDate = dateUtils.getCurrentDate();	
		String status = "AC";
		Company company=new Company();
		company.setCompanyId(companyId);
		return esicRepository.findByEffectiveDateLessThanEqualAndActiveStatusAndCompany(DateUtils.getDateByDate(currentDate), status,company);
	}


	@Override
	public Esi getAllESI(Long companyId) {
		// TODO Auto-generated method stub
		return esicRepository.getAllESI(companyId);
	}

//	@Override
//	public Esi getAllESI(Long companyId) {
//		// TODO Auto-generated method stub
//		return esicRepository.getAllESI(companyId);
//	}

 
	/**
	 * to get Active Esi Object from database based on companyId
	 */
	@Override
	public Esi getActiveESI(Long companyId) {	
		String activeStatus="AC";
  		return esicRepository.getActiveESI( companyId,activeStatus);
	}

	/**
	 * to get Any Esi Object based on payroll process month last Date(YYYY-MM-DD) and company Id
	 */
	@Override
	public Esi getESIByPayrollPsMonth(String payPsMonth, Long companyId) //throws PayRollProcessException 
			{
		// TODO Auto-generated method stub
		
		 Date psMonthDate = DateUtils.getDateForProcessMonth(payPsMonth);
		 Calendar cal = Calendar.getInstance();
	     cal.setTime(psMonthDate);
	     cal.set(Calendar.MINUTE, 0);
	     cal.set(Calendar.SECOND, 0);   
	     cal.set(Calendar.HOUR_OF_DAY, 0);
	     cal.set(Calendar.DATE, cal.getActualMaximum(Calendar.DATE));
		
		List<Esi> esiobj =  esicRepository.getESIByPayrollPsMonth(cal.getTime(), companyId);
		
		//if(esiobj!= null && esiobj.size() >1) {
		//	throw new PayRollProcessException("Multiple Record found in ESI");
	//	}else
		if(esiobj!= null && esiobj.size() == 1) {
			return esiobj.get(0);
		}
		
		return null;
	}
	
	public List<EsiDTO> findAllEsiDescByDate(Long companyId){
		List<Esi> esiList = esicRepository.findAllEsiDescByDate(companyId);
		if(esiList!=null) {
			return	esiList.stream().map((Esi esi) ->
			new EsiDTO(esi.getEsiId(), esi.getEmployeePer(), esi.getEmployerPer(), esi.getMaxGrossLimit(), esi.getEffectiveDate() , esi.getEffectiveEndDate(), esi.getActiveStatus() , esi.getUserId() , esi.getDateCreated() , esi.getCompany().getCompanyId() , esi.getUserIdUpdate() , esi.getEsiCycles())
			).collect(Collectors.toList());
			
		}
		
		return null;
	}
	
	@Transactional
	public Esi saveEsics(Esi existingEsic , Esi newEsic) {
		 esicRepository.save(existingEsic);
		 
		Long esiId = newEsic.getEsiId();
		if(esiId!=null)
		esicRepository.deleteEsiCycleById(esiId);
	
		return esicRepository.save(newEsic);
	}
}
