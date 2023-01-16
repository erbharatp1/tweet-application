package com.csipl.hrms.service.adaptor;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.csipl.hrms.common.constant.StatusMessage;
import com.csipl.hrms.common.util.DateUtils;
import com.csipl.hrms.dto.organisation.StateDTO;
import com.csipl.hrms.dto.payroll.LabourWelfareFundDTO;
import com.csipl.hrms.dto.payroll.LabourWelfareFundInfoDTO;
import com.csipl.hrms.model.common.State;
import com.csipl.hrms.model.payroll.LabourWelfareFund;
import com.csipl.hrms.model.payroll.LabourWelfareFundInfo;


public class LabourWelfareFundAdaptor implements Adaptor<LabourWelfareFundDTO, LabourWelfareFund> {
	DateUtils dateUtils = new DateUtils();
	public final SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
	
	@Override
	public List<LabourWelfareFund> uiDtoToDatabaseModelList(List<LabourWelfareFundDTO> labourWelfareDto) {
		List<LabourWelfareFund> labourWelfareFundList = new ArrayList<LabourWelfareFund>();
		for (LabourWelfareFundDTO dto : labourWelfareDto) {
			labourWelfareFundList.add(uiDtoToDatabaseModel(dto));
		}
		return labourWelfareFundList;
	}

	@Override
	public List<LabourWelfareFundDTO> databaseModelToUiDtoList(List<LabourWelfareFund> labourWelfareFundList) {

		List<LabourWelfareFundDTO> labourWelfareFundDtoList = new ArrayList<LabourWelfareFundDTO>();
		for (LabourWelfareFund labourWelfareFund : labourWelfareFundList) {
			labourWelfareFundDtoList.add(databaseModelToUiDto(labourWelfareFund));
		}
		return labourWelfareFundDtoList;
	}

	@Override
	public LabourWelfareFund uiDtoToDatabaseModel(LabourWelfareFundDTO labourWelfareFundDto) {
		LabourWelfareFund labourWelfareFund = new LabourWelfareFund();
		State state = new State();
		labourWelfareFund.setLabourWelfareFundHeadId(labourWelfareFundDto.getLabourWelfareFundHeadId());
		state.setStateId(labourWelfareFundDto.getStateId());
		state.setStateName(labourWelfareFundDto.getStateName());
		labourWelfareFund.setState(state);
		labourWelfareFund.setEffectiveStartDate(labourWelfareFundDto.getEffectiveStartDate());
		labourWelfareFund.setEffectiveEndDate(labourWelfareFundDto.getEffectiveEndDate());
		labourWelfareFund.setUserId(labourWelfareFundDto.getUserId());
		if (labourWelfareFundDto.getDateCreated() == null) {
			labourWelfareFund.setDateCreated(new Date());
		} else {
			labourWelfareFund.setDateCreated(labourWelfareFundDto.getDateCreated());
		}
		labourWelfareFund.setActiveStatus(labourWelfareFundDto.getActiveStatus());
				labourWelfareFundDto.getLabourWelfareFundInfos().forEach(dto->{
					labourWelfareFund.setPerMonthAmount(dto.getWelFareAmount());
					labourWelfareFund.setLimitAmount(dto.getLimitTo());
		});
		labourWelfareFund.setUserIdUpdate(labourWelfareFundDto.getUserIdUpdate());
		labourWelfareFund.setCompanyId(labourWelfareFundDto.getCompanyId());
		labourWelfareFund.setGroupId(labourWelfareFundDto.getGroupId());
		 labourWelfareFund.setLabourWelfareFundInfos(uiDtoToProffessionalTaxInfoList(labourWelfareFundDto.getLabourWelfareFundInfos(),labourWelfareFund));

		return labourWelfareFund;
	}

	@Override
	public LabourWelfareFundDTO databaseModelToUiDto(LabourWelfareFund labourFund) {

		LabourWelfareFundDTO dto = new LabourWelfareFundDTO();
		dto.setLabourWelfareFundHeadId(labourFund.getLabourWelfareFundHeadId());
		dto.setStateId(labourFund.getState().getStateId());
		dto.setStateName(labourFund.getState().getStateName());
		dto.setCompanyId(labourFund.getCompanyId());
		dto.setPerMonthAmount(labourFund.getPerMonthAmount());
		dto.setGroupId(labourFund.getGroupId());
		dto.setLimitAmount(labourFund.getLimitAmount());
		dto.setEffectiveStartDate(labourFund.getEffectiveStartDate());
		dto.setEffectiveEndDate(labourFund.getEffectiveEndDate());
		dto.setUserId(labourFund.getUserId());
		dto.setDateCreated(labourFund.getDateCreated());
		dto.setUserIdUpdate(labourFund.getUserIdUpdate());
		dto.setActiveStatus(labourFund.getActiveStatus());
		dto.setLabourWelfareFundInfos(labourWelfareInfoModelToUiDtoList(labourFund.getLabourWelfareFundInfos()));
		return dto;
	}

	private List<LabourWelfareFundInfoDTO> labourWelfareInfoModelToUiDtoList(List<LabourWelfareFundInfo> list) {
		List<LabourWelfareFundInfoDTO> labourWelfareFundinfoDtoList = new ArrayList<LabourWelfareFundInfoDTO>();

		for (LabourWelfareFundInfo labourWelfareFundInfo : list) {
			if ((StatusMessage.ACTIVE_CODE).equals(labourWelfareFundInfo.getActiveStatus()))
				labourWelfareFundinfoDtoList.add(labourWelfareFundInfoModelToUiDto(labourWelfareFundInfo));
		}
		return labourWelfareFundinfoDtoList;
	}

	private LabourWelfareFundInfoDTO labourWelfareFundInfoModelToUiDto(LabourWelfareFundInfo labourWelfareFundInfo) {
		LabourWelfareFundInfoDTO labourWelfareFundInfoDTO = new LabourWelfareFundInfoDTO();
		labourWelfareFundInfoDTO.setGradeId(labourWelfareFundInfo.getGradeId());
	//	labourWelfareFundInfoDTO.setGradeName(labourWelfareFundInfo.ge);
		labourWelfareFundInfoDTO.setLimitFrom(labourWelfareFundInfo.getLimitFrom());
		labourWelfareFundInfoDTO.setActiveStatus(labourWelfareFundInfo.getActiveStatus());
		labourWelfareFundInfoDTO.setLimitTo(labourWelfareFundInfo.getLimitTo());
		labourWelfareFundInfoDTO.setWelFareAmount(labourWelfareFundInfo.getWelFareAmount());
		labourWelfareFundInfoDTO.setEmployerWelFareAmount(labourWelfareFundInfo.getEmployerWelFareAmount());
		labourWelfareFundInfoDTO.setLabourWelfareFundInfoId(labourWelfareFundInfo.getLabourWelfareFundInfoId());
		labourWelfareFundInfoDTO.setWelFareAmount(labourWelfareFundInfo.getWelFareAmount());
		labourWelfareFundInfoDTO.setDateCreated(labourWelfareFundInfo.getDateCreated());
		labourWelfareFundInfoDTO.setUserId(labourWelfareFundInfo.getUserId());

		return labourWelfareFundInfoDTO;
	}

	public List<LabourWelfareFundInfo> uiDtoToProffessionalTaxInfoList(
			List<LabourWelfareFundInfoDTO> labourWelfareFundInfoListDtolist, LabourWelfareFund labourWelfareFund) {
		List<LabourWelfareFundInfo> labourWelfareFundInfoList = new ArrayList<LabourWelfareFundInfo>();
		labourWelfareFundInfoListDtolist.forEach(labourWelfareInfoDto -> {
			labourWelfareFundInfoList.add(uiDtoToDatabaseproTaxInfoModel(labourWelfareInfoDto, labourWelfareFund));
		});
		return labourWelfareFundInfoList;
	}

	private LabourWelfareFundInfo uiDtoToDatabaseproTaxInfoModel(LabourWelfareFundInfoDTO labourWelfareFundInfoDTO,
			LabourWelfareFund labourWelfareFund) {
		LabourWelfareFundInfo labourWelfareFundInfo = new LabourWelfareFundInfo();

		labourWelfareFundInfo.setLabourWelfareFundInfoId(labourWelfareFundInfoDTO.getLabourWelfareFundInfoId());
		labourWelfareFundInfo.setGradeId(labourWelfareFundInfoDTO.getGradeId());
		labourWelfareFundInfo.setWelFareAmount(labourWelfareFundInfoDTO.getWelFareAmount());
		labourWelfareFundInfo.setEmployerWelFareAmount(labourWelfareFundInfoDTO.getEmployerWelFareAmount());
		labourWelfareFundInfo.setLimitFrom(labourWelfareFundInfoDTO.getLimitFrom());
		labourWelfareFundInfo.setLimitTo(labourWelfareFundInfoDTO.getLimitTo());
		labourWelfareFundInfo.setActiveStatus(labourWelfareFundInfoDTO.getActiveStatus());
		labourWelfareFundInfo.setWelFareAmount(labourWelfareFundInfoDTO.getWelFareAmount());
		labourWelfareFundInfo.setUserId(labourWelfareFundInfoDTO.getUserId());
		if (labourWelfareFundInfoDTO.getDateCreated() == null) {
			labourWelfareFundInfo.setDateCreated(new Date());
		} else {
			labourWelfareFundInfo.setDateCreated(labourWelfareFundInfoDTO.getDateCreated());
		}
		labourWelfareFundInfo.setUserIdUpdate(labourWelfareFundInfoDTO.getUserIdUpdate());
		labourWelfareFundInfo.setLabourWelfareFund(labourWelfareFund);
		return labourWelfareFundInfo;
	}

	public List<StateDTO> statedatabaseModelToUiDtoList(List<State> stateList) {

		List<StateDTO> stateDTOList = new ArrayList<StateDTO>();
	 
		stateList.forEach(state -> {
			stateDTOList.add(statedatabaseModelToUiDto(state));
		});
		return stateDTOList;
	}

	public StateDTO statedatabaseModelToUiDto(State state) {
		StateDTO stateDTO = new StateDTO();
		stateDTO.setStateId(state.getStateId());
		stateDTO.setStateName(state.getStateName());
		return stateDTO;
	}

	public boolean efectiveDatesEquals(LabourWelfareFund existingLabourWelfareFund ,LabourWelfareFund labourWelfareFund) {
	    String existingLwfective =	df.format(existingLabourWelfareFund.getEffectiveStartDate());
		String effectiveDate = df.format(labourWelfareFund.getEffectiveStartDate());
		if(existingLwfective.equals(effectiveDate)){
			return true;
		}else {
			return false;
		}
		
}

public LabourWelfareFund deactivateLabourWelfareFund(LabourWelfareFund deLabourWelfareFund ,LabourWelfareFundDTO labourWelfareFundDTO) {
	Calendar cal = Calendar.getInstance();
    cal.setTime(labourWelfareFundDTO.getEffectiveStartDate());
    cal.set(Calendar.MINUTE, 0);
    cal.set(Calendar.SECOND, 0);   
    cal.set(Calendar.HOUR_OF_DAY, 0);
    cal.add(Calendar.DATE, -1);
  	Date dateBeforeOneDay = cal.getTime();
	
    LabourWelfareFund labourWelfareFund = new LabourWelfareFund();
    labourWelfareFund = deLabourWelfareFund; 
    labourWelfareFund.setActiveStatus("DE");
    labourWelfareFund.setEffectiveEndDate(dateBeforeOneDay);
     for (LabourWelfareFundInfo labourWelfareFundInfo : labourWelfareFund.getLabourWelfareFundInfos()) {
    	 labourWelfareFundInfo.setActiveStatus("DE");
 	}
   	 return labourWelfareFund;
}

public LabourWelfareFund activateLabourWelfareFund(LabourWelfareFund acLabourWelfareFund) {
	acLabourWelfareFund.setActiveStatus(StatusMessage.ACTIVE_CODE);
	acLabourWelfareFund.setEffectiveEndDate(null);
	for (LabourWelfareFundInfo professionalTaxInfo : acLabourWelfareFund.getLabourWelfareFundInfos()) {
		professionalTaxInfo.setActiveStatus(StatusMessage.ACTIVE_CODE);
	}
  	 return acLabourWelfareFund;
}

public LabourWelfareFund createNewLabourWelfareFund(LabourWelfareFund labourWelfareFund,  LabourWelfareFundDTO labourWelfareFundDTO ) {
	labourWelfareFund.setLabourWelfareFundHeadId(null);
	labourWelfareFund.setActiveStatus(StatusMessage.ACTIVE_CODE);
	labourWelfareFund.setDateCreated(new Date());
	labourWelfareFund.setEffectiveStartDate(labourWelfareFundDTO.getEffectiveStartDate());
	labourWelfareFund.setEffectiveEndDate(null);
	for (LabourWelfareFundInfo professionalTaxInfo : labourWelfareFund.getLabourWelfareFundInfos()) {
		professionalTaxInfo.setLabourWelfareFundInfoId(null);
		professionalTaxInfo.setActiveStatus(StatusMessage.ACTIVE_CODE);
	}
   	 return labourWelfareFund;
}


public LabourWelfareFundDTO databaseModelToUiDtoAll(LabourWelfareFund labourFund) {

	LabourWelfareFundDTO dto = new LabourWelfareFundDTO();
	dto.setLabourWelfareFundHeadId(labourFund.getLabourWelfareFundHeadId());
	dto.setStateId(labourFund.getState().getStateId());
	dto.setStateName(labourFund.getState().getStateName());
	dto.setCompanyId(labourFund.getCompanyId());
	dto.setPerMonthAmount(labourFund.getPerMonthAmount());
	dto.setGroupId(labourFund.getGroupId());
	dto.setLimitAmount(labourFund.getLimitAmount());
	dto.setEffectiveStartDate(labourFund.getEffectiveStartDate());
	dto.setEffectiveEndDate(labourFund.getEffectiveEndDate());
	dto.setUserId(labourFund.getUserId());
	dto.setDateCreated(labourFund.getDateCreated());
	dto.setUserIdUpdate(labourFund.getUserIdUpdate());
	dto.setActiveStatus(labourFund.getActiveStatus());
	dto.setLabourWelfareFundInfos(labourWelfareInfoModelToUiDtoListAll(labourFund.getLabourWelfareFundInfos()));
	return dto;
}

private List<LabourWelfareFundInfoDTO> labourWelfareInfoModelToUiDtoListAll(List<LabourWelfareFundInfo> list) {
	List<LabourWelfareFundInfoDTO> labourWelfareFundinfoDtoList = new ArrayList<LabourWelfareFundInfoDTO>();

	for (LabourWelfareFundInfo labourWelfareFundInfo : list) {
			labourWelfareFundinfoDtoList.add(labourWelfareFundInfoModelToUiDto(labourWelfareFundInfo));
	}
	return labourWelfareFundinfoDtoList;
}
}

