package com.csipl.hrms.service.payroll;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.csipl.hrms.common.util.DateUtils;
import com.csipl.hrms.dto.payroll.ProfessionalTaxDTO;
import com.csipl.hrms.dto.payroll.ProfessionalTaxInfoDTO;
import com.csipl.hrms.model.common.State;
import com.csipl.hrms.model.payroll.ProfessionalTax;
import com.csipl.hrms.model.payroll.ProfessionalTaxInfo;
import com.csipl.hrms.model.payrollprocess.ReportPayOut;
import com.csipl.hrms.service.organization.repository.StateRepository;
import com.csipl.hrms.service.payroll.repository.FinancialYearRepository;
import com.csipl.hrms.service.payroll.repository.PayrollControlRepository;
import com.csipl.hrms.service.payroll.repository.ProfessionalTaxRepository;
import com.csipl.hrms.service.payroll.repository.ReportPayOutRepository;

@Service("professionalTaxService")
public class ProfessionalTaxServiceImpl implements ProfessionalTaxService {

	private static final Logger logger = LoggerFactory.getLogger(ProfessionalTaxServiceImpl.class);
	
	@Autowired
	private ProfessionalTaxRepository professionalTaxRepository;
	
	@Autowired
	private	StateRepository  stateRepository;
	
	@Autowired
	private ReportPayOutRepository reportPayOutRepository;

	@Autowired
	private FinancialYearRepository financialYearRepository;
	
	@Autowired
	private PayrollControlRepository payrollControlRepository;

	/**
	 * Save OR update ProfessionalTax
	 */
	@Transactional
	@Override
	public ProfessionalTax save(ProfessionalTax professionalTax) {
		return professionalTaxRepository.save(professionalTax);
	}

	/**
	 * to get List of ProfessionalTax objects from database based on currentDate
	 */
	@Override
	public List<ProfessionalTax> getAllProfessionalTax(Long companyId) {
		DateUtils dateUtils = new DateUtils();
		Date currentDate = dateUtils.getCurrentDate();
		System.out.println("current date ....."+currentDate);
		return professionalTaxRepository.findAllProfessionalTax( companyId);
	}

	/**
	 * to get ProfessionalTax object from database based on stateId
	 */
	@Override
	public ProfessionalTax findProfessionalTaxOfEmployee( long stateId, Long companyId ) {
		
	//	DateUtils dateUtils = new DateUtils();
		//Date currentDate = dateUtils.getCurrentDate();
		List<ProfessionalTax> profTaxList = professionalTaxRepository.findProfessionalTaxOfEmployees(stateId, companyId);
		ProfessionalTax professionalTax = null;
		if (profTaxList != null && profTaxList.size() > 0) {
			professionalTax = profTaxList.get(0);
		}
		return professionalTax;
	}
	
	

	/**
	 * to get ProfessionalTax object from database based on proTaxId (Primary Key)
	 */
	@Override
	public ProfessionalTax findProfessionalTax(long professionalHeadId ,Long companyId) {
		return professionalTaxRepository.findProfessionalTax(professionalHeadId ,companyId);
	}

	@Override
	public List<State> findAllState(Long companyId) {
		// TODO Auto-generated method stub
		return stateRepository.findStateById(companyId);
	}
   @Transactional
	@Override
	public void updateByActiveStatus(ProfessionalTaxInfo professionalTaxInfo) {
		// TODO Auto-generated method stub
		professionalTaxRepository.updateByStatus(professionalTaxInfo.getProfessionalTaxInfoId(), professionalTaxInfo.getActiveStatus());
	}


@Override
public ProfessionalTax findProfessionalTaxOfEmployeeByProcessMonth(long stateId, Long companyId, String payPsMonth) {
	 Date psMonthDate = DateUtils.getDateForProcessMonth(payPsMonth);
	 Calendar cal = Calendar.getInstance();
     cal.setTime(psMonthDate);
     cal.set(Calendar.MINUTE, 0);
     cal.set(Calendar.SECOND, 0);   
     cal.set(Calendar.HOUR_OF_DAY, 0);
     cal.set(Calendar.DATE, cal.getActualMaximum(Calendar.DATE));
    	List<ProfessionalTax> ptObj =  professionalTaxRepository.findProfessionalTaxOfEmployee(cal.getTime(), stateId,companyId );
	
	if(ptObj!= null && ptObj.size() >1) {
		//throw new PayRollProcessException("Multiple Record found in PT");
	}else if(ptObj!= null && ptObj.size() == 1) {
		return ptObj.get(0);
	}
	return null;
}

   @Transactional
   @Override
   public ProfessionalTax saveProfessionalTaxs(ProfessionalTax existingProfessionalTax, ProfessionalTax professionalTax) {
	    professionalTaxRepository.save(existingProfessionalTax);
	   return professionalTaxRepository.save(professionalTax);
   }

   public boolean payrollCheck(Date date ,Long finencialYearId ,Long comapnyId) {
	   if(finencialYearId == null) {
		   logger.info("finencialYearId is null");
	   }
	   
	     List<String> psMonths= payrollControlRepository.processControlByFinancialYearId(finencialYearId);
	     
	     if(psMonths == null || psMonths.size() == 0) {
			   logger.info("psMonths is null");
		   }
	     
	     Calendar calInput = Calendar.getInstance();
	     calInput.setTime(date);
	     calInput.set(Calendar.MINUTE, 0);
	     calInput.set(Calendar.SECOND, 0);   
	     calInput.set(Calendar.HOUR_OF_DAY, 0);
	     calInput.set(Calendar.DATE ,1);
	     
	     
	     List<String> psMonthsToBeCheck = getProcessMonths(psMonths , calInput.getTime());
	     
	     List<String> payrollCreatedPsMonth = reportPayOutRepository.payrollCreationCheck(comapnyId ,psMonthsToBeCheck);
	    
	     if(payrollCreatedPsMonth!=null && payrollCreatedPsMonth.size()>0) {
	    	 logger.info(payrollCreatedPsMonth.size()+" payrollCreatedPsMonth "+payrollCreatedPsMonth);
	    	 return true; 
	     }else {
	     return false;
	     }
   }

   public List<String> getProcessMonths(List<String> psMonthsList , Date ipDate) {
	   
	   List <Date> processDateList = new ArrayList<Date>();
	   for(String processMonth:  psMonthsList){
		
	   String psMonthArray[] = processMonth.split("-");
	   String month = psMonthArray[0];
	   int year =  Integer.valueOf(psMonthArray[1]);

	   Calendar cal = Calendar.getInstance();
	     cal.set(Calendar.DATE ,1);
	     cal.set(Calendar.MINUTE, 0);
	     cal.set(Calendar.SECOND, 0);   
	     cal.set(Calendar.HOUR_OF_DAY, 0);
	     cal.set(Calendar.YEAR,year);
         cal.set(Calendar.MONTH , monthToValue(month) );
	   
       processDateList.add(cal.getTime());
	   }
	   
	   
	   List<Date> filteredList = processDateList.stream()    // converting the list to stream
               .filter(dates -> dates.equals(ipDate) || dates.after(ipDate))   // filter the stream to create a new stream
               .collect(Collectors.toList());
	   
	   List<String> dateRemainingList =new  ArrayList<String>();
	   for(Date dateRemaining :  filteredList) {
		   String formatedPsMonth=DateUtils.getDateStringWithYYYYMMDD(dateRemaining);
		   dateRemainingList.add(formatedPsMonth);
	   }
	   
	   ////String psMonthsCommaSep = "'" + String.join("','", dateRemainingList) + "'";
	   ////String psMonthsCommaSepformated =psMonthsCommaSep.toUpperCase();
	   ////logger.info("psMonthsCommaSepformated  "+psMonthsCommaSepformated);
	   
	   return dateRemainingList;
   }
   
  
   public int monthToValue(String months) {
	   switch(months) 
       { 
           case "JAN": 
        	   return 0;
           case "FEB": 
        	   return 1;
           case "MAR": 
        	   return 2; 
           case "APR": 
        	   return 3; 
           case "MAY": 
        	   return 4; 
           case "JUN": 
        	   return 5;
           case "JUL": 
        	   return 6; 
           case "AUG": 
        	   return 7; 
           case "SEP": 
        	   return 8; 
           case "OCT": 
        	   return 9; 
           case "NOV": 
        	   return 10;
           case "DEC": 
        	   return 11;
       } 
	   return 0;
   }

@Override
public List<ProfessionalTaxDTO> findAllPTDescByDate(Long companyId) {
	List<ProfessionalTax> ptList = professionalTaxRepository.findAllPTDescByDate(companyId);
	if(ptList!=null) {
		return	ptList.stream().map((ProfessionalTax pt) ->
		new ProfessionalTaxDTO(pt.getProfessionalHeadId(), pt.getState().getStateName(), pt.getState().getStateId(), pt.getUserId(), pt.getDateCreated(), pt.getActiveStatus(), pt.getEffectiveStartDate(), pt.getEffectiveEndDate(), pt.getCompany().getCompanyId(), pt.getUserIdUpdate(), pt.getProfessionalTaxInfos())
		).collect(Collectors.toList());   
	}
	return null;
}
   
@Override
  public List<ProfessionalTaxDTO>findAllPTStateIdDescByDate(Long companyId , Long professionalHeadId){
	  List<ProfessionalTax> ptListByStateId = professionalTaxRepository.findAllPTStateIdDescByDate(companyId ,professionalHeadId);
		if(ptListByStateId!=null) {
			return	ptListByStateId.stream().map((ProfessionalTax pt) ->
			new ProfessionalTaxDTO(pt.getProfessionalHeadId(), pt.getState().getStateName(), pt.getState().getStateId(), pt.getUserId(), pt.getDateCreated(), pt.getActiveStatus(), pt.getEffectiveStartDate(), pt.getEffectiveEndDate(), pt.getCompany().getCompanyId(), pt.getUserIdUpdate(), pt.getProfessionalTaxInfos())
			).collect(Collectors.toList());   
		}
		return null;
}

  public ProfessionalTax findAllPTProfessionalTaxIdDescByDate(Long professionalTaxId ,Long companyId){
	  ProfessionalTax professionalTax = professionalTaxRepository.findPTProfessionalTaxByHeadId(professionalTaxId,companyId);
//		if(ptListByStateId!=null) {
//			return	ptListByStateId.stream().map((ProfessionalTaxInfo pt) ->
//			new ProfessionalTaxInfoDTO(pt.getProfessionalTaxInfoId(), pt.getCategory(), pt.getDateCreated(), pt.getLimitFrom(),pt.getLimitTo(),pt.getTaxAmount(),pt.getUserId(),pt.getUserIdUpdate(),pt.getActiveStatus())
//			).collect(Collectors.toList());   
//		}
		return professionalTax;
}
  
  public boolean payrollCheckObj(String psMonth, Long finencialYearId, Long comapnyId, Long employeeId, BigDecimal grossPay) {
	 
	   String psMonthArray[] = psMonth.split("-");
	   String month = psMonthArray[0];
	   int year =  Integer.valueOf(psMonthArray[1]);

	   Calendar calInput = Calendar.getInstance();
	   calInput.set(Calendar.DATE ,1);
	   calInput.set(Calendar.MINUTE, 0);
	   calInput.set(Calendar.SECOND, 0);   
	   calInput.set(Calendar.HOUR_OF_DAY, 0);
	   calInput.set(Calendar.YEAR,year);
	   calInput.set(Calendar.MONTH , monthToValue(month) );
	  
	  List<String> psMonths= payrollControlRepository.processControlByFinancialYearId(finencialYearId);
	  
	  List<String> psMonthsToBeCheck = getProcessMonths(psMonths , calInput.getTime());
	     
	  List<ReportPayOut> payrollCreatedReportPayOut = reportPayOutRepository.payrollCreationCheckObj(comapnyId,employeeId ,psMonthsToBeCheck);
	     
	  if(payrollCreatedReportPayOut !=null && payrollCreatedReportPayOut.size()>0) {
		 
		  List<ReportPayOut> payrollCreatedReportPayOutFilter =  payrollCreatedReportPayOut.stream()
		  .filter(rp -> rp.getGrossSalary().compareTo(grossPay) == 1 )
		  .collect(Collectors.toList());
		
		  if(payrollCreatedReportPayOutFilter !=null && payrollCreatedReportPayOutFilter.size()>0) {
			  logger.info(" payrollCreatedReportPayOutFilter size"+ payrollCreatedReportPayOutFilter.size());
			  return true;
		  }
	  }
	  
	  return false;
  }
  
}



