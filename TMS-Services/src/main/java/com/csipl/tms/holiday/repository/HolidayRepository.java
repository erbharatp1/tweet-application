package com.csipl.tms.holiday.repository;
import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.csipl.hrms.common.constant.StatusMessage;
import com.csipl.tms.dto.holiday.HolidayDTO;
import com.csipl.tms.model.holiday.TMSHolidays;

public interface HolidayRepository extends CrudRepository <TMSHolidays, Long> {
	//@Query(" from TMSHolidays where companyId =?1 AND activeStatus ='"+StatusMessage.ACTIVE_CODE+"'")
//	@Query(" from TMSHolidays where companyId =?1  and year=year(Now()) ORDER BY  holidayId  DESC ")
   // public List<TMSHolidays> findAllHoliday(Long companyId);
	String findAllHoliday="SELECT hd.holidayId,hd.holidayName,hd.companyId,hd.fromDate ,hd.toDate ,hd.day from TMSHolidays hd "
			+ " JOIN TMSLeavePeriod  lp on hd.leavePeriodId = lp.leavePeriodId AND lp.activeStatus='AC'"
			+ " where hd.companyId =?1 AND hd.activeStatus ='AC' ORDER BY hd.fromDate ASC ";
	@Query(value=findAllHoliday,nativeQuery = true)
	public List<Object[]> findAllHoliday( Long companyId);
	
	@Query(" from TMSHolidays where holidayId=?1 ORDER BY  holidayId  DESC ")
    public TMSHolidays findHoliday( Long holidayId);
	
	String holidaycount="SELECT \r\n" + 
			"CASE\r\n" + 
			"WHEN  Month(tm.fromDate)=Month(Now())\r\n" + 
			"THEN (SELECT SUM(fun_calculate_days_fromdate_todate(tm.fromDate,tm.toDate)))\r\n" + 
			"\r\n" + 
			"ELSE\r\n" + 
			"(SELECT SUM(day(toDate)))\r\n" + 
			"\r\n" + 
			"END \r\n" + 
			"\r\n" + 
			"FROM TMSHolidays tm WHERE companyId=?1 and year=year(Now()) and Month(fromDate)=Month(Now()) or Month(toDate)=Month(Now())";
	
	@Query(value=holidaycount,nativeQuery = true)
	public  BigDecimal holidaycount( Long companyId);
	
	@Query(" FROM TMSHolidays  WHERE companyId=?1  AND MONTH(fromDate)=MONTH(NOW()) OR MONTH(toDate)=MONTH(NOW()) AND year=YEAR(NOW())")
	public List<TMSHolidays> findMonthlyHolidayList(Long companyId);
	
	@Query(" from TMSHolidays where leavePeriodId =?1  ")
    public List<TMSHolidays> findAllLeavePeriod(Long leavePeriodId);
	
	
	@Query(" from TMSHolidays where leavePeriodId =?1 and activeStatus='AC' ORDER BY fromDate ASC ")
    public List<TMSHolidays> findAllHolidayView(Long leavePeriodId);
}

