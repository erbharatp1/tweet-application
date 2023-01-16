package com.csipl.hrms.service.payroll;

import java.io.File;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.support.DefaultMultipartHttpServletRequest;

import com.csipl.hrms.common.constant.StatusMessage;
import com.csipl.hrms.common.util.DateUtils;
import com.csipl.hrms.common.util.HrmsGlobalConstantUtil;
import com.csipl.hrms.model.candidate.CandidateIdProof;
import com.csipl.hrms.model.employee.MasterBook;
import com.csipl.hrms.model.payroll.OtherIncome;
import com.csipl.hrms.model.payrollprocess.FinancialYear;
import com.csipl.hrms.service.employee.repository.MasterBookRepository;
import com.csipl.hrms.service.organization.StorageService;
import com.csipl.hrms.service.payroll.repository.FinancialYearRepository;
import com.csipl.hrms.service.payroll.repository.OtherIncomeRepository;

@Service("otherIncomeService")
public class OtherIncomeServiceImpl implements OtherIncomeService {

	@Autowired
	OtherIncomeRepository otherIncomeRepository;
	
	@Autowired
	FinancialYearRepository financialYearRepository;
	
	@Autowired
	StorageService storageService;
	
	@Autowired
	private MasterBookRepository masterBookRepository;
	
	@Override
	public List<OtherIncome> save(List<OtherIncome> otherIncomeList, Long companyId, HttpServletRequest request) {
		
		MultipartHttpServletRequest multiPartRequest = new DefaultMultipartHttpServletRequest(request);

		multiPartRequest = (MultipartHttpServletRequest) request;
		multiPartRequest.getParameterMap();

		String bookCode = "EMPNO";
		//
		int index = 0;
		int count = 0;
		for (OtherIncome otherIncome : otherIncomeList) {
			Iterator<String> itr = multiPartRequest.getFileNames();
			System.out.println("----designDocument.getDesignName() :::;::" + otherIncome.getDocumentName());
			
			DateUtils dateUtils=new DateUtils();
			Date currentDate=dateUtils.getCurrentDate();
			FinancialYear financialYear=financialYearRepository.getFinancialYear(currentDate, companyId);
			
			otherIncome.setFinancialYear(financialYear);
			
			if (otherIncome.getDocumentName() != null && !("").equals(otherIncome.getDocumentName())) {
				System.out.println("----inner for loop ");
				int fileIndex = 0;
				while (itr.hasNext()) {
					MultipartFile mFile = multiPartRequest.getFile(itr.next());
					System.out.println("fileIndex  " + fileIndex);
					System.out.println("Index  " + index);
					if (fileIndex == index) {
						MasterBook masterBook = masterBookRepository.findMasterBook(1l, bookCode);
						BigDecimal lastNumberValue;
						lastNumberValue = masterBook.getLastNo();
						long longValue;
						longValue = lastNumberValue.longValue() + 1;
						BigDecimal newDecimalValue = new BigDecimal(longValue);
						String fileName = masterBook.getPrefixBook() + 'C' + newDecimalValue;
						String extension = FilenameUtils.getExtension(mFile.getOriginalFilename());
						fileName = fileName + "." + extension;
						// logger.info(" File with extension " + fileName);

//						String path = File.separator + "assets" + File.separator + "ProjectDoc";
						String path = storageService.createFilePath(HrmsGlobalConstantUtil.PROJECT_DOC);
						String dbPath = path + File.separator + fileName;
						
						storageService.store(mFile, path, fileName);
						masterBook.setLastNo(newDecimalValue);
						masterBookRepository.save(masterBook);

						System.out.println("FileName is " + mFile.getOriginalFilename());

						otherIncome.setOtherIncomeDoc(dbPath);
						otherIncome.setDocumentName(fileName);

						System.out.println("store  end ()");

					}
					fileIndex++;
					System.out.println(".while end ()");
				}

				index++;
			} 
			
			
		
		}
	 
		List<OtherIncome> otherIncomes=(List<OtherIncome>) otherIncomeRepository.save(otherIncomeList);
		return otherIncomes;
		
	 
		
//		DateUtils dateUtils=new DateUtils();
//		Date currentDate=dateUtils.getCurrentDate();
//		FinancialYear financialYear=financialYearRepository.getFinancialYear(currentDate, companyId);
//		otherIncomeList.forEach(otherIncome->{
//			otherIncome.setFinancialYear(financialYear);
//		});
//		List<OtherIncome> otherIncomes=(List<OtherIncome>) otherIncomeRepository.save(otherIncomeList);
//		return otherIncomes;
	}

	@Override
	public List<OtherIncome> findOtherIncomes(Long employeeId, Long companyId, FinancialYear financialYear) {
//		DateUtils dateUtils=new DateUtils();
//		Date currentDate=dateUtils.getCurrentDate();
//		FinancialYear financialYear=financialYearRepository.getFinancialYear(currentDate, companyId);
		return otherIncomeRepository.findAllOtherIncome(employeeId,financialYear.getFinancialYearId());
	}

	@Override
	public BigDecimal getTotalOtherIncome(Long employeeId, Long companyId) {
		DateUtils dateUtils=new DateUtils();
		Date currentDate=dateUtils.getCurrentDate();
		FinancialYear financialYear=financialYearRepository.getFinancialYear(currentDate, companyId);
		return otherIncomeRepository.findOtherIncomeSum(employeeId, financialYear.getFinancialYearId());
	}

	@Override
	@Transactional
	public int deleteOtherIncome(Long otherIncomeId) {
		return otherIncomeRepository.deleteOtherIncome(otherIncomeId, StatusMessage.DEACTIVE_CODE);
	}

}
