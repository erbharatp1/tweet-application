package com.csipl.hrms.common.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.csipl.hrms.dto.common.TokenMasterDTO;
import com.csipl.hrms.model.common.TokenMaster;
import com.csipl.hrms.service.common.TokenMasterService;
import com.csipl.hrms.service.common.adaptor.TokenMasterAdaptor;

@RestController
@RequestMapping("/token")
public class TokenMasterController {
	private static final Logger logger = LoggerFactory.getLogger(TokenMasterController.class);

	@Autowired
	TokenMasterService tokenMasterService;

	TokenMasterAdaptor tokenMasterAdaptor = new TokenMasterAdaptor();

	@RequestMapping(method = RequestMethod.POST)
	public void saveTokenMaster(@RequestBody TokenMasterDTO tokenMasterDTO) {
		logger.info("saveTokenMaster is calling : " + " : TokenMasterDTO " + tokenMasterDTO);
		TokenMaster tokenMaster = tokenMasterAdaptor.uiDtoToDatabaseModel(tokenMasterDTO);
		tokenMasterService.save(tokenMaster);

	}

}
