package com.csipl.tms.common.bulkupload;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellValue;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.FormulaEvaluator;

import com.csipl.hrms.model.employee.Employee;
import com.csipl.tms.model.leave.EmployeeOpeningLeaveMaster;
import com.csipl.tms.model.leave.TMSLeaveTypeMaster;

public class EmpLeaveUploadUtil {

	public EmpLeaveUploadUtil() {

	}

	public static final int Employee_Code = 0;
	// sheet 13 Employee Leave Opening
	public static final int LeaveType = 1;
	public static final int LeaveTypeID = 2;
	public static final int ConsumedLeave = 3;
	public static final int LeaveCarryForward = 3;

	public void buildEmployeeLeaveBalance(EmployeeOpeningLeaveMaster employeeLeaveBalance, Cell cell, int index,
			FormulaEvaluator evaluator, Map<Long, TMSLeaveTypeMaster> leaveTypeMap, StringBuilder stringBuilder)
			throws ParseException {

		if (index == Employee_Code) {
			if (cell == null || cell.getCellType() == Cell.CELL_TYPE_BLANK) {
				stringBuilder.append("Employee Code, ");
			} else {

				String cellStringValue = dataConverter(cell);
				if (!cellStringValue.equals("")) {
					Employee employee = new Employee();
					employee.setEmployeeCode(cell.getStringCellValue());
					employeeLeaveBalance.setEmployee(employee);
				}
			}
		} else if (index == LeaveTypeID) {

			if (cell == null || cell.getCellType() == Cell.CELL_TYPE_BLANK) {

				stringBuilder.append(" Leave Type, ");
			} else {
				CellValue cellValue = evaluator.evaluate(cell);
				if (cellValue != null) {
					Long leaveId = Math.round(cellValue.getNumberValue());
					TMSLeaveTypeMaster leaveIds = leaveTypeMap.get(leaveId);
					if (leaveIds != null) {
						if (leaveId != 0) {
							TMSLeaveTypeMaster tmsleaveTypeMaster = new TMSLeaveTypeMaster();
							tmsleaveTypeMaster.setLeaveId(leaveId);
							employeeLeaveBalance.setTmsleaveTypeMaster(tmsleaveTypeMaster);
						} else {
							stringBuilder.append("Leave IdType is missing, ");
						}
					}

					else {
						stringBuilder.append("Leave IdType is not present is database, ");
					}

				}
			}

		} else if (index == ConsumedLeave) {

			if (cell == null || cell.getCellType() == Cell.CELL_TYPE_BLANK) {
				stringBuilder.append(" consumed leave, ");
			} else {
				String cellStringValue = dataConverter(cell);
				if (!cellStringValue.equals("")) {
					Double consumeLeaves = Double.valueOf(cellStringValue);
					BigDecimal consumeLeavesvalue = new BigDecimal(consumeLeaves);
					employeeLeaveBalance.setNoOfOpening(consumeLeavesvalue);
					// .setIdNumber(cellStringValue);
				}

			}

		} else if (index == LeaveCarryForward) {

			if (cell == null || cell.getCellType() == Cell.CELL_TYPE_BLANK) {
				stringBuilder.append(" Leave carryforward, ");
			} else {
				String cellStringValue = dataConverter(cell);
				if (!cellStringValue.equals("")) {
					Double consumeLeaves = Double.valueOf(cellStringValue);
					BigDecimal consumeLeavesvalue = new BigDecimal(consumeLeaves);
					employeeLeaveBalance.setNoOfOpening(consumeLeavesvalue);
					// .setIdNumber(cellStringValue);
				}

			}

		}
	}

	// dataConverter
	private String dataConverter(Cell cell) {
		DataFormatter dataFormatter = new DataFormatter();
		String cellStringValue = dataFormatter.formatCellValue(cell);
		return cellStringValue;
	}

}
