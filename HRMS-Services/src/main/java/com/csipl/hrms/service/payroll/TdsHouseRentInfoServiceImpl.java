package com.csipl.hrms.service.payroll;

import java.io.File;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.csipl.hrms.common.constant.StatusMessage;
import com.csipl.hrms.common.util.HrmsGlobalConstantUtil;
import com.csipl.hrms.model.employee.MasterBook;
import com.csipl.hrms.model.payroll.TdsHouseRentFileInfo;
import com.csipl.hrms.model.payroll.TdsHouseRentInfo;
import com.csipl.hrms.service.employee.repository.MasterBookRepository;
import com.csipl.hrms.service.organization.StorageService;
import com.csipl.hrms.service.payroll.repository.TdsHouseRentInfoRepository;

@Service
public class TdsHouseRentInfoServiceImpl implements TdsHouseRentInfoService {

	@Autowired
	private MasterBookRepository masterBookRepository;

	@Autowired
	StorageService storageService;

	@Autowired
	TdsHouseRentInfoRepository tdsHouseRentInfoRepository;

	@Override
	@Transactional
	public void saveHouseRentInfo(TdsHouseRentInfo tdsHouseRentInfo, List<MultipartFile> fileInfo) {

		String bookCode = "EMPNO";

		List<TdsHouseRentFileInfo> tdsHouseRentFileInfoList = new ArrayList<TdsHouseRentFileInfo>();

		for (MultipartFile file : fileInfo) {
			
			TdsHouseRentFileInfo tdsHouseRentFileInfo = new TdsHouseRentFileInfo();
			
			System.out.println("File Name=====  " + file.getOriginalFilename());
			MasterBook masterBook = masterBookRepository.findMasterBook(1l, bookCode);
			BigDecimal lastNumberValue;
			lastNumberValue = masterBook.getLastNo();
			long longValue;
			longValue = lastNumberValue.longValue() + 1;
			BigDecimal newDecimalValue = new BigDecimal(longValue);
			String fileName = masterBook.getPrefixBook() + newDecimalValue;
			String extension = FilenameUtils.getExtension(file.getOriginalFilename());
			fileName = fileName + "." + extension;
			// logger.info(" File with extension " + fileName);
//			String path = File.separator + "assets" + File.separator + "ProjectDoc";
			String path = storageService.createFilePath(HrmsGlobalConstantUtil.PROJECT_DOC);
			String dbPath = path + File.separator + fileName;
			storageService.store(file, path, fileName);
			masterBook.setLastNo(newDecimalValue);
			masterBookRepository.save(masterBook);
			
			tdsHouseRentFileInfo.setFileName(fileName);
			tdsHouseRentFileInfo.setFilePath(dbPath);
			tdsHouseRentFileInfo.setActiveStatus(StatusMessage.ACTIVE_CODE);
			tdsHouseRentFileInfo.setUserId( Long.valueOf( tdsHouseRentInfo.getUserId().toString()));
			tdsHouseRentFileInfo.setDateCreated(new Date());
			tdsHouseRentFileInfo.setTdsHouseRentInfo(tdsHouseRentInfo);
			tdsHouseRentFileInfo.setOriginalFilename(file.getOriginalFilename());
			tdsHouseRentFileInfoList.add(tdsHouseRentFileInfo);

		}
		tdsHouseRentInfo.setTdsHouseRentFileInfos(tdsHouseRentFileInfoList);
		
		  tdsHouseRentInfoRepository.save(tdsHouseRentInfo);
	}

	@Override
	public List<TdsHouseRentInfo> getHouseRentInfoList(Long tdsTransactionId) {
		return tdsHouseRentInfoRepository.findAllHouseRentInfo(tdsTransactionId);
	}

	@Override
	@Transactional
	public int deleteHouseRentInfoFile(Long tdsHouseRentFileInfoId) {
		// TODO Auto-generated method stub
		return tdsHouseRentInfoRepository.deleteHouseRentInfoFile(tdsHouseRentFileInfoId);
	}

	@Override
	@Transactional
	public int deleteTdsHouseRentInfo(Long tdsHouseRentInfoId) {
		// TODO Auto-generated method stub
		return tdsHouseRentInfoRepository.deleteTdsHouseRentInfo(tdsHouseRentInfoId);
	}

}
