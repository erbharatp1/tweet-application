package com.csipl.hrms.recruitement;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.csipl.hrms.common.exception.PayRollProcessException;
import com.csipl.hrms.common.util.ErrorHandling;
import com.csipl.hrms.dto.recruitment.QuestionTamplatesDTO;
import com.csipl.hrms.model.recruitment.QuestionTamplates;
import com.csipl.hrms.service.recruitement.QuestionTamplatesService;
import com.csipl.hrms.service.recruitement.adaptor.QuestionTamplatesAdaptor;

@RestController
@RequestMapping("/questionTemplates")
public class QuestionTemplatesController {

	private static final Logger logger = LoggerFactory.getLogger(QuestionTemplatesController.class);

	@Autowired
	private QuestionTamplatesService questionTamplatesService;

	@Autowired
	private QuestionTamplatesAdaptor questionTamplatesAdaptor;

	/**
	 * 
	 * @param questionTamplatesDTO
	 * @param req
	 */
	@PostMapping(path = "/saveQuestionTemplates")
	public void save(@RequestBody QuestionTamplatesDTO questionTamplatesDTO, HttpServletRequest req) {
		logger.info("save is calling : questionTamplatesDTO " + questionTamplatesDTO);
		QuestionTamplates questionTamplates = questionTamplatesAdaptor.uiDtoToDatabaseModel(questionTamplatesDTO);
		questionTamplatesService.save(questionTamplates);
	}

	@GetMapping(value = "/fetchQuestionList/{companyId}")
	public @ResponseBody List<QuestionTamplatesDTO> fetchQuestionTamplatesList(
			@PathVariable("companyId") Long companyId, HttpServletRequest req)
			throws ErrorHandling, PayRollProcessException {
		logger.info("fetchQuestionTamplatesList is calling :  " + companyId);
		List<QuestionTamplates> fetchQuestionTamplates = questionTamplatesService.fetchQuestionList(companyId);
	//	if (fetchQuestionTamplates.size() > 0)
			return questionTamplatesAdaptor.databaseModelToUiDtoList(fetchQuestionTamplates);
		//else
		//	throw new ErrorHandling("QuestionTamplates Data Not Found");
	}
	
	@DeleteMapping(value = "/delete/{questionTamplateId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public void deleteByfetchQuestionTampId(@PathVariable("questionTamplateId") Long[] questionTamplateIds) {
		logger.info("deleteByfetchQuestionTampId.deleteByTodoListId()" + questionTamplateIds);
		for (Long questionTamplateId : questionTamplateIds) {
			questionTamplatesService.deleteByfetchQuestionTampId(questionTamplateId);
			logger.info(" .deleteByTodoListId() secussfully" + questionTamplateId);
		}
	}
	
	@GetMapping(value = "/findQuestionTampById/{questionTamplateId}")
	public @ResponseBody QuestionTamplatesDTO getQuestionTamplatesById(
			@PathVariable("questionTamplateId") Long questionTamplateId, HttpServletRequest req) {
		return questionTamplatesAdaptor.databaseModelToUiDto(questionTamplatesService.findQuestionTampById(questionTamplateId));
	}
	
}
