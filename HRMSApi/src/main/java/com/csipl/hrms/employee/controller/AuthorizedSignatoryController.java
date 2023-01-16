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
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.csipl.hrms.dto.employee.AuthorizedSignatoryDTO;
import com.csipl.hrms.model.employee.AuthorizedSignatory;
import com.csipl.hrms.service.adaptor.AuthorizedSignatoryAdaptor;
import com.csipl.hrms.service.employee.AuthorizedSignatoryService;

/**
 * 
 * @author Bharat
 *
 */
@RestController
@RequestMapping("/authorizedSignatory")
public class AuthorizedSignatoryController {
	/**
	 * Logger declaration for knowing the flow of execution for debugging
	 */
	private static final Logger LOG = LoggerFactory.getLogger(AuthorizedSignatoryController.class);

	@Autowired
	private AuthorizedSignatoryService authorizedSignatoryService;
	@Autowired
	private AuthorizedSignatoryAdaptor authorizedSignatoryAdaptor;

	 
	@PostMapping(value = "/file", consumes = "multipart/form-data")
	public void saveAuthorizedSignatory(@RequestPart("uploadFile") MultipartFile file,
			@RequestPart("info") AuthorizedSignatoryDTO authorizedSignatoryDto) {
		LOG.info("saveAuthorizedSignatoryDTO  is calling : " + " : AuthorizedSignatoryDTO " + authorizedSignatoryDto
				+ ":uploadFile" + file);

		AuthorizedSignatory authorizedSignatory = authorizedSignatoryAdaptor
				.uiDtoToDatabaseModel(authorizedSignatoryDto);
		authorizedSignatoryService.save(authorizedSignatory, file, true);
	}

	 
	@PostMapping
	public void save(@RequestBody AuthorizedSignatoryDTO authorizedSignatoryDTO) {
		LOG.info("saveAutho is calling : " + " : saveAutho " + authorizedSignatoryDTO);
		AuthorizedSignatory authorizedSignatory = authorizedSignatoryAdaptor
				.uiDtoToDatabaseModel(authorizedSignatoryDTO);
		LOG.info("AuthorizedSignatory  : " + authorizedSignatory);

		authorizedSignatoryService.save(authorizedSignatory, null, false);
	}
	
	@GetMapping(value = "/getAuthorizedSignatoryById/{letterId}")
	public @ResponseBody AuthorizedSignatoryDTO getAuthorizedSignatoryById(
			@PathVariable("letterId") Long letterId, HttpServletRequest req) {
		return authorizedSignatoryAdaptor.databaseModelToUiDto(authorizedSignatoryService.findAuthorizedSignatoryById(letterId));
	}
	
	

 

}
