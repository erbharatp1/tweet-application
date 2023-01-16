package com.csipl.hrms.org.mail.controller;

import java.util.List;

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

import com.csipl.common.model.EmailNotificationMaster;
import com.csipl.hrms.common.exception.PayRollProcessException;
import com.csipl.hrms.common.util.ErrorHandling;
import com.csipl.hrms.dto.common.EmailConfigurationDTO;
import com.csipl.hrms.dto.common.EmailNotificationMasterDTO;
import com.csipl.hrms.service.adaptor.EmailNotificationAdaptor;
import com.csipl.hrms.service.common.EmailNotificationService;
/**
 * 
 * @author Bharat
 *
 */
@RequestMapping("/emailNotifi")
@RestController
public class EmailNotificationController {

	private static final Logger logger = LoggerFactory.getLogger(EmailNotificationController.class);

	@Autowired
	EmailNotificationService emailService;
	@Autowired
	EmailNotificationAdaptor emailNotificationAdaptor;

	@RequestMapping(path = "/save",method = RequestMethod.POST)
	public void saveEmailNotification(@RequestBody EmailNotificationMasterDTO emailDTO, HttpServletRequest req) {
		logger.info("saveEmailNotification is calling :  " + emailDTO);
		EmailNotificationMaster emailNotification = emailNotificationAdaptor.uiDtoToDatabaseModel(emailDTO);
		emailService.save(emailNotification);
		logger.info("saveEmailNotification is end  :" + emailNotification);
	}

	@RequestMapping(path = "getEmailAllList/{companyId}", method = RequestMethod.GET)
	public @ResponseBody List<EmailNotificationMasterDTO> getEmailAllList(@PathVariable("companyId") Long companyId,
			HttpServletRequest req) throws ErrorHandling, PayRollProcessException {
		logger.info("getEmailAllList is calling :  companyId.." + companyId);
		List<EmailNotificationMaster> emailList = emailService.findEMailList(companyId);
		logger.info("getEmailAllList is end :" + emailList);
		if (emailList != null)
			return emailNotificationAdaptor.databaseModelToUiDtoList(emailList);
		else
			throw new ErrorHandling("EMail List  are not available in company");
	}

	@GetMapping(value = "/findById/{companyId}/{mailId}")
	public @ResponseBody EmailNotificationMasterDTO findById(@PathVariable("companyId") Long companyId,@PathVariable("mailId") Long mailId)
			throws PayRollProcessException {
		logger.info("findById is calling");
		return emailNotificationAdaptor.databaseModelToUiDto(emailService.findById(companyId,mailId));
	}
}
