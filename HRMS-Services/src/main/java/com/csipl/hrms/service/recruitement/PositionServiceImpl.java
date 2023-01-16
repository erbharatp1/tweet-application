package com.csipl.hrms.service.recruitement;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.csipl.hrms.model.recruitment.Position;
import com.csipl.hrms.service.recruitement.repository.PositionRepository;

@Service("positionService")
@Transactional
public class PositionServiceImpl implements PositionService {

	@Autowired
	private PositionRepository positionRepository;

	@Override
	public List<Position> findAllPositionData() {

		return positionRepository.findAllPositionData();
	}

	@Override
	@Transactional
	public void save(final Position position) {

		if (position.getPositionId() != null && position.getPositionId() != 0) {
			positionRepository.deleteLevelById(position.getPositionId());
		}
		positionRepository.save(position);
	}

	public List<Object[]> getRecruitersListBasedOnPosition(final Long positionId) {

		return positionRepository.findRecruitersListBasedOnPosition(positionId);

	}

	@Override
	public List<Object[]> findOnePosition(final Long positionId) {

		return positionRepository.findOnePosition(positionId);
	}

	@Override
	public List<Position> findPositionListForSixMonths() {
		return positionRepository.findPositionListForSixMonths();
	}

	@Override
	public void updateByPosition(final Position position) {

		positionRepository.updateById(position.getPositionId(), position.getPositionStatus(),
				position.getApprovalRemark(), position.getUserIdUpdate());
	}

	@Override
	public List<Object[]> getLevelListBasedOnPosition(Long positionId) {
		// TODO Auto-generated method stub
		return positionRepository.findLevelListBasedOnPosition(positionId);
	}

	@Override
	public List<Object[]> getAllPositionList() {
		// TODO Auto-generated method stub
		return positionRepository.findAllPositionList();
	}

	@Override
	public List<Object[]> findPositiongByPositionCode(String positionCode) {
		return positionRepository.findPositiongByPositionCode(positionCode);
	}

	@Override
	public void saveExtraBudgetRequest(Position position) {
		positionRepository.saveExtraBudgetDetails(position.getPositionId(), position.getExtraBudget(),
				position.getExtraBudgetRemark(), position.getExtraBudgetStatus(), position.getUserIdUpdate(),
				position.getDateUpdated());
	}

	@Override
	public Position findPositionById(final Long positionId) {

		return positionRepository.findOne(positionId);
	}

	@Override
	public void updateExtraBudgetApprovalStatus(Position position) {
		// TODO Auto-generated method stub
		positionRepository.updateExtBdtStsById(position.getPositionId(), position.getExtraBudgetStatus(),
				position.getExtraBudgetApprovalRemark(), position.getUserIdUpdate(), position.getDateUpdated());

	}

	@Override
	public List<Position> findAllPositionDataNext() {
		// TODO Auto-generated method stub
		return positionRepository.findAllPositionDataNext();
	}

	@Override
	public List<Position> findAllPositionCode() {
		// TODO Auto-generated method stub
		return positionRepository.findAllPositionCode();
	}


	@Override
	public List<Object[]> getAllRemainingLevelList(Long positionId, Long interviewScheduleId) {
		// TODO Auto-generated method stub
		return positionRepository.findAllRemainingLevelList(positionId, interviewScheduleId);
	}

	@Override
	public List<Object[]> getAllLevels(Long interviewScheduleId,Long levelId) {
		// TODO Auto-generated method stub
		return positionRepository.getAllLevels(interviewScheduleId,levelId);
	}

	@Override
	public List<Object[]> getAllPositionCountList(Long positionId) {
		// TODO Auto-generated method stub
		return positionRepository.findAllPositionCountList(positionId);
	}

}
