package com.csipl.hrms.service.adaptor;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.csipl.common.model.EmailConfiguration;
import com.csipl.hrms.dto.common.EmailConfigurationDTO;

/**
 * bharat
 */
@Component
public class EmailConfigurationAdaptor implements Adaptor<EmailConfigurationDTO, EmailConfiguration> {

	@Override
	public List<EmailConfigurationDTO> databaseModelToUiDtoList(List<EmailConfiguration> emailNotificationMasterList) {

		return emailNotificationMasterList.stream().map(emailDto -> databaseModelToUiDto(emailDto))
				.collect(Collectors.toList());
	}

	@Override
	public EmailConfiguration uiDtoToDatabaseModel(EmailConfigurationDTO emailDTO) {

		EmailConfiguration email = new EmailConfiguration();
		email.setActiveStatus(emailDTO.getActiveStatus());
		email.setAuth(emailDTO.getAuth());
		email.setCompanyId(emailDTO.getCompanyId());
		if (emailDTO.getEmailConfigureId() != null && emailDTO.getEmailConfigureId() != 0) {
			email.setEmailConfigureId(emailDTO.getEmailConfigureId());
		}
	 
		email.setHost(emailDTO.getHost());
		email.setPassword(emailDTO.getPassword());
		email.setUserName(emailDTO.getUserName());
		email.setProtocol(emailDTO.getProtocol());
		email.setPort(emailDTO.getPort());
		email.setServerType(emailDTO.getServerType());
		email.setSslName(emailDTO.getSslName());
		email.setStarttlsName(emailDTO.getStarttlsName());
		email.setDateCreated(emailDTO.getDateCreated());

		return email;
	}

	@Override
	public List<EmailConfiguration> uiDtoToDatabaseModelList(List<EmailConfigurationDTO> uiobj) {
		// TODO Auto-generated method stub
		return uiobj.stream().map(dto->uiDtoToDatabaseModel(dto)).collect(Collectors.toList());
	}

	@Override
	public EmailConfigurationDTO databaseModelToUiDto(EmailConfiguration emailDTO) {
		EmailConfigurationDTO email = new EmailConfigurationDTO();
		email.setActiveStatus(emailDTO.getActiveStatus());
		email.setAuth(emailDTO.getAuth());
		email.setCompanyId(emailDTO.getCompanyId());
		email.setEmailConfigureId(emailDTO.getEmailConfigureId());
		email.setHost(emailDTO.getHost());
		email.setPassword(emailDTO.getPassword());
		email.setUserName(emailDTO.getUserName());
		email.setProtocol(emailDTO.getProtocol());
		email.setPort(emailDTO.getPort());
		email.setServerType(emailDTO.getServerType());
		email.setSslName(emailDTO.getSslName());
		email.setStarttlsName(emailDTO.getStarttlsName());
		email.setDateCreated(emailDTO.getDateCreated());

		return email;
	}
	

}
