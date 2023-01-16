package com.csipl.hrms.service.adaptor;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.csipl.common.services.dropdown.DropDownCache;
import com.csipl.hrms.common.enums.DropDownEnum;
import com.csipl.hrms.common.enums.EmployeeStatusEnum;
import com.csipl.hrms.common.util.DateUtils;
import com.csipl.hrms.dto.payroll.LoanEMIDTO;
import com.csipl.hrms.dto.payroll.LoanIssueDTO;
import com.csipl.hrms.model.employee.Employee;
import com.csipl.hrms.model.payroll.LoanEMI;
import com.csipl.hrms.model.payroll.LoanIssue;
import com.csipl.hrms.service.payroll.repository.LoanEMIRepository;
import com.csipl.hrms.service.util.ConverterUtil;



public class LoanIssueAdaptor implements Adaptor<LoanIssueDTO, LoanIssue> {
private LoanEMIRepository loanEMIRepository;

	@Override
	public LoanIssue uiDtoToDatabaseModel(LoanIssueDTO loanIssueDTO) {

		LoanIssue loanIssue = new LoanIssue();
		if(loanIssueDTO.getTransactionNo()!=null)
		loanIssue.setTransactionNo(loanIssueDTO.getTransactionNo());
		Employee employee=new Employee();
		employee.setEmployeeId(loanIssueDTO.getEmployeeId());
		loanIssue.setEmployee(employee);
		loanIssue.setLoanAmount(loanIssueDTO.getLoanAmount());
		loanIssue.setLoanPendingAmount(loanIssueDTO.getLoanPendingAmount());
		loanIssue.setIssueDate(loanIssueDTO.getIssueDate());
		loanIssue.setNoOfEmi(loanIssueDTO.getNoOfEmi());
		 
		loanIssue.setEmiStartDate(DateUtils.getDateForProcessMonth(loanIssueDTO.getDeductionFromPayroll()));
		//loanIssue.setRateOfInterest(loanIssueDTO.getRateOfInterest());
		loanIssue.setNaration(loanIssueDTO.getNaration());
		loanIssue.setLoanType(loanIssueDTO.getLoanType());
		//loanIssue.setInterestType(loanIssueDTO.getInterestType());
		loanIssue.setEmiAmount(loanIssueDTO.getEmiAmount());
		;
		
		//loanIssue.setEmiStartDate(loanIssueDTO.getEmiStartDate());
		loanIssue.setTransactionDate(loanIssueDTO.getTransactionDate());
		loanIssue.setUserId(loanIssueDTO.getUserId());
		loanIssue.setRemark(loanIssueDTO.getRemark());
		loanIssue.setDateCreated(loanIssueDTO.getDateCreated());
		loanIssue.setActiveStatus(EmployeeStatusEnum.ActiveStatus.getEmployeeStatus());
		loanIssue.setCompanyId(loanIssueDTO.getCompanyId());
		 
		if(loanIssueDTO.getDateCreated()==null)
			loanIssue.setDateCreated(new Date());
		else
		loanIssue.setDateCreated(loanIssueDTO.getDateCreated());
		loanIssue.setDateUpdate(new Date());
		loanIssue.setUserIdUpdate(loanIssueDTO.getUserIdUpdate());
		return loanIssue;
		
	}


	@Override
	public List<LoanIssueDTO> databaseModelToUiDtoList(List<LoanIssue> loanIssueList) {
		List<LoanIssueDTO> loanIssueDtoList = new ArrayList<LoanIssueDTO>();
		for (LoanIssue loanIssue : loanIssueList) {
			loanIssueDtoList.add(databaseModelToUiDto(loanIssue));
		}
		return loanIssueDtoList;
	}

	public List<LoanIssueDTO> databaseObjModelToUiDtoList(List<Object[]> loanIssueObjList) {
		List<LoanIssueDTO> loanIssueDtoList = new ArrayList<LoanIssueDTO>();
		for (Object[] loanIssueObj : loanIssueObjList) {
			LoanIssueDTO loanIssueDto = new LoanIssueDTO();
			Long employeeId = loanIssueObj[0] != null ? Long.parseLong(loanIssueObj[0].toString()) : null;
			String firstName = loanIssueObj[1] != null ? (String) loanIssueObj[1] : null;
			String lastName = loanIssueObj[2] != null ? (String) loanIssueObj[2] : null;
			BigDecimal totalLoanAmount = loanIssueObj[3] != null ? (new BigDecimal(loanIssueObj[3].toString())) : null;
			String departmentName = loanIssueObj[4] != null ? (String) loanIssueObj[4] : null;
			String designationName = loanIssueObj[5] != null ? (String) loanIssueObj[5] : null;
			String employeeCode = loanIssueObj[6] != null ? (String) loanIssueObj[6] : null;
			BigDecimal loanPendingAmount=loanIssueObj[7] !=null?(new BigDecimal(loanIssueObj[7].toString())):null;
			Long transactionNo=loanIssueObj[8]!=null?Long.parseLong(loanIssueObj[8].toString()):null;
			//String gradeName=loanIssueObj[9]!=null?loanIssueObj[9].toString():null;
			loanIssueDto.setEmployeeId(employeeId);
			loanIssueDto.setEmployeeName(firstName + " " + lastName);
			loanIssueDto.setLoanAmount(totalLoanAmount);
			loanIssueDto.setDepartmentName(departmentName);
			loanIssueDto.setDesignationName(designationName);
			loanIssueDto.setEmployeeCode(employeeCode);
			loanIssueDto.setLoanPendingAmount(loanPendingAmount);
			loanIssueDto.setTransactionNo(transactionNo);
//			loanIssueDto.setGradeName(gradeName);
			loanIssueDtoList.add(loanIssueDto);
		}
		return loanIssueDtoList;
	}

	public List<LoanEMIDTO> databaseModelToLoanEmiDtoList(LoanIssue loanIssue) {
		List<LoanEMIDTO> LoanEmiDTOList = new ArrayList<LoanEMIDTO>();
		// for (LoanIssue loanIssue : loanIssueList) {
		LoanEmiDTOList = databaseModelToLoanEmiDto(loanIssue, LoanEmiDTOList);

		// }
		return LoanEmiDTOList;
	}

	public List<LoanEMIDTO> databaseModelToLoanEmiDto(LoanIssue loanIssue, List<LoanEMIDTO> LoanEmiDTOList) {
//		BigDecimal remaining = new BigDecimal(0);
//
//		remaining = loanIssue.getLoanAmount();
//
//		for (LoanEMI loanEmi : loanIssue.getLoanEmis()) {
//			LoanEMIDTO loanEmiDto = new LoanEMIDTO();
//			loanEmiDto.setEmiDate(loanEmi.getEmiDate());
//			loanEmiDto.setEmiAmount(loanEmi.getEmiAmount());
//			loanEmiDto.setLoanAmount(remaining);
//			remaining = remaining.subtract(loanEmi.getEmiAmount());
//			loanEmiDto.setRemaining(remaining);
//			loanEmiDto.setRemarks(loanEmi.getRemarks());
//			if(loanEmi.getEmiStatus().equals("AC"))
//				loanEmiDto.setEmiStatusLabel("Active"); 
//			else if(loanEmi.getEmiStatus().equals("CE"))
//				loanEmiDto.setEmiStatusLabel("Closed"); 
//
//			LoanEmiDTOList.add(loanEmiDto);
//
//		}
//		return LoanEmiDTOList;
		return null;
	}

	@Override
	public LoanIssueDTO databaseModelToUiDto(LoanIssue loanIssue) {
		BigDecimal totalEmiAmount = new BigDecimal(0);
		LoanIssueDTO loanIssueDto = new LoanIssueDTO();
	
		loanIssueDto.setTransactionNo(loanIssue.getTransactionNo());
		loanIssueDto.setEmployeeId(loanIssue.getEmployee().getEmployeeId());
		loanIssueDto.setEmployeeCode(loanIssue.getEmployee().getEmployeeCode());
		loanIssueDto.setDateOfJoining(loanIssue.getEmployee().getDateOfJoining());
//		loanIssueDto.setInterestType(loanIssue.getInterestType());
		loanIssueDto.setDepartmentName(loanIssue.getEmployee().getDepartment().getDepartmentName());
		
		loanIssueDto.setDesignationName(loanIssue.getEmployee().getDesignation().getDesignationName());
		loanIssueDto.setEmployeeLogoPath(loanIssue.getEmployee().getEmployeeLogoPath());
		loanIssueDto
				.setEmployeeName(loanIssue.getEmployee().getFirstName() + " " + loanIssue.getEmployee().getLastName());
 		loanIssueDto
		.setLovName("("+loanIssue.getEmployee().getEmployeeCode()+") "+loanIssue.getEmployee().getFirstName() + " " + loanIssue.getEmployee().getLastName());
 		loanIssueDto.setLoanAmount(loanIssue.getLoanAmount());
		loanIssueDto.setIssueDate(loanIssue.getIssueDate());
		loanIssueDto.setNoOfEmi(loanIssue.getNoOfEmi());
		loanIssueDto.setCompanyId(loanIssue.getCompanyId());
		//loanIssueDto.setRateOfInterest(loanIssue.getRateOfInterest());
		loanIssueDto.setNaration(loanIssue.getNaration());
		loanIssueDto.setLoanType(loanIssue.getLoanType());
//		loanIssueDto.setLoanTypeLabel(DropDownCache.getInstance()
//				.getDropDownValue(DropDownEnum.LoanType.getDropDownName(), loanIssue.getLoanType()));
		// loanIssueDto.setLoanType(loanIssue.getLoanType());
		loanIssueDto.setEmiAmount(loanIssue.getEmiAmount());
		loanIssueDto.setEmiStartDate(loanIssue.getEmiStartDate());
		loanIssueDto.setTransactionDate(loanIssue.getTransactionDate());
		loanIssueDto.setUserId(loanIssue.getUserId());
		loanIssueDto.setDateCreated(loanIssue.getDateCreated());
		loanIssueDto.setActiveStatus(loanIssue.getActiveStatus());
		loanIssueDto.setRemark(loanIssue.getRemark());
		
 
		BigDecimal loanAmount = loanIssue.getLoanAmount();
		for (LoanEMI emi : loanIssue.getLoanEmis()) {
			if (emi.getEmiAmount() != null) {
				totalEmiAmount = totalEmiAmount.add(emi.getEmiAmount());
			}
		}
		
		loanIssueDto.setTotalEmiAmount(totalEmiAmount);
		MathContext mc = new MathContext(2);
		BigDecimal pendingAmount = loanAmount.subtract(totalEmiAmount);
		loanIssue.setLoanPendingAmount(pendingAmount);

		if (loanIssue.getLoanPendingAmount() != null) {
			 
			loanIssueDto.setLoanPendingAmount(loanIssue.getLoanPendingAmount());
		}

		if(loanIssueDto.getLoanPendingAmount().intValue()==0)
			loanIssueDto.setLoanStatus("Settled");
		else
			loanIssueDto.setLoanStatus("pending");
		loanIssueDto.setActiveStatusLabel(DropDownCache.getInstance()
				.getDropDownValue(DropDownEnum.Status.getDropDownName(), loanIssue.getActiveStatus()));

		BigDecimal remaining = new BigDecimal(0);
		remaining = loanIssue.getLoanAmount().subtract(loanIssue.getEmiAmount());
		if(loanIssue.getLoanEmis()!=null)
		loanIssueDto.setLoanEmisDto(databaseModelToLoanEmiDto(loanIssue.getLoanEmis(), remaining,loanIssue));
		int remainingEmi=0;
		if(loanIssue.getLoanEmis()!=null && loanIssue.getLoanEmis().size()==0)
			remainingEmi = loanIssue.getNoOfEmi();
		else
		 remainingEmi=(int)Math.ceil(pendingAmount.doubleValue()/loanIssue.getEmiAmount().doubleValue());
		loanIssueDto.setRemainingEmi(remainingEmi);
		return loanIssueDto;
		
	}

	@Override
	public List<LoanIssue> uiDtoToDatabaseModelList(List<LoanIssueDTO> loanIssueDtoList) {
		List<LoanIssue> loanIssue = new ArrayList<LoanIssue>();
		for (LoanIssueDTO loanIssueDto : loanIssueDtoList) {
			loanIssue.add(uiDtoToDatabaseModel(loanIssueDto));
		}
		return loanIssue;
	}

	public List<LoanEMIDTO> databaseModelToLoanEmiDto(List<LoanEMI> loanEMIList, BigDecimal remaining,LoanIssue loanIssue) {
		List<LoanEMIDTO> loanEMIDtoList = new ArrayList<LoanEMIDTO>();

		for (LoanEMI loanEMI : loanEMIList) {
			loanEMIDtoList.add(databaseModelToLoanEmiDto(loanEMI, remaining,loanIssue));
		}
		return loanEMIDtoList;

	}

	public LoanEMIDTO databaseModelToLoanEmiDto(LoanEMI loanEMI, BigDecimal remaining,LoanIssue loanIssue) {
		
		LoanEMIDTO loanEMIDto = new LoanEMIDTO();
		loanEMIDto.setEmiAmount(loanEMI.getEmiAmount());
		loanEMIDto.setEmiDate(loanEMI.getEmiDate());
		loanEMIDto.setRemaining(remaining);
		loanEMIDto.setRemarks(loanEMI.getRemarks());
		remaining=loanIssue.getLoanPendingAmount().subtract(loanEMI.getEmiAmount());
		loanEMIDto.setRemaining(remaining);
		

		return loanEMIDto;
	}

	
 	public LoanIssue uiDtoToDatabaseModelSettlement(LoanIssueDTO loanIssueDTO) {

		LoanIssue loanIssue = new LoanIssue();
		loanIssue.setTransactionNo(loanIssueDTO.getTransactionNo());
		Employee employee = new Employee();
		employee.setEmployeeId(loanIssueDTO.getEmployeeId());
		loanIssue.setLoanAmount(loanIssueDTO.getLoanAmount());
		loanIssue.setLoanPendingAmount(loanIssueDTO.getLoanPendingAmount());
		loanIssue.setIssueDate(loanIssueDTO.getIssueDate());
		loanIssue.setNoOfEmi(loanIssueDTO.getNoOfEmi());
		//loanIssue.setRateOfInterest(loanIssueDTO.getRateOfInterest());
		loanIssue.setNaration(loanIssueDTO.getNaration());
		loanIssue.setLoanType(loanIssueDTO.getLoanType());
		//loanIssue.setInterestType(loanIssueDTO.getInterestType());
		loanIssue.setEmiAmount(loanIssueDTO.getEmiAmount());
		loanIssue.setEmiStartDate(loanIssueDTO.getEmiStartDate());
		loanIssue.setTransactionDate(loanIssueDTO.getTransactionDate());
		loanIssue.setCompanyId(loanIssueDTO.getCompanyId());
		
		if(loanIssueDTO.getDateCreated()==null)
			loanIssue.setDateCreated(new Date());
		else
		loanIssue.setDateCreated(loanIssueDTO.getDateCreated());
		loanIssue.setDateUpdate(new Date());
		loanIssue.setUserId(loanIssueDTO.getUserId());
		loanIssue.setUserIdUpdate(loanIssueDTO.getUserId());
		loanIssue.setSettlementAmount(loanIssueDTO.getSettlementAmount());
 		loanIssue.setPaymentMode(loanIssueDTO.getPaymentMode());
		loanIssue.setInstrumentNo(loanIssueDTO.getInstrumentNo());
  		loanIssue.setRemark(loanIssueDTO.getRemark());
 		loanIssue.setIsSettlementCompleted(loanIssueDTO.getIsSettlementCompleted());
		loanIssue.setEmployee(employee);

 		//loanIssue.setIsSettlementCompleted("Y");
		/*if(!loanIssueDTO.getPaymentMode().equals("SA"))
			loanIssue.setActiveStatus("CE");
		else*/
			if((loanIssue.getLoanPendingAmount().subtract(loanIssue.getSettlementAmount())).compareTo(BigDecimal.ZERO)==0)
				loanIssue.setActiveStatus("CE");
			else
			loanIssue.setActiveStatus(EmployeeStatusEnum.ActiveStatus.getEmployeeStatus());
 			loanIssue.setLoanEmis(uiDtoToDatabaseModelListSettlement(loanIssueDTO.getLoanEmisDto(),loanIssue));
  		return loanIssue;
 		
	}
 	public List<LoanEMI> uiDtoToDatabaseModelListSettlement(List<LoanEMIDTO> loanEMIDtoList,LoanIssue loanIssue) {
 		
		 List<LoanEMI> loanEMIList=new ArrayList<LoanEMI>();
		
		for (LoanEMIDTO loanEMIDto : loanEMIDtoList) {
			loanEMIList.add(uiDtoToDatabaseModelListSettlement(loanEMIDto, loanIssue));
		}
 		return loanEMIList;
	}

 

	public LoanEMI uiDtoToDatabaseModelListSettlement(LoanEMIDTO loanEMIDto,LoanIssue loanIssue) {
		
		LoanEMI loanEMI=new LoanEMI();
		loanEMI.setEmiDate(loanEMIDto.getEmiDate());
		
		loanEMI.setEmiAmount(loanEMIDto.getEmiAmount());
		loanEMI.setRemarks(loanEMIDto.getRemarks());
		loanEMI.setLoanIssue(loanIssue);
		loanEMI.setDateCreated(new Date());
		loanEMI.setEmiDate(new Date());
		loanEMI.setUserId(loanEMIDto.getUserId());
		loanEMI.setTransactionFlag(loanEMIDto.getTransactionFlag());
		loanIssue.setTransactionNo(loanEMIDto.getTransactionNo());
		loanEMI.setLoanIssue(loanIssue);
		
		
		
 	//	if(!loanIssue.getPaymentMode().equals("SA")) {
// 			System.out.println("==========================in if================================f");
//			loanEMI.setEmiNo(loanEMIDto.getEmiNo());
//			if(loanEMIDto.getEmiDate()==null)
//				loanEMI.setEmiDate(new Date());
//			else
//			loanEMI.setEmiDate(loanEMIDto.getEmiDate());
//			loanEMI.setEmiAmount(loanEMIDto.getEmiAmount());
//			if(loanEMIDto.getEmiStatus()==null)
//				loanEMI.setEmiStatus("CE");
//			else
//				loanEMI.setEmiStatus(loanEMIDto.getEmiStatus());
//			loanEMI.setUserId(loanEMIDto.getUserId());
// 			if(loanEMIDto.getDateCreated()!=null)
//			loanEMI.setDateCreated(loanEMIDto.getDateCreated());
// 			else
// 				loanEMI.setDateCreated(new Date());
//
//			loanEMI.setRemarks(loanEMIDto.getRemarks());
//			loanEMI.setLoanIssue(loanIssue);
			
	//	}
 	//	else {
// 			System.out.println("===================================in else===========================");
// 			loanEMI.setEmiNo(loanEMIDto.getEmiNo());
// 			loanEMI.setEmiDate(loanEMIDto.getEmiDate());
//			loanEMI.setEmiAmount(loanEMIDto.getEmiAmount());
//			loanEMI.setEmiStatus(loanEMIDto.getEmiStatus());
//			loanEMI.setUserId(loanEMIDto.getUserId());
// 			loanEMI.setDateCreated(loanEMIDto.getDateCreated());
// 			loanEMI.setRemarks(loanEMIDto.getRemarks());
//			loanEMI.setLoanIssue(loanIssue);
 	//	}
		return loanEMI;
	}
	
	
	 
	
 	public LoanIssueDTO databaseModelToUiDtoSettement(LoanIssue loanIssue) {
  		LoanIssueDTO loanIssueDto = new LoanIssueDTO();
		BigDecimal totalEmiAmount = new BigDecimal(0);

 		loanIssueDto.setTransactionNo(loanIssue.getTransactionNo());
		loanIssueDto.setTransactionDate(loanIssue.getTransactionDate());
		loanIssueDto.setEmployeeId(loanIssue.getEmployee().getEmployeeId());
		loanIssueDto.setEmployeeCode(loanIssue.getEmployee().getEmployeeCode());
		loanIssueDto.setDateOfJoining(loanIssue.getEmployee().getDateOfJoining());
		loanIssueDto.setLoanType(loanIssue.getLoanType());
  		//loanIssueDto.setInterestType(loanIssue.getInterestType());
  		
		loanIssueDto.setDepartmentName(loanIssue.getEmployee().getDepartment().getDepartmentName());
		loanIssueDto.setDesignationName(loanIssue.getEmployee().getDesignation().getDesignationName());
		loanIssueDto.setEmployeeName(loanIssue.getEmployee().getFirstName() + " " + loanIssue.getEmployee().getLastName());
		loanIssueDto.setLoanAmount(loanIssue.getLoanAmount());
		loanIssueDto.setEmiStartDate(loanIssue.getEmiStartDate());

		loanIssueDto.setIssueDate(loanIssue.getIssueDate());
		loanIssueDto.setNoOfEmi(loanIssue.getNoOfEmi());
		//loanIssueDto.setRateOfInterest(loanIssue.getRateOfInterest());
		loanIssueDto.setNaration(loanIssue.getNaration());
		loanIssueDto.setLoanTypeLabel(DropDownCache.getInstance()
				.getDropDownValue(DropDownEnum.LoanType.getDropDownName(), loanIssue.getLoanType()));
 		loanIssueDto.setEmiAmount(loanIssue.getEmiAmount());
 		loanIssueDto.setUserId(loanIssue.getUserId());
		loanIssueDto.setDateCreated(loanIssue.getDateCreated());
		loanIssueDto.setActiveStatus(loanIssue.getActiveStatus());
 		loanIssueDto.setActiveStatusLabel(DropDownCache.getInstance()
				.getDropDownValue(DropDownEnum.Status.getDropDownName(), loanIssue.getActiveStatus()));
 		loanIssueDto.setSettlementAmount(loanIssue.getSettlementAmount());
 		loanIssueDto.setPaymentMode(loanIssue.getPaymentMode());
 		loanIssueDto.setInstrumentNo(loanIssue.getInstrumentNo());
 		loanIssueDto.setRemark(loanIssue.getRemark());
 		loanIssueDto.setIsSettlementCompleted(loanIssue.getIsSettlementCompleted());
		BigDecimal loanAmount = loanIssue.getLoanAmount();
  		for (LoanEMI emi : loanIssue.getLoanEmis()) {
			if (emi.getEmiAmount() != null) {
				totalEmiAmount = totalEmiAmount.add(emi.getEmiAmount());
			}
		}
		BigDecimal pendingAmount = loanAmount.subtract(totalEmiAmount);
  		 loanIssueDto.setLoanPendingAmount(pendingAmount);
		loanIssueDto.setLoanEmisDto(databaseModelToLoanEmiDtoSettlement(loanIssue.getLoanEmis(), loanIssue));

		
		
		return loanIssueDto;
 		
	}

	

	public List<LoanEMIDTO> databaseModelToLoanEmiDtoSettlement(List<LoanEMI> loanEMIList,LoanIssue loanIssue) {
		List<LoanEMIDTO> loanEMIDtoList = new ArrayList<LoanEMIDTO>();

		for (LoanEMI loanEMI : loanEMIList) {
			loanEMIDtoList.add(databaseModelToLoanEmiDtoSettlement(loanEMI,loanIssue));
		}
		return loanEMIDtoList;
 	}
	public LoanEMIDTO databaseModelToLoanEmiDtoSettlement(LoanEMI loanEMI,LoanIssue loanIssue) {
		LoanEMIDTO loanEMIDto = new LoanEMIDTO();
		loanEMIDto.setEmiNo(loanEMI.getEmiNo());
		loanEMIDto.setTransactionNo(loanEMI.getLoanIssue().getTransactionNo());
		loanEMIDto.setEmiDate(loanEMI.getEmiDate());
		loanEMIDto.setEmiAmount(loanEMI.getEmiAmount());
		loanEMIDto.setEmiStatus(loanEMI.getEmiStatus());
		loanEMIDto.setUserId(loanEMI.getUserId());
		loanEMIDto.setDateCreated(loanEMI.getDateCreated());
		loanEMIDto.setRemarks(loanEMI.getRemarks());
		if(loanEMI.getEmiStatus().equals("AC"))
			loanEMIDto.setEmiStatusLabel("Active"); 
		else if(loanEMI.getEmiStatus().equals("CE"))
			loanEMIDto.setEmiStatusLabel("Closed"); 

		
  		return loanEMIDto;
	}
	public LoanIssueDTO databaseObjModelToUiDto(Object[] loanIssueObj) {
		LoanIssueDTO loanIssueDto = new LoanIssueDTO();
		
		
			Long employeeId = loanIssueObj[0] != null ? (ConverterUtil.getLong(loanIssueObj[0])) : null;
			String firstName = loanIssueObj[1] != null ? (String) loanIssueObj[1] : null;
			String lastName = loanIssueObj[2] != null ? (String) loanIssueObj[2] : null;
			BigDecimal totalLoanAmount = loanIssueObj[3] != null ? (new BigDecimal(loanIssueObj[3].toString())) : null;
			String departmentName = loanIssueObj[4] != null ? (String) loanIssueObj[4] : null;
			String designationName = loanIssueObj[5] != null ? (String) loanIssueObj[5] : null;
			String employeeCode = loanIssueObj[6] != null ? (String) loanIssueObj[6] : null;
			BigDecimal loanPendingAmount=loanIssueObj[7] !=null?(new BigDecimal(loanIssueObj[7].toString())):null;
			Long transactionNo=loanIssueObj[8]!=null?ConverterUtil.getLong(loanIssueObj[8]):null;
			String gradeName=loanIssueObj[9]!=null?loanIssueObj[9].toString():null;
			Date dateCreated=loanIssueObj[10]!=null?(Date)loanIssueObj[10]:null;
			int noOfEmi=loanIssueObj[11]!=null?Integer.parseInt(loanIssueObj[11].toString()):null;
			BigDecimal emiAmount=loanIssueObj[12]!=null?(new BigDecimal(loanIssueObj[12].toString())):null;
			Date dateOfJoining=loanIssueObj[13]!=null?(Date)loanIssueObj[13]:null;
			loanIssueDto.setEmployeeId(employeeId);
			loanIssueDto.setEmployeeName(firstName + " " + lastName);
			loanIssueDto.setLoanAmount(totalLoanAmount);
			loanIssueDto.setDepartmentName(departmentName);
			loanIssueDto.setDesignationName(designationName);
			loanIssueDto.setEmployeeCode(employeeCode);
			loanIssueDto.setLoanPendingAmount(loanPendingAmount);
			loanIssueDto.setTransactionNo(transactionNo);
			loanIssueDto.setGradeName(gradeName);
			loanIssueDto.setDateCreated(dateCreated);
			loanIssueDto.setDateCreated(dateCreated);
			loanIssueDto.setNoOfEmi(noOfEmi);
			loanIssueDto.setEmiAmount(emiAmount);
			loanIssueDto.setDateOfJoining(dateOfJoining);
			
			
		
		return loanIssueDto;
	}


	public List<LoanIssueDTO> databaseModelToUiDtoListLoanIssueSearch(List<Object[]> loanIssueObjList) {
		List<LoanIssueDTO> loanIssueDtoList = new ArrayList<LoanIssueDTO>();
		for (Object[] loanIssueObj : loanIssueObjList) {
			BigDecimal totalEmiAmount = new BigDecimal(0);
			LoanIssueDTO loanIssueDto = new LoanIssueDTO();
			
			Long transactionNo=loanIssueObj[0]!=null?Long.parseLong(loanIssueObj[0].toString()):null;
			Long employeeId = loanIssueObj[1] != null ? Long.parseLong(loanIssueObj[1].toString()) : null;
			String empName = loanIssueObj[2] != null ? (String) loanIssueObj[2] : null;
			String employeeCode = loanIssueObj[3] != null ? (String) loanIssueObj[3] : null;
			String departmentName = loanIssueObj[4] != null ? (String) loanIssueObj[4] : null;
			String designationName = loanIssueObj[5] != null ? (String) loanIssueObj[5] : null;
			BigDecimal loanAmount = loanIssueObj[6] != null ? (new BigDecimal(loanIssueObj[6].toString())) : null;
			int noOfEmi=loanIssueObj[7]!=null?Integer.parseInt(loanIssueObj[7].toString()):null;
			BigDecimal loanPendingAmount=loanIssueObj[8] !=null?(new BigDecimal(loanIssueObj[8].toString())):null;
			BigDecimal emiAmount=loanIssueObj[9] !=null?(new BigDecimal(loanIssueObj[9].toString())):null;
			List<LoanEMI> emiList=new ArrayList<LoanEMI>();
			try {
			 emiList= loanEMIRepository.findLoanIssueList(transactionNo);
			}
			catch(NullPointerException e) {
			 
			}
			
			if(emiList!=null) {
			for (LoanEMI emi : emiList) {
				if (emi.getEmiAmount() != null) {
					totalEmiAmount = totalEmiAmount.add(emi.getEmiAmount());
				}
			}
			}
			loanIssueDto.setTotalEmiAmount(totalEmiAmount);
			
			BigDecimal pendingAmount = loanAmount.subtract(totalEmiAmount);
		
			
			loanIssueDto.setLoanPendingAmount(pendingAmount);
			
			
			if (loanPendingAmount!= null) {
				
				loanIssueDto.setLoanPendingAmount(loanPendingAmount);
			}
			BigDecimal remaining = new BigDecimal(0);
			remaining = loanAmount.subtract(emiAmount);
			
			int remainingEmi=0;
			if(emiList!=null &&emiList.size()==0)
				remainingEmi = noOfEmi;
			else
			 remainingEmi=(int)Math.ceil(pendingAmount.doubleValue()/emiAmount.doubleValue());
			loanIssueDto.setRemainingEmi(remainingEmi);
			
			loanIssueDto.setTotalEmiAmount(totalEmiAmount);
			loanIssueDto.setEmployeeId(employeeId);
			loanIssueDto.setNoOfEmi(noOfEmi);
			loanIssueDto.setEmployeeName(empName);
		    loanIssueDto.setLoanAmount(loanAmount);
			loanIssueDto.setDepartmentName(departmentName);
			loanIssueDto.setDesignationName(designationName);
			loanIssueDto.setEmployeeCode(employeeCode);
		 
			loanIssueDto.setTransactionNo(transactionNo);
			loanIssueDtoList.add(loanIssueDto);
		}
		return loanIssueDtoList;
	}
}
