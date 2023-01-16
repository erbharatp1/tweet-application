package com.csipl.hrms.service.adaptor;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import com.csipl.common.services.dropdown.DropDownCache;
import com.csipl.hrms.common.constant.StatusMessage;
import com.csipl.hrms.common.enums.DropDownEnum;
import com.csipl.hrms.common.enums.StandardEarningEnum;
import com.csipl.hrms.dto.payroll.PayHeadDTO;
import com.csipl.hrms.model.common.Company;
import com.csipl.hrms.model.common.Groupg;
import com.csipl.hrms.model.payroll.PayHead;
import com.csipl.hrms.model.payroll.TdsSlab;
import com.csipl.hrms.model.payroll.TdsSlabHd;

public class PayHeadAdaptor implements Adaptor<PayHeadDTO, PayHead> {

	

	@Override
	public List<PayHead> uiDtoToDatabaseModelList(List<PayHeadDTO> payHeadDtoList) {
		List<PayHead> payHead = new ArrayList<PayHead>();
		for (PayHeadDTO payHeadDto : payHeadDtoList) {

			payHead.add(uiDtoToDatabaseModel(payHeadDto));
		}
		return payHead;
	}

	@Override
	public List<PayHeadDTO> databaseModelToUiDtoList(List<PayHead> payHeadList) {
		List<PayHeadDTO> payHeadDtoList = new ArrayList<PayHeadDTO>();
		for (PayHead payHead : payHeadList) {

			payHeadDtoList.add(databaseModelToUiDto(payHead));
		}
		return payHeadDtoList;
	}

	public List<PayHeadDTO> databaseModelToUiDtoList1(List<PayHead> payHeadList) {
		List<PayHeadDTO> payHeadDtoList = new ArrayList<PayHeadDTO>();
		for (PayHead payHead : payHeadList) {

			payHeadDtoList.add(databaseModelToUiDto(payHead,new BigDecimal(0.0)));
		}
		return payHeadDtoList;
	}
	
	
	@Override
	public PayHead uiDtoToDatabaseModel(PayHeadDTO payHeadDto) {
		PayHead payHead = new PayHead();
		Company company = new Company();
		//Groupg group = new Groupg();
		company.setCompanyId(payHeadDto.getCompanyId());
		payHead.setCompany(company);
		payHead.setPayHeadId(payHeadDto.getPayHeadId());
		payHead.setPayHeadName(payHeadDto.getPayHeadName());
		payHead.setEarningDeduction(payHeadDto.getEarningDeduction());
		payHead.setExpenseType(payHeadDto.getExpenseType());
		payHead.setIncomeType(payHeadDto.getIncomeType());
		payHead.setIsApplicableOnGratuaty(payHeadDto.getIsApplicableOnGratuaty());
		payHead.setIsApplicableOnEsi(payHeadDto.getIsApplicableOnEsi());
		payHead.setIsApplicableOnPf(payHeadDto.getIsApplicableOnPf());
		payHead.setIsApplicableOnPt(payHeadDto.getIsApplicableOnPt());
		payHead.setIsApplicableOnLWS(payHeadDto.getIsApplicableOnLWS());
		payHead.setUserId(payHeadDto.getUserId());
		payHead.setUserIdUpdate(payHeadDto.getUserIdUpdate());
		payHead.setActiveStatus(payHeadDto.getActiveStatus());
		payHead.setDateUpdate(new Date());
		payHead.setPayHeadFlag(payHeadDto.getPayHeadFlag());
		payHead.setBasedOn(payHeadDto.getBasedOn());
		payHead.setAmount(payHeadDto.getAmount());
		payHead.setPercentage(payHeadDto.getPercentage());
payHead.setPriority(payHeadDto.getPriority());
		payHead.setHeadType(payHeadDto.getHeadType());
		/*group.setGroupId(1L);          //comment for future enhancement
		payHead.setGroupg(group);*/
		if (payHeadDto.getPayHeadId() == null)
			payHead.setDateCreated(new Date());
		else
			payHead.setDateCreated(payHeadDto.getDateCreated());
		return payHead;
	}

	@Override
	public PayHeadDTO databaseModelToUiDto(PayHead payHead) {
		PayHeadDTO payHeadDto = new PayHeadDTO();

		if (payHead.getHeadType().equals("One Time")) {
			payHeadDto.setUserId(payHead.getUserId());
			payHeadDto.setHeadType(payHead.getHeadType());
			payHeadDto.setPayHeadName(payHead.getPayHeadName());
			payHeadDto.setActiveStatus(payHead.getActiveStatus());
			payHeadDto.setDateCreated(payHead.getDateCreated());
			payHeadDto.setPayHeadId(payHead.getPayHeadId());
			payHeadDto.setCompanyId(payHead.getCompany().getCompanyId());
			payHeadDto.setEarningDeduction(payHead.getEarningDeduction());
			// payHeadDto.setEarningDeductionValue(payHead.getEarningDeduction());
			payHeadDto.setExpenseTypeValue(DropDownCache.getInstance()
					.getDropDownValue(DropDownEnum.expense.getDropDownName(), payHead.getExpenseType()));
			payHeadDto.setIncomeTypeValue(DropDownCache.getInstance()
					.getDropDownValue(DropDownEnum.incomeType.getDropDownName(), payHead.getIncomeType()));
			payHeadDto.setEarningDeductionValue(DropDownCache.getInstance()
					.getDropDownValue(DropDownEnum.earningDeduction.getDropDownName(), payHead.getEarningDeduction()));

		} else {
			StringBuilder aplicableOn = new StringBuilder();

			payHeadDto.setPayHeadId(payHead.getPayHeadId());
			payHeadDto.setPayHeadName(payHead.getPayHeadName());
			payHeadDto.setExpenseType(payHead.getExpenseType());
			payHeadDto.setIncomeType(payHead.getIncomeType());
			payHeadDto.setEarningDeduction(payHead.getEarningDeduction());
			payHeadDto.setBasedOn(payHead.getBasedOn());
			if (payHead.getBasedOn().equals("Fixed")) {
				payHeadDto.setBasedOnValue(payHead.getBasedOn());
				// System.out.println("Based ON Inside Full Time========"+payHead.getBasedOn());
			} else if (payHead.getBasedOn() != null || payHead.getBasedOn().equalsIgnoreCase("null")) {
				if (payHead.getBasedOn().equals("Basic") || payHead.getBasedOn().equals("Gross")) {
					payHeadDto.setBasedOnValue(payHead.getPercentage() + " % of  " + payHead.getBasedOn());
					// payHeadDto.setBasedOn(payHead.getPercentage().toString().substring(0,payHead.getPercentage().toString().length()-3
					// ) + "% " + payHead.getBasedOn());
				}
			}
			payHeadDto.setActiveStatus(payHead.getActiveStatus());
			payHeadDto.setAmount(payHead.getAmount());
			payHeadDto.setPercentage(payHead.getPercentage());
			payHeadDto.setHeadType(payHead.getHeadType());
			payHeadDto.setExpenseTypeValue(DropDownCache.getInstance()
					.getDropDownValue(DropDownEnum.expense.getDropDownName(), payHead.getExpenseType()));
			payHeadDto.setIncomeTypeValue(DropDownCache.getInstance()
					.getDropDownValue(DropDownEnum.incomeType.getDropDownName(), payHead.getIncomeType()));
			payHeadDto.setEarningDeductionValue(DropDownCache.getInstance()
					.getDropDownValue(DropDownEnum.earningDeduction.getDropDownName(), payHead.getEarningDeduction()));
			int index = 0;
			if (payHead.getIsApplicableOnGratuaty() != null
					&& payHead.getIsApplicableOnGratuaty().equals(StatusMessage.YES_CODE)) {
				if (index == 0) {
					aplicableOn.append("Gratuaty");
					index++;
				}
			}
			if (payHead.getIsApplicableOnEsi() != null
					&& payHead.getIsApplicableOnEsi().equals(StatusMessage.YES_CODE)) {
				if (index == 0) {
					aplicableOn.append("ESI");
					index++;
				} else {
					aplicableOn.append(",ESI");
				}
			}
			if (payHead.getIsApplicableOnPf() != null && payHead.getIsApplicableOnPf().equals(StatusMessage.YES_CODE)) {
				if (index == 0) {
					aplicableOn.append("PF");
					index++;
				} else {
					aplicableOn.append(",PF");
				}
			}
			if (payHead.getIsApplicableOnPt() != null && payHead.getIsApplicableOnPt().equals(StatusMessage.YES_CODE)) {
				if (index == 0) {
					aplicableOn.append("PT");
					index++;
				} else {
					aplicableOn.append(",PT");
				}
			}
			if (payHead.getIsApplicableOnLWS() != null && payHead.getIsApplicableOnLWS().equals(StatusMessage.YES_CODE)) {
				if (index == 0) {
					aplicableOn.append("LWF");
					index++;
				} else {
					aplicableOn.append(",LWF");
				}
			}
			if ((payHead.getIsApplicableOnPt() != null && payHead.getIsApplicableOnPt().equals(StatusMessage.NO_CODE))
					&& (payHead.getIsApplicableOnGratuaty() != null
							&& payHead.getIsApplicableOnGratuaty().equals(StatusMessage.NO_CODE))
					&& (payHead.getIsApplicableOnPf() != null
							&& payHead.getIsApplicableOnPf().equals(StatusMessage.NO_CODE))
					&& (payHead.getIsApplicableOnPt() != null
							&& payHead.getIsApplicableOnEsi().equals(StatusMessage.NO_CODE))  && payHead.getIsApplicableOnLWS().equals(StatusMessage.NO_CODE)) {

				aplicableOn.append("--");
			}
			payHeadDto.setApplicableOn(aplicableOn.toString());
			payHeadDto.setIsApplicableOnGratuaty(payHead.getIsApplicableOnGratuaty());
			payHeadDto.setIsApplicableOnEsi(payHead.getIsApplicableOnEsi());
			payHeadDto.setIsApplicableOnPf(payHead.getIsApplicableOnPf());
			payHeadDto.setIsApplicableOnPt(payHead.getIsApplicableOnPt());
			payHeadDto.setPayHeadFlag(payHead.getPayHeadFlag());
			payHeadDto.setIsApplicableOnLWS(payHead.getIsApplicableOnLWS());
		 
			payHeadDto.setUserId(payHead.getUserId());

			payHeadDto.setDateCreated(payHead.getDateCreated());
			payHeadDto.setPriority(payHead.getPriority());
		}

		return payHeadDto;
	}

	public List<PayHeadDTO> payStractureStanderedCalculation(List<PayHead> payHeadList, BigDecimal grossPay) {
		List<PayHeadDTO> payHeadDtoList = new ArrayList<PayHeadDTO>();
		BigDecimal basicAmount = new BigDecimal(0);
		BigDecimal totalAmount = new BigDecimal(0);
		BigDecimal beforeTotalAmount = new BigDecimal(0);
		
		
		
		// need to remove
		payHeadList.forEach(payHead->System.out.println(payHead.getPriority()+"------------"+payHead.getPayHeadName()));
		
		
		// Sort thDS slab
		List<PayHead> payHeadSortedList = sortPayHeads(payHeadList);
		
	
		
		// need to remove
		payHeadSortedList.forEach(payHead->System.out.println(payHead.getPriority()+"-----------"+payHead.getPayHeadName()));
		
		
		// find out basic salary 
		for (PayHead payHead : payHeadSortedList) {

			if (("Gross").equals(payHead.getBasedOn()) && payHead.getPayHeadId().longValue() == StandardEarningEnum.BasicSalary.getStandardEarning()) 
				basicAmount = (payHead.getPercentage().multiply(grossPay)).divide(new BigDecimal(100));
		}

		boolean calculatePayheadFlag=false;
		int count=0;
		
		for (PayHead payHead : payHeadSortedList) {
			count++;
			BigDecimal amount = new BigDecimal(0);
			
			if(("Gross").equals(payHead.getBasedOn())) {
				calculatePayheadFlag=true;
				amount = (payHead.getPercentage().multiply(grossPay)).divide(new BigDecimal(100));
				totalAmount=totalAmount.add(amount);
			}

			else if (("Basic").equals(payHead.getBasedOn())) {
				calculatePayheadFlag=true;
				
				amount = (payHead.getPercentage().multiply(basicAmount)).divide(new BigDecimal(100));
				totalAmount=totalAmount.add(amount);

			}

			else if (("Fixed").equals(payHead.getBasedOn())) {
				calculatePayheadFlag=true;
				amount=payHead.getAmount();
				totalAmount=totalAmount.add(amount);
			}
			
			if(calculatePayheadFlag)
			{
				     calculatePayheadFlag=false;
					if(grossPay.compareTo(totalAmount)>0 && count<payHeadSortedList.size()) {
						payHead.setAmount(amount);
						beforeTotalAmount=beforeTotalAmount.add(amount);
						payHeadDtoList.add(databaseModelToUiDto(payHead, grossPay));
					}
					else {
						payHead.setAmount(grossPay.subtract(beforeTotalAmount));
						beforeTotalAmount=beforeTotalAmount.add(grossPay.subtract(beforeTotalAmount));
						payHeadDtoList.add(databaseModelToUiDto(payHead, grossPay));
						break;
					}
					
				
			}
			
			
		}



		return payHeadDtoList;
	}

	private List<PayHead> sortPayHeads(List<PayHead> payHeads) {
	//	List<TdsSlab> tdsSlabs = tdsSlabHd.getTdsSlabs();
		Collections.sort(payHeads, new Comparator<PayHead>() {

			@Override
			public int compare(PayHead payHead1, PayHead payHead2) {
				Long limitTo1 = payHead1.getPriority();
				Long limitTo2 = payHead2.getPriority();
				int diff = limitTo1.compareTo(limitTo2);
				return diff;
			}

		});
		return payHeads;
	}
	
	
	
	public PayHeadDTO databaseModelToUiDto(PayHead payHead, BigDecimal grossPay) {
		PayHeadDTO payHeadDto = new PayHeadDTO();
 
		payHeadDto.setPayHeadId(payHead.getPayHeadId());
		payHeadDto.setPayHeadName(payHead.getPayHeadName());
		
		payHeadDto.setEarningDeduction(payHead.getEarningDeduction());
		payHeadDto.setIncomeType(payHead.getIncomeType());
		payHeadDto.setPayHeadFlag(payHead.getPayHeadFlag());
		payHeadDto.setUserId(payHead.getUserId());
		payHeadDto.setActiveStatus(payHead.getActiveStatus());
		payHeadDto.setDateCreated(payHead.getDateCreated());
		payHeadDto.setActiveStatus(payHead.getActiveStatus());
		payHeadDto.setBasedOn(payHead.getBasedOn());
  		payHeadDto.setIsApplicableOnGratuaty(payHead.getIsApplicableOnGratuaty());
		payHeadDto.setIsApplicableOnEsi(payHead.getIsApplicableOnEsi());
		payHeadDto.setIsApplicableOnPf(payHead.getIsApplicableOnPf());
		payHeadDto.setIsApplicableOnPt(payHead.getIsApplicableOnPt());
		payHeadDto.setIsApplicableOnLWS(payHead.getIsApplicableOnLWS());
		if(payHead.getAmount()!=null)
 		payHeadDto.setAmount(round(payHead.getAmount(),2));
 		payHeadDto.setPriority(payHead.getPriority());
		return payHeadDto;
	}
 

	public static BigDecimal round(BigDecimal value, int places) {
	    if (places < 0) throw new IllegalArgumentException();

	    value = value.setScale(places, RoundingMode.HALF_UP);
	    return value;
	}
}
