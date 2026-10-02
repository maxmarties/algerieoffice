package com.rinitec.algerieoffice.persistence.dao.companymaps.overview;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rinitec.algerieoffice.persistence.modal.companymaps.overview.Timeline;

public interface TimelineRepository extends JpaRepository<Timeline, Long> {
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @return
	 */
	long countByCompanyId(Long companyId);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 */
	void deleteByCompanyId(Long companyId);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @return
	 */
	@Query("select t from Timeline t where t.companyId = :companyId order by t.lineDate")
	List<Timeline> findAllTimeline(@Param("companyId") Long companyId);

	/**
	 * VERSION BEGIN 03/2021
	 * @param lines
	 */
	@Modifying
	@Query("delete from Timeline c where c.id in :lines")
	void deleteLinesTimeline(@Param("lines") List<Long> lines);
	
}
