package com.csipl.tms.biometricconfig.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.csipl.tms.deviceinfo.adaptor.DeviceInfoAdaptor;
import com.csipl.tms.deviceinfo.service.DeviceInfoService;
import com.csipl.tms.dto.deviceinfo.BiometricDeviceInfoDTO;
import com.csipl.tms.model.deviceinfo.DeviceInfo;

@RestController
@RequestMapping("/bioConfig")
public class BiometricConfigurationController {
	
	private static Logger logger=LoggerFactory.getLogger(BiometricConfigurationController.class);
	
	@Autowired
	DeviceInfoService deviceInfoService;
	 
	DeviceInfoAdaptor deviceInfoAdaptor=new DeviceInfoAdaptor();
	
	@PostMapping("/saveBioConfigDeviceInfo")
	public void saveBiometricConfig(@RequestBody BiometricDeviceInfoDTO  deviceInfoDto) {
		logger.info("saveBiometricConfig calling:");
		DeviceInfo deviceInfo=deviceInfoAdaptor.uiDtoToBioDatabaseModel(deviceInfoDto);
		deviceInfoService.saveBiometricConfig(deviceInfo);
	}
	
	@GetMapping("getBioConfigDeviceInfoList/{companyId}")
	public List<BiometricDeviceInfoDTO> getBiometricConfigList(@PathVariable("companyId")String companyId){
		
		Long compId=Long.parseLong(companyId);
		logger.info("getBiometricConfigList calling"+compId);
		List<Object[]> deviceInfoList=deviceInfoService.getAllBiometricConfigList(compId);
		
		return deviceInfoAdaptor.databaseModelToBiometricUiDtoList(deviceInfoList);	
	}
}
