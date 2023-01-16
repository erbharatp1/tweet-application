package com.csipl.tms.weekoffpattern.adaptor;

 
import java.util.ArrayList;
import java.util.List;

import com.csipl.tms.dto.weekofpattern.TMSWeekOffChildPatternDTO;
import com.csipl.tms.dto.weekofpattern.TMSWeekOffMasterPatternDto;
import com.csipl.tms.dto.weekofpattern.WeekOffPatternDTO;
import com.csipl.tms.model.weekofpattern.TMSWeekOffChildPattern;
import com.csipl.tms.model.weekofpattern.TMSWeekOffMasterPattern;
import com.csipl.tms.model.weekofpattern.WeekOffPattern;
import com.csipl.tms.service.Adaptor;

public class TMSWeekOffMasterPatternAdaptor implements Adaptor<TMSWeekOffMasterPatternDto, TMSWeekOffMasterPattern>{

	@Override
	public List<TMSWeekOffMasterPattern> uiDtoToDatabaseModelList(List<TMSWeekOffMasterPatternDto> uiobj) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<TMSWeekOffMasterPatternDto> databaseModelToUiDtoList(List<TMSWeekOffMasterPattern> week_Off_PatternList) {

		List<TMSWeekOffMasterPatternDto> tmsWeekOffMasterPatternDTOList = new ArrayList<TMSWeekOffMasterPatternDto>();
		week_Off_PatternList.forEach(week_Off_Pattern -> {
			tmsWeekOffMasterPatternDTOList.add(databaseModelToUiDto(week_Off_Pattern));
		});
		return tmsWeekOffMasterPatternDTOList;
	}
	
	

	@Override
	public TMSWeekOffMasterPattern uiDtoToDatabaseModel(TMSWeekOffMasterPatternDto tmsWeekOffMasterPatternDto) {

		TMSWeekOffMasterPattern tmsWeekOffMasterPattern = new TMSWeekOffMasterPattern();

		List<TMSWeekOffChildPattern> tmsWeekOffChildPatternList = new ArrayList<TMSWeekOffChildPattern>();

		tmsWeekOffMasterPattern.setPatternId(tmsWeekOffMasterPatternDto.getPatternId());
		tmsWeekOffMasterPattern.setCompanyId(tmsWeekOffMasterPatternDto.getCompanyId());
		tmsWeekOffMasterPattern.setPatternName(tmsWeekOffMasterPatternDto.getPatternName());
		tmsWeekOffMasterPattern.setActiveStatus(tmsWeekOffMasterPatternDto.getActiveStatus());
		tmsWeekOffMasterPattern.setUpdatedDate(tmsWeekOffMasterPatternDto.getUpdatedDate());
		tmsWeekOffMasterPattern.setUserId(tmsWeekOffMasterPatternDto.getUserId());
		tmsWeekOffMasterPattern.setUpdateUserId(tmsWeekOffMasterPatternDto.getUpdateUserId());
		tmsWeekOffMasterPattern.setCreatedDate(tmsWeekOffMasterPatternDto.getCreatedDate());
  
		List<TMSWeekOffChildPatternDTO> tmsWeekOffChildPatternDTOList = tmsWeekOffMasterPatternDto.getTmsweekOffChildPatternsDTO();

		for (TMSWeekOffChildPatternDTO WeekOffChildPatternDTO : tmsWeekOffChildPatternDTOList) {
			TMSWeekOffChildPattern tmsWeekOffChildPattern = new TMSWeekOffChildPattern();
			tmsWeekOffChildPattern.setDayName(WeekOffChildPatternDTO.getDayName());
			tmsWeekOffChildPattern.setId(WeekOffChildPatternDTO.getId());
			tmsWeekOffChildPattern.setNatureOfDay(WeekOffChildPatternDTO.getNatureOfDay());
			tmsWeekOffChildPattern.setPositionOfDay(WeekOffChildPatternDTO.getPositionOfDay());
			tmsWeekOffChildPattern.setActiveStatus(WeekOffChildPatternDTO.getActiveStatus());
			tmsWeekOffChildPattern.setTmsweekOffMasterPattern(tmsWeekOffMasterPattern);
			tmsWeekOffChildPatternList.add(tmsWeekOffChildPattern);
		}
		tmsWeekOffMasterPattern.setTmsweekOffChildPatterns(tmsWeekOffChildPatternList);
		
		return tmsWeekOffMasterPattern;
	}

	@Override
	public TMSWeekOffMasterPatternDto databaseModelToUiDto(TMSWeekOffMasterPattern tmsWeekOffMasterPattern) {
		TMSWeekOffMasterPatternDto tmsWeekOffMasterPatternDTO = new TMSWeekOffMasterPatternDto();
		List<TMSWeekOffChildPatternDTO> tmsWeekOffChildPatternDTOList = new ArrayList<TMSWeekOffChildPatternDTO>();

		tmsWeekOffMasterPatternDTO.setPatternId(tmsWeekOffMasterPattern.getPatternId());
		tmsWeekOffMasterPatternDTO.setCompanyId(tmsWeekOffMasterPattern.getCompanyId());
		tmsWeekOffMasterPatternDTO.setPatternName(tmsWeekOffMasterPattern.getPatternName());
		tmsWeekOffMasterPatternDTO.setActiveStatus(tmsWeekOffMasterPattern.getActiveStatus());
		tmsWeekOffMasterPatternDTO.setUpdatedDate(tmsWeekOffMasterPattern.getUpdatedDate());
		tmsWeekOffMasterPatternDTO.setUserId(tmsWeekOffMasterPattern.getUserId());
		tmsWeekOffMasterPatternDTO.setCreatedDate(tmsWeekOffMasterPattern.getCreatedDate());
		tmsWeekOffMasterPatternDTO.setUpdateUserId(tmsWeekOffMasterPattern.getUpdateUserId());
		List<TMSWeekOffChildPattern> TMSWeekOffChildPatternList = tmsWeekOffMasterPattern.getTmsweekOffChildPatterns();

		for (TMSWeekOffChildPattern TMSWeekOffChildPattern : TMSWeekOffChildPatternList) {
			TMSWeekOffChildPatternDTO tmsWeekOffChildPatternDTO = new TMSWeekOffChildPatternDTO();
			tmsWeekOffChildPatternDTO.setDayName(TMSWeekOffChildPattern.getDayName());
			tmsWeekOffChildPatternDTO.setId(TMSWeekOffChildPattern.getId());
			tmsWeekOffChildPatternDTO.setActiveStatus(TMSWeekOffChildPattern.getActiveStatus());
			tmsWeekOffChildPatternDTO.setNatureOfDay(TMSWeekOffChildPattern.getNatureOfDay());
			tmsWeekOffChildPatternDTO.setPositionOfDay(TMSWeekOffChildPattern.getPositionOfDay());
//			tmsWeekOffChildPatternDTO.setTmsWeekOffMasterPatternDto(tmsWeekOffMasterPatternDTO);
			tmsWeekOffChildPatternDTOList.add(tmsWeekOffChildPatternDTO);
		}
		
		tmsWeekOffMasterPatternDTO.setTmsweekOffChildPatternsDTO(tmsWeekOffChildPatternDTOList);
		return tmsWeekOffMasterPatternDTO;
	}

	public List<TMSWeekOffChildPatternDTO> databaseModelToUiDtoListChild(List<TMSWeekOffChildPattern> tMSWeekOffChildPatternList) {
		List<TMSWeekOffChildPatternDTO> tmsWeekOffChildPatternDTOList = new ArrayList<TMSWeekOffChildPatternDTO>();

		for (TMSWeekOffChildPattern TMSWeekOffChildPattern : tMSWeekOffChildPatternList) {
			TMSWeekOffChildPatternDTO tmsWeekOffChildPatternDTO = new TMSWeekOffChildPatternDTO();
			tmsWeekOffChildPatternDTO.setDayName(TMSWeekOffChildPattern.getDayName());
			tmsWeekOffChildPatternDTO.setId(TMSWeekOffChildPattern.getId());
			tmsWeekOffChildPatternDTO.setActiveStatus(TMSWeekOffChildPattern.getActiveStatus());
			tmsWeekOffChildPatternDTO.setNatureOfDay(TMSWeekOffChildPattern.getNatureOfDay());
			tmsWeekOffChildPatternDTO.setPositionOfDay(TMSWeekOffChildPattern.getPositionOfDay());
			tmsWeekOffChildPatternDTOList.add(tmsWeekOffChildPatternDTO);
		}
		
		return tmsWeekOffChildPatternDTOList;
	}

}
