package com.csipl.tms.attendanceregularizationrequest.service;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.csipl.hrms.common.util.ErrorHandling;
import com.csipl.tms.attendanceregularizationrequest.repository.PunchTimeDetailsRepository;
import com.csipl.tms.attendancescheme.repository.AttendanceSchemeRepository;
import com.csipl.tms.dto.attendanceregularizationrequest.PunchTimeDetailDTO;
import com.csipl.tms.dto.attendancescheme.AttendanceSchemeDTO;
import com.csipl.tms.model.attendanceregularizationrequest.PunchTimeDetail;

@Service("punchTimeDetailsService")
public class PunchTimeDetailsServiceImpl  implements PunchTimeDetailsService{

	@Autowired
	PunchTimeDetailsRepository punchTimeDetailsRepository;
	
	@Autowired
	AttendanceSchemeRepository attendanceSchemeRepository;
	
	@Override
	public void save(PunchTimeDetail punchDetails,PunchTimeDetailDTO punchTimeDetailsDto) throws ErrorHandling{
		
		Long employeeId = punchTimeDetailsDto.getEmployeeId();
		String attendanceFlag = punchDetails.getFlag();
		
		if(attendanceFlag !=null && employeeId != null) {
		if(attendanceFlag.equalsIgnoreCase("Mobile")) {
			AttendanceSchemeDTO attendanceSchemeDTOForMob =	allowedMobileSettingOfEmployee(employeeId);
			if(attendanceSchemeDTOForMob != null) {
					throw new ErrorHandling("You are not allowed to mark attendance through mobile app, please contact to Administrator");
			}
		}else if(attendanceFlag.equalsIgnoreCase("Web")) {
			AttendanceSchemeDTO attendanceSchemeDTOForWeb =	allowedWebSettingOfEmployee(employeeId);
			if(attendanceSchemeDTOForWeb != null) {
				throw new ErrorHandling("You are not allowed to Mark attendance through Web, Please contact to Administrator");
		}
		}
		}
		
		punchTimeDetailsRepository.save(punchDetails);
			
	}
	@Override
	public List<PunchTimeDetail> getAllPunchtime( String employeeCode ,Long companyId,String InOut) {
		Date date = new Date();
		return punchTimeDetailsRepository.findAllPunchTime(employeeCode,companyId,InOut,date);
	}
	@Override
	public List<Object[]> getEmpAttendaceList(Long companyId) {
		Date date = new Date();
		System.out.println("date..."+date);
		SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");
		System.out.println(dateformat.format(date));
		
		return punchTimeDetailsRepository.getEmpAttendaceList(companyId,dateformat.format(date));//
		//return punchTimeDetailsRepository.getEmpAttendaceList(companyId,"2020-01-31");//
	}
	
	@Override
	public List<Object[]> getEmpAttendaceListViaDate(Long companyId ,Date date) {
		SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");
		
		return punchTimeDetailsRepository.getEmpAttendaceList(companyId,dateformat.format(date));
		
	}
	
	@Override
	public List<Object[]> findMaxTime(String employeeCode, Long companyId) {
		// TODO Auto-generated method stub
		return punchTimeDetailsRepository.findMaxTime(employeeCode, companyId);
	}

//		return punchTimeDetailsRepository.findMaxTime(employeeCode, companyId);


	@Override
	public void saveAll(List<PunchTimeDetail> punchTimeDetailList) {
		punchTimeDetailsRepository.save(punchTimeDetailList);
		
	}
	
	private AttendanceSchemeDTO allowedMobileSettingOfEmployee(Long employeeId) {
		List<Object[]>  objs =  attendanceSchemeRepository.allowMobileSettingOfEmployee(employeeId);
		
		if(objs == null || objs.size() == 0) {
			return null;
		}
		for (Object[] obj : objs) {
		AttendanceSchemeDTO attendanceSchemeDTO = new AttendanceSchemeDTO();
		attendanceSchemeDTO.setAttendanceSchemeId(Long.valueOf(obj[0].toString()));
		attendanceSchemeDTO.setEmployeeId(Long.valueOf(obj[2].toString()));
		attendanceSchemeDTO.setUserId(Long.valueOf(obj[3].toString()));
		attendanceSchemeDTO.setAschemeStatus(obj[4].toString());
		attendanceSchemeDTO.setArDays(Long.valueOf(obj[5].toString()));
		attendanceSchemeDTO.setAttendanceTypeId(Long.valueOf(obj[6].toString()));
		attendanceSchemeDTO.setTypeCode(obj[7].toString());
		attendanceSchemeDTO.setaTypeTransactionstatus(obj[8].toString());
		return attendanceSchemeDTO;
		}
		return null;
	}
	
	private AttendanceSchemeDTO allowedWebSettingOfEmployee(Long employeeId) {
		List<Object[]>  objs =  attendanceSchemeRepository.allowWebSettingOfEmployee(employeeId);
		
		if(objs == null || objs.size() == 0) {
			return null;
		}
		for (Object[] obj : objs) {
		AttendanceSchemeDTO attendanceSchemeDTO = new AttendanceSchemeDTO();
		attendanceSchemeDTO.setAttendanceSchemeId(Long.valueOf(obj[0].toString()));
		attendanceSchemeDTO.setEmployeeId(Long.valueOf(obj[2].toString()));
		attendanceSchemeDTO.setUserId(Long.valueOf(obj[3].toString()));
		attendanceSchemeDTO.setAschemeStatus(obj[4].toString());
		attendanceSchemeDTO.setArDays(Long.valueOf(obj[5].toString()));
		attendanceSchemeDTO.setAttendanceTypeId(Long.valueOf(obj[6].toString()));
		attendanceSchemeDTO.setTypeCode(obj[7].toString());
		attendanceSchemeDTO.setaTypeTransactionstatus(obj[8].toString());
		return attendanceSchemeDTO;
		}
		return null;
	}
	@Override
	public List<Object[]> restrictLocationPremise(Long employeeId) {
		return attendanceSchemeRepository.restrictLocationPremise(employeeId);
	}

	@Override
	public List<Object[]> getEmpAttendaceListViaDate(Long companyId ,String date) {
		return punchTimeDetailsRepository.getEmpAttendaceList(companyId,date);
		
	}
	
}
