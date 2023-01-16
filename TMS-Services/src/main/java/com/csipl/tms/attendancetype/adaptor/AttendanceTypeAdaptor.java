package com.csipl.tms.attendancetype.adaptor;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;
import com.csipl.tms.dto.attendancetype.AttendanceTypeDTO;
import com.csipl.tms.model.attendancetype.AttendanceType;

@Component
public class AttendanceTypeAdaptor {

	public synchronized List<AttendanceTypeDTO> modelToUiDtoList(List<AttendanceType> attendanceType) {
		return attendanceType.stream().map(item -> modelToUiDto(item)).collect(Collectors.toList());
	}
	

	public synchronized AttendanceTypeDTO modelToUiDto(AttendanceType attendanceType) {
		AttendanceTypeDTO attendanceTypeDTO = new AttendanceTypeDTO();
		attendanceTypeDTO.setAttendanceTypeId(attendanceType.getAttendanceTypeId());
		attendanceTypeDTO.setCreatedBy(attendanceType.getCreatedBy());
		attendanceTypeDTO.setCreatedDate(attendanceType.getCreatedDate());
		attendanceTypeDTO.setTypeName(attendanceType.getTypeName());
		attendanceTypeDTO.setTypeCode(attendanceType.getTypeCode());
		return attendanceTypeDTO;
	}
	
}
