package com.csipl.hrms.common.util;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.DateFormat;
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
import org.apache.poi.ss.usermodel.DataValidation;
import org.apache.poi.ss.usermodel.DataValidationConstraint;
import org.apache.poi.ss.usermodel.DataValidationHelper;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.apache.poi.xssf.usermodel.XSSFDataValidationHelper;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.csipl.hrms.common.constant.StatusMessage;
import com.csipl.hrms.dto.employee.PayStructureHdDTO;
import com.csipl.hrms.dto.payroll.ArrearReportPayOutDTO;
import com.csipl.hrms.dto.payroll.OneTimeEarningDeductionDTO;
import com.csipl.hrms.dto.payroll.TdsHouseRentInfoDTO;
import com.csipl.hrms.dto.payrollprocess.ReportPayOutDTO;
import com.csipl.hrms.dto.payrollprocess.ReportSummaryDTO;
import com.csipl.hrms.model.common.Company;
import com.csipl.hrms.model.payroll.Esi;

public class PayrollExelWriter {

	/**
	 * Logger declaration for knowing the flow of execution for debugging
	 */
	private static final Logger logger = LoggerFactory.getLogger(ExcelWriter.class);
	private static int HashMap;

	private static double convertBigdecimalToDouble(BigDecimal bigDecimalValue) {
		double doubleValue;
		String str123;
		if (bigDecimalValue != null) {
			doubleValue = bigDecimalValue.doubleValue();
			str123 = bigDecimalValue.toString();
			// System.out.println("string value..." + str123);
		} else
			doubleValue = 0;
		return doubleValue;
	}

	private static String convertBigdecimalToString(BigDecimal bigDecimalValue) {
		double doubleValue;
		String strValue;
		if (bigDecimalValue != null) {
			strValue = bigDecimalValue.toString();
		} else
			strValue = "0.00";
		return strValue;
	}

	public static Workbook PTReport(List<ReportPayOutDTO> reportPayOutList, String[] columns, Company company,
			String fromProcessMonth, String toProcessMonth) throws IOException, InvalidFormatException {

		String toProcessMnth = (fromProcessMonth.equals(toProcessMonth) ? "" : ("to " + toProcessMonth));
		// Create a Workbook
		Workbook workbook = new XSSFWorkbook(); // new HSSFWorkbook() for generating `.xls` file

		/*
		 * CreationHelper helps us create instances for various things like DataFormat,
		 * Hyperlink, RichTextString etc, in a format (HSSF, XSSF) independent way
		 */
		CreationHelper createHelper = workbook.getCreationHelper();

		// Create a Sheet
		Sheet sheet = workbook.createSheet("Professional Tax");

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
		CellStyle headerCellStyle1 = workbook.createCellStyle();
		headerCellStyle1.setFont(headerFont);
		headerCellStyle1.setAlignment(HorizontalAlignment.RIGHT);
		headerCellStyle1.setVerticalAlignment(VerticalAlignment.CENTER);
		headerCellStyle1.setFillBackgroundColor(IndexedColors.LIGHT_CORNFLOWER_BLUE.getIndex());
		headerCellStyle1.setFillPattern(FillPatternType.FINE_DOTS);
		CellStyle headerCellStyle11 = workbook.createCellStyle();
		headerCellStyle11.setFont(headerFont);
		headerCellStyle11.setAlignment(HorizontalAlignment.LEFT);
		headerCellStyle11.setVerticalAlignment(VerticalAlignment.CENTER);
		CellStyle cellStyle112 = workbook.createCellStyle();
		cellStyle112.setAlignment(HorizontalAlignment.RIGHT);
		cellStyle112.setVerticalAlignment(VerticalAlignment.CENTER);
		// Create Other rows and cells with employees data
		Row row0 = sheet.createRow(0);
		sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 1));
		row0.createCell(0).setCellValue(company.getCompanyName());

		Row row1 = sheet.createRow(1);
		sheet.addMergedRegion(new CellRangeAddress(1, 1, 0, 1));
		row1.createCell(0).setCellValue(company.getAddress1().getAddressText() + " ,");
		// sheet.addMergedRegion(new CellRangeAddress(0,1,1,2));

		// String monthAcronym=processMonth.substring(0, 3);
		Row row2 = sheet.createRow(2);
		sheet.addMergedRegion(new CellRangeAddress(2, 2, 0, 2));
		row2.createCell(0).setCellValue(company.getAddress1().getLandmark() + ", "
				+ company.getAddress1().getCity().getCityName() + "- " + company.getAddress1().getPincode());

		Row row4 = sheet.createRow(4);
		Cell cell1 = row4.createCell(0);
		sheet.addMergedRegion(new CellRangeAddress(4, 4, 0, 2));
		cell1.setCellValue("Professional Tax Statement");
		cell1.setCellStyle(headerCellStyle11);

		Row row5 = sheet.createRow(5);
		sheet.addMergedRegion(new CellRangeAddress(5, 5, 0, 2));
		row5.createCell(0).setCellValue(fromProcessMonth + " " + toProcessMnth);

		if (reportPayOutList.isEmpty() || reportPayOutList.equals(" ")) {
			Font headerFont1 = workbook.createFont();
			headerFont1.setBold(true);
			headerFont1.setFontHeightInPoints((short) 16);
			headerFont1.setColor(IndexedColors.BLACK.getIndex());

			CellStyle headerCellStyle12 = workbook.createCellStyle();
			headerCellStyle12.setFont(headerFont);
			headerCellStyle12.setAlignment(HorizontalAlignment.CENTER);
			headerCellStyle12.setVerticalAlignment(VerticalAlignment.CENTER);
			headerCellStyle12.setFillBackgroundColor(IndexedColors.LIGHT_CORNFLOWER_BLUE.getIndex());
			headerCellStyle12.setFillPattern(FillPatternType.FINE_DOTS);

			Row headerRow = sheet.createRow(6);
			sheet.addMergedRegion(new CellRangeAddress(6, 7, 3, 8));
			Cell cell0 = headerRow.createCell(3);
			cell0.setCellValue(" Data not available");
			cell0.setCellStyle(headerCellStyle12);
		} else {
			System.out.println("pt report");
			// Create a Row
			Row headerRow = sheet.createRow(6);

			// Creating cells
			for (int i = 0; i < columns.length; i++) {
				Cell cell = headerRow.createCell(i);
				cell.setCellValue(columns[i]);
				cell.setCellStyle(headerCellStyle);
			}

			// Create Cell Style for formatting Date
			CellStyle dateCellStyle = workbook.createCellStyle();
			dateCellStyle.setDataFormat(createHelper.createDataFormat().getFormat("dd-MM-yyyy"));
			CellStyle cellStyle = workbook.createCellStyle();
			cellStyle.setDataFormat(workbook.getCreationHelper().createDataFormat().getFormat("#.##"));
			int rowNum = 7;

			BigDecimal sumPTamout = new BigDecimal(0);

			for (ReportPayOutDTO reportPayOut : reportPayOutList) {
				if (reportPayOut.getPt() != null && (reportPayOut.getPt().compareTo(BigDecimal.ZERO) > 0)) {
					Row row = sheet.createRow(rowNum++);
					row.createCell(0).setCellValue(reportPayOut.getEmployeeCode());
					row.createCell(1).setCellValue(reportPayOut.getName());
					row.createCell(2).setCellValue(reportPayOut.getDepartmentName());
					row.createCell(3).setCellValue(reportPayOut.getDesignationName());
					sumPTamout = sumPTamout.add(reportPayOut.getPt() != null ? reportPayOut.getPt() : BigDecimal.ZERO);
					Cell createdCell = row.createCell(4);
					createdCell.setCellValue(convertBigdecimalToString(reportPayOut.getPt()));
					createdCell.setCellStyle(cellStyle112);

					row.createCell(5).setCellValue(reportPayOut.getProcessMonth());
					row.createCell(6).setCellValue(reportPayOut.getStateName());
				}
			}
			// headerCellStyle.setAlignment(HorizontalAlignment.RIGHT);
			headerCellStyle.setVerticalAlignment(VerticalAlignment.JUSTIFY);
			Row row11 = sheet.createRow(rowNum++);
			for (int i = 0; i < 3; i++) {
				Cell cell00 = row11.createCell(i);
				cell00.setCellStyle(headerCellStyle1);
			}

			Cell cell0 = row11.createCell(3);
			cell0.setCellValue(" Total");
			cell0.setCellStyle(headerCellStyle1);
			Cell cell11 = row11.createCell(4);
			cell11.setCellValue(convertBigdecimalToDouble(sumPTamout));
			cell11.setCellStyle(headerCellStyle1);
			Cell cell15 = row11.createCell(5);
			cell15.setCellStyle(headerCellStyle1);
			Cell cell16 = row11.createCell(6);
			cell16.setCellStyle(headerCellStyle1);
			// Resize all columns to fit the content size
			for (int i = 0; i < columns.length; i++) {
				sheet.autoSizeColumn(i);
			}
		}

		/*
		 * // Write the output to a file FileOutputStream fileOut = new
		 * FileOutputStream("E:\\poi-generated-file.xlsx"); workbook.write(fileOut);
		 * fileOut.close();
		 */
		return workbook;
	}

	public static Workbook provisionReport(List<ReportPayOutDTO> reportPayOutList, String[] columns, Date fromDate,
			Date toDate, Company company, Long departmentId) throws IOException, InvalidFormatException {

		// Create a Workbook
		Workbook workbook = new XSSFWorkbook(); // new HSSFWorkbook() for generating `.xls` file

		/*
		 * CreationHelper helps us create instances for various things like DataFormat,
		 * Hyperlink, RichTextString etc, in a format (HSSF, XSSF) independent way
		 */
		CreationHelper createHelper = workbook.getCreationHelper();

		// Create a Sheet
		Sheet sheet = workbook.createSheet("Provision");
		DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		String fromdateString = dateFormat.format(fromDate);
		System.out.println(fromdateString);

		String todateString = dateFormat.format(toDate);
		System.out.println(todateString);
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
		CellStyle headerCellStyle1 = workbook.createCellStyle();
		headerCellStyle1.setFont(headerFont);
		headerCellStyle1.setAlignment(HorizontalAlignment.LEFT);
		headerCellStyle1.setVerticalAlignment(VerticalAlignment.CENTER);
		CellStyle cellStyle112 = workbook.createCellStyle();
		cellStyle112.setAlignment(HorizontalAlignment.RIGHT);
		cellStyle112.setVerticalAlignment(VerticalAlignment.CENTER);
		CellStyle dateCellStyle = workbook.createCellStyle();
		dateCellStyle.setDataFormat(createHelper.createDataFormat().getFormat("dd-MM-yyyy"));
		CellStyle cellStyle = workbook.createCellStyle();
		cellStyle.setDataFormat(workbook.getCreationHelper().createDataFormat().getFormat("0.00"));
		CellStyle cellStyle1 = workbook.createCellStyle();
		/*
		 * cellStyle1.setDataFormat(
		 * workbook.getCreationHelper().createDataFormat().getFormat("#.##00"));
		 */

		Row row0 = sheet.createRow(0);
		sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 2));
		row0.createCell(0).setCellValue(company.getCompanyName());

		Row row1 = sheet.createRow(1);
		sheet.addMergedRegion(new CellRangeAddress(1, 1, 0, 1));
		row1.createCell(0).setCellValue(company.getAddress1().getAddressText() + ",");

		Row row2 = sheet.createRow(2);
		sheet.addMergedRegion(new CellRangeAddress(2, 2, 0, 3));
		row2.createCell(0).setCellValue(company.getAddress1().getLandmark() + ", "
				+ company.getAddress1().getCity().getCityName() + "- " + company.getAddress1().getPincode());

		Row row4 = sheet.createRow(4);

		Cell createdCell1 = row4.createCell(0);
		sheet.addMergedRegion(new CellRangeAddress(4, 4, 0, 1));
		createdCell1.setCellValue("Provision Statement");
		createdCell1.setCellStyle(headerCellStyle1);

		Row row5 = sheet.createRow(5);

		sheet.addMergedRegion(new CellRangeAddress(5, 5, 0, 4));
		Cell createdDateCell1 = row5.createCell(0);
		createdDateCell1.setCellValue("From- " + fromdateString + "  To- " + todateString);

		if (reportPayOutList.isEmpty() || reportPayOutList.equals("")) {
			Font headerFont1 = workbook.createFont();
			headerFont1.setBold(true);
			headerFont1.setFontHeightInPoints((short) 16);
			headerFont1.setColor(IndexedColors.BLACK.getIndex());

			CellStyle headerCellStyle12 = workbook.createCellStyle();
			headerCellStyle12.setFont(headerFont);
			headerCellStyle12.setAlignment(HorizontalAlignment.CENTER);
			headerCellStyle12.setVerticalAlignment(VerticalAlignment.CENTER);
			headerCellStyle12.setFillBackgroundColor(IndexedColors.LIGHT_CORNFLOWER_BLUE.getIndex());
			headerCellStyle12.setFillPattern(FillPatternType.FINE_DOTS);

			Row headerRow = sheet.createRow(6);
			sheet.addMergedRegion(new CellRangeAddress(6, 7, 3, 8));
			Cell cell0 = headerRow.createCell(3);
			cell0.setCellValue(" Data not available");
			cell0.setCellStyle(headerCellStyle12);
		} else {
			Row row6 = sheet.createRow(6);

			if (departmentId != 0) {
				for (ReportPayOutDTO reportPayOut : reportPayOutList) {
					row6.createCell(0).setCellValue("Department-" + reportPayOut.getDepartmentName());
					break;
				}
			} else
				row6.createCell(0).setCellValue("Department-" + "All");
			// Create a Row
			Row headerRow = sheet.createRow(7);

			// Creating cells
			for (int i = 0; i < columns.length; i++) {
				Cell cell = headerRow.createCell(i);
				cell.setCellValue(columns[i]);
				cell.setCellStyle(headerCellStyle);
			}

			// Create Cell Style for formatting Date

			int rowNum = 8;

			for (ReportPayOutDTO reportPayOut : reportPayOutList) {
				Row row = sheet.createRow(rowNum++);
				row.createCell(0).setCellValue(reportPayOut.getName());
				row.createCell(1).setCellValue(reportPayOut.getEmployeeCode());
				row.createCell(2).setCellValue(reportPayOut.getBankName());
				row.createCell(3).setCellValue(reportPayOut.getAccountNumber());
				Cell createdCell = row.createCell(4);
				createdCell.setCellValue(convertBigdecimalToString(reportPayOut.getNetPayableAmount()));
				createdCell.setCellStyle(cellStyle112);

				Cell createdDateCell = row.createCell(5);
				createdDateCell.setCellValue(reportPayOut.getProvisionDateCreated());
				createdDateCell.setCellStyle(dateCellStyle);
				row.createCell(6).setCellValue(reportPayOut.getEmpDetp());
			}
			headerCellStyle.setVerticalAlignment(VerticalAlignment.JUSTIFY);
			// Resize all columns to fit the content size
			for (int i = 0; i < columns.length; i++) {
				sheet.autoSizeColumn(i);
			}
		}
		/*
		 * // Write the output to a file FileOutputStream fileOut = new
		 * FileOutputStream("E:\\poi-generated-file.xlsx"); workbook.write(fileOut);
		 * fileOut.close();
		 */
		return workbook;
	}

	public static Workbook payrollMonthlyReport(List<ReportPayOutDTO> reportPayOutList, String[] columns,
			String fromProcessMonth, Company company, Long departmentId) throws IOException, InvalidFormatException {

		Workbook workbook = new XSSFWorkbook();
		CreationHelper createHelper = workbook.getCreationHelper();

		Sheet sheet = workbook.createSheet("Payroll Summery");
		Font headerFont = workbook.createFont();
		headerFont.setBold(true);
		headerFont.setFontHeightInPoints((short) 12);
		headerFont.setColor(IndexedColors.BLACK.getIndex());

		Font rowFont = workbook.createFont();
		rowFont.setBold(true);
		rowFont.setFontHeightInPoints((short) 14);
		rowFont.setColor(IndexedColors.BLACK.getIndex());

		CellStyle headerCellStyle = workbook.createCellStyle();
		headerCellStyle.setFont(headerFont);
		headerCellStyle.setAlignment(HorizontalAlignment.CENTER);
		headerCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
		headerCellStyle.setFillBackgroundColor(IndexedColors.LIGHT_CORNFLOWER_BLUE.getIndex());
		headerCellStyle.setFillPattern(FillPatternType.FINE_DOTS);
		CellStyle headerCellStyle1 = workbook.createCellStyle();
		headerCellStyle1.setFont(headerFont);
		headerCellStyle1.setAlignment(HorizontalAlignment.RIGHT);
		headerCellStyle1.setVerticalAlignment(VerticalAlignment.CENTER);
		headerCellStyle1.setFillBackgroundColor(IndexedColors.LIGHT_CORNFLOWER_BLUE.getIndex());
		headerCellStyle1.setFillPattern(FillPatternType.FINE_DOTS);
		CellStyle headerCellStyle11 = workbook.createCellStyle();
		headerCellStyle11.setFont(headerFont);
		headerCellStyle11.setAlignment(HorizontalAlignment.LEFT);
		headerCellStyle11.setVerticalAlignment(VerticalAlignment.CENTER);
		CellStyle rowCellStyle = workbook.createCellStyle();
		rowCellStyle.setFont(rowFont);

		CellStyle cellStyle112 = workbook.createCellStyle();
		cellStyle112.setAlignment(HorizontalAlignment.RIGHT);
		cellStyle112.setVerticalAlignment(VerticalAlignment.CENTER);
		CellStyle dateCellStyle = workbook.createCellStyle();
		dateCellStyle.setDataFormat(createHelper.createDataFormat().getFormat("dd-MM-yyyy"));
		CellStyle cellStyle = workbook.createCellStyle();
		cellStyle.setDataFormat(workbook.getCreationHelper().createDataFormat().getFormat("0.00"));
		Row row0 = sheet.createRow(0);
		sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 1));
		row0.createCell(0).setCellValue(company.getCompanyName());

		Row row1 = sheet.createRow(1);
		sheet.addMergedRegion(new CellRangeAddress(1, 1, 0, 1));
		row1.createCell(0).setCellValue(company.getAddress1().getAddressText() + ",");

		Row row2 = sheet.createRow(2);
		sheet.addMergedRegion(new CellRangeAddress(2, 2, 0, 3));
		row2.createCell(0).setCellValue(company.getAddress1().getLandmark() + " , "
				+ company.getAddress1().getCity().getCityName() + "- " + company.getAddress1().getPincode());

		Row row4 = sheet.createRow(4);
		Cell createdCell1 = row4.createCell(0);
		sheet.addMergedRegion(new CellRangeAddress(4, 4, 0, 1));
		createdCell1.setCellValue("Payroll Register");
		createdCell1.setCellStyle(headerCellStyle11);
		Row row5 = sheet.createRow(5);
		row5.createCell(0).setCellValue(fromProcessMonth);
		if (reportPayOutList.isEmpty() || reportPayOutList.equals("")) {
			Font headerFont1 = workbook.createFont();
			headerFont1.setBold(true);
			headerFont1.setFontHeightInPoints((short) 16);
			headerFont1.setColor(IndexedColors.BLACK.getIndex());

			CellStyle headerCellStyle12 = workbook.createCellStyle();
			headerCellStyle12.setFont(headerFont);
			headerCellStyle12.setAlignment(HorizontalAlignment.CENTER);
			headerCellStyle12.setVerticalAlignment(VerticalAlignment.CENTER);
			headerCellStyle12.setFillBackgroundColor(IndexedColors.LIGHT_CORNFLOWER_BLUE.getIndex());
			headerCellStyle12.setFillPattern(FillPatternType.FINE_DOTS);

			Row headerRow = sheet.createRow(6);
			sheet.addMergedRegion(new CellRangeAddress(6, 7, 3, 8));
			Cell cell0 = headerRow.createCell(3);
			cell0.setCellValue(" Data not available");
			cell0.setCellStyle(headerCellStyle12);
		} else {
			Row row6 = sheet.createRow(6);
			if (departmentId != 0) {
				for (ReportPayOutDTO reportPayOut : reportPayOutList) {
					row6.createCell(0).setCellValue("Department-" + reportPayOut.getDepartmentName());
					break;
				}
			} else
				row6.createCell(0).setCellValue("Department-" + "All");
			Row headerRow = sheet.createRow(7);

			for (int i = 0; i < columns.length; i++) {
				Cell cell = headerRow.createCell(i);
				cell.setCellValue(columns[i]);
				cell.setCellStyle(headerCellStyle);
			}
			BigDecimal sumBasic = new BigDecimal(0);
			BigDecimal sumDearnessAllowance = new BigDecimal(0);
			BigDecimal sumConveyanceAllowance = new BigDecimal(0);
			BigDecimal sumHra = new BigDecimal(0);
			BigDecimal sumMedicalAllowance = new BigDecimal(0);
			BigDecimal sumAdvanceBonus = new BigDecimal(0);
			BigDecimal sumSpecialAllowance = new BigDecimal(0);
			BigDecimal sumCompanyBenefits = new BigDecimal(0);
			BigDecimal sumOtherAllowance = new BigDecimal(0);
			BigDecimal sumGrossSalary = new BigDecimal(0);
			BigDecimal sumAbsense = new BigDecimal(0);
			BigDecimal sumCasualleave = new BigDecimal(0);
			BigDecimal sumSeekleave = new BigDecimal(0);
			BigDecimal sumPaidleave = new BigDecimal(0);
			BigDecimal sumPresense = new BigDecimal(0);
			BigDecimal sumPublicholidays = new BigDecimal(0);
			BigDecimal sumWeekoff = new BigDecimal(0);
			BigDecimal totalPayDays = new BigDecimal(0);
			// BigDecimal sumOvertime = new BigDecimal(0);
			BigDecimal sumBasicEarning = new BigDecimal(0);
			BigDecimal sumDearnessAllowanceEarning = new BigDecimal(0);
			BigDecimal sumConveyanceAllowanceEarning = new BigDecimal(0);
			BigDecimal sumHraEarning = new BigDecimal(0);
			BigDecimal sumMedicalAllowanceEarning = new BigDecimal(0);
			BigDecimal sumAdvanceBonusEarning = new BigDecimal(0);
			BigDecimal sumSpecialAllowanceEarning = new BigDecimal(0);
			BigDecimal totalCompanyBenefitsEarning = new BigDecimal(0);
			BigDecimal sumOtherAllowanceEarning = new BigDecimal(0);
			BigDecimal sumTotalEarning = new BigDecimal(0);
			BigDecimal sumLoan = new BigDecimal(0);
			BigDecimal sumProvidentFundEmployee = new BigDecimal(0);
			BigDecimal sumEsi_Employee = new BigDecimal(0);
			BigDecimal sumPt = new BigDecimal(0);
			BigDecimal sumTds = new BigDecimal(0);
			BigDecimal sumTotalDeduction = new BigDecimal(0);
			BigDecimal sumNetPayableAmount = new BigDecimal(0);
			int rowNum = 8;
			for (ReportPayOutDTO reportPayOut : reportPayOutList) {
				Row row = sheet.createRow(rowNum++);

				row.createCell(0).setCellValue(reportPayOut.getName());
				row.createCell(1).setCellValue(reportPayOut.getEmployeeCode());
				row.createCell(2).setCellValue(reportPayOut.getBankName());
				row.createCell(3).setCellValue(reportPayOut.getAccountNumber());

				Cell dateOfJoining = row.createCell(4);
				dateOfJoining.setCellValue(reportPayOut.getDateOfJoining());
				dateOfJoining.setCellStyle(dateCellStyle);
				sumBasic = sumBasic.add((reportPayOut.getBasic() != null ? reportPayOut.getBasic() : BigDecimal.ZERO));

				Cell createdCell = row.createCell(5);
				createdCell.setCellValue(convertBigdecimalToString(reportPayOut.getBasic()));
				createdCell.setCellStyle(cellStyle112);

				sumDearnessAllowance = sumDearnessAllowance
						.add((reportPayOut.getDearnessAllowance() != null ? reportPayOut.getDearnessAllowance()
								: BigDecimal.ZERO));

				Cell createdCell2 = row.createCell(6);
				createdCell2.setCellValue(convertBigdecimalToString(reportPayOut.getDearnessAllowance()));
				createdCell2.setCellStyle(cellStyle112);

				sumConveyanceAllowance = sumConveyanceAllowance
						.add(reportPayOut.getConveyanceAllowance() != null ? reportPayOut.getConveyanceAllowance()
								: BigDecimal.ZERO);
				Cell createdCell3 = row.createCell(7);
				createdCell3.setCellValue(convertBigdecimalToString(reportPayOut.getConveyanceAllowance()));
				createdCell3.setCellStyle(cellStyle112);

				sumHra = sumHra.add(reportPayOut.getHra() != null ? reportPayOut.getHra() : BigDecimal.ZERO);
				Cell createdCell4 = row.createCell(8);
				createdCell4.setCellValue(convertBigdecimalToString(reportPayOut.getHra()));
				createdCell4.setCellStyle(cellStyle112);

				sumMedicalAllowance = sumMedicalAllowance
						.add(reportPayOut.getMedicalAllowance() != null ? reportPayOut.getMedicalAllowance()
								: BigDecimal.ZERO);
				Cell createdCell5 = row.createCell(9);
				createdCell5.setCellValue(convertBigdecimalToString(reportPayOut.getMedicalAllowance()));
				createdCell5.setCellStyle(cellStyle112);

				sumAdvanceBonus = sumAdvanceBonus
						.add(reportPayOut.getAdvanceBonus() != null ? reportPayOut.getAdvanceBonus() : BigDecimal.ZERO);
				Cell createdCell6 = row.createCell(10);
				createdCell6.setCellValue(convertBigdecimalToString(reportPayOut.getAdvanceBonus()));
				createdCell6.setCellStyle(cellStyle112);

				sumSpecialAllowance = sumSpecialAllowance
						.add(reportPayOut.getSpecialAllowance() != null ? reportPayOut.getSpecialAllowance()
								: BigDecimal.ZERO);

				Cell createdCell7 = row.createCell(11);
				createdCell7.setCellValue(convertBigdecimalToString(reportPayOut.getSpecialAllowance()));
				createdCell7.setCellStyle(cellStyle112);

				sumCompanyBenefits = sumCompanyBenefits
						.add(reportPayOut.getCompanyBenefits() != null ? reportPayOut.getCompanyBenefits()
								: BigDecimal.ZERO);
				Cell createdCell8 = row.createCell(12);
				createdCell8.setCellValue(convertBigdecimalToString(reportPayOut.getCompanyBenefits()));
				createdCell8.setCellStyle(cellStyle112);

				sumOtherAllowance = sumOtherAllowance.add(
						reportPayOut.getOtherAllowance() != null ? reportPayOut.getOtherAllowance() : BigDecimal.ZERO);

				Cell createdCell9 = row.createCell(13);
				createdCell9.setCellValue(convertBigdecimalToString(reportPayOut.getOtherAllowance()));
				createdCell9.setCellStyle(cellStyle112);

				sumGrossSalary = sumGrossSalary
						.add(reportPayOut.getGrossSalary() != null ? reportPayOut.getGrossSalary() : BigDecimal.ZERO);

				Cell createdCell10 = row.createCell(14);
				createdCell10.setCellValue(convertBigdecimalToString(reportPayOut.getGrossSalary()));
				createdCell10.setCellStyle(cellStyle112);

				sumAbsense = sumAbsense
						.add(reportPayOut.getAbsense() != null ? reportPayOut.getAbsense() : BigDecimal.ZERO);
				row.createCell(15).setCellValue(convertBigdecimalToDouble(reportPayOut.getAbsense()));
				sumCasualleave = sumCasualleave
						.add(reportPayOut.getCasualleave() != null ? reportPayOut.getCasualleave() : BigDecimal.ZERO);
				row.createCell(16).setCellValue(convertBigdecimalToDouble(reportPayOut.getCasualleave()));
				sumSeekleave = sumSeekleave
						.add(reportPayOut.getSeekleave() != null ? reportPayOut.getSeekleave() : BigDecimal.ZERO);
				row.createCell(17).setCellValue(convertBigdecimalToDouble(reportPayOut.getSeekleave()));
				sumPaidleave = sumPaidleave
						.add(reportPayOut.getPaidleave() != null ? reportPayOut.getPaidleave() : BigDecimal.ZERO);
				row.createCell(18).setCellValue(convertBigdecimalToDouble(reportPayOut.getPaidleave()));
				sumPresense = sumPresense
						.add(reportPayOut.getPresense() != null ? reportPayOut.getPresense() : BigDecimal.ZERO);
				row.createCell(19).setCellValue(convertBigdecimalToDouble(reportPayOut.getPresense()));
				sumPublicholidays = sumPublicholidays.add(
						reportPayOut.getPublicholidays() != null ? reportPayOut.getPublicholidays() : BigDecimal.ZERO);
				row.createCell(20).setCellValue(convertBigdecimalToDouble(reportPayOut.getPublicholidays()));
				sumWeekoff = sumWeekoff
						.add(reportPayOut.getWeekoff() != null ? reportPayOut.getWeekoff() : BigDecimal.ZERO);
				row.createCell(21).setCellValue(convertBigdecimalToDouble(reportPayOut.getWeekoff()));
				// sumOvertime =
				// sumOvertime.add(reportPayOut.getOvertime()!=null?reportPayOut.getOvertime():BigDecimal.ZERO);
				// row.createCell(22).setCellValue(convertBigdecimalToDouble(reportPayOut.getOvertime()));
				totalPayDays = totalPayDays
						.add(reportPayOut.getPayDays() != null ? reportPayOut.getPayDays() : BigDecimal.ZERO);
				row.createCell(22).setCellValue(convertBigdecimalToDouble(reportPayOut.getPayDays()));
				sumBasicEarning = sumBasicEarning
						.add(reportPayOut.getBasicEarning() != null ? reportPayOut.getBasicEarning() : BigDecimal.ZERO);

				Cell createdCell11 = row.createCell(23);
				createdCell11.setCellValue(convertBigdecimalToString(reportPayOut.getBasicEarning()));
				createdCell11.setCellStyle(cellStyle112);

				sumDearnessAllowanceEarning = sumDearnessAllowanceEarning.add(
						reportPayOut.getDearnessAllowanceEarning() != null ? reportPayOut.getDearnessAllowanceEarning()
								: BigDecimal.ZERO);

				Cell createdCell12 = row.createCell(24);
				createdCell12.setCellValue(
						Double.valueOf(convertBigdecimalToString(reportPayOut.getDearnessAllowanceEarning())));
				createdCell12.setCellStyle(cellStyle112);

				sumConveyanceAllowanceEarning = sumConveyanceAllowanceEarning
						.add(reportPayOut.getConveyanceAllowanceEarning() != null
								? reportPayOut.getConveyanceAllowanceEarning()
								: BigDecimal.ZERO);

				Cell createdCell13 = row.createCell(25);
				createdCell13.setCellValue(convertBigdecimalToString(reportPayOut.getConveyanceAllowanceEarning()));
				createdCell13.setCellStyle(cellStyle112);
				sumHraEarning = sumHraEarning
						.add(reportPayOut.getHraEarning() != null ? reportPayOut.getHraEarning() : BigDecimal.ZERO);

				Cell createdCell14 = row.createCell(26);
				createdCell14.setCellValue(convertBigdecimalToString(reportPayOut.getHraEarning()));
				createdCell14.setCellStyle(cellStyle112);

				sumMedicalAllowanceEarning = sumMedicalAllowanceEarning.add(
						reportPayOut.getMedicalAllowanceEarning() != null ? reportPayOut.getMedicalAllowanceEarning()
								: BigDecimal.ZERO);

				Cell createdCell15 = row.createCell(27);
				createdCell15.setCellValue(convertBigdecimalToString(reportPayOut.getMedicalAllowanceEarning()));
				createdCell15.setCellStyle(cellStyle112);

				sumAdvanceBonusEarning = sumAdvanceBonusEarning
						.add(reportPayOut.getAdvanceBonusEarning() != null ? reportPayOut.getAdvanceBonusEarning()
								: BigDecimal.ZERO);

				Cell createdCell16 = row.createCell(28);
				createdCell16.setCellValue(convertBigdecimalToDouble(reportPayOut.getAdvanceBonusEarning()));
				createdCell16.setCellStyle(cellStyle112);

				sumSpecialAllowanceEarning = sumSpecialAllowanceEarning.add(
						reportPayOut.getSpecialAllowanceEarning() != null ? reportPayOut.getSpecialAllowanceEarning()
								: BigDecimal.ZERO);

				Cell createdCell17 = row.createCell(29);
				createdCell17.setCellValue(convertBigdecimalToString(reportPayOut.getSpecialAllowanceEarning()));
				createdCell17.setCellStyle(cellStyle112);

				totalCompanyBenefitsEarning = totalCompanyBenefitsEarning.add(
						reportPayOut.getCompanyBenefitsEarning() != null ? reportPayOut.getAbsense() : BigDecimal.ZERO);

				Cell createdCell18 = row.createCell(30);
				createdCell18.setCellValue(convertBigdecimalToString(reportPayOut.getCompanyBenefitsEarning()));
				createdCell18.setCellStyle(cellStyle112);

				sumOtherAllowanceEarning = sumOtherAllowanceEarning
						.add(reportPayOut.getOtherAllowanceEarning() != null ? reportPayOut.getOtherAllowanceEarning()
								: BigDecimal.ZERO);

				Cell createdCell19 = row.createCell(31);
				createdCell19.setCellValue(convertBigdecimalToString(reportPayOut.getOtherAllowanceEarning()));
				createdCell19.setCellStyle(cellStyle112);

				sumTotalEarning = sumTotalEarning
						.add(reportPayOut.getTotalEarning() != null ? reportPayOut.getTotalEarning() : BigDecimal.ZERO);
				Cell createdCell20 = row.createCell(32);
				createdCell20.setCellValue(convertBigdecimalToString(reportPayOut.getTotalEarning()));
				createdCell20.setCellStyle(cellStyle112);

				sumLoan = sumLoan.add(reportPayOut.getLoan() != null ? reportPayOut.getLoan() : BigDecimal.ZERO);

				Cell createdCell21 = row.createCell(33);
				createdCell21.setCellValue(convertBigdecimalToString(reportPayOut.getLoan()));
				createdCell21.setCellStyle(cellStyle112);

				sumProvidentFundEmployee = sumProvidentFundEmployee
						.add(reportPayOut.getProvidentFundEmployee() != null ? reportPayOut.getProvidentFundEmployee()
								: BigDecimal.ZERO);

				Cell createdCell22 = row.createCell(34);
				createdCell22.setCellValue(convertBigdecimalToString(reportPayOut.getProvidentFundEmployee()));
				createdCell22.setCellStyle(cellStyle112);

				sumEsi_Employee = sumEsi_Employee
						.add(reportPayOut.getEsi_Employee() != null ? reportPayOut.getEsi_Employee() : BigDecimal.ZERO);
				Cell createdCell23 = row.createCell(35);
				createdCell23.setCellValue(convertBigdecimalToString(reportPayOut.getEsi_Employee()));
				createdCell23.setCellStyle(cellStyle112);

				sumPt = sumPt.add(reportPayOut.getPt() != null ? reportPayOut.getPt() : BigDecimal.ZERO);

				Cell createdCell24 = row.createCell(36);
				createdCell24.setCellValue(convertBigdecimalToString(reportPayOut.getPt()));
				createdCell24.setCellStyle(cellStyle112);

				sumTds = sumTds.add(reportPayOut.getTds() != null ? reportPayOut.getTds() : BigDecimal.ZERO);

				Cell createdCell25 = row.createCell(37);
				createdCell25.setCellValue(convertBigdecimalToString(reportPayOut.getTds()));
				createdCell25.setCellStyle(cellStyle112);

				sumTotalDeduction = sumTotalDeduction.add(
						reportPayOut.getTotalDeduction() != null ? reportPayOut.getTotalDeduction() : BigDecimal.ZERO);

				Cell createdCell26 = row.createCell(38);
				createdCell26.setCellValue(convertBigdecimalToString(reportPayOut.getTotalDeduction()));
				createdCell26.setCellStyle(cellStyle112);

				sumNetPayableAmount = sumNetPayableAmount
						.add(reportPayOut.getNetPayableAmount() != null ? reportPayOut.getNetPayableAmount()
								: BigDecimal.ZERO);

				Cell createdCell27 = row.createCell(39);
				createdCell27.setCellValue(convertBigdecimalToString(reportPayOut.getNetPayableAmount()));
				createdCell27.setCellStyle(cellStyle112);

			}

			// headerCellStyle.setAlignment(HorizontalAlignment.RIGHT);
			headerCellStyle.setVerticalAlignment(VerticalAlignment.JUSTIFY);
			Row row = sheet.createRow(rowNum);
			for (int i = 0; i < 4; i++) {
				Cell cell11 = row.createCell(i);
				cell11.setCellStyle(headerCellStyle1);
			}
			Cell cell0 = row.createCell(4);
			cell0.setCellValue("Grand Total");
			cell0.setCellStyle(headerCellStyle1);

			Cell cell = row.createCell(5);
			cell.setCellValue(convertBigdecimalToDouble(sumBasic));
			cell.setCellStyle(headerCellStyle1);

			Cell cell1 = row.createCell(6);
			cell1.setCellValue(convertBigdecimalToDouble(sumDearnessAllowance));
			cell1.setCellStyle(headerCellStyle1);

			Cell cell2 = row.createCell(7);
			cell2.setCellValue(convertBigdecimalToDouble(sumConveyanceAllowance));
			cell2.setCellStyle(headerCellStyle1);

			Cell cell3 = row.createCell(8);
			cell3.setCellValue(convertBigdecimalToDouble(sumHra));
			cell3.setCellStyle(headerCellStyle1);

			Cell cell4 = row.createCell(9);
			cell4.setCellValue(convertBigdecimalToDouble(sumMedicalAllowance));
			cell4.setCellStyle(headerCellStyle1);

			Cell cell5 = row.createCell(10);
			cell5.setCellValue(convertBigdecimalToDouble(sumAdvanceBonus));
			cell5.setCellStyle(headerCellStyle1);

			Cell cell6 = row.createCell(11);
			cell6.setCellValue(convertBigdecimalToDouble(sumSpecialAllowance));
			cell6.setCellStyle(headerCellStyle1);

			Cell cell8 = row.createCell(12);
			cell8.setCellValue(convertBigdecimalToDouble(sumCompanyBenefits));
			cell8.setCellStyle(headerCellStyle1);

			Cell cell9 = row.createCell(13);
			cell9.setCellValue(convertBigdecimalToDouble(sumOtherAllowance));
			cell9.setCellStyle(headerCellStyle1);

			Cell cell10 = row.createCell(14);
			cell10.setCellValue(convertBigdecimalToDouble(sumGrossSalary));
			cell10.setCellStyle(headerCellStyle1);

			Cell cell11 = row.createCell(15);
			cell11.setCellValue(convertBigdecimalToDouble(sumAbsense));
			cell11.setCellStyle(headerCellStyle1);

			Cell cell12 = row.createCell(16);
			cell12.setCellValue(convertBigdecimalToDouble(sumCasualleave));
			cell12.setCellStyle(headerCellStyle1);

			Cell cell13 = row.createCell(17);
			cell13.setCellValue(convertBigdecimalToDouble(sumSeekleave));
			cell13.setCellStyle(headerCellStyle1);

			Cell cell14 = row.createCell(18);
			cell14.setCellValue(convertBigdecimalToDouble(sumPaidleave));
			cell14.setCellStyle(headerCellStyle1);

			Cell cell15 = row.createCell(19);
			cell15.setCellValue(convertBigdecimalToDouble(sumPresense));
			cell15.setCellStyle(headerCellStyle1);

			Cell cell16 = row.createCell(20);
			cell16.setCellValue(convertBigdecimalToDouble(sumPublicholidays));
			cell16.setCellStyle(headerCellStyle1);

			Cell cell17 = row.createCell(21);
			cell17.setCellValue(convertBigdecimalToDouble(sumWeekoff));
			cell17.setCellStyle(headerCellStyle1);

			/*
			 * Cell cell18 = row.createCell(22);
			 * cell18.setCellValue(convertBigdecimalToDouble(sumOvertime));
			 * cell18.setCellStyle(headerCellStyle1);
			 */

			Cell cell19 = row.createCell(22);
			cell19.setCellValue(convertBigdecimalToDouble(totalPayDays));
			cell19.setCellStyle(headerCellStyle1);

			Cell cell20 = row.createCell(23);
			cell20.setCellValue(convertBigdecimalToDouble(sumBasicEarning));
			cell20.setCellStyle(headerCellStyle1);

			Cell cell21 = row.createCell(24);
			cell21.setCellValue(convertBigdecimalToDouble(sumDearnessAllowanceEarning));
			cell21.setCellStyle(headerCellStyle1);

			Cell cell22 = row.createCell(25);
			cell22.setCellValue(convertBigdecimalToDouble(sumConveyanceAllowanceEarning));
			cell22.setCellStyle(headerCellStyle1);

			Cell cell23 = row.createCell(26);
			cell23.setCellValue(convertBigdecimalToDouble(sumHraEarning));
			cell23.setCellStyle(headerCellStyle1);

			Cell cell24 = row.createCell(27);
			cell24.setCellValue(convertBigdecimalToDouble(sumMedicalAllowanceEarning));
			cell24.setCellStyle(headerCellStyle1);

			Cell cell25 = row.createCell(28);
			cell25.setCellValue(convertBigdecimalToDouble(sumAdvanceBonusEarning));
			cell25.setCellStyle(headerCellStyle1);

			Cell cell26 = row.createCell(29);
			cell26.setCellValue(convertBigdecimalToDouble(sumSpecialAllowanceEarning));
			cell26.setCellStyle(headerCellStyle1);

			Cell cell27 = row.createCell(30);
			cell27.setCellValue(convertBigdecimalToDouble(totalCompanyBenefitsEarning));
			cell27.setCellStyle(headerCellStyle1);

			Cell cell28 = row.createCell(31);
			cell28.setCellValue(convertBigdecimalToDouble(sumOtherAllowanceEarning));
			cell28.setCellStyle(headerCellStyle1);

			Cell cell29 = row.createCell(32);
			cell29.setCellValue(convertBigdecimalToDouble(sumTotalEarning));
			cell29.setCellStyle(headerCellStyle1);

			Cell cell30 = row.createCell(33);
			cell30.setCellValue(convertBigdecimalToDouble(sumLoan));
			cell30.setCellStyle(headerCellStyle1);

			Cell cell31 = row.createCell(34);
			cell31.setCellValue(convertBigdecimalToDouble(sumProvidentFundEmployee));
			cell31.setCellStyle(headerCellStyle1);

			Cell cell32 = row.createCell(35);
			cell32.setCellValue(convertBigdecimalToDouble(sumEsi_Employee));
			cell32.setCellStyle(headerCellStyle1);

			Cell cell33 = row.createCell(36);
			cell33.setCellValue(convertBigdecimalToDouble(sumPt));
			cell33.setCellStyle(headerCellStyle1);

			Cell cell34 = row.createCell(37);
			cell34.setCellValue(convertBigdecimalToDouble(sumTds));
			cell34.setCellStyle(headerCellStyle1);

			Cell cell35 = row.createCell(38);
			cell35.setCellValue(convertBigdecimalToDouble(sumTotalDeduction));
			cell35.setCellStyle(headerCellStyle1);

			Cell cell36 = row.createCell(39);
			cell36.setCellValue(convertBigdecimalToDouble(sumNetPayableAmount));
			cell36.setCellStyle(headerCellStyle1);

			// Resize all columns to fit the content size
			for (int i = 0; i < columns.length; i++) {
				sheet.autoSizeColumn(i);
			}
		}
		return workbook;
	}

	public static Workbook bankReport(List<ReportPayOutDTO> reportPayOutDtoList1, String[] columns, Company company,
			String processMonth) throws IOException, InvalidFormatException {
		// Create a Workbook
		Workbook workbook = new XSSFWorkbook(); // new HSSFWorkbook() for generating `.xls` file
		/*
		 * CreationHelper helps us create instances for various things like DataFormat,
		 * Hyperlink, RichTextString etc, in a format (HSSF, XSSF) independent way
		 */
		CreationHelper createHelper = workbook.getCreationHelper();
		// Create a Sheet
		Sheet sheet = workbook.createSheet("Bank Report");
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

		CellStyle headerCellStyle1 = workbook.createCellStyle();
		headerCellStyle1.setFont(headerFont);
		headerCellStyle1.setAlignment(HorizontalAlignment.LEFT);
		headerCellStyle1.setVerticalAlignment(VerticalAlignment.CENTER);
		// Create Other rows and cells with employees data
		Row row0 = sheet.createRow(0);
		sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 1));
		row0.createCell(0).setCellValue(company.getCompanyName());

		Row row1 = sheet.createRow(1);
		sheet.addMergedRegion(new CellRangeAddress(1, 1, 0, 1));
		row1.createCell(0).setCellValue(company.getAddress1().getAddressText() + " ,");

		Row row2 = sheet.createRow(2);
		sheet.addMergedRegion(new CellRangeAddress(2, 2, 0, 2));
		row2.createCell(0).setCellValue(company.getAddress1().getLandmark() + ", "
				+ company.getAddress1().getCity().getCityName() + "- " + company.getAddress1().getPincode());

		CellStyle headerCellStyle2 = workbook.createCellStyle();
		headerCellStyle2.setFont(headerFont);
		headerCellStyle2.setAlignment(HorizontalAlignment.LEFT);
		headerCellStyle2.setVerticalAlignment(VerticalAlignment.CENTER);

		Row row4 = sheet.createRow(4);
		sheet.addMergedRegion(new CellRangeAddress(4, 4, 0, 1));
		Cell cell1 = row4.createCell(0);
		cell1.setCellValue("Bank Payment Summery");
		cell1.setCellStyle(headerCellStyle2);

		Row row5 = sheet.createRow(5);
		row5.createCell(0).setCellValue(processMonth);
		// Create a Row
		Row headerRow = sheet.createRow(7);

		// Creating cells
		for (int i = 0; i < columns.length; i++) {
			Cell cell = headerRow.createCell(i);
			cell.setCellValue(columns[i]);
			cell.setCellStyle(headerCellStyle);

		}

		// Create Cell Style for formatting Date
		CellStyle dateCellStyle = workbook.createCellStyle();
		dateCellStyle.setDataFormat(createHelper.createDataFormat().getFormat("dd-MM-yyyy"));
		BigDecimal totalAmount = new BigDecimal(0);
		int rowNum = 8;

		for (ReportPayOutDTO reportPayOut : reportPayOutDtoList1) {
			Row row = sheet.createRow(rowNum++);
			row.createCell(0).setCellValue(reportPayOut.getName());
			row.createCell(1).setCellValue(reportPayOut.getEmployeeCode());
			row.createCell(2).setCellValue(reportPayOut.getBankName());
			row.createCell(3).setCellValue(reportPayOut.getAccountNumber());
			row.createCell(4).setCellValue(convertBigdecimalToDouble(reportPayOut.getNetPayableAmount()));
			row.createCell(5).setCellValue(reportPayOut.getDepartmentName());
			totalAmount = totalAmount.add(reportPayOut.getNetPayableAmount());
		}
		CellStyle headerCellStyle3 = workbook.createCellStyle();
		headerCellStyle3.setFont(headerFont);
		headerCellStyle3.setAlignment(HorizontalAlignment.LEFT);
		headerCellStyle3.setVerticalAlignment(VerticalAlignment.CENTER);
		Row row = sheet.createRow(rowNum);
		for (int i = 0; i < 3; i++) {
			Cell cell11 = row.createCell(i);
			cell11.setCellValue("TOTAL");
			cell11.setCellStyle(headerCellStyle);
		}
		Row rowNew5 = sheet.createRow(rowNum);
		Cell cell0 = rowNew5.createCell(0);
		cell0.setCellValue("");
		Cell cell8 = rowNew5.createCell(1);
		cell8.setCellValue("");
		Cell cell9 = rowNew5.createCell(2);
		cell9.setCellValue("");
		Cell cell3 = rowNew5.createCell(3);
		cell3.setCellValue("TOTAL");
		cell3.setCellStyle(headerCellStyle);

		Cell cell6 = rowNew5.createCell(4);
		headerCellStyle1.setAlignment(HorizontalAlignment.RIGHT);

		cell6.setCellValue(convertBigdecimalToDouble(totalAmount));
		cell6.setCellStyle(headerCellStyle);
		/*
		 * Cell cell5 = rowNew5.createCell(5); cell5.setCellValue("");
		 * cell5.setCellStyle(headerCellStyle);
		 */

		headerCellStyle.setAlignment(HorizontalAlignment.CENTER);
		headerCellStyle.setVerticalAlignment(VerticalAlignment.JUSTIFY);

		// Resize all columns to fit the content size
		for (int i = 0; i < columns.length; i++) {
			sheet.autoSizeColumn(i);
		}

		return workbook;
	}

	public static Workbook reconciliationReport(List<ReportPayOutDTO> reportPayOutDtoList, String[] columns,
			Company company, String processMonth, String checkReco, Long departmentId)
			throws IOException, InvalidFormatException {

		// Create a Workbook
		Workbook workbook = new XSSFWorkbook(); // new HSSFWorkbook() for generating `.xls` file
		CreationHelper createHelper = workbook.getCreationHelper();
		// Create a Sheet
		Sheet sheet = null;
		if (checkReco.equals("true"))
			sheet = workbook.createSheet("Non Reconciliation Report");
		else
			sheet = workbook.createSheet("Reconciliation Report");

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

		CellStyle headerCellStyle1 = workbook.createCellStyle();
		headerCellStyle1.setFont(headerFont);
		headerCellStyle1.setAlignment(HorizontalAlignment.LEFT);
		headerCellStyle1.setVerticalAlignment(VerticalAlignment.CENTER);
		// Create Other rows and cells with employees data
		Row row0 = sheet.createRow(0);
		sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 1));
		row0.createCell(0).setCellValue(company.getCompanyName());

		Row row1 = sheet.createRow(1);
		sheet.addMergedRegion(new CellRangeAddress(1, 1, 0, 1));
		row1.createCell(0).setCellValue(company.getAddress1().getAddressText() + " ,");

		Row row2 = sheet.createRow(2);
		sheet.addMergedRegion(new CellRangeAddress(2, 2, 0, 2));
		row2.createCell(0).setCellValue(company.getAddress1().getLandmark() + ", "
				+ company.getAddress1().getCity().getCityName() + "- " + company.getAddress1().getPincode());

		CellStyle headerCellStyle2 = workbook.createCellStyle();
		headerCellStyle2.setFont(headerFont);
		headerCellStyle2.setAlignment(HorizontalAlignment.LEFT);
		headerCellStyle2.setVerticalAlignment(VerticalAlignment.CENTER);

		Row row4 = sheet.createRow(4);
		sheet.addMergedRegion(new CellRangeAddress(4, 4, 0, 1));
		Cell cell1 = row4.createCell(0);
		cell1.setCellValue("Payroll Reconciliation Statement");
		cell1.setCellStyle(headerCellStyle2);

		Row row5 = sheet.createRow(5);
		sheet.addMergedRegion(new CellRangeAddress(5, 5, 0, 1));
		row5.createCell(0).setCellValue("Month: -" + processMonth);

		Row row6 = sheet.createRow(6);
		sheet.addMergedRegion(new CellRangeAddress(6, 6, 0, 1));

		if (departmentId != 0) {
			for (ReportPayOutDTO reportPayOutDto : reportPayOutDtoList) {
				row6.createCell(0).setCellValue("Department-" + reportPayOutDto.getDepartmentName());
				break;
			}
		} else
			row6.createCell(0).setCellValue("Department-" + "All");

		// Create a Row
		Row headerRow = sheet.createRow(8);

		// Creating cells
		for (int i = 0; i < columns.length; i++) {
			Cell cell = headerRow.createCell(i);
			cell.setCellValue(columns[i]);
			cell.setCellStyle(headerCellStyle);

		}

		// Create Cell Style for formatting Date
		CellStyle dateCellStyle = workbook.createCellStyle();
		dateCellStyle.setDataFormat(createHelper.createDataFormat().getFormat("dd-MM-yyyy"));
		BigDecimal totalAmount = new BigDecimal(0);
		int rowNum = 9;
		if (departmentId == 0) {
			for (ReportPayOutDTO reportPayOut : reportPayOutDtoList) {

				Row row = sheet.createRow(rowNum++);
				row.createCell(0).setCellValue(reportPayOut.getEmployeeCode());
				row.createCell(1).setCellValue(reportPayOut.getName());
				row.createCell(2).setCellValue(reportPayOut.getBankName());
				row.createCell(3).setCellValue(reportPayOut.getAccountNumber());
				row.createCell(4).setCellValue(convertBigdecimalToDouble(reportPayOut.getNetPayableAmount()));

				Cell contractOverDateCell = row.createCell(5);
				if (reportPayOut.getReconciliationDate() != null)
					contractOverDateCell.setCellValue(reportPayOut.getReconciliationDate());
				else
					row.createCell(5).setCellValue("Under Process");
				contractOverDateCell.setCellStyle(dateCellStyle);
				contractOverDateCell.setCellStyle(dateCellStyle);

				if (reportPayOut.getTransactionNo() != null)
					row.createCell(6).setCellValue(reportPayOut.getTransactionNo());
				else
					row.createCell(6).setCellValue("Under Process");

				totalAmount = totalAmount.add(reportPayOut.getNetPayableAmount());
			}
		} else {

			for (ReportPayOutDTO reportPayOut : reportPayOutDtoList) {

				Row row = sheet.createRow(rowNum++);
				row.createCell(0).setCellValue(reportPayOut.getEmployeeCode());
				row.createCell(1).setCellValue(reportPayOut.getName());
				row.createCell(2).setCellValue(reportPayOut.getDepartmentName());

				row.createCell(3).setCellValue(reportPayOut.getBankName());
				row.createCell(4).setCellValue(reportPayOut.getAccountNumber());
				row.createCell(5).setCellValue(convertBigdecimalToDouble(reportPayOut.getNetPayableAmount()));

				Cell contractOverDateCell = row.createCell(6);
				if (reportPayOut.getReconciliationDate() != null)
					contractOverDateCell.setCellValue(reportPayOut.getReconciliationDate());
				else
					row.createCell(6).setCellValue("Under Process");
				contractOverDateCell.setCellStyle(dateCellStyle);
				contractOverDateCell.setCellStyle(dateCellStyle);

				if (reportPayOut.getTransactionNo() != null)
					row.createCell(7).setCellValue(reportPayOut.getTransactionNo());
				else
					row.createCell(7).setCellValue("Under Process");

				totalAmount = totalAmount.add(reportPayOut.getNetPayableAmount());
			}
		}
		Row rowNew5 = sheet.createRow(rowNum);
		// Create a CellStyle with the font
		headerCellStyle1.setFont(headerFont);
		headerCellStyle1.setAlignment(HorizontalAlignment.RIGHT);
		headerCellStyle1.setVerticalAlignment(VerticalAlignment.CENTER);

		Row row = sheet.createRow(rowNum);
		for (int i = 0; i < 2; i++) {
			Cell cell11 = row.createCell(i);
			cell11.setCellStyle(headerCellStyle);
		}

		Cell cell101 = row.createCell(0);
		cell101.setCellValue("");
		cell101.setCellStyle(headerCellStyle);

		Cell cell102 = row.createCell(1);
		cell102.setCellValue("");
		cell102.setCellStyle(headerCellStyle);

		Cell cell103 = row.createCell(2);
		cell103.setCellValue("");
		cell103.setCellStyle(headerCellStyle);

		if (departmentId != 0) {
			Cell cell104 = row.createCell(3);
			cell104.setCellValue("");
			cell104.setCellStyle(headerCellStyle);

			Cell cell3 = row.createCell(4);
			cell3.setCellValue("TOTAL ");
			cell3.setCellStyle(headerCellStyle);

			Cell cell6 = row.createCell(5);
			cell6.setCellValue(convertBigdecimalToDouble(totalAmount));
			cell6.setCellStyle(headerCellStyle);

			Cell cell7 = row.createCell(6);
			cell7.setCellValue("");
			cell7.setCellStyle(headerCellStyle);

			Cell cell8 = row.createCell(7);
			cell8.setCellValue("");
			cell8.setCellStyle(headerCellStyle);

		} else {
			Cell cell3 = row.createCell(3);
			cell3.setCellValue("TOTAL ");
			cell3.setCellStyle(headerCellStyle);

			Cell cell6 = row.createCell(4);
			cell6.setCellValue(convertBigdecimalToDouble(totalAmount));
			cell6.setCellStyle(headerCellStyle);

			Cell cell7 = row.createCell(5);
			cell7.setCellValue("");
			cell7.setCellStyle(headerCellStyle);

			Cell cell8 = row.createCell(6);
			cell8.setCellValue("");
			cell8.setCellStyle(headerCellStyle);
		}

		/*
		 * if (departmentId != 0) { Cell cell9 = row.createCell(7);
		 * cell9.setCellValue(""); cell9.setCellStyle(headerCellStyle); }
		 */ // Resize all columns to fit the content size
		for (int i = 0; i < columns.length; i++) {
			sheet.autoSizeColumn(i);
		}
		return workbook;
	}

	public static Workbook payrollMonthlyReportByEmpId(List<ReportPayOutDTO> reportPayOutList, String[] columns,
			String fromProcessMonth, String toProcessMonth, Company company)
			throws IOException, InvalidFormatException {

		Workbook workbook = new XSSFWorkbook();
		CreationHelper createHelper = workbook.getCreationHelper();
		String toProcessMnth = (fromProcessMonth.equals(toProcessMonth) ? "" : (" to " + toProcessMonth));
		Sheet sheet = workbook.createSheet("Payroll Summery");
		Font headerFont = workbook.createFont();
		headerFont.setBold(true);
		headerFont.setFontHeightInPoints((short) 12);
		headerFont.setColor(IndexedColors.BLACK.getIndex());

		Font rowFont = workbook.createFont();
		rowFont.setBold(true);
		rowFont.setFontHeightInPoints((short) 14);
		rowFont.setColor(IndexedColors.BLACK.getIndex());

		CellStyle headerCellStyle = workbook.createCellStyle();
		headerCellStyle.setFont(headerFont);
		headerCellStyle.setAlignment(HorizontalAlignment.CENTER);
		headerCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
		headerCellStyle.setFillBackgroundColor(IndexedColors.LIGHT_CORNFLOWER_BLUE.getIndex());
		headerCellStyle.setFillPattern(FillPatternType.FINE_DOTS);
		CellStyle headerCellStyle1 = workbook.createCellStyle();
		headerCellStyle1.setFont(headerFont);
		headerCellStyle1.setAlignment(HorizontalAlignment.RIGHT);
		headerCellStyle1.setVerticalAlignment(VerticalAlignment.CENTER);
		headerCellStyle1.setFillBackgroundColor(IndexedColors.LIGHT_CORNFLOWER_BLUE.getIndex());
		headerCellStyle1.setFillPattern(FillPatternType.FINE_DOTS);
		CellStyle headerCellStyle11 = workbook.createCellStyle();
		headerCellStyle11.setFont(headerFont);
		headerCellStyle11.setAlignment(HorizontalAlignment.RIGHT);
		headerCellStyle11.setVerticalAlignment(VerticalAlignment.CENTER);
		CellStyle rowCellStyle = workbook.createCellStyle();
		rowCellStyle.setFont(rowFont);

		CellStyle dateCellStyle = workbook.createCellStyle();
		dateCellStyle.setDataFormat(createHelper.createDataFormat().getFormat("dd-MM-yyyy"));

		Row row0 = sheet.createRow(0);
		sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 3));
		row0.createCell(0).setCellValue(company.getCompanyName());

		Row row1 = sheet.createRow(1);
		sheet.addMergedRegion(new CellRangeAddress(1, 1, 0, 1));
		row1.createCell(0).setCellValue(company.getAddress1().getAddressText() + " ,");

		Row row2 = sheet.createRow(2);
		sheet.addMergedRegion(new CellRangeAddress(2, 2, 0, 3));
		row2.createCell(0).setCellValue(company.getAddress1().getLandmark() + " , "
				+ company.getAddress1().getCity().getCityName() + "- " + company.getAddress1().getPincode());

		Row row4 = sheet.createRow(4);
		Cell celll = row4.createCell(0);
		celll.setCellValue("Payroll Register");
		celll.setCellStyle(headerCellStyle11);

		Row row5 = sheet.createRow(5);
		sheet.addMergedRegion(new CellRangeAddress(5, 5, 0, 1));
		row5.createCell(0).setCellValue(fromProcessMonth + toProcessMnth);
		if (reportPayOutList.isEmpty() || reportPayOutList.equals("")) {
			Font headerFont1 = workbook.createFont();
			headerFont1.setBold(true);
			headerFont1.setFontHeightInPoints((short) 16);
			headerFont1.setColor(IndexedColors.BLACK.getIndex());

			CellStyle headerCellStyle12 = workbook.createCellStyle();
			headerCellStyle12.setFont(headerFont);
			headerCellStyle12.setAlignment(HorizontalAlignment.CENTER);
			headerCellStyle12.setVerticalAlignment(VerticalAlignment.CENTER);
			headerCellStyle12.setFillBackgroundColor(IndexedColors.LIGHT_CORNFLOWER_BLUE.getIndex());
			headerCellStyle12.setFillPattern(FillPatternType.FINE_DOTS);

			Row headerRow = sheet.createRow(6);
			sheet.addMergedRegion(new CellRangeAddress(6, 7, 3, 8));
			Cell cell0 = headerRow.createCell(3);
			cell0.setCellValue(" Data not available");
			cell0.setCellStyle(headerCellStyle12);
		} else {
			Row headerRow = sheet.createRow(6);

			for (int i = 0; i < columns.length; i++) {
				Cell cell = headerRow.createCell(i);
				cell.setCellValue(columns[i]);
				cell.setCellStyle(headerCellStyle);
			}
			BigDecimal sumBasic = new BigDecimal(0);
			BigDecimal sumDearnessAllowance = new BigDecimal(0);
			BigDecimal sumConveyanceAllowance = new BigDecimal(0);
			BigDecimal sumHra = new BigDecimal(0);
			BigDecimal sumMedicalAllowance = new BigDecimal(0);
			BigDecimal sumAdvanceBonus = new BigDecimal(0);
			BigDecimal sumSpecialAllowance = new BigDecimal(0);
			BigDecimal sumCompanyBenefits = new BigDecimal(0);
			BigDecimal sumOtherAllowance = new BigDecimal(0);
			BigDecimal sumGrossSalary = new BigDecimal(0);
			BigDecimal sumAbsense = new BigDecimal(0);
			BigDecimal sumCasualleave = new BigDecimal(0);
			BigDecimal sumSeekleave = new BigDecimal(0);
			BigDecimal sumPaidleave = new BigDecimal(0);
			BigDecimal sumPresense = new BigDecimal(0);
			BigDecimal sumPublicholidays = new BigDecimal(0);
			BigDecimal sumWeekoff = new BigDecimal(0);
			BigDecimal totalPayDays = new BigDecimal(0);
			// BigDecimal sumOvertime = new BigDecimal(0);
			BigDecimal sumBasicEarning = new BigDecimal(0);
			BigDecimal sumDearnessAllowanceEarning = new BigDecimal(0);
			BigDecimal sumConveyanceAllowanceEarning = new BigDecimal(0);
			BigDecimal sumHraEarning = new BigDecimal(0);
			BigDecimal sumMedicalAllowanceEarning = new BigDecimal(0);
			BigDecimal sumAdvanceBonusEarning = new BigDecimal(0);
			BigDecimal sumSpecialAllowanceEarning = new BigDecimal(0);
			BigDecimal totalCompanyBenefitsEarning = new BigDecimal(0);
			BigDecimal sumOtherAllowanceEarning = new BigDecimal(0);
			BigDecimal sumTotalEarning = new BigDecimal(0);
			BigDecimal sumLoan = new BigDecimal(0);
			BigDecimal sumProvidentFundEmployee = new BigDecimal(0);
			BigDecimal sumEsi_Employee = new BigDecimal(0);
			BigDecimal sumPt = new BigDecimal(0);
			BigDecimal sumTds = new BigDecimal(0);
			BigDecimal sumTotalDeduction = new BigDecimal(0);
			BigDecimal sumNetPayableAmount = new BigDecimal(0);
			int rowNum = 7;
			for (ReportPayOutDTO reportPayOut : reportPayOutList) {
				Row row = sheet.createRow(rowNum++);
				row.createCell(0).setCellValue(reportPayOut.getProcessMonth());
				row.createCell(1).setCellValue(reportPayOut.getName());
				row.createCell(2).setCellValue(reportPayOut.getEmployeeCode());
				row.createCell(2).setCellValue(reportPayOut.getBankName());
				row.createCell(4).setCellValue(reportPayOut.getAccountNumber());

				Cell dateOfJoining = row.createCell(5);
				dateOfJoining.setCellValue(reportPayOut.getDateOfJoining());
				dateOfJoining.setCellStyle(dateCellStyle);
				sumBasic = sumBasic.add((reportPayOut.getBasic() != null ? reportPayOut.getBasic() : BigDecimal.ZERO));
				row.createCell(6).setCellValue(convertBigdecimalToDouble(reportPayOut.getBasic()));
				sumDearnessAllowance = sumDearnessAllowance
						.add((reportPayOut.getDearnessAllowance() != null ? reportPayOut.getDearnessAllowance()
								: BigDecimal.ZERO));
				row.createCell(7).setCellValue(convertBigdecimalToDouble(reportPayOut.getDearnessAllowance()));
				sumConveyanceAllowance = sumConveyanceAllowance
						.add(reportPayOut.getConveyanceAllowance() != null ? reportPayOut.getConveyanceAllowance()
								: BigDecimal.ZERO);
				row.createCell(8).setCellValue(convertBigdecimalToDouble(reportPayOut.getConveyanceAllowance()));
				sumHra = sumHra.add(reportPayOut.getHra() != null ? reportPayOut.getHra() : BigDecimal.ZERO);
				row.createCell(9).setCellValue(convertBigdecimalToDouble(reportPayOut.getHra()));
				sumMedicalAllowance = sumMedicalAllowance
						.add(reportPayOut.getMedicalAllowance() != null ? reportPayOut.getMedicalAllowance()
								: BigDecimal.ZERO);
				row.createCell(10).setCellValue(convertBigdecimalToDouble(reportPayOut.getMedicalAllowance()));
				sumAdvanceBonus = sumAdvanceBonus
						.add(reportPayOut.getAdvanceBonus() != null ? reportPayOut.getAdvanceBonus() : BigDecimal.ZERO);
				row.createCell(11).setCellValue(convertBigdecimalToDouble(reportPayOut.getAdvanceBonus()));
				sumSpecialAllowance = sumSpecialAllowance
						.add(reportPayOut.getSpecialAllowance() != null ? reportPayOut.getSpecialAllowance()
								: BigDecimal.ZERO);
				row.createCell(12).setCellValue(convertBigdecimalToDouble(reportPayOut.getSpecialAllowance()));
				sumCompanyBenefits = sumCompanyBenefits
						.add(reportPayOut.getCompanyBenefits() != null ? reportPayOut.getCompanyBenefits()
								: BigDecimal.ZERO);
				row.createCell(13).setCellValue(convertBigdecimalToDouble(reportPayOut.getCompanyBenefits()));
				sumOtherAllowance = sumOtherAllowance.add(
						reportPayOut.getOtherAllowance() != null ? reportPayOut.getOtherAllowance() : BigDecimal.ZERO);
				row.createCell(14).setCellValue(convertBigdecimalToDouble(reportPayOut.getOtherAllowance()));
				sumGrossSalary = sumGrossSalary
						.add(reportPayOut.getGrossSalary() != null ? reportPayOut.getGrossSalary() : BigDecimal.ZERO);
				row.createCell(15).setCellValue(convertBigdecimalToDouble(reportPayOut.getGrossSalary()));
				sumAbsense = sumAbsense
						.add(reportPayOut.getAbsense() != null ? reportPayOut.getAbsense() : BigDecimal.ZERO);
				row.createCell(16).setCellValue(convertBigdecimalToDouble(reportPayOut.getAbsense()));
				sumCasualleave = sumCasualleave
						.add(reportPayOut.getCasualleave() != null ? reportPayOut.getCasualleave() : BigDecimal.ZERO);
				row.createCell(17).setCellValue(convertBigdecimalToDouble(reportPayOut.getCasualleave()));
				sumSeekleave = sumSeekleave
						.add(reportPayOut.getSeekleave() != null ? reportPayOut.getSeekleave() : BigDecimal.ZERO);
				row.createCell(18).setCellValue(convertBigdecimalToDouble(reportPayOut.getSeekleave()));
				sumPaidleave = sumPaidleave
						.add(reportPayOut.getPaidleave() != null ? reportPayOut.getPaidleave() : BigDecimal.ZERO);
				row.createCell(19).setCellValue(convertBigdecimalToDouble(reportPayOut.getPaidleave()));
				sumPresense = sumPresense
						.add(reportPayOut.getPresense() != null ? reportPayOut.getPresense() : BigDecimal.ZERO);
				row.createCell(20).setCellValue(convertBigdecimalToDouble(reportPayOut.getPresense()));
				sumPublicholidays = sumPublicholidays.add(
						reportPayOut.getPublicholidays() != null ? reportPayOut.getPublicholidays() : BigDecimal.ZERO);
				row.createCell(21).setCellValue(convertBigdecimalToDouble(reportPayOut.getPublicholidays()));
				sumWeekoff = sumWeekoff
						.add(reportPayOut.getWeekoff() != null ? reportPayOut.getWeekoff() : BigDecimal.ZERO);
				row.createCell(22).setCellValue(convertBigdecimalToDouble(reportPayOut.getWeekoff()));
				// sumOvertime =
				// sumOvertime.add(reportPayOut.getOvertime()!=null?reportPayOut.getOvertime():BigDecimal.ZERO);
				// row.createCell(23).setCellValue(convertBigdecimalToDouble(reportPayOut.getOvertime()));
				totalPayDays = totalPayDays
						.add(reportPayOut.getPayDays() != null ? reportPayOut.getPayDays() : BigDecimal.ZERO);
				row.createCell(23).setCellValue(convertBigdecimalToDouble(reportPayOut.getPayDays()));
				sumBasicEarning = sumBasicEarning
						.add(reportPayOut.getBasicEarning() != null ? reportPayOut.getBasicEarning() : BigDecimal.ZERO);
				row.createCell(24).setCellValue(convertBigdecimalToDouble(reportPayOut.getBasicEarning()));
				sumDearnessAllowanceEarning = sumDearnessAllowanceEarning.add(
						reportPayOut.getDearnessAllowanceEarning() != null ? reportPayOut.getDearnessAllowanceEarning()
								: BigDecimal.ZERO);
				row.createCell(25).setCellValue(convertBigdecimalToDouble(reportPayOut.getDearnessAllowanceEarning()));
				sumConveyanceAllowanceEarning = sumConveyanceAllowanceEarning
						.add(reportPayOut.getConveyanceAllowanceEarning() != null
								? reportPayOut.getConveyanceAllowanceEarning()
								: BigDecimal.ZERO);
				row.createCell(26)
						.setCellValue(convertBigdecimalToDouble(reportPayOut.getConveyanceAllowanceEarning()));
				sumHraEarning = sumHraEarning
						.add(reportPayOut.getHraEarning() != null ? reportPayOut.getHraEarning() : BigDecimal.ZERO);
				row.createCell(27).setCellValue(convertBigdecimalToDouble(reportPayOut.getHraEarning()));
				sumMedicalAllowanceEarning = sumMedicalAllowanceEarning.add(
						reportPayOut.getMedicalAllowanceEarning() != null ? reportPayOut.getMedicalAllowanceEarning()
								: BigDecimal.ZERO);
				row.createCell(28).setCellValue(convertBigdecimalToDouble(reportPayOut.getMedicalAllowanceEarning()));
				sumAdvanceBonusEarning = sumAdvanceBonusEarning
						.add(reportPayOut.getAdvanceBonusEarning() != null ? reportPayOut.getAdvanceBonusEarning()
								: BigDecimal.ZERO);
				row.createCell(29).setCellValue(convertBigdecimalToDouble(reportPayOut.getAdvanceBonusEarning()));
				sumSpecialAllowanceEarning = sumSpecialAllowanceEarning.add(
						reportPayOut.getSpecialAllowanceEarning() != null ? reportPayOut.getSpecialAllowanceEarning()
								: BigDecimal.ZERO);
				row.createCell(30).setCellValue(convertBigdecimalToDouble(reportPayOut.getSpecialAllowanceEarning()));
				totalCompanyBenefitsEarning = totalCompanyBenefitsEarning.add(
						reportPayOut.getCompanyBenefitsEarning() != null ? reportPayOut.getAbsense() : BigDecimal.ZERO);
				row.createCell(31).setCellValue(convertBigdecimalToDouble(reportPayOut.getCompanyBenefitsEarning()));
				sumOtherAllowanceEarning = sumOtherAllowanceEarning
						.add(reportPayOut.getOtherAllowanceEarning() != null ? reportPayOut.getOtherAllowanceEarning()
								: BigDecimal.ZERO);
				row.createCell(32).setCellValue(convertBigdecimalToDouble(reportPayOut.getOtherAllowanceEarning()));
				sumTotalEarning = sumTotalEarning
						.add(reportPayOut.getTotalEarning() != null ? reportPayOut.getTotalEarning() : BigDecimal.ZERO);
				row.createCell(33).setCellValue(convertBigdecimalToDouble(reportPayOut.getTotalEarning()));
				sumLoan = sumLoan.add(reportPayOut.getLoan() != null ? reportPayOut.getLoan() : BigDecimal.ZERO);
				row.createCell(34).setCellValue(convertBigdecimalToDouble(reportPayOut.getLoan()));
				sumProvidentFundEmployee = sumProvidentFundEmployee
						.add(reportPayOut.getProvidentFundEmployee() != null ? reportPayOut.getProvidentFundEmployee()
								: BigDecimal.ZERO);
				row.createCell(35).setCellValue(convertBigdecimalToDouble(reportPayOut.getProvidentFundEmployee()));
				sumEsi_Employee = sumEsi_Employee
						.add(reportPayOut.getEsi_Employee() != null ? reportPayOut.getEsi_Employee() : BigDecimal.ZERO);
				row.createCell(36).setCellValue(convertBigdecimalToDouble(reportPayOut.getEsi_Employee()));
				sumPt = sumPt.add(reportPayOut.getPt() != null ? reportPayOut.getPt() : BigDecimal.ZERO);
				row.createCell(37).setCellValue(convertBigdecimalToDouble(reportPayOut.getPt()));
				sumTds = sumTds.add(reportPayOut.getTds() != null ? reportPayOut.getTds() : BigDecimal.ZERO);
				row.createCell(38).setCellValue(convertBigdecimalToDouble(reportPayOut.getTds()));
				sumTotalDeduction = sumTotalDeduction.add(
						reportPayOut.getTotalDeduction() != null ? reportPayOut.getTotalDeduction() : BigDecimal.ZERO);
				row.createCell(39).setCellValue(convertBigdecimalToDouble(reportPayOut.getTotalDeduction()));
				sumNetPayableAmount = sumNetPayableAmount
						.add(reportPayOut.getNetPayableAmount() != null ? reportPayOut.getNetPayableAmount()
								: BigDecimal.ZERO);
				row.createCell(40).setCellValue(convertBigdecimalToDouble(reportPayOut.getNetPayableAmount()));
			}

			headerCellStyle.setAlignment(HorizontalAlignment.RIGHT);
			headerCellStyle.setVerticalAlignment(VerticalAlignment.JUSTIFY);
			Row row = sheet.createRow(rowNum);
			for (int i = 0; i < 5; i++) {
				Cell cell11 = row.createCell(i);
				cell11.setCellStyle(headerCellStyle1);
			}
			Cell cell0 = row.createCell(5);
			cell0.setCellValue("Grand Total");
			cell0.setCellStyle(headerCellStyle1);

			Cell cell = row.createCell(6);
			cell.setCellValue(convertBigdecimalToDouble(sumBasic));
			cell.setCellStyle(headerCellStyle1);

			Cell cell1 = row.createCell(7);
			cell1.setCellValue(convertBigdecimalToDouble(sumDearnessAllowance));
			cell1.setCellStyle(headerCellStyle1);

			Cell cell2 = row.createCell(8);
			cell2.setCellValue(convertBigdecimalToDouble(sumConveyanceAllowance));
			cell2.setCellStyle(headerCellStyle1);

			Cell cell3 = row.createCell(9);
			cell3.setCellValue(convertBigdecimalToDouble(sumHra));
			cell3.setCellStyle(headerCellStyle1);

			Cell cell4 = row.createCell(10);
			cell4.setCellValue(convertBigdecimalToDouble(sumMedicalAllowance));
			cell4.setCellStyle(headerCellStyle1);

			Cell cell5 = row.createCell(11);
			cell5.setCellValue(convertBigdecimalToDouble(sumAdvanceBonus));
			cell5.setCellStyle(headerCellStyle1);

			Cell cell6 = row.createCell(12);
			cell6.setCellValue(convertBigdecimalToDouble(sumSpecialAllowance));
			cell6.setCellStyle(headerCellStyle1);

			Cell cell8 = row.createCell(13);
			cell8.setCellValue(convertBigdecimalToDouble(sumCompanyBenefits));
			cell8.setCellStyle(headerCellStyle1);

			Cell cell9 = row.createCell(14);
			cell9.setCellValue(convertBigdecimalToDouble(sumOtherAllowance));
			cell9.setCellStyle(headerCellStyle1);

			Cell cell10 = row.createCell(15);
			cell10.setCellValue(convertBigdecimalToDouble(sumGrossSalary));
			cell10.setCellStyle(headerCellStyle1);

			Cell cell11 = row.createCell(16);
			cell11.setCellValue(convertBigdecimalToDouble(sumAbsense));
			cell11.setCellStyle(headerCellStyle1);

			Cell cell12 = row.createCell(17);
			cell12.setCellValue(convertBigdecimalToDouble(sumCasualleave));
			cell12.setCellStyle(headerCellStyle1);

			Cell cell13 = row.createCell(18);
			cell13.setCellValue(convertBigdecimalToDouble(sumSeekleave));
			cell13.setCellStyle(headerCellStyle1);

			Cell cell14 = row.createCell(19);
			cell14.setCellValue(convertBigdecimalToDouble(sumPaidleave));
			cell14.setCellStyle(headerCellStyle1);

			Cell cell15 = row.createCell(20);
			cell15.setCellValue(convertBigdecimalToDouble(sumPresense));
			cell15.setCellStyle(headerCellStyle1);

			Cell cell16 = row.createCell(21);
			cell16.setCellValue(convertBigdecimalToDouble(sumPublicholidays));
			cell16.setCellStyle(headerCellStyle1);

			Cell cell17 = row.createCell(22);
			cell17.setCellValue(convertBigdecimalToDouble(sumWeekoff));
			cell17.setCellStyle(headerCellStyle1);

			/*
			 * Cell cell18 = row.createCell(23);
			 * cell18.setCellValue(convertBigdecimalToDouble(sumOvertime));
			 * cell18.setCellStyle(headerCellStyle1);
			 */
			Cell cell19 = row.createCell(23);
			cell19.setCellValue(convertBigdecimalToDouble(totalPayDays));
			cell19.setCellStyle(headerCellStyle1);

			Cell cell20 = row.createCell(24);
			cell20.setCellValue(convertBigdecimalToDouble(sumBasicEarning));
			cell20.setCellStyle(headerCellStyle1);

			Cell cell21 = row.createCell(25);
			cell21.setCellValue(convertBigdecimalToDouble(sumDearnessAllowanceEarning));
			cell21.setCellStyle(headerCellStyle1);

			Cell cell22 = row.createCell(26);
			cell22.setCellValue(convertBigdecimalToDouble(sumConveyanceAllowanceEarning));
			cell22.setCellStyle(headerCellStyle1);

			Cell cell23 = row.createCell(27);
			cell23.setCellValue(convertBigdecimalToDouble(sumHraEarning));
			cell23.setCellStyle(headerCellStyle1);

			Cell cell24 = row.createCell(28);
			cell24.setCellValue(convertBigdecimalToDouble(sumMedicalAllowanceEarning));
			cell24.setCellStyle(headerCellStyle1);

			Cell cell25 = row.createCell(29);
			cell25.setCellValue(convertBigdecimalToDouble(sumAdvanceBonusEarning));
			cell25.setCellStyle(headerCellStyle1);

			Cell cell26 = row.createCell(30);
			cell26.setCellValue(convertBigdecimalToDouble(sumSpecialAllowanceEarning));
			cell26.setCellStyle(headerCellStyle1);

			Cell cell27 = row.createCell(31);
			cell27.setCellValue(convertBigdecimalToDouble(totalCompanyBenefitsEarning));
			cell27.setCellStyle(headerCellStyle1);

			Cell cell28 = row.createCell(32);
			cell28.setCellValue(convertBigdecimalToDouble(sumOtherAllowanceEarning));
			cell28.setCellStyle(headerCellStyle1);

			Cell cell29 = row.createCell(33);
			cell29.setCellValue(convertBigdecimalToDouble(sumTotalEarning));
			cell29.setCellStyle(headerCellStyle1);

			Cell cell30 = row.createCell(34);
			cell30.setCellValue(convertBigdecimalToDouble(sumLoan));
			cell30.setCellStyle(headerCellStyle1);

			Cell cell31 = row.createCell(35);
			cell31.setCellValue(convertBigdecimalToDouble(sumProvidentFundEmployee));
			cell31.setCellStyle(headerCellStyle1);

			Cell cell32 = row.createCell(36);
			cell32.setCellValue(convertBigdecimalToDouble(sumEsi_Employee));
			cell32.setCellStyle(headerCellStyle1);

			Cell cell33 = row.createCell(37);
			cell33.setCellValue(convertBigdecimalToDouble(sumPt));
			cell33.setCellStyle(headerCellStyle1);

			Cell cell34 = row.createCell(38);
			cell34.setCellValue(convertBigdecimalToDouble(sumTds));
			cell34.setCellStyle(headerCellStyle1);

			Cell cell35 = row.createCell(39);
			cell35.setCellValue(convertBigdecimalToDouble(sumTotalDeduction));
			cell35.setCellStyle(headerCellStyle1);

			Cell cell36 = row.createCell(40);
			cell36.setCellValue(convertBigdecimalToDouble(sumNetPayableAmount));
			cell36.setCellStyle(headerCellStyle1);

			// Resize all columns to fit the content size
			for (int i = 0; i < columns.length; i++) {
				sheet.autoSizeColumn(i);
			}
		}
		return workbook;
	}

	public static Workbook earningDeductionReport(List<OneTimeEarningDeductionDTO> earningDeductionReportList,
			String[] columns, String processMonth, String type) throws IOException, InvalidFormatException {

		Workbook workbook = new XSSFWorkbook(); // new HSSFWorkbook() for generating `.xls` file

		CreationHelper createHelper = workbook.getCreationHelper();

		// Sheet sheet = workbook.createSheet("One Time Earnings-Dept Wise");
		Sheet sheet = null;
		if (type.equals("EA")) {
			sheet = workbook.createSheet("One Time Earnings-Dept Wise");
		} else if (type.equals("DE")) {
			sheet = workbook.createSheet("One Time Deduction-Dept Wise");
		}

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

		CellStyle headerCellStyle1 = workbook.createCellStyle();
		headerCellStyle1.setFont(headerFont);
		headerCellStyle1.setAlignment(HorizontalAlignment.RIGHT);
		headerCellStyle1.setVerticalAlignment(VerticalAlignment.CENTER);
		headerCellStyle1.setFillBackgroundColor(IndexedColors.LIGHT_CORNFLOWER_BLUE.getIndex());
		headerCellStyle1.setFillPattern(FillPatternType.FINE_DOTS);

		CellStyle headerCellStyle11 = workbook.createCellStyle();
		headerCellStyle11.setFont(headerFont);
		headerCellStyle11.setAlignment(HorizontalAlignment.LEFT);
		headerCellStyle11.setVerticalAlignment(VerticalAlignment.CENTER);
		CellStyle headerCellStyle111 = workbook.createCellStyle();
		headerCellStyle111.setFont(headerFont);
		headerCellStyle111.setAlignment(HorizontalAlignment.LEFT);
		headerCellStyle111.setVerticalAlignment(VerticalAlignment.CENTER);

		CellStyle headerCellStyle112 = workbook.createCellStyle();
		headerCellStyle112.setAlignment(HorizontalAlignment.LEFT);
		headerCellStyle112.setVerticalAlignment(VerticalAlignment.CENTER);

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

		CellStyle addZero = workbook.createCellStyle();
		addZero.setDataFormat(createHelper.createDataFormat().getFormat("0.00"));
		addZero.setAlignment(HorizontalAlignment.RIGHT);
		addZero.setVerticalAlignment(VerticalAlignment.CENTER);

		CellStyle addZero1 = workbook.createCellStyle();
		addZero1.setDataFormat(createHelper.createDataFormat().getFormat("0.00"));
		addZero1.setAlignment(HorizontalAlignment.RIGHT);
		addZero1.setVerticalAlignment(VerticalAlignment.CENTER);

		CellStyle addZeroBold = workbook.createCellStyle();
		addZeroBold.setDataFormat(createHelper.createDataFormat().getFormat("0.00"));
		addZeroBold.setFont(headerFont);
		addZeroBold.setAlignment(HorizontalAlignment.RIGHT);
		addZeroBold.setVerticalAlignment(VerticalAlignment.CENTER);

		// Row row0 = sheet.createRow(0);
		// sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 10));
		// Cell cellCom = row0.createCell(0);
		// cellCom.setCellValue("One Time Earnings -" + processMonth);
		// cellCom.setCellStyle(headerCellStyle111);

		if (type.equals("EA")) {
			Row row0 = sheet.createRow(0);
			sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 10));
			Cell cellCom = row0.createCell(0);
			cellCom.setCellValue("One Time Earning -" + processMonth);
			cellCom.setCellStyle(headerCellStyle111);
		} else if (type.equals("DE")) {
			Row row0 = sheet.createRow(0);
			sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 10));
			Cell cellCom = row0.createCell(0);
			cellCom.setCellValue("One Time Deduction -" + processMonth);
			cellCom.setCellStyle(headerCellStyle111);
		}

		Row headerRow = sheet.createRow(1);
		// Creating cells
		for (int i = 0; i < columns.length; i++) {
			Cell cell = headerRow.createCell(i);
			cell.setCellValue(columns[i]);
			cell.setCellStyle(headerCellStyle);

		}

		CellStyle dateCellStyle = workbook.createCellStyle();
		dateCellStyle.setDataFormat(createHelper.createDataFormat().getFormat("dd-MM-yyyy"));
		int rowNum = 2;

		BigDecimal sum = BigDecimal.ZERO;

		if (earningDeductionReportList != null) {

			for (OneTimeEarningDeductionDTO OneTimeEarningDeduction : earningDeductionReportList) {

				Row row = sheet.createRow(rowNum++);

				if (OneTimeEarningDeduction.getEmployeeCode() != null
						&& OneTimeEarningDeduction.getEmployeeCode().trim() != "")
					row.createCell(0).setCellValue(OneTimeEarningDeduction.getEmployeeCode());
				else
					row.createCell(0).setCellValue("Under Process");

				row.createCell(1).setCellValue(OneTimeEarningDeduction.getEmployeeName());
				row.createCell(2).setCellValue(OneTimeEarningDeduction.getDesignationName());
				row.createCell(3).setCellValue(OneTimeEarningDeduction.getDepartmentName());
				row.createCell(4).setCellValue(OneTimeEarningDeduction.getDeductionType());
				sum = sum.add(OneTimeEarningDeduction.getAmount());

				Cell celltotalearning = row.createCell(5);
				celltotalearning.setCellValue(OneTimeEarningDeduction.getAmount().doubleValue());
				celltotalearning.setCellStyle(addZero1);
				// row.createCell(5).setCellValue(convertBigdecimalToString(OneTimeEarningDeduction.getAmount()));
				row.createCell(6).setCellValue(OneTimeEarningDeduction.getRemarks());
			}
		}
		System.out.println("hi");
		headerCellStyle.setVerticalAlignment(VerticalAlignment.JUSTIFY);

		Row rowNew = sheet.createRow(rowNum);
		sheet.addMergedRegion(new CellRangeAddress(rowNum, rowNum, 0, 4));
		Cell cell3 = rowNew.createCell(0);

		cell3.setCellValue("Total Amount");
		cell3.setCellStyle(cellStyleCentre);
		Cell cell5 = rowNew.createCell(5);
		cell5.setCellValue(sum.doubleValue());
		cell5.setCellStyle(addZeroBold);

		// Resize all columns to fit the content size
		for (int i = 0; i < columns.length; i++) {
			sheet.autoSizeColumn(i);
		}

		/*
		 * // Write the output to a file FileOutputStream fileOut = new
		 * FileOutputStream("E:\\poi-epf.xlsx"); workbook.write(fileOut);
		 * fileOut.close();
		 */
		return workbook;
	}

	public static Workbook payrollEsicEcrReport(List<ReportPayOutDTO> reportPayOutList, String[] columns,
			String processMonth, Company company, ReportSummaryDTO reportSummary)
			throws IOException, InvalidFormatException {

		Workbook workbook = new XSSFWorkbook(); // new HSSFWorkbook() for generating `.xls` file

		CreationHelper createHelper = workbook.getCreationHelper();

		Sheet sheet = workbook.createSheet("ESI-ECR");

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

		CellStyle headerCellStyle1 = workbook.createCellStyle();
		headerCellStyle1.setFont(headerFont);
		headerCellStyle1.setAlignment(HorizontalAlignment.RIGHT);
		headerCellStyle1.setVerticalAlignment(VerticalAlignment.CENTER);
		headerCellStyle1.setFillBackgroundColor(IndexedColors.LIGHT_CORNFLOWER_BLUE.getIndex());
		headerCellStyle1.setFillPattern(FillPatternType.FINE_DOTS);
		CellStyle headerCellStyle11 = workbook.createCellStyle();
		headerCellStyle11.setFont(headerFont);
		headerCellStyle11.setAlignment(HorizontalAlignment.LEFT);
		headerCellStyle11.setVerticalAlignment(VerticalAlignment.CENTER);
		CellStyle headerCellStyle111 = workbook.createCellStyle();
		headerCellStyle111.setFont(headerFont);
		headerCellStyle111.setAlignment(HorizontalAlignment.CENTER);
		headerCellStyle111.setVerticalAlignment(VerticalAlignment.CENTER);
		CellStyle headerCellStyle112 = workbook.createCellStyle();
		headerCellStyle112.setAlignment(HorizontalAlignment.LEFT);
		headerCellStyle112.setVerticalAlignment(VerticalAlignment.CENTER);
		CellStyle cellStyle112 = workbook.createCellStyle();
		cellStyle112.setAlignment(HorizontalAlignment.RIGHT);
		cellStyle112.setVerticalAlignment(VerticalAlignment.CENTER);

		CellStyle headerCellStyleTotal = workbook.createCellStyle();
		headerCellStyleTotal.setAlignment(HorizontalAlignment.CENTER);
		headerCellStyleTotal.setVerticalAlignment(VerticalAlignment.CENTER);

		CellStyle darkFont = workbook.createCellStyle();
		darkFont.setFont(headerFont);
		darkFont.setAlignment(HorizontalAlignment.RIGHT);
		darkFont.setVerticalAlignment(VerticalAlignment.CENTER);

		CellStyle addZero = workbook.createCellStyle();
		addZero.setDataFormat(createHelper.createDataFormat().getFormat("0.00"));
		addZero.setAlignment(HorizontalAlignment.RIGHT);
		addZero.setVerticalAlignment(VerticalAlignment.CENTER);

		CellStyle addZero1 = workbook.createCellStyle();
		addZero1.setDataFormat(createHelper.createDataFormat().getFormat("0.00"));
		addZero1.setAlignment(HorizontalAlignment.RIGHT);
		addZero1.setVerticalAlignment(VerticalAlignment.CENTER);

		CellStyle addZeroLeft = workbook.createCellStyle();
		addZeroLeft.setDataFormat(createHelper.createDataFormat().getFormat("0.00"));
		addZeroLeft.setAlignment(HorizontalAlignment.LEFT);
		addZeroLeft.setVerticalAlignment(VerticalAlignment.CENTER);

		CellStyle addZeroBold = workbook.createCellStyle();
		addZeroBold.setDataFormat(createHelper.createDataFormat().getFormat("0.00"));
		addZeroBold.setFont(headerFont);
		addZeroBold.setAlignment(HorizontalAlignment.RIGHT);
		addZeroBold.setVerticalAlignment(VerticalAlignment.CENTER);
		// Row row0 = sheet.createRow(0);
		// sheet.addMergedRegion(new CellRangeAddress(0,0,0,5));
		// Cell cellCom=row0.createCell(0);
		// cellCom.setCellValue(company.getCompanyName());
		// cellCom.setCellStyle(headerCellStyle111);

		Row row1 = sheet.createRow(0);
		sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 5));
		Cell cellAdd = row1.createCell(0);
		cellAdd.setCellValue("ESI ECR - " + processMonth);
		cellAdd.setCellStyle(headerCellStyle111);

		Row row2 = sheet.createRow(1);
		sheet.addMergedRegion(new CellRangeAddress(1, 1, 0, 5));
		row2.createCell(0).setCellValue("ESIC Code - " + company.getEsicNo());
		// row1.createCell(1).setCellValue(company.getEpfNo());

		Row row3 = sheet.createRow(2);
		sheet.addMergedRegion(new CellRangeAddress(2, 2, 0, 2));
		row3.createCell(0).setCellValue("Total Number of Employees ");
		sheet.addMergedRegion(new CellRangeAddress(2, 2, 3, 5));

		if (!reportSummary.getEpfExcludedEmpCount().testBit(0))
			row3.createCell(3).setCellValue(reportSummary.getEpfExcludedEmpCount().toString());
		else
			row3.createCell(3).setCellValue(0);

		// Row row4 = sheet.createRow(3);
		// sheet.addMergedRegion(new CellRangeAddress(3,3,0,2));
		// row4.createCell(0).setCellValue("Total Number of Excluded Employees" );
		// sheet.addMergedRegion(new CellRangeAddress(3,3,3,5));
		// Cell cellemp= row4.createCell(3);
		// cellemp .setCellValue(reportSummary.getEpfExcludedEmpCount().doubleValue());
		// cellemp.setCellStyle(headerCellStyle112);

		Row row5 = sheet.createRow(3);
		sheet.addMergedRegion(new CellRangeAddress(3, 3, 0, 2));
		row5.createCell(0).setCellValue("Gross Wages of Excluded Employees ");
		sheet.addMergedRegion(new CellRangeAddress(3, 3, 4, 5));

		Cell cellSum = row5.createCell(3);
		if (reportSummary.getEpfExcludedGrassSum() != null) {
			cellSum.setCellValue(reportSummary.getEpfExcludedGrassSum().doubleValue());
			cellSum.setCellStyle(addZeroLeft);
		} else {
			cellSum.setCellValue(new BigDecimal(0.00).doubleValue());
			cellSum.setCellStyle(addZeroLeft);
		}
		// Row row6 = sheet.createRow(5);
		// sheet.addMergedRegion(new CellRangeAddress(5,5,0,2));
		// row6.createCell(0).setCellValue("Month -"+processMonth);
		// sheet.addMergedRegion(new CellRangeAddress(5,5,3,5));
		// row6.createCell(3).setCellValue("");

		Row row7 = sheet.createRow(4);
		sheet.addMergedRegion(new CellRangeAddress(4, 4, 0, 2));
		row7.createCell(0).setCellValue(" ");
		// sheet.addMergedRegion(new CellRangeAddress(4, 4, 3, 5));

		int j = 1;
		Row headerRow = sheet.createRow(5);
		// Creating cells
		for (int i = 0; i < columns.length; i++) {

			Cell cell = headerRow.createCell(i);
			cell.setCellValue(columns[i]);
			cell.setCellStyle(headerCellStyle);

		}

		CellStyle dateCellStyle = workbook.createCellStyle();
		dateCellStyle.setDataFormat(createHelper.createDataFormat().getFormat("dd-MM-yyyy"));

		int rowNum = 6;
		BigDecimal sum = BigDecimal.ZERO;
		if (reportPayOutList != null) {

			for (ReportPayOutDTO reportPayOut : reportPayOutList) {

				Row row = sheet.createRow(rowNum++);

				if (reportPayOut.getEsiNo() != null && reportPayOut.getEsiNo().trim() != "")
					row.createCell(0).setCellValue(reportPayOut.getEsiNo());
				else
					row.createCell(0).setCellValue("Under Process");
				sum = sum.add(reportPayOut.getTotalEarning());
				row.createCell(1).setCellValue(reportPayOut.getName());
				row.createCell(2).setCellValue(convertBigdecimalToDouble(reportPayOut.getPayDays()));
				Cell cellTotal = row.createCell(3);
				cellTotal.setCellValue(convertBigdecimalToDouble(reportPayOut.getTotalEarning()));
				cellTotal.setCellStyle(addZero1);
				row.createCell(4).setCellValue("");
				row.createCell(5).setCellValue("");

			}

		}

		headerCellStyle.setVerticalAlignment(VerticalAlignment.JUSTIFY);

		Row rowNew = sheet.createRow(rowNum);
		sheet.addMergedRegion(new CellRangeAddress(rowNum, rowNum, 0, 2));
		Cell cell3 = rowNew.createCell(0);

		cell3.setCellValue("Total ");
		cell3.setCellStyle(headerCellStyleTotal);

		Cell cell4 = rowNew.createCell(3);
		cell4.setCellValue(sum.doubleValue());
		cell4.setCellStyle(addZeroBold);

		// Resize all columns to fit the content size
		for (int i = 0; i < columns.length; i++) {
			sheet.autoSizeColumn(i);
		}

		/*
		 * // Write the output to a file FileOutputStream fileOut = new
		 * FileOutputStream("E:\\poi-epf.xlsx"); workbook.write(fileOut);
		 * fileOut.close();
		 */
		return workbook;
	}

	public static Workbook payrollEpfEcrReport(List<ReportPayOutDTO> reportPayOutList, String[] columns,
			String processMonth, Company company, List<Object[]> empCount, List<Object[]> totalSum)
			throws IOException, InvalidFormatException {

		Workbook workbook = new XSSFWorkbook(); // new HSSFWorkbook() for generating `.xls` file

		CreationHelper createHelper = workbook.getCreationHelper();

		Sheet sheet = workbook.createSheet("EPF-ECR");

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

		CellStyle headerCellStyle1 = workbook.createCellStyle();
		headerCellStyle1.setFont(headerFont);
		headerCellStyle1.setAlignment(HorizontalAlignment.RIGHT);
		headerCellStyle1.setVerticalAlignment(VerticalAlignment.CENTER);
		headerCellStyle1.setFillBackgroundColor(IndexedColors.LIGHT_CORNFLOWER_BLUE.getIndex());
		headerCellStyle1.setFillPattern(FillPatternType.FINE_DOTS);

		CellStyle headerCellStyle11 = workbook.createCellStyle();
		headerCellStyle11.setFont(headerFont);
		headerCellStyle11.setAlignment(HorizontalAlignment.LEFT);
		headerCellStyle11.setVerticalAlignment(VerticalAlignment.CENTER);
		CellStyle headerCellStyle111 = workbook.createCellStyle();
		headerCellStyle111.setFont(headerFont);
		headerCellStyle111.setAlignment(HorizontalAlignment.CENTER);
		headerCellStyle111.setVerticalAlignment(VerticalAlignment.CENTER);

		CellStyle headerCellStyle112 = workbook.createCellStyle();
		headerCellStyle112.setAlignment(HorizontalAlignment.LEFT);
		headerCellStyle112.setVerticalAlignment(VerticalAlignment.CENTER);

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

		CellStyle addZero1 = workbook.createCellStyle();
		addZero1.setDataFormat(createHelper.createDataFormat().getFormat("0.00"));
		addZero1.setAlignment(HorizontalAlignment.LEFT);
		addZero1.setVerticalAlignment(VerticalAlignment.CENTER);

		Row row0 = sheet.createRow(0);
		sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 10));
		Cell cellCom = row0.createCell(0);
		cellCom.setCellValue("EPF-ECR -" + processMonth);
		cellCom.setCellStyle(headerCellStyle111);

		Row row1 = sheet.createRow(1);
		sheet.addMergedRegion(new CellRangeAddress(1, 1, 0, 10));
		Cell cellAdd = row1.createCell(0);
		cellAdd.setCellValue("PF Code -" + company.getEpfNo());
		cellAdd.setCellStyle(headerCellStyleCode);

		for (Object[] epfObj : empCount) {

			Integer totalNoOfEmployee = epfObj[0] != null ? Integer.parseInt(epfObj[0].toString()) : new Integer(0);

			Row row3 = sheet.createRow(2);
			sheet.addMergedRegion(new CellRangeAddress(2, 2, 0, 3));
			row3.createCell(0).setCellValue("Total Number of Employees ");
			sheet.addMergedRegion(new CellRangeAddress(2, 2, 4, 10));

			Cell totalEmp = row3.createCell(4);
			totalEmp.setCellStyle(headerCellStyle112);
			totalEmp.setCellValue(totalNoOfEmployee);

			// row3.createCell(4).setCellValue(totalNoOfEmployee.doubleValue());

			Integer excludedEmployee = epfObj[1] != null ? Integer.parseInt(epfObj[1].toString()) : new Integer(0);
			Row row4 = sheet.createRow(3);
			sheet.addMergedRegion(new CellRangeAddress(3, 3, 0, 3));
			row4.createCell(0).setCellValue("Total Number of Excluded Employees");
			sheet.addMergedRegion(new CellRangeAddress(3, 3, 4, 10));

			Cell cellemp = row4.createCell(4);
			cellemp.setCellValue(excludedEmployee);
			cellemp.setCellStyle(headerCellStyle112);

			Row row5 = sheet.createRow(4);
			sheet.addMergedRegion(new CellRangeAddress(4, 4, 0, 3));

			row5.createCell(0).setCellValue("Gross Wages of Excluded Employees ");
			sheet.addMergedRegion(new CellRangeAddress(4, 4, 4, 10));

			BigDecimal grossSalary = epfObj[2] != null ? (new BigDecimal(epfObj[2].toString())) : new BigDecimal(0.00);
			Cell cellSum = row5.createCell(4);

			if (grossSalary.compareTo(new BigDecimal(0.00)) > 0) {
				cellSum.setCellValue(grossSalary.doubleValue());
				cellSum.setCellStyle(addZero1);
			} else {
				cellSum.setCellValue(new BigDecimal(0.00).doubleValue());
				cellSum.setCellStyle(addZero1);
			}

		}

		CellStyle addZero = workbook.createCellStyle();
		addZero.setDataFormat(createHelper.createDataFormat().getFormat("0.00"));
		addZero.setAlignment(HorizontalAlignment.RIGHT);
		addZero.setVerticalAlignment(VerticalAlignment.CENTER);

		CellStyle addZeroBold = workbook.createCellStyle();
		addZeroBold.setDataFormat(createHelper.createDataFormat().getFormat("0.00"));
		addZeroBold.setFont(headerFont);
		addZeroBold.setAlignment(HorizontalAlignment.RIGHT);
		addZeroBold.setVerticalAlignment(VerticalAlignment.CENTER);

		CellStyle addZeroBold1 = workbook.createCellStyle();
		addZeroBold1.setDataFormat(createHelper.createDataFormat().getFormat("0.00"));
		addZeroBold1.setFont(headerFont);
		addZeroBold1.setAlignment(HorizontalAlignment.LEFT);
		addZeroBold1.setVerticalAlignment(VerticalAlignment.CENTER);

		Row headerRow = sheet.createRow(5);
		// Creating cells
		for (int i = 0; i < columns.length; i++) {
			Cell cell = headerRow.createCell(i);
			cell.setCellValue(columns[i]);
			cell.setCellStyle(headerCellStyle);

		}

		CellStyle dateCellStyle = workbook.createCellStyle();
		dateCellStyle.setDataFormat(createHelper.createDataFormat().getFormat("dd-MM-yyyy"));

		int rowNum = 6;
		BigDecimal sum = BigDecimal.ZERO;
		BigDecimal totalEarning = BigDecimal.ZERO;
		BigDecimal basicEarning = BigDecimal.ZERO;
		BigDecimal pensionEarningSalary = BigDecimal.ZERO;
		BigDecimal ProvidentFundEmployee = BigDecimal.ZERO;
		BigDecimal providentFundEmployeer = BigDecimal.ZERO;
		BigDecimal providentFundEmployerPension = BigDecimal.ZERO;
		BigDecimal refund = BigDecimal.ZERO;

		BigDecimal epfWages = new BigDecimal(0);
		BigDecimal epsWages = new BigDecimal(0);
		BigDecimal edliWages = new BigDecimal(0);
		BigDecimal epfContri = new BigDecimal(0);
		BigDecimal epsContri = new BigDecimal(0);
		BigDecimal epfEps = new BigDecimal(0);
		BigDecimal grossWages = new BigDecimal(0);

		if (reportPayOutList != null) {

			for (ReportPayOutDTO reportPayOut : reportPayOutList) {

				Row row = sheet.createRow(rowNum++);

				if (reportPayOut.getUnNo() != null && reportPayOut.getUnNo().trim() != "")
					row.createCell(0).setCellValue(reportPayOut.getUnNo());
				else
					row.createCell(0).setCellValue("Under Process");

				row.createCell(1).setCellValue(reportPayOut.getName());

				grossWages = reportPayOut.getTotalEarning() != null ? reportPayOut.getTotalEarning() : BigDecimal.ZERO;
				Cell celltotalearning = row.createCell(2);
				celltotalearning.setCellValue(grossWages.doubleValue());
				celltotalearning.setCellStyle(addZero);

				// BigDecimal totalGrossWages =totalGrossWages+ reportPayOut.getTotalEarning();
				//
				// String totalGrossWages=
				// convertBigdecimalToString(reportPayOut.getTotalEarning());

				String str = FormateUtil.ExcelDataPattern(convertBigdecimalToString(reportPayOut.getBasicEarning()));

				// EarnedStdEpfWagesAmount=reportPayOut.getBasicEarning()
				sum = (reportPayOut.getBasicEarning() != null ? reportPayOut.getBasicEarning() : BigDecimal.ZERO)
						.add(sum);

				CellStyle cellStyle = workbook.createCellStyle();
				cellStyle.setDataFormat(workbook.getCreationHelper().createDataFormat().getFormat(str));

				// Cell cellEarning = row.createCell(3);
				// cellEarning.setCellValue(convertBigdecimalToString(((reportPayOut.getBasicEarning()!=null)?reportPayOut.getBasicEarning():BigDecimal.ZERO).add((reportPayOut.getDearnessAllowanceEarning()!=null)?reportPayOut.getDearnessAllowanceEarning():BigDecimal.ZERO)));
				// cellEarning.setCellStyle(cellStyle112);

				// EarnedStdEpfWagesAmount=reportPayOut.getBasicEarning()

				// emiSum = emiSum.add(loanIssuesDto.getEmiAmount());
				// emiAmount = loanIssueDTO.getEmiAmount() != null ? loanIssueDTO.getEmiAmount()
				// : BigDecimal.ZERO;
				//
				// Cell cell1 = row.createCell(1);
				// cell1.setCellValue(emiAmount.doubleValue());
				// cell1.setCellStyle(addZero);

				epfWages = reportPayOut.getBasicEarning() != null ? reportPayOut.getBasicEarning() : BigDecimal.ZERO;
				Cell cellEarning = row.createCell(3);
				cellEarning.setCellValue(epfWages.doubleValue());
				cellEarning.setCellStyle(addZero);

				epsWages = reportPayOut.getPensionEarningSalary() != null ? reportPayOut.getPensionEarningSalary()
						: BigDecimal.ZERO;
				Cell cellpension = row.createCell(4);
				cellpension.setCellValue(epsWages.doubleValue());
				cellpension.setCellStyle(addZero);

				edliWages = reportPayOut.getPensionEarningSalary() != null ? reportPayOut.getPensionEarningSalary()
						: BigDecimal.ZERO;
				Cell cellpensionearning = row.createCell(5);
				cellpensionearning.setCellValue(edliWages.doubleValue());
				cellpensionearning.setCellStyle(addZero);

				epfContri = reportPayOut.getProvidentFundEmployee() != null ? reportPayOut.getProvidentFundEmployee()
						: BigDecimal.ZERO;
				Cell cellProvidentFund = row.createCell(6);
				cellProvidentFund.setCellValue(epfContri.doubleValue());
				cellProvidentFund.setCellStyle(addZero);

				epsContri = reportPayOut.getProvidentFundEmployerPension() != null
						? reportPayOut.getProvidentFundEmployerPension()
						: BigDecimal.ZERO;
				Cell cellProvident = row.createCell(7);
				cellProvident.setCellValue(epsContri.doubleValue());
				cellProvident.setCellStyle(addZero);

				epfEps = reportPayOut.getProvidentFundEmployer() != null ? reportPayOut.getProvidentFundEmployer()
						: BigDecimal.ZERO;
				Cell cellProfund = row.createCell(8);
				cellProfund.setCellValue(epfEps.doubleValue());
				cellProfund.setCellStyle(addZero);
				System.out.println("hi");
				row.createCell(9).setCellValue(convertBigdecimalToDouble(reportPayOut.getAbsense()));

				Cell cell = row.createCell(10);
				// cell.setCellValue( "0.00");
				cell.setCellValue(0.00);
				cell.setCellStyle(addZero1);
			}

		}
		// System.out.println("hiii");

		headerCellStyle.setVerticalAlignment(VerticalAlignment.JUSTIFY);

		Row rowNew = sheet.createRow(rowNum);
		sheet.addMergedRegion(new CellRangeAddress(rowNum, rowNum, 0, 1));
		Cell cell3 = rowNew.createCell(0);

		cell3.setCellValue("Total ");
		cell3.setCellStyle(cellStyleCentre);
		System.out.println("Sum============================  " + sum);
		for (Object[] total : totalSum) {

			totalEarning = total[0] != null ? (new BigDecimal(total[0].toString())) : null;
			basicEarning = total[1] != null ? (new BigDecimal(total[1].toString())) : null;
			pensionEarningSalary = total[2] != null ? (new BigDecimal(total[2].toString())) : null;
			ProvidentFundEmployee = total[3] != null ? (new BigDecimal(total[3].toString())) : null;
			providentFundEmployeer = total[5] != null ? (new BigDecimal(total[5].toString())) : null;
			providentFundEmployerPension = total[4] != null ? (new BigDecimal(total[4].toString())) : null;

			if (totalEarning != null) {
				Cell cell2 = rowNew.createCell(2);
				cell2.setCellValue(totalEarning.doubleValue());
				cell2.setCellStyle(addZeroBold);
			}

			if (basicEarning != null) {
				Cell cell31 = rowNew.createCell(3);
				cell31.setCellValue(basicEarning.doubleValue());
				cell31.setCellStyle(addZeroBold);
			}

			if (pensionEarningSalary != null) {
				Cell cell32 = rowNew.createCell(4);
				cell32.setCellValue(pensionEarningSalary.doubleValue());
				cell32.setCellStyle(addZeroBold);
			}

			if (pensionEarningSalary != null) {
				Cell cell33 = rowNew.createCell(5);
				cell33.setCellValue(pensionEarningSalary.doubleValue());
				cell33.setCellStyle(addZeroBold);
			}

			if (ProvidentFundEmployee != null) {
				Cell cell34 = rowNew.createCell(6);
				cell34.setCellValue(ProvidentFundEmployee.doubleValue());
				cell34.setCellStyle(addZeroBold);
			}

			if (providentFundEmployeer != null) {
				Cell cell37 = rowNew.createCell(7);
				cell37.setCellValue(providentFundEmployeer.doubleValue());
				cell37.setCellStyle(addZeroBold);
			}

			if (providentFundEmployerPension != null) {
				Cell cell35 = rowNew.createCell(8);
				cell35.setCellValue(providentFundEmployerPension.doubleValue());
				cell35.setCellStyle(addZeroBold);
			}
			// sum
			Cell cell36 = rowNew.createCell(10);
			cell36.setCellValue(0.00);

			cell36.setCellStyle(addZeroBold1);

		}

		// Resize all columns to fit the content size
		for (int i = 0; i < columns.length; i++) {
			sheet.autoSizeColumn(i);
		}

		/*
		 * // Write the output to a file FileOutputStream fileOut = new
		 * FileOutputStream("E:\\poi-epf.xlsx"); workbook.write(fileOut);
		 * fileOut.close();
		 */
		return workbook;
	}

	public static Workbook demo(List<OneTimeEarningDeductionDTO> earningDeductionReportList, String[] columns,
			String processMonth) throws IOException, InvalidFormatException {

		Workbook workbook = new XSSFWorkbook(); // new HSSFWorkbook() for generating `.xls` file

		CreationHelper createHelper = workbook.getCreationHelper();

		Sheet sheet = workbook.createSheet("One Time Earnings-Dept Wise");

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

		CellStyle headerCellStyle1 = workbook.createCellStyle();
		headerCellStyle1.setFont(headerFont);
		headerCellStyle1.setAlignment(HorizontalAlignment.RIGHT);
		headerCellStyle1.setVerticalAlignment(VerticalAlignment.CENTER);
		headerCellStyle1.setFillBackgroundColor(IndexedColors.LIGHT_CORNFLOWER_BLUE.getIndex());
		headerCellStyle1.setFillPattern(FillPatternType.FINE_DOTS);

		CellStyle headerCellStyle11 = workbook.createCellStyle();
		headerCellStyle11.setFont(headerFont);
		headerCellStyle11.setAlignment(HorizontalAlignment.LEFT);
		headerCellStyle11.setVerticalAlignment(VerticalAlignment.CENTER);
		CellStyle headerCellStyle111 = workbook.createCellStyle();
		headerCellStyle111.setFont(headerFont);
		headerCellStyle111.setAlignment(HorizontalAlignment.LEFT);
		headerCellStyle111.setVerticalAlignment(VerticalAlignment.CENTER);

		CellStyle headerCellStyle112 = workbook.createCellStyle();
		headerCellStyle112.setAlignment(HorizontalAlignment.LEFT);
		headerCellStyle112.setVerticalAlignment(VerticalAlignment.CENTER);

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
		sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 10));
		Cell cellCom = row0.createCell(0);
		cellCom.setCellValue("One Time Earnings -" + processMonth);
		cellCom.setCellStyle(headerCellStyle111);

		Row headerRow = sheet.createRow(1);
		// Creating cells
		for (int i = 0; i < columns.length; i++) {
			Cell cell = headerRow.createCell(i);
			cell.setCellValue(columns[i]);
			cell.setCellStyle(headerCellStyle);

		}

		CellStyle dateCellStyle = workbook.createCellStyle();
		dateCellStyle.setDataFormat(createHelper.createDataFormat().getFormat("dd-MM-yyyy"));

		int rowNum = 2;
		BigDecimal sum = BigDecimal.ZERO;
		if (earningDeductionReportList != null) {

			for (OneTimeEarningDeductionDTO OneTimeEarningDeduction : earningDeductionReportList) {

				// int firstRow = sheet.getFirstRowNum();
				Row row = sheet.getRow(rowNum++);
				int firstCol = row.getFirstCellNum();
				int lastColP1 = row.getLastCellNum();

				System.out.println("firstCol " + firstCol);
				System.out.println("lastColP1 " + lastColP1);

				// Row row = sheet.createRow(rowNum++);

				if (OneTimeEarningDeduction.getEmployeeCode() != null
						&& OneTimeEarningDeduction.getEmployeeCode().trim() != "")
					row.createCell(0).setCellValue(OneTimeEarningDeduction.getEmployeeCode());
				else
					row.createCell(0).setCellValue("Under Process");

				row.createCell(1).setCellValue(OneTimeEarningDeduction.getEmployeeName());

				row.createCell(2).setCellValue(OneTimeEarningDeduction.getDesignationName());

				row.createCell(3).setCellValue(OneTimeEarningDeduction.getDepartmentName());

				row.createCell(4).setCellValue(OneTimeEarningDeduction.getDeductionType());

				sum = sum.add(OneTimeEarningDeduction.getAmount());

				Cell celltotalearning = row.createCell(5);
				celltotalearning.setCellValue(convertBigdecimalToString(OneTimeEarningDeduction.getAmount()));
				celltotalearning.setCellStyle(cellStyle112);
				// row.createCell(5).setCellValue(convertBigdecimalToString(OneTimeEarningDeduction.getAmount()));
				row.createCell(6).setCellValue(OneTimeEarningDeduction.getRemarks());

			}

		}

		headerCellStyle.setVerticalAlignment(VerticalAlignment.JUSTIFY);

		Row rowNew = sheet.createRow(rowNum);
		sheet.addMergedRegion(new CellRangeAddress(rowNum, rowNum, 0, 4));
		Cell cell3 = rowNew.createCell(0);

		cell3.setCellValue("Total Amount");
		cell3.setCellStyle(cellStyleCentre);
		// System.out.println("Sum============================ " + sum);

		Cell cell5 = rowNew.createCell(5);

		cell5.setCellValue(convertBigdecimalToString(sum));
		cell5.setCellStyle(headerCellStyleTotal);

		// Resize all columns to fit the content size
		for (int i = 0; i < columns.length; i++) {
			sheet.autoSizeColumn(i);
		}

		/*
		 * // Write the output to a file FileOutputStream fileOut = new
		 * FileOutputStream("E:\\poi-epf.xlsx"); workbook.write(fileOut);
		 * fileOut.close();
		 */
		return workbook;
	}

	@SuppressWarnings("deprecation")
	public static Workbook currentCostToCompanyWriter(List<ReportPayOutDTO> reportPayOutDTOList, Object[] columns,
			String[] earnngPayHeadsId) {

		// , Map<String, String> payHeadsMap
		Workbook workbook = new XSSFWorkbook(); // new HSSFWorkbook() for generating `.xls` file

		CreationHelper createHelper = workbook.getCreationHelper();

		Sheet sheet = workbook.createSheet("Current CTC Monthly");

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

		CellStyle headerCellStyle1 = workbook.createCellStyle();
		headerCellStyle1.setFont(headerFont);
		headerCellStyle1.setAlignment(HorizontalAlignment.RIGHT);
		headerCellStyle1.setVerticalAlignment(VerticalAlignment.CENTER);
		headerCellStyle1.setFillBackgroundColor(IndexedColors.LIGHT_CORNFLOWER_BLUE.getIndex());
		headerCellStyle1.setFillPattern(FillPatternType.FINE_DOTS);

		CellStyle headerCellStyle11 = workbook.createCellStyle();
		headerCellStyle11.setFont(headerFont);
		headerCellStyle11.setAlignment(HorizontalAlignment.LEFT);
		headerCellStyle11.setVerticalAlignment(VerticalAlignment.CENTER);
		CellStyle headerCellStyle111 = workbook.createCellStyle();
		headerCellStyle111.setFont(headerFont);
		headerCellStyle111.setAlignment(HorizontalAlignment.LEFT);
		headerCellStyle111.setVerticalAlignment(VerticalAlignment.CENTER);

		CellStyle headerCellStyle112 = workbook.createCellStyle();
		headerCellStyle112.setAlignment(HorizontalAlignment.LEFT);
		headerCellStyle112.setVerticalAlignment(VerticalAlignment.CENTER);

		CellStyle headerCellStyleTotal = workbook.createCellStyle();
		headerCellStyleTotal.setFont(headerFont);
		headerCellStyleTotal.setAlignment(HorizontalAlignment.RIGHT);
		headerCellStyleTotal.setVerticalAlignment(VerticalAlignment.CENTER);

		CellStyle headerCellStyleCode = workbook.createCellStyle();
		headerCellStyleCode.setFont(headerFont);
		headerCellStyleCode.setAlignment(HorizontalAlignment.LEFT);
		headerCellStyleCode.setVerticalAlignment(VerticalAlignment.CENTER);

		CellStyle addZero = workbook.createCellStyle();
		addZero.setDataFormat(createHelper.createDataFormat().getFormat("0.00"));
		addZero.setAlignment(HorizontalAlignment.RIGHT);
		addZero.setVerticalAlignment(VerticalAlignment.CENTER);

		CellStyle addZeroBold = workbook.createCellStyle();
		addZeroBold.setDataFormat(createHelper.createDataFormat().getFormat("0.00"));
		addZeroBold.setFont(headerFont);
		addZeroBold.setAlignment(HorizontalAlignment.RIGHT);
		addZeroBold.setVerticalAlignment(VerticalAlignment.CENTER);

		Row row0 = sheet.createRow(0);
		sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 10));
		Cell cellCom = row0.createCell(0);
		cellCom.setCellValue("Employee Current CTC - Monthly");
		cellCom.setCellStyle(headerCellStyle111);

		Row headerRow = sheet.createRow(1);
		// Creating cells
		for (int i = 0; i < columns.length; i++) {
			Cell cell = headerRow.createCell(i);
			cell.setCellValue(columns[i].toString());
			cell.setCellStyle(headerCellStyle);

		}

		CellStyle dateCellStyle = workbook.createCellStyle();
		dateCellStyle.setDataFormat(createHelper.createDataFormat().getFormat("dd-MM-yyyy"));

		CellStyle dateCellStyles = workbook.createCellStyle();
		dateCellStyles.setDataFormat(createHelper.createDataFormat().getFormat(""));

		int rowNum = 2;
		// BigDecimal sum = BigDecimal.ZERO;

		BigDecimal totalSum = BigDecimal.ZERO;
		BigDecimal deductionTotalSum = BigDecimal.ZERO;
		BigDecimal totalGrossSalary = BigDecimal.ZERO;
		BigDecimal totalCTC = BigDecimal.ZERO;

		BigDecimal epfEmployee = new BigDecimal(0);
		BigDecimal epfEmployer = new BigDecimal(0);
		BigDecimal esiEmployee = new BigDecimal(0);
		BigDecimal esiEmployer = new BigDecimal(0);
		BigDecimal professionalTax = new BigDecimal(0);
		BigDecimal netPay = new BigDecimal(0);
		BigDecimal adminPer = new BigDecimal(0);
		BigDecimal totalEpfEmployee = new BigDecimal(0);
		BigDecimal totalEpfEmployer = new BigDecimal(0);
		BigDecimal totalEsiEmployee = new BigDecimal(0);
		BigDecimal totalEsiEmployer = new BigDecimal(0);
		BigDecimal totalLwfEmployee = new BigDecimal(0);
		BigDecimal totalLwfEmployer = new BigDecimal(0);
		BigDecimal totalProfessionalTax = new BigDecimal(0);
		BigDecimal totalNetPay = new BigDecimal(0);
		BigDecimal epfEmployeePension = new BigDecimal(0);
		BigDecimal totalEpfEmployeePension = new BigDecimal(0);

		Map<String, BigDecimal> totalSumMap = new HashMap<String, BigDecimal>();
		Map<String, BigDecimal> deductionTotalSumMap = new HashMap<String, BigDecimal>();

		if (reportPayOutDTOList != null) {
			int index = 0;
			int deductionIndex = 0;

			for (ReportPayOutDTO reportPayOutDTO : reportPayOutDTOList) {
				BigDecimal sum = BigDecimal.ZERO;
				BigDecimal deductionSum = BigDecimal.ZERO;
				Row row = sheet.createRow(rowNum++);

				if (reportPayOutDTO.getEmployeeCode() != null && reportPayOutDTO.getEmployeeCode().trim() != "")
					row.createCell(0).setCellValue(reportPayOutDTO.getEmployeeCode());
				else
					row.createCell(0).setCellValue("Under Process");

				row.createCell(1).setCellValue(reportPayOutDTO.getEmpName());

				row.createCell(2).setCellValue(reportPayOutDTO.getDesignationName());

				row.createCell(3).setCellValue(reportPayOutDTO.getDepartmentName());

				Cell createdDateCell = row.createCell(4);
				createdDateCell.setCellValue(reportPayOutDTO.getEmpGrade());

				row.createCell(5).setCellValue(reportPayOutDTO.getEmployeeType());
				row.createCell(6).setCellValue(reportPayOutDTO.getEmployeeStatus());

				Cell effective = row.createCell(7);
				effective.setCellValue(reportPayOutDTO.getEffectiveDate());
				effective.setCellStyle(dateCellStyle);

				Cell updated = row.createCell(8);
				updated.setCellValue(reportPayOutDTO.getDateUpdate());
				updated.setCellStyle(dateCellStyle);

				Cell ctc = row.createCell(9);
				ctc.setCellValue(reportPayOutDTO.getCtc().doubleValue());
				ctc.setCellStyle(addZero);

				Cell cell10 = row.createCell(10);
				cell10.setCellStyle(addZero);
				cell10.setCellValue((reportPayOutDTO.getGrossSalary().doubleValue()));

				if (reportPayOutDTO.getGrossSalary() != null)
					totalGrossSalary = reportPayOutDTO.getGrossSalary().add(totalGrossSalary);

				if (reportPayOutDTO.getCtc() != null)
					totalCTC = reportPayOutDTO.getCtc().add(totalCTC);

				Map<String, String> payHeadsMap = reportPayOutDTO.getPayHeadsMap();

				int value = 11;

				for (int i = 0; i < earnngPayHeadsId.length; i++) {
					Cell cell = row.createCell(value);
					cell.setCellStyle(addZero);
					if (payHeadsMap != null) {
						if (payHeadsMap.get((earnngPayHeadsId[i])) != null) {
							cell.setCellValue(new BigDecimal(payHeadsMap.get((earnngPayHeadsId[i]))).doubleValue());
						}
					} else {
						cell.setCellValue(new BigDecimal(0.00).doubleValue());
					}

					value++;
					if (payHeadsMap != null) {
						if (payHeadsMap.get((earnngPayHeadsId[i])) != null) {
							BigDecimal bigDecimalCurrency = new BigDecimal(payHeadsMap.get((earnngPayHeadsId[i])));
							if (index == 0) {
								totalSumMap.put(earnngPayHeadsId[i], bigDecimalCurrency);
							} else {
								BigDecimal currentSum = totalSumMap.get(earnngPayHeadsId[i]);
								if (currentSum == null)
									currentSum = BigDecimal.ZERO;
								totalSumMap.put(earnngPayHeadsId[i], bigDecimalCurrency.add(currentSum));
							}
							sum = sum.add(bigDecimalCurrency);
						}
					}
				}
				index++;

				totalSum = totalSum.add(sum);

				totalEpfEmployee = totalEpfEmployee
						.add((reportPayOutDTO.getEpfEmployee() != null) ? reportPayOutDTO.getEpfEmployee()
								: BigDecimal.ZERO);
				epfEmployee = reportPayOutDTO.getEpfEmployee() != null ? reportPayOutDTO.getEpfEmployee()
						: BigDecimal.ZERO;

				Cell cell1 = row.createCell(value++);
				cell1.setCellValue((epfEmployee).doubleValue());
				cell1.setCellStyle(addZero);

				totalEpfEmployer = totalEpfEmployer
						.add((reportPayOutDTO.getEpfEmployer() != null) ? reportPayOutDTO.getEpfEmployer()
								: BigDecimal.ZERO);
				epfEmployer = reportPayOutDTO.getEpfEmployer() != null ? reportPayOutDTO.getEpfEmployer()
						: BigDecimal.ZERO;

				Cell cell2 = row.createCell(value++);
				cell2.setCellValue((epfEmployer).doubleValue());
				cell2.setCellStyle(addZero);

				totalEpfEmployeePension = totalEpfEmployeePension
						.add((reportPayOutDTO.getEpfEmployeePension() != null) ? reportPayOutDTO.getEpfEmployeePension()
								: BigDecimal.ZERO);
				epfEmployeePension = reportPayOutDTO.getEpfEmployeePension() != null
						? reportPayOutDTO.getEpfEmployeePension()
						: BigDecimal.ZERO;

				Cell cell12 = row.createCell(value++);
				cell12.setCellValue((epfEmployeePension).doubleValue());
				cell12.setCellStyle(addZero);

				Cell cell13 = row.createCell(value++);
				cell13.setCellValue(0.00);
				cell13.setCellStyle(addZero);

				totalEsiEmployee = totalEsiEmployee
						.add((reportPayOutDTO.getEsi_Employee() != null) ? reportPayOutDTO.getEsi_Employee()
								: BigDecimal.ZERO);
				esiEmployee = reportPayOutDTO.getEsi_Employee() != null ? reportPayOutDTO.getEsi_Employee()
						: BigDecimal.ZERO;

				Cell cell3 = row.createCell(value++);
				cell3.setCellValue(esiEmployee.doubleValue());
				cell3.setCellStyle(addZero);

				totalEsiEmployer = totalEsiEmployer
						.add((reportPayOutDTO.getEsi_Employer() != null) ? reportPayOutDTO.getEsi_Employer()
								: BigDecimal.ZERO);
				esiEmployer = reportPayOutDTO.getEsi_Employer() != null ? reportPayOutDTO.getEsi_Employer()
						: BigDecimal.ZERO;
				Cell cell4 = row.createCell(value++);
				cell4.setCellValue(esiEmployer.doubleValue());
				cell4.setCellStyle(addZero);

				totalLwfEmployee = totalLwfEmployee
						.add((reportPayOutDTO.getLwfEmployeeAmount() != null) ? reportPayOutDTO.getLwfEmployeeAmount()
								: BigDecimal.ZERO);
				BigDecimal lwfEmployer = reportPayOutDTO.getLwfEmployeeAmount() != null
						? reportPayOutDTO.getLwfEmployeeAmount()
						: BigDecimal.ZERO;
				Cell cell5 = row.createCell(value++);
				cell5.setCellValue(lwfEmployer.doubleValue());
				cell5.setCellStyle(addZero);

				totalLwfEmployer = totalLwfEmployer
						.add((reportPayOutDTO.getLwfEmployerAmount() != null) ? reportPayOutDTO.getLwfEmployerAmount()
								: BigDecimal.ZERO);
				lwfEmployer = reportPayOutDTO.getLwfEmployerAmount() != null ? reportPayOutDTO.getLwfEmployerAmount()
						: BigDecimal.ZERO;
				Cell cell6 = row.createCell(value++);
				cell6.setCellValue(lwfEmployer.doubleValue());
				cell6.setCellStyle(addZero);

				totalProfessionalTax = totalProfessionalTax
						.add((reportPayOutDTO.getProfessionalTax() != null) ? reportPayOutDTO.getProfessionalTax()
								: BigDecimal.ZERO);
				professionalTax = reportPayOutDTO.getProfessionalTax() != null ? reportPayOutDTO.getProfessionalTax()
						: BigDecimal.ZERO;
				Cell cellTax = row.createCell(value++);
				cellTax.setCellValue(professionalTax.doubleValue());
				cellTax.setCellStyle(addZero);

				totalNetPay = totalNetPay
						.add((reportPayOutDTO.getNetPayableAmount() != null) ? reportPayOutDTO.getNetPayableAmount()
								: BigDecimal.ZERO);
				netPay = reportPayOutDTO.getNetPayableAmount() != null ? reportPayOutDTO.getNetPayableAmount()
						: BigDecimal.ZERO;
				Cell cellPay = row.createCell(value++);
				cellPay.setCellValue(netPay.doubleValue());
				cellPay.setCellStyle(addZero);

//				
//				adminPer = reportPayOutDTO.getAdminPer() != null ? reportPayOutDTO.getAdminPer()
//						: BigDecimal.ZERO;
//				
//				Cell cell5 = row.createCell(value++);
//				cell5.setCellValue(adminPer.doubleValue());
//				cell5.setCellStyle(addZero);

//				for (int i = 0; i < deductionPayHeadsId.length; i++) {
//					Cell cell = row.createCell(value);
//					cell.setCellStyle(addZero);
//					if (payHeadsMap.get((deductionPayHeadsId[i])) != null)
//						cell.setCellValue(new BigDecimal(payHeadsMap.get((deductionPayHeadsId[i]))).doubleValue());
//					else
//						cell.setCellValue(new BigDecimal(0.00).doubleValue());
//
//					value++;
//					if (payHeadsMap.get((deductionPayHeadsId[i])) != null) {
//						BigDecimal currency = new BigDecimal(payHeadsMap.get((deductionPayHeadsId[i])));
//						if (deductionIndex == 0) {
//							deductionTotalSumMap.put(deductionPayHeadsId[i], currency);
//						} else {
//							BigDecimal deductionCurrentSum = deductionTotalSumMap.get(deductionPayHeadsId[i]);
//							if (deductionCurrentSum == null)
//								deductionCurrentSum = BigDecimal.ZERO;
//							deductionTotalSumMap.put(deductionPayHeadsId[i], currency.add(deductionCurrentSum));
//
//						}
//						deductionSum = deductionSum.add(currency);
//					}
//				}
//				deductionIndex++;
//
//				
//				deductionTotalSum = deductionTotalSum.add(deductionSum);

				// totalNetPayable = totalNetPayable.add(reportPayOutDTO.getNetPayableAmount());
				//
				// Cell net = row.createCell(value++);
				// net.setCellValue(reportPayOutDTO.getNetPayableAmount().doubleValue());
				// net.setCellStyle(addZero);

			}

		}

		headerCellStyle.setVerticalAlignment(VerticalAlignment.JUSTIFY);
		int newValue = 11;
		Row rowNew = sheet.createRow(rowNum);

		Cell ctc = rowNew.createCell(9);
		ctc.setCellStyle(addZeroBold);
		ctc.setCellValue(convertBigdecimalToDouble(totalCTC));

		Cell grossSalary = rowNew.createCell(10);
		grossSalary.setCellStyle(addZeroBold);
		grossSalary.setCellValue(convertBigdecimalToDouble(totalGrossSalary));

		for (int i = 0; i < earnngPayHeadsId.length; i++) {
			Cell createdDateCell = rowNew.createCell(newValue);
			createdDateCell.setCellStyle(addZeroBold);
			createdDateCell.setCellValue(convertBigdecimalToDouble(totalSumMap.get((earnngPayHeadsId[i]))));
			newValue++;
		}
		// System.out.println("hiyy");
		// Cell t = rowNew.createCell(newValue);
		// t.setCellValue(totalSum.doubleValue());
		// t.setCellStyle(addZeroBold);
		// newValue++;
//		for (int i = 0; i < deductionPayHeadsId.length; i++) {
//			Cell createdDateCell = rowNew.createCell(newValue);
//			createdDateCell.setCellStyle(addZeroBold);
//			createdDateCell.setCellValue(convertBigdecimalToDouble(deductionTotalSumMap.get((deductionPayHeadsId[i]))));
//			newValue++;
//		}

		// Cell createdDateCell = rowNew.createCell(newValue);
		// createdDateCell.setCellValue( deductionTotalSum.doubleValue());
		// createdDateCell.setCellStyle(addZeroBold);
		// newValue++;

		// Cell netpay = rowNew.createCell(newValue);
		// netpay.setCellValue(totalNetPayable.doubleValue());
		// netpay.setCellStyle(addZeroBold);

		Cell t = rowNew.createCell(newValue);
		t.setCellValue(totalSum.doubleValue());
		t.setCellStyle(addZeroBold);

		Cell cell5 = rowNew.createCell(newValue);
		cell5.setCellValue(convertBigdecimalToDouble(totalEpfEmployee));
		cell5.setCellStyle(addZeroBold);
		newValue++;

		Cell cell6 = rowNew.createCell(newValue);
		cell6.setCellValue(convertBigdecimalToDouble(totalEpfEmployer));
		cell6.setCellStyle(addZeroBold);
		newValue++;

		Cell cell13 = rowNew.createCell(newValue);
		cell13.setCellValue(convertBigdecimalToDouble(totalEpfEmployeePension));
		cell13.setCellStyle(addZeroBold);
		newValue++;

		newValue++;

		Cell cell7 = rowNew.createCell(newValue);
		cell7.setCellValue(convertBigdecimalToDouble(totalEsiEmployee));
		cell7.setCellStyle(addZeroBold);
		newValue++;

		Cell cell8 = rowNew.createCell(newValue);
		cell8.setCellValue(convertBigdecimalToDouble(totalEsiEmployer));
		cell8.setCellStyle(addZeroBold);
		newValue++;

		Cell cell9 = rowNew.createCell(newValue);
		cell9.setCellValue(convertBigdecimalToDouble(totalLwfEmployee));
		cell9.setCellStyle(addZeroBold);
		newValue++;

		Cell cell10 = rowNew.createCell(newValue);
		cell10.setCellValue(convertBigdecimalToDouble(totalLwfEmployer));
		cell10.setCellStyle(addZeroBold);
		newValue++;

		Cell cell11 = rowNew.createCell(newValue);
		cell11.setCellValue(convertBigdecimalToDouble(totalProfessionalTax));
		cell11.setCellStyle(addZeroBold);
		newValue++;

		Cell cell12 = rowNew.createCell(newValue);
		cell12.setCellValue(convertBigdecimalToDouble(totalNetPay));
		cell12.setCellStyle(addZeroBold);

		for (int i = 0; i < columns.length; i++) {
			sheet.autoSizeColumn(i);
		}
		return workbook;

	}

	public static Workbook professionalTaxStatementMonthlyEmployeeWiseReport(List<ReportPayOutDTO> reportPayOutDTO,
			String[] columns, String processMonth, String wise) {

		Workbook workbook = new XSSFWorkbook(); // new HSSFWorkbook() for generating `.xls` file

		CreationHelper createHelper = workbook.getCreationHelper();

		Sheet sheet = workbook.createSheet("PT Statement Monthaly");

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

		CellStyle headerCellStyle1 = workbook.createCellStyle();
		headerCellStyle1.setFont(headerFont);
		headerCellStyle1.setAlignment(HorizontalAlignment.RIGHT);
		headerCellStyle1.setVerticalAlignment(VerticalAlignment.CENTER);
		headerCellStyle1.setFillBackgroundColor(IndexedColors.LIGHT_CORNFLOWER_BLUE.getIndex());
		headerCellStyle1.setFillPattern(FillPatternType.FINE_DOTS);

		CellStyle headerCellStyle11 = workbook.createCellStyle();
		headerCellStyle11.setFont(headerFont);
		headerCellStyle11.setAlignment(HorizontalAlignment.LEFT);
		headerCellStyle11.setVerticalAlignment(VerticalAlignment.CENTER);
		CellStyle headerCellStyle111 = workbook.createCellStyle();
		headerCellStyle111.setFont(headerFont);
		headerCellStyle111.setAlignment(HorizontalAlignment.LEFT);
		headerCellStyle111.setVerticalAlignment(VerticalAlignment.CENTER);

		CellStyle headerCellStyle112 = workbook.createCellStyle();
		headerCellStyle112.setAlignment(HorizontalAlignment.LEFT);
		headerCellStyle112.setVerticalAlignment(VerticalAlignment.CENTER);

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

		CellStyle addZero = workbook.createCellStyle();
		addZero.setDataFormat(createHelper.createDataFormat().getFormat("0.00"));
		addZero.setAlignment(HorizontalAlignment.CENTER);
		addZero.setVerticalAlignment(VerticalAlignment.CENTER);

		CellStyle addZeroBold = workbook.createCellStyle();
		addZeroBold.setDataFormat(createHelper.createDataFormat().getFormat("0.00"));
		addZeroBold.setFont(headerFont);
		addZeroBold.setAlignment(HorizontalAlignment.CENTER);
		addZeroBold.setVerticalAlignment(VerticalAlignment.CENTER);

		Row row0 = sheet.createRow(0);
		sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 10));
		Cell cellCom = row0.createCell(0);
		cellCom.setCellValue("Professional Tax Statement for " + processMonth);
		cellCom.setCellStyle(headerCellStyle111);

		Row headerRow = sheet.createRow(1);
		// Creating cells
		for (int i = 0; i < columns.length; i++) {
			Cell cell = headerRow.createCell(i);
			cell.setCellValue(columns[i]);
			cell.setCellStyle(headerCellStyle);

		}

		int rowNum = 2;
		BigDecimal sum = BigDecimal.ZERO;
		BigDecimal totalAmount = BigDecimal.ZERO;

		if (reportPayOutDTO != null) {
			int i = 1;
			for (ReportPayOutDTO reportPayOut : reportPayOutDTO) {
				Row row = sheet.createRow(rowNum++);
				row.createCell(0).setCellValue(i);

				i++;

				row.createCell(1).setCellValue(reportPayOut.getEmployeeCode());
				if (reportPayOut.getEmpName() != null)
					row.createCell(2).setCellValue(reportPayOut.getEmpName());
				row.createCell(3).setCellValue(reportPayOut.getJobLocation());
				row.createCell(4).setCellValue(reportPayOut.getStateName());

				sum = sum.add(reportPayOut.getTotalEarning());
				Cell cell4 = row.createCell(5);
				cell4.setCellStyle(addZero);
				cell4.setCellValue(reportPayOut.getTotalEarning().doubleValue());

				if (reportPayOut.getAmount() != null)
					totalAmount = reportPayOut.getAmount().add(totalAmount);

				Cell cell5 = row.createCell(6);
				cell5.setCellStyle(addZero);
				if (reportPayOut.getAmount() != null)
					cell5.setCellValue(reportPayOut.getAmount().doubleValue());

			}

		}

		headerCellStyle.setVerticalAlignment(VerticalAlignment.JUSTIFY);

		Row rowNew = sheet.createRow(rowNum);
		Cell cell5 = rowNew.createCell(5);
		cell5.setCellValue(sum.doubleValue());
		cell5.setCellStyle(addZeroBold);

		Cell cell6 = rowNew.createCell(6);

		cell6.setCellValue(totalAmount.doubleValue());
		cell6.setCellStyle(addZeroBold);

		// Resize all columns to fit the content size
		for (int i = 0; i < columns.length; i++) {
			sheet.autoSizeColumn(i);
		}

		/*
		 * // Write the output to a file FileOutputStream fileOut = new
		 * FileOutputStream("E:\\poi-epf.xlsx"); workbook.write(fileOut);
		 * fileOut.close();
		 */
		return workbook;

	}

	public static Workbook professionalTaxStatementMonthlyStateWiseReport(List<ReportPayOutDTO> reportPayOutDTO,
			String[] columns, String processMonth, String wise) {

		Workbook workbook = new XSSFWorkbook(); // new HSSFWorkbook() for generating `.xls` file

		CreationHelper createHelper = workbook.getCreationHelper();

		Sheet sheet = workbook.createSheet("PT state wise Summary");

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

		CellStyle headerCellStyle1 = workbook.createCellStyle();
		headerCellStyle1.setFont(headerFont);
		headerCellStyle1.setAlignment(HorizontalAlignment.RIGHT);
		headerCellStyle1.setVerticalAlignment(VerticalAlignment.CENTER);
		headerCellStyle1.setFillBackgroundColor(IndexedColors.LIGHT_CORNFLOWER_BLUE.getIndex());
		headerCellStyle1.setFillPattern(FillPatternType.FINE_DOTS);

		CellStyle headerCellStyle11 = workbook.createCellStyle();
		headerCellStyle11.setFont(headerFont);
		headerCellStyle11.setAlignment(HorizontalAlignment.LEFT);
		headerCellStyle11.setVerticalAlignment(VerticalAlignment.CENTER);
		CellStyle headerCellStyle111 = workbook.createCellStyle();
		headerCellStyle111.setFont(headerFont);
		headerCellStyle111.setAlignment(HorizontalAlignment.LEFT);
		headerCellStyle111.setVerticalAlignment(VerticalAlignment.CENTER);

		CellStyle headerCellStyle112 = workbook.createCellStyle();
		headerCellStyle112.setAlignment(HorizontalAlignment.LEFT);
		headerCellStyle112.setVerticalAlignment(VerticalAlignment.CENTER);

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

		CellStyle addZero = workbook.createCellStyle();
		addZero.setDataFormat(createHelper.createDataFormat().getFormat("0.00"));
		addZero.setAlignment(HorizontalAlignment.CENTER);
		addZero.setVerticalAlignment(VerticalAlignment.CENTER);

		CellStyle addZeroBold = workbook.createCellStyle();
		addZeroBold.setDataFormat(createHelper.createDataFormat().getFormat("0.00"));
		addZeroBold.setFont(headerFont);
		addZeroBold.setAlignment(HorizontalAlignment.CENTER);
		addZeroBold.setVerticalAlignment(VerticalAlignment.CENTER);

		Row row0 = sheet.createRow(0);
		sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 10));
		Cell cellCom = row0.createCell(0);
		cellCom.setCellValue("Professional Tax Statewise Summary " + processMonth);
		cellCom.setCellStyle(headerCellStyle111);

		CellStyle dateCellStyle = workbook.createCellStyle();
		dateCellStyle.setDataFormat(createHelper.createDataFormat().getFormat("dd-MM-yyyy"));

		Row headerRow = sheet.createRow(1);
		// Creating cells
		for (int i = 0; i < columns.length; i++) {
			Cell cell = headerRow.createCell(i);
			cell.setCellValue(columns[i]);
			cell.setCellStyle(headerCellStyle);

		}

		int rowNum = 2;
		BigDecimal sum = BigDecimal.ZERO;
		BigDecimal totalAmount = BigDecimal.ZERO;

		if (reportPayOutDTO != null) {

			for (ReportPayOutDTO reportPayOut : reportPayOutDTO) {
				Row row = sheet.createRow(rowNum++);

				row.createCell(0).setCellValue(reportPayOut.getStateName());
				row.createCell(1).setCellValue(reportPayOut.getTotalEmployee());

				sum = sum.add(reportPayOut.getTotalEarning());
				Cell cell2 = row.createCell(2);
				cell2.setCellStyle(addZero);
				cell2.setCellValue(reportPayOut.getTotalEarning().doubleValue());

				if (reportPayOut.getAmount() != null)
					totalAmount = reportPayOut.getAmount().add(totalAmount);

				Cell cell3 = row.createCell(3);
				cell3.setCellStyle(addZero);
				if (reportPayOut.getAmount() != null)
					cell3.setCellValue(reportPayOut.getAmount().doubleValue());

			}

		}

		headerCellStyle.setVerticalAlignment(VerticalAlignment.JUSTIFY);

		Row rowNew = sheet.createRow(rowNum);
		Cell cell5 = rowNew.createCell(2);
		cell5.setCellValue(sum.doubleValue());
		cell5.setCellStyle(addZeroBold);

		Cell cell6 = rowNew.createCell(3);
		cell6.setCellValue(totalAmount.doubleValue());
		cell6.setCellStyle(addZeroBold);

		// Resize all columns to fit the content size
		for (int i = 0; i < columns.length; i++) {
			sheet.autoSizeColumn(i);
		}

		/*
		 * // Write the output to a file FileOutputStream fileOut = new
		 * FileOutputStream("E:\\poi-epf.xlsx"); workbook.write(fileOut);
		 * fileOut.close();
		 */
		return workbook;

	}

	public static Workbook ArrearReport(List<ReportPayOutDTO> reportPayOutDTOList, String[] columns,
			String processMonth) {

		Workbook workbook = new XSSFWorkbook(); // new HSSFWorkbook() for generating `.xls` file

		CreationHelper createHelper = workbook.getCreationHelper();

		// Sheet sheet = workbook.createSheet("Arrear Monthly");

		// Create a Sheet
		Sheet sheet = null;
		if (columns.length == 8)
			sheet = workbook.createSheet("Arrear Monthly");
		else
			sheet = workbook.createSheet("Arrear Annualy");

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

		CellStyle headerCellStyle1 = workbook.createCellStyle();
		headerCellStyle1.setFont(headerFont);
		headerCellStyle1.setAlignment(HorizontalAlignment.RIGHT);
		headerCellStyle1.setVerticalAlignment(VerticalAlignment.CENTER);
		headerCellStyle1.setFillBackgroundColor(IndexedColors.LIGHT_CORNFLOWER_BLUE.getIndex());
		headerCellStyle1.setFillPattern(FillPatternType.FINE_DOTS);

		CellStyle headerCellStyle11 = workbook.createCellStyle();
		headerCellStyle11.setFont(headerFont);
		headerCellStyle11.setAlignment(HorizontalAlignment.LEFT);
		headerCellStyle11.setVerticalAlignment(VerticalAlignment.CENTER);

		CellStyle headerCellStyle111 = workbook.createCellStyle();
		headerCellStyle111.setFont(headerFont);
		headerCellStyle111.setAlignment(HorizontalAlignment.LEFT);
		headerCellStyle111.setVerticalAlignment(VerticalAlignment.CENTER);

		CellStyle headerCellStyle112 = workbook.createCellStyle();
		headerCellStyle112.setAlignment(HorizontalAlignment.LEFT);
		headerCellStyle112.setVerticalAlignment(VerticalAlignment.CENTER);

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

		CellStyle addZero = workbook.createCellStyle();
		addZero.setDataFormat(createHelper.createDataFormat().getFormat("0.00"));
		addZero.setAlignment(HorizontalAlignment.CENTER);
		addZero.setVerticalAlignment(VerticalAlignment.CENTER);

		CellStyle addZeroBold = workbook.createCellStyle();
		addZeroBold.setDataFormat(createHelper.createDataFormat().getFormat("0.00"));
		addZeroBold.setFont(headerFont);
		addZeroBold.setAlignment(HorizontalAlignment.CENTER);
		addZeroBold.setVerticalAlignment(VerticalAlignment.CENTER);

		Row row0 = sheet.createRow(0);

		if (columns.length == 8) {
			sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 7));
			Cell cellCom = row0.createCell(0);
			cellCom.setCellValue("Salary Arrears - " + processMonth);
			cellCom.setCellStyle(headerCellStyle111);
		} else {
			sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 8));
			Cell cellCom = row0.createCell(0);
			cellCom.setCellValue("Salary Arrears - " + processMonth);
			cellCom.setCellStyle(headerCellStyle111);
		}

		Row headerRow = sheet.createRow(1);
		// Creating cells
		for (int i = 0; i < columns.length; i++) {
			Cell cell = headerRow.createCell(i);
			cell.setCellValue(columns[i]);
			cell.setCellStyle(headerCellStyle);

		}
		CellStyle dateCellStyle = workbook.createCellStyle();
		dateCellStyle.setDataFormat(createHelper.createDataFormat().getFormat("dd-MM-yyyy"));
		dateCellStyle.setAlignment(HorizontalAlignment.LEFT);
		dateCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);

		int rowNum = 2;
		BigDecimal totalSum = BigDecimal.ZERO;
		BigDecimal amount = new BigDecimal(0);

		if (reportPayOutDTOList != null) {

			for (ReportPayOutDTO reportPayOutDTO : reportPayOutDTOList) {

				Row row = sheet.createRow(rowNum++);

				if (reportPayOutDTO.getEmployeeCode() != null && reportPayOutDTO.getEmployeeCode().trim() != "")
					row.createCell(0).setCellValue(reportPayOutDTO.getEmployeeCode());
				else
					row.createCell(0).setCellValue("Under Process");

				row.createCell(1).setCellValue(reportPayOutDTO.getEmpName());
				row.createCell(2).setCellValue(reportPayOutDTO.getDesignationName());

				row.createCell(3).setCellValue(reportPayOutDTO.getDepartmentName());

				Cell createdDateCell = row.createCell(4);
				createdDateCell.setCellValue(reportPayOutDTO.getDateOfJoining());
				createdDateCell.setCellStyle(dateCellStyle);

				row.createCell(5).setCellValue(reportPayOutDTO.getGender());
				row.createCell(6).setCellValue(reportPayOutDTO.getJobLocation());

				totalSum = totalSum.add(reportPayOutDTO.getAmount());
				amount = reportPayOutDTO.getAmount() != null ? reportPayOutDTO.getAmount() : BigDecimal.ZERO;

				Cell createdCell = row.createCell(7);
				createdCell.setCellValue(amount.doubleValue());
				createdCell.setCellStyle(addZero);

				if (columns.length == 9) {
					row.createCell(8).setCellValue(reportPayOutDTO.getProcessMonth());
				}

			}

			// Resize all columns to fit the content size
			// headerCellStyle.setVerticalAlignment(VerticalAlignment.JUSTIFY);
			Row rowNew = sheet.createRow(rowNum);
			Cell cell5 = rowNew.createCell(7);
			cell5.setCellValue(totalSum.doubleValue());
			cell5.setCellStyle(addZeroBold);

			for (int i = 0; i < columns.length; i++) {
				sheet.autoSizeColumn(i);
			}
		}
		return workbook;
	}

	public static Workbook PFExitReport(List<ReportPayOutDTO> reportPayOutDTOList, String[] columns, String fromDate,
			String toDate) {
		// TODO Auto-generated method stub

		Workbook workbook = new XSSFWorkbook();

		CreationHelper createHelper = workbook.getCreationHelper();
		Sheet sheet = workbook.createSheet("PF Exit Statement");

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

		CellStyle headerCellStyle1 = workbook.createCellStyle();
		headerCellStyle1.setFont(headerFont);
		headerCellStyle1.setAlignment(HorizontalAlignment.RIGHT);
		headerCellStyle1.setVerticalAlignment(VerticalAlignment.CENTER);
		headerCellStyle1.setFillBackgroundColor(IndexedColors.LIGHT_CORNFLOWER_BLUE.getIndex());
		headerCellStyle1.setFillPattern(FillPatternType.FINE_DOTS);

		CellStyle headerCellStyle11 = workbook.createCellStyle();
		headerCellStyle11.setFont(headerFont);
		headerCellStyle11.setAlignment(HorizontalAlignment.LEFT);
		headerCellStyle11.setVerticalAlignment(VerticalAlignment.CENTER);

		CellStyle headerCellStyle112 = workbook.createCellStyle();
		headerCellStyle112.setAlignment(HorizontalAlignment.LEFT);
		headerCellStyle112.setVerticalAlignment(VerticalAlignment.CENTER);

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

		CellStyle dateCellStyle = workbook.createCellStyle();
		dateCellStyle.setDataFormat(createHelper.createDataFormat().getFormat("dd-MM-yyyy"));

		Row row0 = sheet.createRow(0);
		sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 5));
		Cell cellCom = row0.createCell(0);
		cellCom.setCellValue("PF Exit Statement from " + fromDate + " to " + toDate);
		cellCom.setCellStyle(headerCellStyle11);

		Row headerRow = sheet.createRow(1);
		for (int i = 0; i < columns.length; i++) {
			Cell cell = headerRow.createCell(i);
			cell.setCellValue(columns[i]);
			cell.setCellStyle(headerCellStyle);
		}
		int rowNum = 2;
		int srNo = 1;
		if (reportPayOutDTOList != null) {

			for (ReportPayOutDTO reportPayOutDTO : reportPayOutDTOList) {

				Row row = sheet.createRow(rowNum++);
				Cell cell = row.createCell(0);
				cell.setCellValue(srNo++);
				cell.setCellStyle(cellStyleCentre);

				row.createCell(1).setCellValue(reportPayOutDTO.getEmployeeCode());
				row.createCell(2).setCellValue(reportPayOutDTO.getUnNo());
				row.createCell(3).setCellValue(reportPayOutDTO.getEmpName());

				Cell createdDateCell = row.createCell(4);
				createdDateCell.setCellValue(reportPayOutDTO.getEpfJoining());
				createdDateCell.setCellStyle(dateCellStyle);

				Cell createdDate = row.createCell(5);
				createdDate.setCellValue(reportPayOutDTO.getEndDate());
				createdDate.setCellStyle(dateCellStyle);

			}
		}
		// Resize all columns to fit the content size
		for (int i = 0; i < columns.length; i++) {
			sheet.autoSizeColumn(i);
		}

		return workbook;
	}

	public static Workbook ESIExitReport(List<ReportPayOutDTO> reportPayOutDTOList, String[] columns, String fromDate,
			String toDate) {
		// TODO Auto-generated method stub
		Workbook workbook = new XSSFWorkbook();

		CreationHelper createHelper = workbook.getCreationHelper();
		Sheet sheet = workbook.createSheet("ESI Exit Statement");

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

		CellStyle headerCellStyle1 = workbook.createCellStyle();
		headerCellStyle1.setFont(headerFont);
		headerCellStyle1.setAlignment(HorizontalAlignment.RIGHT);
		headerCellStyle1.setVerticalAlignment(VerticalAlignment.CENTER);
		headerCellStyle1.setFillBackgroundColor(IndexedColors.LIGHT_CORNFLOWER_BLUE.getIndex());
		headerCellStyle1.setFillPattern(FillPatternType.FINE_DOTS);

		CellStyle headerCellStyle11 = workbook.createCellStyle();
		headerCellStyle11.setFont(headerFont);
		headerCellStyle11.setAlignment(HorizontalAlignment.LEFT);
		headerCellStyle11.setVerticalAlignment(VerticalAlignment.CENTER);

		CellStyle headerCellStyle112 = workbook.createCellStyle();
		headerCellStyle112.setAlignment(HorizontalAlignment.LEFT);
		headerCellStyle112.setVerticalAlignment(VerticalAlignment.CENTER);

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

		CellStyle dateCellStyle = workbook.createCellStyle();
		dateCellStyle.setDataFormat(createHelper.createDataFormat().getFormat("dd-MM-yyyy"));

		Row row0 = sheet.createRow(0);
		sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 5));
		Cell cellCom = row0.createCell(0);
		cellCom.setCellValue("ESI Exit Statement from " + fromDate + " to " + toDate);
		cellCom.setCellStyle(headerCellStyle11);

		Row headerRow = sheet.createRow(1);
		for (int i = 0; i < columns.length; i++) {
			Cell cell = headerRow.createCell(i);
			cell.setCellValue(columns[i]);
			cell.setCellStyle(headerCellStyle);
		}
		int rowNum = 2;
		int srNo = 1;
		if (reportPayOutDTOList != null) {

			for (ReportPayOutDTO reportPayOutDTO : reportPayOutDTOList) {

				Row row = sheet.createRow(rowNum++);
				Cell cell = row.createCell(0);
				cell.setCellValue(srNo++);
				cell.setCellStyle(cellStyleCentre);

				row.createCell(1).setCellValue(reportPayOutDTO.getEmployeeCode());
				row.createCell(2).setCellValue(reportPayOutDTO.getUnNo());
				row.createCell(3).setCellValue(reportPayOutDTO.getEmpName());

				Cell createdDateCell = row.createCell(4);
				createdDateCell.setCellValue(reportPayOutDTO.getEsicJoining());
				createdDateCell.setCellStyle(dateCellStyle);

				Cell createdDate = row.createCell(5);
				createdDate.setCellValue(reportPayOutDTO.getEndDate());
				createdDate.setCellStyle(dateCellStyle);

			}
		}
		// Resize all columns to fit the content size
		for (int i = 0; i < columns.length; i++) {
			sheet.autoSizeColumn(i);
		}

		return workbook;
	}

	public static Workbook PaymentRecordStatementReport(List<ReportPayOutDTO> reportPayOutDTOList, String[] columns,
			String processMonth) {
		Workbook workbook = new XSSFWorkbook();
		CreationHelper createHelper = workbook.getCreationHelper();
		Sheet sheet = workbook.createSheet("Payment Record Statement");

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

		CellStyle headerCellStyle1 = workbook.createCellStyle();
		headerCellStyle1.setFont(headerFont);
		headerCellStyle1.setAlignment(HorizontalAlignment.RIGHT);
		headerCellStyle1.setVerticalAlignment(VerticalAlignment.CENTER);
		headerCellStyle1.setFillBackgroundColor(IndexedColors.LIGHT_CORNFLOWER_BLUE.getIndex());
		headerCellStyle1.setFillPattern(FillPatternType.FINE_DOTS);

		CellStyle headerCellStyle11 = workbook.createCellStyle();
		headerCellStyle11.setFont(headerFont);
		headerCellStyle11.setAlignment(HorizontalAlignment.LEFT);
		headerCellStyle11.setVerticalAlignment(VerticalAlignment.CENTER);

		CellStyle headerCellStyle112 = workbook.createCellStyle();
		headerCellStyle112.setAlignment(HorizontalAlignment.LEFT);
		headerCellStyle112.setVerticalAlignment(VerticalAlignment.CENTER);

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

		CellStyle addZero = workbook.createCellStyle();
		addZero.setDataFormat(createHelper.createDataFormat().getFormat("0.00"));
		addZero.setAlignment(HorizontalAlignment.LEFT);
		addZero.setVerticalAlignment(VerticalAlignment.CENTER);

		CellStyle dateCellStyle = workbook.createCellStyle();
		dateCellStyle.setDataFormat(createHelper.createDataFormat().getFormat("dd-MM-yyyy"));

		Row row0 = sheet.createRow(0);
		sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 8));
		Cell cellCom = row0.createCell(0);
		cellCom.setCellValue("Payment Record Statement -" + processMonth);
		cellCom.setCellStyle(headerCellStyle11);

		Row headerRow = sheet.createRow(1);
		for (int i = 0; i < columns.length; i++) {
			Cell cell = headerRow.createCell(i);
			cell.setCellValue(columns[i]);
			cell.setCellStyle(headerCellStyle);
		}

		int rowNum = 2;
		if (reportPayOutDTOList != null) {

			for (ReportPayOutDTO reportPayOutDTO : reportPayOutDTOList) {

				Row row = sheet.createRow(rowNum++);
				row.createCell(0).setCellValue(reportPayOutDTO.getEmployeeCode());
				row.createCell(1).setCellValue(reportPayOutDTO.getEmpName());
				row.createCell(2).setCellValue(reportPayOutDTO.getBankName());
				row.createCell(3).setCellValue(reportPayOutDTO.getAccountNumber());
				row.createCell(4).setCellValue(reportPayOutDTO.getIfscCode());

				Cell cell2 = row.createCell(5);
				cell2.setCellStyle(addZero);
				cell2.setCellValue(reportPayOutDTO.getNetPayableAmount().doubleValue());

				row.createCell(6).setCellValue(reportPayOutDTO.getTransactionNo());
				row.createCell(7).setCellValue(reportPayOutDTO.getTransactionMode());

				Cell createdDateCell = row.createCell(8);
				createdDateCell.setCellValue(reportPayOutDTO.getDateUpdate());
				createdDateCell.setCellStyle(dateCellStyle);
			}
		}

		// Resize all columns to fit the content size
		for (int i = 0; i < columns.length; i++) {
			sheet.autoSizeColumn(i);
		}

		return workbook;
	}

	public static Workbook EsiArrearEcrReport(List<ArrearReportPayOutDTO> arrearReportPayOutList,
			List<Object[]> esicExcludedEmployeeList, String[] columns, String processMonth, Company company, Esi esi) {
		// TODO Auto-generated method stub
		Workbook workbook = new XSSFWorkbook(); // new HSSFWorkbook() for generating `.xls` file

		CreationHelper createHelper = workbook.getCreationHelper();

		Sheet sheet = workbook.createSheet("ESI Arrear ECR");

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

		CellStyle headerCellStyle1 = workbook.createCellStyle();
		headerCellStyle1.setFont(headerFont);
		headerCellStyle1.setAlignment(HorizontalAlignment.RIGHT);
		headerCellStyle1.setVerticalAlignment(VerticalAlignment.CENTER);
		headerCellStyle1.setFillBackgroundColor(IndexedColors.LIGHT_CORNFLOWER_BLUE.getIndex());
		headerCellStyle1.setFillPattern(FillPatternType.FINE_DOTS);

		CellStyle headerCellStyle11 = workbook.createCellStyle();
		headerCellStyle11.setFont(headerFont);
		headerCellStyle11.setAlignment(HorizontalAlignment.LEFT);
		headerCellStyle11.setVerticalAlignment(VerticalAlignment.CENTER);

		CellStyle headerCellStyleEmp = workbook.createCellStyle();
		headerCellStyleEmp.setFont(headerFont);
		headerCellStyleEmp.setAlignment(HorizontalAlignment.RIGHT);
		headerCellStyleEmp.setVerticalAlignment(VerticalAlignment.CENTER);

		CellStyle processMonthTitle = workbook.createCellStyle();
		processMonthTitle.setFont(headerFont);
		processMonthTitle.setAlignment(HorizontalAlignment.CENTER);
		processMonthTitle.setVerticalAlignment(VerticalAlignment.CENTER);

		CellStyle cellStyle112 = workbook.createCellStyle();
		cellStyle112.setAlignment(HorizontalAlignment.LEFT);
		cellStyle112.setVerticalAlignment(VerticalAlignment.CENTER);

		CellStyle addZero1 = workbook.createCellStyle();
		addZero1.setDataFormat(createHelper.createDataFormat().getFormat("0.00"));
		addZero1.setAlignment(HorizontalAlignment.RIGHT);
		addZero1.setVerticalAlignment(VerticalAlignment.CENTER);

		CellStyle addZero = workbook.createCellStyle();
		addZero.setDataFormat(createHelper.createDataFormat().getFormat("0.00"));
		addZero.setAlignment(HorizontalAlignment.LEFT);
		addZero.setVerticalAlignment(VerticalAlignment.CENTER);

		CellStyle addZeroBold = workbook.createCellStyle();
		addZeroBold.setDataFormat(createHelper.createDataFormat().getFormat("0.00"));
		addZeroBold.setFont(headerFont);
		addZeroBold.setAlignment(HorizontalAlignment.RIGHT);
		addZeroBold.setVerticalAlignment(VerticalAlignment.CENTER);
		addZeroBold.setFillBackgroundColor(IndexedColors.LIGHT_CORNFLOWER_BLUE.getIndex());
		addZeroBold.setFillPattern(FillPatternType.FINE_DOTS);

		Row row0 = sheet.createRow(0);
		sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 5));
		Cell cellCom = row0.createCell(0);
		cellCom.setCellValue("ESI Arrear ECR - " + processMonth);
		cellCom.setCellStyle(processMonthTitle);

		Row row1 = sheet.createRow(1);
		sheet.addMergedRegion(new CellRangeAddress(1, 1, 0, 5));
		row1.createCell(0).setCellValue("ESIC Code -" + company.getEsicNo());

		Row row2 = sheet.createRow(2);
		sheet.addMergedRegion(new CellRangeAddress(2, 2, 0, 2));
		row2.createCell(0).setCellValue("Total Number of Employees ");
		// row2.createCell(5).setCellValue();

		Row row3 = sheet.createRow(3);
		sheet.addMergedRegion(new CellRangeAddress(3, 3, 0, 2));
		row3.createCell(0).setCellValue("Total Number of Excluded Employees");

		Row row4 = sheet.createRow(4);
		sheet.addMergedRegion(new CellRangeAddress(4, 4, 0, 2));
		row4.createCell(0).setCellValue("Gross Wages of Excluded Employees");

		BigDecimal grossSalary = new BigDecimal(0);
		for (Object[] esicObj : esicExcludedEmployeeList) {

			Integer totalNoOfEmployee = esicObj[0] != null ? Integer.parseInt(esicObj[0].toString()) : null;
			sheet.addMergedRegion(new CellRangeAddress(2, 2, 3, 5));
			Cell cell1 = row2.createCell(3);
			cell1.setCellValue(totalNoOfEmployee);
			cell1.setCellStyle(cellStyle112);

			Integer excludedEmployee = esicObj[1] != null ? Integer.parseInt(esicObj[1].toString()) : null;
			sheet.addMergedRegion(new CellRangeAddress(3, 3, 3, 5));
			Cell cell2 = row3.createCell(3);
			cell2.setCellValue(excludedEmployee);
			cell2.setCellStyle(cellStyle112);

			grossSalary = esicObj[2] != null ? (new BigDecimal(esicObj[2].toString())) : null;
			sheet.addMergedRegion(new CellRangeAddress(4, 4, 3, 5));
			Cell cell3 = row4.createCell(3);

			if (grossSalary != null) {
				cell3.setCellValue(grossSalary.doubleValue());
				cell3.setCellStyle(addZero);
			} else {
				cell3.setCellValue(new BigDecimal(0.00).doubleValue());
				cell3.setCellStyle(addZero);
			}

		}

		Row headerRow = sheet.createRow(5);
		for (int i = 0; i < columns.length; i++) {
			Cell cell = headerRow.createCell(i);
			cell.setCellValue(columns[i]);
			cell.setCellStyle(headerCellStyle);

		}

		int rowNum = 6;

		if (arrearReportPayOutList != null) {

			for (ArrearReportPayOutDTO arrearReportPayOut : arrearReportPayOutList) {

				Row row = sheet.createRow(rowNum++);
				row.createCell(0).setCellValue(arrearReportPayOut.getESICNumber());
				row.createCell(1).setCellValue(arrearReportPayOut.getName());

				Cell cell11 = row.createCell(2);
				cell11.setCellValue(arrearReportPayOut.getPayDays().doubleValue());
				cell11.setCellStyle(headerCellStyleEmp);

				Cell cell1 = row.createCell(3);
				cell1.setCellValue(arrearReportPayOut.getNetPayableAmount().doubleValue());
				cell1.setCellStyle(addZero1);
			}
		}

		// Resize all columns to fit the content size
		for (int i = 0; i < columns.length; i++) {
			sheet.autoSizeColumn(i);
		}

		return workbook;
	}

	public static Workbook payStructureExcelWriterReport(List<PayStructureHdDTO> payStructureHdList,
			Object[] newColumns, String[] currentPayHeadsId, String status) {
		// TODO Auto-generated method stub
		Workbook workbook = new XSSFWorkbook(); // new HSSFWorkbook() for generating `.xls` file

		CreationHelper createHelper = workbook.getCreationHelper();

		Sheet sheet = workbook.createSheet("Current Salary-Monthly ");

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

		CellStyle headerCellStyle1 = workbook.createCellStyle();
		headerCellStyle1.setFont(headerFont);
		headerCellStyle1.setAlignment(HorizontalAlignment.RIGHT);
		headerCellStyle1.setVerticalAlignment(VerticalAlignment.CENTER);
		headerCellStyle1.setFillBackgroundColor(IndexedColors.LIGHT_CORNFLOWER_BLUE.getIndex());
		headerCellStyle1.setFillPattern(FillPatternType.FINE_DOTS);

		CellStyle headerCellStyle11 = workbook.createCellStyle();
		headerCellStyle11.setFont(headerFont);
		headerCellStyle11.setAlignment(HorizontalAlignment.LEFT);
		headerCellStyle11.setVerticalAlignment(VerticalAlignment.CENTER);
		CellStyle headerCellStyle111 = workbook.createCellStyle();
		headerCellStyle111.setFont(headerFont);
		headerCellStyle111.setAlignment(HorizontalAlignment.LEFT);
		headerCellStyle111.setVerticalAlignment(VerticalAlignment.CENTER);

		CellStyle headerCellStyle112 = workbook.createCellStyle();
		headerCellStyle112.setAlignment(HorizontalAlignment.LEFT);
		headerCellStyle112.setVerticalAlignment(VerticalAlignment.CENTER);

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

		CellStyle addZero = workbook.createCellStyle();
		addZero.setDataFormat(createHelper.createDataFormat().getFormat("0.00"));
		addZero.setAlignment(HorizontalAlignment.RIGHT);
		addZero.setVerticalAlignment(VerticalAlignment.CENTER);

		CellStyle addZeroLeft = workbook.createCellStyle();
		addZeroLeft.setDataFormat(createHelper.createDataFormat().getFormat("0.00"));
		addZeroLeft.setAlignment(HorizontalAlignment.LEFT);
		addZeroLeft.setVerticalAlignment(VerticalAlignment.CENTER);

		CellStyle addZeroBold = workbook.createCellStyle();
		addZeroBold.setDataFormat(createHelper.createDataFormat().getFormat("0.00"));
		addZeroBold.setFont(headerFont);
		addZeroBold.setAlignment(HorizontalAlignment.RIGHT);
		addZeroBold.setVerticalAlignment(VerticalAlignment.CENTER);

		CellStyle dateCellStyle = workbook.createCellStyle();
		dateCellStyle.setDataFormat(createHelper.createDataFormat().getFormat("dd-MM-yyyy"));

		BigDecimal epfEmployee = new BigDecimal(0);
		BigDecimal epfEmployer = new BigDecimal(0);
		BigDecimal esiEmployee = new BigDecimal(0);
		BigDecimal esiEmployer = new BigDecimal(0);
		BigDecimal professionalTax = new BigDecimal(0);
		BigDecimal netPay = new BigDecimal(0);
		BigDecimal totalGrossPay = new BigDecimal(0);
		BigDecimal totalSum = new BigDecimal(0);
		BigDecimal totalEpfEmployee = new BigDecimal(0);
		BigDecimal totalEpfEmployer = new BigDecimal(0);
		BigDecimal totalEsiEmployee = new BigDecimal(0);
		BigDecimal totalEsiEmployer = new BigDecimal(0);
		BigDecimal totalProfessionalTax = new BigDecimal(0);
		BigDecimal totalLwfEmployee = new BigDecimal(0);
		BigDecimal totalLwfEmployer = new BigDecimal(0);
		BigDecimal epfEmployeePension = new BigDecimal(0);
		BigDecimal totalNetPay = new BigDecimal(0);
		BigDecimal totalEpfEmployeePension = new BigDecimal(0);

		Row headerRow0 = sheet.createRow(0);
		// sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 10));
		// Cell cellCom = row0.createCell(0);
		// cellCom.setCellValue("Employees Current Salary - Monthly ");
		// cellCom.setCellStyle(headerCellStyle111);

		if (status.equalsIgnoreCase("null")) {
			sheet.addMergedRegion(new CellRangeAddress(0, 1, 0, 10));
			Cell cell0 = headerRow0.createCell(0);
			cell0.setCellValue("Employees Current Salary - Monthly");
			cell0.setCellStyle(headerCellStyle111);
		} else if (status.equalsIgnoreCase("AC")) {
			sheet.addMergedRegion(new CellRangeAddress(0, 1, 0, 10));
			Cell cell0 = headerRow0.createCell(0);
			cell0.setCellValue(" Working Employees Current Salary - Monthly");
			cell0.setCellStyle(headerCellStyle111);
		} else if (status.equalsIgnoreCase("DE")) {
			sheet.addMergedRegion(new CellRangeAddress(0, 1, 0, 10));
			Cell cell0 = headerRow0.createCell(0);
			cell0.setCellValue(" Former Employees Current Salary - Monthly");
			cell0.setCellStyle(headerCellStyle111);
		}

		Row headerRow = sheet.createRow(2);
		// Creating cells
		for (int i = 0; i < newColumns.length; i++) {
			Cell cell = headerRow.createCell(i);
			cell.setCellValue(newColumns[i].toString());
			cell.setCellStyle(headerCellStyle);

		}

		int rowNum = 3;
		Map<String, BigDecimal> totalSumMap = new HashMap<String, BigDecimal>();

		if (payStructureHdList != null) {
			int index = 0;
			for (PayStructureHdDTO payStructureHdDTO : payStructureHdList) {
				BigDecimal sum = BigDecimal.ZERO;
				Row row = sheet.createRow(rowNum++);

				row.createCell(0).setCellValue(payStructureHdDTO.getEmployeeCode());
				row.createCell(1).setCellValue(payStructureHdDTO.getEmployeeName());
				row.createCell(2).setCellValue(payStructureHdDTO.getDesignationName());
				row.createCell(3).setCellValue(payStructureHdDTO.getDepartmentName());
				row.createCell(4).setCellValue(payStructureHdDTO.getGradesName());
				row.createCell(5).setCellValue(payStructureHdDTO.getEmployeeType());
				row.createCell(6).setCellValue(payStructureHdDTO.getEmployeeStatus());

				Cell createdDateCell = row.createCell(7);
				createdDateCell.setCellValue(payStructureHdDTO.getEffectiveDate());
				createdDateCell.setCellStyle(dateCellStyle);

				Cell createdDate = row.createCell(8);
				createdDate.setCellValue(payStructureHdDTO.getDateUpdate());
				createdDate.setCellStyle(dateCellStyle);

				totalGrossPay = totalGrossPay.add(
						(payStructureHdDTO.getGrossPay() != null) ? payStructureHdDTO.getGrossPay() : BigDecimal.ZERO);
				Cell grossPay = row.createCell(9);

				grossPay.setCellValue(
						(payStructureHdDTO.getGrossPay() != null ? payStructureHdDTO.getGrossPay() : BigDecimal.ZERO)
								.doubleValue());
				grossPay.setCellStyle(addZero);

				Map<String, String> payHeadsMap = payStructureHdDTO.getPayHeadsMap();

				int value = 10;

				for (int i = 0; i < currentPayHeadsId.length; i++) {
					Cell cell = row.createCell(value);
					cell.setCellStyle(addZero);
					// if(payHeadsMap.get((currentPayHeadsId[i]))!=null)
					// cell.setCellValue(new
					// BigDecimal(payHeadsMap.get((currentPayHeadsId[i]))).doubleValue());
					// else
					// cell.setCellValue(new BigDecimal(0.00).doubleValue());

					if (payHeadsMap != null) {
						if (payHeadsMap.get((currentPayHeadsId[i])) != null) {
							cell.setCellValue(new BigDecimal(payHeadsMap.get((currentPayHeadsId[i]))).doubleValue());
						}
					} else {
						cell.setCellValue(new BigDecimal(0.00).doubleValue());
					}

					value++;
					if (payHeadsMap != null) {
						if (payHeadsMap.get((currentPayHeadsId[i])) != null) {
							BigDecimal bigDecimalCurrency = new BigDecimal(payHeadsMap.get((currentPayHeadsId[i])));
							if (index == 0) {
								totalSumMap.put(currentPayHeadsId[i], bigDecimalCurrency);
							} else {
								BigDecimal currentSum = totalSumMap.get(currentPayHeadsId[i]);
								if (currentSum == null)
									currentSum = BigDecimal.ZERO;
								totalSumMap.put(currentPayHeadsId[i], bigDecimalCurrency.add(currentSum));
							}
							sum = sum.add(bigDecimalCurrency);
						}
					}

				}
				index++;

				Cell cells = row.createCell(value);
				cells.setCellValue(sum.doubleValue());
				cells.setCellStyle(addZero);

				// System.out.println("hii");
				// value++;
				totalSum = totalSum.add(sum);

				totalEpfEmployee = totalEpfEmployee
						.add((payStructureHdDTO.getEpfEmployee() != null) ? payStructureHdDTO.getEpfEmployee()
								: BigDecimal.ZERO);
				epfEmployee = payStructureHdDTO.getEpfEmployee() != null ? payStructureHdDTO.getEpfEmployee()
						: BigDecimal.ZERO;
//				epfEmployeePension=payStructureHdDTO.getEpfEmployeePension() != null ? payStructureHdDTO.getEpfEmployeePension()
//						: BigDecimal.ZERO;
				Cell cell1 = row.createCell(value++);
				cell1.setCellValue(epfEmployee.doubleValue());
				cell1.setCellStyle(addZero);

				totalEpfEmployer = totalEpfEmployer
						.add((payStructureHdDTO.getEpfEmployer() != null) ? payStructureHdDTO.getEpfEmployer()
								: BigDecimal.ZERO);
				epfEmployer = payStructureHdDTO.getEpfEmployer() != null ? payStructureHdDTO.getEpfEmployer()
						: BigDecimal.ZERO;
				Cell cell2 = row.createCell(value++);
				cell2.setCellValue(epfEmployer.doubleValue());
				cell2.setCellStyle(addZero);

				totalEpfEmployeePension = totalEpfEmployeePension.add(
						(payStructureHdDTO.getEpfEmployeePension() != null) ? payStructureHdDTO.getEpfEmployeePension()
								: BigDecimal.ZERO);
				epfEmployeePension = payStructureHdDTO.getEpfEmployeePension() != null
						? payStructureHdDTO.getEpfEmployeePension()
						: BigDecimal.ZERO;

				Cell cell11 = row.createCell(value++);
				cell11.setCellValue(epfEmployeePension.doubleValue());
				cell11.setCellStyle(addZero);

				totalEsiEmployee = totalEsiEmployee
						.add((payStructureHdDTO.getEsiEmployee() != null) ? payStructureHdDTO.getEsiEmployee()
								: BigDecimal.ZERO);
				esiEmployee = payStructureHdDTO.getEsiEmployee() != null ? payStructureHdDTO.getEsiEmployee()
						: BigDecimal.ZERO;

				Cell cell3 = row.createCell(value++);
				cell3.setCellValue(esiEmployee.doubleValue());
				cell3.setCellStyle(addZero);

				totalEsiEmployer = totalEsiEmployer
						.add((payStructureHdDTO.getEsiEmployer() != null) ? payStructureHdDTO.getEsiEmployer()
								: BigDecimal.ZERO);
				esiEmployer = payStructureHdDTO.getEsiEmployer() != null ? payStructureHdDTO.getEsiEmployer()
						: BigDecimal.ZERO;
				Cell cell4 = row.createCell(value++);
				cell4.setCellValue(esiEmployer.doubleValue());
				cell4.setCellStyle(addZero);

				totalLwfEmployee = totalLwfEmployee.add(
						(payStructureHdDTO.getLwfEmployeeAmount() != null) ? payStructureHdDTO.getLwfEmployeeAmount()
								: BigDecimal.ZERO);
				BigDecimal lwfEmployer = payStructureHdDTO.getLwfEmployeeAmount() != null
						? payStructureHdDTO.getLwfEmployeeAmount()
						: BigDecimal.ZERO;
				Cell cell5 = row.createCell(value++);
				cell5.setCellValue(lwfEmployer.doubleValue());
				cell5.setCellStyle(addZero);

				totalLwfEmployer = totalLwfEmployer.add(
						(payStructureHdDTO.getLwfEmployerAmount() != null) ? payStructureHdDTO.getLwfEmployerAmount()
								: BigDecimal.ZERO);
				lwfEmployer = payStructureHdDTO.getLwfEmployerAmount() != null
						? payStructureHdDTO.getLwfEmployerAmount()
						: BigDecimal.ZERO;
				Cell cell6 = row.createCell(value++);
				cell6.setCellValue(lwfEmployer.doubleValue());
				cell6.setCellStyle(addZero);

				totalProfessionalTax = totalProfessionalTax
						.add((payStructureHdDTO.getProfessionalTax() != null) ? payStructureHdDTO.getProfessionalTax()
								: BigDecimal.ZERO);
				professionalTax = payStructureHdDTO.getProfessionalTax() != null
						? payStructureHdDTO.getProfessionalTax()
						: BigDecimal.ZERO;
				Cell cellTax = row.createCell(value++);
				cellTax.setCellValue(professionalTax.doubleValue());
				cellTax.setCellStyle(addZero);

				totalNetPay = totalNetPay
						.add((payStructureHdDTO.getNetPay() != null) ? payStructureHdDTO.getNetPay() : BigDecimal.ZERO);
				netPay = payStructureHdDTO.getNetPay() != null ? payStructureHdDTO.getNetPay() : BigDecimal.ZERO;
				Cell cellPay = row.createCell(value++);
				cellPay.setCellValue(netPay.doubleValue());
				cellPay.setCellStyle(addZero);

			}
		}

		headerCellStyle.setVerticalAlignment(VerticalAlignment.JUSTIFY);
		int newValue = 9;
		Row rowNew = sheet.createRow(rowNum);

		Cell cellGross = rowNew.createCell(newValue);
		cellGross.setCellValue(totalGrossPay.doubleValue());
		cellGross.setCellStyle(addZeroBold);
		newValue++;

		for (int i = 0; i < currentPayHeadsId.length; i++) {
			Cell createdDateCell = rowNew.createCell(newValue);
			createdDateCell.setCellStyle(addZeroBold);
			createdDateCell.setCellValue(convertBigdecimalToDouble(totalSumMap.get((currentPayHeadsId[i]))));
			newValue++;
		}

		Cell t = rowNew.createCell(newValue);
		t.setCellValue(totalSum.doubleValue());
		t.setCellStyle(addZeroBold);

		Cell cell5 = rowNew.createCell(newValue);
		cell5.setCellValue(totalEpfEmployee.doubleValue());
		cell5.setCellStyle(addZeroBold);
		newValue++;

		Cell cell6 = rowNew.createCell(newValue);
		cell6.setCellValue(totalEpfEmployer.doubleValue());
		cell6.setCellStyle(addZeroBold);
		newValue++;

		Cell cell13 = rowNew.createCell(newValue);
		cell13.setCellValue(totalEpfEmployeePension.doubleValue());
		cell13.setCellStyle(addZeroBold);
		newValue++;

		Cell cell7 = rowNew.createCell(newValue);
		cell7.setCellValue(totalEsiEmployee.doubleValue());
		cell7.setCellStyle(addZeroBold);
		newValue++;

		Cell cell8 = rowNew.createCell(newValue);
		cell8.setCellValue(totalEsiEmployer.doubleValue());
		cell8.setCellStyle(addZeroBold);
		newValue++;

		Cell cell9 = rowNew.createCell(newValue);
		cell9.setCellValue(convertBigdecimalToDouble(totalLwfEmployee));
		cell9.setCellStyle(addZeroBold);
		newValue++;

		Cell cell10 = rowNew.createCell(newValue);
		cell10.setCellValue(convertBigdecimalToDouble(totalLwfEmployer));
		cell10.setCellStyle(addZeroBold);
		newValue++;

		Cell cell11 = rowNew.createCell(newValue);
		cell11.setCellValue(totalProfessionalTax.doubleValue());
		cell11.setCellStyle(addZeroBold);
		newValue++;

		Cell cell12 = rowNew.createCell(newValue);
		cell12.setCellValue(totalNetPay.doubleValue());
		cell12.setCellStyle(addZeroBold);

		for (int i = 0; i < newColumns.length; i++) {
			sheet.autoSizeColumn(i);
		}

		return workbook;

	}

	@SuppressWarnings("deprecation")
	public static Workbook SalarySheetAnnualReport(Map<String, List<ReportPayOutDTO>> reportPayOutDtoMap,
			List<ReportPayOutDTO> annualSummaryList, Object[] columns, String[] earnngPayHeadsId,
			String[] deductionPayHeadsId, String[] summaryColumns, String financialYear) {

		Workbook workbook = new XSSFWorkbook();
		CreationHelper createHelper = workbook.getCreationHelper();

		Sheet summarySheet = workbook.createSheet("Salary Sheet Annual");

		for (ReportPayOutDTO annualSummary : annualSummaryList) {

			for (Map.Entry<String, List<ReportPayOutDTO>> entry : reportPayOutDtoMap.entrySet()) {

				String processMonthAnnual = entry.getKey();
				List<ReportPayOutDTO> reportPayOutDTOList = entry.getValue();
				if (annualSummary.getProcessMonth().equals(processMonthAnnual)) {
					// new HSSFWorkbook() for generating `.xls` file
					CreationHelper createHelperMonthaly = workbook.getCreationHelper();

					Sheet sheet = workbook.createSheet(processMonthAnnual);

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
					CellStyle headerCellStyle111 = workbook.createCellStyle();
					headerCellStyle111.setFont(headerFont);
					headerCellStyle111.setAlignment(HorizontalAlignment.LEFT);
					headerCellStyle111.setVerticalAlignment(VerticalAlignment.CENTER);

					CellStyle addZero = workbook.createCellStyle();
					addZero.setDataFormat(createHelper.createDataFormat().getFormat("0.00"));
					addZero.setAlignment(HorizontalAlignment.RIGHT);
					addZero.setVerticalAlignment(VerticalAlignment.CENTER);

					CellStyle addZeroBold = workbook.createCellStyle();
					addZeroBold.setDataFormat(createHelper.createDataFormat().getFormat("0.00"));
					addZeroBold.setFont(headerFont);
					addZeroBold.setAlignment(HorizontalAlignment.RIGHT);
					addZeroBold.setVerticalAlignment(VerticalAlignment.CENTER);

					Row row0 = sheet.createRow(0);
					sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 10));
					Cell cellCom = row0.createCell(0);
					cellCom.setCellValue("Salary Sheet - " + processMonthAnnual);
					cellCom.setCellStyle(headerCellStyle111);

					Row headerRow = sheet.createRow(1);
					// Creating cells
					for (int i = 0; i < columns.length; i++) {
						Cell cell = headerRow.createCell(i);
						cell.setCellValue(columns[i].toString());
						cell.setCellStyle(headerCellStyle);

					}

					CellStyle dateCellStyle = workbook.createCellStyle();
					dateCellStyle.setDataFormat(createHelper.createDataFormat().getFormat("dd-MM-yyyy"));

					CellStyle dateCellStyles = workbook.createCellStyle();
					dateCellStyles.setDataFormat(createHelper.createDataFormat().getFormat(""));

					int rowNum = 2;

					BigDecimal totalSum = BigDecimal.ZERO;
					BigDecimal deductionTotalSum = BigDecimal.ZERO;
					BigDecimal totalNetPayable = BigDecimal.ZERO;

					Map<String, BigDecimal> totalSumMap = new HashMap<String, BigDecimal>();
					Map<String, BigDecimal> deductionTotalSumMap = new HashMap<String, BigDecimal>();

					if (reportPayOutDTOList != null) {
						int index = 0;
						int deductionIndex = 0;

						for (ReportPayOutDTO reportPayOutDTO : reportPayOutDTOList) {
							BigDecimal sum = BigDecimal.ZERO;
							BigDecimal deductionSum = BigDecimal.ZERO;
							Row row = sheet.createRow(rowNum++);

							if (reportPayOutDTO.getEmployeeCode() != null
									&& reportPayOutDTO.getEmployeeCode().trim() != "")
								row.createCell(0).setCellValue(reportPayOutDTO.getEmployeeCode());
							else
								row.createCell(0).setCellValue("Under Process");

							row.createCell(1).setCellValue(reportPayOutDTO.getEmpName());

							row.createCell(2).setCellValue(reportPayOutDTO.getDesignationName());

							row.createCell(3).setCellValue(reportPayOutDTO.getDepartmentName());

							Cell createdDateCell = row.createCell(4);
							createdDateCell.setCellValue(reportPayOutDTO.getDateOfJoining());
							createdDateCell.setCellStyle(dateCellStyle);

							row.createCell(5).setCellValue(reportPayOutDTO.getGender());
							row.createCell(6).setCellValue(reportPayOutDTO.getJobLocation());
							row.createCell(7).setCellValue(reportPayOutDTO.getDaysInMonth());
							row.createCell(8).setCellValue(reportPayOutDTO.getAbsent());
							row.createCell(9).setCellValue(reportPayOutDTO.getPayDays().doubleValue());

							Cell cell10 = row.createCell(10);
							cell10.setCellStyle(addZero);
							cell10.setCellValue((reportPayOutDTO.getMonthalyGross().doubleValue()));
							Map<String, String> payHeadsMap = reportPayOutDTO.getPayHeadsMap();

							int value = 11;

							for (int i = 0; i < earnngPayHeadsId.length; i++) {
								Cell cell = row.createCell(value);
								cell.setCellStyle(addZero);
								if (payHeadsMap.get((earnngPayHeadsId[i])) != null)
									cell.setCellValue(
											new BigDecimal(payHeadsMap.get((earnngPayHeadsId[i]))).doubleValue());

								value++;
								if (payHeadsMap.get((earnngPayHeadsId[i])) != null) {
									BigDecimal bigDecimalCurrency = new BigDecimal(
											payHeadsMap.get((earnngPayHeadsId[i])));
									if (index == 0) {
										totalSumMap.put(earnngPayHeadsId[i], bigDecimalCurrency);
									} else {
										BigDecimal currentSum = totalSumMap.get(earnngPayHeadsId[i]);
										if (currentSum == null)
											currentSum = BigDecimal.ZERO;
										totalSumMap.put(earnngPayHeadsId[i], bigDecimalCurrency.add(currentSum));
									}
									sum = sum.add(bigDecimalCurrency);
								}

							}
							index++;

							Cell cells = row.createCell(value);
							cells.setCellValue(sum.doubleValue());
							cells.setCellStyle(addZero);

							value++;
							totalSum = totalSum.add(sum);
							for (int i = 0; i < deductionPayHeadsId.length; i++) {
								Cell cell = row.createCell(value);
								cell.setCellStyle(addZero);
								if (payHeadsMap.get((deductionPayHeadsId[i])) != null)
									cell.setCellValue(
											new BigDecimal(payHeadsMap.get((deductionPayHeadsId[i]))).doubleValue());

								value++;
								if (payHeadsMap.get((deductionPayHeadsId[i])) != null) {
									BigDecimal currency = new BigDecimal(payHeadsMap.get((deductionPayHeadsId[i])));
									if (deductionIndex == 0) {
										deductionTotalSumMap.put(deductionPayHeadsId[i], currency);
									} else {
										BigDecimal deductionCurrentSum = deductionTotalSumMap
												.get(deductionPayHeadsId[i]);
										if (deductionCurrentSum == null)
											deductionCurrentSum = BigDecimal.ZERO;
										deductionTotalSumMap.put(deductionPayHeadsId[i],
												currency.add(deductionCurrentSum));

									}
									deductionSum = deductionSum.add(currency);
								}
							}

							deductionIndex++;

							Cell cell = row.createCell(value);
							cell.setCellValue(deductionSum.doubleValue());
							cell.setCellStyle(addZero);
							value++;
							deductionTotalSum = deductionTotalSum.add(deductionSum);

							totalNetPayable = totalNetPayable.add(reportPayOutDTO.getNetPayableAmount());

							Cell net = row.createCell(value++);
							net.setCellValue(reportPayOutDTO.getNetPayableAmount().doubleValue());
							net.setCellStyle(addZero);

							row.createCell(value++).setCellValue(reportPayOutDTO.getBankName());

							row.createCell(value++).setCellValue(reportPayOutDTO.getAccountNumber());

							Cell cell1 = row.createCell(value++);
							cell1.setCellValue(reportPayOutDTO.getIfscCode());

							row.createCell(value++).setCellValue(reportPayOutDTO.getBranchName());

						}

					}

					headerCellStyle.setVerticalAlignment(VerticalAlignment.JUSTIFY);
					int newValue = 11;
					Row rowNew = sheet.createRow(rowNum);

					for (int i = 0; i < earnngPayHeadsId.length; i++) {
						Cell createdDateCell = rowNew.createCell(newValue);
						createdDateCell.setCellStyle(addZeroBold);
						createdDateCell.setCellValue(convertBigdecimalToDouble(totalSumMap.get((earnngPayHeadsId[i]))));
						newValue++;
					}
					Cell t = rowNew.createCell(newValue);
					t.setCellValue(totalSum.doubleValue());
					t.setCellStyle(addZeroBold);
					newValue++;
					for (int i = 0; i < deductionPayHeadsId.length; i++) {
						Cell createdDateCell = rowNew.createCell(newValue);
						createdDateCell.setCellStyle(addZeroBold);
						createdDateCell.setCellValue(
								convertBigdecimalToDouble(deductionTotalSumMap.get((deductionPayHeadsId[i]))));
						newValue++;
					}

					Cell createdDateCell = rowNew.createCell(newValue);
					createdDateCell.setCellValue(deductionTotalSum.doubleValue());
					createdDateCell.setCellStyle(addZeroBold);
					newValue++;

					Cell netpay = rowNew.createCell(newValue);
					netpay.setCellValue(totalNetPayable.doubleValue());
					netpay.setCellStyle(addZeroBold);

					for (int i = 0; i < columns.length; i++) {
						sheet.autoSizeColumn(i);
					}
					// return workbook;

				}

			}
		}

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

		CellStyle headerCellStyleGreen = workbook.createCellStyle();
		headerCellStyleGreen.setFont(headerFont);
		headerCellStyleGreen.setAlignment(HorizontalAlignment.CENTER);
		headerCellStyleGreen.setVerticalAlignment(VerticalAlignment.CENTER);
		headerCellStyleGreen.setFillBackgroundColor(IndexedColors.LIGHT_GREEN.getIndex());
		headerCellStyleGreen.setFillPattern(FillPatternType.FINE_DOTS);

		CellStyle headerCellStyleRed = workbook.createCellStyle();
		headerCellStyleRed.setFont(headerFont);
		headerCellStyleRed.setAlignment(HorizontalAlignment.CENTER);
		headerCellStyleRed.setVerticalAlignment(VerticalAlignment.CENTER);
		headerCellStyleRed.setFillBackgroundColor(IndexedColors.LIGHT_ORANGE.getIndex());
		headerCellStyleRed.setFillPattern(FillPatternType.FINE_DOTS);

		CellStyle headerCellStyle111 = workbook.createCellStyle();
		headerCellStyle111.setFont(headerFont);
		headerCellStyle111.setAlignment(HorizontalAlignment.LEFT);
		headerCellStyle111.setVerticalAlignment(VerticalAlignment.CENTER);

		CellStyle headerCellStyle101 = workbook.createCellStyle();
		headerCellStyle101.setFont(headerFont);
		headerCellStyle101.setAlignment(HorizontalAlignment.CENTER);
		headerCellStyle101.setVerticalAlignment(VerticalAlignment.CENTER);

		CellStyle addZero = workbook.createCellStyle();
		addZero.setDataFormat(createHelper.createDataFormat().getFormat("0.00"));
		addZero.setAlignment(HorizontalAlignment.RIGHT);
		addZero.setVerticalAlignment(VerticalAlignment.CENTER);

		CellStyle addZeroBold = workbook.createCellStyle();
		addZeroBold.setDataFormat(createHelper.createDataFormat().getFormat("0.00"));
		addZeroBold.setFont(headerFont);
		addZeroBold.setAlignment(HorizontalAlignment.RIGHT);
		addZeroBold.setVerticalAlignment(VerticalAlignment.CENTER);

		Row row0 = summarySheet.createRow(0);
		summarySheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 13));
		Cell cellCom = row0.createCell(0);
		cellCom.setCellValue("Annual Salary Computation - FY " + financialYear);
		cellCom.setCellStyle(headerCellStyle101);

		Row row1 = summarySheet.createRow(1);

		summarySheet.addMergedRegion(new CellRangeAddress(1, 2, 0, 0));
		Cell month = row1.createCell(0);
		month.setCellValue("Month");
		month.setCellStyle(headerCellStyle111);

		summarySheet.addMergedRegion(new CellRangeAddress(1, 1, 1, 5));
		Cell cellRow1 = row1.createCell(1);
		cellRow1.setCellValue("Earnings");
		cellRow1.setCellStyle(headerCellStyleGreen);

		summarySheet.addMergedRegion(new CellRangeAddress(1, 1, 6, 17));
		Cell cellRow10 = row1.createCell(6);
		cellRow10.setCellValue("Deductions");
		cellRow10.setCellStyle(headerCellStyleRed);

		Row headerRow = summarySheet.createRow(2);

		for (int i = 1; i < summaryColumns.length; i++) {
			Cell cell = headerRow.createCell(i);
			cell.setCellValue(summaryColumns[i].toString());
			cell.setCellStyle(headerCellStyle);
		}

		CellStyle dateCellStyle = workbook.createCellStyle();
		dateCellStyle.setDataFormat(createHelper.createDataFormat().getFormat("dd-MM-yyyy"));

		CellStyle dateCellStyles = workbook.createCellStyle();
		dateCellStyles.setDataFormat(createHelper.createDataFormat().getFormat(""));

		int rowNum = 3;
		// BigDecimal sum = BigDecimal.ZERO;

		BigDecimal totalGross = BigDecimal.ZERO;
		BigDecimal totalBasicEarning = BigDecimal.ZERO;
		BigDecimal totalNetPayable = BigDecimal.ZERO;
		BigDecimal totalOtherEarning = BigDecimal.ZERO;
		BigDecimal totalNetPayableAmount = BigDecimal.ZERO;
		BigDecimal totalTotalEarning = BigDecimal.ZERO;
		BigDecimal totalProvidentFundEmployee = BigDecimal.ZERO;
		BigDecimal totalProvidentFundEmployer = BigDecimal.ZERO;
		BigDecimal totalEsi_Employee = BigDecimal.ZERO;
		BigDecimal totalEsi_Employer = BigDecimal.ZERO;
		BigDecimal totalPT = BigDecimal.ZERO;
		BigDecimal totalTds = BigDecimal.ZERO;
		BigDecimal totalLoan = BigDecimal.ZERO;
		BigDecimal totalOtherDeduction = BigDecimal.ZERO;
		BigDecimal totalEPSEmployer = BigDecimal.ZERO;
		BigDecimal totalAdminCharges = BigDecimal.ZERO;
		BigDecimal totalLWFEmployee = BigDecimal.ZERO;
		BigDecimal totalLWFEmployeer = BigDecimal.ZERO;
		BigDecimal CTC = BigDecimal.ZERO;
		Map<String, BigDecimal> totalSumMap = new HashMap<String, BigDecimal>();
		Map<String, BigDecimal> deductionTotalSumMap = new HashMap<String, BigDecimal>();

		if (annualSummaryList != null) {
			int index = 0;
			int deductionIndex = 0;

			for (ReportPayOutDTO annualSummary : annualSummaryList) {
				Row row = summarySheet.createRow(rowNum++);

				Cell cell = row.createCell(0);
				cell.setCellValue(annualSummary.getProcessMonth());

				Cell cell1 = row.createCell(1);
				cell1.setCellValue(annualSummary.getGrossSalary() != null ? annualSummary.getGrossSalary().doubleValue()
						: new BigDecimal(0.00).doubleValue());
				cell1.setCellStyle(addZero);

				totalGross = totalGross.add(
						annualSummary.getGrossSalary() != null ? annualSummary.getGrossSalary() : new BigDecimal(0.00));

				Cell cell2 = row.createCell(2);
				cell2.setCellValue(annualSummary.getBasicEarning().doubleValue() != 0
						? annualSummary.getBasicEarning().doubleValue()
						: new BigDecimal(0.00).doubleValue());
				cell2.setCellStyle(addZero);

				totalBasicEarning = totalBasicEarning
						.add(annualSummary.getBasicEarning() != null ? annualSummary.getBasicEarning()
								: new BigDecimal(0.00));

				Cell cell3 = row.createCell(3);
				cell3.setCellValue(annualSummary.getOtherEarning().doubleValue() != 0
						? annualSummary.getOtherEarning().doubleValue()
						: new BigDecimal(0.00).doubleValue());
				cell3.setCellStyle(addZero);

				totalOtherEarning = totalOtherEarning
						.add(annualSummary.getOtherEarning() != null ? annualSummary.getOtherEarning()
								: new BigDecimal(0.00));

				Cell cell4 = row.createCell(4);
				cell4.setCellValue(annualSummary.getNetPayableAmount().doubleValue() != 0
						? annualSummary.getNetPayableAmount().doubleValue()
						: new BigDecimal(0.00).doubleValue());
				cell4.setCellStyle(addZero);

				totalNetPayable = totalNetPayable
						.add(annualSummary.getNetPayableAmount() != null ? annualSummary.getNetPayableAmount()
								: new BigDecimal(0.00));

				Cell cell5 = row.createCell(5);
				cell5.setCellValue(annualSummary.getTotalEarning().doubleValue() != 0
						? annualSummary.getTotalEarning().doubleValue()
						: new BigDecimal(0.00).doubleValue());
				cell5.setCellStyle(addZero);

				totalTotalEarning = totalTotalEarning
						.add(annualSummary.getTotalEarning() != null ? annualSummary.getTotalEarning()
								: new BigDecimal(0.00));

				Cell cell6 = row.createCell(6);
				cell6.setCellValue(annualSummary.getProvidentFundEmployee() != null
						? annualSummary.getProvidentFundEmployee().doubleValue()
						: new BigDecimal(0.00).doubleValue());
				cell6.setCellStyle(addZero);

				totalProvidentFundEmployee = totalProvidentFundEmployee
						.add(annualSummary.getProvidentFundEmployee() != null ? annualSummary.getProvidentFundEmployee()
								: new BigDecimal(0.00));

				Cell cell7 = row.createCell(7);
				cell7.setCellValue(annualSummary.getProvidentFundEmployer().doubleValue() != 0
						? annualSummary.getProvidentFundEmployer().doubleValue()
						: new BigDecimal(0.00).doubleValue());
				cell7.setCellStyle(addZero);

				totalProvidentFundEmployer = totalProvidentFundEmployer
						.add(annualSummary.getProvidentFundEmployer() != null ? annualSummary.getProvidentFundEmployer()
								: new BigDecimal(0.00));
//mobile

				Cell cell8 = row.createCell(8);
				cell8.setCellValue(annualSummary.getProvidentFundEmployerPension() != null
						? annualSummary.getProvidentFundEmployerPension().doubleValue()
						: new BigDecimal(0.00).doubleValue());
				cell8.setCellStyle(addZero);

				totalEPSEmployer = totalEPSEmployer.add(annualSummary.getProvidentFundEmployerPension() != null
						? annualSummary.getProvidentFundEmployerPension()
						: new BigDecimal(0.00));

				Cell cell9 = row.createCell(9);
				cell9.setCellValue(annualSummary.getAdminCharge() != null ? annualSummary.getAdminCharge().doubleValue()
						: new BigDecimal(0.00).doubleValue());
				cell9.setCellStyle(addZero);

				totalAdminCharges = totalAdminCharges.add(
						annualSummary.getAdminCharge() != null ? annualSummary.getAdminCharge() : new BigDecimal(0.00));

				// mobile

				Cell cell10 = row.createCell(10);
				cell10.setCellValue(
						annualSummary.getEsi_Employee() != null ? annualSummary.getEsi_Employee().doubleValue()
								: new BigDecimal(0.00).doubleValue());
				cell10.setCellStyle(addZero);

				totalEsi_Employee = totalEsi_Employee
						.add(annualSummary.getEsi_Employee() != null ? annualSummary.getEsi_Employee()
								: new BigDecimal(0.00));

				Cell cell11 = row.createCell(11);
				cell11.setCellValue(annualSummary.getEsi_Employer().doubleValue() != 0
						? annualSummary.getEsi_Employer().doubleValue()
						: new BigDecimal(0.00).doubleValue());
				cell11.setCellStyle(addZero);

				totalEsi_Employer = totalEsi_Employer
						.add(annualSummary.getEsi_Employer() != null ? annualSummary.getEsi_Employer()
								: new BigDecimal(0.00));

				// mobile
				Cell cell12 = row.createCell(12);
				cell12.setCellValue(annualSummary.getLwfEmployeeAmount() != null
						? annualSummary.getLwfEmployeeAmount().doubleValue()
						: new BigDecimal(0.00).doubleValue());
				cell12.setCellStyle(addZero);

				totalLWFEmployee = totalLWFEmployee
						.add(annualSummary.getLwfEmployeeAmount() != null ? annualSummary.getLwfEmployeeAmount()
								: new BigDecimal(0.00));

				Cell cell13 = row.createCell(13);
				cell13.setCellValue(annualSummary.getLwfEmployerAmount() != null
						? annualSummary.getLwfEmployerAmount().doubleValue()
						: new BigDecimal(0.00).doubleValue());
				cell13.setCellStyle(addZero);

				totalLWFEmployeer = totalLWFEmployeer
						.add(annualSummary.getLwfEmployerAmount() != null ? annualSummary.getLwfEmployerAmount()
								: new BigDecimal(0.00));
				// mobile

				Cell cell14 = row.createCell(14);
				cell14.setCellValue(annualSummary.getPt() != null ? annualSummary.getPt().doubleValue()
						: new BigDecimal(0.00).doubleValue());
				cell14.setCellStyle(addZero);

				totalPT = totalPT.add(annualSummary.getPt() != null ? annualSummary.getPt() : new BigDecimal(0.00));

				Cell cell15 = row.createCell(15);
				cell15.setCellValue(annualSummary.getTds() != null ? annualSummary.getTds().doubleValue()
						: new BigDecimal(0.00).doubleValue());
				cell15.setCellStyle(addZero);

				totalTds = totalTds.add(annualSummary.getTds() != null ? annualSummary.getTds() : new BigDecimal(0.00));

				Cell cell16 = row.createCell(16);
				cell16.setCellValue(annualSummary.getLoan() != null ? annualSummary.getLoan().doubleValue()
						: new BigDecimal(0.00).doubleValue());
				cell16.setCellStyle(addZero);

				totalLoan = totalLoan
						.add(annualSummary.getLoan() != null ? annualSummary.getLoan() : new BigDecimal(0.00));

				Cell cell17 = row.createCell(17);
				cell17.setCellValue(
						annualSummary.getOtherDeduction() != null ? annualSummary.getOtherDeduction().doubleValue()
								: new BigDecimal(0.00).doubleValue());
				cell17.setCellStyle(addZero);

				totalOtherDeduction = totalOtherDeduction
						.add(annualSummary.getOtherDeduction() != null ? annualSummary.getOtherDeduction()
								: new BigDecimal(0.00));

				Cell cell18 = row.createCell(18);
				cell18.setCellValue(annualSummary.getCtc() != null ? annualSummary.getCtc().doubleValue()
						: new BigDecimal(0.00).doubleValue());
				cell18.setCellStyle(addZero);

				CTC = CTC.add(annualSummary.getCtc() != null ? annualSummary.getCtc() : new BigDecimal(0.00));
				index++;
			}

			Row row = summarySheet.createRow(rowNum++);

			Cell cell13 = row.createCell(1);
			cell13.setCellValue(totalGross != null ? totalGross.doubleValue() : new BigDecimal(0.00).doubleValue());
			cell13.setCellStyle(addZeroBold);

			Cell cell14 = row.createCell(2);
			cell14.setCellValue(
					totalBasicEarning != null ? totalBasicEarning.doubleValue() : new BigDecimal(0.00).doubleValue());
			cell14.setCellStyle(addZeroBold);

			Cell cell15 = row.createCell(3);
			cell15.setCellValue(
					totalOtherEarning != null ? totalOtherEarning.doubleValue() : new BigDecimal(0.00).doubleValue());
			cell15.setCellStyle(addZeroBold);

			Cell cell16 = row.createCell(4);
			cell16.setCellValue(
					totalNetPayable != null ? totalNetPayable.doubleValue() : new BigDecimal(0.00).doubleValue());
			cell16.setCellStyle(addZeroBold);

			Cell cell17 = row.createCell(5);
			cell17.setCellValue(
					totalTotalEarning != null ? totalTotalEarning.doubleValue() : new BigDecimal(0.00).doubleValue());
			cell17.setCellStyle(addZeroBold);

			Cell cell18 = row.createCell(6);
			cell18.setCellValue(totalProvidentFundEmployee != null ? totalProvidentFundEmployee.doubleValue()
					: new BigDecimal(0.00).doubleValue());
			cell18.setCellStyle(addZeroBold);

			Cell cell19 = row.createCell(7);
			cell19.setCellValue(totalProvidentFundEmployer != null ? totalProvidentFundEmployer.doubleValue()
					: new BigDecimal(0.00).doubleValue());
			cell19.setCellStyle(addZeroBold);

			Cell cell1eps = row.createCell(8);
			cell1eps.setCellValue(
					totalEPSEmployer != null ? totalEPSEmployer.doubleValue() : new BigDecimal(0.00).doubleValue());
			cell1eps.setCellStyle(addZeroBold);

			Cell admin = row.createCell(9);
			admin.setCellValue(
					totalAdminCharges != null ? totalAdminCharges.doubleValue() : new BigDecimal(0.00).doubleValue());
			admin.setCellStyle(addZeroBold);

			Cell cell10 = row.createCell(10);
			cell10.setCellValue(
					totalEsi_Employee != null ? totalEsi_Employee.doubleValue() : new BigDecimal(0.00).doubleValue());
			cell10.setCellStyle(addZeroBold);

			Cell cell11 = row.createCell(11);
			cell11.setCellValue(
					totalEsi_Employer != null ? totalEsi_Employer.doubleValue() : new BigDecimal(0.00).doubleValue());
			cell11.setCellStyle(addZeroBold);

			Cell lwfEmp = row.createCell(12);
			lwfEmp.setCellValue(
					totalLWFEmployee != null ? totalLWFEmployee.doubleValue() : new BigDecimal(0.00).doubleValue());
			lwfEmp.setCellStyle(addZeroBold);

			Cell lwfEmpr = row.createCell(13);
			lwfEmpr.setCellValue(
					totalLWFEmployeer != null ? totalLWFEmployeer.doubleValue() : new BigDecimal(0.00).doubleValue());
			lwfEmpr.setCellStyle(addZeroBold);

			Cell cell12 = row.createCell(14);
			cell12.setCellValue(totalPT != null ? totalPT.doubleValue() : new BigDecimal(0.00).doubleValue());
			cell12.setCellStyle(addZeroBold);

			Cell cell110 = row.createCell(15);
			cell110.setCellValue(totalTds != null ? totalTds.doubleValue() : new BigDecimal(0.00).doubleValue());
			cell110.setCellStyle(addZeroBold);

			Cell cell112 = row.createCell(16);
			cell112.setCellValue(totalLoan != null ? totalLoan.doubleValue() : new BigDecimal(0.00).doubleValue());
			cell112.setCellStyle(addZeroBold);

			Cell cell113 = row.createCell(17);
			cell113.setCellValue(totalOtherDeduction != null ? totalOtherDeduction.doubleValue()
					: new BigDecimal(0.00).doubleValue());
			cell113.setCellStyle(addZeroBold);

			Cell ctc = row.createCell(18);
			ctc.setCellValue(CTC != null ? CTC.doubleValue() : new BigDecimal(0.00).doubleValue());
			ctc.setCellStyle(addZeroBold);

		}
		for (int i = 0; i < summaryColumns.length; i++) {
			summarySheet.autoSizeColumn(i);
		}

		return workbook;

	}

	@SuppressWarnings("deprecation")
	public static Workbook SalarySheetReport(List<ReportPayOutDTO> reportPayOutDTOList, Object[] columns,
			String processMonth, String[] earnngPayHeadsId, String[] deductionPayHeadsId) {

		// , Map<String, String> payHeadsMap
		Workbook workbook = new XSSFWorkbook(); // new HSSFWorkbook() for generating `.xls` file

		CreationHelper createHelper = workbook.getCreationHelper();

		Sheet sheet = workbook.createSheet("Salary Sheet Monthly Report");

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

		CellStyle headerCellStyle1 = workbook.createCellStyle();
		headerCellStyle1.setFont(headerFont);
		headerCellStyle1.setAlignment(HorizontalAlignment.RIGHT);
		headerCellStyle1.setVerticalAlignment(VerticalAlignment.CENTER);
		headerCellStyle1.setFillBackgroundColor(IndexedColors.LIGHT_CORNFLOWER_BLUE.getIndex());
		headerCellStyle1.setFillPattern(FillPatternType.FINE_DOTS);

		CellStyle headerCellStyle11 = workbook.createCellStyle();
		headerCellStyle11.setFont(headerFont);
		headerCellStyle11.setAlignment(HorizontalAlignment.LEFT);
		headerCellStyle11.setVerticalAlignment(VerticalAlignment.CENTER);
		CellStyle headerCellStyle111 = workbook.createCellStyle();
		headerCellStyle111.setFont(headerFont);
		headerCellStyle111.setAlignment(HorizontalAlignment.LEFT);
		headerCellStyle111.setVerticalAlignment(VerticalAlignment.CENTER);

		CellStyle headerCellStyle112 = workbook.createCellStyle();
		headerCellStyle112.setAlignment(HorizontalAlignment.LEFT);
		headerCellStyle112.setVerticalAlignment(VerticalAlignment.CENTER);

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

		CellStyle addZero = workbook.createCellStyle();
		addZero.setDataFormat(createHelper.createDataFormat().getFormat("0.00"));
		addZero.setAlignment(HorizontalAlignment.RIGHT);
		addZero.setVerticalAlignment(VerticalAlignment.CENTER);

		CellStyle addZeroBold = workbook.createCellStyle();
		addZeroBold.setDataFormat(createHelper.createDataFormat().getFormat("0.00"));
		addZeroBold.setFont(headerFont);
		addZeroBold.setAlignment(HorizontalAlignment.RIGHT);
		addZeroBold.setVerticalAlignment(VerticalAlignment.CENTER);

		Row row0 = sheet.createRow(0);
		sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 10));
		Cell cellCom = row0.createCell(0);
		cellCom.setCellValue("Salary Sheet - " + processMonth);
		cellCom.setCellStyle(headerCellStyle111);

		Row headerRow = sheet.createRow(1);
		// Creating cells
		for (int i = 0; i < columns.length; i++) {
			Cell cell = headerRow.createCell(i);
			cell.setCellValue(columns[i].toString());
			cell.setCellStyle(headerCellStyle);

		}

		CellStyle dateCellStyle = workbook.createCellStyle();
		dateCellStyle.setDataFormat(createHelper.createDataFormat().getFormat("dd-MM-yyyy"));

		CellStyle dateCellStyles = workbook.createCellStyle();
		dateCellStyles.setDataFormat(createHelper.createDataFormat().getFormat(""));

		int rowNum = 2;
		// BigDecimal sum = BigDecimal.ZERO;

		BigDecimal totalSum = BigDecimal.ZERO;
		BigDecimal deductionTotalSum = BigDecimal.ZERO;
		BigDecimal totalNetPayable = BigDecimal.ZERO;

		Map<String, BigDecimal> totalSumMap = new HashMap<String, BigDecimal>();
		Map<String, BigDecimal> deductionTotalSumMap = new HashMap<String, BigDecimal>();

		if (reportPayOutDTOList != null) {
			int index = 0;
			int deductionIndex = 0;

			for (ReportPayOutDTO reportPayOutDTO : reportPayOutDTOList) {
				BigDecimal sum = BigDecimal.ZERO;
				BigDecimal deductionSum = BigDecimal.ZERO;
				Row row = sheet.createRow(rowNum++);

				if (reportPayOutDTO.getEmployeeCode() != null && reportPayOutDTO.getEmployeeCode().trim() != "")
					row.createCell(0).setCellValue(reportPayOutDTO.getEmployeeCode());
				else
					row.createCell(0).setCellValue("Under Process");

				row.createCell(1).setCellValue(reportPayOutDTO.getEmpName());

				row.createCell(2).setCellValue(reportPayOutDTO.getDesignationName());

				row.createCell(3).setCellValue(reportPayOutDTO.getDepartmentName());

				Cell createdDateCell = row.createCell(4);
				createdDateCell.setCellValue(reportPayOutDTO.getDateOfJoining());
				createdDateCell.setCellStyle(dateCellStyle);

				row.createCell(5).setCellValue(reportPayOutDTO.getGender());
				row.createCell(6).setCellValue(reportPayOutDTO.getJobLocation());
				row.createCell(7).setCellValue(reportPayOutDTO.getDaysInMonth());
				row.createCell(8).setCellValue(reportPayOutDTO.getAbsent());

				// if (reportPayOutDTO.getPayDays() != null)
				row.createCell(9).setCellValue(reportPayOutDTO.getPayDays().doubleValue());

				// row.createCell(10).setCellValue(convertBigdecimalToDouble(reportPayOutDTO.getMonthalyGross()));
				Cell cell10 = row.createCell(10);
				cell10.setCellStyle(addZero);
				cell10.setCellValue((reportPayOutDTO.getMonthalyGross().doubleValue()));
				Map<String, String> payHeadsMap = reportPayOutDTO.getPayHeadsMap();

				int value = 11;

				for (int i = 0; i < earnngPayHeadsId.length; i++) {
					Cell cell = row.createCell(value);
					cell.setCellStyle(addZero);
					if (payHeadsMap.get((earnngPayHeadsId[i])) != null)
						cell.setCellValue(new BigDecimal(payHeadsMap.get((earnngPayHeadsId[i]))).doubleValue());

					value++;
					if (payHeadsMap.get((earnngPayHeadsId[i])) != null) {
						BigDecimal bigDecimalCurrency = new BigDecimal(payHeadsMap.get((earnngPayHeadsId[i])));
						if (index == 0) {
							totalSumMap.put(earnngPayHeadsId[i], bigDecimalCurrency);
						} else {
							BigDecimal currentSum = totalSumMap.get(earnngPayHeadsId[i]);
							if (currentSum == null)
								currentSum = BigDecimal.ZERO;
							totalSumMap.put(earnngPayHeadsId[i], bigDecimalCurrency.add(currentSum));
						}
						sum = sum.add(bigDecimalCurrency);
					}

				}
				index++;

				Cell cells = row.createCell(value);
				cells.setCellValue(sum.doubleValue());
				cells.setCellStyle(addZero);

				value++;
				totalSum = totalSum.add(sum);
				for (int i = 0; i < deductionPayHeadsId.length; i++) {
					Cell cell = row.createCell(value);
					cell.setCellStyle(addZero);
					if (payHeadsMap.get((deductionPayHeadsId[i])) != null)
						cell.setCellValue(new BigDecimal(payHeadsMap.get((deductionPayHeadsId[i]))).doubleValue());

					value++;
					if (payHeadsMap.get((deductionPayHeadsId[i])) != null) {
						BigDecimal currency = new BigDecimal(payHeadsMap.get((deductionPayHeadsId[i])));
						if (deductionIndex == 0) {
							deductionTotalSumMap.put(deductionPayHeadsId[i], currency);
						} else {
							BigDecimal deductionCurrentSum = deductionTotalSumMap.get(deductionPayHeadsId[i]);
							if (deductionCurrentSum == null)
								deductionCurrentSum = BigDecimal.ZERO;
							deductionTotalSumMap.put(deductionPayHeadsId[i], currency.add(deductionCurrentSum));

						}
						deductionSum = deductionSum.add(currency);
					}
				}

				deductionIndex++;

				Cell cell = row.createCell(value);
				cell.setCellValue(deductionSum.doubleValue());
				cell.setCellStyle(addZero);
				value++;
				deductionTotalSum = deductionTotalSum.add(deductionSum);

				totalNetPayable = totalNetPayable.add(reportPayOutDTO.getNetPayableAmount());

				Cell net = row.createCell(value++);
				net.setCellValue(reportPayOutDTO.getNetPayableAmount().doubleValue());
				net.setCellStyle(addZero);

				row.createCell(value++).setCellValue(reportPayOutDTO.getBankName());

				row.createCell(value++).setCellValue(reportPayOutDTO.getAccountNumber());

				Cell cell1 = row.createCell(value++);
				cell1.setCellValue(reportPayOutDTO.getIfscCode());

				row.createCell(value++).setCellValue(reportPayOutDTO.getBranchName());

			}

		}

		headerCellStyle.setVerticalAlignment(VerticalAlignment.JUSTIFY);
		int newValue = 11;
		Row rowNew = sheet.createRow(rowNum);

		for (int i = 0; i < earnngPayHeadsId.length; i++) {
			Cell createdDateCell = rowNew.createCell(newValue);
			createdDateCell.setCellStyle(addZeroBold);
			// if(totalSumMap.get((earnngPayHeadsId[i]))!=null)
			// createdDateCell.setCellValue(totalSumMap.get((earnngPayHeadsId[i])).doubleValue());
			createdDateCell.setCellValue(convertBigdecimalToDouble(totalSumMap.get((earnngPayHeadsId[i]))));
			newValue++;
		}
		Cell t = rowNew.createCell(newValue);
		t.setCellValue(totalSum.doubleValue());
		t.setCellStyle(addZeroBold);
		newValue++;
		for (int i = 0; i < deductionPayHeadsId.length; i++) {
			Cell createdDateCell = rowNew.createCell(newValue);
			createdDateCell.setCellStyle(addZeroBold);
			// if(totalSumMap.get((deductionPayHeadsId[i]))!=null)
			// createdDateCell.setCellValue(
			// deductionTotalSumMap.get((deductionPayHeadsId[i])).doubleValue());
			createdDateCell.setCellValue(convertBigdecimalToDouble(deductionTotalSumMap.get((deductionPayHeadsId[i]))));
			newValue++;
		}

		Cell createdDateCell = rowNew.createCell(newValue);
		createdDateCell.setCellValue(deductionTotalSum.doubleValue());
		createdDateCell.setCellStyle(addZeroBold);
		newValue++;

		Cell netpay = rowNew.createCell(newValue);
		netpay.setCellValue(totalNetPayable.doubleValue());
		netpay.setCellStyle(addZeroBold);
		for (int i = 0; i < columns.length; i++) {
			sheet.autoSizeColumn(i);
		}
		return workbook;

	}

	public static void incomeTaxAnnualyDetails(ReportPayOutDTO prvObj, ReportPayOutDTO next,
			HashMap<String, ReportPayOutDTO> map) {

		prvObj = map.get(next.getEmployeeCode());

		if (prvObj.getEmployeeCode().equals(next.getEmployeeCode())
				&& !prvObj.getProcessMonth().equals(next.getProcessMonth())) {

			if (next.getProcessMonth().substring(0, 3).equalsIgnoreCase("APR")) {

				prvObj.setApr(next.getTds());

			} else if (next.getProcessMonth().substring(0, 3).equalsIgnoreCase("MAY")) {

				prvObj.setMay(next.getTds());

			} else if (next.getProcessMonth().substring(0, 3).equalsIgnoreCase("JUN")) {

				prvObj.setJun(next.getTds());

			} else if (next.getProcessMonth().substring(0, 3).equalsIgnoreCase("JUL")) {

				prvObj.setJul(next.getTds());

			} else if (next.getProcessMonth().substring(0, 3).equalsIgnoreCase("AUG")) {

				prvObj.setAug(next.getTds());

			} else if (next.getProcessMonth().substring(0, 3).equalsIgnoreCase("SEP")) {

				prvObj.setSep(next.getTds());

			} else if (next.getProcessMonth().substring(0, 3).equalsIgnoreCase("OCT")) {

				prvObj.setOct(next.getTds());

			} else if (next.getProcessMonth().substring(0, 3).equalsIgnoreCase("NOV")) {

				prvObj.setNov(next.getTds());

			} else if (next.getProcessMonth().substring(0, 3).equalsIgnoreCase("DEC")) {

				prvObj.setDec(next.getTds());

			} else if (next.getProcessMonth().substring(0, 3).equalsIgnoreCase("JAN")) {

				prvObj.setJan(next.getTds());

			} else if (next.getProcessMonth().substring(0, 3).equalsIgnoreCase("FEB")) {

				prvObj.setFeb(next.getTds());

			} else if (next.getProcessMonth().substring(0, 3).equalsIgnoreCase("MAR")) {

				prvObj.setMar(next.getTds());

			}

		}

	}

	public static Workbook incomeTaxMonthlyReportWriter(List<ReportPayOutDTO> reportPayOutDTOList, String[] columns,
			String processMonth, Long financialYearId) {

		Workbook workbook = new XSSFWorkbook();

		CreationHelper createHelper = workbook.getCreationHelper();

		Sheet sheet = null;

		// Create a Sheet
		if (financialYearId != 0L)
			sheet = workbook.createSheet(" Income Tax Statement Yearly ");
		else
			sheet = workbook.createSheet(" Income Tax Statement Monthly ");

		// Create a Font for styling header cells
		Font headerFont = workbook.createFont();
		headerFont.setBold(true);
		headerFont.setFontHeightInPoints((short) 12);
		headerFont.setColor(IndexedColors.BLACK.getIndex());

		Font headerFont1 = workbook.createFont();
		headerFont1.setBold(true);
		headerFont1.setFontHeightInPoints((short) 11);
		headerFont1.setColor(IndexedColors.BLACK.getIndex());

		Font rowFont = workbook.createFont();
		rowFont.setFontHeightInPoints((short) 11);
		rowFont.setColor(IndexedColors.BLACK.getIndex());

		// Create a CellStyle with the font
		CellStyle headerCellStyle = workbook.createCellStyle();
		headerCellStyle.setFont(headerFont);
		headerCellStyle.setAlignment(HorizontalAlignment.CENTER);
		headerCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
		// headerCellStyle.setVerticalAlignment(VerticalAlignment.JUSTIFY);

		CellStyle headerCellStyle11 = workbook.createCellStyle();
		headerCellStyle11.setFont(headerFont1);
		headerCellStyle11.setAlignment(HorizontalAlignment.CENTER);
		headerCellStyle11.setVerticalAlignment(VerticalAlignment.CENTER);
		headerCellStyle11.setFillBackgroundColor(IndexedColors.LIGHT_CORNFLOWER_BLUE.getIndex());
		headerCellStyle11.setFillPattern(FillPatternType.FINE_DOTS);
		headerCellStyle11.setWrapText(true);

		// Create a CellStyle with the font
		CellStyle headerCellStyle1 = workbook.createCellStyle();
		headerCellStyle1.setFont(headerFont);
		headerCellStyle1.setAlignment(HorizontalAlignment.LEFT);
		headerCellStyle1.setVerticalAlignment(VerticalAlignment.CENTER);

		CellStyle headerCellStyle1t = workbook.createCellStyle();
		headerCellStyle1t.setFont(headerFont1);
		headerCellStyle1t.setAlignment(HorizontalAlignment.RIGHT);
		headerCellStyle1t.setVerticalAlignment(VerticalAlignment.CENTER);

		// To Get Data In Center For Row
		CellStyle rowCellStyle = workbook.createCellStyle();
		rowCellStyle.setFont(rowFont);
		rowCellStyle.setAlignment(HorizontalAlignment.CENTER);
		rowCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);

		CellStyle dateCellStyle = workbook.createCellStyle();
		dateCellStyle.setDataFormat(createHelper.createDataFormat().getFormat("dd-MMM-yyyy"));
		dateCellStyle.setAlignment(HorizontalAlignment.CENTER);
		dateCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);

		CellStyle addZero = workbook.createCellStyle();
		addZero.setDataFormat(createHelper.createDataFormat().getFormat("0.00"));
		addZero.setAlignment(HorizontalAlignment.RIGHT);
		addZero.setVerticalAlignment(VerticalAlignment.CENTER);

		CellStyle addZeroBold = workbook.createCellStyle();
		addZeroBold.setDataFormat(createHelper.createDataFormat().getFormat("0.00"));
		addZeroBold.setFont(headerFont1);
		addZeroBold.setAlignment(HorizontalAlignment.RIGHT);
		addZeroBold.setVerticalAlignment(VerticalAlignment.CENTER);

		int pMonth = DateUtils.getMonthForProcessMonth(processMonth);
		int pYear = DateUtils.getYearForProcessMonth(processMonth);

		Row row0 = sheet.createRow(0);

		Row headerRow = sheet.createRow(1);

		if (financialYearId != 0L) {

			sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, columns.length));
			Cell cellCom = row0.createCell(0);
			cellCom.setCellValue(
					" Income Tax Statement For FY " + columns[5].substring(6) + " - " + columns[16].substring(6));
			cellCom.setCellStyle(headerCellStyle1);

			Cell cell17 = headerRow.createCell(17);
			cell17.setCellValue(" Total Income Tax ");
			cell17.setCellStyle(headerCellStyle11);

		} else {

			sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, columns.length - 1));
			Cell cellCom = row0.createCell(0);
			cellCom.setCellValue(" Income Tax Statement For " + DateUtils.month[pMonth - 1] + " " + pYear);
			cellCom.setCellStyle(headerCellStyle1);

		}

		// Creating cells
		if (financialYearId != 0L) {
			for (int i = 0; i < columns.length; i++) {

				if (i >= 5 && i <= 16) {

					Cell cell = headerRow.createCell(i);
					cell.setCellValue(columns[i].substring(0, 3) + "-" + columns[i].substring(6));
					cell.setCellStyle(headerCellStyle11);

				} else {

					Cell cell = headerRow.createCell(i);
					cell.setCellValue(columns[i]);
					cell.setCellStyle(headerCellStyle11);

				}

			}
		} else {
			for (int i = 0; i < columns.length; i++) {

				Cell cell = headerRow.createCell(i);
				cell.setCellValue(columns[i]);
				cell.setCellStyle(headerCellStyle11);

			}

		}

		BigDecimal totalGross = BigDecimal.ZERO;
		BigDecimal totalTds = BigDecimal.ZERO;
		BigDecimal totalCess = BigDecimal.ZERO;
		BigDecimal totalIncomeTax = BigDecimal.ZERO;

		BigDecimal aprSum = BigDecimal.ZERO;
		BigDecimal maySum = BigDecimal.ZERO;
		BigDecimal junSum = BigDecimal.ZERO;
		BigDecimal julSum = BigDecimal.ZERO;
		BigDecimal augSum = BigDecimal.ZERO;
		BigDecimal sepSum = BigDecimal.ZERO;
		BigDecimal octSum = BigDecimal.ZERO;
		BigDecimal novSum = BigDecimal.ZERO;
		BigDecimal decSum = BigDecimal.ZERO;
		BigDecimal janSum = BigDecimal.ZERO;
		BigDecimal febSum = BigDecimal.ZERO;
		BigDecimal marSum = BigDecimal.ZERO;

		if (reportPayOutDTOList.size() > 0) {

			int rowNum = 2;

			if (financialYearId != 0L) {

				HashMap<String, ReportPayOutDTO> map = new HashMap<String, ReportPayOutDTO>();

				for (ReportPayOutDTO reportPayOutDTO : reportPayOutDTOList) {

					ReportPayOutDTO obj = new ReportPayOutDTO();

					obj.setEmployeeCode(reportPayOutDTO.getEmployeeCode());
					obj.setEmpName(reportPayOutDTO.getEmpName());
					obj.setDateOfJoining(reportPayOutDTO.getDateOfJoining());
					obj.setPanNo(reportPayOutDTO.getPanNo());
					obj.setTds(reportPayOutDTO.getTds());
					obj.setProcessMonth(reportPayOutDTO.getProcessMonth());

					if (map.get(reportPayOutDTO.getEmployeeCode()) != null ? true : false) {
						incomeTaxAnnualyDetails(obj, reportPayOutDTO, map);
					} else {
						map.put(reportPayOutDTO.getEmployeeCode(), obj);

						if (reportPayOutDTO.getProcessMonth().substring(0, 3).equalsIgnoreCase("APR")) {
							obj.setApr(reportPayOutDTO.getTds());

						} else if (reportPayOutDTO.getProcessMonth().substring(0, 3).equalsIgnoreCase("MAY")) {
							obj.setMay(reportPayOutDTO.getTds());

						} else if (reportPayOutDTO.getProcessMonth().substring(0, 3).equalsIgnoreCase("JUN")) {
							obj.setJun(reportPayOutDTO.getTds());

						} else if (reportPayOutDTO.getProcessMonth().substring(0, 3).equalsIgnoreCase("JUL")) {
							obj.setJul(reportPayOutDTO.getTds());

						} else if (reportPayOutDTO.getProcessMonth().substring(0, 3).equalsIgnoreCase("AUG")) {
							obj.setAug(reportPayOutDTO.getTds());

						} else if (reportPayOutDTO.getProcessMonth().substring(0, 3).equalsIgnoreCase("SEP")) {
							obj.setSep(reportPayOutDTO.getTds());

						} else if (reportPayOutDTO.getProcessMonth().substring(0, 3).equalsIgnoreCase("OCT")) {
							obj.setOct(reportPayOutDTO.getTds());

						} else if (reportPayOutDTO.getProcessMonth().substring(0, 3).equalsIgnoreCase("NOV")) {
							obj.setNov(reportPayOutDTO.getTds());

						} else if (reportPayOutDTO.getProcessMonth().substring(0, 3).equalsIgnoreCase("DEC")) {
							obj.setDec(reportPayOutDTO.getTds());

						} else if (reportPayOutDTO.getProcessMonth().substring(0, 3).equalsIgnoreCase("JAN")) {
							obj.setJan(reportPayOutDTO.getTds());

						} else if (reportPayOutDTO.getProcessMonth().substring(0, 3).equalsIgnoreCase("FEB")) {
							obj.setFeb(reportPayOutDTO.getTds());

						} else if (reportPayOutDTO.getProcessMonth().substring(0, 3).equalsIgnoreCase("MAR")) {
							obj.setMar(reportPayOutDTO.getTds());

						}
					}

				}

				for (Entry<String, ReportPayOutDTO> entry : map.entrySet()) {

					Row row = sheet.createRow(rowNum++);

					Cell cell0 = row.createCell(0);
					cell0.setCellValue(rowNum - 2);
					cell0.setCellStyle(rowCellStyle);

					row.createCell(1).setCellValue(entry.getValue().getEmployeeCode());
					row.createCell(2).setCellValue(entry.getValue().getEmpName());

					Cell cell3 = row.createCell(3);
					cell3.setCellValue(entry.getValue().getDateOfJoining());
					cell3.setCellStyle(dateCellStyle);

					row.createCell(4).setCellValue(entry.getValue().getPanNo());

					if (entry.getValue().getApr() != null)
						aprSum = aprSum.add(entry.getValue().getApr());
					if (entry.getValue().getMay() != null)
						maySum = maySum.add(entry.getValue().getMay());
					if (entry.getValue().getJun() != null)
						junSum = junSum.add(entry.getValue().getJun());
					if (entry.getValue().getJul() != null)
						julSum = julSum.add(entry.getValue().getJul());
					if (entry.getValue().getAug() != null)
						augSum = augSum.add(entry.getValue().getAug());
					if (entry.getValue().getSep() != null)
						sepSum = sepSum.add(entry.getValue().getSep());
					if (entry.getValue().getOct() != null)
						octSum = octSum.add(entry.getValue().getOct());
					if (entry.getValue().getNov() != null)
						novSum = novSum.add(entry.getValue().getNov());
					if (entry.getValue().getDec() != null)
						decSum = decSum.add(entry.getValue().getDec());
					if (entry.getValue().getJan() != null)
						janSum = janSum.add(entry.getValue().getJan());
					if (entry.getValue().getFeb() != null)
						febSum = febSum.add(entry.getValue().getFeb());
					if (entry.getValue().getMar() != null)
						marSum = marSum.add(entry.getValue().getMar());

					BigDecimal aprRowSum = BigDecimal.ZERO;

					if (entry.getValue().getApr() != null)
						aprRowSum = aprRowSum.add(entry.getValue().getApr());
					if (entry.getValue().getMay() != null)
						aprRowSum = aprRowSum.add(entry.getValue().getMay());
					if (entry.getValue().getJun() != null)
						aprRowSum = aprRowSum.add(entry.getValue().getJun());
					if (entry.getValue().getJul() != null)
						aprRowSum = aprRowSum.add(entry.getValue().getJul());
					if (entry.getValue().getAug() != null)
						aprRowSum = aprRowSum.add(entry.getValue().getAug());
					if (entry.getValue().getSep() != null)
						aprRowSum = aprRowSum.add(entry.getValue().getSep());
					if (entry.getValue().getOct() != null)
						aprRowSum = aprRowSum.add(entry.getValue().getOct());
					if (entry.getValue().getNov() != null)
						aprRowSum = aprRowSum.add(entry.getValue().getNov());
					if (entry.getValue().getDec() != null)
						aprRowSum = aprRowSum.add(entry.getValue().getDec());
					if (entry.getValue().getJan() != null)
						aprRowSum = aprRowSum.add(entry.getValue().getJan());
					if (entry.getValue().getFeb() != null)
						aprRowSum = aprRowSum.add(entry.getValue().getFeb());
					if (entry.getValue().getMar() != null)
						aprRowSum = aprRowSum.add(entry.getValue().getMar());

					totalIncomeTax = totalIncomeTax.add(aprRowSum);

					Cell cell5 = row.createCell(5);
					cell5.setCellValue(entry.getValue().getApr() != null ? entry.getValue().getApr().doubleValue()
							: new BigDecimal(0).doubleValue());
					cell5.setCellStyle(addZero);

					Cell cell6 = row.createCell(6);
					cell6.setCellValue(entry.getValue().getMay() != null ? entry.getValue().getMay().doubleValue()
							: new BigDecimal(0).doubleValue());
					cell6.setCellStyle(addZero);

					Cell cell7 = row.createCell(7);
					cell7.setCellValue(entry.getValue().getJun() != null ? entry.getValue().getJun().doubleValue()
							: new BigDecimal(0).doubleValue());
					cell7.setCellStyle(addZero);

					Cell cell8 = row.createCell(8);
					cell8.setCellValue(entry.getValue().getJul() != null ? entry.getValue().getJul().doubleValue()
							: new BigDecimal(0).doubleValue());
					cell8.setCellStyle(addZero);

					Cell cell9 = row.createCell(9);
					cell9.setCellValue(entry.getValue().getAug() != null ? entry.getValue().getAug().doubleValue()
							: new BigDecimal(0).doubleValue());
					cell9.setCellStyle(addZero);

					Cell cell10 = row.createCell(10);
					cell10.setCellValue(entry.getValue().getSep() != null ? entry.getValue().getSep().doubleValue()
							: new BigDecimal(0).doubleValue());
					cell10.setCellStyle(addZero);

					Cell cell11 = row.createCell(11);
					cell11.setCellValue(entry.getValue().getOct() != null ? entry.getValue().getOct().doubleValue()
							: new BigDecimal(0).doubleValue());
					cell11.setCellStyle(addZero);

					Cell cell12 = row.createCell(12);
					cell12.setCellValue(entry.getValue().getNov() != null ? entry.getValue().getNov().doubleValue()
							: new BigDecimal(0).doubleValue());
					cell12.setCellStyle(addZero);

					Cell cell13 = row.createCell(13);
					cell13.setCellValue(entry.getValue().getDec() != null ? entry.getValue().getDec().doubleValue()
							: new BigDecimal(0).doubleValue());
					cell13.setCellStyle(addZero);

					Cell cell14 = row.createCell(14);
					cell14.setCellValue(entry.getValue().getJan() != null ? entry.getValue().getJan().doubleValue()
							: new BigDecimal(0).doubleValue());
					cell14.setCellStyle(addZero);

					Cell cell15 = row.createCell(15);
					cell15.setCellValue(entry.getValue().getFeb() != null ? entry.getValue().getFeb().doubleValue()
							: new BigDecimal(0).doubleValue());
					cell15.setCellStyle(addZero);

					Cell cell16 = row.createCell(16);
					cell16.setCellValue(entry.getValue().getMar() != null ? entry.getValue().getMar().doubleValue()
							: new BigDecimal(0).doubleValue());
					cell16.setCellStyle(addZero);

					Cell cell17 = row.createCell(17);
					cell17.setCellValue(aprRowSum.doubleValue());
					cell17.setCellStyle(addZero);

				}

				Row row = sheet.createRow(rowNum);

				sheet.addMergedRegion(new CellRangeAddress(rowNum, rowNum, 0, 2));
				Cell cellT = row.createCell(0);
				cellT.setCellValue(" Total ");
				cellT.setCellStyle(headerCellStyle1t);

				Cell cellT5 = row.createCell(5);
				cellT5.setCellValue(aprSum.doubleValue());
				cellT5.setCellStyle(addZeroBold);

				Cell cellT6 = row.createCell(6);
				cellT6.setCellValue(maySum.doubleValue());
				cellT6.setCellStyle(addZeroBold);

				Cell cellT7 = row.createCell(7);
				cellT7.setCellValue(junSum.doubleValue());
				cellT7.setCellStyle(addZeroBold);

				Cell cellT8 = row.createCell(8);
				cellT8.setCellValue(julSum.doubleValue());
				cellT8.setCellStyle(addZeroBold);

				Cell cellT9 = row.createCell(9);
				cellT9.setCellValue(augSum.doubleValue());
				cellT9.setCellStyle(addZeroBold);

				Cell cellT10 = row.createCell(10);
				cellT10.setCellValue(sepSum.doubleValue());
				cellT10.setCellStyle(addZeroBold);

				Cell cellT11 = row.createCell(11);
				cellT11.setCellValue(octSum.doubleValue());
				cellT11.setCellStyle(addZeroBold);

				Cell cellT12 = row.createCell(12);
				cellT12.setCellValue(novSum.doubleValue());
				cellT12.setCellStyle(addZeroBold);

				Cell cellT13 = row.createCell(13);
				cellT13.setCellValue(decSum.doubleValue());
				cellT13.setCellStyle(addZeroBold);

				Cell cellT14 = row.createCell(14);
				cellT14.setCellValue(janSum.doubleValue());
				cellT14.setCellStyle(addZeroBold);

				Cell cellT15 = row.createCell(15);
				cellT15.setCellValue(febSum.doubleValue());
				cellT15.setCellStyle(addZeroBold);

				Cell cellT16 = row.createCell(16);
				cellT16.setCellValue(marSum.doubleValue());
				cellT16.setCellStyle(addZeroBold);

				Cell cellT17 = row.createCell(17);
				cellT17.setCellValue(totalIncomeTax.doubleValue());
				cellT17.setCellStyle(addZeroBold);

			} else {

				for (ReportPayOutDTO reportPayOutDTO : reportPayOutDTOList) {

					Row row = sheet.createRow(rowNum++);

					Cell cell0 = row.createCell(0);
					cell0.setCellValue(rowNum - 2);
					cell0.setCellStyle(rowCellStyle);

					row.createCell(1).setCellValue(reportPayOutDTO.getEmployeeCode());
					row.createCell(2).setCellValue(reportPayOutDTO.getEmpName());

					Cell cell3 = row.createCell(3);
					cell3.setCellValue(reportPayOutDTO.getDateOfJoining());
					cell3.setCellStyle(dateCellStyle);

					row.createCell(4).setCellValue(reportPayOutDTO.getPanNo());

					if (reportPayOutDTO.getMonthalyGross() != null)
						totalGross = totalGross.add(reportPayOutDTO.getMonthalyGross());

					Cell cell5 = row.createCell(5);
					cell5.setCellValue(reportPayOutDTO.getMonthalyGross() != null
							? reportPayOutDTO.getMonthalyGross().doubleValue()
							: new BigDecimal(0).doubleValue());
					cell5.setCellStyle(addZero);

					if (reportPayOutDTO.getTds() != null)
						totalTds = totalTds.add(reportPayOutDTO.getTds());

					Cell cell6 = row.createCell(6);
					cell6.setCellValue(reportPayOutDTO.getTds() != null ? reportPayOutDTO.getTds().doubleValue()
							: new BigDecimal(0).doubleValue());
					cell6.setCellStyle(addZero);

					// BigDecimal cessPercentage = new BigDecimal("0.04");
					// BigDecimal cess= BigDecimal.ONE;
					// cess=reportPayOutDTO.getTds() != null ?
					// cessPercentage.multiply(reportPayOutDTO.getTds()).setScale(0,
					// RoundingMode.CEILING):new BigDecimal(0);
					// Cell cell8 = row.createCell(7);
					// cell8.setCellValue(cess.doubleValue());
					// cell8.setCellStyle(addZero);

					Cell cell8 = row.createCell(7);
					cell8.setCellValue(0);
					cell8.setCellStyle(addZero);

					BigDecimal cess = BigDecimal.ZERO;

					if (reportPayOutDTO.getTds() != null)
						totalCess = totalCess.add(cess);

					BigDecimal totalTax = BigDecimal.ZERO;
					totalTax = reportPayOutDTO.getTds() != null ? (cess.add(reportPayOutDTO.getTds()))
							: new BigDecimal(0);

					if (reportPayOutDTO.getTds() != null)
						totalIncomeTax = totalIncomeTax.add(totalTax);

					Cell cell9 = row.createCell(8);
					cell9.setCellValue(totalTax.doubleValue());
					cell9.setCellStyle(addZero);

				}

				Row row = sheet.createRow(rowNum);

				sheet.addMergedRegion(new CellRangeAddress(rowNum, rowNum, 0, 2));
				Cell cellT = row.createCell(0);
				cellT.setCellValue(" Total ");
				cellT.setCellStyle(headerCellStyle1t);

				Cell cellT5 = row.createCell(5);
				cellT5.setCellValue(totalGross.doubleValue());
				cellT5.setCellStyle(addZeroBold);

				Cell cellT6 = row.createCell(6);
				cellT6.setCellValue(totalTds.doubleValue());
				cellT6.setCellStyle(addZeroBold);

				Cell cellT8 = row.createCell(7);
				cellT8.setCellValue(totalCess.doubleValue());
				cellT8.setCellStyle(addZeroBold);

				Cell cellT9 = row.createCell(8);
				cellT9.setCellValue(totalIncomeTax.doubleValue());
				cellT9.setCellStyle(addZeroBold);

			}

		} else {
			Row row = sheet.createRow(2);

			sheet.addMergedRegion(new CellRangeAddress(2, 5, 0, columns.length - 1));
			Cell cell = row.createCell(0);
			cell.setCellValue("--  Data Not Available -- ");
			cell.setCellStyle(headerCellStyle);

		}

		// Resize all columns to fit the content size
		for (int i = 0; i < columns.length; i++) {
			sheet.autoSizeColumn(i);
		}

		return workbook;
	}

	public static Workbook rentPaidAndLandLordReportWriter(List<TdsHouseRentInfoDTO> rentPaidDetailsList,
			String[] columns, String financialYear) {

		Workbook workbook = new XSSFWorkbook();

		CreationHelper createHelper = workbook.getCreationHelper();

		// Create a Sheet
		Sheet sheet = workbook.createSheet("Rent Paid & Landlord Details");

		// Create a Font for styling header cells
		Font headerFont = workbook.createFont();
		headerFont.setBold(true);
		headerFont.setFontHeightInPoints((short) 12);
		headerFont.setColor(IndexedColors.BLACK.getIndex());

		Font rowFont = workbook.createFont();
		rowFont.setFontHeightInPoints((short) 11);
		rowFont.setColor(IndexedColors.BLACK.getIndex());

		Font rowBoldFont = workbook.createFont();
		rowBoldFont.setBold(true);
		rowBoldFont.setFontHeightInPoints((short) 11);
		rowBoldFont.setColor(IndexedColors.BLACK.getIndex());

		// Create a CellStyle with the font
		CellStyle headerCellStyle = workbook.createCellStyle();
		headerCellStyle.setFont(headerFont);
		headerCellStyle.setAlignment(HorizontalAlignment.CENTER);
		headerCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
		headerCellStyle.setFillBackgroundColor(IndexedColors.LIGHT_CORNFLOWER_BLUE.getIndex());
		headerCellStyle.setFillPattern(FillPatternType.FINE_DOTS);

		CellStyle headerCellStyle1t = workbook.createCellStyle();
		headerCellStyle1t.setFont(rowBoldFont);
		headerCellStyle1t.setDataFormat(createHelper.createDataFormat().getFormat("0,000.00"));
		headerCellStyle1t.setAlignment(HorizontalAlignment.RIGHT);
		headerCellStyle1t.setVerticalAlignment(VerticalAlignment.CENTER);

		CellStyle headerCellStyle1 = workbook.createCellStyle();
		headerCellStyle1.setFont(headerFont);
		headerCellStyle1.setAlignment(HorizontalAlignment.LEFT);
		headerCellStyle1.setVerticalAlignment(VerticalAlignment.CENTER);

		CellStyle rowHeadCellStyle = workbook.createCellStyle();
		rowHeadCellStyle.setFont(rowFont);
		rowHeadCellStyle.setAlignment(HorizontalAlignment.CENTER);
		rowHeadCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);

		CellStyle rowCellStyle = workbook.createCellStyle();
		rowCellStyle.setFont(rowFont);
		rowCellStyle.setAlignment(HorizontalAlignment.CENTER);
		rowCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);

		CellStyle addZero = workbook.createCellStyle();
		addZero.setDataFormat(createHelper.createDataFormat().getFormat("0,000.00"));
		addZero.setAlignment(HorizontalAlignment.RIGHT);
		addZero.setVerticalAlignment(VerticalAlignment.CENTER);

		int col = columns.length - 1;

		Row row0 = sheet.createRow(0);
		sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, col));
		Cell cellCom = row0.createCell(0);
		cellCom.setCellValue("Rent Paid & Landlord Details");
		cellCom.setCellStyle(headerCellStyle1);

		// Create a Row
		Row headerRow = sheet.createRow(1);

		// Creating cells
		for (int i = 0; i < columns.length; i++) {
			Cell cell = headerRow.createCell(i);
			cell.setCellValue(columns[i]);
			cell.setCellStyle(headerCellStyle);

		}

		BigDecimal totalRent = BigDecimal.ZERO;

		int rowNum = 2;

		if (rentPaidDetailsList.size() > 0) {

			for (TdsHouseRentInfoDTO tdsHouseRentInfoDTO : rentPaidDetailsList) {

				Row row = sheet.createRow(rowNum++);

				row.createCell(0).setCellValue(tdsHouseRentInfoDTO.getEmpCode());
				row.createCell(1).setCellValue(tdsHouseRentInfoDTO.getEmpName());

				Cell cell = row.createCell(2);
				cell.setCellValue(DateUtils.getDateStringWithYYYYMMDD(tdsHouseRentInfoDTO.getFromDate()));

				Cell cell2 = row.createCell(3);
				cell2.setCellValue(DateUtils.getDateStringWithYYYYMMDD(tdsHouseRentInfoDTO.getToDate()));

				row.createCell(4).setCellValue(tdsHouseRentInfoDTO.getAddress());
				row.createCell(5).setCellValue(tdsHouseRentInfoDTO.getCity());

				Cell cell3 = row.createCell(6);
				cell3.setCellValue(tdsHouseRentInfoDTO.getTotalRental().doubleValue());
				cell3.setCellStyle(addZero);

				if (tdsHouseRentInfoDTO.getTotalRental() != null) {
					totalRent = totalRent.add(tdsHouseRentInfoDTO.getTotalRental());
				}

				row.createCell(7).setCellValue(tdsHouseRentInfoDTO.getLandlordName());
				row.createCell(8).setCellValue(tdsHouseRentInfoDTO.getAddressOfLandlord());
				row.createCell(9).setCellValue(tdsHouseRentInfoDTO.getLandlordPan());

			}

			Row row = sheet.createRow(rowNum);
			// sheet.addMergedRegion(new CellRangeAddress(rowNum, rowNum, 6, 6));
			Cell cellT = row.createCell(6);
			cellT.setCellValue(totalRent.doubleValue());
			cellT.setCellStyle(headerCellStyle1t);

		} else {

			Row row = sheet.createRow(2);
			sheet.addMergedRegion(new CellRangeAddress(3, 6, 0, columns.length - 1));

			Cell cell = row.createCell(0);
			cell.setCellValue(" ----Data is not available---- ");
			cell.setCellStyle(headerCellStyle1);

		}

		// Resize all columns to fit the content size
		for (int i = 0; i < columns.length; i++) {
			sheet.autoSizeColumn(i);
		}

		return workbook;
	}

	public static Workbook labourWelfareFundReport(List<ReportPayOutDTO> reportPayOutDTO, String[] columns,
			String processMonth, String wise) {

		Workbook workbook = new XSSFWorkbook(); // new HSSFWorkbook() for generating `.xls` file

		CreationHelper createHelper = workbook.getCreationHelper();

		Sheet sheet = null;
		if (wise.equals("EW")) {
			sheet = workbook.createSheet("LWF Statement Monthly");

		} else if (wise.equals("SW")) {
			sheet = workbook.createSheet("LWF Statement Statewise");

		}

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

		CellStyle headerCellStyle1 = workbook.createCellStyle();
		headerCellStyle1.setFont(headerFont);
		headerCellStyle1.setAlignment(HorizontalAlignment.RIGHT);
		headerCellStyle1.setVerticalAlignment(VerticalAlignment.CENTER);
		headerCellStyle1.setFillBackgroundColor(IndexedColors.LIGHT_CORNFLOWER_BLUE.getIndex());
		headerCellStyle1.setFillPattern(FillPatternType.FINE_DOTS);

		CellStyle headerCellStyle11 = workbook.createCellStyle();
		headerCellStyle11.setFont(headerFont);
		headerCellStyle11.setAlignment(HorizontalAlignment.LEFT);
		headerCellStyle11.setVerticalAlignment(VerticalAlignment.CENTER);
		CellStyle headerCellStyle111 = workbook.createCellStyle();
		headerCellStyle111.setFont(headerFont);
		headerCellStyle111.setAlignment(HorizontalAlignment.LEFT);
		headerCellStyle111.setVerticalAlignment(VerticalAlignment.CENTER);

		CellStyle headerCellStyle12 = workbook.createCellStyle();
		headerCellStyle12.setFont(headerFont);
		headerCellStyle12.setAlignment(HorizontalAlignment.CENTER);
		headerCellStyle12.setVerticalAlignment(VerticalAlignment.CENTER);

		CellStyle headerCellStyleTotal = workbook.createCellStyle();
		headerCellStyleTotal.setFont(headerFont);
		headerCellStyleTotal.setAlignment(HorizontalAlignment.RIGHT);
		headerCellStyleTotal.setVerticalAlignment(VerticalAlignment.CENTER);

		CellStyle addZero = workbook.createCellStyle();
		addZero.setDataFormat(createHelper.createDataFormat().getFormat("0.00"));
		addZero.setAlignment(HorizontalAlignment.CENTER);
		addZero.setVerticalAlignment(VerticalAlignment.CENTER);

		CellStyle addZeroBold = workbook.createCellStyle();
		addZeroBold.setDataFormat(createHelper.createDataFormat().getFormat("0.00"));
		addZeroBold.setFont(headerFont);
		addZeroBold.setAlignment(HorizontalAlignment.CENTER);
		addZeroBold.setVerticalAlignment(VerticalAlignment.CENTER);

		Row row0 = sheet.createRow(0);
		sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 6));
		Cell cellCom = row0.createCell(0);
		if (wise.equals("EW")) {
			cellCom.setCellValue("Labour Welfare Fund Statement for " + processMonth);
		}

		else if (wise.equals("SW")) {
			cellCom.setCellValue("Labour Welfare Fund Statewise Summary for " + processMonth);
		}

		cellCom.setCellStyle(headerCellStyle111);

		Row headerRow = sheet.createRow(1);
		// Creating cells
		for (int i = 0; i < columns.length; i++) {
			Cell cell = headerRow.createCell(i);
			cell.setCellValue(columns[i]);
			cell.setCellStyle(headerCellStyle);

		}

		int rowNum = 2;
		BigDecimal sum = BigDecimal.ZERO;
		BigDecimal totalLwf = BigDecimal.ZERO;
		BigDecimal totalLwfEmployerContr = BigDecimal.ZERO;

		if (reportPayOutDTO.size() > 0) {

			if (wise.equals("EW")) {

				int i = 1;
				for (ReportPayOutDTO reportPayOut : reportPayOutDTO) {
					Row row = sheet.createRow(rowNum++);
					row.createCell(0).setCellValue(i);

					i++;

					row.createCell(1).setCellValue(reportPayOut.getEmployeeCode());
					if (reportPayOut.getEmpName() != null)
						row.createCell(2).setCellValue(reportPayOut.getEmpName());
					row.createCell(3).setCellValue(reportPayOut.getJobLocation());
					row.createCell(4).setCellValue(reportPayOut.getStateName());

					sum = sum.add(reportPayOut.getGrossSalary());
					Cell cell4 = row.createCell(5);
					cell4.setCellStyle(addZero);
					cell4.setCellValue(reportPayOut.getGrossSalary().doubleValue());

					if (reportPayOut.getLwfEmployeeAmount() != null)
						totalLwf = reportPayOut.getLwfEmployeeAmount().add(totalLwf);

					Cell cell5 = row.createCell(6);
					cell5.setCellStyle(addZero);
					if (reportPayOut.getLwfEmployeeAmount() != null) {
						cell5.setCellValue(reportPayOut.getLwfEmployeeAmount().doubleValue());
					} else {
						cell5.setCellValue(0.00);
					}

					if (reportPayOut.getLwfEmployerAmount() != null)
						totalLwfEmployerContr = reportPayOut.getLwfEmployerAmount().add(totalLwfEmployerContr);

					Cell cell6 = row.createCell(7);
					cell6.setCellStyle(addZero);
					if (reportPayOut.getLwfEmployerAmount() != null) {
						cell6.setCellValue(reportPayOut.getLwfEmployerAmount().doubleValue());
					} else {
						cell6.setCellValue(0.00);
					}

				}

				headerCellStyle.setVerticalAlignment(VerticalAlignment.JUSTIFY);

				Row rowNew = sheet.createRow(rowNum);
				Cell cell5 = rowNew.createCell(5);
				cell5.setCellValue(sum.doubleValue());
				cell5.setCellStyle(addZeroBold);

				Cell cell6 = rowNew.createCell(6);
				cell6.setCellValue(totalLwf.doubleValue());
				cell6.setCellStyle(addZeroBold);

				Cell cell7 = rowNew.createCell(7);
				cell7.setCellValue(totalLwfEmployerContr.doubleValue());
				cell7.setCellStyle(addZeroBold);

			}

			else if (wise.equals("SW")) {

				for (ReportPayOutDTO reportPayOut : reportPayOutDTO) {
					Row row = sheet.createRow(rowNum++);

					row.createCell(0).setCellValue(reportPayOut.getStateName());

					Cell cell1 = row.createCell(1);
					cell1.setCellStyle(headerCellStyleTotal);
					cell1.setCellValue(reportPayOut.getTotalEmployee());

					sum = sum.add(reportPayOut.getTotalEarning());
					Cell cell4 = row.createCell(2);
					cell4.setCellStyle(addZero);
					cell4.setCellValue(reportPayOut.getTotalEarning().doubleValue());

					if (reportPayOut.getLwfEmployeeAmount() != null)
						totalLwf = reportPayOut.getLwfEmployeeAmount().add(totalLwf);

					Cell cell5 = row.createCell(3);
					cell5.setCellStyle(addZero);
					if (reportPayOut.getLwfEmployeeAmount() != null) {
						cell5.setCellValue(reportPayOut.getLwfEmployeeAmount().doubleValue());
					} else {
						cell5.setCellValue(0.00);
					}

					if (reportPayOut.getLwfEmployerAmount() != null)
						totalLwfEmployerContr = reportPayOut.getLwfEmployerAmount().add(totalLwfEmployerContr);

					Cell cell6 = row.createCell(4);
					cell6.setCellStyle(addZero);
					if (reportPayOut.getLwfEmployerAmount() != null) {
						cell6.setCellValue(reportPayOut.getLwfEmployerAmount().doubleValue());
					} else {
						cell6.setCellValue(0.00);
					}

				}

				headerCellStyle.setVerticalAlignment(VerticalAlignment.JUSTIFY);

				Row rowNew = sheet.createRow(rowNum);
				Cell cell5 = rowNew.createCell(2);
				cell5.setCellValue(sum.doubleValue());
				cell5.setCellStyle(addZeroBold);

				Cell cell6 = rowNew.createCell(3);
				cell6.setCellValue(totalLwf.doubleValue());
				cell6.setCellStyle(addZeroBold);

				Cell cell7 = rowNew.createCell(4);
				cell7.setCellValue(totalLwfEmployerContr.doubleValue());
				cell7.setCellStyle(addZeroBold);
			}

		}

		else {

			Row row = sheet.createRow(2);
			sheet.addMergedRegion(new CellRangeAddress(2, 6, 0, columns.length - 1));

			Cell cell = row.createCell(0);
			cell.setCellValue(" ----Data is not available---- ");
			cell.setCellStyle(headerCellStyle12);

		}

		// Resize all columns to fit the content size
		for (int i = 0; i < columns.length; i++) {
			sheet.autoSizeColumn(i);
		}

		return workbook;

	}

	public static Workbook annualPFContributionReport(Map<String, List<ReportPayOutDTO>> reportPayOutDtoMap,
			List<ReportPayOutDTO> annualSummaryList, String[] columns, Company company, String[] summaryColumns,
			String financialYear, List<Object[]> totalSum, List<Object[]> employeeCount, List<Object[]> totalMonthly) {

		Workbook workbook = new XSSFWorkbook();
		CreationHelper createHelper = workbook.getCreationHelper();

		Sheet summarySheet = workbook.createSheet("Annual PF contribution statement");

		for (ReportPayOutDTO annualSummary : annualSummaryList) {

			for (Map.Entry<String, List<ReportPayOutDTO>> entry : reportPayOutDtoMap.entrySet()) {

				String processMonthAnnual = entry.getKey();
				List<ReportPayOutDTO> reportPayOutDTOList = entry.getValue();
				if (annualSummary.getProcessMonth().equals(processMonthAnnual)) {
					// new HSSFWorkbook() for generating `.xls` file
					CreationHelper createHelperMonthaly = workbook.getCreationHelper();

					Sheet sheet = workbook.createSheet(processMonthAnnual);

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

					CellStyle headerCellStyle1 = workbook.createCellStyle();
					headerCellStyle1.setFont(headerFont);
					headerCellStyle1.setAlignment(HorizontalAlignment.RIGHT);
					headerCellStyle1.setVerticalAlignment(VerticalAlignment.CENTER);
					headerCellStyle1.setFillBackgroundColor(IndexedColors.LIGHT_CORNFLOWER_BLUE.getIndex());
					headerCellStyle1.setFillPattern(FillPatternType.FINE_DOTS);

					CellStyle headerCellStyle11 = workbook.createCellStyle();
					headerCellStyle11.setFont(headerFont);
					headerCellStyle11.setAlignment(HorizontalAlignment.LEFT);
					headerCellStyle11.setVerticalAlignment(VerticalAlignment.CENTER);
					CellStyle headerCellStyle111 = workbook.createCellStyle();
					headerCellStyle111.setFont(headerFont);
					headerCellStyle111.setAlignment(HorizontalAlignment.CENTER);
					headerCellStyle111.setVerticalAlignment(VerticalAlignment.CENTER);

					CellStyle headerCellStyle112 = workbook.createCellStyle();
					headerCellStyle112.setAlignment(HorizontalAlignment.LEFT);
					headerCellStyle112.setVerticalAlignment(VerticalAlignment.CENTER);

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

					CellStyle addZero1 = workbook.createCellStyle();
					addZero1.setDataFormat(createHelper.createDataFormat().getFormat("0.00"));
					addZero1.setAlignment(HorizontalAlignment.LEFT);
					addZero1.setVerticalAlignment(VerticalAlignment.CENTER);

					Row row0 = sheet.createRow(0);
					sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 10));
					Cell cellCom = row0.createCell(0);
					cellCom.setCellValue("EPF-ECR -" + processMonthAnnual);
					cellCom.setCellStyle(headerCellStyle111);

					Row row1 = sheet.createRow(1);
					sheet.addMergedRegion(new CellRangeAddress(1, 1, 0, 10));
					Cell cellAdd = row1.createCell(0);
					cellAdd.setCellValue("PF Code -" + company.getEpfNo());
					cellAdd.setCellStyle(headerCellStyleCode);

					for (Object[] epfObj : employeeCount) {

						String processMonth = epfObj[3] != null ? (String) epfObj[3] : null;

						if (annualSummary.getProcessMonth().equals(processMonth)) {
							Integer totalNoOfEmployee = epfObj[0] != null ? Integer.parseInt(epfObj[0].toString())
									: new Integer(0);

							Row row3 = sheet.createRow(2);
							sheet.addMergedRegion(new CellRangeAddress(2, 2, 0, 3));
							row3.createCell(0).setCellValue("Total Number of Employees ");
							sheet.addMergedRegion(new CellRangeAddress(2, 2, 4, 10));

							Cell totalEmp = row3.createCell(4);
							totalEmp.setCellStyle(headerCellStyle112);
							totalEmp.setCellValue(totalNoOfEmployee);

							// row3.createCell(4).setCellValue(totalNoOfEmployee.doubleValue());

							Integer excludedEmployee = epfObj[1] != null ? Integer.parseInt(epfObj[1].toString())
									: new Integer(0);
							Row row4 = sheet.createRow(3);
							sheet.addMergedRegion(new CellRangeAddress(3, 3, 0, 3));
							row4.createCell(0).setCellValue("Total Number of Excluded Employees");
							sheet.addMergedRegion(new CellRangeAddress(3, 3, 4, 10));

							Cell cellemp = row4.createCell(4);
							cellemp.setCellValue(excludedEmployee);
							cellemp.setCellStyle(headerCellStyle112);

							Row row5 = sheet.createRow(4);
							sheet.addMergedRegion(new CellRangeAddress(4, 4, 0, 3));

							row5.createCell(0).setCellValue("Gross Wages of Excluded Employees ");
							sheet.addMergedRegion(new CellRangeAddress(4, 4, 4, 10));

							BigDecimal grossSalary = epfObj[2] != null ? (new BigDecimal(epfObj[2].toString()))
									: new BigDecimal(0.00);
							Cell cellSum = row5.createCell(4);

							if (grossSalary.compareTo(new BigDecimal(0.00)) > 0) {
								cellSum.setCellValue(grossSalary.doubleValue());
								cellSum.setCellStyle(addZero1);
							} else {
								cellSum.setCellValue(new BigDecimal(0.00).doubleValue());
								cellSum.setCellStyle(addZero1);
							}
						}

					}

					CellStyle addZero = workbook.createCellStyle();
					addZero.setDataFormat(createHelper.createDataFormat().getFormat("0.00"));
					addZero.setAlignment(HorizontalAlignment.RIGHT);
					addZero.setVerticalAlignment(VerticalAlignment.CENTER);

					CellStyle addZeroBold = workbook.createCellStyle();
					addZeroBold.setDataFormat(createHelper.createDataFormat().getFormat("0.00"));
					addZeroBold.setFont(headerFont);
					addZeroBold.setAlignment(HorizontalAlignment.RIGHT);
					addZeroBold.setVerticalAlignment(VerticalAlignment.CENTER);

					CellStyle addZeroBold1 = workbook.createCellStyle();
					addZeroBold1.setDataFormat(createHelper.createDataFormat().getFormat("0.00"));
					addZeroBold1.setFont(headerFont);
					addZeroBold1.setAlignment(HorizontalAlignment.LEFT);
					addZeroBold1.setVerticalAlignment(VerticalAlignment.CENTER);

					Row headerRow = sheet.createRow(5);
					// Creating cells
					for (int i = 0; i < columns.length; i++) {
						Cell cell = headerRow.createCell(i);
						cell.setCellValue(columns[i]);
						cell.setCellStyle(headerCellStyle);

					}

					CellStyle dateCellStyle = workbook.createCellStyle();
					dateCellStyle.setDataFormat(createHelper.createDataFormat().getFormat("dd-MM-yyyy"));

					int rowNum = 6;
					BigDecimal sum = BigDecimal.ZERO;
					BigDecimal totalEarning = BigDecimal.ZERO;
					BigDecimal basicEarning = BigDecimal.ZERO;
					BigDecimal pensionEarningSalary = BigDecimal.ZERO;
					BigDecimal ProvidentFundEmployee = BigDecimal.ZERO;
					BigDecimal providentFundEmployeer = BigDecimal.ZERO;
					BigDecimal providentFundEmployerPension = BigDecimal.ZERO;
					BigDecimal refund = BigDecimal.ZERO;
					BigDecimal absence = BigDecimal.ZERO;

					BigDecimal epfWages = new BigDecimal(0);
					BigDecimal epsWages = new BigDecimal(0);
					BigDecimal edliWages = new BigDecimal(0);
					BigDecimal epfContri = new BigDecimal(0);
					BigDecimal epsContri = new BigDecimal(0);
					BigDecimal epfEps = new BigDecimal(0);
					BigDecimal grossWages = new BigDecimal(0);

					if (reportPayOutDTOList != null) {

						for (ReportPayOutDTO reportPayOut : reportPayOutDTOList) {

							Row row = sheet.createRow(rowNum++);

							if (reportPayOut.getUnNo() != null && reportPayOut.getUnNo().trim() != "")
								row.createCell(0).setCellValue(reportPayOut.getUnNo());
							else
								row.createCell(0).setCellValue("Under Process");

							row.createCell(1).setCellValue(reportPayOut.getName());

							grossWages = reportPayOut.getTotalEarning() != null ? reportPayOut.getTotalEarning()
									: BigDecimal.ZERO;
							Cell celltotalearning = row.createCell(2);
							celltotalearning.setCellValue(grossWages.doubleValue());
							celltotalearning.setCellStyle(addZero);

							// BigDecimal totalGrossWages =totalGrossWages+ reportPayOut.getTotalEarning();
							//
							// String totalGrossWages=
							// convertBigdecimalToString(reportPayOut.getTotalEarning());

							String str = FormateUtil
									.ExcelDataPattern(convertBigdecimalToString(reportPayOut.getBasicEarning()));

							// EarnedStdEpfWagesAmount=reportPayOut.getBasicEarning()
							sum = (reportPayOut.getBasicEarning() != null ? reportPayOut.getBasicEarning()
									: BigDecimal.ZERO).add(sum);

							CellStyle cellStyle = workbook.createCellStyle();
							cellStyle.setDataFormat(workbook.getCreationHelper().createDataFormat().getFormat(str));

							epfWages = reportPayOut.getBasicEarning() != null ? reportPayOut.getBasicEarning()
									: BigDecimal.ZERO;
							Cell cellEarning = row.createCell(3);
							cellEarning.setCellValue(epfWages.doubleValue());
							cellEarning.setCellStyle(addZero);

							epsWages = reportPayOut.getPensionEarningSalary() != null
									? reportPayOut.getPensionEarningSalary()
									: BigDecimal.ZERO;
							Cell cellpension = row.createCell(4);
							cellpension.setCellValue(epsWages.doubleValue());
							cellpension.setCellStyle(addZero);

							edliWages = reportPayOut.getPensionEarningSalary() != null
									? reportPayOut.getPensionEarningSalary()
									: BigDecimal.ZERO;
							Cell cellpensionearning = row.createCell(5);
							cellpensionearning.setCellValue(edliWages.doubleValue());
							cellpensionearning.setCellStyle(addZero);

							epfContri = reportPayOut.getProvidentFundEmployee() != null
									? reportPayOut.getProvidentFundEmployee()
									: BigDecimal.ZERO;
							Cell cellProvidentFund = row.createCell(6);
							cellProvidentFund.setCellValue(epfContri.doubleValue());
							cellProvidentFund.setCellStyle(addZero);

							epsContri = reportPayOut.getProvidentFundEmployerPension() != null
									? reportPayOut.getProvidentFundEmployerPension()
									: BigDecimal.ZERO;
							Cell cellProvident = row.createCell(7);
							cellProvident.setCellValue(epsContri.doubleValue());
							cellProvident.setCellStyle(addZero);

							epfEps = reportPayOut.getProvidentFundEmployer() != null
									? reportPayOut.getProvidentFundEmployer()
									: BigDecimal.ZERO;
							Cell cellProfund = row.createCell(8);
							cellProfund.setCellValue(epfEps.doubleValue());
							cellProfund.setCellStyle(addZero);

							row.createCell(9).setCellValue(convertBigdecimalToDouble(reportPayOut.getAbsense()));

							Cell cell = row.createCell(10);
							// cell.setCellValue( "0.00");
							cell.setCellValue(0.00);
							cell.setCellStyle(addZero1);
						}

					}
					// System.out.println("hiii");

					headerCellStyle.setVerticalAlignment(VerticalAlignment.JUSTIFY);

					Row rowNew = sheet.createRow(rowNum);
					sheet.addMergedRegion(new CellRangeAddress(rowNum, rowNum, 0, 1));

					Cell cell3 = rowNew.createCell(0);
					cell3.setCellValue("Total ");
					cell3.setCellStyle(cellStyleCentre);
					// System.out.println("Sum============================ " + sum);
					for (Object[] total : totalMonthly) {

						totalEarning = total[0] != null ? (new BigDecimal(total[0].toString())) : null;
						basicEarning = total[1] != null ? (new BigDecimal(total[1].toString())) : null;
						pensionEarningSalary = total[2] != null ? (new BigDecimal(total[2].toString())) : null;
						ProvidentFundEmployee = total[3] != null ? (new BigDecimal(total[3].toString())) : null;
						providentFundEmployeer = total[5] != null ? (new BigDecimal(total[5].toString())) : null;
						providentFundEmployerPension = total[4] != null ? (new BigDecimal(total[4].toString())) : null;
						absence = total[6] != null ? (new BigDecimal(total[6].toString())) : null;
						String processMonth = total[7] != null ? (String) total[7] : null;

						if (annualSummary.getProcessMonth().equals(processMonth)) {
							if (totalEarning != null) {
								Cell cell2 = rowNew.createCell(2);
								cell2.setCellValue(totalEarning.doubleValue());
								cell2.setCellStyle(addZeroBold);
							}

							if (basicEarning != null) {
								Cell cell31 = rowNew.createCell(3);
								cell31.setCellValue(basicEarning.doubleValue());
								cell31.setCellStyle(addZeroBold);
							}

							if (pensionEarningSalary != null) {
								Cell cell32 = rowNew.createCell(4);
								cell32.setCellValue(pensionEarningSalary.doubleValue());
								cell32.setCellStyle(addZeroBold);
							}

							if (pensionEarningSalary != null) {
								Cell cell33 = rowNew.createCell(5);
								cell33.setCellValue(pensionEarningSalary.doubleValue());
								cell33.setCellStyle(addZeroBold);
							}

							if (ProvidentFundEmployee != null) {
								Cell cell34 = rowNew.createCell(6);
								cell34.setCellValue(ProvidentFundEmployee.doubleValue());
								cell34.setCellStyle(addZeroBold);
							}

							if (providentFundEmployeer != null) {
								Cell cell37 = rowNew.createCell(7);
								cell37.setCellValue(providentFundEmployeer.doubleValue());
								cell37.setCellStyle(addZeroBold);
							}

							if (providentFundEmployerPension != null) {
								Cell cell35 = rowNew.createCell(8);
								cell35.setCellValue(providentFundEmployerPension.doubleValue());
								cell35.setCellStyle(addZeroBold);
							}
							if (absence != null) {
								Cell cell37 = rowNew.createCell(9);
								cell37.setCellValue(absence.doubleValue());
								cell37.setCellStyle(addZeroBold);
							}
							// sum
							Cell cell36 = rowNew.createCell(10);
							cell36.setCellValue(0.00);
							cell36.setCellStyle(addZeroBold);
						}

					}

					// Resize all columns to fit the content size
					for (int i = 0; i < columns.length; i++) {
						sheet.autoSizeColumn(i);
					}

				}

			}
		}

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

		CellStyle headerCellStyleGreen = workbook.createCellStyle();
		headerCellStyleGreen.setFont(headerFont);
		headerCellStyleGreen.setAlignment(HorizontalAlignment.CENTER);
		headerCellStyleGreen.setVerticalAlignment(VerticalAlignment.CENTER);
		headerCellStyleGreen.setFillBackgroundColor(IndexedColors.LIGHT_GREEN.getIndex());
		headerCellStyleGreen.setFillPattern(FillPatternType.FINE_DOTS);

		CellStyle headerCellStyleRed = workbook.createCellStyle();
		headerCellStyleRed.setFont(headerFont);
		headerCellStyleRed.setAlignment(HorizontalAlignment.CENTER);
		headerCellStyleRed.setVerticalAlignment(VerticalAlignment.CENTER);
		headerCellStyleRed.setFillBackgroundColor(IndexedColors.LIGHT_ORANGE.getIndex());
		headerCellStyleRed.setFillPattern(FillPatternType.FINE_DOTS);

		CellStyle headerCellStyle111 = workbook.createCellStyle();
		headerCellStyle111.setFont(headerFont);
		headerCellStyle111.setAlignment(HorizontalAlignment.LEFT);
		headerCellStyle111.setVerticalAlignment(VerticalAlignment.CENTER);

		CellStyle headerCellStyle101 = workbook.createCellStyle();
		headerCellStyle101.setFont(headerFont);
		headerCellStyle101.setAlignment(HorizontalAlignment.CENTER);
		headerCellStyle101.setVerticalAlignment(VerticalAlignment.CENTER);

		CellStyle addZero = workbook.createCellStyle();
		addZero.setDataFormat(createHelper.createDataFormat().getFormat("0.00"));
		addZero.setAlignment(HorizontalAlignment.RIGHT);
		addZero.setVerticalAlignment(VerticalAlignment.CENTER);

		CellStyle addZeroBold = workbook.createCellStyle();
		addZeroBold.setDataFormat(createHelper.createDataFormat().getFormat("0.00"));
		addZeroBold.setFont(headerFont);
		addZeroBold.setAlignment(HorizontalAlignment.RIGHT);
		addZeroBold.setVerticalAlignment(VerticalAlignment.CENTER);

		CellStyle addZeroBold1 = workbook.createCellStyle();
		addZeroBold1.setDataFormat(createHelper.createDataFormat().getFormat("0.00"));
		addZeroBold1.setFont(headerFont);
		addZeroBold1.setAlignment(HorizontalAlignment.LEFT);
		addZeroBold1.setVerticalAlignment(VerticalAlignment.CENTER);

		CellStyle cellStyleCentre = workbook.createCellStyle();
		cellStyleCentre.setAlignment(HorizontalAlignment.CENTER);
		cellStyleCentre.setVerticalAlignment(VerticalAlignment.CENTER);

		Row row0 = summarySheet.createRow(0);
		summarySheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 13));
		Cell cellCom = row0.createCell(0);
		cellCom.setCellValue(
				"For Computronics Systems India Pvt. Ltd. PF Contribution Statement for FY -  " + financialYear);
		cellCom.setCellStyle(headerCellStyle101);

		// Row row1 = summarySheet.createRow(1);

		Row headerRow = summarySheet.createRow(1);

//		summarySheet.addMergedRegion(new CellRangeAddress(1, 1, 0, 1));
//		Cell month = headerRow.createCell(0);
//		month.setCellValue("Month");
//		month.setCellStyle(headerCellStyle);

		for (int i = 0; i < summaryColumns.length; i++) {
			Cell cell = headerRow.createCell(i);
			cell.setCellValue(summaryColumns[i].toString());
			cell.setCellStyle(headerCellStyle);
		}

		CellStyle dateCellStyle = workbook.createCellStyle();
		dateCellStyle.setDataFormat(createHelper.createDataFormat().getFormat("dd-MM-yyyy"));

		CellStyle dateCellStyles = workbook.createCellStyle();
		dateCellStyles.setDataFormat(createHelper.createDataFormat().getFormat(""));

		int rowNum = 2;
		// BigDecimal sum = BigDecimal.ZERO;

		BigDecimal sum = BigDecimal.ZERO;
		BigDecimal totalEarning = BigDecimal.ZERO;
		BigDecimal basicEarning = BigDecimal.ZERO;
		BigDecimal pensionEarningSalary = BigDecimal.ZERO;
		BigDecimal ProvidentFundEmployee = BigDecimal.ZERO;
		BigDecimal providentFundEmployeer = BigDecimal.ZERO;
		BigDecimal providentFundEmployerPension = BigDecimal.ZERO;
		BigDecimal refund = BigDecimal.ZERO;
		BigDecimal absence = BigDecimal.ZERO;

		BigDecimal epfWages = new BigDecimal(0);
		BigDecimal epsWages = new BigDecimal(0);
		BigDecimal edliWages = new BigDecimal(0);
		BigDecimal epfContri = new BigDecimal(0);
		BigDecimal epsContri = new BigDecimal(0);
		BigDecimal epfEps = new BigDecimal(0);
		BigDecimal grossWages = new BigDecimal(0);

		if (annualSummaryList != null) {

			for (ReportPayOutDTO reportPayOut : annualSummaryList) {

				Row row = summarySheet.createRow(rowNum++);

				// row.createCell(0).setCellValue(reportPayOut.getProcessMonth());
				Cell cell = row.createCell(0);
				cell.setCellValue(reportPayOut.getProcessMonth());

				grossWages = reportPayOut.getTotalEarning() != null ? reportPayOut.getTotalEarning() : BigDecimal.ZERO;
				Cell celltotalearning = row.createCell(1);
				celltotalearning.setCellValue(grossWages.doubleValue());
				celltotalearning.setCellStyle(addZero);

				String str = FormateUtil.ExcelDataPattern(convertBigdecimalToString(reportPayOut.getBasicEarning()));

				sum = (reportPayOut.getBasicEarning() != null ? reportPayOut.getBasicEarning() : BigDecimal.ZERO)
						.add(sum);

				CellStyle cellStyle = workbook.createCellStyle();
				cellStyle.setDataFormat(workbook.getCreationHelper().createDataFormat().getFormat(str));

				epfWages = reportPayOut.getBasicEarning() != null ? reportPayOut.getBasicEarning() : BigDecimal.ZERO;
				Cell cellEarning = row.createCell(2);
				cellEarning.setCellValue(epfWages.doubleValue());
				cellEarning.setCellStyle(addZero);

				epsWages = reportPayOut.getPensionEarningSalary() != null ? reportPayOut.getPensionEarningSalary()
						: BigDecimal.ZERO;
				Cell cellpension = row.createCell(3);
				cellpension.setCellValue(epsWages.doubleValue());
				cellpension.setCellStyle(addZero);

				edliWages = reportPayOut.getPensionEarningSalary() != null ? reportPayOut.getPensionEarningSalary()
						: BigDecimal.ZERO;
				Cell cellpensionearning = row.createCell(4);
				cellpensionearning.setCellValue(edliWages.doubleValue());
				cellpensionearning.setCellStyle(addZero);

				epfContri = reportPayOut.getProvidentFundEmployee() != null ? reportPayOut.getProvidentFundEmployee()
						: BigDecimal.ZERO;
				Cell cellProvidentFund = row.createCell(5);
				cellProvidentFund.setCellValue(epfContri.doubleValue());
				cellProvidentFund.setCellStyle(addZero);

				epsContri = reportPayOut.getProvidentFundEmployerPension() != null
						? reportPayOut.getProvidentFundEmployerPension()
						: BigDecimal.ZERO;
				Cell cellProvident = row.createCell(6);
				cellProvident.setCellValue(epsContri.doubleValue());
				cellProvident.setCellStyle(addZero);

				epfEps = reportPayOut.getProvidentFundEmployer() != null ? reportPayOut.getProvidentFundEmployer()
						: BigDecimal.ZERO;
				Cell cellProfund = row.createCell(7);
				cellProfund.setCellValue(epfEps.doubleValue());
				cellProfund.setCellStyle(addZero);

				row.createCell(8).setCellValue(convertBigdecimalToDouble(reportPayOut.getAbsense()));

				Cell cell1 = row.createCell(9);
				cell1.setCellValue(0.00);
				cell1.setCellStyle(addZero);
			}

		}

		Row rowNew = summarySheet.createRow(rowNum);

		// System.out.println("Sum============================ " + sum);
		for (Object[] total : totalSum) {

			totalEarning = total[0] != null ? (new BigDecimal(total[0].toString())) : null;
			basicEarning = total[1] != null ? (new BigDecimal(total[1].toString())) : null;
			pensionEarningSalary = total[2] != null ? (new BigDecimal(total[2].toString())) : null;
			ProvidentFundEmployee = total[3] != null ? (new BigDecimal(total[3].toString())) : null;
			providentFundEmployeer = total[5] != null ? (new BigDecimal(total[5].toString())) : null;
			providentFundEmployerPension = total[4] != null ? (new BigDecimal(total[4].toString())) : null;
			absence = total[6] != null ? (new BigDecimal(total[6].toString())) : null;

			if (totalEarning != null) {
				Cell cell2 = rowNew.createCell(1);
				cell2.setCellValue(totalEarning.doubleValue());
				cell2.setCellStyle(addZeroBold);
			}

			if (basicEarning != null) {
				Cell cell31 = rowNew.createCell(2);
				cell31.setCellValue(basicEarning.doubleValue());
				cell31.setCellStyle(addZeroBold);
			}

			if (pensionEarningSalary != null) {
				Cell cell32 = rowNew.createCell(3);
				cell32.setCellValue(pensionEarningSalary.doubleValue());
				cell32.setCellStyle(addZeroBold);
			}

			if (pensionEarningSalary != null) {
				Cell cell33 = rowNew.createCell(4);
				cell33.setCellValue(pensionEarningSalary.doubleValue());
				cell33.setCellStyle(addZeroBold);
			}

			if (ProvidentFundEmployee != null) {
				Cell cell34 = rowNew.createCell(5);
				cell34.setCellValue(ProvidentFundEmployee.doubleValue());
				cell34.setCellStyle(addZeroBold);
			}

			if (providentFundEmployeer != null) {
				Cell cell37 = rowNew.createCell(6);
				cell37.setCellValue(providentFundEmployeer.doubleValue());
				cell37.setCellStyle(addZeroBold);
			}

			if (providentFundEmployerPension != null) {
				Cell cell35 = rowNew.createCell(7);
				cell35.setCellValue(providentFundEmployerPension.doubleValue());
				cell35.setCellStyle(addZeroBold);
			}

			if (absence != null) {
				Cell cell35 = rowNew.createCell(8);
				cell35.setCellValue(absence.doubleValue());
				cell35.setCellStyle(addZeroBold);
			}

			// sum
			Cell cell36 = rowNew.createCell(9);
			cell36.setCellValue(0.00);
			cell36.setCellStyle(addZeroBold);

		}

		for (int i = 0; i < summaryColumns.length; i++) {
			summarySheet.autoSizeColumn(i);
		}

		return workbook;

	}

	public static Workbook pTAnnualMonthlyEmployeeWiseReport(Map<String, List<ReportPayOutDTO>> reportPayOutDtoMap,
			List<ReportPayOutDTO> annualSummaryList, String[] columns, Long financialYearId,
			List<ReportPayOutDTO> monthList, String financialYear) {
		// TODO Auto-generated method stub

		Workbook workbook = new XSSFWorkbook();
		CreationHelper createHelper = workbook.getCreationHelper();

		Sheet summarySheet = workbook.createSheet("Summery");

		for (ReportPayOutDTO annualSummary : monthList) {

			for (Map.Entry<String, List<ReportPayOutDTO>> entry : reportPayOutDtoMap.entrySet()) {

				String processMonthAnnual = entry.getKey();
				List<ReportPayOutDTO> reportPayOutDTOList = entry.getValue();
				if (annualSummary.getProcessMonth().equals(processMonthAnnual)) {
					// new HSSFWorkbook() for generating `.xls` file
					CreationHelper createHelperMonthaly = workbook.getCreationHelper();

					Sheet sheet = workbook.createSheet(processMonthAnnual);

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
					CellStyle headerCellStyle111 = workbook.createCellStyle();
					headerCellStyle111.setFont(headerFont);
					headerCellStyle111.setAlignment(HorizontalAlignment.CENTER);
					headerCellStyle111.setVerticalAlignment(VerticalAlignment.CENTER);

					CellStyle addZero = workbook.createCellStyle();
					addZero.setDataFormat(createHelper.createDataFormat().getFormat("0.00"));
					addZero.setAlignment(HorizontalAlignment.RIGHT);
					addZero.setVerticalAlignment(VerticalAlignment.CENTER);

					CellStyle addZeroBold = workbook.createCellStyle();
					addZeroBold.setDataFormat(createHelper.createDataFormat().getFormat("0.00"));
					addZeroBold.setFont(headerFont);
					addZeroBold.setAlignment(HorizontalAlignment.RIGHT);
					addZeroBold.setVerticalAlignment(VerticalAlignment.CENTER);

					Row row0 = sheet.createRow(0);
					sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 6));
					Cell cellCom = row0.createCell(0);
					cellCom.setCellValue("Professional Tax Statement for  - " + processMonthAnnual);
					cellCom.setCellStyle(headerCellStyle111);

					Row headerRow = sheet.createRow(1);
					// Creating cells
					for (int i = 0; i < columns.length; i++) {
						Cell cell = headerRow.createCell(i);
						cell.setCellValue(columns[i].toString());
						cell.setCellStyle(headerCellStyle);

					}

					CellStyle dateCellStyle = workbook.createCellStyle();
					dateCellStyle.setDataFormat(createHelper.createDataFormat().getFormat("dd-MM-yyyy"));

					CellStyle dateCellStyles = workbook.createCellStyle();
					dateCellStyles.setDataFormat(createHelper.createDataFormat().getFormat(""));

					int rowNum = 2;
					BigDecimal sum = BigDecimal.ZERO;
					BigDecimal totalAmount = BigDecimal.ZERO;

					if (reportPayOutDTOList != null) {
						int i = 1;

						for (ReportPayOutDTO reportPayOut : reportPayOutDTOList) {

							Row row = sheet.createRow(rowNum++);
							row.createCell(0).setCellValue(i);
							i++;

							row.createCell(1).setCellValue(reportPayOut.getEmployeeCode());
							if (reportPayOut.getEmpName() != null)
								row.createCell(2).setCellValue(reportPayOut.getEmpName());
							row.createCell(3).setCellValue(reportPayOut.getJobLocation());
							row.createCell(4).setCellValue(reportPayOut.getStateName());

							sum = sum.add(reportPayOut.getTotalEarning());
							Cell cell4 = row.createCell(5);
							cell4.setCellStyle(addZero);
							cell4.setCellValue(reportPayOut.getTotalEarning().doubleValue());

							if (reportPayOut.getAmount() != null)
								totalAmount = reportPayOut.getAmount().add(totalAmount);

							Cell cell5 = row.createCell(6);
							cell5.setCellStyle(addZero);
							if (reportPayOut.getAmount() != null)
								cell5.setCellValue(reportPayOut.getAmount().doubleValue());

						}

					}

					headerCellStyle.setVerticalAlignment(VerticalAlignment.JUSTIFY);

					Row rowNew = sheet.createRow(rowNum);
					Cell cell5 = rowNew.createCell(5);
					cell5.setCellValue(sum.doubleValue());
					cell5.setCellStyle(addZeroBold);

					Cell cell6 = rowNew.createCell(6);

					cell6.setCellValue(totalAmount.doubleValue());
					cell6.setCellStyle(addZeroBold);

					for (int i = 0; i < columns.length; i++) {
						sheet.autoSizeColumn(i);
					}

				}

			}
		}

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

		CellStyle headerCellStyle111 = workbook.createCellStyle();
		headerCellStyle111.setFont(headerFont);
		headerCellStyle111.setAlignment(HorizontalAlignment.LEFT);
		headerCellStyle111.setVerticalAlignment(VerticalAlignment.CENTER);

		CellStyle headerCellStyle101 = workbook.createCellStyle();
		headerCellStyle101.setFont(headerFont);
		headerCellStyle101.setAlignment(HorizontalAlignment.CENTER);
		headerCellStyle101.setVerticalAlignment(VerticalAlignment.CENTER);

		CellStyle addZero = workbook.createCellStyle();
		addZero.setDataFormat(createHelper.createDataFormat().getFormat("0.00"));
		addZero.setAlignment(HorizontalAlignment.RIGHT);
		addZero.setVerticalAlignment(VerticalAlignment.CENTER);

		CellStyle addZeroBold = workbook.createCellStyle();
		addZeroBold.setDataFormat(createHelper.createDataFormat().getFormat("0.00"));
		addZeroBold.setFont(headerFont);
		addZeroBold.setAlignment(HorizontalAlignment.RIGHT);
		addZeroBold.setVerticalAlignment(VerticalAlignment.CENTER);

		Row row0 = summarySheet.createRow(0);
		summarySheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 6));
		Cell cellCom = row0.createCell(0);
		cellCom.setCellValue("Employee Wise Professional Tax Statement for - FY " + financialYear);
		cellCom.setCellStyle(headerCellStyle101);

		Row row1 = summarySheet.createRow(1);

		// Row headerRow = summarySheet.createRow(2);

		for (int i = 0; i < columns.length; i++) {
			Cell cell = row1.createCell(i);
			cell.setCellValue(columns[i].toString());
			cell.setCellStyle(headerCellStyle);
		}

		CellStyle dateCellStyle = workbook.createCellStyle();
		dateCellStyle.setDataFormat(createHelper.createDataFormat().getFormat("dd-MM-yyyy"));

		CellStyle dateCellStyles = workbook.createCellStyle();
		dateCellStyles.setDataFormat(createHelper.createDataFormat().getFormat(""));

		int rowNum = 2;
		BigDecimal sum = BigDecimal.ZERO;
		BigDecimal totalAmount = BigDecimal.ZERO;

		if (annualSummaryList != null) {

			int i = 1;
			for (ReportPayOutDTO reportPayOut : annualSummaryList) {
				Row row = summarySheet.createRow(rowNum++);

				row.createCell(0).setCellValue(i);

				i++;

				row.createCell(1).setCellValue(reportPayOut.getEmployeeCode());
				if (reportPayOut.getEmpName() != null)
					row.createCell(2).setCellValue(reportPayOut.getEmpName());
				row.createCell(3).setCellValue(reportPayOut.getJobLocation());
				row.createCell(4).setCellValue(reportPayOut.getStateName());

				sum = sum.add(reportPayOut.getTotalEarning());
				Cell cell4 = row.createCell(5);
				cell4.setCellStyle(addZero);
				cell4.setCellValue(reportPayOut.getTotalEarning().doubleValue());

				if (reportPayOut.getAmount() != null)
					totalAmount = reportPayOut.getAmount().add(totalAmount);

				Cell cell5 = row.createCell(6);
				cell5.setCellStyle(addZero);
				if (reportPayOut.getAmount() != null)
					cell5.setCellValue(reportPayOut.getAmount().doubleValue());

			}

		}

		headerCellStyle.setVerticalAlignment(VerticalAlignment.JUSTIFY);

		Row rowNew = summarySheet.createRow(rowNum);
		Cell cell5 = rowNew.createCell(5);
		cell5.setCellValue(sum.doubleValue());
		cell5.setCellStyle(addZeroBold);

		Cell cell6 = rowNew.createCell(6);
		cell6.setCellValue(totalAmount.doubleValue());
		cell6.setCellStyle(addZeroBold);

		for (int i = 0; i < columns.length; i++) {
			summarySheet.autoSizeColumn(i);
		}

		return workbook;

	}

	public static Workbook pTAnnualMonthlyStateWiseReport(Map<String, List<ReportPayOutDTO>> reportPayOutDtoMap,
			List<ReportPayOutDTO> annualSummaryList, String[] columns, Long financialYearId,
			List<ReportPayOutDTO> monthList, String financialYear) {
		// TODO Auto-generated method stub
		Workbook workbook = new XSSFWorkbook();
		CreationHelper createHelper = workbook.getCreationHelper();

		Sheet summarySheet = workbook.createSheet("Summery");

		for (ReportPayOutDTO annualSummary : monthList) {

			for (Map.Entry<String, List<ReportPayOutDTO>> entry : reportPayOutDtoMap.entrySet()) {

				String processMonthAnnual = entry.getKey();
				List<ReportPayOutDTO> reportPayOutDTOList = entry.getValue();
				if (annualSummary.getProcessMonth().equals(processMonthAnnual)) {
					// new HSSFWorkbook() for generating `.xls` file
					CreationHelper createHelperMonthaly = workbook.getCreationHelper();

					Sheet sheet = workbook.createSheet(processMonthAnnual);

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
					CellStyle headerCellStyle111 = workbook.createCellStyle();
					headerCellStyle111.setFont(headerFont);
					headerCellStyle111.setAlignment(HorizontalAlignment.CENTER);
					headerCellStyle111.setVerticalAlignment(VerticalAlignment.CENTER);

					CellStyle addZero = workbook.createCellStyle();
					addZero.setDataFormat(createHelper.createDataFormat().getFormat("0.00"));
					addZero.setAlignment(HorizontalAlignment.RIGHT);
					addZero.setVerticalAlignment(VerticalAlignment.CENTER);

					CellStyle addZeroBold = workbook.createCellStyle();
					addZeroBold.setDataFormat(createHelper.createDataFormat().getFormat("0.00"));
					addZeroBold.setFont(headerFont);
					addZeroBold.setAlignment(HorizontalAlignment.RIGHT);
					addZeroBold.setVerticalAlignment(VerticalAlignment.CENTER);

					Row row0 = sheet.createRow(0);
					sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 3));
					Cell cellCom = row0.createCell(0);
					cellCom.setCellValue("Professional Tax Statement for  - " + processMonthAnnual);
					cellCom.setCellStyle(headerCellStyle111);

					Row headerRow = sheet.createRow(1);
					// Creating cells
					for (int i = 0; i < columns.length; i++) {
						Cell cell = headerRow.createCell(i);
						cell.setCellValue(columns[i].toString());
						cell.setCellStyle(headerCellStyle);

					}

					CellStyle dateCellStyle = workbook.createCellStyle();
					dateCellStyle.setDataFormat(createHelper.createDataFormat().getFormat("dd-MM-yyyy"));

					CellStyle dateCellStyles = workbook.createCellStyle();
					dateCellStyles.setDataFormat(createHelper.createDataFormat().getFormat(""));

					int rowNum = 2;
					BigDecimal sum = BigDecimal.ZERO;
					BigDecimal totalAmount = BigDecimal.ZERO;

					if (reportPayOutDTOList != null) {
						int i = 1;

						for (ReportPayOutDTO reportPayOut : reportPayOutDTOList) {

							Row row = sheet.createRow(rowNum++);

							row.createCell(0).setCellValue(reportPayOut.getStateName());
							row.createCell(1).setCellValue(reportPayOut.getTotalEmployee());

							sum = sum.add(reportPayOut.getTotalEarning());
							Cell cell2 = row.createCell(2);
							cell2.setCellStyle(addZero);
							cell2.setCellValue(reportPayOut.getTotalEarning().doubleValue());

							if (reportPayOut.getAmount() != null)
								totalAmount = reportPayOut.getAmount().add(totalAmount);

							Cell cell3 = row.createCell(3);
							cell3.setCellStyle(addZero);
							if (reportPayOut.getAmount() != null)
								cell3.setCellValue(reportPayOut.getAmount().doubleValue());

						}

					}
					headerCellStyle.setVerticalAlignment(VerticalAlignment.JUSTIFY);

					Row rowNew = sheet.createRow(rowNum);
					Cell cell5 = rowNew.createCell(2);
					cell5.setCellValue(sum.doubleValue());
					cell5.setCellStyle(addZeroBold);

					Cell cell6 = rowNew.createCell(3);
					cell6.setCellValue(totalAmount.doubleValue());
					cell6.setCellStyle(addZeroBold);

					for (int i = 0; i < columns.length; i++) {
						sheet.autoSizeColumn(i);
					}

				}

			}
		}

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

		CellStyle headerCellStyle111 = workbook.createCellStyle();
		headerCellStyle111.setFont(headerFont);
		headerCellStyle111.setAlignment(HorizontalAlignment.LEFT);
		headerCellStyle111.setVerticalAlignment(VerticalAlignment.CENTER);

		CellStyle headerCellStyle101 = workbook.createCellStyle();
		headerCellStyle101.setFont(headerFont);
		headerCellStyle101.setAlignment(HorizontalAlignment.CENTER);
		headerCellStyle101.setVerticalAlignment(VerticalAlignment.CENTER);

		CellStyle addZero = workbook.createCellStyle();
		addZero.setDataFormat(createHelper.createDataFormat().getFormat("0.00"));
		addZero.setAlignment(HorizontalAlignment.RIGHT);
		addZero.setVerticalAlignment(VerticalAlignment.CENTER);

		CellStyle addZeroBold = workbook.createCellStyle();
		addZeroBold.setDataFormat(createHelper.createDataFormat().getFormat("0.00"));
		addZeroBold.setFont(headerFont);
		addZeroBold.setAlignment(HorizontalAlignment.RIGHT);
		addZeroBold.setVerticalAlignment(VerticalAlignment.CENTER);

		Row row0 = summarySheet.createRow(0);
		summarySheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 3));
		Cell cellCom = row0.createCell(0);
		cellCom.setCellValue("State Wise Professional Tax Statement for - FY " + financialYear);
		cellCom.setCellStyle(headerCellStyle101);

		Row row1 = summarySheet.createRow(1);

		// Row headerRow = summarySheet.createRow(2);

		for (int i = 0; i < columns.length; i++) {
			Cell cell = row1.createCell(i);
			cell.setCellValue(columns[i].toString());
			cell.setCellStyle(headerCellStyle);
		}

		CellStyle dateCellStyle = workbook.createCellStyle();
		dateCellStyle.setDataFormat(createHelper.createDataFormat().getFormat("dd-MM-yyyy"));

		CellStyle dateCellStyles = workbook.createCellStyle();
		dateCellStyles.setDataFormat(createHelper.createDataFormat().getFormat(""));

		int rowNum = 2;
		BigDecimal sum = BigDecimal.ZERO;
		BigDecimal totalAmount = BigDecimal.ZERO;

		if (annualSummaryList != null) {

			for (ReportPayOutDTO reportPayOut : annualSummaryList) {
				Row row = summarySheet.createRow(rowNum++);

				row.createCell(0).setCellValue(reportPayOut.getStateName());
				row.createCell(1).setCellValue(reportPayOut.getTotalEmployee());

				sum = sum.add(reportPayOut.getTotalEarning());
				Cell cell2 = row.createCell(2);
				cell2.setCellStyle(addZero);
				cell2.setCellValue(reportPayOut.getTotalEarning().doubleValue());

				if (reportPayOut.getAmount() != null)
					totalAmount = reportPayOut.getAmount().add(totalAmount);

				Cell cell3 = row.createCell(3);
				cell3.setCellStyle(addZero);
				if (reportPayOut.getAmount() != null)
					cell3.setCellValue(reportPayOut.getAmount().doubleValue());
			}
		}
		headerCellStyle.setVerticalAlignment(VerticalAlignment.JUSTIFY);

		Row rowNew = summarySheet.createRow(rowNum);
		Cell cell5 = rowNew.createCell(2);
		cell5.setCellValue(sum.doubleValue());
		cell5.setCellStyle(addZeroBold);

		Cell cell6 = rowNew.createCell(3);
		cell6.setCellValue(totalAmount.doubleValue());
		cell6.setCellStyle(addZeroBold);

		for (int i = 0; i < columns.length; i++) {
			summarySheet.autoSizeColumn(i);
		}

		return workbook;
	}

public static Workbook employeePFContributionReport(List<ReportPayOutDTO> reportPayoutDtoList, String[] columns,
			List<Object[]> employeeCount, List<Object[]> total, String activeStatus) {

		Workbook workbook = new XSSFWorkbook(); // new HSSFWorkbook() for generating `.xls` file

		CreationHelper createHelper = workbook.getCreationHelper();

		Sheet sheet = workbook.createSheet("PF Contribution Statement");

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

		CellStyle headerCellStyle1 = workbook.createCellStyle();
		headerCellStyle1.setFont(headerFont);
		headerCellStyle1.setAlignment(HorizontalAlignment.RIGHT);
		headerCellStyle1.setVerticalAlignment(VerticalAlignment.CENTER);
		headerCellStyle1.setFillBackgroundColor(IndexedColors.LIGHT_CORNFLOWER_BLUE.getIndex());
		headerCellStyle1.setFillPattern(FillPatternType.FINE_DOTS);

		CellStyle headerCellStyle11 = workbook.createCellStyle();
		headerCellStyle11.setFont(headerFont);
		headerCellStyle11.setAlignment(HorizontalAlignment.LEFT);
		headerCellStyle11.setVerticalAlignment(VerticalAlignment.CENTER);
		CellStyle headerCellStyle111 = workbook.createCellStyle();
		headerCellStyle111.setFont(headerFont);
		headerCellStyle111.setAlignment(HorizontalAlignment.CENTER);
		headerCellStyle111.setVerticalAlignment(VerticalAlignment.CENTER);

		CellStyle headerCellStyle112 = workbook.createCellStyle();
		headerCellStyle112.setFont(headerFont);
		headerCellStyle112.setAlignment(HorizontalAlignment.LEFT);
		headerCellStyle112.setVerticalAlignment(VerticalAlignment.CENTER);

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
		headerCellStyleCode.setAlignment(HorizontalAlignment.CENTER);
		headerCellStyleCode.setVerticalAlignment(VerticalAlignment.CENTER);

		CellStyle addZero1 = workbook.createCellStyle();
		addZero1.setDataFormat(createHelper.createDataFormat().getFormat("0.00"));
		addZero1.setAlignment(HorizontalAlignment.LEFT);
		addZero1.setVerticalAlignment(VerticalAlignment.CENTER);

		CellStyle dateCellStyle = workbook.createCellStyle();
		dateCellStyle.setDataFormat(createHelper.createDataFormat().getFormat("dd-MMM-yyyy"));
		dateCellStyle.setFont(headerFont);
		dateCellStyle.setAlignment(HorizontalAlignment.LEFT);
		dateCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);

		Row row0 = sheet.createRow(0);

//		sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 8));
//		Cell cellAdd = row0.createCell(0);
//		cellAdd.setCellValue("For Computronics Systems India Pvt. Ltd. PF Contribution Statement");
//		cellAdd.setCellStyle(headerCellStyleCode);

		StatusMessage sm = new StatusMessage();

		if (activeStatus.equalsIgnoreCase("null")) {
			sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 8));
			Cell cell0 = row0.createCell(0);
			cell0.setCellValue(" For Computronics Systems India Pvt. Ltd. PF Contribution Statement of an Employee");
			cell0.setCellStyle(headerCellStyleCode);
		} else if (activeStatus.equalsIgnoreCase(sm.ACTIVE_CODE)) {
			sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 8));
			Cell cell0 = row0.createCell(0);
			cell0.setCellValue(
					" For Computronics Systems India Pvt. Ltd. PF Contribution Statement of Working Employee");
			cell0.setCellStyle(headerCellStyleCode);
		} else if (activeStatus.equalsIgnoreCase(sm.DEACTIVE_CODE)) {
			sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 8));
			Cell cell0 = row0.createCell(0);
			cell0.setCellValue(
					" For Computronics Systems India Pvt. Ltd. PF Contribution Statement of Former Employee");
			cell0.setCellStyle(headerCellStyleCode);
		}

		for (Object[] epfObj : employeeCount) {

			String employeeName = epfObj[0] != null ? (String) epfObj[0] : null;

			Row row1 = sheet.createRow(1);
			sheet.addMergedRegion(new CellRangeAddress(1, 1, 0, 8));

			Cell totalEmp = row1.createCell(0);
			totalEmp.setCellStyle(headerCellStyle112);
			totalEmp.setCellValue(employeeName);

			Date dateOfJoining = epfObj[1] != null ? (Date) epfObj[1] : null;

			Row row2 = sheet.createRow(2);
			sheet.addMergedRegion(new CellRangeAddress(2, 2, 0, 8));

			if (dateOfJoining != null) {
				Cell cellemp = row2.createCell(0);
				cellemp.setCellValue("DOJ : " + DateUtils.getDateStrInDDMMMYYYY(dateOfJoining));
				cellemp.setCellStyle(dateCellStyle);
			} else {
				Cell cellemp = row2.createCell(0);
				cellemp.setCellValue("DOJ : Not available");
				cellemp.setCellStyle(dateCellStyle);
			}

			Date epfJoining = epfObj[2] != null ? (Date) epfObj[2] : null;
			Row row3 = sheet.createRow(3);
			sheet.addMergedRegion(new CellRangeAddress(3, 3, 0, 8));

			if (epfJoining != null) {
				Cell cellepf = row3.createCell(0);
				cellepf.setCellValue("EPF Effective from : " + DateUtils.getDateStrInDDMMMYYYY(epfJoining));
				cellepf.setCellStyle(dateCellStyle);
			} else {
				Cell cellepf = row3.createCell(0);
				cellepf.setCellValue("EPF Effective from : Not available");
				cellepf.setCellStyle(dateCellStyle);
			}

			String UANNo = epfObj[3] != null ? (String) epfObj[3] : null;
			Row row4 = sheet.createRow(4);
			sheet.addMergedRegion(new CellRangeAddress(4, 4, 0, 8));

			if (UANNo != null) {
				Cell cellUan = row4.createCell(0);
				cellUan.setCellValue("UAN : " + UANNo);
				cellUan.setCellStyle(headerCellStyle112);
			} else {
				Cell cellUan = row4.createCell(0);
				cellUan.setCellValue("UAN : Not available");
				cellUan.setCellStyle(headerCellStyle112);
			}

			String PFNo = epfObj[4] != null ? (String) epfObj[4] : null;
			Row row5 = sheet.createRow(5);
			sheet.addMergedRegion(new CellRangeAddress(5, 5, 0, 8));

			if (PFNo != null) {
				Cell cellPf = row5.createCell(0);
				cellPf.setCellValue("PF No. : " + PFNo);
				cellPf.setCellStyle(headerCellStyle112);
			} else {
				Cell cellPf = row5.createCell(0);
				cellPf.setCellValue("PF No. : Not available");
				cellPf.setCellStyle(headerCellStyle112);
			}

		}

		CellStyle addZero = workbook.createCellStyle();
		addZero.setDataFormat(createHelper.createDataFormat().getFormat("0.00"));
		addZero.setAlignment(HorizontalAlignment.RIGHT);
		addZero.setVerticalAlignment(VerticalAlignment.CENTER);

		CellStyle addZeroBold = workbook.createCellStyle();
		addZeroBold.setDataFormat(createHelper.createDataFormat().getFormat("0.00"));
		addZeroBold.setFont(headerFont);
		addZeroBold.setAlignment(HorizontalAlignment.RIGHT);
		addZeroBold.setVerticalAlignment(VerticalAlignment.CENTER);

		CellStyle addZeroBold1 = workbook.createCellStyle();
		addZeroBold1.setDataFormat(createHelper.createDataFormat().getFormat("0.00"));
		addZeroBold1.setFont(headerFont);
		addZeroBold1.setAlignment(HorizontalAlignment.LEFT);
		addZeroBold1.setVerticalAlignment(VerticalAlignment.CENTER);

		Row headerRow = sheet.createRow(6);
		// Creating cells
		for (int i = 0; i < columns.length; i++) {
			Cell cell = headerRow.createCell(i);
			cell.setCellValue(columns[i]);
			cell.setCellStyle(headerCellStyle);

		}

		int rowNum = 7;
		BigDecimal sum = BigDecimal.ZERO;
		BigDecimal totalEarning = BigDecimal.ZERO;
		BigDecimal basicEarning = BigDecimal.ZERO;
		BigDecimal pensionEarningSalary = BigDecimal.ZERO;
		BigDecimal ProvidentFundEmployee = BigDecimal.ZERO;
		BigDecimal providentFundEmployeer = BigDecimal.ZERO;
		BigDecimal providentFundEmployerPension = BigDecimal.ZERO;
		BigDecimal totalContribution = BigDecimal.ZERO;
		BigDecimal absence = BigDecimal.ZERO;

		BigDecimal epfWages = new BigDecimal(0);
		BigDecimal epsWages = new BigDecimal(0);
		BigDecimal epfContri = new BigDecimal(0);
		BigDecimal epsContri = new BigDecimal(0);
		BigDecimal epfEps = new BigDecimal(0);
		BigDecimal grossWages = new BigDecimal(0);
		BigDecimal totalContro = new BigDecimal(0);

		if (reportPayoutDtoList != null) {

			for (ReportPayOutDTO reportPayOut : reportPayoutDtoList) {

				Row row = sheet.createRow(rowNum++);

				row.createCell(0).setCellValue(reportPayOut.getProcessMonth());

				grossWages = reportPayOut.getTotalEarning() != null ? reportPayOut.getTotalEarning() : BigDecimal.ZERO;
				Cell celltotalearning = row.createCell(1);
				celltotalearning.setCellValue(grossWages.doubleValue());
				celltotalearning.setCellStyle(addZero);

				String str = FormateUtil.ExcelDataPattern(convertBigdecimalToString(reportPayOut.getBasicEarning()));
				sum = (reportPayOut.getBasicEarning() != null ? reportPayOut.getBasicEarning() : BigDecimal.ZERO)
						.add(sum);

				CellStyle cellStyle = workbook.createCellStyle();
				cellStyle.setDataFormat(workbook.getCreationHelper().createDataFormat().getFormat(str));

				epfWages = reportPayOut.getBasicEarning() != null ? reportPayOut.getBasicEarning() : BigDecimal.ZERO;
				Cell cellEarning = row.createCell(2);
				cellEarning.setCellValue(epfWages.doubleValue());
				cellEarning.setCellStyle(addZero);

				epsWages = reportPayOut.getPensionEarningSalary() != null ? reportPayOut.getPensionEarningSalary()
						: BigDecimal.ZERO;
				Cell cellpension = row.createCell(3);
				cellpension.setCellValue(epsWages.doubleValue());
				cellpension.setCellStyle(addZero);

				epfContri = reportPayOut.getProvidentFundEmployee() != null ? reportPayOut.getProvidentFundEmployee()
						: BigDecimal.ZERO;
				Cell cellProvidentFund = row.createCell(4);
				cellProvidentFund.setCellValue(epfContri.doubleValue());
				cellProvidentFund.setCellStyle(addZero);

				epsContri = reportPayOut.getProvidentFundEmployerPension() != null
						? reportPayOut.getProvidentFundEmployerPension()
						: BigDecimal.ZERO;
				Cell cellProvident = row.createCell(5);
				cellProvident.setCellValue(epsContri.doubleValue());
				cellProvident.setCellStyle(addZero);

				epfEps = reportPayOut.getProvidentFundEmployer() != null ? reportPayOut.getProvidentFundEmployer()
						: BigDecimal.ZERO;
				Cell cellProfund = row.createCell(6);
				cellProfund.setCellValue(epfEps.doubleValue());
				cellProfund.setCellStyle(addZero);

				totalContro = reportPayOut.getTotalContribution() != null ? reportPayOut.getTotalContribution()
						: BigDecimal.ZERO;
				Cell cellTotal = row.createCell(7);
				cellTotal.setCellValue(totalContro.doubleValue());
				cellTotal.setCellStyle(addZero);

				row.createCell(8).setCellValue(convertBigdecimalToDouble(reportPayOut.getAbsense()));

			}

		}

		headerCellStyle.setVerticalAlignment(VerticalAlignment.JUSTIFY);

		Row rowNew = sheet.createRow(rowNum);

		for (Object[] totalSum : total) {

			totalEarning = totalSum[0] != null ? (new BigDecimal(totalSum[0].toString())) : null;
			basicEarning = totalSum[1] != null ? (new BigDecimal(totalSum[1].toString())) : null;
			pensionEarningSalary = totalSum[2] != null ? (new BigDecimal(totalSum[2].toString())) : null;
			ProvidentFundEmployee = totalSum[3] != null ? (new BigDecimal(totalSum[3].toString())) : null;
			providentFundEmployeer = totalSum[5] != null ? (new BigDecimal(totalSum[5].toString())) : null;
			providentFundEmployerPension = totalSum[4] != null ? (new BigDecimal(totalSum[4].toString())) : null;
			totalContribution = totalSum[6] != null ? (new BigDecimal(totalSum[6].toString())) : null;
			absence = totalSum[7] != null ? (new BigDecimal(totalSum[7].toString())) : null;

			if (totalEarning != null) {
				Cell cell2 = rowNew.createCell(1);
				cell2.setCellValue(totalEarning.doubleValue());
				cell2.setCellStyle(addZeroBold);
			}

			if (basicEarning != null) {
				Cell cell31 = rowNew.createCell(2);
				cell31.setCellValue(basicEarning.doubleValue());
				cell31.setCellStyle(addZeroBold);
			}

			if (pensionEarningSalary != null) {
				Cell cell32 = rowNew.createCell(3);
				cell32.setCellValue(pensionEarningSalary.doubleValue());
				cell32.setCellStyle(addZeroBold);
			}

			if (ProvidentFundEmployee != null) {
				Cell cell34 = rowNew.createCell(4);
				cell34.setCellValue(ProvidentFundEmployee.doubleValue());
				cell34.setCellStyle(addZeroBold);
			}

			if (providentFundEmployeer != null) {
				Cell cell37 = rowNew.createCell(5);
				cell37.setCellValue(providentFundEmployeer.doubleValue());
				cell37.setCellStyle(addZeroBold);
			}

			if (providentFundEmployerPension != null) {
				Cell cell35 = rowNew.createCell(6);
				cell35.setCellValue(providentFundEmployerPension.doubleValue());
				cell35.setCellStyle(addZeroBold);
			}

			if (totalContribution != null) {
				Cell cell35 = rowNew.createCell(7);
				cell35.setCellValue(totalContribution.doubleValue());
				cell35.setCellStyle(addZeroBold);
			}

			if (absence != null) {
				Cell cell35 = rowNew.createCell(8);
				cell35.setCellValue(absence.doubleValue());
				cell35.setCellStyle(addZeroBold);
			}

		}

		// Resize all columns to fit the content size
		for (int i = 0; i < columns.length; i++) {
			sheet.autoSizeColumn(i);
		}

		return workbook;

	}

public static   Workbook SampleFileWritter(String[] columns , String processMonth, List<String> payHeadList, String status ) {
	// TODO Auto-generated method stub
//	Workbook workbook = new XSSFWorkbook();
//	XSSFSheet sheet = (XSSFSheet)workbook.createSheet("sheet");
	DataValidation dataValidation = null;
	DataValidationConstraint constraint = null;
	DataValidationHelper validationHelper = null;
	
	DataValidation dataValidation1 = null;
	DataValidationConstraint constraint1 = null;
	DataValidationHelper validationHelper1 = null;
	
//	CreationHelper createHelper = workbook.getCreationHelper();
//	Sheet sheet = workbook.createSheet("One Time Earning");
	XSSFWorkbook workbook = new XSSFWorkbook();
	XSSFSheet sheet= null;
	if(status.equals("EA")) {
		  sheet = (XSSFSheet) workbook.createSheet("One Time Earning");
	}else {
		  sheet = (XSSFSheet) workbook.createSheet("One Time Deduction");
	}
	
	CreationHelper createHelper = workbook.getCreationHelper();
	
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

	CellStyle headerCellStyle1 = workbook.createCellStyle();
	headerCellStyle1.setFont(headerFont);
	headerCellStyle1.setAlignment(HorizontalAlignment.RIGHT);
	headerCellStyle1.setVerticalAlignment(VerticalAlignment.CENTER);
	headerCellStyle1.setFillBackgroundColor(IndexedColors.LIGHT_CORNFLOWER_BLUE.getIndex());
	headerCellStyle1.setFillPattern(FillPatternType.FINE_DOTS);

	CellStyle headerCellStyle11 = workbook.createCellStyle();
	headerCellStyle11.setFont(headerFont);
	headerCellStyle11.setAlignment(HorizontalAlignment.LEFT);
	headerCellStyle11.setVerticalAlignment(VerticalAlignment.CENTER);

	CellStyle headerCellStyle112 = workbook.createCellStyle();
	headerCellStyle112.setAlignment(HorizontalAlignment.LEFT);
	headerCellStyle112.setVerticalAlignment(VerticalAlignment.CENTER);

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

	CellStyle dateCellStyle = workbook.createCellStyle();
	dateCellStyle.setDataFormat(createHelper.createDataFormat().getFormat("dd-MM-yyyy"));
	//Map<String, String> map = new HashMap<String, String>();
    validationHelper=new XSSFDataValidationHelper(sheet);
    validationHelper1=new XSSFDataValidationHelper(sheet);
    CellRangeAddressList addressList = new  CellRangeAddressList(1,1000,2,2); 
    CellRangeAddressList empCode = new CellRangeAddressList(1, 1000, 5, 5);
	String[] itemsArray = new String[payHeadList.size()]; 
	itemsArray = payHeadList.toArray(itemsArray);

//	String[] empCodeArray = new String[employeeCodeList.size()];
//	empCodeArray = employeeCodeList.toArray(empCodeArray);
	
	constraint = validationHelper.createExplicitListConstraint(itemsArray);
	dataValidation = validationHelper.createValidation(constraint, addressList);
    dataValidation.setSuppressDropDownArrow(true);
    sheet.addValidationData(dataValidation);
    processMonth="'"+processMonth; 
	constraint1 = validationHelper1.createExplicitListConstraint(new String[] {processMonth});
	dataValidation1 = validationHelper1.createValidation(constraint1, empCode);
	dataValidation1.setSuppressDropDownArrow(true);
    sheet.addValidationData(dataValidation1);  
    
	Row headerRow = sheet.createRow(0);
	for (int i = 0; i < columns.length; i++) {
		Cell cell = headerRow.createCell(i);
		cell.setCellValue(columns[i]);
		cell.setCellStyle(headerCellStyle);
	}
	int rowNum = 2;
	 
	 
	// Resize all columns to fit the content size
	for (int i = 0; i < columns.length; i++) {
		sheet.autoSizeColumn(i);
	}

	return workbook;
}

}
