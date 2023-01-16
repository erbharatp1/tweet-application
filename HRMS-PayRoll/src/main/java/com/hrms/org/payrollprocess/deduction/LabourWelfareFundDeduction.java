package com.hrms.org.payrollprocess.deduction;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import com.csipl.hrms.common.enums.StandardDeductionEnum;
import com.csipl.hrms.model.employee.Employee;
import com.csipl.hrms.model.payroll.LabourWelfareFund;
import com.csipl.hrms.model.payroll.LabourWelfareFundInfo;
import com.csipl.hrms.model.payrollprocess.PayOut;
import com.csipl.hrms.model.payrollprocess.PayOutPK;
import com.csipl.hrms.model.payrollprocess.PayrollControl;
import com.hrms.org.payrollprocess.dto.PayRollProcessDTO;
import com.hrms.org.payrollprocess.dto.PayRollProcessHDDTO;
import com.hrms.org.payrollprocess.util.PayRollProcessUtil;

public class LabourWelfareFundDeduction extends Calculation {

	@Override
	public ArrayList<PayOut> getCalculation(List<PayRollProcessDTO> lwfList,
			PayRollProcessHDDTO payRollProcessHDDTO, PayrollControl payrollControl ) {

		ArrayList<PayOut> listDeduction = new ArrayList<>();
		LabourWelfareFund lwf= payRollProcessHDDTO.getLabourWelfareFund();
		if ( lwf != null && lwfList.size()>0 ) {
		
			BigDecimal grossAmountamount=new BigDecimal(0.0);
			//BigDecimal grossAmountamount = payRollProcessHDDTO.getTotalGrossSalary();
			//BigDecimal grossAmountamount = payRollProcessHDDTO.getReportPayOut().getTotalEarning();
			BigDecimal perMonthAmount  = new BigDecimal(0);
			BigDecimal employerWelFareAmount  = new BigDecimal(0);
			boolean lwfFlag = false;
			
			for (PayRollProcessDTO obj : lwfList) {
				grossAmountamount = grossAmountamount.add(obj.getPayOut().getAmount()); // calculated
				//baseEarning = baseEarning.add( obj.getPayStructure().getAmount() ); // original
			}
	
			
			List<LabourWelfareFundInfo> lwfinfoList =   lwf.getLabourWelfareFundInfos();
			//lwfInfo.getGradeId().equals(lwfList.get(0).getPayStructure().getPayStructureHd().getGrade().getGradesId())
			for (LabourWelfareFundInfo lwfInfo : lwfinfoList) {

				if (lwfInfo.getGradeId().equals(payRollProcessHDDTO.getEmployee().getGradesId())) {
					if (isBetween(grossAmountamount, lwfInfo)) {
						perMonthAmount = lwfInfo.getWelFareAmount();
						employerWelFareAmount= lwfInfo.getEmployerWelFareAmount();
						lwfFlag = true;
						break;
					}

				} /*else if (lwfInfo.getg.equals(payRollProcessHDDTO.getReportPayOut().getGender())) {
					if (isBetween(grossAmountamount, lwfInfo)) {
						perMonthAmount = lwfInfo.getTaxAmount();
						lwfFlag = true;
						break;
					}

				}*/

			}
			
			
		  	
			if( lwfFlag  ) {
				PayRollProcessUtil util = new PayRollProcessUtil();
				PayOut payOutEmpLWF =new PayOut();
				PayOutPK pk = new PayOutPK();
				pk.setEmployeeId(payRollProcessHDDTO.getListPayRollProcessDTOs().get(0).getPayOut().getEmployee()
						.getEmployeeId());
				pk.setProcessMonth(payRollProcessHDDTO.getPayMonth());
			    pk.setPayHeadId( StandardDeductionEnum.LWF.getStandardDeduction() );
			    payOutEmpLWF.setId(pk);
				Employee employee = new Employee();
				employee.setEmployeeId( payRollProcessHDDTO.getListPayRollProcessDTOs().get(0).getPayOut().getEmployee()
						.getEmployeeId() );
				payOutEmpLWF.setAmount(perMonthAmount);
				payOutEmpLWF.setEmployee( employee );
				
				util.fillDeductionValueInReportPayOut( payRollProcessHDDTO.getReportPayOut() , StandardDeductionEnum.LWF.getStandardDeduction() , perMonthAmount );
				listDeduction.add(payOutEmpLWF);
				
			}
			
			if( lwfFlag  ) {
				PayRollProcessUtil util = new PayRollProcessUtil();
				PayOut payOutEmployerLWF =new PayOut();
				PayOutPK pk = new PayOutPK();
				pk.setEmployeeId(payRollProcessHDDTO.getListPayRollProcessDTOs().get(0).getPayOut().getEmployee()
						.getEmployeeId());
				pk.setProcessMonth(payRollProcessHDDTO.getPayMonth());
			    pk.setPayHeadId( StandardDeductionEnum.LWF_Employer.getStandardDeduction() );
			    payOutEmployerLWF.setId(pk);
				Employee employee = new Employee();
				employee.setEmployeeId( payRollProcessHDDTO.getListPayRollProcessDTOs().get(0).getPayOut().getEmployee()
						.getEmployeeId() );
				payOutEmployerLWF.setAmount(employerWelFareAmount);
				payOutEmployerLWF.setEmployee( employee );
				
				util.fillDeductionValueInReportPayOut( payRollProcessHDDTO.getReportPayOut() , StandardDeductionEnum.LWF_Employer.getStandardDeduction() , employerWelFareAmount );
				listDeduction.add(payOutEmployerLWF);
				
			}
			
		}
		
		return listDeduction;
	}

	
	    boolean isBetween( BigDecimal grossAmountamount, LabourWelfareFundInfo lwfInfo ){
	    	  return ( lwfInfo.getLimitFrom().compareTo( grossAmountamount ) <= 0 && lwfInfo.getLimitTo().compareTo( grossAmountamount ) > 0 );
		}
}
