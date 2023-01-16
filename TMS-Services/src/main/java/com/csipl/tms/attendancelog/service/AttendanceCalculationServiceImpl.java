package com.csipl.tms.attendancelog.service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Transactional
@Service("attendanceCalculationService")
public class AttendanceCalculationServiceImpl implements AttendanceCalculationService {

	@PersistenceContext
	@Autowired
	private EntityManager em = null;

	private static final Logger logger = LoggerFactory.getLogger(AttendanceCalculationServiceImpl.class);

	/*
	 * @SuppressWarnings("unchecked") public List<Object[]>
	 * getAllPunchDetails(Object[] object) {
	 * 
	 * String deviceName = (String) object[2]; logger.info("deviceName------>" +
	 * deviceName); DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	 * final Calendar cal = Calendar.getInstance(); cal.add(Calendar.DATE, -10);
	 * 
	 * String miDate = new
	 * StringBuffer(dateFormat.format(cal.getTime())).append(" 00:00:00.000").
	 * toString(); String mxDate = new
	 * StringBuffer(dateFormat.format(cal.getTime())).append(" 23:59:59.000").
	 * toString(); logger.info("miDate------>" + miDate + " ----- " +
	 * "mxDate------>" + mxDate); logger.info("Previous date ----->" +
	 * cal.getTime());
	 * 
	 * String query =
	 * "SELECT MIN(sno) as mintime, MIN(date)as mindate, MAX(sno)as maxtime, MAX(date)as maxdate ,DATEDIFF(n,MIN(date),MAX(date) ) AS MinsWorked,tktno ,flag, d.DeviceId FROM PunchTimeDetails p join Devices d ON d.DeviceSName=p.flag WHERE date >= ?1 AND date <= ?2  AND flag IN ("
	 * + deviceName + ") GROUP BY tktno,flag,d.DeviceId"; Query nativeQuery =
	 * em.createNativeQuery(query); nativeQuery.setParameter(1,
	 * miDate).setParameter(2, mxDate); final List<Object[]> resultList =
	 * nativeQuery.getResultList(); logger.info("resultList size------>" +
	 * resultList.size()); return resultList; }
	 */
	@Override
	public List<Object[]> getAttendanceFromEpush(List<String> serialNoList) {

		// String query = "SELECT MIN(sno) as mintime, MIN(date)as mindate, MAX(sno)as
		// maxtime, MAX(date)as maxdate ,DATEDIFF(n,MIN(date),MAX(date) ) AS
		// MinsWorked,tktno ,flag, d.DeviceId FROM PunchTimeDetails p join Devices d ON
		// d.DeviceSName=p.flag WHERE date >= ?1 AND date <= ?2 AND flag IN ("
		// + deviceName + ") GROUP BY tktno,flag,d.DeviceId";

		Date currentDate = new Date();
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String date = dateFormat.format(currentDate);
		// String query = "SELECT MIN(cast(log.LogDate as time) )as
		// minTime,MAX(cast(log.LogDate as time) )as maxTime ,log.UserId ,log.LogDate
		// ,TIMEDIFF(MAX(cast(log.LogDate as time)),MIN(cast(log.LogDate as time)) ) AS
		// MinsWorked FROM epushserver.Devices dv left JOIN
		// epushserver.DeviceLogs_Processed log ON log.DeviceId = dv.DeviceId WHERE
		// dv.SerialNumber IN ('AEVB181660117') AND cast(log.LogDate as date) =?1 group
		// by log.UserId";
		String query = "SELECT MIN(cast(log.LogDate as time) )as minTime,MAX(cast(log.LogDate as time) )as maxTime ,log.UserId ,log.LogDate ,TIMEDIFF(MAX(cast(log.LogDate as time)),MIN(cast(log.LogDate as time)) ) AS MinsWorked FROM epushserver.Devices dv left JOIN epushserver.DeviceLogs_Processed log ON log.DeviceId = dv.DeviceId  WHERE dv.SerialNumber IN :serialNoList AND cast(log.LogDate as date) =?1 group by log.UserId";
		System.out.println("Query...." + query);

		Query nativeQuery = em.createNativeQuery(query);
		Optional<Query> op = Optional.of(nativeQuery);
		// nativeQuery.setParameter(1, miDate).setParameter(2, mxDate);
		nativeQuery.setParameter("serialNoList", serialNoList);
		nativeQuery.setParameter(1, date);
		// nativeQuery.setParameter(1, serialNoList);
		List<Object[]> resultList = null;
		if (nativeQuery.getMaxResults() > 0)
			resultList = nativeQuery.getResultList();
		logger.info("resultList size------>" + resultList.size());

		// final List<Object[]> resultList = nativeQuery.getResultList();
		logger.info("resultList size------>" + resultList.size());
		return resultList;

	}

	@Override
	public List<Object[]> getAttendanceFromEpushViaDate(List<String> serialNoList ,Date currentDate) {

		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String date = dateFormat.format(currentDate);
		
		String query = "SELECT MIN(cast(log.LogDate as time) )as minTime,MAX(cast(log.LogDate as time) )as maxTime ,log.UserId ,log.LogDate ,TIMEDIFF(MAX(cast(log.LogDate as time)),MIN(cast(log.LogDate as time)) ) AS MinsWorked FROM epushserver.Devices dv left JOIN epushserver.DeviceLogs_Processed log ON log.DeviceId = dv.DeviceId  WHERE dv.SerialNumber IN :serialNoList AND cast(log.LogDate as date) =?1 group by log.UserId";
		
		Query nativeQuery = em.createNativeQuery(query);
		Optional<Query> op = Optional.of(nativeQuery);
		// nativeQuery.setParameter(1, miDate).setParameter(2, mxDate);
		nativeQuery.setParameter("serialNoList", serialNoList);
		nativeQuery.setParameter(1, date);
		// nativeQuery.setParameter(1, serialNoList);
		List<Object[]> resultList = new ArrayList<Object[]>();
		if (nativeQuery.getMaxResults() > 0)
			resultList = nativeQuery.getResultList();
		
		logger.info("resultList size------>" + resultList.size());
		return resultList;

	}
	
	@Override
	public List<Object[]> getFirstAttendanceLogFromEpush(List<String> serialNoList) {
		Date currentDate = new Date();
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String date = dateFormat.format(currentDate);
		String query = "SELECT dv.DeviceId ,log.UserId,log.LogDate,log.Direction, min(cast(log.LogDate as time))  FROM epushserver.Devices dv left JOIN epushserver.DeviceLogs_Processed log ON log.DeviceId = dv.DeviceId  WHERE dv.SerialNumber IN :serialNoList AND cast(log.LogDate as date) =?1 group by log.UserId";

		Query nativeQuery = em.createNativeQuery(query);
		// nativeQuery.setParameter(1, miDate).setParameter(2, mxDate);
		nativeQuery.setParameter("serialNoList", serialNoList);
		nativeQuery.setParameter(1, date);
		System.out.println("query..." + nativeQuery);
		final List<Object[]> resultList = nativeQuery.getResultList();
		logger.info("resultList size------>" + resultList.size());
		return resultList;

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> getCheckInAttendanceFromEpush(List<String> serialNoList, String boimetricId) {

		String bioId = boimetricId;
		String query = "SELECT log.UserId,cast(log.LogDate as date), cast(log.LogDate as time) FROM epushserver.Devices dv left JOIN epushserver.DeviceLogs_Processed log ON log.DeviceId = dv.DeviceId  WHERE dv.SerialNumber IN :serialNoList AND cast(log.LogDate as date)=cast(CURDATE() as date) AND log.UserId=?1 ORDER BY cast(log.LogDate as time) DESC ";

		Query nativeQuery = em.createNativeQuery(query);

		nativeQuery.setParameter("serialNoList", serialNoList);

		nativeQuery.setParameter(1, bioId);

		System.out.println("query..." + nativeQuery);
		final List<Object[]> resultList = nativeQuery.getResultList();

		return resultList;
	}

	
	public List<Object[]> getAttendanceFromEpushViaDate(List<String> serialNoList , LocalDate currentDate) {
		//DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		//String date = dateFormat.format(currentDate);
		String date = currentDate.toString();
		String query = "SELECT dv.DeviceId ,log.UserId,log.LogDate,log.Direction, min(cast(log.LogDate as time))  FROM epushserver.Devices dv left JOIN epushserver.DeviceLogs_Processed log ON log.DeviceId = dv.DeviceId  WHERE dv.SerialNumber IN :serialNoList AND cast(log.LogDate as date) =?1 group by log.UserId";

		Query nativeQuery = em.createNativeQuery(query);
		// nativeQuery.setParameter(1, miDate).setParameter(2, mxDate);
		nativeQuery.setParameter("serialNoList", serialNoList);
		nativeQuery.setParameter(1, date);
		
		final List<Object[]> resultList = nativeQuery.getResultList();
		logger.info("resultList size------>" + resultList.size());
		return resultList;

	}
}
