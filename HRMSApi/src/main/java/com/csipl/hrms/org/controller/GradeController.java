package com.csipl.hrms.org.controller;

import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.csipl.hrms.common.exception.PayRollProcessException;
import com.csipl.hrms.common.util.ErrorHandling;
import com.csipl.hrms.dto.organisation.GradeDTO;
import com.csipl.hrms.model.organisation.Grade;
import com.csipl.hrms.service.adaptor.GradeAdaptor;
import com.csipl.hrms.service.organization.GradeService;

@RestController
@RequestMapping("/grade")
public class GradeController  {
	/**
	 * Logger declaration for knowing the flow of execution for debugging
	 */
	private static final Logger logger = LoggerFactory.getLogger(GradeController.class);
	GradeAdaptor gradeAdaptor = new GradeAdaptor();
	@Autowired
	GradeService gradeService;
	 
	@RequestMapping(method = RequestMethod.POST)
	public void saveGrade(@RequestBody GradeDTO gradeDto, HttpServletRequest req) {
		logger.info("saveGrade is calling : GradeDTO "+ gradeDto);
		Grade grade = gradeAdaptor.uiDtoToDatabaseModel(gradeDto);
		gradeService.save(grade);
	}
	
	@RequestMapping(value = "gradeList/{companyId}",method = RequestMethod.GET)
	public @ResponseBody List<GradeDTO> findAllGrades(@PathVariable("companyId") String companyId,HttpServletRequest req) throws ErrorHandling, PayRollProcessException {
		logger.info("findAllGrades is calling : ");
		Long longcompanyId = Long.parseLong(companyId);
		List<Grade> gradeList = gradeService.getAllGrades(longcompanyId);
		logger.info("findAllGrades  : gradeList " +gradeList);
		if (gradeList != null && gradeList.size() > 0)
			return gradeAdaptor.databaseModelToUiDtoList(gradeList);
		else
			throw new ErrorHandling("Grade data not present");
	}
	 
	@RequestMapping(value = "/{gradeId}", method = RequestMethod.GET)
	public @ResponseBody GradeDTO findGradeDetails(@PathVariable("gradeId") String gradeId, HttpServletRequest req) {
		logger.info("findAllGrades is calling : gradeId "+gradeId);
		Long gradeLongId = Long.parseLong(gradeId);
		return gradeAdaptor.databaseModelToUiDto(gradeService.findGradeDetails(gradeLongId));
	}
	@GetMapping(path="findGradeList/{companyId}")
	public @ResponseBody List<GradeDTO> findGradeList(@PathVariable("companyId") Long companyId,HttpServletRequest req) throws ErrorHandling, PayRollProcessException {
		logger.info("findGradeList is calling : ");
		List<Grade> gradeList = gradeService.findAllGradesList(companyId);
		logger.info("findGradeList  : findGradeList " +gradeList);
		if (gradeList != null && gradeList.size() > 0)
			return gradeAdaptor.databaseModelToUiDtoList(gradeList);
		else
			throw new ErrorHandling("Grade data not present");
	}
}
