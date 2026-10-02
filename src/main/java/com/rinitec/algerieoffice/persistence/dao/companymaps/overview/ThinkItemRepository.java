package com.rinitec.algerieoffice.persistence.dao.companymaps.overview;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rinitec.algerieoffice.persistence.modal.companymaps.overview.ThinkItem;

public interface ThinkItemRepository extends JpaRepository<ThinkItem, Long> {

	/**
	 * VERSION BEGIN 03/2021
	 * @param lines
	 */
	@Modifying
	@Query("delete from ThinkItem t where t.id in :lines")
	void deleteLinesThinkItem(@Param("lines") List<Long> lines);
	
}
