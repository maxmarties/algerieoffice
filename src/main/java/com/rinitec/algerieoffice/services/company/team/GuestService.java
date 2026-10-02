package com.rinitec.algerieoffice.services.company.team;

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

import com.rinitec.algerieoffice.persistence.dao.company.team.GuestRepository;
import com.rinitec.algerieoffice.persistence.dao.users.RoleRepository;
import com.rinitec.algerieoffice.persistence.dao.users.UserRepository;
import com.rinitec.algerieoffice.persistence.modal.company.team.Guest;
import com.rinitec.algerieoffice.persistence.modal.users.Role;
import com.rinitec.algerieoffice.persistence.modal.users.User;
import com.rinitec.algerieoffice.utils.ParseUtil;
import com.rinitec.algerieoffice.web.error.exception.AccessLeaderException;
import com.rinitec.algerieoffice.web.error.exception.AlreadyExistException;
import com.rinitec.algerieoffice.web.error.exception.NotFoundException;
import com.rinitec.algerieoffice.web.error.exception.UrlUnavailableException;
import com.rinitec.algerieoffice.web.form.company.team.TeamguestForm;
import com.rinitec.algerieoffice.web.modal.admins.ElementsList;
import com.rinitec.algerieoffice.web.modal.company.team.GuestLine;
import com.rinitec.algerieoffice.web.modal.user.communication.UserContributorLine;

@Service
public class GuestService implements IGuestService {

	private GuestRepository guestRepository;
	private UserRepository userRepository;
	private RoleRepository roleRepository;
	
	@Autowired
	public GuestService(GuestRepository guestRepository, UserRepository userRepository, 
			RoleRepository roleRepository) {
		this.guestRepository = guestRepository;
		this.userRepository = userRepository;
		this.roleRepository = roleRepository;
	}
	
	@Override
	@Transactional
	public Guest addGuest(final TeamguestForm teamguestForm, final Long companyId, final Long autorId) {
		final User user = userRepository.findByEmail(teamguestForm.getGuestmail());
		if(user == null) {
			throw new NotFoundException("message.input.notfound");
		}
		if(companyId.equals(user.getCompanyId())) {
			throw new AccessLeaderException("message.input.guest");
		}
		if(user.getCompanyId() != null) {
			throw new UrlUnavailableException("message.error.guest");
		}
		if(guestRepository.existsByCompanyIdAndUserId(companyId, user.getId())) {
			throw new AlreadyExistException("message.warning.guest");
		}
		final Guest guest = new Guest();
		guest.setCompanyId(companyId);
		guest.setUserId(user.getId());
		guest.setRole(teamguestForm.getGuestrole());
		guest.setGuestDate(new DateTime(Date.from(Instant.now())));
		guest.setGuestBy(autorId);
		return guestRepository.save(guest);
	}
	
	@Override
	@Transactional(readOnly = true)
	public ElementsList findUserContributorList(final Long userId, final Integer filter, final String search, final int sort,
			final int rows, final int page, final boolean hasDesc) {
		final Long countResult = guestRepository.countAllGuestUserCriteria(userId, filter, search);
		final List<UserContributorLine> lines = guestRepository.findAllGuestUserCriteria(userId, filter, search, sort, rows, page, hasDesc);
		return new ElementsList(countResult, lines);
	}
	
	@Override
	@Transactional
	public Guest validateUserGuest(final Long id, final User user) {
		final Optional<Guest> uOptional = guestRepository.findById(id);
		if(!uOptional.isPresent() || !user.getId().equals(uOptional.get().getUserId())) {
			throw new NotFoundException("message.error.notfound");
		}
		final Guest guest = uOptional.get();
		user.setCompanyId(guest.getCompanyId());
		user.setRoles(new ArrayList<Role>(Arrays.asList(roleRepository.findByName("ROLE_ACCOUNT"),
				roleRepository.findByName(ParseUtil.getRoleName(guest.getRole())))));
		userRepository.save(user);
		guestRepository.deleteByUserId(user.getId());
		return guest;
	}
	
	@Override
	@Transactional
	public void deleteUserGuest(final Long id, final Long userId) {
		final Optional<Guest> uOptional = guestRepository.findById(id);
		if(!uOptional.isPresent() || !userId.equals(uOptional.get().getUserId())) {
			throw new NotFoundException("message.error.notfound");
		}
		final Guest guest = uOptional.get();
		guestRepository.delete(guest);
	}
	
	@Override
	@Transactional
	public void deleteUserGuests(final List<Long> lines, final Long userId) {
		if(lines.isEmpty() || lines.size() != (int) guestRepository.countUserGuests(userId, lines)) {
			throw new NotFoundException("message.error.notfound");
		}
		guestRepository.deleteUserGuests(lines);
	}
	
	@Override
	@Transactional
	public void deleteAllUserGuests(final Long userId) {
		guestRepository.deleteByUserId(userId);
	}
	
	@Override
	@Transactional(readOnly = true)
	public ElementsList findGuestList(final Long companyId, final Integer filter, final String search, final int sort, final int rows, 
			final int page, final boolean hasDesc) {
		final Long countResult = guestRepository.countAllGuestCompanyCriteria(companyId, filter, search);
		final List<GuestLine> lines = countResult == 0L ? new ArrayList<GuestLine>() 
				: guestRepository.findAllGuestCompanyCriteria(companyId, filter, search, sort, rows, page, hasDesc);
		return new ElementsList(countResult, lines);
	}
	
	@Override
	@Transactional
	public void deleteCompanyGuest(final Long id, final Long companyId) {
		final Optional<Guest> uOptional = guestRepository.findById(id);
		if(!uOptional.isPresent() || !companyId.equals(uOptional.get().getCompanyId())) {
			throw new NotFoundException("message.error.notfound");
		}
		final Guest guest = uOptional.get();
		guestRepository.delete(guest);
	}
	
	@Override
	@Transactional
	public void deleteCompanyGuests(final List<Long> lines, final Long companyId) {
		if(lines.isEmpty() || lines.size() != (int) guestRepository.countCompanyGuests(companyId, lines)) {
			throw new NotFoundException("message.error.notfound");
		}
		guestRepository.deleteCompanyGuests(lines);
	}
	
	@Override
	@Transactional
	public void deleteAllCompanyGuests(final Long companyId) {
		guestRepository.deleteByCompanyId(companyId);
	}
	
}
