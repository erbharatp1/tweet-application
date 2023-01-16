package com.csipl.hrms.employee.controller;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import com.csipl.common.services.dropdown.DropDownHdService;
import com.csipl.hrms.common.exception.PayRollProcessException;
import com.csipl.hrms.common.util.BulkUploadUtil;
import com.csipl.hrms.common.util.ErrorHandling;
import com.csipl.hrms.dto.employee.EmployeeDTO;
import com.csipl.hrms.dto.organisation.BranchDTO;
import com.csipl.hrms.model.authoriztion.RoleMaster;
import com.csipl.hrms.model.common.Branch;
import com.csipl.hrms.model.common.State;
import com.csipl.hrms.model.employee.Employee;
import com.csipl.hrms.model.employee.EmployeeBulkUploadMaster;
import com.csipl.hrms.model.employee.EmployeeEducation;
import com.csipl.hrms.model.employee.EmployeeFamily;
import com.csipl.hrms.model.employee.EmployeeIdProof;
import com.csipl.hrms.model.employee.EmployeeLanguage;
import com.csipl.hrms.model.employee.EmployeeStatuary;
import com.csipl.hrms.model.employee.PayStructureHd;
import com.csipl.hrms.model.employee.ProfessionalInformation;
import com.csipl.hrms.model.organisation.Department;
import com.csipl.hrms.model.organisation.Designation;
import com.csipl.hrms.model.organisation.Grade;
import com.csipl.hrms.service.adaptor.BranchAdaptor;
import com.csipl.hrms.service.authorization.RoleMasterService;
import com.csipl.hrms.service.authorization.repository.RoleMasterRepository;
import com.csipl.hrms.service.common.StateService;
import com.csipl.hrms.service.employee.EmployeePersonalInformationService;
import com.csipl.hrms.service.employee.bulkupload.BulkUploadService;
import com.csipl.hrms.service.organization.BranchService;
import com.csipl.hrms.service.organization.DepartmentService;
import com.csipl.hrms.service.organization.DesignationService;
import com.csipl.hrms.service.organization.GradeService;
import com.csipl.tms.attendancescheme.adaptor.AttendanceSchemeAdaptor;
import com.csipl.tms.attendancescheme.service.AttendanceSchemeService;
import com.csipl.tms.dto.attendancescheme.AttendanceSchemeDTO;
import com.csipl.tms.dto.leave.LeaveSchemeMasterDTO;
import com.csipl.tms.dto.shift.ShiftDTO;
import com.csipl.tms.dto.weekofpattern.TMSWeekOffMasterPatternDto;
import com.csipl.tms.leave.adaptor.LeaveSchemeAdaptor;
import com.csipl.tms.leave.service.LeavePeriodService;
import com.csipl.tms.leave.service.LeaveSchemeService;
import com.csipl.tms.model.attendancescheme.AttendanceScheme;
import com.csipl.tms.model.leave.LeaveSchemeMaster;
import com.csipl.tms.model.leave.TMSLeavePeriod;
import com.csipl.tms.model.shift.Shift;
import com.csipl.tms.model.weekofpattern.TMSWeekOffMasterPattern;
import com.csipl.tms.shift.adaptor.ShiftAdaptor;
import com.csipl.tms.shift.service.ShiftService;
import com.csipl.tms.weekoffpattern.adaptor.TMSWeekOffMasterPatternAdaptor;
import com.csipl.tms.weekoffpattern.service.WeekOffPatternService;




@RestController
public class BulkUploadController {
	private static final Logger logger = LoggerFactory.getLogger(BulkUploadController.class);

	@Autowired
	BulkUploadService bulkUploadService;
	@Autowired
	EmployeePersonalInformationService employeePersonalInformationService;
	@Autowired
	RoleMasterRepository roleMasterRepository;
	@Autowired
	DropDownHdService dropDownHdService; 
	@Autowired
	DepartmentService departmentService;
	@Autowired
	DesignationService designationService;
	@Autowired
	GradeService gradeService;
	@Autowired
	RoleMasterService roleMasterService;
	@Autowired
	StateService stateService;
	@Autowired
	private org.springframework.core.env.Environment environment;
	
	@Autowired
	ShiftService shiftService;
	

	@Autowired
	WeekOffPatternService week_Off_PatternService;

	@Autowired
	LeaveSchemeService leaveSchemeService;
	
	@Autowired
	AttendanceSchemeService attendanceSchemeService;
	
	@Autowired
	LeavePeriodService leavePeriodService;
	
	@Autowired
	BranchService branchService;
	

	LeaveSchemeAdaptor leaveSchemeAdaptor=new LeaveSchemeAdaptor();
	ShiftAdaptor shiftAdaptor = new ShiftAdaptor();
	
	TMSWeekOffMasterPatternAdaptor tmsWeekOffMasterPatternAdaptor = new TMSWeekOffMasterPatternAdaptor();
	
	AttendanceSchemeAdaptor attendanceSchemeAdaptor=new AttendanceSchemeAdaptor();

	BranchAdaptor branchAdaptor  =new BranchAdaptor();
	
	/**
	 * @param file
	 *            This is the first parameter for taking file Input
	 * @param employeeDto
	 *            This is the second parameter for getting Employee Object from UI
	 * @param req
	 *            This is the third parameter to maintain user session
	 * @throws PayRollProcessException 
	 * @throws IOException 
	 * @throws InvalidFormatException 
	 * @throws EncryptedDocumentException 
	 * @throws CustomException 
	 * @throws ErrorHandling
	 */
	
	@RequestMapping(value = "/bulkInfoUpload", method = RequestMethod.POST, consumes = "multipart/form-data", produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ErrorHandling employeeUpload(@RequestPart("uploadFile") MultipartFile file,
				@RequestPart("info") EmployeeDTO employeeDto) throws PayRollProcessException,ErrorHandling, IOException, EncryptedDocumentException, InvalidFormatException, org.springframework.dao.DataIntegrityViolationException {
		logger.info("bulkUploadOfEmployee is calling : " + "" + ": uploadFile" + file);

		BulkUploadUtil bulkUploadUtil =new BulkUploadUtil();
		ErrorHandling error = new ErrorHandling();
		Map<Integer, StringBuilder> errorMap = new HashMap<Integer, StringBuilder>();
		
		ShiftDTO shiftDto = new ShiftDTO();
		
		LeaveSchemeMasterDTO leaveSchemeMasterDTO= null;
		if(employeeDto.getDropdownId().equals("Onboarding Details") && file !=null) {
			Map<Long, State> stateMap = loadStateInMap();
			Map<String,Long> reportingIds = loadEmployeeMap(employeeDto.getCompanyId());
			Map<Long,String> headerMap = loadExcelHeaderInMap("EOB");
			Map<Long,Department> departmentMap= loadDepartarmentInMap(employeeDto.getCompanyId());
			Map<Long, Designation> designationMap = loadDesignationsInMap(employeeDto.getCompanyId());
			Map<Long, Grade> gradeMap = loadGradesInMap(employeeDto.getCompanyId());
			Map<Long, RoleMaster> roleMap = loadRolesInMap();;
			//shiftDto = getshiftByRestTemplate(employeeDto.getCompanyId().toString());
			
			
			List<Shift> shiftList = shiftService.findAllShift(employeeDto.getCompanyId());
			List<ShiftDTO> shiftDtoList = shiftAdaptor.databaseModelToUiDtoList(shiftList);
			shiftDto.setShiftdto(shiftDtoList);
			
			TMSWeekOffMasterPatternDto WeekOffPatternDto = new TMSWeekOffMasterPatternDto();
			List<TMSWeekOffMasterPattern> week_Off_PatternList = week_Off_PatternService.getAllTMSWeekOffPattern(employeeDto.getCompanyId());
			logger.info("getAllWeekOffPattern is end  :" + week_Off_PatternList);
			List<TMSWeekOffMasterPatternDto> weekOffPatternDtoList = tmsWeekOffMasterPatternAdaptor.databaseModelToUiDtoList(week_Off_PatternList);
			WeekOffPatternDto.setWeekOffDto(weekOffPatternDtoList);
			
			
			
			///weekOffPatternDTO= getPatternByRestTamplate(employeeDto.getCompanyId().toString());
			Map<Long, ShiftDTO> shiftMap= loadShiftInMap(shiftDto);
			Map<Long, TMSWeekOffMasterPatternDto> weekOffMap = loadWeekOffInMap(WeekOffPatternDto);
		
			LeaveSchemeMasterDTO leaveSchemeMasterDto = new LeaveSchemeMasterDTO();
			List<TMSLeavePeriod> leavePeriodList= leavePeriodService.findleavePeriodStatus(employeeDto.getCompanyId());
			List<LeaveSchemeMaster> leaveSchemeList = leaveSchemeService.findLeaveScheme(leavePeriodList.get(0).getLeavePeriodId());
			List<LeaveSchemeMasterDTO> leaveSchemeDTOList = leaveSchemeAdaptor.databaseModelToUiDtoList(leaveSchemeList);
			leaveSchemeMasterDto.setLeaveSchemeMasterDto(leaveSchemeDTOList);
			
			
			
			AttendanceSchemeDTO attendanceSchemeDto = new AttendanceSchemeDTO();
			List<AttendanceScheme> attendanceSchemeList=attendanceSchemeService.allAttendanceSchemes();
			List<AttendanceSchemeDTO> attendanceSchemeDTOList = attendanceSchemeAdaptor.modelToUiDtoList(attendanceSchemeList);
			attendanceSchemeDto.setAttendanceSchemeDto(attendanceSchemeDTOList);
//			
			//leaveSchemeMasterDTO= getLeaveSchemeByRestTamplate(employeeDto.getCompanyId().toString());
			
			
			BranchDTO branchDto=new BranchDTO();
			List<Branch> branchList = branchService.findAllBranches(employeeDto.getCompanyId());
			List<BranchDTO> branchDTOList= branchAdaptor.databaseModelToUiDtoList(branchList);
			branchDto.setBranchDto(branchDTOList);
			
		
			Map<Long, LeaveSchemeMasterDTO> leaveSchemeMap = loadLeaveSchemeInMap(leaveSchemeMasterDto);
			Map<Long, AttendanceSchemeDTO> attendanceSchemeMap = loadAttendanceSchemeInMap(attendanceSchemeDto);
			Map<Long, BranchDTO>  branchMap=loadBranchInMap(branchDto);
			
		List<Employee> employeeList= bulkUploadUtil.saveEmployeeOnboard(file, employeeDto,headerMap,reportingIds,departmentMap,designationMap,gradeMap,roleMap,shiftMap,weekOffMap,stateMap,leaveSchemeMap,attendanceSchemeMap,branchMap,errorMap);
		
		logger.info("============6");
		if (errorMap.size() > 0) {
			StringBuilder builder = new StringBuilder();

			String message, value = null;
			for (Map.Entry<Integer, StringBuilder> entry : errorMap.entrySet()) {
				message = entry.getValue().substring(0, entry.getValue().lastIndexOf(","));
				builder.append("For row - " + entry.getKey() + " " + message + " is missing,  ");
			}
			logger.info("============7");
			error.setMessage("There is problem" + builder);
			throw new PayRollProcessException(builder.toString());
		} 
		else {
			logger.info("============8");
			if(employeeList.size() >0) {
			bulkUploadService.saveAll(employeeList, employeeDto,errorMap);
			logger.info("Employee File Uploaded Successfully.............");
			}
			else {
			
				logger.info("Employee File could not upload");
				throw new PayRollProcessException("There is problem in excel,Please upload correct excel format ");
			}
		}
	
		}
		else if(employeeDto.getDropdownId().equals("ID & Address Proof") && file !=null) {
			logger.info("============9");
			
			
			Map<Long,String> headerMap = loadExcelHeaderInMap("EIA");
			List<EmployeeIdProof> employeeIdProofList= bulkUploadUtil.saveEmployeIdAddress(file, employeeDto,headerMap, errorMap);
			if (errorMap.size() > 0) {
				StringBuilder builder = new StringBuilder();

				String message, value = null;
				for (Map.Entry<Integer, StringBuilder> entry : errorMap.entrySet()) {
					message = entry.getValue().substring(0, entry.getValue().lastIndexOf(","));
					builder.append("For row - " + entry.getKey() + " " + message + " is missing,  ");
				}
				logger.info("============10");
				error.setMessage("There is problem" + builder);
				throw new PayRollProcessException(builder.toString());
			} 
			else {
				logger.info("============11");
				if(employeeIdProofList.size() >0) {
				bulkUploadService.saveIdProof(employeeIdProofList, employeeDto,errorMap);
				logger.info("Employee Id proof File Uploaded Successfully.............");
				}
				else {
					logger.info("Employee Id proof File can not upload");
					throw new PayRollProcessException("There is problem in excel,Please upload correct excel format ");
				}
			
			}
			
		}
		else if(employeeDto.getDropdownId().equals("Professional Info") && file !=null) {
			logger.info("============9");
			Map<Long,String> headerMap = loadExcelHeaderInMap("EP");
			List<ProfessionalInformation> employeeProfessionalList= bulkUploadUtil.saveEmployeeProfessionalInfo(file, employeeDto,headerMap,errorMap);
			if (errorMap.size() > 0) {
				StringBuilder builder = new StringBuilder();
				String message, value = null;
				for (Map.Entry<Integer, StringBuilder> entry : errorMap.entrySet()) {
					message = entry.getValue().substring(0, entry.getValue().lastIndexOf(","));
					builder.append("For row - " + entry.getKey() + " " + message + " is missing,  ");
				}
				logger.info("============10");
				error.setMessage("There is problem" + builder);
				throw new PayRollProcessException(builder.toString());
			} 
			else {
				logger.info("============11");
				if(employeeProfessionalList.size() >0) {
				bulkUploadService.saveProfessionalInfo(employeeProfessionalList, employeeDto,errorMap);
				logger.info("Employee Professional File Uploaded Successfully.............");
				}
				else {
					logger.info("Employee Professional File can not upload");
					throw new PayRollProcessException("There is problem in excel,Please upload correct excel format ");
					
				}
			}
		}
		else if(employeeDto.getDropdownId().equals("Educational Info") && file !=null) {
			logger.info("============9");
			Map<Long,String> headerMap = loadExcelHeaderInMap("EE");
			List<EmployeeEducation> employeeEducationList= bulkUploadUtil.saveEmployeeEducationalInfo(file, employeeDto,headerMap,errorMap);
			if (errorMap.size() > 0) {
				StringBuilder builder = new StringBuilder();

				String message, value = null;
				for (Map.Entry<Integer, StringBuilder> entry : errorMap.entrySet()) {
					message = entry.getValue().substring(0, entry.getValue().lastIndexOf(","));
					builder.append("For row - " + entry.getKey() + " " + message + " is missing,  ");
				}
				logger.info("============10");
				error.setMessage("There is problem" + builder);
				throw new PayRollProcessException(builder.toString());
			} 
			else {
				logger.info("============11");
				if(employeeEducationList.size() >0) {
				bulkUploadService.saveEducationInfo(employeeEducationList, employeeDto,errorMap);
				logger.info("Employee Educational File Uploaded Successfully.............");
				}
				else {
					logger.info("Employee Educational File can not upload");
					throw new PayRollProcessException("There is problem in excel,Please upload correct excel format ");
					
				}
			}
		}
		else if(employeeDto.getDropdownId().equals("Family Details") && file !=null) {
			logger.info("============9");
			Map<Long,String> headerMap = loadExcelHeaderInMap("EF");
			List<EmployeeFamily> employeeFamilyList= bulkUploadUtil.saveEmployeFamily(file, employeeDto,headerMap,errorMap);
			if (errorMap.size() > 0) {
				StringBuilder builder = new StringBuilder();

				String message, value = null;
				for (Map.Entry<Integer, StringBuilder> entry : errorMap.entrySet()) {
					message = entry.getValue().substring(0, entry.getValue().lastIndexOf(","));
					builder.append("For row - " + entry.getKey() + " " + message + " is missing,  ");
				}
				logger.info("============10");
				error.setMessage("There is problem" + builder);
				throw new PayRollProcessException(builder.toString());
			} 
			else {
				logger.info("============11");
				if(employeeFamilyList.size() >0) {
				bulkUploadService.saveFamily(employeeFamilyList, employeeDto,errorMap);
				logger.info("Employee family File Uploaded Successfully.............");
				}
				else {
					logger.info("Employee family File can not upload");
					throw new PayRollProcessException("There is problem in excel,Please upload correct excel format ");
					
				}
			}
		}
		else if(employeeDto.getDropdownId().equals("Language Known Status") && file !=null) {
			logger.info("============9");
			Map<Long,String> headerMap = loadExcelHeaderInMap("EL");
			List<EmployeeLanguage> employeeLanguageList= bulkUploadUtil.saveEmployeeLanguage(file, employeeDto,headerMap,errorMap);
			if (errorMap.size() > 0) {
				StringBuilder builder = new StringBuilder();

				String message, value = null;
				for (Map.Entry<Integer, StringBuilder> entry : errorMap.entrySet()) {
					message = entry.getValue().substring(0, entry.getValue().lastIndexOf(","));
					builder.append("For row - " + entry.getKey() + " " + message + " is missing,  ");
				}
				logger.info("============10");
				error.setMessage("There is problem" + builder);
				throw new PayRollProcessException(builder.toString());
			} 
			else {
				logger.info("============11");
				if(employeeLanguageList.size() >0 ) {
				bulkUploadService.saveLanguage(employeeLanguageList, employeeDto,errorMap);
				logger.info("Employee Language  File Uploaded Successfully.............");
				}
				else {
					logger.info("Employee Language File can not upload");
					throw new PayRollProcessException("There is problem in excel,Please upload correct excel format ");
					
				}
			}
		}
		else if(employeeDto.getDropdownId().equals("UAN and PF Number") && file !=null) {
			logger.info("============9");
			Map<Long,String> headerMap = loadExcelHeaderInMap("EUP");
			List<EmployeeStatuary> employeeUANList= bulkUploadUtil.saveEmployeeUAN(file, employeeDto,headerMap,errorMap);
			if (errorMap.size() > 0) {
				StringBuilder builder = new StringBuilder();

				String message, value = null;
				for (Map.Entry<Integer, StringBuilder> entry : errorMap.entrySet()) {
					message = entry.getValue().substring(0, entry.getValue().lastIndexOf(","));
					builder.append("For row - " + entry.getKey() + " " + message + " is missing,  ");
				}
				logger.info("============10");
				error.setMessage("There is problem" + builder);
				throw new PayRollProcessException(builder.toString());
			} 
			else {
				logger.info("============11");
				if(employeeUANList.size() >0) {
				bulkUploadService.saveUanAndPf(employeeUANList, employeeDto,errorMap);
				logger.info("Employee UAN and PF FIle Uploaded Successfully.............");
				}
				else {
				
					logger.info("Employee UAN and PF FIle can not upload");
					throw new PayRollProcessException("There is problem in excel,Please upload correct excel format ");
					
				}
			}
			
				
		}
		else if(employeeDto.getDropdownId().equals("ESI Number") && file !=null) {
			logger.info("============9");
			Map<Long,String> headerMap = loadExcelHeaderInMap("EES");
			List<EmployeeStatuary> employeeESIList= bulkUploadUtil.saveEmployeeESI(file, employeeDto,headerMap,errorMap);
			if (errorMap.size() > 0) {
				StringBuilder builder = new StringBuilder();

				String message, value = null;
				for (Map.Entry<Integer, StringBuilder> entry : errorMap.entrySet()) {
					message = entry.getValue().substring(0, entry.getValue().lastIndexOf(","));
					builder.append("For row - " + entry.getKey() + " " + message + " is missing,  ");
				}
				logger.info("============10");
				error.setMessage("There is problem" + builder);
				throw new PayRollProcessException(builder.toString());
			} 
			else {
				logger.info("============11");
				if(employeeESIList.size() >0) {
				bulkUploadService.saveEsi(employeeESIList, employeeDto,errorMap);
				logger.info("Employee ESI FIle Uploaded Successfully.............");
				}
				else {
					logger.info("Employee ESI FIle can not upload");
					throw new PayRollProcessException("There is problem in excel,Please upload correct excel format ");
					
				}
			}
		}
		else if(employeeDto.getDropdownId().equals("Medical Insurance") && file !=null) {
			logger.info("============9");
			Map<Long,String> headerMap = loadExcelHeaderInMap("EMI");
			List<EmployeeStatuary> employeeMiList= bulkUploadUtil.saveEmployeeMi(file, employeeDto,headerMap,errorMap);
			if (errorMap.size() > 0) {
				StringBuilder builder = new StringBuilder();

				String message, value = null;
				for (Map.Entry<Integer, StringBuilder> entry : errorMap.entrySet()) {
					message = entry.getValue().substring(0, entry.getValue().lastIndexOf(","));
					builder.append("For row - " + entry.getKey() + " " + message + " is missing,  ");
				}
				logger.info("============10");
				error.setMessage("There is problem" + builder);
				throw new PayRollProcessException(builder.toString());
			} 
			else {
				logger.info("============11");
				if(employeeMiList.size() >0) {
				bulkUploadService.saveMi(employeeMiList, employeeDto,errorMap);
				logger.info("Employee MI FIle Uploaded Successfully.............");
				}
				else {
					logger.info("Employee MI FIle can not upload");
					throw new PayRollProcessException("There is problem in excel,Please upload correct excel format ");
					
				}
			}
			
				
		}
		else if(employeeDto.getDropdownId().equals("Accidental Insurance") && file !=null) {
			logger.info("============9");
			Map<Long,String> headerMap = loadExcelHeaderInMap("EAI");
			List<EmployeeStatuary> employeeMiList= bulkUploadUtil.saveEmployeeAi(file, employeeDto,headerMap,errorMap);
			if (errorMap.size() > 0) {
				StringBuilder builder = new StringBuilder();

				String message, value = null;
				for (Map.Entry<Integer, StringBuilder> entry : errorMap.entrySet()) {
					message = entry.getValue().substring(0, entry.getValue().lastIndexOf(","));
					builder.append("For row - " + entry.getKey() + " " + message + " is missing,  ");
				}
				logger.info("============10");
				error.setMessage("There is problem" + builder);
				throw new PayRollProcessException(builder.toString());
			} 
			else {
				logger.info("============11");
				if(employeeMiList.size() >0) {
				bulkUploadService.saveAi(employeeMiList, employeeDto,errorMap);
				logger.info("Employee AI FIle Uploaded Successfully.............");
				}
				
				else {
					logger.info("============11");
					logger.info("Employee AI FIle can not upload");
					throw new PayRollProcessException("There is problem in excel,Please upload correct excel format ");
					
				}
			}
			
				
		}
		
		else if(employeeDto.getDropdownId().equals("Paystructure") && file !=null) {
			logger.info("============9");
			Map<Long,String> headerMap = loadExcelHeaderInMap("EOP");
			List<PayStructureHd> employeePayStructureList= bulkUploadUtil.saveEmployeePaystructure(file,headerMap, employeeDto,errorMap);
			if (errorMap.size() > 0) {
				StringBuilder builder = new StringBuilder();

				String message, value = null;
				for (Map.Entry<Integer, StringBuilder> entry : errorMap.entrySet()) {
					message = entry.getValue().substring(0, entry.getValue().lastIndexOf(","));
					builder.append("For row - " + entry.getKey() + " " + message + " is missing,  ");
				}
				logger.info("============10");
				error.setMessage("There is problem" + builder);
				throw new PayRollProcessException(builder.toString());
			} 
			else {
				logger.info("============11");
				if(employeePayStructureList.size() >0) {
					// Saving Pay Structure
					 bulkUploadService.saveEmployeePaystructure(employeePayStructureList, employeeDto,errorMap) ;
					
				logger.info("Employee Paystructure FIle Uploaded Successfully.............");
				}
				
				else {
					logger.info("============11");
					logger.info("Employee Paystructure FIle can not upload");
					throw new PayRollProcessException("There is problem in excel,Please upload correct excel format ");
					
				}
			}
			
				
		}
		
		else {
			StringBuilder builder = new StringBuilder();
			builder.append("Please upload file ");
			error.setMessage("" + builder);
		}
		return error;
	}
	
	private Map<Long,String> loadExcelHeaderInMap(String fileCode) {
		List<EmployeeBulkUploadMaster> headers = bulkUploadService.findHeaderName(fileCode);
		Map<Long,String>  headerMap = new LinkedHashMap<Long,String> ();

		for (EmployeeBulkUploadMaster head :headers ) {
			headerMap.put( head.getIndexNumber(),head.getColumnHead());
		}
		return headerMap;
	}
	
	private Map<String,Long> loadEmployeeMap(Long companyId) {
		List<Employee> emp = bulkUploadService.findAllEmployee(companyId);
		Map<String,Long>  empMap = new LinkedHashMap<String,Long> ();

		for (Employee head :emp ) {
			empMap.put( head.getEmployeeCode(),head.getEmployeeId());
		}
		return empMap;
	}
	
	private Map<Long, Department> loadDepartarmentInMap(Long companyId) {
		List<Department> departments = departmentService.findAllDepartment(companyId);
		Map<Long, Department> departmentMap = new HashMap<Long, Department>();
		
		for (Department depart : departments) {
			//stateId = city.getState().getStateId();
			// key = city.getCityName()+"#"+stateId;
			departmentMap.put(depart.getDepartmentId(), depart);
		}
		return departmentMap;
	}
	
	private Map<Long, Designation> loadDesignationsInMap(Long companyId) {
		List<Designation> designations = designationService.findAllDesignation(companyId);
		Map<Long, Designation> designationMap = new HashMap<Long, Designation>();

		for (Designation designation : designations) {

			designationMap.put(designation.getDesignationId(), designation);
		}
		return designationMap;
	}
	private Map<Long, Grade> loadGradesInMap(Long companyId) {
		List<Grade> grades = gradeService.getAllGrades(companyId);
		Map<Long, Grade> gradeMap = new HashMap<Long, Grade>();

		for (Grade g : grades) {

			gradeMap.put(g.getGradesId(), g);
		}
		return gradeMap;
	}
	private Map<Long, RoleMaster> loadRolesInMap() {
		List<RoleMaster> roles = roleMasterService.getAllRoleMasters();
		Map<Long, RoleMaster> roleMap = new HashMap<Long, RoleMaster>();

		for (RoleMaster g : roles) {

			roleMap.put(g.getRoleId(), g);
		}
		return roleMap;
	}
	private ShiftDTO getshiftByRestTemplate(String companyId) {
		
		logger.info("getshiftByRestTamplate is calling : ");
		String url = environment.getProperty("application.shiftIdsOnCompanyId");
 
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders header = new HttpHeaders();
		header.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		Map<String, Long> params = new HashMap<>();
		params.put("companyId", Long.parseLong(companyId));
		
	    System.out.println("==========="+restTemplate.getForObject(url, ShiftDTO.class, params)); 
		return restTemplate.getForObject(url, ShiftDTO.class, params);
	}
	private Map<Long, ShiftDTO> loadShiftInMap(ShiftDTO shiftDto) {
		Map<Long, ShiftDTO> shiftMap = new HashMap<Long, ShiftDTO>();
		for (ShiftDTO g : shiftDto.getShiftdto()) {
			shiftMap.put(g.getShiftId(), g);
		}
		return shiftMap;
	}
	
	private TMSWeekOffMasterPatternDto getPatternByRestTamplate(String companyId) {
		
		logger.info("getshiftByRestTamplate is calling : ");
		String url = environment.getProperty("application.weekoffpatternOnCompanyId");
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders header = new HttpHeaders();
		header.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		Map<String, Long> params = new HashMap<>();
		params.put("companyId", Long.parseLong(companyId));
		
	    //System.out.println("==========="+restTemplate.getForObject(url, ShiftDTO.class, params)); 
		return (restTemplate.getForObject(url, TMSWeekOffMasterPatternDto.class, params));
	}
	
	private Map<Long, TMSWeekOffMasterPatternDto> loadWeekOffInMap(TMSWeekOffMasterPatternDto weekOffPattenDto) {
		Map<Long, TMSWeekOffMasterPatternDto> weekOffMap = new HashMap<Long, TMSWeekOffMasterPatternDto>();
		for (TMSWeekOffMasterPatternDto g : weekOffPattenDto.getWeekOffDto()) {
			weekOffMap.put(g.getPatternId(), g);
		}
		return weekOffMap;
	}											
	
private LeaveSchemeMasterDTO getLeaveSchemeByRestTamplate(String companyId) {
		
		logger.info("getLeaveSchemeByRestTamplate is calling : ");
		String url = environment.getProperty("application.leaveSchemeCompanyId");
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders header = new HttpHeaders();
		header.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		Map<String, Long> params = new HashMap<>();
		params.put("companyId", Long.parseLong(companyId));
		
	    System.out.println("==========="+restTemplate.getForObject(url, LeaveSchemeMasterDTO.class, params)); 
		return (restTemplate.getForObject(url, LeaveSchemeMasterDTO.class, params));
	}

	private Map<Long, LeaveSchemeMasterDTO> loadLeaveSchemeInMap(LeaveSchemeMasterDTO leaveSchemeMasterDTO) {
		Map<Long, LeaveSchemeMasterDTO> leaveSchemeMap = new HashMap<Long, LeaveSchemeMasterDTO>();
		for (LeaveSchemeMasterDTO g : leaveSchemeMasterDTO.getLeaveSchemeMasterDto()) {
			leaveSchemeMap.put(g.getLeaveSchemeId(), g);
		}
		return leaveSchemeMap;
	}
	
	private Map<Long, AttendanceSchemeDTO> loadAttendanceSchemeInMap(AttendanceSchemeDTO attendanceSchemeDto) {
		Map<Long, AttendanceSchemeDTO> attendanceSchemeMap = new HashMap<Long, AttendanceSchemeDTO>();
		for (AttendanceSchemeDTO g : attendanceSchemeDto.getAttendanceSchemeDto()) {
			attendanceSchemeMap.put(g.getAttendanceSchemeId(), g);
		}
		return attendanceSchemeMap;
	}

	
	private Map<Long,BranchDTO> loadBranchInMap(BranchDTO branchDto)
	{
		Map<Long, BranchDTO> branchMap=new HashMap<Long, BranchDTO>();
		
		for(BranchDTO  b :branchDto.getBranchDto())
		{
			branchMap.put(b.getBranchId(), b);
		}
		
		return branchMap;
		
	}



	private Map<Long, State> loadStateInMap() {
		List<State> states = stateService.findAllState();
		Map<Long, State> stateMap = new HashMap<Long, State>();
		for (State state : states) {
			//stateId = city.getState().getStateId();
			// key = city.getCityName()+"#"+stateId;
			stateMap.put(state.getStateId(), state);
		}
		return stateMap;
	}
	

}
	

