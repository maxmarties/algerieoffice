package com.rinitec.algerieoffice.persistence.dao.company.portfolio;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rinitec.algerieoffice.persistence.modal.company.portfolio.WorkDetail;

public interface WorkDetailRepository extends JpaRepository<WorkDetail, UUID> {

	/**
	 * VERSION BEGIN 03/2021
	 * @param urlExtern
	 * @return
	 */
	boolean existsByUrlExtern(String urlExtern);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param workId
	 * @return
	 */
	@Query("select w.photoUUID from WorkDetail w where w.id = ?1")
	Optional<UUID> findPhotoUUIDById(UUID workId);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param lines
	 * @return
	 */
	@Query("select w.photoUUID from WorkDetail w where w.id in :lines")
	List<UUID> findAllPhotoUUIDById(@Param("lines") List<UUID> lines);

	/**
	 * VERSION BEGIN 03/2021
	 * @param lines
	 */
	@Modifying
	@Query("delete from WorkDetail w where w.id in :lines")
	void deleteWorksDetail(@Param("lines") List<UUID> lines);
	
}
