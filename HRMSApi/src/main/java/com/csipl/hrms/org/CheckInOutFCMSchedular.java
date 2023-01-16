package com.csipl.hrms.org;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import com.csipl.hrms.service.common.PushNotificationsServiceImpl;

@Component
public class CheckInOutFCMSchedular {

	@Autowired
	PushNotificationsServiceImpl fcm;

	@Scheduled(cron = "0 0 20 * * ?")
	public void checkOutNotification() {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
			Date now = new Date();
			String strDate = sdf.format(now);
			System.out.println("Java attendanceio expression:: " + strDate);

			//fcm.fcmRequestforCheckIO("Attendance Check Out",
			//		"Please Do Attendance Out Otherwise You Will Be Mark As Absent", "attendanceio", 0l,
			//		"attendanceio");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Scheduled(cron = "0 0 9 * * ?")
	public void checkInNotification() throws ParseException {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
			Date now = new Date();
			String strDate = sdf.format(now);
			System.out.println("Java attendanceio expression:: " + strDate);

			//fcm.fcmRequestforCheckIO("Attendance Check In", "Please Mark Attendance In", "attendanceio", 0l,
			//		"attendanceio");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
