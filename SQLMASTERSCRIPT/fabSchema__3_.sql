-- phpMyAdmin SQL Dump
-- version 4.5.4.1deb2ubuntu2.1
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: Oct 19, 2019 at 06:13 AM
-- Server version: 5.7.27-0ubuntu0.16.04.1
-- PHP Version: 7.0.33-0ubuntu0.16.04.6

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `fabSchema`
--

DELIMITER $$
--
-- Procedures
--
CREATE DEFINER=`root`@`localhost` PROCEDURE `pro_anniversary` (IN `p_comp_id` INT(127))  NO SQL
BEGIN

   
    DECLARE e_sqlstate 		VARCHAR(255); 
    DECLARE e_error_no 		INT(127);
    DECLARE e_error_message	TEXT;
    DECLARE e_remark		TEXT;
 
    DECLARE EXIT HANDLER FOR SQLEXCEPTION
  	BEGIN
		GET CURRENT DIAGNOSTICS CONDITION 1 e_sqlstate = RETURNED_SQLSTATE, e_error_no = MYSQL_ERRNO, e_error_message = MESSAGE_TEXT;
        
		SET	@p_process_status	=	1000;
		SET	@p_process_message	=	'Error... please Contact Administrator';
       
 	 SET	@p_process_remark	=  fun_set_exception_handling ( 2, 'pro_anniversary', e_sqlstate, e_error_no, e_error_message, e_remark);
			
	     END;
         
         
           SET @@sql_mode = 'NO_ENGINE_SUBSTITUTION';
         
                 DROP TEMPORARY TABLE IF EXISTS temp;
    CREATE TEMPORARY TABLE IF NOT EXISTS temp (
        empName varchar(50) NOT NULL,
         employeeCode varchar(10) DEFAULT NULL,departmentName  varchar(10) DEFAULT NULL,
     designationName   varchar(10) DEFAULT NULL, anniversaryDate  datetime  , empLogoPath varchar(200) NOT NULL, firstName varchar(50) NOT NULL,
	 lastName varchar(50) DEFAULT NULL);
   
      INSERT INTO temp ( empName, employeeCode,departmentName,designationName,anniversaryDate,empLogoPath,firstName,lastName)
    select concat(em.firstName,' ' ,em.lastName),em.employeeCode ,dp.departmentName,dg.designationName,em.anniversaryDate,em.employeeLogoPath,em.firstName,em.lastName
                  from 
      Employee em 
     left join PayStructureHd ph on ph.employeeId=em.employeeId and (ph.dateEnd is null or ph.dateEnd>CURRENT_DATE) and (ph.effectiveDate is NOT null and ph.effectiveDate<=CURRENT_DATE ) and ph.activeStatus='AC'
  left join Department dp on dp.departmentId=em.departmentId
     left join Designation dg on dg.designationId=em.designationId
      where 
      MONTH(em.anniversaryDate) = MONTH(NOW())  AND em.companyId=p_comp_id AND   em.activeStatus ='AC'
      AND Day(em.anniversaryDate)= day(now());   
    
     INSERT INTO temp ( empName, employeeCode,departmentName,designationName,anniversaryDate,empLogoPath,firstName,lastName)
    
   select concat(em.firstName,' ' ,em.lastName),em.employeeCode ,dp.departmentName,dg.designationName,em.anniversaryDate,em.employeeLogoPath,em.firstName,em.lastName
                  from 
      Employee em 
     left join PayStructureHd ph on ph.employeeId=em.employeeId and (ph.dateEnd is null or ph.dateEnd>CURRENT_DATE) and (ph.effectiveDate is NOT null and ph.effectiveDate<=CURRENT_DATE ) and ph.activeStatus='AC'
  left join Department dp on dp.departmentId=em.departmentId
     left join Designation dg on dg.designationId=em.designationId
      where 
      MONTH(em.anniversaryDate) = MONTH(NOW()) AND  day(em.anniversaryDate)> day(now())  AND em.companyId=p_comp_id AND   em.activeStatus ='AC'
      ORDER BY day(em.anniversaryDate) ASC, MONTH(em.anniversaryDate) DESC;

     INSERT INTO temp ( empName, employeeCode,departmentName,designationName,anniversaryDate,empLogoPath,firstName,lastName)
    
 
 select concat(em.firstName,' ' ,em.lastName),em.employeeCode ,dp.departmentName,dg.designationName,em.anniversaryDate,em.employeeLogoPath,em.firstName,em.lastName
                  from 
      Employee em 
     left join PayStructureHd ph on ph.employeeId=em.employeeId and (ph.dateEnd is null or ph.dateEnd>CURRENT_DATE) and (ph.effectiveDate is NOT null and ph.effectiveDate<=CURRENT_DATE ) and ph.activeStatus='AC'
  left join Department dp on dp.departmentId=em.departmentId
     left join Designation dg on dg.designationId=em.designationId
      where 
      MONTH(em.anniversaryDate) = MONTH(NOW()) AND  day(em.anniversaryDate)< day(now())  AND em.companyId=p_comp_id AND   em.activeStatus ='AC'
     ORDER BY day(em.anniversaryDate) DESC;
		select * from temp ;
		END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `pro_attendancelog_count_report` (IN `p_selected_date` VARCHAR(250), IN `p_employee_name` VARCHAR(250), IN `p_sorttype` VARCHAR(250), IN `p_activestatus` VARCHAR(250), IN `p_employee_code` VARCHAR(250), IN `p_offset` VARCHAR(127), IN `p_limit` VARCHAR(127), IN `p_active_value` VARCHAR(250), IN `p_company_id` VARCHAR(127), IN `p_dept_id` VARCHAR(127), IN `p_desg_id` VARCHAR(127), IN `p_title` VARCHAR(127))  NO SQL
BEGIN
    DECLARE v_offset int DEFAULT 0;
    DECLARE v_limit int DEFAULT 0;
    DECLARE v_sort varchar(50);
    DECLARE v_value varchar(50);
    set v_offset = CAST(p_offset AS UNSIGNED);
    set v_limit  = CAST(p_limit AS UNSIGNED);
    set v_value = CONCAT('e.',p_active_value);
    set @v_sort = CONCAT(v_value,' ',p_sorttype);
   
 
    SET @@sql_mode = 'NO_ENGINE_SUBSTITUTION'; 
    
     CASE 
      WHEN(   (p_selected_date!=null or p_selected_date!='') 
          AND (p_employee_name=null or p_employee_name='')  
          AND (p_sorttype!=null or p_sorttype!='')  
          AND (p_employee_code=null or p_employee_code='') 
          AND (p_offset!=null OR p_offset!='')
          AND (p_limit!=null OR p_limit!='') 
          AND  (p_active_value=null or p_active_value='') 
          AND (p_company_id!=null or p_company_id!='')  
          AND  (p_dept_id=null or p_dept_id='')  
          AND  (p_desg_id=null or p_desg_id='')
          AND  (p_title=null or p_title='') 
           
         ) THEN
   select  e.firstName,e.lastName, dg.designationName, al.inTime    ,al.outTime, al.location,al.mode,al.delayedTime,e.employeeId, al.status, al.attendanceDate,
(CASE
 when((select tl.holidayId FROM TMSHolidays tl  WHERE (p_selected_date >=tl.fromDate) AND (p_selected_date<=tl.toDate)>0)) THEN
      'Holiday'
      WHEN(tle.halfFullDay='H') Then 'Half Day Leave'
      when(tle.halfFullDay='F') THEN 'Full Day Leave'
      when((SELECT fun_check_patternIdDays_bydate(e.patternId,p_selected_date))=1)  then 'Week-Off'
      WHEN( al.status='P') Then 'Present'
      else 
     'Absent'  END )  as 'leave status' ,
    ( (SELECT fun_count_present_leave_absent	(p_company_id,'P',p_selected_date))) as presentCount,
     ( (SELECT fun_count_present_leave_absent	(p_company_id,'L',p_selected_date))) as leaveCount,
      ( (SELECT fun_count_present_leave_absent	(p_company_id,'A',p_selected_date))) as absentCount
     from Employee e
     left OUTER join AttendanceLogs al on al.employeeId=e.employeeId  and al.attendanceDate =p_selected_date and al.companyId=p_company_id
     left OUTER join TMSLeaveEntriesDatewise tle on  tle.employeeId=e.employeeId and tle.leaveStatus='APR' and tle.companyId=p_company_id
 left OUTER join Department dept  on  dept.departmentId= e.departmentId  and dept.companyId=p_company_id
     left OUTER join Designation dg  on  dg.designationId= e.designationId  and dg.companyId=p_company_id
  group by e.employeeId
    limit  v_offset ,v_limit 
  ; 
    WHEN( (p_selected_date!=null or p_selected_date!='') 
          AND (p_employee_name!=null or p_employee_name!='')  
          AND (p_sorttype!=null or p_sorttype!='')  
          AND (p_employee_code=null or p_employee_code='') 
          AND (p_offset!=null OR p_offset!='')
          AND (p_limit!=null OR p_limit!='') 
          AND (p_active_value=null or p_active_value='')        
          AND (p_company_id!=null or p_company_id!='')  
          AND (p_dept_id=null or p_dept_id='')  
          AND (p_desg_id=null or p_desg_id='')  
          AND  (p_title=null or p_title='') 
         
         ) THEN
       
  select  e.firstName,e.lastName, dg.designationName, al.inTime ,al.outTime, al.location,al.mode,al.delayedTime,e.employeeId, al.status, al.attendanceDate,
(CASE when((select tl.holidayId FROM TMSHolidays tl  WHERE (p_selected_date >=tl.fromDate) AND (p_selected_date<=tl.toDate)>0)) THEN
      'Holiday'
      WHEN(tle.halfFullDay='H') Then 'Half Day Leave'
      when(tle.halfFullDay='F') THEN 'Full Day Leave'
      when((SELECT fun_check_patternIdDays_bydate(e.patternId,p_selected_date))=1)  then 'Week-Off'
      WHEN( al.status='P') Then 'Present'
      else 
     'Absent'  END )  as 'leave status' ,
    ( (SELECT fun_count_present_leave_absent	(p_company_id,'P',p_selected_date))) as presentCount,
     ( (SELECT fun_count_present_leave_absent	(p_company_id,'L',p_selected_date))) as leaveCount,
      ( (SELECT fun_count_present_leave_absent	(p_company_id,'A',p_selected_date))) as absentCount
     from Employee e
     left OUTER join AttendanceLogs al on al.employeeId=e.employeeId  and al.attendanceDate =p_selected_date and al.companyId=p_company_id
     left OUTER join TMSLeaveEntriesDatewise tle on tle.employeeId=e.employeeId and tle.leaveStatus='APR' and tle.companyId=p_company_id
      left OUTER join Department dept  on  dept.departmentId= e.departmentId  and dept.companyId=p_company_id
 
     left OUTER join Designation dg  on  dg.designationId= e.designationId  and dg.companyId=p_company_id
 where e.firstName LIKE concat(p_employee_name, '%')
  group by e.employeeId
   limit  v_offset ,v_limit 
   ; 
   
    WHEN  (    (p_selected_date!=null or p_selected_date!='') 
           AND (p_employee_name=null or p_employee_name='')  
           AND (p_sorttype!=null or p_sorttype!='')  
           AND (p_employee_code=null or p_employee_code='') 
           AND (p_offset!=null OR p_offset!='')
           AND (p_limit!=null OR p_limit!='') 
           AND (p_active_value=null or p_active_value='')        
           AND (p_company_id!=null or p_company_id!='')  
           AND (p_dept_id!=null or p_dept_id!='')  
           AND (p_desg_id=null or p_desg_id='')  
           AND (p_title=null or p_title='') 
         ) THEN
         select  e.firstName,e.lastName, dg.designationName, al.inTime ,al.outTime, al.location,al.mode,al.delayedTime,e.employeeId, al.status, al.attendanceDate,
(CASE when((select tl.holidayId FROM TMSHolidays tl  WHERE (p_selected_date >=tl.fromDate) AND (p_selected_date<=tl.toDate)>0)) THEN
      'Holiday'
      WHEN(tle.halfFullDay='H') Then 'Half Day Leave'
      when(tle.halfFullDay='F') THEN 'Full Day Leave'
      when((SELECT fun_check_patternIdDays_bydate(e.patternId,p_selected_date))=1)  then 'Week-Off'
      WHEN( al.status='P') Then 'Present'
      else 
     'Absent'  END )  as 'leave status',
    ( (SELECT fun_count_present_leave_absent	(p_company_id,'P',p_selected_date))) as presentCount,
     ( (SELECT fun_count_present_leave_absent	(p_company_id,'L',p_selected_date))) as leaveCount,
      ( (SELECT fun_count_present_leave_absent	(p_company_id,'A',p_selected_date))) as absentCount
     from Employee e
     left OUTER join AttendanceLogs al on al.employeeId=e.employeeId  and al.attendanceDate =p_selected_date and al.companyId=p_company_id
     left OUTER join TMSLeaveEntriesDatewise tle on tle.employeeId=e.employeeId and tle.leaveStatus='APR' and tle.companyId=p_company_id
      left OUTER join Department dept  on  dept.departmentId= e.departmentId  and dept.companyId=p_company_id
 
     left OUTER join Designation dg  on  dg.designationId= e.designationId  and dg.companyId=p_company_id
 where e.departmentId=p_dept_id  
    group by e.employeeId
     limit  v_offset ,v_limit 
   ; 
   
    WHEN( (p_selected_date!=null or p_selected_date!='')  
           AND (p_employee_name=null or p_employee_name='')  
           AND (p_sorttype!=null or p_sorttype!='')  
           AND (p_employee_code!=null or p_employee_code!='') 
           AND (p_offset!=null OR p_offset!='')
           AND (p_limit!=null OR p_limit!='') 
           AND  (p_active_value=null or p_active_value='')        
           AND  (p_company_id!=null or p_company_id!='')  
           AND (p_dept_id=null or p_dept_id='')  
           AND (p_desg_id=null or p_desg_id='')  
           AND  (p_title=null or p_title='') 
         ) THEN
      
           select  e.firstName,e.lastName, dg.designationName, al.inTime ,al.outTime, al.location,al.mode,al.delayedTime,e.employeeId, al.status, al.attendanceDate,
(CASE when((select tl.holidayId FROM TMSHolidays tl  WHERE (p_selected_date >=tl.fromDate) AND (p_selected_date<=tl.toDate)>0)) THEN
      'Holiday'
      WHEN(tle.halfFullDay='H') Then 'Half Day Leave'
      when(tle.halfFullDay='F') THEN 'Full Day Leave'
      when((SELECT fun_check_patternIdDays_bydate(e.patternId,p_selected_date))=1)  then 'Week-Off'
      WHEN( al.status='P') Then 'Present'
      else 
     'Absent'  END )  as 'leave status' ,
    ( (SELECT fun_count_present_leave_absent	(p_company_id,'P',p_selected_date))) as presentCount,
     ( (SELECT fun_count_present_leave_absent	(p_company_id,'L',p_selected_date))) as leaveCount,
      ( (SELECT fun_count_present_leave_absent	(p_company_id,'A',p_selected_date))) as absentCount
     from Employee e
     left OUTER join AttendanceLogs al on al.employeeId=e.employeeId  and al.attendanceDate =p_selected_date and al.companyId=p_company_id
     left OUTER join TMSLeaveEntriesDatewise tle on tle.employeeId=e.employeeId and tle.leaveStatus='APR' and tle.companyId=p_company_id
      left OUTER join Department dept  on  dept.departmentId= e.departmentId  and dept.companyId=p_company_id
 
     left OUTER join Designation dg  on  dg.designationId= e.designationId  and dg.companyId=p_company_id
 where e.employeeCode=p_employee_code
   group by e.employeeId
   limit  v_offset ,v_limit
   
   ; 
  
   
   
   WHEN( (p_selected_date!=null or p_selected_date!='') AND
          (p_employee_name=null or p_employee_name='')  AND
          (p_sorttype!=null or p_sorttype!='') AND 
           (p_employee_code=null or p_employee_code='') 
           and (p_offset!=null OR p_offset!='')
           and (p_limit!=null OR p_limit!='') 
           and  (p_active_value=null or p_active_value='')        
         and  (p_company_id!=null or p_company_id!='')  
           and (p_dept_id=null or p_dept_id='')  
           and (p_desg_id!=null or p_desg_id!='')  
           AND (p_title=null or p_title='') 
        
         ) THEN
         
       select  e.firstName,e.lastName, dg.designationName, al.inTime ,al.outTime, al.location,al.mode,al.delayedTime,e.employeeId, al.status, al.attendanceDate,
(CASE when((select tl.holidayId FROM TMSHolidays tl  WHERE (p_selected_date >=tl.fromDate) AND (p_selected_date<=tl.toDate)>0)) THEN
      'Holiday'
      WHEN(tle.halfFullDay='H') Then 'Half Day Leave'
      when(tle.halfFullDay='F') THEN 'Full Day Leave'
      when((SELECT fun_check_patternIdDays_bydate(e.patternId,p_selected_date))=1)  then 'Week-Off'
      WHEN( al.status='P') Then 'Present'
      else 
     'Absent'  END )  as 'leave status' ,
    ( (SELECT fun_count_present_leave_absent	(p_company_id,'P',p_selected_date))) as presentCount,
     ( (SELECT fun_count_present_leave_absent	(p_company_id,'L',p_selected_date))) as leaveCount,
      ( (SELECT fun_count_present_leave_absent	(p_company_id,'A',p_selected_date))) as absentCount
     from Employee e
     left OUTER join AttendanceLogs al on al.employeeId=e.employeeId  and al.attendanceDate =p_selected_date and al.companyId=p_company_id
     left OUTER join TMSLeaveEntriesDatewise tle on tle.employeeId=e.employeeId and tle.leaveStatus='APR' and tle.companyId=p_company_id
      left OUTER join Department dept  on  dept.departmentId= e.departmentId  and dept.companyId=p_company_id
 
     left OUTER join Designation dg  on  dg.designationId= e.designationId  and dg.companyId=p_company_id
 where e.designationId=p_desg_id   
  group by e.employeeId
 limit  v_offset ,v_limit
   ; 
  
  
    WHEN( (p_selected_date!=null or p_selected_date!='') AND
          (p_employee_name=null or p_employee_name='')  AND
          (p_sorttype!=null or p_sorttype!='') AND 
           (p_employee_code=null or p_employee_code='') 
           and (p_offset!=null OR p_offset!='')
           and (p_limit!=null OR p_limit!='') 
           and  (p_active_value=null or p_active_value='')        
           and  (p_company_id!=null or p_company_id!='')  
           and (p_dept_id!=null or p_dept_id!='')  
           and (p_desg_id!=null or p_desg_id!='')  
           AND (p_title=null or p_title='') 
        
         ) THEN
         
      select  e.firstName,e.lastName, dg.designationName, al.inTime ,al.outTime, al.location,al.mode,al.delayedTime,e.employeeId, al.status, al.attendanceDate,
(CASE when((select tl.holidayId FROM TMSHolidays tl  WHERE (p_selected_date >=tl.fromDate) AND (p_selected_date<=tl.toDate)>0)) THEN
      'Holiday'
      WHEN(tle.halfFullDay='H') Then 'Half Day Leave'
      when(tle.halfFullDay='F') THEN 'Full Day Leave'
      when((SELECT fun_check_patternIdDays_bydate(e.patternId,p_selected_date))=1)  then 'Week-Off'
      WHEN( al.status='P') Then 'Present'
      else 
     'Absent'  END )  as 'leave status' ,
    ( (SELECT fun_count_present_leave_absent	(p_company_id,'P',p_selected_date))) as presentCount,
     ( (SELECT fun_count_present_leave_absent	(p_company_id,'L',p_selected_date))) as leaveCount,
      ( (SELECT fun_count_present_leave_absent	(p_company_id,'A',p_selected_date))) as absentCount
     from Employee e
     left OUTER join AttendanceLogs al on al.employeeId=e.employeeId  and al.attendanceDate =p_selected_date and al.companyId=p_company_id
     left OUTER join TMSLeaveEntriesDatewise tle on tle.employeeId=e.employeeId and tle.leaveStatus='APR' and tle.companyId=p_company_id
      left OUTER join Department dept  on  dept.departmentId= e.departmentId  and dept.companyId=p_company_id
 
     left OUTER join Designation dg  on  dg.designationId= e.designationId  and dg.companyId=p_company_id
 where e.designationId=p_desg_id  and e.departmentId  =p_dept_id
  group by e.employeeId
   limit  v_offset ,v_limit 
  
   ; 
  
  
   WHEN( (p_selected_date!=null or p_selected_date!='') AND
          (p_employee_name=null or p_employee_name='')  AND
          (p_sorttype!=null or p_sorttype!='') AND 
           (p_employee_code!=null or p_employee_code!='') 
           and (p_offset!=null OR p_offset!='')
           and (p_limit!=null OR p_limit!='') 
           and  (p_active_value=null or p_active_value='')        
           and  (p_company_id!=null or p_company_id!='')  
           and (p_dept_id!=null or p_dept_id!='')  
           and (p_desg_id!=null or p_desg_id!='')  
           AND (p_title=null or p_title='') 
        
         ) THEN
         
         select  e.firstName,e.lastName, dg.designationName, al.inTime ,al.outTime, al.location,al.mode,al.delayedTime,e.employeeId, al.status, al.attendanceDate,
(CASE when((select tl.holidayId FROM TMSHolidays tl  WHERE (p_selected_date >=tl.fromDate) AND (p_selected_date<=tl.toDate)>0)) THEN
      'Holiday'
      WHEN(tle.halfFullDay='H') Then 'Half Day Leave'
      when(tle.halfFullDay='F') THEN 'Full Day Leave'
      when((SELECT fun_check_patternIdDays_bydate(e.patternId,p_selected_date))=1)  then 'Week-Off'
      WHEN( al.status='P') Then 'Present'
      else 
     'Absent'  END )  as 'leave status' ,
    ( (SELECT fun_count_present_leave_absent	(p_company_id,'P',p_selected_date))) as presentCount,
     ( (SELECT fun_count_present_leave_absent	(p_company_id,'L',p_selected_date))) as leaveCount,
      ( (SELECT fun_count_present_leave_absent	(p_company_id,'A',p_selected_date))) as absentCount
     from Employee e
     left OUTER join AttendanceLogs al on al.employeeId=e.employeeId  and al.attendanceDate =p_selected_date and al.companyId=p_company_id
     left OUTER join TMSLeaveEntriesDatewise tle on tle.employeeId=e.employeeId and tle.leaveStatus='APR' and tle.companyId=p_company_id
      left OUTER join Department dept  on  dept.departmentId= e.departmentId  and dept.companyId=p_company_id
 
     left OUTER join Designation dg  on  dg.designationId= e.designationId  and dg.companyId=p_company_id
 where e.designationId=p_desg_id  and e.departmentId  =p_dept_id and e.employeeCode=p_employee_code
  group by e.employeeId
   limit  v_offset ,v_limit
   ; 
   
   
     WHEN( (p_selected_date!=null or p_selected_date!='') 
           AND (p_employee_name=null or p_employee_name='')  
           AND (p_sorttype!=null or p_sorttype!='')  
           AND (p_employee_code!=null or p_employee_code!='') 
           AND (p_offset!=null OR p_offset!='')
           AND (p_limit!=null OR p_limit!='') 
           AND  (p_active_value=null or p_active_value='')        
           AND  (p_company_id!=null or p_company_id!='')  
           AND (p_dept_id!=null or p_dept_id!='')  
           AND (p_desg_id=null or p_desg_id='')  
           AND (p_title=null or p_title='') 
         ) THEN
         
select  e.firstName,e.lastName, dg.designationName, al.inTime ,al.outTime, al.location,al.mode,al.delayedTime,e.employeeId, al.status, al.attendanceDate,
(CASE when((select tl.holidayId FROM TMSHolidays tl  WHERE (p_selected_date >=tl.fromDate) AND (p_selected_date<=tl.toDate)>0)) THEN
      'Holiday'
      WHEN(tle.halfFullDay='H') Then 'Half Day Leave'
      when(tle.halfFullDay='F') THEN 'Full Day Leave'
      when((SELECT fun_check_patternIdDays_bydate(e.patternId,p_selected_date))=1)  then 'Week-Off'
      WHEN( al.status='P') Then 'Present'
      else 
     'Absent'  END )  as 'leave status' ,
    ( (SELECT fun_count_present_leave_absent	(p_company_id,'P',p_selected_date))) as presentCount,
     ( (SELECT fun_count_present_leave_absent	(p_company_id,'L',p_selected_date))) as leaveCount,
      ( (SELECT fun_count_present_leave_absent	(p_company_id,'A',p_selected_date))) as absentCount
     from Employee e
     left OUTER join AttendanceLogs al on al.employeeId=e.employeeId  and al.attendanceDate =p_selected_date and al.companyId=p_company_id
     left OUTER join TMSLeaveEntriesDatewise tle on tle.employeeId=e.employeeId and tle.leaveStatus='APR' and tle.companyId=p_company_id
      left OUTER join Department dept  on  dept.departmentId= e.departmentId  and dept.companyId=p_company_id
 
     left OUTER join Designation dg  on  dg.designationId= e.designationId  and dg.companyId=p_company_id
 where  e.departmentId  =p_dept_id and e.employeeCode=p_employee_code
 group by e.employeeId
  limit  v_offset ,v_limit ; 
   
   
     WHEN( (p_selected_date!=null or p_selected_date!='') 
           AND (p_employee_name=null or p_employee_name='')  
           AND (p_sorttype!=null or p_sorttype!='')  
           AND (p_employee_code!=null or p_employee_code!='') 
           AND (p_offset!=null OR p_offset!='')
           AND (p_limit!=null OR p_limit!='') 
           AND  (p_active_value=null or p_active_value='')        
           AND  (p_company_id!=null or p_company_id!='')  
           AND (p_dept_id=null or p_dept_id='')  
           AND (p_desg_id!=null or p_desg_id!='') 
           AND (p_title=null or p_title='') 
        
         ) THEN
         
         select  e.firstName,e.lastName, dg.designationName, al.inTime ,al.outTime, al.location,al.mode,al.delayedTime,e.employeeId, al.status, al.attendanceDate,
(CASE when((select tl.holidayId FROM TMSHolidays tl  WHERE (p_selected_date >=tl.fromDate) AND (p_selected_date<=tl.toDate)>0)) THEN
      'Holiday'
      WHEN(tle.halfFullDay='H') Then 'Half Day Leave'
      when(tle.halfFullDay='F') THEN 'Full Day Leave'
      when((SELECT fun_check_patternIdDays_bydate(e.patternId,p_selected_date))=1)  then 'Week-Off'
      WHEN( al.status='P') Then 'Present'
      else 
     'Absent'  END )  as 'leave status' ,
    ( (SELECT fun_count_present_leave_absent	(p_company_id,'P',p_selected_date))) as presentCount,
     ( (SELECT fun_count_present_leave_absent	(p_company_id,'L',p_selected_date))) as leaveCount,
      ( (SELECT fun_count_present_leave_absent	(p_company_id,'A',p_selected_date))) as absentCount
     from Employee e
     left OUTER join AttendanceLogs al on al.employeeId=e.employeeId  and al.attendanceDate =p_selected_date and al.companyId=p_company_id
     left OUTER join TMSLeaveEntriesDatewise tle on tle.employeeId=e.employeeId and tle.leaveStatus='APR' and tle.companyId=p_company_id
      left OUTER join Department dept  on  dept.departmentId= e.departmentId  and dept.companyId=p_company_id
 
     left OUTER join Designation dg  on  dg.designationId= e.designationId  and dg.companyId=p_company_id
 where  e.designationId  =p_desg_id and e.employeeCode=p_employee_code
  group by e.employeeId
 limit  v_offset ,v_limit ; 
 
    WHEN( (p_selected_date!=null or p_selected_date!='') 
           AND (p_employee_name=null or p_employee_name='')  
           AND (p_sorttype!=null or p_sorttype!='')  
           AND  (p_employee_code=null or p_employee_code='') 
           AND (p_offset!=null OR p_offset!='')
           AND (p_limit!=null OR p_limit!='') 
           AND  (p_active_value!=null or p_active_value!='')        
           AND  (p_company_id!=null or p_company_id!='')  
           AND (p_dept_id=null or p_dept_id='')  
           AND (p_desg_id=null or p_desg_id='')
           AND (p_title=null or p_title='')
        
         ) THEN
        
        SET @Query1 =CONCAT("select  e.firstName,e.lastName, dg.designationName, al.inTime ,al.outTime, al.location,al.mode,al.delayedTime,e.employeeId, al.status, al.attendanceDate,
(CASE when((select tl.holidayId FROM TMSHolidays tl  WHERE (p_selected_date >=tl.fromDate) AND (p_selected_date<=tl.toDate)>0)) THEN
      'Holiday'
      WHEN(tle.halfFullDay='H') Then 'Half Day Leave'
      when(tle.halfFullDay='F') THEN 'Full Day Leave'
      when((SELECT fun_check_patternIdDays_bydate(e.patternId  ",p_selected_date," ))=1)  then 'Week-Off'
      WHEN( al.status='P') Then 'Present'
      else 
     'Absent'  END )  as 'leave status' 
     from Employee e
     left OUTER join AttendanceLogs al on al.employeeId=e.employeeId  and al.attendanceDate =",p_selected_date, " and al.companyId= " ,p_company_id,
               "  left OUTER join TMSLeaveEntriesDatewise tle on tle.employeeId=e.employeeId and tle.leaveStatus='APR' and tle.companyId=" ,p_company_id,
     "  left OUTER join Department dept  on  dept.departmentId= e.departmentId  and dept.companyId= ",p_company_id,
     
     " left OUTER join Designation dg  on  dg.designationId= e.designationId  and dg.companyId= ",p_company_id," order by ",@v_sort," limit ",v_offset,',',v_limit);

 PREPARE stmt3 FROM @Query1;
 EXECUTE stmt3;
 DEALLOCATE PREPARE stmt3
 ; 
  
    WHEN( (p_selected_date!=null or p_selected_date!='') 
           AND (p_employee_name=null or p_employee_name='')  
           AND (p_sorttype!=null or p_sorttype!='')  
           AND (p_employee_code=null or p_employee_code='') 
           AND (p_offset!=null OR p_offset!='')
           AND (p_limit!=null OR p_limit!='') 
           AND (p_active_value=null or p_active_value='')        
           AND (p_company_id!=null or p_company_id!='')  
           AND (p_dept_id=null or p_dept_id='')  
           AND (p_desg_id=null or p_desg_id='') 
           AND (p_title!=null or p_title!='') 
        
         ) THEN
         if(p_title='P') then
         select e.firstName,e.lastName, dg.designationName, al.inTime    ,al.outTime, al.location,al.mode,al.delayedTime,e.employeeId, al.status
,(case when al.status='p' THEN
  'Present'
  when al.status='HD' THEN
  'Half Day'
 
  end  ) as 'leave status' from AttendanceLogs al 
join Employee e on e.employeeId=al.employeeId 
join Designation dg on dg.designationId=e.designationId
WHERE al.attendanceDate =p_selected_date and al.companyId=p_company_id and  al.status='HD' or al.status='P'
 limit  v_offset ,v_limit;
  ELSEIF(p_title='A') THEN
  
 SELECT e.firstName,e.lastName, dg.designationName ,"NA" as "inTime" ,"NA" as "outTime", "NA" as "location","NA" as "mode","NA" as "delayedTime" ,e.employeeId, "NA" as "status"
,'Absent' as 'leave status'  FROM Employee e 
join Designation dg on dg.designationId=e.designationId
WHERE  e.employeeId and e.companyId=p_company_id NOT IN (SELECT al.employeeId FROM AttendanceLogs al where al.attendanceDate=p_selected_date and al.companyId=p_company_id)
  limit  v_offset ,v_limit;
  end if;
   
     WHEN( (p_selected_date!=null or p_selected_date!='') 
           AND (p_employee_name=null or p_employee_name='')  
           AND (p_sorttype!=null or p_sorttype!='')  
           AND (p_employee_code=null or p_employee_code='') 
           AND (p_offset!=null OR p_offset!='')
           AND (p_limit!=null OR p_limit!='') 
           AND (p_active_value=null or p_active_value='')        
           AND (p_company_id!=null or p_company_id!='')  
           AND (p_dept_id!=null or p_dept_id!='')  
           AND (p_desg_id=null or p_desg_id='') 
           AND (p_title!=null or p_title!='') 
        
         ) THEN
         if(p_title='P') then
         select e.firstName,e.lastName, dg.designationName, al.inTime    ,al.outTime, al.location,al.mode,al.delayedTime,e.employeeId, al.status
,(case when al.status='p' THEN
  'Present'
  when al.status='HD' THEN
  'Half Day'
   end  ) as 'leave status' from AttendanceLogs al 
join Employee e on e.employeeId=al.employeeId 
join Designation dg on dg.designationId=e.designationId
WHERE al.attendanceDate =p_selected_date and al.companyId=p_company_id and  al.status='HD' or al.status='P' and e.departmentId=p_dept_id
 limit  v_offset ,v_limit;
  ELSEIF(p_title='A') THEN
  
 SELECT e.firstName,e.lastName, dg.designationName ,"NA" as "inTime" ,"NA" as "outTime", "NA" as "location","NA" as "mode","NA" as "delayedTime" ,e.employeeId, "NA" as "status"
,'Absent' as 'leave status'  FROM Employee e 
join Designation dg on dg.designationId=e.designationId
WHERE  e.employeeId  NOT IN (SELECT al.employeeId FROM AttendanceLogs al where al.attendanceDate=p_selected_date and al.companyId=p_company_id) and e.companyId=p_company_id and e.departmentId=p_dept_id
  limit  v_offset ,v_limit;
  
  end if;

   
     WHEN( (p_selected_date!=null or p_selected_date!='') 
           AND (p_employee_name=null or p_employee_name='')  
           AND (p_sorttype!=null or p_sorttype!='')  
           AND (p_employee_code=null or p_employee_code='') 
           AND (p_offset!=null OR p_offset!='')
           AND (p_limit!=null OR p_limit!='') 
           AND (p_active_value=null or p_active_value='')        
           AND (p_company_id!=null or p_company_id!='')  
           AND (p_dept_id!=null or p_dept_id!='')  
           AND (p_desg_id!=null or p_desg_id!='') 
           AND (p_title!=null or p_title!='') 
        
         ) THEN
         if(p_title='P') then
         select e.firstName,e.lastName, dg.designationName, al.inTime    ,al.outTime, al.location,al.mode,al.delayedTime,e.employeeId, al.status
,(case when al.status='p' THEN
  'Present'
  when al.status='HD' THEN
  'Half Day'
   end  ) as 'leave status' from AttendanceLogs al 
join Employee e on e.employeeId=al.employeeId 
join Designation dg on dg.designationId=e.designationId
WHERE al.attendanceDate =p_selected_date and al.companyId=p_company_id and  al.status='HD' or al.status='P' and e.departmentId=p_dept_id and e.designationId=p_desg_id
 limit  v_offset ,v_limit;
  ELSEIF(p_title='A') THEN
  
 SELECT e.firstName,e.lastName, dg.designationName ,"NA" as "inTime" ,"NA" as "outTime", "NA" as "location","NA" as "mode","NA" as "delayedTime" ,e.employeeId, "NA" as "status"
,'Absent' as 'leave status'  FROM Employee e 
join Designation dg on dg.designationId=e.designationId
WHERE  e.employeeId  NOT IN (SELECT al.employeeId FROM AttendanceLogs al where al.attendanceDate=p_selected_date and al.companyId=p_company_id) and e.companyId=p_company_id and e.departmentId=p_dept_id and e.designationId=p_desg_id
  limit  v_offset ,v_limit;
  
  end if;

     
   
   
   else 
   
     SELECT 1;
    
END CASE;

end$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `pro_attendancelog_report` (IN `p_selected_date` VARCHAR(250), IN `p_employee_name` VARCHAR(250), IN `p_sorttype` VARCHAR(250), IN `p_activestatus` VARCHAR(250), IN `p_employee_code` VARCHAR(250), IN `p_offset` VARCHAR(127), IN `p_limit` VARCHAR(127), IN `p_active_value` VARCHAR(250), IN `p_company_id` VARCHAR(127), IN `p_dept_id` VARCHAR(127), IN `p_desg_id` VARCHAR(127), IN `p_title` VARCHAR(127))  NO SQL
BEGIN
    DECLARE v_offset int DEFAULT 0;
    DECLARE v_limit int DEFAULT 0;
    DECLARE v_sort varchar(50);
    DECLARE v_value varchar(50);
    set v_offset = CAST(p_offset AS UNSIGNED);
    set v_limit  = CAST(p_limit AS UNSIGNED);
    set v_value = CONCAT('e.',p_active_value);
    set @v_sort = CONCAT(v_value,' ',p_sorttype);
   
 
    SET @@sql_mode = 'NO_ENGINE_SUBSTITUTION'; 
    
     CASE 
      WHEN(   (p_selected_date!=null or p_selected_date!='') 
          AND (p_employee_name=null or p_employee_name='')  
          AND (p_sorttype!=null or p_sorttype!='')  
          AND (p_employee_code=null or p_employee_code='') 
          AND (p_offset!=null OR p_offset!='')
          AND (p_limit!=null OR p_limit!='') 
          AND  (p_active_value=null or p_active_value='') 
          AND (p_company_id!=null or p_company_id!='')  
          AND  (p_dept_id=null or p_dept_id='')  
          AND  (p_desg_id=null or p_desg_id='')
          AND  (p_title=null or p_title='') 
           
         ) THEN
   select  e.firstName,e.lastName, dg.designationName, al.inTime    ,al.outTime, al.location,al.mode,al.delayedTime,e.employeeId, al.status, al.attendanceDate,
(CASE
 when((select tl.holidayId FROM TMSHolidays tl  WHERE (p_selected_date >=tl.fromDate) AND (p_selected_date<=tl.toDate)>0)) THEN
      'Holiday'
      WHEN(tle.halfFullDay='H') Then 'Half Day Leave'
      when(tle.halfFullDay='F') THEN 'Full Day Leave'
      when((SELECT fun_check_patternIdDays_bydate(e.patternId,p_selected_date))=1)  then 'Week-Off'
      WHEN( al.status='P') Then 'Present'
      else 
     'Absent'  END )  as 'leave status' ,
       ( (SELECT fun_count_present_leave_absent	(p_company_id,'P',p_selected_date))) as presentCount,
     ( (SELECT fun_count_present_leave_absent	(p_company_id,'L',p_selected_date))) as leaveCount,
      ( (SELECT fun_count_present_leave_absent	(p_company_id,'A',p_selected_date))) as absentCount
     from Employee e
     left OUTER join AttendanceLogs al on al.employeeId=e.employeeId  and al.attendanceDate =p_selected_date and al.companyId=p_company_id
     left OUTER join TMSLeaveEntriesDatewise tle on  tle.employeeId=e.employeeId and tle.leaveStatus='APR' and tle.companyId=p_company_id
 left OUTER join Department dept  on  dept.departmentId= e.departmentId  and dept.companyId=p_company_id
     left OUTER join Designation dg  on  dg.designationId= e.designationId  and dg.companyId=p_company_id
  group by e.employeeId
    limit  v_offset ,v_limit 
  ; 
    WHEN( (p_selected_date!=null or p_selected_date!='') 
          AND (p_employee_name!=null or p_employee_name!='')  
          AND (p_sorttype!=null or p_sorttype!='')  
          AND (p_employee_code=null or p_employee_code='') 
          AND (p_offset!=null OR p_offset!='')
          AND (p_limit!=null OR p_limit!='') 
          AND (p_active_value=null or p_active_value='')        
          AND (p_company_id!=null or p_company_id!='')  
          AND (p_dept_id=null or p_dept_id='')  
          AND (p_desg_id=null or p_desg_id='')  
          AND  (p_title=null or p_title='') 
         
         ) THEN
       
  select  e.firstName,e.lastName, dg.designationName, al.inTime ,al.outTime, al.location,al.mode,al.delayedTime,e.employeeId, al.status, al.attendanceDate,
(CASE when((select tl.holidayId FROM TMSHolidays tl  WHERE (p_selected_date >=tl.fromDate) AND (p_selected_date<=tl.toDate)>0)) THEN
      'Holiday'
      WHEN(tle.halfFullDay='H') Then 'Half Day Leave'
      when(tle.halfFullDay='F') THEN 'Full Day Leave'
      when((SELECT fun_check_patternIdDays_bydate(e.patternId,p_selected_date))=1)  then 'Week-Off'
      WHEN( al.status='P') Then 'Present'
      else 
     'Absent'  END )  as 'leave status' ,
    ( (SELECT fun_count_present_leave_absent	(p_company_id,'P',p_selected_date))) as presentCount,
     ( (SELECT fun_count_present_leave_absent	(p_company_id,'L',p_selected_date))) as leaveCount,
      ( (SELECT fun_count_present_leave_absent	(p_company_id,'A',p_selected_date))) as absentCount
     from Employee e
     left OUTER join AttendanceLogs al on al.employeeId=e.employeeId  and al.attendanceDate =p_selected_date and al.companyId=p_company_id
     left OUTER join TMSLeaveEntriesDatewise tle on tle.employeeId=e.employeeId and tle.leaveStatus='APR' and tle.companyId=p_company_id
      left OUTER join Department dept  on  dept.departmentId= e.departmentId  and dept.companyId=p_company_id
 
     left OUTER join Designation dg  on  dg.designationId= e.designationId  and dg.companyId=p_company_id
 where e.firstName LIKE concat(p_employee_name, '%')
  group by e.employeeId
   limit  v_offset ,v_limit 
   ; 
   
    WHEN  (    (p_selected_date!=null or p_selected_date!='') 
           AND (p_employee_name=null or p_employee_name='')  
           AND (p_sorttype!=null or p_sorttype!='')  
           AND (p_employee_code=null or p_employee_code='') 
           AND (p_offset!=null OR p_offset!='')
           AND (p_limit!=null OR p_limit!='') 
           AND (p_active_value=null or p_active_value='')        
           AND (p_company_id!=null or p_company_id!='')  
           AND (p_dept_id!=null or p_dept_id!='')  
           AND (p_desg_id=null or p_desg_id='')  
           AND (p_title=null or p_title='') 
         ) THEN
         select  e.firstName,e.lastName, dg.designationName, al.inTime ,al.outTime, al.location,al.mode,al.delayedTime,e.employeeId, al.status, al.attendanceDate,
(CASE when((select tl.holidayId FROM TMSHolidays tl  WHERE (p_selected_date >=tl.fromDate) AND (p_selected_date<=tl.toDate)>0)) THEN
      'Holiday'
      WHEN(tle.halfFullDay='H') Then 'Half Day Leave'
      when(tle.halfFullDay='F') THEN 'Full Day Leave'
      when((SELECT fun_check_patternIdDays_bydate(e.patternId,p_selected_date))=1)  then 'Week-Off'
      WHEN( al.status='P') Then 'Present'
      else 
     'Absent'  END )  as 'leave status' ,
    ( (SELECT fun_count_present_leave_absent	(p_company_id,'P',p_selected_date))) as presentCount,
     ( (SELECT fun_count_present_leave_absent	(p_company_id,'L',p_selected_date))) as leaveCount,
      ( (SELECT fun_count_present_leave_absent	(p_company_id,'A',p_selected_date))) as absentCount
     from Employee e
     left OUTER join AttendanceLogs al on al.employeeId=e.employeeId  and al.attendanceDate =p_selected_date and al.companyId=p_company_id
     left OUTER join TMSLeaveEntriesDatewise tle on tle.employeeId=e.employeeId and tle.leaveStatus='APR' and tle.companyId=p_company_id
      left OUTER join Department dept  on  dept.departmentId= e.departmentId  and dept.companyId=p_company_id
 
     left OUTER join Designation dg  on  dg.designationId= e.designationId  and dg.companyId=p_company_id
 where e.departmentId=p_dept_id  
    group by e.employeeId
     limit  v_offset ,v_limit 
   ; 
   
    WHEN( (p_selected_date!=null or p_selected_date!='')  
           AND (p_employee_name=null or p_employee_name='')  
           AND (p_sorttype!=null or p_sorttype!='')  
           AND (p_employee_code!=null or p_employee_code!='') 
           AND (p_offset!=null OR p_offset!='')
           AND (p_limit!=null OR p_limit!='') 
           AND  (p_active_value=null or p_active_value='')        
           AND  (p_company_id!=null or p_company_id!='')  
           AND (p_dept_id=null or p_dept_id='')  
           AND (p_desg_id=null or p_desg_id='')  
           AND  (p_title=null or p_title='') 
         ) THEN
      
           select  e.firstName,e.lastName, dg.designationName, al.inTime ,al.outTime, al.location,al.mode,al.delayedTime,e.employeeId, al.status, al.attendanceDate,
(CASE when((select tl.holidayId FROM TMSHolidays tl  WHERE (p_selected_date >=tl.fromDate) AND (p_selected_date<=tl.toDate)>0)) THEN
      'Holiday'
      WHEN(tle.halfFullDay='H') Then 'Half Day Leave'
      when(tle.halfFullDay='F') THEN 'Full Day Leave'
      when((SELECT fun_check_patternIdDays_bydate(e.patternId,p_selected_date))=1)  then 'Week-Off'
      WHEN( al.status='P') Then 'Present'
      else 
     'Absent'  END )  as 'leave status' ,
    ( (SELECT fun_count_present_leave_absent	(p_company_id,'P',p_selected_date))) as presentCount,
     ( (SELECT fun_count_present_leave_absent	(p_company_id,'L',p_selected_date))) as leaveCount,
      ( (SELECT fun_count_present_leave_absent	(p_company_id,'A',p_selected_date))) as absentCount
     from Employee e
     left OUTER join AttendanceLogs al on al.employeeId=e.employeeId  and al.attendanceDate =p_selected_date and al.companyId=p_company_id
     left OUTER join TMSLeaveEntriesDatewise tle on tle.employeeId=e.employeeId and tle.leaveStatus='APR' and tle.companyId=p_company_id
      left OUTER join Department dept  on  dept.departmentId= e.departmentId  and dept.companyId=p_company_id
 
     left OUTER join Designation dg  on  dg.designationId= e.designationId  and dg.companyId=p_company_id
 where e.employeeCode=p_employee_code
   group by e.employeeId
   limit  v_offset ,v_limit
   
   ; 
  
   
   
   WHEN( (p_selected_date!=null or p_selected_date!='') AND
          (p_employee_name=null or p_employee_name='')  AND
          (p_sorttype!=null or p_sorttype!='') AND 
           (p_employee_code=null or p_employee_code='') 
           and (p_offset!=null OR p_offset!='')
           and (p_limit!=null OR p_limit!='') 
           and  (p_active_value=null or p_active_value='')        
         and  (p_company_id!=null or p_company_id!='')  
           and (p_dept_id=null or p_dept_id='')  
           and (p_desg_id!=null or p_desg_id!='')  
           AND (p_title=null or p_title='') 
        
         ) THEN
         
       select  e.firstName,e.lastName, dg.designationName, al.inTime ,al.outTime, al.location,al.mode,al.delayedTime,e.employeeId, al.status, al.attendanceDate,
(CASE when((select tl.holidayId FROM TMSHolidays tl  WHERE (p_selected_date >=tl.fromDate) AND (p_selected_date<=tl.toDate)>0)) THEN
      'Holiday'
      WHEN(tle.halfFullDay='H') Then 'Half Day Leave'
      when(tle.halfFullDay='F') THEN 'Full Day Leave'
      when((SELECT fun_check_patternIdDays_bydate(e.patternId,p_selected_date))=1)  then 'Week-Off'
      WHEN( al.status='P') Then 'Present'
      else 
     'Absent'  END )  as 'leave status' ,
    ( (SELECT fun_count_present_leave_absent	(p_company_id,'P',p_selected_date))) as presentCount,
     ( (SELECT fun_count_present_leave_absent	(p_company_id,'L',p_selected_date))) as leaveCount,
      ( (SELECT fun_count_present_leave_absent	(p_company_id,'A',p_selected_date))) as absentCount
     from Employee e
     left OUTER join AttendanceLogs al on al.employeeId=e.employeeId  and al.attendanceDate =p_selected_date and al.companyId=p_company_id
     left OUTER join TMSLeaveEntriesDatewise tle on tle.employeeId=e.employeeId and tle.leaveStatus='APR' and tle.companyId=p_company_id
      left OUTER join Department dept  on  dept.departmentId= e.departmentId  and dept.companyId=p_company_id
 
     left OUTER join Designation dg  on  dg.designationId= e.designationId  and dg.companyId=p_company_id
 where e.designationId=p_desg_id   
  group by e.employeeId
 limit  v_offset ,v_limit
   ; 
  
  
    WHEN( (p_selected_date!=null or p_selected_date!='') AND
          (p_employee_name=null or p_employee_name='')  AND
          (p_sorttype!=null or p_sorttype!='') AND 
           (p_employee_code=null or p_employee_code='') 
           and (p_offset!=null OR p_offset!='')
           and (p_limit!=null OR p_limit!='') 
           and  (p_active_value=null or p_active_value='')        
           and  (p_company_id!=null or p_company_id!='')  
           and (p_dept_id!=null or p_dept_id!='')  
           and (p_desg_id!=null or p_desg_id!='')  
           AND (p_title=null or p_title='') 
        
         ) THEN
         
      select  e.firstName,e.lastName, dg.designationName, al.inTime ,al.outTime, al.location,al.mode,al.delayedTime,e.employeeId, al.status, al.attendanceDate,
(CASE when((select tl.holidayId FROM TMSHolidays tl  WHERE (p_selected_date >=tl.fromDate) AND (p_selected_date<=tl.toDate)>0)) THEN
      'Holiday'
      WHEN(tle.halfFullDay='H') Then 'Half Day Leave'
      when(tle.halfFullDay='F') THEN 'Full Day Leave'
      when((SELECT fun_check_patternIdDays_bydate(e.patternId,p_selected_date))=1)  then 'Week-Off'
      WHEN( al.status='P') Then 'Present'
      else 
     'Absent'  END )  as 'leave status' ,
    ( (SELECT fun_count_present_leave_absent	(p_company_id,'P',p_selected_date))) as presentCount,
     ( (SELECT fun_count_present_leave_absent	(p_company_id,'L',p_selected_date))) as leaveCount,
      ( (SELECT fun_count_present_leave_absent	(p_company_id,'A',p_selected_date))) as absentCount
     from Employee e
     left OUTER join AttendanceLogs al on al.employeeId=e.employeeId  and al.attendanceDate =p_selected_date and al.companyId=p_company_id
     left OUTER join TMSLeaveEntriesDatewise tle on tle.employeeId=e.employeeId and tle.leaveStatus='APR' and tle.companyId=p_company_id
      left OUTER join Department dept  on  dept.departmentId= e.departmentId  and dept.companyId=p_company_id
 
     left OUTER join Designation dg  on  dg.designationId= e.designationId  and dg.companyId=p_company_id
 where e.designationId=p_desg_id  and e.departmentId  =p_dept_id
  group by e.employeeId
   limit  v_offset ,v_limit 
  
   ; 
  
  
   WHEN( (p_selected_date!=null or p_selected_date!='') AND
          (p_employee_name=null or p_employee_name='')  AND
          (p_sorttype!=null or p_sorttype!='') AND 
           (p_employee_code!=null or p_employee_code!='') 
           and (p_offset!=null OR p_offset!='')
           and (p_limit!=null OR p_limit!='') 
           and  (p_active_value=null or p_active_value='')        
           and  (p_company_id!=null or p_company_id!='')  
           and (p_dept_id!=null or p_dept_id!='')  
           and (p_desg_id!=null or p_desg_id!='')  
           AND (p_title=null or p_title='') 
        
         ) THEN
         
         select  e.firstName,e.lastName, dg.designationName, al.inTime ,al.outTime, al.location,al.mode,al.delayedTime,e.employeeId, al.status, al.attendanceDate,
(CASE when((select tl.holidayId FROM TMSHolidays tl  WHERE (p_selected_date >=tl.fromDate) AND (p_selected_date<=tl.toDate)>0)) THEN
      'Holiday'
      WHEN(tle.halfFullDay='H') Then 'Half Day Leave'
      when(tle.halfFullDay='F') THEN 'Full Day Leave'
      when((SELECT fun_check_patternIdDays_bydate(e.patternId,p_selected_date))=1)  then 'Week-Off'
      WHEN( al.status='P') Then 'Present'
      else 
     'Absent'  END )  as 'leave status' ,
    ( (SELECT fun_count_present_leave_absent	(p_company_id,'P',p_selected_date))) as presentCount,
     ( (SELECT fun_count_present_leave_absent	(p_company_id,'L',p_selected_date))) as leaveCount,
      ( (SELECT fun_count_present_leave_absent	(p_company_id,'A',p_selected_date))) as absentCount
     from Employee e
     left OUTER join AttendanceLogs al on al.employeeId=e.employeeId  and al.attendanceDate =p_selected_date and al.companyId=p_company_id
     left OUTER join TMSLeaveEntriesDatewise tle on tle.employeeId=e.employeeId and tle.leaveStatus='APR' and tle.companyId=p_company_id
      left OUTER join Department dept  on  dept.departmentId= e.departmentId  and dept.companyId=p_company_id
 
     left OUTER join Designation dg  on  dg.designationId= e.designationId  and dg.companyId=p_company_id
 where e.designationId=p_desg_id  and e.departmentId  =p_dept_id and e.employeeCode=p_employee_code
  group by e.employeeId
   limit  v_offset ,v_limit
   ; 
   
   
     WHEN( (p_selected_date!=null or p_selected_date!='') 
           AND (p_employee_name=null or p_employee_name='')  
           AND (p_sorttype!=null or p_sorttype!='')  
           AND (p_employee_code!=null or p_employee_code!='') 
           AND (p_offset!=null OR p_offset!='')
           AND (p_limit!=null OR p_limit!='') 
           AND  (p_active_value=null or p_active_value='')        
           AND  (p_company_id!=null or p_company_id!='')  
           AND (p_dept_id!=null or p_dept_id!='')  
           AND (p_desg_id=null or p_desg_id='')  
           AND (p_title=null or p_title='') 
         ) THEN
         
select  e.firstName,e.lastName, dg.designationName, al.inTime ,al.outTime, al.location,al.mode,al.delayedTime,e.employeeId, al.status, al.attendanceDate,
(CASE when((select tl.holidayId FROM TMSHolidays tl  WHERE (p_selected_date >=tl.fromDate) AND (p_selected_date<=tl.toDate)>0)) THEN
      'Holiday'
      WHEN(tle.halfFullDay='H') Then 'Half Day Leave'
      when(tle.halfFullDay='F') THEN 'Full Day Leave'
      when((SELECT fun_check_patternIdDays_bydate(e.patternId,p_selected_date))=1)  then 'Week-Off'
      WHEN( al.status='P') Then 'Present'
      else 
     'Absent'  END )  as 'leave status' ,
    ( (SELECT fun_count_present_leave_absent	(p_company_id,'P',p_selected_date))) as presentCount,
     ( (SELECT fun_count_present_leave_absent	(p_company_id,'L',p_selected_date))) as leaveCount,
      ( (SELECT fun_count_present_leave_absent	(p_company_id,'A',p_selected_date))) as absentCount
     from Employee e
     left OUTER join AttendanceLogs al on al.employeeId=e.employeeId  and al.attendanceDate =p_selected_date and al.companyId=p_company_id
     left OUTER join TMSLeaveEntriesDatewise tle on tle.employeeId=e.employeeId and tle.leaveStatus='APR' and tle.companyId=p_company_id
      left OUTER join Department dept  on  dept.departmentId= e.departmentId  and dept.companyId=p_company_id
 
     left OUTER join Designation dg  on  dg.designationId= e.designationId  and dg.companyId=p_company_id
 where  e.departmentId  =p_dept_id and e.employeeCode=p_employee_code
 group by e.employeeId
  limit  v_offset ,v_limit ; 
   
   
     WHEN( (p_selected_date!=null or p_selected_date!='') 
           AND (p_employee_name=null or p_employee_name='')  
           AND (p_sorttype!=null or p_sorttype!='')  
           AND (p_employee_code!=null or p_employee_code!='') 
           AND (p_offset!=null OR p_offset!='')
           AND (p_limit!=null OR p_limit!='') 
           AND  (p_active_value=null or p_active_value='')        
           AND  (p_company_id!=null or p_company_id!='')  
           AND (p_dept_id=null or p_dept_id='')  
           AND (p_desg_id!=null or p_desg_id!='') 
           AND (p_title=null or p_title='') 
        
         ) THEN
         
         select  e.firstName,e.lastName, dg.designationName, al.inTime ,al.outTime, al.location,al.mode,al.delayedTime,e.employeeId, al.status, al.attendanceDate,
(CASE when((select tl.holidayId FROM TMSHolidays tl  WHERE (p_selected_date >=tl.fromDate) AND (p_selected_date<=tl.toDate)>0)) THEN
      'Holiday'
      WHEN(tle.halfFullDay='H') Then 'Half Day Leave'
      when(tle.halfFullDay='F') THEN 'Full Day Leave'
      when((SELECT fun_check_patternIdDays_bydate(e.patternId,p_selected_date))=1)  then 'Week-Off'
      WHEN( al.status='P') Then 'Present'
      else 
     'Absent'  END )  as 'leave status' ,
    ( (SELECT fun_count_present_leave_absent	(p_company_id,'P',p_selected_date))) as presentCount,
     ( (SELECT fun_count_present_leave_absent	(p_company_id,'L',p_selected_date))) as leaveCount,
      ( (SELECT fun_count_present_leave_absent	(p_company_id,'A',p_selected_date))) as absentCount
     from Employee e
     left OUTER join AttendanceLogs al on al.employeeId=e.employeeId  and al.attendanceDate =p_selected_date and al.companyId=p_company_id
     left OUTER join TMSLeaveEntriesDatewise tle on tle.employeeId=e.employeeId and tle.leaveStatus='APR' and tle.companyId=p_company_id
      left OUTER join Department dept  on  dept.departmentId= e.departmentId  and dept.companyId=p_company_id
 
     left OUTER join Designation dg  on  dg.designationId= e.designationId  and dg.companyId=p_company_id
 where  e.designationId  =p_desg_id and e.employeeCode=p_employee_code
  group by e.employeeId
 limit  v_offset ,v_limit ; 
 
    WHEN( (p_selected_date!=null or p_selected_date!='') 
           AND (p_employee_name=null or p_employee_name='')  
           AND (p_sorttype!=null or p_sorttype!='')  
           AND  (p_employee_code=null or p_employee_code='') 
           AND (p_offset!=null OR p_offset!='')
           AND (p_limit!=null OR p_limit!='') 
           AND  (p_active_value!=null or p_active_value!='')        
           AND  (p_company_id!=null or p_company_id!='')  
           AND (p_dept_id=null or p_dept_id='')  
           AND (p_desg_id=null or p_desg_id='')
           AND (p_title=null or p_title='')
        
         ) THEN
        
        SET @Query1 =CONCAT("select  e.firstName,e.lastName, dg.designationName, al.inTime ,al.outTime, al.location,al.mode,al.delayedTime,e.employeeId, al.status, al.attendanceDate,
(CASE when((select tl.holidayId FROM TMSHolidays tl  WHERE (p_selected_date >=tl.fromDate) AND (p_selected_date<=tl.toDate)>0)) THEN
      'Holiday'
      WHEN(tle.halfFullDay='H') Then 'Half Day Leave'
      when(tle.halfFullDay='F') THEN 'Full Day Leave'
      when((SELECT fun_check_patternIdDays_bydate(e.patternId  ",p_selected_date," ))=1)  then 'Week-Off'
      WHEN( al.status='P') Then 'Present'
      else 
     'Absent'  END )  as 'leave status' ,
    ( (SELECT fun_count_present_leave_absent	(p_company_id,'P',p_selected_date))) as presentCount,
     ( (SELECT fun_count_present_leave_absent	(p_company_id,'L',p_selected_date))) as leaveCount,
      ( (SELECT fun_count_present_leave_absent	(p_company_id,'A',p_selected_date))) as absentCount
     from Employee e
     left OUTER join AttendanceLogs al on al.employeeId=e.employeeId  and al.attendanceDate =",p_selected_date, " and al.companyId= " ,p_company_id,
               "  left OUTER join TMSLeaveEntriesDatewise tle on tle.employeeId=e.employeeId and tle.leaveStatus='APR' and tle.companyId=" ,p_company_id,
     "  left OUTER join Department dept  on  dept.departmentId= e.departmentId  and dept.companyId= ",p_company_id,
     
     " left OUTER join Designation dg  on  dg.designationId= e.designationId  and dg.companyId= ",p_company_id," order by ",@v_sort," limit ",v_offset,',',v_limit);

 PREPARE stmt3 FROM @Query1;
 EXECUTE stmt3;
 DEALLOCATE PREPARE stmt3
 ; 
  
    WHEN( (p_selected_date!=null or p_selected_date!='') 
           AND (p_employee_name=null or p_employee_name='')  
           AND (p_sorttype!=null or p_sorttype!='')  
           AND (p_employee_code=null or p_employee_code='') 
           AND (p_offset!=null OR p_offset!='')
           AND (p_limit!=null OR p_limit!='') 
           AND (p_active_value=null or p_active_value='')        
           AND (p_company_id!=null or p_company_id!='')  
           AND (p_dept_id=null or p_dept_id='')  
           AND (p_desg_id=null or p_desg_id='') 
           AND (p_title!=null or p_title!='') 
        
         ) THEN
         if(p_title='P') then
         select e.firstName,e.lastName, dg.designationName, al.inTime    ,al.outTime, al.location,al.mode,al.delayedTime,e.employeeId, al.status
,(case when al.status='p' THEN
  'Present'
  when al.status='HD' THEN
  'Half Day'
 
  end  ) as 'leave status' from AttendanceLogs al 
join Employee e on e.employeeId=al.employeeId 
join Designation dg on dg.designationId=e.designationId
WHERE al.attendanceDate =p_selected_date and al.companyId=p_company_id and  al.status='HD' or al.status='P'
 limit  v_offset ,v_limit;
  ELSEIF(p_title='A') THEN
  
 SELECT e.firstName,e.lastName, dg.designationName ,"NA" as "inTime" ,"NA" as "outTime", "NA" as "location","NA" as "mode","NA" as "delayedTime" ,e.employeeId, "NA" as "status"
,'Absent' as 'leave status'  FROM Employee e 
join Designation dg on dg.designationId=e.designationId
WHERE  e.employeeId and e.companyId=p_company_id NOT IN (SELECT al.employeeId FROM AttendanceLogs al where al.attendanceDate=p_selected_date and al.companyId=p_company_id)
  limit  v_offset ,v_limit;
  end if;
   
     WHEN( (p_selected_date!=null or p_selected_date!='') 
           AND (p_employee_name=null or p_employee_name='')  
           AND (p_sorttype!=null or p_sorttype!='')  
           AND (p_employee_code=null or p_employee_code='') 
           AND (p_offset!=null OR p_offset!='')
           AND (p_limit!=null OR p_limit!='') 
           AND (p_active_value=null or p_active_value='')        
           AND (p_company_id!=null or p_company_id!='')  
           AND (p_dept_id!=null or p_dept_id!='')  
           AND (p_desg_id=null or p_desg_id='') 
           AND (p_title!=null or p_title!='') 
        
         ) THEN
         if(p_title='P') then
         select e.firstName,e.lastName, dg.designationName, al.inTime    ,al.outTime, al.location,al.mode,al.delayedTime,e.employeeId, al.status
,(case when al.status='p' THEN
  'Present'
  when al.status='HD' THEN
  'Half Day'
   end  ) as 'leave status' from AttendanceLogs al 
join Employee e on e.employeeId=al.employeeId 
join Designation dg on dg.designationId=e.designationId
WHERE al.attendanceDate =p_selected_date and al.companyId=p_company_id and  al.status='HD' or al.status='P' and e.departmentId=p_dept_id
 limit  v_offset ,v_limit;
  ELSEIF(p_title='A') THEN
  
 SELECT e.firstName,e.lastName, dg.designationName ,"NA" as "inTime" ,"NA" as "outTime", "NA" as "location","NA" as "mode","NA" as "delayedTime" ,e.employeeId, "NA" as "status"
,'Absent' as 'leave status'  FROM Employee e 
join Designation dg on dg.designationId=e.designationId
WHERE  e.employeeId  NOT IN (SELECT al.employeeId FROM AttendanceLogs al where al.attendanceDate=p_selected_date and al.companyId=p_company_id) and e.companyId=p_company_id and e.departmentId=p_dept_id
  limit  v_offset ,v_limit;
  
  end if;

   
     WHEN( (p_selected_date!=null or p_selected_date!='') 
           AND (p_employee_name=null or p_employee_name='')  
           AND (p_sorttype!=null or p_sorttype!='')  
           AND (p_employee_code=null or p_employee_code='') 
           AND (p_offset!=null OR p_offset!='')
           AND (p_limit!=null OR p_limit!='') 
           AND (p_active_value=null or p_active_value='')        
           AND (p_company_id!=null or p_company_id!='')  
           AND (p_dept_id!=null or p_dept_id!='')  
           AND (p_desg_id!=null or p_desg_id!='') 
           AND (p_title!=null or p_title!='') 
        
         ) THEN
         if(p_title='P') then
         select e.firstName,e.lastName, dg.designationName, al.inTime    ,al.outTime, al.location,al.mode,al.delayedTime,e.employeeId, al.status
,(case when al.status='p' THEN
  'Present'
  when al.status='HD' THEN
  'Half Day'
   end  ) as 'leave status' from AttendanceLogs al 
join Employee e on e.employeeId=al.employeeId 
join Designation dg on dg.designationId=e.designationId
WHERE al.attendanceDate =p_selected_date and al.companyId=p_company_id and  al.status='HD' or al.status='P' and e.departmentId=p_dept_id and e.designationId=p_desg_id
 limit  v_offset ,v_limit;
  ELSEIF(p_title='A') THEN
  
 SELECT e.firstName,e.lastName, dg.designationName ,"NA" as "inTime" ,"NA" as "outTime", "NA" as "location","NA" as "mode","NA" as "delayedTime" ,e.employeeId, "NA" as "status"
,'Absent' as 'leave status'  FROM Employee e 
join Designation dg on dg.designationId=e.designationId
WHERE  e.employeeId  NOT IN (SELECT al.employeeId FROM AttendanceLogs al where al.attendanceDate=p_selected_date and al.companyId=p_company_id) and e.companyId=p_company_id and e.departmentId=p_dept_id and e.designationId=p_desg_id
  limit  v_offset ,v_limit;
  
  end if;

     
   
   
   else 
   
     SELECT 1;
    
END CASE;

end$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `pro_birthday` (IN `p_comp_id` INT(127))  NO SQL
BEGIN

   
    DECLARE e_sqlstate 		VARCHAR(255); 
    DECLARE e_error_no 		INT(127);
    DECLARE e_error_message	TEXT;
    DECLARE e_remark		TEXT;
 
    DECLARE EXIT HANDLER FOR SQLEXCEPTION
  	BEGIN
		GET CURRENT DIAGNOSTICS CONDITION 1 e_sqlstate = RETURNED_SQLSTATE, e_error_no = MYSQL_ERRNO, e_error_message = MESSAGE_TEXT;
        
		SET	@p_process_status	=	1000;
		SET	@p_process_message	=	'Error... please Contact Administrator';
       
 	 SET	@p_process_remark	=  fun_set_exception_handling ( 2, 'pro_birthday', e_sqlstate, e_error_no, e_error_message, e_remark);
			
	     END;
         
         
           SET @@sql_mode = 'NO_ENGINE_SUBSTITUTION';
         
                 DROP TEMPORARY TABLE IF EXISTS temp;
    CREATE TEMPORARY TABLE IF NOT EXISTS temp (
        empName varchar(50) NOT NULL,
         employeeCode varchar(10) DEFAULT NULL,departmentName  varchar(10) DEFAULT NULL,
     designationName   varchar(10) DEFAULT NULL, dateOfBirth  datetime  , empLogoPath varchar(200) NOT NULL, firstName varchar(50) NOT NULL,
	 lastName varchar(50) DEFAULT NULL);
   
      INSERT INTO temp ( empName, employeeCode,departmentName,designationName,dateOfBirth,empLogoPath,firstName,lastName)
    
	  select concat(em.firstName,' ' ,em.lastName),em.employeeCode ,dp.departmentName,dg.designationName,em.dateOfBirth,em.employeeLogoPath,em.firstName,em.lastName
                  from 
      Employee em 
     left join PayStructureHd ph on ph.employeeId=em.employeeId and (ph.dateEnd is null or ph.dateEnd>CURRENT_DATE) and (ph.effectiveDate is NOT null and ph.effectiveDate<=CURRENT_DATE ) and ph.activeStatus='AC'
     left join Grades gd on gd.gradesId=ph.gradesId
     left join Designation dg on dg.designationId=em.designationId
     left join Department dp on dp.departmentId=em.departmentId
      where 
      MONTH(em.dateOfBirth) = MONTH(NOW())  AND em.companyId=p_comp_id AND   em.activeStatus ='AC' AND day(em.dateOfBirth)= day(now());
                
    
  INSERT INTO temp ( empName, employeeCode,departmentName,designationName,dateOfBirth,empLogoPath,firstName,lastName)
    
    
	 select concat(em.firstName,' ' ,em.lastName),em.employeeCode ,dp.departmentName,dg.designationName,em.dateOfBirth,em.employeeLogoPath,em.firstName,em.lastName
                  from 
      Employee em 
     left join PayStructureHd ph on ph.employeeId=em.employeeId and (ph.dateEnd is null or ph.dateEnd>CURRENT_DATE) and (ph.effectiveDate is NOT null and ph.effectiveDate<=CURRENT_DATE ) and ph.activeStatus='AC'
     left join Grades gd on gd.gradesId=ph.gradesId
     left join Designation dg on dg.designationId=em.designationId
     left join Department dp on dp.departmentId=em.departmentId
      where 
     MONTH(em.dateOfBirth) = MONTH(NOW()) AND  day(em.dateOfBirth)> day(now()) AND em.companyId=1 AND   em.activeStatus ='AC'  ORDER BY day(em.dateOfBirth) ASC, MONTH(em.dateOfBirth) DESC;
                

INSERT INTO temp ( empName, employeeCode,departmentName,designationName,dateOfBirth,empLogoPath,firstName,lastName)
    
 
 select concat(em.firstName,' ' ,em.lastName),em.employeeCode ,dp.departmentName,dg.designationName,em.dateOfBirth,em.employeeLogoPath,em.firstName,em.lastName
	
                  from 
      Employee em 
     left join PayStructureHd ph on ph.employeeId=em.employeeId and (ph.dateEnd is null or ph.dateEnd>CURRENT_DATE) and (ph.effectiveDate is NOT null and ph.effectiveDate<=CURRENT_DATE ) and ph.activeStatus='AC'
     left join Grades gd on gd.gradesId=ph.gradesId
     left join Designation dg on dg.designationId=em.designationId
     left join Department dp on dp.departmentId=em.departmentId
      where 
     MONTH(em.dateOfBirth) = MONTH(NOW()) AND  day(em.dateOfBirth)< day(now()) AND em.companyId=1 AND   em.activeStatus ='AC'   ORDER BY day(em.dateOfBirth) DESC, MONTH(em.dateOfBirth) DESC;
                
		
		select * from temp ;
		END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `pro_bulkattendance_view` (IN `p_selected_date` VARCHAR(50), IN `p_employee_name` VARCHAR(50), IN `p_sorttype` VARCHAR(50), IN `p_activestatus` VARCHAR(50), IN `p_employee_code` VARCHAR(50), IN `p_dept_id` VARCHAR(50), IN `p_desg_id` VARCHAR(50), IN `p_company_id` VARCHAR(50), IN `p_active_value` VARCHAR(50))  NO SQL
BEGIN
             
    SET @@sql_mode = 'NO_ENGINE_SUBSTITUTION'; 
    
     CASE 
     
   
     WHEN( (p_selected_date!=null or p_selected_date!='') AND
          (p_employee_name=null or p_employee_name='')  AND
          (p_sorttype=null or p_sorttype='') AND 
           (p_employee_code=null or p_employee_code='') 
                          and  (p_activestatus!=null or p_activestatus!='')   
            and (p_company_id		!=null or p_company_id!='')  
          and  (p_dept_id=null or p_dept_id='')  
          and  (p_desg_id=null or p_desg_id='')    
         ) THEN
  
     select e.employeeId,e.firstName,e.lastName,al.attendanceLogId,al.attendanceDate,e.employeeCode,e.companyId,al.inTime,al.outTime,al.inDeviceId,al.outDeviceId,al.location,al.mode,al.createdBy,al.createdDate,al.updatedBy,al.delayedTime,al.inout,al.status,al.totalTime,
(CASE when((tle.fromDate=th.fromDate) or (tle.toDate=th.toDate)) THEN
      'Holiday'
      WHEN(tle.halfFullDay='H') Then 'Half Day Leave'
      when(tle.halfFullDay='F') THEN 'Full Day Leave'
      when((SELECT fun_check_patternIdDays_bydate(e.patternId,p_selected_date))=1)  then 'Week-Off'
      else 
     'Absent'  END )  as 'leave status' 
     from Employee e
     left OUTER join AttendanceLogs al on al.employeeId=e.employeeId  and al.attendanceDate =p_selected_date and al.companyId=p_company_id
     left OUTER join TMSLeaveEntries tle on tle.employeeId=e.employeeId and tle.status='A' and tle.companyId=p_company_id
     left OUTER join TMSHolidays th on  th.fromDate= tle.fromDate or th.toDate=tle.toDate and th.companyId=p_company_id
     left OUTER join Designation dg  on  dg.designationId= e.designationId  and dg.companyId=p_company_id  and e.activeStatus=p_activestatus
   
  
   ; 
   
     WHEN( (p_selected_date!=null or p_selected_date!='') AND
          (p_employee_name=null or p_employee_name='')  AND
          (p_sorttype=null or p_sorttype='') AND 
           (p_employee_code=null or p_employee_code='') 
                           and  (p_activestatus!=null or p_activestatus!='')   
            and (p_company_id!=null or p_company_id!='')  
          and  (p_dept_id!=null or p_dept_id!='')  
          and  (p_desg_id=null or p_desg_id='')    
         ) THEN
  
     select e.employeeId,e.firstName,e.lastName,al.attendanceLogId,al.attendanceDate,e.employeeCode,e.companyId,al.inTime,al.outTime,al.inDeviceId,al.outDeviceId,al.location,al.mode,al.createdBy,al.createdDate,al.updatedBy,al.delayedTime,al.inout,al.status,al.totalTime,
(CASE when((tle.fromDate=th.fromDate) or (tle.toDate=th.toDate)) THEN
      'Holiday'
      WHEN(tle.halfFullDay='H') Then 'Half Day Leave'
      when(tle.halfFullDay='F') THEN 'Full Day Leave'
      when((SELECT fun_check_patternIdDays_bydate(e.patternId,p_selected_date))=1)  then 'Week-Off'
      else 
     'Absent'  END )  as 'leave status' 
     from Employee e
     left OUTER join AttendanceLogs al on al.employeeId=e.employeeId  and al.attendanceDate =p_selected_date and al.companyId=p_company_id
     left OUTER join TMSLeaveEntries tle on tle.employeeId=e.employeeId and tle.status='A' and tle.companyId=p_company_id
     left OUTER join TMSHolidays th on  th.fromDate= tle.fromDate or th.toDate=tle.toDate and th.companyId=p_company_id
     left OUTER join Designation dg  on  dg.designationId= e.designationId  and dg.companyId=p_company_id
    where  e.departmentId  =p_dept_id  and e.activeStatus=p_activestatus
  
   ; 
   
      WHEN( (p_selected_date!=null or p_selected_date!='') AND
          (p_employee_name=null or p_employee_name='')  AND
          (p_sorttype=null or p_sorttype='') AND 
           (p_employee_code=null or p_employee_code='') 
                      and  (p_activestatus!=null or p_activestatus!='')   
            and (p_company_id!=null or p_company_id!='')  
          and  (p_dept_id=null or p_dept_id='')  
          and  (p_desg_id!=null or p_desg_id!='')    
         ) THEN
  
     select e.employeeId,e.firstName,e.lastName,al.attendanceLogId,al.attendanceDate,e.employeeCode,e.companyId,al.inTime,al.outTime,al.inDeviceId,al.outDeviceId,al.location,al.mode,al.createdBy,al.createdDate,al.updatedBy,al.delayedTime,al.inout,al.status,al.totalTime,
(CASE when((tle.fromDate=th.fromDate) or (tle.toDate=th.toDate)) THEN
      'Holiday'
      WHEN(tle.halfFullDay='H') Then 'Half Day Leave'
      when(tle.halfFullDay='F') THEN 'Full Day Leave'
      when((SELECT fun_check_patternIdDays_bydate(e.patternId,p_selected_date))=1)  then 'Week-Off'
      else 
     'Absent'  END )  as 'leave status' 
     from Employee e
     left OUTER join AttendanceLogs al on al.employeeId=e.employeeId  and al.attendanceDate =p_selected_date and al.companyId=p_company_id
     left OUTER join TMSLeaveEntries tle on tle.employeeId=e.employeeId and tle.status='A' and tle.companyId=p_company_id
     left OUTER join TMSHolidays th on  th.fromDate= tle.fromDate or th.toDate=tle.toDate and th.companyId=p_company_id
     left OUTER join Designation dg  on  dg.designationId= e.designationId  and dg.companyId=p_company_id
    where  e.designationId  =p_desg_id and e.activeStatus=p_activestatus
   ; 
  
     WHEN( (p_selected_date!=null or p_selected_date!='') AND
          (p_employee_name=null or p_employee_name='')  AND
          (p_sorttype=null or p_sorttype='') AND 
           (p_employee_code=null or p_employee_code='') 
                      and  (p_activestatus!=null or p_activestatus!='')   
            and (p_company_id!=null or p_company_id!='')  
          and  (p_dept_id!=null or p_dept_id!='')  
          and  (p_desg_id!=null or p_desg_id!='')    
         ) THEN
  
    select e.employeeId,e.firstName,e.lastName,al.attendanceLogId,al.attendanceDate,e.employeeCode,e.companyId,al.inTime,al.outTime,al.inDeviceId,al.outDeviceId,al.location,al.mode,al.createdBy,al.createdDate,al.updatedBy,al.delayedTime,al.inout,al.status,al.totalTime,
(CASE when((tle.fromDate=th.fromDate) or (tle.toDate=th.toDate)) THEN
      'Holiday'
      WHEN(tle.halfFullDay='H') Then 'Half Day Leave'
      when(tle.halfFullDay='F') THEN 'Full Day Leave'
      when((SELECT fun_check_patternIdDays_bydate(e.patternId,p_selected_date))=1)  then 'Week-Off'
      else 
     'Absent'  END )  as 'leave status' 
     from Employee e
     left OUTER join AttendanceLogs al on al.employeeId=e.employeeId  and al.attendanceDate =p_selected_date and al.companyId=p_company_id
     left OUTER join TMSLeaveEntries tle on tle.employeeId=e.employeeId and tle.status='A' and tle.companyId=p_company_id
     left OUTER join TMSHolidays th on  th.fromDate= tle.fromDate or th.toDate=tle.toDate and th.companyId=p_company_id
     left OUTER join Designation dg  on  dg.designationId= e.designationId  and dg.companyId=p_company_id
    where  e.designationId  =p_desg_id and e.departmentId=p_dept_id
    and e.activeStatus=p_activestatus
   ;
   
     WHEN( (p_selected_date!=null or p_selected_date!='') AND
          (p_employee_name!=null or p_employee_name!='')  AND
          (p_sorttype=null or p_sorttype='') AND 
           (p_employee_code=null or p_employee_code='') 
                          and  (p_activestatus!=null or p_activestatus!='')   
            and (p_company_id		!=null or p_company_id!='')  
          and  (p_dept_id=null or p_dept_id='')  
          and  (p_desg_id=null or p_desg_id='')    
         ) THEN
  
     select e.employeeId,e.firstName,e.lastName,al.attendanceLogId,al.attendanceDate,e.employeeCode,e.companyId,al.inTime,al.outTime,al.inDeviceId,al.outDeviceId,al.location,al.mode,al.createdBy,al.createdDate,al.updatedBy,al.delayedTime,al.inout,al.status,al.totalTime,
(CASE when((tle.fromDate=th.fromDate) or (tle.toDate=th.toDate)) THEN
      'Holiday'
      WHEN(tle.halfFullDay='H') Then 'Half Day Leave'
      when(tle.halfFullDay='F') THEN 'Full Day Leave'
      when((SELECT fun_check_patternIdDays_bydate(e.patternId,p_selected_date))=1)  then 'Week-Off'
      else 
     'Absent'  END )  as 'leave status' 
     from Employee e
     left OUTER join AttendanceLogs al on al.employeeId=e.employeeId  and al.attendanceDate =p_selected_date and al.companyId=p_company_id
     left OUTER join TMSLeaveEntries tle on tle.employeeId=e.employeeId and tle.status='A' and tle.companyId=p_company_id
     left OUTER join TMSHolidays th on  th.fromDate= tle.fromDate or th.toDate=tle.toDate and th.companyId=p_company_id
     left OUTER join Designation dg  on  dg.designationId= e.designationId  and dg.companyId=p_company_id 
      where e.firstName LIKE concat(p_employee_name, '%') or e.lastName LIKE concat(p_employee_name, '%') and   
     e.activeStatus=p_activestatus
   
  
   ; 
   
   
   
    WHEN( (p_selected_date!=null or p_selected_date!='') AND
          (p_activestatus!=null or p_activestatus!='')   and
             (p_company_id!=null or p_company_id!='') and 
         
                   (p_employee_name=null or p_employee_name='')  AND
           (p_sorttype=null or p_sorttype='') AND 
           (p_employee_code!=null or p_employee_code!='') and
                            
            (p_dept_id!=null or p_dept_id!='')  
          and  (p_desg_id=null or p_desg_id='')    
         ) THEN
  
     select e.employeeId,e.firstName,e.lastName,al.attendanceLogId,al.attendanceDate,e.employeeCode,e.companyId,al.inTime,al.outTime,al.inDeviceId,al.outDeviceId,al.location,al.mode,al.createdBy,al.createdDate,al.updatedBy,al.delayedTime,al.inout,al.status,al.totalTime,
(CASE when((tle.fromDate=th.fromDate) or (tle.toDate=th.toDate)) THEN
      'Holiday'
      WHEN(tle.halfFullDay='H') Then 'Half Day Leave'
      when(tle.halfFullDay='F') THEN 'Full Day Leave'
      when((SELECT fun_check_patternIdDays_bydate(e.patternId,p_selected_date))=1)  then 'Week-Off'
      else 
     'Absent'  END )  as 'leave status' 
     from Employee e
     left OUTER join AttendanceLogs al on al.employeeId=e.employeeId  and al.attendanceDate =p_selected_date and al.companyId=p_company_id
     left OUTER join TMSLeaveEntries tle on tle.employeeId=e.employeeId and tle.status='A' and tle.companyId=p_company_id
     left OUTER join TMSHolidays th on  th.fromDate= tle.fromDate or th.toDate=tle.toDate and th.companyId=p_company_id
     left OUTER join Designation dg  on  dg.designationId= e.designationId  and dg.companyId=p_company_id
    where  e.departmentId  =p_dept_id  and  e.activeStatus=p_activestatus and e.employeeCode=p_employee_code
   ; 
   
   
    WHEN( (p_selected_date!=null or p_selected_date!='') AND
              (p_activestatus!=null or p_activestatus!='')   and
             (p_company_id!=null or p_company_id!='')  AND
         
        
           (p_employee_code!=null or p_employee_code!='') and
                          (p_employee_name=null or p_employee_name='')  AND
          (p_sorttype=null or p_sorttype='') AND 
            (p_dept_id=null or p_dept_id='') and 
            (p_desg_id=null or p_desg_id='')    
         ) THEN
  
      select e.employeeId,e.firstName,e.lastName,al.attendanceLogId,al.attendanceDate,e.employeeCode,e.companyId,al.inTime,al.outTime,al.inDeviceId,al.outDeviceId,al.location,al.mode,al.createdBy,al.createdDate,al.updatedBy,al.delayedTime,al.inout,al.status,al.totalTime,
(CASE when((tle.fromDate=th.fromDate) or (tle.toDate=th.toDate)) THEN
      'Holiday'
      WHEN(tle.halfFullDay='H') Then 'Half Day Leave'
      when(tle.halfFullDay='F') THEN 'Full Day Leave'
      when((SELECT fun_check_patternIdDays_bydate(e.patternId,p_selected_date))=1)  then 'Week-Off'
      else 
     'Absent'  END )  as 'leave status' 
     from Employee e
     left OUTER join AttendanceLogs al on al.employeeId=e.employeeId  and al.attendanceDate =p_selected_date and al.companyId=p_company_id
     left OUTER join TMSLeaveEntries tle on tle.employeeId=e.employeeId and tle.status='A' and tle.companyId=p_company_id
     left OUTER join TMSHolidays th on  th.fromDate= tle.fromDate or th.toDate=tle.toDate and th.companyId=p_company_id
     left OUTER join Designation dg  on  dg.designationId= e.designationId  and dg.companyId=p_company_id
    where   e.activeStatus=p_activestatus and e.employeeCode=p_employee_code
   ; 
   
   
    WHEN( (p_selected_date!=null or p_selected_date!='') AND
            (p_activestatus!=null or p_activestatus!='')  and 
             (p_company_id!=null or p_company_id!='')  AND
         
         (p_employee_code!=null or p_employee_code!='') and
             (p_desg_id!=null or p_desg_id!='') and
         
          (p_employee_name=null or p_employee_name='')  AND
          (p_sorttype=null or p_sorttype='') AND 
                             (p_dept_id=null or p_dept_id='')  
             
         ) THEN
  
     select e.employeeId,e.firstName,e.lastName,al.attendanceLogId,al.attendanceDate,e.employeeCode,e.companyId,al.inTime,al.outTime,al.inDeviceId,al.outDeviceId,al.location,al.mode,al.createdBy,al.createdDate,al.updatedBy,al.delayedTime,al.inout,al.status,al.totalTime,
(CASE when((tle.fromDate=th.fromDate) or (tle.toDate=th.toDate)) THEN
      'Holiday'
      WHEN(tle.halfFullDay='H') Then 'Half Day Leave'
      when(tle.halfFullDay='F') THEN 'Full Day Leave'
      when((SELECT fun_check_patternIdDays_bydate(e.patternId,p_selected_date))=1)  then 'Week-Off'
      else 
     'Absent'  END )  as 'leave status' 
     from Employee e
     left OUTER join AttendanceLogs al on al.employeeId=e.employeeId  and al.attendanceDate =p_selected_date and al.companyId=p_company_id
     left OUTER join TMSLeaveEntries tle on tle.employeeId=e.employeeId and tle.status='A' and tle.companyId=p_company_id
     left OUTER join TMSHolidays th on  th.fromDate= tle.fromDate or th.toDate=tle.toDate and th.companyId=p_company_id
     left OUTER join Designation dg  on  dg.designationId= e.designationId  and dg.companyId=p_company_id
    where   e.activeStatus=p_activestatus and e.designationId=p_desg_id AND e.employeeCode=p_employee_code
   ; 
   WHEN( (p_selected_date!=null or p_selected_date!='') AND
        (p_activestatus!=null or p_activestatus!='')   AND
             (p_company_id!=null or p_company_id!='')  AND
        
          (p_sorttype=null or p_sorttype='') AND 
           (p_employee_code!=null or p_employee_code!='') AND
                  (p_employee_name=null or p_employee_name='')  AND

                             (p_dept_id!=null or p_dept_id!='')  
          and  (p_desg_id!=null or p_desg_id!='')    
         ) THEN
  
   select e.employeeId,e.firstName,e.lastName,al.attendanceLogId,al.attendanceDate,e.employeeCode,e.companyId,al.inTime,al.outTime,al.inDeviceId,al.outDeviceId,al.location,al.mode,al.createdBy,al.createdDate,al.updatedBy,al.delayedTime,al.inout,al.status,al.totalTime,
(CASE when((tle.fromDate=th.fromDate) or (tle.toDate=th.toDate)) THEN
      'Holiday'
      WHEN(tle.halfFullDay='H') Then 'Half Day Leave'
      when(tle.halfFullDay='F') THEN 'Full Day Leave'
      when((SELECT fun_check_patternIdDays_bydate(e.patternId,p_selected_date))=1)  then 'Week-Off'
      else 
     'Absent'  END )  as 'leave status' 
     from Employee e
     left OUTER join AttendanceLogs al on al.employeeId=e.employeeId  and al.attendanceDate =p_selected_date and al.companyId=p_company_id
     left OUTER join TMSLeaveEntries tle on tle.employeeId=e.employeeId and tle.status='A' and tle.companyId=p_company_id
     left OUTER join TMSHolidays th on  th.fromDate= tle.fromDate or th.toDate=tle.toDate and th.companyId=p_company_id
     left OUTER join Designation dg  on  dg.designationId= e.designationId  and dg.companyId=p_company_id
    where   e.activeStatus=p_activestatus and e.designationId=p_desg_id AND e.departmentId=p_dept_id   AND e.employeeCode=p_employee_code
   ; 
  else 
     SELECT 1;
END CASE;

end$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `pro_company_announcement` (IN `p_comp_id` INT(127))  NO SQL
BEGIN

    
    DECLARE e_sqlstate 		VARCHAR(255); 
    DECLARE e_error_no 		INT(127);
    DECLARE e_error_message	TEXT;
    DECLARE e_remark		TEXT;
 
    DECLARE EXIT HANDLER FOR SQLEXCEPTION
  	BEGIN
		GET CURRENT DIAGNOSTICS CONDITION 1 e_sqlstate = RETURNED_SQLSTATE, e_error_no = MYSQL_ERRNO, e_error_message = MESSAGE_TEXT;
        
		SET	@p_process_status	=	1000;
		SET	@p_process_message	=	'Error... please Contact Administrator';
		SET	@p_process_remark	=  fun_set_exception_handling ( 2, 'pro_company_announcement', e_sqlstate, e_error_no, e_error_message, e_remark);
			
	     END;
      
   
      SELECT m.title,m.description,dept.departmentName,m.dateFrom ,m.dateTo FROM MassCommunication m JOIN Department dept ON m.departmentId=dept.departmentId

WHERE m.dateFrom > DATE_SUB(CURDATE(), INTERVAL 4 WEEK) and m.activeStatus='AC'  and m.companyId=p_comp_id;

      
      

End$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `pro_count_leave_notification` (IN `p_comp_id` INT(127) UNSIGNED, IN `p_emp_id` INT(127) UNSIGNED)  NO SQL
BEGIN
    
    DECLARE e_sqlstate 		VARCHAR(255); 
    DECLARE e_error_no 		INT(127);
    DECLARE e_error_message	TEXT;
    DECLARE e_remark		TEXT;
 
    DECLARE EXIT HANDLER FOR SQLEXCEPTION
  	BEGIN
		GET CURRENT DIAGNOSTICS CONDITION 1 e_sqlstate = RETURNED_SQLSTATE, e_error_no = MYSQL_ERRNO, e_error_message = MESSAGE_TEXT;
        
		SET	@p_process_status	=	1000;
		SET	@p_process_message	=	'Error... please Contact Administrator';
		SET	@p_process_remark	=  fun_set_exception_handling ( 1, 'pro_count_leave_notification', e_sqlstate, e_error_no, e_error_message, e_remark);
			
	     END;
      
      
         
  
	   SELECT 	fun_count_leave_notification(p_comp_id,p_emp_id) as leaveCount,
       fun_count_sepration_notification(p_comp_id,p_emp_id) as seprationCount,
       fun_count_comp_off_notification(p_comp_id,p_emp_id) as compOffCount,
       fun_count_arrequest_notification(p_comp_id,p_emp_id) as arrequestCount,
       fun_count_help_notification(p_comp_id,p_emp_id) as helpCount;
		
		  

 
                                    

END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `pro_count_notification` (IN `p_comp_id` INT(127) UNSIGNED, IN `p_emp_id` INT(127) UNSIGNED)  NO SQL
BEGIN
    
    DECLARE e_sqlstate 		VARCHAR(255); 
    DECLARE e_error_no 		INT(127);
    DECLARE e_error_message	TEXT;
    DECLARE e_remark		TEXT;
 
    DECLARE EXIT HANDLER FOR SQLEXCEPTION
  	BEGIN
		GET CURRENT DIAGNOSTICS CONDITION 1 e_sqlstate = RETURNED_SQLSTATE, e_error_no = MYSQL_ERRNO, e_error_message = MESSAGE_TEXT;
        
		SET	@p_process_status	=	1000;
		SET	@p_process_message	=	'Error... please Contact Administrator';
		SET	@p_process_remark	=  fun_set_exception_handling ( 1, 'pro_count_notification', e_sqlstate, e_error_no, e_error_message, e_remark);
			
	     END;
  
  
	   
	   SELECT 	fun_count_leave_notification(p_comp_id,p_emp_id) as leaveCount,
       fun_count_sepration_notification(p_comp_id,p_emp_id) as seprationCount,
       fun_count_comp_off_notification(p_comp_id,p_emp_id) as compOffCount,
       fun_count_arrequest_notification(p_comp_id,p_emp_id) as arrequestCount,
       fun_count_help_notification(p_comp_id,p_emp_id) as helpCount;
		

   SELECT 'leave' as newfield, e.firstName ,e.lastName,thd.employeeRemark,thd.dateCreated FROM TMSLeaveEntries thd INNER join Employee e on thd.employeeId= e.employeeId where thd.status='PEN'
 and DATE(thd.dateCreated) = CURRENT_DATE and e.ReportingToEmployee=p_emp_id  and thd.companyId=p_comp_id;       		
	
			 
SELECT  'separation' as newfield, e.firstName ,e.lastName,thd.remark,thd.description,thd.dateCreated   FROM  Separation thd  INNER join Employee e on thd.employeeId= e.employeeId 
 where thd.status='PEN' and DATE(thd.dateCreated) = CURRENT_DATE and  e.ReportingToEmployee=p_emp_id  and thd.companyId=p_comp_id;   
		
			 
             
SELECT 'comp_off' as newfield, e.firstName,e.lastName ,thd.remark,thd.dateCreated  FROM TMSCompensantoryOff thd INNER join Employee e on thd.employeeId= e.employeeId where thd.status='PEN' and 
DATE(thd.dateCreated) = CURRENT_DATE and e.ReportingToEmployee=p_emp_id and thd.companyId=p_comp_id;   		
	
    
     
             
SELECT 'ar_request' as newfield, e.firstName,e.lastName,thd.employeeRemark,thd.dateCreated FROM TMSARRequest thd INNER join Employee e on thd.employeeId= e.employeeId where thd.status='PEN'
 and DATE(thd.dateCreated) = CURRENT_DATE and e.ReportingToEmployee=p_emp_id  and thd.companyId=p_comp_id; 
 
    
             
	SELECT 'help' as newfield, e.firstName,e.lastName,thd.title,thd.dateCreated FROM TicketRaisingHD thd INNER join Employee e on thd.employeeId= e.employeeId where thd.status='Open' and DATE(thd.dateCreated) = CURRENT_DATE and e.ReportingToEmployee=p_emp_id and thd.companyID=p_comp_id;
		
     

END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `pro_department_wise_ctc` (IN `p_comp_id` INT(127), IN `p_process_month` INT(250))  NO SQL
BEGIN

   
    DECLARE e_sqlstate 		VARCHAR(255); 
    DECLARE e_error_no 		INT(127);
    DECLARE e_error_message	TEXT;
    DECLARE e_remark		TEXT;
 
    DECLARE EXIT HANDLER FOR SQLEXCEPTION
  	BEGIN
		GET CURRENT DIAGNOSTICS CONDITION 1 e_sqlstate = RETURNED_SQLSTATE, e_error_no = MYSQL_ERRNO, e_error_message = MESSAGE_TEXT;
        
		SET	@p_process_status	=	1000;
		SET	@p_process_message	=	'Error... please Contact Administrator';
        SET	@p_process_remark=p_process_month;
 	 SET	@p_process_remark	=  fun_set_exception_handling ( 2, 'pro_department_wise_ctc', e_sqlstate, e_error_no, e_error_message, e_remark);
			
	     END;
       
   IF(p_process_month >0 ) THEN
      

  SET @@sql_mode = 'NO_ENGINE_SUBSTITUTION';
      
    DROP TEMPORARY TABLE IF EXISTS temp;
    CREATE TEMPORARY TABLE IF NOT EXISTS temp ( id int(11) NOT NULL AUTO_INCREMENT,
        month	VARCHAR(127) NOT NULL, PRIMARY KEY (id));
   
    
   INSERT INTO temp ( month )
    
    SELECT DISTINCT pl.processMonth  FROM PayRegisterHd pl 

WHERE pl.dateCreated>DATE_SUB(now(),INTERVAL  p_process_month MONTH ) AND
pl.companyId= p_comp_id 
order by month(str_to_date(SUBSTRING_INDEX(pl.processMonth,'-',1),'%b')) desc  limit  p_process_month;
   
   
        
SELECT  dp.departmentName as departmentName, SUM( IFNULL(rp.BasicEarning, 0)+ IFNULL(rp.dearnessAllowanceEarning, 0)+IFNULL(rp.ConveyanceAllowanceEarning, 0)+IFNULL(rp.HRAEarning, 0)+IFNULL(rp.MedicalAllowanceEarning, 0)+IFNULL(rp.AdvanceBonusEarning, 0)+IFNULL(rp.SpecialAllowanceEarning,0)+IFNULL(rp.leaveTravelAllowanceEarning,0)+IFNULL(rp.performanceLinkedIncomeEarning,0)+IFNULL(rp.uniformAllowanceEarning,0)+IFNULL(rp.CompanyBenefitsEarning,0)+IFNULL(rp.ProvidentFundEmployer,0)+IFNULL(rp.ProvidentFundEmployerPension,0)+IFNULL(rp.ESI_Employer,0)) as CTC FROM ReportPayOut rp  
join Department dp on dp.departmentId=rp.departmentId  and rp.companyId=p_comp_id

where 
 
 rp.processMonth in (select month from temp )
 
group by dp.departmentName  ;
       
      
      
      ELSE
      
        SET @@sql_mode = 'NO_ENGINE_SUBSTITUTION';
      
    DROP TEMPORARY TABLE IF EXISTS temp;
    CREATE TEMPORARY TABLE IF NOT EXISTS temp ( id int(11) NOT NULL AUTO_INCREMENT,
        month	VARCHAR(127) NOT NULL, PRIMARY KEY (id));
   
    
    INSERT INTO temp ( month )
    
    SELECT DISTINCT pl.processMonth  FROM PayRegisterHd pl 

WHERE pl.dateCreated>DATE_SUB(now(),INTERVAL  p_process_month MONTH ) AND
pl.companyId= p_comp_id 
order by month(str_to_date(SUBSTRING_INDEX(pl.processMonth,'-',1),'%b')) desc  limit  1;

   
   
        
SELECT  dp.departmentName as departmentName, SUM( IFNULL(rp.BasicEarning, 0)+ IFNULL(rp.dearnessAllowanceEarning, 0)+IFNULL(rp.ConveyanceAllowanceEarning, 0)+IFNULL(rp.HRAEarning, 0)+IFNULL(rp.MedicalAllowanceEarning, 0)+IFNULL(rp.AdvanceBonusEarning, 0)+IFNULL(rp.SpecialAllowanceEarning,0)+IFNULL(rp.leaveTravelAllowanceEarning,0)+IFNULL(rp.performanceLinkedIncomeEarning,0)+IFNULL(rp.uniformAllowanceEarning,0)+IFNULL(rp.CompanyBenefitsEarning,0)+IFNULL(rp.ProvidentFundEmployer,0)+IFNULL(rp.ProvidentFundEmployerPension,0)+IFNULL(rp.ESI_Employer,0)) as CTC FROM ReportPayOut rp  
join Department dp on dp.departmentId=rp.departmentId  and rp.companyId=p_comp_id

where 
 
 rp.processMonth in (select month from temp )
 
group by dp.departmentName  ;
       

End IF;


End$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `pro_department_wise_ration` (IN `p_comp_id` INT(127))  NO SQL
BEGIN

 DECLARE e_sqlstate 		VARCHAR(255); 
    DECLARE e_error_no 		INT(127);
    DECLARE e_error_message	TEXT;
    DECLARE e_remark		TEXT;
 
    DECLARE EXIT HANDLER FOR SQLEXCEPTION
  	BEGIN
		GET CURRENT DIAGNOSTICS CONDITION 1 e_sqlstate = RETURNED_SQLSTATE, e_error_no = MYSQL_ERRNO, e_error_message = MESSAGE_TEXT;
        
		SET	@p_process_status	=	1000;
		SET	@p_process_message	=	'Error... please Contact Administrator';
		SET	@p_process_remark	=  fun_set_exception_handling ( 2, 'pro_department_wise_ctc', e_sqlstate, e_error_no, e_error_message, e_remark);
			
	     END;


 SET @@sql_mode = 'NO_ENGINE_SUBSTITUTION';
select r.departmentId,d.departmentName,round((count(r.departmentId)/(select  count(d.designationName) as value from Designation d where d.activeStatus='AC' and d.companyId=p_comp_id))*100)  as per from ReportPayOut r
join Department d on d.departmentId=r.departmentId and d.companyId=p_comp_id
where r.companyId=p_comp_id
group by r.departmentId ;





END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `pro_designation_wise_ctc` (IN `p_comp_id` INT(127), IN `p_process_month` INT(127))  NO SQL
BEGIN
   DECLARE v_process_month INT(127);
    DECLARE e_sqlstate 		VARCHAR(255); 
    DECLARE e_error_no 		INT(127);
 
    
    DECLARE e_error_message	TEXT;
    DECLARE e_remark		TEXT;
  
    DECLARE EXIT HANDLER FOR SQLEXCEPTION
  	BEGIN
		GET CURRENT DIAGNOSTICS CONDITION 1 e_sqlstate = RETURNED_SQLSTATE, e_error_no = MYSQL_ERRNO, e_error_message = MESSAGE_TEXT;
        
		SET	@p_process_status	=	1000;
		SET	@p_process_message	=	'Error... please Contact Administrator';
		 			
	     END;
         
         SET @@sql_mode = 'NO_ENGINE_SUBSTITUTION';

   
      
       IF(p_process_month>0) then 
     
           DROP TEMPORARY TABLE IF EXISTS temp;
    CREATE TEMPORARY TABLE IF NOT EXISTS temp ( id int(11) NOT NULL AUTO_INCREMENT,
        month	VARCHAR(127) NOT NULL, PRIMARY KEY (id));
   
    
    INSERT INTO temp ( month )
    
    SELECT DISTINCT pl.processMonth  FROM PayRegisterHd pl 

WHERE pl.dateCreated>DATE_SUB(now(),INTERVAL  p_process_month MONTH ) AND
pl.companyId= p_comp_id 
order by month(str_to_date(SUBSTRING_INDEX(pl.processMonth,'-',1),'%b')) desc  limit  p_process_month;
   
SELECT  dp.designationName as designationName, SUM( IFNULL(rp.BasicEarning, 0)+ IFNULL(rp.dearnessAllowanceEarning, 0)+IFNULL(rp.ConveyanceAllowanceEarning, 0)+IFNULL(rp.HRAEarning, 

0)+IFNULL(rp.MedicalAllowanceEarning, 0)+IFNULL(rp.AdvanceBonusEarning, 0)+IFNULL(rp.SpecialAllowanceEarning,0)+IFNULL(rp.leaveTravelAllowanceEarning,0)+IFNULL

(rp.performanceLinkedIncomeEarning,0)+IFNULL(rp.uniformAllowanceEarning,0)+IFNULL(rp.CompanyBenefitsEarning,0)+IFNULL(rp.ProvidentFundEmployer,0)+IFNULL

(rp.ProvidentFundEmployerPension,0)+IFNULL(rp.ESI_Employer,0)) as CTC FROM ReportPayOut rp  
join Employee e on e.employeeId= rp.employeeId and e.activeStatus='AC' and e.companyId=p_comp_id
join Designation dp on dp.designationId=e.designationId and rp.companyId=p_comp_id


where  
 rp.processMonth in (select month from temp )
 group by  dp.designationName ;
 
     
       
       ELSE
       
       SET @@sql_mode = 'NO_ENGINE_SUBSTITUTION';
  DROP TEMPORARY TABLE IF EXISTS temp;
    CREATE TEMPORARY TABLE IF NOT EXISTS temp ( id int(11) NOT NULL AUTO_INCREMENT,
        month	VARCHAR(127) NOT NULL, PRIMARY KEY (id));
   
    INSERT INTO temp ( month )
    
    SELECT DISTINCT pl.processMonth  FROM PayRegisterHd pl 

WHERE pl.dateCreated>DATE_SUB(now(),INTERVAL  p_process_month MONTH ) AND
pl.companyId= p_comp_id 
order by month(str_to_date(SUBSTRING_INDEX(pl.processMonth,'-',1),'%b')) desc  limit  1;
   
SELECT  dp.designationName as designationName, SUM( IFNULL(rp.BasicEarning, 0)+ IFNULL(rp.dearnessAllowanceEarning, 0)+IFNULL(rp.ConveyanceAllowanceEarning, 0)+IFNULL(rp.HRAEarning, 

0)+IFNULL(rp.MedicalAllowanceEarning, 0)+IFNULL(rp.AdvanceBonusEarning, 0)+IFNULL(rp.SpecialAllowanceEarning,0)+IFNULL(rp.leaveTravelAllowanceEarning,0)+IFNULL

(rp.performanceLinkedIncomeEarning,0)+IFNULL(rp.uniformAllowanceEarning,0)+IFNULL(rp.CompanyBenefitsEarning,0)+IFNULL(rp.ProvidentFundEmployer,0)+IFNULL

(rp.ProvidentFundEmployerPension,0)+IFNULL(rp.ESI_Employer,0)) as CTC FROM ReportPayOut rp  
join Employee e on e.employeeId= rp.employeeId and e.activeStatus='AC' and e.companyId=p_comp_id
join Designation dp on dp.designationId=e.designationId and rp.companyId=p_comp_id


where  
 rp.processMonth in (select month from temp )
 group by  dp.designationName ;
 
 
 End if ;


       
 







End$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `pro_empholdsalary_report` (IN `p_selected_month` VARCHAR(50), IN `p_dept_id` VARCHAR(127), IN `p_desg_id` VARCHAR(127), IN `p_grad_id` VARCHAR(127), IN `p_company_id` VARCHAR(127))  NO SQL
BEGIN
 DECLARE v_month varchar (250) ;
  set v_month=concat('"',p_selected_month,'"');


SET @Query = concat(" SELECT h.holdSalaryId,h.remark,e.employeeCode,e.employeeId,e.firstName,e.lastName,p.grossPay,d.departmentName,g.gradesName,h.payrollMonth,h.status,h.userId,h.userIdUpdate,h.dateCreated,h.dateUpdate,h.companyId FROM HoldSalary h join Employee e on h.employeeId=e.employeeId JOIN PayStructureHd p on h.employeeId=p.employeeId 
join Department d on e.departmentId=d.departmentId 
join Grades g on g.gradesId=e.gradesId 
join Designation dg  on  dg.designationId= e.designationId  
where h.status='HO' and h.companyId= ",p_company_id, ' and h.payrollMonth= ', v_month) ;
set @Query1='';
   if(p_dept_id!=null or p_dept_id!='') THEN
 
   set  @Query1=concat(@Query, " and d.departmentId= ",p_dept_id);
     
   end if;
 
   if(p_desg_id!=null or p_desg_id!='') THEN
   
    set  @Query1=concat(@Query, " and dg.designationId= ",p_desg_id);
  
     
   end if;
 
 
 
  
   
    if(p_grad_id!=null or p_grad_id!='') THEN
    set  @Query1=concat(@Query, " and g.gradesId= ",p_grad_id);
     
   end if;


 PREPARE stmt3 FROM @Query1;
 EXECUTE stmt3;
 DEALLOCATE PREPARE stmt3
 ; 




END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `Pro_employee_On_absent_percentage` (IN `p_comp_id` INT)  NO SQL
BEGIN

    DECLARE e_sqlstate 		VARCHAR(255); 
    DECLARE e_error_no 		INT(127);
    DECLARE e_error_message	TEXT;
    DECLARE e_remark		TEXT;
 
    DECLARE EXIT HANDLER FOR SQLEXCEPTION
  	BEGIN
		GET CURRENT DIAGNOSTICS CONDITION 1 e_sqlstate = RETURNED_SQLSTATE, e_error_no = MYSQL_ERRNO, e_error_message = MESSAGE_TEXT;
        
		SET	@p_process_status	=	1000;
		SET	@p_process_message	=	'Error... please Contact Administrator';
		SET	@p_process_remark	=  fun_set_exception_handling ( 1, 'Pro_employee_On_absent_percentage', e_sqlstate, e_error_no, e_error_message, e_remark);
			
	     END;


 SET @@sql_mode = 'NO_ENGINE_SUBSTITUTION';

SELECT round((SUM(payDays-(presense+weekoff+publicholidays+leaves))*100)/(select count(*) from Employee where companyId=p_comp_id AND activeStatus='AC' )) as absent ,processMonth FROM Attendance
where CURRENT_DATE>DATE_SUB(now(), INTERVAL 6 MONTH) and YEAR(dateCreated)=year(now()) AND companyId=p_comp_id
group BY processMonth
 order by month(str_to_date(SUBSTRING_INDEX(processMonth,'-',1),'%b')) ASC LIMIT 6;




END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `pro_emp_attendance_bymonth` (IN `p_comp_id` INT(127), IN `p_emp_id` INT(250))  NO SQL
BEGIN
 DECLARE e_sqlstate 		VARCHAR(255); 
    DECLARE e_error_no 		INT(127);
    DECLARE e_error_message	TEXT;
    DECLARE e_remark		TEXT;
        DECLARE EXIT HANDLER FOR SQLEXCEPTION

	BEGIN
		GET CURRENT DIAGNOSTICS CONDITION 1 e_sqlstate = RETURNED_SQLSTATE, e_error_no = MYSQL_ERRNO, e_error_message = MESSAGE_TEXT;
        
		SET	@p_process_status	=	1000;
		SET	@p_process_message	=	'Error... please Contact Administrator';
		SET	@p_process_remark	=  fun_set_exception_handling ( 2, 'pro_emp_attendance_bymonth', e_sqlstate, e_error_no, e_error_message, e_remark);
			
	     END;
      SELECT(
             SELECT  count(*) FROM AttendanceLogs al WHERE Month(al.attendanceDate)=Month(now()))AS 'presense',
      (select Sum(fun_calculate_days_fromdate_todate(tl.fromDate,tl.toDate)) from TMSLeaveEntries tl WHERE tl.fromDate < now() AND tl.status='APR'AND MONTH(tl.fromDate)= MONTH(now()))AS 'on leave'


;      
         
   
         END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `pro_emp_attrition_joined` (IN `p_comp_id` INT(127))  NO SQL
BEGIN

 DECLARE e_sqlstate 		VARCHAR(255); 
    DECLARE e_error_no 		INT(127);
    DECLARE e_error_message	TEXT;
    DECLARE e_remark		TEXT;
 
    DECLARE EXIT HANDLER FOR SQLEXCEPTION
  	BEGIN
		GET CURRENT DIAGNOSTICS CONDITION 1 e_sqlstate = RETURNED_SQLSTATE, e_error_no = MYSQL_ERRNO, e_error_message = MESSAGE_TEXT;
        
		SET	@p_process_status	=	1000;
		SET	@p_process_message	=	'Error... please Contact Administrator';
		SET	@p_process_remark	=  fun_set_exception_handling ( 4, 'pro_emp_attrition_joined', e_sqlstate, e_error_no, e_error_message, e_remark);
			
	     END;
   



SET @@sql_mode = 'NO_ENGINE_SUBSTITUTION';
 Select COUNT(e.employeeId),DATE_FORMAT(e.dateOfJoining ,"%b") as month from Employee e  where e.activeStatus='Ac' and e.companyId=p_comp_id and YEAR( CAST(e.dateOfJoining as date))=YEAR(NOW())   GROUP BY MONTHNAME(CAST(e.dateOfJoining as date))
 HAVING COUNT(DATE_FORMAT(e.dateOfJoining ,"%b")) >=1  
 ORDER BY e.dateOfJoining;







END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `pro_emp_attrition_resigned` (IN `p_comp_id` INT(127))  NO SQL
BEGIN

DECLARE e_sqlstate 		VARCHAR(255); 
    DECLARE e_error_no 		INT(127);
    DECLARE e_error_message	TEXT;
    DECLARE e_remark		TEXT;
 
    DECLARE EXIT HANDLER FOR SQLEXCEPTION
  	BEGIN
		GET CURRENT DIAGNOSTICS CONDITION 1 e_sqlstate = RETURNED_SQLSTATE, e_error_no = MYSQL_ERRNO, e_error_message = MESSAGE_TEXT;
        
		SET	@p_process_status	=	1000;
		SET	@p_process_message	=	'Error... please Contact Administrator';
		SET	@p_process_remark	=  fun_set_exception_handling ( 2, 'pro_emp_attrition_resigned', e_sqlstate, e_error_no, e_error_message, e_remark);
			
	     END;
           SET @@sql_mode = 'NO_ENGINE_SUBSTITUTION';
 Select COUNT(e.employeeId),DATE_FORMAT(e.dateUpdate ,"%b") as month
  from Employee e  where e.activeStatus='DE' and e.companyId=p_comp_id and YEAR( CAST(e.dateUpdate as 

date))=YEAR(NOW())   GROUP BY MONTHNAME(CAST(e.dateUpdate as date))
 HAVING COUNT(MONTHNAME(CAST(e.dateUpdate as date))) >=1  
 ORDER BY e.dateUpdate ;







END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `pro_emp_count_agewisedistribution` (IN `p_comp_id` INT(127))  NO SQL
BEGIN

 DECLARE e_sqlstate 		VARCHAR(255); 
    DECLARE e_error_no 		INT(127);
    DECLARE e_error_message	TEXT;
    DECLARE e_remark		TEXT;
 
    DECLARE EXIT HANDLER FOR SQLEXCEPTION
  	BEGIN
		GET CURRENT DIAGNOSTICS CONDITION 1 e_sqlstate = RETURNED_SQLSTATE, e_error_no = MYSQL_ERRNO, e_error_message = MESSAGE_TEXT;
        
		SET	@p_process_status	=	1000;
		SET	@p_process_message	=	'Error... please Contact Administrator';
		SET	@p_process_remark	=  fun_set_exception_handling ( 2, 'pro_emp_count_agewisedistribution', e_sqlstate, e_error_no, e_error_message, e_remark);
			
	     END;
      
   

select (SELECT COUNT(e.employeeId) 
from Employee e where e.companyId=p_comp_id and e.activeStatus='AC'  and  (DATE_FORMAT(NOW(), '%Y') - DATE_FORMAT

(e.dateOfBirth, '%Y') - (DATE_FORMAT(NOW(), '00-%m-%d') < DATE_FORMAT(e.dateOfBirth, '00-%m-%d')) between 15 and 

20 ) ) as '15-20'
,
(SELECT COUNT(e.employeeId) 
from Employee e where e.companyId=p_comp_id and e.activeStatus='AC'  and  (DATE_FORMAT(NOW(), '%Y') - DATE_FORMAT

(e.dateOfBirth, '%Y') - (DATE_FORMAT(NOW(), '00-%m-%d') < DATE_FORMAT(e.dateOfBirth, '00-%m-%d')) between 21 and 

30 ) ) as '20-30'

,
(SELECT COUNT(e.employeeId) 
from Employee e where e.companyId=p_comp_id and e.activeStatus='AC'  and  (DATE_FORMAT(NOW(), '%Y') - DATE_FORMAT

(e.dateOfBirth, '%Y') - (DATE_FORMAT(NOW(), '00-%m-%d') < DATE_FORMAT(e.dateOfBirth, '00-%m-%d')) between 31 and 

40 ) ) as '30-40'

,
(SELECT COUNT(e.employeeId) 
from Employee e where e.companyId=p_comp_id and e.activeStatus='AC'  and  (DATE_FORMAT(NOW(), '%Y') - DATE_FORMAT

(e.dateOfBirth, '%Y') - (DATE_FORMAT(NOW(), '00-%m-%d') < DATE_FORMAT(e.dateOfBirth, '00-%m-%d')) between 41 and 

50 ) ) as '40-50' ,
(SELECT COUNT(e.employeeId) 
from Employee e where e.companyId=p_comp_id and e.activeStatus='AC'  and  (DATE_FORMAT(NOW(), '%Y') - DATE_FORMAT

(e.dateOfBirth, '%Y') - (DATE_FORMAT(NOW(), '00-%m-%d') < DATE_FORMAT(e.dateOfBirth, '00-%m-%d')) between 51 and 

60 ) ) as '50-60'
;






END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `pro_emp_count_departmentwise` (IN `p_comp_id` INT(127))  NO SQL
BEGIN



DECLARE e_sqlstate 		VARCHAR(255); 
    DECLARE e_error_no 		INT(127);
    DECLARE e_error_message	TEXT;
    DECLARE e_remark		TEXT;
 
    DECLARE EXIT HANDLER FOR SQLEXCEPTION
  	BEGIN
		GET CURRENT DIAGNOSTICS CONDITION 1 e_sqlstate = RETURNED_SQLSTATE, e_error_no = MYSQL_ERRNO, e_error_message = MESSAGE_TEXT;
        
		SET	@p_process_status	=	1000;
		SET	@p_process_message	=	'Error... please Contact Administrator';
		SET	@p_process_remark	=  fun_set_exception_handling ( 2, 'pro_emp_count_departmentwise', e_sqlstate, e_error_no, e_error_message, e_remark);
			
	     END;


 
 SET @@sql_mode = 'NO_ENGINE_SUBSTITUTION';
SELECT  COUNT(e.employeeId),dp.departmentName as departmentName FROM Employee e 

join Department dp on dp.departmentId=e.departmentId and dp.companyId=p_comp_id

where   e.activeStatus='AC' and e.companyId=p_comp_id
 
group by  dp.departmentName ;






End$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `pro_emp_count_designationwise` (IN `p_comp_id` INT(127))  NO SQL
BEGIN


 DECLARE e_sqlstate 		VARCHAR(255); 
    DECLARE e_error_no 		INT(127);
    DECLARE e_error_message	TEXT;
    DECLARE e_remark		TEXT;
 
    DECLARE EXIT HANDLER FOR SQLEXCEPTION
  	BEGIN
		GET CURRENT DIAGNOSTICS CONDITION 1 e_sqlstate = RETURNED_SQLSTATE, e_error_no = MYSQL_ERRNO, e_error_message = MESSAGE_TEXT;
        
		SET	@p_process_status	=	1000;
		SET	@p_process_message	=	'Error... please Contact Administrator';
		SET	@p_process_remark	=  fun_set_exception_handling ( 2, 'pro_emp_count_designationwise', e_sqlstate, e_error_no, e_error_message, e_remark);
			
	     END;


 
  SET @@sql_mode = 'NO_ENGINE_SUBSTITUTION';
SELECT  COUNT(e.employeeId),dp.designationName as designationName FROM Employee e 

join Designation dp on dp.designationId=e.designationId and dp.companyId=p_comp_id

where   e.activeStatus='AC' and e.companyId=p_comp_id
 
group by  dp.designationName ;





END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `pro_emp_count_todaydate` (IN `p_comp_id` INT(127) UNSIGNED, IN `p_flag` VARCHAR(250))  NO SQL
BEGIN
    
    DECLARE e_sqlstate                 VARCHAR(255); 
    DECLARE e_error_no                 INT(127);
    DECLARE e_error_message        TEXT;
    DECLARE e_remark                TEXT;
 
    DECLARE EXIT HANDLER FOR SQLEXCEPTION
          BEGIN
                GET CURRENT DIAGNOSTICS CONDITION 1 e_sqlstate = RETURNED_SQLSTATE, e_error_no = MYSQL_ERRNO, e_error_message = MESSAGE_TEXT;
        
                SET        @p_process_status        =        1000;
                SET        @p_process_message        =        'Error... please Contact Administrator';
                SET        @p_process_remark        =  fun_set_exception_handling ( 1, 'pro_emp_count_todaydate', e_sqlstate, e_error_no, e_error_message, e_remark);
                        
             END;
      
      
         CASE  WHEN p_flag IS NULL OR p_flag =' ' THEN
  
           SELECT fun_count_empanniversarytodaydate(p_comp_id) as anniversarydate,
         
   fun_count_empbirthdaytodaydate(p_comp_id) as birthdaydate,
   fun_count_empworkanniversarytodaydate(p_comp_id) AS
               workanniversarydate, 
   fun_get_emp_count(p_comp_id) AS empCount,                    fun_get_dept_count(p_comp_id) As deptCount,
 fun_count_holiday_monthwise(p_comp_id) As holidayCount,
 fun_candidate_initiate_count(p_comp_id)  As initCanCount ,
 fun_candidate_pending_Count(p_comp_id) As penCanCount,
 fun_noticePeriod_emp_count(p_comp_id) As empNoticePeriodCount,
 fun_count_emp_exit_this_month(p_comp_id) As empExitThisMonthCount,
 fun_get_Count_emp_settlementPending(p_comp_id)  As empFinalSettlementCount

 ;
        
       WHEN p_flag='data_birhtday' THEN
       
CALL pro_birthday(p_comp_id);     

        
                         WHEN p_flag='data_anniversary' THEN
CALL pro_anniversary(p_comp_id);    
                
                         WHEN p_flag='data_work' THEN
             
CALL pro_work_anniversary(p_comp_id);    

     WHEN p_flag='data_holiday' THEN
             
select tm.holidayName,tm.fromDate,tm.toDate,tm.day
                  from 
      TMSHolidays tm  join 
      TMSLeavePeriod tp  on  tm.leavePeriodId= tp.leavePeriodId and   tp.activeStatus='AC'
     
      where (tm.activeStatus='AC')
       and  (MONTH(tm.fromDate)=MONTH(NOW()) OR MONTH(tm.toDate)=MONTH(NOW())) and
       tp.companyId=p_comp_id ;
  
                   
        
                
       END CASE;

END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `pro_emp_doc_confirmation` (IN `p_comp_id` INT(127))  NO SQL
BEGIN

    DECLARE e_sqlstate 		VARCHAR(255); 
    DECLARE e_error_no 		INT(127);
    DECLARE e_error_message	TEXT;
    DECLARE e_remark		TEXT;
   
   
  
    DECLARE EXIT HANDLER FOR SQLEXCEPTION
  	BEGIN
		GET CURRENT DIAGNOSTICS CONDITION 1 e_sqlstate = RETURNED_SQLSTATE, e_error_no = MYSQL_ERRNO, e_error_message = MESSAGE_TEXT;
        
		SET	@p_process_status	=	1000;
		SET	@p_process_message	=	'Error... please Contact Administrator';
		SET	@p_process_remark	=  fun_set_exception_handling ( 4, 'pro_department_wise_ctc', e_sqlstate, e_error_no, e_error_message, e_remark);
        
  
	     END;
    

SET @@sql_mode = 'NO_ENGINE_SUBSTITUTION';
select * from ( select UPPER(concat(concat(emp.firstName,' '),emp.lastName)) as Name,emp.employeeCode,emp.employeeLogoPath,desg.designationName,dept.departmentName, (case when (md.UI is null or md.UI='') and ('UI'=(select mi.docCode from MandatoryInfo mi 

where 

mi.status='AC' and mi.docCode='UI' and mi.companyId=p_comp_id)) THEN
    'Aadhar Card,' end ) as UI 
    ,(case when (md.UA is null or md.UA='') and ('UA'=(select mi.docCode from MandatoryInfo mi where 

mi.status='AC' and mi.docCode='UA' and mi.companyId=p_comp_id)) THEN
    'UAN,' end ) as UA 
    ,(case when (md.ES is null or md.ES='') and ('ES'=(select mi.docCode from MandatoryInfo mi where 

mi.status='AC' and mi.docCode='ES' and mi.companyId=p_comp_id)) THEN
    'ESI,' end ) as ES 
    ,(case when (md.BA is null or md.BA='') and ('BA'=(select mi.docCode from MandatoryInfo mi where 

mi.status='AC' and mi.docCode='BA' and mi.companyId=p_comp_id)) THEN
    'Bank Account Number,' end ) as BA
    ,(case when (md.AI is null or md.AI='') and ('AI'=(select mi.docCode from MandatoryInfo mi where 

mi.status='AC' and mi.docCode='AI' and mi.companyId=p_comp_id)) THEN
    'Accidental Insurance,' end ) as AI ,(case when (md.MI is null or md.MI='') and ('MI'=(select 

mi.docCode from MandatoryInfo mi where mi.status='AC' and mi.docCode='MI' and mi.companyId=p_comp_id)) THEN
    'Medical Insurance' end ) as MI ,emp.firstName,emp.lastName
    from MandatoryInfoCheck md
                
    join Employee emp on emp.employeeId=md.employeeId and emp.companyId=p_comp_id and emp.activeStatus='AC'
 left join Department dept on dept.departmentId=emp.departmentId
 left join Designation desg on desg.designationId=emp.designationId
                
                ) as b where b.UA is not null or b.UI is not null or b.ES is not null or b.BA is not null or b.AI 

is not null or b.MI is not null GROUP by b.Name  

;









End$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `pro_emp_genderwise_ratio` (IN `p_comp_id` INT(127))  NO SQL
BEGIN

 DECLARE e_sqlstate 		VARCHAR(255); 
    DECLARE e_error_no 		INT(127);
    DECLARE e_error_message	TEXT;
    DECLARE e_remark		TEXT;
 
    DECLARE EXIT HANDLER FOR SQLEXCEPTION
  	BEGIN
		GET CURRENT DIAGNOSTICS CONDITION 1 e_sqlstate = RETURNED_SQLSTATE, e_error_no = MYSQL_ERRNO, e_error_message = MESSAGE_TEXT;
        
		SET	@p_process_status	=	1000;
		SET	@p_process_message	=	'Error... please Contact Administrator';
		SET	@p_process_remark	=  fun_set_exception_handling ( 2, 'pro_department_wise_ctc', e_sqlstate, e_error_no, e_error_message, e_remark);
			
	     END;
      
      
        SET @@sql_mode = 'NO_ENGINE_SUBSTITUTION';


select * from( select concat(round(((select COUNT(*) from Employee e where  e.companyId=p_comp_id and gender = 'F') / (select COUNT(*) from Employee e where e.companyId=p_comp_id))*100),'%') as woman , 
              concat(round(((select COUNT(*) from Employee e where  e.companyId=p_comp_id and gender = 'M') / (select COUNT(*) from Employee e where  e.companyId=p_comp_id ))*100),'%') as man ,
                concat(round(((select COUNT(*) from Employee e where  e.companyId=p_comp_id and gender = 'T') / (select COUNT(*) from Employee e where  e.companyId=p_comp_id))*100),'%') as other
              from Employee where CompanyId =p_comp_id ) as e  group by e.woman ;








END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `pro_emp_notification` (IN `p_comp_id` INT(127), IN `p_flag` VARCHAR(255))  NO SQL
BEGIN

 DECLARE e_sqlstate 		VARCHAR(255); 
    DECLARE e_error_no 		INT(127);
    DECLARE e_error_message	TEXT;
    DECLARE e_remark		TEXT;
 
    DECLARE EXIT HANDLER FOR SQLEXCEPTION
  	BEGIN
		GET CURRENT DIAGNOSTICS CONDITION 1 e_sqlstate = RETURNED_SQLSTATE, e_error_no = MYSQL_ERRNO, e_error_message = MESSAGE_TEXT;
        
		SET	@p_process_status	=	1000;
		SET	@p_process_message	=	'Error... please Contact Administrator';
		SET	@p_process_remark	=  fun_set_exception_handling ( 1, 'pro_emp_count_todaydate', e_sqlstate, e_error_no, e_error_message, e_remark);
			
	     END;
      
      
         CASE  WHEN p_flag IS NULL OR p_flag ='' THEN
  
	   SELECT fun_count_employee_notification(p_comp_id,'Both') as notificationCount
            
         ;
		    
		
		 WHEN p_flag='noti_list' THEN

      select  'Help Desk Notification  ' , fun_count_employee_notification(p_comp_id,'Ticket') 	,
	 'Sepration Notification  ' , fun_count_employee_notification(p_comp_id,'Sepration') ;
			
            WHEN p_flag='view' THEN
  
            	select '';
		
			
       END CASE;











End$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `pro_emp_payrollmonth_status` (IN `p_comp_id` INT(127))  NO SQL
BEGIN


    DECLARE e_sqlstate 		VARCHAR(255); 
    DECLARE e_error_no 		INT(127);
    DECLARE e_error_message	TEXT;
    DECLARE e_remark		TEXT;
 
    DECLARE EXIT HANDLER FOR SQLEXCEPTION
  	BEGIN
		GET CURRENT DIAGNOSTICS CONDITION 1 e_sqlstate = RETURNED_SQLSTATE, e_error_no = MYSQL_ERRNO, e_error_message = MESSAGE_TEXT;
        
		SET	@p_process_status	=	1000;
		SET	@p_process_message	=	'Error... please Contact Administrator';
		SET	@p_process_remark	=  fun_set_exception_handling ( 2, 'pro_emp_payrollmonth_status', e_sqlstate, e_error_no, e_error_message, e_remark);
			
	     END;


SET @@sql_mode = 'NO_ENGINE_SUBSTITUTION';
 SELECT pl.processMonth,GROUP_CONCAT( d.departmentName), (CASE when (pl.isPayRollLocked='true') THEN 
 
 'Generate' end)  as 'Generate Status',
 (CASE when (pl.isPayRollLocked='false') THEN 
 
 'Disbursement' end)  as 'Disbursement Status'
 FROM PayRollLock pl 
join Department d on d.departmentId=pl.departmentId and d.activeStatus='Ac'
   where  pl.companyId=p_comp_id and  SPLIT_STR(pl.processMonth,'-',2)=YEAR(now())

    GROUP by pl.processMonth 
  
  ORDER by month(str_to_date(SUBSTRING_INDEX(pl.processMonth,'-',1),'%b')) ;
  







END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `pro_emp_reportpt_bymonth` (IN `p_comp_id` INT(127), IN `p_from_month` VARCHAR(250), IN `p_to_month` VARCHAR(250), IN `p_emp_id` INT(127), IN `p_state_id` INT(127), IN `p_dept_id` INT(127))  NO SQL
BEGIN

 DECLARE e_sqlstate 		VARCHAR(255); 
    DECLARE e_error_no 		INT(127);
    DECLARE e_error_message	TEXT;
    DECLARE e_remark		TEXT;
 
    DECLARE EXIT HANDLER FOR SQLEXCEPTION
  	BEGIN
		GET CURRENT DIAGNOSTICS CONDITION 1 e_sqlstate = RETURNED_SQLSTATE, e_error_no = MYSQL_ERRNO, e_error_message = MESSAGE_TEXT;
        
		SET	@p_process_status	=	1000;
		SET	@p_process_message	=	'Error... please Contact Administrator';
		SET	@p_process_remark	=  fun_set_exception_handling ( 2, 'pro_emp_ptreport_bymonth', e_sqlstate, e_error_no, e_error_message, e_remark);
			
	     END;

SET @@sql_mode = 'NO_ENGINE_SUBSTITUTION';
 
 CASE   WHEN((p_comp_id!=null or p_comp_id!='') and           (p_from_month!=null or p_from_month!='')
 and (p_to_month!=null or p_to_month!='')     
 and (p_emp_id!=null or p_emp_id!='')
 and (p_dept_id!=null or p_dept_id!='') 
 and (p_state_id!=null or p_state_id!='')         
         ) THEN
     SELECT
    rp.employeeCode,
    rp.name,
    dept.departmentName,
    desg.designationName,
    rp.processMonth,
    rp.pt,
    st.stateName
FROM
    ReportPayOut rp
JOIN Employee emp on emp.employeeId=rp.employeeId 
JOIN Designation desg on desg.designationId=emp.designationId
JOIN Department dept ON
    rp.departmentId = dept.departmentId 

  JOIN State st ON
  emp.stateId = st.stateId 
   where  str_to_date(rp.processMonth,'%b-%Y') BETWEEN  str_to_date(p_from_month,'%b-%Y') and  str_to_date(p_to_month,'%b-%Y')  
   and rp.companyId=1 and rp.employeeId=p_emp_id and dept.departmentId=p_dept_id and st.stateId=p_state_id
   order by rp.employeeId ASC;

 WHEN((p_comp_id!=null OR p_comp_id!='') and           (p_from_month!=null OR p_from_month!='')
 and (p_to_month!=null OR p_to_month!='')     
 and (p_emp_id!=null OR p_emp_id!='')
 and (p_dept_id!=null OR p_dept_id!='')   ) THEN
      SELECT
    rp.employeeCode,
    rp.name,
    dept.departmentName,
    desg.designationName,
    rp.processMonth,
    rp.pt,
    st.stateName
FROM
    ReportPayOut rp
JOIN Employee emp on emp.employeeId=rp.employeeId 
JOIN Designation desg on desg.designationId=emp.designationId
JOIN Department dept ON
    rp.departmentId = dept.departmentId 

  JOIN State st ON
  emp.stateId = st.stateId 
   where  str_to_date(rp.processMonth,'%b-%Y') BETWEEN  str_to_date(p_from_month,'%b-%Y') and  str_to_date(p_to_month,'%b-%Y')  
   and rp.companyId=1 and rp.employeeId=p_emp_id and dept.departmentId=p_dept_id
   order by rp.employeeId ASC;

  WHEN((p_comp_id!=null or p_comp_id!='') and           (p_from_month!=null or p_from_month!='')
 and (p_to_month!=null or p_to_month!='')     
 and (p_dept_id!=null or p_dept_id!='') 
  and (p_state_id!=null or p_state_id!='')  ) THEN
     SELECT
    rp.employeeCode,
    rp.name,
    dept.departmentName,
    desg.designationName,
    rp.processMonth,
    rp.pt,
    st.stateName
FROM
    ReportPayOut rp
JOIN Employee emp on emp.employeeId=rp.employeeId 
JOIN Designation desg on desg.designationId=emp.designationId
JOIN Department dept ON
    rp.departmentId = dept.departmentId 

  JOIN State st ON
  emp.stateId = st.stateId 
   where  str_to_date(rp.processMonth,'%b-%Y') BETWEEN  str_to_date(p_from_month,'%b-%Y') and  str_to_date(p_to_month,'%b-%Y')  
   and rp.companyId=1 and  dept.departmentId=p_dept_id and st.stateId=p_state_id
   order by rp.employeeId ASC;

 WHEN((p_comp_id!=null or p_comp_id!='') and           (p_from_month!=null or p_from_month!='')
 and (p_emp_id!=null or p_emp_id!='')
 and (p_state_id!=null or p_state_id!='')        ) THEN
       SELECT
    rp.employeeCode,
    rp.name,
    dept.departmentName,
    desg.designationName,
    rp.processMonth,
    rp.pt,
    st.stateName
FROM
    ReportPayOut rp
JOIN Employee emp on emp.employeeId=rp.employeeId 
JOIN Designation desg on desg.designationId=emp.designationId
JOIN Department dept ON
    rp.departmentId = dept.departmentId 

  JOIN State st ON
  emp.stateId = st.stateId and emp.stateId=p_state_id
   where  str_to_date(rp.processMonth,'%b-%Y') BETWEEN  str_to_date(p_from_month,'%b-%Y') and  str_to_date(p_to_month,'%b-%Y')  
   and rp.companyId=1 and rp.employeeId=p_emp_id and st.stateId=p_state_id
   order by rp.employeeId ASC;
   

 WHEN((p_comp_id!=null or p_comp_id!='') and           (p_from_month!=null or p_from_month!='')
 and (p_to_month!=null or p_to_month!='')     
 and (p_emp_id!=null or p_emp_id!='')          
         ) THEN
      
     SELECT
    rp.employeeCode,
    rp.name,
    dept.departmentName,
    desg.designationName,
    rp.processMonth,
    rp.pt,
    st.stateName
FROM
    ReportPayOut rp
JOIN Employee emp on emp.employeeId=rp.employeeId and emp.employeeId =p_emp_id
JOIN Designation desg on desg.designationId=emp.designationId
JOIN Department dept ON
    rp.departmentId = dept.departmentId 

  JOIN State st ON
  emp.stateId = st.stateId
   where  str_to_date(rp.processMonth,'%b-%Y') BETWEEN  str_to_date(p_from_month,'%b-%Y') and  str_to_date(p_to_month,'%b-%Y')  
   and rp.companyId=p_comp_id and rp.employeeId=p_emp_id
   order by rp.employeeId ASC;


  
   
   
   
    
      WHEN((p_comp_id!=null or p_comp_id!='') and           (p_from_month!=null or p_from_month!='')
 and (p_to_month!=null or p_to_month!='')     
 and (p_state_id!=null or p_state_id!='')  ) THEN
    SELECT
    rp.employeeCode,
    rp.name,
    dept.departmentName,
    desg.designationName,
    rp.processMonth,
    rp.pt,
    st.stateName
FROM
    ReportPayOut rp
JOIN Employee emp on emp.employeeId=rp.employeeId 
JOIN Designation desg on desg.designationId=emp.designationId
JOIN Department dept ON
    rp.departmentId = dept.departmentId 

  JOIN State st ON
  emp.stateId = st.stateId 
   where  str_to_date(rp.processMonth,'%b-%Y') BETWEEN  str_to_date(p_from_month,'%b-%Y') and  str_to_date(p_to_month,'%b-%Y')  
   and rp.companyId=1 and   st.stateId=p_state_id
   order by rp.employeeId ASC;




      WHEN((p_comp_id!=null or p_comp_id!='') and           (p_from_month!=null or p_from_month!='')
 and (p_to_month!=null or p_to_month!='')     
 and (p_dept_id!=null or p_dept_id!='')  ) THEN
    SELECT
    rp.employeeCode,
    rp.name,
    dept.departmentName,
    desg.designationName,
    rp.processMonth,
    rp.pt,
    st.stateName
FROM
    ReportPayOut rp
JOIN Employee emp on emp.employeeId=rp.employeeId 
JOIN Designation desg on desg.designationId=emp.designationId
JOIN Department dept ON
    rp.departmentId = dept.departmentId 

  JOIN State st ON
  emp.stateId = st.stateId 
   where  str_to_date(rp.processMonth,'%b-%Y') BETWEEN  str_to_date(p_from_month,'%b-%Y') and  str_to_date(p_to_month,'%b-%Y')  
   and rp.companyId=1 and   dept.departmentId=p_dept_id
   order by rp.employeeId ASC;



WHEN((p_comp_id != null or p_comp_id!='') and           (p_from_month!=null or p_from_month!='')
 and (p_to_month!=null or p_to_month!='')         
          ) THEN
          
      SELECT
    rp.employeeCode,
    rp.name,
    dept.departmentName,
    desg.designationName,
    rp.processMonth,
    rp.pt,
    st.stateName
FROM
    ReportPayOut rp
JOIN Employee emp on emp.employeeId=rp.employeeId 
JOIN Designation desg on desg.designationId=emp.designationId
JOIN Department dept ON
    rp.departmentId = dept.departmentId 

  JOIN State st ON
  emp.stateId = st.stateId
   where  str_to_date(rp.processMonth,'%b-%Y') BETWEEN  str_to_date(p_from_month,'%b-%Y') and  str_to_date(p_to_month,'%b-%Y')  
   and rp.companyId=p_comp_id 
   order by rp.employeeId ASC;
      
     
END CASE;    





END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `pro_emp_sepration_status` (IN `p_comp_id` INT(127))  NO SQL
BEGIN

    DECLARE e_sqlstate 		VARCHAR(255); 
    DECLARE e_error_no 		INT(127);
    DECLARE e_error_message	TEXT;
    DECLARE e_remark		TEXT;
 
    DECLARE EXIT HANDLER FOR SQLEXCEPTION
  	BEGIN
		GET CURRENT DIAGNOSTICS CONDITION 1 e_sqlstate = RETURNED_SQLSTATE, e_error_no = MYSQL_ERRNO, e_error_message = MESSAGE_TEXT;
        
		SET	@p_process_status	=	1000;
		SET	@p_process_message	=	'Error... please Contact Administrator';
		SET	@p_process_remark	=  fun_set_exception_handling ( 4, 'pro_department_wise_ctc', e_sqlstate, e_error_no, e_error_message, e_remark);
			
	     END;
    
SELECT  UPPER(concat(concat(emp.firstName,' '),emp.lastName)) as Name,emp.employeeCode,desg.designationName,dept.departmentName ,s.dateCreated FROM Separation s 

 join Employee emp on emp.employeeId=s.employeeId and emp.activeStatus='AC'
 left join Department dept on dept.departmentId=emp.departmentId
 left join Designation desg on desg.designationId=emp.designationId

WHERE s.status='P'  and s.companyId=p_comp_id and 
DATE(s.dateCreated) > (NOW() - INTERVAL 7 DAY) ;






END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `pro_emp_ticket_status` (IN `p_comp_id` INT(127), IN `p_user_id` INT(250), IN `p_role_name` VARCHAR(250))  NO SQL
BEGIN

    DECLARE e_sqlstate 		VARCHAR(255); 
    DECLARE e_error_no 		INT(127);
    DECLARE e_error_message	TEXT;
    DECLARE e_remark		TEXT;
 
    DECLARE EXIT HANDLER FOR SQLEXCEPTION
  	BEGIN
		GET CURRENT DIAGNOSTICS CONDITION 1 e_sqlstate = RETURNED_SQLSTATE, e_error_no = MYSQL_ERRNO, e_error_message = MESSAGE_TEXT;
        
		SET	@p_process_status	=	1000;
		SET	@p_process_message	=	'Error... please Contact Administrator';
		SET	@p_process_remark	=  fun_set_exception_handling ( 2, 'pro_emp_ticket_status', e_sqlstate, e_error_no, e_error_message, e_remark);
			
	     END;
         
     
     IF( trim(p_role_name)='FSSUser')   THEN 
     
     Select  ( Select SUM( ( SELECT COUNT(*) FROM TicketRaisingHD td
 WHERE  YEAR(td.dateCreated)=YEAR(NOW()) 
 and td.companyId=p_comp_id  and td.status='Open'  ) +
 (SELECT COUNT(*) FROM TicketRaisingHD td
 WHERE  YEAR(td.dateCreated)=YEAR(NOW()) 
 and td.companyId=p_comp_id and td.status='Closed') +
                     
  (SELECT COUNT(*) FROM TicketRaisingHD td
 WHERE  YEAR(td.dateCreated)=YEAR(NOW()) 
 and td.companyId=p_comp_id and td.status='Completed') +                    

( SELECT COUNT(*) FROM TicketRaisingHD td
 WHERE  YEAR(td.dateCreated)=YEAR(NOW()) 
 and td.companyId=p_comp_id and td.status='InProgress') ) ) as 'Ticket Logged' ,
 
 (SELECT COUNT(*) FROM TicketRaisingHD td
 WHERE  YEAR(td.dateCreated)=YEAR(NOW()) 
 and td.companyId=p_comp_id and td.status='Completed') as 'Ticket Resolved' ,

(( Select SUM( ( SELECT COUNT(*) FROM TicketRaisingHD td
 WHERE  YEAR(td.dateCreated)=YEAR(NOW()) 
 and td.companyId=p_comp_id  and td.status='Open'  ) +
 (SELECT COUNT(*) FROM TicketRaisingHD td
 WHERE  YEAR(td.dateCreated)=YEAR(NOW()) 
 and td.companyId=p_comp_id and td.status='Closed') +
                     
  (SELECT COUNT(*) FROM TicketRaisingHD td
 WHERE  YEAR(td.dateCreated)=YEAR(NOW()) 
 and td.companyId=p_comp_id and td.status='Completed') +                    

( SELECT COUNT(*) FROM TicketRaisingHD td
 WHERE  YEAR(td.dateCreated)=YEAR(NOW()) 
 and td.companyId=p_comp_id and td.status='InProgress') ) ) - (SELECT COUNT(*) FROM TicketRaisingHD td
 WHERE  YEAR(td.dateCreated)=YEAR(NOW()) 
 and td.companyId=p_comp_id and td.status='Completed')) as 'Ticket Pending' 


;
      
  

     
  ELSEIF(trim(p_role_name)='ESSUser') THEN
  
   set @emp_id= (SELECT  u.nameOfUser from Users u
 where u.userId=p_user_id and u.companyId=p_comp_id);


Select  ( Select SUM( ( SELECT COUNT(*) FROM TicketRaisingHD td
 WHERE  YEAR(td.dateCreated)=YEAR(NOW()) 
 and td.companyId=p_comp_id and td.employeeId=(select e.employeeId from Employee e where e.employeeCode=@emp_id)  and td.status='Open'  ) +
 (SELECT COUNT(*) FROM TicketRaisingHD td
 WHERE  YEAR(td.dateCreated)=YEAR(NOW()) 
 and td.companyId=p_comp_id  and td.employeeId=(select e.employeeId from Employee e where e.employeeCode=@emp_id)  and td.status='Closed') +
                     
  (SELECT COUNT(*) FROM TicketRaisingHD td
 WHERE  YEAR(td.dateCreated)=YEAR(NOW()) 
 and td.companyId=p_comp_id and td.employeeId=(select e.employeeId from Employee e where e.employeeCode=@emp_id)  and td.status='Completed') +                    

( SELECT COUNT(*) FROM TicketRaisingHD td
 WHERE  YEAR(td.dateCreated)=YEAR(NOW()) 
 and td.companyId=p_comp_id and td.employeeId=(select e.employeeId from Employee e where e.employeeCode=@emp_id)   and td.status='InProgress') ) ) as 'Ticket Logged' ,
 
 (SELECT COUNT(*) FROM TicketRaisingHD td
 WHERE  YEAR(td.dateCreated)=YEAR(NOW()) 
 and td.companyId=p_comp_id and td.employeeId=(select e.employeeId from Employee e where e.employeeCode=@emp_id)   and td.status='Completed') as 'Ticket Resolved' ,

(( Select SUM( ( SELECT COUNT(*) FROM TicketRaisingHD td
 WHERE  YEAR(td.dateCreated)=YEAR(NOW()) 
 and td.companyId=p_comp_id and td.employeeId=(select e.employeeId from Employee e where e.employeeCode=@emp_id)   and td.status='Open'  ) +
 (SELECT COUNT(*) FROM TicketRaisingHD td
 WHERE  YEAR(td.dateCreated)=YEAR(NOW()) 
 and td.companyId=p_comp_id and td.employeeId=(select e.employeeId from Employee e where e.employeeCode=@emp_id)   and td.status='Closed') +
                     
  (SELECT COUNT(*) FROM TicketRaisingHD td
 WHERE  YEAR(td.dateCreated)=YEAR(NOW()) 
 and td.companyId=p_comp_id  and td.employeeId=(select e.employeeId from Employee e where e.employeeCode=@emp_id)   and td.status='Completed') +                    

( SELECT COUNT(*) FROM TicketRaisingHD td
 WHERE  YEAR(td.dateCreated)=YEAR(NOW()) 
 and td.companyId=p_comp_id and td.employeeId=(select e.employeeId from Employee e where e.employeeCode=@emp_id)   and td.status='InProgress') ) ) - (SELECT COUNT(*) FROM TicketRaisingHD td
 WHERE  YEAR(td.dateCreated)=YEAR(NOW()) 
 and td.companyId=p_comp_id  and td.employeeId=(select e.employeeId from Employee e where e.employeeCode=@emp_id)  and td.status='Completed')) as 'Ticket Pending' 


;

 ELSEIF(trim(p_role_name)='Admin') THEN
 
 
 Select  ( Select SUM( ( SELECT COUNT(*) FROM TicketRaisingHD td
 WHERE  YEAR(td.dateCreated)=YEAR(NOW()) 
 and td.companyId=p_comp_id  and td.status='Open'  ) +
 (SELECT COUNT(*) FROM TicketRaisingHD td
 WHERE  YEAR(td.dateCreated)=YEAR(NOW()) 
 and td.companyId=p_comp_id and td.status='Closed') +
                     
  (SELECT COUNT(*) FROM TicketRaisingHD td
 WHERE  YEAR(td.dateCreated)=YEAR(NOW()) 
 and td.companyId=p_comp_id and td.status='Completed') +                    

( SELECT COUNT(*) FROM TicketRaisingHD td
 WHERE  YEAR(td.dateCreated)=YEAR(NOW()) 
 and td.companyId=p_comp_id and td.status='InProgress') ) ) as 'Ticket Logged' ,
 
 (SELECT COUNT(*) FROM TicketRaisingHD td
 WHERE  YEAR(td.dateCreated)=YEAR(NOW()) 
 and td.companyId=p_comp_id and td.status='Completed') as 'Ticket Resolved' ,

(( Select SUM( ( SELECT COUNT(*) FROM TicketRaisingHD td
 WHERE  YEAR(td.dateCreated)=YEAR(NOW()) 
 and td.companyId=p_comp_id  and td.status='Open'  ) +
 (SELECT COUNT(*) FROM TicketRaisingHD td
 WHERE  YEAR(td.dateCreated)=YEAR(NOW()) 
 and td.companyId=p_comp_id and td.status='Closed') +
                     
  (SELECT COUNT(*) FROM TicketRaisingHD td
 WHERE  YEAR(td.dateCreated)=YEAR(NOW()) 
 and td.companyId=p_comp_id and td.status='Completed') +                    

( SELECT COUNT(*) FROM TicketRaisingHD td
 WHERE  YEAR(td.dateCreated)=YEAR(NOW()) 
 and td.companyId=p_comp_id and td.status='InProgress') ) ) - (SELECT COUNT(*) FROM TicketRaisingHD td
 WHERE  YEAR(td.dateCreated)=YEAR(NOW()) 
 and td.companyId=p_comp_id and td.status='Completed')) as 'Ticket Pending' 


;
else 

  set @emp_id= (SELECT  u.nameOfUser from Users u
 where u.userId=p_user_id and u.companyId=p_comp_id);


Select  ( Select SUM( ( SELECT COUNT(*) FROM TicketRaisingHD td
 WHERE  YEAR(td.dateCreated)=YEAR(NOW()) 
 and td.companyId=p_comp_id and td.employeeId=(select e.employeeId from Employee e where e.employeeCode=@emp_id)  and td.status='Open'  ) +
 (SELECT COUNT(*) FROM TicketRaisingHD td
 WHERE  YEAR(td.dateCreated)=YEAR(NOW()) 
 and td.companyId=p_comp_id  and td.employeeId=(select e.employeeId from Employee e where e.employeeCode=@emp_id)  and td.status='Closed') +
                     
  (SELECT COUNT(*) FROM TicketRaisingHD td
 WHERE  YEAR(td.dateCreated)=YEAR(NOW()) 
 and td.companyId=p_comp_id and td.employeeId=(select e.employeeId from Employee e where e.employeeCode=@emp_id)  and td.status='Completed') +                    

( SELECT COUNT(*) FROM TicketRaisingHD td
 WHERE  YEAR(td.dateCreated)=YEAR(NOW()) 
 and td.companyId=p_comp_id and td.employeeId=(select e.employeeId from Employee e where e.employeeCode=@emp_id)   and td.status='InProgress') ) ) as 'Ticket Logged' ,
 
 (SELECT COUNT(*) FROM TicketRaisingHD td
 WHERE  YEAR(td.dateCreated)=YEAR(NOW()) 
 and td.companyId=p_comp_id and td.employeeId=(select e.employeeId from Employee e where e.employeeCode=@emp_id)   and td.status='Completed') as 'Ticket Resolved' ,

(( Select SUM( ( SELECT COUNT(*) FROM TicketRaisingHD td
 WHERE  YEAR(td.dateCreated)=YEAR(NOW()) 
 and td.companyId=p_comp_id and td.employeeId=(select e.employeeId from Employee e where e.employeeCode=@emp_id)   and td.status='Open'  ) +
 (SELECT COUNT(*) FROM TicketRaisingHD td
 WHERE  YEAR(td.dateCreated)=YEAR(NOW()) 
 and td.companyId=p_comp_id and td.employeeId=(select e.employeeId from Employee e where e.employeeCode=@emp_id)   and td.status='Closed') +
                     
  (SELECT COUNT(*) FROM TicketRaisingHD td
 WHERE  YEAR(td.dateCreated)=YEAR(NOW()) 
 and td.companyId=p_comp_id  and td.employeeId=(select e.employeeId from Employee e where e.employeeCode=@emp_id)   and td.status='Completed') +                    

( SELECT COUNT(*) FROM TicketRaisingHD td
 WHERE  YEAR(td.dateCreated)=YEAR(NOW()) 
 and td.companyId=p_comp_id and td.employeeId=(select e.employeeId from Employee e where e.employeeCode=@emp_id)   and td.status='InProgress') ) ) - (SELECT COUNT(*) FROM TicketRaisingHD td
 WHERE  YEAR(td.dateCreated)=YEAR(NOW()) 
 and td.companyId=p_comp_id  and td.employeeId=(select e.employeeId from Employee e where e.employeeCode=@emp_id)  and td.status='Completed')) as 'Ticket Pending' 


;

END IF;


END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `pro_esi_contribution` (IN `p_comp_id` INT(127), IN `p_process_month` INT(127))  NO SQL
BEGIN

DECLARE e_sqlstate 		VARCHAR(255); 
    DECLARE e_error_no 		INT(127);
    DECLARE e_error_message	TEXT;
    DECLARE e_remark		TEXT;
 
    DECLARE EXIT HANDLER FOR SQLEXCEPTION
  	BEGIN
		GET CURRENT DIAGNOSTICS CONDITION 1 e_sqlstate = RETURNED_SQLSTATE, e_error_no = MYSQL_ERRNO, e_error_message = MESSAGE_TEXT;
        
		SET	@p_process_status	=	1000;
		SET	@p_process_message	=	'Error... please Contact Administrator';
		SET	@p_process_remark	=  fun_set_exception_handling ( 1, 'pro_esi_contribution', e_sqlstate, e_error_no, e_error_message, e_remark);
			
	     END;
          SET @@sql_mode = 'NO_ENGINE_SUBSTITUTION';

    
         IF(p_process_month>0) then 
         
       
     DROP TEMPORARY TABLE IF EXISTS temp;
    CREATE TEMPORARY TABLE IF NOT EXISTS temp ( id int(11) NOT NULL AUTO_INCREMENT,
        month	VARCHAR(127) NOT NULL, PRIMARY KEY (id));
   
    INSERT INTO temp ( month )
    
    SELECT DISTINCT pl.processMonth  FROM PayRegisterHd pl 

WHERE pl.dateCreated>DATE_SUB(now(),INTERVAL  p_process_month MONTH ) AND
pl.companyId= p_comp_id 
order by month(str_to_date(SUBSTRING_INDEX(pl.processMonth,'-',1),'%b')) desc  limit  p_process_month;
  
select  SUM( IFNULL(p.ESI_Employee, 0)) as emp_esi,sum(IFNULL(p.ESI_Employer,0)) empr_esi  from ReportPayOut p  

inner join Employee e on e.employeeId=p.employeeId and e.activeStatus='AC'and  e.companyId=p_comp_id

where  p.processMonth in (select month from temp )

 order by month(str_to_date(SUBSTRING_INDEX(p.processMonth,'-',1),'%b'));


         
         
         
         
         ELSE
         
 DROP TEMPORARY TABLE IF EXISTS temp;
    CREATE TEMPORARY TABLE IF NOT EXISTS temp ( id int(11) NOT NULL AUTO_INCREMENT,
        month	VARCHAR(127) NOT NULL, PRIMARY KEY (id));
   
    
     INSERT INTO temp ( month )
    
    SELECT DISTINCT pl.processMonth  FROM PayRegisterHd pl 

WHERE pl.dateCreated>DATE_SUB(now(),INTERVAL  p_process_month MONTH ) AND
pl.companyId= p_comp_id 
order by month(str_to_date(SUBSTRING_INDEX(pl.processMonth,'-',1),'%b')) desc  limit  1;
  
select  SUM( IFNULL(p.ESI_Employee, 0)) as emp_esi,sum(IFNULL(p.ESI_Employer,0)) empr_esi  from ReportPayOut p  

inner join Employee e on e.employeeId=p.employeeId and e.activeStatus='AC'and  e.companyId=p_comp_id 

where  p.processMonth in (select month from temp )

 order by month(str_to_date(SUBSTRING_INDEX(p.processMonth,'-',1),'%b'));



END IF;
















END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `Pro_fetch_process_month` (IN `p_comp_id` INT)  NO SQL
BEGIN


    
    DECLARE e_sqlstate 		VARCHAR(255); 
    DECLARE e_error_no 		INT(127);
    DECLARE e_error_message	TEXT;
    DECLARE e_remark		TEXT;
 
    DECLARE EXIT HANDLER FOR SQLEXCEPTION
  	BEGIN
		GET CURRENT DIAGNOSTICS CONDITION 1 e_sqlstate = RETURNED_SQLSTATE, e_error_no = MYSQL_ERRNO, e_error_message = MESSAGE_TEXT;
        
		SET	@p_process_status	=	1000;
		SET	@p_process_message	=	'Error... please Contact Administrator';
		SET	@p_process_remark	=  fun_set_exception_handling ( 2, 'Pro_fetch_process_month', e_sqlstate, e_error_no, e_error_message, e_remark);
			
	     END;
      

      
 SET @@sql_mode = 'NO_ENGINE_SUBSTITUTION';
       
SELECT DISTINCT ph.processMonth, ph.payRegisterHdId FROM  PayRegisterHd
			 ph WHERE ph.companyId=p_comp_id group by ph.processMonth order by month(str_to_date(SUBSTRING_INDEX(ph.processMonth,'-',1),'%b'));

 

  

END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `pro_lastsixmonth_ctc` (IN `p_comp_id` INT(127), IN `p_process_month` INT(127))  NO SQL
BEGIN


    
    DECLARE e_sqlstate 		VARCHAR(255); 
    DECLARE e_error_no 		INT(127);
    DECLARE e_error_message	TEXT;
    DECLARE e_remark		TEXT;
 
    DECLARE EXIT HANDLER FOR SQLEXCEPTION
  	BEGIN
		GET CURRENT DIAGNOSTICS CONDITION 1 e_sqlstate = RETURNED_SQLSTATE, e_error_no = MYSQL_ERRNO, e_error_message = MESSAGE_TEXT;
        
		SET	@p_process_status	=	1000;
		SET	@p_process_message	=	'Error... please Contact Administrator';
		SET	@p_process_remark	=  fun_set_exception_handling ( 2, 'pro_department_wise_ctc', e_sqlstate, e_error_no, e_error_message, e_remark);
			
	     END;
      
      if(p_process_month>0) then
      
 SET @@sql_mode = 'NO_ENGINE_SUBSTITUTION';
        DROP TEMPORARY TABLE IF EXISTS temp;
    CREATE TEMPORARY TABLE IF NOT EXISTS temp ( id int(11) NOT NULL AUTO_INCREMENT,
        month	VARCHAR(127) NOT NULL, PRIMARY KEY (id));
   
    
    INSERT INTO temp ( month )
    
    SELECT DISTINCT pl.processMonth  FROM PayRegisterHd pl 

WHERE pl.dateCreated>DATE_SUB(now(),INTERVAL  6 MONTH ) AND
pl.companyId= p_comp_id 
order by month(str_to_date(SUBSTRING_INDEX(pl.processMonth,'-',1),'%b')) desc  limit  6;

SELECT  rp.processMonth, SUM( IFNULL(rp.BasicEarning, 0)+ IFNULL(rp.dearnessAllowanceEarning, 0)+IFNULL(rp.ConveyanceAllowanceEarning, 0)+IFNULL(rp.HRAEarning, 0)+IFNULL(rp.MedicalAllowanceEarning, 0)+IFNULL(rp.AdvanceBonusEarning, 0)+IFNULL(rp.SpecialAllowanceEarning,0)+IFNULL(rp.leaveTravelAllowanceEarning,0)+IFNULL(rp.performanceLinkedIncomeEarning,0)+IFNULL(rp.uniformAllowanceEarning,0)+IFNULL(rp.CompanyBenefitsEarning,0)+IFNULL(rp.ProvidentFundEmployer,0)+IFNULL(rp.ProvidentFundEmployerPension,0)+IFNULL(rp.ESI_Employer,0)) as CTC FROM ReportPayOut rp  

where  rp.processMonth in (select month from temp ) 
and rp.companyId=1 
GROUP BY rp.processMonth
 order by month(str_to_date(SUBSTRING_INDEX(rp.processMonth,'-',1),'%b')) 

 ;

 

   ELSE
   SET @@sql_mode = 'NO_ENGINE_SUBSTITUTION';
        DROP TEMPORARY TABLE IF EXISTS temp;
    CREATE TEMPORARY TABLE IF NOT EXISTS temp ( id int(11) NOT NULL AUTO_INCREMENT,
        month	VARCHAR(127) NOT NULL, PRIMARY KEY (id));
   
    INSERT INTO temp ( month )
    
    SELECT DISTINCT pl.processMonth  FROM PayRegisterHd pl 

WHERE pl.dateCreated>DATE_SUB(now(),INTERVAL  6 MONTH ) AND
pl.companyId= p_comp_id 
order by month(str_to_date(SUBSTRING_INDEX(pl.processMonth,'-',1),'%b')) desc  limit  6;

SELECT  rp.processMonth, SUM( IFNULL(rp.BasicEarning, 0)+ IFNULL(rp.dearnessAllowanceEarning, 0)+IFNULL(rp.ConveyanceAllowanceEarning, 0)+IFNULL(rp.HRAEarning, 0)+IFNULL(rp.MedicalAllowanceEarning, 0)+IFNULL(rp.AdvanceBonusEarning, 0)+IFNULL(rp.SpecialAllowanceEarning,0)+IFNULL(rp.leaveTravelAllowanceEarning,0)+IFNULL(rp.performanceLinkedIncomeEarning,0)+IFNULL(rp.uniformAllowanceEarning,0)+IFNULL(rp.CompanyBenefitsEarning,0)+IFNULL(rp.ProvidentFundEmployer,0)+IFNULL(rp.ProvidentFundEmployerPension,0)+IFNULL(rp.ESI_Employer,0)) as CTC FROM ReportPayOut rp  

where  rp.processMonth in (select month from temp ) 
and rp.companyId=p_comp_id
GROUP BY rp.processMonth
 order by month(str_to_date(SUBSTRING_INDEX(rp.processMonth,'-',1),'%b')) 

 ;

End IF;

END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `pro_myteam_on_leave` (IN `p_flag` VARCHAR(250), IN `p_comp_id` INT(127) UNSIGNED, IN `p_emp_id` INT(127) UNSIGNED)  NO SQL
BEGIN
    
    DECLARE e_sqlstate 		VARCHAR(255); 
    DECLARE e_error_no 		INT(127);
    DECLARE e_error_message	TEXT;
    DECLARE e_remark		TEXT;
 
    DECLARE EXIT HANDLER FOR SQLEXCEPTION
  	BEGIN
		GET CURRENT DIAGNOSTICS CONDITION 1 e_sqlstate = RETURNED_SQLSTATE, e_error_no = MYSQL_ERRNO, e_error_message = MESSAGE_TEXT;
        
		SET	@p_process_status	=	1000;
		SET	@p_process_message	=	'Error... please Contact Administrator';
		SET	@p_process_remark	=  fun_set_exception_handling ( 1, 'pro_myteam_on_leave', e_sqlstate, e_error_no, e_error_message, e_remark);
			
	     END;
      
      
         CASE  WHEN p_flag IS NULL OR p_flag =' ' THEN
  
	   SELECT fun_count_myteam_on_leave(p_comp_id,p_emp_id) as teamOnLeaveToday,
       fun_myteam_on_leave_monthwise(p_comp_id,p_emp_id) as teamOnLeaveMonth;
		
		 WHEN p_flag='myteam_today' THEN

select  e.employeeCode,e.firstName,e.lastName,degi.designationName,dept.departmentName ,e.employeeLogoPath, tl.fromDate,tl.toDate,tl.days
                                     from TMSLeaveEntries tl INNER join Employee e  JOIN Designation degi JOIN Department dept on tl.employeeId= e.employeeId and degi.designationId=e.designationId and dept.departmentId=e.departmentId  where DAY(tl.fromDate)= DAY(now()) AND tl.status='APR' and e.companyId=p_comp_id and e.ReportingToEmployee=p_emp_id;                       
     		
	
			 WHEN p_flag='myteam_month' THEN
  select  e.employeeCode,e.firstName,e.lastName,degi.designationName,dept.departmentName ,e.employeeLogoPath, tl.fromDate,tl.toDate,tl.days from TMSLeaveEntries tl INNER join Employee e  JOIN Designation degi JOIN Department dept on tl.employeeId= e.employeeId and degi.designationId=e.designationId and dept.departmentId=e.departmentId  where month(tl.fromDate)= month(now()) AND tl.status='APR' and e.companyId=p_comp_id and e.ReportingToEmployee=p_emp_id;
      
                                     END CASE;

END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `pro_new_attendancelog_report` (IN `p_selected_date` VARCHAR(250), IN `p_employee_name` VARCHAR(250), IN `p_sorttype` VARCHAR(250), IN `p_activestatus` VARCHAR(250), IN `p_employee_code` VARCHAR(250), IN `p_offset` VARCHAR(127), IN `p_limit` VARCHAR(127), IN `p_active_value` VARCHAR(250), IN `p_company_id` VARCHAR(127), IN `p_dept_id` VARCHAR(127), IN `p_desg_id` VARCHAR(127), IN `p_title` VARCHAR(127))  NO SQL
BEGIN
    DECLARE v_offset int DEFAULT 0;
    DECLARE v_limit int DEFAULT 0;
    DECLARE v_sort varchar(50);
    DECLARE v_value varchar(50);
    set v_offset = CAST(p_offset AS UNSIGNED);
    set v_limit  = CAST(p_limit AS UNSIGNED);
    set v_value = CONCAT('e.',p_active_value);
    set @v_sort = CONCAT(v_value,' ',p_sorttype);
   
 
    SET @@sql_mode = 'NO_ENGINE_SUBSTITUTION'; 
    
     CASE 
      WHEN(   (p_selected_date!=null or p_selected_date!='') 
          AND (p_employee_name=null or p_employee_name='')  
          AND (p_sorttype!=null or p_sorttype!='')  
          AND (p_employee_code=null or p_employee_code='') 
          AND (p_offset!=null OR p_offset!='')
          AND (p_limit!=null OR p_limit!='') 
          AND  (p_active_value=null or p_active_value='') 
          AND (p_company_id!=null or p_company_id!='')  
          AND  (p_dept_id=null or p_dept_id='')  
          AND  (p_desg_id=null or p_desg_id='')
          AND  (p_title=null or p_title='') 
           
         ) THEN

select  e.firstName,e.lastName, dg.designationName, al.inTime  ,al.outTime , al.location,al.mode,al.delayedTime,e.employeeId,
 al.status, al.attendanceDate,e.employeeCode,dept.departmentName,concat(TIMEDIFF(cast(al.inTime as time) ,sh.graceTime),'') as reportedLateBy ,concat(TIMEDIFF(cast(al.outTime as time) ,sh.toTime),'') as leftEarlyBy,
(CASE
 when((select tl.holidayId FROM TMSHolidays tl  WHERE (p_selected_date >=tl.fromDate) AND (p_selected_date<=tl.toDate)>0)) THEN
      'Holiday'
      WHEN(tle.halfFullDay='H') Then 'Half Day Leave'
      when(tle.halfFullDay='F') THEN 'Full Day Leave'
      when((SELECT fun_check_patternIdDays_bydate(e.patternId,p_selected_date))=1)  then 'Week-Off'
      WHEN( al.status='P') Then 'Present'
      else 
     'Absent'  END )  as 'leave status' ,
       ( (SELECT fun_count_present_leave_absent	(p_company_id,'P',p_selected_date))) as presentCount,
     ( (SELECT fun_count_present_leave_absent	(p_company_id,'L',p_selected_date))) as leaveCount,
      ( (SELECT fun_count_present_leave_absent	(p_company_id,'A',p_selected_date))) as absentCount
     from Employee e
     left OUTER join AttendanceLogs al on al.employeeId=e.employeeId  and al.attendanceDate =p_selected_date and al.companyId=p_company_id
     left OUTER join TMSLeaveEntriesDatewise tle on  tle.employeeId=e.employeeId and tle.leaveStatus='APR' and tle.companyId=p_company_id and tle.leaveDate=p_selected_date
 left OUTER join Department dept  on  dept.departmentId= e.departmentId  and dept.companyId=p_company_id
     left OUTER join Designation dg  on  dg.designationId= e.designationId  and dg.companyId=p_company_id
      left OUTER join TMSShift sh on al.employeeCode = e.employeeCode and sh.shiftId=e.shiftId and sh.companyId=p_company_id
 WHERE e.activeStatus='AC' group by e.employeeId
     limit  v_offset ,v_limit 
  ; 
  
  
    WHEN( (p_selected_date!=null or p_selected_date!='') 
          AND (p_employee_name!=null or p_employee_name!='')  
          AND (p_sorttype!=null or p_sorttype!='')  
          AND (p_employee_code=null or p_employee_code='') 
          AND (p_offset!=null OR p_offset!='')
          AND (p_limit!=null OR p_limit!='') 
          AND (p_active_value=null or p_active_value='')        
          AND (p_company_id!=null or p_company_id!='')  
          AND (p_dept_id=null or p_dept_id='')  
          AND (p_desg_id=null or p_desg_id='')  
          AND  (p_title=null or p_title='') 
         
         ) THEN
  
       
  select  e.firstName,e.lastName, dg.designationName, al.inTime  ,al.outTime , al.location,al.mode,al.delayedTime,e.employeeId,
  al.status, al.attendanceDate,e.employeeCode,dept.departmentName,concat(TIMEDIFF(cast(al.inTime as time) ,sh.graceTime),'') as reportedLateBy,concat(TIMEDIFF(cast(al.outTime as time) ,sh.toTime),'') as leftEarlyBy,
(CASE when((select tl.holidayId FROM TMSHolidays tl  WHERE (p_selected_date >=tl.fromDate) AND (p_selected_date<=tl.toDate)>0)) THEN
      'Holiday'
      WHEN(tle.halfFullDay='H') Then 'Half Day Leave'
      when(tle.halfFullDay='F') THEN 'Full Day Leave'
      when((SELECT fun_check_patternIdDays_bydate(e.patternId,p_selected_date))=1)  then 'Week-Off'
      WHEN( al.status='P') Then 'Present'
      else 
     'Absent'  END )  as 'leave status' ,
    ( (SELECT fun_count_present_leave_absent	(p_company_id,'P',p_selected_date))) as presentCount,
     ( (SELECT fun_count_present_leave_absent	(p_company_id,'L',p_selected_date))) as leaveCount,
      ( (SELECT fun_count_present_leave_absent	(p_company_id,'A',p_selected_date))) as absentCount
     from Employee e
     left OUTER join AttendanceLogs al on al.employeeId=e.employeeId  and al.attendanceDate =p_selected_date and al.companyId=p_company_id
     left OUTER join TMSLeaveEntriesDatewise tle on tle.employeeId=e.employeeId and tle.leaveStatus='APR' and tle.companyId=p_company_id and tle.leaveDate=p_selected_date
      left OUTER join Department dept  on  dept.departmentId= e.departmentId  and dept.companyId=p_company_id
 
     left OUTER join Designation dg  on  dg.designationId= e.designationId  and dg.companyId=p_company_id
       left OUTER join TMSShift sh on al.employeeCode = e.employeeCode and sh.shiftId=e.shiftId and sh.companyId=p_company_id
 where e.firstName LIKE concat(p_employee_name, '%') and e.activeStatus='AC'
  group by e.employeeId
  
     limit  v_offset ,v_limit 
   ; 
   

   
    WHEN  (    (p_selected_date!=null or p_selected_date!='') 
           AND (p_employee_name=null or p_employee_name='')  
           AND (p_sorttype!=null or p_sorttype!='')  
           AND (p_employee_code=null or p_employee_code='') 
           AND (p_offset!=null OR p_offset!='')
           AND (p_limit!=null OR p_limit!='') 
           AND (p_active_value=null or p_active_value='')        
           AND (p_company_id!=null or p_company_id!='')  
           AND (p_dept_id!=null or p_dept_id!='')  
           AND (p_desg_id=null or p_desg_id='')  
           AND (p_title=null or p_title='') 
         ) THEN
  
  
  select  e.firstName,e.lastName, dg.designationName, al.inTime  ,al.outTime , al.location,al.mode,al.delayedTime,e.employeeId, al.status, al.attendanceDate,e.employeeCode,dept.departmentName,concat(TIMEDIFF(cast(al.inTime as time) ,sh.graceTime),'') as reportedLateBy,concat(TIMEDIFF(cast(al.outTime as time) ,sh.toTime),'') as leftEarlyBy,
(CASE when((select tl.holidayId FROM TMSHolidays tl  WHERE (p_selected_date >=tl.fromDate) AND (p_selected_date<=tl.toDate)>0)) THEN
      'Holiday'
      WHEN(tle.halfFullDay='H') Then 'Half Day Leave'
      when(tle.halfFullDay='F') THEN 'Full Day Leave'
      when((SELECT fun_check_patternIdDays_bydate(e.patternId,p_selected_date))=1)  then 'Week-Off'
      WHEN( al.status='P') Then 'Present'
      else 
     'Absent'  END )  as 'leave status' ,
    ( (SELECT fun_count_present_leave_absent	(p_company_id,'P',p_selected_date))) as presentCount,
     ( (SELECT fun_count_present_leave_absent	(p_company_id,'L',p_selected_date))) as leaveCount,
      ( (SELECT fun_count_present_leave_absent	(p_company_id,'A',p_selected_date))) as absentCount
     from Employee e
     left OUTER join AttendanceLogs al on al.employeeId=e.employeeId  and al.attendanceDate =p_selected_date and al.companyId=p_company_id
     left OUTER join TMSLeaveEntriesDatewise tle on tle.employeeId=e.employeeId and tle.leaveStatus='APR' and tle.companyId=p_company_id and tle.leaveDate=p_selected_date
      left OUTER join Department dept  on  dept.departmentId= e.departmentId  and dept.companyId=p_company_id
 
     left OUTER join Designation dg  on  dg.designationId= e.designationId  and dg.companyId=p_company_id
        left OUTER join TMSShift sh on al.employeeCode = e.employeeCode and sh.shiftId=e.shiftId and sh.companyId=p_company_id
 where e.departmentId=p_dept_id AND e.activeStatus='AC'
    group by e.employeeId	
	   limit  v_offset ,v_limit 
   ; 
   
    WHEN( (p_selected_date!=null or p_selected_date!='')  
           AND (p_employee_name=null or p_employee_name='')  
           AND (p_sorttype!=null or p_sorttype!='')  
           AND (p_employee_code!=null or p_employee_code!='') 
           AND (p_offset!=null OR p_offset!='')
           AND (p_limit!=null OR p_limit!='') 
           AND  (p_active_value=null or p_active_value='')        
           AND  (p_company_id!=null or p_company_id!='')  
           AND (p_dept_id=null or p_dept_id='')  
           AND (p_desg_id=null or p_desg_id='')  
           AND  (p_title=null or p_title='') 
         ) THEN
      
	
	select  e.firstName,e.lastName, dg.designationName, al.inTime  ,al.outTime , al.location,al.mode,al.delayedTime,e.employeeId, al.status, al.attendanceDate,e.employeeCode,dept.departmentName,concat(TIMEDIFF(cast(al.inTime as time) ,sh.graceTime),'') as reportedLateBy ,concat(TIMEDIFF(cast(al.outTime as time) ,sh.toTime),'') as leftEarlyBy,
(CASE when((select tl.holidayId FROM TMSHolidays tl  WHERE (p_selected_date >=tl.fromDate) AND (p_selected_date<=tl.toDate)>0)) THEN
      'Holiday'
      WHEN(tle.halfFullDay='H') Then 'Half Day Leave'
      when(tle.halfFullDay='F') THEN 'Full Day Leave'
      when((SELECT fun_check_patternIdDays_bydate(e.patternId,p_selected_date))=1)  then 'Week-Off'
      WHEN( al.status='P') Then 'Present'
      else 
     'Absent'  END )  as 'leave status' ,
    ( (SELECT fun_count_present_leave_absent	(p_company_id,'P',p_selected_date))) as presentCount,
     ( (SELECT fun_count_present_leave_absent	(p_company_id,'L',p_selected_date))) as leaveCount,
      ( (SELECT fun_count_present_leave_absent	(p_company_id,'A',p_selected_date))) as absentCount
     from Employee e
     left OUTER join AttendanceLogs al on al.employeeId=e.employeeId  and al.attendanceDate =p_selected_date and al.companyId=p_company_id
     left OUTER join TMSLeaveEntriesDatewise tle on tle.employeeId=e.employeeId and tle.leaveStatus='APR' and tle.companyId=p_company_id and tle.leaveDate=p_selected_date
      left OUTER join Department dept  on  dept.departmentId= e.departmentId  and dept.companyId=p_company_id
 
     left OUTER join Designation dg  on  dg.designationId= e.designationId  and dg.companyId=p_company_id
         left OUTER join TMSShift sh on al.employeeCode = e.employeeCode and sh.shiftId=e.shiftId and sh.companyId=p_company_id
 where e.employeeCode=p_employee_code AND e.activeStatus='AC'
   group by e.employeeId
   
    limit  v_offset ,v_limit
   
   ; 
  
   
   
   WHEN( (p_selected_date!=null or p_selected_date!='') AND
          (p_employee_name=null or p_employee_name='')  AND
          (p_sorttype!=null or p_sorttype!='') AND 
           (p_employee_code=null or p_employee_code='') 
           and (p_offset!=null OR p_offset!='')
           and (p_limit!=null OR p_limit!='') 
           and  (p_active_value=null or p_active_value='')        
         and  (p_company_id!=null or p_company_id!='')  
           and (p_dept_id=null or p_dept_id='')  
           and (p_desg_id!=null or p_desg_id!='')  
           AND (p_title=null or p_title='') 
        
         ) THEN
         
		 
		 select  e.firstName,e.lastName, dg.designationName, al.inTime  ,al.outTime , al.location,al.mode,al.delayedTime,e.employeeId, al.status, al.attendanceDate,e.employeeCode,dept.departmentName,concat(TIMEDIFF(cast(al.inTime as time) ,sh.graceTime),'') as reportedLateBy ,concat(TIMEDIFF(cast(al.outTime as time) ,sh.toTime),'') as leftEarlyBy,
(CASE when((select tl.holidayId FROM TMSHolidays tl  WHERE (p_selected_date >=tl.fromDate) AND (p_selected_date<=tl.toDate)>0)) THEN
      'Holiday'
      WHEN(tle.halfFullDay='H') Then 'Half Day Leave'
      when(tle.halfFullDay='F') THEN 'Full Day Leave'
      when((SELECT fun_check_patternIdDays_bydate(e.patternId,p_selected_date))=1)  then 'Week-Off'
      WHEN( al.status='P') Then 'Present'
      else 
     'Absent'  END )  as 'leave status' ,
    ( (SELECT fun_count_present_leave_absent	(p_company_id,'P',p_selected_date))) as presentCount,
     ( (SELECT fun_count_present_leave_absent	(p_company_id,'L',p_selected_date))) as leaveCount,
      ( (SELECT fun_count_present_leave_absent	(p_company_id,'A',p_selected_date))) as absentCount
     from Employee e
     left OUTER join AttendanceLogs al on al.employeeId=e.employeeId  and al.attendanceDate =p_selected_date and al.companyId=p_company_id
     left OUTER join TMSLeaveEntriesDatewise tle on tle.employeeId=e.employeeId and tle.leaveStatus='APR' and tle.companyId=p_company_id and tle.leaveDate=p_selected_date
      left OUTER join Department dept  on  dept.departmentId= e.departmentId  and dept.companyId=p_company_id
 
     left OUTER join Designation dg  on  dg.designationId= e.designationId  and dg.companyId=p_company_id
         left OUTER join TMSShift sh on al.employeeCode = e.employeeCode and sh.shiftId=e.shiftId and sh.companyId=p_company_id
 where e.designationId=p_desg_id   AND e.activeStatus='AC'
  group by e.employeeId
  
  
  limit  v_offset ,v_limit
   ; 
  
  
    WHEN( (p_selected_date!=null or p_selected_date!='') AND
          (p_employee_name=null or p_employee_name='')  AND
          (p_sorttype!=null or p_sorttype!='') AND 
           (p_employee_code=null or p_employee_code='') 
           and (p_offset!=null OR p_offset!='')
           and (p_limit!=null OR p_limit!='') 
           and  (p_active_value=null or p_active_value='')        
           and  (p_company_id!=null or p_company_id!='')  
           and (p_dept_id!=null or p_dept_id!='')  
           and (p_desg_id!=null or p_desg_id!='')  
           AND (p_title=null or p_title='') 
        
         ) THEN
         
		 
		 select  e.firstName,e.lastName, dg.designationName, al.inTime  ,al.outTime , al.location,al.mode,al.delayedTime,e.employeeId, al.status, al.attendanceDate,e.employeeCode,dept.departmentName,concat(TIMEDIFF(cast(al.inTime as time) ,sh.graceTime),'') as reportedLateBy ,concat(TIMEDIFF(cast(al.outTime as time) ,sh.toTime),'') as leftEarlyBy,
(CASE when((select tl.holidayId FROM TMSHolidays tl  WHERE (p_selected_date >=tl.fromDate) AND (p_selected_date<=tl.toDate)>0)) THEN
      'Holiday'
      WHEN(tle.halfFullDay='H') Then 'Half Day Leave'
      when(tle.halfFullDay='F') THEN 'Full Day Leave'
      when((SELECT fun_check_patternIdDays_bydate(e.patternId,p_selected_date))=1)  then 'Week-Off'
      WHEN( al.status='P') Then 'Present'
      else 
     'Absent'  END )  as 'leave status' ,
    ( (SELECT fun_count_present_leave_absent	(p_company_id,'P',p_selected_date))) as presentCount,
     ( (SELECT fun_count_present_leave_absent	(p_company_id,'L',p_selected_date))) as leaveCount,
      ( (SELECT fun_count_present_leave_absent	(p_company_id,'A',p_selected_date))) as absentCount
     from Employee e
     left OUTER join AttendanceLogs al on al.employeeId=e.employeeId  and al.attendanceDate =p_selected_date and al.companyId=p_company_id
     left OUTER join TMSLeaveEntriesDatewise tle on tle.employeeId=e.employeeId and tle.leaveStatus='APR' and tle.companyId=p_company_id and tle.leaveDate=p_selected_date
      left OUTER join Department dept  on  dept.departmentId= e.departmentId  and dept.companyId=p_company_id
 
     left OUTER join Designation dg  on  dg.designationId= e.designationId  and dg.companyId=p_company_id
         left OUTER join TMSShift sh on al.employeeCode = e.employeeCode and sh.shiftId=e.shiftId and sh.companyId=p_company_id
 where e.designationId=p_desg_id  and e.departmentId  =p_dept_id AND e.activeStatus='AC'
  group by e.employeeId
  
  limit  v_offset ,v_limit 
  
   ; 
  
  
   WHEN( (p_selected_date!=null or p_selected_date!='') AND
          (p_employee_name=null or p_employee_name='')  AND
          (p_sorttype!=null or p_sorttype!='') AND 
           (p_employee_code!=null or p_employee_code!='') 
           and (p_offset!=null OR p_offset!='')
           and (p_limit!=null OR p_limit!='') 
           and  (p_active_value=null or p_active_value='')        
           and  (p_company_id!=null or p_company_id!='')  
           and (p_dept_id!=null or p_dept_id!='')  
           and (p_desg_id!=null or p_desg_id!='')  
           AND (p_title=null or p_title='') 
        
         ) THEN
         
  
  select  e.firstName,e.lastName, dg.designationName, al.inTime  ,al.outTime , al.location,al.mode,al.delayedTime,e.employeeId, al.status, al.attendanceDate,e.employeeCode,dept.departmentName,concat(TIMEDIFF(cast(al.inTime as time) ,sh.graceTime),'') as reportedLateBy,concat(TIMEDIFF(cast(al.outTime as time) ,sh.toTime),'') as leftEarlyBy,
(CASE when((select tl.holidayId FROM TMSHolidays tl  WHERE (p_selected_date >=tl.fromDate) AND (p_selected_date<=tl.toDate)>0)) THEN
      'Holiday'
      WHEN(tle.halfFullDay='H') Then 'Half Day Leave'
      when(tle.halfFullDay='F') THEN 'Full Day Leave'
      when((SELECT fun_check_patternIdDays_bydate(e.patternId,p_selected_date))=1)  then 'Week-Off'
      WHEN( al.status='P') Then 'Present'
      else 
     'Absent'  END )  as 'leave status' ,
    ( (SELECT fun_count_present_leave_absent	(p_company_id,'P',p_selected_date))) as presentCount,
     ( (SELECT fun_count_present_leave_absent	(p_company_id,'L',p_selected_date))) as leaveCount,
      ( (SELECT fun_count_present_leave_absent	(p_company_id,'A',p_selected_date))) as absentCount
     from Employee e
     left OUTER join AttendanceLogs al on al.employeeId=e.employeeId  and al.attendanceDate =p_selected_date and al.companyId=p_company_id
     left OUTER join TMSLeaveEntriesDatewise tle on tle.employeeId=e.employeeId and tle.leaveStatus='APR' and tle.companyId=p_company_id and tle.leaveDate=p_selected_date
      left OUTER join Department dept  on  dept.departmentId= e.departmentId  and dept.companyId=p_company_id
 
     left OUTER join Designation dg  on  dg.designationId= e.designationId  and dg.companyId=p_company_id
      left OUTER join TMSShift sh on al.employeeCode = e.employeeCode and sh.shiftId=e.shiftId and sh.companyId=p_company_id
 where e.designationId=p_desg_id  and e.departmentId  =p_dept_id and e.employeeCode=p_employee_code AND e.activeStatus='AC'
  group by e.employeeId
  
   limit  v_offset ,v_limit
   ; 
   
   
     WHEN( (p_selected_date!=null or p_selected_date!='') 
           AND (p_employee_name=null or p_employee_name='')  
           AND (p_sorttype!=null or p_sorttype!='')  
           AND (p_employee_code!=null or p_employee_code!='') 
           AND (p_offset!=null OR p_offset!='')
           AND (p_limit!=null OR p_limit!='') 
           AND  (p_active_value=null or p_active_value='')        
           AND  (p_company_id!=null or p_company_id!='')  
           AND (p_dept_id!=null or p_dept_id!='')  
           AND (p_desg_id=null or p_desg_id='')  
           AND (p_title=null or p_title='') 
         ) THEN
         
		 
		 select  e.firstName,e.lastName, dg.designationName, al.inTime  ,al.outTime , al.location,al.mode,al.delayedTime,e.employeeId, al.status, al.attendanceDate,e.employeeCode,dept.departmentName,concat(TIMEDIFF(cast(al.inTime as time) ,sh.graceTime),'') as reportedLateBy,concat(TIMEDIFF(cast(al.outTime as time) ,sh.toTime),'') as leftEarlyBy,
(CASE when((select tl.holidayId FROM TMSHolidays tl  WHERE (p_selected_date >=tl.fromDate) AND (p_selected_date<=tl.toDate)>0)) THEN
      'Holiday'
      WHEN(tle.halfFullDay='H') Then 'Half Day Leave'
      when(tle.halfFullDay='F') THEN 'Full Day Leave'
      when((SELECT fun_check_patternIdDays_bydate(e.patternId,p_selected_date))=1)  then 'Week-Off'
      WHEN( al.status='P') Then 'Present'
      else 
     'Absent'  END )  as 'leave status' ,
    ( (SELECT fun_count_present_leave_absent	(p_company_id,'P',p_selected_date))) as presentCount,
     ( (SELECT fun_count_present_leave_absent	(p_company_id,'L',p_selected_date))) as leaveCount,
      ( (SELECT fun_count_present_leave_absent	(p_company_id,'A',p_selected_date))) as absentCount
     from Employee e
     left OUTER join AttendanceLogs al on al.employeeId=e.employeeId  and al.attendanceDate =p_selected_date and al.companyId=p_company_id
     left OUTER join TMSLeaveEntriesDatewise tle on tle.employeeId=e.employeeId and tle.leaveStatus='APR' and tle.companyId=p_company_id and tle.leaveDate=p_selected_date
      left OUTER join Department dept  on  dept.departmentId= e.departmentId  and dept.companyId=p_company_id
     left OUTER join Designation dg  on  dg.designationId= e.designationId  and dg.companyId=p_company_id
      left OUTER join TMSShift sh on al.employeeCode = e.employeeCode and sh.shiftId=e.shiftId and sh.companyId=p_company_id
 where  e.departmentId  =p_dept_id and e.employeeCode=p_employee_code AND e.activeStatus='AC'
 group by e.employeeId
 
  limit  v_offset ,v_limit ; 
   
   
     WHEN( (p_selected_date!=null or p_selected_date!='') 
           AND (p_employee_name=null or p_employee_name='')  
           AND (p_sorttype!=null or p_sorttype!='')  
           AND (p_employee_code!=null or p_employee_code!='') 
           AND (p_offset!=null OR p_offset!='')
           AND (p_limit!=null OR p_limit!='') 
           AND  (p_active_value=null or p_active_value='')        
           AND  (p_company_id!=null or p_company_id!='')  
           AND (p_dept_id=null or p_dept_id='')  
           AND (p_desg_id!=null or p_desg_id!='') 
           AND (p_title=null or p_title='') 
        
         ) THEN
         
		 
		 select  e.firstName,e.lastName, dg.designationName, al.inTime  ,al.outTime , al.location,al.mode,al.delayedTime,e.employeeId, al.status, al.attendanceDate,e.employeeCode,dept.departmentName,concat(TIMEDIFF(cast(al.inTime as time) ,sh.graceTime),'') as reportedLateBy,concat(TIMEDIFF(cast(al.outTime as time) ,sh.toTime),'') as leftEarlyBy,
(CASE when((select tl.holidayId FROM TMSHolidays tl  WHERE (p_selected_date >=tl.fromDate) AND (p_selected_date<=tl.toDate)>0)) THEN
      'Holiday'
      WHEN(tle.halfFullDay='H') Then 'Half Day Leave'
      when(tle.halfFullDay='F') THEN 'Full Day Leave'
      when((SELECT fun_check_patternIdDays_bydate(e.patternId,p_selected_date))=1)  then 'Week-Off'
      WHEN( al.status='P') Then 'Present'
      else 
     'Absent'  END )  as 'leave status' ,
    ( (SELECT fun_count_present_leave_absent	(p_company_id,'P',p_selected_date))) as presentCount,
     ( (SELECT fun_count_present_leave_absent	(p_company_id,'L',p_selected_date))) as leaveCount,
      ( (SELECT fun_count_present_leave_absent	(p_company_id,'A',p_selected_date))) as absentCount
     from Employee e
     left OUTER join AttendanceLogs al on al.employeeId=e.employeeId  and al.attendanceDate =p_selected_date and al.companyId=p_company_id
     left OUTER join TMSLeaveEntriesDatewise tle on tle.employeeId=e.employeeId and tle.leaveStatus='APR' and tle.companyId=p_company_id and tle.leaveDate=p_selected_date
      left OUTER join Department dept  on  dept.departmentId= e.departmentId  and dept.companyId=p_company_id
     left OUTER join Designation dg  on  dg.designationId= e.designationId  and dg.companyId=p_company_id
      left OUTER join TMSShift sh on al.employeeCode = e.employeeCode and sh.shiftId=e.shiftId and sh.companyId=p_company_id
 where  e.designationId  =p_desg_id and e.employeeCode=p_employee_code AND e.activeStatus='AC'
  group by e.employeeId
  
   limit  v_offset ,v_limit ; 
 
    WHEN( (p_selected_date!=null or p_selected_date!='') 
           AND (p_employee_name=null or p_employee_name='')  
           AND (p_sorttype!=null or p_sorttype!='')  
           AND  (p_employee_code=null or p_employee_code='') 
           AND (p_offset!=null OR p_offset!='')
           AND (p_limit!=null OR p_limit!='') 
           AND  (p_active_value!=null or p_active_value!='')        
           AND  (p_company_id!=null or p_company_id!='')  
           AND (p_dept_id=null or p_dept_id='')  
           AND (p_desg_id=null or p_desg_id='')
           AND (p_title=null or p_title='')
        
         ) THEN
        
		
		 SET @Query1 =CONCAT(" select  e.firstName,e.lastName, dg.designationName, al.inTime  ,al.outTime , al.location,al.mode,al.delayedTime,e.employeeId, al.status, al.attendanceDate,e.employeeCode,dept.departmentName,concat(TIMEDIFF(cast(al.inTime as time) ,sh.graceTime),'') as reportedLateBy,concat(TIMEDIFF(cast(al.outTime as time) ,sh.toTime),'') as leftEarlyBy,
(CASE when((select tl.holidayId FROM TMSHolidays tl  WHERE (p_selected_date >=tl.fromDate) AND (p_selected_date<=tl.toDate)>0)) THEN
      'Holiday'
      WHEN(tle.halfFullDay='H') Then 'Half Day Leave'
      when(tle.halfFullDay='F') THEN 'Full Day Leave'
      when((SELECT fun_check_patternIdDays_bydate(e.patternId  ",p_selected_date," ))=1)  then 'Week-Off'
      WHEN( al.status='P') Then 'Present'
      else 
     'Absent'  END )  as 'leave status' ,
    ( (SELECT fun_count_present_leave_absent	(p_company_id,'P',p_selected_date))) as presentCount,
     ( (SELECT fun_count_present_leave_absent	(p_company_id,'L',p_selected_date))) as leaveCount,
      ( (SELECT fun_count_present_leave_absent	(p_company_id,'A',p_selected_date))) as absentCount
     from Employee e
     left OUTER join AttendanceLogs al on al.employeeId=e.employeeId  and al.attendanceDate =",p_selected_date, " and al.companyId= " ,p_company_id,
               "  left OUTER join TMSLeaveEntriesDatewise tle on tle.employeeId=e.employeeId and tle.leaveStatus='APR' and tle.companyId=" ,p_company_id, " and tle.leaveDate=" ,p_selected_date,
     "  left OUTER join Department dept  on  dept.departmentId= e.departmentId  and dept.companyId= ",p_company_id,
    "  left OUTER join TMSShift sh on al.employeeCode = e.employeeCode and sh.shiftId=e.shiftId and sh.companyId= ",p_company_id,
     " left OUTER join Designation dg  on  dg.designationId= e.designationId  and dg.companyId= ",p_company_id, " WHERE e.activeStatus='AC'  order by ",@v_sort," limit ",v_offset,',',v_limit);

 PREPARE stmt3 FROM @Query1;
 EXECUTE stmt3;
 DEALLOCATE PREPARE stmt3
 ; 
  WHEN( (p_selected_date!=null or p_selected_date!='') 
           AND (p_employee_name=null or p_employee_name='')  
           AND (p_sorttype!=null or p_sorttype!='')  
           AND (p_employee_code=null or p_employee_code='') 
           AND (p_offset!=null OR p_offset!='')
           AND (p_limit!=null OR p_limit!='') 
           AND (p_active_value=null or p_active_value='')        
           AND (p_company_id!=null or p_company_id!='')  
           AND (p_dept_id=null or p_dept_id='')  
           AND (p_desg_id=null or p_desg_id='') 
           AND (p_title!=null or p_title!='') 
        
         ) THEN
		 
		  if(p_title='P') then
		 select e.firstName,e.lastName, dg.designationName, al.inTime ,al.outTime, al.location,al.mode,al.delayedTime,e.employeeId, al.status
,(case when al.status='p' THEN
  'Present'
  when al.status='HD' THEN
  'Half Day'
  end) as 'leave status' , al.attendanceDate,e.employeeCode,dept.departmentName,concat(TIMEDIFF(cast(al.inTime as time) ,sh.graceTime),'') as reportedLateBy,concat(TIMEDIFF(cast(al.outTime as time) ,sh.toTime),'') as leftEarlyBy from AttendanceLogs al 
join Employee e on e.employeeId=al.employeeId 
join Designation dg on dg.designationId=e.designationId
join Department dept  on  dept.departmentId= e.departmentId  
join TMSShift sh on al.employeeCode = e.employeeCode and sh.shiftId=e.shiftId and sh.companyId=p_company_id
WHERE al.attendanceDate =p_selected_date and al.companyId=p_company_id and  al.status='HD' or al.status='P'
 limit  v_offset ,v_limit;
  ELSEIF(p_title='A') THEN
  
  SELECT e.firstName,e.lastName, dg.designationName ,"NA" as "inTime" ,"NA" as "outTime", "NA" as "location","NA" as "mode","NA" as "delayedTime" ,e.employeeId, "NA" as "status"
,'Absent' as 'leave status',"NA" as attendanceDate,e.employeeCode,dept.departmentName,"NA" as reportedLateBy from  Employee e 
join Designation dg on dg.designationId=e.designationId
join Department dept  on  dept.departmentId= e.departmentId  
WHERE  e.employeeId and e.companyId=p_company_id NOT IN (SELECT al.employeeId FROM AttendanceLogs al where al.attendanceDate=p_selected_date and al.companyId=p_company_id)

 limit  v_offset ,v_limit;
  end if;
   
     WHEN( (p_selected_date!=null or p_selected_date!='') 
           AND (p_employee_name=null or p_employee_name='')  
           AND (p_sorttype!=null or p_sorttype!='')  
           AND (p_employee_code=null or p_employee_code='') 
           AND (p_offset!=null OR p_offset!='')
           AND (p_limit!=null OR p_limit!='') 
           AND (p_active_value=null or p_active_value='')        
           AND (p_company_id!=null or p_company_id!='')  
           AND (p_dept_id!=null or p_dept_id!='')  
           AND (p_desg_id=null or p_desg_id='') 
           AND (p_title!=null or p_title!='') 
        
         ) THEN
         if(p_title='P') then
		 
		 select e.firstName,e.lastName, dg.designationName, al.inTime ,al.outTime, al.location,al.mode,al.delayedTime,e.employeeId, al.status
,(case when al.status='p' THEN
  'Present'
  when al.status='HD' THEN
  'Half Day'
   end  ) as 'leave status' , al.attendanceDate,e.employeeCode,dept.departmentName,concat(TIMEDIFF(cast(al.inTime as time) ,sh.graceTime),'') as reportedLateBy ,concat(TIMEDIFF(cast(al.outTime as time) ,sh.toTime),'') as leftEarlyBy from AttendanceLogs al 
join Employee e on e.employeeId=al.employeeId 
join Designation dg on dg.designationId=e.designationId
join Department dept  on  dept.departmentId= e.departmentId  
join TMSShift sh on al.employeeCode = e.employeeCode and sh.shiftId=e.shiftId and sh.companyId=p_company_id
WHERE al.attendanceDate =p_selected_date and al.companyId=p_company_id and  al.status='HD' or al.status='P' and e.departmentId=p_dept_id
 limit  v_offset ,v_limit;
  ELSEIF(p_title='A') THEN
  
    SELECT e.firstName,e.lastName, dg.designationName ,"NA" as "inTime" ,"NA" as "outTime", "NA" as "location","NA" as "mode","NA" as "delayedTime" ,e.employeeId, "NA" as "status"
,'Absent' as 'leave status' ,"NA" as attendanceDate,e.employeeCode,dept.departmentName,"NA" as reportedLateBy ,"NA" as leftEarlyBy FROM Employee e 
join Designation dg on dg.designationId=e.designationId
join Department dept  on  dept.departmentId= e.departmentId  
WHERE  e.employeeId  NOT IN (SELECT al.employeeId FROM AttendanceLogs al where al.attendanceDate=p_selected_date and al.companyId=p_company_id) and e.companyId=p_company_id and e.departmentId=p_dept_id AND e.activeStatus='AC'
  
   limit  v_offset ,v_limit;
  
  end if;

   
     WHEN( (p_selected_date!=null or p_selected_date!='') 
           AND (p_employee_name=null or p_employee_name='')  
           AND (p_sorttype!=null or p_sorttype!='')  
           AND (p_employee_code=null or p_employee_code='') 
           AND (p_offset!=null OR p_offset!='')
           AND (p_limit!=null OR p_limit!='') 
           AND (p_active_value=null or p_active_value='')        
           AND (p_company_id!=null or p_company_id!='')  
           AND (p_dept_id!=null or p_dept_id!='')  
           AND (p_desg_id!=null or p_desg_id!='') 
           AND (p_title!=null or p_title!='') 
        
         ) THEN
         if(p_title='P') then
		 
		 
		 select e.firstName,e.lastName, dg.designationName, al.inTime    ,al.outTime, al.location,al.mode,al.delayedTime,e.employeeId, al.status
,(case when al.status='p' THEN
  'Present'
  when al.status='HD' THEN
  'Half Day'
   end  ) as 'leave status' , al.attendanceDate,e.employeeCode,dept.departmentName,concat(TIMEDIFF(cast(al.inTime as time) ,sh.graceTime),'') as reportedLateBy,concat(TIMEDIFF(cast(al.outTime as time) ,sh.toTime),'') as leftEarlyBy from AttendanceLogs al 
join Employee e on e.employeeId=al.employeeId 
join Designation dg on dg.designationId=e.designationId
join Department dept  on  dept.departmentId= e.departmentId  
join TMSShift sh on al.employeeCode = e.employeeCode and sh.shiftId=e.shiftId and sh.companyId=p_company_id
WHERE al.attendanceDate =p_selected_date and al.companyId=p_company_id and  al.status='HD' or al.status='P' and e.departmentId=p_dept_id and e.designationId=p_desg_id

limit  v_offset ,v_limit;
  ELSEIF(p_title='A') THEN
  
  SELECT e.firstName,e.lastName, dg.designationName ,"NA" as "inTime" ,"NA" as "outTime", "NA" as "location","NA" as "mode","NA" as "delayedTime" ,e.employeeId, "NA" as "status"
,'Absent' as 'leave status' ,"NA" as attendanceDate,e.employeeCode,dept.departmentName,"NA" as reportedLateBy ,"NA" as leftEarlyBy FROM Employee e 
join Designation dg on dg.designationId=e.designationId
join Department dept  on  dept.departmentId= e.departmentId  
WHERE  e.employeeId  NOT IN (SELECT al.employeeId FROM AttendanceLogs al where al.attendanceDate=p_selected_date and al.companyId=p_company_id) and e.companyId=p_company_id and e.departmentId=p_dept_id and e.designationId=p_desg_id AND e.activeStatus='AC'

 limit  v_offset ,v_limit;
  
  end if;

     
   
   
   else 
   
     SELECT 1;
    
END CASE;

end$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `pro_new_payroll_leave_ar` (IN `p_company_id` INT(127), IN `p_employeeId` INT(127))  NO SQL
BEGIN

    DECLARE e_sqlstate 		VARCHAR(255); 
    DECLARE e_error_no 		INT(127);
    DECLARE e_error_message	TEXT;
    DECLARE e_remark		TEXT;
 
    DECLARE EXIT HANDLER FOR SQLEXCEPTION
  	BEGIN
		GET CURRENT DIAGNOSTICS CONDITION 1 e_sqlstate = RETURNED_SQLSTATE, e_error_no = MYSQL_ERRNO, e_error_message = MESSAGE_TEXT;
        
		SET	@p_process_status	=	1000;
		SET	@p_process_message	=	'Error... please Contact Administrator';
		SET	@p_process_remark	=  fun_set_exception_handling ( 1, 'pro_new_payroll_leave_ar', e_sqlstate, e_error_no, e_error_message, e_remark);
			
	     END;


 SET @@sql_mode = 'NO_ENGINE_SUBSTITUTION';

SELECT em.processMonth ,em.dateCreated FROM ReportPayOut em WHERE em.dateCreated > DATE_SUB(now(), INTERVAL 6 MONTH) AND em.companyId=p_company_id AND em.employeeId=p_employeeId order by month(str_to_date(SUBSTRING_INDEX(em.processMonth,'-',1),'%b')) DESC LIMIT 6
 ;






END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `pro_payrollreport_byempid` (IN `p_comp_id` INT(127), IN `p_emp_id` INT(127), IN `p_from_process_month` VARCHAR(250), IN `p_to_process_month` VARCHAR(250))  NO SQL
BEGIN

  
    DECLARE e_sqlstate 		VARCHAR(255); 
    DECLARE e_error_no 		INT(127);
    DECLARE e_error_message	TEXT;
    DECLARE e_remark		TEXT;
 
    DECLARE EXIT HANDLER FOR SQLEXCEPTION
  	BEGIN
		GET CURRENT DIAGNOSTICS CONDITION 1 e_sqlstate = RETURNED_SQLSTATE, e_error_no = MYSQL_ERRNO, e_error_message = MESSAGE_TEXT;
        
		SET	@p_process_status	=	1000;
		SET	@p_process_message	=	'Error... please Contact Administrator';
		SET	@p_process_remark	=  fun_set_exception_handling ( 2, 'pro_payrollreport_byempid', e_sqlstate, e_error_no, e_error_message, e_remark);
			
	     END;

 SET @@sql_mode = 'NO_ENGINE_SUBSTITUTION';
SELECT  rp.processMonth , rp .Name ,rp.employeeCode,rp.bankName,rp.accountNumber,rp.dateOfJoining, 
									rp.Basic ,rp.dearnessAllowance,rp.ConveyanceAllowance,rp.HRA,rp.MedicalAllowance, 
						rp.AdvanceBonus,rp.SpecialAllowance ,rp.CompanyBenefits ,rp.otherAllowance 
								,rp.GrossSalary ,rp.absense,rp.casualleave,rp.seekleave,rp.paidleave
						,rp.presense ,rp.publicholidays ,rp.weekoff,rp.overtime ,rp.payDays ,rp.BasicEarning 
							,rp.dearnessAllowanceEarning,rp.ConveyanceAllowanceEarning ,rp.HRAEarning,rp.MedicalAllowanceEarning
							,rp.AdvanceBonusEarning, rp.SpecialAllowanceEarning,rp.CompanyBenefitsEarning,rp.otherAllowanceEarning
							,rp.TotalEarning,rp.EmployeeLoansAdvnaceEarning,rp.ProvidentFundEmployee,rp.ESI_Employee, 
					rp.PT,rp.TDS,rp.TotalDeduction,rp.NetPayableAmount
							 FROM ReportPayOut  rp WHERE
                             
                              ( month(str_to_date(SUBSTRING_INDEX(rp.processMonth,'-',1),'%b')) BETWEEN month(str_to_date(SUBSTRING_INDEX(p_from_process_month,'-',1),'%b'))  and month(str_to_date(SUBSTRING_INDEX(p_to_process_month,'-',1),'%b')))
                  and           
                             rp.employeeId=p_emp_id 
						 AND rp.companyId=p_comp_id
							ORDER BY  rp.dateOfJoining  ASC;




END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `pro_payroll_input_leave_ar` (IN `p_comp_id` INT(127) UNSIGNED, IN `p_process_month` VARCHAR(250), IN `p_process_year` VARCHAR(250))  NO SQL
BEGIN

   
    DECLARE e_sqlstate 		VARCHAR(255); 
    DECLARE e_error_no 		INT(127);
    DECLARE e_error_message	TEXT;
    DECLARE e_remark		TEXT;
 
    DECLARE EXIT HANDLER FOR SQLEXCEPTION
  	BEGIN
		GET CURRENT DIAGNOSTICS CONDITION 1 e_sqlstate = RETURNED_SQLSTATE, e_error_no = MYSQL_ERRNO, e_error_message = MESSAGE_TEXT;
        
		SET	@p_process_status	=	1000;
		SET	@p_process_message	=	'Error... please Contact Administrator';
        SET	@p_process_remark=p_process_month;
 	 SET	@p_process_remark	=  fun_set_exception_handling ( 2, 'pro_payroll_input_leave_ar', e_sqlstate, e_error_no, e_error_message, e_remark);
			
	     END;
         
         
           SET @@sql_mode = 'NO_ENGINE_SUBSTITUTION';
      
      DROP TEMPORARY TABLE IF EXISTS temp;
    CREATE TEMPORARY TABLE IF NOT EXISTS temp ( firstName varchar(50) NOT NULL,
	 lastName varchar(50) DEFAULT NULL, employeeId int(11) NOT NULL,fromDate date NOT NULL,
  toDate date NOT NULL, Id int(11),employeeCode varchar(10) DEFAULT NULL,status varchar(5) DEFAULT NULL,
  remark  varchar(50) DEFAULT NULL,departmentName  varchar(50) DEFAULT NULL
        );
   
    
   INSERT INTO temp ( firstName,lastName ,employeeId,fromDate,toDate,Id,employeeCode,status,remark,departmentName)
    
		select e.firstName,e.lastName,  e.employeeId,al.fromDate,al.toDate ,al.leaveId,e.employeeCode ,al.status ,'Leave'as 
		remark,dept.departmentName from TMSLeaveEntries al join  Employee e  on al.employeeId=e.employeeId JOIN Department dept on e.departmentId=dept.departmentId  and Month(al.fromDate) =p_process_month 
		and year(al.fromDate) =p_process_year and al.companyId=p_comp_id AND al.status='PEN' AND e.activeStatus='AC' ORDER BY al.leaveId DESC;

 INSERT INTO temp ( firstName,lastName ,employeeId,fromDate,toDate,Id,employeeCode,status,remark,departmentName)
 
	select e.firstName,e.lastName,  e.employeeId,al.fromDate,al.toDate ,al.arID,e.employeeCode, al.status, 'Attendance Regularization'as remark,dept.departmentName from TMSARRequest al 
 	  join  Employee e  on al.employeeId=e.employeeId JOIN Department dept on e.departmentId=dept.departmentId  and Month(al.fromDate) 
 		=p_process_month and year(al.fromDate) =p_process_year and al.companyId=p_comp_id AND al.status='PEN' AND e.activeStatus='AC' ORDER BY al.arID DESC;
		
		
		select * from temp ;
		END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `pro_pf_contribution` (IN `p_comp_id` INT(127), IN `p_process_month` INT(127))  NO SQL
BEGIN

DECLARE e_sqlstate 		VARCHAR(255); 
    DECLARE e_error_no 		INT(127);
    DECLARE e_error_message	TEXT;
    DECLARE e_remark		TEXT;
 
    DECLARE EXIT HANDLER FOR SQLEXCEPTION
  	BEGIN
		GET CURRENT DIAGNOSTICS CONDITION 1 e_sqlstate = RETURNED_SQLSTATE, e_error_no = MYSQL_ERRNO, e_error_message = MESSAGE_TEXT;
        
		SET	@p_process_status	=	1000;
		SET	@p_process_message	=	'Error... please Contact Administrator';
		SET	@p_process_remark	=  fun_set_exception_handling ( 1, 'pro_emp_count_todaydate', e_sqlstate, e_error_no, e_error_message, e_remark);
			
	     END;

  SET @@sql_mode = 'NO_ENGINE_SUBSTITUTION';

  
   if(p_process_month>0)then 
   
     DROP TEMPORARY TABLE IF EXISTS temp;
    CREATE TEMPORARY TABLE IF NOT EXISTS temp ( id int(11) NOT NULL AUTO_INCREMENT,
        month	VARCHAR(127) NOT NULL, PRIMARY KEY (id));
   
    
   INSERT INTO temp ( month )
    
    SELECT DISTINCT pl.processMonth  FROM PayRegisterHd pl 

WHERE pl.dateCreated>DATE_SUB(now(),INTERVAL  p_process_month MONTH ) AND
pl.companyId= p_comp_id 
order by month(str_to_date(SUBSTRING_INDEX(pl.processMonth,'-',1),'%b')) desc  limit  p_process_month;
    
select  SUM( IFNULL(p.ProvidentFundEmployee, 0)) as emp_pf,sum(IFNULL(p.ProvidentFundEmployer,0)) empr_pf,sum(IFNULL(p.ProvidentFundEmployerPension,0)) empr_pf_pen  from ReportPayOut p  

inner join Employee e on e.employeeId=p.employeeId and e.activeStatus='AC'and  e.companyId=1

where  p.processMonth in (select month from temp )

 order by month(str_to_date(SUBSTRING_INDEX(p.processMonth,'-',1),'%b'));

    
   
   ELSE
   
      
    SET @@sql_mode = 'NO_ENGINE_SUBSTITUTION';
    
  DROP TEMPORARY TABLE IF EXISTS temp;
    CREATE TEMPORARY TABLE IF NOT EXISTS temp ( id int(11) NOT NULL AUTO_INCREMENT,
        month	VARCHAR(127) NOT NULL, PRIMARY KEY (id));
   
    
    INSERT INTO temp ( month )
    
    SELECT DISTINCT pl.processMonth  FROM PayRegisterHd pl 

WHERE pl.dateCreated>DATE_SUB(now(),INTERVAL  p_process_month MONTH ) AND
pl.companyId= p_comp_id 
order by month(str_to_date(SUBSTRING_INDEX(pl.processMonth,'-',1),'%b')) desc  limit  1;
    
select  SUM( IFNULL(p.ProvidentFundEmployee, 0)) as emp_pf,sum(IFNULL(p.ProvidentFundEmployer,0)) empr_pf,sum(IFNULL(p.ProvidentFundEmployerPension,0)) empr_pf_pen  from ReportPayOut p  

inner join Employee e on e.employeeId=p.employeeId and e.activeStatus='AC'and  e.companyId=1

where  p.processMonth in (select month from temp )

 order by month(str_to_date(SUBSTRING_INDEX(p.processMonth,'-',1),'%b'));


End IF;



END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `Pro_processMonth_by_reportPayout` (IN `p_comp_id` INT)  NO SQL
BEGIN


    
    DECLARE e_sqlstate 		VARCHAR(255); 
    DECLARE e_error_no 		INT(127);
    DECLARE e_error_message	TEXT;
    DECLARE e_remark		TEXT;
 
    DECLARE EXIT HANDLER FOR SQLEXCEPTION
  	BEGIN
		GET CURRENT DIAGNOSTICS CONDITION 1 e_sqlstate = RETURNED_SQLSTATE, e_error_no = MYSQL_ERRNO, e_error_message = MESSAGE_TEXT;
        
		SET	@p_process_status	=	1000;
		SET	@p_process_message	=	'Error... please Contact Administrator';
		SET	@p_process_remark	=  fun_set_exception_handling ( 2, 'Pro_fetch_process_month', e_sqlstate, e_error_no, e_error_message, e_remark);
			
	     END;
      

      
 SET @@sql_mode = 'NO_ENGINE_SUBSTITUTION';
       
select DISTINCT processMonth FROM ReportPayOut WHERE 

companyId=p_comp_id group by processMonth order by month(str_to_date(SUBSTRING_INDEX(processMonth,'-',1),'%b'));


  

END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `pro_str_processmonth_byLastmonth` (IN `p_comp_id` INT(127), IN `p_last_month` INT(127))  NO SQL
BEGIN

    DECLARE e_sqlstate 		VARCHAR(255); 
    DECLARE e_error_no 		INT(127);
    DECLARE e_error_message	TEXT;
    DECLARE e_remark		TEXT;
 
    DECLARE EXIT HANDLER FOR SQLEXCEPTION
  	BEGIN
		GET CURRENT DIAGNOSTICS CONDITION 1 e_sqlstate = RETURNED_SQLSTATE, e_error_no = MYSQL_ERRNO, e_error_message = MESSAGE_TEXT;
        
		SET	@p_process_status	=	1000;
		SET	@p_process_message	=	'Error... please Contact Administrator';
		SET	@p_process_remark	=  fun_set_exception_handling ( 1, 'pro_str_processmonth_byLastmonth', e_sqlstate, e_error_no, e_error_message, e_remark);
			
	     END;


 SET @@sql_mode = 'NO_ENGINE_SUBSTITUTION';

SELECT GROUP_CONCAT(DISTINCT CONCAT('"',pl.processMonth,'"'))  FROM PayRollLock pl WHERE pl.dateCreated>DATE_SUB(now(),INTERVAL  p_last_month MONTH ) AND
pl.companyId= p_comp_id 
 order by month(str_to_date(SUBSTRING_INDEX(pl.processMonth,'-',1),'%b'))  limit  p_last_month
 ;






END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `pro_work_anniversary` (IN `p_comp_id` INT(127))  NO SQL
BEGIN

   
    DECLARE e_sqlstate 		VARCHAR(255); 
    DECLARE e_error_no 		INT(127);
    DECLARE e_error_message	TEXT;
    DECLARE e_remark		TEXT;
 
    DECLARE EXIT HANDLER FOR SQLEXCEPTION
  	BEGIN
		GET CURRENT DIAGNOSTICS CONDITION 1 e_sqlstate = RETURNED_SQLSTATE, e_error_no = MYSQL_ERRNO, e_error_message = MESSAGE_TEXT;
        
		SET	@p_process_status	=	1000;
		SET	@p_process_message	=	'Error... please Contact Administrator';
       
 	 SET	@p_process_remark	=  fun_set_exception_handling ( 2, 'pro_work_anniversary', e_sqlstate, e_error_no, e_error_message, e_remark);
			
	     END;
         
         
           SET @@sql_mode = 'NO_ENGINE_SUBSTITUTION';
         
                 DROP TEMPORARY TABLE IF EXISTS temp;
    CREATE TEMPORARY TABLE IF NOT EXISTS temp (
        empName varchar(50) NOT NULL,
         employeeCode varchar(10) DEFAULT NULL,departmentName  varchar(10) DEFAULT NULL,
     designationName   varchar(10) DEFAULT NULL, anniversaryDate  datetime  , empLogoPath varchar(200) NOT NULL, firstName varchar(50) NOT NULL,
	 lastName varchar(50) DEFAULT NULL);
   
      INSERT INTO temp ( empName, employeeCode,departmentName,designationName,anniversaryDate,empLogoPath,firstName,lastName)
  
  select concat(em.firstName,' '
                 ,em.lastName),em.employeeCode ,dp.departmentName,dg.designationName,em.dateOfJoining,em.employeeLogoPath,em.firstName,em.lastName
           from 
      Employee em 
      left join PayStructureHd ph on ph.employeeId=em.employeeId and (ph.dateEnd is null or ph.dateEnd>CURRENT_DATE) and (ph.effectiveDate is NOT null and ph.effectiveDate<=CURRENT_DATE ) and       ph.activeStatus='AC'
          left join Department dp on dp.departmentId=em.departmentId
      left join Designation dg on dg.designationId=em.designationId
      where 
      MONTH(em.dateOfJoining) = MONTH(NOW())  AND em.companyId=p_comp_id AND   em.activeStatus ='AC'
     AND Day(em.dateOfJoining)= day(now());   
     
     INSERT INTO temp ( empName, employeeCode,departmentName,designationName,anniversaryDate,empLogoPath,firstName,lastName)
    
     select concat(em.firstName,' '
                 ,em.lastName),em.employeeCode ,dp.departmentName,dg.designationName,em.dateOfJoining,em.employeeLogoPath,em.firstName,em.lastName
           from 
      Employee em 
      left join PayStructureHd ph on ph.employeeId=em.employeeId and (ph.dateEnd is null or ph.dateEnd>CURRENT_DATE) and (ph.effectiveDate is NOT null and ph.effectiveDate<=CURRENT_DATE ) and       ph.activeStatus='AC'
          left join Department dp on dp.departmentId=em.departmentId
      left join Designation dg on dg.designationId=em.designationId
      where 
      MONTH(em.dateOfJoining) = MONTH(NOW())   AND  day(em.dateOfJoining)> day(now()) AND em.companyId=p_comp_id AND   em.activeStatus ='AC'
    ORDER BY day(em.dateOfJoining) ASC, MONTH(em.dateOfJoining) DESC;

     INSERT INTO temp ( empName, employeeCode,departmentName,designationName,anniversaryDate,empLogoPath,firstName,lastName)
    
 
  select concat(em.firstName,' '
                 ,em.lastName),em.employeeCode ,dp.departmentName,dg.designationName,em.dateOfJoining,em.employeeLogoPath,em.firstName,em.lastName
           from 
      Employee em 
      left join PayStructureHd ph on ph.employeeId=em.employeeId and (ph.dateEnd is null or ph.dateEnd>CURRENT_DATE) and (ph.effectiveDate is NOT null and ph.effectiveDate<=CURRENT_DATE ) and       ph.activeStatus='AC'
          left join Department dp on dp.departmentId=em.departmentId
      left join Designation dg on dg.designationId=em.designationId
      where 
      MONTH(em.dateOfJoining) = MONTH(NOW())   AND  day(em.dateOfJoining)< day(now()) AND em.companyId=p_comp_id AND   em.activeStatus ='AC'
   ORDER BY day(em.dateOfJoining) DESC;
		select * from temp ;
		END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `test` (IN `p_month` INT(127))  NO SQL
BEGIN


SET @a =(select  Month (now()-interval p_month month));
SET @b=( Select  Month(NOW()));
SET @c=0;
  IF(@b>@a) THEN
        
     SET @c=YEAR(NOW());
      
        
    else
     SET @c=YEAR(NOW())-1;  
        
   END IF;     
        
    select @c;



END$$

--
-- Functions
--
CREATE DEFINER=`root`@`localhost` FUNCTION `fun_calculate_days_fromdate_todate` (`p_from_date` VARCHAR(127), `p_to_date` VARCHAR(127)) RETURNS INT(100) NO SQL
BEGIN


  IF(Month(p_from_date)=Month(p_to_date)) THEN
  
   return  DAY(p_to_date) -DAY(p_from_date)+1;
  
  ELSEIF (Month(p_from_date)<Month(p_to_date)) THEN
  
   return  DAY(LAST_DAY(p_from_date))-DAY(p_from_date)+1;
  
  ELSE
  
   return 3;
 
  END IF;

  return 1;

END$$

CREATE DEFINER=`root`@`localhost` FUNCTION `fun_candidate_initiate_count` (`p_comp_id` INT(127)) RETURNS INT(11) NO SQL
BEGIN

DECLARE v_can_count INT(127);
	
        SELECT count(c.candidateId) INTO 	v_can_count  
        from Candidate  c  where   c.candidateStatus ='IN'  AND c.companyId=p_comp_id;
                        
      
 RETURN v_can_count ;

End$$

CREATE DEFINER=`root`@`localhost` FUNCTION `fun_candidate_pending_Count` (`p_comp_id` INT) RETURNS INT(11) NO SQL
BEGIN

DECLARE v_can_pen_count INT(127);
	
        SELECT count(c.candidateId) INTO 	v_can_pen_count  
        from Candidate  c  where   c.candidateStatus ='PN'  AND c.companyId=p_comp_id;
                        
      
 RETURN v_can_pen_count ;

End$$

CREATE DEFINER=`root`@`localhost` FUNCTION `fun_check_patternIdDays_bydate` (`patternId` INT(25), `dateStr` VARCHAR(25)) RETURNS INT(100) NO SQL
BEGIN
 DECLARE V_DAYS VARCHAR(255);  
 DECLARE DAYS VARCHAR(255);  
 DECLARE position  INT;
set V_DAYS=   (select twp.day from TMSWeekOffPattern twp where twp.patternId=patternId);

  SET position= 1;

 set @length= (SELECT fun_get_array_lenght(V_DAYS,','));
 WHILE position <=@length  DO
  set DAYS= (select fun_spilt_str(V_DAYS,',',position));
      if(DAYS=(select  DATE_FORMAT(dateStr, '%a'))) THEN
      
         return 1 ;
         
       
       
       end if;
         
  SET  position = position+ 1; 
 END WHILE;
 RETURN 0;



END$$

CREATE DEFINER=`root`@`localhost` FUNCTION `fun_count_arrequest_notification` (`p_comp_id` INT(127) UNSIGNED, `p_emp_id` INT(127) UNSIGNED) RETURNS INT(11) NO SQL
BEGIN


DECLARE v_ar_request_count INT(127) DEFAULT 0;
 	
    
    
SELECT count( thd.arID)  INTO v_ar_request_count FROM TMSARRequest thd INNER join Employee e on thd.employeeId= e.employeeId where thd.status='PEN'
 and DATE(thd.dateCreated) = CURRENT_DATE() and e.ReportingToEmployee=p_emp_id  and thd.companyId=p_comp_id; 
 
  
     return v_ar_request_count; 

      
       

   
END$$

CREATE DEFINER=`root`@`localhost` FUNCTION `fun_count_comp_off_notification` (`p_comp_id` INT(127) UNSIGNED, `p_emp_id` INT(127) UNSIGNED) RETURNS INT(11) NO SQL
BEGIN


DECLARE v_comp_off_count INT(127) DEFAULT 0;
 	
   
              
SELECT count( thd.tmsCompensantoryOffId) INTO v_comp_off_count FROM TMSCompensantoryOff thd INNER join Employee e on thd.employeeId= e.employeeId where thd.status='PEN' and 
DATE(thd.dateCreated) = CURRENT_DATE() and e.ReportingToEmployee=p_emp_id and thd.companyId=p_comp_id;

         return v_comp_off_count;
      
          
   
END$$

CREATE DEFINER=`root`@`localhost` FUNCTION `fun_count_empanniversarytodaydate` (`p_comp_id` INT(127)) RETURNS INT(11) NO SQL
BEGIN

DECLARE v_aniversary_count INT(127);
	
              
                        
      select COUNT(em.employeeId)
	  INTO 	v_aniversary_count  from 
      Employee em where 
      MONTH(em.anniversaryDate) = MONTH(NOW()) AND em.activeStatus ='AC'  

and em.companyId=p_comp_id ;    
    
 RETURN v_aniversary_count ;

End$$

CREATE DEFINER=`root`@`localhost` FUNCTION `fun_count_empbirthdaytodaydate` (`p_comp_id` INT(127)) RETURNS INT(11) NO SQL
BEGIN

DECLARE v_birthday_count INT(127);
	

    
       select COUNT(em.employeeId)
			INTO 		v_birthday_count  from 
      Employee em where 
      MONTH(em.dateOfBirth) = MONTH(NOW())  AND   em.activeStatus ='AC' and em.companyId=p_comp_id;
                    
                        
                             
RETURN v_birthday_count ;

End$$

CREATE DEFINER=`root`@`localhost` FUNCTION `fun_count_employee_notification` (`p_comp_id` INT(127), `p_flag` VARCHAR(255)) RETURNS INT(11) NO SQL
BEGIN


DECLARE v_ticket_count INT(127) DEFAULT 0;
DECLARE v_sepration_count INT(127) DEFAULT 0;
	
    case when p_flag='ticket' then
      
      SELECT   count( thd.ticketRaisingHDId) INTO v_ticket_count  FROM  TicketRaisingHD thd
 where thd.status='Open' and DATE(thd.dateCreated) = CURRENT_DATE() and thd.companyId=p_comp_id;    
     return v_ticket_count;

   when p_flag='sepration' then
 SELECT   count( s.separationId) INTO v_sepration_count FROM  Separation s
 
 where s.status='P' and s.dateCreated = CURRENT_DATE();
 
   return v_sepration_count;
     
      when p_flag='Both' then 
   
   SELECT   count( thd.ticketRaisingHDId) INTO v_ticket_count  FROM  TicketRaisingHD thd
 where thd.status='Open' and DATE(thd.dateCreated) = CURRENT_DATE() and thd.companyId=p_comp_id;
 
  SELECT   count( s.separationId) INTO v_sepration_count FROM  Separation s
   
 where s.status='P' and s.dateCreated = CURRENT_DATE() ;

      
      return v_ticket_count+v_sepration_count;
      
         END CASE;

   
END$$

CREATE DEFINER=`root`@`localhost` FUNCTION `fun_count_empworkanniversarytodaydate` (`p_comp_id` INT(127)) RETURNS INT(11) NO SQL
BEGIN


DECLARE v_workanniversary_count INT(127);
	
                        
      select COUNT(em.employeeId)
			INTO 		v_workanniversary_count from 
      Employee em where 
      MONTH(em.dateOfJoining) = MONTH(NOW())  AND   em.activeStatus ='AC'  and em.companyId=p_comp_id ;    
   
                             
RETURN v_workanniversary_count ;


END$$

CREATE DEFINER=`root`@`localhost` FUNCTION `fun_count_emp_exit_this_month` (`p_comp_id` INT(11)) RETURNS INT(11) NO SQL
BEGIN

DECLARE v_exit_emp_count INT(127);
	select COUNT(em.employeeId)
	  INTO 	v_exit_emp_count  from 
      Employee em  join Separation s on em.employeeId= s.employeeId
     where s.status ='APR'  	and s.exitDate >= CURRENT_DATE   and   MONTH(s.exitDate) = 	MONTH(NOW())  and   em.activeStatus ='AC'
    and em.companyId=p_comp_id ;    
    
 RETURN v_exit_emp_count ;

End$$

CREATE DEFINER=`root`@`localhost` FUNCTION `fun_count_help_notification` (`p_comp_id` INT(127) UNSIGNED, `p_emp_id` INT(127) UNSIGNED) RETURNS INT(11) NO SQL
BEGIN


DECLARE v_help_count INT(127) DEFAULT 0;
 
SELECT count( thd.ticketRaisingHDId) INTO v_help_count  FROM TicketRaisingHD thd INNER join Employee e on thd.employeeId= e.employeeId where thd.status='Open' and DATE(thd.dateCreated) = CURRENT_DATE() and e.ReportingToEmployee=p_emp_id and thd.companyID=p_comp_id;



         return v_help_count;
      
          
   
END$$

CREATE DEFINER=`root`@`localhost` FUNCTION `fun_count_holiday_monthwise` (`p_comp_id` INT) RETURNS INT(11) NO SQL
BEGIN

DECLARE v_holiday_count INT(127);
        

    
       select COUNT(*)
                        INTO                 v_holiday_count  from 
      TMSHolidays tm  join 
      TMSLeavePeriod tp  on  tm.leavePeriodId= tp.leavePeriodId and   tp.activeStatus='AC'
     
      where (tm.activeStatus='AC')
       and  (MONTH(tm.fromDate)=MONTH(NOW()) OR MONTH(tm.toDate)=MONTH(NOW())) and
       tp.companyId=p_comp_id;
                    
                        
                             
RETURN v_holiday_count ;

End$$

CREATE DEFINER=`root`@`localhost` FUNCTION `fun_count_leave_notification` (`p_comp_id` INT(127) UNSIGNED, `p_emp_id` INT(127) UNSIGNED) RETURNS INT(11) NO SQL
BEGIN


DECLARE v_leave_count INT(127) DEFAULT 0;
 
     SELECT count( thd.leaveId) INTO v_leave_count FROM TMSLeaveEntries thd INNER join Employee e on thd.employeeId= e.employeeId where thd.status='PEN'
 and DATE(thd.dateCreated) = CURRENT_DATE() and e.ReportingToEmployee=p_emp_id  and thd.companyId=p_comp_id; 
  
     return v_leave_count; 

 
      
   
END$$

CREATE DEFINER=`root`@`localhost` FUNCTION `fun_count_myteam_on_leave` (`p_comp_id` INT(127) UNSIGNED, `p_emp_id` INT(127) UNSIGNED) RETURNS INT(11) NO SQL
BEGIN

DECLARE v_emp_count INT(127);
	
        select COUNT(e.employeeId) 
         INTO 	v_emp_count 
        from TMSLeaveEntries tl INNER join Employee e on tl.employeeId= e.employeeId where 
DAY(tl.fromDate)= DAY(now()) AND tl.status='APR'
and e.companyId=p_comp_id and e.ReportingToEmployee=p_emp_id;
      
                        
         
    
 RETURN v_emp_count ;

End$$

CREATE DEFINER=`root`@`localhost` FUNCTION `fun_count_present_leave_absent` (`p_comp_id` INT(127), `p_flag` VARCHAR(255), `p_date` VARCHAR(255)) RETURNS INT(11) UNSIGNED NO SQL
BEGIN


DECLARE v_present_count INT(127) DEFAULT 0;
DECLARE v_absent_count INT(127) DEFAULT 0;
DECLARE v_leave_count INT(127) DEFAULT 0;
DECLARE v_emp_count INT(127) DEFAULT 0;	
    case when p_flag='P' then
      
      SELECT   count( al.attendanceLogId) INTO v_present_count  FROM  AttendanceLogs al
 where  DATE(al.attendanceDate) = p_date and al.companyId=p_comp_id;    
     return v_present_count;

   when p_flag='L' then
 SELECT   count( ld.leaveId) INTO v_leave_count FROM  TMSLeaveEntriesDatewise ld
 
 where ld.leaveStatus='APR' and ld.leaveDate=p_date ;
 
   return v_leave_count;
     
      when p_flag='A' then 
   
   SELECT   count( al.attendanceLogId) INTO v_present_count  FROM  AttendanceLogs al
 where  DATE(al.attendanceDate) = p_date and al.companyId=p_comp_id;  
 
 SELECT   count( ld.leaveId) INTO v_leave_count FROM  TMSLeaveEntriesDatewise ld
 
 where ld.leaveStatus='APR' and ld.leaveDate=p_date ;
                     
      select COUNT(em.employeeId)
	  INTO 	v_emp_count  from 
      Employee em where 
     em.activeStatus ='AC' and em.companyId=p_comp_id ;    
    
 
      
      return v_emp_count-(v_leave_count +v_present_count);
      
         END CASE;

   
END$$

CREATE DEFINER=`root`@`localhost` FUNCTION `fun_count_sepration_notification` (`p_comp_id` INT(127) UNSIGNED, `p_emp_id` INT(127) UNSIGNED) RETURNS INT(11) NO SQL
BEGIN


DECLARE v_seperation_count INT(127) DEFAULT 0;
 	
     
SELECT   count( thd.separationId)  INTO  v_seperation_count FROM  Separation thd  INNER join Employee e on thd.employeeId= e.employeeId 
 where thd.status='PEN' and DATE(thd.dateCreated) = CURRENT_DATE() and   e.ReportingToEmployee=p_emp_id
 and thd.companyId=p_comp_id  ;  
 
         return v_seperation_count;

      
        
   
END$$

CREATE DEFINER=`root`@`localhost` FUNCTION `fun_data_validation` (`p_from_date` VARCHAR(127), `p_to_date` VARCHAR(127), `p_employee_id` INT(127)) RETURNS INT(100) NO SQL
BEGIN


IF EXISTS(select * from TMSShift) THEN
RETURN 1;
ELSE

RETURN 0;
end if;



END$$

CREATE DEFINER=`root`@`localhost` FUNCTION `fun_get_array_lenght` (`p_string` TEXT, `p_char` VARCHAR(255)) RETURNS INT(11) BEGIN
	
	DECLARE v_count 		INT 		 DEFAULT 0;
	DECLARE v_test_number 	INT 		 DEFAULT NULL;
	
	loop_occurence : LOOP
	 	
		SET v_test_number = INSTR(p_string, p_char);
		
		IF  v_test_number = 0 THEN
			LEAVE  loop_occurence;
		END IF;
		
		SET v_count = v_count + 1;
		
		SET	p_string	=	SUBSTRING( p_string, INSTR(p_string, p_char)+1, LENGTH(p_string) );
		
	END LOOP;
	
	SET v_count = v_count + 1;
	
	RETURN (v_count);

END$$

CREATE DEFINER=`root`@`localhost` FUNCTION `fun_get_array_value` (`p_string` TEXT, `p_char` VARCHAR(255), `p_index` INT) RETURNS TEXT CHARSET utf8 BEGIN

	DECLARE v_print_string 	TEXT DEFAULT NULL;
	
	DECLARE v_occurence 	INT 		 DEFAULT 0;
	
	DECLARE v_string_value 	TEXT DEFAULT NULL;
	
	DECLARE v_id 			INT 		 DEFAULT 0;
	
	

	DROP TEMPORARY TABLE IF EXISTS string_array;
	CREATE TEMPORARY TABLE IF NOT EXISTS string_array  
		(
			  id 				int(127) 		NOT NULL ,
			  string 			TEXT 			NOT NULL,
			  PRIMARY KEY (id)
			);
	
	SET v_occurence =  fun_get_occurence(p_string, p_char);
	
	loop_label : LOOP
	 	
		SET v_print_string = SUBSTRING_INDEX(p_string, p_char, 1);
		
		INSERT INTO string_array (id, string)  VALUES (v_id, v_print_string);
		
		SET v_id = v_id + 1;
		
		SET	p_string	=	SUBSTRING( p_string, INSTR(p_string, p_char)+1, LENGTH(p_string) );
	 	
		IF  v_occurence = 0 THEN 
			LEAVE  loop_label;
		END  IF;
		
		SET v_occurence = 	v_occurence - 1; 
		
     END LOOP; 
	 
	 SELECT string INTO v_string_value FROM string_array WHERE id = p_index;
	 
	 RETURN v_string_value;  

END$$

CREATE DEFINER=`root`@`localhost` FUNCTION `fun_get_Count_emp_settlementPending` (`p_comp_id` INT) RETURNS INT(11) NO SQL
BEGIN

DECLARE v_settlement_emp_count INT(127);
	
    select COUNT(e.employeeId)
	  INTO 	v_settlement_emp_count   
      from Employee e JOIN Separation s  ON e.employeeId=s.employeeId JOIN Department dept
      ON e.departmentId=dept.departmentId  Where  s.status='APR' and e.activeStatus='AC' and e.companyId=p_comp_id;
      

    
 RETURN v_settlement_emp_count ;

End$$

CREATE DEFINER=`root`@`localhost` FUNCTION `fun_get_dept_count` (`p_comp_id` INT(127)) RETURNS INT(11) NO SQL
BEGIN


DECLARE v_dept_count INT(127);
	
              
                        
      select COUNT( * )
	  INTO 	v_dept_count  from 
      Department d where d.companyId=p_comp_id
     ;    
    
 RETURN v_dept_count ;








end$$

CREATE DEFINER=`root`@`localhost` FUNCTION `fun_get_emp_count` (`p_comp_id` INT) RETURNS INT(11) NO SQL
BEGIN

DECLARE v_emp_count INT(127);
	
              
                        
      select COUNT(em.employeeId)
	  INTO 	v_emp_count  from 
      Employee em where 
     em.activeStatus ='AC' and em.companyId=p_comp_id ;    
    
 RETURN v_emp_count ;

End$$

CREATE DEFINER=`root`@`localhost` FUNCTION `fun_myteam_on_leave_monthwise` (`p_comp_id` INT(127) UNSIGNED, `p_emp_id` INT(127) UNSIGNED) RETURNS INT(11) NO SQL
BEGIN

DECLARE v_emp_count INT(127);
	
    
    
        select COUNT(e.employeeId) 
         INTO 	v_emp_count 
        from TMSLeaveEntries tl INNER join Employee e on tl.employeeId= e.employeeId where 
MONTH(tl.fromDate)= MONTH(now()) AND tl.status='APR'
and e.companyId=p_comp_id and e.ReportingToEmployee=p_emp_id;
      
                        
         
    
 RETURN v_emp_count ;

End$$

CREATE DEFINER=`root`@`localhost` FUNCTION `fun_noticePeriod_emp_count` (`p_comp_id` INT) RETURNS INT(11) NO SQL
BEGIN

DECLARE v_noticePeriod_emp_count INT(127);
	select COUNT(em.employeeId)
	  INTO 	v_noticePeriod_emp_count  from 
      Employee em  join Separation s on em.employeeId= s.employeeId
    where s.status ='APR'   and s.exitDate >= CURRENT_DATE and   em.activeStatus ='AC'
    and em.companyId=p_comp_id ;    
    
 RETURN v_noticePeriod_emp_count ;

End$$

CREATE DEFINER=`root`@`localhost` FUNCTION `fun_set_exception_handling` (`p_process_id` INT(20), `p_process_name` VARCHAR(50), `p_sqlstate` VARCHAR(255), `p_error_no` INT(127), `p_error_message` TEXT, `p_remark` TEXT) RETURNS TEXT CHARSET utf8 BEGIN
	DECLARE v_full_error TEXT DEFAULT NULL;

	INSERT INTO procedure_exception_log
			(procedure_id ,	procedure_name,	code, 		error_no, 	message, 	mysql_server_time) 
	VALUES 
			(p_process_id,	p_process_name, 	p_sqlstate,	p_error_no ,	p_error_message,	NOW());
        
    SET v_full_error = CONCAT("ERROR ", p_error_no, " (",p_sqlstate, "): ", p_error_message);     
        
RETURN v_full_error;

END$$

CREATE DEFINER=`root`@`localhost` FUNCTION `fun_spilt_str` (`x` TEXT, `delim` VARCHAR(12), `pos` INT) RETURNS VARCHAR(1024) CHARSET utf8 RETURN REPLACE(SUBSTRING(SUBSTRING_INDEX(x, delim, pos),
       LENGTH(SUBSTRING_INDEX(x, delim, pos -1)) + 1),
       delim, '')$$

CREATE DEFINER=`root`@`localhost` FUNCTION `SPLIT_STR` (`x` VARCHAR(255), `delim` VARCHAR(12), `pos` INT) RETURNS VARCHAR(255) CHARSET latin1 BEGIN 
    RETURN REPLACE(SUBSTRING(SUBSTRING_INDEX(x, delim, pos),
       LENGTH(SUBSTRING_INDEX(x, delim, pos -1)) + 1),
       delim, '');
END$$

DELIMITER ;

-- --------------------------------------------------------

--
-- Table structure for table `AdditionalUserObjects`
--

CREATE TABLE `AdditionalUserObjects` (
  `additionalUserObjectsId` int(11) NOT NULL,
  `userId` int(11) NOT NULL,
  `objectId` int(11) NOT NULL,
  `addRecord` char(1) NOT NULL DEFAULT 'N',
  `modRecord` char(1) NOT NULL DEFAULT 'N',
  `delRecord` char(1) NOT NULL DEFAULT 'N',
  `viewRecord` char(1) NOT NULL DEFAULT 'N',
  `allowModi` char(1) DEFAULT 'N',
  `sUserId` int(11) NOT NULL,
  `dateCreated` datetime DEFAULT NULL,
  `userIdUpdate` int(11) DEFAULT NULL,
  `dateUpdate` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `Address`
--

CREATE TABLE `Address` (
  `addressId` int(11) NOT NULL,
  `countryId` int(11) NOT NULL,
  `stateId` int(11) NOT NULL,
  `cityId` int(11) NOT NULL,
  `pincode` char(6) DEFAULT NULL,
  `addressText` varchar(250) DEFAULT NULL,
  `landmark` varchar(100) DEFAULT NULL,
  `telephone` varchar(15) DEFAULT NULL,
  `mobile` varchar(15) DEFAULT NULL,
  `fax` varchar(50) DEFAULT NULL,
  `emailId` varchar(50) DEFAULT NULL,
  `website` varchar(50) DEFAULT NULL,
  `allowModi` char(1) DEFAULT 'N',
  `userId` int(11) DEFAULT NULL,
  `dateCreated` datetime DEFAULT NULL,
  `userIdUpdate` int(11) DEFAULT NULL,
  `dateUpdate` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `Address`
--

INSERT INTO `Address` (`addressId`, `countryId`, `stateId`, `cityId`, `pincode`, `addressText`, `landmark`, `telephone`, `mobile`, `fax`, `emailId`, `website`, `allowModi`, `userId`, `dateCreated`, `userIdUpdate`, `dateUpdate`) VALUES
(1, 1, 1, 2, '452009', 'Tenco Enclave,931,Near Rajendra Nagar,A B Road,Bijalpur,indore', NULL, NULL, NULL, NULL, NULL, NULL, NULL, 1, NULL, NULL, '2019-07-30 06:53:44');

-- --------------------------------------------------------

--
-- Table structure for table `AddressHistory`
--

CREATE TABLE `AddressHistory` (
  `addressId` int(11) NOT NULL,
  `countryId` int(11) NOT NULL,
  `stateId` int(11) NOT NULL,
  `cityId` int(11) NOT NULL,
  `pincode` char(6) DEFAULT NULL,
  `addressText` varchar(100) DEFAULT NULL,
  `landmark` varchar(100) DEFAULT NULL,
  `telephone` varchar(15) DEFAULT NULL,
  `mobile` varchar(15) DEFAULT NULL,
  `fax` varchar(50) DEFAULT NULL,
  `emailId` varchar(50) DEFAULT NULL,
  `website` varchar(50) DEFAULT NULL,
  `allowModi` char(1) DEFAULT 'N',
  `userId` int(11) NOT NULL,
  `dateCreated` datetime DEFAULT NULL,
  `userIdUpdate` varchar(50) DEFAULT NULL,
  `dateUpdate` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `ArearCalculation`
--

CREATE TABLE `ArearCalculation` (
  `arearCalculationId` int(11) NOT NULL,
  `arearId` int(11) NOT NULL,
  `payrollMonth` varchar(25) DEFAULT NULL,
  `actualAmount` decimal(10,2) NOT NULL DEFAULT '0.00',
  `pfDeduction` decimal(10,2) NOT NULL DEFAULT '0.00',
  `esiDeduction` decimal(10,2) DEFAULT '0.00',
  `ptDeduction` decimal(10,0) DEFAULT '0',
  `netPayableAmount` decimal(10,2) DEFAULT '0.00',
  `userId` int(11) NOT NULL,
  `dateCreated` datetime NOT NULL,
  `userIdUpdate` int(11) DEFAULT NULL,
  `dateUpdated` datetime DEFAULT NULL,
  `companyId` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `ArearMaster`
--

CREATE TABLE `ArearMaster` (
  `arearId` int(11) NOT NULL,
  `employeeId` int(11) NOT NULL,
  `bookedPayrollMonth` varchar(25) DEFAULT NULL,
  `arearFrom` datetime NOT NULL,
  `arearTo` datetime NOT NULL,
  `isBooked` int(1) DEFAULT '0',
  `companyId` int(11) NOT NULL,
  `userId` int(11) NOT NULL,
  `dateCreated` datetime NOT NULL,
  `userIdUpdate` int(11) DEFAULT NULL,
  `dateUpdate` datetime DEFAULT NULL,
  `basicSalary` decimal(10,2) DEFAULT '0.00',
  `specialAllowance` decimal(10,2) DEFAULT '0.00'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `ArrearPayOut`
--

CREATE TABLE `ArrearPayOut` (
  `processMonth` varchar(10) NOT NULL,
  `employeeId` int(11) NOT NULL,
  `payHeadId` int(11) NOT NULL,
  `amount` decimal(12,2) DEFAULT '0.00'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `ArrearReportPayOut`
--

CREATE TABLE `ArrearReportPayOut` (
  `processMonth` varchar(10) NOT NULL,
  `processDate` date DEFAULT NULL,
  `employeeId` int(11) NOT NULL,
  `departmentId` int(11) NOT NULL,
  `companyId` int(11) DEFAULT NULL,
  `cityId` int(11) NOT NULL,
  `employeeCode` varchar(10) DEFAULT NULL,
  `Name` varchar(100) DEFAULT NULL,
  `bankName` varchar(50) DEFAULT NULL,
  `accountNumber` varchar(20) DEFAULT NULL,
  `dateOfJoining` date DEFAULT NULL,
  `presense` decimal(5,2) DEFAULT '0.00',
  `weekoff` decimal(5,2) DEFAULT '0.00',
  `publicholidays` decimal(5,2) DEFAULT '0.00',
  `paidleave` decimal(5,2) DEFAULT '0.00',
  `casualleave` decimal(6,2) DEFAULT '0.00',
  `seekleave` decimal(5,2) DEFAULT '0.00',
  `absense` decimal(5,2) DEFAULT '0.00',
  `payDays` decimal(5,2) DEFAULT '0.00',
  `payableDays` decimal(5,2) NOT NULL,
  `Basic` decimal(12,2) DEFAULT '0.00',
  `BasicEarning` decimal(12,2) DEFAULT '0.00',
  `dearnessAllowance` decimal(12,2) DEFAULT '0.00',
  `dearnessAllowanceEarning` decimal(12,2) DEFAULT '0.00',
  `ConveyanceAllowance` decimal(12,2) DEFAULT '0.00',
  `ConveyanceAllowanceEarning` decimal(12,2) DEFAULT '0.00',
  `HRA` decimal(12,2) DEFAULT '0.00',
  `HRAEarning` decimal(12,2) DEFAULT '0.00',
  `MedicalAllowance` decimal(12,2) DEFAULT '0.00',
  `MedicalAllowanceEarning` decimal(12,2) DEFAULT '0.00',
  `AdvanceBonus` decimal(12,2) DEFAULT '0.00',
  `AdvanceBonusEarning` decimal(12,2) DEFAULT '0.00',
  `SpecialAllowance` decimal(12,2) DEFAULT '0.00',
  `SpecialAllowanceEarning` decimal(12,2) DEFAULT '0.00',
  `leaveTravelAllowance` decimal(12,2) DEFAULT '0.00',
  `leaveTravelAllowanceEarning` decimal(12,2) DEFAULT '0.00',
  `performanceLinkedIncome` decimal(12,2) DEFAULT '0.00',
  `performanceLinkedIncomeEarning` decimal(12,2) DEFAULT '0.00',
  `uniformAllowance` decimal(12,2) DEFAULT '0.00',
  `uniformAllowanceEarning` decimal(12,2) DEFAULT '0.00',
  `CompanyBenefits` decimal(12,2) DEFAULT '0.00',
  `CompanyBenefitsEarning` decimal(12,2) DEFAULT '0.00',
  `EmployeeLoansAdvnace` decimal(12,2) DEFAULT '0.00',
  `EmployeeLoansAdvnaceEarning` decimal(12,2) DEFAULT '0.00',
  `ProvidentFundEmployee` decimal(12,2) DEFAULT '0.00',
  `ProvidentFundEmployer` decimal(12,2) DEFAULT '0.00',
  `ProvidentFundEmployerPension` decimal(12,2) DEFAULT '0.00',
  `ESI_Employee` decimal(12,2) DEFAULT '0.00',
  `ESI_Employer` decimal(12,2) DEFAULT '0.00',
  `PT` decimal(12,2) DEFAULT '0.00',
  `TDS` decimal(12,2) DEFAULT '0.00',
  `Loan` decimal(12,2) DEFAULT '0.00',
  `GrossSalary` decimal(12,2) DEFAULT '0.00',
  `TotalEarning` decimal(12,2) DEFAULT '0.00',
  `TotalDeduction` decimal(12,2) DEFAULT '0.00',
  `NetPayableAmount` decimal(12,2) DEFAULT '0.00',
  `UANNO` varchar(20) DEFAULT NULL,
  `PFNumber` varchar(50) DEFAULT NULL,
  `ESICNumber` varchar(50) DEFAULT NULL,
  `fatherName` varchar(100) DEFAULT NULL,
  `DOB` date DEFAULT NULL,
  `gender` varchar(10) DEFAULT NULL,
  `husbandName` varchar(100) DEFAULT NULL,
  `maritalStatus` varchar(20) DEFAULT NULL,
  `epfJoining` date DEFAULT NULL,
  `IFSCCode` varchar(20) DEFAULT NULL,
  `aadharNo` varchar(20) DEFAULT NULL,
  `PANNO` varchar(20) DEFAULT NULL,
  `mobileNo` varchar(15) DEFAULT NULL,
  `email` varchar(50) DEFAULT NULL,
  `otherAllowance` decimal(12,2) DEFAULT '0.00',
  `otherAllowanceEarning` decimal(12,2) DEFAULT '0.00',
  `overtime` decimal(5,2) DEFAULT '0.00',
  `transactionNo` varchar(20) DEFAULT NULL,
  `bankAccountNumber` varchar(20) DEFAULT NULL,
  `bankAmount` decimal(12,2) DEFAULT '0.00',
  `userId` int(11) NOT NULL,
  `userIdUpdate` int(11) DEFAULT NULL,
  `dateCreated` date NOT NULL,
  `dateUpdate` date DEFAULT NULL,
  `PFEarning` decimal(12,2) DEFAULT '0.00',
  `PensionEarningSalary` decimal(12,2) DEFAULT '0.00',
  `otherEarning` decimal(12,2) DEFAULT NULL,
  `otherDeduction` decimal(12,2) DEFAULT '0.00',
  `arearAmount` decimal(10,2) DEFAULT '0.00',
  `isNoPFDeduction` char(1) DEFAULT 'N',
  `isNoESIDeduction` char(1) DEFAULT 'N',
  `remarks` varchar(250) DEFAULT NULL,
  `epfNominee` varchar(200) DEFAULT NULL,
  `epfNomineeRelation` varchar(100) DEFAULT NULL,
  `esicNominee` varchar(200) DEFAULT NULL,
  `esicNomineeRelation` varchar(100) DEFAULT NULL,
  `esicjoining` date DEFAULT NULL,
  `transactionMode` varchar(125) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `Attendance`
--

CREATE TABLE `Attendance` (
  `srno` int(11) DEFAULT NULL,
  `employeeCode` varchar(10) NOT NULL,
  `employeeName` varchar(50) DEFAULT NULL,
  `presense` decimal(5,2) DEFAULT '0.00',
  `weekoff` decimal(5,2) DEFAULT '0.00',
  `publicholidays` decimal(5,2) DEFAULT '0.00',
  `leaves` decimal(10,2) DEFAULT NULL,
  `paidleave` decimal(5,2) DEFAULT '0.00',
  `casualleave` decimal(6,2) DEFAULT '0.00',
  `seekleave` decimal(5,2) DEFAULT '0.00',
  `absense` decimal(5,2) DEFAULT '0.00',
  `payDays` decimal(5,2) DEFAULT '0.00',
  `companyId` int(11) NOT NULL,
  `processMonth` varchar(10) NOT NULL,
  `userId` int(11) NOT NULL,
  `dateCreated` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `AttendanceLogs`
--

CREATE TABLE `AttendanceLogs` (
  `attendanceLogId` int(11) NOT NULL,
  `attendanceDate` date NOT NULL,
  `employeeId` int(11) DEFAULT NULL,
  `employeeCode` varchar(10) DEFAULT NULL,
  `inTime` varchar(10) DEFAULT NULL,
  `outTime` varchar(10) DEFAULT NULL,
  `inDeviceId` int(11) DEFAULT NULL,
  `outDeviceId` int(11) DEFAULT NULL,
  `location` varchar(100) DEFAULT NULL,
  `mode` varchar(100) DEFAULT NULL,
  `companyId` int(11) NOT NULL,
  `createdBy` int(11) DEFAULT NULL,
  `createdDate` datetime DEFAULT NULL,
  `updatedBy` int(11) DEFAULT NULL,
  `updatedDate` date NOT NULL,
  `delayedTime` varchar(25) DEFAULT NULL,
  `inout` varchar(200) DEFAULT NULL,
  `status` varchar(200) DEFAULT NULL,
  `totalTime` varchar(200) DEFAULT NULL,
  `latitude` varchar(500) DEFAULT NULL,
  `longitude` varchar(500) DEFAULT NULL,
  `address` varchar(500) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `Banks`
--

CREATE TABLE `Banks` (
  `bankId` int(11) NOT NULL,
  `AccountHolder` varchar(50) DEFAULT NULL,
  `BankName` varchar(50) DEFAULT NULL,
  `AccountNo` varchar(50) DEFAULT NULL,
  `IFSCCode` varchar(50) DEFAULT NULL,
  `BankBranch` varchar(50) DEFAULT NULL,
  `AccountType` varchar(2) DEFAULT NULL,
  `effectiveStartDate` datetime DEFAULT NULL,
  `effectiveEndDate` datetime DEFAULT NULL,
  `activeStatus` varchar(2) DEFAULT NULL,
  `branchId` int(11) DEFAULT NULL,
  `companyId` int(11) NOT NULL,
  `groupId` int(11) DEFAULT NULL,
  `allowModi` char(1) DEFAULT 'N',
  `userId` int(11) NOT NULL,
  `dateCreated` datetime DEFAULT NULL,
  `userIdUpdate` int(11) DEFAULT NULL,
  `dateUpdate` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Triggers `Banks`
--
DELIMITER $$
CREATE TRIGGER `banks_history_trigger` BEFORE UPDATE ON `Banks` FOR EACH ROW BEGIN

 declare v_bankId int(11) ;
 declare v_AccountHolder varchar(50) ;
 declare v_BankName varchar(50);
 declare v_AccountNo varchar(50) ;
 declare v_IFSCCode varchar(50) ;
 declare v_BankBranch varchar(50) ;
 declare v_AccountType varchar(2) ;
 declare v_effectiveStartDate datetime ;
 declare v_effectiveEndDate datetime ;
 declare v_activeStatus varchar(2) ;
 declare v_branchId int(11) ;
 declare v_companyId int(11) ;
 declare v_groupId int(11) ;
 declare v_allowModi char(1) ;
 declare v_userId int(11) ;
 declare v_dateCreated datetime ;
 declare v_userIdUpdate int(11) ;
 declare v_dateUpdate datetime ;
 
 
 
  set v_bankId =OLD.bankId ;
 set v_AccountHolder =OLD.AccountHolder ;
 set v_BankName =OLD.BankName;
 set v_AccountNo =OLD.AccountNo ;
 set v_IFSCCode =OLD.IFSCCode ;
 set v_BankBranch =OLD.BankBranch ;
 set v_AccountType =OLD.AccountType ;
 set v_effectiveStartDate =OLD.effectiveStartDate ;
 set v_effectiveEndDate =OLD.effectiveEndDate ;
 set v_activeStatus =OLD.activeStatus ;
 set v_branchId =OLD.branchId ;
 set v_companyId =OLD.companyId ;
 set v_groupId =OLD.groupId ;
 set v_allowModi =OLD.allowModi ;
 set v_userId =OLD.userId ;
 set v_dateCreated =OLD.dateCreated ;
 set v_userIdUpdate =NEW.userIdUpdate ;
 set v_dateUpdate =OLD.dateUpdate ;
 

 
  Insert INTO BanksHistory(

  bankId ,
 AccountHolder,
 BankName,
 AccountNo ,
 IFSCCode ,
 BankBranch ,
 AccountType ,
 effectiveStartDate ,
 effectiveEndDate ,
 activeStatus ,
 branchId ,
 companyId ,
 groupId ,
 allowModi ,
 userId ,
 dateCreated ,
 userIdUpdate ,
 dateUpdate 
      
   ) VALUES
  (
      
   v_bankId ,
 v_AccountHolder,
 v_BankName,
 v_AccountNo ,
 v_IFSCCode ,
 v_BankBranch ,
 v_AccountType ,
 v_effectiveStartDate ,
 v_effectiveEndDate ,
 v_activeStatus ,
 v_branchId ,
 v_companyId ,
 v_groupId ,
 v_allowModi ,
 v_userId ,
 v_dateCreated ,
 v_userIdUpdate ,
 v_dateUpdate 
      
  );
End
$$
DELIMITER ;

-- --------------------------------------------------------

--
-- Table structure for table `BanksHistory`
--

CREATE TABLE `BanksHistory` (
  `bankId` int(11) NOT NULL,
  `AccountHolder` varchar(50) DEFAULT NULL,
  `BankName` varchar(50) DEFAULT NULL,
  `AccountNo` varchar(50) DEFAULT NULL,
  `IFSCCode` varchar(50) DEFAULT NULL,
  `BankBranch` varchar(50) DEFAULT NULL,
  `AccountType` varchar(2) DEFAULT NULL,
  `effectiveStartDate` datetime DEFAULT NULL,
  `effectiveEndDate` datetime DEFAULT NULL,
  `activeStatus` varchar(2) DEFAULT NULL,
  `branchId` int(11) DEFAULT NULL,
  `companyId` int(11) NOT NULL,
  `groupId` int(11) DEFAULT NULL,
  `allowModi` char(1) DEFAULT 'N',
  `userId` int(11) NOT NULL,
  `dateCreated` datetime DEFAULT NULL,
  `userIdUpdate` int(11) DEFAULT NULL,
  `dateUpdate` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `BatchEmployee`
--

CREATE TABLE `BatchEmployee` (
  `id` int(11) NOT NULL,
  `firstName` varchar(100) NOT NULL,
  `lastName` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `BATCH_JOB_EXECUTION`
--

CREATE TABLE `BATCH_JOB_EXECUTION` (
  `JOB_EXECUTION_ID` bigint(20) NOT NULL,
  `VERSION` bigint(20) DEFAULT NULL,
  `JOB_INSTANCE_ID` bigint(20) NOT NULL,
  `CREATE_TIME` datetime NOT NULL,
  `START_TIME` datetime DEFAULT NULL,
  `END_TIME` datetime DEFAULT NULL,
  `STATUS` varchar(10) DEFAULT NULL,
  `EXIT_CODE` varchar(2500) DEFAULT NULL,
  `EXIT_MESSAGE` varchar(2500) DEFAULT NULL,
  `LAST_UPDATED` datetime DEFAULT NULL,
  `JOB_CONFIGURATION_LOCATION` varchar(2500) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `BATCH_JOB_EXECUTION_CONTEXT`
--

CREATE TABLE `BATCH_JOB_EXECUTION_CONTEXT` (
  `JOB_EXECUTION_ID` bigint(20) NOT NULL,
  `SHORT_CONTEXT` varchar(2500) NOT NULL,
  `SERIALIZED_CONTEXT` text
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `BATCH_JOB_EXECUTION_PARAMS`
--

CREATE TABLE `BATCH_JOB_EXECUTION_PARAMS` (
  `JOB_EXECUTION_ID` bigint(20) NOT NULL,
  `TYPE_CD` varchar(6) NOT NULL,
  `KEY_NAME` varchar(100) NOT NULL,
  `STRING_VAL` varchar(250) DEFAULT NULL,
  `DATE_VAL` datetime DEFAULT NULL,
  `LONG_VAL` bigint(20) DEFAULT NULL,
  `DOUBLE_VAL` double DEFAULT NULL,
  `IDENTIFYING` char(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `BATCH_JOB_EXECUTION_SEQ`
--

CREATE TABLE `BATCH_JOB_EXECUTION_SEQ` (
  `ID` bigint(20) NOT NULL,
  `UNIQUE_KEY` char(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `BATCH_JOB_INSTANCE`
--

CREATE TABLE `BATCH_JOB_INSTANCE` (
  `JOB_INSTANCE_ID` bigint(20) NOT NULL,
  `VERSION` bigint(20) DEFAULT NULL,
  `JOB_NAME` varchar(100) NOT NULL,
  `JOB_KEY` varchar(32) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `BATCH_JOB_SEQ`
--

CREATE TABLE `BATCH_JOB_SEQ` (
  `ID` bigint(20) NOT NULL,
  `UNIQUE_KEY` char(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `BATCH_STEP_EXECUTION`
--

CREATE TABLE `BATCH_STEP_EXECUTION` (
  `STEP_EXECUTION_ID` bigint(20) NOT NULL,
  `VERSION` bigint(20) NOT NULL,
  `STEP_NAME` varchar(100) NOT NULL,
  `JOB_EXECUTION_ID` bigint(20) NOT NULL,
  `START_TIME` datetime NOT NULL,
  `END_TIME` datetime DEFAULT NULL,
  `STATUS` varchar(10) DEFAULT NULL,
  `COMMIT_COUNT` bigint(20) DEFAULT NULL,
  `READ_COUNT` bigint(20) DEFAULT NULL,
  `FILTER_COUNT` bigint(20) DEFAULT NULL,
  `WRITE_COUNT` bigint(20) DEFAULT NULL,
  `READ_SKIP_COUNT` bigint(20) DEFAULT NULL,
  `WRITE_SKIP_COUNT` bigint(20) DEFAULT NULL,
  `PROCESS_SKIP_COUNT` bigint(20) DEFAULT NULL,
  `ROLLBACK_COUNT` bigint(20) DEFAULT NULL,
  `EXIT_CODE` varchar(2500) DEFAULT NULL,
  `EXIT_MESSAGE` varchar(2500) DEFAULT NULL,
  `LAST_UPDATED` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `BATCH_STEP_EXECUTION_CONTEXT`
--

CREATE TABLE `BATCH_STEP_EXECUTION_CONTEXT` (
  `STEP_EXECUTION_ID` bigint(20) NOT NULL,
  `SHORT_CONTEXT` varchar(2500) NOT NULL,
  `SERIALIZED_CONTEXT` text
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `BATCH_STEP_EXECUTION_SEQ`
--

CREATE TABLE `BATCH_STEP_EXECUTION_SEQ` (
  `ID` bigint(20) NOT NULL,
  `UNIQUE_KEY` char(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `Bonus`
--

CREATE TABLE `Bonus` (
  `bonusId` int(11) NOT NULL,
  `financialYear` varchar(10) DEFAULT NULL,
  `gradesId` int(11) NOT NULL,
  `bonusPer` decimal(5,2) DEFAULT '0.00',
  `effectiveStartDate` datetime DEFAULT NULL,
  `effectiveEndDate` datetime DEFAULT NULL,
  `activeStatus` varchar(2) DEFAULT 'A',
  `effectiveDate` date DEFAULT NULL,
  `companyId` int(11) NOT NULL,
  `groupId` int(11) DEFAULT NULL,
  `allowModi` char(1) DEFAULT 'N',
  `userId` int(11) NOT NULL,
  `dateCreated` datetime DEFAULT NULL,
  `userIdUpdate` int(11) DEFAULT NULL,
  `dateUpdate` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Triggers `Bonus`
--
DELIMITER $$
CREATE TRIGGER `bonus_history_trigger` BEFORE UPDATE ON `Bonus` FOR EACH ROW BEGIN

 declare v_bonusId int(11) ;
 declare v_financialYear varchar(10) ;
 declare v_gradesId int(11) ;
 declare v_bonusPer decimal(5,2) ;
 declare v_effectiveStartDate datetime ;
 declare v_effectiveEndDate datetime ;
 declare v_activeStatus varchar(2) ;
 declare v_effectiveDate date ;
 declare v_companyId int(11) ;
 declare v_groupId int(11) ;
 declare v_allowModi char(1); 
 declare v_userId int(11) ;
 declare v_dateCreated datetime ; 
 declare v_userIdUpdate int(11) ;
 declare v_dateUpdate datetime ;
 
 
 set v_bonusId =OLD.bonusId ;
 set v_financialYear =OLD.financialYear ;
 set v_gradesId =OLD.gradesId ;
 set v_bonusPer =OLD.bonusPer ;
 set v_effectiveStartDate =OLD.effectiveStartDate ;
 set v_effectiveEndDate =OLD.effectiveEndDate ;
 set v_activeStatus =OLD.activeStatus ;
 set v_effectiveDate =OLD.effectiveDate ;
 set v_companyId =OLD.companyId ;
 set v_groupId =OLD.groupId ;
 set v_allowModi =OLD.allowModi; 
 set v_userId =OLD.userId ;
 set v_dateCreated =OLD.dateCreated ; 
 set v_userIdUpdate =new.userIdUpdate ;
 set v_dateUpdate =OLD.dateUpdate ;
 
 
  Insert INTO BonusHistory(

   bonusId,
  financialYear ,
  gradesId,
  bonusPer,
  effectiveStartDate ,
  effectiveEndDate,
  activeStatus ,
  effectiveDate ,
  companyId ,
  groupId ,
  allowModi ,
  userId ,
  dateCreated ,
  userIdUpdate ,
  dateUpdate 
      
   ) VALUES
  (
      
   v_bonusId,
  v_financialYear ,
  v_gradesId,
  v_bonusPer,
  v_effectiveStartDate ,
  v_effectiveEndDate,
  v_activeStatus ,
  v_effectiveDate ,
  v_companyId ,
  v_groupId ,
  v_allowModi ,
  v_userId ,
  v_dateCreated ,
  v_userIdUpdate ,
  v_dateUpdate 
      
  );
End
$$
DELIMITER ;

-- --------------------------------------------------------

--
-- Table structure for table `BonusHistory`
--

CREATE TABLE `BonusHistory` (
  `bonusId` int(11) NOT NULL,
  `financialYear` varchar(10) DEFAULT NULL,
  `gradesId` int(11) NOT NULL,
  `bonusPer` decimal(5,2) DEFAULT '0.00',
  `effectiveStartDate` datetime DEFAULT NULL,
  `effectiveEndDate` datetime DEFAULT NULL,
  `activeStatus` varchar(2) DEFAULT 'A',
  `effectiveDate` date DEFAULT NULL,
  `companyId` int(11) NOT NULL,
  `groupId` int(11) DEFAULT NULL,
  `allowModi` char(1) DEFAULT 'N',
  `userId` int(11) NOT NULL,
  `dateCreated` datetime DEFAULT NULL,
  `userIdUpdate` int(11) DEFAULT NULL,
  `dateUpdate` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `Branch`
--

CREATE TABLE `Branch` (
  `branchId` int(11) NOT NULL,
  `companyId` int(11) NOT NULL,
  `groupId` int(11) DEFAULT NULL,
  `projectBranchpPantIndicator` char(1) DEFAULT 'B',
  `branchName` varchar(100) NOT NULL,
  `branchGstNo` varchar(50) DEFAULT NULL,
  `branchAddressId` int(11) NOT NULL,
  `effectiveStartDate` datetime DEFAULT NULL,
  `effectiveEndDate` datetime DEFAULT NULL,
  `activeStatus` varchar(2) DEFAULT NULL,
  `allowModi` char(1) DEFAULT 'N',
  `userId` int(11) DEFAULT NULL,
  `dateCreated` datetime DEFAULT NULL,
  `userIdUpdate` int(11) DEFAULT NULL,
  `dateUpdate` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Triggers `Branch`
--
DELIMITER $$
CREATE TRIGGER `branch_history_trigger` BEFORE UPDATE ON `Branch` FOR EACH ROW BEGIN
declare v_branchId INT(11);
declare v_companyId INT(11);
declare v_groupId VARCHAR(11);
declare v_projectBranchpPantIndicator char(1) ;
  
declare  v_branchName varchar(100) ;
declare  v_branchGstNo varchar(50) ;
declare  v_branchAddressId int(11) ;
declare  v_effectiveStartDate datetime ;
declare  v_effectiveEndDate datetime ;
declare  v_activeStatus varchar(2) ;
declare  v_allowModi char(1) ;
declare  v_userId int(11) ;
declare  v_dateCreated datetime ;
declare  v_userIdUpdate int(11) ;
declare  v_dateUpdate datetime ;



  set v_branchId=OLD.branchId ;
set v_companyId =OLD.companyId;
set v_groupId =OLD.groupId;
set v_projectBranchpPantIndicator =OLD.projectBranchpPantIndicator ;
  
set  v_branchName =OLD.branchName ;
set  v_branchGstNo =OLD.branchGstNo ;
set  v_branchAddressId =OLD.branchAddressId ;
set  v_effectiveStartDate =OLD.effectiveStartDate ;
set  v_effectiveEndDate =OLD.effectiveEndDate ;
set  v_activeStatus =OLD.activeStatus ;
set  v_allowModi =OLD.allowModi ;
set  v_userId =OLD.userId ;
set  v_dateCreated =OLD.dateCreated ;
set  v_userIdUpdate =NEW.userIdUpdate ;
set  v_dateUpdate =OLD.dateUpdate ;
 
  
 
Insert INTO BranchHistory(
branchId,
companyId,
groupId,
projectBranchpPantIndicator ,
branchName ,
branchGstNo,
branchAddressId,
effectiveStartDate ,
effectiveEndDate,
activeStatus ,
allowModi ,
userId ,
dateCreated ,
userIdUpdate ,
dateUpdate
   ) VALUES
  (
 
v_branchId,
v_companyId,
v_groupId,
v_projectBranchpPantIndicator ,
v_branchName ,
v_branchGstNo,
v_branchAddressId,
v_effectiveStartDate ,
v_effectiveEndDate,
v_activeStatus ,
v_allowModi ,
v_userId ,
v_dateCreated ,
v_userIdUpdate ,
v_dateUpdate
  );
  
  INSERT INTO AddressHistory(addressId,countryId,	stateId,	cityId,	pincode,	addressText,	landmark,	telephone,	mobile	,fax,	emailId,	website	,allowModi,	userId	,dateCreated,	userIdUpdate )
   select addressId,countryId,	stateId,	cityId,	pincode,	addressText,	landmark,	telephone,	mobile	,fax,	emailId,	website	,allowModi,	userId	,dateCreated,	v_userIdUpdate
	  from 
      Address ah where ah.addressId IN (v_branchAddressId)
     ; 
     
End
$$
DELIMITER ;

-- --------------------------------------------------------

--
-- Table structure for table `BranchHistory`
--

CREATE TABLE `BranchHistory` (
  `branchId` int(11) NOT NULL,
  `companyId` int(11) NOT NULL,
  `groupId` int(11) DEFAULT NULL,
  `projectBranchpPantIndicator` char(1) DEFAULT 'B',
  `branchName` varchar(100) NOT NULL,
  `branchGstNo` varchar(50) DEFAULT NULL,
  `branchAddressId` int(11) NOT NULL,
  `effectiveStartDate` datetime DEFAULT NULL,
  `effectiveEndDate` datetime DEFAULT NULL,
  `activeStatus` varchar(2) DEFAULT NULL,
  `allowModi` char(1) DEFAULT 'N',
  `userId` int(11) NOT NULL,
  `dateCreated` datetime DEFAULT NULL,
  `userIdUpdate` int(11) DEFAULT NULL,
  `dateUpdate` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `Candidate`
--

CREATE TABLE `Candidate` (
  `candidateId` int(11) NOT NULL,
  `candidateCode` varchar(50) DEFAULT NULL,
  `firstName` varchar(50) DEFAULT NULL,
  `middleName` varchar(50) DEFAULT NULL,
  `lastName` varchar(50) DEFAULT NULL,
  `dateOfJoining` date DEFAULT NULL,
  `endDate` date DEFAULT NULL,
  `dateOfBirth` date DEFAULT NULL,
  `anniversaryDate` date DEFAULT NULL,
  `probationDays` int(11) DEFAULT '0',
  `noticePeriodDays` int(11) DEFAULT '30',
  `gender` varchar(2) DEFAULT NULL,
  `maritalStatus` varchar(2) DEFAULT NULL,
  `bloodGroup` varchar(2) DEFAULT NULL,
  `empType` varchar(2) DEFAULT NULL,
  `voluntaryPfContribution` varchar(1) DEFAULT 'N',
  `departmentId` int(11) DEFAULT NULL,
  `designationId` int(11) DEFAULT NULL,
  `projectId` int(11) DEFAULT NULL,
  `clientId` int(11) DEFAULT NULL,
  `ReportingToEmployee` varchar(50) DEFAULT NULL,
  `contractStartDate` date DEFAULT NULL,
  `cityId` int(11) DEFAULT NULL,
  `contractOverDate` date DEFAULT NULL,
  `permanentAddressId` int(11) DEFAULT NULL,
  `presentAddressId` int(11) DEFAULT NULL,
  `referenceName` char(20) DEFAULT NULL,
  `referenceAddressId` int(11) DEFAULT NULL,
  `effectiveDate` date DEFAULT NULL,
  `activeStatus` varchar(2) DEFAULT 'A',
  `adharNumber` varchar(15) DEFAULT NULL,
  `candidateLogoPath` varchar(50) DEFAULT NULL,
  `effectiveStartDate` datetime DEFAULT NULL,
  `effectiveEndDate` datetime DEFAULT NULL,
  `companyId` int(11) DEFAULT NULL,
  `groupId` int(11) DEFAULT NULL,
  `allowModi` char(1) DEFAULT 'N',
  `userId` int(11) DEFAULT NULL,
  `dateCreated` datetime DEFAULT NULL,
  `userIdUpdate` int(11) DEFAULT NULL,
  `dateUpdate` datetime DEFAULT NULL,
  `stateId` int(11) DEFAULT NULL,
  `mobile` varchar(16) DEFAULT NULL,
  `emailId` varchar(50) DEFAULT NULL,
  `shiftId` int(11) DEFAULT NULL,
  `patternId` int(11) DEFAULT NULL,
  `timeContract` varchar(2) DEFAULT NULL,
  `languageId` int(11) DEFAULT NULL,
  `candidateStatus` varchar(125) DEFAULT NULL,
  `declineReason` varchar(250) DEFAULT NULL,
  `password` varchar(50) DEFAULT NULL,
  `leaveSchemeId` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `CandidateAddress`
--

CREATE TABLE `CandidateAddress` (
  `addressId` int(11) NOT NULL,
  `countryId` int(11) NOT NULL,
  `stateId` int(11) NOT NULL,
  `cityId` int(11) NOT NULL,
  `pincode` char(6) DEFAULT NULL,
  `addressText` varchar(250) DEFAULT NULL,
  `landmark` varchar(100) DEFAULT NULL,
  `telephone` varchar(15) DEFAULT NULL,
  `mobile` varchar(15) DEFAULT NULL,
  `fax` varchar(50) DEFAULT NULL,
  `emailId` varchar(50) DEFAULT NULL,
  `website` varchar(50) DEFAULT NULL,
  `allowModi` char(1) DEFAULT 'N',
  `userId` int(11) NOT NULL,
  `dateCreated` datetime DEFAULT NULL,
  `userIdUpdate` int(11) DEFAULT NULL,
  `dateUpdate` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `CandidateEducation`
--

CREATE TABLE `CandidateEducation` (
  `educationId` int(11) NOT NULL,
  `candidateId` int(11) NOT NULL,
  `qualificationId` varchar(2) NOT NULL,
  `degreeName` varchar(50) DEFAULT NULL,
  `nameOfInstitution` varchar(50) DEFAULT NULL,
  `nameOfBoard` varchar(50) DEFAULT NULL,
  `marksPer` decimal(5,2) DEFAULT NULL,
  `passingYear` int(11) DEFAULT NULL,
  `regularCorrespondance` varchar(2) DEFAULT 'R',
  `effectiveStartDate` datetime DEFAULT NULL,
  `effectiveEndDate` datetime DEFAULT NULL,
  `activeStatus` varchar(2) DEFAULT NULL,
  `companyId` int(11) DEFAULT NULL,
  `userId` int(11) DEFAULT NULL,
  `dateCreated` datetime DEFAULT NULL,
  `userIdUpdate` int(11) DEFAULT NULL,
  `dateUpdate` datetime DEFAULT NULL,
  `candidateEducationDoc` varchar(250) DEFAULT NULL,
  `documentName` varchar(250) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `CandidateFamily`
--

CREATE TABLE `CandidateFamily` (
  `familyId` int(11) NOT NULL,
  `candidateId` int(11) NOT NULL,
  `relation` varchar(2) DEFAULT NULL,
  `captions` varchar(2) DEFAULT NULL,
  `name` varchar(50) NOT NULL,
  `qualificationId` varchar(2) DEFAULT NULL,
  `occupations` varchar(2) DEFAULT NULL,
  `dateOfBirth` date DEFAULT NULL,
  `contactPhone` varchar(10) DEFAULT NULL,
  `contactMobile` varchar(10) DEFAULT NULL,
  `companyId` int(11) NOT NULL,
  `userId` int(11) DEFAULT NULL,
  `dateCreated` datetime DEFAULT NULL,
  `userIdUpdate` int(11) DEFAULT NULL,
  `dateUpdate` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `CandidateIdProofs`
--

CREATE TABLE `CandidateIdProofs` (
  `candidateIdProofsId` int(11) NOT NULL,
  `candidateId` int(11) NOT NULL,
  `idTypeId` varchar(2) NOT NULL,
  `idNumber` varchar(20) NOT NULL,
  `dateIssue` date DEFAULT NULL,
  `dateFrom` date DEFAULT NULL,
  `dateTo` date DEFAULT NULL,
  `candidateIdProofDoc` varchar(250) DEFAULT NULL,
  `activeStatus` varchar(2) DEFAULT 'A',
  `userId` int(11) DEFAULT NULL,
  `dateCreated` datetime DEFAULT NULL,
  `userIdUpdate` int(11) DEFAULT NULL,
  `dateUpdate` datetime DEFAULT NULL,
  `idProofDoc` varchar(250) DEFAULT NULL,
  `documentName` varchar(250) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `CandidateLanguage`
--

CREATE TABLE `CandidateLanguage` (
  `candidateLanguageId` int(11) NOT NULL,
  `candidatePersonalId` int(11) NOT NULL,
  `languageId` int(11) NOT NULL,
  `langRead` varchar(2) DEFAULT NULL,
  `langWrite` varchar(2) DEFAULT NULL,
  `langSpeak` varchar(2) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `CandidateNominee`
--

CREATE TABLE `CandidateNominee` (
  `candidateNomineeid` int(11) NOT NULL,
  `candidateId` int(11) NOT NULL,
  `familyId` int(11) NOT NULL,
  `staturyHeadId` varchar(2) DEFAULT NULL,
  `staturyHeadName` varchar(250) DEFAULT NULL,
  `activeStatus` varchar(2) DEFAULT NULL,
  `userId` int(11) DEFAULT NULL,
  `dateCreated` datetime NOT NULL,
  `userIdUpdate` int(11) DEFAULT NULL,
  `dateUpdate` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `CandidateOfficialInformation`
--

CREATE TABLE `CandidateOfficialInformation` (
  `candidateOfficialId` int(11) NOT NULL,
  `candidateId` int(11) NOT NULL,
  `companyId` int(11) NOT NULL,
  `uanNumber` varchar(50) DEFAULT NULL,
  `pfNumber` varchar(50) DEFAULT NULL,
  `pfEnrollDate` datetime DEFAULT NULL,
  `isPfApplicable` varchar(1) DEFAULT NULL,
  `esiNumber` varchar(50) DEFAULT NULL,
  `esiEnrollDate` datetime DEFAULT NULL,
  `isEsicApplicable` varchar(1) DEFAULT NULL,
  `medicalInsurance` varchar(50) DEFAULT NULL,
  `miFromDate` datetime DEFAULT NULL,
  `miToDate` datetime DEFAULT NULL,
  `isMiApplicable` varchar(1) DEFAULT NULL,
  `accidentalInsurance` varchar(50) DEFAULT NULL,
  `aiFromDate` datetime DEFAULT NULL,
  `aiToDate` datetime DEFAULT NULL,
  `isAiApplicable` varchar(1) DEFAULT NULL,
  `gradeId` int(11) DEFAULT NULL,
  `probationDays` int(11) DEFAULT NULL,
  `noticePeriod` int(11) DEFAULT NULL,
  `dateCreated` datetime NOT NULL,
  `dateUpdate` datetime DEFAULT NULL,
  `userId` int(11) NOT NULL,
  `userIdUpdate` int(11) NOT NULL,
  `employeeCode` varchar(11) DEFAULT NULL,
  `employeeCodeStatus` varchar(11) DEFAULT NULL,
  `pfExitDate` date DEFAULT NULL,
  `esiExitDate` date DEFAULT NULL,
  `biometricId` varchar(25) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `CandidatePersonal`
--

CREATE TABLE `CandidatePersonal` (
  `candidatePersonalId` int(11) NOT NULL,
  `candidateId` int(11) NOT NULL,
  `presentAddressId` int(11) DEFAULT NULL,
  `permanentAddressId` int(11) DEFAULT NULL,
  `referenceAddressId` int(11) DEFAULT NULL,
  `dateOfBirth` date DEFAULT NULL,
  `gender` varchar(2) NOT NULL,
  `bloodGroup` varchar(2) DEFAULT NULL,
  `alternateNumber` varchar(16) DEFAULT NULL,
  `maritalStatus` varchar(2) NOT NULL,
  `anniversaryDate` date DEFAULT NULL,
  `referenceName` varchar(125) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `CandidateProfessionalInformation`
--

CREATE TABLE `CandidateProfessionalInformation` (
  `candidateProfessionalInformationId` int(11) NOT NULL,
  `candidateId` int(11) NOT NULL,
  `organizationName` varchar(50) DEFAULT NULL,
  `dateFrom` date DEFAULT NULL,
  `dateTo` date DEFAULT NULL,
  `designation` varchar(50) DEFAULT NULL,
  `reportingTo` varchar(50) DEFAULT NULL,
  `reportingContact` varchar(50) DEFAULT NULL,
  `annualSalary` decimal(12,0) DEFAULT NULL,
  `reasonForChange` varchar(50) DEFAULT NULL,
  `companyId` int(11) DEFAULT NULL,
  `userId` int(11) DEFAULT NULL,
  `dateCreated` datetime DEFAULT NULL,
  `userIdUpdate` int(11) DEFAULT NULL,
  `dateUpdate` datetime DEFAULT NULL,
  `professionalDoc` varchar(250) DEFAULT NULL,
  `documentName` varchar(250) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `CandidateSkills`
--

CREATE TABLE `CandidateSkills` (
  `candidateSkillsId` int(11) NOT NULL,
  `candidateId` int(11) NOT NULL,
  `skillId` int(11) NOT NULL,
  `activeStatus` varchar(2) DEFAULT 'A',
  `userId` int(11) NOT NULL,
  `dateCreated` datetime DEFAULT NULL,
  `userIdUpdate` int(11) DEFAULT NULL,
  `dateUpdate` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `CandidateStatuary`
--

CREATE TABLE `CandidateStatuary` (
  `candidateStatuaryId` int(11) NOT NULL,
  `candidateId` int(11) NOT NULL,
  `bankId` varchar(50) NOT NULL,
  `oldUan` varchar(50) DEFAULT NULL,
  `oldEsi` varchar(50) DEFAULT NULL,
  `panNumber` varchar(50) DEFAULT NULL,
  `branch` text,
  `ifscCode` varchar(50) NOT NULL,
  `accountNumber` varchar(50) NOT NULL,
  `userId` int(11) DEFAULT NULL,
  `userIdUpdate` int(11) DEFAULT NULL,
  `activeStatus` varchar(2) NOT NULL,
  `dateCreated` datetime NOT NULL,
  `dateUpdated` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `category`
--

CREATE TABLE `category` (
  `category_id` int(11) NOT NULL,
  `name` varchar(20) NOT NULL,
  `parent` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `City`
--

CREATE TABLE `City` (
  `cityId` int(11) NOT NULL,
  `countryId` int(11) NOT NULL,
  `stateId` int(11) NOT NULL,
  `cityName` varchar(50) NOT NULL,
  `allowModi` char(1) DEFAULT 'N',
  `userId` int(11) NOT NULL,
  `dateCreated` datetime DEFAULT NULL,
  `userIdUpdate` int(11) DEFAULT NULL,
  `dateUpdate` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `City`
--

INSERT INTO `City` (`cityId`, `countryId`, `stateId`, `cityName`, `allowModi`, `userId`, `dateCreated`, `userIdUpdate`, `dateUpdate`) VALUES
(1, 1, 1, 'Bhopal', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2, 1, 1, 'Indore', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(4, 1, 1, 'Jabalpur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(6, 1, 1, 'Gwalior', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(72, 1, 17, 'Bilaspur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(79, 1, 17, 'Raipur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(115, 1, 23, 'Nagpur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(123, 1, 23, 'Pune', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(141, 1, 27, 'Bhubneshwar', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(171, 1, 32, 'Kolkata', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(177, 1, 4, 'New Delhi', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(178, 1, 10, 'Saran', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(179, 1, 10, 'Patna', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(180, 1, 10, 'Bhagalpur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(181, 1, 10, 'Motihari', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(182, 1, 10, 'Kaimur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(183, 1, 10, 'Arrah', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(184, 1, 10, 'Siwan', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(185, 1, 10, 'Madhubani', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(186, 1, 10, 'Muzaffarpur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(187, 1, 10, 'Samastipur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(188, 1, 10, 'Sasara', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(189, 1, 10, 'Sitamarhi', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(190, 1, 10, 'Dighalbank', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(191, 1, 10, 'Kishanganj', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(192, 1, 10, 'Biharsharif', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(193, 1, 10, 'Amarpur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(194, 1, 10, 'Gaya', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(195, 1, 10, 'Banka', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(196, 1, 10, ' Katihar', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(197, 1, 10, 'Purnia', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(198, 1, 10, 'Betia', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(199, 1, 10, 'Darbhanga', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(200, 1, 10, 'Hazipur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(201, 1, 10, 'Aurangabad', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(202, 1, 10, 'Araria', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(203, 1, 10, 'Areraj', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(204, 1, 10, 'Jahanabad', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(205, 1, 10, 'Asarganj', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(206, 1, 10, 'Saharsa', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(207, 1, 10, 'Begusarai', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(208, 1, 10, 'Bagaha', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(209, 1, 10, 'Bahadurganj', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(210, 1, 10, 'Sheikhpura', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(211, 1, 10, 'Bakhtiarpur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(212, 1, 10, 'Khagaria', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(213, 1, 10, 'Munger', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(214, 1, 10, 'Barahiya', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(215, 1, 10, 'Barh', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(216, 1, 10, 'Gopalganj', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(217, 1, 10, 'Buxar ', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(218, 1, 10, 'Madhepura', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(219, 1, 10, 'Behea', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(220, 1, 10, 'Belsand', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(221, 1, 10, 'Bettiah', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(222, 1, 10, 'Bhabua', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(223, 1, 10, 'Bikramganj', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(224, 1, 10, 'Birpur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(225, 1, 10, 'Bodh Gaya', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(226, 1, 10, 'Adapur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(227, 1, 10, 'Amawan', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(228, 1, 10, 'Jamui', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(229, 1, 10, 'Chanpatia', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(230, 1, 10, 'Chapra', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(231, 1, 10, 'Colgong', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(232, 1, 10, 'Daudnagar', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(233, 1, 10, 'Dhaka', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(234, 1, 10, 'Nawada', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(235, 1, 10, 'Dighwara', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(236, 1, 10, 'Forbesganj', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(237, 1, 10, 'Ghoghardiha', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(238, 1, 10, 'Hilsa', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(239, 1, 10, 'Hajipur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(240, 1, 10, 'Durgawati', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(241, 1, 10, 'Islampur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(242, 1, 10, 'Jagdishpur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(243, 1, 10, 'Jainagar', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(244, 1, 10, 'Jamalpur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(245, 1, 10, 'Jhajha', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(246, 1, 10, 'Jhanjharpur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(247, 1, 10, 'Jogbani', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(248, 1, 10, 'Afzalpur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(249, 1, 10, 'Kanti', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(250, 1, 10, 'Abgila', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(251, 1, 10, 'Karpi', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(252, 1, 10, 'Kasba', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(253, 1, 10, 'Khagaul', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(254, 1, 10, 'Khusrupur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(255, 1, 10, 'Lakhisarai', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(256, 1, 10, 'Koath', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(257, 1, 10, 'Koilwar', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(258, 1, 10, 'Ahmadpur Harna', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(259, 1, 10, 'Ghoshi', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(260, 1, 10, 'Lalganj', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(261, 1, 10, 'Maharajganj', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(262, 1, 10, 'Mairwa', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(263, 1, 10, 'Makhdumpur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(264, 1, 10, 'Maner', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(265, 1, 10, 'Manihari', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(266, 1, 10, 'Masaurhi', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(267, 1, 10, 'Mirganj', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(268, 1, 10, 'Mohiuddinagar', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(269, 1, 10, 'Motipur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(270, 1, 10, 'Murliganj', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(271, 1, 10, 'Nabinagar', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(272, 1, 10, 'Narkatiaganj', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(273, 1, 10, 'Bhagwanpur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(274, 1, 10, 'Bahadurpur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(275, 1, 10, 'Nirmali', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(276, 1, 10, 'Supaul ', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(277, 1, 10, 'Rafiganj', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(278, 1, 10, 'Raghunathpur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(279, 1, 10, 'Rajgir', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(280, 1, 10, 'Ahirawan', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(281, 1, 10, 'Akbarpur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(282, 1, 10, 'Revelganj', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(283, 1, 10, 'Sheohar ', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(284, 1, 10, 'Sherghati', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(285, 1, 10, 'Silao', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(286, 1, 10, 'Gidhaur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(287, 1, 27, 'Sonepur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(288, 1, 10, 'Sugauli', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(289, 1, 10, 'Sultanganj', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(290, 1, 10, 'Tekari', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(291, 1, 10, 'Baikunthpur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(292, 1, 10, 'Warisaliganj', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(293, 1, 17, 'Akaltara', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(294, 1, 17, 'Jagdalpur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(295, 1, 17, 'Rajnandgaon', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(296, 1, 17, 'Ambikapur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(297, 1, 17, 'Durg', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(298, 1, 17, 'Arang', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(299, 1, 17, 'Jashpur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(300, 1, 17, 'Baikunthpur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(301, 1, 17, 'Baloda', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(302, 1, 17, 'Raigarh', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(303, 1, 17, 'Bhanpuri', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(304, 1, 17, 'Bhatapara', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(305, 1, 17, 'Bhatgaon', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(306, 1, 17, 'Bilha', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(307, 1, 17, 'Birgaon', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(308, 1, 17, 'Janjgir - Champa', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(309, 1, 17, 'Champa', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(310, 1, 17, 'Kanker ', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(311, 1, 17, 'Dantewada', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(312, 1, 17, 'Dhamtari ', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(313, 1, 17, 'Dongargaon', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(314, 1, 17, 'Kawardha ', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(315, 1, 17, 'Geedam', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(316, 1, 17, 'Gharghoda', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(317, 1, 17, 'Korba ', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(318, 1, 17, 'Katghora', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(319, 1, 17, 'Khairagarh', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(320, 1, 17, 'Kharod', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(321, 1, 17, 'Kharsia', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(322, 1, 17, 'Kirandul', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(323, 1, 17, 'Kondagaon', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(324, 1, 17, 'Kurud', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(325, 1, 17, 'Mahasamund ', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(326, 1, 17, 'Manendragarh', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(327, 1, 17, 'Mungeli', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(328, 1, 17, 'Patan', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(329, 1, 17, 'Pendra', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(330, 1, 17, 'Ratanpur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(331, 1, 17, 'Sakti', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(332, 1, 17, 'Saraipali', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(333, 1, 17, 'Sarangarh', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(334, 1, 17, 'Simga', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(335, 1, 17, 'Surajpur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(336, 1, 17, 'Takhatpur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(337, 1, 13, 'Margao', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(338, 1, 13, 'Aldana', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(339, 1, 13, 'Panaji', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(340, 1, 13, 'Aquem', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(341, 1, 13, 'Calangute', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(342, 1, 13, 'Canacona', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(343, 1, 13, 'Candolim', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(344, 1, 13, 'Chicalim', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(345, 1, 13, 'Colvale', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(346, 1, 13, 'Cuncolim', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(347, 1, 13, 'Goa Velha', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(348, 1, 13, 'Navelim', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(349, 1, 13, 'Pale', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(350, 1, 13, 'Pernem', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(351, 1, 13, 'Ponda', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(352, 1, 13, 'Queula', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(353, 1, 13, 'Saligao', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(354, 1, 13, 'Sanquelim', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(355, 1, 13, 'Siolim', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(356, 1, 13, 'Valpoi', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(357, 1, 6, 'Valsad', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(358, 1, 6, 'Dohad', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(359, 1, 6, 'Abrama', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(360, 1, 6, 'Navsari  ', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(361, 1, 6, 'Bharuch', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(362, 1, 6, 'Godhara', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(363, 1, 6, 'Adalaj', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(364, 1, 6, 'Surendranagar', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(365, 1, 6, 'Anand', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(366, 1, 6, 'Bhuj ', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(367, 1, 6, 'Mahsana', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(368, 1, 6, 'Jamnagar', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(369, 1, 6, 'Junagadh', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(370, 1, 6, 'Palanpur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(371, 1, 6, 'Patan', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(372, 1, 6, 'Surat', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(373, 1, 6, 'Ahmedabad', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(374, 1, 6, 'The Dangs', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(375, 1, 6, 'Rajkot', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(376, 1, 6, 'Amreli', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(377, 1, 6, 'Vadodara', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(378, 1, 6, 'Himatnagar', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(379, 1, 6, 'Nadiad', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(380, 1, 6, 'Bhavnagar', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(381, 1, 6, 'Ambaji', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(382, 1, 6, 'Andada', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(383, 1, 6, 'Anjar', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(384, 1, 6, 'Gandhinagar', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(385, 1, 6, 'Atul', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(386, 1, 6, 'Bantwa', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(387, 1, 6, 'Bavla', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(388, 1, 6, 'Bhachau', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(389, 1, 6, 'Rajpipla', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(390, 1, 6, 'Bhanvad', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(391, 1, 6, 'Bhayavadar', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(392, 1, 6, 'Bodeli', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(393, 1, 6, 'Boriavi', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(394, 1, 6, 'Borsad', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(395, 1, 6, 'Botad', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(396, 1, 6, 'Chaklasi', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(397, 1, 6, 'Chalala', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(398, 1, 6, 'Chalthan', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(399, 1, 6, 'Chanasma', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(400, 1, 6, 'Chhaya', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(401, 1, 6, 'Chikhli', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(402, 1, 6, 'Chorvad', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(403, 1, 6, 'Dabhoi', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(404, 1, 6, 'Dahegam', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(405, 1, 6, 'Dakor', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(406, 1, 6, 'Damnagar', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(407, 1, 6, 'Devsar', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(408, 1, 6, 'Dhandhuka', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(409, 1, 6, 'Dhanera', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(410, 1, 6, 'Dharampur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(411, 1, 6, 'Dhola', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(412, 1, 6, 'Dholka', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(413, 1, 6, 'Dhrangadhra', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(414, 1, 6, 'Dhrol', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(415, 1, 6, 'Digvijaygram', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(416, 1, 6, 'Dwarka', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(417, 1, 6, 'Gandevi', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(418, 1, 6, 'Godhra', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(419, 1, 6, 'Gondal', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(420, 1, 6, 'Halol', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(421, 1, 6, 'Harij', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(422, 1, 6, 'Idar', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(423, 1, 6, 'Jafrabad', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(424, 1, 6, 'Jalalpore', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(425, 1, 6, 'Jam Jodhpur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(426, 1, 6, 'Jasdan', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(427, 1, 6, 'Kadi', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(428, 1, 6, 'Kadodara', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(429, 1, 6, 'Kalavad', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(430, 1, 6, 'Kalol', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(431, 1, 6, 'Kanodar', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(432, 1, 6, 'Karamsad', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(433, 1, 6, 'Keshod', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(434, 1, 6, 'Khambhalia', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(435, 1, 6, 'Khambhat', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(436, 1, 6, 'Kharaghoda', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(437, 1, 6, 'Kheda', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(438, 1, 6, 'Khedbrahma', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(439, 1, 6, 'Kheralu', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(440, 1, 6, 'Porbandar', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(441, 1, 6, 'Kutiyana', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(442, 1, 6, 'Lathi', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(443, 1, 6, 'Limbdi', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(444, 1, 6, 'Lunawada', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(445, 1, 6, 'Mahudha', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(446, 1, 6, 'Mahuva', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(447, 1, 6, 'Malpur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(448, 1, 6, 'Manavadar', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(449, 1, 6, 'Mandvi', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(450, 1, 6, 'Mangrol', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(451, 1, 6, 'Meghraj', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(452, 1, 6, 'Mithapur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(453, 1, 6, 'Modasa', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(454, 1, 6, 'Mundra', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(455, 1, 6, 'Nandej', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(456, 1, 6, 'Ode', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(457, 1, 6, 'Paddhari', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(458, 1, 6, 'Padra', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(459, 1, 6, 'Palej', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(460, 1, 6, 'Palitana', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(461, 1, 6, 'Petlad', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(462, 1, 6, 'Prantij', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(463, 1, 6, 'Radhanpur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(464, 1, 6, 'Rajula', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(465, 1, 6, 'Ramol', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(466, 1, 6, 'Ranavav', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(467, 1, 6, 'Ranip', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(468, 1, 6, 'Ranoli', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(469, 1, 6, 'Sachin', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(470, 1, 6, 'Salaya', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(471, 1, 6, 'Sanand', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(472, 1, 6, 'Santrampur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(473, 1, 6, 'Savarkundla', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(474, 1, 6, 'Sayan', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(475, 1, 6, 'Sidhpur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(476, 1, 6, 'Sihor', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(477, 1, 6, 'Songadh', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(478, 1, 6, 'Talaja', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(479, 1, 6, 'Talod', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(480, 1, 6, 'Thangadh', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(481, 1, 6, 'Tharad', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(482, 1, 6, 'Umreth', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(483, 1, 6, 'Udvada', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(484, 1, 6, 'Unjha', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(485, 1, 6, 'Upleta', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(486, 1, 6, 'Vadia', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(487, 1, 6, 'Vapi', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(488, 1, 6, 'Vartej', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(489, 1, 6, 'Vastral', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(490, 1, 6, 'Vejalpur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(491, 1, 6, 'Veraval', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(492, 1, 6, 'Vijapur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(493, 1, 6, 'Viramgam', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(494, 1, 6, 'Visavadar', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(495, 1, 6, 'Visnagar', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(496, 1, 6, 'Vyara', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(497, 1, 6, 'Wankaner', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(498, 1, 1, 'Damoh', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(499, 1, 1, 'Seoni', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(500, 1, 1, 'Shajapur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(501, 1, 1, 'Sehore', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(502, 1, 1, 'Ajaigarh', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(503, 1, 1, 'Akoda', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(504, 1, 1, 'Ratlam', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(505, 1, 1, 'Alirajpur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(506, 1, 1, 'Amanganj', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(507, 1, 1, 'Shahdol', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(508, 1, 1, 'Satna', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(509, 1, 1, 'Amarwara', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(510, 1, 1, 'Bhind', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(511, 1, 1, 'Ambah', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(512, 1, 1, 'Umaria', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(513, 1, 1, 'Narasinghpur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(514, 1, 1, 'Dhar', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(515, 1, 1, 'Amla', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(516, 1, 1, 'Betul', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(517, 1, 1, 'Rewa', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(518, 1, 1, 'Vidisha', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(519, 1, 1, 'Mandla', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(520, 1, 1, 'Khargone', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(521, 1, 1, 'Raisen', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(522, 1, 1, 'Guna', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(523, 1, 1, 'Khandwa', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(524, 1, 1, 'Ashok Nagar', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(525, 1, 1, 'Neemuch', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(526, 1, 1, 'Tikamgarh', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(527, 1, 1, 'Shivpuri', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(528, 1, 1, 'Badnawar', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(529, 1, 1, 'Bagh', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(530, 1, 1, 'Bagli', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(531, 1, 1, 'Katni', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(532, 1, 1, 'Baihar', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(533, 1, 1, 'Baikunthpur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(534, 1, 1, ' Balaghat', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(535, 1, 1, 'Ujjain', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(536, 1, 1, 'Chhatarpur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(537, 1, 1, 'Jhabua', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(538, 1, 1, 'Sagar', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(539, 1, 1, 'Hoshangabad', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(540, 1, 1, 'Bansatar Kheda', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(541, 1, 1, 'Panna', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(542, 1, 1, 'Bareli', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(543, 1, 1, 'Barhi', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(544, 1, 1, 'Datia', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(545, 1, 1, 'Dewas', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(546, 1, 1, 'Barwani', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(547, 1, 1, 'Betma', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(548, 1, 1, 'Bhander', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(549, 1, 1, 'Bharveli', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(550, 1, 1, 'Bhedaghat', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(551, 1, 1, 'Mandasur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(552, 1, 1, 'Bhitarwar', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(553, 1, 1, 'Biaora', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(554, 1, 1, 'Chhindwara', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(555, 1, 1, 'Boda', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(556, 1, 1, 'Burhanpur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(557, 1, 1, 'Buxwaha', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(558, 1, 1, 'Chandla', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(559, 1, 1, 'Chichli', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(560, 1, 1, 'Sidhi', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(561, 1, 1, 'Daboh', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(562, 1, 1, 'Dabra', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(563, 1, 1, 'Depalpur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(564, 1, 1, 'Dhamnod', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(565, 1, 1, 'Dharampuri', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(566, 1, 1, 'Gadarwara', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(567, 1, 1, 'Gairatganj', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(568, 1, 1, 'Gohad', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(569, 1, 1, 'Gormi', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(570, 1, 1, 'Govindgarh', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(571, 1, 1, 'Gurh', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(572, 1, 1, 'Harda', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(573, 1, 1, 'Harpalpur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(574, 1, 1, 'Harsud', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(575, 1, 1, 'Hatta', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(576, 1, 1, 'Hindoria', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(577, 1, 1, 'Itarsi', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(578, 1, 1, 'Jobat', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(579, 1, 1, 'Joura', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(580, 1, 1, 'Kannod', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(581, 1, 1, 'Kareli', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(582, 1, 1, 'Karnawad', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(583, 1, 1, 'Katangi', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(584, 1, 1, 'Khategaon', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(585, 1, 1, 'Khetia', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(586, 1, 1, 'Kukshi', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(587, 1, 1, 'Lahar', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(588, 1, 1, 'Laundi', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(589, 1, 1, 'Maharajpur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(590, 1, 1, 'Majholi', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(591, 1, 1, 'Manasa', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(592, 1, 1, 'Manawar', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(593, 1, 1, 'Mandideep', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(594, 1, 1, 'Mandleshwar', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(595, 1, 1, 'Mangawan', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(596, 1, 1, 'Manpur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(597, 1, 1, 'Mau', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(598, 1, 1, 'Mauganj', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(599, 1, 1, 'Meghnagar', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(600, 1, 1, 'Mehgaon', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(601, 1, 1, 'Mihona', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(602, 1, 1, 'Mohgaon', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(603, 1, 1, 'Morena', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(604, 1, 1, 'Multai', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(605, 1, 1, 'Mundi', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(606, 1, 1, 'Mungaoli', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(607, 1, 1, 'Namli', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(608, 1, 1, 'Narayangarh', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(609, 1, 1, 'Narsinghgarh', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(610, 1, 1, 'Nepanagar', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(611, 1, 1, 'Pachmarhi Cantt', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(612, 1, 1, 'Pachore', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(613, 1, 1, 'Pandhana', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(614, 1, 1, 'Pansemal', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(615, 1, 1, 'Patan', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(616, 1, 1, 'Patharia', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(617, 1, 1, 'Porsa', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(618, 1, 1, ' Raigarh', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(619, 1, 1, 'Rajgarh', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(620, 1, 1, 'Rajnagar', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(621, 1, 1, 'Rajpur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(622, 1, 1, 'Ranapur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(623, 1, 1, 'Ratangarh', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(624, 1, 1, 'Sabalgarh', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(625, 1, 1, 'Sailana', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(626, 1, 1, 'Sanchi', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(627, 1, 1, 'Sarangpur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(628, 1, 1, 'Sardarpur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(629, 1, 1, 'Sarni', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(630, 1, 1, 'Satwas', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(631, 1, 1, 'Sausar', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(632, 1, 1, 'Sawer', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(633, 1, 1, 'Sendhwa', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(634, 1, 1, 'Seoni Malwa', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(635, 1, 1, 'Shahpura', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(636, 1, 1, 'Dindori', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(637, 1, 1, 'Sihora', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(638, 1, 1, 'Sohagpur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(639, 1, 1, 'Sultanpur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(640, 1, 1, 'Tal', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(641, 1, 1, 'Tekanpur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(642, 1, 1, 'Thandla', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(643, 1, 1, 'Tirodi', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(644, 1, 1, 'Udaipura', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(645, 1, 1, 'Ukwa', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(646, 1, 23, 'Mumbai', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(647, 1, 23, 'Amraoti', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(648, 1, 23, 'Ahmednagar', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(649, 1, 23, 'Satara', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(650, 1, 23, 'Nashik', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(651, 1, 23, 'Ratnagiri', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(652, 1, 23, 'Chandrapur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(653, 1, 23, 'Akola', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(654, 1, 23, 'Jalgaon', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(655, 1, 23, 'Sangli', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(656, 1, 23, 'Bhandara', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(657, 1, 23, 'Sholapur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(658, 1, 23, 'Thane', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(659, 1, 23, 'Osmanabad', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(660, 1, 23, 'Aurangabad', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(661, 1, 23, 'Oras', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(662, 1, 23, 'Ajra', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(663, 1, 23, 'Parbhani', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(664, 1, 23, 'Dhule', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(665, 1, 23, 'Akkalkot', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(666, 1, 23, 'Akot', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(667, 1, 23, 'Alibag', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(668, 1, 23, 'Gadchiroli', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(669, 1, 23, 'Wardha', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(670, 1, 23, 'Amalner', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(671, 1, 23, 'Beed', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(672, 1, 23, 'Ambad', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(673, 1, 23, 'Ambejogai', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(674, 1, 23, 'Kolhapur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(675, 1, 23, 'Buldhana', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(676, 1, 23, 'Ashti', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(677, 1, 23, 'Deoni', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(678, 1, 23, 'Anjangaon', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(679, 1, 23, 'Nanded', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(680, 1, 23, 'Yeotamal', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(681, 1, 23, 'Arvi', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(682, 1, 23, 'Dhamangaon Railway', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(683, 1, 23, 'Latur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(684, 1, 23, 'Badlapur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(685, 1, 23, 'Balapur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(686, 1, 23, 'Ballarpur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(687, 1, 23, 'Baramati', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(688, 1, 23, 'Barshi', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(689, 1, 23, 'Bhadravati', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(690, 1, 23, 'Bhagur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(691, 1, 23, 'Bhiwandi', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(692, 1, 23, 'Bhokardan', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(693, 1, 23, 'Bhor', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(694, 1, 23, 'Budhgaon', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(695, 1, 23, 'Chakan', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(696, 1, 23, 'Chikhaldara', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(697, 1, 23, 'Chinchani', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(698, 1, 23, 'Dahanu', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(699, 1, 23, 'Solapur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(700, 1, 23, 'Darwha', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(701, 1, 23, 'Daund', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(702, 1, 23, 'Deolali', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(703, 1, 23, 'Deoli', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(704, 1, 23, 'Chalisgaon', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(705, 1, 23, 'Dharangaon', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(706, 1, 23, 'Dharmabad', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(707, 1, 23, 'Digras', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(708, 1, 23, 'Dudhani', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(709, 1, 23, 'Erandol', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(710, 1, 23, 'Faizpur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(711, 1, 23, 'Desaiganj (Vadasa)', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(712, 1, 23, 'Gangakhed', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(713, 1, 23, 'Gangapur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(714, 1, 23, 'Gokhivare', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(715, 1, 23, 'Goregaon', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(716, 1, 23, 'Guhagar', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(717, 1, 23, 'Hadgaon', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(718, 1, 23, 'Pen', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(719, 1, 23, 'Jamkhed', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(720, 1, 23, 'Hingoli', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(721, 1, 23, 'Hinganghat', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(722, 1, 23, 'Ichalkaranji', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(723, 1, 23, 'Igatpuri', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(724, 1, 23, 'Indapur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(725, 1, 23, 'Chandvad', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(726, 1, 23, 'Jawhar', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(727, 1, 23, 'Jaysingpur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(728, 1, 23, 'Jejuri', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(729, 1, 23, 'Jintur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(730, 1, 23, 'Junnar', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(731, 1, 23, 'Kagal', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(732, 1, 23, 'Kalamb', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(733, 1, 23, 'Washim', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(734, 1, 23, 'Kankavli', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(735, 1, 23, 'Kannad', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(736, 1, 23, 'Karanja', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(737, 1, 23, 'Karjat', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(738, 1, 23, 'Karmala', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(739, 1, 23, 'Katol', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(740, 1, 23, 'Chopda', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(741, 1, 23, 'Khapa', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(742, 1, 23, 'Khed', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(743, 1, 23, 'Kherdi', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(744, 1, 23, 'Khopoli', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(745, 1, 23, 'Khuldabad', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(746, 1, 23, 'Kinwat', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(747, 1, 23, 'Kodoli', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(748, 1, 23, 'Kopargaon', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(749, 1, 23, 'Kudal', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(750, 1, 23, 'Kundalwadi', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(751, 1, 23, 'Kurundvad', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(752, 1, 23, 'Lanja', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(753, 1, 23, 'Lasalgaon', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(754, 1, 23, 'Loha', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(755, 1, 23, 'Lonar', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(756, 1, 23, 'Lonavala', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(757, 1, 23, 'Mahabaleshwar', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(758, 1, 23, 'Mahad', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(759, 1, 23, 'Ambegaon', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(760, 1, 23, 'Maindargi', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(761, 1, 23, 'Shahapur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(762, 1, 23, 'Malegaon', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(763, 1, 23, 'Malkapur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(764, 1, 23, 'Manchar', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(765, 1, 23, 'Manmad', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(766, 1, 23, 'Manor', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(767, 1, 23, 'Mansar', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(768, 1, 23, 'Manwath', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(769, 1, 23, 'Matheran', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(770, 1, 23, 'Mhasla', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(771, 1, 23, 'Mohpa', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(772, 1, 23, 'Morshi', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(773, 1, 23, 'Mowad', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(774, 1, 23, 'Mukhed', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(775, 1, 23, 'Mul', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(776, 1, 23, 'Murbad', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(777, 1, 23, 'Murgud', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(778, 1, 23, 'Murud', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(779, 1, 23, 'Nagothana', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(780, 1, 23, 'Naldurg', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(781, 1, 23, 'Nandgaon', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(782, 1, 23, 'Nandura', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(783, 1, 23, 'Nandurbar', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(784, 1, 23, 'Neral', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(785, 1, 23, 'Chamorshi', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(786, 1, 23, 'Ozar', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(787, 1, 23, 'Pachora', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(788, 1, 23, 'Padagha', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(789, 1, 23, 'Paithan', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(790, 1, 23, 'Pali', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(791, 1, 23, 'Panhala', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00');
INSERT INTO `City` (`cityId`, `countryId`, `stateId`, `cityName`, `allowModi`, `userId`, `dateCreated`, `userIdUpdate`, `dateUpdate`) VALUES
(792, 1, 23, 'Paranda', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(793, 1, 23, 'Parola', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(794, 1, 23, 'Partur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(795, 1, 23, 'Patan', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(796, 1, 23, 'Pathardi', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(797, 1, 23, 'Pathri', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(798, 1, 23, 'Patur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(799, 1, 23, 'Phaltan', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(800, 1, 23, 'Ghansawangi', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(801, 1, 23, 'Pimpri Chinchwad', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(802, 1, 23, 'Poladpur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(803, 1, 23, 'Kalwan', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(804, 1, 23, 'Pulgaon', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(805, 1, 23, 'Purna', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(806, 1, 23, 'Pusad', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(807, 1, 23, 'Rahimatpur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(808, 1, 23, 'Rahuri', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(809, 1, 23, 'Rajapur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(810, 1, 23, 'Basmath', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(811, 1, 23, 'Rajur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(812, 1, 23, 'Ramtek', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(813, 1, 23, 'Raver', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(814, 1, 23, 'Risod', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(815, 1, 23, 'Sailu', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(816, 1, 23, 'Sangamner', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(817, 1, 23, 'Sangole', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(818, 1, 23, 'Sasti', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(819, 1, 23, 'Satana', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(820, 1, 23, 'Savda', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(821, 1, 23, 'Savner', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(822, 1, 23, 'Shahade', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(823, 1, 23, 'Shirdi', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(824, 1, 23, 'Shirur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(825, 1, 23, 'Shivajinagar', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(826, 1, 23, 'Shrivardhan', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(827, 1, 23, 'Sillod', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(828, 1, 23, 'Sindi', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(829, 1, 23, 'Sindkhed Raja', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(830, 1, 23, 'Sinnar', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(831, 1, 23, 'Sonpeth', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(832, 1, 23, 'Surgana', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(833, 1, 23, 'Talode', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(834, 1, 23, 'Jalna', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(835, 1, 23, 'Tarapur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(836, 1, 23, 'Tasgaon', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(837, 1, 23, 'Telhara', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(838, 1, 23, 'Tirora', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(839, 1, 23, 'Trimbak', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(840, 1, 23, 'Tuljapur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(841, 1, 23, 'Tumsar', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(842, 1, 23, 'Udgir', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(843, 1, 23, 'Ulhasnagar', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(844, 1, 23, 'Umred', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(845, 1, 23, 'Uran', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(846, 1, 23, 'Uran Islampur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(847, 1, 23, 'Vada', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(848, 1, 23, 'Vadgaon', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(849, 1, 23, 'Vaijapur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(850, 1, 23, 'Vengurla', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(851, 1, 23, 'Miraj', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(852, 1, 23, 'Malshiras', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(853, 1, 23, 'Virar', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(854, 1, 23, 'Wani', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(855, 1, 23, 'Wadi', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(856, 1, 23, 'Wai', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(857, 1, 23, 'Warud', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(858, 1, 27, 'Puri', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(859, 1, 27, 'Baleshwar', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(860, 1, 27, 'Cuttack', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(861, 1, 27, 'Bolangir', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(862, 1, 27, 'Sambalpur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(863, 1, 27, 'Baripara', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(864, 1, 27, 'Koraput', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(865, 1, 27, 'Naupada', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(866, 1, 27, 'Kalahandi', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(867, 1, 27, 'Jagatsinghapur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(868, 1, 27, 'Dhenkanal', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(869, 1, 27, 'Anandapur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(870, 1, 27, 'Bargarh', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(871, 1, 27, 'Chatrapur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(872, 1, 27, 'Athmallik', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(873, 1, 27, 'Kendrapara', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(874, 1, 27, 'Anugul', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(875, 1, 27, 'Baudh', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(876, 1, 27, 'Debagarh ', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(877, 1, 27, 'Balangir', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(878, 1, 27, 'Kordha', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(879, 1, 27, 'Balimela', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(880, 1, 27, 'Kendujhar', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(881, 1, 27, 'Sundargarh', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(882, 1, 27, 'Kandhamal', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(883, 1, 27, 'Balugaon', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(884, 1, 27, 'Banki', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(885, 1, 27, 'Nayagarh', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(886, 1, 27, 'Barbil', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(887, 1, 27, 'Phulbani', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(888, 1, 27, 'Basudebpur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(889, 1, 27, 'Belpahar', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(890, 1, 27, 'Bhawanipatna', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(891, 1, 27, 'Bhuban', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(892, 1, 27, 'Panikoii', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(893, 1, 27, 'Parlakhemundi', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(894, 1, 27, 'Buguda', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(895, 1, 27, 'Burla', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(896, 1, 27, 'Champua', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(897, 1, 27, 'Rayagada', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(898, 1, 27, 'Damanjodi', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(899, 1, 27, 'Bhadrak', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(900, 1, 27, 'Digapahandi', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(901, 1, 27, 'Sonapur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(902, 1, 27, 'Ganjam', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(903, 1, 27, 'Gopalpur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(904, 1, 27, 'Gudari', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(905, 1, 27, 'Gunupur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(906, 1, 27, 'Hinjilicut', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(907, 1, 27, 'Hirakud', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(908, 1, 27, 'Jalda', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(909, 1, 27, 'Jaleswar', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(910, 1, 27, 'Jatani', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(911, 1, 27, 'Jharsuguda', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(912, 1, 27, 'Jhumpura', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(913, 1, 27, 'Joda', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(914, 1, 27, 'Junagarh', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(915, 1, 27, 'Kantabanji', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(916, 1, 27, 'Kantilo', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(917, 1, 27, 'Karanjia', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(918, 1, 27, 'Kesinga', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(919, 1, 27, 'Khariar', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(920, 1, 27, 'Kodala', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(921, 1, 27, 'Nabarangapur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(922, 1, 27, 'Konark', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(923, 1, 27, 'Kotpad', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(924, 1, 27, 'Malkangiri', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(925, 1, 27, 'Mukhiguda', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(926, 1, 27, 'Nuapatna', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(927, 1, 27, 'Padmapur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(928, 1, 27, 'Panposh', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(929, 1, 27, 'Patnagarh', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(930, 1, 27, 'Pattamundai', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(931, 1, 27, 'Purusottampur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(932, 1, 27, 'Rairangpur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(933, 1, 27, 'Rambha', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(934, 1, 27, 'Remuna', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(935, 1, 27, 'Soro', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(936, 1, 27, 'Tensa', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(937, 1, 27, 'Udala', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(938, 1, 2, 'Agra', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(939, 1, 2, 'Bareilly', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(940, 1, 2, 'Varanasi', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(941, 1, 2, 'Lucknow', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(942, 1, 2, 'Mirzapur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(943, 1, 2, 'Jhansi', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(944, 1, 2, 'Fatehpur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(945, 1, 2, 'Meerut', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(946, 1, 2, 'Etwah', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(947, 1, 2, 'Gonda', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(948, 1, 51, 'Pratapgarh', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(949, 1, 2, 'Afzalgarh', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(950, 1, 2, 'Moradabad', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(951, 1, 2, 'Kanpur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(952, 1, 2, 'Allahabad', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(953, 1, 2, 'Bulandshahr', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(954, 1, 2, 'Aligarh', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(955, 1, 2, 'Hardoi', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(956, 1, 2, 'Shahjahanpur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(957, 1, 2, 'Jaunpur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(958, 1, 2, 'Azamgarh', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(959, 1, 2, 'Ahraura', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(960, 1, 2, 'Raibareilly', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(961, 1, 2, 'Ailum', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(962, 1, 2, 'Kheri', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(963, 1, 2, 'Jalaun', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(964, 1, 2, 'Unnao', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(965, 1, 2, 'Mainpuri', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(966, 1, 2, 'Akbarpur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(967, 1, 2, 'Sultanpur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(968, 1, 2, 'Gorakhpur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(969, 1, 2, 'Sitapur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(970, 1, 2, 'Barabanki', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(971, 1, 2, 'Aliganj', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(972, 1, 2, 'Etah', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(973, 1, 2, 'Farukhabad', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(974, 1, 2, 'Muzaffarnagar', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(975, 1, 2, 'Badaun', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(976, 1, 2, 'Faizabad', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(977, 1, 2, 'Amanpur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(978, 1, 2, 'Pilibhit', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(979, 1, 2, 'Ghazipur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(980, 1, 2, 'Sonbhadra', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(981, 1, 2, 'Ambehta', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(982, 1, 2, 'Amethi', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(983, 1, 2, 'Amila', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(984, 1, 2, 'Amilo', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(985, 1, 2, 'Aminagar Sarai', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(986, 1, 2, 'Basti', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(987, 1, 2, 'Amraudha', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(988, 1, 2, 'Ghaziabad', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(989, 1, 2, 'Amroha', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(990, 1, 2, 'Antu', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(991, 1, 2, 'Anupshahr', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(992, 1, 2, 'Aonla', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(993, 1, 2, 'Mathura', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(994, 1, 2, 'Armapur Estate', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(995, 1, 2, 'Bijnore', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(996, 1, 2, 'Ballia', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(997, 1, 2, 'Atrauli', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(998, 1, 2, 'Atraulia', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(999, 1, 1, 'Banda', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1000, 1, 2, 'Auraiya', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1001, 1, 2, 'Aurangabad', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1002, 1, 2, 'Awagarh', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1003, 1, 2, 'Ayodhya', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1004, 1, 2, 'Baberu', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1005, 1, 2, 'Babina', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1006, 1, 2, 'Babrala', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1007, 1, 2, 'Babugarh', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1008, 1, 2, 'Chandauli', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1009, 1, 2, 'Bachhrawan', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1010, 1, 2, 'Saharanpur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1011, 1, 2, 'Baghpat', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1012, 1, 2, 'Bah', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1013, 1, 2, 'Bahadurganj', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1014, 1, 2, 'Baheri', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1015, 1, 2, 'Bahjoi', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1016, 1, 2, 'Bahraich', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1017, 1, 2, 'Deoria', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1018, 1, 2, 'Bajna', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1019, 1, 2, 'Bakewar', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1020, 1, 2, 'Baldeo', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1021, 1, 17, 'Balrampur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1022, 1, 2, 'Hathras', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1023, 1, 2, 'Bangarmau', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1024, 1, 2, 'Banki', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1025, 1, 2, 'Lalitpur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1026, 1, 2, 'Bansdih', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1027, 1, 2, 'Bansgaon', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1028, 1, 2, 'Bansi', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1029, 1, 2, 'Baragaon', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1030, 1, 2, 'Kannauj', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1031, 1, 2, 'Baraut', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1032, 1, 2, 'Barhalganj', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1033, 1, 2, 'Barkhera', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1034, 1, 2, 'Barsana', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1035, 1, 2, 'Hamirpur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1036, 1, 2, 'Beniganj', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1037, 1, 2, 'Bewar', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1038, 1, 2, 'Bhadohi', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1039, 1, 2, 'Bhagwant Nagar', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1040, 1, 2, 'Bharatganj', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1041, 1, 2, 'Bharthana', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1042, 1, 2, 'Bharwari', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1043, 1, 2, 'Bhinga', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1044, 1, 2, 'Bhogaon', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1045, 1, 2, 'Bidhuna', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1046, 1, 2, 'Bijnor', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1047, 1, 2, 'Bikapur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1048, 1, 2, 'Bilari', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1049, 1, 2, 'Bilaspur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1050, 1, 2, 'Bilgram', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1051, 1, 2, 'Bilsanda', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1052, 1, 2, 'Bilsi', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1053, 1, 2, 'Bindki', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1054, 1, 51, 'Bisalpur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1055, 1, 2, 'Bisauli', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1056, 1, 2, 'Biswan', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1057, 1, 2, 'Bithoor', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1058, 1, 2, 'Kushinagar', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1059, 1, 2, 'Budaun', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1060, 1, 2, 'Budhana', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1061, 1, 2, 'Bugrasi', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1062, 1, 2, 'Rampur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1063, 1, 2, 'Chail', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1064, 1, 2, 'Chakia', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1065, 1, 2, 'Chandausi', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1066, 1, 2, 'Chandpur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1067, 1, 2, 'Kaushambi', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1068, 1, 2, 'Chhaprauli', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1069, 1, 2, 'Chhibramau', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1070, 1, 2, 'Chopan', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1071, 1, 2, 'Chunar', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1072, 1, 2, 'Colonelganj', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1073, 1, 2, 'Dadri', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1074, 1, 2, 'Dalmau', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1075, 1, 2, 'Dankaur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1076, 1, 2, 'Dataganj', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1077, 1, 2, 'Daurala', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1078, 1, 2, 'Dayalbagh', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1079, 1, 2, 'Deoband', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1080, 1, 2, 'Dewa', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1081, 1, 2, 'Ambedkar Nagar', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1082, 1, 2, 'Dhampur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1083, 1, 2, 'Mahrajganj', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1084, 1, 2, 'Dibai', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1085, 1, 2, 'Siddharthnagar', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1086, 1, 2, 'Dostpur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1087, 1, 2, 'Dudhi', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1088, 1, 2, 'Ekdil', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1089, 1, 2, 'Erich', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1090, 1, 2, 'Gautam Buddha Nagar', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1091, 1, 2, 'Etawah', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1092, 1, 2, 'Faridpur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1093, 1, 2, 'Fatehabad', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1094, 1, 2, 'Fatehgarh', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1095, 1, 2, 'Fatehpur Sikri', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1096, 1, 2, 'Firozabad', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1097, 1, 2, 'Gajraula', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1098, 1, 2, 'Gangaghat', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1099, 1, 2, 'Gangapur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1100, 1, 2, 'Gangoh', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1101, 1, 2, 'Garautha', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1102, 1, 2, 'Gauri Bazar', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1103, 1, 2, 'Gausganj', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1104, 1, 2, 'Ghatampur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1105, 1, 2, 'Ghorawal', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1106, 1, 2, 'Ghosi', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1107, 1, 2, 'Gokul', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1108, 1, 2, 'Gola Bazar', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1109, 1, 2, 'Gopamau', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1110, 1, 2, 'Gopiganj', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1111, 1, 2, 'Gunnaur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1112, 1, 2, 'Gursahaiganj', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1113, 1, 2, 'Gursarai', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1114, 1, 2, 'Gyanpur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1115, 1, 2, 'Haidergarh', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1116, 1, 2, 'Haldaur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1117, 1, 2, 'Handia', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1118, 1, 2, 'Hapur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1119, 1, 2, 'Harduaganj', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1120, 1, 2, 'Hariharpur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1121, 1, 2, 'Harraiya', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1122, 1, 2, 'Hasanpur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1123, 1, 2, 'Hasayan', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1124, 1, 2, 'Hastinapur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1125, 1, 2, 'Hata', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1126, 1, 2, 'Iglas', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1127, 1, 2, 'Ikauna', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1128, 1, 2, 'Mahoba', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1129, 1, 2, 'Jahangirabad', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1130, 1, 2, 'Jahangirpur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1131, 1, 2, 'Jais', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1132, 1, 2, 'Jalalabad', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1133, 1, 2, 'Jalalpur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1134, 1, 2, 'Jarwal', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1135, 1, 2, 'Jasrana', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1136, 1, 2, 'Jaswantnagar', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1137, 1, 2, 'Jewar', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1138, 1, 2, 'Jhalu', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1139, 1, 2, 'Jhinjhak', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1140, 1, 2, 'Jhinjhana', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1141, 1, 2, 'Jhusi', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1142, 1, 2, 'Joya', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1143, 1, 2, 'Kadipur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1144, 1, 2, 'Kairana', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1145, 1, 2, 'Kakrala', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1146, 1, 2, 'Kalpi', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1147, 1, 2, 'Kamalganj', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1148, 1, 2, 'Kanth', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1149, 1, 2, 'Karari', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1150, 1, 2, 'Karhal', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1151, 1, 2, 'Kasganj', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1152, 1, 2, 'Katra', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1153, 1, 2, 'Kauriaganj', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1154, 1, 2, 'Khadda', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1155, 1, 2, 'Khaga', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1156, 1, 2, 'Khair', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1157, 1, 2, 'Khairabad', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1158, 1, 2, 'Khalilabad', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1159, 1, 2, 'Khamaria', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1160, 1, 2, 'Khanpur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1161, 1, 2, 'Kharela', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1162, 1, 2, 'Khatauli', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1163, 1, 2, 'Khudaganj', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1164, 1, 2, 'Khurja', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1165, 1, 2, 'Khutar', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1166, 1, 2, 'Kiratpur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1167, 1, 2, 'Kishni', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1168, 1, 2, 'Konch', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1169, 1, 2, 'Kopaganj', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1170, 1, 2, 'Kunda', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1171, 1, 2, 'Kundarki', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1172, 1, 2, 'Kuraoli', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1173, 1, 2, 'Kurara', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1174, 1, 2, 'Kusmara', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1175, 1, 2, 'Laharpur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1176, 1, 2, 'Lakhna', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1177, 1, 2, 'Lalganj', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1178, 1, 2, 'Lar', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1179, 1, 2, 'Loni', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1180, 1, 2, 'Madhoganj', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1181, 1, 2, 'Madhogarh', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1182, 1, 2, 'Chitrakoot', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1183, 1, 2, 'Maghar', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1184, 1, 2, 'Mahaban', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1185, 1, 2, 'Maholi', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1186, 1, 2, 'Mailani', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1187, 1, 2, 'Majhauli Raj', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1188, 1, 2, 'Malihabad', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1189, 1, 2, 'Mallawan', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1190, 1, 2, 'Mandawar', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1191, 1, 2, 'Manikpur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1192, 1, 2, 'Marehra', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1193, 1, 2, 'Mariahu', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1194, 1, 2, 'Mauranipur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1195, 1, 2, 'Maurawan', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1196, 1, 2, 'Mawana', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1197, 1, 2, 'Mehdawal', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1198, 1, 2, 'Mehnagar', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1199, 1, 2, 'Milak', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1200, 1, 2, 'Mirganj', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1201, 1, 2, 'Modinagar', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1202, 1, 2, 'Mohammadabad', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1203, 1, 2, 'Mohammadi', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1204, 1, 2, 'Mohan', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1205, 1, 2, 'Mohiuddinpur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1206, 1, 2, 'Moth', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1207, 1, 2, 'Mubarakpur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1208, 1, 2, 'Muradnagar', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1209, 1, 2, 'Mursan', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1210, 1, 2, 'Musafirkhana', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1211, 1, 2, 'Nadigaon', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1212, 1, 2, 'Nagina', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1213, 1, 2, 'Nagram', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1214, 1, 2, 'Nai Bazar', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1215, 1, 2, 'Najibabad', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1216, 1, 2, 'Nakur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1217, 1, 2, 'Nanauta', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1218, 1, 2, 'Nanpara', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1219, 1, 2, 'Naraini', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1220, 1, 2, 'Narauli', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1221, 1, 2, 'Nawabganj', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1222, 1, 2, 'Nichlaul', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1223, 1, 2, 'Nizamabad', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1224, 1, 2, 'Obra', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1225, 1, 2, 'Orai', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1226, 1, 2, 'Pachperwa', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1227, 1, 2, 'Padrauna', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1228, 1, 2, 'Pahasu', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1229, 1, 2, 'Pali', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1230, 1, 2, 'Patti', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1231, 1, 2, 'Shrawasti', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1232, 1, 2, 'Phulpur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1233, 1, 2, 'Pihani', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1234, 1, 2, 'Pilkhana', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1235, 1, 2, 'Pilkhuwa', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1236, 1, 2, 'Pinahat', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1237, 1, 2, 'Pratapgarh City', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1238, 1, 2, 'Pukhrayan', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1239, 1, 2, 'Puranpur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1240, 1, 2, 'Purwa', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1241, 1, 2, 'Radhakund', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1242, 1, 2, 'Rae Bareli', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1243, 1, 2, 'Rajapur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1244, 1, 2, 'Ramkola', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1245, 1, 2, 'Ramnagar', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1246, 1, 2, 'Rampura', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1247, 1, 2, 'Ranipur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1248, 1, 2, 'Rasra', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1249, 1, 2, 'Rasulabad', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1250, 1, 2, 'Raya', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1251, 1, 2, 'Reoti', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1252, 1, 2, 'Rudrapur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1253, 1, 2, 'Rura', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1254, 1, 2, 'Sadabad', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1255, 1, 2, 'Sadat', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1256, 1, 2, 'Safipur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1257, 1, 2, 'Sahanpur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1258, 1, 2, 'Sahaspur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1259, 1, 2, 'Sahaswan', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1260, 1, 2, 'Sahatwar', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1261, 1, 2, 'Sahawar', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1262, 1, 2, 'Sahjanwa', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1263, 1, 2, 'Saidpur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1264, 1, 2, 'Salempur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1265, 1, 2, 'Salon', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1266, 1, 2, 'Sambhal', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1267, 1, 2, 'Samthar', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1268, 1, 2, 'Sandi', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1269, 1, 2, 'Sandila', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1270, 1, 2, 'Sarai Mir', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1271, 1, 2, 'Sasni', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1272, 1, 2, 'Satrikh', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1273, 1, 2, 'Saurikh', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1274, 1, 2, 'Seohara', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1275, 1, 2, 'Shahabad', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1276, 1, 2, 'Shahganj', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1277, 1, 2, 'Shahi', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1278, 1, 2, 'Shahpur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1279, 1, 2, 'Shamli', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1280, 1, 2, 'Shankargarh', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1281, 1, 2, 'Shergarh', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1282, 1, 2, 'Sherkot', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1283, 1, 2, 'Shikarpur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1284, 1, 2, 'Shikohabad', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1285, 1, 2, 'Sikanderpur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1286, 1, 2, 'Sikandra', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1287, 1, 2, 'Sikandrabad', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1288, 1, 2, 'Sirathu', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1289, 1, 2, 'Sirsaganj', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1290, 1, 2, 'Sirsi', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1291, 1, 2, 'Sisauli', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1292, 1, 2, 'Suar', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1293, 1, 2, 'Suriyawan', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1294, 1, 2, 'Talbehat', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1295, 1, 2, 'Talgram', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1296, 1, 2, 'Tanda', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1297, 1, 2, 'Tetri Bazar', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1298, 1, 2, 'Thakurdwara', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1299, 1, 2, 'Thana Bhawan', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1300, 1, 2, 'Tilhar', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1301, 1, 2, 'Tindwari', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1302, 1, 2, 'Tulsipur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1303, 1, 2, 'Tundla', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1304, 1, 2, 'Ujhani', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1305, 1, 2, 'Ujhari', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1306, 1, 2, 'Umri', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1307, 1, 2, 'Un', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1308, 1, 2, 'Usawan', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1309, 1, 2, 'Usehat', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1310, 1, 2, 'Utraula', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1311, 1, 2, 'Wazirganj', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1312, 1, 2, 'Zaidpur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1313, 1, 2, 'Zamania', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1314, 1, 32, 'Bardhaman', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1315, 1, 32, 'Darjiling ', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1316, 1, 32, 'Howrah', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1317, 1, 32, 'Suri', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1318, 1, 32, 'Barast', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1319, 1, 32, 'Jalpaiguri', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1320, 1, 32, 'Chinsurah', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1321, 1, 32, 'Adra', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1322, 1, 32, 'Alipore', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1323, 1, 32, 'English Bazar', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1324, 1, 32, 'Berhampore', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1325, 1, 32, 'Aiho', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1326, 1, 32, 'Bankura', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1327, 1, 32, 'Alipurduar', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1328, 1, 32, 'Medinipur ', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1329, 1, 32, 'Krishnagar', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1330, 1, 32, 'Amtala', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1331, 1, 32, 'Puruliya', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1332, 1, 32, '24 Parganas', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1333, 1, 32, 'Aurangabad', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1334, 1, 32, 'Baduria', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1335, 1, 32, 'Bagnan', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1336, 1, 32, ' West Dinajpur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1337, 1, 32, 'Bahula', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1338, 1, 32, 'Koch Bihar ', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1339, 1, 32, 'Canning - II', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1340, 1, 32, 'Balichak', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1341, 1, 32, 'Ballavpur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1342, 1, 32, 'Bally', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1343, 1, 32, 'Bansberia', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1344, 1, 32, 'Baranagar', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1345, 1, 32, 'Barasat', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1346, 1, 32, 'Raigung', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1347, 1, 32, 'Begampur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1348, 1, 32, 'Balurghat', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1349, 1, 32, 'Beliatore', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1350, 1, 32, 'Bhadreswar', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1351, 1, 32, 'Bidhan Nagar', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1352, 1, 32, 'Birlapur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1353, 1, 32, 'Bishnupur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1354, 1, 32, 'Bolpur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1355, 1, 32, 'Bowali', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1356, 1, 32, 'Budge Budge', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1357, 1, 32, 'Chakdaha', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1358, 1, 32, 'Champdani', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1359, 1, 32, 'Chittaranjan', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1360, 1, 32, 'Dainhat', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1361, 1, 32, 'Debipur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1362, 1, 32, 'Dhakuria', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1363, 1, 32, 'Dhandadihi', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1364, 1, 32, 'Dhatrigram', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1365, 1, 32, 'Dhulian', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1366, 1, 32, 'Dinhata', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1367, 1, 32, 'Domjur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1368, 1, 32, 'Dubrajpur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1369, 1, 32, 'Durgapur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1370, 1, 32, 'Egra', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1371, 1, 32, 'Falakata', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1372, 1, 32, 'Gairkata', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1373, 1, 32, 'Gangarampur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1374, 1, 32, 'Ghatal', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1375, 1, 32, 'Ghorsala', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1376, 1, 32, 'Goaljan', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1377, 1, 32, 'Gobardanga', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1378, 1, 32, 'Gopinathpur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1379, 1, 32, 'Haldia', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1380, 1, 32, 'Haldibari', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1381, 1, 32, 'Halisahar', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1382, 1, 32, 'Haripur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1383, 1, 32, 'Islampur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1384, 1, 32, 'Jangipur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1385, 1, 32, 'Jhalda', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1386, 1, 32, 'Kalimpong', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1387, 1, 32, 'Kalna', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1388, 1, 32, 'Kalyani', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1389, 1, 32, 'Kamarhati', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1390, 1, 32, 'Kanaipur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1391, 1, 32, 'Kanchrapara', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1392, 1, 32, 'Karimpur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1393, 1, 32, 'Kasba', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1394, 1, 32, 'Kendua', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1395, 1, 32, 'Kharagpur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1396, 1, 32, 'Kharar', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1397, 1, 32, 'Kolaghat', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1398, 1, 32, 'Konnagar', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1399, 1, 32, 'Krishnapur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1400, 1, 32, 'Kulti', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1401, 1, 32, 'Kurseong', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1402, 1, 32, 'Madanpur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1403, 1, 32, 'Maslandapur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1404, 1, 32, 'Mathabhanga', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1405, 1, 32, 'Mekliganj', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1406, 1, 32, 'Memari', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1407, 1, 32, 'Mirik', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1408, 1, 32, 'Nabagram', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1409, 1, 32, 'Naihati', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00');
INSERT INTO `City` (`cityId`, `countryId`, `stateId`, `cityName`, `allowModi`, `userId`, `dateCreated`, `userIdUpdate`, `dateUpdate`) VALUES
(1410, 1, 32, 'Nasra', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1411, 1, 32, 'Natibpur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1412, 1, 32, 'Panchla', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1413, 1, 32, 'Pandua', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1414, 1, 32, 'Panihati', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1415, 1, 32, 'Panuhat', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1416, 1, 32, 'Raghunathpur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1417, 1, 32, 'Raiganj', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1418, 1, 32, 'Rajarhat Gopalpur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1419, 1, 32, 'Ramchandrapur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1420, 1, 32, 'Ramjibanpur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1421, 1, 32, 'Ramnagar', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1422, 1, 32, 'Rishra', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1423, 1, 32, 'Sahapur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1424, 1, 32, 'Sainthia', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1425, 1, 32, 'Sankrail', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1426, 1, 32, 'Santipur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1427, 1, 32, 'Santoshpur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1428, 1, 32, 'Sarenga', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1429, 1, 32, 'Simla', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1430, 1, 32, 'Singur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1431, 1, 32, 'Sonamukhi', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1432, 1, 32, 'Taherpur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1433, 1, 32, 'Taki', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1434, 1, 32, 'Titagarh', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1435, 1, 32, 'Ukhra', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1436, 1, 32, 'Uluberia', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1437, 1, 33, 'Port Blair', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1438, 1, 33, 'Car Nicobar', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1439, 1, 34, 'Nellore', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1440, 1, 56, 'Nalgonda', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1441, 1, 56, 'Adilabad', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1442, 1, 56, 'Hyderabad', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1443, 1, 34, 'Vishakhapatnam', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1444, 1, 34, 'Mahboobnagar', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1445, 1, 34, 'Guntur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1446, 1, 34, 'Anantapur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1447, 1, 34, 'Kurnool', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1448, 1, 56, 'Warangal Rural', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1449, 1, 56, 'Karimnagar', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1450, 1, 34, 'Lingotam', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1451, 1, 34, 'Achanta', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1452, 1, 34, 'Addanki', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1453, 1, 34, 'Prakasam', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1454, 1, 34, 'East Godavari', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1455, 1, 34, 'Adivarampet', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1456, 1, 56, 'Khammam', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1457, 1, 34, 'Adoni', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1458, 1, 34, 'Agadur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1459, 1, 34, 'Agnoor', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1460, 1, 34, 'West Godavari', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1461, 1, 34, 'Ainapur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1462, 1, 34, 'Krishna', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1463, 1, 34, 'Ajjada', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1464, 1, 34, 'Cuddapah', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1465, 1, 34, 'Srikakulam', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1466, 1, 34, 'Chittoor', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1467, 1, 34, 'Vizianagaram', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1468, 1, 56, 'Medak', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1469, 1, 56, 'Nizamabad', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1470, 1, 34, 'Ranga Reddy', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1471, 1, 34, 'Amalapuram', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1472, 1, 34, 'Anakapalle', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1473, 1, 34, 'Asifabad', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1474, 1, 34, 'Balkonda', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1475, 1, 34, 'Balusupadu', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1476, 1, 34, 'Bandankal', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1477, 1, 34, 'Banswada', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1478, 1, 34, 'Bardipur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1479, 1, 34, 'Bhadrachalam', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1480, 1, 34, 'Bhainsa', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1481, 1, 56, 'Bhongir', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1482, 1, 34, 'Bodhan', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1483, 1, 34, 'Chembakur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1484, 1, 34, 'Chirala', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1485, 1, 34, 'Dammapeta', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1486, 1, 34, 'Dangeru', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1487, 1, 34, 'Dharmavaram', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1488, 1, 34, 'Domakonda', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1489, 1, 34, 'Dommeru', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1490, 1, 34, 'Ekambara kuppam', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1491, 1, 34, 'Eluru', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1492, 1, 34, 'Gadwal', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1493, 1, 34, 'Gooty', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1494, 1, 34, 'Gudivada', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1495, 1, 34, 'Gudur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1496, 1, 34, 'Gundmal', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1497, 1, 34, 'Gundugolanu', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1498, 1, 34, 'Gundupapala', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1499, 1, 34, 'Guntakal', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1500, 1, 34, 'Hindupur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1501, 1, 34, 'Husnabad', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1502, 1, 34, 'Huzurnagar', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1503, 1, 34, 'Ibrahimpatnam', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1504, 1, 34, 'Ichapuram', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1505, 1, 56, 'Jagtial', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1506, 1, 34, 'Jangaon', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1507, 1, 34, 'Kadiri', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1508, 1, 34, 'Kakinada', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1509, 1, 34, 'Kallur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1510, 1, 56, 'Kamareddy', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1511, 1, 34, 'Kandukur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1512, 1, 34, 'Kanuru', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1513, 1, 34, 'Kavali', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1514, 1, 34, 'Koratla', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1515, 1, 34, 'Kovvur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1516, 1, 34, 'Kuppam', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1517, 1, 34, 'Macherla', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1518, 1, 34, 'Machilipatnam', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1519, 1, 34, 'Madanapalle', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1520, 1, 34, 'Malkajgiri', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1521, 1, 34, 'Mangalagiri', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1522, 1, 34, 'Markapur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1523, 1, 34, 'Nagari', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1524, 1, 34, 'Nandyal', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1525, 1, 34, 'Narayanpet', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1526, 1, 34, 'Narsapur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1527, 1, 34, 'Narsipatnam', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1528, 1, 34, 'Nidadavole', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1529, 1, 56, 'Nirmal', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1530, 1, 34, 'Nuzvid', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1531, 1, 34, 'Ongole', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1532, 1, 34, 'Palwancha', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1533, 1, 34, 'Pedana', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1534, 1, 34, 'Peddapuram', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1535, 1, 34, 'Ponnur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1536, 1, 34, 'Proddatur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1537, 1, 34, 'Puttur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1538, 1, 34, 'Rajahmundry', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1539, 1, 34, 'Rajam', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1540, 1, 34, 'Rajendranagar', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1541, 1, 34, 'Rayadurg', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1542, 1, 34, 'Repalle', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1543, 1, 56, 'Sangareddy', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1544, 1, 34, 'Sarapaka', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1545, 1, 34, 'Sattenapalle', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1546, 1, 34, 'Secunderabad', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1547, 1, 56, 'Siddipet', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1548, 1, 34, 'Sirsilla', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1549, 1, 34, 'Sompeta', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1550, 1, 56, 'Suryapet', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1551, 1, 34, 'Suryaraopeta', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1552, 1, 34, 'Tadepalligudem', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1553, 1, 34, 'Tandur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1554, 1, 34, 'Tanuku', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1555, 1, 34, 'Tenali', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1556, 1, 34, 'Tirumala', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1557, 1, 34, 'Tirupati', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1558, 1, 34, 'Vepagunta', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1559, 1, 34, 'Vijayawada', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1560, 1, 34, 'Visakhapatnam', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1561, 1, 34, 'Wanaparthi', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1562, 1, 34, 'Yeditha', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1563, 1, 34, 'Yellandu', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1564, 1, 34, 'Yemmiganur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1565, 1, 34, 'Yerraguntla', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1566, 1, 34, 'Zahirabad', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1567, 1, 35, 'Along', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1568, 1, 35, 'Tezu', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1569, 1, 35, 'Basar', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1570, 1, 35, 'Bomdila', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1571, 1, 35, 'Ziro', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1572, 1, 35, 'Itanagar', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1573, 1, 35, 'Pasighat ', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1574, 1, 35, 'Jairampur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1575, 1, 35, 'Khonsa', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1576, 1, 35, 'Namsai', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1577, 1, 35, 'Roing', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1578, 1, 35, 'Seppa', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1579, 1, 35, 'Daporijo', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1580, 1, 35, 'Changlang', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1581, 1, 35, 'Yingkiong ', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1582, 1, 36, 'Abhayapuri', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1583, 1, 36, 'Dhubri', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1584, 1, 36, 'Goalpara', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1585, 1, 36, 'Tezpur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1586, 1, 36, 'Nagaon', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1587, 1, 36, 'Guwahati', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1588, 1, 36, 'Amguri', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1589, 1, 36, 'Jorhat', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1590, 1, 36, 'Silchar', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1591, 1, 36, 'Karimganj', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1592, 1, 36, 'Kokrajhar', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1593, 1, 36, 'Dibrugarh', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1594, 1, 36, 'Mangaldoi', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1595, 1, 36, 'Badarpur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1596, 1, 36, 'North Lakhimpur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1597, 1, 36, 'Barpeta', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1598, 1, 36, 'Hajo', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1599, 1, 36, 'Diphu', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1600, 1, 36, 'Nalbari', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1601, 1, 36, 'Bongaigaon', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1602, 1, 36, 'Barpathar', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1603, 1, 36, 'Basugaon', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1604, 1, 36, 'Golaghat', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1605, 1, 36, 'Sibsagar', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1606, 1, 36, 'Marigaon', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1607, 1, 36, 'Bijni', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1608, 1, 36, 'Bokajan', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1609, 1, 36, 'Bokakhat', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1610, 1, 36, 'Chabua', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1611, 1, 36, 'Chapar', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1612, 1, 36, 'Dergaon', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1613, 1, 36, 'Dharapur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1614, 1, 36, 'Dhekiajuli', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1615, 1, 36, 'Dhemaji', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1616, 1, 36, 'Dhing', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1617, 1, 36, 'Digboi', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1618, 1, 36, 'Tinsukia', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1619, 1, 36, 'Gohpur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1620, 1, 36, 'Haflong', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1621, 1, 36, 'Hailakandi', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1622, 1, 36, 'Hamren', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1623, 1, 36, 'Kalaigaon', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1624, 1, 36, 'Hojai', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1625, 1, 36, 'Howli', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1626, 1, 36, 'Howraghat', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1627, 1, 36, 'Lakhipur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1628, 1, 36, 'Lala', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1629, 1, 36, 'Lanka', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1630, 1, 17, 'Narayanpur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1631, 1, 36, 'Lumding', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1632, 1, 36, 'Mahur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1633, 1, 36, 'Maibong', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1634, 1, 36, 'Mankachar', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1635, 1, 36, 'Margherita', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1636, 1, 36, 'Mariani', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1637, 1, 36, 'Moranhat', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1638, 1, 36, 'Namrup', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1639, 1, 36, 'Nazira', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1640, 1, 36, 'North Guwahati', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1641, 1, 36, 'Palasbari', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1642, 1, 36, 'Pathsala', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1643, 1, 36, 'Rangapara', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1644, 1, 36, 'Rangia', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1645, 1, 36, 'Sapatgram', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1646, 1, 36, 'Sarthebari', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1647, 1, 36, 'Sarupathar', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1648, 1, 36, 'Silapathar', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1649, 1, 36, 'Sonari', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1650, 1, 36, 'Tangla', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1651, 1, 36, 'Tihu', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1652, 1, 36, 'Udalguri', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1653, 1, 39, 'Hissar', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1654, 1, 39, 'Bhiwani', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1655, 1, 39, 'Ambala', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1656, 1, 39, 'Karnal', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1657, 1, 39, 'Rohtak', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1658, 1, 39, 'Gurgaon', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1659, 1, 39, 'Sonepat', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1660, 1, 39, 'Narnaul', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1661, 1, 39, 'Faridabad', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1662, 1, 39, 'Sirsa', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1663, 1, 39, 'Jhajjar', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1664, 1, 39, 'Assandh', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1665, 1, 39, 'Jind', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1666, 1, 39, 'Bahadurgarh', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1667, 1, 39, 'Rewari', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1668, 1, 39, 'Barwala', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1669, 1, 39, 'Bawal', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1670, 1, 39, 'Bawani Khera', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1671, 1, 39, 'Beri', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1672, 1, 39, 'Bilaspur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1673, 1, 39, 'Kurukshetra', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1674, 1, 39, 'Charkhi Dadri', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1675, 1, 39, 'Chhachhrauli', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1676, 1, 39, 'Panipat', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1677, 1, 39, 'Kaithal', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1678, 1, 39, 'Dharuhera', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1679, 1, 39, 'Ellenabad', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1680, 1, 39, 'Fatehabad', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1681, 1, 39, 'Fatehbad', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1682, 1, 39, 'Ferozepur Jhirka', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1683, 1, 39, 'Ganaur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1684, 1, 39, 'Gharaunda', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1685, 1, 39, 'Gohana', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1686, 1, 39, 'Hansi', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1687, 1, 39, 'Hassanpur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1688, 1, 39, 'Hathin', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1689, 1, 39, 'Jagadhri', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1690, 1, 39, 'Yamunanagar', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1691, 1, 39, 'Jakhal Mandi', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1692, 1, 39, 'Julana', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1693, 1, 39, 'Kalanaur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1694, 1, 39, 'Kalka', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1695, 1, 39, 'Panchkula ', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1696, 1, 39, 'Kanina', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1697, 1, 39, 'Ladwa', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1698, 1, 39, 'Loharu', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1699, 1, 39, 'Mandi Dabwali', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1700, 1, 39, 'Mustafabad', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1701, 1, 39, 'Naraingarh', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1702, 1, 39, 'Narnaund', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1703, 1, 39, 'Narwana', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1704, 1, 39, 'Nilokheri', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1705, 1, 39, 'Nuh', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1706, 1, 39, 'Palwal', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1707, 1, 39, 'Pataudi', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1708, 1, 39, 'Pehowa', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1709, 1, 39, 'Pinjore', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1710, 1, 39, 'Pundri', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1711, 1, 39, 'Radaur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1712, 1, 39, 'Raipur Rani', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1713, 1, 39, 'Rania', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1714, 1, 39, 'Safidon', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1715, 1, 39, 'Samalkha', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1716, 1, 39, 'Sohna', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1717, 1, 39, 'Tohana', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1718, 1, 39, 'Tosham', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1719, 1, 39, 'Uchana', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1720, 1, 40, 'Shimla', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1721, 1, 40, 'Solan', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1722, 1, 40, 'Dharamasala', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1723, 1, 6, 'Una', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1724, 1, 40, 'Reckong Peo', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1725, 1, 40, 'Arki', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1726, 1, 40, 'Kullu', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1727, 1, 40, 'Hamirpur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1728, 1, 40, 'Nahan', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1729, 1, 40, 'Mandi', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1730, 1, 40, 'Bakloh', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1731, 1, 40, 'Banjar', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1732, 1, 40, 'Chamba', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1733, 1, 40, 'Bilaspur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1734, 1, 40, 'Bhota', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1735, 1, 40, 'Bhuntar', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1736, 1, 40, ' Bilaspur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1737, 1, 40, 'Dagshai', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1738, 1, 40, 'Daulatpur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1739, 1, 40, 'Gagret', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1740, 1, 40, 'Ghumarwin', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1741, 1, 40, 'Jubbal', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1742, 1, 40, 'Jutogh', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1743, 1, 40, 'Kangra', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1744, 1, 40, 'Kasauli', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1745, 1, 40, 'Nadaun', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1746, 1, 40, 'Nagrota Bagwan', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1747, 1, 40, 'Nalagarh', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1748, 1, 40, 'Narkanda', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1749, 1, 40, 'Nurpur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1750, 1, 40, 'Palampur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1751, 1, 40, 'Paonta Sahib', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1752, 1, 40, 'Parwanoo', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1753, 1, 40, 'Rajgarh', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1754, 1, 40, 'Rampur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1755, 1, 40, 'Rohru', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1756, 1, 40, 'Santokhgarh', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1757, 1, 40, 'Sarkaghat', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1758, 1, 40, 'Keylong', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1759, 1, 40, 'Theog', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1760, 1, 42, 'Ranchi', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1761, 1, 42, 'Dhanbad', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1762, 1, 42, 'Adityapur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1763, 1, 42, 'Jamshedpur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1764, 1, 42, 'Paschimi Singhbhum', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1765, 1, 42, 'Dumka', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1766, 1, 42, 'Pakaur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1767, 1, 42, 'Hazaribagh', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1768, 1, 42, 'Giridih', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1769, 1, 42, 'Deoghar', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1770, 1, 42, 'Palamu', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1771, 1, 42, 'Gumla', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1772, 1, 42, 'Bokaro', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1773, 1, 42, 'Sahebganj', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1774, 1, 42, 'Bermo', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1775, 1, 42, 'Grahwa', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1776, 1, 42, 'Lohardaga', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1777, 1, 42, 'Bundu', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1778, 1, 42, 'Chakradharpur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1779, 1, 42, 'Chandil', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1780, 1, 42, 'Chandrapura', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1781, 1, 42, 'Chas', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1782, 1, 42, 'Chatra', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1783, 1, 42, 'Chiria', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1784, 1, 42, 'Chirkunda', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1785, 1, 42, 'Garhwa', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1786, 1, 42, 'Ghatshila', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1787, 1, 42, 'Gobindpur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1788, 1, 42, 'Gomoh', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1789, 1, 42, 'Gua', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1790, 1, 42, 'Kodarma', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1791, 1, 42, 'Jamadoba', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1792, 1, 42, 'Jamtara', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1793, 1, 42, 'Jaridih Bazar', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1794, 1, 42, 'Jasidih', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1795, 1, 42, 'Jharia', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1796, 1, 42, 'Jhinkpani', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1797, 1, 42, 'Jhumri Tilaiya', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1798, 1, 42, 'Jugsalai', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1799, 1, 42, 'Kandra', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1800, 1, 42, 'Kanke', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1801, 1, 42, 'Kedla', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1802, 1, 42, 'Khunti', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1803, 1, 42, 'Kuju', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1804, 1, 42, 'Latehar', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1805, 1, 42, 'Madhupur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1806, 1, 42, 'Malkera', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1807, 1, 42, 'Mango', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1808, 1, 42, 'Mihijam', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1809, 1, 42, 'Mugma', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1810, 1, 42, 'Noamundi', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1811, 1, 42, 'Patratu', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1812, 1, 42, 'Rajmahal', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1813, 1, 42, 'Sahibganj', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1814, 1, 42, 'Sijua', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1815, 1, 42, 'Simdega', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1816, 1, 42, 'Sindri', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1817, 1, 42, 'Sini', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1818, 1, 42, 'Sirka', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1819, 1, 42, 'Tisra', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1820, 1, 42, 'Topa', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1821, 1, 42, 'Topchanchi', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1822, 1, 7, 'Bangalore', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1823, 1, 7, 'Bangalore North', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1824, 1, 7, 'Hiriyur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1825, 1, 7, 'Mandya', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1826, 1, 7, 'Mangalore', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1827, 1, 7, ' Belgaum', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1828, 1, 7, 'Hubli', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1829, 1, 7, 'Sringeri', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1830, 1, 7, 'Udupi', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1831, 1, 7, 'Gulbarga', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1832, 1, 7, 'Mysore', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1833, 1, 7, 'Anekal', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1834, 1, 7, 'Adyar', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1835, 1, 7, 'Afzalpur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1836, 1, 7, 'Haveri', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1837, 1, 7, 'Koppal', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1838, 1, 7, 'Bangalore South', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1839, 1, 7, 'Indi', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1840, 1, 7, 'Arsikere', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1841, 1, 7, 'Arkalgud', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1842, 1, 7, 'Hassan', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1843, 1, 7, 'Chikamaglur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1844, 1, 7, 'Karwar', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1845, 1, 7, 'Shimoga', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1846, 1, 17, 'Bijapur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1847, 1, 7, 'Bidar', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1848, 1, 7, 'Tarikere', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1849, 1, 7, 'Chitradurga', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1850, 1, 7, 'Hangal', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1851, 1, 7, 'Dharwar', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1852, 1, 7, 'Krishnarajpet', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1853, 1, 7, 'Navalgund', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1854, 1, 7, 'Mulbagal', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1855, 1, 7, 'Chamarajanagar', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1856, 1, 7, 'Sindgi', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1857, 1, 7, 'Kolar', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1858, 1, 7, 'Alnavar', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1859, 1, 7, 'Alur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1860, 1, 7, 'Aland', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1861, 1, 7, 'Bellary', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1862, 1, 7, 'Hungund', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1863, 1, 7, 'Turuvekere', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1864, 1, 7, 'Virajpet', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1865, 1, 7, 'Kunigal', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1866, 1, 7, 'Davanagere', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1867, 1, 7, 'Sagar', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1868, 1, 7, 'Sorab', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1869, 1, 7, 'Bhadravati', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1870, 1, 7, 'Somvarpet', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1871, 1, 7, 'Jevargi', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1872, 1, 7, 'Raichur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1873, 1, 7, 'Ankola', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1874, 1, 7, 'Annigeri', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1875, 1, 7, 'Tumkur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1876, 1, 7, 'Belgaum', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1877, 1, 7, 'Hosanagara', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1878, 1, 7, 'Devadurga', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1879, 1, 7, 'Yelbarga', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1880, 1, 7, 'Jagalur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1881, 1, 7, 'Badami', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1882, 1, 7, 'Ron', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1883, 1, 7, 'Athni', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1884, 1, 7, 'Aurad', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1885, 1, 7, 'Heggadadevankote', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1886, 1, 7, 'Madhugiri', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1887, 1, 7, 'Bagalkot', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1888, 1, 7, 'Manvi', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1889, 1, 7, 'Koduru', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1890, 1, 7, 'Sakleshpur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1891, 1, 7, 'Bagepalli', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1892, 1, 7, 'Bajpe', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1893, 1, 7, 'Shirhatti', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1894, 1, 7, 'Kalghatgi', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1895, 1, 7, 'Mudigere', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1896, 1, 7, 'Kollegal', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1897, 1, 7, 'Gundlupet', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1898, 1, 7, 'Gangawati', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1899, 1, 7, 'Shiggaon', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1900, 1, 7, 'Chik Ballapur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1901, 1, 7, 'Bannur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1902, 1, 7, 'Bantwal', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1903, 1, 7, 'Tirthahalli', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1904, 1, 7, 'Chintamani', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1905, 1, 7, 'Hagaribommanahalli', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1906, 1, 7, 'Hosadurga', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1907, 1, 7, 'Malavalli', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1908, 1, 7, 'Malur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1909, 1, 7, 'Harihar', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1910, 1, 7, 'Homnabad', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1911, 1, 7, 'Bangarapet', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1912, 1, 7, 'Madikeri', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1913, 1, 7, 'Supa', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1914, 1, 7, 'Chitapur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1915, 1, 7, 'Bhalki', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1916, 1, 7, 'Bhatkal', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1917, 1, 7, 'Channarayapatna', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1918, 1, 7, 'Jamkhandi', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1919, 1, 7, 'Belur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1920, 1, 7, 'Bilgi', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1921, 1, 7, 'Gubbi', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1922, 1, 7, 'Tirumakudal Narsipur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1923, 1, 7, 'Bommanahalli', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1924, 1, 7, 'Koratagere', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1925, 1, 7, 'Byadgi', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1926, 1, 7, 'Byatarayanapura', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1927, 1, 7, 'Mundgod', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1928, 1, 7, 'Challakere', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1929, 1, 7, 'Channapatna', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1930, 1, 7, 'Devanahalli', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1931, 1, 7, 'Hirekerur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1932, 1, 7, 'Chikodi', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1933, 1, 7, 'Honnali', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1934, 1, 7, 'Chincholi', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1935, 1, 7, 'Lingsugur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1936, 1, 7, 'Mundargi', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1937, 1, 7, 'Dandeli', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1938, 1, 7, 'Dasarahalli', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1939, 1, 7, 'Kushtagi', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1940, 1, 7, 'Yadgir', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1941, 1, 7, 'Dharwad', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1942, 1, 7, 'Gauribidanur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1943, 1, 7, 'Hunsur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1944, 1, 7, 'Gokak', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1945, 1, 7, 'Gonikoppal', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1946, 1, 7, 'Koppa', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1947, 1, 7, 'Kundgol', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1948, 1, 7, 'Tiptur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1949, 1, 7, 'Mudhol', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1950, 1, 7, 'Dod Ballapur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1951, 1, 7, 'Piriyapatna', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1952, 1, 7, 'Haliyal', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1953, 1, 7, 'Hole Narsipur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1954, 1, 7, 'Harapanahalli', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1955, 1, 7, 'Shahpur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1956, 1, 7, 'Chiknayakanhalli', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1957, 1, 7, 'Gadag', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1958, 1, 7, 'Savanur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1959, 1, 7, 'Shorapur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1960, 1, 7, 'Hebbalu', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1961, 1, 7, 'Sira', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1962, 1, 7, 'Holalkere', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1963, 1, 7, 'Honavar', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1964, 1, 7, 'Ranibennur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1965, 1, 7, 'Pavagada', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1966, 1, 7, 'Channagiri', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1967, 1, 7, 'Hoskote', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1968, 1, 7, 'Hospet', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1969, 1, 7, 'Hukeri', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1970, 1, 7, 'Sedam', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1971, 1, 7, 'Jog Falls', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1972, 1, 7, 'Kadur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1973, 1, 7, 'Chikamangalur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1974, 1, 7, 'Kampli', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1975, 1, 7, 'Kanakapura', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1976, 1, 7, 'Kannur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1977, 1, 7, 'Muddebihal', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1978, 1, 7, 'Kengeri', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1979, 1, 7, 'Kerur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1980, 1, 7, 'Khanapur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1981, 1, 7, 'Kodigenahalli', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1982, 1, 7, 'Puttur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1983, 1, 7, 'Konnur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1984, 1, 7, 'Shikarpur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1985, 1, 7, 'Kotturu', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1986, 1, 7, 'Krishnarajasagara', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1987, 1, 7, 'Kudchi', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1988, 1, 7, 'Basavana Bagevadi', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1989, 1, 7, 'Kudligi', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1990, 1, 7, 'Kudremukh', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1991, 1, 7, 'Kumta', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1992, 1, 7, 'Kushalnagar', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1993, 1, 7, 'Lakshmeshwar', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1994, 1, 7, 'Londa', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1995, 1, 7, 'Maddur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1996, 1, 7, 'Magadi', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1997, 1, 7, 'Narasimharajapura', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1998, 1, 7, 'Mahadevapura', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(1999, 1, 7, 'Yelandur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2000, 1, 7, 'Basavakalyan', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2001, 1, 7, 'Krishnarajanagara', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2002, 1, 7, 'Mudgal', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2003, 1, 7, 'Mulgund', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2004, 1, 7, 'Mulki', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2005, 1, 7, 'Nagamangala', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2006, 1, 7, 'Nanjangud', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2007, 1, 7, 'Naregal', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2008, 1, 7, 'Nipani', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2009, 1, 7, 'Hadagalli', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2010, 1, 7, 'Pandavapura', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2011, 1, 7, 'Pudu', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2012, 1, 7, 'Ramanagaram', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2013, 1, 7, 'Ramdurg', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2014, 1, 7, 'Sindhnur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2015, 1, 7, 'Saligram', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2016, 1, 7, 'Sandur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2017, 1, 7, 'Sankeshwar', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2018, 1, 7, 'Shahabad', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2019, 1, 7, 'Shaktinagar', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00');
INSERT INTO `City` (`cityId`, `countryId`, `stateId`, `cityName`, `allowModi`, `userId`, `dateCreated`, `userIdUpdate`, `dateUpdate`) VALUES
(2020, 1, 7, 'Siddapur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2021, 1, 7, 'Sidlaghatta', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2022, 1, 7, 'Sirsi', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2023, 1, 7, 'Siruguppa', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2024, 1, 7, 'Tekkalakota', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2025, 1, 7, 'Terdal', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2026, 1, 7, 'Thumbe', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2027, 1, 7, 'Chikmagalur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2028, 1, 7, 'Ullal', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2029, 1, 7, 'Venkatapura', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2030, 1, 7, 'Vijayapura', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2031, 1, 7, 'Wadi', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2032, 1, 7, 'Yelahanka', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2033, 1, 7, 'Srinivaspur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2034, 1, 7, 'Yellapur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2035, 1, 1, 'Malapuram', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2036, 1, 44, 'Kozhikode', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2037, 1, 44, 'Kasargode', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2038, 1, 44, 'Vythiri', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2039, 1, 44, 'Thrissur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2040, 1, 44, 'Cannanore (Kannur)', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2041, 1, 44, 'Trivandrum', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2042, 1, 44, 'Kollam', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2043, 1, 44, 'Pathanamthitta', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2044, 1, 44, 'Iduki', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2045, 1, 44, 'Kottayam', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2047, 1, 44, 'Ernakulam', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2048, 1, 44, 'Hosdurg', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2049, 1, 44, 'Alappuzha', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2050, 1, 44, 'Taliparamba', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2051, 1, 44, 'Alleppey', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2052, 1, 44, 'Palghat', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2053, 1, 44, 'Perinthalmanna', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2054, 1, 44, 'Aluva', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2055, 1, 44, 'Wayanad', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2056, 1, 44, 'Nilambur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2057, 1, 44, 'Neyyattinkara', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2058, 1, 44, 'Thiruvananthapuram', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2059, 1, 44, 'Kasaragod', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2060, 1, 44, 'Thalassery', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2061, 1, 44, 'Arookutty', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2062, 1, 44, 'Aroor', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2063, 1, 44, 'Kozhenchery', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2064, 1, 44, 'Attingal', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2065, 1, 44, 'Azhikode South', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2066, 1, 44, 'Beypore', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2067, 1, 44, 'Brahmakulam', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2068, 1, 44, 'Udumbanchola', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2069, 1, 44, 'Chavakkad', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2070, 1, 44, 'Chendamangalam', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2071, 1, 44, 'Chengamanad', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2072, 1, 44, 'Chengannur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2073, 1, 44, 'Cheranallur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2074, 1, 44, 'Cherthala', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2075, 1, 44, 'Kanjirappally', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2076, 1, 44, 'Ranni', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2077, 1, 44, 'Chirakkal', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2078, 1, 44, 'Dharmadom', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2079, 1, 44, 'Edathala', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2080, 1, 44, 'Mananthavady', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2081, 1, 44, 'Ernad', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2082, 1, 44, 'Eranholi', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2083, 1, 44, 'Irinjalakuda', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2084, 1, 44, 'Kadachira', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2085, 1, 44, 'Kadambur ', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2086, 1, 44, 'Mannarkad', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2087, 1, 44, 'Kalliasseri', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2088, 1, 44, 'Kalpetta', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2089, 1, 44, 'Kanhangad', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2090, 1, 44, 'Kanhirode', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2091, 1, 44, 'Kannadiparamba', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2092, 1, 44, 'Kannur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2093, 1, 44, 'Chirayinkeezhu', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2094, 1, 44, 'Ottappalam', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2095, 1, 44, 'Karthikappally', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2096, 1, 44, 'Kayamkulam', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2097, 1, 44, 'Sulthanbathery', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2098, 1, 44, 'Meenachil', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2099, 1, 44, 'Kochi', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2100, 1, 44, 'Kunnathunad', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2101, 1, 44, 'Kodungallur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2102, 1, 44, 'Quilandy', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2103, 1, 44, 'Kothamangalam', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2104, 1, 44, 'Vaikom', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2105, 1, 44, 'Devikulam', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2106, 1, 44, 'Tirur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2107, 1, 44, 'Mallappally', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2108, 1, 44, 'Kudlu', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2109, 1, 44, 'Kanayannur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2110, 1, 44, 'Kottarakkara', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2111, 1, 44, 'Kunnamkulam', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2112, 1, 44, 'Malappuram', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2113, 1, 44, 'Manjeri', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2114, 1, 44, 'Manjeshwar', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2115, 1, 44, 'Maradu', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2116, 1, 44, 'Mukundapuram', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2117, 1, 44, 'Mavoor', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2118, 1, 44, 'Methala', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2119, 1, 44, 'Muhamma', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2120, 1, 44, 'Muvattupuzha', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2121, 1, 44, 'Mulavukad', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2122, 1, 44, 'Munderi', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2123, 1, 44, 'Nadathara', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2124, 1, 44, 'Tirurangadi', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2125, 1, 44, 'Nedumangad', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2126, 1, 44, 'Thodupuzha', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2127, 1, 44, 'Olavanna', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2128, 1, 44, 'Palai', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2129, 1, 44, 'Palakkad', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2130, 1, 44, 'Palayad', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2131, 1, 44, 'Paluvai', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2132, 1, 44, 'Pappinisseri', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2133, 1, 44, 'Pathiriyad', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2134, 1, 44, 'Payyannur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2135, 1, 44, 'Peringathur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2136, 1, 44, 'Perumbaikad', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2137, 1, 44, 'Perumbavoor', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2138, 1, 44, 'Pottore', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2139, 1, 44, 'Punalur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2140, 1, 44, 'Puranattukara', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2141, 1, 44, 'Ramanattukara', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2142, 1, 44, 'Thaikkad', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2143, 1, 44, 'Thiruvankulam', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2144, 1, 44, 'Thottada', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2145, 1, 44, 'Udma', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2146, 1, 44, 'Vadakara', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2147, 1, 44, 'Valapattanam', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2148, 1, 44, 'Vallachira', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2149, 1, 44, 'Varam', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2150, 1, 44, 'Varkala', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2151, 1, 44, 'Changanassery', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2152, 1, 45, 'Bishenpur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2153, 1, 45, 'Chandel', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2154, 1, 45, 'Senapati', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2155, 1, 45, 'Churachandpur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2156, 1, 45, 'Imphal', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2157, 1, 45, 'Jiribam', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2158, 1, 45, 'Thoubal', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2159, 1, 45, 'Mayang Imphal', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2160, 1, 45, 'Moirang', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2161, 1, 45, 'Moreh', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2162, 1, 45, 'Nambol', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2163, 1, 45, 'Tamenglong', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2164, 1, 45, 'Ukhrul', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2165, 1, 45, 'Sugnu', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2166, 1, 46, 'Nongstoin', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2167, 1, 46, 'Shillong', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2168, 1, 46, 'Baghmara', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2169, 1, 46, 'Jowai', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2170, 1, 46, 'Tura', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2171, 1, 46, 'Cherrapunjee', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2172, 1, 46, 'Jaiaw', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2173, 1, 46, 'MadanRitting', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2174, 1, 46, 'Williamnagar', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2175, 1, 46, 'Nongpoh', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2176, 1, 46, 'Nongthymmai', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2177, 1, 46, 'Resubelpara', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2178, 1, 47, 'Aizwal', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2179, 1, 47, 'Lungleh', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2180, 1, 47, 'Lawngtlai', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2181, 1, 47, 'Darlawn', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2182, 1, 47, 'Hnahthial', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2183, 1, 47, 'Khawzawl', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2184, 1, 47, 'Saiha', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2185, 1, 47, 'Sherchip', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2186, 1, 47, 'Lunglei', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2187, 1, 47, 'Lungsen', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2188, 1, 47, 'Champhai', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2189, 1, 47, 'Sairang', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2190, 1, 47, 'Saitual', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2191, 1, 47, 'Serchhip', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2192, 1, 47, 'Kolasib', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2193, 1, 47, 'Vairengte', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2194, 1, 48, 'Zunehboto', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2195, 1, 48, 'Kohima', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2196, 1, 48, 'Mokokchung', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2197, 1, 48, 'Phek', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2198, 1, 48, 'Chukitong', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2199, 1, 48, 'Chumukhedima', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2200, 1, 48, 'Dimapur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2201, 1, 48, 'Tuengsang', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2202, 1, 48, ' Mon', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2203, 1, 48, ' Wokha', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2204, 1, 49, 'Pondicherry', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2205, 1, 49, 'Karaikal', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2206, 1, 49, 'Mahe', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2207, 1, 49, 'Yanam', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2208, 1, 50, 'Ludhiana', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2209, 1, 50, 'Abohar', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2210, 1, 50, 'Ferozepur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2211, 1, 50, 'Gurdaspur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2212, 1, 50, 'Jalandhar', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2213, 1, 50, 'Ahmedgarh', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2214, 1, 50, 'Ajnala', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2215, 1, 50, 'Hoshiarpur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2216, 1, 50, 'Akalgarh', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2217, 1, 50, 'Faridkot', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2218, 1, 50, 'Alawalpur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2219, 1, 50, 'Sangrur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2220, 1, 50, 'Amloh', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2221, 1, 50, 'Amritsar', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2222, 1, 50, 'Patiala', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2223, 1, 50, 'Anandpur Sahib', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2224, 1, 50, 'Badhni Kalan', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2225, 1, 50, 'Bhatinda', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2226, 1, 50, 'Banga', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2227, 1, 50, 'Bariwala', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2228, 1, 50, 'Barnala', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2229, 1, 50, 'Fatehgarh Sahib', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2230, 1, 50, 'Batala', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2231, 1, 50, 'Kapurthala', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2232, 1, 50, 'Begowal', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2233, 1, 50, 'Rupnagar', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2234, 1, 50, 'Bhadaur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2235, 1, 50, 'Bhawanigarh', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2236, 1, 50, 'Bhikhi', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2237, 1, 50, 'Bhikhiwind', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2238, 1, 50, 'Budhlada', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2239, 1, 50, 'Cheema', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2240, 1, 50, 'Nawanshahr', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2241, 1, 50, 'Dera Baba Nanak', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2242, 1, 50, 'Dhanaula', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2243, 1, 50, 'Dharamkot', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2244, 1, 50, 'Dhariwal', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2245, 1, 50, 'Dhilwan', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2246, 1, 50, 'Dhuri', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2247, 1, 50, 'Dina Nagar', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2248, 1, 50, 'Dirba', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2249, 1, 50, 'Fatehgarh Churian', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2250, 1, 50, 'Ghagga', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2251, 1, 50, 'Ghanaur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2252, 1, 50, 'Gidderbaha', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2253, 1, 50, 'Goraya', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2254, 1, 50, 'Hajipur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2255, 1, 50, 'Hariana', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2256, 1, 50, 'Jaitu', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2257, 1, 50, 'Jalalabad', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2258, 1, 50, 'Jandiala', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2259, 1, 50, 'Kalanaur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2260, 1, 50, 'Kartarpur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2261, 1, 50, 'Kharar', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2262, 1, 50, 'Khem Karan', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2263, 1, 50, 'Kurali', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2264, 1, 50, 'Lehragaga', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2265, 1, 50, 'Longowal', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2266, 1, 50, 'Machhiwara', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2267, 1, 50, 'Mahilpur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2268, 1, 50, 'Majitha', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2269, 1, 50, 'Makhu', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2270, 1, 50, 'Malerkotla', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2271, 1, 50, 'Malout', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2272, 1, 50, 'Mansa', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2273, 1, 50, 'Maur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2274, 1, 50, 'Moga', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2275, 1, 50, 'Morinda', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2276, 1, 50, 'Mukerian', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2277, 1, 50, 'Muktsar', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2278, 1, 50, 'Nabha', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2279, 1, 50, 'Nakodar', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2280, 1, 50, 'Nangal', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2281, 1, 50, 'Pathankot', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2282, 1, 50, 'Patti', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2283, 1, 50, 'Payal', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2284, 1, 50, 'Phagwara', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2285, 1, 50, 'Phillaur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2286, 1, 50, 'Qadian', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2287, 1, 50, 'Rahon', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2288, 1, 50, 'Raikot', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2289, 1, 50, 'Rajpura', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2290, 1, 50, 'Raman', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2291, 1, 50, 'Rampura Phul', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2292, 1, 50, 'Samana', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2293, 1, 50, 'Samrala', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2294, 1, 50, 'Sanaur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2295, 1, 50, 'Sangat', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2296, 1, 50, 'Sardulgarh', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2297, 1, 50, 'Shahkot', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2298, 1, 50, 'Sri Hargobindpur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2299, 1, 50, 'Sujanpur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2300, 1, 50, 'Sultanpur Lodhi', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2301, 1, 50, 'Sunam', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2302, 1, 50, 'Talwandi Bhai', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2303, 1, 50, 'Zira', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2304, 1, 51, 'Jaipur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2305, 1, 51, 'Jodhpur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2306, 1, 51, 'Sikar', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2307, 1, 51, 'Sirohi', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2308, 1, 51, 'Ajmer', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2309, 1, 51, 'Churu', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2310, 1, 51, 'JhunJhunu', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2311, 1, 51, 'Bhilwara', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2312, 1, 51, 'Jalore', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2313, 1, 51, 'Alwar', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2314, 1, 51, 'Bharatpur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2315, 1, 51, 'Aklera', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2316, 1, 51, 'Chittorgarh', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2317, 1, 51, 'Nagaur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2318, 1, 51, 'Kota', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2319, 1, 51, 'Tonk', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2320, 1, 51, 'Dausa', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2321, 1, 51, 'Dholpur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2322, 1, 51, 'Pali', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2323, 1, 51, 'Dungarpur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2324, 1, 51, 'Anupgarh', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2325, 1, 51, 'Banswara', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2326, 1, 51, 'Barmer', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2327, 1, 51, 'Udaipur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2328, 1, 51, 'Asind', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2329, 1, 51, 'Jhalawar', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2330, 1, 51, 'Bundi', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2331, 1, 51, 'Bagru', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2332, 1, 51, 'Ganganagar', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2333, 1, 51, 'Swaimadhopur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2334, 1, 51, 'Bikaner', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2335, 1, 51, 'Bakani', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2336, 1, 51, 'Bali', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2337, 1, 51, 'Balotra', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2338, 1, 51, 'Bandikui', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2339, 1, 51, 'Rajsamand', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2340, 1, 51, 'Bari Sadri', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2341, 1, 51, 'Bayana', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2342, 1, 51, 'Beawar', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2343, 1, 51, 'Begun', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2344, 1, 51, 'Jaisalmer', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2345, 1, 51, 'Bhawani Mandi', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2346, 1, 51, 'Bhinder', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2347, 1, 51, 'Bhusawar', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2348, 1, 51, 'Bidasar', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2349, 1, 51, 'Bilara', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2350, 1, 51, 'Bissau', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2351, 1, 51, 'Baran', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2352, 1, 51, 'Chaksu', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2353, 1, 51, 'Chechat', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2354, 1, 51, 'Chhabra', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2355, 1, 51, 'Chhapar', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2356, 1, 51, 'Chhoti Sadri', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2357, 1, 51, 'Chirawa', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2358, 1, 51, 'Chomu', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2359, 1, 51, 'Deeg', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2360, 1, 51, 'Deoli', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2361, 1, 51, 'Dhariawad', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2362, 1, 51, 'Karauli', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2363, 1, 51, 'Didwana', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2364, 1, 51, 'Gajsinghpur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2365, 1, 51, 'Galiakot', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2366, 1, 51, 'Gulabpura', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2367, 1, 51, 'Hindaun', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2368, 1, 51, 'Jahazpur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2369, 1, 51, 'Jobner', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2370, 1, 51, 'Kaman', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2371, 1, 51, 'Kapasan', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2372, 1, 51, 'Karanpur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2373, 1, 51, 'Kekri', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2374, 1, 51, 'Keshoraipatan', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2375, 1, 51, 'Khairthal', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2376, 1, 51, 'Khandela', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2377, 1, 51, 'Kherli', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2378, 1, 51, 'Khetri', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2379, 1, 51, 'Kishangarh', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2380, 1, 51, 'Kishangarh Renwal', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2381, 1, 51, 'Kotputli', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2382, 1, 51, 'Kuchera', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2383, 1, 51, 'Kumher', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2384, 1, 51, 'Kushalgarh', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2385, 1, 51, 'Lakheri', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2386, 1, 51, 'Lalsot', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2387, 1, 51, 'Losal', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2388, 1, 51, 'Mahu Kalan', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2389, 1, 51, 'Mahwa', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2390, 1, 51, 'Makrana', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2391, 1, 51, 'Malpura', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2392, 1, 51, 'Mandalgarh', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2393, 1, 51, 'Mandawa', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2394, 1, 51, 'Manohar Thana', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2395, 1, 51, 'Merta City', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2396, 1, 51, 'Mukandgarh', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2397, 1, 51, 'Nadbai', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2398, 1, 51, 'Nasirabad', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2399, 1, 51, 'Nathdwara', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2400, 1, 51, 'Nawalgarh', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2401, 1, 51, 'Nimbahera', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2402, 1, 51, 'Nohar', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2403, 1, 51, 'Hanumangarh', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2404, 1, 51, 'Nokha', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2405, 1, 51, 'Padampur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2406, 1, 51, 'Parbatsar', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2407, 1, 51, 'Phalodi', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2408, 1, 51, 'Pilani', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2409, 1, 51, 'Pilibanga', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2410, 1, 51, 'Pindwara', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2411, 1, 51, 'Pirawa', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2412, 1, 51, 'Pokaran', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2413, 1, 51, 'Pushkar', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2414, 1, 51, 'Raisinghnagar', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2415, 1, 51, 'Rajakhera', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2416, 1, 51, 'Rajaldesar', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2417, 1, 51, 'Ramganj Mandi', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2418, 1, 51, 'Rani', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2419, 1, 51, 'Ratangarh', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2420, 1, 51, 'Ratannagar', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2421, 1, 51, 'Sadri', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2422, 1, 51, 'Sadulshahar', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2423, 1, 51, 'Sagwara', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2424, 1, 51, 'Salumbar', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2425, 1, 51, 'Sanchore', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2426, 1, 51, 'Sangod', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2427, 1, 51, 'Sardarshahar', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2428, 1, 51, 'Sarwar', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2429, 1, 51, 'Shahpura', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2430, 1, 51, 'Sheoganj', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2431, 1, 51, 'Sri Madhopur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2432, 1, 51, 'Sujangarh', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2433, 1, 51, 'Suket', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2434, 1, 51, 'Sumerpur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2435, 1, 51, 'Surajgarh', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2436, 1, 51, 'Suratgarh', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2437, 1, 51, 'Taranagar', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2438, 1, 51, 'Tijara', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2439, 1, 51, 'Udaipurwati', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2440, 1, 51, 'Uniara', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2441, 1, 51, 'Viratnagar', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2442, 1, 51, 'Weir', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2443, 1, 52, 'Gyalshing', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2444, 1, 52, 'Mangan', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2445, 1, 52, 'Gangtok', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2446, 1, 52, 'Namchi', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2447, 1, 52, 'Nayabazar', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2448, 1, 52, 'Rangpo', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2449, 1, 52, 'Singtam', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2450, 1, 53, 'Vellore', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2451, 1, 53, 'Abiramam', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2452, 1, 53, 'Chennai', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2453, 1, 53, 'Thanjavur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2454, 1, 53, 'Cuddalore', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2455, 1, 53, 'Tirunelveli', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2456, 1, 53, 'Acharapakkam', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2457, 1, 53, 'Tiruvallur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2458, 1, 53, 'Ramanathapuram', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2459, 1, 53, 'Pudukottai', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2460, 1, 53, 'Sivaganga', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2461, 1, 53, 'Tiruchirappalli', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2462, 1, 53, 'Perambalur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2463, 1, 53, 'Dharmapuri', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2464, 1, 53, 'Kanyakumari', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2465, 1, 53, 'Dindigul', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2466, 1, 53, 'Coimbatore', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2467, 1, 53, 'Madurai', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2468, 1, 53, 'Salem', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2469, 1, 53, 'Virudhunagar', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2470, 1, 53, 'Alandur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2471, 1, 53, 'Alanganallur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2472, 1, 53, 'Alangayam', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2473, 1, 53, 'Alangudi', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2474, 1, 53, 'Alangulam', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2475, 1, 53, 'Alapakkam', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2476, 1, 53, 'Ambasamudram', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2477, 1, 53, 'Ambur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2478, 1, 53, 'Karur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2479, 1, 53, 'Ammavarikuppam', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2480, 1, 53, 'Ammoor', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2481, 1, 53, 'Viluppuram', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2482, 1, 53, 'Anaiyur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2483, 1, 53, 'Anakaputhur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2484, 1, 53, 'Ariyalur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2485, 1, 53, 'Theni', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2486, 1, 53, 'Annavasal', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2487, 1, 53, 'Anthiyur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2488, 1, 53, 'Arakandanallur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2489, 1, 53, 'Nagapattinam', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2490, 1, 53, 'Aravakurichi', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2491, 1, 53, 'Uthagamandalam', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2492, 1, 53, 'Arimalam', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2493, 1, 53, 'Ariyur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2494, 1, 53, 'Arumbavur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2495, 1, 53, 'Arumuganeri', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2496, 1, 53, 'Athani', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2497, 1, 53, 'Athanur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2498, 1, 53, 'Attayampatti', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2499, 1, 53, 'Attur ', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2500, 1, 53, 'Avadi', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2501, 1, 53, 'Avanashi', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2502, 1, 53, 'Avaniapuram', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2503, 1, 53, 'Ayakudi', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2504, 1, 53, 'Ayyalur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2505, 1, 53, 'Ayyampalayam', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2506, 1, 53, 'Ayyampettai', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2507, 1, 53, 'Balasamudram', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2508, 1, 53, 'Batlagundu', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2509, 1, 53, 'Erode', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2510, 1, 53, 'Bhavanisagar', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2511, 1, 53, 'Bhuvanagiri', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2512, 1, 53, 'Kancheepuram', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2513, 1, 53, 'Chengalpattu', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2514, 1, 53, 'Chengam', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2515, 1, 53, 'Chennasamudram', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2516, 1, 53, 'Chennimalai', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2517, 1, 53, 'Thoothukkudi', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2518, 1, 53, 'Chettipalayam', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2519, 1, 53, 'Chidambaram', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2520, 1, 53, 'Chinnalapatti', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2521, 1, 53, 'Chinnamanur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2522, 1, 53, 'Chinnasalem', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2523, 1, 53, 'Chitlapakkam', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2524, 1, 53, 'Desur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2525, 1, 53, 'Devadanapatti', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2526, 1, 53, 'Devarshola', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2527, 1, 53, 'Dusi', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2528, 1, 53, 'Elathur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2529, 1, 53, 'Elayirampannai', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2530, 1, 53, 'Eral', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2531, 1, 53, 'Ganapathipuram', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2532, 1, 53, 'Gangaikondan', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2533, 1, 53, 'Gangavalli', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2534, 1, 53, 'Gingee', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2535, 1, 53, 'Harur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2536, 1, 53, 'Highways', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2537, 1, 53, 'Hosur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2538, 1, 53, 'Ilanji', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2539, 1, 53, 'Irugur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2540, 1, 53, 'Jagathala', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2541, 1, 53, 'Jambai', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2542, 1, 53, 'Thiruvallur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2543, 1, 53, 'Kadambur ', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2544, 1, 53, 'Kadathur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2545, 1, 53, 'Kadayanallur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2546, 1, 53, 'Kalambur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2547, 1, 53, 'Kalapatti', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2548, 1, 53, 'Kalappanaickenpatti', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2549, 1, 53, 'Kalavai', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2550, 1, 53, 'Kallakudi', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2551, 1, 53, 'Kalugumalai', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2552, 1, 53, 'Kamayagoundanpatti', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2553, 1, 53, 'Kambainallur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2554, 1, 53, 'Kanadukathan', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2555, 1, 53, 'Kanam', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2556, 1, 53, 'Kandanur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2557, 1, 53, 'Kangayampalayam', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2558, 1, 53, 'Kaniyur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2559, 1, 53, 'Kanjikoil', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2560, 1, 53, 'Kannamangalam', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2561, 1, 53, 'Kannampalayam', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2562, 1, 53, 'Kannankurichi', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2563, 1, 53, 'Kannivadi', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2564, 1, 53, 'Karamadai', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2565, 1, 53, 'Karuppur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2566, 1, 53, 'Katpadi', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2567, 1, 53, 'Kattumannarkoil', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2568, 1, 53, 'Keeramangalam', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2569, 1, 53, 'Killiyur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2570, 1, 53, 'Kilpennathur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2571, 1, 53, 'Kilvelur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2572, 1, 53, 'Kinathukadavu', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2573, 1, 53, 'Kodaikanal', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2574, 1, 53, 'Kodavasal', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2575, 1, 53, 'Kodumudi', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2576, 1, 53, 'Kolathupalayam', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2577, 1, 53, 'Kolathur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2578, 1, 53, 'Komaralingam', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2579, 1, 53, 'Kombai', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2580, 1, 53, 'Konavattam', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2581, 1, 53, 'Kondalampatti', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2582, 1, 53, 'Konganapuram', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2583, 1, 53, 'Koradacheri', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2584, 1, 53, 'Kotagiri', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2585, 1, 53, 'Kottaiyur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2586, 1, 53, 'Kottakuppam', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2587, 1, 53, 'Kottaram', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2588, 1, 53, 'Kottur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2589, 1, 53, 'Kovilpalayam', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2590, 1, 53, 'Kovilpatti', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2591, 1, 53, 'Krishnagiri ', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2592, 1, 53, 'Krishnarayapuram', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2593, 1, 53, 'Kulasekarapuram', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2594, 1, 53, 'Kuniyamuthur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2595, 1, 53, 'Kurinjipadi', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2596, 1, 53, 'Kurudampalayam', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2597, 1, 53, 'Kurumbalur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2598, 1, 53, 'Thiruvarur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2599, 1, 53, 'Labbaikudikadu', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2600, 1, 53, 'Lalgudi', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2601, 1, 53, 'Lalpet', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2602, 1, 53, 'Madathukulam', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2603, 1, 53, 'Madukkarai', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2604, 1, 53, 'Madukkur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2605, 1, 53, 'Maduravoyal', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2606, 1, 53, 'Mallasamudram', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2607, 1, 53, 'Mamallapuram', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2608, 1, 53, 'Manachanallur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2609, 1, 53, 'Manali', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2610, 1, 53, 'Manalmedu', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2611, 1, 53, 'Manalurpet', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2612, 1, 53, 'Manavalakurichi', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2613, 1, 53, 'Mandapam', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2614, 1, 53, 'Mangadu', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2615, 1, 53, 'Mangalam ', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2616, 1, 53, 'Maraimalainagar', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2617, 1, 53, 'Marandahalli', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2618, 1, 53, 'Marudur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2619, 1, 53, 'Mecheri', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2620, 1, 53, 'Melagaram', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2621, 1, 53, 'Melattur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2622, 1, 53, 'Mettupalayam', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2623, 1, 53, 'Mettur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00');
INSERT INTO `City` (`cityId`, `countryId`, `stateId`, `cityName`, `allowModi`, `userId`, `dateCreated`, `userIdUpdate`, `dateUpdate`) VALUES
(2624, 1, 53, 'Minjur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2625, 1, 53, 'Modakurichi', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2626, 1, 53, 'Mohanur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2627, 1, 53, 'Mudukulathur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2628, 1, 53, 'Mukkudal', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2629, 1, 53, 'Mulanur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2630, 1, 53, 'Muthur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2631, 1, 53, 'Naduvattam', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2632, 1, 53, 'Nallur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2633, 1, 53, 'Nambiyur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2634, 1, 53, 'Nangavalli', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2635, 1, 53, 'Nanguneri', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2636, 1, 53, 'Nannilam', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2637, 1, 53, 'Naranapuram', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2638, 1, 53, 'Narasingapuram', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2639, 1, 53, 'Nasiyanur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2640, 1, 53, 'Nattarasankottai', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2641, 1, 53, 'Nellikuppam', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2642, 1, 53, 'Nerkuppai', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2643, 1, 53, 'Neyveli ', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2644, 1, 53, 'Oddanchatram', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2645, 1, 53, 'Olagadam', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2646, 1, 53, 'Omalur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2647, 1, 53, 'Othakadai', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2648, 1, 53, 'Othakalmandapam', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2649, 1, 53, 'Pacode', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2650, 1, 53, 'Padaiveedu', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2651, 1, 53, 'Padianallur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2652, 1, 53, 'Padmanabhapuram', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2653, 1, 53, 'Palavakkam', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2654, 1, 53, 'Palayam', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2655, 1, 53, 'Palladam', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2656, 1, 53, 'Pallapatti', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2657, 1, 53, 'Pallathur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2658, 1, 53, 'Pallikonda', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2659, 1, 53, 'Pallipattu', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2660, 1, 53, 'Pammal', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2661, 1, 53, 'Panagudi', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2662, 1, 53, 'Panapakkam', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2663, 1, 53, 'Pannaikadu', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2664, 1, 53, 'Pannaipuram', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2665, 1, 53, 'Panruti', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2666, 1, 53, 'Pappireddipatti', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2667, 1, 53, 'Paramathi', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2668, 1, 53, 'Parangipettai', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2669, 1, 53, 'Paravai', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2670, 1, 53, 'Pasur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2671, 1, 53, 'Pennadam', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2672, 1, 53, 'Pennagaram', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2673, 1, 53, 'Pennathur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2674, 1, 53, 'Peraiyur ', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2675, 1, 53, 'Peralam', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2676, 1, 53, 'Periyakulam', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2677, 1, 53, 'Perundurai', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2678, 1, 53, 'Perungudi', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2679, 1, 53, 'Perungulam', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2680, 1, 53, 'Perur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2681, 1, 53, 'Pollachi', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2682, 1, 53, 'Polichalur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2683, 1, 53, 'Polur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2684, 1, 53, 'Tiruvannamalai', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2685, 1, 53, 'Ponneri', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2686, 1, 53, 'Poolambadi', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2687, 1, 53, 'Poolampatti', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2688, 1, 53, 'Poonamallee', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2689, 1, 53, 'Porur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2690, 1, 53, 'Pudukkottai', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2691, 1, 53, 'Pudupalayam', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2692, 1, 53, 'Pudupatti', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2693, 1, 53, 'Puthalam', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2694, 1, 53, 'Puvalur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2695, 1, 53, 'Rajapalayam', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2696, 1, 53, 'Ramapuram', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2697, 1, 53, 'Rameswaram', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2698, 1, 53, 'Namakkal', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2699, 1, 53, 'Rasipuram', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2700, 1, 53, 'Samathur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2701, 1, 53, 'Sankarapuram', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2702, 1, 53, 'Sattur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2703, 1, 53, 'Sayalgudi', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2704, 1, 53, 'Seithur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2705, 1, 53, 'Sembakkam', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2706, 1, 53, 'Sevugampatti', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2707, 1, 53, 'Shenbakkam', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2708, 1, 53, 'Sholur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2709, 1, 53, 'Singampuneri', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2710, 1, 53, 'Sirumugai', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2711, 1, 53, 'Sithayankottai', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2712, 1, 53, 'Sivagiri', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2713, 1, 53, 'Srimushnam', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2714, 1, 53, 'Sriperumbudur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2715, 1, 53, 'Suchindram', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2716, 1, 53, 'Sundarapandiam', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2717, 1, 53, 'Surampatti', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2718, 1, 53, 'Surandai', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2719, 1, 53, 'Tambaram ', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2720, 1, 53, 'Thamaraikulam', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2721, 1, 53, 'Thammampatti', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2722, 1, 53, 'Tharamangalam', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2723, 1, 53, 'Thenthiruperai', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2724, 1, 53, 'Thorapadi', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2725, 1, 53, 'Thuthipattu', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2726, 1, 53, 'Timiri', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2727, 1, 53, 'Tiruchendur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2728, 1, 53, 'Tiruttani', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2729, 1, 53, 'Tiruverkadu', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2730, 1, 53, 'Tiruvottiyur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2731, 1, 53, 'Udayarpalayam', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2732, 1, 53, 'Uppidamangalam', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2733, 1, 53, 'Uthamapalayam', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2734, 1, 53, 'Uthiramerur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2735, 1, 53, 'Vadamadurai', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2736, 1, 53, 'Vadipatti', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2737, 1, 53, 'Vadugapatti', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2738, 1, 53, 'Vallam ', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2739, 1, 53, 'Valparai', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2740, 1, 53, 'Vandalur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2741, 1, 53, 'Vandavasi', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2742, 1, 53, 'Vaniyambadi', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2743, 1, 53, 'Vasudevanallur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2744, 1, 53, 'Vedasandur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2745, 1, 53, 'Veerapandi', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2746, 1, 53, 'Vellakoil', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2747, 1, 53, 'Vellalur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2748, 1, 53, 'Velur ', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2749, 1, 53, 'Veppathur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2750, 1, 53, 'Vettaikaranpudur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2751, 1, 53, 'Vettavalam', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2752, 1, 53, 'Vikramasingapuram', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2753, 1, 53, 'Vikravandi', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2754, 1, 53, 'Vilapakkam', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2755, 1, 53, 'Vilathikulam', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2756, 1, 53, 'Villukuri', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2757, 1, 53, 'Viswanatham', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2758, 1, 53, 'Walajabad', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2759, 1, 53, 'Walajapet', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2760, 1, 53, 'Wellington', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2761, 1, 54, 'Agartala', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2762, 1, 54, 'Kailashahar', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2763, 1, 54, ' Amarpur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2764, 1, 54, 'Udaipur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2765, 1, 54, ' Badarghat', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2766, 1, 54, 'Beloniya', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2767, 1, 54, ' Dharma Nagar', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2768, 1, 54, ' Jogendranagar', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2769, 1, 54, ' Kamalpur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2770, 1, 54, ' Kanchanpur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2771, 1, 54, ' Khowai', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2772, 1, 54, ' Kumarghat', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2773, 1, 54, ' Kunjaban', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2774, 1, 54, ' Ranirbazar', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2775, 1, 54, ' Sabroom', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2776, 1, 54, 'Ambassa', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2777, 1, 54, ' Teliamura', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2778, 1, 55, 'Pithoragarh', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2779, 1, 55, 'Adheriya Khal', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2780, 1, 55, 'Adibadri', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2781, 1, 55, 'Agustmuni', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2782, 1, 55, 'Dehradun', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2783, 1, 55, 'Almora', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2784, 1, 55, 'Gopeshwar', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2785, 1, 55, 'Badrinath', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2786, 1, 55, 'Bageshwar', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2787, 1, 55, 'Pauri', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2788, 1, 55, 'Nainital', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2789, 1, 55, 'Bhimtal', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2790, 1, 55, 'Bhirgukhal', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2791, 1, 55, 'Bhowali', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2792, 1, 55, 'Uttarkashi', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2793, 1, 55, 'Chakisain', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2794, 1, 55, 'Chakrata ', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2795, 1, 55, 'Chamoli', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2796, 1, 55, 'Champawat', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2797, 1, 55, 'Chelusain', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2798, 1, 55, 'Chipalghat', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2799, 1, 55, 'Dehal Chauri', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2800, 1, 55, 'Dev Rajkhal', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2801, 1, 55, 'Devidhura', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2802, 1, 55, 'Devprayag', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2803, 1, 55, 'Dharasoo', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2804, 1, 55, 'Didihat', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2805, 1, 55, 'Dineshpur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2806, 1, 55, 'Doiwala', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2807, 1, 55, 'Dugadd', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2808, 1, 55, 'Dukharkhal', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2809, 1, 55, 'Dwarahat', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2810, 1, 55, 'Dwarikhal', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2811, 1, 55, 'Ekeshwar', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2812, 1, 55, 'Gadarpur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2813, 1, 55, 'Gairsain', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2814, 1, 55, 'Gauchar', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2815, 1, 55, 'Gokulnagar', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2816, 1, 55, 'Guptakashi', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2817, 1, 55, 'Gwaldam', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2818, 1, 55, 'Haldwani', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2819, 1, 55, 'Herbertpur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2820, 1, 55, 'Jakhani', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2821, 1, 55, 'Jaspur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2822, 1, 55, 'Joshimath', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2823, 1, 55, 'Kaladhungi', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2824, 1, 55, 'Kalagarh', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2825, 1, 55, 'Kaljikhal', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2826, 1, 55, 'Kanda Khal', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2827, 1, 55, 'Kanskhet', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2828, 1, 55, 'Karan Prayag', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2829, 1, 55, 'Kashipur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2830, 1, 55, 'Kausani', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2831, 1, 55, 'Khatima', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2832, 1, 55, 'Khirshn', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2833, 1, 55, 'Kichha', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2834, 1, 55, 'Kotdwar', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2835, 1, 55, 'Kotdwara', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2836, 1, 55, 'Lansdowne', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2837, 1, 55, 'Lohaghat', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2838, 1, 55, 'Rudra Prayag', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2839, 1, 55, 'Maithana', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2840, 1, 55, 'Mawadhar', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2841, 1, 55, 'Munsiari', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2842, 1, 55, 'Mussoorie', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2843, 1, 55, 'Nainidada', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2844, 1, 55, 'Naithana', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2845, 1, 55, 'Nand Prayag', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2846, 1, 55, 'Naugaon Khal', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2847, 1, 55, 'Okhimath', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2848, 1, 55, 'Pabua', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2849, 1, 55, 'Paidul', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2850, 1, 55, 'Pant Nagar', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2851, 1, 55, 'Parsundakhal', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2852, 1, 55, 'Pattisain', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2853, 1, 55, 'Paukhal', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2854, 1, 55, 'Pipalkoti', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2855, 1, 55, 'Porhra', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2856, 1, 55, 'Rudrapur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2857, 1, 55, 'Raipur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2858, 1, 55, 'Raiwala', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2859, 1, 55, 'Ram Nagar', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2860, 1, 55, 'Ramgarh', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2861, 1, 55, 'Rikhani Khal', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2862, 1, 55, 'Rishikesh', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2863, 1, 55, 'Rithakhal', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2864, 1, 55, 'Roorkee', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2865, 1, 55, 'Satpuri', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2866, 1, 55, 'Sillogl', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2867, 1, 55, 'Bughani', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2868, 1, 55, 'Srinagar', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2869, 1, 55, 'Sittarganj', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2870, 1, 55, 'Toli', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2871, 1, 55, 'Topowan', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2872, 1, 55, 'Vedikhal', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2873, 1, 55, 'Vikas Nagar', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2874, 1, 6, 'Sabar Kantha', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2875, 1, 6, 'Kutch', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2876, 1, 6, 'Banas Kantha', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2877, 1, 6, 'Amod', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2878, 1, 6, 'Narmada', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2879, 1, 6, 'Dahod', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2880, 1, 6, 'Dang', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2881, 1, 6, 'Tapi', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2882, 1, 6, 'Panch Mahal', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2883, 1, 17, 'Bhilai', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2884, 1, 1, 'khajuraho', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2885, 1, 1, 'Nagda', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2886, 1, 1, 'Singrauli', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2887, 1, 1, 'Waidhan', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2888, 1, 37, 'Dadra', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2889, 1, 37, 'Naroli', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2890, 1, 6, 'Silvassa', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2891, 1, 37, 'Samarvarni', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2892, 1, 6, 'dadar and nagar haveli', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2893, 1, 6, 'ankleshwar', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2894, 1, 6, 'Chhota Udaipur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2895, 1, 6, 'Daman Gujarat', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2896, 1, 6, 'Morbi', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2897, 1, 6, 'Udalpur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2898, 1, 6, 'Sabarmati', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2899, 1, 6, 'Mahemdavad', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2900, 1, 6, 'Motakarala', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2901, 1, 6, 'madhapar', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2902, 1, 6, 'Gandhigram', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2903, 1, 6, 'Navrangpura', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2904, 1, 6, 'SAMAKHYALI', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2905, 1, 6, 'RATNAL', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2906, 1, 6, 'Nakhatrana', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2907, 1, 6, 'DESAIWADA', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2908, 1, 6, 'daskroi', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2909, 1, 6, 'MIRZAPUR GJ', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2910, 1, 6, 'Umergaon', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2911, 1, 34, 'hassan sagar', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2912, 1, 6, 'sankheda', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2913, 1, 6, 'CHANSMA', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2914, 1, 6, 'VADOLI', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2915, 1, 6, 'Velanga', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2916, 1, 6, 'motera', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2917, 1, 6, 'NASWADI', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2918, 1, 6, 'olpad', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2919, 1, 6, 'CHOKDI', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2920, 1, 6, 'THALTEJ', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2921, 1, 6, 'BAWLA', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2922, 1, 6, 'SARTEJ', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2923, 1, 6, 'NAVJIVAN', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2924, 1, 6, 'DAHGAM', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2925, 1, 6, 'DAHEJ', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2926, 1, 6, 'BODHAN GJ', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2927, 1, 6, 'vaso', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2928, 1, 6, 'BARDOLI', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2929, 1, 6, 'KARJAN', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2930, 1, 6, 'MANJUSAR', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2931, 1, 6, 'limkheda', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2932, 1, 6, 'SOMNATH', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2933, 1, 6, 'Dabholi', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2934, 1, 6, 'VANKA', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2935, 1, 6, 'Godpar', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2936, 1, 6, 'VEMALI', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2937, 1, 6, 'SHERKHI', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2938, 1, 6, 'MAHISAGAR', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2939, 1, 6, 'AMROLI', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2940, 1, 6, 'WAGHODIA', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2941, 1, 6, 'UCHAPAN', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2942, 1, 17, 'Bastar', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2943, 1, 6, 'Chandkheda', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2944, 1, 6, 'JHAGADIA', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2945, 1, 1, 'Gautampura', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2946, 1, 51, 'Bhiwadi', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2947, 1, 1, 'Agar', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2948, 1, 1, 'Amarpatan', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2949, 1, 1, 'Amarwada', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2950, 1, 1, 'Anuppur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2951, 1, 1, 'Arone', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2952, 1, 1, 'Ashta', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2953, 1, 1, 'Atner', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2954, 1, 1, 'Babaichichli', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2955, 1, 1, 'Badamalhera', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2956, 1, 1, 'Badarwsas', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2957, 1, 1, 'Balaghat', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2958, 1, 1, 'Badnagar', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2959, 1, 1, 'Baldeogarh', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2960, 1, 1, 'Baldi', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2961, 1, 1, 'Bamori', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2962, 1, 1, 'Bandhavgarh', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2963, 1, 1, 'Badwani', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2964, 1, 1, 'Barwaha', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2965, 1, 1, 'Batkakhapa', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2966, 1, 1, 'Begamganj', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2967, 1, 1, 'Beohari', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2968, 1, 1, 'Berasia', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2969, 1, 1, 'Chachaura', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2970, 1, 1, 'Chanderi', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2971, 1, 1, 'Chaurai', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2972, 1, 1, 'Chhapara', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2973, 1, 1, 'Chicholi', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2974, 1, 1, 'Chitrangi', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2975, 1, 1, 'Churhat', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2976, 1, 1, 'Deori', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2977, 1, 1, 'Deosar', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2978, 1, 1, 'Ganjbasoda', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2979, 1, 1, 'Garoth', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2980, 1, 1, 'Ghansour', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2981, 1, 1, 'Ghatia', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2982, 1, 1, 'Ghatigaon', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2983, 1, 1, 'Ghorandogri', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2984, 1, 1, 'Ghughari', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2985, 1, 1, 'Gogaon', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2986, 1, 1, 'Goharganj', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2987, 1, 1, 'Gopalganj', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2988, 1, 1, 'Gotegaon', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2989, 1, 1, 'Gourihar', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2990, 1, 1, 'Gunnore', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2991, 1, 1, 'Gyraspur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2992, 1, 1, 'Hanumana', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2993, 1, 1, 'Harrai', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2994, 1, 1, 'Ichhawar', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2995, 1, 1, 'Isagarh', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2996, 1, 1, 'Jabera', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2997, 1, 1, 'Jagdalpur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2998, 1, 1, 'Jaisinghnagar', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2999, 1, 1, 'Jaithari', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(3000, 1, 1, 'Jaitpur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(3001, 1, 1, 'Jaitwara', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(3002, 1, 1, 'Jamai', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(3003, 1, 1, 'Jaora', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(3004, 1, 1, 'Jatara', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(3005, 1, 1, 'Jawad', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(3006, 1, 1, 'Jora', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(3007, 1, 1, 'Kakaiya', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(3008, 1, 1, 'Kannodi', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(3009, 1, 1, 'Karanjia', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(3010, 1, 1, 'Karera', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(3011, 1, 1, 'Karhal', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(3012, 1, 1, 'Karpa', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(3013, 1, 1, 'Kasrawad', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(3014, 1, 1, 'Keolari', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(3015, 1, 1, 'Khachrod', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(3016, 1, 1, 'Khakner', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(3017, 1, 1, 'Khalwa', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(3018, 1, 1, 'Khaniadhana', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(3019, 1, 1, 'Khilchipur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(3020, 1, 1, 'Khirkiya', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(3021, 1, 1, 'Khurai', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(3022, 1, 1, 'Kolaras', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(3023, 1, 1, 'Kotma', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(3024, 1, 1, 'Kundam', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(3025, 1, 1, 'Kurwai', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(3026, 1, 1, 'Kusmi', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(3027, 1, 1, 'Laher', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(3028, 1, 1, 'Lakhnadon', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(3029, 1, 1, 'Lamta', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(3030, 1, 1, 'Lanji', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(3031, 1, 1, 'Lateri', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(3032, 1, 1, 'Maheshwar', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(3033, 1, 1, 'Mahidpurcity', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(3034, 1, 1, 'Maihar', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(3035, 1, 1, 'Majhagwan', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(3036, 1, 1, 'Malhargarh', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(3037, 1, 1, 'Mandsaur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(3038, 1, 1, 'Mawai', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(3039, 1, 1, 'Mhow', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(3040, 1, 1, 'Nagod', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(3041, 1, 1, 'Nainpur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(3042, 1, 1, 'Narsingarh', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(3043, 1, 1, 'Narsinghpur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(3044, 1, 1, 'Narwar', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(3045, 1, 1, 'Nasrullaganj', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(3046, 1, 1, 'Nateran', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(3047, 1, 1, 'Niwari', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(3048, 1, 1, 'Niwas', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(3049, 1, 1, 'Nowgaon', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(3050, 1, 1, 'Pachmarhi', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(3051, 1, 1, 'Pandhurna', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(3052, 1, 1, 'Parasia', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(3053, 1, 1, 'Patera', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(3054, 1, 1, 'Pawai', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(3055, 1, 1, 'Petlawad', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(3056, 1, 1, 'Pichhore', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(3057, 1, 1, 'Piparia', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(3058, 1, 1, 'Pohari', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(3059, 1, 1, 'Prabhapattan', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(3060, 1, 1, 'Punasa', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(3061, 1, 1, 'Pushprajgarh', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(3062, 1, 1, 'Raghogarh', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(3063, 1, 1, 'Raghunathpur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(3064, 1, 1, 'Rahatgarh', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(3065, 1, 1, 'Rehli', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(3066, 1, 1, 'Sanwer', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(3067, 1, 1, 'Saunsar', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(3068, 1, 1, 'Seondha', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(3069, 1, 1, 'Seonimalwa', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(3070, 1, 1, 'Shahnagar', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(3071, 1, 1, 'Shahpur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(3072, 1, 1, 'Sheopur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(3073, 1, 1, 'Sheopurkalan', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(3074, 1, 1, 'Shujalpur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(3075, 1, 1, 'Silwani', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(3076, 1, 1, 'Sirmour', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(3077, 1, 1, 'Sironj ', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(3078, 1, 1, 'Sitamau', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(3079, 1, 1, 'Sondhwa', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(3080, 1, 1, 'Sonkatch', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(3081, 1, 1, 'Susner', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(3082, 1, 1, 'Tamia', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(3083, 1, 1, 'Tarana', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(3084, 1, 1, 'Tendukheda', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(3085, 1, 1, 'Teonthar', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(3086, 1, 1, 'Timarani', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(3087, 1, 1, 'Umariapan', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(3088, 1, 1, 'Vijayraghogarh', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(3089, 1, 1, 'Waraseoni', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(3090, 1, 1, 'Zhirnia', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(3091, 1, 6, 'Gir Somnath', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(3092, 1, 17, 'Bishrampur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(3093, 1, 51, 'Bilouchi', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(3094, 1, 51, 'Paota', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(3095, 1, 6, 'Lodhika', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(3096, 1, 6, 'Gandhidham', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(3097, 1, 17, 'fingeshwar', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(3098, 1, 17, 'gariaband', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(3099, 1, 17, 'Lawan', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(3100, 1, 51, 'Falna', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(3101, 1, 51, 'Rawatbhata', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(3102, 1, 6, 'Bahiyal Ganghinagar', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(3103, 1, 23, 'GONDIA', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(3104, 1, 6, 'Utavali', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(3105, 1, 6, 'jambusar', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(3106, 1, 23, 'Bhivapur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(3107, 1, 23, 'Kalmeshwar', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(3108, 1, 23, 'Kampthee', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(3109, 1, 23, 'Kuhi', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(3110, 1, 23, 'Narkhed', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(3111, 1, 23, 'Parseoni', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(3112, 1, 23, 'Saoner', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(3113, 1, 23, 'Hingna', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(3114, 1, 23, 'Mouda', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(3115, 1, 23, 'AMRAVATI', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(3116, 1, 51, 'FATEHPURA', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(3117, 1, 23, 'VASAI', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(3118, 1, 51, 'Sayala', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(3119, 1, 6, 'JANKHVAV', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(3120, 1, 6, 'BAGASARA', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(3121, 1, 17, 'Sukma', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(3122, 1, 27, 'BOUDH', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(3123, 1, 27, 'DEOGARH', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(3124, 1, 27, 'Rourkela', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(3125, 1, 27, 'KALASANBI', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(3126, 1, 27, 'DHARAMGARH', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(3127, 1, 17, 'sarguja', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(3128, 1, 27, 'baliguda', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(3129, 1, 17, 'Bhairmgarh', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(3130, 1, 51, 'Basni', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(3131, 1, 51, 'Bapini', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(3132, 1, 17, 'BeejaPur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(3133, 1, 51, 'Behror', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(3134, 1, 23, 'SINDHUDURGH', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(3135, 1, 51, 'BERRU', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(3136, 1, 17, 'Jhagrakhand', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(3137, 1, 17, 'balod', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(3138, 1, 51, 'KOTRA', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(3139, 1, 6, 'Vad nagar', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(3140, 1, 38, 'Diu', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(3141, 1, 6, 'sojitra', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(3142, 1, 53, 'TIRUPUR', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(3143, 1, 23, 'Karad', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(3144, 1, 23, 'Chiplun', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(3145, 1, 23, 'Radhanagari', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(3146, 1, 34, 'Kadpa', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(3147, 1, 56, 'VIKARABAD', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(3148, 1, 6, 'Gariydhar', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(3149, 1, 6, 'Sathamba', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(3150, 1, 56, 'Bhadradri', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(3151, 1, 56, 'Bhupalpalle', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(3152, 1, 56, 'Jogulamba', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(3153, 1, 56, 'Komaram Bheem', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(3154, 1, 56, 'Mahabubabad', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(3155, 1, 56, 'Mahabubnagar', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(3156, 1, 56, 'Mancherial', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(3157, 1, 56, 'Medchal', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(3158, 1, 56, 'Nagarkurnool', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(3159, 1, 56, 'Peddapalli', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(3160, 1, 56, 'Sircilla', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(3161, 1, 56, 'Shamshabad', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(3162, 1, 56, 'Wanaparthy', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(3163, 1, 56, 'Warangal Urban', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(3164, 1, 7, 'Koramangala', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(3165, 1, 6, 'Vayor', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(3166, 1, 10, 'VAISHALI', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(3167, 1, 10, 'WEST CHAMPARAN', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(3168, 1, 10, 'EAST CHAMPARAN', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(3169, 1, 10, 'BHOJPUR', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(3170, 1, 23, 'Palghar', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(3171, 1, 10, 'Nalanda', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(3172, 1, 10, 'Arwal', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(3173, 1, 23, 'Boisar', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(3174, 1, 23, 'Talasari', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(3175, 1, 23, 'Gulvanch', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(3176, 1, 10, 'Rohtas', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(3177, 1, 10, 'Champaran', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(3178, 1, 34, 'VIJAYNAGARAM', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(3179, 1, 1, 'Bidwar', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(3180, 1, 23, 'Raigad', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(3181, 1, 10, 'Fatuha', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(3182, 1, 13, 'Corlim', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(3183, 1, 23, 'kalher', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(3198, 1, 53, 'Krishangiri', 'N', 1, NULL, NULL, NULL),
(3204, 1, 2, 'Noida', '1', 1, NULL, 1, NULL),
(3205, 1, 41, 'Jammu', NULL, 1, NULL, 1, NULL),
(3206, 1, 50, 'Chandigarh', NULL, 1, NULL, 1, NULL),
(3207, 1, 23, 'ghatkopar', NULL, 1, NULL, 1, NULL);

-- --------------------------------------------------------

--
-- Table structure for table `Clients`
--

CREATE TABLE `Clients` (
  `clientId` int(11) NOT NULL,
  `clientName` varchar(50) NOT NULL,
  `addressId` int(11) NOT NULL,
  `concernPerson` varchar(50) DEFAULT NULL,
  `gstNo` varchar(50) DEFAULT NULL,
  `effectiveStartDate` datetime DEFAULT NULL,
  `effectiveEndDate` datetime DEFAULT NULL,
  `activeStatus` varchar(2) DEFAULT NULL,
  `companyId` int(11) NOT NULL,
  `groupId` int(11) DEFAULT NULL,
  `allowModi` char(1) DEFAULT 'N',
  `userId` int(11) NOT NULL,
  `dateCreated` datetime DEFAULT NULL,
  `userIdUpdate` int(11) DEFAULT NULL,
  `dateUpdate` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Triggers `Clients`
--
DELIMITER $$
CREATE TRIGGER `clients_history_trigger` BEFORE UPDATE ON `Clients` FOR EACH ROW BEGIN

 declare v_clientId int(11);
 declare v_clientName varchar(50) ;
declare  v_addressId int(11) ;
 declare v_concernPerson varchar(50) ;
 declare v_gstNo varchar(50);
declare  v_effectiveStartDate datetime ;
declare  v_effectiveEndDate datetime ;
 declare v_activeStatus varchar(2) ;
 declare v_companyId int(11);
 declare v_groupId int(11);
declare  v_allowModi char(1) ;
declare  v_userId int(11) ;
declare  v_dateCreated datetime ;
 declare v_userIdUpdate int(11) ;
declare  v_dateUpdate datetime;


 set v_clientId =OLD.clientId;
 set v_clientName =OLD.clientName ;
set  v_addressId =OLD.addressId ;
 set v_concernPerson =OLD.concernPerson ;
 set v_gstNo =OLD.gstNo;
set  v_effectiveStartDate =OLD.effectiveStartDate ;
set  v_effectiveEndDate =OLD.effectiveEndDate ;
 set v_activeStatus =OLD.activeStatus ;
 set v_companyId =OLD.companyId;
 set v_groupId =OLD.groupId;
set  v_allowModi =OLD.allowModi ;
set  v_userId =OLD.userId ;
set  v_dateCreated =OLD.dateCreated ;
 set v_userIdUpdate =NEW.userIdUpdate ;
set  v_dateUpdate =NEW.dateUpdate;




  
 
Insert INTO ClientsHistory(

clientId ,
clientName ,
addressId ,
concernPerson ,
gstNo ,
effectiveStartDate ,
effectiveEndDate,
activeStatus ,
companyId,
groupId ,
allowModi ,
userId ,
dateCreated ,
userIdUpdate ,
dateUpdate 
    
   ) VALUES
  (
 
v_clientId ,
v_clientName ,
v_addressId ,
v_concernPerson ,
v_gstNo ,
v_effectiveStartDate ,
v_effectiveEndDate,
v_activeStatus ,
v_companyId,
v_groupId ,
v_allowModi ,
v_userId ,
v_dateCreated ,
v_userIdUpdate ,
v_dateUpdate 
      
      
  );
  
  INSERT INTO AddressHistory(addressId,countryId,	stateId,	cityId,	pincode,	addressText,	landmark,	telephone,	mobile	,fax,	emailId,	website	,allowModi,	userId	,dateCreated,	userIdUpdate )
   select addressId,countryId,	stateId,	cityId,	pincode,	addressText,	landmark,	telephone,	mobile	,fax,	emailId,	website	,allowModi,	userId	,dateCreated,	v_userIdUpdate
	  from 
      Address ah where ah.addressId IN (v_addressId)
     ; 
     
End
$$
DELIMITER ;

-- --------------------------------------------------------

--
-- Table structure for table `ClientsHistory`
--

CREATE TABLE `ClientsHistory` (
  `clientId` int(11) NOT NULL,
  `clientName` varchar(50) NOT NULL,
  `addressId` int(11) NOT NULL,
  `concernPerson` varchar(50) DEFAULT NULL,
  `gstNo` varchar(50) DEFAULT NULL,
  `effectiveStartDate` datetime DEFAULT NULL,
  `effectiveEndDate` datetime DEFAULT NULL,
  `activeStatus` varchar(2) DEFAULT NULL,
  `companyId` int(11) NOT NULL,
  `groupId` int(11) DEFAULT NULL,
  `allowModi` char(1) DEFAULT 'N',
  `userId` int(11) DEFAULT NULL,
  `dateCreated` datetime DEFAULT NULL,
  `userIdUpdate` int(11) DEFAULT NULL,
  `dateUpdate` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `Company`
--

CREATE TABLE `Company` (
  `companyId` int(11) NOT NULL,
  `groupId` int(11) DEFAULT NULL,
  `companyAbbreviation` varchar(20) DEFAULT NULL,
  `companyName` varchar(100) NOT NULL,
  `companyLogo` blob,
  `domainName` varchar(20) DEFAULT 'trttt',
  `registeredOfficeAddressId` int(11) DEFAULT NULL,
  `corporateOfficeAddressId` int(11) DEFAULT NULL,
  `registrationNo` varchar(50) DEFAULT NULL,
  `gumastaNo` varchar(50) DEFAULT NULL,
  `nagarnigamNo` varchar(50) DEFAULT NULL,
  `panNo` varchar(50) DEFAULT NULL,
  `gstNo` varchar(50) DEFAULT NULL,
  `epfNo` varchar(50) DEFAULT NULL,
  `esicNo` varchar(50) DEFAULT NULL,
  `retirementAge` int(11) DEFAULT '999',
  `dateOfBirth` date DEFAULT NULL,
  `companyLogoPath` varchar(50) DEFAULT NULL,
  `ImportExportCode` varchar(50) DEFAULT NULL,
  `tanNo` varchar(50) DEFAULT NULL,
  `typeOfIndustry` varchar(50) DEFAULT NULL,
  `allowModi` char(1) DEFAULT 'N',
  `effectiveStartDate` datetime DEFAULT NULL,
  `effectiveEndDate` datetime DEFAULT NULL,
  `activeStatus` varchar(2) DEFAULT NULL,
  `authorizedPerson` varchar(50) NOT NULL,
  `mobile` varchar(16) NOT NULL,
  `emailId` varchar(50) NOT NULL,
  `website` varchar(50) NOT NULL,
  `userId` int(11) NOT NULL,
  `dateCreated` datetime DEFAULT NULL,
  `userIdUpdate` int(11) DEFAULT NULL,
  `dateUpdate` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `Company`
--

INSERT INTO `Company` (`companyId`, `groupId`, `companyAbbreviation`, `companyName`, `companyLogo`, `domainName`, `registeredOfficeAddressId`, `corporateOfficeAddressId`, `registrationNo`, `gumastaNo`, `nagarnigamNo`, `panNo`, `gstNo`, `epfNo`, `esicNo`, `retirementAge`, `dateOfBirth`, `companyLogoPath`, `ImportExportCode`, `tanNo`, `typeOfIndustry`, `allowModi`, `effectiveStartDate`, `effectiveEndDate`, `activeStatus`, `authorizedPerson`, `mobile`, `emailId`, `website`, `userId`, `dateCreated`, `userIdUpdate`, `dateUpdate`) VALUES
(1, NULL, NULL, 'Tenco System and switchgear Pvt. Ltd.', NULL, 'www.tencogroup.net', 1, NULL, NULL, NULL, NULL, 'XXXXXXXXXXX', NULL, 'XXXXXXXXXXXXXXX', 'XXXXXXXXXXXX', NULL, NULL, '/images/companyImages/logo-tencogroup.jpg', NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'Pavitra Agal', '9893569668', 'hr@tencogroup.net', 'www.tencogroup.net', 1, NULL, NULL, '2019-07-30 06:53:44');

-- --------------------------------------------------------

--
-- Table structure for table `CompanyHistory`
--

CREATE TABLE `CompanyHistory` (
  `companyId` int(11) NOT NULL,
  `groupId` int(11) DEFAULT NULL,
  `companyAbbreviation` varchar(20) DEFAULT NULL,
  `companyName` varchar(100) NOT NULL,
  `companyLogo` blob,
  `registeredOfficeAddressId` int(11) NOT NULL,
  `corporateOfficeAddressId` int(11) NOT NULL,
  `registrationNo` varchar(50) DEFAULT NULL,
  `gumastaNo` varchar(50) DEFAULT NULL,
  `nagarnigamNo` varchar(50) DEFAULT NULL,
  `panNo` varchar(50) DEFAULT NULL,
  `gstNo` varchar(50) DEFAULT NULL,
  `epfNo` varchar(50) DEFAULT NULL,
  `esicNo` varchar(50) DEFAULT NULL,
  `retirementAge` int(11) DEFAULT '999',
  `dateOfBirth` date DEFAULT NULL,
  `companyLogoPath` varchar(50) DEFAULT NULL,
  `ImportExportCode` varchar(50) DEFAULT NULL,
  `tanNo` varchar(50) DEFAULT NULL,
  `typeOfIndustry` varchar(50) DEFAULT NULL,
  `allowModi` char(1) DEFAULT 'N',
  `effectiveStartDate` datetime DEFAULT NULL,
  `effectiveEndDate` datetime DEFAULT NULL,
  `activeStatus` varchar(2) DEFAULT NULL,
  `userId` int(11) NOT NULL,
  `dateCreated` datetime DEFAULT NULL,
  `userIdUpdate` varchar(250) DEFAULT NULL,
  `dateUpdate` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `Country`
--

CREATE TABLE `Country` (
  `countryId` int(11) NOT NULL,
  `countryName` varchar(50) NOT NULL,
  `allowModi` char(1) DEFAULT 'N',
  `userId` int(11) NOT NULL,
  `dateCreated` datetime DEFAULT NULL,
  `userIdUpdate` int(11) DEFAULT NULL,
  `dateUpdate` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `Country`
--

INSERT INTO `Country` (`countryId`, `countryName`, `allowModi`, `userId`, `dateCreated`, `userIdUpdate`, `dateUpdate`) VALUES
(1, 'India', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00');

-- --------------------------------------------------------

--
-- Table structure for table `Department`
--

CREATE TABLE `Department` (
  `departmentId` int(11) NOT NULL,
  `departmentName` char(40) DEFAULT NULL,
  `departmentCode` varchar(250) DEFAULT NULL,
  `companyId` int(11) NOT NULL,
  `groupId` int(11) DEFAULT NULL,
  `patternId` int(11) DEFAULT NULL,
  `effectiveStartDate` datetime DEFAULT NULL,
  `effectiveEndDate` datetime DEFAULT NULL,
  `activeStatus` varchar(2) DEFAULT NULL,
  `allowModi` char(1) DEFAULT 'N',
  `userId` int(11) NOT NULL,
  `dateCreated` datetime DEFAULT NULL,
  `userIdUpdate` int(11) DEFAULT NULL,
  `dateUpdate` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Triggers `Department`
--
DELIMITER $$
CREATE TRIGGER `department_history_trigger` BEFORE UPDATE ON `Department` FOR EACH ROW BEGIN

  declare v_departmentId int(11) ;
  declare v_departmentName char(40) ;
  declare v_companyId int(11) ;
  declare v_groupId int(11) ;
  declare v_effectiveStartDate datetime ;
  declare v_effectiveEndDate datetime ;
  declare v_activeStatus varchar(2) ;
  declare v_allowModi char(1) ;
  declare v_userId int(11) ;
  declare v_dateCreated datetime ;
  declare v_userIdUpdate int(11) ;
  declare v_dateUpdate datetime ;

 set v_departmentId =OLD.departmentId ;
  set v_departmentName =OLD.departmentName ;
  set v_companyId =OLD.companyId ;
  set v_groupId =OLD.groupId;
  set v_effectiveStartDate =OLD.effectiveStartDate ;
  set v_effectiveEndDate =OLD.effectiveEndDate ;
  set v_activeStatus =OLD.activeStatus ;
  set v_allowModi =OLD.allowModi ;
  set v_userId =OLD.userId ;
  set v_dateCreated =OLD.dateCreated;
  set v_userIdUpdate =NEW.userIdUpdate ;
  set v_dateUpdate =OLD.dateUpdate ;


Insert INTO DepartmentHistory(

departmentId,
departmentName,
companyId,
groupId,
effectiveStartDate,
effectiveEndDate,
activeStatus ,
allowModi ,
userId ,
dateCreated,
userIdUpdate,
dateUpdate
    
   ) VALUES
  (
      
 v_departmentId,
v_departmentName,
v_companyId,
v_groupId,
v_effectiveStartDate,
v_effectiveEndDate,
v_activeStatus ,
v_allowModi ,
v_userId ,
v_dateCreated,
v_userIdUpdate,
v_dateUpdate
      
  );
End
$$
DELIMITER ;

-- --------------------------------------------------------

--
-- Table structure for table `DepartmentHistory`
--

CREATE TABLE `DepartmentHistory` (
  `departmentId` int(11) NOT NULL,
  `departmentName` char(40) DEFAULT NULL,
  `companyId` int(11) NOT NULL,
  `groupId` int(11) DEFAULT NULL,
  `effectiveStartDate` datetime DEFAULT NULL,
  `effectiveEndDate` datetime DEFAULT NULL,
  `activeStatus` varchar(2) DEFAULT NULL,
  `allowModi` char(1) DEFAULT 'N',
  `userId` int(11) NOT NULL,
  `dateCreated` datetime DEFAULT NULL,
  `userIdUpdate` int(11) DEFAULT NULL,
  `dateUpdate` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `DepartmentHistory`
--

INSERT INTO `DepartmentHistory` (`departmentId`, `departmentName`, `companyId`, `groupId`, `effectiveStartDate`, `effectiveEndDate`, `activeStatus`, `allowModi`, `userId`, `dateCreated`, `userIdUpdate`, `dateUpdate`) VALUES
(1, 'Mobile', 1, NULL, NULL, NULL, 'AC', NULL, 1, '2019-07-25 11:12:19', 1, '2019-07-25 11:12:19');

-- --------------------------------------------------------

--
-- Table structure for table `DeptDesignationMapping`
--

CREATE TABLE `DeptDesignationMapping` (
  `deptDesgId` int(11) NOT NULL,
  `departmentId` int(11) NOT NULL,
  `designationId` int(11) NOT NULL,
  `companyId` int(11) NOT NULL,
  `activeStatus` varchar(2) NOT NULL,
  `userId` int(11) NOT NULL,
  `dateCreated` datetime DEFAULT NULL,
  `userIdUpdate` int(11) DEFAULT NULL,
  `dateUpdate` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `DeptDesignationMapping`
--

INSERT INTO `DeptDesignationMapping` (`deptDesgId`, `departmentId`, `designationId`, `companyId`, `activeStatus`, `userId`, `dateCreated`, `userIdUpdate`, `dateUpdate`) VALUES
(1, 8, 1, 1, 'AC', 1, NULL, NULL, NULL),
(6, 8, 2, 1, 'AC', 1, NULL, NULL, NULL),
(8, 6, 4, 1, 'AC', 1, NULL, NULL, NULL),
(9, 6, 5, 1, 'AC', 1, NULL, NULL, NULL),
(10, 5, 6, 1, 'AC', 1, NULL, NULL, NULL),
(11, 5, 7, 1, 'AC', 1, NULL, NULL, NULL),
(12, 5, 8, 1, 'AC', 1, NULL, NULL, NULL),
(13, 5, 9, 1, 'AC', 1, NULL, NULL, NULL),
(14, 5, 10, 1, 'AC', 1, NULL, NULL, NULL),
(15, 5, 11, 1, 'AC', 1, NULL, NULL, NULL),
(16, 5, 12, 1, 'AC', 1, NULL, NULL, NULL),
(17, 2, 13, 1, 'AC', 1, NULL, NULL, NULL),
(18, 2, 14, 1, 'AC', 1, NULL, NULL, NULL),
(19, 1, 14, 1, 'AC', 1, NULL, NULL, NULL),
(20, 4, 15, 1, 'AC', 1, NULL, NULL, NULL),
(21, 4, 16, 1, 'AC', 1, NULL, NULL, NULL),
(22, 1, 17, 1, 'AC', 1, NULL, NULL, NULL),
(23, 1, 18, 1, 'AC', 1, NULL, NULL, NULL),
(24, 1, 19, 1, 'AC', 1, NULL, NULL, NULL),
(25, 1, 20, 1, 'AC', 1, NULL, NULL, NULL),
(26, 5, 21, 1, 'AC', 1, NULL, NULL, NULL),
(27, 3, 22, 1, 'AC', 1, NULL, NULL, NULL),
(28, 3, 23, 1, 'AC', 1, NULL, NULL, NULL),
(29, 3, 24, 1, 'AC', 1, NULL, NULL, NULL),
(30, 2, 24, 1, 'AC', 1, NULL, NULL, NULL),
(31, 1, 24, 1, 'AC', 1, NULL, NULL, NULL),
(32, 8, 24, 1, 'AC', 1, NULL, NULL, NULL),
(33, 6, 24, 1, 'AC', 1, NULL, NULL, NULL),
(34, 4, 24, 1, 'AC', 1, NULL, NULL, NULL),
(35, 3, 25, 1, 'AC', 1, NULL, NULL, NULL),
(36, 6, 25, 1, 'AC', 1, NULL, NULL, NULL),
(37, 7, 25, 1, 'AC', 1, NULL, NULL, NULL),
(38, 4, 25, 1, 'AC', 1, NULL, NULL, NULL),
(39, 1, 25, 1, 'AC', 1, NULL, NULL, NULL),
(40, 2, 25, 1, 'AC', 1, NULL, NULL, NULL),
(41, 5, 25, 1, 'AC', 1, NULL, NULL, NULL),
(42, 8, 25, 1, 'AC', 1, NULL, NULL, NULL),
(43, 8, 26, 1, 'AC', 1, NULL, NULL, NULL),
(44, 5, 26, 1, 'AC', 1, NULL, NULL, NULL),
(45, 2, 26, 1, 'AC', 1, NULL, NULL, NULL),
(46, 7, 26, 1, 'AC', 1, NULL, NULL, NULL),
(47, 4, 26, 1, 'AC', 1, NULL, NULL, NULL),
(48, 1, 26, 1, 'AC', 1, NULL, NULL, NULL),
(49, 6, 26, 1, 'AC', 1, NULL, NULL, NULL),
(50, 3, 26, 1, 'AC', 1, NULL, NULL, NULL),
(51, 7, 3, 1, 'AC', 1, NULL, NULL, NULL);

-- --------------------------------------------------------

--
-- Table structure for table `Designation`
--

CREATE TABLE `Designation` (
  `designationId` int(11) NOT NULL,
  `designationName` char(40) DEFAULT NULL,
  `departmentId` int(11) DEFAULT NULL,
  `companyId` int(11) NOT NULL,
  `groupId` int(11) DEFAULT NULL,
  `effectiveStartDate` datetime DEFAULT NULL,
  `effectiveEndDate` datetime DEFAULT NULL,
  `activeStatus` varchar(2) DEFAULT NULL,
  `allowModi` char(1) DEFAULT 'N',
  `userId` int(11) NOT NULL,
  `dateCreated` datetime DEFAULT NULL,
  `userIdUpdate` int(11) DEFAULT NULL,
  `dateUpdate` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Triggers `Designation`
--
DELIMITER $$
CREATE TRIGGER `designation_history_trigger` BEFORE UPDATE ON `Designation` FOR EACH ROW BEGIN

 declare  v_designationId int(11);
declare  v_designationName char(40) ;
declare  v_departmentId int(11) ;
declare  v_companyId int(11);
 declare v_groupId int(11) ;
 declare v_effectiveStartDate datetime ;
 declare v_effectiveEndDate datetime ;
 declare v_activeStatus varchar(2) ;
 declare v_allowModi char(1) ;
 declare v_userId int(11) ;
 declare v_dateCreated datetime ;
 declare v_userIdUpdate int(11) ;
 declare v_dateUpdate datetime ;
 
 
set  v_designationId =OLD.designationId;
set  v_designationName =OLD.designationName ;
set  v_departmentId =OLD.departmentId ;
set  v_companyId =OLD.companyId;
 set v_groupId =OLD.groupId ;
 set v_effectiveStartDate =OLD.effectiveStartDate ;
 set v_effectiveEndDate =OLD.effectiveEndDate ;
 set v_activeStatus =OLD.activeStatus ;
 set v_allowModi =OLD.allowModi ;
 set v_userId =OLD.userId ;
 set v_dateCreated =OLD.dateCreated ;
 set v_userIdUpdate =NEW.userIdUpdate ;
 set v_dateUpdate =OLD.dateUpdate ;
 

Insert INTO DesignationHistory(

designationId ,
designationName ,
departmentId,
companyId,
groupId,
effectiveStartDate ,
effectiveEndDate ,
activeStatus ,
allowModi ,
userId ,
dateCreated ,
userIdUpdate ,
dateUpdate 
    
   ) VALUES
  (
      
v_designationId ,
v_designationName ,
v_departmentId,
v_companyId,
v_groupId,
v_effectiveStartDate ,
v_effectiveEndDate ,
v_activeStatus ,
v_allowModi ,
v_userId ,
v_dateCreated ,
v_userIdUpdate ,
v_dateUpdate 
      
  );
End
$$
DELIMITER ;

-- --------------------------------------------------------

--
-- Table structure for table `DesignationHistory`
--

CREATE TABLE `DesignationHistory` (
  `designationId` int(11) NOT NULL,
  `designationName` char(40) DEFAULT NULL,
  `departmentId` int(11) DEFAULT NULL,
  `companyId` int(11) NOT NULL,
  `groupId` int(11) DEFAULT NULL,
  `effectiveStartDate` datetime DEFAULT NULL,
  `effectiveEndDate` datetime DEFAULT NULL,
  `activeStatus` varchar(2) DEFAULT NULL,
  `allowModi` char(1) DEFAULT 'N',
  `userId` int(11) NOT NULL,
  `dateCreated` datetime DEFAULT NULL,
  `userIdUpdate` int(11) DEFAULT NULL,
  `dateUpdate` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `DesignationHistory`
--

INSERT INTO `DesignationHistory` (`designationId`, `designationName`, `departmentId`, `companyId`, `groupId`, `effectiveStartDate`, `effectiveEndDate`, `activeStatus`, `allowModi`, `userId`, `dateCreated`, `userIdUpdate`, `dateUpdate`) VALUES
(2, 'Senior Database Administrator', NULL, 1, NULL, NULL, NULL, 'AC', NULL, 1, NULL, NULL, '2019-07-25 11:17:12'),
(2, 'Senior Database Administrator', NULL, 1, NULL, NULL, NULL, 'AC', NULL, 1, NULL, NULL, '2019-07-25 11:17:41'),
(2, 'Content Writer', NULL, 1, NULL, NULL, NULL, 'AC', NULL, 1, NULL, NULL, '2019-07-25 11:18:01'),
(2, 'Senior Database Administrator', NULL, 1, NULL, NULL, NULL, 'AC', NULL, 1, NULL, NULL, '2019-07-25 11:18:43'),
(3, 'Content Writer', NULL, 1, NULL, NULL, NULL, 'AC', NULL, 1, NULL, NULL, '2019-07-25 11:20:57');

-- --------------------------------------------------------

--
-- Table structure for table `DeviceInfo`
--

CREATE TABLE `DeviceInfo` (
  `deviceId` int(11) NOT NULL,
  `machineIP` varchar(50) DEFAULT NULL,
  `serialNumber` varchar(50) NOT NULL,
  `deviceName` varchar(50) NOT NULL,
  `companyId` int(11) NOT NULL,
  `prefix` varchar(10) NOT NULL,
  `address` varchar(250) DEFAULT NULL,
  `userId` int(11) NOT NULL,
  `userIdUpdate` int(11) NOT NULL,
  `dateCreated` date NOT NULL,
  `dateUpdate` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `DeviceLogs`
--

CREATE TABLE `DeviceLogs` (
  `deviceLogId` int(11) NOT NULL,
  `deviceId` int(50) NOT NULL,
  `userId` varchar(50) NOT NULL,
  `logDate` datetime NOT NULL,
  `direction` varchar(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `DeviceLogsInfo`
--

CREATE TABLE `DeviceLogsInfo` (
  `deviceLogId` int(11) NOT NULL,
  `deviceId` int(50) NOT NULL,
  `userId` varchar(50) NOT NULL,
  `logDate` datetime NOT NULL,
  `direction` varchar(10) NOT NULL,
  `mode` varchar(250) DEFAULT NULL,
  `latitude` varchar(250) DEFAULT NULL,
  `longitude` varchar(250) DEFAULT NULL,
  `address` varchar(250) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `DrowpdownHd`
--

CREATE TABLE `DrowpdownHd` (
  `drowpdownId` tinyint(4) NOT NULL,
  `drowpdownName` varchar(50) DEFAULT NULL,
  `companyId` int(11) NOT NULL,
  `groupId` int(11) DEFAULT NULL,
  `allowModi` char(1) DEFAULT 'N',
  `userId` int(11) DEFAULT NULL,
  `userIdUpdate` int(11) DEFAULT NULL,
  `dateUpdate` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `DrowpdownHd`
--

INSERT INTO `DrowpdownHd` (`drowpdownId`, `drowpdownName`, `companyId`, `groupId`, `allowModi`, `userId`, `userIdUpdate`, `dateUpdate`) VALUES
(1, 'SelectIdType', 1, NULL, 'N', 1, 1, NULL),
(2, 'Category', 1, NULL, 'N', 1, 1, NULL),
(3, 'Qualification', 1, NULL, 'N', 1, 1, NULL),
(4, 'EmploymentType', 1, NULL, 'N', 1, 1, NULL),
(5, 'MaritalStatus', 1, NULL, 'N', 1, 1, NULL),
(6, 'BloodGroup', 1, NULL, 'N', 1, 1, NULL),
(7, 'Relation', 1, NULL, 'N', 1, 1, NULL),
(8, 'Captions', 1, NULL, 'N', 1, 1, NULL),
(9, 'Status', 1, NULL, 'N', 1, 1, NULL),
(10, 'EarningDeduction', 1, NULL, 'N', 1, 1, NULL),
(11, 'AccountType', 1, NULL, 'N', 1, 1, NULL),
(12, 'Statuary', 1, NULL, 'N', 1, 1, NULL),
(13, 'ItemUnit', 1, NULL, 'N', 1, 1, NULL),
(14, 'BankName', 1, NULL, 'N', 1, 1, NULL),
(15, 'Occupation', 1, NULL, 'N', 1, 1, NULL),
(17, 'RegularCorrespondence', 1, NULL, 'N', 1, 1, NULL),
(18, 'LoanType', 1, NULL, 'N', 1, 1, NULL),
(19, 'Gender', 1, NULL, 'N', 1, 1, NULL),
(20, 'ConfirmOptions', 1, NULL, 'N', 1, 1, NULL),
(21, 'IncomeType', 1, NULL, 'N', 1, 1, NULL),
(22, 'Expense', 1, NULL, 'N', 1, 1, NULL),
(23, 'ProcessMonthFlag', 1, NULL, 'N', 1, 1, NULL),
(24, 'Degree', 1, NULL, 'N', 1, 1, NULL),
(25, 'Categories', 1, NULL, 'N', 1, 1, NULL),
(26, 'ResignationReason', 1, NULL, 'N', 1, 1, '2018-04-19 00:00:00'),
(27, 'MndatoryInfo', 1, NULL, 'N', 1, 1, '2018-04-19 00:00:00'),
(28, 'SeparationMode', 1, NULL, 'N', 1, 1, '2018-04-19 00:00:00'),
(29, 'Payment Mode', 1, NULL, 'N', 1, 1, '2018-07-02 00:00:00'),
(31, 'Title', 1, NULL, 'N', 1, 1, NULL),
(32, 'DesignationLabel', 1, NULL, 'N', 1, 1, '2018-07-27 00:00:00'),
(33, 'Nature', 1, NULL, 'N', 1, 1, '2018-08-13 00:00:00'),
(34, 'ARCategory', 1, NULL, 'N', 1, 1, '2018-08-22 00:00:00'),
(35, 'Notification Type ', 1, NULL, 'N', 1, 1, '2018-10-03 00:00:00'),
(36, 'ReasonForChange', 1, NULL, 'N', 1, 1, '2018-11-27 00:00:00'),
(37, 'postGraduate', 1, NULL, 'N', 1, 1, '2019-06-13 00:00:00'),
(38, 'graduadte', 1, NULL, 'N', 1, 1, '2019-06-13 00:00:00'),
(39, 'TenthSubject', 1, 1, 'N', 1, 1, '2019-08-13 00:00:00'),
(40, 'twelfthSubject', 1, NULL, 'N', 1, 1, '2019-08-13 00:00:00');

-- --------------------------------------------------------

--
-- Table structure for table `DrowpdownList`
--

CREATE TABLE `DrowpdownList` (
  `drowpdownListId` smallint(6) NOT NULL,
  `drowpdownId` tinyint(4) NOT NULL,
  `listValue` varchar(50) DEFAULT NULL,
  `listCode` char(2) DEFAULT NULL,
  `activeStatus` varchar(2) DEFAULT 'A'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `DrowpdownList`
--

INSERT INTO `DrowpdownList` (`drowpdownListId`, `drowpdownId`, `listValue`, `listCode`, `activeStatus`) VALUES
(2, 1, 'PAN Number', 'PA', 'A'),
(3, 1, 'Driving Licence', 'DL', 'A'),
(4, 1, 'Passport Number', 'PS', 'A'),
(5, 1, 'Voter ID', 'VO', 'A'),
(6, 2, 'Men', 'M', 'A'),
(7, 2, 'Women', 'F', 'A'),
(8, 2, 'Transgender', 'T', 'A'),
(9, 2, 'Senior Citizen', 'SE', 'A'),
(10, 3, '10th', 'TE', 'A'),
(11, 3, '12th', 'TW', 'A'),
(12, 3, 'Graduate', 'GR', 'A'),
(13, 3, 'Post Graduate', 'PO', 'A'),
(14, 3, 'Professional', 'PR', 'D'),
(15, 3, 'Extra Curriculum', 'EX', 'D'),
(16, 4, 'On Roll', 'ON', 'A'),
(17, 4, 'Off Roll', 'OF', 'A'),
(18, 4, 'Trainee', 'TR', 'A'),
(19, 4, 'Apprentship', 'AP', 'A'),
(20, 5, 'Married', 'MA', 'A'),
(21, 5, 'Unmarried', 'UN', 'A'),
(22, 6, 'A+', 'AP', 'A'),
(23, 6, 'A-', 'AN', 'A'),
(24, 6, 'B+', 'BP', 'A'),
(25, 6, 'B-', 'BN', 'A'),
(26, 6, 'O+', 'OP', 'A'),
(27, 6, 'O-', 'ON', 'A'),
(28, 6, 'AB+', 'AB', 'A'),
(29, 6, 'AB-', 'AM', 'A'),
(30, 7, 'Father', 'FA', 'A'),
(31, 7, 'Mother', 'MO', 'A'),
(32, 7, 'Spouse', 'SP', 'A'),
(33, 7, 'Child', 'CH', 'A'),
(34, 7, 'Sister', 'SI', 'A'),
(35, 7, 'Brother', 'BR', 'A'),
(36, 7, 'Husband', 'HB', 'A'),
(37, 8, 'Mr.', 'MR', 'A'),
(38, 8, 'Mrs.', 'MS', 'A'),
(39, 8, 'Ms.', 'MI', 'A'),
(40, 8, 'Dr.', 'DO', 'A'),
(41, 9, 'Active', 'AC', 'A'),
(42, 9, 'Deactive', 'DE', 'A'),
(43, 10, 'Earning', 'EA', 'A'),
(44, 10, 'Deduction', 'DE', 'A'),
(45, 11, 'Savings', 'SA', 'A'),
(46, 11, 'Current', 'CU', 'A'),
(47, 11, 'Salary', 'SL', 'A'),
(48, 12, 'Provident Fund', 'PF', 'A'),
(49, 12, 'UAN', 'UA', 'D'),
(50, 12, 'ESI Number', 'ES', 'D'),
(51, 12, 'Medical Insurance', 'ME', 'A'),
(52, 12, 'Accidental Insurance', 'AC', 'A'),
(53, 13, 'Laptop', 'LA', 'A'),
(54, 13, 'Broadband Dongle', 'BR', 'A'),
(55, 13, 'Mobile Phone', 'MO', 'A'),
(56, 13, 'SIM Card', 'SI', 'A'),
(57, 13, 'Vehicle', 'VE', 'A'),
(58, 14, 'State Bank Of India', 'ST', 'A'),
(59, 14, 'HDFC Bank', 'HD', 'A'),
(60, 14, 'ICICI Bank', 'IC', 'A'),
(61, 14, 'Punjab National Bank', 'PU', 'A'),
(62, 14, 'Axis Bank', 'AX', 'A'),
(63, 14, 'Canara Bank', 'CA', 'A'),
(64, 14, 'Bank of Baroda', 'BB', 'A'),
(65, 14, 'IDBI Bank', 'ID', 'A'),
(66, 14, 'Bank Of India', 'BI', 'A'),
(67, 14, 'Allahabad Bank', 'AL', 'A'),
(68, 14, 'Andhra Bank', 'AN', 'A'),
(69, 14, 'Bank Of Maharashtra', 'BM', 'A'),
(70, 14, 'Central bank Of India', 'CE', 'A'),
(71, 14, 'Corporation Bank', 'CO', 'A'),
(72, 14, 'Dena Bank', 'DE', 'A'),
(73, 14, 'Indian Bank', 'IN', 'A'),
(74, 14, 'Indian Overseas Bank', 'IO', 'A'),
(75, 14, 'Oriental Bank Of Commerce', 'OR', 'A'),
(77, 14, 'Syndicate Bank', 'SY', 'A'),
(78, 14, 'UCO Bank', 'UC', 'A'),
(79, 14, 'Union Bank Of India', 'UB', 'A'),
(80, 14, 'United Bank Of India', 'UI', 'A'),
(81, 14, 'Vijaya Bank', 'VI', 'A'),
(82, 14, 'Kotak mahindra Bank', 'KO', 'A'),
(83, 14, 'Induslnd Bank', 'IU', 'A'),
(84, 14, 'Federal Bank', 'FE', 'A'),
(86, 14, 'Yes Bank Ltd', 'YE', 'A'),
(87, 14, 'Jammu and Kashmir Bank', 'JA', 'A'),
(88, 14, 'Karnataka Bank Ltd', 'KA', 'A'),
(89, 14, 'Karur Vysya Bank', 'KR', 'A'),
(90, 14, 'City Union Bank', 'CI', 'A'),
(91, 14, 'ING Vysya Bank', 'IG', 'A'),
(92, 14, 'Laxmi Vilas Bank', 'LA', 'A'),
(93, 14, 'Punjab & Sind Bank', 'PN', 'A'),
(94, 14, 'State Bank of Bikaner & Jaipur', 'SJ', 'A'),
(95, 14, 'State Bank of Hyderabad', 'SH', 'A'),
(96, 14, 'State Bank of Mysore', 'SM', 'A'),
(97, 14, 'State Bank of Patiala', 'SP', 'A'),
(98, 14, 'HSBC Bank', 'HS', 'A'),
(99, 14, 'IDFC Bank', 'IF', 'A'),
(100, 14, 'Bandhan bank', 'BA', 'A'),
(101, 14, 'RBL Bank', 'RB', 'A'),
(102, 14, 'Andhra Pragati gramin bank', 'AG', 'A'),
(103, 14, 'Bharat Co-op Bank (Mumbai)', 'BH', 'A'),
(104, 14, 'Citi bank', 'CT', 'A'),
(105, 14, 'Gurgaon Gramin Bank', 'GU', 'A'),
(106, 14, 'Maharashtra State Co-Op bank', 'MA', 'A'),
(107, 14, 'Mumbai District Central Co-op Bank', 'MU', 'A'),
(108, 14, 'Punjab and Maharashtra Co-Op bank', 'PM', 'A'),
(109, 14, 'Reserve Bank Of India', 'RE', 'A'),
(110, 14, 'SBER Bank', 'SB', 'A'),
(111, 14, 'Standard Chartered bank', 'SC', 'A'),
(112, 14, 'West Bengal State Co-Op Bank', 'WE', 'A'),
(113, 15, 'Teaching', 'TE', 'A'),
(114, 15, 'Writing', 'WR', 'A'),
(115, 15, 'Manager', 'MA', 'A'),
(116, 15, 'Engineer', 'EN', 'A'),
(117, 15, 'Lawyer', 'LA', 'A'),
(118, 15, 'Banker', 'BA', 'A'),
(119, 15, 'Clerk', 'CL', 'A'),
(120, 15, 'Designer', 'DE', 'A'),
(121, 15, 'Film and Television', 'FI', 'A'),
(122, 15, 'Theatre Personnel', 'TH', 'A'),
(123, 15, 'Architect', 'AR', 'A'),
(124, 15, 'Agriculture', 'AG', 'A'),
(125, 15, 'Central Government', 'CE', 'A'),
(126, 15, 'State Government', 'ST', 'A'),
(127, 15, 'Interns', 'IN', 'A'),
(128, 15, 'IT Professional', 'IT', 'A'),
(129, 15, 'Telecommunications', 'TL', 'A'),
(130, 15, 'Carpenter', 'CA', 'A'),
(131, 15, 'Businessman', 'BU', 'A'),
(132, 15, 'Scientist', 'SC', 'A'),
(133, 15, 'Politics', 'PO', 'A'),
(134, 15, 'Private Job', 'PR', 'A'),
(135, 15, 'Other', 'OT', 'A'),
(136, 17, 'Regular', 'R', 'A'),
(137, 17, 'Correspondence', 'C', 'A'),
(138, 18, 'Advance salary', 'AS', 'A'),
(139, 18, 'Personal Loan', 'PL', 'A'),
(140, 18, 'Housing Loan', 'HL', 'A'),
(141, 18, 'Other Deduction', 'OD', 'A'),
(142, 19, 'Male', 'M', 'A'),
(143, 19, 'Female', 'F', 'A'),
(144, 19, 'Transgender', 'T', 'A'),
(145, 20, 'Yes', 'Y', 'A'),
(146, 20, 'No', 'N', 'A'),
(147, 21, 'Attendance based Earnings', 'A', 'A'),
(148, 21, 'Computation based Earnings', 'C', 'A'),
(149, 21, 'Flat Rate based Earnings', 'F', 'A'),
(150, 21, 'Production based Earnings', 'P', 'A'),
(151, 21, 'User Defined Earnings ', 'U', 'A'),
(152, 22, 'Direct', 'D', 'A'),
(153, 22, 'Indirect', 'I', 'A'),
(154, 23, 'Active', 'AC', 'A'),
(155, 23, 'Open', 'OP', 'A'),
(156, 23, 'Close', 'CE', 'A'),
(157, 38, 'B.A.', 'BA', 'A'),
(158, 38, 'B.Arch', 'BR', 'A'),
(159, 38, 'BCA', 'BC', 'A'),
(160, 38, 'BE', 'BE', 'A'),
(161, 38, 'B.Pharam', 'BP', 'A'),
(162, 37, 'MA', 'MA', 'A'),
(163, 37, 'MBA', 'MB', 'A'),
(164, 37, 'PGDCA', 'PG', 'A'),
(165, 37, 'B.ED', 'BD', 'A'),
(166, 38, 'BBA', 'BB', 'A'),
(167, 38, 'B.Com', 'BC', 'A'),
(168, 38, 'B.sc', 'BS', 'A'),
(169, 37, 'M.sc', 'MS', 'A'),
(170, 38, 'B.Tech', 'BT', 'A'),
(171, 37, 'M.Tech', 'MT', 'A'),
(172, 37, 'M.E', 'ME', 'A'),
(173, 5, 'Widowed', 'WD', 'A'),
(174, 5, 'Divorced', 'DV', 'A'),
(175, 5, 'Separated', 'SP', 'A'),
(176, 14, 'Other Bank', 'OB', 'A'),
(177, 25, 'All', 'A', 'A'),
(178, 25, 'Men', 'M', 'A'),
(179, 25, 'Women', 'W', 'A'),
(182, 26, 'Career Growth', 'CG', 'A'),
(183, 26, 'Personal Issue', 'PI', 'A'),
(184, 26, 'Medical Issue', 'MI', 'A'),
(185, 26, 'Issue with Company', 'IC', 'A'),
(186, 26, 'Other', 'OT', 'A'),
(187, 27, 'UID', 'UI', 'A'),
(188, 27, 'UAN', 'UA', 'A'),
(189, 27, 'ESI', 'ES', 'A'),
(190, 27, 'Bank Account Number', 'BA', 'A'),
(191, 27, 'Accidental Insurance', 'AI', 'A'),
(192, 27, 'Medical Insurance', 'MI', 'A'),
(193, 28, 'Resigned', 'RE', 'A'),
(194, 28, 'Terminated', 'TE', 'A'),
(195, 28, 'Absconded', 'AB', 'A'),
(196, 28, 'Other', 'OT', 'A'),
(210, 14, 'Tamilnad Mercantile Bank', 'TM', 'A'),
(212, 24, 'Not Applicable', 'NA', 'A'),
(213, 24, 'Others', 'OT', 'A'),
(214, 15, 'Farmer', 'FM', 'A'),
(215, 27, 'PAN', 'PN', 'A'),
(216, 27, 'EPF', 'PF', 'A'),
(217, 29, 'Online Banking', 'OB', 'A'),
(218, 29, 'Cheque', 'CH', 'A'),
(219, 29, 'Cash', 'CA', 'A'),
(220, 29, 'Salary', 'SA', 'A'),
(221, 29, 'Bad debts', 'BD', 'A'),
(222, 2, 'Surcharge', 'SU', 'A'),
(223, 2, 'Education Cess', 'BD', 'A'),
(224, 15, 'Doctor', 'DC', 'A'),
(225, 15, 'House Wife', 'HW', 'A'),
(226, 1, 'Aadhar Card', 'AA', 'D'),
(227, 31, 'Birth Day', 'BD', 'A'),
(228, 31, 'Salary Slip', 'SS', 'A'),
(229, 31, 'Joining Anniversery', 'JA', 'A'),
(230, 31, 'Anniversery', 'AN', 'A'),
(231, 32, 'Label-1', '1', 'A'),
(232, 32, 'Label-2', '2', 'A'),
(233, 32, 'Label-3', '3', 'A'),
(234, 32, 'Label-4', '4', 'A'),
(235, 32, 'Label-5', '5', 'A'),
(236, 33, 'Encash', 'EC', 'A'),
(237, 33, 'Non-Encash', 'NE', 'A'),
(238, 27, 'Nominee of UAN', 'NU', 'A'),
(239, 27, 'Nominee of Accidental Insurance', 'NA', 'A'),
(240, 27, 'Nominee of Medical Insurance', 'UM', 'A'),
(241, 34, 'Miss Punch', 'MP', 'A'),
(242, 34, 'Machine Not Working', 'MN', 'A'),
(243, 34, 'Card Not Working', 'CN', 'A'),
(244, 34, 'Power Cut', 'PC', 'A'),
(246, 35, 'Leave Apply', 'LA', 'A'),
(247, 35, 'Attendance Regularize', 'AR', 'A'),
(248, 36, 'Transfer', 'TR', 'A'),
(249, 36, 'SalaryGrowth', 'SG', 'A'),
(250, 36, 'Other', 'OT', 'A'),
(251, 12, 'Employee State Insurance', 'ES', 'A'),
(252, 4, 'Contract', 'CO', 'A'),
(253, 4, 'Permanent', 'PE', 'A'),
(254, 39, 'All Applicable', 'AA', 'A'),
(255, 40, 'Bio', 'BO', 'A'),
(256, 40, 'Maths', 'MS', 'A'),
(257, 40, 'Arts', 'AR', 'A'),
(258, 40, 'Diploma Courses', 'DP', 'A'),
(259, 40, 'Other', 'OT', 'A'),
(260, 1, 'Adhar card', 'AC', 'A'),
(269, 40, 'commerce', 'CM', 'A');

-- --------------------------------------------------------

--
-- Table structure for table `EmailConfiguration`
--

CREATE TABLE `EmailConfiguration` (
  `emailConfigureId` int(11) NOT NULL,
  `serverType` varchar(50) DEFAULT NULL,
  `host` varchar(50) NOT NULL,
  `port` int(11) NOT NULL,
  `protocol` varchar(11) DEFAULT NULL,
  `auth` varchar(11) DEFAULT NULL,
  `sslName` varchar(111) DEFAULT NULL,
  `starttlsName` varchar(111) DEFAULT NULL,
  `userName` varchar(100) NOT NULL,
  `password` varchar(100) NOT NULL,
  `activeStatus` varchar(11) DEFAULT NULL,
  `companyId` int(11) DEFAULT NULL,
  `dateCreated` date DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `EmailConfiguration`
--

INSERT INTO `EmailConfiguration` (`emailConfigureId`, `serverType`, `host`, `port`, `protocol`, `auth`, `sslName`, `starttlsName`, `userName`, `password`, `activeStatus`, `companyId`, `dateCreated`) VALUES
(1, 'gmail', 'smtp.gmail.com', 587, 'smtp', 'true', 'true', 'true', 'greetings@computronics.in', 'com@123', 'DE', 1, '2019-09-12'),
(3, 'notification', 'smtp.yandex.com', 465, 'smtp', 'true', 'true', 'true', 'notifications@fabhr.in', 'fab123', 'DE', 1, '2019-09-13'),
(4, 'noreply', 'smtp.yandex.com', 465, 'smtp', 'true', 'true', 'true', 'noreply@fabhr.in', 'fab123', 'DE', 1, '2019-09-12'),
(5, 'greetings', 'smtp.yandex.com', 465, 'smtp', 'true', 'true', 'true', 'greetings@fabhr.in', 'fab123', 'DE', 1, '2019-09-12'),
(6, 'demo', 'smtp.yandex.com', 465, 'smtp', 'true', 'true', 'true', 'demo@fabhr.in', 'fab123', 'DE', 1, '2019-09-12'),
(18, NULL, 'smtp.gmail.com', 587, 'smtp', 'true', 'true', 'true', 'greetings@computronics.in', 'com@123', 'DE', 1, '2019-09-17'),
(19, NULL, 'smtp.gmail.com', 587, 'smtp', 'true', 'true', 'true', 'greetings@computronics.in', 'com@123', 'DE', 1, '2019-09-17'),
(21, NULL, 'smtp.gmail.com', 587, 'smtp', 'true', 'true', 'true', 'greetings@computronics.in', 'com@123', 'DE', 1, '2019-09-17'),
(22, NULL, 'smtp.gmail.com', 587, 'smtp', 'true', 'true', 'true', 'greetings@computronics.in', 'com@123', 'DE', 1, '2019-09-17'),
(23, NULL, 'smtp.gmail.com', 587, 'smtp', 'true', 'true', 'true', 'greetings@computronics.in', 'com@1234', 'DE', 1, '2019-09-17'),
(24, NULL, 'smtp.gmail.com', 587, 'smtp', 'true', 'true', 'true', 'greetings@computronics.in', 'com@1234', 'DE', 1, '2019-09-17'),
(25, NULL, 'smtp.gmail.com', 587, 'smtp', 'false', 'false', 'false', 'greetings@computronics.in', 'com@1234', 'DE', 1, '2019-09-17'),
(26, NULL, 'smtp.gmail.com', 587, 'smtp', 'true', 'true', 'true', 'greetings@computronics.in', 'com@123', 'DE', 1, '2019-09-17'),
(27, NULL, 'smtp.gmail.com', 587, 'smtp', 'true', 'true', 'true', 'greetings@computronics.in', 'com@1234', 'DE', 1, '2019-09-18'),
(28, NULL, 'smtp.gmail.com', 587, 'smtp', 'true', 'true', 'true', 'greetings@computronics.in', 'com@123', 'DE', 1, '2019-09-18'),
(29, NULL, 'smtp.gmail.com', 587, 'smtp', 'true', 'true', 'true', 'greetings@computronics.in', 'com@1234', 'DE', 1, '2019-09-18'),
(30, NULL, 'smtp.yandex.com', 465, 'smtp', 'true', 'true', 'true', 'notifications@fabhr.in', 'fab123', 'DE', 1, '2019-09-19'),
(31, NULL, 'smtp.yandex.com', 465, 'smtp', 'true', 'true', 'true', 'notifications@fabhr.in', 'fab1234', 'DE', 1, '2019-09-21'),
(32, NULL, 'smtp.gmail.com', 587, 'smtp', 'true', 'true', 'true', 'greetings@computronics.in', 'com@123', 'DE', 1, '2019-09-21'),
(33, NULL, 'smtp.gmail.com', 587, 'smtp', 'false', 'false', 'false', 'greetings@computronics.in', 'com@123', 'DE', 1, '2019-09-21'),
(34, NULL, 'smtp.gmail.com', 587, 'smtp', 'true', 'true', 'false', 'greetings@computronics.in', 'com@123', 'DE', 1, '2019-09-21'),
(35, NULL, 'smtp.gmail.com', 587, 'smtp', 'false', 'false', 'true', 'greetings@computronics.in', 'com@123', 'DE', 1, '2019-09-21'),
(36, NULL, 'smtp.gmail.com', 587, 'smtp', 'true', 'true', 'true', 'greetings@computronics.in', 'com@1234', 'DE', 1, '2019-09-21'),
(37, NULL, 'smtp.gmail.com', 587, 'smtp', 'false', 'false', 'false', 'greetings@computronics.in', 'com@1234', 'DE', 1, '2019-10-05'),
(38, NULL, 'smtp.gmail.com', 587, 'smtp', 'true', 'true', 'true', 'greetings@computronics.in', 'com@1234', 'AC', 1, '2019-10-09');

-- --------------------------------------------------------

--
-- Table structure for table `EmailNotificationMaster`
--

CREATE TABLE `EmailNotificationMaster` (
  `mailId` int(11) NOT NULL,
  `emailConfigureId` int(11) DEFAULT NULL,
  `title` varchar(50) NOT NULL,
  `subject` varchar(200) DEFAULT NULL,
  `fromMail` varchar(50) DEFAULT NULL,
  `toMail` text,
  `cc` text,
  `bcc` text,
  `activeStatus` varchar(5) DEFAULT NULL,
  `userName` varchar(50) DEFAULT NULL,
  `password` varchar(50) DEFAULT NULL,
  `userId` int(11) DEFAULT NULL,
  `dateCreated` date DEFAULT NULL,
  `userIdUpdate` date DEFAULT NULL,
  `dateUpdate` date DEFAULT NULL,
  `companyId` int(11) DEFAULT NULL,
  `mailType` varchar(11) DEFAULT NULL,
  `isReportingTo` varchar(5) DEFAULT NULL,
  `isReportingToManager` varchar(5) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `EmailNotificationMaster`
--

INSERT INTO `EmailNotificationMaster` (`mailId`, `emailConfigureId`, `title`, `subject`, `fromMail`, `toMail`, `cc`, `bcc`, `activeStatus`, `userName`, `password`, `userId`, `dateCreated`, `userIdUpdate`, `dateUpdate`, `companyId`, `mailType`, `isReportingTo`, `isReportingToManager`) VALUES
(1, 37, 'Invite for Onboarding', 'Invite for Onboarding', 'Invite@computronics.in', 'neelesh@computronics.in', 'vipul@computronics.in', 'vipul@computronics.in', 'AC', 'Invite@computronics.in', 'comp@123', 1, '2019-10-11', '2019-10-11', '2019-10-11', 1, 'IOB', 'N', 'N'),
(2, 38, 'Welcome onBoarding', 'Welcome onBoarding', 'Welcome@computronics.in', 'neelesh@computronics.in', 'vipul@computronics.in', 'vipul@computronics.in', 'AC', 'Welcome@computronics.in', 'comp@123', 300, '2019-10-15', '2019-10-15', '2019-10-15', 1, 'WOB', 'N', 'N'),
(3, 37, 'Birthday', 'Birthday', 'Greetings@computronics.in', 'neelesh@computronics.in', 'neeleshsharma71@gmail.com', 'vipul@computronics.in', 'AC', 'birthday@computronics.in', 'comp@123', 1, '2019-10-05', '2019-10-05', '2019-10-05', 1, 'HBD', 'N', 'Y'),
(4, 37, 'Work Anniversary', 'Work Anniversary', 'Greetings@computronics.in', 'neelesh@computronics.in', 'neeleshsharma71@gmail.com', 'vipul@computronics.in', 'AC', 'Greetings@computronics.in', 'comp@123', 1, '2019-10-14', '2019-10-14', '2019-10-14', 1, 'HWA', 'N', 'Y'),
(5, 37, 'Separation', 'Separation', 'alert@computronics.in', 'neelesh@computronics.in', 'neeleshsharma71@gmail.com', 'vipul@computronics.in', 'AC', 'alert@computronics.in', 'comp@123', 1, NULL, NULL, NULL, 1, 'SP', 'Y', 'Y'),
(6, 37, 'Leave', 'Leave', 'alert@computronics.in', 'neelesh@computronics.in', 'neelesh@computronics.in', 'vipul@computronics.in', 'AC', 'alert@computronics.in', 'comp@123', 1, '2019-10-10', '2019-10-10', '2019-10-10', 1, 'LV', 'N', 'Y'),
(7, 38, 'Compensatory Off', 'Compensatory Off', 'alert@computronics.in', 'neelesh@computronics.in', 'vipul@computronics.in', 'vipul@computronics.in', 'AC', 'alert@computronics.in', 'comp@123', 300, '2019-10-15', '2019-10-15', '2019-10-15', 1, 'CO', 'Y', 'Y'),
(8, 37, 'Attendance Regularize', 'Attendance Regularize', 'alert@computronics.in', 'neelesh@computronics.in', 'neeleshsharma71@gmail.com', 'vipul@computronics.in', 'AC', 'alert@computronics.in', 'comp@123', 1, '2019-10-09', '2019-10-09', '2019-10-09', 1, 'AR', 'N', 'Y'),
(9, 37, 'Late Comers', 'Late Comers', 'alert@computronics.in', 'neelesh@computronics.in', 'neeleshsharma71@gmail.com', 'vipul@computronics.in', 'AC', 'Notification@computronics.in', 'comp@123', 1, NULL, NULL, NULL, 1, 'LC', 'Y', 'Y');

-- --------------------------------------------------------

--
-- Table structure for table `Employee`
--

CREATE TABLE `Employee` (
  `employeeId` int(11) NOT NULL,
  `employeeCode` varchar(10) DEFAULT NULL,
  `firstName` varchar(50) NOT NULL,
  `middleName` varchar(50) DEFAULT NULL,
  `lastName` varchar(50) DEFAULT NULL,
  `dateOfJoining` date DEFAULT NULL,
  `endDate` date DEFAULT NULL,
  `dateOfBirth` date DEFAULT NULL,
  `anniversaryDate` date DEFAULT NULL,
  `probationDays` int(11) DEFAULT '0',
  `noticePeriodDays` int(11) DEFAULT '30',
  `gender` varchar(2) NOT NULL,
  `maritalStatus` varchar(2) DEFAULT NULL,
  `bloodGroup` varchar(2) DEFAULT NULL,
  `empType` varchar(2) DEFAULT NULL,
  `voluntaryPfContribution` varchar(1) DEFAULT 'N',
  `departmentId` int(11) NOT NULL,
  `designationId` int(11) NOT NULL,
  `projectId` int(11) DEFAULT NULL,
  `clientId` int(11) DEFAULT NULL,
  `ReportingToEmployee` varchar(50) DEFAULT NULL,
  `contractStartDate` date DEFAULT NULL,
  `cityId` int(11) DEFAULT NULL,
  `contractOverDate` date DEFAULT NULL,
  `permanentAddressId` int(11) DEFAULT NULL,
  `presentAddressId` int(11) DEFAULT NULL,
  `referenceName` char(20) DEFAULT NULL,
  `referenceAddressId` int(11) DEFAULT NULL,
  `effectiveDate` date DEFAULT NULL,
  `activeStatus` varchar(2) DEFAULT 'A',
  `adharNumber` varchar(15) DEFAULT NULL,
  `employeeLogoPath` varchar(50) DEFAULT NULL,
  `effectiveStartDate` datetime DEFAULT NULL,
  `effectiveEndDate` datetime DEFAULT NULL,
  `companyId` int(11) NOT NULL,
  `groupId` int(11) DEFAULT NULL,
  `allowModi` char(1) DEFAULT 'N',
  `userId` int(11) NOT NULL,
  `dateCreated` datetime DEFAULT NULL,
  `userIdUpdate` int(11) DEFAULT NULL,
  `dateUpdate` datetime DEFAULT NULL,
  `stateId` int(11) DEFAULT NULL,
  `gradesId` int(11) NOT NULL,
  `alternateNumber` varchar(20) DEFAULT NULL,
  `timeContract` varchar(2) DEFAULT NULL,
  `shiftId` int(11) DEFAULT NULL,
  `patternId` int(11) DEFAULT NULL,
  `contactNo` varchar(15) DEFAULT NULL,
  `officialEmail` varchar(50) DEFAULT NULL,
  `personalEmail` varchar(50) DEFAULT NULL,
  `tdsLockUnlockStatus` varchar(10) DEFAULT NULL,
  `tdsStatus` varchar(100) DEFAULT NULL,
  `UploadStatus` varchar(20) DEFAULT NULL,
  `leaveSchemeId` int(11) DEFAULT NULL,
  `biometricId` varchar(25) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Triggers `Employee`
--
DELIMITER $$
CREATE TRIGGER `employeePersonal_history_trigger` BEFORE UPDATE ON `Employee` FOR EACH ROW BEGIN

declare v_employeeId int(11) ;
 declare v_employeeCode varchar(10) ;
 declare v_employeeCode_new varchar(10) ;
declare  v_firstName varchar(50) ;
declare  v_firstName_new varchar(50) ;
declare  v_middleName varchar(50) ;
 declare v_lastName varchar(50) ;
  declare v_lastName_new varchar(50) ;
 declare v_dateOfJoining date ;
 declare v_dateOfJoining_new date ;
 declare  v_dateOfBirth date ;
 declare  v_dateOfBirth_new date ;
 declare v_anniversaryDate date ;
  declare v_anniversaryDate_new date ;
declare  v_probationDays int(11) ;
declare  v_probationDays_new int(11) ;
 declare v_noticePeriodDays int(11) ;
  declare v_noticePeriodDays_new int(11) ;
declare  v_gender varchar(2) ;
declare  v_maritalStatus varchar(2) ;
declare  v_bloodGroup varchar(2) ;
declare  v_empType varchar(2) ;
declare  v_empType_new varchar(2) ;
declare  v_voluntaryPfContribution varchar(1) ;
declare  v_voluntaryPfContribution_new varchar(1) ;
declare  v_departmentId int(11) ;
declare  v_departmentId_new int(11) ;
declare  v_designationId int(11) ;
declare  v_designationId_new int(11) ;
declare  v_projectId int(11) ;
declare  v_clientId int(11);
declare  v_cityId int(11) ;
declare  v_stateId int(11) ;
declare  v_ReportingToEmployee int(11) ;
declare  v_ReportingToEmployee_new int(11) ;
 declare v_contractStartDate date ;
 declare v_contractStartDate_new date ;
 declare v_contractOverDate date ;
  declare v_contractOverDate_new date ;
declare  v_permanentAddressId int(11) ;
declare  v_presentAddressId int(11) ;
declare  v_referenceName char(20) ;
declare  v_referenceAddressId int(11) ;
declare  v_effectiveDate date ;
declare  v_activeStatus varchar(2) ;
 declare v_adharNumber varchar(15) ;
 declare v_adharNumber_new varchar(15) ;
declare  v_employeeLogoPath varchar(50) ;
declare  v_effectiveStartDate datetime ;
declare  v_effectiveEndDate datetime ;
declare  v_companyId int(11) ;
declare  v_groupId int(11) ;
declare  v_allowModi char(1) ;
declare  v_userId int(11) ;
declare  v_dateCreated datetime ;
declare  v_userIdUpdate int(11) ;
declare  v_dateUpdate datetime ;

SET v_employeeId =OLD.employeeId ;
 SET v_employeeCode =OLD.employeeCode ;
 SET v_employeeCode_new =NEW.employeeCode ;
SET  v_firstName =OLD.firstName ;
SET  v_firstName_new =NEW.firstName ;
SET  v_middleName =OLD.middleName ;
 SET v_lastName =OLD.lastName ;
 SET v_lastName_new =NEW.lastName ;
 SET v_dateOfJoining =OLD.dateOfJoining ;
 SET v_dateOfJoining_new =NEW.dateOfJoining ;
 SET  v_dateOfBirth =OLD.dateOfBirth ;
 SET  v_dateOfBirth_new =NEW.dateOfBirth ;
 SET v_anniversaryDate =OLD.anniversaryDate ;
 SET v_anniversaryDate_new =NEW.anniversaryDate ;
SET  v_probationDays =OLD.probationDays ;
SET  v_probationDays_new =NEW.probationDays ;
 SET v_noticePeriodDays =OLD.noticePeriodDays ;
 SET v_noticePeriodDays_new =NEW.noticePeriodDays ;
SET  v_gender =OLD.gender ;
SET  v_maritalStatus =OLD.maritalStatus ;
SET  v_bloodGroup =OLD.bloodGroup ;
SET  v_empType =OLD.empType ;
SET  v_empType_new =NEW.empType ;
SET  v_voluntaryPfContribution =OLD.voluntaryPfContribution ;
SET  v_voluntaryPfContribution_new =NEW.voluntaryPfContribution ;
SET  v_departmentId =OLD.departmentId ;
SET  v_departmentId_new =NEW.departmentId ;
SET  v_designationId =OLD.designationId ;
SET  v_designationId_new =NEW.designationId ;
SET  v_projectId =OLD.projectId ;
SET  v_clientId =OLD.clientId;
SET  v_cityId =OLD.cityId ;
SET  v_stateId =OLD.stateId ;
SET  v_ReportingToEmployee =OLD.ReportingToEmployee ;
SET  v_ReportingToEmployee_new =NEW.ReportingToEmployee ;
 SET v_contractStartDate =OLD.contractStartDate ;
 SET v_contractStartDate_new =NEW.contractStartDate ;
 SET v_contractOverDate =OLD.contractOverDate ;
  SET v_contractOverDate_new =NEW.contractOverDate ;
SET  v_permanentAddressId =OLD.permanentAddressId ;
SET  v_presentAddressId =OLD.presentAddressId ;
SET  v_referenceName =OLD.referenceName ;
SET  v_referenceAddressId =OLD.referenceAddressId;
SET  v_effectiveDate =OLD.effectiveDate ;
SET  v_activeStatus =OLD.activeStatus ;
SET v_adharNumber =OLD.adharNumber ;
SET v_adharNumber_new =NEW.adharNumber ;
SET  v_employeeLogoPath =OLD.employeeLogoPath ;
SET  v_effectiveStartDate =OLD.effectiveStartDate ;
SET  v_effectiveEndDate =OLD.effectiveEndDate ;
SET  v_companyId =OLD.companyId ;
SET  v_groupId =OLD.groupId ;
SET  v_allowModi =OLD.allowModi ;
SET  v_userId =OLD.userId ;
SET  v_dateCreated =OLD.dateCreated ;
SET  v_userIdUpdate =NEW.userIdUpdate ;
SET  v_dateUpdate =OLD.dateCreated ;


 IF (
     
     (v_employeeCode_new <> v_employeeCode) or 
     (v_firstName_new <> v_firstName) or 
     (v_lastName_new <> v_lastName ) or 
     (v_dateOfJoining_new <> v_dateOfJoining) or 
     (v_dateOfBirth_new <> v_dateOfBirth ) or 
     (v_anniversaryDate_new <> v_anniversaryDate ) or 
     (v_probationDays_new <> v_probationDays ) or 
     (v_noticePeriodDays_new <> v_noticePeriodDays ) or 
     (v_empType_new <> v_empType ) or 
     (v_voluntaryPfContribution_new <> v_voluntaryPfContribution ) or 
     (v_departmentId_new <> v_departmentId ) or 
     (v_designationId_new <> v_designationId ) or 
     (v_ReportingToEmployee_new <> v_ReportingToEmployee ) or 
     (v_contractStartDate_new <> v_contractStartDate ) or 
     (v_contractOverDate_new <> v_contractOverDate ) or 
     (v_adharNumber_new <> v_adharNumber ) 
    ) 
     
     THEN
 
Insert INTO EmployeeHistory(

 employeeId ,
  employeeCode ,
  firstName ,
  middleName ,
  lastName ,
  dateOfJoining ,
   dateOfBirth ,
  anniversaryDate ,
  probationDays ,
  noticePeriodDays ,
  gender ,
  maritalStatus ,
  bloodGroup ,
  empType ,
  voluntaryPfContribution ,
  departmentId ,
  designationId ,
  projectId ,
  clientId ,
  cityId ,
  stateId ,
  ReportingToEmployee ,
  contractStartDate ,
  contractOverDate ,
  permanentAddressId ,
  presentAddressId ,
  referenceName ,
  referenceAddressId ,
  effectiveDate ,
  activeStatus ,
  adharNumber ,
  employeeLogoPath ,
  effectiveStartDate ,
  effectiveEndDate ,
  companyId ,
  groupId ,
  allowModi ,
  userId ,
  dateCreated ,
  userIdUpdate ,
  dateUpdate 
    
   ) 
   VALUES  (
 
 v_employeeId ,
  v_employeeCode_new ,
  v_firstName_new,
  v_middleName ,
  v_lastName_new ,
  v_dateOfJoining_new ,
   v_dateOfBirth_new ,
  v_anniversaryDate_new ,
  v_probationDays_new ,
 v_noticePeriodDays_new ,
  v_gender ,
  v_maritalStatus ,
  v_bloodGroup ,
  v_empType_new ,
  v_voluntaryPfContribution_new ,
  v_departmentId_new,
  v_designationId_new ,
  v_projectId,
  v_clientId ,
  v_cityId ,
  v_stateId ,
  v_ReportingToEmployee_new ,
  v_contractStartDate_new ,
   v_contractOverDate_new ,
  v_permanentAddressId ,
  v_presentAddressId ,
  v_referenceName,
  v_referenceAddressId ,
  v_effectiveDate ,
 v_activeStatus ,
   v_adharNumber_new ,
  v_employeeLogoPath ,
  v_effectiveStartDate ,
  v_effectiveEndDate,
  v_companyId ,
  v_groupId ,
  v_allowModi ,
  v_userId ,
  v_dateCreated ,
  v_userIdUpdate ,
 v_dateUpdate 
  
  );
 End IF  ;
End
$$
DELIMITER ;

-- --------------------------------------------------------

--
-- Table structure for table `EmployeeAssets`
--

CREATE TABLE `EmployeeAssets` (
  `employeeAssetsId` int(11) NOT NULL,
  `employeeId` int(11) NOT NULL,
  `itemId` int(11) NOT NULL,
  `issueDescription` varchar(50) NOT NULL,
  `quantity` int(11) DEFAULT '1',
  `amount` decimal(12,2) DEFAULT '0.00',
  `recievedRemark` varchar(500) DEFAULT NULL,
  `dateFrom` date DEFAULT NULL,
  `dateTo` date DEFAULT NULL,
  `companyId` int(11) NOT NULL,
  `activeStatus` varchar(2) DEFAULT 'A',
  `allowModi` char(1) DEFAULT 'N',
  `userId` int(11) NOT NULL,
  `dateCreated` datetime DEFAULT NULL,
  `userIdUpdate` int(11) DEFAULT NULL,
  `dateUpdate` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `EmployeeBank`
--

CREATE TABLE `EmployeeBank` (
  `EmployeeBankId` int(11) NOT NULL,
  `employeeId` int(11) NOT NULL,
  `accountType` varchar(2) NOT NULL,
  `bankId` varchar(2) NOT NULL,
  `accountNumber` varchar(50) DEFAULT NULL,
  `ifscCode` varchar(15) DEFAULT NULL,
  `bankBranch` varchar(50) DEFAULT NULL,
  `effectiveDate` date DEFAULT NULL,
  `allowModi` char(1) DEFAULT 'N',
  `activeStatus` varchar(2) NOT NULL DEFAULT 'AC',
  `userId` int(11) NOT NULL,
  `dateCreated` datetime DEFAULT NULL,
  `userIdUpdate` int(11) DEFAULT NULL,
  `dateUpdate` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Triggers `EmployeeBank`
--
DELIMITER $$
CREATE TRIGGER `employeeBank_history_trigger` BEFORE UPDATE ON `EmployeeBank` FOR EACH ROW BEGIN

 declare v_EmployeeBankId int(11) ;
 declare v_employeeId int(11) ;
 declare v_accountType varchar(2) ;
 declare v_bankId varchar(2) ;
 declare  v_accountNumber varchar(50) ;
 declare v_ifscCode varchar(15) ;
 declare v_bankBranch varchar(50) ;
 declare v_effectiveDate date ;
 declare v_allowModi char(1) ;
 declare v_activeStatus varchar(2) ;
 declare v_userId int(11) ;
 declare v_dateCreated datetime ;
 declare v_userIdUpdate int(11) ;
 declare v_dateUpdate datetime ;
 

 
  set v_EmployeeBankId =OLD.EmployeeBankId ;
 set v_employeeId =OLD.employeeId ;
 set v_accountType =OLD.accountType ;
 set v_bankId =OLD.bankId ;
 set  v_accountNumber =OLD.accountNumber ;
 set v_ifscCode =OLD.ifscCode ;
 set v_bankBranch =OLD.bankBranch ;
 set v_effectiveDate =OLD.effectiveDate ;
 set v_allowModi =OLD.allowModi ;
 set v_activeStatus =OLD.activeStatus ;
 set v_userId =OLD.userId ;
 set v_dateCreated =OLD.dateCreated ;
 set v_userIdUpdate =NEW.userIdUpdate ;
 set v_dateUpdate =OLD.dateUpdate ;
 
 
 
Insert INTO EmployeeBankHistory(

 EmployeeBankId ,
 employeeId ,
 accountType ,
 bankId ,
 accountNumber ,
 ifscCode ,
 bankBranch ,
 effectiveDate ,
 allowModi ,
 activeStatus ,
 userId ,
 dateCreated ,
 userIdUpdate ,
 dateUpdate 
    
   ) VALUES
  (
 
 v_EmployeeBankId ,
 v_employeeId ,
 v_accountType ,
 v_bankId ,
 v_accountNumber ,
 v_ifscCode ,
 v_bankBranch ,
 v_effectiveDate ,
 v_allowModi ,
 v_activeStatus ,
 v_userId ,
 v_dateCreated ,
 v_userIdUpdate ,
 v_dateUpdate 
      
      
  );
  
End
$$
DELIMITER ;

-- --------------------------------------------------------

--
-- Table structure for table `EmployeeBankHistory`
--

CREATE TABLE `EmployeeBankHistory` (
  `EmployeeBankId` int(11) NOT NULL,
  `employeeId` int(11) NOT NULL,
  `accountType` varchar(2) NOT NULL,
  `bankId` varchar(2) NOT NULL,
  `accountNumber` varchar(50) DEFAULT NULL,
  `ifscCode` varchar(15) DEFAULT NULL,
  `bankBranch` varchar(50) DEFAULT NULL,
  `effectiveDate` date DEFAULT NULL,
  `allowModi` char(1) DEFAULT 'N',
  `activeStatus` varchar(2) NOT NULL DEFAULT 'AC',
  `userId` int(11) NOT NULL,
  `dateCreated` datetime DEFAULT NULL,
  `userIdUpdate` int(11) DEFAULT NULL,
  `dateUpdate` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `EmployeeBulkUploadMaster`
--

CREATE TABLE `EmployeeBulkUploadMaster` (
  `indexNumber` int(11) NOT NULL,
  `columnHead` varchar(50) NOT NULL,
  `fileCode` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `EmployeeBulkUploadMaster`
--

INSERT INTO `EmployeeBulkUploadMaster` (`indexNumber`, `columnHead`, `fileCode`) VALUES
(1, 'First Name', 'EOB'),
(2, 'Middle Name', 'EOB'),
(3, 'Last Name', 'EOB'),
(4, 'Aadhar No.', 'EOB'),
(5, 'Contact No.', 'EOB'),
(6, 'Email ID', 'EOB'),
(7, 'Job Location', 'EOB'),
(8, 'Job Location ID', 'EOB'),
(9, 'State Location', 'EOB'),
(10, 'State Location ID', 'EOB'),
(11, 'Shift', 'EOB'),
(12, 'Shift ID', 'EOB'),
(13, 'Weekly Off Pattern', 'EOB'),
(14, 'Weekly Off Pattern ID', 'EOB'),
(15, 'Leave Scheme', 'EOB'),
(16, 'Leave Scheme ID', 'EOB'),
(17, 'Department', 'EOB'),
(18, 'Department ID', 'EOB'),
(19, 'Designation', 'EOB'),
(20, 'Designation ID', 'EOB'),
(21, 'Reporting To (Emp Code)', 'EOB'),
(22, 'Grade', 'EOB'),
(23, 'Grade ID', 'EOB'),
(27, 'DOJ(MM/DD/YYYY)', 'EOB'),
(28, 'Contract Start Date (DD/MM/YYYY)', 'EOB'),
(26, 'Permanent (PE)/Contract (CO)', 'EOB'),
(24, 'Probation Period', 'EOB'),
(25, 'Notice Period', 'EOB'),
(30, 'Full Time (FT) /Part Time (PT)', 'EOB'),
(31, 'System Role', 'EOB'),
(32, 'Role ID', 'EOB'),
(33, 'DOB(MM/DD/YYYY)', 'EOB'),
(34, 'Gender', 'EOB'),
(36, 'Blood Group', 'EOB'),
(37, 'Blood Group ID', 'EOB'),
(38, 'Alternate No', 'EOB'),
(0, 'Employee Code', 'EOB'),
(0, 'Employee Code', 'EIA'),
(1, 'ID Type', 'EIA'),
(2, 'ID', 'EIA'),
(3, 'ID Number', 'EIA'),
(4, 'Issue Date (MM/DD/YYYY)', 'EIA'),
(0, 'Employee Code', 'EF'),
(1, 'Title', 'EF'),
(2, 'Title ID', 'EF'),
(3, 'Name', 'EF'),
(4, 'Relation', 'EF'),
(5, 'Relation ID', 'EF'),
(6, 'Education', 'EF'),
(7, 'Education ID', 'EF'),
(0, 'Employee Code', 'EUP'),
(1, 'UAN Number', 'EUP'),
(2, 'PF Number', 'EUP'),
(3, 'Effective From(MM/DD/YYYY)', 'EUP'),
(0, 'Employee Code', 'EES'),
(1, 'ESIC Number', 'EES'),
(2, 'Effective From(MM/DD/YYYY)', 'EES'),
(0, 'Employee Code', 'EMI'),
(1, 'Medical Insurance', 'EMI'),
(2, 'Effective From(MM/DD/YYYY)', 'EMI'),
(3, 'Effective To(MM/DD/YYYY)', 'EMI'),
(0, 'Employee Code', 'EAI'),
(1, 'Accidental Insurance', 'EAI'),
(2, 'Effective From(MM/DD/YYYY)', 'EAI'),
(3, 'Effective To(MM/DD/YYYY)', 'EAI'),
(0, 'Employee Code', 'EP'),
(1, 'Organization Name', 'EP'),
(2, 'Working From(MM/DD/YYYY)', 'EP'),
(3, 'Working To(MM/DD/YYYY)', 'EP'),
(4, 'Designation', 'EP'),
(5, 'Reporting To', 'EP'),
(6, 'Contact No', 'EP'),
(7, 'Annual Salary', 'EP'),
(8, 'Reason for Change', 'EP'),
(0, 'Employee Code', 'EE'),
(1, 'Education Level', 'EE'),
(2, 'Education Level ID', 'EE'),
(3, 'Degree', 'EE'),
(4, 'Degree ID', 'EE'),
(5, 'School/College', 'EE'),
(6, 'Board/University', 'EE'),
(7, 'Marks/Grade', 'EE'),
(0, 'Employee Code', 'EL'),
(1, 'Language', 'EL'),
(2, 'Language ID', 'EL'),
(3, 'Read', 'EL'),
(4, 'Write', 'EL'),
(5, 'Expiry Date (MM/DD/YYYY)', 'EIA'),
(5, 'Speak', 'EL'),
(9, 'Reason for Change ID', 'EP'),
(8, 'Year Of Passing', 'EE'),
(9, 'Regular/Corresponding', 'EE'),
(8, 'Occupation', 'EF'),
(9, 'Occupation ID', 'EF'),
(10, 'Date Of Birth(MM/DD/YYYY)', 'EF'),
(11, 'Contact Number', 'EF'),
(29, 'Contract End Date (MM/DD/YYYY)', 'EOB'),
(35, 'Gender ID', 'EOB'),
(39, 'Marital Status', 'EOB'),
(40, 'Marital Status ID', 'EOB'),
(50, 'Present Address', 'EOB'),
(58, 'Reference Details', 'EOB'),
(70, 'Bank ID', 'EOB'),
(71, 'Account No', 'EOB'),
(69, 'Bank Name', 'EOB'),
(0, 'Employee Code', 'EOP'),
(1, 'Gross Pay', 'EOP'),
(2, 'Process Month', 'EOP'),
(3, 'Net Pay', 'EOP'),
(4, 'CTC', 'EOP'),
(5, 'Employee ESI', 'EOP'),
(6, 'Employer ESI', 'EOP'),
(7, 'Employee EPF', 'EOP'),
(8, 'Employer EPF', 'EOP'),
(9, 'Professional Tax', 'EOP'),
(10, 'CTC', 'EOP'),
(11, 'Pay Head ID', 'EOP'),
(12, 'Amount', 'EOP'),
(13, 'PF Status', 'EOP'),
(14, 'ESI Status', 'EOP'),
(4, 'Effective To(MM/DD/YYYY)', 'EUP'),
(3, 'Effective To(MM/DD/YYYY)', 'EES'),
(72, 'Branch', 'EOB'),
(73, 'IFSC Code', 'EOB'),
(41, 'Anniversary Date (MM/DD/YYYY)', 'EOB'),
(42, 'Permanent Address', 'EOB'),
(4, 'Effective To(MM/DD/YYYY)', 'EUP'),
(3, 'Effective To(MM/DD/YYYY)', 'EES');

-- --------------------------------------------------------

--
-- Table structure for table `EmployeeDocuments`
--

CREATE TABLE `EmployeeDocuments` (
  `employeeDocumentsId` int(11) NOT NULL,
  `employeeId` int(11) NOT NULL,
  `DocumentsId` varchar(2) NOT NULL,
  `description` varchar(50) NOT NULL,
  `fileLocation` varchar(100) NOT NULL,
  `activeStatus` varchar(2) DEFAULT 'A',
  `allowModi` char(1) DEFAULT 'N',
  `userId` int(11) NOT NULL,
  `dateCreated` datetime DEFAULT NULL,
  `userIdUpdate` int(11) DEFAULT NULL,
  `dateUpdate` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `EmployeeEducation`
--

CREATE TABLE `EmployeeEducation` (
  `educationId` int(11) NOT NULL,
  `employeeId` int(11) NOT NULL,
  `qualificationId` varchar(2) NOT NULL,
  `degreeName` varchar(50) DEFAULT NULL,
  `nameOfInstitution` varchar(50) DEFAULT NULL,
  `nameOfBoard` varchar(50) DEFAULT NULL,
  `marksPer` decimal(5,2) DEFAULT NULL,
  `passingYear` int(11) DEFAULT NULL,
  `regularCorrespondance` varchar(2) DEFAULT 'R',
  `effectiveStartDate` datetime DEFAULT NULL,
  `effectiveEndDate` datetime DEFAULT NULL,
  `activeStatus` varchar(2) DEFAULT NULL,
  `companyId` int(11) DEFAULT NULL,
  `groupId` int(11) DEFAULT NULL,
  `allowModi` char(1) DEFAULT 'N',
  `userId` int(11) DEFAULT NULL,
  `dateCreated` datetime DEFAULT NULL,
  `userIdUpdate` int(11) DEFAULT NULL,
  `dateUpdate` datetime DEFAULT NULL,
  `documentName` varchar(500) DEFAULT NULL,
  `employeeEducationDoc` varchar(500) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `EmployeeFamily`
--

CREATE TABLE `EmployeeFamily` (
  `familyId` int(11) NOT NULL,
  `employeeId` int(11) NOT NULL,
  `relation` varchar(2) DEFAULT NULL,
  `captions` varchar(2) DEFAULT NULL,
  `name` varchar(50) NOT NULL,
  `qualificationId` varchar(2) DEFAULT NULL,
  `occupations` varchar(2) DEFAULT NULL,
  `dateOfBirth` date DEFAULT NULL,
  `contactPhone` varchar(10) DEFAULT NULL,
  `contactMobile` varchar(10) DEFAULT NULL,
  `allowModi` char(1) DEFAULT 'N',
  `userId` int(11) NOT NULL,
  `dateCreated` datetime DEFAULT NULL,
  `userIdUpdate` int(11) DEFAULT NULL,
  `dateUpdate` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `EmployeeHistory`
--

CREATE TABLE `EmployeeHistory` (
  `employeeHistoryId` int(11) NOT NULL,
  `employeeId` int(11) NOT NULL,
  `employeeCode` varchar(10) DEFAULT NULL,
  `firstName` varchar(50) NOT NULL,
  `middleName` varchar(50) DEFAULT NULL,
  `lastName` varchar(50) DEFAULT NULL,
  `dateOfJoining` date NOT NULL,
  `dateOfBirth` date NOT NULL,
  `anniversaryDate` date DEFAULT NULL,
  `probationDays` int(11) DEFAULT '0',
  `noticePeriodDays` int(11) DEFAULT '30',
  `gender` varchar(2) NOT NULL,
  `maritalStatus` varchar(2) NOT NULL,
  `bloodGroup` varchar(2) DEFAULT NULL,
  `empType` varchar(2) NOT NULL,
  `voluntaryPfContribution` varchar(1) DEFAULT 'N',
  `departmentId` int(11) NOT NULL,
  `designationId` int(11) NOT NULL,
  `projectId` int(11) DEFAULT NULL,
  `clientId` int(11) DEFAULT NULL,
  `cityId` int(11) DEFAULT NULL,
  `stateId` int(11) DEFAULT NULL,
  `ReportingToEmployee` int(11) DEFAULT NULL,
  `contractStartDate` date DEFAULT NULL,
  `contractOverDate` date DEFAULT NULL,
  `permanentAddressId` int(11) DEFAULT NULL,
  `presentAddressId` int(11) DEFAULT NULL,
  `referenceName` char(20) DEFAULT NULL,
  `referenceAddressId` int(11) DEFAULT NULL,
  `effectiveDate` date DEFAULT NULL,
  `activeStatus` varchar(2) DEFAULT 'A',
  `adharNumber` varchar(15) DEFAULT NULL,
  `employeeLogoPath` varchar(50) DEFAULT NULL,
  `effectiveStartDate` datetime DEFAULT NULL,
  `effectiveEndDate` datetime DEFAULT NULL,
  `companyId` int(11) NOT NULL,
  `groupId` int(11) NOT NULL,
  `allowModi` char(1) DEFAULT 'N',
  `userId` int(11) NOT NULL,
  `dateCreated` datetime DEFAULT NULL,
  `userIdUpdate` int(11) DEFAULT NULL,
  `dateUpdate` datetime DEFAULT NULL,
  `biometricId` varchar(25) DEFAULT NULL,
  `leaveSchemeId` varchar(25) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `EmployeeIdProofs`
--

CREATE TABLE `EmployeeIdProofs` (
  `employeeIdProofsId` int(11) NOT NULL,
  `employeeId` int(11) NOT NULL,
  `idTypeId` varchar(2) NOT NULL,
  `idNumber` varchar(20) NOT NULL,
  `dateIssue` date DEFAULT NULL,
  `dateFrom` date DEFAULT NULL,
  `dateTo` date DEFAULT NULL,
  `activeStatus` varchar(2) DEFAULT 'A',
  `allowModi` char(1) DEFAULT 'N',
  `userId` int(11) NOT NULL,
  `dateCreated` datetime DEFAULT NULL,
  `userIdUpdate` int(11) DEFAULT NULL,
  `dateUpdate` datetime DEFAULT NULL,
  `idProofDoc` varchar(250) DEFAULT NULL,
  `documentName` varchar(250) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Triggers `EmployeeIdProofs`
--
DELIMITER $$
CREATE TRIGGER `employeeIdProofs_history_trigger` BEFORE UPDATE ON `EmployeeIdProofs` FOR EACH ROW BEGIN

declare v_employeeIdProofsId int(11) ;
declare  v_employeeId int(11) ;
declare  v_idTypeId varchar(2) ;
 declare v_idNumber varchar(20) ;
 declare v_dateIssue date ;
declare  v_dateFrom date ;
declare  v_dateTo date ;
 declare v_activeStatus varchar(2) ;
declare  v_allowModi char(1) ;
declare  v_userId int(11) ;
declare  v_dateCreated datetime ;
 declare v_userIdUpdate int(11) ;
 declare v_dateUpdate datetime ;
 
 
 
 set v_employeeIdProofsId =OLD.employeeIdProofsId;
set  v_employeeId =OLD.employeeId  ;
set  v_idTypeId =OLD.idTypeId ;
 set v_idNumber =OLD.idNumber ;
 set v_dateIssue =OLD.dateIssue ;
set  v_dateFrom =OLD.dateFrom ;
set  v_dateTo =OLD.dateTo ;
 set v_activeStatus =OLD.activeStatus ;
set  v_allowModi =OLD.allowModi ;
set  v_userId =OLD.userId ;
set  v_dateCreated =OLD.dateCreated ;
 set v_userIdUpdate =NEW.userIdUpdate ;
 set v_dateUpdate =OLD.dateUpdate ;
 
 
Insert INTO EmployeeIdProofsHistory(

employeeIdProofsId ,
employeeId ,
idTypeId ,
 idNumber ,
 dateIssue ,
dateFrom ,
dateTo ,
 activeStatus ,
allowModi ,
userId ,
dateCreated ,
 userIdUpdate ,
dateUpdate 
    
   ) VALUES
  (
      
v_employeeIdProofsId ,
v_employeeId ,
v_idTypeId ,
 v_idNumber ,
 v_dateIssue ,
v_dateFrom ,
v_dateTo ,
 v_activeStatus ,
v_allowModi ,
v_userId ,
v_dateCreated ,
 v_userIdUpdate ,
v_dateUpdate 
      
  );
End
$$
DELIMITER ;

-- --------------------------------------------------------

--
-- Table structure for table `EmployeeIdProofsHistory`
--

CREATE TABLE `EmployeeIdProofsHistory` (
  `employeeIdProofsHistoryId` int(11) NOT NULL,
  `employeeIdProofsId` int(11) NOT NULL,
  `employeeId` int(11) NOT NULL,
  `idTypeId` varchar(2) NOT NULL,
  `idNumber` varchar(20) NOT NULL,
  `dateIssue` date DEFAULT NULL,
  `dateFrom` date DEFAULT NULL,
  `dateTo` date DEFAULT NULL,
  `activeStatus` varchar(2) DEFAULT 'A',
  `allowModi` char(1) DEFAULT 'N',
  `userId` int(11) NOT NULL,
  `dateCreated` datetime DEFAULT NULL,
  `userIdUpdate` int(11) DEFAULT NULL,
  `dateUpdate` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `EmployeeLanguage`
--

CREATE TABLE `EmployeeLanguage` (
  `employeeLanguageId` int(11) NOT NULL,
  `employeeId` int(11) NOT NULL,
  `languageId` int(11) NOT NULL,
  `langRead` varchar(2) DEFAULT NULL,
  `langWrite` varchar(2) DEFAULT NULL,
  `langSpeak` varchar(2) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `EmployeeNominee`
--

CREATE TABLE `EmployeeNominee` (
  `employeeNomineeid` int(11) NOT NULL,
  `employeeId` int(11) NOT NULL,
  `familyId` int(11) NOT NULL,
  `staturyHeadId` varchar(2) DEFAULT NULL,
  `staturyHeadName` varchar(250) DEFAULT NULL,
  `activeStatus` varchar(2) DEFAULT NULL,
  `userId` int(11) DEFAULT NULL,
  `dateCreated` datetime NOT NULL,
  `userIdUpdate` int(11) DEFAULT NULL,
  `dateUpdate` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `EmployeeOpeningLeaveMaster`
--

CREATE TABLE `EmployeeOpeningLeaveMaster` (
  `empOpeningId` int(11) NOT NULL,
  `noOfOpening` decimal(11,2) DEFAULT NULL,
  `employeeId` int(11) NOT NULL,
  `leavePeriodId` int(11) NOT NULL,
  `leaveTypeMasterId` int(11) NOT NULL,
  `status` varchar(3) DEFAULT NULL,
  `companyId` int(11) NOT NULL,
  `activeStatus` varchar(2) DEFAULT NULL,
  `userId` int(11) NOT NULL,
  `dateCreated` date DEFAULT NULL,
  `dateUpdate` date DEFAULT NULL,
  `userIdUpdate` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `EmployeeSkills`
--

CREATE TABLE `EmployeeSkills` (
  `employeeSkillsId` int(11) NOT NULL,
  `employeeId` int(11) NOT NULL,
  `skillId` int(11) NOT NULL,
  `activeStatus` varchar(2) DEFAULT 'A',
  `allowModi` char(1) DEFAULT 'N',
  `userId` int(11) NOT NULL,
  `dateCreated` datetime DEFAULT NULL,
  `userIdUpdate` int(11) DEFAULT NULL,
  `dateUpdate` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `EmployeeStatuary`
--

CREATE TABLE `EmployeeStatuary` (
  `statuaryId` int(11) NOT NULL,
  `employeeId` int(11) NOT NULL,
  `statuaryType` varchar(2) NOT NULL,
  `statuaryNumber` varchar(100) DEFAULT NULL,
  `familyId` int(11) DEFAULT NULL,
  `isApplicable` varchar(2) DEFAULT NULL,
  `dateFrom` date DEFAULT NULL,
  `dateTo` date DEFAULT NULL,
  `status` varchar(5) NOT NULL,
  `effectiveStartDate` date DEFAULT NULL,
  `effectiveEndDate` date DEFAULT NULL,
  `allowModi` char(1) DEFAULT 'N',
  `userId` int(11) DEFAULT NULL,
  `dateCreated` datetime DEFAULT NULL,
  `userIdUpdate` int(11) DEFAULT NULL,
  `dateUpdate` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Triggers `EmployeeStatuary`
--
DELIMITER $$
CREATE TRIGGER `employeeStatuary_history_trigger` BEFORE UPDATE ON `EmployeeStatuary` FOR EACH ROW BEGIN

declare  v_statuaryId int(11) ;
 declare v_employeeId int(11) ;
declare  v_statuaryType varchar(2) ;
declare  v_statuaryNumber varchar(50) ;
declare  v_familyId int(11) ;
declare  v_dateFrom date ;
declare  v_dateTo date ;
 declare v_allowModi char(1) ;
 declare v_userId int(11) ;
declare  v_dateCreated datetime ;
 declare v_userIdUpdate int(11) ;
 declare v_dateUpdate datetime ;
 
 set  v_statuaryId =OLD.statuaryId ;
 set v_employeeId =OLD.employeeId ;
set  v_statuaryType =OLD.statuaryType ;
set  v_statuaryNumber =OLD.statuaryNumber ;
set  v_familyId =OLD.familyId ;
set  v_dateFrom =OLD.dateFrom ;
set  v_dateTo =OLD.dateTo ;
 set v_allowModi =OLD.allowModi ;
 set v_userId =OLD.userId ;
set  v_dateCreated =OLD.dateCreated ;
 set v_userIdUpdate =NEW.userIdUpdate ;
 set v_dateUpdate =OLD.dateUpdate ;
 
Insert INTO EmployeeStatuaryHistory(

 statuaryId ,
 employeeId ,
statuaryType ,
statuaryNumber ,
familyId ,
dateFrom ,
dateTo ,
 allowModi ,
userId ,
dateCreated ,
userIdUpdate ,
 dateUpdate 
    
   ) VALUES
  (
 
  v_statuaryId ,
 v_employeeId ,
v_statuaryType ,
v_statuaryNumber ,
v_familyId ,
v_dateFrom ,
v_dateTo ,
 v_allowModi ,
v_userId ,
v_dateCreated ,
v_userIdUpdate ,
 v_dateUpdate 
      
  );
  
End
$$
DELIMITER ;

-- --------------------------------------------------------

--
-- Table structure for table `EmployeeStatuaryHistory`
--

CREATE TABLE `EmployeeStatuaryHistory` (
  `statuaryId` int(11) NOT NULL,
  `employeeId` int(11) NOT NULL,
  `statuaryType` varchar(2) NOT NULL,
  `statuaryNumber` varchar(50) DEFAULT NULL,
  `familyId` int(11) DEFAULT NULL,
  `dateFrom` date DEFAULT NULL,
  `dateTo` date DEFAULT NULL,
  `allowModi` char(1) DEFAULT 'N',
  `userId` int(11) NOT NULL,
  `dateCreated` datetime DEFAULT NULL,
  `userIdUpdate` int(11) DEFAULT NULL,
  `dateUpdate` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `EntryInAuditTable`
--

CREATE TABLE `EntryInAuditTable` (
  `technicalTableName` varchar(50) NOT NULL,
  `functionalDescription` varchar(500) NOT NULL,
  `logAudit` char(1) NOT NULL DEFAULT 'Y',
  `allowModi` char(1) DEFAULT 'N',
  `userId` int(11) NOT NULL,
  `dateCreated` datetime DEFAULT NULL,
  `userIdUpdate` int(11) DEFAULT NULL,
  `dateUpdate` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `Epf`
--

CREATE TABLE `Epf` (
  `epfId` int(11) NOT NULL,
  `epfName` varchar(50) DEFAULT NULL,
  `maxBasicLimit` decimal(12,2) DEFAULT '0.00',
  `employeePer` decimal(5,2) DEFAULT '0.00',
  `employerPer` decimal(5,2) DEFAULT '0.00',
  `employerPensionPer` decimal(5,2) DEFAULT '0.00',
  `adminPer` decimal(5,2) DEFAULT '0.00',
  `edliPer` decimal(5,2) DEFAULT '0.00',
  `edliExpPer` decimal(5,2) DEFAULT '0.00',
  `activeStatus` varchar(2) DEFAULT 'A',
  `effectiveDate` date NOT NULL,
  `companyId` int(11) NOT NULL,
  `groupId` int(11) DEFAULT NULL,
  `effectiveStartDate` datetime DEFAULT NULL,
  `effectiveEndDate` datetime DEFAULT NULL,
  `allowModi` char(1) DEFAULT 'N',
  `userId` int(11) NOT NULL,
  `dateCreated` datetime DEFAULT NULL,
  `userIdUpdate` int(11) DEFAULT NULL,
  `dateUpdate` datetime DEFAULT NULL,
  `maxPensionLimit` decimal(12,2) DEFAULT '0.00',
  `isActual` varchar(2) NOT NULL DEFAULT 'N'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `Epf`
--

INSERT INTO `Epf` (`epfId`, `epfName`, `maxBasicLimit`, `employeePer`, `employerPer`, `employerPensionPer`, `adminPer`, `edliPer`, `edliExpPer`, `activeStatus`, `effectiveDate`, `companyId`, `groupId`, `effectiveStartDate`, `effectiveEndDate`, `allowModi`, `userId`, `dateCreated`, `userIdUpdate`, `dateUpdate`, `maxPensionLimit`, `isActual`) VALUES
(1, NULL, '15000.00', '12.00', '3.67', '8.33', '0.50', '0.50', '0.00', 'AC', '2019-05-01', 1, NULL, NULL, NULL, NULL, 1, '2019-07-25 11:31:43', 1, '2019-07-30 06:46:36', '15000.00', 'N');

--
-- Triggers `Epf`
--
DELIMITER $$
CREATE TRIGGER `epf_history_trigger` BEFORE UPDATE ON `Epf` FOR EACH ROW BEGIN

 declare v_epfId int(11) ;
 declare v_epfName varchar(50) ;
 declare v_maxBasicLimit decimal(12,2) ;
 declare v_employeePer decimal(5,2) ;
 declare v_employerPer decimal(5,2);
 declare v_employerPensionPer decimal(5,2) ;
 declare v_adminPer decimal(5,2) ;
 declare v_edliPer decimal(5,2) ;
 declare v_edliExpPer decimal(5,2) ;
 declare v_maxPensionLimit decimal(12,2) ;
 declare v_isActual varchar(2) ;
 declare v_activeStatus varchar(2) ;
 declare v_effectiveDate date ;
 declare v_companyId int(11) ;
 declare v_groupId int(11) ;
 declare v_effectiveStartDate datetime ;
 declare v_effectiveEndDate datetime ;
 declare v_allowModi char(1) ;
 declare v_userId int(11) ;
 declare v_dateCreated datetime ;
 declare v_userIdUpdate int(11) ;
 declare v_dateUpdate datetime ;


set v_epfId =OLD.epfId ;
 set v_epfName =OLD.epfName ;
 set v_maxBasicLimit =OLD.maxBasicLimit ;
 set v_employeePer =OLD.employeePer ;
 set v_employerPer =OLD.employerPer ;
 set v_employerPensionPer = OLD.employerPensionPer ;
 set v_adminPer =OLD.adminPer ;
 set v_edliPer =OLD.edliPer ;
 set v_edliExpPer =OLD.edliExpPer ;
 set v_maxPensionLimit =OLD.maxPensionLimit ;
 set v_isActual =OLD.isActual ;
 set v_activeStatus =OLD.activeStatus ;
 set v_effectiveDate =OLD.effectiveDate ;
 set v_companyId =OLD.companyId ;
 set v_groupId =OLD.groupId ;
 set v_effectiveStartDate =OLD.effectiveStartDate ;
 set v_effectiveEndDate =OLD.effectiveEndDate ;
 set v_allowModi =OLD.allowModi ;
 set v_userId =OLD.userId ;
 set v_dateCreated =OLD.dateCreated ;
 set v_userIdUpdate =NEW.userIdUpdate ;
 set v_dateUpdate =OLD.dateUpdate ;



Insert INTO EpfHistory(

   epfId,
 epfName ,
 maxBasicLimit ,
 employeePer ,
 employerPer ,
 employerPensionPer ,
 adminPer,
 edliPer ,
 edliExpPer ,
maxPensionLimit ,
 isActual ,
 activeStatus ,
 effectiveDate ,
 companyId ,
 groupId,
 effectiveStartDate ,
 effectiveEndDate ,
 allowModi ,
 userId ,
 dateCreated ,
 userIdUpdate ,
 dateUpdate 
    
   ) VALUES
  (
 
    v_epfId,
 v_epfName ,
 v_maxBasicLimit ,
 v_employeePer ,
 v_employerPer ,
 v_employerPensionPer ,
 v_adminPer,
 v_edliPer ,
 v_edliExpPer ,
v_maxPensionLimit ,
 v_isActual ,
 v_activeStatus ,
 v_effectiveDate ,
 v_companyId ,
 v_groupId,
 v_effectiveStartDate ,
 v_effectiveEndDate ,
 v_allowModi ,
 v_userId ,
 v_dateCreated ,
 v_userIdUpdate ,
 v_dateUpdate 
      
  );
End
$$
DELIMITER ;

-- --------------------------------------------------------

--
-- Table structure for table `EpfHistory`
--

CREATE TABLE `EpfHistory` (
  `epfId` int(11) NOT NULL,
  `epfName` varchar(50) DEFAULT NULL,
  `maxBasicLimit` decimal(12,2) DEFAULT '0.00',
  `employeePer` decimal(5,2) DEFAULT '0.00',
  `employerPer` decimal(5,2) DEFAULT '0.00',
  `employerPensionPer` decimal(5,2) DEFAULT '0.00',
  `adminPer` decimal(5,2) DEFAULT '0.00',
  `edliPer` decimal(5,2) DEFAULT '0.00',
  `edliExpPer` decimal(5,2) DEFAULT '0.00',
  `maxPensionLimit` decimal(12,2) DEFAULT '0.00',
  `isActual` varchar(2) NOT NULL DEFAULT 'N',
  `activeStatus` varchar(2) DEFAULT 'A',
  `effectiveDate` date NOT NULL,
  `companyId` int(11) NOT NULL,
  `groupId` int(11) DEFAULT NULL,
  `effectiveStartDate` datetime DEFAULT NULL,
  `effectiveEndDate` datetime DEFAULT NULL,
  `allowModi` char(1) DEFAULT 'N',
  `userId` int(11) NOT NULL,
  `dateCreated` datetime DEFAULT NULL,
  `userIdUpdate` int(11) DEFAULT NULL,
  `dateUpdate` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `EpfHistory`
--

INSERT INTO `EpfHistory` (`epfId`, `epfName`, `maxBasicLimit`, `employeePer`, `employerPer`, `employerPensionPer`, `adminPer`, `edliPer`, `edliExpPer`, `maxPensionLimit`, `isActual`, `activeStatus`, `effectiveDate`, `companyId`, `groupId`, `effectiveStartDate`, `effectiveEndDate`, `allowModi`, `userId`, `dateCreated`, `userIdUpdate`, `dateUpdate`) VALUES
(1, 'dd', '10.00', '10.00', '10.00', '10.00', '10.00', '10.00', '10.00', '10.00', 'N', 'AC', '2019-05-01', 1, NULL, '2019-05-22 00:00:00', '2019-05-15 00:00:00', 'N', 1, NULL, NULL, NULL),
(1, 'dd', '15000.00', '10.00', '10.00', '10.00', '10.00', '10.00', '10.00', '10.00', 'N', 'AC', '2019-05-01', 1, NULL, '2019-05-22 00:00:00', '2019-05-15 00:00:00', 'N', 1, NULL, NULL, NULL),
(1, 'dd', '15000.00', '12.00', '10.00', '10.00', '10.00', '10.00', '10.00', '10.00', 'N', 'AC', '2019-05-01', 1, NULL, '2019-05-22 00:00:00', '2019-05-15 00:00:00', 'N', 1, NULL, NULL, NULL),
(1, 'dd', '15000.00', '12.00', '3.67', '10.00', '10.00', '10.00', '10.00', '10.00', 'N', 'AC', '2019-05-01', 1, NULL, '2019-05-22 00:00:00', '2019-05-15 00:00:00', 'N', 1, NULL, NULL, NULL),
(1, 'dd', '15000.00', '12.00', '3.67', '8.33', '10.00', '10.00', '10.00', '10.00', 'N', 'AC', '2019-05-01', 1, NULL, '2019-05-22 00:00:00', '2019-05-15 00:00:00', 'N', 1, NULL, NULL, NULL),
(1, 'dd', '15000.00', '12.00', '3.67', '8.33', '0.50', '10.00', '10.00', '10.00', 'N', 'AC', '2019-05-01', 1, NULL, '2019-05-22 00:00:00', '2019-05-15 00:00:00', 'N', 1, NULL, NULL, NULL),
(1, 'dd', '15000.00', '12.00', '3.67', '8.33', '0.50', '0.50', '10.00', '10.00', 'N', 'AC', '2019-05-01', 1, NULL, '2019-05-22 00:00:00', '2019-05-15 00:00:00', 'N', 1, NULL, NULL, NULL),
(1, 'dd', '15000.00', '12.00', '3.67', '8.33', '0.50', '0.50', '0.00', '10.00', 'N', 'AC', '2019-05-01', 1, NULL, '2019-05-22 00:00:00', '2019-05-15 00:00:00', 'N', 1, NULL, NULL, NULL),
(1, 'dd', '15000.00', '12.00', '3.67', '8.33', '0.50', '0.50', '0.00', '15000.00', 'N', 'AC', '2019-05-01', 1, NULL, '2019-05-22 00:00:00', '2019-05-15 00:00:00', 'N', 1, NULL, NULL, NULL),
(1, 'dd', '15000.00', '12.00', '3.67', '8.33', '0.50', '0.50', '0.00', '15000.00', 'N', 'AC', '2019-05-01', 1, NULL, '2019-05-22 00:00:00', '2019-05-15 00:00:00', 'N', 1, NULL, NULL, NULL),
(1, 'dd', '15000.00', '12.00', '3.67', '8.33', '0.50', '0.50', '0.00', '15000.00', 'N', 'AC', '2019-05-01', 1, NULL, '2019-05-22 00:00:00', '2019-05-15 00:00:00', 'N', 1, NULL, 1, NULL),
(1, NULL, '15000.00', '12.00', '3.67', '8.33', '0.50', '0.50', '0.00', '15000.00', 'Y', 'AC', '2019-05-01', 1, NULL, NULL, NULL, NULL, 1, '2019-07-25 11:31:43', 1, '2019-07-25 11:31:43');

-- --------------------------------------------------------

--
-- Table structure for table `Esi`
--

CREATE TABLE `Esi` (
  `esiId` int(11) NOT NULL,
  `esiName` varchar(50) DEFAULT NULL,
  `maxGrossLimit` decimal(12,2) DEFAULT '0.00',
  `employeePer` decimal(5,2) DEFAULT '0.00',
  `employerPer` decimal(5,2) DEFAULT '0.00',
  `effectiveStartDate` datetime DEFAULT NULL,
  `effectiveEndDate` datetime DEFAULT NULL,
  `activeStatus` varchar(2) DEFAULT '',
  `effectiveDate` date NOT NULL,
  `companyId` int(11) NOT NULL,
  `groupId` int(11) DEFAULT NULL,
  `allowModi` char(1) DEFAULT 'N',
  `userId` int(11) NOT NULL,
  `dateCreated` datetime DEFAULT NULL,
  `userIdUpdate` int(11) DEFAULT NULL,
  `dateUpdate` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `Esi`
--

INSERT INTO `Esi` (`esiId`, `esiName`, `maxGrossLimit`, `employeePer`, `employerPer`, `effectiveStartDate`, `effectiveEndDate`, `activeStatus`, `effectiveDate`, `companyId`, `groupId`, `allowModi`, `userId`, `dateCreated`, `userIdUpdate`, `dateUpdate`) VALUES
(71, NULL, '21000.00', '0.75', '3.25', NULL, NULL, 'DE', '2019-07-25', 1, NULL, NULL, 467, '2019-07-02 18:34:50', 1, '2019-07-25 11:35:13'),
(72, NULL, '21000.00', '0.75', '3.25', NULL, NULL, 'AC', '2019-07-25', 1, NULL, NULL, 467, '2019-07-25 11:35:13', 1, '2019-07-25 11:35:13');

--
-- Triggers `Esi`
--
DELIMITER $$
CREATE TRIGGER `esi_history_trigger` BEFORE UPDATE ON `Esi` FOR EACH ROW BEGIN

declare  v_esiId int(11) ;
 declare v_esiName varchar(50) ;
 declare v_maxGrossLimit decimal(12,2) ;
declare  v_employeePer decimal(5,2) ;
 declare v_employerPer decimal(5,2) ;
 declare v_effectiveStartDate datetime ;
declare  v_effectiveEndDate datetime ;
 declare v_activeStatus varchar(2) ;
 declare v_effectiveDate date ;
declare  v_companyId int(11) ;
declare  v_groupId int(11) ;
declare  v_allowModi char(1) ;
declare  v_userId int(11) ;
declare  v_dateCreated datetime ;
declare  v_userIdUpdate int(11) ;
declare  v_dateUpdate datetime ;


SET  v_esiId =OLD.esiId ;
 SET v_esiName =OLD.esiName ;
 SET v_maxGrossLimit =OLD.maxGrossLimit ;
SET  v_employeePer =OLD.employeePer;
 SET v_employerPer =OLD.employerPer ;
 SET v_effectiveStartDate =OLD.effectiveStartDate ;
SET  v_effectiveEndDate =OLD.effectiveEndDate ;
 SET v_activeStatus =OLD.activeStatus ;
 SET v_effectiveDate =OLD.effectiveDate ;
SET  v_companyId =OLD.companyId ;
SET  v_groupId =OLD.groupId ;
SET  v_allowModi =OLD.allowModi ;
SET  v_userId =OLD.userId ;
SET  v_dateCreated =OLD.dateCreated ;
SET  v_userIdUpdate =NEW.userIdUpdate ;
SET  v_dateUpdate =OLD.dateUpdate ;





Insert INTO EsiHistory(

  esiId ,
esiName ,
 maxGrossLimit ,
employeePer ,
 employerPer ,
 effectiveStartDate ,
effectiveEndDate ,
 activeStatus ,
 effectiveDate,
companyId,
groupId ,
allowModi,
userId ,
dateCreated ,
userIdUpdate ,
dateUpdate 
    
    
   ) VALUES
  (
 
   v_esiId ,
v_esiName ,
 v_maxGrossLimit ,
v_employeePer ,
 v_employerPer ,
 v_effectiveStartDate ,
v_effectiveEndDate ,
 v_activeStatus ,
 v_effectiveDate,
v_companyId,
v_groupId ,
v_allowModi,
v_userId ,
v_dateCreated ,
v_userIdUpdate ,
v_dateUpdate 
      
  );
End
$$
DELIMITER ;

-- --------------------------------------------------------

--
-- Table structure for table `EsiCycle`
--

CREATE TABLE `EsiCycle` (
  `esiCycleId` int(11) NOT NULL,
  `esiId` int(11) NOT NULL,
  `fromperiod` varchar(12) DEFAULT NULL,
  `toperiod` varchar(12) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `EsiCycle`
--

INSERT INTO `EsiCycle` (`esiCycleId`, `esiId`, `fromperiod`, `toperiod`) VALUES
(1, 71, 'APR', 'SEP'),
(2, 71, 'OCT', 'MAR'),
(3, 72, 'APR', 'SEP'),
(4, 72, 'OCT', 'MAR');

-- --------------------------------------------------------

--
-- Table structure for table `EsiHistory`
--

CREATE TABLE `EsiHistory` (
  `esiId` int(11) NOT NULL,
  `esiName` varchar(50) DEFAULT NULL,
  `maxGrossLimit` decimal(12,2) DEFAULT '0.00',
  `employeePer` decimal(5,2) DEFAULT '0.00',
  `employerPer` decimal(5,2) DEFAULT '0.00',
  `effectiveStartDate` datetime DEFAULT NULL,
  `effectiveEndDate` datetime DEFAULT NULL,
  `activeStatus` varchar(2) DEFAULT 'A',
  `effectiveDate` date NOT NULL,
  `companyId` int(11) NOT NULL,
  `groupId` int(11) DEFAULT NULL,
  `allowModi` char(1) DEFAULT 'N',
  `userId` int(11) NOT NULL,
  `dateCreated` datetime DEFAULT NULL,
  `userIdUpdate` int(11) DEFAULT NULL,
  `dateUpdate` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `EsiHistory`
--

INSERT INTO `EsiHistory` (`esiId`, `esiName`, `maxGrossLimit`, `employeePer`, `employerPer`, `effectiveStartDate`, `effectiveEndDate`, `activeStatus`, `effectiveDate`, `companyId`, `groupId`, `allowModi`, `userId`, `dateCreated`, `userIdUpdate`, `dateUpdate`) VALUES
(71, NULL, '21000.00', '1.75', '4.75', NULL, NULL, 'AC', '2019-07-02', 1, NULL, NULL, 467, '2019-07-02 18:34:50', 467, '2019-07-02 18:34:50'),
(71, NULL, '21000.00', '1.75', '4.75', NULL, NULL, 'AC', '2019-07-02', 1, NULL, NULL, 467, '2019-07-02 18:34:50', 467, '2019-07-02 18:34:50'),
(71, NULL, '21000.00', '1.75', '4.75', NULL, NULL, 'AC', '2019-07-02', 1, NULL, NULL, 467, '2019-07-02 18:34:50', 1, '2019-07-02 18:34:50');

-- --------------------------------------------------------

--
-- Table structure for table `events`
--

CREATE TABLE `events` (
  `event_id` int(11) NOT NULL,
  `event_title` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `finalSettlement`
--

CREATE TABLE `finalSettlement` (
  `finalSettlementId` int(5) NOT NULL,
  `employeeId` int(5) DEFAULT NULL,
  `companyId` int(5) DEFAULT NULL,
  `salaryPayable` decimal(12,2) DEFAULT NULL,
  `loan` decimal(12,2) DEFAULT NULL,
  `leaveEncashment` decimal(12,2) DEFAULT NULL,
  `gratuity` decimal(12,2) DEFAULT NULL,
  `incomeTax` decimal(12,2) DEFAULT NULL,
  `netPayable` decimal(12,2) NOT NULL,
  `userId` int(5) DEFAULT NULL,
  `dateCreated` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `FinalSettlementReport`
--

CREATE TABLE `FinalSettlementReport` (
  `finalReportId` int(11) NOT NULL,
  `employeeId` int(11) DEFAULT NULL,
  `lastPaidMonth` varchar(25) DEFAULT NULL,
  `lastPaidSalary` decimal(12,2) DEFAULT NULL,
  `salaryPayableMonth` varchar(25) DEFAULT NULL,
  `salaryPayable` decimal(12,2) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `FinancialMonth`
--

CREATE TABLE `FinancialMonth` (
  `financialMonthId` int(11) NOT NULL,
  `month` varchar(25) NOT NULL,
  `companyId` int(11) NOT NULL,
  `userId` int(11) NOT NULL,
  `dateCreated` datetime NOT NULL,
  `userIdUpdate` int(11) NOT NULL,
  `updatedDate` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `FinancialMonth`
--

INSERT INTO `FinancialMonth` (`financialMonthId`, `month`, `companyId`, `userId`, `dateCreated`, `userIdUpdate`, `updatedDate`) VALUES
(1, '3', 1, 467, '2019-03-28 00:00:00', 467, '2019-03-27 00:00:00');

-- --------------------------------------------------------

--
-- Table structure for table `FinancialYear`
--

CREATE TABLE `FinancialYear` (
  `financialYearId` int(11) NOT NULL,
  `financialYear` varchar(10) DEFAULT NULL,
  `dateFrom` date DEFAULT NULL,
  `dateTo` date DEFAULT NULL,
  `isPayrollDaysManuals` char(1) DEFAULT 'N',
  `companyId` int(11) NOT NULL,
  `groupId` int(11) DEFAULT NULL,
  `allowModi` char(1) DEFAULT 'N',
  `userId` int(11) NOT NULL,
  `dateCreated` datetime DEFAULT NULL,
  `userIdUpdate` int(11) DEFAULT NULL,
  `dateUpdate` datetime DEFAULT NULL,
  `effectiveStartDate` datetime DEFAULT NULL,
  `effectiveEndDate` datetime DEFAULT NULL,
  `activeStatus` varchar(2) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `FinancialYear`
--

INSERT INTO `FinancialYear` (`financialYearId`, `financialYear`, `dateFrom`, `dateTo`, `isPayrollDaysManuals`, `companyId`, `groupId`, `allowModi`, `userId`, `dateCreated`, `userIdUpdate`, `dateUpdate`, `effectiveStartDate`, `effectiveEndDate`, `activeStatus`) VALUES
(1, '2019-2020', '2019-04-01', '2020-03-31', 'N', 1, NULL, NULL, 1, '2019-07-25 11:41:07', NULL, NULL, NULL, NULL, 'AC');

-- --------------------------------------------------------

--
-- Table structure for table `Grades`
--

CREATE TABLE `Grades` (
  `gradesId` int(11) NOT NULL,
  `gradesName` char(40) DEFAULT NULL,
  `salaryFrom` decimal(12,2) DEFAULT '0.00',
  `salaryTo` decimal(12,2) DEFAULT '0.00',
  `incrementPer` decimal(12,2) DEFAULT '0.00',
  `effectiveStartDate` datetime DEFAULT NULL,
  `effectiveEndDate` datetime DEFAULT NULL,
  `activeStatus` varchar(2) DEFAULT NULL,
  `companyId` int(11) DEFAULT NULL,
  `groupId` int(11) DEFAULT NULL,
  `allowModi` char(1) DEFAULT 'N',
  `userId` int(11) NOT NULL,
  `dateCreated` datetime DEFAULT NULL,
  `userIdUpdate` int(11) DEFAULT NULL,
  `dateUpdate` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `Grades`
--

INSERT INTO `Grades` (`gradesId`, `gradesName`, `salaryFrom`, `salaryTo`, `incrementPer`, `effectiveStartDate`, `effectiveEndDate`, `activeStatus`, `companyId`, `groupId`, `allowModi`, `userId`, `dateCreated`, `userIdUpdate`, `dateUpdate`) VALUES
(1, 'G1', '1000.00', '9999999999.00', '30.00', NULL, NULL, 'AC', 1, NULL, NULL, 1, '2019-07-25 11:11:40', 1, '2019-07-25 11:11:40');

--
-- Triggers `Grades`
--
DELIMITER $$
CREATE TRIGGER `grades_history_trigger` BEFORE UPDATE ON `Grades` FOR EACH ROW BEGIN

 declare v_gradesId int(11);
 declare v_gradesName char(40) ;
 declare v_salaryFrom decimal(12,2);
declare  v_salaryTo decimal(12,2);
 declare v_incrementPer decimal(12,2) ;
declare  v_effectiveStartDate datetime;
declare  v_effectiveEndDate datetime;
 declare v_activeStatus varchar(2) ;
 declare v_companyId int(11) ;
 declare v_groupId int(11) ;
 declare v_allowModi char(1) ;
 declare v_userId int(11) ;
 declare v_dateCreated datetime ;
 declare v_userIdUpdate int(11);
 declare v_dateUpdate datetime ;


 set v_gradesId =OLD.gradesId;
 set v_gradesName =OLD.gradesName;
 set v_salaryFrom =OLD.salaryFrom;
set  v_salaryTo =OLD.salaryTo;
 set v_incrementPer =OLD.incrementPer ;
set  v_effectiveStartDate =OLD.effectiveStartDate;
set  v_effectiveEndDate =OLD.effectiveEndDate;
 set v_activeStatus =OLD.activeStatus ;
 set v_companyId =OLD.companyId ;
 set v_groupId =OLD.groupId ; 
 set v_allowModi =OLD.allowModi ;
 set v_userId =OLD.userId ;
 set v_dateCreated =OLD.dateCreated;
 set v_userIdUpdate =NEW.userIdUpdate;
 set v_dateUpdate =OLD.dateUpdate ;


 
  
 
Insert INTO GradesHistory(

    gradesId ,
 gradesName,
 salaryFrom ,
salaryTo,
incrementPer ,
effectiveStartDate ,
effectiveEndDate ,
activeStatus,
companyId ,
groupId ,
allowModi,
userId ,
dateCreated ,
userIdUpdate ,
dateUpdate
    
   ) VALUES
  (
 
   v_gradesId ,
 v_gradesName,
 v_salaryFrom ,
v_salaryTo,
v_incrementPer ,
v_effectiveStartDate ,
v_effectiveEndDate ,
v_activeStatus,
v_companyId ,
v_groupId ,
v_allowModi,
v_userId ,
v_dateCreated ,
v_userIdUpdate ,
v_dateUpdate
      
  );
End
$$
DELIMITER ;

-- --------------------------------------------------------

--
-- Table structure for table `GradesHistory`
--

CREATE TABLE `GradesHistory` (
  `gradesId` int(11) NOT NULL,
  `gradesName` char(40) DEFAULT NULL,
  `salaryFrom` decimal(12,2) DEFAULT '0.00',
  `salaryTo` decimal(12,2) DEFAULT '0.00',
  `incrementPer` decimal(12,2) DEFAULT '0.00',
  `effectiveStartDate` datetime DEFAULT NULL,
  `effectiveEndDate` datetime DEFAULT NULL,
  `activeStatus` varchar(2) DEFAULT NULL,
  `companyId` int(11) NOT NULL,
  `groupId` int(11) DEFAULT NULL,
  `allowModi` char(1) DEFAULT 'N',
  `userId` int(11) NOT NULL,
  `dateCreated` datetime DEFAULT NULL,
  `userIdUpdate` int(11) DEFAULT NULL,
  `dateUpdate` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `GradesPayDefinition`
--

CREATE TABLE `GradesPayDefinition` (
  `gradesPayId` int(11) NOT NULL,
  `gradesId` int(11) NOT NULL,
  `payHeadId` int(11) NOT NULL,
  `percenatage` decimal(5,2) DEFAULT '0.00',
  `allowModi` char(1) DEFAULT 'N',
  `userId` int(11) NOT NULL,
  `dateCreated` datetime DEFAULT NULL,
  `userIdUpdate` int(11) DEFAULT NULL,
  `dateUpdate` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `Gratuaty`
--

CREATE TABLE `Gratuaty` (
  `graduityId` int(11) NOT NULL,
  `noOfMonths` int(11) NOT NULL,
  `noOfDays` int(11) NOT NULL,
  `noOfDaysDevide` int(11) NOT NULL,
  `effectiveStartDate` datetime DEFAULT NULL,
  `effectiveEndDate` datetime DEFAULT NULL,
  `activeStatus` varchar(2) DEFAULT 'A',
  `effectiveDate` date NOT NULL,
  `companyId` int(11) NOT NULL,
  `groupId` int(11) DEFAULT NULL,
  `allowModi` char(1) DEFAULT 'N',
  `userId` int(11) NOT NULL,
  `dateCreated` datetime DEFAULT NULL,
  `userIdUpdate` int(11) DEFAULT NULL,
  `dateUpdate` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `Gratuaty`
--

INSERT INTO `Gratuaty` (`graduityId`, `noOfMonths`, `noOfDays`, `noOfDaysDevide`, `effectiveStartDate`, `effectiveEndDate`, `activeStatus`, `effectiveDate`, `companyId`, `groupId`, `allowModi`, `userId`, `dateCreated`, `userIdUpdate`, `dateUpdate`) VALUES
(15, 52, 15, 26, NULL, NULL, 'DE', '2019-05-15', 1, NULL, NULL, 1, NULL, 467, '2019-07-02 18:49:45'),
(16, 56, 15, 26, NULL, NULL, 'AC', '2019-05-15', 1, NULL, NULL, 1, NULL, 1, '2019-07-25 11:33:21');

--
-- Triggers `Gratuaty`
--
DELIMITER $$
CREATE TRIGGER `gratuaty_history_trigger` BEFORE UPDATE ON `Gratuaty` FOR EACH ROW BEGIN

 DECLARE v_graduityId int(11) ;
 declare v_noOfMonths int(11);
 declare v_noOfDays int(11) ;
 declare v_noOfDaysDevide int(11) ;
 declare v_effectiveStartDate datetime ;
 declare v_effectiveEndDate datetime ;
 declare v_activeStatus varchar(2) ;
 declare v_effectiveDate date ;
 declare v_companyId int(11) ;
 declare v_groupId int(11) ;
 declare v_allowModi char(1) ;
 declare v_userId int(11) ;
 declare v_dateCreated datetime ;
 declare v_userIdUpdate int(11) ;
 declare v_dateUpdate datetime ;
 
 
  set v_graduityId =OLD.graduityId ;
 set v_noOfMonths =OLD.noOfMonths;
 set v_noOfDays =OLD.noOfDays ;
 set v_noOfDaysDevide =OLD.noOfDaysDevide ;
 set v_effectiveStartDate =OLD.effectiveStartDate ;
 set v_effectiveEndDate =OLD.effectiveEndDate ;
 set v_activeStatus =OLD.activeStatus ;
 set v_effectiveDate =OLD.effectiveDate ;
 set v_companyId =OLD.companyId ;
 set v_groupId =OLD.groupId ;
 set v_allowModi =OLD.allowModi ;
 set v_userId =OLD.userId ;
 set v_dateCreated =OLD.dateCreated ;
 set v_userIdUpdate =NEW.userIdUpdate ;
 set v_dateUpdate =OLD.dateUpdate ;
 
 
  Insert INTO GratuatyHistory(

 graduityId,
 noOfMonths,
 noOfDays,
 noOfDaysDevide,
 effectiveStartDate,
 effectiveEndDate,
 activeStatus,
 effectiveDate,
 companyId,
 groupId,
 allowModi,
 userId,
 dateCreated,
 userIdUpdate,
 dateUpdate
      
   ) VALUES
  (
      
  v_graduityId,
 v_noOfMonths,
 v_noOfDays,
 v_noOfDaysDevide,
 v_effectiveStartDate,
 v_effectiveEndDate,
 v_activeStatus,
 v_effectiveDate,
 v_companyId,
 v_groupId,
 v_allowModi,
 v_userId,
 v_dateCreated,
 v_userIdUpdate,
 v_dateUpdate
      
  );
End
$$
DELIMITER ;

-- --------------------------------------------------------

--
-- Table structure for table `GratuatyHistory`
--

CREATE TABLE `GratuatyHistory` (
  `graduityId` int(11) NOT NULL,
  `noOfMonths` int(11) NOT NULL,
  `noOfDays` int(11) NOT NULL,
  `noOfDaysDevide` int(11) NOT NULL,
  `effectiveStartDate` datetime DEFAULT NULL,
  `effectiveEndDate` datetime DEFAULT NULL,
  `activeStatus` varchar(2) DEFAULT 'A',
  `effectiveDate` date NOT NULL,
  `companyId` int(11) NOT NULL,
  `groupId` int(11) DEFAULT NULL,
  `allowModi` char(1) DEFAULT 'N',
  `userId` int(11) NOT NULL,
  `dateCreated` datetime DEFAULT NULL,
  `userIdUpdate` int(11) DEFAULT NULL,
  `dateUpdate` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `GratuatyHistory`
--

INSERT INTO `GratuatyHistory` (`graduityId`, `noOfMonths`, `noOfDays`, `noOfDaysDevide`, `effectiveStartDate`, `effectiveEndDate`, `activeStatus`, `effectiveDate`, `companyId`, `groupId`, `allowModi`, `userId`, `dateCreated`, `userIdUpdate`, `dateUpdate`) VALUES
(15, 52, 15, 26, NULL, NULL, 'AC', '2019-05-15', 1, NULL, NULL, 1, NULL, 467, '2019-07-02 18:49:45'),
(15, 52, 15, 26, NULL, NULL, 'AC', '2019-05-15', 1, NULL, NULL, 1, NULL, 467, '2019-07-02 18:49:45'),
(15, 52, 15, 26, NULL, NULL, 'AC', '2019-05-15', 1, NULL, NULL, 1, NULL, 467, '2019-07-02 18:49:45'),
(15, 52, 15, 26, NULL, NULL, 'AC', '2019-05-15', 1, NULL, NULL, 1, NULL, 467, '2019-07-02 18:49:45');

-- --------------------------------------------------------

--
-- Table structure for table `Groupg`
--

CREATE TABLE `Groupg` (
  `groupId` int(11) NOT NULL,
  `groupAbbrebiation` varchar(20) DEFAULT NULL,
  `groupName` varchar(100) NOT NULL,
  `groupAddressId` int(11) NOT NULL,
  `dateOfBirth` date DEFAULT NULL,
  `groupLogo` blob,
  `groupLogoPath` varchar(50) DEFAULT NULL,
  `allowModi` char(1) DEFAULT 'N',
  `userId` int(11) NOT NULL,
  `dateCreated` datetime DEFAULT NULL,
  `userIdUpdate` int(11) DEFAULT NULL,
  `dateUpdate` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `hibernate_sequences`
--

CREATE TABLE `hibernate_sequences` (
  `sequence_name` varchar(255) DEFAULT NULL,
  `sequence_next_hi_value` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `HistoryAttendance`
--

CREATE TABLE `HistoryAttendance` (
  `srno` int(11) DEFAULT NULL,
  `employeeCode` varchar(10) NOT NULL,
  `employeeName` varchar(50) DEFAULT NULL,
  `presense` decimal(5,2) DEFAULT '0.00',
  `weekoff` decimal(5,2) DEFAULT '0.00',
  `publicholidays` decimal(5,2) DEFAULT '0.00',
  `paidleave` decimal(5,2) DEFAULT '0.00',
  `casualleave` decimal(6,2) DEFAULT '0.00',
  `seekleave` decimal(5,2) DEFAULT '0.00',
  `absense` decimal(5,2) DEFAULT '0.00',
  `payDays` decimal(5,2) DEFAULT '0.00',
  `processMonth` varchar(10) NOT NULL,
  `employeeId` int(11) NOT NULL,
  `companyId` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `HoldSalary`
--

CREATE TABLE `HoldSalary` (
  `holdSalaryId` int(10) NOT NULL,
  `payrollMonth` varchar(10) DEFAULT NULL,
  `employeeId` int(10) DEFAULT NULL,
  `remark` varchar(100) NOT NULL,
  `status` varchar(5) NOT NULL,
  `companyId` int(5) NOT NULL,
  `userId` int(10) NOT NULL,
  `userIdUpdate` int(10) DEFAULT NULL,
  `dateCreated` date DEFAULT NULL,
  `dateUpdate` date DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `InterestingThoughts`
--

CREATE TABLE `InterestingThoughts` (
  `interestingThoughtsId` int(11) NOT NULL,
  `employeeId` int(11) DEFAULT NULL,
  `thoughts` varchar(300) DEFAULT NULL,
  `companyId` int(11) NOT NULL,
  `userId` int(11) DEFAULT NULL,
  `dateCreated` date DEFAULT NULL,
  `userIdUpdate` int(11) DEFAULT NULL,
  `dateUpdate` date DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `Item`
--

CREATE TABLE `Item` (
  `itemId` int(11) NOT NULL,
  `itemUnit` varchar(50) DEFAULT NULL,
  `itemName` varchar(50) DEFAULT NULL,
  `hsnCode` varchar(50) DEFAULT NULL,
  `gstRate` decimal(5,2) DEFAULT '0.00',
  `activeStatus` varchar(2) DEFAULT 'A',
  `companyId` int(11) NOT NULL,
  `groupId` int(11) DEFAULT NULL,
  `effectiveStartDate` datetime DEFAULT NULL,
  `effectiveEndDate` datetime DEFAULT NULL,
  `allowModi` char(1) DEFAULT 'N',
  `userId` int(11) NOT NULL,
  `dateCreated` datetime DEFAULT NULL,
  `userIdUpdate` int(11) DEFAULT NULL,
  `dateUpdate` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `Item`
--

INSERT INTO `Item` (`itemId`, `itemUnit`, `itemName`, `hsnCode`, `gstRate`, `activeStatus`, `companyId`, `groupId`, `effectiveStartDate`, `effectiveEndDate`, `allowModi`, `userId`, `dateCreated`, `userIdUpdate`, `dateUpdate`) VALUES
(1, '1', 'Laptop', NULL, NULL, 'AC', 1, NULL, NULL, NULL, NULL, 1, '2019-07-25 11:42:29', NULL, '2019-07-25 11:42:29'),
(2, '1', 'Mobile', NULL, NULL, 'AC', 1, NULL, NULL, NULL, NULL, 1, '2019-07-25 11:42:37', NULL, '2019-07-25 11:42:37'),
(3, '1', 'Dongle', NULL, NULL, 'AC', 1, NULL, NULL, NULL, NULL, 1, '2019-07-25 11:42:45', NULL, '2019-07-25 11:42:45'),
(4, '1', 'SIM Card', NULL, NULL, 'AC', 1, NULL, NULL, NULL, NULL, 1, '2019-07-25 11:42:53', NULL, '2019-07-25 11:42:53'),
(5, '1', 'Employee ID Card', NULL, NULL, 'AC', 1, NULL, NULL, NULL, NULL, 1, NULL, NULL, '2019-07-25 11:44:55');

-- --------------------------------------------------------

--
-- Table structure for table `ItemHistory`
--

CREATE TABLE `ItemHistory` (
  `itemId` int(11) NOT NULL,
  `itemName` varchar(50) DEFAULT NULL,
  `itemUnit` varchar(5) NOT NULL,
  `hsnCode` varchar(12) DEFAULT NULL,
  `gstRate` decimal(5,2) DEFAULT '0.00',
  `activeStatus` varchar(2) DEFAULT 'A',
  `companyId` int(11) NOT NULL,
  `groupId` int(11) DEFAULT NULL,
  `effectiveStartDate` datetime DEFAULT NULL,
  `effectiveEndDate` datetime DEFAULT NULL,
  `allowModi` char(1) DEFAULT 'N',
  `userId` int(11) NOT NULL,
  `dateCreated` datetime DEFAULT NULL,
  `userIdUpdate` int(11) DEFAULT NULL,
  `dateUpdate` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `Kra`
--

CREATE TABLE `Kra` (
  `kraId` int(11) NOT NULL,
  `kraName` varchar(50) DEFAULT NULL,
  `designationId` int(11) NOT NULL,
  `departmentId` int(11) NOT NULL,
  `companyId` int(11) NOT NULL,
  `groupId` int(11) DEFAULT NULL,
  `effectiveStartDate` datetime DEFAULT NULL,
  `effectiveEndDate` datetime DEFAULT NULL,
  `activeStatus` varchar(2) DEFAULT NULL,
  `allowModi` char(1) DEFAULT 'N',
  `userId` int(11) NOT NULL,
  `dateCreated` datetime DEFAULT NULL,
  `userIdUpdate` int(11) DEFAULT NULL,
  `dateUpdate` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `LabourWelfareFund`
--

CREATE TABLE `LabourWelfareFund` (
  `labourWelfareFundHeadId` int(11) NOT NULL,
  `stateId` int(11) DEFAULT NULL,
  `perMonthAmount` decimal(12,2) DEFAULT '0.00',
  `limitAmount` decimal(12,2) DEFAULT '0.00',
  `activeStatus` varchar(2) DEFAULT 'A',
  `effectiveStartDate` date DEFAULT NULL,
  `effectiveEndDate` date DEFAULT NULL,
  `companyId` int(11) NOT NULL,
  `groupId` int(11) DEFAULT NULL,
  `allowModi` char(1) DEFAULT 'N',
  `userId` int(11) NOT NULL,
  `dateCreated` datetime DEFAULT NULL,
  `userIdUpdate` int(11) DEFAULT NULL,
  `dateUpdate` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `LabourWelfareFundInfo`
--

CREATE TABLE `LabourWelfareFundInfo` (
  `labourWelfareFundInfoId` int(11) NOT NULL,
  `labourWelfareFundId` int(11) NOT NULL,
  `gradeId` int(11) DEFAULT NULL,
  `limitFrom` decimal(12,2) NOT NULL,
  `limitTo` decimal(12,2) NOT NULL,
  `welFareAmount` decimal(12,2) NOT NULL,
  `allowModi` char(1) DEFAULT 'N',
  `userId` int(11) NOT NULL,
  `dateCreated` date DEFAULT NULL,
  `userIdUpdate` int(11) DEFAULT NULL,
  `dateUpdate` date DEFAULT NULL,
  `activeStatus` varchar(2) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `Language`
--

CREATE TABLE `Language` (
  `languageId` int(11) NOT NULL,
  `name` varchar(50) NOT NULL,
  `code` varchar(2) NOT NULL,
  `companyId` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `Language`
--

INSERT INTO `Language` (`languageId`, `name`, `code`, `companyId`) VALUES
(5, 'Hindi', 'HI', 1),
(6, 'English', 'EN', 1);

-- --------------------------------------------------------

--
-- Table structure for table `LeaveSchemeMaster`
--

CREATE TABLE `LeaveSchemeMaster` (
  `leaveSchemeId` int(11) NOT NULL,
  `leavePeriodId` int(11) NOT NULL,
  `leaveSchemeName` varchar(250) NOT NULL,
  `status` varchar(11) NOT NULL,
  `userId` int(11) NOT NULL,
  `dateCreated` datetime NOT NULL,
  `userIdUpdate` int(11) DEFAULT NULL,
  `dateUpdates` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `LeaveSchemeMaster`
--

INSERT INTO `LeaveSchemeMaster` (`leaveSchemeId`, `leavePeriodId`, `leaveSchemeName`, `status`, `userId`, `dateCreated`, `userIdUpdate`, `dateUpdates`) VALUES
(1, 0, 'Leave Scheme For All', '', 145, '2019-08-26 00:00:00', NULL, NULL);

-- --------------------------------------------------------

--
-- Table structure for table `LoanEMI`
--

CREATE TABLE `LoanEMI` (
  `emiNo` int(11) NOT NULL,
  `transactionNo` int(11) NOT NULL,
  `emiDate` date NOT NULL,
  `emiAmount` decimal(12,2) DEFAULT NULL,
  `emiStatus` varchar(2) DEFAULT NULL,
  `userId` int(11) NOT NULL,
  `dateCreated` date NOT NULL,
  `remarks` varchar(100) DEFAULT NULL,
  `transactionFlag` varchar(5) DEFAULT NULL,
  `processMonth` varchar(250) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `LoanIssue`
--

CREATE TABLE `LoanIssue` (
  `transactionNo` int(11) NOT NULL,
  `transactionDate` date DEFAULT NULL,
  `employeeId` int(11) NOT NULL,
  `loanType` varchar(2) DEFAULT NULL,
  `interestType` varchar(2) DEFAULT NULL,
  `loanAmount` decimal(12,2) DEFAULT '0.00',
  `issueDate` date NOT NULL,
  `noOfEmi` int(11) DEFAULT '0',
  `emiStartDate` date DEFAULT NULL,
  `rateOfInterest` decimal(5,2) DEFAULT '0.00',
  `emiAmount` decimal(12,2) DEFAULT '0.00',
  `naration` varchar(50) DEFAULT NULL,
  `activeStatus` varchar(2) DEFAULT 'A',
  `effectiveStartDate` datetime DEFAULT NULL,
  `effectiveEndDate` datetime DEFAULT NULL,
  `allowModi` char(1) DEFAULT 'N',
  `userId` int(11) NOT NULL,
  `companyId` int(11) DEFAULT NULL,
  `dateCreated` datetime DEFAULT NULL,
  `userIdUpdate` int(11) DEFAULT NULL,
  `dateUpdate` datetime DEFAULT NULL,
  `settlementAmount` decimal(12,2) DEFAULT NULL,
  `paymentMode` varchar(2) DEFAULT NULL,
  `instrumentNo` varchar(50) DEFAULT NULL,
  `remark` varchar(250) DEFAULT NULL,
  `isSettlementCompleted` varchar(2) DEFAULT NULL,
  `loanPendingAmount` decimal(12,2) DEFAULT '0.00'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `Mail`
--

CREATE TABLE `Mail` (
  `mailId` int(11) NOT NULL,
  `title` varchar(200) DEFAULT NULL,
  `subject` varchar(1000) DEFAULT NULL,
  `toMail` varchar(100) DEFAULT NULL,
  `fromMail` varchar(100) DEFAULT NULL,
  `cc` varchar(100) DEFAULT NULL,
  `bcc` varchar(100) DEFAULT NULL,
  `mailBody` varchar(300) DEFAULT NULL,
  `headerBody` varchar(150) DEFAULT NULL,
  `footerBody` varchar(150) DEFAULT NULL,
  `functionName` varchar(50) NOT NULL,
  `dateCreated` datetime DEFAULT NULL,
  `userId` int(11) DEFAULT NULL,
  `userIdUpdate` int(11) DEFAULT NULL,
  `dateUpdate` datetime DEFAULT NULL,
  `companyId` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `Mail`
--

INSERT INTO `Mail` (`mailId`, `title`, `subject`, `toMail`, `fromMail`, `cc`, `bcc`, `mailBody`, `headerBody`, `footerBody`, `functionName`, `dateCreated`, `userId`, `userIdUpdate`, `dateUpdate`, `companyId`) VALUES
(1, 'Happy Birth Day', 'Happy Birth Day', '', 'greetings@computronics.in', 'greetings@computronics.in', NULL, NULL, NULL, NULL, 'birthDay', '2018-06-21 00:00:00', 1, NULL, NULL, 1),
(2, 'Joining Anniversary', 'Joining Anniversary', NULL, 'greetings@computronics.in', 'greetings@computronics.in', NULL, NULL, NULL, NULL, 'joiningAnniversery', NULL, 1, NULL, NULL, 1),
(3, 'salary slip', 'salary slip', 'greetings@computronics.in', 'greetings@computronics.in', 'greetings@computronics.in', NULL, NULL, NULL, NULL, 'salarySlip', '2018-06-14 00:00:00', 1, NULL, NULL, 1),
(4, 'Notification', 'Notification', 'greetings@computronics.in', 'greetings@computronics.in', 'greetings@computronics.in', NULL, NULL, NULL, NULL, 'Notification Mail', '2018-10-09 00:00:00', 1, 1, NULL, NULL),
(5, 'Invitation', 'Invitation Mail', 'greetings@computronics.in', 'greetings@computronics.in', 'greetings@computronics.in', NULL, 'Notification Mail', NULL, NULL, '', '2018-11-16 00:00:00', 1, NULL, NULL, 1),
(6, 'Attendance', 'Daily Attendance ', 'neelesh@computronics.in', 'greetings@computronics.in', 'greetings@computronics.in', NULL, NULL, NULL, NULL, 'Attendance', '2019-08-27 00:00:00', 1, NULL, NULL, 1);

-- --------------------------------------------------------

--
-- Table structure for table `MandatoryInfo`
--

CREATE TABLE `MandatoryInfo` (
  `mandatoryInfoId` int(11) NOT NULL,
  `docName` varchar(100) NOT NULL,
  `docCode` varchar(2) NOT NULL,
  `status` varchar(2) NOT NULL,
  `companyId` int(11) NOT NULL,
  `effectiveStartDate` datetime DEFAULT NULL,
  `effectiveEndDate` datetime DEFAULT NULL,
  `activeStatus` varchar(2) DEFAULT NULL,
  `userId` int(11) NOT NULL,
  `dateCreated` datetime DEFAULT NULL,
  `userIdUpdate` int(11) DEFAULT NULL,
  `dateUpdate` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `MandatoryInfo`
--

INSERT INTO `MandatoryInfo` (`mandatoryInfoId`, `docName`, `docCode`, `status`, `companyId`, `effectiveStartDate`, `effectiveEndDate`, `activeStatus`, `userId`, `dateCreated`, `userIdUpdate`, `dateUpdate`) VALUES
(759, 'UID', 'UI', 'AC', 1, '2019-07-25 11:31:16', NULL, 'AC', 0, '2019-02-25 16:51:59', 1, '2019-07-25 11:31:16'),
(760, 'UAN', 'UA', 'AN', 1, '2019-07-25 11:31:16', NULL, 'AC', 0, '2019-02-25 16:51:59', 1, '2019-07-25 11:31:16'),
(761, 'ESI', 'ES', 'AN', 1, '2019-07-25 11:31:16', NULL, 'AC', 0, '2019-02-25 16:51:59', 1, '2019-07-25 11:31:16'),
(762, 'Bank Account Number', 'BA', 'AC', 1, '2019-07-25 11:31:16', NULL, 'AC', 0, '2019-02-25 16:51:59', 1, '2019-07-25 11:31:16'),
(763, 'Accidental Insurance', 'AI', 'AN', 1, '2019-07-25 11:31:16', NULL, 'AC', 0, '2019-02-25 16:51:59', 1, '2019-07-25 11:31:16'),
(764, 'Medical Insurance', 'MI', 'AN', 1, '2019-07-25 11:31:16', NULL, 'AC', 0, '2019-02-25 16:51:59', 1, '2019-07-25 11:31:16'),
(765, 'PAN', 'PN', 'AN', 1, '2019-07-25 11:31:16', NULL, 'AC', 0, '2019-02-25 16:51:59', 1, '2019-07-25 11:31:16'),
(766, 'EPF', 'PF', 'AN', 1, '2019-07-25 11:31:16', NULL, 'AC', 0, '2019-02-25 16:51:59', 1, '2019-07-25 11:31:16'),
(767, 'Nominee of UAN', 'NU', 'AN', 1, '2019-07-25 11:31:16', NULL, 'AC', 0, '2019-02-25 16:51:59', 1, '2019-07-25 11:31:16'),
(768, 'Nominee of Accidental Insurance', 'NA', 'AN', 1, '2019-07-25 11:31:16', NULL, 'AC', 0, '2019-02-25 16:51:59', 1, '2019-07-25 11:31:16'),
(769, 'Nominee of Medical Insurance', 'UM', 'AN', 1, '2019-07-25 11:31:16', NULL, 'AC', 0, '2019-02-25 16:51:59', 1, '2019-07-25 11:31:16');

--
-- Triggers `MandatoryInfo`
--
DELIMITER $$
CREATE TRIGGER `mandatoryInfo_history_trigger` BEFORE UPDATE ON `MandatoryInfo` FOR EACH ROW BEGIN

 declare v_mandatoryInfoId int(11) ;
 declare v_docName varchar(100) ;
 declare v_docCode varchar(2) ;
 declare v_status varchar(2) ;
 declare v_companyId int(11) ;
 declare v_effectiveStartDate datetime ;
 declare v_effectiveEndDate datetime ;
 declare v_activeStatus varchar(2) ;
 declare v_userId int(11) ;
 declare v_dateCreated datetime ;
 declare v_userIdUpdate int(11) ;
 declare v_dateUpdate datetime ;



 set v_mandatoryInfoId =OLD.mandatoryInfoId ;
 set v_docName =OLD.docName ;
 set v_docCode =OLD.docCode ;
 set v_status =OLD.status ;
 set v_companyId =OLD.companyId ;
 set v_effectiveStartDate =OLD.effectiveStartDate ;
 set v_effectiveEndDate =OLD.effectiveEndDate ;
 set v_activeStatus =OLD.activeStatus ;
 set v_userId =OLD.userId ;
 set v_dateCreated =OLD.dateCreated ;
 set v_userIdUpdate =NEW.userIdUpdate ;
 set v_dateUpdate =OLD.dateUpdate ;

  


Insert INTO MandatoryInfoHistory(

     mandatoryInfoId ,
 docName ,
 docCode ,
status ,
companyId ,
effectiveStartDate ,
 effectiveEndDate ,
 activeStatus ,
 userId ,
 dateCreated ,
 userIdUpdate ,
 dateUpdate 
    
    
   ) VALUES
  (
      
 v_mandatoryInfoId ,
 v_docName ,
 v_docCode ,
v_status ,
v_companyId ,
v_effectiveStartDate ,
 v_effectiveEndDate ,
 v_activeStatus ,
 v_userId ,
 v_dateCreated ,
 v_userIdUpdate ,
 v_dateUpdate 
      
  );
End
$$
DELIMITER ;

-- --------------------------------------------------------

--
-- Table structure for table `MandatoryInfoCheck`
--

CREATE TABLE `MandatoryInfoCheck` (
  `mandatoryInfoCheckId` int(11) NOT NULL,
  `employeeId` int(11) NOT NULL,
  `UI` varchar(5) DEFAULT NULL,
  `UA` varchar(5) DEFAULT NULL,
  `ES` varchar(5) DEFAULT NULL,
  `BA` varchar(5) DEFAULT NULL,
  `AI` varchar(5) DEFAULT NULL,
  `MI` varchar(5) DEFAULT NULL,
  `PA` varchar(5) DEFAULT NULL,
  `PF` varchar(5) DEFAULT NULL,
  `NA` varchar(5) DEFAULT NULL,
  `NM` varchar(5) DEFAULT NULL,
  `NU` varchar(5) DEFAULT NULL,
  `flag3` varchar(5) DEFAULT NULL,
  `flag4` varchar(5) DEFAULT NULL,
  `userId` int(11) NOT NULL,
  `dateCreated` datetime DEFAULT NULL,
  `userIdUpdate` int(11) DEFAULT NULL,
  `dateUpdate` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `MandatoryInfoHistory`
--

CREATE TABLE `MandatoryInfoHistory` (
  `mandatoryInfoId` int(11) NOT NULL,
  `docName` varchar(100) NOT NULL,
  `docCode` varchar(2) NOT NULL,
  `status` varchar(2) DEFAULT NULL,
  `companyId` int(11) NOT NULL,
  `effectiveStartDate` datetime DEFAULT NULL,
  `effectiveEndDate` datetime DEFAULT NULL,
  `activeStatus` varchar(2) DEFAULT NULL,
  `userId` int(11) NOT NULL,
  `dateCreated` datetime DEFAULT NULL,
  `userIdUpdate` int(11) DEFAULT NULL,
  `dateUpdate` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `MassCommunication`
--

CREATE TABLE `MassCommunication` (
  `massCommunicationId` int(11) NOT NULL,
  `departmentId` int(11) NOT NULL,
  `title` varchar(100) NOT NULL,
  `description` varchar(1000) DEFAULT NULL,
  `dateFrom` date NOT NULL,
  `dateTo` date NOT NULL,
  `effectiveStartDate` datetime DEFAULT NULL,
  `effectiveEndDate` datetime DEFAULT NULL,
  `companyId` int(11) NOT NULL,
  `groupId` int(11) DEFAULT NULL,
  `activeStatus` varchar(2) DEFAULT NULL,
  `userId` int(11) NOT NULL,
  `dateCreated` datetime NOT NULL,
  `userIdUpdate` int(11) DEFAULT NULL,
  `dateUpdate` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `MasterBook`
--

CREATE TABLE `MasterBook` (
  `bookId` int(11) NOT NULL,
  `bookCode` char(6) NOT NULL,
  `prefixBook` char(6) NOT NULL,
  `bookName` char(40) DEFAULT NULL,
  `startFrom` decimal(10,0) DEFAULT '0',
  `lastNo` decimal(10,0) DEFAULT '0',
  `bookType` char(4) NOT NULL,
  `defaultBook` char(1) DEFAULT 'N',
  `activeStatus` varchar(2) DEFAULT 'A',
  `companyId` int(11) NOT NULL,
  `groupId` int(11) DEFAULT NULL,
  `allowModi` char(1) DEFAULT 'N',
  `userId` int(11) NOT NULL,
  `dateCreated` datetime DEFAULT NULL,
  `userIdUpdate` int(11) DEFAULT NULL,
  `dateUpdate` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `MasterBook`
--

INSERT INTO `MasterBook` (`bookId`, `bookCode`, `prefixBook`, `bookName`, `startFrom`, `lastNo`, `bookType`, `defaultBook`, `activeStatus`, `companyId`, `groupId`, `allowModi`, `userId`, `dateCreated`, `userIdUpdate`, `dateUpdate`) VALUES
(1, 'EMPNO', 'COM-', 'Employe code', '1', '2544', 'EMPL', 'N', 'A', 1, NULL, 'N', 1, NULL, NULL, NULL),
(2, 'TICNO', '#TIC', 'Ticket Issue', '1000', '1231', 'TICK', 'N', 'A', 1, NULL, 'N', 1, '2018-04-14 00:00:00', 1, NULL),
(3, 'EMPNO', 'DEMO-', 'Employe code', '1', '1748', 'EMPL', 'N', 'A', 2, NULL, 'N', 2, '2018-05-07 00:00:00', NULL, NULL),
(4, 'TICNO', '#TIC', 'Ticket Issue', '1000', '1026', 'TICK', 'N', 'A', 2, NULL, 'N', 1, '2018-04-14 00:00:00', 1, NULL);

-- --------------------------------------------------------

--
-- Table structure for table `MasterBookType`
--

CREATE TABLE `MasterBookType` (
  `bookTypeId` int(11) NOT NULL,
  `bookType` char(4) NOT NULL,
  `bookTypeName` char(40) DEFAULT NULL,
  `companyId` int(11) NOT NULL,
  `groupId` int(11) DEFAULT NULL,
  `allowModi` char(1) DEFAULT 'N',
  `userId` int(11) NOT NULL,
  `dateCreated` datetime DEFAULT NULL,
  `userIdUpdate` int(11) DEFAULT NULL,
  `dateUpdate` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `MasterBookType`
--

INSERT INTO `MasterBookType` (`bookTypeId`, `bookType`, `bookTypeName`, `companyId`, `groupId`, `allowModi`, `userId`, `dateCreated`, `userIdUpdate`, `dateUpdate`) VALUES
(1, 'EMPL', 'Employee Master', 1, NULL, 'N', 1, NULL, NULL, NULL),
(3, 'TICK', 'Ticket Master', 1, NULL, 'N', 1, '2018-04-14 00:00:00', 1, NULL);

-- --------------------------------------------------------

--
-- Table structure for table `MenuMaster`
--

CREATE TABLE `MenuMaster` (
  `menuId` int(11) NOT NULL,
  `menuName` varchar(50) NOT NULL,
  `companyId` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `MenuMaster`
--

INSERT INTO `MenuMaster` (`menuId`, `menuName`, `companyId`) VALUES
(1, 'MyFabHr', 1),
(2, 'People', 1),
(3, 'Time & Attendance', 1),
(4, 'Payroll', 1),
(5, 'Helpdesk', 1),
(6, 'Assets', 1),
(7, 'Reports', 1),
(8, 'Settings', 1),
(9, 'Dashboard', 1);

-- --------------------------------------------------------

--
-- Table structure for table `Nominee`
--

CREATE TABLE `Nominee` (
  `employeeCode` varchar(50) NOT NULL,
  `EPF_DOJ` varchar(12) NOT NULL,
  `employeeId` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `Notification`
--

CREATE TABLE `Notification` (
  `notificationId` int(11) NOT NULL,
  `mailId` int(11) DEFAULT NULL,
  `notificationText` varchar(200) NOT NULL,
  `isMail` int(1) NOT NULL,
  `ui` int(1) NOT NULL,
  `sms` int(11) NOT NULL,
  `notificationType` varchar(2) DEFAULT NULL,
  `companyId` int(11) NOT NULL,
  `userId` int(11) NOT NULL,
  `dateCreated` date NOT NULL,
  `userIdUpdate` int(11) NOT NULL,
  `dateUpdate` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `Notification`
--

INSERT INTO `Notification` (`notificationId`, `mailId`, `notificationText`, `isMail`, `ui`, `sms`, `notificationType`, `companyId`, `userId`, `dateCreated`, `userIdUpdate`, `dateUpdate`) VALUES
(1, 4, '<VAR1> has raised  <VAR2> request.', 1, 0, 0, 'LA', 1, 467, '2018-10-05', 467, '2018-10-17'),
(2, 4, '<VAR1> has raised  <VAR2> request.', 1, 1, 1, 'AR', 1, 1, '2018-10-08', 1, '2018-10-08'),
(3, 4, '<VAR1> has been <VAR2> by <VAR3>', 1, 1, 1, 'CL', 1, 12, '2018-10-08', 12, '2018-10-08'),
(4, 5, '<VAR1> has raised  <VAR2> request.', 1, 1, 0, 'IV', 1, 1, '2018-11-16', 1, '2018-11-16'),
(5, 4, '<VAR1> has raised  <VAR2> request.', 1, 0, 0, 'SP', 1, 467, '2018-10-05', 467, '2018-10-17');

-- --------------------------------------------------------

--
-- Table structure for table `ObjectsInSystem`
--

CREATE TABLE `ObjectsInSystem` (
  `objectId` int(11) NOT NULL,
  `objectTechnicalName` char(50) NOT NULL,
  `objectDescription` char(50) NOT NULL,
  `allowModi` char(1) DEFAULT 'N',
  `userId` int(11) NOT NULL,
  `dateCreated` datetime DEFAULT NULL,
  `userIdUpdate` int(11) DEFAULT NULL,
  `dateUpdate` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `ObjectsInSystemInRole`
--

CREATE TABLE `ObjectsInSystemInRole` (
  `ObjectsInSystemInRoleId` int(11) NOT NULL,
  `roleId` int(11) NOT NULL,
  `objectId` int(11) NOT NULL,
  `addRecord` char(1) NOT NULL DEFAULT 'Y',
  `modRecord` char(1) NOT NULL DEFAULT 'N',
  `delRecord` char(1) NOT NULL DEFAULT 'N',
  `viewRecord` char(1) NOT NULL DEFAULT 'Y',
  `allowModi` char(1) DEFAULT 'N',
  `userId` int(11) NOT NULL,
  `dateCreated` datetime DEFAULT NULL,
  `userIdUpdate` int(11) DEFAULT NULL,
  `dateUpdate` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `OneTimeEarningDeduction`
--

CREATE TABLE `OneTimeEarningDeduction` (
  `Id` int(11) NOT NULL,
  `employeeId` int(11) NOT NULL,
  `earningDeductionMonth` varchar(20) DEFAULT NULL,
  `amount` decimal(12,2) DEFAULT '0.00',
  `payHeadId` int(20) NOT NULL,
  `type` varchar(20) NOT NULL,
  `remarks` varchar(150) DEFAULT NULL,
  `companyId` int(11) NOT NULL,
  `userId` int(11) NOT NULL,
  `dateCreated` date NOT NULL,
  `updateId` int(11) DEFAULT NULL,
  `updateDate` date NOT NULL,
  `isEarningDeduction` varchar(2) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `OtherIncome`
--

CREATE TABLE `OtherIncome` (
  `otherIncomeId` int(11) NOT NULL,
  `description` varchar(200) NOT NULL,
  `employeeId` int(11) NOT NULL,
  `amount` decimal(12,2) NOT NULL,
  `status` varchar(15) DEFAULT NULL,
  `approveStatus` varchar(2) DEFAULT NULL,
  `financialYearId` int(10) NOT NULL,
  `otherIncomeDoc` varchar(250) DEFAULT NULL,
  `documentName` varchar(250) DEFAULT NULL,
  `companyId` int(11) NOT NULL,
  `userId` int(11) NOT NULL,
  `effectiveStartDate` datetime DEFAULT NULL,
  `effectiveEndDate` datetime DEFAULT NULL,
  `tdsTransactionUpdateStatus` varchar(10) DEFAULT NULL,
  `activeStatus` varchar(2) DEFAULT NULL,
  `dateCreated` datetime DEFAULT NULL,
  `userIdUpdate` int(11) DEFAULT NULL,
  `dateUpdate` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `Overtime`
--

CREATE TABLE `Overtime` (
  `overtimeId` int(11) NOT NULL,
  `noOfDays` int(11) DEFAULT '0',
  `fixAmount` decimal(12,2) DEFAULT '0.00',
  `ratio` int(11) DEFAULT '1',
  `effectiveStartDate` datetime DEFAULT NULL,
  `effectiveEndDate` datetime DEFAULT NULL,
  `activeStatus` varchar(2) DEFAULT 'A',
  `companyId` int(11) NOT NULL,
  `groupId` int(11) DEFAULT NULL,
  `allowModi` char(1) DEFAULT 'N',
  `userId` int(11) NOT NULL,
  `dateCreated` datetime DEFAULT NULL,
  `userIdUpdate` int(11) DEFAULT NULL,
  `dateUpdate` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Triggers `Overtime`
--
DELIMITER $$
CREATE TRIGGER `overtime_history_trigger` BEFORE UPDATE ON `Overtime` FOR EACH ROW BEGIN

  declare v_overtimeId int(11);
  declare v_noOfDays int(11) ;
  declare v_fixAmount decimal(12,2) ;
  declare v_ratio int(11) ;
  declare v_effectiveStartDate datetime ;
  declare v_effectiveEndDate datetime ;
  declare v_activeStatus varchar(2) ;
  declare v_companyId int(11) ;
  declare v_groupId int(11) ;
  declare v_allowModi char(1) ;
  declare v_userId int(11) ;
  declare v_dateCreated datetime ;
  declare v_userIdUpdate int(11) ;
  declare v_dateUpdate datetime ;
 
 
  set v_overtimeId =OLD.overtimeId;
  set v_noOfDays =OLD.noOfDays ;
  set v_fixAmount =OLD.fixAmount ;
  set v_ratio =OLD.ratio ;
  set v_effectiveStartDate =OLD.effectiveStartDate ;
  set v_effectiveEndDate =OLD.effectiveEndDate ;
  set v_activeStatus =OLD.activeStatus ;
  set v_companyId =OLD.companyId ;
  set v_groupId =OLD.groupId ;
  set v_allowModi =OLD.allowModi ;
  set v_userId =OLD.userId ;
  set v_dateCreated =OLD.dateCreated ;
  set v_userIdUpdate =NEW.userIdUpdate ;
  set v_dateUpdate =OLD.dateUpdate ;
  
 
  Insert INTO OvertimeHistory(

 overtimeId ,
  noOfDays ,
  fixAmount ,
  ratio ,
  effectiveStartDate ,
 effectiveEndDate,
  activeStatus,
  companyId ,
 groupId ,
  allowModi ,
  userId ,
  dateCreated ,
  userIdUpdate ,
  dateUpdate 
      
   ) VALUES
  (
      
  v_overtimeId ,
  v_noOfDays ,
  v_fixAmount ,
  v_ratio ,
  v_effectiveStartDate ,
 v_effectiveEndDate,
  v_activeStatus,
  v_companyId ,
 v_groupId ,
  v_allowModi ,
  v_userId ,
  v_dateCreated ,
  v_userIdUpdate ,
  v_dateUpdate 
      
  );
End
$$
DELIMITER ;

-- --------------------------------------------------------

--
-- Table structure for table `OvertimeHistory`
--

CREATE TABLE `OvertimeHistory` (
  `overtimeId` int(11) NOT NULL,
  `noOfDays` int(11) DEFAULT '0',
  `fixAmount` decimal(12,2) DEFAULT '0.00',
  `ratio` int(11) DEFAULT '1',
  `effectiveStartDate` datetime DEFAULT NULL,
  `effectiveEndDate` datetime DEFAULT NULL,
  `activeStatus` varchar(2) DEFAULT 'A',
  `companyId` int(11) NOT NULL,
  `groupId` int(11) DEFAULT NULL,
  `allowModi` char(1) DEFAULT 'N',
  `userId` int(11) NOT NULL,
  `dateCreated` datetime DEFAULT NULL,
  `userIdUpdate` int(11) DEFAULT NULL,
  `dateUpdate` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `PayHeads`
--

CREATE TABLE `PayHeads` (
  `payHeadId` int(11) NOT NULL,
  `payHeadName` varchar(50) NOT NULL,
  `earningDeduction` varchar(2) NOT NULL,
  `payHeadFlag` varchar(5) DEFAULT NULL,
  `priority` int(11) DEFAULT NULL,
  `sequenceId` bigint(11) DEFAULT NULL,
  `basedOn` varchar(10) DEFAULT NULL,
  `percentage` decimal(5,2) DEFAULT NULL,
  `amount` decimal(10,2) DEFAULT '0.00',
  `incomeType` varchar(2) DEFAULT NULL,
  `isApplicableOnGratuaty` varchar(1) DEFAULT 'N',
  `isApplicableOnPf` varchar(1) DEFAULT 'N',
  `isApplicableOnEsi` varchar(1) DEFAULT 'N',
  `isApplicableOnPt` varchar(1) DEFAULT 'N',
  `isApplicableOnLWS` varchar(1) DEFAULT NULL,
  `expenseType` varchar(2) DEFAULT NULL,
  `effectiveStartDate` datetime DEFAULT NULL,
  `effectiveEndDate` datetime DEFAULT NULL,
  `headType` varchar(20) DEFAULT NULL,
  `activeStatus` varchar(2) DEFAULT NULL,
  `companyId` int(11) NOT NULL,
  `groupId` int(11) DEFAULT NULL,
  `allowModi` char(1) DEFAULT 'N',
  `userId` int(11) NOT NULL,
  `dateCreated` datetime DEFAULT NULL,
  `userIdUpdate` int(11) DEFAULT NULL,
  `dateUpdate` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `PayHeads`
--

INSERT INTO `PayHeads` (`payHeadId`, `payHeadName`, `earningDeduction`, `payHeadFlag`, `priority`, `sequenceId`, `basedOn`, `percentage`, `amount`, `incomeType`, `isApplicableOnGratuaty`, `isApplicableOnPf`, `isApplicableOnEsi`, `isApplicableOnPt`, `isApplicableOnLWS`, `expenseType`, `effectiveStartDate`, `effectiveEndDate`, `headType`, `activeStatus`, `companyId`, `groupId`, `allowModi`, `userId`, `dateCreated`, `userIdUpdate`, `dateUpdate`) VALUES
(1, 'Basic', 'EA', 'Y', 1, 1, 'Gross', '70.00', '14000.00', 'A', 'Y', 'Y', 'Y', 'Y', '', NULL, NULL, NULL, 'Full Time', 'AC', 1, NULL, NULL, 467, '2019-06-06 11:05:03', NULL, '2019-06-17 15:20:02'),
(2, 'Advance Bonus', 'EA', 'Y', 2, 3, 'Basic', '8.33', '1166.20', 'A', 'N', 'N', 'Y', 'Y', '', NULL, NULL, NULL, 'Full Time', 'AC', 1, NULL, NULL, 467, '2019-06-06 11:05:10', NULL, '2019-06-19 11:46:49'),
(3, 'House Rent', 'EA', 'Y', 3, 2, 'Basic', '20.00', '2800.00', 'A', 'N', 'N', 'Y', 'Y', '', NULL, NULL, NULL, 'Full Time', 'AC', 1, NULL, NULL, 467, '2019-06-06 11:05:23', NULL, '2019-06-12 18:14:56'),
(5, 'Leave Travel Allowance', 'EA', 'Y', 10, 9, 'Gross', '34.87', '783.80', 'A', 'Y', 'Y', 'Y', 'Y', '', NULL, NULL, NULL, 'Full Time', 'DE', 1, NULL, NULL, 100067, '2019-05-20 10:52:46', NULL, '2019-06-20 13:05:24'),
(101, 'PF-Employee', 'DE', NULL, NULL, 10, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '', NULL, NULL, NULL, 'One Time', 'AC', 1, NULL, NULL, 1, NULL, NULL, '2019-06-04 14:31:50'),
(102, 'PF-Employer', 'DE', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '', NULL, NULL, NULL, 'One Time', 'AC', 1, NULL, NULL, 1, NULL, NULL, '2019-06-04 14:42:17'),
(103, 'Pension-Employer', 'DE', NULL, 13, NULL, NULL, NULL, NULL, 'A', NULL, NULL, NULL, NULL, '', NULL, NULL, NULL, 'One Time', 'AC', 1, NULL, NULL, 1, NULL, NULL, '2019-05-20 11:13:30'),
(104, 'ESI-Employee', 'DE', NULL, NULL, 11, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '', NULL, NULL, NULL, 'One Time', 'AC', 1, NULL, NULL, 1, NULL, NULL, '2019-05-31 14:13:40'),
(105, 'ESI-Employer', 'DE', NULL, NULL, NULL, NULL, NULL, '0.00', 'A', 'N', 'N', 'N', 'N', '', 'D', NULL, NULL, 'One Time', 'AC', 1, NULL, NULL, 1, NULL, 1, NULL),
(106, 'Professional Tax', 'DE', NULL, NULL, 12, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '', NULL, NULL, NULL, 'One Time', 'AC', 1, NULL, NULL, 1, NULL, NULL, '2019-06-04 14:44:04'),
(107, 'TDS', 'DE', NULL, NULL, 13, NULL, NULL, '0.00', 'A', 'N', 'N', 'N', 'N', '', 'D', NULL, NULL, 'One Time', 'AC', 1, NULL, NULL, 1, NULL, 1, NULL),
(108, 'LWF', 'DE', NULL, NULL, NULL, NULL, NULL, '0.00', 'A', 'N', 'N', 'N', 'N', '', 'D', NULL, NULL, 'One Time', 'AC', 1, NULL, NULL, 1, NULL, 1, NULL),
(110, 'Loan', 'DE', NULL, 14, 14, NULL, NULL, '0.00', 'A', 'N', 'N', 'N', 'N', '', 'D', NULL, NULL, 'One Time', 'AC', 1, NULL, NULL, 1, NULL, 1, NULL),
(114, 'Arrear', 'EA', NULL, NULL, 8, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '', NULL, NULL, NULL, 'One Time', 'AC', 1, NULL, NULL, 467, '2019-05-22 18:51:47', NULL, '2019-06-12 16:23:58'),
(130, 'Overtime', 'EA', 'N', 7, 7, NULL, NULL, NULL, NULL, 'N', 'N', 'Y', 'Y', '', NULL, NULL, NULL, 'One Time', 'AC', 1, NULL, NULL, 467, '2019-05-22 18:51:47', NULL, '2019-05-22 18:51:47'),
(131, 'Recovery', 'DE', 'N', 15, 15, NULL, NULL, NULL, NULL, 'N', 'N', 'Y', 'Y', '', NULL, NULL, NULL, 'One Time', 'AC', 1, NULL, NULL, 467, '2019-05-22 18:51:47', NULL, '2019-05-22 18:51:47'),
(133, 'Medical Allowance', 'EA', 'Y', 4, 5, 'Fixed', NULL, '1250.00', 'A', 'N', 'N', 'Y', 'Y', '', NULL, NULL, NULL, 'Full Time', 'DE', 1, NULL, NULL, 467, '2019-06-05 17:20:12', NULL, '2019-06-12 16:24:03'),
(134, 'Special Allowance', 'EA', 'Y', 5, 6, 'Basic', '10.00', '1050.00', 'A', 'N', 'N', 'Y', 'Y', '', NULL, NULL, NULL, 'Full Time', 'DE', 1, NULL, NULL, 467, '2019-06-04 15:39:29', NULL, '2019-06-19 12:20:58'),
(135, 'Conveyance', 'EA', 'Y', 6, 4, 'Fixed', NULL, '475.35', 'A', 'N', 'N', 'Y', 'Y', '', NULL, NULL, NULL, 'Full Time', 'DE', 1, NULL, NULL, 467, '2019-06-04 15:36:58', NULL, '2019-06-11 15:49:30'),
(136, 'Dearness Allowance', 'EA', 'Y', 10, NULL, 'Fixed', NULL, '2033.80', 'A', 'N', 'Y', 'Y', 'Y', '', NULL, NULL, NULL, 'Full Time', 'DE', 1, NULL, NULL, 467, '2019-06-19 12:20:15', NULL, '2019-07-25 11:40:23'),
(137, 'Advance Salary', 'DE', 'N', NULL, 16, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '', NULL, NULL, NULL, 'One Time', 'AC', 1, NULL, NULL, 467, '2019-06-20 13:02:15', NULL, '2019-06-20 13:02:15'),
(138, 'Mobile Deduction', 'DE', 'N', NULL, 17, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '', NULL, NULL, NULL, 'One Time', 'AC', 1, NULL, NULL, 467, '2019-06-20 13:15:09', NULL, '2019-06-20 13:15:09'),
(139, 'Advance against Expenses', 'DE', 'N', NULL, 18, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '', NULL, NULL, NULL, 'One Time', 'AC', 1, NULL, NULL, 467, '2019-06-20 13:16:43', NULL, '2019-06-20 13:16:43'),
(140, 'Sodexo', 'DE', 'N', NULL, 19, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '', NULL, NULL, NULL, 'One Time', 'AC', 1, NULL, NULL, 467, '2019-06-20 13:17:15', NULL, '2019-06-20 13:17:15'),
(141, 'Other', 'DE', 'N', NULL, 20, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '', NULL, NULL, NULL, 'One Time', 'AC', 1, NULL, NULL, 467, '2019-06-20 13:18:16', NULL, '2019-06-20 13:18:16'),
(142, 'Incentive', 'EA', NULL, NULL, 9, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '', NULL, NULL, NULL, 'One Time', 'AC', 1, NULL, NULL, 467, '2019-06-20 18:52:46', NULL, '2019-06-20 19:41:55'),
(143, 'Performance Bonus', 'EA', 'N', NULL, 9, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '', NULL, NULL, NULL, 'One Time', 'AC', 1, NULL, NULL, 467, '2019-06-20 18:54:14', NULL, '2019-06-20 18:54:14');

--
-- Triggers `PayHeads`
--
DELIMITER $$
CREATE TRIGGER `payHeads_history_trigger` BEFORE UPDATE ON `PayHeads` FOR EACH ROW BEGIN

 declare v_payHeadId int(11) ;
 declare v_payHeadName varchar(50) ;
 declare v_earningDeduction varchar(2) ;
 declare v_incomeType varchar(2) ;
 declare v_isApplicableOnGratuaty varchar(1) ;
 declare v_isApplicableOnPf varchar(1);
 declare v_isApplicableOnEsi varchar(1) ;
 declare v_isApplicableOnPt varchar(1);
 declare v_expenseType varchar(2) ;
 declare v_effectiveStartDate datetime ;
 declare v_effectiveEndDate datetime ;
 declare v_activeStatus varchar(2) ;
 declare v_companyId int(11) ;
 declare v_groupId int(11) ;
 declare v_allowModi char(1) ;
 declare v_userId int(11) ;
 declare v_dateCreated datetime ;
 declare v_userIdUpdate int(11) ;
 declare v_dateUpdate datetime ;


 set v_payHeadId =OLD.payHeadId ;
 set v_payHeadName =OLD.payHeadName ;
 set v_earningDeduction =OLD.earningDeduction ;
 set v_incomeType =OLD.incomeType ;
 set v_isApplicableOnGratuaty =OLD.isApplicableOnGratuaty ;
 set v_isApplicableOnPf =OLD.isApplicableOnPf;
 set v_isApplicableOnEsi =OLD.isApplicableOnEsi ;
 set v_isApplicableOnPt =OLD.isApplicableOnPt;
 set v_expenseType =OLD.expenseType ;
 set v_effectiveStartDate =OLD.effectiveStartDate ;
 set v_effectiveEndDate =OLD.effectiveEndDate ;
 set v_activeStatus =OLD.activeStatus ;
 set v_companyId =OLD.companyId ;
 set v_groupId =OLD.groupId ;
 set v_allowModi =OLD.allowModi ;
 set v_userId =OLD.userId ;
 set v_dateCreated =OLD.dateCreated ;
 set v_userIdUpdate =NEW.userIdUpdate ;
 set v_dateUpdate =OLD.dateUpdate ;


  Insert INTO PayHeadsHistory(

 payHeadId ,
 payHeadName ,
 earningDeduction ,
 incomeType ,
 isApplicableOnGratuaty ,
 isApplicableOnPf ,
 isApplicableOnEsi ,
 isApplicableOnPt ,
 expenseType ,
 effectiveStartDate ,
 effectiveEndDate ,
 activeStatus ,
 companyId ,
 groupId ,
 allowModi ,
 userId ,
 dateCreated ,
 userIdUpdate ,
 dateUpdate 
   ) VALUES
  (
 v_payHeadId ,
 v_payHeadName ,
 v_earningDeduction ,
 v_incomeType ,
 v_isApplicableOnGratuaty ,
 v_isApplicableOnPf ,
 v_isApplicableOnEsi ,
 v_isApplicableOnPt ,
 v_expenseType ,
 v_effectiveStartDate ,
 v_effectiveEndDate ,
 v_activeStatus ,
 v_companyId ,
 v_groupId ,
 v_allowModi ,
 v_userId ,
 v_dateCreated ,
 v_userIdUpdate ,
 v_dateUpdate 
  );
End
$$
DELIMITER ;

-- --------------------------------------------------------

--
-- Table structure for table `PayHeadsHistory`
--

CREATE TABLE `PayHeadsHistory` (
  `payHeadId` int(11) NOT NULL,
  `payHeadName` varchar(50) NOT NULL,
  `earningDeduction` varchar(2) NOT NULL,
  `incomeType` varchar(2) DEFAULT NULL,
  `isApplicableOnGratuaty` varchar(1) DEFAULT 'N',
  `isApplicableOnPf` varchar(1) DEFAULT 'N',
  `isApplicableOnEsi` varchar(1) DEFAULT 'N',
  `isApplicableOnPt` varchar(1) DEFAULT 'N',
  `expenseType` varchar(2) DEFAULT NULL,
  `effectiveStartDate` datetime DEFAULT NULL,
  `effectiveEndDate` datetime DEFAULT NULL,
  `activeStatus` varchar(2) DEFAULT NULL,
  `companyId` int(11) NOT NULL,
  `groupId` int(11) DEFAULT NULL,
  `allowModi` char(1) DEFAULT 'N',
  `userId` int(11) NOT NULL,
  `dateCreated` datetime DEFAULT NULL,
  `userIdUpdate` int(11) DEFAULT NULL,
  `dateUpdate` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `PayHeadsHistory`
--

INSERT INTO `PayHeadsHistory` (`payHeadId`, `payHeadName`, `earningDeduction`, `incomeType`, `isApplicableOnGratuaty`, `isApplicableOnPf`, `isApplicableOnEsi`, `isApplicableOnPt`, `expenseType`, `effectiveStartDate`, `effectiveEndDate`, `activeStatus`, `companyId`, `groupId`, `allowModi`, `userId`, `dateCreated`, `userIdUpdate`, `dateUpdate`) VALUES
(1, 'Basic', 'EA', 'A', 'Y', 'Y', 'Y', 'Y', NULL, NULL, NULL, 'AC', 1, NULL, NULL, 467, '2019-06-06 11:05:03', NULL, '2019-06-17 15:20:02'),
(2, 'Advance Bonus', 'EA', 'A', 'N', 'N', 'Y', 'Y', NULL, NULL, NULL, 'AC', 1, NULL, NULL, 467, '2019-06-06 11:05:10', NULL, '2019-06-19 11:46:49'),
(3, 'House Rent', 'EA', 'A', 'N', 'N', 'Y', 'Y', NULL, NULL, NULL, 'AC', 1, NULL, NULL, 467, '2019-06-06 11:05:23', NULL, '2019-06-12 18:14:56'),
(5, 'Leave Travel Allowance', 'EA', 'A', 'Y', 'Y', 'Y', 'Y', NULL, NULL, NULL, 'DE', 1, NULL, NULL, 100067, '2019-05-20 10:52:46', NULL, '2019-06-20 13:05:24'),
(101, 'PF-Employee', 'DE', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'AC', 1, NULL, NULL, 1, NULL, NULL, '2019-06-04 14:31:50'),
(102, 'PF-Employer', 'DE', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'AC', 1, NULL, NULL, 1, NULL, NULL, '2019-06-04 14:42:17'),
(103, 'Pension-Employer', 'DE', 'A', NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'AC', 1, NULL, NULL, 1, NULL, NULL, '2019-05-20 11:13:30'),
(104, 'ESI-Employee', 'DE', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'AC', 1, NULL, NULL, 1, NULL, NULL, '2019-05-31 14:13:40'),
(105, 'ESI-Employer', 'DE', 'A', 'N', 'N', 'N', 'N', 'D', NULL, NULL, 'AC', 1, NULL, NULL, 1, NULL, 1, NULL),
(106, 'Professional Tax', 'DE', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'AC', 1, NULL, NULL, 1, NULL, NULL, '2019-06-04 14:44:04'),
(107, 'TDS', 'DE', 'A', 'N', 'N', 'N', 'N', 'D', NULL, NULL, 'AC', 1, NULL, NULL, 1, NULL, 1, NULL),
(108, 'LWF', 'DE', 'A', 'N', 'N', 'N', 'N', 'D', NULL, NULL, 'AC', 1, NULL, NULL, 1, NULL, 1, NULL),
(110, 'Loan', 'DE', 'A', 'N', 'N', 'N', 'N', 'D', NULL, NULL, 'AC', 1, NULL, NULL, 1, NULL, 1, NULL),
(114, 'Arrear', 'EA', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'AC', 1, NULL, NULL, 467, '2019-05-22 18:51:47', NULL, '2019-06-12 16:23:58'),
(130, 'Overtime', 'EA', NULL, 'N', 'N', 'Y', 'Y', NULL, NULL, NULL, 'AC', 1, NULL, NULL, 467, '2019-05-22 18:51:47', NULL, '2019-05-22 18:51:47'),
(131, 'Recovery', 'DE', NULL, 'N', 'N', 'Y', 'Y', NULL, NULL, NULL, 'AC', 1, NULL, NULL, 467, '2019-05-22 18:51:47', NULL, '2019-05-22 18:51:47'),
(133, 'Medical Allowance', 'EA', 'A', 'N', 'N', 'Y', 'Y', NULL, NULL, NULL, 'DE', 1, NULL, NULL, 467, '2019-06-05 17:20:12', NULL, '2019-06-12 16:24:03'),
(134, 'Special Allowance', 'EA', 'A', 'N', 'N', 'Y', 'Y', NULL, NULL, NULL, 'DE', 1, NULL, NULL, 467, '2019-06-04 15:39:29', NULL, '2019-06-19 12:20:58'),
(135, 'Conveyance', 'EA', 'A', 'N', 'N', 'Y', 'Y', NULL, NULL, NULL, 'DE', 1, NULL, NULL, 467, '2019-06-04 15:36:58', NULL, '2019-06-11 15:49:30'),
(136, 'Dearness Allowance', 'EA', 'A', 'N', 'Y', 'Y', 'Y', NULL, NULL, NULL, 'AC', 1, NULL, NULL, 467, '2019-06-19 12:20:15', NULL, '2019-06-20 19:34:08'),
(137, 'Advance Salary', 'DE', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'AC', 1, NULL, NULL, 467, '2019-06-20 13:02:15', NULL, '2019-06-20 13:02:15'),
(138, 'Mobile Deduction', 'DE', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'AC', 1, NULL, NULL, 467, '2019-06-20 13:15:09', NULL, '2019-06-20 13:15:09'),
(139, 'Advance against Expenses', 'DE', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'AC', 1, NULL, NULL, 467, '2019-06-20 13:16:43', NULL, '2019-06-20 13:16:43'),
(140, 'Sodexo', 'DE', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'AC', 1, NULL, NULL, 467, '2019-06-20 13:17:15', NULL, '2019-06-20 13:17:15'),
(141, 'Other', 'DE', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'AC', 1, NULL, NULL, 467, '2019-06-20 13:18:16', NULL, '2019-06-20 13:18:16'),
(142, 'Incentive', 'EA', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'AC', 1, NULL, NULL, 467, '2019-06-20 18:52:46', NULL, '2019-06-20 19:41:55'),
(143, 'Performance Bonus', 'EA', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'AC', 1, NULL, NULL, 467, '2019-06-20 18:54:14', NULL, '2019-06-20 18:54:14'),
(136, 'Dearness Allowance', 'EA', 'A', 'N', 'Y', 'Y', 'Y', NULL, NULL, NULL, 'AC', 1, NULL, NULL, 467, '2019-06-19 12:20:15', NULL, '2019-06-20 19:34:08');

-- --------------------------------------------------------

--
-- Table structure for table `PayOut`
--

CREATE TABLE `PayOut` (
  `processMonth` varchar(10) NOT NULL,
  `employeeId` int(11) NOT NULL,
  `payHeadId` int(11) NOT NULL,
  `amount` decimal(12,2) DEFAULT '0.00',
  `loanId` varchar(50) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `PayRegister`
--

CREATE TABLE `PayRegister` (
  `payRegisterId` int(11) NOT NULL,
  `payRegisterHdId` int(11) NOT NULL,
  `employeeId` int(11) NOT NULL,
  `employeeCode` varchar(25) NOT NULL,
  `departmentId` int(11) NOT NULL,
  `userId` int(11) DEFAULT NULL,
  `dateCreated` datetime DEFAULT NULL,
  `userIdUpdate` int(11) DEFAULT NULL,
  `dateUpdate` datetime DEFAULT NULL,
  `payrollLockFlag` tinyint(1) DEFAULT NULL,
  `activeStatus` varchar(2) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `PayRegisterHd`
--

CREATE TABLE `PayRegisterHd` (
  `payRegisterHdId` int(11) NOT NULL,
  `processMonth` varchar(25) NOT NULL,
  `userId` int(11) NOT NULL,
  `dateCreated` date DEFAULT NULL,
  `userIdUpdate` int(11) DEFAULT NULL,
  `dateUpdate` datetime DEFAULT NULL,
  `companyId` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `PayrollControl`
--

CREATE TABLE `PayrollControl` (
  `controlId` int(11) NOT NULL,
  `financialYearId` int(11) DEFAULT NULL,
  `processMonth` varchar(10) NOT NULL,
  `activeStatus` varchar(2) DEFAULT 'A',
  `payrollDays` int(11) NOT NULL,
  `groupId` int(11) DEFAULT NULL,
  `allowModi` char(1) DEFAULT 'N',
  `userId` int(11) NOT NULL,
  `dateCreated` datetime DEFAULT NULL,
  `userIdUpdate` int(11) DEFAULT NULL,
  `dateUpdate` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `PayrollControl`
--

INSERT INTO `PayrollControl` (`controlId`, `financialYearId`, `processMonth`, `activeStatus`, `payrollDays`, `groupId`, `allowModi`, `userId`, `dateCreated`, `userIdUpdate`, `dateUpdate`) VALUES
(1, 1, 'APR-2019', 'OP', 30, NULL, NULL, 1, '2019-07-25 11:41:07', NULL, NULL),
(2, 1, 'MAY-2019', 'OP', 31, NULL, NULL, 1, '2019-07-25 11:41:07', NULL, NULL),
(3, 1, 'JUN-2019', 'OP', 30, NULL, NULL, 1, '2019-07-25 11:41:07', NULL, NULL),
(4, 1, 'JUL-2019', 'OP', 31, NULL, NULL, 1, '2019-07-25 11:41:07', NULL, NULL),
(5, 1, 'AUG-2019', 'OP', 31, NULL, NULL, 1, '2019-07-25 11:41:07', NULL, NULL),
(6, 1, 'SEP-2019', 'OP', 30, NULL, NULL, 1, '2019-07-25 11:41:07', NULL, NULL),
(7, 1, 'OCT-2019', 'OP', 31, NULL, NULL, 1, '2019-07-25 11:41:07', NULL, NULL),
(8, 1, 'NOV-2019', 'OP', 30, NULL, NULL, 1, '2019-07-25 11:41:07', NULL, NULL),
(9, 1, 'DEC-2019', 'OP', 31, NULL, NULL, 1, '2019-07-25 11:41:07', NULL, NULL),
(10, 1, 'JAN-2020', 'OP', 31, NULL, NULL, 1, '2019-07-25 11:41:07', NULL, NULL),
(11, 1, 'FEB-2020', 'OP', 29, NULL, NULL, 1, '2019-07-25 11:41:07', NULL, NULL),
(12, 1, 'MAR-2020', 'OP', 31, NULL, NULL, 1, '2019-07-25 11:41:07', NULL, NULL);

-- --------------------------------------------------------

--
-- Table structure for table `PayRollLock`
--

CREATE TABLE `PayRollLock` (
  `departmentId` int(11) NOT NULL,
  `screenName` varchar(20) NOT NULL,
  `processMonth` varchar(10) NOT NULL,
  `isPayRollLocked` varchar(5) NOT NULL,
  `companyId` int(11) NOT NULL,
  `userId` int(11) NOT NULL,
  `dateCreated` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `PayStructure`
--

CREATE TABLE `PayStructure` (
  `payStructureId` int(11) NOT NULL,
  `payStructureHdId` int(11) NOT NULL,
  `payHeadId` int(11) NOT NULL,
  `amount` decimal(12,2) DEFAULT '0.00',
  `allowModi` char(1) DEFAULT 'N',
  `userId` int(11) NOT NULL,
  `dateCreated` datetime DEFAULT NULL,
  `userIdUpdate` int(11) DEFAULT NULL,
  `dateUpdate` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `PayStructureHd`
--

CREATE TABLE `PayStructureHd` (
  `payStructureHdId` int(11) NOT NULL,
  `employeeId` int(11) NOT NULL,
  `gradesId` int(11) DEFAULT NULL,
  `isNoPFDeduction` char(1) DEFAULT 'N',
  `grossPay` decimal(12,2) DEFAULT '0.00',
  `effectiveDate` date NOT NULL,
  `dateEnd` date DEFAULT NULL,
  `activeStatus` varchar(2) DEFAULT 'A',
  `esicEndDate` date DEFAULT NULL,
  `processMonth` varchar(10) DEFAULT NULL,
  `netPay` decimal(10,0) DEFAULT NULL,
  `ctc` decimal(10,0) DEFAULT NULL,
  `esiEmployee` decimal(10,0) DEFAULT NULL,
  `esiEmployer` decimal(10,0) DEFAULT NULL,
  `epfEmployee` decimal(10,0) DEFAULT NULL,
  `epfEmployer` decimal(10,0) DEFAULT NULL,
  `professionalTax` decimal(10,0) DEFAULT NULL,
  `allowModi` char(1) DEFAULT 'N',
  `userId` int(11) NOT NULL,
  `dateCreated` datetime DEFAULT NULL,
  `userIdUpdate` int(11) DEFAULT NULL,
  `dateUpdate` datetime DEFAULT NULL,
  `costtocompany` decimal(12,2) DEFAULT '0.00',
  `through_excel_flag` varchar(2) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `PreviousEmployerIncome`
--

CREATE TABLE `PreviousEmployerIncome` (
  `previousEmployerIncomeId` int(11) NOT NULL,
  `particular` varchar(50) NOT NULL,
  `userId` int(11) NOT NULL,
  `tdsTransactionUpdateStatus` varchar(10) DEFAULT NULL,
  `dateCreated` datetime NOT NULL,
  `userIdUpdate` int(11) NOT NULL,
  `dateUpdate` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `PreviousEmployerIncome`
--

INSERT INTO `PreviousEmployerIncome` (`previousEmployerIncomeId`, `particular`, `userId`, `tdsTransactionUpdateStatus`, `dateCreated`, `userIdUpdate`, `dateUpdate`) VALUES
(1, 'Income after Exemptions', 1, NULL, '2018-07-02 00:00:00', 1, '2018-07-02 00:00:00'),
(2, 'Professional Tax', 1, NULL, '2018-07-02 00:00:00', 1, '2018-07-02 00:00:00'),
(3, 'Provident Fund', 1, NULL, '2018-07-02 00:00:00', 1, '2018-07-02 00:00:00'),
(4, 'Total Tax', 1, NULL, '2018-07-02 00:00:00', 1, '2018-07-02 00:00:00'),
(5, 'Tax On Income', 1, NULL, '2018-07-02 00:00:00', 1, '2018-07-02 00:00:00'),
(6, 'Surcharge', 1, NULL, '2018-07-03 00:00:00', 1, '2018-07-03 00:00:00'),
(7, 'Education Cess', 1, NULL, '2018-07-03 00:00:00', 1, '2018-07-03 00:00:00');

-- --------------------------------------------------------

--
-- Table structure for table `PreviousEmployerIncomeFile`
--

CREATE TABLE `PreviousEmployerIncomeFile` (
  `PreviousEmployerIncomeFileId` int(16) NOT NULL,
  `fileName` varchar(30) NOT NULL,
  `originalFilename` varchar(150) NOT NULL,
  `filePath` varchar(300) NOT NULL,
  `financialYearId` int(16) NOT NULL,
  `employeeId` int(16) NOT NULL,
  `activeStatus` varchar(10) DEFAULT NULL,
  `userId` int(16) NOT NULL,
  `userIdUpdate` int(16) DEFAULT NULL,
  `dateCreated` date DEFAULT NULL,
  `dateUpdated` date DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `PreviousEmployerIncomeTds`
--

CREATE TABLE `PreviousEmployerIncomeTds` (
  `previousEmployerIncomeTdsId` int(11) NOT NULL,
  `previousEmployerIncomeId` int(11) NOT NULL,
  `amount` decimal(12,2) DEFAULT NULL,
  `employeeId` int(11) NOT NULL,
  `financialYearId` bigint(10) NOT NULL,
  `tdsTransactionUpdateStatus` varchar(5) DEFAULT NULL,
  `userId` int(11) NOT NULL,
  `dateCreated` datetime NOT NULL,
  `userIdUpdate` int(11) NOT NULL,
  `dateUpdate` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `procedure_exception_log`
--

CREATE TABLE `procedure_exception_log` (
  `id` int(11) NOT NULL,
  `procedure_id` int(127) DEFAULT NULL,
  `procedure_name` varchar(100) DEFAULT NULL,
  `process_id` int(127) DEFAULT NULL,
  `code` varchar(100) DEFAULT NULL,
  `error_no` varchar(100) DEFAULT NULL,
  `message` text,
  `mysql_server_time` datetime DEFAULT NULL,
  `scripting_server_time` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `ProfessionalInformation`
--

CREATE TABLE `ProfessionalInformation` (
  `historyId` int(11) NOT NULL,
  `employeeId` int(11) NOT NULL,
  `organizationName` varchar(50) DEFAULT NULL,
  `dateFrom` date DEFAULT NULL,
  `dateTo` date DEFAULT NULL,
  `designation` varchar(50) DEFAULT NULL,
  `reportingTo` varchar(50) DEFAULT NULL,
  `reportingContact` varchar(50) DEFAULT NULL,
  `annualSalary` decimal(12,0) DEFAULT NULL,
  `reasonForChange` varchar(50) DEFAULT NULL,
  `allowModi` char(1) DEFAULT 'N',
  `userId` int(11) NOT NULL,
  `dateCreated` datetime DEFAULT NULL,
  `userIdUpdate` int(11) DEFAULT NULL,
  `dateUpdate` datetime DEFAULT NULL,
  `professionalDoc` varchar(250) DEFAULT NULL,
  `documentName` varchar(250) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `ProfessionalTax`
--

CREATE TABLE `ProfessionalTax` (
  `professionalHeadId` int(11) NOT NULL,
  `stateId` int(11) DEFAULT NULL,
  `perMonthAmount` decimal(12,2) DEFAULT '0.00',
  `limitAmount` decimal(12,2) DEFAULT '0.00',
  `activeStatus` varchar(2) DEFAULT 'A',
  `effectiveStartDate` date DEFAULT NULL,
  `effectiveEndDate` date DEFAULT NULL,
  `companyId` int(11) NOT NULL,
  `groupId` int(11) DEFAULT NULL,
  `allowModi` char(1) DEFAULT 'N',
  `userId` int(11) NOT NULL,
  `dateCreated` datetime DEFAULT NULL,
  `userIdUpdate` int(11) DEFAULT NULL,
  `dateUpdate` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `ProfessionalTax`
--

INSERT INTO `ProfessionalTax` (`professionalHeadId`, `stateId`, `perMonthAmount`, `limitAmount`, `activeStatus`, `effectiveStartDate`, `effectiveEndDate`, `companyId`, `groupId`, `allowModi`, `userId`, `dateCreated`, `userIdUpdate`, `dateUpdate`) VALUES
(1, 1, NULL, NULL, 'DE', NULL, NULL, 1, NULL, NULL, 467, '2019-04-10 00:00:00', 467, NULL),
(152, 48, NULL, NULL, 'DE', NULL, NULL, 1, NULL, NULL, 467, '2019-04-26 17:50:22', 467, NULL),
(153, 33, NULL, NULL, 'AC', NULL, NULL, 1, NULL, NULL, 467, '2019-04-26 17:50:36', 467, NULL),
(154, 23, NULL, NULL, 'DE', NULL, NULL, 1, NULL, NULL, 467, '2019-04-26 17:52:06', 467, NULL),
(155, 1, NULL, NULL, 'DE', NULL, NULL, 1, NULL, NULL, 467, '2019-04-26 20:34:45', 467, NULL),
(156, 44, NULL, NULL, 'DE', NULL, NULL, 1, NULL, NULL, 467, '2019-04-27 12:50:02', 467, NULL),
(157, 44, NULL, NULL, 'DE', NULL, NULL, 1, NULL, NULL, 467, '2019-04-27 12:50:14', 467, NULL),
(158, 44, NULL, NULL, 'DE', NULL, NULL, 1, NULL, NULL, 467, '2019-04-27 12:50:29', 467, NULL),
(159, 42, NULL, NULL, 'DE', NULL, NULL, 1, NULL, NULL, 467, '2019-04-28 15:22:21', 467, NULL),
(160, 40, NULL, NULL, 'DE', NULL, NULL, 1, NULL, NULL, 467, '2019-05-01 11:02:15', 467, NULL),
(161, 42, NULL, NULL, 'DE', NULL, NULL, 1, NULL, NULL, 467, '2019-05-06 11:34:33', 467, NULL),
(162, 42, NULL, NULL, 'DE', NULL, NULL, 1, NULL, NULL, 467, '2019-05-06 11:34:33', 467, NULL),
(163, 48, NULL, NULL, 'DE', NULL, NULL, 1, NULL, NULL, 467, '2019-05-06 11:34:40', 467, NULL),
(164, 48, NULL, NULL, 'DE', NULL, NULL, 1, NULL, NULL, 467, '2019-05-06 11:34:48', 467, NULL),
(165, 42, NULL, NULL, 'AC', NULL, NULL, 1, NULL, NULL, 467, '2019-05-06 11:34:52', 467, NULL),
(166, 1, NULL, NULL, 'DE', NULL, NULL, 1, NULL, NULL, 467, '2019-05-07 13:04:57', 467, NULL),
(167, 38, NULL, NULL, 'AC', NULL, NULL, 1, NULL, NULL, 467, '2019-05-09 16:52:02', 467, NULL),
(168, 37, NULL, NULL, 'AC', NULL, NULL, 1, NULL, NULL, 467, '2019-05-09 17:07:28', 467, NULL),
(169, 49, NULL, NULL, 'DE', NULL, NULL, 1, NULL, NULL, 467, '2019-05-09 17:21:18', 467, NULL),
(170, 46, NULL, NULL, 'DE', NULL, NULL, 1, NULL, NULL, 467, '2019-05-13 18:14:04', 100110, NULL),
(171, 46, NULL, NULL, 'AC', NULL, NULL, 1, NULL, NULL, 467, '2019-05-13 18:14:21', 100110, NULL),
(172, 50, NULL, NULL, 'DE', NULL, NULL, 1, NULL, NULL, 467, '2019-05-13 18:15:40', 467, NULL),
(173, 1, NULL, NULL, 'AC', NULL, NULL, 1, NULL, NULL, 467, '2019-05-16 18:04:52', 467, NULL),
(174, 23, NULL, NULL, 'DE', NULL, NULL, 1, NULL, NULL, 467, '2019-05-16 18:13:19', 467, NULL),
(175, 23, NULL, NULL, 'DE', NULL, NULL, 1, NULL, NULL, 467, '2019-05-16 18:13:32', 467, NULL),
(176, 40, NULL, NULL, 'AC', NULL, NULL, 1, NULL, NULL, 467, '2019-06-19 13:25:53', 467, NULL),
(177, 17, NULL, NULL, 'DE', NULL, NULL, 1, NULL, NULL, 467, '2019-06-24 15:48:36', 100372, NULL),
(178, 17, NULL, NULL, 'AC', NULL, NULL, 1, NULL, NULL, 467, '2019-06-24 15:48:51', 100372, NULL),
(179, 42, NULL, NULL, 'AC', NULL, NULL, 1, NULL, NULL, 467, '2019-07-09 09:45:47', 467, NULL),
(180, 44, NULL, NULL, 'AC', NULL, NULL, 1, NULL, NULL, 467, '2019-07-09 09:53:44', 467, NULL),
(181, 23, NULL, NULL, 'AC', NULL, NULL, 1, NULL, NULL, 467, '2019-07-09 10:05:11', 467, NULL),
(182, 48, NULL, NULL, 'AC', NULL, NULL, 1, NULL, NULL, 467, '2019-07-09 10:11:47', 467, NULL),
(183, 49, NULL, NULL, 'AC', NULL, NULL, 1, NULL, NULL, 467, '2019-07-09 10:24:04', 467, NULL),
(184, 50, NULL, NULL, 'AC', NULL, NULL, 1, NULL, NULL, 467, '2019-07-09 10:25:57', 467, NULL);

--
-- Triggers `ProfessionalTax`
--
DELIMITER $$
CREATE TRIGGER `professionalTax_history_trigger` BEFORE UPDATE ON `ProfessionalTax` FOR EACH ROW BEGIN

  declare v_professionalHeadId int(11);
  declare v_stateId int(11) ;
  declare v_perMonthAmount decimal(12,2) ;
  declare v_limitAmount decimal(12,2) ;
  declare v_activeStatus varchar(2) ;
  declare v_effectiveStartDate date ;
  declare v_effectiveEndDate date ;
  declare v_companyId int(11) ;
  declare v_groupId int(11) ;
  declare v_allowModi char(1) ;
  declare v_userId int(11) ;
  declare v_dateCreated datetime ;
  declare v_userIdUpdate int(11) ;
  declare v_dateUpdate datetime ;
 
 
  set v_professionalHeadId =OLD.professionalHeadId;
  set v_stateId =OLD.stateId ;
  set v_perMonthAmount =OLD.perMonthAmount ;
  set v_limitAmount =OLD.limitAmount ;
  set v_activeStatus =OLD.activeStatus ;
  set v_effectiveStartDate =OLD.effectiveStartDate ;
  set v_effectiveEndDate =OLD.effectiveEndDate ;
  set v_companyId =OLD.companyId ;
  set v_groupId =OLD.groupId ;
  set v_allowModi =OLD.allowModi ;
  set v_userId =OLD.userId ;
  set v_dateCreated =OLD.dateCreated ;
  set v_userIdUpdate =NEW.userIdUpdate ;
  set v_dateUpdate =OLD.dateUpdate ;
 
 
  Insert INTO ProfessionalTaxHistory(

    professionalHeadId ,
 stateId ,
 perMonthAmount ,
 limitAmount ,
  activeStatus ,
  effectiveStartDate ,
 effectiveEndDate ,
 companyId ,
  groupId ,
  allowModi ,
  userId ,
  dateCreated ,
  userIdUpdate ,
  dateUpdate 
      
   ) VALUES
  (
      
 v_professionalHeadId ,
 v_stateId ,
 v_perMonthAmount ,
 v_limitAmount ,
 v_activeStatus ,
 v_effectiveStartDate ,
 v_effectiveEndDate ,
 v_companyId ,
 v_groupId ,
 v_allowModi ,
 v_userId ,
 v_dateCreated ,
 v_userIdUpdate ,
 v_dateUpdate 
      
  );
End
$$
DELIMITER ;

-- --------------------------------------------------------

--
-- Table structure for table `ProfessionalTaxHistory`
--

CREATE TABLE `ProfessionalTaxHistory` (
  `professionalHeadId` int(11) NOT NULL,
  `stateId` int(11) NOT NULL,
  `perMonthAmount` decimal(12,2) DEFAULT '0.00',
  `limitAmount` decimal(12,2) DEFAULT '0.00',
  `activeStatus` varchar(2) DEFAULT 'A',
  `effectiveStartDate` date DEFAULT NULL,
  `effectiveEndDate` date DEFAULT NULL,
  `companyId` int(11) NOT NULL,
  `groupId` int(11) DEFAULT NULL,
  `allowModi` char(1) DEFAULT 'N',
  `userId` int(11) NOT NULL,
  `dateCreated` datetime DEFAULT NULL,
  `userIdUpdate` int(11) DEFAULT NULL,
  `dateUpdate` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `ProfessionalTaxHistory`
--

INSERT INTO `ProfessionalTaxHistory` (`professionalHeadId`, `stateId`, `perMonthAmount`, `limitAmount`, `activeStatus`, `effectiveStartDate`, `effectiveEndDate`, `companyId`, `groupId`, `allowModi`, `userId`, `dateCreated`, `userIdUpdate`, `dateUpdate`) VALUES
(170, 46, NULL, NULL, 'DE', NULL, NULL, 1, NULL, NULL, 100110, '2019-05-13 18:14:04', 100110, NULL),
(171, 46, NULL, NULL, 'AC', NULL, NULL, 1, NULL, NULL, 100110, '2019-05-13 18:14:21', 100110, NULL),
(172, 50, NULL, NULL, 'AC', NULL, NULL, 1, NULL, NULL, 100110, '2019-05-13 18:15:40', 100110, NULL),
(177, 17, NULL, NULL, 'DE', NULL, NULL, 1, NULL, NULL, 100372, '2019-06-24 15:48:36', 100372, NULL),
(178, 17, NULL, NULL, 'AC', NULL, NULL, 1, NULL, NULL, 100372, '2019-06-24 15:48:51', 100372, NULL),
(170, 46, NULL, NULL, 'DE', NULL, NULL, 1, NULL, NULL, 467, '2019-05-13 18:14:04', 467, NULL),
(171, 46, NULL, NULL, 'AC', NULL, NULL, 1, NULL, NULL, 467, '2019-05-13 18:14:21', 467, NULL),
(172, 50, NULL, NULL, 'AC', NULL, NULL, 1, NULL, NULL, 467, '2019-05-13 18:15:40', 467, NULL),
(177, 17, NULL, NULL, 'DE', NULL, NULL, 1, NULL, NULL, 467, '2019-06-24 15:48:36', 467, NULL),
(178, 17, NULL, NULL, 'AC', NULL, NULL, 1, NULL, NULL, 467, '2019-06-24 15:48:51', 467, NULL);

-- --------------------------------------------------------

--
-- Table structure for table `ProfessionalTaxInfo`
--

CREATE TABLE `ProfessionalTaxInfo` (
  `ProfessionalTaxInfoId` int(11) NOT NULL,
  `ProfessionalTaxId` int(11) NOT NULL,
  `category` varchar(2) NOT NULL,
  `limitFrom` decimal(12,2) NOT NULL,
  `limitTo` decimal(12,2) NOT NULL,
  `taxAmount` decimal(12,2) NOT NULL,
  `allowModi` char(1) DEFAULT 'N',
  `userId` int(11) NOT NULL,
  `dateCreated` date DEFAULT NULL,
  `userIdUpdate` int(11) DEFAULT NULL,
  `dateUpdate` date DEFAULT NULL,
  `activeStatus` varchar(2) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `ProfessionalTaxInfo`
--

INSERT INTO `ProfessionalTaxInfo` (`ProfessionalTaxInfoId`, `ProfessionalTaxId`, `category`, `limitFrom`, `limitTo`, `taxAmount`, `allowModi`, `userId`, `dateCreated`, `userIdUpdate`, `dateUpdate`, `activeStatus`) VALUES
(1, 1, 'W', '55.00', '55.00', '55.00', NULL, 467, '2019-04-10', 467, NULL, 'DE'),
(223, 152, 'A', '123.00', '123.00', '123.00', NULL, 467, '2019-04-26', 467, NULL, 'DE'),
(224, 153, 'A', '123.00', '123.00', '12.00', NULL, 467, '2019-04-26', 467, NULL, 'DE'),
(225, 154, 'A', '123.00', '123.00', '12.00', NULL, 467, '2019-04-26', 467, NULL, 'DE'),
(226, 1, 'A', '23.00', '24.00', '8989.00', NULL, 467, '2019-04-26', 467, NULL, 'AC'),
(227, 1, 'M', '24.00', '24.00', '8989.00', NULL, 467, '2019-04-26', 467, NULL, 'AC'),
(228, 155, 'A', '23.00', '24.00', '8989.00', NULL, 467, '2019-04-26', 467, NULL, 'DE'),
(229, 155, 'M', '24.00', '24.00', '8989.00', NULL, 467, '2019-04-26', 467, NULL, 'DE'),
(230, 156, 'A', '56.00', '56.00', '56.00', NULL, 467, '2019-04-27', 467, NULL, 'DE'),
(231, 156, 'A', '55.00', '55.00', '55.00', NULL, 467, '2019-04-27', 467, NULL, 'AC'),
(232, 157, 'A', '56.00', '56.00', '56.00', NULL, 467, '2019-04-27', 467, NULL, 'DE'),
(233, 157, 'A', '55.00', '55.00', '55.00', NULL, 467, '2019-04-27', 467, NULL, 'DE'),
(234, 157, 'A', '5.00', '55.00', '55.00', NULL, 467, '2019-04-27', 467, NULL, 'AC'),
(235, 158, 'A', '56.00', '56.00', '56.00', NULL, 467, '2019-04-27', 467, NULL, 'DE'),
(236, 158, 'A', '5.00', '55.00', '55.00', NULL, 467, '2019-04-27', 467, NULL, 'DE'),
(237, 159, 'A', '45.00', '46.00', '47.00', NULL, 467, '2019-04-28', 467, NULL, 'DE'),
(238, 159, 'M', '46.00', '46.00', '46.00', NULL, 467, '2019-04-28', 467, NULL, 'DE'),
(239, 160, 'A', '19000.00', '25000.00', '167.00', NULL, 467, '2019-05-01', 467, NULL, 'DE'),
(240, 162, 'M', '46.00', '46.00', '46.00', NULL, 467, '2019-05-06', 467, NULL, 'DE'),
(241, 161, 'M', '46.00', '46.00', '46.00', NULL, 467, '2019-04-28', 467, NULL, 'DE'),
(242, 163, 'A', '123.00', '123.00', '123.00', NULL, 467, '2019-05-06', 467, NULL, 'DE'),
(243, 164, 'A', '4001.00', '5000.00', '35.00', NULL, 467, '2019-05-06', 467, NULL, 'DE'),
(244, 165, 'M', '46.00', '46.00', '46.00', NULL, 467, '2019-05-06', 467, NULL, 'AC'),
(245, 155, 'M', '45.00', '556.00', '6885.00', NULL, 467, '2019-05-07', 467, NULL, 'AC'),
(246, 166, 'A', '23.00', '24.00', '8989.00', NULL, 467, '2019-04-26', 467, NULL, 'DE'),
(247, 166, 'M', '45.00', '556.00', '6885.00', NULL, 467, '2019-05-07', 467, NULL, 'DE'),
(248, 167, 'M', '323.00', '233.00', '2333.00', NULL, 467, '2019-05-09', 467, NULL, 'AC'),
(249, 168, 'M', '122.80', '123.00', '123.00', NULL, 467, '2019-05-09', 467, NULL, 'AC'),
(250, 169, 'A', '8334.00', '16667.00', '21.00', NULL, 467, '2019-05-09', 467, NULL, 'DE'),
(251, 170, 'A', '45.00', '56.00', '66.00', NULL, 100110, '2019-05-13', 100110, NULL, 'DE'),
(252, 170, 'A', '56.00', '56.00', '66.00', NULL, 100110, '2019-05-13', 100110, NULL, 'AC'),
(253, 171, 'A', '45.00', '56.00', '66.00', NULL, 100110, '2019-05-13', 100110, NULL, 'DE'),
(254, 171, 'A', '56.00', '56.00', '66.00', NULL, 100110, '2019-05-13', 100110, NULL, 'DE'),
(255, 172, 'A', '20834.00', '999999.00', '200.00', NULL, 467, '2019-05-13', 467, NULL, 'DE'),
(256, 166, 'A', '18750.00', '25000.00', '125.00', NULL, 467, '2019-05-16', 467, NULL, 'AC'),
(257, 166, 'A', '25000.00', '33333.00', '167.00', NULL, 467, '2019-05-16', 467, NULL, 'AC'),
(258, 166, 'A', '33333.00', '99999.00', '208.00', NULL, 467, '2019-05-16', 467, NULL, 'AC'),
(259, 173, 'A', '18750.00', '25000.00', '125.00', NULL, 467, '2019-05-16', 467, NULL, 'AC'),
(260, 173, 'A', '25000.00', '33333.00', '167.00', NULL, 467, '2019-05-16', 467, NULL, 'AC'),
(261, 173, 'A', '33333.00', '99999.00', '208.00', NULL, 467, '2019-05-16', 467, NULL, 'AC'),
(262, 154, 'A', '18750.00', '25000.00', '125.00', NULL, 467, '2019-05-16', 467, NULL, 'AC'),
(263, 154, 'M', '25000.00', '33333.00', '167.00', NULL, 467, '2019-05-16', 467, NULL, 'AC'),
(264, 154, 'W', '33333.00', '999999.00', '208.00', NULL, 467, '2019-05-16', 467, NULL, 'AC'),
(265, 174, 'A', '18750.00', '25000.00', '125.00', NULL, 467, '2019-05-16', 467, NULL, 'DE'),
(266, 174, 'A', '25000.00', '33333.00', '167.00', NULL, 467, '2019-05-16', 467, NULL, 'DE'),
(267, 174, 'A', '33333.00', '999999.00', '208.00', NULL, 467, '2019-05-16', 467, NULL, 'DE'),
(268, 175, 'W', '7501.00', '10000.00', '0.00', NULL, 467, '2019-05-16', 467, NULL, 'DE'),
(269, 175, 'M', '7501.00', '10000.00', '175.00', NULL, 467, '2019-05-16', 467, NULL, 'DE'),
(270, 175, 'A', '10001.00', '999999.00', '208.00', NULL, 467, '2019-05-16', 467, NULL, 'DE'),
(271, 176, 'A', '19000.00', '25000.00', '167.00', NULL, 467, '2019-05-01', 467, NULL, 'AC'),
(272, 177, 'A', '12.00', '12.00', '12.00', NULL, 100372, '2019-06-24', 100372, NULL, 'DE'),
(273, 178, 'A', '12.00', '12.00', '12.00', NULL, 100372, '2019-06-24', 100372, NULL, 'DE'),
(274, 161, 'A', '25001.00', '41667.00', '100.00', NULL, 467, '2019-07-09', 467, NULL, 'AC'),
(275, 161, 'A', '41668.00', '66667.00', '150.00', NULL, 467, '2019-07-09', 467, NULL, 'AC'),
(276, 161, 'A', '66668.00', '83333.00', '175.00', NULL, 467, '2019-07-09', 467, NULL, 'AC'),
(277, 161, 'A', '83334.00', '999999.00', '208.00', NULL, 467, '2019-07-09', 467, NULL, 'AC'),
(278, 179, 'A', '25001.00', '41667.00', '100.00', NULL, 467, '2019-07-09', 467, NULL, 'AC'),
(279, 179, 'A', '41668.00', '66667.00', '150.00', NULL, 467, '2019-07-09', 467, NULL, 'AC'),
(280, 179, 'A', '66668.00', '83333.00', '175.00', NULL, 467, '2019-07-09', 467, NULL, 'AC'),
(281, 179, 'A', '83334.00', '999999.00', '208.00', NULL, 467, '2019-07-09', 467, NULL, 'AC'),
(282, 158, 'A', '12000.00', '17999.00', '120.00', NULL, 467, '2019-07-09', 467, NULL, 'AC'),
(283, 158, 'A', '18000.00', '29999.00', '180.00', NULL, 467, '2019-07-09', 467, NULL, 'AC'),
(284, 158, 'A', '30000.00', '44999.00', '300.00', NULL, 467, '2019-07-09', 467, NULL, 'AC'),
(285, 158, 'A', '45000.00', '59999.00', '450.00', NULL, 467, '2019-07-09', 467, NULL, 'AC'),
(286, 158, 'A', '60000.00', '74999.00', '600.00', NULL, 467, '2019-07-09', 467, NULL, 'AC'),
(287, 158, 'A', '75000.00', '99999.00', '750.00', NULL, 467, '2019-07-09', 467, NULL, 'AC'),
(288, 158, 'A', '100000.00', '124999.00', '1000.00', NULL, 467, '2019-07-09', 467, NULL, 'AC'),
(289, 158, 'A', '125000.00', '999999.00', '1250.00', NULL, 467, '2019-07-09', 467, NULL, 'AC'),
(290, 180, 'A', '12000.00', '17999.00', '120.00', NULL, 467, '2019-07-09', 467, NULL, 'AC'),
(291, 180, 'A', '18000.00', '29999.00', '180.00', NULL, 467, '2019-07-09', 467, NULL, 'AC'),
(292, 180, 'A', '30000.00', '44999.00', '300.00', NULL, 467, '2019-07-09', 467, NULL, 'AC'),
(293, 180, 'A', '45000.00', '59999.00', '450.00', NULL, 467, '2019-07-09', 467, NULL, 'AC'),
(294, 180, 'A', '60000.00', '74999.00', '600.00', NULL, 467, '2019-07-09', 467, NULL, 'AC'),
(295, 180, 'A', '75000.00', '99999.00', '750.00', NULL, 467, '2019-07-09', 467, NULL, 'AC'),
(296, 180, 'A', '100000.00', '124999.00', '1000.00', NULL, 467, '2019-07-09', 467, NULL, 'AC'),
(297, 180, 'A', '125000.00', '999999.00', '1250.00', NULL, 467, '2019-07-09', 467, NULL, 'AC'),
(298, 181, 'W', '7501.00', '10000.00', '0.00', NULL, 467, '2019-05-16', 467, NULL, 'AC'),
(299, 181, 'M', '7501.00', '10000.00', '175.00', NULL, 467, '2019-05-16', 467, NULL, 'AC'),
(300, 181, 'A', '10001.00', '999999.00', '208.00', NULL, 467, '2019-05-16', 467, NULL, 'AC'),
(301, 164, 'A', '5001.00', '7000.00', '75.00', NULL, 467, '2019-07-09', 467, NULL, 'AC'),
(302, 164, 'A', '7001.00', '9000.00', '110.00', NULL, 467, '2019-07-09', 467, NULL, 'AC'),
(303, 164, 'A', '9001.00', '12000.00', '180.00', NULL, 467, '2019-07-09', 467, NULL, 'AC'),
(304, 164, 'A', '12001.00', '999999.00', '208.00', NULL, 467, '2019-07-09', 467, NULL, 'AC'),
(305, 182, 'A', '4001.00', '5000.00', '35.00', NULL, 467, '2019-05-06', 467, NULL, 'AC'),
(306, 182, 'A', '5001.00', '7000.00', '75.00', NULL, 467, '2019-07-09', 467, NULL, 'AC'),
(307, 182, 'A', '7001.00', '9000.00', '110.00', NULL, 467, '2019-07-09', 467, NULL, 'AC'),
(308, 182, 'A', '9001.00', '12000.00', '180.00', NULL, 467, '2019-07-09', 467, NULL, 'AC'),
(309, 182, 'A', '12001.00', '999999.00', '208.00', NULL, 467, '2019-07-09', 467, NULL, 'AC'),
(310, 169, 'A', '16668.00', '25000.00', '42.00', NULL, 467, '2019-07-09', 467, NULL, 'AC'),
(311, 169, 'A', '25001.00', '33333.00', '63.00', NULL, 467, '2019-07-09', 467, NULL, 'AC'),
(312, 169, 'A', '33334.00', '41667.00', '83.00', NULL, 467, '2019-07-09', 467, NULL, 'AC'),
(313, 169, 'A', '41668.00', '999999.00', '104.00', NULL, 467, '2019-07-09', 467, NULL, 'AC'),
(314, 183, 'A', '8334.00', '16667.00', '21.00', NULL, 467, '2019-05-09', 467, NULL, 'AC'),
(315, 183, 'A', '16668.00', '25000.00', '42.00', NULL, 467, '2019-07-09', 467, NULL, 'AC'),
(316, 183, 'A', '25001.00', '33333.00', '63.00', NULL, 467, '2019-07-09', 467, NULL, 'AC'),
(317, 183, 'A', '33334.00', '41667.00', '83.00', NULL, 467, '2019-07-09', 467, NULL, 'AC'),
(318, 183, 'A', '41668.00', '999999.00', '104.00', NULL, 467, '2019-07-09', 467, NULL, 'AC'),
(319, 184, 'A', '20834.00', '999999.00', '200.00', NULL, 467, '2019-05-13', 467, NULL, 'AC');

-- --------------------------------------------------------

--
-- Table structure for table `Project`
--

CREATE TABLE `Project` (
  `projectId` int(11) NOT NULL,
  `departmentId` int(11) DEFAULT NULL,
  `clientId` int(11) DEFAULT NULL,
  `projectName` char(40) DEFAULT NULL,
  `branchId` int(11) DEFAULT NULL,
  `effectiveStartDate` datetime DEFAULT NULL,
  `effectiveEndDate` datetime DEFAULT NULL,
  `activeStatus` varchar(2) DEFAULT NULL,
  `companyId` int(11) NOT NULL,
  `groupId` int(11) DEFAULT NULL,
  `allowModi` char(1) DEFAULT 'N',
  `userId` int(11) NOT NULL,
  `dateCreated` datetime DEFAULT NULL,
  `userIdUpdate` int(11) DEFAULT NULL,
  `dateUpdate` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Triggers `Project`
--
DELIMITER $$
CREATE TRIGGER `project_history_trigger` BEFORE UPDATE ON `Project` FOR EACH ROW BEGIN

 declare v_projectId int(11) ;
 declare v_departmentId int(11) ;
 declare v_clientId int(11) ;
 declare  v_projectName char(40);
 declare  v_branchId int(11) ;
 declare v_effectiveStartDate datetime;
 declare v_effectiveEndDate datetime ;
 declare v_activeStatus varchar(2) ;
declare  v_companyId int(11) ;
declare  v_groupId int(11);
declare  v_allowModi char(1) ;
declare  v_userId int(11) ;
declare  v_dateCreated datetime ;
 declare v_userIdUpdate int(11) ;
 declare v_dateUpdate datetime ;


  set v_projectId =OLD.projectId ;
 set v_departmentId =OLD.departmentId ;
 set v_clientId =OLD.clientId ;
set  v_projectName =OLD.projectName;
set  v_branchId =OLD.branchId ;
 set v_effectiveStartDate =OLD.effectiveStartDate;
 set v_effectiveEndDate =OLD.effectiveEndDate ;
 set v_activeStatus =OLD.activeStatus ;
set  v_companyId =OLD.companyId ;
set  v_groupId =OLD.groupId;
set  v_allowModi =OLD.allowModi ;
set  v_userId =OLD.userId ;
set  v_dateCreated =OLD.dateCreated ;
 set v_userIdUpdate =NEW.userIdUpdate ;
 set v_dateUpdate =OLD.dateUpdate ;


Insert INTO ProjectHistory(
projectId ,
 departmentId,
 clientId ,
 projectName,
branchId ,
 effectiveStartDate ,
effectiveEndDate ,
 activeStatus ,
companyId ,
groupId ,
allowModi ,
userId ,
dateCreated ,
 userIdUpdate ,
 dateUpdate 
    
   ) VALUES
  (
      
v_projectId ,
 v_departmentId,
 v_clientId ,
 v_projectName,
v_branchId ,
 v_effectiveStartDate ,
v_effectiveEndDate ,
 v_activeStatus ,
v_companyId ,
v_groupId ,
v_allowModi ,
v_userId ,
v_dateCreated ,
 v_userIdUpdate ,
 v_dateUpdate 
      
  );
End
$$
DELIMITER ;

-- --------------------------------------------------------

--
-- Table structure for table `ProjectHistory`
--

CREATE TABLE `ProjectHistory` (
  `projectId` int(11) NOT NULL,
  `departmentId` int(11) DEFAULT NULL,
  `clientId` int(11) DEFAULT NULL,
  `projectName` char(40) DEFAULT NULL,
  `branchId` int(11) DEFAULT NULL,
  `effectiveStartDate` datetime DEFAULT NULL,
  `effectiveEndDate` datetime DEFAULT NULL,
  `activeStatus` varchar(2) DEFAULT NULL,
  `companyId` int(11) NOT NULL,
  `groupId` int(11) DEFAULT NULL,
  `allowModi` char(1) DEFAULT 'N',
  `userId` int(11) NOT NULL,
  `dateCreated` datetime DEFAULT NULL,
  `userIdUpdate` int(11) DEFAULT NULL,
  `dateUpdate` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `Provision`
--

CREATE TABLE `Provision` (
  `provisionId` int(11) NOT NULL,
  `employeeId` int(11) NOT NULL,
  `companyId` int(11) NOT NULL,
  `departmentId` int(11) NOT NULL,
  `processMonth` varchar(10) NOT NULL,
  `narration` varchar(200) NOT NULL,
  `effectiveStartDate` datetime DEFAULT NULL,
  `effectiveEndDate` datetime DEFAULT NULL,
  `activeStatus` varchar(2) NOT NULL,
  `userId` int(11) NOT NULL,
  `dateCreated` datetime DEFAULT NULL,
  `userIdUpdate` int(11) DEFAULT NULL,
  `dateUpdate` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `PunchTimeDetail`
--

CREATE TABLE `PunchTimeDetail` (
  `punchTimeDetailsId` int(11) NOT NULL,
  `date` datetime NOT NULL,
  `flag` varchar(50) NOT NULL,
  `hhMm` int(11) DEFAULT NULL,
  `time` varchar(50) DEFAULT NULL,
  `in_out` varchar(50) NOT NULL,
  `sNo` int(11) NOT NULL,
  `tktNo` varchar(50) NOT NULL,
  `tmmm` int(50) DEFAULT NULL,
  `code` int(11) DEFAULT NULL,
  `latitude` varchar(500) DEFAULT NULL,
  `longitude` varchar(500) DEFAULT NULL,
  `address` varchar(500) DEFAULT NULL,
  `companyId` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `PunchTimeDetails`
--

CREATE TABLE `PunchTimeDetails` (
  `PunchTimeDetailsId` int(11) NOT NULL,
  `tktno` varchar(255) DEFAULT NULL,
  `hhmm` float DEFAULT NULL,
  `tmmm` float DEFAULT NULL,
  `hh_mm` float DEFAULT NULL,
  `flag` varchar(255) DEFAULT NULL,
  `date` datetime DEFAULT NULL,
  `sno` float DEFAULT NULL,
  `INOUT` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `RAW_REPORT`
--

CREATE TABLE `RAW_REPORT` (
  `DATE` varchar(222) DEFAULT NULL,
  `IMPRESSIONS` varchar(222) DEFAULT NULL,
  `CLICKS` varchar(222) DEFAULT NULL,
  `EARNING` varchar(222) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `ReportPayOut`
--

CREATE TABLE `ReportPayOut` (
  `processMonth` varchar(10) NOT NULL,
  `processDate` date DEFAULT NULL,
  `employeeId` int(11) NOT NULL,
  `departmentId` int(11) NOT NULL,
  `companyId` int(11) NOT NULL,
  `cityId` int(11) NOT NULL,
  `employeeCode` varchar(10) DEFAULT NULL,
  `Name` varchar(100) DEFAULT NULL,
  `bankName` varchar(50) DEFAULT NULL,
  `accountNumber` varchar(20) DEFAULT NULL,
  `dateOfJoining` date DEFAULT NULL,
  `presense` decimal(5,2) DEFAULT '0.00',
  `weekoff` decimal(5,2) DEFAULT '0.00',
  `publicholidays` decimal(5,2) DEFAULT '0.00',
  `leaves` decimal(10,2) NOT NULL,
  `paidleave` decimal(5,2) DEFAULT '0.00',
  `casualleave` decimal(6,2) DEFAULT '0.00',
  `seekleave` decimal(5,2) DEFAULT '0.00',
  `absense` decimal(5,2) DEFAULT '0.00',
  `payDays` decimal(5,2) DEFAULT '0.00',
  `payableDays` decimal(5,2) NOT NULL,
  `Basic` decimal(12,2) DEFAULT '0.00',
  `BasicEarning` decimal(12,2) DEFAULT '0.00',
  `dearnessAllowance` decimal(12,2) DEFAULT '0.00',
  `dearnessAllowanceEarning` decimal(12,2) DEFAULT '0.00',
  `ConveyanceAllowance` decimal(12,2) DEFAULT '0.00',
  `ConveyanceAllowanceEarning` decimal(12,2) DEFAULT '0.00',
  `HRA` decimal(12,2) DEFAULT '0.00',
  `HRAEarning` decimal(12,2) DEFAULT '0.00',
  `MedicalAllowance` decimal(12,2) DEFAULT '0.00',
  `MedicalAllowanceEarning` decimal(12,2) DEFAULT '0.00',
  `AdvanceBonus` decimal(12,2) DEFAULT '0.00',
  `AdvanceBonusEarning` decimal(12,2) DEFAULT '0.00',
  `SpecialAllowance` decimal(12,2) DEFAULT '0.00',
  `SpecialAllowanceEarning` decimal(12,2) DEFAULT '0.00',
  `leaveTravelAllowance` decimal(12,2) DEFAULT '0.00',
  `leaveTravelAllowanceEarning` decimal(12,2) DEFAULT '0.00',
  `performanceLinkedIncome` decimal(12,2) DEFAULT '0.00',
  `performanceLinkedIncomeEarning` decimal(12,2) DEFAULT '0.00',
  `uniformAllowance` decimal(12,2) DEFAULT '0.00',
  `uniformAllowanceEarning` decimal(12,2) DEFAULT '0.00',
  `CompanyBenefits` decimal(12,2) DEFAULT '0.00',
  `CompanyBenefitsEarning` decimal(12,2) DEFAULT '0.00',
  `EmployeeLoansAdvnace` decimal(12,2) DEFAULT '0.00',
  `EmployeeLoansAdvnaceEarning` decimal(12,2) DEFAULT '0.00',
  `ProvidentFundEmployee` decimal(12,2) DEFAULT '0.00',
  `ProvidentFundEmployer` decimal(12,2) DEFAULT '0.00',
  `ProvidentFundEmployerPension` decimal(12,2) DEFAULT '0.00',
  `ESI_Employee` decimal(12,2) DEFAULT '0.00',
  `ESI_Employer` decimal(12,2) DEFAULT '0.00',
  `PT` decimal(12,2) DEFAULT '0.00',
  `LWF` decimal(12,2) DEFAULT NULL,
  `TDS` decimal(12,2) DEFAULT '0.00',
  `Loan` decimal(12,2) DEFAULT '0.00',
  `GrossSalary` decimal(12,2) DEFAULT '0.00',
  `TotalEarning` decimal(12,2) DEFAULT '0.00',
  `stdEpfWagesAmount` decimal(12,2) DEFAULT '0.00',
  `EarnedStdEpfWagesAmount` decimal(12,2) DEFAULT '0.00',
  `TotalDeduction` decimal(12,2) DEFAULT '0.00',
  `NetPayableAmount` decimal(12,2) DEFAULT '0.00',
  `UANNO` varchar(20) DEFAULT NULL,
  `PFNumber` varchar(50) DEFAULT NULL,
  `ESICNumber` varchar(50) DEFAULT NULL,
  `fatherName` varchar(100) DEFAULT NULL,
  `DOB` date DEFAULT NULL,
  `gender` varchar(10) DEFAULT NULL,
  `husbandName` varchar(100) DEFAULT NULL,
  `maritalStatus` varchar(20) DEFAULT NULL,
  `epfJoining` date DEFAULT NULL,
  `IFSCCode` varchar(20) DEFAULT NULL,
  `aadharNo` varchar(20) DEFAULT NULL,
  `PANNO` varchar(20) DEFAULT NULL,
  `mobileNo` varchar(15) DEFAULT NULL,
  `email` varchar(50) DEFAULT NULL,
  `otherAllowance` decimal(12,2) DEFAULT '0.00',
  `otherAllowanceEarning` decimal(12,2) DEFAULT '0.00',
  `overtime` decimal(5,2) DEFAULT '0.00',
  `transactionNo` varchar(20) DEFAULT NULL,
  `bankAccountNumber` varchar(20) DEFAULT NULL,
  `bankAmount` decimal(12,2) DEFAULT '0.00',
  `userId` int(11) NOT NULL,
  `userIdUpdate` int(11) DEFAULT NULL,
  `dateCreated` date NOT NULL,
  `dateUpdate` date DEFAULT NULL,
  `PFEarning` decimal(12,2) DEFAULT '0.00',
  `PensionEarningSalary` decimal(12,2) DEFAULT '0.00',
  `otherEarning` decimal(12,2) NOT NULL DEFAULT '0.00',
  `incentive` decimal(10,2) DEFAULT NULL,
  `performanceBonus` decimal(10,2) DEFAULT '0.00',
  `otherDeduction` decimal(12,2) DEFAULT '0.00',
  `arearAmount` decimal(10,2) DEFAULT '0.00',
  `tdsAmount` decimal(10,2) DEFAULT '0.00',
  `isNoPFDeduction` char(1) DEFAULT 'N',
  `isNoESIDeduction` char(1) DEFAULT 'N',
  `remarks` varchar(250) DEFAULT NULL,
  `epfNominee` varchar(200) DEFAULT NULL,
  `epfNomineeRelation` varchar(100) DEFAULT NULL,
  `esicNominee` varchar(200) DEFAULT NULL,
  `esicNomineeRelation` varchar(100) DEFAULT NULL,
  `esicjoining` date DEFAULT NULL,
  `transactionMode` varchar(125) DEFAULT NULL,
  `isEpfOnActualAmount` varchar(2) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `RoleMaster`
--

CREATE TABLE `RoleMaster` (
  `roleId` int(11) NOT NULL,
  `roleDescription` char(50) NOT NULL,
  `activeStatus` varchar(2) NOT NULL,
  `allowModi` char(1) DEFAULT 'N',
  `userId` int(11) NOT NULL,
  `dateCreated` datetime DEFAULT NULL,
  `userIdUpdate` int(11) DEFAULT NULL,
  `dateUpdate` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `RoleMaster`
--

INSERT INTO `RoleMaster` (`roleId`, `roleDescription`, `activeStatus`, `allowModi`, `userId`, `dateCreated`, `userIdUpdate`, `dateUpdate`) VALUES
(1, 'Admin', 'AC', NULL, 1, NULL, 467, '2019-06-29 17:18:27'),
(2, 'SuperAdmin', 'DE', NULL, 1, NULL, 1, '2019-07-25 11:45:53'),
(3, 'ESSUser', 'AC', NULL, 1, NULL, 467, '2019-07-02 10:59:29'),
(4, 'FSSUser', 'AC', NULL, 1, NULL, 467, '2019-07-02 10:59:24'),
(5, 'Dss', 'DE', NULL, 467, NULL, 467, '2019-06-29 16:29:33'),
(6, 'Developer', 'DE', NULL, 467, '2019-07-16 17:03:32', 1, '2019-07-25 11:45:39');

-- --------------------------------------------------------

--
-- Table structure for table `RoleSubmenuActionMaster`
--

CREATE TABLE `RoleSubmenuActionMaster` (
  `roleSubmenuActionId` int(11) NOT NULL,
  `roleId` int(11) NOT NULL,
  `status` varchar(11) DEFAULT NULL,
  `submenuActionId` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `RoleSubmenuActionMaster`
--

INSERT INTO `RoleSubmenuActionMaster` (`roleSubmenuActionId`, `roleId`, `status`, `submenuActionId`) VALUES
(1, 1, 'AC', 2),
(2, 1, 'AC', 1),
(3, 1, 'AC', 3),
(4, 1, 'AC', 5),
(5, 1, 'AC', 6),
(6, 1, 'AC', 10),
(7, 1, 'AC', 13),
(8, 6, 'DE', 1),
(9, 6, 'DE', 2),
(10, 6, 'AC', 3),
(11, 6, 'AC', 5),
(12, 6, 'AC', 10),
(13, 6, 'AC', 6),
(14, 6, 'AC', 11),
(15, 6, 'AC', 12),
(16, 6, 'AC', 13),
(17, 6, 'AC', 15),
(18, 6, 'AC', 14),
(19, 6, 'AC', 16),
(20, 6, 'AC', 17),
(21, 6, 'AC', 18),
(22, 6, 'AC', 19),
(23, 6, 'AC', 20),
(24, 6, 'AC', 21),
(25, 6, 'AC', 22),
(26, 6, 'AC', 23),
(27, 6, 'AC', 24),
(28, 6, 'AC', 25),
(29, 6, 'AC', 26),
(30, 6, 'AC', 28),
(31, 6, 'AC', 30),
(32, 6, 'AC', 31),
(33, 6, 'AC', 34),
(34, 6, 'AC', 4),
(35, 6, 'AC', 7),
(36, 6, 'AC', 8),
(37, 6, 'AC', 27),
(38, 1, 'AC', 11),
(39, 1, 'AC', 12),
(40, 1, 'AC', 14),
(41, 1, 'AC', 15),
(42, 1, 'AC', 16),
(43, 1, 'AC', 17),
(44, 1, 'AC', 18),
(45, 1, 'AC', 19),
(46, 1, 'AC', 20),
(47, 1, 'AC', 21),
(48, 1, 'AC', 22),
(49, 1, 'AC', 23),
(50, 1, 'AC', 24),
(51, 1, 'AC', 25),
(52, 1, 'AC', 26),
(53, 1, 'AC', 28),
(54, 1, 'AC', 30),
(55, 1, 'AC', 31),
(56, 1, 'AC', 34),
(57, 6, 'AC', 87),
(58, 6, 'AC', 88),
(59, 6, 'AC', 98),
(60, 6, 'AC', 89),
(61, 6, 'DE', 90),
(62, 6, 'AC', 90),
(63, 6, 'AC', 96),
(64, 6, 'AC', 97),
(65, 6, 'AC', 95),
(66, 1, 'AC', 87),
(67, 1, 'AC', 88),
(68, 1, 'AC', 89),
(69, 1, 'AC', 90),
(70, 1, 'AC', 97),
(71, 1, 'AC', 96),
(72, 1, 'AC', 95),
(112, 1, 'AC', 107),
(113, 1, 'AC', 107),
(114, 1, 'AC', 108),
(115, 1, 'AC', 108),
(116, 1, 'AC', 109),
(118, 1, 'AC', 111),
(119, 1, 'AC', 111),
(120, 1, 'AC', 112),
(121, 1, 'AC', 112),
(122, 1, 'AC', 110),
(123, 1, 'AC', 110),
(124, 1, 'AC', 113),
(125, 1, 'AC', 114),
(126, 1, 'AC', 113),
(127, 1, 'AC', 116),
(128, 1, 'AC', 114),
(129, 1, 'AC', 118),
(130, 1, 'AC', 116),
(131, 1, 'AC', 120),
(132, 1, 'AC', 118),
(133, 1, 'AC', 123),
(134, 1, 'AC', 120),
(135, 1, 'AC', 122),
(136, 1, 'AC', 123),
(137, 1, 'AC', 124),
(138, 1, 'AC', 122),
(139, 1, 'AC', 125),
(140, 1, 'AC', 124),
(141, 1, 'DE', 126),
(142, 1, 'AC', 125),
(143, 1, 'DE', 126),
(144, 6, 'AC', 107),
(145, 6, 'AC', 108),
(146, 6, 'AC', 109),
(147, 6, 'AC', 110),
(148, 6, 'AC', 111),
(149, 6, 'AC', 112),
(150, 6, 'AC', 113),
(151, 6, 'AC', 114),
(152, 6, 'AC', 116),
(153, 6, 'AC', 118),
(154, 6, 'AC', 120),
(155, 6, 'AC', 122),
(156, 6, 'AC', 123),
(157, 6, 'AC', 124),
(158, 6, 'AC', 125),
(159, 6, 'AC', 126),
(160, 6, 'AC', 32),
(161, 6, 'AC', 33),
(162, 6, 'AC', 39),
(163, 6, 'AC', 158),
(164, 6, 'AC', 40),
(165, 6, 'AC', 41),
(166, 6, 'AC', 43),
(167, 6, 'AC', 44),
(168, 6, 'AC', 159),
(169, 6, 'AC', 45),
(170, 6, 'AC', 46),
(171, 6, 'AC', 47),
(172, 6, 'AC', 160),
(173, 3, 'AC', 107),
(174, 3, 'AC', 108),
(175, 3, 'AC', 109),
(176, 3, 'AC', 110),
(177, 3, 'AC', 111),
(178, 3, 'AC', 113),
(179, 3, 'AC', 114),
(180, 3, 'AC', 116),
(181, 3, 'AC', 118),
(182, 3, 'AC', 120),
(183, 3, 'DE', 122),
(184, 3, 'DE', 123),
(185, 3, 'DE', 124),
(186, 3, 'DE', 125),
(187, 3, 'DE', 126),
(188, 3, 'AC', 112),
(189, 1, 'AC', 32),
(190, 1, 'AC', 33),
(191, 6, 'AC', 161),
(192, 6, 'AC', 162),
(193, 6, 'AC', 163),
(194, 1, 'AC', 161),
(195, 1, 'AC', 162),
(196, 1, 'AC', 163),
(197, 1, 'AC', 164),
(198, 1, 'AC', 165),
(199, 1, 'AC', 166),
(200, 1, 'AC', 53),
(201, 1, 'AC', 54),
(202, 1, 'AC', 55),
(203, 1, 'AC', 56),
(204, 1, 'AC', 40),
(205, 1, 'AC', 41),
(206, 1, 'AC', 43),
(207, 1, 'AC', 44),
(208, 1, 'AC', 159),
(209, 1, 'AC', 45),
(210, 1, 'AC', 46),
(211, 1, 'AC', 47),
(212, 1, 'AC', 160),
(213, 1, 'AC', 4),
(214, 1, 'AC', 7),
(215, 1, 'AC', 8),
(216, 1, 'AC', 27),
(217, 1, 'AC', 29),
(218, 1, 'AC', 4),
(219, 1, 'AC', 7),
(220, 1, 'AC', 8),
(221, 1, 'AC', 27),
(222, 1, 'AC', 29),
(223, 6, 'AC', 48),
(224, 6, 'AC', 49),
(225, 6, 'AC', 50),
(226, 6, 'AC', 51),
(227, 6, 'AC', 59),
(251, 6, 'AC', 76),
(252, 6, 'AC', 77),
(253, 6, 'AC', 80),
(254, 6, 'AC', 81),
(255, 6, 'AC', 52),
(256, 6, 'AC', 58),
(257, 6, 'AC', 61),
(258, 6, 'AC', 62),
(259, 6, 'AC', 63),
(260, 6, 'AC', 65),
(261, 6, 'AC', 83),
(262, 6, 'AC', 84),
(263, 6, 'AC', 131),
(264, 6, 'AC', 132),
(265, 6, 'AC', 136),
(266, 6, 'AC', 137),
(267, 6, 'AC', 134),
(268, 6, 'AC', 133),
(269, 6, 'AC', 86),
(270, 6, 'AC', 85),
(271, 6, 'AC', 167),
(272, 6, 'AC', 149),
(273, 6, 'AC', 145),
(274, 6, 'AC', 138),
(275, 6, 'AC', 150),
(276, 6, 'AC', 151),
(277, 6, 'AC', 152),
(278, 1, 'AC', 48),
(279, 1, 'AC', 49),
(280, 1, 'AC', 50),
(281, 1, 'AC', 51),
(282, 1, 'AC', 59),
(283, 1, 'AC', 60),
(284, 1, 'AC', 64),
(285, 1, 'AC', 66),
(286, 1, 'AC', 69),
(287, 1, 'AC', 71),
(288, 1, 'AC', 72),
(289, 1, 'AC', 74),
(290, 1, 'AC', 75),
(291, 1, 'AC', 76),
(292, 1, 'AC', 77),
(293, 1, 'AC', 80),
(294, 1, 'AC', 81),
(295, 1, 'AC', 98),
(296, 3, 'AC', 1),
(297, 3, 'AC', 2),
(298, 3, 'AC', 3),
(299, 3, 'AC', 5),
(300, 3, 'AC', 6),
(301, 3, 'AC', 10),
(302, 3, 'AC', 11),
(303, 3, 'AC', 12),
(304, 3, 'AC', 13),
(305, 3, 'AC', 14),
(306, 3, 'AC', 15),
(307, 3, 'AC', 16),
(308, 3, 'AC', 17),
(309, 3, 'AC', 18),
(310, 3, 'AC', 19),
(311, 3, 'AC', 20),
(312, 3, 'AC', 21),
(313, 3, 'AC', 22),
(314, 3, 'AC', 23),
(315, 3, 'AC', 24),
(316, 3, 'AC', 25),
(317, 3, 'AC', 26),
(318, 3, 'AC', 28),
(319, 3, 'AC', 30),
(320, 3, 'AC', 31),
(321, 3, 'AC', 161),
(322, 3, 'AC', 162),
(323, 3, 'AC', 163),
(324, 3, 'AC', 164),
(325, 3, 'AC', 165),
(326, 3, 'AC', 166),
(327, 3, 'AC', 34),
(328, 3, 'AC', 87),
(329, 1, 'AC', 86),
(330, 1, 'AC', 85),
(331, 1, 'AC', 130),
(332, 1, 'AC', 129),
(333, 1, 'AC', 127),
(334, 1, 'AC', 128),
(335, 1, 'AC', 121),
(336, 1, 'AC', 119),
(337, 1, 'AC', 117),
(338, 1, 'AC', 115),
(339, 1, 'AC', 106),
(340, 1, 'AC', 105),
(341, 1, 'AC', 104),
(342, 1, 'AC', 102),
(343, 1, 'AC', 103),
(344, 1, 'AC', 101),
(346, 1, 'AC', 99),
(347, 1, 'AC', 171),
(348, 1, 'AC', 170),
(349, 1, 'AC', 169),
(350, 1, 'AC', 156),
(351, 1, 'AC', 155),
(352, 1, 'AC', 154),
(353, 1, 'AC', 153),
(354, 1, 'AC', 152),
(355, 1, 'AC', 151),
(356, 1, 'AC', 150),
(357, 1, 'AC', 168),
(358, 1, 'AC', 167),
(359, 1, 'AC', 157),
(360, 1, 'AC', 149),
(361, 1, 'AC', 148),
(362, 1, 'AC', 147),
(363, 1, 'AC', 146),
(364, 1, 'AC', 145),
(365, 1, 'AC', 144),
(366, 1, 'AC', 141),
(367, 1, 'AC', 140),
(368, 1, 'AC', 139),
(369, 1, 'AC', 138),
(370, 1, 'AC', 137),
(371, 1, 'AC', 136),
(372, 1, 'AC', 134),
(373, 1, 'AC', 133),
(374, 1, 'AC', 132),
(375, 1, 'AC', 131),
(376, 1, 'AC', 84),
(377, 1, 'AC', 83),
(378, 1, 'AC', 65),
(379, 1, 'AC', 63),
(380, 1, 'AC', 62),
(381, 1, 'AC', 61),
(382, 1, 'AC', 58),
(383, 1, 'AC', 52),
(384, 1, 'AC', 67),
(385, 6, 'AC', 99),
(387, 6, 'AC', 101),
(388, 6, 'AC', 102),
(389, 6, 'AC', 172),
(390, 6, 'AC', 103),
(391, 6, 'AC', 104),
(392, 6, 'AC', 105),
(393, 6, 'AC', 106),
(394, 6, 'AC', 115),
(395, 6, 'AC', 117),
(396, 6, 'AC', 119),
(397, 6, 'AC', 121),
(398, 6, 'AC', 127),
(399, 6, 'AC', 128),
(400, 6, 'AC', 129),
(401, 6, 'AC', 130),
(402, 6, 'AC', 173),
(403, 1, 'AC', 182),
(404, 1, 'AC', 181),
(405, 1, 'AC', 180),
(406, 1, 'AC', 179),
(407, 1, 'AC', 178),
(408, 1, 'AC', 177),
(409, 1, 'AC', 176),
(410, 1, 'AC', 175),
(411, 1, 'AC', 174),
(412, 1, 'AC', 173),
(413, 1, 'AC', 186),
(414, 1, 'AC', 185),
(415, 1, 'AC', 184),
(416, 1, 'AC', 183),
(417, 1, 'AC', 190),
(418, 1, 'AC', 191),
(419, 1, 'AC', 192),
(420, 1, 'AC', 193),
(421, 1, 'AC', 194),
(422, 1, 'AC', 195),
(423, 1, 'AC', 196),
(424, 1, 'AC', 197),
(425, 1, 'AC', 39),
(426, 1, 'AC', 158),
(427, 1, 'AC', 202),
(428, 1, 'AC', 201),
(429, 1, 'AC', 200),
(430, 1, 'AC', 199),
(431, 1, 'AC', 215),
(432, 1, 'AC', 214),
(433, 1, 'AC', 213),
(434, 1, 'AC', 211),
(435, 1, 'AC', 212),
(436, 1, 'AC', 210),
(437, 1, 'AC', 209),
(438, 1, 'AC', 208),
(439, 1, 'AC', 207),
(440, 1, 'AC', 206),
(441, 1, 'AC', 205),
(442, 1, 'AC', 204),
(443, 1, 'AC', 203),
(444, 1, 'AC', 172),
(445, 6, 'AC', 174),
(446, 6, 'AC', 175),
(447, 6, 'AC', 176),
(448, 6, 'AC', 177),
(449, 6, 'AC', 178),
(450, 6, 'AC', 179),
(451, 6, 'AC', 180),
(452, 6, 'AC', 181),
(453, 6, 'AC', 182),
(454, 6, 'AC', 226),
(455, 6, 'AC', 184),
(456, 6, 'AC', 185),
(457, 6, 'AC', 186),
(458, 6, 'AC', 183),
(459, 6, 'AC', 187),
(460, 6, 'AC', 188),
(461, 6, 'AC', 189),
(462, 6, 'AC', 190),
(463, 6, 'AC', 198),
(464, 6, 'AC', 197),
(465, 6, 'AC', 196),
(466, 6, 'AC', 195),
(467, 6, 'AC', 194),
(468, 6, 'AC', 193),
(469, 6, 'AC', 192),
(470, 6, 'AC', 191),
(471, 6, 'AC', 214),
(472, 6, 'AC', 215),
(473, 6, 'AC', 213),
(474, 6, 'AC', 212),
(475, 6, 'AC', 211),
(476, 6, 'AC', 210),
(477, 6, 'AC', 209),
(478, 6, 'AC', 208),
(479, 6, 'AC', 207),
(480, 6, 'AC', 206),
(481, 6, 'AC', 205),
(482, 6, 'AC', 204),
(483, 6, 'AC', 203),
(491, 1, 'AC', 232),
(492, 1, 'AC', 233),
(493, 1, 'AC', 234),
(494, 1, 'AC', 235),
(495, 1, 'AC', 236),
(496, 1, 'AC', 237),
(497, 1, 'AC', 238),
(498, 1, 'AC', 198),
(499, 1, 'AC', 187),
(500, 1, 'AC', 188),
(501, 1, 'AC', 189),
(502, 1, 'AC', 53),
(503, 1, 'AC', 54),
(504, 1, 'AC', 55),
(505, 1, 'AC', 56),
(506, 1, 'AC', 218),
(507, 1, 'AC', 223),
(508, 1, 'AC', 224),
(509, 1, 'AC', 225),
(510, 1, 'AC', 227),
(511, 1, 'AC', 228),
(512, 1, 'AC', 229),
(513, 1, 'AC', 230),
(514, 1, 'AC', 231),
(515, 1, 'AC', 88),
(516, 1, 'AC', 183),
(517, 1, 'AC', 184),
(518, 1, 'AC', 185),
(519, 1, 'AC', 186),
(520, 1, 'AC', 226),
(521, 3, 'AC', 173),
(522, 3, 'AC', 174),
(523, 3, 'AC', 175),
(524, 3, 'AC', 176),
(525, 3, 'AC', 177),
(526, 3, 'AC', 178),
(527, 3, 'AC', 179),
(528, 3, 'AC', 181),
(529, 3, 'AC', 182),
(530, 3, 'AC', 180);

-- --------------------------------------------------------

--
-- Table structure for table `Separation`
--

CREATE TABLE `Separation` (
  `separationId` int(11) NOT NULL,
  `employeeId` int(11) NOT NULL,
  `appliedByEmployeeId` int(11) DEFAULT NULL,
  `replacementEmployeeId` int(11) DEFAULT NULL,
  `resoan` varchar(100) NOT NULL,
  `endDate` date NOT NULL,
  `exitDate` date DEFAULT NULL,
  `description` varchar(400) DEFAULT NULL,
  `approvalId` int(11) DEFAULT NULL,
  `status` varchar(3) NOT NULL,
  `remark` varchar(250) DEFAULT NULL,
  `companyId` int(11) NOT NULL,
  `effectiveStartDate` datetime DEFAULT NULL,
  `effectiveEndDate` datetime DEFAULT NULL,
  `activeStatus` varchar(2) DEFAULT NULL,
  `dateCreated` date NOT NULL,
  `dateUpdate` date NOT NULL,
  `userId` int(11) NOT NULL,
  `userIdUpdate` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `Skills`
--

CREATE TABLE `Skills` (
  `skillId` int(11) NOT NULL,
  `skillName` varchar(50) NOT NULL,
  `departmentId` int(11) NOT NULL,
  `effectiveStartDate` datetime DEFAULT NULL,
  `effectiveEndDate` datetime DEFAULT NULL,
  `companyId` int(11) NOT NULL,
  `groupId` int(11) DEFAULT NULL,
  `activeStatus` varchar(2) DEFAULT 'AC',
  `allowModi` char(1) DEFAULT 'N',
  `userId` int(11) NOT NULL,
  `dateCreated` datetime DEFAULT NULL,
  `userIdUpdate` int(11) DEFAULT NULL,
  `dateUpdate` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `State`
--

CREATE TABLE `State` (
  `stateId` int(11) NOT NULL,
  `countryId` int(11) NOT NULL,
  `stateName` varchar(50) NOT NULL,
  `allowModi` char(1) DEFAULT 'N',
  `userId` int(11) NOT NULL,
  `dateCreated` datetime DEFAULT NULL,
  `userIdUpdate` int(11) DEFAULT NULL,
  `dateUpdate` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `State`
--

INSERT INTO `State` (`stateId`, `countryId`, `stateName`, `allowModi`, `userId`, `dateCreated`, `userIdUpdate`, `dateUpdate`) VALUES
(1, 1, 'Madhya Pradesh', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(2, 1, 'Uttar Pradesh', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(4, 1, 'Delhi', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(6, 1, 'Gujarat', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(7, 1, 'Karnataka', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(10, 1, 'Bihar', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(13, 1, 'Goa', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(17, 1, 'Chhattisgarh', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(23, 1, 'Maharashtra', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(27, 1, 'Orissa', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(32, 1, 'West Bengal', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(33, 1, 'Andaman Nicobar', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(34, 1, 'Andhra Pradesh', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(35, 1, 'Arunachal Pradesh', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(36, 1, 'Assam', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(37, 1, 'Dadra  Nagar Haveli', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(38, 1, 'Daman  Diu', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(39, 1, 'Haryana', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(40, 1, 'Himachal Pradesh', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(41, 1, 'Jammu  Kashmir', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(42, 1, 'Jharkhand', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(44, 1, 'Kerala', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(45, 1, 'Manipur', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(46, 1, 'Meghalaya', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(47, 1, 'Mizoram', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(48, 1, 'Nagaland', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(49, 1, 'Pondicherry', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(50, 1, 'Punjab', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(51, 1, 'Rajasthan', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(52, 1, 'Sikkim', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(53, 1, 'Tamil Nadu', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(54, 1, 'Tripura', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(55, 1, 'Uttaranchal', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00'),
(56, 1, 'Telangana', 'N', 1, '2018-03-30 00:00:00', 1, '2018-03-30 00:00:00');

-- --------------------------------------------------------

--
-- Table structure for table `SubmenuActionMaster`
--

CREATE TABLE `SubmenuActionMaster` (
  `submenuActionId` int(11) NOT NULL,
  `submenuId` int(11) DEFAULT NULL,
  `urlPath` varchar(250) NOT NULL,
  `title` varchar(500) NOT NULL,
  `uniqueCode` varchar(50) DEFAULT NULL,
  `status` varchar(10) NOT NULL,
  `description` varchar(500) DEFAULT NULL,
  `userId` int(11) NOT NULL,
  `dateCreated` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `SubmenuActionMaster`
--

INSERT INTO `SubmenuActionMaster` (`submenuActionId`, `submenuId`, `urlPath`, `title`, `uniqueCode`, `status`, `description`, `userId`, `dateCreated`) VALUES
(1, 1, 'http://localhost:8080/hrmsApi/tms/empTicketRaising/employeeId', 'View My Helpdesk', 'ViewMyHelpdesk002', 'AC', NULL, 1, '2019-07-16'),
(2, 1, 'http://localhost:8080/hrmsApi/tms/ticketRaising/emailId', 'Add My Helpdesk', 'AddMyHelpdesk001', 'AC', NULL, 1, '2019-07-16'),
(3, 2, 'http://localhost:8080/tmsApi/calendar?companyId=\'+companyId+\'&employeeId=\'+employeeId+\'&fromDate=\'+ fromDate+\'&toDate=\'+toDate', 'View Calendar', 'ViewCalendar002', 'AC', NULL, 1, '2019-07-16'),
(4, 27, 'http://localhost:8080/hrmsApi/tms/ticketRaising/companyId', 'View Ticket List', 'ViewTicketList001', 'AC', NULL, 1, '2019-07-16'),
(5, 2, 'http://localhost:8080/tmsApi/attendanceregularizationrequest/pending/employeeId', 'View Attendance Regularize Request', 'ViewARRequest002', 'AC', NULL, 1, '2019-07-16'),
(6, 2, 'http://localhost:8080/tmsApi/webAttendance/punchTimeDetail', 'Check In Out', 'CheckInOut001', 'AC', NULL, 1, '2019-07-16'),
(7, 27, 'http://localhost:8080/hrmsApi/employee/employeeInfo/employeeId', 'View Employee Info', 'ViewEmployeeInfo001', 'AC', NULL, 1, '2019-07-16'),
(8, 27, 'http://localhost:8080/hrmsApi/tms/ticketRaisingById/ticketRaisingHDId', 'View Ticket Raising By Id', 'ViewTicketRaisingById001', 'AC', NULL, 1, '2019-07-16'),
(10, 2, 'http://localhost:8080/tmsApi/attendanceregularizationrequest/attendanceRegRequest', 'Add Attendance Regularize Request', 'AddAttendanceRegularize001', 'AC', NULL, 1, '2019-07-16'),
(11, 2, 'http://localhost:8080/tmsApi/attendanceregularizationrequest/attendanceRegRequest', 'Edit Attendance Regularize Request', 'EditAttendanceRegularize003', 'AC', NULL, 1, '2019-07-16'),
(12, 2, ' http://localhost:8080/tmsApi/attendanceregularizationrequest/arRequest', 'Cancel Attendance Regularize Request', 'CancelArRequest005', 'AC', NULL, 1, '2019-07-16'),
(13, 3, 'http://localhost:8080/tmsApi/holidays/findAllHolidayView/leavePeriodId', 'View Holiday', 'ViewHoliday002', 'AC', NULL, 1, '2019-07-16'),
(14, 3, 'http://localhost:8080/tmsApi/leaveApply/employeePendingLeaveEntry/employeeId', 'View Leave Apply', 'ViewLeaveApply002', 'AC', NULL, 1, '2019-07-16'),
(15, 3, 'http://localhost:8080/tmsApi/compensatoryOff/pending/employeeId', 'View Comp Off', 'ViewCompOff002', 'AC', NULL, 1, '2019-07-16'),
(16, 3, 'http://localhost:8080/tmsApi/leaveApply', 'Add Leave Apply', 'AddLeaveApply001', 'AC', NULL, 1, '2019-07-16'),
(17, 3, ' http://localhost:8080/tmsApi/compensatoryOff', 'Add Compensatory Off', 'AddCompensatoryOff001', 'AC', NULL, 1, '2019-07-16'),
(18, 3, 'http://localhost:8080/tmsApi/leaveApply/leaveBalance/employeeId/companyId', 'View Leave Summary', 'ViewLeaveSummary002', 'AC', NULL, 1, '2019-07-16'),
(19, 3, 'http://localhost:8080/tmsApi/leaveApply/leaveBalance/employeeId/companyId', 'View Team Leave Calendar', 'ViewTeamLeaveCalendar002', 'AC', NULL, 1, '2019-07-16'),
(20, 3, 'http://localhost:8080/tmsApi/leaveApply', 'Edit Leave Apply', 'EditLeaveApply003', 'AC', NULL, 1, '2019-07-16'),
(21, 3, 'http://localhost:8080/tmsApi/leaveApply', 'Cancel Leave Apply', 'CancelLeaveApply005', 'AC', NULL, 1, '2019-07-16'),
(22, 3, ' http://localhost:8080/tmsApi/compensatoryOff', 'Edit Compensatory Off', 'EditCompensatoryOff003', 'AC', NULL, 1, '2019-07-16'),
(23, 3, ' http://localhost:8080/tmsApi/compensatoryOff', 'Cancel Compensatory Off', 'CancelCompensatoryOff003', 'AC', NULL, 1, '2019-07-16'),
(24, 6, 'http://localhost:8080/hrmsApi/salaryReport/employeeSalarySlipMonth/companyId/employeeId', 'View My Pay Slip', 'ViewMyPaySlip002', 'AC', NULL, 1, '2019-07-16'),
(25, 6, 'http://localhost:8080/hrmsApi/salaryReport/payoutPdf/empCode/processMonth/companyId', 'Download My Pay Slip', 'DownloadMyPaySlip006', 'AC', NULL, 1, '2019-07-16'),
(26, 7, 'http://localhost:8080/hrmsApi/EmpAssets/empId', 'View Employee Assets', 'ViewEmployeeAssets002', 'AC', NULL, 1, '2019-07-16'),
(27, 27, 'http://localhost:8080/hrmsApi/tms/ticketRaising/emailId/ticketRaisingHd', ' Add Ticket Without File', 'AddTicketWithoutFile001', 'AC', NULL, 1, '2019-07-16'),
(28, 8, 'http://localhost:8080/hrmsApi/loanIssueEmployee?companyId=\' + companyId + \'&employeeId=\' + employeeId', 'View My Loan', 'ViewMyLoan002', 'AC', NULL, 1, '2019-07-16'),
(29, 27, 'http://localhost:8080/hrmsApi/tms/ticketRaisingFile/emailId/formData', ' Add Ticket With File', ' AddTicketWithFile001', 'AC', NULL, 1, '2019-07-16'),
(30, 8, 'http://localhost:8080/hrmsApi/separation/employeeId', 'View Past Resignation Request', 'ViewPastResignationRequest002', 'AC', NULL, 1, '2019-07-16'),
(31, 8, 'http://localhost:8080/hrmsApi//employee/employeeInfo/${employeeId}', 'View Seperated Employee Info', 'ViewSeperatedEmployeeInfo002', 'AC', NULL, 1, '2019-07-16'),
(32, 28, 'http://localhost:8080/hrmsApi/employee/employeeList?companyId=companyId', 'View All Employee List', 'ViewAllEmployeeList002', 'AC', NULL, 1, '2019-07-16'),
(33, 28, 'http://localhost:8080/hrmsApi/EmpAssets/assets/companyId', 'View Company Assets', 'ViewCompanyAssets002', 'AC', NULL, 1, '2019-07-16'),
(34, 10, 'http://localhost:8080/hrmsApi/separation/resignation', 'Add Resignation', 'AddResignation001', 'AC', NULL, 1, '2019-07-16'),
(39, 28, 'http://localhost:8080/hrmsApi/EmpAssets/companyId', 'Add Assets ', 'AddAssets001', 'AC', NULL, 1, '2019-07-16'),
(40, 16, 'http://localhost:8080/tmsApi/attendanceregularizationrequest/ar/companyId/status', 'View Attendance Regularize Request', 'ViewAllPagingARRequest002', 'AC', NULL, 1, '2019-07-16'),
(41, 16, 'http://localhost:8080/tmsApi/attendanceLog/attendanceLogEntity', 'View Attendance Log', 'ViewAttendanceLog002', 'AC', NULL, 1, '2019-07-16'),
(43, 16, 'http://localhost:8080/tmsApi/attendanceLog/attendanceLogs/attendanceDate/searchEmployee', 'View Bulk Attendance Logs', 'ViewBulkAttendanceLogs002', 'AC', NULL, 1, '2019-07-17'),
(44, 16, 'http://localhost:8080/tmsApi/attendanceLog/bulkAttendance/attendanceDate/attendanceStatus', 'Save All Bulk Attendance Logs', 'SaveAllBulkAttendanceLogs001', 'AC', NULL, 1, '2019-07-17'),
(45, 17, 'http://localhost:8080/tmsApi/leaveApply/searchEntity/searchEmployee', 'View Leave Applied', 'ViewLeaveApplied002', 'AC', NULL, 1, '2019-07-17'),
(46, 17, 'http://localhost:8080/tmsApi/compensatoryOff/compOffList/status/searchEntity', 'View All CompOff List', 'ViewAllCompOffList002', 'AC', NULL, 1, '2019-07-17'),
(47, 17, 'http://localhost:8080/tmsApi/leaveApply/leaveBalanceSummary', 'Approve Reject Leave', 'ApproveRejectLeave003', 'AC', NULL, 1, '2019-07-17'),
(48, 33, 'http://localhost:8080/hrmsApi/grade/gradeList/companyId', 'View Grade List', 'ViewGradeList002', 'AC', NULL, 1, '2019-07-18'),
(49, 33, 'http://localhost:8080/hrmsApi/department?companyId=companyId', 'View Department List', 'ViewDepartmentList002', 'AC', NULL, 1, '2019-07-18'),
(50, 33, 'http://localhost:8080/hrmsApi/designation/companyId', 'View Designation List', 'ViewDesignationList002', 'AC', NULL, 1, '2019-07-18'),
(51, 33, 'http://localhost:8080/hrmsApi/branch/findAll/companyId', 'View All Branch', 'ViewAllBranch002', 'AC', NULL, 1, '2019-07-18'),
(52, 34, 'http://localhost:8080/hrmsApi/mandatoryInfoDd?companyId=companyId', 'View Mandatory Info List', 'ViewMandatoryInfoList002', 'AC', NULL, 1, '2019-07-18'),
(53, 11, 'http://localhost:8080/hrmsApi/empCountByDesignationWise/companyId', 'OverView Designation wise ', 'ViewDesignationwise002', 'AC', NULL, 1, '2019-07-18'),
(54, 11, 'http://localhost:8080/hrmsApi/empCountByDepartmentWise/companyId', 'OverView Department wise ', 'ViewDepartmentwise002', 'AC', NULL, 1, '2019-07-18'),
(55, 11, 'http://localhost:8080/hrmsApi/empGenderWiseRatio/companyId', 'OverView Gender wise ', 'ViewGenderwise002', 'AC', NULL, 1, '2019-07-18'),
(56, 11, 'http://localhost:8080/hrmsApi/empAgeWiseRatio/companyId', 'OverView Age wise ', 'ViewAgewise002', 'AC', NULL, 1, '2019-07-18'),
(58, 34, 'http://localhost:8080/hrmsApi/mandatoryInfo', 'Add Mandatory Info ', 'AddMandatoryInfo001', 'AC', NULL, 1, '2019-07-18'),
(59, 33, 'http://localhost:8080/hrmsApi/company/companyId', 'View Company Info', 'ViewCompanyInfo002', 'AC', NULL, 1, '2019-07-18'),
(60, 33, 'http://localhost:8080/hrmsApi/branch/branch', 'Enable Disable Branch', 'EnableDisableBranch003', 'AC', NULL, 1, '2019-07-18'),
(61, 35, 'http://localhost:8080/hrmsApi/epf/companyId', 'View Epf ', 'ViewEpf002', 'AC', NULL, 1, '2019-07-18'),
(62, 35, 'http://localhost:8080/hrmsApi/gratuity/companyId', 'View Gratuity   ', 'ViewGratuity002', 'AC', NULL, 1, '2019-07-18'),
(63, 35, 'http://localhost:8080/hrmsApi/esic/companyId', 'View Esic', 'ViewEsic002', 'AC', NULL, 1, '2019-07-18'),
(64, 33, 'http://localhost:8080/hrmsApi/grade/grade', 'Enable Disable Grade', 'EnableDisableGrade003', 'AC', NULL, 1, '2019-07-18'),
(65, 35, 'http://localhost:8080/hrmsApi/epf/epf', 'Edit Epf ', 'EditEpf003', 'AC', NULL, 1, '2019-07-18'),
(66, 33, 'http://localhost:8080/hrmsApi/department/department', 'Enable Disable Department', 'EnableDisableDepartment003', 'AC', NULL, 1, '2019-07-19'),
(67, 33, 'http://localhost:8080/hrmsApi/designation/designationStatusUpdate/designation', 'Enable Disable Designation', 'EnableDisableDesignation003', 'AC', NULL, 1, '2019-07-19'),
(69, 33, 'http://localhost:8080/hrmsApi/company/file/formData', ' Edit Company Info ', 'EditCompanyInfoWithFile003', 'AC', NULL, 1, '2019-07-19'),
(71, 33, 'http://localhost:8080/hrmsApi/branch/branch', 'Edit Branch', 'EditBranch003', 'AC', NULL, 1, '2019-07-19'),
(72, 33, 'http://localhost:8080/hrmsApi/branch/branch', 'Save Branch', 'SaveBranch001', 'AC', NULL, 1, '2019-07-19'),
(74, 33, 'http://localhost:8080/hrmsApi/grade/grade', 'Save Grade', 'SaveGrade001', 'AC', NULL, 1, '2019-07-19'),
(75, 33, 'http://localhost:8080/hrmsApi/grade/grade', 'Edit Grade', 'EditGrade003', 'AC', NULL, 1, '2019-07-19'),
(76, 33, 'http://localhost:8080/hrmsApi/department/department', 'Save Department', 'saveDepartment001', 'AC', NULL, 1, '2019-07-19'),
(77, 33, 'http://localhost:8080/hrmsApi/department/department', 'Edit Department', 'EditDepartment003', 'AC', NULL, 1, '2019-07-19'),
(80, 33, 'http://localhost:8080/hrmsApi/designation/designationSave/designation', 'Save Designation', 'saveDesignation001', 'AC', NULL, 1, '2019-07-19'),
(81, 33, 'http://localhost:8080/hrmsApi/designation/designationStatusUpdate/designation', ' Edit Designation', 'EditDesignation003', 'AC', NULL, 1, '2019-07-19'),
(83, 35, 'http://localhost:8080/hrmsApi/esic/esic', 'Edit Esic ', 'EditEsic003', 'AC', NULL, 1, '2019-07-19'),
(84, 35, 'http://localhost:8080/hrmsApi/gratuity/gratuaty', ' Edit Gratuaty  ', 'EditGratuaty003', 'AC', NULL, 1, '2019-07-19'),
(85, 45, 'http://localhost:8080/hrmsApi/professionalTax/findById/companyId', 'View ProfessionalTaxList', 'ViewProfessionalTaxList002', 'AC', NULL, 1, '2019-07-20'),
(86, 45, 'http://localhost:8080/hrmsApi/professionalTax/proTax', 'Add Professional Tax ', 'AddProfessionalTax001', 'AC', NULL, 1, '2019-07-20'),
(87, 46, '', 'View MyFabHr Menu', 'ViewMyFabHrMenu002', 'AC', NULL, 1, '2019-07-20'),
(88, 47, '', 'View People Menu', 'ViewPeopleMenu002', 'AC', NULL, 1, '2019-07-20'),
(89, 48, '', 'View Time & Attendance Menu', 'ViewTimeAttendanceMenu002', 'AC', NULL, 1, '2019-07-20'),
(90, 49, '', 'View Payroll Menu', 'ViewPayrollMenu002', 'AC', NULL, 1, '2019-07-20'),
(95, 27, '', 'View Helpdesk Menu', 'ViewHelpdeskMenu002', 'AC', NULL, 1, '2019-07-20'),
(96, 28, '', 'View Assets Menu', 'ViewAssetsMenu002', 'AC', NULL, 1, '2019-07-20'),
(97, 51, '', 'View Reports Menu', 'ViewReportsMenu002', 'AC', NULL, 1, '2019-07-20'),
(98, 50, '', 'View Settings Menu', 'ViewSettingsMenu002', 'AC', NULL, 1, '2019-07-20'),
(99, 39, 'http://localhost:8080/hrmsApi/payhead/payHeadList?companyId=companyId', 'View PayHead   ', 'ViewPayHeadList002', 'AC', NULL, 1, '2019-07-20'),
(101, 39, 'http://localhost:8080/hrmsApi/payhead/payHead', 'Edit PayHead ', 'EditPayHead003', 'AC', NULL, 1, '2019-07-20'),
(102, 39, 'http://localhost:8080/hrmsApi/payhead/payHead', 'Enable Disable PayHead ', 'EnableDisablePayhead003', 'AC', NULL, 1, '2019-07-20'),
(103, 40, 'http://localhost:8080/hrmsApi/payroll/financialYearList/companyId', 'View Payroll Period', 'ViewFinancialYearList002', 'AC', NULL, 1, '2019-07-20'),
(104, 40, 'http://localhost:8080/hrmsApi/payroll/financialYearStatusUpdate/payrollPeriod', 'Enable Disable Payroll Period', 'EnableDisablePeriod003', 'AC', NULL, 1, '2019-07-20'),
(105, 40, 'http://localhost:8080/hrmsApi/payroll/payrollPeriod/payrollPeriod', 'Add PayrollPeriod ', 'AddPayrollPeriod001', 'AC', NULL, 1, '2019-07-20'),
(106, 40, 'http://localhost:8080/hrmsApi/payroll/payrollPeriod/payrollPeriod', 'Edit PayrollPeriod ', 'EditPayrollPeriod003', 'AC', NULL, 1, '2019-07-20'),
(107, 52, 'http://localhost:8080/hrmsApi/empDashboardInfo?companyId=\'+companyId', 'View Birthday', 'ViewBirthday002', 'AC', NULL, 1, '2019-07-20'),
(108, 52, 'http://localhost:8080/hrmsApi/empDashboardInfo?companyId=\'+companyId', 'View Anniversary', 'ViewAnniversary002', 'AC', NULL, 1, '2019-07-20'),
(109, 52, 'http://localhost:8080/hrmsApi/empDashboardInfo?companyId=\'+companyId', 'View Holiday', 'ViewHoliday003', 'AC', NULL, 1, '2019-07-20'),
(110, 52, 'http://localhost:8080/hrmsApi/employee/myProfileInfo/userName', 'View Employee Info Card', 'ViewEmployeeInfo002', 'AC', NULL, 1, '2019-07-20'),
(111, 52, 'http://localhost:8080/hrmsApi/empCompanyAnnouncement?companyId=\' + companyId', 'View Announcements', 'ViewAnnouncements002', 'AC', NULL, 1, '2019-07-20'),
(112, 52, 'http://localhost:8080/hrmsApi/interestingThoughts/getAllInterestingThought/companyId', 'View Interesting Thoughts', 'ViewInterestingThoughts002', 'AC', NULL, 1, '2019-07-20'),
(113, 52, 'http://localhost:8080/hrmsApi//todoList/getAllTodoPandingList/', 'View To Do List', 'ViewToDoList002', 'AC', NULL, 1, '2019-07-20'),
(114, 52, 'http://localhost:8080/hrmsApi//leaveApply/countMyTeam/companyId/employeeId', 'View My Pending Request', 'ViewMyPendingRequest002', 'AC', NULL, 1, '2019-07-20'),
(115, 41, 'http://localhost:8080/hrmsApi/tms/ticketType/companyId', 'View TicketTypeList ', 'ViewTicketTypeList002', 'AC', NULL, 1, '2019-07-20'),
(116, 52, 'http://localhost:8080/hrmsApi/getMyTeamMonthCount/companyId/employeeId', 'View My Attendance This Month', 'ViewMyAttendanceThisMonth002', 'AC', NULL, 1, '2019-07-20'),
(117, 41, 'http://localhost:8080/hrmsApi/tms/ticketType/ticketType', 'Add TicketType ', 'AddTicketType001', 'AC', NULL, 1, '2019-07-20'),
(118, 52, 'http://localhost:8080/hrmsApi/getMyTeamMonthCount/companyId/employeeId', 'View My Team on Leave', 'ViewMyTeamonLeave002', 'AC', NULL, 1, '2019-07-20'),
(119, 41, 'http://localhost:8080/hrmsApi/tms/delete/ticketTypeId', 'Delete TicketType', 'DeleteTicketType004', 'AC', NULL, 1, '2019-07-20'),
(120, 52, 'http://localhost:8080/hrmsApi/getMyTeamMonthCount/companyId/employeeId', 'View My Team Attendance Today', 'ViewMyTeamAttendanceToday002', 'AC', NULL, 1, '2019-07-20'),
(121, 41, 'http://localhost:8080/hrmsApi/tms/ticketType/ticketType', 'Edit TicketType ', 'EditTicketType003', 'AC', NULL, 1, '2019-07-20'),
(122, 52, 'http://localhost:8080/hrmsApi/employeeDocumentConfirmationInfo?companyId', 'View Documents Dues', 'ViewDocumentsDues002', 'AC', NULL, 1, '2019-07-20'),
(123, 52, 'http://localhost:8080/hrmsApi/empAttrition?companyId=\'+companyId', 'View Employees Addition & Attrition', 'ViewEmployeesAddition&Attrition002', 'AC', NULL, 1, '2019-07-20'),
(124, 52, 'http://localhost:8080/hrmsApi/empDashboardInfo?companyId=\'+companyId', 'View Onboard', 'ViewOnboard002', 'AC', NULL, 1, '2019-07-20'),
(125, 52, 'http://localhost:8080/hrmsApi/empDashboardInfo?companyId=\'+companyId', 'View Seperated', 'ViewSeperated002', 'AC', NULL, 1, '2019-07-20'),
(126, 52, 'http://localhost:8080/hrmsApi/empDashboardInfo?companyId=\'+companyId', 'View Total footer', 'ViewTotal002', 'AC', NULL, 1, '2019-07-20'),
(127, 42, 'http://localhost:8080/hrmsApi/items/companyId', 'View Asset ItemList ', 'ViewAssetItemList002', 'AC', NULL, 1, '2019-07-22'),
(128, 42, 'http://localhost:8080/hrmsApi/items/item', 'Add Asset Item ', 'AddAssetItem001', 'AC', NULL, 1, '2019-07-22'),
(129, 42, 'http://localhost:8080/hrmsApi/items/deleteItems/itemId', 'Delete Asset Item ', 'DeleteAssetItem004', 'AC', NULL, 1, '2019-07-22'),
(130, 42, 'http://localhost:8080/hrmsApi/items/item', 'Edit Asset Item ', 'EditAssetItem003', 'AC', NULL, 1, '2019-07-22'),
(131, 36, 'http://localhost:8080/hrmsApi/tdsSlabHd/findByFinencailYear/companyId/financialYearId', 'View Tds Slab List', 'ViewTdsSlabList002', 'AC', NULL, 1, '2019-07-23'),
(132, 36, 'http://localhost:8080/hrmsApi/tdsSectionSetup/findByFinanicalYearId/companyId/financialYearId', 'View Tds Group List', 'ViewTdsGroupList002', 'AC', NULL, 1, '2019-07-23'),
(133, 36, 'http://localhost:8080/hrmsApi/tdsSlab/saveTdsSlabHd/tdsSlabList', 'Add Tds Slab', 'AddTdsSlab001', 'AC', NULL, 1, '2019-07-23'),
(134, 36, 'http://localhost:8080/hrmsApi/tdsSlab/updateByStatus/tdsSlab', 'delete Tds Slab Row', 'deleteTdsSlabRow004', 'AC', NULL, 1, '2019-07-23'),
(136, 36, 'http://localhost:8080/hrmsApi/tdsSectionSetup/saveTdsSection/exemptionAndDeduction', 'Save Exemption And Deduction', 'SaveExemptionAndDeduction001', 'AC', NULL, 1, '2019-07-23'),
(137, 36, 'http://localhost:8080/hrmsApi/tdsSectionSetup/updateByStatus/exemptionAndDeduction', 'delete Exemption And Deduction Row', 'deleteExemptionAndDeductionRow004', 'AC', NULL, 1, '2019-07-23'),
(138, 37, 'http://localhost:8080/tmsApi/leaveTypeMaster/companyId', 'View leave Type Master', 'ViewleaveTypeMaster002', 'AC', NULL, 1, '2019-07-23'),
(139, 37, 'http://localhost:8080/tmsApi/leaveType/save/leavePeriodId/leaveType', ' Enable Disable Leave', 'EnableDisableLeave003', 'AC', NULL, 1, '2019-07-23'),
(140, 37, 'http://localhost:8080/tmsApi/holidays/leavePeriodId/holiday', 'Enable Disable holidays', 'EnableDisableholidays003', 'AC', NULL, 1, '2019-07-23'),
(141, 37, 'http://localhost:8080/tmsApi/leaveRules/save/absenteeismrules', 'Enable Disable Absent Rules', 'EnableDisableAbsentRules003', 'AC', NULL, 1, '2019-07-23'),
(144, 37, 'http://localhost:8080/tmsApi/leaveType/save/leavePeriodId/leaveType', 'Edit Leave ', 'EditRowLeaveList003', 'AC', NULL, 1, '2019-07-23'),
(145, 37, 'http://localhost:8080/tmsApi/leaveType/findByLeavePeroid/leavePeriodId', 'View Leave Period', 'ViewLeaveTypeByLeavePeriodId002', 'AC', NULL, 1, '2019-07-23'),
(146, 37, 'http://localhost:8080/tmsApi/leaveType/save/leavePeriodId/leaveType', 'Add Leave', 'AddLeave001', 'AC', NULL, 1, '2019-07-23'),
(147, 37, ' http://localhost:8080/tmsApi/holidays/holiday', 'Add Holidays', 'AddHolidays001', 'AC', NULL, 1, '2019-07-23'),
(148, 37, 'http://localhost:8080/tmsApi/holidays/leavePeriodId/holiday', 'Edit Holidays', 'EditHolidays003', 'AC', NULL, 1, '2019-07-23'),
(149, 37, 'http://localhost:8080/tmsApi/holidays/findAllHolydays/leavePeriodId', 'View All Holidays', 'ViewAllHolidays002', 'AC', NULL, 1, '2019-07-23'),
(150, 38, 'http://localhost:8080/tmsApi/rules/halfday/companyId', 'View Half Day', 'ViewHalfDay002', 'AC', NULL, 1, '2019-07-24'),
(151, 38, 'http://localhost:8080/tmsApi/weekoffpattern/weekOff/companyId', ' View Weekly Off List', 'ViewWeeklyOffList002', 'AC', NULL, 1, '2019-07-24'),
(152, 38, 'http://localhost:8080/tmsApi/shift/companyId', 'View Shift List', 'ViewShiftList002', 'AC', NULL, 1, '2019-07-24'),
(153, 38, 'http://localhost:8080/tmsApi/shift/shift', 'Enable Disable Shift', 'EnableDisableShift003', 'AC', NULL, 1, '2019-07-24'),
(154, 38, 'http://localhost:8080/tmsApi/weekoffpattern/weeklyOff', 'Enable Disable weekoffpattern', 'EnableDisableweekoffpattern003', 'AC', NULL, 1, '2019-07-24'),
(155, 38, 'http://localhost:8080/tmsApi/shift/shift', 'Add Shift', 'AddShift001', 'AC', NULL, 1, '2019-07-24'),
(156, 38, 'http://localhost:8080/tmsApi/weekoffpattern/weeklyOff', 'Add weekoffpattern', 'Addweekoffpattern001', 'AC', NULL, 1, '2019-07-24'),
(157, 37, 'http://localhost:8080/tmsApi/rules/halfday/halfday', 'Add Halfday', 'AddHalfday001', 'AC', NULL, 1, '2019-07-24'),
(158, 28, 'http://localhost:8080/hrmsApi/EmpAssets/assetsStatusUpdate/employeeAssetsId', 'Edit Employee Assets', 'EditEmployeeAssets003', 'AC', NULL, 1, '2019-07-24'),
(159, 16, 'http://localhost:8080/tmsApi/attendanceregularizationrequest', 'Approve Reject AR Request', 'ApproveRejectARRequest001', 'AC', NULL, 1, '2019-07-24'),
(160, 17, 'http://localhost:8080/tmsApi/compensatoryOff', 'Approve Reject CompOff', 'ApproveRejectCompOff001', 'AC', NULL, 1, '2019-07-24'),
(161, 9, 'http://localhost:8080/tmsApi/leaveApply/approvalsPending/companyId/employeeId', 'View Approvals Leave Request', 'ViewApprovalsLeaveRequest002', 'AC', NULL, 1, '2019-07-29'),
(162, 9, ' http://localhost:8080/tmsApi/compensatoryOff/approvalsPending/companyId/employeeId', 'View Approvals Compensatory Off', 'ViewApprovalsCompOff002', 'AC', NULL, 1, '2019-07-29'),
(163, 9, 'http://localhost:8080/tmsApi/attendanceregularizationrequest/approvalsPending/companyId/employeeId', 'View Approvals Attendance Regularize', 'ViewApprovalsAR002', 'AC', NULL, 1, '2019-07-29'),
(164, 9, 'http://localhost:8080/tmsApi/attendanceregularizationrequest', 'Approve Reject Approvals Leave', 'ApproveRejectApprovalsLeave001', 'AC', NULL, 1, '2019-07-29'),
(165, 9, 'http://localhost:8080/tmsApi/compensatoryOff', 'Approve Reject Approvals CompOff', 'ApproveRejectApprovalsCompOff001', 'AC', NULL, 1, '2019-07-29'),
(166, 9, 'http://localhost:8080/tmsApi/attendanceregularizationrequest', 'Approve Reject Approvals Attendance Regularize', 'ApproveRejectApprovalsAR001', 'AC', NULL, 1, '2019-07-29'),
(167, 37, 'http://localhost:8080/tmsApi/leaveRules/leavePeriodId', 'View Absenteeism', 'ViewAbsenteeism003', 'AC', NULL, 1, '2019-07-30'),
(168, 37, 'http://localhost:8080/tmsApi/leavePeriod/companyId', 'View Previous Session', 'ViewPreviousSession002', 'AC', NULL, 1, '2019-07-30'),
(169, 38, 'http:/localhost:8080/tmsApi/shift/shift', 'Edit Shift', 'EditShift003', 'AC', NULL, 1, '2019-07-30'),
(170, 38, 'http://localhost:8080/tmsApi/weekoffpattern/weekly...', 'Edit WeeklyOff', 'EditWeeklyOff003', 'AC', NULL, 1, '2019-07-30'),
(171, 38, 'http://localhost:8080/tmsApi/rules/halfday/halfday', 'Edit HalfDay', 'EditHalfDay003', 'AC', NULL, 1, '2019-07-30'),
(172, 39, 'http://localhost:8080/hrmsApi/payhead/payHeadByID/payHead', 'Add PayHead', 'AddPayHead001', 'AC', NULL, 1, '2019-07-31'),
(173, 46, '', 'View Helpdesk Submenu', 'ViewHelpdeskSubmenu002', 'AC', NULL, 1, '2019-07-31'),
(174, 46, '', 'View My Attendance Submenu', 'ViewMyAttendanceSubmenu002', 'AC', NULL, 1, '2019-07-31'),
(175, 46, '', 'View My Leave Submenu', 'ViewMyLeaveSubmenu002', 'AC', NULL, 1, '2019-07-31'),
(176, 46, '', 'View My Team Submenu', 'ViewMyTeamSubmenu002', 'AC', NULL, 1, '2019-07-31'),
(177, 46, '', 'View Income Tax Submenu', 'ViewIncomeTaxSubmenu002', 'AC', NULL, 1, '2019-07-31'),
(178, 46, '', 'View Pay Slips Submenu', 'ViewPayslipsSubmenu002', 'AC', NULL, 1, '2019-07-31'),
(179, 46, '', 'View My Assets Submenu', 'ViewMyAssetsSubmenu002', 'AC', NULL, 1, '2019-07-31'),
(180, 46, '', 'View My Loan Submenu', 'ViewMyLoanSubmenu002', 'AC', NULL, 1, '2019-07-31'),
(181, 46, '', 'View Approvals Submenu', 'ViewApprovalsSubmenu002', 'AC', NULL, 1, '2019-07-31'),
(182, 46, '', 'View Resign Submenu', 'ViewResignSubmenu002', 'AC', NULL, 1, '2019-07-31'),
(183, 47, '', 'View People Overview Submenu', 'ViewPeopleOverviewSubmenu002', 'AC', NULL, 1, '2019-07-31'),
(184, 47, '', 'View Directory Submenu', 'ViewDirectorySubmenu002', 'AC', NULL, 1, '2019-07-31'),
(185, 47, '', 'View Company Announcement Submenu', 'ViewCompanyAnnouncementSubmenu002', 'AC', NULL, 1, '2019-07-31'),
(186, 47, '', 'View Manage Roles and Permissions Submenu', 'ViewManageRolesSubmenu002', 'AC', NULL, 1, '2019-07-31'),
(187, 48, '', 'View Time & Attendance Overview Submenu', 'ViewT&AOverviewSubmenu002', 'AC', NULL, 1, '2019-07-31'),
(188, 48, '', 'View Attendance Submenu', 'ViewAttendanceSubmenu002', 'AC', NULL, 1, '2019-07-31'),
(189, 48, '', 'View Leave Submenu', 'ViewLeaveSubmenu002', 'AC', NULL, 1, '2019-07-31'),
(190, 49, '', 'View Payroll Overview Submenu', 'ViewPayrollOverviewSubmenu002', 'AC', NULL, 1, '2019-07-31'),
(191, 49, '', 'View Salary Structure Submenu', 'ViewSalaryStructureSubmenu002', 'AC', NULL, 1, '2019-07-31'),
(192, 49, '', 'View Payroll Inputs Submenu', 'ViewPayrollInputsSubmenu002', 'AC', NULL, 1, '2019-07-31'),
(193, 49, '', 'View Final Settlement Submenu', 'ViewFinalSettlementSubmenu002', 'AC', NULL, 1, '2019-07-31'),
(194, 49, '', 'View Income Tax Submenu', 'ViewPayrollIncometaxSubmenu002', 'AC', NULL, 1, '2019-07-31'),
(195, 49, '', 'View Salary Slips Submenu', 'ViewSalarySlipsSubmenu002', 'AC', NULL, 1, '2019-07-31'),
(196, 49, '', 'View Payment Transfer Statement Submenu', 'ViewPTSSubmenu002', 'AC', NULL, 1, '2019-07-31'),
(197, 49, '', 'View Record Payment Submenu', 'ViewRecordPaymentSubmenu002', 'AC', NULL, 1, '2019-07-31'),
(198, 49, '', 'View Run Payroll Submenu', 'ViewRunPayrollSubmenu002', 'AC', NULL, 1, '2019-07-31'),
(199, 29, '', 'View People Reports', 'ViewPeopleReports002', 'AC', NULL, 1, '2019-07-31'),
(200, 30, '', 'View Time & Attendance Reports', 'ViewT&AReports002', 'AC', NULL, 1, '2019-07-31'),
(201, 31, '', 'View Payroll & Statutory Compliance Reports', 'ViewPayrollStatutoryReports002', 'AC', NULL, 1, '2019-07-31'),
(202, 32, '', 'View Other Miscellaneous Reports', 'ViewOtherReports002', 'AC', NULL, 1, '2019-07-31'),
(203, 50, '', 'View Organization Submenu', 'ViewOrganizationSubmenu002', 'AC', NULL, 1, '2019-07-31'),
(204, 50, '', 'View Document Submenu', 'ViewDocumentSubmenu002', 'AC', NULL, 1, '2019-07-31'),
(205, 50, '', 'View Statutory Submenu', 'ViewStatutorySubmenu002', 'AC', NULL, 1, '2019-07-31'),
(206, 50, '', 'View Taxes Submenu', 'ViewTaxesSubmenu002', 'AC', NULL, 1, '2019-07-31'),
(207, 50, '', 'View Professional Tax Submenu', 'ViewProfessionalTaxeSubmenu002', 'AC', NULL, 1, '2019-07-31'),
(208, 50, '', 'View Setting Leave Submenu', 'ViewSettingLeaveSubmenu002', 'AC', NULL, 1, '2019-07-31'),
(209, 50, '', 'View Setting Attendance Submenu', 'ViewSettingAttendanceSubmenu002', 'AC', NULL, 1, '2019-07-31'),
(210, 50, '', 'View Pay Heads Submenu', 'ViewPayHeadsSubmenu002', 'AC', NULL, 1, '2019-07-31'),
(211, 50, '', 'View Payroll Period Submenu', 'ViewPayrollPeriodSubmenu002', 'AC', NULL, 1, '2019-07-31'),
(212, 50, '', 'View Setting Helpdesk Submenu', 'ViewSettingHelpdeskSubmenu002', 'AC', NULL, 1, '2019-07-31'),
(213, 50, '', 'View Setting Assets Submenu', 'ViewSettingAssetsSubmenu002', 'AC', NULL, 1, '2019-07-31'),
(214, 50, '', 'View Roles & Permissions Submenu', 'ViewRolesPermissionsSubmenu002', 'AC', NULL, 1, '2019-07-31'),
(215, 50, '', 'View Developer Submenu', 'ViewDeveloperSubmenu002', 'AC', NULL, 1, '2019-07-31'),
(218, 12, 'http://localhost:8080/hrmsApi/employee/allEmployees', 'Edit Employee Onboard', 'EditOnboardEmployee003', 'AC', NULL, 1, '2019-07-31'),
(223, 12, 'http://localhost:8080/hrmsApi/candidate/', 'Add New Joinee', 'AddNewJoinee001', 'AC', NULL, 1, '2019-07-31'),
(224, 12, 'http://localhost:8080/hrmsApi/bulkInfoUpload', 'Add Bulk Upload', 'AddBulkUpload001', 'AC', NULL, 1, '2019-07-31'),
(225, 12, 'http://localhost:8080/hrmsApi/candidate/', 'Edit Candidate', 'EditCandidate002', 'AC', NULL, 1, '2019-07-31'),
(226, 47, '', 'View Separation Submenu', 'ViewSeparationSubmenu002', 'AC', NULL, 1, '2019-08-01'),
(227, 13, 'http://localhost:8080/hrmsApi/massCommunication', 'Add Company Announcement', 'AddCompanyAnnouncement001', 'AC', NULL, 1, '2019-08-01'),
(228, 13, 'http://localhost:8080/hrmsApi/empCompanyAnnouncement?companyId=companyId', 'View Company Announcement', 'ViewCompanyAnnouncement002', 'AC', NULL, 1, '2019-08-01'),
(229, 13, 'http://localhost:8080/hrmsApi/massCommunication', 'Edit Company Announcement', 'EditCompanyAnnouncement003', 'AC', NULL, 1, '2019-08-01'),
(230, 14, 'http://localhost:8080/hrmsApi/separation/status', 'View Separation', 'ViewSeparation002', 'AC', NULL, 1, '2019-08-01'),
(231, 14, 'http://localhost:8080/hrmsApi/separation/resignation', 'Approve Reject Separation Request', 'ApproveRejectSeparationRequest001', 'AC', NULL, 1, '2019-08-01'),
(232, 18, 'http://localhost:8080/hrmsApi/departmentWiseCTCInfoWithMonth/processMonth/companyId', 'Overview Department Wise CTC', 'ViewDepartmentWiseCTC002', 'AC', NULL, 1, '2019-08-01'),
(233, 18, 'http://localhost:8080/hrmsApi/designationWiseCTCInfoWithMonth/companyId/processMonth', 'Overview Designation Wise CTC', 'ViewDesignationWiseCTC002', 'AC', NULL, 1, '2019-08-01'),
(234, 18, 'http://localhost:8080/hrmsApi/getLastTwelveMonthSalary/companyId', 'Overview Last Twelve Month Salary', 'ViewLastTwelveMonthSalary002', 'AC', NULL, 1, '2019-08-01'),
(235, 18, 'http://localhost:8080/hrmsApi/empPFContributionInfo/companyId/processMonth', 'Overview PF Contribution', 'ViewPFContribution002', 'AC', NULL, 1, '2019-08-01'),
(236, 18, 'http://localhost:8080/hrmsApi/empESIContributionInfoWithMonth/companyId/processMonth', 'Overview ESI Contribution', 'ViewESIContribution002', 'AC', NULL, 1, '2019-08-01'),
(237, 26, 'http://localhost:8080/hrmsApi/rollBackPayroll/', 'RollBack Payroll', 'AddRollBackPayroll001', 'AC', NULL, 1, '2019-08-01'),
(238, 26, 'http://localhost:8080/hrmsApi/rollBackPayroll/', 'Process Attendance', 'ProcessAttendance001', 'AC', NULL, 1, '2019-08-01');

-- --------------------------------------------------------

--
-- Table structure for table `SubMenuMaster`
--

CREATE TABLE `SubMenuMaster` (
  `subMenuId` int(11) NOT NULL,
  `menuId` int(11) NOT NULL,
  `submenuName` varchar(50) NOT NULL,
  `status` varchar(11) DEFAULT NULL,
  `userId` int(11) NOT NULL,
  `dateCreated` date NOT NULL,
  `userIdUpdate` int(11) DEFAULT NULL,
  `dateUpdate` date DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `SubMenuMaster`
--

INSERT INTO `SubMenuMaster` (`subMenuId`, `menuId`, `submenuName`, `status`, `userId`, `dateCreated`, `userIdUpdate`, `dateUpdate`) VALUES
(1, 1, 'Hepldesk', 'AC', 1, '2019-07-03', 1, '2019-07-05'),
(2, 1, 'My Attendance', 'AC', 1, '2019-07-16', 1, '2019-07-16'),
(3, 1, 'My Leave', 'AC', 1, '2019-07-03', 1, '2019-07-05'),
(4, 1, 'My Team', 'AC', 1, '2019-07-03', 1, '2019-07-05'),
(5, 1, 'Income Tax', 'AC', 1, '2019-07-16', 1, '2019-07-16'),
(6, 1, 'Pay Slips', 'AC', 1, '2019-07-03', 1, '2019-07-05'),
(7, 1, 'Assets', 'AC', 1, '2019-07-16', 1, '2019-07-16'),
(8, 1, 'Loan', 'AC', 1, '2019-07-03', 1, '2019-07-05'),
(9, 1, 'Approvals', 'AC', 1, '2019-07-16', 1, '2019-07-16'),
(10, 1, 'Resign', 'AC', 1, '2019-07-03', 1, '2019-07-05'),
(11, 2, 'Overview', 'AC', 1, '2019-07-03', 1, '2019-07-05'),
(12, 2, 'Directory', 'AC', 1, '2019-07-16', 1, '2019-07-16'),
(13, 2, 'Company Announcement', 'AC', 1, '2019-07-03', 1, '2019-07-05'),
(14, 2, 'Separation', 'AC', 1, '2019-07-16', 1, '2019-07-16'),
(15, 2, 'Manage Roles and Permissions', 'AC', 1, '2019-07-03', 1, '2019-07-05'),
(16, 3, 'Attendance', 'AC', 1, '2019-07-03', 1, '2019-07-05'),
(17, 3, 'Leave', 'AC', 1, '2019-07-16', 1, '2019-07-16'),
(18, 4, 'Overview', 'AC', 1, '2019-07-03', 1, '2019-07-05'),
(19, 4, 'Salary Structure', 'AC', 1, '2019-07-16', 1, '2019-07-16'),
(20, 4, 'Payroll Inputs', 'AC', 1, '2019-07-03', 1, '2019-07-05'),
(21, 4, 'Final Settlement', 'AC', 1, '2019-07-16', 1, '2019-07-16'),
(22, 4, 'Income Tax', 'AC', 1, '2019-07-03', 1, '2019-07-05'),
(23, 4, 'SalarySlips', 'AC', 1, '2019-07-16', 1, '2019-07-16'),
(24, 4, 'Payment Transfer Statement', 'AC', 1, '2019-07-03', 1, '2019-07-05'),
(25, 4, 'Record Payment', 'AC', 1, '2019-07-16', 1, '2019-07-16'),
(26, 4, 'Run Payroll', 'AC', 1, '2019-07-03', 1, '2019-07-05'),
(27, 5, 'Helpdesk', 'AC', 1, '2019-07-03', 1, '2019-07-05'),
(28, 6, 'Assets', 'AC', 1, '2019-07-03', 1, '2019-07-05'),
(29, 7, 'People', 'AC', 1, '2019-07-03', 1, '2019-07-05'),
(30, 7, 'Time & Attendance', 'AC', 1, '2019-07-16', 1, '2019-07-16'),
(31, 7, 'Payroll & Statutory Compliance', 'AC', 1, '2019-07-03', 1, '2019-07-05'),
(32, 7, 'Other Miscellaneous', 'AC', 1, '2019-07-16', 1, '2019-07-16'),
(33, 8, 'Organization', 'AC', 1, '2019-07-03', 1, '2019-07-05'),
(34, 8, 'Document', 'AC', 1, '2019-07-16', 1, '2019-07-16'),
(35, 8, 'Statutory', 'AC', 1, '2019-07-03', 1, '2019-07-05'),
(36, 8, 'Taxes', 'AC', 1, '2019-07-16', 1, '2019-07-16'),
(37, 8, 'Leave', 'AC', 1, '2019-07-03', 1, '2019-07-05'),
(38, 8, 'Attendance', 'AC', 1, '2019-07-16', 1, '2019-07-16'),
(39, 8, 'Pay Heads', 'AC', 1, '2019-07-03', 1, '2019-07-05'),
(40, 8, 'Payroll Period', 'AC', 1, '2019-07-16', 1, '2019-07-16'),
(41, 8, 'Helpdesk', 'AC', 1, '2019-07-03', 1, '2019-07-05'),
(42, 8, 'Assets', 'AC', 1, '2019-07-16', 1, '2019-07-16'),
(43, 8, 'Roles & Permissions', 'AC', 1, '2019-07-03', 1, '2019-07-05'),
(44, 8, 'Developer', 'AC', 1, '2019-07-16', 1, '2019-07-16'),
(45, 8, 'Professional Tax', 'AC', 1, '2019-07-03', 1, '2019-07-05'),
(46, 1, 'MyFabHr', 'AC', 1, '2019-07-03', 1, '2019-07-05'),
(47, 2, 'People', 'AC', 1, '2019-07-03', 1, '2019-07-05'),
(48, 3, 'Time & Attendance', 'AC', 1, '2019-07-03', 1, '2019-07-05'),
(49, 4, 'Payroll', 'AC', 1, '2019-07-03', 1, '2019-07-05'),
(50, 8, 'Settings', 'AC', 1, '2019-07-03', 1, '2019-07-05'),
(51, 7, 'Reports', 'AC', 1, '2019-07-03', 1, '2019-07-05'),
(52, 9, 'Dashboard', 'AC', 1, '2019-07-03', 1, '2019-07-05'),
(53, 3, 'Overview', 'AC', 1, '2019-07-03', 1, '2019-07-05');

-- --------------------------------------------------------

--
-- Table structure for table `TBL_EMP`
--

CREATE TABLE `TBL_EMP` (
  `ID` varchar(111) DEFAULT NULL,
  `FIRSTNAME` varchar(111) DEFAULT NULL,
  `LASTNAME` varchar(111) DEFAULT NULL,
  `AA` varchar(111) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `TdsApproved`
--

CREATE TABLE `TdsApproved` (
  `transactionId` int(11) NOT NULL,
  `transactionApprovedHdId` int(11) NOT NULL,
  `tdsGroupId` int(11) NOT NULL,
  `limitAmount` decimal(12,2) DEFAULT '0.00',
  `proofAmount` decimal(12,2) DEFAULT '0.00',
  `approvedAmount` decimal(12,2) DEFAULT '0.00'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `TdsCityMaster`
--

CREATE TABLE `TdsCityMaster` (
  `tdsCityMasterId` int(11) NOT NULL,
  `financialYear` varchar(10) NOT NULL,
  `companyId` int(11) NOT NULL,
  `cityType` varchar(12) NOT NULL,
  `percentage` decimal(12,2) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `TdsDeduction`
--

CREATE TABLE `TdsDeduction` (
  `tdsDeductionId` int(11) NOT NULL,
  `employeeId` int(10) NOT NULL,
  `companyId` int(11) NOT NULL,
  `financialYearId` int(10) NOT NULL,
  `totalTax` int(50) DEFAULT NULL,
  `taxTobeDeductedMonthly` decimal(10,2) DEFAULT '0.00',
  `taxDeductedMonthly` decimal(10,2) DEFAULT '0.00',
  `remark` varchar(100) DEFAULT NULL,
  `userId` int(10) NOT NULL,
  `userIdUpdate` int(10) DEFAULT NULL,
  `dateCreated` date NOT NULL,
  `dateUpdate` date DEFAULT NULL,
  `activeStatus` varchar(2) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `TdsGroup`
--

CREATE TABLE `TdsGroup` (
  `tdsGroupId` int(11) NOT NULL,
  `financialYear` varchar(10) DEFAULT NULL,
  `effectiveStartDate` date DEFAULT NULL,
  `effectiveEndDate` date DEFAULT NULL,
  `tdsGroupName` varchar(10) NOT NULL,
  `tdsDescription` varchar(500) DEFAULT NULL,
  `maxLimit` decimal(12,2) DEFAULT '0.00',
  `ageForExtra` smallint(6) DEFAULT '0',
  `addLimitOnAge` decimal(12,2) DEFAULT '0.00',
  `isSubGroupReq` varchar(1) DEFAULT NULL,
  `isSubGroupLimit` varchar(1) DEFAULT NULL,
  `isIncome` varchar(1) DEFAULT 'N',
  `activeStatus` varchar(2) DEFAULT 'A',
  `companyId` int(11) NOT NULL,
  `groupId` int(11) DEFAULT NULL,
  `allowModi` char(1) DEFAULT 'N',
  `userId` int(11) NOT NULL,
  `dateCreated` datetime DEFAULT NULL,
  `userIdUpdate` int(11) DEFAULT NULL,
  `dateUpdate` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `TdsGroupMaster`
--

CREATE TABLE `TdsGroupMaster` (
  `tdsGroupMasterId` int(14) NOT NULL,
  `tdsGroupName` varchar(100) NOT NULL,
  `isSubGroupLimit` varchar(2) DEFAULT NULL,
  `companyId` int(10) NOT NULL,
  `userId` int(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `TdsGroupMaster`
--

INSERT INTO `TdsGroupMaster` (`tdsGroupMasterId`, `tdsGroupName`, `isSubGroupLimit`, `companyId`, `userId`) VALUES
(1, '80C', 'Y', 1, 1),
(2, '80D,80E,80G AND OTHERS', 'N', 1, 1),
(3, 'Section 10', 'N', 1, 1),
(4, 'Section 24', 'N', 1, 1),
(5, 'Other Standard', 'N', 1, 1);

-- --------------------------------------------------------

--
-- Table structure for table `TdsGroupSetup`
--

CREATE TABLE `TdsGroupSetup` (
  `tdsGroupId` int(11) NOT NULL,
  `tdsGroupMasterId` int(11) NOT NULL,
  `financialYearId` int(11) NOT NULL,
  `maxLimit` decimal(12,0) DEFAULT NULL,
  `activeStatus` varchar(2) NOT NULL,
  `companyId` int(11) NOT NULL,
  `userId` int(11) NOT NULL,
  `dateCreated` datetime NOT NULL,
  `userIdUpdate` int(11) DEFAULT NULL,
  `dateUpdate` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `TdsGroupSetup`
--

INSERT INTO `TdsGroupSetup` (`tdsGroupId`, `tdsGroupMasterId`, `financialYearId`, `maxLimit`, `activeStatus`, `companyId`, `userId`, `dateCreated`, `userIdUpdate`, `dateUpdate`) VALUES
(21, 1, 1, '150000', 'DE', 1, 1, '2019-07-02 19:54:08', NULL, NULL),
(22, 2, 1, NULL, 'DE', 1, 1, '2019-07-02 19:54:08', NULL, NULL),
(23, 3, 1, NULL, 'DE', 1, 1, '2019-07-02 19:54:08', NULL, NULL),
(24, 4, 1, NULL, 'DE', 1, 1, '2019-07-02 19:54:08', NULL, NULL),
(25, 5, 1, NULL, 'DE', 1, 1, '2019-07-02 19:54:08', NULL, NULL),
(26, 1, 1, '150000', 'AC', 1, 1, '2019-07-29 14:34:22', NULL, '2019-07-29 14:34:22'),
(27, 2, 1, NULL, 'AC', 1, 1, '2019-07-29 14:34:22', NULL, '2019-07-29 14:34:22'),
(28, 3, 1, NULL, 'AC', 1, 1, '2019-07-29 14:34:22', NULL, '2019-07-29 14:34:22'),
(29, 4, 1, NULL, 'AC', 1, 1, '2019-07-29 14:34:22', NULL, '2019-07-29 14:34:22'),
(30, 5, 1, NULL, 'AC', 1, 1, '2019-07-29 14:34:22', NULL, '2019-07-29 14:34:22');

-- --------------------------------------------------------

--
-- Table structure for table `TdsHistory`
--

CREATE TABLE `TdsHistory` (
  `tdsHistoryId` int(11) NOT NULL,
  `financialYear` varchar(10) DEFAULT NULL,
  `employeeId` int(11) DEFAULT NULL,
  `approveId` int(11) DEFAULT NULL,
  `status` varchar(10) DEFAULT NULL,
  `dateCreated` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `TdsHouseRentFileInfo`
--

CREATE TABLE `TdsHouseRentFileInfo` (
  `tdsHouseRentFileInfoId` bigint(16) NOT NULL,
  `tdsHouseRentInfoId` bigint(16) NOT NULL,
  `fileName` varchar(30) NOT NULL,
  `originalFilename` varchar(150) NOT NULL,
  `filePath` varchar(300) NOT NULL,
  `activeStatus` varchar(10) DEFAULT NULL,
  `dateCreated` date DEFAULT NULL,
  `dateUpdated` date DEFAULT NULL,
  `userId` int(16) NOT NULL,
  `userIdUpdate` int(16) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `TdsHouseRentInfo`
--

CREATE TABLE `TdsHouseRentInfo` (
  `tdsHouseRentInfoId` bigint(16) NOT NULL,
  `tdsTransactionId` int(16) NOT NULL,
  `landlordName` varchar(50) DEFAULT NULL,
  `landlordPan` varchar(20) DEFAULT NULL,
  `fromDate` date NOT NULL,
  `toDate` date NOT NULL,
  `addressOfRentalProperty` text,
  `addressOfLandlord` text,
  `totalRental` decimal(10,2) NOT NULL,
  `activeStatus` varchar(5) DEFAULT NULL,
  `dateCreated` datetime DEFAULT NULL,
  `dateUpdated` datetime DEFAULT NULL,
  `userId` bigint(16) NOT NULL,
  `userIdUpdate` bigint(16) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `TdsPayroll`
--

CREATE TABLE `TdsPayroll` (
  `transactionId` int(11) NOT NULL,
  `transactionHdId` int(11) NOT NULL,
  `tdsSLabId` int(11) NOT NULL,
  `limitFrom` decimal(12,2) DEFAULT '0.00',
  `limitTo` decimal(12,2) DEFAULT '0.00',
  `tdsPercentage` decimal(5,2) DEFAULT '0.00',
  `actualAmount` decimal(12,2) DEFAULT '0.00',
  `taxAmouunt` decimal(12,2) DEFAULT '0.00'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `TdsPayrollHd`
--

CREATE TABLE `TdsPayrollHd` (
  `transactionHdId` int(11) NOT NULL,
  `employeeId` int(11) NOT NULL,
  `financialYear` varchar(10) DEFAULT NULL,
  `grossIncome` decimal(12,2) DEFAULT '0.00',
  `grossIncomeFy` decimal(12,2) DEFAULT NULL,
  `tdsApproved` decimal(12,2) DEFAULT '0.00',
  `taxableAmount` decimal(12,2) DEFAULT '0.00',
  `companyId` int(11) NOT NULL,
  `active` varchar(10) NOT NULL,
  `dateUpdate` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `TdsSection`
--

CREATE TABLE `TdsSection` (
  `tdsSectionId` int(11) NOT NULL,
  `tdsGroupId` int(11) NOT NULL,
  `tdsSectionName` varchar(10) NOT NULL,
  `tdsDescription` varchar(500) NOT NULL,
  `maxLimit` decimal(12,2) DEFAULT '0.00',
  `ageForExtra` smallint(6) DEFAULT '0',
  `addLimitOnAge` decimal(12,2) DEFAULT '0.00',
  `isParrentRecord` varchar(1) DEFAULT NULL,
  `allowModi` char(1) DEFAULT 'N',
  `userId` int(11) NOT NULL,
  `dateCreated` datetime DEFAULT NULL,
  `userIdUpdate` int(11) DEFAULT NULL,
  `dateUpdate` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `TdsSectionSetup`
--

CREATE TABLE `TdsSectionSetup` (
  `tdsSectionId` int(11) NOT NULL,
  `tdsGroupId` int(11) NOT NULL,
  `tdsSectionName` varchar(250) NOT NULL,
  `maxLimit` int(25) DEFAULT NULL,
  `activeStatus` varchar(2) NOT NULL,
  `userId` int(11) NOT NULL,
  `dateCreated` datetime NOT NULL,
  `userIdUpdate` int(11) DEFAULT NULL,
  `dateUpdate` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `TdsSectionSetup`
--

INSERT INTO `TdsSectionSetup` (`tdsSectionId`, `tdsGroupId`, `tdsSectionName`, `maxLimit`, `activeStatus`, `userId`, `dateCreated`, `userIdUpdate`, `dateUpdate`) VALUES
(1, 21, '80C-5 Years of Fixed Deposit in Schedule Bank', NULL, 'DE', 1, '2019-07-25 14:00:00', 1, '2019-07-25 14:00:00'),
(2, 22, '80D-Preventive Health Check Up', 8000, 'DE', 1, '2019-07-25 14:14:18', 1, '2019-07-25 14:14:18'),
(3, 24, ' Interest on Housing Loan (Self Occupied)', 200000, 'DE', 1, '2019-07-25 13:43:03', 1, '2019-07-25 13:43:03'),
(4, 21, '80C-Children Tuition Fees', NULL, 'DE', 1, '2019-07-25 14:00:00', 1, '2019-07-25 14:00:00'),
(5, 21, '80C-Deposit in NSC', NULL, 'DE', 1, '2019-07-25 14:00:00', 1, '2019-07-25 14:00:00'),
(6, 21, '80C-Deposit in NSS', NULL, 'DE', 1, '2019-07-25 14:00:00', 1, '2019-07-25 14:00:00'),
(7, 21, '80C-Contribution to Pension Fund', NULL, 'DE', 1, '2019-07-25 14:00:00', 1, '2019-07-25 14:00:00'),
(8, 21, '80C-Deposit in Post Savings Schemes', NULL, 'DE', 1, '2019-07-25 14:00:00', 1, '2019-07-25 14:00:00'),
(9, 21, '80C-Equity Linked Savings Schemes', NULL, 'DE', 1, '2019-07-25 14:00:00', 1, '2019-07-25 14:00:00'),
(10, 21, '80C-Infrastructure Bonds', NULL, 'DE', 1, '2019-07-25 14:00:00', 1, '2019-07-25 14:00:00'),
(11, 21, '80C-Interest on NSC Reinvested', NULL, 'DE', 1, '2019-07-25 14:00:00', 1, '2019-07-25 14:00:00'),
(12, 21, '80C-Kisan Vikas Patra', NULL, 'DE', 1, '2019-07-25 14:00:00', 1, '2019-07-25 14:00:00'),
(13, 21, '80C-Mutual Funds', NULL, 'DE', 1, '2019-07-25 14:00:00', 1, '2019-07-25 14:00:00'),
(14, 21, '80C-Life Insurance Premium', NULL, 'DE', 1, '2019-07-25 14:00:00', 1, '2019-07-25 14:00:00'),
(15, 21, '80C-Long Term Infrastructure Bonds', NULL, 'DE', 1, '2019-07-25 14:00:00', 1, '2019-07-25 14:00:00'),
(16, 21, '80C-NABARD Rural Bonds', NULL, 'DE', 1, '2019-07-25 14:00:00', 1, '2019-07-25 14:00:00'),
(17, 21, '80C-NHB Scheme', NULL, 'DE', 1, '2019-07-25 14:00:00', 1, '2019-07-25 14:00:00'),
(18, 21, '80C-National Pension Scheme', NULL, 'DE', 1, '2019-07-25 14:00:00', 1, '2019-07-25 14:00:00'),
(19, 21, '80C-Post office time deposit for five years', NULL, 'DE', 1, '2019-07-25 14:00:00', 1, '2019-07-25 14:00:00'),
(20, 21, '80C-Pradhan Mantri Suraksha Bima Yojana', NULL, 'DE', 1, '2019-07-25 14:00:00', 1, '2019-07-25 14:00:00'),
(21, 21, '80C-Public Provident Fund', NULL, 'DE', 1, '2019-07-25 14:00:00', 1, '2019-07-25 14:00:00'),
(22, 21, '80C-Employee Provident Fund', NULL, 'DE', 1, '2019-07-25 14:00:00', 1, '2019-07-25 14:00:00'),
(23, 21, '80C-Repayment of Housing loan (Principal Amount)', NULL, 'DE', 1, '2019-07-25 14:00:00', 1, '2019-07-25 14:00:00'),
(24, 21, '80C-Stamp Duty and Registration Charges', NULL, 'DE', 1, '2019-07-25 14:00:00', 1, '2019-07-25 14:00:00'),
(25, 21, '80C-Sukanya Samriddhi Yojana', NULL, 'DE', 1, '2019-07-25 14:00:00', 1, '2019-07-25 14:00:00'),
(26, 21, '80C-Unit Linked Insurance Premium', NULL, 'DE', 1, '2019-07-25 14:00:00', 1, '2019-07-25 14:00:00'),
(27, 22, '80D-Preventive Health Check Up-Dependent Parents', 5000, 'DE', 1, '2019-07-25 14:14:18', 1, '2019-07-25 14:14:18'),
(28, 22, '80D-Medical Bills Very Senior Citizen', 30000, 'DE', 1, '2019-07-25 14:14:18', 1, '2019-07-25 14:14:18'),
(29, 22, '80D-Medical Insurance Premium', 50000, 'DE', 1, '2019-07-25 14:14:18', 1, '2019-07-25 14:14:18'),
(30, 22, '80D-Medical Insurance Premium-Dependent Parents', 50000, 'DE', 1, '2019-07-25 14:14:18', 1, '2019-07-25 14:14:18'),
(31, 22, '80D-Medical Insurance Self and family', 25000, 'DE', 1, '2019-07-25 14:14:18', 1, '2019-07-25 14:14:18'),
(32, 22, '80D-Medical Insurance parents', 30000, 'DE', 1, '2019-07-25 14:14:18', 1, '2019-07-25 14:14:18'),
(33, 22, '80D-Medical Insurance Senior Citizen', 50000, 'DE', 1, '2019-07-25 14:14:18', 1, '2019-07-25 14:14:18'),
(34, 22, '80CCD1B-Contribution to NPS 2015', 50000, 'DE', 1, '2019-07-25 14:14:18', 1, '2019-07-25 14:14:18'),
(35, 22, '80G-Donation - 100% Exemption', 99999999, 'DE', 1, '2019-07-25 14:14:18', 1, '2019-07-25 14:14:18'),
(36, 22, '80TTA-Interest on Deposits in Savings Account', 10000, 'DE', 1, '2019-07-25 14:14:18', 1, '2019-07-25 14:14:18'),
(37, 22, '80TTB-Interest on Deposits in Savings Account (Age>60 Years)', 50000, 'DE', 1, '2019-07-25 14:14:18', 1, '2019-07-25 14:14:18'),
(38, 22, '80E-Interest on Loan of self higher education', 99999999, 'DE', 1, '2019-07-25 14:14:18', 1, '2019-07-25 14:14:18'),
(39, 22, '80EE-Home Loan Interest for first time owners (FY 16-17)', 50000, 'DE', 1, '2019-07-25 14:14:18', 1, '2019-07-25 14:14:18'),
(40, 22, '80EEA-Interest paid on home loan for affordable housing (FY 19-20)', 150000, 'DE', 1, '2019-07-25 14:14:18', 1, '2019-07-25 14:14:18'),
(41, 22, '80DD-Medical Treatment / Insurance of handicapped dependent', 75000, 'DE', 1, '2019-07-25 14:14:18', 1, '2019-07-25 14:14:18'),
(42, 22, '80DD-Medical Treatment / Insurance of handicapped dependent (Severe)', 125000, 'DE', 1, '2019-07-25 14:14:18', 1, '2019-07-25 14:14:18'),
(43, 22, '80DDB-Medical Treatment (Specified Disease only)', 40000, 'DE', 1, '2019-07-25 14:14:18', 1, '2019-07-25 14:14:18'),
(44, 22, '80DDB-Medical Treatment (Specified Disease only) - Senior Citizen', 60000, 'DE', 1, '2019-07-25 14:14:18', 1, '2019-07-25 14:14:18'),
(45, 22, '80DDB-Medical Treatment (Specified Disease only) - Very Senior Citizen', 80000, 'DE', 1, '2019-07-25 14:14:18', 1, '2019-07-25 14:14:18'),
(46, 22, '80U-Permanent Physical Disability (Above 40%)', 125000, 'DE', 1, '2019-07-25 14:14:18', 1, '2019-07-25 14:14:18'),
(47, 22, '80U-Permanent Physical Disability (Below 40%)', 75000, 'DE', 1, '2019-07-25 14:14:18', 1, '2019-07-25 14:14:18'),
(48, 22, '80CCG - Rajiv Gandhi Equity Scheme', 25000, 'DE', 1, '2019-07-25 14:14:18', 1, '2019-07-25 14:14:18'),
(49, 26, '80C-5 Years of Fixed Deposit in Schedule Bank', NULL, 'AC', 1, '2019-07-25 14:00:00', 1, '2019-07-25 14:00:00'),
(50, 26, '80C-Children Tuition Fees', NULL, 'AC', 1, '2019-07-25 14:00:00', 1, '2019-07-25 14:00:00'),
(51, 26, '80C-Deposit in NSC', NULL, 'AC', 1, '2019-07-25 14:00:00', 1, '2019-07-25 14:00:00'),
(52, 26, '80C-Deposit in NSS', NULL, 'AC', 1, '2019-07-25 14:00:00', 1, '2019-07-25 14:00:00'),
(53, 26, '80C-Contribution to Pension Fund', NULL, 'AC', 1, '2019-07-25 14:00:00', 1, '2019-07-25 14:00:00'),
(54, 26, '80C-Deposit in Post Savings Schemes', NULL, 'AC', 1, '2019-07-25 14:00:00', 1, '2019-07-25 14:00:00'),
(55, 26, '80C-Equity Linked Savings Schemes', NULL, 'AC', 1, '2019-07-25 14:00:00', 1, '2019-07-25 14:00:00'),
(56, 26, '80C-Infrastructure Bonds', NULL, 'AC', 1, '2019-07-25 14:00:00', 1, '2019-07-25 14:00:00'),
(57, 26, '80C-Interest on NSC Reinvested', NULL, 'AC', 1, '2019-07-25 14:00:00', 1, '2019-07-25 14:00:00'),
(58, 26, '80C-Kisan Vikas Patra', NULL, 'AC', 1, '2019-07-25 14:00:00', 1, '2019-07-25 14:00:00'),
(59, 26, '80C-Mutual Funds', NULL, 'AC', 1, '2019-07-25 14:00:00', 1, '2019-07-25 14:00:00'),
(60, 26, '80C-Life Insurance Premium', NULL, 'AC', 1, '2019-07-25 14:00:00', 1, '2019-07-25 14:00:00'),
(61, 26, '80C-Long Term Infrastructure Bonds', NULL, 'AC', 1, '2019-07-25 14:00:00', 1, '2019-07-25 14:00:00'),
(62, 26, '80C-NABARD Rural Bonds', NULL, 'AC', 1, '2019-07-25 14:00:00', 1, '2019-07-25 14:00:00'),
(63, 26, '80C-NHB Scheme', NULL, 'AC', 1, '2019-07-25 14:00:00', 1, '2019-07-25 14:00:00'),
(64, 26, '80C-National Pension Scheme', NULL, 'AC', 1, '2019-07-25 14:00:00', 1, '2019-07-25 14:00:00'),
(65, 26, '80C-Post office time deposit for five years', NULL, 'AC', 1, '2019-07-25 14:00:00', 1, '2019-07-25 14:00:00'),
(66, 26, '80C-Pradhan Mantri Suraksha Bima Yojana', NULL, 'AC', 1, '2019-07-25 14:00:00', 1, '2019-07-25 14:00:00'),
(67, 26, '80C-Public Provident Fund', NULL, 'AC', 1, '2019-07-25 14:00:00', 1, '2019-07-25 14:00:00'),
(68, 26, '80C-Employee Provident Fund', NULL, 'AC', 1, '2019-07-25 14:00:00', 1, '2019-07-25 14:00:00'),
(69, 26, '80C-Repayment of Housing loan (Principal Amount)', NULL, 'AC', 1, '2019-07-25 14:00:00', 1, '2019-07-25 14:00:00'),
(70, 26, '80C-Stamp Duty and Registration Charges', NULL, 'AC', 1, '2019-07-25 14:00:00', 1, '2019-07-25 14:00:00'),
(71, 26, '80C-Sukanya Samriddhi Yojana', NULL, 'AC', 1, '2019-07-25 14:00:00', 1, '2019-07-25 14:00:00'),
(72, 26, '80C-Unit Linked Insurance Premium', NULL, 'AC', 1, '2019-07-25 14:00:00', 1, '2019-07-25 14:00:00'),
(73, 27, '80D-Preventive Health Check Up', 8000, 'AC', 1, '2019-07-25 14:14:18', 1, '2019-07-25 14:14:18'),
(74, 27, '80D-Preventive Health Check Up-Dependent Parents', 5000, 'AC', 1, '2019-07-25 14:14:18', 1, '2019-07-25 14:14:18'),
(75, 27, '80D-Medical Bills Very Senior Citizen', 30000, 'AC', 1, '2019-07-25 14:14:18', 1, '2019-07-25 14:14:18'),
(76, 27, '80D-Medical Insurance Premium', 50000, 'AC', 1, '2019-07-25 14:14:18', 1, '2019-07-25 14:14:18'),
(77, 27, '80D-Medical Insurance Premium-Dependent Parents', 50000, 'AC', 1, '2019-07-25 14:14:18', 1, '2019-07-25 14:14:18'),
(78, 27, '80D-Medical Insurance Self and family', 25000, 'AC', 1, '2019-07-25 14:14:18', 1, '2019-07-25 14:14:18'),
(79, 27, '80D-Medical Insurance parents', 30000, 'AC', 1, '2019-07-25 14:14:18', 1, '2019-07-25 14:14:18'),
(80, 27, '80D-Medical Insurance Senior Citizen', 50000, 'AC', 1, '2019-07-25 14:14:18', 1, '2019-07-25 14:14:18'),
(81, 27, '80CCD1B-Contribution to NPS 2015', 50000, 'AC', 1, '2019-07-25 14:14:18', 1, '2019-07-25 14:14:18'),
(82, 27, '80G-Donation - 100% Exemption', 99999999, 'AC', 1, '2019-07-25 14:14:18', 1, '2019-07-25 14:14:18'),
(83, 27, '80TTA-Interest on Deposits in Savings Account', 10000, 'AC', 1, '2019-07-25 14:14:18', 1, '2019-07-25 14:14:18'),
(84, 27, '80TTB-Interest on Deposits in Savings Account (Age>60 Years)', 50000, 'AC', 1, '2019-07-25 14:14:18', 1, '2019-07-25 14:14:18'),
(85, 27, '80E-Interest on Loan of self higher education', 99999999, 'AC', 1, '2019-07-25 14:14:18', 1, '2019-07-25 14:14:18'),
(86, 27, '80EE-Home Loan Interest for first time owners (FY 16-17)', 50000, 'AC', 1, '2019-07-25 14:14:18', 1, '2019-07-25 14:14:18'),
(87, 27, '80EEA-Interest paid on home loan for affordable housing (FY 19-20)', 150000, 'AC', 1, '2019-07-25 14:14:18', 1, '2019-07-25 14:14:18'),
(88, 27, '80DD-Medical Treatment / Insurance of handicapped dependent', 75000, 'AC', 1, '2019-07-25 14:14:18', 1, '2019-07-25 14:14:18'),
(89, 27, '80DD-Medical Treatment / Insurance of handicapped dependent (Severe)', 125000, 'AC', 1, '2019-07-25 14:14:18', 1, '2019-07-25 14:14:18'),
(90, 27, '80DDB-Medical Treatment (Specified Disease only)', 40000, 'AC', 1, '2019-07-25 14:14:18', 1, '2019-07-25 14:14:18'),
(91, 27, '80DDB-Medical Treatment (Specified Disease only) - Senior Citizen', 60000, 'AC', 1, '2019-07-25 14:14:18', 1, '2019-07-25 14:14:18'),
(92, 27, '80DDB-Medical Treatment (Specified Disease only) - Very Senior Citizen', 80000, 'AC', 1, '2019-07-25 14:14:18', 1, '2019-07-25 14:14:18'),
(93, 27, '80U-Permanent Physical Disability (Above 40%)', 125000, 'AC', 1, '2019-07-25 14:14:18', 1, '2019-07-25 14:14:18'),
(94, 27, '80U-Permanent Physical Disability (Below 40%)', 75000, 'AC', 1, '2019-07-25 14:14:18', 1, '2019-07-25 14:14:18'),
(95, 27, '80CCG - Rajiv Gandhi Equity Scheme', 25000, 'AC', 1, '2019-07-25 14:14:18', 1, '2019-07-25 14:14:18'),
(96, 29, ' Interest on Housing Loan (Self Occupied)', 200000, 'AC', 1, '2019-07-25 13:43:03', 1, '2019-07-25 13:43:03');

-- --------------------------------------------------------

--
-- Table structure for table `TdsSlab`
--

CREATE TABLE `TdsSlab` (
  `tdsSLabId` int(11) NOT NULL,
  `tdsSLabHdId` int(11) DEFAULT NULL,
  `limitFrom` decimal(12,2) DEFAULT NULL,
  `limitTo` decimal(12,2) DEFAULT NULL,
  `tdsPercentage` decimal(5,2) DEFAULT NULL,
  `allowModi` char(1) DEFAULT 'N',
  `userId` int(11) NOT NULL,
  `dateCreated` datetime DEFAULT NULL,
  `userIdUpdate` int(11) DEFAULT NULL,
  `dateUpdate` datetime DEFAULT NULL,
  `activeStatus` varchar(2) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `TdsSlab`
--

INSERT INTO `TdsSlab` (`tdsSLabId`, `tdsSLabHdId`, `limitFrom`, `limitTo`, `tdsPercentage`, `allowModi`, `userId`, `dateCreated`, `userIdUpdate`, `dateUpdate`, `activeStatus`) VALUES
(31, 25, '0.00', '250000.00', '0.00', NULL, 1, '2019-07-25 13:16:23', 1, '2019-07-25 13:16:23', 'DE'),
(32, 25, '250000.00', '500000.00', '5.00', NULL, 1, '2019-07-25 13:16:23', 1, '2019-07-25 13:16:23', 'DE'),
(33, 25, '500000.00', '1000000.00', '20.00', NULL, 1, '2019-07-25 13:16:23', 1, '2019-07-25 13:16:23', 'DE'),
(34, 27, '0.00', '250000.00', '0.00', NULL, 1, '2019-07-25 13:20:50', 1, '2019-07-25 13:20:50', 'DE'),
(35, 27, '250000.00', '500000.00', '5.00', NULL, 1, '2019-07-25 13:20:50', 1, '2019-07-25 13:20:50', 'DE'),
(36, 27, '500000.00', '1000000.00', '20.00', NULL, 1, '2019-07-25 13:20:50', 1, '2019-07-25 13:20:50', 'DE'),
(37, 28, '0.00', '300000.00', '0.00', NULL, 1, '2019-07-25 13:26:04', 1, '2019-07-25 13:26:04', 'DE'),
(38, 28, '300000.00', '500000.00', '5.00', NULL, 1, '2019-07-25 13:26:04', 1, '2019-07-25 13:26:04', 'DE'),
(39, 28, '500000.00', '1000000.00', '20.00', NULL, 1, '2019-07-25 13:26:04', 1, '2019-07-25 13:26:04', 'DE'),
(40, 29, '1.00', '99999999.00', '4.00', NULL, 100414, '2019-07-13 18:13:47', 100414, '2019-07-13 18:13:47', 'DE'),
(41, 25, '1000000.00', '99999999.00', '30.00', NULL, 1, '2019-07-25 13:16:23', 1, '2019-07-25 13:16:23', 'DE'),
(42, 27, '1000000.00', '99999999.00', '30.00', NULL, 1, '2019-07-25 13:20:50', 1, '2019-07-25 13:20:50', 'DE'),
(43, 28, '1000000.00', '99999999.00', '30.00', NULL, 1, '2019-07-25 13:26:04', 1, '2019-07-25 13:26:04', 'DE'),
(44, 31, '0.00', '250000.00', '0.00', NULL, 1, '2019-07-25 13:16:23', NULL, NULL, 'AC'),
(45, 31, '250000.00', '500000.00', '5.00', NULL, 1, '2019-07-25 13:16:23', NULL, NULL, 'AC'),
(46, 31, '500000.00', '1000000.00', '20.00', NULL, 1, '2019-07-25 13:16:23', NULL, NULL, 'AC'),
(47, 31, '1000000.00', '99999999.00', '30.00', NULL, 1, '2019-07-25 13:16:23', NULL, NULL, 'AC'),
(48, 33, '0.00', '250000.00', '0.00', NULL, 1, '2019-07-25 13:20:50', NULL, NULL, 'AC'),
(49, 33, '250000.00', '500000.00', '5.00', NULL, 1, '2019-07-25 13:20:50', NULL, NULL, 'AC'),
(50, 33, '500000.00', '1000000.00', '20.00', NULL, 1, '2019-07-25 13:20:50', NULL, NULL, 'AC'),
(51, 33, '1000000.00', '99999999.00', '30.00', NULL, 1, '2019-07-25 13:20:50', NULL, NULL, 'AC'),
(52, 34, '0.00', '300000.00', '0.00', NULL, 1, '2019-07-25 13:26:04', NULL, NULL, 'AC'),
(53, 34, '300000.00', '500000.00', '5.00', NULL, 1, '2019-07-25 13:26:04', NULL, NULL, 'AC'),
(54, 34, '500000.00', '1000000.00', '20.00', NULL, 1, '2019-07-25 13:26:04', NULL, NULL, 'AC'),
(55, 34, '1000000.00', '99999999.00', '30.00', NULL, 1, '2019-07-25 13:26:04', NULL, NULL, 'AC'),
(56, 35, '1.00', '99999999.00', '4.00', NULL, 100414, '2019-07-13 18:13:47', NULL, NULL, 'AC');

--
-- Triggers `TdsSlab`
--
DELIMITER $$
CREATE TRIGGER `tdsSlab_history_trigger` BEFORE UPDATE ON `TdsSlab` FOR EACH ROW BEGIN

 declare v_tdsSLabId int(11) ;
 declare v_tdsSLabHdId int(11) ;
 declare v_limitFrom decimal(12,2) ;
 declare v_limitTo decimal(12,2) ;
 declare v_tdsPercentage decimal(5,2) ;
 declare v_allowModi char(1) ;
 declare v_userId int(11) ;
 declare v_dateCreated datetime ;
 declare v_userIdUpdate int(11) ;
 declare v_dateUpdate datetime ;


 SET v_tdsSLabId =OLD.tdsSLabId ;
 SET v_tdsSLabHdId =OLD.tdsSLabHdId ;
 SET v_limitFrom =OLD.limitFrom ;
 SET v_limitTo =OLD.limitTo ;
 SET v_tdsPercentage =OLD.tdsPercentage ;
 SET v_allowModi =OLD.allowModi ;
 SET v_userId =OLD.userId ;
 SET v_dateCreated =OLD.dateCreated ;
 SET v_userIdUpdate =NEW.userIdUpdate ;
 SET v_dateUpdate =OLD.dateUpdate ;


Insert INTO TdsSlabHistory(

  tdsSLabId ,
 tdsSLabHdId ,
 limitFrom ,
 limitTo ,
 tdsPercentage ,
 allowModi ,
 userId ,
 dateCreated ,
 userIdUpdate ,
 dateUpdate 
    
   ) VALUES
  (
 
 v_tdsSLabId ,
 v_tdsSLabHdId ,
 v_limitFrom ,
 v_limitTo ,
 v_tdsPercentage ,
 v_allowModi ,
 v_userId ,
 v_dateCreated ,
 v_userIdUpdate ,
 v_dateUpdate 
  
  );
End
$$
DELIMITER ;

-- --------------------------------------------------------

--
-- Table structure for table `TdsSlabHd`
--

CREATE TABLE `TdsSlabHd` (
  `tdsSLabHdId` int(11) NOT NULL,
  `tdsSlabMasterId` int(10) NOT NULL,
  `finencialYearId` int(10) NOT NULL,
  `dateEffective` date DEFAULT NULL,
  `effectiveStartDate` datetime DEFAULT NULL,
  `effectiveEndDate` datetime DEFAULT NULL,
  `activeStatus` varchar(2) DEFAULT NULL,
  `companyId` int(11) NOT NULL,
  `groupId` int(11) DEFAULT NULL,
  `allowModi` char(1) DEFAULT 'N',
  `userId` int(11) NOT NULL,
  `dateCreated` datetime DEFAULT NULL,
  `userIdUpdate` int(11) DEFAULT NULL,
  `dateUpdate` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `TdsSlabHd`
--

INSERT INTO `TdsSlabHd` (`tdsSLabHdId`, `tdsSlabMasterId`, `finencialYearId`, `dateEffective`, `effectiveStartDate`, `effectiveEndDate`, `activeStatus`, `companyId`, `groupId`, `allowModi`, `userId`, `dateCreated`, `userIdUpdate`, `dateUpdate`) VALUES
(25, 1, 1, NULL, NULL, NULL, 'DE', 1, NULL, NULL, 1, '2019-07-02 19:54:08', NULL, NULL),
(26, 2, 1, NULL, NULL, NULL, 'DE', 1, NULL, NULL, 1, '2019-07-02 19:54:08', NULL, NULL),
(27, 3, 1, NULL, NULL, NULL, 'DE', 1, NULL, NULL, 1, '2019-07-02 19:54:08', NULL, NULL),
(28, 4, 1, NULL, NULL, NULL, 'DE', 1, NULL, NULL, 1, '2019-07-02 19:54:08', NULL, NULL),
(29, 5, 1, NULL, NULL, NULL, 'DE', 1, NULL, NULL, 1, '2019-07-02 19:54:08', NULL, NULL),
(30, 6, 1, NULL, NULL, NULL, 'DE', 1, NULL, NULL, 1, '2019-07-02 19:54:08', NULL, NULL),
(31, 1, 1, NULL, NULL, NULL, 'AC', 1, NULL, NULL, 1, '2019-07-29 14:34:24', NULL, '2019-07-29 14:34:24'),
(32, 2, 1, NULL, NULL, NULL, 'AC', 1, NULL, NULL, 1, '2019-07-29 14:34:24', NULL, '2019-07-29 14:34:24'),
(33, 3, 1, NULL, NULL, NULL, 'AC', 1, NULL, NULL, 1, '2019-07-29 14:34:24', NULL, '2019-07-29 14:34:24'),
(34, 4, 1, NULL, NULL, NULL, 'AC', 1, NULL, NULL, 1, '2019-07-29 14:34:24', NULL, '2019-07-29 14:34:24'),
(35, 5, 1, NULL, NULL, NULL, 'AC', 1, NULL, NULL, 1, '2019-07-29 14:34:24', NULL, '2019-07-29 14:34:24'),
(36, 6, 1, NULL, NULL, NULL, 'AC', 1, NULL, NULL, 1, '2019-07-29 14:34:24', NULL, '2019-07-29 14:34:24');

-- --------------------------------------------------------

--
-- Table structure for table `TdsSlabHistory`
--

CREATE TABLE `TdsSlabHistory` (
  `tdsSLabId` int(11) NOT NULL,
  `tdsSLabHdId` int(11) DEFAULT NULL,
  `limitFrom` decimal(12,2) DEFAULT '0.00',
  `limitTo` decimal(12,2) DEFAULT '0.00',
  `tdsPercentage` decimal(5,2) DEFAULT '0.00',
  `allowModi` char(1) DEFAULT 'N',
  `userId` int(11) NOT NULL,
  `dateCreated` datetime DEFAULT NULL,
  `userIdUpdate` int(11) DEFAULT NULL,
  `dateUpdate` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `TdsSlabHistory`
--

INSERT INTO `TdsSlabHistory` (`tdsSLabId`, `tdsSLabHdId`, `limitFrom`, `limitTo`, `tdsPercentage`, `allowModi`, `userId`, `dateCreated`, `userIdUpdate`, `dateUpdate`) VALUES
(31, 25, '250001.00', '500000.00', '5.00', NULL, 100414, '2019-07-13 18:13:04', 1, '2019-07-13 18:13:04'),
(32, 25, '500001.00', '1000000.00', '20.00', NULL, 100414, '2019-07-13 18:13:04', 1, '2019-07-13 18:13:04'),
(33, 25, '1000001.00', '99999999.00', '30.00', NULL, 100414, '2019-07-13 18:13:04', 1, '2019-07-13 18:13:04'),
(31, 25, '250001.00', '500000.00', '5.00', NULL, 1, '2019-07-25 13:07:20', 1, '2019-07-25 13:07:20'),
(32, 25, '500001.00', '1000000.00', '20.00', NULL, 1, '2019-07-25 13:07:20', 1, '2019-07-25 13:07:20'),
(33, 25, '1000001.00', '99999999.00', '30.00', NULL, 1, '2019-07-25 13:07:20', 1, '2019-07-25 13:07:20'),
(34, 27, '250001.00', '500000.00', '5.00', NULL, 100414, '2019-07-13 18:12:52', 1, '2019-07-13 18:12:52'),
(35, 27, '500001.00', '1000000.00', '20.00', NULL, 100414, '2019-07-13 18:12:52', 1, '2019-07-13 18:12:52'),
(36, 27, '1000001.00', '99999999.00', '30.00', NULL, 100414, '2019-07-13 18:12:52', 1, '2019-07-13 18:12:52'),
(31, 25, '250001.00', '500000.00', '5.00', NULL, 1, '2019-07-25 13:07:21', 1, '2019-07-25 13:07:21'),
(32, 25, '500001.00', '1000000.00', '20.00', NULL, 1, '2019-07-25 13:07:21', 1, '2019-07-25 13:07:21'),
(33, 25, '1000001.00', '99999999.00', '30.00', NULL, 1, '2019-07-25 13:07:21', 1, '2019-07-25 13:07:21'),
(31, 25, '250001.00', '500000.00', '5.00', NULL, 1, '2019-07-25 13:13:06', 1, '2019-07-25 13:13:06'),
(32, 25, '500001.00', '1000000.00', '20.00', NULL, 1, '2019-07-25 13:13:06', 1, '2019-07-25 13:13:06'),
(33, 25, '1000001.00', '99999999.00', '30.00', NULL, 1, '2019-07-25 13:13:06', 1, '2019-07-25 13:13:06'),
(34, 27, '250001.00', '500000.00', '5.00', NULL, 1, '2019-07-25 13:07:26', 1, '2019-07-25 13:07:26'),
(35, 27, '500001.00', '1000000.00', '20.00', NULL, 1, '2019-07-25 13:07:26', 1, '2019-07-25 13:07:26'),
(36, 27, '1000001.00', '99999999.00', '30.00', NULL, 1, '2019-07-25 13:07:26', 1, '2019-07-25 13:07:26'),
(37, 28, '300001.00', '500000.00', '5.00', NULL, 100414, '2019-07-13 18:12:34', 1, '2019-07-13 18:12:34'),
(38, 28, '500001.00', '1000000.00', '20.00', NULL, 100414, '2019-07-13 18:12:34', 1, '2019-07-13 18:12:34'),
(39, 28, '1000001.00', '99999999.00', '30.00', NULL, 100414, '2019-07-13 18:12:34', 1, '2019-07-13 18:12:34'),
(31, 25, '1.00', '250000.00', '1.00', NULL, 1, '2019-07-25 13:16:23', 1, '2019-07-25 13:16:23'),
(31, 25, '0.00', '250000.00', '1.00', NULL, 1, '2019-07-25 13:16:23', 1, '2019-07-25 13:16:23'),
(34, 27, '1.00', '250000.00', '1.00', NULL, 1, '2019-07-25 13:20:50', 1, '2019-07-25 13:20:50'),
(34, 27, '0.00', '250000.00', '1.00', NULL, 1, '2019-07-25 13:20:50', 1, '2019-07-25 13:20:50'),
(37, 28, '1.00', '300000.00', '1.00', NULL, 1, '2019-07-25 13:26:04', 1, '2019-07-25 13:26:04'),
(37, 28, '0.00', '300000.00', '1.00', NULL, 1, '2019-07-25 13:26:04', 1, '2019-07-25 13:26:04'),
(31, 25, '0.00', '250000.00', '0.00', NULL, 1, '2019-07-25 13:16:23', 1, '2019-07-25 13:16:23'),
(32, 25, '250000.00', '500000.00', '5.00', NULL, 1, '2019-07-25 13:16:23', 1, '2019-07-25 13:16:23'),
(33, 25, '500000.00', '1000000.00', '20.00', NULL, 1, '2019-07-25 13:16:23', 1, '2019-07-25 13:16:23'),
(41, 25, '1000000.00', '99999999.00', '30.00', NULL, 1, '2019-07-25 13:16:23', 1, '2019-07-25 13:16:23'),
(34, 27, '0.00', '250000.00', '0.00', NULL, 1, '2019-07-25 13:20:50', 1, '2019-07-25 13:20:50'),
(35, 27, '250000.00', '500000.00', '5.00', NULL, 1, '2019-07-25 13:20:50', 1, '2019-07-25 13:20:50'),
(36, 27, '500000.00', '1000000.00', '20.00', NULL, 1, '2019-07-25 13:20:50', 1, '2019-07-25 13:20:50'),
(42, 27, '1000000.00', '99999999.00', '30.00', NULL, 1, '2019-07-25 13:20:50', 1, '2019-07-25 13:20:50'),
(37, 28, '0.00', '300000.00', '0.00', NULL, 1, '2019-07-25 13:26:04', 1, '2019-07-25 13:26:04'),
(38, 28, '300000.00', '500000.00', '5.00', NULL, 1, '2019-07-25 13:26:04', 1, '2019-07-25 13:26:04'),
(39, 28, '500000.00', '1000000.00', '20.00', NULL, 1, '2019-07-25 13:26:04', 1, '2019-07-25 13:26:04'),
(43, 28, '1000000.00', '99999999.00', '30.00', NULL, 1, '2019-07-25 13:26:04', 1, '2019-07-25 13:26:04'),
(40, 29, '1.00', '99999999.00', '4.00', NULL, 100414, '2019-07-13 18:13:47', 100414, '2019-07-13 18:13:47');

-- --------------------------------------------------------

--
-- Table structure for table `TdsSlabMaster`
--

CREATE TABLE `TdsSlabMaster` (
  `tdsSlabMasterId` int(11) NOT NULL,
  `tdsCategory` varchar(10) NOT NULL,
  `companyId` int(10) NOT NULL,
  `userId` int(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `TdsSlabMaster`
--

INSERT INTO `TdsSlabMaster` (`tdsSlabMasterId`, `tdsCategory`, `companyId`, `userId`) VALUES
(1, 'M', 1, 1),
(2, 'SU', 1, 1),
(3, 'F', 1, 1),
(4, 'SE', 1, 1),
(5, 'EC', 1, 1),
(6, 'T', 1, 1);

-- --------------------------------------------------------

--
-- Table structure for table `TdsStandardExemption`
--

CREATE TABLE `TdsStandardExemption` (
  `tdsStandardExemptionId` int(11) NOT NULL,
  `financialYearId` int(10) NOT NULL,
  `amount` decimal(12,2) NOT NULL,
  `userId` int(11) NOT NULL,
  `companyId` int(11) NOT NULL,
  `activeStatus` varchar(2) DEFAULT NULL,
  `effectiveStartDate` datetime DEFAULT NULL,
  `effectiveEndDate` datetime DEFAULT NULL,
  `dateCreated` datetime DEFAULT NULL,
  `userIdUpdate` int(11) DEFAULT NULL,
  `dateUpdate` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `TdsSummary`
--

CREATE TABLE `TdsSummary` (
  `tdsSummaryId` int(11) NOT NULL,
  `employeeId` int(11) NOT NULL,
  `financialYear` varchar(10) NOT NULL,
  `yearlyGross` decimal(12,2) DEFAULT NULL,
  `otherIncome` decimal(12,2) DEFAULT NULL,
  `netYearlyIncome` decimal(12,2) DEFAULT NULL,
  `exempStandard` decimal(12,2) DEFAULT NULL,
  `exempPfAmount` decimal(12,2) DEFAULT NULL,
  `exempPtAmount` decimal(12,2) DEFAULT NULL,
  `exempEsicAmount` decimal(12,2) DEFAULT NULL,
  `exempAmountAsPerSlab` decimal(12,2) DEFAULT NULL,
  `exemptedTotalIncome` decimal(12,2) DEFAULT NULL,
  `yearlyTaxableIncome` decimal(12,2) DEFAULT NULL,
  `taxableIncomeFy` decimal(12,2) DEFAULT NULL,
  `tdsYearlyBeforeDeclaration` decimal(12,2) DEFAULT NULL,
  `tdsMonthlyBeforeDeclaration` decimal(12,2) DEFAULT NULL,
  `incomeDeclared` decimal(12,2) DEFAULT NULL,
  `declaredIncomeApproved` decimal(12,2) DEFAULT NULL,
  `netTaxableIncome` decimal(12,2) DEFAULT NULL,
  `tdsYearlyAfterDeclaration` decimal(12,2) DEFAULT NULL,
  `tdsMonthlyAfterDeclaration` decimal(12,2) DEFAULT NULL,
  `total80cAmount` decimal(12,2) DEFAULT NULL,
  `userId` int(11) NOT NULL,
  `active` varchar(2) DEFAULT NULL,
  `dateCreated` datetime DEFAULT NULL,
  `userIdUpdate` int(11) DEFAULT NULL,
  `dateUpdate` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `TdsSummaryChange`
--

CREATE TABLE `TdsSummaryChange` (
  `tdsSummaryChangeId` int(11) NOT NULL,
  `financialYearId` bigint(10) NOT NULL,
  `employeeId` int(11) NOT NULL,
  `yearlyGross` decimal(12,2) NOT NULL,
  `yearlyGrossFy` decimal(12,2) NOT NULL,
  `otherIncome` decimal(12,2) NOT NULL,
  `preEmpIncome` decimal(12,2) DEFAULT NULL,
  `netYearlyIncome` decimal(12,2) NOT NULL,
  `exempStandard` decimal(12,2) NOT NULL,
  `exempPfAmount` decimal(12,2) NOT NULL,
  `exempPtAmount` decimal(12,2) NOT NULL,
  `exempEsicAmount` decimal(12,2) DEFAULT NULL,
  `exemptedTotalIncome` decimal(12,2) NOT NULL,
  `totalIncomeProfessionalTax` decimal(12,2) NOT NULL,
  `chapter6a` decimal(12,2) NOT NULL,
  `section10` decimal(12,2) NOT NULL,
  `section24` decimal(12,2) NOT NULL,
  `totalDeductionIncome` decimal(12,2) NOT NULL,
  `taxableIncome` decimal(12,2) NOT NULL,
  `tax` decimal(12,2) NOT NULL,
  `surcharge` decimal(12,2) NOT NULL,
  `educationCess` decimal(12,2) NOT NULL,
  `surchargePer` decimal(12,2) DEFAULT NULL,
  `educationCessPer` decimal(12,2) DEFAULT NULL,
  `section84ARebateTax` decimal(12,2) DEFAULT NULL,
  `totalTax` decimal(12,2) DEFAULT NULL,
  `netTaxYearly` decimal(12,2) DEFAULT NULL,
  `netTaxMonthly` decimal(12,2) DEFAULT NULL,
  `incomeAfterExemptions` decimal(12,2) NOT NULL,
  `professionalTax` decimal(12,2) NOT NULL,
  `providentFund` decimal(12,2) NOT NULL,
  `potalTax` decimal(12,2) NOT NULL,
  `taxOnIncome` decimal(12,2) NOT NULL,
  `previousSurcharge` decimal(12,2) NOT NULL,
  `previousEducationCess` decimal(12,2) NOT NULL,
  `total80cAmount` decimal(12,2) DEFAULT NULL,
  `section84ARebateAmount` decimal(12,2) DEFAULT NULL,
  `totalGrossPaid` decimal(12,2) DEFAULT NULL,
  `totalGrossToBePaid` decimal(12,2) DEFAULT NULL,
  `totalOneTimeEarningPaid` decimal(12,2) DEFAULT NULL,
  `declarProcessMonth` decimal(12,2) DEFAULT NULL,
  `totalTaxPaid` decimal(12,2) DEFAULT NULL,
  `totalTaxToBePaid` decimal(12,2) DEFAULT NULL,
  `userId` int(11) NOT NULL,
  `active` varchar(2) NOT NULL,
  `dateCreated` datetime DEFAULT NULL,
  `userIdUpdate` int(11) DEFAULT NULL,
  `dateUpdate` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `TdsTransaction`
--

CREATE TABLE `TdsTransaction` (
  `tdsTransactionId` int(11) NOT NULL,
  `employeeId` int(11) NOT NULL,
  `financialYearId` int(11) NOT NULL,
  `tdsGroupId` int(11) NOT NULL,
  `tdsSectionId` int(10) NOT NULL,
  `maxLimit` decimal(12,2) DEFAULT NULL,
  `investmentAmount` decimal(12,2) DEFAULT '0.00',
  `approvedAmount` decimal(12,2) DEFAULT NULL,
  `basicDA` decimal(12,2) DEFAULT NULL,
  `status` varchar(15) DEFAULT NULL,
  `approveStatus` varchar(2) DEFAULT 'N',
  `proof` varchar(2) DEFAULT 'N',
  `noOfDocuments` int(11) NOT NULL,
  `investmentDetail` varchar(50) DEFAULT NULL,
  `city` varchar(12) DEFAULT NULL,
  `tdsTransactionUpdateStatus` varchar(10) DEFAULT NULL,
  `remarks` varchar(100) DEFAULT NULL,
  `fileLocation` varchar(100) DEFAULT NULL,
  `allowModi` char(1) DEFAULT 'N',
  `activeStatus` varchar(2) DEFAULT NULL,
  `userId` int(11) NOT NULL,
  `dateCreated` datetime DEFAULT NULL,
  `userIdUpdate` int(11) DEFAULT NULL,
  `dateUpdate` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `TdsTransactionFile`
--

CREATE TABLE `TdsTransactionFile` (
  `TdsTransactionFileId` int(11) NOT NULL,
  `employeeId` int(11) NOT NULL,
  `title` varchar(50) NOT NULL,
  `fileName` varchar(100) NOT NULL,
  `filePath` varchar(500) NOT NULL,
  `userId` int(11) NOT NULL,
  `dateCreated` datetime DEFAULT NULL,
  `userIdUpdate` int(11) DEFAULT NULL,
  `dateUpdate` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `TdsTransactionFileInfo`
--

CREATE TABLE `TdsTransactionFileInfo` (
  `tdsTransactionFileInfoId` int(16) NOT NULL,
  `tdsTransactionId` int(16) NOT NULL,
  `fileName` varchar(30) NOT NULL,
  `originalFilename` varchar(150) NOT NULL,
  `filePath` varchar(300) NOT NULL,
  `activeStatus` varchar(10) DEFAULT NULL,
  `dateCreated` date DEFAULT NULL,
  `dateUpdated` date DEFAULT NULL,
  `userId` int(16) NOT NULL,
  `userIdUpdate` int(16) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `TdsTransactionHistory`
--

CREATE TABLE `TdsTransactionHistory` (
  `tdsTransactionId` int(11) NOT NULL,
  `financialYear` varchar(10) NOT NULL,
  `employeeId` int(11) NOT NULL,
  `tdsGroupId` int(11) NOT NULL,
  `tdsSectionId` int(11) NOT NULL,
  `maxLimit` decimal(12,2) DEFAULT NULL,
  `investmentAmount` decimal(12,2) DEFAULT '0.00',
  `approveStatus` varchar(2) DEFAULT 'N',
  `proof` varchar(2) DEFAULT 'N',
  `noOfDocuments` int(11) NOT NULL,
  `investmentDetail` varchar(50) DEFAULT NULL,
  `fileLocation` varchar(100) DEFAULT NULL,
  `allowModi` char(1) DEFAULT 'N',
  `activeStatus` varchar(2) DEFAULT NULL,
  `userId` int(11) NOT NULL,
  `dateCreated` datetime DEFAULT NULL,
  `userIdUpdate` int(11) DEFAULT NULL,
  `dateUpdate` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `TempEmployeeNaminee`
--

CREATE TABLE `TempEmployeeNaminee` (
  `employeeCode` varchar(512) NOT NULL,
  `uanNo` varchar(512) NOT NULL,
  `EPFJOINING` varchar(10) NOT NULL,
  `employeeId` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `TicketDesc`
--

CREATE TABLE `TicketDesc` (
  `ticketDescId` int(11) NOT NULL,
  `ticketRaisingHDId` int(11) NOT NULL,
  `description` text,
  `userId` int(11) NOT NULL,
  `dateCreated` datetime DEFAULT NULL,
  `userIdUpdate` int(11) DEFAULT NULL,
  `dateUpdate` datetime DEFAULT NULL,
  `employeeId` int(11) NOT NULL,
  `fileLocation` varchar(100) DEFAULT NULL,
  `fileExtension` varchar(20) DEFAULT NULL,
  `status` varchar(12) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `TicketRaisingHD`
--

CREATE TABLE `TicketRaisingHD` (
  `ticketRaisingHDId` int(11) NOT NULL,
  `ticketTypeId` int(11) NOT NULL,
  `employeeId` int(11) NOT NULL,
  `title` varchar(100) DEFAULT NULL,
  `createdBy` int(11) DEFAULT NULL,
  `status` varchar(10) NOT NULL,
  `ticketNo` varchar(25) NOT NULL,
  `effectiveStartDate` datetime DEFAULT NULL,
  `effectiveEndDate` datetime DEFAULT NULL,
  `activeStatus` varchar(2) DEFAULT NULL,
  `userId` int(11) DEFAULT NULL,
  `dateCreated` datetime DEFAULT NULL,
  `userIdUpdate` int(11) DEFAULT NULL,
  `dateUpdate` datetime DEFAULT NULL,
  `companyID` int(11) DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `TicketType`
--

CREATE TABLE `TicketType` (
  `ticketTypeId` int(11) NOT NULL,
  `category` varchar(50) NOT NULL,
  `email` varchar(50) NOT NULL,
  `tat` int(20) NOT NULL,
  `companyId` int(11) NOT NULL,
  `activeStatus` varchar(2) NOT NULL,
  `effectiveStartDate` datetime DEFAULT NULL,
  `effectiveEndDate` datetime DEFAULT NULL,
  `userId` int(11) NOT NULL,
  `dateCreated` datetime DEFAULT NULL,
  `userIdUpdate` int(11) DEFAULT NULL,
  `dateUpdate` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `TicketType`
--

INSERT INTO `TicketType` (`ticketTypeId`, `category`, `email`, `tat`, `companyId`, `activeStatus`, `effectiveStartDate`, `effectiveEndDate`, `userId`, `dateCreated`, `userIdUpdate`, `dateUpdate`) VALUES
(1, 'Network Issue', 'hr@tencogroup.net', 24, 1, 'AC', NULL, NULL, 1, '2019-07-30 06:52:18', 1, '2019-07-30 06:52:18');

-- --------------------------------------------------------

--
-- Table structure for table `TMSARRequest`
--

CREATE TABLE `TMSARRequest` (
  `arID` int(11) NOT NULL,
  `employeeId` int(11) NOT NULL,
  `approvalId` int(11) DEFAULT NULL,
  `arCategory` varchar(2) NOT NULL,
  `fromDate` date NOT NULL,
  `toDate` date NOT NULL,
  `days` int(11) NOT NULL,
  `status` varchar(5) NOT NULL,
  `approvalRemark` text,
  `employeeRemark` text NOT NULL,
  `companyId` int(11) NOT NULL,
  `actionableDate` date DEFAULT NULL,
  `userId` int(11) NOT NULL,
  `dateCreated` date NOT NULL,
  `userIdUpdate` int(11) NOT NULL,
  `dateUpdate` date NOT NULL,
  `cancelRemark` varchar(200) DEFAULT NULL,
  `isRead` tinyint(1) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `TMSCompensantoryOff`
--

CREATE TABLE `TMSCompensantoryOff` (
  `tmsCompensantoryOffId` int(11) NOT NULL,
  `employeeId` int(11) NOT NULL,
  `approvalId` int(11) DEFAULT NULL,
  `fromDate` datetime DEFAULT NULL,
  `toDate` datetime DEFAULT NULL,
  `days` int(11) DEFAULT NULL,
  `remark` text,
  `approvalRemark` text,
  `leaveTypeId` int(11) DEFAULT NULL,
  `status` varchar(5) NOT NULL,
  `userId` int(11) NOT NULL,
  `dateCreated` date DEFAULT NULL,
  `userIdUpdate` int(11) DEFAULT NULL,
  `dateUpdate` date DEFAULT NULL,
  `companyId` int(20) NOT NULL,
  `cancelRemark` varchar(500) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `TMSHalfDayRule`
--

CREATE TABLE `TMSHalfDayRule` (
  `halfDayRuleId` int(11) NOT NULL,
  `minimumRequireHour` int(11) NOT NULL,
  `maximumRequireHour` int(11) NOT NULL,
  `userId` int(11) NOT NULL,
  `updatedId` int(11) DEFAULT NULL,
  `createdDate` date NOT NULL,
  `updatedDate` date DEFAULT NULL,
  `companyId` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `TMSHalfDayRule`
--

INSERT INTO `TMSHalfDayRule` (`halfDayRuleId`, `minimumRequireHour`, `maximumRequireHour`, `userId`, `updatedId`, `createdDate`, `updatedDate`, `companyId`) VALUES
(1, 4, 8, 1, NULL, '2019-07-25', '2019-07-25', 1);

-- --------------------------------------------------------

--
-- Table structure for table `TMSHolidays`
--

CREATE TABLE `TMSHolidays` (
  `holidayId` int(11) NOT NULL,
  `leavePeriodId` int(11) DEFAULT NULL,
  `holidayName` varchar(100) NOT NULL,
  `companyId` int(11) NOT NULL,
  `fromDate` date DEFAULT NULL,
  `toDate` date DEFAULT NULL,
  `day` int(11) DEFAULT NULL,
  `userId` int(11) NOT NULL,
  `updateUserId` int(11) DEFAULT NULL,
  `createdDate` date NOT NULL,
  `updatedDate` date DEFAULT NULL,
  `isMandatory` tinyint(1) DEFAULT '0',
  `year` varchar(4) DEFAULT NULL,
  `activeStatus` varchar(2) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `TMSLeaveCarryForward`
--

CREATE TABLE `TMSLeaveCarryForward` (
  `leaveCarryForwardId` int(11) NOT NULL,
  `leavePeriodId` int(11) DEFAULT NULL,
  `employeeId` int(11) DEFAULT NULL,
  `companyId` int(11) DEFAULT NULL,
  `leaveTypeMasterId` int(100) DEFAULT NULL,
  `leaveCount` int(20) NOT NULL,
  `activeStatus` varchar(2) DEFAULT NULL,
  `userId` int(11) DEFAULT NULL,
  `userIdUpdate` int(11) DEFAULT NULL,
  `dateCreated` date DEFAULT NULL,
  `dateUpdate` date DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `TMSLeaveEmailNotification`
--

CREATE TABLE `TMSLeaveEmailNotification` (
  `tmsLeaveEmailNotificationId` int(11) NOT NULL,
  `leaveEntryId` int(11) NOT NULL,
  `employeeId` int(11) NOT NULL,
  `emailId` varchar(250) DEFAULT NULL,
  `mobileNo` int(15) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `TMSLeaveEntries`
--

CREATE TABLE `TMSLeaveEntries` (
  `leaveId` int(11) NOT NULL,
  `companyId` int(11) NOT NULL,
  `leaveTypeId` int(11) NOT NULL,
  `employeeId` int(11) NOT NULL,
  `approvalId` int(11) DEFAULT NULL,
  `notificationId` int(11) DEFAULT NULL,
  `halfFullDay` varchar(2) DEFAULT NULL,
  `status` varchar(5) DEFAULT NULL,
  `fromDate` date NOT NULL,
  `toDate` date NOT NULL,
  `days` decimal(10,2) DEFAULT NULL,
  `isApproved` tinyint(1) DEFAULT NULL,
  `approvalRemark` text,
  `employeeRemark` text,
  `halfDayFor` varchar(30) DEFAULT NULL,
  `isRead` tinyint(1) DEFAULT NULL,
  `actionableDate` date DEFAULT NULL,
  `notifyEmployee` text,
  `userId` int(11) NOT NULL,
  `dateCreated` date NOT NULL,
  `userIdUpdate` int(11) NOT NULL,
  `dateUpdate` date NOT NULL,
  `cancleRemark` text,
  `appliedByEmployeeId` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `TMSLeaveEntriesDatewise`
--

CREATE TABLE `TMSLeaveEntriesDatewise` (
  `id` bigint(8) NOT NULL,
  `leaveId` int(11) NOT NULL,
  `employeeId` int(11) NOT NULL,
  `leaveDate` date NOT NULL,
  `leaveStatus` varchar(6) NOT NULL,
  `leaveNature` varchar(20) NOT NULL,
  `leaveFromToSandwitchDate` date DEFAULT NULL,
  `halfFullDay` varchar(6) DEFAULT NULL,
  `day` int(50) NOT NULL,
  `companyId` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `TMSLeavePeriod`
--

CREATE TABLE `TMSLeavePeriod` (
  `leavePeriodId` int(11) NOT NULL,
  `startDate` date NOT NULL,
  `endDate` date NOT NULL,
  `leavePeriodName` varchar(20) DEFAULT NULL,
  `activeStatus` varchar(2) DEFAULT NULL,
  `userId` int(11) NOT NULL,
  `dateCreated` date NOT NULL,
  `userIdUpdate` int(11) DEFAULT NULL,
  `dateUpdate` date DEFAULT NULL,
  `companyId` int(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `TMSLeaveRuleMaster`
--

CREATE TABLE `TMSLeaveRuleMaster` (
  `leaveRuleMasterId` int(11) NOT NULL,
  `ruleName` varchar(100) NOT NULL,
  `ruleCode` varchar(4) NOT NULL,
  `companyId` int(11) NOT NULL,
  `activeStatus` varchar(2) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `TMSLeaveRuleMaster`
--

INSERT INTO `TMSLeaveRuleMaster` (`leaveRuleMasterId`, `ruleName`, `ruleCode`, `companyId`, `activeStatus`) VALUES
(1, 'Set Weekly off as leave if weekly off is found between two leave.', 'WOA', 1, 'AC'),
(2, 'Set Weekly off as absent if weekly off is found between two leave.', 'HOA', 1, 'AC'),
(3, 'Set Weekly off as absent if weekly off is found between two absent.', 'WAF', 1, 'AC'),
(4, 'Set Public Holiday as leave if Public Holiday is found between two leave.', 'WRR', 1, 'AC'),
(5, 'Set Public Holiday as absent if public Holiday is found between two leave', 'WOP', 1, 'AC'),
(6, 'Set Public Holiday as absent if Public Holiday is found between two absent.', 'HOA', 1, 'AC');

-- --------------------------------------------------------

--
-- Table structure for table `TMSLeaveRules`
--

CREATE TABLE `TMSLeaveRules` (
  `leaveRuleId` int(11) NOT NULL,
  `leaveRuleHdId` int(11) DEFAULT NULL,
  `leaveRuleMasterId` int(11) NOT NULL,
  `days` int(3) DEFAULT NULL,
  `description` varchar(100) DEFAULT NULL,
  `activeStatus` varchar(2) DEFAULT NULL,
  `userId` int(11) DEFAULT NULL,
  `dateCreated` date DEFAULT NULL,
  `userIdUpdate` int(11) DEFAULT NULL,
  `dateUpdate` date DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `TMSLeaveRulesHd`
--

CREATE TABLE `TMSLeaveRulesHd` (
  `leaveRulesHdId` int(11) NOT NULL,
  `leavePeriodId` int(11) NOT NULL,
  `userId` int(11) NOT NULL,
  `userIdUpdate` int(11) NOT NULL,
  `dateCreated` date NOT NULL,
  `dateUpdate` date NOT NULL,
  `companyId` int(11) NOT NULL,
  `activeStatus` varchar(2) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `TMSLeaveType`
--

CREATE TABLE `TMSLeaveType` (
  `leaveTypeId` int(11) NOT NULL,
  `leavePeriodId` int(11) NOT NULL,
  `leaveSchemeId` int(11) NOT NULL,
  `companyId` int(11) DEFAULT NULL,
  `leaveTypeMasterId` int(11) NOT NULL,
  `leaveMode` varchar(2) NOT NULL,
  `yearlyLimit` int(11) DEFAULT NULL,
  `leaveFrequencyInMonth` int(11) DEFAULT NULL,
  `maxLeaveInMonth` int(11) DEFAULT NULL,
  `notice` int(11) DEFAULT NULL,
  `nature` varchar(255) DEFAULT NULL,
  `indexDays` int(11) DEFAULT NULL,
  `isWeekOffAsPL` tinyint(1) DEFAULT NULL,
  `weekOffAsPLCount` int(11) DEFAULT NULL,
  `carryForwardLimit` int(11) DEFAULT NULL,
  `encashLimit` int(11) DEFAULT NULL,
  `isLeaveInProbation` varchar(1) DEFAULT 'N',
  `activeStatus` varchar(2) DEFAULT NULL,
  `userId` int(11) DEFAULT NULL,
  `userIdUpdate` int(11) DEFAULT NULL,
  `dateCreated` date DEFAULT NULL,
  `dateUpdate` date DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `TMSLeaveTypeMaster`
--

CREATE TABLE `TMSLeaveTypeMaster` (
  `leaveId` int(11) NOT NULL,
  `leaveName` varchar(50) NOT NULL,
  `activeStatus` varchar(2) NOT NULL,
  `userId` int(11) NOT NULL,
  `userIdUpdate` int(11) NOT NULL,
  `dateCreated` date NOT NULL,
  `dateUpdate` date NOT NULL,
  `companyId` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `TMSLeaveTypeMaster`
--

INSERT INTO `TMSLeaveTypeMaster` (`leaveId`, `leaveName`, `activeStatus`, `userId`, `userIdUpdate`, `dateCreated`, `dateUpdate`, `companyId`) VALUES
(1, 'Casual leave', 'AC', 467, 467, '2019-01-01', '2019-01-01', 1),
(2, 'Sick Leave', 'AC', 467, 1, '2019-02-01', '2019-02-01', 1),
(3, 'Paid leave', 'AC', 1, 1, '2019-02-02', '2019-02-02', 1),
(6, 'LOP', 'AC', 1, 1, '2019-05-02', '2019-05-02', 1),
(7, 'Compensantory Off', 'AC', 1, 1, '2019-05-02', '2019-05-02', 1);

-- --------------------------------------------------------

--
-- Table structure for table `TMSShift`
--

CREATE TABLE `TMSShift` (
  `shiftId` int(11) NOT NULL,
  `shiftFName` varchar(50) NOT NULL,
  `startTime` varchar(50) NOT NULL,
  `endTime` varchar(50) NOT NULL,
  `fromTime` varchar(11) DEFAULT NULL,
  `toTime` varchar(11) DEFAULT NULL,
  `graceTime` varchar(50) DEFAULT NULL,
  `graceFrqInMonth` int(11) DEFAULT NULL,
  `shiftDuration` varchar(11) DEFAULT NULL,
  `effectiveDate` date DEFAULT NULL,
  `activeStatus` varchar(2) NOT NULL,
  `userId` int(11) NOT NULL,
  `updateUserId` int(11) DEFAULT NULL,
  `createdDate` date NOT NULL,
  `updatedDate` date DEFAULT NULL,
  `companyId` int(11) NOT NULL,
  `startPeriod` varchar(2) DEFAULT NULL,
  `endPeriod` varchar(2) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `TMSShift`
--

INSERT INTO `TMSShift` (`shiftId`, `shiftFName`, `startTime`, `endTime`, `fromTime`, `toTime`, `graceTime`, `graceFrqInMonth`, `shiftDuration`, `effectiveDate`, `activeStatus`, `userId`, `updateUserId`, `createdDate`, `updatedDate`, `companyId`, `startPeriod`, `endPeriod`) VALUES
(1, 'General', '10:00', '19:00', '10:00', '7:00', '10:10', 2, '9.0', '2019-04-01', 'AC', 1, NULL, '2019-07-25', NULL, 1, 'AM', 'PM');

-- --------------------------------------------------------

--
-- Table structure for table `TMSWeekOffPattern`
--

CREATE TABLE `TMSWeekOffPattern` (
  `patternId` int(11) NOT NULL,
  `patternName` varchar(100) NOT NULL,
  `day` varchar(100) NOT NULL,
  `userId` int(11) NOT NULL,
  `updateUserId` int(11) DEFAULT NULL,
  `createdDate` date NOT NULL,
  `updatedDate` date DEFAULT NULL,
  `companyId` int(11) NOT NULL,
  `activeStatus` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `TMSWeekOffPattern`
--

INSERT INTO `TMSWeekOffPattern` (`patternId`, `patternName`, `day`, `userId`, `updateUserId`, `createdDate`, `updatedDate`, `companyId`, `activeStatus`) VALUES
(1, 'General', 'SUN', 1, 1, '2019-07-25', '2019-07-30', 1, 'AC');

-- --------------------------------------------------------

--
-- Table structure for table `ToDoList`
--

CREATE TABLE `ToDoList` (
  `todoListId` int(11) NOT NULL,
  `task` varchar(300) DEFAULT NULL,
  `employeeId` int(11) NOT NULL,
  `activeStatus` varchar(11) DEFAULT NULL,
  `companyId` int(11) DEFAULT NULL,
  `userId` int(11) DEFAULT NULL,
  `dateCreated` date DEFAULT NULL,
  `userIdUpdate` int(11) DEFAULT NULL,
  `dateUpdate` date DEFAULT NULL,
  `dueDate` date DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `TokenMaster`
--

CREATE TABLE `TokenMaster` (
  `tokenId` int(11) NOT NULL,
  `userId` int(11) NOT NULL,
  `tokentypeId` int(11) NOT NULL,
  `tokenValue` varchar(500) DEFAULT NULL,
  `dateCreated` datetime NOT NULL,
  `createdBy` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `TokenTypeMaster`
--

CREATE TABLE `TokenTypeMaster` (
  `tokenTypeId` int(11) NOT NULL,
  `tokenType` varchar(250) NOT NULL,
  `dateCreated` datetime NOT NULL,
  `userId` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `TokenTypeMaster`
--

INSERT INTO `TokenTypeMaster` (`tokenTypeId`, `tokenType`, `dateCreated`, `userId`) VALUES
(1, 'AndroidFirebase', '2019-09-16 00:00:00', 1),
(2, 'WebFirebase', '2019-09-16 00:00:00', 1);

-- --------------------------------------------------------

--
-- Table structure for table `TransactionApprovedHd`
--

CREATE TABLE `TransactionApprovedHd` (
  `transactionApprovedHdId` int(11) NOT NULL,
  `employeeId` int(11) NOT NULL,
  `financialYear` varchar(22) NOT NULL,
  `status` varchar(22) NOT NULL,
  `approveId` int(11) DEFAULT NULL,
  `userId` int(11) NOT NULL,
  `dateCreated` datetime NOT NULL,
  `userIdUpdate` int(11) NOT NULL,
  `dateUpdate` datetime NOT NULL,
  `active` varchar(10) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `UserRoles`
--

CREATE TABLE `UserRoles` (
  `userRolesSrNo` int(11) NOT NULL,
  `userId` int(11) NOT NULL,
  `roleId` int(11) NOT NULL,
  `allowModi` char(1) DEFAULT 'N',
  `sUserId` int(11) NOT NULL,
  `dateCreated` datetime DEFAULT NULL,
  `userIdUpdate` int(11) DEFAULT NULL,
  `dateUpdate` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `UserRoles`
--

INSERT INTO `UserRoles` (`userRolesSrNo`, `userId`, `roleId`, `allowModi`, `sUserId`, `dateCreated`, `userIdUpdate`, `dateUpdate`) VALUES
(1, 1, 1, NULL, 1, '2018-02-09 00:00:00', NULL, NULL);

-- --------------------------------------------------------

--
-- Table structure for table `Users`
--

CREATE TABLE `Users` (
  `userId` int(11) NOT NULL,
  `branchId` int(11) DEFAULT NULL,
  `companyId` int(11) NOT NULL,
  `groupId` int(11) DEFAULT NULL,
  `nameOfUser` char(50) DEFAULT NULL,
  `loginName` varchar(100) DEFAULT NULL,
  `userAddressId` int(11) DEFAULT NULL,
  `emailOfUser` varchar(255) DEFAULT NULL,
  `userPassword` varchar(50) DEFAULT NULL,
  `changePassword` varchar(50) DEFAULT NULL,
  `userAttempts` int(2) DEFAULT '0',
  `userEmail` varchar(255) DEFAULT NULL,
  `userMobileNo` varchar(15) DEFAULT NULL,
  `allowModi` char(1) DEFAULT 'N',
  `SuserId` int(11) NOT NULL,
  `dateCreated` datetime DEFAULT NULL,
  `userIdUpdate` int(11) DEFAULT NULL,
  `dateUpdate` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `Users`
--

INSERT INTO `Users` (`userId`, `branchId`, `companyId`, `groupId`, `nameOfUser`, `loginName`, `userAddressId`, `emailOfUser`, `userPassword`, `changePassword`, `userAttempts`, `userEmail`, `userMobileNo`, `allowModi`, `SuserId`, `dateCreated`, `userIdUpdate`, `dateUpdate`) VALUES
(1, NULL, 1, NULL, 'Administrator', 'Administrator', NULL, 'hr@tencogroup.net', '7c222fb2927d828af22f592134e8932480637c0d', '7c222fb2927d828af22f592134e8932480637c0d', 0, NULL, '9893569668', 'N', 1, NULL, NULL, NULL);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `AdditionalUserObjects`
--
ALTER TABLE `AdditionalUserObjects`
  ADD PRIMARY KEY (`additionalUserObjectsId`),
  ADD KEY `userId` (`userId`),
  ADD KEY `objectId` (`objectId`);

--
-- Indexes for table `Address`
--
ALTER TABLE `Address`
  ADD PRIMARY KEY (`addressId`),
  ADD KEY `countryId` (`countryId`),
  ADD KEY `stateId` (`stateId`),
  ADD KEY `cityId` (`cityId`);

--
-- Indexes for table `ArearCalculation`
--
ALTER TABLE `ArearCalculation`
  ADD PRIMARY KEY (`arearCalculationId`),
  ADD KEY `arearId` (`arearId`);

--
-- Indexes for table `ArearMaster`
--
ALTER TABLE `ArearMaster`
  ADD PRIMARY KEY (`arearId`),
  ADD KEY `employeeId` (`employeeId`);

--
-- Indexes for table `ArrearPayOut`
--
ALTER TABLE `ArrearPayOut`
  ADD PRIMARY KEY (`processMonth`,`employeeId`,`payHeadId`),
  ADD KEY `employeeId` (`employeeId`);

--
-- Indexes for table `ArrearReportPayOut`
--
ALTER TABLE `ArrearReportPayOut`
  ADD PRIMARY KEY (`processMonth`,`employeeId`),
  ADD KEY `employeeId` (`employeeId`),
  ADD KEY `companyId` (`companyId`);

--
-- Indexes for table `Attendance`
--
ALTER TABLE `Attendance`
  ADD PRIMARY KEY (`employeeCode`,`processMonth`),
  ADD KEY `companyId` (`companyId`);

--
-- Indexes for table `AttendanceLogs`
--
ALTER TABLE `AttendanceLogs`
  ADD PRIMARY KEY (`attendanceLogId`),
  ADD UNIQUE KEY `UK_AttendanceLogs_attendanceLo` (`attendanceLogId`),
  ADD KEY `FK_AttendanceLogs_companyId` (`companyId`),
  ADD KEY `FK_AttendanceLogs_employeeId` (`employeeId`);

--
-- Indexes for table `Banks`
--
ALTER TABLE `Banks`
  ADD PRIMARY KEY (`bankId`),
  ADD UNIQUE KEY `UC_AccountNo` (`AccountNo`),
  ADD KEY `companyId` (`companyId`);

--
-- Indexes for table `BatchEmployee`
--
ALTER TABLE `BatchEmployee`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `BATCH_JOB_EXECUTION`
--
ALTER TABLE `BATCH_JOB_EXECUTION`
  ADD PRIMARY KEY (`JOB_EXECUTION_ID`),
  ADD KEY `JOB_INST_EXEC_FK` (`JOB_INSTANCE_ID`);

--
-- Indexes for table `BATCH_JOB_EXECUTION_CONTEXT`
--
ALTER TABLE `BATCH_JOB_EXECUTION_CONTEXT`
  ADD PRIMARY KEY (`JOB_EXECUTION_ID`);

--
-- Indexes for table `BATCH_JOB_EXECUTION_PARAMS`
--
ALTER TABLE `BATCH_JOB_EXECUTION_PARAMS`
  ADD KEY `JOB_EXEC_PARAMS_FK` (`JOB_EXECUTION_ID`);

--
-- Indexes for table `BATCH_JOB_EXECUTION_SEQ`
--
ALTER TABLE `BATCH_JOB_EXECUTION_SEQ`
  ADD UNIQUE KEY `UNIQUE_KEY_UN` (`UNIQUE_KEY`);

--
-- Indexes for table `BATCH_JOB_INSTANCE`
--
ALTER TABLE `BATCH_JOB_INSTANCE`
  ADD PRIMARY KEY (`JOB_INSTANCE_ID`),
  ADD UNIQUE KEY `JOB_INST_UN` (`JOB_NAME`,`JOB_KEY`);

--
-- Indexes for table `BATCH_JOB_SEQ`
--
ALTER TABLE `BATCH_JOB_SEQ`
  ADD UNIQUE KEY `UNIQUE_KEY_UN` (`UNIQUE_KEY`);

--
-- Indexes for table `BATCH_STEP_EXECUTION`
--
ALTER TABLE `BATCH_STEP_EXECUTION`
  ADD PRIMARY KEY (`STEP_EXECUTION_ID`),
  ADD KEY `JOB_EXEC_STEP_FK` (`JOB_EXECUTION_ID`);

--
-- Indexes for table `BATCH_STEP_EXECUTION_CONTEXT`
--
ALTER TABLE `BATCH_STEP_EXECUTION_CONTEXT`
  ADD PRIMARY KEY (`STEP_EXECUTION_ID`);

--
-- Indexes for table `BATCH_STEP_EXECUTION_SEQ`
--
ALTER TABLE `BATCH_STEP_EXECUTION_SEQ`
  ADD UNIQUE KEY `UNIQUE_KEY_UN` (`UNIQUE_KEY`);

--
-- Indexes for table `Bonus`
--
ALTER TABLE `Bonus`
  ADD PRIMARY KEY (`bonusId`),
  ADD UNIQUE KEY `financialYear` (`financialYear`,`gradesId`),
  ADD KEY `gradesId` (`gradesId`);

--
-- Indexes for table `Branch`
--
ALTER TABLE `Branch`
  ADD PRIMARY KEY (`branchId`),
  ADD KEY `groupId` (`groupId`),
  ADD KEY `companyId` (`companyId`),
  ADD KEY `branchAddressId` (`branchAddressId`);

--
-- Indexes for table `Candidate`
--
ALTER TABLE `Candidate`
  ADD PRIMARY KEY (`candidateId`),
  ADD KEY `companyId` (`companyId`),
  ADD KEY `permanentAddressId` (`permanentAddressId`),
  ADD KEY `presentAddressId` (`presentAddressId`),
  ADD KEY `referenceAddressId` (`referenceAddressId`),
  ADD KEY `departmentId` (`departmentId`),
  ADD KEY `designationId` (`designationId`),
  ADD KEY `projectId` (`projectId`),
  ADD KEY `clientId` (`clientId`),
  ADD KEY `cityId` (`cityId`),
  ADD KEY `shiftId` (`shiftId`),
  ADD KEY `patternId` (`patternId`);

--
-- Indexes for table `CandidateAddress`
--
ALTER TABLE `CandidateAddress`
  ADD PRIMARY KEY (`addressId`),
  ADD KEY `countryId` (`countryId`),
  ADD KEY `stateId` (`stateId`),
  ADD KEY `cityId` (`cityId`);

--
-- Indexes for table `CandidateEducation`
--
ALTER TABLE `CandidateEducation`
  ADD PRIMARY KEY (`educationId`),
  ADD UNIQUE KEY `candidateId` (`candidateId`,`qualificationId`);

--
-- Indexes for table `CandidateFamily`
--
ALTER TABLE `CandidateFamily`
  ADD PRIMARY KEY (`familyId`),
  ADD UNIQUE KEY `candidateId` (`candidateId`,`name`,`dateOfBirth`);

--
-- Indexes for table `CandidateIdProofs`
--
ALTER TABLE `CandidateIdProofs`
  ADD PRIMARY KEY (`candidateIdProofsId`),
  ADD UNIQUE KEY `UC_EmployeeIdProofs` (`candidateId`,`idTypeId`),
  ADD UNIQUE KEY `employeeId` (`candidateId`,`idTypeId`),
  ADD UNIQUE KEY `candidateId` (`candidateId`,`idTypeId`);

--
-- Indexes for table `CandidateLanguage`
--
ALTER TABLE `CandidateLanguage`
  ADD PRIMARY KEY (`candidateLanguageId`),
  ADD KEY `language` (`languageId`),
  ADD KEY `candidatePersonalId` (`candidatePersonalId`);

--
-- Indexes for table `CandidateNominee`
--
ALTER TABLE `CandidateNominee`
  ADD PRIMARY KEY (`candidateNomineeid`),
  ADD UNIQUE KEY `candidateId` (`candidateId`,`staturyHeadId`),
  ADD KEY `familyId` (`familyId`);

--
-- Indexes for table `CandidateOfficialInformation`
--
ALTER TABLE `CandidateOfficialInformation`
  ADD PRIMARY KEY (`candidateOfficialId`),
  ADD UNIQUE KEY `candidateId_2` (`candidateId`),
  ADD UNIQUE KEY `candidateId_3` (`candidateId`),
  ADD UNIQUE KEY `candidateId_4` (`candidateId`),
  ADD KEY `candidateId` (`candidateId`),
  ADD KEY `gradeId` (`gradeId`);

--
-- Indexes for table `CandidatePersonal`
--
ALTER TABLE `CandidatePersonal`
  ADD PRIMARY KEY (`candidatePersonalId`),
  ADD UNIQUE KEY `candidateId_2` (`candidateId`),
  ADD KEY `candidateId` (`candidateId`),
  ADD KEY `permanentAddressId` (`permanentAddressId`),
  ADD KEY `presentAddressId` (`presentAddressId`),
  ADD KEY `referenceAddressId` (`referenceAddressId`);

--
-- Indexes for table `CandidateProfessionalInformation`
--
ALTER TABLE `CandidateProfessionalInformation`
  ADD PRIMARY KEY (`candidateProfessionalInformationId`),
  ADD UNIQUE KEY `candidateId` (`candidateId`,`dateFrom`);

--
-- Indexes for table `CandidateSkills`
--
ALTER TABLE `CandidateSkills`
  ADD PRIMARY KEY (`candidateSkillsId`),
  ADD UNIQUE KEY `candidateId` (`candidateId`,`skillId`),
  ADD KEY `skillId` (`skillId`);

--
-- Indexes for table `CandidateStatuary`
--
ALTER TABLE `CandidateStatuary`
  ADD PRIMARY KEY (`candidateStatuaryId`),
  ADD KEY `candidateId` (`candidateId`);

--
-- Indexes for table `category`
--
ALTER TABLE `category`
  ADD PRIMARY KEY (`category_id`);

--
-- Indexes for table `City`
--
ALTER TABLE `City`
  ADD PRIMARY KEY (`cityId`),
  ADD KEY `countryId` (`countryId`),
  ADD KEY `stateId` (`stateId`);

--
-- Indexes for table `Clients`
--
ALTER TABLE `Clients`
  ADD PRIMARY KEY (`clientId`),
  ADD UNIQUE KEY `clientName+companyID` (`clientName`,`companyId`) USING BTREE,
  ADD UNIQUE KEY `UC_gstNo` (`gstNo`),
  ADD KEY `addressId` (`addressId`);

--
-- Indexes for table `Company`
--
ALTER TABLE `Company`
  ADD PRIMARY KEY (`companyId`),
  ADD KEY `groupId` (`groupId`),
  ADD KEY `registeredOfficeAddressId` (`registeredOfficeAddressId`);

--
-- Indexes for table `Country`
--
ALTER TABLE `Country`
  ADD PRIMARY KEY (`countryId`);

--
-- Indexes for table `Department`
--
ALTER TABLE `Department`
  ADD PRIMARY KEY (`departmentId`),
  ADD UNIQUE KEY `unique_index` (`companyId`,`departmentName`);

--
-- Indexes for table `DeptDesignationMapping`
--
ALTER TABLE `DeptDesignationMapping`
  ADD PRIMARY KEY (`deptDesgId`),
  ADD KEY `designationId` (`designationId`),
  ADD KEY `departmentId` (`departmentId`);

--
-- Indexes for table `Designation`
--
ALTER TABLE `Designation`
  ADD PRIMARY KEY (`designationId`),
  ADD KEY `departmentId` (`departmentId`);

--
-- Indexes for table `DeviceInfo`
--
ALTER TABLE `DeviceInfo`
  ADD PRIMARY KEY (`deviceId`),
  ADD UNIQUE KEY `deviceName` (`deviceName`),
  ADD UNIQUE KEY `serialNumber` (`serialNumber`);

--
-- Indexes for table `DeviceLogs`
--
ALTER TABLE `DeviceLogs`
  ADD PRIMARY KEY (`deviceLogId`);

--
-- Indexes for table `DeviceLogsInfo`
--
ALTER TABLE `DeviceLogsInfo`
  ADD PRIMARY KEY (`deviceLogId`);

--
-- Indexes for table `DrowpdownHd`
--
ALTER TABLE `DrowpdownHd`
  ADD PRIMARY KEY (`drowpdownId`),
  ADD UNIQUE KEY `UC_drowpdownName` (`drowpdownName`),
  ADD KEY `companyId` (`companyId`);

--
-- Indexes for table `DrowpdownList`
--
ALTER TABLE `DrowpdownList`
  ADD PRIMARY KEY (`drowpdownListId`),
  ADD KEY `drowpdownId` (`drowpdownId`);

--
-- Indexes for table `EmailConfiguration`
--
ALTER TABLE `EmailConfiguration`
  ADD PRIMARY KEY (`emailConfigureId`);

--
-- Indexes for table `EmailNotificationMaster`
--
ALTER TABLE `EmailNotificationMaster`
  ADD PRIMARY KEY (`mailId`),
  ADD KEY `emailNotification` (`emailConfigureId`);

--
-- Indexes for table `Employee`
--
ALTER TABLE `Employee`
  ADD PRIMARY KEY (`employeeId`),
  ADD UNIQUE KEY `UcEmployee` (`employeeCode`),
  ADD KEY `companyId` (`companyId`),
  ADD KEY `permanentAddressId` (`permanentAddressId`),
  ADD KEY `presentAddressId` (`presentAddressId`),
  ADD KEY `referenceAddressId` (`referenceAddressId`),
  ADD KEY `departmentId` (`departmentId`),
  ADD KEY `designationId` (`designationId`),
  ADD KEY `projectId` (`projectId`),
  ADD KEY `clientId` (`clientId`),
  ADD KEY `cityId` (`cityId`),
  ADD KEY `patternId` (`patternId`),
  ADD KEY `leaveSchemeId` (`leaveSchemeId`);

--
-- Indexes for table `EmployeeAssets`
--
ALTER TABLE `EmployeeAssets`
  ADD PRIMARY KEY (`employeeAssetsId`),
  ADD KEY `EmployeeAssets_ibfk_1` (`employeeId`),
  ADD KEY `itemId` (`itemId`);

--
-- Indexes for table `EmployeeBank`
--
ALTER TABLE `EmployeeBank`
  ADD PRIMARY KEY (`EmployeeBankId`),
  ADD KEY `employeeId` (`employeeId`);

--
-- Indexes for table `EmployeeDocuments`
--
ALTER TABLE `EmployeeDocuments`
  ADD PRIMARY KEY (`employeeDocumentsId`),
  ADD UNIQUE KEY `UC_EmployeeDocuments` (`employeeId`,`DocumentsId`);

--
-- Indexes for table `EmployeeEducation`
--
ALTER TABLE `EmployeeEducation`
  ADD PRIMARY KEY (`educationId`);

--
-- Indexes for table `EmployeeFamily`
--
ALTER TABLE `EmployeeFamily`
  ADD PRIMARY KEY (`familyId`),
  ADD KEY `employeeId` (`employeeId`);

--
-- Indexes for table `EmployeeHistory`
--
ALTER TABLE `EmployeeHistory`
  ADD PRIMARY KEY (`employeeHistoryId`);

--
-- Indexes for table `EmployeeIdProofs`
--
ALTER TABLE `EmployeeIdProofs`
  ADD PRIMARY KEY (`employeeIdProofsId`),
  ADD UNIQUE KEY `UC_EmployeeIdProofs` (`employeeId`,`idTypeId`),
  ADD UNIQUE KEY `employeeId` (`employeeId`,`idTypeId`);

--
-- Indexes for table `EmployeeIdProofsHistory`
--
ALTER TABLE `EmployeeIdProofsHistory`
  ADD PRIMARY KEY (`employeeIdProofsHistoryId`);

--
-- Indexes for table `EmployeeLanguage`
--
ALTER TABLE `EmployeeLanguage`
  ADD PRIMARY KEY (`employeeLanguageId`),
  ADD KEY `employeeId` (`employeeId`),
  ADD KEY `languageId` (`languageId`);

--
-- Indexes for table `EmployeeNominee`
--
ALTER TABLE `EmployeeNominee`
  ADD PRIMARY KEY (`employeeNomineeid`),
  ADD KEY `familyId` (`familyId`),
  ADD KEY `employeeId` (`employeeId`);

--
-- Indexes for table `EmployeeOpeningLeaveMaster`
--
ALTER TABLE `EmployeeOpeningLeaveMaster`
  ADD PRIMARY KEY (`empOpeningId`),
  ADD KEY `leavePeriodId` (`leavePeriodId`),
  ADD KEY `companyId` (`companyId`),
  ADD KEY `leaveTypeMasterId` (`leaveTypeMasterId`),
  ADD KEY `employeeId` (`employeeId`);

--
-- Indexes for table `EmployeeSkills`
--
ALTER TABLE `EmployeeSkills`
  ADD PRIMARY KEY (`employeeSkillsId`),
  ADD UNIQUE KEY `UC_EmployeeSkills` (`employeeId`,`skillId`),
  ADD KEY `skillId` (`skillId`);

--
-- Indexes for table `EmployeeStatuary`
--
ALTER TABLE `EmployeeStatuary`
  ADD PRIMARY KEY (`statuaryId`),
  ADD KEY `familyId` (`familyId`),
  ADD KEY `employeeId` (`employeeId`);

--
-- Indexes for table `EntryInAuditTable`
--
ALTER TABLE `EntryInAuditTable`
  ADD PRIMARY KEY (`technicalTableName`);

--
-- Indexes for table `Epf`
--
ALTER TABLE `Epf`
  ADD PRIMARY KEY (`epfId`),
  ADD KEY `companyId` (`companyId`);

--
-- Indexes for table `Esi`
--
ALTER TABLE `Esi`
  ADD PRIMARY KEY (`esiId`),
  ADD KEY `companyId` (`companyId`);

--
-- Indexes for table `EsiCycle`
--
ALTER TABLE `EsiCycle`
  ADD PRIMARY KEY (`esiCycleId`),
  ADD KEY `esiId` (`esiId`);

--
-- Indexes for table `events`
--
ALTER TABLE `events`
  ADD PRIMARY KEY (`event_id`);

--
-- Indexes for table `finalSettlement`
--
ALTER TABLE `finalSettlement`
  ADD PRIMARY KEY (`finalSettlementId`);

--
-- Indexes for table `FinalSettlementReport`
--
ALTER TABLE `FinalSettlementReport`
  ADD PRIMARY KEY (`finalReportId`);

--
-- Indexes for table `FinancialMonth`
--
ALTER TABLE `FinancialMonth`
  ADD PRIMARY KEY (`financialMonthId`);

--
-- Indexes for table `FinancialYear`
--
ALTER TABLE `FinancialYear`
  ADD PRIMARY KEY (`financialYearId`),
  ADD KEY `companyId` (`companyId`);

--
-- Indexes for table `Grades`
--
ALTER TABLE `Grades`
  ADD PRIMARY KEY (`gradesId`),
  ADD UNIQUE KEY `gradesName` (`gradesName`),
  ADD KEY `companyId` (`companyId`);

--
-- Indexes for table `GradesPayDefinition`
--
ALTER TABLE `GradesPayDefinition`
  ADD PRIMARY KEY (`gradesPayId`),
  ADD UNIQUE KEY `gradesId` (`gradesId`,`payHeadId`),
  ADD KEY `payHeadId` (`payHeadId`);

--
-- Indexes for table `Gratuaty`
--
ALTER TABLE `Gratuaty`
  ADD PRIMARY KEY (`graduityId`),
  ADD KEY `companyId` (`companyId`);

--
-- Indexes for table `Groupg`
--
ALTER TABLE `Groupg`
  ADD PRIMARY KEY (`groupId`),
  ADD KEY `groupAddressId` (`groupAddressId`);

--
-- Indexes for table `HistoryAttendance`
--
ALTER TABLE `HistoryAttendance`
  ADD PRIMARY KEY (`processMonth`,`employeeId`);

--
-- Indexes for table `HoldSalary`
--
ALTER TABLE `HoldSalary`
  ADD PRIMARY KEY (`holdSalaryId`),
  ADD UNIQUE KEY `payrollMonth` (`payrollMonth`,`employeeId`),
  ADD KEY `HoldSalary_ibfk_1` (`employeeId`);

--
-- Indexes for table `InterestingThoughts`
--
ALTER TABLE `InterestingThoughts`
  ADD PRIMARY KEY (`interestingThoughtsId`);

--
-- Indexes for table `Item`
--
ALTER TABLE `Item`
  ADD PRIMARY KEY (`itemId`);

--
-- Indexes for table `Kra`
--
ALTER TABLE `Kra`
  ADD PRIMARY KEY (`kraId`),
  ADD UNIQUE KEY `UC_kraName` (`kraName`),
  ADD KEY `departmentId` (`departmentId`),
  ADD KEY `designationId` (`designationId`);

--
-- Indexes for table `LabourWelfareFund`
--
ALTER TABLE `LabourWelfareFund`
  ADD PRIMARY KEY (`labourWelfareFundHeadId`),
  ADD KEY `stateId` (`stateId`);

--
-- Indexes for table `LabourWelfareFundInfo`
--
ALTER TABLE `LabourWelfareFundInfo`
  ADD PRIMARY KEY (`labourWelfareFundInfoId`);

--
-- Indexes for table `Language`
--
ALTER TABLE `Language`
  ADD PRIMARY KEY (`languageId`);

--
-- Indexes for table `LeaveSchemeMaster`
--
ALTER TABLE `LeaveSchemeMaster`
  ADD PRIMARY KEY (`leaveSchemeId`);

--
-- Indexes for table `LoanEMI`
--
ALTER TABLE `LoanEMI`
  ADD PRIMARY KEY (`emiNo`),
  ADD KEY `transactionNo` (`transactionNo`);

--
-- Indexes for table `LoanIssue`
--
ALTER TABLE `LoanIssue`
  ADD PRIMARY KEY (`transactionNo`),
  ADD KEY `employeeId` (`employeeId`),
  ADD KEY `LoanIssue_ibfk_2` (`companyId`);

--
-- Indexes for table `Mail`
--
ALTER TABLE `Mail`
  ADD PRIMARY KEY (`mailId`);

--
-- Indexes for table `MandatoryInfo`
--
ALTER TABLE `MandatoryInfo`
  ADD PRIMARY KEY (`mandatoryInfoId`),
  ADD UNIQUE KEY `docName` (`docName`,`docCode`,`companyId`),
  ADD UNIQUE KEY `docName_2` (`docName`,`docCode`,`companyId`),
  ADD KEY `companyId` (`companyId`);

--
-- Indexes for table `MandatoryInfoCheck`
--
ALTER TABLE `MandatoryInfoCheck`
  ADD PRIMARY KEY (`mandatoryInfoCheckId`),
  ADD KEY `empId` (`employeeId`);

--
-- Indexes for table `MassCommunication`
--
ALTER TABLE `MassCommunication`
  ADD PRIMARY KEY (`massCommunicationId`),
  ADD KEY `departmentId` (`departmentId`);

--
-- Indexes for table `MasterBook`
--
ALTER TABLE `MasterBook`
  ADD PRIMARY KEY (`bookId`),
  ADD KEY `companyId` (`companyId`);

--
-- Indexes for table `MasterBookType`
--
ALTER TABLE `MasterBookType`
  ADD PRIMARY KEY (`bookTypeId`),
  ADD KEY `companyId` (`companyId`);

--
-- Indexes for table `MenuMaster`
--
ALTER TABLE `MenuMaster`
  ADD PRIMARY KEY (`menuId`),
  ADD KEY `companyId` (`companyId`);

--
-- Indexes for table `Notification`
--
ALTER TABLE `Notification`
  ADD PRIMARY KEY (`notificationId`),
  ADD UNIQUE KEY `notificationType` (`notificationType`),
  ADD KEY `mailId` (`mailId`);

--
-- Indexes for table `ObjectsInSystem`
--
ALTER TABLE `ObjectsInSystem`
  ADD PRIMARY KEY (`objectId`);

--
-- Indexes for table `ObjectsInSystemInRole`
--
ALTER TABLE `ObjectsInSystemInRole`
  ADD PRIMARY KEY (`ObjectsInSystemInRoleId`),
  ADD KEY `roleId` (`roleId`),
  ADD KEY `objectId` (`objectId`);

--
-- Indexes for table `OneTimeEarningDeduction`
--
ALTER TABLE `OneTimeEarningDeduction`
  ADD PRIMARY KEY (`Id`),
  ADD KEY `companyId` (`companyId`),
  ADD KEY `employeeId` (`employeeId`);

--
-- Indexes for table `OtherIncome`
--
ALTER TABLE `OtherIncome`
  ADD PRIMARY KEY (`otherIncomeId`),
  ADD KEY `employeeId` (`employeeId`);

--
-- Indexes for table `Overtime`
--
ALTER TABLE `Overtime`
  ADD PRIMARY KEY (`overtimeId`),
  ADD KEY `companyId` (`companyId`);

--
-- Indexes for table `PayHeads`
--
ALTER TABLE `PayHeads`
  ADD PRIMARY KEY (`payHeadId`),
  ADD UNIQUE KEY `payHeadName` (`payHeadName`),
  ADD KEY `companyId` (`companyId`);

--
-- Indexes for table `PayOut`
--
ALTER TABLE `PayOut`
  ADD PRIMARY KEY (`processMonth`,`employeeId`,`payHeadId`),
  ADD KEY `employeeId` (`employeeId`),
  ADD KEY `payHeadId` (`payHeadId`);

--
-- Indexes for table `PayRegister`
--
ALTER TABLE `PayRegister`
  ADD PRIMARY KEY (`payRegisterId`),
  ADD KEY `employeeId` (`employeeId`),
  ADD KEY `departmentId` (`departmentId`),
  ADD KEY `payRegisterHdId` (`payRegisterHdId`);

--
-- Indexes for table `PayRegisterHd`
--
ALTER TABLE `PayRegisterHd`
  ADD PRIMARY KEY (`payRegisterHdId`);

--
-- Indexes for table `PayrollControl`
--
ALTER TABLE `PayrollControl`
  ADD PRIMARY KEY (`controlId`),
  ADD KEY `financialYearId` (`financialYearId`);

--
-- Indexes for table `PayRollLock`
--
ALTER TABLE `PayRollLock`
  ADD PRIMARY KEY (`departmentId`,`processMonth`),
  ADD KEY `companyId` (`companyId`);

--
-- Indexes for table `PayStructure`
--
ALTER TABLE `PayStructure`
  ADD PRIMARY KEY (`payStructureId`),
  ADD KEY `payStructureHdId` (`payStructureHdId`),
  ADD KEY `payHeadId` (`payHeadId`);

--
-- Indexes for table `PayStructureHd`
--
ALTER TABLE `PayStructureHd`
  ADD PRIMARY KEY (`payStructureHdId`),
  ADD UNIQUE KEY `employeeId` (`employeeId`,`grossPay`,`effectiveDate`),
  ADD KEY `gradesId` (`gradesId`);

--
-- Indexes for table `PreviousEmployerIncome`
--
ALTER TABLE `PreviousEmployerIncome`
  ADD PRIMARY KEY (`previousEmployerIncomeId`);

--
-- Indexes for table `PreviousEmployerIncomeFile`
--
ALTER TABLE `PreviousEmployerIncomeFile`
  ADD PRIMARY KEY (`PreviousEmployerIncomeFileId`);

--
-- Indexes for table `PreviousEmployerIncomeTds`
--
ALTER TABLE `PreviousEmployerIncomeTds`
  ADD PRIMARY KEY (`previousEmployerIncomeTdsId`),
  ADD KEY `previousEmployerIncomeId` (`previousEmployerIncomeId`),
  ADD KEY `employeeId` (`employeeId`);

--
-- Indexes for table `ProfessionalInformation`
--
ALTER TABLE `ProfessionalInformation`
  ADD PRIMARY KEY (`historyId`),
  ADD KEY `employeeId` (`employeeId`);

--
-- Indexes for table `ProfessionalTax`
--
ALTER TABLE `ProfessionalTax`
  ADD PRIMARY KEY (`professionalHeadId`),
  ADD KEY `stateId` (`stateId`);

--
-- Indexes for table `ProfessionalTaxInfo`
--
ALTER TABLE `ProfessionalTaxInfo`
  ADD PRIMARY KEY (`ProfessionalTaxInfoId`);

--
-- Indexes for table `Project`
--
ALTER TABLE `Project`
  ADD PRIMARY KEY (`projectId`),
  ADD UNIQUE KEY `UC_projectName` (`projectName`),
  ADD UNIQUE KEY `departmentId` (`departmentId`,`projectName`),
  ADD KEY `clientId` (`clientId`);

--
-- Indexes for table `Provision`
--
ALTER TABLE `Provision`
  ADD PRIMARY KEY (`provisionId`),
  ADD KEY `employeeId` (`employeeId`),
  ADD KEY `departmentId` (`departmentId`);

--
-- Indexes for table `PunchTimeDetail`
--
ALTER TABLE `PunchTimeDetail`
  ADD PRIMARY KEY (`punchTimeDetailsId`);

--
-- Indexes for table `PunchTimeDetails`
--
ALTER TABLE `PunchTimeDetails`
  ADD PRIMARY KEY (`PunchTimeDetailsId`);

--
-- Indexes for table `ReportPayOut`
--
ALTER TABLE `ReportPayOut`
  ADD PRIMARY KEY (`processMonth`,`employeeId`),
  ADD KEY `employeeId` (`employeeId`),
  ADD KEY `companyId` (`companyId`);

--
-- Indexes for table `RoleMaster`
--
ALTER TABLE `RoleMaster`
  ADD PRIMARY KEY (`roleId`);

--
-- Indexes for table `RoleSubmenuActionMaster`
--
ALTER TABLE `RoleSubmenuActionMaster`
  ADD PRIMARY KEY (`roleSubmenuActionId`),
  ADD KEY `submenuActionId` (`submenuActionId`),
  ADD KEY `roleId` (`roleId`);

--
-- Indexes for table `Separation`
--
ALTER TABLE `Separation`
  ADD PRIMARY KEY (`separationId`),
  ADD KEY `employeeId` (`employeeId`),
  ADD KEY `approvalId` (`approvalId`),
  ADD KEY `companyId` (`companyId`);

--
-- Indexes for table `Skills`
--
ALTER TABLE `Skills`
  ADD PRIMARY KEY (`skillId`),
  ADD UNIQUE KEY `UC_skillName` (`skillName`),
  ADD UNIQUE KEY `skillName` (`skillName`,`departmentId`),
  ADD KEY `departmentId` (`departmentId`);

--
-- Indexes for table `State`
--
ALTER TABLE `State`
  ADD PRIMARY KEY (`stateId`),
  ADD KEY `countryId` (`countryId`);

--
-- Indexes for table `SubmenuActionMaster`
--
ALTER TABLE `SubmenuActionMaster`
  ADD PRIMARY KEY (`submenuActionId`),
  ADD UNIQUE KEY `unique` (`uniqueCode`),
  ADD KEY `submenuId` (`submenuId`);

--
-- Indexes for table `SubMenuMaster`
--
ALTER TABLE `SubMenuMaster`
  ADD PRIMARY KEY (`subMenuId`),
  ADD KEY `menuId` (`menuId`);

--
-- Indexes for table `TdsApproved`
--
ALTER TABLE `TdsApproved`
  ADD PRIMARY KEY (`transactionId`),
  ADD KEY `tdsGroupId` (`tdsGroupId`),
  ADD KEY `transactionApprovedHdId` (`transactionApprovedHdId`);

--
-- Indexes for table `TdsCityMaster`
--
ALTER TABLE `TdsCityMaster`
  ADD PRIMARY KEY (`tdsCityMasterId`);

--
-- Indexes for table `TdsDeduction`
--
ALTER TABLE `TdsDeduction`
  ADD PRIMARY KEY (`tdsDeductionId`),
  ADD KEY `employeeId` (`employeeId`),
  ADD KEY `finencialYearId` (`financialYearId`),
  ADD KEY `companyId` (`companyId`);

--
-- Indexes for table `TdsGroup`
--
ALTER TABLE `TdsGroup`
  ADD PRIMARY KEY (`tdsGroupId`),
  ADD KEY `companyId` (`companyId`);

--
-- Indexes for table `TdsGroupMaster`
--
ALTER TABLE `TdsGroupMaster`
  ADD PRIMARY KEY (`tdsGroupMasterId`);

--
-- Indexes for table `TdsGroupSetup`
--
ALTER TABLE `TdsGroupSetup`
  ADD PRIMARY KEY (`tdsGroupId`),
  ADD KEY `financialYearId` (`financialYearId`),
  ADD KEY `tdsGroupMasterId` (`tdsGroupMasterId`);

--
-- Indexes for table `TdsHistory`
--
ALTER TABLE `TdsHistory`
  ADD PRIMARY KEY (`tdsHistoryId`),
  ADD KEY `employeeId` (`employeeId`);

--
-- Indexes for table `TdsHouseRentFileInfo`
--
ALTER TABLE `TdsHouseRentFileInfo`
  ADD PRIMARY KEY (`tdsHouseRentFileInfoId`),
  ADD KEY `tdsHouseRentInfoId` (`tdsHouseRentInfoId`);

--
-- Indexes for table `TdsHouseRentInfo`
--
ALTER TABLE `TdsHouseRentInfo`
  ADD PRIMARY KEY (`tdsHouseRentInfoId`),
  ADD KEY `tdsTransactionId` (`tdsTransactionId`);

--
-- Indexes for table `TdsPayroll`
--
ALTER TABLE `TdsPayroll`
  ADD PRIMARY KEY (`transactionId`),
  ADD KEY `tdsSLabId` (`tdsSLabId`),
  ADD KEY `transactionHdId` (`transactionHdId`);

--
-- Indexes for table `TdsPayrollHd`
--
ALTER TABLE `TdsPayrollHd`
  ADD PRIMARY KEY (`transactionHdId`),
  ADD KEY `employeeId` (`employeeId`);

--
-- Indexes for table `TdsSection`
--
ALTER TABLE `TdsSection`
  ADD PRIMARY KEY (`tdsSectionId`),
  ADD KEY `TdsSection_ibfk_1` (`tdsGroupId`);

--
-- Indexes for table `TdsSectionSetup`
--
ALTER TABLE `TdsSectionSetup`
  ADD PRIMARY KEY (`tdsSectionId`),
  ADD KEY `tdsGroupId` (`tdsGroupId`);

--
-- Indexes for table `TdsSlab`
--
ALTER TABLE `TdsSlab`
  ADD PRIMARY KEY (`tdsSLabId`),
  ADD KEY `tdsSLabHdId` (`tdsSLabHdId`);

--
-- Indexes for table `TdsSlabHd`
--
ALTER TABLE `TdsSlabHd`
  ADD PRIMARY KEY (`tdsSLabHdId`),
  ADD KEY `companyId` (`companyId`),
  ADD KEY `tdsSlabMasterId` (`tdsSlabMasterId`),
  ADD KEY `finencialYearId` (`finencialYearId`);

--
-- Indexes for table `TdsSlabMaster`
--
ALTER TABLE `TdsSlabMaster`
  ADD PRIMARY KEY (`tdsSlabMasterId`);

--
-- Indexes for table `TdsStandardExemption`
--
ALTER TABLE `TdsStandardExemption`
  ADD PRIMARY KEY (`tdsStandardExemptionId`),
  ADD UNIQUE KEY `financialYear` (`financialYearId`);

--
-- Indexes for table `TdsSummary`
--
ALTER TABLE `TdsSummary`
  ADD PRIMARY KEY (`tdsSummaryId`),
  ADD KEY `employeeId` (`employeeId`);

--
-- Indexes for table `TdsSummaryChange`
--
ALTER TABLE `TdsSummaryChange`
  ADD PRIMARY KEY (`tdsSummaryChangeId`),
  ADD KEY `employeeId` (`employeeId`);

--
-- Indexes for table `TdsTransaction`
--
ALTER TABLE `TdsTransaction`
  ADD PRIMARY KEY (`tdsTransactionId`),
  ADD KEY `employeeId` (`employeeId`),
  ADD KEY `tdsGroupSetupId` (`tdsGroupId`),
  ADD KEY `tdsSectionSetupId` (`tdsSectionId`),
  ADD KEY `financialYearId` (`financialYearId`) USING BTREE;

--
-- Indexes for table `TdsTransactionFile`
--
ALTER TABLE `TdsTransactionFile`
  ADD PRIMARY KEY (`TdsTransactionFileId`),
  ADD KEY `employeeId` (`employeeId`);

--
-- Indexes for table `TdsTransactionFileInfo`
--
ALTER TABLE `TdsTransactionFileInfo`
  ADD PRIMARY KEY (`tdsTransactionFileInfoId`),
  ADD KEY `tdsTransactionId` (`tdsTransactionId`);

--
-- Indexes for table `TempEmployeeNaminee`
--
ALTER TABLE `TempEmployeeNaminee`
  ADD PRIMARY KEY (`employeeCode`),
  ADD UNIQUE KEY `employeeCode` (`employeeCode`);

--
-- Indexes for table `TicketDesc`
--
ALTER TABLE `TicketDesc`
  ADD PRIMARY KEY (`ticketDescId`),
  ADD KEY `ticketRaisingHDId` (`ticketRaisingHDId`),
  ADD KEY `employeeId` (`employeeId`);

--
-- Indexes for table `TicketRaisingHD`
--
ALTER TABLE `TicketRaisingHD`
  ADD PRIMARY KEY (`ticketRaisingHDId`),
  ADD KEY `ticketTypeId` (`ticketTypeId`),
  ADD KEY `employeeId` (`employeeId`),
  ADD KEY `FK_CompanyId` (`companyID`);

--
-- Indexes for table `TicketType`
--
ALTER TABLE `TicketType`
  ADD PRIMARY KEY (`ticketTypeId`),
  ADD KEY `companyId` (`companyId`);

--
-- Indexes for table `TMSARRequest`
--
ALTER TABLE `TMSARRequest`
  ADD PRIMARY KEY (`arID`),
  ADD KEY `employeeId` (`employeeId`),
  ADD KEY `approvalId` (`approvalId`),
  ADD KEY `companyId` (`companyId`);

--
-- Indexes for table `TMSCompensantoryOff`
--
ALTER TABLE `TMSCompensantoryOff`
  ADD PRIMARY KEY (`tmsCompensantoryOffId`);

--
-- Indexes for table `TMSHalfDayRule`
--
ALTER TABLE `TMSHalfDayRule`
  ADD PRIMARY KEY (`halfDayRuleId`),
  ADD KEY `FK_HailfDayRule_Company` (`companyId`);

--
-- Indexes for table `TMSHolidays`
--
ALTER TABLE `TMSHolidays`
  ADD PRIMARY KEY (`holidayId`),
  ADD KEY `FK_Holidays_Company_Id` (`companyId`),
  ADD KEY `leavePeriodId` (`leavePeriodId`);

--
-- Indexes for table `TMSLeaveCarryForward`
--
ALTER TABLE `TMSLeaveCarryForward`
  ADD PRIMARY KEY (`leaveCarryForwardId`),
  ADD KEY `leavePeriodId` (`leavePeriodId`),
  ADD KEY `leaveTypeMasterId` (`leaveTypeMasterId`);

--
-- Indexes for table `TMSLeaveEmailNotification`
--
ALTER TABLE `TMSLeaveEmailNotification`
  ADD PRIMARY KEY (`tmsLeaveEmailNotificationId`),
  ADD KEY `employeeId` (`employeeId`),
  ADD KEY `leaveEntryId` (`leaveEntryId`);

--
-- Indexes for table `TMSLeaveEntries`
--
ALTER TABLE `TMSLeaveEntries`
  ADD PRIMARY KEY (`leaveId`),
  ADD KEY `FK_LeaveEntries_leaveTypeId` (`leaveTypeId`),
  ADD KEY `FK_LeaveEntries_notificationId` (`notificationId`),
  ADD KEY `companyId` (`companyId`),
  ADD KEY `approvalId` (`approvalId`),
  ADD KEY `employeeId` (`employeeId`),
  ADD KEY `appliedByEmployeeId` (`appliedByEmployeeId`);

--
-- Indexes for table `TMSLeaveEntriesDatewise`
--
ALTER TABLE `TMSLeaveEntriesDatewise`
  ADD PRIMARY KEY (`id`),
  ADD KEY `leaveId` (`leaveId`),
  ADD KEY `employeeId` (`employeeId`);

--
-- Indexes for table `TMSLeavePeriod`
--
ALTER TABLE `TMSLeavePeriod`
  ADD PRIMARY KEY (`leavePeriodId`);

--
-- Indexes for table `TMSLeaveRuleMaster`
--
ALTER TABLE `TMSLeaveRuleMaster`
  ADD PRIMARY KEY (`leaveRuleMasterId`);

--
-- Indexes for table `TMSLeaveRules`
--
ALTER TABLE `TMSLeaveRules`
  ADD PRIMARY KEY (`leaveRuleId`),
  ADD KEY `leaveRuleHdId` (`leaveRuleHdId`),
  ADD KEY `leaveRuleMasterId` (`leaveRuleMasterId`);

--
-- Indexes for table `TMSLeaveRulesHd`
--
ALTER TABLE `TMSLeaveRulesHd`
  ADD PRIMARY KEY (`leaveRulesHdId`);

--
-- Indexes for table `TMSLeaveType`
--
ALTER TABLE `TMSLeaveType`
  ADD PRIMARY KEY (`leaveTypeId`),
  ADD KEY `companyId` (`companyId`),
  ADD KEY `leavePeriodId` (`leavePeriodId`);

--
-- Indexes for table `TMSLeaveTypeMaster`
--
ALTER TABLE `TMSLeaveTypeMaster`
  ADD PRIMARY KEY (`leaveId`);

--
-- Indexes for table `TMSShift`
--
ALTER TABLE `TMSShift`
  ADD PRIMARY KEY (`shiftId`),
  ADD KEY `FK_Shift_Company_ID` (`companyId`);

--
-- Indexes for table `TMSWeekOffPattern`
--
ALTER TABLE `TMSWeekOffPattern`
  ADD PRIMARY KEY (`patternId`),
  ADD KEY `FK_WOP_Company_ID` (`companyId`);

--
-- Indexes for table `ToDoList`
--
ALTER TABLE `ToDoList`
  ADD PRIMARY KEY (`todoListId`);

--
-- Indexes for table `TokenMaster`
--
ALTER TABLE `TokenMaster`
  ADD PRIMARY KEY (`tokenId`),
  ADD KEY `tokentypeId` (`tokentypeId`),
  ADD KEY `userId` (`userId`);

--
-- Indexes for table `TokenTypeMaster`
--
ALTER TABLE `TokenTypeMaster`
  ADD PRIMARY KEY (`tokenTypeId`);

--
-- Indexes for table `TransactionApprovedHd`
--
ALTER TABLE `TransactionApprovedHd`
  ADD PRIMARY KEY (`transactionApprovedHdId`),
  ADD KEY `employeeId` (`employeeId`),
  ADD KEY `approveId` (`approveId`);

--
-- Indexes for table `UserRoles`
--
ALTER TABLE `UserRoles`
  ADD PRIMARY KEY (`userRolesSrNo`),
  ADD KEY `userId` (`userId`),
  ADD KEY `roleId` (`roleId`);

--
-- Indexes for table `Users`
--
ALTER TABLE `Users`
  ADD PRIMARY KEY (`userId`),
  ADD UNIQUE KEY `nameOfUser` (`nameOfUser`),
  ADD UNIQUE KEY `nameOfUser_2` (`nameOfUser`),
  ADD UNIQUE KEY `nameOfUser_3` (`nameOfUser`),
  ADD UNIQUE KEY `nameOfUser_4` (`nameOfUser`),
  ADD KEY `userAddressId` (`userAddressId`),
  ADD KEY `groupId` (`groupId`),
  ADD KEY `companyId` (`companyId`),
  ADD KEY `branchId` (`branchId`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `AdditionalUserObjects`
--
ALTER TABLE `AdditionalUserObjects`
  MODIFY `additionalUserObjectsId` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `Address`
--
ALTER TABLE `Address`
  MODIFY `addressId` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;
--
-- AUTO_INCREMENT for table `ArearCalculation`
--
ALTER TABLE `ArearCalculation`
  MODIFY `arearCalculationId` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `ArearMaster`
--
ALTER TABLE `ArearMaster`
  MODIFY `arearId` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `AttendanceLogs`
--
ALTER TABLE `AttendanceLogs`
  MODIFY `attendanceLogId` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `Banks`
--
ALTER TABLE `Banks`
  MODIFY `bankId` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `Bonus`
--
ALTER TABLE `Bonus`
  MODIFY `bonusId` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `Branch`
--
ALTER TABLE `Branch`
  MODIFY `branchId` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `Candidate`
--
ALTER TABLE `Candidate`
  MODIFY `candidateId` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `CandidateAddress`
--
ALTER TABLE `CandidateAddress`
  MODIFY `addressId` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `CandidateEducation`
--
ALTER TABLE `CandidateEducation`
  MODIFY `educationId` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `CandidateFamily`
--
ALTER TABLE `CandidateFamily`
  MODIFY `familyId` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `CandidateIdProofs`
--
ALTER TABLE `CandidateIdProofs`
  MODIFY `candidateIdProofsId` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `CandidateLanguage`
--
ALTER TABLE `CandidateLanguage`
  MODIFY `candidateLanguageId` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `CandidateNominee`
--
ALTER TABLE `CandidateNominee`
  MODIFY `candidateNomineeid` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `CandidateOfficialInformation`
--
ALTER TABLE `CandidateOfficialInformation`
  MODIFY `candidateOfficialId` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `CandidatePersonal`
--
ALTER TABLE `CandidatePersonal`
  MODIFY `candidatePersonalId` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `CandidateProfessionalInformation`
--
ALTER TABLE `CandidateProfessionalInformation`
  MODIFY `candidateProfessionalInformationId` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `CandidateSkills`
--
ALTER TABLE `CandidateSkills`
  MODIFY `candidateSkillsId` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `CandidateStatuary`
--
ALTER TABLE `CandidateStatuary`
  MODIFY `candidateStatuaryId` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `category`
--
ALTER TABLE `category`
  MODIFY `category_id` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `City`
--
ALTER TABLE `City`
  MODIFY `cityId` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3208;
--
-- AUTO_INCREMENT for table `Clients`
--
ALTER TABLE `Clients`
  MODIFY `clientId` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `Company`
--
ALTER TABLE `Company`
  MODIFY `companyId` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;
--
-- AUTO_INCREMENT for table `Country`
--
ALTER TABLE `Country`
  MODIFY `countryId` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;
--
-- AUTO_INCREMENT for table `Department`
--
ALTER TABLE `Department`
  MODIFY `departmentId` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `DeptDesignationMapping`
--
ALTER TABLE `DeptDesignationMapping`
  MODIFY `deptDesgId` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=52;
--
-- AUTO_INCREMENT for table `Designation`
--
ALTER TABLE `Designation`
  MODIFY `designationId` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `DeviceInfo`
--
ALTER TABLE `DeviceInfo`
  MODIFY `deviceId` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `DeviceLogs`
--
ALTER TABLE `DeviceLogs`
  MODIFY `deviceLogId` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `DeviceLogsInfo`
--
ALTER TABLE `DeviceLogsInfo`
  MODIFY `deviceLogId` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `DrowpdownHd`
--
ALTER TABLE `DrowpdownHd`
  MODIFY `drowpdownId` tinyint(4) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=41;
--
-- AUTO_INCREMENT for table `DrowpdownList`
--
ALTER TABLE `DrowpdownList`
  MODIFY `drowpdownListId` smallint(6) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=270;
--
-- AUTO_INCREMENT for table `EmailConfiguration`
--
ALTER TABLE `EmailConfiguration`
  MODIFY `emailConfigureId` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=39;
--
-- AUTO_INCREMENT for table `EmailNotificationMaster`
--
ALTER TABLE `EmailNotificationMaster`
  MODIFY `mailId` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=51;
--
-- AUTO_INCREMENT for table `Employee`
--
ALTER TABLE `Employee`
  MODIFY `employeeId` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `EmployeeAssets`
--
ALTER TABLE `EmployeeAssets`
  MODIFY `employeeAssetsId` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `EmployeeBank`
--
ALTER TABLE `EmployeeBank`
  MODIFY `EmployeeBankId` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `EmployeeDocuments`
--
ALTER TABLE `EmployeeDocuments`
  MODIFY `employeeDocumentsId` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `EmployeeEducation`
--
ALTER TABLE `EmployeeEducation`
  MODIFY `educationId` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `EmployeeFamily`
--
ALTER TABLE `EmployeeFamily`
  MODIFY `familyId` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `EmployeeHistory`
--
ALTER TABLE `EmployeeHistory`
  MODIFY `employeeHistoryId` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `EmployeeIdProofs`
--
ALTER TABLE `EmployeeIdProofs`
  MODIFY `employeeIdProofsId` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `EmployeeIdProofsHistory`
--
ALTER TABLE `EmployeeIdProofsHistory`
  MODIFY `employeeIdProofsHistoryId` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `EmployeeLanguage`
--
ALTER TABLE `EmployeeLanguage`
  MODIFY `employeeLanguageId` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `EmployeeNominee`
--
ALTER TABLE `EmployeeNominee`
  MODIFY `employeeNomineeid` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `EmployeeOpeningLeaveMaster`
--
ALTER TABLE `EmployeeOpeningLeaveMaster`
  MODIFY `empOpeningId` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `EmployeeSkills`
--
ALTER TABLE `EmployeeSkills`
  MODIFY `employeeSkillsId` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `EmployeeStatuary`
--
ALTER TABLE `EmployeeStatuary`
  MODIFY `statuaryId` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `Epf`
--
ALTER TABLE `Epf`
  MODIFY `epfId` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;
--
-- AUTO_INCREMENT for table `Esi`
--
ALTER TABLE `Esi`
  MODIFY `esiId` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=73;
--
-- AUTO_INCREMENT for table `EsiCycle`
--
ALTER TABLE `EsiCycle`
  MODIFY `esiCycleId` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;
--
-- AUTO_INCREMENT for table `finalSettlement`
--
ALTER TABLE `finalSettlement`
  MODIFY `finalSettlementId` int(5) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `FinalSettlementReport`
--
ALTER TABLE `FinalSettlementReport`
  MODIFY `finalReportId` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `FinancialMonth`
--
ALTER TABLE `FinancialMonth`
  MODIFY `financialMonthId` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;
--
-- AUTO_INCREMENT for table `FinancialYear`
--
ALTER TABLE `FinancialYear`
  MODIFY `financialYearId` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;
--
-- AUTO_INCREMENT for table `Grades`
--
ALTER TABLE `Grades`
  MODIFY `gradesId` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;
--
-- AUTO_INCREMENT for table `GradesPayDefinition`
--
ALTER TABLE `GradesPayDefinition`
  MODIFY `gradesPayId` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `Gratuaty`
--
ALTER TABLE `Gratuaty`
  MODIFY `graduityId` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=17;
--
-- AUTO_INCREMENT for table `Groupg`
--
ALTER TABLE `Groupg`
  MODIFY `groupId` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `HoldSalary`
--
ALTER TABLE `HoldSalary`
  MODIFY `holdSalaryId` int(10) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `InterestingThoughts`
--
ALTER TABLE `InterestingThoughts`
  MODIFY `interestingThoughtsId` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `Item`
--
ALTER TABLE `Item`
  MODIFY `itemId` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;
--
-- AUTO_INCREMENT for table `Kra`
--
ALTER TABLE `Kra`
  MODIFY `kraId` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `LabourWelfareFund`
--
ALTER TABLE `LabourWelfareFund`
  MODIFY `labourWelfareFundHeadId` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `LabourWelfareFundInfo`
--
ALTER TABLE `LabourWelfareFundInfo`
  MODIFY `labourWelfareFundInfoId` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `Language`
--
ALTER TABLE `Language`
  MODIFY `languageId` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;
--
-- AUTO_INCREMENT for table `LeaveSchemeMaster`
--
ALTER TABLE `LeaveSchemeMaster`
  MODIFY `leaveSchemeId` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;
--
-- AUTO_INCREMENT for table `LoanEMI`
--
ALTER TABLE `LoanEMI`
  MODIFY `emiNo` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `LoanIssue`
--
ALTER TABLE `LoanIssue`
  MODIFY `transactionNo` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `Mail`
--
ALTER TABLE `Mail`
  MODIFY `mailId` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;
--
-- AUTO_INCREMENT for table `MandatoryInfo`
--
ALTER TABLE `MandatoryInfo`
  MODIFY `mandatoryInfoId` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=770;
--
-- AUTO_INCREMENT for table `MandatoryInfoCheck`
--
ALTER TABLE `MandatoryInfoCheck`
  MODIFY `mandatoryInfoCheckId` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `MassCommunication`
--
ALTER TABLE `MassCommunication`
  MODIFY `massCommunicationId` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `MasterBook`
--
ALTER TABLE `MasterBook`
  MODIFY `bookId` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;
--
-- AUTO_INCREMENT for table `MasterBookType`
--
ALTER TABLE `MasterBookType`
  MODIFY `bookTypeId` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;
--
-- AUTO_INCREMENT for table `MenuMaster`
--
ALTER TABLE `MenuMaster`
  MODIFY `menuId` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;
--
-- AUTO_INCREMENT for table `Notification`
--
ALTER TABLE `Notification`
  MODIFY `notificationId` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;
--
-- AUTO_INCREMENT for table `ObjectsInSystem`
--
ALTER TABLE `ObjectsInSystem`
  MODIFY `objectId` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `ObjectsInSystemInRole`
--
ALTER TABLE `ObjectsInSystemInRole`
  MODIFY `ObjectsInSystemInRoleId` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `OneTimeEarningDeduction`
--
ALTER TABLE `OneTimeEarningDeduction`
  MODIFY `Id` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `OtherIncome`
--
ALTER TABLE `OtherIncome`
  MODIFY `otherIncomeId` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `Overtime`
--
ALTER TABLE `Overtime`
  MODIFY `overtimeId` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `PayHeads`
--
ALTER TABLE `PayHeads`
  MODIFY `payHeadId` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=144;
--
-- AUTO_INCREMENT for table `PayRegister`
--
ALTER TABLE `PayRegister`
  MODIFY `payRegisterId` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `PayRegisterHd`
--
ALTER TABLE `PayRegisterHd`
  MODIFY `payRegisterHdId` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `PayrollControl`
--
ALTER TABLE `PayrollControl`
  MODIFY `controlId` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;
--
-- AUTO_INCREMENT for table `PayStructure`
--
ALTER TABLE `PayStructure`
  MODIFY `payStructureId` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `PayStructureHd`
--
ALTER TABLE `PayStructureHd`
  MODIFY `payStructureHdId` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `PreviousEmployerIncome`
--
ALTER TABLE `PreviousEmployerIncome`
  MODIFY `previousEmployerIncomeId` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;
--
-- AUTO_INCREMENT for table `PreviousEmployerIncomeFile`
--
ALTER TABLE `PreviousEmployerIncomeFile`
  MODIFY `PreviousEmployerIncomeFileId` int(16) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `PreviousEmployerIncomeTds`
--
ALTER TABLE `PreviousEmployerIncomeTds`
  MODIFY `previousEmployerIncomeTdsId` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `ProfessionalInformation`
--
ALTER TABLE `ProfessionalInformation`
  MODIFY `historyId` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `ProfessionalTax`
--
ALTER TABLE `ProfessionalTax`
  MODIFY `professionalHeadId` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=185;
--
-- AUTO_INCREMENT for table `ProfessionalTaxInfo`
--
ALTER TABLE `ProfessionalTaxInfo`
  MODIFY `ProfessionalTaxInfoId` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=320;
--
-- AUTO_INCREMENT for table `Project`
--
ALTER TABLE `Project`
  MODIFY `projectId` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `Provision`
--
ALTER TABLE `Provision`
  MODIFY `provisionId` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `PunchTimeDetail`
--
ALTER TABLE `PunchTimeDetail`
  MODIFY `punchTimeDetailsId` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `RoleMaster`
--
ALTER TABLE `RoleMaster`
  MODIFY `roleId` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;
--
-- AUTO_INCREMENT for table `RoleSubmenuActionMaster`
--
ALTER TABLE `RoleSubmenuActionMaster`
  MODIFY `roleSubmenuActionId` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=531;
--
-- AUTO_INCREMENT for table `Separation`
--
ALTER TABLE `Separation`
  MODIFY `separationId` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `Skills`
--
ALTER TABLE `Skills`
  MODIFY `skillId` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `State`
--
ALTER TABLE `State`
  MODIFY `stateId` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=57;
--
-- AUTO_INCREMENT for table `SubmenuActionMaster`
--
ALTER TABLE `SubmenuActionMaster`
  MODIFY `submenuActionId` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=239;
--
-- AUTO_INCREMENT for table `SubMenuMaster`
--
ALTER TABLE `SubMenuMaster`
  MODIFY `subMenuId` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=54;
--
-- AUTO_INCREMENT for table `TdsApproved`
--
ALTER TABLE `TdsApproved`
  MODIFY `transactionId` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `TdsCityMaster`
--
ALTER TABLE `TdsCityMaster`
  MODIFY `tdsCityMasterId` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `TdsDeduction`
--
ALTER TABLE `TdsDeduction`
  MODIFY `tdsDeductionId` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `TdsGroup`
--
ALTER TABLE `TdsGroup`
  MODIFY `tdsGroupId` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `TdsGroupMaster`
--
ALTER TABLE `TdsGroupMaster`
  MODIFY `tdsGroupMasterId` int(14) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;
--
-- AUTO_INCREMENT for table `TdsGroupSetup`
--
ALTER TABLE `TdsGroupSetup`
  MODIFY `tdsGroupId` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=31;
--
-- AUTO_INCREMENT for table `TdsHistory`
--
ALTER TABLE `TdsHistory`
  MODIFY `tdsHistoryId` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `TdsHouseRentFileInfo`
--
ALTER TABLE `TdsHouseRentFileInfo`
  MODIFY `tdsHouseRentFileInfoId` bigint(16) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `TdsHouseRentInfo`
--
ALTER TABLE `TdsHouseRentInfo`
  MODIFY `tdsHouseRentInfoId` bigint(16) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `TdsPayroll`
--
ALTER TABLE `TdsPayroll`
  MODIFY `transactionId` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `TdsPayrollHd`
--
ALTER TABLE `TdsPayrollHd`
  MODIFY `transactionHdId` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `TdsSection`
--
ALTER TABLE `TdsSection`
  MODIFY `tdsSectionId` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `TdsSectionSetup`
--
ALTER TABLE `TdsSectionSetup`
  MODIFY `tdsSectionId` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=97;
--
-- AUTO_INCREMENT for table `TdsSlab`
--
ALTER TABLE `TdsSlab`
  MODIFY `tdsSLabId` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=57;
--
-- AUTO_INCREMENT for table `TdsSlabHd`
--
ALTER TABLE `TdsSlabHd`
  MODIFY `tdsSLabHdId` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=37;
--
-- AUTO_INCREMENT for table `TdsSlabMaster`
--
ALTER TABLE `TdsSlabMaster`
  MODIFY `tdsSlabMasterId` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;
--
-- AUTO_INCREMENT for table `TdsStandardExemption`
--
ALTER TABLE `TdsStandardExemption`
  MODIFY `tdsStandardExemptionId` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `TdsSummary`
--
ALTER TABLE `TdsSummary`
  MODIFY `tdsSummaryId` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `TdsSummaryChange`
--
ALTER TABLE `TdsSummaryChange`
  MODIFY `tdsSummaryChangeId` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `TdsTransaction`
--
ALTER TABLE `TdsTransaction`
  MODIFY `tdsTransactionId` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `TdsTransactionFile`
--
ALTER TABLE `TdsTransactionFile`
  MODIFY `TdsTransactionFileId` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `TdsTransactionFileInfo`
--
ALTER TABLE `TdsTransactionFileInfo`
  MODIFY `tdsTransactionFileInfoId` int(16) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `TicketDesc`
--
ALTER TABLE `TicketDesc`
  MODIFY `ticketDescId` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `TicketRaisingHD`
--
ALTER TABLE `TicketRaisingHD`
  MODIFY `ticketRaisingHDId` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `TicketType`
--
ALTER TABLE `TicketType`
  MODIFY `ticketTypeId` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;
--
-- AUTO_INCREMENT for table `TMSARRequest`
--
ALTER TABLE `TMSARRequest`
  MODIFY `arID` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `TMSCompensantoryOff`
--
ALTER TABLE `TMSCompensantoryOff`
  MODIFY `tmsCompensantoryOffId` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `TMSHalfDayRule`
--
ALTER TABLE `TMSHalfDayRule`
  MODIFY `halfDayRuleId` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;
--
-- AUTO_INCREMENT for table `TMSHolidays`
--
ALTER TABLE `TMSHolidays`
  MODIFY `holidayId` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `TMSLeaveCarryForward`
--
ALTER TABLE `TMSLeaveCarryForward`
  MODIFY `leaveCarryForwardId` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `TMSLeaveEmailNotification`
--
ALTER TABLE `TMSLeaveEmailNotification`
  MODIFY `tmsLeaveEmailNotificationId` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `TMSLeaveEntries`
--
ALTER TABLE `TMSLeaveEntries`
  MODIFY `leaveId` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `TMSLeaveEntriesDatewise`
--
ALTER TABLE `TMSLeaveEntriesDatewise`
  MODIFY `id` bigint(8) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `TMSLeavePeriod`
--
ALTER TABLE `TMSLeavePeriod`
  MODIFY `leavePeriodId` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `TMSLeaveRuleMaster`
--
ALTER TABLE `TMSLeaveRuleMaster`
  MODIFY `leaveRuleMasterId` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;
--
-- AUTO_INCREMENT for table `TMSLeaveRules`
--
ALTER TABLE `TMSLeaveRules`
  MODIFY `leaveRuleId` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `TMSLeaveRulesHd`
--
ALTER TABLE `TMSLeaveRulesHd`
  MODIFY `leaveRulesHdId` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `TMSLeaveType`
--
ALTER TABLE `TMSLeaveType`
  MODIFY `leaveTypeId` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `TMSLeaveTypeMaster`
--
ALTER TABLE `TMSLeaveTypeMaster`
  MODIFY `leaveId` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;
--
-- AUTO_INCREMENT for table `TMSShift`
--
ALTER TABLE `TMSShift`
  MODIFY `shiftId` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;
--
-- AUTO_INCREMENT for table `TMSWeekOffPattern`
--
ALTER TABLE `TMSWeekOffPattern`
  MODIFY `patternId` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;
--
-- AUTO_INCREMENT for table `ToDoList`
--
ALTER TABLE `ToDoList`
  MODIFY `todoListId` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `TokenMaster`
--
ALTER TABLE `TokenMaster`
  MODIFY `tokenId` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `TokenTypeMaster`
--
ALTER TABLE `TokenTypeMaster`
  MODIFY `tokenTypeId` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;
--
-- AUTO_INCREMENT for table `TransactionApprovedHd`
--
ALTER TABLE `TransactionApprovedHd`
  MODIFY `transactionApprovedHdId` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `UserRoles`
--
ALTER TABLE `UserRoles`
  MODIFY `userRolesSrNo` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;
--
-- AUTO_INCREMENT for table `Users`
--
ALTER TABLE `Users`
  MODIFY `userId` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;
--
-- Constraints for dumped tables
--

--
-- Constraints for table `AdditionalUserObjects`
--
ALTER TABLE `AdditionalUserObjects`
  ADD CONSTRAINT `AdditionalUserObjects_ibfk_1` FOREIGN KEY (`userId`) REFERENCES `Users` (`userId`),
  ADD CONSTRAINT `AdditionalUserObjects_ibfk_2` FOREIGN KEY (`objectId`) REFERENCES `ObjectsInSystem` (`objectId`);

--
-- Constraints for table `Address`
--
ALTER TABLE `Address`
  ADD CONSTRAINT `Address_ibfk_1` FOREIGN KEY (`countryId`) REFERENCES `Country` (`countryId`),
  ADD CONSTRAINT `Address_ibfk_2` FOREIGN KEY (`stateId`) REFERENCES `State` (`stateId`),
  ADD CONSTRAINT `Address_ibfk_3` FOREIGN KEY (`cityId`) REFERENCES `City` (`cityId`);

--
-- Constraints for table `ArearCalculation`
--
ALTER TABLE `ArearCalculation`
  ADD CONSTRAINT `ArearCalculation_ibfk_1` FOREIGN KEY (`arearId`) REFERENCES `ArearMaster` (`arearId`);

--
-- Constraints for table `ArearMaster`
--
ALTER TABLE `ArearMaster`
  ADD CONSTRAINT `ArearMaster_ibfk_1` FOREIGN KEY (`employeeId`) REFERENCES `Employee` (`employeeId`);

--
-- Constraints for table `Attendance`
--
ALTER TABLE `Attendance`
  ADD CONSTRAINT `Attendance_ibfk_1` FOREIGN KEY (`companyId`) REFERENCES `Company` (`companyId`);

--
-- Constraints for table `AttendanceLogs`
--
ALTER TABLE `AttendanceLogs`
  ADD CONSTRAINT `AttendanceLogs_ibfk_1` FOREIGN KEY (`companyId`) REFERENCES `Company` (`companyId`),
  ADD CONSTRAINT `AttendanceLogs_ibfk_2` FOREIGN KEY (`employeeId`) REFERENCES `Employee` (`employeeId`);

--
-- Constraints for table `Banks`
--
ALTER TABLE `Banks`
  ADD CONSTRAINT `Banks_ibfk_1` FOREIGN KEY (`companyId`) REFERENCES `Company` (`companyId`);

--
-- Constraints for table `Bonus`
--
ALTER TABLE `Bonus`
  ADD CONSTRAINT `Bonus_ibfk_1` FOREIGN KEY (`gradesId`) REFERENCES `Grades` (`gradesId`);

--
-- Constraints for table `Branch`
--
ALTER TABLE `Branch`
  ADD CONSTRAINT `Branch_ibfk_1` FOREIGN KEY (`groupId`) REFERENCES `Groupg` (`groupId`),
  ADD CONSTRAINT `Branch_ibfk_2` FOREIGN KEY (`companyId`) REFERENCES `Company` (`companyId`);

--
-- Constraints for table `Candidate`
--
ALTER TABLE `Candidate`
  ADD CONSTRAINT `Candidate_ibfk_1` FOREIGN KEY (`permanentAddressId`) REFERENCES `Address` (`addressId`),
  ADD CONSTRAINT `Candidate_ibfk_2` FOREIGN KEY (`presentAddressId`) REFERENCES `Address` (`addressId`),
  ADD CONSTRAINT `Candidate_ibfk_3` FOREIGN KEY (`referenceAddressId`) REFERENCES `Address` (`addressId`),
  ADD CONSTRAINT `Candidate_ibfk_4` FOREIGN KEY (`departmentId`) REFERENCES `Department` (`departmentId`),
  ADD CONSTRAINT `Candidate_ibfk_5` FOREIGN KEY (`clientId`) REFERENCES `Clients` (`clientId`),
  ADD CONSTRAINT `Candidate_ibfk_6` FOREIGN KEY (`projectId`) REFERENCES `Project` (`projectId`),
  ADD CONSTRAINT `Candidate_ibfk_7` FOREIGN KEY (`designationId`) REFERENCES `Designation` (`designationId`);

--
-- Constraints for table `CandidateAddress`
--
ALTER TABLE `CandidateAddress`
  ADD CONSTRAINT `CandidateAddress_ibfk_1` FOREIGN KEY (`countryId`) REFERENCES `Country` (`countryId`),
  ADD CONSTRAINT `CandidateAddress_ibfk_2` FOREIGN KEY (`stateId`) REFERENCES `State` (`stateId`),
  ADD CONSTRAINT `CandidateAddress_ibfk_3` FOREIGN KEY (`cityId`) REFERENCES `City` (`cityId`);

--
-- Constraints for table `CandidateEducation`
--
ALTER TABLE `CandidateEducation`
  ADD CONSTRAINT `CandidateEducation_ibfk_1` FOREIGN KEY (`candidateId`) REFERENCES `Candidate` (`candidateId`);

--
-- Constraints for table `CandidateIdProofs`
--
ALTER TABLE `CandidateIdProofs`
  ADD CONSTRAINT `CandidateIdProofs_ibfk_1` FOREIGN KEY (`candidateId`) REFERENCES `Candidate` (`candidateId`);

--
-- Constraints for table `CandidateLanguage`
--
ALTER TABLE `CandidateLanguage`
  ADD CONSTRAINT `CandidateLanguage_ibfk_1` FOREIGN KEY (`languageId`) REFERENCES `Language` (`languageId`),
  ADD CONSTRAINT `CandidateLanguage_ibfk_2` FOREIGN KEY (`candidatePersonalId`) REFERENCES `CandidatePersonal` (`candidatePersonalId`);

--
-- Constraints for table `CandidateNominee`
--
ALTER TABLE `CandidateNominee`
  ADD CONSTRAINT `CandidateNominee_ibfk_1` FOREIGN KEY (`familyId`) REFERENCES `CandidateFamily` (`familyId`),
  ADD CONSTRAINT `CandidateNominee_ibfk_2` FOREIGN KEY (`candidateId`) REFERENCES `Candidate` (`candidateId`);

--
-- Constraints for table `CandidateOfficialInformation`
--
ALTER TABLE `CandidateOfficialInformation`
  ADD CONSTRAINT `CandidateOfficialInformation_ibfk_2` FOREIGN KEY (`gradeId`) REFERENCES `Grades` (`gradesId`);

--
-- Constraints for table `CandidatePersonal`
--
ALTER TABLE `CandidatePersonal`
  ADD CONSTRAINT `CandidatePersonal_ibfk_1` FOREIGN KEY (`candidateId`) REFERENCES `Candidate` (`candidateId`),
  ADD CONSTRAINT `CandidatePersonal_ibfk_2` FOREIGN KEY (`permanentAddressId`) REFERENCES `CandidateAddress` (`addressId`),
  ADD CONSTRAINT `CandidatePersonal_ibfk_3` FOREIGN KEY (`presentAddressId`) REFERENCES `CandidateAddress` (`addressId`),
  ADD CONSTRAINT `CandidatePersonal_ibfk_4` FOREIGN KEY (`referenceAddressId`) REFERENCES `CandidateAddress` (`addressId`);

--
-- Constraints for table `CandidateSkills`
--
ALTER TABLE `CandidateSkills`
  ADD CONSTRAINT `CandidateSkills_ibfk_1` FOREIGN KEY (`skillId`) REFERENCES `Skills` (`skillId`),
  ADD CONSTRAINT `CandidateSkills_ibfk_2` FOREIGN KEY (`candidateId`) REFERENCES `Candidate` (`candidateId`);

--
-- Constraints for table `City`
--
ALTER TABLE `City`
  ADD CONSTRAINT `City_ibfk_1` FOREIGN KEY (`countryId`) REFERENCES `Country` (`countryId`),
  ADD CONSTRAINT `City_ibfk_2` FOREIGN KEY (`stateId`) REFERENCES `State` (`stateId`);

--
-- Constraints for table `Clients`
--
ALTER TABLE `Clients`
  ADD CONSTRAINT `Clients_ibfk_1` FOREIGN KEY (`addressId`) REFERENCES `Address` (`addressId`);

--
-- Constraints for table `Department`
--
ALTER TABLE `Department`
  ADD CONSTRAINT `Department_ibfk_1` FOREIGN KEY (`companyId`) REFERENCES `Company` (`companyId`);

--
-- Constraints for table `EmailNotificationMaster`
--
ALTER TABLE `EmailNotificationMaster`
  ADD CONSTRAINT `emailNotification` FOREIGN KEY (`emailConfigureId`) REFERENCES `EmailConfiguration` (`emailConfigureId`);

--
-- Constraints for table `Employee`
--
ALTER TABLE `Employee`
  ADD CONSTRAINT `Employee_ibfk_1` FOREIGN KEY (`leaveSchemeId`) REFERENCES `LeaveSchemeMaster` (`leaveSchemeId`),
  ADD CONSTRAINT `Employee_ibfk_2` FOREIGN KEY (`leaveSchemeId`) REFERENCES `LeaveSchemeMaster` (`leaveSchemeId`);

--
-- Constraints for table `EmployeeOpeningLeaveMaster`
--
ALTER TABLE `EmployeeOpeningLeaveMaster`
  ADD CONSTRAINT `EmployeeOpeningLeaveMaster_ibfk_1` FOREIGN KEY (`leavePeriodId`) REFERENCES `TMSLeavePeriod` (`leavePeriodId`),
  ADD CONSTRAINT `EmployeeOpeningLeaveMaster_ibfk_2` FOREIGN KEY (`companyId`) REFERENCES `Company` (`companyId`),
  ADD CONSTRAINT `EmployeeOpeningLeaveMaster_ibfk_3` FOREIGN KEY (`leaveTypeMasterId`) REFERENCES `TMSLeaveTypeMaster` (`leaveId`),
  ADD CONSTRAINT `EmployeeOpeningLeaveMaster_ibfk_4` FOREIGN KEY (`employeeId`) REFERENCES `Employee` (`employeeId`);

--
-- Constraints for table `EmployeeStatuary`
--
ALTER TABLE `EmployeeStatuary`
  ADD CONSTRAINT `EmployeeStatuary_ibfk_2` FOREIGN KEY (`familyId`) REFERENCES `EmployeeFamily` (`familyId`),
  ADD CONSTRAINT `EmployeeStatuary_ibfk_3` FOREIGN KEY (`employeeId`) REFERENCES `Employee` (`employeeId`);

--
-- Constraints for table `RoleSubmenuActionMaster`
--
ALTER TABLE `RoleSubmenuActionMaster`
  ADD CONSTRAINT `RoleSubmenuActionMaster_ibfk_1` FOREIGN KEY (`submenuActionId`) REFERENCES `SubmenuActionMaster` (`submenuActionId`),
  ADD CONSTRAINT `RoleSubmenuActionMaster_ibfk_2` FOREIGN KEY (`roleId`) REFERENCES `RoleMaster` (`roleId`);

--
-- Constraints for table `SubmenuActionMaster`
--
ALTER TABLE `SubmenuActionMaster`
  ADD CONSTRAINT `SubmenuActionMaster_ibfk_1` FOREIGN KEY (`submenuId`) REFERENCES `SubMenuMaster` (`subMenuId`);

--
-- Constraints for table `TdsApproved`
--
ALTER TABLE `TdsApproved`
  ADD CONSTRAINT `TdsApproved_ibfk_2` FOREIGN KEY (`tdsGroupId`) REFERENCES `TdsGroupSetup` (`tdsGroupId`);

--
-- Constraints for table `TokenMaster`
--
ALTER TABLE `TokenMaster`
  ADD CONSTRAINT `TokenMaster_ibfk_1` FOREIGN KEY (`tokentypeId`) REFERENCES `TokenTypeMaster` (`tokenTypeId`),
  ADD CONSTRAINT `TokenMaster_ibfk_2` FOREIGN KEY (`userId`) REFERENCES `Users` (`userId`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
