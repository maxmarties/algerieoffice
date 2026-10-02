package com.rinitec.algerieoffice.persistence.dao.users.feedback;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rinitec.algerieoffice.persistence.modal.users.feedback.Contact;

public interface ContactRepository extends JpaRepository<Contact, UUID>, ContactRepositoryCustom {

	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 */
	void deleteByCompanyId(Long companyId);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @param lines
	 * @return
	 */
	@Query("select count(c.id) from Contact c where c.companyId = :companyId and c.id in :lines")
	long countContacts(@Param("companyId") Long companyId, @Param("lines") List<UUID> lines);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param lines
	 * @return
	 */
	@Query("select count(c.id) from Contact c where c.id in :lines")
	long countContacts(@Param("lines") List<UUID> lines);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param lines
	 */
	@Modifying
	@Query("delete from Contact c where c.id in :lines")
	void deleteContacts(@Param("lines") List<UUID> lines);
	
}
