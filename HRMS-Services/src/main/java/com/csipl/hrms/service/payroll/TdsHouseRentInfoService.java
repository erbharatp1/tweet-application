package com.csipl.hrms.service.payroll;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.csipl.hrms.model.payroll.TdsHouseRentInfo;

public interface TdsHouseRentInfoService {
	public void saveHouseRentInfo(TdsHouseRentInfo tdsHouseRentInfo, List<MultipartFile> fileInfo);

	public List<TdsHouseRentInfo> getHouseRentInfoList(Long tdsTransactionId);

	public int deleteHouseRentInfoFile(Long tdsHouseRentFileInfoId);

	public int deleteTdsHouseRentInfo(Long tdsHouseRentInfoId);
}
