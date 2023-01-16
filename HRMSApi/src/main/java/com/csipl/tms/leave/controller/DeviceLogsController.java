package com.csipl.tms.leave.controller;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.csipl.hrms.common.exception.PayRollProcessException;
import com.csipl.hrms.common.util.ErrorHandling;
import com.csipl.tms.attendanceCalculation.controller.AttendanceReportExcelWriter;
import com.csipl.tms.attendanceregularizationrequest.service.ARRequestPagingAndFilterService;
import com.csipl.tms.deviceinfo.service.DeviceLogsService;
import com.csipl.tms.dto.common.EntityCountDTO;
import com.csipl.tms.dto.common.PageIndex;
import com.csipl.tms.dto.common.SearchDTO;
import com.csipl.tms.dto.deviceinfo.DeviceLogsInfoDTO;

@RequestMapping("/deviceLogs")
@RestController
public class DeviceLogsController {
	private static final Logger log = LoggerFactory.getLogger(DeviceLogsController.class);

	@Autowired
	DeviceLogsService deviceLogsService;

	@Autowired
	ARRequestPagingAndFilterService aRRequestPagingAndFilterService;

	@RequestMapping(value = "/presentAll", method = RequestMethod.GET)
	public int presentCountAll(HttpServletRequest req) {
		int presentCount = deviceLogsService.presentCountAll();
		return presentCount;
	}

	@RequestMapping(value = "/presentReportingTo/{companyId}/{employeeId}", method = RequestMethod.GET)
	public int presentCountReportingTo(@PathVariable("companyId") Long companyId,
			@PathVariable("employeeId") Long employeeId, HttpServletRequest req) {

		int presentCountR = deviceLogsService.presentCountReportingTo(companyId, employeeId);
		return presentCountR;
	}

	@RequestMapping(value = "/absentAll", method = RequestMethod.GET)
	public int absentCountAll(HttpServletRequest req) {
		int presentCount = deviceLogsService.absentCountAll();
		return presentCount;
	}

	@RequestMapping(value = "/absentReportingTo/{companyId}/{employeeId}", method = RequestMethod.GET)
	public int absentCountReportingTo(@PathVariable("companyId") Long companyId,
			@PathVariable("employeeId") Long employeeId, HttpServletRequest req) {
		int presentCount = deviceLogsService.absentCountReportingTo(companyId, employeeId);
		return presentCount;
	}

	@RequestMapping(value = "/lateComers", method = RequestMethod.GET)
	public int lateComersAll(HttpServletRequest req) throws ParseException {

		int presentCount = deviceLogsService.lateComersAll();

		return presentCount;
	}

	@RequestMapping(value = "/lateComersReportingTo/{companyId}/{employeeId}", method = RequestMethod.GET)
	public int lateComersReportingTo(@PathVariable("companyId") Long companyId,
			@PathVariable("employeeId") Long employeeId, HttpServletRequest req) throws ParseException {

		int presentCount = deviceLogsService.lateComersReportingTo(companyId, employeeId);
		return presentCount;
	}

	/**
	 * 
	 * @param companyId
	 * @param req
	 * @return
	 * @throws ErrorHandling
	 * @throws PayRollProcessException
	 */
	@RequestMapping(value = "/getAllLateComersList/{companyId}", method = RequestMethod.GET)
	public @ResponseBody List<DeviceLogsInfoDTO> getAllLateComersList(@PathVariable Long companyId,
			HttpServletRequest req) throws ErrorHandling, PayRollProcessException {
		log.info("getAllLateComersList() companyId--  " + companyId);
		List<DeviceLogsInfoDTO> list = deviceLogsService.getAllLateComersList(companyId);
		if (list.size() > 0)
			return list;
		else
			throw new ErrorHandling("LateComers Data Not Found");
	}

	/**
	 * 
	 * @param companyId
	 * @param req
	 * @return
	 * @throws ErrorHandling
	 * @throws PayRollProcessException
	 */

	@RequestMapping(value = "/getAllAbsentList/{companyId}", method = RequestMethod.GET)
	public @ResponseBody List<DeviceLogsInfoDTO> getAllAbsentList(@PathVariable Long companyId, HttpServletRequest req)
			throws ErrorHandling, PayRollProcessException {
		log.info("getAllAbsentList() companyId--  " + companyId);
		List<DeviceLogsInfoDTO> list = deviceLogsService.getAllAbsentList(companyId);
		if (list.size() > 0)
			return list;
		else
			throw new ErrorHandling(" Absent List Data Not Found");
	}

	/**
	 * 
	 * @param companyId
	 * @param req
	 * @return
	 * @throws ErrorHandling
	 * @throws PayRollProcessException
	 */
	@RequestMapping(value = "/getAllPresentList/{companyId}", method = RequestMethod.GET)
	public @ResponseBody List<DeviceLogsInfoDTO> getAllPresentList(@PathVariable Long companyId, HttpServletRequest req)
			throws ErrorHandling, PayRollProcessException {
		log.info("getAllPresentList() companyId--  " + companyId);
		List<DeviceLogsInfoDTO> list = deviceLogsService.getAllPresentList(companyId);
		if (list.size() > 0)
			return list;
		else
			throw new ErrorHandling("  Present List Data Not Found");
	}

	/**
	 * 
	 * @param companyId
	 * @param req
	 * @return
	 * @throws ErrorHandling
	 * @throws PayRollProcessException
	 */
	// @RequestMapping(value =
	// "/getAllLateComersListByDate/{companyId}/{actionDate}", method =
	// RequestMethod.GET)
	@RequestMapping(value = "/getAllLateComersListByDate/{companyId}/{actionDate}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody List<DeviceLogsInfoDTO> getAllLateComersListByDate(@PathVariable("companyId") Long companyId,
			@PathVariable("actionDate") Date actionDate, @RequestBody SearchDTO searcDto)
			throws ErrorHandling, PayRollProcessException {
		log.info("getAllLateComersListByDate() companyId--  " + companyId);
		// List<DeviceLogsInfoDTO> list =
		// deviceLogsService.getAllLateComersListByDate(companyId, actionDate);
		List<DeviceLogsInfoDTO> list = aRRequestPagingAndFilterService.getAllLateComersListByDate(companyId, actionDate,
				searcDto);
		if (list.size() > 0)
			return list;
		else
			throw new ErrorHandling("LateComers Data Not Found");
	}

	/**
	 * 
	 * @param companyId
	 * @param req
	 * @return
	 * @throws ErrorHandling
	 * @throws PayRollProcessException
	 */

	// @RequestMapping(value = "/getAllAbsentListByDate/{companyId}/{actionDate}",
	// method = RequestMethod.GET)
	@RequestMapping(value = "/getAllAbsentListByDate/{companyId}/{actionDate}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody List<DeviceLogsInfoDTO> getAllAbsentListByDate(@PathVariable("companyId") Long companyId,
			@PathVariable("actionDate") Date actionDate, @RequestBody SearchDTO searcDto)
			throws ErrorHandling, PayRollProcessException {
		log.info("getAllAbsentListByDate() companyId--  " + companyId);
		List<DeviceLogsInfoDTO> list = aRRequestPagingAndFilterService.getAllAbsentListByDate(companyId, actionDate,
				searcDto);

		if (list.size() > 0)
			return list;
		else
			throw new ErrorHandling(" Absent List Data Not Found");
	}

	/**
	 * 
	 * @param companyId
	 * @param req
	 * @return
	 * @throws ErrorHandling
	 * @throws PayRollProcessException
	 */
	// @RequestMapping(value = "/getAllPresentListByDate/{companyId}/{actionDate}",
	// method = RequestMethod.GET)
	@RequestMapping(value = "/getAllPresentListByDate/{companyId}/{actionDate}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody List<DeviceLogsInfoDTO> getAllPresentListByDate(@PathVariable("companyId") Long companyId,
			@PathVariable("actionDate") Date actionDate, @RequestBody SearchDTO searcDto)
			throws ErrorHandling, PayRollProcessException {
		log.info("getAllPresentListByDate() companyId--  " + companyId + "--date --" + actionDate);
		List<DeviceLogsInfoDTO> list = aRRequestPagingAndFilterService.getAllPresentListByDate(companyId, actionDate,
				searcDto);

		if (list.size() > 0)
			return list;
		else
			throw new ErrorHandling("  Present List Data Not Found");
	}

	@RequestMapping(path = "/currentAttendanceReport/{companyId}/{attendanceDate}", method = RequestMethod.GET)
	public void currentAttendanceReport(@PathVariable("companyId") Long companyId,
			@PathVariable("attendanceDate") Date attendanceDate, HttpServletRequest req, HttpServletResponse response)
			throws ErrorHandling, ParseException, InvalidFormatException {
		log.info(">>>>>> currentAttendanceReport <<<<<<<<<<<<<<<<<<< Date " + attendanceDate);
		log.info(">>>>>> currentAttendanceReport <<<<<<<<<<<<<<<<<<< Date toString " + attendanceDate.toString());
		String[] columns = { "Sr. No.", "Date", "Employee Code", "Employee", "Department", "Job Location",
				"Reporting Manager", "Shift", "Shift Duration", "Punching Mode", "Time In", "Time Out", "Total Hours",
				"Attendance Status", "Late By", "Early By", "Early Before", "Location -Time In", "Location-Time Out" };
		List<DeviceLogsInfoDTO> devicesLogDetails = deviceLogsService.currentAttendanceReport(companyId,
				attendanceDate);
		String attendDate = new SimpleDateFormat("yyyy-MM-dd").format(attendanceDate);
		try {
			response.setContentType("application/application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
			response.setHeader("Content-Disposition", "attachment;filename=liveAttandanceReport.xlsx");
			if (devicesLogDetails.size() > 0) {
				Workbook workbook = AttendanceReportExcelWriter.currentAttendanceReport(devicesLogDetails, columns,
						attendDate);
				ServletOutputStream fileOut = response.getOutputStream();
				workbook.write(fileOut);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@GetMapping(path = "/teamsAttendanceReport/{companyId}/{employeeId}/{attendanceDate}")
	public void teamsAttendanceReport(@PathVariable("companyId") Long companyId,
			@PathVariable("employeeId") Long employeeId, @PathVariable("attendanceDate") Date attendanceDate,
			HttpServletRequest req, HttpServletResponse response)
			throws ErrorHandling, ParseException, InvalidFormatException {
		String[] columns = { "Sr. No.", "Date", "Employee Code", "Employee", "Department", "Job Location",
				"Reporting Manager", "Shift", "Shift Duration", "Punching Mode", "Time In", "Time Out", "Total Hours",
				"Attendance Status", "Late By", "Early By", "Early Before", "Location -Time In", "Location-Time Out" };
		List<DeviceLogsInfoDTO> devicesLogDetails = deviceLogsService.getTeamsAttendanceReport(companyId, employeeId,
				attendanceDate);
		String attendDate = new SimpleDateFormat("yyyy-MM-dd").format(attendanceDate);
		try {
			response.setContentType("application/application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
			response.setHeader("Content-Disposition", "attachment;filename=liveAttandanceReport.xlsx");
			if (devicesLogDetails.size() > 0) {
				Workbook workbook = AttendanceReportExcelWriter.currentAttendanceReport(devicesLogDetails, columns,
						attendDate);
				ServletOutputStream fileOut = response.getOutputStream();
				workbook.write(fileOut);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@RequestMapping(value = "/punchInOnCDate/{nameOfUser}", method = RequestMethod.GET)
	public @ResponseBody DeviceLogsInfoDTO puchInOnCurrentDate(@PathVariable("nameOfUser") String nameOfUser) {
		log.info(" name of user  " + nameOfUser);
		DeviceLogsInfoDTO deviceLogsInfoDTO = deviceLogsService.punchInOnCDate(nameOfUser);
		return deviceLogsInfoDTO;
	}

	// Teams Absent list on Date
	// @GetMapping(value = "/teamsAbsentListByDate/{companyId}/{employeeId}")
	@RequestMapping(value = "/teamsAbsentListByDate/{companyId}/{employeeId}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody List<DeviceLogsInfoDTO> getTeamsAbsentListByDate(@PathVariable("companyId") Long companyId,
			@PathVariable("employeeId") Long employeeId, @RequestBody SearchDTO searcDto)
			throws ErrorHandling, PayRollProcessException {
		log.info("getTeamsAbsentListByDate() companyId--  " + companyId);

		List<DeviceLogsInfoDTO> list = aRRequestPagingAndFilterService.getTeamsAbsentListByDate(companyId, employeeId,
				new Date(), searcDto);

		if (list.size() > 0)
			return list;
		else
			throw new ErrorHandling(" Absent List Data Not Found");
	}

	@RequestMapping(value = "/teamsAbsentListByDateCount/{companyId}/{pageSize}/{employeeId}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody EntityCountDTO teamsAbsentListByDateCount(@PathVariable("companyId") Long companyId,
			@PathVariable("pageSize") String pageSize, @PathVariable("employeeId") Long employeeId,
			@RequestBody SearchDTO searcDto) throws PayRollProcessException {
		int count;
		List<PageIndex> pageIndexList = new ArrayList<PageIndex>();

		int parPageRecord = Integer.parseInt(pageSize);
		EntityCountDTO entityCountDto = new EntityCountDTO();
		List<DeviceLogsInfoDTO> absentList = aRRequestPagingAndFilterService.getTeamsAbsentListByDate(companyId,
				employeeId, new Date(), searcDto);

		count = absentList.size();
		System.out.println("absentList count :" + count);
		int pages = (count + parPageRecord - 1) / parPageRecord;

		for (int i = 1; i <= pages; i++) {
			PageIndex pageIndex = new PageIndex();
			pageIndex.setPageIndex(i);
			pageIndexList.add(pageIndex);
		}
		entityCountDto.setPageIndexs(pageIndexList);
		entityCountDto.setCount(count);
		return entityCountDto;
	}

	// Teams Present list on Date
	// @GetMapping(value = "/teamsPresentListByDate/{companyId}/{employeeId}")
	@RequestMapping(value = "/teamsPresentListByDate/{companyId}/{employeeId}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody List<DeviceLogsInfoDTO> teamsPresentListByDate(@PathVariable("companyId") Long companyId,
			@PathVariable("employeeId") Long employeeId, @RequestBody SearchDTO searcDto)
			throws ErrorHandling, PayRollProcessException {
		log.info("getteamsPresentListByDate() companyId--  " + companyId);
		// List<DeviceLogsInfoDTO> list =
		// deviceLogsService.getTeamsPresentListByDate(companyId, employeeId, new
		// Date());
		List<DeviceLogsInfoDTO> list = aRRequestPagingAndFilterService.getTeamsPresentListByDate(companyId, employeeId,
				new Date(), searcDto);

		if (list.size() > 0)
			return list;
		else
			throw new ErrorHandling("  Present List Data Not Found");
	}

	// Teams LateComers list on Date
	// @GetMapping(value = "/teamsLateComersListByDate/{companyId}/{employeeId}")
	@RequestMapping(value = "/teamsLateComersListByDate/{companyId}/{employeeId}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody List<DeviceLogsInfoDTO> teamsLateComersListByDate(@PathVariable("companyId") Long companyId,
			@PathVariable("employeeId") Long employeeId, @RequestBody SearchDTO searcDto)
			throws ErrorHandling, PayRollProcessException {
		log.info("getteamsLateComersListByDate() companyId--  " + companyId);
		List<DeviceLogsInfoDTO> list = aRRequestPagingAndFilterService.getTeamsLateComersListByDate(companyId,
				employeeId, new Date(), searcDto);
		if (list.size() > 0)
			return list;
		else
			throw new ErrorHandling("LateComers Data Not Found");
	}

	@RequestMapping(value = "/teamsPresentListByDateCount/{companyId}/{pageSize}/{employeeId}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody EntityCountDTO teamsPresentListByDateCount(@PathVariable("companyId") Long companyId,
			@PathVariable("pageSize") String pageSize, @PathVariable("employeeId") Long employeeId,
			@RequestBody SearchDTO searcDto) throws PayRollProcessException {
		int count;
		List<PageIndex> pageIndexList = new ArrayList<PageIndex>();

		int parPageRecord = Integer.parseInt(pageSize);
		EntityCountDTO entityCountDto = new EntityCountDTO();
		List<DeviceLogsInfoDTO> presentList = aRRequestPagingAndFilterService.getTeamsPresentListByDate(companyId,
				employeeId, new Date(), searcDto);

		count = presentList.size();
		System.out.println("presentList count :" + count);
		int pages = (count + parPageRecord - 1) / parPageRecord;

		for (int i = 1; i <= pages; i++) {
			PageIndex pageIndex = new PageIndex();
			pageIndex.setPageIndex(i);
			pageIndexList.add(pageIndex);
		}
		entityCountDto.setPageIndexs(pageIndexList);
		entityCountDto.setCount(count);
		return entityCountDto;
	}

	@RequestMapping(value = "/teamsLateComersListByDateCount/{companyId}/{pageSize}/{employeeId}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody EntityCountDTO teamsLateComersListByDateCount(@PathVariable("companyId") Long companyId,
			@PathVariable("pageSize") String pageSize, @PathVariable("employeeId") Long employeeId,
			@RequestBody SearchDTO searcDto) throws PayRollProcessException {
		int count;
		List<PageIndex> pageIndexList = new ArrayList<PageIndex>();

		int parPageRecord = Integer.parseInt(pageSize);
		EntityCountDTO entityCountDto = new EntityCountDTO();
		List<DeviceLogsInfoDTO> lateComersList = aRRequestPagingAndFilterService.getTeamsLateComersListByDate(companyId,
				employeeId, new Date(), searcDto);

		count = lateComersList.size();
		System.out.println("lateComersList count :" + count);
		int pages = (count + parPageRecord - 1) / parPageRecord;

		for (int i = 1; i <= pages; i++) {
			PageIndex pageIndex = new PageIndex();
			pageIndex.setPageIndex(i);
			pageIndexList.add(pageIndex);
		}
		entityCountDto.setPageIndexs(pageIndexList);
		entityCountDto.setCount(count);
		return entityCountDto;
	}

	@RequestMapping(value = "/getAllLateComersListByDateCount/{companyId}/{pageSize}/{actionDate}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody EntityCountDTO getAllLateComersListByDateCount(@PathVariable("companyId") Long companyId,
			@PathVariable("pageSize") String pageSize, @PathVariable("actionDate") Date actionDate,
			@RequestBody SearchDTO searcDto) throws PayRollProcessException {
		int count;
		List<PageIndex> pageIndexList = new ArrayList<PageIndex>();

		int parPageRecord = Integer.parseInt(pageSize);
		EntityCountDTO entityCountDto = new EntityCountDTO();
		List<DeviceLogsInfoDTO> lateComersList = aRRequestPagingAndFilterService.getAllLateComersListByDate(companyId,
				actionDate, searcDto);

		count = lateComersList.size();
		System.out.println("lateComersList count :" + count);
		int pages = (count + parPageRecord - 1) / parPageRecord;

		for (int i = 1; i <= pages; i++) {
			PageIndex pageIndex = new PageIndex();
			pageIndex.setPageIndex(i);
			pageIndexList.add(pageIndex);
		}
		entityCountDto.setPageIndexs(pageIndexList);
		entityCountDto.setCount(count);
		return entityCountDto;
	}

	@RequestMapping(value = "/getAllAbsentListByDateCount/{companyId}/{pageSize}/{actionDate}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody EntityCountDTO getAllAbsentListByDateCount(@PathVariable("companyId") Long companyId,
			@PathVariable("pageSize") String pageSize, @PathVariable("actionDate") Date actionDate,
			@RequestBody SearchDTO searcDto) throws PayRollProcessException {
		int count;
		List<PageIndex> pageIndexList = new ArrayList<PageIndex>();

		int parPageRecord = Integer.parseInt(pageSize);
		EntityCountDTO entityCountDto = new EntityCountDTO();
		List<DeviceLogsInfoDTO> absentList = aRRequestPagingAndFilterService.getAllAbsentListByDate(companyId,
				actionDate, searcDto);

		count = absentList.size();
		System.out.println("absentList count :" + count);
		int pages = (count + parPageRecord - 1) / parPageRecord;

		for (int i = 1; i <= pages; i++) {
			PageIndex pageIndex = new PageIndex();
			pageIndex.setPageIndex(i);
			pageIndexList.add(pageIndex);
		}
		entityCountDto.setPageIndexs(pageIndexList);
		entityCountDto.setCount(count);
		return entityCountDto;
	}

	@RequestMapping(value = "/getAllPresentListByDateCount/{companyId}/{pageSize}/{actionDate}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody EntityCountDTO getAllPresentListByDateCount(@PathVariable("companyId") Long companyId,
			@PathVariable("pageSize") String pageSize, @PathVariable("actionDate") Date actionDate,
			@RequestBody SearchDTO searcDto) throws PayRollProcessException {
		int count;
		List<PageIndex> pageIndexList = new ArrayList<PageIndex>();

		int parPageRecord = Integer.parseInt(pageSize);
		EntityCountDTO entityCountDto = new EntityCountDTO();
		List<DeviceLogsInfoDTO> presentList = aRRequestPagingAndFilterService.getAllPresentListByDate(companyId,
				actionDate, searcDto);

		count = presentList.size();
		System.out.println("presentList count :" + count);
		int pages = (count + parPageRecord - 1) / parPageRecord;

		for (int i = 1; i <= pages; i++) {
			PageIndex pageIndex = new PageIndex();
			pageIndex.setPageIndex(i);
			pageIndexList.add(pageIndex);
		}
		entityCountDto.setPageIndexs(pageIndexList);
		entityCountDto.setCount(count);
		return entityCountDto;
	}

}
