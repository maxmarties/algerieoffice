package com.rinitec.algerieoffice.persistence.dao.admins.ads;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rinitec.algerieoffice.persistence.modal.admins.ads.Newsletter;

public interface NewsletterRepository extends JpaRepository<Newsletter, Long>, NewsletterRepositoryCustom {

	/**
	 * VERSION BEGIN 03/2021
	 * @param email
	 * @return
	 */
	boolean existsByEmail(String email);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param lines
	 * @return
	 */
	@Query("select count(n.id) from Newsletter n where n.id in :lines")
	long countNewsletter(@Param("lines") List<Long> lines);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param lines
	 */
	@Modifying
	@Query("delete from Newsletter n where n.id in :lines")
	void deleteNewsletters(@Param("lines") List<Long> lines);
	
}
