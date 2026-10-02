package com.rinitec.algerieoffice.services.token;

import com.rinitec.algerieoffice.persistence.modal.token.PhoneToken;
import com.rinitec.algerieoffice.persistence.modal.token.VerificationToken;
import com.rinitec.algerieoffice.persistence.modal.users.User;
import com.rinitec.algerieoffice.web.error.exception.InvalidImageException;

public interface ITokenService {
	public static final String TOKEN_INVALID = "invalid";
    public static final String TOKEN_EXPIRED = "expired";
    public static final String TOKEN_VALID = "valide";
    public static final String TOKEN_ENABLED = "enabled";

    /**
     * VERSION BEGIN 03/2021
     * @param user
     * @param token
     * @return
     */
	VerificationToken createVerificationTokenForUser(User user, String token);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param user
	 * @return
	 */
	VerificationToken getVerificationToken(User user);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param token
	 * @return
	 */
	VerificationToken generateNewVerificationToken(String token);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param token
	 * @return
	 */
	User getUserByVerificationToken(String token);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param token
	 * @return
	 */
	String validateVerificationToken(String token);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param user
	 * @param token
	 */
	void createPasswordResetToken(User user, String token);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param userId
	 * @param token
	 * @return
	 */
	String validatePasswordResetToken(Long userId, String token);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param userId
	 * @param token
	 * @return
	 */
	PhoneToken createPhoneTokenForUser(Long userId, String token);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param userId
	 * @param token
	 * @return
	 * @throws InvalidImageException
	 */
	String validatePhoneToken(Long userId, String token) throws InvalidImageException;
	
}
