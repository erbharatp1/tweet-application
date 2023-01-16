package com.csipl.hrms.service.adaptor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.map.HashedMap;

import com.csipl.hrms.dto.payroll.TdsSummaryChangeDTO;
import com.csipl.hrms.dto.payroll.TdsTransactionDTO;
import com.csipl.hrms.model.payroll.TdsSummaryChange;

public class TdsSummaryChangeAdaptor implements Adaptor<TdsSummaryChangeDTO, TdsSummaryChange> {

	@Override
	public List<TdsSummaryChange> uiDtoToDatabaseModelList(List<TdsSummaryChangeDTO> uiobj) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<TdsSummaryChangeDTO> databaseModelToUiDtoList(List<TdsSummaryChange> dbobj) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TdsSummaryChange uiDtoToDatabaseModel(TdsSummaryChangeDTO tdsSummaryChangeDto) {
		TdsSummaryChange tdsSummaryChange = new TdsSummaryChange();
		tdsSummaryChange.setYearlyGross(tdsSummaryChangeDto.getYearlyGross());
		tdsSummaryChange.setYearlyGrossFy(tdsSummaryChangeDto.getYearlyGrossFy());
		tdsSummaryChange.setOtherIncome(tdsSummaryChangeDto.getOtherIncome());
		tdsSummaryChange.setPreEmpIncome(tdsSummaryChangeDto.getPreEmpIncome());
		tdsSummaryChange.setNetYearlyIncome(tdsSummaryChangeDto.getNetYearlyIncome());
		tdsSummaryChange.setExempStandard(tdsSummaryChangeDto.getExempStandard());
		tdsSummaryChange.setExempPfAmount(tdsSummaryChangeDto.getExempPfAmount());
		tdsSummaryChange.setExempPtAmount(tdsSummaryChangeDto.getExempPtAmount());
		tdsSummaryChange.setExemptedTotalIncome(tdsSummaryChangeDto.getExemptedTotalIncome());
		tdsSummaryChange.setTotalIncomeProfessionalTax(tdsSummaryChangeDto.getTotalIncomeProfessionalTax());
		tdsSummaryChange.setChapter6a(tdsSummaryChangeDto.getChapter6a());
		tdsSummaryChange.setSection10(tdsSummaryChangeDto.getSection10());
		tdsSummaryChange.setSection24(tdsSummaryChangeDto.getSection24());
		tdsSummaryChange.setTotalDeductionIncome(tdsSummaryChangeDto.getTotalDeductionIncome());
		tdsSummaryChange.setTaxableIncome(tdsSummaryChangeDto.getTaxableIncome());
		tdsSummaryChange.setTax(tdsSummaryChangeDto.getTax());
		tdsSummaryChange.setSurcharge(tdsSummaryChangeDto.getSurcharge());
		tdsSummaryChange.setEducationCess(tdsSummaryChangeDto.getEducationCess());
		tdsSummaryChange.setTotalTax(tdsSummaryChangeDto.getTotalTax());
		tdsSummaryChange.setNetTaxYearly(tdsSummaryChangeDto.getNetTaxYearly());
		tdsSummaryChange.setNetTaxMonthly(tdsSummaryChangeDto.getNetTaxMonthly());
		tdsSummaryChange.setIncomeAfterExemptions(tdsSummaryChangeDto.getIncomeAfterExemptions());
		tdsSummaryChange.setProfessionalTax(tdsSummaryChangeDto.getProfessionalTax());
		tdsSummaryChange.setProvidentFund(tdsSummaryChangeDto.getProvidentFund());
		tdsSummaryChange.setPotalTax(tdsSummaryChangeDto.getPotalTax());
		tdsSummaryChange.setTaxOnIncome(tdsSummaryChangeDto.getTaxOnIncome());
		tdsSummaryChange.setPreviousSurcharge(tdsSummaryChangeDto.getPreviousSurcharge());
		tdsSummaryChange.setPreviousEducationCess(tdsSummaryChangeDto.getPreviousEducationCess());
		tdsSummaryChange.setEducationCessPer(tdsSummaryChangeDto.getEducationCessPer());
		tdsSummaryChange.setSurchargePer(tdsSummaryChangeDto.getSurchargePer());
		tdsSummaryChange.setSection84ARebateTax(tdsSummaryChangeDto.getSection84ARebateTax());
		tdsSummaryChange.setTotal80cAmount(tdsSummaryChangeDto.getTotal80cAmount());
		tdsSummaryChange.setSection84ARebateAmount(tdsSummaryChangeDto.getSection84ARebateAmount());
		
		tdsSummaryChange.setTotalGrossPaid(tdsSummaryChangeDto.getTotalGrossPaid() != null ? tdsSummaryChangeDto.getTotalGrossPaid(): new BigDecimal(0.00));
		tdsSummaryChange.setTotalGrossToBePaid(tdsSummaryChangeDto.getTotalGrossToBePaid() != null ? tdsSummaryChangeDto.getTotalGrossToBePaid(): new BigDecimal(0.00));
		tdsSummaryChange.setTotalTaxPaid(tdsSummaryChangeDto.getTotalTaxPaid() != null ? tdsSummaryChangeDto.getTotalTaxPaid(): new BigDecimal(0.00));
		tdsSummaryChange.setTotalOneTimeEarningPaid(tdsSummaryChangeDto.getTotalOneTimeEarningPaid()!= null ? tdsSummaryChangeDto.getTotalOneTimeEarningPaid(): new BigDecimal(0.00));
		return tdsSummaryChange;
	}

	@Override
	public TdsSummaryChangeDTO databaseModelToUiDto(TdsSummaryChange tdsSummaryChange) {
		TdsSummaryChangeDTO tdsSummaryChangeDto = new TdsSummaryChangeDTO();
		tdsSummaryChangeDto.setYearlyGross(tdsSummaryChange.getYearlyGross());
		tdsSummaryChangeDto.setYearlyGrossFy(tdsSummaryChange.getYearlyGrossFy());
		tdsSummaryChangeDto.setOtherIncome(tdsSummaryChange.getOtherIncome());
		tdsSummaryChangeDto.setPreEmpIncome(tdsSummaryChange.getPreEmpIncome());
		tdsSummaryChangeDto.setNetYearlyIncome(tdsSummaryChange.getNetYearlyIncome());
		tdsSummaryChangeDto.setExempStandard(tdsSummaryChange.getExempStandard());
		tdsSummaryChangeDto.setExempPfAmount(tdsSummaryChange.getExempPfAmount());
		tdsSummaryChangeDto.setExempPtAmount(tdsSummaryChange.getExempPtAmount());
		tdsSummaryChangeDto.setExemptedTotalIncome(tdsSummaryChange.getExemptedTotalIncome());
		tdsSummaryChangeDto.setTotalIncomeProfessionalTax(tdsSummaryChange.getTotalIncomeProfessionalTax());
		tdsSummaryChangeDto.setChapter6a(tdsSummaryChange.getChapter6a());
		tdsSummaryChangeDto.setSection10(tdsSummaryChange.getSection10());
		tdsSummaryChangeDto.setSection24(tdsSummaryChange.getSection24());
		tdsSummaryChangeDto.setTotalDeductionIncome(tdsSummaryChange.getTotalDeductionIncome());
		tdsSummaryChangeDto.setTaxableIncome(tdsSummaryChange.getTaxableIncome());
		tdsSummaryChangeDto.setTax(tdsSummaryChange.getTax());
		tdsSummaryChangeDto.setSurcharge(tdsSummaryChange.getSurcharge());
		tdsSummaryChangeDto.setEducationCess(tdsSummaryChange.getEducationCess());
		tdsSummaryChangeDto.setTotalTax(tdsSummaryChange.getTotalTax());
		tdsSummaryChangeDto.setNetTaxYearly(tdsSummaryChange.getNetTaxYearly());
		tdsSummaryChangeDto.setNetTaxMonthly(tdsSummaryChange.getNetTaxMonthly());
		tdsSummaryChangeDto.setIncomeAfterExemptions(tdsSummaryChange.getIncomeAfterExemptions());
		tdsSummaryChangeDto.setProfessionalTax(tdsSummaryChange.getProfessionalTax());
		tdsSummaryChangeDto.setProvidentFund(tdsSummaryChange.getProvidentFund());
		tdsSummaryChangeDto.setPotalTax(tdsSummaryChange.getPotalTax());
		tdsSummaryChangeDto.setTaxOnIncome(tdsSummaryChange.getTaxOnIncome());
		tdsSummaryChangeDto.setPreviousSurcharge(tdsSummaryChange.getPreviousSurcharge());
		tdsSummaryChangeDto.setPreviousEducationCess(tdsSummaryChange.getPreviousEducationCess());
		tdsSummaryChangeDto.setFinancialYearId(tdsSummaryChange.getFinancialYearId());
		tdsSummaryChangeDto.setEducationCessPer(tdsSummaryChange.getEducationCessPer());
		tdsSummaryChangeDto.setSurchargePer(tdsSummaryChange.getSurchargePer());
		tdsSummaryChangeDto.setSection84ARebateTax(tdsSummaryChange.getSection84ARebateTax());
		tdsSummaryChangeDto.setTotal80cAmount(tdsSummaryChange.getTotal80cAmount());
		tdsSummaryChangeDto.setSection84ARebateAmount(tdsSummaryChange.getSection84ARebateAmount());
		
		tdsSummaryChangeDto.setTotalGrossPaid(tdsSummaryChange.getTotalGrossPaid()!=null ? tdsSummaryChange.getTotalGrossPaid(): new BigDecimal(0.00));
		tdsSummaryChangeDto.setTotalGrossToBePaid(tdsSummaryChange.getTotalGrossToBePaid() !=null ? tdsSummaryChange.getTotalGrossToBePaid(): new BigDecimal(0.00));
		tdsSummaryChangeDto.setTotalTaxPaid(tdsSummaryChange.getTotalTaxPaid() !=null ? tdsSummaryChange.getTotalTaxPaid(): new BigDecimal(0.00) );
		tdsSummaryChangeDto.setTotalOneTimeEarningPaid(tdsSummaryChange.getTotalOneTimeEarningPaid() !=null ? tdsSummaryChange.getTotalOneTimeEarningPaid(): new BigDecimal(0.00));
		
		return tdsSummaryChangeDto;
	}

	public List<TdsSummaryChangeDTO> databaseModelToUiDtoLists(List<Object[]> tdsSummaryChange) {

		List<TdsSummaryChangeDTO> TdsSummaryChangeDTOList = new ArrayList<>();
		for (Object[] tdsSummary : tdsSummaryChange) {
			TdsSummaryChangeDTO tdsSummaryChangeDto = new TdsSummaryChangeDTO();
			
			String employeeCode = tdsSummary[0] != null ? (String) tdsSummary[0] : null;
			String employeeName = tdsSummary[1] != null ? (String) tdsSummary[1] : null;
			String desginationName = tdsSummary[2] != null ? (String) tdsSummary[2] : null;
			String departmentName = tdsSummary[3] != null ? (String) tdsSummary[3] : null;
			Date dateOfJoining = tdsSummary[4] != null ? (Date) tdsSummary[4] : null;
			String PanNumber = tdsSummary[5] != null ? (String) tdsSummary[5] : null;
			
			BigDecimal d = tdsSummary[6] != null ? (new BigDecimal(tdsSummary[6].toString())) : null;
			BigDecimal ptd = tdsSummary[7] != null ? (new BigDecimal(tdsSummary[7].toString())) : null;
			BigDecimal pts = tdsSummary[8] != null ? (new BigDecimal(tdsSummary[8].toString())) : null;
			
			BigDecimal yearlyGross = tdsSummary[9] != null ? (new BigDecimal(tdsSummary[9].toString())) : null;
			
			BigDecimal yearlyGrossFy = tdsSummary[10] != null ? (new BigDecimal(tdsSummary[10].toString())) : null;
			BigDecimal otherIncome = tdsSummary[11] != null ? (new BigDecimal(tdsSummary[11].toString())) : null;
			BigDecimal preEmpIncome = tdsSummary[12] != null ? (new BigDecimal(tdsSummary[12].toString())) : null;
			
			BigDecimal netYearlyIncome = tdsSummary[13] != null ? (new BigDecimal(tdsSummary[13].toString())) : null;
			BigDecimal exampStandard = tdsSummary[14] != null ? (new BigDecimal(tdsSummary[14].toString())) : null;
			BigDecimal exampPfAmount = tdsSummary[15] != null ? (new BigDecimal(tdsSummary[15].toString())) : null;
			BigDecimal exampPtAmount = tdsSummary[16] != null ? (new BigDecimal(tdsSummary[16].toString())) : null;
			BigDecimal exampEsicAmount = tdsSummary[17] != null ? (new BigDecimal(tdsSummary[17].toString())) : null;
			
			BigDecimal examptedTotalIncome = tdsSummary[18] != null ? (new BigDecimal(tdsSummary[18].toString())) : null;
			BigDecimal TotalIncomeProfessionalTax = tdsSummary[19] != null ? (new BigDecimal(tdsSummary[19].toString())) : null;
			BigDecimal chapter6A = tdsSummary[20] != null ? (new BigDecimal(tdsSummary[20].toString())) : null;
			BigDecimal section10 = tdsSummary[21] != null ? (new BigDecimal(tdsSummary[21].toString())) : null;
			BigDecimal section24 = tdsSummary[22] != null ? (new BigDecimal(tdsSummary[22].toString())) : null;
			
			BigDecimal totalDeductionIncome = tdsSummary[23] != null ? (new BigDecimal(tdsSummary[23].toString())) : null;
			BigDecimal taxableIncome = tdsSummary[24] != null ? (new BigDecimal(tdsSummary[24].toString())) : null;
			BigDecimal tax = tdsSummary[25] != null ? (new BigDecimal(tdsSummary[25].toString())) : null;
			BigDecimal surcharge = tdsSummary[26] != null ? (new BigDecimal(tdsSummary[26].toString())) : null;
			
			BigDecimal educationCess = tdsSummary[27] != null ? (new BigDecimal(tdsSummary[27].toString())) : null;
			BigDecimal surchargePer = tdsSummary[28] != null ? (new BigDecimal(tdsSummary[28].toString())) : null;
			BigDecimal educationCessPer = tdsSummary[29] != null ? (new BigDecimal(tdsSummary[29].toString())) : null;
			
			BigDecimal section84ARebateTax = tdsSummary[30] != null ? (new BigDecimal(tdsSummary[30].toString())) : null;
			BigDecimal totalTax = tdsSummary[31] != null ? (new BigDecimal(tdsSummary[31].toString())) : null;
			BigDecimal netTaxYearly = tdsSummary[32] != null ? (new BigDecimal(tdsSummary[32].toString())) : null;
			BigDecimal netTaxMonthly = tdsSummary[33] != null ? (new BigDecimal(tdsSummary[33].toString())) : null;
			
			BigDecimal incomeAfterExemption = tdsSummary[34] != null ? (new BigDecimal(tdsSummary[34].toString())) : null;
			BigDecimal professionTax = tdsSummary[35] != null ? (new BigDecimal(tdsSummary[35].toString())) : null;
			BigDecimal providentFund = tdsSummary[36] != null ? (new BigDecimal(tdsSummary[36].toString())) : null;
			BigDecimal postalTax = tdsSummary[37] != null ? (new BigDecimal(tdsSummary[37].toString())) : null;
			BigDecimal taxOnIncome = tdsSummary[38] != null ? (new BigDecimal(tdsSummary[38].toString())) : null;
			BigDecimal preSurcharhe = tdsSummary[39] != null ? (new BigDecimal(tdsSummary[39].toString())) : null;
			BigDecimal preEduCess = tdsSummary[40] != null ? (new BigDecimal(tdsSummary[40].toString())) : null;
			
			BigDecimal total80cAmount = tdsSummary[41] != null ? (new BigDecimal(tdsSummary[41].toString())) : null;
			BigDecimal section84ARebateAmount = tdsSummary[42] != null ? (new BigDecimal(tdsSummary[42].toString())) : null;
			BigDecimal totalGrossPaid = tdsSummary[43] != null ? (new BigDecimal(tdsSummary[43].toString())) : null;
			BigDecimal totalGrossPaidToBePaid = tdsSummary[44] != null ? (new BigDecimal(tdsSummary[44].toString())) : null;
			BigDecimal oneTimeEarningPaid = tdsSummary[45] != null ? (new BigDecimal(tdsSummary[45].toString())) : null;
			BigDecimal declareProcessMonth = tdsSummary[46] != null ? (new BigDecimal(tdsSummary[46].toString())) : null;
			BigDecimal totalTaxPaid = tdsSummary[47] != null ? (new BigDecimal(tdsSummary[47].toString())) : null;
			BigDecimal totalTaxPaidToBePaid = tdsSummary[48] != null ? (new BigDecimal(tdsSummary[48].toString())) : null;
		 
			
			tdsSummaryChangeDto.setEmployeeCode(employeeCode);
			tdsSummaryChangeDto.setEmployeeName(employeeName);
			tdsSummaryChangeDto.setDesignation(desginationName);
			tdsSummaryChangeDto.setDepartment(departmentName);
			tdsSummaryChangeDto.setDateOFJoining(dateOfJoining.toString());
			tdsSummaryChangeDto.setPanNumber(PanNumber);
			tdsSummaryChangeDto.setYearlyGross(yearlyGross);
			tdsSummaryChangeDto.setYearlyGrossFy(yearlyGrossFy);
			tdsSummaryChangeDto.setOtherIncome(otherIncome);
			tdsSummaryChangeDto.setPreEmpIncome(preEmpIncome);
			tdsSummaryChangeDto.setNetYearlyIncome(netYearlyIncome);
			tdsSummaryChangeDto.setExempStandard(exampStandard);
			tdsSummaryChangeDto.setExempPfAmount(exampPfAmount);
			tdsSummaryChangeDto.setExempPtAmount(exampPtAmount);
			tdsSummaryChangeDto.setExemptedTotalIncome(examptedTotalIncome);
			tdsSummaryChangeDto.setTotalIncomeProfessionalTax(TotalIncomeProfessionalTax);
			tdsSummaryChangeDto.setChapter6a(chapter6A);
			tdsSummaryChangeDto.setSection10(section10);
			tdsSummaryChangeDto.setSection24(section24);
			tdsSummaryChangeDto.setTotalDeductionIncome(totalDeductionIncome);
			tdsSummaryChangeDto.setTaxableIncome(taxableIncome);
			tdsSummaryChangeDto.setTax(tax);
			tdsSummaryChangeDto.setSurcharge(surcharge);
			 
			tdsSummaryChangeDto.setEducationCess(educationCess);
			tdsSummaryChangeDto.setTotalTax(totalTax);
			tdsSummaryChangeDto.setNetTaxYearly(netTaxYearly);
			tdsSummaryChangeDto.setNetTaxMonthly(netTaxMonthly);
			
			tdsSummaryChangeDto.setIncomeAfterExemptions(incomeAfterExemption);
			tdsSummaryChangeDto.setProfessionalTax(professionTax);
			tdsSummaryChangeDto.setProvidentFund(providentFund);
			tdsSummaryChangeDto.setPotalTax(postalTax);
			tdsSummaryChangeDto.setTaxOnIncome(taxOnIncome);
			tdsSummaryChangeDto.setPreviousSurcharge(preSurcharhe);
			tdsSummaryChangeDto.setPreviousEducationCess(preEduCess);
//			tdsSummaryChangeDto.setFinancialYearId(tdsSummaryChange.getFinancialYearId());
			tdsSummaryChangeDto.setEducationCessPer(educationCessPer);
			tdsSummaryChangeDto.setSurchargePer(surchargePer);
			 
			tdsSummaryChangeDto.setSection84ARebateTax(section84ARebateTax);
			tdsSummaryChangeDto.setTotal80cAmount(total80cAmount);
			tdsSummaryChangeDto.setSection84ARebateAmount(section84ARebateAmount);
			
			tdsSummaryChangeDto.setTotalGrossPaid(totalGrossPaid);
			tdsSummaryChangeDto.setTotalGrossToBePaid(totalGrossPaidToBePaid);
			tdsSummaryChangeDto.setTotalTaxPaid(totalTaxPaid);
			tdsSummaryChangeDto.setTotalOneTimeEarningPaid(oneTimeEarningPaid);
			
			TdsSummaryChangeDTOList.add(tdsSummaryChangeDto);
			
		}
		return TdsSummaryChangeDTOList;

	}

	public Map<String, List<TdsTransactionDTO>> databaseModelToMap(List<Object[]> employeeTdsDeclaration) {
		
		Map<String, List<TdsTransactionDTO>> map = new HashMap<>();
		List<TdsTransactionDTO> tdsTransactionDTOList = new ArrayList<>();
		
		BigDecimal amount = new BigDecimal(0.0);
		BigDecimal totalAmount = new BigDecimal(0.0);
		BigDecimal limitAmount = new BigDecimal(0.0);
		BigDecimal approvedAmountTransation =    new BigDecimal(0.0);
		BigDecimal lastAmount =    new BigDecimal(0.0);
		BigDecimal lastApprovedAmount =    new BigDecimal(0.0);
	 String empCode="";
		for (Object[] tdsDeclaration : employeeTdsDeclaration) {
			 
			TdsTransactionDTO tdsTransactionDTO = new TdsTransactionDTO();
			
			String employeeCode = tdsDeclaration[0] != null ? (String) tdsDeclaration[0] : null;
			String employeeName = tdsDeclaration[1] != null ? (String) tdsDeclaration[1] : null;
			String desginationName = tdsDeclaration[2] != null ? (String) tdsDeclaration[2] : null;
			String departmentName = tdsDeclaration[3] != null ? (String) tdsDeclaration[3] : null;
			Date dateOfJoining = tdsDeclaration[4] != null ? (Date) tdsDeclaration[4] : null;
			String PanNumber = tdsDeclaration[5] != null ? (String) tdsDeclaration[5] : null;
			
			Integer sectionId = tdsDeclaration[6] != null ? (Integer) tdsDeclaration[6] : null;
			String tdsSectionName = tdsDeclaration[7] != null ? (String) tdsDeclaration[7] : null;
			String tdsGroupName = tdsDeclaration[8] != null ? (String) tdsDeclaration[8] : null;
		 
			BigDecimal maxLimit = tdsDeclaration[9] != null ? (new BigDecimal(tdsDeclaration[9].toString())) : null;
			BigDecimal investmentAmount = tdsDeclaration[10] != null ? (new BigDecimal(tdsDeclaration[10].toString())) : null;
			BigDecimal approvedAmount = tdsDeclaration[11] != null ? (new BigDecimal(tdsDeclaration[11].toString())) : null;
			
			BigDecimal groupLimit80C = tdsDeclaration[12] != null ? (new BigDecimal(tdsDeclaration[12].toString())) : null;
			BigDecimal otherIncome = tdsDeclaration[13] != null ? (new BigDecimal(tdsDeclaration[13].toString())) : null;
			BigDecimal preIncome = tdsDeclaration[14] != null ? (new BigDecimal(tdsDeclaration[14].toString())) : null;
			BigDecimal exptPfAmount = tdsDeclaration[15] != null ? (new BigDecimal(tdsDeclaration[15].toString())) : null;
			
			BigDecimal totalOtherIncome  =otherIncome.add(preIncome);
			
			tdsTransactionDTO.setEmployeeCode(employeeCode);
			tdsTransactionDTO.setEmployeeName(employeeName);
			tdsTransactionDTO.setDesignation(desginationName);
			tdsTransactionDTO.setDepartment(departmentName);
			tdsTransactionDTO.setDateOFJoining(dateOfJoining.toString());
			tdsTransactionDTO.setPanNumber(PanNumber);
			
			tdsTransactionDTO.setTdsSectionSetupId(Long.valueOf(sectionId));
			tdsTransactionDTO.setTdsSectionName(tdsSectionName); 
			tdsTransactionDTO.setTdsGroupName(tdsGroupName);
			tdsTransactionDTO.setMaxLimit(maxLimit);
			tdsTransactionDTO.setInvestmentAmount(investmentAmount);
			tdsTransactionDTO.setApprovedAmount(approvedAmount);
			tdsTransactionDTO.setTotalOtherIncome(totalOtherIncome);
			
			if(empCode!="" && !empCode.equals(employeeCode)) {
				totalAmount=new BigDecimal(0.0);
				lastAmount= new BigDecimal(0.0);
			}
			
			
			if(groupLimit80C!=null) {
				totalAmount=totalAmount.add(investmentAmount);
				
				groupLimit80C=groupLimit80C.subtract(exptPfAmount);
				limitAmount=groupLimit80C;
				if (groupLimit80C.compareTo(totalAmount) >= 0) {
					approvedAmountTransation =  investmentAmount;
					if (groupLimit80C.compareTo(totalAmount) == 0) {
						lastAmount=lastAmount.add(totalAmount);
					}
				}
				else {
					if (lastAmount.compareTo(lastApprovedAmount) == 0) {
						lastAmount=totalAmount.subtract( investmentAmount);
						if (groupLimit80C.compareTo(lastAmount) >= 0) {
							approvedAmountTransation= limitAmount.subtract(lastAmount);
						}
					}else {
						approvedAmountTransation= new BigDecimal(0.0);
					}
					
				}
				tdsTransactionDTO.setApprovedAmount(approvedAmountTransation);
			}
			
			empCode=employeeCode;
			
			if(map.containsKey(employeeCode)) {
				tdsTransactionDTOList.add(tdsTransactionDTO);
				map.put(employeeCode, tdsTransactionDTOList);
			} else {
//				if(map.size()>0)  
//				totalAmount=new BigDecimal(0.0);
				
				approvedAmountTransation= new BigDecimal(0.0);
				tdsTransactionDTOList = new ArrayList<>();
				tdsTransactionDTOList.add(tdsTransactionDTO);
				map.put(employeeCode, tdsTransactionDTOList);
				//tdsTransactionDTOList = new ArrayList<>();

			}
			
		}
		return map;
	}
}
