package com.csipl.hrms.service.recruitement.repository;

import java.util.Date;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.csipl.hrms.model.recruitment.CandidateLetter;

@Repository("candidateLetterRepository")
public interface CandidateLetterRepository extends CrudRepository<CandidateLetter, Long> {

	@Query(" from CandidateLetter where interviewScheduleId=?1 AND activeStatus='AC' ")
	CandidateLetter findCandidateLetterById(Long interviewScheduleId);

	String GET_OFFER_LETTER_ID = " SELECT l.letterId, l.letterName FROM Letter l ";

	@Query(value = GET_OFFER_LETTER_ID, nativeQuery = true)
	CandidateLetter findOfferLetterId();

	public static final String UPDATE_DECLARATION_STATUS = "UPDATE CandidateLetter SET declerationDate = :declerationDate , declerationStatus =:declerationStatus WHERE candidateLetterId =:candidateLetterId AND interviewScheduleId=:interviewScheduleId";

	@Modifying
	@Query(value = UPDATE_DECLARATION_STATUS)
	void updateDeclarationStatus(@Param("candidateLetterId") Long candidateLetterId,
			@Param("declerationStatus") String declerationStatus,
			@Param("interviewScheduleId") Long interviewScheduleId, @Param("declerationDate") Date declerationDate);
	
	public static final String UPDATE_ANNEXURE_STATUS = "UPDATE CandidateLetter SET annexureStatus =:annexureStatus WHERE candidateLetterId =:candidateLetterId AND interviewScheduleId=:interviewScheduleId";

	@Modifying
	@Query(value = UPDATE_ANNEXURE_STATUS)
	void updateSelectedAnnexure(@Param("candidateLetterId") Long candidateLetterId,
			@Param("annexureStatus") String declerationStatus,
			@Param("interviewScheduleId") Long interviewScheduleId);

}
