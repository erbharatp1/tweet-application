package com.csipl.hrms.service.common;

import com.csipl.common.model.EmailConfiguration;


public interface EmailConfigurationService {

	public EmailConfiguration save(EmailConfiguration mail);

	public EmailConfiguration findById(Long companyId);
	 
}

