package com.csipl.hrms.service.payroll;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.csipl.hrms.common.util.DateUtils;
import com.csipl.hrms.model.payroll.TdsGroup;
import com.csipl.hrms.model.payroll.TdsGroupSetup;
import com.csipl.hrms.model.payrollprocess.FinancialYear;
import com.csipl.hrms.service.payroll.repository.FinancialYearRepository;
import com.csipl.hrms.service.payroll.repository.InvestmentRepository;

@Service("investmentService")
public class InvestmentServiceImpl implements InvestmentService {
 	@Autowired
	  private InvestmentRepository investmentRepository;

 	@Autowired
	FinancialYearRepository financialYearRepository;
 	
	/**
	 * to get List of TdsGroup Objects from database based on currentDate
	 */
	@Override
	public List<TdsGroupSetup> getInvestmentList(Long companyId, FinancialYear financialYear) {
//		DateUtils dateUtils=new DateUtils();
//		Date currentDate=dateUtils.getCurrentDate();
		
//		DateUtils dateUtils=new DateUtils();
//		Date currentDate=dateUtils.getCurrentDate();
//		FinancialYear financialYear=financialYearRepository.getFinancialYear(currentDate, companyId);
		
 		return investmentRepository.findAllInvestment(companyId, financialYear.getFinancialYearId());
  	}
	/**
	 * Save OR update tdsGroup object  into Database 
	 */
	@Override
	public void save(TdsGroup tdsGroup) {
		//investmentRepository.save(tdsGroup);
	}
	/**
	 * to get TdsGroup Object from database based on tdsGroupId (Primary Key)
	 */
	@Override
	public TdsGroup getInvestment(long tdsGroupId) {
//		TdsGroup tdsGroup = investmentRepository.findInvestment(tdsGroupId);
//		return tdsGroup;
		
		return null;
	}
}
