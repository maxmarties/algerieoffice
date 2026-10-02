package com.rinitec.algerieoffice.persistence.dao.token;

import org.joda.time.DateTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.rinitec.algerieoffice.persistence.modal.token.PhoneToken;
import com.rinitec.algerieoffice.persistence.modal.users.profiles.Profile;

public interface PhoneTokenRepository extends JpaRepository<PhoneToken, Long> {

	/**
	 * VERSION BEGIN 03/2021
	 * @param token
	 * @return
	 */
	PhoneToken findByToken(String token);

	/**
	 * VERSION BEGIN 03/2021
	 * @param profile
	 * @return
	 */
	PhoneToken findByProfile(Profile profile);
    
	/**
	 * VERSION BEGIN 03/2021
	 * @param now
	 */
    @Modifying
    @Query("delete from PhoneToken p where p.expiryDate <= ?1")
    void deleteAllExpiredSince(DateTime now);
    
    /**
     * VERSION BEGIN 03/2021
     * @param profile
     */
    void deleteByProfile(Profile profile);
	
}
