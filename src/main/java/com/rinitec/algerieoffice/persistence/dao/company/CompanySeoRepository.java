package com.rinitec.algerieoffice.persistence.dao.company;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.rinitec.algerieoffice.persistence.modal.company.CompanySeo;

public interface CompanySeoRepository extends JpaRepository<CompanySeo, Long> {

	/**
	 * VERSION BEGIN 03/2021
	 * @param url
	 * @return
	 */
	boolean existsByUrl(String url);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @return
	 */
	@Query("select c.url from CompanySeo c where c.companyId = ?1")
	Optional<String> findUrlById(Long companyId);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param url
	 * @param companyId
	 */
	@Modifying
	@Query("update CompanySeo c set c.url = ?1 where c.companyId = ?2")
	void updateUrlById(String url, Long companyId);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @return
	 */
	@Query("select c.tageline from CompanySeo c where c.companyId = ?1")
	Optional<String> findTagelineById(Long companyId);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @return
	 */
	@Query("select c.keysword from CompanySeo c where c.companyId = ?1")
	Optional<String> findKeyswordById(Long companyId);
	
}
