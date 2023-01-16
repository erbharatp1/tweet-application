package com.csipl.hrms.service.common;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.csipl.common.model.EmailNotificationMaster;
import com.csipl.hrms.service.common.repository.EmailNotificationRepository;

@Service("eMailService")
@Transactional
public class EmailNotificationServiceImpl implements EmailNotificationService {
	private static final Logger logger = LoggerFactory.getLogger(EmailNotificationServiceImpl.class);
	@Autowired
	EmailNotificationRepository emailNotificationRepository;

	@Override
	public EmailNotificationMaster save(EmailNotificationMaster mail) {
		return emailNotificationRepository.save(mail);
	}

	@Override
	public List<EmailNotificationMaster> findEMailList(Long companyId) {
		return emailNotificationRepository.findAll(companyId);
	}

	@Override
	public EmailNotificationMaster findById(Long companyId, Long mailId) {
		// TODO Auto-generated method stub
		return emailNotificationRepository.findById(companyId, mailId);
	}

	@Override
	public EmailNotificationMaster findEMailListByStatus(String mailType) {
		// TODO Auto-generated method stub
		return emailNotificationRepository.findAllByStatus(mailType);
	}

//	@Override
//	public List<EmailNotificationMaster> notificationListByCommunicationType(long companyId, String communicationType) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public List<EmailNotificationMaster> notificationListByCompanyId(long companyId) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public EmailNotificationMaster addNotification(EmailNotificationMaster notification) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public EmailNotificationMaster getNotification(long mailId) {
//		// TODO Auto-generated method stub
//		return null;
//	}

//	@Override
//	public String sendNotification(EmailNotificationMasterDTO notificationDTO) {
//		EmailNotificationMaster notification = emailNotificationRepository.findOne(notificationDTO.getMailId());
//
//		String notificationText = notification.getNotificationText();
//
//	  
//		Map<String, String> map = new HashMap<String, String>();
//	 
//		String[] valueArr = notificationDTO.getMessageValues();
//		for (int i = 0; i < notificationDTO.getMessageValues().length; i++) {
//			map.put("VAR" + i, valueArr[i]);
//		}
//
//		for (Map.Entry<String, String> entry : map.entrySet()) {
//			notificationText = notificationText.replace("<" + entry.getKey() + ">", entry.getValue());
//		}
//	 
//		notification.setNotificationText(notificationText);
//
//		 
//
//			Message msg = new Message(new SendMail());
//			msg.executeMessage(notification, mailSender);
//		 
//			smsService.sendNotificationBySms(notification, notificationDTO.getMobileNo());
//	 
//		return notificationText;
//	}
//
//	@Override
//	public void sendMail(EmailNotificationMaster notification) {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public String sendNotification(long notificationId) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public String sendNotification(NotificationMailSmsDto notificationDTO) {
//		System.out.println("NotificationServicesImpl.sendNotification()");
//		logger.info("======================== sendNotification() in method============================");
//		EmailNotificationMaster notification = emailNotificationRepository.findAllByStatus(notificationDTO.getNotificationType());
//		notificationDTO.setSubject(notification.getSubject());
//		notificationDTO.setMessage(notification.getNotificationText());
//		notificationDTO.setFrom(notification.getUserName());
//		notificationDTO.setPassword(notification.getPassword());
//		List<String> to = notificationDTO.getTo();
//		to.add(notification.getToMail());
// 		logger.info("======================== sendNotification() in mail IDs============================" + to);
//		List<String> to2 = to.stream().distinct().collect(Collectors.toList());
//		notificationDTO.setTo(to2);
//
//		List<String> cc = notificationDTO.getCc();
//		cc.add(notification.getCc());
//		List<String> cc2 = cc.stream().distinct().collect(Collectors.toList());
//		notificationDTO.setCc(cc2);
//
//	 
//			Message msg = new Message(new SendMail());
//			msg.executeMessage(notificationDTO, mailSender, velocityEngine);
//		 
//		return notification.getNotificationText();
//	}

}
