package com.rinitec.algerieoffice.persistence.dao.company.profile;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rinitec.algerieoffice.persistence.modal.company.profile.CompanyLinked;

public interface CompanyLinkedRepository extends JpaRepository<CompanyLinked, Long>, CompanyLinkedRepositoryCustom {
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param facebook
	 * @return
	 */
	boolean existsByFacebook(String facebook);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param twitter
	 * @return
	 */
	boolean existsByTwitter(String twitter);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param linkedin
	 * @return
	 */
	boolean existsByLinkedin(String linkedin);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param youtube
	 * @return
	 */
	boolean existsByYoutube(String youtube);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param google
	 * @return
	 */
	boolean existsByGoogle(String google);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param instagram
	 * @return
	 */
	boolean existsByInstagram(String instagram);
	
}
