package com.csipl.hrms.service.payroll.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.csipl.hrms.common.constant.StatusMessage;
import com.csipl.hrms.model.payrollprocess.PayRegister;


@Repository
public interface PayrollRollbackRepository extends CrudRepository<PayRegister,Long>{
	
//	String fetchPayrollRegisterProcessMonth="SELECT DISTINCT ph.processMonth, ph.payRegisterHdId FROM  PayRegisterHd"
//			+ " ph WHERE ph.companyId=?1 group by ph.processMonth";
//	@Query(value=fetchPayrollRegisterProcessMonth, nativeQuery=true)
//	List<Object[]> findPayrollRegisterProcessMonth(Long companyId); 
//	
	//To get process month order wise
	String fetchPayrollRegisterProcessMonth = "CALL  	Pro_fetch_process_month( :p_comp_id)";
	@Query(value = fetchPayrollRegisterProcessMonth, nativeQuery = true)
	public List<Object[]> findPayrollRegisterProcessMonth(@Param(value = "p_comp_id") Long p_comp_id);
	////order by pc.controlId
	////order by month(str_to_date(SUBSTRING_INDEX(ph.processMonth,'-',1),'%b'))
	String fetchPayrollRegisterProcessMonthForRollback =" SELECT DISTINCT pc.processMonth, ph.payRegisterHdId FROM  PayRegisterHd ph" + 
			" LEFT JOIN PayrollControl pc  on pc.processMonth=ph.processMonth" + 
			" WHERE ph.companyId=?1 and pc.activeStatus='OP' and pc.ispayrollLocked='N' and ph.dateCreated > DATE_SUB(now(), INTERVAL 6 MONTH) "
			+ " group by ph.processMonth order by pc.controlId ";
	@Query(value=fetchPayrollRegisterProcessMonthForRollback ,nativeQuery = true)
	public List<Object[]> fetchPayrollRegistProcessMonthForRollBack(Long companyID);
	
	
	String fetchPayrollRegisterListByHdId ="SELECT pHd.payRegisterHdId,pHd.processMonth,pHd.dateUpdate,replace(concat(\"/\",GROUP_CONCAT(DISTINCT dept.departmentName),\"/\"),\",\",\"/\") as departmentName FROM PayRegisterHd pHd JOIN PayRegister pr ON pHd.payRegisterHdId = pr.payRegisterHdId \r\n" + 
			"JOIN Department dept ON dept.departmentId = pr.departmentId WHERE pHd.companyId =?1 and pHd.processMonth =?2 and pr.activeStatus='AC'\r\n" + 
			"group by pHd.payRegisterHdId";
	@Query(value=fetchPayrollRegisterListByHdId ,nativeQuery = true)
	public List<Object[]> fetchPayrollRegisterListByHdId(Long companyID, String processMonth);

	
	String fetchEmployeePayrollRegisterListRollback ="select p.payRegisterId,rp.employeeId,rp.employeeCode,rp.Name,d.departmentName,\r\n" + 
			"			rp.dateOfJoining,rp.payDays,( IFNULL(rp.TotalEarning,0)+IFNULL(rp.otherEarning,0)+IFNULL(rp.arearAmount,0)) as  grossSalary,rp.TotalDeduction ,rp.NetPayableAmount , rp.processmonth from PayRegister p  join PayRegisterHd hd on hd.payRegisterHdId=p.payRegisterHdId\r\n" + 
			"			join ReportPayOut rp on rp.processMonth= hd.processMonth and p.employeeId=rp.employeeId JOIN Department d on p.departmentId= d.departmentId where hd.payRegisterHdId=?1 and  p.payrollLockFlag=0 and p.activeStatus='AC' group by rp.employeeId";
	@Query(value=fetchEmployeePayrollRegisterListRollback ,nativeQuery = true)
	public List<Object[]> fetchEmployeePayrollRegisterListForRollback(Long hdId);


	String deactiveEmpFromPayRegister="update PayRegister p set p.activeStatus=?1 where p.employeeId=?2 and p.payRegisterHdId=?3 ";
	@Modifying
	@Query(value=deactiveEmpFromPayRegister ,nativeQuery = true)
	void deactiveEmpFromPayRegister(String status, Long empID,Long payReghdId);
	
	
	String deleteEmpFromReportPayOut="DELETE FROM ReportPayOut WHERE employeeId=?1 and processMonth=?2";
	@Modifying
	@Query(value=deleteEmpFromReportPayOut ,nativeQuery = true)
	void deleteEmpFromReportPayOut(Long empID,String processMonth);
	
	String deleteEmpFromPayOut=" DELETE FROM PayOut WHERE (employeeId=?1 and processMonth=?2) ";
	@Modifying
	@Query(value=deleteEmpFromPayOut ,nativeQuery = true)
	void deleteEmpFromPayout(Long empID,String processMonth);
	
	
}
