package com.csipl.tms.bulkupload.service;

import java.util.List;
import java.util.Map;

import com.csipl.hrms.common.exception.PayRollProcessException;
import com.csipl.hrms.dto.employee.EmployeeDTO;
import com.csipl.tms.model.leave.EmployeeOpeningLeaveMaster;

public interface BulkUploadLeaveService {

	public void saveEmployeeLeaveOpeningBalance(List<EmployeeOpeningLeaveMaster> employeeLeaveOpeningList,
			EmployeeDTO employeeDto, Map<Integer, StringBuilder> errorMap) throws PayRollProcessException;

	public void saveEmployeeLeaveCarryForward(List<EmployeeOpeningLeaveMaster> employeeLeaveOpeningList,
			EmployeeDTO employeeDto, Map<Integer, StringBuilder> errorMap) throws PayRollProcessException;

}
