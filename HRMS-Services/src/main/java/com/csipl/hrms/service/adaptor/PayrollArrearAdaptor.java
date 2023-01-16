package com.csipl.hrms.service.adaptor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.csipl.hrms.model.employee.Employee;
import com.csipl.hrms.model.payrollprocess.ArrearPayOut;
import com.csipl.hrms.model.payrollprocess.ArrearPayOutPK;
import com.csipl.hrms.model.payrollprocess.ArrearReportPayOut;
import com.csipl.hrms.model.payrollprocess.ArrearReportPayOutPK;
import com.csipl.hrms.model.payrollprocess.PayOut;
import com.csipl.hrms.model.payrollprocess.PayOutPK;
import com.csipl.hrms.model.payrollprocess.ReportPayOut;
import com.csipl.hrms.model.payrollprocess.ReportPayOutPK;

public class PayrollArrearAdaptor  {

 	public ArrearReportPayOut reportPayOutListToArrearReportPayOutConversion(List<ReportPayOut> reportPayOutList) {
 		ArrearReportPayOut arrearReportPayOut=new ArrearReportPayOut();
 		
 		for (ReportPayOut reportPayOut : reportPayOutList) {
 			ArrearReportPayOutPK arrearReportPayOutPK=new ArrearReportPayOutPK();
 			arrearReportPayOutPK.setEmployeeId(reportPayOut.getId().getEmployeeId());
 			
 			arrearReportPayOutPK.setProcessMonth(reportPayOut.getId().getProcessMonth());
 			arrearReportPayOut.setId(arrearReportPayOutPK);
 			arrearReportPayOut.setProcessDate(reportPayOut.getProcessDate());
 			arrearReportPayOut.setCompanyId(reportPayOut.getCompanyId());
 			arrearReportPayOut.setEmployeeCode(reportPayOut.getEmployeeCode());
 			arrearReportPayOut.setDepartmentId(reportPayOut.getDepartmentId());
 			arrearReportPayOut.setCityId(reportPayOut.getCityId());
 			arrearReportPayOut.setName(reportPayOut.getBankName());
 			arrearReportPayOut.setBankName(reportPayOut.getBankName());
 			arrearReportPayOut.setAccountNumber(reportPayOut.getAccountNumber());
 			arrearReportPayOut.setDateOfJoining(reportPayOut.getDateOfJoining());
 			arrearReportPayOut.setPresense(reportPayOut.getPresense());
 			arrearReportPayOut.setWeekoff(reportPayOut.getWeekoff());
 			arrearReportPayOut.setPublicholidays(reportPayOut.getPublicholidays());
 			arrearReportPayOut.setPaidleave(reportPayOut.getPaidleave());
 			arrearReportPayOut.setCasualleave(reportPayOut.getCasualleave());
 			arrearReportPayOut.setSeekleave(reportPayOut.getSeekleave());
 			arrearReportPayOut.setAbsense(reportPayOut.getAbsense());
 			arrearReportPayOut.setPayDays(reportPayOut.getPayDays());
 			arrearReportPayOut.setPayableDays(reportPayOut.getPayableDays());
 			arrearReportPayOut.setBasic(reportPayOut.getBasic());
 			arrearReportPayOut.setBasicEarning(reportPayOut.getBasicEarning());
 			arrearReportPayOut.setDearnessAllowance(reportPayOut.getDearnessAllowance());
 			arrearReportPayOut.setDearnessAllowanceEarning(reportPayOut.getDearnessAllowanceEarning());
 			arrearReportPayOut.setConveyanceAllowance(reportPayOut.getConveyanceAllowance());
 			arrearReportPayOut.setConveyanceAllowanceEarning(reportPayOut.getConveyanceAllowanceEarning());
 			arrearReportPayOut.setHra(reportPayOut.getHra());
 			arrearReportPayOut.setHRAEarning(reportPayOut.getHRAEarning());
 			arrearReportPayOut.setMedicalAllowance(reportPayOut.getMedicalAllowance());
 			arrearReportPayOut.setMedicalAllowanceEarning(reportPayOut.getMedicalAllowanceEarning());
 			arrearReportPayOut.setAdvanceBonus(reportPayOut.getAdvanceBonus());
 			arrearReportPayOut.setAdvanceBonusEarning(reportPayOut.getAdvanceBonusEarning());
 			arrearReportPayOut.setSpecialAllowance(reportPayOut.getSpecialAllowance());
 			arrearReportPayOut.setSpecialAllowanceEarning(reportPayOut.getSpecialAllowanceEarning());
 			arrearReportPayOut.setLeaveTravelAllowance(reportPayOut.getLeaveTravelAllowance());
 			arrearReportPayOut.setLeaveTravelAllowanceEarning(reportPayOut.getLeaveTravelAllowanceEarning());
 			arrearReportPayOut.setPerformanceLinkedIncome(reportPayOut.getPerformanceLinkedIncome());
 			arrearReportPayOut.setPerformanceLinkedIncomeEarning(reportPayOut.getPerformanceLinkedIncomeEarning());
 			arrearReportPayOut.setUniformAllowance(reportPayOut.getUniformAllowance());
 			arrearReportPayOut.setUniformAllowanceEarning(reportPayOut.getUniformAllowanceEarning());
 			arrearReportPayOut.setCompanyBenefits(reportPayOut.getCompanyBenefits());
 			arrearReportPayOut.setCompanyBenefitsEarning(reportPayOut.getCompanyBenefitsEarning());
 			arrearReportPayOut.setProvidentFundEmployee(reportPayOut.getProvidentFundEmployee());
 			arrearReportPayOut.setProvidentFundEmployer(reportPayOut.getProvidentFundEmployer());
 			
 			arrearReportPayOut.setIsNoESIDeduction(reportPayOut.getIsNoESIDeduction());
 			arrearReportPayOut.setIsNoPFDeduction(reportPayOut.getIsNoPFDeduction());
 			
 			arrearReportPayOut.setProvidentFundEmployerPension(reportPayOut.getProvidentFundEmployerPension());
 			arrearReportPayOut.setESI_Employee(reportPayOut.getESI_Employee());
 			arrearReportPayOut.setESI_Employer(reportPayOut.getESI_Employer());
 			arrearReportPayOut.setPt(reportPayOut.getPt());
 			arrearReportPayOut.setTds(reportPayOut.getTds());
 			arrearReportPayOut.setLoan(reportPayOut.getLoan());
 			arrearReportPayOut.setGrossSalary(reportPayOut.getGrossSalary());
 			arrearReportPayOut.setTotalEarning(reportPayOut.getTotalEarning());
 			arrearReportPayOut.setTotalDeduction(reportPayOut.getTotalDeduction());
 			arrearReportPayOut.setNetPayableAmount(reportPayOut.getNetPayableAmount());
 			arrearReportPayOut.setUanno(reportPayOut.getUanno());
 			arrearReportPayOut.setPFNumber(reportPayOut.getPFNumber());
 			arrearReportPayOut.setESICNumber(reportPayOut.getESICNumber());
 			arrearReportPayOut.setFatherName(reportPayOut.getFatherName());
 			arrearReportPayOut.setDob(reportPayOut.getDob());
 			arrearReportPayOut.setGender(reportPayOut.getGender());
 			arrearReportPayOut.setHusbandName(reportPayOut.getHusbandName());
 			arrearReportPayOut.setMaritalStatus(reportPayOut.getMaritalStatus());
 			arrearReportPayOut.setEpfJoining(reportPayOut.getEpfJoining());
 			arrearReportPayOut.setIFSCCode(reportPayOut.getIFSCCode());
 			arrearReportPayOut.setAadharNo(reportPayOut.getAadharNo());
 			arrearReportPayOut.setPanno(reportPayOut.getPanno());
 			arrearReportPayOut.setMobileNo(reportPayOut.getMobileNo());
 			arrearReportPayOut.setEmail(reportPayOut.getEmail());
 			arrearReportPayOut.setOtherAllowance(reportPayOut.getOtherAllowance());
 			arrearReportPayOut.setOtherAllowanceEarning(reportPayOut.getOtherAllowanceEarning());
 			arrearReportPayOut.setOvertime(reportPayOut.getOvertime());
 			arrearReportPayOut.setTransactionNo(reportPayOut.getTransactionNo());
 			arrearReportPayOut.setBankAccountNumber(reportPayOut.getBankAccountNumber());
 			arrearReportPayOut.setBankAmount(reportPayOut.getBankAmount());
 			arrearReportPayOut.setUserId(reportPayOut.getUserId());
 			arrearReportPayOut.setUserIdUpdate(reportPayOut.getUserIdUpdate());
 			arrearReportPayOut.setDateCreated(reportPayOut.getDateCreated());
 			arrearReportPayOut.setDateUpdate(reportPayOut.getDateUpdate());
 			//arrearReportPayOut.setPFEarning(reportPayOut.getPf);
 			arrearReportPayOut.setPensionEarningSalary(reportPayOut.getPensionEarningSalary());
 			arrearReportPayOut.setOtherEarning(reportPayOut.getOtherEarning());
 			arrearReportPayOut.setOtherDeduction(reportPayOut.getOtherDeduction());
 			arrearReportPayOut.setArearAmount(reportPayOut.getArearAmount());
 			arrearReportPayOut.setIsNoPFDeduction(reportPayOut.getIsNoPFDeduction());
 			arrearReportPayOut.setRemarks(reportPayOut.getRemarks());
 			arrearReportPayOut.setEpfNominee(reportPayOut.getEpfNominee());
 			arrearReportPayOut.setEpfNomineeRelation(reportPayOut.getEpfNomineeRelation());
 			arrearReportPayOut.setEsicNominee(reportPayOut.getEsicNominee());
 			arrearReportPayOut.setEsicNomineeRelation(reportPayOut.getEsicNomineeRelation());
 			//arrearReportPayOut.setEsicjoining(reportPayOut.getEsic);
 			arrearReportPayOut.setTransactionMode(reportPayOut.getTransactionMode());
 			
  		}
 		
 		return arrearReportPayOut;
	}

 	 

 	public ArrearReportPayOut uiDtoToDatabaseModel(ReportPayOut uiobj) {
		// TODO Auto-generated method stub
		return null;
	}



	public List<ArrearPayOut> payOutListToArrearPayOutListConversion(List<PayOut> reportPayOuts) {
		List<ArrearPayOut> arrearPayOutList=new ArrayList<ArrearPayOut>();
 		for (PayOut payOut : reportPayOuts) {
 			arrearPayOutList.add(payOutToArrearPayOutConversion(payOut));
		}
     	 return arrearPayOutList;
	} 



	private ArrearPayOut payOutToArrearPayOutConversion(PayOut payOut) {
		ArrearPayOut arrearPayOut=new ArrearPayOut();
 		ArrearPayOutPK arrearPayOutPK=new ArrearPayOutPK();
 		
		arrearPayOutPK.setEmployeeId(payOut.getId().getEmployeeId());
		arrearPayOutPK.setPayHeadId(payOut.getId().getPayHeadId());
		arrearPayOutPK.setProcessMonth(payOut.getId().getProcessMonth());
 		arrearPayOut.setAmount(payOut.getAmount());
 		arrearPayOut.setId(arrearPayOutPK);
 		
  		return arrearPayOut;
	}



	public ReportPayOut reportPayOutObjectListToreportPayOutConversion(List<Object[]> reportPayOutObjectList) {
		ReportPayOut reportPayOut=new ReportPayOut();
		for (Object[] report : reportPayOutObjectList) {
 
 			ReportPayOutPK reportPayOutPK=new ReportPayOutPK();
			Long companyId=report[0]!=null?  Long.parseLong(report[0].toString()):null;
			Long employeeId=report[1]!=null? Long.parseLong(report[1].toString()):null;
 			String processMonth=report[2]!=null?(String)report[2]: "";
 			BigDecimal grossSalary=report[3]!=null?(BigDecimal)report[3]: null;
 			BigDecimal totalEarning=report[4]!=null?(BigDecimal)report[4]: null;
 			BigDecimal providentFundEmployee=report[5]!=null?(BigDecimal)report[5]: null;
 			BigDecimal providentFundEmployer=report[6]!=null?(BigDecimal)report[6]: null;
 			BigDecimal providentFundEmployerPension=report[7]!=null?(BigDecimal)report[7]: null;
 			String isNoPFDeduction=report[8] !=null ? ((Character)report[8]).toString() :null;
 			String isNoESIDeduction=report[9] !=null ?((Character)report[9]).toString():null;
 			BigDecimal esiEmployee=report[10]!=null?(BigDecimal)report[10]: null;
 			BigDecimal pt=report[11]!=null?(BigDecimal)report[11]: null;
 			reportPayOut.setCompanyId(companyId);
  			reportPayOutPK.setProcessMonth(processMonth);
 			reportPayOutPK.setEmployeeId(employeeId);
 			reportPayOut.setGrossSalary(grossSalary);
 			reportPayOut.setTotalEarning(totalEarning);
 			reportPayOut.setProvidentFundEmployee(providentFundEmployee);
 			reportPayOut.setProvidentFundEmployer(providentFundEmployer);
 			reportPayOut.setProvidentFundEmployerPension(providentFundEmployerPension);
 			reportPayOut.setIsNoPFDeduction(isNoPFDeduction);
 			reportPayOut.setIsNoESIDeduction(isNoESIDeduction);
 			reportPayOut.setESI_Employee(esiEmployee);
 			reportPayOut.setPt(pt);
    		}
 
		return reportPayOut;
	}



	public PayOut payOutObjectListTopayOutConversion(List<Object[]> rayOutObjectList) {
		PayOut payOut=new PayOut();
		for (Object[] payOutObj : rayOutObjectList) {
			PayOutPK payOutPK=new PayOutPK(); 
			
			
			Long employeeId=payOutObj[0] !=null ? (Integer)payOutObj[0] :0l;
			Long payHeadId=payOutObj[1] !=null ? (Integer)payOutObj[1] :0l;
			String processMonth=payOutObj[2] !=null ? (String)payOutObj[2] :"";
			BigDecimal amount=payOutObj[3]!=null? (BigDecimal)payOutObj[3]:null;	
			payOutPK.setEmployeeId(employeeId);
			payOutPK.setPayHeadId(payHeadId);
			payOutPK.setProcessMonth(processMonth);
			payOut.setAmount(amount);
			payOut.setId(payOutPK);	
		}
 		return payOut;
	}



 


	
}
