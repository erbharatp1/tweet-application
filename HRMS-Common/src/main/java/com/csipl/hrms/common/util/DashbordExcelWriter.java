package com.csipl.hrms.common.util;

import java.util.List;

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
import com.csipl.hrms.dto.report.EmployeeReportDTO;

public class DashbordExcelWriter {

	public static Workbook empDueDcoumentReport(List<EmployeeReportDTO> empDueDcoumentList, String[] columns) {

		Workbook workbook = new XSSFWorkbook(); // new HSSFWorkbook() for generating `.xls` file

		Sheet sheet = workbook.createSheet("Documents Dues List");

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
		headerCellTitleStyle.setAlignment(HorizontalAlignment.CENTER);
		headerCellTitleStyle.setVerticalAlignment(VerticalAlignment.CENTER);

		CellStyle headerCellStyle = workbook.createCellStyle();
		headerCellStyle.setFont(headerFont);
		headerCellStyle.setAlignment(HorizontalAlignment.CENTER);
		headerCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
		headerCellStyle.setFillBackgroundColor(IndexedColors.LIGHT_CORNFLOWER_BLUE.getIndex());
		headerCellStyle.setFillPattern(FillPatternType.FINE_DOTS);
        
		CellStyle centerCellData=workbook.createCellStyle();
		centerCellData.setAlignment(HorizontalAlignment.CENTER);
		centerCellData.setVerticalAlignment(VerticalAlignment.CENTER);		

		Row row0 = sheet.createRow(0);
		sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 14));
		Cell cellCom = row0.createCell(0);
		cellCom.setCellValue("Documents Dues List");
		cellCom.setCellStyle(headerCellTitleStyle);

		Row headerRow = sheet.createRow(1);
		// Creating cells
		for (int i = 0; i < columns.length; i++) {
			Cell cell = headerRow.createCell(i);
			cell.setCellValue(columns[i]);
			cell.setCellStyle(headerCellStyle);

		}

		int rowNum = 2;

		if (empDueDcoumentList != null) {

			for (EmployeeReportDTO dueDcoumentObj : empDueDcoumentList) {

				Row row = sheet.createRow(rowNum++);

				row.createCell(0).setCellValue(dueDcoumentObj.getEmpCode());
				row.createCell(1).setCellValue(dueDcoumentObj.getEmpName());
				row.createCell(2).setCellValue(dueDcoumentObj.getEmpDesignation());
				row.createCell(3).setCellValue(dueDcoumentObj.getEmpDetp());

				if (dueDcoumentObj.getuId() == null) {
				 Cell cell=	row.createCell(4);
				 cell.setCellValue("-");
				 cell.setCellStyle(centerCellData);
				} else {
					 Cell cell=	row.createCell(4);
					 cell.setCellValue(StatusMessage.PENDING_VALUE);
					 cell.setCellStyle(centerCellData);
				}

				if (dueDcoumentObj.getuA() == null) {
					 Cell cell=	row.createCell(5);
					 cell.setCellValue("-");
					 cell.setCellStyle(centerCellData);
				} else {
					 Cell cell=	row.createCell(5);
					 cell.setCellValue(StatusMessage.PENDING_VALUE);
					 cell.setCellStyle(centerCellData);
				}

				if (dueDcoumentObj.geteS() == null) {
					 Cell cell=	row.createCell(6);
					 cell.setCellValue("-");
					 cell.setCellStyle(centerCellData);
				} else {
					 Cell cell=	row.createCell(6);
					 cell.setCellValue(StatusMessage.PENDING_VALUE);
					 cell.setCellStyle(centerCellData);
				}

				if (dueDcoumentObj.getbA() == null) {
					Cell cell=	row.createCell(7);
					 cell.setCellValue("-");
					 cell.setCellStyle(centerCellData);
				} else {
					Cell cell=	row.createCell(7);
					 cell.setCellValue(StatusMessage.PENDING_VALUE);
					 cell.setCellStyle(centerCellData);
				}

				if (dueDcoumentObj.getaI() == null) {
					Cell cell=	row.createCell(8);
					 cell.setCellValue("-");
					 cell.setCellStyle(centerCellData);
				} else {
					Cell cell=	row.createCell(8);
					 cell.setCellValue(StatusMessage.PENDING_VALUE);
					 cell.setCellStyle(centerCellData);
				}

				if (dueDcoumentObj.getmI() == null) {
					 Cell cell=	row.createCell(9);
					 cell.setCellValue("-");
					 cell.setCellStyle(centerCellData);
				} else {
					Cell cell=	row.createCell(9);
					 cell.setCellValue(StatusMessage.PENDING_VALUE);
					 cell.setCellStyle(centerCellData);
				}
				if (dueDcoumentObj.getPan() == null) {
					 Cell cell=	row.createCell(10);
					 cell.setCellValue("-");
					 cell.setCellStyle(centerCellData);
				} else {
					Cell cell=	row.createCell(10);
					 cell.setCellValue(StatusMessage.PENDING_VALUE);
					 cell.setCellStyle(centerCellData);
				}
			}

		}
		for (int i = 0; i < columns.length; i++) {
			sheet.autoSizeColumn(i);
		}

		return workbook;

	}

}
