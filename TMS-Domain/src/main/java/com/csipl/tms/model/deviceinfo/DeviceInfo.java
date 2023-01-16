package com.csipl.tms.model.deviceinfo;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.math.BigInteger;

/**
 * The persistent class for the DeviceInfo database table.
 * 
 */
@Entity
@NamedQuery(name = "DeviceInfo.findAll", query = "SELECT d FROM DeviceInfo d")
public class DeviceInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long deviceId;

	private Long companyId;

	@Temporal(TemporalType.DATE)
	private Date dateCreated;

	@Temporal(TemporalType.DATE)
	private Date dateUpdate;

	private String deviceName;

	private String machineIP;

	private String prefix;

	private String serialNumber;

	private Long userId;

	private Long userIdUpdate;
	
	
	/*private String mode;

	  private String latitude;

	  private String longitude;
    */

	private String address;

	private String status;
	
  /*public String getMode() {
		return mode;
	}
    
	public void setMode(String mode) {
		this.mode = mode;
	}

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
	}*/

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public DeviceInfo() {
	}

	public Long getDeviceId() {
		return this.deviceId;
	}

	public void setDeviceId(Long deviceId) {
		this.deviceId = deviceId;
	}

	public Long getCompanyId() {
		return this.companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	public Date getDateCreated() {
		return this.dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	public Date getDateUpdate() {
		return this.dateUpdate;
	}

	public void setDateUpdate(Date dateUpdate) {
		this.dateUpdate = dateUpdate;
	}

	public String getDeviceName() {
		return this.deviceName;
	}

	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}

	public String getMachineIP() {
		return this.machineIP;
	}

	public void setMachineIP(String machineIP) {
		this.machineIP = machineIP;
	}

	public String getPrefix() {
		return this.prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public String getSerialNumber() {
		return this.serialNumber;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

	public Long getUserId() {
		return this.userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getUserIdUpdate() {
		return this.userIdUpdate;
	}

	public void setUserIdUpdate(Long userIdUpdate) {
		this.userIdUpdate = userIdUpdate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	

}