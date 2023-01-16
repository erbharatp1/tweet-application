package com.csipl.hrms.service.common;

import java.util.List;

import com.csipl.common.dto.notification.NotificationMailSmsDto;
import com.csipl.common.model.EmailNotificationMaster;
import com.csipl.hrms.dto.common.EmailNotificationMasterDTO;

public interface EmailNotificationService {

	public EmailNotificationMaster save(EmailNotificationMaster mail);

	List<EmailNotificationMaster> findEMailList(Long companyId);

	public EmailNotificationMaster findById(Long companyId, Long mailId);

	EmailNotificationMaster findEMailListByStatus(String mailType);

//	public List<EmailNotificationMaster> notificationListByCommunicationType(long companyId, String communicationType);
//
//	public List<EmailNotificationMaster> notificationListByCompanyId(long companyId);
//
//	public EmailNotificationMaster addNotification(EmailNotificationMaster notification);
//
//	public EmailNotificationMaster getNotification(long mailId);

//	public String sendNotification(EmailNotificationMasterDTO notificationDTO);
//
//	public String sendNotification(NotificationMailSmsDto notificationDTO);
//
//	public void sendMail(EmailNotificationMaster notification);
//
//	public String sendNotification(long notificationId);

}
