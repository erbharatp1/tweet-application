package com.csipl.hrms.service.payroll.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.csipl.hrms.common.constant.StatusMessage;
import com.csipl.hrms.model.payroll.TdsTransactionFileInfo;

public interface TdsTransactionFileInfoRepository extends CrudRepository<TdsTransactionFileInfo, Long>{
	@Query("from TdsTransactionFileInfo where tdsTransactionId=?1 and activeStatus='"+StatusMessage.ACTIVE_CODE+"'")
    public List<TdsTransactionFileInfo> getTdsTransactionFileInfoList(Long tdsTransactionId);
	
	
	@Modifying
	@Query(value = "UPDATE TdsTransactionFileInfo SET activeStatus='"+StatusMessage.DEACTIVE_CODE+"' WHERE tdsTransactionFileInfoId=?1 ", nativeQuery = true)
	public int deleteTdsTransactionFileInfo(Long tdsHouseRentFileInfoId);
}
