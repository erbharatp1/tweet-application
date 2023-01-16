package com.csipl.hrms.service.payroll.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.csipl.hrms.model.employee.ExpenseType;



@Repository
public interface ExpenseTypeRepository  extends CrudRepository<ExpenseType,Long>{
	
	@Query("from ExpenseType where companyId=?1 ")
	List<ExpenseType> findExpenseTypeList(Long companyId);

	@Query("from ExpenseType where expenseTypeId=?1 ")
	ExpenseType findExpenseTypeById(Long expenseTypeId);

	 
}
