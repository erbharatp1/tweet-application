package com.csipl.tms.attendanceregularizationrequest.service;

import java.util.Date;
import java.util.List;

import com.csipl.hrms.common.util.ErrorHandling;
import com.csipl.tms.dto.attendanceregularizationrequest.PunchTimeDetailDTO;
import com.csipl.tms.model.attendanceregularizationrequest.PunchTimeDetail;

public interface PunchTimeDetailsService {
	public void save(PunchTimeDetail punchDetails,PunchTimeDetailDTO punchTimeDetailsDto) throws ErrorHandling;
	public List<PunchTimeDetail> getAllPunchtime(String employeeCode,Long companyId,String InOut);
	public List<Object[]>  getEmpAttendaceList(Long companyid);
	public List<Object[]>  getEmpAttendaceListViaDate(Long companyid ,Date date);
	public List<Object[]>  getEmpAttendaceListViaDate(Long companyid ,String date);
	public List<Object[]> findMaxTime(String employeeCode,Long companyId);
	
	public void saveAll(List<PunchTimeDetail> punchTimeDetailList);
	public List<Object[]> restrictLocationPremise(Long employeeId);
}
