package com.csipl.hrms.service.employee.repository;

import java.math.BigDecimal;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.csipl.hrms.model.employee.MasterBook;

@Repository
public interface MasterBookRepository extends CrudRepository<MasterBook, Long> {
 

	@Query(" from MasterBook where companyId=?1 And bookCode=?2")
	public MasterBook findMasterBook(Long companyId, String bookCode);

	@Query(" from MasterBook where companyId=?1 And bookCode=?2 And bookType=?3")
	public MasterBook getMasterBook(Long companyId, String bookCode, String bookType);

	@Modifying
	@Transactional
	@Query("UPDATE MasterBook SET prefixBook=?1, startFrom=?2, lastNo=?3 where bookCode='EMPNO' and companyId=?4 ")
	public void updateEmployeeCodePrefix(String prefixBook, BigDecimal startFrom, BigDecimal lastNo, Long companyId);

}
