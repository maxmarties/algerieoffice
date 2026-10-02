package com.rinitec.algerieoffice.persistence.dao.company.portfolio;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rinitec.algerieoffice.persistence.modal.company.portfolio.Event;

public interface EventRepository extends JpaRepository<Event, UUID>, EventRepositoryCustom {

	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @return
	 */
	long countByCompanyId(Long companyId);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param hasPublished
	 * @return
	 */
	long countByHasPublished(Boolean hasPublished);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @param hasPublished
	 * @return
	 */
	long countByCompanyIdAndHasPublished(Long companyId, Boolean hasPublished);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param urlExtern
	 * @return
	 */
	boolean existsByUrlExtern(String urlExtern);
	
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
	@Query("select count(e.id) from Event e where e.companyId = :companyId and e.id in :lines")
	long countEvents(@Param("companyId") Long companyId, @Param("lines") List<UUID> lines);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @param identify
	 * @return
	 */
	@Query("select e.id from Event e where e.companyId = ?1 and e.identify = ?2")
	Optional<UUID> findIdByIdentify(Long companyId, String identify);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @return
	 */
	@Query("select e.id from Event e where e.companyId = ?1")
	List<UUID> findAllIdByCompanyId(Long companyId);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param eventId
	 * @return
	 */
	@Query("select e.title from Event e where e.id = ?1")
	Optional<String> findTitleByEventId(UUID eventId);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param eventId
	 * @param companyId
	 * @return
	 */
	@Query("select e.title from Event e where e.id = ?1 and e.companyId = ?2")
	Optional<String> findTitleById(UUID eventId, Long companyId);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param lines
	 */
	@Modifying
	@Query("delete from Event e where e.id in :lines")
	void deleteEvents(@Param("lines") List<UUID> lines);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @param identify
	 * @return
	 */
	@Query("select e from Event e where e.companyId = ?1 and e.identify = ?2 and e.hasPublished = true")
	Optional<Event> findEventByCompanyIdAndIdentify(Long companyId, String identify);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param eventId
	 */
	@Modifying
	@Query("update Event e set e.clickCount = (e.clickCount + 1) where e.id = ?1")
	void incrementClicks(UUID eventId);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @return
	 */
	@Query("select e.id from Event e where e.companyId = ?1")
	List<UUID> findAllEventIdsByCompanyId(Long companyId);
	
}
