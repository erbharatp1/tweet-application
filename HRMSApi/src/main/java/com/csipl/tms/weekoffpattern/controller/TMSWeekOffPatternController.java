package com.csipl.tms.weekoffpattern.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.csipl.hrms.common.util.ErrorHandling;
import com.csipl.tms.dto.weekofpattern.TMSWeekOffChildPatternDTO;
import com.csipl.tms.dto.weekofpattern.TMSWeekOffMasterPatternDto;
import com.csipl.tms.dto.weekofpattern.WeekOffPatternDTO;
import com.csipl.tms.model.weekofpattern.TMSWeekOffChildPattern;
import com.csipl.tms.model.weekofpattern.TMSWeekOffMasterPattern;
import com.csipl.tms.model.weekofpattern.WeekOffPattern;
import com.csipl.tms.weekoffpattern.adaptor.TMSWeekOffMasterPatternAdaptor;
import com.csipl.tms.weekoffpattern.adaptor.WeekOffPatternAdaptor;
import com.csipl.tms.weekoffpattern.service.WeekOffPatternService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RequestMapping("/TMSweekoffpattern")
@RestController
public class TMSWeekOffPatternController {


	@Autowired
	WeekOffPatternService week_Off_PatternService;
	/**
	 * Logger declaration for knowing the flow of execution for debugging
	 */
	private static final Logger logger = LoggerFactory.getLogger(WeekOffPatternController.class);

	TMSWeekOffMasterPatternAdaptor tmsWeekOffMasterPatternAdaptor = new TMSWeekOffMasterPatternAdaptor();
 
	@RequestMapping(method = RequestMethod.POST)
	public @ResponseBody TMSWeekOffMasterPatternDto save(@RequestBody TMSWeekOffMasterPatternDto week_Off_PatternDto) {
		TMSWeekOffMasterPattern week_Off_Pattern = tmsWeekOffMasterPatternAdaptor.uiDtoToDatabaseModel(week_Off_PatternDto);
		return tmsWeekOffMasterPatternAdaptor.databaseModelToUiDto(week_Off_PatternService.saveTMSWeekOffMasterPattern(week_Off_Pattern));
	}
 
 
//	@RequestMapping(value = "/companyweekoffpattern/{companyId}", method = RequestMethod.GET)
//	public @ResponseBody TMSWeekOffMasterPatternDto getAllWeekOffPatternDTO(@PathVariable("companyId") Long companyId)
//			throws ErrorHandling {
//		logger.info("getAllWeekOffPattern is calling : ");
//		TMSWeekOffMasterPatternDto WeekOffPatternDto = new TMSWeekOffMasterPatternDto();
//		List<TMSWeekOffMasterPattern> week_Off_PatternList = week_Off_PatternService.getAllTMSWeekOffPattern(companyId);
//		
//		
//		logger.info("getAllWeekOffPattern is end  :" + week_Off_PatternList);
//		List<TMSWeekOffMasterPatternDto> weekOffPatternDtoList = tmsWeekOffMasterPatternAdaptor.databaseModelToUiDtoList(week_Off_PatternList);
//		WeekOffPatternDto.setWeekOffDto(weekOffPatternDtoList);
//		return WeekOffPatternDto;
//
//	}
 
	
	@RequestMapping(value = "/{companyId}", method = RequestMethod.GET)
	public @ResponseBody List<TMSWeekOffMasterPatternDto> getAllWeekOffPattern(@PathVariable("companyId") Long companyId)
			throws ErrorHandling {
		logger.info("getAllWeekOffPattern is calling : ");
		WeekOffPatternDTO WeekOffPatternDto = new WeekOffPatternDTO();
		List<TMSWeekOffMasterPattern> week_Off_PatternList = week_Off_PatternService.getAllTMSWeekOffPattern(companyId);
		logger.info("getAllWeekOffPattern is end  :" + week_Off_PatternList);
		List<TMSWeekOffMasterPatternDto> weekOffPatternDtoList = tmsWeekOffMasterPatternAdaptor.databaseModelToUiDtoList(week_Off_PatternList);
		return weekOffPatternDtoList;

	}
	
	@RequestMapping(value = "/companyweekoffpattern/{companyId}", method = RequestMethod.GET)
	public @ResponseBody TMSWeekOffMasterPatternDto getAllWeekOffPatternDTO(@PathVariable("companyId") Long companyId)
			throws ErrorHandling {
		logger.info("getAllWeekOffPattern is calling : ");
		TMSWeekOffMasterPatternDto WeekOffPatternDto = new TMSWeekOffMasterPatternDto();
		List<TMSWeekOffMasterPattern> week_Off_PatternList = week_Off_PatternService.getAllTMSWeekOffPattern(companyId);
		logger.info("getAllWeekOffPattern is end  :" + week_Off_PatternList);
		List<TMSWeekOffMasterPatternDto> weekOffPatternDtoList = tmsWeekOffMasterPatternAdaptor.databaseModelToUiDtoList(week_Off_PatternList);
		WeekOffPatternDto.setWeekOffDto(weekOffPatternDtoList);
		return WeekOffPatternDto;

	}


	 
	@RequestMapping(value = "/weekOff/{companyId}", method = RequestMethod.GET)
	public @ResponseBody List<TMSWeekOffMasterPatternDto> getWeekOffPatternBycompnyId(@PathVariable("companyId") Long companyId) throws ErrorHandling {
		
		List<TMSWeekOffMasterPattern> week_Off_PatternList = week_Off_PatternService.getAllTMSWeekOffPattern(companyId);
		logger.info("getAllWeekOffPattern is end  :" + week_Off_PatternList);
		if (week_Off_PatternList != null)
			return tmsWeekOffMasterPatternAdaptor.databaseModelToUiDtoList(week_Off_PatternList);
		else
			throw new ErrorHandling("weekoffpattern not found");
	}

 
	@RequestMapping(value = "/getWeekOffPattern/{patternId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody TMSWeekOffMasterPatternDto getWeekOffPattern(@PathVariable("patternId") Long patternId) {
		logger.info("getWeekOffPattern is calling : patternId =" + patternId);
		return tmsWeekOffMasterPatternAdaptor.databaseModelToUiDto(week_Off_PatternService.findTMSWeekOffMasterPattern(patternId));
	}

	
	@RequestMapping(value = "/tmsWeekOffChildPattern/{patternId}/{dayName}", method = RequestMethod.GET)
	public @ResponseBody List<TMSWeekOffChildPatternDTO> getWeekOffPatternChild(@PathVariable("patternId") Long patternId, @PathVariable("dayName") String dayName) throws ErrorHandling {
		List<TMSWeekOffChildPattern> TMSWeekOffChildPatternList = week_Off_PatternService.getTMSWeekOffChildPattern(patternId, dayName);
		return tmsWeekOffMasterPatternAdaptor.databaseModelToUiDtoListChild(TMSWeekOffChildPatternList);
	}
	
	
	@RequestMapping(value = "/deleteWeekOffPatternChild/{weekOffPatternChildId}", method = RequestMethod.GET)
	public void deleteWeekOffPatternChild(@PathVariable("weekOffPatternChildId") Long weekOffPatternChildId) throws ErrorHandling {
		 week_Off_PatternService.deleteWeekOffPatternChild(weekOffPatternChildId);
	}
}
