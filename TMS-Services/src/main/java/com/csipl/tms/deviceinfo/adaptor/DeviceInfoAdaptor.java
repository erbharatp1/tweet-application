package com.csipl.tms.deviceinfo.adaptor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.csipl.tms.dto.deviceinfo.BiometricDeviceInfoDTO;
import com.csipl.tms.dto.deviceinfo.DeviceInfoDTO;
import com.csipl.tms.model.deviceinfo.DeviceInfo;
import com.csipl.tms.service.Adaptor;

public class DeviceInfoAdaptor implements Adaptor<DeviceInfoDTO, DeviceInfo> {

	String prefix;
	Long companyId;
	String deviceNames;
	String deviceIds;

	@Override
	public List<DeviceInfo> uiDtoToDatabaseModelList(List<DeviceInfoDTO> uiobj) {
		return null;
	}

	@Override
	public List<DeviceInfoDTO> databaseModelToUiDtoList(List<DeviceInfo> dbobj) {
		return null;
	}

	@Override
	public DeviceInfo uiDtoToDatabaseModel(DeviceInfoDTO uiobj) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DeviceInfoDTO databaseModelToUiDto(DeviceInfo dbobj) {
		return null;
	}

	public Object[] getDeviceInfo(List<DeviceInfo> deviceInfoList) {
		Object object[] = new Object[3];
		StringBuffer deviceNameSb = new StringBuffer();
		StringBuffer deviceIdSb = new StringBuffer();
		for (DeviceInfo deviceInfo : deviceInfoList) {
			prefix = deviceInfo.getPrefix();
			companyId = deviceInfo.getCompanyId();
			String deviceName = deviceInfo.getDeviceName();
			deviceNameSb.append("'").append(deviceName).append("'").append(",");
			/*Long deviceId = deviceInfo.getDeviceId();
			deviceIdSb.append(deviceId).append(",");*/
		}
		deviceNames = deviceNameSb.substring(0, deviceNameSb.length() - 1).toString();
		//deviceIds = deviceIdSb.substring(0, deviceIdSb.length() - 1).toString();
		object[0] = prefix;
		object[1] = companyId;
		object[2] = deviceNames;
		//object[3] = deviceIds;
		return object;
	}
	
	
	//info.deviceId ,info.machineIP,info.deviceName, info.prefix, info.serialNumber,info.companyId 
	public List<DeviceInfo> getDeviceInfoList(List<Object[]> deviceInfoObjList) {
		List<DeviceInfo> deviceInfoList = new ArrayList<DeviceInfo>();
		StringBuffer deviceNameSb = new StringBuffer();
		StringBuffer deviceIdSb = new StringBuffer();
		for (Object[] deviceInfo : deviceInfoObjList) {
			DeviceInfo deviceInfoDetails = new DeviceInfo();
			Long deviceId=deviceInfo[0]!=null?Long.parseLong(deviceInfo[0].toString()):null;
			String machineIP=deviceInfo[1]!=null?(String)deviceInfo[1]:null;
			String deviceName=deviceInfo[2]!=null?(String)deviceInfo[2]:null;
			String prefix=deviceInfo[3]!=null?(String)deviceInfo[3]:null;
			String serialNumber=deviceInfo[4]!=null?(String)deviceInfo[4]:null;
			Long companyId=deviceInfo[5]!=null?Long.parseLong(deviceInfo[5].toString()):null;
			
			deviceInfoDetails.setDeviceId(deviceId);
			deviceInfoDetails.setMachineIP(machineIP);
			deviceInfoDetails.setDeviceName(deviceName);
			deviceInfoDetails.setPrefix(prefix);
			deviceInfoDetails.setSerialNumber(serialNumber);
			deviceInfoDetails.setCompanyId(companyId);
			deviceInfoList.add(deviceInfoDetails);
			
		}
	
		return deviceInfoList;
	}

	
	public DeviceInfo uiDtoToBioDatabaseModel(BiometricDeviceInfoDTO bmdeviceInfoDTO) {
		DeviceInfo deviceInfo=new DeviceInfo();
		
		if(bmdeviceInfoDTO.getDeviceId()!=null) {
			deviceInfo.setDeviceId(bmdeviceInfoDTO.getDeviceId());
		}
		deviceInfo.setCompanyId(bmdeviceInfoDTO.getCompanyId());
		deviceInfo.setMachineIP(bmdeviceInfoDTO.getMachineIP());
		deviceInfo.setSerialNumber(bmdeviceInfoDTO.getSerialNumber());
		deviceInfo.setDeviceName(bmdeviceInfoDTO.getDeviceName());
		//deviceInfo.setPrefix(bmdeviceInfoDTO.getPrefix());
		deviceInfo.setPrefix("COM");
		deviceInfo.setAddress(bmdeviceInfoDTO.getAddress());
		deviceInfo.setUserId(bmdeviceInfoDTO.getUserId());
		deviceInfo.setUserIdUpdate(bmdeviceInfoDTO.getUserIdUpdate());
		deviceInfo.setDateCreated(bmdeviceInfoDTO.getDateCreated());
		deviceInfo.setDateUpdate(bmdeviceInfoDTO.getDateUpdate());
		deviceInfo.setStatus(bmdeviceInfoDTO.getStatus());
		return deviceInfo;
	}
	
	
public List<BiometricDeviceInfoDTO> databaseModelToBiometricUiDtoList(List<Object[]> deviceInfoList) {
		
		List<BiometricDeviceInfoDTO> deviceInfoDTOList = new ArrayList<BiometricDeviceInfoDTO>();
		
		for (Object[] deviceInfo : deviceInfoList) {
			BiometricDeviceInfoDTO deviceInfoDetailsDTO = new BiometricDeviceInfoDTO();
			Long deviceId=deviceInfo[0]!=null?Long.parseLong(deviceInfo[0].toString()):null;
			String machineIP=deviceInfo[1]!=null?(String)deviceInfo[1]:null;
			String serialNumber=deviceInfo[2]!=null?(String)deviceInfo[2]:null;
			String deviceName=deviceInfo[3]!=null?(String)deviceInfo[3]:null;
			String address=deviceInfo[4]!=null?(String)deviceInfo[4]:null;
			Long companyId=deviceInfo[5]!=null?Long.parseLong(deviceInfo[5].toString()):null;
			String prefix=deviceInfo[6]!=null?(String)deviceInfo[6]:null;
			Long userId=deviceInfo[7]!=null?Long.parseLong(deviceInfo[7].toString()):null;
            Date dateCreated=deviceInfo[8]!=null?(Date)deviceInfo[8]:null;
			String status=deviceInfo[9]!=null?(String)deviceInfo[9]:null;
            
            deviceInfoDetailsDTO.setDeviceId(deviceId);
            deviceInfoDetailsDTO.setMachineIP(machineIP);
            deviceInfoDetailsDTO.setSerialNumber(serialNumber);
            deviceInfoDetailsDTO.setDeviceName(deviceName);
            deviceInfoDetailsDTO.setAddress(address);
            deviceInfoDetailsDTO.setCompanyId(companyId);
            deviceInfoDetailsDTO.setPrefix(prefix);
            deviceInfoDetailsDTO.setUserId(userId);
            deviceInfoDetailsDTO.setDateCreated(dateCreated);
			deviceInfoDetailsDTO.setStatus(status);
            
			deviceInfoDTOList.add(deviceInfoDetailsDTO);
		}
	
		return deviceInfoDTOList;
	
	}
}
