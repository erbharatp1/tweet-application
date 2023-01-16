package com.csipl.hrms.service.payroll;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.csipl.hrms.common.constant.StatusMessage;
import com.csipl.hrms.common.util.DateUtils;
import com.csipl.hrms.common.util.HrmsGlobalConstantUtil;
import com.csipl.hrms.model.employee.MasterBook;
import com.csipl.hrms.model.payroll.PreviousEmployerIncomeFile;
import com.csipl.hrms.model.payroll.PreviousEmployerIncomeTds;
import com.csipl.hrms.model.payrollprocess.FinancialYear;
import com.csipl.hrms.service.employee.repository.MasterBookRepository;
import com.csipl.hrms.service.organization.StorageService;
import com.csipl.hrms.service.payroll.repository.FinancialYearRepository;
import com.csipl.hrms.service.payroll.repository.PreviousEmployerIncomeFileRepository;
import com.csipl.hrms.service.payroll.repository.PreviousEmployerIncomeRepository;

@Service("previousEmployerIncomeService")
public class PreviousEmployerIncomeServiceImpl implements PreviousEmployerIncomeService {

	
 
	
	@Autowired
	private MasterBookRepository masterBookRepository;
	
	@Autowired
	StorageService storageService;
	 
	@Autowired
	PreviousEmployerIncomeRepository previousEmployerIncomeRepository;
	
	@Autowired
	PreviousEmployerIncomeFileRepository previousEmployerIncomeFileRepository;
	
	
	@Autowired
	FinancialYearRepository financialYearRepository;
	
	@Override
	public List<Object[]> getPreviousEmployerIncomeObjectList(Long employeeId, Long financialYearId) {
		return previousEmployerIncomeRepository.findAllPreviousEmployerIncome(employeeId, financialYearId);
	}

//	@Override
//	public void save(List<PreviousEmployerIncomeTds> previousEmployerIncomeTdsList, Long companyId) {
//		DateUtils dateUtils=new DateUtils();
//		Date currentDate=dateUtils.getCurrentDate();
//		FinancialYear financialYear=financialYearRepository.getFinancialYear(currentDate,companyId);
//		previousEmployerIncomeTdsList.forEach(previousEmployerIncomeTds->{
//			previousEmployerIncomeTds.setFinancialYearId(financialYear.getFinancialYearId());
//		});
//		previousEmployerIncomeRepository.save(previousEmployerIncomeTdsList);
//	}

	@Override
	public List<PreviousEmployerIncomeTds> getPreviousEmployerIncomeList(Long employeeId, Long companyId) {
		DateUtils dateUtils=new DateUtils();
		Date currentDate=dateUtils.getCurrentDate();
		FinancialYear financialYear=financialYearRepository.getFinancialYear(currentDate,companyId);
		return previousEmployerIncomeRepository.getAllPreviousEmployerIncome(employeeId, financialYear.getFinancialYearId());
	}
	
	
	
	//new
	@Override
	@Transactional
	public void save(List<PreviousEmployerIncomeTds> previousEmployerIncomeTdsList, Long employeeId, Long companyId,List<MultipartFile> fileInfo,Long financialYearId) {
		DateUtils dateUtils=new DateUtils();
		Date currentDate=dateUtils.getCurrentDate();
		FinancialYear financialYear=financialYearRepository.getFinancialYear(currentDate,companyId);
		previousEmployerIncomeTdsList.forEach(previousEmployerIncomeTds->{
			previousEmployerIncomeTds.setFinancialYearId(financialYear.getFinancialYearId());
		});
		
		String bookCode = "EMPNO";
		List<PreviousEmployerIncomeFile>  previousEmployerIncomeTdsFileInfoList=new ArrayList<PreviousEmployerIncomeFile>();
		
            for (MultipartFile file : fileInfo) {
			
	        PreviousEmployerIncomeFile previousEmployerIncomeTdsFileInfo = new PreviousEmployerIncomeFile();
			
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
			
			previousEmployerIncomeTdsFileInfo.setFileName(fileName);
			previousEmployerIncomeTdsFileInfo.setFilePath(dbPath);
			previousEmployerIncomeTdsFileInfo.setActiveStatus(StatusMessage.ACTIVE_CODE);
			previousEmployerIncomeTdsFileInfo.setDateCreated(new Date());
			previousEmployerIncomeTdsFileInfo.setOriginalFilename(file.getOriginalFilename());
			previousEmployerIncomeTdsFileInfo.setEmployeeId(employeeId);
			previousEmployerIncomeTdsFileInfo.setFinancialYearId(financialYearId);
			
	        Long userId=0l;
			for (PreviousEmployerIncomeTds previousEmployerIncomeTds : previousEmployerIncomeTdsList) {
				if(previousEmployerIncomeTds.getUserId()!=null)
					userId=previousEmployerIncomeTds.getUserId();
//		      previousEmployerIncomeTdsFileInfo.setUserId(Long.valueOf(previousEmployerIncomeTds.getUserId().toString());
					
			}
		previousEmployerIncomeTdsFileInfo.setUserId(userId);

		previousEmployerIncomeTdsFileInfoList.add(previousEmployerIncomeTdsFileInfo);
		}

		/*for (PreviousEmployerIncomeTds previousEmployerIncomeTds : previousEmployerIncomeTdsList) {
			previousEmployerIncomeTds.setPreviousEmployerIncomeFile(previousEmployerIncomeTdsFileInfoList);

		}*/
		previousEmployerIncomeFileRepository.save(previousEmployerIncomeTdsFileInfoList);
	
		previousEmployerIncomeRepository.save(previousEmployerIncomeTdsList);
	}
	
	 
	@Override
	@Transactional
	public int deletePreviousEmployerIncomeFileInfo(Long previousEmployerIncomeFileId) {
		// TODO Auto-generated method stub
		return previousEmployerIncomeFileRepository.deletePreviousEmployerIncomeFileInfo(previousEmployerIncomeFileId);
	}

	@Override
	public List<PreviousEmployerIncomeFile> getPreviousEmployerIncomeFileList(Long empId, Long financialYrId) {
		// TODO Auto-generated method stub
		return previousEmployerIncomeFileRepository.findAllPreviousEmployerIncomeFile(empId,financialYrId);
	}
}
