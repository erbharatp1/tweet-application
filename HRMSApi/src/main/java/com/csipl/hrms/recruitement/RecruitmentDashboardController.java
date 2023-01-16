package com.csipl.hrms.recruitement;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.csipl.hrms.common.enums.PositionClosedSourceEnum;
import com.csipl.hrms.common.enums.PositionCreatedSourceEnum;
import com.csipl.hrms.common.util.ErrorHandling;
import com.csipl.hrms.dto.recruitment.Categories;
import com.csipl.hrms.dto.recruitment.Category;
import com.csipl.hrms.dto.recruitment.Dataset;
import com.csipl.hrms.dto.recruitment.PositionClosedBySourceChart;
import com.csipl.hrms.dto.recruitment.PositionClosedBySourceGraphDTO;
import com.csipl.hrms.dto.recruitment.PositionCreatedSourceChart;
import com.csipl.hrms.dto.recruitment.PositionCreatedSourceData;
import com.csipl.hrms.dto.recruitment.PositionCreatedSourceGraphDTO;
import com.csipl.hrms.dto.recruitment.PositionSourceGraphDTO;
import com.csipl.hrms.service.recruitement.RecruitmentDashboardService;

@RestController
@RequestMapping({ "/recruitmentDashboard" })
public class RecruitmentDashboardController {

	private static final Logger logger = LoggerFactory.getLogger(RecruitmentDashboardController.class);

	@Autowired
	RecruitmentDashboardService recruitmentDashboardService;

	@RequestMapping(value = { "/positionCreatedSource/{companyId}/{processMonth}" }, method = { RequestMethod.GET })
	public @ResponseBody PositionCreatedSourceGraphDTO getPositionCreatedSource(
			@PathVariable("companyId") Long companyId, @PathVariable("processMonth") Long processMonth,
			HttpServletRequest req) throws ErrorHandling {

		logger.info("getPositionCreatedSource is calling");

		List<PositionSourceGraphDTO> noOfPositionList = recruitmentDashboardService.getPositionCreatedSource(companyId,
				processMonth);

		PositionCreatedSourceGraphDTO positionCreatedSourceGraphDTO = null;

		if (noOfPositionList.size() > 0) {

			List<String> colorList = Arrays.asList("#ffbe3c", "#375baf", "#ff6060", "#1a84cc", "#845cee", "#2095f2",
					"#ff6060", "#a3a1fb", "#86b800", "#b9b9b9", "#f8b350", "#2095f2", "#888888");
			ArrayList<PositionCreatedSourceData> arrayData = new ArrayList<PositionCreatedSourceData>();
			ArrayList<Category> categoryList = new ArrayList<Category>();

			ArrayList<Dataset> datasetList = new ArrayList<Dataset>();

			Dataset dataset = new Dataset();
			Categories categories = new Categories();
			ArrayList<Categories> categoriesList = new ArrayList<Categories>();

			PositionCreatedSourceChart chart = new PositionCreatedSourceChart(

					PositionCreatedSourceEnum.theme.getPieChartValue(),
					PositionCreatedSourceEnum.showValues.getPieChartValue(),
					PositionCreatedSourceEnum.numVisiblePlot.getPieChartValue(),
					PositionCreatedSourceEnum.scrollheight.getPieChartValue(),
					PositionCreatedSourceEnum.flatScrollBars.getPieChartValue(),
					PositionCreatedSourceEnum.scrollShowButtons.getPieChartValue(),
					PositionCreatedSourceEnum.showHoverEffect.getPieChartValue(),
					PositionCreatedSourceEnum.showYAxisValue.getPieChartValue(),
					PositionCreatedSourceEnum.labelFontColor.getPieChartValue());

			int index = 0;
			for (PositionSourceGraphDTO empOnLeave : noOfPositionList) {
				if (colorList.size() <= index) {
					index = 0;
				}
				PositionCreatedSourceData data = new PositionCreatedSourceData();
				data.setValue(empOnLeave.getPositionCount());
				data.setColor(colorList.get(index));
				Category category = new Category();
				category.setLabel(empOnLeave.getSourceOfPosition());
				categoryList.add(category);
				arrayData.add(data);
				index++;
			}
			dataset.setData(arrayData);
			categories.setCategory(categoryList);
			datasetList.add(dataset);
			categoriesList.add(categories);

			positionCreatedSourceGraphDTO = new PositionCreatedSourceGraphDTO(chart, categoriesList, datasetList);
		}

		return positionCreatedSourceGraphDTO;
	}

	@RequestMapping(value = { "/overallBacklog/{companyId}/{processMonth}" }, method = { RequestMethod.GET })
	public @ResponseBody PositionCreatedSourceGraphDTO getOverAllBacklogs(@PathVariable("companyId") Long companyId,
			@PathVariable("processMonth") Long processMonth, HttpServletRequest req) throws ErrorHandling {

		logger.info("getOverAllBacklogs is calling");

		Map<String, String> employeeOnLeaveList = recruitmentDashboardService.getOverallBacklogs(companyId,
				processMonth);

		PositionCreatedSourceGraphDTO leaveTakenByLeaveTypeGraphDto = null;

		if (employeeOnLeaveList.size() > 0) {

			List<String> colorList = Arrays.asList("#ffbe3c", "#375baf", "#ff6060", "#1a84cc", "#845cee", "#2095f2",
					"#ff6060", "#a3a1fb", "#86b800", "#b9b9b9", "#f8b350", "#2095f2", "#888888");
			ArrayList<PositionCreatedSourceData> arrayData = new ArrayList<PositionCreatedSourceData>();
			ArrayList<Category> categoryList = new ArrayList<Category>();

			ArrayList<Dataset> datasetList = new ArrayList<Dataset>();

			Dataset dataset = new Dataset();
			Categories categories = new Categories();
			ArrayList<Categories> categoriesList = new ArrayList<Categories>();

			PositionCreatedSourceChart chart = new PositionCreatedSourceChart(

					PositionCreatedSourceEnum.theme.getPieChartValue(),
					PositionCreatedSourceEnum.showValues.getPieChartValue(),
					PositionCreatedSourceEnum.numVisiblePlot.getPieChartValue(),
					PositionCreatedSourceEnum.scrollheight.getPieChartValue(),
					PositionCreatedSourceEnum.flatScrollBars.getPieChartValue(),
					PositionCreatedSourceEnum.scrollShowButtons.getPieChartValue(),
					PositionCreatedSourceEnum.showHoverEffect.getPieChartValue(),
					PositionCreatedSourceEnum.showYAxisValue.getPieChartValue(),
					PositionCreatedSourceEnum.labelFontColor.getPieChartValue());

			int index = 0;
			for (Map.Entry<String, String> empOnLeave : employeeOnLeaveList.entrySet()) {
				if (colorList.size() <= index) {
					index = 0;
				}
				PositionCreatedSourceData data = new PositionCreatedSourceData();
				data.setValue(empOnLeave.getValue());
				data.setColor(colorList.get(index));
				Category category = new Category();
				category.setLabel(empOnLeave.getKey());
				categoryList.add(category);
				arrayData.add(data);
				index++;
			}
			dataset.setData(arrayData);
			categories.setCategory(categoryList);
			datasetList.add(dataset);
			categoriesList.add(categories);

			leaveTakenByLeaveTypeGraphDto = new PositionCreatedSourceGraphDTO(chart, categoriesList, datasetList);
		}

		return leaveTakenByLeaveTypeGraphDto;
	}

	@RequestMapping(value = { "/positionClosedBySource" }, method = { RequestMethod.GET })
	public @ResponseBody PositionClosedBySourceGraphDTO getPositionClosedBySource(HttpServletRequest req)
			throws ErrorHandling {

		logger.info("getPositionCreatedSource is calling");

		List<PositionSourceGraphDTO> positionClosedSource = recruitmentDashboardService.getPositionClosedBySource();

		PositionClosedBySourceGraphDTO positionCreatedSourceGraphDTO = null;

		if (positionClosedSource.size() > 0) {

			List<String> colorList = Arrays.asList("#ffbe3c", "#375baf", "#ff6060", "#1a84cc", "#845cee", "#2095f2",
					"#ff6060", "#a3a1fb", "#86b800", "#b9b9b9", "#f8b350", "#2095f2", "#888888");
			ArrayList<PositionCreatedSourceData> arrayData = new ArrayList<PositionCreatedSourceData>();
			ArrayList<Category> categoryList = new ArrayList<Category>();

			ArrayList<Dataset> datasetList = new ArrayList<Dataset>();

			Dataset dataset = new Dataset();
			Categories categories = new Categories();
			ArrayList<Categories> categoriesList = new ArrayList<Categories>();

			PositionClosedBySourceChart chart = new PositionClosedBySourceChart(

					PositionClosedSourceEnum.theme.getPieChartValue(),
					PositionClosedSourceEnum.xAxisName.getPieChartValue(),
					PositionClosedSourceEnum.yAxisName.getPieChartValue(),
					PositionClosedSourceEnum.lineThickness.getPieChartValue(),
					PositionClosedSourceEnum.divlineAlpha.getPieChartValue(),
					PositionClosedSourceEnum.divlineColor.getPieChartValue(),
					PositionClosedSourceEnum.divlineThickness.getPieChartValue(),
					PositionClosedSourceEnum.divLineIsDashed.getPieChartValue(),
					PositionClosedSourceEnum.divLineDashLen.getPieChartValue(),
					PositionClosedSourceEnum.divLineGapLen.getPieChartValue(),
					PositionClosedSourceEnum.showXAxisLine.getPieChartValue(),
					PositionClosedSourceEnum.xAxisLineThickness.getPieChartValue(),
					PositionClosedSourceEnum.anchorRadius.getPieChartValue(),
					PositionClosedSourceEnum.anchorBorderThickness.getPieChartValue(),
					PositionClosedSourceEnum.anchorBorderColor.getPieChartValue(),
					PositionClosedSourceEnum.anchorBgColor.getPieChartValue());

			int index = 0;
			for (PositionSourceGraphDTO empOnLeave : positionClosedSource) {
				if (colorList.size() <= index) {
					index = 0;
				}
				PositionCreatedSourceData data = new PositionCreatedSourceData();
				data.setValue(empOnLeave.getSourceOfProfileCount());
				data.setColor(colorList.get(index));
				Category category = new Category();
				category.setLabel(empOnLeave.getSourceOfProfile());
				categoryList.add(category);
				arrayData.add(data);
				index++;
			}
			dataset.setData(arrayData);
			categories.setCategory(categoryList);
			datasetList.add(dataset);
			categoriesList.add(categories);

			positionCreatedSourceGraphDTO = new PositionClosedBySourceGraphDTO(chart, categoriesList, datasetList);
		}

		return positionCreatedSourceGraphDTO;
	}

	@RequestMapping(value = { "/positionClosedByMonth/{companyId}" }, method = { RequestMethod.GET })
	public @ResponseBody PositionCreatedSourceGraphDTO getPositionClosedByMonth(
			@PathVariable("companyId") Long companyId, HttpServletRequest req) throws ErrorHandling {

		logger.info("getPositionClosedByMonth is calling");

		List<PositionSourceGraphDTO> positionClosedByMonthList = recruitmentDashboardService
				.getPositionClosedByMonth(companyId);

		PositionCreatedSourceGraphDTO positionCreatedSourceGraphDTO = null;

		if (positionClosedByMonthList.size() > 0) {

			List<String> colorList = Arrays.asList("#ffbe3c", "#375baf", "#ff6060", "#1a84cc", "#845cee", "#2095f2",
					"#ff6060", "#a3a1fb", "#86b800", "#b9b9b9", "#f8b350", "#2095f2", "#888888");
			ArrayList<PositionCreatedSourceData> arrayData = new ArrayList<PositionCreatedSourceData>();
			ArrayList<Category> categoryList = new ArrayList<Category>();

			ArrayList<Dataset> datasetList = new ArrayList<Dataset>();

			Dataset dataset = new Dataset();
			Categories categories = new Categories();
			ArrayList<Categories> categoriesList = new ArrayList<Categories>();

			PositionCreatedSourceChart chart = new PositionCreatedSourceChart(

					PositionCreatedSourceEnum.theme.getPieChartValue(),
					PositionCreatedSourceEnum.showValues.getPieChartValue(),
					PositionCreatedSourceEnum.numVisiblePlot.getPieChartValue(),
					PositionCreatedSourceEnum.scrollheight.getPieChartValue(),
					PositionCreatedSourceEnum.flatScrollBars.getPieChartValue(),
					PositionCreatedSourceEnum.scrollShowButtons.getPieChartValue(),
					PositionCreatedSourceEnum.showHoverEffect.getPieChartValue(),
					PositionCreatedSourceEnum.showYAxisValue.getPieChartValue(),
					PositionCreatedSourceEnum.labelFontColor.getPieChartValue());

			int index = 0;
			for (PositionSourceGraphDTO positionClosedByMonth : positionClosedByMonthList) {
				if (colorList.size() <= index) {
					index = 0;
				}
				PositionCreatedSourceData data = new PositionCreatedSourceData();
				data.setValue(positionClosedByMonth.getPositionClosedByMonthCount());
				data.setColor(colorList.get(index));
				Category category = new Category();
				category.setLabel(
						positionClosedByMonth.getMonthName().substring(0, 3) + '-' + positionClosedByMonth.getYear());
				categoryList.add(category);
				arrayData.add(data);
				index++;
			}
			dataset.setData(arrayData);
			categories.setCategory(categoryList);
			datasetList.add(dataset);
			categoriesList.add(categories);

			positionCreatedSourceGraphDTO = new PositionCreatedSourceGraphDTO(chart, categoriesList, datasetList);
		}

		return positionCreatedSourceGraphDTO;
	}

	@RequestMapping(value = { "/recruiterBacklog/{companyId}/{processMonth}" }, method = { RequestMethod.GET })
	public @ResponseBody PositionCreatedSourceGraphDTO getRecruiterBacklogs(@PathVariable("companyId") Long companyId,
			@PathVariable("processMonth") Long processMonth, HttpServletRequest req) throws ErrorHandling {

		Map<String, PositionSourceGraphDTO> map = recruitmentDashboardService.getRecruiterBacklogs(companyId,
				processMonth);

		PositionCreatedSourceGraphDTO positionCreatedSourceGraphDTO = null;

		if (map.size() > 0) {

			ArrayList<Category> categoryList = new ArrayList<Category>();

			ArrayList<Dataset> datasetList = new ArrayList<Dataset>();
			ArrayList<Dataset> dataList = new ArrayList<Dataset>();

			Dataset totaldataset = new Dataset();
			Dataset closeddataset = new Dataset();
			Dataset opendataset = new Dataset();
			Categories categories = new Categories();
			ArrayList<Categories> categoriesList = new ArrayList<Categories>();

			PositionCreatedSourceChart chart = new PositionCreatedSourceChart(

					PositionCreatedSourceEnum.theme.getPieChartValue(),
					PositionCreatedSourceEnum.showValues.getPieChartValue(),
					PositionCreatedSourceEnum.numVisiblePlot.getPieChartValue(),
					PositionCreatedSourceEnum.scrollheight.getPieChartValue(),
					PositionCreatedSourceEnum.flatScrollBars.getPieChartValue(),
					PositionCreatedSourceEnum.scrollShowButtons.getPieChartValue(),
					PositionCreatedSourceEnum.showHoverEffect.getPieChartValue(),
					PositionCreatedSourceEnum.showYAxisValue.getPieChartValue(),
					PositionCreatedSourceEnum.labelFontColor.getPieChartValue());

			ArrayList<PositionCreatedSourceData> arrayData = new ArrayList<PositionCreatedSourceData>();
			ArrayList<PositionCreatedSourceData> arrayData2 = new ArrayList<PositionCreatedSourceData>();
			ArrayList<PositionCreatedSourceData> arrayData3 = new ArrayList<PositionCreatedSourceData>();

			for (Map.Entry<String, PositionSourceGraphDTO> entry : map.entrySet()) {

				PositionCreatedSourceData data = new PositionCreatedSourceData();
				PositionSourceGraphDTO positionSourceGraphDTO = entry.getValue();
				data.setValue(positionSourceGraphDTO.getTotalPositionCount());
				totaldataset.setSeriesname("Total");
				Category category = new Category();
				category.setLabel(entry.getKey());
				categoryList.add(category);
				arrayData.add(data);

			}

			for (Map.Entry<String, PositionSourceGraphDTO> entry : map.entrySet()) {

				PositionCreatedSourceData data = new PositionCreatedSourceData();
				PositionSourceGraphDTO positionSourceGraphDTO = entry.getValue();
				data.setValue(positionSourceGraphDTO.getClosePositionCount());
				closeddataset.setSeriesname("Closed");
				arrayData2.add(data);

			}

			for (Map.Entry<String, PositionSourceGraphDTO> entry : map.entrySet()) {

				PositionCreatedSourceData data = new PositionCreatedSourceData();
				PositionSourceGraphDTO positionSourceGraphDTO = entry.getValue();
				data.setValue(positionSourceGraphDTO.getOpenCount());
				opendataset.setSeriesname("Open");
				arrayData3.add(data);

			}

			totaldataset.setData(arrayData);
			closeddataset.setData(arrayData2);
			opendataset.setData(arrayData3);
			categories.setCategory(categoryList);
			datasetList.add(totaldataset);
			datasetList.add(closeddataset);
			datasetList.add(opendataset);
			dataList.addAll(datasetList);
			categoriesList.add(categories);

			positionCreatedSourceGraphDTO = new PositionCreatedSourceGraphDTO(chart, categoriesList, dataList);
		}

		return positionCreatedSourceGraphDTO;
	}

}
