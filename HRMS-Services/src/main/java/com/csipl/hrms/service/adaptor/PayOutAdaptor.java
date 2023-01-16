 package com.csipl.hrms.service.adaptor;

 import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

 import com.csipl.hrms.dto.payrollprocess.PayOutDTO;
import com.csipl.hrms.dto.payrollprocess.PayOutListDTO;
import com.csipl.hrms.model.payrollprocess.PayOut;
import com.csipl.hrms.model.payrollprocess.PayOutPK;


public class PayOutAdaptor {
  	public PayOutListDTO databaseModelToUiDtoList(List<PayOut> payOutList) {
		 //List<PayOutDTO> payOutDtoList=new ArrayList<PayOutDTO>();
		 PayOutListDTO payOutListDto=new PayOutListDTO();
		 
		  List<PayOutDTO> earningPayHeads = new ArrayList<PayOutDTO>();
		  List<PayOutDTO> deductionPayHeads = new ArrayList<PayOutDTO>();
		 
		for (PayOut payOut : payOutList) {
			 if(payOut.getEarningDeduction()!=null) {
				 if ( payOut.getEarningDeduction().equals("EA") ) {
					 earningPayHeads.add(databaseModelToUiDto(payOut)); 
				 } else {
					 deductionPayHeads.add(databaseModelToUiDto(payOut)); 
				 }
			 }
		} 
		payOutListDto.setDeductionPayHead(deductionPayHeads);
		payOutListDto.setEarningPayHead(earningPayHeads);
 		return payOutListDto;
	}
 
	public PayOutDTO databaseModelToUiDto(PayOut payOut) {
		PayOutDTO payOutDto=new PayOutDTO();
		if(payOut.getId()!=null) {
		payOutDto.setPayHeadId(payOut.getId().getPayHeadId());
		payOutDto.setProcessMonth(payOut.getId().getProcessMonth());
		}
			payOutDto.setAmount(payOut.getAmount());
			payOutDto.setPayHeadName(payOut.getPayHeadName());
			if(payOut.getEmployee()!=null)
			payOutDto.setEmployeeId(payOut.getEmployee().getEmployeeId());
			
  		return payOutDto;
	}

	public List<PayOutDTO> payOutObjectListTopayOutDtoListConversion(List<Object[]> payOutDtoObjList) {
		 List<PayOutDTO> payOutDTOList=new  ArrayList<PayOutDTO>();
 		for (Object[] payOutObj : payOutDtoObjList) {
			PayOutDTO payOutDto=new PayOutDTO(); 
			Integer employeeId=(Integer) (payOutObj[0] !=null ? (Integer) payOutObj[0] :0);
			
			Integer payHeadId=(Integer) (payOutObj[1] !=null ? (Integer) payOutObj[1] :0);

			///Long c=payOutObj[1] !=null ? (Long)payOutObj[1] :0l;
			String processMonth=payOutObj[2] !=null ? (String)payOutObj[2] :"";
			BigDecimal amount=payOutObj[3]!=null? (BigDecimal)payOutObj[3]:null;	
			String payHeadName=payOutObj[4] !=null ? (String)payOutObj[4] :"";
			String earningDeduction=payOutObj[5] !=null ? (String)payOutObj[5] :"";

			payOutDto.setEmployeeId(Long.valueOf(employeeId));
			payOutDto.setPayHeadId(Long.valueOf(payHeadId));
			payOutDto.setProcessMonth(processMonth);
			payOutDto.setAmount(amount);
			payOutDto.setPayHeadName(payHeadName);
			payOutDto.setEarningDeduction(earningDeduction);
			payOutDTOList.add(payOutDto);
 		}
		
 		return payOutDTOList;
	}

}
