package com.rinitec.algerieoffice.persistence.dao.company;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rinitec.algerieoffice.persistence.modal.company.Company;

public interface CompanyRepository extends JpaRepository<Company, Long>, CompanyRepositoryCustom {
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param enabled
	 * @return
	 */
	long countByEnabled(boolean enabled);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param lang
	 * @return
	 */
	long countByLang(String lang);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param phone
	 * @return
	 */
	boolean existsByPhone(String phone);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param companymail
	 * @return
	 */
	boolean existsByCompanymail(String companymail);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param companymail
	 * @return
	 */
	Company findByCompanymail(String companymail);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param id
	 * @return
	 */
	@Query("select c.tradename from Company c where c.id = ?1")
	Optional<String> findTradenameById(Long id);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param id
	 * @return
	 */
	@Query("select c.phone from Company c where c.id = ?1")
	Optional<String> findPhoneById(Long id);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param id
	 * @return
	 */
	@Query("select c.companymail from Company c where c.id = ?1")
	Optional<String> findCompanymailById(Long id);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param id
	 * @return
	 */
	@Query("select c.hasAvatar from Company c where c.id = ?1")
	Optional<Boolean> findHasAvatarById(Long id);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param id
	 * @return
	 */
	@Query("select c.id from Company c where c.id = ?1 and c.enabled = true and c.locked = false")
	Optional<Long> findCompanyIdApprouved(Long id);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param lines
	 * @return
	 */
	@Query("select c.companymail from Company c where c.id in :lines")
	List<String> findAllEmailByCompaniesId(@Param("lines") List<Long> lines);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param hasAvatar
	 * @param id
	 */
	@Modifying
	@Query("update Company c set c.hasAvatar = ?1 where c.id = ?2")
	void updateHasAvatarById(Boolean hasAvatar, Long id);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param companymail
	 * @param id
	 */
	@Modifying
	@Query("update Company c set c.companymail = ?1 where c.id = ?2")
	void updateCompanymailById(String companymail, Long id);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param phone
	 * @param id
	 */
	@Modifying
	@Query("update Company c set c.phone = ?1 where c.id = ?2")
	void updatePhoneById(String phone, Long id);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param enabled
	 * @param id
	 */
	@Modifying
	@Query("update Company c set c.enabled = ?1 where c.id = ?2")
	void updateEnabledById(boolean enabled, Long id);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param locked
	 * @param lines
	 */
	@Modifying
	@Query("update Company c set c.locked = :locked where c.id in :lines")
	void updateLockedByIds(@Param("locked") boolean locked, @Param("lines") List<Long> lines);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param id
	 */
	@Modifying
	@Query("update Company c set c.enabled = false where c.id = ?1")
	void deactiavteCompany(Long id);
	
}
