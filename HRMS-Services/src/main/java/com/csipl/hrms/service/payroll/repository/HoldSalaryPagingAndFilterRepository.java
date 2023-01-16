package com.csipl.hrms.service.payroll.repository;

import java.util.List;

import com.csipl.hrms.dto.payroll.HoldSalaryDTO;

public interface HoldSalaryPagingAndFilterRepository {
	
	public List<Object[]> holdSalarySearch(HoldSalaryDTO holdSalarySearchDto );

}
