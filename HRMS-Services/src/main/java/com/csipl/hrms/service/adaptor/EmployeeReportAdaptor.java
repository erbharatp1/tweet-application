package com.csipl.hrms.service.adaptor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.csipl.common.services.dropdown.DropDownCache;
import com.csipl.hrms.common.enums.DropDownEnum;
import com.csipl.hrms.dto.employee.EmployeeDTO;
import com.csipl.hrms.dto.employee.EmployeeEducationDTO;
import com.csipl.hrms.dto.employee.EmployeeFamilyDTO;
import com.csipl.hrms.dto.employee.EmployeeIdProofDTO;
import com.csipl.hrms.dto.employee.EmployeeStatuaryDTO;
import com.csipl.hrms.dto.employee.SeparationDTO;
import com.csipl.hrms.dto.organisation.AddressDTO;
import com.csipl.hrms.dto.report.EsiInfoDTO;
import com.csipl.hrms.dto.report.PfReportDTO;
import com.csipl.hrms.model.employee.EmployeeLanguage;
import com.csipl.hrms.model.employee.ProfessionalInformation;
import com.csipl.hrms.service.util.ConverterUtil;

public class EmployeeReportAdaptor {

	public List<EmployeeDTO> databaseModelToObjectArray(List<Object[]> objectEmployeeList) {
		List<EmployeeDTO> employeeDtoList = new ArrayList<EmployeeDTO>();

		for (Object[] report : objectEmployeeList) {
			EmployeeDTO employeeDto = new EmployeeDTO();
			AddressDTO addressDto = new AddressDTO();
			String employeeCode = report[0] != null ? (String) report[0] : null;
			String firstName = report[1] != null ? (String) report[1] : null;
			String middleName = report[2] != null ? (String) report[2] : null;
			String lastName = report[3] != null ? (String) report[3] : null;
			Date dob = report[4] != null ? (Date) report[4] : null;
			String gender = report[5] != null ? (String) report[5] : null;
			String maritalStatus = report[6] != null ? (String) report[6] : null;
			String addressText = report[7] != null ? (String) report[7] : null;
			String landmark = report[8] != null ? (String) report[8] : null;
			String empAddrsCountryName = report[9] != null ? (String) report[9] : null;
			String empAddrsStateName = report[10] != null ? (String) report[10] : null;
			String empAddrsCityName = report[11] != null ? (String) report[11] : null;
			String pincode = report[12] != null ? (String) report[12] : null;
			String mobile = report[13] != null ? (String) report[13] : null;
			String telephone = report[14] != null ? (String) report[14] : null;
			String emailId = report[15] != null ? (String) report[15] : null;
			String empStateName = report[16] != null ? (String) (report[16]) : null;
			String empCityName = report[17] != null ? (String) (report[17]) : null;
			String bloodGroup = report[18] != null ? (String) report[18] : null;
			Long probationDays = report[19] != null ? Long.parseLong(report[19].toString()) : null;
			String empType = report[20] != null ? (String) report[20] : null;
			String departmentName = report[21] != null ? (String) report[21] : null;
			String designationName = report[22] != null ? (String) report[22] : null;
			Date contractOverDate = report[23] != null ? (Date) report[23] : null;
			String referenceName = report[24] != null ? (String) report[24] : null;
			Date dateOfJoining = report[25] != null ? (Date) report[25] : null;
			String accountType = report[26] != null ? (String) report[26] : null;
			String bankId = report[27] != null ? (String) report[27] : null;
			String accountNumber = report[28] != null ? (String) report[28] : null;
			String ifscCode = report[29] != null ? (String) report[29] : null;
			String idType = report[30] != null ? (String) report[30] : null;
			String idNumber = report[31] != null ? (String) report[31] : null;
			String statuaryType = report[32] != null ? (String) report[32] : null;
			String statuaryNumber = report[33] != null ? (String) report[33] : null;
			String payHeadId = report[34] != null ? (String) report[34] : null;
			String payHeadAmount = report[35] != null ? (String) report[35] : null;
			String[] idTypeParts = null, idNumberParts = null, statuaryTypeParts = null, statuaryNumberParts = null,
					payHeadIdParts = null, payHeadAmountParts = null;

			if (idType != null)
				idTypeParts = idType.split(",");
			if (idNumber != null)
				idNumberParts = idNumber.split(",");
			if (statuaryType != null)
				statuaryTypeParts = statuaryType.split(",");
			if (statuaryNumber != null)
				statuaryNumberParts = statuaryNumber.split(",");
			if (payHeadId != null)
				payHeadIdParts = payHeadId.split(",");
			if (payHeadAmount != null)
				payHeadAmountParts = payHeadAmount.split(",");

			String typePart1 = null;
			String typePart2 = null;
			String typePart3 = null;
			String typePart4 = null;
			String typePart5 = null;
			String numberPart1 = null;
			String numberPart2 = null;
			String numberPart3 = null;
			String numberPart4 = null;
			String numberPart5 = null;

			String statuaryTypePart1 = null;
			String statuaryTypePart2 = null;
			String statuaryTypePart3 = null;
			String statuaryTypePart4 = null;
			String statuaryTypePart5 = null;
			String statuaryNumberPart1 = null;
			String statuaryNumberPart2 = null;
			String statuaryNumberPart3 = null;
			String statuaryNumberPart4 = null;
			String statuaryNumberPart5 = null;

			String payHeadIdPart1 = null;
			String payHeadIdPart2 = null;
			String payHeadIdPart3 = null;
			String payHeadIdPart4 = null;
			String payHeadIdPart5 = null;
			String payHeadIdPart6 = null;
			String payHeadIdPart7 = null;
			String payHeadIdPart8 = null;
			String payHeadIdPart9 = null;
			String payHeadIdPart10 = null;
			String payHeadIdPart11 = null;

			String payHeadNumberPart1 = null;
			String payHeadNumberPart2 = null;
			String payHeadNumberPart3 = null;
			String payHeadNumberPart4 = null;
			String payHeadNumberPart5 = null;
			String payHeadNumberPart6 = null;
			String payHeadNumberPart7 = null;
			String payHeadNumberPart8 = null;
			String payHeadNumberPart9 = null;
			String payHeadNumberPart10 = null;
			String payHeadNumberPart11 = null;

			try {
				if (idTypeParts[0] != null)
					typePart1 = idTypeParts[0];
				if (idTypeParts[1] != null)
					typePart2 = idTypeParts[1];
				if (idTypeParts[2] != null)
					typePart3 = idTypeParts[2];
				if (idTypeParts[3] != null)
					typePart4 = idTypeParts[3];
				if (idTypeParts[4] != null)
					typePart5 = idTypeParts[4];
			} catch (ArrayIndexOutOfBoundsException e) {
			} catch (NullPointerException mpe) {
			}
			try {
				numberPart1 = idNumberParts[0];
				numberPart2 = idNumberParts[1];
				numberPart3 = idNumberParts[2];
				numberPart4 = idNumberParts[3];
				numberPart5 = idNumberParts[4];

			} catch (ArrayIndexOutOfBoundsException e) {
			} catch (NullPointerException mpe) {
			}
			try {
				if (statuaryTypeParts[0] != null)
					statuaryTypePart1 = statuaryTypeParts[0];
				if (statuaryTypeParts[1] != null)
					statuaryTypePart2 = statuaryTypeParts[1];
				if (statuaryTypeParts[2] != null)
					statuaryTypePart3 = statuaryTypeParts[2];
				if (statuaryTypeParts[3] != null)
					statuaryTypePart4 = statuaryTypeParts[3];
				if (statuaryTypeParts[4] != null)
					statuaryTypePart5 = statuaryTypeParts[4];

			} catch (ArrayIndexOutOfBoundsException e) {
			} catch (NullPointerException mpe) {
			}

			try {
				if (statuaryNumberParts[0] != null)
					statuaryNumberPart1 = statuaryNumberParts[0];
				if (statuaryNumberParts[1] != null)
					statuaryNumberPart2 = statuaryNumberParts[1];
				if (statuaryNumberParts[2] != null)
					statuaryNumberPart3 = statuaryNumberParts[2];
				if (statuaryNumberParts[3] != null)
					statuaryNumberPart4 = statuaryNumberParts[3];
				if (statuaryNumberParts[4] != null)
					statuaryNumberPart5 = statuaryNumberParts[4];

			} catch (ArrayIndexOutOfBoundsException e) {
			} catch (NullPointerException mpe) {
			}

			try {
				if (payHeadIdParts[0] != null)
					payHeadIdPart1 = payHeadIdParts[0];
				if (payHeadIdParts[1] != null)
					payHeadIdPart2 = payHeadIdParts[1];
				if (payHeadIdParts[2] != null)
					payHeadIdPart3 = payHeadIdParts[2];
				if (payHeadIdParts[3] != null)
					payHeadIdPart4 = payHeadIdParts[3];
				if (payHeadIdParts[4] != null)
					payHeadIdPart5 = payHeadIdParts[4];
				if (payHeadIdParts[5] != null)
					payHeadIdPart6 = payHeadIdParts[5];
				if (payHeadIdParts[6] != null)
					payHeadIdPart7 = payHeadIdParts[6];
				if (payHeadIdParts[7] != null)
					payHeadIdPart8 = payHeadIdParts[7];
				if (payHeadIdParts[8] != null)
					payHeadIdPart9 = payHeadIdParts[8];
				if (payHeadIdParts[9] != null)
					payHeadIdPart10 = payHeadIdParts[9];
				if (payHeadIdParts[10] != null)
					payHeadIdPart11 = payHeadIdParts[10];
			} catch (ArrayIndexOutOfBoundsException e) {
			} catch (NullPointerException mpe) {
			}
			try {
				if (payHeadAmountParts[0] != null)
					payHeadNumberPart1 = payHeadAmountParts[0];
				if (payHeadAmountParts[1] != null)
					payHeadNumberPart2 = payHeadAmountParts[1];
				if (payHeadAmountParts[2] != null)
					payHeadNumberPart3 = payHeadAmountParts[2];
				if (payHeadAmountParts[3] != null)
					payHeadNumberPart4 = payHeadAmountParts[3];
				if (payHeadAmountParts[4] != null)
					payHeadNumberPart5 = payHeadAmountParts[4];
				if (payHeadAmountParts[5] != null)
					payHeadNumberPart6 = payHeadAmountParts[5];
				if (payHeadAmountParts[6] != null)
					payHeadNumberPart7 = payHeadAmountParts[6];
				if (payHeadAmountParts[7] != null)
					payHeadNumberPart8 = payHeadAmountParts[7];
				if (payHeadAmountParts[8] != null)
					payHeadNumberPart9 = payHeadAmountParts[8];
				if (payHeadAmountParts[9] != null)
					payHeadNumberPart10 = payHeadAmountParts[9];
				if (payHeadAmountParts[10] != null)
					payHeadNumberPart11 = payHeadAmountParts[10];
			} catch (ArrayIndexOutOfBoundsException e) {
			} catch (NullPointerException mpe) {
			}

			String aadhar = null;
			String pan = null;

			if (typePart1 != null && typePart1.equals("AA")) {
				aadhar = numberPart1;

			} else if (typePart2 != null && typePart2.equals("AA")) {
				aadhar = numberPart2;

			} else if (typePart3 != null && typePart3.equals("AA")) {
				aadhar = numberPart3;

			} else if (typePart4 != null && typePart4.equals("AA")) {
				aadhar = numberPart4;

			} else if (typePart5 != null && typePart5.equals("AA")) {
				aadhar = numberPart5;

			}
			if (typePart2 != null && typePart1.equals("PA")) {
				pan = numberPart1;
			} else if (typePart2 != null && typePart2.equals("PA")) {
				pan = numberPart2;

			} else if (typePart3 != null && typePart3.equals("PA")) {
				pan = numberPart3;

			} else if (typePart4 != null && typePart4.equals("PA")) {
				pan = numberPart4;

			} else if (typePart5 != null && typePart5.equals("PA")) {
				pan = numberPart5;

			}
			if (aadhar != null) {
				employeeDto.setAadharNumber(aadhar);

			}
			if (pan != null) {
				employeeDto.setPan(pan);
			}
			String empPFNumber = null;
			String empUANumber = null;
			String empESINumber = null;

			if (statuaryTypePart1 != null && statuaryTypePart1.equals("PF")) {
				empPFNumber = statuaryNumberPart1;
			} else if (statuaryTypePart2 != null && statuaryTypePart2.equals("PF")) {
				empPFNumber = statuaryNumberPart2;
			} else if (statuaryTypePart3 != null && statuaryTypePart3.equals("PF")) {
				empPFNumber = statuaryNumberPart3;
			} else if (statuaryTypePart4 != null && statuaryTypePart4.equals("PF")) {
				empPFNumber = statuaryNumberPart4;
			} else if (statuaryTypePart5 != null && statuaryTypePart5.equals("PF")) {
				empPFNumber = statuaryNumberPart5;
			}

			if (statuaryTypePart1 != null && statuaryTypePart1.equals("UA")) {
				empUANumber = statuaryNumberPart1;

			} else if (statuaryTypePart2 != null && statuaryTypePart2.equals("UA")) {
				empUANumber = statuaryNumberPart2;

			} else if (statuaryTypePart3 != null && statuaryTypePart3.equals("UA")) {
				empUANumber = statuaryNumberPart3;

			} else if (statuaryTypePart4 != null && statuaryTypePart4.equals("UA")) {
				empUANumber = statuaryNumberPart4;

			} else if (statuaryTypePart5 != null && statuaryTypePart5.equals("UA")) {
				empUANumber = statuaryNumberPart5;
			}
			if (statuaryTypePart1 != null && statuaryTypePart1.equals("ES")) {
				empESINumber = statuaryNumberPart1;

			} else if (statuaryTypePart2 != null && statuaryTypePart2.equals("ES")) {
				empESINumber = statuaryNumberPart2;

			} else if (statuaryTypePart3 != null && statuaryTypePart3.equals("ES")) {
				empESINumber = statuaryNumberPart3;

			} else if (statuaryTypePart4 != null && statuaryTypePart4.equals("ES")) {
				empESINumber = statuaryNumberPart4;

			} else if (statuaryTypePart5 != null && statuaryTypePart5.equals("ES")) {
				empESINumber = statuaryNumberPart5;
			}

			if (empPFNumber != null) {
				employeeDto.setEmpPFNumber(empPFNumber);
			}
			if (empUANumber != null) {
				employeeDto.setUanNumber(empUANumber);
			}
			if (empESINumber != null) {
				employeeDto.setEmpESINumber(empESINumber);
			}
			String basicSalary = null;
			String dearnessAllowance = null;
			String houseRentAllowance = null;
			String conveyanceAllowance = null;
			String specialAllowance = null;
			String medicalAllowance = null;
			String advanceBonus = null;
			String performanceLinkedIncome = null;
			String companyBenefits = null;
			String leaveTravelAllowance = null;
			String uniformAllowance = null;

			if (payHeadIdPart1 != null && payHeadIdPart1.equals("1")) {
				basicSalary = payHeadNumberPart1;
			} else if (payHeadIdPart2 != null && payHeadIdPart2.equals("1")) {
				basicSalary = payHeadNumberPart2;
			} else if (payHeadIdPart3 != null && payHeadIdPart3.equals("1")) {
				basicSalary = payHeadNumberPart3;
			} else if (payHeadIdPart4 != null && payHeadIdPart4.equals("1")) {
				basicSalary = payHeadNumberPart4;
			} else if (payHeadIdPart5 != null && payHeadIdPart5.equals("1")) {
				basicSalary = payHeadNumberPart5;
			} else if (payHeadIdPart6 != null && payHeadIdPart6.equals("1")) {
				basicSalary = payHeadNumberPart6;
			} else if (payHeadIdPart7 != null && payHeadIdPart7.equals("1")) {
				basicSalary = payHeadNumberPart7;
			} else if (payHeadIdPart8 != null && payHeadIdPart8.equals("1")) {
				basicSalary = payHeadNumberPart8;
			} else if (payHeadIdPart9 != null && payHeadIdPart9.equals("1")) {
				basicSalary = payHeadNumberPart9;
			} else if (payHeadIdPart10 != null && payHeadIdPart10.equals("1")) {
				basicSalary = payHeadNumberPart10;
			} else if (payHeadIdPart11 != null && payHeadIdPart11.equals("1")) {
				basicSalary = payHeadNumberPart11;
			}

			if (payHeadIdPart1 != null && payHeadIdPart1.equals("2")) {
				dearnessAllowance = payHeadNumberPart1;
			} else if (payHeadIdPart2 != null && payHeadIdPart2.equals("2")) {
				dearnessAllowance = payHeadNumberPart2;
			} else if (payHeadIdPart3 != null && payHeadIdPart3.equals("2")) {
				dearnessAllowance = payHeadNumberPart3;
			} else if (payHeadIdPart4 != null && payHeadIdPart4.equals("2")) {
				dearnessAllowance = payHeadNumberPart4;
			} else if (payHeadIdPart5 != null && payHeadIdPart5.equals("2")) {
				dearnessAllowance = payHeadNumberPart5;
			} else if (payHeadIdPart6 != null && payHeadIdPart6.equals("2")) {
				dearnessAllowance = payHeadNumberPart6;
			} else if (payHeadIdPart7 != null && payHeadIdPart7.equals("2")) {
				dearnessAllowance = payHeadNumberPart7;
			} else if (payHeadIdPart8 != null && payHeadIdPart8.equals("2")) {
				dearnessAllowance = payHeadNumberPart8;
			} else if (payHeadIdPart9 != null && payHeadIdPart9.equals("2")) {
				dearnessAllowance = payHeadNumberPart9;
			} else if (payHeadIdPart10 != null && payHeadIdPart10.equals("2")) {
				dearnessAllowance = payHeadNumberPart10;
			} else if (payHeadIdPart11 != null && payHeadIdPart11.equals("2")) {
				dearnessAllowance = payHeadNumberPart11;
			}

			if (payHeadIdPart1 != null && payHeadIdPart1.equals("3")) {
				houseRentAllowance = payHeadNumberPart1;
			} else if (payHeadIdPart2 != null && payHeadIdPart2.equals("3")) {
				houseRentAllowance = payHeadNumberPart2;
			} else if (payHeadIdPart3 != null && payHeadIdPart3.equals("3")) {
				houseRentAllowance = payHeadNumberPart3;
			} else if (payHeadIdPart4 != null && payHeadIdPart4.equals("3")) {
				houseRentAllowance = payHeadNumberPart4;
			} else if (payHeadIdPart5 != null && payHeadIdPart5.equals("3")) {
				houseRentAllowance = payHeadNumberPart5;
			} else if (payHeadIdPart6 != null && payHeadIdPart6.equals("3")) {
				houseRentAllowance = payHeadNumberPart6;
			} else if (payHeadIdPart7 != null && payHeadIdPart7.equals("3")) {
				houseRentAllowance = payHeadNumberPart7;
			} else if (payHeadIdPart8 != null && payHeadIdPart8.equals("3")) {
				houseRentAllowance = payHeadNumberPart8;
			} else if (payHeadIdPart9 != null && payHeadIdPart9.equals("3")) {
				houseRentAllowance = payHeadNumberPart9;
			} else if (payHeadIdPart10 != null && payHeadIdPart10.equals("3")) {
				houseRentAllowance = payHeadNumberPart10;
			} else if (payHeadIdPart11 != null && payHeadIdPart11.equals("3")) {
				houseRentAllowance = payHeadNumberPart11;
			}

			if (payHeadIdPart1 != null && payHeadIdPart1.equals("4")) {
				conveyanceAllowance = payHeadNumberPart1;
			} else if (payHeadIdPart2 != null && payHeadIdPart2.equals("4")) {
				conveyanceAllowance = payHeadNumberPart2;
			} else if (payHeadIdPart3 != null && payHeadIdPart3.equals("4")) {
				conveyanceAllowance = payHeadNumberPart3;
			} else if (payHeadIdPart4 != null && payHeadIdPart4.equals("4")) {
				conveyanceAllowance = payHeadNumberPart4;
			} else if (payHeadIdPart5 != null && payHeadIdPart5.equals("4")) {
				conveyanceAllowance = payHeadNumberPart5;
			} else if (payHeadIdPart6 != null && payHeadIdPart6.equals("4")) {
				conveyanceAllowance = payHeadNumberPart6;
			} else if (payHeadIdPart7 != null && payHeadIdPart7.equals("4")) {
				conveyanceAllowance = payHeadNumberPart7;
			} else if (payHeadIdPart8 != null && payHeadIdPart8.equals("4")) {
				conveyanceAllowance = payHeadNumberPart8;
			} else if (payHeadIdPart9 != null && payHeadIdPart9.equals("4")) {
				conveyanceAllowance = payHeadNumberPart9;
			} else if (payHeadIdPart10 != null && payHeadIdPart10.equals("4")) {
				conveyanceAllowance = payHeadNumberPart10;
			} else if (payHeadIdPart11 != null && payHeadIdPart11.equals("4")) {
				conveyanceAllowance = payHeadNumberPart11;
			}

			if (payHeadIdPart1 != null && payHeadIdPart1.equals("5")) {
				specialAllowance = payHeadNumberPart1;
			} else if (payHeadIdPart2 != null && payHeadIdPart2.equals("5")) {
				specialAllowance = payHeadNumberPart2;
			} else if (payHeadIdPart3 != null && payHeadIdPart3.equals("5")) {
				specialAllowance = payHeadNumberPart3;
			} else if (payHeadIdPart4 != null && payHeadIdPart4.equals("5")) {
				specialAllowance = payHeadNumberPart4;
			} else if (payHeadIdPart5 != null && payHeadIdPart5.equals("5")) {
				specialAllowance = payHeadNumberPart5;
			} else if (payHeadIdPart6 != null && payHeadIdPart6.equals("5")) {
				specialAllowance = payHeadNumberPart6;
			} else if (payHeadIdPart7 != null && payHeadIdPart7.equals("5")) {
				specialAllowance = payHeadNumberPart7;
			} else if (payHeadIdPart8 != null && payHeadIdPart8.equals("5")) {
				specialAllowance = payHeadNumberPart8;
			} else if (payHeadIdPart9 != null && payHeadIdPart9.equals("5")) {
				specialAllowance = payHeadNumberPart9;
			} else if (payHeadIdPart10 != null && payHeadIdPart10.equals("5")) {
				specialAllowance = payHeadNumberPart10;
			} else if (payHeadIdPart11 != null && payHeadIdPart11.equals("5")) {
				specialAllowance = payHeadNumberPart11;
			}
			if (payHeadIdPart1 != null && payHeadIdPart1.equals("6")) {
				medicalAllowance = payHeadNumberPart1;
			} else if (payHeadIdPart2 != null && payHeadIdPart2.equals("6")) {
				medicalAllowance = payHeadNumberPart2;
			} else if (payHeadIdPart3 != null && payHeadIdPart3.equals("6")) {
				medicalAllowance = payHeadNumberPart3;
			} else if (payHeadIdPart4 != null && payHeadIdPart4.equals("6")) {
				medicalAllowance = payHeadNumberPart4;
			} else if (payHeadIdPart5 != null && payHeadIdPart5.equals("6")) {
				medicalAllowance = payHeadNumberPart5;
			} else if (payHeadIdPart6 != null && payHeadIdPart6.equals("6")) {
				medicalAllowance = payHeadNumberPart6;
			} else if (payHeadIdPart7 != null && payHeadIdPart7.equals("6")) {
				medicalAllowance = payHeadNumberPart7;
			} else if (payHeadIdPart8 != null && payHeadIdPart8.equals("6")) {
				medicalAllowance = payHeadNumberPart8;
			} else if (payHeadIdPart9 != null && payHeadIdPart9.equals("6")) {
				medicalAllowance = payHeadNumberPart9;
			} else if (payHeadIdPart10 != null && payHeadIdPart10.equals("6")) {
				medicalAllowance = payHeadNumberPart10;
			} else if (payHeadIdPart11 != null && payHeadIdPart11.equals("6")) {
				medicalAllowance = payHeadNumberPart11;
			}
			if (payHeadIdPart1 != null && payHeadIdPart1.equals("7")) {
				advanceBonus = payHeadNumberPart1;
			} else if (payHeadIdPart2 != null && payHeadIdPart2.equals("7")) {
				advanceBonus = payHeadNumberPart2;
			} else if (payHeadIdPart3 != null && payHeadIdPart3.equals("7")) {
				advanceBonus = payHeadNumberPart3;
			} else if (payHeadIdPart4 != null && payHeadIdPart4.equals("7")) {
				advanceBonus = payHeadNumberPart4;
			} else if (payHeadIdPart5 != null && payHeadIdPart5.equals("7")) {
				advanceBonus = payHeadNumberPart5;
			} else if (payHeadIdPart6 != null && payHeadIdPart6.equals("7")) {
				advanceBonus = payHeadNumberPart6;
			} else if (payHeadIdPart7 != null && payHeadIdPart7.equals("7")) {
				advanceBonus = payHeadNumberPart7;
			} else if (payHeadIdPart8 != null && payHeadIdPart8.equals("7")) {
				advanceBonus = payHeadNumberPart8;
			} else if (payHeadIdPart9 != null && payHeadIdPart9.equals("7")) {
				advanceBonus = payHeadNumberPart9;
			} else if (payHeadIdPart10 != null && payHeadIdPart10.equals("7")) {
				advanceBonus = payHeadNumberPart10;
			} else if (payHeadIdPart11 != null && payHeadIdPart11.equals("7")) {
				advanceBonus = payHeadNumberPart11;
			}

			if (payHeadIdPart1 != null && payHeadIdPart1.equals("8")) {
				performanceLinkedIncome = payHeadNumberPart1;
			} else if (payHeadIdPart2 != null && payHeadIdPart2.equals("8")) {
				performanceLinkedIncome = payHeadNumberPart2;
			} else if (payHeadIdPart3 != null && payHeadIdPart3.equals("8")) {
				performanceLinkedIncome = payHeadNumberPart3;
			} else if (payHeadIdPart4 != null && payHeadIdPart4.equals("8")) {
				performanceLinkedIncome = payHeadNumberPart4;
			} else if (payHeadIdPart5 != null && payHeadIdPart5.equals("8")) {
				performanceLinkedIncome = payHeadNumberPart5;
			} else if (payHeadIdPart6 != null && payHeadIdPart6.equals("8")) {
				performanceLinkedIncome = payHeadNumberPart6;
			} else if (payHeadIdPart7 != null && payHeadIdPart7.equals("8")) {
				performanceLinkedIncome = payHeadNumberPart7;
			} else if (payHeadIdPart8 != null && payHeadIdPart8.equals("8")) {
				performanceLinkedIncome = payHeadNumberPart8;
			} else if (payHeadIdPart9 != null && payHeadIdPart9.equals("8")) {
				performanceLinkedIncome = payHeadNumberPart9;
			} else if (payHeadIdPart10 != null && payHeadIdPart10.equals("8")) {
				performanceLinkedIncome = payHeadNumberPart10;
			} else if (payHeadIdPart11 != null && payHeadIdPart11.equals("8")) {
				performanceLinkedIncome = payHeadNumberPart11;
			}
			if (payHeadIdPart1 != null && payHeadIdPart1.equals("9")) {
				companyBenefits = payHeadNumberPart1;
			} else if (payHeadIdPart2 != null && payHeadIdPart2.equals("9")) {
				companyBenefits = payHeadNumberPart2;
			} else if (payHeadIdPart3 != null && payHeadIdPart3.equals("9")) {
				companyBenefits = payHeadNumberPart3;
			} else if (payHeadIdPart4 != null && payHeadIdPart4.equals("9")) {
				companyBenefits = payHeadNumberPart4;
			} else if (payHeadIdPart5 != null && payHeadIdPart5.equals("9")) {
				companyBenefits = payHeadNumberPart5;
			} else if (payHeadIdPart6 != null && payHeadIdPart6.equals("9")) {
				companyBenefits = payHeadNumberPart6;
			} else if (payHeadIdPart7 != null && payHeadIdPart7.equals("9")) {
				companyBenefits = payHeadNumberPart7;
			} else if (payHeadIdPart8 != null && payHeadIdPart8.equals("9")) {
				companyBenefits = payHeadNumberPart8;
			} else if (payHeadIdPart9 != null && payHeadIdPart9.equals("9")) {
				companyBenefits = payHeadNumberPart9;
			} else if (payHeadIdPart10 != null && payHeadIdPart10.equals("9")) {
				companyBenefits = payHeadNumberPart10;
			} else if (payHeadIdPart11 != null && payHeadIdPart11.equals("9")) {
				companyBenefits = payHeadNumberPart11;
			}

			if (payHeadIdPart1 != null && payHeadIdPart1.equals("10")) {
				leaveTravelAllowance = payHeadNumberPart1;
			} else if (payHeadIdPart2 != null && payHeadIdPart2.equals("10")) {
				leaveTravelAllowance = payHeadNumberPart2;
			} else if (payHeadIdPart3 != null && payHeadIdPart3.equals("10")) {
				leaveTravelAllowance = payHeadNumberPart3;
			} else if (payHeadIdPart4 != null && payHeadIdPart4.equals("10")) {
				leaveTravelAllowance = payHeadNumberPart4;
			} else if (payHeadIdPart5 != null && payHeadIdPart5.equals("10")) {
				leaveTravelAllowance = payHeadNumberPart5;
			} else if (payHeadIdPart6 != null && payHeadIdPart6.equals("10")) {
				leaveTravelAllowance = payHeadNumberPart6;
			} else if (payHeadIdPart7 != null && payHeadIdPart7.equals("10")) {
				leaveTravelAllowance = payHeadNumberPart7;
			} else if (payHeadIdPart8 != null && payHeadIdPart8.equals("10")) {
				leaveTravelAllowance = payHeadNumberPart8;
			} else if (payHeadIdPart9 != null && payHeadIdPart9.equals("10")) {
				leaveTravelAllowance = payHeadNumberPart9;
			} else if (payHeadIdPart10 != null && payHeadIdPart10.equals("10")) {
				leaveTravelAllowance = payHeadNumberPart10;
			} else if (payHeadIdPart11 != null && payHeadIdPart11.equals("10")) {
				leaveTravelAllowance = payHeadNumberPart11;
			}
			if (payHeadIdPart1 != null && payHeadIdPart1.equals("11")) {
				uniformAllowance = payHeadNumberPart1;
			} else if (payHeadIdPart2 != null && payHeadIdPart2.equals("11")) {
				uniformAllowance = payHeadNumberPart2;
			} else if (payHeadIdPart3 != null && payHeadIdPart3.equals("11")) {
				uniformAllowance = payHeadNumberPart3;
			} else if (payHeadIdPart4 != null && payHeadIdPart4.equals("11")) {
				uniformAllowance = payHeadNumberPart4;
			} else if (payHeadIdPart5 != null && payHeadIdPart5.equals("11")) {
				uniformAllowance = payHeadNumberPart5;
			} else if (payHeadIdPart6 != null && payHeadIdPart6.equals("11")) {
				uniformAllowance = payHeadNumberPart6;
			} else if (payHeadIdPart7 != null && payHeadIdPart7.equals("11")) {
				uniformAllowance = payHeadNumberPart7;
			} else if (payHeadIdPart8 != null && payHeadIdPart8.equals("11")) {
				uniformAllowance = payHeadNumberPart8;
			} else if (payHeadIdPart9 != null && payHeadIdPart9.equals("11")) {
				uniformAllowance = payHeadNumberPart9;
			} else if (payHeadIdPart10 != null && payHeadIdPart10.equals("11")) {
				uniformAllowance = payHeadNumberPart10;
			} else if (payHeadIdPart11 != null && payHeadIdPart11.equals("11")) {
				uniformAllowance = payHeadNumberPart11;
			}
			employeeDto.setEmployeeCode(employeeCode);
			employeeDto.setFirstName(firstName);
			employeeDto.setMiddleName(middleName);
			employeeDto.setLastName(lastName);
			employeeDto.setDateOfBirth(dob);
			// employeeDto.setGender(gender);
			employeeDto.setGender(
					DropDownCache.getInstance().getDropDownValue(DropDownEnum.Gender.getDropDownName(), gender));

			employeeDto.setDateOfJoining(dateOfJoining);
			employeeDto.setMaritalStatus(maritalStatus);
			employeeDto.setMaritalStatus(DropDownCache.getInstance()
					.getDropDownValue(DropDownEnum.MaritalStatus.getDropDownName(), maritalStatus));

			employeeDto.setStateName(empStateName);
			employeeDto.setCityName(empCityName);
			employeeDto.setBloodGroup(bloodGroup);
			employeeDto.setProbationDays(probationDays);

			// employeeDto.setEmpType(empType);
			employeeDto.setEmpType(DropDownCache.getInstance()
					.getDropDownValue(DropDownEnum.EmploymentType.getDropDownName(), empType));

			employeeDto.setDepartmentName(departmentName);
			employeeDto.setDesignationName(designationName);
			employeeDto.setContractOverDate(contractOverDate);
			employeeDto.setReferenceName(referenceName);

			employeeDto.setDateOfJoining(dateOfJoining);
			addressDto.setAddressText(addressText);
			addressDto.setLandmark(landmark);
			addressDto.setCountryName(empAddrsCountryName);
			addressDto.setStateName(empAddrsStateName);
			addressDto.setCityName(empAddrsCityName);
			addressDto.setPincode(pincode);
			addressDto.setMobile(mobile);
			addressDto.setTelephone(telephone);
			addressDto.setEmailId(emailId);
			employeeDto.setAddress1(addressDto);
			employeeDto.setAccountNumber(accountNumber);
			// employeeDto.setBankId(bankId);
			employeeDto.setBankId(
					DropDownCache.getInstance().getDropDownValue(DropDownEnum.BankName.getDropDownName(), bankId));

			employeeDto.setIfscCode(ifscCode);
			// employeeDto.setAccountType(accountType);
			employeeDto.setAccountType(DropDownCache.getInstance()
					.getDropDownValue(DropDownEnum.AccountType.getDropDownName(), accountType));

			employeeDto.setBasicSalary(basicSalary);
			employeeDto.setDearnessAllowance(dearnessAllowance);
			employeeDto.setHouseRentAllowance(houseRentAllowance);
			employeeDto.setConveyanceAllowance(conveyanceAllowance);
			employeeDto.setSpecialAllowance(specialAllowance);
			employeeDto.setMedicalAllowance(medicalAllowance);
			employeeDto.setAdvanceBonus(advanceBonus);
			employeeDto.setPerformanceLinkedIncome(performanceLinkedIncome);
			employeeDto.setCompanyBenefits(companyBenefits);
			employeeDto.setLeaveTravelAllowance(leaveTravelAllowance);
			employeeDto.setUniformAllowance(uniformAllowance);
			employeeDtoList.add(employeeDto);

		}

		return employeeDtoList;
	}

	public List<EmployeeDTO> databaseModelToUIDtoList(List<Object[]> objectEmployeeList) {
		List<EmployeeDTO> employeeDtoList = new ArrayList<EmployeeDTO>();

		for (Object[] report : objectEmployeeList) {
			EmployeeDTO employeeDto = new EmployeeDTO();
			AddressDTO addressDto = new AddressDTO();

			AddressDTO addressDto2 = new AddressDTO();
			AddressDTO addressDto3 = new AddressDTO();

			String employeeCode = report[0] != null ? (String) report[0] : null;
			String firstName = report[1] != null ? (String) report[1] : null;
			String middleName = report[2] != null ? (String) report[2] : null;
			String lastName = report[3] != null ? (String) report[3] : null;
			String adharNumber = report[4] != null ? (String) report[4] : null;
			String contactNo = report[5] != null ? (String) report[5] : null;
			String emailId = report[6] != null ? (String) report[6] : null;
			String jobLocation = report[7] != null ? (String) report[7] : null;
			String shiftName = report[8] != null ? (String) report[8] : null;
			String weeklyOffPattern = report[9] != null ? (String) report[9] : null;
			String attendanceScheme = report[10] != null ? (String) report[10] : null;
			String leaveScheme = report[11] != null ? (String) report[11] : null;
			String departmentName = report[12] != null ? (String) report[12] : null;
			String designationName = report[13] != null ? (String) report[13] : null;
			String branchName = report[14] != null ? (String) report[14] : null;
			String reportingTo = report[15] != null ? (String) report[15] : null;
			String gradesName = report[16] != null ? (String) report[16] : null;
			Integer probationDays = report[17] != null ? (Integer) report[17] : null;
			Integer noticePeriodDays = report[18] != null ? (Integer) report[18] : null;
			String empType = report[19] != null ? (String) report[19] : null;
			Date dateOfJoining = report[20] != null ? (Date) report[20] : null;
			Date contractStartDate = report[21] != null ? (Date) report[21] : null;
			Date contractOverDate = report[22] != null ? (Date) report[22] : null;
			String timeContract = report[23] != null ? (String) report[23] : null;
			String officialMailId = report[24] != null ? (String) report[24] : null;
			String roleDescription = report[25] != null ? (String) report[25] : null;
			Date dob = report[26] != null ? (Date) report[26] : null;
			String gender = report[27] != null ? (String) report[27] : null;
			String bloodGroup = report[28] != null ? (String) report[28] : null;
			String alternateNumber = report[29] != null ? (String) report[29] : null;
			String maritalStatus = report[30] != null ? (String) report[30] : null;
			Date anniversaryDate = report[31] != null ? (Date) report[31] : null;
			String addressText = report[32] != null ? (String) report[32] : null;
			String landmark = report[33] != null ? (String) report[33] : null;
			String pincode = report[34] != null ? (String) report[34] : null;
			String empAddrsCountryName = report[35] != null ? (String) report[35] : null;
			String empAddrsStateName = report[36] != null ? (String) report[36] : null;
			String empAddrsCityName = report[37] != null ? (String) report[37] : null;
			String presentaddressText = report[38] != null ? (String) report[38] : null;
			String presentlandmark = report[39] != null ? (String) report[39] : null;
			String presentpincode = report[40] != null ? (String) report[40] : null;
			String presentempAddrsCountryName = report[41] != null ? (String) report[41] : null;
			String presentempAddrsStateName = report[42] != null ? (String) report[42] : null;
			String presentempAddrsCityName = report[43] != null ? (String) report[43] : null;
			String referenceName = report[44] != null ? (String) report[44] : null;
			String referenceMobile = report[45] != null ? (String) report[45] : null;
			String refAddressTextLandmark = report[46] != null ? (String) report[46] : null;
			String referenceEmailId = report[47] != null ? (String) report[47] : null;
			String referencepincode = report[48] != null ? (String) report[48] : null;
			String referenceEmpAddrsCountryName = report[49] != null ? (String) report[49] : null;
			String referenceEmpAddrsStateName = report[50] != null ? (String) report[50] : null;
			// Integer referenceEmpAddrsStateId = report[44] != null ? (Integer) report[44]
			// : null;
			String referenceEmpAddrsCityName = report[51] != null ? (String) report[51] : null;
			// Integer referenceEmpAddrsCityId = report[46] != null ? (Integer) report[46] :
			// null;
			String bankId = report[52] != null ? (String) report[52] : null;
			String accountNumber = report[53] != null ? (String) report[53] : null;
			String bankBranch = report[54] != null ? (String) report[54] : null;
			String ifscCode = report[55] != null ? (String) report[55] : null;

			employeeDto.setEmployeeCode(employeeCode);
			employeeDto.setFirstName(firstName);
			employeeDto.setMiddleName(middleName);
			employeeDto.setLastName(lastName);
			employeeDto.setDateOfBirth(dob);
			// employeeDto.setGender(gender);
			employeeDto.setGender(
					DropDownCache.getInstance().getDropDownValue(DropDownEnum.Gender.getDropDownName(), gender));

			employeeDto.setDateOfJoining(dateOfJoining);
			employeeDto.setMaritalStatus(maritalStatus);
			employeeDto.setMaritalStatus(DropDownCache.getInstance()
					.getDropDownValue(DropDownEnum.MaritalStatus.getDropDownName(), maritalStatus));

			employeeDto.setStateName(empAddrsStateName);
			employeeDto.setCityName(empAddrsCityName);
			employeeDto.setBloodGroup(bloodGroup);

			if (probationDays != null)
				employeeDto.setProbationDays(Long.valueOf(probationDays));

			if (roleDescription != null)
				 employeeDto.setRoleDescription(roleDescription);

				// employeeDto.setEmpType(empType);
				// employeeDto.setEmpType(DropDownCache.getInstance().getDropDownValue(DropDownEnum.EmploymentType.getDropDownName(),empType));

				employeeDto.setAadharNumber(adharNumber);
			employeeDto.setDateOfJoining(dateOfJoining);
			employeeDto.setAnniversaryDate(anniversaryDate);
			employeeDto.setTimeContract(timeContract);
			employeeDto.setContractStartDate(contractStartDate);
			employeeDto.setBankBranch(bankBranch);
			employeeDto.setContactNo(contactNo);
			employeeDto.setWeeklyOffPattern(weeklyOffPattern);
			employeeDto.setAttendanceSchemeName(attendanceScheme);
			employeeDto.setBranchName(branchName);
			employeeDto.setLeaveSchemeName(leaveScheme);
			employeeDto.setShiftName(shiftName);
			employeeDto.setJobLocation(jobLocation);
			if (noticePeriodDays != null)
				employeeDto.setNoticePeriodDays(Long.valueOf(noticePeriodDays));

			// if(referenceEmpAddrsCityId!=null)
			// employeeDto.setReferenceEmpAddrsCityId(Long.valueOf(referenceEmpAddrsCityId));
			// if(referenceEmpAddrsStateId!=null)
			// employeeDto.setReferenceEmpAddrsStateId(Long.valueOf(referenceEmpAddrsStateId));

			if (gradesName != null)
				employeeDto.setGradesName(gradesName);
			if (probationDays != null)
				employeeDto.setProbationDays(Long.valueOf(probationDays));
			if (alternateNumber != null)
				employeeDto.setAlternateNumber(alternateNumber);

			employeeDto.setDepartmentName(departmentName);
			employeeDto.setDesignationName(designationName);
			employeeDto.setContractOverDate(contractOverDate);
			employeeDto.setOfficialEmailId(officialMailId);
			employeeDto.setReferenceName(referenceName);
			// referenceMobile
			employeeDto.setReferenceMobile(referenceMobile);
			employeeDto.setDateOfJoining(dateOfJoining);
			employeeDto.setEmailId(emailId);
			addressDto.setAddressText(addressText);
			addressDto.setLandmark(landmark);
			addressDto.setCountryName(empAddrsCountryName);
			addressDto.setStateName(empAddrsStateName);
			addressDto.setCityName(empAddrsCityName);
			addressDto.setPincode(pincode);
			addressDto.setMobile(contactNo);
			// addressDto.setTelephone(telephone);
			addressDto.setEmailId(emailId);
			employeeDto.setAddress1(addressDto);
			employeeDto.setReportingEmployeeFirstName(reportingTo);
			addressDto2.setAddressText(presentaddressText);
			addressDto2.setLandmark(presentlandmark);
			addressDto2.setCountryName(presentempAddrsCountryName);
			addressDto2.setStateName(presentempAddrsStateName);
			addressDto2.setCityName(presentempAddrsCityName);
			addressDto2.setPincode(presentpincode);

			// if (referenceEmpAddrsCityId != null)
			// employeeDto.setReferenceEmpAddrsCityId(Long.valueOf(referenceEmpAddrsCityId));
			// if (referenceEmpAddrsStateId != null)
			// employeeDto.setReferenceEmpAddrsStateId(Long.valueOf(referenceEmpAddrsStateId));
			if (referenceName != null)
				employeeDto.setReferenceName(referenceName);
			if (referenceMobile != null)
				employeeDto.setReferenceMobile(referenceMobile);
			if (referenceEmailId != null)
				employeeDto.setReferenceEmailId(referenceEmailId);
			if (refAddressTextLandmark != null)
				addressDto3.setAddressText(refAddressTextLandmark);
			if (referenceEmpAddrsCountryName != null)
				addressDto3.setCountryName(referenceEmpAddrsCountryName);
			if (referenceEmpAddrsStateName != null)
				addressDto3.setStateName(referenceEmpAddrsStateName);
			if (referenceEmpAddrsCityName != null)
				addressDto3.setCityName(referenceEmpAddrsCityName);
			if (referencepincode != null)
				addressDto3.setPincode(referencepincode);

			employeeDto.setAddress2(addressDto2);

			employeeDto.setAddress3(addressDto3);

			employeeDto.setAccountNumber(accountNumber);
			employeeDto.setBankId(bankId);
			employeeDto.setBankId(
					DropDownCache.getInstance().getDropDownValue(DropDownEnum.BankName.getDropDownName(), bankId));

			employeeDto.setIfscCode(ifscCode);
			employeeDto.setEmpType(empType);

			// employeeDto.setAccountType(accountType);
			// employeeDto.setAccountType(DropDownCache.getInstance().getDropDownValue(DropDownEnum.AccountType.getDropDownName(),
			// accountType));

			// employeeDto.setBasicSalary(basicSalary);
			// employeeDto.setDearnessAllowance(dearnessAllowance);
			// employeeDto.setHouseRentAllowance(houseRentAllowance);
			// employeeDto.setConveyanceAllowance(conveyanceAllowance);
			// employeeDto.setSpecialAllowance(specialAllowance);
			// employeeDto.setMedicalAllowance(medicalAllowance);
			// employeeDto.setAdvanceBonus(advanceBonus);
			// employeeDto.setPerformanceLinkedIncome(performanceLinkedIncome);
			// employeeDto.setCompanyBenefits(companyBenefits);
			// employeeDto.setLeaveTravelAllowance(leaveTravelAllowance);
			// employeeDto.setUniformAllowance(uniformAllowance);
			employeeDtoList.add(employeeDto);

		}
		return employeeDtoList;
	}

	public List<EmployeeDTO> databaseListToUIDtoList(List<Object[]> employeeList) {
		List<EmployeeDTO> employeeDtoList = new ArrayList<EmployeeDTO>();

		for (Object[] report : employeeList) {
			EmployeeDTO employeeDto = new EmployeeDTO();
			AddressDTO addressDto = new AddressDTO();

			AddressDTO addressDto2 = new AddressDTO();
			AddressDTO addressDto3 = new AddressDTO();

			String employeeCode = report[0] != null ? (String) report[0] : null;
			String firstName = report[1] != null ? (String) report[1] : null;
			String middleName = report[2] != null ? (String) report[2] : null;
			String lastName = report[3] != null ? (String) report[3] : null;

			String adharNumber = report[4] != null ? (String) report[4] : null;
			String contactNo = report[5] != null ? (String) report[5] : null;
			String emailId = report[6] != null ? (String) report[6] : null;
			String jobLocation = report[7] != null ? (String) report[7] : null;
			String shiftName = report[8] != null ? (String) report[8] : null;
			String weeklyOffPattern = report[9] != null ? (String) report[9] : null;
			String departmentName = report[10] != null ? (String) report[10] : null;
			String designationName = report[11] != null ? (String) report[11] : null;

			String gradesName = report[12] != null ? (String) report[12] : null;
			Integer probationDays = report[13] != null ? (Integer) report[13] : null;

			Integer noticePeriodDays = report[14] != null ? (Integer) report[14] : null;

			Date dateOfJoining = report[15] != null ? (Date) report[15] : null;

			Date contractStartDate = report[16] != null ? (Date) report[16] : null;
			Date contractOverDate = report[17] != null ? (Date) report[17] : null;
			String timeContract = report[18] != null ? (String) report[18] : null;
			Date dob = report[19] != null ? (Date) report[19] : null;
			String gender = report[20] != null ? (String) report[20] : null;
			String bloodGroup = report[21] != null ? (String) report[21] : null;
			String alternateNumber = report[22] != null ? (String) report[22] : null;
			String maritalStatus = report[23] != null ? (String) report[23] : null;
			Date anniversaryDate = report[24] != null ? (Date) report[24] : null;

			String addressText = report[25] != null ? (String) report[25] : null;
			String landmark = report[26] != null ? (String) report[26] : null;
			String pincode = report[27] != null ? (String) report[27] : null;
			String empAddrsCountryName = report[28] != null ? (String) report[28] : null;
			String empAddrsStateName = report[29] != null ? (String) report[29] : null;
			String empAddrsCityName = report[30] != null ? (String) report[30] : null;

			String presentaddressText = report[31] != null ? (String) report[31] : null;
			String presentlandmark = report[32] != null ? (String) report[32] : null;
			String presentpincode = report[33] != null ? (String) report[33] : null;
			String presentempAddrsCountryName = report[34] != null ? (String) report[34] : null;
			String presentempAddrsStateName = report[35] != null ? (String) report[35] : null;
			String presentempAddrsCityName = report[36] != null ? (String) report[36] : null;

			String referenceName = report[37] != null ? (String) report[37] : null;
			String referenceMobile = report[38] != null ? (String) report[38] : null;

			String refAddressTextLandmark = report[39] != null ? (String) report[39] : null;
			String referenceEmailId = report[40] != null ? (String) report[40] : null;
			String referencepincode = report[41] != null ? (String) report[41] : null;
			String referenceEmpAddrsCountryName = report[42] != null ? (String) report[42] : null;
			String referenceEmpAddrsStateName = report[43] != null ? (String) report[43] : null;
			// Integer referenceEmpAddrsStateId = report[44] != null ? (Integer) report[44]
			// : null;
			String referenceEmpAddrsCityName = report[44] != null ? (String) report[44] : null;
			// Integer referenceEmpAddrsCityId = report[46] != null ? (Integer) report[46] :
			// null;
			String bankId = report[45] != null ? (String) report[45] : null;
			String accountNumber = report[46] != null ? (String) report[46] : null;
			String bankBranch = report[47] != null ? (String) report[47] : null;
			String ifscCode = report[48] != null ? (String) report[48] : null;

			String reportingTo = report[49] != null ? (String) report[49] : null;

			String empType = report[50] != null ? (String) report[50] : null;

			String systemRole = report[51] != null ? (String) report[51] : null;
			Date endDate = report[52] != null ? (Date) report[52] : null;

			employeeDto.setEmployeeCode(employeeCode);
			employeeDto.setFirstName(firstName);
			employeeDto.setMiddleName(middleName);
			employeeDto.setLastName(lastName);
			employeeDto.setDateOfBirth(dob);
			// employeeDto.setGender(gender);
			employeeDto.setGender(
					DropDownCache.getInstance().getDropDownValue(DropDownEnum.Gender.getDropDownName(), gender));

			employeeDto.setDateOfJoining(dateOfJoining);
			employeeDto.setEndDate(endDate);
			employeeDto.setMaritalStatus(maritalStatus);
			employeeDto.setMaritalStatus(DropDownCache.getInstance()
					.getDropDownValue(DropDownEnum.MaritalStatus.getDropDownName(), maritalStatus));

			employeeDto.setStateName(empAddrsStateName);
			employeeDto.setCityName(empAddrsCityName);
			employeeDto.setBloodGroup(bloodGroup);

			if (probationDays != null)
				employeeDto.setProbationDays(Long.valueOf(probationDays));

			if (systemRole != null)
				// employeeDto.setSystemRole(systemRole);

				employeeDto.setAadharNumber(adharNumber);
			employeeDto.setDateOfJoining(dateOfJoining);
			employeeDto.setAnniversaryDate(anniversaryDate);
			employeeDto.setTimeContract(timeContract);
			employeeDto.setContractStartDate(contractStartDate);
			employeeDto.setBankBranch(bankBranch);
			employeeDto.setContactNo(contactNo);
			employeeDto.setWeeklyOffPattern(weeklyOffPattern);
			employeeDto.setShiftName(shiftName);
			employeeDto.setJobLocation(jobLocation);
			if (noticePeriodDays != null)
				employeeDto.setNoticePeriodDays(Long.valueOf(noticePeriodDays));

			if (gradesName != null)
				employeeDto.setGradesName(gradesName);
			if (probationDays != null)
				employeeDto.setProbationDays(Long.valueOf(probationDays));
			if (alternateNumber != null)
				employeeDto.setAlternateNumber(alternateNumber);

			employeeDto.setDepartmentName(departmentName);
			employeeDto.setDesignationName(designationName);
			employeeDto.setContractOverDate(contractOverDate);

			employeeDto.setReferenceName(referenceName);
			// referenceMobile
			employeeDto.setReferenceMobile(referenceMobile);
			employeeDto.setDateOfJoining(dateOfJoining);
			employeeDto.setEmailId(emailId);
			addressDto.setAddressText(addressText);
			addressDto.setLandmark(landmark);
			addressDto.setCountryName(empAddrsCountryName);
			addressDto.setStateName(empAddrsStateName);
			addressDto.setCityName(empAddrsCityName);
			addressDto.setPincode(pincode);
			addressDto.setMobile(contactNo);
			// addressDto.setTelephone(telephone);
			addressDto.setEmailId(emailId);
			employeeDto.setAddress1(addressDto);
			employeeDto.setReportingEmployeeFirstName(reportingTo);
			addressDto2.setAddressText(presentaddressText);
			addressDto2.setLandmark(presentlandmark);
			addressDto2.setCountryName(presentempAddrsCountryName);
			addressDto2.setStateName(presentempAddrsStateName);
			addressDto2.setCityName(presentempAddrsCityName);
			addressDto2.setPincode(presentpincode);

			if (referenceName != null)
				employeeDto.setReferenceName(referenceName);
			if (referenceMobile != null)
				employeeDto.setReferenceMobile(referenceMobile);
			if (referenceEmailId != null)
				employeeDto.setReferenceEmailId(referenceEmailId);
			if (refAddressTextLandmark != null)
				addressDto3.setAddressText(refAddressTextLandmark);
			if (referenceEmpAddrsCountryName != null)
				addressDto3.setCountryName(referenceEmpAddrsCountryName);
			if (referenceEmpAddrsStateName != null)
				addressDto3.setStateName(referenceEmpAddrsStateName);
			if (referenceEmpAddrsCityName != null)
				addressDto3.setCityName(referenceEmpAddrsCityName);
			if (referencepincode != null)
				addressDto3.setPincode(referencepincode);

			employeeDto.setAddress2(addressDto2);

			employeeDto.setAddress3(addressDto3);

			employeeDto.setAccountNumber(accountNumber);
			employeeDto.setBankId(bankId);
			employeeDto.setBankId(
					DropDownCache.getInstance().getDropDownValue(DropDownEnum.BankName.getDropDownName(), bankId));

			employeeDto.setIfscCode(ifscCode);
			employeeDto.setEmpType(empType);

			employeeDtoList.add(employeeDto);

		}
		return employeeDtoList;

	}

	public List<EmployeeIdProofDTO> objectListToIdAddressProofsReport(List<Object[]> addressIdProofsObj,
			Long employeeId) {

		List<EmployeeIdProofDTO> employeeIdAddreassDtoList = new ArrayList<EmployeeIdProofDTO>();

		if (employeeId > 0L) {
			for (Object[] report : addressIdProofsObj) {

				EmployeeIdProofDTO employeeIdProofDTO = new EmployeeIdProofDTO();

				String employeeCode = report[0] != null ? (String) report[0] : null;
				String employee = report[1] != null ? (String) report[1] : null;
				String adharCard = report[2] != null ? (String) report[2] : " ";
				String panCard = report[3] != null ? (String) report[3] : " ";
				String VoterId = report[4] != null ? (String) report[4] : " ";
				String DrivingNo = report[5] != null ? (String) report[5] : " ";
				Date dlIssue = report[6] != null ? (Date) report[6] : null;
				Date dlExpiry = report[7] != null ? (Date) report[7] : null;
				String passportNo = report[8] != null ? (String) report[8] : " ";
				Date psIssue = report[9] != null ? (Date) report[9] : null;
				Date psExpiry = report[10] != null ? (Date) report[10] : null;

				employeeIdProofDTO.setEmpCode(employeeCode);
				employeeIdProofDTO.setEmpName(employee);
				employeeIdProofDTO.setAdharCardNo(adharCard);
				employeeIdProofDTO.setPanCardNo(panCard);
				employeeIdProofDTO.setVoterIdNo(VoterId);
				employeeIdProofDTO.setDrivingLicenceNo(DrivingNo);
				employeeIdProofDTO.setdLIssueDate(dlIssue);
				employeeIdProofDTO.setdLExpiryDate(dlExpiry);
				employeeIdProofDTO.setPassportNo(passportNo);
				employeeIdProofDTO.setpSIssueDate(psIssue);
				employeeIdProofDTO.setpSExpiryDate(psExpiry);

				employeeIdAddreassDtoList.add(employeeIdProofDTO);
			}
			return employeeIdAddreassDtoList;
		} else {

			for (Object[] report : addressIdProofsObj) {

				EmployeeIdProofDTO employeeIdProofDTO = new EmployeeIdProofDTO();

				String employeeCode = report[0] != null ? (String) report[0] : null;
				String employee = report[1] != null ? (String) report[1] : null;
				String idTypeId = report[2] != null ? (String) report[2] : " ";
				String idNumber = report[3] != null ? (String) report[3] : " ";
				Date Issue = report[4] != null ? (Date) report[4] : null;
				Date Expiry = report[5] != null ? (Date) report[5] : null;

				employeeIdProofDTO.setEmpCode(employeeCode);
				employeeIdProofDTO.setEmpName(employee);
				employeeIdProofDTO.setIdTypeId(idTypeId);
				employeeIdProofDTO.setIdNumber(idNumber);
				employeeIdProofDTO.setDateIssue(Issue);
				employeeIdProofDTO.setDateExpiry(Expiry);

				employeeIdAddreassDtoList.add(employeeIdProofDTO);
			}
			return employeeIdAddreassDtoList;

		}

	}

	public List<EmployeeStatuaryDTO> objectListToAccidentalInsuranceReport(List<Object[]> reportAcInsObj) {

		List<EmployeeStatuaryDTO> reportAcInsList = new ArrayList<EmployeeStatuaryDTO>();

		for (Object[] report : reportAcInsObj) {

			EmployeeStatuaryDTO employeeStatuaryDTO = new EmployeeStatuaryDTO();

			String employeeCode = report[0] != null ? (String) report[0] : null;
			String employee = report[1] != null ? (String) report[1] : null;
			String AcInsNumber = report[2] != null ? (String) report[2] : " Under Process ";
			Date acInsIssueDate = report[3] != null ? (Date) report[3] : null;
			Date acInsExpiryDate = report[4] != null ? (Date) report[4] : null;

			employeeStatuaryDTO.setEmpAcCode(employeeCode);
			employeeStatuaryDTO.setEmpAcName(employee);
			if (AcInsNumber.isEmpty()) {
				employeeStatuaryDTO.setAccidentalInsNo(" Under Process ");
			} else {
				employeeStatuaryDTO.setAccidentalInsNo(AcInsNumber);
			}

			employeeStatuaryDTO.setAcInsIssueDate(acInsIssueDate);
			employeeStatuaryDTO.setAcInsExpiryDate(acInsExpiryDate);

			reportAcInsList.add(employeeStatuaryDTO);
		}
		return reportAcInsList;
	}

	public List<PfReportDTO> objectListToPfUANNumbersReport(List<Object[]> employeePfData, List<Long> departmentIds) {

		List<PfReportDTO> employeePfDtoList = new ArrayList<PfReportDTO>();

		for (Object[] report : employeePfData) {
			PfReportDTO pfReportDTO = new PfReportDTO();

			String employeeCode = report[0] != null ? (String) report[0] : null;
			String employee = report[1] != null ? (String) report[1] : null;
			String uanNumber = report[2] != null ? (String) report[2] : " Under Process ";
			String PfNumber = report[3] != null ? (String) report[3] : " Under Process ";
			Date dateFrom = report[4] != null ? (Date) report[4] : null;
			Date dateTo = report[5] != null ? (Date) report[5] : null;

			if (departmentIds.size() > 0) {
				String statuaryType = report[2] != null ? (String) report[2] : null;
				String statuaryNumber = report[3] != null ? (String) report[3] : " Under Process ";

				if (statuaryNumber.isEmpty()) {
					pfReportDTO.setStatuaryNumber(" Under Process ");
				} else {
					pfReportDTO.setStatuaryNumber(statuaryNumber);
				}
				pfReportDTO.setStatuaryType(statuaryType);

			}

			pfReportDTO.setEmpCode(employeeCode);
			pfReportDTO.setEmpName(employee);
			if (uanNumber.isEmpty()) {
				pfReportDTO.setUanno(" Under Process ");

			} else {
				pfReportDTO.setUanno(uanNumber);
			}
			if (PfNumber.isEmpty()) {
				pfReportDTO.setPfNo(" Under Process ");
			} else {
				pfReportDTO.setPfNo(PfNumber);
			}
			pfReportDTO.setPfIssueDate(dateFrom);
			pfReportDTO.setPfExpiryDate(dateTo);

			employeePfDtoList.add(pfReportDTO);
		}
		return employeePfDtoList;
	}

	/**
	 * @author ${Nisha Parveen}
	 *
	 */
	public List<EmployeeFamilyDTO> objectListToFamilyDetailsReport(List<Object[]> familyDetailsObj) {

		List<EmployeeFamilyDTO> familyDetailsDTOList = new ArrayList<EmployeeFamilyDTO>();

		for (Object[] report : familyDetailsObj) {
			EmployeeFamilyDTO employeeFamilyDTO = new EmployeeFamilyDTO();

			String employeeCode = report[0] != null ? (String) report[0] : " ";
			String employee = report[1] != null ? (String) report[1] : " ";
			String name = report[2] != null ? (String) report[2] : " ";
			String relation = report[3] != null ? (String) report[3] : " ";
			String education = report[4] != null ? (String) report[4] : " ";
			String occupation = report[5] != null ? (String) report[5] : " ";
			Date dob = report[6] != null ? (Date) report[6] : null;
			String contact = report[7] != null ? (String) report[7] : " ";

			employeeFamilyDTO.setEmpCode(employeeCode);
			employeeFamilyDTO.setEmpName(employee);

			employeeFamilyDTO.setName(name);

			employeeFamilyDTO.setRelation(
					DropDownCache.getInstance().getDropDownValue(DropDownEnum.Relation.getDropDownName(), relation));

			employeeFamilyDTO.setQualificationId(DropDownCache.getInstance()
					.getDropDownValue(DropDownEnum.Qualification.getDropDownName(), education));

			employeeFamilyDTO.setOccupations(DropDownCache.getInstance()
					.getDropDownValue(DropDownEnum.Occupation.getDropDownName(), occupation));

			employeeFamilyDTO.setDob(dob);

			employeeFamilyDTO.setContactMobile(contact);

			familyDetailsDTOList.add(employeeFamilyDTO);
		}
		return familyDetailsDTOList;
	}

	/**
	 * @author ${Nisha Parveen}
	 *
	 */
	public List<EmployeeEducationDTO> objectListToEducationalDetailsReport(List<Object[]> educationalDetailsObj) {

		List<EmployeeEducationDTO> educationalDetailsDTOList = new ArrayList<EmployeeEducationDTO>();

		for (Object[] report : educationalDetailsObj) {
			EmployeeEducationDTO employeeEducationDTO = new EmployeeEducationDTO();

			String employeeCode = report[0] != null ? (String) report[0] : null;
			String employee = report[1] != null ? (String) report[1] : null;
			String educationLevel = report[2] != null ? (String) report[2] : null;
			String degree = report[3] != null ? (String) report[3] : null;
			String nameOfInstitution = report[4] != null ? (String) report[4] : null;
			String nameOfBoard = report[5] != null ? (String) report[5] : null;
			BigDecimal marks = report[6] != null ? (BigDecimal) report[6] : null;
			Integer passingYear = report[7] != null ? (Integer) report[7] : null;
			String regularCorrespondance = report[8] != null ? (String) report[8] : null;

			employeeEducationDTO.setEmpCode(employeeCode);
			employeeEducationDTO.setEmpName(employee);

			employeeEducationDTO.setQualificationId(DropDownCache.getInstance()
					.getDropDownValue(DropDownEnum.Qualification.getDropDownName(), educationLevel));

			employeeEducationDTO.setDegreeName(
					DropDownCache.getInstance().getDropDownValue(DropDownEnum.Graduate.getDropDownName(), degree));

			employeeEducationDTO.setNameOfInstitution(nameOfInstitution);
			employeeEducationDTO.setNameOfBoard(nameOfBoard);
			employeeEducationDTO.setMarksPer(marks);
			employeeEducationDTO.setPassoutYear(passingYear);

			employeeEducationDTO.setRegularCorrespondance(DropDownCache.getInstance()
					.getDropDownValue(DropDownEnum.RegularCorrespondence.getDropDownName(), regularCorrespondance));

			educationalDetailsDTOList.add(employeeEducationDTO);
		}
		return educationalDetailsDTOList;
	}

	public List<ProfessionalInformation> objectListToDtoProfDetailsList(List<Object[]> profDetailsObjectList) {

		List<ProfessionalInformation> professionalInformationDTOList = new ArrayList<ProfessionalInformation>();

		for (Object[] report : profDetailsObjectList) {

			ProfessionalInformation professionalInformationDTO = new ProfessionalInformation();

			String employeeCode = report[0] != null ? (String) report[0] : null;
			String employeeName = report[1] != null ? (String) report[1] : null;
			String organizationName = report[2] != null ? (String) report[2] : null;
			Date fromDate = report[3] != null ? (Date) report[3] : null;
			Date toDate = report[4] != null ? (Date) report[4] : null;
			String designation = report[5] != null ? (String) report[5] : null;
			String reportingTo = report[6] != null ? (String) report[6] : null;
			String reportingContact = report[7] != null ? (String) report[7] : null;
			BigDecimal annualSalary = report[8] != null ? (BigDecimal) report[8] : null;
			String reasonForChangeId = report[9] != null ? (String) report[9] : null;

			professionalInformationDTO.setEmpCode(employeeCode);
			professionalInformationDTO.setEmployeeName(employeeName);
			professionalInformationDTO.setOrganizationName(organizationName);
			professionalInformationDTO.setDateFrom(fromDate);
			professionalInformationDTO.setDateTo(toDate);
			professionalInformationDTO.setDesignation(designation);
			professionalInformationDTO.setReportingTo(reportingTo);
			professionalInformationDTO.setReportingContact(reportingContact);
			professionalInformationDTO.setAnnualSalary(annualSalary);
			professionalInformationDTO.setReasonForChange(DropDownCache.getInstance()
					.getDropDownValue(DropDownEnum.ReasonForChange.getDropDownName(), reasonForChangeId));

			professionalInformationDTOList.add(professionalInformationDTO);
		}
		return professionalInformationDTOList;
	}

	// Nominee Details

	public List<EmployeeStatuaryDTO> objectListToDtoNomineeDetailsList(List<Object[]> nomineeDetailsObjectList) {

		List<EmployeeStatuaryDTO> employeeStatuaryDTOList = new ArrayList<EmployeeStatuaryDTO>();

		for (Object[] report : nomineeDetailsObjectList) {

			EmployeeStatuaryDTO employeeStatuaryDTO = new EmployeeStatuaryDTO();

			String employeeCode = report[0] != null ? (String) report[0] : "---";
			String employeeName = report[1] != null ? (String) report[1] : "---";
			String nomineeName = report[2] != null ? (String) report[2] : "---";
			String relation = report[3] != null ? (String) report[3] : "";
			String nomineeFor = report[4] != null ? (String) report[4] : "";
			String contactNumber = report[5] != null ? (String) report[5] : "---";

			employeeStatuaryDTO.setEmpCode(employeeCode);
			employeeStatuaryDTO.setEmpName(employeeName);
			employeeStatuaryDTO.setName(nomineeName);
			if (relation.isEmpty())
				employeeStatuaryDTO.setFamilyRelation("---");
			else
				employeeStatuaryDTO.setFamilyRelation(DropDownCache.getInstance()
						.getDropDownValue(DropDownEnum.Relation.getDropDownName(), relation));

			if (nomineeFor.isEmpty())
				employeeStatuaryDTO.setStatuaryType("---");
			else
				employeeStatuaryDTO.setStatuaryType(DropDownCache.getInstance()
						.getDropDownValue(DropDownEnum.Statuary.getDropDownName(), nomineeFor));

			if (contactNumber.isEmpty())
				employeeStatuaryDTO.setContactMobile("---");
			else
				employeeStatuaryDTO.setContactMobile(contactNumber);

			employeeStatuaryDTOList.add(employeeStatuaryDTO);
		}
		return employeeStatuaryDTOList;

	}

	// Esic
	public List<EsiInfoDTO> objectListToEsiInfoDtoList(List<Object[]> esiInfoObjectList) {

		List<EsiInfoDTO> esiInfoDTOList = new ArrayList<EsiInfoDTO>();
		for (Object[] report : esiInfoObjectList) {
			EsiInfoDTO esiInfoDTO = new EsiInfoDTO();

			String employeeCode = report[0] != null ? (String) report[0] : null;
			String employee = report[1] != null ? (String) report[1] : null;
			String statuaryNumber = report[2] != null ? (String) report[2] : " under process ";
			Date dateFrom = report[3] != null ? (Date) report[3] : null;
			Date dateTo = report[4] != null ? (Date) report[4] : null;

			esiInfoDTO.setEmployeeCode(employeeCode);
			esiInfoDTO.setEmployeeName(employee);
			if (statuaryNumber.isEmpty())
				esiInfoDTO.setEsicNumber(" under process ");
			else
				esiInfoDTO.setEsicNumber(statuaryNumber);
			esiInfoDTO.setEsicIssueDate(dateFrom);
			esiInfoDTO.setEsicExpiryDate(dateTo);

			esiInfoDTOList.add(esiInfoDTO);

		}
		return esiInfoDTOList;

	}

	// Med Ins

	public List<EmployeeStatuaryDTO> objectListToEmployeeStatuaryDtoList(List<Object[]> medInsObjectList) {

		List<EmployeeStatuaryDTO> employeeStatuaryDTOList = new ArrayList<EmployeeStatuaryDTO>();

		for (Object[] report : medInsObjectList) {
			EmployeeStatuaryDTO employeeStatuaryDTO = new EmployeeStatuaryDTO();

			String employeeCode = report[0] != null ? (String) report[0] : null;
			String name = report[1] != null ? (String) report[1] : null;
			String statuaryNumber = report[2] != null ? (String) report[2] : " under process ";
			Date dateFrom = report[3] != null ? (Date) report[3] : null;
			Date dateTo = report[4] != null ? (Date) report[4] : null;

			employeeStatuaryDTO.setEmpCode(employeeCode);
			employeeStatuaryDTO.setEmpName(name);

			if (statuaryNumber.isEmpty())
				employeeStatuaryDTO.setStatuaryNumber(" under process ");
			else
				employeeStatuaryDTO.setStatuaryNumber(statuaryNumber);

			employeeStatuaryDTO.setEffectiveStartDate(dateFrom);
			employeeStatuaryDTO.setEffectiveEndDate(dateTo);

			employeeStatuaryDTOList.add(employeeStatuaryDTO);

		}
		return employeeStatuaryDTOList;

	}

	public List<EmployeeDTO> objectListToEmployeesOnNoticePeriod(List<Object[]> employeeList) {
		// TODO Auto-generated method stub
		List<EmployeeDTO> employeeDtoList = new ArrayList<EmployeeDTO>();

		for (Object[] report : employeeList) {
			EmployeeDTO employeeDto = new EmployeeDTO();

			String employeeCode = report[0] != null ? (String) report[0] : null;
			String firstName = report[1] != null ? (String) report[1] : null;
			String middleName = report[2] != null ? (String) report[2] : null;
			String lastName = report[3] != null ? (String) report[3] : null;
			String contactNo = report[4] != null ? (String) report[4] : null;
			String emailId = report[5] != null ? (String) report[5] : null;
			Date dateOfJoining = report[6] != null ? (Date) report[6] : null;
			String jobLocation = report[7] != null ? (String) report[7] : null;
			String shiftName = report[8] != null ? (String) report[8] : null;
			String weeklyOffPattern = report[9] != null ? (String) report[9] : null;
			String departmentName = report[10] != null ? (String) report[10] : null;
			String designationName = report[11] != null ? (String) report[11] : null;
			String reportingTo = report[12] != null ? (String) report[12] : null;
			String gradesName = report[13] != null ? (String) report[13] : null;
			Integer probationDays = report[14] != null ? (Integer) report[14] : null;
			Integer noticePeriodDays = report[15] != null ? (Integer) report[15] : null;
			String empType = report[16] != null ? (String) report[16] : null;
			String timeContract = report[17] != null ? (String) report[17] : null;
			Date resignationDate = report[18] != null ? (Date) report[18] : null;
			Date exitDate = report[19] != null ? (Date) report[19] : null;

			employeeDto.setEmployeeCode(employeeCode);
			employeeDto.setFirstName(firstName);
			employeeDto.setMiddleName(middleName);
			employeeDto.setLastName(lastName);
			employeeDto.setContactNo(contactNo);
			employeeDto.setEmailId(emailId);
			employeeDto.setDateOfJoining(dateOfJoining);
			employeeDto.setJobLocation(jobLocation);
			employeeDto.setShiftName(shiftName);
			employeeDto.setWeeklyOffPattern(weeklyOffPattern);
			employeeDto.setDepartmentName(departmentName);
			employeeDto.setDesignationName(designationName);
			employeeDto.setReportingEmployeeFirstName(reportingTo);
			if (noticePeriodDays != null)
				employeeDto.setNoticePeriodDays(Long.valueOf(noticePeriodDays));
			if (gradesName != null)
				employeeDto.setGradesName(gradesName);
			if (probationDays != null)
				employeeDto.setProbationDays(Long.valueOf(probationDays));

			if (timeContract != null) {
				if (timeContract.equals("FT")) {
					employeeDto.setTimeContract("Full Time");
				} else if (timeContract.equals("HF")) {
					employeeDto.setTimeContract("Half Time");
				}
			}

			if (empType != null) {
				if (empType.equals("PE")) {
					employeeDto.setEmpType("Permanent");
				} else if (empType.equals("CO")) {
					employeeDto.setEmpType("Contract");
				}
			}

			employeeDto.setResignationDate(resignationDate);
			employeeDto.setExitDate(exitDate);
			employeeDtoList.add(employeeDto);
		}
		return employeeDtoList;

	}

	public List<EmployeeLanguage> objectListToLanguageKnownStatusReport(List<Object[]> languageKnownObj,
			Long employeeId) {

		List<EmployeeLanguage> languageKnownStatusList = new ArrayList<EmployeeLanguage>();

		if (employeeId != 0L) {

			for (Object[] report : languageKnownObj) {

				EmployeeLanguage employeeLanguage = new EmployeeLanguage();

				String employeeCode = report[0] != null ? (String) report[0] : null;
				String firstName = report[1] != null ? (String) report[1] : null;
				String middleName = report[2] != null ? (String) report[2] : null;
				String lastName = report[3] != null ? (String) report[3] : null;
				String deptName = report[4] != null ? (String) report[4] : null;
				String designation = report[5] != null ? (String) report[5] : null;
				String hRead = report[6] != null ? (String) report[6] : null;
				String hWrite = report[7] != null ? (String) report[7] : null;
				String hSpeak = report[8] != null ? (String) report[8] : null;
				String eRead = report[9] != null ? (String) report[9] : null;
				String eWrite = report[10] != null ? (String) report[10] : null;
				String eSpeak = report[11] != null ? (String) report[11] : null;

				employeeLanguage.setEmployeeCode(employeeCode);
				employeeLanguage.setFirstName(firstName);
				employeeLanguage.setMiddleName(middleName);
				employeeLanguage.setLastName(lastName);
				employeeLanguage.setDeptName(deptName);
				employeeLanguage.setDesignation(designation);
				employeeLanguage.sethRead(hRead);
				employeeLanguage.sethWrite(hWrite);
				employeeLanguage.sethSpeak(hSpeak);
				employeeLanguage.seteRead(eRead);
				employeeLanguage.seteWrite(eWrite);
				employeeLanguage.seteSpeak(eSpeak);

				languageKnownStatusList.add(employeeLanguage);
			}

		} else {
			for (Object[] report : languageKnownObj) {

				EmployeeLanguage employeeLanguage = new EmployeeLanguage();

				String employeeCode = report[0] != null ? (String) report[0] : null;
				String firstName = report[1] != null ? (String) report[1] : null;
				String middleName = report[2] != null ? (String) report[2] : null;
				String lastName = report[3] != null ? (String) report[3] : null;
				String deptName = report[4] != null ? (String) report[4] : null;
				String designation = report[5] != null ? (String) report[5] : null;
				Integer langId = report[6] != null ? (Integer) report[6] : 0;
				String read = report[7] != null ? (String) report[7] : null;
				String write = report[8] != null ? (String) report[8] : null;
				String speak = report[9] != null ? (String) report[9] : null;

				employeeLanguage.setEmployeeCode(employeeCode);
				employeeLanguage.setFirstName(firstName);
				employeeLanguage.setMiddleName(middleName);
				employeeLanguage.setLastName(lastName);
				employeeLanguage.setDeptName(deptName);
				employeeLanguage.setDesignation(designation);
				employeeLanguage.setLangId(langId);
				employeeLanguage.setLangRead(read);
				employeeLanguage.setLangWrite(write);
				employeeLanguage.setLangSpeak(speak);

				languageKnownStatusList.add(employeeLanguage);

			}
		}
		return languageKnownStatusList;

	}

	public List<SeparationDTO> objectListToSeparationRequestReport(List<Object[]> sepRequestObj) {

		List<SeparationDTO> sepRequestDTOList = new ArrayList<SeparationDTO>();

		for (Object[] report : sepRequestObj) {

			SeparationDTO separationDTO = new SeparationDTO();

			String empCode = report[0] != null ? (String) report[0] : null;
			String empName = report[1] != null ? (String) report[1] : null;
			String deptName = report[2] != null ? (String) report[2] : null;
			String desName = report[3] != null ? (String) report[3] : null;
			String jobLocationName = report[4] != null ? (String) report[4] : null;
			String repManagerName = report[5] != null ? (String) report[5] : null;
			Date joiningDate = report[6] != null ? ConverterUtil.getDate(report[6].toString()) : null;
			Date requestedOn = report[7] != null ? ConverterUtil.getDate(report[7].toString()) : null;
			String reason = report[8] != null ? (String) report[8] : null;
			Date exitDate = report[9] != null ? ConverterUtil.getDate(report[9].toString()) : null;
			Long noticePeriod = report[10] != null ? Long.parseLong(report[10].toString()) : null;
			String empRemark = report[11] != null ? (String) report[11] : null;

			separationDTO.setEmployeeCode(empCode);
			separationDTO.setEmployeeName(empName);
			separationDTO.setDepartmentName(deptName);
			separationDTO.setDesignationName(desName);
			separationDTO.setJobLocation(jobLocationName);
			separationDTO.setReportingTo(repManagerName);
			separationDTO.setDateOfJoining(joiningDate);
			separationDTO.setDateCreated(requestedOn);
			separationDTO.setDescription(reason);
			separationDTO.setExitDate(exitDate);
			separationDTO.setInNoticePeriod(noticePeriod);
			separationDTO.setRemark(empRemark);

			sepRequestDTOList.add(separationDTO);
		}

		return sepRequestDTOList;
	}

}
