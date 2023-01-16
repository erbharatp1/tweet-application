package com.csipl.hrms.service.adaptor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.csipl.common.model.EmailConfiguration;
import com.csipl.common.model.EmailNotificationMaster;
import com.csipl.hrms.dto.common.EmailNotificationMasterDTO;

/**
 * bharat
 */
@Component
public class EmailNotificationAdaptor implements Adaptor<EmailNotificationMasterDTO, EmailNotificationMaster> {

	@Override
	public List<EmailNotificationMasterDTO> databaseModelToUiDtoList(List<EmailNotificationMaster> emailNotificationMasterList) {	
		List<EmailNotificationMasterDTO> emailList = new ArrayList<EmailNotificationMasterDTO>();
		for (EmailNotificationMaster emailMaster : emailNotificationMasterList) {
			emailList.add(databaseModelToUiDto(emailMaster));
		}
		return emailList;

	}
	


	@Override
	public EmailNotificationMaster uiDtoToDatabaseModel(EmailNotificationMasterDTO emailDTO) {

		EmailNotificationMaster email = new EmailNotificationMaster();
		email.setMailId(emailDTO.getMailId());
		email.setBcc(emailDTO.getBcc());
		email.setCc(emailDTO.getCc());
		email.setTitle(emailDTO.getTitle());
		email.setUserName(emailDTO.getUserName());
		email.setSubject(emailDTO.getSubject());
		email.setFromMail(emailDTO.getFromMail());
		email.setToMail(emailDTO.getToMail());
		email.setPassword(emailDTO.getPassword());
		email.setActiveStatus(emailDTO.getActiveStatus());
		email.setMailType(emailDTO.getMailType());
		email.setUserId(emailDTO.getUserId());
		email.setTitle(emailDTO.getTitle());
		
		email.setIsApplicableOnReportingTo(emailDTO.getIsApplicableOnReportingTo());
		email.setIsApplicableOnReportingToManager(emailDTO.getIsApplicableOnReportingToManager());
		
		email.setCompanyId(emailDTO.getCompanyId());
		email.setUserId(emailDTO.getUserId());
		EmailConfiguration confugration = new EmailConfiguration();
		confugration.setEmailConfigureId(emailDTO.getEmailConfigureId());
		email.setEmailConfiguration(confugration);
		if (emailDTO.getDateCreated() == null)
			email.setDateCreated(new java.util.Date());
		else
			email.setDateCreated(emailDTO.getDateCreated());
		email.setDateUpdate(new Date());
		email.setUserIdUpdate(emailDTO.getUserIdUpdate());
		return email;
	}

	@Override
	public EmailNotificationMasterDTO databaseModelToUiDto(EmailNotificationMaster email) {
		EmailNotificationMasterDTO emailDTO = new EmailNotificationMasterDTO();
		emailDTO.setMailId(email.getMailId());
		emailDTO.setBcc(email.getBcc());
		emailDTO.setCc(email.getCc());
		emailDTO.setTitle(email.getTitle());
		emailDTO.setUserName(email.getUserName());
		emailDTO.setSubject(email.getSubject());
		emailDTO.setFromMail(email.getFromMail());
		emailDTO.setToMail(email.getToMail());
		emailDTO.setPassword(email.getPassword());
		emailDTO.setActiveStatus(email.getActiveStatus());
		emailDTO.setMailType(email.getMailType());
		emailDTO.setUserId(email.getUserId());
		emailDTO.setTitle(email.getTitle());
		emailDTO.setCompanyId(email.getCompanyId());
		emailDTO.setUserId(email.getUserId());
		emailDTO.setIsApplicableOnReportingTo(email.getIsApplicableOnReportingTo());
		emailDTO.setIsApplicableOnReportingToManager(email.getIsApplicableOnReportingToManager());
		
		if (email.getToMail()==null) {
			emailDTO.setCountTo(0);
		} else {
			emailDTO.setCountTo(email.getToMail().split(",").length);
		}
		if (email.getCc()==null) {
			emailDTO.setCountCC(0);
		} else {
			emailDTO.setCountCC(email.getCc().split(",").length);
		}
		if (email.getBcc()==null) {
			emailDTO.setCountBCC(0);
		} else {
			emailDTO.setCountBCC(email.getBcc().split(",").length);
		}

		emailDTO.setEmailConfigureId(email.getEmailConfiguration().getEmailConfigureId());
		if (email.getDateCreated() == null)
			emailDTO.setDateCreated(new java.util.Date());
		else
			emailDTO.setDateCreated(email.getDateCreated());
		emailDTO.setDateUpdate(new Date());
		emailDTO.setUserIdUpdate(email.getUserIdUpdate());
		return emailDTO;
	}

	@Override
	public List<EmailNotificationMaster> uiDtoToDatabaseModelList(List<EmailNotificationMasterDTO> uiobj) {
		return uiobj.stream().map(email -> uiDtoToDatabaseModel(email)).collect(Collectors.toList());
	}

}
