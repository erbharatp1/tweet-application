package com.csipl.hrms.service.recruitement.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.csipl.tms.dto.common.SearchDTO;
@Repository
public class InterviewSchedulerPaginationRepositoryImpl implements InterviewSchedulerPaginationRepository {
	
	@PersistenceContext(unitName = "mySQL")
	@Autowired
	private EntityManager em;

	
	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> findAssignedInterviewSchedule(Long companyId, SearchDTO searcDto) {
		StringBuilder sb = new StringBuilder();
//		sb.append(
//				" Select i.candidateName, i.candidateContactNo, i.candidateEmailId, p.positionTitle, p.positionCode,\r\n" + 
//						"p.requiredExperience, p.noOfLevel,  concat(e.firstName, ' ', e.lastName), i.interviewScheduleId,  MAX(c.evalutionId),   GROUP_CONCAT(concat(pi.levelIndex,'-', pi.levelName, '-', IFNULL(c.status,'Pending')) ORDER by pi.levelId) \r\n" + 
//						"from InterviewScheduler i LEFT JOIN CandidateEvolution c on i.interviewScheduleId=c.interviewScheduleId\r\n" + 
//						"LEFT JOIN Position p on p.positionId=i.positionId\r\n" + 
//						"LEFT JOIN PositionAllocationXref pa on pa.positionId=p.positionId\r\n" + 
//						"LEFT JOIN  Employee e on e.employeeId=pa.recruiterEmployeeId LEFT JOIN PositionInterviewlevelXRef pi on pi.levelId=c.levelId \r\n" + 
//						"WHERE e.companyId=:companyId and i.activeStatus='AC' ");
		
		sb.append("Select i.candidateName, i.candidateContactNo, i.candidateEmailId, p.positionTitle, p.positionCode,\r\n" + 
				"	p.requiredExperience, p.noOfLevel,  concat(e.firstName, ' ', e.lastName), i.interviewScheduleId,  MAX(c.evalutionId),  GROUP_CONCAT(concat(pi.levelIndex,'-', pi.levelName, '-', IFNULL(c.status,'Pending')) ORDER by pi.levelId),  i.positionId \r\n" + 
				"	from InterviewScheduler i LEFT JOIN CandidateEvolution c on i.interviewScheduleId=c.interviewScheduleId\r\n" + 
				"	LEFT JOIN Position p on p.positionId=i.positionId\r\n" + 
				"	LEFT JOIN  Employee e on e.employeeId=i.recuiterId    \r\n" + 
				"   LEFT JOIN PositionInterviewlevelXRef pi on pi.levelId=c.levelId and   i.positionId=pi.positionId\r\n" + 
				"   WHERE e.companyId=:companyId and i.activeStatus='AC' AND c.interviewTime IS NOT NULL AND c.interviewDate IS NOT NULL  ");
				
				
		String active = searcDto.getActive();
		System.out.println(" 1-Active  - " + active);
		String sortDirection = searcDto.getSortDirection();
		int offset = searcDto.getOffset();
		buildCondtion(sb, searcDto);
		sortSearchQuery(sb, active, sortDirection);
		int limit = searcDto.getLimit();

		String search = sb.toString();

		//System.out.println("Query Start-----------------------");
		//System.out.println(search);
		//System.out.println("Query End-----------------------");
		Query nativeQuery = em.createNativeQuery(search);
		nativeQuery.setParameter("companyId", companyId);
		nativeQuery.setFirstResult(offset);
		nativeQuery.setMaxResults(limit);
		final List<Object[]> resultList = nativeQuery.getResultList();
		System.out.println("result Size ---" + resultList.size());
		return resultList;

	}
	
	/**
	 * @param employeeSearchDto
	 * @param sb
	 */
	private void buildCondtion(StringBuilder sb, SearchDTO searcDto) {

		if (searcDto.getEmployeeName() != null && !searcDto.getEmployeeName().equals("")
				&& !searcDto.getEmployeeName().equalsIgnoreCase("null")
				&& !searcDto.getEmployeeName().equalsIgnoreCase("undefined")) {

//			sb.append(" and ( e.firstName LIKE '" + searcDto.getEmployeeName() + "%' or  e.lastName LIKE '"
//					+ searcDto.getEmployeeName() + "%' )");
			
			
			sb.append(" and ( e.firstName LIKE '" + searcDto.getEmployeeName() + "%' or  e.lastName LIKE '"
					+ searcDto.getEmployeeName() + "%'" + "OR p.positionCode LIKE '%" + searcDto.getEmployeeName()
					+ "%'" + "OR p.positionTitle LIKE '%" + searcDto.getEmployeeName() + "%'"
					+ "OR i.candidateName LIKE '%" + searcDto.getEmployeeName() + "%'" + "OR i.candidateContactNo LIKE '%" + searcDto.getEmployeeName() + "%'"  +   "OR  i.candidateEmailId LIKE '%"
					+ searcDto.getEmployeeName() + "%' )");

		}
		
		
		if (searcDto.getCandidateName() != null && !searcDto.getCandidateName().equals("")
				&& !searcDto.getCandidateName().equalsIgnoreCase("null")
				&& !searcDto.getCandidateName().equalsIgnoreCase("undefined")) {

			sb.append(" and i.candidateName LIKE '" + searcDto.getCandidateName() + "%' ");
		
		}
		
		
		if (searcDto.getCandidateContactNo() != null && !searcDto.getCandidateContactNo().equals("")
				&& !searcDto.getCandidateContactNo().equalsIgnoreCase("null")
				&& !searcDto.getCandidateContactNo().equalsIgnoreCase("undefined")) {

			sb.append(" and i.candidateContactNo LIKE '" + searcDto.getCandidateContactNo() + "%' ");

		}
		
		
		if (searcDto.getCandidateEmailId() != null && !searcDto.getCandidateEmailId().equals("")
				&& !searcDto.getCandidateEmailId().equalsIgnoreCase("null")
				&& !searcDto.getCandidateEmailId().equalsIgnoreCase("undefined")) {

			sb.append(" and i.candidateEmailId LIKE '" + searcDto.getCandidateEmailId() + "%' ");

		}
		
		if (searcDto.getPositionTitle() != null && !searcDto.getPositionTitle().equals("")
				&& !searcDto.getPositionTitle().equalsIgnoreCase("null")
				&& !searcDto.getPositionTitle().equalsIgnoreCase("undefined")) {

			sb.append(" and p.positionTitle LIKE '" + searcDto.getPositionTitle() + "%' ");
		}

		if (searcDto.getPositionCode() != null && !searcDto.getPositionCode().equals("")
				&& !searcDto.getPositionCode().equalsIgnoreCase("null")
				&& !searcDto.getPositionCode().equalsIgnoreCase("undefined")) {

			sb.append(" and p.positionCode LIKE '" + searcDto.getPositionCode() + "%' ");
		}

		
		
		

	}

	/**
	 * @param sb
	 * @param active
	 * @param sortDirection
	 */
	private void sortSearchQuery(StringBuilder sb, String active, String sortDirection) {
		System.out.println("Active - " + active);
		if (active != null && (active.trim().equals("") || active.trim().equals("undefined"))) {

			sb.append("  group by c.interviewScheduleId order by c.interviewScheduleId desc ");

		}else if (active != null && (active.trim().equals("candidateName"))) {

			if (sortDirection.equals("asc")) {
				sb.append(" group by c.interviewScheduleId order by i.candidateName asc ");
			} else {
				sb.append(" group by c.interviewScheduleId order by i.candidateName desc ");
			}
		
		
		}
		else if (active != null && (active.trim().equals("positionTitle"))) {

			if (sortDirection.equals("asc")) {
				sb.append(" group by c.interviewScheduleId order by p.positionTitle asc ");
			} else {
				sb.append(" group by c.interviewScheduleId order by p.positionTitle desc ");
			}
		
		
		}else if (active != null && (active.trim().equals("firstName"))) {

			if (sortDirection.equals("asc")) {
				sb.append(" group by c.interviewScheduleId order by e.firstName asc ");
			} else {
				sb.append(" group by c.interviewScheduleId order by e.firstName desc ");
			}

		} else {

			sb.append("   group by c.interviewScheduleId order by c.interviewScheduleId desc");
		}
	}


}
