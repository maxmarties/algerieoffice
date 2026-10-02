package com.rinitec.algerieoffice.persistence.dao.inbox;

import java.util.List;
import java.util.UUID;

import org.joda.time.DateTime;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rinitec.algerieoffice.persistence.modal.inbox.Talk;

public interface TalkRepository extends JpaRepository<Talk, UUID>, TalkRepositoryCustom {

	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @param consulted
	 * @return
	 */
	long countByCompanyIdAndConsulted(Long companyId, Boolean consulted);
	
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
	@Query("select count(t.id) from Talk t where t.companyId = :companyId and t.id in :lines")
	long countTalks(@Param("companyId") Long companyId, @Param("lines") List<UUID> lines);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param date
	 */
	@Modifying
    @Query("delete from Talk t where t.talkedDate <= ?1")
    void deleteAllExpiredSince(DateTime date);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @param pageable
	 * @return
	 */
	@Query("select t from Talk t where t.companyId = :companyId order by t.talkedDate DESC")
	List<Talk> findAllTalk(@Param("companyId") Long companyId, Pageable pageable);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 */
	@Modifying
	@Query("update Talk t set t.consulted = true where t.companyId = ?1")
	void updateAllConsultedByCompanyId(Long companyId);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param lines
	 */
	@Modifying
	@Query("delete from Talk t where t.id in :lines")
	void deleteTalks(@Param("lines") List<UUID> lines);
	
}
