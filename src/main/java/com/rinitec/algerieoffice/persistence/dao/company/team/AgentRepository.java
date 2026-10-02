package com.rinitec.algerieoffice.persistence.dao.company.team;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rinitec.algerieoffice.persistence.modal.company.team.Agent;

public interface AgentRepository extends JpaRepository<Agent, Long>, AgentRepositoryCustom {

	/**
	 * VERSION BEGIN 03/2021
	 * @param email
	 * @return
	 */
	boolean existsByEmail(String email);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param phone
	 * @return
	 */
	boolean existsByPhone(String phone);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param facebook
	 * @return
	 */
	boolean existsByFacebook(String facebook);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param twitter
	 * @return
	 */
	boolean existsByTwitter(String twitter);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param linkedin
	 * @return
	 */
	boolean existsByLinkedin(String linkedin);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param userId
	 * @return
	 */
	boolean existsByUserId(Long userId);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @return
	 */
	long countByCompanyId(Long companyId);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @param hasPingled
	 * @return
	 */
	long countByCompanyIdAndHasPingled(Long companyId, Boolean hasPingled);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param sexe
	 * @return
	 */
	long countBySexe(Boolean sexe);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param hasPingled
	 * @return
	 */
	long countByHasPingled(Boolean hasPingled);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 */
	void deleteByCompanyId(Long companyId);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @param lines
	 * @return
	 */
	@Query("select count(a.id) from Agent a where a.companyId = :companyId and a.id in :lines")
	long countAgents(@Param("companyId") Long companyId, @Param("lines") List<Long> lines);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @return
	 */
	@Query("select a.id from Agent a where a.companyId = ?1")
	List<Long> findAllIdByCompanyId(Long companyId);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param userId
	 */
	@Modifying
	@Query("update Agent a set a.userId = null where a.userId = ?1")
	void trashUserId(Long userId);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @param lines
	 */
	@Modifying
	@Query("update Agent a set a.userId = null where a.companyId = :companyId and a.userId in :lines")
	void trashUsers(@Param("companyId") Long companyId, @Param("lines") List<Long> lines);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param lines
	 */
	@Modifying
	@Query("delete from Agent a where a.id in :lines")
	void deleteAgents(@Param("lines") List<Long> lines);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @param pageable
	 * @return
	 */
	@Query("select a from Agent a where a.companyId = :companyId")
	List<Agent> findAllExplorerAgent(@Param("companyId") Long companyId, Pageable pageable);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @param pageable
	 * @return
	 */
	@Query("select a from Agent a where a.companyId = :companyId and a.hasPingled = true")
	List<Agent> findExplorerContactAgent(@Param("companyId") Long companyId, Pageable pageable);
	
}
