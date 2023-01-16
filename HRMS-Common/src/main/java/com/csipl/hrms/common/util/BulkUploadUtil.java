package com.csipl.hrms.common.util;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import com.csipl.hrms.common.exception.PayRollProcessException;
//import com.csipl.hrms.dto.common.LeaveSchemeMasterDTO;
//import com.csipl.hrms.dto.common.TMSWeekOffMasterPatternDto;
import com.csipl.hrms.dto.common.WeekOffPatternDTO;
import com.csipl.hrms.dto.employee.EmployeeDTO;
import com.csipl.hrms.dto.organisation.BranchDTO;
import com.csipl.hrms.model.authoriztion.RoleMaster;
import com.csipl.hrms.model.common.Address;
import com.csipl.hrms.model.common.Company;
import com.csipl.hrms.model.common.State;
import com.csipl.hrms.model.employee.Employee;
import com.csipl.hrms.model.employee.EmployeeBank;
import com.csipl.hrms.model.employee.EmployeeEducation;
import com.csipl.hrms.model.employee.EmployeeFamily;
import com.csipl.hrms.model.employee.EmployeeIdProof;
import com.csipl.hrms.model.employee.EmployeeLanguage;
import com.csipl.hrms.model.employee.EmployeeStatuary;
import com.csipl.hrms.model.employee.PayStructure;
import com.csipl.hrms.model.employee.PayStructureHd;
import com.csipl.hrms.model.employee.ProfessionalInformation;
import com.csipl.hrms.model.organisation.Department;
import com.csipl.hrms.model.organisation.Designation;
import com.csipl.hrms.model.organisation.Grade;
import com.csipl.tms.dto.attendancescheme.AttendanceSchemeDTO;
import com.csipl.tms.dto.leave.LeaveSchemeMasterDTO;
import com.csipl.tms.dto.shift.ShiftDTO;
import com.csipl.tms.dto.weekofpattern.TMSWeekOffMasterPatternDto;

public class BulkUploadUtil {

	private static final Logger logger = LoggerFactory.getLogger(BulkUploadUtil.class);

	public List<Employee> saveEmployeeOnboard(MultipartFile file, EmployeeDTO employeeDto, Map<Long, String> headerMap,
			Map<String, Long> reportingIds, Map<Long, Department> departmentMap, Map<Long, Designation> designationMap,
			Map<Long, Grade> gradeMap, Map<Long, RoleMaster> roleMap, Map<Long, ShiftDTO> shiftMap,
			Map<Long, TMSWeekOffMasterPatternDto> weekOffMap, Map<Long, State> stateMap, Map<Long, LeaveSchemeMasterDTO> leaveSchemeMap, Map<Long, AttendanceSchemeDTO> attendanceSchemeMap, Map<Long, BranchDTO> branchMap, Map<Integer, StringBuilder> errorMap)
			throws IOException, EncryptedDocumentException, InvalidFormatException, PayRollProcessException {

		Workbook workbook = WorkbookFactory.create(file.getInputStream());
		logger.info("workbook is : " + workbook);
		List<Employee> employees = new ArrayList<Employee>();
		try {
			String empCodePrefix = "";
			Sheet sheet = workbook.getSheetAt(0);
			FormulaEvaluator evaluator = workbook.getCreationHelper().createFormulaEvaluator();

			List<Integer> employeeCodes = new ArrayList<Integer>();
			EmpUploadUtil infoUtill = new EmpUploadUtil();
			StringBuilder stringBuilder = new StringBuilder();

			int lastRow = sheet.getLastRowNum();
			int errorIndex = 0;
			logger.info("============1");

			HashMap<Long, String> map = new LinkedHashMap<>();
			// to check column values from DB
			for (int i = 0; i <= sheet.getRow(0).getLastCellNum(); i++) {

				Cell cell = sheet.getRow(0).getCell(i);
				if (cell == null || cell.getStringCellValue() == null) {
					break;

				} else {
					String columnName = sheet.getRow(0).getCell(i).getStringCellValue();
					columnName = columnName.replace("*", "");
					Long colIndex = (long) cell.getColumnIndex();
					map.put(colIndex, columnName);
					while (map.values().remove(""))
						;
					// System.out.println(columnName);
				}
			}
			System.out.println(map);
			System.out.println("headermap from db =================" + headerMap);

			boolean check = false;
			check = checkIfExcelIsCorrect(map, headerMap);
			if (check == true)
				throw new PayRollProcessException("Please upload correct excel format");

			for (int index = 2; index < lastRow; index++) {
				logger.info("============2");
				Row rowData = sheet.getRow(index);
				boolean flag = checkIfRowIsLast(rowData, infoUtill);
				if (flag)
					break;

				sheet.setColumnHidden(1, false);
				Employee employee = new Employee();
				Address presentAddress = new Address();
				Address permanentAddress = new Address();
				Address referenceAddress = new Address();
				EmployeeBank bankDetails = new EmployeeBank();
				Company company = new Company();

				bankDetails.setActiveStatus("AC");
				bankDetails.setDateCreated(new Date());
				bankDetails.setUserId(employeeDto.getUserId());
				List<EmployeeBank> employeeBanks = new ArrayList<EmployeeBank>();

				company.setCompanyId(employeeDto.getCompanyId());
				employee.setCompanyId(employeeDto.getCompanyId());
				employee.setCompany(company);
				employee.setUserId(employeeDto.getUserId());

				logger.info("============3");
				for (int colIx = infoUtill.Employee_Code; colIx <= infoUtill.IfscCode; colIx++) {
					Cell cell = rowData.getCell(colIx);

					System.out.println(colIx);
					if (colIx == infoUtill.Employee_Code || colIx == infoUtill.First_Name
							|| colIx == infoUtill.Middle_Name || colIx == infoUtill.Last_Name || colIx == infoUtill.DOB
							|| colIx == infoUtill.AadharCardNo || colIx == infoUtill.ContactNo
							|| colIx == infoUtill.EmailId || colIx == infoUtill.JobLocationStateID
							|| colIx == infoUtill.JobLocationID || colIx == infoUtill.ShiftID
							|| colIx == infoUtill.WeeklyOffPatternID  || colIx == infoUtill.AttendanceSchemeID || colIx == infoUtill.LeaveSchemeID || colIx == infoUtill.DepartmentID
							|| colIx == infoUtill.DesignationID    || colIx == infoUtill.BranchID    || colIx == infoUtill.ReportingToID
							|| colIx == infoUtill.GradeID || colIx == infoUtill.ProbationDays
							|| colIx == infoUtill.NoticePeriod || colIx == infoUtill.EmployeeType_ID
							|| colIx == infoUtill.RoleID || colIx == infoUtill.DateOfJoining
							|| colIx == infoUtill.ContractStartDate || colIx == infoUtill.ContractEndDate
							|| colIx == infoUtill.TimeContract  || colIx == infoUtill.OfficialEmailID  || colIx == infoUtill.DOB || colIx == infoUtill.Gender_ID
							|| colIx == infoUtill.BloodGroup_ID || colIx == infoUtill.AlternateNo
							|| colIx == infoUtill.Marital_ID || colIx == infoUtill.AnniversaryDate
							|| colIx == infoUtill.ReferenceName || colIx == infoUtill.ReferenceContactNo
							|| colIx == infoUtill.ReferenceEmail) {

						if (colIx == infoUtill.Employee_Code) {
							String value = cell.getStringCellValue();
							if (value == null) {
								stringBuilder.append("Employee Code can't empty");
								throw new PayRollProcessException(stringBuilder.toString());
							} 
//							else if (value != null && value.indexOf("-") > 0) {
//								String code = value.substring(value.lastIndexOf("-") + 1, value.length());
//								employeeCodes.add(Integer.parseInt(code));
//							}
//							String[] parts = value.split("-");
//							empCodePrefix = parts[0] + "-";// COM
						}

						infoUtill.buildEmployee(employee, reportingIds, cell, colIx, evaluator, departmentMap,
								designationMap, gradeMap, roleMap, shiftMap, weekOffMap, stateMap,leaveSchemeMap,attendanceSchemeMap,branchMap, stringBuilder);
					}

					if (colIx == infoUtill.Present_Address_AddressText || colIx == infoUtill.Present_Address_City_ID
							|| colIx == infoUtill.Present_Address_Country_ID
							|| colIx == infoUtill.Present_Address_State_ID
							|| colIx == infoUtill.Present_Address_Pin_Code || colIx == infoUtill.ContactNo
							|| colIx == infoUtill.EmailId) {
						infoUtill.buildPresentAddress(presentAddress, cell, colIx, evaluator, stringBuilder, stateMap,
								index);
					}
					if (colIx == infoUtill.Permanent_Address_AddressText || colIx == infoUtill.Permanent_Address_City_ID
							|| colIx == infoUtill.Permanent_Address_CountryID
							|| colIx == infoUtill.Permanent_Address_State_ID
							|| colIx == infoUtill.Permanent_Address_Pin_Code || colIx == infoUtill.ContactNo
							|| colIx == infoUtill.EmailId) {

						infoUtill.builPermanentdAddress(permanentAddress, cell, colIx, evaluator, stateMap,
								stringBuilder);
					}
					if (colIx == infoUtill.Reference_Address_AddressText || colIx == infoUtill.Reference_Address_City_ID
							|| colIx == infoUtill.Reference_Address_Country_ID
							|| colIx == infoUtill.Reference_Address_State_ID
							|| colIx == infoUtill.Reference_Address_Pin_Code) {

						infoUtill.builReferencedAddress(referenceAddress, cell, colIx, evaluator, stringBuilder);
					}
					if (colIx == infoUtill.BankId || colIx == infoUtill.BankBranch || colIx == infoUtill.AccountNumber
							|| colIx == infoUtill.IfscCode) {

						infoUtill.buildBankDetails(bankDetails, cell, colIx, evaluator, stringBuilder);

					}
				}
				logger.info("============4");
				if (stringBuilder.length() > 0) {
					errorMap.put(errorIndex, stringBuilder);
				} else {
					employee.setAddress1(presentAddress);
					employee.setAddress2(permanentAddress);

					if (referenceAddress != null && referenceAddress.getCity() != null
							&& referenceAddress.getState() != null && referenceAddress.getCountry() != null) {
						employee.setAddress3(referenceAddress);
					}
					bankDetails.setEmployee(employee);
					employeeBanks.add(bankDetails);
					employee.setEmployeeBanks(employeeBanks);
					employees.add(employee);
					errorIndex++;
					logger.info("============5");
					workbook.close();

				}
			}
		} catch (IOException ex) {

			workbook.close();
			ex.printStackTrace();
			throw new PayRollProcessException("Internal Server error");
		} catch (ParseException e) {
			workbook.close();
			e.printStackTrace();
			throw new PayRollProcessException("Internal Server error");
		}
		return employees;
	}

	// ID & Address Proof
	public List<EmployeeIdProof> saveEmployeIdAddress(MultipartFile file, EmployeeDTO employeeDto,
			Map<Long, String> headerMap, Map<Integer, StringBuilder> errorMap)
			throws IOException, EncryptedDocumentException, InvalidFormatException, PayRollProcessException {

		Workbook workbook = WorkbookFactory.create(file.getInputStream());
		logger.info("workbook is : " + workbook);
		List<EmployeeIdProof> employeeIdProof = new ArrayList<EmployeeIdProof>();
		try {
			EmpUploadUtil infoUtill = new EmpUploadUtil();
			StringBuilder stringBuilder = new StringBuilder();

			Sheet sheet = workbook.getSheetAt(0);
			FormulaEvaluator evaluator = workbook.getCreationHelper().createFormulaEvaluator();
			int lastRow = sheet.getLastRowNum();

			HashMap<Long, String> map = new LinkedHashMap<>();
			for (int i = 0; i <= sheet.getRow(0).getLastCellNum(); i++) {
				Cell cell = sheet.getRow(0).getCell(i);
				if (cell == null || cell.getStringCellValue().equals("") || cell.getStringCellValue() == null) {
					break;

				} else {
					String columnName = sheet.getRow(0).getCell(i).getStringCellValue();
					columnName = columnName.replace("*", "");
					Long colIndex = (long) cell.getColumnIndex();
					map.put(colIndex, columnName);
				}
			}
			System.out.println(map);
			boolean check = checkIfExcelIsCorrect(map, headerMap);
			if (check)
				throw new PayRollProcessException("Please upload correct excel format");

			int errorIndex = 0;
			logger.info("============1" + "lastRow  --" + lastRow);
			logger.info("============2");
			for (int index = 2; index < lastRow; index++) {
				Row rowData = sheet.getRow(index);
				sheet.setColumnHidden(1, false);
				boolean flag = checkIfRowIsLastIDAddress(rowData, infoUtill);
				if (flag)
					break;

				EmployeeIdProof empIdProof = new EmployeeIdProof();
				empIdProof.setUserId(employeeDto.getUserId());

				logger.info("============3");
				for (int colIx = infoUtill.Employee_Code; colIx <= infoUtill.expiryDate; colIx++) {

					Cell cell = rowData.getCell(colIx);

					System.out.println(colIx);
					if (colIx == infoUtill.Employee_Code || colIx == infoUtill.IdType_ID || colIx == infoUtill.IdNumber
							|| colIx == infoUtill.issueDate || colIx == infoUtill.expiryDate) {

						if (colIx == infoUtill.Employee_Code) {
							String value = cell.getStringCellValue();
							if (value == null) {
								stringBuilder.append("Employee Code can't empty");
								throw new PayRollProcessException(stringBuilder.toString());
							}
						}
						infoUtill.buildIdAndAddressProof(empIdProof, cell, colIx, evaluator, stringBuilder);
					}

				}
				logger.info("============4");
				if (stringBuilder.length() > 0) {
					errorMap.put(errorIndex, stringBuilder);
				} else {
					errorIndex++;
					logger.info("============5");
					addUniqueEmpID(empIdProof, employeeIdProof);
					// employeeIdProof.add(empIdProof);
					workbook.close();

				}
			}

		} catch (IOException ex) {

			workbook.close();
			ex.printStackTrace();
			throw new PayRollProcessException("Internal Server error");
		} catch (ParseException e) {
			workbook.close();
			e.printStackTrace();
			throw new PayRollProcessException("Internal Server error");
		}
		return employeeIdProof;
	}

//To upload Professional information
	public List<ProfessionalInformation> saveEmployeeProfessionalInfo(MultipartFile file, EmployeeDTO employeeDto,
			Map<Long, String> headerMap, Map<Integer, StringBuilder> errorMap)
			throws IOException, EncryptedDocumentException, InvalidFormatException, PayRollProcessException {
		Workbook workbook = WorkbookFactory.create(file.getInputStream());
		logger.info("workbook is : " + workbook);
		List<ProfessionalInformation> employeeProfessionalList = new ArrayList<ProfessionalInformation>();
		try {
			Sheet sheet = workbook.getSheetAt(0);
			FormulaEvaluator evaluator = workbook.getCreationHelper().createFormulaEvaluator();
			int lastRow = sheet.getLastRowNum();
			int errorIndex = 0;
			logger.info("============1");
			EmpUploadUtil infoUtill = new EmpUploadUtil();
			StringBuilder stringBuilder = new StringBuilder();
			HashMap<Long, String> map = new LinkedHashMap<>();
			for (int i = 0; i <= sheet.getRow(0).getLastCellNum(); i++) {
				Cell cell = sheet.getRow(0).getCell(i);
				if (cell == null || cell.getStringCellValue().equals("") || cell.getStringCellValue() == null) {
					break;

				} else {
					String columnName = sheet.getRow(0).getCell(i).getStringCellValue();
					columnName = columnName.replace("*", "");
					Long colIndex = (long) cell.getColumnIndex();
					map.put(colIndex, columnName);
				}
			}
			System.out.println(map);
			boolean check = checkIfExcelIsCorrect(map, headerMap);
			if (check)
				throw new PayRollProcessException("Please upload correct excel format");

			logger.info("============2");
			for (int index = 2; index < lastRow; index++) {
				Row rowData = sheet.getRow(index);
				sheet.setColumnHidden(1, false);
				boolean flag = checkIfRowIsLastProfessionalInfo(rowData, infoUtill);
				if (flag)
					break;

				ProfessionalInformation empProfession = new ProfessionalInformation();
				empProfession.setUserId(employeeDto.getUserId());
				logger.info("============3");
				for (int colIx = infoUtill.Employee_Code; colIx <= infoUtill.Reason_For_Change_ID; colIx++) {

					Cell cell = rowData.getCell(colIx);
					System.out.println(colIx);
					if (colIx == infoUtill.Employee_Code || colIx == infoUtill.Org_Name || colIx == infoUtill.Work_From
							|| colIx == infoUtill.Work_TO || colIx == infoUtill.Designation_Pro
							|| colIx == infoUtill.Reason_For_Change_ID || colIx == infoUtill.ReportingTO_Pro
							|| colIx == infoUtill.Reporting_Contact || colIx == infoUtill.Annual_Salary) {

						if (colIx == infoUtill.Employee_Code) {
							String value = cell.getStringCellValue();
							if (value == null) {
								stringBuilder.append("Employee Code can't empty");
								throw new PayRollProcessException(stringBuilder.toString());
							}
						}
						infoUtill.buildEmployeeProfessional(empProfession, cell, colIx, evaluator, stringBuilder);
					}

				}
				logger.info("============4");
				if (stringBuilder.length() > 0) {
					errorMap.put(errorIndex, stringBuilder);
				} else {
					errorIndex++;
					logger.info("============5");
					employeeProfessionalList.add(empProfession);
					workbook.close();

				}
			}
		} catch (IOException ex) {

			workbook.close();
			ex.printStackTrace();
			throw new PayRollProcessException("Internal Server error");
		} catch (ParseException e) {
			workbook.close();
			e.printStackTrace();
			throw new PayRollProcessException("Internal Server error");
		}
		return employeeProfessionalList;
	}

//To upload Educational information
	public List<EmployeeEducation> saveEmployeeEducationalInfo(MultipartFile file, EmployeeDTO employeeDto,
			Map<Long, String> headerMap, Map<Integer, StringBuilder> errorMap)
			throws IOException, EncryptedDocumentException, InvalidFormatException, PayRollProcessException {

		Workbook workbook = WorkbookFactory.create(file.getInputStream());
		logger.info("workbook is : " + workbook);
		List<EmployeeEducation> employeeEducationalList = new ArrayList<EmployeeEducation>();
		try {
			Sheet sheet = workbook.getSheetAt(0);
			FormulaEvaluator evaluator = workbook.getCreationHelper().createFormulaEvaluator();
			int lastRow = sheet.getLastRowNum();
			int errorIndex = 0;
			logger.info("============1");
			EmpUploadUtil infoUtill = new EmpUploadUtil();
			StringBuilder stringBuilder = new StringBuilder();
			HashMap<Long, String> map = new LinkedHashMap<>();
			for (int i = 0; i <= sheet.getRow(0).getLastCellNum(); i++) {
				Cell cell = sheet.getRow(0).getCell(i);
				if (cell == null || cell.getStringCellValue().equals("") || cell.getStringCellValue() == null) {
					break;

				} else {
					String columnName = sheet.getRow(0).getCell(i).getStringCellValue();
					columnName = columnName.replace("*", "");
					Long colIndex = (long) cell.getColumnIndex();
					map.put(colIndex, columnName);
				}
			}
			System.out.println(map);
			boolean check = checkIfExcelIsCorrect(map, headerMap);
			if (check)
				throw new PayRollProcessException("Please upload correct excel format");

			logger.info("============2");
			for (int index = 2; index < lastRow; index++) {
				Row rowData = sheet.getRow(index);
				sheet.setColumnHidden(1, false);
				boolean flag = checkIfRowIsLastEducationalInfo(rowData, infoUtill);
				if (flag)
					break;

				EmployeeEducation empEducation = new EmployeeEducation();
				empEducation.setUserId(employeeDto.getUserId());
				logger.info("============3");
				for (int colIx = infoUtill.Employee_Code; colIx <= infoUtill.Regular_Corr; colIx++) {

					Cell cell = rowData.getCell(colIx);
					System.out.println(colIx);
					if (colIx == infoUtill.Employee_Code || colIx == infoUtill.Education_Level_ID
							|| colIx == infoUtill.Degree_ID || colIx == infoUtill.School_College_Name
							|| colIx == infoUtill.Board_Of_Education || colIx == infoUtill.Marks
							|| colIx == infoUtill.Passing_Year || colIx == infoUtill.Regular_Corr) {

						if (colIx == infoUtill.Employee_Code) {
							String value = cell.getStringCellValue();
							if (value == null) {
								stringBuilder.append("Employee Code can't empty");
								throw new PayRollProcessException(stringBuilder.toString());
							}
						}
						infoUtill.buildEmployeeEducational(empEducation, cell, colIx, evaluator, stringBuilder);
					}

				}
				logger.info("============4");
				if (stringBuilder.length() > 0) {
					errorMap.put(errorIndex, stringBuilder);
				} else {
					errorIndex++;
					logger.info("============5");
					employeeEducationalList.add(empEducation);
					workbook.close();

				}
			}
		} catch (IOException ex) {

			workbook.close();
			ex.printStackTrace();
			throw new PayRollProcessException("Internal Server error");
		} catch (ParseException e) {
			workbook.close();
			e.printStackTrace();
			throw new PayRollProcessException("Internal Server error");
		}
		return employeeEducationalList;
	}

//To upload Family details
	public List<EmployeeFamily> saveEmployeFamily(MultipartFile file, EmployeeDTO employeeDto,
			Map<Long, String> headerMap, Map<Integer, StringBuilder> errorMap)
			throws IOException, EncryptedDocumentException, InvalidFormatException, PayRollProcessException {

		Workbook workbook = WorkbookFactory.create(file.getInputStream());
		logger.info("workbook is : " + workbook);
		List<EmployeeFamily> employeeFamilyList = new ArrayList<EmployeeFamily>();
		try {
			Sheet sheet = workbook.getSheetAt(0);
			FormulaEvaluator evaluator = workbook.getCreationHelper().createFormulaEvaluator();
			int lastRow = sheet.getLastRowNum();
			int errorIndex = 0;
			logger.info("============1");

			EmpUploadUtil infoUtill = new EmpUploadUtil();
			StringBuilder stringBuilder = new StringBuilder();

			HashMap<Long, String> map = new LinkedHashMap<>();
			for (int i = 0; i <= sheet.getRow(0).getLastCellNum(); i++) {
				Cell cell = sheet.getRow(0).getCell(i);
				if (cell == null || cell.getStringCellValue().equals("") || cell.getStringCellValue() == null) {
					break;

				} else {
					String columnName = sheet.getRow(0).getCell(i).getStringCellValue();
					columnName = columnName.replace("*", "");
					Long colIndex = (long) cell.getColumnIndex();
					map.put(colIndex, columnName);
				}
			}
			System.out.println(map);
			boolean check = checkIfExcelIsCorrect(map, headerMap);
			if (check)
				throw new PayRollProcessException("Please upload correct excel format");

			logger.info("============2");
			for (int index = 2; index < lastRow; index++) {

				Row rowData = sheet.getRow(index);
				sheet.setColumnHidden(1, false);
				boolean flag = checkIfRowIsLastFamily(rowData, infoUtill);
				if (flag)
					break;
				EmployeeFamily empFamily = new EmployeeFamily();
				empFamily.setUserId(employeeDto.getUserId());

				logger.info("============3");
				for (int colIx = infoUtill.Employee_Code; colIx <= infoUtill.Contact_No; colIx++) {

					Cell cell = rowData.getCell(colIx);
					System.out.println(colIx);
					if (colIx == infoUtill.Employee_Code || colIx == infoUtill.Title_ID || colIx == infoUtill.Name
							|| colIx == infoUtill.Date_Of_Birth || colIx == infoUtill.Contact_No
							|| colIx == infoUtill.Relation_ID || colIx == infoUtill.Education_ID
							|| colIx == infoUtill.Occupation_ID) {

						if (colIx == infoUtill.Employee_Code) {
							String value = cell.getStringCellValue();
							if (value == null) {
								stringBuilder.append("Employee Code can't empty");
								throw new PayRollProcessException(stringBuilder.toString());
							}
						}
						infoUtill.buildEmployeeFamily(empFamily, cell, colIx, evaluator, stringBuilder);
					}

				}
				logger.info("============4");
				if (stringBuilder.length() > 0) {
					errorMap.put(errorIndex, stringBuilder);
				} else {
					errorIndex++;
					logger.info("============5");
					employeeFamilyList.add(empFamily);
					workbook.close();

				}
			}
		} catch (IOException ex) {

			workbook.close();
			ex.printStackTrace();
			throw new PayRollProcessException("Internal Server error");
		} catch (ParseException e) {
			workbook.close();
			e.printStackTrace();
			throw new PayRollProcessException("Internal Server error");
		}
		return employeeFamilyList;
	}

//To upload Language Details
	public List<EmployeeLanguage> saveEmployeeLanguage(MultipartFile file, EmployeeDTO employeeDto,
			Map<Long, String> headerMap, Map<Integer, StringBuilder> errorMap)
			throws IOException, EncryptedDocumentException, InvalidFormatException, PayRollProcessException {

		Workbook workbook = WorkbookFactory.create(file.getInputStream());
		logger.info("workbook is : " + workbook);
		List<EmployeeLanguage> employeeLanguageList = new ArrayList<EmployeeLanguage>();
		try {
			Sheet sheet = workbook.getSheetAt(0);
			FormulaEvaluator evaluator = workbook.getCreationHelper().createFormulaEvaluator();
			int lastRow = sheet.getLastRowNum();
			int errorIndex = 0;
			logger.info("============1");

			EmpUploadUtil infoUtill = new EmpUploadUtil();
			StringBuilder stringBuilder = new StringBuilder();

			HashMap<Long, String> map = new LinkedHashMap<>();
			for (int i = 0; i <= sheet.getRow(0).getLastCellNum(); i++) {
				Cell cell = sheet.getRow(0).getCell(i);
				if (cell == null || cell.getStringCellValue().equals("") || cell.getStringCellValue() == null) {
					break;

				} else {
					String columnName = sheet.getRow(0).getCell(i).getStringCellValue();
					columnName = columnName.replace("*", "");
					Long colIndex = (long) cell.getColumnIndex();
					map.put(colIndex, columnName);
				}
			}
			System.out.println(map);
			boolean check = checkIfExcelIsCorrect(map, headerMap);
			if (check)
				throw new PayRollProcessException("Please upload correct excel format");

			logger.info("============2");
			for (int index = 2; index < lastRow; index++) {

				Row rowData = sheet.getRow(index);
				sheet.setColumnHidden(1, false);
				boolean flag = checkIfRowIsLastLanguage(rowData, infoUtill);
				if (flag)
					break;
				EmployeeLanguage empLang = new EmployeeLanguage();
				Employee e = new Employee();
				e.setUserId(employeeDto.getUserId());
				empLang.setEmployee(e);

				logger.info("============3");
				for (int colIx = EmpUploadUtil.Employee_Code; colIx <= EmpUploadUtil.Speak; colIx++) {

					Cell cell = rowData.getCell(colIx);
					System.out.println(colIx);
					if (colIx == infoUtill.Employee_Code || colIx == infoUtill.Language_ID || colIx == infoUtill.Read
							|| colIx == infoUtill.Write || colIx == infoUtill.Speak) {
						if (colIx == infoUtill.Employee_Code) {
							String value = cell.getStringCellValue();
							if (value == null) {
								stringBuilder.append("Employee Code can't empty");
								throw new PayRollProcessException(stringBuilder.toString());
							}
						}
						infoUtill.buildEmployeeLanguage(empLang, cell, colIx, evaluator, stringBuilder);
					}

				}
				logger.info("============4");
				if (stringBuilder.length() > 0) {
					errorMap.put(errorIndex, stringBuilder);
				} else {
					errorIndex++;
					logger.info("============5");

					addUniqueEmpLang(empLang, employeeLanguageList);
					// employeeLanguageList.add(empLang);
					workbook.close();

				}
			}
		} catch (IOException ex) {

			workbook.close();
			ex.printStackTrace();
			throw new PayRollProcessException("Internal Server error");
		} catch (ParseException e) {
			workbook.close();
			e.printStackTrace();
			throw new PayRollProcessException("Internal Server error");
		}
		return employeeLanguageList;
	}

//To upload UAN and PF,
	public List<EmployeeStatuary> saveEmployeeUAN(MultipartFile file, EmployeeDTO employeeDto,
			Map<Long, String> headerMap, Map<Integer, StringBuilder> errorMap)
			throws IOException, EncryptedDocumentException, InvalidFormatException, PayRollProcessException {

		Workbook workbook = WorkbookFactory.create(file.getInputStream());
		logger.info("workbook is : " + workbook);
		List<EmployeeStatuary> employeeStatuaryList = new ArrayList<EmployeeStatuary>();
		try {
			Sheet sheet = workbook.getSheetAt(0);
			FormulaEvaluator evaluator = workbook.getCreationHelper().createFormulaEvaluator();
			int lastRow = sheet.getLastRowNum();
			int errorIndex = 0;
			logger.info("============1");

			EmpUploadUtil infoUtill = new EmpUploadUtil();
			StringBuilder stringBuilder = new StringBuilder();
			HashMap<Long, String> map = new LinkedHashMap<>();
			for (int i = 0; i <= sheet.getRow(0).getLastCellNum(); i++) {
				Cell cell = sheet.getRow(0).getCell(i);
				if (cell == null || cell.getStringCellValue().equals("") || cell.getStringCellValue() == null) {
					break;

				} else {
					String columnName = sheet.getRow(0).getCell(i).getStringCellValue();
					columnName = columnName.replace("*", "");
					Long colIndex = (long) cell.getColumnIndex();
					map.put(colIndex, columnName);
				}
			}
			System.out.println(map);
			boolean check = checkIfExcelIsCorrect(map, headerMap);
			if (check)
				throw new PayRollProcessException("Please upload correct excel format");

			logger.info("============2");
			for (int index = 2; index < lastRow; index++) {

				Row rowData = sheet.getRow(index);

				sheet.setColumnHidden(1, false);
				boolean flag = checkIfRowIsLastUAN(rowData, infoUtill);
				if (flag)
					break;

				EmployeeStatuary empUanAndPf = new EmployeeStatuary();
				EmployeeStatuary empStatuary = new EmployeeStatuary();
				empUanAndPf.setUserId(employeeDto.getUserId());

				logger.info("============3");
				for (int colIx = infoUtill.Employee_Code; colIx <= infoUtill.Effective_To; colIx++) {

					Cell cell = rowData.getCell(colIx);
					System.out.println(colIx);
					if (colIx == infoUtill.Employee_Code || colIx == infoUtill.UAN_Number
							|| colIx == infoUtill.PF_Number || colIx == infoUtill.Effective_From || colIx == infoUtill.Effective_To) {
						if (colIx == infoUtill.Employee_Code) {
							String value = cell.getStringCellValue();
							if (value == null) {
								stringBuilder.append("Employee Code can't empty");
								throw new PayRollProcessException(stringBuilder.toString());
							}
						}

						infoUtill.buildEmployeeUanAndPf(empUanAndPf, empStatuary, cell, colIx, evaluator,
								stringBuilder);
					}

				}
				logger.info("============4");
				if (stringBuilder.length() > 0) {
					errorMap.put(errorIndex, stringBuilder);
				} else {
					errorIndex++;
					logger.info("============5");
					addUniqueUAN(empUanAndPf, employeeStatuaryList);
					addUniqueUAN(empStatuary, employeeStatuaryList);
					// employeeStatuaryList.add(empUanAndPf);
					// employeeStatuaryList.add(empStatuary);
					workbook.close();

				}
			}
		} catch (IOException ex) {

			workbook.close();
			ex.printStackTrace();
			throw new PayRollProcessException("Internal Server error");
		} catch (ParseException e) {
			workbook.close();
			e.printStackTrace();
			throw new PayRollProcessException("Internal Server error");
		}
		return employeeStatuaryList;
	}

//To upload ESIC Number
	public List<EmployeeStatuary> saveEmployeeESI(MultipartFile file, EmployeeDTO employeeDto,
			Map<Long, String> headerMap, Map<Integer, StringBuilder> errorMap)
			throws IOException, EncryptedDocumentException, InvalidFormatException, PayRollProcessException {

		Workbook workbook = WorkbookFactory.create(file.getInputStream());
		logger.info("workbook is : " + workbook);
		List<EmployeeStatuary> employeeStatuaryList = new ArrayList<EmployeeStatuary>();
		try {
			Sheet sheet = workbook.getSheetAt(0);
			FormulaEvaluator evaluator = workbook.getCreationHelper().createFormulaEvaluator();
			int lastRow = sheet.getLastRowNum();
			int errorIndex = 0;
			logger.info("============1");

			EmpUploadUtil infoUtill = new EmpUploadUtil();
			StringBuilder stringBuilder = new StringBuilder();
			HashMap<Long, String> map = new LinkedHashMap<>();
			for (int i = 0; i <= sheet.getRow(0).getLastCellNum(); i++) {
				Cell cell = sheet.getRow(0).getCell(i);
				if (cell == null || cell.getStringCellValue().equals("") || cell.getStringCellValue() == null) {
					break;

				} else {
					String columnName = sheet.getRow(0).getCell(i).getStringCellValue();
					columnName = columnName.replace("*", "");
					Long colIndex = (long) cell.getColumnIndex();
					map.put(colIndex, columnName);
				}
			}
			System.out.println(map);
			boolean check = checkIfExcelIsCorrect(map, headerMap);
			if (check)
				throw new PayRollProcessException("Please upload correct excel format");

			logger.info("============2");
			for (int index = 2; index < lastRow; index++) {

				Row rowData = sheet.getRow(index);
				sheet.setColumnHidden(1, false);
				boolean flag = checkIfRowIsLastESI(rowData, infoUtill);
				if (flag)
					break;
				EmployeeStatuary empEsi = new EmployeeStatuary();
				empEsi.setUserId(employeeDto.getUserId());

				logger.info("============3");
				for (int colIx = infoUtill.Employee_Code; colIx <= infoUtill.Effective_to_Esi; colIx++) {

					Cell cell = rowData.getCell(colIx);
					System.out.println(colIx);
					if (colIx == infoUtill.Employee_Code || colIx == infoUtill.ESIC_Number
							|| colIx == infoUtill.Effective_from_Esi || colIx == infoUtill.Effective_to_Esi) {

						if (colIx == infoUtill.Employee_Code) {
							String value = cell.getStringCellValue();
							if (value == null) {
								stringBuilder.append("Employee Code can't empty");
								throw new PayRollProcessException(stringBuilder.toString());
							}
						}
						infoUtill.buildEmployeeEsi(empEsi, cell, colIx, evaluator, stringBuilder);
					}

				}
				logger.info("============4");
				if (stringBuilder.length() > 0) {
					errorMap.put(errorIndex, stringBuilder);
				} else {
					errorIndex++;
					logger.info("============5");
					employeeStatuaryList.add(empEsi);
					workbook.close();

				}
			}
		} catch (IOException ex) {

			workbook.close();
			ex.printStackTrace();
			throw new PayRollProcessException("Internal Server error");
		} catch (ParseException e) {
			workbook.close();
			e.printStackTrace();
			throw new PayRollProcessException("Internal Server error");
		}
		return employeeStatuaryList;
	}

//To upload Medical Insurance
	public List<EmployeeStatuary> saveEmployeeMi(MultipartFile file, EmployeeDTO employeeDto,
			Map<Long, String> headerMap, Map<Integer, StringBuilder> errorMap)
			throws IOException, EncryptedDocumentException, InvalidFormatException, PayRollProcessException {

		Workbook workbook = WorkbookFactory.create(file.getInputStream());
		logger.info("workbook is : " + workbook);
		List<EmployeeStatuary> employeeStatuaryList = new ArrayList<EmployeeStatuary>();
		try {
			Sheet sheet = workbook.getSheetAt(0);
			FormulaEvaluator evaluator = workbook.getCreationHelper().createFormulaEvaluator();
			int lastRow = sheet.getLastRowNum();
			int errorIndex = 0;
			logger.info("============1");
			EmpUploadUtil infoUtill = new EmpUploadUtil();
			StringBuilder stringBuilder = new StringBuilder();

			HashMap<Long, String> map = new LinkedHashMap<>();
			for (int i = 0; i <= sheet.getRow(0).getLastCellNum(); i++) {
				Cell cell = sheet.getRow(0).getCell(i);
				if (cell == null || cell.getStringCellValue().equals("") || cell.getStringCellValue() == null) {
					break;

				} else {
					String columnName = sheet.getRow(0).getCell(i).getStringCellValue();
					columnName = columnName.replace("*", "");
					Long colIndex = (long) cell.getColumnIndex();
					map.put(colIndex, columnName);
				}
			}
			System.out.println(map);
			boolean check = checkIfExcelIsCorrect(map, headerMap);
			if (check)
				throw new PayRollProcessException("Please upload correct excel format");

			logger.info("============2");
			for (int index = 2; index < lastRow; index++) {

				Row rowData = sheet.getRow(index);
				sheet.setColumnHidden(1, false);
				boolean flag = checkIfRowIsLastMi(rowData, infoUtill);
				if (flag)
					break;
				EmployeeStatuary empEsi = new EmployeeStatuary();
				empEsi.setUserId(employeeDto.getUserId());

				logger.info("============3");
				for (int colIx = infoUtill.Employee_Code; colIx <= infoUtill.Effective_To_Mi; colIx++) {
					Cell cell = rowData.getCell(colIx);
					if (colIx == infoUtill.Employee_Code) {
						String value = cell.getStringCellValue();
						if (value == null) {
							stringBuilder.append("Employee Code can't empty");
							throw new PayRollProcessException(stringBuilder.toString());
						}
					}
					System.out.println(colIx);
					if (colIx == infoUtill.Employee_Code || colIx == infoUtill.Mi_Number
							|| colIx == infoUtill.Effective_To_Mi || colIx == infoUtill.Effective_from_Mi) {

						infoUtill.buildEmployeeMi(empEsi, cell, colIx, evaluator, stringBuilder);
					}
				}
				logger.info("============4");
				if (stringBuilder.length() > 0) {
					errorMap.put(errorIndex, stringBuilder);
				} else {
					errorIndex++;
					logger.info("============5");
					employeeStatuaryList.add(empEsi);
					workbook.close();

				}
			}
		} catch (IOException ex) {

			workbook.close();
			ex.printStackTrace();
			throw new PayRollProcessException("Internal Server error");
		} catch (ParseException e) {
			workbook.close();
			e.printStackTrace();
			throw new PayRollProcessException("Internal Server error");
		}
		return employeeStatuaryList;
	}

//To upload Accidental Insurance
	public List<EmployeeStatuary> saveEmployeeAi(MultipartFile file, EmployeeDTO employeeDto,
			Map<Long, String> headerMap, Map<Integer, StringBuilder> errorMap)
			throws IOException, EncryptedDocumentException, InvalidFormatException, PayRollProcessException {

		Workbook workbook = WorkbookFactory.create(file.getInputStream());
		logger.info("workbook is : " + workbook);
		List<EmployeeStatuary> employeeStatuaryList = new ArrayList<EmployeeStatuary>();
		try {
			Sheet sheet = workbook.getSheetAt(0);
			FormulaEvaluator evaluator = workbook.getCreationHelper().createFormulaEvaluator();
			int lastRow = sheet.getLastRowNum();
			int errorIndex = 0;
			logger.info("============1");

			EmpUploadUtil infoUtill = new EmpUploadUtil();
			StringBuilder stringBuilder = new StringBuilder();
			HashMap<Long, String> map = new LinkedHashMap<>();
			for (int i = 0; i <= sheet.getRow(0).getLastCellNum(); i++) {
				Cell cell = sheet.getRow(0).getCell(i);
				if (cell == null || cell.getStringCellValue().equals("") || cell.getStringCellValue() == null) {
					break;

				} else {
					String columnName = sheet.getRow(0).getCell(i).getStringCellValue();
					columnName = columnName.replace("*", "");
					Long colIndex = (long) cell.getColumnIndex();
					map.put(colIndex, columnName);
				}
			}
			System.out.println(map);
			boolean check = checkIfExcelIsCorrect(map, headerMap);
			if (check)
				throw new PayRollProcessException("Please upload correct excel format");

			logger.info("============2");
			for (int index = 2; index < lastRow; index++) {

				Row rowData = sheet.getRow(index);
				sheet.setColumnHidden(1, false);
				boolean flag = checkIfRowIsLastAi(rowData, infoUtill);
				if (flag)
					break;
				EmployeeStatuary empEsi = new EmployeeStatuary();
				empEsi.setUserId(employeeDto.getUserId());

				logger.info("============3");
				for (int colIx = infoUtill.Employee_Code; colIx <= infoUtill.Effective_To_Ai; colIx++) {
					Cell cell = rowData.getCell(colIx);
					System.out.println(colIx);
					if (colIx == infoUtill.Employee_Code) {
						String value = cell.getStringCellValue();
						if (value == null) {
							stringBuilder.append("Employee Code can't empty");
							throw new PayRollProcessException(stringBuilder.toString());
						}
					}
					if (colIx == infoUtill.Employee_Code || colIx == infoUtill.Effective_from_Ai
							|| colIx == infoUtill.Effective_To_Ai || colIx == infoUtill.Ai_Number) {

						infoUtill.buildEmployeeAi(empEsi, cell, colIx, evaluator, stringBuilder);
					}

				}
				logger.info("============4");
				if (stringBuilder.length() > 0) {
					errorMap.put(errorIndex, stringBuilder);
				} else {
					errorIndex++;
					logger.info("============5");
					employeeStatuaryList.add(empEsi);
					workbook.close();

				}
			}
		} catch (IOException ex) {

			workbook.close();
			ex.printStackTrace();
			throw new PayRollProcessException("Internal Server error");
		} catch (ParseException e) {
			workbook.close();
			e.printStackTrace();
			throw new PayRollProcessException("Internal Server error");
		}
		return employeeStatuaryList;
	}

//To upload Pay structure 
	public List<PayStructureHd> saveEmployeePaystructure(MultipartFile file,Map<Long, String> headerMap, EmployeeDTO employeeDto,
			Map<Integer, StringBuilder> errorMap)
			throws IOException, EncryptedDocumentException, InvalidFormatException, PayRollProcessException {

		Workbook workbook = WorkbookFactory.create(file.getInputStream());
		logger.info("workbook is : " + workbook);
		List<PayStructureHd> employeePaystrcutureList = new ArrayList<PayStructureHd>();
		try {
			Sheet sheet = workbook.getSheetAt(0);
			FormulaEvaluator evaluator = workbook.getCreationHelper().createFormulaEvaluator();
			int lastRow = sheet.getLastRowNum();
			int errorIndex = 0;
			logger.info("============1");

			EmpUploadUtil infoUtill = new EmpUploadUtil();
			StringBuilder stringBuilder = new StringBuilder();
			
			HashMap<Long, String> map = new LinkedHashMap<>();
			for (int i = 0; i <= sheet.getRow(0).getLastCellNum(); i++) {
				Cell cell = sheet.getRow(0).getCell(i);
				if (cell == null || cell.getStringCellValue().equals("") || cell.getStringCellValue() == null) {
					break;

				} else {
					String columnName = sheet.getRow(0).getCell(i).getStringCellValue();
					columnName = columnName.replace("*", "");
					Long colIndex = (long) cell.getColumnIndex();
					map.put(colIndex, columnName);
				}
			}
			System.out.println(map);
			boolean check = checkIfExcelIsCorrect(map, headerMap);
			if (check)
				throw new PayRollProcessException("Please upload correct excel format");
 
			logger.info("============2");
			for (int index = 2; index < lastRow; index++) {

				Row rowData = sheet.getRow(index);
				sheet.setColumnHidden(1, false);
				boolean flag = checkIfRowIsLastPayStructure(rowData, infoUtill);
				if (flag)
					break;
				PayStructureHd empPay = new PayStructureHd();
				PayStructure empHead = new PayStructure();
				empPay.setUserId(employeeDto.getUserId());

				logger.info("============3");
				for (int colIx = infoUtill.Employee_Code; colIx <= infoUtill.LWFStatus; colIx++) {
					Cell cell = rowData.getCell(colIx);
					System.out.println(colIx);
					if (colIx == infoUtill.Employee_Code) {
						String value = cell.getStringCellValue();
						if (value == null) {
							stringBuilder.append("Employee Code can't empty");
							throw new PayRollProcessException(stringBuilder.toString());
						}
					}
					if (colIx == infoUtill.Employee_Code || colIx == infoUtill.GrossPay

							|| colIx == infoUtill.ProcessMonth || colIx == infoUtill.NetPay || colIx == infoUtill.CTC
							|| colIx == infoUtill.PFEmployee || colIx == infoUtill.PFEmployer   || colIx == infoUtill.EPSEmployer
							|| colIx == infoUtill.ESIEmployee || colIx == infoUtill.ESIEmployer  
							|| colIx == infoUtill.LWFEmployee || colIx == infoUtill.LWFEmployer
							|| colIx == infoUtill.ProfessionalTax || colIx == infoUtill.PayHeadID
							|| colIx == infoUtill.Amount || colIx == infoUtill.PFStatus 
							|| colIx == infoUtill.ESIStatus  || colIx == infoUtill.LWFStatus  
							
							) {
						infoUtill.buildEmployeePaystructure(empPay, cell, colIx, evaluator, stringBuilder);
					}

				}
				logger.info("============4");
				if (stringBuilder.length() > 0) {
					errorMap.put(errorIndex, stringBuilder);
				} else {
					errorIndex++;
					logger.info("============5");
					// empPay.setPayStructures(empHead);
					employeePaystrcutureList.add(empPay);
					workbook.close();

				}
			}
		} catch (IOException ex) {

			workbook.close();
			ex.printStackTrace();
			throw new PayRollProcessException("Internal Server error");
		} catch (ParseException e) {
			workbook.close();
			e.printStackTrace();
			throw new PayRollProcessException("Internal Server error");
		} catch (Exception e) {
			workbook.close();
			e.printStackTrace();
			throw new PayRollProcessException("Internal Server error");
		}
		return employeePaystrcutureList;
	}

	/*
	 * //To upload leave opening balance public List<EmployeeOpeningLeaveMaster>
	 * saveEmployeeLeaveOpeningBalance(MultipartFile file, EmployeeDTO
	 * employeeDto,Map<Integer, StringBuilder> errorMap) throws IOException,
	 * EncryptedDocumentException, InvalidFormatException, PayRollProcessException {
	 * 
	 * Workbook workbook = WorkbookFactory.create(file.getInputStream());
	 * logger.info("workbook is : " + workbook); List<EmployeeOpeningLeaveMaster>
	 * employeeLeave= new ArrayList<EmployeeOpeningLeaveMaster>(); try {
	 * EmpUploadUtil infoUtill = new EmpUploadUtil(); StringBuilder stringBuilder =
	 * new StringBuilder();
	 * 
	 * Sheet sheet = workbook.getSheetAt(0); FormulaEvaluator evaluator =
	 * workbook.getCreationHelper().createFormulaEvaluator(); int lastRow =
	 * sheet.getLastRowNum();
	 * 
	 * 
	 * HashMap<Long,String> map = new LinkedHashMap<>(); for(int i =0;
	 * i<=sheet.getRow(0).getLastCellNum();i++) { Cell cell =
	 * sheet.getRow(0).getCell(i); if(cell == null ||
	 * cell.getStringCellValue().equals("") || cell.getStringCellValue() == null) {
	 * break;
	 * 
	 * } else { String columnName=sheet.getRow(0).getCell(i).getStringCellValue();
	 * columnName= columnName.replace("*", ""); Long colIndex = (long)
	 * cell.getColumnIndex(); map.put( colIndex,columnName); } }
	 * System.out.println(map); boolean check =
	 * checkIfExcelIsCorrect(map,headerMap); if(check) throw new
	 * PayRollProcessException("Please upload correct excel format");
	 * 
	 * int errorIndex = 0; logger.info("============1"+"lastRow  --"+lastRow);
	 * logger.info("============2"); for (int index = 2; index < lastRow; index++) {
	 * Row rowData = sheet.getRow(index); sheet.setColumnHidden(1,false); boolean
	 * flag = checkIfRowIsEmployeeLeaveOpening(rowData, infoUtill); if (flag) break;
	 * 
	 * EmployeeOpeningLeaveMaster empLeaveOpening = new
	 * EmployeeOpeningLeaveMaster();
	 * empLeaveOpening.setUserId(employeeDto.getUserId());
	 * 
	 * logger.info("============3"); for (int colIx = infoUtill.Employee_Code; colIx
	 * <= infoUtill.ConsumedLeave; colIx++) {
	 * 
	 * Cell cell = rowData.getCell(colIx);
	 * 
	 * System.out.println(colIx); if (colIx == infoUtill.Employee_Code || colIx ==
	 * infoUtill.LeaveTypeID || colIx == infoUtill.ConsumedLeave ) {
	 * 
	 * 
	 * if (colIx == infoUtill.Employee_Code) { String value =
	 * cell.getStringCellValue(); if(value == null) {
	 * stringBuilder.append("Employee Code can't empty"); throw new
	 * PayRollProcessException(stringBuilder.toString()); } }
	 * infoUtill.buildEmployeeLeaveBalance(empLeaveOpening, cell, colIx, evaluator,
	 * stringBuilder); }
	 * 
	 * } logger.info("============4"); if (stringBuilder.length() > 0) {
	 * errorMap.put(errorIndex, stringBuilder); } else { errorIndex++;
	 * logger.info("============5"); //addUniqueEmpID(empIdProof,employeeIdProof);
	 * employeeLeave.add(empLeaveOpening); workbook.close();
	 * 
	 * } }
	 * 
	 * 
	 * } catch (IOException ex) {
	 * 
	 * workbook.close(); ex.printStackTrace(); throw new
	 * PayRollProcessException("Internal Server error"); } catch (ParseException e)
	 * { workbook.close(); e.printStackTrace(); throw new
	 * PayRollProcessException("Internal Server error"); } return employeeLeave; }
	 */
	/*
	 * private boolean checkIfRowIsLast(Row row, EmpUploadUtil infoUtill) { boolean
	 * flag = false; if (row == null) { return true; } if (row.getLastCellNum() <=
	 * 0) { return true; } for(int c= row.getFirstCellNum(); c<
	 * row.getLastCellNum(); c++) { Cell cell = row.getCell(c); if(cell != null &&
	 * cell.getCellType() != cell.CELL_TYPE_BLANK ) return false; }
	 * 
	 * return true; }
	 */

	private boolean checkIfRowIsLast(Row row, EmpUploadUtil infoUtill) {
		boolean flag = false;

		for (int colIx = infoUtill.Employee_Code; colIx < infoUtill.IfscCode; colIx++) {
			Cell cell = row.getCell(colIx);

			if (colIx == infoUtill.Employee_Code) {
				if (cell == null || cell.getCellType() == Cell.CELL_TYPE_BLANK) {
					flag = true;
					break;
				}
			}
		}

		return flag;
	}

	private boolean checkIfRowIsLastIDAddress(Row row, EmpUploadUtil infoUtill) {
		boolean flag = false;

		for (int colIx = infoUtill.Employee_Code; colIx < infoUtill.expiryDate; colIx++) {
			Cell cell = row.getCell(colIx);

			if (colIx == infoUtill.Employee_Code) {
				if (cell == null || cell.getCellType() == Cell.CELL_TYPE_BLANK) {
					flag = true;
					break;
				}
			}
		}

		return flag;
	}

	private boolean checkIfRowIsLastFamily(Row row, EmpUploadUtil infoUtill) {
		boolean flag = false;

		for (int colIx = infoUtill.Employee_Code; colIx < infoUtill.Contact_No; colIx++) {
			Cell cell = row.getCell(colIx);

			if (colIx == infoUtill.Employee_Code) {
				if (cell == null || cell.getCellType() == Cell.CELL_TYPE_BLANK) {
					flag = true;
					break;
				}
			}
		}

		return flag;
	}

	private boolean checkIfRowIsLastProfessionalInfo(Row row, EmpUploadUtil infoUtill) {
		boolean flag = false;

		for (int colIx = infoUtill.Employee_Code; colIx < infoUtill.Reason_For_Change_ID; colIx++) {
			Cell cell = row.getCell(colIx);

			if (colIx == infoUtill.Employee_Code) {
				if (cell == null || cell.getCellType() == Cell.CELL_TYPE_BLANK) {
					flag = true;
					break;
				}
			}
		}

		return flag;
	}

	private boolean checkIfRowIsLastEducationalInfo(Row row, EmpUploadUtil infoUtill) {
		boolean flag = false;

		for (int colIx = infoUtill.Employee_Code; colIx < infoUtill.Regular_Corr; colIx++) {
			Cell cell = row.getCell(colIx);

			if (colIx == infoUtill.Employee_Code) {
				if (cell == null || cell.getCellType() == Cell.CELL_TYPE_BLANK) {
					flag = true;
					break;
				}
			}
		}

		return flag;
	}

	private boolean checkIfRowIsLastLanguage(Row row, EmpUploadUtil infoUtill) {
		boolean flag = false;

		for (int colIx = infoUtill.Employee_Code; colIx < infoUtill.Speak; colIx++) {
			Cell cell = row.getCell(colIx);

			if (colIx == infoUtill.Employee_Code) {
				if (cell == null || cell.getCellType() == Cell.CELL_TYPE_BLANK) {
					flag = true;
					break;
				}
			}
		}

		return flag;
	}

	private boolean checkIfRowIsLastUAN(Row row, EmpUploadUtil infoUtill) {
		boolean flag = false;

		for (int colIx = infoUtill.Employee_Code; colIx < infoUtill.Effective_To; colIx++) {
			Cell cell = row.getCell(colIx);

			if (colIx == infoUtill.Employee_Code) {
				if (cell == null || cell.getCellType() == Cell.CELL_TYPE_BLANK) {
					flag = true;
					break;
				}
			}
		}

		return flag;
	}

	private boolean checkIfRowIsLastESI(Row row, EmpUploadUtil infoUtill) {
		boolean flag = false;

		for (int colIx = infoUtill.Employee_Code; colIx < infoUtill.Effective_to_Esi; colIx++) {
			Cell cell = row.getCell(colIx);

			if (colIx == infoUtill.Employee_Code) {
				if (cell == null || cell.getCellType() == Cell.CELL_TYPE_BLANK) {
					flag = true;
					break;
				}
			}
		}

		return flag;
	}

	private boolean checkIfRowIsLastMi(Row row, EmpUploadUtil infoUtill) {
		boolean flag = false;

		for (int colIx = infoUtill.Employee_Code; colIx < infoUtill.Effective_To_Mi; colIx++) {
			Cell cell = row.getCell(colIx);

			if (colIx == infoUtill.Employee_Code) {
				if (cell == null || cell.getCellType() == Cell.CELL_TYPE_BLANK) {
					flag = true;
					break;
				}
			}
		}

		return flag;
	}

	private boolean checkIfRowIsLastAi(Row row, EmpUploadUtil infoUtill) {
		boolean flag = false;

		for (int colIx = infoUtill.Employee_Code; colIx < infoUtill.Effective_To_Ai; colIx++) {
			Cell cell = row.getCell(colIx);

			if (colIx == infoUtill.Employee_Code) {
				if (cell == null || cell.getCellType() == Cell.CELL_TYPE_BLANK) {
					flag = true;
					break;
				}
			}
		}

		return flag;
	}

	private boolean checkIfRowIsLastPayStructure(Row row, EmpUploadUtil infoUtill) {
		boolean flag = false;

		for (int colIx = infoUtill.Employee_Code; colIx < infoUtill.LWFStatus; colIx++) {
			Cell cell = row.getCell(colIx);

			if (colIx == infoUtill.Employee_Code) {
				if (cell == null || cell.getCellType() == Cell.CELL_TYPE_BLANK) {
					flag = true;
					break;
				}
			}
		}

		return flag;
	}

	private boolean checkIfRowIsEmployeeLeaveOpening(Row row, EmpUploadUtil infoUtill) {
		boolean flag = false;

		for (int colIx = infoUtill.Employee_Code; colIx < infoUtill.expiryDate; colIx++) {
			Cell cell = row.getCell(colIx);

			if (colIx == infoUtill.Employee_Code) {
				if (cell == null || cell.getCellType() == Cell.CELL_TYPE_BLANK) {
					flag = true;
					break;
				}
			}
		}

		return flag;
	}

	private boolean checkIfExcelIsCorrect(HashMap<Long, String> map, Map<Long, String> headerMap) {
		boolean flag = false;

		if (map.size() != headerMap.size()) {
			return true;
		}
		try {
			for (Long k : headerMap.keySet()) {
				if (!map.get(k).equals(headerMap.get(k))) {
					return true;
				}
			}
			/*
			 * for (Long y : map.keySet()) { if (!headerMap.containsKey(y)) { return true; }
			 * }
			 */
		} catch (NullPointerException np) {
			return true;
		}
		return false;
	}

	// check in excel employee language can not be repeated.
	private void addUniqueEmpLang(EmployeeLanguage empLang, List<EmployeeLanguage> employeeLanguageList)
			throws PayRollProcessException {
		boolean flag = false;

		for (EmployeeLanguage employeeLanguage : employeeLanguageList) {
			if (employeeLanguage.getLanguage().getLanguageId() == empLang.getLanguage().getLanguageId()
					&& employeeLanguage.getEmployee().getEmployeeCode()
							.equals(empLang.getEmployee().getEmployeeCode())) {
				flag = true;
				break;
			}

		}
		if (flag) {
			throw new PayRollProcessException(
					empLang.getEmployee().getEmployeeCode() + "  Duplicate Entry found for the employee  in excel");
		} else {
			employeeLanguageList.add(empLang);
		}

	}

	// check in excel and store in list if there is ID proof is already present
	private void addUniqueEmpID(EmployeeIdProof empIdProof, List<EmployeeIdProof> employeeIdProofList)
			throws PayRollProcessException {
		boolean flag = false;

		for (EmployeeIdProof employeeIdProof : employeeIdProofList) {
			if (employeeIdProof.getIdTypeId().equals(empIdProof.getIdTypeId())&& (employeeIdProof.getEmployee()
					.getEmployeeCode().equals(empIdProof.getEmployee().getEmployeeCode()))
					
					|| (employeeIdProof.getIdNumber().equals(empIdProof.getIdNumber())) && (employeeIdProof.getEmployee()
							.getEmployeeCode().equals(empIdProof.getEmployee().getEmployeeCode()))) {
				flag = true;
				break;
			}

		}
		if (flag) {
			throw new PayRollProcessException(
					empIdProof.getEmployee().getEmployeeCode() + "  Duplicate Entry found for the employee  in excel");
		} else {
			employeeIdProofList.add(empIdProof);
		}

	}

	// check in excel employee UAN can not be repeated for same employee.
	private void addUniqueUAN(EmployeeStatuary empUanAndPf, List<EmployeeStatuary> employeeStatuaryList)
			throws PayRollProcessException {
		boolean flag = false;

		for (EmployeeStatuary employeeUAN : employeeStatuaryList) {
			if ((employeeUAN.getEmployee().getEmployeeCode().equals(empUanAndPf.getEmployee().getEmployeeCode()))
					&& (employeeUAN.getStatuaryNumber().equals(empUanAndPf.getStatuaryNumber()))

					|| (employeeUAN.getStatuaryType().equals(empUanAndPf.getStatuaryType()))
							&& (employeeUAN.getStatuaryNumber().equals(empUanAndPf.getStatuaryNumber()))) {
				flag = true;
				break;
			}

		}
		if (flag) {
			throw new PayRollProcessException(
					empUanAndPf.getEmployee().getEmployeeCode() + "  Duplicate Entry found for the employee  in excel");
		} else {
			employeeStatuaryList.add(empUanAndPf);
		}

	}

}
