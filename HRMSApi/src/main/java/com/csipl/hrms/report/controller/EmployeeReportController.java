package com.csipl.hrms.report.controller;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.csipl.common.services.dropdown.DropDownCache;
import com.csipl.hrms.common.enums.DropDownEnum;
import com.csipl.hrms.common.exception.PayRollProcessException;
import com.csipl.hrms.common.util.EmployeeExcelWriter;
import com.csipl.hrms.common.util.ErrorHandling;
import com.csipl.hrms.dto.employee.EmployeeDTO;
import com.csipl.hrms.dto.employee.EmployeeEducationDTO;
import com.csipl.hrms.dto.employee.EmployeeFamilyDTO;
import com.csipl.hrms.dto.employee.EmployeeIdProofDTO;
import com.csipl.hrms.dto.employee.EmployeeStatuaryDTO;
import com.csipl.hrms.dto.employee.SeparationDTO;
import com.csipl.hrms.dto.report.EsiInfoDTO;
import com.csipl.hrms.dto.report.PfReportDTO;
import com.csipl.hrms.model.employee.Employee;
import com.csipl.hrms.model.employee.EmployeeBank;
import com.csipl.hrms.model.employee.EmployeeLanguage;
import com.csipl.hrms.model.employee.ProfessionalInformation;
import com.csipl.hrms.org.BaseController;
import com.csipl.hrms.service.adaptor.EmployeeReportAdaptor;
import com.csipl.hrms.service.report.EmployeeReportService;

@RequestMapping("/employeesReport")
@RestController
public class EmployeeReportController extends BaseController {
	private static final Logger logger = LoggerFactory.getLogger(EmployeeReportController.class);
	@Autowired
	EmployeeReportService employeeReportService;

	EmployeeReportAdaptor employeeReportAdaptor = new EmployeeReportAdaptor();

	@RequestMapping(value = "/onBoardEmployeeReport/{companyId}/{status}/{departmentList}", method = RequestMethod.GET)
	public void generateActiveOnBoardEmp(@PathVariable("companyId") String companyId,
			@PathVariable("status") String status, @PathVariable List<Long> departmentList, HttpServletRequest req,
			HttpServletResponse response) throws ErrorHandling, PayRollProcessException {
		Long longcompanyId = Long.parseLong(companyId);
		logger.info("generate     employees");
		// String[] columns = { "Employee Code", "First Name", "Middle Name", "Last
		// Name", "Date Of Birth", "Gender",
		// "Date Of Joining", "Marital Status", "Plot/Steet/Area", "Landmark",
		// "Country", "State", "City",
		// "Pin Code", "Mobile No", "Telephone", "Email", "Plot/Steet/Area", "Landmark",
		// "Country", "State",
		// "City", "Pin Code", "Mobile No", "Telephone", "Email", "Job Location", "Job
		// State", "Blood Group",
		// "Probation days", "Emp Type", "Department", "Designation", "Contract Over
		// date", "Reference Name",
		// "Aadhar Card No", "Pan Card No", "PF No", "UAN", "ESI No", "Account Type",
		// "Bank name", "Account No",
		// "IFSC Code", "Basic Salary", "Dearness Allowance", "House Rent Allowance",
		// "Conveyance Allowance",
		// "Special Allowance", "Medical Allowance", "Advance Bonus", "Performance
		// Linked Income",
		// "Company Benefits", "Leave Travel Allowance", "Uniform Allowance" };

		String[] columns = { "Employee Code", "First Name", "Middle  Name", "Last Name", "Aadhar Card No", "Contact No",
				"Email ID", "Job Location", "   Shift   ", "Weekly Off Pattern", " Attendance Scheme ", "Leave Scheme",
				"Department", "Designation", " Branch ", "Reporting To", "Grade", "Probation Period", "Notice Period",
				"Permanent/Contract", "Date of Joining", "Contract Start Date", "Contract End Date",
				"Full Time/Part Time", " Official Email ID ", "System Role", "Date of Birth", "Gender", "Blood Group",
				"Alternate No", "Marital Status", "Anniversary Date", "Plot/Steet/Area", "Landmark", "Pin Code",
				"Country", "State", "City", "Plot/Steet/Area", "Landmark", "Pin Code", "Country", "State", "City",
				"Name", "Contact No", "Email ID", "Streat Area/Landmark", "Pin Code", "Country", "State", "City",
				"Bank Name", "Account No", "Branch", "IFSC Code" };

		String[] columns1 = { "Employee Code", "First Name", "Middle  Name", "Last Name", "Date Of Birth", "Gender",
				"Date Of Joining", "Date Of Resignation", "Marital Status", "Plot/Steet/Area", "Landmark", "Country",
				"State", "City", "Pin Code", "Mobile No", "Telephone", "Email", "Plot/Steet/Area", "Landmark",
				"Country", "State", "City", "Pin Code", "Mobile No", "Telephone", "Email", "Job Location", "Job State",
				"Blood Group", "Probation days", "Emp Type", "Department", "Designation", "Contract Over date",
				"Reference Name", "Aadhar Card No", "Pan Card No", "PF No", "UAN", "ESI No", "Account Type",
				"Bank name", "Account No", "IFSC Code", "Basic Salary", "Dearness Allowance", "House Rent Allowance",
				"Conveyance Allowance", "Special Allowance", "Medical Allowance", "Advance Bonus",
				"Performance Linked Income", "Company Benefits", "Leave Travel Allowance", "Uniform Allowance" };

		response.setContentType("application/application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
		response.setHeader("Content-Disposition", "attachment;filename=activeOnboardEmployeesReport.xlsx");
		response.setHeader(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, HttpHeaders.CONTENT_DISPOSITION);
		Workbook workbook;

		List<Object[]> employeeList = employeeReportService.findEmployeesReportStatusBased(status, longcompanyId,
				departmentList);

		List<EmployeeDTO> employeeDtoList = employeeReportAdaptor.databaseModelToUIDtoList(employeeList);

		try {
			if (status.equals("AC")) {
				workbook = EmployeeExcelWriter.employeeReport(columns, employeeDtoList, status);
			} else {
				workbook = EmployeeExcelWriter.employeeReport(columns1, employeeDtoList, status);
			}

			ServletOutputStream fileOut = response.getOutputStream();
			workbook.write(fileOut);
		} catch (InvalidFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@RequestMapping(value = "/formerEmployeeReport/{companyId}/{fromDate}/{toDate}/{departmentList}", method = RequestMethod.GET)
	public void getFormerEmployeeReport(@PathVariable("companyId") String companyId,
			@PathVariable("fromDate") String fromDate, @PathVariable("toDate") String toDate,
			@PathVariable List<Long> departmentList, HttpServletRequest req, HttpServletResponse response)
			throws ErrorHandling, PayRollProcessException, InvalidFormatException, ParseException {
		Long longcompanyId = Long.parseLong(companyId);
		logger.info("generate  employees");

		String[] columns = { "Employee Code", "First Name", "Middle  Name", "Last Name", "Aadhar Card No", "Contact No",
				"Email ID", "Job Location", "Shift", "Weekly Off Pattern", "Department", "Designation", "Reporting To",
				"Grade", "Probation Period", "Notice Period", "Permanent/Contract", "Date of Joining", "End Date",
				"Contract Start Date", "Contract End Date", "Full Time/Part Time", "System Role", "Date of Birth",
				"Gender", "Blood Group", "Alternate No", "Marital Status", "Anniversary Date", "Plot/Steet/Area",
				"Landmark", "Pin Code", "Country", "State", "City", "Plot/Steet/Area", "Landmark", "Pin Code",
				"Country", "State", "City", "Name", "Contact No", "Email ID", "Streat Area/Landmark", "Pin Code",
				"Country", "State", "City", "Bank Name", "Account No", "Branch", "IFSC Code" };

		response.setContentType("application/application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
		response.setHeader("Content-Disposition", "attachment;filename=formerEmployeeReport.xlsx");
		response.setHeader(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, HttpHeaders.CONTENT_DISPOSITION);
		Workbook workbook;

		DateFormat inputFormat = new SimpleDateFormat("E MMM dd yyyy ");
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date fromDate1 = dateFormat.parse(fromDate);
		Date toDate1 = dateFormat.parse(toDate);
		List<Object[]> employeeList = employeeReportService.findFormerEmployeeReport(longcompanyId, fromDate1, toDate1,
				departmentList);

		List<EmployeeDTO> employeeDtoList = employeeReportAdaptor.databaseListToUIDtoList(employeeList);

		try {
			workbook = EmployeeExcelWriter.formerEmployeeReport(columns, employeeDtoList);

			ServletOutputStream fileOut = response.getOutputStream();
			workbook.write(fileOut);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@RequestMapping(value = "/dept/{companyId}/{fromDate}/{toDate}/{deptId}/{status}", method = RequestMethod.GET)
	public void employeeReportDeptBased(@PathVariable("companyId") String companyId,
			@PathVariable("fromDate") String fromDate, @PathVariable("toDate") String toDate,
			@PathVariable("deptId") String deptId, @PathVariable("status") String status, HttpServletRequest req,
			HttpServletResponse response) throws ErrorHandling, PayRollProcessException, ParseException {
		Long longcompanyId = Long.parseLong(companyId);
		logger.info("employeeReportDeptBased      employees");

		String[] columns = { "Employee Code", "First Name", "Middle  Name", "Last Name", "Date Of Birth", "Gender",
				"Date Of Joining", "Marital Status", "Plot/Steet/Area", "Landmark", "Country", "State", "City",
				"Pin Code", "Mobile No", "Telephone", "Email", "Plot/Steet/Area", "Landmark", "Country", "State",
				"City", "Pin Code", "Mobile No", "Telephone", "Email", "Job Location", "Job State", "Blood Group",
				"Probation days", "Emp Type", "Department", "Designation", "Contract Over date", "Reference Name",
				"Aadhar Card No", "Pan Card No", "PF No", "UAN", "ESI No", "Account Type", "Bank name", "Account No",
				"IFSC Code", "Basic Salary", "Dearness Allowance", "House Rent Allowance", "Conveyance Allowance",
				"Special Allowance", "Medical Allowance", "Advance Bonus", "Performance Linked Income",
				"Company Benefits", "Leave Travel Allowance", "Uniform Allowance" };

		String[] columns1 = { "Employee Code", "First Name", "Middle  Name", "Last Name", "Date Of Birth", "Gender",
				"Date Of Joining", "Date Of Resignation", "Marital Status", "Plot/Steet/Area", "Landmark", "Country",
				"State", "City", "Pin Code", "Mobile No", "Telephone", "Email", "Plot/Steet/Area", "Landmark",
				"Country", "State", "City", "Pin Code", "Mobile No", "Telephone", "Email", "Job Location", "Job State",
				"Blood Group", "Probation days", "Emp Type", "Department", "Designation", "Contract Over date",
				"Reference Name", "Aadhar Card No", "Pan Card No", "PF No", "UAN", "ESI No", "Account Type",
				"Bank name", "Account No", "IFSC Code", "Basic Salary", "Dearness Allowance", "House Rent Allowance",
				"Conveyance Allowance", "Special Allowance", "Medical Allowance", "Advance Bonus",
				"Performance Linked Income", "Company Benefits", "Leave Travel Allowance", "Uniform Allowance" };

		response.setContentType("application/application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
		response.setHeader("Content-Disposition", "attachment;filename=employeesReport.xlsx");
		response.setHeader(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, HttpHeaders.CONTENT_DISPOSITION);
		Workbook workbook;

		DateFormat inputFormat = new SimpleDateFormat("E MMM dd yyyy ");
		Date fromDate1 = inputFormat.parse(fromDate);
		Date toDate1 = inputFormat.parse(toDate);
		Long departmentId = Long.parseLong(deptId);

		List<Employee> employeeList = employeeReportService.findEmployeesReportDeptAndStatusBased(longcompanyId,
				fromDate1, toDate1, departmentId, status);

		for (Employee employee2 : employeeList) {
			employee2.setGenderValue(DropDownCache.getInstance().getDropDownValue(DropDownEnum.Gender.getDropDownName(),
					employee2.getGender()));
			employee2.setMaritalStatusValue(DropDownCache.getInstance()
					.getDropDownValue(DropDownEnum.MaritalStatus.getDropDownName(), employee2.getMaritalStatus()));
			employee2.setBloodGroupValue(DropDownCache.getInstance()
					.getDropDownValue(DropDownEnum.BloodGroup.getDropDownName(), employee2.getBloodGroup()));
			employee2.setEmpTypeValue(DropDownCache.getInstance()
					.getDropDownValue(DropDownEnum.EmploymentType.getDropDownName(), employee2.getEmpType()));

			for (EmployeeBank employeeBank : employee2.getEmployeeBanks()) {
				if (employeeBank.getAccountType().equals("SA")) {
					employee2.setAccountTypeValue(DropDownCache.getInstance().getDropDownValue(
							DropDownEnum.AccountType.getDropDownName(), employeeBank.getAccountType()));
					employee2.setBankNameValue(DropDownCache.getInstance()
							.getDropDownValue(DropDownEnum.BankName.getDropDownName(), employeeBank.getBankId()));
				}

			}
		}

		// try {
		// if (status.equals("AC"))
		// workbook = EmployeeExcelWriter.employeeReport(columns, employeeList, status);
		// else
		// workbook = EmployeeExcelWriter.employeeReport(columns1, employeeList,
		// status);
		// ServletOutputStream fileOut = response.getOutputStream();
		// workbook.write(fileOut);
		// } catch (InvalidFormatException e) {
		// e.printStackTrace();
		// } catch (IOException e) {
		// e.printStackTrace();
		// }
	}

	@RequestMapping(path = "/idAddressProofsReport", method = RequestMethod.GET)
	public void idAddressProofsReport(@RequestParam Long companyId, @RequestParam(required = false) Long employeeId,
			@RequestParam(required = false) List<Long> departmentIds,
			@RequestParam(required = false) String activeStatus, HttpServletRequest req, HttpServletResponse response)
			throws ErrorHandling, PayRollProcessException, ParseException, InvalidFormatException {

		String[] columns = { "Employee Code", "Employee", "Adhar Card No", "Pan Card No", "Voter ID No",
				"Driving Licence No", "DL Issue Date", "DL Expiry Date", "Passport No", "PS Issue Date",
				"PS Expiry Date" };

		List<Object[]> addressIdProofsObj = employeeReportService.getIdAddressProofsReport(companyId, employeeId,
				departmentIds, activeStatus);
		List<EmployeeIdProofDTO> employeeIdProofDTOList = employeeReportAdaptor
				.objectListToIdAddressProofsReport(addressIdProofsObj, employeeId);

		try {
			response.setContentType("application/application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
			response.setHeader("Content-Disposition", "attachment;filename=IdAndAddressProofsReport.xlsx");
			response.setHeader(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, HttpHeaders.CONTENT_DISPOSITION);
			if (companyId != null) {
				Workbook workbook = EmployeeExcelWriter.idAddressProofsReportWriter(employeeIdProofDTOList, columns,
						employeeId, activeStatus);
				ServletOutputStream fileOut = response.getOutputStream();
				workbook.write(fileOut);
			} else
				throw new ErrorHandling("Invalid session .Please login again");
		} catch (IOException e) {

			e.printStackTrace();
		}

	}

	/**
	 * @author ${Nisha Parveen}
	 *
	 */
	@RequestMapping(path = "/accidentalInsuranceReport", method = RequestMethod.GET)
	public void accidentalInsuranceReport(@RequestParam Long companyId, @RequestParam(required = false) Long employeeId,
			@RequestParam(required = false) List<Long> departmentIds,
			@RequestParam(required = false) String activeStatus, HttpServletRequest req, HttpServletResponse response)
			throws ErrorHandling, PayRollProcessException, ParseException, InvalidFormatException, IOException {

		String[] column = { "  Employee Code  ", "  Employee  ", "  Insurance Number  ", "  Effective From  ",
				"  Effective To  ", };

		List<Object[]> reportAcInsObj = employeeReportService.getAccidentalInsuranceReport(companyId, employeeId,
				departmentIds, activeStatus);

		List<EmployeeStatuaryDTO> reportAcInsDTOList = employeeReportAdaptor
				.objectListToAccidentalInsuranceReport(reportAcInsObj);

		try {
			response.setContentType("application/application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
			response.setHeader("Content-Disposition", "attachment;filename=AccidentalAndInsuranceReport.xlsx");
			response.setHeader(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, HttpHeaders.CONTENT_DISPOSITION);
			if (companyId != null) {
				Workbook workbook = EmployeeExcelWriter.accidentalInsuranceReportWriter(reportAcInsDTOList, column,
						activeStatus);
				ServletOutputStream fileOut = response.getOutputStream();
				workbook.write(fileOut);
			}
		} catch (IOException e) {

			e.printStackTrace();
		}
	}

	/**
	 * @author ${Nisha Parveen}
	 *
	 */
	@RequestMapping(path = "/pfUANNumbersReport", method = RequestMethod.GET)
	public void pfUANNumbersReport(@RequestParam Long companyId, @RequestParam(required = false) Long employeeId,
			@RequestParam(required = false) List<Long> departmentIds,
			@RequestParam(required = false) String activeStatus, HttpServletRequest req, HttpServletResponse response)
			throws ErrorHandling, PayRollProcessException, ParseException, InvalidFormatException, IOException {

		String[] column = { "Employee Code", "Employee", "UAN", "PF Number", "Effective From", "Effective To" };

		List<Object[]> employeePfUanObj = employeeReportService.getPfUANNumbersReport(companyId, employeeId,
				departmentIds, activeStatus);

		List<PfReportDTO> employeePfUanDTOList = employeeReportAdaptor.objectListToPfUANNumbersReport(employeePfUanObj,
				departmentIds);

		try {
			response.setContentType("application/application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
			response.setHeader("Content-Disposition", "attachment;filename=PFAndUANNumbersReport.xlsx");
			response.setHeader(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, HttpHeaders.CONTENT_DISPOSITION);
			if (companyId != null) {
				Workbook workbook = EmployeeExcelWriter.pfUANReportWriter(employeePfUanDTOList, column, departmentIds,
						employeeId, activeStatus);
				ServletOutputStream fileOut = response.getOutputStream();
				workbook.write(fileOut);
			} else
				throw new ErrorHandling("Invalid session .Please login again");
		} catch (IOException e) {

			e.printStackTrace();
		}
	}

	/**
	 * @author ${Nisha Parveen}
	 *
	 */
	@RequestMapping(path = "/familyDetailsOfEmployee", method = RequestMethod.GET)
	public void FamilyDetailsReport(@RequestParam Long companyId, @RequestParam(required = false) Long employeeId,
			@RequestParam(required = false) List<Long> departmentIds,
			@RequestParam(required = false) String activeStatus, HttpServletRequest req, HttpServletResponse response)
			throws ErrorHandling, PayRollProcessException, ParseException, InvalidFormatException {

		String[] columns = { "   Employee Code   ", "     Employee     ", "   Name   ", "   Relation   ",
				"   Education   ", "   Occupation   ", "   Date Of Birth   ", "   Contact No   " };

		Map<String, List<EmployeeFamilyDTO>> familyDetailsObj = employeeReportService.getFamilyDetailsReport(companyId,
				employeeId, departmentIds, activeStatus);

		// List<EmployeeFamilyDTO> employeeFamilyDTOList =
		// employeeReportAdaptor.objectListToFamilyDetailsReport(familyDetailsObj);

		try {
			response.setContentType("application/application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
			response.setHeader("Content-Disposition", "attachment;filename=FamilyDetailsReport.xlsx");
			response.setHeader(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, HttpHeaders.CONTENT_DISPOSITION);
			if (companyId != null) {
				Workbook workbook = EmployeeExcelWriter.familyDetailsReportWriter(columns, familyDetailsObj,
						activeStatus);
				ServletOutputStream fileOut = response.getOutputStream();
				workbook.write(fileOut);
			} else
				throw new ErrorHandling("Invalid session .Please login again");
		} catch (IOException e) {

			e.printStackTrace();
		}
	}

	/**
	 * @author ${Nisha Parveen}
	 *
	 */
	@RequestMapping(path = "/educationalDetailsOfEmployee", method = RequestMethod.GET)
	public void EducationalDetailsReport(@RequestParam Long companyId, @RequestParam(required = false) Long employeeId,
			@RequestParam(required = false) List<Long> departmentIds,
			@RequestParam(required = false) String activeStatus, HttpServletRequest req, HttpServletResponse response)
			throws ErrorHandling, PayRollProcessException, ParseException, InvalidFormatException {

		String[] columns = { "   Employee Code   ", "     Employee     ", "  Education Level  ", "   Degree   ",
				"   School/College   ", "   Board/University   ", "   Marks/Grade   ", "   Year Of Passing   ",
				"   Regular/Corresponding   " };

		Map<String, List<EmployeeEducationDTO>> educationalDetailsObj = employeeReportService
				.getEducationalDetailsReport(companyId, employeeId, departmentIds, activeStatus);

		try {
			response.setContentType("application/application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
			response.setHeader("Content-Disposition", "attachment;filename=EducationalDetailsReport.xlsx");
			response.setHeader(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, HttpHeaders.CONTENT_DISPOSITION);
			if (companyId != null) {
				Workbook workbook = EmployeeExcelWriter.educationalDetailsReportWriter(columns, educationalDetailsObj,
						activeStatus);
				ServletOutputStream fileOut = response.getOutputStream();
				workbook.write(fileOut);
			} else
				throw new ErrorHandling("Invalid session .Please login again");
		} catch (IOException e) {

			e.printStackTrace();
		}
	}

	@RequestMapping(path = "/esicReport", method = RequestMethod.GET)
	public void employeeEsicDataReport(Long companyId, @RequestParam(required = false) Long employeeId,
			@RequestParam(required = false) String status, @RequestParam(required = false) List<Long> departmentList,
			HttpServletRequest req, HttpServletResponse response)
			throws ErrorHandling, PayRollProcessException, ParseException, InvalidFormatException, IOException {

		String[] columns = { "   Employee Code   ", "   Employee   ", "   ESIC Number   ", "   Effective From   ",
				"   Effective To   " };

		List<Object[]> esiInfoObjectList = employeeReportService.getEsicReport(companyId, employeeId, departmentList,
				status);
		List<EsiInfoDTO> esiInfoDTOList = employeeReportAdaptor.objectListToEsiInfoDtoList(esiInfoObjectList);

		try {
			response.setContentType("application/application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
			response.setHeader("Content-Disposition", "attachment;filename=ESIC Numbers Report.xlsx");
			response.setHeader(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, HttpHeaders.CONTENT_DISPOSITION);
			if (companyId != null) {
				Workbook workbook = EmployeeExcelWriter.esicReportWriter(columns, esiInfoDTOList, status);
				ServletOutputStream fileOut = response.getOutputStream();
				workbook.write(fileOut);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (NumberFormatException ne) {
			ne.printStackTrace();
		}

	}

	@RequestMapping(path = "/medicalInsuranceReport", method = RequestMethod.GET)
	public void employeeMinDataReport(Long companyId, @RequestParam(required = false) Long employeeId,
			@RequestParam(required = false) String status, @RequestParam(required = false) List<Long> departmentList,
			HttpServletRequest req, HttpServletResponse response)
			throws ErrorHandling, PayRollProcessException, ParseException, InvalidFormatException, IOException {

		String[] columns = { "   Employee Code   ", "   Employee   ", "   Insurance Number   ", "   Effective From   ",
				"   Effective To   " };

		List<Object[]> medInsObjectList = employeeReportService.getMedicalInsuranceReport(companyId, employeeId,
				departmentList, status);
		List<EmployeeStatuaryDTO> employeeStatuaryDTOList = employeeReportAdaptor
				.objectListToEmployeeStatuaryDtoList(medInsObjectList);

		try {

			response.setContentType("application/application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
			response.setHeader("Content-Disposition", "attachment;filename=Medical Insurance Number Report.xlsx");
			response.setHeader(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, HttpHeaders.CONTENT_DISPOSITION);
			if (companyId != null) {

				Workbook workbook = EmployeeExcelWriter.medicalInsuranceReportWriter(columns, employeeStatuaryDTOList,
						status);
				ServletOutputStream fileOut = response.getOutputStream();
				workbook.write(fileOut);

			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (NumberFormatException ne) {
			ne.printStackTrace();
		}
	}

	@RequestMapping(path = "/professionalDetailsReport", method = RequestMethod.GET)
	public void professionalDetailsReport(@RequestParam Long companyId, @RequestParam(required = false) Long employeeId,
			@RequestParam(required = false) String status, @RequestParam(required = false) List<Long> departmentList,
			HttpServletRequest req, HttpServletResponse response)
			throws ErrorHandling, ParseException, InvalidFormatException, IOException {

		String[] columns = { "Employee Code", "    Employee    ", "Organization Name", "Working From", "Working To",
				" Designation ", " Reporting To ", "Contact No.", "Annual Salary", "Reason for Change" };

		Map<String, List<ProfessionalInformation>> profDetailsList = employeeReportService
				.getProfessionalDetailsReport(companyId, employeeId, status, departmentList);

		try {
			response.setContentType("application/application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
			response.setHeader("Content-Disposition", "attachment;filename=Professional Details Report.xlsx");
			response.setHeader(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, HttpHeaders.CONTENT_DISPOSITION);
			if (companyId != null) {
				Workbook workbook = EmployeeExcelWriter.profDetailsReportWriter(columns, profDetailsList, status);
				ServletOutputStream fileOut = response.getOutputStream();
				workbook.write(fileOut);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (NumberFormatException ne) {
			ne.printStackTrace();
		}

	}

	// Nominee Details

	@RequestMapping(path = "/nomineeDetailsReport", method = RequestMethod.GET)
	public void nomineeDetailsReport(@RequestParam Long companyId, @RequestParam(required = false) Long employeeId,
			@RequestParam(required = false) String status, @RequestParam(required = false) List<Long> departmentList,
			HttpServletRequest req, HttpServletResponse response)
			throws ErrorHandling, ParseException, InvalidFormatException, IOException {

		String[] columns = { "   Employee Code   ", "        Employee Name        ", "   Nominee Name   ",
				"   Relation   ", "   Nominee For   ", "   Contact Number   " };

		Map<String, List<EmployeeStatuaryDTO>> nomineeDetailsList = employeeReportService
				.getNomineeDetailsReport(companyId, employeeId, status, departmentList);

		try {
			response.setContentType("application/application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
			response.setHeader("Content-Disposition", "attachment;filename=Nominee Details Report.xlsx");
			response.setHeader(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, HttpHeaders.CONTENT_DISPOSITION);
			if (companyId != null) {
				Workbook workbook = EmployeeExcelWriter.nomineeDetailsReportWriter(columns, nomineeDetailsList, status);
				ServletOutputStream fileOut = response.getOutputStream();
				workbook.write(fileOut);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (NumberFormatException ne) {
			ne.printStackTrace();
		}

	}

	// employees Details Working On notice Period

	@RequestMapping(value = "/employeesOnNoticePeriod/{companyId}/{fromDate}/{toDate}", method = RequestMethod.GET)
	public void employeesDetailsWorkingOnNoticePeriodReport(@PathVariable("companyId") Long companyId,
			@PathVariable("fromDate") String fromDate, @PathVariable("toDate") String toDate, HttpServletRequest req,
			HttpServletResponse response)
			throws ErrorHandling, PayRollProcessException, InvalidFormatException, ParseException {

		String[] columns = { "Employee Code", "First Name", "Middle  Name", "Last Name", "Contact No", "Email ID",
				"Date of Joining", "Job Location", "Shift", "Weekly Off Pattern", "Department", "Designation",
				"Reporting To", "Grade", "Probation Period", "Notice Period", "Permanent/Contract",
				"Full Time/Part Time", "Resigned On", "Date of Exit" };

		DateFormat inputFormat = new SimpleDateFormat("E MMM dd yyyy ");
		Date fDate = inputFormat.parse(fromDate);
		Date tDate = inputFormat.parse(toDate);

		List<Object[]> employeeList = employeeReportService.getEmployeesOnNoticePeriod(companyId, fDate, tDate);

		List<EmployeeDTO> employeeDtoList = employeeReportAdaptor.objectListToEmployeesOnNoticePeriod(employeeList);

		try {
			response.setContentType("application/application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
			response.setHeader("Content-Disposition", "attachment;filename=EmployeesOnNoticePeriod.xlsx");
			response.setHeader(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, HttpHeaders.CONTENT_DISPOSITION);
			if (companyId != null) {
				Workbook workbook = EmployeeExcelWriter.employeeOnNoticePeriodReportWriter(columns, employeeDtoList,
						fDate, tDate);
				ServletOutputStream fileOut = response.getOutputStream();
				workbook.write(fileOut);
			} else
				throw new ErrorHandling("Invalid session .Please login again");
		} catch (IOException e) {

			e.printStackTrace();
		}
	}

	// Language Known Status
	/**
	 * @author ${Nisha Parveen}
	 *
	 */
	@RequestMapping(path = "/languageKnownStatusReport", method = RequestMethod.GET)
	public void languageKnownStatusReport(@RequestParam Long companyId, @RequestParam(required = false) Long employeeId,
			@RequestParam(required = false) String activeStatus,
			@RequestParam(required = false) List<Long> departmentIds, HttpServletRequest req,
			HttpServletResponse response)
			throws ErrorHandling, PayRollProcessException, ParseException, InvalidFormatException, IOException {

		String[] column = { " Employee Code ", " First Name ", " Middle Name ", " Last Name ", " Department ",
				" Designation ", " Read ", " Write ", " Speak ", " Read ", " Write ", " Speak " };

		List<Object[]> languageKnownObj = employeeReportService.getLanguageKnownStatusReport(companyId, employeeId,
				activeStatus, departmentIds);

		List<EmployeeLanguage> employeeLanguageList = employeeReportAdaptor
				.objectListToLanguageKnownStatusReport(languageKnownObj, employeeId);

		try {
			response.setContentType("application/application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
			response.setHeader("Content-Disposition", "attachment;filename=Language known status report.xlsx");
			response.setHeader(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, HttpHeaders.CONTENT_DISPOSITION);
			if (companyId != null) {
				Workbook workbook = EmployeeExcelWriter.languageKnownStatusReportWriter(employeeId, column,
						activeStatus, employeeLanguageList);

				ServletOutputStream fileOut = response.getOutputStream();

				workbook.write(fileOut);
			}
		} catch (IOException e) {

			e.printStackTrace();
		}
	}

	// Separation request summary report
	@RequestMapping(value = "/separationReqSummary", method = RequestMethod.GET)
	public void earlyComersReport(@RequestParam Long companyId, @RequestParam String fromDate,
			@RequestParam String toDate, @RequestParam(required = false) Long employeeId,
			@RequestParam(required = false) List<Long> departmentIds, HttpServletRequest req,
			HttpServletResponse response) throws ErrorHandling, ParseException, IOException, InvalidFormatException {

		DateFormat inputFormat = new SimpleDateFormat("E MMM dd yyyy ");
		Date fDate = inputFormat.parse(fromDate);
		Date tDate = inputFormat.parse(toDate);

		List<Object[]> sepRequestObj = employeeReportService.getSeparationReqSummary(companyId, employeeId,
				departmentIds, fDate, tDate);

		List<SeparationDTO> separationDTOList = employeeReportAdaptor
				.objectListToSeparationRequestReport(sepRequestObj);

		String[] columns = { "  Code  ", "  Employee  ", "  Department  ", "  Designation  ", "  Job Location  ",
				"  Reporting Manager  ", "  Joining Date  ", "  Requested On  ", "  Reason  ", " Exit Date ",
				" Notice Period Served ", "  Employee Remarks  " };

		try {
			response.setContentType("application/application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
			response.setHeader("Content-Disposition", "attachment;filename=Separation Request Summary Report.xlsx");
			response.setHeader(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, HttpHeaders.CONTENT_DISPOSITION);
			if (companyId > 0) {
				Workbook workbook = EmployeeExcelWriter.separationRequestReport(separationDTOList, columns, fDate,
						tDate);

				ServletOutputStream fileOut = response.getOutputStream();

				workbook.write(fileOut);
			} else
				throw new ErrorHandling("Invalid session .Please login again");
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}