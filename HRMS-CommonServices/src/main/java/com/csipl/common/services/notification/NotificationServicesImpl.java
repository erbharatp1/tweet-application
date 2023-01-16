package com.csipl.common.services.notification;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.velocity.app.VelocityEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.csipl.common.dto.notification.NotificationDTO;
import com.csipl.common.dto.notification.NotificationMailSmsDto;
import com.csipl.common.message.utils.Message;
import com.csipl.common.message.utils.SendMail;
import com.csipl.common.message.utils.SmsService;
import com.csipl.common.model.Notification;
import com.csipl.common.services.notification.repository.NotificationRepository;

@Service("notificationServices")
public class NotificationServicesImpl implements NotificationServices {
	private static final Logger logger = LoggerFactory.getLogger(NotificationServicesImpl.class);
	@Autowired
	NotificationRepository notificationRepository;

	@Autowired
	JavaMailSender mailSender;

	@Autowired
	private VelocityEngine velocityEngine;

	@Autowired
	SmsService smsService;

	@Override
	public List<Notification> notificationListByCommunicationType(long companyId, String communicationType) {

		return notificationRepository.notificationListByCommunicationType(companyId, communicationType);

	}

	@Override
	public List<Notification> notificationListByCompanyId(long companyId) {
		return null;
	}

	@Override
	public Notification addNotification(Notification notification) {

		return notificationRepository.save(notification);

	}

	@Override
	public Notification getNotification(long notificationId) {

		return notificationRepository.getNotification(notificationId);

	}

	@Override
	public String sendNotification(NotificationDTO notificationDTO) {

		Notification notification = notificationRepository.getNotification(notificationDTO.getNotificationId());

		String notificationText = notification.getNotificationText();

		logger.info("=====before======" + notificationText);
		/*
		 * 
		 * logger.info("========before======="+notificationText); for( Map.Entry<String,
		 * String> entry : notificationDTO.getArrayMap().get(j).entrySet()) {
		 * 
		 * String key = entry.getKey(); String value = entry.getValue();
		 * notificationText=notificationText.replace(key, value);
		 * logger.info("====key===="+key+"===value==="+value); j++; }
		 * 
		 */
		Map<String, String> map = new HashMap<String, String>();
		logger.info("notificationDTO.getMessageValues()" + notificationDTO.getMessageValues());
		String[] valueArr = notificationDTO.getMessageValues();
		for (int i = 0; i < notificationDTO.getMessageValues().length; i++) {
			map.put("VAR" + i, valueArr[i]);
		}

		for (Map.Entry<String, String> entry : map.entrySet()) {
			notificationText = notificationText.replace("<" + entry.getKey() + ">", entry.getValue());
		}
		logger.info("=====after======" + notificationText);
		notification.setNotificationText(notificationText);

		if (notification.getIsMail() > 0) {

			Message msg = new Message(new SendMail());
			msg.executeMessage(notification, mailSender);
		}
		if (notification.getSms() > 0) {
			smsService.sendNotificationBySms(notification, notificationDTO.getMobileNo());
		}
		if (notification.getUi() > 0) {
			return notificationText;

		}
		return notificationText;
	}

	@Override
	public String sendNotification(NotificationMailSmsDto notificationDTO) {
		// TODO Auto-generated method stub
		System.out.println("NotificationServicesImpl.sendNotification()");
		//logger.info("======================== sendNotification() in method============================");
		Notification notification = notificationRepository.getNotification(notificationDTO.getNotificationType());
		notificationDTO.setSubject(notification.getMail().getSubject());
		notificationDTO.setMessage(notification.getNotificationText());
		notificationDTO.setFrom(notification.getMail().getFromMail());

		List<String> to = notificationDTO.getTo();
		to.add(notification.getMail().getToMail());
//		logger.info("======================== sendNotification() in mail IDs============================" + to);
		List<String> to2 = to.stream().distinct().collect(Collectors.toList());
		notificationDTO.setTo(to2);

		List<String> cc = notificationDTO.getCc();
		cc.add(notification.getMail().getCc());
		List<String> cc2 = cc.stream().distinct().collect(Collectors.toList());
		notificationDTO.setCc(cc2);

		if (notification.getIsMail() > 0) {

			logger.info(" sendNotification()in in block of method");

			Message msg = new Message(new SendMail());
			msg.executeMessage(notificationDTO, mailSender, velocityEngine);
		}
//		if (notification.getSms() > 0) {
//			logger.info("NotificationServicesImpl.sendNotification()"+notificationDTO.getMobileNo());
// 			smsService.sendNotificationBySms(notification, notificationDTO.getMobileNo());
// 			
// 		}
		if (notification.getUi() > 0) {
			return notification.getNotificationText();

		}
		return notification.getNotificationText();
	}

	@Override
	public String sendNotification(long notificationId) {

		Notification notification = notificationRepository.getNotification(notificationId);

		return notification.getNotificationText();
	}

	@Override
	public void sendMail(Notification notification) {
		Message msg = new Message(new SendMail());
		msg.executeMessage(notification, mailSender);

	}

}