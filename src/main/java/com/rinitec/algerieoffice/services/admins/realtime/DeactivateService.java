package com.rinitec.algerieoffice.services.admins.realtime;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rinitec.algerieoffice.persistence.dao.admins.realtime.DeactivateRepository;
import com.rinitec.algerieoffice.persistence.dao.company.CompanyRepository;
import com.rinitec.algerieoffice.persistence.dao.users.RoleRepository;
import com.rinitec.algerieoffice.persistence.dao.users.UserRepository;
import com.rinitec.algerieoffice.persistence.modal.admins.realtime.Deactivate;
import com.rinitec.algerieoffice.persistence.modal.users.Role;
import com.rinitec.algerieoffice.persistence.modal.users.User;
import com.rinitec.algerieoffice.web.form.company.tools.DeactivateForm;

@Service
public class DeactivateService implements IDeactivateService {

	private UserRepository userRepository;
	private RoleRepository roleRepository;
	private CompanyRepository companyRepository;
	private DeactivateRepository deactivateRepository;
	
	@Autowired
	public DeactivateService(UserRepository userRepository, RoleRepository roleRepository, 
			CompanyRepository companyRepository, DeactivateRepository deactivateRepository) {
		this.userRepository = userRepository;
		this.roleRepository = roleRepository;
		this.companyRepository = companyRepository;
		this.deactivateRepository = deactivateRepository;
	}
	
	@Transactional
	private final Deactivate postDeactivate(final DeactivateForm deactivateForm, final boolean hasCompany) {
		final Deactivate deactivate = new Deactivate();
		deactivate.setAccountId(deactivateForm.getCompanyId());
		deactivate.setReason(deactivateForm.getReason());
		deactivate.setObservation(deactivateForm.getObservation());
		deactivate.setHasCompany(hasCompany);
		deactivate.setDeactivateDate(new DateTime(Date.from(Instant.now())));
		return deactivateRepository.save(deactivate);
	}
	
	@Transactional
	private final User deactivateUserCompany(final User user) {
		user.setCompanyId(null);
		user.setRoles(new ArrayList<Role>(Arrays.asList(roleRepository.findByName("ROLE_ACCOUNT"), 
				roleRepository.findByName("ROLE_VISITOR"))));
		return userRepository.save(user);
	}
	
	@Override
	@Transactional
	public List<String> deactivateCompany(final DeactivateForm deactivateForm) {
		final List<String> emails = new ArrayList<String>();
		final List<User> users = userRepository.findByCompanyId(deactivateForm.getCompanyId());
		for (final User user : users) {
			emails.add(user.getEmail());
			deactivateUserCompany(user);
		}
		postDeactivate(deactivateForm, true);
		companyRepository.deactiavteCompany(deactivateForm.getCompanyId());
		return emails;
	}
	
	@Transactional
	private final User deactivateUser(final Long userId) {
		final Optional<User> uOptional = userRepository.findById(userId);
		if(uOptional.isPresent()) {
			final User user = uOptional.get();
			user.setExpired(true);
			return userRepository.save(user);
		}
		return null;
	}
	
	@Override
	@Transactional
	public User deactivateUser(final DeactivateForm deactivateForm) {
		final User user = deactivateUser(deactivateForm.getCompanyId());
		if(user != null) {
			postDeactivate(deactivateForm, false);
			return user;
		}
		return null;
	}
	
}
