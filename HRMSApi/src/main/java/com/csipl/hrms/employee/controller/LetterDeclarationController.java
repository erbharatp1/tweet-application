package com.csipl.hrms.employee.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.csipl.hrms.dto.employee.LetterDaclarationDTO;
import com.csipl.hrms.model.employee.LetterDaclaration;
import com.csipl.hrms.service.adaptor.LetterDeclarationAdaptor;
import com.csipl.hrms.service.employee.LetterDaclarationService;

/**
 * 
 * @author Bharat
 *
 */
@RestController
@RequestMapping("/letterDeclaration")
public class LetterDeclarationController {
	/**
	 * Logger declaration for knowing the flow of execution for debugging
	 */
	private static final Logger LOG = LoggerFactory.getLogger(LetterDeclarationController.class);

	@Autowired
	private LetterDaclarationService letterDaclarationService;
	@Autowired
	private LetterDeclarationAdaptor letterDeclarationAdaptor;

	/**
	 * 
	 * @param letterDaclarationDTO
	 * @param req
	 */
	@PostMapping(path = "/save")
	public void save(@RequestBody LetterDaclarationDTO letterDaclarationDTO, HttpServletRequest req) {
		LOG.info("save is calling : LetterDaclarationDTO " + letterDaclarationDTO);
		LetterDaclaration letterDaclaration = letterDeclarationAdaptor.uiDtoToDatabaseModel(letterDaclarationDTO);
		letterDaclarationService.save(letterDaclaration);
	}
	/**
	 * 
	 * @param letterId
	 * @param req
	 * @return
	 */
	@GetMapping(value = "/findLetterById/{letterId}")
	public @ResponseBody LetterDaclarationDTO findLetterById(
			@PathVariable("letterId") Long letterId, HttpServletRequest req) {
		LOG.info("letterDeclarationController.findLetterById()"+letterId);
		LetterDaclaration letterDaclaration = letterDaclarationService.findLetterDaclarationById(letterId);
		return letterDeclarationAdaptor.databaseModelToUiDto(letterDaclaration);
	}
	
	

 

}
