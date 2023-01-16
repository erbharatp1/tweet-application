package com.csipl.hrms.service.adaptor;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.csipl.hrms.common.constant.StatusMessage;
import com.csipl.hrms.dto.payroll.GratuityDTO;
import com.csipl.hrms.model.common.Company;
import com.csipl.hrms.model.common.Groupg;
import com.csipl.hrms.model.payroll.Gratuaty;

public class GratuityAdaptor implements Adaptor<GratuityDTO, Gratuaty> {
	
	public final SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
	
	@Override
	public Gratuaty uiDtoToDatabaseModel(GratuityDTO gratuityDto) {
		Company company = new Company();
		Groupg group = new Groupg();
		Gratuaty gratuity = new Gratuaty();
		if (gratuityDto.getGratuityId() != null && gratuityDto.getGratuityId() != 0) {
			gratuity.setGraduityId(gratuityDto.getGratuityId());
		}

		company.setCompanyId(gratuityDto.getCompanyId());
		gratuity.setCompany(company);
		gratuity.setEffectiveDate(gratuityDto.getEffectiveDate());
		gratuity.setNoOfDays(gratuityDto.getNoOfDays());
		gratuity.setNoOfDaysDevide(gratuityDto.getNoOfDaysDevide());
		gratuity.setNoOfMonths(gratuityDto.getNoOfMonths());
		gratuity.setActiveStatus(gratuityDto.getActiveStatus());
		gratuity.setUserId(gratuityDto.getUserId());
		gratuity.setUserIdUpdate(gratuityDto.getUserIdUpdate());
		gratuity.setDateUpdate(new Date());
	/*	group.setGroupId(1L);    //comment for future enhancement
		gratuity.setGroupg(group);*/
//		if (gratuityDto.getGratuityId() == null) {
//			gratuity.setGraduityId(gratuityDto.getGratuityId());
//			gratuity.setDateCreated(new Date());
//			 
//		} else
//			gratuity.setDateCreated(gratuityDto.getDateCreated());

		return gratuity;
	}

	@Override
	public List<GratuityDTO> databaseModelToUiDtoList(List<Gratuaty> gratuityList) {
		List<GratuityDTO> gratuityDtoList = new ArrayList<GratuityDTO>();
		for (Gratuaty gratuity : gratuityList) {

			gratuityDtoList.add(databaseModelToUiDto(gratuity));
		}
		return gratuityDtoList;
	}

	@Override
	public GratuityDTO databaseModelToUiDto(Gratuaty gratuity) {
		GratuityDTO gratuityDto = new GratuityDTO();
		gratuityDto.setGratuityId(gratuity.getGraduityId());
		gratuityDto.setEffectiveDate(gratuity.getEffectiveDate());
		gratuityDto.setNoOfDays(gratuity.getNoOfDays());
		gratuityDto.setNoOfDaysDevide(gratuity.getNoOfDaysDevide());
		gratuityDto.setNoOfMonths(gratuity.getNoOfMonths());
		gratuityDto.setActiveStatus(gratuity.getActiveStatus());
		gratuityDto.setUserId(gratuity.getUserId());
		gratuityDto.setDateCreated(gratuity.getDateCreated());
		return gratuityDto;
	}

	@Override
	public List<Gratuaty> uiDtoToDatabaseModelList(List<GratuityDTO> gratuityDtoList) {
		List<Gratuaty> gratuity = new ArrayList<Gratuaty>();
		for (GratuityDTO gratuityDto : gratuityDtoList) {

			gratuity.add(uiDtoToDatabaseModel(gratuityDto));
		}
		return gratuity;
	}

	public boolean efectiveDatesEquals(Gratuaty existingGratuaty ,Gratuaty gratuity) {
	    String existingGratuatyEffective =	df.format(existingGratuaty.getEffectiveDate());
		String effectiveDate= df.format(gratuity.getEffectiveDate());
		if(existingGratuatyEffective.equals(effectiveDate)){
			return true;
		}else {
			return false;
		}		
	}
	
	public Gratuaty deactivateExistingGratuaty(Gratuaty existingGratuaty ,GratuityDTO gratuatyDTO) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(gratuatyDTO.getEffectiveDate());
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.add(Calendar.DATE, -1);
		Date dateBeforeOneDay = cal.getTime();
		existingGratuaty.setActiveStatus(StatusMessage.DEACTIVE_CODE);
		existingGratuaty.setEffectiveEndDate(dateBeforeOneDay);
	   	 return existingGratuaty;
	}
	
	public Gratuaty createNewGratuaty(Gratuaty gratuaty,  GratuityDTO gratuatyDTO ) {
		gratuaty.setGraduityId(null);
		gratuaty.setActiveStatus(StatusMessage.ACTIVE_CODE);
		gratuaty.setDateCreated(new Date());
		gratuaty.setEffectiveDate(gratuatyDTO.getEffectiveDate());
		gratuaty.setEffectiveEndDate(null);

		
	   	 return gratuaty;
	}
	
}
