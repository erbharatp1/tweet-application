package com.csipl.hrms.service.payroll;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.csipl.hrms.model.payroll.TdsTransaction;
import com.csipl.hrms.model.payroll.TdsTransactionFileInfo;
import com.csipl.hrms.model.payrollprocess.FinancialYear;

public interface TdsTransactionService {

	public List<Object[]> getTdsTransactionObjectList(Long employeeId,Long financialYearId);
	public List<Object[]> getTdsSummaryObjectList(Long employeeId,Long companyId);
	public void save(List<TdsTransaction> tdsTransactionList,Long employeeId, Long companyId, List<MultipartFile> fileInfo);
	public List<TdsTransaction> getTdsTrasactionListforApproval(Long empId,FinancialYear financialYear);
	public List<TdsTransaction> getTdsTransactionList(Long employeeId,Long companyId);
	public BigDecimal getTotalInvestment(Long employeeId,Long companyId);
	public List<TdsTransactionFileInfo> getTdsTransactionFileInfoList(Long tdsTransactionId);
	public int deleteTdsTransactionFileInfo(Long tdsTransactionFileInfoId);
	public Long getTDSTransactionUpdateStatusCount(Long employeeId,  Long financialYearId);
}
