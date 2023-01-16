package com.csipl.hrms.common.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import com.csipl.hrms.dto.employee.EmployeeAssetDTO;
import com.csipl.hrms.dto.employee.EmployeeRoleSummaryDTO;
import com.csipl.hrms.dto.employee.EmployeeTicketSummaryDTO;
import com.csipl.hrms.dto.payroll.ArrearReportPayOutDTO;

public class MiscellaneousExcelWriter {

	public static Workbook assetAllocationReport(List<EmployeeAssetDTO> assetAllocationList, String[] columns) {
		
		Workbook workbook = new XSSFWorkbook(); // new HSSFWorkbook() for generating `.xls` file

		CreationHelper createHelper = workbook.getCreationHelper();

		Sheet sheet = workbook.createSheet("Asset Allocation");

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
		// TODO Auto-generated method stub
		
		
		Row row0 = sheet.createRow(0);
		sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 7));
		Cell cellCom = row0.createCell(0);
		cellCom.setCellValue("Asset Summary ");
		cellCom.setCellStyle(headerCellStyle111);

		Row headerRow = sheet.createRow(1);
		// Creating cells
		for (int i = 0; i < columns.length; i++) {
			Cell cell = headerRow.createCell(i);
			cell.setCellValue(columns[i]);
			cell.setCellStyle(headerCellStyle);

		}

		int rowNum = 2;

		if (assetAllocationList != null) {

			for (EmployeeAssetDTO employeeAsset : assetAllocationList) {

				Row row = sheet.createRow(rowNum++);

				row.createCell(0).setCellValue(employeeAsset.getEmployeeCode());
				row.createCell(1).setCellValue(employeeAsset.getEmployeeName());
				row.createCell(2).setCellValue(employeeAsset.getEmployeeDesignation());
				row.createCell(3).setCellValue(employeeAsset.getDepartment());
				row.createCell(4).setCellValue(employeeAsset.getItemName());
				row.createCell(5).setCellValue(employeeAsset.getIssueDescription());

				Cell cell0 = row.createCell(6);
				if (employeeAsset.getReceivedOn() != null) {
					cell0.setCellValue(
							employeeAsset.getAllocatedOn() + " " + "To" + " " + employeeAsset.getReceivedOn());
					cell0.setCellStyle(dateCellStyle);
				} else {
					cell0.setCellValue(employeeAsset.getAllocatedOn());
					cell0.setCellStyle(dateCellStyle);
				}

				row.createCell(7).setCellValue(employeeAsset.getRecievedRemark());

			}

		}
		for (int i = 0; i < columns.length; i++) {
			sheet.autoSizeColumn(i);
		}

		return workbook;
	}
	

	// current Role Summary Report
	public static Workbook currentRoleSummaryReport(List<EmployeeRoleSummaryDTO> currentRoleSummaryReportList,
			String[] columns) {
		Workbook workbook = new XSSFWorkbook(); // new HSSFWorkbook() for generating `.xls` file

		Sheet sheet = workbook.createSheet("Current Role Summary");

		Font headerFont = workbook.createFont();
		headerFont.setBold(true);
		headerFont.setFontHeightInPoints((short) 12);
		headerFont.setColor(IndexedColors.BLACK.getIndex());

		CellStyle headerCellTitleStyle = workbook.createCellStyle();
		headerCellTitleStyle.setFont(headerFont);
		headerCellTitleStyle.setAlignment(HorizontalAlignment.CENTER);
		headerCellTitleStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        
		CellStyle dataNotFoundStyle = workbook.createCellStyle();
		dataNotFoundStyle.setFont(headerFont);
		dataNotFoundStyle.setAlignment(HorizontalAlignment.CENTER);
		dataNotFoundStyle.setVerticalAlignment(VerticalAlignment.CENTER);

		CellStyle centerCellData = workbook.createCellStyle();
		centerCellData.setAlignment(HorizontalAlignment.CENTER);
		centerCellData.setVerticalAlignment(VerticalAlignment.CENTER);

		CellStyle headerCellStyle = workbook.createCellStyle();
		headerCellStyle.setFont(headerFont);
		headerCellStyle.setAlignment(HorizontalAlignment.CENTER);
		headerCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
		headerCellStyle.setFillBackgroundColor(IndexedColors.LIGHT_CORNFLOWER_BLUE.getIndex());
		headerCellStyle.setFillPattern(FillPatternType.FINE_DOTS);

		Row row0 = sheet.createRow(0);
		sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 5));
		Cell cellCom = row0.createCell(0);
		cellCom.setCellValue("Role Summary");
		cellCom.setCellStyle(headerCellTitleStyle);

		Row headerRow = sheet.createRow(1);
		// Creating cells
		for (int i = 0; i < columns.length; i++) {
			Cell cell = headerRow.createCell(i);
			cell.setCellValue(columns[i]);
			cell.setCellStyle(headerCellStyle);

		}

		int rowNum = 2;

		if (currentRoleSummaryReportList.size()>=1) {

			for (EmployeeRoleSummaryDTO currentRoleSummaryReportObj : currentRoleSummaryReportList) {

				Row row = sheet.createRow(rowNum++);

				row.createCell(0).setCellValue(currentRoleSummaryReportObj.getEmployeeCode());
				row.createCell(1).setCellValue(currentRoleSummaryReportObj.getEmployeeName());
				row.createCell(2).setCellValue(currentRoleSummaryReportObj.getEmployeeDesignation());
				row.createCell(3).setCellValue(currentRoleSummaryReportObj.getEmployeeDepartment());

				Cell cellRole = row.createCell(4);
				cellRole.setCellValue(currentRoleSummaryReportObj.getEmployeeRole());
				cellRole.setCellStyle(centerCellData);

				Cell cellStatus = row.createCell(5);
				cellStatus.setCellValue(currentRoleSummaryReportObj.getActiveStatus());
				cellStatus.setCellStyle(centerCellData);

			}

		} else {
			Row row = sheet.createRow(4);
			sheet.addMergedRegion(new CellRangeAddress(3, 4, 0, 5));
			Cell dataNotFoundCell = row.createCell(0);
			dataNotFoundCell.setCellValue("No data found");
			dataNotFoundCell.setCellStyle(dataNotFoundStyle);

		}
		for (int i = 0; i < columns.length; i++) {
			sheet.autoSizeColumn(i);
		}

		return workbook;

	}


	// Ticket Summary Report
		public static Workbook ticketSummaryReport(List<EmployeeTicketSummaryDTO> tickrtSummaryReportList, String[] columns,
				Date startDate, Date endDate) throws ParseException {
			Workbook workbook = new XSSFWorkbook();

			Sheet sheet = workbook.createSheet("Ticket  Summary");

			/*
			 * XSSFDataFormat df = (XSSFDataFormat) workbook.createDataFormat(); CellStyle
			 * cs = workbook.createCellStyle();
			 * cs.setDataFormat(df.getFormat("dd-mmm-yyyy"));
			 */
			CreationHelper createHelper = workbook.getCreationHelper();

			Font headerFont = workbook.createFont();
			headerFont.setBold(true);
			headerFont.setFontHeightInPoints((short) 12);
			headerFont.setColor(IndexedColors.BLACK.getIndex());

			Font titleFont = workbook.createFont();
			titleFont.setBold(true);
			titleFont.setFontHeightInPoints((short) 15);
			titleFont.setColor(IndexedColors.BLACK.getIndex());

			CellStyle headerCellTitleStyle = workbook.createCellStyle();
			headerCellTitleStyle.setFont(titleFont);
			headerCellTitleStyle.setDataFormat(createHelper.createDataFormat().getFormat("dd-mmm-yyyy"));
			headerCellTitleStyle.setAlignment(HorizontalAlignment.LEFT);
			headerCellTitleStyle.setVerticalAlignment(VerticalAlignment.CENTER);

			CellStyle dataNotFoundStyle = workbook.createCellStyle();
			dataNotFoundStyle.setFont(headerFont);
			dataNotFoundStyle.setAlignment(HorizontalAlignment.CENTER);
			dataNotFoundStyle.setVerticalAlignment(VerticalAlignment.CENTER);

			CellStyle centerCellData = workbook.createCellStyle();
			centerCellData.setAlignment(HorizontalAlignment.CENTER);
			centerCellData.setVerticalAlignment(VerticalAlignment.CENTER);

			CellStyle headerCellStyle = workbook.createCellStyle();
			headerCellStyle.setFont(headerFont);
			headerCellStyle.setAlignment(HorizontalAlignment.CENTER);
			headerCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
			headerCellStyle.setFillBackgroundColor(IndexedColors.LIGHT_CORNFLOWER_BLUE.getIndex());
			headerCellStyle.setFillPattern(FillPatternType.FINE_DOTS);

			CellStyle commentCellStyle = workbook.createCellStyle();
			commentCellStyle.setFont(headerFont);
			commentCellStyle.setFillBackgroundColor(IndexedColors.LIGHT_CORNFLOWER_BLUE.getIndex());
			commentCellStyle.setFillPattern(FillPatternType.FINE_DOTS);

			CellStyle commentStyle = workbook.createCellStyle();
			commentStyle.setWrapText(true);

			CellStyle dateStyle = workbook.createCellStyle();
			dateStyle.setDataFormat(createHelper.createDataFormat().getFormat("dd-mmm-yyyy"));
			dateStyle.setAlignment(HorizontalAlignment.CENTER);
			dateStyle.setVerticalAlignment(VerticalAlignment.CENTER);

			// Format date for excel heading
			DateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy");
			String fromDate = dateFormat.format(startDate);
			String toDate = dateFormat.format(endDate);

			Row row0 = sheet.createRow(0);
			sheet.addMergedRegion(new CellRangeAddress(0, 1, 0, 9));
			Cell cellCom = row0.createCell(0);
			cellCom.setCellValue("Ticket Summary-" + fromDate + " To " + toDate);
			cellCom.setCellStyle(headerCellTitleStyle);

			Row headerRow = sheet.createRow(2);

			for (int i = 0; i < columns.length; i++) {
				Cell cell = headerRow.createCell(i);
				if (columns[i] == "Comments") {
					cell.setCellValue(columns[i]);
					cell.setCellStyle(commentCellStyle);
				} else {
					cell.setCellValue(columns[i]);
					cell.setCellStyle(headerCellStyle);
				}

			}
			// store comments in map

			Map<Long, String> map = new HashMap<Long, String>();
			String comment = "";

			for (EmployeeTicketSummaryDTO tickrtSummaryReportObj : tickrtSummaryReportList) {

				if (!map.containsKey(tickrtSummaryReportObj.getTicketId())) {
					comment = "";
				}

				// map.put(listdata.getEmpId(), listdata.getPassword());
				if (map.containsKey(tickrtSummaryReportObj.getTicketId())) {
					comment = comment + "\n" + tickrtSummaryReportObj.getComments().replaceAll("[\\\r\\\n]+", "").trim();
					map.put(tickrtSummaryReportObj.getTicketId(), comment);

				} else {
					comment = tickrtSummaryReportObj.getComments().replaceAll("[\\\r\\\n]+", "").trim();
					map.put(tickrtSummaryReportObj.getTicketId(), tickrtSummaryReportObj.getComments());
				}

			}

			int rowNum = 3;

			if (tickrtSummaryReportList.size() >= 1) {
				Long ticketChecker = 0l;
				for (EmployeeTicketSummaryDTO currentTicketSummaryReportObj : tickrtSummaryReportList) {

					if (currentTicketSummaryReportObj.getTicketId() != ticketChecker) {
						ticketChecker = currentTicketSummaryReportObj.getTicketId();

						Row row = sheet.createRow(rowNum++);

						Cell cell0 = row.createCell(0);
						cell0.setCellValue(currentTicketSummaryReportObj.getEmployeeCode());
						cell0.setCellStyle(centerCellData);

						Cell cell1 = row.createCell(1);
						cell1.setCellValue(currentTicketSummaryReportObj.getEmployeeName());
						cell1.setCellStyle(centerCellData);

						Cell cell2 = row.createCell(2);
						cell2.setCellValue(currentTicketSummaryReportObj.getEmployeeDesignation());
						cell2.setCellStyle(centerCellData);

						Cell cell3 = row.createCell(3);
						cell3.setCellValue(currentTicketSummaryReportObj.getEmployeeDepartment());
						cell3.setCellStyle(centerCellData);

						Cell cell4 = row.createCell(4);
						cell4.setCellValue(currentTicketSummaryReportObj.getTicketNo());
						cell4.setCellStyle(centerCellData);

						Cell cell5 = row.createCell(5);
						cell5.setCellValue(currentTicketSummaryReportObj.getCategory());
						cell5.setCellStyle(centerCellData);

						Cell cell6 = row.createCell(6);
						cell6.setCellValue(currentTicketSummaryReportObj.getDatedOn());
						cell6.setCellStyle(dateStyle);

						Cell cell7 = row.createCell(7);
						cell7.setCellValue(currentTicketSummaryReportObj.getStatus());
						cell7.setCellStyle(centerCellData);

						Cell cell8 = row.createCell(8);
						cell8.setCellValue(currentTicketSummaryReportObj.getClosedOn());
						cell8.setCellStyle(dateStyle);

						if (map.containsKey(currentTicketSummaryReportObj.getTicketId())) {
							Cell commentCell = row.createCell(9);
							commentCell.setCellStyle(commentStyle);
							commentCell.setCellValue(map.get(currentTicketSummaryReportObj.getTicketId()));
						}
					}

				}

			} else {
				Row row = sheet.createRow(4);
				sheet.addMergedRegion(new CellRangeAddress(4, 5, 0, 9));
				Cell dataNotFoundCell = row.createCell(0);
				dataNotFoundCell.setCellValue("No data found");
				dataNotFoundCell.setCellStyle(dataNotFoundStyle);

			}

			for (int i = 0; i < columns.length; i++) {
				sheet.autoSizeColumn(i);
			}

			return workbook;

		}

}
