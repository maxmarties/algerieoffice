package com.rinitec.algerieoffice.persistence.dao.company.portfolio;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rinitec.algerieoffice.persistence.modal.company.portfolio.EventDetail;

public interface EventDetailRepository extends JpaRepository<EventDetail, UUID> {
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param eventId
	 * @return
	 */
	@Query("select e.photoUUID from EventDetail e where e.id = ?1")
	Optional<UUID> findPhotoUUIDById(UUID eventId);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param lines
	 * @return
	 */
	@Query("select e.photoUUID from EventDetail e where e.id in :lines")
	List<UUID> findAllPhotoUUIDById(@Param("lines") List<UUID> lines);

	/**
	 * VERSION BEGIN 03/2021
	 * @param lines
	 */
	@Modifying
	@Query("delete from EventDetail e where e.id in :lines")
	void deleteEventsDetail(@Param("lines") List<UUID> lines);
	
}
