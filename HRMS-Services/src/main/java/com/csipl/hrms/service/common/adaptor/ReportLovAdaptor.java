package com.csipl.hrms.service.common.adaptor;

import java.util.ArrayList;
import java.util.List;

import com.csipl.hrms.dto.common.ReportLovDTO;
import com.csipl.hrms.model.common.ReportLov;
import com.csipl.hrms.service.adaptor.Adaptor;

public class ReportLovAdaptor implements Adaptor<ReportLovDTO, ReportLov> {

	@Override
	public List<ReportLov> uiDtoToDatabaseModelList(List<ReportLovDTO> uiobj) {

		return null;
	}

	@Override
	public List<ReportLovDTO> databaseModelToUiDtoList(List<ReportLov> reportLovList) {
		List<ReportLovDTO> reportLovDTOList = new ArrayList<ReportLovDTO>();
		for (ReportLov reportLov : reportLovList) {
			reportLovDTOList.add(databaseModelToUiDto(reportLov));
		}
		return reportLovDTOList;
	}

	@Override
	public ReportLov uiDtoToDatabaseModel(ReportLovDTO uiobj) {

		return null;
	}

	@Override
	public ReportLovDTO databaseModelToUiDto(ReportLov reportLov) {
		ReportLovDTO reportLovDTO = new ReportLovDTO();
		reportLovDTO.setModuleName(reportLov.getModuleName());
		reportLovDTO.setReportId(reportLov.getReportId());
		reportLovDTO.setReportLovId(reportLov.getReportLovId());
		reportLovDTO.setReportName(reportLov.getReportName());
		reportLovDTO.setStatus(reportLov.getStatus());

		return reportLovDTO;
	}

}
