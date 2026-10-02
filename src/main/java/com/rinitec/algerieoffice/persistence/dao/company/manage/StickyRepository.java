package com.rinitec.algerieoffice.persistence.dao.company.manage;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rinitec.algerieoffice.persistence.modal.company.manage.Sticky;

public interface StickyRepository extends JpaRepository<Sticky, Long> {

	/**
	 * VERSION BEGIN 03/2021
	 * @param urlExtern
	 * @return
	 */
	boolean existsByUrlExtern(String urlExtern);
	
}
