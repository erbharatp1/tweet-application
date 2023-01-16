package com.csipl.tms.deviceinfo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.csipl.tms.model.deviceinfo.DeviceInfo;

public interface DeviceInfoRepository extends CrudRepository<DeviceInfo, Long>{

	@Query("from DeviceInfo where companyId=?1")
	List<DeviceInfo> findAllDeviceInfo(long companyId);
	
	String getDeviceInfo ="SELECT info.deviceId ,info.machineIP,info.deviceName, info.prefix, info.serialNumber,info.companyId FROM DeviceInfo info JOIN Company com ON com.companyId = info.companyId AND com.activeStatus = 'AC' ";
	@Query(value=getDeviceInfo,nativeQuery = true)
	List<Object[]> findAllDevice();
	

	String getAllBiometricConfigLstInfo ="SELECT info.deviceId ,info.machineIP,info.serialNumber,info.deviceName, info.address,info.companyId, info.prefix,info.userId,info.dateCreated,info.status FROM DeviceInfo info WHERE info.companyId=?1";
	@Query(value=getAllBiometricConfigLstInfo,nativeQuery = true)
	List<Object[]> getAllBiometricConfigList(Long companyId);


}
