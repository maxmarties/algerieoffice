package com.rinitec.algerieoffice.persistence.dao.users.profiles;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.rinitec.algerieoffice.persistence.modal.users.profiles.Profile;

public interface ProfileRepository extends JpaRepository<Profile, Long> {

	/**
	 * VERSION BEGIN 03/2021
	 * @param sexe
	 * @return
	 */
	long countBySexe(Boolean sexe);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param phone
	 * @return
	 */
	boolean existsByPhone(String phone);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param website
	 * @return
	 */
	boolean existsByWebsite(String website);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param userId
	 * @return
	 */
	@Query("select p.phone from Profile p where p.userId = ?1")
	Optional<String> findPhoneByUserId(Long userId);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param userId
	 * @return
	 */
	@Query("select p.function from Profile p where p.userId = ?1")
	Optional<String> findFunctionByUserId(Long userId);
	
}
