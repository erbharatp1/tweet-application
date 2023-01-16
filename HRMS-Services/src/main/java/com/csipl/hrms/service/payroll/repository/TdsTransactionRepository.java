package com.csipl.hrms.service.payroll.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.csipl.hrms.common.constant.StatusMessage;
import com.csipl.hrms.model.payroll.TdsTransaction;

public interface TdsTransactionRepository extends CrudRepository<TdsTransaction, Long>{

//String queryForTdsTransaction="select td.tdsGroupId,td.tdsSectionId,td.tdsSectionName,td.tdsDescription,tg.tdsGroupName,\r\n" + 
//		"tt.investmentAmount,tt.tdsTransactionId,tt.approveStatus, tt.userId, tt.dateCreated, td.maxLimit,tt.employeeId,\r\n"+
//		"tt.status, tt.financialYear, tt.city from TdsSection td join TdsGroup tg on tg.tdsGroupId=td.tdsGroupId and\r\n" + 
//		"(tg.effectiveEndDate is null or tg.effectiveEndDate>CURRENT_DATE) and\r\n" + 
//		"(tg.effectiveStartDate is NOT null and tg.effectiveStartDate<=CURRENT_DATE )\r\n" + 
//		"left join TdsTransaction tt on tt.tdsSectionId=td.tdsSectionId  \r\n" + 
//		"and tt.employeeId=?1 and tt.financialYear=?2 ORDER by td.tdsSectionName";

//String queryForTdsTransaction="select tss.tdsGroupId,tss.tdsSectionId,tss.tdsSectionName, tss.maxLimit,tgm.tdsGroupName, tt.investmentAmount,"
//		+ "tt.tdsTransactionId,tt.approveStatus, tt.userId, tt.dateCreated,tt.employeeId, tt.status, tt.financialYear,"
//		+ "tt.city, tgs.tdsGroupMasterId,tgs.maxLimit as tdsGroupmaxLimit, tt.remarks, tt.landlordName, tt.landlordPAN "
//		+ "from TdsSectionSetup tss join TdsGroupSetup tgs on tgs.tdsGroupId=tss.tdsGroupId "
//		+ "LEFT join TdsTransaction tt on tt.tdsSectionId=tss.tdsSectionId  and tt.employeeId=?1 and  tt.financialYear=?2 "
//		+ "join TdsGroupMaster tgm on  tgm.tdsGroupMasterId=tgs.tdsGroupMasterId";	
	
	
	String queryForTdsTransaction="select tss.tdsGroupId,tss.tdsSectionId,tss.tdsSectionName, tss.maxLimit,tgm.tdsGroupName,"
			+ " tt.investmentAmount, tt.tdsTransactionId,tt.approveStatus, tt.userId, tt.dateCreated,tt.employeeId, tt.status,"
			+ " tt.financialYearId, tt.city, tgs.tdsGroupMasterId,tgs.maxLimit as tdsGroupmaxLimit, tt.remarks "
			+ " from TdsSectionSetup tss join TdsGroupSetup tgs on tgs.tdsGroupId=tss.tdsGroupId "
			+ " and tss.activeStatus='"+StatusMessage.ACTIVE_CODE+"'"
			+ " LEFT join TdsTransaction tt on tt.tdsSectionId=tss.tdsSectionId  and tt.employeeId=?1 and tt.financialYearId=?2 "
			+ " join TdsGroupMaster tgm on  tgm.tdsGroupMasterId=tgs.tdsGroupMasterId ";
	
	
	String queryForTdsTransaction1="select tss.tdsGroupId,tss.tdsSectionId,tss.tdsSectionName, tss.maxLimit,tgm.tdsGroupName,"
			+ " tt.investmentAmount, tt.tdsTransactionId,tt.approveStatus, tt.userId, tt.dateCreated,tt.employeeId, tt.status,"
			+ " tt.financialYearId, tt.city, tgs.tdsGroupMasterId,tgs.maxLimit as tdsGroupmaxLimit, tt.remarks "
			+ " from TdsSectionSetup tss join TdsGroupSetup tgs on tgs.tdsGroupId=tss.tdsGroupId "
			+ " and tss.activeStatus='"+StatusMessage.ACTIVE_CODE+"'  and tgs.activeStatus='"+StatusMessage.ACTIVE_CODE+"'"
			+ " LEFT join TdsTransaction tt on tt.tdsSectionId=tss.tdsSectionId  and tt.employeeId=?1 and tt.financialYearId=?2 "
			+ " join TdsGroupMaster tgm on  tgm.tdsGroupMasterId=tgs.tdsGroupMasterId ";
	
	
	/*String queryForTdsTransaction="select tss.tdsGroupId,tss.tdsSectionId,tss.tdsSectionName, tss.maxLimit,tgm.tdsGroupName,"
			+ " tt.investmentAmount, tt.tdsTransactionId,tt.approveStatus, tt.userId, tt.dateCreated,tt.employeeId, tt.status,"
			+ " tt.financialYear, tt.city, tgs.tdsGroupMasterId,tgs.maxLimit as tdsGroupmaxLimit, tt.remarks  , ttf.tdsTransactionFileInfoId,"
			+ "ttf.fileName, ttf.originalFilename, ttf.filePath"
			+ " from TdsSectionSetup tss join TdsGroupSetup tgs on tgs.tdsGroupId=tss.tdsGroupId and tss.activeStatus='"+StatusMessage.ACTIVE_CODE+"'"
			+ " LEFT join TdsTransaction tt on tt.tdsSectionId=tss.tdsSectionId  and tt.employeeId=?1 and tt.financialYear=?2 "
			+ "	LEFT join TdsTransactionFileInfo ttf on tt.tdsTransactionId = ttf.tdsTransactionId and ttf.activeStatus ='"+StatusMessage.ACTIVE_CODE+"'"
			+ " join TdsGroupMaster tgm on  tgm.tdsGroupMasterId=tgs.tdsGroupMasterId ";*/
	
	
	
	
String queryForTdsSummary="select ts.tdsGroupId, ts.tdsSectionId, ts.tdsSectionName, ts.tdsDescription, ts.maxLimit, tt.financialYearId,\r\n"
		+ "tt.investmentAmount, tt.dateCreated, tt.status, tt.approvedAmount from TdsSection ts JOIN TdsTransaction tt on \r\n"
		+ "ts.tdsSectionId=tt.tdsSectionId where tt.employeeId=?1 and tt.financialYearId=?2 ORDER BY ts.tdsSectionName ASC";

String queryForStatusUPDATE = "UPDATE TdsTransaction SET status='Declared' WHERE employeeId=?1 and financialYearId=?2";
			
	@Query(value=queryForTdsTransaction1, nativeQuery=true)
    public List<Object[]> getTdsTransactionObjectList(Long employeeId, Long financialYearId);
    
    @Query(value=queryForTdsSummary, nativeQuery=true)
    public List<Object[]> getTdsSummaryObjectList(Long employeeId, Long financialYearId);
    
    @Query("from TdsTransaction tt where employeeId=?1 and financialYear.financialYearId=?2")
    public List<TdsTransaction> getTdsTransactionList(Long employeeId, Long financialYearId);
    
    @Query("SELECT SUM(investmentAmount) from TdsTransaction  where employeeId=?1 and financialYearId=?2")
    public BigDecimal getTotalInvestment(Long employeeId, Long financialYearId);
    
    @Modifying
    @Query(value=queryForStatusUPDATE, nativeQuery=true)
    public void updateStatus( Long employeeId,Long financialYearId);

    
    @Query("SELECT COUNT(tdsTransactionUpdateStatus) from TdsTransaction  where employeeId=?1 and financialYearId=?2  and tdsTransactionUpdateStatus='AC' ")
	public Long getTDSTransactionUpdateStatusCount(Long employeeId,  Long financialYearId);
}
