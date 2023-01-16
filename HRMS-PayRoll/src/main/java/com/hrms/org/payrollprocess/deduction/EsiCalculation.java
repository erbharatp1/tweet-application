package com.hrms.org.payrollprocess.deduction;


import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import com.csipl.hrms.common.enums.StandardDeductionEnum;
import com.csipl.hrms.common.util.DateUtils;
import com.csipl.hrms.model.employee.Employee;
import com.csipl.hrms.model.payroll.Esi;
import com.csipl.hrms.model.payrollprocess.PayOut;
import com.csipl.hrms.model.payrollprocess.PayOutPK;
import com.csipl.hrms.model.payrollprocess.PayrollControl;
import com.hrms.org.payrollprocess.dto.PayRollProcessDTO;
import com.hrms.org.payrollprocess.dto.PayRollProcessHDDTO;
import com.hrms.org.payrollprocess.util.PayRollProcessUtil;

import ch.qos.logback.classic.Logger;

public class EsiCalculation extends Calculation{

	
	

	
	@Override
	public ArrayList<PayOut> getCalculation( List<PayRollProcessDTO> esiList , PayRollProcessHDDTO payRollProcessHDDTO, PayrollControl payrollControl ) {
		// TODO Auto-generated method stub
		
		
	    String processMonth=payrollControl.getProcessMonth();
	    DateUtils.getDateForProcessMonth(processMonth);
		
			
		BigDecimal amount = new BigDecimal(0);
		BigDecimal baseEarning = new BigDecimal(0);
		ArrayList<PayOut> listDeduction = new ArrayList<>();
		
			for (PayRollProcessDTO obj : esiList) {
				amount = amount.add(obj.getPayOut().getAmount());
				baseEarning = baseEarning.add( obj.getPayStructure().getAmount() ); // original
			}
		
            Esi esi=payRollProcessHDDTO.getEsi();
			
	   /*     if(esi.getMaxGrossLimit().compareTo(amount)==-1  ) {
	    	   
	 // 		BigDecimal earningStep1 = amount.divide(payDays, 2, RoundingMode.HALF_UP ).multiply(dayWorked).multiply(epf.getEmployeePer());
		    		BigDecimal employee_contribution_ESI  = amount.multiply(esi.getEmployeePer()).divide(new BigDecimal(100),2,RoundingMode.HALF_UP);
		     		BigDecimal employer_Contribution_ESI  = amount.multiply(esi.getEmployerPer()).divide(new BigDecimal(100),2,RoundingMode.HALF_UP);
		    	
	     	   	setEmployeeStateInsuranceValue(payRollProcessHDDTO, listDeduction, employee_contribution_ESI ,StandardDeductionEnum.ESI_Employee_Contribution.getStandardDeduction());  
	     		setEmployeeStateInsuranceValue(payRollProcessHDDTO, listDeduction, employer_Contribution_ESI ,StandardDeductionEnum.ESI_Employer_Contribution.getStandardDeduction());  
		       		             
	       
	          return listDeduction;
	         
	           	
	        }
	        else*/ 
            Boolean esiLifeCycleFlag=false;
            if(payRollProcessHDDTO.getEsiLifeCycleEndDate()!=null) {
            	esiLifeCycleFlag=( DateUtils.getDateForProcessMonth(processMonth)  ).compareTo((payRollProcessHDDTO.getEsiLifeCycleEndDate()))<=0;
                System.out.println("esiLifeCycleFlag is true");
            }
            //esi max gross limit 210000 
            if ( esi.getMaxGrossLimit().compareTo( baseEarning )==0 || esi.getMaxGrossLimit().compareTo( baseEarning) == 1|| esiLifeCycleFlag) {
	       	 
	      		BigDecimal employee_contribution_ESI  = amount.multiply(esi.getEmployeePer()).divide(new BigDecimal(100),2,RoundingMode.HALF_UP);
	     		BigDecimal employer_Contribution_ESI  = amount.multiply(esi.getEmployerPer()).divide(new BigDecimal(100),2,RoundingMode.HALF_UP);
	     		
	     		setEmployeeStateInsuranceValue(payRollProcessHDDTO, listDeduction, employee_contribution_ESI ,StandardDeductionEnum.ESI_Employee_Contribution.getStandardDeduction());  
	     		setEmployeeStateInsuranceValue(payRollProcessHDDTO, listDeduction, employer_Contribution_ESI ,StandardDeductionEnum.ESI_Employer_Contribution.getStandardDeduction());  
		            
	       
	          return listDeduction;
		
	        }
			
	      
		return listDeduction;
		
	   }
	
	private void setEmployeeStateInsuranceValue(PayRollProcessHDDTO payRollProcessHDDTO,
			ArrayList<PayOut> listDeduction, BigDecimal amount_contribution_ESI ,long standardDeduction ) {
		PayOut payOutEmpESI =new PayOut();
		PayRollProcessUtil util = new PayRollProcessUtil();
		PayOutPK pk = new PayOutPK();
		pk.setEmployeeId(payRollProcessHDDTO.getListPayRollProcessDTOs().get(0).getPayOut().getEmployee()
				.getEmployeeId());
		pk.setProcessMonth(payRollProcessHDDTO.getPayMonth());
		pk.setPayHeadId( standardDeduction );
		payOutEmpESI.setId(pk);
		
		Employee employee = new Employee();
		employee.setEmployeeId( payRollProcessHDDTO.getListPayRollProcessDTOs().get(0).getPayOut().getEmployee()
				.getEmployeeId() );
		
		payOutEmpESI.setAmount(amount_contribution_ESI);
		payOutEmpESI.setEmployee( employee );
		
		util.fillDeductionValueInReportPayOut( payRollProcessHDDTO.getReportPayOut() , standardDeduction, amount_contribution_ESI );
		
		listDeduction.add(payOutEmpESI);
	}
		

	}
	

	


	
	


