package com.csipl.hrms.service.recruitement;

import java.util.List;

import com.csipl.hrms.model.recruitment.Position;

public interface PositionService {

	List<Position> findAllPositionData();

	void save(Position position);

	List<Object[]> getRecruitersListBasedOnPosition(Long positionId);

	List<Object[]> findOnePosition(Long positionId);

	List<Position> findPositionListForSixMonths();

	public void updateByPosition(Position position);

	List<Object[]> getLevelListBasedOnPosition(Long positionId);

	List<Object[]> findPositiongByPositionCode(String positionCode);

	void saveExtraBudgetRequest(Position position);

	Position findPositionById(Long positionId);

	void updateExtraBudgetApprovalStatus(Position position);

	List<Position> findAllPositionDataNext();

	List<Object[]> getAllPositionList();

	List<Position> findAllPositionCode();

	List<Object[]> getAllRemainingLevelList(Long positionId, Long interviewScheduleId);

	List<Object[]> getAllLevels(Long interviewScheduleId,Long levelId);

	List<Object[]> getAllPositionCountList(Long positionId);

}
