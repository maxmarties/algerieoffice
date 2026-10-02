package com.rinitec.algerieoffice.persistence.dao.companymaps.overview;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rinitec.algerieoffice.persistence.modal.companymaps.overview.CatalogItem;

public interface CatalogItemRepository extends JpaRepository<CatalogItem, Long> {

	/**
	 * VERSION BEGIN 03/2021
	 * @param lines
	 */
	@Modifying
	@Query("delete from CatalogItem c where c.id in :lines")
	void deleteLinesCatalogItem(@Param("lines") List<Long> lines);
	
}
