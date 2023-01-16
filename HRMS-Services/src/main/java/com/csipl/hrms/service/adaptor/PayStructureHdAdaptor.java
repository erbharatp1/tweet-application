package com.csipl.hrms.service.adaptor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.csipl.hrms.common.enums.ActiveStatusEnum;
import com.csipl.hrms.common.util.DateUtils;
import com.csipl.hrms.dto.employee.EmployeeDTO;
import com.csipl.hrms.dto.employee.PayStructureHdDTO;
import com.csipl.hrms.dto.payrollprocess.PayOutDTO;
import com.csipl.hrms.model.employee.Employee;
import com.csipl.hrms.model.employee.PayStructureHd;
import com.csipl.hrms.model.organisation.Grade;
import com.csipl.hrms.common.enums.StandardDeductionEnum;

public class PayStructureHdAdaptor implements Adaptor<PayStructureHdDTO, PayStructureHd> {
	PayStructureAdaptor payStructureAdaptor = new PayStructureAdaptor();

	@Override
	public PayStructureHd uiDtoToDatabaseModel(PayStructureHdDTO payStructureHdDto) {
		
	 

		PayStructureHd payStructureHd = new PayStructureHd();
		Grade grade = new Grade();
		Employee employee = new Employee();
		payStructureHd.setPayStructureHdId(payStructureHdDto.getPayStructureHdId());
		payStructureHd.setUpdateFlage(payStructureHdDto.isUpdateFlag());
		employee.setEmployeeId(payStructureHdDto.getEmployeeId());
		payStructureHd.setEmployee(employee);
		grade.setGradesId(payStructureHdDto.getGradesId());
		payStructureHd.setGrade(grade);
		payStructureHd.setGrossPay(payStructureHdDto.getGrossPay());
		
		
		/*if (payStructureHdDto.isRevisionUpdateFlag())
			payStructureHd.setEffectiveDate(payStructureHdDto.getEffectiveDate());
		else
			payStructureHd.setEffectiveDate(new Date());*/
		if(payStructureHdDto.getProcessMonth()!=null)
		payStructureHd.setEffectiveDate(DateUtils.getDateForProcessMonth(payStructureHdDto.getProcessMonth()));
		
		payStructureHd.setActiveStatus(payStructureHdDto.getActiveStatus());

		if (payStructureHdDto.getDateEnd() != null)
			payStructureHd.setDateEnd(payStructureHdDto.getDateEnd());
		payStructureHd.setActiveStatus(ActiveStatusEnum.ActiveStatus.getActiveStatus());
		payStructureHd.setIsNoPFDeduction(payStructureHdDto.getIsNoPFDeduction());

		if (payStructureHd.getPayStructureHdId() != null) {
		} else {
			payStructureHd.setDateCreated(new Date());
		}
 		payStructureHd.setProcessMonth(payStructureHdDto.getProcessMonth());
		payStructureHd.setDateUpdate(new Date());
		payStructureHd.setUserId(payStructureHdDto.getUserId());
		payStructureHd.setUserIdUpdate(payStructureHdDto.getUserIdUpdate());
		payStructureHd.setPayStructures(payStructureAdaptor.uiDtoToDatabaseModelListWithId(payStructureHdDto.getPayStructureDtoList(), payStructureHd));
		
		
		for (PayOutDTO payOutDTO : payStructureHdDto.getPayOutDtoList()) {
			
			
			if(payOutDTO.getPayHeadId()==StandardDeductionEnum.PF_Employee_Contribution.getStandardDeduction()) 
				payStructureHd.setEpfEmployee(payOutDTO.getAmount());
 		if(payOutDTO.getPayHeadId()==StandardDeductionEnum.PF_Employer_Contribution.getStandardDeduction())  
			payStructureHd.setEpfEmployer(payOutDTO.getAmount());

 		if(payOutDTO.getPayHeadId()==StandardDeductionEnum.ESI_Employee_Contribution.getStandardDeduction()) 
			payStructureHd.setEsiEmployee(payOutDTO.getAmount());

 		if(payOutDTO.getPayHeadId()==StandardDeductionEnum.ESI_Employer_Contribution.getStandardDeduction())  
			payStructureHd.setEsiEmployer(payOutDTO.getAmount());

 		if(payOutDTO.getPayHeadId()==StandardDeductionEnum.PT.getStandardDeduction())  
			payStructureHd.setProfessionalTax(payOutDTO.getAmount());
 		
 		if(payOutDTO.getPayHeadId()==StandardDeductionEnum.LWF.getStandardDeduction())  
			payStructureHd.setLwfEmployeeAmount(payOutDTO.getAmount());
 		
 		if(payOutDTO.getPayHeadId()==StandardDeductionEnum.LWF_Employer.getStandardDeduction())  
			payStructureHd.setLwfEmployerAmount(payOutDTO.getAmount());
 		if(payOutDTO.getPayHeadId()==StandardDeductionEnum.Pension_Employer_Contribution.getStandardDeduction())  
			payStructureHd.setEpfEmployeePension(payOutDTO.getAmount());

 		}
		payStructureHd.setCostToCompany(payStructureHdDto.getCtc());
		payStructureHd.setNetPay(payStructureHdDto.getNetPay());
		payStructureHd.setCtc(payStructureHdDto.getCtc());
		
		return payStructureHd;
	
	/*	PF_Employee_Contribution ( 101 ),
		PF_Employer_Contribution ( 102 ),
 		ESI_Employee_Contribution ( 104 ),
		ESI_Employer_Contribution ( 105 ),
		PT( 106 ),*/
	 
	
	}

	@Override
	public PayStructureHdDTO databaseModelToUiDto(PayStructureHd payStructureHd) {
		PayStructureHdDTO payStructureHdDto = new PayStructureHdDTO();
		if (payStructureHd.getEmployee() != null)
			payStructureHdDto.setEmployeeId(payStructureHd.getEmployee().getEmployeeId());

		payStructureHdDto.setPayStructureHdId(payStructureHd.getPayStructureHdId());
		if (payStructureHd.getGrade() != null) {
			payStructureHdDto.setGradesId(payStructureHd.getGrade().getGradesId());
			payStructureHdDto.setGradesName(payStructureHd.getGrade().getGradesName());

		}
		payStructureHdDto.setGrossPay(payStructureHd.getGrossPay());
		payStructureHdDto.setEffectiveDate(payStructureHd.getEffectiveDate());
		payStructureHdDto.setDateEnd(payStructureHd.getDateEnd());
		payStructureHdDto.setActiveStatus(payStructureHd.getActiveStatus());
		payStructureHdDto.setUserId(payStructureHd.getUserId());
		payStructureHdDto.setDateCreated(payStructureHd.getDateCreated());
		payStructureHdDto.setEmployeeCode(payStructureHd.getEmployee().getEmployeeCode());
		payStructureHdDto.setFirstName(payStructureHd.getEmployee().getFirstName());
		payStructureHdDto.setLastName(payStructureHd.getEmployee().getLastName());
		payStructureHdDto.setDepartmentName(payStructureHd.getEmployee().getDepartment().getDepartmentName());
		payStructureHdDto.setDesignationName(payStructureHd.getEmployee().getDesignation().getDesignationName());
		payStructureHdDto.setProcessMonth(payStructureHd.getProcessMonth());
		if (payStructureHd.getPayStructures() != null)
			payStructureHdDto.setPayStructureDtoList(
					payStructureAdaptor.databaseModelToUiDtoList(payStructureHd.getPayStructures()));
		payStructureHdDto.setActiveStatus(payStructureHd.getActiveStatus());
		payStructureHdDto.setIsNoPFDeduction(payStructureHd.getIsNoPFDeduction());
		payStructureHdDto.setLwfEmployeeAmount(payStructureHd.getLwfEmployeeAmount());
		payStructureHdDto.setLwfEmployerAmount(payStructureHd.getLwfEmployerAmount());
		List<PayOutDTO> payOutDTOList=new ArrayList<>();
		if(payStructureHd.getEpfEmployee()!=null)
		{
			PayOutDTO PayOutDTO=new PayOutDTO();
			PayOutDTO.setAmount(payStructureHd.getEpfEmployee());
			PayOutDTO.setPayHeadId(StandardDeductionEnum.PF_Employee_Contribution.getStandardDeduction());
			payOutDTOList.add(PayOutDTO);
   		}
 		if(payStructureHd.getEpfEmployer()!=null)
		{
			PayOutDTO PayOutDTO=new PayOutDTO();
			PayOutDTO.setAmount(payStructureHd.getEpfEmployer());
			PayOutDTO.setPayHeadId(StandardDeductionEnum.PF_Employer_Contribution.getStandardDeduction());
			payOutDTOList.add(PayOutDTO);
   		}
 		if(payStructureHd.getEsiEmployee()!=null)
		{
			PayOutDTO PayOutDTO=new PayOutDTO();
			PayOutDTO.setAmount(payStructureHd.getEsiEmployee());
			PayOutDTO.setPayHeadId(StandardDeductionEnum.ESI_Employee_Contribution.getStandardDeduction());
			payOutDTOList.add(PayOutDTO);
   		}
 		if(payStructureHd.getEsiEmployer()!=null)
		{
			PayOutDTO PayOutDTO=new PayOutDTO();
			PayOutDTO.setAmount(payStructureHd.getEsiEmployer());
			PayOutDTO.setPayHeadId(StandardDeductionEnum.ESI_Employer_Contribution.getStandardDeduction());
			payOutDTOList.add(PayOutDTO);
   		}
 		if(payStructureHd.getProfessionalTax()!=null)
		{
			PayOutDTO PayOutDTO=new PayOutDTO();
			PayOutDTO.setAmount(payStructureHd.getProfessionalTax());
			PayOutDTO.setPayHeadId(StandardDeductionEnum.PT.getStandardDeduction());
			payOutDTOList.add(PayOutDTO);
   		}
 		payStructureHdDto.setPayOutDtoList(payOutDTOList);
 		
 		if(payStructureHd.getNetPay()!=null)
		{
 			payStructureHdDto.setNetPay(payStructureHd.getNetPay());
   		}
 		if(payStructureHd.getCtc()!=null)
		{
 			payStructureHdDto.setCtc( payStructureHd.getCtc());
   		}
		return payStructureHdDto;
	}

	@Override
	public List<PayStructureHd> uiDtoToDatabaseModelList(List<PayStructureHdDTO> payStructureHdDtoList) {

		List<PayStructureHd> PayStructureHdList = new ArrayList<PayStructureHd>();

		payStructureHdDtoList.forEach(payStructureHdDTO -> {
			PayStructureHd payStructureHd = uiDtoToDatabaseModel(payStructureHdDTO);

			payStructureHd.setPayStructures(payStructureAdaptor
					.uiDtoToDatabaseModelListWithId(payStructureHdDTO.getPayStructureDtoList(), payStructureHd));

			PayStructureHdList.add(payStructureHd);
		});

		return PayStructureHdList;
	}

	public List<PayStructureHd> uiDtoToDatabaseModelList(List<PayStructureHdDTO> payStructureHdDtoList,
			Employee employee) {

		List<PayStructureHd> PayStructureHdList = new ArrayList<PayStructureHd>();

		payStructureHdDtoList.forEach(payStructureHdDTO -> {
			PayStructureHd payStructureHd = uiDtoToDatabaseModel(payStructureHdDTO, employee);

			/*
			 * payStructureHd.setPayStructures(payStructureAdaptor
			 * .uiDtoToDatabaseModelListWithId( payStructureHdDTO.getPayStructureDtoList(),
			 * payStructureHd) );
			 */

			PayStructureHdList.add(payStructureHd);
		});

		return PayStructureHdList;
	}

	public PayStructureHd uiDtoToDatabaseModel(PayStructureHdDTO payStructureHdDto, Employee employee) {
		PayStructureHd payStructureHd = new PayStructureHd();
		Grade grade = new Grade();

		payStructureHd.setPayStructureHdId(payStructureHdDto.getPayStructureHdId());
		employee.setEmployeeId(payStructureHdDto.getEmployeeId());
		payStructureHd.setEmployee(employee);

		payStructureHd.setUserId(employee.getUserId());
		payStructureHd.setDateCreated(new Date());
		grade.setGradesId(payStructureHdDto.getGradesId());
		payStructureHd.setGrade(grade);
		payStructureHd.setGrossPay(payStructureHdDto.getGrossPay());
		payStructureHd.setEffectiveDate(payStructureHdDto.getEffectiveDate());
		payStructureHd.setActiveStatus(payStructureHdDto.getActiveStatus());
		payStructureHd.setPayStructures(payStructureAdaptor
				.uiDtoToDatabaseModelListWithId(payStructureHdDto.getPayStructureDtoList(), payStructureHd, employee));
		payStructureHd.setIsNoPFDeduction(payStructureHdDto.getIsNoPFDeduction());
		payStructureHdDto.setCtc(payStructureHdDto.getCtc());
		return payStructureHd;
	}

	@Override
	public List<PayStructureHdDTO> databaseModelToUiDtoList(List<PayStructureHd> payStructureHdList) {
		List<PayStructureHdDTO> payStructureHdDtoList = new ArrayList<PayStructureHdDTO>();
		for (PayStructureHd payStructureHd : payStructureHdList) {
			payStructureHdDtoList.add(databaseModelToUiDto(payStructureHd));
		}
		return payStructureHdDtoList;
	}

	public PayStructureHd uiDtoToDatabaseModelRevision(PayStructureHdDTO payStructureHdDto) {
		PayStructureHd payStructureHd = new PayStructureHd();
		Grade grade = new Grade();
		Employee employee = new Employee();
		// payStructureHd.setPayStructureHdId(payStructureHdDto.getPayStructureHdId());
		payStructureHd.setUserId(payStructureHdDto.getUserId());
		payStructureHd.setDateCreated(payStructureHdDto.getDateCreated());
		payStructureHd.setUpdateFlage(payStructureHdDto.isUpdateFlag());
		employee.setEmployeeId(payStructureHdDto.getEmployeeId());
		payStructureHd.setEmployee(employee);
		grade.setGradesId(payStructureHdDto.getGradesId());
		payStructureHd.setGrade(grade);
		payStructureHd.setGrossPay(payStructureHdDto.getGrossPay());
		//payStructureHd.setEffectiveDate(payStructureHdDto.getEffectiveDate());
		payStructureHd.setEffectiveDate(DateUtils.getDateForProcessMonth(payStructureHdDto.getProcessMonth()));

		payStructureHd.setActiveStatus(payStructureHdDto.getActiveStatus());
		payStructureHd.setProcessMonth(payStructureHdDto.getProcessMonth());
		
		if (payStructureHdDto.getPayStructureHdId() != null) {
		} else {
			payStructureHd.setDateCreated(new Date());
		}
		
		payStructureHd.setDateUpdate(new Date());
		payStructureHd.setUserId(payStructureHdDto.getUserId());
		payStructureHd.setUserIdUpdate(payStructureHdDto.getUserIdUpdate());
		
		
		payStructureHd.setPayStructures(payStructureAdaptor
				.uiDtoToDatabaseModelListWithIdRevision(payStructureHdDto.getPayStructureDtoList(), payStructureHd));

		payStructureHd.setActiveStatus(ActiveStatusEnum.ActiveStatus.getActiveStatus());
		payStructureHd.setIsNoPFDeduction(payStructureHdDto.getIsNoPFDeduction());
		payStructureHdDto.setCtc(payStructureHdDto.getCtc());
		return payStructureHd;
	}
	
	public List<EmployeeDTO> databaseModelObjtoUiDto(List<Object[]> employees)
	{
		List<EmployeeDTO> employeeDTOList=new ArrayList<EmployeeDTO>();
		
		for(Object[] emp:employees)
		{
			EmployeeDTO employeeDTO=new EmployeeDTO();
			
			String firstName=emp[0]!=null?(String)emp[0]:null;
			String lastName=emp[1]!=null?(String)emp[1]:null;
			Date dateOfJoining=emp[2]!=null?(Date)emp[2]:null;
			String gradeName=emp[3]!=null?(String)emp[3]:null;
			String departmentName=emp[4]!=null?(String)emp[4]:null;
			Date dateOfBirth=emp[5]!=null?(Date)emp[5]:null;
			String designationName=emp[6]!=null?(String)emp[6]:null;
			String employeeCode=emp[7]!=null?(String)emp[7]:null;
 			Long employeeId = emp[8] != null ? Long.parseLong(emp[8].toString()) : null;
 			String reportingFirstName=emp[9]!=null?(String)emp[9]:null;
			String reportingLastName=emp[10]!=null?(String)emp[10]:null;
			String employeeLogoPath=emp[11]!=null?(String)emp[11]:null;
			

			employeeDTO.setFirstName(firstName);
			employeeDTO.setLastName(lastName);
			employeeDTO.setDateOfJoining(dateOfJoining);
			employeeDTO.setGradeName(gradeName);
			employeeDTO.setDepartmentName(departmentName);
			employeeDTO.setDateOfBirth(dateOfBirth);
			employeeDTO.setDesignationName(designationName);
			employeeDTO.setEmployeeCode(employeeCode);
			employeeDTO.setEmployeeId(employeeId);
			employeeDTO.setReportingEmployeeFirstName(reportingFirstName);
			employeeDTO.setReportingEmployeeLastName(reportingLastName);
			employeeDTO.setEmployeeLogoPath(employeeLogoPath);

			employeeDTOList.add(employeeDTO);
			
		}
		
		return employeeDTOList;
	}

}
