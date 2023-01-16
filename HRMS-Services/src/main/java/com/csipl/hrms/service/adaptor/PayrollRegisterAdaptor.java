package com.csipl.hrms.service.adaptor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.csipl.hrms.common.util.DateUtils;
import com.csipl.hrms.dto.payroll.PayRegisterHdDTO;

import com.csipl.hrms.dto.payrollprocess.ReportPayOutDTO;
import com.csipl.hrms.model.payrollprocess.ReportPayOut;
import com.csipl.hrms.model.payrollprocess.ReportPayOutPK;


public class PayrollRegisterAdaptor {
	public List<PayRegisterHdDTO> reportPayOutObjectListToPayRegisterHdDTOConversion(List<Object[]> objectList) {
		List<PayRegisterHdDTO> reportPayOutDtoList=new ArrayList<PayRegisterHdDTO>();
		for (Object[] obj : objectList) {
			if(obj!=null) {
			PayRegisterHdDTO payRegisterHdDto = new PayRegisterHdDTO();
			
 			Long payRegisterHdId=obj[0]!=null?  Long.parseLong(obj[0].toString()):null;
			//Long companyId=obj[1]!=null?  Long.parseLong(obj[1].toString()):null;
 			String processMonth=obj[1]!=null?(String)obj[1]: "";
 			Date dateUpdate = obj[2] != null ? (Date) obj[2] : null;
 			String registerName=obj[3]!=null?(String)obj[3]: "";
 			payRegisterHdDto.setPayRegisterHdId(payRegisterHdId);
 			payRegisterHdDto.setProcessMonth(processMonth);
 			payRegisterHdDto.setDateUpdate(dateUpdate);
 			payRegisterHdDto.setRegisterName(registerName+processMonth);
 			//System.out.println(" payRegisterHdId"+ payRegisterHdId);
 			//System.out.println("processMonth"+processMonth);
 			//System.out.println("Payroll Register"+registerName+processMonth);
 			
 			reportPayOutDtoList.add(payRegisterHdDto);
			}
    		}
 
		return reportPayOutDtoList;
	}

	public List<PayRegisterHdDTO> reportPayOutObjectListToPayRegisterHdDTO(List<Object[]> objectList,String processMonth) {
		List<PayRegisterHdDTO> reportPayOutDtoList=new ArrayList<PayRegisterHdDTO>();
		for (Object[] obj : objectList) {
			if(obj!=null) {
			PayRegisterHdDTO payRegisterHdDto = new PayRegisterHdDTO();
			
 			//Long payRegisterHdId=obj[0]!=null?  Long.parseLong(obj[0].toString()):null;
			//Long companyId=obj[1]!=null?  Long.parseLong(obj[1].toString()):null;
 			String employeeName=obj[0]!=null?(String)obj[0]: "";
 			String employeeCode=obj[1]!=null?(String)obj[1]: "";
 			String departmentName=obj[2]!=null?(String)obj[2]: "";
 			Date dateOfJoining = obj[3] != null ? (Date) obj[3] : null;
 			
 			BigDecimal payableDays=obj[4]!=null?(BigDecimal)obj[4]:null;
			BigDecimal grossSalary=obj[5]!=null?(BigDecimal)obj[5]:null;
			BigDecimal deduction =obj[6]!=null?(BigDecimal)obj[6]:null;
			BigDecimal netPayable=obj[7]!=null?(BigDecimal)obj[7]:null;
			
			BigDecimal earning=obj[8]!=null?(BigDecimal)obj[8]:null;
			
 			payRegisterHdDto.setEmployeeName(employeeName);
 			payRegisterHdDto.setEmployeeCode(employeeCode);
 			payRegisterHdDto.setDepartmentName(departmentName);
 			payRegisterHdDto.setDOJ(dateOfJoining);
 			payRegisterHdDto.setDeduction(deduction);
 			payRegisterHdDto.setGrossSalary(grossSalary);
 			payRegisterHdDto.setNetPayableAmount(netPayable);
 			payRegisterHdDto.setPayableGross(earning);
 		    Date month =  DateUtils.getDateForProcessMonth(processMonth);
 			int monthDays =DateUtils.findMonthDay(DateUtils.getYear(month),DateUtils.getMonth(month));
 			System.out.println("monthDays..."+monthDays);
 			payRegisterHdDto.setDays(monthDays+"/"+payableDays.longValue());
 			reportPayOutDtoList.add(payRegisterHdDto);
			}
    		}
 
		return reportPayOutDtoList;
	}
	

	public List<ReportPayOutDTO> reportPayOutObjectListToPayRegisterHdReport(List<Object[]> objectList,String processMonth) {
		List<ReportPayOutDTO> reportPayOutDTOList = new ArrayList<ReportPayOutDTO>();
		for (Object[] reportPayOutObj : objectList) {
			if(reportPayOutObj!=null) {
				ReportPayOutDTO reportPayOutDto = new ReportPayOutDTO();
		
 			//Long payRegisterHdId=obj[0]!=null?  Long.parseLong(obj[0].toString()):null;
			//Long companyId=obj[1]!=null?  Long.parseLong(obj[1].toString()):null;
 			String employeeName=reportPayOutObj[0]!=null?(String)reportPayOutObj[0]: "";
 			String employeeCode=reportPayOutObj[1]!=null?(String)reportPayOutObj[1]: "";
 			Long companyId=reportPayOutObj[2]!=null?  Long.parseLong(reportPayOutObj[2].toString()):null;
 			String companyName=reportPayOutObj[3]!=null?(String)reportPayOutObj[3]: "";
 			String departmentName=reportPayOutObj[4]!=null?(String)reportPayOutObj[4]: "";
 			String bankName=reportPayOutObj[5]!=null?(String)reportPayOutObj[5]: "";
 			String accountNumber=reportPayOutObj[6]!=null?(String)reportPayOutObj[6]:null;
 		    String IFSCCode=reportPayOutObj[7]!=null?(String)reportPayOutObj[7]:null;
 			Date dateOfJoining = reportPayOutObj[8] != null ? (Date) reportPayOutObj[8] : null;
 			BigDecimal basic=reportPayOutObj[9]!=null?(BigDecimal)reportPayOutObj[9]:null;
 			BigDecimal da=reportPayOutObj[10]!=null?(BigDecimal)reportPayOutObj[10]:null;
 			BigDecimal conveyanceAllowance=reportPayOutObj[11]!=null?(BigDecimal)reportPayOutObj[11]:null;
 			BigDecimal hra=reportPayOutObj[12]!=null?(BigDecimal)reportPayOutObj[12]:null;
 			BigDecimal medicalAllowance=reportPayOutObj[13]!=null?(BigDecimal)reportPayOutObj[13]:null;
 			BigDecimal advanceBonus=reportPayOutObj[14]!=null?(BigDecimal)reportPayOutObj[14]:null;
			BigDecimal specialAllowance=reportPayOutObj[15]!=null?(BigDecimal)reportPayOutObj[15]:null;
			BigDecimal companyBenefits=reportPayOutObj[16]!=null?(BigDecimal)reportPayOutObj[16]:null;
			BigDecimal grossSalary=reportPayOutObj[17]!=null?(BigDecimal)reportPayOutObj[17]:null;
			BigDecimal absense=reportPayOutObj[18]!=null?(BigDecimal)reportPayOutObj[18]:null;
			BigDecimal payDays=reportPayOutObj[19]!=null?(BigDecimal)reportPayOutObj[19]:null;
			BigDecimal basicEarning=reportPayOutObj[20]!=null?(BigDecimal)reportPayOutObj[20]:null;
			BigDecimal conveyanceAllowanceEarning=reportPayOutObj[21]!=null?(BigDecimal)reportPayOutObj[21]:null;
			BigDecimal hraEarning=reportPayOutObj[22]!=null?(BigDecimal)reportPayOutObj[22]:null;
			BigDecimal medicalAllowanceEarning=reportPayOutObj[23]!=null?(BigDecimal)reportPayOutObj[23]:null;
			BigDecimal advanceBonusEarning=reportPayOutObj[24]!=null?(BigDecimal)reportPayOutObj[24]:null;
			BigDecimal specialAllowanceEarning=reportPayOutObj[25]!=null?(BigDecimal)reportPayOutObj[25]:null;
			BigDecimal companyBenefitsEarning=reportPayOutObj[26]!=null?(BigDecimal)reportPayOutObj[26]:null;
			BigDecimal otherAllowanceEarning=reportPayOutObj[27]!=null?(BigDecimal)reportPayOutObj[27]:null;
			BigDecimal totalEarning=reportPayOutObj[28]!=null?(BigDecimal)reportPayOutObj[28]:null;
			BigDecimal loan=reportPayOutObj[29]!=null?(BigDecimal)reportPayOutObj[29]:null;
			BigDecimal edhocDeduction=reportPayOutObj[30]!=null?(BigDecimal)reportPayOutObj[30]:null;
			BigDecimal providentFundEmployee=reportPayOutObj[31]!=null?(BigDecimal)reportPayOutObj[31]:null;
			BigDecimal esi_Employee=reportPayOutObj[32]!=null?(BigDecimal)reportPayOutObj[32]:null;
			BigDecimal pt=reportPayOutObj[33]!=null?(BigDecimal)reportPayOutObj[33]:null;
			BigDecimal tds=reportPayOutObj[34]!=null?(BigDecimal)reportPayOutObj[34]:null;
			BigDecimal totalDeduction=reportPayOutObj[35]!=null?(BigDecimal)reportPayOutObj[35]:null;
			BigDecimal netPayableAmount=reportPayOutObj[36]!=null?(BigDecimal)reportPayOutObj[36]:null;
			BigDecimal otherEarning=reportPayOutObj[37]!=null?(BigDecimal)reportPayOutObj[37]:null;
			
 			
			reportPayOutDto.setName(employeeName);
			reportPayOutDto.setEmployeeCode(employeeCode);
			reportPayOutDto.setBankName(bankName);
			reportPayOutDto.setDepartmentName(departmentName);
			reportPayOutDto.setIfscCode(IFSCCode);
			reportPayOutDto.setAccountNumber(accountNumber);
			reportPayOutDto.setDateOfJoining(dateOfJoining);
			reportPayOutDto.setBasic(basic);
			reportPayOutDto.setConveyanceAllowance(conveyanceAllowance);
			reportPayOutDto.setHra(hra);
			reportPayOutDto.setHraEarning(hraEarning);
			//reportPayOutDto.setOtherAllowance(otherAllowance);
			//reportPayOutDto.setOvertime(overtime);
			reportPayOutDto.setOtherAllowanceEarning(otherAllowanceEarning);
			reportPayOutDto.setAbsense(absense);
			reportPayOutDto.setAdvanceBonus(advanceBonus);
			reportPayOutDto.setAdvanceBonusEarning(advanceBonusEarning);
			reportPayOutDto.setBasicEarning(basicEarning);
		
			reportPayOutDto.setCompanyBenefits(companyBenefits);
			reportPayOutDto.setCompanyBenefitsEarning(companyBenefitsEarning);
			reportPayOutDto.setConveyanceAllowanceEarning(conveyanceAllowanceEarning);
			//reportPayOutDto.setEmployeeLoansAdvnace(employeeLoansAdvnace);
			//reportPayOutDto.setEmployeeLoansAdvnaceEarning(employeeLoansAdvnaceEarning);
			reportPayOutDto.setEsi_Employee(esi_Employee);
			//reportPayOutDto.setEsi_Employer(esi_Employer);
			reportPayOutDto.setGrossSalary(grossSalary);
			reportPayOutDto.setLoan(loan);
			reportPayOutDto.setMedicalAllowance(medicalAllowance);
			reportPayOutDto.setMedicalAllowanceEarning(medicalAllowanceEarning);
			reportPayOutDto.setNetPayableAmount(netPayableAmount);
			
			reportPayOutDto.setPayDays(payDays);
		
			reportPayOutDto.setProvidentFundEmployee(providentFundEmployee);
			//reportPayOutDto.setProvidentFundEmployer(providentFundEmployer);
			//reportPayOutDto.setProvidentFundEmployerPension(providentFundEmployerPension);
			reportPayOutDto.setPt(pt);
			reportPayOutDto.setOtherEarning(otherEarning);
			reportPayOutDto.setSpecialAllowance(specialAllowance);
			reportPayOutDto.setSpecialAllowanceEarning(specialAllowanceEarning);
			reportPayOutDto.setTds(tds);
			reportPayOutDto.setTotalDeduction(totalDeduction);
			reportPayOutDto.setTotalEarning(totalEarning);
			reportPayOutDto.setCompanyId(companyId);
			reportPayOutDto.setCompanyName(companyName);
		//	reportPayOutDto.setWeekoff(weekoff);
			//reportPayOutDto.setCityId(cityId);
			//reportPayOutDto.setCompanyId(companyId);
			//reportPayOutDto.setUnNo(unNo);
			//reportPayOutDto.setPanNo(panNo);
			//reportPayOutDto.setAadharNo(aadharNo);
			//reportPayOutDto.setStateName(stateName);
			//reportPayOutDto.setProvisionDateCreated(provisionDateCreated);
			reportPayOutDTOList.add(reportPayOutDto);
			}
    		}
 
		return reportPayOutDTOList;
	}

	//nidhi 
	public List<PayRegisterHdDTO> payRollRegisterProcessMonthList(List<Object[]> objectList) {
		List<PayRegisterHdDTO> payRollRegisterList=new ArrayList<PayRegisterHdDTO>();
		for (Object[] obj : objectList) {
			if(obj!=null) {
			PayRegisterHdDTO payRegisterHdDto = new PayRegisterHdDTO();
			
 			String processMonth= obj[0]!= null ? (String) obj[0]:null;
 			Long payRegisterHdId= obj[1]!= null? Long.parseLong(obj[1].toString()):null;
 			payRegisterHdDto.setPayRegisterHdId(payRegisterHdId);
 			payRegisterHdDto.setProcessMonth(processMonth);
 			payRollRegisterList.add(payRegisterHdDto);
			}
    		}
		return payRollRegisterList;
	}
	public List<PayRegisterHdDTO> payRollRegisterList(List<Object[]> objectList) {
		List<PayRegisterHdDTO> payRollRegisterList=new ArrayList<PayRegisterHdDTO>();
		for (Object[] obj : objectList) {
			if(obj!=null) {
			PayRegisterHdDTO payRegisterHdDto = new PayRegisterHdDTO();
			Long payRegisterHdId= obj[0]!= null? Long.parseLong(obj[0].toString()):null;
 			String processMonth= obj[1]!= null ? (String) obj[1]:null;
 			Date dateOfUpdate = obj[2] != null ? (Date) obj[2] : null;
 			String departmentName=  obj[3]!= null ? (String) obj[3]:null;
 			payRegisterHdDto.setRegisterName("Register" + departmentName+ processMonth );
 			payRegisterHdDto.setPayRegisterHdId(payRegisterHdId);
 			payRegisterHdDto.setDateUpdate(dateOfUpdate);
 			payRollRegisterList.add(payRegisterHdDto);
			}
    		}
		return payRollRegisterList;
	}
	public List<PayRegisterHdDTO> employeePayrollRegisterRollback(List<Object[]> objectList) {
		List<PayRegisterHdDTO> reportPayOutDtoList=new ArrayList<PayRegisterHdDTO>();
		for (Object[] obj : objectList) {
			if(obj!=null) {
			PayRegisterHdDTO payRegisterHdDto = new PayRegisterHdDTO();
 			Long payRegisterHdId=obj[0]!=null?  Long.parseLong(obj[0].toString()):null;
			Long employeeId=obj[1]!=null?  Long.parseLong(obj[1].toString()):null;
			String employeeCode=obj[2]!=null?(String)obj[2]: "";
			String employeeName=obj[3]!=null?(String)obj[3]: "";
			String departmentName=obj[4]!=null?(String)obj[4]: "";
			Date dateOfJoining = obj[5] != null ? (Date) obj[5] : null;
			BigDecimal payableDays=obj[6]!=null?(BigDecimal)obj[6]:null;
			BigDecimal grossSalary=obj[7]!=null?(BigDecimal)obj[7]:null;
			BigDecimal totalDeduction=obj[8]!=null?(BigDecimal)obj[8]:null;
			BigDecimal netPayable=obj[9]!=null?(BigDecimal)obj[9]:null;
			String processMonth=obj[10]!=null?(String)obj[10]:null;
			
			payRegisterHdDto.setEmployeeId(employeeId);
			payRegisterHdDto.setPayRegisterHdId(payRegisterHdId);
 			payRegisterHdDto.setEmployeeName(employeeName);
 			payRegisterHdDto.setEmployeeCode(employeeCode);
 			payRegisterHdDto.setDepartmentName(departmentName);
 			payRegisterHdDto.setDOJ(dateOfJoining);
 			payRegisterHdDto.setDeduction(totalDeduction);
 			payRegisterHdDto.setGrossSalary(grossSalary);
 			payRegisterHdDto.setNetPayableAmount(netPayable);
 		    Date month =  DateUtils.getDateForProcessMonth(processMonth);
 			int monthDays =DateUtils.findMonthDay(DateUtils.getYear(month),DateUtils.getMonth(month));
 			System.out.println("monthDays..."+monthDays);
 			payRegisterHdDto.setDays(monthDays+"/"+payableDays.longValue());
 			reportPayOutDtoList.add(payRegisterHdDto);
			}
    		}
 
		return reportPayOutDtoList;

	}
	
	public List<ReportPayOutDTO> objectListToSalarySheethdList(List<Object[]> monthlyReportList) {
		List<ReportPayOutDTO> reportPayoutList = new ArrayList<>();
		
		for (Object[] reportPayoutObj : monthlyReportList) {
			
			ReportPayOutDTO reportPayoutDto = new ReportPayOutDTO();

			String employeeCode = reportPayoutObj[0] != null ? (String) reportPayoutObj[0] : null;
			String empName = reportPayoutObj[1] != null ? (String) reportPayoutObj[1] : null;
			String designationName = reportPayoutObj[2] != null ? (String) reportPayoutObj[2] : null;
			String departmentName = reportPayoutObj[3] != null ? (String) reportPayoutObj[3] : null;
			Date dateOfJoining = reportPayoutObj[4] != null ? (Date) reportPayoutObj[4] : null;
			String gender = reportPayoutObj[5] != null ? (String) reportPayoutObj[5] : null;
			String jobLocation = reportPayoutObj[6] != null ? (String) reportPayoutObj[6] : null;
			BigDecimal daysInMonth = reportPayoutObj[7] != null ? (new BigDecimal(reportPayoutObj[7].toString())) : null;
			BigDecimal absent = reportPayoutObj[8] != null ? (new BigDecimal(reportPayoutObj[8].toString())) : null;
			BigDecimal payDays = reportPayoutObj[9] != null ? (new BigDecimal(reportPayoutObj[9].toString())) : null;
			BigDecimal monthlyGross = reportPayoutObj[10] != null ? (new BigDecimal(reportPayoutObj[10].toString()))
					: null;
			String amount = null;
			String payHeadId = null;
			payHeadId = reportPayoutObj[11] != null ? (String) reportPayoutObj[11] : null;
			amount = reportPayoutObj[12] != null ? (String) reportPayoutObj[12] : null;
			String[] amounts = amount.split(",");
			String[] payHeadIds = payHeadId.split(",");
			Map<String, String> map = new HashMap<String, String>();

			for (int i = 0; i < payHeadIds.length; i++) {
				map.put(payHeadIds[i], amounts[i]);
			}
			reportPayoutDto.setPayHeadsMap(map);
			 
			BigDecimal netPayableAmount = reportPayoutObj[13] != null ? (new BigDecimal(reportPayoutObj[13].toString()))
					: null;
			String bankName = reportPayoutObj[14] != null ? (String) reportPayoutObj[14].toString() : null;
			String accountNumber = reportPayoutObj[15] != null ? (String) reportPayoutObj[15].toString() : null;
			String ifscCode = reportPayoutObj[16] != null ? (String) reportPayoutObj[16].toString() : null;
			String branchName = reportPayoutObj[17] != null ? (String) reportPayoutObj[17].toString() : null;
		 
			reportPayoutDto.setEmployeeCode(employeeCode);
			reportPayoutDto.setEmpName(empName);
			reportPayoutDto.setDesignationName(designationName);
			reportPayoutDto.setDepartmentName(departmentName);
			reportPayoutDto.setDateOfJoining(dateOfJoining);
			reportPayoutDto.setMonthalyGross(monthlyGross);
			
			if(gender.equals("M"))
			reportPayoutDto.setGender("Male");
			
			if(gender.equals("F"))
			reportPayoutDto.setGender("Female");
			
			reportPayoutDto.setDaysInMonth(daysInMonth.longValue());
			reportPayoutDto.setJobLocation(jobLocation);
			reportPayoutDto.setAbsent(absent.longValue());
			reportPayoutDto.setPayDays(payDays);
			reportPayoutDto.setNetPayableAmount(netPayableAmount);
			reportPayoutDto.setBankName(bankName);
			reportPayoutDto.setAccountNumber(accountNumber);
			reportPayoutDto.setIfscCode(ifscCode);
			reportPayoutDto.setBranchName(branchName);
			reportPayoutList.add(reportPayoutDto);
		}
		return reportPayoutList;
	}
	
	public List<ReportPayOutDTO> paymentTransferSheetReport(List<Object[]> objectList,String processMonth) {
		List<ReportPayOutDTO> reportPayOutDTOList = new ArrayList<ReportPayOutDTO>();
		for (Object[] reportPayOutObj : objectList) {
			if(reportPayOutObj!=null) {
				ReportPayOutDTO reportPayOutDto = new ReportPayOutDTO();
		
 			//Long payRegisterHdId=obj[0]!=null?  Long.parseLong(obj[0].toString()):null;
			//Long companyId=obj[1]!=null?  Long.parseLong(obj[1].toString()):null;
 			String employeeName=reportPayOutObj[0]!=null?(String)reportPayOutObj[0]: "";
 			String employeeCode=reportPayOutObj[1]!=null?(String)reportPayOutObj[1]: "";
 			Long companyId=reportPayOutObj[2]!=null?  Long.parseLong(reportPayOutObj[2].toString()):null;
 			String companyName=reportPayOutObj[3]!=null?(String)reportPayOutObj[3]: "";
 			String departmentName=reportPayOutObj[4]!=null?(String)reportPayOutObj[4]: "";
 			String bankName=reportPayOutObj[5]!=null?(String)reportPayOutObj[5]: "";
 			String accountNumber=reportPayOutObj[6]!=null?(String)reportPayOutObj[6]:null;
 		    String IFSCCode=reportPayOutObj[7]!=null?(String)reportPayOutObj[7]:null;
 			Date dateOfJoining = reportPayOutObj[8] != null ? (Date) reportPayOutObj[8] : null;
 			BigDecimal basic=reportPayOutObj[9]!=null?(BigDecimal)reportPayOutObj[9]:null;
 			BigDecimal da=reportPayOutObj[10]!=null?(BigDecimal)reportPayOutObj[10]:null;
 			BigDecimal conveyanceAllowance=reportPayOutObj[11]!=null?(BigDecimal)reportPayOutObj[11]:null;
 			BigDecimal hra=reportPayOutObj[12]!=null?(BigDecimal)reportPayOutObj[12]:null;
 			BigDecimal medicalAllowance=reportPayOutObj[13]!=null?(BigDecimal)reportPayOutObj[13]:null;
 			BigDecimal advanceBonus=reportPayOutObj[14]!=null?(BigDecimal)reportPayOutObj[14]:null;
			BigDecimal specialAllowance=reportPayOutObj[15]!=null?(BigDecimal)reportPayOutObj[15]:null;
			BigDecimal companyBenefits=reportPayOutObj[16]!=null?(BigDecimal)reportPayOutObj[16]:null;
			BigDecimal grossSalary=reportPayOutObj[17]!=null?(BigDecimal)reportPayOutObj[17]:null;
			BigDecimal absense=reportPayOutObj[18]!=null?(BigDecimal)reportPayOutObj[18]:null;
			BigDecimal payDays=reportPayOutObj[19]!=null?(BigDecimal)reportPayOutObj[19]:null;
			BigDecimal basicEarning=reportPayOutObj[20]!=null?(BigDecimal)reportPayOutObj[20]:null;
			BigDecimal conveyanceAllowanceEarning=reportPayOutObj[21]!=null?(BigDecimal)reportPayOutObj[21]:null;
			BigDecimal hraEarning=reportPayOutObj[22]!=null?(BigDecimal)reportPayOutObj[22]:null;
			BigDecimal medicalAllowanceEarning=reportPayOutObj[23]!=null?(BigDecimal)reportPayOutObj[23]:null;
			BigDecimal advanceBonusEarning=reportPayOutObj[24]!=null?(BigDecimal)reportPayOutObj[24]:null;
			BigDecimal specialAllowanceEarning=reportPayOutObj[25]!=null?(BigDecimal)reportPayOutObj[25]:null;
			BigDecimal companyBenefitsEarning=reportPayOutObj[26]!=null?(BigDecimal)reportPayOutObj[26]:null;
			BigDecimal otherAllowanceEarning=reportPayOutObj[27]!=null?(BigDecimal)reportPayOutObj[27]:null;
			BigDecimal totalEarning=reportPayOutObj[28]!=null?(BigDecimal)reportPayOutObj[28]:null;
			BigDecimal loan=reportPayOutObj[29]!=null?(BigDecimal)reportPayOutObj[29]:null;
			BigDecimal edhocDeduction=reportPayOutObj[30]!=null?(BigDecimal)reportPayOutObj[30]:null;
			BigDecimal providentFundEmployee=reportPayOutObj[31]!=null?(BigDecimal)reportPayOutObj[31]:null;
			BigDecimal esi_Employee=reportPayOutObj[32]!=null?(BigDecimal)reportPayOutObj[32]:null;
			BigDecimal pt=reportPayOutObj[33]!=null?(BigDecimal)reportPayOutObj[33]:null;
			BigDecimal tds=reportPayOutObj[34]!=null?(BigDecimal)reportPayOutObj[34]:null;
			BigDecimal totalDeduction=reportPayOutObj[35]!=null?(BigDecimal)reportPayOutObj[35]:null;
			BigDecimal netPayableAmount=reportPayOutObj[36]!=null?(BigDecimal)reportPayOutObj[36]:null;
			BigDecimal otherEarning=reportPayOutObj[37]!=null?(BigDecimal)reportPayOutObj[37]:null;
			String transNo=reportPayOutObj[38]!=null?(String)reportPayOutObj[38]:null;
			String status=reportPayOutObj[39]!=null?(String)reportPayOutObj[39]:null;
 			
			reportPayOutDto.setName(employeeName);
			reportPayOutDto.setEmployeeCode(employeeCode);
			reportPayOutDto.setBankName(bankName);
			reportPayOutDto.setDepartmentName(departmentName);
			reportPayOutDto.setIfscCode(IFSCCode);
			reportPayOutDto.setAccountNumber(accountNumber);
			reportPayOutDto.setDateOfJoining(dateOfJoining);
			reportPayOutDto.setBasic(basic);
			reportPayOutDto.setConveyanceAllowance(conveyanceAllowance);
			reportPayOutDto.setHra(hra);
			reportPayOutDto.setHraEarning(hraEarning);
			//reportPayOutDto.setOtherAllowance(otherAllowance);
			//reportPayOutDto.setOvertime(overtime);
			reportPayOutDto.setOtherAllowanceEarning(otherAllowanceEarning);
			reportPayOutDto.setAbsense(absense);
			reportPayOutDto.setAdvanceBonus(advanceBonus);
			reportPayOutDto.setAdvanceBonusEarning(advanceBonusEarning);
			reportPayOutDto.setBasicEarning(basicEarning);
		
			reportPayOutDto.setCompanyBenefits(companyBenefits);
			reportPayOutDto.setCompanyBenefitsEarning(companyBenefitsEarning);
			reportPayOutDto.setConveyanceAllowanceEarning(conveyanceAllowanceEarning);
			//reportPayOutDto.setEmployeeLoansAdvnace(employeeLoansAdvnace);
			//reportPayOutDto.setEmployeeLoansAdvnaceEarning(employeeLoansAdvnaceEarning);
			reportPayOutDto.setEsi_Employee(esi_Employee);
			//reportPayOutDto.setEsi_Employer(esi_Employer);
			reportPayOutDto.setGrossSalary(grossSalary);
			reportPayOutDto.setLoan(loan);
			reportPayOutDto.setMedicalAllowance(medicalAllowance);
			reportPayOutDto.setMedicalAllowanceEarning(medicalAllowanceEarning);
			reportPayOutDto.setNetPayableAmount(netPayableAmount);
			
			reportPayOutDto.setPayDays(payDays);
		
			reportPayOutDto.setProvidentFundEmployee(providentFundEmployee);
			//reportPayOutDto.setProvidentFundEmployer(providentFundEmployer);
			//reportPayOutDto.setProvidentFundEmployerPension(providentFundEmployerPension);
			reportPayOutDto.setPt(pt);
			reportPayOutDto.setOtherEarning(otherEarning);
			reportPayOutDto.setSpecialAllowance(specialAllowance);
			reportPayOutDto.setSpecialAllowanceEarning(specialAllowanceEarning);
			reportPayOutDto.setTds(tds);
			reportPayOutDto.setTotalDeduction(totalDeduction);
			reportPayOutDto.setTotalEarning(totalEarning);
			reportPayOutDto.setCompanyId(companyId);
			reportPayOutDto.setCompanyName(companyName);
			reportPayOutDto.setTransactionNo(transNo);
			
			 if(transNo != null) {
					reportPayOutDto.setEmployeeStatus("Transferred"); 
				}
			 else if(status!=null &&  status.equals("HO")) {
				reportPayOutDto.setEmployeeStatus("HOLD");
			 }
			else {
				reportPayOutDto.setEmployeeStatus("Need To Transfer"); 
			}
		//	reportPayOutDto.setWeekoff(weekoff);
			//reportPayOutDto.setCityId(cityId);
			//reportPayOutDto.setCompanyId(companyId);
			//reportPayOutDto.setUnNo(unNo);
			//reportPayOutDto.setPanNo(panNo);
			//reportPayOutDto.setAadharNo(aadharNo);
			//reportPayOutDto.setStateName(stateName);
			//reportPayOutDto.setProvisionDateCreated(provisionDateCreated);
			reportPayOutDTOList.add(reportPayOutDto);
			}
    		}
 
		return reportPayOutDTOList;
	}
}
