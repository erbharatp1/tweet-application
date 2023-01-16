package com.csipl.tms.attendanceepush.service;

import java.util.List;

import com.csipl.tms.model.attedanceepush.DeviceLog;

public interface AttendanceEPushService {
	String attendanceEPush(List<DeviceLog> epushLog);
}
