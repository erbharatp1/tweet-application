package com.csipl.tms.attendanceCalculation.adaptor;

import java.sql.Time;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.csipl.hrms.common.util.DateUtils;
import com.csipl.hrms.dto.employee.EmployeeDTO;
import com.csipl.tms.dto.attendancelog.AttendanceLogDTO;
import com.csipl.tms.dto.deviceinfo.DeviceLogsInfoDTO;
import com.csipl.tms.model.attendancelog.AttendanceLog;
import com.csipl.tms.model.deviceinfo.DeviceLogsInfo;
import com.csipl.tms.model.halfdayrule.HalfDayRule;
/*import com.csipl.tms.model.common.Company;
import com.csipl.tms.model.common.Employee;*/
import com.csipl.tms.service.Adaptor;

public class AttendanceCalculationAdaptor implements Adaptor<AttendanceLogDTO, AttendanceLog> {

	private static final Logger logger = LoggerFactory.getLogger(AttendanceCalculationAdaptor.class);
	
	public List<AttendanceLog> uiDtoToDatabaseModelList1(List<AttendanceLogDTO> attendanceLogDtoList,
			Map<String, Long> mapEmpIdAndEmpCode, HalfDayRule halfDayRule) {
		List<AttendanceLog> attendanceLogList = new ArrayList<AttendanceLog>();
		attendanceLogDtoList.forEach(attendanceLogDto -> {
			attendanceLogList.add(uiDtoToDatabaseModel1(attendanceLogDto, mapEmpIdAndEmpCode, halfDayRule));
		});
		return attendanceLogList;
	}

	// attendance sync
	public List<AttendanceLog> uiDtoToDatabaseModelList2(List<AttendanceLogDTO> attendanceLogDtoList,
			Map<String, EmployeeDTO> mapEmpIdAndEmpCode, HalfDayRule halfDayRule,
			List<Object[]> lateCommersEmployeeListWithCount, List<DeviceLogsInfoDTO> deviceLogsInfoDTO) {
		List<AttendanceLog> attendanceLogList = new ArrayList<AttendanceLog>();
		attendanceLogDtoList.forEach(attendanceLogDto -> {
			attendanceLogList.add(uiDtoToDatabaseModel2(attendanceLogDto, mapEmpIdAndEmpCode, halfDayRule,
					lateCommersEmployeeListWithCount, deviceLogsInfoDTO));
		});
		return attendanceLogList;
	}

	@Override
	public List<AttendanceLogDTO> databaseModelToUiDtoList(List<AttendanceLog> dbobj) {
		return null;
	}

	public AttendanceLog uiDtoToDatabaseModel1(AttendanceLogDTO attendanceLogDto, Map<String, Long> mapEmpIdAndEmpCode,
			HalfDayRule halfDayRule) {
		AttendanceLog attendanceLog = new AttendanceLog();
		/*
		 * Employee employee = new Employee(); Company company = new Company();
		 * company.setCompanyId(attendanceLogDto.getCompanyId()); if
		 * (attendanceLogDto.getEmployeeId() != null)
		 * //employee.setEmployeeId(attendanceLogDto.getEmployeeId());
		 */
		if (attendanceLogDto.getEmployeeId() != null)
			attendanceLog.setAttendanceDate(attendanceLogDto.getAttendanceDate());

		// attendanceLog.setInTime(attendanceLogDto.getInTime());
		// np
		String time1 = null;
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
		try {
			Date date1 = sdf.parse(attendanceLogDto.getInTime());

			SimpleDateFormat sdf2 = new SimpleDateFormat("hh:mm:ss aa");

			time1 = sdf2.format(date1);

		} catch (ParseException e) {
			e.printStackTrace();
		}
		attendanceLog.setInTime(time1);

		// attendanceLog.setOutTime(attendanceLogDto.getOutTime());
		// np
		String time2 = null;
		SimpleDateFormat sd = new SimpleDateFormat("HH:mm:ss");
		try {
			Date date2 = sd.parse(attendanceLogDto.getOutTime());

			SimpleDateFormat sd2 = new SimpleDateFormat("hh:mm:ss aa");

			time2 = sd2.format(date2);

		} catch (ParseException e) {
			e.printStackTrace();
		}
		attendanceLog.setOutTime(time2);

		if (attendanceLogDto.getInDeviceId() != null)
			attendanceLog.setInDeviceId(attendanceLogDto.getInDeviceId());
		else
			attendanceLog.setInDeviceId(1l);
		if (attendanceLogDto.getOutDeviceId() != null)
			attendanceLog.setOutDeviceId(attendanceLogDto.getOutDeviceId());
		else
			attendanceLog.setOutDeviceId(1l);
		attendanceLog.setCompanyId(attendanceLogDto.getCompanyId());
		/*
		 * attendanceLog.setCreatedBy(123l); attendanceLog.setUpdatedBy(123l);
		 */
		attendanceLog.setCreatedDate(new Date());
		attendanceLog.setUpdatedDate(new Date());
		attendanceLog.setAttendanceDate(attendanceLogDto.getAttendanceDate());
		attendanceLog.setEmployeeId(attendanceLogDto.getEmployeeId());
		attendanceLog.setEmployeeCode(attendanceLogDto.getEmployeeCode());
		attendanceLog.setMode(attendanceLogDto.getMode());
		attendanceLog.setLatitude(attendanceLogDto.getLatitude());
		attendanceLog.setLongitude(attendanceLogDto.getLongitude());
		attendanceLog.setAddress(attendanceLogDto.getAddress());
		/*
		 * LocalTime t1 = LocalTime.parse(attendanceLogDto.getInTime()); LocalTime t2 =
		 * LocalTime.parse(attendanceLogDto.getOutTime()); Duration diff =
		 * Duration.between(t2, t1); System.out.println(diff.toHours());
		 */
		// SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
		// np
		SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss aa");

		Date d1 = null;
		Date d2 = null;
		try {
			d1 = format.parse(attendanceLogDto.getInTime());
			d2 = format.parse(attendanceLogDto.getOutTime());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		long diff = d2.getTime() - d1.getTime();
		long diffSeconds = diff / 1000 % 60;
		long diffMinutes = diff / (60 * 1000) % 60;
		long diffHours = diff / (60 * 60 * 1000);
		attendanceLog.setTotalTime(diffHours + " Hours" + diffMinutes + " Minuts");

		if (mapEmpIdAndEmpCode.containsKey(attendanceLogDto.getEmployeeCode())) {
			Long employeeId = (Long) mapEmpIdAndEmpCode.get(attendanceLogDto.getEmployeeCode());
			attendanceLog.setEmployeeId(employeeId);
			// attendanceLog.setEmployeeCode(attendanceLogDto.getEmployeeCode());

		}

		if (diffHours >= halfDayRule.getMaximumRequireHour())
			attendanceLog.setStatus("P");
		else if (diffHours >= halfDayRule.getMinimumRequireHour())
			attendanceLog.setStatus("P/2");
		else
			attendanceLog.setStatus("A");

		return attendanceLog;
	}

	// attendance sync

	public AttendanceLog uiDtoToDatabaseModel2(AttendanceLogDTO attendanceLogDto,Map<String, EmployeeDTO> mapEmpIdAndEmpCode,HalfDayRule halfDayRule, List<Object[]> lateCommersEmployeeListWithCount, List<DeviceLogsInfoDTO> deviceLogsInfoDTO) {
		AttendanceLog attendanceLog = new AttendanceLog();
		/*
		 * Employee employee = new Employee(); Company company = new Company();
		 * company.setCompanyId(attendanceLogDto.getCompanyId()); if
		 * (attendanceLogDto.getEmployeeId() != null)
		 * //employee.setEmployeeId(attendanceLogDto.getEmployeeId());
		 */
		if (attendanceLogDto.getEmployeeId() != null)
			attendanceLog.setAttendanceDate(attendanceLogDto.getAttendanceDate());

		attendanceLog.setInTime(attendanceLogDto.getInTime());
		attendanceLog.setOutTime(attendanceLogDto.getOutTime());
		if(attendanceLogDto.getInDeviceId()!=null)
		attendanceLog.setInDeviceId(attendanceLogDto.getInDeviceId());
		else
			attendanceLog.setInDeviceId(1l);
		if(attendanceLogDto.getOutDeviceId()!=null)
		attendanceLog.setOutDeviceId(attendanceLogDto.getOutDeviceId());
		else
			attendanceLog.setOutDeviceId(1l);
		attendanceLog.setCompanyId(attendanceLogDto.getCompanyId());
		/*
		 * attendanceLog.setCreatedBy(123l); attendanceLog.setUpdatedBy(123l);
		 */
		attendanceLog.setCreatedDate(new Date());
		attendanceLog.setUpdatedDate(new Date());
		attendanceLog.setAttendanceDate(attendanceLogDto.getAttendanceDate());
		attendanceLog.setEmployeeId(attendanceLogDto.getEmployeeId());
		attendanceLog.setEmployeeCode(attendanceLogDto.getEmployeeCode());
		attendanceLog.setMode(attendanceLogDto.getMode());
		attendanceLog.setLatitude(attendanceLogDto.getLatitude());
		attendanceLog.setLongitude(attendanceLogDto.getLongitude());
		attendanceLog.setAddress(attendanceLogDto.getAddress());
		
		attendanceLog.setOutTimeLatitude(attendanceLogDto.getOutTimeLatitude());
		attendanceLog.setOutTimeLangitude(attendanceLogDto.getOutTimeLangitude());
		attendanceLog.setOutTimeAddress(attendanceLogDto.getOutTimeAddress());
		   /* LocalTime t1 = LocalTime.parse(attendanceLogDto.getInTime());
		    LocalTime t2 = LocalTime.parse(attendanceLogDto.getOutTime());
		    Duration diff = Duration.between(t2, t1);
		    System.out.println(diff.toHours());*/
		 SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");

		    Date d1 = null;
		    Date d2 = null;
		 try {
		        d1 = format.parse(attendanceLogDto.getInTime());
		        d2 = format.parse(attendanceLogDto.getOutTime());
		    } catch (ParseException e) {
		        e.printStackTrace();
		    }
		 long diff = d2.getTime() - d1.getTime();
		    long diffSeconds = diff / 1000 % 60;
		    long diffMinutes = diff / (60 * 1000) % 60;
		    long diffHours = diff / (60 * 60 * 1000);
		attendanceLog.setTotalTime(diffHours+" Hours"+diffMinutes +" Minuts");
		
		/* if (mapEmpIdAndEmpCode.containsKey(attendanceLogDto.getEmployeeCode())) {
				Long employeeId = (Long) mapEmpIdAndEmpCode.get(attendanceLogDto.getEmployeeCode());
				attendanceLog.setEmployeeId(employeeId);
				//attendanceLog.setEmployeeCode(attendanceLogDto.getEmployeeCode());
				
			}*/
		
		//new implementation for late comers consider half day if late comers count is greater than  graceFrqInMonth
		Object count   = lateCommersEmployeeListWithCount.stream().filter(x ->x[1].equals(attendanceLogDto.getEmployeeCode())).map(map->map[0]).findAny().orElse(0); 
		Object graceFrqInMonth = lateCommersEmployeeListWithCount.stream().filter(x ->x[1].equals(attendanceLogDto.getEmployeeCode())).map(map->map[4]).findAny().orElse(0); 
		String employeeCode = deviceLogsInfoDTO.stream().filter(emp->emp.getEmpCode().equals(attendanceLogDto.getEmployeeCode())).map(DeviceLogsInfoDTO::getEmpCode).findAny().orElse(null);
		Object halfDayRuleflag = lateCommersEmployeeListWithCount.stream().filter(x ->x[1].equals(attendanceLogDto.getEmployeeCode())).map(map->map[5]).findAny().orElse(null); 
		 
		Long longCount = Long.valueOf(count.toString());
		longCount++;

		if (diffHours >= halfDayRule.getMaximumRequireHour()) {
			attendanceLog.setStatus("P");
			if (((longCount).compareTo(Long.valueOf(graceFrqInMonth.toString())) > 0 && employeeCode != null && halfDayRuleflag != null && halfDayRuleflag.equals("AC")))
				attendanceLog.setStatus("P/2");
		}
		else if (diffHours >= halfDayRule.getMinimumRequireHour())
			attendanceLog.setStatus("P/2");
		else
			attendanceLog.setStatus("A");

		
		
		 
			
		return attendanceLog;
	}
	
	@Override
	public AttendanceLogDTO databaseModelToUiDto(AttendanceLog attendanceLog) {
		return null;
	}

	/*
	 * Commented by Ravindra Singh previous Code without resttemplate
	 * 
	 */

	/*
	 * public List<AttendanceLogDTO> objListToDto(List<Object[]> objectList, String
	 * prefix, Long companyId, List<Object[]> objCodeList, Map<String, Long>
	 * mapEmpIdAndEmpCode) throws ParseException { // DateFormat dateFormat = new
	 * SimpleDateFormat("yyyy-mm-dd hh:mm:ss"); // DateFormat onlyDate = new
	 * SimpleDateFormat("yyyy-MM-dd"); DateFormat onlyTime = new
	 * SimpleDateFormat("hh:mm:ss"); List<AttendanceLogDTO> attendanceLogDtoList =
	 * new ArrayList<AttendanceLogDTO>();
	 * 
	 * for (Object[] obj : objCodeList) { Long employeeId = obj[0] != null ?
	 * Long.parseLong(obj[0].toString()) : null; String employeeCode = obj[1] !=
	 * null ? (String) obj[1] : null; mapEmpIdAndEmpCode.put(employeeCode,
	 * employeeId); }
	 * 
	 * for (Object[] arrOfAttendanceDto : objectList) { AttendanceLogDTO
	 * attendanceLogDto = new AttendanceLogDTO();
	 * attendanceLogDto.setCompanyId(companyId); // Double minSno =
	 * arrOfAttendanceDto[0] != null ? //
	 * Double.parseDouble(arrOfAttendanceDto[0].toString()) : null; Date mindate =
	 * arrOfAttendanceDto[1] != null ? (Date) (arrOfAttendanceDto[1]) : null; //
	 * Double maxSno = arrOfAttendanceDto[2] != null ? //
	 * Double.parseDouble(arrOfAttendanceDto[2].toString()) : null; Date maxdate =
	 * arrOfAttendanceDto[3] != null ? (Date) (arrOfAttendanceDto[3]) : null; Long
	 * tktno = arrOfAttendanceDto[5] != null ?
	 * Long.parseLong(arrOfAttendanceDto[5].toString()) : null; // String deviceName
	 * = arrOfAttendanceDto[6] != null ? (String) // arrOfAttendanceDto[6] : null;
	 * Long deviceId = arrOfAttendanceDto[7] != null ?
	 * Long.parseLong(arrOfAttendanceDto[7].toString()) : null;
	 * attendanceLogDto.setEmployeeCode(prefix + tktno); if
	 * (mapEmpIdAndEmpCode.containsKey(attendanceLogDto.getEmployeeCode())) { Long
	 * employeeId = (Long)
	 * mapEmpIdAndEmpCode.get(attendanceLogDto.getEmployeeCode());
	 * attendanceLogDto.setEmployeeId(employeeId); }
	 * 
	 * if (mindate != null) { attendanceLogDto.setAttendanceDate(mindate); } if
	 * (mindate != null) { String minDate = onlyTime.format(mindate);
	 * attendanceLogDto.setInTime(minDate); } if (maxdate != null) { String maxDate
	 * = onlyTime.format(maxdate); attendanceLogDto.setOutTime(maxDate); }
	 * 
	 * attendanceLogDto.setInDeviceId(deviceId);
	 * attendanceLogDto.setOutDeviceId(1L);
	 * attendanceLogDtoList.add(attendanceLogDto); }
	 * 
	 * return attendanceLogDtoList;
	 * 
	 * }
	 */

	/*
	 * Pragya With RestTemplate MIN(cast(log.LogDate as time) )as
	 * minTime,MAX(cast(log.LogDate as time) )as maxTime ,log.UserId ,log.LogDate
	 * ,TIMEDIFF(MAX(cast(log.LogDate as time)),MIN(cast(log.LogDate as time)) ) AS
	 * MinsWorked
	 */
	public List<AttendanceLogDTO> objListToDto(List<Object[]> epushAttendanceList, List<EmployeeDTO> employeeDtoList,
			Map<String, Long> mapEmpIdAndEmpCode, String prefix, Long companyId) {
		// DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
		// DateFormat onlyDate = new SimpleDateFormat("yyyy-MM-dd");

		List<AttendanceLogDTO> attendanceLogDtoList = new ArrayList<AttendanceLogDTO>();

		for (EmployeeDTO employeeDto : employeeDtoList) {
			Long employeeId = employeeDto.getEmployeeId();
			String employeeCode = employeeDto.getEmployeeCode();
			mapEmpIdAndEmpCode.put(employeeCode, employeeId);
		}

		for (Object[] epushAttendanceDto : epushAttendanceList) {
			AttendanceLogDTO attendanceLogDto = new AttendanceLogDTO();
			Time minDate = epushAttendanceDto[0] != null ? (Time) epushAttendanceDto[0] : null;
			Time maxDate = epushAttendanceDto[1] != null ? (Time) epushAttendanceDto[1] : null;
			String userId = epushAttendanceDto[2] != null ? (String) epushAttendanceDto[2] : null;
			Date attendanceDate = epushAttendanceDto[3] != null ? (Date) epushAttendanceDto[3] : null;
			Time minWorkedTime = epushAttendanceDto[4] != null ? (Time) epushAttendanceDto[4] : null;

			if (mapEmpIdAndEmpCode.containsKey(attendanceLogDto.getEmployeeCode())) {
				Long employeeId = (Long) mapEmpIdAndEmpCode.get(attendanceLogDto.getEmployeeCode());
				attendanceLogDto.setEmployeeId(employeeId);
			}

			attendanceLogDto.setAttendanceDate(attendanceDate);
			attendanceLogDto.setEmployeeCode(prefix + "-" + userId);

			// attendanceLogDto.setInTime(minDate.toString());
			// np
			String time1 = null;
			SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
			try {
				Date date1 = sdf.parse(minDate.toString());

				SimpleDateFormat sdf2 = new SimpleDateFormat("hh:mm:ss aa");

				time1 = sdf2.format(date1);

			} catch (ParseException e) {
				e.printStackTrace();
			}
			attendanceLogDto.setInTime(time1);

			attendanceLogDto.setTotalTime(minWorkedTime.toString());

			// attendanceLogDto.setOutTime(maxDate.toString());
			// np
			String time2 = null;
			SimpleDateFormat sd = new SimpleDateFormat("HH:mm:ss");
			try {
				Date date2 = sd.parse(maxDate.toString());

				SimpleDateFormat sd2 = new SimpleDateFormat("hh:mm:ss aa");

				time2 = sd2.format(date2);

			} catch (ParseException e) {
				e.printStackTrace();
			}
			attendanceLogDto.setOutTime(time2);

			attendanceLogDto.setCompanyId(companyId);
			attendanceLogDto.setMode("BioMatric");
			// attendanceLogDto.set
			// attendanceLogDto.setInDeviceId(deviceId);
			// attendanceLogDto.setOutDeviceId(1L);
			attendanceLogDtoList.add(attendanceLogDto);
		}

		return attendanceLogDtoList;

	}

	// testing by biomatric Id

	public List<AttendanceLogDTO> objListToDto1(List<Object[]> epushAttendanceList, List<EmployeeDTO> employeeDtoList,
			Map<String, EmployeeDTO> mapEmpIdAndEmpCode, String prefix, Long companyId) {
		// DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
		// DateFormat onlyDate = new SimpleDateFormat("yyyy-MM-dd");

		List<AttendanceLogDTO> attendanceLogDtoList = new ArrayList<AttendanceLogDTO>();

		for (EmployeeDTO employeeDto : employeeDtoList) {
			Long employeeId = employeeDto.getEmployeeId();
			String boimatricId = employeeDto.getBiometricId();
			mapEmpIdAndEmpCode.put(boimatricId, employeeDto);
		}

		for (Object[] epushAttendanceDto : epushAttendanceList) {
			AttendanceLogDTO attendanceLogDto = new AttendanceLogDTO();
			Time minDate = epushAttendanceDto[0] != null ? (Time) epushAttendanceDto[0] : null;
			Time maxDate = epushAttendanceDto[1] != null ? (Time) epushAttendanceDto[1] : null;
			String userId = epushAttendanceDto[2] != null ? (String) epushAttendanceDto[2] : null;

			Date attendanceDate = epushAttendanceDto[3] != null ? (Date) epushAttendanceDto[3] : null;
			Time minWorkedTime = epushAttendanceDto[4] != null ? (Time) epushAttendanceDto[4] : null;
			String systemId[] = new String[2];
			if (userId != null)
				systemId = userId.split(" ");

			attendanceLogDto.setAttendanceDate(attendanceDate);
			// attendanceLogDto.setEmployeeCode(prefix +"-"+ userId);

			 attendanceLogDto.setInTime(minDate.toString());
			

			attendanceLogDto.setTotalTime(minWorkedTime.toString());

			 attendanceLogDto.setOutTime(maxDate.toString());
			

			attendanceLogDto.setCompanyId(companyId);
			attendanceLogDto.setBiomatricId(systemId[0].trim());
			attendanceLogDto.setMode("BioMatric");
			// attendanceLogDto.set
			// attendanceLogDto.setInDeviceId(deviceId);
			// attendanceLogDto.setOutDeviceId(1L);
			if (mapEmpIdAndEmpCode.containsKey(systemId[0].trim())) {
				EmployeeDTO employee = (EmployeeDTO) mapEmpIdAndEmpCode.get(systemId[0].trim());
				attendanceLogDto.setEmployeeId(employee.getEmployeeId());
				attendanceLogDto.setEmployeeCode(employee.getEmployeeCode());
				attendanceLogDtoList.add(attendanceLogDto);
			}else {
				if(systemId[0] != null)
				logger.info("User id not belongs to employee "+systemId[0]);
			}

		}

		return attendanceLogDtoList;

	}

	public List<AttendanceLogDTO> AttendanceObjListToDto(List<Object[]> epushAttendanceList,
			List<AttendanceLogDTO> systemAttendanceLogList, String prefix) throws ParseException {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");

		// DateFormat onlytime = new SimpleDateFormat("hh:mm:ss");
		// np
		DateFormat onlytime = new SimpleDateFormat("hh:mm:ss aa");

		List<AttendanceLogDTO> deviceAttendanceLogsList = new ArrayList<AttendanceLogDTO>();

		for (Object[] epushAttendanceDto : epushAttendanceList) {
			AttendanceLogDTO DeviceAttendanceLogsInfo = new AttendanceLogDTO();
			Integer deviceId = epushAttendanceDto[0] != null ? (Integer) epushAttendanceDto[0] : null;
			String userId = epushAttendanceDto[1] != null ? (String) epushAttendanceDto[1] : null;
			Date date = null;
			/*
			 * if(epushAttendanceDto[2]!=null) { date = new
			 * Date((long)epushAttendanceDto[2]); }
			 */
			// String attendanceDate =
			// epushAttendanceDto[2]!=null?(String)epushAttendanceDto[2]:null;
			Timestamp attendanceDate = epushAttendanceDto[2] != null ? (Timestamp) epushAttendanceDto[2] : null;
			String direction = epushAttendanceDto[3] != null ? (String) epushAttendanceDto[3] : null;
			String systemId[] = new String[2];
			if (userId != null)
				systemId = userId.split(" ");

			// System.out.println("attendanceDate..."+attendanceDate);
			String attendanceTime = onlytime.format(attendanceDate);
			DeviceAttendanceLogsInfo.setAttendanceDate(attendanceDate);
			DeviceAttendanceLogsInfo.setDeviceId(deviceId.longValue());

			DeviceAttendanceLogsInfo.setEmployeeCode(prefix + "-" + userId);
			DeviceAttendanceLogsInfo.setDirection(direction);
			DeviceAttendanceLogsInfo.setMode("BioMatric");
			DeviceAttendanceLogsInfo.setInTime(attendanceTime);

			// attendanceLogDto.set
			// attendanceLogDto.setInDeviceId(deviceId);
			// attendanceLogDto.setOutDeviceId(1L);
			deviceAttendanceLogsList.add(DeviceAttendanceLogsInfo);
		}

		return deviceAttendanceLogsList;

	}

	public List<AttendanceLogDTO> EpushAttendanceObjListToDto(List<Object[]> epushAttendanceList, String prefix,
			Map<String, EmployeeDTO> mapEmpIdAndEmpCode) {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");

		 DateFormat onlytime = new SimpleDateFormat("hh:mm:ss");
		// np
		//DateFormat onlytime = new SimpleDateFormat("hh:mm:ss aa");

		List<AttendanceLogDTO> deviceAttendanceLogsList = new ArrayList<AttendanceLogDTO>();

		for (Object[] epushAttendanceDto : epushAttendanceList) {
			AttendanceLogDTO DeviceAttendanceLogsInfo = new AttendanceLogDTO();
			Integer deviceId = epushAttendanceDto[0] != null ? (Integer) epushAttendanceDto[0] : null;
			String userId = epushAttendanceDto[1] != null ? (String) epushAttendanceDto[1] : null;
			Date date = null;
			/*
			 * if(epushAttendanceDto[2]!=null) { date = new
			 * Date((long)epushAttendanceDto[2]); }
			 */
			// String attendanceDate =
			// epushAttendanceDto[2]!=null?(String)epushAttendanceDto[2]:null;
			Timestamp attendanceDate = epushAttendanceDto[2] != null ? (Timestamp) epushAttendanceDto[2] : null;
			String direction = epushAttendanceDto[3] != null ? (String) epushAttendanceDto[3] : null;
			String systemId[] = new String[2];
			if (userId != null)
				systemId = userId.split(" ");

			// System.out.println("attendanceDate..."+attendanceDate);
			String attendanceTime = onlytime.format(attendanceDate);
			DeviceAttendanceLogsInfo.setAttendanceDate(attendanceDate);
			DeviceAttendanceLogsInfo.setDeviceId(deviceId.longValue());

			// DeviceAttendanceLogsInfo.setEmployeeCode(prefix+"-"+userId);
			DeviceAttendanceLogsInfo.setDirection(direction);
			DeviceAttendanceLogsInfo.setMode("BioMatric");
			DeviceAttendanceLogsInfo.setInTime(attendanceTime);
			DeviceAttendanceLogsInfo.setBiomatricId(systemId[0].trim());
			// attendanceLogDto.set
			// attendanceLogDto.setInDeviceId(deviceId);
			// attendanceLogDto.setOutDeviceId(1L);

			if (mapEmpIdAndEmpCode.containsKey(systemId[0].trim()) && (systemId[0].trim()) != null) {
				EmployeeDTO employee = (EmployeeDTO) mapEmpIdAndEmpCode.get(systemId[0].trim());
				DeviceAttendanceLogsInfo.setEmployeeId(employee.getEmployeeId());
				DeviceAttendanceLogsInfo.setEmployeeCode(employee.getEmployeeCode());
				deviceAttendanceLogsList.add(DeviceAttendanceLogsInfo);
			}

		}

		return deviceAttendanceLogsList;

	}

	public List<AttendanceLogDTO> EpushAttendanceObjCheckInListToDto(List<Object[]> epushAttendanceList)
			throws ParseException {

		List<AttendanceLogDTO> deviceAttendanceLogsList = new ArrayList<AttendanceLogDTO>();

		for (Object[] epushAttendanceDto : epushAttendanceList) {
			AttendanceLogDTO DeviceAttendanceLogsInfo = new AttendanceLogDTO();

			String userId = epushAttendanceDto[0] != null ? (String) epushAttendanceDto[0] : null;

			Date attendanceDate = epushAttendanceDto[1] != null ? (Date) epushAttendanceDto[1] : null;
			Date attendanceTime = epushAttendanceDto[2] != null ? (Date) epushAttendanceDto[2] : null;

			String systemId[] = new String[1];
			if (userId != null)
				systemId = userId.split(" ");

			String inTime = null;
			SimpleDateFormat sd = new SimpleDateFormat("HH:mm:ss");
			Date inDate = sd.parse(attendanceTime.toString());
			SimpleDateFormat sd2 = new SimpleDateFormat("hh:mm:ss aa");
			inTime = sd2.format(inDate);

			DeviceAttendanceLogsInfo.setAttendanceDate(attendanceDate);
			DeviceAttendanceLogsInfo.setMode("BioMetric");
			DeviceAttendanceLogsInfo.setCheckInTime(inTime);
			DeviceAttendanceLogsInfo.setBiomatricId(systemId[0].trim());

			deviceAttendanceLogsList.add(DeviceAttendanceLogsInfo);

		}

		return deviceAttendanceLogsList;

	}

	public List<AttendanceLogDTO> objectListToUImodel(List<Object[]> attendancObjList, Map<String, Long> mapEmpCode) {
		List<AttendanceLogDTO> punchTimeDtoList = new ArrayList<AttendanceLogDTO>();
		for (Object[] attendanceObj : attendancObjList) {
			
			AttendanceLogDTO attendanceLogDto = new AttendanceLogDTO();
			Long minSno = attendanceObj[0] != null ? Long.parseLong(attendanceObj[0].toString()) : null;
			Long maxSno = attendanceObj[1] != null ? Long.parseLong(attendanceObj[1].toString()) : null;
			String minTime = attendanceObj[2] != null ? (String) attendanceObj[2] : null;
			String maxTime = attendanceObj[3] != null ? (String) attendanceObj[3] : null;
			String tktNo = attendanceObj[4] != null ? (String) attendanceObj[4] : null;

			Date date = attendanceObj[5] != null ? (Date) attendanceObj[5] : null;
			Long companyId = attendanceObj[6] != null ? Long.parseLong(attendanceObj[6].toString()) : null;
			String mode = attendanceObj[7] != null ? (String) attendanceObj[7] : null;
			String latitude = attendanceObj[8] != null ? (String) attendanceObj[8] : null;
			String longitude = attendanceObj[9] != null ? (String) attendanceObj[9] : null;
			String address = attendanceObj[10] != null ? (String) attendanceObj[10] : null;
			String time = attendanceObj[11] != null ? (String) attendanceObj[11] : null;
			attendanceLogDto.setAttendanceDate(date);

			// attendanceLogDto.setInTime(minTime);
			// np
			String time1 = null;
			SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
			try {
				Date date1 = sdf.parse(minTime);

				SimpleDateFormat sdf2 = new SimpleDateFormat("hh:mm:ss aa");

				time1 = sdf2.format(date1);

			}catch (Exception e) {
				e.getMessage();
			}
			attendanceLogDto.setInTime(time1);

			// attendanceLogDto.setOutTime(maxTime);
			// np
			String time2 = null;
			SimpleDateFormat sd = new SimpleDateFormat("HH:mm:ss");
			try {
				Date date2 = sd.parse(maxTime);

				SimpleDateFormat sd2 = new SimpleDateFormat("hh:mm:ss aa");

				time2 = sd2.format(date2);

			} catch (Exception e) {
				e.getMessage();
			}
			attendanceLogDto.setOutTime(time2);

			attendanceLogDto.setMaxSno(maxSno);
			attendanceLogDto.setMinSno(minSno);
			// attendanceLogDto.setEmployeeCode(tktNo);
			attendanceLogDto.setMode(mode);
			attendanceLogDto.setLatitude(latitude);
			attendanceLogDto.setLongitude(longitude);
			attendanceLogDto.setAddress(address);
			//String[] arrOfStr = tktNo.split("-");
			attendanceLogDto.setEmployeeCode(getEmployeeCode(tktNo));

			attendanceLogDto.setCompanyId(companyId);
			attendanceLogDto.setDeviceId(0l);
			attendanceLogDto.setDirection("In");
			attendanceLogDto.setTotalTime(time);
			if (mapEmpCode.containsKey(attendanceLogDto.getEmployeeCode())) {
				Long employeeId = (Long) mapEmpCode.get(attendanceLogDto.getEmployeeCode());
				attendanceLogDto.setEmployeeId(employeeId);
				punchTimeDtoList.add(attendanceLogDto);
			}
		}
		
		return punchTimeDtoList;
	}

	//puchtimeDetails Model to dto conversion for cron
	public List<AttendanceLogDTO> objectListToUImodelforCron(List<Object[]> attendancObjList, Map<String, Long> mapEmpCode) {
		List<AttendanceLogDTO> punchTimeDtoList =new ArrayList<AttendanceLogDTO>();
		for (Object[] attendanceObj : attendancObjList) {
			System.out.println("attendanceObj"+attendanceObj);
			AttendanceLogDTO attendanceLogDto =new AttendanceLogDTO();
			Long minSno=attendanceObj[0]!=null?Long.parseLong(attendanceObj[0].toString()):null;
			Long maxSno=attendanceObj[1]!=null?Long.parseLong(attendanceObj[1].toString()):null;
			String minTime=attendanceObj[2]!=null?(String)attendanceObj[2]:null;
			String maxTime=attendanceObj[3]!=null?(String)attendanceObj[3]:null;
			String tktNo=attendanceObj[4]!=null?(String)attendanceObj[4]:null;
			
			Date date = attendanceObj[5] != null ? (Date) attendanceObj[5] : null;
			Long companyId=attendanceObj[6]!=null?Long.parseLong(attendanceObj[6].toString()):null;
			String mode=attendanceObj[7]!=null?(String)attendanceObj[7]:null;
			String latitude=attendanceObj[8]!=null?(String)attendanceObj[8]:null;
			String longitude=attendanceObj[9]!=null?(String)attendanceObj[9]:null;
			String address=attendanceObj[10]!=null?(String)attendanceObj[10]:null;
			String time=attendanceObj[11]!=null?(String)attendanceObj[11]:null;
			
			String  outTimeLatitude=attendanceObj[12]!=null?(String)attendanceObj[12]:null;
			String outTimeLongitude=attendanceObj[13]!=null?(String)attendanceObj[13]:null;
			String outTimeAddress=attendanceObj[14]!=null?(String)attendanceObj[14]:null;
			
			attendanceLogDto.setOutTimeLangitude(outTimeLongitude);
			attendanceLogDto.setOutTimeLatitude(outTimeLatitude);
			attendanceLogDto.setOutTimeAddress(outTimeAddress);
			attendanceLogDto.setAttendanceDate(date);
			attendanceLogDto.setInTime(minTime);
			attendanceLogDto.setOutTime(maxTime);
			attendanceLogDto.setMaxSno(maxSno);
			attendanceLogDto.setMinSno(minSno);
		//	attendanceLogDto.setEmployeeCode(tktNo);
			attendanceLogDto.setMode(mode);
			attendanceLogDto.setLatitude(latitude);
			attendanceLogDto.setLongitude(longitude);
			attendanceLogDto.setAddress(address);
		//	String[] arrOfStr = tktNo.split("-");
			attendanceLogDto.setEmployeeCode(getEmployeeCode(tktNo));
		
			 
			attendanceLogDto.setCompanyId(companyId);
			attendanceLogDto.setDeviceId(0l);
			attendanceLogDto.setDirection("In");
			attendanceLogDto.setTotalTime(time);
			 if (mapEmpCode.containsKey(attendanceLogDto.getEmployeeCode())) {
					Long employeeId = (Long) mapEmpCode.get(attendanceLogDto.getEmployeeCode());
					attendanceLogDto.setEmployeeId(employeeId);
					punchTimeDtoList.add(attendanceLogDto);
				
				}
			
   		}
		System.out.println("punchTimeDtoList..."+punchTimeDtoList);
		return punchTimeDtoList;
	}
	
	
	
	public String getEmployeeCode(String userId) {
		String[] arrOfStr = userId.split("-");
		System.out.println(arrOfStr.length);
		if (arrOfStr.length == 2) {
			return arrOfStr[0];
		} else {
			return arrOfStr[0] + "-" + arrOfStr[1];
		}

	}

	public List<AttendanceLogDTO> attendanceCalculation(List<AttendanceLogDTO> attendanceLogDtoList,
			List<AttendanceLogDTO> systemAttendanceLogList, Map<String, Long> mapEmpIdAndEmpCode, Long comapnyId) {
		List<AttendanceLogDTO> attendanceLogList = new ArrayList<AttendanceLogDTO>();
		if ((attendanceLogDtoList.size() > 0) && (systemAttendanceLogList.size() > 0)) {
			Collections.sort(attendanceLogDtoList);
			Collections.sort(systemAttendanceLogList);

			for (AttendanceLogDTO attendanceLogDTO : attendanceLogDtoList) {
				int iStringSearch = Collections.binarySearch(systemAttendanceLogList, attendanceLogDTO);
				if (iStringSearch >= 0) {

					// List<AttendanceLogDTO> att = attendanceLogDtoList.stream(). filter(p ->
					// p.getEmployeeCode() .equals(systemAttendanceLogDTO.getEmployeeCode())).
					// collect(Collectors.toCollection(() -> new ArrayList<AttendanceLogDTO>()));
					// System.out.println("att..."+att);
					AttendanceLogDTO attendanceLogDto = systemAttendanceLogList.get(iStringSearch);
					if (attendanceLogDTO.getInTime().compareTo(attendanceLogDto.getInTime()) <= 0) {

						// attendanceLogDTO.setInTime(attendanceLogDTO.getInTime());
						// np
						String time1 = null;
						SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
						try {
							Date date1 = sdf.parse(attendanceLogDTO.getInTime());

							SimpleDateFormat sdf2 = new SimpleDateFormat("hh:mm:ss aa");

							time1 = sdf2.format(date1);

						} catch (ParseException e) {
							e.printStackTrace();
						}
						attendanceLogDTO.setInTime(time1);

						attendanceLogDTO.setMode(attendanceLogDTO.getMode());
						attendanceLogDTO.setLatitude(attendanceLogDTO.getLatitude());
						attendanceLogDTO.setLongitude(attendanceLogDTO.getLongitude());
						attendanceLogDTO.setAddress(attendanceLogDTO.getAddress());
					} else {

						// attendanceLogDTO.setInTime(attendanceLogDto.getInTime());
						// np
						String time1 = null;
						SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
						try {
							Date date1 = sdf.parse(attendanceLogDto.getInTime());

							SimpleDateFormat sdf2 = new SimpleDateFormat("hh:mm:ss aa");

							time1 = sdf2.format(date1);

						} catch (ParseException e) {
							e.printStackTrace();
						}
						attendanceLogDTO.setInTime(time1);

						attendanceLogDTO.setLatitude(attendanceLogDto.getLatitude());
						attendanceLogDTO.setLongitude(attendanceLogDto.getLongitude());
						attendanceLogDTO.setAddress(attendanceLogDto.getAddress());
					}
					if (attendanceLogDTO.getOutTime().compareTo(attendanceLogDto.getOutTime()) > 0) {

						// attendanceLogDTO.setOutTime(attendanceLogDTO.getOutTime());
						// np
						String time2 = null;
						SimpleDateFormat sd = new SimpleDateFormat("HH:mm:ss");
						try {
							Date date2 = sd.parse(attendanceLogDTO.getOutTime());

							SimpleDateFormat sd2 = new SimpleDateFormat("hh:mm:ss aa");

							time2 = sd2.format(date2);

						} catch (ParseException e) {
							e.printStackTrace();
						}
						attendanceLogDTO.setOutTime(time2);

					} else {
						// attendanceLogDTO.setOutTime(attendanceLogDTO.getOutTime());
						// np
						String time2 = null;
						SimpleDateFormat sd = new SimpleDateFormat("HH:mm:ss");
						try {
							Date date2 = sd.parse(attendanceLogDTO.getOutTime());

							SimpleDateFormat sd2 = new SimpleDateFormat("hh:mm:ss aa");

							time2 = sd2.format(date2);

						} catch (ParseException e) {
							e.printStackTrace();
						}
						attendanceLogDTO.setOutTime(time2);

					}
					attendanceLogDTO.setCompanyId(comapnyId);
					attendanceLogDTO.setTotalTime(attendanceLogDto.getTotalTime());
					if (mapEmpIdAndEmpCode.containsKey(attendanceLogDto.getEmployeeCode())) {
						Long employeeId = (Long) mapEmpIdAndEmpCode.get(attendanceLogDto.getEmployeeCode());
						attendanceLogDTO.setEmployeeId(employeeId);
						attendanceLogDTO.setEmployeeCode(attendanceLogDto.getEmployeeCode());
						attendanceLogList.add(attendanceLogDTO);
					}

					System.out.println("systemAttendanceLogDTO.setEmployeeId..." + attendanceLogDTO.getEmployeeId());

					boolean flg = systemAttendanceLogList
							.removeIf(obj -> obj.getEmployeeCode().equals(attendanceLogDTO.getEmployeeCode()));
					// System.out.println("flg...."+flg);

				} else {
					if (mapEmpIdAndEmpCode.containsKey(attendanceLogDTO.getEmployeeCode())) {
						Long employeeId = (Long) mapEmpIdAndEmpCode.get(attendanceLogDTO.getEmployeeCode());
						attendanceLogDTO.setEmployeeId(employeeId);
						attendanceLogDTO.setEmployeeCode(attendanceLogDTO.getEmployeeCode());
						attendanceLogList.add(attendanceLogDTO);
					}
				}

			}
			attendanceLogList.addAll(systemAttendanceLogList);
			System.out.println("----*** FINAL LIST FOR  SAVE***-----" + attendanceLogList);
		} else if (attendanceLogDtoList.size() > 0) {
			for (AttendanceLogDTO attendanceLogObjDto : attendanceLogDtoList) {
				AttendanceLogDTO attendanceLogDto = new AttendanceLogDTO();
				attendanceLogDto.setAttendanceDate(attendanceLogObjDto.getAttendanceDate());

				// attendanceLogDto.setInTime(attendanceLogObjDto.getInTime());
				// np
				String time1 = null;
				SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
				try {
					Date date1 = sdf.parse(attendanceLogObjDto.getInTime());

					SimpleDateFormat sdf2 = new SimpleDateFormat("hh:mm:ss aa");

					time1 = sdf2.format(date1);

				} catch (ParseException e) {
					e.printStackTrace();
				}
				attendanceLogDto.setInTime(time1);

				// attendanceLogDto.setOutTime(attendanceLogObjDto.getOutTime());
				// np
				String time2 = null;
				SimpleDateFormat sd = new SimpleDateFormat("HH:mm:ss");
				try {
					Date date2 = sd.parse(attendanceLogObjDto.getOutTime());

					SimpleDateFormat sd2 = new SimpleDateFormat("hh:mm:ss aa");

					time2 = sd2.format(date2);

				} catch (ParseException e) {
					e.printStackTrace();
				}
				attendanceLogDto.setOutTime(time2);

				attendanceLogDto.setCompanyId(comapnyId);
				attendanceLogDto.setCreatedDate(new Date());
				attendanceLogDto.setTotalTime(attendanceLogObjDto.getTotalTime());
				if (mapEmpIdAndEmpCode.containsKey(attendanceLogObjDto.getEmployeeCode())) {
					Long employeeId = (Long) mapEmpIdAndEmpCode.get(attendanceLogObjDto.getEmployeeCode());
					attendanceLogDto.setEmployeeId(employeeId);
					attendanceLogDto.setEmployeeCode(attendanceLogObjDto.getEmployeeCode());
					attendanceLogList.add(attendanceLogDto);
				}

			}

		} else {
			for (AttendanceLogDTO attendanceObjLogDto : systemAttendanceLogList) {
				AttendanceLogDTO attendanceLogDto = new AttendanceLogDTO();
				attendanceLogDto.setAttendanceDate(attendanceObjLogDto.getAttendanceDate());

				// attendanceLogDto.setInTime(attendanceObjLogDto.getInTime());
				// np
				String time1 = null;
				SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
				try {
					Date date1 = sdf.parse(attendanceObjLogDto.getInTime());

					SimpleDateFormat sdf2 = new SimpleDateFormat("hh:mm:ss aa");

					time1 = sdf2.format(date1);

				} catch (ParseException e) {
					e.printStackTrace();
				}
				attendanceLogDto.setInTime(time1);

				// attendanceLogDto.setOutTime(attendanceObjLogDto.getOutTime());
				// np
				String time2 = null;
				SimpleDateFormat sd = new SimpleDateFormat("HH:mm:ss");
				try {
					Date date2 = sd.parse(attendanceObjLogDto.getOutTime());

					SimpleDateFormat sd2 = new SimpleDateFormat("hh:mm:ss aa");

					time2 = sd2.format(date2);

				} catch (ParseException e) {
					e.printStackTrace();
				}
				attendanceLogDto.setOutTime(time2);

				attendanceLogDto.setCompanyId(comapnyId);
				attendanceLogDto.setCreatedDate(new Date());
				attendanceLogDto.setTotalTime(attendanceObjLogDto.getTotalTime());
				attendanceLogDto.setLatitude(attendanceObjLogDto.getLatitude());
				attendanceLogDto.setLongitude(attendanceObjLogDto.getLongitude());
				attendanceLogDto.setAddress(attendanceObjLogDto.getAddress());
				if (mapEmpIdAndEmpCode.containsKey(attendanceObjLogDto.getEmployeeCode())) {
					Long employeeId = (Long) mapEmpIdAndEmpCode.get(attendanceObjLogDto.getEmployeeCode());
					attendanceLogDto.setEmployeeId(employeeId);
					attendanceLogDto.setEmployeeCode(attendanceObjLogDto.getEmployeeCode());
					attendanceLogList.add(attendanceLogDto);
				}

			}
		}

		return attendanceLogList;
	}

	public List<AttendanceLogDTO> attendanceCalculationForfirstAttendance(List<AttendanceLogDTO> attendanceLogDtoList,
			List<AttendanceLogDTO> systemAttendanceLogList, Map<String, Long> mapEmpIdAndEmpCode, Long comapnyId) {
		List<AttendanceLogDTO> attendanceLogList = new ArrayList<AttendanceLogDTO>();
		if ((attendanceLogDtoList.size() > 0) && (systemAttendanceLogList.size() > 0)) {
			Collections.sort(attendanceLogDtoList);
			Collections.sort(systemAttendanceLogList);

			for (AttendanceLogDTO attendanceLogDTO : attendanceLogDtoList) {
				int iStringSearch = Collections.binarySearch(systemAttendanceLogList, attendanceLogDTO);
				if (iStringSearch >= 0) {

					// List<AttendanceLogDTO> att = attendanceLogDtoList.stream(). filter(p ->
					// p.getEmployeeCode() .equals(systemAttendanceLogDTO.getEmployeeCode())).
					// collect(Collectors.toCollection(() -> new ArrayList<AttendanceLogDTO>()));
					// System.out.println("att..."+att);
					AttendanceLogDTO attendanceLogDto = systemAttendanceLogList.get(iStringSearch);
					if (attendanceLogDTO.getInTime().compareTo(attendanceLogDto.getInTime()) <= 0) {

						// attendanceLogDTO.setInTime(attendanceLogDTO.getInTime());
						// np
						String time1 = null;
						SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
						try {
							Date date1 = sdf.parse(attendanceLogDTO.getInTime());

							SimpleDateFormat sdf2 = new SimpleDateFormat("hh:mm:ss aa");

							time1 = sdf2.format(date1);

						} catch (ParseException e) {
							e.printStackTrace();
						}
						attendanceLogDTO.setInTime(time1);

						attendanceLogDTO.setMode(attendanceLogDTO.getMode());
						attendanceLogDTO.setLatitude(attendanceLogDTO.getLatitude());
						attendanceLogDTO.setLongitude(attendanceLogDTO.getLongitude());
						attendanceLogDTO.setAddress(attendanceLogDTO.getAddress());
					} else {
						// attendanceLogDTO.setInTime(attendanceLogDto.getInTime());
						// np
						String time1 = null;
						SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
						try {
							Date date1 = sdf.parse(attendanceLogDTO.getInTime());

							SimpleDateFormat sdf2 = new SimpleDateFormat("hh:mm:ss aa");

							time1 = sdf2.format(date1);

						} catch (ParseException e) {
							e.printStackTrace();
						}
						attendanceLogDTO.setInTime(time1);

						attendanceLogDTO.setLatitude(attendanceLogDto.getLatitude());
						attendanceLogDTO.setLongitude(attendanceLogDto.getLongitude());
						attendanceLogDTO.setAddress(attendanceLogDto.getAddress());
					}

					attendanceLogDTO.setCompanyId(comapnyId);
					attendanceLogDTO.setTotalTime(attendanceLogDto.getTotalTime());
					if (mapEmpIdAndEmpCode.containsKey(attendanceLogDto.getEmployeeCode())) {
						Long employeeId = (Long) mapEmpIdAndEmpCode.get(attendanceLogDto.getEmployeeCode());
						attendanceLogDTO.setEmployeeId(employeeId);
						attendanceLogDTO.setEmployeeCode(attendanceLogDto.getEmployeeCode());
						attendanceLogList.add(attendanceLogDTO);
					}

					System.out.println("systemAttendanceLogDTO.setEmployeeId..." + attendanceLogDTO.getEmployeeId());

					boolean flg = systemAttendanceLogList
							.removeIf(obj -> obj.getEmployeeCode().equals(attendanceLogDTO.getEmployeeCode()));
					// System.out.println("flg...."+flg);

				} else
					attendanceLogList.add(attendanceLogDTO);

			}
			attendanceLogList.addAll(systemAttendanceLogList);
			System.out.println("----*** FINAL LIST FOR  SAVE***-----" + attendanceLogList);
		} else if (attendanceLogDtoList.size() > 0) {
			for (AttendanceLogDTO attendanceLogObjDto : attendanceLogDtoList) {
				AttendanceLogDTO attendanceLogDto = new AttendanceLogDTO();
				attendanceLogDto.setAttendanceDate(attendanceLogObjDto.getAttendanceDate());

				// attendanceLogDto.setInTime(attendanceLogObjDto.getInTime());
				// np
				String time1 = null;
				SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
				try {
					Date date1 = sdf.parse(attendanceLogObjDto.getInTime());

					SimpleDateFormat sdf2 = new SimpleDateFormat("hh:mm:ss aa");

					time1 = sdf2.format(date1);

				} catch (ParseException e) {
					e.printStackTrace();
				}
				attendanceLogDto.setInTime(time1);

				// attendanceLogDto.setOutTime(attendanceLogObjDto.getOutTime());
				// np
				String time2 = null;
				SimpleDateFormat sd = new SimpleDateFormat("HH:mm:ss");
				try {
					Date date2 = sd.parse(attendanceLogObjDto.getOutTime());

					SimpleDateFormat sd2 = new SimpleDateFormat("hh:mm:ss aa");

					time2 = sd2.format(date2);

				} catch (ParseException e) {
					e.printStackTrace();
				}
				attendanceLogDto.setOutTime(time2);

				attendanceLogDto.setDeviceId(attendanceLogObjDto.getDeviceId());
				attendanceLogDto.setCompanyId(comapnyId);
				attendanceLogDto.setCreatedDate(new Date());
				attendanceLogDto.setMode(attendanceLogObjDto.getMode());
				attendanceLogDto.setTotalTime(attendanceLogObjDto.getTotalTime());
				attendanceLogDto.setDirection(attendanceLogObjDto.getDirection());
				if (mapEmpIdAndEmpCode.containsKey(attendanceLogObjDto.getEmployeeCode())) {
					Long employeeId = (Long) mapEmpIdAndEmpCode.get(attendanceLogObjDto.getEmployeeCode());
					attendanceLogDto.setEmployeeId(employeeId);
					attendanceLogDto.setEmployeeCode(attendanceLogObjDto.getEmployeeCode());
					attendanceLogList.add(attendanceLogDto);
				}

			}

		} else {
			for (AttendanceLogDTO attendanceObjLogDto : systemAttendanceLogList) {
				AttendanceLogDTO attendanceLogDto = new AttendanceLogDTO();
				attendanceLogDto.setAttendanceDate(attendanceObjLogDto.getAttendanceDate());

				// attendanceLogDto.setInTime(attendanceObjLogDto.getInTime());
				// np
				String time1 = null;
				SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
				try {
					Date date1 = sdf.parse(attendanceObjLogDto.getInTime());

					SimpleDateFormat sdf2 = new SimpleDateFormat("hh:mm:ss aa");

					time1 = sdf2.format(date1);

				} catch (ParseException e) {
					e.printStackTrace();
				}
				attendanceLogDto.setInTime(time1);

				// attendanceLogDto.setOutTime(attendanceObjLogDto.getOutTime());
				// np
				String time2 = null;
				SimpleDateFormat sd = new SimpleDateFormat("HH:mm:ss");
				try {
					Date date2 = sd.parse(attendanceObjLogDto.getOutTime());

					SimpleDateFormat sd2 = new SimpleDateFormat("hh:mm:ss aa");

					time2 = sd2.format(date2);

				} catch (ParseException e) {
					e.printStackTrace();
				}
				attendanceLogDto.setOutTime(time2);

				attendanceLogDto.setCompanyId(comapnyId);
				attendanceLogDto.setDeviceId(attendanceObjLogDto.getDeviceId());
				attendanceLogDto.setCreatedDate(new Date());
				attendanceLogDto.setTotalTime(attendanceObjLogDto.getTotalTime());
				attendanceLogDto.setMode(attendanceObjLogDto.getMode());
				attendanceLogDto.setDirection(attendanceObjLogDto.getDirection());
				if (mapEmpIdAndEmpCode.containsKey(attendanceObjLogDto.getEmployeeCode())) {
					Long employeeId = (Long) mapEmpIdAndEmpCode.get(attendanceObjLogDto.getEmployeeCode());
					attendanceLogDto.setEmployeeId(employeeId);
					attendanceLogDto.setEmployeeCode(attendanceObjLogDto.getEmployeeCode());
					attendanceLogList.add(attendanceLogDto);
				}

			}
		}

		return attendanceLogList;
	}

	public List<AttendanceLogDTO> attendanceCalculationForfirstAttendanceList(
			List<AttendanceLogDTO> epushAttendanceLogDtoList, List<AttendanceLogDTO> systemAttendanceLogList,
			Map<String, EmployeeDTO> mapEmpIdAndEmpCode, Long comapnyId) {
		List<AttendanceLogDTO> attendanceLogList = new ArrayList<AttendanceLogDTO>();
		if ((epushAttendanceLogDtoList.size() > 0) && (systemAttendanceLogList.size() > 0)) {
			Collections.sort(epushAttendanceLogDtoList);
			Collections.sort(systemAttendanceLogList);

			for (AttendanceLogDTO attendanceLogDTO : epushAttendanceLogDtoList) {
				int iStringSearch = Collections.binarySearch(systemAttendanceLogList, attendanceLogDTO);
				if (iStringSearch >= 0) {

					// List<AttendanceLogDTO> att = attendanceLogDtoList.stream(). filter(p ->
					// p.getEmployeeCode() .equals(systemAttendanceLogDTO.getEmployeeCode())).
					// collect(Collectors.toCollection(() -> new ArrayList<AttendanceLogDTO>()));
					// System.out.println("att..."+att);
					AttendanceLogDTO systemAttendanceLogDto = systemAttendanceLogList.get(iStringSearch);
					if (attendanceLogDTO.getInTime().compareTo(systemAttendanceLogDto.getInTime()) <= 0) {

						 attendanceLogDTO.setInTime(attendanceLogDTO.getInTime());
						/*// np
						String time1 = null;
						SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
						try {
							Date date1 = sdf.parse(attendanceLogDTO.getInTime());

							SimpleDateFormat sdf2 = new SimpleDateFormat("hh:mm:ss aa");

							time1 = sdf2.format(date1);

						} catch (ParseException e) {
							e.printStackTrace();
						}
						attendanceLogDTO.setInTime(time1);*/

						attendanceLogDTO.setMode(attendanceLogDTO.getMode());
						attendanceLogDTO.setLatitude(attendanceLogDTO.getLatitude());
						attendanceLogDTO.setLongitude(attendanceLogDTO.getLongitude());
						attendanceLogDTO.setAddress(attendanceLogDTO.getAddress());
					} else {

						attendanceLogDTO.setInTime(systemAttendanceLogDto.getInTime());
					/*	// np
						String time1 = null;
						SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
						try {
							Date date1 = sdf.parse(systemAttendanceLogDto.getInTime());

							SimpleDateFormat sdf2 = new SimpleDateFormat("hh:mm:ss aa");

							time1 = sdf2.format(date1);

						} catch (ParseException e) {
							e.printStackTrace();
						}
						attendanceLogDTO.setInTime(time1);*/

						attendanceLogDTO.setMode(systemAttendanceLogDto.getMode());
						attendanceLogDTO.setLatitude(systemAttendanceLogDto.getLatitude());
						attendanceLogDTO.setLongitude(systemAttendanceLogDto.getLongitude());
						attendanceLogDTO.setAddress(systemAttendanceLogDto.getAddress());
						attendanceLogDTO.setAttendanceDate(systemAttendanceLogDto.getAttendanceDate());
					}

					attendanceLogDTO.setCompanyId(comapnyId);
					attendanceLogDTO.setTotalTime(systemAttendanceLogDto.getTotalTime());

					if (mapEmpIdAndEmpCode.containsKey(attendanceLogDTO.getBiomatricId())) {
						EmployeeDTO employee = (EmployeeDTO) mapEmpIdAndEmpCode.get(attendanceLogDTO.getBiomatricId());
						attendanceLogDTO.setEmployeeId(employee.getEmployeeId());
						attendanceLogDTO.setEmployeeCode(employee.getEmployeeCode());
						attendanceLogList.add(attendanceLogDTO);
					}

					System.out.println("systemAttendanceLogDTO.setEmployeeId..." + attendanceLogDTO.getEmployeeId());

					boolean flg = systemAttendanceLogList
							.removeIf(obj -> obj.getEmployeeCode().equals(systemAttendanceLogDto.getEmployeeCode()));
					// System.out.println("flg...."+flg);

				} else
					attendanceLogList.add(attendanceLogDTO);

			}
			attendanceLogList.addAll(systemAttendanceLogList);
			System.out.println("----*** FINAL LIST FOR  SAVE***-----" + attendanceLogList);
		} else if (epushAttendanceLogDtoList.size() > 0) {
			for (AttendanceLogDTO attendanceLogObjDto : epushAttendanceLogDtoList) {
				AttendanceLogDTO attendanceLogDto = new AttendanceLogDTO();
				attendanceLogDto.setAttendanceDate(attendanceLogObjDto.getAttendanceDate());

				 attendanceLogDto.setInTime(attendanceLogObjDto.getInTime());
				// np
				/*String time1 = null;
				SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
				try {
					Date date1 = sdf.parse(attendanceLogObjDto.getInTime());

					SimpleDateFormat sdf2 = new SimpleDateFormat("hh:mm:ss aa");

					time1 = sdf2.format(date1);

				} catch (ParseException e) {
					e.printStackTrace();
				}
				attendanceLogDto.setInTime(time1);*/

				 attendanceLogDto.setOutTime(attendanceLogObjDto.getOutTime());
				// np
				/*String time2 = null;
				SimpleDateFormat sd = new SimpleDateFormat("HH:mm:ss");
				try {
					Date date2 = sd.parse(attendanceLogObjDto.getOutTime());

					SimpleDateFormat sd2 = new SimpleDateFormat("hh:mm:ss aa");

					time2 = sd2.format(date2);

				} catch (ParseException e) {
					e.printStackTrace();
				}
				attendanceLogDto.setOutTime(time2);
*/
				attendanceLogDto.setDeviceId(attendanceLogObjDto.getDeviceId());
				attendanceLogDto.setCompanyId(comapnyId);
				attendanceLogDto.setCreatedDate(new Date());
				attendanceLogDto.setMode(attendanceLogObjDto.getMode());
				attendanceLogDto.setTotalTime(attendanceLogObjDto.getTotalTime());
				attendanceLogDto.setDirection(attendanceLogObjDto.getDirection());
				/*
				 * if (mapEmpIdAndEmpCode.containsKey(attendanceLogObjDto.getEmployeeCode())) {
				 * Long employeeId = (Long)
				 * mapEmpIdAndEmpCode.get(attendanceLogObjDto.getEmployeeCode());
				 * attendanceLogDto.setEmployeeId(employeeId);
				 * attendanceLogDto.setEmployeeCode(attendanceLogObjDto.getEmployeeCode());
				 * attendanceLogList.add(attendanceLogDto); }
				 */

				if (mapEmpIdAndEmpCode.containsKey(attendanceLogObjDto.getBiomatricId())) {
					EmployeeDTO employee = (EmployeeDTO) mapEmpIdAndEmpCode.get(attendanceLogObjDto.getBiomatricId());
					attendanceLogDto.setEmployeeId(employee.getEmployeeId());
					attendanceLogDto.setEmployeeCode(employee.getEmployeeCode());
					attendanceLogList.add(attendanceLogDto);
				}

			}

		} else {
			for (AttendanceLogDTO attendanceObjLogDto : systemAttendanceLogList) {
				AttendanceLogDTO attendanceLogDto = new AttendanceLogDTO();
				attendanceLogDto.setAttendanceDate(attendanceObjLogDto.getAttendanceDate());

				 attendanceLogDto.setInTime(attendanceObjLogDto.getInTime());
				// np
				/*String time1 = null;
				SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
				try {
					Date date1 = sdf.parse(attendanceObjLogDto.getInTime());

					SimpleDateFormat sdf2 = new SimpleDateFormat("hh:mm:ss aa");

					time1 = sdf2.format(date1);

				} catch (ParseException e) {
					e.printStackTrace();
				}
				attendanceLogDto.setInTime(time1);*/

				attendanceLogDto.setOutTime(attendanceObjLogDto.getOutTime());
				// np
			/*	String time2 = null;
				SimpleDateFormat sd = new SimpleDateFormat("HH:mm:ss");
				try {
					Date date2 = sd.parse(attendanceObjLogDto.getOutTime());

					SimpleDateFormat sd2 = new SimpleDateFormat("hh:mm:ss aa");

					time2 = sd2.format(date2);

				} catch (ParseException e) {
					e.printStackTrace();
				}
				attendanceLogDto.setOutTime(time2);*/

				attendanceLogDto.setCompanyId(comapnyId);
				attendanceLogDto.setDeviceId(attendanceObjLogDto.getDeviceId());
				attendanceLogDto.setCreatedDate(new Date());
				attendanceLogDto.setTotalTime(attendanceObjLogDto.getTotalTime());
				attendanceLogDto.setMode(attendanceObjLogDto.getMode());
				attendanceLogDto.setDirection(attendanceObjLogDto.getDirection());
				attendanceLogDto.setEmployeeId(attendanceObjLogDto.getEmployeeId());
				attendanceLogDto.setEmployeeCode(attendanceObjLogDto.getEmployeeCode());
				/*
				 * if (mapEmpIdAndEmpCode.containsKey(attendanceObjLogDto.getBiomatricId())) {
				 * EmployeeDTO employee = (EmployeeDTO)
				 * mapEmpIdAndEmpCode.get(attendanceObjLogDto.getBiomatricId());
				 * attendanceLogDto.setEmployeeId(employee.getEmployeeId());
				 * attendanceLogDto.setEmployeeCode(employee.getEmployeeCode());
				 * 
				 * }
				 */
				attendanceLogList.add(attendanceLogDto);
			}
		}

		return attendanceLogList;
	}

	@Override
	public List<AttendanceLog> uiDtoToDatabaseModelList(List<AttendanceLogDTO> uiobj) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AttendanceLog uiDtoToDatabaseModel(AttendanceLogDTO uiobj) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<DeviceLogsInfo> attendanceLogDtotoDeviceInfoList(List<AttendanceLogDTO> attendanceLogList) {
		List<DeviceLogsInfo> deviceLogsInfoList = new ArrayList<DeviceLogsInfo>();
		for (AttendanceLogDTO attendanceLogDto : attendanceLogList) {
			DeviceLogsInfo deviceLogsInfo = new DeviceLogsInfo();
			deviceLogsInfo.setDeviceId(attendanceLogDto.getDeviceId());
			deviceLogsInfo.setLogDate(attendanceLogDto.getAttendanceDate());
			deviceLogsInfo.setDirection(attendanceLogDto.getDirection());
			deviceLogsInfo.setUserId(attendanceLogDto.getEmployeeCode());
			deviceLogsInfo.setMode(attendanceLogDto.getMode());
			deviceLogsInfo.setLatitude(attendanceLogDto.getLatitude());
			deviceLogsInfo.setLongitude(attendanceLogDto.getLongitude());
			deviceLogsInfo.setAddress(attendanceLogDto.getAddress());
			deviceLogsInfoList.add(deviceLogsInfo);
		}
		return deviceLogsInfoList;

	}

	///boimatric Id testing
	public List<AttendanceLogDTO>  attendanceCalculation1(List<AttendanceLogDTO> epushAttendanceLogDtoList,List<AttendanceLogDTO> systemAttendanceLogList, Map<String, EmployeeDTO> mapEmpIdAndEmpCode,Long comapnyId){
	List<AttendanceLogDTO> attendanceLogList= new ArrayList<AttendanceLogDTO>();
	if((epushAttendanceLogDtoList.size()>0)&&(systemAttendanceLogList.size()>0)){
	Collections.sort(epushAttendanceLogDtoList);
	Collections.sort(systemAttendanceLogList);


	for (AttendanceLogDTO attendanceLogDTO : epushAttendanceLogDtoList) {
	int iStringSearch = Collections.binarySearch(systemAttendanceLogList, attendanceLogDTO);
	if(iStringSearch>=0) {

	// List<AttendanceLogDTO> att =  attendanceLogDtoList.stream(). filter(p -> p.getEmployeeCode() .equals(systemAttendanceLogDTO.getEmployeeCode())). collect(Collectors.toCollection(() -> new ArrayList<AttendanceLogDTO>()));
	// System.out.println("att..."+att);
	AttendanceLogDTO systemAttendanceLogDto = systemAttendanceLogList.get(iStringSearch);
	if(attendanceLogDTO.getInTime().compareTo(systemAttendanceLogDto.getInTime())<=0) {
	attendanceLogDTO.setInTime(attendanceLogDTO.getInTime());
	attendanceLogDTO.setMode(attendanceLogDTO.getMode());
	attendanceLogDTO.setLatitude(attendanceLogDTO.getLatitude());
	attendanceLogDTO.setLongitude(attendanceLogDTO.getLongitude());
	attendanceLogDTO.setAddress(attendanceLogDTO.getAddress());
	}
	else {
	attendanceLogDTO.setInTime(systemAttendanceLogDto.getInTime());
	attendanceLogDTO.setLatitude(systemAttendanceLogDto.getLatitude());
	attendanceLogDTO.setLongitude(systemAttendanceLogDto.getLongitude());
	attendanceLogDTO.setAddress(systemAttendanceLogDto.getAddress());
	attendanceLogDTO.setMode(systemAttendanceLogDto.getMode());
	attendanceLogDTO.setAttendanceDate(systemAttendanceLogDto.getAttendanceDate());
	}
	if(attendanceLogDTO.getOutTime().compareTo(systemAttendanceLogDto.getOutTime())>=0) {
	attendanceLogDTO.setOutTime(attendanceLogDTO.getOutTime());
	attendanceLogDTO.setOutTimeLangitude(attendanceLogDTO.getOutTimeLangitude());
	attendanceLogDTO.setOutTimeLatitude(attendanceLogDTO.getOutTimeLatitude());

	}
	else {
	attendanceLogDTO.setOutTime(systemAttendanceLogDto.getOutTime());
	attendanceLogDTO.setOutTimeLangitude(systemAttendanceLogDto.getOutTimeLangitude());
	attendanceLogDTO.setOutTimeLatitude(systemAttendanceLogDto.getOutTimeLatitude());
	}
	attendanceLogDTO.setCompanyId(comapnyId);
	attendanceLogDTO.setTotalTime(systemAttendanceLogDto.getTotalTime());

	if (mapEmpIdAndEmpCode.containsKey(attendanceLogDTO.getBiomatricId())) {
	EmployeeDTO employee = (EmployeeDTO) mapEmpIdAndEmpCode.get(attendanceLogDTO.getBiomatricId());
	attendanceLogDTO.setEmployeeId(employee.getEmployeeId());
	attendanceLogDTO.setEmployeeCode(employee.getEmployeeCode());
	attendanceLogList.add(attendanceLogDTO);
	}

	System.out.println("systemAttendanceLogDTO.setEmployeeId..."+attendanceLogDTO.getEmployeeId());



	boolean flg= systemAttendanceLogList.removeIf(obj -> obj.getEmployeeCode().equals(systemAttendanceLogDto.getEmployeeCode()));
	// System.out.println("flg...."+flg);

	}
	else
	attendanceLogList.add(attendanceLogDTO) ;


	}
	attendanceLogList.addAll(systemAttendanceLogList);
	System.out.println("----*** FINAL LIST FOR  SAVE***-----"+attendanceLogList);
	}
	else if(epushAttendanceLogDtoList.size()>0) {
	for (AttendanceLogDTO attendanceLogObjDto : epushAttendanceLogDtoList) {
	AttendanceLogDTO attendanceLogDto = new AttendanceLogDTO();
	attendanceLogDto.setAttendanceDate(attendanceLogObjDto.getAttendanceDate());
	attendanceLogDto.setInTime(attendanceLogObjDto.getInTime());
	attendanceLogDto.setOutTime(attendanceLogObjDto.getOutTime());
	attendanceLogDto.setDeviceId(attendanceLogObjDto.getDeviceId());
	attendanceLogDto.setCompanyId(comapnyId);
	attendanceLogDto.setCreatedDate(new Date());
	attendanceLogDto.setMode(attendanceLogObjDto.getMode());
	attendanceLogDto.setTotalTime(attendanceLogObjDto.getTotalTime());
	attendanceLogDto.setDirection(attendanceLogObjDto.getDirection());
	/* if (mapEmpIdAndEmpCode.containsKey(attendanceLogObjDto.getEmployeeCode())) {
	Long employeeId = (Long) mapEmpIdAndEmpCode.get(attendanceLogObjDto.getEmployeeCode());
	attendanceLogDto.setEmployeeId(employeeId);
	attendanceLogDto.setEmployeeCode(attendanceLogObjDto.getEmployeeCode());
	attendanceLogList.add(attendanceLogDto);
	}*/

	if (mapEmpIdAndEmpCode.containsKey(attendanceLogObjDto.getBiomatricId())) {
	EmployeeDTO employee = (EmployeeDTO) mapEmpIdAndEmpCode.get(attendanceLogObjDto.getBiomatricId());
	attendanceLogDto.setEmployeeId(employee.getEmployeeId());
	attendanceLogDto.setEmployeeCode(employee.getEmployeeCode());
	attendanceLogList.add(attendanceLogDto);
	}

	}


	}
	else {
	for (AttendanceLogDTO attendanceObjLogDto : systemAttendanceLogList) {
	AttendanceLogDTO attendanceLogDto = new AttendanceLogDTO();
	attendanceLogDto.setAttendanceDate(attendanceObjLogDto.getAttendanceDate());
	attendanceLogDto.setInTime(attendanceObjLogDto.getInTime());
	attendanceLogDto.setOutTime(attendanceObjLogDto.getOutTime());
	attendanceLogDto.setCompanyId(comapnyId);
	attendanceLogDto.setDeviceId(attendanceObjLogDto.getDeviceId());
	attendanceLogDto.setCreatedDate(new Date());
	attendanceLogDto.setTotalTime(attendanceObjLogDto.getTotalTime());
	attendanceLogDto.setMode(attendanceObjLogDto.getMode());
	attendanceLogDto.setDirection(attendanceObjLogDto.getDirection());
	attendanceLogDto.setEmployeeCode(attendanceObjLogDto.getEmployeeCode());
	attendanceLogDto.setEmployeeId(attendanceObjLogDto.getEmployeeId());

	attendanceLogDto.setLatitude(attendanceObjLogDto.getLatitude());
	attendanceLogDto.setLongitude(attendanceObjLogDto.getLongitude());
	attendanceLogDto.setAddress(attendanceObjLogDto.getAddress());
	attendanceLogDto.setOutTimeAddress(attendanceObjLogDto.getOutTimeAddress());
	attendanceLogDto.setOutTimeLangitude(attendanceObjLogDto.getOutTimeLangitude());
	attendanceLogDto.setOutTimeLatitude(attendanceObjLogDto.getOutTimeLatitude());
	/*if (mapEmpIdAndEmpCode.containsKey(attendanceObjLogDto.getBiomatricId())) {
	EmployeeDTO employee = (EmployeeDTO) mapEmpIdAndEmpCode.get(attendanceObjLogDto.getBiomatricId());
	attendanceLogDto.setEmployeeId(employee.getEmployeeId());
	attendanceLogDto.setEmployeeCode(employee.getEmployeeCode());

	}*/
	attendanceLogList.add(attendanceLogDto);
	}
	}

	return attendanceLogList;
	}
	
	
	/*
	 04-MAR-2020
	 */
	 public List<AttendanceLogDTO> unseccessSyncedDateList(List<AttendanceLog> attendanceLogList ,Date currentDate) {
		 List<AttendanceLogDTO> attendanceLogSyncedFailedList = new ArrayList<AttendanceLogDTO>();
		 
		 Calendar cal = Calendar.getInstance();
	     cal.setTime(currentDate);
	     cal.set(Calendar.MINUTE, 0);
	     cal.set(Calendar.SECOND, 0);   
	     cal.set(Calendar.HOUR_OF_DAY, 0);
	     currentDate = cal.getTime();
	     
	     cal.add(Calendar.DATE, -29);
	   	 Date previousThirtyDays = cal.getTime();
	    
	     List<AttendanceLogDTO> attendanceLogDTOList = attendanceLogList.stream().map(attendanceLog -> attendanceLogtoAttendanceLogDTO(attendanceLog)).collect(Collectors.toList());
	     List<LocalDate> betweenDateList = DateUtils.getDatesBetweenUsing(previousThirtyDays , currentDate);
	     
		
	     List<LocalDate> filteredList = betweenDateList.stream()
	                                .filter(allDates -> (attendanceLogDTOList.stream()
	                                        .filter(syncedDates -> 
	                                        syncedDates.getAttendanceDates().toString().equalsIgnoreCase(allDates.toString()
	                                        		))
	                                        .count())<1)
	                                        .collect(Collectors.toList());
	                        
	     LocalDate localDateNow  = LocalDate.now(); 
	     if(filteredList!=null) {
	     for(LocalDate localDAte :filteredList) {
	    	 AttendanceLogDTO attendanceLogDTO = new AttendanceLogDTO();
	    	 attendanceLogDTO.setAttendanceDates(localDAte);
	    	 attendanceLogDTO.setStartDates(localDAte.toString());
	    	 if(!localDateNow.equals(localDAte)) {
	    	 attendanceLogSyncedFailedList.add(attendanceLogDTO);
	    	 }
	     }
	     }
	     return attendanceLogSyncedFailedList;
	 }
	
	public AttendanceLogDTO attendanceLogtoAttendanceLogDTO(AttendanceLog attendanceLog) {
		AttendanceLogDTO attendanceLogDTO = new AttendanceLogDTO();
		attendanceLogDTO.setAttendanceDate(attendanceLog.getAttendanceDate());
		attendanceLogDTO.setAttendanceDates(DateUtils.toLocalDate(attendanceLog.getAttendanceDate()));
		
		return attendanceLogDTO;
	}
	
}
