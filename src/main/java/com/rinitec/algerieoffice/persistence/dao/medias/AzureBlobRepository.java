package com.rinitec.algerieoffice.persistence.dao.medias;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.rinitec.algerieoffice.persistence.modal.medias.AzureBlob;

public interface AzureBlobRepository extends JpaRepository<AzureBlob, UUID> {
	
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
	 * @param filename
	 * @return
	 */
	AzureBlob findByFilename(String filename);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param srcname
	 * @return
	 */
	AzureBlob findBySrcname(String srcname);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param filename
	 * @return
	 */
	@Query("select a.srcname from AzureBlob a where a.filename = ?1")
	Optional<String> findSrcnameByFilename(String filename);
	
}
