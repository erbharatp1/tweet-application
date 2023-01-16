package com.hrms.org.payrollprocess.deduction;



import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import com.csipl.hrms.model.employee.PayStructure;
import com.csipl.hrms.model.employee.PayStructureHd;
import com.csipl.hrms.model.payroll.LabourWelfareFund;
import com.csipl.hrms.model.payrollprocess.PayOut;
import com.csipl.hrms.model.payrollprocess.PayrollControl;
import com.hrms.org.payrollprocess.dto.PayRollProcessDTO;
import com.hrms.org.payrollprocess.dto.PayRollProcessHDDTO;





public class CalculationPayHead  {
	
	
	
	
 
    
  public List<PayOut>  calculationDeduction(PayRollProcessHDDTO payRollProcessHDDTO, PayrollControl payrollControl, PayStructureHd payStructureHd ){
	  List<PayOut> listDeduction = new ArrayList<PayOut>();
	
		   List<PayRollProcessDTO> epfList = new ArrayList<PayRollProcessDTO>();
		   List<PayRollProcessDTO> esiList = new ArrayList<PayRollProcessDTO>();
		   List<PayRollProcessDTO> ptList = new ArrayList<PayRollProcessDTO>();
		   List<PayRollProcessDTO> lwfList = new ArrayList<PayRollProcessDTO>();
		   BigDecimal ptAmount=new BigDecimal(0.0);
		   
		//finding the paystructre list which will be applicable on epf and esi  ,pt,lwf 
		   
		for(PayRollProcessDTO payRollProcessDTO : payRollProcessHDDTO.getListPayRollProcessDTOs()) {
			
			PayStructure payStructure=payRollProcessDTO.getPayStructure();
			

			if( ("Y").equalsIgnoreCase(payStructure.getPayHead().getIsApplicableOnEsi() )) 

				
			esiList.add( payRollProcessDTO );
					

				
			 if (("Y").equalsIgnoreCase( payStructure.getPayHead().getIsApplicableOnPf()) ) 
				
				epfList.add( payRollProcessDTO );
			
			 
			 
			 
			 if (("Y").equalsIgnoreCase (payStructure.getPayHead().getIsApplicableOnPt() )) {
				 
				 //ptAmount =ptAmount.add(payStructure.getAmount());
					
				ptList.add( payRollProcessDTO );
			 }
			 
			 
            if (("Y").equalsIgnoreCase (payStructure.getPayHead().getIsApplicableOnLWS() )) {
				 
				 //ptAmount =ptAmount.add(payStructure.getAmount());
					
            	lwfList.add( payRollProcessDTO );
		    }
				
		}
		


			
			/*if ( payStructureHd.getIsNoPFDeduction() == null  
			|| ( payStructureHd.getIsNoPFDeduction() != null &&  payStructureHd.getIsNoPFDeduction().equals("") ) 
			|| ( payStructureHd.getIsNoPFDeduction() != null &&  payStructureHd.getIsNoPFDeduction().equals("N") ) )*/
		System.out.println(payRollProcessHDDTO.getReportPayOut().getIsNoPFDeduction());
		
		if ( payRollProcessHDDTO.getReportPayOut().getIsNoPFDeduction() == null  
				|| ( payRollProcessHDDTO.getReportPayOut().getIsNoPFDeduction() != null &&  payRollProcessHDDTO.getReportPayOut().getIsNoPFDeduction().equals("") ) 
				|| ( payRollProcessHDDTO.getReportPayOut().getIsNoPFDeduction() != null &&  payRollProcessHDDTO.getReportPayOut().getIsNoPFDeduction().equals("N") ) ) {
		EpfCalculation epfCalculation= new EpfCalculation();	
		listDeduction.addAll( epfCalculation.getCalculation(epfList,payRollProcessHDDTO, payrollControl) );
		
		}
	
		
		
		
		if ( payRollProcessHDDTO.getReportPayOut().getIsNoESIDeduction() == null  
				|| ( payRollProcessHDDTO.getReportPayOut().getIsNoESIDeduction() != null &&  payRollProcessHDDTO.getReportPayOut().getIsNoESIDeduction().equals("") ) 
				|| ( payRollProcessHDDTO.getReportPayOut().getIsNoESIDeduction() != null &&  payRollProcessHDDTO.getReportPayOut().getIsNoESIDeduction().equals("N") ) ) {
	
		EsiCalculation esiCalculation =new EsiCalculation();	
		listDeduction.addAll( esiCalculation.getCalculation(esiList,payRollProcessHDDTO, payrollControl) );
		}
		
		
		ProfessionalTaxDeduction ptCalculation = new ProfessionalTaxDeduction();
		listDeduction.addAll(ptCalculation.getCalculation(ptList, payRollProcessHDDTO, payrollControl));
		LabourWelfareFundDeduction lwFundCalculation = new LabourWelfareFundDeduction();
		
		listDeduction.addAll(lwFundCalculation.getCalculation(lwfList, payRollProcessHDDTO, payrollControl));
		
		
	  return listDeduction;
  }



}
