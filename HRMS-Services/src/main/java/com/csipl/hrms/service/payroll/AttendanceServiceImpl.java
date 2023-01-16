package com.csipl.hrms.service.payroll;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.csipl.hrms.dto.employee.EmployeeDTO;
import com.csipl.hrms.common.enums.RelationEnum;
import com.csipl.hrms.common.exception.PayRollProcessException;
import com.csipl.hrms.common.util.DateUtils;
import com.csipl.hrms.model.employee.Employee;
import com.csipl.hrms.model.payrollprocess.Attendance;
import com.csipl.hrms.model.payrollprocess.ReportPayOut;
import com.csipl.hrms.model.payrollprocess.ReportPayOutPK;
import com.csipl.hrms.service.employee.repository.EmployeePersonalInformationRepository;
import com.csipl.hrms.service.payroll.repository.AttendanceRepository;
import com.csipl.hrms.service.util.ConverterUtil;

@Transactional
@Service("attendanceService")
public class AttendanceServiceImpl implements AttendanceService {
	private static final Logger logger = LoggerFactory.getLogger(AttendanceServiceImpl.class);
	@Autowired
	private AttendanceRepository attendanceRepository;
	@PersistenceContext(unitName = "mySQL")
	@Autowired
	private EntityManager eManager;
	
	@Autowired
	EmployeePersonalInformationRepository employeePersonalInformationRepository;
	
	@Override
	public List<ReportPayOut> fetchEmployeeForSalary(long companyId, StringBuilder employeeIds, String payMonth ) {	
		
		logger.info("fetchEmployeeForSalary is calling : "+" : companyId "+ companyId +"employeeIds " +employeeIds +"payMonth"+payMonth);
		String attendanceQuery ="   select "
				+ "attendance.presense , " // index = 0
				+ "attendance.weekoff ,"	// index = 1
				+ "attendance.seekleave , "	// index = 2
				+ " attendance.paidleave ,"	// index = 3
				+ " attendance.payDays , "	// index = 4
				+ " attendance.publicholidays,"	// index = 5
				+ " attendance.absense,"		// index = 6
				+ " emp.employeeId,"			// index = 7
				+ " emp.departmentId, "	// index = 8
				+ " emp.cityId, "					// index = 9
				+ " emp.firstName , "		// index = 10
				+ " emp.lastName , "		// index = 11
				+ " emp.employeeCode , "	// index = 12
				+" COALESCE(empBank.accountNumber, ''),   "	// index = 13
				+" emp.dateOfJoining,   "			// index = 14
				//+" COALESCE(dropDownList.listValue, ''),  "	// index = 15
				+" COALESCE(empBank.bankId, ''),   "	// index = 15
				+" emp.companyId,  "				// index = 16
				+" COALESCE(empFamily.relation, ''),"	// index = 17
				+" empFamily.name, "				// index = 18
				+" emp.maritalStatus,"				// index = 19
				+" address.emailId,"				// index = 20
				+" address.mobile,  "				// index = 21
				+" empBank.ifscCode,  "				// index = 22
				+" emp.gender,  "				// index = 23
				+" emp.dateOfBirth,  "				// index = 24
				+" emp.stateId,  "				// index = 25
				+" emp.adharNumber,  "				// index = 26
				+" phd.isNoPFDeduction, "  		// index = 27
				+" attendance.leaves   "        // index =28
				+" from Attendance attendance JOIN Employee emp ON attendance.employeeCode = emp.employeeCode and attendance.processMonth=?2 and emp.employeeId in("+employeeIds+") and attendance.companyId = ?1 "
				+" LEFT JOIN EmployeeBank empBank ON emp.employeeId =  empBank.employeeId "
				+" LEFT JOIN EmployeeFamily empFamily ON emp.employeeId = empFamily.employeeId " 
				+" LEFT JOIN Address address ON emp.permanentAddressId = address.addressId "
				+" LEFT JOIN PayStructureHd phd  ON emp.employeeId = phd.employeeId "
				+" and  ( phd.dateEnd is null  and phd.effectiveDate is NOT null and phd.effectiveDate> NOW() ) "
				+" and ( ( accountType='SA' or accountType='SL') and empBank.activeStatus='AC'  )   group by emp.employeeId order by emp.departmentId, emp.employeeId";		
		
		Query nativeQuery = eManager.createNativeQuery(attendanceQuery);
		nativeQuery.setParameter(1, companyId);
		//nativeQuery.setParameter(2, employeeIds);
		nativeQuery.setParameter(2, payMonth);
		final List<Object[]> attendanceList = nativeQuery.getResultList();
		System.out.println("==========attendanceList=========="+attendanceList.size());
	//	List<Object[]>  attendanceList  = attendanceRepository.fetchEmployeeForSalary( companyId, employeeIds, payMonth  );
		//List< Attendance > attendanceDTOList = new ArrayList <Attendance>();	
		List<ReportPayOut> reportPayOuts = new ArrayList<ReportPayOut>();
		String relation;
		
		Long tempDepartmentId = null;
		for ( Object[] attendanceObj : attendanceList ) { 
			//Attendance attendance = new Attendance();
			tempDepartmentId = ConverterUtil.getLong( attendanceObj[8]    ) ;
			
		//	if ( tempDepartmentId != null && (tempDepartmentId == departmentId) ) {
			
			ReportPayOut reportPayOut = new ReportPayOut();
			
			reportPayOut.setDepartmentId( ConverterUtil.getLong( attendanceObj[8]) );	
			
			ReportPayOutPK pk = new ReportPayOutPK();
			pk.setProcessMonth( payMonth );
			pk.setEmployeeId( ConverterUtil.getLong( attendanceObj[7])   );
			reportPayOut.setId( pk );
			
			reportPayOut.setPresense(  ConverterUtil.getBigDecimal( attendanceObj[0] )  );
			reportPayOut.setWeekoff(  ConverterUtil.getBigDecimal( attendanceObj[1] ) );
			reportPayOut.setSeekleave( ConverterUtil.getBigDecimal( attendanceObj[2] )  );
			reportPayOut.setPaidleave( ConverterUtil.getBigDecimal( attendanceObj[3] ) );
			reportPayOut.setPayDays( ConverterUtil.getBigDecimal( attendanceObj[4] ) );
			reportPayOut.setPublicholidays( ConverterUtil.getBigDecimal( attendanceObj[5] ));
			reportPayOut.setAbsense( ConverterUtil.getBigDecimal( attendanceObj[6] )  );
		
			
			//reportPayOut.setEmployeeId(   ConverterUtil.getLong( attendanceObj[7]    ) );	
			
			reportPayOut.setCityId( ConverterUtil.getLong( attendanceObj[9]    ) );
			
			reportPayOut.setName( attendanceObj[ 10 ].toString() +"  " +attendanceObj [11].toString()  );
			reportPayOut.setEmployeeCode( attendanceObj[ 12 ].toString() );
			reportPayOut.setAccountNumber( attendanceObj[ 13 ].toString() );
			
			if ( attendanceObj[ 14 ] != null) {
				Date doj = (Date)( attendanceObj[ 14 ] );
				reportPayOut.setDateOfJoining( doj  );
				reportPayOut.setEpfJoining( doj );
			}
			
			
			reportPayOut.setBankShortName( attendanceObj[ 15 ].toString() );   
			
			reportPayOut.setCompanyId( ConverterUtil.getLong( attendanceObj[16] ) );
			
			relation = attendanceObj[ 17 ].toString();
			if ( relation.equals( RelationEnum.Father.getRelation())) {
				if ( attendanceObj[ 18 ] != null) 
					reportPayOut.setFatherName(  attendanceObj[ 18 ].toString() );
			}
			
			if ( relation.equals( RelationEnum.Husband.getRelation())) {
				if ( attendanceObj[ 18 ] != null) 
					reportPayOut.setHusbandName(  attendanceObj[ 18 ].toString() );
			}
			
			if ( attendanceObj[ 19 ] != null) 
				reportPayOut.setMaritalStatus(  attendanceObj[ 19 ].toString() );
			
			if ( attendanceObj[ 20 ] != null) 
					reportPayOut.setEmail(  attendanceObj[ 20 ].toString() );
			
			if ( attendanceObj[ 21 ] != null) 
				reportPayOut.setMobileNo(  attendanceObj[ 21 ].toString() );
			
			if ( attendanceObj[ 22 ] != null) 
				reportPayOut.setIFSCCode(  attendanceObj[ 22 ].toString() );
			
			if ( attendanceObj[ 23 ] != null) 
				reportPayOut.setGender(  attendanceObj[ 23 ].toString() );
			
			
			
			if ( attendanceObj[ 24 ] != null) 
				reportPayOut.setDob( (Date) attendanceObj[ 24 ] );
			
			reportPayOut.setStateId( ConverterUtil.getLong( attendanceObj[ 25 ]  ) );
			
			if ( attendanceObj[ 26 ] != null) 
				reportPayOut.setAadharNo(  attendanceObj[ 26 ].toString() );
			
			if ( attendanceObj[ 28 ] != null) 
				reportPayOut.setLeaves(ConverterUtil.getBigDecimal( attendanceObj[28] ) );
			
			/*if ( attendanceObj[ 27 ] != null) 
				reportPayOut.setIsNoPFDeduction(  attendanceObj[ 26 ].toString() );*/
			
			reportPayOuts.add( reportPayOut );
			//}
			
		}	
	
		return reportPayOuts;
	}
  
	@Override
	public List<EmployeeDTO> fetchEmployeeForValidation(long departmentId ,long companyId, String payMonth ) {
		
		Date processMonth = DateUtils.getPayMonth( payMonth );
		List<Object[]>  employeeList  = attendanceRepository.fetchEmployeeForValidation( departmentId , companyId, processMonth );
		List<EmployeeDTO> employee = new ArrayList<EmployeeDTO>();

		
		for ( Object[] empObj : employeeList ) { 
			EmployeeDTO empValidation = new EmployeeDTO();
			empValidation.setEmployeeId(ConverterUtil.getLong( empObj[0]) );
			empValidation.setEmployeeCode(empObj[1].toString());
			empValidation.setDepartmentId(ConverterUtil.getLong( empObj[2] ));
			empValidation.setBankId(ConverterUtil.getString(empObj[3]));
			empValidation.setPayStructureHdId(ConverterUtil.getLong( empObj[4]));
			//empValidation.setUanNumber(ConverterUtil.getString( empObj[5]));
			empValidation.setAadharNumber(ConverterUtil.getString( empObj[5]));
			employee.add( empValidation );
		}	
		return employee;
	}

	@Override
	public List<Attendance> fetchEmployeeForSalary(long companyId, long departmentId) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
//	@Override
//	public List<String> validateAttendanceBeforeUpload( long companyId, long departmentId, String processMonth ) {
//		//boolean flag = false;
//		
//		List<String> deptList =new ArrayList<String>();
//		if ( departmentId > 0 ) {
//			 deptList=  attendanceRepository.isAttendanceUploadedForMonthAndDepartment(departmentId, processMonth);
//			
//		} else {
//			 deptList =  attendanceRepository.isAttendanceUploadedForMonthAndCompany(companyId, processMonth);
//			
//		}
//		
//		return deptList;
//	}

	@Override
	public void  upload( List<Attendance> attendances) throws PayRollProcessException {
		
		
		attendanceRepository.save(attendances);
	}

	@Override
	public List<EmployeeDTO> fetchEmployeeForValidation( long companyId, String payMonth ) {
		
		Date processMonth = DateUtils.getPayMonth( payMonth );
		List<Object[]>  employeeList  = attendanceRepository.fetchEmployeeForValidation( companyId, processMonth );
		List<EmployeeDTO> employee = new ArrayList<EmployeeDTO>();

		
		for ( Object[] empObj : employeeList ) { 
			EmployeeDTO empValidation = new EmployeeDTO();
			empValidation.setEmployeeId(ConverterUtil.getLong( empObj[0]) );
			empValidation.setEmployeeCode(empObj[1].toString());
			empValidation.setDepartmentId(ConverterUtil.getLong( empObj[2] ));
			empValidation.setBankId(ConverterUtil.getString(empObj[3]));
			empValidation.setPayStructureHdId(ConverterUtil.getLong( empObj[4]));
			//empValidation.setUanNumber(ConverterUtil.getString( empObj[5]));
			empValidation.setAadharNumber(ConverterUtil.getString( empObj[5]));
			employee.add( empValidation );
		}	
		return employee;
	}

	@Override
	public void checkFormerEmployees(List<String> empCodeListCsv , Long companyId) throws PayRollProcessException {
		
		//logger.info("empCodeListCsv "+ empCodeListCsv);
		
		List<Employee> activeEmployees = employeePersonalInformationRepository.findAllActiveEmployees(companyId);
		
		List<String> filteredEmpCodeDElist = empCodeListCsv.stream()
		.filter(empCode-> activeEmployees.stream()
		.filter(activeEmp -> empCode.equalsIgnoreCase(activeEmp.getEmployeeCode())
						).count()<1)
    .collect(Collectors.toList());
		
		if(filteredEmpCodeDElist != null && filteredEmpCodeDElist.size()>0) {
			throw new PayRollProcessException(
					"Can Not Upload Attendance Of Deactive Employees  : " + filteredEmpCodeDElist);
		}
		
		//logger.info("filteredEmpCodeDElist "+ filteredEmpCodeDElist);
	}
	
//	@Override
//	public List<String> findDepartmentForProcessing(  long companyId,  String processMonth  ) {
//		return attendanceRepository.findDepartmentForProcessing( companyId, processMonth );
//	}

}
