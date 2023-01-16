package com.csipl.hrms.service.report.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.csipl.hrms.model.payroll.TdsSummaryChange;

@Repository
public interface TdsReportRepository extends CrudRepository<TdsSummaryChange, Long> {

	String statememntOfAnnualTax = "SELECT e.employeeCode, CONCAT(e.firstName, ' ', e.lastName) as employeeName, des.designationName, dp.departmentName,"
			+ " e.dateOfJoining, eip.idNumber as PAN, tsc.*   FROM   Employee e  " + 
			"   LEFT JOIN Department dp ON dp.departmentId=e.departmentId  LEFT JOIN Designation des ON des.designationId= e.designationId "
			+ " LEFT JOIN EmployeeIdProofs eip on eip.employeeId=e.employeeId and eip.idTypeId='PA' JOIN TdsSummaryChange tsc ON tsc.employeeId=e.employeeId "
			+ " and tsc.financialYearId=?2 and tsc.active='AC' WHERE e.companyId=?1 ";
	@Query(value = statememntOfAnnualTax, nativeQuery = true)
	List<Object[]> getStatememntOfAnnualTax(Long companyId, Long financialYearId);
	
	String declarationColumn ="select tss.tdsSectionId,tss.tdsSectionName      \r\n" + 
			"		 			     from TdsSectionSetup tss join TdsGroupSetup tgs on tgs.tdsGroupId=tss.tdsGroupId       \r\n" + 
			"					       join TdsTransaction tt on tt.tdsSectionId=tss.tdsSectionId   and tt.financialYearId= tgs.financialYearId\r\n" + 
			"			 			     join TdsGroupMaster tgm on  tgm.tdsGroupMasterId=tgs.tdsGroupMasterId WHERE tgs.companyId=?1 and tt.financialYearId=?2    GROUP BY tss.tdsSectionId  ORDER BY tss.tdsSectionId ";
	@Query(value = declarationColumn, nativeQuery = true)
	List<Object[]> getDeclarationColumn(Long companyId, Long financialYearId);
	
	String employeeTdsDeclaration="SELECT e.employeeCode, CONCAT(e.firstName, ' ', e.lastName) as employeeName, des.designationName, dp.departmentName,"
			+ "  e.dateOfJoining, eip.idNumber as PAN, tss.tdsSectionId,tss.tdsSectionName,tgm.tdsGroupName  , tss.maxLimit, tt.investmentAmount,tt.approvedAmount, "
			+ "  tgs.maxLimit as otherLimit ,  tsc.otherIncome,tsc.preEmpIncome, (tsc.exempPfAmount+ tsc.potalTax)  pf      FROM  TdsSectionSetup tss   join TdsGroupSetup tgs on tgs.tdsGroupId=tss.tdsGroupId  join TdsTransaction tt on tt.tdsSectionId=tss.tdsSectionId  and tt.financialYearId= tgs.financialYearId     \r\n" + 
			"			 	     join TdsGroupMaster tgm on  tgm.tdsGroupMasterId=tgs.tdsGroupMasterId JOIN Employee e ON e.employeeId=tt.employeeId \r\n" + 
			"			  LEFT JOIN Department dp ON dp.departmentId=e.departmentId  LEFT JOIN Designation des ON des.designationId= e.designationId "
			+ "    LEFT JOIN EmployeeIdProofs eip on eip.employeeId=e.employeeId and eip.idTypeId='PA' join TdsSummaryChange tsc on tsc.financialYearId=tt.financialYearId "
			+ "   and tsc.employeeId=tt.employeeId and active='AC'\r\n" + 
			"			  WHERE e.companyId=?1 and tt.financialYearId=?2  ORDER BY   e.employeeId,tss.tdsSectionId ";
	@Query(value = employeeTdsDeclaration, nativeQuery = true)
	List<Object[]> getEmployeeTdsDeclaration(Long companyId, Long financialYearId);

}
