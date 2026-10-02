package com.rinitec.algerieoffice.persistence.dao.medias;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rinitec.algerieoffice.persistence.modal.medias.Photo;

public interface PhotoRepository extends JpaRepository<Photo, UUID> {
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 */
	void deleteByCompanyId(Long companyId);
	
}
