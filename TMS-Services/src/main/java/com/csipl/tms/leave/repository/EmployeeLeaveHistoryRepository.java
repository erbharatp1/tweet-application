package com.csipl.tms.leave.repository;

import org.springframework.data.repository.CrudRepository;

import com.csipl.tms.model.leave.EmployeeLeaveHistory;



public interface EmployeeLeaveHistoryRepository extends CrudRepository<EmployeeLeaveHistory, Long>  {

}
