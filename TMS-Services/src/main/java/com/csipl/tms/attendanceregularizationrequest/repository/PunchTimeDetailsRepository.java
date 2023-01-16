package com.csipl.tms.attendanceregularizationrequest.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.csipl.tms.model.attendanceregularizationrequest.PunchTimeDetail;

public interface PunchTimeDetailsRepository extends CrudRepository<PunchTimeDetail, Long>{
	@Query("from PunchTimeDetail where tktNo=?1 AND companyId=?2 AND in_out =?3 AND date=?4")
	List<PunchTimeDetail> findAllPunchTime( String employeeCode,Long companyId,String InOut,Date date);
	
//	String getEmpAttendaceList ="SELECT MIN(sno), MAX(sno),MIN(time) ,MAX(time),tktno ,date ,companyId, flag ,latitude, longitude , address,time  FROM PunchTimeDetail WHERE cast(date as date)=?2 AND companyId=?1 GROUP BY tktno";
	
	String getEmpAttendaceList = "SELECT  pd.minSno, pd.maxSno,pd.mintime,pd.mtime , pd.tktno ,pd.date ,pd.companyId, pd.flag ,  pd.latitude,"
			+ " pd.longitude, pd.address, pd.time, p.latitude as outTimelatitude, p.longitude as outTimelongitude, p.address as addr "
			+ " FROM   PunchTimeDetail p INNER JOIN "
			+ " (SELECT   MAX(punchTimeDetailsId) as pId, latitude as latitude, longitude as longitude, tktno tn, address ,"
			+ " MIN(sno) as minSno , MAX( sno) as maxSno,MIN( time) mintime,MAX( time) mtime, tktno , date as date, companyId as companyId,  flag  as flag, time "
			+ "    FROM PunchTimeDetail WHERE cast( date as date)=?2     GROUP BY  tktno )"
			+ "  pd ON pd.pId=p.punchTimeDetailsId and pd.tn=p.tktno WHERE p.companyId=?1  GROUP BY p.tktno";
	@Query(value=getEmpAttendaceList,nativeQuery = true)
	List<Object[]> getEmpAttendaceList(Long companyId ,String date);
	
//	@Query("select  MAX(time),in_out from PunchTimeDetail WHERE date = CURDATE() and tktNo =?1 and companyId=?2")
//	 public PunchTimeDetail findMaxTime(String employeeCode,Long companyId);
	
   String FIND_MAX_TIME="select MAX(pd.time) as time, pd.in_out \r\n" + 
   		"from PunchTimeDetail pd \r\n" + 
   		"WHERE ( (CAST(pd.date AS DATE) = CURDATE()) and (pd.tktNo=?1) ) and pd.companyId=?2\r\n" + 
   		"and pd.time= (select MAX(pt.time) from PunchTimeDetail pt WHERE CAST(date AS DATE) = CURDATE() and pt.tktNo=?1)";	
//	String findMaxTime="select MAX(pd.time) as time, pd.in_out from PunchTimeDetail pd WHERE CAST(pd.date AS DATE) = CURDATE() and pd.tktNo=?1 and pd.companyId=?2 and pd.time =(select MAX(pt.time) from PunchTimeDetail pt WHERE CAST(date AS DATE) = CURDATE())";
	@Query(value=FIND_MAX_TIME,nativeQuery = true)
	 public List<Object[]> findMaxTime(String employeeCode,Long companyId);
	
	//@Query("DISTINCT(tktNo) from PunchTimeDetail where  companyId=?1 AND date=?2")
	//List<PunchTimeDetail> findEmployeeCodeList( Long companyId,Date date);
}
