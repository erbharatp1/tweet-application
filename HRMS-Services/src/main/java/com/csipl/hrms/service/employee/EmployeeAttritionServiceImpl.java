package com.csipl.hrms.service.employee;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.csipl.hrms.common.enums.AttritionEnum;
import com.csipl.hrms.dto.report.AttritionChart;
import com.csipl.hrms.dto.report.AttritionDto;
import com.csipl.hrms.dto.report.Category;
import com.csipl.hrms.dto.report.DataJoined;
import com.csipl.hrms.dto.report.DataResigned;
import com.csipl.hrms.dto.report.DatasetJoined;
import com.csipl.hrms.dto.report.DatasetResigned;
import com.csipl.hrms.dto.report.EmployeeReportDTO;
import com.csipl.hrms.service.report.EmployeeReportService;

@Service("employeeAttritionService")
public class EmployeeAttritionServiceImpl implements EmployeeAttritionService {
	@Autowired
	EmployeeReportService employeeReportService;
	
      	@Override
	public AttritionDto empAttritionDetail(Long companyId) {
		List<EmployeeReportDTO> empResignedList = employeeReportService.EmpAttritionofResigned(companyId);
		List<EmployeeReportDTO> empJoinedList = employeeReportService.EmpAttritionofJoined(companyId);
		
		HashMap<String, ArrayList<Category>> hashMap = new HashMap<String, ArrayList<Category>>();
		ArrayList<HashMap<String, ArrayList<Category>>> categories = new ArrayList<HashMap<String, ArrayList<Category>>>();

		ArrayList<Category> category = new ArrayList<Category>();
		ArrayList<Object> dataset = new ArrayList<Object>();

		/* new logic for attrition two graph */

		ArrayList<String> joinMonth = new ArrayList<>();
		ArrayList<String> joinValue = new ArrayList<>();
		ArrayList<String> resValue = new ArrayList<>();

		HashMap<String, String> hs = new HashMap<String, String>();
		hs.put("Jan", "");
		hs.put("Feb", "");
		hs.put("Mar", "");
		hs.put("Apr", "");
		hs.put("May", "");
		hs.put("Jun", "");
		hs.put("Jul", "");
		hs.put("Aug", "");
		hs.put("Sep", "");
		hs.put("Oct", "");
		hs.put("Nov", "");
		hs.put("Dec", "");

		HashMap<String, String> hsRes = new HashMap<String, String>();
		hsRes.put("Jan", "");
		hsRes.put("Feb", "");
		hsRes.put("Mar", "");
		hsRes.put("Apr", "");
		hsRes.put("May", "");
		hsRes.put("Jun", "");
		hsRes.put("Jul", "");
		hsRes.put("Aug", "");
		hsRes.put("Sep", "");
		hsRes.put("Oct", "");
		hsRes.put("Nov", "");
		hsRes.put("Dec", "");

		for (EmployeeReportDTO empDt : empJoinedList) {
			hs.replace(empDt.getProcessMonth(), empDt.getEmpCount());

		}

		for (EmployeeReportDTO empDt : empResignedList) {
			hsRes.replace(empDt.getProcessMonth(), empDt.getEmpCount());

		}

		joinMonth.add("Jan");
		joinMonth.add("Feb");
		joinMonth.add("Mar");
		joinMonth.add("Apr");
		joinMonth.add("May");
		joinMonth.add("Jun");
		joinMonth.add("Jul");
		joinMonth.add("Aug");
		joinMonth.add("Sep");
		joinMonth.add("Oct");
		joinMonth.add("Nov");
		joinMonth.add("Dec");

		for (int j = 0; j < joinMonth.size(); j++) {
			joinValue.add(hs.get(joinMonth.get(j)));
		//	System.out.println("========" + joinValue.get(j));
		}
		for (int j = 0; j < joinMonth.size(); j++) {
			resValue.add(hsRes.get(joinMonth.get(j)));
			//System.out.println("========" + resValue.get(j));
		}

		AttritionChart chart = new AttritionChart(AttritionEnum.caption.getPieChartValue(),
				AttritionEnum.yAxisName.getPieChartValue(), AttritionEnum.plotgradientcolor.getPieChartValue(),
				AttritionEnum.bgcolor.getPieChartValue(), AttritionEnum.showalternatehgridcolor.getPieChartValue(),
				AttritionEnum.divlinecolor.getPieChartValue(), AttritionEnum.showvalues.getPieChartValue(),
				AttritionEnum.showcanvasborder.getPieChartValue(), AttritionEnum.canvasborderalpha.getPieChartValue(),
				AttritionEnum.canvasbordercolor.getPieChartValue(),
				AttritionEnum.canvasborderthickness.getPieChartValue(), AttritionEnum.yaxismaxvalue.getPieChartValue(),
				AttritionEnum.captionpadding.getPieChartValue(), AttritionEnum.linethickness.getPieChartValue(),
				AttritionEnum.yaxisvaluespadding.getPieChartValue(), AttritionEnum.legendshadow.getPieChartValue(),
				AttritionEnum.legendborderalpha.getPieChartValue(), AttritionEnum.palettecolors.getPieChartValue(),
				AttritionEnum.showborder.getPieChartValue());
		

		for (String empMonth : joinMonth) {

			Category c = new Category(empMonth, "false", "true");
			category.add(c);
		}
		hashMap.put("category", category);
		categories.add(hashMap);

		ArrayList<DataJoined> arr = new ArrayList<DataJoined>();
		for (String value : joinValue) {

			DataJoined data = new DataJoined(value);
			arr.add(data);

		}

		DatasetJoined dj = new DatasetJoined(arr);
		dataset.add(dj);

		ArrayList<DataResigned> arr1 = new ArrayList<DataResigned>();

		for (String value : resValue) {

			DataResigned data = new DataResigned(value);
			arr1.add(data);

		}

		DatasetResigned dr = new DatasetResigned(arr1);
		dataset.add(dr);
		AttritionDto dto = new AttritionDto(chart, categories, dataset);
		return dto;
	}

}
