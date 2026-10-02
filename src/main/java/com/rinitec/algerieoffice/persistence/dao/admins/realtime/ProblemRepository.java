package com.rinitec.algerieoffice.persistence.dao.admins.realtime;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rinitec.algerieoffice.persistence.modal.admins.realtime.Problem;

public interface ProblemRepository extends JpaRepository<Problem, UUID>, ProblemRepositoryCustom {

	/**
	 * VERSION BEGIN 03/2021
	 * @param userId
	 */
	void deleteByUserId(Long userId);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param lines
	 * @return
	 */
	@Query("select count(p.id) from Problem p where p.id in :lines")
	long countProblems(@Param("lines") List<UUID> lines);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param lines
	 */
	@Modifying
	@Query("delete from Problem p where p.id in :lines")
	void deleteProblems(@Param("lines") List<UUID> lines);
	
}
