package com.rinitec.algerieoffice.services.company.team;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rinitec.algerieoffice.enums.AvatarType;
import com.rinitec.algerieoffice.persistence.dao.company.CompanyAccountRepository;
import com.rinitec.algerieoffice.persistence.dao.company.manage.PreferencesRepository;
import com.rinitec.algerieoffice.persistence.dao.company.team.AgentRepository;
import com.rinitec.algerieoffice.persistence.dao.company.team.GuestRepository;
import com.rinitec.algerieoffice.persistence.dao.users.RoleRepository;
import com.rinitec.algerieoffice.persistence.dao.users.UserRepository;
import com.rinitec.algerieoffice.persistence.dao.users.profiles.AccountRepository;
import com.rinitec.algerieoffice.persistence.modal.company.manage.Preferences;
import com.rinitec.algerieoffice.persistence.modal.users.Role;
import com.rinitec.algerieoffice.persistence.modal.users.User;
import com.rinitec.algerieoffice.persistence.modal.users.profiles.Account;
import com.rinitec.algerieoffice.services.medias.IAvatarService;
import com.rinitec.algerieoffice.utils.ParseUtil;
import com.rinitec.algerieoffice.utils.RequestUtil;
import com.rinitec.algerieoffice.web.error.exception.AlreadyExistException;
import com.rinitec.algerieoffice.web.error.exception.NotFoundException;
import com.rinitec.algerieoffice.web.form.ConstraintesURL;
import com.rinitec.algerieoffice.web.form.company.team.TeamuserForm;
import com.rinitec.algerieoffice.web.modal.admins.ElementsList;
import com.rinitec.algerieoffice.web.modal.company.team.UserLine;

@Service
public class TeamService implements ITeamService {

	private HttpServletRequest request;
	private PasswordEncoder passwordEncoder;
	private UserRepository userRepository;
	private RoleRepository roleRepository;
	private AccountRepository accountRepository;
	private AgentRepository agentRepository;
	private GuestRepository guestRepository;
	private PreferencesRepository preferencesRepository;
	private CompanyAccountRepository companyAccountRepository;
	private IAvatarService avatarService;
	
	@Autowired
	public TeamService(HttpServletRequest request, PasswordEncoder passwordEncoder, UserRepository userRepository, 
			RoleRepository roleRepository, AccountRepository accountRepository, AgentRepository agentRepository, 
			GuestRepository guestRepository, PreferencesRepository preferencesRepository, 
			CompanyAccountRepository companyAccountRepository, IAvatarService avatarService) {
		this.request = request;
		this.passwordEncoder = passwordEncoder;
		this.userRepository = userRepository;
		this.roleRepository = roleRepository;
		this.accountRepository = accountRepository;
		this.agentRepository = agentRepository;
		this.guestRepository = guestRepository;
		this.preferencesRepository = preferencesRepository;
		this.companyAccountRepository = companyAccountRepository;
		this.avatarService = avatarService;
	}
	
	@Override
	@Transactional(readOnly = true)
	public long countGuest(final Long companyId) {
		return guestRepository.countByCompanyId(companyId);
	}
	
	@Override
	@Transactional(readOnly = true)
	public long countUserAndGuest(final Long companyId) {
		return userRepository.countByCompanyId(companyId) + guestRepository.countByCompanyId(companyId);
	}
	
	@Override
	@Transactional(readOnly = true)
	public boolean hasSuperAdmin(final Long id, final Long companyId) {
		final Optional<Long> uOptional = companyAccountRepository.findCreatedById(companyId);
		return uOptional.isPresent() ? uOptional.get().equals(id) : false;
	}
	
	@Override
	@Transactional(readOnly = true)
	public boolean hasContentSuperAdmin(final List<Long> ids, final Long companyId) {
		final Optional<Long> uOptional = companyAccountRepository.findCreatedById(companyId);
		if(uOptional.isPresent()) {
			return ids.contains(uOptional.get());
		}
		return false;
	}
	
	private final TeamuserForm parseTeamuserForm(final User user) {
		final TeamuserForm teamuserForm = new TeamuserForm();
		teamuserForm.setId(user.getId());
		teamuserForm.setCompanyId(user.getCompanyId());
		teamuserForm.setFirstname(user.getFirstName());
		teamuserForm.setLastname(user.getLastName());
		teamuserForm.setEmail(user.getEmail());
		teamuserForm.setRole(ParseUtil.getRoleIndex(user.getRoles()));
		teamuserForm.setHasAvatar(user.getHasAvatar());
		teamuserForm.setUrlAvatar(user.getHasAvatar() 
				? ConstraintesURL.URL_AVATARS + "?postedId=" + user.getId() + "&type=" + AvatarType.account 
				: "/static/picts/avatars/account-min.jpg");
		return teamuserForm;
	}
	
	@Override
	@Transactional(readOnly = true)
	public TeamuserForm readTeamuserForm(final Long id, final Long companyId) {
		final Optional<User> uOptional = userRepository.findById(id);
		if(uOptional.isPresent() && companyId.equals(uOptional.get().getCompanyId())) {
			final User user = uOptional.get();
			return parseTeamuserForm(user);
		}
		return null;
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
	private final User createNewUser(final TeamuserForm teamuserForm) {
		final User user = new User();
		user.setCompanyId(teamuserForm.getCompanyId());
		user.setFirstName(teamuserForm.getFirstname());
		user.setLastName(teamuserForm.getLastname());
		user.setEmail(teamuserForm.getEmail());
		if(teamuserForm.isHasRandomPassword()) {
			final String randomPassword = ParseUtil.generatePassword(8);
			user.setPassword(passwordEncoder.encode(randomPassword));
			user.setRandomPassword(randomPassword);
		} else {
			user.setPassword(passwordEncoder.encode(teamuserForm.getPassword()));
		}
		user.setRoles(new ArrayList<Role>(Arrays.asList(roleRepository.findByName("ROLE_ACCOUNT"),
				roleRepository.findByName(ParseUtil.getRoleName(teamuserForm.getRole())))));
		user.setHasAvatar(teamuserForm.isHasAvatar());
		return userRepository.save(user);
	}
	
	@Transactional
	private final Account createAccount(final User user) {
		final Account account = new Account();
		account.setUserId(user.getId());
		account.setPseudo(generatePseudo(user.getDisplayName()));
		account.setCreateDate(new DateTime(Date.from(Instant.now())));
		account.setIp(RequestUtil.getClientIP(request));
		return accountRepository.save(account);
	}
	
	@Override
	@Transactional
	public User addUser(final TeamuserForm teamuserForm) {
		if(userRepository.existsByEmail(teamuserForm.getEmail())) {
			throw new AlreadyExistException("message.error.emailexist");
		}
		final User user = createNewUser(teamuserForm);
		createAccount(user);
		if(teamuserForm.isHasAvatar()) {
			avatarService.postOrUpdate(teamuserForm.getFile(), user.getId(), AvatarType.account);
		}
		return user;
	}
	
	@Override
	@Transactional
	public User updateUser(final TeamuserForm teamuserForm) {
		final Optional<User> uOptional = userRepository.findById(teamuserForm.getId());
		if(uOptional.isPresent() && teamuserForm.getCompanyId().equals(uOptional.get().getCompanyId())) {
			final User user = uOptional.get();
			if(!teamuserForm.getEmail().equalsIgnoreCase(user.getEmail()) && userRepository.existsByEmail(teamuserForm.getEmail())) {
				throw new AlreadyExistException("message.error.emailexist");
			}
			user.setFirstName(teamuserForm.getFirstname());
			user.setLastName(teamuserForm.getLastname());
			user.setRoles(new ArrayList<Role>(Arrays.asList(roleRepository.findByName("ROLE_ACCOUNT"),
					roleRepository.findByName(ParseUtil.getRoleName(teamuserForm.getRole())))));
			if(!teamuserForm.getEmail().equalsIgnoreCase(user.getEmail())) {
				user.setEmail(teamuserForm.getEmail());
				user.setEnabled(false);
			}
			if(teamuserForm.isHasFileChanged()) {
				if(teamuserForm.isHasAvatar()) {
					avatarService.postOrUpdate(teamuserForm.getFile(), user.getId(), AvatarType.account);
				} else if(user.getHasAvatar()) {
					avatarService.deleteAvatar(user.getId(), AvatarType.account);
				}
				user.setHasAvatar(teamuserForm.isHasAvatar());
			}
			return userRepository.save(user);
		}
		return null;
	}
	
	@Override
	@Transactional(readOnly = true)
	public ElementsList findUsersList(final Long companyId, final Integer filter, final String search, final int sort, 
			final int rows, final int page, final boolean hasDesc) {
		final Long countResult = userRepository.countAllUserCriteria(companyId, filter, search);
		final List<UserLine> lines = countResult == 0L ? new ArrayList<UserLine>() 
				: userRepository.findAllUserCriteria(companyId, filter, search, sort, rows, page, hasDesc);
		return new ElementsList(countResult, lines);
	}
	
	@Transactional
	private final Long readCompanyAdmin(final Long companyId) {
		final Optional<Long> uOptional = companyAccountRepository.findCreatedById(companyId);
		return uOptional.isPresent() ? uOptional.get() : null;
	}
	
	@Transactional
	private final Preferences updateMessengerId(final Long companyId, final Long userId) {
		final Optional<Preferences> uOptional = preferencesRepository.findById(companyId);
		if(uOptional.isPresent() && uOptional.get().getMessengerId().equals(userId)) {
			final Preferences preferences = uOptional.get();
			final Long companyAdminId = readCompanyAdmin(companyId);
			preferences.setMessengerId(companyAdminId);
			return preferencesRepository.save(preferences);
		}
		return null;
	}
	
	@Override
	@Transactional
	public User removeUser(final Long id, final Long companyId) {
		final Optional<User> uOptional = userRepository.findById(id);
		if(!uOptional.isPresent() || !companyId.equals(uOptional.get().getCompanyId())) {
			throw new NotFoundException("message.error.notfound");
		}
		final User user = uOptional.get();
		user.setCompanyId(null);
		user.setRoles(new ArrayList<Role>(Arrays.asList(roleRepository.findByName("ROLE_ACCOUNT"), roleRepository.findByName("ROLE_VISITOR"))));
		updateMessengerId(companyId, id);
		agentRepository.trashUserId(id);
		return userRepository.save(user);
	}
	
	@Override
	@Transactional
	public void removeUsers(final List<Long> lines, final Long companyId) {
		if(lines.isEmpty()) {
			throw new NotFoundException("message.error.notfound");
		}
		final List<User> users = userRepository.findAllById(lines);
		for (final User user : users) {
			if(companyId.equals(user.getCompanyId())) {
				user.setCompanyId(null);
				user.setRoles(new ArrayList<Role>(Arrays.asList(roleRepository.findByName("ROLE_ACCOUNT"), roleRepository.findByName("ROLE_VISITOR"))));
				updateMessengerId(companyId, user.getId());
			}
		}
		if(!users.isEmpty()) {
			userRepository.saveAll(users);
			agentRepository.trashUsers(companyId, lines);
		}
	}
	
}
