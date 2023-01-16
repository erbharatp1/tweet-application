package com.csipl.tms.attendanceregularizationrequest.adaptor;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.io.FilenameUtils;
import org.springframework.web.multipart.MultipartFile;

import com.csipl.hrms.common.util.GlobalConstantUtils;
import com.csipl.hrms.common.util.StorageUtil;
import com.csipl.tms.dto.attendancelog.AttendanceLogDTO;
import com.csipl.tms.dto.attendanceregularizationrequest.PunchTimeDetailDTO;
import com.csipl.tms.dto.attendanceregularizationrequest.SystemAttendanceDTO;
import com.csipl.tms.dto.latlonglocation.RestrictedLocationPremiseDTO;
import com.csipl.tms.model.attendancelog.AttendanceLog;
import com.csipl.tms.model.attendanceregularizationrequest.PunchTimeDetail;
import com.csipl.tms.service.Adaptor;

public class AttendanceAdaptor implements Adaptor<PunchTimeDetailDTO, PunchTimeDetail> {

	StorageUtil storageUtil = new StorageUtil();

	@Override
	public List<PunchTimeDetail> uiDtoToDatabaseModelList(List<PunchTimeDetailDTO> uiobj) {
		List<PunchTimeDetail> punchTimeDetailList = new ArrayList<PunchTimeDetail>();
		for (PunchTimeDetailDTO punchTimeDetailDTO : uiobj) {
			punchTimeDetailList.add(uiDtoToBioMetricpunchTimeDetailModel(punchTimeDetailDTO, 0l));
		}
		return punchTimeDetailList;
	}

	@Override
	public List<PunchTimeDetailDTO> databaseModelToUiDtoList(List<PunchTimeDetail> dbobj) {
		return null;
	}

	public PunchTimeDetail uiDtoTopunchTimeDetailModel(PunchTimeDetailDTO punchTimeDetailDto, Long count) {
		PunchTimeDetail punchTimeDetail = new PunchTimeDetail();
		Date date = new Date();
		// System.out.println("Date..." + date);

		punchTimeDetail.setDate(date);

		punchTimeDetail.setTime((new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime())));
		punchTimeDetail.setIn_out(punchTimeDetailDto.getIn_out());
		punchTimeDetail.setSNo(count + 1);
		punchTimeDetail.setTktNo(punchTimeDetailDto.getTktNo());
		punchTimeDetail.setLatitude(punchTimeDetailDto.getLatitude());
		punchTimeDetail.setLongitude(punchTimeDetailDto.getLongitude());
		punchTimeDetail.setAddress(punchTimeDetailDto.getAddress());
		if (punchTimeDetailDto.getFlag() != null)
			punchTimeDetail.setFlag(punchTimeDetailDto.getFlag());
		else
			punchTimeDetail.setFlag("Web");
		String tkt = punchTimeDetailDto.getTktNo();
		String[] arrOfStr = tkt.split("-");
		for (String string : arrOfStr) {
			// System.out.println(string);
		}

		punchTimeDetail.setCompanyId(punchTimeDetailDto.getCompanyId());
		punchTimeDetail.setHhMm(1l);
		return punchTimeDetail;
	}

	@Override
	public PunchTimeDetailDTO databaseModelToUiDto(PunchTimeDetail punchTimeDetail) {
		PunchTimeDetailDTO punchTimeDetailDTO = new PunchTimeDetailDTO();
		punchTimeDetailDTO.setCompanyId(punchTimeDetail.getCompanyId());

		punchTimeDetailDTO.setDate(punchTimeDetail.getDate());

		punchTimeDetailDTO.setIn_out(punchTimeDetail.getIn_out());

		// punchTimeDetailDTO.setCompanyId(punchTimeDetail.getCompanyId());
		//
		// punchTimeDetailDTO.setCompanyId(punchTimeDetail.getCompanyId());

		return null;
	}

	@Override
	public PunchTimeDetail uiDtoToDatabaseModel(PunchTimeDetailDTO uiobj) {
		return null;
	}

	public List<SystemAttendanceDTO> objectListToUImodel(List<Object[]> attendancObjList) {
		List<SystemAttendanceDTO> punchTimeDtoList = new ArrayList<SystemAttendanceDTO>();
		for (Object[] attendanceObj : attendancObjList) {

			SystemAttendanceDTO systemAttendanceDto = new SystemAttendanceDTO();
			Long minSno = attendanceObj[0] != null ? Long.parseLong(attendanceObj[0].toString()) : null;
			Long maxSno = attendanceObj[1] != null ? Long.parseLong(attendanceObj[1].toString()) : null;
			String minTime = attendanceObj[2] != null ? (String) attendanceObj[2] : "";
			String maxTime = attendanceObj[3] != null ? (String) attendanceObj[3] : "";
			String tktNo = attendanceObj[4] != null ? (String) attendanceObj[4] : null;

			Date date = attendanceObj[5] != null ? (Date) attendanceObj[5] : null;
			Long companyId = attendanceObj[6] != null ? Long.parseLong(attendanceObj[6].toString()) : null;

			systemAttendanceDto.setDate(date);

//			systemAttendanceDto.setIntime(minTime);
//			systemAttendanceDto.setOuttime(maxTime);

			String time1 = null;
			SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
			try {
				Date date1 = sdf.parse(minTime);

				SimpleDateFormat sdf2 = new SimpleDateFormat("hh:mm:ss aa");

				time1 = sdf2.format(date1);

			} catch (ParseException e) {
				// e.printStackTrace();
			}
			systemAttendanceDto.setIntime(time1);

			String time2 = null;
			SimpleDateFormat sd = new SimpleDateFormat("HH:mm:ss");
			try {
				Date date2 = sd.parse(maxTime);

				SimpleDateFormat sd2 = new SimpleDateFormat("hh:mm:ss aa");

				time2 = sd2.format(date2);

			} catch (ParseException e) {
				// e.printStackTrace();
			}
			systemAttendanceDto.setOuttime(time2);

			systemAttendanceDto.setMaxSNo(maxSno);
			systemAttendanceDto.setMinSNo(minSno);
			systemAttendanceDto.setTktNo(tktNo);
			systemAttendanceDto.setCompanyId(companyId);
			punchTimeDtoList.add(systemAttendanceDto);
		}

		return punchTimeDtoList;
	}

	public List<AttendanceLogDTO> objArraytoDtoList(List<Object[]> attendanceLogList) {
		List<AttendanceLogDTO> attendanceDtoList = new ArrayList<AttendanceLogDTO>();
		for (Object[] attendanceLog : attendanceLogList) {
			AttendanceLogDTO attendanceLogDto = new AttendanceLogDTO();
			Date attendanceDate = attendanceLog[1] != null ? (Date) (attendanceLog[1]) : null;
			Long employeeId = attendanceLog[2] != null ? Long.parseLong(attendanceLog[2].toString()) : null;
			String employeeCode = attendanceLog[3] != null ? (String) attendanceLog[3] : null;
			String inTime = attendanceLog[4] != null ? (String) attendanceLog[4] : "";
			String outTime = attendanceLog[5] != null ? (String) attendanceLog[5] : "";
			Long inDeviceId = attendanceLog[6] != null ? Long.parseLong(attendanceLog[6].toString()) : null;
			Long outDeviceId = attendanceLog[7] != null ? Long.parseLong(attendanceLog[7].toString()) : null;
			// Long companyId = attendanceLog[8] != null ?
			// Long.parseLong(attendanceLog[8].toString()) : null;
			String mode = attendanceLog[9] != null ? (String) attendanceLog[9] : null;
			attendanceLogDto.setAttendanceDate(attendanceDate);
			attendanceLogDto.setEmployeeId(employeeId);
			attendanceLogDto.setEmployeeCode(employeeCode);

//			attendanceLogDto.setInTime(inTime);
//			attendanceLogDto.setOutTime(outTime);

			String time1 = null;
			SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
			try {
				Date date1 = sdf.parse(inTime);

				SimpleDateFormat sdf2 = new SimpleDateFormat("hh:mm:ss aa");

				time1 = sdf2.format(date1);

			} catch (ParseException e) {
				// e.printStackTrace();
			}
			attendanceLogDto.setInTime(time1);

			String time2 = null;
			SimpleDateFormat sd = new SimpleDateFormat("HH:mm:ss");
			try {
				Date date2 = sd.parse(outTime);

				SimpleDateFormat sd2 = new SimpleDateFormat("hh:mm:ss aa");

				time2 = sd2.format(date2);

			} catch (ParseException e) {
				// e.printStackTrace();
			}
			attendanceLogDto.setOutTime(time2);

			attendanceLogDto.setInDeviceId(inDeviceId);
			attendanceLogDto.setOutDeviceId(outDeviceId);
			attendanceLogDto.setMode(mode);
			// attendanceLogDto.setCompanyId(companyId);
			attendanceDtoList.add(attendanceLogDto);
		}
		return attendanceDtoList;
	}

//SELECT emp.employeeId,emp.employeeCode,concat(concat(emp.firstName,' '),emp.lastName) as empname, al.attendanceDate,al.inTime,al.outTime,al.location,al.mode,al.status,al.totalTime  from Employee emp LEFT join AttendanceLogs al ON al.employeeId= emp.employeeId WHERE emp.
	public List<AttendanceLog> attendanceObjArraytoDtoList(List<Object[]> attendanceLogList) {
		List<AttendanceLog> attendanceDtoList = new ArrayList<AttendanceLog>();
		for (Object[] attendanceLogObj : attendanceLogList) {
			AttendanceLog attendanceLog = new AttendanceLog();
			Long employeeId = attendanceLogObj[0] != null ? Long.parseLong(attendanceLogObj[0].toString()) : null;

			String employeeCode = attendanceLogObj[1] != null ? (String) attendanceLogObj[1] : null;
			String employeeName = attendanceLogObj[2] != null ? (String) attendanceLogObj[2] : null;
			String departmentName = attendanceLogObj[3] != null ? (String) attendanceLogObj[3] : null;
			String designationName = attendanceLogObj[4] != null ? (String) attendanceLogObj[4] : null;
			String reportingManager = attendanceLogObj[5] != null ? (String) attendanceLogObj[5] : null;
			String jobLocation = attendanceLogObj[6] != null ? (String) attendanceLogObj[6] : null;

			Date attendanceDate = attendanceLogObj[7] != null ? (Date) (attendanceLogObj[7]) : null;
			String inTime = attendanceLogObj[8] != null ? (String) attendanceLogObj[8] : "";
			String outTime = attendanceLogObj[9] != null ? (String) attendanceLogObj[9] : "";
			String location = attendanceLogObj[10] != null ? (String) attendanceLogObj[10] : null;

			String mode = attendanceLogObj[11] != null ? (String) attendanceLogObj[11] : null;
			String status = attendanceLogObj[12] != null ? (String) attendanceLogObj[12] : null;
			String totalTime = attendanceLogObj[13] != null ? (String) attendanceLogObj[13] : null;
			String shiftName = attendanceLogObj[14] != null ? (String) attendanceLogObj[14] : null;
			String shiftDuration = attendanceLogObj[15] != null ? (String) attendanceLogObj[15] : null;
			attendanceLog.setAttendanceDate(attendanceDate);
			attendanceLog.setEmployeeId(employeeId);
			attendanceLog.setEmployeeCode(employeeCode);
			attendanceLog.setDepartmentName(departmentName);
			attendanceLog.setDesignationName(designationName);
			attendanceLog.setReportingTo(reportingManager);
			attendanceLog.setJobLocation(jobLocation);

//			attendanceLog.setInTime(inTime);
//			attendanceLog.setOutTime(outTime);
			// np
			String time1 = null;
			SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
			try {
				Date date1 = sdf.parse(inTime);

				SimpleDateFormat sdf2 = new SimpleDateFormat("hh:mm:ss aa");

				time1 = sdf2.format(date1);

			} catch (ParseException e) {
				// e.printStackTrace();
			}
			if (time1 != null) {
				attendanceLog.setInTime(time1);
			} else {
				attendanceLog.setInTime("NA");
			}

			// np
			String time2 = null;
			SimpleDateFormat sd = new SimpleDateFormat("HH:mm:ss");
			try {
				Date date2 = sd.parse(outTime);

				SimpleDateFormat sd2 = new SimpleDateFormat("hh:mm:ss aa");

				time2 = sd2.format(date2);

			} catch (ParseException e) {
				// e.printStackTrace();
			}
			if (time2 != null) {
				attendanceLog.setOutTime(time2);
			} else {
				attendanceLog.setOutTime("NA");
			}

			attendanceLog.setLocation(location);
			attendanceLog.setStatus(status);
			attendanceLog.setMode(mode);
			attendanceLog.setEmployeeName(employeeName);
			if(shiftName != null) {
				attendanceLog.setShiftName(shiftName);
			}else {
				attendanceLog.setShiftName("NA");
			}
			
			attendanceLog.setTotalTime(totalTime);
			// attendanceLogDto.setCompanyId(companyId);
			attendanceDtoList.add(attendanceLog);
		}
		return attendanceDtoList;
	}

	public PunchTimeDetailDTO punchdatabaseModelToUiDto(List<Object[]> punchTimeDetail) {

		PunchTimeDetailDTO punchTimeDetailDTO = new PunchTimeDetailDTO();

		for (Object[] punch : punchTimeDetail) {

			if (punch[0] != null) {
				punchTimeDetailDTO.setTime(punch[0].toString());
			}
			if (punch[1] != null) {
				punchTimeDetailDTO.setIn_out(punch[1].toString());
			}
		}

//		if (punchTimeDetail[0] != null) {
//			 punchTimeDetail[0].getClass();
//
//			punchTimeDetailDTO.setIn_out(PunchTimeDetails.getIn_out());
//			punchTimeDetailDTO.setTime(PunchTimeDetails.getTime());
//
//		}

		// punchTimeDetailDTO.setTime(punchTimeDetail.getTime());
		// punchTimeDetailDTO.setIn_out(punchTimeDetail.getIn_out());

		// if (punchTimeDetail[1] != null) {
		// punchTimeDetailDTO.setIn_out(punchTimeDetail[1].toString()); }

		return punchTimeDetailDTO;
	}

	public PunchTimeDetail uiDtoToBioMetricpunchTimeDetailModel(PunchTimeDetailDTO punchTimeDetailDto, Long count) {
		PunchTimeDetail punchTimeDetail = new PunchTimeDetail();

		punchTimeDetail.setDate(punchTimeDetailDto.getDate());

		punchTimeDetail.setTime((punchTimeDetailDto.getTime()));
		punchTimeDetail.setIn_out(punchTimeDetailDto.getIn_out());
		punchTimeDetail.setSNo(count + 1);
		punchTimeDetail.setTktNo(punchTimeDetailDto.getTktNo());

		if (punchTimeDetailDto.getFlag() != null)
			punchTimeDetail.setFlag(punchTimeDetailDto.getFlag());
		else
			punchTimeDetail.setFlag("BiometricLane");
		String tkt = punchTimeDetailDto.getTktNo();
		String[] arrOfStr = tkt.split("-");
		for (String string : arrOfStr) {
			System.out.println(string);
		}

		punchTimeDetail.setCompanyId(punchTimeDetailDto.getCompanyId());
		punchTimeDetail.setHhMm(1l);
		return punchTimeDetail;
	}

	public List<RestrictedLocationPremiseDTO> objRestrictedLocationPremiseToDtoList(List<Object[]> obj) {
		return obj.stream().map(item -> objRestrictedLocationPremiseToDto(item)).collect(Collectors.toList());
	}

	public RestrictedLocationPremiseDTO objRestrictedLocationPremiseToDto(Object[] obj) {
		RestrictedLocationPremiseDTO restrictedLocationPremiseDTO = new RestrictedLocationPremiseDTO();
		Long attendanceSchemeId = obj[0] != null ? Long.valueOf(obj[0].toString()) : null;
		String firstName = obj[1] != null ? (String) obj[1] : null;
		Long employeeId = obj[2] != null ? Long.valueOf(obj[2].toString()) : null;
		Long userId = obj[3] != null ? Long.valueOf(obj[3].toString()) : null;
		String aschemeStatus = obj[4] != null ? (String) obj[4] : null;
		Long arDays = obj[5] != null ? Long.valueOf(obj[5].toString()) : null;
		Long attendanceTypeId = obj[6] != null ? Long.valueOf(obj[6].toString()) : null;
		String typeCode = obj[7] != null ? obj[7].toString() : null;
		String aTypeTransactionstatus = obj[8] != null ? (String) obj[8] : null;
		Long locationId = obj[9] != null ? Long.valueOf(obj[9].toString()) : null;
		Double latitude = obj[10] != null ? Double.valueOf(obj[10].toString()) : null;
		Double longitude = obj[11] != null ? Double.valueOf(obj[11].toString()) : null;
		Double radius = obj[12] != null ? Double.valueOf(obj[12].toString()) : null;

		restrictedLocationPremiseDTO.setAttendanceSchemeId(attendanceSchemeId);
		restrictedLocationPremiseDTO.setFirstName(firstName);
		restrictedLocationPremiseDTO.setEmployeeId(employeeId);
		restrictedLocationPremiseDTO.setUserId(userId);
		restrictedLocationPremiseDTO.setAschemeStatus(aschemeStatus);
		restrictedLocationPremiseDTO.setArDays(arDays);
		restrictedLocationPremiseDTO.setAttendanceTypeId(attendanceTypeId);
		restrictedLocationPremiseDTO.setTypeCode(typeCode);
		restrictedLocationPremiseDTO.setaTypeTransactionstatus(aTypeTransactionstatus);
		restrictedLocationPremiseDTO.setLocationId(locationId);
		restrictedLocationPremiseDTO.setLatitude(latitude);
		restrictedLocationPremiseDTO.setLongitude(longitude);
		restrictedLocationPremiseDTO.setRadius(radius);

		return restrictedLocationPremiseDTO;
	}
	
	public PunchTimeDetail addFilNameToPunchTimeDetailObj(PunchTimeDetail punchTimeDetail,MultipartFile multipartFile, boolean isFile) {	
		String fileName = "";
		if (isFile) {
			fileName = "map_snapshot"+punchTimeDetail.getDate().toInstant().toEpochMilli();
			String extension = FilenameUtils.getExtension(multipartFile.getOriginalFilename());
			fileName = fileName + "." + extension;

			String path =GlobalConstantUtils.custom_separateor +"docfiles"+GlobalConstantUtils.custom_separateor + "mapsnapshot";
			String dbPath = path + GlobalConstantUtils.custom_separateor + fileName;
			storageUtil.store(multipartFile, path, fileName);
			punchTimeDetail.setFileLocation(dbPath);
		}
		return punchTimeDetail;
	}
}
