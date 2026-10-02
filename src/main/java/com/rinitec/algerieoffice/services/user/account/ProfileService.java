package com.rinitec.algerieoffice.services.user.account;

import java.util.Optional;

import org.joda.time.format.DateTimeFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.rinitec.algerieoffice.enums.AvatarType;
import com.rinitec.algerieoffice.enums.SocialProvider;
import com.rinitec.algerieoffice.persistence.dao.users.IdentityRepository;
import com.rinitec.algerieoffice.persistence.dao.users.UserRepository;
import com.rinitec.algerieoffice.persistence.dao.users.profiles.AccountRepository;
import com.rinitec.algerieoffice.persistence.dao.users.profiles.ProfileRepository;
import com.rinitec.algerieoffice.persistence.modal.IdentityID;
import com.rinitec.algerieoffice.persistence.modal.users.Identity;
import com.rinitec.algerieoffice.persistence.modal.users.User;
import com.rinitec.algerieoffice.persistence.modal.users.profiles.Account;
import com.rinitec.algerieoffice.persistence.modal.users.profiles.Profile;
import com.rinitec.algerieoffice.services.medias.IAvatarService;
import com.rinitec.algerieoffice.web.error.exception.AlreadyExistException;
import com.rinitec.algerieoffice.web.error.exception.PhoneExistException;
import com.rinitec.algerieoffice.web.error.exception.UrlUnavailableException;
import com.rinitec.algerieoffice.web.form.ConstraintesURL;
import com.rinitec.algerieoffice.web.form.user.account.CoordinatesForm;
import com.rinitec.algerieoffice.web.form.user.account.IdentitiesForm;
import com.rinitec.algerieoffice.web.form.user.account.LoginForm;
import com.rinitec.algerieoffice.web.modal.user.account.IdentityProvider;

@Service
public class ProfileService implements IProfileService {

	private PasswordEncoder passwordEncoder;
	private UserRepository userRepository;
	private AccountRepository accountRepository;
	private ProfileRepository profileRepository;
	private IdentityRepository identityRepository;
	private IAvatarService avatarService;
	
	@Autowired
	public ProfileService(PasswordEncoder passwordEncoder, UserRepository userRepository, 
			AccountRepository accountRepository, ProfileRepository profileRepository,  
			IdentityRepository identityRepository, IAvatarService avatarService) {
		this.passwordEncoder = passwordEncoder;
		this.userRepository = userRepository;
		this.accountRepository = accountRepository;
		this.profileRepository = profileRepository;
		this.identityRepository = identityRepository;
		this.avatarService = avatarService;
	}
	
	@Override
	@Transactional(readOnly = true)
	public CoordinatesForm readCoordinatesForm(final Long userId) {
		final CoordinatesForm profileForm = new CoordinatesForm();
		final Optional<Profile> uOptional = profileRepository.findById(userId);
		if(uOptional.isPresent()) {
			final Profile profile = uOptional.get();
			profileForm.setSexe(profile.getSexe());
			profileForm.setBirthDate(DateTimeFormat.forPattern("dd/MM/yyyy").print(profile.getBirthDate()));
			profileForm.setFunction(profile.getFunction());
			profileForm.setBiography(profile.getBiography());
			profileForm.setAddress(profile.getAddress());
			profileForm.setPostal(profile.getPostal());
			profileForm.setWilaya(profile.getWilaya());
			profileForm.setPhone(profile.getPhone());
			profileForm.setWebsite(profile.getWebsite());
			profileForm.setHasPhone(profile.getHasPhone());
		}
		profileForm.setId(userId);
		return profileForm;
	}
	
	@Override
	@Transactional
	public Profile updateProfile(final CoordinatesForm coordinatesForm) {
		final Optional<Profile> uOptional = profileRepository.findById(coordinatesForm.getId());
		final Profile profile = uOptional.isPresent() ? uOptional.get() : new Profile(coordinatesForm.getId());
		if(!coordinatesForm.getPhone().equals(profile.getPhone()) && profileRepository.existsByPhone(coordinatesForm.getPhone())) {
			throw new PhoneExistException("message.error.alreadyexist");
		}
		if(!StringUtils.isEmpty(coordinatesForm.getWebsite()) && !coordinatesForm.getWebsite().equalsIgnoreCase(profile.getWebsite()) 
				&& profileRepository.existsByWebsite(coordinatesForm.getWebsite())) {
			throw new AlreadyExistException("message.error.alreadyexist");
		}
		profile.setSexe(coordinatesForm.getSexe());
		profile.setBirthDate(DateTimeFormat.forPattern("dd/MM/yyyy").parseDateTime(coordinatesForm.getBirthDate()));
		profile.setFunction(coordinatesForm.getFunction());
		profile.setBiography(coordinatesForm.getBiography());
		profile.setAddress(coordinatesForm.getAddress());
		profile.setPostal(coordinatesForm.getPostal());
		profile.setWilaya(coordinatesForm.getWilaya());
		profile.setPhone(coordinatesForm.getPhone());
		profile.setHasPhone(coordinatesForm.isHasPhone());
		profile.setWebsite(!StringUtils.isEmpty(coordinatesForm.getWebsite()) ? coordinatesForm.getWebsite() : null);
		if(!coordinatesForm.getPhone().equals(profile.getPhone())) {
			profile.setEnabled(false);
		}
		return profileRepository.save(profile);
	}
	
	@Transactional(readOnly = true)
	private final IdentityProvider readIdentityProvider(final Long userId, final String provider) {
		final IdentityID identityID = new IdentityID(userId, provider);
		final Identity identity = identityRepository.findByIdentityID(identityID);
		return identity != null ? new IdentityProvider(identity) : null;
	}
	
	@Override
	@Transactional(readOnly = true)
	public IdentitiesForm readIdentitiesForm(final User user) {
		final IdentitiesForm identitiesForm = new IdentitiesForm();
		final Optional<Profile> uOptional = profileRepository.findById(user.getId());
		final Profile profile = uOptional.isPresent() ? uOptional.get() : null;
		identitiesForm.setId(user.getId());
		identitiesForm.setEmail(user.getEmail());
		identitiesForm.setPhone(profile != null ? profile.getPhone() : null);
		identitiesForm.getSocial()[0] = readIdentityProvider(user.getId(), SocialProvider.FACEBOOK.getProviderType());
		identitiesForm.getSocial()[1] = readIdentityProvider(user.getId(), SocialProvider.GOOGLE.getProviderType());
		identitiesForm.getSocial()[2] = readIdentityProvider(user.getId(), SocialProvider.LINKEDIN.getProviderType());
		identitiesForm.setEnabledEmail(user.isEnabled());
		identitiesForm.setEnabledPhone(profile != null ? profile.isEnabled() : false);
		return identitiesForm;
	}
	
	@Override
	@Transactional
	public void deleteIdentity(final Long userId, final String provider) {
		final IdentityID identityID = new IdentityID(userId, provider);
		identityRepository.deleteById(identityID);
	}
	
	@Override
	@Transactional(readOnly = true)
	public String getPhoneProfile(final Long userId) {
		final Optional<String> uOptional = profileRepository.findPhoneByUserId(userId);
		return uOptional.isPresent() ? uOptional.get() : null;
	}
	
	@Override
	@Transactional(readOnly = true)
	public boolean existsByPseudo(final String pseudo) {
		return accountRepository.existsByPseudo(pseudo);
	}
	
	@Override
	@Transactional(readOnly = true)
	public LoginForm readLoginForm(final User user) {
		final Account account = accountRepository.findById(user.getId()).get();
		final LoginForm loginForm = new LoginForm();
		loginForm.setId(user.getId());
		loginForm.setPseudo(account.getPseudo());
		loginForm.setCheckedPseudo(account.getPseudo());
		loginForm.setFirstname(user.getFirstName());
		loginForm.setLastname(user.getLastName());
		loginForm.setEmail(user.getEmail());
		loginForm.setHasAvatar(user.getHasAvatar());
		loginForm.setUrlAvatar(user.getHasAvatar() 
				? ConstraintesURL.URL_AVATARS + "?postedId=" + user.getId() + "&type=" + AvatarType.account 
				: "/static/picts/avatars/account-min.jpg");
		loginForm.setHasAccepte(account.getHasAccepte());
		return loginForm;
	}
	
	@Transactional
	private final Account updateAccount(final Account account, final LoginForm loginForm) {
		account.setPseudo(loginForm.getPseudo());
		account.setHasAccepte(loginForm.isHasAccepte());
		return accountRepository.save(account);
	}
	
	@Override
	@Transactional
	public User updateLogin(final LoginForm loginForm, final User user) {
		final Account account = accountRepository.findById(user.getId()).get();
		if(!loginForm.getPseudo().equals(account.getPseudo()) && accountRepository.existsByPseudo(loginForm.getPseudo())) {
			throw new UrlUnavailableException("message.error.url");
		}
		if(!loginForm.getEmail().equalsIgnoreCase(user.getEmail())) {
			if(userRepository.existsByEmail(loginForm.getEmail())) {
				throw new AlreadyExistException("message.error.emailexist");
			}
			user.setEmail(loginForm.getEmail());
			user.setEnabled(false);
		}
		if(!StringUtils.isEmpty(loginForm.getNewpassword())) {
			user.setPassword(passwordEncoder.encode(loginForm.getNewpassword()));
		}
		if(loginForm.isHasFileChanged()) {
			if(loginForm.isHasAvatar()) {
				avatarService.postOrUpdate(loginForm.getFile(), user.getId(), AvatarType.account);
			} else if(user.getHasAvatar()) {
				avatarService.deleteAvatar(user.getId(), AvatarType.account);
			}
			user.setHasAvatar(loginForm.isHasAvatar());
		}
		user.setFirstName(loginForm.getFirstname());
		user.setLastName(loginForm.getLastname());
		updateAccount(account, loginForm);
		return userRepository.save(user);
	}
	
}
