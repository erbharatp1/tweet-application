package com.csipl.tms.attendancescheme.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.csipl.tms.model.attendancescheme.AttendanceScheme;


@Repository
public interface AttendanceSchemeRepository extends CrudRepository<AttendanceScheme, Long> {

	@Query("FROM AttendanceScheme")
	public List<AttendanceScheme> allAttendanceSchemes();
	
	@Query("FROM AttendanceScheme where activeStatus='AC'")
	public List<AttendanceScheme> activeAttendanceSchemes();
	
	@Query(" from AttendanceScheme where attendanceSchemeId=?1 ")
    public AttendanceScheme attendanceSchemeById( Long attendanceSchemeId);
	
	@Query(" from AttendanceScheme where schemeName=?1 ")
    public List<AttendanceScheme> findSchemesByName( String schemeName);
	
	public static final String ACTIVE_AR_DAY_COUNT ="SELECT ascheme.attendanceSchemeId ,emp.firstName ,emp.employeeId ,emp.userId, ascheme.activeStatus AS aschemeStatus ,ascheme.arDays,atypeTransaction.attendanceTypeId,atype.typeCode , atypeTransaction.activeStatus AS aTypeTransactionstatus " + 
			"FROM AttendanceScheme ascheme " + 
			"LEFT JOIN AttendanceTypeTransaction atypeTransaction on atypeTransaction.attendanceSchemeId = ascheme.attendanceSchemeId " + 
			"LEFT JOIN AttendanceType atype on atype.attendanceTypeId = atypeTransaction.attendanceTypeId " + 
			"Left JOIN Employee emp on emp.attendanceSchemeId = ascheme.attendanceSchemeId " + 
			"WHERE emp.employeeId =?1 AND atype.typeCode ='AR_DAYS'  AND atypeTransaction.activeStatus='AC' AND ascheme.activeStatus ='AC'";
	@Query(nativeQuery = true,value = ACTIVE_AR_DAY_COUNT)
	public List<Object[]>  arDaysSettingOfEmployee(Long employeeId);
	
	public static final String ACTIVE_ALLOW_MOBILE ="SELECT ascheme.attendanceSchemeId ,emp.firstName ,emp.employeeId ,emp.userId, ascheme.activeStatus AS aschemeStatus ,ascheme.arDays,atypeTransaction.attendanceTypeId,atype.typeCode , atypeTransaction.activeStatus AS aTypeTransactionstatus " + 
			"FROM AttendanceScheme ascheme " + 
			"LEFT JOIN AttendanceTypeTransaction atypeTransaction on atypeTransaction.attendanceSchemeId = ascheme.attendanceSchemeId " + 
			"LEFT JOIN AttendanceType atype on atype.attendanceTypeId = atypeTransaction.attendanceTypeId " + 
			"Left JOIN Employee emp on emp.attendanceSchemeId = ascheme.attendanceSchemeId " + 
			"WHERE emp.employeeId =?1 AND atype.typeCode ='AM'  AND atypeTransaction.activeStatus='DE' AND ascheme.activeStatus ='AC'";
	@Query(nativeQuery = true,value = ACTIVE_ALLOW_MOBILE)
	public List<Object[]>  allowMobileSettingOfEmployee(Long employeeId);
	
	public static final String ACTIVE_ALLOW_WEB ="SELECT ascheme.attendanceSchemeId ,emp.firstName ,emp.employeeId ,emp.userId, ascheme.activeStatus AS aschemeStatus ,ascheme.arDays,atypeTransaction.attendanceTypeId,atype.typeCode , atypeTransaction.activeStatus AS aTypeTransactionstatus " + 
			"FROM AttendanceScheme ascheme " + 
			"LEFT JOIN AttendanceTypeTransaction atypeTransaction on atypeTransaction.attendanceSchemeId = ascheme.attendanceSchemeId " + 
			"LEFT JOIN AttendanceType atype on atype.attendanceTypeId = atypeTransaction.attendanceTypeId " + 
			"Left JOIN Employee emp on emp.attendanceSchemeId = ascheme.attendanceSchemeId " + 
			"WHERE emp.employeeId =?1 AND atype.typeCode ='AW'  AND atypeTransaction.activeStatus='DE' AND ascheme.activeStatus ='AC'";
	@Query(nativeQuery = true,value = ACTIVE_ALLOW_WEB)
	public List<Object[]>  allowWebSettingOfEmployee(Long employeeId);
	
	public static final String RESTRICT_LOCATION_PREMISE ="SELECT ascheme.attendanceSchemeId ,emp.firstName ,emp.employeeId ,emp.userId, ascheme.activeStatus AS aschemeStatus ,ascheme.arDays,atypeTransaction.attendanceTypeId,atype.typeCode , atypeTransaction.activeStatus AS aTypeTransactionstatus , alm.locationId,lm.latitude , lm.longitude , lm.radius\r\n" + 
			"FROM AttendanceScheme ascheme \r\n" + 
			"LEFT JOIN AttendanceTypeTransaction atypeTransaction on atypeTransaction.attendanceSchemeId = ascheme.attendanceSchemeId\r\n" + 
			"LEFT JOIN AttendanceType atype on atype.attendanceTypeId = atypeTransaction.attendanceTypeId\r\n" + 
			"Left JOIN Employee emp on emp.attendanceSchemeId = ascheme.attendanceSchemeId\r\n" + 
			"Left JOIN AttendanceLocationMapping alm on alm.attendanceSchemeId = ascheme.attendanceSchemeId\r\n" + 
			"LEFT JOIN LocationMaster lm on alm.locationId = lm.locationId\r\n" + 
			"WHERE emp.employeeId =?1 AND atype.typeCode ='LOCATION_RANGE'  AND atypeTransaction.activeStatus='AC' AND ascheme.activeStatus ='AC'";
	@Query(nativeQuery = true,value = RESTRICT_LOCATION_PREMISE)
	public List<Object[]>  restrictLocationPremise(Long employeeId);

	
	
	
}
