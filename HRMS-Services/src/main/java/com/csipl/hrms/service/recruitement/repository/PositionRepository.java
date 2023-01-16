package com.csipl.hrms.service.recruitement.repository;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.csipl.hrms.model.recruitment.Position;

@Repository("positionRepository")
public interface PositionRepository extends CrudRepository<Position, Long> {

	public static final String UPDATE_BY_POSITION = " UPDATE Position ps SET ps.positionStatus =:positionStatus  , ps.approvalRemark=:approvalRemark, ps.userIdUpdate=:userIdUpdate WHERE ps.positionId = :positionId";

	public static final String DELETE_LEVEL_BY_ID = "DELETE FROM PositionInterviewlevelXRef WHERE PositionInterviewlevelXRef.positionId =?1";

	public static final String FIND_ONE_POSITION = "select  pos.positionId, pos.positionCode, pos.positionTitle,pos.noOfLevel,pos.requiredExperience, pos.employeementType, pos.jobLocation, pos.noOfPosition,  pos.expectedate, pos.maxBudget, pos.sourceOfPosion, pos.interviewType, pos.remark, pos.positionStatus, pos.extraBudget, pos.extraBudgetStatus, gr.gradesName\r\n"
			+ ",\r\n"
			+ "CONCat((CONCAT(UPPER(SUBSTRING(aprEmp.firstName ,1,1)),LOWER(SUBSTRING(aprEmp.firstName ,2)))),' ',( CONCAT(UPPER(SUBSTRING(aprEmp.lastName ,1,1)),LOWER(SUBSTRING(aprEmp.lastName ,2)))) )as 'UserName',aprEmp.employeeCode as 'UserNameCode'\r\n"
			+ ",pos.datecreated, \r\n"
			+ "CONCat((CONCAT(UPPER(SUBSTRING(eep.firstName ,1,1)),LOWER(SUBSTRING(eep.firstName ,2)))),' ',( CONCAT(UPPER(SUBSTRING(eep.lastName ,1,1)),LOWER(SUBSTRING(eep.lastName ,2)))) )as 'HiringMangerName',eep.employeeCode as 'HiringMangerCode' ,pas.employeeName,appEmp.employeeCode\r\n"
			+ "FROM Position pos join Employee emp  LEFT JOIN Users us on us.userId = pos.userIdUpdate LEFT join Employee aprEmp on aprEmp.employeeCode = us.nameOfUser LEFT JOIN Employee eep ON pos.hiringManagerId =eep.employeeId LEFT JOIN Grades gr on pos.gradeId= gr.gradesId\r\n"
			+ "LEFT JOIN PositionApprovalPerson pas on pos.approvePersonId=pas.positionApprovalPersonId LEFT JOIN Employee appEmp on pas.employeeId=appEmp.employeeId\r\n"
			+ "WHERE pos.positionId=?1 GROUP BY pos.positionId";

	@Query(" from Position WHERE positionStatus='APR' ORDER BY positionId DESC")
	List<Position> findAllPositionData();

	public static final String RECRUITERS_LIST_BASED_ON_POSITION = "Select  pa.recruiterEmployeeId , concat(e.firstName, ' ' ,e.lastName),pi.levelId,pi.levelName from PositionAllocationXref pa\r\n"
			+ "LEFT JOIN Position p on p.positionId=pa.positionId\r\n"
			+ "LEFT JOIN Employee e on e.employeeId=pa.recruiterEmployeeId  "
			+ "LEFT JOIN PositionInterviewlevelXRef pi on pi.positionId=p.positionId WHERE p.positionId=?1 \r\n";

	public static final String RecruitersListBasedOnPosition = "Select  pa.recruiterEmployeeId , concat(e.firstName, ' ' ,e.lastName) from PositionAllocationXref pa\r\n"
			+ "LEFT JOIN Position p on p.positionId=pa.positionId\r\n"
			+ "LEFT JOIN Employee e on e.employeeId=pa.recruiterEmployeeId  " + " WHERE p.positionId=?1 \r\n";

	@Query(value = RecruitersListBasedOnPosition, nativeQuery = true)
	List<Object[]> findRecruitersListBasedOnPosition(Long positionId);

	@Query(value = FIND_ONE_POSITION, nativeQuery = true)
	List<Object[]> findOnePosition(Long positionId);

	@Query(value = "select * FROM Position ps  WHERE  ps.datecreated > DATE_SUB(now(), INTERVAL 6 MONTH)   ORDER BY  ps.positionId  DESC", nativeQuery = true)
	List<Position> findPositionListForSixMonths();

	@Modifying
	@Query(value = UPDATE_BY_POSITION)
	void updateById(@Param("positionId") Long positionId, @Param("positionStatus") String positionStatus,
			@Param("approvalRemark") String approvalRemark, @Param("userIdUpdate") Long userIdUpdate);

	public static final String LevelListBasedOnPosition = "Select  pi.levelId,pi.levelName from Position p \r\n"
			+ "		 LEFT JOIN PositionInterviewlevelXRef pi on pi.positionId=p.positionId WHERE p.positionId=?1 AND pi.levelIndex='L1'\r\n";

	@Query(value = LevelListBasedOnPosition, nativeQuery = true)
	List<Object[]> findLevelListBasedOnPosition(Long positionId);

//	public static final String All_Position_List = " Select p.positionId, concat(p.positionTitle, '(', p.positionCode, ')') from Position p  JOIN PositionAllocationXref pa on pa.positionId=p.positionId JOIN PositionInterviewlevelXRef pi on p.positionId=pi.positionId GROUP BY p.positionId  order by p.positionId DESC ";
	public static final String All_Position_List ="Select  p.positionId, concat(p.positionTitle, '(', p.positionCode, ')'), p.noOfPosition  from CandidateFinalEvalution cf \r\n" + 
			"  RIGHT JOIN Position p on p.positionId=cf.positionId\r\n" + 
			"     GROUP By p.positionId  ORDER BY p.positionId  DESC";
	@Query(value = All_Position_List, nativeQuery = true)
	List<Object[]> findAllPositionList();

	String POSITION_BY_POSITION_CODE = "SELECT p.positionId, p.positionCode, p.positionTitle, p.requiredExperience, p.jobLocation,p.noOfLevel,g.gradesName ,p.maxBudget, p.noOfPosition,p.expectedate, p.extraBudget, concat(e.firstName, ' ', e.lastName)as employeeName"
			+ ",p.sourceOfPosion,p.remark,p.datecreated,p.gradeId,p.hiringManagerId,p.employeementType,p.extraBudgetRemark,p.userId,p.JdId,p.approvePersonId,p.interviewType,p.extraBudgetStatus,p.positionStatus,p.positionType, pi.levelName from Position p LEFT JOIN Employee e ON e.employeeId=p.hiringManagerId LEFT JOIN Grades g "
			+ "ON g.gradesId=p.gradeId LEFT JOIN PositionInterviewlevelXRef pi on pi.positionId=p.positionId WHERE p.positionStatus='APR' and p.positionCode=?1 GROUP BY p.positionId ";

	@Query(value = POSITION_BY_POSITION_CODE, nativeQuery = true)
	List<Object[]> findPositiongByPositionCode(String positionCode);

	public static final String UPDATE_EXT_BDT_STATUS = " UPDATE Position ps SET ps.extraBudgetStatus =:extraBudgetStatus  , ps.extraBudgetApprovalRemark=:extraBudgetApprovalRemark,ps.userIdUpdate=:userIdUpdate,ps.dateUpdated=:dateUpdated WHERE ps.positionId = :positionId";

	@Modifying
	@Query(value = UPDATE_EXT_BDT_STATUS, nativeQuery = true)
	void updateExtBdtStsById(@Param("positionId") Long positionId, @Param("extraBudgetStatus") String extraBudgetStatus,
			@Param("extraBudgetApprovalRemark") String extraBudgetApprovalRemark,
			@Param("userIdUpdate") Long userIdUpdate, @Param("dateUpdated") Date dateUpdated);

	@Modifying
	@Query(value = DELETE_LEVEL_BY_ID, nativeQuery = true)
	public void deleteLevelById(Long positionId);

	public static final String SAVE_EXT_BDT_DETAILS = " UPDATE Position ps SET ps.extraBudget =:extraBudget , ps.extraBudgetRemark=:extraBudgetRemark,ps.extraBudgetStatus =:extraBudgetStatus,ps.userIdUpdate=:userIdUpdate,ps.dateUpdated=:dateUpdated WHERE ps.positionId = :positionId";

	@Modifying
	@Query(value = SAVE_EXT_BDT_DETAILS, nativeQuery = true)
	void saveExtraBudgetDetails(@Param("positionId") Long positionId, @Param("extraBudget") BigDecimal extraBudget,
			@Param("extraBudgetRemark") String extraBudgetRemark, @Param("extraBudgetStatus") String extraBudgetStatus,
			@Param("userIdUpdate") Long userIdUpdate, @Param("dateUpdated") Date dateUpdated);

	@Query(" from Position ORDER BY positionId DESC")
	List<Position> findAllPositionDataNext();

	@Query(" from Position p where p.positionStatus='APR'")
	List<Position> findAllPositionCode();

	public static final String AllRemainingLevelList = "Select  pi.levelId, pi.levelName from PositionInterviewlevelXRef pi \r\n"
			+ "	 LEFT JOIN InterviewScheduler i on  i.positionId=pi.positionId \r\n"
			+ "	 LEFT JOIN CandidateEvolution c on c.levelId=pi.levelId\r\n"
			+ "	 WHERE i.positionId=?1  AND c.interviewScheduleId=?2 AND c.isInterviewScheduled='N' GROUP BY c.levelId";

	@Query(value = AllRemainingLevelList, nativeQuery = true)
	List<Object[]> findAllRemainingLevelList(Long positionId, Long interviewScheduleId);

	public static final String AllLevels = " Select  pi.levelId, pi.levelName from PositionInterviewlevelXRef pi \r\n" + 
			"  LEFT JOIN InterviewScheduler i on  i.positionId=pi.positionId LEFT JOIN CandidateEvolution c on c.levelId=pi.levelId\r\n" + 
			"  WHERE i.interviewScheduleId=?1  and c.levelId=?2 GROUP BY c.levelId";

	@Query(value = AllLevels, nativeQuery = true)
	List<Object[]> getAllLevels(Long interviewScheduleId, Long levelId);

	
	public static final String AllPositionCount = "Select  p.positionId, concat(p.positionTitle, '(', p.positionCode, ')'), p.noOfPosition, count(cf.positionId),( p.noOfPosition-(count(cf.positionId))) as finalCount from CandidateFinalEvalution cf \r\n" + 
			"LEFT JOIN Position p on p.positionId=cf.positionId\r\n" + 
			"WHERE cf.finalStatus='C'   AND p.positionId=?1  ORDER BY p.positionId DESC";

	@Query(value = AllPositionCount, nativeQuery = true)
	List<Object[]> findAllPositionCountList(Long positionId);

}
