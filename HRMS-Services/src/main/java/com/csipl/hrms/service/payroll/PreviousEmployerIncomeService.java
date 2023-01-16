package com.csipl.hrms.service.payroll;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.csipl.hrms.model.payroll.PreviousEmployerIncomeFile;
import com.csipl.hrms.model.payroll.PreviousEmployerIncomeTds;

public interface PreviousEmployerIncomeService {

//	public void  save(List<PreviousEmployerIncomeTds> previousEmployerIncomeTdsList, Long companyId);
	public List<Object[]> getPreviousEmployerIncomeObjectList(Long employeeId,Long financialYearId);
	public List<PreviousEmployerIncomeTds> getPreviousEmployerIncomeList(Long employeeId,Long companyId);
	
	
	public void  save(List<PreviousEmployerIncomeTds> previousEmployerIncomeTdsList, Long employeeId,Long companyId,List<MultipartFile> fileInfo,Long financialYearId);
	public int deletePreviousEmployerIncomeFileInfo(Long previousEmployerIncomeFileId);
	public List<PreviousEmployerIncomeFile> getPreviousEmployerIncomeFileList(Long empId, Long financialYrId);

}
