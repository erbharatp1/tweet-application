package com.csipl.hrms.common.util;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.csipl.hrms.dto.employee.EmployeeDTO;
import com.csipl.hrms.dto.employee.EmployeeEducationDTO;
import com.csipl.hrms.dto.employee.EmployeeFamilyDTO;
import com.csipl.hrms.dto.employee.EmployeeIdProofDTO;
import com.csipl.hrms.dto.employee.EmployeeStatuaryDTO;
import com.csipl.hrms.dto.employee.SeparationDTO;
import com.csipl.hrms.dto.report.EsiInfoDTO;
import com.csipl.hrms.dto.report.PfReportDTO;
import com.csipl.hrms.model.employee.EmployeeLanguage;
import com.csipl.hrms.model.employee.ProfessionalInformation;

public class EmployeeExcelWriter {

	private static double convertBigdecimalToDouble(BigDecimal bigDecimalValue) {
		double doubleValue;

		if (bigDecimalValue != null)
			doubleValue = bigDecimalValue.doubleValue();
		else
			doubleValue = 0.0;
		return doubleValue;
	}

	public static Workbook employeeReport(String[] columns, List<EmployeeDTO> employeeList, String status)
			throws IOException, InvalidFormatException {

		// Create a Workbook
		XSSFWorkbook workbook = new XSSFWorkbook(); // new HSSFWorkbook() for generating `.xls` file

		/*
		 * CreationHelper helps us create instances for various things like DataFormat,
		 * Hyperlink, RichTextString etc, in a format (HSSF, XSSF) independent way
		 */
		CreationHelper createHelper = workbook.getCreationHelper();

		// Create a Sheet
		XSSFSheet sheet = workbook.createSheet("Active Onboard Employees");

		// sheet.addMergedRegion(CellRangeAddress.valueOf("A2:AB2"));
		//
		// sheet.addMergedRegion(CellRangeAddress.valueOf("AY2:BB2"));

		// Create a Font for styling header cells
		Font headerFont = workbook.createFont();
		headerFont.setBold(true);
		headerFont.setFontHeightInPoints((short) 12);
		headerFont.setColor(IndexedColors.BLACK.getIndex());

		// Create a CellStyle with the font
		// Create a CellStyle with the font
		CellStyle headCellStyle = workbook.createCellStyle();
		headCellStyle.setFont(headerFont);
		headCellStyle.setAlignment(HorizontalAlignment.CENTER);
		headCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
		headCellStyle.setFillBackgroundColor(IndexedColors.LIGHT_CORNFLOWER_BLUE.getIndex());
		headCellStyle.setFillPattern(FillPatternType.FINE_DOTS);

		CellStyle headerCellStyle = workbook.createCellStyle();
		headerCellStyle.setFont(headerFont);
		headerCellStyle.setAlignment(HorizontalAlignment.CENTER);
		headerCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
		headerCellStyle.setFillBackgroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
		headerCellStyle.setFillPattern(FillPatternType.FINE_DOTS);

		CellStyle present = workbook.createCellStyle();
		present.setFont(headerFont);
		present.setAlignment(HorizontalAlignment.CENTER);
		present.setVerticalAlignment(VerticalAlignment.CENTER);
		present.setFillBackgroundColor(IndexedColors.LIGHT_BLUE.getIndex());
		present.setFillPattern(FillPatternType.FINE_DOTS);

		CellStyle permanent = workbook.createCellStyle();
		permanent.setFont(headerFont);
		permanent.setAlignment(HorizontalAlignment.CENTER);
		permanent.setVerticalAlignment(VerticalAlignment.CENTER);
		permanent.setFillBackgroundColor(IndexedColors.SKY_BLUE.getIndex());
		permanent.setFillPattern(FillPatternType.FINE_DOTS);

		CellStyle referanceAdd = workbook.createCellStyle();
		referanceAdd.setFont(headerFont);
		referanceAdd.setAlignment(HorizontalAlignment.CENTER);
		referanceAdd.setVerticalAlignment(VerticalAlignment.CENTER);
		referanceAdd.setFillBackgroundColor(IndexedColors.AQUA.getIndex());
		referanceAdd.setFillPattern(FillPatternType.FINE_DOTS);

		CellStyle dateCellStyle = workbook.createCellStyle();
		dateCellStyle.setDataFormat(createHelper.createDataFormat().getFormat("dd-MM-yyyy"));
		XSSFRow row0 = sheet.createRow(0);

		XSSFRow headerRow = sheet.createRow(1);

		// Creating cells
		for (int i = 0; i < columns.length; i++) {
			Cell cell = headerRow.createCell(i);
			cell.setCellValue(columns[i]);
			cell.setCellStyle(headCellStyle);

		}

		// Create Cell Style for formatting Date
		dateCellStyle.setDataFormat(createHelper.createDataFormat().getFormat("dd-MM-yyyy"));

		int rowNum = 2;
		if (status.equals("AC")) {

			sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 27));
			Cell cell1 = row0.createCell(0);
			cell1.setCellValue("");
			cell1.setCellStyle(headCellStyle);

			sheet.addMergedRegion(new CellRangeAddress(0, 0, 28, 33));
			Cell cell8 = row0.createCell(28);
			cell8.setCellValue("Permanent Address");
			cell8.setCellStyle(permanent);

			// Row row1 = sheet.createRow(1);
			sheet.addMergedRegion(new CellRangeAddress(0, 0, 34, 39));
			Cell cell9 = row0.createCell(34);
			cell9.setCellValue("Present Address");
			cell9.setCellStyle(present);

			sheet.addMergedRegion(new CellRangeAddress(0, 0, 40, 47));
			Cell cell2 = row0.createCell(40);
			cell2.setCellValue("Reference Details");
			cell2.setCellStyle(referanceAdd);

			sheet.addMergedRegion(new CellRangeAddress(0, 0, 48, 51)); // 50,53
			Cell cell23 = row0.createCell(48); // 50
			cell23.setCellValue("");
			cell23.setCellStyle(headCellStyle);

			for (EmployeeDTO employee : employeeList) {
				Row row = sheet.createRow(rowNum++);
				// row.createCell(0).setCellValue(srNo++);
				if (employee.getEmployeeCode() != null)
					row.createCell(0).setCellValue(employee.getEmployeeCode());
				row.createCell(1).setCellValue(employee.getFirstName());
				if (employee.getMiddleName() != null)
					row.createCell(2).setCellValue(employee.getMiddleName());
				row.createCell(3).setCellValue(employee.getLastName());
				row.createCell(4).setCellValue(employee.getAadharNumber());
				row.createCell(5).setCellValue(employee.getContactNo());
				row.createCell(6).setCellValue(employee.getEmailId());
				row.createCell(7).setCellValue(employee.getJobLocation());
				row.createCell(8).setCellValue(employee.getShiftName());
				row.createCell(9).setCellValue(employee.getWeeklyOffPattern());
				row.createCell(10).setCellValue(employee.getAttendanceSchemeName());
				row.createCell(11).setCellValue(employee.getLeaveSchemeName());
				row.createCell(12).setCellValue(employee.getDepartmentName());
				row.createCell(13).setCellValue(employee.getDesignationName());
				row.createCell(14).setCellValue(employee.getBranchName());
				row.createCell(15).setCellValue(employee.getReportingEmployeeFirstName());

				row.createCell(16).setCellValue(employee.getGradesName());

				if (employee.getProbationDays() != null)
					row.createCell(17).setCellValue(employee.getProbationDays());

				if (employee.getNoticePeriodDays() != null)
					row.createCell(18).setCellValue(employee.getNoticePeriodDays());
				if (employee.getEmpType() != null)
					row.createCell(19).setCellValue(employee.getEmpType());

				Cell dateOfJoining = row.createCell(20);
				dateOfJoining.setCellValue(employee.getDateOfJoining());
				dateOfJoining.setCellStyle(dateCellStyle);

				// row.createCell(17).setCellValue(employee.getDateOfJoining());
				// row.createCell(21).setCellValue(employee.getContractStartDate());
				Cell startDate = row.createCell(21);
				startDate.setCellValue(employee.getContractStartDate());
				startDate.setCellStyle(dateCellStyle);
				// row.createCell(22).setCellValue(employee.getContractOverDate());
				Cell endDate = row.createCell(22);
				endDate.setCellValue(employee.getContractOverDate());
				endDate.setCellStyle(dateCellStyle);

				row.createCell(23).setCellValue(employee.getTimeContract());
				row.createCell(24).setCellValue(employee.getOfficialEmailId());

				if (employee.getRoleDescription() != null)
					row.createCell(25).setCellValue(employee.getRoleDescription());

				Cell dob = row.createCell(26);
				dob.setCellValue(employee.getDateOfBirth());
				dob.setCellStyle(dateCellStyle);

				// row.createCell(22).setCellValue(employee.getDateOfBirth());

				row.createCell(27).setCellValue(employee.getGender());
				row.createCell(28).setCellValue(employee.getBloodGroup());

				if (employee.getAlternateNumber() != null)
					row.createCell(29).setCellValue(employee.getAlternateNumber());

				row.createCell(30).setCellValue(employee.getMaritalStatus());

				// row.createCell(31).setCellValue(employee.getAnniversaryDate());
				Cell annDate = row.createCell(31);
				annDate.setCellValue(employee.getAnniversaryDate());
				annDate.setCellStyle(dateCellStyle);

				if (employee.getAddress1() != null) {
					row.createCell(32).setCellValue(employee.getAddress1().getAddressText());
					row.createCell(33).setCellValue(employee.getAddress1().getLandmark());
					row.createCell(34).setCellValue(employee.getAddress1().getPincode());
					if (employee.getAddress1().getCountryName() != null)
						row.createCell(35).setCellValue(employee.getAddress1().getCountryName());
					if (employee.getAddress1().getStateName() != null)
						row.createCell(36).setCellValue(employee.getAddress1().getStateName());
					if (employee.getAddress1().getCityName() != null)
						row.createCell(37).setCellValue(employee.getAddress1().getCityName());

				}

				if (employee.getAddress2() != null) {
					row.createCell(38).setCellValue(employee.getAddress2().getAddressText());
					row.createCell(39).setCellValue(employee.getAddress2().getLandmark());
					row.createCell(40).setCellValue(employee.getAddress2().getPincode());
					if (employee.getAddress2().getCountryName() != null)
						row.createCell(41).setCellValue(employee.getAddress2().getCountryName());
					if (employee.getAddress2().getStateName() != null)
						row.createCell(42).setCellValue(employee.getAddress2().getStateName());
					if (employee.getAddress2().getCityName() != null)
						row.createCell(43).setCellValue(employee.getAddress2().getCityName());

				}

				if (employee.getReferenceName() != null)
					row.createCell(44).setCellValue(employee.getReferenceName());
				if (employee.getReferenceMobile() != null)
					row.createCell(45).setCellValue(employee.getReferenceMobile());
				if (employee.getReferenceEmailId() != null)
					row.createCell(46).setCellValue(employee.getReferenceEmailId());

				if (employee.getAddress3() != null) {
					row.createCell(47).setCellValue(employee.getAddress3().getAddressText());
					row.createCell(48).setCellValue(employee.getAddress3().getPincode());
					if (employee.getAddress3().getCountryName() != null)
						row.createCell(49).setCellValue(employee.getAddress3().getCountryName());
					if (employee.getAddress3().getStateName() != null)
						row.createCell(50).setCellValue(employee.getAddress3().getStateName());
					if (employee.getAddress3().getCityName() != null)
						row.createCell(51).setCellValue(employee.getAddress3().getCityName());

				}

				row.createCell(52).setCellValue(employee.getBankId());
				row.createCell(53).setCellValue(employee.getAccountNumber());
				row.createCell(54).setCellValue(employee.getBankBranch());
				row.createCell(55).setCellValue(employee.getIfscCode());

				BigDecimal basicSalary = new BigDecimal(0);
				BigDecimal dearnessAllowance = new BigDecimal(0);
				BigDecimal houseRentAllowance = new BigDecimal(0);
				BigDecimal conveyanceAllowance = new BigDecimal(0);
				BigDecimal specialAllowance = new BigDecimal(0);
				BigDecimal medicalAllowance = new BigDecimal(0);
				BigDecimal advanceBonus = new BigDecimal(0);
				BigDecimal performanceLinkedIncome = new BigDecimal(0);
				BigDecimal companyBenefits = new BigDecimal(0);
				BigDecimal leaveTravelAllowance = new BigDecimal(0);
				BigDecimal uniformAllowance = new BigDecimal(0);

			}

		} else {
			sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 8));
			Cell cell1 = row0.createCell(0);
			cell1.setCellValue("");
			cell1.setCellStyle(headCellStyle);

			sheet.addMergedRegion(new CellRangeAddress(0, 0, 9, 17));
			Cell cell8 = row0.createCell(9);
			cell8.setCellValue("Present Address");
			cell8.setCellStyle(headerCellStyle);

			// Row row1 = sheet.createRow(1);
			sheet.addMergedRegion(new CellRangeAddress(0, 0, 18, 26));
			Cell cell9 = row0.createCell(18);
			cell9.setCellValue("Permanent Address");
			cell9.setCellStyle(headerCellStyle);

			sheet.addMergedRegion(new CellRangeAddress(0, 0, 27, 35));
			Cell cell2 = row0.createCell(27);
			cell2.setCellValue("");
			cell2.setCellStyle(headCellStyle);

			sheet.addMergedRegion(new CellRangeAddress(0, 0, 36, 37));
			Cell cell10 = row0.createCell(36);
			cell10.setCellValue("ID and Address Proof");
			cell10.setCellStyle(headerCellStyle);

			sheet.addMergedRegion(new CellRangeAddress(0, 0, 38, 40));
			Cell cell11 = row0.createCell(38);
			cell11.setCellValue("Statutory Info.");
			cell11.setCellStyle(headerCellStyle);

			sheet.addMergedRegion(new CellRangeAddress(0, 0, 41, 44));
			Cell cell12 = row0.createCell(41);
			cell12.setCellValue("Banking details");
			cell12.setCellStyle(headerCellStyle);

			sheet.addMergedRegion(new CellRangeAddress(0, 0, 45, 56));
			Cell cell13 = row0.createCell(45);
			cell13.setCellValue("Payheads");
			cell13.setCellStyle(headerCellStyle);

			for (EmployeeDTO employee : employeeList) {
				Row row = sheet.createRow(rowNum++);
				// row.createCell(0).setCellValue(srNo++);
				if (employee.getEmployeeCode() != null)
					row.createCell(0).setCellValue(employee.getEmployeeCode());
				row.createCell(1).setCellValue(employee.getFirstName());
				if (employee.getMiddleName() != null)
					row.createCell(2).setCellValue(employee.getMiddleName());
				row.createCell(3).setCellValue(employee.getLastName());
				Cell dateOfBirthCell = row.createCell(4);
				dateOfBirthCell.setCellValue(employee.getDateOfBirth());
				dateOfBirthCell.setCellStyle(dateCellStyle);

				// row.createCell(5).setCellValue(employee.getGenderValue());

				Cell dateOfJoingCell = row.createCell(6);
				dateOfJoingCell.setCellValue(employee.getDateOfJoining());
				dateOfJoingCell.setCellStyle(dateCellStyle);

				Cell dateOfJoiningCell = row.createCell(7);
				// dateOfJoiningCell.setCellValue(employee.getDateUpdate());
				dateOfJoiningCell.setCellStyle(dateCellStyle);

				// row.createCell(8).setCellValue(employee.getMaritalStatusValue());
				if (employee.getAddress1() != null) {
					row.createCell(9).setCellValue(employee.getAddress1().getAddressText());
					row.createCell(10).setCellValue(employee.getAddress1().getLandmark());
					if (employee.getAddress1().getCountry() != null)
						row.createCell(11).setCellValue(employee.getAddress1().getCountry().getCountryName());
					if (employee.getAddress1().getState() != null)
						row.createCell(12).setCellValue(employee.getAddress1().getState().getStateName());
					if (employee.getAddress1().getCity() != null)
						row.createCell(13).setCellValue(employee.getAddress1().getCity().getCityName());
					row.createCell(14).setCellValue(employee.getAddress1().getPincode());
					row.createCell(15).setCellValue(employee.getAddress1().getMobile());
					row.createCell(16).setCellValue(employee.getAddress1().getTelephone());
					row.createCell(17).setCellValue(employee.getAddress1().getEmailId());
				}

				if (employee.getAddress2() != null) {
					row.createCell(18).setCellValue(employee.getAddress2().getAddressText());
					row.createCell(19).setCellValue(employee.getAddress2().getLandmark());
					if (employee.getAddress2().getCountry() != null)
						row.createCell(20).setCellValue(employee.getAddress2().getCountry().getCountryName());
					if (employee.getAddress2().getState() != null)
						row.createCell(21).setCellValue(employee.getAddress2().getState().getStateName());
					if (employee.getAddress2().getCity() != null)
						row.createCell(22).setCellValue(employee.getAddress2().getCity().getCityName());
					row.createCell(23).setCellValue(employee.getAddress2().getPincode());
					row.createCell(24).setCellValue(employee.getAddress2().getMobile());
					row.createCell(25).setCellValue(employee.getAddress2().getTelephone());
					row.createCell(26).setCellValue(employee.getAddress2().getEmailId());
				}

				Cell contractOverDateCell = row.createCell(34);
				contractOverDateCell.setCellValue(employee.getContractOverDate());
				contractOverDateCell.setCellStyle(dateCellStyle);

				if (employee.getReferenceName() != null)
					row.createCell(35).setCellValue(employee.getReferenceName());

				String pan = "";

				String ifscCode = "", accountNumber = "";

				if (accountNumber != null)
					row.createCell(43).setCellValue(accountNumber);
				if (ifscCode != null)
					row.createCell(44).setCellValue(ifscCode);

			}

		}

		sheet.addMergedRegion(new CellRangeAddress(rowNum + 1, rowNum + 1, 4, 6));
		sheet.addMergedRegion(new CellRangeAddress(rowNum + 2, rowNum + 2, 5, 6));

		// Resize all columns to fit the content size
		for (int i = 0; i < columns.length; i++) {
			sheet.autoSizeColumn(i);
		}

		return workbook;
	}

	public static Workbook formerEmployeeReport(String[] columns, List<EmployeeDTO> employeeList)
			throws IOException, InvalidFormatException {
		XSSFWorkbook workbook = new XSSFWorkbook(); // new HSSFWorkbook() for generating `.xls` file

		/*
		 * CreationHelper helps us create instances for various things like DataFormat,
		 * Hyperlink, RichTextString etc, in a format (HSSF, XSSF) independent way
		 */
		CreationHelper createHelper = workbook.getCreationHelper();

		// Create a Sheet
		XSSFSheet sheet = workbook.createSheet("Former Employee");

		// Create a Font for styling header cells
		Font headerFont = workbook.createFont();
		headerFont.setBold(true);
		headerFont.setFontHeightInPoints((short) 12);
		headerFont.setColor(IndexedColors.BLACK.getIndex());

		// Create a CellStyle with the font
		// Create a CellStyle with the font
		CellStyle headCellStyle = workbook.createCellStyle();
		headCellStyle.setFont(headerFont);
		headCellStyle.setAlignment(HorizontalAlignment.CENTER);
		headCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
		headCellStyle.setFillBackgroundColor(IndexedColors.LIGHT_CORNFLOWER_BLUE.getIndex());
		headCellStyle.setFillPattern(FillPatternType.FINE_DOTS);

		CellStyle headerCellStyle = workbook.createCellStyle();
		headerCellStyle.setFont(headerFont);
		headerCellStyle.setAlignment(HorizontalAlignment.CENTER);
		headerCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
		headerCellStyle.setFillBackgroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
		headerCellStyle.setFillPattern(FillPatternType.FINE_DOTS);

		CellStyle present = workbook.createCellStyle();
		present.setFont(headerFont);
		present.setAlignment(HorizontalAlignment.CENTER);
		present.setVerticalAlignment(VerticalAlignment.CENTER);
		present.setFillBackgroundColor(IndexedColors.LIGHT_BLUE.getIndex());
		present.setFillPattern(FillPatternType.FINE_DOTS);

		CellStyle permanent = workbook.createCellStyle();
		permanent.setFont(headerFont);
		permanent.setAlignment(HorizontalAlignment.CENTER);
		permanent.setVerticalAlignment(VerticalAlignment.CENTER);
		permanent.setFillBackgroundColor(IndexedColors.SKY_BLUE.getIndex());
		permanent.setFillPattern(FillPatternType.FINE_DOTS);

		CellStyle referanceAdd = workbook.createCellStyle();
		referanceAdd.setFont(headerFont);
		referanceAdd.setAlignment(HorizontalAlignment.CENTER);
		referanceAdd.setVerticalAlignment(VerticalAlignment.CENTER);
		referanceAdd.setFillBackgroundColor(IndexedColors.AQUA.getIndex());
		referanceAdd.setFillPattern(FillPatternType.FINE_DOTS);

		// Create a Row

		// Create Cell Style for formatting Date
		CellStyle dateCellStyle = workbook.createCellStyle();
		dateCellStyle.setDataFormat(createHelper.createDataFormat().getFormat("dd-MM-yyyy"));
		XSSFRow row0 = sheet.createRow(0);

		XSSFRow headerRow = sheet.createRow(1);

		// Creating cells
		for (int i = 0; i < columns.length; i++) {
			Cell cell = headerRow.createCell(i);
			cell.setCellValue(columns[i]);
			cell.setCellStyle(headCellStyle);

		}

		// Create Cell Style for formatting Date
		dateCellStyle.setDataFormat(createHelper.createDataFormat().getFormat("dd-MM-yyyy"));

		int rowNum = 2;

		sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 28));
		Cell cell1 = row0.createCell(0);
		cell1.setCellValue("");
		cell1.setCellStyle(headCellStyle);

		sheet.addMergedRegion(new CellRangeAddress(0, 0, 29, 34));
		Cell cell8 = row0.createCell(29);
		cell8.setCellValue("Permanent Address");
		cell8.setCellStyle(permanent);

		// Row row1 = sheet.createRow(1);
		sheet.addMergedRegion(new CellRangeAddress(0, 0, 35, 40));
		Cell cell9 = row0.createCell(35);
		cell9.setCellValue("Present Address");
		cell9.setCellStyle(present);

		sheet.addMergedRegion(new CellRangeAddress(0, 0, 41, 48));
		Cell cell2 = row0.createCell(41);
		cell2.setCellValue("Reference Details");
		cell2.setCellStyle(referanceAdd);

		sheet.addMergedRegion(new CellRangeAddress(0, 0, 49, 52)); // 50,53
		Cell cell23 = row0.createCell(49); // 50
		cell23.setCellValue("");
		cell23.setCellStyle(headCellStyle);

		for (EmployeeDTO employee : employeeList) {
			Row row = sheet.createRow(rowNum++);
			// row.createCell(0).setCellValue(srNo++);
			if (employee.getEmployeeCode() != null)
				row.createCell(0).setCellValue(employee.getEmployeeCode());
			row.createCell(1).setCellValue(employee.getFirstName());
			if (employee.getMiddleName() != null)
				row.createCell(2).setCellValue(employee.getMiddleName());
			row.createCell(3).setCellValue(employee.getLastName());
			row.createCell(4).setCellValue(employee.getAadharNumber());
			row.createCell(5).setCellValue(employee.getContactNo());
			row.createCell(6).setCellValue(employee.getEmailId());
			row.createCell(7).setCellValue(employee.getJobLocation());
			row.createCell(8).setCellValue(employee.getShiftName());
			row.createCell(9).setCellValue(employee.getWeeklyOffPattern());
			row.createCell(10).setCellValue(employee.getDepartmentName());
			row.createCell(11).setCellValue(employee.getDesignationName());
			row.createCell(12).setCellValue(employee.getReportingEmployeeFirstName());

			row.createCell(13).setCellValue(employee.getGradesName());

			if (employee.getProbationDays() != null)
				row.createCell(14).setCellValue(employee.getProbationDays());

			if (employee.getNoticePeriodDays() != null)
				row.createCell(15).setCellValue(employee.getNoticePeriodDays());
			if (employee.getEmpType() != null)
				row.createCell(16).setCellValue(employee.getEmpType());

			Cell dateOfJoining = row.createCell(17);
			dateOfJoining.setCellValue(employee.getDateOfJoining());
			dateOfJoining.setCellStyle(dateCellStyle);

			// row.createCell(17).setCellValue(employee.getDateOfJoining());
			Cell endDate = row.createCell(18);
			endDate.setCellValue(employee.getEndDate());
			endDate.setCellStyle(dateCellStyle);

			row.createCell(19).setCellValue(employee.getContractStartDate());
			row.createCell(20).setCellValue(employee.getContractOverDate());
			row.createCell(21).setCellValue(employee.getTimeContract());

			if (employee.getSystemRole() != null)
				row.createCell(22).setCellValue(employee.getSystemRole());

			Cell dob = row.createCell(23);
			dob.setCellValue(employee.getDateOfBirth());
			dob.setCellStyle(dateCellStyle);

			// row.createCell(22).setCellValue(employee.getDateOfBirth());

			row.createCell(24).setCellValue(employee.getGender());
			row.createCell(25).setCellValue(employee.getBloodGroup());

			if (employee.getAlternateNumber() != null)
				row.createCell(26).setCellValue(employee.getAlternateNumber());

			row.createCell(27).setCellValue(employee.getMaritalStatus());
			row.createCell(28).setCellValue(employee.getAnniversaryDate());

			if (employee.getAddress1() != null) {
				row.createCell(29).setCellValue(employee.getAddress1().getAddressText());
				row.createCell(30).setCellValue(employee.getAddress1().getLandmark());
				row.createCell(31).setCellValue(employee.getAddress1().getPincode());
				if (employee.getAddress1().getCountryName() != null)
					row.createCell(32).setCellValue(employee.getAddress1().getCountryName());
				if (employee.getAddress1().getStateName() != null)
					row.createCell(33).setCellValue(employee.getAddress1().getStateName());
				if (employee.getAddress1().getCityName() != null)
					row.createCell(34).setCellValue(employee.getAddress1().getCityName());

			}

			if (employee.getAddress2() != null) {
				row.createCell(35).setCellValue(employee.getAddress2().getAddressText());
				row.createCell(36).setCellValue(employee.getAddress2().getLandmark());
				row.createCell(37).setCellValue(employee.getAddress2().getPincode());
				if (employee.getAddress2().getCountryName() != null)
					row.createCell(38).setCellValue(employee.getAddress2().getCountryName());
				if (employee.getAddress2().getStateName() != null)
					row.createCell(39).setCellValue(employee.getAddress2().getStateName());
				if (employee.getAddress2().getCityName() != null)
					row.createCell(40).setCellValue(employee.getAddress2().getCityName());

			}

			if (employee.getReferenceName() != null)
				row.createCell(41).setCellValue(employee.getReferenceName());
			if (employee.getReferenceMobile() != null)
				row.createCell(42).setCellValue(employee.getReferenceMobile());
			if (employee.getReferenceEmailId() != null)
				row.createCell(43).setCellValue(employee.getReferenceEmailId());

			if (employee.getAddress3() != null) {
				row.createCell(44).setCellValue(employee.getAddress3().getAddressText());
				// row.createCell(44).setCellValue(employee.getAddress2().getLandmark());
				row.createCell(45).setCellValue(employee.getAddress3().getPincode());
				if (employee.getAddress3().getCountryName() != null)
					row.createCell(46).setCellValue(employee.getAddress3().getCountryName());
				if (employee.getAddress3().getStateName() != null)
					row.createCell(47).setCellValue(employee.getAddress3().getStateName());
				// if (employee.getAddress3().getStateId() != null)
				// row.createCell(47).setCellValue(employee.getAddress3().getStateId());
				if (employee.getAddress3().getCityName() != null)
					row.createCell(48).setCellValue(employee.getAddress3().getCityName());

			}

			row.createCell(49).setCellValue(employee.getBankId());
			row.createCell(50).setCellValue(employee.getAccountNumber());
			row.createCell(51).setCellValue(employee.getBankBranch());
			row.createCell(52).setCellValue(employee.getIfscCode());

			BigDecimal basicSalary = new BigDecimal(0);
			BigDecimal dearnessAllowance = new BigDecimal(0);
			BigDecimal houseRentAllowance = new BigDecimal(0);
			BigDecimal conveyanceAllowance = new BigDecimal(0);
			BigDecimal specialAllowance = new BigDecimal(0);
			BigDecimal medicalAllowance = new BigDecimal(0);
			BigDecimal advanceBonus = new BigDecimal(0);
			BigDecimal performanceLinkedIncome = new BigDecimal(0);
			BigDecimal companyBenefits = new BigDecimal(0);
			BigDecimal leaveTravelAllowance = new BigDecimal(0);
			BigDecimal uniformAllowance = new BigDecimal(0);

		}

		sheet.addMergedRegion(new CellRangeAddress(rowNum + 1, rowNum + 1, 4, 6));
		sheet.addMergedRegion(new CellRangeAddress(rowNum + 2, rowNum + 2, 5, 6));

		// Resize all columns to fit the content size
		for (int i = 0; i < columns.length; i++) {
			sheet.autoSizeColumn(i);
		}

		return workbook;

	}

	public static void addCustomObjIdAddr(EmployeeIdProofDTO prvObj, EmployeeIdProofDTO employeeIdProofDTO,
			HashMap<String, EmployeeIdProofDTO> map) {

		prvObj = map.get(employeeIdProofDTO.getEmpCode());
		if (prvObj.getEmpCode().equals(employeeIdProofDTO.getEmpCode())
				&& !prvObj.getIdTypeId().equals(employeeIdProofDTO.getIdTypeId())) {

			prvObj.setIdTypeId(employeeIdProofDTO.getIdTypeId());
			prvObj.setIdNumber(employeeIdProofDTO.getIdNumber());
			if (employeeIdProofDTO.getIdTypeId().equalsIgnoreCase("AC")) {

				prvObj.setAdharCardNo(employeeIdProofDTO.getIdNumber());

			} else if (employeeIdProofDTO.getIdTypeId().equalsIgnoreCase("PA")) {

				prvObj.setPanCardNo(employeeIdProofDTO.getIdNumber());
			} else if (employeeIdProofDTO.getIdTypeId().equalsIgnoreCase("VO")) {

				prvObj.setVoterIdNo(employeeIdProofDTO.getVoterIdNo());

			} else if (employeeIdProofDTO.getIdTypeId().equalsIgnoreCase("DL")) {

				prvObj.setDrivingLicenceNo(employeeIdProofDTO.getIdNumber());

				prvObj.setdLIssueDate(employeeIdProofDTO.getDateIssue());

				prvObj.setdLExpiryDate(employeeIdProofDTO.getDateExpiry());
			} else if (employeeIdProofDTO.getIdTypeId().equalsIgnoreCase("PS")) {

				prvObj.setPassportNo(employeeIdProofDTO.getIdNumber());

				prvObj.setpSIssueDate(employeeIdProofDTO.getDateIssue());

				prvObj.setpSExpiryDate(employeeIdProofDTO.getDateExpiry());

			}

		}

	}

	public static Workbook idAddressProofsReportWriter(List<EmployeeIdProofDTO> employeeIdProofDTOList,
			Object[] newColumns, Long employeeId, String status) throws IOException, InvalidFormatException {

		Workbook workbook = new XSSFWorkbook();

		CreationHelper createHelper = workbook.getCreationHelper();

		// Create a Sheet
		Sheet sheet = workbook.createSheet("ID & Address Proof");

		// Create a Font for styling header cells
		Font headerFont = workbook.createFont();
		headerFont.setBold(true);
		headerFont.setFontHeightInPoints((short) 12);
		headerFont.setColor(IndexedColors.BLACK.getIndex());

		Font rowFont = workbook.createFont();
		rowFont.setBold(true);
		rowFont.setFontHeightInPoints((short) 14);
		rowFont.setColor(IndexedColors.BLACK.getIndex());

		// Create a CellStyle with the font
		CellStyle headerCellStyle1 = workbook.createCellStyle();
		headerCellStyle1.setFont(headerFont);
		headerCellStyle1.setAlignment(HorizontalAlignment.LEFT);
		headerCellStyle1.setVerticalAlignment(VerticalAlignment.CENTER);

		CellStyle headerCellStyle2 = workbook.createCellStyle();
		headerCellStyle2.setFont(headerFont);
		headerCellStyle2.setAlignment(HorizontalAlignment.CENTER);
		headerCellStyle2.setVerticalAlignment(VerticalAlignment.CENTER);
		headerCellStyle2.setFillBackgroundColor(IndexedColors.LIGHT_CORNFLOWER_BLUE.getIndex());
		headerCellStyle2.setFillPattern(FillPatternType.FINE_DOTS);

		CellStyle rowCellStyle = workbook.createCellStyle();
		rowCellStyle.setFont(rowFont);

		// Create Cell Style for formatting Date
		CellStyle dateCellStyle = workbook.createCellStyle();
		dateCellStyle.setDataFormat(createHelper.createDataFormat().getFormat("dd-MMM-yyyy"));

		// creating row
		Row headerRow0 = sheet.createRow(0);

		if (status.equalsIgnoreCase("null")) {
			sheet.addMergedRegion(new CellRangeAddress(0, 1, 0, newColumns.length - 1));
			Cell cell0 = headerRow0.createCell(0);
			cell0.setCellValue("ID & Address Proof Details of an Employee");
			cell0.setCellStyle(headerCellStyle1);
		} else if (status.equalsIgnoreCase("AC")) {
			sheet.addMergedRegion(new CellRangeAddress(0, 1, 0, newColumns.length - 1));
			Cell cell0 = headerRow0.createCell(0);
			cell0.setCellValue(" ID & Address Proof Details of  Working Employees");
			cell0.setCellStyle(headerCellStyle1);
		} else if (status.equalsIgnoreCase("DE")) {
			sheet.addMergedRegion(new CellRangeAddress(0, 1, 0, newColumns.length - 1));
			Cell cell0 = headerRow0.createCell(0);
			cell0.setCellValue(" ID & Address Proof Details of  Former Employees");
			cell0.setCellStyle(headerCellStyle1);
		}

		Row headerRow1 = sheet.createRow(2);
		// Creating cells
		for (int i = 0; i < newColumns.length; i++) {
			Cell cell = headerRow1.createCell(i);
			cell.setCellValue(newColumns[i].toString());
			cell.setCellStyle(headerCellStyle2);
		}

		if (employeeIdProofDTOList.size() > 0) {

			int rowNum = 3;
			if (employeeId != 0L) {
				for (EmployeeIdProofDTO employeeIdProofDTO : employeeIdProofDTOList) {
					Row row = sheet.createRow(rowNum++);

					row.createCell(0).setCellValue(employeeIdProofDTO.getEmpCode());
					row.createCell(1).setCellValue(employeeIdProofDTO.getEmpName());
					row.createCell(2).setCellValue(employeeIdProofDTO.getAdharCardNo());
					row.createCell(3).setCellValue(employeeIdProofDTO.getPanCardNo());

					row.createCell(4).setCellValue(employeeIdProofDTO.getVoterIdNo());

					row.createCell(5).setCellValue(employeeIdProofDTO.getDrivingLicenceNo());
					Cell dateDLIssue = row.createCell(6);
					dateDLIssue.setCellValue(employeeIdProofDTO.getdLIssueDate());
					dateDLIssue.setCellStyle(dateCellStyle);
					Cell dateDLExpiry = row.createCell(7);
					dateDLExpiry.setCellValue(employeeIdProofDTO.getdLExpiryDate());
					dateDLExpiry.setCellStyle(dateCellStyle);

					row.createCell(8).setCellValue(employeeIdProofDTO.getPassportNo());
					Cell datePSIssue = row.createCell(9);
					datePSIssue.setCellValue(employeeIdProofDTO.getpSIssueDate());
					datePSIssue.setCellStyle(dateCellStyle);
					Cell datePSExpiry = row.createCell(10);
					datePSExpiry.setCellValue(employeeIdProofDTO.getpSExpiryDate());
					datePSExpiry.setCellStyle(dateCellStyle);
				}

			} else {
				HashMap<String, EmployeeIdProofDTO> map = new HashMap<String, EmployeeIdProofDTO>();

				for (EmployeeIdProofDTO employeeIdProofDTO : employeeIdProofDTOList) {

					EmployeeIdProofDTO obj = new EmployeeIdProofDTO();

					obj.setEmpCode(employeeIdProofDTO.getEmpCode());
					obj.setEmpName(employeeIdProofDTO.getEmpName());
					obj.setIdTypeId(employeeIdProofDTO.getIdTypeId());
					obj.setIdNumber(employeeIdProofDTO.getIdNumber());

					obj.setDateIssue(employeeIdProofDTO.getDateIssue());
					obj.setDateExpiry(employeeIdProofDTO.getDateExpiry());

					if (map.get(employeeIdProofDTO.getEmpCode()) != null ? true : false) {
						addCustomObjIdAddr(obj, employeeIdProofDTO, map);
					} else {
						map.put(employeeIdProofDTO.getEmpCode(), obj);
						if (employeeIdProofDTO.getIdTypeId().equalsIgnoreCase("AC")) {
							obj.setAdharCardNo(employeeIdProofDTO.getIdNumber());

						} else if (employeeIdProofDTO.getIdTypeId().equalsIgnoreCase("PA")) {
							obj.setPanCardNo(employeeIdProofDTO.getIdNumber());

						} else if (employeeIdProofDTO.getIdTypeId().equalsIgnoreCase("VO")) {
							obj.setVoterIdNo(employeeIdProofDTO.getIdNumber());

						} else if (employeeIdProofDTO.getIdTypeId().equalsIgnoreCase("DL")) {

							obj.setDrivingLicenceNo(employeeIdProofDTO.getIdNumber());
							obj.setdLIssueDate(employeeIdProofDTO.getDateIssue());
							obj.setdLExpiryDate(employeeIdProofDTO.getDateExpiry());

						} else if (employeeIdProofDTO.getIdTypeId().equalsIgnoreCase("PS")) {

							obj.setPassportNo(employeeIdProofDTO.getIdNumber());
							obj.setpSIssueDate(employeeIdProofDTO.getDateIssue());
							obj.setpSExpiryDate(employeeIdProofDTO.getDateExpiry());

						}

					}

				}
				int rowNuum = 3;

				for (Entry<String, EmployeeIdProofDTO> entry : map.entrySet()) {

					Row row = sheet.createRow(rowNuum++);
					row.createCell(0).setCellValue(entry.getValue().getEmpCode());
					row.createCell(1).setCellValue(entry.getValue().getEmpName());

					row.createCell(2).setCellValue(entry.getValue().getAdharCardNo());
					row.createCell(3).setCellValue(entry.getValue().getPanCardNo());

					row.createCell(4).setCellValue(entry.getValue().getVoterIdNo());

					row.createCell(5).setCellValue(entry.getValue().getDrivingLicenceNo());
					Cell dateDLIssue = row.createCell(6);
					dateDLIssue.setCellValue(entry.getValue().getdLIssueDate());
					dateDLIssue.setCellStyle(dateCellStyle);
					Cell dateDLExpiry = row.createCell(7);
					dateDLExpiry.setCellValue(entry.getValue().getdLExpiryDate());
					dateDLExpiry.setCellStyle(dateCellStyle);

					row.createCell(8).setCellValue(entry.getValue().getPassportNo());
					Cell datePSIssue = row.createCell(9);
					datePSIssue.setCellValue(entry.getValue().getpSIssueDate());
					datePSIssue.setCellStyle(dateCellStyle);
					Cell datePSExpiry = row.createCell(10);
					datePSExpiry.setCellValue(entry.getValue().getpSExpiryDate());
					datePSExpiry.setCellStyle(dateCellStyle);

					// row.createCell(11).setCellValue(entry.getValue().getDeptName());

				}
			}
		} else {

			Row row = sheet.createRow(3);

			sheet.addMergedRegion(new CellRangeAddress(3, 6, 0, newColumns.length - 1));
			Cell cell = row.createCell(0);
			cell.setCellValue(" --  Data Not Available -- ");
			cell.setCellStyle(headerCellStyle1);

		}

		// Resize columns to fit the content size
		for (int i = 0; i < newColumns.length; i++) {
			sheet.autoSizeColumn(i);
		}
		return workbook;
	}

	/**
	 * @author ${Nisha Parveen}
	 *
	 */
	// PF and UAN report Department wise 1122
	public static void addCustomObj(PfReportDTO prvObj, PfReportDTO pfReportDTO, HashMap<String, PfReportDTO> map) {

		prvObj = map.get(pfReportDTO.getEmpCode());
		if ((prvObj.getEmpCode().equals(pfReportDTO.getEmpCode()))
				&& (!prvObj.getStatuaryType().equals(pfReportDTO.getStatuaryType()))) {

			prvObj.setStatuaryNumber(pfReportDTO.getStatuaryNumber());
			prvObj.setPfNo(pfReportDTO.getStatuaryNumber());

			if (pfReportDTO.getStatuaryType().equalsIgnoreCase("UA")) {

				prvObj.setUanno(pfReportDTO.getStatuaryNumber());

			} else if (pfReportDTO.getStatuaryType().equalsIgnoreCase("PF")) {

				prvObj.setPfNo(pfReportDTO.getStatuaryNumber());
			}
		}

	}

	/**
	 * @author ${Nisha Parveen}
	 * @param employeeId
	 * @param departmentIds
	 *
	 */
	public static Workbook pfUANReportWriter(List<PfReportDTO> employeePfDTOEmployeeWiseList, String[] column2,
			List<Long> departmentIds, Long employeeId, String status) {

		Workbook workbook = new XSSFWorkbook();

		CreationHelper createHelper = workbook.getCreationHelper();

		// Create a Sheet
		Sheet sheet = workbook.createSheet("UAN & PF Numbers");

		// Create a Font for styling header cells
		Font headerFont = workbook.createFont();
		headerFont.setBold(true);
		headerFont.setFontHeightInPoints((short) 12);
		headerFont.setColor(IndexedColors.BLACK.getIndex());

		Font rowFont = workbook.createFont();
		rowFont.setBold(true);
		rowFont.setFontHeightInPoints((short) 14);
		rowFont.setColor(IndexedColors.BLACK.getIndex());

		// Create a CellStyle with the font
		CellStyle headerCellStyle1 = workbook.createCellStyle();
		headerCellStyle1.setFont(headerFont);
		headerCellStyle1.setAlignment(HorizontalAlignment.LEFT);
		headerCellStyle1.setVerticalAlignment(VerticalAlignment.CENTER);

		CellStyle headerCellStyle2 = workbook.createCellStyle();
		headerCellStyle2.setFont(headerFont);
		headerCellStyle2.setAlignment(HorizontalAlignment.CENTER);
		headerCellStyle2.setVerticalAlignment(VerticalAlignment.CENTER);
		headerCellStyle2.setFillPattern(FillPatternType.FINE_DOTS);

		CellStyle rowCellStyle = workbook.createCellStyle();
		rowCellStyle.setFont(rowFont);

		// Create Cell Style for formatting Date
		CellStyle dateCellStyle = workbook.createCellStyle();
		dateCellStyle.setDataFormat(createHelper.createDataFormat().getFormat("dd-MM-yyyy"));

		Row headerRow0 = sheet.createRow(0);

		if (status.equalsIgnoreCase("null")) {
			sheet.addMergedRegion(new CellRangeAddress(0, 1, 0, column2.length - 1));
			Cell cell0 = headerRow0.createCell(0);
			cell0.setCellValue(" UAN and PF Numbers of an Employee");
			cell0.setCellStyle(headerCellStyle1);
		} else if (status.equalsIgnoreCase("AC")) {
			sheet.addMergedRegion(new CellRangeAddress(0, 1, 0, column2.length - 1));
			Cell cell0 = headerRow0.createCell(0);
			cell0.setCellValue(" UAN and PF Numbers of  Working Employees");
			cell0.setCellStyle(headerCellStyle1);
		} else if (status.equalsIgnoreCase("DE")) {
			sheet.addMergedRegion(new CellRangeAddress(0, 1, 0, column2.length - 1));
			Cell cell0 = headerRow0.createCell(0);
			cell0.setCellValue(" UAN and PF Numbers of  Former Employees");
			cell0.setCellStyle(headerCellStyle1);
		}

		Row headerRow1 = sheet.createRow(2); // Creating cells
		for (int i = 0; i < column2.length; i++) {
			Cell cell = headerRow1.createCell(i);
			cell.setCellValue(column2[i].toString());
			cell.setCellStyle(headerCellStyle2);
		}
		// Employee Wise

		if (employeePfDTOEmployeeWiseList.size() > 0) {
			int rowNum = 3;

			if (employeeId != 0L) {
				for (PfReportDTO pfReportDTO : employeePfDTOEmployeeWiseList) {
					Row row = sheet.createRow(rowNum++);

					row.createCell(0).setCellValue(pfReportDTO.getEmpCode());
					row.createCell(1).setCellValue(pfReportDTO.getEmpName());

					row.createCell(2).setCellValue(pfReportDTO.getUanno());
					row.createCell(3).setCellValue(pfReportDTO.getPfNo());

					Cell dateDLIssue = row.createCell(4);
					dateDLIssue.setCellValue(pfReportDTO.getPfIssueDate());
					dateDLIssue.setCellStyle(dateCellStyle);
					Cell dateDLExpiry = row.createCell(5);
					dateDLExpiry.setCellValue(pfReportDTO.getPfExpiryDate());
					dateDLExpiry.setCellStyle(dateCellStyle);

					if (departmentIds.size() > 0) {
						row.createCell(2).setCellValue(pfReportDTO.getStatuaryType());
						row.createCell(3).setCellValue(pfReportDTO.getStatuaryNumber());
						// row.createCell(6).setCellValue(pfReportDTO.getDepartmentName());
					}

				}
			} else { // Dept wise

				HashMap<String, PfReportDTO> map = new HashMap<String, PfReportDTO>();

				if (employeePfDTOEmployeeWiseList != null) {
					for (PfReportDTO pfReportDTO : employeePfDTOEmployeeWiseList) {

						PfReportDTO obj = new PfReportDTO();
						obj.setEmpCode(pfReportDTO.getEmpCode());
						obj.setEmpName(pfReportDTO.getEmpName());
						obj.setStatuaryType(pfReportDTO.getStatuaryType());
						obj.setStatuaryNumber(pfReportDTO.getStatuaryNumber());
						obj.setPfIssueDate(pfReportDTO.getPfIssueDate());
						obj.setPfExpiryDate(pfReportDTO.getPfExpiryDate());
						// obj.setDepartmentName(pfReportDTO.getDepartmentName());

						if (map.get(pfReportDTO.getEmpCode()) != null ? true : false) {
							addCustomObj(obj, pfReportDTO, map);
						} else {
							map.put(pfReportDTO.getEmpCode(), obj);
							if (pfReportDTO.getStatuaryType().equalsIgnoreCase("UA")) {
								obj.setUanno(pfReportDTO.getStatuaryNumber());

							} else if (pfReportDTO.getStatuaryType().equalsIgnoreCase("PF")) {
								obj.setPfNo(pfReportDTO.getStatuaryNumber());

							}

						}
					}
				}
				int rowNuum = 3;

				for (Map.Entry<String, PfReportDTO> entry : map.entrySet()) {

					Row row = sheet.createRow(rowNuum++);
					row.createCell(0).setCellValue(entry.getValue().getEmpCode());
					row.createCell(1).setCellValue(entry.getValue().getEmpName());

					row.createCell(2).setCellValue(entry.getValue().getUanno());
					row.createCell(3).setCellValue(entry.getValue().getPfNo());

					Cell datePSIssue = row.createCell(4);
					datePSIssue.setCellValue(entry.getValue().getPfIssueDate());
					datePSIssue.setCellStyle(dateCellStyle);
					Cell datePSExpiry = row.createCell(5);
					datePSExpiry.setCellValue(entry.getValue().getPfExpiryDate());
					datePSExpiry.setCellStyle(dateCellStyle);

					// row.createCell(6).setCellValue(entry.getValue().getDepartmentName());

				}
			}
		} else {

			Row row = sheet.createRow(3);

			sheet.addMergedRegion(new CellRangeAddress(3, 6, 0, column2.length - 1));
			Cell cell = row.createCell(0);
			cell.setCellValue(" --  Data Not Available -- ");
			cell.setCellStyle(headerCellStyle1);

		}

		// Resize columns to fit the content size
		for (int i = 0; i < column2.length; i++) {
			sheet.autoSizeColumn(i);
		}
		return workbook;
	}

	/**
	 * @author ${Nisha Parveen}
	 * @param status
	 *
	 */
	public static Workbook accidentalInsuranceReportWriter(List<EmployeeStatuaryDTO> reportAcInsDTOList,
			Object[] newColumns, String status) {

		Workbook workbook = new XSSFWorkbook();

		CreationHelper createHelper = workbook.getCreationHelper();

		// Create a Sheet
		Sheet sheet = workbook.createSheet("Accidental Insurance Numbers");

		// Create a Font for styling header cells
		Font headerFont = workbook.createFont();
		headerFont.setBold(true);
		headerFont.setFontHeightInPoints((short) 12);
		headerFont.setColor(IndexedColors.BLACK.getIndex());

		Font rowFont = workbook.createFont();
		rowFont.setBold(true);
		rowFont.setFontHeightInPoints((short) 14);
		rowFont.setColor(IndexedColors.BLACK.getIndex());

		// Create a CellStyle with the font
		CellStyle headerCellStyle1 = workbook.createCellStyle();
		headerCellStyle1.setFont(headerFont);
		headerCellStyle1.setAlignment(HorizontalAlignment.LEFT);
		headerCellStyle1.setVerticalAlignment(VerticalAlignment.CENTER);

		CellStyle headerCellStyle2 = workbook.createCellStyle();
		headerCellStyle2.setFont(headerFont);
		headerCellStyle2.setAlignment(HorizontalAlignment.CENTER);
		headerCellStyle2.setVerticalAlignment(VerticalAlignment.CENTER);
		headerCellStyle2.setFillPattern(FillPatternType.FINE_DOTS);

		CellStyle rowCellStyle = workbook.createCellStyle();
		rowCellStyle.setFont(rowFont);

		// Create Cell Style for formatting Date
		CellStyle dateCellStyle = workbook.createCellStyle();
		dateCellStyle.setDataFormat(createHelper.createDataFormat().getFormat("dd-MM-yyyy"));

		// int col = newColumns.length - 1;
		// creating row
		Row headerRow0 = sheet.createRow(0);

		if (status.equalsIgnoreCase("null")) {
			sheet.addMergedRegion(new CellRangeAddress(0, 1, 0, newColumns.length - 1));
			Cell cell0 = headerRow0.createCell(0);
			cell0.setCellValue("Accidental Insurance Number of an Employee");
			cell0.setCellStyle(headerCellStyle1);
		} else if (status.equalsIgnoreCase("AC")) {
			sheet.addMergedRegion(new CellRangeAddress(0, 1, 0, newColumns.length - 1));
			Cell cell0 = headerRow0.createCell(0);
			cell0.setCellValue(" Accidental Insurance Numbers of  Working Employees");
			cell0.setCellStyle(headerCellStyle1);
		} else if (status.equalsIgnoreCase("DE")) {
			sheet.addMergedRegion(new CellRangeAddress(0, 1, 0, newColumns.length - 1));
			Cell cell0 = headerRow0.createCell(0);
			cell0.setCellValue(" Accidental Insurance Numbers of  Former Employees");
			cell0.setCellStyle(headerCellStyle1);
		}

		Row headerRow1 = sheet.createRow(2);
		// Creating cells
		for (int i = 0; i < newColumns.length; i++) {
			Cell cell = headerRow1.createCell(i);
			cell.setCellValue(newColumns[i].toString());
			cell.setCellStyle(headerCellStyle2);
		}

		if (reportAcInsDTOList.size() > 0) {

			int rowNum = 3;
			for (EmployeeStatuaryDTO employeeStatuaryDTO : reportAcInsDTOList) {
				Row row = sheet.createRow(rowNum++);

				row.createCell(0).setCellValue(employeeStatuaryDTO.getEmpAcCode());
				row.createCell(1).setCellValue(employeeStatuaryDTO.getEmpAcName());

				row.createCell(2).setCellValue(employeeStatuaryDTO.getAccidentalInsNo());
				Cell datePSIssue = row.createCell(3);
				datePSIssue.setCellValue(employeeStatuaryDTO.getAcInsIssueDate());
				datePSIssue.setCellStyle(dateCellStyle);
				Cell datePSExpiry = row.createCell(4);
				datePSExpiry.setCellValue(employeeStatuaryDTO.getAcInsExpiryDate());
				datePSExpiry.setCellStyle(dateCellStyle);

			}
		} else {

			Row row = sheet.createRow(3);

			sheet.addMergedRegion(new CellRangeAddress(3, 6, 0, newColumns.length - 1));
			Cell cell = row.createCell(0);
			cell.setCellValue(" --  Data Not Available -- ");
			cell.setCellStyle(headerCellStyle1);

		}

		// Resize columns to fit the content size
		for (int i = 0; i < newColumns.length; i++) {
			sheet.autoSizeColumn(i);
		}
		return workbook;
	}

	/**
	 * @author ${Nisha Parveen}
	 * @param activeStatus
	 *
	 */
	public static Workbook familyDetailsReportWriter(Object[] newColumns, Map<String, List<EmployeeFamilyDTO>> map,
			String status) {

		Workbook workbook = new XSSFWorkbook();

		CreationHelper createHelper = workbook.getCreationHelper();

		// Create a Sheet
		Sheet sheet = workbook.createSheet("Family Details");

		// Create a Font for styling header cells
		Font headerFont = workbook.createFont();
		headerFont.setBold(true);
		headerFont.setFontHeightInPoints((short) 12);
		headerFont.setColor(IndexedColors.BLACK.getIndex());

		Font rowFont = workbook.createFont();
		// rowFont.setBold(true);
		rowFont.setFontHeightInPoints((short) 11);
		rowFont.setColor(IndexedColors.BLACK.getIndex());

		// Create a CellStyle with the font
		CellStyle headerCellStyle1 = workbook.createCellStyle();
		headerCellStyle1.setFont(headerFont);
		headerCellStyle1.setAlignment(HorizontalAlignment.LEFT);
		headerCellStyle1.setVerticalAlignment(VerticalAlignment.CENTER);
		// headerCellStyle1.setFillPattern(FillPatternType.FINE_DOTS);

		CellStyle headerCellStyle2 = workbook.createCellStyle();
		headerCellStyle2.setFont(headerFont);
		headerCellStyle2.setAlignment(HorizontalAlignment.CENTER);
		headerCellStyle2.setVerticalAlignment(VerticalAlignment.CENTER);
		headerCellStyle2.setFillPattern(FillPatternType.FINE_DOTS);

		CellStyle rowCellStyle = workbook.createCellStyle();
		rowCellStyle.setFont(rowFont);
		rowCellStyle.setAlignment(HorizontalAlignment.CENTER);
		rowCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);

		// Create Cell Style for formatting Date
		CellStyle dateCellStyle = workbook.createCellStyle();
		dateCellStyle.setDataFormat(createHelper.createDataFormat().getFormat("dd-MMM-yyyy"));
		dateCellStyle.setAlignment(HorizontalAlignment.CENTER);
		dateCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);

		// creating row
		Row headerRow0 = sheet.createRow(0);

		if (status.equalsIgnoreCase("null")) {
			sheet.addMergedRegion(new CellRangeAddress(0, 1, 0, newColumns.length - 1));
			Cell cell0 = headerRow0.createCell(0);
			cell0.setCellValue("Family Details of an Employee");
			cell0.setCellStyle(headerCellStyle1);
		} else if (status.equalsIgnoreCase("AC")) {
			sheet.addMergedRegion(new CellRangeAddress(0, 1, 0, newColumns.length - 1));
			Cell cell0 = headerRow0.createCell(0);
			cell0.setCellValue(" Family Details of  Working Employees");
			cell0.setCellStyle(headerCellStyle1);
		} else if (status.equalsIgnoreCase("DE")) {
			sheet.addMergedRegion(new CellRangeAddress(0, 1, 0, newColumns.length - 1));
			Cell cell0 = headerRow0.createCell(0);
			cell0.setCellValue(" Family Details of  Former Employees");
			cell0.setCellStyle(headerCellStyle1);
		}

		Row headerRow1 = sheet.createRow(2);
		// Creating cells
		for (int i = 0; i < newColumns.length; i++) {
			Cell cell = headerRow1.createCell(i);
			cell.setCellValue(newColumns[i].toString());
			cell.setCellStyle(headerCellStyle2);
		}

		int rowNum = 3;

		for (Map.Entry<String, List<EmployeeFamilyDTO>> entry : map.entrySet()) {

			List<EmployeeFamilyDTO> employeeFamilyList = entry.getValue();

			if (employeeFamilyList.size() > 0) {

				int num = employeeFamilyList.size();
				int size = 0;

				for (EmployeeFamilyDTO employeeFamilyDTO : employeeFamilyList) {

					// int rowCount =rowNum;
					Row row = sheet.createRow(rowNum);

					if (num == 1) {

						Cell cel0 = row.createCell(0);
						cel0.setCellValue(employeeFamilyDTO.getEmpCode());
						cel0.setCellStyle(rowCellStyle);

						Cell cel1 = row.createCell(1);
						cel1.setCellValue(employeeFamilyDTO.getEmpName());
						cel1.setCellStyle(rowCellStyle);

						// row.createCell(0).setCellValue(employeeFamilyDTO.getEmpCode());
						// row.createCell(1).setCellValue(employeeFamilyDTO.getEmpName());

					} else if (size++ == 0) {

						Cell cell1 = row.createCell(0);
						sheet.addMergedRegion(new CellRangeAddress(rowNum, (rowNum + num) - 1, 0, 0));
						cell1.setCellValue(employeeFamilyDTO.getEmpCode());
						cell1.setCellStyle(rowCellStyle);

						Cell cell2 = row.createCell(1);
						sheet.addMergedRegion(new CellRangeAddress(rowNum, (rowNum + num) - 1, 1, 1));
						cell2.setCellValue(employeeFamilyDTO.getEmpName());
						cell2.setCellStyle(rowCellStyle);
					}

					Cell cel2 = row.createCell(2);
					cel2.setCellValue(employeeFamilyDTO.getName());
					cel2.setCellStyle(rowCellStyle);

					Cell cel3 = row.createCell(3);
					cel3.setCellValue(employeeFamilyDTO.getRelation());
					cel3.setCellStyle(rowCellStyle);

					Cell cel4 = row.createCell(4);
					cel4.setCellValue(employeeFamilyDTO.getQualificationId());
					cel4.setCellStyle(rowCellStyle);

					Cell cel5 = row.createCell(5);
					cel5.setCellValue(employeeFamilyDTO.getOccupations());
					cel5.setCellStyle(rowCellStyle);

					// row.createCell(2).setCellValue(employeeFamilyDTO.getName());
					// row.createCell(3).setCellValue(employeeFamilyDTO.getRelation());
					// row.createCell(4).setCellValue(employeeFamilyDTO.getQualificationId());
					// row.createCell(5).setCellValue(employeeFamilyDTO.getOccupations());
					Cell datePSIssue = row.createCell(6);
					datePSIssue.setCellValue(employeeFamilyDTO.getDob());
					datePSIssue.setCellStyle(dateCellStyle);

					Cell cel7 = row.createCell(7);
					cel7.setCellValue(employeeFamilyDTO.getContactMobile());
					cel7.setCellStyle(rowCellStyle);
					// row.createCell(7).setCellValue(employeeFamilyDTO.getContactMobile());

					rowNum++;
				}
			} else if (employeeFamilyList.isEmpty() || map.isEmpty()) {

				Row row = sheet.createRow(3);

				sheet.addMergedRegion(new CellRangeAddress(3, 6, 0, newColumns.length - 1));
				Cell cell = row.createCell(0);
				cell.setCellValue(" --  Data Not Available -- ");
				cell.setCellStyle(headerCellStyle1);

			}

		}

		// Resize columns to fit the content size
		for (int i = 0; i < newColumns.length; i++) {
			sheet.autoSizeColumn(i);
		}
		return workbook;
	}

	/**
	 * @author ${Nisha Parveen}
	 * @param status
	 *
	 */
	public static Workbook educationalDetailsReportWriter(String[] newColumns,
			Map<String, List<EmployeeEducationDTO>> map, String status) {

		Workbook workbook = new XSSFWorkbook();

		CreationHelper createHelper = workbook.getCreationHelper();

		// Create a Sheet
		Sheet sheet = workbook.createSheet("Educational Details");

		// Create a Font for styling header cells
		Font headerFont = workbook.createFont();
		headerFont.setBold(true);
		headerFont.setFontHeightInPoints((short) 12);
		headerFont.setColor(IndexedColors.BLACK.getIndex());

		Font rowFont = workbook.createFont();
		// rowFont.setBold(true);
		rowFont.setFontHeightInPoints((short) 11);
		rowFont.setColor(IndexedColors.BLACK.getIndex());

		// Create a CellStyle with the font
		CellStyle headerCellStyle1 = workbook.createCellStyle();
		headerCellStyle1.setFont(headerFont);
		headerCellStyle1.setAlignment(HorizontalAlignment.LEFT);
		headerCellStyle1.setVerticalAlignment(VerticalAlignment.CENTER);

		CellStyle headerCellStyle2 = workbook.createCellStyle();
		headerCellStyle2.setFont(headerFont);
		headerCellStyle2.setAlignment(HorizontalAlignment.CENTER);
		headerCellStyle2.setVerticalAlignment(VerticalAlignment.CENTER);
		headerCellStyle2.setFillPattern(FillPatternType.FINE_DOTS);

		CellStyle rowCellStyle = workbook.createCellStyle();
		rowCellStyle.setFont(rowFont);
		rowCellStyle.setAlignment(HorizontalAlignment.CENTER);
		rowCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);

		// Create Cell Style for formatting Date
		CellStyle dateCellStyle = workbook.createCellStyle();
		dateCellStyle.setDataFormat(createHelper.createDataFormat().getFormat("dd-MMM-yyyy"));
		dateCellStyle.setAlignment(HorizontalAlignment.CENTER);
		dateCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);

		// creating row
		Row headerRow0 = sheet.createRow(0);

		if (status.equalsIgnoreCase("null")) {
			sheet.addMergedRegion(new CellRangeAddress(0, 1, 0, newColumns.length - 1));
			Cell cell0 = headerRow0.createCell(0);
			cell0.setCellValue("Educational Details of an Employee");
			cell0.setCellStyle(headerCellStyle1);
		} else if (status.equalsIgnoreCase("AC")) {
			sheet.addMergedRegion(new CellRangeAddress(0, 1, 0, newColumns.length - 1));
			Cell cell0 = headerRow0.createCell(0);
			cell0.setCellValue(" Educational Details of  Working Employees");
			cell0.setCellStyle(headerCellStyle1);
		} else if (status.equalsIgnoreCase("DE")) {
			sheet.addMergedRegion(new CellRangeAddress(0, 1, 0, newColumns.length - 1));
			Cell cell0 = headerRow0.createCell(0);
			cell0.setCellValue(" Educational Details of  Former Employees");
			cell0.setCellStyle(headerCellStyle1);
		}

		Row headerRow1 = sheet.createRow(2);
		// Creating cells
		for (int i = 0; i < newColumns.length; i++) {
			Cell cell = headerRow1.createCell(i);
			cell.setCellValue(newColumns[i].toString());
			cell.setCellStyle(headerCellStyle2);
		}

		int rowNum = 3;

		for (Map.Entry<String, List<EmployeeEducationDTO>> entry : map.entrySet()) {

			List<EmployeeEducationDTO> employeeEducationList = entry.getValue();
			if (employeeEducationList.size() > 0) {
				int num = employeeEducationList.size();
				int size = 0;

				for (EmployeeEducationDTO employeeEducationDTO : employeeEducationList) {

					Row row = sheet.createRow(rowNum);

					if (num == 1) {

						Cell cel0 = row.createCell(0);
						cel0.setCellValue(employeeEducationDTO.getEmpCode());
						cel0.setCellStyle(rowCellStyle);

						Cell cel1 = row.createCell(1);
						cel1.setCellValue(employeeEducationDTO.getEmpName());
						cel1.setCellStyle(rowCellStyle);

					} else if (size++ == 0) {

						Cell cell1 = row.createCell(0);
						sheet.addMergedRegion(new CellRangeAddress(rowNum, (rowNum + num) - 1, 0, 0));
						cell1.setCellValue(employeeEducationDTO.getEmpCode());
						cell1.setCellStyle(rowCellStyle);

						Cell cell2 = row.createCell(1);
						sheet.addMergedRegion(new CellRangeAddress(rowNum, (rowNum + num) - 1, 1, 1));
						cell2.setCellValue(employeeEducationDTO.getEmpName());
						cell2.setCellStyle(rowCellStyle);
					}

					Cell cel2 = row.createCell(2);
					cel2.setCellValue(employeeEducationDTO.getQualificationId());
					cel2.setCellStyle(rowCellStyle);

					Cell cel3 = row.createCell(3);
					cel3.setCellValue(employeeEducationDTO.getDegreeName());
					cel3.setCellStyle(rowCellStyle);

					Cell cel4 = row.createCell(4);
					cel4.setCellValue(employeeEducationDTO.getNameOfInstitution());
					cel4.setCellStyle(rowCellStyle);

					Cell cel5 = row.createCell(5);
					cel5.setCellValue(employeeEducationDTO.getNameOfBoard());
					cel5.setCellStyle(rowCellStyle);

					Cell cel6 = row.createCell(6);
					cel6.setCellValue(
							employeeEducationDTO.getMarksPer() != null ? employeeEducationDTO.getMarksPer().toString()
									: BigDecimal.ZERO.toString());
					cel6.setCellStyle(rowCellStyle);

					Cell cel7 = row.createCell(7);
					cel7.setCellValue(employeeEducationDTO.getPassoutYear());
					cel7.setCellStyle(rowCellStyle);

					Cell cel8 = row.createCell(8);
					cel8.setCellValue(employeeEducationDTO.getRegularCorrespondance());
					cel8.setCellStyle(rowCellStyle);

					rowNum++;
				}
			} else if (employeeEducationList.isEmpty() || map.isEmpty()) {

				Row row = sheet.createRow(3);

				sheet.addMergedRegion(new CellRangeAddress(3, 6, 0, newColumns.length - 1));
				Cell cell = row.createCell(0);
				cell.setCellValue(" --  Data Not Available -- ");
				cell.setCellStyle(headerCellStyle1);

			}

		}

		// Resize columns to fit the content size
		for (int i = 0; i < newColumns.length; i++) {
			sheet.autoSizeColumn(i);
		}

		return workbook;
	}

	// Esic

	public static Workbook esicReportWriter(String[] columns, List<EsiInfoDTO> esiInfoDTOList, String status) {

		Workbook workbook = new XSSFWorkbook();
		CreationHelper createHelper = workbook.getCreationHelper();
		// Create a Sheet
		Sheet sheet = workbook.createSheet("ESIC Numbers");

		// Create a Font for styling header cells
		Font headerFont = workbook.createFont();
		headerFont.setBold(true);
		headerFont.setFontHeightInPoints((short) 12);
		headerFont.setColor(IndexedColors.BLACK.getIndex());

		Font rowFont = workbook.createFont();
		rowFont.setBold(true);
		rowFont.setFontHeightInPoints((short) 14);
		rowFont.setColor(IndexedColors.BLACK.getIndex());

		// Create a CellStyle with the font
		CellStyle headerCellStyle1 = workbook.createCellStyle();
		headerCellStyle1.setFont(headerFont);
		headerCellStyle1.setAlignment(HorizontalAlignment.CENTER);
		headerCellStyle1.setVerticalAlignment(VerticalAlignment.CENTER);

		CellStyle headerCellStyle2 = workbook.createCellStyle();
		headerCellStyle2.setFont(headerFont);
		headerCellStyle2.setAlignment(HorizontalAlignment.CENTER);
		headerCellStyle2.setVerticalAlignment(VerticalAlignment.CENTER);
		headerCellStyle2.setFillBackgroundColor(IndexedColors.LIGHT_CORNFLOWER_BLUE.getIndex());
		headerCellStyle2.setFillPattern(FillPatternType.FINE_DOTS);

		CellStyle rowCellStyle = workbook.createCellStyle();
		rowCellStyle.setFont(rowFont);

		// Create Cell Style for formatting Date
		CellStyle dateCellStyle = workbook.createCellStyle();
		dateCellStyle.setAlignment(HorizontalAlignment.CENTER);
		dateCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
		dateCellStyle.setDataFormat(createHelper.createDataFormat().getFormat("dd-mmm-yyyy"));

		// creating row
		Row headerRow0 = sheet.createRow(0);

		if (status.equalsIgnoreCase("null")) {
			sheet.addMergedRegion(new CellRangeAddress(0, 1, 0, columns.length - 1));
			Cell cell0 = headerRow0.createCell(0);
			cell0.setCellValue("ESIC Number of an Employee");
			cell0.setCellStyle(headerCellStyle1);
		} else if (status.equalsIgnoreCase("AC")) {
			sheet.addMergedRegion(new CellRangeAddress(0, 1, 0, columns.length - 1));
			Cell cell0 = headerRow0.createCell(0);
			cell0.setCellValue("ESIC Numbers of  Working Employees");
			cell0.setCellStyle(headerCellStyle1);
		} else if (status.equalsIgnoreCase("DE")) {
			sheet.addMergedRegion(new CellRangeAddress(0, 1, 0, columns.length - 1));
			Cell cell0 = headerRow0.createCell(0);
			cell0.setCellValue("ESIC Numbers of  Former Employees");
			cell0.setCellStyle(headerCellStyle1);
		}

		Row headerRow1 = sheet.createRow(2);
		// Creating cells
		for (int i = 0; i < columns.length; i++) {
			Cell cell = headerRow1.createCell(i);
			cell.setCellValue(columns[i].toString());
			cell.setCellStyle(headerCellStyle2);
		}

		if (esiInfoDTOList.size() > 0) {

			int rowNum = 3;

			for (EsiInfoDTO esiInfoDTO : esiInfoDTOList) {

				Row row = sheet.createRow(rowNum++);

				row.createCell(0).setCellValue(esiInfoDTO.getEmployeeCode());

				row.createCell(1).setCellValue(esiInfoDTO.getEmployeeName());

				row.createCell(2).setCellValue(esiInfoDTO.getEsicNumber());

				Cell dateEsicIssue = row.createCell(3);

				dateEsicIssue.setCellValue(esiInfoDTO.getEsicIssueDate());
				dateEsicIssue.setCellStyle(dateCellStyle);

				Cell dateEsicExpiry = row.createCell(4);

				dateEsicExpiry.setCellValue(esiInfoDTO.getEsicExpiryDate());
				dateEsicExpiry.setCellStyle(dateCellStyle);

			}
		} else {

			Row row = sheet.createRow(3);

			sheet.addMergedRegion(new CellRangeAddress(3, 6, 0, columns.length - 1));
			Cell cell = row.createCell(0);
			cell.setCellValue(" --  Data Not Available -- ");
			cell.setCellStyle(headerCellStyle1);

		}

		// Resize columns to fit the content size in Exel Sheet
		for (int i = 0; i < columns.length; i++) {
			sheet.autoSizeColumn(i);
		}
		return workbook;

	}

	// Med Ins
	public static Workbook medicalInsuranceReportWriter(String[] columns,
			List<EmployeeStatuaryDTO> employeeStatuaryDTOList, String status) {

		Workbook workbook = new XSSFWorkbook();

		CreationHelper createHelper = workbook.getCreationHelper();

		// Create a Sheet
		Sheet sheet = workbook.createSheet("Medical Insurance NO.");

		// Create a Font for styling header cells
		Font headerFont = workbook.createFont();
		headerFont.setBold(true);
		headerFont.setFontHeightInPoints((short) 12);
		headerFont.setColor(IndexedColors.BLACK.getIndex());

		Font rowFont = workbook.createFont();
		rowFont.setBold(true);
		rowFont.setFontHeightInPoints((short) 14);
		rowFont.setColor(IndexedColors.BLACK.getIndex());

		CellStyle headerCellStyle1 = workbook.createCellStyle();
		headerCellStyle1.setFont(headerFont);
		headerCellStyle1.setAlignment(HorizontalAlignment.CENTER);
		headerCellStyle1.setVerticalAlignment(VerticalAlignment.CENTER);

		CellStyle headerCellStyle2 = workbook.createCellStyle();
		headerCellStyle2.setFont(headerFont);
		headerCellStyle2.setAlignment(HorizontalAlignment.CENTER);
		headerCellStyle2.setVerticalAlignment(VerticalAlignment.CENTER);
		headerCellStyle2.setFillBackgroundColor(IndexedColors.LIGHT_CORNFLOWER_BLUE.getIndex());
		headerCellStyle2.setFillPattern(FillPatternType.FINE_DOTS);

		CellStyle rowCellStyle = workbook.createCellStyle();
		rowCellStyle.setFont(rowFont);

		// Create Cell Style for formatting Date
		CellStyle dateCellStyle = workbook.createCellStyle();
		dateCellStyle.setAlignment(HorizontalAlignment.CENTER);
		dateCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
		dateCellStyle.setDataFormat(createHelper.createDataFormat().getFormat("dd-mmm-yyyy"));

		// int col = columns.length - 1;
		// creating row
		Row headerRow0 = sheet.createRow(0);

		if (status.equalsIgnoreCase("null")) {
			sheet.addMergedRegion(new CellRangeAddress(0, 1, 0, columns.length - 1));
			Cell cell0 = headerRow0.createCell(0);
			cell0.setCellValue("Medical Insurance Number of an Employee");
			cell0.setCellStyle(headerCellStyle1);
		} else if (status.equalsIgnoreCase("AC")) {
			sheet.addMergedRegion(new CellRangeAddress(0, 1, 0, columns.length - 1));
			Cell cell0 = headerRow0.createCell(0);
			cell0.setCellValue("Medical Insurance Numbers of  Working Employees");
			cell0.setCellStyle(headerCellStyle1);
		} else if (status.equalsIgnoreCase("DE")) {
			sheet.addMergedRegion(new CellRangeAddress(0, 1, 0, columns.length - 1));
			Cell cell0 = headerRow0.createCell(0);
			cell0.setCellValue("Medical Insurance Numbers of  Former Employees");
			cell0.setCellStyle(headerCellStyle1);
		}

		Row headerRow1 = sheet.createRow(2);

		for (int i = 0; i < columns.length; i++) {
			Cell cell = headerRow1.createCell(i);
			cell.setCellValue(columns[i].toString());
			cell.setCellStyle(headerCellStyle2);
		}

		if (employeeStatuaryDTOList.size() > 0) {

			int rowNum = 3;
			for (EmployeeStatuaryDTO EmployeeStatuaryDTO : employeeStatuaryDTOList) {

				Row row = sheet.createRow(rowNum++);

				row.createCell(0).setCellValue(EmployeeStatuaryDTO.getEmpCode());

				row.createCell(1).setCellValue(EmployeeStatuaryDTO.getEmpName());

				row.createCell(2).setCellValue(EmployeeStatuaryDTO.getStatuaryNumber());

				Cell dateMinIssue = row.createCell(3);

				dateMinIssue.setCellValue(EmployeeStatuaryDTO.getEffectiveStartDate());
				dateMinIssue.setCellStyle(dateCellStyle);

				Cell dateMinExpiry = row.createCell(4);

				dateMinExpiry.setCellValue(EmployeeStatuaryDTO.getEffectiveEndDate());
				dateMinExpiry.setCellStyle(dateCellStyle);

			}
		} else {

			Row row = sheet.createRow(3);

			sheet.addMergedRegion(new CellRangeAddress(3, 6, 0, columns.length - 1));
			Cell cell = row.createCell(0);
			cell.setCellValue(" --  Data Not Available -- ");
			cell.setCellStyle(headerCellStyle1);

		}

		// Resize columns to fit the content size in Exel Sheet
		for (int i = 0; i < columns.length; i++) {
			sheet.autoSizeColumn(i);
		}
		return workbook;

	}

	// Professional Reports

	public static Workbook profDetailsReportWriter(String[] columns, Map<String, List<ProfessionalInformation>> map,
			String status) {

		Workbook workbook = new XSSFWorkbook();
		CreationHelper createHelper = workbook.getCreationHelper();
		// Create a Sheet
		Sheet sheet = workbook.createSheet("Professional Details");

		// Create a Font for styling header cells
		Font headerFont = workbook.createFont();
		headerFont.setBold(true);
		headerFont.setFontHeightInPoints((short) 12);
		headerFont.setColor(IndexedColors.BLACK.getIndex());

		Font rowFont = workbook.createFont();
		rowFont.setFontHeightInPoints((short) 11);
		rowFont.setColor(IndexedColors.BLACK.getIndex());

		CellStyle headerCellStyle11 = workbook.createCellStyle();
		headerCellStyle11.setFont(headerFont);
		headerCellStyle11.setAlignment(HorizontalAlignment.CENTER);
		headerCellStyle11.setVerticalAlignment(VerticalAlignment.CENTER);
		headerCellStyle11.setFillBackgroundColor(IndexedColors.LIGHT_CORNFLOWER_BLUE.getIndex());
		headerCellStyle11.setFillPattern(FillPatternType.FINE_DOTS);

		// Create a CellStyle with the font
		CellStyle headerCellStyle1 = workbook.createCellStyle();
		headerCellStyle1.setFont(headerFont);
		headerCellStyle1.setAlignment(HorizontalAlignment.LEFT);
		headerCellStyle1.setVerticalAlignment(VerticalAlignment.CENTER);

		// To Get Data In Center For Row
		CellStyle rowCellStyle = workbook.createCellStyle();
		rowCellStyle.setFont(rowFont);
		rowCellStyle.setAlignment(HorizontalAlignment.CENTER);
		rowCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);

		CellStyle dateCellStyle = workbook.createCellStyle();
		dateCellStyle.setDataFormat(createHelper.createDataFormat().getFormat("dd-mmm-yyyy"));
		dateCellStyle.setAlignment(HorizontalAlignment.CENTER);
		dateCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);

		Row headerRow0 = sheet.createRow(0);

		if (status.equalsIgnoreCase("null")) {
			sheet.addMergedRegion(new CellRangeAddress(0, 1, 0, columns.length - 1));
			Cell cell0 = headerRow0.createCell(0);
			cell0.setCellValue("Professional Details of an Employee");
			cell0.setCellStyle(headerCellStyle1);
		} else if (status.equalsIgnoreCase("AC")) {
			sheet.addMergedRegion(new CellRangeAddress(0, 1, 0, columns.length - 1));
			Cell cell0 = headerRow0.createCell(0);
			cell0.setCellValue("Professional Details of  Working Employees");
			cell0.setCellStyle(headerCellStyle1);
		} else if (status.equalsIgnoreCase("DE")) {
			sheet.addMergedRegion(new CellRangeAddress(0, 1, 0, columns.length - 1));
			Cell cell0 = headerRow0.createCell(0);
			cell0.setCellValue("Professional Details of  Former Employees");
			cell0.setCellStyle(headerCellStyle1);
		}

		Row headerRow1 = sheet.createRow(2);

		for (int i = 0; i < columns.length; i++) {
			Cell cell = headerRow1.createCell(i);
			cell.setCellValue(columns[i].toString());
			cell.setCellStyle(headerCellStyle11);
		}

		int rowNum = 3;

		if (!map.isEmpty()) {
			for (Map.Entry<String, List<ProfessionalInformation>> entry : map.entrySet()) {

				List<ProfessionalInformation> profDetailsList = entry.getValue();

				if (profDetailsList.size() > 0) {
					int size = profDetailsList.size();
					int temp = 0;

					for (ProfessionalInformation professionalInformationDTO : profDetailsList) {

						Row row = sheet.createRow(rowNum);

						if (size == 1) {

							Cell cell1 = row.createCell(0);
							cell1.setCellValue(professionalInformationDTO.getEmpCode());
							cell1.setCellStyle(rowCellStyle);

							Cell cell2 = row.createCell(1);
							cell2.setCellValue(professionalInformationDTO.getEmployeeName());
							cell2.setCellStyle(rowCellStyle);

						} else {
							if (temp++ == 0) {

								Cell cell1 = row.createCell(0);
								sheet.addMergedRegion(new CellRangeAddress(rowNum, (rowNum + size) - 1, 0, 0));
								cell1.setCellValue(professionalInformationDTO.getEmpCode());
								cell1.setCellStyle(rowCellStyle);

								Cell cell2 = row.createCell(1);
								sheet.addMergedRegion(new CellRangeAddress(rowNum, (rowNum + size) - 1, 1, 1));
								cell2.setCellValue(professionalInformationDTO.getEmployeeName());
								cell2.setCellStyle(rowCellStyle);
							}
						}

						Cell cell2 = row.createCell(2);
						cell2.setCellValue(professionalInformationDTO.getOrganizationName());
						cell2.setCellStyle(rowCellStyle);

						Cell cell3 = row.createCell(3);
						cell3.setCellValue(professionalInformationDTO.getDateFrom());
						cell3.setCellStyle(dateCellStyle);

						Cell cell4 = row.createCell(4);
						cell4.setCellValue(professionalInformationDTO.getDateTo());
						cell4.setCellStyle(dateCellStyle);

						Cell cell5 = row.createCell(5);
						cell5.setCellValue(professionalInformationDTO.getDesignation());
						cell5.setCellStyle(rowCellStyle);

						Cell cell6 = row.createCell(6);
						cell6.setCellValue(professionalInformationDTO.getReportingTo());
						cell6.setCellStyle(rowCellStyle);

						Cell cell7 = row.createCell(7);
						cell7.setCellValue(professionalInformationDTO.getReportingContact());
						cell7.setCellStyle(rowCellStyle);

						Cell cell8 = row.createCell(8);
						cell8.setCellValue((professionalInformationDTO.getAnnualSalary() != null
								? professionalInformationDTO.getAnnualSalary().toString()
								: " ------ ")); // BigDecimal.ZERO.toString())
						cell8.setCellStyle(rowCellStyle);

						Cell cell9 = row.createCell(9);
						cell9.setCellValue(professionalInformationDTO.getReasonForChange());
						cell9.setCellStyle(rowCellStyle);

						rowNum++;

					}
				} else {

					Row row = sheet.createRow(3);

					sheet.addMergedRegion(new CellRangeAddress(3, 6, 0, columns.length - 1));
					Cell cell = row.createCell(0);
					cell.setCellValue(" --  Data Not Available -- ");
					cell.setCellStyle(rowCellStyle);

				}

			}
		} else {

			Row row = sheet.createRow(3);

			sheet.addMergedRegion(new CellRangeAddress(3, 6, 0, columns.length - 1));
			Cell cell = row.createCell(0);
			cell.setCellValue(" --  Data Not Available -- ");
			cell.setCellStyle(rowCellStyle);

		}

		// Resize columns to fit the content size
		for (int i = 0; i < columns.length; i++) {
			sheet.autoSizeColumn(i);
		}
		return workbook;
	}

	// Nominee Detalis Report

	public static Workbook nomineeDetailsReportWriter(String[] columns, Map<String, List<EmployeeStatuaryDTO>> map,
			String status) {

		Workbook workbook = new XSSFWorkbook();
		CreationHelper createHelper = workbook.getCreationHelper();
		// Create a Sheet
		Sheet sheet = workbook.createSheet("Nominee Details");

		// Create a Font for styling header cells
		Font headerFont = workbook.createFont();
		headerFont.setBold(true);
		headerFont.setFontHeightInPoints((short) 12);
		headerFont.setColor(IndexedColors.BLACK.getIndex());

		Font rowFont = workbook.createFont();
		rowFont.setFontHeightInPoints((short) 11);
		rowFont.setColor(IndexedColors.BLACK.getIndex());

		CellStyle headerCellStyle11 = workbook.createCellStyle();
		headerCellStyle11.setFont(headerFont);
		headerCellStyle11.setAlignment(HorizontalAlignment.CENTER);
		headerCellStyle11.setVerticalAlignment(VerticalAlignment.CENTER);
		headerCellStyle11.setFillBackgroundColor(IndexedColors.LIGHT_CORNFLOWER_BLUE.getIndex());
		headerCellStyle11.setFillPattern(FillPatternType.FINE_DOTS);

		// Create a CellStyle with the font
		CellStyle headerCellStyle1 = workbook.createCellStyle();
		headerCellStyle1.setFont(headerFont);
		headerCellStyle1.setAlignment(HorizontalAlignment.LEFT);
		headerCellStyle1.setVerticalAlignment(VerticalAlignment.CENTER);

		// To Get Data In Center For Row
		CellStyle rowCellStyle = workbook.createCellStyle();
		rowCellStyle.setFont(rowFont);
		rowCellStyle.setAlignment(HorizontalAlignment.CENTER);
		rowCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);

		CellStyle dateCellStyle = workbook.createCellStyle();
		dateCellStyle.setDataFormat(createHelper.createDataFormat().getFormat("dd-mmm-yyyy"));
		dateCellStyle.setAlignment(HorizontalAlignment.CENTER);
		dateCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);

		Row headerRow0 = sheet.createRow(0);

		if (status.equalsIgnoreCase("null")) {
			sheet.addMergedRegion(new CellRangeAddress(0, 1, 0, columns.length - 1));
			Cell cell0 = headerRow0.createCell(0);
			cell0.setCellValue(" Nominee Details of an Employee");
			cell0.setCellStyle(headerCellStyle1);
		} else if (status.equalsIgnoreCase("AC")) {
			sheet.addMergedRegion(new CellRangeAddress(0, 1, 0, columns.length - 1));
			Cell cell0 = headerRow0.createCell(0);
			cell0.setCellValue(" Nominee Details of  Working Employees");
			cell0.setCellStyle(headerCellStyle1);
		} else if (status.equalsIgnoreCase("DE")) {
			sheet.addMergedRegion(new CellRangeAddress(0, 1, 0, columns.length - 1));
			Cell cell0 = headerRow0.createCell(0);
			cell0.setCellValue(" Nominee Details of  Former Employees");
			cell0.setCellStyle(headerCellStyle1);
		}

		Row headerRow1 = sheet.createRow(2);

		for (int i = 0; i < columns.length; i++) {
			Cell cell = headerRow1.createCell(i);
			cell.setCellValue(columns[i].toString());
			cell.setCellStyle(headerCellStyle11);
		}

		int rowNum = 3;

		if (!map.isEmpty()) {
			for (Map.Entry<String, List<EmployeeStatuaryDTO>> entry : map.entrySet()) {

				List<EmployeeStatuaryDTO> nomineeDetailsList = entry.getValue();
				if (nomineeDetailsList.size() > 0) {

					int size = nomineeDetailsList.size();
					int temp = 0;

					for (EmployeeStatuaryDTO employeeStatuaryDTO : nomineeDetailsList) {

						Row row = sheet.createRow(rowNum);

						if (size == 1) {

							Cell cell1 = row.createCell(0);
							cell1.setCellValue(employeeStatuaryDTO.getEmpCode());
							cell1.setCellStyle(rowCellStyle);

							Cell cell2 = row.createCell(1);
							cell2.setCellValue(employeeStatuaryDTO.getEmpName());
							cell2.setCellStyle(rowCellStyle);

						} else {
							if (temp++ == 0) {

								Cell cell1 = row.createCell(0);
								sheet.addMergedRegion(new CellRangeAddress(rowNum, (rowNum + size) - 1, 0, 0));
								cell1.setCellValue(employeeStatuaryDTO.getEmpCode());
								cell1.setCellStyle(rowCellStyle);

								Cell cell2 = row.createCell(1);
								sheet.addMergedRegion(new CellRangeAddress(rowNum, (rowNum + size) - 1, 1, 1));
								cell2.setCellValue(employeeStatuaryDTO.getEmpName());
								cell2.setCellStyle(rowCellStyle);
							}
						}

						Cell cell2 = row.createCell(2);
						cell2.setCellValue(employeeStatuaryDTO.getName());
						cell2.setCellStyle(rowCellStyle);

						Cell cell3 = row.createCell(3);
						cell3.setCellValue(employeeStatuaryDTO.getFamilyRelation());
						cell3.setCellStyle(rowCellStyle);

						Cell cell4 = row.createCell(4);
						cell4.setCellValue(employeeStatuaryDTO.getStatuaryType());
						cell4.setCellStyle(rowCellStyle);

						Cell cell5 = row.createCell(5);
						cell5.setCellValue(employeeStatuaryDTO.getContactMobile());
						cell5.setCellStyle(rowCellStyle);

						rowNum++;
					}

				} else {

					Row row = sheet.createRow(3);

					sheet.addMergedRegion(new CellRangeAddress(3, 6, 0, columns.length - 1));
					Cell cell = row.createCell(0);
					cell.setCellValue(" --  Data Not Available -- ");
					cell.setCellStyle(rowCellStyle);
				}
			}
		} else {

			Row row = sheet.createRow(3);

			sheet.addMergedRegion(new CellRangeAddress(3, 6, 0, columns.length - 1));
			Cell cell = row.createCell(0);
			cell.setCellValue(" --  Data Not Available -- ");
			cell.setCellStyle(rowCellStyle);

		}

		// Resize columns to fit the content size
		for (int i = 0; i < columns.length; i++) {
			sheet.autoSizeColumn(i);
		}
		return workbook;
	}

	public static Workbook employeeOnNoticePeriodReportWriter(String[] columns, List<EmployeeDTO> employeeDtoList,
			Date fDate, Date tDate) {
		Workbook workbook = new XSSFWorkbook(); // new HSSFWorkbook() for generating `.xls` file

		CreationHelper createHelper = workbook.getCreationHelper();

		Sheet sheet = workbook.createSheet("Employees Details who are in Notice Peiord");

		Font headerFont = workbook.createFont();
		headerFont.setBold(true);
		headerFont.setFontHeightInPoints((short) 12);
		headerFont.setColor(IndexedColors.BLACK.getIndex());

		CellStyle headerCellStyle = workbook.createCellStyle();
		headerCellStyle.setFont(headerFont);
		headerCellStyle.setAlignment(HorizontalAlignment.CENTER);
		headerCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
		headerCellStyle.setFillBackgroundColor(IndexedColors.LIGHT_CORNFLOWER_BLUE.getIndex());
		headerCellStyle.setFillPattern(FillPatternType.FINE_DOTS);

		CellStyle headerCellStyle11 = workbook.createCellStyle();
		headerCellStyle11.setFont(headerFont);
		headerCellStyle11.setAlignment(HorizontalAlignment.LEFT);
		headerCellStyle11.setVerticalAlignment(VerticalAlignment.CENTER);

		CellStyle headerCellStyle112 = workbook.createCellStyle();
		headerCellStyle112.setAlignment(HorizontalAlignment.LEFT);
		headerCellStyle112.setVerticalAlignment(VerticalAlignment.CENTER);

		CellStyle headerCellStyle113 = workbook.createCellStyle();
		headerCellStyle113.setFont(headerFont);
		headerCellStyle113.setAlignment(HorizontalAlignment.CENTER);
		headerCellStyle113.setVerticalAlignment(VerticalAlignment.CENTER);

		CellStyle cellStyle112 = workbook.createCellStyle();
		cellStyle112.setAlignment(HorizontalAlignment.RIGHT);
		cellStyle112.setVerticalAlignment(VerticalAlignment.CENTER);

		CellStyle cellStyleCentre = workbook.createCellStyle();
		cellStyleCentre.setAlignment(HorizontalAlignment.CENTER);
		cellStyleCentre.setVerticalAlignment(VerticalAlignment.CENTER);

		CellStyle headerCellStyleTotal = workbook.createCellStyle();
		headerCellStyleTotal.setFont(headerFont);
		headerCellStyleTotal.setAlignment(HorizontalAlignment.RIGHT);
		headerCellStyleTotal.setVerticalAlignment(VerticalAlignment.CENTER);

		CellStyle headerCellStyleCode = workbook.createCellStyle();
		headerCellStyleCode.setFont(headerFont);
		headerCellStyleCode.setAlignment(HorizontalAlignment.LEFT);
		headerCellStyleCode.setVerticalAlignment(VerticalAlignment.CENTER);

		Row row0 = sheet.createRow(0);
		sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 19));
		Cell cellCom = row0.createCell(0);
		cellCom.setCellValue("Employees Details who are in Notice Peiord");
		cellCom.setCellStyle(headerCellStyle11);

		CellStyle dateCellStyle = workbook.createCellStyle();
		dateCellStyle.setDataFormat(createHelper.createDataFormat().getFormat("dd-MM-yyyy"));
		CellStyle dateCellStyles = workbook.createCellStyle();
		dateCellStyles.setDataFormat(createHelper.createDataFormat().getFormat(""));

		String dateFrom = null;
		SimpleDateFormat s1 = new SimpleDateFormat("dd-MMM-yyyy");
		dateFrom = s1.format(fDate);

		String dateTo = null;
		SimpleDateFormat s2 = new SimpleDateFormat("dd-MMM-yyyy");
		dateTo = s2.format(tDate);

		Row headerRow1 = sheet.createRow(1);
		sheet.addMergedRegion(new CellRangeAddress(1, 1, 0, 19));
		Cell cell1 = headerRow1.createCell(0);
		cell1.setCellValue(dateFrom + " To " + dateTo);
		cell1.setCellStyle(headerCellStyle11);

		Row headerRow2 = sheet.createRow(2);

		for (int i = 0; i < columns.length; i++) {
			Cell cell = headerRow2.createCell(i);
			cell.setCellValue(columns[i].toString());
			cell.setCellStyle(headerCellStyle);
		}

		int rowNum = 3;
		if (employeeDtoList != null) {

			for (EmployeeDTO employeeDTO : employeeDtoList) {

				Row row = sheet.createRow(rowNum++);

				row.createCell(0).setCellValue(employeeDTO.getEmployeeCode());

				row.createCell(1).setCellValue(employeeDTO.getFirstName());
				if (employeeDTO.getMiddleName() != null)
					row.createCell(2).setCellValue(employeeDTO.getMiddleName());
				row.createCell(3).setCellValue(employeeDTO.getLastName());

				row.createCell(4).setCellValue(employeeDTO.getContactNo());
				row.createCell(5).setCellValue(employeeDTO.getEmailId());
				Cell cell11 = row.createCell(6);
				cell11.setCellValue(employeeDTO.getDateOfJoining());
				cell11.setCellStyle(dateCellStyle);

				row.createCell(7).setCellValue(employeeDTO.getJobLocation());
				row.createCell(8).setCellValue(employeeDTO.getShiftName());
				row.createCell(9).setCellValue(employeeDTO.getWeeklyOffPattern());
				row.createCell(10).setCellValue(employeeDTO.getDepartmentName());
				row.createCell(11).setCellValue(employeeDTO.getDesignationName());
				row.createCell(12).setCellValue(employeeDTO.getReportingEmployeeFirstName());

				row.createCell(13).setCellValue(employeeDTO.getGradesName());

				if (employeeDTO.getProbationDays() != null)
					row.createCell(14).setCellValue(employeeDTO.getProbationDays());

				if (employeeDTO.getNoticePeriodDays() != null)
					row.createCell(15).setCellValue(employeeDTO.getNoticePeriodDays());

				row.createCell(17).setCellValue(employeeDTO.getTimeContract());

				if (employeeDTO.getEmpType() != null)
					row.createCell(16).setCellValue(employeeDTO.getEmpType());

				Cell cell12 = row.createCell(18);
				cell12.setCellValue(employeeDTO.getResignationDate());
				cell12.setCellStyle(dateCellStyle);

				Cell cell13 = row.createCell(19);
				cell13.setCellValue(employeeDTO.getExitDate());
				cell13.setCellStyle(dateCellStyle);

			}
		}

		// Resize columns to fit the content size
		for (int i = 0; i < columns.length; i++) {
			sheet.autoSizeColumn(i);
		}

		return workbook;
	}

	public static void addCustomObjLangDetails(EmployeeLanguage prvObj, EmployeeLanguage employeeLanguage,
			HashMap<String, EmployeeLanguage> map) {

		prvObj = map.get(employeeLanguage.getEmployeeCode());

		if (prvObj.getEmployeeCode().equals(employeeLanguage.getEmployeeCode())
				&& !prvObj.getLangId().equals(employeeLanguage.getLangId())) {

			if (employeeLanguage.getLangId() == 5) {
				prvObj.sethRead(employeeLanguage.getLangRead());
				prvObj.sethSpeak(employeeLanguage.getLangSpeak());
				prvObj.sethWrite(employeeLanguage.getLangWrite());
			} else {

				prvObj.seteRead(employeeLanguage.getLangRead());
				prvObj.seteSpeak(employeeLanguage.getLangSpeak());
				prvObj.seteWrite(employeeLanguage.getLangWrite());

			}

		}

	}

	public static Workbook languageKnownStatusReportWriter(Long employeeId, String[] newColumns, String activeStatus,
			List<EmployeeLanguage> languageKnownDTOList) {

		Workbook workbook = new XSSFWorkbook();

		CreationHelper createHelper = workbook.getCreationHelper();

		// Create a Sheet
		Sheet sheet = workbook.createSheet("Language Known Status");

		// Create a Font for styling header cells
		Font headerFont = workbook.createFont();
		headerFont.setBold(true);
		headerFont.setFontHeightInPoints((short) 12);
		headerFont.setColor(IndexedColors.BLACK.getIndex());

		Font rowFont = workbook.createFont();
		rowFont.setBold(true);
		rowFont.setFontHeightInPoints((short) 14);
		rowFont.setColor(IndexedColors.BLACK.getIndex());

		// Create a CellStyle with the font
		CellStyle headerCellStyle1 = workbook.createCellStyle();
		headerCellStyle1.setFont(headerFont);
		headerCellStyle1.setAlignment(HorizontalAlignment.LEFT);
		headerCellStyle1.setVerticalAlignment(VerticalAlignment.CENTER);

		CellStyle headerCellStyle2 = workbook.createCellStyle();
		headerCellStyle2.setFont(headerFont);
		headerCellStyle2.setAlignment(HorizontalAlignment.CENTER);
		headerCellStyle2.setVerticalAlignment(VerticalAlignment.CENTER);

		CellStyle rowCellStyle = workbook.createCellStyle();
		rowCellStyle.setFont(rowFont);

		// Create Cell Style for formatting Date
		CellStyle dateCellStyle = workbook.createCellStyle();
		dateCellStyle.setDataFormat(createHelper.createDataFormat().getFormat("dd-MMM-yyyy"));

		int col = newColumns.length - 1;

		Row headerRow0 = sheet.createRow(0);

		if (activeStatus == null) {
			sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, col));
			Cell cell0 = headerRow0.createCell(0);
			cell0.setCellValue("Language known status of an Employee");
			cell0.setCellStyle(headerCellStyle1);
		} else if (activeStatus.equalsIgnoreCase("AC")) {
			sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, col));
			Cell cell0 = headerRow0.createCell(0);
			cell0.setCellValue("Language known status of  Working Employees");
			cell0.setCellStyle(headerCellStyle1);
		} else if (activeStatus.equalsIgnoreCase("DE")) {
			sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, col));
			Cell cell0 = headerRow0.createCell(0);
			cell0.setCellValue("Language known status of  Former Employees");
			cell0.setCellStyle(headerCellStyle1);
		}

		Row headerRow2 = sheet.createRow(1);
		Row headerRow3 = sheet.createRow(2);

		for (int i = 0; i <= 5; i++) {
			sheet.addMergedRegion(new CellRangeAddress(1, 2, i, i));
			Cell cell = headerRow2.createCell(i);
			cell.setCellValue(newColumns[i]);
			cell.setCellStyle(headerCellStyle2);
			headerCellStyle2.setWrapText(true);

		}

		for (int j = 6; j < newColumns.length; j++) {
			Cell cell = headerRow3.createCell(j);
			cell.setCellValue(newColumns[j]);
			cell.setCellStyle(headerCellStyle2);

		}

		Cell c1 = headerRow2.createCell(6);
		sheet.addMergedRegion(new CellRangeAddress(1, 1, 6, 8));
		c1.setCellValue("Hindi");
		c1.setCellStyle(headerCellStyle2);

		Cell c2 = headerRow2.createCell(9);
		sheet.addMergedRegion(new CellRangeAddress(1, 1, 9, 11));
		c2.setCellValue("English");
		c2.setCellStyle(headerCellStyle2);

		if (languageKnownDTOList.size() > 0) {

			int rowNum = 3;
			if (employeeId != 0L) {
				for (EmployeeLanguage employeeLanguage : languageKnownDTOList) {

					Row row = sheet.createRow(rowNum++);

					row.createCell(0).setCellValue(employeeLanguage.getEmployeeCode());
					row.createCell(1).setCellValue(employeeLanguage.getFirstName());
					row.createCell(2).setCellValue(employeeLanguage.getMiddleName());
					row.createCell(3).setCellValue(employeeLanguage.getLastName());
					row.createCell(4).setCellValue(employeeLanguage.getDeptName());
					row.createCell(5).setCellValue(employeeLanguage.getDesignation());
					row.createCell(6).setCellValue(employeeLanguage.gethRead());
					row.createCell(7).setCellValue(employeeLanguage.gethWrite());
					row.createCell(8).setCellValue(employeeLanguage.gethSpeak());
					row.createCell(9).setCellValue(employeeLanguage.geteRead());
					row.createCell(10).setCellValue(employeeLanguage.geteWrite());
					row.createCell(11).setCellValue(employeeLanguage.geteSpeak());

				}

			} else {
				HashMap<String, EmployeeLanguage> map = new HashMap<String, EmployeeLanguage>();

				for (EmployeeLanguage employeeLanguage : languageKnownDTOList) {

					EmployeeLanguage obj = new EmployeeLanguage();

					obj.setEmployeeCode(employeeLanguage.getEmployeeCode());
					obj.setFirstName(employeeLanguage.getFirstName());
					obj.setMiddleName(employeeLanguage.getMiddleName());
					obj.setLastName(employeeLanguage.getLastName());
					obj.setDeptName(employeeLanguage.getDeptName());
					obj.setDesignation(employeeLanguage.getDesignation());
					obj.setLangId(employeeLanguage.getLangId());
					obj.setLangRead(employeeLanguage.getLangRead());
					obj.setLangWrite(employeeLanguage.getLangWrite());
					obj.setLangSpeak(employeeLanguage.getLangSpeak());

					if (map.get(employeeLanguage.getEmployeeCode()) != null ? true : false) {
						addCustomObjLangDetails(obj, employeeLanguage, map);
					} else {
						map.put(employeeLanguage.getEmployeeCode(), obj);
						if (employeeLanguage.getLangId() == 6) {
							obj.seteRead(employeeLanguage.getLangRead());
							obj.seteWrite(employeeLanguage.getLangWrite());
							obj.seteSpeak(employeeLanguage.getLangSpeak());
						} else {

							obj.sethRead(employeeLanguage.getLangRead());
							obj.sethWrite(employeeLanguage.getLangWrite());
							obj.sethSpeak(employeeLanguage.getLangSpeak());

						}
					}

				}
				int rowNuum = 3;

				if (!map.isEmpty()) {

					for (Entry<String, EmployeeLanguage> entry : map.entrySet()) {

						Row row = sheet.createRow(rowNuum++);

						row.createCell(0).setCellValue(entry.getValue().getEmployeeCode());
						row.createCell(1).setCellValue(entry.getValue().getFirstName());
						row.createCell(2).setCellValue(entry.getValue().getMiddleName());
						row.createCell(3).setCellValue(entry.getValue().getLastName());
						row.createCell(4).setCellValue(entry.getValue().getDeptName());
						row.createCell(5).setCellValue(entry.getValue().getDesignation());
						row.createCell(6).setCellValue(entry.getValue().gethRead());
						row.createCell(7).setCellValue(entry.getValue().gethWrite());
						row.createCell(8).setCellValue(entry.getValue().gethSpeak());
						row.createCell(9).setCellValue(entry.getValue().geteRead());
						row.createCell(10).setCellValue(entry.getValue().geteWrite());
						row.createCell(11).setCellValue(entry.getValue().geteSpeak());

					}
				} else {

					Row row = sheet.createRow(3);

					sheet.addMergedRegion(new CellRangeAddress(3, 6, 0, newColumns.length - 1));
					Cell cell = row.createCell(0);
					cell.setCellValue(" --  Data Not Available -- ");
					cell.setCellStyle(headerCellStyle1);

				}
			}
		} else {

			Row row = sheet.createRow(3);

			sheet.addMergedRegion(new CellRangeAddress(3, 6, 0, newColumns.length - 1));
			Cell cell = row.createCell(0);
			cell.setCellValue(" --  Data Not Available -- ");
			cell.setCellStyle(headerCellStyle1);

		}

		// Resize columns to fit the content size
		for (int i = 0; i < newColumns.length; i++) {
			sheet.autoSizeColumn(i);
		}

		return workbook;
	}

	public static Workbook separationRequestReport(List<SeparationDTO> separationDTOList, String[] columns, Date fDate,
			Date tDate) {

		Workbook workbook = new XSSFWorkbook();

		CreationHelper createHelper = workbook.getCreationHelper();

		// Create a Sheet
		Sheet sheet = workbook.createSheet("Separation Request Summary");

		// Create a Font for styling header cells
		Font headerFont = workbook.createFont();
		headerFont.setBold(true);
		headerFont.setFontHeightInPoints((short) 12);
		headerFont.setColor(IndexedColors.BLACK.getIndex());

		// Create a CellStyle with the font
		CellStyle headerCellStyle = workbook.createCellStyle();
		headerCellStyle.setFont(headerFont);
		headerCellStyle.setAlignment(HorizontalAlignment.CENTER);
		headerCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
		headerCellStyle.setFillBackgroundColor(IndexedColors.LIGHT_CORNFLOWER_BLUE.getIndex());
		headerCellStyle.setFillPattern(FillPatternType.FINE_DOTS);

		CellStyle headerCellStyle2 = workbook.createCellStyle();
		headerCellStyle2.setFont(headerFont);
		headerCellStyle2.setAlignment(HorizontalAlignment.CENTER);
		headerCellStyle2.setVerticalAlignment(VerticalAlignment.CENTER);

		Row row0 = sheet.createRow(0);
		sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, columns.length - 1));
		Cell cellCom = row0.createCell(0);
		cellCom.setCellValue("Separation Request Summary");
		cellCom.setCellStyle(headerCellStyle2);

		Row row1 = sheet.createRow(1);
		sheet.addMergedRegion(new CellRangeAddress(1, 1, 0, columns.length - 1));
		Cell cell1 = row1.createCell(0);
		cell1.setCellValue(DateUtils.getDateStrInDDMMMYYYY(fDate) + " To " + DateUtils.getDateStrInDDMMMYYYY(tDate));
		cell1.setCellStyle(headerCellStyle2);

		// Create a Row
		Row headerRow = sheet.createRow(2);

		// Creating cells
		for (int i = 0; i < columns.length; i++) {
			Cell cell = headerRow.createCell(i);
			cell.setCellValue(columns[i]);
			cell.setCellStyle(headerCellStyle);

		}
		if (separationDTOList.size() > 0) {
			int rowNum = 3;
			for (SeparationDTO separationDTO : separationDTOList) {

				Row row = sheet.createRow(rowNum++);

				CellStyle dateCellStyle = workbook.createCellStyle();
				dateCellStyle.setDataFormat(createHelper.createDataFormat().getFormat("dd-MMM-yyyy"));

				row.createCell(0).setCellValue(separationDTO.getEmployeeCode());
				row.createCell(1).setCellValue(separationDTO.getEmployeeName());
				row.createCell(2).setCellValue(separationDTO.getDepartmentName());
				row.createCell(3).setCellValue(separationDTO.getDesignationName());
				row.createCell(4).setCellValue(separationDTO.getJobLocation());
				row.createCell(5).setCellValue(separationDTO.getReportingTo());

				Cell cell = row.createCell(6);
				cell.setCellValue(separationDTO.getDateOfJoining());
				cell.setCellStyle(dateCellStyle);

				Cell cel1 = row.createCell(7);
				cel1.setCellValue(separationDTO.getDateCreated());
				cel1.setCellStyle(dateCellStyle);

				row.createCell(8).setCellValue(separationDTO.getDescription());

				Cell cell2 = row.createCell(9);
				cell2.setCellValue(separationDTO.getExitDate());
				cell2.setCellStyle(dateCellStyle);

				row.createCell(10).setCellValue(separationDTO.getInNoticePeriod());
				row.createCell(11).setCellValue(separationDTO.getRemark());

			}

		} else {

			Row row = sheet.createRow(3);

			sheet.addMergedRegion(new CellRangeAddress(3, 6, 0, columns.length - 1));
			Cell cell = row.createCell(0);
			cell.setCellValue("--Data Not Available -- ");
			cell.setCellStyle(headerCellStyle2);

		}
		// Resize all columns to fit the content size
		for (int i = 0; i < columns.length; i++) {
			sheet.autoSizeColumn(i);
		}

		return workbook;
	}

}
