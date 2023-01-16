package com.csipl.tms.report.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.csipl.hrms.common.enums.FrequentLeaveTakerEnum;
import com.csipl.hrms.common.enums.LeaveTakenByLeaveTypeEnum;
import com.csipl.hrms.common.enums.TimeAndAttendanceEnum;
import com.csipl.hrms.common.util.ErrorHandling;
import com.csipl.tms.dto.common.Categories;
import com.csipl.tms.dto.common.Category;
import com.csipl.tms.dto.common.Dataset;
import com.csipl.tms.dto.common.FrequentLeaveTakerChart;
import com.csipl.tms.dto.common.FrequentLeaveTakerGraphDto;
import com.csipl.tms.dto.common.LeaveTakenByLeaveTypeChart;
import com.csipl.tms.dto.common.LeaveTakenByLeaveTypeGraphDto;
import com.csipl.tms.dto.common.TimeLeaveAttendanceChart;
import com.csipl.tms.dto.common.TimeLeaveAttendanceData;
import com.csipl.tms.dto.common.TimeLeaveAttendanceGraphDto;
import com.csipl.tms.dto.monthattendance.EmployeeLeaveGraphDTO;
import com.csipl.tms.report.service.LeaveAttendanceReportService;

@RestController
@RequestMapping({"/timeAttendaceOverview"})
public class TimeAndAttendanceOverviewController {
	private static final Logger logger = LoggerFactory.getLogger(TimeAndAttendanceOverviewController.class);
	

	@Autowired
	LeaveAttendanceReportService leaveAttendanceReportService;
	
	
	@RequestMapping(value = { "/employeeOnLeave/{companyId}"}, method = {org.springframework.web.bind.annotation.RequestMethod.GET })
	public @ResponseBody TimeLeaveAttendanceGraphDto getEmployeeOnLeave(@PathVariable("companyId") Long companyId,
			HttpServletRequest req) throws ErrorHandling {
		logger.info("getEmployeeOnLeave compid is : " + companyId);
		List<EmployeeLeaveGraphDTO> employeeOnLeaveList= leaveAttendanceReportService.getEmployeeOnLeavePercentage(companyId);
		
		TimeLeaveAttendanceGraphDto timeLeaveAttendanceGraphDto = null;
 
		if (employeeOnLeaveList.size() > 0) {
			//Collections.sort(employeeOnLeaveList, new ObjectSorter());

			List<String> colorList = Arrays.asList("#ffbe3c", "#375baf", "#ff6060", "#1a84cc", "#845cee", "#2095f2",
					"#ff6060", "#a3a1fb", "#86b800", "#b9b9b9", "#f8b350", "#2095f2", "#888888");
			ArrayList<TimeLeaveAttendanceData> arrayData = new ArrayList<TimeLeaveAttendanceData>();
			ArrayList<Category> categoryList = new ArrayList<Category>();

			ArrayList<Dataset> datasetList = new ArrayList<Dataset>();

			Dataset dataset = new Dataset();
			Categories categories = new Categories();
			ArrayList<Categories> categoriesList = new ArrayList<Categories>();

			TimeLeaveAttendanceChart chart = new TimeLeaveAttendanceChart(
					TimeAndAttendanceEnum.theme.getPieChartValue(),
					TimeAndAttendanceEnum.showvalues.getPieChartValue(),
					TimeAndAttendanceEnum.numVisiblePlot.getPieChartValue(),
					TimeAndAttendanceEnum.scrollheight.getPieChartValue(),
					TimeAndAttendanceEnum.flatScrollBars.getPieChartValue(),
					TimeAndAttendanceEnum.scrollShowButtons.getPieChartValue(),
					TimeAndAttendanceEnum.scrollColor.getPieChartValue(),
					TimeAndAttendanceEnum.showHoverEffect.getPieChartValue(),
					TimeAndAttendanceEnum.numbersuffix.getPieChartValue(),
					TimeAndAttendanceEnum.showYAxisvalue.getPieChartValue(),
					TimeAndAttendanceEnum.paletteColors.getPieChartValue(),
					TimeAndAttendanceEnum.labelFontColor.getPieChartValue());
			int index = 0;
			for (EmployeeLeaveGraphDTO empOnLeave : employeeOnLeaveList) {
				if (colorList.size() <= index) {
					index = 0;
				}
				TimeLeaveAttendanceData data = new TimeLeaveAttendanceData();
				data.setValue(String.valueOf(empOnLeave.getEmployeeOnLeavePercentage()));
				data.setColor(colorList.get(index));
				Category category = new Category();
				category.setLabel(empOnLeave.getMonth());
				categoryList.add(category);
				arrayData.add(data);
				index++;
			}
			dataset.setData(arrayData);
			categories.setCategory(categoryList);
			datasetList.add(dataset);
			categoriesList.add(categories);

			timeLeaveAttendanceGraphDto = new TimeLeaveAttendanceGraphDto(chart, categoriesList, datasetList);
		}

		return timeLeaveAttendanceGraphDto;
	}
	
	

	@RequestMapping(value = { "/employeeLeaveTakenByLeaveType/{companyId}"}, method = {org.springframework.web.bind.annotation.RequestMethod.GET })
	public @ResponseBody LeaveTakenByLeaveTypeGraphDto getLeaveTakenByLeaveType(@PathVariable("companyId") Long companyId,
			HttpServletRequest req) throws ErrorHandling {
		logger.info("getLeaveTakenByLeaveType compid is : " + companyId);
		List<EmployeeLeaveGraphDTO> employeeOnLeaveList= leaveAttendanceReportService.getLeaveTakenByLeaveType(companyId);
		
		LeaveTakenByLeaveTypeGraphDto leaveTakenByLeaveTypeGraphDto = null;
 
		if (employeeOnLeaveList.size() > 0) {
			//Collections.sort(employeeOnLeaveList, new ObjectSorter());

			List<String> colorList = Arrays.asList("#ffbe3c", "#375baf", "#ff6060", "#1a84cc", "#845cee", "#2095f2",
					"#ff6060", "#a3a1fb", "#86b800", "#b9b9b9", "#f8b350", "#2095f2", "#888888");
			ArrayList<TimeLeaveAttendanceData> arrayData = new ArrayList<TimeLeaveAttendanceData>();
			ArrayList<Category> categoryList = new ArrayList<Category>();

			ArrayList<Dataset> datasetList = new ArrayList<Dataset>();

			Dataset dataset = new Dataset();
			Categories categories = new Categories();
			ArrayList<Categories> categoriesList = new ArrayList<Categories>();

			LeaveTakenByLeaveTypeChart chart = new LeaveTakenByLeaveTypeChart(
				
					
					LeaveTakenByLeaveTypeEnum.theme.getPieChartValue(),
					LeaveTakenByLeaveTypeEnum.showValues.getPieChartValue(),
					LeaveTakenByLeaveTypeEnum.numVisiblePlot.getPieChartValue(),
					LeaveTakenByLeaveTypeEnum.scrollheight.getPieChartValue(),
					LeaveTakenByLeaveTypeEnum.flatScrollBars.getPieChartValue(),
					LeaveTakenByLeaveTypeEnum.scrollShowButtons.getPieChartValue(),
					LeaveTakenByLeaveTypeEnum.showHoverEffect.getPieChartValue(),
					LeaveTakenByLeaveTypeEnum.showYAxisValue.getPieChartValue(),
					LeaveTakenByLeaveTypeEnum.labelFontColor.getPieChartValue());

			
			int index = 0;
			for (EmployeeLeaveGraphDTO empOnLeave : employeeOnLeaveList) {
				if (colorList.size() <= index) {
					index = 0;
				}
				TimeLeaveAttendanceData data = new TimeLeaveAttendanceData();
				data.setValue(empOnLeave.getLeaveTypeCount());
				data.setColor(colorList.get(index));
				Category category = new Category();
				category.setLabel(empOnLeave.getLeaveType());
				categoryList.add(category);
				arrayData.add(data);
				index++;
			}
			dataset.setData(arrayData);
			categories.setCategory(categoryList);
			datasetList.add(dataset);
			categoriesList.add(categories);

			leaveTakenByLeaveTypeGraphDto = new LeaveTakenByLeaveTypeGraphDto(chart, categoriesList, datasetList);
		}

		return leaveTakenByLeaveTypeGraphDto;
	}
	
	
	@RequestMapping(value = { "/employeeOnAbsent/{companyId}"}, method = {org.springframework.web.bind.annotation.RequestMethod.GET })
	public @ResponseBody TimeLeaveAttendanceGraphDto getEmployeeOnAbsent(@PathVariable("companyId") Long companyId,
			HttpServletRequest req) throws ErrorHandling {
		logger.info("getEmployeeOnAbsent compid is : " + companyId);
		List<EmployeeLeaveGraphDTO> employeeOnLeaveList= leaveAttendanceReportService.getEmployeeOnAbsent(companyId);
		
		TimeLeaveAttendanceGraphDto timeLeaveAttendanceGraphDto = null;
 
		if (employeeOnLeaveList.size() > 0) {
			//Collections.sort(employeeOnLeaveList, new ObjectSorter());

			List<String> colorList = Arrays.asList("#ffbe3c", "#375baf", "#ff6060", "#1a84cc", "#845cee", "#2095f2",
					"#ff6060", "#a3a1fb", "#86b800", "#b9b9b9", "#f8b350", "#2095f2", "#888888");
			ArrayList<TimeLeaveAttendanceData> arrayData = new ArrayList<TimeLeaveAttendanceData>();
			ArrayList<Category> categoryList = new ArrayList<Category>();

			ArrayList<Dataset> datasetList = new ArrayList<Dataset>();

			Dataset dataset = new Dataset();
			Categories categories = new Categories();
			ArrayList<Categories> categoriesList = new ArrayList<Categories>();

			TimeLeaveAttendanceChart chart = new TimeLeaveAttendanceChart(
					TimeAndAttendanceEnum.theme.getPieChartValue(),
					TimeAndAttendanceEnum.showvalues.getPieChartValue(),
					TimeAndAttendanceEnum.numVisiblePlot.getPieChartValue(),
					TimeAndAttendanceEnum.scrollheight.getPieChartValue(),
					TimeAndAttendanceEnum.flatScrollBars.getPieChartValue(),
					TimeAndAttendanceEnum.scrollShowButtons.getPieChartValue(),
					TimeAndAttendanceEnum.scrollColor.getPieChartValue(),
					TimeAndAttendanceEnum.showHoverEffect.getPieChartValue(),
					TimeAndAttendanceEnum.numbersuffix.getPieChartValue(),
					TimeAndAttendanceEnum.showYAxisvalue.getPieChartValue(),
					TimeAndAttendanceEnum.paletteColors.getPieChartValue(),
					TimeAndAttendanceEnum.labelFontColor.getPieChartValue());

			 
			int index = 0;
			for (EmployeeLeaveGraphDTO empOnLeave : employeeOnLeaveList) {
				if (colorList.size() <= index) {
					index = 0;
				}
				TimeLeaveAttendanceData data = new TimeLeaveAttendanceData();
				data.setValue(String.valueOf(empOnLeave.getEmployeeOnAbsent()));
				data.setColor(colorList.get(index));
				Category category = new Category();
				category.setLabel(empOnLeave.getMonth());
				categoryList.add(category);
				arrayData.add(data);
				index++;
			}
			dataset.setData(arrayData);
			categories.setCategory(categoryList);
			datasetList.add(dataset);
			categoriesList.add(categories);

			timeLeaveAttendanceGraphDto = new TimeLeaveAttendanceGraphDto(chart, categoriesList, datasetList);
		}

		return timeLeaveAttendanceGraphDto;
	}
	
	
	@RequestMapping(value = { "/employeeFrequentLeaveTaker/{companyId}"}, method = {org.springframework.web.bind.annotation.RequestMethod.GET })
	public @ResponseBody FrequentLeaveTakerGraphDto getEmployeeFrequentLeaveTaker(@PathVariable("companyId") Long companyId,
			HttpServletRequest req) throws ErrorHandling {
		logger.info("getEmployeeOnLeave compid is : " + companyId);
		List<EmployeeLeaveGraphDTO> employeeOnLeaveList= leaveAttendanceReportService.getEmployeeFrequentLeaveTaker(companyId);
		
		FrequentLeaveTakerGraphDto frequentLeaveTakerGraphDto = null;
 
		if (employeeOnLeaveList.size() > 0) {
			//Collections.sort(employeeOnLeaveList, new ObjectSorter());

			List<String> colorList = Arrays.asList("#ffbe3c", "#375baf", "#ff6060", "#1a84cc", "#845cee", "#2095f2",
					"#ff6060", "#a3a1fb", "#86b800", "#b9b9b9", "#f8b350", "#2095f2", "#888888");
			ArrayList<TimeLeaveAttendanceData> arrayData = new ArrayList<TimeLeaveAttendanceData>();
			ArrayList<Category> categoryList = new ArrayList<Category>();

			ArrayList<Dataset> datasetList = new ArrayList<Dataset>();

			Dataset dataset = new Dataset();
			Categories categories = new Categories();
			ArrayList<Categories> categoriesList = new ArrayList<Categories>();

			FrequentLeaveTakerChart chart = new FrequentLeaveTakerChart(
					FrequentLeaveTakerEnum.theme.getPieChartValue(),
					FrequentLeaveTakerEnum.showValues.getPieChartValue(),
					FrequentLeaveTakerEnum.numVisiblePlot.getPieChartValue(),
					FrequentLeaveTakerEnum.scrollheight.getPieChartValue(),
					FrequentLeaveTakerEnum.flatScrollBars.getPieChartValue(),
					FrequentLeaveTakerEnum.scrollShowButtons.getPieChartValue(),
					FrequentLeaveTakerEnum.scrollColor.getPieChartValue(),
					FrequentLeaveTakerEnum.showHoverEffect.getPieChartValue(),
					FrequentLeaveTakerEnum.showYAxisValue.getPieChartValue(),
					FrequentLeaveTakerEnum.paletteColors.getPieChartValue(),
					FrequentLeaveTakerEnum.labelFontColor.getPieChartValue());

			  
			int index = 0;
			for (EmployeeLeaveGraphDTO empOnLeave : employeeOnLeaveList) {
				if (colorList.size() <= index) {
					index = 0;
				}
				TimeLeaveAttendanceData data = new TimeLeaveAttendanceData();
				data.setValue(empOnLeave.getFreLeaveCount());
				//data.setColor(colorList.get(index));
				Category category = new Category();
				category.setLabel(empOnLeave.getEmpName());
				categoryList.add(category);
				arrayData.add(data);
				index++;
			}
			dataset.setData(arrayData);
			categories.setCategory(categoryList);
			datasetList.add(dataset);
			categoriesList.add(categories);

			frequentLeaveTakerGraphDto = new FrequentLeaveTakerGraphDto(chart, categoriesList, datasetList);
		}

		return frequentLeaveTakerGraphDto;
	}

	
}
