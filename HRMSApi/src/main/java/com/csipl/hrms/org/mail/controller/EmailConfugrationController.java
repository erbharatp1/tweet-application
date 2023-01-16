package com.csipl.hrms.org.mail.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.csipl.common.model.EmailConfiguration;
import com.csipl.hrms.common.exception.PayRollProcessException;
import com.csipl.hrms.dto.common.EmailConfigurationDTO;
import com.csipl.hrms.org.HRMS;
import com.csipl.hrms.service.adaptor.EmailConfigurationAdaptor;
import com.csipl.hrms.service.common.EmailConfigurationService;

/**
 * 
 * @author Bharat
 *
 */
@RestController
@RequestMapping("/emailConfugration")
public class EmailConfugrationController {

	private static final Logger logger = LoggerFactory.getLogger(EmailConfugrationController.class);

	@Autowired
	EmailConfigurationService emailService;
	@Autowired
	EmailConfigurationAdaptor emailConfigurationAdaptor;
 

	@RequestMapping(path = "/save", method = RequestMethod.POST)
	public void saveEmailNotification(@RequestBody EmailConfigurationDTO emailConfugraDTO, HttpServletRequest req) {
		logger.info("saveEmailConfugration is calling :  " + emailConfugraDTO);
		EmailConfiguration email = emailConfigurationAdaptor.uiDtoToDatabaseModel(emailConfugraDTO);
		emailService.save(email);
	 
		logger.info("saveEmailConfugration is end  :" + email);
	}

	// @RequestMapping(value = "/findById/{companyId}", method = RequestMethod.GET)
	@GetMapping(value = "/findById/{companyId}")
	public @ResponseBody EmailConfigurationDTO findById(@PathVariable("companyId") Long companyId)
			throws PayRollProcessException {
		logger.info("findById is calling");
		return emailConfigurationAdaptor.databaseModelToUiDto(emailService.findById(companyId));
	}

}
