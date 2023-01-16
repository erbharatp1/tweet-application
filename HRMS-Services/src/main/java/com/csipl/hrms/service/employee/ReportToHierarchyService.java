package com.csipl.hrms.service.employee;

import java.util.List;

import com.csipl.hrms.dto.employee.EmployeeNode;

public interface ReportToHierarchyService {
	public  List<Object[]> getTeamHirarchy(Long companyId);
}
