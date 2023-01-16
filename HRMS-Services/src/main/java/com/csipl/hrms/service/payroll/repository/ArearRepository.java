package com.csipl.hrms.service.payroll.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.csipl.hrms.model.payroll.ArearCalculation;


public interface ArearRepository  extends CrudRepository<ArearCalculation,Long>{

	@Query(nativeQuery=true, value="SELECT e.employeeCode, Concat(e.firstName,' ',e.lastName) as empName,dept.departmentName,sum(ac.actualAmount),sum(ac.netPayableAmount),(sum(ac.esiDeduction)+sum(ac.pfDeduction)+sum(ac.ptDeduction)) as deductionAmt,am.arearFrom,am.arearId,am.arearTo,am.isBooked,am.companyId,am.userId,am.dateCreated,am.userIdUpdate,am.dateUpdate,e.employeeId,e.dateOfJoining,desig.designationName,gr.gradesName,e.employeeLogoPath FROM ArearCalculation ac join ArearMaster am on am.arearId=ac.arearId join Employee e on e.employeeId=am.employeeId join Department dept on dept.departmentId=e.departmentId join Designation desig on desig.designationId=e.designationId join Grades gr on gr.gradesId=e.gradesId where ac.companyId=?1 and am.isBooked='0' GROUP BY ac.arearId")
    public List<Object[]> findAllArear(Long companyId);

	@Query(nativeQuery=true, value="SELECT ac.payrollMonth,ac.actualAmount,ac.pfDeduction,ac.esiDeduction,ac.netPayableAmount,am.basicSalary,am.specialAllowance,ac.ptDeduction FROM ArearMaster am join ArearCalculation ac on ac.arearId=am.arearId WHERE ac.arearId=?1  order by month(str_to_date(SUBSTRING_INDEX(ac.payrollMonth,'-',1),'%b')) ASC")
    public List<Object[]> findArearCalculation(Long arearId);

    @Query( nativeQuery = true,value = "SELECT ar.arearId FROM ArearMaster ar  JOIN ArearCalculation ac on ac.arearId = ar.arearId  WHERE ar.employeeId =:employeeId  AND ac.payrollMonth IN :processMonthList ")
	public List<Object[]> arearListByprocessMonth(@Param("processMonthList") List<String> processMonthList, @Param("employeeId") Long employeeId);
 

    
}
