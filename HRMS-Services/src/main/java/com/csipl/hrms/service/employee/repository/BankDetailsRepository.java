package com.csipl.hrms.service.employee.repository;


import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import com.csipl.hrms.model.employee.EmployeeBank;


public interface BankDetailsRepository extends CrudRepository<EmployeeBank, Long>{

	@Query(" from EmployeeBank where employeeId=?1 AND activeStatus='AC' ORDER BY  employeeBankId  DESC") //and activeStatus='AC'
    public List<EmployeeBank> findAllBankDetails(Long employeeId);
	@Query(" from EmployeeBank where employeeId=?1  AND accountType ='SA' ORDER BY  employeeBankId  DESC") //and activeStatus='AC'
    public List<EmployeeBank> findBankDetails(Long employeeId);
	 String bankCheck= "SELECT COUNT(*) FROM EmployeeBank eb join Employee e on eb.employeeId= e.employeeId  where  e.activeStatus='AC' and eb.accountNumber=?1";
	 @Query(value = bankCheck, nativeQuery = true)
	 public int bankAccountNumber(String bankAccountNumber);
	 
	 @Query("FROM EmployeeBank WHERE employeeId =?1") 
		public EmployeeBank bankDetail(Long employeeId);
	 
	 String bankCheckExist= "SELECT COUNT(*) FROM EmployeeBank eb join Employee e on eb.employeeId= e.employeeId  where e.employeeId !=?1 and e.activeStatus='AC' and eb.accountNumber=?2";
	 @Query(value = bankCheckExist, nativeQuery = true)
	 public int bankAccountNumberCheckExist(Long employeeId , String bankAccountNumber);
	 
}
