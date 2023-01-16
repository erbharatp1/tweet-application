package com.hrms.org.payrollprocess.loan;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.csipl.hrms.common.enums.StandardDeductionEnum;
import com.csipl.hrms.model.payroll.OneTimeDeduction;
import com.csipl.hrms.model.payroll.OneTimeEarningDeduction;
import com.csipl.hrms.model.payrollprocess.PayOut;
import com.csipl.hrms.model.payrollprocess.PayOutPK;
import com.csipl.hrms.model.payrollprocess.ReportPayOut;

public class OneTimeCalculation {
	 private final Logger logger = LoggerFactory.getLogger(OneTimeCalculation.class);
	 
	 public  List<PayOut>  calculateOneTimeEarning( List<OneTimeEarningDeduction> oneTimeEarningList , ReportPayOut reportPayOut, String processMonth ) {
		
		  BigDecimal earningAmount = new BigDecimal(0);
		  List<PayOut> earningPayouts= new ArrayList<>();
		 for (OneTimeEarningDeduction oneTimeEarning : oneTimeEarningList) {
			
			 earningAmount = earningAmount.add( oneTimeEarning.getAmount() );
			 PayOut payOut = new PayOut();
				PayOutPK pk = new PayOutPK();
				pk.setEmployeeId(reportPayOut.getId().getEmployeeId());
				pk.setProcessMonth(processMonth);
				pk.setPayHeadId(oneTimeEarning.getPayHeadId());
				payOut.setId(pk);
				payOut.setAmount(oneTimeEarning.getAmount());
				earningPayouts.add(payOut);
		 }
		    
			reportPayOut.setOtherEarning(earningAmount);
			
		 return earningPayouts;
	 }
	 
/*	 public  PayOut  calculateOneTimeDeduction( List<OneTimeEarningDeduction> oneTimeDeductionList , ReportPayOut reportPayOut, String processMonth ) {
			
		  BigDecimal deductionAmount = new BigDecimal(0);
		 for (OneTimeEarningDeduction oneTimeDeduction : oneTimeDeductionList) {
			
			 deductionAmount = deductionAmount.add( oneTimeDeduction.getAmount() );
		
		 
		 }
		    PayOut payOut = new PayOut();
			PayOutPK pk = new PayOutPK();
			pk.setEmployeeId(reportPayOut.getId().getEmployeeId());
			pk.setProcessMonth(processMonth);
			pk.setPayHeadId(StandardDeductionEnum.Others.getStandardDeduction());
			payOut.setId(pk);
			payOut.setAmount(deductionAmount);
			reportPayOut.setOtherDeduction(deductionAmount);
			
		 return payOut;
	 }*/
	 
	 
	 public  List<PayOut>  calculateOneTimeDeduction( List<OneTimeEarningDeduction> oneTimeDeductionList , ReportPayOut reportPayOut, String processMonth ) {
			
		  BigDecimal deductionAmount = new BigDecimal(0);
		  List<PayOut> deductionPayouts= new ArrayList<>();
		 for (OneTimeEarningDeduction oneTimeDeduction : oneTimeDeductionList) {
			
			 deductionAmount = deductionAmount.add( oneTimeDeduction.getAmount() );
			  PayOut payOut = new PayOut();
				PayOutPK pk = new PayOutPK();
				pk.setEmployeeId(reportPayOut.getId().getEmployeeId());
				pk.setProcessMonth(processMonth);
				pk.setPayHeadId(oneTimeDeduction.getPayHeadId());
				payOut.setId(pk);
				payOut.setAmount(oneTimeDeduction.getAmount());
				deductionPayouts.add(payOut);
		 
		
		 
		 }
		    
			reportPayOut.setOtherDeduction(deductionAmount);
			
		 return deductionPayouts;
	 }
}
