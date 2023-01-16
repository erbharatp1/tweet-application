package com.csipl.tms.leave.adaptor;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.csipl.hrms.common.constant.StatusMessage;
import com.csipl.hrms.common.util.DateUtils;
import com.csipl.hrms.model.employee.Employee;
import com.csipl.tms.dto.leave.CompOffSearchDTO;
import com.csipl.tms.dto.leave.CompensatoryOffDTO;
import com.csipl.tms.model.leave.CompensatoryOff;

public class CompensatoryOffAdaptor {

	public List<CompensatoryOff> uiDtoToDatabaseModelList(List<CompensatoryOffDTO> uiobj) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<CompensatoryOffDTO> databaseModelToUiDtoList(List<CompensatoryOff> CompensatoryOffList) {
		List<CompensatoryOffDTO> compensatoryOffDTOList = new ArrayList<CompensatoryOffDTO>();
		for (CompensatoryOff compensatoryOff : CompensatoryOffList) {
			compensatoryOffDTOList.add(databaseModelToUiDto(compensatoryOff, null, null));
		}
		return compensatoryOffDTOList;
	}

	public CompensatoryOff uiDtoToDatabaseModel(CompensatoryOffDTO CompensatoryOffdto, Long leavePeriodId) {
		CompensatoryOff compensatoryOff = new CompensatoryOff();
		DateUtils dateUtils = new DateUtils();
		Date currentDate = dateUtils.getCurrentDate();
		compensatoryOff.setFromDate(CompensatoryOffdto.getFromDate());
		compensatoryOff.setToDate(CompensatoryOffdto.getToDate());
		compensatoryOff.setRemark(CompensatoryOffdto.getRemark());
		// compensatoryOff.setDays(CompensatoryOffdto.getDays());
		compensatoryOff.setHalfFullDay(CompensatoryOffdto.getHalf_fullDay());

		String halfFullDay = compensatoryOff.getHalfFullDay() != null ? compensatoryOff.getHalfFullDay() : "";

		if (halfFullDay.equals("H")) {

			compensatoryOff.setDays(CompensatoryOffdto.getDays());

		} else {

			long difference = compensatoryOff.getToDate().getTime() - compensatoryOff.getFromDate().getTime();
//			 long daysBetween= (difference / (1000*60*60*24)) + 1;
//			 compensatoryOffDTO.setDays(daysBetween);

			long daysBetween = (difference / (1000 * 60 * 60 * 24)) + 1;
			BigDecimal daysbtwn = new BigDecimal(daysBetween);
			compensatoryOff.setDays(daysbtwn);
		}

		compensatoryOff.setEmployeeId(CompensatoryOffdto.getEmployeeId());
		compensatoryOff.setApprovalRemark(CompensatoryOffdto.getApprovalRemark());
		compensatoryOff.setLeaveTypeId(CompensatoryOffdto.getLeaveTypeId());
		if (CompensatoryOffdto.getStatus() != null)
			compensatoryOff.setStatus(CompensatoryOffdto.getStatus());
		else if (CompensatoryOffdto.getStatus() == null && CompensatoryOffdto.getApprovalId() != null)
			compensatoryOff.setStatus(StatusMessage.APPROVED_CODE);
		else
			compensatoryOff.setStatus(StatusMessage.PENDING_CODE);
		compensatoryOff.setUserId(CompensatoryOffdto.getUserId());
//		compensatoryOff.setDateCreated(currentDate);
		compensatoryOff.setUserIdUpdate(CompensatoryOffdto.getUserIdUpdate());
		if (CompensatoryOffdto.getApprovalId() != null)
			compensatoryOff.setDateUpdate(currentDate);
		compensatoryOff.setCompanyId(CompensatoryOffdto.getCompanyId());
		compensatoryOff.setApprovalId(CompensatoryOffdto.getApprovalId());
		compensatoryOff.setLeaveTypeId(StatusMessage.COMPENSATORY_OFF_ID);
		compensatoryOff.setLeavePeriodId(leavePeriodId);
		compensatoryOff.setCancelRemark(CompensatoryOffdto.getCancelRemark());

		if (CompensatoryOffdto.getTmsCompensantoryOffId() != null) {
			compensatoryOff.setTmsCompensantoryOffId(CompensatoryOffdto.getTmsCompensantoryOffId());
			compensatoryOff.setDateCreated(CompensatoryOffdto.getDateCreated());
		} else
			compensatoryOff.setDateCreated(new Date());
		return compensatoryOff;
	}

	public CompensatoryOffDTO databaseModelToUiDto(CompensatoryOff compensatoryOff, Employee employeeEmp,
			Employee approvalEmp) {
		// TODO Auto-generated method stub
		CompensatoryOffDTO compensatoryOffDTO = new CompensatoryOffDTO();
		compensatoryOffDTO.setTmsCompensantoryOffId(compensatoryOff.getTmsCompensantoryOffId());

		compensatoryOffDTO.setTmsCompensantoryOffId(compensatoryOff.getTmsCompensantoryOffId());
		compensatoryOffDTO.setRemark(compensatoryOff.getRemark());
		compensatoryOffDTO.setApprovalId(compensatoryOff.getApprovalId());
		compensatoryOffDTO.setEmployeeId(compensatoryOff.getEmployeeId());
		compensatoryOffDTO.setApprovalRemark(compensatoryOff.getApprovalRemark());
		compensatoryOffDTO.setLeaveTypeId(compensatoryOff.getLeaveTypeId());
		compensatoryOffDTO.setUserId(compensatoryOff.getUserId());

		compensatoryOffDTO.setHalf_fullDay(compensatoryOff.getHalfFullDay());
		compensatoryOffDTO.setDateCreated(compensatoryOff.getDateCreated());
		compensatoryOffDTO.setUserIdUpdate(compensatoryOff.getUserIdUpdate());
		compensatoryOffDTO.setDateUpdate(compensatoryOff.getDateUpdate());
		compensatoryOffDTO.setCompanyId(compensatoryOff.getCompanyId());
		compensatoryOffDTO.setStatus(compensatoryOff.getStatus());
		compensatoryOffDTO.setCancelRemark(compensatoryOff.getCancelRemark());
		if (compensatoryOffDTO.getStatus().equals(StatusMessage.PENDING_CODE)) {
			compensatoryOffDTO.setStatusValue(StatusMessage.PENDING_VALUE);
		} else if (compensatoryOffDTO.getStatus().equals(StatusMessage.REJECTED_CODE)) {
			compensatoryOffDTO.setStatusValue(StatusMessage.REJECTED_VALUE);
		} else if (compensatoryOffDTO.getStatus().equals(StatusMessage.APPROVED_CODE)) {
			compensatoryOffDTO.setStatusValue(StatusMessage.APPROVED_VALUE);
		} else if (compensatoryOffDTO.getStatus().equals(StatusMessage.CANCEL_CODE)) {
			compensatoryOffDTO.setStatusValue(StatusMessage.CANCEL_VALUE);
		}

		compensatoryOffDTO.setFromDate(compensatoryOff.getFromDate());
		compensatoryOffDTO.setToDate(compensatoryOff.getToDate());
		SimpleDateFormat myFormat = new SimpleDateFormat("yyyy MM dd");

		String halfFullDay = compensatoryOff.getHalfFullDay() != null ? compensatoryOff.getHalfFullDay() : "";

		if (halfFullDay.equals("H")) {

			compensatoryOffDTO.setDays(compensatoryOff.getDays());

		} else {

			long difference = compensatoryOff.getToDate().getTime() - compensatoryOff.getFromDate().getTime();
//			 long daysBetween= (difference / (1000*60*60*24)) + 1;
//			 compensatoryOffDTO.setDays(daysBetween);

			long daysBetween = (difference / (1000 * 60 * 60 * 24)) + 1;
			BigDecimal daysbtwn = new BigDecimal(daysBetween);
			compensatoryOffDTO.setDays(daysbtwn);
		}

		if (employeeEmp != null) {
			compensatoryOffDTO.setEmployeeName(employeeEmp.getFirstName() + " " + employeeEmp.getLastName());
			compensatoryOffDTO.setDepartment(employeeEmp.getDepartment().getDepartmentName());
			compensatoryOffDTO.setDesignation(employeeEmp.getDesignation().getDesignationName());
			compensatoryOffDTO.setEmployeeCode(employeeEmp.getEmployeeCode());
		}
		if (approvalEmp != null) {
			compensatoryOffDTO.setApprovalId(approvalEmp.getEmployeeId());
			compensatoryOffDTO.setApprovalEmployeeName(approvalEmp.getFirstName() + " " + approvalEmp.getLastName());
			compensatoryOffDTO.setApprovalEmployeeDepartment(approvalEmp.getDepartment().getDepartmentName());
			compensatoryOffDTO.setApprovalEmployeeDesignation(approvalEmp.getDesignation().getDesignationName());
			compensatoryOffDTO.setApprovalEmployeeCode(approvalEmp.getEmployeeCode());
		}

		return compensatoryOffDTO;
	}

	public List<CompensatoryOffDTO> modeltoDTOList(List<Object[]> compOffList, CompOffSearchDTO compOffSearchDTO) {
		List<CompensatoryOffDTO> compensatoryOffDTOList = new ArrayList<CompensatoryOffDTO>();

		for (Object[] compOffbj : compOffList) {
			CompensatoryOffDTO compensatoryOffDTO = new CompensatoryOffDTO();

			if (compOffbj[0] != null) {
				compensatoryOffDTO.setEmployeeName(compOffbj[0].toString());
			}

			if (compOffbj[1] != null) {
				compensatoryOffDTO.setFromDate((Date) compOffbj[1]);
			}
			if (compOffbj[2] != null) {
				compensatoryOffDTO.setToDate((Date) compOffbj[2]);
			}

			if (compOffbj[3] != null) {
				compensatoryOffDTO.setLeaveTypeId(Long.parseLong(compOffbj[3].toString()));
			}

			if (compOffbj[4] != null) {
				compensatoryOffDTO.setStatus(compOffbj[4].toString());

				if (compensatoryOffDTO.getStatus().equals(StatusMessage.PENDING_CODE)) {
					compensatoryOffDTO.setStatusValue(StatusMessage.PENDING_VALUE);
				} else if (compensatoryOffDTO.getStatus().equals(StatusMessage.REJECTED_CODE)) {
					compensatoryOffDTO.setStatusValue(StatusMessage.REJECTED_VALUE);
				} else if (compensatoryOffDTO.getStatus().equals(StatusMessage.APPROVED_CODE)) {
					compensatoryOffDTO.setStatusValue(StatusMessage.APPROVED_VALUE);
				} else if (compensatoryOffDTO.getStatus().equals(StatusMessage.CANCEL_CODE)) {
					compensatoryOffDTO.setStatusValue(StatusMessage.CANCEL_VALUE);
				}

			}
			if (compOffbj[5] != null) {
				compensatoryOffDTO.setDateCreated((Date) compOffbj[5]);
			}
			if (compOffbj[6] != null) {
				compensatoryOffDTO.setTmsCompensantoryOffId(Long.parseLong(compOffbj[6].toString()));
			}
			if (compOffbj[7] != null) {
				compensatoryOffDTO.setEmployeeId(Long.parseLong(compOffbj[7].toString()));
			}
			if (compOffbj[8] != null) {
				compensatoryOffDTO.setApprovalRemark(compOffbj[8].toString());
			}
			if (compOffbj[9] != null) {
				compensatoryOffDTO.setCancelRemark(compOffbj[9].toString());
			}
			if (compOffbj[10] != null) {
				compensatoryOffDTO.setRemark(compOffbj[10].toString());
			}
			if (compOffbj[11] != null) {
				compensatoryOffDTO.setDesignation(compOffbj[11].toString());
			}
			if (compOffbj[12] != null) {
				compensatoryOffDTO.setEmployeeCode(compOffbj[12].toString());
			}

			String halfFullDay = compOffbj[13] != null ? (String) compOffbj[13] : "";

			BigDecimal days = compOffbj[14] != null ? (new BigDecimal(compOffbj[14].toString()))
					: new BigDecimal("0.0");
//			if (compOffbj[13] != null) {
//				compensatoryOffDTO.setHalf_fullDay(compOffbj[13].toString());
//			}

//			if (halfFullDay.equals("H")) {
//
//				compensatoryOffDTO.setDays(compensatoryOffDTO.getDays());
//
//			} else {
//
//				// SimpleDateFormat myFormat = new SimpleDateFormat("yyyy MM dd");
//				long difference = compensatoryOffDTO.getToDate().getTime() - compensatoryOffDTO.getFromDate().getTime();
////				 long daysBetween= (difference / (1000*60*60*24)) + 1;
////				 compensatoryOffDTO.setDays(daysBetween);
//				long daysBetween = (difference / (1000 * 60 * 60 * 24)) + 1;
//				BigDecimal daysbtwn = new BigDecimal(daysBetween);
//
//				compensatoryOffDTO.setDays(daysbtwn);
//			}
			compensatoryOffDTO.setHalf_fullDay(halfFullDay);
			compensatoryOffDTO.setDays(days);

			compensatoryOffDTOList.add(compensatoryOffDTO);

		}

		return compensatoryOffDTOList;
	}

//	ecd.employeeCode,ecd.firstName,ecd.lastName,dept.departmentName,co.tmsCompensantoryOffId,co.dateCreated,co.fromDate,co.toDate,
	// co.days,co.status,co.employeeId,co.remark,co.userId ,deg.designationName
	// ,ecd.employeeLogoPath
	public List<CompensatoryOffDTO> objcompOffListToObjUiDtoList(List<Object[]> compOffObjList) {
		List<CompensatoryOffDTO> compOffDtoList = new ArrayList<CompensatoryOffDTO>();

		for (Object[] compOffObj : compOffObjList) {
			CompensatoryOffDTO compOffDto = new CompensatoryOffDTO();

			String empCode = compOffObj[0] != null ? (String) compOffObj[0] : null;
			String firstName = compOffObj[1] != null ? (String) compOffObj[1] : null;
			String lastName = compOffObj[2] != null ? (String) compOffObj[2] : null;
			String departmentName = compOffObj[3] != null ? (String) compOffObj[3] : null;
			Long tmsCompensantoryOffId = compOffObj[4] != null ? Long.parseLong(compOffObj[4].toString()) : null;
			Date dateCreated = compOffObj[5] != null ? (Date) (compOffObj[5]) : null;
			Date fromDate = compOffObj[6] != null ? (Date) (compOffObj[6]) : null;
			Date toDate = compOffObj[7] != null ? (Date) (compOffObj[7]) : null;
			// Long days = compOffObj[8] != null ? Long.parseLong(compOffObj[8].toString())
			// : null;
			BigDecimal days = compOffObj[8] != null ? (new BigDecimal(compOffObj[8].toString()))
					: new BigDecimal("0.0");
			String status = compOffObj[9] != null ? (String) compOffObj[9] : null;
			Long employeeId = compOffObj[10] != null ? Long.parseLong(compOffObj[10].toString()) : null;
			String remark = compOffObj[11] != null ? (String) compOffObj[11] : null;
			Long userId = compOffObj[12] != null ? Long.parseLong(compOffObj[12].toString()) : null;
			String designationName = compOffObj[13] != null ? (String) compOffObj[13] : null;
			String employeeLogoPath = compOffObj[14] != null ? (String) compOffObj[14] : null;

			compOffDto.setEmployeeCode(empCode);
			compOffDto.setEmployeeName(firstName + " " + lastName);
			compOffDto.setDepartment(departmentName);
			compOffDto.setTmsCompensantoryOffId(tmsCompensantoryOffId);
			compOffDto.setDateCreated(dateCreated);
			compOffDto.setEmployeeId(employeeId);
			compOffDto.setRemark(remark);
			compOffDto.setUserId(userId);
			compOffDto.setDesignation(designationName);
			compOffDto.setEmployeeLogoPath(employeeLogoPath);

			compOffDto.setFromDate(fromDate);
			compOffDto.setToDate(toDate);
			compOffDto.setDays(days);

			if (status.equals(StatusMessage.PENDING_CODE)) {
				compOffDto.setStatus(StatusMessage.PENDING_CODE);
				compOffDto.setStatusValue(StatusMessage.PENDING_VALUE);
			}
			if (status.equals(StatusMessage.APPROVED_CODE)) {
				compOffDto.setStatus(StatusMessage.APPROVED_CODE);
				compOffDto.setStatusValue(StatusMessage.APPROVED_VALUE);
			}
			if (status.equals(StatusMessage.REJECTED_CODE)) {
				compOffDto.setStatus(StatusMessage.REJECTED_CODE);
				compOffDto.setStatusValue(StatusMessage.REJECTED_VALUE);
			}

			if (status.equals(StatusMessage.CANCEL_CODE)) {
				compOffDto.setStatus(StatusMessage.CANCEL_CODE);
				compOffDto.setStatusValue(StatusMessage.CANCEL_VALUE);
			}

			compOffDtoList.add(compOffDto);
		}
		return compOffDtoList;
	}

}
