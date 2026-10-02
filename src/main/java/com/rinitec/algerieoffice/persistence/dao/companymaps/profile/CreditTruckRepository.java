package com.rinitec.algerieoffice.persistence.dao.companymaps.profile;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rinitec.algerieoffice.persistence.modal.companymaps.profile.CreditTruck;

public interface CreditTruckRepository extends JpaRepository<CreditTruck, Long> {

	/**
	 * VERSION BEGIN 03/2021
	 * @param lines
	 */
	@Modifying
	@Query("delete from CreditTruck c where c.id in :lines")
	void deleteLinesCreditTruck(@Param("lines") List<Long> lines);
	
}
