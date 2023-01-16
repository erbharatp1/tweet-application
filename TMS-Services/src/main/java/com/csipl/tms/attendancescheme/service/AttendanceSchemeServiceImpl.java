package com.csipl.tms.attendancescheme.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.csipl.tms.attendancescheme.repository.AttendanceSchemeRepository;
import com.csipl.tms.attendancetypetxnhistory.repository.AttendanceTypeTxnHistoryRepository;
import com.csipl.tms.model.attendancescheme.AttendanceScheme;
import com.csipl.tms.model.attendancetypetransaction.AttendanceTypeTransaction;
import com.csipl.tms.model.attendancetypetransaction.AttendanceTypeTxnHistory;
import com.csipl.tms.model.latlonglocation.AttendanceLocationMapping;

@Service("attendanceSchemeService")
@Transactional
public class AttendanceSchemeServiceImpl implements AttendanceSchemeService{

	@Autowired
	private AttendanceSchemeRepository attendanceSchemeRepository;
	
	@Autowired
	AttendanceTypeTxnHistoryRepository attendanceTypeTxnHistoryRepository;
	
	@Override
	public void saveAttendanceScheme(AttendanceScheme attendanceScheme) {
		AttendanceScheme attendanceScheme2 =attendanceSchemeRepository.save(attendanceScheme);
		saveTxnHistroy(attendanceScheme2);
	}
	
	@Override
	public List<AttendanceScheme> allAttendanceSchemes() {
		return attendanceSchemeRepository.allAttendanceSchemes();
	}

	@Override
	public AttendanceScheme attendanceSchemeById(Long attendanceSchemeId) {
		return attendanceSchemeRepository.attendanceSchemeById(attendanceSchemeId);
	}

	@Override
	public List<AttendanceScheme> findSchemesByName(String schemeName) {
		return attendanceSchemeRepository.findSchemesByName(schemeName);
	}

	@Override
	public List<AttendanceScheme> activeAttendanceSchemes() {
		return attendanceSchemeRepository.activeAttendanceSchemes();
	}

	public void saveTxnHistroy(AttendanceScheme attendanceScheme2) {
		AttendanceTypeTxnHistory attendanceTypeTxnHistory = new AttendanceTypeTxnHistory();
		attendanceTypeTxnHistory.setAttendanceSchemeId(attendanceScheme2.getAttendanceSchemeId());
		attendanceTypeTxnHistory.setCreatedBy(attendanceScheme2.getCreatedBy());
		attendanceTypeTxnHistory.setActiveStatus(attendanceScheme2.getActiveStatus());
		attendanceTypeTxnHistory.setCreatedDate(new Date());
		List<AttendanceTypeTransaction> attendanceTypeTransaction = attendanceScheme2.getAttendanceTypeTransactions();
		if(attendanceTypeTransaction != null) {
			String transectionIds ="";
		for(AttendanceTypeTransaction attendanceTypeTransactionDTO: attendanceTypeTransaction) {
			transectionIds = transectionIds  + "," +attendanceTypeTransactionDTO.getAttendanceTypeTransactionId();
		}
		attendanceTypeTxnHistory.setAttendanceTypeId(transectionIds);
		}
		
		List<AttendanceLocationMapping> attendanceLocationMapping = attendanceScheme2.getAttendanceLocationMappings();
		if(attendanceLocationMapping!=null) {
			String LocationMappingIds ="";
		for(AttendanceLocationMapping attendanceLocationMappingDto: attendanceLocationMapping) {
			LocationMappingIds = LocationMappingIds + "," + attendanceLocationMappingDto.getAttendanceLocationId();
		}
		attendanceTypeTxnHistory.setLocationId(LocationMappingIds);
		}
		
		attendanceTypeTxnHistoryRepository.save(attendanceTypeTxnHistory);
	}

	
}
