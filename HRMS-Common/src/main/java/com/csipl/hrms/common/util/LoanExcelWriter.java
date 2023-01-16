package com.csipl.hrms.common.util;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;

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

import com.csipl.hrms.dto.payroll.LoanEMIDTO;
import com.csipl.hrms.dto.payroll.LoanIssueDTO;

public class LoanExcelWriter {
	public static Workbook loanConsolidatedStatementReport(List<LoanIssueDTO> LoanIssueDtoList, String[] columns,
			Long companyId, List<String> loanStatus) throws IOException, InvalidFormatException {

		// Create a Workbook
		Workbook workbook = new XSSFWorkbook();
		CreationHelper createHelper = workbook.getCreationHelper();
		Sheet sheet = workbook.createSheet("Loan Statement-Consolidated");

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
		headerCellStyle11.setAlignment(HorizontalAlignment.CENTER);
		headerCellStyle11.setVerticalAlignment(VerticalAlignment.CENTER);
		headerCellStyle11.setFillBackgroundColor(IndexedColors.LIGHT_CORNFLOWER_BLUE.getIndex());

		CellStyle headerCellStyle1 = workbook.createCellStyle();
		headerCellStyle1.setAlignment(HorizontalAlignment.CENTER);
		headerCellStyle1.setVerticalAlignment(VerticalAlignment.CENTER);

		CellStyle cellStyle112 = workbook.createCellStyle();
		cellStyle112.setAlignment(HorizontalAlignment.LEFT);
		cellStyle112.setVerticalAlignment(VerticalAlignment.CENTER);

		CellStyle date = workbook.createCellStyle();
		date.setDataFormat(createHelper.createDataFormat().getFormat("0.00"));
		date.setAlignment(HorizontalAlignment.LEFT);
		date.setVerticalAlignment(VerticalAlignment.CENTER);

		Row row0 = sheet.createRow(0);
		sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 12));
		Cell cellCom = row0.createCell(0);
		cellCom.setCellValue("Loan Statement-Consolidated");
		cellCom.setCellStyle(headerCellStyle11);

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

		CellStyle cellStyle1 = workbook.createCellStyle();
		date.setDataFormat(workbook.createDataFormat().getFormat("0.00"));

		BigDecimal loanAmount = new BigDecimal(0);
		BigDecimal outStandingAmount = new BigDecimal(0);
		BigDecimal loanRecovered = new BigDecimal(0);

		int rowNum = 2;
		if (LoanIssueDtoList != null) {

			for (LoanIssueDTO loanIssueDTO : LoanIssueDtoList) {
				Row row = sheet.createRow(rowNum++);

				row.createCell(0).setCellValue(loanIssueDTO.getEmployeeCode());
				row.createCell(1).setCellValue(loanIssueDTO.getEmployeeName());
				row.createCell(2).setCellValue(loanIssueDTO.getDesignationName());
				row.createCell(3).setCellValue(loanIssueDTO.getDepartmentName());

				Cell cell4 = row.createCell(4);
				cell4.setCellValue((loanIssueDTO.getTransactionNo()));
				cell4.setCellStyle(cellStyle112);

				loanAmount = loanIssueDTO.getLoanAmount() != null ? loanIssueDTO.getLoanAmount() : BigDecimal.ZERO;

				Cell cell5 = row.createCell(5);
				cell5.setCellValue(loanAmount.doubleValue());
				cell5.setCellStyle(date);

				Cell cell6 = row.createCell(6);
				cell6.setCellValue(loanIssueDTO.getIssueDate());
				cell6.setCellStyle(dateCellStyle);

				row.createCell(7).setCellValue(loanIssueDTO.getLoanStatus());

				Cell cell8 = row.createCell(8);
				cell8.setCellValue(loanIssueDTO.getEmiStartDate());
				cell8.setCellStyle(dateCellStyle);

				Cell cell9 = row.createCell(9);
				cell9.setCellValue(String.valueOf(loanIssueDTO.getNoOfEmi()));
				cell9.setCellStyle(cellStyle112);

				Cell cell10 = row.createCell(10);
				cell10.setCellValue(String.valueOf(loanIssueDTO.getRemainingEmi()));
				cell10.setCellStyle(date);

				outStandingAmount = loanIssueDTO.getLoanPendingAmount() != null ? loanIssueDTO.getLoanPendingAmount()
						: BigDecimal.ZERO;
				Cell cell11 = row.createCell(11);
				cell11.setCellValue(outStandingAmount.doubleValue());
				cell11.setCellStyle(date);

				loanRecovered = loanIssueDTO.getTotalEmiAmount() != null ? loanIssueDTO.getTotalEmiAmount()
						: BigDecimal.ZERO;
				Cell cell12 = row.createCell(12);
				cell12.setCellValue(loanRecovered.doubleValue());
				cell12.setCellStyle(date);
			}
		}

		// Resize all columns to fit the content size
		for (int i = 0; i < columns.length; i++) {
			sheet.autoSizeColumn(i);
		}
		return workbook;

	}

	public static Workbook loanDetailedStatementReport(List<LoanIssueDTO> LoanIssueDtoList,
			LoanIssueDTO loanIssue, String[] rows, String[] columns, Long companyId,
			Long loanAccountNo) throws IOException, InvalidFormatException {
		// TODO Auto-generated method stub
		Workbook workbook = new XSSFWorkbook();
		CreationHelper creationHelper = workbook.getCreationHelper();
		Sheet sheet = workbook.createSheet("Loan Statement-Detailed");

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

		CellStyle headerCellStyle2 = workbook.createCellStyle();
		headerCellStyle2.setFont(headerFont);
		headerCellStyle2.setAlignment(HorizontalAlignment.LEFT);
		headerCellStyle2.setVerticalAlignment(VerticalAlignment.CENTER);
		headerCellStyle2.setFillBackgroundColor(IndexedColors.LIGHT_CORNFLOWER_BLUE.getIndex());

		CellStyle headerCellStyle3 = workbook.createCellStyle();
		headerCellStyle3.setFont(headerFont);
		headerCellStyle3.setAlignment(HorizontalAlignment.CENTER);
		headerCellStyle3.setVerticalAlignment(VerticalAlignment.CENTER);
		headerCellStyle3.setFillBackgroundColor(IndexedColors.LIGHT_CORNFLOWER_BLUE.getIndex());

		CellStyle headerCellStyle1 = workbook.createCellStyle();
		headerCellStyle1.setAlignment(HorizontalAlignment.CENTER);
		headerCellStyle1.setVerticalAlignment(VerticalAlignment.CENTER);

		CellStyle cellStyle112 = workbook.createCellStyle();
		cellStyle112.setAlignment(HorizontalAlignment.LEFT);
		cellStyle112.setVerticalAlignment(VerticalAlignment.CENTER);

		CellStyle cellStyleRight = workbook.createCellStyle();
		cellStyleRight.setAlignment(HorizontalAlignment.RIGHT);
		cellStyleRight.setVerticalAlignment(VerticalAlignment.CENTER);

		CellStyle date = workbook.createCellStyle();
		date.setDataFormat(workbook.createDataFormat().getFormat("0.00"));
		date.setAlignment(HorizontalAlignment.LEFT);
		date.setVerticalAlignment(VerticalAlignment.CENTER);

		CellStyle addZero = workbook.createCellStyle();
		addZero.setDataFormat(creationHelper.createDataFormat().getFormat("0.00"));
		addZero.setAlignment(HorizontalAlignment.CENTER);
		addZero.setVerticalAlignment(VerticalAlignment.CENTER);

		CellStyle addZeroRight = workbook.createCellStyle();
		addZeroRight.setDataFormat(creationHelper.createDataFormat().getFormat("0.00"));
		addZeroRight.setAlignment(HorizontalAlignment.RIGHT);
		addZeroRight.setVerticalAlignment(VerticalAlignment.CENTER);

		CellStyle addZeroBold = workbook.createCellStyle();
		addZeroBold.setDataFormat(creationHelper.createDataFormat().getFormat("0.00"));
		addZeroBold.setFont(headerFont);
		addZeroBold.setAlignment(HorizontalAlignment.CENTER);
		addZeroBold.setVerticalAlignment(VerticalAlignment.CENTER);

		CellStyle dateCellStyle = workbook.createCellStyle();
		dateCellStyle.setDataFormat(creationHelper.createDataFormat().getFormat("dd-mm-yyyy"));
		dateCellStyle.setAlignment(HorizontalAlignment.LEFT);
		dateCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);

		CellStyle dateCellStyleRight = workbook.createCellStyle();
		dateCellStyleRight.setDataFormat(creationHelper.createDataFormat().getFormat("dd-mm-yyyy"));
		dateCellStyleRight.setAlignment(HorizontalAlignment.RIGHT);
		dateCellStyleRight.setVerticalAlignment(VerticalAlignment.CENTER);

		CellStyle headerCellStyleTotal = workbook.createCellStyle();
		headerCellStyleTotal.setFillBackgroundColor(IndexedColors.LIGHT_CORNFLOWER_BLUE.getIndex());
		headerCellStyleTotal.setAlignment(HorizontalAlignment.LEFT);
		headerCellStyleTotal.setVerticalAlignment(VerticalAlignment.CENTER);

		Row row0 = sheet.createRow(0);
		sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 3));
		Cell cellCom = row0.createCell(0);
		cellCom.setCellValue("Loan Statement");
		cellCom.setCellStyle(headerCellStyle);

		BigDecimal loanAmount = new BigDecimal(0);
		int rowNum = 2;
		int index = 0;
		int value = 0;

		LoanIssueDTO loanIssueDTO = new LoanIssueDTO();

		if (LoanIssueDtoList.size() > 0)
			loanIssueDTO = LoanIssueDtoList.get(0);

		Row row11 = sheet.createRow(1);
		sheet.addMergedRegion(new CellRangeAddress(1, 1, 0, 3));
		Cell cellCom3 = row11.createCell(0);
		cellCom3.setCellValue(loanIssueDTO.getEmployeeName());
		cellCom3.setCellStyle(headerCellStyle2);

		for (int i = 0; i < 3; i++) {
			{
				Row rowHeader1 = sheet.createRow(rowNum++);

				for (int j = 0; j < 4; j++) {
					if (j == 0) {
						Cell cell2 = rowHeader1.createCell(j);
						cell2.setCellValue(rows[index]);
						cell2.setCellStyle(headerCellStyle2);
						index++;
					} else if (j == 2) {
						Cell cell2 = rowHeader1.createCell(j);
						cell2.setCellValue(rows[index]);
						cell2.setCellStyle(headerCellStyle2);
						index++;
					} else {

						if (value == 0) {
							Cell cellCom2 = rowHeader1.createCell(j);
							cellCom2.setCellValue(loanIssueDTO.getEmployeeCode());
							cellCom2.setCellStyle(cellStyle112);
							value++;
						}

						else if (value == 1) {

							Cell cellCom2 = rowHeader1.createCell(j);
							cellCom2.setCellValue(String.valueOf(loanIssueDTO.getLoanAccountNo()));
							cellCom2.setCellStyle(cellStyleRight);
							value++;
						} else if (value == 2) {
							Cell cellCom2 = rowHeader1.createCell(j);
							cellCom2.setCellValue(loanIssueDTO.getDesignationName());
							cellCom2.setCellStyle(cellStyle112);
							value++;
						} else if (value == 3) {
							loanAmount = loanIssueDTO.getLoanAmount() != null ? loanIssueDTO.getLoanAmount()
									: BigDecimal.ZERO;
							Cell cellCom2 = rowHeader1.createCell(j);
							cellCom2.setCellValue(loanAmount.doubleValue());
							cellCom2.setCellStyle(addZeroRight);
							value++;
						} else if (value == 4) {
							Cell cellCom2 = rowHeader1.createCell(j);
							cellCom2.setCellValue(loanIssueDTO.getDepartmentName());
							cellCom2.setCellStyle(cellStyle112);
							value++;
						} else if (value == 5) {
							Cell cellCom2 = rowHeader1.createCell(j);
							cellCom2.setCellValue(loanIssueDTO.getIssueDate());
							cellCom2.setCellStyle(dateCellStyleRight);
							value++;
						}
					}

				}

			}
		}

		LoanIssueDTO loanIssueDto = new LoanIssueDTO();

		if (LoanIssueDtoList.size() > 0)
			loanIssueDto = LoanIssueDtoList.get(0);

		Row row6 = sheet.createRow(6);
		sheet.addMergedRegion(new CellRangeAddress(6, 6, 0, 1));
		Cell cellCom1 = row6.createCell(0);
		cellCom1.setCellValue(("EMI Start from" + " " + loanIssueDTO.getEmiStartDate()));
		cellCom1.setCellStyle(dateCellStyle);

		Row rowHeader = sheet.createRow(7);
		for (int i = 0; i < columns.length; i++) {
			Cell cell = rowHeader.createCell(i);
			cell.setCellValue(columns[i]);
			cell.setCellStyle(headerCellStyle);
		}
		BigDecimal emiAmount = new BigDecimal(0);
		BigDecimal sumOfEmiAmount = new BigDecimal(0);
		BigDecimal emiSum = BigDecimal.ZERO;
		BigDecimal loanSum = BigDecimal.ZERO;
		BigDecimal emiRemaining = BigDecimal.ZERO;
		
		int rowNumber = 8;
		if (loanIssue != null ) {
			for (LoanEMIDTO loanIssuesDto : loanIssue.getLoanEmisDto()) {
		
				Row row = sheet.createRow(rowNumber++);
				Cell cell0 = row.createCell(0);
				cell0.setCellValue(loanIssuesDto.getEmiDate());
				cell0.setCellStyle(dateCellStyle);

				emiSum = emiSum.add(loanIssuesDto.getEmiAmount());
				emiAmount = loanIssuesDto.getEmiAmount() != null ? loanIssuesDto.getEmiAmount() : BigDecimal.ZERO;

				Cell cell1 = row.createCell(1);
				cell1.setCellValue(emiAmount.doubleValue());
				cell1.setCellStyle(addZero);

				//emiRemaining = loanIssuesDto.getRemaining() != null ? loanIssuesDto.getRemaining() : BigDecimal.ZERO;
				sumOfEmiAmount = emiAmount.add(sumOfEmiAmount);
				Cell cell2 = row.createCell(2);
				cell2.setCellValue(loanAmount.subtract(sumOfEmiAmount).doubleValue());
				cell2.setCellStyle(addZero);

				Cell cell3 = row.createCell(3);
				cell3.setCellValue(loanIssuesDto.getRemarks());
				cell3.setCellStyle(dateCellStyle);
			}

			headerCellStyle.setVerticalAlignment(VerticalAlignment.JUSTIFY);

			Row rowNew = sheet.createRow(rowNumber);

			Cell cell4 = rowNew.createCell(0);
			cell4.setCellValue("Total ");
			cell4.setCellStyle(headerCellStyleTotal);

			Cell cell5 = rowNew.createCell(1);
			cell5.setCellValue(emiSum.doubleValue());
			cell5.setCellStyle(addZeroBold);

			// Cell cell6 = rowNew.createCell(2);
			// cell6.setCellValue(sumOfEmiAmount.doubleValue());
			// cell6.setCellStyle(addZeroBold);

			// Resize all columns to fit the content size
			for (int i = 0; i < columns.length; i++) {
				sheet.autoSizeColumn(i);
			}

		}
		return workbook;
	}
}
