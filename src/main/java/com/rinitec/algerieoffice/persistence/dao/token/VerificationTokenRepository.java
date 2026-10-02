package com.rinitec.algerieoffice.persistence.dao.token;

import org.joda.time.DateTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.rinitec.algerieoffice.persistence.modal.token.VerificationToken;
import com.rinitec.algerieoffice.persistence.modal.users.User;

public interface VerificationTokenRepository extends JpaRepository<VerificationToken, Long> {

	/**
	 * VERSION BEGIN 03/2021
	 * @param token
	 * @return
	 */
	VerificationToken findByToken(String token);

	/**
	 * VERSION BEGIN 03/2021
	 * @param user
	 * @return
	 */
    VerificationToken findByUser(User user);
    
    /**
     * VERSION BEGIN 03/2021
     * @param now
     */
    @Modifying
    @Query("delete from VerificationToken t where t.expiryDate <= ?1")
    void deleteAllExpiredSince(DateTime now);
    
    /**
     * VERSION BEGIN 03/2021
     * @param user
     */
    void deleteByUser(User user);
	
}
