package com.csipl.tms.shift.adaptor;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.csipl.common.services.dropdown.DropDownCache;
import com.csipl.hrms.common.enums.DropDownEnum;
import com.csipl.hrms.service.util.ConverterUtil;
//import com.csipl.hrms.common.model.Company;
//import com.csipl.hrms.common.enums.DropDownEnum;
//import com.csipl.hrms.service.cache.DropDownCache;
import com.csipl.tms.dto.shift.ShiftDTO;
import com.csipl.tms.dto.shift.ShiftDurationDTO;
import com.csipl.tms.model.shift.Shift;
import com.csipl.tms.service.Adaptor;

public class ShiftAdaptor implements Adaptor<ShiftDTO, Shift> {

	@Override
	public List<Shift> uiDtoToDatabaseModelList(List<ShiftDTO> uiobj) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ShiftDTO> databaseModelToUiDtoList(List<Shift> shiftList) {
		List<ShiftDTO> shiftDtoList = new ArrayList<ShiftDTO>();
		for (Shift shift : shiftList) {
			shiftDtoList.add(databaseModelToUiDto(shift));
		}
		return shiftDtoList;

	}

	@Override
	public Shift uiDtoToDatabaseModel(ShiftDTO shiftDto) {
		Shift shift = new Shift();
		// Company company = new Company();
		shift.setShiftId(shiftDto.getShiftId());
		shift.setShiftFName(shiftDto.getShiftFName());
		shift.setGraceTime(shiftDto.getGraceTime());
		shift.setGraceFrqInMonth(shiftDto.getGraceFrqInMonth());
		shift.setGraceTime(shiftDto.getGraceTime());
		shift.setStartTime(shiftDto.getStartTime());
		shift.setHalfDayRuleflag(shiftDto.getHalfDayRuleflag());
		String fromtime = shiftDto.getStartTime().substring(0, 2);

		Long fromTime = Long.parseLong(fromtime);
		if (fromTime > 12) {
			shift.setFromTime(String.valueOf(fromTime - 12) + ":" + shiftDto.getStartTime().substring(3, 5));
			shift.setStartPeriod("PM");
		} else {
			shift.setStartPeriod("AM");
			shift.setFromTime(shiftDto.getStartTime());
		}

		String totime = shiftDto.getEndTime().substring(0, 2);
		String sbEnd = shiftDto.getEndTime().substring(3, 5);
		Long toTime = Long.parseLong(totime);
		if (toTime > 12) {
			shift.setToTime(String.valueOf((toTime - 12)) + ":" + sbEnd);
			shift.setEndPeriod("PM");
		} else {
			shift.setEndPeriod("AM");
			shift.setToTime(shiftDto.getEndTime());
		}
		String dateStart = "01/14/2012 " + shiftDto.getStartTime() + ":00";
		String dateEnd = "01/14/2012 " + shiftDto.getEndTime() + ":00";
		SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");

		try {
			Date d1 = format.parse(dateStart);
			Date d2 = format.parse(dateEnd);
			long diff = d2.getTime() - d1.getTime();
			long diffMinutes = diff / (60 * 1000) % 60;
			long diffHours = diff / (60 * 60 * 1000) % 24;
			shift.setShiftDuration(diffHours + "." + diffMinutes);
		} catch (Exception e) {
			e.printStackTrace();
		}
		shift.setEndTime(shiftDto.getEndTime());
		if (shiftDto.getShiftId() == null)
			shift.setCreatedDate(new Date());
		else
			shift.setCreatedDate(shiftDto.getCreatedDate());
		shift.setUserId(shiftDto.getUserId());
		shift.setUpdateUserId(shiftDto.getUpdateUserId());
		// company.setCompanyId(shiftDto.getCompanyId());
		// shift.setCompany(company);
		shift.setCompanyId(shiftDto.getCompanyId());
		shift.setActiveStatus(shiftDto.getActiveStatus());
		shift.setEffectiveDate(shiftDto.getEffectiveDate());
		return shift;
	}

	@Override
	public ShiftDTO databaseModelToUiDto(Shift shift) {
		ShiftDTO shiftDto = new ShiftDTO();
		shiftDto.setShiftId(shift.getShiftId());
		shiftDto.setShiftFName(shift.getShiftFName());
		shiftDto.setShiftDuration(shift.getShiftDuration());
		shiftDto.setStartPeriod(shift.getStartPeriod());

		// shiftDto.setStartTime(shift.getStartTime());
		// np
		String time1 = null;
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
		try {
			Date date1 = sdf.parse(shift.getStartTime());

			SimpleDateFormat sdf2 = new SimpleDateFormat("hh:mm aa");

			time1 = sdf2.format(date1);

		} catch (ParseException e) {
			e.printStackTrace();
		}
		shiftDto.setStartTime(time1);

		 shiftDto.setEndPeriod(shift.getEndPeriod());

		// shiftDto.setEndTime(shift.getEndTime());
		// np
		String time3 = null;
		SimpleDateFormat sdf2 = new SimpleDateFormat("HH:mm");
		try {
			Date date1 = sdf2.parse(shift.getEndTime());

			SimpleDateFormat sdf3 = new SimpleDateFormat("hh:mm aa");

			time3 = sdf3.format(date1);

		} catch (ParseException e) {
			e.printStackTrace();
		}
		shiftDto.setEndTime(time3);

		// shiftDto.setGraceTime(shift.getGraceTime());
		// np
		String time4 = null;
		SimpleDateFormat sdf4 = new SimpleDateFormat("HH:mm");
		try {
			Date date1 = sdf4.parse(shift.getGraceTime());

			SimpleDateFormat sdf5 = new SimpleDateFormat("hh:mm aa");

			time4 = sdf5.format(date1);

		} catch (ParseException e) {
			e.printStackTrace();
		}
		shiftDto.setGraceTime(time4);

		shiftDto.setGraceFrqInMonth(shift.getGraceFrqInMonth());
		shiftDto.setCreatedDate(shift.getCreatedDate());
		shiftDto.setUserId(shift.getUserId());
		shiftDto.setEffectiveDate(shift.getEffectiveDate());
		shiftDto.setCompanyId(shift.getCompanyId());
		shiftDto.setHalfDayRuleflag(shift.getHalfDayRuleflag());
		/*
		 * if (shift.getActiveStatus().equals("AC"))
		 * shiftDto.setActiveStatusValue("Active"); else
		 * shiftDto.setActiveStatusValue("Deactive");
		 */

		shiftDto.setActiveStatusValue(DropDownCache.getInstance()
				.getDropDownValue(DropDownEnum.Status.getDropDownName(), shift.getActiveStatus()));
		shiftDto.setActiveStatus(shift.getActiveStatus());
		return shiftDto;
	}

	/*
	 * public List<ShiftDTO> modeltoDTOList(List<Object[]> shiftList, ShiftSearchDTO
	 * shiftSearchDto) { List<ShiftDTO> shiftDTOList = new ArrayList<ShiftDTO>();
	 * for (Object[] shiftObj : shiftList) { ShiftDTO shiftDto = new ShiftDTO();
	 * Long shiftId = shiftObj[0] != null ? Long.parseLong(shiftObj[0].toString()) :
	 * null; String shiftName = shiftObj[1] != null ? (String) shiftObj[1] : null;
	 * String startTime = shiftObj[2] != null ? (String) (shiftObj[2]) : null;
	 * String endTime = shiftObj[3] != null ? (String) (shiftObj[3]) : null; String
	 * activeStatus = shiftObj[4] != null ? (String) shiftObj[4] : null; String
	 * graceTime = shiftObj[5] != null ? (String) (shiftObj[5]) : null; Long
	 * graceFreqInMonth = shiftObj[6] != null ?
	 * Long.parseLong(shiftObj[6].toString()) : null; Date effectiveDate =
	 * shiftObj[7] != null ? (Date) (shiftObj[7]) : null;
	 * shiftDto.setShiftId(shiftId); shiftDto.setShiftFName(shiftName);
	 * shiftDto.setStartTime(startTime); shiftDto.setEndTime(endTime);
	 * shiftDto.setActiveStatus(activeStatus); shiftDto.setGraceTime(graceTime);
	 * shiftDto.setGraceFrqInMonth(graceFreqInMonth);
	 * shiftDto.setEffectiveDate(effectiveDate); shiftDTOList.add(shiftDto); }
	 * return shiftDTOList; }
	 */

	public ShiftDurationDTO objectarrayToDto(List<Object[]> obj2) {
		ShiftDurationDTO shiftDTO = new ShiftDurationDTO();
		if (obj2 != null && obj2.size() > 0) {
			// '{211, COM-2180, Neeraj Chouhan, 1, General, 10:30, 19:00, 8.0}';
			// List<Object[]>
			Object[] obj = obj2.get(0);
			String employeeId = ConverterUtil.getString(obj[0]);
			String employeeCode = ConverterUtil.getString(obj[1]);
			String employeeName = ConverterUtil.getString(obj[2]);
			String shiftName = ConverterUtil.getString(obj[4]);
			String startTime = ConverterUtil.getString(obj[5]);
			String endTime = ConverterUtil.getString(obj[6]);
			String shiftDuration = ConverterUtil.getString(obj[7]);

			shiftDTO.setEmployeeId(employeeId);
			shiftDTO.setEmployeeCode(employeeCode);
			shiftDTO.setEmployeeName(employeeName);
			shiftDTO.setShiftName(shiftName);
			shiftDTO.setStartTime(startTime);
			shiftDTO.setEndTime(endTime);
			shiftDTO.setShiftDuration(shiftDuration);

		}
		return shiftDTO;
	}

}