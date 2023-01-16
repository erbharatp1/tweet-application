package com.csipl.hrms.common.util;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellValue;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.FormulaEvaluator;

//import com.csipl.hrms.dto.common.LeaveSchemeMasterDTO;
import com.csipl.hrms.dto.common.WeekOffPatternDTO;
import com.csipl.hrms.dto.employee.PayStructureHdDTO;
import com.csipl.hrms.dto.organisation.BranchDTO;
import com.csipl.hrms.model.authoriztion.RoleMaster;
import com.csipl.hrms.model.common.Address;
import com.csipl.hrms.model.common.Branch;
import com.csipl.hrms.model.common.City;
import com.csipl.hrms.model.common.Country;
import com.csipl.hrms.model.common.Language;
import com.csipl.hrms.model.common.State;
import com.csipl.hrms.model.employee.Employee;
import com.csipl.hrms.model.employee.EmployeeBank;
import com.csipl.hrms.model.employee.EmployeeEducation;
import com.csipl.hrms.model.employee.EmployeeFamily;
import com.csipl.hrms.model.employee.EmployeeIdProof;
import com.csipl.hrms.model.employee.EmployeeLanguage;
import com.csipl.hrms.model.employee.EmployeeSkill;
import com.csipl.hrms.model.employee.EmployeeStatuary;
import com.csipl.hrms.model.employee.PayStructure;
import com.csipl.hrms.model.employee.PayStructureHd;
import com.csipl.hrms.model.employee.ProfessionalInformation;
import com.csipl.hrms.model.employee.Skill;
import com.csipl.hrms.model.organisation.Department;
import com.csipl.hrms.model.organisation.Designation;
//import com.csipl.tms.dto.shift.ShiftDTO;
//import com.csipl.tms.model.shift.Shift;
//import com.csipl.tms.model.weekofpattern.WeekOffPattern;
import com.csipl.hrms.model.organisation.Grade;
import com.csipl.tms.dto.attendancescheme.AttendanceSchemeDTO;
import com.csipl.tms.dto.leave.LeaveSchemeMasterDTO;
import com.csipl.tms.dto.shift.ShiftDTO;
import com.csipl.tms.dto.weekofpattern.TMSWeekOffMasterPatternDto;

public class EmpUploadUtil {

	public EmpUploadUtil() {
		
	}
	
	public static final BigDecimal Min_Gross_Salary_G1 = new BigDecimal(5000);
	public static final BigDecimal Max_Gross_Salary_G1 = new BigDecimal(35000);
	public static final BigDecimal Min_Gross_Salary_G2 = new BigDecimal(35001);
	public static final BigDecimal Max_Gross_Salary_G2 = new BigDecimal(80000);
	public static final BigDecimal Min_Gross_Salary_G3 = new BigDecimal(80001);

	public static final int Employee_Code = 0;
	public static final int First_Name = 1;
	public static final int Middle_Name = 2;
	public static final int Last_Name = 3;
	public static final int AadharCardNo = 4;
	public static final int ContactNo = 5;
	public static final int EmailId = 6;
	public static final int JobLocation = 7;
	public static final int JobLocationID=8;
	public static final int JobLocationState=9;
	public static final int JobLocationStateID=10;
	public static final int Shift = 11;
	public static final int ShiftID= 12;
	public static final int WeeklyOffPattern = 13;
	public static final int WeeklyOffPatternID = 14;
	public static final int AttendanceScheme= 15;
	public static final int AttendanceSchemeID = 16;
	public static final int LeaveScheme = 17;
	public static final int LeaveSchemeID = 18;
	public static final int Department = 19;
	public static final int DepartmentID = 20;
	public static final int Designation = 21;
	public static final int DesignationID = 22;
	public static final int BranchName = 23;
	public static final int BranchID = 24;
	public static final int ReportingToID=25;
	public static final int Grade = 26;
	public static final int GradeID = 27;
	public static final int ProbationDays = 28;
	public static final int NoticePeriod = 29;
	public static final int EmployeeType_ID = 30;
	public static final int DateOfJoining = 31;
	public static final int ContractStartDate = 32;
	public static final int ContractEndDate = 33;
	public static final int TimeContract= 34;
	public static final int OfficialEmailID= 35;
	public static final int SystemRole = 36;
	public static final int RoleID = 37;
	public static final int DOB = 38;
	public static final int Gender = 39;
	public static final int Gender_ID = 40;
	public static final int BloodGroup = 41;
	public static final int BloodGroup_ID = 42;
	public static final int AlternateNo = 43;
	public static final int Marital =44;
	public static final int Marital_ID =45;
	public static final int AnniversaryDate = 46;
	public static final int Permanent_Address_AddressText = 47;
	public static final int Permanent_Address_Pin_Code = 48;
	public static final int Permanent_Address_Country = 49;
	public static final int Permanent_Address_CountryID = 50;
	public static final int Permanent_Address_State = 51;
	public static final int Permanent_Address_State_ID =52;
	public static final int Permanent_Address_City = 53;
	public static final int Permanent_Address_City_ID = 54;
	public static final int Present_Address_AddressText = 55;
	public static final int Present_Address_Pin_Code = 56;
	public static final int Present_Address_Country= 57;
	public static final int Present_Address_Country_ID = 58;
	public static final int Present_Address_State= 59;
	public static final int Present_Address_State_ID  = 60;
	public static final int Present_Address_City = 61;
	public static final int Present_Address_City_ID= 62;
	public static final int ReferenceName = 63;
	public static final int ReferenceContactNo = 64;
	public static final int ReferenceEmail = 65;
	public static final int Reference_Address_AddressText = 66;
	public static final int Reference_Address_Pin_Code = 67;
	public static final int Reference_Address_Country = 68;
	public static final int Reference_Address_Country_ID = 69;
	public static final int Reference_Address_State = 70;
	public static final int Reference_Address_State_ID = 71;
	public static final int Reference_Address_City = 72;
	public static final int Reference_Address_City_ID = 73;
	public static final int BankName= 74;
	public static final int BankId = 75;
	public static final int AccountNumber = 76;
	public static final int BankBranch = 77;
	public static final int IfscCode = 78;
	
	//Sheet 2 for ID And Address Proof
	
	public static final int IdType=1;
	public static final int IdType_ID=2;
	public static final int IdNumber= 3;
	public static final int issueDate=4;
	public static final int expiryDate=5;
	

	//Sheet 3 For Family details
	
	public static final int Title= 1;
	public static final int Title_ID= 2;
	public static final int Name= 3;
	public static final int Relation=4;
	public static final int Relation_ID=5;
	public static final int Education=6;
	public static final int Education_ID=7;
	public static final int Occupation=8;
	public static final int Occupation_ID=9;
	public static final int Date_Of_Birth=10;
	public static final int Contact_No=11;
	
	//sheet 4 for Uan and Pf details
	
	public static final int UAN_Number= 1;
	public static final int PF_Number= 2;
	public static final int Effective_From= 3;
	public static final int Effective_To= 4;
	//sheet 5 for esi 
	public static final int Employee_Code_ESI = 0;
	public static final int ESIC_Number= 1;
	public static final int Effective_from_Esi= 2;
	public static final int Effective_to_Esi= 3;
	//sheet 6 for mi
	
	public static final int Mi_Number= 1;
	public static final int Effective_from_Mi= 2;
	public static final int Effective_To_Mi= 3;
	
	// sheet 7 for ai
	
	public static final int Ai_Number= 1;
	public static final int Effective_from_Ai= 2;
	public static final int Effective_To_Ai= 3;
	
	//Sheet 8 for Professional

	public static final int Org_Name= 1;
	public static final int Work_From= 2;
	public static final int Work_TO= 3;
	public static final int Designation_Pro= 4;
	public static final int ReportingTO_Pro= 5;
	public static final int Reporting_Contact= 6;
	public static final int Annual_Salary= 7;
	public static final int Reason_For_Change= 8;
	public static final int Reason_For_Change_ID=9;
	
	//sheet 9 for Educational 

	public static final int Education_Level= 1;
	public static final int Education_Level_ID= 2;
	public static final int Degree= 3;
	public static final int Degree_ID=4 ;
	public static final int School_College_Name= 5;
	public static final int Board_Of_Education= 6;
	public static final int Marks= 7;
	public static final int Passing_Year= 8;
	public static final int Regular_Corr= 9;
	
	//sheet 10 for Language
	
	public static final int Language= 1;
	public static final int Language_ID= 2;
	public static final int Read= 3;
	public static final int Write= 4;
	public static final int Speak= 5;
	
	//sheet 11 for skills
	public static final int Skill_Name= 1;
	public static final int Skill1= 2;
	public static final int Skill2= 3;
	public static final int Skill3= 4;
	public static final int Skill4= 5;
	public static final int Skill5= 6;
	public static final int Skill6= 7;
	public static final int Skill7= 8;
	public static final int Skill8= 9;
	public static final int Skill9= 10;
	public static final int Skill10= 11;
	
	
	//Sheet 12 for pay structure 
//	public static final int GrossPay= 1;
////	public static final int Effective_Date= 2;
////	public static final int End_Date= 3;
//	public static final int ProcessMonth= 2;
//	public static final int NetPay= 3;
//	public static final int CTC= 4;
//	public static final int EmployeeESI= 5;
//	public static final int EmployerESI= 6;
//	public static final int EmployeeEPF= 7;
//	public static final int EmployerEPF= 8;
//	public static final int ProfessionalTax= 9;
//	public static final int CTC1= 10;
//	public static final int PayHeadID= 11;
//	public static final int Amount= 12;
//	public static final int PF_Status= 13;
//	public static final int ESI_Status= 14;

	//Sheet 12 for pay structure
	public static final int GrossPay= 1;
	public static final int ProcessMonth= 2;
	public static final int NetPay= 3;
	public static final int CTC= 4;
	public static final int PFEmployee= 5;
	public static final int PFEmployer= 6;
	public static final int EPSEmployer=7;
	public static final int ESIEmployee= 8;
	public static final int ESIEmployer= 9;
	public static final int LWFEmployee= 10;
	public static final int LWFEmployer= 11;
	public static final int ProfessionalTax= 12;
	public static final int PayHeadID= 13;
	public static final int Amount= 14;
	public static final int PFStatus= 15;
	public static final int ESIStatus= 16;
	public static final int LWFStatus= 17;
	
	
	
	
	
	
	
	
	//sheet 13 Employee Leave Opening
	public static final int LeaveType= 1;
	public static final int LeaveTypeID= 2;
	public static final int ConsumedLeave= 3;

	
	
	public void buildEmployee(Employee employee,Map<String,Long> reportingIds, Cell cell, int index, FormulaEvaluator evaluator,
			Map<Long,Department> departmentMap,	Map<Long, Designation> designationMap,Map<Long, Grade> gradeMap,Map<Long, RoleMaster> roleMap ,Map<Long, ShiftDTO> shiftMap,Map<Long, TMSWeekOffMasterPatternDto> weekOffMap,Map<Long, State> stateMap,Map<Long, LeaveSchemeMasterDTO> leaveSchemeMap, Map<Long, AttendanceSchemeDTO> attendanceSchemeMap, Map<Long, BranchDTO> branchMap, StringBuilder stringBuilder)
			throws ParseException {

	
 		if (index == Employee_Code) {
			if (cell == null || cell.getCellType() == Cell.CELL_TYPE_BLANK) {
				stringBuilder.append("Employee Code, ");
			} else {
				String cellStringValue = dataConverter(cell);
				if (!cellStringValue.equals("")) {
				employee.setEmployeeCode(cell.getStringCellValue());
			}
			}

		} else if (index == First_Name) {

			if (cell == null || cell.getCellType() == Cell.CELL_TYPE_BLANK) {
				stringBuilder.append("First Name, ");
			} else {
				String cellStringValue = dataConverter(cell);
				if (!cellStringValue.equals("")) {
				employee.setFirstName(cell.getStringCellValue().trim());
				}
			}

		} else if (index == Middle_Name) {
			
				String cellStringValue = dataConverter(cell);
				if (!cellStringValue.equals("")) {
				employee.setMiddleName(cell.getStringCellValue().trim());
				}

		} else if (index == Last_Name) {

			if (cell == null || cell.getCellType() == Cell.CELL_TYPE_BLANK) {
				stringBuilder.append("Last Name, ");
			} else {
				String cellStringValue = dataConverter(cell);
				if (!cellStringValue.equals("")) {
				employee.setLastName(cell.getStringCellValue().trim());
				}
			}

		} 
		else if (index == AadharCardNo) {

			if (cell == null || cell.getCellType() == Cell.CELL_TYPE_BLANK) {
				stringBuilder.append("Aadhar Card No, ");
			} else {
				String cellStringValue = dataConverter(cell);
				if (!cellStringValue.equals("")) {
				employee.setAdharNumber(cellStringValue);
				}
			}

		}
		else if (index == ContactNo) {

			if (cell == null || cell.getCellType() == Cell.CELL_TYPE_BLANK) {
				stringBuilder.append("Contact No, ");
			} else {
				String cellStringValue = dataConverter(cell);
				if (!cellStringValue.equals("")) {
				employee.setContactNo(cellStringValue);
				}
			}

		}
		else if (index == EmailId) {

			if (cell == null || cell.getCellType() == Cell.CELL_TYPE_BLANK) {
				stringBuilder.append("emailId, ");
			} else {
				String cellStringValue = dataConverter(cell);
				if (!cellStringValue.equals("")) {
				//employee.setOfficialEmail(cellStringValue);
				employee.setPersonalEmail(cellStringValue);
				}
			}

		}
		else if (index == JobLocationID) {

			if (cell == null || cell.getCellType() == Cell.CELL_TYPE_BLANK) {
				stringBuilder.append("Job Location city, ");
			} else {
				CellValue cellValue = evaluator.evaluate(cell);
				if (cellValue != null) {
				Long cityId = Math.round(cellValue.getNumberValue());
				if(cityId != 0 ) {
				City city= new City();
				city.setCityId(cityId);
				employee.setCity(city);
				}
				else {
					stringBuilder.append("Job Location city, ");
				}
			}
				
			}
		} 
		else if (index == JobLocationStateID) {

			if (cell == null || cell.getCellType() == Cell.CELL_TYPE_BLANK) {
				stringBuilder.append("Job Location state, ");
			} else {
				CellValue cellValue = evaluator.evaluate(cell);
				if (cellValue != null) {
				Long stateId = Math.round(cellValue.getNumberValue());
				State stateIds= stateMap.get(stateId);
				if(stateIds != null) {
					if(stateId != 0 ) {
						State state = new State();
						state.setStateId(stateId);
						employee.setState(state);
						}
						else {
							stringBuilder.append("Job Location state, ");	
						}
				}
				else {
					stringBuilder.append("Job Location state is not present in database, ");	
				}
				
			}
			}
		} 
		else if (index == ShiftID) {
			
			if (cell == null || cell.getCellType() == Cell.CELL_TYPE_BLANK) {
				stringBuilder.append("shift, ");
			} else {
				 CellValue cellValue = evaluator.evaluate(cell);
				if (cellValue != null) {
					Long shiftId = Math.round(cellValue.getNumberValue());
					ShiftDTO sIds = shiftMap.get(shiftId);
					if (sIds != null) {
						if (shiftId != 0) {
							employee.setShiftId(shiftId);
						} else {
							stringBuilder.append("shift Id, ");
						}
					}
				
					else {
						stringBuilder.append("shift Id is incorrect in database, ");
					}
				}
			}
			
		}
		
		else if (index == WeeklyOffPatternID) {
			if (cell == null || cell.getCellType() == Cell.CELL_TYPE_BLANK) {
				stringBuilder.append("week off pattern, ");
			} 
			else
			{
				CellValue cellValue = evaluator.evaluate(cell);
				if (cellValue != null) {
				Long patternId = Math.round(cellValue.getNumberValue());
				TMSWeekOffMasterPatternDto sIds = weekOffMap.get(patternId);
					if (sIds != null) {
						if (patternId != 0) {
							employee.setPatternId(patternId);
						} else {
							stringBuilder.append("week off pattern, ");
						}
					} else {
						stringBuilder.append("week off pattern is not present in database, ");
					}

				}
			}

		}
 		
 		
		else if (index == AttendanceSchemeID) {
			if (cell == null || cell.getCellType() == Cell.CELL_TYPE_BLANK) {
				stringBuilder.append("Attendance scheme, ");
			} 
			else
			{
				CellValue cellValue = evaluator.evaluate(cell);
				if (cellValue != null) {
				Long attendanceSchemeId = Math.round(cellValue.getNumberValue());
				
				AttendanceSchemeDTO sIds=   attendanceSchemeMap.get(attendanceSchemeId);
					if (sIds != null) {
						if (attendanceSchemeId != 0) {
							employee.setAttendanceSchemeId(attendanceSchemeId);
						} else {
							stringBuilder.append("Attendance scheme, ");
						}
					} else {
						stringBuilder.append("Attendance scheme is not present in database, ");
					}

				}
			}

		}
 		
		else if (index == LeaveSchemeID) {
			if (cell == null || cell.getCellType() == Cell.CELL_TYPE_BLANK) {
				stringBuilder.append("Leave scheme, ");
			} 
			else
			{
				CellValue cellValue = evaluator.evaluate(cell);
				if (cellValue != null) {
				Long schemeId = Math.round(cellValue.getNumberValue());
				
				LeaveSchemeMasterDTO sIds=   leaveSchemeMap.get(schemeId);
					if (sIds != null) {
						if (schemeId != 0) {
							employee.setLeaveSchemeId(schemeId);
						} else {
							stringBuilder.append("Leave scheme, ");
						}
					} else {
						stringBuilder.append("Leave scheme is not present in database, ");
					}

				}
			}

		}
		else if (index == DepartmentID) {

			if (cell == null || cell.getCellType() == Cell.CELL_TYPE_BLANK) {
				stringBuilder.append("Department, ");
			} else {
				CellValue cellValue = evaluator.evaluate(cell);
				if (cellValue != null) {
				Long departmentId = Math.round(cellValue.getNumberValue());
				Department depIds= departmentMap.get(departmentId);
				if( depIds != null) {
					if(departmentId != 0) {
						Department department = new Department();
						department.setDepartmentId(departmentId);
						employee.setDepartment(department);
						}
						else {
							stringBuilder.append("Department, ");
						}
				}
				else {
					stringBuilder.append("Department is not present is database, ");
				}
				
				}
			}

		} 
		 else if (index == DesignationID) {

				if (cell == null || cell.getCellType() == Cell.CELL_TYPE_BLANK) {
					stringBuilder.append("Designation, ");
				} else {
					CellValue cellValue = evaluator.evaluate(cell);
					if (cellValue != null) {
					Long designationId = Math.round(cellValue.getNumberValue());
					Designation depIds= designationMap.get(designationId);
					if(depIds != null) {
					if(designationId != 0 ) {
					Designation designation = new Designation();
					designation.setDesignationId(designationId);
					employee.setDesignation(designation);
					}
					else {
						stringBuilder.append("Designation, ");
					}
					}
					else {
						stringBuilder.append("Designation is not present is database, ");
					}
					}

				}
		 }
 		
 		
		else if (index == BranchID) {

			CellValue cellValue = evaluator.evaluate(cell);
			if (cellValue != null) {
				Long branchId = Math.round(cellValue.getNumberValue());
				BranchDTO branchID = branchMap.get(branchId);

				if (branchID != null) {
					if (branchId != 0) {
						Branch branch = new Branch();
						branch.setBranchId(branchId);
						employee.setBranch(branch);
					} else {
						stringBuilder.append("Branch ID ");
					}
				} else {
					stringBuilder.append("Branch ID is not present in database, ");
				}
			}

		}
 		
		 else if (index == ReportingToID) {
			 if (cell == null || cell.getCellType() == Cell.CELL_TYPE_BLANK) {
					stringBuilder.append("reporting to, ");
				} 
			 else {
				 CellValue cellValue = evaluator.evaluate(cell);
				 if (cellValue != null || !cellValue.equals("")  ) {
					 Long reportingTo= reportingIds.get(cellValue.getStringValue());
					 if(reportingTo == null) {
						 stringBuilder.append("reporting to is not present in database, "); 
					 }else
					 employee.setReportingToEmployee(reportingTo);
				 }
			 }
				
			} 
		 else if (index == GradeID) {
				if (cell == null || cell.getCellType() == Cell.CELL_TYPE_BLANK) {
					stringBuilder.append("grade, ");
				} else {
					CellValue cellValue = evaluator.evaluate(cell);
					if (cellValue != null) {
					Long gradeId = Math.round(cellValue.getNumberValue());
					Grade gIds= gradeMap.get(gradeId);
					if(gIds != null) {
						if (gradeId != 0) {
							employee.setGradesId(gradeId);
						} else {
							stringBuilder.append("grade, ");
						}
					}
					else {
						stringBuilder.append("grade is not present in database, ");	
					}
					}
					
					
				}
			}
		 else if (index == ProbationDays) {
			 if (cell == null || cell.getCellType() == Cell.CELL_TYPE_BLANK) {
					stringBuilder.append("probabtion days, ");
				}
			 else {
				 String cellStringValue = dataConverter(cell);

					if (!cellStringValue.equals("")) {
						Long days = Long.valueOf(cellStringValue);
						employee.setProbationDays(days);
					}
			 }
				
			}
		 else if (index == NoticePeriod) {
			 if (cell == null || cell.getCellType() == Cell.CELL_TYPE_BLANK) {
					stringBuilder.append("notice period, ");
				}
			 else {
				String cellStringValue = dataConverter(cell);
				if (!cellStringValue.equals("")) {
					Long days = Long.valueOf(cellStringValue);
					employee.setNoticePeriodDays(days);
				}
			 }
			}
		 else if (index == EmployeeType_ID) {
			 if (cell == null || cell.getCellType() == Cell.CELL_TYPE_BLANK) {
					stringBuilder.append("employee type, ");
				}
			 else {
				CellValue cellValue = evaluator.evaluate(cell);
				if (cellValue != null)
					employee.setEmpType(cellValue.getStringValue());

			} 
		 }
		 else if (index == RoleID) {

				if (cell == null || cell.getCellType() == Cell.CELL_TYPE_BLANK) {
					stringBuilder.append("system role, ");
				} else {
					CellValue cellValue = evaluator.evaluate(cell);
					if (cellValue != null) {
						long  roleID = Math.round(cellValue.getNumberValue());
						RoleMaster roleMaster= roleMap.get(roleID);
						if(roleMaster != null) {
						if(roleID != 0) {
						employee.setRoleId(roleID);
						}
						else {
							stringBuilder.append("system role, ");	
						}
						}
						else {
					stringBuilder.append("system role is not present in database, ");	
					}
				}
			}
		 }
				
		 else if (index == DateOfJoining) {

				if (cell == null || cell.getCellType() == Cell.CELL_TYPE_BLANK) {
					stringBuilder.append("Date of joining, ");
				} else {
					//CellValue cellValue = evaluator.evaluate(cell);
					Date date= cell.getDateCellValue();
					
					if (!date.toString().equals("")) {
						 SimpleDateFormat DtFormat = new SimpleDateFormat("dd/MM/yyyy");
						 String dateString= DtFormat.format(date);
						DateUtils dateUtils = new DateUtils();
						 Date sqlDate=	dateUtils.getDateFromString(dateString);
						employee.setDateOfJoining(sqlDate);
					}
				}
		 }
		 else if (index == ContractStartDate) {
		if(cell != null) {
			CellValue cellValue = evaluator.evaluate(cell);
			if(cellValue != null) {
			 Date date= cell.getDateCellValue();
					if (!date.toString().equals("") || date != null) {  //null pointer exception
						 SimpleDateFormat DtFormat = new SimpleDateFormat("dd/MM/yyyy");
						 String dateString= DtFormat.format(date);
						DateUtils dateUtils = new DateUtils();
						 Date contractStartDate=	dateUtils.getDateFromString(dateString);
						 employee.setContractStartDate(contractStartDate);
					}
			} 
		 }
		 }

		 else if (index == ContractEndDate) {
			 if(cell != null) {
				 CellValue cellValue = evaluator.evaluate(cell);
					if(cellValue != null) {
						Date date= cell.getDateCellValue();
					if (!date.toString().equals("") || date != null) {
						 SimpleDateFormat DtFormat = new SimpleDateFormat("dd/MM/yyyy");
						 String dateString= DtFormat.format(date);
						DateUtils dateUtils = new DateUtils();
						 Date contractOverDate=	dateUtils.getDateFromString(dateString);
						 employee.setContractOverDate(contractOverDate);
					}	
			} 
			 }
		 }
		 else if (index == TimeContract) {

				if (cell == null || cell.getCellType() == Cell.CELL_TYPE_BLANK) {
					stringBuilder.append("timing contract, ");
				} else {
					CellValue cellValue = evaluator.evaluate(cell);
					if (cellValue != null)
						employee.setTimeContract(cellValue.getStringValue());
			}
		 }
 		
 		
		 else if (index == OfficialEmailID) {

				if (cell == null || cell.getCellType() == Cell.CELL_TYPE_BLANK) {
					stringBuilder.append("emailId, ");
				} else {
					String cellStringValue = dataConverter(cell);
					if (!cellStringValue.equals("")) {
					employee.setOfficialEmail(cellStringValue);
					//employee.setPersonalEmail(cellStringValue);
					}
				}

			}
 		
		 else if (index == DOB) {

				if (cell == null || cell.getCellType() == Cell.CELL_TYPE_BLANK) {
					stringBuilder.append("Date of Birth, ");
				} else {
					 Date date= cell.getDateCellValue();
					 if (!date.toString().equals("")) {
						 SimpleDateFormat DtFormat = new SimpleDateFormat("dd/MM/yyyy");
						 String dateString= DtFormat.format(date);
						DateUtils dateUtils = new DateUtils();
						 Date dob=	dateUtils.getDateFromString(dateString);
						 employee.setDateOfBirth(dob);
					}
				}

			} 
		 else if (index == Gender_ID) {
			 if (cell == null || cell.getCellType() == Cell.CELL_TYPE_BLANK) {
					stringBuilder.append("Gender, ");
				} else {

				CellValue cellValue = evaluator.evaluate(cell);
				if (cellValue != null)
					employee.setGender(cellValue.getStringValue());
				}
			}
		 else if (index == BloodGroup_ID) {
			
				CellValue cellValue = evaluator.evaluate(cell);
				if (cellValue != null)
					employee.setBloodGroup(cellValue.getStringValue());
					
			} 
		 else if (index == AlternateNo) {
			
					String cellStringValue = dataConverter(cell);
						if (!cellStringValue.equals("")) {
					employee.setAlternateNumber(cellStringValue);
						}
			}
		 else if (index == Marital_ID) {

				if (cell == null || cell.getCellType() == Cell.CELL_TYPE_BLANK) {
					stringBuilder.append("Marital status, ");
				} else {
					CellValue cellValue = evaluator.evaluate(cell);
					if (cellValue != null)
					employee.setMaritalStatus(cellValue.getStringValue());

				}
			}
		 else if (index == AnniversaryDate) {
			 if(cell != null) {
				 CellValue cellValue = evaluator.evaluate(cell);
					if(cellValue != null) {
						Date date= cell.getDateCellValue();
					if (!date.toString().equals("") || date != null) {
						 SimpleDateFormat DtFormat = new SimpleDateFormat("dd/MM/yyyy");
						 String dateString= DtFormat.format(date);
						DateUtils dateUtils = new DateUtils();
						 Date doa=	dateUtils.getDateFromString(dateString);
						 employee.setAnniversaryDate(doa);
					}	
			} 
			 }

			} 
		 else if (index == ReferenceName) {
					String cellStringValue = dataConverter(cell);
						if (!cellStringValue.equals("")) {
					employee.setReferenceName(cellStringValue);
					
				}
		 }
		
		
	}

	public void buildPresentAddress(Address address, Cell cell, int index, FormulaEvaluator evaluator,
			StringBuilder stringBuilder, Map<Long, State> stateMap,int rowIndex) {

		if (index == Present_Address_AddressText) {
			String cellStringValue = dataConverter(cell);
			if (!cellStringValue.equals("")) {
				address.setAddressText(cellStringValue);
			}

		}  
		 else if (index == Present_Address_Country_ID) {

				if (cell == null || cell.getCellType() == Cell.CELL_TYPE_BLANK) {
					stringBuilder.append("country, ");
				} else {

					CellValue cellValue = evaluator.evaluate(cell);
					if (cellValue != null) {
					Long countryId = Math.round(cellValue.getNumberValue());
					if(countryId != 0) {
					Country coun = new Country();
					coun.setCountryId(countryId);
					address.setCountry(coun);
					}
					else {
						stringBuilder.append("country, ");	
					}
					}
				}

			}
		else if (index == Present_Address_State_ID) {

			if (cell == null || cell.getCellType() == Cell.CELL_TYPE_BLANK) {
				stringBuilder.append("State, ");
			} else {

				CellValue cellValue = evaluator.evaluate(cell);
				if (cellValue != null) {
				Long stateId = Math.round(cellValue.getNumberValue());
				State stateIds= stateMap.get(stateId);
				if(stateIds != null) {
					if(stateId != 0) {
						State state = new State();
						state.setStateId(stateId);
						address.setState(state);
						}
						else {
							stringBuilder.append("State, ");
						}
				}
				else {
					stringBuilder.append("State is not present in database, ");
				}
				
				}
			}

		} 
			  else if (index == Present_Address_City_ID) {
			  
			  if (cell == null || cell.getCellType() == Cell.CELL_TYPE_BLANK) {
			  stringBuilder.append("City, "); 
			  } 
			  else { 
			   CellValue cellValue = evaluator.evaluate(cell); 
			   if (cellValue != null) {
			  Long cityId = Math.round(cellValue.getNumberValue()); 
			  if(cityId != 0) {
			  City city= new City();
			  city.setCityId(cityId);
			  address.setCity(city); 
			  }
			  else {
				  stringBuilder.append("City, ");  
			  }
			   }
			  
			  }
			  }
			 
		else if (index == Present_Address_Pin_Code) {

			String cellStringValue = dataConverter(cell);
			if (!cellStringValue.equals("")) {
				address.setPincode(cellStringValue);
			}
		}
		else if (index == ContactNo) {

			if (cell == null || cell.getCellType() == Cell.CELL_TYPE_BLANK) {
				stringBuilder.append("Contact No, ");
			} else {
				String cellStringValue = dataConverter(cell);
				if (!cellStringValue.equals("")) {
					address.setMobile(cellStringValue);
				}
			}

		}
		else if (index == EmailId) {

			if (cell == null || cell.getCellType() == Cell.CELL_TYPE_BLANK) {
				stringBuilder.append("emailId, ");
			} else {
				String cellStringValue = dataConverter(cell);
				if (!cellStringValue.equals("")) {
				address.setEmailId(cellStringValue);
				}
			}
		}
	}

	public void builPermanentdAddress(Address address, Cell cell, int index, FormulaEvaluator evaluator,Map<Long, State> stateMap,
			StringBuilder stringBuilder) {

		if (index == Permanent_Address_AddressText) {
			String cellStringValue = dataConverter(cell);
			if (!cellStringValue.equals("")) {
				address.setAddressText(cell.getStringCellValue());
					}

		}
		 else if (index == Permanent_Address_CountryID) {

				if (cell == null || cell.getCellType() == Cell.CELL_TYPE_BLANK) {
					stringBuilder.append("country, ");
				} else {
					CellValue cellValue = evaluator.evaluate(cell);
					if (cellValue != null) {
					Long countryId = Math.round(cellValue.getNumberValue());
					  if(countryId != 0) {
					Country coun = new Country();
					coun.setCountryId(countryId);
					address.setCountry(coun);
					  }
					  else {
						  stringBuilder.append("country, ");
					  }
				}
				}

			}
		 else if (index == Permanent_Address_State_ID) {

			if (cell == null || cell.getCellType() == Cell.CELL_TYPE_BLANK) {
				stringBuilder.append("State, ");
			} else {
				CellValue cellValue = evaluator.evaluate(cell);
				if (cellValue != null) {
				Long stateId = Math.round(cellValue.getNumberValue());
				State stateIds= stateMap.get(stateId);
				if(stateIds != null) {
					 if(stateId != 0) {
							State state = new State();
							state.setStateId(stateId);
							address.setState(state);
							  }
							  else {
								  stringBuilder.append("State, ");
							  }
				}
				else {
					  stringBuilder.append("State is not present in database, ");
				}
				 
				}
			}

		} 
			  else if (index == Permanent_Address_City_ID) {
			 if (cell == null || cell.getCellType() == Cell.CELL_TYPE_BLANK) {
			 stringBuilder.append("City, "); 
			 } else { 
				 CellValue cellValue = evaluator.evaluate(cell); 
				 if (cellValue != null) {
				 Long cityId = Math.round(cellValue.getNumberValue()); 
				  if(cityId != 0) {
				 City city= new City();
				  city.setCityId(cityId);
				  address.setCity(city); 
				  }
				  else {
					  stringBuilder.append("City, ");  
				  }
				  }
			 }
			 }
			 
		 else if (index == Permanent_Address_Pin_Code) {
			String cellStringValue = dataConverter(cell);
			if (!cellStringValue.equals("")) {
				address.setPincode(cellStringValue);
			}

		} 
		
		 else if (index == ContactNo) {

				if (cell == null || cell.getCellType() == Cell.CELL_TYPE_BLANK) {
					stringBuilder.append("Contact No, ");
				} else {
					String cellStringValue = dataConverter(cell);
					if (!cellStringValue.equals("")) {
						address.setMobile(cellStringValue);
					}
				}

			}
			else if (index == EmailId) {

				if (cell == null || cell.getCellType() == Cell.CELL_TYPE_BLANK) {
					stringBuilder.append("emailId, ");
				} else {
					String cellStringValue = dataConverter(cell);
					if (!cellStringValue.equals("")) {
					address.setEmailId(cellStringValue);
					}
				}
			}
	}
	public void builReferencedAddress(Address address, Cell cell, int index, FormulaEvaluator evaluator,
			StringBuilder stringBuilder) {

		if (index == Reference_Address_AddressText) {
			String cellStringValue = dataConverter(cell);
			if (!cellStringValue.equals("")) {
				address.setAddressText(cell.getStringCellValue());
			}

		}  else if (index == Reference_Address_State_ID) {

			
				 CellValue cellValue = evaluator.evaluate(cell);
					if (cellValue != null) {
					Long stateId = Math.round(cellValue.getNumberValue());
					if(stateId !=0 ) {
				State state = new State();
				state.setStateId(stateId);
				address.setState(state);
				}
					}
		} 
		 else if (index == Reference_Address_City_ID) {
			
			CellValue cellValue = evaluator.evaluate(cell);
			if (cellValue != null) {
			Long cityId = Math.round(cellValue.getNumberValue());
			if(cityId !=0 ) {
				 City city= new City();
				  city.setCityId(cityId);
				  address.setCity(city);
				}
			}
		} 
		 else if (index == Reference_Address_Country_ID) {
			
			       CellValue cellValue = evaluator.evaluate(cell);
					if (cellValue != null) {
					Long countryId = Math.round(cellValue.getNumberValue());
					if(countryId !=0 ) {
						Country coun = new Country();
						coun.setCountryId(countryId);
						address.setCountry(coun);
					}
			       }
		 }
		 else if (index == Reference_Address_Pin_Code) {

				String cellStringValue = dataConverter(cell);
				if (!cellStringValue.equals("")) {
					address.setPincode(cellStringValue);
				}
			}
		 else if (index == ReferenceContactNo) {

				//CellValue cellValue = evaluator.evaluate(cell);
				String cellStringValue = dataConverter(cell);
				if (!cellStringValue.equals("")) {
					address.setMobile(cell.getStringCellValue());
			
			}
	 }
	 else if (index == ReferenceEmail) {

				String cellStringValue = dataConverter(cell);
				if (!cellStringValue.equals("")) {
					address.setEmailId(cell.getStringCellValue().trim());
			}
	 }
	}


	public void buildBankDetails(EmployeeBank bankDetails, Cell cell, int index, FormulaEvaluator evaluator,
			StringBuilder stringBuilder) throws ParseException {

		
		  if (index == AccountNumber) {

			if (cell == null || cell.getCellType() == Cell.CELL_TYPE_BLANK) {
				stringBuilder.append("Account Number, ");
			} else {
				String cellStringValue = dataConverter(cell);
				if (!cellStringValue.equals("")) {
						
					bankDetails.setAccountNumber(cellStringValue);
				}
			}
		 }
			
		else if (index == BankId) {
			if (cell == null || cell.getCellType() == Cell.CELL_TYPE_BLANK) {
				stringBuilder.append("Bank name, ");
			} else {
			String cellStringValue = dataConverter(cell);
			if (!cellStringValue.equals("")) {
				  CellValue cellValue = evaluator.evaluate(cell);
				bankDetails.setBankId(cellValue.getStringValue());
				bankDetails.setAccountType("SA");
			}
			}

		}
		else if (index == BankBranch) {
		
			String cellStringValue = dataConverter(cell);
			if (!cellStringValue.equals("")) {
				bankDetails.setBankBranch(cellStringValue);
			}
			

		}
		else if (index == IfscCode) {

			if (cell == null || cell.getCellType() == Cell.CELL_TYPE_BLANK) {
				stringBuilder.append("IfscCode, ");
			} else {
				String cellStringValue = dataConverter(cell);
				if (!cellStringValue.equals("")) {
					bankDetails.setIfscCode(cellStringValue);
				}
			}
		}
		  /*else if (index == EffectiveDate) {
			

			DataFormatter dataFormatter = new DataFormatter();
			String cellStringValue = dataFormatter.formatCellValue(cell);

			Date effectiveDate = new SimpleDateFormat("dd/MM/yyyy").parse(cellStringValue);

			bankDetailsDto.setEffectiveDate(effectiveDate);

		}*/

	}

	public void buildIdAndAddressProof(EmployeeIdProof employeeIdProof, Cell cell, int index,
			FormulaEvaluator evaluator,StringBuilder stringBuilder) throws ParseException {
		
		if (index == Employee_Code) {
			if (cell == null || cell.getCellType() == Cell.CELL_TYPE_BLANK) {
				stringBuilder.append("Employee Code, ");
			} else {
				String cellStringValue = dataConverter(cell);
				if (!cellStringValue.equals("")) {
					Employee employee = new Employee();
					employee.setEmployeeCode(cell.getStringCellValue());
					employeeIdProof.setEmployee(employee);
			}
			}
		}
		else if (index == IdType_ID) {

			if (cell == null || cell.getCellType() == Cell.CELL_TYPE_BLANK) {
				stringBuilder.append(" IdType, ");
			} else {
				CellValue cellValue = evaluator.evaluate(cell);
				if (cellValue != null) {
					String cellStringValue = cell.getStringCellValue();
					 //String id = EnumUtil.getEnumKey(idList, cellStringValue);
				if (!cellStringValue.equals("")) {
				employeeIdProof.setIdTypeId(cellStringValue);
				}
				else {
					stringBuilder.append(" IdType is not present in database, ");	
				}
				}
			}

		} 
		else if (index == IdNumber) {

			if (cell == null || cell.getCellType() == Cell.CELL_TYPE_BLANK  ) {
				stringBuilder.append(" IdNumber, ");
			} else {
				String cellStringValue =  dataConverter(cell);
				if (!cellStringValue.equals("")) {
					employeeIdProof.setIdNumber(cellStringValue);
				}
			}

		} 
		else if (index == issueDate) {
			
			if(cell != null ) {
				CellValue cellValue = evaluator.evaluate(cell);
				if(cellValue != null) {
				 Date date= cell.getDateCellValue();
						if (!date.toString().equals("")) {
							 SimpleDateFormat DtFormat = new SimpleDateFormat("dd/MM/yyyy");
							 String dateString= DtFormat.format(date);
							DateUtils dateUtils = new DateUtils();
							 Date issue=	dateUtils.getDateFromString(dateString);
							 employeeIdProof.setDateFrom(issue);
						}
					}
				}
			
			}
		
		else if (index == expiryDate) {
			if(cell != null ) {
				CellValue cellValue = evaluator.evaluate(cell);
				if(cellValue != null) {
				 Date date= cell.getDateCellValue();
						if ( !date.toString().equals("")) {
							 SimpleDateFormat DtFormat = new SimpleDateFormat("dd/MM/yyyy");
							 String dateString= DtFormat.format(date);
							DateUtils dateUtils = new DateUtils();
							 Date expDate=	dateUtils.getDateFromString(dateString);
							 employeeIdProof.setDateTo(expDate);
						}
				}
			}
		}
	}

	// build employee Professional
	public void buildEmployeeProfessional(ProfessionalInformation empProfessional, Cell cell, int index,
			FormulaEvaluator evaluator, StringBuilder stringBuilder) throws ParseException {

		if (index == Employee_Code) {
			if (cell == null || cell.getCellType() == Cell.CELL_TYPE_BLANK) {
				stringBuilder.append("Employee Code, ");
			} else {
				String cellStringValue = dataConverter(cell);
				if (!cellStringValue.equals("")) {
					empProfessional.setEmpCode(cellStringValue);
			}
			}
		}
		else if (index == Org_Name) {

			if (cell == null || cell.getCellType() == Cell.CELL_TYPE_BLANK) {
				stringBuilder.append(" Organization Name, ");
			} else {
				
				String cellStringValue = dataConverter(cell);
				if (!cellStringValue.equals("")) {
					empProfessional.setOrganizationName(cell.getStringCellValue());
				}
			}

		} 
		else if (index == Work_From) {

			if (cell == null || cell.getCellType() == Cell.CELL_TYPE_BLANK) {
				stringBuilder.append(" Work From, ");
			} else {
				Date date= cell.getDateCellValue();
				if ( !date.toString().equals("")) {
					 SimpleDateFormat DtFormat = new SimpleDateFormat("dd/MM/yyyy");
					 String dateString= DtFormat.format(date);
					DateUtils dateUtils = new DateUtils();
					 Date workFrom=	dateUtils.getDateFromString(dateString);
					 empProfessional.setDateFrom(workFrom);
				}
			}

		} 
		else if (index == Work_TO) {

			if (cell == null || cell.getCellType() == Cell.CELL_TYPE_BLANK) {
				stringBuilder.append(" Work to, ");
			} else {
				Date date= cell.getDateCellValue();
				if ( !date.toString().equals("")) {
					 SimpleDateFormat DtFormat = new SimpleDateFormat("dd/MM/yyyy");
					 String dateString= DtFormat.format(date);
					DateUtils dateUtils = new DateUtils();
					 Date dateTo=	dateUtils.getDateFromString(dateString);
					 empProfessional.setDateTo(dateTo);
				}
			}
		} 
		else if (index == Designation_Pro) {

			if (cell == null || cell.getCellType() == Cell.CELL_TYPE_BLANK) {
				stringBuilder.append(" Designation, ");
			} else {
				String cellStringValue = dataConverter(cell);
				if (!cellStringValue.equals("")) {
					empProfessional.setDesignation(cell.getStringCellValue());
				}
			}
		} 
		else if (index == Reason_For_Change_ID) {

			if (cell == null || cell.getCellType() == Cell.CELL_TYPE_BLANK) {
				stringBuilder.append(" Reason For Change, ");
			} else {
				 CellValue cellValue = evaluator.evaluate(cell);
					if (cellValue != null) {
					empProfessional.setReasonForChange(cellValue.getStringValue());
				}
			}
		}
		else if (index == ReportingTO_Pro) {

			if (cell == null || cell.getCellType() == Cell.CELL_TYPE_BLANK) {
				stringBuilder.append(" Reporting To, ");
			} else {
				String cellStringValue = dataConverter(cell);
				if (!cellStringValue.equals("")) {
					empProfessional.setReportingTo(cell.getStringCellValue());
				}
			}
		}
		else if (index == Reporting_Contact) {

			
				String cellStringValue = dataConverter(cell);
				if (!cellStringValue.equals("")) {
					empProfessional.setReportingContact(cellStringValue);
				
			}
		}
		else if (index == Annual_Salary) {

			
				String cellStringValue = dataConverter(cell);
				if (!cellStringValue.equals("")) {
					BigDecimal annual= new BigDecimal(cellStringValue);
					empProfessional.setAnnualSalary(annual);
				
			}
		}
		
	}
	// build employee Educational
	public void buildEmployeeEducational(EmployeeEducation empEducational, Cell cell, int index,
			FormulaEvaluator evaluator, StringBuilder stringBuilder) throws ParseException {

		if (index == Employee_Code) {
			if (cell == null || cell.getCellType() == Cell.CELL_TYPE_BLANK) {
				stringBuilder.append("Employee Code, ");
			} else {
				String cellStringValue = dataConverter(cell);
				if (!cellStringValue.equals("")) {
					empEducational.setEmpCode(cellStringValue);
			}
			}
		}
		else if (index == Education_Level_ID) {

			if (cell == null || cell.getCellType() == Cell.CELL_TYPE_BLANK) {
				stringBuilder.append(" Educational Level, ");
			} else {
				
				CellValue cellValue = evaluator.evaluate(cell);
				if (cellValue != null) {
					empEducational.setQualificationId(cellValue.getStringValue());
				}
			}

		} 
		else if (index == Degree_ID) {

			if (cell == null || cell.getCellType() == Cell.CELL_TYPE_BLANK) {
				stringBuilder.append(" Degree, ");
			} else {
				
				CellValue cellValue = evaluator.evaluate(cell);
				if (cellValue != null) {
					empEducational.setDegreeName(cellValue.getStringValue());
				}
			}

		} 
		else if (index == School_College_Name) {

			
				String cellStringValue = dataConverter(cell);
				if (!cellStringValue.equals("")) {
					empEducational.setNameOfInstitution(cellStringValue);
					
			}
		} 
		else if (index == Board_Of_Education) {

				String cellStringValue = dataConverter(cell);
				if (!cellStringValue.equals("")) {
					empEducational.setNameOfBoard(cellStringValue);
				
			}
		} 
		else if (index == Marks) {

			
				String cellStringValue = dataConverter(cell);
				if (!cellStringValue.equals("")) {
					BigDecimal marks= new BigDecimal(cellStringValue);
					empEducational.setMarksPer(marks);
				
			}
		} 
		else if (index == Passing_Year) {

			
				String cellStringValue = dataConverter(cell);
				if (!cellStringValue.equals("")) {
					Long passingYear= new Long(cellStringValue);
					empEducational.setPassingYear(passingYear);
				
			}
		}
		else if (index == Regular_Corr) {

			
			CellValue cellValue = evaluator.evaluate(cell);
				if (cellValue != null) {
					empEducational.setRegularCorrespondance(cellValue.getStringValue());
				}
			
		}
	}
	//build Employee Family
	public void buildEmployeeFamily(EmployeeFamily empFamily, Cell cell, int index,
			FormulaEvaluator evaluator, StringBuilder stringBuilder) throws ParseException {

		if (index == Employee_Code) {
			if (cell == null || cell.getCellType() == Cell.CELL_TYPE_BLANK) {
				stringBuilder.append("Employee Code, ");
			} else {
				String cellStringValue = dataConverter(cell);
				if (!cellStringValue.equals("")) {
					Employee employee = new Employee();
					employee.setEmployeeCode(cell.getStringCellValue());
					empFamily.setEmployee(employee);
			}
			}
		}
		else if (index == Title_ID) {
				CellValue cellValue = evaluator.evaluate(cell);
		
				if (cellValue != null) {
					empFamily.setCaptions(cellValue.getStringValue());
			}

		} 
		else if (index == Name) {

			if (cell == null || cell.getCellType() == Cell.CELL_TYPE_BLANK) {
				stringBuilder.append(" Name, ");
			} else {
				
				String cellStringValue = dataConverter(cell);
				if (!cellStringValue.equals("")) {
					empFamily.setName(cell.getStringCellValue());
				}
			}

		} 
		else if (index == Relation_ID) {

			if (cell == null || cell.getCellType() == Cell.CELL_TYPE_BLANK) {
				stringBuilder.append(" relation, ");
			} else {
				CellValue cellValue = evaluator.evaluate(cell);
				if (cellValue != null) {
					empFamily.setRelation(cellValue.getStringValue());
				}
			}
		} 
		else if (index == Education_ID) {

				CellValue cellValue = evaluator.evaluate(cell);
				if (cellValue != null) {
					empFamily.setQualificationId(cellValue.getStringValue());
				
			}
		} 
		else if (index == Occupation_ID) {

		
				CellValue cellValue = evaluator.evaluate(cell);
				if (cellValue != null) {
					empFamily.setOccupations(cellValue.getStringValue());
				}
		}
		else if (index == Date_Of_Birth) {
			if(cell != null) {
				CellValue cellValue = evaluator.evaluate(cell);
				if(cellValue != null) {
				 Date date= cell.getDateCellValue();
						if (!date.toString().equals("") || date != null) {  //null pointer exception
							 SimpleDateFormat DtFormat = new SimpleDateFormat("dd/MM/yyyy");
							 String dateString= DtFormat.format(date);
							DateUtils dateUtils = new DateUtils();
							 Date dateOfBirth=	dateUtils.getDateFromString(dateString);
							 empFamily.setDateOfBirth(dateOfBirth);
						}
				} 
			}
		}
		else if (index == Contact_No) {
			
				String cellStringValue = dataConverter(cell);
				if (!cellStringValue.equals("")) {
					empFamily.setContactMobile(cellStringValue.trim());
				}
		}
		
	}
	//build Employee Language
		public void buildEmployeeLanguage(EmployeeLanguage empLang, Cell cell, int index,
				FormulaEvaluator evaluator, StringBuilder stringBuilder) throws ParseException {

			if (index == Employee_Code) {
				if (cell == null || cell.getCellType() == Cell.CELL_TYPE_BLANK) {
					stringBuilder.append("Employee Code, ");
				} else {
					String cellStringValue = dataConverter(cell);
					if (!cellStringValue.equals("")) {
						Employee employee = new Employee();
						employee.setEmployeeCode(cell.getStringCellValue());
						empLang.setEmployee(employee);
				}
				}
			}
			else if (index == Language_ID) {

				if (cell == null || cell.getCellType() == Cell.CELL_TYPE_BLANK) {
					stringBuilder.append(" Language, ");
				} else {
					CellValue cellValue = evaluator.evaluate(cell);
					if (cellValue != null) {
						Long langId=  Math.round(cellValue.getNumberValue());
						Language l= new Language();
						l.setLanguageId(langId);
						empLang.setLanguage(l);
					}
				}

			} 
			else if (index == Read) {
					CellValue cellValue = evaluator.evaluate(cell);
					if (cellValue != null) {
						empLang.setLangRead(cellValue.getStringValue());

					} 
			}
			else if (index == Write) {
					CellValue cellValue = evaluator.evaluate(cell);
					if (cellValue != null) {
						empLang.setLangWrite(cellValue.getStringValue());
				
			} 
			}
			else if (index == Speak) {
					CellValue cellValue = evaluator.evaluate(cell);
					if (cellValue != null) {
						empLang.setLangSpeak(cellValue.getStringValue());
					}
			}
		}
		
		//build Employee Skills
				public void buildEmployeeSkills(List<EmployeeSkill> empSkill, Cell cell, int index,
						FormulaEvaluator evaluator, StringBuilder stringBuilder) throws ParseException {
					if (index == Employee_Code) {
						if (cell == null || cell.getCellType() == Cell.CELL_TYPE_BLANK) {
							stringBuilder.append("Employee Code, ");
						} else {
							String cellStringValue = dataConverter(cell);
							if (!cellStringValue.equals("")) {
								Employee employee = new Employee();
								EmployeeSkill skill = new EmployeeSkill();
								employee.setEmployeeCode(cell.getStringCellValue());
								skill.setEmployee(employee);
								empSkill.add(skill);
								
						}
						}
					}
					else if (index == Skill_Name) {

						if (cell == null || cell.getCellType() == Cell.CELL_TYPE_BLANK) {
							stringBuilder.append(" Skill name, ");
						} else {
							String cellStringValue = dataConverter(cell);
							if (!cellStringValue.equals("")) {
								EmployeeSkill skill = new EmployeeSkill();
								Long skillId= new Long(cellStringValue);
								Skill s= new Skill();
								s.setSkillId(skillId);
								skill.setSkill(s);
								empSkill.add(skill);
								
							}
						}

					} 
					else if (index == Skill1) {
							String cellStringValue = dataConverter(cell);
							if (!cellStringValue.equals("")) {
								EmployeeSkill skill = new EmployeeSkill();
								Long skillId= new Long(cellStringValue);
								Skill s= new Skill();
								s.setSkillId(skillId);
								skill.setSkill(s);
								empSkill.add(skill);
							
							} 
					}
					else if (index == Skill2) {
							String cellStringValue = dataConverter(cell);
							if (!cellStringValue.equals("")) {
								EmployeeSkill skill = new EmployeeSkill();
								Long skillId= new Long(cellStringValue);
								Skill s= new Skill();
								s.setSkillId(skillId);
								skill.setSkill(s);
								empSkill.add(skill);
					} 
					}
					else if (index == Skill3) {
							String cellStringValue = dataConverter(cell);
							if (!cellStringValue.equals("")) {
								EmployeeSkill skill = new EmployeeSkill();
								Long skillId= new Long(cellStringValue);
								Skill s= new Skill();
								s.setSkillId(skillId);
								skill.setSkill(s);
								empSkill.add(skill);
							}
					}
					else if (index == Skill4) {
						String cellStringValue = dataConverter(cell);
						if (!cellStringValue.equals("")) {
							EmployeeSkill skill = new EmployeeSkill();
							Long skillId= new Long(cellStringValue);
							Skill s= new Skill();
							s.setSkillId(skillId);
							skill.setSkill(s);
							empSkill.add(skill);
						}
				}
					else if (index == Skill5) {
						String cellStringValue = dataConverter(cell);
						if (!cellStringValue.equals("")) {
							EmployeeSkill skill = new EmployeeSkill();
							Long skillId= new Long(cellStringValue);
							Skill s= new Skill();
							s.setSkillId(skillId);
							skill.setSkill(s);
							empSkill.add(skill);
						}
				}
					else if (index == Skill6) {
						String cellStringValue = dataConverter(cell);
						if (!cellStringValue.equals("")) {
							EmployeeSkill skill = new EmployeeSkill();
							Long skillId= new Long(cellStringValue);
							Skill s= new Skill();
							s.setSkillId(skillId);
							skill.setSkill(s);
							empSkill.add(skill);
						}
				}
					else if (index == Skill7) {
						String cellStringValue = dataConverter(cell);
						if (!cellStringValue.equals("")) {
							EmployeeSkill skill = new EmployeeSkill();
							Long skillId= new Long(cellStringValue);
							Skill s= new Skill();
							s.setSkillId(skillId);
							skill.setSkill(s);
							empSkill.add(skill);
						}
				}
					else if (index == Skill8) {
						String cellStringValue = dataConverter(cell);
						if (!cellStringValue.equals("")) {
							EmployeeSkill skill = new EmployeeSkill();
							Long skillId= new Long(cellStringValue);
							Skill s= new Skill();
							s.setSkillId(skillId);
							skill.setSkill(s);
							empSkill.add(skill);
						}
				}
					else if (index == Skill9) {
						String cellStringValue = dataConverter(cell);
						if (!cellStringValue.equals("")) {
							EmployeeSkill skill = new EmployeeSkill();
							Long skillId= new Long(cellStringValue);
							Skill s= new Skill();
							s.setSkillId(skillId);
							skill.setSkill(s);
							empSkill.add(skill);
						}
				}
					else if (index == Skill10) {
						String cellStringValue = dataConverter(cell);
						if (!cellStringValue.equals("")) {
							EmployeeSkill skill = new EmployeeSkill();
							Long skillId= new Long(cellStringValue);
							Skill s= new Skill();
							s.setSkillId(skillId);
							skill.setSkill(s);
							empSkill.add(skill);
						}
				}
					
				}
	// read Employee UAN and PF sheet
	public void buildEmployeeUanAndPf(EmployeeStatuary empUanAndPf,EmployeeStatuary empStatuary, Cell cell, int index,
			FormulaEvaluator evaluator, StringBuilder stringBuilder) throws ParseException {
		//CellType type = cell.getCellTypeEnum();
		if (index == Employee_Code) {
			if (cell == null || cell.getCellType() == Cell.CELL_TYPE_BLANK) {
				stringBuilder.append("Employee Code, ");
			} else {
				String cellStringValue = dataConverter(cell);
				if (!cellStringValue.equals("")) {
					Employee employee = new Employee();
					employee.setEmployeeCode(cell.getStringCellValue());
					empUanAndPf.setEmployee(employee);
					empStatuary.setEmployee(employee);
			}
			}
		}
		else if (index == UAN_Number) {
			
			
			if (cell == null || cell.getCellType() == Cell.CELL_TYPE_BLANK ) {
				stringBuilder.append(" UAN Number, ");
			} else {
				
				String cellStringValue = dataConverter(cell);
				if (!cellStringValue.equals("")) {
					empUanAndPf.setStatuaryType("UA");
					empUanAndPf.setStatuaryNumber(cellStringValue);
				}
			}

		} 
		else if (index == PF_Number) {

			if (cell == null || cell.getCellType() == Cell.CELL_TYPE_BLANK) {
				stringBuilder.append(" Pf Number, ");
			} else {
				
				String cellStringValue = dataConverter(cell);
				if (!cellStringValue.equals("")) {
					empStatuary.setStatuaryType("PF");
					empStatuary.setStatuaryNumber(cellStringValue);
				}
			}

		} 
		else if (index == Effective_From) {

			if (cell == null || cell.getCellType() == Cell.CELL_TYPE_BLANK) {
				stringBuilder.append(" Effective from, ");
			} else {
				//String cellStringValue = dataConverter(cell);
				Date date= cell.getDateCellValue();
				if (!date.equals("")) {
					 SimpleDateFormat DtFormat = new SimpleDateFormat("dd/MM/yyyy");
					 String dateString= DtFormat.format(date);
					DateUtils dateUtils = new DateUtils();
					 Date dateFrom=	dateUtils.getDateFromString(dateString);
					 empStatuary.setDateFrom(dateFrom);
				}
			}
		}
		else if (index == Effective_To) {

			if(cell != null) {
				CellValue cellValue = evaluator.evaluate(cell);
				if(cellValue != null) {
				 Date date= cell.getDateCellValue();
						if (!date.toString().equals("") || date != null) {  //null pointer exception
							 SimpleDateFormat DtFormat = new SimpleDateFormat("dd/MM/yyyy");
							 String dateString= DtFormat.format(date);
							DateUtils dateUtils = new DateUtils();
							 Date dateTo=	dateUtils.getDateFromString(dateString);
							 empStatuary.setDateTo(dateTo);
						}
				} 
			 }
		}
		
	}
	// read Employee ESI Sheet
		public void buildEmployeeEsi(EmployeeStatuary empEsi, Cell cell, int index,
				FormulaEvaluator evaluator, StringBuilder stringBuilder) throws ParseException {
			//CellType type = cell.getCellTypeEnum();
			if (index == Employee_Code) {
				if (cell == null || cell.getCellType() == Cell.CELL_TYPE_BLANK) {
					stringBuilder.append("Employee Code, ");
				} else {
					String cellStringValue = dataConverter(cell);
					if (!cellStringValue.equals("")) {
						Employee employee = new Employee();
						employee.setEmployeeCode(cell.getStringCellValue());
						empEsi.setEmployee(employee);
				}
				}
			}
			else if (index == ESIC_Number) {
				
				
				if (cell == null || cell.getCellType() == Cell.CELL_TYPE_BLANK ) {
					stringBuilder.append(" ESIC Number, ");
				} else {
					
					String cellStringValue = dataConverter(cell);
					if (!cellStringValue.equals("")) {
						empEsi.setStatuaryType("ES");
						empEsi.setStatuaryNumber(cellStringValue);
					}
				}

			} 
			else if (index == Effective_from_Esi) {

				if (cell == null || cell.getCellType() == Cell.CELL_TYPE_BLANK) {
					stringBuilder.append(" Effective from, ");
				} else {
						 Date date= cell.getDateCellValue();
							if (!date.toString().equals("")) {
								 SimpleDateFormat DtFormat = new SimpleDateFormat("dd/MM/yyyy");
								 String dateString= DtFormat.format(date);
								DateUtils dateUtils = new DateUtils();
								 Date dateFrom=	dateUtils.getDateFromString(dateString);
								 empEsi.setDateFrom(dateFrom);
							
					}
				}
			}
			else if (index == Effective_to_Esi) {

				if(cell != null) {
					CellValue cellValue = evaluator.evaluate(cell);
					if(cellValue != null) {
					 Date date= cell.getDateCellValue();
							if (!date.toString().equals("") || date != null) {  //null pointer exception
								 SimpleDateFormat DtFormat = new SimpleDateFormat("dd/MM/yyyy");
								 String dateString= DtFormat.format(date);
								DateUtils dateUtils = new DateUtils();
								 Date dateTo=	dateUtils.getDateFromString(dateString);
								 empEsi.setDateTo(dateTo);
							}
					} 
				 }
			}
			
		}
		// read Employee MI Sheet
				public void buildEmployeeMi(EmployeeStatuary empEsi, Cell cell, int index,
						FormulaEvaluator evaluator, StringBuilder stringBuilder) throws ParseException {
					//CellType type = cell.getCellTypeEnum();
					if (index == Employee_Code) {
						if (cell == null || cell.getCellType() == Cell.CELL_TYPE_BLANK) {
							stringBuilder.append("Employee Code, ");
						} else {
							String cellStringValue = dataConverter(cell);
							if (!cellStringValue.equals("")) {
								Employee employee = new Employee();
								employee.setEmployeeCode(cell.getStringCellValue());
								empEsi.setEmployee(employee);
						}
						}
					}
					else if (index == Mi_Number) {
						
						
						if (cell == null || cell.getCellType() == Cell.CELL_TYPE_BLANK ) {
							stringBuilder.append(" MI Number, ");
						} else {
							
							String cellStringValue = dataConverter(cell);
							if (!cellStringValue.equals("")) {
								empEsi.setStatuaryType("ME");
								empEsi.setStatuaryNumber(cellStringValue);
							}
						}

					} 
				
					else if (index == Effective_from_Mi) {

						if (cell == null || cell.getCellType() == Cell.CELL_TYPE_BLANK) {
							stringBuilder.append(" Effective from, ");
						} else {
							 Date date= cell.getDateCellValue();
								if (!date.toString().equals("")) {
									 SimpleDateFormat DtFormat = new SimpleDateFormat("dd/MM/yyyy");
									 String dateString= DtFormat.format(date);
									DateUtils dateUtils = new DateUtils();
									 Date dateFrom=	dateUtils.getDateFromString(dateString);
									 empEsi.setDateFrom(dateFrom);
								}
						}
					}
					else if (index == Effective_To_Mi) {

						if (cell == null || cell.getCellType() == Cell.CELL_TYPE_BLANK) {
							stringBuilder.append(" Effective To, ");
						} else {
							Date date= cell.getDateCellValue();
							if (!date.toString().equals("")) {
								 SimpleDateFormat DtFormat = new SimpleDateFormat("dd/MM/yyyy");
								 String dateString= DtFormat.format(date);
								DateUtils dateUtils = new DateUtils();
								 Date dateTo=	dateUtils.getDateFromString(dateString);
								 empEsi.setDateTo(dateTo);
							}
						}
					}
					
				}
				// read Employee AI Sheet
				public void buildEmployeeAi(EmployeeStatuary empEsi, Cell cell, int index,
						FormulaEvaluator evaluator, StringBuilder stringBuilder) throws ParseException {
					//CellType type = cell.getCellTypeEnum();
					if (index == Employee_Code) {
						if (cell == null || cell.getCellType() == Cell.CELL_TYPE_BLANK) {
							stringBuilder.append("Employee Code, ");
						} else {
							String cellStringValue = dataConverter(cell);
							if (!cellStringValue.equals("")) {
								Employee employee = new Employee();
								employee.setEmployeeCode(cell.getStringCellValue());
								empEsi.setEmployee(employee);
						}
						}
					}
					else if (index == Ai_Number) {
						if (cell == null || cell.getCellType() == Cell.CELL_TYPE_BLANK ) {
							stringBuilder.append(" AI Number, ");
						} else {
							
							String cellStringValue = dataConverter(cell);
							if (!cellStringValue.equals("")) {
								empEsi.setStatuaryType("AC");
								empEsi.setStatuaryNumber(cellStringValue);
							}
						}

					} 
					else if (index == Effective_from_Ai) {

						if (cell == null || cell.getCellType() == Cell.CELL_TYPE_BLANK) {
							stringBuilder.append(" Effective from, ");
						} else {
							Date date= cell.getDateCellValue();
							if (!date.toString().equals("")) {
								 SimpleDateFormat DtFormat = new SimpleDateFormat("dd/MM/yyyy");
								 String dateString= DtFormat.format(date);
								DateUtils dateUtils = new DateUtils();
								 Date dateFrom=	dateUtils.getDateFromString(dateString);
								 empEsi.setDateFrom(dateFrom);
							}
						}
					}
					else if (index == Effective_To_Ai) {

						if (cell == null || cell.getCellType() == Cell.CELL_TYPE_BLANK) {
							stringBuilder.append(" Effective To, ");
						} else {
							Date date= cell.getDateCellValue();
							if (!date.toString().equals("")) {
								 SimpleDateFormat DtFormat = new SimpleDateFormat("dd/MM/yyyy");
								 String dateString= DtFormat.format(date);
								DateUtils dateUtils = new DateUtils();
								 Date dateTo=	dateUtils.getDateFromString(dateString);
								 empEsi.setDateTo(dateTo);
							}
						}
					}
					
				}
				
				// read Employee pay structure Sheet
				public void buildEmployeePaystructure(PayStructureHd empPay, Cell cell, int index,
						FormulaEvaluator evaluator, StringBuilder stringBuilder) throws ParseException {
					//CellType type = cell.getCellTypeEnum();
					if (index == Employee_Code) {
						if (cell == null || cell.getCellType() == Cell.CELL_TYPE_BLANK) {
							stringBuilder.append("Employee Code, ");
						} else {
							String cellStringValue = dataConverter(cell);
							if (!cellStringValue.equals("")) {
								Employee employee = new Employee();
								employee.setEmployeeCode(cell.getStringCellValue());
								empPay.setEmployee(employee);
						}
						}
					}
					else if (index == GrossPay) {
						if (cell == null || cell.getCellType() == Cell.CELL_TYPE_BLANK ) {
							stringBuilder.append(" Gross Pay, ");
						} else {
							
							String cellStringValue = dataConverter(cell);
							if (!cellStringValue.equals("")) {
								empPay.setGrossPay(new BigDecimal(cellStringValue));
								
							}
						}

					} 
		/*
		 * else if (index == Effective_Date) {
		 * 
		 * if (cell == null || cell.getCellType() == Cell.CELL_TYPE_BLANK) {
		 * stringBuilder.append(" Effective Date, "); } else { Date date=
		 * cell.getDateCellValue(); if (!date.toString().equals("")) { SimpleDateFormat
		 * DtFormat = new SimpleDateFormat("dd/MM/yyyy"); String dateString=
		 * DtFormat.format(date); DateUtils dateUtils = new DateUtils(); Date dateFrom=
		 * dateUtils.getDateFromString(dateString); empPay.setEffectiveDate(dateFrom); }
		 * } }
		 */
				
					else if (index == ProcessMonth) {

						if (cell == null || cell.getCellType() == Cell.CELL_TYPE_BLANK) {
							stringBuilder.append(" Process Month, ");
						} else {
							String cellStringValue = dataConverter(cell);
							if (!cellStringValue.equals("")) {
								empPay.setProcessMonth(cellStringValue);
							}
						}
					}
					else if (index == NetPay) {

						if (cell == null || cell.getCellType() == Cell.CELL_TYPE_BLANK) {
							stringBuilder.append("Net Pay, ");
						} else {
							String cellStringValue = dataConverter(cell);
							if (!cellStringValue.equals("")) {
								empPay.setNetPay(new BigDecimal(cellStringValue));
							}
						}
					}
					else if (index == CTC) {

						if (cell == null || cell.getCellType() == Cell.CELL_TYPE_BLANK) {
							stringBuilder.append(" CTC , ");
						} else {
							String cellStringValue = dataConverter(cell);
							if (!cellStringValue.equals("")) {
								empPay.setCtc(new BigDecimal(cellStringValue));
								empPay.setCostToCompany(new BigDecimal(cellStringValue));
							}
						}
					}
					
					
					else if (index == PFEmployee) {

						if (cell == null || cell.getCellType() == Cell.CELL_TYPE_BLANK) {
							stringBuilder.append(" PF Employee , ");
						} else {
							String cellStringValue = dataConverter(cell);
							if (!cellStringValue.equals("")) {
								empPay.setEpfEmployee(new BigDecimal(cellStringValue));
							}
						}
					}
					else if (index == PFEmployer) {

						if (cell == null || cell.getCellType() == Cell.CELL_TYPE_BLANK) {
							stringBuilder.append(" PF Employer , ");
						} else {
							String cellStringValue = dataConverter(cell);
							if (!cellStringValue.equals("")) {
								empPay.setEpfEmployer(new BigDecimal(cellStringValue));
							}
						}
					}
					
					
					
					else if (index == EPSEmployer) {

						if (cell == null || cell.getCellType() == Cell.CELL_TYPE_BLANK) {
							stringBuilder.append(" EPS Employer , ");
						} else {
							String cellStringValue = dataConverter(cell);
							if (!cellStringValue.equals("")) {
								empPay.setEpfEmployeePension(new BigDecimal(cellStringValue));
								
							}
						}
					}
					
					else if (index == ESIEmployee) {

						if (cell == null || cell.getCellType() == Cell.CELL_TYPE_BLANK) {
							stringBuilder.append(" ESI Employee, ");
						} else {
							String cellStringValue = dataConverter(cell);
							if (!cellStringValue.equals("")) {
								empPay.setEsiEmployee(new BigDecimal(cellStringValue));
							}
						}
					}
					
					else if (index == ESIEmployer) {

						if (cell == null || cell.getCellType() == Cell.CELL_TYPE_BLANK) {
							stringBuilder.append(" ESI Employer , ");
						} else {
							String cellStringValue = dataConverter(cell);
							if (!cellStringValue.equals("")) {
								empPay.setEsiEmployer(new BigDecimal(cellStringValue));
							}
						}
					}
					
					
					
					
					
					else if (index == LWFEmployee) {

						if (cell == null || cell.getCellType() == Cell.CELL_TYPE_BLANK) {
							stringBuilder.append(" LWF Employer, ");
						} else {
							String cellStringValue = dataConverter(cell);
							if (!cellStringValue.equals("")) {
								empPay.setLwfEmployeeAmount(new BigDecimal(cellStringValue));
							}
						}
					}
					
					
					else if (index == LWFEmployer) {

						if (cell == null || cell.getCellType() == Cell.CELL_TYPE_BLANK) {
							stringBuilder.append("LWF Employer , ");
						} else {
							String cellStringValue = dataConverter(cell);
							if (!cellStringValue.equals("")) {
								empPay.setLwfEmployerAmount(new BigDecimal(cellStringValue));
							}
						}
					}
					
					
					
					
					else if (index == ProfessionalTax) {

						if (cell == null || cell.getCellType() == Cell.CELL_TYPE_BLANK) {
							stringBuilder.append(" Professional Tax, ");
						} else {
							String cellStringValue = dataConverter(cell);
							if (!cellStringValue.equals("")) {
								empPay.setProfessionalTax(new BigDecimal(cellStringValue));
							}
						}
					}
//					else if (index == CTC1) {
//
//						if (cell == null || cell.getCellType() == Cell.CELL_TYPE_BLANK) {
//							stringBuilder.append(" Cost to company, ");
//						} else {
//							String cellStringValue = dataConverter(cell);
//							if (!cellStringValue.equals("")) {
//								empPay.setCostToCompany(new BigDecimal(cellStringValue));
//							}
//						}
//					}
					else	if (index == PayHeadID) {

						if (cell == null || cell.getCellType() == Cell.CELL_TYPE_BLANK) {
							stringBuilder.append("  Pay Head Id, ");
						} else {
							String cellStringValue = dataConverter(cell);
							if (!cellStringValue.equals("")) {
								empPay.setPayHeadExcelId(cellStringValue.trim());
							}
						}
					}
					else if (index == Amount) {

						if (cell == null || cell.getCellType() == Cell.CELL_TYPE_BLANK) {
							stringBuilder.append(" Amount, ");
						} else {
							String cellStringValue = dataConverter(cell);
							if (!cellStringValue.equals("")) {
								empPay.setAmountExcel(cellStringValue.trim());
							}
						}
					}
					else if (index == PFStatus) {

						if (cell == null || cell.getCellType() == Cell.CELL_TYPE_BLANK) {
							stringBuilder.append(" PF status , ");
						} else {
							CellValue cellValue = evaluator.evaluate(cell);
							if (cellValue != null) {
								String pfApplicable= cellValue.getStringValue();
								if(pfApplicable.equals("Applicable")) {
									empPay.setIs_pf_applicable("N");
								}
								else {
								empPay.setIs_pf_applicable("Y");	
								}
							}
							
						}
					}
					else if (index == ESIStatus) {

						if (cell == null || cell.getCellType() == Cell.CELL_TYPE_BLANK) {
							stringBuilder.append(" ESI status , ");
						} else {
							CellValue cellValue = evaluator.evaluate(cell);
							if (cellValue != null) {
								String esiApplicable= cellValue.getStringValue();
								if(esiApplicable.equals("Applicable")) {
									
									empPay.setIs_esi_applicable("N");	
								}
								else {
								empPay.setIs_esi_applicable("Y");	
								}
							}
						}
					}
					
					else if (index == LWFStatus) {

						if (cell == null || cell.getCellType() == Cell.CELL_TYPE_BLANK) {
							stringBuilder.append(" LWF status , ");
						} else {
							CellValue cellValue = evaluator.evaluate(cell);
							if (cellValue != null) {
								String lwfApplicable= cellValue.getStringValue();
								if(lwfApplicable.equals("Applicable")) {
									
									empPay.setIs_lwf_applicable("N");
								}
								else {
								empPay.setIs_lwf_applicable("Y");	
								}
							}
						}
					}
				}
				
	/*
	 * public void buildEmployeeLeaveBalance(EmployeeOpeningLeaveMaster
	 * employeeLeaveBalance, Cell cell, int index, FormulaEvaluator
	 * evaluator,StringBuilder stringBuilder) throws ParseException {
	 * 
	 * if (index == Employee_Code) { if (cell == null || cell.getCellType() ==
	 * Cell.CELL_TYPE_BLANK) { stringBuilder.append("Employee Code, "); } else {
	 * String cellStringValue = dataConverter(cell); if
	 * (!cellStringValue.equals("")) { Employee employee = new Employee();
	 * employee.setEmployeeCode(cell.getStringCellValue());
	 * employeeLeaveBalance.setEmployee(employee); } } } else if (index ==
	 * LeaveTypeID) {
	 * 
	 * if (cell == null || cell.getCellType() == Cell.CELL_TYPE_BLANK) {
	 * stringBuilder.append(" Leave Type, "); } else { CellValue cellValue =
	 * evaluator.evaluate(cell); if (cellValue != null) { String cellStringValue =
	 * cell.getStringCellValue(); //String id = EnumUtil.getEnumKey(idList,
	 * cellStringValue); if (!cellStringValue.equals("")) { employeeLeaveBalance.set
	 * // setIdTypeId(cellStringValue); } else {
	 * stringBuilder.append(" IdType is not present in database, "); } } }
	 * 
	 * } else if (index == ConsumedLeave) {
	 * 
	 * if (cell == null || cell.getCellType() == Cell.CELL_TYPE_BLANK ) {
	 * stringBuilder.append(" IdNumber, "); } else { String cellStringValue =
	 * dataConverter(cell); if (!cellStringValue.equals("")) {
	 * employeeLeaveBalance.setIdNumber(cellStringValue); } }
	 * 
	 * } }
	 */

	public void buildPayHeadInfo( PayStructure empPay , Cell cell, int index,
			FormulaEvaluator evaluator, StringBuilder stringBuilder) {

		 

	}

	public BigDecimal payheads(PayStructureHdDTO payStructureHdDTO, Cell cell, int index, FormulaEvaluator evaluator,
			StringBuilder stringBuilder) {
		BigDecimal amount = new BigDecimal(0);

		/*if (index == BasicSalary) {

			String cellStringValue = dataConverter(cell);

			if (!cellStringValue.equals("")) {
				PayStructureDTO payStructure = new PayStructureDTO();
				BigDecimal basic = new BigDecimal(cellStringValue);
				payStructure.setAmount(basic);
				payStructure.setPayHeadId(StandardEarningEnum.BasicSalary.getStandardEarning());

				payStructureHdDTO.getPayStructureDtoList().add(payStructure);
				amount = basic;
			}

		} else if (index == DearnessAllowance) {

			String cellStringValue = dataConverter(cell);

			if (!cellStringValue.equals("")) {
				PayStructureDTO payStructure = new PayStructureDTO();
				BigDecimal da = new BigDecimal(cellStringValue);
				payStructure.setAmount(da);
				payStructure.setPayHeadId(StandardEarningEnum.DearnessAllowance.getStandardEarning());
				amount = da;
				payStructureHdDTO.getPayStructureDtoList().add(payStructure);
			}

		} else if (index == HouseRentAllowance) {
			String cellStringValue = dataConverter(cell);

			if (!cellStringValue.equals("")) {
				PayStructureDTO payStructure = new PayStructureDTO();
				BigDecimal hra = new BigDecimal(cellStringValue);
				payStructure.setAmount(hra);
				payStructure.setPayHeadId(StandardEarningEnum.HouseRentAllowance.getStandardEarning());
				amount = hra;
				payStructureHdDTO.getPayStructureDtoList().add(payStructure);
			}

		} else if (index == ConveyanceAllowance) {

			String cellStringValue = dataConverter(cell);
			if (!cellStringValue.equals("")) {
				PayStructureDTO payStructure = new PayStructureDTO();
				BigDecimal ca = new BigDecimal(cellStringValue);
				payStructure.setAmount(ca);
				payStructure.setPayHeadId(StandardEarningEnum.ConveyanceAllowance.getStandardEarning());
				amount = ca;
				payStructureHdDTO.getPayStructureDtoList().add(payStructure);
			}

		} else if (index == SpecialAllowance) {
			String cellStringValue = dataConverter(cell);
			if (!cellStringValue.equals("")) {
				PayStructureDTO payStructure = new PayStructureDTO();
				BigDecimal sa = new BigDecimal(cellStringValue);
				payStructure.setAmount(sa);
				payStructure.setPayHeadId(StandardEarningEnum.SpecialAllowance.getStandardEarning());
				amount = sa;
				payStructureHdDTO.getPayStructureDtoList().add(payStructure);
			}

		} else if (index == MedicalAllowance) {
			String cellStringValue = dataConverter(cell);
			if (!cellStringValue.equals("")) {
				PayStructureDTO payStructure = new PayStructureDTO();
				BigDecimal ma = new BigDecimal(cellStringValue);
				payStructure.setAmount(ma);
				payStructure.setPayHeadId(StandardEarningEnum.MedicalAllowance.getStandardEarning());
				amount = ma;
				payStructureHdDTO.getPayStructureDtoList().add(payStructure);
			}

		} else if (index == AdvanceBonus) {
			String cellStringValue = dataConverter(cell);

			if (!cellStringValue.equals("")) {
				PayStructureDTO payStructure = new PayStructureDTO();
				BigDecimal ab = new BigDecimal(cellStringValue);
				payStructure.setAmount(ab);
				payStructure.setPayHeadId(StandardEarningEnum.AdvanceBonus.getStandardEarning());
				amount = ab;
				payStructureHdDTO.getPayStructureDtoList().add(payStructure);
			}

		} else if (index == PerformanceLinkedIncome) {

			String cellStringValue = dataConverter(cell);
			if (!cellStringValue.equals("")) {
				PayStructureDTO payStructure = new PayStructureDTO();
				BigDecimal pi = new BigDecimal(cellStringValue);
				payStructure.setAmount(pi);
				payStructure.setPayHeadId(StandardEarningEnum.PerformanceLinkedIncome.getStandardEarning());
				amount = pi;
				payStructureHdDTO.getPayStructureDtoList().add(payStructure);
			}

		} else if (index == CompanyBenefits) {

			String cellStringValue = dataConverter(cell);
			if (!cellStringValue.equals("")) {
				PayStructureDTO payStructure = new PayStructureDTO();
				BigDecimal cb = new BigDecimal(cellStringValue);
				payStructure.setAmount(cb);
				payStructure.setPayHeadId(StandardEarningEnum.CompanyBenefits.getStandardEarning());
				amount = cb;
				payStructureHdDTO.getPayStructureDtoList().add(payStructure);
			}

		} else if (index == LeaveTravelAllowance) {

			String cellStringValue = dataConverter(cell);
			if (!cellStringValue.equals("")) {
				PayStructureDTO payStructure = new PayStructureDTO();
				BigDecimal lta = new BigDecimal(cellStringValue);
				payStructure.setAmount(lta);
				payStructure.setPayHeadId(StandardEarningEnum.LeaveTravelAllowance.getStandardEarning());
				amount = lta;
				payStructureHdDTO.getPayStructureDtoList().add(payStructure);
			}

		} else if (index == UniformAllowance) {
			String cellStringValue = dataConverter(cell);
			if (!cellStringValue.equals("")) {
				PayStructureDTO payStructure = new PayStructureDTO();
				BigDecimal ua = new BigDecimal(cellStringValue);
				payStructure.setAmount(ua);
				payStructure.setPayHeadId(StandardEarningEnum.UniformAllowance.getStandardEarning());
				amount = ua;
				payStructureHdDTO.getPayStructureDtoList().add(payStructure);
			}

		}*/

		return amount;
	}

	// dataConverter
	private String dataConverter(Cell cell) {
			DataFormatter dataFormatter = new DataFormatter();
			String cellStringValue = dataFormatter.formatCellValue(cell);
			return cellStringValue;
	}


	private Designation getDesignationId(Map<Long, Designation> designationMap, Long designationId) {

		return designationMap.get(designationId);
	}

	private Department getDepartmentId(Map<Long, Department> departmentMap, Long departmentId) {

		return departmentMap.get(departmentId);
	}
	private Grade getGradeId(Map<Long, Grade> gradeMap, Long gradeId) {

		return gradeMap.get(gradeId);
	}
}
