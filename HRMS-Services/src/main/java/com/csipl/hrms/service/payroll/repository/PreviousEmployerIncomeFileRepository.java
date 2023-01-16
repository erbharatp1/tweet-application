package com.csipl.hrms.service.payroll.repository;

import com.csipl.hrms.common.constant.StatusMessage;
import com.csipl.hrms.model.payroll.PreviousEmployerIncomeFile;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface PreviousEmployerIncomeFileRepository  extends CrudRepository<PreviousEmployerIncomeFile, Long>{
	
	@Query("from PreviousEmployerIncomeFile where employeeId=?1 and financialYearId=?2 and activeStatus='"+StatusMessage.ACTIVE_CODE+"'")
	public List<PreviousEmployerIncomeFile> findAllPreviousEmployerIncomeFile(Long empId, Long financialYrId);

	
	@Modifying
	@Query(value = "UPDATE PreviousEmployerIncomeFile SET activeStatus='"+StatusMessage.DEACTIVE_CODE+"' WHERE PreviousEmployerIncomeFileId=?1 ", nativeQuery = true)
	public int deletePreviousEmployerIncomeFileInfo(Long previousEmployerIncomeFileId);

}
