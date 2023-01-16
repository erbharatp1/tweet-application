package com.csipl.hrms.service.recruitement.adaptor;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.csipl.hrms.common.enums.StandardDeductionEnum;
import com.csipl.hrms.dto.employee.PayStructureDTO;
import com.csipl.hrms.dto.payrollprocess.PayOutDTO;
import com.csipl.hrms.dto.recruitment.CandidatePayStructureDTO;
import com.csipl.hrms.dto.recruitment.CandidatePayStructureHdDTO;
import com.csipl.hrms.model.employee.PayStructure;
import com.csipl.hrms.model.employee.PayStructureHd;
import com.csipl.hrms.model.payroll.PayHead;
import com.csipl.hrms.model.recruitment.CandidatePayStructure;
import com.csipl.hrms.model.recruitment.CandidatePayStructureHd;
import com.csipl.hrms.model.recruitment.InterviewScheduler;
import com.csipl.hrms.service.adaptor.Adaptor;

public class CandidatePaystructureAdaptor implements Adaptor<CandidatePayStructureHdDTO, CandidatePayStructureHd> {

	@Override
	public List<CandidatePayStructureHd> uiDtoToDatabaseModelList(List<CandidatePayStructureHdDTO> uiobj) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<CandidatePayStructureHdDTO> databaseModelToUiDtoList(List<CandidatePayStructureHd> dbobj) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CandidatePayStructureHd uiDtoToDatabaseModel(CandidatePayStructureHdDTO candidatePayStructureHdDto) {
		CandidatePayStructureHd candidatePayStructureHd = new CandidatePayStructureHd();
		candidatePayStructureHd.setEpfEmployeePension(candidatePayStructureHdDto.getEpfEmployeePension());
		candidatePayStructureHd.setCandidatePaystructureHdId(candidatePayStructureHdDto.getCandidatePaystructureHdId());
		candidatePayStructureHd.setEffectiveDate(candidatePayStructureHdDto.getEffectiveDate());
		candidatePayStructureHd.setEpfEmployee(candidatePayStructureHdDto.getEpfEmployee());
		candidatePayStructureHd.setEpfEmployer(candidatePayStructureHdDto.getEpfEmployer());
		candidatePayStructureHd.setEsiEmployee(candidatePayStructureHdDto.getEsiEmployee());
		candidatePayStructureHd.setEsiEmployer(candidatePayStructureHdDto.getEsiEmployer());
		candidatePayStructureHd.setGrossPay(candidatePayStructureHdDto.getGrossPay());
		candidatePayStructureHd.setCtc(candidatePayStructureHdDto.getCtc());
		candidatePayStructureHd.setLwfEmployeeAmount(candidatePayStructureHdDto.getLwfEmployeeAmount());
		candidatePayStructureHd.setLwfEmployerAmount(candidatePayStructureHdDto.getLwfEmployerAmount());
		candidatePayStructureHd.setNetPay(candidatePayStructureHdDto.getNetPay());
		candidatePayStructureHd.setProcessMonth(candidatePayStructureHdDto.getProcessMonth());
		candidatePayStructureHd.setActiveStatus("AC");
		candidatePayStructureHd.setDateCreated(new Date());;
		candidatePayStructureHd.setProfessionalTax(candidatePayStructureHdDto.getProfessionalTax());
		candidatePayStructureHd.setEffectiveDate(candidatePayStructureHdDto.getEffectiveDate());
		InterviewScheduler interviewSchedular = new InterviewScheduler();
		interviewSchedular.setInterviewScheduleId(candidatePayStructureHdDto.getInterviewScheduleId());
		candidatePayStructureHd.setInterviewScheduler(interviewSchedular);
		candidatePayStructureHd.setCandidatePayStructure(uiDtoToDatabaseModelListWithId(candidatePayStructureHdDto.getPayStructureDtoList(), candidatePayStructureHd));

		for (PayOutDTO payOutDTO : candidatePayStructureHdDto.getPayOutDtoList()) {
			
			
			if(payOutDTO.getPayHeadId()==StandardDeductionEnum.PF_Employee_Contribution.getStandardDeduction()) 
				candidatePayStructureHd.setEpfEmployee(payOutDTO.getAmount());
 		if(payOutDTO.getPayHeadId()==StandardDeductionEnum.PF_Employer_Contribution.getStandardDeduction())  
 			candidatePayStructureHd.setEpfEmployer(payOutDTO.getAmount());

 		if(payOutDTO.getPayHeadId()==StandardDeductionEnum.ESI_Employee_Contribution.getStandardDeduction()) 
 			candidatePayStructureHd.setEsiEmployee(payOutDTO.getAmount());

 		if(payOutDTO.getPayHeadId()==StandardDeductionEnum.ESI_Employer_Contribution.getStandardDeduction())  
 			candidatePayStructureHd.setEsiEmployer(payOutDTO.getAmount());

 		if(payOutDTO.getPayHeadId()==StandardDeductionEnum.PT.getStandardDeduction())  
 			candidatePayStructureHd.setProfessionalTax(payOutDTO.getAmount());
 		
 		if(payOutDTO.getPayHeadId()==StandardDeductionEnum.LWF.getStandardDeduction())  
 			candidatePayStructureHd.setLwfEmployeeAmount(payOutDTO.getAmount());
 		
 		if(payOutDTO.getPayHeadId()==StandardDeductionEnum.LWF_Employer.getStandardDeduction())  
 			candidatePayStructureHd.setLwfEmployerAmount(payOutDTO.getAmount());
 		if(payOutDTO.getPayHeadId()==StandardDeductionEnum.Pension_Employer_Contribution.getStandardDeduction())  
 			candidatePayStructureHd.setEpfEmployeePension(payOutDTO.getAmount());

 		}
		
		candidatePayStructureHd.setNetPay(candidatePayStructureHdDto.getNetPay());
		
		
		
		return candidatePayStructureHd;
	}
	public List<CandidatePayStructure> uiDtoToDatabaseModelListWithId(List<CandidatePayStructureDTO> payStructureDtoList,
			CandidatePayStructureHd payStructureHd) {
		List<CandidatePayStructure> payStructureList = new ArrayList<CandidatePayStructure>();
		for (CandidatePayStructureDTO payStructureDto : payStructureDtoList) {
			payStructureList.add(uiDtoToDatabaseModelWithId(payStructureDto, payStructureHd));
		}
		return payStructureList;
	}
	
	public CandidatePayStructure uiDtoToDatabaseModelWithId(CandidatePayStructureDTO payStructureDto, CandidatePayStructureHd payStructureHd) {
		CandidatePayStructure payStructure = new CandidatePayStructure();
		PayHead payHead = new PayHead();
		payStructure.setCandidatePaystructureId(payStructureDto.getPayStructureId());
		payHead.setPayHeadId(payStructureDto.getPayHeadId());
 		payStructure.setPayHead(payHead);
		payStructure.setAmount(payStructureDto.getAmount());
		payStructure.setCandidatePayStructureHd(payStructureHd);

		if (payStructureDto.getPayStructureId() != null) {
			payStructure.setUserId(payStructureDto.getUserId());
			payStructure.setDateCreated(payStructureDto.getDateCreated());
		} else {
			payStructure.setUserId(payStructureHd.getUserId());
			payStructure.setDateCreated(new Date());
		}
		//payStructure.setUserIdUpdate(payStructureHd.getUserIdUpdate());
		//payStructure.setDateUpdate(new Date());

		return payStructure;

	}

	@Override
	public CandidatePayStructureHdDTO databaseModelToUiDto(CandidatePayStructureHd candidatePayStructureHd) {
		CandidatePayStructureHdDTO candidatePayStructureHdDto = new CandidatePayStructureHdDTO() ;
		/*if (payStructureHd.getEmployee() != null)
			payStructureHdDto.setEmployeeId(payStructureHd.getEmployee().getEmployeeId());
*/
		candidatePayStructureHdDto.setCandidatePaystructureHdId(candidatePayStructureHd.getCandidatePaystructureHdId());
		/*if (payStructureHd.getGrade() != null) {
			payStructureHdDto.setGradesId(payStructureHd.getGrade().getGradesId());
			payStructureHdDto.setGradesName(payStructureHd.getGrade().getGradesName());

		}*/
		candidatePayStructureHdDto.setGrossPay(candidatePayStructureHd.getGrossPay());
		candidatePayStructureHdDto.setEffectiveDate(candidatePayStructureHd.getEffectiveDate());
		candidatePayStructureHdDto.setActiveStatus(candidatePayStructureHd.getActiveStatus());
		candidatePayStructureHdDto.setUserId(candidatePayStructureHd.getUserId());
		candidatePayStructureHdDto.setDateCreated(candidatePayStructureHd.getDateCreated());
		candidatePayStructureHdDto.setProcessMonth(candidatePayStructureHd.getProcessMonth());
		if (candidatePayStructureHd.getCandidatePayStructure() != null)
			candidatePayStructureHdDto.setPayStructureDtoList(
					CandidatePaystructureModelToUiDtoList(candidatePayStructureHd.getCandidatePayStructure()));
		candidatePayStructureHdDto.setActiveStatus(candidatePayStructureHd.getActiveStatus());
		//candidatePayStructureHdDto.setIsNoPFDeduction(candidatePayStructureHd.getIsNoPFDeduction());
		candidatePayStructureHdDto.setLwfEmployeeAmount(candidatePayStructureHd.getLwfEmployeeAmount());
		candidatePayStructureHdDto.setLwfEmployerAmount(candidatePayStructureHd.getLwfEmployerAmount());
		List<PayOutDTO> payOutDTOList=new ArrayList<>();
		if(candidatePayStructureHd.getEpfEmployee()!=null)
		{
			PayOutDTO PayOutDTO=new PayOutDTO();
			PayOutDTO.setAmount(candidatePayStructureHd.getEpfEmployee());
			PayOutDTO.setPayHeadId(StandardDeductionEnum.PF_Employee_Contribution.getStandardDeduction());
			payOutDTOList.add(PayOutDTO);
   		}
 		if(candidatePayStructureHd.getEpfEmployer()!=null)
		{
			PayOutDTO PayOutDTO=new PayOutDTO();
			PayOutDTO.setAmount(candidatePayStructureHd.getEpfEmployer());
			PayOutDTO.setPayHeadId(StandardDeductionEnum.PF_Employer_Contribution.getStandardDeduction());
			payOutDTOList.add(PayOutDTO);
   		}
 		if(candidatePayStructureHd.getEsiEmployee()!=null)
		{
			PayOutDTO PayOutDTO=new PayOutDTO();
			PayOutDTO.setAmount(candidatePayStructureHd.getEsiEmployee());
			PayOutDTO.setPayHeadId(StandardDeductionEnum.ESI_Employee_Contribution.getStandardDeduction());
			payOutDTOList.add(PayOutDTO);
   		}
 		if(candidatePayStructureHd.getEsiEmployer()!=null)
		{
			PayOutDTO PayOutDTO=new PayOutDTO();
			PayOutDTO.setAmount(candidatePayStructureHd.getEsiEmployer());
			PayOutDTO.setPayHeadId(StandardDeductionEnum.ESI_Employer_Contribution.getStandardDeduction());
			payOutDTOList.add(PayOutDTO);
   		}
 		if(candidatePayStructureHd.getProfessionalTax()!=null)
		{
			PayOutDTO PayOutDTO=new PayOutDTO();
			PayOutDTO.setAmount(candidatePayStructureHd.getProfessionalTax());
			PayOutDTO.setPayHeadId(StandardDeductionEnum.PT.getStandardDeduction());
			payOutDTOList.add(PayOutDTO);
   		}
 		candidatePayStructureHdDto.setPayOutDtoList(payOutDTOList);
 		
 		if(candidatePayStructureHd.getNetPay()!=null)
		{
 			candidatePayStructureHdDto.setNetPay(candidatePayStructureHd.getNetPay());
   		}
 		if(candidatePayStructureHd.getCtc()!=null)
		{
 			candidatePayStructureHdDto.setCtc( candidatePayStructureHd.getCtc());
   		}
		return candidatePayStructureHdDto;
	}
	public List<CandidatePayStructureDTO> CandidatePaystructureModelToUiDtoList(List<CandidatePayStructure> candidatePayStructureList) {
		List<CandidatePayStructureDTO> payStructureDtoList = new ArrayList<CandidatePayStructureDTO>();
		for (CandidatePayStructure candidatePayStructure : candidatePayStructureList) {
			payStructureDtoList.add(candidatePayStructureModelToUiDto(candidatePayStructure));
		}
		return payStructureDtoList;
	}

	private CandidatePayStructureDTO candidatePayStructureModelToUiDto(CandidatePayStructure candidatePayStructure) {
		CandidatePayStructureDTO payStructureDto = new CandidatePayStructureDTO();
		payStructureDto.setAmount(candidatePayStructure.getAmount());
		payStructureDto.setPayStructureId(candidatePayStructure.getCandidatePaystructureId());
		if (candidatePayStructure.getCandidatePayStructureHd() != null)
			payStructureDto.setCandidatePaystructureHdId(candidatePayStructure.getCandidatePayStructureHd().getCandidatePaystructureHdId());
		if (candidatePayStructure.getPayHead() != null) {
			payStructureDto.setPayHeadId(candidatePayStructure.getPayHead().getPayHeadId());
			payStructureDto.setPayHeadName(candidatePayStructure.getPayHead().getPayHeadName());
		}
		payStructureDto.setUserId(candidatePayStructure.getUserId());
		payStructureDto.setDateCreated(candidatePayStructure.getDateCreated());
		return payStructureDto;
	}
}
