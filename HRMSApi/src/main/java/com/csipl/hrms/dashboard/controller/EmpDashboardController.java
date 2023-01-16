package com.csipl.hrms.dashboard.controller;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.csipl.hrms.common.enums.AgeChartEnum;
import com.csipl.hrms.common.enums.AgeWiseChartEnum;
import com.csipl.hrms.common.enums.DesignationChartEnum;
import com.csipl.hrms.common.enums.EsicChartEnumNew;
import com.csipl.hrms.common.enums.GenderChartEnumNew;
import com.csipl.hrms.common.enums.GraphEnum;
import com.csipl.hrms.common.enums.LeaveMyTeamEnum;
import com.csipl.hrms.common.enums.PFChartEnum;
import com.csipl.hrms.common.enums.PFlageEnum;
import com.csipl.hrms.common.exception.PayRollProcessException;
import com.csipl.hrms.common.util.DashbordExcelWriter;
import com.csipl.hrms.common.util.ErrorHandling;
import com.csipl.hrms.dto.payroll.dashboard.ChartNew;
import com.csipl.hrms.dto.report.AgeData;
import com.csipl.hrms.dto.report.AgeDto;
import com.csipl.hrms.dto.report.AgeWiseChart;
import com.csipl.hrms.dto.report.AttendanceChart;
import com.csipl.hrms.dto.report.AttendanceData;
import com.csipl.hrms.dto.report.AttendanceDto;
import com.csipl.hrms.dto.report.AttritionDto;
import com.csipl.hrms.dto.report.Chart;
import com.csipl.hrms.dto.report.Data;
import com.csipl.hrms.dto.report.DepartmentChart;
import com.csipl.hrms.dto.report.DepartmentChartDto;
import com.csipl.hrms.dto.report.DepartmentData;
import com.csipl.hrms.dto.report.DepartmentReportDTO;
import com.csipl.hrms.dto.report.DesignationChartDto;
import com.csipl.hrms.dto.report.DesignationData;
import com.csipl.hrms.dto.report.EmployeeReportDTO;
import com.csipl.hrms.dto.report.EsicChart;
import com.csipl.hrms.dto.report.GenderChart;
import com.csipl.hrms.dto.report.GenderDto;
import com.csipl.hrms.dto.report.GraphDto;
import com.csipl.hrms.dto.report.PfChart;
import com.csipl.hrms.model.payrollprocess.FinancialYear;
import com.csipl.hrms.service.adaptor.EmployeePersonalInformationAdaptor;
import com.csipl.hrms.service.employee.EmployeeAttritionService;
import com.csipl.hrms.service.payroll.FinancialYearService;
import com.csipl.hrms.service.report.DepartmentReportService;
import com.csipl.hrms.service.report.EmployeeReportService;

@RestController
public class EmpDashboardController {

	private static final Logger log = LoggerFactory.getLogger(EmpDashboardController.class);

	@Autowired
	EmployeeReportService employeeReportService;

	@Autowired
	EmployeeAttritionService employeeAttritionService;

	@Autowired
	FinancialYearService financialYearService;

	@Autowired
	DepartmentReportService departmentReportService;

	EmployeePersonalInformationAdaptor employeePersonalInformationAdaptor = new EmployeePersonalInformationAdaptor();

	@RequestMapping(path = "/countNotification", method = RequestMethod.GET)
	public @ResponseBody EmployeeReportDTO countNotificationApp(@RequestParam("companyId") String companyId)
			throws ErrorHandling, PayRollProcessException {
		Long companyIdValue = Long.parseLong(companyId);
		EmployeeReportDTO employeeReportDTO = employeeReportService.countNotification(companyIdValue);
		if (employeeReportDTO != null)
			return employeeReportDTO;
		else
			throw new ErrorHandling("Data Not Found");

	}

	// countNotificationForSessionRole

	@RequestMapping(path = "/countNotificationForSessionRole", method = RequestMethod.GET)
	public @ResponseBody EmployeeReportDTO countNotificationForSessionRole(
			@RequestParam("currentRole") String currentRole, @RequestParam("companyId") String companyId,
			HttpServletRequest req) throws ErrorHandling, PayRollProcessException

	{
		HttpSession session = req.getSession();
		session.setAttribute("currentRole", currentRole);
		Long companyIdValue = Long.parseLong(companyId);
		EmployeeReportDTO employeeReportDTO = employeeReportService.countNotification(companyIdValue);
		if (employeeReportDTO != null)
			return employeeReportDTO;
		else
			throw new ErrorHandling("Data Not Found");

	}

	@RequestMapping(path = "/employeeNotification", method = RequestMethod.GET)
	public @ResponseBody List<EmployeeReportDTO> employeeNotificationApp(@RequestParam("companyId") String companyId)
			throws ErrorHandling, PayRollProcessException {
		Long companyIdValue = Long.parseLong(companyId);
		List<EmployeeReportDTO> empList = employeeReportService.employeeNotification(companyIdValue,
				PFlageEnum.Notification.getpFlageEnum());
		if (empList != null)
			return empList;
		else
			throw new ErrorHandling("Data Not Found");

	}

	/**
	 * 
	 * @param companyId
	 * @return
	 * @throws ErrorHandling
	 * @throws PayRollProcessException
	 */
	@RequestMapping(path = "/empGenderWiseRatio/{companyId}", method = RequestMethod.GET)
	public @ResponseBody GenderDto empGenderWiseRatioApp(@PathVariable("companyId") Long companyId)
			throws ErrorHandling, PayRollProcessException {
		log.info("EmpDashboardController.empGenderWiseRatioApp()");
		List<EmployeeReportDTO> employeeReportDTO = employeeReportService.empGenderWiseRatio(companyId);
		ArrayList<Data> arrayData = new ArrayList<Data>();

		GenderChart chart = new GenderChart(GenderChartEnumNew.bgColor.getPieChartValue(),
				GenderChartEnumNew.startingAngle.getPieChartValue(), GenderChartEnumNew.showLegend.getPieChartValue(),
				GenderChartEnumNew.showTooltip.getPieChartValue(), GenderChartEnumNew.decimals.getPieChartValue(),
				GenderChartEnumNew.theme.getPieChartValue(), GenderChartEnumNew.pieRadius.getPieChartValue(),
				GenderChartEnumNew.labelDistance.getPieChartValue(),
				GenderChartEnumNew.showPercentValues.getPieChartValue(),
				GenderChartEnumNew.showPercentInToolTip.getPieChartValue(),
				GenderChartEnumNew.formatNumber.getPieChartValue(),
				GenderChartEnumNew.formatNumberScale.getPieChartValue(),
				GenderChartEnumNew.forceNumberScale.getPieChartValue(),
				GenderChartEnumNew.numberScaleUnit.getPieChartValue(),
				GenderChartEnumNew.smartLineColor.getPieChartValue(),
				GenderChartEnumNew.labelFontColor.getPieChartValue(), GenderChartEnumNew.showLabels.getPieChartValue(),
				GenderChartEnumNew.legendItemFontColor.getPieChartValue());
		for (EmployeeReportDTO empDt : employeeReportDTO) {
			Data maleData = new Data("Male", empDt.getMalePer(), GenderChartEnumNew.maleColor.getPieChartValue());
			Data femaleData = new Data("Female", empDt.getFemalePer(),
					GenderChartEnumNew.feMaleColor.getPieChartValue());

			arrayData.add(maleData);
			arrayData.add(femaleData);

		}

		GenderDto gt = new GenderDto(chart, arrayData);

		return gt;

	}

	/**
	 * 
	 * @param companyId
	 * @return
	 * @throws ErrorHandling
	 * @throws PayRollProcessException
	 */
	@RequestMapping(path = "/empAgeWiseRatio/{companyId}", method = RequestMethod.GET)
	public @ResponseBody AgeDto empAgeWiseRatioApp(@PathVariable("companyId") Long companyId)
			throws ErrorHandling, PayRollProcessException {
		log.info("EmpDashboardController.empAgeWiseRatioApp()");

		List<EmployeeReportDTO> employeeReportDTO = employeeReportService.empAgeWiseRatio(companyId);
		ArrayList<AgeData> arrayData = new ArrayList<AgeData>();

		AgeWiseChart chart = new AgeWiseChart(AgeWiseChartEnum.bgColor.getPieChartValue(),
				AgeWiseChartEnum.startingAngle.getPieChartValue(), AgeWiseChartEnum.showLegend.getPieChartValue(),
				AgeWiseChartEnum.showTooltip.getPieChartValue(), AgeWiseChartEnum.decimals.getPieChartValue(),
				AgeWiseChartEnum.theme.getPieChartValue(), AgeWiseChartEnum.pieRadius.getPieChartValue(),
				AgeWiseChartEnum.labelDistance.getPieChartValue(),
				AgeWiseChartEnum.showPercentValues.getPieChartValue(),
				AgeWiseChartEnum.showPercentInToolTip.getPieChartValue(),
				AgeWiseChartEnum.formatNumber.getPieChartValue(), AgeWiseChartEnum.formatNumberScale.getPieChartValue(),
				AgeWiseChartEnum.forceNumberScale.getPieChartValue(),
				AgeWiseChartEnum.numberScaleUnit.getPieChartValue(), AgeWiseChartEnum.smartLineColor.getPieChartValue(),
				AgeWiseChartEnum.labelFontColor.getPieChartValue(), AgeWiseChartEnum.showLabels.getPieChartValue(),
				AgeWiseChartEnum.legendItemFontColor.getPieChartValue());

		for (EmployeeReportDTO empDt : employeeReportDTO) {
			if (empDt.getEmpAge().equalsIgnoreCase("0")) {
			} else {
				AgeData data = new AgeData(empDt.getEmpRange(), empDt.getEmpAge(), empDt.getColor());
				arrayData.add(data);
			}
		}
		AgeDto at = new AgeDto(chart, arrayData);
		return at;

	}

	/*
	 * @RequestMapping(path = "/empAttendanceRatio", method = RequestMethod.GET)
	 * public @ResponseBody AttendanceDto
	 * empAttendanceMonthly(@RequestParam("companyId") String companyId,
	 * 
	 * @RequestParam("employeeId") String employeeId) throws ErrorHandling,
	 * PayRollProcessException {
	 * log.info("EmpDashboardController.empAttendanceMonthly()"); Long
	 * companyIdValue = Long.parseLong(companyId); Long employeeIdValue =
	 * Long.parseLong(employeeId); List<EmployeeReportDTO> employeeReportDTO =
	 * employeeReportService.empAttendanceRatio(companyIdValue, employeeIdValue);
	 * 
	 * ArrayList<AttendanceData> arrayData = new ArrayList<AttendanceData>();
	 * 
	 * AttendanceChart chart = new
	 * AttendanceChart(AgeChartEnum.formatnumberscale.getPieChartValue(),
	 * AgeChartEnum.use3DLighting.getPieChartValue(),
	 * AgeChartEnum.enableSmartLabels.getPieChartValue(),
	 * AgeChartEnum.startingAngle.getPieChartValue(),
	 * AgeChartEnum.showLabels.getPieChartValue(),
	 * AgeChartEnum.showPercentValues.getPieChartValue(),
	 * AgeChartEnum.showLegend.getPieChartValue(),
	 * AgeChartEnum.centerLabelBold.getPieChartValue(),
	 * AgeChartEnum.showTooltip.getPieChartValue(),
	 * AgeChartEnum.decimals.getPieChartValue(),
	 * AgeChartEnum.useDataPlotColorForLabels.getPieChartValue(),
	 * AgeChartEnum.theme.getPieChartValue());
	 * 
	 * for (EmployeeReportDTO empDt : employeeReportDTO) { if
	 * (empDt.getEmpAttenance().equalsIgnoreCase("0")) {
	 * 
	 * } else { AttendanceData data = new
	 * AttendanceData(empDt.getEmpAttendanceeRange(), empDt.getEmpAttenance());
	 * arrayData.add(data); }
	 * 
	 * }
	 * 
	 * AttendanceDto at = new AttendanceDto(chart, arrayData); //
	 * System.out.println("attendance dto.." + at.toString()); return at;
	 * 
	 * }
	 */
//	 
//	  @GetMapping(path = "/empAttendanceRatio/{companyId}/{employeeId}")
//	  public @ResponseBody AttendanceDto
//	  empAttendanceMonthly(@PathVariable("companyId") Long companyId,
//	  
//			  @PathVariable("employeeId") Long employeeId) throws ErrorHandling,
//	  PayRollProcessException {
//	  log.info("EmpDashboardController.empAttendanceMonthly()");  
//	   List<EmployeeReportDTO> employeeReportDTO =
//	  employeeReportService.empAttendanceRatio(companyId, employeeId);
//	  
//	  ArrayList<AttendanceData> arrayData = new ArrayList<AttendanceData>();
//	  
//	  AttendanceChart chart = new
//	  AttendanceChart(AgeChartEnum.formatnumberscale.getPieChartValue(),
//	  AgeChartEnum.use3DLighting.getPieChartValue(),
//	  AgeChartEnum.enableSmartLabels.getPieChartValue(),
//	  AgeChartEnum.startingAngle.getPieChartValue(),
//	  AgeChartEnum.showLabels.getPieChartValue(),
//	  AgeChartEnum.showPercentValues.getPieChartValue(),
//	  AgeChartEnum.showLegend.getPieChartValue(),
//	  AgeChartEnum.centerLabelBold.getPieChartValue(),
//	  AgeChartEnum.showTooltip.getPieChartValue(),
//	  AgeChartEnum.decimals.getPieChartValue(),
//	  AgeChartEnum.useDataPlotColorForLabels.getPieChartValue(),
//	  AgeChartEnum.theme.getPieChartValue());
//	  
//	  for (EmployeeReportDTO empDt : employeeReportDTO) { if
//	  (empDt.getEmpAttenance().equalsIgnoreCase("0")) {
//	  
//	  } else { AttendanceData data = new
//	  AttendanceData(empDt.getEmpAttendanceeRange(), empDt.getEmpAttenance());
//	  arrayData.add(data); }
//	  
//	  }
//	  
//	  AttendanceDto at = new AttendanceDto(chart, arrayData); //
//	  System.out.println("attendance dto.." + at.toString()); return at;
//	  
//	  }

	@RequestMapping(path = "/empDashboardInfo", method = RequestMethod.GET)
	public @ResponseBody EmployeeReportDTO EmployeeDashboardInfoApp(@RequestParam("companyId") String companyId)
			throws ErrorHandling, PayRollProcessException {
		log.info("EmpDashboardController.EmployeeDashboardInfoApp()");
		Long companyIdValue = Long.parseLong(companyId);
		EmployeeReportDTO employeeReportDTO = employeeReportService.countEMPIMPTODAYDATE(companyIdValue, "");
		if (employeeReportDTO != null)
			return employeeReportDTO;
		else
			throw new ErrorHandling("Data Not Found");

	}

	@RequestMapping(path = "/empBirthDayInfo", method = RequestMethod.GET)
	public @ResponseBody List<EmployeeReportDTO> EmployeeBirthDayInfo(@RequestParam("companyId") String companyId)
			throws ErrorHandling, PayRollProcessException {
		log.info("EmpDashboardController.EmployeeBirthDayInfo()");
		Long companyIdValue = Long.parseLong(companyId);
		// System.out.println(" empBirthDayInfo ");
		List<EmployeeReportDTO> empBirthDayList = employeeReportService.fetchBirthDayEmpList(companyIdValue,
				PFlageEnum.BirthDay.getpFlageEnum());
		if (empBirthDayList.size() > 0)
			return empBirthDayList;
		else
			throw new ErrorHandling("Data Not Found");

	}

	@RequestMapping(path = "/empAnniversaryInfo", method = RequestMethod.GET)
	public @ResponseBody List<EmployeeReportDTO> EmployeeAnniverSaryInfoApp(@RequestParam("companyId") String companyId)
			throws ErrorHandling, PayRollProcessException {
		log.info("EmpDashboardController.EmployeeAnniverSaryInfoApp()");
		Long companyIdValue = Long.parseLong(companyId);
		List<EmployeeReportDTO> empAnniversaryList = employeeReportService.fetchAnniversaryDayEmpList(companyIdValue,
				PFlageEnum.Anniversary.getpFlageEnum());
		if (empAnniversaryList.size() > 0)
			return empAnniversaryList;
		else
			throw new ErrorHandling("Data Not Found");

	}

	@RequestMapping(path = "/empWorkAnniversaryInfo", method = RequestMethod.GET)
	public @ResponseBody List<EmployeeReportDTO> EmployeeWorkAnniverSaryInfoApp(
			@RequestParam("companyId") String companyId) throws ErrorHandling, PayRollProcessException {
		Long companyIdValue = Long.parseLong(companyId);
		List<EmployeeReportDTO> empAnniversaryList = employeeReportService
				.fetchWorkAnniversaryDayEmpList(companyIdValue, PFlageEnum.Joinig.getpFlageEnum());
		if (empAnniversaryList.size() > 0)
			return empAnniversaryList;
		else
			throw new ErrorHandling("Data Not Found");

	}

	@RequestMapping(path = "/holidayInfoMonthWise", method = RequestMethod.GET)
	public @ResponseBody List<EmployeeReportDTO> holidayInfoApp(@RequestParam("companyId") String companyId)
			throws ErrorHandling, PayRollProcessException, ParseException {
		Long companyIdValue = Long.parseLong(companyId);
		List<EmployeeReportDTO> holidayListMonthWise = employeeReportService.fetchHolidayListByMonth(companyIdValue,
				PFlageEnum.Holiday.getpFlageEnum());
		if (holidayListMonthWise.size() > 0)
			return holidayListMonthWise;
		else
			throw new ErrorHandling("Data Not Found");

	}

	@RequestMapping(path = "/employeeDocumentConfirmationInfo", method = RequestMethod.GET)
	public @ResponseBody List<EmployeeReportDTO> EmployeeDocumentConfirmationInfo(
			@RequestParam("companyId") String companyId, HttpServletRequest req)
			throws ErrorHandling, PayRollProcessException {
		Long longcompanyId = Long.parseLong(companyId);
		List<EmployeeReportDTO> empDcoumentList = employeeReportService
				.fetchEmployeeDocumentConfirmation(longcompanyId);
		if (empDcoumentList.size() > 0)
			return empDcoumentList;
		else
			throw new ErrorHandling("Data Not Found");

	}

	@RequestMapping(path = "/employeeSeprationInfo", method = RequestMethod.GET)

	public @ResponseBody List<EmployeeReportDTO> EmployeeSeprationInfo(@RequestParam("companyId") String companyId,
			HttpServletRequest req) throws ErrorHandling, PayRollProcessException {
		Long longcompanyId = Long.parseLong(companyId);
		List<EmployeeReportDTO> empAnniversaryList = employeeReportService.fetchEmployeeSeprationInfo(longcompanyId);
		if (empAnniversaryList.size() > 0)
			return empAnniversaryList;
		else
			throw new ErrorHandling("Data Not Found");
	}

	@RequestMapping(path = "/empCountByDesignationWise/{companyId}", method = RequestMethod.GET)
	public @ResponseBody DesignationChartDto empCountByDesignationWise(@PathVariable("companyId") Long companyId,
			HttpServletRequest req) throws ErrorHandling, PayRollProcessException {
		log.info("EmpDashboardController.empCountByDesignationWise()");
		List<EmployeeReportDTO> empList = employeeReportService.empCountByDesignationWise(companyId);

		ArrayList<DesignationData> arrayData = new ArrayList<DesignationData>();

		com.csipl.hrms.dto.payroll.dashboard.ChartNew chart = new ChartNew(
				DesignationChartEnum.theme.getPieChartValue(), DesignationChartEnum.baseFontColor.getPieChartValue(),
				DesignationChartEnum.showBorder.getPieChartValue(), DesignationChartEnum.bgColor.getPieChartValue(),
				DesignationChartEnum.canvasBgColor.getPieChartValue(),
				DesignationChartEnum.showCanvasBorder.getPieChartValue(),
				DesignationChartEnum.showCanvasBorder.getPieChartValue(),
				DesignationChartEnum.baseFontSize.getPieChartValue(),
				DesignationChartEnum.showValues.getPieChartValue(),
				DesignationChartEnum.setAdaptiveYMin.getPieChartValue(),
				DesignationChartEnum.labelFontColor.getPieChartValue(),
				DesignationChartEnum.formatNumber.getPieChartValue(),
				DesignationChartEnum.yFormatNumber.getPieChartValue(),
				DesignationChartEnum.xFormatNumber.getPieChartValue(),
				DesignationChartEnum.formatNumberScale.getPieChartValue(),
				DesignationChartEnum.showYAxisvalue.getPieChartValue(),
				DesignationChartEnum.placeValuesInside.getPieChartValue());

		for (EmployeeReportDTO empDt : empList) {
			if (empDt.getEmpCount().equalsIgnoreCase("0")) {
			} else {
				DesignationData data = new DesignationData(empDt.getEmpDesignation(), empDt.getEmpCount(),
						empDt.getColor());
				arrayData.add(data);
			}
		}
		DesignationChartDto dt = new DesignationChartDto(chart, arrayData);

		return dt;
	}

	/**
	 * 
	 * empCountByDepartmentWise
	 */
	@RequestMapping(path = "/empCountByDepartmentWise/{companyId}", method = RequestMethod.GET)
	public @ResponseBody DepartmentChartDto empCountByDepartmentWise(@PathVariable("companyId") Long companyId,
			HttpServletRequest req) throws ErrorHandling, PayRollProcessException {
		log.info("EmpDashboardController.empCountByDepartmentWise()");
		List<EmployeeReportDTO> empList = employeeReportService.empCountByDepartmentWise(companyId);
		ArrayList<DepartmentData> arrayData = new ArrayList<DepartmentData>();
		com.csipl.hrms.dto.payroll.dashboard.ChartNew chart = new ChartNew(
				DesignationChartEnum.theme.getPieChartValue(), DesignationChartEnum.baseFontColor.getPieChartValue(),
				DesignationChartEnum.showBorder.getPieChartValue(), DesignationChartEnum.bgColor.getPieChartValue(),
				DesignationChartEnum.canvasBgColor.getPieChartValue(),
				DesignationChartEnum.showCanvasBorder.getPieChartValue(),
				DesignationChartEnum.showCanvasBorder.getPieChartValue(),
				DesignationChartEnum.baseFontSize.getPieChartValue(),
				DesignationChartEnum.showValues.getPieChartValue(),
				DesignationChartEnum.setAdaptiveYMin.getPieChartValue(),
				DesignationChartEnum.labelFontColor.getPieChartValue(),
				DesignationChartEnum.formatNumber.getPieChartValue(),
				DesignationChartEnum.yFormatNumber.getPieChartValue(),
				DesignationChartEnum.xFormatNumber.getPieChartValue(),
				DesignationChartEnum.formatNumberScale.getPieChartValue(),
				DesignationChartEnum.showYAxisvalue.getPieChartValue(),
				DesignationChartEnum.placeValuesInside.getPieChartValue());
		for (EmployeeReportDTO empDt : empList) {
			if (empDt.getEmpCount().equalsIgnoreCase("0")) {
			} else {
				DepartmentData data = new DepartmentData(empDt.getEmpDetp(), empDt.getEmpCount(), empDt.getColor());
				arrayData.add(data);
			}
		}
		DepartmentChartDto dt = new DepartmentChartDto(chart, arrayData);

		return dt;
	}

	/**
	 * 
	 * DepartmentWiseCTCInfoWithMonth
	 */
	@RequestMapping(path = "/departmentWiseCTCInfoWithMonth/{processMonth}/{companyId}", method = RequestMethod.GET)
	public @ResponseBody com.csipl.hrms.dto.payroll.dashboard.GraphDtoNew DepartmentWiseCTCInfoWithMonth(
			@PathVariable("processMonth") Long processMonth, @PathVariable("companyId") Long companyId,
			HttpServletRequest req) throws ErrorHandling, PayRollProcessException {
		log.info("EmpDashboardController.DepartmentWiseCTCInfoWithMonth()");
		List<DepartmentReportDTO> deptReportDto = departmentReportService.departmentWiseCTC(companyId, processMonth);

		ArrayList<com.csipl.hrms.dto.payroll.dashboard.Data> arrayData = new ArrayList<com.csipl.hrms.dto.payroll.dashboard.Data>();
		com.csipl.hrms.dto.payroll.dashboard.ChartNew chart = new ChartNew(
				DesignationChartEnum.theme.getPieChartValue(), DesignationChartEnum.baseFontColor.getPieChartValue(),
				DesignationChartEnum.showBorder.getPieChartValue(), DesignationChartEnum.bgColor.getPieChartValue(),
				DesignationChartEnum.canvasBgColor.getPieChartValue(),
				DesignationChartEnum.showCanvasBorder.getPieChartValue(),
				DesignationChartEnum.showCanvasBorder.getPieChartValue(),
				DesignationChartEnum.baseFontSize.getPieChartValue(),
				DesignationChartEnum.showValues.getPieChartValue(),
				DesignationChartEnum.setAdaptiveYMin.getPieChartValue(),
				DesignationChartEnum.labelFontColor.getPieChartValue(),
				DesignationChartEnum.formatNumber.getPieChartValue(),
				DesignationChartEnum.yFormatNumber.getPieChartValue(),
				DesignationChartEnum.xFormatNumber.getPieChartValue(),
				DesignationChartEnum.formatNumberScale.getPieChartValue(),
				DesignationChartEnum.showYAxisvalue.getPieChartValue(),
				DesignationChartEnum.placeValuesInside.getPieChartValue());
		for (DepartmentReportDTO deptDt : deptReportDto) {

			com.csipl.hrms.dto.payroll.dashboard.Data data = new com.csipl.hrms.dto.payroll.dashboard.Data(
					deptDt.getDeptNAME(), deptDt.getDeptCTC(), deptDt.getColor());
			arrayData.add(data);
		}

		com.csipl.hrms.dto.payroll.dashboard.GraphDtoNew gt = new com.csipl.hrms.dto.payroll.dashboard.GraphDtoNew(
				chart, arrayData);

		return gt;
	}

	// Designation Wise CTC

	/**
	 * 
	 * @param companyId
	 * @param req
	 * @return
	 * @throws ErrorHandling
	 * @throws PayRollProcessException
	 * 
	 */
	@RequestMapping(path = "/designationWiseCTCInfo/{companyId}", method = RequestMethod.GET)
	public @ResponseBody com.csipl.hrms.dto.payroll.dashboard.GraphDtoNew DesignationWiseCTCInfo(
			@PathVariable("companyId") Long companyId, HttpServletRequest req)
			throws ErrorHandling, PayRollProcessException {
		log.info("EmpDashboardController.DesignationWiseCTCInfo()");
		List<EmployeeReportDTO> empList = employeeReportService.fetchDesignationWiseCTC(companyId, 0L);
		ArrayList<com.csipl.hrms.dto.payroll.dashboard.Data> arrayData = new ArrayList<com.csipl.hrms.dto.payroll.dashboard.Data>();

		com.csipl.hrms.dto.payroll.dashboard.ChartNew chart = new ChartNew(
				DesignationChartEnum.theme.getPieChartValue(), DesignationChartEnum.baseFontColor.getPieChartValue(),
				DesignationChartEnum.showBorder.getPieChartValue(), DesignationChartEnum.bgColor.getPieChartValue(),
				DesignationChartEnum.canvasBgColor.getPieChartValue(),
				DesignationChartEnum.showCanvasBorder.getPieChartValue(),
				DesignationChartEnum.showCanvasBorder.getPieChartValue(),
				DesignationChartEnum.baseFontSize.getPieChartValue(),
				DesignationChartEnum.showValues.getPieChartValue(),
				DesignationChartEnum.setAdaptiveYMin.getPieChartValue(),
				DesignationChartEnum.labelFontColor.getPieChartValue(),
				DesignationChartEnum.formatNumber.getPieChartValue(),
				DesignationChartEnum.yFormatNumber.getPieChartValue(),
				DesignationChartEnum.xFormatNumber.getPieChartValue(),
				DesignationChartEnum.formatNumberScale.getPieChartValue(),
				DesignationChartEnum.showYAxisvalue.getPieChartValue(),
				DesignationChartEnum.placeValuesInside.getPieChartValue());

		for (EmployeeReportDTO emptDt : empList) {
			com.csipl.hrms.dto.payroll.dashboard.Data data = new com.csipl.hrms.dto.payroll.dashboard.Data(
					emptDt.getEmpDesignation(), emptDt.getEmpCtc(), emptDt.getColor());
			arrayData.add(data);
		}

		com.csipl.hrms.dto.payroll.dashboard.GraphDtoNew gt = new com.csipl.hrms.dto.payroll.dashboard.GraphDtoNew(
				chart, arrayData);

		return gt;

	}

	/**
	 * 
	 * @param companyId
	 * @param req
	 * @return getLastTwelveMonthSalary
	 * @throws ErrorHandling
	 * @throws PayRollProcessException
	 * 
	 */
	@RequestMapping(path = "/getLastTwelveMonthSalary/{companyId}", method = RequestMethod.GET)
	public @ResponseBody com.csipl.hrms.dto.payroll.dashboard.GraphDtoNew getLastTwelveMonthSalary(
			@PathVariable("companyId") Long companyId, HttpServletRequest req)
			throws ErrorHandling, PayRollProcessException {
		log.info("EmpDashboardController.getLastTwelveMonthSalary()");
		List<EmployeeReportDTO> empList = employeeReportService.fetchLastTwelveMonthSalary(companyId, 0L);
		ArrayList<com.csipl.hrms.dto.payroll.dashboard.Data> arrayData = new ArrayList<com.csipl.hrms.dto.payroll.dashboard.Data>();

		com.csipl.hrms.dto.payroll.dashboard.ChartNew chart = new ChartNew(
				DesignationChartEnum.theme.getPieChartValue(), DesignationChartEnum.baseFontColor.getPieChartValue(),
				DesignationChartEnum.showBorder.getPieChartValue(), DesignationChartEnum.bgColor.getPieChartValue(),
				DesignationChartEnum.canvasBgColor.getPieChartValue(),
				DesignationChartEnum.showCanvasBorder.getPieChartValue(),
				DesignationChartEnum.showCanvasBorder.getPieChartValue(),
				DesignationChartEnum.baseFontSize.getPieChartValue(),
				DesignationChartEnum.showValues.getPieChartValue(),
				DesignationChartEnum.setAdaptiveYMin.getPieChartValue(),
				DesignationChartEnum.labelFontColor.getPieChartValue(),
				DesignationChartEnum.formatNumber.getPieChartValue(),
				DesignationChartEnum.yFormatNumber.getPieChartValue(),
				DesignationChartEnum.xFormatNumber.getPieChartValue(),
				DesignationChartEnum.formatNumberScale.getPieChartValue(),
				DesignationChartEnum.showYAxisvalue.getPieChartValue(),
				DesignationChartEnum.placeValuesInside.getPieChartValue());

		for (EmployeeReportDTO emptDt : empList) {
			com.csipl.hrms.dto.payroll.dashboard.Data data = new com.csipl.hrms.dto.payroll.dashboard.Data(
					emptDt.getEmpDesignation(), emptDt.getEmpCtc(), "#575bde");
			arrayData.add(data);
		}

		com.csipl.hrms.dto.payroll.dashboard.GraphDtoNew gt = new com.csipl.hrms.dto.payroll.dashboard.GraphDtoNew(
				chart, arrayData);
		// arrayData.clear();
		return gt;

	}

	/**
	 * 
	 * @param processMonth
	 * @param companyId
	 * @param req
	 * @return DesignationWiseCTCInfo
	 * @throws ErrorHandling
	 * @throws PayRollProcessException
	 */

	@RequestMapping(path = "/designationWiseCTCInfoWithMonth/{companyId}/{processMonth}", method = RequestMethod.GET)
	public @ResponseBody com.csipl.hrms.dto.payroll.dashboard.GraphDtoNew DesignationWiseCTCInfo(
			@PathVariable("processMonth") Long processMonth, @PathVariable("companyId") Long companyId,
			HttpServletRequest req) throws ErrorHandling, PayRollProcessException {
		log.info("EmpDashboardController.DesignationWiseCTCInfo()");
		List<EmployeeReportDTO> empList = employeeReportService.fetchDesignationWiseCTC(companyId, processMonth);
		ArrayList<com.csipl.hrms.dto.payroll.dashboard.Data> arrayData = new ArrayList<com.csipl.hrms.dto.payroll.dashboard.Data>();
		com.csipl.hrms.dto.payroll.dashboard.ChartNew chart = new ChartNew(
				DesignationChartEnum.theme.getPieChartValue(), DesignationChartEnum.baseFontColor.getPieChartValue(),
				DesignationChartEnum.showBorder.getPieChartValue(), DesignationChartEnum.bgColor.getPieChartValue(),
				DesignationChartEnum.canvasBgColor.getPieChartValue(),
				DesignationChartEnum.showCanvasBorder.getPieChartValue(),
				DesignationChartEnum.showCanvasBorder.getPieChartValue(),
				DesignationChartEnum.baseFontSize.getPieChartValue(),
				DesignationChartEnum.showValues.getPieChartValue(),
				DesignationChartEnum.setAdaptiveYMin.getPieChartValue(),
				DesignationChartEnum.labelFontColor.getPieChartValue(),
				DesignationChartEnum.formatNumber.getPieChartValue(),
				DesignationChartEnum.yFormatNumber.getPieChartValue(),
				DesignationChartEnum.xFormatNumber.getPieChartValue(),
				DesignationChartEnum.formatNumberScale.getPieChartValue(),
				DesignationChartEnum.showYAxisvalue.getPieChartValue(),
				DesignationChartEnum.placeValuesInside.getPieChartValue());

		for (EmployeeReportDTO emptDt : empList) {
			com.csipl.hrms.dto.payroll.dashboard.Data data = new com.csipl.hrms.dto.payroll.dashboard.Data(
					emptDt.getEmpDesignation(), emptDt.getEmpCtc(), emptDt.getColor());
			arrayData.add(data);
		}
		com.csipl.hrms.dto.payroll.dashboard.GraphDtoNew gt = new com.csipl.hrms.dto.payroll.dashboard.GraphDtoNew(
				chart, arrayData);

		return gt;

	}

	/**
	 * 
	 * @param companyId
	 * @param req
	 * @return EmpPfContribution EmpPfContribution
	 * @throws ErrorHandling
	 * @throws PayRollProcessException
	 * 
	 */
	@RequestMapping(path = "/empPFContributionInfo/{companyId}/{processMonth}", method = RequestMethod.GET)
	public @ResponseBody com.csipl.hrms.dto.esipf.dashboard.GraphDto EmpPfContribution(
			@PathVariable("companyId") Long companyId, @PathVariable("processMonth") Long processMonth,
			HttpServletRequest req) throws ErrorHandling, PayRollProcessException {
		log.info("EmpDashboardController.EmpPfContribution()");
		List<EmployeeReportDTO> empList = employeeReportService.fetchEmpPfContribution(companyId, processMonth);
		ArrayList<com.csipl.hrms.dto.esipf.dashboard.Data> arrayData = new ArrayList<com.csipl.hrms.dto.esipf.dashboard.Data>();

		PfChart chart = new PfChart(PFChartEnum.bgColor.getPieChartValue(),
				PFChartEnum.startingAngle.getPieChartValue(), PFChartEnum.showLegend.getPieChartValue(),
				PFChartEnum.centerLabelBold.getPieChartValue(), PFChartEnum.showTooltip.getPieChartValue(),
				PFChartEnum.decimals.getPieChartValue(), PFChartEnum.theme.getPieChartValue(),
				PFChartEnum.pieRadius.getPieChartValue(), PFChartEnum.labelDistance.getPieChartValue(),
				PFChartEnum.showPercentValues.getPieChartValue(), PFChartEnum.showPercentInToolTip.getPieChartValue(),
				PFChartEnum.formatNumber.getPieChartValue(), PFChartEnum.formatNumberScale.getPieChartValue(),
				PFChartEnum.forceNumberScale.getPieChartValue(), PFChartEnum.numberScaleUnit.getPieChartValue(),
				PFChartEnum.smartLineColor.getPieChartValue(), PFChartEnum.labelFontColor.getPieChartValue());

		for (EmployeeReportDTO empDt : empList) {
			if (empDt.getAmtEmployee() != null) {
				com.csipl.hrms.dto.esipf.dashboard.Data data = new com.csipl.hrms.dto.esipf.dashboard.Data("Employee",
						empDt.getAmtEmployee(), PFChartEnum.employeeColor.getPieChartValue());
				arrayData.add(data);
			}
			if (empDt.getAmtEmployer() != null) {
				com.csipl.hrms.dto.esipf.dashboard.Data data1 = new com.csipl.hrms.dto.esipf.dashboard.Data("Employer",
						empDt.getAmtEmployer(), PFChartEnum.employeerColor.getPieChartValue());
				arrayData.add(data1);
			}
			if (empDt.getAmtPension() != null) {
				com.csipl.hrms.dto.esipf.dashboard.Data data2 = new com.csipl.hrms.dto.esipf.dashboard.Data("Pension",
						empDt.getAmtPension(), PFChartEnum.pensionColor.getPieChartValue());
				arrayData.add(data2);

			}

		}

		com.csipl.hrms.dto.esipf.dashboard.GraphDto gt = new com.csipl.hrms.dto.esipf.dashboard.GraphDto(chart,
				arrayData);
		return gt;

	}

	/**
	 * 
	 * EmpESIContributionWithMonth
	 */
	@RequestMapping(path = "/empESIContributionInfoWithMonth/{companyId}/{processMonth}", method = RequestMethod.GET)
	public @ResponseBody com.csipl.hrms.dto.esipf.dashboard.GraphDtoNew EmpESIContributionWithMonth(
			@PathVariable("companyId") Long companyId, @PathVariable("processMonth") Long processMonth,
			HttpServletRequest req) throws ErrorHandling, PayRollProcessException {
		log.info("EmpDashboardController.EmpESIContributionWithMonth()");
		List<EmployeeReportDTO> empList = employeeReportService.fetchEmpESIContribution(companyId, processMonth);
		ArrayList<com.csipl.hrms.dto.esipf.dashboard.Data> arrayData = new ArrayList<com.csipl.hrms.dto.esipf.dashboard.Data>();

		EsicChart chart = new EsicChart(EsicChartEnumNew.bgColor.getPieChartValue(),
				EsicChartEnumNew.startingAngle.getPieChartValue(), EsicChartEnumNew.showLegend.getPieChartValue(),
				EsicChartEnumNew.centerLabelBold.getPieChartValue(), EsicChartEnumNew.showTooltip.getPieChartValue(),
				EsicChartEnumNew.decimals.getPieChartValue(), EsicChartEnumNew.theme.getPieChartValue(),
				EsicChartEnumNew.pieRadius.getPieChartValue(), EsicChartEnumNew.labelDistance.getPieChartValue(),
				EsicChartEnumNew.showPercentValues.getPieChartValue(),
				EsicChartEnumNew.showPercentInToolTip.getPieChartValue(),
				EsicChartEnumNew.formatNumber.getPieChartValue(), EsicChartEnumNew.formatNumberScale.getPieChartValue(),
				EsicChartEnumNew.forceNumberScale.getPieChartValue(),
				EsicChartEnumNew.numberScaleUnit.getPieChartValue(), EsicChartEnumNew.smartLineColor.getPieChartValue(),
				EsicChartEnumNew.labelFontColor.getPieChartValue());

		for (EmployeeReportDTO empDt : empList) {
			log.info("EmpDashboardController.EmpESIContributionWithMonth()");

			com.csipl.hrms.dto.esipf.dashboard.Data data = new com.csipl.hrms.dto.esipf.dashboard.Data("Employee",
					empDt.getAmtEmployee(), EsicChartEnumNew.employeeColor.getPieChartValue());
			arrayData.add(data);
			com.csipl.hrms.dto.esipf.dashboard.Data data1 = new com.csipl.hrms.dto.esipf.dashboard.Data("Employer",
					empDt.getAmtEmployer(), EsicChartEnumNew.employeerColor.getPieChartValue());
			arrayData.add(data1);
		}

		com.csipl.hrms.dto.esipf.dashboard.GraphDtoNew gt = new com.csipl.hrms.dto.esipf.dashboard.GraphDtoNew(chart,
				arrayData);
		log.info("EmpDashboardController.EmpESIContributionWithMonth()" + arrayData);
		return gt;
	}

	/*
	 * // @RequestMapping(path = "/empPFContributionInfoWithMonth", method =
	 * RequestMethod.GET) // public @ResponseBody
	 * com.csipl.hrms.dto.esipf.dashboard.GraphDto EmpPfContributionWithMonth(
	 * // @RequestParam("processMonth") String
	 * processMonth, @RequestParam("companyId") String companyId, //
	 * HttpServletRequest req) throws ErrorHandling, PayRollProcessException { //
	 * log.info("EmpDashboardController.EmpPfContributionWithMonth()"); // Long
	 * p_process_month = Long.parseLong(processMonth); // Long longcompanyId =
	 * Long.parseLong(companyId); // List<EmployeeReportDTO> empList =
	 * employeeReportService.fetchEmpPfContribution(longcompanyId, p_process_month);
	 * // ArrayList<com.csipl.hrms.dto.esipf.dashboard.Data> arrayData = new
	 * ArrayList<com.csipl.hrms.dto.esipf.dashboard.Data>(); //
	 * com.csipl.hrms.dto.esipf.dashboard.Chart chart = new
	 * com.csipl.hrms.dto.esipf.dashboard.Chart( //
	 * GraphEnum.numberPrefix.getPieChartValue(),
	 * GraphEnum.formatnumberscale.getPieChartValue(), //
	 * GraphEnum.decimals.getPieChartValue(),
	 * GraphEnum.skipOverlapLabels.getPieChartValue(), //
	 * GraphEnum.theme.getPieChartValue()); // for (EmployeeReportDTO empDt :
	 * empList) { // // com.csipl.hrms.dto.esipf.dashboard.Data data = new
	 * com.csipl.hrms.dto.esipf.dashboard.Data("Employee", //
	 * empDt.getAmtEmployee()); // arrayData.add(data); // //
	 * com.csipl.hrms.dto.esipf.dashboard.Data data1 = new
	 * com.csipl.hrms.dto.esipf.dashboard.Data("Employer", //
	 * empDt.getAmtEmployer()); // arrayData.add(data1); // //
	 * com.csipl.hrms.dto.esipf.dashboard.Data data2 = new
	 * com.csipl.hrms.dto.esipf.dashboard.Data("Pension", // empDt.getAmtPension());
	 * // arrayData.add(data2); // // } // //
	 * com.csipl.hrms.dto.esipf.dashboard.GraphDto gt = new
	 * com.csipl.hrms.dto.esipf.dashboard.GraphDto(chart, // arrayData); // //
	 * return gt; // // }
	 * 
	 * // @RequestMapping(path = "/empESIContributionInfo", method =
	 * RequestMethod.GET) // public @ResponseBody
	 * com.csipl.hrms.dto.esipf.dashboard.GraphDto EmpESIContribution(
	 * // @RequestParam("companyId") String companyId, HttpServletRequest req) //
	 * throws ErrorHandling, PayRollProcessException { //
	 * log.info("EmpDashboardController.EmpESIContribution()"); // Long
	 * longcompanyId = Long.parseLong(companyId); // List<EmployeeReportDTO> empList
	 * = employeeReportService.fetchEmpESIContribution(longcompanyId, 0L); //
	 * ArrayList<com.csipl.hrms.dto.esipf.dashboard.Data> arrayData = new
	 * ArrayList<com.csipl.hrms.dto.esipf.dashboard.Data>(); //
	 * com.csipl.hrms.dto.esipf.dashboard.Chart chart = new
	 * com.csipl.hrms.dto.esipf.dashboard.Chart( //
	 * GraphEnum.numberPrefix.getPieChartValue(),
	 * GraphEnum.formatnumberscale.getPieChartValue(), //
	 * GraphEnum.decimals.getPieChartValue(),
	 * GraphEnum.skipOverlapLabels.getPieChartValue(), //
	 * GraphEnum.theme.pieChartValue); // for (EmployeeReportDTO empDt : empList) {
	 * // // com.csipl.hrms.dto.esipf.dashboard.Data data = new
	 * com.csipl.hrms.dto.esipf.dashboard.Data("Employee", //
	 * empDt.getAmtEmployee()); // arrayData.add(data); //
	 * com.csipl.hrms.dto.esipf.dashboard.Data data1 = new
	 * com.csipl.hrms.dto.esipf.dashboard.Data("Employer", //
	 * empDt.getAmtEmployer()); // arrayData.add(data1); // // } // //
	 * com.csipl.hrms.dto.esipf.dashboard.GraphDto gt = new
	 * com.csipl.hrms.dto.esipf.dashboard.GraphDto(chart, // arrayData); // //
	 * return gt; // // }
	 */ // Bank Percentage headcount_by_bankpay

	@RequestMapping(path = "/empPayrollStatus", method = RequestMethod.GET)
	public @ResponseBody List<EmployeeReportDTO> empPayrollstatusbyMonth(@RequestParam("companyId") String companyId,
			HttpServletRequest req) throws ErrorHandling, PayRollProcessException {
		log.info("EmpDashboardController.empPayrollstatusbyMonth()");
		Long longcompanyId = Long.parseLong(companyId);
		List<EmployeeReportDTO> empList = employeeReportService.empPayrollstatusbyMonth(longcompanyId);
		if (empList != null)
			return empList;
		else
			throw new ErrorHandling("Data Not Found");

	}

	// Bank Percentage headcount_by_bankpay

	@RequestMapping(path = "/headCountByBankPayInfo", method = RequestMethod.GET)
	public @ResponseBody GraphDto HeadCountByBankPay(@RequestParam("companyId") String companyId,
			HttpServletRequest req) throws ErrorHandling, PayRollProcessException {
		log.info("EmpDashboardController.HeadCountByBankPay()");
		Long longcompanyId = Long.parseLong(companyId);

		List<EmployeeReportDTO> empList = employeeReportService.fetchHeadCountByBankPay(longcompanyId);

		ArrayList<Data> arrayData = new ArrayList<Data>();
		Chart chart = new Chart(GraphEnum.numberPrefix.getPieChartValue(), GraphEnum.decimals.getPieChartValue(),
				GraphEnum.skipOverlapLabels.getPieChartValue(), GraphEnum.theme.pieChartValue);
		for (EmployeeReportDTO empDt : empList) {
			Data data = new Data(empDt.getEmpBankName(), empDt.getBankPer());
			arrayData.add(data);
		}

		GraphDto gt = new GraphDto(chart, arrayData);

		return gt;
	}

	@RequestMapping(path = "/departmentWiseRatio", method = RequestMethod.GET)
	public @ResponseBody GraphDto departmentWiseRatio(@RequestParam("companyId") String companyId,
			HttpServletRequest req) throws ErrorHandling, PayRollProcessException {
		log.info("EmpDashboardController.departmentWiseRatio()");
		Long longcompanyId = Long.parseLong(companyId);
		List<EmployeeReportDTO> empList = employeeReportService.departmentWiseRatio(longcompanyId);
		ArrayList<Data> arrayData = new ArrayList<Data>();

		DepartmentChart chart = new DepartmentChart(GraphEnum.numberPrefix.getPieChartValue(),
				GraphEnum.theme.getPieChartValue(), GraphEnum.startingAngle.getPieChartValue(),
				GraphEnum.showlabels.getPieChartValue(), GraphEnum.showlegend.getPieChartValue(),
				GraphEnum.enablemultislicing.getPieChartValue(), GraphEnum.slicingdistance.getPieChartValue(),
				GraphEnum.showpercentvalues.getPieChartValue(), GraphEnum.showpercentintooltip.getPieChartValue());

		for (EmployeeReportDTO empDt : empList) {
			Data data = new Data(empDt.getEmpDetp(), empDt.getDeptCount());
			arrayData.add(data);

		}

		GraphDto gt = new GraphDto(chart, arrayData);

		return gt;

	}

	@RequestMapping(path = "/lastSixMonthCTCInfo", method = RequestMethod.GET)
	public @ResponseBody com.csipl.hrms.dto.payroll.dashboard.GraphDto LastSixMonthCTCInfo(
			@RequestParam("companyId") String companyId, HttpServletRequest req)
			throws ErrorHandling, PayRollProcessException {
		log.info("EmpDashboardController.LastSixMonthCTCInfo()");
		Long longcompanyId = Long.parseLong(companyId);
		List<DepartmentReportDTO> deptReportDto = departmentReportService.LastSixMonthCTC(longcompanyId, 0L);
		ArrayList<com.csipl.hrms.dto.payroll.dashboard.Data> arrayData = new ArrayList<com.csipl.hrms.dto.payroll.dashboard.Data>();

		com.csipl.hrms.dto.payroll.dashboard.Chart chart = new com.csipl.hrms.dto.payroll.dashboard.Chart(
				GraphEnum.caption.getPieChartValue(), GraphEnum.numberPrefix.getPieChartValue(),
				GraphEnum.formatnumberscale.getPieChartValue(), GraphEnum.showBorder.pieChartValue,
				GraphEnum.use3DLighting.pieChartValue, GraphEnum.enableSmartLabels.pieChartValue,
				GraphEnum.startingAngle.pieChartValue, GraphEnum.showLabels.pieChartValue,
				GraphEnum.showPercentValues.pieChartValue, GraphEnum.showLegend.pieChartValue,
				GraphEnum.thousandSeparatorPosition.pieChartValue, GraphEnum.baseFont.pieChartValue,
				GraphEnum.baseFontSize.pieChartValue, GraphEnum.baseFontColor.pieChartValue,
				GraphEnum.centerLabelBold.pieChartValue, GraphEnum.showTooltip.pieChartValue,
				GraphEnum.decimals.pieChartValue, GraphEnum.theme.pieChartValue

		);

		for (DepartmentReportDTO deptDt : deptReportDto) {
			// String str[]= deptDt.getLastMonth().split("-");
			com.csipl.hrms.dto.payroll.dashboard.Data data = new com.csipl.hrms.dto.payroll.dashboard.Data(
					deptDt.getLastMonth(), deptDt.getDeptCTC());
			arrayData.add(data);
		}

		com.csipl.hrms.dto.payroll.dashboard.GraphDto gt = new com.csipl.hrms.dto.payroll.dashboard.GraphDto(chart,
				arrayData);

		return gt;
	}

	@RequestMapping(path = "/lastSixMonthCTCInfowithMonth", method = RequestMethod.GET)
	public @ResponseBody com.csipl.hrms.dto.payroll.dashboard.GraphDto LastSixMonthCTCInfoWithMonth(
			@RequestParam("processMonth") String processMonth, @RequestParam("companyId") String companyId,
			HttpServletRequest req) throws ErrorHandling, PayRollProcessException {
		log.info("EmpDashboardController.LastSixMonthCTCInfoWithMonth()");
		Long p_process_month = Long.parseLong(processMonth);
		Long longcompanyId = Long.parseLong(companyId);
		// System.out.println("====month ====" + p_process_month);
		List<DepartmentReportDTO> deptReportDto = departmentReportService.LastSixMonthCTC(longcompanyId,
				p_process_month);
		ArrayList<com.csipl.hrms.dto.payroll.dashboard.Data> arrayData = new ArrayList<com.csipl.hrms.dto.payroll.dashboard.Data>();

		com.csipl.hrms.dto.payroll.dashboard.Chart chart = new com.csipl.hrms.dto.payroll.dashboard.Chart(
				GraphEnum.caption.getPieChartValue(), GraphEnum.numberPrefix.getPieChartValue(),
				GraphEnum.formatnumberscale.getPieChartValue(), GraphEnum.showBorder.pieChartValue,
				GraphEnum.use3DLighting.pieChartValue, GraphEnum.enableSmartLabels.pieChartValue,
				GraphEnum.startingAngle.pieChartValue, GraphEnum.showLabels.pieChartValue,
				GraphEnum.showPercentValues.pieChartValue, GraphEnum.showLegend.pieChartValue,
				GraphEnum.thousandSeparatorPosition.pieChartValue, GraphEnum.baseFont.pieChartValue,
				GraphEnum.baseFontSize.pieChartValue, GraphEnum.baseFontColor.pieChartValue,
				GraphEnum.centerLabelBold.pieChartValue, GraphEnum.showTooltip.pieChartValue,
				GraphEnum.decimals.pieChartValue, GraphEnum.theme.pieChartValue

		);

		for (DepartmentReportDTO deptDt : deptReportDto) {
			// String str[]= deptDt.getLastMonth().split("-");
			com.csipl.hrms.dto.payroll.dashboard.Data data = new com.csipl.hrms.dto.payroll.dashboard.Data(
					deptDt.getLastMonth(), deptDt.getDeptCTC());
			arrayData.add(data);
		}

		com.csipl.hrms.dto.payroll.dashboard.GraphDto gt = new com.csipl.hrms.dto.payroll.dashboard.GraphDto(chart,
				arrayData);

		return gt;
	}

	@RequestMapping(path = "/empAttrition", method = RequestMethod.GET)
	public @ResponseBody AttritionDto EmpAttritionJoinedResigned(@RequestParam("companyId") String companyId,
			HttpServletRequest req) throws ErrorHandling, PayRollProcessException {
		log.info("EmpDashboardController.EmpAttritionJoinedResigned()");
		Long longcompanyId = Long.parseLong(companyId);
		Date date = new Date();
		FinancialYear fyDto = financialYearService.findCurrentFinancialYear(date, longcompanyId);
		AttritionDto dto = employeeAttritionService.empAttritionDetail(longcompanyId);
		dto.setAttritionYear(fyDto.getFinancialYear());
		return dto;
	}

	@RequestMapping(path = "/empCompanyAnnouncement", method = RequestMethod.GET)
	public @ResponseBody List<EmployeeReportDTO> empCompanyAnnouncementApp(@RequestParam("companyId") String companyId)
			throws ErrorHandling, PayRollProcessException {
		Long companyIdValue = Long.parseLong(companyId);
		List<EmployeeReportDTO> empList = employeeReportService.empCompanyAnnouncement(companyIdValue);

		if (empList != null)
			return empList;
		else
			throw new ErrorHandling("Data Not Found");

	}

	@RequestMapping(path = "/empTicketStatus", method = RequestMethod.GET)
	public @ResponseBody EmployeeReportDTO empTicketStatusApp(@RequestParam("companyId") String companyId,
			@RequestParam("userId") String userId, @RequestParam("currentRole") String currentRole)
			throws ErrorHandling, PayRollProcessException {
		System.out.println(
				"=====companyId========" + companyId + "===userId===" + userId + "====currentRole===" + currentRole);

		Long companyIdValue = Long.parseLong(companyId);
		Long userIdValue = Long.parseLong(userId);
		EmployeeReportDTO empList = employeeReportService.empTicketStatus(companyIdValue, userIdValue, currentRole);

		if (empList != null)
			return empList;
		else
			throw new ErrorHandling("Data Not Found");

	}

	@RequestMapping(path = "/empTicketStatusWithMonth", method = RequestMethod.GET)
	public @ResponseBody EmployeeReportDTO empTicketStatuswithMonth(@RequestParam("lastMonth") String lastMonth,
			@RequestParam("companyId") String companyId, HttpServletRequest req)
			throws ErrorHandling, PayRollProcessException {
		Long last_Month = Long.parseLong(lastMonth);
		Long companyIdValue = Long.parseLong(companyId);
		EmployeeReportDTO empList = employeeReportService.empTicketStatuswithMonth(companyIdValue, last_Month);

		if (empList != null)

			return empList;
		else

			throw new ErrorHandling("Data Not Found");

	}

	/**
	 * 
	 * getUpcomingBDayList
	 */
	@RequestMapping(value = "/empBirthdayForToday/{companyId}", method = RequestMethod.GET)
	public @ResponseBody List<EmployeeReportDTO> getUpcomingBDayList(@PathVariable Long companyId,
			HttpServletRequest req) throws ErrorHandling, PayRollProcessException {
		log.info("EmpDashboardController.getUpcomingBDayList()");
		List<Object[]> list = employeeReportService.getUpcomingBDayList(companyId);
		if (list.size() > 0)
			return employeePersonalInformationAdaptor.databaseModelToUiDtoEmpBdayList(list);
		else
			throw new ErrorHandling("Birthday Data Not Found");

	}

	/**
	 * 
	 * getAnniversaryList
	 */
	@RequestMapping(value = "/empAnniversaryForToday/{companyId}", method = RequestMethod.GET)
	public @ResponseBody List<EmployeeReportDTO> getAnniversaryList(@PathVariable Long companyId,
			HttpServletRequest req) throws ErrorHandling, PayRollProcessException {
		log.info("EmpDashboardController.getAnniversaryList()");
		List<Object[]> list = employeeReportService.getAnniversaryList(companyId);
		if (list.size() > 0)
			return employeePersonalInformationAdaptor.databaseModelToUiDtoEmpAnniversaryList(list);
		else
			throw new ErrorHandling("Anniversary Data Not Found");

	}

	@RequestMapping(value = "/empWorkAnniversaryForToday/{companyId}", method = RequestMethod.GET)
	public @ResponseBody List<EmployeeReportDTO> getWorkAnniversaryList(@PathVariable Long companyId,
			HttpServletRequest req) throws ErrorHandling, PayRollProcessException {
		log.info("EmpDashboardController.getWorkAnniversaryList()");
		List<Object[]> list = employeeReportService.getWorkAnniversaryList(companyId);
		if (list.size() > 0)
			return employeePersonalInformationAdaptor.databaseModelToUiDtoEmpWorkAnniversaryList(list);
		else
			throw new ErrorHandling("Anniversary Data Not Found");
	}

	/**
	 * 
	 * @param companyId
	 * @param employeeId
	 * @param req
	 * @return
	 * @throws ErrorHandling
	 * @throws PayRollProcessException
	 */
	@RequestMapping(value = "/getMyTeamToday/{companyId}/{employeeId}", method = RequestMethod.GET)
	public @ResponseBody List<EmployeeReportDTO> getMyTeamToday(@PathVariable Long companyId,
			@PathVariable Long employeeId, HttpServletRequest req) throws ErrorHandling, PayRollProcessException {
		log.info("EmpDashboardController.getMyTeamToday()");
		List<EmployeeReportDTO> list = employeeReportService.getMyTeamToday(LeaveMyTeamEnum.Today.getLeaveFlagEnum(),
				companyId, employeeId);
		// if (list.size() > 0)
		return list;
//		else
//			throw new ErrorHandling("MyTeamToday Data Not Found");
	}

	/**
	 * 
	 * @param companyId
	 * @param employeeId
	 * @param req
	 * @return
	 * @throws ErrorHandling
	 * @throws PayRollProcessException
	 */
	@RequestMapping(value = "/getMyTeamMonth/{companyId}/{employeeId}", method = RequestMethod.GET)
	public @ResponseBody List<EmployeeReportDTO> getMyTeamMonth(@PathVariable Long companyId,
			@PathVariable Long employeeId, HttpServletRequest req) throws ErrorHandling, PayRollProcessException {
		log.info("EmpDashboardController.getMyTeamMonth()");
		List<EmployeeReportDTO> list = employeeReportService.getMyTeamToday(LeaveMyTeamEnum.Month.getLeaveFlagEnum(),
				companyId, employeeId);
//		if (list.size() > 0)
		return list;
//		else
//			throw new ErrorHandling("MyTeam Month Data Not Found");
	}

	@RequestMapping(value = "/allEmpLeaveToday/{companyId}", method = RequestMethod.GET)
	public @ResponseBody List<EmployeeReportDTO> allEmpLeaveToday(@PathVariable Long companyId, HttpServletRequest req)
			throws ErrorHandling, PayRollProcessException {
		List<EmployeeReportDTO> list = employeeReportService
				.allEmployeeLeaveToday(LeaveMyTeamEnum.Today.getLeaveFlagEnum(), companyId);
		return list;
	}

	@RequestMapping(value = "/allEmpLeaveMonth/{companyId}", method = RequestMethod.GET)
	public @ResponseBody List<EmployeeReportDTO> allEmpLeaveMonth(@PathVariable Long companyId, HttpServletRequest req)
			throws ErrorHandling, PayRollProcessException {
		List<EmployeeReportDTO> list = employeeReportService
				.allEmployeeLeaveToday(LeaveMyTeamEnum.Month.getLeaveFlagEnum(), companyId);
		return list;
	}

	/**
	 * 
	 * @param companyId
	 * @param employeeId
	 * @param req        getMyTeamMonthCount
	 * @return
	 * @throws ErrorHandling
	 * @throws PayRollProcessException
	 */
	@RequestMapping(value = "/getMyTeamMonthCount/{companyId}/{employeeId}", method = RequestMethod.GET)
	public @ResponseBody List<EmployeeReportDTO> getMyTeamMonthCount(@PathVariable Long companyId,
			@PathVariable Long employeeId, HttpServletRequest req) throws ErrorHandling, PayRollProcessException {
		log.info("EmpDashboardController.getMyTeamMonthCount()");
		List<EmployeeReportDTO> list = employeeReportService.getMyTeamMonthCount("", companyId, employeeId);
//		if (list.size() > 0)
		return list;
//		else
//			throw new ErrorHandling("getMyTeamMonthCount Data Not Found");
	}

	/**
	 * 
	 * @param companyId
	 * @param employeeId
	 * @param req        getCountNotification
	 * @return
	 * @throws ErrorHandling
	 * @throws PayRollProcessException
	 */
	@RequestMapping(value = "/getCountNotification/{companyId}/{employeeId}", method = RequestMethod.GET)
	public @ResponseBody List<EmployeeReportDTO> getCountNotification(@PathVariable Long companyId,
			@PathVariable Long employeeId, HttpServletRequest req) throws ErrorHandling, PayRollProcessException {
		log.info("EmpDashboardController.getCountNotification() companyId--  " + companyId + " emp ID--" + employeeId);
		List<EmployeeReportDTO> list = employeeReportService.getCountNotificationLeave(companyId, employeeId);
		if (list.size() > 0)
			return list;
		else
			throw new ErrorHandling("getCountNotification Data Not Found");
	}

	/**
	 * 
	 * @param companyId
	 * @param employeeId
	 * @param req
	 * @return
	 * @throws ErrorHandling
	 * @throws PayRollProcessException
	 */
	@RequestMapping(value = "/getCountNotificationLeave/{companyId}/{employeeId}", method = RequestMethod.GET)
	public @ResponseBody List<EmployeeReportDTO> getCountNotificationLeave(@PathVariable Long companyId,
			@PathVariable Long employeeId, HttpServletRequest req) throws ErrorHandling, PayRollProcessException {
		log.info("EmpDashboardController.getCountNotificationLeave() companyId--  " + companyId + " emp ID--"
				+ employeeId);
		List<EmployeeReportDTO> list = employeeReportService.getCountNotificationLeaveBy(companyId, employeeId);
		if (list.size() > 0)
			return list;
		else
			throw new ErrorHandling("getCountNotificationLeave Data Not Found");
	}

	/**
	 * 
	 * @param companyId
	 * @param employeeId
	 * @param req
	 * @return
	 * @throws ErrorHandling
	 * @throws PayRollProcessException
	 */
	@RequestMapping(value = "/getCountNotificationArRequest/{companyId}/{employeeId}", method = RequestMethod.GET)
	public @ResponseBody List<EmployeeReportDTO> getCountNotificationArRequest(@PathVariable Long companyId,
			@PathVariable Long employeeId, HttpServletRequest req) throws ErrorHandling, PayRollProcessException {
		log.info("EmpDashboardController.getCountNotificationArRequest() companyId--  " + companyId + " emp ID--"
				+ employeeId);
		List<EmployeeReportDTO> list = employeeReportService.getCountNotificationArRequest(companyId, employeeId);
		if (list.size() > 0)
			return list;
		else
			throw new ErrorHandling("getCountNotificationArRequest Data Not Found");
	}

	/**
	 * 
	 * @param companyId
	 * @param employeeId
	 * @param req
	 * @return
	 * @throws ErrorHandling
	 * @throws PayRollProcessException
	 */
	@RequestMapping(value = "/getCountNotificationCompOff/{companyId}/{employeeId}", method = RequestMethod.GET)
	public @ResponseBody List<EmployeeReportDTO> getCountNotificationCompOff(@PathVariable Long companyId,
			@PathVariable Long employeeId, HttpServletRequest req) throws ErrorHandling, PayRollProcessException {
		log.info("EmpDashboardController.getCountNotificationCompOff() companyId--  " + companyId + " emp ID--"
				+ employeeId);
		List<EmployeeReportDTO> list = employeeReportService.getCountNotificationCompOff(companyId, employeeId);
		if (list.size() > 0)
			return list;
		else
			throw new ErrorHandling("getCountNotificationCompOff Data Not Found");
	}

	/**
	 * 
	 * @param companyId
	 * @param employeeId
	 * @param req
	 * @return
	 * @throws ErrorHandling
	 * @throws PayRollProcessException
	 */
	@RequestMapping(value = "/getCountNotificationSepration/{companyId}/{employeeId}", method = RequestMethod.GET)
	public @ResponseBody List<EmployeeReportDTO> getCountNotificationSepration(@PathVariable Long companyId,
			@PathVariable Long employeeId, HttpServletRequest req) throws ErrorHandling, PayRollProcessException {
		log.info("EmpDashboardController.getCountNotificationSepration() companyId--  " + companyId + " emp ID--"
				+ employeeId);
		List<EmployeeReportDTO> list = employeeReportService.getCountNotificationSepration(companyId, employeeId);
		if (list.size() > 0)
			return list;
		else
			throw new ErrorHandling("getCountNotificationSepration Data Not Found");
	}

	/**
	 * 
	 * @param companyId
	 * @param employeeId
	 * @param req
	 * @return
	 * @throws ErrorHandling
	 * @throws PayRollProcessException
	 */
	@RequestMapping(value = "/getCountNotificationHelp/{companyId}/{employeeId}", method = RequestMethod.GET)
	public @ResponseBody List<EmployeeReportDTO> getCountNotificationHelp(@PathVariable Long companyId,
			@PathVariable Long employeeId, HttpServletRequest req) throws ErrorHandling, PayRollProcessException {
		log.info("EmpDashboardController.getCountNotificationHelp() companyId--  " + companyId + " emp ID--"
				+ employeeId);
		List<EmployeeReportDTO> list = employeeReportService.getCountNotificationHelp(companyId, employeeId);
		if (list.size() > 0)
			return list;
		else
			throw new ErrorHandling("getCountNotificationHelp Data Not Found");
	}

	/**
	 * 
	 * @param companyId
	 * @param req
	 * @return
	 * @throws ErrorHandling
	 * @throws PayRollProcessException
	 */
	@RequestMapping(value = "/getCountLeave/{companyId}", method = RequestMethod.GET)
	public @ResponseBody List<EmployeeReportDTO> getCountLeave(@PathVariable Long companyId, HttpServletRequest req)
			throws ErrorHandling, PayRollProcessException {
		log.info("EmpDashboardController.getCountNotificationHelp() companyId--  " + companyId);
		List<EmployeeReportDTO> list = employeeReportService.getCountLeave(companyId);
		if (list.size() > 0)
			return list;
		else
			throw new ErrorHandling("Count  Leave Data Not Found");
	}

	// Employee Due Documents Report
	@RequestMapping(path = "/employeeDocumentConfirmationInfoReport/{companyId}", method = RequestMethod.GET)
	public @ResponseBody void EmployeeDueDocumentInfoReport(@PathVariable("companyId") Long companyId,
			HttpServletRequest request, HttpServletResponse response) throws ErrorHandling {

		String[] columns = { "Emp Code", "Employee", "Designation", "Department", "Aadhar", "UAN", "ESI",
				"Bank Account No", "Accidental Insurance", "Medical Insurance", "PAN" };

		List<EmployeeReportDTO> empDeuDcoumentList = employeeReportService.fetchEmployeeDocumentConfirmation(companyId);

		try {
			response.setContentType("application/application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
			response.setHeader("Content-Disposition", "attachment;filename=Due Documents List.xlsx");
			response.setHeader(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, HttpHeaders.CONTENT_DISPOSITION);

			if (companyId != null) {
				Workbook workbook = DashbordExcelWriter.empDueDcoumentReport(empDeuDcoumentList, columns);
				ServletOutputStream fileOut = response.getOutputStream();
				workbook.write(fileOut);
			} else
				throw new ErrorHandling("Invalid session .Please login again");
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
