package com.csipl.tms.bulkupload.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.csipl.hrms.common.constant.StatusMessage;
import com.csipl.hrms.common.exception.PayRollProcessException;
import com.csipl.hrms.dto.employee.EmployeeDTO;
import com.csipl.hrms.model.common.Company;
import com.csipl.hrms.model.employee.Employee;
import com.csipl.hrms.service.employee.repository.EmployeePersonalInformationRepository;
import com.csipl.tms.leave.repository.EmployeeOpenningLeaveRepository;
import com.csipl.tms.leave.repository.LeavePeriodRepository;
import com.csipl.tms.model.leave.EmployeeOpeningLeaveMaster;
import com.csipl.tms.model.leave.TMSLeavePeriod;

@Transactional
@Service("bulkUploadLeaveService")
public class BulkUploadLeaveServiceImpl implements BulkUploadLeaveService {
	/**
	 * Logger declaration for knowing the flow of execution for debugging
	 */
	private final Logger logger = LoggerFactory.getLogger(BulkUploadLeaveServiceImpl.class);
	@Autowired
	private EmployeePersonalInformationRepository employeePersonalInformationRepository;

	@Autowired
	private LeavePeriodRepository leavePeriodRepository;

	@Autowired
	private EmployeeOpenningLeaveRepository employeeOpenningLeaveRepository;

	@Override
	public void saveEmployeeLeaveOpeningBalance(List<EmployeeOpeningLeaveMaster> employeeLeaveOpeningList,
			EmployeeDTO employeeDto, Map<Integer, StringBuilder> errorMap) throws PayRollProcessException {
		logger.info("employeeLeaveOpeningList is ===== " + employeeLeaveOpeningList);

		List<EmployeeOpeningLeaveMaster> employeeLeaveList = new ArrayList<EmployeeOpeningLeaveMaster>();
		Employee employee = null;
		EmployeeOpeningLeaveMaster empLeave = null;
		List<TMSLeavePeriod> tms = leavePeriodRepository.findleavePeriodStatus(employeeDto.getCompanyId());
		Long leavePeriod = tms.get(0).getLeavePeriodId();
		TMSLeavePeriod tmsLeavePeriod = new TMSLeavePeriod();
		tmsLeavePeriod.setLeavePeriodId(leavePeriod);
		for (EmployeeOpeningLeaveMaster employeeLeaveOpen : employeeLeaveOpeningList) {
			employee = employeePersonalInformationRepository
					.findEmployees(employeeLeaveOpen.getEmployee().getEmployeeCode(), employeeDto.getCompanyId());
			if (employee == null) {
				throw new PayRollProcessException(employeeLeaveOpen.getEmployee().getEmployeeCode()
						+ " No employee found for this Code, Please onboard the employee first");
			} else {
				empLeave = employeeOpenningLeaveRepository.findEmployeeOpeningBalance(employee.getEmployeeId(),
						employeeLeaveOpen.getTmsleaveTypeMaster().getLeaveId(), tmsLeavePeriod.getLeavePeriodId());
				if (empLeave != null  && empLeave.getStatus().equals("OP")) {
					employeeLeaveOpen.setEmpOpeningId(empLeave.getEmpOpeningId());
					employeeLeaveOpen.setEmployee(empLeave.getEmployee());
					employeeLeaveOpen.setUserIdUpdate(employeeDto.getUserId());
					employeeLeaveOpen.setDateUpdate(new Date());
					employeeLeaveOpen.setActiveStatus(StatusMessage.ACTIVE_CODE);
					employeeLeaveOpen.setTmsleavePeriod(empLeave.getTmsleavePeriod());
					employeeLeaveOpen.setTmsleaveTypeMaster(empLeave.getTmsleaveTypeMaster());
					employeeLeaveOpen.setCompany(empLeave.getCompany());
					employeeLeaveOpen.setStatus(empLeave.getStatus());
					employeeLeaveList.add(employeeLeaveOpen);

				} else {
					employeeLeaveOpen.setEmployee(employee);
					employeeLeaveOpen.setDateCreated(new Date());
					employeeLeaveOpen.setUserId(employeeDto.getUserId());
					employeeLeaveOpen.setActiveStatus(StatusMessage.ACTIVE_CODE);
					employeeLeaveOpen.setStatus(StatusMessage.OPEN_CODE);
					employeeLeaveOpen.setTmsleavePeriod(tmsLeavePeriod);
					Company com = new Company();
					com.setCompanyId(employeeDto.getCompanyId());
					employeeLeaveOpen.setCompany(com);
					employeeLeaveList.add(employeeLeaveOpen);

				}

			}
		}

		employeeOpenningLeaveRepository.save(employeeLeaveOpeningList);
	}

	@Override
	public void saveEmployeeLeaveCarryForward(List<EmployeeOpeningLeaveMaster> employeeLeaveOpeningList,
			EmployeeDTO employeeDto, Map<Integer, StringBuilder> errorMap) throws PayRollProcessException {
		logger.info("employeeLeaveOpeningList is ===== " + employeeLeaveOpeningList);

		List<EmployeeOpeningLeaveMaster> employeeLeaveList = new ArrayList<EmployeeOpeningLeaveMaster>();
		Employee employee = null;
		EmployeeOpeningLeaveMaster empLeave = null;
		List<TMSLeavePeriod> tms = leavePeriodRepository.findleavePeriodStatus(employeeDto.getCompanyId());
		Long leavePeriod = tms.get(0).getLeavePeriodId();
		TMSLeavePeriod tmsLeavePeriod = new TMSLeavePeriod();
		tmsLeavePeriod.setLeavePeriodId(leavePeriod);
		for (EmployeeOpeningLeaveMaster employeeLeaveOpen : employeeLeaveOpeningList) {
			employee = employeePersonalInformationRepository
					.findEmployees(employeeLeaveOpen.getEmployee().getEmployeeCode(), employeeDto.getCompanyId());
			if (employee == null) {
				throw new PayRollProcessException(employeeLeaveOpen.getEmployee().getEmployeeCode()
						+ " No employee found for this Code, Please onboard the employee first");
			} else {
				empLeave = employeeOpenningLeaveRepository.findEmployeeOpeningBalance(employee.getEmployeeId(),
						employeeLeaveOpen.getTmsleaveTypeMaster().getLeaveId(), tmsLeavePeriod.getLeavePeriodId());
				if (empLeave != null && empLeave.getStatus().equals("CF")) {
					employeeLeaveOpen.setEmpOpeningId(empLeave.getEmpOpeningId());
					employeeLeaveOpen.setEmployee(empLeave.getEmployee());
					employeeLeaveOpen.setUserIdUpdate(employeeDto.getUserId());
					employeeLeaveOpen.setDateUpdate(new Date());
					employeeLeaveOpen.setActiveStatus(StatusMessage.ACTIVE_CODE);
					employeeLeaveOpen.setTmsleavePeriod(empLeave.getTmsleavePeriod());
					employeeLeaveOpen.setTmsleaveTypeMaster(empLeave.getTmsleaveTypeMaster());
					employeeLeaveOpen.setCompany(empLeave.getCompany());
					employeeLeaveOpen.setStatus(empLeave.getStatus());
					employeeLeaveList.add(employeeLeaveOpen);

				} else {
					employeeLeaveOpen.setEmployee(employee);
					employeeLeaveOpen.setDateCreated(new Date());
					employeeLeaveOpen.setUserId(employeeDto.getUserId());
					employeeLeaveOpen.setActiveStatus(StatusMessage.ACTIVE_CODE);
					employeeLeaveOpen.setStatus(StatusMessage.CARRYFORWORD_LEAVE_CODE);
					employeeLeaveOpen.setTmsleavePeriod(tmsLeavePeriod);
					Company com = new Company();
					com.setCompanyId(employeeDto.getCompanyId());
					employeeLeaveOpen.setCompany(com);
					employeeLeaveList.add(employeeLeaveOpen);

				}

			}
		}

		employeeOpenningLeaveRepository.save(employeeLeaveOpeningList);
	}
}
