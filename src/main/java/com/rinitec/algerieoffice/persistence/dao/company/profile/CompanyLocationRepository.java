package com.rinitec.algerieoffice.persistence.dao.company.profile;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rinitec.algerieoffice.persistence.modal.company.profile.CompanyLocation;

public interface CompanyLocationRepository extends JpaRepository<CompanyLocation, Long>, CompanyLocationRepositoryCustom {

	/**
	 * VERSION BEGIN 03/2021
	 * @param urlmap
	 * @return
	 */
	boolean existsByUrlmap(String urlmap);
	
}
