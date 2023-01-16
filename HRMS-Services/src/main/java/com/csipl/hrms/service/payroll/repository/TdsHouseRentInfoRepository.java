package com.csipl.hrms.service.payroll.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.csipl.hrms.common.constant.StatusMessage;
import com.csipl.hrms.model.payroll.TdsHouseRentInfo;

@Repository
public interface TdsHouseRentInfoRepository extends CrudRepository<TdsHouseRentInfo, Long> {

	@Query("from TdsHouseRentInfo hr where hr.tdsTransaction.tdsTransactionId =?1 and activeStatus='"+StatusMessage.ACTIVE_CODE+"'")
	public List<TdsHouseRentInfo> findAllHouseRentInfo(Long tdsTransactionId);

	@Modifying
	@Query(value = "UPDATE TdsHouseRentFileInfo SET activeStatus='"+StatusMessage.DEACTIVE_CODE+"' WHERE tdsHouseRentFileInfoId=?1 ", nativeQuery = true)
	public int deleteHouseRentInfoFile(Long tdsHouseRentFileInfoId);

	
	@Modifying
	@Query(value = "UPDATE TdsHouseRentInfo SET activeStatus='"+StatusMessage.DEACTIVE_CODE+"' WHERE tdsHouseRentInfoId=?1 ", nativeQuery = true)
	public int deleteTdsHouseRentInfo(Long tdsHouseRentInfoId);

	
}
