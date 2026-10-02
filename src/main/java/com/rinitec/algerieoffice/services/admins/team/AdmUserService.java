package com.rinitec.algerieoffice.services.admins.team;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rinitec.algerieoffice.persistence.dao.users.RoleRepository;
import com.rinitec.algerieoffice.persistence.dao.users.UserRepository;
import com.rinitec.algerieoffice.persistence.dao.users.profiles.AccountRepository;
import com.rinitec.algerieoffice.persistence.modal.users.Role;
import com.rinitec.algerieoffice.persistence.modal.users.User;
import com.rinitec.algerieoffice.persistence.modal.users.profiles.Account;
import com.rinitec.algerieoffice.services.admins.IDeleteUserService;
import com.rinitec.algerieoffice.utils.ParseUtil;
import com.rinitec.algerieoffice.web.error.exception.AlreadyExistException;
import com.rinitec.algerieoffice.web.error.exception.NotFoundException;
import com.rinitec.algerieoffice.web.form.admins.team.AdmManagerForm;
import com.rinitec.algerieoffice.web.form.admins.team.AdmUserForm;
import com.rinitec.algerieoffice.web.modal.admins.ElementsList;
import com.rinitec.algerieoffice.web.modal.admins.team.AdmManagerLine;
import com.rinitec.algerieoffice.web.modal.admins.team.AdmModeratorLine;
import com.rinitec.algerieoffice.web.modal.admins.team.AdmUserLine;

@Service
public class AdmUserService implements IAdmUserService {

	private UserRepository userRepository;
	private RoleRepository roleRepository;
	private AccountRepository accountRepository;
	private PasswordEncoder passwordEncoder;
	private IDeleteUserService deleteUserService;
	
	@Autowired
	public AdmUserService(UserRepository userRepository, RoleRepository roleRepository, AccountRepository accountRepository, 
			PasswordEncoder passwordEncoder, IDeleteUserService deleteUserService) {
		this.userRepository = userRepository;
		this.roleRepository = roleRepository;
		this.accountRepository = accountRepository;
		this.passwordEncoder = passwordEncoder;
		this.deleteUserService = deleteUserService;
	}
	
	@Override
	@Transactional(readOnly = true)
	public ElementsList findAdmUsersList(final Boolean filter, final String search, final int sort, final int rows, final int page, 
			final boolean hasDesc) {
		final Long countResult = userRepository.countAllAdmUserCriteria(filter, search);
		final List<AdmUserLine> lines = countResult == 0L ? new ArrayList<AdmUserLine>() 
				: userRepository.findAllAdmUserCriteria(filter, search, sort, rows, page, hasDesc);
		return new ElementsList(countResult, lines);
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
	private final User createNewUser(final AdmUserForm admUserForm) {
		final User user = new User();
		user.setFirstName(admUserForm.getFirstname());
		user.setLastName(admUserForm.getLastname());
		user.setEmail(admUserForm.getEmail());
		user.setPassword(passwordEncoder.encode(admUserForm.getPassword()));
		user.setEnabled(true);
		user.setRoles(new ArrayList<Role>(Arrays.asList(roleRepository.findByName("ROLE_ACCOUNT"), 
				roleRepository.findByName("ROLE_VISITOR"))));
		return userRepository.save(user);
	}
	
	@Transactional
	private final Account createAccount(final User user) {
		final Account account = new Account();
		account.setUserId(user.getId());
		account.setPseudo(generatePseudo(user.getDisplayName()));
		account.setCreateDate(new DateTime(Date.from(Instant.now())));
		account.setActivateDate(new DateTime(Date.from(Instant.now())));
		account.setIp("0:0:0:0:297");
		return accountRepository.save(account);
	}
	
	@Override
	@Transactional
	public User addUser(final AdmUserForm admUserForm) {
		if(userRepository.existsByEmail(admUserForm.getEmail())) {
			throw new AlreadyExistException("message.error.emailexist");
		}
		final User user = createNewUser(admUserForm);
		createAccount(user);
		return user;
	}
	
	@Override
	@Transactional
	public User lockUser(final Long userId) {
		final Optional<User> uOptional = userRepository.findById(userId);
		if(!uOptional.isPresent() || uOptional.get().getAdmin()) {
			throw new NotFoundException("message.error.notfound");
		}
		final User user = uOptional.get();
		user.setLocked(!user.isLocked());
		return userRepository.save(user);
	}
	
	@Override
	@Transactional
	public User deleteUser(final Long userId) {
		final Optional<User> uOptional = userRepository.findById(userId);
		if(!uOptional.isPresent() || uOptional.get().getAdmin()) {
			throw new NotFoundException("message.error.notfound");
		}
		final User user = uOptional.get();
		deleteUserService.deleteUser(user);
		return user;
	}
	
	@Override
	@Transactional
	public void deleteUsers(final List<Long> lines) {
		final List<User> users = userRepository.findAllUsers(lines);
		for (final User user : users) {
			deleteUserService.deleteUser(user);
		}
	}
	
	@Override
	@Transactional(readOnly = true)
	public ElementsList findAdmModeratorsList(final Integer filter, final String search, final int sort, final int rows, final int page,
			final boolean hasDesc) {
		final Long countResult = userRepository.countAllAdmModeratorCriteria(filter, search);
		final List<AdmModeratorLine> lines = countResult == 0L ? new ArrayList<AdmModeratorLine>() 
				: userRepository.findAllAdmModeratorCriteria(filter, search, sort, rows, page, hasDesc);
		return new ElementsList(countResult, lines);
	}
	
	@Override
	@Transactional(readOnly = true)
	public ElementsList findAdmManagersList(final Integer filter, final String search, final int sort, final int rows, final int page,
			final boolean hasDesc) {
		final Long countResult = userRepository.countAllAdmManagerCriteria(filter, search);
		final List<AdmManagerLine> lines = countResult == 0L ? new ArrayList<AdmManagerLine>() 
				: userRepository.findAllAdmManagerCriteria(filter, search, sort, rows, page, hasDesc);
		return new ElementsList(countResult, lines);
	}
	
	private final AdmManagerForm parseAdmManagerForm(final User user) {
		final AdmManagerForm admManagerForm = new AdmManagerForm();
		admManagerForm.setId(user.getId());
		admManagerForm.setFirstname(user.getFirstName());
		admManagerForm.setLastname(user.getLastName());
		admManagerForm.setEmail(user.getEmail());
		admManagerForm.setRole(ParseUtil.getRoleAdminIndex(user.getRoles()));
		return admManagerForm;
	}
	
	@Override
	@Transactional(readOnly = true)
	public AdmManagerForm readAdmManagerForm(final Long id) {
		final Optional<User> uOptional = userRepository.findById(id);
		if(uOptional.isPresent() && uOptional.get().getAdmin() 
				&& !ParseUtil.hasRoleAdmin(uOptional.get().getRoles())) {
			final User user = uOptional.get();
			return parseAdmManagerForm(user);
		}
		return null;
	}
	
	@Transactional
	private final User createNewManager(final AdmManagerForm admManagerForm) {
		final User user = new User();
		user.setFirstName(admManagerForm.getFirstname());
		user.setLastName(admManagerForm.getLastname());
		user.setEmail(admManagerForm.getEmail());
		user.setPassword(passwordEncoder.encode(admManagerForm.getPassword()));
		user.setRoles(new ArrayList<Role>(Arrays.asList(roleRepository.findByName("ROLE_ACCOUNT"),
				roleRepository.findByName(ParseUtil.getRoleAdmin(admManagerForm.getRole())))));
		user.setAdmin(true);
		user.setEnabled(true);
		return userRepository.save(user);
	}
	
	@Override
	@Transactional
	public User addManager(final AdmManagerForm admManagerForm) {
		if(userRepository.existsByEmail(admManagerForm.getEmail())) {
			throw new AlreadyExistException("message.error.emailexist");
		}
		final User user = createNewManager(admManagerForm);
		createAccount(user);
		return user;
	}
	
	@Override
	@Transactional
	public User updateManager(final AdmManagerForm admManagerForm) {
		final Optional<User> uOptional = userRepository.findById(admManagerForm.getId());
		if(uOptional.isPresent() && uOptional.get().getAdmin() 
				&& !ParseUtil.hasRoleAdmin(uOptional.get().getRoles())) {
			final User user = uOptional.get();
			if(!admManagerForm.getEmail().equalsIgnoreCase(user.getEmail()) 
					&& userRepository.existsByEmail(admManagerForm.getEmail())) {
				throw new AlreadyExistException("message.error.emailexist");
			}
			user.setFirstName(admManagerForm.getFirstname());
			user.setLastName(admManagerForm.getLastname());
			user.setEmail(admManagerForm.getEmail());
			user.setRoles(new ArrayList<Role>(Arrays.asList(roleRepository.findByName("ROLE_ACCOUNT"),
					roleRepository.findByName(ParseUtil.getRoleAdmin(admManagerForm.getRole())))));
			return userRepository.save(user);
		}
		return null;
	}
	
	@Override
	@Transactional
	public User lockManager(final Long userId) {
		final Optional<User> uOptional = userRepository.findById(userId);
		if(!uOptional.isPresent() || !uOptional.get().getAdmin() 
				|| ParseUtil.hasRoleAdmin(uOptional.get().getRoles())) {
			throw new NotFoundException("message.error.notfound");
		}
		final User user = uOptional.get();
		user.setLocked(!user.isLocked());
		return userRepository.save(user);
	}
	
	@Override
	@Transactional
	public User removeManager(final Long id) {
		final Optional<User> uOptional = userRepository.findById(id);
		if(uOptional.isPresent() && uOptional.get().getAdmin() 
				&& !ParseUtil.hasRoleAdmin(uOptional.get().getRoles())) {
			final User user = uOptional.get();
			user.setAdmin(false);
			user.setRoles(new ArrayList<Role>(Arrays.asList(roleRepository.findByName("ROLE_ACCOUNT"), 
					roleRepository.findByName("ROLE_VISITOR"))));
			return userRepository.save(user);
		}
		return null;
	}
	
	@Override
	@Transactional
	public void removeManagers(final List<Long> lines) {
		final List<User> users = userRepository.findAllById(lines);
		for (final User user : users) {
			if(user.getAdmin() && !ParseUtil.hasRoleAdmin(user.getRoles())) {
				user.setAdmin(false);
				user.setRoles(new ArrayList<Role>(Arrays.asList(roleRepository.findByName("ROLE_ACCOUNT"), 
						roleRepository.findByName("ROLE_VISITOR"))));
			}
		}
		userRepository.saveAll(users);
	}
	
}
