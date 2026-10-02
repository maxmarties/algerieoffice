package com.rinitec.algerieoffice.persistence.dao.users.feedback;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rinitec.algerieoffice.persistence.modal.users.feedback.Evaluation;

public interface EvaluationRepository extends JpaRepository<Evaluation, UUID>, EvaluationRepositoryCustom {
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param userId
	 * @return
	 */
	long countByUserId(Long userId);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @return
	 */
	long countByCompanyId(Long companyId);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @param liked
	 * @return
	 */
	long countByCompanyIdAndLiked(Long companyId, Boolean liked);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param userId
	 */
	void deleteByUserId(Long userId);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 */
	void deleteByCompanyId(Long companyId);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param userId
	 * @return
	 */
	boolean existsByUserId(Long userId);

	/**
	 * VERSION BEGIN 03/2021
	 * @param userId
	 * @param companyId
	 * @return
	 */
	Evaluation findByUserIdAndCompanyId(Long userId, Long companyId);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @return
	 */
	@Query("select avg(e.note) from Evaluation e where e.companyId = ?1")
	float findNoteByCompanyId(Long companyId);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param userId
	 * @param lines
	 * @return
	 */
	@Query("select count(e.id) from Evaluation e where e.userId = :userId and e.id in :lines")
	long countUserEvaluations(@Param("userId") Long userId, @Param("lines") List<UUID> lines);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param lines
	 */
	@Modifying
	@Query("delete from Evaluation e where e.id in :lines")
	void deleteUserEvaluations(@Param("lines") List<UUID> lines);
	
}
