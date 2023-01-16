package com.csipl.tms.model.deviceinfo;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the DeviceLogsInfo database table.
 * 
 */
@Entity
@NamedQuery(name="DeviceLogsInfo.findAll", query="SELECT d FROM DeviceLogsInfo d")
public class DeviceLogsInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long deviceLogId;

	private Long deviceId;

	private String direction;

	@Temporal(TemporalType.TIMESTAMP)
	private Date logDate;

	private String userId;
	
	private String latitude;

	private String longitude;
	
	private String mode;
	
	private String address;


	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public DeviceLogsInfo() {
	}

	public Long getDeviceLogId() {
		return this.deviceLogId;
	}

	public void setDeviceLogId(Long deviceLogId) {
		this.deviceLogId = deviceLogId;
	}

	public Long getDeviceId() {
		return this.deviceId;
	}

	public void setDeviceId(Long deviceId) {
		this.deviceId = deviceId;
	}

	public String getDirection() {
		return this.direction;
	}

	public void setDirection(String direction) {
		this.direction = direction;
	}

	public Date getLogDate() {
		return this.logDate;
	}

	public void setLogDate(Date logDate) {
		this.logDate = logDate;
	}

	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}

}