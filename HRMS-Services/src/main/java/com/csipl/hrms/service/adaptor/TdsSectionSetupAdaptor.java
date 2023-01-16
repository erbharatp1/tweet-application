package com.csipl.hrms.service.adaptor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import com.csipl.hrms.dto.payroll.TdsSectionSetupDTO;
import com.csipl.hrms.model.payroll.TdsGroupSetup;
import com.csipl.hrms.model.payroll.TdsSectionSetup;
public class TdsSectionSetupAdaptor implements Adaptor<TdsSectionSetupDTO, TdsSectionSetup> {

	@Override
	public List<TdsSectionSetup> uiDtoToDatabaseModelList(List<TdsSectionSetupDTO> tdsSectionSetupDTOList) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<TdsSectionSetupDTO> databaseModelToUiDtoList(List<TdsSectionSetup> tdsSectionSetupList) {
		List<TdsSectionSetupDTO> tdsSectionSetupDTOList = new ArrayList<TdsSectionSetupDTO>();
		tdsSectionSetupList.forEach(tdsSectionSetup -> {
			tdsSectionSetupDTOList.add(databaseModelToUiDto(tdsSectionSetup));
		});
		return tdsSectionSetupDTOList;
	}

	@Override
	public TdsSectionSetup uiDtoToDatabaseModel(TdsSectionSetupDTO tdsSectionSetupDTO) {
		TdsSectionSetup tdsSectionSetup = new TdsSectionSetup();
		tdsSectionSetup.setTdsSectionId(tdsSectionSetupDTO.getTdsSectionId());
		tdsSectionSetup.setActiveStatus(tdsSectionSetupDTO.getActiveStatus());
		tdsSectionSetup.setTdsSectionName(tdsSectionSetupDTO.getTdsSectionName());
		tdsSectionSetup.setMaxLimit(tdsSectionSetupDTO.getMaxLimit());
		tdsSectionSetup.setUserId(tdsSectionSetupDTO.getUserId());
		tdsSectionSetup.setDateCreated(tdsSectionSetupDTO.getDateCreated());
		if (tdsSectionSetupDTO.getDateCreated() == null)
			tdsSectionSetup.setDateCreated(new Date());
		else
			tdsSectionSetup.setDateCreated(tdsSectionSetupDTO.getDateCreated());
		tdsSectionSetup.setDateUpdate(new Date());
		tdsSectionSetup.setUserIdUpdate(tdsSectionSetupDTO.getUserIdUpdate());
		return tdsSectionSetup;
	}

	public List<TdsSectionSetupDTO> databaseModelToUiDto(List<TdsSectionSetup> tdsSectionSetupDTO) {
		List<TdsSectionSetupDTO> tdsSectionSetupDTOList = new ArrayList<TdsSectionSetupDTO>();
		tdsSectionSetupDTO.forEach(items -> {
			tdsSectionSetupDTOList.add(databaseModelToUiDto(items));
		});

		return tdsSectionSetupDTOList;
	}

	public TdsSectionSetup uiDtoToDatabaseModel(TdsSectionSetupDTO tdsSectionSetupDTO,
			TdsSectionSetup tdsSectionSetup) {
		TdsGroupSetup tdsGroupSetup = new TdsGroupSetup();
		tdsGroupSetup.setTdsGroupId(tdsSectionSetupDTO.getTdsGroupId());
		tdsSectionSetup.setTdsGroupSetup(tdsGroupSetup);
		tdsSectionSetup.setTdsSectionId(tdsSectionSetupDTO.getTdsSectionId());
		tdsSectionSetup.setActiveStatus(tdsSectionSetupDTO.getActiveStatus());
		tdsSectionSetup.setMaxLimit(tdsSectionSetupDTO.getMaxLimit());
		tdsSectionSetup.setTdsSectionName(tdsSectionSetupDTO.getTdsSectionName());
		tdsSectionSetup.setUserId(tdsSectionSetupDTO.getUserId());
		if (tdsSectionSetupDTO.getDateCreated() == null)
			tdsSectionSetup.setDateCreated(new Date());
		else
			tdsSectionSetup.setDateCreated(tdsSectionSetupDTO.getDateCreated());
		tdsSectionSetup.setDateUpdate(new Date());
		tdsSectionSetup.setUserIdUpdate(tdsSectionSetupDTO.getUserId());
		return tdsSectionSetup;
	}

	@Override
	public TdsSectionSetupDTO databaseModelToUiDto(TdsSectionSetup tdsSectionSetup) {
		TdsSectionSetupDTO tdsSectionSetupDTO = new TdsSectionSetupDTO();
		tdsSectionSetupDTO.setTdsSectionId(tdsSectionSetup.getTdsSectionId());
		tdsSectionSetupDTO.setTdsSectionName(tdsSectionSetup.getTdsSectionName());
		tdsSectionSetupDTO.setMaxLimit(tdsSectionSetup.getMaxLimit());
		tdsSectionSetupDTO.setUserId(tdsSectionSetup.getUserId());
		tdsSectionSetupDTO.setActiveStatus(tdsSectionSetup.getActiveStatus());
		tdsSectionSetupDTO.setDateCreated(tdsSectionSetup.getDateCreated());
		return tdsSectionSetupDTO;
	}

	public List<TdsSectionSetup> uiDtoToDatabaseModelListTds(List<TdsSectionSetupDTO> tdsSectionSetupList,
			TdsSectionSetup tdsSectionSetup) {
		
		List<TdsSectionSetup> tdsSectionList = new ArrayList<TdsSectionSetup>();
		for (TdsSectionSetupDTO tdsSectionSetupDTO : tdsSectionSetupList) {
			tdsSectionList.add(uiDtoToDatabaseModel(tdsSectionSetupDTO, tdsSectionSetup));
		}

		return tdsSectionList;
	}

	public TdsSectionSetup tdsSectionUitoDatabaseModel(TdsSectionSetupDTO tdsSectionSetupDTO) {
		TdsSectionSetup tdsSectionSetup = new TdsSectionSetup();
		TdsGroupSetup tdsGroupSetup = new TdsGroupSetup();
		tdsGroupSetup.setTdsGroupId(tdsSectionSetupDTO.getTdsGroupId());
		tdsSectionSetup.setTdsGroupSetup(tdsGroupSetup);
		tdsSectionSetup.setActiveStatus(tdsSectionSetupDTO.getActiveStatus());
		tdsSectionSetup.setTdsSectionId(tdsSectionSetupDTO.getTdsSectionId());
		tdsSectionSetup.setTdsSectionName(tdsSectionSetupDTO.getTdsSectionName());
		tdsSectionSetup.setMaxLimit(tdsSectionSetupDTO.getMaxLimit());
		tdsSectionSetup.setUserId(tdsSectionSetupDTO.getUserId());
		tdsSectionSetup.setDateCreated(tdsSectionSetupDTO.getDateCreated());
		if (tdsSectionSetupDTO.getDateCreated() == null)
			tdsSectionSetup.setDateCreated(new Date());
		else
			tdsSectionSetup.setDateCreated(tdsSectionSetupDTO.getDateCreated());
		tdsSectionSetup.setDateUpdate(new Date());
		tdsSectionSetup.setUserIdUpdate(tdsSectionSetupDTO.getUserId());

		return tdsSectionSetup;
	}

	public List<TdsSectionSetup> tdsSectionuiDtoToDatabaseModelListTds(List<TdsSectionSetupDTO> tdsSectionSetupDtoList) {
		List<TdsSectionSetup> tdsSectionSetupList = new ArrayList<TdsSectionSetup>();
		for (TdsSectionSetupDTO TdsSectionSetupDto : tdsSectionSetupDtoList) {
			tdsSectionSetupList.add(tdsSectionUitoDatabaseModel(TdsSectionSetupDto));
		}
		return tdsSectionSetupList;
	}

	public List<TdsSectionSetupDTO> tdsSectiondatabaseModelToUiDtoList(List<TdsSectionSetup> tdsSectionSetupList) {
		List<TdsSectionSetupDTO> tdsSectionSetupDto = new ArrayList<TdsSectionSetupDTO>();
		for (TdsSectionSetup tdsSectionSetup : tdsSectionSetupList) {
			tdsSectionSetupDto.add(tdsSectionbDatabaseModelToUi(tdsSectionSetup));
		}
		return tdsSectionSetupDto;
	}

	private TdsSectionSetupDTO tdsSectionbDatabaseModelToUi(TdsSectionSetup tdsSectionSetup) {
		TdsSectionSetupDTO tdsSectionSetupDTO = new TdsSectionSetupDTO();
		tdsSectionSetupDTO.setTdsSectionId(tdsSectionSetup.getTdsSectionId());
	     tdsSectionSetupDTO.setMaxLimit(tdsSectionSetup.getMaxLimit());
		tdsSectionSetupDTO.setActiveStatus(tdsSectionSetup.getActiveStatus());
		tdsSectionSetupDTO.setTdsSectionName(tdsSectionSetup.getTdsSectionName());
		tdsSectionSetupDTO.setUserId(tdsSectionSetup.getUserId());
		tdsSectionSetupDTO.setDateCreated(tdsSectionSetup.getDateCreated());
		tdsSectionSetupDTO.setUserIdUpdate(tdsSectionSetup.getUserIdUpdate());
		return tdsSectionSetupDTO;

	}

}
