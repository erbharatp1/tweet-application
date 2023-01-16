package com.csipl.hrms.report.controller;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.csipl.hrms.common.util.ErrorHandling;
import com.csipl.hrms.common.util.MiscellaneousExcelWriter;
import com.csipl.hrms.dto.employee.EmployeeAssetDTO;
import com.csipl.hrms.dto.employee.EmployeeRoleSummaryDTO;
import com.csipl.hrms.dto.employee.EmployeeTicketSummaryDTO;
import com.csipl.hrms.service.adaptor.MiscellaneousReportAdaptor;
import com.csipl.hrms.service.adaptor.MiscellaneousRoleSummaryReportAdaptor;
import com.csipl.hrms.service.adaptor.MiscellaneousTicketSummaryAdapter;
import com.csipl.hrms.service.report.MiscellaneousReportService;


@RequestMapping("/miscellaneousReport")
@RestController



public class MiscellaneousReportController {

	@Autowired
	MiscellaneousReportService miscellaneousReportService;
	
	MiscellaneousReportAdaptor miscellaneousReportAdaptor=new MiscellaneousReportAdaptor();
	
	MiscellaneousRoleSummaryReportAdaptor miscellaneousRoleSummarytAdaptor = new MiscellaneousRoleSummaryReportAdaptor();
	
	MiscellaneousTicketSummaryAdapter miscellaneousTicketSummaryAdapter = new MiscellaneousTicketSummaryAdapter();
	
	// AssetAllocationAndBalanceSummaryReport
	@RequestMapping(path = "/assetAllocation/{companyId}/{flag}", method = RequestMethod.GET)
	public void generateAssetAllocationAndBalanceSummaryReport(@PathVariable("companyId") Long companyId,
			@PathVariable("flag") Long flag, HttpServletRequest req, HttpServletResponse response)
			throws ErrorHandling, ParseException {

		String[] columns = { "Code", "Employee", "Designation", "Department", "Asset Type", "Description",
				"Allocated On", "Remarks" };

		List<Object[]> assetAllocationObj = miscellaneousReportService.findAssetAllocationAndRecoveryReport(companyId,
				flag);

		List<EmployeeAssetDTO> assetAllocationList = miscellaneousReportAdaptor
				.objectListToAssetAllocationReport(assetAllocationObj);
		try {
			response.setContentType("application/application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
			response.setHeader("Content-Disposition", "attachment;filename=assetAllocationReport.xlsx");
			response.setHeader(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, HttpHeaders.CONTENT_DISPOSITION);
			if (companyId != null) {
				Workbook workbook = MiscellaneousExcelWriter.assetAllocationReport(assetAllocationList, columns);
				ServletOutputStream fileOut = response.getOutputStream();
				workbook.write(fileOut);
			} else
				throw new ErrorHandling("Invalid session .Please login again");
		} catch (IOException e) {

			e.printStackTrace();
		}

	}
	
	
	/*
	 * Shubham yaduwanshi
	 */
	// current Role Summary Excel Report
	@RequestMapping(path = "/currentRoleSummaryReport/{companyId}/{flag}", method = RequestMethod.GET)
	public void currentRoleSummaryReport(@PathVariable("companyId") Long companyId, @PathVariable("flag") Long flag,
			HttpServletRequest req, HttpServletResponse response) throws ErrorHandling, ParseException {

		String[] columns = { "Code", "Employee", "Designation", "Department", "Role", "Employee Status" };

		List<Object[]> roleSummeryObjList = miscellaneousReportService.currentRoleSummaryReport(companyId, flag);

		List<EmployeeRoleSummaryDTO> currentRoleSummaryReportList = miscellaneousRoleSummarytAdaptor
				.objectListToRoleSummaryReport(roleSummeryObjList);

		try {
			response.setContentType("application/application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
			response.setHeader("Content-Disposition", "attachment;filename=Current Roles.xlsx");
			response.setHeader(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, HttpHeaders.CONTENT_DISPOSITION);
			if (companyId != null) {
				Workbook workbook = MiscellaneousExcelWriter.currentRoleSummaryReport(currentRoleSummaryReportList,
						columns);
				ServletOutputStream fileOut = response.getOutputStream();
				workbook.write(fileOut);

			} else
				throw new ErrorHandling("Invalid session .Please login again");
		} catch (IOException e) {

			e.printStackTrace();
		}

	}

	/*
	 * Shubham yaduwanshi
	 */
	// Ticket Summary Excel Report
	@RequestMapping(path = "/ticketsSummaryRepot", method = RequestMethod.GET)
	public void ticketsSummaryRepot(@RequestParam Long companyId, @RequestParam String fromDate,
			@RequestParam String toDate, HttpServletRequest req, HttpServletResponse response)
			throws ErrorHandling, ParseException, InvalidFormatException, IOException {

		String[] columns = { "Emp Code", "Employee", "Designation", "Department", "Ticket No", "Category", "Dated On",
				"Status", "Closed On", "Comments" };

		DateFormat inputFormat = new SimpleDateFormat("E MMM dd yyyy");
		Date startDate = inputFormat.parse(fromDate);
		Date endDate = inputFormat.parse(toDate);

		List<Object[]> ticketsSummaryObj = miscellaneousReportService.ticketsSummaryReport(companyId, startDate,
				endDate);

		List<EmployeeTicketSummaryDTO> tickrtSummaryReportList = miscellaneousTicketSummaryAdapter
				.objectListToTicketSummaryReport(ticketsSummaryObj);

		try {
			response.setHeader("Content-Disposition", "attachment;filename=Ticket Summary.xlsx");
			response.setHeader(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, HttpHeaders.CONTENT_DISPOSITION);
			if (companyId != null) {
				Workbook workbook = MiscellaneousExcelWriter.ticketSummaryReport(tickrtSummaryReportList, columns,
						startDate, endDate);
				ServletOutputStream fileOut = response.getOutputStream();
				workbook.write(fileOut);

			} else
				throw new ErrorHandling("Invalid session .Please login again");
		} catch (IOException e) {

			e.printStackTrace();
		}

	}
		
}
