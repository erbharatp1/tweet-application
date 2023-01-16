package com.csipl.hrms.common.util;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;

import com.csipl.hrms.dto.payroll.LoanIssueDTO;
import com.csipl.hrms.dto.payrollprocess.ReportPayOutDTO;
import com.csipl.hrms.model.common.Company;
import com.csipl.hrms.model.organisation.Department;
import com.csipl.hrms.model.payroll.Epf;
import com.csipl.hrms.model.payroll.Esi;
import com.csipl.hrms.model.payrollprocess.ReportPayOut;

import com.itextpdf.text.log.SysoCounter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class ExcelWriter {

	private static double convertBigdecimalToDouble(BigDecimal bigDecimalValue) {
		double doubleValue;

		if (bigDecimalValue != null)
			doubleValue = bigDecimalValue.doubleValue();
		else
			doubleValue = 0.0;
		return doubleValue;
	}

	private static Department getDesignationId(Map<Long, Department> designationMap, Long designationId) {

		return designationMap.get(designationId);
	}

	public static Workbook esiReport(List<ReportPayOutDTO> reportPayOutList, List<Object[]> consulatantList, String[] columns, String processMonth,
			Company company, Esi esi) throws IOException, InvalidFormatException {
		// Create a Workbook
		Workbook workbook = new XSSFWorkbook(); // new HSSFWorkbook() for generating `.xls` file

		/*
		 * CreationHelper helps us create instances for various things like DataFormat,
		 * Hyperlink, RichTextString etc, in a format (HSSF, XSSF) independent way
		 */
		CreationHelper createHelper = workbook.getCreationHelper();

		// Create a Sheet
		Sheet sheet = workbook.createSheet("ESI Working for Consultant");

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
		headerCellStyle11.setAlignment(HorizontalAlignment.RIGHT);
		headerCellStyle11.setVerticalAlignment(VerticalAlignment.CENTER);
		
		CellStyle headerCellStyle111 = workbook.createCellStyle();
		headerCellStyle111.setFont(headerFont);
		headerCellStyle111.setAlignment(HorizontalAlignment.LEFT);
		headerCellStyle111.setVerticalAlignment(VerticalAlignment.CENTER);

		CellStyle headerCellStyleEmp = workbook.createCellStyle();
		headerCellStyleEmp.setFont(headerFont);
		headerCellStyleEmp.setAlignment(HorizontalAlignment.RIGHT);
		headerCellStyleEmp.setVerticalAlignment(VerticalAlignment.CENTER);

		CellStyle title = workbook.createCellStyle();
		title.setFont(headerFont);
		title.setAlignment(HorizontalAlignment.CENTER);
		title.setVerticalAlignment(VerticalAlignment.CENTER);
		
		
		CellStyle addZero = workbook.createCellStyle();
		addZero.setDataFormat(createHelper.createDataFormat().getFormat("0.00"));
		addZero.setAlignment(HorizontalAlignment.RIGHT);
		addZero.setVerticalAlignment(VerticalAlignment.CENTER);
		
		CellStyle addZeroLeft = workbook.createCellStyle();
		addZeroLeft.setDataFormat(createHelper.createDataFormat().getFormat("0.00"));
		addZeroLeft.setAlignment(HorizontalAlignment.LEFT);
		addZeroLeft.setVerticalAlignment(VerticalAlignment.CENTER);
		
		CellStyle addZero1 = workbook.createCellStyle();
		addZero1.setDataFormat(createHelper.createDataFormat().getFormat("0.00"));
		addZero1.setAlignment(HorizontalAlignment.RIGHT);
		addZero1.setVerticalAlignment(VerticalAlignment.CENTER);
		
		CellStyle addZeroBold = workbook.createCellStyle();
		addZeroBold.setDataFormat(createHelper.createDataFormat().getFormat("0.00"));
		addZeroBold.setFont(headerFont);
		addZeroBold.setAlignment(HorizontalAlignment.RIGHT);
		addZeroBold.setVerticalAlignment(VerticalAlignment.CENTER);
		addZeroBold.setFillBackgroundColor(IndexedColors.LIGHT_CORNFLOWER_BLUE.getIndex());
		addZeroBold.setFillPattern(FillPatternType.FINE_DOTS);
		
		CellStyle cellStyle112 = workbook.createCellStyle();
		cellStyle112.setAlignment(HorizontalAlignment.LEFT);
		cellStyle112.setVerticalAlignment(VerticalAlignment.CENTER);
		
		CellStyle date = workbook.createCellStyle();
		date.setDataFormat(createHelper.createDataFormat().getFormat("0.00"));
		date.setAlignment(HorizontalAlignment.LEFT);
		date.setVerticalAlignment(VerticalAlignment.CENTER);
		
		
		// Create Other rows and cells with employees data
		Row row0 = sheet.createRow(0);
		sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 13));
		Cell cellCom = row0.createCell(0);
		cellCom.setCellValue("ESI Working - " + processMonth);
		cellCom.setCellStyle(title);

		Row row1 = sheet.createRow(1);
		sheet.addMergedRegion(new CellRangeAddress(1, 1, 0, 13));
		row1.createCell(0).setCellValue("ESIC CODE -" + company.getEsicNo());

		Row row2 = sheet.createRow(2);
		sheet.addMergedRegion(new CellRangeAddress(2, 2, 0, 4));
		row2.createCell(0).setCellValue("Total Number of Employees ");

		Row row3 = sheet.createRow(3);
		sheet.addMergedRegion(new CellRangeAddress(3, 3, 0, 4));
		row3.createCell(0).setCellValue("Total Number of Excluded Employees");

		Row row4 = sheet.createRow(4);
		sheet.addMergedRegion(new CellRangeAddress(4, 4, 0, 4));
		row4.createCell(0).setCellValue("Gross Wages of Excluded Employees");

		// Row headerRow = sheet.createRow(5);

		// sheet.addMergedRegion(new CellRangeAddress(0,1,1,2));

		// String monthAcronym=processMonth.substring(0, 3);
		// Row row2 = sheet.createRow(2);
		// sheet.addMergedRegion(new CellRangeAddress(2,2,0,2));
		// row2.createCell(0).setCellValue("SALARY SHEET FOR MONTH OF -"+ processMonth);

		// Create a Row
		BigDecimal grossSalary = new BigDecimal(0);
		for (Object[] epfObj : consulatantList) {
		
			Integer totalNoOfEmployee = epfObj[0] != null ? Integer.parseInt(epfObj[0].toString()) : null;
			sheet.addMergedRegion(new CellRangeAddress(2, 2, 5, 10));
			Cell cell1 = row2.createCell(5);
			cell1.setCellValue(totalNoOfEmployee);
			cell1.setCellStyle(cellStyle112);

			Integer excludedEmployee = epfObj[1] != null ? Integer.parseInt(epfObj[1].toString()) : null;
			sheet.addMergedRegion(new CellRangeAddress(3, 3, 5, 10));
			Cell cell2 = row3.createCell(5);
			cell2.setCellValue(excludedEmployee);
			cell2.setCellStyle(cellStyle112);

			 grossSalary = epfObj[2] != null ? (new BigDecimal(epfObj[2].toString()))
					: null;
			 sheet.addMergedRegion(new CellRangeAddress(4, 4, 5, 10));
			Cell cell3 = row4.createCell(5);
		
			if(grossSalary!=null) {
			cell3.setCellValue(grossSalary.doubleValue());
			cell3.setCellStyle(addZeroLeft);
			}
			else
			{
				cell3.setCellValue(new BigDecimal(0.00).doubleValue());
				cell3.setCellStyle(addZeroLeft);
			}
			
			
		}
		Row headerRow = sheet.createRow(5);

		// Creating cells
		for (int i = 0; i < columns.length; i++) {
			Cell cell = headerRow.createCell(i);
			cell.setCellValue(columns[i]);
			cell.setCellStyle(headerCellStyle);

		}

		// Create Cell Style for formatting Date
		CellStyle dateCellStyle = workbook.createCellStyle();
		dateCellStyle.setDataFormat(createHelper.createDataFormat().getFormat("dd-MM-yyyy"));
		BigDecimal sumEarning = new BigDecimal(0);

		int rowNum = 6;
		int srNo = 1;
		BigDecimal sumGrossSalary = new BigDecimal(0);
		BigDecimal sumESI = new BigDecimal(0);
		for (ReportPayOutDTO reportPayOut : reportPayOutList) {
			Row row = sheet.createRow(rowNum++);
			row.createCell(0).setCellValue(srNo++);
			row.createCell(1).setCellValue(reportPayOut.getEmployeeCode());

			if (reportPayOut.getEsiNo() != null || reportPayOut.getEsiNo() == "")
				row.createCell(2).setCellValue(reportPayOut.getEsiNo());
			else
				row.createCell(2).setCellValue("Under Process");
			row.createCell(3).setCellValue(reportPayOut.getName());
			row.createCell(4).setCellValue(reportPayOut.getFatherName());
			row.createCell(5).setCellValue(reportPayOut.getNominee());
			row.createCell(6).setCellValue(reportPayOut.getNomineeRelation());

			Cell dateOfBirthCell = row.createCell(7);
			dateOfBirthCell.setCellValue(reportPayOut.getDOB());
			dateOfBirthCell.setCellStyle(dateCellStyle);

			row.createCell(8).setCellValue(reportPayOut.getGender());

			Cell dateOfJoiningCell = row.createCell(9);
			dateOfJoiningCell.setCellValue(reportPayOut.getEsicJoining());
			dateOfJoiningCell.setCellStyle(dateCellStyle);
			
			row.createCell(10).setCellValue(convertBigdecimalToDouble(reportPayOut.getPresense()));
			sumGrossSalary = sumGrossSalary
					.add((reportPayOut.getGrossSalary() != null) ? reportPayOut.getGrossSalary() : BigDecimal.ZERO);
            Cell cellgrossSalary = row.createCell(11);
			cellgrossSalary.setCellValue(reportPayOut.getGrossSalary().doubleValue());
			cellgrossSalary.setCellStyle(addZero1);		
			
			sumEarning = sumEarning
					.add((reportPayOut.getTotalEarning() != null) ? reportPayOut.getTotalEarning() : BigDecimal.ZERO);
			Cell cellEarning=row.createCell(12);
			cellEarning.setCellValue(reportPayOut.getTotalEarning().doubleValue());
			cellEarning.setCellStyle(addZero1);
			
			sumESI = sumESI
					.add((reportPayOut.getEsi_Employee() != null) ? reportPayOut.getEsi_Employee() : BigDecimal.ZERO);
			Cell cell1=row.createCell(13);
			cell1.setCellValue(convertBigdecimalToDouble(reportPayOut.getEsi_Employee()));
			cell1.setCellStyle(addZero1);

		}
		headerCellStyle.setVerticalAlignment(VerticalAlignment.JUSTIFY);
		Row row = sheet.createRow(rowNum);
		for (int i = 0; i < 11; i++) {
			Cell cell11 = row.createCell(i);
			cell11.setCellStyle(headerCellStyle1);

		}
		Cell cell = row.createCell(11);
		cell.setCellValue(sumGrossSalary.doubleValue());
		cell.setCellStyle(addZeroBold);

		Cell cell1 = row.createCell(12);
		cell1.setCellValue(sumEarning.doubleValue());
		cell1.setCellStyle(addZeroBold);

		Cell cell2 = row.createCell(13);
		cell2.setCellValue(sumESI.doubleValue());
		cell2.setCellStyle(addZeroBold);

		Row rowNew = sheet.createRow(rowNum + 1);
		sheet.addMergedRegion(new CellRangeAddress(rowNum + 1, rowNum + 1, 8, 12));
		Cell cell3 = rowNew.createCell(8);

		cell3.setCellValue("EMPLOYER CONTRIBUTION " + esi.getEmployerPer());
		cell3.setCellStyle(headerCellStyleEmp);

		BigDecimal emoPer = (sumEarning.multiply(esi.getEmployerPer())).divide(new BigDecimal(100));
		Cell cell4 = rowNew.createCell(13);
		cell4.setCellValue(convertBigdecimalToDouble(emoPer));
		cell4.setCellStyle(headerCellStyleEmp);

		Row rowNew1 = sheet.createRow(rowNum + 2);
		sheet.addMergedRegion(new CellRangeAddress(rowNum + 2, rowNum + 2, 8, 12));
		Cell cell5 = rowNew1.createCell(8);

		cell5.setCellValue("TOTAL AMOUNT");
		cell5.setCellStyle(headerCellStyleEmp);

		Cell cell6 = rowNew1.createCell(13);
		cell6.setCellValue(convertBigdecimalToDouble(sumESI.add(emoPer)));
		cell6.setCellStyle(headerCellStyleEmp);

		// Resize all columns to fit the content size
		for (int i = 0; i < columns.length; i++) {
			sheet.autoSizeColumn(i);
		}

		/*
		 * // Write the output to a file FileOutputStream fileOut = new
		 * FileOutputStream("E:\\poi-generated-file.xlsx"); workbook.write(fileOut);
		 * fileOut.close();
		 */
		return workbook;
	}

	public static Workbook payoutReport(List<ReportPayOut> reportPayOutList, String[] columns, String processMonth,
			String departmentName) throws IOException, InvalidFormatException {

		Workbook workbook = new XSSFWorkbook();
		CreationHelper createHelper = workbook.getCreationHelper();

		Sheet sheet = workbook.createSheet("Payroll Report");

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

		CellStyle rowCellStyle = workbook.createCellStyle();
		rowCellStyle.setFont(rowFont);

		CellStyle dateCellStyle = workbook.createCellStyle();
		dateCellStyle.setDataFormat(createHelper.createDataFormat().getFormat("dd-MM-yyyy"));

		Row row = sheet.createRow(0);

		Cell paySheet = row.createCell(0);
		paySheet.setCellValue("Pay Sheet");
		paySheet.setCellStyle(rowCellStyle);
		Cell dept = row.createCell(2);
		dept.setCellValue("Department:");
		dept.setCellStyle(rowCellStyle);
		Cell paySheet1 = row.createCell(3);
		if (departmentName != null)
			paySheet1.setCellValue(departmentName);
		else
			paySheet1.setCellValue("All Department");

		Row row1 = sheet.createRow(1);
		String month = processMonth.substring(0, 3);
		int year = Integer.valueOf(processMonth.substring(4, 8));

		row1.createCell(0).setCellValue(DateUtils.createDate("01-" + processMonth, month, year));

		// row1.createCell(0).setCellValue(processMonth);

		Row headerRow = sheet.createRow(2);

		for (int i = 0; i < columns.length; i++) {
			Cell cell = headerRow.createCell(i);
			cell.setCellValue(columns[i]);
			cell.setCellStyle(headerCellStyle);
		}

		int rowNum = 3;
		for (ReportPayOut reportPayOut : reportPayOutList) {
			Row row2 = sheet.createRow(rowNum++);

			row2.createCell(0).setCellValue(reportPayOut.getName());
			row2.createCell(1).setCellValue(reportPayOut.getEmployeeCode());
			row2.createCell(2).setCellValue(reportPayOut.getBankName());
			row2.createCell(3).setCellValue(reportPayOut.getAccountNumber());

			Cell dateOfJoining = row2.createCell(4);
			dateOfJoining.setCellValue(reportPayOut.getDateOfJoining());
			dateOfJoining.setCellStyle(dateCellStyle);

			row2.createCell(5).setCellValue(convertBigdecimalToDouble(reportPayOut.getBasic()));
			row2.createCell(6).setCellValue(convertBigdecimalToDouble(reportPayOut.getDearnessAllowance()));
			row2.createCell(7).setCellValue(convertBigdecimalToDouble(reportPayOut.getConveyanceAllowance()));
			row2.createCell(8).setCellValue(convertBigdecimalToDouble(reportPayOut.getHra()));
			row2.createCell(9).setCellValue(convertBigdecimalToDouble(reportPayOut.getMedicalAllowance()));
			row2.createCell(10).setCellValue(convertBigdecimalToDouble(reportPayOut.getAdvanceBonus()));
			row2.createCell(11).setCellValue(convertBigdecimalToDouble(reportPayOut.getSpecialAllowance()));
			row2.createCell(12).setCellValue(convertBigdecimalToDouble(reportPayOut.getCompanyBenefits()));
			row2.createCell(13).setCellValue(convertBigdecimalToDouble(reportPayOut.getOtherAllowance()));
			row2.createCell(14).setCellValue(convertBigdecimalToDouble(reportPayOut.getGrossSalary()));
			row2.createCell(15).setCellValue(convertBigdecimalToDouble(reportPayOut.getAbsense()));
			row2.createCell(16).setCellValue(convertBigdecimalToDouble(reportPayOut.getCasualleave()));
			row2.createCell(17).setCellValue(convertBigdecimalToDouble(reportPayOut.getSeekleave()));
			row2.createCell(18).setCellValue(convertBigdecimalToDouble(reportPayOut.getPaidleave()));
			row2.createCell(19).setCellValue(convertBigdecimalToDouble(reportPayOut.getPresense()));
			row2.createCell(20).setCellValue(convertBigdecimalToDouble(reportPayOut.getPublicholidays()));
			row2.createCell(21).setCellValue(convertBigdecimalToDouble(reportPayOut.getWeekoff()));
			// row2.createCell(22).setCellValue(convertBigdecimalToDouble(reportPayOut.getOvertime()));
			row2.createCell(22).setCellValue(convertBigdecimalToDouble(reportPayOut.getPayDays()));
			row2.createCell(23).setCellValue(convertBigdecimalToDouble(reportPayOut.getBasicEarning()));
			row2.createCell(24).setCellValue(convertBigdecimalToDouble(reportPayOut.getDearnessAllowanceEarning()));
			row2.createCell(25).setCellValue(convertBigdecimalToDouble(reportPayOut.getConveyanceAllowanceEarning()));
			row2.createCell(26).setCellValue(convertBigdecimalToDouble(reportPayOut.getHRAEarning()));
			row2.createCell(27).setCellValue(convertBigdecimalToDouble(reportPayOut.getMedicalAllowanceEarning()));
			row2.createCell(28).setCellValue(convertBigdecimalToDouble(reportPayOut.getAdvanceBonusEarning()));
			row2.createCell(29).setCellValue(convertBigdecimalToDouble(reportPayOut.getSpecialAllowanceEarning()));
			row2.createCell(30).setCellValue(convertBigdecimalToDouble(reportPayOut.getCompanyBenefitsEarning()));
			row2.createCell(31).setCellValue(convertBigdecimalToDouble(reportPayOut.getOtherAllowanceEarning()));
			row2.createCell(32).setCellValue(convertBigdecimalToDouble(reportPayOut.getTotalEarning()));
			row2.createCell(33).setCellValue(convertBigdecimalToDouble(reportPayOut.getLoan()));
			row2.createCell(34).setCellValue(convertBigdecimalToDouble(reportPayOut.getOtherDeduction()));
			row2.createCell(35).setCellValue(convertBigdecimalToDouble(reportPayOut.getProvidentFundEmployee()));
			row2.createCell(36).setCellValue(convertBigdecimalToDouble(reportPayOut.getESI_Employee()));
			row2.createCell(37).setCellValue(convertBigdecimalToDouble(reportPayOut.getPt()));
			row2.createCell(38).setCellValue(convertBigdecimalToDouble(reportPayOut.getTds()));
			row2.createCell(39).setCellValue(convertBigdecimalToDouble(reportPayOut.getTotalDeduction()));
			row2.createCell(40).setCellValue(convertBigdecimalToDouble(reportPayOut.getNetPayableAmount()));
		}

		// Resize all columns to fit the content size
		for (int i = 0; i < columns.length; i++) {
			sheet.autoSizeColumn(i);
		}

		return workbook;
	}

	public static Workbook trfReport(List<ReportPayOut> reportPayOutList, String[] columns, String processMonth,
			String departmentName, Map<Long, Department> hashMapReport) throws IOException, InvalidFormatException {

		Workbook workbook = new XSSFWorkbook();
		CreationHelper createHelper = workbook.getCreationHelper();

		Sheet sheet = workbook.createSheet("TRF Report");

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

		CellStyle rowCellStyle = workbook.createCellStyle();
		rowCellStyle.setFont(rowFont);

		CellStyle dateCellStyle = workbook.createCellStyle();
		dateCellStyle.setDataFormat(createHelper.createDataFormat().getFormat("dd/MM/yyyy"));

		Row row = sheet.createRow(0);
		Cell paySheet = row.createCell(0);
		paySheet.setCellValue("TRF Sheet");
		paySheet.setCellStyle(rowCellStyle);
		sheet.addMergedRegion(new CellRangeAddress(0, 0, 2, 3));
		Cell dept = row.createCell(2);
		Cell paySheet1 = row.createCell(2);
		if (departmentName != null)
			paySheet1.setCellValue("Department:" + departmentName);
		else
			paySheet1.setCellValue("Department:" + "All ");
		dept.setCellStyle(rowCellStyle);

		Row headerRow = sheet.createRow(1);

		for (int i = 0; i < columns.length; i++) {
			Cell cell = headerRow.createCell(i);
			cell.setCellValue(columns[i]);
			cell.setCellStyle(headerCellStyle);
		}

		int rowNum = 2;
		for (ReportPayOut reportPayOut : reportPayOutList) {
			Row row1 = sheet.createRow(rowNum++);

			row1.createCell(0).setCellValue(reportPayOut.getName());
			row1.createCell(1).setCellValue(reportPayOut.getEmployeeCode());
			row1.createCell(2).setCellValue(reportPayOut.getBankName());
			row1.createCell(3).setCellValue(reportPayOut.getAccountNumber());
			row1.createCell(4).setCellValue(convertBigdecimalToDouble(reportPayOut.getNetPayableAmount()));
			row1.createCell(5).setCellValue(processMonth);
			Department department = getDesignationId(hashMapReport, reportPayOut.getDepartmentId());
			row1.createCell(6).setCellValue(department.getDepartmentName());
		}

		// Resize all columns to fit the content size
		for (int i = 0; i < columns.length; i++) {
			sheet.autoSizeColumn(i);
		}

		return workbook;
	}

	public static Workbook epfReport(List<ReportPayOutDTO> reportPayOutList, List<Object[]> consulatantList, String[] columns, String processMonth,
			Company company, Epf epf) throws IOException, InvalidFormatException {

		Workbook workbook = new XSSFWorkbook(); // new HSSFWorkbook() for generating `.xls` file

		CreationHelper createHelper = workbook.getCreationHelper();

		Sheet sheet = workbook.createSheet("PF Working for Consultant");

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

		CellStyle date = workbook.createCellStyle();
		date.setDataFormat(createHelper.createDataFormat().getFormat("0.00"));
		date.setAlignment(HorizontalAlignment.LEFT);
		date.setVerticalAlignment(VerticalAlignment.CENTER);
		
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
		sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 14));
		Cell cellCom = row0.createCell(0);
		cellCom.setCellValue("PF Working - " + processMonth);
		cellCom.setCellStyle(processMonthTitle);

		Row row1 = sheet.createRow(1);
		sheet.addMergedRegion(new CellRangeAddress(1, 1, 0, 14));
		row1.createCell(0).setCellValue("PF CODE -" + company.getEpfNo());
		 
		Row row2 = sheet.createRow(2);
		sheet.addMergedRegion(new CellRangeAddress(2, 2, 0, 4));
		row2.createCell(0).setCellValue("Total Number of Employees ");
		//row2.createCell(5).setCellValue();

		Row row3 = sheet.createRow(3);
		sheet.addMergedRegion(new CellRangeAddress(3, 3, 0, 4));
		row3.createCell(0).setCellValue("Total Number of Excluded Employees");

		Row row4 = sheet.createRow(4);
		sheet.addMergedRegion(new CellRangeAddress(4, 4, 0, 4));
		row4.createCell(0).setCellValue("Gross Wages of Excluded Employees");
		
		
	
		BigDecimal grossSalary = new BigDecimal(0);
		for (Object[] epfObj : consulatantList) {
		
			Integer totalNoOfEmployee = epfObj[0] != null ? Integer.parseInt(epfObj[0].toString()) : null;
			sheet.addMergedRegion(new CellRangeAddress(2, 2, 5, 10));
			Cell cell1 = row2.createCell(5);
			cell1.setCellValue(totalNoOfEmployee);
			cell1.setCellStyle(cellStyle112);

			Integer excludedEmployee = epfObj[1] != null ? Integer.parseInt(epfObj[1].toString()) : null;
			sheet.addMergedRegion(new CellRangeAddress(3, 3, 5, 10));
			Cell cell2 = row3.createCell(5);
			cell2.setCellValue(excludedEmployee);
			cell2.setCellStyle(cellStyle112);

			 grossSalary = epfObj[2] != null ? (new BigDecimal(epfObj[2].toString()))
					: null;
			 sheet.addMergedRegion(new CellRangeAddress(4, 4, 5, 10));
			Cell cell3 = row4.createCell(5);
//			cell3.setCellStyle(addZero);
			
			if(grossSalary!=null) {
				cell3.setCellValue(grossSalary.doubleValue());
				cell3.setCellStyle(addZero);
			}else {
				cell3.setCellValue(new BigDecimal(0.00).doubleValue());
				cell3.setCellStyle(addZero);
			}
			
				
			
		}

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
		int srNo = 1;
		BigDecimal totalAmount = new BigDecimal(0);
		BigDecimal sumGrossSalary = new BigDecimal(0);
		BigDecimal sumPfBasic = new BigDecimal(0);
		BigDecimal sumAllowance = new BigDecimal(0);
		BigDecimal sumEarnGrossSalary = new BigDecimal(0);
		BigDecimal sumEarnBasic = new BigDecimal(0);
		BigDecimal sumPensionSalary = new BigDecimal(0);
		BigDecimal sumEarnAllowance = new BigDecimal(0);
		BigDecimal sumDeduction = new BigDecimal(0);

		if (reportPayOutList != null) {

			for (ReportPayOutDTO reportPayOut : reportPayOutList) {

				Row row = sheet.createRow(rowNum++);
				row.createCell(0).setCellValue(srNo++);
				row.createCell(1).setCellValue(reportPayOut.getEmployeeCode());
				if (reportPayOut.getUnNo() != null)
					row.createCell(2).setCellValue(reportPayOut.getUnNo());
				else
					row.createCell(2).setCellValue("Under Process");
				/*
				 * if(reportPayOut.getPFNumber()!=null)
				 * row.createCell(2).setCellValue(reportPayOut.getPFNumber()); else
				 * row.createCell(2).setCellValue("Under Process");
				 */
				row.createCell(3).setCellValue(reportPayOut.getName());
				row.createCell(4).setCellValue(reportPayOut.getFatherName());
				row.createCell(5).setCellValue(reportPayOut.getNominee());
				row.createCell(6).setCellValue(reportPayOut.getNomineeRelation());
				Cell dateOfBirthCell = row.createCell(7);
				dateOfBirthCell.setCellValue(reportPayOut.getDOB());
				dateOfBirthCell.setCellStyle(dateCellStyle);
				// reportPayOut.
				row.createCell(8).setCellValue(reportPayOut.getGender());

				Cell epfJoiningCell = row.createCell(9);
				epfJoiningCell.setCellValue(reportPayOut.getEpfJoining());
				epfJoiningCell.setCellStyle(dateCellStyle);
				row.createCell(10).setCellValue(reportPayOut.getMaritalStatus());
				row.createCell(11).setCellValue(reportPayOut.getAccountNumber());
				row.createCell(12).setCellValue(reportPayOut.getIfscCode());
				row.createCell(13).setCellValue(reportPayOut.getAadharNo());
				row.createCell(14).setCellValue(reportPayOut.getPanNo());
				row.createCell(15).setCellValue(reportPayOut.getMobNo());
				row.createCell(16).setCellValue(reportPayOut.getEmail());
				
				sumGrossSalary = sumGrossSalary .add((reportPayOut.getGrossSalary() != null) ? reportPayOut.getGrossSalary() : BigDecimal.ZERO);
				
//				row.createCell(17).setCellValue(convertBigdecimalToDouble(reportPayOut.getGrossSalary()));
				    Cell cellgrossSalary = row.createCell(17);
					cellgrossSalary.setCellValue(reportPayOut.getGrossSalary().doubleValue());
					cellgrossSalary.setCellStyle(addZero1);	
				
//				if (reportPayOut.getDearnessAllowance() != null && reportPayOut.getBasic() != null) {
//					sumPfBasic = sumPfBasic.add(reportPayOut.getBasic().add(reportPayOut.getDearnessAllowance()));
//					row.createCell(18).setCellValue(convertBigdecimalToDouble(
//							reportPayOut.getBasic().add(reportPayOut.getDearnessAllowance())));
//				} else {
				//stdEpfWagesAmount=reportPayOut.getBasic()
					sumPfBasic = sumPfBasic.add((reportPayOut.getBasic() != null) ? reportPayOut.getBasic() : BigDecimal.ZERO);
					
					//row.createCell(18).setCellValue(convertBigdecimalToDouble(reportPayOut.getBasic()));
					Cell cellBasic = row.createCell(18);
					cellBasic.setCellValue(reportPayOut.getBasic().doubleValue());
					cellBasic.setCellStyle(addZero1);	
					
//				}

					//stdEpfWagesAmount=reportPayOut.getBasic()
				sumAllowance = sumAllowance.add(reportPayOut.getGrossSalary()
						.subtract(((reportPayOut.getBasic() != null) ? reportPayOut.getBasic() : BigDecimal.ZERO)));
//				row.createCell(19)
//						.setCellValue(convertBigdecimalToDouble(reportPayOut.getGrossSalary()
//								.subtract((reportPayOut.getProvidentFundEmployee() != null)
//										? reportPayOut.getProvidentFundEmployee()
//										: BigDecimal.ZERO)));

				
//				row.createCell(19)
//				.setCellValue(convertBigdecimalToDouble(reportPayOut.getGrossSalary().subtract(((reportPayOut.getBasic() != null) ? reportPayOut.getBasic() : BigDecimal.ZERO))));
				Cell cellepf = row.createCell(19);
				cellepf.setCellValue(reportPayOut.getGrossSalary().subtract(((reportPayOut.getBasic() != null) ? reportPayOut.getBasic() : BigDecimal.ZERO)).doubleValue());
				cellepf.setCellStyle(addZero1);	
				
				
				row.createCell(20).setCellValue(convertBigdecimalToDouble(reportPayOut.getPayDays()));
				row.createCell(21).setCellValue(convertBigdecimalToDouble(reportPayOut.getAbsense()));
				
				sumEarnGrossSalary = sumEarnGrossSalary.add(
						(reportPayOut.getTotalEarning() != null) ? reportPayOut.getTotalEarning() : BigDecimal.ZERO);
			//	row.createCell(22).setCellValue(convertBigdecimalToDouble(reportPayOut.getTotalEarning()));
				Cell cellEarning = row.createCell(22);
				cellEarning.setCellValue(reportPayOut.getTotalEarning().doubleValue());
				cellEarning.setCellStyle(addZero1);	
				
				
				//EarnedStdEpfWagesAmount=reportPayOut.getBasicEarning()
				sumEarnBasic = sumEarnBasic.add(
						((reportPayOut.getBasicEarning() != null) ? reportPayOut.getBasicEarning() : BigDecimal.ZERO));
				
				//EarnedStdEpfWagesAmount=reportPayOut.getBasicEarning()
//				row.createCell(23).setCellValue(convertBigdecimalToDouble(
//						((reportPayOut.getBasicEarning() != null) ? reportPayOut.getBasicEarning() : BigDecimal.ZERO)));
				Cell cellBasicEarning = row.createCell(23);
				cellBasicEarning.setCellValue(((reportPayOut.getBasicEarning() != null) ? reportPayOut.getBasicEarning() : BigDecimal.ZERO).doubleValue());
				cellBasicEarning.setCellStyle(addZero1);	
				
				
				//EarnedStdEpfWagesAmount=reportPayOut.getTotalEarning()+otherearning
				sumEarnAllowance = sumEarnAllowance
						.add(reportPayOut.getTotalEarning().subtract(reportPayOut.getBasicEarning()));
				
				//EarnedStdEpfWagesAmount=reportPayOut.getTotalEarning()+otherearning
//				row.createCell(24).setCellValue(convertBigdecimalToDouble(reportPayOut.getTotalEarning().subtract((reportPayOut.getBasicEarning()))));
				Cell cellOtherEarning = row.createCell(24);
				cellOtherEarning.setCellValue(reportPayOut.getTotalEarning().subtract((reportPayOut.getBasicEarning())).doubleValue());
				cellOtherEarning.setCellStyle(addZero1);	
				
				sumPensionSalary = sumPensionSalary
						.add((reportPayOut.getPensionEarningSalary() != null) ? reportPayOut.getPensionEarningSalary()
								: BigDecimal.ZERO);
				
//				row.createCell(25).setCellValue(convertBigdecimalToDouble(reportPayOut.getPensionEarningSalary()));
				Cell cellPension = row.createCell(25);
				cellPension.setCellValue(reportPayOut.getPensionEarningSalary().doubleValue());
				cellPension.setCellStyle(addZero1);	
				
				sumDeduction = sumDeduction
						.add((reportPayOut.getProvidentFundEmployee() != null) ? reportPayOut.getProvidentFundEmployee()
								: BigDecimal.ZERO);
				
				//row.createCell(26).setCellValue(convertBigdecimalToDouble(reportPayOut.getProvidentFundEmployee()));
				Cell cellPf = row.createCell(26);
				cellPf.setCellValue(reportPayOut.getProvidentFundEmployee().doubleValue());
				cellPf.setCellStyle(addZero1);	

			}
		}

		headerCellStyle.setVerticalAlignment(VerticalAlignment.JUSTIFY);
		Row row = sheet.createRow(rowNum);
		for (int i = 0; i < 17; i++) {
			Cell cellblank = row.createCell(i);
			cellblank.setCellStyle(headerCellStyle1);
		}
		Cell cell = row.createCell(17);
		cell.setCellValue(convertBigdecimalToDouble(sumGrossSalary));
		cell.setCellStyle(addZeroBold);

		Cell cell1 = row.createCell(18);
		cell1.setCellValue(convertBigdecimalToDouble(sumPfBasic));
		cell1.setCellStyle(addZeroBold);

		Cell cell2 = row.createCell(19);
		cell2.setCellValue(convertBigdecimalToDouble(sumAllowance));
		cell2.setCellStyle(addZeroBold);

		Cell cell11 = row.createCell(20);
		cell11.setCellStyle(headerCellStyle1);

		Cell cell111 = row.createCell(21);
		cell111.setCellStyle(headerCellStyle1);

		Cell cell3 = row.createCell(22);
		cell3.setCellValue(convertBigdecimalToDouble(sumEarnGrossSalary));
		cell3.setCellStyle(addZeroBold);

		Cell cell4 = row.createCell(23);
		cell4.setCellValue(convertBigdecimalToDouble(sumEarnBasic));
		cell4.setCellStyle(addZeroBold);

		Cell cell5 = row.createCell(24);
		cell5.setCellValue(convertBigdecimalToDouble(sumEarnAllowance));
		cell5.setCellStyle(addZeroBold);

		Cell cell6 = row.createCell(25);
		cell6.setCellValue(convertBigdecimalToDouble(sumPensionSalary));
		cell6.setCellStyle(addZeroBold);

		Cell cell7 = row.createCell(26);
		cell7.setCellValue(convertBigdecimalToDouble(sumDeduction));
		cell7.setCellStyle(addZeroBold);

		Row rowNew1 = sheet.createRow(rowNum + 2);
		sheet.addMergedRegion(new CellRangeAddress(rowNum + 2, rowNum + 2, 21, 25));
		Cell cell18 = rowNew1.createCell(21);
		cell18.setCellValue("EMPLOYER SHARE ");
		cell18.setCellStyle(headerCellStyleEmp);

		Cell cell19 = rowNew1.createCell(26);
		cell19.setCellValue(convertBigdecimalToDouble(sumDeduction));
		cell19.setCellStyle(headerCellStyleEmp);

		Row rowNew2 = sheet.createRow(rowNum + 3);
		sheet.addMergedRegion(new CellRangeAddress(rowNum + 3, rowNum + 3, 21, 25));
		Cell cell20 = rowNew2.createCell(21);
		cell20.setCellValue("ADM CHARGES " + epf.getAdminPer() + "% -:");
		cell20.setCellStyle(headerCellStyleEmp);

		Cell cell21 = rowNew2.createCell(26);
		BigDecimal epfAdm = (sumEarnBasic.multiply(epf.getAdminPer())).divide(new BigDecimal(100));
		BigDecimal fiveHundred = new BigDecimal(500);
		epfAdm = ((epfAdm).compareTo(fiveHundred) < 0) ? fiveHundred : epfAdm;
		cell21.setCellValue(convertBigdecimalToDouble(epfAdm));
		cell21.setCellStyle(headerCellStyleEmp);

		Row rowNew3 = sheet.createRow(rowNum + 4);
		sheet.addMergedRegion(new CellRangeAddress(rowNum + 4, rowNum + 4, 21, 25));
		Cell cell22 = rowNew3.createCell(21);
		cell22.setCellValue("EDLI CHARGES " + epf.getEdliPer() + "% -:");
		cell22.setCellStyle(headerCellStyleEmp);

		sheet.addMergedRegion(new CellRangeAddress(rowNum + 4, rowNum + 4, 6, 7));
		Cell cell23 = rowNew3.createCell(26);
		BigDecimal epfEDLI = (sumPensionSalary.multiply(epf.getEdliPer())).divide(new BigDecimal(100));
		cell23.setCellValue(convertBigdecimalToDouble(epfEDLI));
		cell23.setCellStyle(headerCellStyleEmp);

		Row rowNew4 = sheet.createRow(rowNum + 5);
		sheet.addMergedRegion(new CellRangeAddress(rowNum + 5, rowNum + 5, 21, 25));
		Cell cell24 = rowNew4.createCell(21);
		cell24.setCellValue("EDLI EXP CHARGES " + epf.getEdliExpPer() + "% -:");
		cell24.setCellStyle(headerCellStyleEmp);

		Cell cell25 = rowNew4.createCell(26);
		BigDecimal epfEDLIExp = (sumPensionSalary.multiply(epf.getEdliExpPer())).divide(new BigDecimal(100));

		BigDecimal twoHundred = new BigDecimal(200);

		epfEDLIExp = ((epfEDLIExp).compareTo(twoHundred) < 0) ? twoHundred : epfEDLIExp;
		cell25.setCellValue(convertBigdecimalToDouble(epfEDLIExp));
		cell25.setCellStyle(headerCellStyleEmp);

		Row rowNew5 = sheet.createRow(rowNum + 6);

		totalAmount = totalAmount.add(totalAmount);
		BigDecimal totalAmount2 = totalAmount.add(totalAmount);

		BigDecimal totalAmount3 = totalAmount2.add(sumDeduction);
		BigDecimal totalAmount4 = totalAmount3.add(epfAdm);
		BigDecimal totalAmount5 = totalAmount4.add(epfEDLI);
		BigDecimal totalAmount6 = totalAmount5.add(epfEDLIExp);

		sheet.addMergedRegion(new CellRangeAddress(rowNum + 6, rowNum + 6, 21, 25));
		Cell cell26 = rowNew5.createCell(21);
		cell26.setCellValue("TOTAL AMOUNT -:");

		cell26.setCellStyle(headerCellStyleEmp);

		Cell cell27 = rowNew5.createCell(26);

		cell27.setCellValue(convertBigdecimalToDouble(totalAmount6));
		cell27.setCellStyle(headerCellStyleEmp);

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
	
	
	public static Workbook esicEmployeeWiseReport(List<ReportPayOutDTO> annualSummaryList, String[] columns,
			List<Object[]> employeeEsic) {
		Workbook workbook = new XSSFWorkbook(); // new HSSFWorkbook() for generating `.xls` file

		/*
		 * CreationHelper helps us create instances for various things like DataFormat,
		 * Hyperlink, RichTextString etc, in a format (HSSF, XSSF) independent way
		 */
		CreationHelper createHelper = workbook.getCreationHelper();

		// Create a Sheet
		Sheet sheet = workbook.createSheet("ESIC Statement");

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
		headerCellStyle11.setAlignment(HorizontalAlignment.RIGHT);
		headerCellStyle11.setVerticalAlignment(VerticalAlignment.CENTER);
		
		CellStyle headerCellStyle111 = workbook.createCellStyle();
		headerCellStyle111.setFont(headerFont);
		headerCellStyle111.setAlignment(HorizontalAlignment.LEFT);
		headerCellStyle111.setVerticalAlignment(VerticalAlignment.CENTER);

		CellStyle headerCellStyleEmp = workbook.createCellStyle();
		headerCellStyleEmp.setFont(headerFont);
		headerCellStyleEmp.setAlignment(HorizontalAlignment.RIGHT);
		headerCellStyleEmp.setVerticalAlignment(VerticalAlignment.CENTER);

		CellStyle title = workbook.createCellStyle();
		title.setFont(headerFont);
		title.setAlignment(HorizontalAlignment.CENTER);
		title.setVerticalAlignment(VerticalAlignment.CENTER);
		
		
		CellStyle addZero = workbook.createCellStyle();
		addZero.setDataFormat(createHelper.createDataFormat().getFormat("0.00"));
		addZero.setAlignment(HorizontalAlignment.RIGHT);
		addZero.setVerticalAlignment(VerticalAlignment.CENTER);
		
		CellStyle addZeroLeft = workbook.createCellStyle();
		addZeroLeft.setDataFormat(createHelper.createDataFormat().getFormat("0.00"));
		addZeroLeft.setAlignment(HorizontalAlignment.LEFT);
		addZeroLeft.setVerticalAlignment(VerticalAlignment.CENTER);
		
		CellStyle addZero1 = workbook.createCellStyle();
		addZero1.setDataFormat(createHelper.createDataFormat().getFormat("0.00"));
		addZero1.setAlignment(HorizontalAlignment.RIGHT);
		addZero1.setVerticalAlignment(VerticalAlignment.CENTER);
		
		CellStyle addZeroBold = workbook.createCellStyle();
		addZeroBold.setDataFormat(createHelper.createDataFormat().getFormat("0.00"));
		addZeroBold.setFont(headerFont);
		addZeroBold.setAlignment(HorizontalAlignment.RIGHT);
		addZeroBold.setVerticalAlignment(VerticalAlignment.CENTER);
		addZeroBold.setFillBackgroundColor(IndexedColors.LIGHT_CORNFLOWER_BLUE.getIndex());
		addZeroBold.setFillPattern(FillPatternType.FINE_DOTS);
		
		CellStyle cellStyle112 = workbook.createCellStyle();
		title.setFont(headerFont);
		title.setAlignment(HorizontalAlignment.CENTER);
		title.setVerticalAlignment(VerticalAlignment.CENTER);
		
		
		CellStyle date = workbook.createCellStyle();
		date.setDataFormat(createHelper.createDataFormat().getFormat("0.00"));
		date.setAlignment(HorizontalAlignment.LEFT);
		date.setVerticalAlignment(VerticalAlignment.CENTER);
		
		CellStyle dateCellStyle = workbook.createCellStyle();
		dateCellStyle.setDataFormat(createHelper.createDataFormat().getFormat("dd-MM-yyyy"));
		
		CellStyle headerCellStyle101 = workbook.createCellStyle();
		headerCellStyle101.setAlignment(HorizontalAlignment.CENTER);
		headerCellStyle101.setVerticalAlignment(VerticalAlignment.CENTER);

		
		// Create Other rows and cells with employees data
		Row row0 = sheet.createRow(0);
		sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 5));
		Cell cellCom = row0.createCell(0);
		cellCom.setCellValue("For Computronics Systems India Pvt. Ltd.  ESIC Contribution Statement " );
		cellCom.setCellStyle(title);

		Row row1 = sheet.createRow(1);
	
		Row row2 = sheet.createRow(2);
	
		Row row3 = sheet.createRow(3);
	
		Row row4 = sheet.createRow(4);
	
		
		for (Object[] epfObj : employeeEsic) {
		
			String name = epfObj[0] != null ?  (String) epfObj[0] : null; 
			sheet.addMergedRegion(new CellRangeAddress(1, 1, 0, 5));
			Cell cell1 = row1.createCell(0);
			cell1.setCellValue(name);
			cell1.setCellStyle(cellStyle112);
			
			
			Date dateOfJoining = epfObj[1] != null ? (Date) epfObj[1] : null;
			sheet.addMergedRegion(new CellRangeAddress(2, 2, 0, 5));
			Cell cell2 = row2.createCell(0);
			cell2.setCellValue("DOJ:"+ dateOfJoining);
			cell2.setCellStyle(dateCellStyle);
			
			
			
			Date startDate = epfObj[2] != null ? (Date) epfObj[2] : null;
			sheet.addMergedRegion(new CellRangeAddress(3, 3, 0, 5));
			Cell cell3 = row3.createCell(0);
			cell3.setCellValue("ESIC Effective from: "+ startDate);
			cell3.setCellStyle(dateCellStyle);
			
			String esicNumber = epfObj[3] != null ?  (String) epfObj[3] : null; 
			sheet.addMergedRegion(new CellRangeAddress(4, 4, 0, 5));
			Cell cell4 = row4.createCell(0);
			cell4.setCellValue("ESIC No: "+ esicNumber);
			cell4.setCellStyle(cellStyle112);
			
			
			
		}
		Row headerRow = sheet.createRow(5);

		// Creating cells
		for (int i = 0; i < columns.length; i++) {
			Cell cell = headerRow.createCell(i);
			cell.setCellValue(columns[i]);
			cell.setCellStyle(headerCellStyle);

		}

	
		BigDecimal sumGrossSalary = new BigDecimal(0);
		BigDecimal sumEsiEmployee = new BigDecimal(0);
		BigDecimal sumEsiEmployer = new BigDecimal(0);
		BigDecimal totalShare = new BigDecimal(0);
		BigDecimal absense = new BigDecimal(0);

		int rowNum = 6;
	
		for (ReportPayOutDTO reportPayOut : annualSummaryList) {
			Row row = sheet.createRow(rowNum++);
			
			row.createCell(0).setCellValue(reportPayOut.getProcessMonth());
		
			
			sumGrossSalary = sumGrossSalary
					.add((reportPayOut.getGrossSalary() != null) ? reportPayOut.getGrossSalary() : BigDecimal.ZERO);
            Cell cellgrossSalary = row.createCell(1);
			cellgrossSalary.setCellValue(reportPayOut.getGrossSalary().doubleValue());
			cellgrossSalary.setCellStyle(addZero1);		
			
			
			sumEsiEmployee = sumEsiEmployee
					.add((reportPayOut.getEsi_Employee() != null) ? reportPayOut.getEsi_Employee() : BigDecimal.ZERO);
			Cell cell1=row.createCell(2);
			cell1.setCellValue(convertBigdecimalToDouble(reportPayOut.getEsi_Employee()));
			cell1.setCellStyle(addZero1);
			
			
			sumEsiEmployer = sumEsiEmployer
					.add((reportPayOut.getEsi_Employer() != null) ? reportPayOut.getEsi_Employer() : BigDecimal.ZERO);
			Cell cell2=row.createCell(3);
			cell2.setCellValue(convertBigdecimalToDouble(reportPayOut.getEsi_Employer()));
			cell2.setCellStyle(addZero1);
			
			totalShare = totalShare
					.add((reportPayOut.getTotalEarning() != null) ? reportPayOut.getTotalEarning() : BigDecimal.ZERO);
			Cell cell3=row.createCell(4);
			cell3.setCellValue(convertBigdecimalToDouble(reportPayOut.getTotalEarning()));
			cell3.setCellStyle(addZero1);
			
			
			absense = absense
					.add((reportPayOut.getAbsense() != null) ? reportPayOut.getAbsense() : BigDecimal.ZERO);
			Cell cell4=row.createCell(5);
			cell4.setCellValue(convertBigdecimalToDouble(reportPayOut.getAbsense()));
			cell4.setCellStyle(addZero1);

		}
		headerCellStyle.setVerticalAlignment(VerticalAlignment.JUSTIFY);
		Row rowNew = sheet.createRow(rowNum);

		Cell cell3 = rowNew.createCell(0);
		cell3.setCellValue("Total ");
		cell3.setCellStyle(title);
		
		Cell cell = rowNew.createCell(1);
		cell.setCellValue(convertBigdecimalToDouble(sumGrossSalary));
		cell.setCellStyle(addZeroBold);

		Cell cell1 = rowNew.createCell(2);
		cell1.setCellValue(convertBigdecimalToDouble(sumEsiEmployee));
		cell1.setCellStyle(addZeroBold);

		Cell cell2 = rowNew.createCell(3);
		cell2.setCellValue(convertBigdecimalToDouble(sumEsiEmployer));
		cell2.setCellStyle(addZeroBold);
		
		Cell cell5 = rowNew.createCell(4);
		cell5.setCellValue(convertBigdecimalToDouble(totalShare));
		cell5.setCellStyle(addZeroBold);
		
		Cell cell4 = rowNew.createCell(5);
		cell4.setCellValue(convertBigdecimalToDouble(absense));
		cell4.setCellStyle(addZeroBold);

	
		// Resize all columns to fit the content size
		for (int i = 0; i < columns.length; i++) {
			sheet.autoSizeColumn(i);
		}

		/*
		 * // Write the output to a file FileOutputStream fileOut = new
		 * FileOutputStream("E:\\poi-generated-file.xlsx"); workbook.write(fileOut);
		 * fileOut.close();
		 */
		return workbook;

	}

	
	
	public static Workbook esicAnnualMonthlyReport(Map<String, List<ReportPayOutDTO>> reportPayOutDtoMap,
			List<ReportPayOutDTO> annualSummaryList, String[] columnsSummery, String[] monthlyColumns,
			Long financialYearId, String financialYear, Company company, List<Object[]> employeeCount) {
		Workbook workbook = new XSSFWorkbook();
		CreationHelper createHelper = workbook.getCreationHelper();

		Sheet summarySheet = workbook.createSheet("Summery");

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
					
					CellStyle addZero3 = workbook.createCellStyle();
					addZero3.setDataFormat(createHelper.createDataFormat().getFormat("0.00"));
					addZero3.setAlignment(HorizontalAlignment.RIGHT);
					addZero3.setVerticalAlignment(VerticalAlignment.CENTER);

					Row row0 = sheet.createRow(0);
					sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 5));
					Cell cellCom = row0.createCell(0);
					cellCom.setCellValue("ESIC-ECR -" + processMonthAnnual);
					cellCom.setCellStyle(headerCellStyle111);

					Row row1 = sheet.createRow(1);
					sheet.addMergedRegion(new CellRangeAddress(1, 1, 0, 10));
					Cell cellAdd = row1.createCell(0);
					cellAdd.setCellValue("ESIC Code -" + company.getEpfNo());
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
					for (int i = 0; i < monthlyColumns.length; i++) {
						Cell cell = headerRow.createCell(i);
						cell.setCellValue(monthlyColumns[i]);
						cell.setCellStyle(headerCellStyle);

					}

					CellStyle dateCellStyle = workbook.createCellStyle();
					dateCellStyle.setDataFormat(createHelper.createDataFormat().getFormat("dd-MM-yyyy"));

					int rowNum = 6;
					BigDecimal sumGrossSalary = new BigDecimal(0);
					BigDecimal esiEmployee = new BigDecimal(0);
					BigDecimal esiEmployer = new BigDecimal(0);
					BigDecimal absense = new BigDecimal(0);
				
					if (reportPayOutDTOList != null) {

						for (ReportPayOutDTO reportPayOut : reportPayOutDTOList) {

							Row row = sheet.createRow(rowNum++);
							
							if (reportPayOut.getEsiNo() != null || reportPayOut.getEsiNo() == "")
								row.createCell(0).setCellValue(reportPayOut.getEsiNo());
							else
								row.createCell(0).setCellValue("Under Process");
							row.createCell(1).setCellValue(reportPayOut.getName());
							
					sumGrossSalary = sumGrossSalary .add((reportPayOut.getGrossSalary() != null) ? reportPayOut.getGrossSalary() : BigDecimal.ZERO);
							   Cell cellgrossSalary = row.createCell(2);
								cellgrossSalary.setCellValue(reportPayOut.getGrossSalary().doubleValue());
								cellgrossSalary.setCellStyle(addZero3);	
								
								esiEmployee = esiEmployee .add((reportPayOut.getEsi_Employee() != null) ? reportPayOut.getEsi_Employee() : BigDecimal.ZERO);
								Cell cell1 = row.createCell(3);
								cell1.setCellValue(reportPayOut.getEsi_Employee().doubleValue());
								cell1.setCellStyle(addZero3);	
								
								esiEmployer = esiEmployer .add((reportPayOut.getEsi_Employer() != null) ? reportPayOut.getEsi_Employer() : BigDecimal.ZERO);
								Cell cell2 = row.createCell(4);
								cell2.setCellValue(reportPayOut.getEsi_Employer().doubleValue());
								cell2.setCellStyle(addZero3);	
								
								
								absense = absense .add((reportPayOut.getAbsense() != null) ? reportPayOut.getAbsense() : BigDecimal.ZERO);
								Cell cell3 = row.createCell(5);
								cell3.setCellValue(reportPayOut.getAbsense().doubleValue());
								cell3.setCellStyle(addZero3);	
							}
						}
					

					headerCellStyle.setVerticalAlignment(VerticalAlignment.JUSTIFY);

					Row rowNew = sheet.createRow(rowNum);
					sheet.addMergedRegion(new CellRangeAddress(rowNum, rowNum, 0, 1));

					Cell cell3 = rowNew.createCell(0);
					cell3.setCellValue("Total ");
					cell3.setCellStyle(headerCellStyle111);
					
					Cell cell = rowNew.createCell(2);
					cell.setCellValue(convertBigdecimalToDouble(sumGrossSalary));
					cell.setCellStyle(addZeroBold);

					Cell cell1 = rowNew.createCell(3);
					cell1.setCellValue(convertBigdecimalToDouble(esiEmployee));
					cell1.setCellStyle(addZeroBold);

					Cell cell2 = rowNew.createCell(4);
					cell2.setCellValue(convertBigdecimalToDouble(esiEmployer));
					cell2.setCellStyle(addZeroBold);
					
					Cell cell4 = rowNew.createCell(4);
					cell4.setCellValue(convertBigdecimalToDouble(absense));
					cell4.setCellStyle(addZeroBold);
			

					
					// Resize all columns to fit the content size
					for (int i = 0; i < monthlyColumns .length; i++) {
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
		summarySheet.addMergedRegion(new CellRangeAddress(0, 1, 0, 5));
		Cell cellCom = row0.createCell(0);
		cellCom.setCellValue(
				"For Computronics Systems India Pvt. Ltd. ESIC Statement for FY -  " + financialYear);
		cellCom.setCellStyle(headerCellStyle101);
		
		Row headerRow = summarySheet.createRow(2);

//		summarySheet.addMergedRegion(new CellRangeAddress(1, 1, 0, 1));
//		Cell month = headerRow.createCell(0);
//		month.setCellValue("Month");
//		month.setCellStyle(headerCellStyle);

		for (int i = 0; i < columnsSummery.length; i++) {
			Cell cell = headerRow.createCell(i);
			cell.setCellValue(columnsSummery[i].toString());
			cell.setCellStyle(headerCellStyle);
		}

		CellStyle dateCellStyle = workbook.createCellStyle();
		dateCellStyle.setDataFormat(createHelper.createDataFormat().getFormat("dd-MM-yyyy"));

		CellStyle dateCellStyles = workbook.createCellStyle();
		dateCellStyles.setDataFormat(createHelper.createDataFormat().getFormat(""));

		int rowNum = 3;
		// BigDecimal sum = BigDecimal.ZERO;

		BigDecimal sumGrossSalary = BigDecimal.ZERO;
		BigDecimal esiEmployee = BigDecimal.ZERO;
		BigDecimal esiEmployer = BigDecimal.ZERO;
		BigDecimal totalShare = BigDecimal.ZERO;
		BigDecimal absense = BigDecimal.ZERO;
		

		if (annualSummaryList != null) {

			for (ReportPayOutDTO reportPayOut : annualSummaryList) {

				Row row = summarySheet.createRow(rowNum++);

				// row.createCell(0).setCellValue(reportPayOut.getProcessMonth());
				Cell cell = row.createCell(0);
				cell.setCellValue(reportPayOut.getProcessMonth());

				sumGrossSalary = sumGrossSalary .add((reportPayOut.getGrossSalary() != null) ? reportPayOut.getGrossSalary() : BigDecimal.ZERO);
				   Cell cellgrossSalary = row.createCell(1);
					cellgrossSalary.setCellValue(reportPayOut.getGrossSalary().doubleValue());
					cellgrossSalary.setCellStyle(addZero);	
					
					esiEmployee = esiEmployee .add((reportPayOut.getEsi_Employee() != null) ? reportPayOut.getEsi_Employee() : BigDecimal.ZERO);
					Cell cell1 = row.createCell(2);
					cell1.setCellValue(reportPayOut.getEsi_Employee().doubleValue());
					cell1.setCellStyle(addZero);	
					
					esiEmployer = esiEmployer .add((reportPayOut.getEsi_Employer() != null) ? reportPayOut.getEsi_Employer() : BigDecimal.ZERO);
					Cell cell2 = row.createCell(3);
					cell2.setCellValue(reportPayOut.getEsi_Employer().doubleValue());
					cell2.setCellStyle(addZero);	
					
				totalShare = totalShare.add(
						(reportPayOut.getTotalEarning() != null) ? reportPayOut.getTotalEarning() : BigDecimal.ZERO);
				Cell cell4 = row.createCell(4);
				cell4.setCellValue(reportPayOut.getTotalEarning().doubleValue());
				cell4.setCellStyle(addZero);

				absense = absense .add((reportPayOut.getAbsense() != null) ? reportPayOut.getAbsense() : BigDecimal.ZERO);
				Cell cell3 = row.createCell(5);
				cell3.setCellValue(reportPayOut.getAbsense().doubleValue());
				cell3.setCellStyle(addZero);

			}
		}

		Row rowNew = summarySheet.createRow(rowNum);
	
	
		summarySheet.addMergedRegion(new CellRangeAddress(rowNum, rowNum, 0, 1));

		Cell cell3 = rowNew.createCell(0);
		cell3.setCellValue("Total ");
		cell3.setCellStyle(headerCellStyle101);
		
		Cell cell = rowNew.createCell(2);
		cell.setCellValue(convertBigdecimalToDouble(sumGrossSalary));
		cell.setCellStyle(addZeroBold);

		Cell cell1 = rowNew.createCell(3);
		cell1.setCellValue(convertBigdecimalToDouble(esiEmployee));
		cell1.setCellStyle(addZeroBold);

		Cell cell2 = rowNew.createCell(4);
		cell2.setCellValue(convertBigdecimalToDouble(esiEmployer));
		cell2.setCellStyle(addZeroBold);
		
		Cell cell4 = rowNew.createCell(4);
		cell4.setCellValue(convertBigdecimalToDouble(absense));
		cell4.setCellStyle(addZeroBold);
			

		for (int i = 0; i < columnsSummery.length; i++) {
			summarySheet.autoSizeColumn(i);
		}

		return workbook;

	}



}
