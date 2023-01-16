package com.csipl.hrms.service.adaptor;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.csipl.hrms.common.constant.StatusMessage;
import com.csipl.hrms.common.util.DateUtils;
import com.csipl.hrms.dto.organisation.StateDTO;
import com.csipl.hrms.dto.payroll.ProfessionalTaxDTO;
import com.csipl.hrms.dto.payroll.ProfessionalTaxInfoDTO;
import com.csipl.hrms.model.common.Company;
import com.csipl.hrms.model.common.State;
import com.csipl.hrms.model.payroll.ProfessionalTax;
import com.csipl.hrms.model.payroll.ProfessionalTaxInfo;







public class ProfessionalTaxAdaptor implements Adaptor<ProfessionalTaxDTO, ProfessionalTax> {
	DateUtils dateUtils = new DateUtils();
	public final SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
	
	@Override
	public List<ProfessionalTax> uiDtoToDatabaseModelList(List<ProfessionalTaxDTO> professionalTaxDtoList) {
		List<ProfessionalTax> professionalTaxList = new ArrayList<ProfessionalTax>();
		for (ProfessionalTaxDTO professionalTaxDto : professionalTaxDtoList) {
			professionalTaxList.add(uiDtoToDatabaseModel(professionalTaxDto));
		}
		return professionalTaxList;
	}

	@Override
	public List<ProfessionalTaxDTO> databaseModelToUiDtoList(List<ProfessionalTax> professionalTaxList) {

		List<ProfessionalTaxDTO> professionalTaxDtoList = new ArrayList<ProfessionalTaxDTO>();
		for (ProfessionalTax professionalTax : professionalTaxList) {
			professionalTaxDtoList.add(databaseModelToUiDto(professionalTax));
		}
		return professionalTaxDtoList;
	}

	@Override
	public ProfessionalTax uiDtoToDatabaseModel(ProfessionalTaxDTO professionalTaxDto) {
		ProfessionalTax professionalTax = new ProfessionalTax();
		State state = new State();
		if(professionalTaxDto.getProfessionalHeadId()!=null) {
			professionalTax.setProfessionalHeadId(professionalTaxDto.getProfessionalHeadId());
		}
		
		state.setStateId(professionalTaxDto.getStateId());
		state.setStateName(professionalTaxDto.getStateName());
		professionalTax.setState(state);
		Company company = new Company();
		
		
		if(professionalTaxDto.getEffectiveStartDate()!=null && !("").equals( professionalTaxDto.getEffectiveStartDate()))
			professionalTax.setEffectiveStartDate(professionalTaxDto.getEffectiveStartDate());
		
		professionalTax.setEffectiveEndDate(professionalTaxDto.getEffectiveEndDate());
		professionalTax.setUserId(professionalTaxDto.getUserId());
		if(professionalTaxDto.getDateCreated()==null)
		{
			professionalTax.setDateCreated(new Date());
		}
		else
		{
			professionalTax.setDateCreated(professionalTaxDto.getDateCreated());
		}
		professionalTax.setActiveStatus(professionalTaxDto.getActiveStatus());
		professionalTax.setUserIdUpdate(professionalTaxDto.getUserIdUpdate());
		company.setCompanyId(professionalTaxDto.getCompanyId());
		professionalTax.setCompany(company);
	
		professionalTax.setProfessionalTaxInfos(uiDtoToProffessionalTaxInfoList(professionalTaxDto.getProfessionalTaxInfos(),professionalTax));
			
		return professionalTax;
	}

	@Override
	public ProfessionalTaxDTO databaseModelToUiDto(ProfessionalTax professionalTax) {

		ProfessionalTaxDTO professionalTaxDto = new ProfessionalTaxDTO();
		professionalTaxDto.setProfessionalHeadId(professionalTax.getProfessionalHeadId());
		professionalTaxDto.setStateId(professionalTax.getState().getStateId());
		professionalTaxDto.setCompanyId(professionalTax.getCompany().getCompanyId());
	//	professionalTaxDto.setGroupId(professionalTax.getGroupg().getGroupId());
		professionalTaxDto.setStateName(professionalTax.getState().getStateName());
		if(professionalTax.getEffectiveStartDate()!=null) {
		
			professionalTaxDto.setEffectiveStartDate( professionalTax.getEffectiveStartDate() );
			}
		
		professionalTaxDto.setEffectiveEndDate(professionalTax.getEffectiveEndDate());
		professionalTaxDto.setUserId(professionalTax.getUserId());
		professionalTaxDto.setDateCreated(professionalTax.getDateCreated());
		professionalTaxDto.setUserIdUpdate(professionalTax.getUserIdUpdate());
		professionalTaxDto.setActiveStatus(professionalTax.getActiveStatus());
		professionalTaxDto.setProfessionalTaxInfos(proTaxInfoModelToUiDtoList(professionalTax.getProfessionalTaxInfos()));
		return professionalTaxDto;
	}
	private List<ProfessionalTaxInfoDTO> proTaxInfoModelToUiDtoList(List<ProfessionalTaxInfo> professionalTaxInfoList) {
		List<ProfessionalTaxInfoDTO> professionalTaxinfoDtoList = new ArrayList<ProfessionalTaxInfoDTO>();
		
		for (ProfessionalTaxInfo professionalTaxInfo : professionalTaxInfoList) {
			
			if((StatusMessage.ACTIVE_CODE).equals(professionalTaxInfo.getActiveStatus()))
		
			professionalTaxinfoDtoList.add(proTaxInfoModelToUiDto(professionalTaxInfo));
		}
		return professionalTaxinfoDtoList;
	}
	private ProfessionalTaxInfoDTO proTaxInfoModelToUiDto(ProfessionalTaxInfo professionalTaxInfo) {
		ProfessionalTaxInfoDTO professionalTaxInfoDTO = new ProfessionalTaxInfoDTO();
		professionalTaxInfoDTO.setCategory(professionalTaxInfo.getCategory());
		professionalTaxInfoDTO.setLimitFrom(professionalTaxInfo.getLimitFrom());
		professionalTaxInfoDTO.setActiveStatus(professionalTaxInfo.getActiveStatus());
		professionalTaxInfoDTO.setLimitTo(professionalTaxInfo.getLimitTo());
		professionalTaxInfoDTO.setProfessionalTaxInfoId(professionalTaxInfo.getProfessionalTaxInfoId());
		professionalTaxInfoDTO.setTaxAmount(professionalTaxInfo.getTaxAmount());
		professionalTaxInfoDTO.setDateCreated(professionalTaxInfo.getDateCreated());
		
		professionalTaxInfoDTO.setUserId(professionalTaxInfo.getUserId());
		
		return professionalTaxInfoDTO;
	}
	public List<ProfessionalTaxInfo> uiDtoToProffessionalTaxInfoList(List<ProfessionalTaxInfoDTO> professionalTaxInfoListDtolist,ProfessionalTax professionalTax) {
		 List<ProfessionalTaxInfo> professionalTaxInfoList=new ArrayList<ProfessionalTaxInfo>();
		 professionalTaxInfoListDtolist.forEach(professionalTaxInfoDto->{
			 professionalTaxInfoList.add(uiDtoToDatabaseproTaxInfoModel( professionalTaxInfoDto,professionalTax)); 
		 });
		 
		 
		 /*
		 * for (ProfessionalTaxInfoDTO professionalTaxInfoDto :
		 * professionalTaxInfoListDtolist) {
		 * professionalTaxInfoList.add(uiDtoToDatabaseproTaxInfoModel(
		 * professionalTaxInfoDto,professionalTax)); }
		 */
		
		return professionalTaxInfoList;
	}
	
	private ProfessionalTaxInfo uiDtoToDatabaseproTaxInfoModel(ProfessionalTaxInfoDTO professionalTaxInfoDto,ProfessionalTax professionalTax) {
		
		ProfessionalTaxInfo professionalTaxInfo =new ProfessionalTaxInfo();
		professionalTaxInfo.setProfessionalTaxInfoId(professionalTaxInfoDto.getProfessionalTaxInfoId());
		professionalTaxInfo.setCategory(professionalTaxInfoDto.getCategory());
		professionalTaxInfo.setLimitFrom(professionalTaxInfoDto.getLimitFrom());
		professionalTaxInfo.setLimitTo(professionalTaxInfoDto.getLimitTo());
		professionalTaxInfo.setActiveStatus(professionalTaxInfoDto.getActiveStatus());
		professionalTaxInfo.setTaxAmount(professionalTaxInfoDto.getTaxAmount());
		professionalTaxInfo.setUserId(professionalTaxInfoDto.getUserId());
		if(professionalTaxInfoDto.getDateCreated()==null)
		{
			professionalTaxInfo.setDateCreated(new Date());
		}
		else
		{
			professionalTaxInfo.setDateCreated(professionalTaxInfoDto.getDateCreated());
		}
		professionalTaxInfo.setUserIdUpdate(professionalTaxInfoDto.getUserIdUpdate());	
	   professionalTaxInfo.setProfessionalTax(professionalTax);
		return professionalTaxInfo;
	}

	public List<StateDTO> statedatabaseModelToUiDtoList(List<State> stateList) {

		List<StateDTO> stateDTOList = new ArrayList<StateDTO>();
		/*
		 * for (State state : stateList) {
		 * stateDTOList.add(statedatabaseModelToUiDto(state)); }
		 */
		stateList.forEach(state->{
			stateDTOList.add(statedatabaseModelToUiDto(state));
		});
		return stateDTOList;
	}
	
	public StateDTO statedatabaseModelToUiDto(State state) {
		StateDTO stateDTO = new StateDTO();
		stateDTO.setStateId(state.getStateId());
		stateDTO.setStateName(state.getStateName());
		stateDTO.setStateName(state.getStateName());
		return stateDTO;
	}
	
	public boolean efectiveDatesEquals(ProfessionalTax existingProfessionalTax ,ProfessionalTax professionalTax) {
	    String existingPTEffective =	df.format(existingProfessionalTax.getEffectiveStartDate());
		String effectiveDate = df.format(professionalTax.getEffectiveStartDate());
		if(existingPTEffective.equals(effectiveDate)){
			return true;
		}else {
			return false;
		}
		
}

public ProfessionalTax deactivateProfessionalTax(ProfessionalTax deProfessionalTax ,ProfessionalTaxDTO professionalTaxDTO) {
	Calendar cal = Calendar.getInstance();
    cal.setTime(professionalTaxDTO.getEffectiveStartDate());
    cal.set(Calendar.MINUTE, 0);
    cal.set(Calendar.SECOND, 0);   
    cal.set(Calendar.HOUR_OF_DAY, 0);
    cal.add(Calendar.DATE, -1);
  	Date dateBeforeOneDay = cal.getTime();
  	
  	deProfessionalTax.setActiveStatus("DE");
  	
  	ProfessionalTax professionalTaxs = new ProfessionalTax();
	professionalTaxs = deProfessionalTax; 
   	professionalTaxs.setActiveStatus("DE");
   	professionalTaxs.setEffectiveEndDate(dateBeforeOneDay);
     for (ProfessionalTaxInfo professionalTaxInfo : professionalTaxs.getProfessionalTaxInfos()) {
 		professionalTaxInfo.setActiveStatus("DE");
 	}
   	 return professionalTaxs;
}

public ProfessionalTax activateProfessionalTax(ProfessionalTax acProfessionalTax) {
	acProfessionalTax.setActiveStatus(StatusMessage.ACTIVE_CODE);
	acProfessionalTax.setEffectiveEndDate(null);
	for (ProfessionalTaxInfo professionalTaxInfo : acProfessionalTax.getProfessionalTaxInfos()) {
		professionalTaxInfo.setActiveStatus(StatusMessage.ACTIVE_CODE);
	}
  	 return acProfessionalTax;
}

public ProfessionalTax createNewProfessionalTax(ProfessionalTax professionalTax,  ProfessionalTaxDTO professionalTaxDTO ) {
	professionalTax.setProfessionalHeadId(null);
	professionalTax.setActiveStatus(StatusMessage.ACTIVE_CODE);
	professionalTax.setDateCreated(new Date());
	professionalTax.setEffectiveStartDate(professionalTaxDTO.getEffectiveStartDate());
	professionalTax.setEffectiveEndDate(null);
	for (ProfessionalTaxInfo professionalTaxInfo : professionalTax.getProfessionalTaxInfos()) {
		professionalTaxInfo.setProfessionalTaxInfoId(null);
		professionalTaxInfo.setActiveStatus(StatusMessage.ACTIVE_CODE);
	}
   	 return professionalTax;
}

public ProfessionalTaxDTO databaseModelToUiDtoAll(ProfessionalTax professionalTax) {

	ProfessionalTaxDTO professionalTaxDto = new ProfessionalTaxDTO();
	professionalTaxDto.setProfessionalHeadId(professionalTax.getProfessionalHeadId());
	professionalTaxDto.setStateId(professionalTax.getState().getStateId());
	professionalTaxDto.setCompanyId(professionalTax.getCompany().getCompanyId());
//	professionalTaxDto.setGroupId(professionalTax.getGroupg().getGroupId());
	professionalTaxDto.setStateName(professionalTax.getState().getStateName());
	if(professionalTax.getEffectiveStartDate()!=null) {
	
		professionalTaxDto.setEffectiveStartDate( professionalTax.getEffectiveStartDate() );
		}
	
	professionalTaxDto.setEffectiveEndDate(professionalTax.getEffectiveEndDate());
	professionalTaxDto.setUserId(professionalTax.getUserId());
	professionalTaxDto.setDateCreated(professionalTax.getDateCreated());
	professionalTaxDto.setUserIdUpdate(professionalTax.getUserIdUpdate());
	professionalTaxDto.setActiveStatus(professionalTax.getActiveStatus());
	professionalTaxDto.setProfessionalTaxInfos(proTaxInfoModelToUiDtoListAll(professionalTax.getProfessionalTaxInfos()));
	return professionalTaxDto;
}
private List<ProfessionalTaxInfoDTO> proTaxInfoModelToUiDtoListAll(List<ProfessionalTaxInfo> professionalTaxInfoList) {
	List<ProfessionalTaxInfoDTO> professionalTaxinfoDtoList = new ArrayList<ProfessionalTaxInfoDTO>();
	
	for (ProfessionalTaxInfo professionalTaxInfo : professionalTaxInfoList) {
		professionalTaxinfoDtoList.add(proTaxInfoModelToUiDto(professionalTaxInfo));
	}
	return professionalTaxinfoDtoList;
}

}
