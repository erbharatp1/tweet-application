package com.csipl.tms.leave.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.csipl.hrms.common.constant.StatusMessage;
import com.csipl.hrms.model.employee.Employee;
import com.csipl.hrms.service.employee.repository.EmployeePersonalInformationRepository;
import com.csipl.tms.attendancelog.service.AttendanceLogServiceImpl;
import com.csipl.tms.leave.adaptor.LeaveEntryAdaptor;
import com.csipl.tms.leave.adaptor.LeavePeriodAdaptor;
import com.csipl.tms.leave.repository.EmployeeLeaveHistoryRepository;
import com.csipl.tms.leave.repository.EmployeeOpenningLeaveRepository;
import com.csipl.tms.leave.repository.LeavePeriodRepository;
import com.csipl.tms.leave.repository.LeaveRulesHdRepository;
import com.csipl.tms.leave.repository.TMSLeaveRuleMasterRepository;
import com.csipl.tms.model.leave.EmployeeLeaveHistory;
import com.csipl.tms.model.leave.EmployeeOpeningLeaveMaster;
import com.csipl.tms.model.leave.TMSLeavePeriod;
import com.csipl.tms.model.leave.TMSLeaveRuleMaster;
import com.csipl.tms.model.leave.TMSLeaveRules;
import com.csipl.tms.model.leave.TMSLeaveRulesHd;
import com.csipl.tms.model.leave.TMSLeaveType;

@Service("leavePeriodService")
public class LeavePeriodServiceImpl implements LeavePeriodService {

	private static final Logger logger = LoggerFactory.getLogger(LeavePeriodServiceImpl.class);
	@Autowired
	private LeavePeriodRepository leavePeriodRepository;

	@Autowired
	private LeaveRulesHdRepository leaveRulesHdRepository;

	@Autowired
	private TMSLeaveRuleMasterRepository tMSLeaveRuleMasterRepository;
	
	@Autowired
	LeaveTypeService leaveTypeService;
	
	@Autowired
	private EmployeePersonalInformationRepository employeePersonalInformationRepository;
	
	@Autowired
	private EmployeeOpenningLeaveRepository  employeeOpenningLeaveRepository;
	
	@Autowired
	EmployeeLeaveHistoryRepository employeeLeaveHistoryRepository;
	 
	LeavePeriodAdaptor leavePeriodAdaptor= new LeavePeriodAdaptor();
	
	LeaveEntryAdaptor leaveEntryAdaptor = new LeaveEntryAdaptor();
	
	@Transactional
	@Override
	public TMSLeavePeriod save(TMSLeavePeriod leavePeriod) {
		TMSLeavePeriod tMSLeavePeriod = null;

		if (leavePeriod.getActiveStatus().equals(StatusMessage.DEACTIVE_CODE)) {
			TMSLeaveRulesHd tMSLeaveRulesHd = leaveRulesHdRepository
					.findLeaveRulesHdByPeriodId(leavePeriod.getLeavePeriodId());
			tMSLeaveRulesHd.setActiveStatus(leavePeriod.getActiveStatus());
			tMSLeaveRulesHd.setDateUpdate(new Date());
			leaveRulesHdRepository.save(tMSLeaveRulesHd);
			leavePeriod.setDateUpdate(new Date());
			return leavePeriodRepository.save(leavePeriod);

		}
		if (leavePeriod.getLeavePeriodId() == null) {

			tMSLeavePeriod = leavePeriodRepository.save(leavePeriod);
			List<TMSLeaveRuleMaster> tMSLeaveRuleMasterList = tMSLeaveRuleMasterRepository.findAllLeaveRule(tMSLeavePeriod.getCompanyId());
			
			TMSLeaveRulesHd tMSLeaveRulesHd = new TMSLeaveRulesHd();
			tMSLeaveRulesHd.setCompanyId(tMSLeavePeriod.getCompanyId());
			tMSLeaveRulesHd.setLeavePeriodId(tMSLeavePeriod.getLeavePeriodId());
			tMSLeaveRulesHd.setUserId(tMSLeavePeriod.getUserId());
			tMSLeaveRulesHd.setDateCreated(new Date());
			tMSLeaveRulesHd.setDateUpdate(new Date());
			tMSLeaveRulesHd.setUserIdUpdate(tMSLeavePeriod.getUserId());
			tMSLeaveRulesHd.setActiveStatus(tMSLeavePeriod.getActiveStatus());

			List<TMSLeaveRules> tMSLeaveRulesList = new ArrayList<TMSLeaveRules>();
			
			
			for(TMSLeaveRuleMaster rule:tMSLeaveRuleMasterList) {
			
				tMSLeaveRulesList.add(leavePeriodAdaptor.tMSLeaveRuleMasterToTMSLeaveRule(rule, tMSLeaveRulesHd));
			}
			tMSLeaveRulesHd.setTmsleaveRules(tMSLeaveRulesList);
			// employee carriforword leave save logic
			Object[] leavePeriodObjList =leavePeriodRepository.findLastupdatedPeriod();
			System.out.println("leavePeriodObjList..."+leavePeriodObjList);
			if(leavePeriodObjList!=null && leavePeriodObjList.length!= 0 ) {
			Integer Id = leavePeriodObjList[0]!=null?(Integer)leavePeriodObjList[0]:null;
		    String  leavePeriodId = String.valueOf(Id);
		    TMSLeavePeriod tmsLeavePeriod   = leavePeriodRepository.save(leavePeriod);
			 List<EmployeeOpeningLeaveMaster> employeeOpeningList = new ArrayList<EmployeeOpeningLeaveMaster>(); 
			 List<EmployeeLeaveHistory> employeeHistoryList = new ArrayList<EmployeeLeaveHistory>(); 

			 List<Employee> employeeList = employeePersonalInformationRepository.getAllActiveEmployee(tMSLeaveRulesHd.getCompanyId());
		 for (Employee employee : employeeList) {
			 List<Object[]> leaveTypeList = leaveTypeService.findOpeningLeaveType(employee.getEmployeeId(),tMSLeaveRulesHd.getCompanyId(),leavePeriodId); 
			 employeeOpeningList.addAll( leaveEntryAdaptor.databaseObjectListToEmployeeOpenningLeave(leaveTypeList,employeeOpeningList,tmsLeavePeriod));
			 employeeHistoryList.addAll(leaveEntryAdaptor.databaseObjectListToEmployeeLeaveHistory(leaveTypeList,employeeOpeningList,tmsLeavePeriod));
		}
		 employeeOpenningLeaveRepository.save(employeeOpeningList);
		 employeeLeaveHistoryRepository.save(employeeHistoryList);
			}
			leaveRulesHdRepository.save(tMSLeaveRulesHd);
			return tMSLeavePeriod;
		}

		return tMSLeavePeriod;
	}

	@Override
	public List<TMSLeavePeriod> findAllLeavePeriod(Long companyId) {
		return leavePeriodRepository.findAllLeavePeriod(companyId);
	}

	@Override
	public List<TMSLeavePeriod> findleavePeriodStatus(Long companyId) {
		return leavePeriodRepository.findleavePeriodStatus(companyId);
	}
 
	
	 public void saveLeaveOpenning(TMSLeaveRulesHd tMSLeaveRulesHd) {
		
		
	 }

	@Override
	public TMSLeavePeriod leavePeriod(Long companyId) {
		// TODO Auto-generated method stub
		return leavePeriodRepository.leavePeriod(companyId);
	}

	@Override
	public TMSLeavePeriod findLeavePeriodByProcessMonth(Long companyId, String processMonth) {
		logger.info("companyId "+companyId+" processMonth "+processMonth);
		return leavePeriodRepository.findLeavePeriodByProcessMonth(companyId ,processMonth );
	}
	
	public List<TMSLeavePeriod> findLeavePeriodByProcessMonthWithACflag(Long companyId ,String  processMonth) {
		logger.info("companyId "+companyId+" processMonth "+processMonth);
		return leavePeriodRepository.findLeavePeriodByProcessMonthWithACflag(companyId ,processMonth );
	}
	
}
