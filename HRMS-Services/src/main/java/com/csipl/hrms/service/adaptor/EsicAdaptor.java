package com.csipl.hrms.service.adaptor;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.csipl.hrms.common.constant.StatusMessage;
import com.csipl.hrms.dto.payroll.EpfDTO;
import com.csipl.hrms.dto.payroll.EsiCycleDTO;
import com.csipl.hrms.dto.payroll.EsiDTO;
import com.csipl.hrms.model.common.Company;
import com.csipl.hrms.model.payroll.Epf;
import com.csipl.hrms.model.payroll.Esi;
import com.csipl.hrms.model.payroll.EsiCycle;



public class EsicAdaptor implements Adaptor<EsiDTO, Esi> {

	public final SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
	
	@Override
	public List<Esi> uiDtoToDatabaseModelList(List<EsiDTO> esicDtoList) {
		List<Esi> esicList=new ArrayList<Esi>();
		for(EsiDTO esicDto:esicDtoList) {
		
			esicList.add(uiDtoToDatabaseModel(esicDto));
		}
		return esicList;
	}

	@Override
	public List<EsiDTO> databaseModelToUiDtoList(List<Esi> esicList) {
		List<EsiDTO> esicDtoList=new ArrayList<EsiDTO>();
		for(Esi esic:esicList) {
		
			esicDtoList.add(databaseModelToUiDto(esic));
		}
		return esicDtoList;
	}

	@Override
	public Esi uiDtoToDatabaseModel(EsiDTO esicDto) {
		
		Esi esic=new Esi();
		esic.setEsiId(esicDto.getEsiId());
		esic.setEffectiveDate(esicDto.getEffectiveDate());
		esic.setEffectiveEndDate(esicDto.getEffectiveEndDate());
		esic.setEmployeePer(esicDto.getEmployeePer());
		esic.setEmployerPer(esicDto.getEmployerPer());
		esic.setMaxGrossLimit(esicDto.getMaxGrossLimit());
		esic.setActiveStatus(esicDto.getActiveStatus());
		esic.setUserId(esicDto.getUserId());
		Company company=new Company();
		company.setCompanyId(esicDto.getCompanyId());
		esic.setCompany(company);
		/*Groupg groupg=new Groupg();
		groupg.setGroupId(1l);      //comment for future enhancement
		esic.setGroupId(groupg.getGroupId());*/
		esic.setDateUpdate(new Date());
		if(esicDto.getDateCreated()==null)
			esic.setDateCreated(new Date());
		else
			esic.setDateCreated(esicDto.getDateCreated());
		esic.setUserIdUpdate(esicDto.getUserIdUpdate());
		esic.setEsiCycles(uiDtoToEsiCycleList(esicDto.getEsiCycleDto(),esic));

		return esic;
	}

	private List<EsiCycle> uiDtoToEsiCycleList(List<EsiCycleDTO> esiCycleDtoList, Esi esic) {
		List<EsiCycle> esiCycleList=new ArrayList<EsiCycle>();
		 System.out.println(esiCycleDtoList);
        for (EsiCycleDTO esiCycleDTO : esiCycleDtoList) {
        	esiCycleList.add(uiDtoEsicCycleToDatabase(esiCycleDTO,esic));
		}
		
		return esiCycleList;
	}

	private EsiCycle uiDtoEsicCycleToDatabase(EsiCycleDTO esiCycleDTO, Esi esic) {
		EsiCycle esiCycle=new EsiCycle();
		esiCycle.setEsiCycleId(esiCycleDTO.getEsiCycleId());	
		esiCycle.setFromperiod(esiCycleDTO.getFromperiod());		
		esiCycle.setToperiod(esiCycleDTO.getToperiod());	
		esiCycle.setEsi(esic);
		return esiCycle;
	}

	@Override
	public EsiDTO databaseModelToUiDto(Esi esic) {
		EsiDTO esicDto=new EsiDTO();
		esicDto.setEsiId(esic.getEsiId());
		esicDto.setEffectiveDate(esic.getEffectiveDate());
		esicDto.setEmployeePer(esic.getEmployeePer());
		esicDto.setEmployerPer(esic.getEmployerPer());
		esicDto.setMaxGrossLimit(esic.getMaxGrossLimit());
		esicDto.setActiveStatus(esic.getActiveStatus());
		esicDto.setUserId(esic.getUserId());
		esicDto.setDateCreated(esic.getDateCreated());
		Company company=new Company();
		Long comp = esic.getCompany().getCompanyId();
		esicDto.setCompanyId(comp);
		esicDto.setEsiCycleDto(esicCycleModelToUiList(esic.getEsiCycles()));
		return esicDto;
	}
	
	private List<EsiCycleDTO> esicCycleModelToUiList(List<EsiCycle> esiCyclesList) {
	List<EsiCycleDTO> esiCycleDTOList=new ArrayList<EsiCycleDTO>();
	for (EsiCycle esiCycle : esiCyclesList) {
		esiCycleDTOList.add(esicCycleModelToUiDto(esiCycle));
	}
	
	
		return esiCycleDTOList;
	}

	private EsiCycleDTO esicCycleModelToUiDto(EsiCycle esiCycle) {
		EsiCycleDTO esiCycleDTO = new EsiCycleDTO();
		esiCycleDTO.setEsiCycleId(esiCycle.getEsiCycleId());
		esiCycleDTO.setEsiId(esiCycle.getEsi().getEsiId());
		esiCycleDTO.setFromperiod(esiCycle.getFromperiod());
		esiCycleDTO.setToperiod(esiCycle.getToperiod());
		
		
		
		return esiCycleDTO;
	}

	public EsiDTO databaseModelToUiDtoObject(List<Esi> esicList)
	{
		

		for(Esi esic:esicList) {
		
			return databaseModelToUiDto(esic);
		}
		return null;
	}

	public boolean efectiveDatesEquals(Esi existingEsi ,Esi esi) {
	    String existingesiEffective =	df.format(existingEsi.getEffectiveDate());
		String effectiveDate= df.format(esi.getEffectiveDate());
		if(existingesiEffective.equals(effectiveDate)){
			return true;
		}else {
			return false;
		}		
	}
	
	public Esi deactivateExistingEsi(Esi existingEsic ,EsiDTO esiDTO) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(esiDTO.getEffectiveDate());
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.add(Calendar.DATE, -1);
		Date dateBeforeOneDay = cal.getTime();
		existingEsic.setActiveStatus(StatusMessage.DEACTIVE_CODE);
		existingEsic.setEffectiveEndDate(dateBeforeOneDay);
	   	 return existingEsic;
	}
	
	public Esi createNewEsi(Esi esic,  EsiDTO esiDTO ) {
		esic.setEsiId(null);
		esic.setActiveStatus(StatusMessage.ACTIVE_CODE);
		esic.setDateCreated(new Date());
		esic.setEffectiveDate(esiDTO.getEffectiveDate());
		esic.setEffectiveEndDate(null);

		for (EsiCycle esiCycle : esic.getEsiCycles()) {
			esiCycle.setEsiCycleId(null);
		}
	   	 return esic;
	}
	
}
