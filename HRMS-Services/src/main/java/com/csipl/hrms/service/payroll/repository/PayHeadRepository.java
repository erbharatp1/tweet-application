package com.csipl.hrms.service.payroll.repository;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import com.csipl.hrms.common.constant.StatusMessage;
import com.csipl.hrms.model.payroll.PayHead;
public interface PayHeadRepository extends CrudRepository<PayHead, Long> {

	@Query(" from PayHead where earningDeduction=?1 AND companyId=?2 AND payHeadFlag='N'  and activeStatus='AC' ORDER BY  payHeadId ASC  ")

    public List<PayHead> findAllPayHead(String earningDeduction,Long  companyId);
	
	@Query(" from PayHead where earningDeduction=?1 AND companyId=?2 AND payHeadFlag='N'  and activeStatus='AC' AND headType='One Time' ORDER BY  payHeadId ASC  ")
    public List<PayHead> findAllOneTimePayHead(String earningDeduction,Long  companyId);
	
	@Query(" from PayHead where companyId=?1 and activeStatus='AC' ORDER BY  payHeadId  ASC  ")
	public List<PayHead> findActivePayHeadOfCompany(long companyId);
	
	@Query(" from PayHead where companyId=?1  ORDER BY  payHeadId  ASC  ")
	public List<PayHead> findAllPayHeadOfCompany(long companyId);

	@Query(" from PayHead where earningDeduction='EA' AND companyId=?1 AND payHeadFlag='Y'  and activeStatus='AC' ORDER BY  payHeadId ASC  ")
	public List<PayHead> findAllEarnigPaystructurePayHeads(Long companyId);

	@Query("SELECT COUNT(1) from PayHead where priority=?1 and activeStatus='"+StatusMessage.ACTIVE_CODE+"'")
	public int getPriority(Long priority);

	@Query("SELECT COUNT(1) from PayHead where priority=?1 AND activeStatus=?2 AND payHeadId NOT IN (?3)")
	public int getPriorityByStatus(Long priority, String activeStatus, Long payHeadId);

	@Query(value="SELECT MAX(sequenceId) FROM  PayHeads WHERE activeStatus='AC' and earningDeduction='EA' and companyId=?1", nativeQuery=true)
	public  Long getEarningSquenceId(Long companyId);
	
	@Query(value="SELECT MAX(sequenceId) FROM  PayHeads WHERE activeStatus='AC' and earningDeduction='DE' and companyId=?1", nativeQuery=true)
	public  Long getDeductionSquenceId(Long companyId);
	
	@Query(value="SELECT MAX(sequenceId) FROM  PayHeads WHERE activeStatus='AC' and companyId=?1", nativeQuery=true)
	public  Long getMaxSquenceId(Long companyId);
	
	
	 


}
