package com.rinitec.algerieoffice.persistence.dao.token;

import org.joda.time.DateTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.rinitec.algerieoffice.persistence.modal.token.PasswordResetToken;
import com.rinitec.algerieoffice.persistence.modal.users.User;

public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Long> {

	/**
	 * VERSION BEGIN 03/2021
	 * @param token
	 * @return
	 */
	PasswordResetToken findByToken(String token);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param user
	 * @return
	 */
	PasswordResetToken findByUser(User user);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param now
	 */
	@Modifying
    @Query("delete from PasswordResetToken t where t.expiryDate <= ?1")
    void deleteAllExpiredSince(DateTime now);
    
	/**
	 * VERSION BEGIN 03/2021
	 * @param user
	 */
    void deleteByUser(User user);
	
}
