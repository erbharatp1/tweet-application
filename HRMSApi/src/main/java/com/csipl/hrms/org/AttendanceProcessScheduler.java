package com.csipl.hrms.org;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.mail.internet.MimeMessage;

import org.apache.velocity.app.VelocityEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.ui.velocity.VelocityEngineUtils;

import com.csipl.common.model.EmailConfiguration;
import com.csipl.common.model.EmailNotificationMaster;
import com.csipl.hrms.common.constant.StatusMessage;
import com.csipl.hrms.dto.employee.EmployeeDTO;
import com.csipl.hrms.model.employee.Employee;
import com.csipl.hrms.service.adaptor.EmployeePersonalInformationAdaptor;
import com.csipl.hrms.service.common.EmailNotificationService;
import com.csipl.hrms.service.common.repository.EmailConfigurationRepository;
import com.csipl.hrms.service.employee.EmployeePersonalInformationService;
import com.csipl.hrms.service.organization.CompanyService;
import com.csipl.tms.attendanceCalculation.adaptor.AttendanceCalculationAdaptor;
import com.csipl.tms.attendancelog.service.AttendanceCalculationService;
import com.csipl.tms.attendancelog.service.AttendanceLogService;
import com.csipl.tms.attendanceregularizationrequest.service.PunchTimeDetailsService;
import com.csipl.tms.deviceinfo.adaptor.DeviceInfoAdaptor;
import com.csipl.tms.deviceinfo.repository.DeviceLogsRepository;
import com.csipl.tms.deviceinfo.service.DeviceInfoService;
import com.csipl.tms.deviceinfo.service.DeviceLogsService;
import com.csipl.tms.dto.attendancelog.AttendanceLogDTO;
import com.csipl.tms.dto.deviceinfo.DeviceLogsInfoDTO;
import com.csipl.tms.model.attendancelog.AttendanceLog;
import com.csipl.tms.model.deviceinfo.DeviceInfo;
import com.csipl.tms.model.deviceinfo.DeviceLogsInfo;
import com.csipl.tms.model.halfdayrule.HalfDayRule;
import com.csipl.tms.rules.service.RulesService;



@Component
public class AttendanceProcessScheduler {
private static final Logger logger = LoggerFactory.getLogger(AttendanceProcessScheduler.class);
private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");	
public static final String ENCODING = "UTF-8";
  
@Autowired
private VelocityEngine velocityEngine;

@Autowired
CompanyService companyService;

@Autowired
DeviceLogsRepository deviceLogsRepository;
@Autowired
EmailConfigurationRepository emailConfugrationRepository;

@Autowired
EmailNotificationService emailNotificationService;

 
@Autowired
AttendanceCalculationService attendanceCalculationService;

@Autowired
AttendanceLogService attendanceLogService;

@Autowired
DeviceInfoService deviceInfoService;

@Autowired
PunchTimeDetailsService punchTimeDetailsService;

@Autowired
DeviceLogsService deviceLogsService;

@Autowired
RulesService rulesService;

@Autowired
EmployeePersonalInformationService employeePersonalInformationService;

 

@Autowired
Environment env;

AttendanceCalculationAdaptor attendanceCalculationAdaptor = new AttendanceCalculationAdaptor();

EmployeePersonalInformationAdaptor employeePersonalInformationAdaptor = new EmployeePersonalInformationAdaptor();

DeviceInfoAdaptor deviceInfoAdaptor = new DeviceInfoAdaptor();

	public void scheduleTaskWithFixedDelay() {
	}

	public void scheduleTaskWithInitialDelay() {
	}
	Object tenant ="computronics.in"  ;
	
	@Scheduled(cron = "0 53 22 * * ?")
	public void scheduleTaskWithCronExpression() {
		Map<Object, Object> mapObj =  MasterService.getDataSourceHashMap(env);
		mapObj.forEach((k,v)->{
			this.tenant = k;
			EmailConfiguration confugration = null;
			EmailNotificationMaster listEmail = null;
		try {
			    
			TenantContext.setTenantId(this.tenant!=null ? this.tenant.toString() : "computronics.in" );
			confugration = emailConfugrationRepository.findEMail();
			listEmail= emailNotificationService.findEMailListByStatus(StatusMessage.ATTENDANCE_CLONE_SYNC);
			
			logger.info("AttendanceProcessScheduler final day attendance calculation ...");
			List<Object[]> devideInfoObjList = deviceInfoService.findDevice();
			List<String> sreialNoList = new ArrayList<String>();
			List<DeviceInfo> deviceInfoList = deviceInfoAdaptor.getDeviceInfoList(devideInfoObjList);
			Long companyId =1L;
			String prefix = "COM";
			for (DeviceInfo deviceInfo : deviceInfoList) {
				sreialNoList.add(deviceInfo.getSerialNumber());	
				companyId = deviceInfo.getCompanyId();
				prefix = deviceInfo.getPrefix();
			}
			
			//START fatching failed crone dates and resubmit unsync attendance
			List<AttendanceLogDTO> syncFailedDateList = unsuccessSyncAttendance(companyId);
			if(syncFailedDateList != null) {
				for(AttendanceLogDTO failedAttendanceLogDTO : syncFailedDateList) {
					if(failedAttendanceLogDTO.getAttendanceDates() != null) {
					Date date = Date.from(failedAttendanceLogDTO.getAttendanceDates().atStartOfDay(ZoneId.systemDefault()).toInstant());
				    SyncAttendanceViaDate(date);
					}
				}
			}
			
			//END fatching failed crone dates and resubmit unsync attendance
			
			List<Employee> employeeList = employeePersonalInformationService.fetchEmployee(companyId);
			List<EmployeeDTO> employeeDtoList = employeePersonalInformationAdaptor.databaseModelToUiDtoListNativeSearch(employeeList);	
			Map<String, EmployeeDTO> mapEmpIdAndEmpCode = new HashMap<>();
			Map<String, Long> mapEmpCode = new HashMap<>();
			for (EmployeeDTO employeeDtoObj : employeeDtoList) {
				Long employeeId = employeeDtoObj.getEmployeeId();
				String boimatricId = employeeDtoObj.getBiometricId();
				String employeeCode = employeeDtoObj.getEmployeeCode();
				mapEmpCode.put(employeeCode, employeeId);
			}
			List<Object[]> objectList = new ArrayList<Object[]>();
			List<AttendanceLogDTO> attendanceLogDtoList = new ArrayList<AttendanceLogDTO>();
			List<Object[]> lateCommersEmployeeListWithCount = attendanceLogService.getLateCommersEmployeeListWithCount(companyId);
			
			List<DeviceLogsInfoDTO>  deviceLogsInfoDTO=	deviceLogsService.getAllLateComersListByDate(companyId, new Date());
			if(sreialNoList.size()>0) {
			 objectList =attendanceCalculationService.getAttendanceFromEpush( sreialNoList);
			 attendanceLogDtoList = attendanceCalculationAdaptor.objListToDto1(objectList,employeeDtoList,mapEmpIdAndEmpCode,prefix,companyId);
			}
			List<Object[]> empAttendanceList = punchTimeDetailsService.getEmpAttendaceList(companyId);
			List<AttendanceLogDTO> systemAttendanceLogList= attendanceCalculationAdaptor.objectListToUImodelforCron(empAttendanceList,mapEmpCode);
			List<AttendanceLogDTO> attendanceLogList= attendanceCalculationAdaptor.attendanceCalculation1(attendanceLogDtoList, systemAttendanceLogList,mapEmpIdAndEmpCode,companyId);
			
			HalfDayRule halfDayRule = rulesService.getHalfDayRule(companyId);  
			List<AttendanceLog> attendanceLogList1 = attendanceCalculationAdaptor.uiDtoToDatabaseModelList2(attendanceLogList,mapEmpIdAndEmpCode,halfDayRule, lateCommersEmployeeListWithCount, deviceLogsInfoDTO);

			attendanceLogService.savePunchTimeLog(attendanceLogList1);
			logger.info("AttendanceProcessScheduler data saved  successfully...");
			TenantContext.setTenantId("");
		
		}catch(Exception exception)
		{
			logger.info("AttendanceProcessScheduler  data not saved for ..."+this.tenant);
			// if clone fail then triggerMail will call
			triggerEmail(new Date(),confugration,listEmail);
		}
		});
		
	}
	//@PostConstruct
	@Scheduled(cron = "0 0 12 * * ?")
	public void scheduleTaskWithCronExpressionForFirstAttendance() throws ParseException {
		
		Map<Object, Object> mapObj =  MasterService.getDataSourceHashMap(env);

		mapObj.forEach((k,v)->{
			this.tenant = k;
			try {
			System.out.println("Item : " + k + " Count : " + v);
//		    request.setAttribute("tenantId", k);
			//  CurrentTenantIdentifierResolverImpl.setDefaultTenantForScheduledTasks(k.toString());	    
			TenantContext.setTenantId(this.tenant!=null ? this.tenant.toString() : "computronics.in" );
		logger.info("late comers scheduler Id----->" );
		List<Object[]> devideInfoObjList = deviceInfoService.findDevice();
		List<String> sreialNoList = new ArrayList<String>();
		List<DeviceInfo> deviceInfoList = deviceInfoAdaptor.getDeviceInfoList(devideInfoObjList);
		Long companyId =1L;
		String prefix = "COM";
		for (DeviceInfo deviceInfo : deviceInfoList) {
			sreialNoList.add(deviceInfo.getSerialNumber());	
			companyId = deviceInfo.getCompanyId();
			prefix = deviceInfo.getPrefix();
		}
		
		List<Employee> employeeList = employeePersonalInformationService.fetchEmployee(companyId);
		List<EmployeeDTO> employeeDtoList = employeePersonalInformationAdaptor.databaseModelToUiDtoListNativeSearch(employeeList);	
		Map<String, EmployeeDTO> mapEmpIdAndEmpCode = new HashMap<>();
		Map<String, Long> mapEmpCode = new HashMap<>();
		for (EmployeeDTO employeeDtoObj : employeeDtoList) {
			Long employeeId = employeeDtoObj.getEmployeeId();
			String boimatricId = employeeDtoObj.getBiometricId();
			String employeeCode = employeeDtoObj.getEmployeeCode();
			mapEmpIdAndEmpCode.put(boimatricId, employeeDtoObj);
			mapEmpCode.put(employeeCode, employeeId);
		}
		List<Object[]> epushAttendanceobjectList = new ArrayList<Object[]>();
		List<AttendanceLogDTO> epushAttendanceLogDtoList = new ArrayList<AttendanceLogDTO>();
		List<Object[]> empAttendanceList = punchTimeDetailsService.getEmpAttendaceList(companyId);
		List<AttendanceLogDTO> systemAttendanceLogList= attendanceCalculationAdaptor.objectListToUImodelforCron(empAttendanceList,mapEmpCode);
		if(sreialNoList.size()>0) {
		 epushAttendanceobjectList =attendanceCalculationService.getFirstAttendanceLogFromEpush( sreialNoList);
	
		epushAttendanceLogDtoList = attendanceCalculationAdaptor.EpushAttendanceObjListToDto(epushAttendanceobjectList,prefix,mapEmpIdAndEmpCode);
		
		}
		List<AttendanceLogDTO> attendanceLogList= attendanceCalculationAdaptor.attendanceCalculationForfirstAttendanceList(epushAttendanceLogDtoList, systemAttendanceLogList,mapEmpIdAndEmpCode,companyId);
		
		List<DeviceLogsInfo> deviceLogsInfoList	 = 	attendanceCalculationAdaptor.attendanceLogDtotoDeviceInfoList(attendanceLogList);
		deviceLogsService.saveAll(deviceLogsInfoList);
		}catch(Exception exception)
		{
			logger.info("LateComersProcessScheduler  data not saved for ..."+this.tenant);
		}
		
		});
	
	}
	/*private EmployeeDTO getEmployeeIdByRestTamplate(String companyId) {
		logger.info("Company Id----->" + companyId);
		logger.info("getEmployeeIdByRestTamplate is calling : ");
		String url = environment.getProperty("application.employeeTemp");
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders header = new HttpHeaders();
		header.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		Map<String, String> params = new HashMap<>();
		params.put("companyId", companyId);
		return restTemplate.getForObject(url, EmployeeDTO.class, params);
	}*/
	public void scheduleTaskWithFixedRate() {
		logger.info("Fixed Rate Task :: Execution Time - {}", dateTimeFormatter.format(LocalDateTime.now()));
	}
	
	
	/*
	 * 31-MAR-2020
	 */
	public synchronized void SyncAttendanceViaDate( Date date) {
		List<Object[]> devideInfoObjList = deviceInfoService.findDevice();
		List<String> sreialNoList = new ArrayList<String>();
		List<DeviceInfo> deviceInfoList = deviceInfoAdaptor.getDeviceInfoList(devideInfoObjList);
		Long companyId = 1L;
		String prefix = "COM";
		for (DeviceInfo deviceInfo : deviceInfoList) {
			sreialNoList.add(deviceInfo.getSerialNumber());
			companyId = deviceInfo.getCompanyId();
			prefix = deviceInfo.getPrefix();
		}

		List<Employee> employeeList = employeePersonalInformationService.fetchEmployee(companyId);
		List<EmployeeDTO> employeeDtoList = employeePersonalInformationAdaptor
				.databaseModelToUiDtoListNativeSearch(employeeList);
		EmployeeDTO employeeDto = new EmployeeDTO();
		employeeDto.setEmployessList(employeeDtoList);

		Map<String, EmployeeDTO> mapEmpIdAndEmpCode = new HashMap<>();
		Map<String, Long> mapEmpCode = new HashMap<>();
		for (EmployeeDTO employeeDtoObj : employeeDtoList) {
			Long employeeId = employeeDtoObj.getEmployeeId();
			String boimatricId = employeeDtoObj.getBiometricId();
			String employeeCode = employeeDtoObj.getEmployeeCode();
			mapEmpIdAndEmpCode.put(boimatricId, employeeDtoObj);
			mapEmpCode.put(employeeCode, employeeId);
		}

		List<Object[]> lateCommersEmployeeListWithCount = attendanceLogService
				.getLateCommersEmployeeListWithCountViaDate(companyId, date);
		List<DeviceLogsInfoDTO> deviceLogsInfoDTO = deviceLogsService.getAllLateComersListByDate(companyId, date);
		List<Object[]> objectList = new ArrayList<Object[]>();
		List<AttendanceLogDTO> attendanceLogDtoList = new ArrayList<AttendanceLogDTO>();
		if (sreialNoList.size() > 0) {
			objectList = attendanceCalculationService.getAttendanceFromEpushViaDate(sreialNoList, date);
			attendanceLogDtoList = attendanceCalculationAdaptor.objListToDto1(objectList, employeeDtoList,
					mapEmpIdAndEmpCode, prefix, companyId);
		}

		List<Object[]> empAttendanceList = punchTimeDetailsService.getEmpAttendaceListViaDate(companyId, date);
		List<AttendanceLogDTO> systemAttendanceLogList = attendanceCalculationAdaptor
				.objectListToUImodelforCron(empAttendanceList, mapEmpCode);
		List<AttendanceLogDTO> attendanceLogList = attendanceCalculationAdaptor
				.attendanceCalculation1(attendanceLogDtoList, systemAttendanceLogList, mapEmpIdAndEmpCode, companyId);

		HalfDayRule halfDayRule = rulesService.getHalfDayRule(companyId);
		List<AttendanceLog> attendanceLogList1 = attendanceCalculationAdaptor.uiDtoToDatabaseModelList2(
				attendanceLogList, mapEmpIdAndEmpCode, halfDayRule, lateCommersEmployeeListWithCount,
				deviceLogsInfoDTO);

		attendanceLogService.savePunchTimeLogViaDate(attendanceLogList1, date);

	}

	/*
	 * 31-MAR-2020
	 */
	public List<AttendanceLogDTO> unsuccessSyncAttendance(Long companyId) {
		Date currentDate = new Date();

		List<Object[]> devideInfoObjList = deviceInfoService.findDevice();
		List<String> sreialNoList = new ArrayList<String>();
		List<DeviceInfo> deviceInfoList = deviceInfoAdaptor.getDeviceInfoList(devideInfoObjList);
		// Long companyId =1L;
		String prefix = "COM";
		for (DeviceInfo deviceInfo : deviceInfoList) {
			sreialNoList.add(deviceInfo.getSerialNumber());
			companyId = deviceInfo.getCompanyId();
			prefix = deviceInfo.getPrefix();
		}

		List<Employee> employeeList = employeePersonalInformationService.fetchEmployee(companyId);
		List<EmployeeDTO> employeeDtoList = employeePersonalInformationAdaptor
				.databaseModelToUiDtoListNativeSearch(employeeList);
		EmployeeDTO employeeDto = new EmployeeDTO();
		employeeDto.setEmployessList(employeeDtoList);
		
		Map<String, EmployeeDTO> mapEmpIdAndEmpCode = new HashMap<>();
		Map<String, Long> mapEmpCode = new HashMap<>();
		for (EmployeeDTO employeeDtoObj : employeeDtoList) {
			Long employeeId = employeeDtoObj.getEmployeeId();
			String boimatricId = employeeDtoObj.getBiometricId();
			String employeeCode = employeeDtoObj.getEmployeeCode();
			mapEmpIdAndEmpCode.put(boimatricId, employeeDtoObj);
			mapEmpCode.put(employeeCode, employeeId);
		}
		List<AttendanceLog> attendanceLogList = attendanceLogService.getAttendanceLogPreviousDays(companyId,
				currentDate);
		List<AttendanceLogDTO> attendanceLogSyncedFailedList = attendanceCalculationAdaptor
				.unseccessSyncedDateList(attendanceLogList, currentDate);

		List<Object[]> objectList = new ArrayList<Object[]>();
		List<AttendanceLogDTO> attendanceLogDtoList = new ArrayList<AttendanceLogDTO>();
		
		List<Object[]> empAttendanceList = new ArrayList<Object[]>();

		Iterator<AttendanceLogDTO> itr = attendanceLogSyncedFailedList.iterator();

		while (itr.hasNext()) {
			AttendanceLogDTO syncedFailed = itr.next();
			if (sreialNoList.size() > 0) {
				objectList = attendanceCalculationService.getAttendanceFromEpushViaDate(sreialNoList,
						syncedFailed.getAttendanceDates());
				attendanceLogDtoList = attendanceCalculationAdaptor.objListToDto1(objectList, employeeDtoList,
						mapEmpIdAndEmpCode, prefix, companyId);
			}

			empAttendanceList = punchTimeDetailsService.getEmpAttendaceListViaDate(companyId,
					syncedFailed.getStartDates());
			
			
			if (attendanceLogDtoList.size() == 0 && empAttendanceList.size() == 0) {
				itr.remove();
			}
		}
		return attendanceLogSyncedFailedList;
	}
	
	private void triggerEmail(Date missingDate,EmailConfiguration confugration,EmailNotificationMaster listEmail) {

		try {
			 
			JavaMailSenderImpl mailSender = null;
			mailSender = new JavaMailSenderImpl();
			Properties props = mailSender.getJavaMailProperties();
			if (confugration.getHost().equals(StatusMessage.HOST_NAME_SMTP)) {
				logger.info("AttendanceCloneSyncEmailScheduler==.getJavaMailSender()");
				mailSender.setHost(confugration.getHost());
				mailSender.setPort(confugration.getPort());
				mailSender.setUsername(listEmail.getUserName());
				mailSender.setPassword(listEmail.getPassword());
				props.put(StatusMessage.MAIL_TRANSPORT_PROTOCOL, confugration.getProtocol());
				props.put(StatusMessage.MAIL_SMTP_AUTH, confugration.getAuth());
				props.put(StatusMessage.MAIL_SMTP_SSL_TRUST, confugration.getSslName());
				props.put(StatusMessage.MAIL_SMTP_STARTTLS_ENABLE, confugration.getStarttlsName());
				props.put(StatusMessage.MAIL_MIME_CHARSET, ENCODING);

			} else {
				logger.info("AttendanceCloneSyncEmailScheduler==.getJavaMailSender()");
				mailSender.setHost(confugration.getHost());
				mailSender.setPort(confugration.getPort());
				mailSender.setUsername(listEmail.getUserName());
				mailSender.setPassword(listEmail.getPassword());
				props.put(StatusMessage.MAIL_TRANSPORT_PROTOCOL, confugration.getProtocol());
				props.put(StatusMessage.MAIL_SMTP_AUTH, confugration.getAuth());
				props.put(StatusMessage.MAIL_SMTP_SSL_TRUST, confugration.getSslName());
				props.put(StatusMessage.MAIL_SMTP_STARTTLS_ENABLE, confugration.getStarttlsName());
				props.put(StatusMessage.MAIL_MIME_CHARSET, ENCODING);
				props.put(StatusMessage.MAIL_SMTP_SOCKETFACTORY_CLASS, "javax.net.ssl.SSLSocketFactory");
				props.put(StatusMessage.MAIL_SMTP_SOCKETFACTORY_FALLBACK, "true");
			}
		 
			SimpleDateFormat sm1 = new SimpleDateFormat("dd-MM-yyyy");
			String currentStatus = sm1.format(missingDate);

			MimeMessage mimeMessage = mailSender.createMimeMessage();
			MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);

			Map<String, Object> model = new HashMap<String, Object>();

			model.put("missingDate", currentStatus);
			String text = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine,
					"templates/attendance_clone_sync.vm", "UTF-8", model);
			mimeMessageHelper.setSubject(listEmail.getSubject());
			mimeMessageHelper.setFrom(listEmail.getUserName());

			if (listEmail.getToMail() != null && !listEmail.getToMail().equals("")) {
				String toList = listEmail.getToMail();
				String[] to = toList.split(",");
				mimeMessageHelper.setTo(to);
			}
//			else {
//				String toList = StatusMessage.EMAIL;
//				mimeMessageHelper.setTo(toList);
//			}
			if (listEmail.getCc() != null && !listEmail.getCc().equals("")) {
				String ccList = listEmail.getCc();
				String[] cc = ccList.split(",");
				mimeMessageHelper.setCc(cc);
			}
			if (listEmail.getBcc() != null && !listEmail.getBcc().equals("")) {
				String bccList = listEmail.getBcc();
				String[] bcc = bccList.split(",");
				mimeMessageHelper.setBcc(bcc);
			}
			mimeMessageHelper.setText(text, true);
			if (listEmail.getActiveStatus().equals(StatusMessage.ACTIVE_CODE)) {
				try {
					mailSender.send(mimeMessageHelper.getMimeMessage());
					logger.info(" AttendanceCloneSyncEmailScheduler mail send Successfully");
				} catch (Exception e) {
					e.printStackTrace();
					logger.info(" AttendanceCloneSyncEmailSchedulerr mail send failed");
				}
			} else
				logger.info(" AttendanceCloneSyncEmailSchedulerr mail send failed ActiveStatus()"
						+ listEmail.getActiveStatus());

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
