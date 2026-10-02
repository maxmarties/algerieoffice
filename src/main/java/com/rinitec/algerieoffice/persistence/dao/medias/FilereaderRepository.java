package com.rinitec.algerieoffice.persistence.dao.medias;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.rinitec.algerieoffice.persistence.modal.medias.Filereader;

public interface FilereaderRepository extends JpaRepository<Filereader, UUID>, FilereaderRepositoryCustom {

	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 */
	void deleteByCompanyId(Long companyId);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param fileId
	 * @return
	 */
	@Query("select f.filename from Filereader f where f.id = ?1")
	Optional<String> findFilenameByFilereaderId(UUID fileId);
	
}
