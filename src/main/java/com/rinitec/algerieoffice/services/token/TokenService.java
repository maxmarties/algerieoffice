package com.rinitec.algerieoffice.services.token;

import java.util.Calendar;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rinitec.algerieoffice.persistence.dao.token.PasswordResetTokenRepository;
import com.rinitec.algerieoffice.persistence.dao.token.PhoneTokenRepository;
import com.rinitec.algerieoffice.persistence.dao.token.VerificationTokenRepository;
import com.rinitec.algerieoffice.persistence.dao.users.UserRepository;
import com.rinitec.algerieoffice.persistence.dao.users.profiles.ProfileRepository;
import com.rinitec.algerieoffice.persistence.modal.token.PasswordResetToken;
import com.rinitec.algerieoffice.persistence.modal.token.PhoneToken;
import com.rinitec.algerieoffice.persistence.modal.token.VerificationToken;
import com.rinitec.algerieoffice.persistence.modal.users.User;
import com.rinitec.algerieoffice.persistence.modal.users.profiles.Profile;
import com.rinitec.algerieoffice.utils.SecurityUtil;
import com.rinitec.algerieoffice.web.error.exception.InvalidImageException;

@Service
public class TokenService implements ITokenService {

	private UserRepository userRepository;
	private ProfileRepository profileRepository;
	private VerificationTokenRepository tokenRepository;
	private PasswordResetTokenRepository passwordRepository;
	private PhoneTokenRepository phoneTokenRepository;
	
	@Autowired
	public TokenService(UserRepository userRepository, ProfileRepository profileRepository, 
			VerificationTokenRepository tokenRepository, PasswordResetTokenRepository passwordRepository, 
			PhoneTokenRepository phoneTokenRepository) {
		this.userRepository = userRepository;
		this.profileRepository = profileRepository;
		this.tokenRepository = tokenRepository;
		this.passwordRepository = passwordRepository;
		this.phoneTokenRepository = phoneTokenRepository;
	}
	
	@Override
	@Transactional
	public VerificationToken createVerificationTokenForUser(final User user, final String token) {
		final VerificationToken verificationToken = new VerificationToken(token, user);
		return tokenRepository.save(verificationToken);
	}
	
	@Override
	@Transactional(readOnly = true)
	public VerificationToken getVerificationToken(final User user) {
		return tokenRepository.findByUser(user);
	}
	
	@Override
	@Transactional
	public VerificationToken generateNewVerificationToken(final String token) {
		final VerificationToken verificationToken = tokenRepository.findByToken(token);
		verificationToken.updateToken(UUID.randomUUID().toString());
    	return tokenRepository.save(verificationToken);
	}
	
	@Override
	@Transactional(readOnly = true)
	public User getUserByVerificationToken(final String token) {
		final VerificationToken verificationToken = tokenRepository.findByToken(token);
    	return verificationToken != null ? verificationToken.getUser() : null;
	}
	
	@Override
	@Transactional
	public String validateVerificationToken(final String token) {
		final VerificationToken verificationToken = tokenRepository.findByToken(token);
		if (verificationToken == null) {
            return TOKEN_INVALID;
        }
		final User user = verificationToken.getUser();
    	if(user.isEnabled()) {
			return TOKEN_ENABLED;
		}
    	final Calendar cal = Calendar.getInstance();
		if ((verificationToken.getExpiryDate().toDate().getTime() - cal.getTime().getTime()) <= 0) {
            return TOKEN_EXPIRED;
		}
		user.setEnabled(true);
		userRepository.save(user);
		return TOKEN_VALID;
	}
	
	@Override
	@Transactional
	public void createPasswordResetToken(final User user, final String token) {
		final PasswordResetToken passwordToken = new PasswordResetToken(token, user);
    	passwordRepository.save(passwordToken);
	}
	
	@Override
	@Transactional
	public String validatePasswordResetToken(final Long userId, final String token) {
		final PasswordResetToken passToken = passwordRepository.findByToken(token);
		if (passToken == null || !passToken.getUser().getId().equals(userId)) {
			return TOKEN_INVALID;
		}
		final Calendar cal = Calendar.getInstance();
		if ((passToken.getExpiryDate().toDate().getTime() - cal.getTime().getTime()) <= 0) {
			passwordRepository.delete(passToken);
			return TOKEN_EXPIRED;
		}
		SecurityUtil.authUpdatePassword(passToken.getUser());
    	return TOKEN_VALID;
	}
	
	@Override
	@Transactional
	public PhoneToken createPhoneTokenForUser(final Long userId, final String token) {
		final Optional<Profile> uOptional = profileRepository.findById(userId);
		if(uOptional.isPresent()) {
			final Profile profile = uOptional.get();
			final PhoneToken phoneToken = new PhoneToken(token, profile);
			return phoneTokenRepository.save(phoneToken);
		}
		return null;
	}
	
	@Override
	@Transactional
	public String validatePhoneToken(final Long userId, final String token) {
		final Optional<Profile> uOptional = profileRepository.findById(userId);
		if(uOptional.isPresent()) {
			final Profile profile = uOptional.get();
			final PhoneToken phoneToken = phoneTokenRepository.findByToken(token);
			if(phoneToken == null || !phoneToken.getProfile().getUserId().equals(userId)) {
				throw new InvalidImageException("message.input.invalid");
			}
			final Calendar cal = Calendar.getInstance();
			if ((phoneToken.getExpiryDate().toDate().getTime() - cal.getTime().getTime()) <= 0) {
				phoneTokenRepository.delete(phoneToken);
				throw new InvalidImageException("message.input.code");
			}
			profile.setEnabled(true);
			profileRepository.save(profile);
			return TOKEN_VALID;
		}
		return null;
	}
	
}
