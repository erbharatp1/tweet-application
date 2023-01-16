package com.csipl.hrms.service.employee.bulkupload;

import java.util.List;
import java.util.Map;

import com.csipl.hrms.common.exception.PayRollProcessException;
import com.csipl.hrms.common.util.ErrorHandling;
import com.csipl.hrms.dto.employee.EmployeeDTO;
import com.csipl.hrms.model.employee.Employee;
import com.csipl.hrms.model.employee.EmployeeBulkUploadMaster;
import com.csipl.hrms.model.employee.EmployeeEducation;
import com.csipl.hrms.model.employee.EmployeeFamily;
import com.csipl.hrms.model.employee.EmployeeIdProof;
import com.csipl.hrms.model.employee.EmployeeLanguage;
import com.csipl.hrms.model.employee.EmployeeSkill;
import com.csipl.hrms.model.employee.EmployeeStatuary;
import com.csipl.hrms.model.employee.MasterBook;
import com.csipl.hrms.model.employee.PayStructureHd;
import com.csipl.hrms.model.employee.ProfessionalInformation;

public interface BulkUploadService {

public void saveAll(List<Employee> employeeList,EmployeeDTO employeeDto,Map<Integer, StringBuilder> errorMap) throws PayRollProcessException ;
public void saveIdProof(List<EmployeeIdProof> employeeIdProofList,EmployeeDTO employeeDto,Map<Integer, StringBuilder> errorMap) throws PayRollProcessException ;
public void saveFamily(List<EmployeeFamily> employeeFamilyList,EmployeeDTO employeeDto,Map<Integer, StringBuilder> errorMap) throws PayRollProcessException ;
public void saveUanAndPf(List<EmployeeStatuary> employeeStatuaryList,EmployeeDTO employeeDto,Map<Integer, StringBuilder> errorMap) throws PayRollProcessException;
public void saveEsi(List<EmployeeStatuary> employeeStatuaryList,EmployeeDTO employeeDto,Map<Integer, StringBuilder> errorMap) throws PayRollProcessException;
public void saveMi(List<EmployeeStatuary> employeeStatuaryList,EmployeeDTO employeeDto,Map<Integer, StringBuilder> errorMap) throws PayRollProcessException;
public void saveAi(List<EmployeeStatuary> employeeStatuaryList,EmployeeDTO employeeDto,Map<Integer, StringBuilder> errorMap) throws PayRollProcessException;
public void saveProfessionalInfo(List<ProfessionalInformation> employeeProfessionalList,EmployeeDTO employeeDto,Map<Integer, StringBuilder> errorMap) throws PayRollProcessException;
public void saveEducationInfo(List<EmployeeEducation> employeeEducationList,EmployeeDTO employeeDto,Map<Integer, StringBuilder> errorMap) throws PayRollProcessException ;
public void saveLanguage(List<EmployeeLanguage> employeeLanguageList,EmployeeDTO employeeDto,Map<Integer, StringBuilder> errorMap) throws PayRollProcessException ;
public void saveSkills(List<EmployeeSkill> employeeSkillsList,EmployeeDTO employeeDto,Map<Integer, StringBuilder> errorMap) ;
public MasterBook findMasterBook(String bookCode,Long companyId,String bookType);
public List<EmployeeBulkUploadMaster> findHeaderName(String fileCode);
public List<Employee> findAllEmployee(Long companyId);
public void saveEmployeePaystructure(List<PayStructureHd> employeePayStrcutureList,EmployeeDTO employeeDto,Map<Integer, StringBuilder> errorMap) throws PayRollProcessException,ErrorHandling,org.springframework.dao.DataIntegrityViolationException ;

}
