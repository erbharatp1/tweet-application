package com.csipl.hrms.service.payroll.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.csipl.hrms.model.payroll.LabourWelfareFund;
import com.csipl.hrms.model.payroll.LabourWelfareFundInfo;

@Repository
public interface LabourWalfareFundRepository extends CrudRepository<LabourWelfareFund, Long> {

	public static final String UPDATE_BY_ID = "Update LabourWelfareFundInfo pt SET pt.activeStatus=:activeStatus WHERE pt.labourWelfareFundInfoId=:labourWelfareFundInfoId";

	@Query(" from LabourWelfareFund  where   companyId=?1 AND activeStatus= 'AC'")
	public List<LabourWelfareFund> findAllLabourWelfareFund(Long companyId);

	@Query(" from LabourWelfareFund pt where labourWelfareFundHeadId=?1 and companyId=?2 ")
	public LabourWelfareFund findLabourWelfareFund(Long labourWelfareFundHeadId, Long companyId);

	@Modifying
	@Query(UPDATE_BY_ID)
	public void updateByStatus(@Param("labourWelfareFundInfoId") Long labourWelfareFundInfoId,
			@Param("activeStatus") String activeStatus);
	
    @Query("from LabourWelfareFund lwf where lwf.state.stateId=?1 \r\n" + 
    		"AND ((lwf.effectiveStartDate<=?3 AND ?3<=lwf.effectiveEndDate) OR (lwf.effectiveStartDate <=?3 AND  lwf.effectiveEndDate IS NULL )) and companyId=?2 AND activeStatus = 'AC'")
	public List<LabourWelfareFund> findLabourWelfareFundEmployee( long stateId, long companyId ,Date date);

    
    public static final String applicableStatus = "Update LabourWelfareFund lwf SET lwf.activeStatus=:activeStatus , lwf.isApplicable='Y' WHERE lwf.activeStatus='AC' or lwf.isApplicable='Y' ";
    @Modifying
	@Query(applicableStatus)
	public void updateApplicableStatus(@Param("activeStatus") String activeStatus);

    @Query("SELECT COUNT(l.labourWelfareFundHeadId) from LabourWelfareFund l where l.isApplicable='Y' and companyId=?1")
	public int getActiveCount(Long companyId);
	 
    @Query(" from LabourWelfareFund lwf where lwf.companyId =?1  AND lwf.activeStatus!= 'AC' ORDER BY lwf.effectiveStartDate DESC ") 
	 public List<LabourWelfareFund> findAllLWFDescByDate(Long companyId);
	
	@Query(" from LabourWelfareFund lwf where lwf.companyId =?1 and lwf.state.stateId =?2 ORDER BY lwf.effectiveStartDate DESC ") 
	 public List<LabourWelfareFund> findAllLWFStateIdDescByDate(Long companyId ,Long stateId);
	
	@Query(value="select * from LabourWelfareFundInfo lwfi where lwfi.companyId =?1 and lwfi.labourWelfareFundId =?2 ORDER BY lwfi.effectiveStartDate DESC ",nativeQuery = true) 
	 public List<LabourWelfareFundInfo> findAllLWFLabourWelfareFundIdDescByDate(Long companyId ,Long labourWelfareFundId);
	
	@Query(" from LabourWelfareFund lwf where lwf.labourWelfareFundHeadId=?1 and lwf.companyId=?2 ORDER BY lwf.labourWelfareFundHeadId DESC ")
	public LabourWelfareFund lwfByHeadId(Long labourWelfareFundHeadId, Long companyId);
	  //labourWelfareFundId
// 	@Query(" from LabourWelfareFund profTax where profTax.state.stateId=?1 and companyId=?2 and  (effectiveEndDate is null or effectiveEndDate>?3) and (effectiveStartDate is NOT null and effectiveStartDate<=?3) ")
//    public List< LabourWelfareFund> findProfessionalTaxOfEmployee( long stateId, long companyId, Date date );
// 	
//	@Query(" from LabourWelfareFund pt where labourWelfareFundHeadId=?1 and companyId=?2 " )
//    public  LabourWelfareFund findProfessionalTax( Long labourWelfareFundHeadId ,Long companyId);
//	
//	
//	@Modifying
// 	@Query(UPDATE_BY_ID)
// 	public void updateByStatus(@Param("labourWelfareFundInfoId") Long labourWelfareFundInfoId, @Param("activeStatus") String activeStatus);

}
