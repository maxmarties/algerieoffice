package com.rinitec.algerieoffice.persistence.dao.company.portfolio;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rinitec.algerieoffice.persistence.modal.company.portfolio.EventCalendar;

public interface EventCalendarRepository extends JpaRepository<EventCalendar, UUID> {

	/**
	 * VERSION BEGIN 03/2021
	 * @param eventUUID
	 */
	void deleteByEventUUID(UUID eventUUID);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param eventUUID
	 * @return
	 */
	@Query("select e from EventCalendar e where e.eventUUID = ?1 order by e.eventDate")
	List<EventCalendar> findAllByEventUUID(UUID eventUUID);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param lines
	 */
	@Modifying
	@Query("delete from EventCalendar e where e.id in :lines")
	void deleteLinesEventCalendar(@Param("lines") List<UUID> lines);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param eventUUID
	 * @return
	 */
	@Query("select e.wilaya from EventCalendar e where e.eventUUID = ?1")
	List<Integer> findAllWilayasByEventUUID(UUID eventUUID);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param lines
	 */
	@Modifying
	@Query("delete from EventCalendar e where e.eventUUID in :lines")
	void deleteEventCalendarByEventIds(@Param("lines") List<UUID> lines);
	
}
