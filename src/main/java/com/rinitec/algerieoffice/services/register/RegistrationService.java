package com.rinitec.algerieoffice.services.register;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.rinitec.algerieoffice.enums.AvatarType;
import com.rinitec.algerieoffice.persistence.dao.admins.data.ActivityRepository;
import com.rinitec.algerieoffice.persistence.dao.company.CompanyAccountRepository;
import com.rinitec.algerieoffice.persistence.dao.company.CompanyAddressRepository;
import com.rinitec.algerieoffice.persistence.dao.company.CompanyRepository;
import com.rinitec.algerieoffice.persistence.dao.company.CompanySearchRepository;
import com.rinitec.algerieoffice.persistence.dao.company.CompanySeoRepository;
import com.rinitec.algerieoffice.persistence.dao.users.IdentityRepository;
import com.rinitec.algerieoffice.persistence.dao.users.RoleRepository;
import com.rinitec.algerieoffice.persistence.dao.users.UserRepository;
import com.rinitec.algerieoffice.persistence.dao.users.profiles.AccountRepository;
import com.rinitec.algerieoffice.persistence.modal.IdentityID;
import com.rinitec.algerieoffice.persistence.modal.admins.data.Activity;
import com.rinitec.algerieoffice.persistence.modal.company.Company;
import com.rinitec.algerieoffice.persistence.modal.company.CompanyAccount;
import com.rinitec.algerieoffice.persistence.modal.company.CompanyAddress;
import com.rinitec.algerieoffice.persistence.modal.company.CompanySearch;
import com.rinitec.algerieoffice.persistence.modal.company.CompanySeo;
import com.rinitec.algerieoffice.persistence.modal.users.Identity;
import com.rinitec.algerieoffice.persistence.modal.users.Role;
import com.rinitec.algerieoffice.persistence.modal.users.User;
import com.rinitec.algerieoffice.persistence.modal.users.profiles.Account;
import com.rinitec.algerieoffice.security.LocalUser;
import com.rinitec.algerieoffice.security.oauth2.client.OAuth2UserInfo;
import com.rinitec.algerieoffice.security.oauth2.client.OAuth2UserInfoFactory;
import com.rinitec.algerieoffice.services.medias.IAvatarService;
import com.rinitec.algerieoffice.utils.ParseUtil;
import com.rinitec.algerieoffice.utils.RequestUtil;
import com.rinitec.algerieoffice.web.error.exception.AlreadyExistException;
import com.rinitec.algerieoffice.web.error.exception.CompanymailExistException;
import com.rinitec.algerieoffice.web.error.exception.NotFoundException;
import com.rinitec.algerieoffice.web.error.exception.PhoneExistException;
import com.rinitec.algerieoffice.web.error.oauth2.OAuth2AuthenticationProcessingException;
import com.rinitec.algerieoffice.web.form.register.CompanyForm;
import com.rinitec.algerieoffice.web.form.register.UserForm;
import com.rinitec.algerieoffice.web.form.user.AddcompanyForm;

@Service
public class RegistrationService implements IRegistrationService {
	
	private HttpServletRequest request;
	private PasswordEncoder passwordEncoder;
	private UserRepository userRepository;
	private RoleRepository roleRepository;
	private AccountRepository accountRepository;
	private IdentityRepository identityRepository;
	private CompanyRepository companyRepository;
	private CompanySeoRepository companySeoRepository;
	private CompanyAccountRepository companyAccountRepository;
	private CompanyAddressRepository companyAddressRepository;
	private CompanySearchRepository companySearchRepository;
	private ActivityRepository activityRepository;
	private IAvatarService avatarService;
	
	@Autowired
	public RegistrationService(HttpServletRequest request, PasswordEncoder passwordEncoder, UserRepository userRepository, RoleRepository roleRepository, 
			AccountRepository accountRepository, IdentityRepository identityRepository, CompanyRepository companyRepository, CompanySeoRepository companySeoRepository, 
			CompanyAccountRepository companyAccountRepository, CompanyAddressRepository companyAddressRepository, CompanySearchRepository companySearchRepository, 
			ActivityRepository activityRepository, IAvatarService avatarService) {
		this.request = request;
		this.passwordEncoder = passwordEncoder;
		this.userRepository = userRepository;
		this.roleRepository = roleRepository;
		this.accountRepository = accountRepository;
		this.identityRepository = identityRepository;
		this.companyRepository = companyRepository;
		this.companySeoRepository = companySeoRepository;
		this.companyAccountRepository = companyAccountRepository;
		this.companyAddressRepository = companyAddressRepository;
		this.companySearchRepository = companySearchRepository;
		this.activityRepository = activityRepository;
		this.avatarService = avatarService;
	}
	
	@Transactional
	private final String generatePseudo(final String username) {
		String pseudo = ParseUtil.generatePseudo(10, username);
		while(accountRepository.existsByPseudo(pseudo)) {
			pseudo = ParseUtil.generatePseudo(10, username);
		}
		return pseudo;
	}
	
	@Transactional
	private final User createUser(final UserForm userForm) {
		final User user = new User();
		user.setFirstName(userForm.getFirstname());
		user.setLastName(userForm.getLastname());
		user.setEmail(userForm.getEmail());
		user.setPassword(passwordEncoder.encode(userForm.getPassword()));
		user.setRoles(new ArrayList<Role>(Arrays.asList(roleRepository.findByName("ROLE_ACCOUNT"), 
				roleRepository.findByName("ROLE_VISITOR"))));
		return userRepository.save(user);
	}
	
	@Transactional
	private final Account createAccount(final User user, final boolean hasAccepte) {
		final Account account = new Account();
		final String pseudo = generatePseudo(user.getDisplayName());
		account.setUserId(user.getId());
		account.setPseudo(pseudo);
		account.setCreateDate(new DateTime(Date.from(Instant.now())));
		account.setIp(RequestUtil.getClientIP(request));
		account.setHasAccepte(hasAccepte);
		if(user.isEnabled()) {
			account.setActivateDate(new DateTime(Date.from(Instant.now())));
		}
		return accountRepository.save(account);
	}
	
	@Override
	@Transactional
	public User registerNewUser(final UserForm userForm) {
		if(userRepository.existsByEmail(userForm.getEmail())) {
			throw new AlreadyExistException("message.error.emailexist");
		}
		final User user = createUser(userForm);
		createAccount(user, userForm.isHasAccepte());
		return user;
	}
	
	@Transactional
	private final Company createCompany(final CompanyForm companyForm) {
		final Company company = new Company();
		final Activity activity = activityRepository.findByCode(companyForm.getActivity());
		company.setDenomination(companyForm.getDenomination());
		company.setTradename(StringUtils.isEmpty(companyForm.getTradename()) ? companyForm.getDenomination() : companyForm.getTradename());
		company.setLang(companyForm.getLang());
		company.setPhone(companyForm.getPhone());
		company.setCompanymail(StringUtils.isEmpty(companyForm.getCompanymail()) ? companyForm.getEmail() : companyForm.getCompanymail());
		company.setHasAvatar(companyForm.isHasAvatar());
		company.setActivities(new ArrayList<Activity>(Arrays.asList(activity)));
		return companyRepository.save(company);
	}
	
	@Transactional
	private final CompanyAddress createCompanyAddress(final Company company, final String address, final String postal, final Integer wilaya) {
		final CompanyAddress companyAddress = new CompanyAddress();
		companyAddress.setAddress(address);
		companyAddress.setPostal(postal);
		companyAddress.setWilaya(wilaya);
		companyAddress.setCompany(company);
		return companyAddressRepository.save(companyAddress);
	}
	
	@Transactional
	private final CompanySeo createCompanySeo(final Company company, final String description) {
		final CompanySeo companySeo = new CompanySeo();
		companySeo.setDescription(description);
		companySeo.setCompany(company);
		return companySeoRepository.save(companySeo);
	}
	
	@Transactional
	private final CompanyAccount createCompanyAccount(final Long companyId, final Long userId) {
		final CompanyAccount companyAccount = new CompanyAccount();
		companyAccount.setCompanyId(companyId);
		companyAccount.setCreatedById(userId);
		companyAccount.setCreatedDate(new DateTime(Date.from(Instant.now())));
		companyAccount.setModifiedDate(new DateTime(Date.from(Instant.now())));
		return companyAccountRepository.save(companyAccount);
	}
	
	@Transactional
	private final CompanySearch createCompanySearch(final Long companyId) {
		final CompanySearch companySearch = new CompanySearch(companyId);
		return companySearchRepository.save(companySearch);
	}
	
	@Transactional
	private final User createUser(final Long companyId, final CompanyForm companyForm) {
		final User user = new User();
		user.setCompanyId(companyId);
		user.setFirstName(companyForm.getFirstname());
		user.setLastName(companyForm.getLastname());
		user.setEmail(companyForm.getEmail());
		user.setPassword(passwordEncoder.encode(companyForm.getPassword()));
		user.setRoles(new ArrayList<Role>(Arrays.asList(roleRepository.findByName("ROLE_ACCOUNT"), 
				roleRepository.findByName("ROLE_COMPANY_ADMIN"))));
		return userRepository.save(user);
	}
	
	@Override
	@Transactional
	public User registerNewCompany(final CompanyForm companyForm) {
		if(userRepository.existsByEmail(companyForm.getEmail()) ) {
			throw new AlreadyExistException("message.error.emailexist");
		}
		if(!activityRepository.existsByCode(companyForm.getActivity())) {
			throw new NotFoundException("message.input.notfound");
		}
		if(companyRepository.existsByPhone(companyForm.getPhone())) {
			throw new PhoneExistException("message.error.phoneexist");
		}
		if(StringUtils.isEmpty(companyForm.getCompanymail())) {
			if(companyRepository.existsByCompanymail(companyForm.getEmail())) {
				throw new AlreadyExistException("message.error.companymailexist");
			}
		} else if(companyRepository.existsByCompanymail(companyForm.getCompanymail())) {
			throw new CompanymailExistException("message.error.companymailexist");
		}
		final Company company = createCompany(companyForm);
		final User user = createUser(company.getId(), companyForm);
		createCompanyAddress(company, companyForm.getAddress(), companyForm.getPostal(), companyForm.getWilaya());
		createCompanySeo(company, companyForm.getDescription());
		createCompanyAccount(company.getId(), user.getId());
		createCompanySearch(company.getId());
		createAccount(user, companyForm.isHasAccepte());
		if(companyForm.isHasAvatar()) {
			avatarService.postOrUpdate(companyForm.getFile(), company.getId(), AvatarType.company);
		}
		return user;
	}
	
	@Transactional
	private final Company addCompany(final AddcompanyForm addcompanyForm) {
		final Company company = new Company();
		final Activity activity = activityRepository.findByCode(addcompanyForm.getActivity());
		company.setDenomination(addcompanyForm.getDenomination());
		company.setTradename(StringUtils.isEmpty(addcompanyForm.getTradename()) ? addcompanyForm.getDenomination() : addcompanyForm.getTradename());
		company.setLang(addcompanyForm.getLang());
		company.setPhone(addcompanyForm.getPhone());
		company.setCompanymail(addcompanyForm.getEmail());
		company.setHasAvatar(addcompanyForm.isHasAvatar());
		company.setActivities(new ArrayList<Activity>(Arrays.asList(activity)));
		return companyRepository.save(company);
	}
	
	@Transactional
	private final User updateUserCompany(final String email, final Long companyId) {
		final User user = userRepository.findByEmail(email);
		user.setCompanyId(companyId);
		user.setRoles(new ArrayList<Role>(Arrays.asList(roleRepository.findByName("ROLE_ACCOUNT"), 
				roleRepository.findByName("ROLE_COMPANY_ADMIN"))));
		return userRepository.save(user);
	}
	
	@Override
	@Transactional
	public User addNewCompany(final User user, final AddcompanyForm addcompanyForm) {
		if(!activityRepository.existsByCode(addcompanyForm.getActivity())) {
			throw new NotFoundException("message.input.notfound");
		}
		if(companyRepository.existsByPhone(addcompanyForm.getPhone())) {
			throw new PhoneExistException("message.error.phoneexist");
		}
		if(companyRepository.existsByCompanymail(addcompanyForm.getEmail())) {
			throw new AlreadyExistException("message.error.companymailexist");
		}
		final Company company = addCompany(addcompanyForm);
		createCompanyAddress(company, addcompanyForm.getAddress(), addcompanyForm.getPostal(), addcompanyForm.getWilaya());
		createCompanySeo(company, addcompanyForm.getDescription());
		createCompanyAccount(company.getId(), user.getId());
		createCompanySearch(company.getId());
		if(addcompanyForm.isHasAvatar()) {
			avatarService.postOrUpdate(addcompanyForm.getFile(), company.getId(), AvatarType.company);
		}
		return updateUserCompany(user.getEmail(), company.getId());
	}
	
	@Transactional
	private final User createUser(final OAuth2UserInfo oAuth2UserInfo, final String randomPassword) {
		final User user = new User();
		final String firstName = oAuth2UserInfo.getFirstname();
		final String lastName = oAuth2UserInfo.getLastname();
		user.setFirstName(firstName.length() > 30 ? firstName.subSequence(0, 30).toString() : firstName);
		user.setLastName(lastName.length() > 30 ? lastName.subSequence(0, 30).toString() : lastName);
		user.setEmail(oAuth2UserInfo.getEmail());
		user.setRandomPassword(randomPassword);
		user.setPassword(passwordEncoder.encode(randomPassword));
		user.setRoles(new ArrayList<Role>(Arrays.asList(roleRepository.findByName("ROLE_ACCOUNT"), 
				roleRepository.findByName("ROLE_VISITOR"))));
		return userRepository.save(user);
	}
	
	@Transactional
	private final Account createOrActivateAccount(final User user) {
		final Optional<Account> uOptional = accountRepository.findById(user.getId());
		if(uOptional.isPresent()) {
			final Account account = uOptional.get();
			if(account.getActivateDate() == null) {
				account.setActivateDate(new DateTime(Date.from(Instant.now())));
			} else {
				account.setNumberOfVisits(account.getNumberOfVisits() + 1);
				account.setLastLoginDate(new DateTime(Date.from(Instant.now())));
			}
			return accountRepository.save(account);
		} else {
			return createAccount(user, false);
		}
	}
	
	@Transactional
	private final Identity createOrUpdateIdentity(final Long userId, final String provider, final OAuth2UserInfo oAuth2UserInfo) {
		final IdentityID identityID = new IdentityID(userId, provider);
		Identity identity = identityRepository.findByIdentityID(identityID);
		if(identity == null) {
			identity = new Identity();
			identity.setIdentityID(identityID);
			identity.setUsermail(oAuth2UserInfo.getEmail());
			identity.setDisplayname(oAuth2UserInfo.getDisplayName());
			identity.setImageurl(oAuth2UserInfo.getImageUrl());
			return identityRepository.save(identity);
		}
		boolean hasChangedIdentity = false;
		if(!identity.getUsermail().equals(oAuth2UserInfo.getEmail())) {
			identity.setUsermail(oAuth2UserInfo.getEmail());
			hasChangedIdentity = true;
		}
		if(!identity.getDisplayname().equals(oAuth2UserInfo.getDisplayName())) {
			identity.setDisplayname(oAuth2UserInfo.getDisplayName());
			hasChangedIdentity = true;
		}
		if(!StringUtils.isEmpty(identity.getImageurl()) && !identity.getImageurl().equals(oAuth2UserInfo.getImageUrl())) {
			identity.setImageurl(oAuth2UserInfo.getImageUrl());
			hasChangedIdentity = true;
		}
		if(hasChangedIdentity) {
			return identityRepository.save(identity);
		}
		return identity;
	}
	
	private final IdentityID findCurrIdentity(final List<Identity> identities, final String provider) {
		for (final Identity identity : identities) {
			if(identity.getIdentityID().getProvider().equals(provider)) {
				return identity.getIdentityID();
			}
		}
		return null;
	}
	
	@Transactional
	private final LocalUser identifyRemember(final Long userId, final String provider, final OAuth2UserInfo oAuth2UserInfo, 
			final List<Identity> identities) {
		RequestUtil.destroyedRemember(request);
		final User user = userRepository.findById(userId).get();
		final IdentityID identityID = findCurrIdentity(identities, provider);
		if(identities.isEmpty() || identityID == null || identityID.getUserId().equals(userId)) {
			createOrUpdateIdentity(userId, provider, oAuth2UserInfo);
		}
		return LocalUser.build(user);
	}
	
	@Override
	@Transactional
	public LocalUser registerOrLogin(final String provider, final Map<String, Object> attributes, 
			final OidcIdToken idToken, final OidcUserInfo userInfo) {
		final OAuth2UserInfo oAuth2UserInfo = OAuth2UserInfoFactory.getOAuth2UserInfo(provider, attributes);
		if (StringUtils.isEmpty(oAuth2UserInfo.getDisplayName())) {
			throw new OAuth2AuthenticationProcessingException("auth.oauth2.nameNotfound");
		}
		if (StringUtils.isEmpty(oAuth2UserInfo.getEmail())) {
			throw new OAuth2AuthenticationProcessingException("auth.oauth2.mailNotfound");
		}
		final List<Identity> identities = identityRepository.findByUsermail(oAuth2UserInfo.getEmail());
		final Long userId = RequestUtil.getRemembredIdentity(request);
		if(userId != null) {
			return identifyRemember(userId, provider, oAuth2UserInfo, identities);
		}
		User user = null;
		if(identities.isEmpty()) {
			user = userRepository.findByEmail(oAuth2UserInfo.getEmail());
		} else {
			final IdentityID identityID = findCurrIdentity(identities, provider);
			if(identityID != null) {
				final Optional<User> uOptional = userRepository.findById(identityID.getUserId());
				if(uOptional.isPresent()) {
					user = uOptional.get();
				}
			}
		}
		if(user == null) {
			final String randomPassword = ParseUtil.generatePassword(8);
			user = createUser(oAuth2UserInfo, randomPassword);
		}
		if(!user.isEnabled()) {
			user.setEnabled(true);
			user = userRepository.save(user);
		}
		createOrActivateAccount(user);
		createOrUpdateIdentity(user.getId(), provider, oAuth2UserInfo);
		return LocalUser.create(user, attributes, idToken, userInfo);
	}
	
}
