package com.rinitec.algerieoffice.services.user;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rinitec.algerieoffice.persistence.dao.users.UserRepository;
import com.rinitec.algerieoffice.persistence.modal.users.User;

@Service
public class UserService implements IUserService {

	private PasswordEncoder passwordEncoder;
	private UserRepository userRepository;
	
	@Autowired
	public UserService(PasswordEncoder passwordEncoder, UserRepository userRepository) {
		this.passwordEncoder = passwordEncoder;
		this.userRepository = userRepository;
	}
	
	@Override
	@Transactional(readOnly = true)
	public User findUserByEmail(final String email) {
		return userRepository.findByEmail(email);
	}
	
	@Override
	@Transactional
	public void changePassword(final User user, final String password) {
		user.setPassword(passwordEncoder.encode(password));
        userRepository.save(user);
	}
	
	@Override
	public boolean checkIfValidOldPassword(final User user, final String password) {
		return passwordEncoder.matches(password, user.getPassword());
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<String> findAllEmail(final List<Long> usersId) {
		return userRepository.findAllEmailByUsersId(usersId);
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<Long> findAllIdByCompany(final Long companyId) {
		return userRepository.findAllIdByCompanyId(companyId);
	}
	
	@Override
	@Transactional(readOnly = true)
	public String findUsermail(final Long userId) {
		final Optional<String> uOptional = userRepository.findEmailById(userId);
		return uOptional.isPresent() ? uOptional.get() : null;
	}
	
}
