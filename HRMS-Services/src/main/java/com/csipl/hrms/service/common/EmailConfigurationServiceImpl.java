package com.csipl.hrms.service.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.csipl.common.model.EmailConfiguration;
import com.csipl.hrms.common.constant.StatusMessage;
import com.csipl.hrms.model.payroll.Gratuaty;
import com.csipl.hrms.service.common.repository.EmailConfigurationRepository;

@Service("emailConfigurationService")
@Transactional
public class EmailConfigurationServiceImpl implements EmailConfigurationService {

	@Autowired
	EmailConfigurationRepository emailConfugrationRepository;

	@Override
	public EmailConfiguration save(EmailConfiguration mail) {
		EmailConfiguration emailConfigura = emailConfugrationRepository.findOne(mail.getEmailConfigureId());
		emailConfigura.setActiveStatus(StatusMessage.DEACTIVE_CODE);
		emailConfugrationRepository.save(emailConfigura);
		mail.setEmailConfigureId(null);
		return emailConfugrationRepository.save(mail);
	}

	@Override
	public EmailConfiguration findById(Long companyId) {
		return emailConfugrationRepository.getById(companyId);
	}

}
