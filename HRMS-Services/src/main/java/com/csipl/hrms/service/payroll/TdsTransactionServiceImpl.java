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
import com.csipl.hrms.model.payroll.TdsHouseRentFileInfo;
import com.csipl.hrms.model.payroll.TdsTransaction;
import com.csipl.hrms.model.payroll.TdsTransactionFileInfo;
import com.csipl.hrms.model.payrollprocess.FinancialYear;
import com.csipl.hrms.service.employee.repository.MasterBookRepository;
import com.csipl.hrms.service.organization.StorageService;
import com.csipl.hrms.service.payroll.repository.FinancialYearRepository;
import com.csipl.hrms.service.payroll.repository.OtherIncomeRepository;
import com.csipl.hrms.service.payroll.repository.PreviousEmployerIncomeRepository;
import com.csipl.hrms.service.payroll.repository.TdsApprovalRepository;
import com.csipl.hrms.service.payroll.repository.TdsHouseRentInfoRepository;
import com.csipl.hrms.service.payroll.repository.TdsTransactionFileInfoRepository;
import com.csipl.hrms.service.payroll.repository.TdsTransactionRepository;

@Service("tdsTransactionService")
public class TdsTransactionServiceImpl implements TdsTransactionService {

	@Autowired
	TdsTransactionRepository tdsTransactionRepository;
	
	@Autowired
	FinancialYearRepository financialYearRepository;
	
	@Autowired
	OtherIncomeRepository otherIncomeRepository;
	
	@Autowired
	PreviousEmployerIncomeRepository 	previousEmployerIncomeRepository;

	@Autowired
	private MasterBookRepository masterBookRepository;
	
	@Autowired
	TdsApprovalRepository tdsApprovalRepository;
	
	@Autowired
	StorageService storageService;
	
	@Autowired
	TdsTransactionFileInfoRepository tdsTransactionFileInfoRepository;
	
	@Override
	public List<Object[]> getTdsTransactionObjectList(Long employeeId,Long financialYearId) {
//		DateUtils dateUtils=new DateUtils();
//		Date currentDate=dateUtils.getCurrentDate();
//		FinancialYear financialYear=financialYearRepository.getFinancialYear(currentDate,companyId);
		return tdsTransactionRepository.getTdsTransactionObjectList(employeeId, financialYearId);
	}

	/**
	 * Save OR update department object into Database 
	 */
	@Override
	@Transactional
	public void save(List<TdsTransaction> tdsTransactionList, Long employeeId,Long companyId, List<MultipartFile> fileInfo) {		
		DateUtils dateUtils=new DateUtils();
		Date currentDate=dateUtils.getCurrentDate();
		FinancialYear financialYear=financialYearRepository.getFinancialYear(currentDate,companyId);
		tdsTransactionList.forEach(tdsTransaction->{
			tdsTransaction.setFinancialYear(financialYear);
		});
		
		String bookCode = "EMPNO";
		List<TdsTransactionFileInfo> TdsTransactionFileInfoList = new ArrayList<TdsTransactionFileInfo>();

		for (MultipartFile file : fileInfo) {
			
			TdsTransactionFileInfo tdsTransactionFileInfo = new TdsTransactionFileInfo();
			
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
			
			tdsTransactionFileInfo.setFileName(fileName);
			tdsTransactionFileInfo.setFilePath(dbPath);
			tdsTransactionFileInfo.setActiveStatus(StatusMessage.ACTIVE_CODE);
			tdsTransactionFileInfo.setDateCreated(new Date());
			tdsTransactionFileInfo.setOriginalFilename(file.getOriginalFilename());
			for(TdsTransaction tdsTransaction:tdsTransactionList) {
				tdsTransactionFileInfo.setTdsTransaction(tdsTransaction);
				tdsTransactionFileInfo.setUserId( Long.valueOf( tdsTransaction.getUserId().toString()));
			}
			
			TdsTransactionFileInfoList.add(tdsTransactionFileInfo);
		}
		
		for(TdsTransaction tdsTransaction:tdsTransactionList) {
			tdsTransaction.setTdsTransactionFileInfo(TdsTransactionFileInfoList);
		}
		
		tdsTransactionRepository.save(tdsTransactionList);
	}
	/**
	 * To get List of TdsTransaction objects from Database based on empIdId and financialYear
	 */
	@Override
	public List<TdsTransaction> getTdsTrasactionListforApproval(Long empId, FinancialYear financialYear) {
		return tdsApprovalRepository.getTdsTrasactionList(empId,financialYear.getFinancialYearId());
	}

	@Override
	public List<TdsTransaction> getTdsTransactionList(Long employeeId,Long companyId) {
		DateUtils dateUtils=new DateUtils();
		Date currentDate=dateUtils.getCurrentDate();
		FinancialYear financialYear=financialYearRepository.getFinancialYear(currentDate,companyId);
		return tdsTransactionRepository.getTdsTransactionList(employeeId, financialYear.getFinancialYearId());
	}

	@Override
	public List<Object[]> getTdsSummaryObjectList(Long employeeId, Long companyId) {
		DateUtils dateUtils=new DateUtils();
		Date currentDate=dateUtils.getCurrentDate();
		FinancialYear financialYear=financialYearRepository.getFinancialYear(currentDate,companyId);
		return  tdsTransactionRepository.getTdsSummaryObjectList(employeeId, financialYear.getFinancialYearId());
	}

	@Override
	public BigDecimal getTotalInvestment(Long employeeId, Long companyId) {
		DateUtils dateUtils=new DateUtils();
		Date currentDate=dateUtils.getCurrentDate();
		FinancialYear financialYear=financialYearRepository.getFinancialYear(currentDate,companyId);
		return tdsTransactionRepository.getTotalInvestment(employeeId, financialYear.getFinancialYearId());
	}

	@Override
	public List<TdsTransactionFileInfo> getTdsTransactionFileInfoList(Long tdsTransactionId) {
		return tdsTransactionFileInfoRepository.getTdsTransactionFileInfoList(tdsTransactionId);
	}

	@Override
	@Transactional
	public int deleteTdsTransactionFileInfo(Long tdsTransactionFileInfoId) {
		// TODO Auto-generated method stub
		return tdsTransactionFileInfoRepository.deleteTdsTransactionFileInfo(tdsTransactionFileInfoId);
	}

	@Override
	public Long getTDSTransactionUpdateStatusCount(Long employeeId, Long financialYearId) {
		Long otherIncomeCount = otherIncomeRepository.getOtherIncomeUpdateStatusCount(employeeId, financialYearId);
		Long transactionCount = tdsTransactionRepository.getTDSTransactionUpdateStatusCount(employeeId,financialYearId);
		Long pIncomeCount=previousEmployerIncomeRepository.getTDSTransactionUpdateStatusCount(employeeId, financialYearId);
		Long count = otherIncomeCount + transactionCount + pIncomeCount;
		
		System.out.println("TDSTransactionUpdateStatusCount "+count);
		return count;
	}

}
