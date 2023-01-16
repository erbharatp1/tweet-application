package com.csipl.tms.attendanceCalculation.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.csipl.hrms.common.constant.StatusMessage;
import com.csipl.hrms.common.util.DateUtils;
import com.csipl.hrms.service.payroll.AttendanceDTO;
import com.csipl.tms.dto.attendancelog.AttendanceLogDTO;
import com.csipl.tms.dto.attendancelog.AttendanceLogDetailsDTO;
import com.csipl.tms.dto.attendanceregularizationrequest.AttendanceRegularizationRequestDTO;
import com.csipl.tms.dto.deviceinfo.DeviceLogsInfoDTO;
import com.csipl.tms.dto.leave.CompensatoryOffDTO;
import com.csipl.tms.dto.leave.LeaveEntryDTO;

public class AttendanceReportExcelWriter {

	public static Workbook attendanceReport(List<AttendanceLogDetailsDTO> attendanceLogDetailsDtoList, String[] columns)
			throws IOException, InvalidFormatException {
		// Create a Workbook
		Workbook workbook = new XSSFWorkbook(); // new HSSFWorkbook() for generating `.xls` file

		/*
		 * CreationHelper helps us create instances for various things like DataFormat,
		 * Hyperlink, RichTextString etc, in a format (HSSF, XSSF) independent way
		 */
		CreationHelper createHelper = workbook.getCreationHelper();

		// Create a Sheet
		Sheet sheet = workbook.createSheet("Attendance Report");

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
		// Create Other rows and cells with employees data
		Row row0 = sheet.createRow(0);
		sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 2));
		Cell cellCom = row0.createCell(0);
		cellCom.setCellValue("");
		cellCom.setCellStyle(headerCellStyle111);

		Row row1 = sheet.createRow(1);
		sheet.addMergedRegion(new CellRangeAddress(1, 1, 0, 2));
		row1.createCell(0).setCellValue("Present-" + 20 + " Leave-	 " + 5 + " Absent- " + 400);

		// sheet.addMergedRegion(new CellRangeAddress(0,1,1,2));

		// String monthAcronym=processMonth.substring(0, 3);
		Row row2 = sheet.createRow(2);
		sheet.addMergedRegion(new CellRangeAddress(2, 2, 0, 2));
		// row2.createCell(0).setCellValue("A" + "JAN");

		// Create a Row
		Row headerRow = sheet.createRow(3);

		// Creating cells
		for (int i = 0; i < columns.length; i++) {
			Cell cell = headerRow.createCell(i);
			cell.setCellValue(columns[i]);
			cell.setCellStyle(headerCellStyle);

		}

		// Create Cell Style for formatting Date
		CellStyle dateCellStyle = workbook.createCellStyle();
		dateCellStyle.setDataFormat(createHelper.createDataFormat().getFormat("dd-MM-yyyy"));

		int rowNum = 4;
		int srNo = 1;

		for (AttendanceLogDetailsDTO attendanceLogDetailsDto : attendanceLogDetailsDtoList) {
			Row row = sheet.createRow(rowNum++);
			row.createCell(0).setCellValue(srNo++);
			row.createCell(1).setCellValue(attendanceLogDetailsDto.getName());

			row.createCell(2).setCellValue(attendanceLogDetailsDto.getStatus());

			row.createCell(3).setCellValue(attendanceLogDetailsDto.getPunchRecord());
			row.createCell(4).setCellValue(attendanceLogDetailsDto.getLocation());
			row.createCell(5).setCellValue(attendanceLogDetailsDto.getMode());
			row.createCell(6).setCellValue(attendanceLogDetailsDto.getDelayedTime());

		}

		return workbook;
	}

	public static Workbook attendanceNewReport(List<AttendanceLogDetailsDTO> attendanceLogDetailsDtoList,
			String[] columns, String attendDate) throws IOException, InvalidFormatException {

		// Create a Workbook
		Workbook workbook = new XSSFWorkbook(); // new HSSFWorkbook() for generating `.xls` file

		/*
		 * CreationHelper helps us create instances for various things like DataFormat,
		 * Hyperlink, RichTextString etc, in a format (HSSF, XSSF) independent way
		 */
		CreationHelper createHelper = workbook.getCreationHelper();

		// Create a Sheet
		Sheet sheet = workbook.createSheet("Attendance Report");

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
		headerCellStyle1.setAlignment(HorizontalAlignment.CENTER);
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
		// Create Other rows and cells with employees data
//		Row row0 = sheet.createRow(0);
//		sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 2));
//		Cell cellCom = row0.createCell(0);
//		cellCom.setCellValue("");
//		cellCom.setCellStyle(headerCellStyle111);

		// add header format

		Row headerRow0 = sheet.createRow(0);

		sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 18));
		Cell cell0 = headerRow0.createCell(0);
		cell0.setCellValue("Attendance Logs Summary");
		cell0.setCellStyle(headerCellStyle1);

		Row headerRow1 = sheet.createRow(1);
//		String dateFrom = null;
//		SimpleDateFormat s1 = new SimpleDateFormat("dd-MMM-yyyy");
//		dateFrom = s1.format(attendDate);

		sheet.addMergedRegion(new CellRangeAddress(1, 1, 0, 18));
		Cell cell1 = headerRow1.createCell(0);
		cell1.setCellValue(attendDate);
		cell1.setCellStyle(headerCellStyle1);

		sheet.addMergedRegion(new CellRangeAddress(2, 2, 0, 2));
		// row2.createCell(0).setCellValue("A" + "JAN");

		// Create a Row
		Row headerRow = sheet.createRow(3);

		// Creating cells
		for (int i = 0; i < columns.length; i++) {
			Cell cell = headerRow.createCell(i);
			cell.setCellValue(columns[i]);
			cell.setCellStyle(headerCellStyle);

		}

		// Create Cell Style for formatting Date
		CellStyle dateCellStyle = workbook.createCellStyle();
		dateCellStyle.setDataFormat(createHelper.createDataFormat().getFormat("dd-MM-yyyy"));

		int rowNum = 4;
		int srNo = 1;

		for (AttendanceLogDetailsDTO attendanceLogDetailsDto : attendanceLogDetailsDtoList) {
			Row row = sheet.createRow(rowNum++);
			row.createCell(0).setCellValue(srNo++);
			row.createCell(1).setCellValue(attendDate);
			row.createCell(2).setCellValue(attendanceLogDetailsDto.getEmployeeCode());
			row.createCell(3).setCellValue(attendanceLogDetailsDto.getName());
			row.createCell(4).setCellValue(attendanceLogDetailsDto.getDepartmentName());
			row.createCell(5).setCellValue(attendanceLogDetailsDto.getJobLocation());
			row.createCell(6).setCellValue(attendanceLogDetailsDto.getReportingTo());
			row.createCell(7).setCellValue(attendanceLogDetailsDto.getShift());
			row.createCell(8).setCellValue(attendanceLogDetailsDto.getShiftDuration());
			row.createCell(9).setCellValue(attendanceLogDetailsDto.getMode());
			row.createCell(10).setCellValue(attendanceLogDetailsDto.getTimeIn());
			row.createCell(11).setCellValue(attendanceLogDetailsDto.getTimeOut());
			row.createCell(12).setCellValue(attendanceLogDetailsDto.getTotalHours());
			row.createCell(13).setCellValue(attendanceLogDetailsDto.getLeaveStatus());
			row.createCell(14).setCellValue(attendanceLogDetailsDto.getLateBy());
			row.createCell(15).setCellValue(attendanceLogDetailsDto.getLeftEarlyBy());
			row.createCell(16).setCellValue(attendanceLogDetailsDto.getEarlyBefore());
			row.createCell(17).setCellValue(attendanceLogDetailsDto.getLocationIn());
			row.createCell(18).setCellValue(attendanceLogDetailsDto.getLocationOut());

		}

		return workbook;
	}

	// All day time and attendance report
	public static Workbook dayWiseattendanceReport(List<AttendanceLogDTO> attendanceLogDetailsDtoList, String[] columns,
			List<Date> dateColumnList, String[] moreColumns) throws IOException, InvalidFormatException {
		// Create a Workbook
		Workbook workbook = new XSSFWorkbook(); // new HSSFWorkbook() for generating `.xls` file

		/*
		 * CreationHelper helps us create instances for various things like DataFormat,
		 * Hyperlink, RichTextString etc, in a format (HSSF, XSSF) independent way
		 */
		CreationHelper createHelper = workbook.getCreationHelper();

		// Create a Sheet
		Sheet sheet = workbook.createSheet("Attendance Report");

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
		// Create Other rows and cells with employees data
		// Row row0 = sheet.createRow(0);
		// sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 2));
		// Cell cellCom = row0.createCell(0);
		// cellCom.setCellValue("");
		// cellCom.setCellStyle(headerCellStyle111);

		Row row0 = sheet.createRow(0);
		sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 2));
		row0.createCell(0)
				.setCellValue("Attendance Logs Summary  " + attendanceLogDetailsDtoList.get(0).getProcessMonth());

		// sheet.addMergedRegion(new CellRangeAddress(0,1,1,2));

		// String monthAcronym=processMonth.substring(0, 3);
		// Row row2 = sheet.createRow(2);
		// sheet.addMergedRegion(new CellRangeAddress(2, 2, 0, 2));
		// row2.createCell(0).setCellValue("A" + "JAN");

		// Create a Row
		Row headerRow = sheet.createRow(1);
		int cellNumber = 0;
		int dayCell = 0;
		// Creating cells
		for (int i = 0; i < columns.length; i++) {
			Cell cell = headerRow.createCell(i);
			cell.setCellValue(columns[i]);
			cell.setCellStyle(headerCellStyle);
			cellNumber = i;
			dayCell = i;
		}
		// creating dynamic cells to show date
		// Create Cell Style for formatting Date

		int nextNo = 0;
		DateUtils util = new DateUtils();
		CellStyle dateCellStyle = workbook.createCellStyle();
		dateCellStyle.setDataFormat(createHelper.createDataFormat().getFormat("dd-MM-yyyy"));
		for (int j = cellNumber, k = 0; k < dateColumnList.size(); j++, k++) {
			Cell cell = row0.createCell(++cellNumber);
			String d = util.getDateStringWirhYYYYMMDD(dateColumnList.get(k));
			cell.setCellValue(d);
			cell.setCellStyle(dateCellStyle);
			nextNo = j;
		}

		// Creating days cells
		for (int j = dayCell, k = 0; k < dateColumnList.size(); j++, k++) {
			Cell cell = headerRow.createCell(++dayCell);
			String d = util.getDay(dateColumnList.get(k));
			cell.setCellValue(d);
			cell.setCellStyle(headerCellStyle);
		}

		nextNo++;
		// Creating more cells
		for (int i = 0; i < moreColumns.length; i++) {
			Cell cell = headerRow.createCell(++nextNo);
			cell.setCellValue(moreColumns[i]);
			cell.setCellStyle(headerCellStyle);

		}

		int rowNum = 2;
		int srNo = 0;

		for (int i = 0; i < attendanceLogDetailsDtoList.size(); i++) {
			// for (AttendanceLogDTO attendanceLogDtos : attendanceLogDetailsDtoList) {
			Row row = sheet.createRow(rowNum++);
			// row.createCell(0).setCellValue(srNo++);
			row.createCell(0).setCellValue(attendanceLogDetailsDtoList.get(i).getEmployeeCode());
			row.createCell(1).setCellValue(attendanceLogDetailsDtoList.get(i).getEmployeeName());
			row.createCell(2).setCellValue(attendanceLogDetailsDtoList.get(i).getDepartmentName());
			row.createCell(3).setCellValue(attendanceLogDetailsDtoList.get(i).getDesignationName());
			row.createCell(4).setCellValue(attendanceLogDetailsDtoList.get(i).getJobLocation());
			row.createCell(5).setCellValue(attendanceLogDetailsDtoList.get(i).getReportingTo());
			for (int j = 6, k = 0; k < attendanceLogDetailsDtoList.get(i).getEvents().size(); j++, k++) {
				row.createCell(j).setCellValue(attendanceLogDetailsDtoList.get(i).getEvents().get(k).getTitle());
				srNo = j;
			}
			srNo++;
			row.createCell(srNo)
					.setCellValue(convertBigdecimalToString(attendanceLogDetailsDtoList.get(i).getPresense()));
			row.createCell(++srNo)
					.setCellValue(convertBigdecimalToString(attendanceLogDetailsDtoList.get(i).getHalfD()));
			row.createCell(++srNo).setCellValue(convertBigdecimalToString(attendanceLogDetailsDtoList.get(i).getArD()));
			row.createCell(++srNo)
					.setCellValue(convertBigdecimalToString(attendanceLogDetailsDtoList.get(i).getTotalPresentDays()));
			row.createCell(++srNo)
					.setCellValue(convertBigdecimalToString(attendanceLogDetailsDtoList.get(i).getWeekoff()));
			row.createCell(++srNo)
					.setCellValue(convertBigdecimalToString(attendanceLogDetailsDtoList.get(i).getPublicholidays()));
			row.createCell(++srNo)
					.setCellValue(convertBigdecimalToString(attendanceLogDetailsDtoList.get(i).getLeaves()));
			row.createCell(++srNo)
			.setCellValue(convertBigdecimalToString(attendanceLogDetailsDtoList.get(i).getAbsenseForCalender()));
			row.createCell(++srNo)
					.setCellValue(convertBigdecimalToString(attendanceLogDetailsDtoList.get(i).getPayDays()));
			row.createCell(++srNo).setCellValue(attendanceLogDetailsDtoList.get(i).getEvents().size());

		}
		// Resize all columns to fit the content size
		for (int i = 0; i < columns.length; i++) {
			sheet.autoSizeColumn(i);
		}

		for (int i = 0; i < dateColumnList.size(); i++) {
			sheet.autoSizeColumn(i);
		}
		for (int i = 0; i < moreColumns.length; i++) {
			sheet.autoSizeColumn(i);
		}
		return workbook;
	}

	// Late comers report
	public static Workbook lateComersReport(List<AttendanceLogDTO> lateComerList, String[] columns, Date fDate,
			Date tDate) throws IOException, InvalidFormatException {
		// Create a Workbook
		Workbook workbook = new XSSFWorkbook();

		CreationHelper createHelper = workbook.getCreationHelper();

		// Create a Sheet
		Sheet sheet = workbook.createSheet("Late Comers");

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

		CellStyle headerCellStyle1 = workbook.createCellStyle();
		headerCellStyle1.setFont(headerFont);
		headerCellStyle1.setAlignment(HorizontalAlignment.CENTER);
		headerCellStyle1.setVerticalAlignment(VerticalAlignment.CENTER);
		headerCellStyle1.setFillBackgroundColor(IndexedColors.LIGHT_CORNFLOWER_BLUE.getIndex());
		headerCellStyle1.setFillPattern(FillPatternType.FINE_DOTS);

		Row row0 = sheet.createRow(0);
		Cell cellCom = row0.createCell(0);
		sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 10));
		cellCom.setCellValue("Late Comers Details");
		cellCom.setCellStyle(headerCellStyle);

		Row row1 = sheet.createRow(1);
		Cell cell1 = row1.createCell(0);
		sheet.addMergedRegion(new CellRangeAddress(1, 1, 0, 10));
		cell1.setCellValue(DateUtils.getDateStrInDDMMMYYYY(fDate) + " To " + DateUtils.getDateStrInDDMMMYYYY(tDate));
		cell1.setCellStyle(headerCellStyle);

		// Create a Row
		Row headerRow = sheet.createRow(2);

		// Creating cells
		for (int i = 0; i < columns.length; i++) {
			Cell cell = headerRow.createCell(i);
			cell.setCellValue(columns[i]);
			cell.setCellStyle(headerCellStyle1);

		}

		int rowNum = 3;

		if (lateComerList.size() > 0) {

			for (AttendanceLogDTO attendanceLogDtos : lateComerList) {

				Row row = sheet.createRow(rowNum++);

				CellStyle dateCellStyle = workbook.createCellStyle();
				dateCellStyle.setDataFormat(createHelper.createDataFormat().getFormat("dd-MMM-yyyy"));

				Cell cell = row.createCell(0);
				cell.setCellValue(attendanceLogDtos.getAttendanceDate());
				cell.setCellStyle(dateCellStyle);

				row.createCell(1).setCellValue(attendanceLogDtos.getEmployeeCode());
				row.createCell(2).setCellValue(attendanceLogDtos.getEmployeeName());
				row.createCell(3).setCellValue(attendanceLogDtos.getDepartmentName());
				row.createCell(4).setCellValue(attendanceLogDtos.getDesignationName());
				row.createCell(5).setCellValue(attendanceLogDtos.getJobLocation());
				row.createCell(6).setCellValue(attendanceLogDtos.getReportingTo());
				row.createCell(7).setCellValue(attendanceLogDtos.getShiftName());
				row.createCell(8).setCellValue(attendanceLogDtos.getShiftDuration());
				row.createCell(9).setCellValue(attendanceLogDtos.getInTime());
				row.createCell(10).setCellValue(attendanceLogDtos.getOutTime());
				row.createCell(11).setCellValue(attendanceLogDtos.getTotalTime());
				row.createCell(12).setCellValue(attendanceLogDtos.getDelayedTime());
				row.createCell(13).setCellValue(attendanceLogDtos.getStatus());

			}
		} else {

			Row row = sheet.createRow(3);

			sheet.addMergedRegion(new CellRangeAddress(3, 6, 0, columns.length - 1));
			Cell cell = row.createCell(0);
			cell.setCellValue("--Data Not Available -- ");
			cell.setCellStyle(headerCellStyle);

		}
		// Resize all columns to fit the content size
		for (int i = 0; i < columns.length; i++) {
			sheet.autoSizeColumn(i);
		}

		return workbook;
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

	@SuppressWarnings("unlikely-arg-type")
	public static Workbook leaveEntitlementAndBalanceSummaryWriter(Object[] columns,
			List<AttendanceDTO> attendanceDTOList, Date startDate, Date endDate, String[] typesOfLeavesId,
			String[] typesOfLeavesIdEntitlement) {

		Workbook workbook = new XSSFWorkbook(); // new HSSFWorkbook() for generating `.xls` file

		CreationHelper createHelper = workbook.getCreationHelper();

		Sheet sheet = workbook.createSheet("Leave Entitilement And Balance Summary");

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

		CellStyle headerCellStyleCenter = workbook.createCellStyle();
		headerCellStyleCenter.setFont(headerFont);
		headerCellStyleCenter.setAlignment(HorizontalAlignment.CENTER);
		headerCellStyleCenter.setVerticalAlignment(VerticalAlignment.CENTER);

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

		CellStyle addZero = workbook.createCellStyle();
		addZero.setDataFormat(createHelper.createDataFormat().getFormat("0.00"));
		addZero.setAlignment(HorizontalAlignment.RIGHT);
		addZero.setVerticalAlignment(VerticalAlignment.CENTER);

		CellStyle addZeroCenter = workbook.createCellStyle();
		addZeroCenter.setDataFormat(createHelper.createDataFormat().getFormat("0.00"));
		addZeroCenter.setAlignment(HorizontalAlignment.CENTER);
		addZeroCenter.setVerticalAlignment(VerticalAlignment.CENTER);

		Row row0 = sheet.createRow(0);
		sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 9));
		Cell cellCom = row0.createCell(0);
		cellCom.setCellValue("Leave Entitlement & Balance summary");
		cellCom.setCellStyle(headerCellStyleCenter);

		CellStyle dateCellStyle = workbook.createCellStyle();
		dateCellStyle.setDataFormat(createHelper.createDataFormat().getFormat("dd-MM-yyyy"));

		CellStyle dateCellStyles = workbook.createCellStyle();
		dateCellStyles.setDataFormat(createHelper.createDataFormat().getFormat(""));

		String dateFrom = null;
		SimpleDateFormat s1 = new SimpleDateFormat("dd-MMM-yyyy");
		dateFrom = s1.format(startDate);

		String dateTo = null;
		SimpleDateFormat s2 = new SimpleDateFormat("dd-MMM-yyyy");
		dateTo = s2.format(endDate);

		Row headerRow1 = sheet.createRow(1);
		sheet.addMergedRegion(new CellRangeAddress(1, 1, 0, 9));
		Cell cell1 = headerRow1.createCell(0);
		// cell1.setCellValue(DateUtils.getchangeMonthDateFormat(fromdateString) + " To
		// " + DateUtils.getchangeMonthDateFormat(todateString));
		cell1.setCellValue(dateFrom + " To " + dateTo);
		cell1.setCellStyle(headerCellStyleCenter);

		Row headerRow = sheet.createRow(2);
		// Creating cells
		for (int i = 0; i < columns.length; i++) {
			Cell cell = headerRow.createCell(i);
			cell.setCellValue(columns[i].toString());
			cell.setCellStyle(headerCellStyle);

		}
		int rowNum = 3;

		if (attendanceDTOList != null) {

			for (AttendanceDTO attendanceDTO : attendanceDTOList) {
				Row row = sheet.createRow(rowNum++);

				row.createCell(0).setCellValue(attendanceDTO.getEmployeeCode());
				row.createCell(1).setCellValue(attendanceDTO.getEmployeeName());
				row.createCell(2).setCellValue(attendanceDTO.getDepartment());
				row.createCell(3).setCellValue(attendanceDTO.getDesignation());
				row.createCell(4).setCellValue(attendanceDTO.getJobLocation());

				Cell cell13 = row.createCell(5);
				cell13.setCellValue(attendanceDTO.getDateOfJoining());
				cell13.setCellStyle(dateCellStyle);

				row.createCell(6).setCellValue(attendanceDTO.getReportingTo());

				row.createCell(7).setCellValue(attendanceDTO.getLeaveScheme());

				Cell cell11 = row.createCell(8);
				cell11.setCellValue(
						attendanceDTO.getAnnualQuotaTotal() != null ? attendanceDTO.getAnnualQuotaTotal().doubleValue()
								: new BigDecimal(0.00).doubleValue());
				cell11.setCellStyle(addZeroCenter);

				Cell cell12 = row.createCell(9);
				cell12.setCellValue(attendanceDTO.getCarryForwardTotal() != null
						? attendanceDTO.getCarryForwardTotal().doubleValue()
						: new BigDecimal(0.00).doubleValue());
				cell12.setCellStyle(addZeroCenter);

				Cell cell3 = row.createCell(10);
				cell3.setCellValue(
						attendanceDTO.getConsumedTotal() != null ? attendanceDTO.getConsumedTotal().doubleValue()
								: new BigDecimal(0.00).doubleValue());
				cell3.setCellStyle(addZeroCenter);

				Cell cell4 = row.createCell(11);
				cell4.setCellValue(
						attendanceDTO.getBalanceTotal() != null ? attendanceDTO.getBalanceTotal().doubleValue()
								: new BigDecimal(0.00).doubleValue());
				cell4.setCellStyle(addZeroCenter);

				Map<String, List<BigDecimal>> leaveIdMap = attendanceDTO.getLeaveTypeMap();
				Map<String, List<BigDecimal>> leaveEntitlemntMap = attendanceDTO.getLeaveEntitlementTypeMap();
				BigDecimal leaveConsumed = new BigDecimal(0.0);

				int value = 12;
				BigDecimal carryForward = new BigDecimal(0.0);
				BigDecimal consumed = new BigDecimal(0.0);
				BigDecimal balance = new BigDecimal(0.0);
				BigDecimal annualQuota = new BigDecimal(0.0);

				String[] leaveId = typesOfLeavesId;
				if (leaveIdMap != null) {

					for (int i = 0; i < leaveId.length; i++) {
						for (Map.Entry<String, List<BigDecimal>> leaveData : leaveIdMap.entrySet()) {

							List<BigDecimal> data = leaveData.getValue();
							String key = leaveData.getKey();
							Set<String> hashkey = leaveIdMap.keySet();
							// String[] a1=hashkey.toArray(new String[0]);
							if (leaveId[i].equals(key)) {
								if (data.get(0).compareTo(new BigDecimal(0.0)) > 0)
									carryForward = data.get(0);
								else
									carryForward = new BigDecimal(0.0);

								Cell cell5 = row.createCell(value++);
								cell5.setCellValue(carryForward.doubleValue());
								cell5.setCellStyle(addZeroCenter);

								if (data.get(1).compareTo(new BigDecimal(0.0)) > 0)
									consumed = data.get(1);
								else
									consumed = new BigDecimal(0.0);
								Cell cell6 = row.createCell(value++);
								cell6.setCellValue(consumed.doubleValue());
								cell6.setCellStyle(addZeroCenter);

								if (data.get(2).compareTo(new BigDecimal(0.0)) > 0)
									balance = data.get(2);
								else
									balance = new BigDecimal(0.0);

								Cell cell7 = row.createCell(value++);
								cell7.setCellValue(balance.doubleValue());
								cell7.setCellStyle(addZeroCenter);

								if (data.get(3).compareTo(new BigDecimal(0.0)) > 0)
									annualQuota = data.get(3);
								else
									annualQuota = new BigDecimal(0.0);

								Cell cell8 = row.createCell(value++);
								cell8.setCellValue(annualQuota.doubleValue());
								cell8.setCellStyle(addZeroCenter);
								break;
							}

							else {

								if (!leaveIdMap.containsKey(leaveId[i])) {

									Cell cell5 = row.createCell(value++);
									cell5.setCellValue(0.00);
									cell5.setCellStyle(addZeroCenter);

									Cell cell6 = row.createCell(value++);
									cell6.setCellValue(0.00);
									cell6.setCellStyle(addZeroCenter);

									Cell cell7 = row.createCell(value++);
									cell7.setCellValue(0.00);
									cell7.setCellStyle(addZeroCenter);

									Cell cell8 = row.createCell(value++);
									cell8.setCellValue(0.00);
									cell8.setCellStyle(addZeroCenter);

									break;

								}
							}
						}

						if (leaveIdMap.isEmpty()) {

							Cell cell5 = row.createCell(value++);
							cell5.setCellValue(0.00);
							cell5.setCellStyle(addZeroCenter);

							Cell cell6 = row.createCell(value++);
							cell6.setCellValue(0.00);
							cell6.setCellStyle(addZeroCenter);

							Cell cell7 = row.createCell(value++);
							cell7.setCellValue(0.00);
							cell7.setCellStyle(addZeroCenter);

							Cell cell8 = row.createCell(value++);
							cell8.setCellValue(0.00);
							cell8.setCellStyle(addZeroCenter);
						}

					}
				}

				if (leaveIdMap != null) {
					for (int i = 0; i < typesOfLeavesIdEntitlement.length; i++) {
						for (Map.Entry<String, List<BigDecimal>> leaveData : leaveIdMap.entrySet()) {

							List<BigDecimal> data = leaveData.getValue();
							String key = leaveData.getKey();
							if (typesOfLeavesIdEntitlement[i].equals(key)) {
								if (key.equals("6")) {
									if (data.get(0).compareTo(new BigDecimal(0.0)) > 0)
										leaveConsumed = data.get(0);
									else
										leaveConsumed = new BigDecimal(0.0);
									Cell cell5 = row.createCell(value++);
									cell5.setCellValue(leaveConsumed.doubleValue());
									cell5.setCellStyle(addZeroCenter);

									break;

								}

								else if (key.equals("7")) {
									if (data.get(0).compareTo(new BigDecimal(0.0)) > 0)
										leaveConsumed = data.get(0);
									else
										leaveConsumed = new BigDecimal(0.0);
									Cell cell5 = row.createCell(value++);
									cell5.setCellValue(leaveConsumed.doubleValue());
									cell5.setCellStyle(addZeroCenter);

									if (data.get(1).compareTo(new BigDecimal(0.0)) > 0)
										balance = data.get(1);
									else
										balance = new BigDecimal(0.0);

									Cell cell7 = row.createCell(value++);
									cell7.setCellValue(balance.doubleValue());
									cell7.setCellStyle(addZeroCenter);

									if (data.get(2).compareTo(new BigDecimal(0.0)) > 0)
										balance = data.get(2);
									else
										balance = new BigDecimal(0.0);

									break;

								}

								// typesOfLeavesIdEntitlement = (String[])
								// ArrayUtils.remove(typesOfLeavesIdEntitlement,
								// i);

							} else {
								if (!leaveIdMap.containsKey(typesOfLeavesIdEntitlement[i])) {

									if (typesOfLeavesIdEntitlement[i].equals("6")) {
										Cell cell5 = row.createCell(value++);
										cell5.setCellValue(0.00);
										cell5.setCellStyle(addZeroCenter);
										break;
									}

									else if (typesOfLeavesIdEntitlement[i].equals("7")) {

										Cell cell5 = row.createCell(value++);
										cell5.setCellValue(0.00);
										cell5.setCellStyle(addZeroCenter);

										Cell cell6 = row.createCell(value++);
										cell6.setCellValue(0.00);
										cell6.setCellStyle(addZeroCenter);

										break;
									}
								}
							}

						}

						if (leaveIdMap.isEmpty()) {

							if (typesOfLeavesIdEntitlement[i].equals("6")) {
								Cell cell5 = row.createCell(value++);
								cell5.setCellValue(0.00);
								cell5.setCellStyle(addZeroCenter);
							}

							else if (typesOfLeavesIdEntitlement[i].equals("7")) {
								Cell cell5 = row.createCell(value++);
								cell5.setCellValue(0.00);
								cell5.setCellStyle(addZeroCenter);

								Cell cell6 = row.createCell(value++);
								cell6.setCellValue(0.00);
								cell6.setCellStyle(addZeroCenter);

							}

						}
					}

				}
			}

		}

		for (int i = 0; i < columns.length; i++) {
			sheet.autoSizeColumn(i);
		}

		// TODO Auto-generated method stub
		return workbook;
	}

	public static Workbook leaveRequestExcelWriter(String[] columns, List<LeaveEntryDTO> leaveRequestDtoList,
			Date fromDate, Date toDate) {

		Workbook workbook = new XSSFWorkbook();
		CreationHelper createHelper = workbook.getCreationHelper();
		// Create a Sheet
		Sheet sheet = workbook.createSheet("Leave Request ");

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
		headerCellStyle1.setAlignment(HorizontalAlignment.CENTER);
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

		sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 14));
		Cell cell0 = headerRow0.createCell(0);
		cell0.setCellValue("Leave Request Summary");
		cell0.setCellStyle(headerCellStyle1);

		Row headerRow1 = sheet.createRow(1);

		// Date fromDates = attendanceRegularizationRequest.getFromDate();
		// String processMonth = DateUtils.getDays(fromDates) + "-" +
		// DateUtils.getMonthOfProcess(fromDates).toUpperCase()
		// + "-" + DateUtils.getYearOfProcess(fromDates);

		String d1 = null;
		SimpleDateFormat s1 = new SimpleDateFormat("dd-MMM-yyyy");
		d1 = s1.format(fromDate);

		String d2 = null;
		SimpleDateFormat s2 = new SimpleDateFormat("dd-MMM-yyyy");
		d2 = s2.format(toDate);

		sheet.addMergedRegion(new CellRangeAddress(1, 1, 0, 14));
		Cell cell1 = headerRow1.createCell(0);
		cell1.setCellValue(d1 + " To " + d2);
		cell1.setCellStyle(headerCellStyle1);

		Row headerRow2 = sheet.createRow(2);

		for (int i = 0; i < columns.length; i++) {
			Cell cell = headerRow2.createCell(i);
			cell.setCellValue(columns[i].toString());
			cell.setCellStyle(headerCellStyle11);
		}

		int rowNum = 3;

		if (leaveRequestDtoList != null) {
			for (LeaveEntryDTO leaveEntryDTO : leaveRequestDtoList) {

				Row row = sheet.createRow(rowNum++);

				row.createCell(0).setCellValue(leaveEntryDTO.getEmployeeCode());
				row.createCell(1).setCellValue(leaveEntryDTO.getEmployeeName());
				row.createCell(2).setCellValue(leaveEntryDTO.getDepartment());
				row.createCell(3).setCellValue(leaveEntryDTO.getDesignation());
				row.createCell(4).setCellValue(leaveEntryDTO.getJobLocation());
				row.createCell(5).setCellValue(leaveEntryDTO.getReportingManager());

				Cell cell11 = row.createCell(6);
				cell11.setCellValue(leaveEntryDTO.getDateCreated());
				cell11.setCellStyle(dateCellStyle);

				row.createCell(7).setCellValue(leaveEntryDTO.getLeaveType());
				row.createCell(8).setCellValue(DateUtils.getDateStrByDate(leaveEntryDTO.getFromDate()) + "  " + "To"
						+ "  " + (DateUtils.getDateStrByDate(leaveEntryDTO.getToDate())));

				Cell cell12 = row.createCell(9);
				cell12.setCellValue(leaveEntryDTO.getDays().doubleValue());

				row.createCell(10).setCellValue(leaveEntryDTO.getEmployeeRemark());
				row.createCell(11).setCellValue(leaveEntryDTO.getStatus());
				row.createCell(12).setCellValue(leaveEntryDTO.getActionTakenBy());

				Cell actionTakenOn = row.createCell(13);
				actionTakenOn.setCellValue(leaveEntryDTO.getActionableDate());
				actionTakenOn.setCellStyle(dateCellStyle);

				row.createCell(14).setCellValue(leaveEntryDTO.getApprovalRemark());

			}

		}

		for (int i = 0; i < columns.length; i++) {
			sheet.autoSizeColumn(i);
		}
		return workbook;
	}

	/**
	 * @author ${Nisha Parveen}
	 *
	 */
	public static Workbook compOffReqSummaryReportWriter(List<CompensatoryOffDTO> CompensatoryOffList,
			Object[] newColumns, Date fromDate, Date toDate) {

		Workbook workbook = new XSSFWorkbook();
		CreationHelper createHelper = workbook.getCreationHelper();
		Sheet sheet = workbook.createSheet("Compensatory off request summary");

		DateUtils dateUtil = new DateUtils();
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
		headerCellStyle1.setFillBackgroundColor(IndexedColors.LIGHT_CORNFLOWER_BLUE.getIndex());
		headerCellStyle1.setFillPattern(FillPatternType.FINE_DOTS);

		CellStyle headerCellStyle2 = workbook.createCellStyle();
		headerCellStyle2.setFont(headerFont);
		headerCellStyle2.setAlignment(HorizontalAlignment.CENTER);
		headerCellStyle2.setVerticalAlignment(VerticalAlignment.CENTER);

		CellStyle rowCellStyle = workbook.createCellStyle();
		rowCellStyle.setAlignment(HorizontalAlignment.CENTER);
		rowCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
		rowCellStyle.setFont(rowFont);

		// Create Cell Style for formatting Date
		CellStyle dateCellStyle = workbook.createCellStyle();
		dateCellStyle.setDataFormat(createHelper.createDataFormat().getFormat("dd-mmm-yyyy"));
		dateCellStyle.setAlignment(HorizontalAlignment.CENTER);
		dateCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
		// Not Used In code
		CellStyle dateCellStyle2 = workbook.createCellStyle();
		dateCellStyle2.setDataFormat(createHelper.createDataFormat().getFormat("dd-mmm-yyyy"));
		dateCellStyle2.setAlignment(HorizontalAlignment.CENTER);
		dateCellStyle2.setVerticalAlignment(VerticalAlignment.CENTER);
		dateCellStyle2.setFont(headerFont);

		Row headerRow0 = sheet.createRow(0);
		sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, newColumns.length - 1));
		Cell cell0 = headerRow0.createCell(0);
		cell0.setCellValue("Compensatory Off Request Summary");
		cell0.setCellStyle(headerCellStyle2);

		Row headerRow1 = sheet.createRow(1);
		sheet.addMergedRegion(new CellRangeAddress(1, 1, 0, newColumns.length - 1));
		Cell cell1 = headerRow1.createCell(0);
		cell1.setCellValue(
				DateUtils.getDateStrInDDMMMYYYY(fromDate) + " To " + DateUtils.getDateStrInDDMMMYYYY(toDate));
		cell1.setCellStyle(dateCellStyle2);

		Row headerRow2 = sheet.createRow(2);

		// Creating cells
		for (int i = 0; i < newColumns.length; i++) {
			Cell cell = headerRow2.createCell(i);
			cell.setCellValue(newColumns[i].toString());
			cell.setCellStyle(headerCellStyle1);
		}

		if (CompensatoryOffList.size() > 0) {

			int rowNum = 3;
			for (CompensatoryOffDTO compensatoryOffDTO : CompensatoryOffList) {
				Row row = sheet.createRow(rowNum++);

				row.createCell(0).setCellValue(compensatoryOffDTO.getEmployeeCode());
				row.createCell(1).setCellValue(compensatoryOffDTO.getEmployeeName());
				row.createCell(2).setCellValue(compensatoryOffDTO.getDepartment());
				row.createCell(3).setCellValue(compensatoryOffDTO.getDesignation());
				row.createCell(4).setCellValue(compensatoryOffDTO.getJobLocation());
				row.createCell(5).setCellValue(compensatoryOffDTO.getReportingManager());

				Cell requestedOn = row.createCell(6);
				requestedOn.setCellValue(compensatoryOffDTO.getRequestedOn());
				requestedOn.setCellStyle(dateCellStyle);

				row.createCell(7)
						.setCellValue(getchangeDateFormat(DateUtils.getDateStrByDate(compensatoryOffDTO.getFromDate()))
								+ " To "
								+ getchangeDateFormat(DateUtils.getDateStrByDate(compensatoryOffDTO.getToDate())));

				row.createCell(8).setCellValue(compensatoryOffDTO.getDay());
				row.createCell(9).setCellValue(compensatoryOffDTO.getRequesterRemark());
				row.createCell(10).setCellValue(compensatoryOffDTO.getStatus());
				row.createCell(11).setCellValue(compensatoryOffDTO.getActionTakenBy());

				Cell actionTakenOn = row.createCell(12);
				actionTakenOn.setCellValue(compensatoryOffDTO.getActionTakenOn());
				actionTakenOn.setCellStyle(dateCellStyle);

				row.createCell(13).setCellValue(compensatoryOffDTO.getActionerRemark());
				row.setRowStyle(rowCellStyle);
			}
		} else {

			Row row = sheet.createRow(3);

			sheet.addMergedRegion(new CellRangeAddress(3, 6, 0, newColumns.length - 1));
			Cell cell = row.createCell(0);
			cell.setCellValue("--Data Not Available -- ");
			cell.setCellStyle(headerCellStyle2);

		}

		// Resize all columns to fit the content size
		for (int i = 0; i < newColumns.length; i++) {
			sheet.autoSizeColumn(i);
		}
		return workbook;
	}

	public static Workbook earlyComersReport(List<AttendanceLogDTO> attendanceLogDTOList, String[] columns, Date fDate,
			Date tDate) {

		Workbook workbook = new XSSFWorkbook();

		CreationHelper createHelper = workbook.getCreationHelper();

		// Create a Sheet
		Sheet sheet = workbook.createSheet("Early Comers");

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
		cellCom.setCellValue("Early Comers Details");
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
		if (attendanceLogDTOList.size() > 0) {
			int rowNum = 3;
			for (AttendanceLogDTO attendanceLogDto : attendanceLogDTOList) {

				Row row = sheet.createRow(rowNum++);

				CellStyle dateCellStyle = workbook.createCellStyle();
				dateCellStyle.setDataFormat(createHelper.createDataFormat().getFormat("dd-MMM-yyyy"));

				Cell cell = row.createCell(0);
				cell.setCellValue(attendanceLogDto.getAttendanceDate());
				cell.setCellStyle(dateCellStyle);

				row.createCell(1).setCellValue(attendanceLogDto.getEmployeeCode());
				row.createCell(2).setCellValue(attendanceLogDto.getEmployeeName());
				row.createCell(3).setCellValue(attendanceLogDto.getDepartmentName());
				row.createCell(4).setCellValue(attendanceLogDto.getDesignationName());
				row.createCell(5).setCellValue(attendanceLogDto.getJobLocation());
				row.createCell(6).setCellValue(attendanceLogDto.getReportingTo());
				row.createCell(7).setCellValue(attendanceLogDto.getShiftName());
				row.createCell(8).setCellValue(attendanceLogDto.getShiftDuration());
				row.createCell(9).setCellValue(attendanceLogDto.getInTime());
				row.createCell(10).setCellValue(attendanceLogDto.getOutTime());
				row.createCell(11).setCellValue(attendanceLogDto.getTotalTime());
				row.createCell(12).setCellValue(attendanceLogDto.getEarlyTime());
				row.createCell(13).setCellValue(attendanceLogDto.getStatus());

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

	public static Workbook WorkedOnHolidaysDetailsReport(List<AttendanceLogDTO> newAttendanceLogDTOList,
			String[] columns, Date fDate, Date tDate) {

		Workbook workbook = new XSSFWorkbook();

		CreationHelper createHelper = workbook.getCreationHelper();

		// Create a Sheet
		Sheet sheet = workbook.createSheet("Worked on Holidays Details");

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
		sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 11));
		Cell cellCom = row0.createCell(0);
		cellCom.setCellValue("Worked on Holidays / Week-offs");
		cellCom.setCellStyle(headerCellStyle2);

		Row row1 = sheet.createRow(1);
		sheet.addMergedRegion(new CellRangeAddress(1, 1, 0, 11));
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

		int rowNum = 3;
		if (newAttendanceLogDTOList.size() > 0) {
			for (AttendanceLogDTO attendanceLogDto : newAttendanceLogDTOList) {

				Row row = sheet.createRow(rowNum++);

				CellStyle dateCellStyle = workbook.createCellStyle();
				dateCellStyle.setDataFormat(createHelper.createDataFormat().getFormat("dd-MMM-yyyy"));

				Cell cell = row.createCell(0);
				cell.setCellValue(attendanceLogDto.getAttendanceDate());
				cell.setCellStyle(dateCellStyle);

				row.createCell(1).setCellValue(attendanceLogDto.getEmployeeCode());
				row.createCell(2).setCellValue(attendanceLogDto.getEmployeeName());
				row.createCell(3).setCellValue(attendanceLogDto.getDepartmentName());
				row.createCell(4).setCellValue(attendanceLogDto.getDesignationName());
				row.createCell(5).setCellValue(attendanceLogDto.getJobLocation());
				row.createCell(6).setCellValue(attendanceLogDto.getReportingTo());
				row.createCell(7).setCellValue(attendanceLogDto.getShiftName());
				row.createCell(8).setCellValue(attendanceLogDto.getShiftDuration());
				row.createCell(9).setCellValue(attendanceLogDto.getInTime());
				row.createCell(10).setCellValue(attendanceLogDto.getOutTime());
				row.createCell(11).setCellValue(attendanceLogDto.getTotalTime());
				row.createCell(12).setCellValue(attendanceLogDto.getStatus());

			}
		} else {

			Row row = sheet.createRow(3);
			sheet.addMergedRegion(new CellRangeAddress(3, 6, 0, columns.length - 1));
			Cell cell = row.createCell(0);
			cell.setCellValue(" ----Data is not available---- ");
			cell.setCellStyle(headerCellStyle2);

		}
		// Resize all columns to fit the content size
		for (int i = 0; i < columns.length; i++) {
			sheet.autoSizeColumn(i);
		}

		return workbook;
	}

	public static Workbook earlyLeaversReportWriter(List<AttendanceLogDTO> earlyLeaversList, String[] columns,
			Date fDate, Date tDate) {

		Workbook workbook = new XSSFWorkbook();

		CreationHelper createHelper = workbook.getCreationHelper();

		// Create a Sheet
		Sheet sheet = workbook.createSheet("Early Going");

		// Create a Font for styling header cells
		Font headerFont = workbook.createFont();
		headerFont.setBold(true);
		headerFont.setFontHeightInPoints((short) 12);
		headerFont.setColor(IndexedColors.BLACK.getIndex());

		Font rowFont = workbook.createFont();
		rowFont.setFontHeightInPoints((short) 11);
		rowFont.setColor(IndexedColors.BLACK.getIndex());

		// Create a CellStyle with the font
		CellStyle headerCellStyle = workbook.createCellStyle();
		headerCellStyle.setFont(headerFont);
		headerCellStyle.setAlignment(HorizontalAlignment.CENTER);
		headerCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);

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
		dateCellStyle.setDataFormat(createHelper.createDataFormat().getFormat("dd-MMM-yyyy"));
		dateCellStyle.setAlignment(HorizontalAlignment.CENTER);
		dateCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);

		Row row0 = sheet.createRow(0);

		sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, columns.length - 1));
		Cell cellCom = row0.createCell(0);
		cellCom.setCellValue(" Early Leavers Details ");
		cellCom.setCellStyle(headerCellStyle);

		Row row1 = sheet.createRow(1);
		sheet.addMergedRegion(new CellRangeAddress(1, 1, 0, columns.length - 1));
		Cell cell1 = row1.createCell(0);
		cell1.setCellValue(DateUtils.getDateStrInDDMMMYYYY(fDate) + " To " + DateUtils.getDateStrInDDMMMYYYY(tDate));
		cell1.setCellStyle(headerCellStyle);

		// Create a Row
		Row headerRow = sheet.createRow(2);

		// Creating cells
		for (int i = 0; i < columns.length; i++) {
			Cell cell = headerRow.createCell(i);
			cell.setCellValue(columns[i]);
			cell.setCellStyle(headerCellStyle11);

		}

		if (earlyLeaversList.size() > 0) {
			int rowNum = 3;
			for (AttendanceLogDTO attendanceLogDto : earlyLeaversList) {
				Row row = sheet.createRow(rowNum++);

				Cell cell = row.createCell(0);
				cell.setCellValue(attendanceLogDto.getAttendanceDate());
				cell.setCellStyle(dateCellStyle);

				row.createCell(1).setCellValue(attendanceLogDto.getEmployeeCode());
				row.createCell(2).setCellValue(attendanceLogDto.getEmployeeName());
				row.createCell(3).setCellValue(attendanceLogDto.getDepartmentName());
				row.createCell(4).setCellValue(attendanceLogDto.getDesignationName());
				row.createCell(5).setCellValue(attendanceLogDto.getJobLocation());
				row.createCell(6).setCellValue(attendanceLogDto.getReportingTo());
				row.createCell(7).setCellValue(attendanceLogDto.getShiftName());
				row.createCell(8).setCellValue(attendanceLogDto.getShiftDuration());
				row.createCell(9).setCellValue(attendanceLogDto.getInTime());
				row.createCell(10).setCellValue(attendanceLogDto.getOutTime());
				row.createCell(11).setCellValue(attendanceLogDto.getTotalTime());
				row.createCell(12).setCellValue(attendanceLogDto.getEarlyTime());

				Cell cell13 = row.createCell(13);
				cell13.setCellValue(attendanceLogDto.getStatus());
				cell13.setCellStyle(rowCellStyle);

			}

		} else {
			Row row = sheet.createRow(3);

			sheet.addMergedRegion(new CellRangeAddress(3, 6, 0, columns.length - 1));
			Cell cell = row.createCell(0);
			cell.setCellValue("--Data Not Available -- ");
			cell.setCellStyle(headerCellStyle);

		}

		// Resize all columns to fit the content size
		for (int i = 0; i < columns.length; i++) {
			sheet.autoSizeColumn(i);
		}

		return workbook;

	}

	public static Workbook arRequestReportWriter(String[] columns,
			List<AttendanceRegularizationRequestDTO> arRequestDTOList, Date fDate, Date tDate) {

		Workbook workbook = new XSSFWorkbook();
		CreationHelper createHelper = workbook.getCreationHelper();
		// Create a Sheet
		Sheet sheet = workbook.createSheet("AR Summary");

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
		headerCellStyle1.setAlignment(HorizontalAlignment.CENTER);
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

		sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, columns.length - 1));
		Cell cell0 = headerRow0.createCell(0);
		cell0.setCellValue("Attendance Regularization Summary");
		cell0.setCellStyle(headerCellStyle1);

		Row headerRow1 = sheet.createRow(1);

		sheet.addMergedRegion(new CellRangeAddress(1, 1, 0, columns.length - 1));
		Cell cell1 = headerRow1.createCell(0);
		cell1.setCellValue(DateUtils.getDateStrInDDMMMYYYY(fDate) + " To " + DateUtils.getDateStrInDDMMMYYYY(tDate));
		cell1.setCellStyle(headerCellStyle1);

		Row headerRow2 = sheet.createRow(2);

		for (int i = 0; i < columns.length; i++) {
			Cell cell = headerRow2.createCell(i);
			cell.setCellValue(columns[i].toString());
			cell.setCellStyle(headerCellStyle11);
		}

		if (arRequestDTOList.size() > 0) {

			int rowNum = 3;

			for (AttendanceRegularizationRequestDTO arRequestDTO : arRequestDTOList) {

				Row row = sheet.createRow(rowNum++);

				row.createCell(0).setCellValue(arRequestDTO.getEmployeeCode());
				row.createCell(1).setCellValue(arRequestDTO.getEmployeeName());
				row.createCell(2).setCellValue(arRequestDTO.getEmployeeDepartment());
				row.createCell(3).setCellValue(arRequestDTO.getEmployeeDesignation());
				row.createCell(4).setCellValue(arRequestDTO.getCityName());
				row.createCell(5).setCellValue(arRequestDTO.getReportingManagerName());

				Cell requestedOn = row.createCell(6);
				requestedOn.setCellValue(arRequestDTO.getDateCreated());
				requestedOn.setCellStyle(dateCellStyle);

				row.createCell(7).setCellValue(arRequestDTO.getArCategory());
				row.createCell(8).setCellValue(DateUtils.getDateStrInDDMMMYYYY(arRequestDTO.getFromDate()) + " To "
						+ DateUtils.getDateStrInDDMMMYYYY(arRequestDTO.getToDate()));
				row.createCell(9).setCellValue(arRequestDTO.getDay());
				row.createCell(10).setCellValue(arRequestDTO.getEmployeeRemark());
				row.createCell(11).setCellValue(arRequestDTO.getStatus());
				row.createCell(12).setCellValue(arRequestDTO.getActionTakenBy());

				Cell actionTakenOn = row.createCell(13);
				actionTakenOn.setCellValue(arRequestDTO.getActionableDate());
				actionTakenOn.setCellStyle(dateCellStyle);

				row.createCell(14).setCellValue(arRequestDTO.getApprovalRemark());
			}
		} else {

			Row row = sheet.createRow(3);

			sheet.addMergedRegion(new CellRangeAddress(3, 6, 0, columns.length - 1));
			Cell cell = row.createCell(0);
			cell.setCellValue("--Data Not Available -- ");
			cell.setCellStyle(headerCellStyle1);

		}
		for (int i = 0; i < columns.length; i++) {
			sheet.autoSizeColumn(i);
		}
		return workbook;

	}

	public static Workbook overTimeDayWiseReportWriter(List<AttendanceLogDTO> attendanceLogDtoList, String[] columns,
			Date fDate, Date tDate) {

		Workbook workbook = new XSSFWorkbook();

		CreationHelper createHelper = workbook.getCreationHelper();

		// Create a Sheet
		Sheet sheet = workbook.createSheet(" Over Time-Day Wise ");

		// Create a Font for styling header cells
		Font headerFont = workbook.createFont();
		headerFont.setBold(true);
		headerFont.setFontHeightInPoints((short) 12);
		headerFont.setColor(IndexedColors.BLACK.getIndex());

		Font rowFont = workbook.createFont();
		rowFont.setFontHeightInPoints((short) 11);
		rowFont.setColor(IndexedColors.BLACK.getIndex());

		// Create a CellStyle with the font
		CellStyle headerCellStyle = workbook.createCellStyle();
		headerCellStyle.setFont(headerFont);
		headerCellStyle.setAlignment(HorizontalAlignment.CENTER);
		headerCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);

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
		dateCellStyle.setDataFormat(createHelper.createDataFormat().getFormat("dd-MMM-yyyy"));
		dateCellStyle.setAlignment(HorizontalAlignment.CENTER);
		dateCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);

		Row row0 = sheet.createRow(0);

		sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, columns.length - 1));
		Cell cellCom = row0.createCell(0);
		cellCom.setCellValue(" Over Time-Day Wise ");
		cellCom.setCellStyle(headerCellStyle);

		Row row1 = sheet.createRow(1);
		sheet.addMergedRegion(new CellRangeAddress(1, 1, 0, columns.length - 1));
		Cell cell1 = row1.createCell(0);
		cell1.setCellValue(DateUtils.getDateStrInDDMMMYYYY(fDate) + " To " + DateUtils.getDateStrInDDMMMYYYY(tDate));
		cell1.setCellStyle(headerCellStyle);

		// Create a Row
		Row headerRow = sheet.createRow(2);

		// Creating cells
		for (int i = 0; i < columns.length; i++) {
			Cell cell = headerRow.createCell(i);
			cell.setCellValue(columns[i]);
			cell.setCellStyle(headerCellStyle11);

		}

		if (attendanceLogDtoList.size() > 0) {
			int rowNum = 3;
			for (AttendanceLogDTO attendanceLogDto : attendanceLogDtoList) {
				Row row = sheet.createRow(rowNum++);

				Cell cell = row.createCell(0);
				cell.setCellValue(attendanceLogDto.getAttendanceDate());
				cell.setCellStyle(dateCellStyle);

				row.createCell(1).setCellValue(attendanceLogDto.getEmployeeCode());
				row.createCell(2).setCellValue(attendanceLogDto.getEmployeeName());
				row.createCell(3).setCellValue(attendanceLogDto.getDepartmentName());
				row.createCell(4).setCellValue(attendanceLogDto.getDesignationName());
				row.createCell(5).setCellValue(attendanceLogDto.getJobLocation());
				row.createCell(6).setCellValue(attendanceLogDto.getReportingTo());
				row.createCell(7).setCellValue(attendanceLogDto.getShiftName());
				row.createCell(8).setCellValue(attendanceLogDto.getShiftDuration());
				row.createCell(9).setCellValue(attendanceLogDto.getInTime());
				row.createCell(10).setCellValue(attendanceLogDto.getOutTime());
				row.createCell(11).setCellValue(attendanceLogDto.getTotalTime());
				row.createCell(12).setCellValue(attendanceLogDto.getOverTime());

			}

		} else {
			Row row = sheet.createRow(3);

			sheet.addMergedRegion(new CellRangeAddress(3, 6, 0, columns.length - 1));
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

	public static Workbook overTimeMonthWiseReportWriter(List<AttendanceLogDTO> attendanceLogDtoList, String[] columns,
			Integer pMonth, Integer pYear) {

		Workbook workbook = new XSSFWorkbook();

		CreationHelper createHelper = workbook.getCreationHelper();

		// Create a Sheet
		Sheet sheet = workbook.createSheet(" Over Time-Month Wise ");

		// Create a Font for styling header cells
		Font headerFont = workbook.createFont();
		headerFont.setBold(true);
		headerFont.setFontHeightInPoints((short) 12);
		headerFont.setColor(IndexedColors.BLACK.getIndex());

		Font rowFont = workbook.createFont();
		rowFont.setFontHeightInPoints((short) 11);
		rowFont.setColor(IndexedColors.BLACK.getIndex());

		// Create a CellStyle with the font
		CellStyle headerCellStyle = workbook.createCellStyle();
		headerCellStyle.setFont(headerFont);
		headerCellStyle.setAlignment(HorizontalAlignment.CENTER);
		headerCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);

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
		dateCellStyle.setDataFormat(createHelper.createDataFormat().getFormat("dd-MMM-yyyy"));
		dateCellStyle.setAlignment(HorizontalAlignment.CENTER);
		dateCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);

		Row row0 = sheet.createRow(0);

		sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, columns.length - 1));
		Cell cellCom = row0.createCell(0);
		cellCom.setCellValue(" Over Time Details ");
		cellCom.setCellStyle(headerCellStyle);

		Row row1 = sheet.createRow(1);
		sheet.addMergedRegion(new CellRangeAddress(1, 1, 0, columns.length - 1));
		Cell cell1 = row1.createCell(0);
		cell1.setCellValue(" Month " + " - " + DateUtils.month[pMonth - 1] + " " + pYear);
		cell1.setCellStyle(headerCellStyle);

		// Create a Row
		Row headerRow = sheet.createRow(2);

		// Creating cells
		for (int i = 0; i < columns.length; i++) {
			Cell cell = headerRow.createCell(i);
			cell.setCellValue(columns[i]);
			cell.setCellStyle(headerCellStyle11);

		}

		if (attendanceLogDtoList.size() > 0) {
			int rowNum = 3;
			for (AttendanceLogDTO attendanceLogDto : attendanceLogDtoList) {
				Row row = sheet.createRow(rowNum++);

				row.createCell(0).setCellValue(attendanceLogDto.getEmployeeCode());
				row.createCell(1).setCellValue(attendanceLogDto.getEmployeeName());
				row.createCell(2).setCellValue(attendanceLogDto.getDepartmentName());
				row.createCell(3).setCellValue(attendanceLogDto.getDesignationName());
				row.createCell(4).setCellValue(attendanceLogDto.getJobLocation());
				row.createCell(5).setCellValue(attendanceLogDto.getReportingTo());
				row.createCell(6).setCellValue(attendanceLogDto.getShiftName());
				row.createCell(7).setCellValue(attendanceLogDto.getShiftDuration());
				row.createCell(8).setCellValue(attendanceLogDto.getTotalTime());
				row.createCell(9).setCellValue(attendanceLogDto.getOverTime());

			}

		} else {
			Row row = sheet.createRow(3);

			sheet.addMergedRegion(new CellRangeAddress(3, 6, 0, columns.length - 1));
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

	public static Workbook missingCheckIn_OutExcelWriter(String[] columns,
			List<AttendanceLogDTO> missingPunchRecordDtoList, Date startDate, Date endDate) {
		// TODO Auto-generated method stub
		Workbook workbook = new XSSFWorkbook();
		CreationHelper createHelper = workbook.getCreationHelper();
		// Create a Sheet
		Sheet sheet = workbook.createSheet("Missing Check IN-OUT ");

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
		headerCellStyle1.setAlignment(HorizontalAlignment.CENTER);
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

		sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 10));
		Cell cell0 = headerRow0.createCell(0);
		cell0.setCellValue("Missing Punch Records");
		cell0.setCellStyle(headerCellStyle1);

		Row headerRow1 = sheet.createRow(1);

		String dateFrom = null;
		SimpleDateFormat s1 = new SimpleDateFormat("dd-MMM-yyyy");
		dateFrom = s1.format(startDate);

		String dateTo = null;
		SimpleDateFormat s2 = new SimpleDateFormat("dd-MMM-yyyy");
		dateTo = s2.format(endDate);

		// DateUtils dateUtil = new DateUtils();
		// String dateFrom = dateUtil.getDateStringWirhYYYYMMDD(startDate);
		// String dateTo = dateUtil.getDateStringWirhYYYYMMDD(endDate);

		sheet.addMergedRegion(new CellRangeAddress(1, 1, 0, 10));
		Cell cell1 = headerRow1.createCell(0);
		cell1.setCellValue(dateFrom + " To " + dateTo);
		cell1.setCellStyle(headerCellStyle1);

		CellStyle dateCellStyle1 = workbook.createCellStyle();
		dateCellStyle1.setDataFormat(createHelper.createDataFormat().getFormat("dd-MMM-yyyy"));

		Row headerRow2 = sheet.createRow(2);

		for (int i = 0; i < columns.length; i++) {
			Cell cell = headerRow2.createCell(i);
			cell.setCellValue(columns[i].toString());
			cell.setCellStyle(headerCellStyle11);
		}

		int rowNum = 3;
		if (missingPunchRecordDtoList != null) {
			for (AttendanceLogDTO attendanceLogDTO : missingPunchRecordDtoList) {

				Row row = sheet.createRow(rowNum++);
				Cell cell = row.createCell(0);
				cell.setCellValue(attendanceLogDTO.getAttendanceDate());
				cell.setCellStyle(dateCellStyle1);

				row.createCell(1).setCellValue(attendanceLogDTO.getEmployeeCode());
				row.createCell(2).setCellValue(attendanceLogDTO.getEmployeeName());
				row.createCell(3).setCellValue(attendanceLogDTO.getDepartmentName());
				row.createCell(4).setCellValue(attendanceLogDTO.getDesignationName());
				row.createCell(5).setCellValue(attendanceLogDTO.getJobLocation());
				row.createCell(6).setCellValue(attendanceLogDTO.getReportingTo());
				row.createCell(7).setCellValue(attendanceLogDTO.getShiftName());
				row.createCell(8).setCellValue(attendanceLogDTO.getShiftDuration());
				row.createCell(9).setCellValue(attendanceLogDTO.getInTime());
				row.createCell(10).setCellValue(attendanceLogDTO.getStatus());

			}

		}

		for (int i = 0; i < columns.length; i++) {
			sheet.autoSizeColumn(i);
		}
		return workbook;
	}

	public static Workbook leaveEncashedSummaryExcelWriter(Object[] newColumns, List<AttendanceDTO> leaveEncashedList,
			Date startDate, Date endDate, String[] typesOfLeavesId, int leavesSize) {
		// TODO Auto-generated method stub
		Workbook workbook = new XSSFWorkbook(); // new HSSFWorkbook() for generating `.xls` file

		CreationHelper createHelper = workbook.getCreationHelper();

		Sheet sheet = workbook.createSheet("Leave Encashed Summary");

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
		headerCellStyle.setWrapText(true);

		CellStyle headerCellStyle1 = workbook.createCellStyle();
		headerCellStyle1.setFont(headerFont);
		headerCellStyle1.setAlignment(HorizontalAlignment.CENTER);
		headerCellStyle1.setVerticalAlignment(VerticalAlignment.CENTER);
		headerCellStyle1.setFillBackgroundColor((IndexedColors.GREY_50_PERCENT.getIndex()));
		headerCellStyle1.setFillPattern(FillPatternType.FINE_DOTS);

		CellStyle headerCellStyle12 = workbook.createCellStyle();
		headerCellStyle12.setFont(headerFont);
		headerCellStyle12.setAlignment(HorizontalAlignment.CENTER);
		headerCellStyle12.setVerticalAlignment(VerticalAlignment.CENTER);
		headerCellStyle12.setFillBackgroundColor((IndexedColors.GREY_50_PERCENT.getIndex()));
		headerCellStyle12.setFillPattern(FillPatternType.FINE_DOTS);
		headerCellStyle12.setWrapText(true);

		CellStyle headerCellStyleCenter = workbook.createCellStyle();
		headerCellStyleCenter.setFont(headerFont);
		headerCellStyleCenter.setAlignment(HorizontalAlignment.CENTER);
		headerCellStyleCenter.setVerticalAlignment(VerticalAlignment.CENTER);

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

		CellStyle addZero = workbook.createCellStyle();
		addZero.setDataFormat(createHelper.createDataFormat().getFormat("0.00"));
		addZero.setAlignment(HorizontalAlignment.RIGHT);
		addZero.setVerticalAlignment(VerticalAlignment.CENTER);

		CellStyle addZeroCenter = workbook.createCellStyle();
		addZeroCenter.setDataFormat(createHelper.createDataFormat().getFormat("0.00"));
		addZeroCenter.setAlignment(HorizontalAlignment.CENTER);
		addZeroCenter.setVerticalAlignment(VerticalAlignment.CENTER);

		CellStyle addZeroBold = workbook.createCellStyle();
		addZeroBold.setDataFormat(createHelper.createDataFormat().getFormat("0.00"));
		addZeroBold.setFont(headerFont);
		addZeroBold.setAlignment(HorizontalAlignment.RIGHT);
		addZeroBold.setVerticalAlignment(VerticalAlignment.CENTER);

		Row row0 = sheet.createRow(0);
		sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 9));
		Cell cellCom = row0.createCell(0);
		cellCom.setCellValue("Leave Encashed Summary");
		cellCom.setCellStyle(headerCellStyleCenter);

		CellStyle dateCellStyle = workbook.createCellStyle();
		dateCellStyle.setDataFormat(createHelper.createDataFormat().getFormat("dd-MM-yyyy"));
		CellStyle dateCellStyles = workbook.createCellStyle();
		dateCellStyles.setDataFormat(createHelper.createDataFormat().getFormat(""));

		String dateFrom = null;
		SimpleDateFormat s1 = new SimpleDateFormat("dd-MMM-yyyy");
		dateFrom = s1.format(startDate);

		String dateTo = null;
		SimpleDateFormat s2 = new SimpleDateFormat("dd-MMM-yyyy");
		dateTo = s2.format(endDate);

		Row headerRow1 = sheet.createRow(1);
		sheet.addMergedRegion(new CellRangeAddress(1, 1, 0, 9));
		Cell cell1 = headerRow1.createCell(0);
		cell1.setCellValue(dateFrom + " To " + dateTo);
		cell1.setCellStyle(headerCellStyleCenter);

		Row headerRow2 = sheet.createRow(2);
		Row headerRow3 = sheet.createRow(3);

		for (int i = 0; i <= 5; i++) {
			sheet.addMergedRegion(new CellRangeAddress(2, 3, i, i));
			Cell cell = headerRow2.createCell(i);
			cell.setCellValue("  " + newColumns[i].toString() + "   ");
			cell.setCellStyle(headerCellStyle);

		}

		for (int j = 6; j < newColumns.length; j++) {
			Cell cell = headerRow3.createCell(j);
			cell.setCellValue(newColumns[j].toString());
			cell.setCellStyle(headerCellStyle1);
		}

		if (leavesSize <= 1) {
			Cell cellCom1 = headerRow2.createCell(6);
			cellCom1.setCellValue("Closing Balance");
			cellCom1.setCellStyle(headerCellStyle12);
		} else {
			sheet.addMergedRegion(new CellRangeAddress(2, 2, 6, (leavesSize + 5)));
			Cell cellCom1 = headerRow2.createCell(6);
			cellCom1.setCellValue("Closing Balance");
			cellCom1.setCellStyle(headerCellStyle12);
		}

		int leaveEncashSize = leavesSize + 6;
		int size = leaveEncashSize + leavesSize;

		if (leavesSize <= 1) {
			Cell cellCom11 = headerRow2.createCell(leaveEncashSize);
			cellCom11.setCellValue("Leave Encashed");
			cellCom11.setCellStyle(headerCellStyle12);
		} else {
			sheet.addMergedRegion(new CellRangeAddress(2, 2, leaveEncashSize, size - 1));
			Cell cellCom11 = headerRow2.createCell(leaveEncashSize);
			cellCom11.setCellValue("Leave Encashed");
			cellCom11.setCellStyle(headerCellStyle12);
		}

		int rowNum = 4;

		if (leaveEncashedList != null) {

			for (AttendanceDTO attendanceDTO : leaveEncashedList) {
				Row row = sheet.createRow(rowNum++);

				row.createCell(0).setCellValue(attendanceDTO.getEmployeeCode());
				row.createCell(1).setCellValue(attendanceDTO.getEmployeeName());
				row.createCell(2).setCellValue(attendanceDTO.getDepartment());
				row.createCell(3).setCellValue(attendanceDTO.getDesignation());
				row.createCell(4).setCellValue(attendanceDTO.getJobLocation());
				row.createCell(5).setCellValue(attendanceDTO.getReportingTo());

				Map<String, List<BigDecimal>> leaveClosedMap = attendanceDTO.getLeaveClosedBalanceMap();
				Map<String, List<BigDecimal>> leaveEncashMap = attendanceDTO.getLeaveEncashMap();

				int value = 6;
				BigDecimal closingBalanceLeave = new BigDecimal(0.0);
				BigDecimal leaveEncashed = new BigDecimal(0.0);

				if (leaveClosedMap != null) {
					for (int i = 0; i < typesOfLeavesId.length; i++) {
						for (Map.Entry<String, List<BigDecimal>> leaveData : leaveClosedMap.entrySet()) {

							List<BigDecimal> data = leaveData.getValue();
							String key = leaveData.getKey();
							// for (String a : typesOfLeavesExpiryId) {
							if (typesOfLeavesId[i].equals(key)) {

								if (data.get(0).compareTo(new BigDecimal(0.0)) > 0)
									closingBalanceLeave = (BigDecimal) data.get(0);
								else
									closingBalanceLeave = new BigDecimal(0.0);

								Cell cell5 = row.createCell(value++);
								cell5.setCellValue(closingBalanceLeave.doubleValue());
								cell5.setCellStyle(addZeroCenter);
								break;

							}
							// }
							if (!leaveClosedMap.containsKey(typesOfLeavesId[i])) {

								Cell cell5 = row.createCell(value++);
								cell5.setCellValue(closingBalanceLeave.doubleValue());
								cell5.setCellStyle(addZeroCenter);
								break;
							}
						}

						if (leaveClosedMap.isEmpty()) {
							Cell cell5 = row.createCell(value++);
							cell5.setCellValue(0.00);
							cell5.setCellStyle(addZeroCenter);
						}

					}
					Cell cell6 = row.createCell(value++);
					cell6.setCellValue(attendanceDTO.getClosingBalanceLeaveTotal() != null
							? attendanceDTO.getClosingBalanceLeaveTotal().doubleValue()
							: new BigDecimal(0.00).doubleValue());
					cell6.setCellStyle(addZeroCenter);

				}

				if (leaveEncashMap != null) {
					for (int i = 0; i < typesOfLeavesId.length; i++) {
						for (Map.Entry<String, List<BigDecimal>> leaveData : leaveEncashMap.entrySet()) {

							List<BigDecimal> data = leaveData.getValue();
							String key = leaveData.getKey();
							// for (String a : typesOfLeavesExpiryId) {
							if (typesOfLeavesId[i].equals(key)) {

								if (data.get(0).compareTo(new BigDecimal(0.0)) > 0)
									leaveEncashed = (BigDecimal) data.get(0);
								else
									leaveEncashed = new BigDecimal(0.0);

								Cell cell5 = row.createCell(value++);
								cell5.setCellValue(leaveEncashed.doubleValue());
								cell5.setCellStyle(addZeroCenter);
								break;

							}
							// }
							if (!leaveEncashMap.containsKey(typesOfLeavesId[i])) {

								Cell cell5 = row.createCell(value++);
								cell5.setCellValue(leaveEncashed.doubleValue());
								cell5.setCellStyle(addZeroCenter);
								break;
							}
						}

						if (leaveClosedMap.isEmpty()) {
							Cell cell5 = row.createCell(value++);
							cell5.setCellValue(0.00);
							cell5.setCellStyle(addZeroCenter);
						}

					}
					Cell cell8 = row.createCell(value++);
					cell8.setCellValue(attendanceDTO.getLeaveEncashedTotal() != null
							? attendanceDTO.getLeaveEncashedTotal().doubleValue()
							: new BigDecimal(0.00).doubleValue());
					cell8.setCellStyle(addZeroCenter);

				}

			}
		}

		for (int i = 0; i < newColumns.length; i++) {
			sheet.autoSizeColumn(i);
		}

		return workbook;
	}

	public static Workbook attendanceLogsSumReportWriter(List<AttendanceLogDTO> attendanceLogDTOList, String[] columns,
			Date fDate, Date tDate) {

		Workbook workbook = new XSSFWorkbook();

		CreationHelper createHelper = workbook.getCreationHelper();

		// Create a Sheet
		Sheet sheet = workbook.createSheet("Attendance Logs Summary");

		// Create a Font for styling header cells
		Font headerFont = workbook.createFont();
		headerFont.setBold(true);
		headerFont.setFontHeightInPoints((short) 12);
		headerFont.setColor(IndexedColors.BLACK.getIndex());

		Font rowFont = workbook.createFont();
		rowFont.setFontHeightInPoints((short) 11);
		rowFont.setColor(IndexedColors.BLACK.getIndex());

		// Create a CellStyle with the font
		CellStyle headerCellStyle = workbook.createCellStyle();
		headerCellStyle.setFont(headerFont);
		headerCellStyle.setAlignment(HorizontalAlignment.CENTER);
		headerCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
		headerCellStyle.setFillBackgroundColor(IndexedColors.LIGHT_BLUE.getIndex());
		headerCellStyle.setFillPattern(FillPatternType.FINE_DOTS);
		headerCellStyle.setFillBackgroundColor(IndexedColors.LIGHT_CORNFLOWER_BLUE.getIndex());

		CellStyle headerCellStyle2 = workbook.createCellStyle();
		headerCellStyle2.setFont(headerFont);
		headerCellStyle2.setAlignment(HorizontalAlignment.CENTER);
		headerCellStyle2.setVerticalAlignment(VerticalAlignment.CENTER);

		CellStyle rowHeadCellStyle = workbook.createCellStyle();
		rowHeadCellStyle.setFont(rowFont);
		rowHeadCellStyle.setAlignment(HorizontalAlignment.CENTER);
		rowHeadCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);

		CellStyle rowCellStyle = workbook.createCellStyle();
		rowCellStyle.setFont(rowFont);
		rowCellStyle.setAlignment(HorizontalAlignment.CENTER);
		rowCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);

		Row row0 = sheet.createRow(0);
		sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 11));
		Cell cellCom = row0.createCell(0);
		cellCom.setCellValue("Attendance Logs Summary");
		cellCom.setCellStyle(headerCellStyle2);

		Row row1 = sheet.createRow(1);
		sheet.addMergedRegion(new CellRangeAddress(1, 1, 0, 11));
		Cell cell1 = row1.createCell(0);
		cell1.setCellValue(
				"From " + DateUtils.getDateStrInDDMMMYYYY(fDate) + " to " + DateUtils.getDateStrInDDMMMYYYY(tDate));
		cell1.setCellStyle(headerCellStyle2);

		// Create a Row
		Row headerRow = sheet.createRow(2);

		// Creating cells
		for (int i = 0; i < columns.length; i++) {
			Cell cell = headerRow.createCell(i);
			cell.setCellValue(columns[i]);
			cell.setCellStyle(headerCellStyle);

		}

		int rowNum = 3;
		if (attendanceLogDTOList.size() > 0) {
			for (AttendanceLogDTO attendanceLogDto : attendanceLogDTOList) {

				Row row = sheet.createRow(rowNum++);

				CellStyle dateCellStyle = workbook.createCellStyle();
				dateCellStyle.setDataFormat(createHelper.createDataFormat().getFormat("dd-MMM-yyyy"));

				Cell cell = row.createCell(0);
				cell.setCellValue(attendanceLogDto.getAttendanceDate());
				cell.setCellStyle(dateCellStyle);

				row.createCell(1).setCellValue(attendanceLogDto.getEmployeeCode());
				row.createCell(2).setCellValue(attendanceLogDto.getEmployeeName());
				row.createCell(3).setCellValue(attendanceLogDto.getDepartmentName());
				row.createCell(4).setCellValue(attendanceLogDto.getDesignationName());
				row.createCell(5).setCellValue(attendanceLogDto.getJobLocation());
				row.createCell(6).setCellValue(attendanceLogDto.getReportingTo());
				row.createCell(7).setCellValue(attendanceLogDto.getShiftName());
				row.createCell(8).setCellValue(attendanceLogDto.getShiftDuration());
				row.createCell(9).setCellValue(attendanceLogDto.getMode());
				row.createCell(10).setCellValue(attendanceLogDto.getInTime());
				row.createCell(11).setCellValue(attendanceLogDto.getOutTime());
				row.createCell(12).setCellValue(attendanceLogDto.getTotalTime());
				row.createCell(13).setCellValue(attendanceLogDto.getStatus());
				row.createCell(14).setCellValue(attendanceLogDto.getLateBy());
				row.createCell(15).setCellValue(attendanceLogDto.getEarlyBy());
				row.createCell(16).setCellValue(attendanceLogDto.getEarlyBefore());
				row.createCell(17).setCellValue(attendanceLogDto.getLocationTimeIn());
				row.createCell(18).setCellValue(attendanceLogDto.getLocationTimeOut());

			}
		} else {

			Row row = sheet.createRow(3);
			sheet.addMergedRegion(new CellRangeAddress(3, 6, 0, columns.length - 1));
			Cell cell = row.createCell(0);
			cell.setCellValue(" ----Data is not available---- ");
			cell.setCellStyle(headerCellStyle2);

		}

		// Resize all columns to fit the content size
		for (int i = 0; i < columns.length; i++) {
			sheet.autoSizeColumn(i);
		}

		return workbook;
	}

	public static Workbook leaveExpirySummaryExcelWriter(Object[] newColumns, List<AttendanceDTO> leaveExpiryList,
			Date startDate, Date endDate, String[] typesOfLeavesExpiryId) {
		Workbook workbook = new XSSFWorkbook(); // new HSSFWorkbook() for generating `.xls` file

		CreationHelper createHelper = workbook.getCreationHelper();

		Sheet sheet = workbook.createSheet("Leave Expiry Summary");

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
		headerCellStyle.setWrapText(true);

		CellStyle headerCellStyleCenter = workbook.createCellStyle();
		headerCellStyleCenter.setFont(headerFont);
		headerCellStyleCenter.setAlignment(HorizontalAlignment.CENTER);
		headerCellStyleCenter.setVerticalAlignment(VerticalAlignment.CENTER);

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

		CellStyle headerCellStyle113 = workbook.createCellStyle();
		headerCellStyle113.setFont(headerFont);
		headerCellStyle113.setAlignment(HorizontalAlignment.CENTER);
		headerCellStyle113.setVerticalAlignment(VerticalAlignment.CENTER);

		CellStyle addZeroCenter = workbook.createCellStyle();
		addZeroCenter.setDataFormat(createHelper.createDataFormat().getFormat("0.00"));
		addZeroCenter.setAlignment(HorizontalAlignment.CENTER);
		addZeroCenter.setVerticalAlignment(VerticalAlignment.CENTER);

		Row row0 = sheet.createRow(0);
		sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 10));
		Cell cellCom = row0.createCell(0);
		cellCom.setCellValue("Leave Expiry Summary");
		cellCom.setCellStyle(headerCellStyleCenter);

		CellStyle dateCellStyle = workbook.createCellStyle();
		dateCellStyle.setDataFormat(createHelper.createDataFormat().getFormat("dd-MM-yyyy"));

		CellStyle dateCellStyles = workbook.createCellStyle();
		dateCellStyles.setDataFormat(createHelper.createDataFormat().getFormat(""));

		String dateFrom = null;
		SimpleDateFormat s1 = new SimpleDateFormat("dd-MMM-yyyy");
		dateFrom = s1.format(startDate);

		String dateTo = null;
		SimpleDateFormat s2 = new SimpleDateFormat("dd-MMM-yyyy");
		dateTo = s2.format(endDate);

		Row headerRow1 = sheet.createRow(1);
		sheet.addMergedRegion(new CellRangeAddress(1, 1, 0, 10));
		Cell cell1 = headerRow1.createCell(0);
		// cell1.setCellValue(DateUtils.getchangeMonthDateFormat(fromdateString) + " To
		// " + DateUtils.getchangeMonthDateFormat(todateString));
		cell1.setCellValue(dateFrom + " To " + dateTo);
		cell1.setCellStyle(headerCellStyleCenter);

		Row headerRow = sheet.createRow(2);
		// Creating cells
		for (int i = 0; i < newColumns.length; i++) {
			Cell cell = headerRow.createCell(i);
			cell.setCellValue(newColumns[i].toString());
			cell.setCellStyle(headerCellStyle);

		}
		int rowNum = 3;

		if (leaveExpiryList != null) {

			for (AttendanceDTO attendanceDTO : leaveExpiryList) {
				Row row = sheet.createRow(rowNum++);

				row.createCell(0).setCellValue(attendanceDTO.getEmployeeCode());
				row.createCell(1).setCellValue(attendanceDTO.getEmployeeName());
				row.createCell(2).setCellValue(attendanceDTO.getDepartment());
				row.createCell(3).setCellValue(attendanceDTO.getDesignation());
				row.createCell(4).setCellValue(attendanceDTO.getJobLocation());
				row.createCell(5).setCellValue(attendanceDTO.getReportingTo());

				Map<String, List<BigDecimal>> leaveExpiryMap = attendanceDTO.getLeaveExpiryMap();
				int value = 6;

				BigDecimal expiryLeave = new BigDecimal(0.0);

				if (leaveExpiryMap != null) {
					for (int i = 0; i < typesOfLeavesExpiryId.length; i++) {
						for (Map.Entry<String, List<BigDecimal>> leaveExpiryData : leaveExpiryMap.entrySet()) {

							List<BigDecimal> data = leaveExpiryData.getValue();
							String key = leaveExpiryData.getKey();
							// for (String a : typesOfLeavesExpiryId) {
							if (typesOfLeavesExpiryId[i].equals(key)) {

								if (data.get(0).compareTo(new BigDecimal(0.0)) > 0)
									expiryLeave = (BigDecimal) data.get(0);
								else
									expiryLeave = new BigDecimal(0.0);

								Cell cell5 = row.createCell(value++);
								cell5.setCellValue(expiryLeave.doubleValue());
								cell5.setCellStyle(addZeroCenter);
								break;

							}
							// }
							if (!leaveExpiryMap.containsKey(typesOfLeavesExpiryId[i])) {

								Cell cell5 = row.createCell(value++);
								cell5.setCellValue(expiryLeave.doubleValue());
								cell5.setCellStyle(addZeroCenter);
								break;
							}
						}

						if (leaveExpiryMap.isEmpty()) {
							Cell cell5 = row.createCell(value++);
							cell5.setCellValue(0.00);
							cell5.setCellStyle(addZeroCenter);
						}

					}
				}

			}
		}
		for (int i = 0; i < newColumns.length; i++) {
			sheet.autoSizeColumn(i);
		}

		return workbook;

	}

	public static Workbook currentAttendanceReport(List<DeviceLogsInfoDTO> devicesLogDetails, String[] columns,
			String attendDate) throws IOException, InvalidFormatException {

		// Create a Workbook
		Workbook workbook = new XSSFWorkbook(); // new HSSFWorkbook() for generating `.xls` file

		/*
		 * CreationHelper helps us create instances for various things like DataFormat,
		 * Hyperlink, RichTextString etc, in a format (HSSF, XSSF) independent way
		 */
		CreationHelper createHelper = workbook.getCreationHelper();

		// Create a Sheet
		Sheet sheet = workbook.createSheet("Attendance Report");

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
		headerCellStyle1.setAlignment(HorizontalAlignment.CENTER);
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
		// Create Other rows and cells with employees data
//		Row row0 = sheet.createRow(0);
//		sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 2));
//		Cell cellCom = row0.createCell(0);
//		cellCom.setCellValue("");
//		cellCom.setCellStyle(headerCellStyle111);

		// add header format

		Row headerRow0 = sheet.createRow(0);

		sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 18));
		Cell cell0 = headerRow0.createCell(0);
		cell0.setCellValue("Attendance Logs Summary");
		cell0.setCellStyle(headerCellStyle1);

		Row headerRow1 = sheet.createRow(1);
//		String dateFrom = null;
//		SimpleDateFormat s1 = new SimpleDateFormat("dd-MMM-yyyy");
//		dateFrom = s1.format(attendDate);

		sheet.addMergedRegion(new CellRangeAddress(1, 1, 0, 18));
		Cell cell1 = headerRow1.createCell(0);
		cell1.setCellValue(attendDate);
		cell1.setCellStyle(headerCellStyle1);

		sheet.addMergedRegion(new CellRangeAddress(2, 2, 0, 2));

		// Create a Row
		Row headerRow = sheet.createRow(3);

		// Creating cells
		for (int i = 0; i < columns.length; i++) {
			Cell cell = headerRow.createCell(i);
			cell.setCellValue(columns[i]);
			cell.setCellStyle(headerCellStyle);

		}

		// Create Cell Style for formatting Date
		CellStyle dateCellStyle = workbook.createCellStyle();
		dateCellStyle.setDataFormat(createHelper.createDataFormat().getFormat("dd-MM-yyyy"));

		int rowNum = 4;
		int srNo = 1;

		for (DeviceLogsInfoDTO deviceLogsDto : devicesLogDetails) {
			// Date", "Employee Code","Employee", "Department","Job Location","Reporting
			// Manager","Shift","Shift Duration","Punching Mode","Time In","Time Out","Total
			// Hours","Attendance Status","Late By","Early By","Early Before", "Location
			// -Time In", "Location-Time Out"
			Row row = sheet.createRow(rowNum++);
			row.createCell(0).setCellValue(srNo++);
			row.createCell(1).setCellValue(deviceLogsDto.getAttedanceDate());
			row.createCell(2).setCellValue(deviceLogsDto.getEmployeeCode());
			row.createCell(3).setCellValue(deviceLogsDto.getName());
			row.createCell(4).setCellValue(deviceLogsDto.getDepartmentName());
			row.createCell(5).setCellValue(deviceLogsDto.getJobLocation());
			row.createCell(6).setCellValue(deviceLogsDto.getReportingTo());
			row.createCell(7).setCellValue(deviceLogsDto.getShift());
			row.createCell(8).setCellValue(deviceLogsDto.getShiftDuration());
			row.createCell(9).setCellValue(deviceLogsDto.getMode());
			row.createCell(10).setCellValue(deviceLogsDto.getTimeIn());
			row.createCell(11).setCellValue(deviceLogsDto.getTimeOut());
			row.createCell(12).setCellValue(deviceLogsDto.getTotalHours());
			row.createCell(13).setCellValue(deviceLogsDto.getLeaveStatus());
			row.createCell(14).setCellValue(deviceLogsDto.getLateBy());
			row.createCell(15).setCellValue(deviceLogsDto.getLeftEarlyBy());
			row.createCell(16).setCellValue(deviceLogsDto.getEarlyBefore());
			row.createCell(17).setCellValue(deviceLogsDto.getLocationIn());
			row.createCell(18).setCellValue(deviceLogsDto.getLocationOut());
		}
		return workbook;
	}

	/**
	 * @author ${Nisha Parveen}
	 *
	 */

	public static String getchangeDateFormat(String str) {
		String demo = str; // "2019-08-15" arr[0]=2019
		String arr[] = demo.split("-");
		Integer i = Integer.valueOf(arr[1]) - 1;
		String date = arr[2] + "-" + StatusMessage.month[i] + "-" + arr[0];
		return date.toString();
	}

	public static Workbook shiftScheduleSummaryWriter(List<AttendanceLogDTO> attendanceLogDtoList, String[] columns) {

		Workbook workbook = new XSSFWorkbook();

		CreationHelper createHelper = workbook.getCreationHelper();

		// Create a Sheet
		Sheet sheet = workbook.createSheet("Current Shift Schedule Summary");

		// Create a Font for styling header cells
		Font headerFont = workbook.createFont();
		headerFont.setBold(true);
		headerFont.setFontHeightInPoints((short) 12);
		headerFont.setColor(IndexedColors.BLACK.getIndex());

		Font rowFont = workbook.createFont();
		rowFont.setFontHeightInPoints((short) 11);
		rowFont.setColor(IndexedColors.BLACK.getIndex());

		// Create a CellStyle with the font
		CellStyle headerCellStyle = workbook.createCellStyle();
		headerCellStyle.setFont(headerFont);
		headerCellStyle.setAlignment(HorizontalAlignment.CENTER);
		headerCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);

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
		dateCellStyle.setDataFormat(createHelper.createDataFormat().getFormat("dd-MMM-yyyy"));
		dateCellStyle.setAlignment(HorizontalAlignment.CENTER);
		dateCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);

		Row row0 = sheet.createRow(0);

		sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, columns.length - 1));
		Cell cellCom = row0.createCell(0);
		cellCom.setCellValue(" Current Shift Schedule Summary ");
		cellCom.setCellStyle(headerCellStyle);

//		Row row1 = sheet.createRow(1);
//		sheet.addMergedRegion(new CellRangeAddress(1, 1, 0, columns.length - 1));
//		Cell cell1 = row1.createCell(0);
//		cell1.setCellValue(DateUtils.getDateStrInDDMMMYYYY(fDate) + " To " + DateUtils.getDateStrInDDMMMYYYY(tDate));
//		cell1.setCellStyle(headerCellStyle);

		// Create a Row
		Row headerRow = sheet.createRow(1);

		// Creating cells
		for (int i = 0; i < columns.length; i++) {
			Cell cell = headerRow.createCell(i);
			cell.setCellValue(columns[i]);
			cell.setCellStyle(headerCellStyle11);

		}

		if (attendanceLogDtoList.size() > 0) {
			int rowNum = 2;
			for (AttendanceLogDTO attendanceLogDto : attendanceLogDtoList) {
				Row row = sheet.createRow(rowNum++);

				row.createCell(0).setCellValue(attendanceLogDto.getEmployeeCode());
				row.createCell(1).setCellValue(attendanceLogDto.getEmployeeName());
				row.createCell(2).setCellValue(attendanceLogDto.getDepartmentName());
				row.createCell(3).setCellValue(attendanceLogDto.getDesignationName());
				row.createCell(4).setCellValue(attendanceLogDto.getJobLocation());
				row.createCell(5).setCellValue(attendanceLogDto.getReportingTo());
				row.createCell(6).setCellValue(attendanceLogDto.getShiftName());
				row.createCell(7).setCellValue(attendanceLogDto.getShiftDuration());

			}

		} else {
			Row row = sheet.createRow(2);

			sheet.addMergedRegion(new CellRangeAddress(2, 6, 0, columns.length - 1));
			Cell cell = row.createCell(0);
			cell.setCellValue("--Data Not Available -- ");
			cell.setCellStyle(headerCellStyle);

		}

		// Resize all columns to fit the content size
		for (int i = 0; i < columns.length; i++) {
			sheet.autoSizeColumn(i);
		}

		return workbook;

	}
}