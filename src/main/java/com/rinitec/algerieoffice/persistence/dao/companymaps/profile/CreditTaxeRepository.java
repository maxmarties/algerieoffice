package com.rinitec.algerieoffice.persistence.dao.companymaps.profile;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rinitec.algerieoffice.persistence.modal.companymaps.profile.CreditTaxe;

public interface CreditTaxeRepository extends JpaRepository<CreditTaxe, Long> {

	/**
	 * VERSION BEGIN 03/2021
	 * @param lines
	 */
	@Modifying
	@Query("delete from CreditTaxe c where c.id in :lines")
	void deleteLinesCreditTaxe(@Param("lines") List<Long> lines);
	
}
