package com.rinitec.algerieoffice.services.admins.realtime;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rinitec.algerieoffice.persistence.dao.admins.realtime.DeactivateRepository;
import com.rinitec.algerieoffice.persistence.dao.users.UserRepository;
import com.rinitec.algerieoffice.persistence.modal.admins.realtime.Deactivate;
import com.rinitec.algerieoffice.persistence.modal.users.User;
import com.rinitec.algerieoffice.services.admins.IDeleteCompanyService;
import com.rinitec.algerieoffice.services.admins.IDeleteUserService;
import com.rinitec.algerieoffice.web.error.exception.InvalidResourceException;
import com.rinitec.algerieoffice.web.error.exception.NotFoundException;
import com.rinitec.algerieoffice.web.modal.admins.ElementsList;
import com.rinitec.algerieoffice.web.modal.admins.realtime.DeactivateCompanyLine;
import com.rinitec.algerieoffice.web.modal.admins.realtime.DeactivateUserLine;

@Service
public class AdmDeactivateService implements IAdmDeactivateService {

	private UserRepository userRepository;
	private DeactivateRepository deactivateRepository;
	private IDeleteUserService deleteUserService;
	private IDeleteCompanyService deleteCompanyService;
	
	@Autowired
	public AdmDeactivateService(UserRepository userRepository, DeactivateRepository deactivateRepository, 
			IDeleteUserService deleteUserService, IDeleteCompanyService deleteCompanyService) {
		this.userRepository = userRepository;
		this.deactivateRepository = deactivateRepository;
		this.deleteUserService = deleteUserService;
		this.deleteCompanyService = deleteCompanyService;
	}
	
	@Override
	@Transactional(readOnly = true)
	public ElementsList findDeactivateCompanyList(final Integer filter, final String search, final int sort, final int rows, final int page,
			final boolean hasDesc) {
		final Long countResult = deactivateRepository.countAllDeactivateCompany(filter, search);
		final List<DeactivateCompanyLine> lines = countResult == 0L ? new ArrayList<DeactivateCompanyLine>() 
				: deactivateRepository.findAllDeactivateCompany(filter, search, sort, rows, page, hasDesc);
		return new ElementsList(countResult, lines);
	}
	
	@Override
	@Transactional
	public Deactivate validateDeactivate(final String id) {
		try {
			final Optional<Deactivate> uOptional = deactivateRepository.findById(UUID.fromString(id));
			if(!uOptional.isPresent()) {
				throw new NotFoundException("message.error.notfound");
			}
			final Deactivate deactivate = uOptional.get();
			deactivate.setConsulted(true);
			return deactivateRepository.save(deactivate);
		} catch (IllegalArgumentException e) {
			throw new InvalidResourceException();
		}
	}
	
	@Override
	@Transactional
	public Deactivate deleteDeactivateCompany(final String id) {
		try {
			final Optional<Deactivate> uOptional = deactivateRepository.findById(UUID.fromString(id));
			if(!uOptional.isPresent() || !uOptional.get().getHasCompany()) {
				throw new NotFoundException("message.error.notfound");
			}
			final Deactivate deactivate = uOptional.get();
			deleteCompanyService.deleteCompany(deactivate.getAccountId());
			deactivateRepository.delete(deactivate);
			return deactivate;
		} catch (IllegalArgumentException e) {
			throw new InvalidResourceException();
		}
	}
	
	@Override
	@Transactional
	public void deleteDeactivateCompanies(final List<String> lines) {
		for (final String id : lines) {
			deleteDeactivateCompany(id);
		}
	}
	
	@Override
	@Transactional(readOnly = true)
	public ElementsList findDeactivateUserList(Integer filter, String search, int sort, int rows, int page,
			boolean hasDesc) {
		final Long countResult = deactivateRepository.countAllDeactivateUser(filter, search);
		final List<DeactivateUserLine> lines = countResult == 0L ? new ArrayList<DeactivateUserLine>() 
				: deactivateRepository.findAllDeactivateUser(filter, search, sort, rows, page, hasDesc);
		return new ElementsList(countResult, lines);
	}
	
	@Override
	@Transactional
	public User restoreDeactivate(final String id) {
		try {
			final Optional<Deactivate> uOptional = deactivateRepository.findById(UUID.fromString(id));
			if(!uOptional.isPresent() || uOptional.get().getHasCompany()) {
				throw new NotFoundException("message.error.notfound");
			}
			final Deactivate deactivate = uOptional.get();
			final User user = userRepository.findById(deactivate.getAccountId()).get();
			user.setExpired(false);
			deactivateRepository.delete(deactivate);
			return userRepository.save(user);
		} catch (IllegalArgumentException e) {
			throw new InvalidResourceException();
		}
	}
	
	@Override
	@Transactional
	public User deleteDeactivateUser(final String id) {
		try {
			final Optional<Deactivate> uOptional = deactivateRepository.findById(UUID.fromString(id));
			if(!uOptional.isPresent() || uOptional.get().getHasCompany()) {
				throw new NotFoundException("message.error.notfound");
			}
			final Deactivate deactivate = uOptional.get();
			final User user = userRepository.findById(deactivate.getAccountId()).get();
			deleteUserService.deleteUser(user);
			deactivateRepository.delete(deactivate);
			return user;
		} catch (IllegalArgumentException e) {
			throw new InvalidResourceException();
		}
	}
	
	@Override
	@Transactional
	public void deleteDeactivateUsers(final List<String> lines) {
		for (final String id : lines) {
			deleteDeactivateUser(id);
		}
	}
	
}
