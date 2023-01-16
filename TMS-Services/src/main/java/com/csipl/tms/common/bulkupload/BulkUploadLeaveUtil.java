package com.csipl.tms.common.bulkupload;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import com.csipl.hrms.common.exception.PayRollProcessException;
import com.csipl.hrms.dto.employee.EmployeeDTO;
import com.csipl.tms.model.leave.EmployeeOpeningLeaveMaster;
import com.csipl.tms.model.leave.TMSLeaveTypeMaster;

public class BulkUploadLeaveUtil {

	private static final Logger logger = LoggerFactory.getLogger(BulkUploadLeaveUtil.class);
	// To upload leave opening balance

	public List<EmployeeOpeningLeaveMaster> saveEmployeeLeaveOpeningBalance(MultipartFile file, EmployeeDTO employeeDto,
			Map<Long, TMSLeaveTypeMaster> leaveTypeMap, Map<Integer, StringBuilder> errorMap)
			throws IOException, EncryptedDocumentException, InvalidFormatException, PayRollProcessException {

		Workbook workbook = WorkbookFactory.create(file.getInputStream());
		logger.info("workbook is : " + workbook);
		List<EmployeeOpeningLeaveMaster> employeeLeave = new ArrayList<EmployeeOpeningLeaveMaster>();
		try {
			EmpLeaveUploadUtil infoUtill = new EmpLeaveUploadUtil();
			StringBuilder stringBuilder = new StringBuilder();

			Sheet sheet = workbook.getSheetAt(0);
			FormulaEvaluator evaluator = workbook.getCreationHelper().createFormulaEvaluator();
			int lastRow = sheet.getLastRowNum();

//			HashMap<Long, String> map = new LinkedHashMap<>();
//			for (int i = 0; i <= sheet.getRow(0).getLastCellNum(); i++) {
//				Cell cell = sheet.getRow(0).getCell(i);
//				if (cell == null || cell.getStringCellValue().equals("") || cell.getStringCellValue() == null) {
//					break;
//
//				} else {
//					String columnName = sheet.getRow(0).getCell(i).getStringCellValue();
//					columnName = columnName.replace("*", "");
//					Long colIndex = (long) cell.getColumnIndex();
//					map.put(colIndex, columnName);
//				}
//			}
//			System.out.println(map);
//			boolean check = checkIfExcelIsCorrect(map, headerMap);
//			if (check)
//				throw new PayRollProcessException("Please upload correct excel format");

			int errorIndex = 0;
			logger.info("============1" + "lastRow  --" + lastRow);
			logger.info("============2");
			for (int index = 2; index < lastRow; index++) {
				Row rowData = sheet.getRow(index);
				sheet.setColumnHidden(1, false);
				boolean flag = checkIfRowIsEmployeeLeaveOpening(rowData, infoUtill);
				if (flag)
					break;

				EmployeeOpeningLeaveMaster empLeaveOpening = new EmployeeOpeningLeaveMaster();
				empLeaveOpening.setUserId(employeeDto.getUserId());

				logger.info("============3");
				for (int colIx = infoUtill.Employee_Code; colIx <= infoUtill.ConsumedLeave; colIx++) {

					Cell cell = rowData.getCell(colIx);

					System.out.println(colIx);
					if (colIx == infoUtill.Employee_Code || colIx == infoUtill.LeaveTypeID
							|| colIx == infoUtill.ConsumedLeave) {

						if (colIx == infoUtill.Employee_Code) {
							String value = cell.getStringCellValue();
							if (value == null) {
								stringBuilder.append("Employee Code can't empty");
								throw new PayRollProcessException(stringBuilder.toString());
							}
						}
						infoUtill.buildEmployeeLeaveBalance(empLeaveOpening, cell, colIx, evaluator, leaveTypeMap,
								stringBuilder);
					}

				}
				logger.info("============4");
				if (stringBuilder.length() > 0) {
					errorMap.put(errorIndex, stringBuilder);
				} else {
					errorIndex++;
					logger.info("============5"); // addUniqueEmpID(empIdProof,employeeIdProof);
					employeeLeave.add(empLeaveOpening);
					workbook.close();

				}
			}

		} catch (IOException ex) {

			workbook.close();
			ex.printStackTrace();
			throw new PayRollProcessException("Internal Server error");
		} catch (ParseException e) {
			workbook.close();
			e.printStackTrace();
			throw new PayRollProcessException("Internal Server error");
		}
		return employeeLeave;
	}

	private boolean checkIfRowIsEmployeeLeaveOpening(Row row, EmpLeaveUploadUtil infoUtill) {
		boolean flag = false;

		for (int colIx = infoUtill.Employee_Code; colIx < infoUtill.ConsumedLeave; colIx++) {
			Cell cell = row.getCell(colIx);

			if (colIx == infoUtill.Employee_Code) {
				if (cell == null || cell.getCellType() == Cell.CELL_TYPE_BLANK) {
					flag = true;
					break;
				}
			}
		}

		return flag;
	}

	private boolean checkIfRowIsEmployeeLeaveCarryForward(Row row, EmpLeaveUploadUtil infoUtill) {
		boolean flag = false;

		for (int colIx = infoUtill.Employee_Code; colIx < infoUtill.LeaveCarryForward; colIx++) {
			Cell cell = row.getCell(colIx);

			if (colIx == infoUtill.Employee_Code) {
				if (cell == null || cell.getCellType() == Cell.CELL_TYPE_BLANK) {
					flag = true;
					break;
				}
			}
		}

		return flag;
	}

	public List<EmployeeOpeningLeaveMaster> saveEmployeeLeaveCarryForward(MultipartFile file, EmployeeDTO employeeDto,
			Map<Long, TMSLeaveTypeMaster> leaveTypeMap, Map<Integer, StringBuilder> errorMap)
			throws IOException, EncryptedDocumentException, InvalidFormatException, PayRollProcessException {

		Workbook workbook = WorkbookFactory.create(file.getInputStream());
		logger.info("workbook is : " + workbook);
		List<EmployeeOpeningLeaveMaster> employeeLeave = new ArrayList<EmployeeOpeningLeaveMaster>();
		try {
			EmpLeaveUploadUtil infoUtill = new EmpLeaveUploadUtil();
			StringBuilder stringBuilder = new StringBuilder();

			Sheet sheet = workbook.getSheetAt(0);
			FormulaEvaluator evaluator = workbook.getCreationHelper().createFormulaEvaluator();
			int lastRow = sheet.getLastRowNum();

			int errorIndex = 0;
			logger.info("============13" + "lastRow  --" + lastRow);
			logger.info("============14");
			for (int index = 2; index < lastRow; index++) {
				Row rowData = sheet.getRow(index);
				sheet.setColumnHidden(1, false);
				boolean flag = checkIfRowIsEmployeeLeaveCarryForward(rowData, infoUtill);
				if (flag)
					break;

				EmployeeOpeningLeaveMaster empLeaveOpening = new EmployeeOpeningLeaveMaster();
				empLeaveOpening.setUserId(employeeDto.getUserId());

				logger.info("============15");
				for (int colIx = infoUtill.Employee_Code; colIx <= infoUtill.LeaveCarryForward; colIx++) {

					Cell cell = rowData.getCell(colIx);

					System.out.println(colIx);
					if (colIx == infoUtill.Employee_Code || colIx == infoUtill.LeaveTypeID
							|| colIx == infoUtill.LeaveCarryForward) {

						if (colIx == infoUtill.Employee_Code) {
							String value = cell.getStringCellValue();
							if (value == null) {
								stringBuilder.append("Employee Code can't empty");
								throw new PayRollProcessException(stringBuilder.toString());
							}
						}
						infoUtill.buildEmployeeLeaveBalance(empLeaveOpening, cell, colIx, evaluator, leaveTypeMap,
								stringBuilder);
					}

				}
				logger.info("============16");
				if (stringBuilder.length() > 0) {
					errorMap.put(errorIndex, stringBuilder);
				} else {
					errorIndex++;
					logger.info("============17"); // addUniqueEmpID(empIdProof,employeeIdProof);
					employeeLeave.add(empLeaveOpening);
					workbook.close();

				}
			}

		} catch (IOException ex) {

			workbook.close();
			ex.printStackTrace();
			throw new PayRollProcessException("Internal Server error");
		} catch (ParseException e) {
			workbook.close();
			e.printStackTrace();
			throw new PayRollProcessException("Internal Server error");
		}
		return employeeLeave;
	}

	/*
	 * private boolean checkIfExcelIsCorrect(HashMap<Long, String> map, Map<Long,
	 * String> headerMap) { boolean flag = false;
	 * 
	 * if (map.size() != headerMap.size()) { return true; } try { for (Long k :
	 * headerMap.keySet()) { if (!map.get(k).equals(headerMap.get(k))) { return
	 * true; } }
	 * 
	 * for (Long y : map.keySet()) { if (!headerMap.containsKey(y)) { return true; }
	 * }
	 * 
	 * } catch (NullPointerException np) { return true; } return false; }
	 */

}
