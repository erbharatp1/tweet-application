package com.csipl.hrms.service.report;



import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.csipl.hrms.dto.report.DepartmentReportDTO;
import com.csipl.hrms.service.report.repository.DepartmentReportRepository;
import com.csipl.hrms.service.util.ConverterUtil;

@Service("departmentReportService")
public class DepartmentReportServiceImpl implements DepartmentReportService {

	 
	@Autowired
	private DepartmentReportRepository departmentReportRepository;
	@Override
	public List<DepartmentReportDTO> departmentWiseCTC(Long companyId,Long p_process_month) {
	 
		List<Object[]>  empReportList  = departmentReportRepository.fetchDepartmentWiseCTCList(companyId,p_process_month);
		List<DepartmentReportDTO> departmentReportDTO = new ArrayList<DepartmentReportDTO>();
		List<String> colorDegiCount = Arrays.asList("#ffbe3c", "#375baf", "#ff6060", "#1a84cc", "#845cee", "#2095f2",
				"#ff6060", "#a3a1fb", "#86b800", "#b9b9b9", "#f8b350", "#2095f2", "#888888","#ffbe3c", "#375baf", "#ff6060", "#1a84cc", "#845cee", "#2095f2",
				"#ff6060", "#a3a1fb", "#86b800", "#b9b9b9", "#f8b350", "#2095f2", "#888888","#ffbe3c", "#375baf", "#ff6060", "#1a84cc", "#845cee", "#2095f2",
				"#ff6060", "#a3a1fb", "#86b800", "#b9b9b9", "#f8b350", "#2095f2", "#888888","#ffbe3c", "#375baf", "#ff6060", "#1a84cc", "#845cee", "#2095f2",
				"#ff6060", "#a3a1fb", "#86b800", "#b9b9b9", "#f8b350", "#2095f2", "#888888");
	 
		int j=0;
		for (Object[] report : empReportList) {
			DepartmentReportDTO deptDto = new DepartmentReportDTO();
			deptDto.setDeptNAME(ConverterUtil.getString(report[0]));
			deptDto.setDeptCTC(ConverterUtil.getString(report[1]));
			deptDto.setColor(colorDegiCount.get(j));
			j++;
 
			departmentReportDTO.add(deptDto);

		}
		
		return departmentReportDTO ;
		
	
	}
	
	
	@Override
	public String processmonthbyLastmonth(Long companyId,Long p_process_month) {
		// TODO Auto-generated method stub
	
		Object  empReportList  = departmentReportRepository.processmonthbyLastmonth(companyId,p_process_month);
		       if((String)empReportList!=null) {
		    	   String processMonth=(String)empReportList;
		    	   System.out.println("======service======="+processMonth);
		    	   return processMonth;
		       }
			
		
		return null ;
		
	
	}
	
	@Override
	public List<DepartmentReportDTO> LastSixMonthCTC(Long companyId ,Long p_process_month) {
		// TODO Auto-generated method stub
		List<Object[]>  dptReportList  = departmentReportRepository.fetchLastSixMonthCTC(companyId,p_process_month);
		List<DepartmentReportDTO> departmentReportDTO = new ArrayList<DepartmentReportDTO>();
	
					
				 for ( Object[] report : dptReportList ) { 
					DepartmentReportDTO deptDto = new DepartmentReportDTO();
					if(ConverterUtil.getString( report[0])!=null) {
						 deptDto.setLastMonth(ConverterUtil.getString( report[0]));	
					}
					if(ConverterUtil.getString( report[1])!=null) {
						 deptDto.setDeptCTC(ConverterUtil.getString( report[1]));
					}
					
					
					 departmentReportDTO.add(deptDto);
					 System.out.println("========strored==================="+ConverterUtil.getString( report[0]));
					 System.out.println("==========strored================="+ConverterUtil.getString( report[1]));
					
					
				}	
		
		return departmentReportDTO ;
	}

}
